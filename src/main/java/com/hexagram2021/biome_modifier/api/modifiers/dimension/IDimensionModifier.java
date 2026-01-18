package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.IModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.dimension.DimensionType;

public interface IDimensionModifier extends IModifier<DimensionType, IModifiableDimension.DimensionModificationParametersList> {
	/**
	 * Can this modifier modify a certain dimension? (Matching "dimensions" field.)
	 *
	 * @param dimension the registered dimension to be modified.
	 *
	 * @return true if this modifier can modify the input dimension.
	 */
	@Override
	boolean canModify(Holder<DimensionType> dimension);
	/**
	 * Apply the modification to a dimension.
	 *
	 * @param list the "parameters list" of a registered dimension to be modified.
	 */
	@Override
	void modify(IModifiableDimension.DimensionModificationParametersList list);

	IDimensionModifierType type();

	Codec<IDimensionModifier> REGISTRY_CODEC = IDimensionModifierType.REGISTRY_CODEC.dispatch(IDimensionModifier::type, IDimensionModifierType::codec);

	/**
	 * @deprecated Use {@link #REGISTRY_CODEC} instead.
	 * @param dynamicOps	dynamic ops
	 * @param object		Json object
	 * @return A dimension modifier instance.
	 */
	@Deprecated(forRemoval = true, since = "26")
	static IDimensionModifier fromJson(DynamicOps<JsonElement> dynamicOps, JsonObject object) {
		String type = GsonHelper.convertToString(object.getAsJsonPrimitive("type"), "type");
		ResourceLocation typeId = ResourceLocation.tryParse(type);
		if(typeId == null || !IDimensionModifierType.DIMENSION_MODIFIER_TYPES.containsKey(typeId)) {
			throw new IllegalArgumentException("Unexpected type: %s".formatted(typeId));
		}
		Codec<? extends IDimensionModifier> codec = IDimensionModifierType.DIMENSION_MODIFIER_TYPES.get(typeId).codec().codec();
		return codec.parse(dynamicOps, object).getOrThrow(JsonParseException::new);
	}
}
