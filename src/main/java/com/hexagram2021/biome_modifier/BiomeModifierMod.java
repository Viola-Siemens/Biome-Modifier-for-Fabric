package com.hexagram2021.biome_modifier;

import com.hexagram2021.biome_modifier.api.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.common.manager.BiomeModifierManager;
import com.hexagram2021.biome_modifier.common.manager.BiomeModifierRegistries;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class BiomeModifierMod implements ModInitializer {
	public static final String MODID = "biome_modifier";

	@Override
	public void onInitialize() {
		BiomeModifierTypes.init();
		BiomeModifierRegistries.init();
		ServerLifecycleEvents.SERVER_STARTING.register(BiomeModifierMod::onServerAboutToStart);
	}

	@SuppressWarnings("ConstantValue")
	private static void onServerAboutToStart(MinecraftServer server) {
		long beginTime = System.currentTimeMillis();
		BMLogger.info("Applying biome modifiers...");
		RegistryAccess registryAccess = server.registryAccess();
		Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
		int biomeModifiersCount = 0;
		try {
			BiomeModifierManager manager = Objects.requireNonNull(BiomeModifierManager.INSTANCE);
			manager.load(registryAccess);
			biomeModifiersCount = manager.getAllBiomeModifiers().size();
			biomeRegistry.holders().forEach(biomeHolder -> {
				Biome biome = biomeHolder.value();
				if((Object)biome instanceof IModifiableBiome modifiableBiome) {
					IModifiableBiome.BiomeModificationParametersList list = modifiableBiome.biome_modifier$getBiomeModificationParametersList(registryAccess);
					AtomicInteger count = new AtomicInteger(0);
					manager.applyAllBiomeModifiers((id, modifier) -> {
						if(modifier.canModify(biomeHolder)) {
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
		long endTime = System.currentTimeMillis();
		BMLogger.info("Applied %d biome modifiers in %d ms.".formatted(biomeModifiersCount, endTime - beginTime));
	}
}
