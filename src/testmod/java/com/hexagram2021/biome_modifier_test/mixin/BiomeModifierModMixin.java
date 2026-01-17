package com.hexagram2021.biome_modifier_test.mixin;

import com.hexagram2021.biome_modifier.BiomeModifierMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("java:S1118")
@Mixin(value = BiomeModifierMod.class, remap = false)
public class BiomeModifierModMixin {
	@WrapOperation(method = "genericallyApplyModifiersFromManager", at = @At(value = "INVOKE", target = "Lcom/hexagram2021/biome_modifier/common/utils/BMLogger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"))
	private static void onError(String message, Throwable e, Operation<Void> operation) {
		throw new IllegalStateException(message, e);
	}
}
