package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class PiglinSafe extends AbstractDimensionModifier {
	public static final Codec<PiglinSafe> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(PiglinSafe::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(PiglinSafe::priority),
					Codec.BOOL.fieldOf("piglin_safe").forGetter(PiglinSafe::piglinSafe)
			).apply(instance, PiglinSafe::new)
	);

	final boolean piglinSafe;

	public PiglinSafe(HolderSet<DimensionType> dimensionTypes, int priority, boolean piglinSafe) {
		super(dimensionTypes, priority);
		this.piglinSafe = piglinSafe;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setPiglinSafe(this.piglinSafe);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.PIGLIN_SAFE;
	}

	public boolean piglinSafe() {
		return this.piglinSafe;
	}
}
