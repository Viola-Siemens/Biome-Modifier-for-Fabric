package com.hexagram2021.biome_modifier.api.modifiers.biome;

import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public final class BiomeModifierNames {
	public static final ResourceLocation NONE = ResourceLocation.fromNamespaceAndPath(MODID, "none");
	public static final ResourceLocation ADD_FEATURES = ResourceLocation.fromNamespaceAndPath(MODID, "add_features");
	public static final ResourceLocation REMOVE_FEATURES = ResourceLocation.fromNamespaceAndPath(MODID, "remove_features");
	public static final ResourceLocation ADD_SPAWNS = ResourceLocation.fromNamespaceAndPath(MODID, "add_spawns");
	public static final ResourceLocation REMOVE_SPAWNS = ResourceLocation.fromNamespaceAndPath(MODID, "remove_spawns");
	public static final ResourceLocation ADD_SPAWN_COSTS = ResourceLocation.fromNamespaceAndPath(MODID, "add_spawn_costs");
	public static final ResourceLocation REMOVE_SPAWN_COSTS = ResourceLocation.fromNamespaceAndPath(MODID, "remove_spawn_costs");
	public static final ResourceLocation ADD_CARVERS = ResourceLocation.fromNamespaceAndPath(MODID, "add_carvers");
	public static final ResourceLocation REMOVE_CARVERS = ResourceLocation.fromNamespaceAndPath(MODID, "remove_carvers");
	public static final ResourceLocation PRECIPITATION = ResourceLocation.fromNamespaceAndPath(MODID, "precipitation");
	public static final ResourceLocation TEMPERATURE = ResourceLocation.fromNamespaceAndPath(MODID, "temperature");
	public static final ResourceLocation DOWNFALL = ResourceLocation.fromNamespaceAndPath(MODID, "downfall");
	public static final ResourceLocation TEMPERATURE_ADJUSTMENT = ResourceLocation.fromNamespaceAndPath(MODID, "temperature_adjustment");
	public static final ResourceLocation CREATURE_SPAWN_PROBABILITY = ResourceLocation.fromNamespaceAndPath(MODID, "creature_spawn_probability");
	public static final ResourceLocation FOG_COLOR = ResourceLocation.fromNamespaceAndPath(MODID, "fog_color");
	public static final ResourceLocation WATER_COLOR = ResourceLocation.fromNamespaceAndPath(MODID, "water_color");
	public static final ResourceLocation WATER_FOG_COLOR = ResourceLocation.fromNamespaceAndPath(MODID, "water_fog_color");
	public static final ResourceLocation SKY_COLOR = ResourceLocation.fromNamespaceAndPath(MODID, "sky_color");

	private BiomeModifierNames() {
	}
}
