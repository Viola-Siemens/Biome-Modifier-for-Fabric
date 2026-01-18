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

public class MonsterSpawnBlockLightLimit extends AbstractDimensionModifier {
	public static final MapCodec<MonsterSpawnBlockLightLimit> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(MonsterSpawnBlockLightLimit::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(MonsterSpawnBlockLightLimit::priority),
					Codec.intRange(0, 15).fieldOf("monster_spawn_block_light_limit").forGetter(MonsterSpawnBlockLightLimit::monsterSpawnBlockLightLimit)
			).apply(instance, MonsterSpawnBlockLightLimit::new)
	);

	final int monsterSpawnBlockLightLimit;

	public MonsterSpawnBlockLightLimit(HolderSet<DimensionType> dimensionTypes, int priority, int monsterSpawnBlockLightLimit) {
		super(dimensionTypes, priority);
		this.monsterSpawnBlockLightLimit = monsterSpawnBlockLightLimit;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setMonsterSpawnBlockLightLimit(this.monsterSpawnBlockLightLimit);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.MONSTER_SPAWN_BLOCK_LIGHT_LIMIT;
	}

	public int monsterSpawnBlockLightLimit() {
		return this.monsterSpawnBlockLightLimit;
	}
}
