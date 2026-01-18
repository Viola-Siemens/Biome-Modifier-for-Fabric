package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;

public class Effects extends AbstractDimensionModifier {
	public static final MapCodec<Effects> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(Effects::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Effects::priority),
					ResourceLocation.CODEC.fieldOf("effects").forGetter(Effects::effectsLocation)
			).apply(instance, Effects::new)
	);

	final ResourceLocation effectsLocation;

	public Effects(HolderSet<DimensionType> dimensionTypes, int priority, ResourceLocation effectsLocation) {
		super(dimensionTypes, priority);
		this.effectsLocation = effectsLocation;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setEffectsLocation(this.effectsLocation);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.EFFECTS;
	}

	public ResourceLocation effectsLocation() {
		return this.effectsLocation;
	}
}
