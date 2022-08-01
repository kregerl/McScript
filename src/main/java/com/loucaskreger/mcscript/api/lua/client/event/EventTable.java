package com.loucaskreger.mcscript.api.lua.client.event;

import com.loucaskreger.mcscript.util.LuaUtils;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.Optional;
import java.util.function.Consumer;

public class EventTable extends LuaTable {

    public EventTable(Event event) {
        super();
        this.set("EventPriority", LuaUtils.tableFromEnum(EventPriority.class));
        this.set("Result", LuaUtils.tableFromEnum(Event.Result.class));

        this.set("isCancelable", valueOf(event.isCancelable()));
        this.set("isCanceled", valueOf(event.isCanceled()));
        this.set("getPriority", valueOf(event.getPhase().name()));
        this.set("hasResult", valueOf(event.hasResult()));
        this.set("getResult", valueOf(event.getResult().name()));

        this.set("setCanceled", new EventValueConsumer<LuaBoolean>("setCanceled", luaValue -> event.setCanceled(luaValue.booleanValue())));
//        this.set("setPhase", new EventValueConsumer<LuaString>("setCanceled", luaString -> event.setPhase()));
//        this.set("setCanceled", new EventValueConsumer<LuaBoolean>("setCanceled", luaValue -> event.setCanceled(luaValue.booleanValue())));

    }

    private static class EventValueConsumer<T extends LuaValue> extends LuaUtils.LuaConsumer<T> {

        public EventValueConsumer(String name, Consumer<T> consumer) {
            super(name, consumer);
        }

        @Override
        public LuaValue call(LuaValue arg) {
            Optional<T> typedArg = LuaUtils.tryConvert(arg);
            if (typedArg.isPresent()) {
                this.consumer.accept(typedArg.get());
            } else {
                throw new LuaError("Illegal event parameter in function: " + this.name);
            }
            return NIL;
        }
    }


    public static class ClientTickEvent extends EventTable {

        public ClientTickEvent(TickEvent.ClientTickEvent event) {
            super(event);

        }

    }

}
