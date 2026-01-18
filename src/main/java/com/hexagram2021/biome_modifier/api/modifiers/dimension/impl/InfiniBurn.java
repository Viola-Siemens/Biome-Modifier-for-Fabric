package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

public class InfiniBurn extends AbstractDimensionModifier {
	public static final MapCodec<InfiniBurn> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(InfiniBurn::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(InfiniBurn::priority),
					TagKey.hashedCodec(Registries.BLOCK).fieldOf("infiniburn").forGetter(InfiniBurn::infiniburn)
			).apply(instance, InfiniBurn::new)
	);

	final TagKey<Block> infiniburn;

	public InfiniBurn(HolderSet<DimensionType> dimensionTypes, int priority, TagKey<Block> infiniburn) {
		super(dimensionTypes, priority);
		this.infiniburn = infiniburn;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setInfiniburn(this.infiniburn);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.INFINIBURN;
	}

	public TagKey<Block> infiniburn() {
		return this.infiniburn;
	}
}
