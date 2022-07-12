package com.loucaskreger.mcscript.util;

import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ScriptManager {

    public static void init() {
        INSTANCE = new ScriptManager();
    }

    public ScriptManager() {
        this.scripts = new ArrayList<>();
    }

    public void loadScripts() {
        File dir = SCRIPT_PATH.toFile();
        File[] files = dir.listFiles();
        if (files == null) {
            LOGGER.error("Could not find the scripts path, maybe the folder doesn't exist?");
        } else {
            for (File file : files) {
                this.scripts.add(new Script(file));
            }
        }
    }

    public List<Script> scripts;

    public static ScriptManager INSTANCE;
    private static final Path SCRIPT_PATH = FMLPaths.GAMEDIR.get().resolve("scripts");
    private static final Logger LOGGER = LogManager.getLogger();
}
