package com.hexagram2021.biome_modifier.common.manager;

import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifier;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class BiomeModifierRegistries {
	public static final ResourceKey<Registry<IBiomeModifier>> REGISTRY_BIOME_MODIFIER =
			ResourceKey.createRegistryKey(new ResourceLocation("biome_modifiers"));

	public static void init() {
		DynamicRegistries.register(REGISTRY_BIOME_MODIFIER, IBiomeModifier.REGISTRY_CODEC);
	}
}
