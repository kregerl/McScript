package com.loucaskreger.mcscript;

import com.loucaskreger.mcscript.lua.LuaExecutor;
import com.loucaskreger.mcscript.util.ScriptManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

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
//        McScript.LOGGER.info("Setup Client Thread: " + Thread.currentThread());
//        LuaExecutor.getInstance().submit(() -> {
////        McScript.LOGGER.info("RUNNING SUBMITTED");
////        EventHandler.getInstance().clearLocks();
//            ScriptManager manager = ScriptManager.getInstance();
//            Globals globals = JsePlatform.standardGlobals();
//
//            manager.loadScripts();
//            McScript.LOGGER.info(manager.files.size());
//            for (File file : manager.files) {
//                McScript.LOGGER.info("Inside Files Loop");
//                LuaValue chunk = globals.loadfile(file.getPath());
//                chunk.call("com/loucaskreger/mcscript/libs/");
//            }
//        });

    }
}
