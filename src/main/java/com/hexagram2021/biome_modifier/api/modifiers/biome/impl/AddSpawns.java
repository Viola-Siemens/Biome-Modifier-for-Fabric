package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.List;

public class AddSpawns extends AbstractBiomeModifier {
	public static final Codec<AddSpawns> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddSpawns::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(AddSpawns::priority),
					Codec.list(MobSpawnSettings.SpawnerData.CODEC).fieldOf("spawners").forGetter(AddSpawns::spawners)
			).apply(instance, AddSpawns::new)
	);

	final List<MobSpawnSettings.SpawnerData> spawners;

	protected AddSpawns(HolderSet<Biome> biomes, int priority, List<MobSpawnSettings.SpawnerData> spawners) {
		super(biomes, priority);
		this.spawners = spawners;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) {
		list.addSpawners(this.spawners);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.ADD_SPAWNS;
	}

	public List<MobSpawnSettings.SpawnerData> spawners() {
		return this.spawners;
	}
}
