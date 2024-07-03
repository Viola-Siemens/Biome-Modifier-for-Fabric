package com.hexagram2021.biome_modifier.api.modifiers;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;

public abstract class AbstractBiomeModifier implements IBiomeModifier {
	final HolderSet<Biome> biomes;
	final int priority;

	protected AbstractBiomeModifier(HolderSet<Biome> biomes, int priority) {
		this.biomes = biomes;
		this.priority = priority;
	}

	@Override
	public void modify(IModifiableBiome.BiomeModificationParametersList list) {
		try {
			this.modifyParametersList(list);
		} catch(RuntimeException e) {
			list.error(e);
		}
	}

	@Override
	public boolean canModify(Biome biome) {
		return this.biomes.contains(Holder.direct(biome));
	}
	public abstract void modifyParametersList(IModifiableBiome.BiomeModificationParametersList list) throws RuntimeException;

	public HolderSet<Biome> biomes() {
		return this.biomes;
	}
	public int priority() {
		return this.priority;
	}
}
