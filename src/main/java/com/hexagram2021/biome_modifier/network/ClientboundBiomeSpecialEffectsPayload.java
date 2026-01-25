package com.hexagram2021.biome_modifier.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

import static com.hexagram2021.biome_modifier.BiomeModifierMod.MODID;

public record ClientboundBiomeSpecialEffectsPayload(ResourceLocation biome, BiomeSpecialEffects effects) implements CustomPacketPayload {
	public static final ResourceLocation BIOME_SPECIAL_EFFECTS = ResourceLocation.fromNamespaceAndPath(MODID, "biome_special_effects");

	public static final StreamCodec<FriendlyByteBuf, ClientboundBiomeSpecialEffectsPayload> STREAM_CODEC = CustomPacketPayload.codec(
			ClientboundBiomeSpecialEffectsPayload::write, ClientboundBiomeSpecialEffectsPayload::new
	);
	public static final StreamCodec<ByteBuf, BiomeSpecialEffects> BIOME_SPECIAL_EFFECTS_STREAM_CODEC = ByteBufCodecs.fromCodec(BiomeSpecialEffects.CODEC);
	public static final Type<ClientboundBiomeSpecialEffectsPayload> PAYLOAD_TYPE = new Type<>(BIOME_SPECIAL_EFFECTS);

	public ClientboundBiomeSpecialEffectsPayload(FriendlyByteBuf buf) {
		this(buf.readResourceLocation(), BIOME_SPECIAL_EFFECTS_STREAM_CODEC.decode(buf));
	}

	private void write(FriendlyByteBuf buf) {
		buf.writeResourceLocation(this.biome);
		BIOME_SPECIAL_EFFECTS_STREAM_CODEC.encode(buf, this.effects);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return PAYLOAD_TYPE;
	}
}
