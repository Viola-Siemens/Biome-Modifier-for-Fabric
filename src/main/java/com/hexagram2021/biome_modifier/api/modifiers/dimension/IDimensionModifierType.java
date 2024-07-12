package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import com.google.common.collect.Maps;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

@FunctionalInterface
public interface IDimensionModifierType {

	/**
	 * To support datapack loading, we need codec to serialize and deserialize modifiers.
	 *
	 * @return Codec of this dimension modifier.
	 */
	Codec<? extends IDimensionModifier> codec();

	Map<ResourceLocation, IDimensionModifierType> DIMENSION_MODIFIER_TYPES = Maps.newHashMap();
	Map<IDimensionModifierType, ResourceLocation> DIMENSION_MODIFIER_IDS = Maps.newIdentityHashMap();
	static void register(ResourceLocation id, IDimensionModifierType dimensionModifierType) {
		if(DIMENSION_MODIFIER_TYPES.containsKey(id)) {
			BMLogger.warn(new IllegalStateException("Duplicate dimension modifier type registered: %s.".formatted(id)));
		}
		if(DIMENSION_MODIFIER_IDS.containsKey(dimensionModifierType)) {
			BMLogger.warn(new IllegalStateException("Duplicate dimension modifier object registered for %s.".formatted(id)));
		}
		DIMENSION_MODIFIER_TYPES.put(id, dimensionModifierType);
		DIMENSION_MODIFIER_IDS.put(dimensionModifierType, id);
	}

	Codec<IDimensionModifierType> REGISTRY_CODEC = new Codec<>() {
		@Override
		public <R> DataResult<Pair<IDimensionModifierType, R>> decode(DynamicOps<R> ops, R input) {
			return ResourceLocation.CODEC.decode(ops, input).flatMap(pair -> {
				if(!DIMENSION_MODIFIER_TYPES.containsKey(pair.getFirst())) {
					return DataResult.error(() -> "Unexpected type: %s".formatted(pair.getFirst()));
				}
				return DataResult.success(pair.mapFirst(DIMENSION_MODIFIER_TYPES::get));
			});
		}

		@Override
		public <R> DataResult<R> encode(IDimensionModifierType input, DynamicOps<R> ops, R prefix) {
			ResourceLocation id = DIMENSION_MODIFIER_IDS.get(input);
			if(id == null) {
				return DataResult.error(() -> "Unknown dimension modifier type: %s".formatted(input));
			}
			R key = ops.createString(id.toString());
			return ops.mergeToPrimitive(prefix, key);
		}
	};
}
