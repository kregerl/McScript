package com.loucaskreger.mcscript.api.lua;

import com.loucaskreger.mcscript.util.LuaModule;
import com.loucaskreger.mcscript.util.LuaUtils;
import org.luaj.vm2.*;

import java.util.Optional;
import java.util.function.Consumer;

public class Registry extends LuaTable {

    public Registry(LuaModule module) {
        super();

        this.set("setName", new RegistryValueConsumer<LuaString>("setName", luaString -> module.setDisplayName(luaString.toString())));
        this.set("setAuthor", new RegistryValueConsumer<LuaString>("setAuthor", luaString -> module.setAuthor(luaString.toString())));
        this.set("setDescription", new RegistryValueConsumer<LuaString>("setDescription", luaString -> module.setDescription(luaString.toString())));
        this.set("setVersion", new RegistryValueConsumer<LuaString>("setVersion", luaString -> module.setVersion(luaString.toString())));
    }

    private static class RegistryValueConsumer<T extends LuaValue> extends LuaUtils.LuaConsumer<T> {

        private final String name;

        public RegistryValueConsumer(String name, Consumer<T> consumer) {
            super(consumer);
            this.name = name;
        }

        @Override
        public LuaValue call(LuaValue arg) {
            Optional<T> typedArg = LuaUtils.tryConvert(arg);
            if (typedArg.isPresent()) {
                this.consumer.accept(typedArg.get());
            } else {
                throw new LuaError("Illegal registry parameter: " + this.name);
            }
            return NIL;
        }
    }

}
