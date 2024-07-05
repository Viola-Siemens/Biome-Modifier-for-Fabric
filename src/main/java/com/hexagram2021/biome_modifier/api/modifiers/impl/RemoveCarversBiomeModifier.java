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

public class RemoveCarversBiomeModifier extends AbstractBiomeModifier {
	public static final Codec<RemoveCarversBiomeModifier> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(RemoveCarversBiomeModifier::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(RemoveCarversBiomeModifier::priority),
					ConfiguredWorldCarver.LIST_CODEC.fieldOf("carvers").forGetter(RemoveCarversBiomeModifier::carvers),
					GenerationStep.Carving.CODEC.fieldOf("step").forGetter(RemoveCarversBiomeModifier::step)
			).apply(instance, RemoveCarversBiomeModifier::new)
	);

	final HolderSet<ConfiguredWorldCarver<?>> carvers;
	final GenerationStep.Carving step;

	protected RemoveCarversBiomeModifier(HolderSet<Biome> biomes, int priority, HolderSet<ConfiguredWorldCarver<?>> carvers, GenerationStep.Carving step) {
		super(biomes, priority);
		this.carvers = carvers;
		this.step = step;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) {
		list.removeCarvers(this.step, this.carvers.stream().toList());
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.REMOVE_CARVERS;
	}

	public HolderSet<ConfiguredWorldCarver<?>> carvers() {
		return this.carvers;
	}

	public GenerationStep.Carving step() {
		return this.step;
	}
}
