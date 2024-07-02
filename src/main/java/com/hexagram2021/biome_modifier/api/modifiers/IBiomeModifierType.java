package com.hexagram2021.biome_modifier.api.modifiers;

import com.google.common.collect.Maps;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

@FunctionalInterface
public interface IBiomeModifierType {

	/**
	 * To support datapack loading, we need codec to serialize and deserialize modifiers.
	 *
	 * @return Codec of this biome modifier.
	 */
	Codec<? extends IBiomeModifier> codec();

	Map<ResourceLocation, IBiomeModifierType> BIOME_MODIFIER_TYPES = Maps.newHashMap();
	static void register(ResourceLocation id, IBiomeModifierType biomeModifierType) {
		if(BIOME_MODIFIER_TYPES.containsKey(id)) {
			BMLogger.warn(new IllegalStateException("Duplicate biome modifier type registered: %s."));
		}
		BIOME_MODIFIER_TYPES.put(id, biomeModifierType);
	}
}
