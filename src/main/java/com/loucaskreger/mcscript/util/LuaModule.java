package com.loucaskreger.mcscript.util;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

public class LuaModule {

    private final Globals globals;
    private final String path;
    private final String name;
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

}
