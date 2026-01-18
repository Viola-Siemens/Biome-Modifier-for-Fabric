package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class CoordinateScale extends AbstractDimensionModifier {
	public static final MapCodec<CoordinateScale> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(CoordinateScale::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(CoordinateScale::priority),
					Codec.doubleRange(1.0E-5, 3.0E+7).fieldOf("coordinate_scale").forGetter(CoordinateScale::coordinateScale)
			).apply(instance, CoordinateScale::new)
	);

	final double coordinateScale;

	public CoordinateScale(HolderSet<DimensionType> dimensionTypes, int priority, double coordinateScale) {
		super(dimensionTypes, priority);
		this.coordinateScale = coordinateScale;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setCoordinateScale(this.coordinateScale);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.COORDINATE_SCALE;
	}

	public double coordinateScale() {
		return this.coordinateScale;
	}
}
