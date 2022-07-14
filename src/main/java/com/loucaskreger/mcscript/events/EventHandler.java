package com.loucaskreger.mcscript.events;

import com.loucaskreger.mcscript.McScript;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class EventHandler {

    private static volatile EventHandler instance;

    private Map<EventType, LuaFunction> listeners;
    private Map<EventType, ReentrantLock> locks;

    public static EventHandler getInstance() {
        if (instance == null) {
            synchronized (EventHandler.class) {
                if (instance == null) {
                    instance = new EventHandler();
                }
            }
        }
        return instance;
    }

    EventHandler() {
        McScript.LOGGER.info("INITIALIZED ANOTHER EVENT HANDLER");
        if (listeners == null) {
            McScript.LOGGER.info("Listeners is null");
            listeners = new HashMap<>();
        }
        if (locks == null) {
            McScript.LOGGER.info("Locks is null");
            locks = new HashMap<>();
        }
    }

    public void addEventListener(EventType type, LuaFunction listener) {
        McScript.LOGGER.info("addEventListener Thread: " + Thread.currentThread());
        McScript.LOGGER.info("addEventListener");
        listeners.put(type, listener);
        locks.put(type, new ReentrantLock());
        McScript.LOGGER.info("Listeners: " + listeners.containsKey(type));
        McScript.LOGGER.info("Locks: " + locks.containsKey(type));
        McScript.LOGGER.info("----------------------");
        this.getEventListener(type);
    }

    public Optional<LuaFunction> getEventListener(EventType type) {
        McScript.LOGGER.info("getEventListener Thread: " + Thread.currentThread());
        McScript.LOGGER.info("getEventListener");
        McScript.LOGGER.info("Listeners: " + listeners.containsKey(type));
        McScript.LOGGER.info("Locks: " + locks.containsKey(type));
        return Optional.of(listeners.get(type));
    }

    public void clearLocks() {
        locks.clear();
    }

    public boolean dispatchEvents(EventType type, Object event) {
        McScript.LOGGER.info("HERE");
        Optional<LuaFunction> func = getEventListener(type);
        McScript.LOGGER.info(func.isPresent());
        if (func.isPresent()) {
            McScript.LOGGER.info("Function is present");
            try {
                ReentrantLock lock = locks.get(type);
                if (lock.tryLock()) {
                    McScript.LOGGER.info("Inside Lock");
                    try {
                        McScript.LOGGER.info("Want to be here");
                        func.get().call(CoerceJavaToLua.coerce(event));
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (LuaError e) {
                McScript.LOGGER.error("Lua error: " + e.getMessage().toString());
                this.clearLocks();
                return false;
            }
            return true;
        }
        McScript.LOGGER.info("Function is not Present");
        McScript.LOGGER.info(locks.size());
        McScript.LOGGER.info(listeners.size());
        return false;
    }

    public void debug() {
        McScript.LOGGER.info("listeners: " + listeners.size());
        McScript.LOGGER.info("locks: " + locks.size());
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            McScript.LOGGER.info("GC'd EventHandler object");
        } catch (Exception e) {
            throw e;
        } finally {
            McScript.LOGGER.info("GC'd Super object");
            super.finalize();
        }
    }
}
