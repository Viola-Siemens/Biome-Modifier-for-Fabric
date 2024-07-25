package com.hexagram2021.biome_modifier.mixin;

import com.hexagram2021.biome_modifier.api.IModifiableBiome;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Biome.class, priority = 53639)
public class BiomeMixin implements IModifiableBiome {
	@Unique
	private boolean biome_modifier$isModified = false;

	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	private Biome.ClimateSettings biome_modifier$climateSettings;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	private BiomeSpecialEffects biome_modifier$specialEffects;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	private BiomeGenerationSettings biome_modifier$generationSettings;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	private MobSpawnSettings biome_modifier$mobSettings;

	@Inject(method = "<init>", at = @At(value = "TAIL"))
	public void biome_modifier$constructor(Biome.ClimateSettings climateSettings, BiomeSpecialEffects biomeSpecialEffects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnSettings mobSpawnSettings, CallbackInfo ci) {
		this.biome_modifier$climateSettings = climateSettings;
		this.biome_modifier$specialEffects = biomeSpecialEffects;
		this.biome_modifier$generationSettings = biomeGenerationSettings;
		this.biome_modifier$mobSettings = mobSpawnSettings;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified climate settings.
	 */
	@Redirect(method = {
			"hasPrecipitation",
			"getHeightAdjustedTemperature",
			"getGrassColorFromTexture",
			"getFoliageColorFromTexture",
			"getBaseTemperature"
	}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/biome/Biome;climateSettings:Lnet/minecraft/world/level/biome/Biome$ClimateSettings;", opcode = Opcodes.GETFIELD))
	private Biome.ClimateSettings biome_modifier$modifiedClimateSettings(Biome instance) {
		return this.biome_modifier$climateSettings;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified special effects.
	 */
	@Redirect(method = {
			"getSkyColor",
			"getFogColor",
			"getGrassColor",
			"getFoliageColor",
			"getSpecialEffects",
			"getWaterColor",
			"getWaterFogColor",
			"getAmbientParticle",
			"getAmbientLoop",
			"getAmbientMood",
			"getAmbientAdditions",
			"getBackgroundMusic"
	}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/biome/Biome;specialEffects:Lnet/minecraft/world/level/biome/BiomeSpecialEffects;", opcode = Opcodes.GETFIELD))
	private BiomeSpecialEffects biome_modifier$modifiedSpecialEffects(Biome instance) {
		return this.biome_modifier$specialEffects;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified mob spawn settings.
	 */
	@Redirect(method = "getMobSettings", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/biome/Biome;mobSettings:Lnet/minecraft/world/level/biome/MobSpawnSettings;", opcode = Opcodes.GETFIELD))
	private MobSpawnSettings biome_modifier$modifiedMobSettings(Biome instance) {
		return this.biome_modifier$mobSettings;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified mob spawn settings.
	 */
	@Redirect(method = "getGenerationSettings", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/biome/Biome;generationSettings:Lnet/minecraft/world/level/biome/BiomeGenerationSettings;", opcode = Opcodes.GETFIELD))
	private BiomeGenerationSettings biome_modifier$modifiedGenerationSettings(Biome instance) {
		return this.biome_modifier$generationSettings;
	}

	@Override
	public BiomeModificationParametersList biome_modifier$getModificationParametersList(RegistryAccess registryAccess) {
		return new BiomeModificationParametersList(
				registryAccess,
				registryAccess.registryOrThrow(Registries.BIOME).getKey((Biome)(Object)this),
				this.biome_modifier$climateSettings,
				this.biome_modifier$specialEffects,
				this.biome_modifier$generationSettings,
				this.biome_modifier$mobSettings
		);
	}

	@Override
	public void biome_modifier$modify(BiomeModificationParametersList list) {
		if(this.biome_modifier$isModified) {
			throw new IllegalStateException("Biome has already been modified!");
		}
		this.biome_modifier$isModified = true;

		this.biome_modifier$climateSettings = new Biome.ClimateSettings(
				list.hasPrecipitation(), list.temperature(), list.temperatureModifier(), list.downfall()
		);
		this.biome_modifier$specialEffects = new BiomeSpecialEffects(
				list.fogColor(), list.waterColor(), list.waterFogColor(), list.skyColor(),
				list.foliageColorOverride(), list.grassColorOverride(), list.grassColorModifier(),
				list.ambientParticleSettings(), list.ambientLoopSoundEvent(), list.ambientMoodSettings(),
				list.ambientAdditionsSettings(), list.backgroundMusic()
		);
		this.biome_modifier$generationSettings = new BiomeGenerationSettings(list.carvers(), list.features());
		this.biome_modifier$mobSettings = new MobSpawnSettings(list.creatureGenerationProbability(), list.spawners(), list.mobSpawnCosts());
	}
}
