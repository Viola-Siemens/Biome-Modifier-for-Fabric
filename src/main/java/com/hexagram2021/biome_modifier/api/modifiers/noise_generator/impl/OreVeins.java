package com.hexagram2021.biome_modifier.api.modifiers.noise_generator.impl;

import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.AbstractNoiseGeneratorModifier;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifierType;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.NoiseGeneratorModifierTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class OreVeins extends AbstractNoiseGeneratorModifier {
	public static final Codec<OreVeins> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					NOISE_GENERATOR_LIST_CODEC.fieldOf("noise_settings").forGetter(OreVeins::noiseGenerators),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(OreVeins::priority),
					Codec.BOOL.fieldOf("ore_veins_enabled").forGetter(OreVeins::oreVeinsEnabled)
			).apply(instance, OreVeins::new)
	);

	final boolean oreVeinsEnabled;

	protected OreVeins(HolderSet<NoiseGeneratorSettings> noiseGenerators, int priority, boolean oreVeinsEnabled) {
		super(noiseGenerators, priority);
		this.oreVeinsEnabled = oreVeinsEnabled;
	}

	@Override
	public void modifyParametersList(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) throws RuntimeException {
		list.setOreVeinsEnabled(this.oreVeinsEnabled);
	}

	@Override
	public INoiseGeneratorModifierType type() {
		return NoiseGeneratorModifierTypes.ORE_VEINS;
	}

	public boolean oreVeinsEnabled() {
		return this.oreVeinsEnabled;
	}
}
