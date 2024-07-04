package com.hexagram2021.biome_modifier.common.manager;

import com.google.common.collect.ImmutableMap;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class BiomeModifierManager {
	@Nullable
	public static BiomeModifierManager INSTANCE = new BiomeModifierManager();

	private Map<ResourceLocation, IBiomeModifier> biomeModifiersByName = ImmutableMap.of();

	public BiomeModifierManager() {
	}

	public void load(RegistryAccess registryAccess) {
		ImmutableMap.Builder<ResourceLocation, IBiomeModifier> builder = ImmutableMap.builder();
		registryAccess.registryOrThrow(BiomeModifierRegistries.REGISTRY_BIOME_MODIFIER).holders().forEach(holder -> builder.put(holder.key().location(), holder.value()));
		this.biomeModifiersByName = builder.build();
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

	public void applyAllBiomeModifiers(BiConsumer<ResourceLocation, IBiomeModifier> consumer) {
		this.biomeModifiersByName.forEach(consumer);
	}
}
