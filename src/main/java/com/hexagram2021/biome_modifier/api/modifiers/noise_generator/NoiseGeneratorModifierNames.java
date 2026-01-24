package com.hexagram2021.biome_modifier.api.modifiers.noise_generator;

import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public final class NoiseGeneratorModifierNames {
	public static final ResourceLocation NONE = ResourceLocation.fromNamespaceAndPath(MODID, "none");
	public static final ResourceLocation DEFAULT_BLOCK = ResourceLocation.fromNamespaceAndPath(MODID, "default_block");
	public static final ResourceLocation DEFAULT_FLUID = ResourceLocation.fromNamespaceAndPath(MODID, "default_fluid");
	public static final ResourceLocation SEA_LEVEL = ResourceLocation.fromNamespaceAndPath(MODID, "sea_level");
	public static final ResourceLocation AQUIFERS = ResourceLocation.fromNamespaceAndPath(MODID, "aquifers");
	public static final ResourceLocation ORE_VEINS = ResourceLocation.fromNamespaceAndPath(MODID, "ore_veins");

	private NoiseGeneratorModifierNames() {
	}
}
