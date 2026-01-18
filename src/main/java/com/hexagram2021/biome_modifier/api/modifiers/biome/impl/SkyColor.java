package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.ColorType;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Function;

public class SkyColor extends AbstractBiomeModifier {
	public static final MapCodec<SkyColor> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(SkyColor::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(SkyColor::priority),
					Codec.either(Codec.INT, ColorType.CODEC.codec()).fieldOf("color").xmap(
							either -> either.map(Function.identity(), ColorType::get), Either::left
					).forGetter(SkyColor::color)
			).apply(instance, SkyColor::new)
	);

	final int color;

	protected SkyColor(HolderSet<Biome> biomes, int priority, int color) {
		super(biomes, priority);
		this.color = color;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setSkyColor(this.color);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.SKY_COLOR;
	}

	public int color() {
		return this.color;
	}
}
