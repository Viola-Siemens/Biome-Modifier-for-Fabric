package com.hexagram2021.biome_modifier;

import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.common.manager.*;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;

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

	private static void onServerAboutToStart(MinecraftServer server) {
		RegistryAccess registryAccess = server.registryAccess();

		applyDimensionModifiers(registryAccess);
		applyBiomeModifiers(registryAccess);
	}

	@SuppressWarnings("ConstantValue")
	private static void applyDimensionModifiers(RegistryAccess registryAccess) {
		long beginTime = System.currentTimeMillis();
		BMLogger.info("Applying dimension modifiers...");
		Registry<DimensionType> dimensionTypeRegistry = registryAccess.registryOrThrow(Registries.DIMENSION_TYPE);
		int dimensionModifiersCount = 0;
		try {
			DimensionModifierManager manager = Objects.requireNonNull(DimensionModifierManager.INSTANCE);
			manager.load(registryAccess);
			manager.freeze();
			dimensionModifiersCount = manager.getAllDimensionModifiers().size();
			dimensionTypeRegistry.holders().forEach(dimensionHolder -> {
				DimensionType dimensionType = dimensionHolder.value();
				if((Object)dimensionType instanceof IModifiableDimension modifiableDimension) {
					IModifiableDimension.DimensionModificationParametersList list = modifiableDimension.biome_modifier$getDimensionModificationParametersList(registryAccess);
					AtomicInteger count = new AtomicInteger(0);
					manager.applyAllDimensionModifiers((id, modifier) -> {
						if(modifier.canModify(dimensionHolder)) {
							modifier.modify(list);
						}
						if(list.errorCount() > count.get()) {
							BMLogger.error("Above error occurs when applying dimension modifier %s.".formatted(id));
							count.set(list.errorCount());
						}
					});
					modifiableDimension.biome_modifier$modifyDimension(list);
				}
			});
		} catch(RuntimeException e) {
			BMLogger.error("Error when applying dimension modifiers: ", e);
		}
		long endTime = System.currentTimeMillis();
		BMLogger.info("Applied %d dimension modifiers in %d ms.".formatted(dimensionModifiersCount, endTime - beginTime));
	}

	@SuppressWarnings("ConstantValue")
	private static void applyBiomeModifiers(RegistryAccess registryAccess) {
		long beginTime = System.currentTimeMillis();
		BMLogger.info("Applying biome modifiers...");
		Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
		int biomeModifiersCount = 0;
		try {
			BiomeModifierManager manager = Objects.requireNonNull(BiomeModifierManager.INSTANCE);
			manager.load(registryAccess);
			manager.freeze();
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
							BMLogger.error("Above error occurs when applying biome modifier %s.".formatted(id));
							count.set(list.errorCount());
						}
					});
					modifiableBiome.biome_modifier$modifyBiome(list);
				}
			});
		} catch(RuntimeException e) {
			BMLogger.error("Error when applying biome modifiers: ", e);
		}
		long endTime = System.currentTimeMillis();
		BMLogger.info("Applied %d biome modifiers in %d ms.".formatted(biomeModifiersCount, endTime - beginTime));
	}
}
