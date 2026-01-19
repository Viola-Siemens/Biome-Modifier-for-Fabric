package com.hexagram2021.biome_modifier.mixin;

import com.hexagram2021.biome_modifier.api.IModifiableDimension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalLong;

@SuppressWarnings({"java:S100", "java:S116", "OptionalUsedAsFieldOrParameterType"})
@Mixin(value = DimensionType.class, priority = 53639)
public class DimensionTypeMixin implements IModifiableDimension {
	@Unique
	private boolean biome_modifier$isModified = false;

	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	OptionalLong biome_modifier$fixedTime;
	@Unique
	boolean biome_modifier$hasSkyLight;
	@Unique
	boolean biome_modifier$hasCeiling;
	@Unique
	boolean biome_modifier$ultraWarm;
	@Unique
	boolean biome_modifier$natural;
	@Unique
	double biome_modifier$coordinateScale;
	@Unique
	boolean biome_modifier$bedWorks;
	@Unique
	boolean biome_modifier$respawnAnchorWorks;
	@Unique
	int biome_modifier$minY;
	@Unique
	int biome_modifier$height;
	@Unique
	int biome_modifier$logicalHeight;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	TagKey<Block> biome_modifier$infiniburn;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	ResourceLocation biome_modifier$effectsLocation;
	@Unique
	float biome_modifier$ambientLight;
	@SuppressWarnings("NotNullFieldNotInitialized")
	@Unique
	DimensionType.MonsterSettings biome_modifier$monsterSettings;

	@SuppressWarnings("java:S107")
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	public void biome_modifier$constructor(OptionalLong fixedTime, boolean hasSkyLight, boolean hasCeiling, boolean ultraWarm, boolean natural,
										   double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks, int minY, int height, int logicalHeight,
										   TagKey<Block> infiniburn, ResourceLocation effectsLocation, float ambientLight,
										   DimensionType.MonsterSettings monsterSettings, CallbackInfo ci) {
		this.biome_modifier$fixedTime = fixedTime;
		this.biome_modifier$hasSkyLight = hasSkyLight;
		this.biome_modifier$hasCeiling = hasCeiling;
		this.biome_modifier$ultraWarm = ultraWarm;
		this.biome_modifier$natural = natural;
		this.biome_modifier$coordinateScale = coordinateScale;
		this.biome_modifier$bedWorks = bedWorks;
		this.biome_modifier$respawnAnchorWorks = respawnAnchorWorks;
		this.biome_modifier$minY = minY;
		this.biome_modifier$height = height;
		this.biome_modifier$logicalHeight = logicalHeight;
		this.biome_modifier$infiniburn = infiniburn;
		this.biome_modifier$effectsLocation = effectsLocation;
		this.biome_modifier$ambientLight = ambientLight;
		this.biome_modifier$monsterSettings = monsterSettings;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified fixed time.
	 */
	@WrapOperation(method = {"fixedTime", "hasFixedTime", "timeOfDay"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;fixedTime:Ljava/util/OptionalLong;", opcode = Opcodes.GETFIELD))
	private OptionalLong biome_modifier$modifiedFixedTime(DimensionType instance, Operation<OptionalLong> original) {
		return this.biome_modifier$fixedTime;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified has skylight.
	 */
	@WrapOperation(method = {"hasSkyLight"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;hasSkyLight:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedHasSkyLight(DimensionType instance, Operation<Boolean> original) {
		return this.biome_modifier$hasSkyLight;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified has ceiling.
	 */
	@WrapOperation(method = {"hasCeiling"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;hasCeiling:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedHasCeiling(DimensionType instance, Operation<Boolean> original) {
		return this.biome_modifier$hasCeiling;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified ultra warm.
	 */
	@WrapOperation(method = {"ultraWarm"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;ultraWarm:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedUltraWarm(DimensionType instance, Operation<Boolean> original) {
		return this.biome_modifier$ultraWarm;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified natural.
	 */
	@WrapOperation(method = {"natural"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;natural:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedNatural(DimensionType instance, Operation<Boolean> original) {
		return this.biome_modifier$natural;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified coordinate scale.
	 */
	@WrapOperation(method = {"coordinateScale"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;coordinateScale:D", opcode = Opcodes.GETFIELD))
	private double biome_modifier$modifiedCoordinateScale(DimensionType instance, Operation<Double> original) {
		return this.biome_modifier$coordinateScale;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified bed works.
	 */
	@WrapOperation(method = {"bedWorks"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;bedWorks:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedBedWorks(DimensionType instance, Operation<Boolean> original) {
		return this.biome_modifier$bedWorks;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified respawn anchor works.
	 */
	@WrapOperation(method = {"respawnAnchorWorks"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;respawnAnchorWorks:Z", opcode = Opcodes.GETFIELD))
	private boolean biome_modifier$modifiedRespawnAnchorWorks(DimensionType instance, Operation<Boolean> original) {
		return this.biome_modifier$respawnAnchorWorks;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified min Y.
	 */
	@WrapOperation(method = {"minY"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;minY:I", opcode = Opcodes.GETFIELD))
	private int biome_modifier$modifiedMinY(DimensionType instance, Operation<Integer> original) {
		return this.biome_modifier$minY;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified height.
	 */
	@WrapOperation(method = {"height"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;height:I", opcode = Opcodes.GETFIELD))
	private int biome_modifier$modifiedHeight(DimensionType instance, Operation<Integer> original) {
		return this.biome_modifier$height;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified logical height.
	 */
	@WrapOperation(method = {"logicalHeight"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;logicalHeight:I", opcode = Opcodes.GETFIELD))
	private int biome_modifier$modifiedLogicalHeight(DimensionType instance, Operation<Integer> original) {
		return this.biome_modifier$logicalHeight;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified infiniburn.
	 */
	@WrapOperation(method = {"infiniburn"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;infiniburn:Lnet/minecraft/tags/TagKey;", opcode = Opcodes.GETFIELD))
	private TagKey<Block> biome_modifier$modifiedInfiniburn(DimensionType instance, Operation<TagKey<Block>> original) {
		return this.biome_modifier$infiniburn;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified effects location.
	 */
	@WrapOperation(method = {"effectsLocation"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;effectsLocation:Lnet/minecraft/resources/ResourceLocation;", opcode = Opcodes.GETFIELD))
	private ResourceLocation biome_modifier$modifiedEffectsLocation(DimensionType instance, Operation<ResourceLocation> original) {
		return this.biome_modifier$effectsLocation;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified ambient light.
	 */
	@WrapOperation(method = {"ambientLight"}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;ambientLight:F", opcode = Opcodes.GETFIELD))
	private float biome_modifier$modifiedAmbientLight(DimensionType instance, Operation<Float> original) {
		return this.biome_modifier$ambientLight;
	}

	/**
	 * @author Biome Modifier for Fabric.
	 * @reason Redirect this method by using modified monster settings.
	 */
	@WrapOperation(method = {
			"monsterSettings",
			"piglinSafe",
			"hasRaids",
			"monsterSpawnLightTest",
			"monsterSpawnBlockLightLimit",
	}, at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/dimension/DimensionType;monsterSettings:Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;", opcode = Opcodes.GETFIELD))
	private DimensionType.MonsterSettings biome_modifier$modifiedMonsterSettings(DimensionType instance, Operation<DimensionType.MonsterSettings> original) {
		return this.biome_modifier$monsterSettings;
	}

	@Override
	public DimensionModificationParametersList biome_modifier$getModificationParametersList(RegistryAccess registryAccess) {
		return new DimensionModificationParametersList(registryAccess, (DimensionType)(Object)this);
	}

	@Override
	public void biome_modifier$modify(DimensionModificationParametersList list) {
		if(this.biome_modifier$isModified) {
			throw new IllegalStateException("Dimension has already been modified!");
		}
		this.biome_modifier$isModified = true;

		this.biome_modifier$fixedTime = list.fixedTime();
		this.biome_modifier$hasSkyLight = list.hasSkyLight();
		this.biome_modifier$hasCeiling = list.hasCeiling();
		this.biome_modifier$ultraWarm = list.ultraWarm();
		this.biome_modifier$natural = list.natural();
		this.biome_modifier$coordinateScale = list.coordinateScale();
		this.biome_modifier$bedWorks = list.bedWorks();
		this.biome_modifier$respawnAnchorWorks = list.respawnAnchorWorks();
		this.biome_modifier$minY = list.minY();
		this.biome_modifier$height = list.height();
		this.biome_modifier$logicalHeight = list.logicalHeight();
		this.biome_modifier$infiniburn = list.infiniburn();
		this.biome_modifier$effectsLocation = list.effectsLocation();
		this.biome_modifier$ambientLight = list.ambientLight();
		this.biome_modifier$monsterSettings = new DimensionType.MonsterSettings(list.piglinSafe(), list.hasRaids(), list.monsterSpawnLightTest(), list.monsterSpawnBlockLightLimit());
	}
}
