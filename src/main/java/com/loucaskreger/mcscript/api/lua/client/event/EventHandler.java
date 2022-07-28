package com.loucaskreger.mcscript.api.lua.client.event;

import com.loucaskreger.mcscript.McScript;
import net.minecraftforge.eventbus.api.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventHandler extends LuaTable {

    public static Map<String, Map<EventType, LuaFunction>> listeners;
    private final String moduleName;

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

    private static Optional<LuaFunction> getListener(String moduleName, EventType key) {
        return Optional.ofNullable(listeners.get(moduleName).get(key));
    }

    public static void dispatch(EventType key, Event event) {
        for (String moduleName : listeners.keySet()) {
            Optional<LuaFunction> callback = getListener(moduleName, key);
            try {
                callback.ifPresent(listener -> listener.call(CoerceJavaToLua.coerce(event)));
            } catch (LuaError e) {
                McScript.LUA_LOGGER.error("Error dispatching event");
            }
        }
    }

    class addEventListener extends TwoArgFunction {
        @Override
        public LuaValue call(LuaValue name, LuaValue listener) {
            if (!(name instanceof LuaString)) {
                throw new LuaError("The first argument is not a string! Expected an event type");
            }
            if (!(listener instanceof LuaFunction)) {
                throw new LuaError("The first argument is not a function. Expected a callback");
            }
            Optional<EventType> eventType = EventType.eventFromName(name.tojstring());
            if (eventType.isPresent()) {
                listeners.computeIfAbsent(EventHandler.this.moduleName, k -> new HashMap<>());
                listeners.get(EventHandler.this.moduleName).put(eventType.get(), (LuaFunction) listener);
            } else {
                throw new LuaError(String.format("Event type `%s` is not a valid event type", name.tojstring()));
            }
            return LuaValue.NIL;
        }
    }
}
