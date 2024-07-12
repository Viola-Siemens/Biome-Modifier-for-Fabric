package com.hexagram2021.biome_modifier.api.modifiers.biome;

import com.google.common.collect.Maps;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
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
	Map<IBiomeModifierType, ResourceLocation> BIOME_MODIFIER_IDS = Maps.newIdentityHashMap();
	static void register(ResourceLocation id, IBiomeModifierType biomeModifierType) {
		if(BIOME_MODIFIER_TYPES.containsKey(id)) {
			BMLogger.warn(new IllegalStateException("Duplicate biome modifier type registered: %s.".formatted(id)));
		}
		if(BIOME_MODIFIER_IDS.containsKey(biomeModifierType)) {
			BMLogger.warn(new IllegalStateException("Duplicate biome modifier object registered for %s.".formatted(id)));
		}
		BIOME_MODIFIER_TYPES.put(id, biomeModifierType);
		BIOME_MODIFIER_IDS.put(biomeModifierType, id);
	}

	Codec<IBiomeModifierType> REGISTRY_CODEC = new Codec<>() {
		@Override
		public <R> DataResult<Pair<IBiomeModifierType, R>> decode(DynamicOps<R> ops, R input) {
			return ResourceLocation.CODEC.decode(ops, input).flatMap(pair -> {
				if(!BIOME_MODIFIER_TYPES.containsKey(pair.getFirst())) {
					return DataResult.error(() -> "Unexpected type: %s".formatted(pair.getFirst()));
				}
				return DataResult.success(pair.mapFirst(BIOME_MODIFIER_TYPES::get));
			});
		}

		@Override
		public <R> DataResult<R> encode(IBiomeModifierType input, DynamicOps<R> ops, R prefix) {
			ResourceLocation id = BIOME_MODIFIER_IDS.get(input);
			if(id == null) {
				return DataResult.error(() -> "Unknown biome modifier type: %s".formatted(input));
			}
			R key = ops.createString(id.toString());
			return ops.mergeToPrimitive(prefix, key);
		}
	};
}
