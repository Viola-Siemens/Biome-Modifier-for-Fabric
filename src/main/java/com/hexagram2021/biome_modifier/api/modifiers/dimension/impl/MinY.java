package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class MinY extends AbstractDimensionModifier {
	public static final Codec<MinY> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(MinY::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(MinY::priority),
					Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("min_y").forGetter(MinY::minY)
			).apply(instance, MinY::new)
	);

	final int minY;

	public MinY(HolderSet<DimensionType> dimensionTypes, int priority, int minY) {
		super(dimensionTypes, priority);
		this.minY = minY;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setMinY(this.minY);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.MIN_Y;
	}

	public int minY() {
		return this.minY;
	}
}
