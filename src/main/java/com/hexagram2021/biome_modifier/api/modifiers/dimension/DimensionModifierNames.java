package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public final class DimensionModifierNames {
	public static final ResourceLocation NONE = new ResourceLocation(MODID, "none");
	public static final ResourceLocation FIXED_TIME = new ResourceLocation(MODID, "fixed_time");
	public static final ResourceLocation SKY_LIGHT = new ResourceLocation(MODID, "sky_light");
	public static final ResourceLocation CEILING = new ResourceLocation(MODID, "ceiling");
	public static final ResourceLocation ULTRA_WARM = new ResourceLocation(MODID, "ultra_warm");
	public static final ResourceLocation NATURAL = new ResourceLocation(MODID, "natural");
	public static final ResourceLocation COORDINATE_SCALE = new ResourceLocation(MODID, "coordinate_scale");
	public static final ResourceLocation BED_WORKS = new ResourceLocation(MODID, "bed_works");
	public static final ResourceLocation RESPAWN_ANCHOR_WORKS = new ResourceLocation(MODID, "respawn_anchor_works");
	public static final ResourceLocation MIN_Y = new ResourceLocation(MODID, "min_y");
	public static final ResourceLocation HEIGHT = new ResourceLocation(MODID, "height");
	public static final ResourceLocation LOGICAL_HEIGHT = new ResourceLocation(MODID, "logical_height");
	public static final ResourceLocation INFINIBURN = new ResourceLocation(MODID, "infiniburn");
	public static final ResourceLocation EFFECTS = new ResourceLocation(MODID, "effects");
	public static final ResourceLocation AMBIENT_LIGHT = new ResourceLocation(MODID, "ambient_light");
	public static final ResourceLocation PIGLIN_SAFE = new ResourceLocation(MODID, "piglin_safe");
	public static final ResourceLocation RAIDS = new ResourceLocation(MODID, "raids");
	public static final ResourceLocation MONSTER_SPAWN_LIGHT = new ResourceLocation(MODID, "monster_spawn_light");
	public static final ResourceLocation MONSTER_SPAWN_BLOCK_LIGHT_LIMIT = new ResourceLocation(MODID, "monster_spawn_block_light_limit");

	private DimensionModifierNames() {
	}
}
