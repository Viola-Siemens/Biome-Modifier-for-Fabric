package com.hexagram2021.biome_modifier.api.modifiers.noise_generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import com.hexagram2021.biome_modifier.api.modifiers.IModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public interface INoiseGeneratorModifier extends IModifier<NoiseGeneratorSettings, IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList> {
	/**
	 * Can this modifier modify a certain noise generator? (Matching "noise_generators" field.)
	 *
	 * @param noise_generator the registered noise generator to be modified.
	 *
	 * @return true if this modifier can modify the input noise generator.
	 */
	@Override
	boolean canModify(Holder<NoiseGeneratorSettings> noise_generator);
	/**
	 * Apply the modification to a noise generator.
	 *
	 * @param list the "parameters list" of a registered noise generator to be modified.
	 */
	@Override
	void modify(IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList list);

	INoiseGeneratorModifierType type();

	Codec<INoiseGeneratorModifier> REGISTRY_CODEC = INoiseGeneratorModifierType.REGISTRY_CODEC.dispatch(INoiseGeneratorModifier::type, INoiseGeneratorModifierType::codec);

	@Deprecated
	static INoiseGeneratorModifier fromJson(DynamicOps<JsonElement> dynamicOps, JsonObject object) {
		String type = GsonHelper.convertToString(object.getAsJsonPrimitive("type"), "type");
		ResourceLocation typeId = ResourceLocation.tryParse(type);
		if(typeId == null || !INoiseGeneratorModifierType.NOISE_GENERATOR_MODIFIER_TYPES.containsKey(typeId)) {
			throw new IllegalArgumentException("Unexpected type: %s".formatted(typeId));
		}
		Codec<? extends INoiseGeneratorModifier> codec = INoiseGeneratorModifierType.NOISE_GENERATOR_MODIFIER_TYPES.get(typeId).codec();
		return Util.getOrThrow(codec.parse(dynamicOps, object), JsonParseException::new);
	}
}
