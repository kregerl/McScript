package com.loucaskreger.mcscript.events;

import com.loucaskreger.mcscript.McScript;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

@Mod.EventBusSubscriber(modid = McScript.MOD_ID, value = Dist.CLIENT)
public class EventSubscriber {

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        LuaFunction callback = EventHandler.getInstance().getEventCallback(EventType.CLIENT_TICK);
        if (callback != null) {
            callback.call(CoerceJavaToLua.coerce(event));
        } else {
            McScript.LOGGER.info("NULL HERE");
        }
    }
}
