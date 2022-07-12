package com.loucaskreger.mcscript.util;

import com.loucaskreger.mcscript.libs.events;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;

public class Script {

    public Script(LuaValue script) {
        this.script = script;
    }

    public Script(File file) {
        this.globals = JsePlatform.standardGlobals();
        System.out.println(file.getPath());
        this.script = globals.loadfile(file.getPath());
    }

    public void call() {
        this.script.call(LuaValue.valueOf(LIB_PATH));
    }

    private final LuaValue script;
    public Globals globals;
    private static final String LIB_PATH = "com/loucaskreger/mcscript/libs/";
}
