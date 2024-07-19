package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class AmbientLight extends AbstractDimensionModifier {
	public static final Codec<AmbientLight> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(AmbientLight::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(AmbientLight::priority),
					Codec.FLOAT.fieldOf("ambient_light").forGetter(AmbientLight::ambientLight)
			).apply(instance, AmbientLight::new)
	);

	final float ambientLight;

	public AmbientLight(HolderSet<DimensionType> dimensionTypes, int priority, float ambientLight) {
		super(dimensionTypes, priority);
		this.ambientLight = ambientLight;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setAmbientLight(this.ambientLight);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.AMBIENT_LIGHT;
	}

	public float ambientLight() {
		return this.ambientLight;
	}
}
