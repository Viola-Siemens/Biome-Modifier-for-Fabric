package com.hexagram2021.biome_modifier.api;

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

	private BiomeModifierNames() {
	}
}
