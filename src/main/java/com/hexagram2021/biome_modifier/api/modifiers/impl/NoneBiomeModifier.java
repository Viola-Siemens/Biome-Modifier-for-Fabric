package com.hexagram2021.biome_modifier.api.modifiers.impl;

import com.hexagram2021.biome_modifier.api.BiomeModifierTypes;
import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifier;
import com.hexagram2021.biome_modifier.api.modifiers.IBiomeModifierType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public class NoneBiomeModifier implements IBiomeModifier {
	public static final Codec<NoneBiomeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.point(new NoneBiomeModifier()));

	protected NoneBiomeModifier() {
	}

	@Override
	public boolean canModify(Holder<Biome> biome) {
		return false;
	}
	@Override
	public void modify(IModifiableBiome.BiomeModificationParametersList list) {
	}

	@Override
	public IBiomeModifierType type() {
		return BiomeModifierTypes.NONE;
	}
}
