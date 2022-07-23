package com.loucaskreger.mcscript.client;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.api.lua.client.event.EventHandler;
import com.loucaskreger.mcscript.api.lua.client.event.EventType;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.DifficultyChangeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;

@Mod.EventBusSubscriber(modid = McScript.MOD_ID, value = Dist.CLIENT)
@SuppressWarnings("unused")
public class EventSubscriber {

    private static final KeyBinding key = new KeyBinding("", GLFW_KEY_K, "");

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        // TODO: Convert events to a lua table so things like phase can be used by lua
//        if (event.phase == TickEvent.Phase.START) {
        EventHandler.getListener(EventType.CLIENT_TICK).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
//        }
    }

    @SubscribeEvent
    public static void onChatMessageSent(final ClientChatEvent event) {
        EventHandler.getListener(EventType.CHAT_MSG_SENT).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onChatMessageReceived(final ClientChatReceivedEvent event) {
        EventHandler.getListener(EventType.CHAT_MSG_RECEIVED).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onLogin(final ClientPlayerNetworkEvent.LoggedInEvent event) {
        EventHandler.getListener(EventType.LOGGED_IN).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onLogout(final ClientPlayerNetworkEvent.LoggedOutEvent event) {
        EventHandler.getListener(EventType.LOGGED_OUT).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }


    @SubscribeEvent
    public static void onClickInput(final InputEvent.ClickInputEvent event) {
        // Triggered on Attack, Place block, and Pick block
        EventHandler.getListener(EventType.CLICK_INPUT).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onMouseInput(final InputEvent.MouseInputEvent event) {
        EventHandler.getListener(EventType.MOUSE_INPUT).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onMouseScroll(final InputEvent.MouseScrollEvent event) {
        EventHandler.getListener(EventType.MOUSE_SCROLLED).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onKeyboardInput(final InputEvent.KeyInputEvent event) {
        EventHandler.getListener(EventType.KEYBOARD_INPUT).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    @SubscribeEvent
    public static void onRawMouseEvent(final InputEvent.RawMouseEvent event) {
        // Cancellable before keybindings are updated
        EventHandler.getListener(EventType.RAW_MOUSE_EVENT).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }

    // TODO: Add render events and hud API

    @SubscribeEvent
    public static void onDifficultyChanged(final DifficultyChangeEvent event) {
        McScript.LOGGER.info("Difficulty Changed" + event.getDifficulty());
        EventHandler.getListener(EventType.DIFFICULTY_CHANGED).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }


}
