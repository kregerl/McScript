package com.loucaskreger.mcscript.api.lua.client.event;

import java.util.Arrays;
import java.util.Optional;

public enum EventType {
    CLIENT_TICK,
    CHAT_MSG_SENT,
    CHAT_MSG_RECEIVED,
    DIFFICULTY_CHANGED,// Maybe server side?
    LOGGED_IN,
    LOGGED_OUT,
    RESPAWNED,
    CLICK_INPUT,
    MOUSE_INPUT,
    MOUSE_SCROLLED,
    KEYBOARD_INPUT,
    RAW_MOUSE_EVENT;

    public static Optional<EventType> eventFromName(String name) {
        return Arrays.stream(EventType.values()).filter(et -> et.name().equals(name)).findFirst();
    }
}