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
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AddFeaturesBiomeModifier extends AbstractBiomeModifier {
	public static final Codec<AddFeaturesBiomeModifier> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(AddFeaturesBiomeModifier::biomes),
					Codec.INT.fieldOf("priority").forGetter(AddFeaturesBiomeModifier::priority),
					PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(AddFeaturesBiomeModifier::features),
					GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(AddFeaturesBiomeModifier::step)
			).apply(instance, AddFeaturesBiomeModifier::new)
	);

	final HolderSet<PlacedFeature> features;
	final GenerationStep.Decoration step;

	protected AddFeaturesBiomeModifier(HolderSet<Biome> biomes, int priority, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) {
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
