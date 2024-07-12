package com.hexagram2021.biome_modifier.api;

import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.OptionalLong;

public interface IModifiableDimension {
	DimensionModificationParametersList biome_modifier$getDimensionModificationParametersList(RegistryAccess registryAccess);
	void biome_modifier$modifyDimension(DimensionModificationParametersList list);

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	final class DimensionModificationParametersList {
		final RegistryAccess registryAccess;
		@Nullable
		final ResourceLocation dimensionId;
		int error = 0;

		//DimensionType
		OptionalLong fixedTime;
		boolean hasSkyLight;
		boolean hasCeiling;
		boolean ultraWarm;
		boolean natural;
		double coordinateScale;
		boolean bedWorks;
		boolean respawnAnchorWorks;
		int minY;
		int height;
		int logicalHeight;
		TagKey<Block> infiniburn;
		ResourceLocation effectsLocation;
		float ambientLight;
		DimensionType.MonsterSettings monsterSettings;

		public DimensionModificationParametersList(RegistryAccess registryAccess, DimensionType dimensionType) {
			this.registryAccess = registryAccess;
			this.dimensionId = registryAccess.registryOrThrow(Registries.DIMENSION_TYPE).getKey(dimensionType);
			this.fixedTime = dimensionType.fixedTime();
			this.hasSkyLight = dimensionType.hasSkyLight();
			this.hasCeiling = dimensionType.hasCeiling();
			this.ultraWarm = dimensionType.ultraWarm();
			this.natural = dimensionType.natural();
			this.coordinateScale = dimensionType.coordinateScale();
			this.bedWorks = dimensionType.bedWorks();
			this.respawnAnchorWorks = dimensionType.respawnAnchorWorks();
			this.minY = dimensionType.minY();
			this.height = dimensionType.height();
			this.logicalHeight = dimensionType.logicalHeight();
			this.infiniburn = dimensionType.infiniburn();
			this.effectsLocation = dimensionType.effectsLocation();
			this.ambientLight = dimensionType.ambientLight();
			this.monsterSettings = dimensionType.monsterSettings();
		}

		public OptionalLong fixedTime() {
			return this.fixedTime;
		}
		public void setFixedTime(OptionalLong fixedTime) {
			this.fixedTime = fixedTime;
		}

		public boolean hasSkyLight() {
			return this.hasSkyLight;
		}
		public void setHasSkyLight(boolean hasSkyLight) {
			this.hasSkyLight = hasSkyLight;
		}

		public boolean hasCeiling() {
			return this.hasCeiling;
		}
		public void setHasCeiling(boolean hasCeiling) {
			this.hasCeiling = hasCeiling;
		}

		public boolean ultraWarm() {
			return this.ultraWarm;
		}
		public void setUltraWarm(boolean ultraWarm) {
			this.ultraWarm = ultraWarm;
		}

		public boolean natural() {
			return this.natural;
		}
		public void setNatural(boolean natural) {
			this.natural = natural;
		}

		public double coordinateScale() {
			return this.coordinateScale;
		}
		public void setCoordinateScale(double coordinateScale) {
			this.coordinateScale = coordinateScale;
		}

		public boolean bedWorks() {
			return this.bedWorks;
		}
		public void setBedWorks(boolean bedWorks) {
			this.bedWorks = bedWorks;
		}

		public boolean respawnAnchorWorks() {
			return this.respawnAnchorWorks;
		}
		public void setRespawnAnchorWorks(boolean respawnAnchorWorks) {
			this.respawnAnchorWorks = respawnAnchorWorks;
		}

		public int minY() {
			return this.minY;
		}
		public void setMinY(int minY) {
			this.minY = minY;
		}

		public int height() {
			return this.height;
		}
		public void setHeight(int height) {
			this.height = height;
		}

		public int logicalHeight() {
			return this.logicalHeight;
		}
		public void setLogicalHeight(int logicalHeight) {
			this.logicalHeight = logicalHeight;
		}

		public TagKey<Block> infiniburn() {
			return this.infiniburn;
		}
		public void setInfiniburn(TagKey<Block> infiniburn) {
			this.infiniburn = infiniburn;
		}

		public ResourceLocation effectsLocation() {
			return this.effectsLocation;
		}
		public void setEffectsLocation(ResourceLocation effectsLocation) {
			this.effectsLocation = effectsLocation;
		}

		public float ambientLight() {
			return this.ambientLight;
		}
		public void setAmbientLight(float ambientLight) {
			this.ambientLight = ambientLight;
		}

		public DimensionType.MonsterSettings monsterSettings() {
			return this.monsterSettings;
		}
		public void setMonsterSettings(DimensionType.MonsterSettings monsterSettings) {
			this.monsterSettings = monsterSettings;
		}

		public boolean hasError() {
			return this.error > 0;
		}
		public int errorCount() {
			return this.error;
		}

		@ApiStatus.Internal
		private void sendFirstMessage() {
			if(!this.hasError()) {
				BMLogger.info("Following problems occur when modifying dimension %s.".formatted(this.dimensionId));
			}
			this.error += 1;
		}

		@ApiStatus.Internal
		public void warnExisting(String existing, @Nullable Object object, String lookup) {
			this.sendFirstMessage();
			BMLogger.warn("%s %s already exists in %s.".formatted(existing, object, lookup));
		}

		@ApiStatus.Internal
		public void warnMissing(String missing, @Nullable Object object, String lookup) {
			this.sendFirstMessage();
			BMLogger.warn("%s %s does not exist in %s.".formatted(missing, object, lookup));
		}

		@ApiStatus.Internal
		public void error(ResourceLocation registry, ResourceLocation object) {
			this.sendFirstMessage();
			BMLogger.error("Registry %s does not contain entry %s. This may cause by missing or version-mismatched dependencies.".formatted(registry, object));
		}

		@ApiStatus.Internal
		public void error(Throwable e) {
			this.sendFirstMessage();
			BMLogger.error("Unexpected error occurs. This modifier will be ignored. Don't report to Dimension Modifier, report to the datapack.\n", e);
		}
	}
}
