package com.loucaskreger.mcscript.api.lua;

import com.loucaskreger.mcscript.McScript;
import com.loucaskreger.mcscript.events.EventType;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventHandler extends LuaTable {
    public static Map<EventType, LuaFunction> listeners;

    public EventHandler() {
        super();
        listeners = new HashMap<>();
//        Class<?>[] classes = EventHandler.class.getDeclaredClasses();
        this.set("addEventListener", new addEventListener());
    }

    static class addEventListener extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue name, LuaValue listener) {
            if (!(name instanceof LuaString)) {
                McScript.LOGGER.info("Not a LuaString");
                return LuaValue.FALSE;
            }
            if (!(listener instanceof LuaFunction)) {
                McScript.LOGGER.info("Not a LuaFunction");
                return LuaValue.FALSE;
            }
            Optional<EventType> eventType = EventType.eventFromName(name.tojstring());
            if (eventType.isPresent()) {
                listeners.put(eventType.get(), (LuaFunction) listener);
            } else {
                McScript.LOGGER.info(String.format("Event type `%s` is not a valid event type", name.tojstring()));
            }
            return LuaValue.TRUE;
        }
    }

}
