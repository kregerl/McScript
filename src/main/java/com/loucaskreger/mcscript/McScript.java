package com.loucaskreger.mcscript;

import com.loucaskreger.mcscript.util.Script;
import com.loucaskreger.mcscript.util.ScriptManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaThread;

import java.lang.reflect.Method;

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
        Method getThread = ObfuscationReflectionHelper.findMethod(Minecraft.class, "func_213170_ax"); // getRunningThread
        try {
            Thread thread = (Thread) getThread.invoke(Minecraft.getInstance());
            ScriptManager.init();

            LOGGER.info("SetupClient");
            ScriptManager.INSTANCE.loadScripts();
            for (Script script : ScriptManager.INSTANCE.scripts) {
                script.call();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
