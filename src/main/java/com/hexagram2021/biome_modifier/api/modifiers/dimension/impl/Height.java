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

public class Height extends AbstractDimensionModifier {
	public static final MapCodec<Height> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(Height::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Height::priority),
					Codec.intRange(DimensionType.MIN_HEIGHT, DimensionType.Y_SIZE).fieldOf("height").forGetter(Height::height)
			).apply(instance, Height::new)
	);

	final int height;

	public Height(HolderSet<DimensionType> dimensionTypes, int priority, int height) {
		super(dimensionTypes, priority);
		this.height = height;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setHeight(this.height);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.HEIGHT;
	}

	public int height() {
		return this.height;
	}
}
