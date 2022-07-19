package com.loucaskreger.mcscript.util;

import com.loucaskreger.mcscript.McScript;
import net.minecraftforge.fml.loading.FMLPaths;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ScriptManager {

    private static final Path SCRIPT_PATH = FMLPaths.GAMEDIR.get().resolve("scripts");
    private static final String LIB_PATH = "com/loucaskreger/mcscript/libs/";
    private static ScriptManager instance;
    private Map<String, LuaValue> scripts;
    private Globals globals;

    public static ScriptManager getInstance() {
        if (instance == null) {
            instance = new ScriptManager();
        }
        return instance;
    }

    public ScriptManager() {
        this.globals = JsePlatform.standardGlobals();
        this.scripts = new HashMap<>();
    }

    public LuaValue getFromGlobals(String key) {
        return this.globals.get(key);
    }

    public void setGlobalsEntry(String key, LuaValue value) {
        this.globals.set(key, value);
    }

    public void callAll() {
        McScript.LOGGER.info("Calling loaded Lua scripts");
        for (Map.Entry<String, LuaValue> entry : this.scripts.entrySet()) {
            McScript.LOGGER.info("Key: " + entry.getKey());
            entry.getValue().call(LuaValue.valueOf(LIB_PATH));
        }
    }

    public void loadScripts() {
        File dir = SCRIPT_PATH.toFile();
        File[] files = dir.listFiles();
        if (files == null) {
            McScript.LOGGER.error("Could not find the scripts path, maybe the folder doesn't exist?");
        } else {
            for (File file : files) {
                LuaValue chunk = this.globals.loadfile(file.getPath());
                this.scripts.put(file.getName(), chunk);
            }
        }
        McScript.LOGGER.info(String.format("Loaded %d scripts", this.scripts.size()));
    }
}
