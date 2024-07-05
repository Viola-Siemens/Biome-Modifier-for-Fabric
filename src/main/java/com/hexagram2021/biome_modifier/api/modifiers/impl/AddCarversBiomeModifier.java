package com.hexagram2021.biome_modifier.api.modifiers.impl;

import com.hexagram2021.biome_modifier.api.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;

public class AddCarversBiomeModifier extends AbstractBiomeModifier {
	public static final Codec<AddCarversBiomeModifier> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddCarversBiomeModifier::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(AddCarversBiomeModifier::priority),
					ConfiguredWorldCarver.LIST_CODEC.fieldOf("carvers").forGetter(AddCarversBiomeModifier::carvers),
					GenerationStep.Carving.CODEC.fieldOf("step").forGetter(AddCarversBiomeModifier::step)
			).apply(instance, AddCarversBiomeModifier::new)
	);

	final HolderSet<ConfiguredWorldCarver<?>> carvers;
	final GenerationStep.Carving step;

	protected AddCarversBiomeModifier(HolderSet<Biome> biomes, int priority, HolderSet<ConfiguredWorldCarver<?>> carvers, GenerationStep.Carving step) {
		super(biomes, priority);
		this.carvers = carvers;
		this.step = step;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) {
		list.addCarvers(this.step, this.carvers.stream().toList());
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.ADD_CARVERS;
	}

	public HolderSet<ConfiguredWorldCarver<?>> carvers() {
		return this.carvers;
	}

	public GenerationStep.Carving step() {
		return this.step;
	}
}
