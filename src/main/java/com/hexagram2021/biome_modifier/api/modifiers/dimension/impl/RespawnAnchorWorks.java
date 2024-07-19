package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class RespawnAnchorWorks extends AbstractDimensionModifier {
	public static final Codec<RespawnAnchorWorks> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(RespawnAnchorWorks::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(RespawnAnchorWorks::priority),
					Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(RespawnAnchorWorks::respawnAnchorWorks)
			).apply(instance, RespawnAnchorWorks::new)
	);

	final boolean respawnAnchorWorks;

	public RespawnAnchorWorks(HolderSet<DimensionType> dimensionTypes, int priority, boolean respawnAnchorWorks) {
		super(dimensionTypes, priority);
		this.respawnAnchorWorks = respawnAnchorWorks;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setRespawnAnchorWorks(this.respawnAnchorWorks);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.RESPAWN_ANCHOR_WORKS;
	}

	public boolean respawnAnchorWorks() {
		return this.respawnAnchorWorks;
	}
}
