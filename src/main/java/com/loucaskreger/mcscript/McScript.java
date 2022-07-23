package com.loucaskreger.mcscript;

import com.loucaskreger.mcscript.api.lua.client.Player;
import com.loucaskreger.mcscript.api.lua.client.event.EventHandler;
import com.loucaskreger.mcscript.api.lua.client.event.EventType;
import com.loucaskreger.mcscript.util.LuaUtils;
import com.loucaskreger.mcscript.util.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaError;

@Mod("mcscript")
public class McScript {
    public static final String MOD_ID = "mcscript";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Logger LUA_LOGGER = LogManager.getLogger("lua");

    public McScript() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
    }

    private void setupClient(final FMLClientSetupEvent event) {
        ModuleManager manager = ModuleManager.getInstance();
        manager.loadScripts();
        manager.setGlobalsEntry("EventHandler", script -> new EventHandler(script.getName()));
        manager.setGlobalsEntry("EventType", LuaUtils.tableFromEnum(EventType.class));
        manager.setGlobalsEntry("Phase", LuaUtils.tableFromEnum(TickEvent.Phase.class));
        manager.setGlobalsEntry("Player", new Player());
        manager.callAll();

    }
}
