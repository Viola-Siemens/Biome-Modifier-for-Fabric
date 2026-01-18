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

public class Natural extends AbstractDimensionModifier {
	public static final MapCodec<Natural> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(Natural::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Natural::priority),
					Codec.BOOL.fieldOf("natural").forGetter(Natural::natural)
			).apply(instance, Natural::new)
	);

	final boolean natural;

	public Natural(HolderSet<DimensionType> dimensionTypes, int priority, boolean natural) {
		super(dimensionTypes, priority);
		this.natural = natural;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setNatural(this.natural);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.NATURAL;
	}

	public boolean natural() {
		return this.natural;
	}
}
