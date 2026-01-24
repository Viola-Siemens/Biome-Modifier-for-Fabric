package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public final class DimensionModifierNames {
	public static final ResourceLocation NONE = ResourceLocation.fromNamespaceAndPath(MODID, "none");
	public static final ResourceLocation FIXED_TIME = ResourceLocation.fromNamespaceAndPath(MODID, "fixed_time");
	public static final ResourceLocation SKY_LIGHT = ResourceLocation.fromNamespaceAndPath(MODID, "sky_light");
	public static final ResourceLocation CEILING = ResourceLocation.fromNamespaceAndPath(MODID, "ceiling");
	public static final ResourceLocation ULTRA_WARM = ResourceLocation.fromNamespaceAndPath(MODID, "ultra_warm");
	public static final ResourceLocation NATURAL = ResourceLocation.fromNamespaceAndPath(MODID, "natural");
	public static final ResourceLocation COORDINATE_SCALE = ResourceLocation.fromNamespaceAndPath(MODID, "coordinate_scale");
	public static final ResourceLocation BED_WORKS = ResourceLocation.fromNamespaceAndPath(MODID, "bed_works");
	public static final ResourceLocation RESPAWN_ANCHOR_WORKS = ResourceLocation.fromNamespaceAndPath(MODID, "respawn_anchor_works");
	public static final ResourceLocation MIN_Y = ResourceLocation.fromNamespaceAndPath(MODID, "min_y");
	public static final ResourceLocation HEIGHT = ResourceLocation.fromNamespaceAndPath(MODID, "height");
	public static final ResourceLocation LOGICAL_HEIGHT = ResourceLocation.fromNamespaceAndPath(MODID, "logical_height");
	public static final ResourceLocation INFINIBURN = ResourceLocation.fromNamespaceAndPath(MODID, "infiniburn");
	public static final ResourceLocation EFFECTS = ResourceLocation.fromNamespaceAndPath(MODID, "effects");
	public static final ResourceLocation AMBIENT_LIGHT = ResourceLocation.fromNamespaceAndPath(MODID, "ambient_light");
	public static final ResourceLocation PIGLIN_SAFE = ResourceLocation.fromNamespaceAndPath(MODID, "piglin_safe");
	public static final ResourceLocation RAIDS = ResourceLocation.fromNamespaceAndPath(MODID, "raids");
	public static final ResourceLocation MONSTER_SPAWN_LIGHT = ResourceLocation.fromNamespaceAndPath(MODID, "monster_spawn_light");
	public static final ResourceLocation MONSTER_SPAWN_BLOCK_LIGHT_LIMIT = ResourceLocation.fromNamespaceAndPath(MODID, "monster_spawn_block_light_limit");

	private DimensionModifierNames() {
	}
}
