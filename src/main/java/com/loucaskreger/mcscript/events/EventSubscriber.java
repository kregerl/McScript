package com.loucaskreger.mcscript.events;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.api.lua.EventHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_K;

@Mod.EventBusSubscriber(modid = McScript.MOD_ID, value = Dist.CLIENT)
public class EventSubscriber {

    private static final KeyBinding key = new KeyBinding("", GLFW_KEY_K, "");
    private static final Globals globals = JsePlatform.standardGlobals();

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (key.isDown()) {
            McScript.LOGGER.info("Key pressed");
            EventHandler.listeners.get(EventType.CLIENT_TICK).call();
        }
    }
//
//    @SubscribeEvent
//    public static void onChatMessage(final ClientChatEvent event) {
//        if ("-load".equals(event.getMessage())) {
//            McScript.LOGGER.info("-load called from Thread: " + Thread.currentThread());
////            LuaExecutor.getInstance().submit(() -> {
//            ScriptManager manager = ScriptManager.getInstance();
//            manager.loadScripts();
//            McScript.LOGGER.info(manager.files.size());
//            for (File file : manager.files) {
//                McScript.LOGGER.info("Inside Files Loop");
//                LuaValue chunk = globals.loadfile(file.getPath());
//                chunk.call("com/loucaskreger/mcscript/libs/");
//            }
////            });
//        }
//    }
}
