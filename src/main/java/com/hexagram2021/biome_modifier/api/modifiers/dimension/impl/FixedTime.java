package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class FixedTime extends AbstractDimensionModifier {
	public static final MapCodec<FixedTime> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(FixedTime::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(FixedTime::priority),
					ExtraCodecs.asOptionalLong(Codec.LONG.optionalFieldOf("fixed_time")).forGetter(FixedTime::fixedTime)
			).apply(instance, FixedTime::new)
	);

	final OptionalLong fixedTime;

	public FixedTime(HolderSet<DimensionType> dimensionTypes, int priority, OptionalLong fixedTime) {
		super(dimensionTypes, priority);
		this.fixedTime = fixedTime;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setFixedTime(this.fixedTime);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.FIXED_TIME;
	}

	public OptionalLong fixedTime() {
		return this.fixedTime;
	}
}
