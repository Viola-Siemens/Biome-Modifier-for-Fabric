package com.hexagram2021.biome_modifier.common.manager;

import com.hexagram2021.biome_modifier.api.IModifiableApi;
import com.hexagram2021.biome_modifier.api.modifiers.IModifier;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IModifierManager<T extends IModifier<?, ?>, A extends T, M extends IModifiableApi<?>> {
	void load(RegistryAccess registryAccess);
	void freeze();
	void unfreeze();

	Optional<T> getModifier(ResourceLocation id);
	Collection<ResourceLocation> getAllModifierIds();
	Collection<T> getAllModifiers();
	void applyAllModifiers(BiConsumer<ResourceLocation, A> consumer);

	void tryCastOrElseThrow(Object object, Consumer<M> consumer) throws ClassCastException;
}
