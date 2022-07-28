package com.loucaskreger.mcscript.util;

import com.loucaskreger.mcscript.McScript;
import net.minecraftforge.fml.loading.FMLPaths;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ModuleManager {

    // TODO: Make this point to .minecraft/lua/scripts
    private static final Path SCRIPT_PATH = FMLPaths.GAMEDIR.get().resolve("scripts");
    private static ModuleManager instance;
    private Map<String, LuaModule> modules;

    public static ModuleManager getInstance() {
        if (instance == null) {
            instance = new ModuleManager();
        }
        return instance;
    }

    public ModuleManager() {
        this.modules = new HashMap<>();
    }

    public void setGlobalsEntry(String key, LuaValue value) {
        for (LuaModule script : this.modules.values()) {
            script.setGlobalsEntry(key, value);
        }
    }

    public void setGlobalsEntry(String key, Function<LuaModule, LuaValue> func) {
        for (LuaModule module : this.modules.values()) {
            module.setGlobalsEntry(key, func.apply(module));
        }
    }

    public void callAll() {
        McScript.LOGGER.info("Calling loaded Lua modules");
        for (Map.Entry<String, LuaModule> entry : this.modules.entrySet()) {
            McScript.LOGGER.info("Key: " + entry.getKey());
            LuaModule module = entry.getValue();
            try {
                module.call();
            } catch (LuaError e) {
                McScript.LUA_LOGGER.error("Caught lua error: " + e.getLocalizedMessage());
                module.setError(true);
            }
        }
    }

    public Optional<LuaModule> getModule(String name) {
        return Optional.ofNullable(this.modules.get(name));
    }

    public void loadScripts() {
        File dir = SCRIPT_PATH.toFile();
        File[] files = dir.listFiles();
        if (files == null) {
            McScript.LOGGER.error("Could not find the scripts path, maybe the folder doesn't exist?");
        } else {
            for (File file : files) {
                if (file.getName().contains(".lua")) {
                    this.modules.put(file.getName(), new LuaModule(file));
                }
            }
        }
        McScript.LOGGER.info(String.format("Loaded %d modules", this.modules.size()));
    }

    public Map<String, LuaModule> getModules() {
        return this.modules;
    }
}
