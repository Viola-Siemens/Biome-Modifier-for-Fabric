package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AddFeatures extends AbstractBiomeModifier {
	public static final Codec<AddFeatures> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddFeatures::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(AddFeatures::priority),
					PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeatures::features),
					GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeatures::step)
			).apply(instance, AddFeatures::new)
	);

	final HolderSet<PlacedFeature> features;
	final GenerationStep.Decoration step;

	protected AddFeatures(HolderSet<Biome> biomes, int priority, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) {
		super(biomes, priority);
		this.features = features;
		this.step = step;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) {
		list.addFeatures(this.step, this.features.stream().toList());
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.ADD_FEATURES;
	}

	public HolderSet<PlacedFeature> features() {
		return this.features;
	}

	public GenerationStep.Decoration step() {
		return this.step;
	}
}
