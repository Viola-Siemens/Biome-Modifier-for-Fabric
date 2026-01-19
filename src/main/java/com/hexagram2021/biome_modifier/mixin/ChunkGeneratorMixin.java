package com.hexagram2021.biome_modifier.mixin;

import com.hexagram2021.biome_modifier.common.utils.Invalidatable;
import com.hexagram2021.biome_modifier.common.utils.MemoizingInvalidatableSupplier;
import com.hexagram2021.biome_modifier.common.utils.NonSerializableMemoizingInvalidatableSupplier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"java:S100", "java:S1118"})
@Mixin(value = ChunkGenerator.class, priority = 53639)
public class ChunkGeneratorMixin implements Invalidatable {
	@Shadow @Final
	private Supplier<List<FeatureSorter.StepFeatureData>> featuresPerStep;

	@WrapOperation(method = "<init>(Lnet/minecraft/world/level/biome/BiomeSource;Ljava/util/function/Function;)V", at = @At(value = "INVOKE", target = "Lcom/google/common/base/Suppliers;memoize(Lcom/google/common/base/Supplier;)Lcom/google/common/base/Supplier;", remap = false))
	private static <T> com.google.common.base.Supplier<T> biome_modifier$redirectMemoize(com.google.common.base.Supplier<T> instance, Operation<com.google.common.base.Supplier<T>> original) {
		return instance instanceof Serializable ? new MemoizingInvalidatableSupplier<>(instance) : new NonSerializableMemoizingInvalidatableSupplier<>(instance);
	}

	@Override
	public void biome_modifier$invalidate() {
		if(this.featuresPerStep instanceof Invalidatable invalidatable) {
			invalidatable.biome_modifier$invalidate();
		}
	}
}
