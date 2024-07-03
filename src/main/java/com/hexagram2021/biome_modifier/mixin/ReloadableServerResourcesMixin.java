package com.hexagram2021.biome_modifier.mixin;

import com.hexagram2021.biome_modifier.common.manager.BiomeModifierManager;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	public void biome_modifier$constructor(RegistryAccess.Frozen frozen, FeatureFlagSet featureFlagSet, Commands.CommandSelection commandSelection, int level, CallbackInfo ci) {
		ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new BiomeModifierManager(frozen));
	}
}
