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

public class Precipitation extends AbstractBiomeModifier {
	public static final MapCodec<Precipitation> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(Precipitation::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Precipitation::priority),
					Codec.BOOL.fieldOf("has_precipitation").forGetter(Precipitation::precipitation)
			).apply(instance, Precipitation::new)
	);

	final boolean precipitation;

	protected Precipitation(HolderSet<Biome> biomes, int priority, boolean precipitation) {
		super(biomes, priority);
		this.precipitation = precipitation;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setHasPrecipitation(this.precipitation);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.PRECIPITATION;
	}

	public boolean precipitation() {
		return this.precipitation;
	}
}
