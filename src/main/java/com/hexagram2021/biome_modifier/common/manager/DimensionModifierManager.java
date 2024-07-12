package com.hexagram2021.biome_modifier.common.manager;

import com.google.common.collect.ImmutableMap;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifier;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class DimensionModifierManager {
	public static final DimensionModifierManager INSTANCE = new DimensionModifierManager();

	@Nullable
	private List<DimensionModifierHolder> frozenQueue = null;

	private Map<ResourceLocation, IDimensionModifier> dimensionModifiersByName = ImmutableMap.of();

	public DimensionModifierManager() {
	}

	public void load(RegistryAccess registryAccess) {
		if(this.frozenQueue != null) {
			throw new IllegalStateException("Registry is already frozen!");
		}
		ImmutableMap.Builder<ResourceLocation, IDimensionModifier> builder = ImmutableMap.builder();
		registryAccess.registryOrThrow(BiomeModifierRegistries.REGISTRY_DIMENSION_MODIFIER).holders().forEach(holder -> builder.put(holder.key().location(), holder.value()));
		this.dimensionModifiersByName = builder.build();
	}

	public void freeze() {
		if(this.frozenQueue != null) {
			BMLogger.warn("freeze() called in the frozen registry!");
		} else {
			this.frozenQueue = this.dimensionModifiersByName.entrySet().stream()
					.filter(entry -> entry.getValue() instanceof AbstractDimensionModifier)
					.map(entry -> new DimensionModifierHolder(entry.getKey(), (AbstractDimensionModifier)entry.getValue()))
					.sorted().toList();
		}
	}

	public Optional<IDimensionModifier> getDimensionModifier(ResourceLocation id) {
		return Optional.ofNullable(this.dimensionModifiersByName.get(id));
	}

	public Collection<ResourceLocation> getAllDimensionModifierIds() {
		return this.dimensionModifiersByName.keySet();
	}

	public Collection<IDimensionModifier> getAllDimensionModifiers() {
		return this.dimensionModifiersByName.values();
	}

	public void applyAllDimensionModifiers(BiConsumer<ResourceLocation, AbstractDimensionModifier> consumer) {
		if(this.frozenQueue == null) {
			throw new IllegalStateException("Try to apply dimension modifiers when registry is not frozen!");
		}
		this.frozenQueue.forEach(holder -> consumer.accept(holder.id(), holder.dimensionModifier()));
	}

	record DimensionModifierHolder(ResourceLocation id, AbstractDimensionModifier dimensionModifier) implements Comparable<DimensionModifierHolder> {
		@Override
		public int compareTo(DimensionModifierHolder o) {
			return Integer.compare(this.dimensionModifier.priority(), o.dimensionModifier.priority());
		}
	}
}
