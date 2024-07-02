package com.hexagram2021.biome_modifier.api;

import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.hexagram2021.biome_modifier.api.modifiers.impl.*;
import net.minecraft.resources.ResourceLocation;

public final class BiomeModifierTypes {
	public static final IBiomeModifierType NONE = register(BiomeModifierNames.NONE, () -> NoneBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_FEATURES = register(BiomeModifierNames.ADD_FEATURES, () -> AddFeaturesBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_FEATURES = register(BiomeModifierNames.REMOVE_FEATURES, () -> RemoveFeaturesBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_SPAWNS = register(BiomeModifierNames.ADD_SPAWNS, () -> AddSpawnsBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_SPAWNS = register(BiomeModifierNames.REMOVE_SPAWNS, () -> RemoveSpawnsBiomeModifier.CODEC);
	//public static final IBiomeModifierType ADD_SPAWN_COSTS = register(BiomeModifierNames.ADD_SPAWN_COSTS, () -> AddSpawnCostsBiomeModifier.CODEC);
	//public static final IBiomeModifierType REMOVE_SPAWN_COSTS = register(BiomeModifierNames.REMOVE_SPAWN_COSTS, () -> RemoveSpawnCostsBiomeModifier.CODEC);

	private BiomeModifierTypes() {
	}

	public static IBiomeModifierType register(ResourceLocation id, IBiomeModifierType type) {
		IBiomeModifierType.register(id, type);
		return type;
	}
}
