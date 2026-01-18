package com.hexagram2021.biome_modifier.api.modifiers.noise_generator.impl;

import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifier;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifierType;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.NoiseGeneratorModifierTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class NoneNoiseGeneratorModifier implements INoiseGeneratorModifier {
	public static final MapCodec<NoneNoiseGeneratorModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.point(new NoneNoiseGeneratorModifier()));

	protected NoneNoiseGeneratorModifier() {
	}

	@Override
	public boolean canModify(Holder<NoiseGeneratorSettings> biome) {
		return false;
	}
	@Override
	public void modify(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) {
		// Do nothing
	}

	@Override
	public INoiseGeneratorModifierType type() {
		return NoiseGeneratorModifierTypes.NONE;
	}
}
