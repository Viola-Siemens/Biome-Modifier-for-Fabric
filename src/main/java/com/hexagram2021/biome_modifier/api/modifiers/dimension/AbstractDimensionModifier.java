package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public abstract class AbstractDimensionModifier implements IDimensionModifier {
	final HolderSet<DimensionType> dimensionTypes;
	final int priority;

	protected AbstractDimensionModifier(HolderSet<DimensionType> dimensionTypes, int priority) {
		this.dimensionTypes = dimensionTypes;
		this.priority = priority;
	}

	@Override
	public void modify(IModifiableDimension.DimensionModificationParametersList list) {
		try {
			this.modifyParametersList(list);
		} catch(RuntimeException e) {
			list.error(e);
		}
	}

	@Override
	public boolean canModify(Holder<DimensionType> dimensionTypes) {
		return this.dimensionTypes.contains(dimensionTypes);
	}
	public abstract void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException;

	public HolderSet<DimensionType> dimensionTypes() {
		return this.dimensionTypes;
	}
	public int priority() {
		return this.priority;
	}
}
