package com.hexagram2021.biome_modifier.common.utils;

import com.google.common.base.Supplier;

import javax.annotation.Nullable;

public class NonSerializableMemoizingInvalidatableSupplier<T> implements Supplier<T>, Invalidatable {
	@SuppressWarnings("java:S1948")
	private final Supplier<T> delegate;
	private transient volatile boolean initialized;
	@Nullable
	private transient T value;

	public NonSerializableMemoizingInvalidatableSupplier(Supplier<T> delegate) {
		this.delegate = delegate;
	}

	@Override @Nullable
	public T get() {
		if (!this.initialized) {
			synchronized (this) {
				if (!this.initialized) {
					T t = this.delegate.get();
					this.value = t;
					this.initialized = true;
					return t;
				}
			}
		}
		return this.value;
	}

	@Override
	public String toString() {
		return "NonSerializableMemoizingInvalidatableSupplier(" +
				(this.initialized ? "<supplier that returned " + this.value + ">" : this.delegate) +
				")";
	}

	@Override
	public void biome_modifier$invalidate() {
		if(this.initialized) {
			synchronized (this) {
				if(this.initialized) {
					this.initialized = false;
					this.value = null;
				}
			}
		}
	}
}
