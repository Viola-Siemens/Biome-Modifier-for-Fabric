package com.hexagram2021.biome_modifier.api.modifiers.noise_generator.impl;

import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.AbstractNoiseGeneratorModifier;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifierType;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.NoiseGeneratorModifierTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class SeaLevel extends AbstractNoiseGeneratorModifier {
	public static final MapCodec<SeaLevel> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					NOISE_GENERATOR_LIST_CODEC.fieldOf("noise_settings").forGetter(SeaLevel::noiseGenerators),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(SeaLevel::priority),
					Codec.INT.fieldOf("sea_level").forGetter(SeaLevel::seaLevel)
			).apply(instance, SeaLevel::new)
	);

	final int seaLevel;

	protected SeaLevel(HolderSet<NoiseGeneratorSettings> noiseGenerators, int priority, int seaLevel) {
		super(noiseGenerators, priority);
		this.seaLevel = seaLevel;
	}

	@Override
	public void modifyParametersList(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) throws RuntimeException {
		list.setSeaLevel(this.seaLevel);
	}

	@Override
	public INoiseGeneratorModifierType type() {
		return NoiseGeneratorModifierTypes.SEA_LEVEL;
	}

	public int seaLevel() {
		return this.seaLevel;
	}
}
