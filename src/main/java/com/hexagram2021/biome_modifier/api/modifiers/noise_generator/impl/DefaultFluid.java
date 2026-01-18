package com.hexagram2021.biome_modifier.api.modifiers.noise_generator.impl;

import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.AbstractNoiseGeneratorModifier;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifierType;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.NoiseGeneratorModifierTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class DefaultFluid extends AbstractNoiseGeneratorModifier {
	public static final MapCodec<DefaultFluid> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					NOISE_GENERATOR_LIST_CODEC.fieldOf("noise_settings").forGetter(DefaultFluid::noiseGenerators),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(DefaultFluid::priority),
					BlockState.CODEC.fieldOf("default_fluid").forGetter(DefaultFluid::defaultFluid)
			).apply(instance, DefaultFluid::new)
	);

	final BlockState defaultFluid;

	protected DefaultFluid(HolderSet<NoiseGeneratorSettings> noiseGenerators, int priority, BlockState defaultFluid) {
		super(noiseGenerators, priority);
		this.defaultFluid = defaultFluid;
	}

	@Override
	public void modifyParametersList(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) throws RuntimeException {
		list.setDefaultFluid(this.defaultFluid);
	}

	@Override
	public INoiseGeneratorModifierType type() {
		return NoiseGeneratorModifierTypes.DEFAULT_FLUID;
	}

	public BlockState defaultFluid() {
		return this.defaultFluid;
	}
}
