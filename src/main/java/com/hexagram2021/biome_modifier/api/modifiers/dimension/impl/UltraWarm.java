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

public class UltraWarm extends AbstractDimensionModifier {
	public static final MapCodec<UltraWarm> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(UltraWarm::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(UltraWarm::priority),
					Codec.BOOL.fieldOf("ultrawarm").forGetter(UltraWarm::ultraWarm)
			).apply(instance, UltraWarm::new)
	);

	final boolean ultraWarm;

	public UltraWarm(HolderSet<DimensionType> dimensionTypes, int priority, boolean ultraWarm) {
		super(dimensionTypes, priority);
		this.ultraWarm = ultraWarm;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setUltraWarm(this.ultraWarm);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.ULTRA_WARM;
	}

	public boolean ultraWarm() {
		return this.ultraWarm;
	}
}
