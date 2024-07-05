package com.hexagram2021.biome_modifier.api.modifiers.impl;

import com.hexagram2021.biome_modifier.api.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.Map;

public class AddSpawnCostsBiomeModifier extends AbstractBiomeModifier {
	public static final Codec<AddSpawnCostsBiomeModifier> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddSpawnCostsBiomeModifier::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(AddSpawnCostsBiomeModifier::priority),
					Codec.simpleMap(BuiltInRegistries.ENTITY_TYPE.byNameCodec(), MobSpawnSettings.MobSpawnCost.CODEC, BuiltInRegistries.ENTITY_TYPE)
							.fieldOf("spawn_costs").forGetter(AddSpawnCostsBiomeModifier::spawnerCosts)
			).apply(instance, AddSpawnCostsBiomeModifier::new)
	);

	final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> spawnerCosts;

	protected AddSpawnCostsBiomeModifier(HolderSet<Biome> biomes, int priority, Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> spawnerCosts) {
		super(biomes, priority);
		this.spawnerCosts = spawnerCosts;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) {
		list.addSpawnerCosts(this.spawnerCosts);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.ADD_SPAWN_COSTS;
	}

	public Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> spawnerCosts() {
		return this.spawnerCosts;
	}
}
