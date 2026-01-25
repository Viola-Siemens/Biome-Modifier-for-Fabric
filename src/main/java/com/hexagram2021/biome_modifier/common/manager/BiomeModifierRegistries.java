package com.hexagram2021.biome_modifier.common.manager;

import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifier;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class BiomeModifierRegistries {
	public static final ResourceKey<Registry<IBiomeModifier>> REGISTRY_BIOME_MODIFIER =
			ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("biome_modifiers"));
	public static final ResourceKey<Registry<IDimensionModifier>> REGISTRY_DIMENSION_MODIFIER =
			ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("dimension_modifiers"));
	public static final ResourceKey<Registry<INoiseGeneratorModifier>> REGISTRY_NOISE_GENERATOR_MODIFIER =
			ResourceKey.createRegistryKey(ResourceLocation.withDefaultNamespace("noise_generator_modifiers"));

	private BiomeModifierRegistries() {
	}

	public static void init() {
		DynamicRegistries.register(REGISTRY_BIOME_MODIFIER, IBiomeModifier.REGISTRY_CODEC);
		DynamicRegistries.register(REGISTRY_DIMENSION_MODIFIER, IDimensionModifier.REGISTRY_CODEC);
		DynamicRegistries.register(REGISTRY_NOISE_GENERATOR_MODIFIER, INoiseGeneratorModifier.REGISTRY_CODEC);
	}
}
