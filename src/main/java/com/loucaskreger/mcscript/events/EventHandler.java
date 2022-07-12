package com.loucaskreger.mcscript.events;

import com.loucaskreger.mcscript.McScript;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;

import java.util.HashMap;
import java.util.Map;

public class EventHandler {

    public static EventHandler getInstance() {
        if (INSTANCE == null) {
            McScript.LOGGER.info("INIT HERE");
            INSTANCE = new EventHandler();
        }
        return INSTANCE;
    }

    private EventHandler() {
        this.callbacks = new HashMap<>();
    }

    public void addEventCallback(EventType type, LuaClosure callback) {
        McScript.LOGGER.info("Is Null: ");
        McScript.LOGGER.info(callback == null);
        McScript.LOGGER.info("Added event to Type: " + type + " Callback: " + callback.tojstring());
        callbacks.put(type, callback);
        McScript.LOGGER.info(callbacks.containsKey(type));
        McScript.LOGGER.info(EventType.CLIENT_TICK == type);
        McScript.LOGGER.info(callbacks.size());
    }

    public LuaFunction getEventCallback(EventType type) {
        McScript.LOGGER.info(callbacks.size());
        McScript.LOGGER.info(EventType.CLIENT_TICK == type);
        McScript.LOGGER.info("Trying to get event of type: " + type);
        McScript.LOGGER.info(callbacks.containsKey(type));
        return callbacks.get(type);
    }


    private final Map<EventType, LuaClosure> callbacks;
    private static EventHandler INSTANCE;
}
