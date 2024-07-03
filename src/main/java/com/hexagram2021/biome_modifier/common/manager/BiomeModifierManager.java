package com.hexagram2021.biome_modifier.common.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.hexagram2021.biome_modifier.BiomeModifierMod;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifier;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class BiomeModifierManager extends SimpleJsonResourceReloadListener implements IdentifiableResourceReloadListener {
	private static final ResourceLocation ID = new ResourceLocation(BiomeModifierMod.MODID, "biome_modifiers");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	@Nullable
	public static BiomeModifierManager INSTANCE = null;

	private final RegistryAccess registryAccess;

	private Map<ResourceLocation, IBiomeModifier> biomeModifiersByName = ImmutableMap.of();

	public BiomeModifierManager(RegistryAccess registryAccess) {
		super(GSON, ID.getPath());
		this.registryAccess = registryAccess;
		if(INSTANCE != null) {
			BMLogger.error("INSTANCE already exists!");
		}
		INSTANCE = this;
	}

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	public Collection<ResourceLocation> getFabricDependencies() {
		return Collections.singletonList(ResourceReloadListenerKeys.TAGS);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> modifiers, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
		ImmutableMap.Builder<ResourceLocation, IBiomeModifier> builder = ImmutableMap.builder();
		DynamicOps<JsonElement> dynamicOps = RegistryOps.create(JsonOps.INSTANCE, this.registryAccess);
		for(Map.Entry<ResourceLocation, JsonElement> entry: modifiers.entrySet()) {
			ResourceLocation id = entry.getKey();
			if (id.getPath().startsWith("_")) {
				continue;
			}
			try {
				if (entry.getValue().isJsonObject() && !processConditions(entry.getValue().getAsJsonObject())) {
					BMLogger.debug("Skipping loading biome modifier %s as it's conditions were not met".formatted(id));
					continue;
				}
				JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "top element");
				IBiomeModifier biomeModifier = IBiomeModifier.fromJson(dynamicOps, jsonObject);
				builder.put(id, biomeModifier);
			} catch (RuntimeException e) {
				BMLogger.error("Parsing error loading biome modifier %s.".formatted(id), e);
			}
		}
		this.biomeModifiersByName = builder.build();
	}

	private static final String CONDITIONS_FIELD = "conditions";
	private static boolean processConditions(JsonObject json) {
		return !json.has(CONDITIONS_FIELD) || BiomeModifierLoadCondition.fromJson(json.get(CONDITIONS_FIELD)).test();
	}

	public Optional<IBiomeModifier> getBiomeModifier(ResourceLocation id) {
		return Optional.ofNullable(this.biomeModifiersByName.get(id));
	}

	public Stream<ResourceLocation> getAllBiomeModifierIds() {
		return this.biomeModifiersByName.keySet().stream();
	}

	public Collection<IBiomeModifier> getAllBiomeModifiers() {
		return this.biomeModifiersByName.values();
	}

	public void applyAllBiomeModifiers(BiConsumer<ResourceLocation, IBiomeModifier> consumer) {
		this.biomeModifiersByName.forEach(consumer);
	}
}
