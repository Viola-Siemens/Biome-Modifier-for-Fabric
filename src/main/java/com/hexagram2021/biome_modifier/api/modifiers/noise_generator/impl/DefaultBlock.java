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

public class DefaultBlock extends AbstractNoiseGeneratorModifier {
	public static final MapCodec<DefaultBlock> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					NOISE_GENERATOR_LIST_CODEC.fieldOf("noise_settings").forGetter(DefaultBlock::noiseGenerators),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(DefaultBlock::priority),
					BlockState.CODEC.fieldOf("default_block").forGetter(DefaultBlock::defaultBlock)
			).apply(instance, DefaultBlock::new)
	);

	final BlockState defaultBlock;

	protected DefaultBlock(HolderSet<NoiseGeneratorSettings> noiseGenerators, int priority, BlockState defaultBlock) {
		super(noiseGenerators, priority);
		this.defaultBlock = defaultBlock;
	}

	@Override
	public void modifyParametersList(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list) throws RuntimeException {
		list.setDefaultBlock(this.defaultBlock);
	}

	@Override
	public INoiseGeneratorModifierType type() {
		return NoiseGeneratorModifierTypes.DEFAULT_BLOCK;
	}

	public BlockState defaultBlock() {
		return this.defaultBlock;
	}
}
