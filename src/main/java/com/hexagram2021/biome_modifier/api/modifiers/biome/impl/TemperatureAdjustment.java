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

public class TemperatureAdjustment extends AbstractBiomeModifier {
	public static final MapCodec<TemperatureAdjustment> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(TemperatureAdjustment::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(TemperatureAdjustment::priority),
					Biome.TemperatureModifier.CODEC.fieldOf("temperature_modifier").forGetter(TemperatureAdjustment::temperatureModifier)
			).apply(instance, TemperatureAdjustment::new)
	);

	final Biome.TemperatureModifier temperatureModifier;

	protected TemperatureAdjustment(HolderSet<Biome> biomes, int priority, Biome.TemperatureModifier temperatureModifier) {
		super(biomes, priority);
		this.temperatureModifier = temperatureModifier;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setTemperatureModifier(this.temperatureModifier);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.TEMPERATURE_ADJUSTMENT;
	}

	public Biome.TemperatureModifier temperatureModifier() {
		return this.temperatureModifier;
	}
}
