package com.loucaskreger.mcscript.util;

import com.loucaskreger.mcscript.McScript;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

public class LuaModule {

    private final Globals globals;
    private final String path;
    private final String name;
    private String displayName;
    private String description;
    private String author;
    private String version;
    private LuaValue chunk;
    private boolean error;


    public LuaModule(File file) {
        this.globals = JsePlatform.standardGlobals();
        this.path = file.getPath();
        this.name = file.getName();
        this.chunk = this.globals.loadfile(this.path);
    }

    public String getName() {
        return this.name;
    }

    public void setGlobalsEntry(String key, LuaValue value) {
        this.globals.set(key, value);
    }

    public void call() {
        McScript.LOGGER.info(this.getDisplayName());
        McScript.LOGGER.info(this.getAuthor());
        McScript.LOGGER.info(this.getDescription());
        McScript.LOGGER.info(this.getVersion());
        this.chunk.call();
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return this.error;
    }

    public void reloadModule() {
        this.chunk = this.globals.loadfile(this.path);
        this.error = false;
        this.call();
    }

    public String getDisplayName() {
        return displayName == null ? this.name : this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
