package com.hexagram2021.biome_modifier;

import com.hexagram2021.biome_modifier.api.IErrorHandlerParametersList;
import com.hexagram2021.biome_modifier.api.IModifiableApi;
import com.hexagram2021.biome_modifier.api.modifiers.IModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.NoiseGeneratorModifierTypes;
import com.hexagram2021.biome_modifier.common.manager.*;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.hexagram2021.biome_modifier.common.utils.Invalidatable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.atomic.AtomicInteger;

public class BiomeModifierMod implements ModInitializer {
	public static final String MODID = "biome_modifier";

	@Override
	public void onInitialize() {
		BiomeModifierTypes.init();
		DimensionModifierTypes.init();
		NoiseGeneratorModifierTypes.init();
		BiomeModifierRegistries.init();
		ServerLifecycleEvents.SERVER_STARTING.register(BiomeModifierMod::onServerAboutToStart);
		ServerLifecycleEvents.SERVER_STOPPED.register(BiomeModifierMod::onServerStopped);
	}

	private static void onServerAboutToStart(MinecraftServer server) {
		RegistryAccess registryAccess = server.registryAccess();

		applyDimensionModifiers(registryAccess);
		applyNoiseGeneratorModifiers(registryAccess);
		applyBiomeModifiers(registryAccess);

		invalidateChunkGeneratorCache(registryAccess);
	}

	private static void onServerStopped(MinecraftServer server) {
		BiomeModifierManager.INSTANCE.unfreeze();
		DimensionModifierManager.INSTANCE.unfreeze();
		NoiseGeneratorModifierManager.INSTANCE.unfreeze();
	}

	private static void applyNoiseGeneratorModifiers(RegistryAccess registryAccess) {
		genericallyApplyModifiersFromManager(NoiseGeneratorModifierManager.INSTANCE, Registries.NOISE_SETTINGS, registryAccess, "noise generator");
	}

	private static void applyDimensionModifiers(RegistryAccess registryAccess) {
		genericallyApplyModifiersFromManager(DimensionModifierManager.INSTANCE, Registries.DIMENSION_TYPE, registryAccess, "dimension");
	}

	private static void applyBiomeModifiers(RegistryAccess registryAccess) {
		genericallyApplyModifiersFromManager(BiomeModifierManager.INSTANCE, Registries.BIOME, registryAccess, "biome");
	}

	private static void invalidateChunkGeneratorCache(RegistryAccess registryAccess) {
		registryAccess.registryOrThrow(Registries.LEVEL_STEM).forEach(levelStem -> {
			if(levelStem.generator() instanceof Invalidatable invalidatable) {
				invalidatable.biome_modifier$invalidate();
			}
		});
	}

	private static <R, L extends IErrorHandlerParametersList, T extends IModifier<R, L>, A extends T, M extends IModifiableApi<L>>
	void genericallyApplyModifiersFromManager(IModifierManager<T, A, M> manager, ResourceKey<Registry<R>> registryKey,
											  RegistryAccess registryAccess, String debugInfo) {
		long beginTime = System.currentTimeMillis();
		BMLogger.info("Applying %s modifiers...".formatted(debugInfo));
		Registry<R> registry = registryAccess.registryOrThrow(registryKey);
		int modifiersCount = 0;
		try {
			manager.load(registryAccess);
			manager.freeze();
			modifiersCount = manager.getAllModifiers().size();
			registry.holders().forEach(holder -> {
				R value = holder.value();
				manager.tryCastOrElseThrow(value, modifiable -> {
					var list = modifiable.biome_modifier$getModificationParametersList(registryAccess);
					AtomicInteger errorCount = new AtomicInteger(0);
					manager.applyAllModifiers((id, modifier) -> {
						if(modifier.canModify(holder)) {
							modifier.modify(list);
						}
						if(list.errorCount() > errorCount.get()) {
							BMLogger.error("Above error occurs when applying %s modifier %s.".formatted(debugInfo, id));
							errorCount.set(list.errorCount());
						}
					});
					modifiable.biome_modifier$modify(list);
				});
			});
		} catch(RuntimeException e) {
			BMLogger.error("Error when applying %s modifiers: ".formatted(debugInfo), e);
		}
		long endTime = System.currentTimeMillis();
		BMLogger.info("Applied %d %s modifiers in %d ms.".formatted(modifiersCount, debugInfo, endTime - beginTime));
	}
}
