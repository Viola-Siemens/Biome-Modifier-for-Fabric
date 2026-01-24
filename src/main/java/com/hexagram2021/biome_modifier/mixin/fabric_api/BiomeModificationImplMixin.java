package com.hexagram2021.biome_modifier.mixin.fabric_api;

import net.fabricmc.fabric.impl.biome.modification.BiomeModificationImpl;
import net.minecraft.world.level.dimension.LevelStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"java:S100", "java:S1118"})
@Mixin(value = BiomeModificationImpl.class, remap = false)
public class BiomeModificationImplMixin {
	/**
	 * Skip memoize
	 * @param dimensionOptions dimension options
	 * @param ci callback info
	 * @see com.hexagram2021.biome_modifier.mixin.ChunkGeneratorMixin#biome_modifier$redirectMemoize
	 */
	@Inject(method = "lambda$finalizeWorldGen$6", at = @At(value = "HEAD"), cancellable = true)
	private static void biome_modifier$redirectMemoize(LevelStem dimensionOptions, CallbackInfo ci) {
		ci.cancel();
	}
}
