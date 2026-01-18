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

public class Ceiling extends AbstractDimensionModifier {
	public static final MapCodec<Ceiling> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(Ceiling::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Ceiling::priority),
					Codec.BOOL.fieldOf("has_ceiling").forGetter(Ceiling::ceiling)
			).apply(instance, Ceiling::new)
	);

	final boolean ceiling;

	public Ceiling(HolderSet<DimensionType> dimensionTypes, int priority, boolean ceiling) {
		super(dimensionTypes, priority);
		this.ceiling = ceiling;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setHasCeiling(this.ceiling);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.CEILING;
	}

	public boolean ceiling() {
		return this.ceiling;
	}
}
