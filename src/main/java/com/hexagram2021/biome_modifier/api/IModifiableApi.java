package com.hexagram2021.biome_modifier.api;

import net.minecraft.core.RegistryAccess;

public interface IModifiableApi<L extends IErrorHandlerParametersList> {
	L biome_modifier$getModificationParametersList(RegistryAccess registryAccess);
	void biome_modifier$modify(L list);
}
