package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.dimension.DimensionType;

public interface IDimensionModifier {
	/**
	 * Can this modifier modify a certain dimension? (Matching "dimensions" field.)
	 *
	 * @param dimension the registered dimension to be modified.
	 *
	 * @return true if this modifier can modify the input dimension.
	 */
	boolean canModify(Holder<DimensionType> dimension);
	/**
	 * Apply the modification to a dimension.
	 *
	 * @param list the "parameters list" of a registered dimension to be modified.
	 */
	void modify(IModifiableDimension.DimensionModificationParametersList list);

	IDimensionModifierType type();

	Codec<IDimensionModifier> REGISTRY_CODEC = IDimensionModifierType.REGISTRY_CODEC.dispatch(IDimensionModifier::type, IDimensionModifierType::codec);

	@Deprecated
	static IDimensionModifier fromJson(DynamicOps<JsonElement> dynamicOps, JsonObject object) {
		String type = GsonHelper.convertToString(object.getAsJsonPrimitive("type"), "type");
		ResourceLocation typeId = ResourceLocation.tryParse(type);
		if(typeId == null || !IDimensionModifierType.DIMENSION_MODIFIER_TYPES.containsKey(typeId)) {
			throw new IllegalArgumentException("Unexpected type: %s".formatted(typeId));
		}
		Codec<? extends IDimensionModifier> codec = IDimensionModifierType.DIMENSION_MODIFIER_TYPES.get(typeId).codec();
		return Util.getOrThrow(codec.parse(dynamicOps, object), JsonParseException::new);
	}
}
