package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

public class RemoveSpawnCosts extends AbstractBiomeModifier {
	public static final Codec<RemoveSpawnCosts> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(RemoveSpawnCosts::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(RemoveSpawnCosts::priority),
					RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entity_types").forGetter(RemoveSpawnCosts::entityTypes)
			).apply(instance, RemoveSpawnCosts::new)
	);

	final HolderSet<EntityType<?>> entityTypes;

	protected RemoveSpawnCosts(HolderSet<Biome> biomes, int priority, HolderSet<EntityType<?>> entityTypes) {
		super(biomes, priority);
		this.entityTypes = entityTypes;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.removeSpawnerCosts(this.entityTypes.stream().map(Holder::value).toList());
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.REMOVE_SPAWN_COSTS;
	}

	public HolderSet<EntityType<?>> entityTypes() {
		return this.entityTypes;
	}
}
