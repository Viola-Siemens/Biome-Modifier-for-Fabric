package com.hexagram2021.biome_modifier.api.modifiers.dimension;

import com.hexagram2021.biome_modifier.api.modifiers.dimension.impl.NoneDimensionModifier;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.resources.ResourceLocation;

public final class DimensionModifierTypes {
	public static final IDimensionModifierType NONE = register(DimensionModifierNames.NONE, () -> NoneDimensionModifier.CODEC);

	private DimensionModifierTypes() {
	}

	public static IDimensionModifierType register(ResourceLocation id, IDimensionModifierType type) {
		IDimensionModifierType.register(id, type);
		return type;
	}

	public static void init() {
		BMLogger.info("Loaded %d biome modifier types.".formatted(IDimensionModifierType.DIMENSION_MODIFIER_TYPES.size()));
	}
}
