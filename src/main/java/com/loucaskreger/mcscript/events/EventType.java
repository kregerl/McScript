package com.loucaskreger.mcscript.events;


import java.util.Arrays;
import java.util.Optional;

public enum EventType {
    CLIENT_TICK("clientTick");

    final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return this.eventName;
    }

    public static Optional<EventType> eventFromName(String name) {
        return Arrays.stream(EventType.values()).filter(et -> et.getEventName().equals(name)).findFirst();
    }
}