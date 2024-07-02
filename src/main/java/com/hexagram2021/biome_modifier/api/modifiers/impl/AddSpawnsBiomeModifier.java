package com.hexagram2021.biome_modifier.api.modifiers.impl;

import com.hexagram2021.biome_modifier.api.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class AddSpawnsBiomeModifier extends AbstractBiomeModifier {
	public static final Codec<AddSpawnsBiomeModifier> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(AddSpawnsBiomeModifier::biomes),
					Codec.INT.fieldOf("priority").forGetter(AddSpawnsBiomeModifier::priority),
					Codec.list(MobSpawnSettings.SpawnerData.CODEC).fieldOf("spawners").forGetter(AddSpawnsBiomeModifier::spawners)
			).apply(instance, AddSpawnsBiomeModifier::new)
	);

	final List<MobSpawnSettings.SpawnerData> spawners;

	protected AddSpawnsBiomeModifier(HolderSet<Biome> biomes, int priority, List<MobSpawnSettings.SpawnerData> spawners) {
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
