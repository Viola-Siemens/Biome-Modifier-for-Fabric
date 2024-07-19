package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class LogicalHeight extends AbstractDimensionModifier {
	public static final Codec<LogicalHeight> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(LogicalHeight::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(LogicalHeight::priority),
					Codec.intRange(0, DimensionType.Y_SIZE).fieldOf("logical_height").forGetter(LogicalHeight::logicalHeight)
			).apply(instance, LogicalHeight::new)
	);

	final int logicalHeight;

	public LogicalHeight(HolderSet<DimensionType> dimensionTypes, int priority, int logicalHeight) {
		super(dimensionTypes, priority);
		this.logicalHeight = logicalHeight;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setLogicalHeight(this.logicalHeight);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.LOGICAL_HEIGHT;
	}

	public int logicalHeight() {
		return this.logicalHeight;
	}
}
