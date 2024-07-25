package com.hexagram2021.biome_modifier.api;

import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;

public interface IModifiableNoiseGenerator extends IModifiableApi<IModifiableNoiseGenerator.NoiseGeneratorModificationParametersList> {
	NoiseGeneratorModificationParametersList biome_modifier$getModificationParametersList(RegistryAccess registryAccess);
	void biome_modifier$modify(NoiseGeneratorModificationParametersList list);

	final class NoiseGeneratorModificationParametersList implements IErrorHandlerParametersList {
		final RegistryAccess registryAccess;
		@Nullable
		final ResourceLocation noiseGeneratorId;
		int error = 0;

		//noiseSettings
		BlockState defaultBlock;
		BlockState defaultFluid;
		int seaLevel;
		boolean aquifersEnabled;
		boolean oreVeinsEnabled;

		public NoiseGeneratorModificationParametersList(RegistryAccess registryAccess, NoiseGeneratorSettings noiseGenerator) {
			this.registryAccess = registryAccess;
			this.noiseGeneratorId = registryAccess.registryOrThrow(Registries.NOISE_SETTINGS).getKey(noiseGenerator);
			this.defaultBlock = noiseGenerator.defaultBlock();
			this.defaultFluid = noiseGenerator.defaultFluid();
			this.seaLevel = noiseGenerator.seaLevel();
			this.aquifersEnabled = noiseGenerator.aquifersEnabled();
			this.oreVeinsEnabled = noiseGenerator.oreVeinsEnabled();
		}

		@Override
		public boolean hasError() {
			return this.error > 0;
		}
		@Override
		public int errorCount() {
			return this.error;
		}

		public BlockState defaultBlock() {
			return this.defaultBlock;
		}
		public void setDefaultBlock(BlockState defaultBlock) {
			this.defaultBlock = defaultBlock;
		}

		public BlockState defaultFluid() {
			return this.defaultFluid;
		}
		public void setDefaultFluid(BlockState defaultFluid) {
			this.defaultFluid = defaultFluid;
		}

		public int seaLevel() {
			return this.seaLevel;
		}
		public void setSeaLevel(int seaLevel) {
			this.seaLevel = seaLevel;
		}

		public boolean aquifersEnabled() {
			return this.aquifersEnabled;
		}
		public void setAquifersEnabled(boolean aquifersEnabled) {
			this.aquifersEnabled = aquifersEnabled;
		}

		public boolean oreVeinsEnabled() {
			return this.oreVeinsEnabled;
		}
		public void setOreVeinsEnabled(boolean oreVeinsEnabled) {
			this.oreVeinsEnabled = oreVeinsEnabled;
		}

		@ApiStatus.Internal
		private void sendFirstMessage() {
			if(!this.hasError()) {
				BMLogger.info("Following problems occur when modifying noise generator %s.".formatted(this.noiseGeneratorId));
			}
			this.error += 1;
		}

		@Override
		@ApiStatus.Internal
		public void error(Throwable e) {
			this.sendFirstMessage();
			BMLogger.error("Unexpected error occurs. This noise generator modifier will be ignored. Don't report to Biome Modifier, report to the datapack.\n", e);
		}
	}
}
