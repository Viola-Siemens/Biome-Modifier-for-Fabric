package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.dimension.DimensionType;

public class NoneDimensionModifier implements IDimensionModifier {
	public static final Codec<NoneDimensionModifier> CODEC = RecordCodecBuilder.create(instance -> instance.point(new NoneDimensionModifier()));

	protected NoneDimensionModifier() {
	}

	@Override
	public boolean canModify(Holder<DimensionType> biome) {
		return false;
	}
	@Override
	public void modify(IModifiableDimension.DimensionModificationParametersList list) {
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.NONE;
	}
}
