package com.hexagram2021.biome_modifier.api.modifiers.biome.impl;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.biome.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.biome.IBiomeModifierType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public class NoneBiomeModifier implements IBiomeModifier {
	public static final MapCodec<NoneBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.point(new NoneBiomeModifier()));

	protected NoneBiomeModifier() {
	}

	@Override
	public boolean canModify(Holder<Biome> biome) {
		return false;
	}
	@Override
	public void modify(IModifiableBiome.BiomeModificationParametersList list) {
		// Do nothing
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.NONE;
	}
}
