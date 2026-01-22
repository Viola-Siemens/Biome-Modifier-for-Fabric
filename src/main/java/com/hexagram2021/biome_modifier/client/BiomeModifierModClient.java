package com.hexagram2021.biome_modifier.client;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import com.hexagram2021.biome_modifier.network.ClientboundBiomeSpecialEffectsPayload;
import com.hexagram2021.biome_modifier.network.ServerboundRequestBiomeSpecialEffectsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class BiomeModifierModClient implements ClientModInitializer {
	@SuppressWarnings({"ConstantValue", "java:S1905"})
	@Override
	public void onInitializeClient() {
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			try {
				Registry<Biome> biomeRegistry = handler.registryAccess().registryOrThrow(Registries.BIOME);
				for (ResourceKey<Biome> biomeKey: biomeRegistry.registryKeySet()) {
					if((Object)biomeRegistry.get(biomeKey) instanceof IModifiableBiome modifiableBiome && !modifiableBiome.biome_modifier$isModified()) {
						ClientPlayNetworking.send(new ServerboundRequestBiomeSpecialEffectsPayload(biomeKey.location()));
					}
				}
			} catch (Exception e) {
				BMLogger.error("Error when sending request for biome special effects: ", e);
			}
		});

		ClientPlayNetworking.registerGlobalReceiver(ClientboundBiomeSpecialEffectsPayload.PAYLOAD_TYPE, (payload, context) -> {
			RegistryAccess registryAccess = context.player().registryAccess();
			if((Object)registryAccess.registryOrThrow(Registries.BIOME).get(payload.biome()) instanceof IModifiableBiome modifiableBiome && !modifiableBiome.biome_modifier$isModified()) {
				IModifiableBiome.BiomeModificationParametersList list = modifiableBiome.biome_modifier$getModificationParametersList(registryAccess);
				list.setSpecialEffects(payload.effects());
				modifiableBiome.biome_modifier$modify(list);
			}
		});
	}
}
