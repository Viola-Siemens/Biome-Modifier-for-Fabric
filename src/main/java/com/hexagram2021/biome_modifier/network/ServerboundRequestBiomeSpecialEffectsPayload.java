package com.hexagram2021.biome_modifier.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public record ServerboundRequestBiomeSpecialEffectsPayload(ResourceLocation biome) implements CustomPacketPayload {
	public static final ResourceLocation REQUEST_BIOME_SPECIAL_EFFECTS = ResourceLocation.fromNamespaceAndPath(MODID, "request_biome_special_effects");

	public static final StreamCodec<FriendlyByteBuf, ServerboundRequestBiomeSpecialEffectsPayload> STREAM_CODEC = CustomPacketPayload.codec(
			ServerboundRequestBiomeSpecialEffectsPayload::write, ServerboundRequestBiomeSpecialEffectsPayload::new
	);
	public static final Type<ServerboundRequestBiomeSpecialEffectsPayload> PAYLOAD_TYPE = new Type<>(REQUEST_BIOME_SPECIAL_EFFECTS);

	public ServerboundRequestBiomeSpecialEffectsPayload(FriendlyByteBuf buf) {
		this(buf.readResourceLocation());
	}

	private void write(FriendlyByteBuf buf) {
		buf.writeResourceLocation(this.biome);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return PAYLOAD_TYPE;
	}
}
