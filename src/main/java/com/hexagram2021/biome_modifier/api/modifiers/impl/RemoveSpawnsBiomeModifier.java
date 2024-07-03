package com.hexagram2021.biome_modifier.api.modifiers.impl;

import com.hexagram2021.biome_modifier.api.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

public class RemoveSpawnsBiomeModifier extends AbstractBiomeModifier {
	public static final Codec<RemoveSpawnsBiomeModifier> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Biome.LIST_CODEC.fieldOf("biomes").forGetter(RemoveSpawnsBiomeModifier::biomes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(RemoveSpawnsBiomeModifier::priority),
					RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE).fieldOf("entity_types").forGetter(RemoveSpawnsBiomeModifier::entityTypes)
			).apply(instance, RemoveSpawnsBiomeModifier::new)
	);

	final HolderSet<EntityType<?>> entityTypes;

	protected RemoveSpawnsBiomeModifier(HolderSet<Biome> biomes, int priority, HolderSet<EntityType<?>> entityTypes) {
		super(biomes, priority);
		this.entityTypes = entityTypes;
	}

	@Override
	public void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException {
		list.removeSpawners(this.entityTypes.stream().map(Holder::value).toList());
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.REMOVE_SPAWNS;
	}

	public HolderSet<EntityType<?>> entityTypes() {
		return this.entityTypes;
	}
}
