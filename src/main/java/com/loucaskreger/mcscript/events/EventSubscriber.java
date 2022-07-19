package com.loucaskreger.mcscript.events;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.api.lua.EventHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;

@Mod.EventBusSubscriber(modid = McScript.MOD_ID, value = Dist.CLIENT)
public class EventSubscriber {

    private static final KeyBinding key = new KeyBinding("", GLFW_KEY_K, "");

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (key.isDown()) {
            McScript.LOGGER.info("Key pressed (Java)");
            EventHandler.getListener(EventType.CLIENT_TICK).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
        }
    }

    @SubscribeEvent
    public static void onChatMessage(final ClientChatEvent event) {
        EventHandler.getListener(EventType.CHAT_MSG_RECEIVED).ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
    }
}
