package com.hexagram2021.biome_modifier.api.modifiers;

import com.hexagram2021.biome_modifier.api.IErrorHandlerParametersList;
import net.minecraft.core.Holder;

public interface IModifier<T, L extends IErrorHandlerParametersList> {
	boolean canModify(Holder<T> object);
	void modify(L list);
}
