package com.loucaskreger.mcscript.api.lua.client.event;

import com.loucaskreger.mcscript.McScript;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventHandler extends LuaTable {

    // TODO: Make this logger point to a different file, maybe .minecraft/lua/logs
    private static final Logger LOGGER = LogManager.getLogger("test");
    public static Map<EventType, LuaFunction> listeners;

    public EventHandler() {
        super();
        listeners = new HashMap<>();
        this.set("addEventListener", new addEventListener());

        // TODO: This works but is it needed?
//        Class<?>[] classes = EventHandler.class.getDeclaredClasses();
//        for (Class<?> clazz : classes) {
//            try {
//                LibFunction function = (LibFunction) clazz.newInstance();
//                this.set(clazz.getSimpleName(), function);
////                McScript.LOGGER.info(String.format("Instantiating classes named: %s with function %s", clazz.getName(), function.tostring() ));
//            } catch (IllegalAccessException | InstantiationException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static Optional<LuaFunction> getListener(EventType key) {
        return Optional.ofNullable(listeners.get(key));
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
