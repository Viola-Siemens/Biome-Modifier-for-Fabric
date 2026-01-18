package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

public class CreatureGenerationProbability extends AbstractBiomeModifier {
	public static final MapCodec<CreatureGenerationProbability> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(CreatureGenerationProbability::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(CreatureGenerationProbability::priority),
					Codec.floatRange(0.0F, 0.9999999F).fieldOf("creature_spawn_probability").forGetter(CreatureGenerationProbability::creatureGenerationProbability)
			).apply(instance, CreatureGenerationProbability::new)
	);

	final float creatureGenerationProbability;

	protected CreatureGenerationProbability(HolderSet<Biome> biomes, int priority, float creatureGenerationProbability) {
		super(biomes, priority);
		this.creatureGenerationProbability = creatureGenerationProbability;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.setCreatureGenerationProbability(this.creatureGenerationProbability);
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.CREATURE_SPAWN_PROBABILITY;
	}

	public float creatureGenerationProbability() {
		return this.creatureGenerationProbability;
	}
}
