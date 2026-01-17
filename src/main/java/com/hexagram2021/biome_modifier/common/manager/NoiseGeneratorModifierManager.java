package com.hexagram2021.biome_modifier.common.manager;

import com.google.common.collect.ImmutableMap;
import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.AbstractNoiseGeneratorModifier;
import com.hexagram2021.biome_modifier.api.modifiers.noise_generator.INoiseGeneratorModifier;
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

public class NoiseGeneratorModifierManager implements IModifierManager<INoiseGeneratorModifier, AbstractNoiseGeneratorModifier, IModifiableNoiseGenerator> {
	public static final NoiseGeneratorModifierManager INSTANCE = new NoiseGeneratorModifierManager();

	@Nullable
	private List<NoiseGeneratorModifierHolder> frozenQueue = null;

	private Map<ResourceLocation, INoiseGeneratorModifier> noiseGeneratorModifiersByName = ImmutableMap.of();

	public NoiseGeneratorModifierManager() {
		// Empty
	}

	@Override
	public void load(RegistryAccess registryAccess) {
		if(this.frozenQueue != null) {
			throw new IllegalStateException("Registry is already frozen!");
		}
		ImmutableMap.Builder<ResourceLocation, INoiseGeneratorModifier> builder = ImmutableMap.builder();
		registryAccess.registryOrThrow(BiomeModifierRegistries.REGISTRY_NOISE_GENERATOR_MODIFIER).holders().forEach(holder -> builder.put(holder.key().location(), holder.value()));
		this.noiseGeneratorModifiersByName = builder.build();
	}

	@Override
	public void freeze() {
		if(this.frozenQueue != null) {
			BMLogger.warn("freeze() called in the frozen registry!");
		} else {
			this.frozenQueue = this.noiseGeneratorModifiersByName.entrySet().stream()
					.filter(entry -> entry.getValue() instanceof AbstractNoiseGeneratorModifier)
					.map(entry -> new NoiseGeneratorModifierHolder(entry.getKey(), (AbstractNoiseGeneratorModifier)entry.getValue()))
					.sorted().toList();
		}
	}

	@Override
	public void unfreeze() {
		if(this.frozenQueue == null) {
			BMLogger.warn("unfreeze() called in the unfrozen registry!");
		}
		this.frozenQueue = null;
	}

	@Override
	public Optional<INoiseGeneratorModifier> getModifier(ResourceLocation id) {
		return Optional.ofNullable(this.noiseGeneratorModifiersByName.get(id));
	}

	@Override
	public Collection<ResourceLocation> getAllModifierIds() {
		return this.noiseGeneratorModifiersByName.keySet();
	}

	@Override
	public Collection<INoiseGeneratorModifier> getAllModifiers() {
		return this.noiseGeneratorModifiersByName.values();
	}

	@Override
	public void applyAllModifiers(BiConsumer<ResourceLocation, AbstractNoiseGeneratorModifier> consumer) {
		if(this.frozenQueue == null) {
			throw new IllegalStateException("Try to apply noise generator modifiers when registry is not frozen!");
		}
		this.frozenQueue.forEach(holder -> consumer.accept(holder.id(), holder.noiseGeneratorModifier()));
	}

	@Override
	public void tryCastOrElseThrow(Object object, Consumer<IModifiableNoiseGenerator> consumer) {
		IModifiableNoiseGenerator modifiableNoiseGenerator = (IModifiableNoiseGenerator)object;
		consumer.accept(modifiableNoiseGenerator);
	}

	record NoiseGeneratorModifierHolder(ResourceLocation id, AbstractNoiseGeneratorModifier noiseGeneratorModifier) implements Comparable<NoiseGeneratorModifierHolder> {
		@Override
		public int compareTo(NoiseGeneratorModifierHolder o) {
			return Integer.compare(this.noiseGeneratorModifier.priority(), o.noiseGeneratorModifier.priority());
		}
	}
}
