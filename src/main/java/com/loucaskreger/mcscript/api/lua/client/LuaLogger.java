package com.loucaskreger.mcscript.api.lua.client;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.util.LuaUtils;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.function.BiConsumer;

public class LuaLogger extends LuaTable {

    public LuaLogger() {
        super();

        this.set("info", new Log("info", (logger, luaValue) -> logger.info(luaValue.tojstring())));
        this.set("warn", new Log("warn", (logger, luaValue) -> logger.warn(luaValue.tojstring())));
        this.set("debug", new Log("debug", (logger, luaValue) -> logger.debug(luaValue.tojstring())));
    }


    static class Log extends LuaUtils.LuaSetter<Logger, LuaValue> {

        public Log(String name, BiConsumer<Logger, LuaValue> setter) {
            super(name, setter);
        }

        @Override
        public LuaValue call(LuaValue arg) {
            this.setter.accept(McScript.LUA_LOGGER, arg);
            return super.call();
        }
    }
}
