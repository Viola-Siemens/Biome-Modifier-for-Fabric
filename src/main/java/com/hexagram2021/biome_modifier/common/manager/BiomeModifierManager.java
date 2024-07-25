package com.hexagram2021.biome_modifier.common.manager;

import com.google.common.collect.ImmutableMap;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.AbstractBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifier;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BiomeModifierManager implements IModifierManager<IBiomeModifier, AbstractBiomeModifier, IModifiableBiome> {
	public static final BiomeModifierManager INSTANCE = new BiomeModifierManager();

	@Nullable
	private List<BiomeModifierHolder> frozenQueue = null;

	private Map<ResourceLocation, IBiomeModifier> biomeModifiersByName = ImmutableMap.of();

	public BiomeModifierManager() {
	}

	@Override
	public void load(RegistryAccess registryAccess) {
		if(this.frozenQueue != null) {
			throw new IllegalStateException("Registry is already frozen!");
		}
		ImmutableMap.Builder<ResourceLocation, IBiomeModifier> builder = ImmutableMap.builder();
		registryAccess.registryOrThrow(BiomeModifierRegistries.REGISTRY_BIOME_MODIFIER).holders().forEach(holder -> builder.put(holder.key().location(), holder.value()));
		this.biomeModifiersByName = builder.build();
	}

	@Override
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

	@Override
	public Optional<IBiomeModifier> getModifier(ResourceLocation id) {
		return Optional.ofNullable(this.biomeModifiersByName.get(id));
	}

	@Override
	public Collection<ResourceLocation> getAllModifierIds() {
		return this.biomeModifiersByName.keySet();
	}

	@Override
	public Collection<IBiomeModifier> getAllModifiers() {
		return this.biomeModifiersByName.values();
	}

	@Override
	public void applyAllModifiers(BiConsumer<ResourceLocation, AbstractBiomeModifier> consumer) {
		if(this.frozenQueue == null) {
			throw new IllegalStateException("Try to apply biome modifiers when registry is not frozen!");
		}
		this.frozenQueue.forEach(holder -> consumer.accept(holder.id(), holder.biomeModifier()));
	}

	@Override
	public void tryCastOrElseThrow(Object object, Consumer<IModifiableBiome> consumer) {
		IModifiableBiome modifiableBiome = (IModifiableBiome)object;
		consumer.accept(modifiableBiome);
	}

	record BiomeModifierHolder(ResourceLocation id, AbstractBiomeModifier biomeModifier) implements Comparable<BiomeModifierHolder> {
		@Override
		public int compareTo(BiomeModifierManager.BiomeModifierHolder o) {
			return Integer.compare(this.biomeModifier.priority(), o.biomeModifier.priority());
		}
	}
}
