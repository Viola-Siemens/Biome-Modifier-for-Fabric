package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import com.hexagram2021.biome_modifier.api.modifiers.dimension.impl.*;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.resources.ResourceLocation;

public final class DimensionModifierTypes {
	public static final IDimensionModifierType NONE = register(DimensionModifierNames.NONE, () -> NoneDimensionModifier.CODEC);
	public static final IDimensionModifierType FIXED_TIME = register(DimensionModifierNames.FIXED_TIME, () -> FixedTime.CODEC);
	public static final IDimensionModifierType SKY_LIGHT = register(DimensionModifierNames.SKY_LIGHT, () -> SkyLight.CODEC);
	public static final IDimensionModifierType CEILING = register(DimensionModifierNames.CEILING, () -> Ceiling.CODEC);
	public static final IDimensionModifierType ULTRA_WARM = register(DimensionModifierNames.ULTRA_WARM, () -> UltraWarm.CODEC);
	public static final IDimensionModifierType NATURAL = register(DimensionModifierNames.NATURAL, () -> Natural.CODEC);
	public static final IDimensionModifierType COORDINATE_SCALE = register(DimensionModifierNames.COORDINATE_SCALE, () -> CoordinateScale.CODEC);
	public static final IDimensionModifierType BED_WORKS = register(DimensionModifierNames.BED_WORKS, () -> BedWorks.CODEC);
	public static final IDimensionModifierType RESPAWN_ANCHOR_WORKS = register(DimensionModifierNames.RESPAWN_ANCHOR_WORKS, () -> RespawnAnchorWorks.CODEC);
	public static final IDimensionModifierType MIN_Y = register(DimensionModifierNames.MIN_Y, () -> MinY.CODEC);
	public static final IDimensionModifierType HEIGHT = register(DimensionModifierNames.HEIGHT, () -> Height.CODEC);
	public static final IDimensionModifierType LOGICAL_HEIGHT = register(DimensionModifierNames.LOGICAL_HEIGHT, () -> LogicalHeight.CODEC);
	public static final IDimensionModifierType INFINIBURN = register(DimensionModifierNames.INFINIBURN, () -> InfiniBurn.CODEC);
	public static final IDimensionModifierType EFFECTS = register(DimensionModifierNames.EFFECTS, () -> Effects.CODEC);
	public static final IDimensionModifierType AMBIENT_LIGHT = register(DimensionModifierNames.AMBIENT_LIGHT, () -> AmbientLight.CODEC);
	public static final IDimensionModifierType PIGLIN_SAFE = register(DimensionModifierNames.PIGLIN_SAFE, () -> PiglinSafe.CODEC);
	public static final IDimensionModifierType RAIDS = register(DimensionModifierNames.RAIDS, () -> Raids.CODEC);
	public static final IDimensionModifierType MONSTER_SPAWN_LIGHT = register(DimensionModifierNames.MONSTER_SPAWN_LIGHT, () -> MonsterSpawnLight.CODEC);
	public static final IDimensionModifierType MONSTER_SPAWN_BLOCK_LIGHT_LIMIT = register(DimensionModifierNames.MONSTER_SPAWN_BLOCK_LIGHT_LIMIT, () -> MonsterSpawnBlockLightLimit.CODEC);

	private DimensionModifierTypes() {
	}

	public static IDimensionModifierType register(ResourceLocation id, IDimensionModifierType type) {
		IDimensionModifierType.register(id, type);
		return type;
	}

	public static void init() {
		BMLogger.info("Loaded %d dimension modifier types.".formatted(IDimensionModifierType.DIMENSION_MODIFIER_TYPES.size()));
	}
}
