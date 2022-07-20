package com.loucaskreger.mcscript.api.lua.client;

import com.loucaskreger.mcscript.McScript;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

public class Logger extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue lib = tableOf();
        lib.set("info", new info());
        lib.set("warn", new warn());
        lib.set("debug", new debug());
        env.set("Logger", lib);
        return lib;
    }

    static class info extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue arg) {
            McScript.LUA_LOGGER.info(arg.tojstring());
            return NIL;
        }
    }

    static class warn extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue arg) {
            McScript.LUA_LOGGER.warn(arg.tojstring());
            return NIL;
        }
    }

    static class debug extends OneArgFunction {
        @Override
        public LuaValue call(LuaValue arg) {
            McScript.LUA_LOGGER.debug(arg.tojstring());
            return NIL;
        }
    }
}
