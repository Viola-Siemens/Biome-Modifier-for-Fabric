package com.hexagram2021.biome_modifier.api.modifiers.noise_generator;

import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public final class NoiseGeneratorModifierNames {
	public static final ResourceLocation NONE = new ResourceLocation(MODID, "none");
	public static final ResourceLocation DEFAULT_BLOCK = new ResourceLocation(MODID, "default_block");
	public static final ResourceLocation DEFAULT_FLUID = new ResourceLocation(MODID, "default_fluid");
	public static final ResourceLocation SEA_LEVEL = new ResourceLocation(MODID, "sea_level");
	public static final ResourceLocation AQUIFERS = new ResourceLocation(MODID, "aquifers");
	public static final ResourceLocation ORE_VEINS = new ResourceLocation(MODID, "ore_veins");

	private NoiseGeneratorModifierNames() {
	}
}
