package com.loucaskreger.mcscript.util;

import com.loucaskreger.mcscript.McScript;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ScriptManager {


    public static ScriptManager getInstance() {
        return INSTANCE;
    }

    public ScriptManager() {
        this.scripts = new ArrayList<>();
        this.files = new ArrayList<>();
    }


    public void loadScripts() {
        File dir = SCRIPT_PATH.toFile();
        File[] files = dir.listFiles();
        if (files == null) {
            LOGGER.error("Could not find the scripts path, maybe the folder doesn't exist?");
        } else {
            for (File file : files) {
                this.files.add(file);
                this.scripts.add(new Script(file));
            }
        }
        McScript.LOGGER.info(String.format("Loaded %d scripts", this.files.size()));
    }

    public List<Script> scripts;
    public List<File> files;

    private static ScriptManager INSTANCE = new ScriptManager();
    private static final Path SCRIPT_PATH = FMLPaths.GAMEDIR.get().resolve("scripts");
    private static final Logger LOGGER = LogManager.getLogger();
}
