package com.hexagram2021.biome_modifier.common.manager;

import com.google.common.collect.ImmutableMap;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifier;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;

public class BiomeModifierManager {
	public static final BiomeModifierManager INSTANCE = new BiomeModifierManager();

	@Nullable
	private List<BiomeModifierHolder> frozenQueue = null;

	private Map<ResourceLocation, IBiomeModifier> biomeModifiersByName = ImmutableMap.of();

	public BiomeModifierManager() {
	}

	public void load(RegistryAccess registryAccess) {
		if(this.frozenQueue != null) {
			throw new IllegalStateException("Registry is already frozen!");
		}
		ImmutableMap.Builder<ResourceLocation, IBiomeModifier> builder = ImmutableMap.builder();
		registryAccess.registryOrThrow(BiomeModifierRegistries.REGISTRY_BIOME_MODIFIER).holders().forEach(holder -> builder.put(holder.key().location(), holder.value()));
		this.biomeModifiersByName = builder.build();
	}

	public void freeze() {
		if(this.frozenQueue != null) {
			BMLogger.warn("freeze() called in the frozen registry!");
		} else {
			this.frozenQueue = this.biomeModifiersByName.entrySet().stream()
					.filter(entry -> entry.getValue() instanceof AbstractBiomeModifier)
					.map(entry -> new BiomeModifierHolder(entry.getKey(), (AbstractBiomeModifier)entry.getValue()))
					.sorted().toList();
		}
	}

	public Optional<IBiomeModifier> getBiomeModifier(ResourceLocation id) {
		return Optional.ofNullable(this.biomeModifiersByName.get(id));
	}

	public Collection<ResourceLocation> getAllBiomeModifierIds() {
		return this.biomeModifiersByName.keySet();
	}

	public Collection<IBiomeModifier> getAllBiomeModifiers() {
		return this.biomeModifiersByName.values();
	}

	public void applyAllBiomeModifiers(BiConsumer<ResourceLocation, AbstractBiomeModifier> consumer) {
		if(this.frozenQueue == null) {
			throw new IllegalStateException("Try to apply biome modifiers when registry is not frozen!");
		}
		this.frozenQueue.forEach(holder -> consumer.accept(holder.id(), holder.biomeModifier()));
	}

	record BiomeModifierHolder(ResourceLocation id, AbstractBiomeModifier biomeModifier) implements Comparable<BiomeModifierHolder> {
		@Override
		public int compareTo(BiomeModifierManager.BiomeModifierHolder o) {
			return Integer.compare(this.biomeModifier.priority(), o.biomeModifier.priority());
		}
	}
}
