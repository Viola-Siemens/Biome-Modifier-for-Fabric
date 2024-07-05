package com.hexagram2021.biome_modifier.api;

import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.hexagram2021.biome_modifier.api.modifiers.impl.*;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.resources.ResourceLocation;

public final class BiomeModifierTypes {
	public static final IBiomeModifierType NONE = register(BiomeModifierNames.NONE, () -> NoneBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_FEATURES = register(BiomeModifierNames.ADD_FEATURES, () -> AddFeaturesBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_FEATURES = register(BiomeModifierNames.REMOVE_FEATURES, () -> RemoveFeaturesBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_SPAWNS = register(BiomeModifierNames.ADD_SPAWNS, () -> AddSpawnsBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_SPAWNS = register(BiomeModifierNames.REMOVE_SPAWNS, () -> RemoveSpawnsBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_SPAWN_COSTS = register(BiomeModifierNames.ADD_SPAWN_COSTS, () -> AddSpawnCostsBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_SPAWN_COSTS = register(BiomeModifierNames.REMOVE_SPAWN_COSTS, () -> RemoveSpawnCostsBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_CARVERS = register(BiomeModifierNames.ADD_CARVERS, () -> AddCarversBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_CARVERS = register(BiomeModifierNames.REMOVE_CARVERS, () -> RemoveCarversBiomeModifier.CODEC);

	private BiomeModifierTypes() {
	}

	public static IBiomeModifierType register(ResourceLocation id, IBiomeModifierType type) {
		IBiomeModifierType.register(id, type);
		return type;
	}

	public static void init() {
		BMLogger.info("Loaded %d biome modifier types.".formatted(IBiomeModifierType.BIOME_MODIFIER_TYPES.size()));
	}
}
