package com.hexagram2021.biome_modifier.api.modifiers.noise_generator;

import com.google.common.collect.Maps;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

@FunctionalInterface
public interface INoiseGeneratorModifierType {

	/**
	 * To support datapack loading, we need codec to serialize and deserialize modifiers.
	 *
	 * @return Codec of this noise generator modifier.
	 */
	Codec<? extends INoiseGeneratorModifier> codec();

	Map<ResourceLocation, INoiseGeneratorModifierType> NOISE_GENERATOR_MODIFIER_TYPES = Maps.newHashMap();
	Map<INoiseGeneratorModifierType, ResourceLocation> NOISE_GENERATOR_MODIFIER_IDS = Maps.newIdentityHashMap();
	static void register(ResourceLocation id, INoiseGeneratorModifierType noiseGeneratorModifierType) {
		if(NOISE_GENERATOR_MODIFIER_TYPES.containsKey(id)) {
			BMLogger.warn(new IllegalStateException("Duplicate noise generator modifier type registered: %s.".formatted(id)));
		}
		if(NOISE_GENERATOR_MODIFIER_IDS.containsKey(noiseGeneratorModifierType)) {
			BMLogger.warn(new IllegalStateException("Duplicate noise generator modifier object registered for %s.".formatted(id)));
		}
		NOISE_GENERATOR_MODIFIER_TYPES.put(id, noiseGeneratorModifierType);
		NOISE_GENERATOR_MODIFIER_IDS.put(noiseGeneratorModifierType, id);
	}

	Codec<INoiseGeneratorModifierType> REGISTRY_CODEC = new Codec<>() {
		@Override
		public <R> DataResult<Pair<INoiseGeneratorModifierType, R>> decode(DynamicOps<R> ops, R input) {
			return ResourceLocation.CODEC.decode(ops, input).flatMap(pair -> {
				if(!NOISE_GENERATOR_MODIFIER_TYPES.containsKey(pair.getFirst())) {
					return DataResult.error(() -> "Unexpected type: %s".formatted(pair.getFirst()));
				}
				return DataResult.success(pair.mapFirst(NOISE_GENERATOR_MODIFIER_TYPES::get));
			});
		}

		@Override
		public <R> DataResult<R> encode(INoiseGeneratorModifierType input, DynamicOps<R> ops, R prefix) {
			ResourceLocation id = NOISE_GENERATOR_MODIFIER_IDS.get(input);
			if(id == null) {
				return DataResult.error(() -> "Unknown noise generator modifier type: %s".formatted(input));
			}
			R key = ops.createString(id.toString());
			return ops.mergeToPrimitive(prefix, key);
		}
	};
}
