package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

public class Downfall extends AbstractBiomeModifier {
	public static final MapCodec<Downfall> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(Downfall::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Downfall::priority),
					Codec.FLOAT.fieldOf("downfall").forGetter(Downfall::downfall)
			).apply(instance, Downfall::new)
	);

	final float downfall;

	protected Downfall(HolderSet<Biome> biomes, int priority, float downfall) {
		super(biomes, priority);
		this.downfall = downfall;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setDownfall(this.downfall);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.DOWNFALL;
	}

	public float downfall() {
		return this.downfall;
	}
}
