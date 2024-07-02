package com.hexagram2021.biome_modifier;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.common.manager.BiomeModifierManager;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BiomeModifierMod implements ModInitializer {
	public static final String MODID = "biome_modifier";

	@Override
	public void onInitialize() {
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new BiomeModifierManager());
		ServerLifecycleEvents.SERVER_STARTING.register(BiomeModifierMod::onServerAboutToStart);
	}

	@SuppressWarnings("ConstantValue")
	private static void onServerAboutToStart(MinecraftServer server) {
		RegistryAccess registryAccess = server.registryAccess();
		Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
		try {
			BiomeModifierManager manager = Objects.requireNonNull(BiomeModifierManager.INSTANCE);
			biomeRegistry.forEach(biome -> {
				if((Object)biome instanceof IModifiableBiome modifiableBiome) {
					IModifiableBiome.BiomeModificationParametersList list = modifiableBiome.biome_modifier$getBiomeModificationParametersList(registryAccess);
					AtomicInteger count = new AtomicInteger(0);
					manager.applyAllBiomeModifiers((id, modifier) -> {
						if(modifier.canModify(biome)) {
							modifier.modify(list);
						}
						if(list.errorCount() > count.get()) {
							BMLogger.error("Above error occurs when applying modifier %s.".formatted(id));
							count.set(list.errorCount());
						}
					});
					modifiableBiome.biome_modifier$modifyBiome(list);
				}
			});
		} catch(RuntimeException e) {
			BMLogger.error("Error when applying modifiers: ", e);
		}
	}
}
