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

public class Aquifers extends AbstractNoiseGeneratorModifier {
	public static final MapCodec<Aquifers> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					NOISE_GENERATOR_LIST_CODEC.fieldOf("noise_settings").forGetter(Aquifers::noiseGenerators),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Aquifers::priority),
					Codec.BOOL.fieldOf("aquifers_enabled").forGetter(Aquifers::isAquifersEnabled)
			).apply(instance, Aquifers::new)
	);

	final boolean isAquifersEnabled;

	protected Aquifers(HolderSet<NoiseGeneratorSettings> noiseGenerators, int priority, boolean isAquifersEnabled) {
		super(noiseGenerators, priority);
		this.isAquifersEnabled = isAquifersEnabled;
	}

	@Override
	public void modifyParametersList(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) throws RuntimeException {
		list.setAquifersEnabled(this.isAquifersEnabled);
	}

	@Override
	public INoiseGeneratorModifierType type() {
		return NoiseGeneratorModifierTypes.AQUIFERS;
	}

	public boolean isAquifersEnabled() {
		return this.isAquifersEnabled;
	}
}
