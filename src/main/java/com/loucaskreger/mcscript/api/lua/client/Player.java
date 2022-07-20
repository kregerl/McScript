package com.loucaskreger.mcscript.api.lua.client;

import com.loucaskreger.mcscript.util.LuaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.Optional;
import java.util.function.Function;

public class Player extends LuaTable {


    public Player() {
        super();
        // Enums
        this.set("Hand", LuaUtils.tableFromEnum(Hand.class));
        this.set("HandSide", LuaUtils.tableFromEnum(HandSide.class));

        // Custom function
        this.set("isNil", new IsNil());

        // Getters
        this.set("getCurrentMood", new PlayerValueGetter("getCurrentMood", (player) -> valueOf(player.getCurrentMood())));
        this.set("isLocalPlayer", new PlayerValueGetter("isLocalPlayer", (player) -> valueOf(player.isLocalPlayer())));
        this.set("isSuppressingSlidingDownLadder", new PlayerValueGetter("isSuppressingSlidingDownLadder", (player) -> valueOf(player.isSuppressingSlidingDownLadder())));
        this.set("canSpawnSprintParticle", new PlayerValueGetter("canSpawnSprintParticle", (player) -> valueOf(player.canSpawnSprintParticle())));
        this.set("canSpawnSoulSpeedParticle", new PlayerValueGetter("canSpawnSoulSpeedParticle", (player) -> valueOf(player.canSpawnSoulSpeedParticle())));
        this.set("getServerBrand", new PlayerValueGetter("getServerBrand", (player) -> valueOf(player.getServerBrand())));
        this.set("shouldShowDeathScreen", new PlayerValueGetter("shouldShowDeathScreen", (player) -> valueOf(player.shouldShowDeathScreen())));
        this.set("isEffectiveAi", new PlayerValueGetter("isEffectiveAi", (player) -> valueOf(player.isEffectiveAi())));
        this.set("isUsingItem", new PlayerValueGetter("isUsingItem", (player) -> valueOf(player.isUsingItem())));
        this.set("getUsedItemHand", new PlayerValueGetter("getUsedItemHand", (player) -> valueOf(player.getUsedItemHand().name())));
        this.set("isRidingJumpable", new PlayerValueGetter("isRidingJumpable", (player) -> valueOf(player.isRidingJumpable())));
        this.set("getJumpRidingScale", new PlayerValueGetter("getJumpRidingScale", (player) -> valueOf(player.getJumpRidingScale())));
        this.set("isShiftKeyDown", new PlayerValueGetter("isShiftKeyDown", (player) -> valueOf(player.isShiftKeyDown())));
        this.set("isCrouching", new PlayerValueGetter("isCrouching", (player) -> valueOf(player.isCrouching())));
        this.set("isMovingSlowly", new PlayerValueGetter("isMovingSlowly", (player) -> valueOf(player.isMovingSlowly())));
        this.set("isHandsBusy", new PlayerValueGetter("isHandsBusy", (player) -> valueOf(player.isHandsBusy())));
        this.set("isAutoJumpEnabled", new PlayerValueGetter("isAutoJumpEnabled", (player) -> valueOf(player.isAutoJumpEnabled())));
        this.set("getWaterVision", new PlayerValueGetter("getWaterVision", (player) -> valueOf(player.getWaterVision())));
        this.set("isUnderWater", new PlayerValueGetter("isUnderWater", (player) -> valueOf(player.isUnderWater())));
        this.set("getLuck", new PlayerValueGetter("getLuck", (player) -> valueOf(player.getLuck())));
        this.set("getPortalWaitTime", new PlayerValueGetter("getPortalWaitTime", (player) -> valueOf(player.getPortalWaitTime())));
        this.set("getScore", new PlayerValueGetter("getScore", (player) -> valueOf(player.getScore())));
        this.set("getLuck", new PlayerValueGetter("getLuck", (player) -> valueOf(player.getLuck())));
        this.set("getRidingOffset", new PlayerValueGetter("getRidingOffset", (player) -> valueOf(player.getMyRidingOffset())));
        this.set("isAffectedByFluids", new PlayerValueGetter("isAffectedByFluids", (player) -> valueOf(player.isAffectedByFluids())));
        this.set("isPushedByFluid", new PlayerValueGetter("isPushedByFluid", (player) -> valueOf(player.isPushedByFluid())));
        this.set("isSleepingLongEnough", new PlayerValueGetter("isSleepingLongEnough", (player) -> valueOf(player.isSleepingLongEnough())));
        this.set("getSleepTimer", new PlayerValueGetter("getSleepTimer", (player) -> valueOf(player.getSleepTimer())));
        this.set("getSpeed", new PlayerValueGetter("getSpeed", (player) -> valueOf(player.getSpeed())));
        this.set("tryToStartFallFlying", new PlayerValueGetter("tryToStartFallFlying", (player) -> valueOf(player.tryToStartFallFlying())));
        this.set("getEnchantmentSeed", new PlayerValueGetter("getEnchantmentSeed", (player) -> valueOf(player.getEnchantmentSeed())));
        this.set("getXpNeededForNextLevel", new PlayerValueGetter("getXpNeededForNextLevel", (player) -> valueOf(player.getXpNeededForNextLevel())));
        this.set("isHurt", new PlayerValueGetter("isHurt", (player) -> valueOf(player.isHurt())));
        this.set("getName", new PlayerValueGetter("getName", (player) -> valueOf(player.getName().getContents())));
        this.set("isSpectator", new PlayerValueGetter("isSpectator", (player) -> valueOf(player.isSpectator())));
        this.set("isSwimming", new PlayerValueGetter("isSwimming", (player) -> valueOf(player.isSwimming())));
        this.set("isCreative", new PlayerValueGetter("isCreative", (player) -> valueOf(player.isCreative())));
        this.set("getScoreboardName", new PlayerValueGetter("getScoreboardName", (player) -> valueOf(player.getScoreboardName())));
        this.set("getAbsorptionAmount", new PlayerValueGetter("getAbsorptionAmount", (player) -> valueOf(player.getAbsorptionAmount())));
        this.set("getMainArm", new PlayerValueGetter("getMainArm", (player) -> valueOf(player.getMainArm().name())));

        this.set("getExperience", new PlayerValueGetter("getExperience", (player) -> valueOf(player.experienceLevel)));
        this.set("getSprintTime", new PlayerValueGetter("getSprintTime", (player) -> valueOf(player.sprintTime)));
        this.set("getTotalExperience", new PlayerValueGetter("getTotalExperience", (player) -> valueOf(player.totalExperience)));
        this.set("getExperienceProgress", new PlayerValueGetter("getExperienceProgress", (player) -> valueOf(player.experienceProgress)));
        this.set("takeXpDelay", new PlayerValueGetter("takeXpDelay", (player) -> valueOf(player.takeXpDelay)));

//        this.set("getYBob", new PlayerValueGetter("getYBob", (player) -> valueOf(player.yBob)));
//        this.set("getXBob", new PlayerValueGetter("getXBob", (player) -> valueOf(player.xBob)));
//        this.set("getYBobO", new PlayerValueGetter("getYBobO", (player) -> valueOf(player.yBobO)));
//        this.set("getXBobO", new PlayerValueGetter("getXBobO", (player) -> valueOf(player.xBobO)));


    }

    private static class IsNil extends ZeroArgFunction {

        @Override
        public LuaValue call() {
            Optional<ClientPlayerEntity> player = Optional.ofNullable(Minecraft.getInstance().player);
            if (player.isPresent()) {
                return FALSE;
            } else {
                return TRUE;
            }
        }
    }

    private static class PlayerValueGetter extends ZeroArgFunction {

        private final Function<ClientPlayerEntity, LuaValue> getter;
        private final String name;

        public PlayerValueGetter(String name, Function<ClientPlayerEntity, LuaValue> getter) {
            this.name = name;
            this.getter = getter;
        }

        @Override
        public LuaValue call() {
            Optional<ClientPlayerEntity> player = Optional.ofNullable(Minecraft.getInstance().player);
            if (!player.isPresent()) {
                return NIL;
            }
            return this.getter.apply(player.get());
        }

        @Override
        public String toString() {
            return "function " + this.name + "(void)" + this.getter.toString();
        }
    }
}
