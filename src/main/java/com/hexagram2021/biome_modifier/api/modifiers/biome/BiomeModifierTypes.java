package com.hexagram2021.biome_modifier.api.modifiers.biome;

import com.hexagram2021.biome_modifier.api.modifiers.biome.impl.*;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.resources.ResourceLocation;

public final class BiomeModifierTypes {
	public static final IBiomeModifierType NONE = register(BiomeModifierNames.NONE, () -> NoneBiomeModifier.CODEC);
	public static final IBiomeModifierType ADD_FEATURES = register(BiomeModifierNames.ADD_FEATURES, () -> AddFeatures.CODEC);
	public static final IBiomeModifierType REMOVE_FEATURES = register(BiomeModifierNames.REMOVE_FEATURES, () -> RemoveFeatures.CODEC);
	public static final IBiomeModifierType ADD_SPAWNS = register(BiomeModifierNames.ADD_SPAWNS, () -> AddSpawns.CODEC);
	public static final IBiomeModifierType REMOVE_SPAWNS = register(BiomeModifierNames.REMOVE_SPAWNS, () -> RemoveSpawns.CODEC);
	public static final IBiomeModifierType ADD_SPAWN_COSTS = register(BiomeModifierNames.ADD_SPAWN_COSTS, () -> AddSpawnCosts.CODEC);
	public static final IBiomeModifierType REMOVE_SPAWN_COSTS = register(BiomeModifierNames.REMOVE_SPAWN_COSTS, () -> RemoveSpawnCosts.CODEC);
	public static final IBiomeModifierType ADD_CARVERS = register(BiomeModifierNames.ADD_CARVERS, () -> AddCarversBiomeModifier.CODEC);
	public static final IBiomeModifierType REMOVE_CARVERS = register(BiomeModifierNames.REMOVE_CARVERS, () -> RemoveCarvers.CODEC);
	public static final IBiomeModifierType PRECIPITATION = register(BiomeModifierNames.PRECIPITATION, () -> Precipitation.CODEC);
	public static final IBiomeModifierType TEMPERATURE = register(BiomeModifierNames.TEMPERATURE, () -> Temperature.CODEC);
	public static final IBiomeModifierType DOWNFALL = register(BiomeModifierNames.DOWNFALL, () -> Downfall.CODEC);
	public static final IBiomeModifierType TEMPERATURE_ADJUSTMENT = register(BiomeModifierNames.TEMPERATURE_ADJUSTMENT, () -> TemperatureAdjustment.CODEC);
	public static final IBiomeModifierType CREATURE_SPAWN_PROBABILITY = register(BiomeModifierNames.CREATURE_SPAWN_PROBABILITY, () -> CreatureGenerationProbability.CODEC);
	public static final IBiomeModifierType FOG_COLOR = register(BiomeModifierNames.FOG_COLOR, () -> FogColor.CODEC);
	public static final IBiomeModifierType WATER_COLOR = register(BiomeModifierNames.WATER_COLOR, () -> WaterColor.CODEC);
	public static final IBiomeModifierType WATER_FOG_COLOR = register(BiomeModifierNames.WATER_FOG_COLOR, () -> WaterFogColor.CODEC);
	public static final IBiomeModifierType SKY_COLOR = register(BiomeModifierNames.SKY_COLOR, () -> SkyColor.CODEC);

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
