package com.hexagram2021.biome_modifier.mixin;

import com.hexagram2021.biome_modifier.api.IModifiableNoiseGenerator;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@SuppressWarnings({"java:S100", "java:S116"})
@Mixin(value = NoiseGeneratorSettings.class, priority = 53639)
public class NoiseGeneratorSettingsMixin implements IModifiableNoiseGenerator {
	@Unique
	private boolean biome_modifier$isModified = false;

	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	BlockState biome_modifier$defaultBlock;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	BlockState biome_modifier$defaultFluid;
	@Unique
	int biome_modifier$seaLevel;
	@Unique
	boolean biome_modifier$aquifersEnabled;
	@Unique
	boolean biome_modifier$oreVeinsEnabled;

	@SuppressWarnings("java:S107")
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	public void biome_modifier$constructor(NoiseSettings noiseSettings, BlockState defaultBlock, BlockState defaultFluid, NoiseRouter noiseRouter,
										   SurfaceRules.RuleSource surfaceRule, List<Climate.ParameterPoint> spawnTarget, int seaLevel,
										   boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource,
										   CallbackInfo ci) {
		this.biome_modifier$defaultBlock = defaultBlock;
		this.biome_modifier$defaultFluid = defaultFluid;
		this.biome_modifier$seaLevel = seaLevel;
		this.biome_modifier$aquifersEnabled = aquifersEnabled;
		this.biome_modifier$oreVeinsEnabled = oreVeinsEnabled;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified default block.
	 */
	@Redirect(method = {"defaultBlock"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;defaultBlock:Lnet/minecraft/world/level/block/state/BlockState;", opcode = Opcodes.GETFIELD))
	private BlockState biome_modifier$modifiedDefaultBlock(NoiseGeneratorSettings instance) {
		return this.biome_modifier$defaultBlock;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified default fluid.
	 */
	@Redirect(method = {"defaultFluid"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;defaultFluid:Lnet/minecraft/world/level/block/state/BlockState;", opcode = Opcodes.GETFIELD))
	private BlockState biome_modifier$modifiedDefaultFluid(NoiseGeneratorSettings instance) {
		return this.biome_modifier$defaultFluid;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified sea level.
	 */
	@Redirect(method = {"seaLevel"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;seaLevel:I", opcode = Opcodes.GETFIELD))
	private int biome_modifier$modifiedSeaLevel(NoiseGeneratorSettings instance) {
		return this.biome_modifier$seaLevel;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified sea level.
	 */
	@Redirect(method = {"aquifersEnabled", "isAquifersEnabled"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;aquifersEnabled:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedAquifersEnabled(NoiseGeneratorSettings instance) {
		return this.biome_modifier$aquifersEnabled;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified sea level.
	 */
	@Redirect(method = {"oreVeinsEnabled"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;oreVeinsEnabled:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedOreVeinsEnabled(NoiseGeneratorSettings instance) {
		return this.biome_modifier$oreVeinsEnabled;
	}

	@Override
	public NoiseGeneratorModificationParametersList biome_modifier$getModificationParametersList(RegistryAccess registryAccess) {
		return new NoiseGeneratorModificationParametersList(registryAccess, (NoiseGeneratorSettings)(Object)this);
	}

	@Override
	public void biome_modifier$modify(NoiseGeneratorModificationParametersList list) {
		if(this.biome_modifier$isModified) {
			throw new IllegalStateException("Noise generator has already been modified!");
		}
		this.biome_modifier$isModified = true;

		this.biome_modifier$defaultBlock = list.defaultBlock();
		this.biome_modifier$defaultFluid = list.defaultFluid();
		this.biome_modifier$seaLevel = list.seaLevel();
		this.biome_modifier$aquifersEnabled = list.aquifersEnabled();
		this.biome_modifier$oreVeinsEnabled = list.oreVeinsEnabled();
	}
}
