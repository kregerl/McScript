package com.loucaskreger.mcscript.api.lua.client;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.util.LuaUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3d;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Player extends LuaTable {

    private Vector3d prevPos;

    public Player() {
        super();
        this.prevPos = Vector3d.ZERO;
        // Enums
        this.set("Hand", LuaUtils.tableFromEnum(Hand.class));
        this.set("HandSide", LuaUtils.tableFromEnum(HandSide.class));

        this.set("isNil", new IsNil());

        this.setGetters();
        this.setSetters();
    }

    // Getters
    private void setGetters() {
        this.set("getCurrentMood", new PlayerValueGetter("getCurrentMood", player -> valueOf(player.getCurrentMood())));
        this.set("getEnchantmentSeed", new PlayerValueGetter("getEnchantmentSeed", player -> valueOf(player.getEnchantmentSeed())));
        this.set("getExp", new PlayerValueGetter("getExperience", player -> valueOf(player.experienceLevel)));
        this.set("getExpProgress", new PlayerValueGetter("getExperienceProgress", player -> valueOf(player.experienceProgress)));
        this.set("getJumpRidingScale", new PlayerValueGetter("getJumpRidingScale", player -> valueOf(player.getJumpRidingScale())));
        this.set("getLuck", new PlayerValueGetter("getLuck", player -> valueOf(player.getLuck())));
        this.set("getMainArm", new PlayerValueGetter("getMainArm", player -> valueOf(player.getMainArm().name())));
        this.set("getName", new PlayerValueGetter("getName", player -> valueOf(player.getName().getContents())));
        this.set("getPortalWaitTime", new PlayerValueGetter("getPortalWaitTime", player -> valueOf(player.getPortalWaitTime())));
        this.set("getRidingOffset", new PlayerValueGetter("getRidingOffset", player -> valueOf(player.getMyRidingOffset())));
        this.set("getScore", new PlayerValueGetter("getScore", player -> valueOf(player.getScore())));
        this.set("getScoreboardName", new PlayerValueGetter("getScoreboardName", player -> valueOf(player.getScoreboardName())));
        this.set("getServerBrand", new PlayerValueGetter("getServerBrand", player -> valueOf(player.getServerBrand())));
        this.set("getSleepTimer", new PlayerValueGetter("getSleepTimer", player -> valueOf(player.getSleepTimer())));
        this.set("getSpeed", new PlayerValueGetter("getSpeed", player -> valueOf(player.getSpeed())));
        this.set("getSprintTime", new PlayerValueGetter("getSprintTime", player -> valueOf(player.sprintTime)));
        this.set("getTotalExp", new PlayerValueGetter("getTotalExperience", player -> valueOf(player.totalExperience)));
        this.set("getUsedItemHand", new PlayerValueGetter("getUsedItemHand", player -> valueOf(player.getUsedItemHand().name())));
        this.set("getWaterVision", new PlayerValueGetter("getWaterVision", player -> valueOf(player.getWaterVision())));
        this.set("getXpNeededForNextLevel", new PlayerValueGetter("getXpNeededForNextLevel", player -> valueOf(player.getXpNeededForNextLevel())));
        this.set("isAffectedByFluids", new PlayerValueGetter("isAffectedByFluids", player -> valueOf(player.isAffectedByFluids())));
        this.set("isAutoJumpEnabled", new PlayerValueGetter("isAutoJumpEnabled", player -> valueOf(player.isAutoJumpEnabled())));
        this.set("isCreative", new PlayerValueGetter("isCreative", player -> valueOf(player.isCreative())));
        this.set("isCrouching", new PlayerValueGetter("isCrouching", player -> valueOf(player.isCrouching())));
        this.set("isHandsBusy", new PlayerValueGetter("isHandsBusy", player -> valueOf(player.isHandsBusy())));
        this.set("isHurt", new PlayerValueGetter("isHurt", player -> valueOf(player.isHurt())));
        this.set("isInLava", new PlayerValueGetter("isInLava", player -> valueOf(player.isInLava())));
        this.set("isInWater", new PlayerValueGetter("isInWater", player -> valueOf(player.isInWater())));
        this.set("isLocalPlayer", new PlayerValueGetter("isLocalPlayer", player -> valueOf(player.isLocalPlayer())));
        this.set("isMovingSlowly", new PlayerValueGetter("isMovingSlowly", player -> valueOf(player.isMovingSlowly())));
        this.set("isPushedByFluid", new PlayerValueGetter("isPushedByFluid", player -> valueOf(player.isPushedByFluid())));
        this.set("isRidingJumpable", new PlayerValueGetter("isRidingJumpable", player -> valueOf(player.isRidingJumpable())));
        this.set("isShiftKeyDown", new PlayerValueGetter("isShiftKeyDown", player -> valueOf(player.isShiftKeyDown())));
        this.set("isSleepingLongEnough", new PlayerValueGetter("isSleepingLongEnough", player -> valueOf(player.isSleepingLongEnough())));
        this.set("isSpectator", new PlayerValueGetter("isSpectator", player -> valueOf(player.isSpectator())));
        this.set("isSwimming", new PlayerValueGetter("isSwimming", player -> valueOf(player.isSwimming())));
        this.set("isUnderWater", new PlayerValueGetter("isUnderWater", player -> valueOf(player.isUnderWater())));
        this.set("isUsingItem", new PlayerValueGetter("isUsingItem", player -> valueOf(player.isUsingItem())));
        this.set("isWalking", new PlayerValueGetter("isWalking", this::isWalking));
        this.set("shouldShowDeathScreen", new PlayerValueGetter("shouldShowDeathScreen", player -> valueOf(player.shouldShowDeathScreen())));
        this.set("takeXpDelay", new PlayerValueGetter("takeXpDelay", player -> valueOf(player.takeXpDelay)));
        this.set("getAbsorptionAmount", new PlayerValueGetter("getAbsorptionAmount", player -> valueOf(player.getAbsorptionAmount())));

        this.set("getInventory", new PlayerValueGetter("getInventory", PlayerInventory::new));
    }

    private LuaBoolean isWalking(ClientPlayerEntity player) {
        Vector3d tmp = player.getPosition(0);
        if (tmp.equals(this.prevPos) || !player.isOnGround()) {
            return FALSE;
        }
        this.prevPos = tmp;
        LuaBoolean result = valueOf(player.isOnGround());
        McScript.LOGGER.info(result);
        return result;

    }

    private void setSetters() {
        this.set("setSprinting", new PlayerValueSetter<LuaBoolean>("setSprinting", (player, sprinting) -> player.setSprinting(sprinting.booleanValue())));
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

    // TODO: See if its possible to make this use java types for generic instead of LuaValues like LuaBoolean.
    private static class PlayerValueSetter<T> extends LuaUtils.LuaSetter<ClientPlayerEntity, T> {

        public PlayerValueSetter(String name, BiConsumer<ClientPlayerEntity, T> setter) {
            super(name, setter);
        }

        @Override
        public LuaValue call(LuaValue arg) {
            Optional<ClientPlayerEntity> player = Optional.ofNullable(Minecraft.getInstance().player);
            if (!player.isPresent()) {
                return FALSE;
            }
            Optional<T> param = Optional.ofNullable(this.convert(arg));
            if (!param.isPresent()) {
                return FALSE;
            }
            this.setter.accept(player.get(), param.get());
            return TRUE;
        }

        // Yikes
        private T convert(LuaValue arg) {
            try {
                return (T) arg;
            } catch (ClassCastException e) {
                return null;
            }
        }
    }

    // TODO: Could remove returning NIL when player is nil since the user should check if the player is nil in the first place.
    private static class PlayerValueGetter extends LuaUtils.LuaGetter<ClientPlayerEntity> {

        public PlayerValueGetter(String name, Function<ClientPlayerEntity, LuaValue> getter) {
            super(name, getter);
        }

        @Override
        public LuaValue call() {
            Optional<ClientPlayerEntity> player = Optional.ofNullable(Minecraft.getInstance().player);
            if (!player.isPresent()) {
                return NIL;
            }
            return this.getter.apply(player.get());
        }
    }
}
