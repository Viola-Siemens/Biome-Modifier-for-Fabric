package com.hexagram2021.biome_modifier.common.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.Objects;

record BiomeModifierLoadCondition(ConditionType type, String value, boolean present) {
	BiomeModifierLoadCondition(ConditionType type, String value) {
		this(type, value, true);
	}

	BiomeModifierLoadCondition(ConditionType type, String value, boolean present) {
		this.type = type;
		this.value = value;
		this.present = present;

		if(this.type == ConditionType.MOD_LOAD) {
			if(!ResourceLocation.isValidNamespace(this.value)) {
				throw new IllegalArgumentException("Not a valid modid for condition value: \"%s\"!".formatted(this.value));
			}
		} else {
			if(!ResourceLocation.isValidResourceLocation(this.value)) {
				throw new IllegalArgumentException("Not a valid resource location for condition value: \"%s\"!".formatted(this.value));
			}
		}
	}

	public boolean test() {
		return switch (this.type) {
			case MOD_LOAD -> FabricLoader.getInstance().getModContainer(this.value).isPresent();
		};
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BiomeModifierLoadCondition that = (BiomeModifierLoadCondition) o;
		return this.present == that.present && this.type == that.type && this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.value, this.present);
	}

	public static BiomeModifierLoadCondition fromJson(JsonElement json) {
		if(json.isJsonObject()) {
			JsonObject jsonObject = json.getAsJsonObject();
			return new BiomeModifierLoadCondition(getConditionType(jsonObject), getValue(jsonObject, "value", "id"), GsonHelper.getAsBoolean(jsonObject, "present", true));
		}
		if(json.isJsonPrimitive()) {
			JsonPrimitive primitive = json.getAsJsonPrimitive();
			if(primitive.isString()) {
				return new BiomeModifierLoadCondition(ConditionType.MOD_LOAD, primitive.getAsString());
			}
		}
		throw new IllegalArgumentException("Field \"condition\" is not a json object or a string!");
	}

	private static ConditionType getConditionType(JsonObject json) {
		String type = GsonHelper.getAsString(json, "condition_type", "mod_load");
		return switch (type) {
			case "mod", "mod_load" -> ConditionType.MOD_LOAD;
			default -> throw new IllegalArgumentException("Unknown condition type value: %s!".formatted(type));
		};
	}

	private static String getValue(JsonObject json, String... possibleKeys) {
		for(String key: possibleKeys) {
			if(json.has(key)) {
				return json.get(key).getAsString();
			}
		}
		throw new IllegalArgumentException("Field \"value\" is not present!");
	}

	enum ConditionType {
		MOD_LOAD
	}
}
