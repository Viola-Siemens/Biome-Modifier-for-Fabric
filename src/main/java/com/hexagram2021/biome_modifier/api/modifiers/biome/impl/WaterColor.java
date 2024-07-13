package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.ColorType;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Function;

public class WaterColor extends AbstractBiomeModifier {
	public static final Codec<WaterColor> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColor::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(WaterColor::priority),
					Codec.either(Codec.INT, ColorType.CODEC).fieldOf("color").xmap(
							either -> either.map(Function.identity(), ColorType::get), Either::left
					).forGetter(WaterColor::color)
			).apply(instance, WaterColor::new)
	);

	final int color;

	protected WaterColor(HolderSet<Biome> biomes, int priority, int color) {
		super(biomes, priority);
		this.color = color;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setWaterColor(this.color);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.WATER_COLOR;
	}

	public int color() {
		return this.color;
	}
}
