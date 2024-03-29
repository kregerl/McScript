package com.loucaskreger.mcscript.util;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class LuaUtils {

    // Lua tables are created in the order {key1, value1, key2, value2}
    public static <T extends Enum<T>> LuaTable tableFromEnum(Class<T> enumClazz) {
        List<LuaValue> values = new ArrayList<>();
        for (T elem : EnumSet.allOf(enumClazz)) {
            LuaValue value = LuaValue.valueOf(elem.name());
            values.add(value);
            values.add(value);
        }
        return LuaTable.tableOf(values.toArray(new LuaValue[0]));
    }

    // TODO: Add a function to get the enum value from a enum.class and string that matches it.


    public static <T> Optional<T> tryConvert(LuaValue arg) {
        try {
            return Optional.of((T) arg);
        } catch (ClassCastException e) {
            return Optional.empty();
        }
    }

    /**
     * Getter base class for exposing Java functions to Lua.
     *
     * @param <T> The type that is expected as an input to the function {@link #getter}
     */
    public static abstract class LuaGetter<T> extends ZeroArgFunction {

        protected final Function<T, LuaValue> getter;
        protected final String name;

        public LuaGetter(String name, Function<T, LuaValue> getter) {
            this.name = name;
            this.getter = getter;
        }

        @Override
        public String toString() {
            return "function " + this.name + "(void)" + this.getter.toString();
        }
    }

    /**
     * The Setter base class for exposing Java functions to Lua.
     *
     * @param <T> The type T that is provides to the function caller
     * @param <S> The type S that is provides to the function caller
     */
    public static abstract class LuaSetter<T, S> extends OneArgFunction {
        protected final BiConsumer<T, S> setter;
        protected final String name;

        public LuaSetter(String name, BiConsumer<T, S> setter) {
            this.name = name;
            this.setter = setter;
        }
    }

    public static abstract class LuaConsumer<T extends LuaValue> extends OneArgFunction {
        protected Consumer<T> consumer;
        protected final String name;

        public LuaConsumer(String name, Consumer<T> consumer) {
            this.name = name;
            this.consumer = consumer;
        }
    }
}