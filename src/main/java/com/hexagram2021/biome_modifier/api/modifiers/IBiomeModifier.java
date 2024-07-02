package com.hexagram2021.biome_modifier.api.modifiers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

public interface IBiomeModifier {
	/**
	 * Can this modifier modify a certain biome?
	 *
	 * @param biome the registered biome to be modified.
	 *
	 * @return true if this modifier can modify the input biome.
	 */
	boolean canModify(Biome biome);
	/**
	 * Apply the modification to a biome.
	 *
	 * @param list the "parameters list" of a registered biome to be modified.
	 */
	void modify(IModifiableBiome.BiomeModificationParametersList list);

	IBiomeModifierType type();

	static IBiomeModifier fromJson(JsonObject object) {
		String type = GsonHelper.convertToString(object.getAsJsonPrimitive("type"), "type");
		ResourceLocation typeId = ResourceLocation.tryParse(type);
		if(typeId == null || !IBiomeModifierType.BIOME_MODIFIER_TYPES.containsKey(typeId)) {
			throw new IllegalArgumentException("Unexpected type: %s");
		}
		Codec<? extends IBiomeModifier> codec = IBiomeModifierType.BIOME_MODIFIER_TYPES.get(typeId).codec();
		return Util.getOrThrow(codec.parse(JsonOps.INSTANCE, object), JsonParseException::new);
	}
}
