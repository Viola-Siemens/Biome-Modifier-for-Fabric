package com.hexagram2021.biome_modifier.api.modifiers.biome;

import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public final class BiomeModifierNames {
	public static final ResourceLocation NONE = new ResourceLocation(MODID, "none");
	public static final ResourceLocation ADD_FEATURES = new ResourceLocation(MODID, "add_features");
	public static final ResourceLocation REMOVE_FEATURES = new ResourceLocation(MODID, "remove_features");
	public static final ResourceLocation ADD_SPAWNS = new ResourceLocation(MODID, "add_spawns");
	public static final ResourceLocation REMOVE_SPAWNS = new ResourceLocation(MODID, "remove_spawns");
	public static final ResourceLocation ADD_SPAWN_COSTS = new ResourceLocation(MODID, "add_spawn_costs");
	public static final ResourceLocation REMOVE_SPAWN_COSTS = new ResourceLocation(MODID, "remove_spawn_costs");
	public static final ResourceLocation ADD_CARVERS = new ResourceLocation(MODID, "add_carvers");
	public static final ResourceLocation REMOVE_CARVERS = new ResourceLocation(MODID, "remove_carvers");
	public static final ResourceLocation PRECIPITATION = new ResourceLocation(MODID, "precipitation");
	public static final ResourceLocation TEMPERATURE = new ResourceLocation(MODID, "temperature");
	public static final ResourceLocation DOWNFALL = new ResourceLocation(MODID, "downfall");
	public static final ResourceLocation TEMPERATURE_ADJUSTMENT = new ResourceLocation(MODID, "temperature_adjustment");
	public static final ResourceLocation CREATURE_SPAWN_PROBABILITY = new ResourceLocation(MODID, "creature_spawn_probability");
	public static final ResourceLocation FOG_COLOR = new ResourceLocation(MODID, "fog_color");
	public static final ResourceLocation WATER_COLOR = new ResourceLocation(MODID, "water_color");
	public static final ResourceLocation WATER_FOG_COLOR = new ResourceLocation(MODID, "water_fog_color");
	public static final ResourceLocation SKY_COLOR = new ResourceLocation(MODID, "sky_color");

	private BiomeModifierNames() {
	}
}
