package com.hexagram2021.biome_modifier.api.modifiers.noise_generator;

import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public abstract class AbstractNoiseGeneratorModifier implements INoiseGeneratorModifier {
	public static final Codec<HolderSet<NoiseGeneratorSettings>> NOISE_GENERATOR_LIST_CODEC = RegistryCodecs.homogeneousList(Registries.NOISE_SETTINGS, NoiseGeneratorSettings.DIRECT_CODEC);

	final HolderSet<NoiseGeneratorSettings> noiseGenerators;
	final int priority;

	protected AbstractNoiseGeneratorModifier(HolderSet<NoiseGeneratorSettings> noiseGenerators, int priority) {
		this.noiseGenerators = noiseGenerators;
		this.priority = priority;
	}

	@Override
	public void modify(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) {
		try {
			this.modifyParametersList(list);
		} catch(RuntimeException e) {
			list.error(e);
		}
	}

	@Override
	public boolean canModify(Holder<NoiseGeneratorSettings> noiseGenerator) {
		return this.noiseGenerators.contains(noiseGenerator);
	}
	public abstract void modifyParametersList(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) throws RuntimeException;

	public HolderSet<NoiseGeneratorSettings> noiseGenerators() {
		return this.noiseGenerators;
	}
	public int priority() {
		return this.priority;
	}
}
