package com.loucaskreger.mcscript.util;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

public class LuaModule {

    private final Globals globals;
    private final LuaValue chunk;
    private final String name;
    private boolean error;

    public LuaModule(File file) {
        this.globals = JsePlatform.standardGlobals();
        this.chunk = this.globals.loadfile(file.getPath());
        this.name = file.getName();
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

}
