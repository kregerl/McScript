package com.loucaskreger.mcscript.events;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum EventType {
    CLIENT_TICK("CLIENT_TICK");

    final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }

    public static LuaTable getTable() {
        List<LuaValue> values = new ArrayList<>();
        for (EventType type : EventType.values()) {
            // Lua tables are created in the order {key1, value1, key2, value2}
            LuaValue value = LuaValue.valueOf(type.getEventName());
            values.add(value);
            values.add(value);
        }
        return LuaTable.tableOf(values.toArray(new LuaValue[0]));
    }

    public static Optional<EventType> eventFromName(String name) {
        return Arrays.stream(EventType.values()).filter(et -> et.getEventName().equals(name)).findFirst();
    }
}