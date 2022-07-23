package com.loucaskreger.mcscript.api.lua.client.event;

import com.loucaskreger.mcscript.McScript;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.TwoArgFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventHandler extends LuaTable {

    // TODO: Make this logger point to a different file, maybe .minecraft/lua/logs
    private static final Logger LOGGER = LogManager.getLogger("test");
    // TODO: event listeners should also be split by script, make another map holding a str of the script name
    public static Map<EventType, LuaFunction> listeners;
    private String moduleName;

    public EventHandler(String moduleName) {
        super();
        this.moduleName = moduleName;
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

    class addEventListener extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue name, LuaValue listener) {
            McScript.LOGGER.info("Module name: " + EventHandler.this.moduleName);
            if (!(name instanceof LuaString)) {
                throw new LuaError("The first argument is not a string! Expected an event type");
            }
            if (!(listener instanceof LuaFunction)) {
                throw new LuaError("The first argument is not a function. Expected a callback");
            }
            Optional<EventType> eventType = EventType.eventFromName(name.tojstring());
            if (eventType.isPresent()) {
                listeners.put(eventType.get(), (LuaFunction) listener);
            } else {
                McScript.LOGGER.info(String.format("Event type `%s` is not a valid event type", name.tojstring()));
                throw new LuaError(String.format("Event type `%s` is not a valid event type", name.tojstring()));
            }
            return LuaValue.NIL;
        }
    }
}
