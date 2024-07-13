package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

public class Temperature extends AbstractBiomeModifier {
	public static final Codec<Temperature> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(Temperature::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Temperature::priority),
					Codec.FLOAT.fieldOf("temperature").forGetter(Temperature::temperature)
			).apply(instance, Temperature::new)
	);

	final float temperature;

	protected Temperature(HolderSet<Biome> biomes, int priority, float temperature) {
		super(biomes, priority);
		this.temperature = temperature;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setTemperature(this.temperature);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.TEMPERATURE;
	}

	public float temperature() {
		return this.temperature;
	}
}
