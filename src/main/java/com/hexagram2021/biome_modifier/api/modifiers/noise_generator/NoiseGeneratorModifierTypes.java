package com.hexagram2021.biome_modifier.api.modifiers.noise_generator;

import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.impl.*;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.resources.ResourceLocation;

public final class NoiseGeneratorModifierTypes {
	public static final INoiseGeneratorModifierType NONE = register(NoiseGeneratorModifierNames.NONE, () -> NoneNoiseGeneratorModifier.CODEC);
	public static final INoiseGeneratorModifierType DEFAULT_BLOCK = register(NoiseGeneratorModifierNames.DEFAULT_BLOCK, () -> DefaultBlock.CODEC);
	public static final INoiseGeneratorModifierType DEFAULT_FLUID = register(NoiseGeneratorModifierNames.DEFAULT_FLUID, () -> DefaultFluid.CODEC);
	public static final INoiseGeneratorModifierType SEA_LEVEL = register(NoiseGeneratorModifierNames.SEA_LEVEL, () -> SeaLevel.CODEC);
	public static final INoiseGeneratorModifierType AQUIFERS = register(NoiseGeneratorModifierNames.AQUIFERS, () -> Aquifers.CODEC);
	public static final INoiseGeneratorModifierType ORE_VEINS = register(NoiseGeneratorModifierNames.ORE_VEINS, () -> OreVeins.CODEC);

	private NoiseGeneratorModifierTypes() {
	}

	public static INoiseGeneratorModifierType register(ResourceLocation id, INoiseGeneratorModifierType type) {
		INoiseGeneratorModifierType.register(id, type);
		return type;
	}

	public static void init() {
		BMLogger.info("Loaded %d noise generator modifier types.".formatted(INoiseGeneratorModifierType.NOISE_GENERATOR_MODIFIER_TYPES.size()));
	}
}
