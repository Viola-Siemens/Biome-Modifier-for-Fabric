package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class BedWorks extends AbstractDimensionModifier {
	public static final Codec<BedWorks> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(BedWorks::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(BedWorks::priority),
					Codec.BOOL.fieldOf("bed_works").forGetter(BedWorks::bedWorks)
			).apply(instance, BedWorks::new)
	);

	final boolean bedWorks;

	public BedWorks(HolderSet<DimensionType> dimensionTypes, int priority, boolean bedWorks) {
		super(dimensionTypes, priority);
		this.bedWorks = bedWorks;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setBedWorks(this.bedWorks);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.BED_WORKS;
	}

	public boolean bedWorks() {
		return this.bedWorks;
	}
}
