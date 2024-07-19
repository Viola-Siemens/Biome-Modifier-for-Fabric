package com.hexagram2021.biome_modifier.api.modifiers.dimension.impl;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.AbstractDimensionModifier;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.DimensionModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.dimension.IDimensionModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.dimension.DimensionType;

public class SkyLight extends AbstractDimensionModifier {
	public static final Codec<SkyLight> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					DIMENSION_LIST_CODEC.fieldOf("dimension_types").forGetter(SkyLight::dimensionTypes),
					Codec.INT.optionalFieldOf("priority", 1000).forGetter(SkyLight::priority),
					Codec.BOOL.fieldOf("has_skylight").forGetter(SkyLight::skyLight)
			).apply(instance, SkyLight::new)
	);

	final boolean skyLight;

	public SkyLight(HolderSet<DimensionType> dimensionTypes, int priority, boolean skyLight) {
		super(dimensionTypes, priority);
		this.skyLight = skyLight;
	}

	@Override
	public void modifyParametersList(IModifiableDimension.DimensionModificationParametersList list) throws RuntimeException {
		list.setHasSkyLight(this.skyLight);
	}

	@Override
	public IDimensionModifierType type() {
		return DimensionModifierTypes.SKY_LIGHT;
	}

	public boolean skyLight() {
		return this.skyLight;
	}
}
