package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class Raids extends AbstractDimensionModifier {
	public static final Codec<Raids> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(Raids::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(Raids::priority),
					Codec.BOOL.fieldOf("has_raids").forGetter(Raids::hasRaids)
			).apply(instance, Raids::new)
	);

	final boolean hasRaids;

	public Raids(HolderSet<DimensionType> dimensionTypes, int priority, boolean hasRaids) {
		super(dimensionTypes, priority);
		this.hasRaids = hasRaids;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setHasRaids(this.hasRaids);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.RAIDS;
	}

	public boolean hasRaids() {
		return this.hasRaids;
	}
}
