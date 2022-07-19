package com.loucaskreger.mcscript;

import com.loucaskreger.mcscript.api.lua.EventHandler;
import com.loucaskreger.mcscript.events.EventType;
import com.loucaskreger.mcscript.util.ScriptManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("mcscript")
public class McScript {
    public static final String MOD_ID = "mcscript";
    public static final Logger LOGGER = LogManager.getLogger();

    public McScript() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
    }

    private void setupClient(final FMLClientSetupEvent event) {
        ScriptManager manager = ScriptManager.getInstance();
        manager.loadScripts();
        manager.setGlobalsEntry("EventType", EventType.getTable());
        manager.setGlobalsEntry("EventHandler", new EventHandler());
        manager.callAll();
    }
}
