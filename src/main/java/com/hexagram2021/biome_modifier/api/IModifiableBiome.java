package com.hexagram2021.biome_modifier.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hexagram2021.biome_modifier.common.utils.BMLogger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public interface IModifiableBiome extends IModifiableApi<IModifiableBiome.BiomeModificationParametersList> {
	BiomeModificationParametersList biome_modifier$getModificationParametersList(RegistryAccess registryAccess);
	void biome_modifier$modify(BiomeModificationParametersList list);

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	final class BiomeModificationParametersList implements IErrorHandlerParametersList {
		final RegistryAccess registryAccess;
		@Nullable
		final ResourceLocation biomeId;
		int error = 0;
		//climateSettings
		boolean hasPrecipitation;
		float temperature;
		Biome.TemperatureModifier temperatureModifier;
		float downfall;
		//specialEffects
		int fogColor;
		int waterColor;
		int waterFogColor;
		int skyColor;
		Optional<Integer> foliageColorOverride;
		Optional<Integer> grassColorOverride;
		BiomeSpecialEffects.GrassColorModifier grassColorModifier;
		Optional<AmbientParticleSettings> ambientParticleSettings;
		Optional<Holder<SoundEvent>> ambientLoopSoundEvent;
		Optional<AmbientMoodSettings> ambientMoodSettings;
		Optional<AmbientAdditionsSettings> ambientAdditionsSettings;
		Optional<Music> backgroundMusic;
		//generationSettings
		final Map<GenerationStep.Carving, List<Holder<ConfiguredWorldCarver<?>>>> carvers;
		final List<List<Holder<PlacedFeature>>> features;
		//mobSettings
		float creatureGenerationProbability;
		final Map<MobCategory, List<MobSpawnSettings.SpawnerData>> spawners;
		final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> mobSpawnCosts;

		public BiomeModificationParametersList(RegistryAccess registryAccess, @Nullable ResourceLocation biomeId,
											   Biome.ClimateSettings climateSettings, BiomeSpecialEffects specialEffects,
											   BiomeGenerationSettings generationSettings, MobSpawnSettings mobSettings) {
			this.registryAccess = registryAccess;
			this.biomeId = biomeId;
			this.hasPrecipitation = climateSettings.hasPrecipitation();
			this.temperature = climateSettings.temperature();
			this.temperatureModifier = climateSettings.temperatureModifier();
			this.downfall = climateSettings.downfall();
			this.fogColor = specialEffects.getFogColor();
			this.waterColor = specialEffects.getWaterColor();
			this.waterFogColor = specialEffects.getWaterFogColor();
			this.skyColor = specialEffects.getSkyColor();
			this.foliageColorOverride = specialEffects.getFoliageColorOverride();
			this.grassColorOverride = specialEffects.getGrassColorOverride();
			this.grassColorModifier = specialEffects.getGrassColorModifier();
			this.ambientParticleSettings = specialEffects.getAmbientParticleSettings();
			this.ambientLoopSoundEvent = specialEffects.getAmbientLoopSoundEvent();
			this.ambientMoodSettings = specialEffects.getAmbientMoodSettings();
			this.ambientAdditionsSettings = specialEffects.getAmbientAdditionsSettings();
			this.backgroundMusic = specialEffects.getBackgroundMusic();
			this.carvers = Maps.newHashMap();
			generationSettings.carvers.forEach((carving, holders) -> this.carvers.put(carving, Lists.newArrayList(holders)));
			this.features = generationSettings.features().stream().map(Lists::newArrayList).collect(Collectors.toList());
			this.creatureGenerationProbability = mobSettings.getCreatureProbability();
			this.spawners = Maps.newHashMap();
			mobSettings.spawners.forEach((mobCategory, spawnerDataList) -> this.spawners.put(mobCategory, Lists.newArrayList(spawnerDataList.unwrap())));
			this.mobSpawnCosts = Maps.newHashMap(mobSettings.mobSpawnCosts);
		}

		public boolean hasPrecipitation() {
			return this.hasPrecipitation;
		}
		public void setHasPrecipitation(boolean hasPrecipitation) {
			this.hasPrecipitation = hasPrecipitation;
		}

		public float temperature() {
			return this.temperature;
		}
		public void setTemperature(float temperature) {
			this.temperature = temperature;
		}

		public Biome.TemperatureModifier temperatureModifier() {
			return this.temperatureModifier;
		}
		public void setTemperatureModifier(Biome.TemperatureModifier temperatureModifier) {
			this.temperatureModifier = temperatureModifier;
		}

		public float downfall() {
			return this.downfall;
		}
		public void setDownfall(float downfall) {
			this.downfall = downfall;
		}

		public int fogColor() {
			return this.fogColor;
		}
		public void setFogColor(int fogColor) {
			this.fogColor = fogColor;
		}

		public int waterColor() {
			return this.waterColor;
		}
		public void setWaterColor(int waterColor) {
			this.waterColor = waterColor;
		}

		public int waterFogColor() {
			return this.waterFogColor;
		}
		public void setWaterFogColor(int waterFogColor) {
			this.waterFogColor = waterFogColor;
		}

		public int skyColor() {
			return this.skyColor;
		}
		public void setSkyColor(int skyColor) {
			this.skyColor = skyColor;
		}

		public Optional<Integer> foliageColorOverride() {
			return this.foliageColorOverride;
		}
		public void setFoliageColorOverride(Optional<Integer> foliageColorOverride) {
			this.foliageColorOverride = foliageColorOverride;
		}

		public Optional<Integer> grassColorOverride() {
			return this.grassColorOverride;
		}
		public void setGrassColorOverride(Optional<Integer> grassColorOverride) {
			this.grassColorOverride = grassColorOverride;
		}

		public BiomeSpecialEffects.GrassColorModifier grassColorModifier() {
			return this.grassColorModifier;
		}
		public void setGrassColorModifier(BiomeSpecialEffects.GrassColorModifier grassColorModifier) {
			this.grassColorModifier = grassColorModifier;
		}

		public Optional<AmbientParticleSettings> ambientParticleSettings() {
			return this.ambientParticleSettings;
		}
		public void setAmbientParticleSettings(Optional<AmbientParticleSettings> ambientParticleSettings) {
			this.ambientParticleSettings = ambientParticleSettings;
		}

		public Optional<Holder<SoundEvent>> ambientLoopSoundEvent() {
			return this.ambientLoopSoundEvent;
		}
		public void setAmbientLoopSoundEvent(Optional<Holder<SoundEvent>> ambientLoopSoundEvent) {
			this.ambientLoopSoundEvent = ambientLoopSoundEvent;
		}

		public Optional<AmbientMoodSettings> ambientMoodSettings() {
			return this.ambientMoodSettings;
		}
		public void setAmbientMoodSettings(Optional<AmbientMoodSettings> ambientMoodSettings) {
			this.ambientMoodSettings = ambientMoodSettings;
		}

		public Optional<AmbientAdditionsSettings> ambientAdditionsSettings() {
			return this.ambientAdditionsSettings;
		}
		public void setAmbientAdditionsSettings(Optional<AmbientAdditionsSettings> ambientAdditionsSettings) {
			this.ambientAdditionsSettings = ambientAdditionsSettings;
		}

		public Optional<Music> backgroundMusic() {
			return this.backgroundMusic;
		}
		public void setBackgroundMusic(Optional<Music> backgroundMusic) {
			this.backgroundMusic = backgroundMusic;
		}

		public Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> carvers() {
			ImmutableMap.Builder<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> builder = ImmutableMap.builder();
			this.carvers.forEach((carving, holders) -> builder.put(carving, HolderSet.direct(holders)));
			return builder.build();
		}
		public void addCarvers(GenerationStep.Carving step, List<Holder<ConfiguredWorldCarver<?>>> carvers) {
			if(this.carvers.containsKey(step)) {
				this.carvers.get(step).addAll(carvers);
			} else {
				this.carvers.put(step, Lists.newArrayList(carvers));
			}
		}
		public void removeCarvers(GenerationStep.Carving step, List<Holder<ConfiguredWorldCarver<?>>> carvers) {
			if(!this.carvers.containsKey(step)) {
				this.warnMissing("Step", step, "carvers");
				return;
			}
			List<Holder<ConfiguredWorldCarver<?>>> list = this.carvers.get(step);
			for(Holder<ConfiguredWorldCarver<?>> holder: carvers) {
				if(!list.remove(holder)) {
					Registry<ConfiguredWorldCarver<?>> registry = this.registryAccess.registryOrThrow(Registries.CONFIGURED_CARVER);
					this.warnMissing("Carver", registry.getKey(holder.value()), "carvers");
				}
			}
		}

		public List<HolderSet<PlacedFeature>> features() {
			return ImmutableList.copyOf(this.features.stream().map(HolderSet::direct).collect(Collectors.toList()));
		}
		public void addFeatures(GenerationStep.Decoration step, List<Holder<PlacedFeature>> features) {
			int stepOrdinal = step.ordinal();
			while(this.features.size() <= stepOrdinal) {
				this.features.add(Lists.newArrayList());
			}
			this.features.get(stepOrdinal).addAll(features);
		}
		public void removeFeatures(GenerationStep.Decoration step, List<Holder<PlacedFeature>> features) {
			int stepOrdinal = step.ordinal();
			if(this.features.size() <= stepOrdinal) {
				this.warnMissing("Step", step, "features");
				return;
			}
			List<Holder<PlacedFeature>> list = this.features.get(stepOrdinal);
			for(Holder<PlacedFeature> holder: features) {
				if(!list.remove(holder)) {
					Registry<PlacedFeature> registry = this.registryAccess.registryOrThrow(Registries.PLACED_FEATURE);
					this.warnMissing("Feature", registry.getKey(holder.value()), "features");
				}
			}
		}

		public float creatureGenerationProbability() {
			return this.creatureGenerationProbability;
		}
		public void setCreatureGenerationProbability(float creatureGenerationProbability) {
			this.creatureGenerationProbability = creatureGenerationProbability;
		}

		public Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> spawners() {
			ImmutableMap.Builder<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> builder = ImmutableMap.builder();
			this.spawners.forEach((mobCategory, spawnerDatas) -> builder.put(mobCategory, WeightedRandomList.create(spawnerDatas)));
			return builder.build();
		}
		public void addSpawners(List<MobSpawnSettings.SpawnerData> list) {
			list.forEach(spawnerData -> {
				List<MobSpawnSettings.SpawnerData> pool = this.spawners.get(spawnerData.type.getCategory());
				if(pool.stream().anyMatch(spawnerData1 -> Objects.equals(spawnerData.type, spawnerData1.type))) {
					Registry<EntityType<?>> registry = this.registryAccess.registryOrThrow(Registries.ENTITY_TYPE);
					this.warnExisting("Spawner", registry.getKey(spawnerData.type), "spawners");
				} else {
					pool.add(spawnerData);
				}
			});
		}
		public void removeSpawners(List<? extends EntityType<?>> list) {
			list.forEach(type -> {
				if(!this.spawners.get(type.getCategory()).removeIf(spawnerData -> Objects.equals(spawnerData.type, type))) {
					Registry<EntityType<?>> registry = this.registryAccess.registryOrThrow(Registries.ENTITY_TYPE);
					this.warnMissing("Spawner", registry.getKey(type), "spawners");
				}
			});
		}

		public Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> mobSpawnCosts() {
			return ImmutableMap.copyOf(this.mobSpawnCosts);
		}
		public void addSpawnerCosts(Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> costs) {
			costs.forEach((entityType, mobSpawnCost) -> {
				if(this.mobSpawnCosts.put(entityType, mobSpawnCost) != null) {
					Registry<EntityType<?>> registry = this.registryAccess.registryOrThrow(Registries.ENTITY_TYPE);
					this.warnExisting("Entity type", registry.getKey(entityType), "spawner costs");
				}
			});
		}
		public void removeSpawnerCosts(List<? extends EntityType<?>> types) {
			types.forEach(type -> {
				if(this.mobSpawnCosts.remove(type) == null) {
					Registry<EntityType<?>> registry = this.registryAccess.registryOrThrow(Registries.ENTITY_TYPE);
					this.warnMissing("Entity type", registry.getKey(type), "spawner costs");
				}
			});
		}

		@Override
		public boolean hasError() {
			return this.error > 0;
		}
		@Override
		public int errorCount() {
			return this.error;
		}

		@ApiStatus.Internal
		private void sendFirstMessage() {
			if(!this.hasError()) {
				BMLogger.info("Following problems occur when modifying biome %s.".formatted(this.biomeId));
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

		@Override
		@ApiStatus.Internal
		public void error(Throwable e) {
			this.sendFirstMessage();
			BMLogger.error("Unexpected error occurs. This biome modifier will be ignored. Don't report to Biome Modifier, report to the datapack.\n", e);
		}
	}
}
