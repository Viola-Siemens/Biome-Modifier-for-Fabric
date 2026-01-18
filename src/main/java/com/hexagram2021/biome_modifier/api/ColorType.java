package com.hexagram2021.biome_modifier.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ColorType(byte r, byte g, byte b) {
	public static final MapCodec<ColorType> CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					Codec.BYTE.fieldOf("r").forGetter(ColorType::r),
					Codec.BYTE.fieldOf("g").forGetter(ColorType::g),
					Codec.BYTE.fieldOf("b").forGetter(ColorType::b)
			).apply(instance, ColorType::new)
	);

	@SuppressWarnings("unused")
	public ColorType(int color) {
		this((byte)((color >> 16) & 0xff), (byte)((color >> 8) & 0xff), (byte)(color & 0xff));
	}

	public int get() {
		return (this.r * 256 + this.g) * 256 + this.b;
	}
}
