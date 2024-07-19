package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.dimension.DimensionType;

public class MonsterSpawnLight extends AbstractDimensionModifier {
	public static final Codec<MonsterSpawnLight> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(MonsterSpawnLight::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(MonsterSpawnLight::priority),
					IntProvider.codec(0, 15).fieldOf("monster_spawn_light_level").forGetter(MonsterSpawnLight::monsterSpawnLightLevel)
			).apply(instance, MonsterSpawnLight::new)
	);

	final IntProvider monsterSpawnLightLevel;

	public MonsterSpawnLight(HolderSet<DimensionType> dimensionTypes, int priority, IntProvider monsterSpawnLightLevel) {
		super(dimensionTypes, priority);
		this.monsterSpawnLightLevel = monsterSpawnLightLevel;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setMonsterSpawnLightTest(this.monsterSpawnLightLevel);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.MONSTER_SPAWN_LIGHT;
	}

	public IntProvider monsterSpawnLightLevel() {
		return this.monsterSpawnLightLevel;
	}
}
