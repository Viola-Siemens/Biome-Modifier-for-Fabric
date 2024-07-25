package com.hexagram2021.biome_modifier.api;

public interface IErrorHandlerParametersList {
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	boolean hasError();
	int errorCount();

	void error(Throwable e);
}
