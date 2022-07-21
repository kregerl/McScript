package com.loucaskreger.mcscript.util;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

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
     * @param <T> The type T that
     * @param <S>
     */
    public static abstract class LuaSetter<T, S> extends OneArgFunction {
        protected final BiConsumer<T, S> setter;
        protected final String name;

        public LuaSetter(String name, BiConsumer<T, S> setter) {
            this.name = name;
            this.setter = setter;
        }
    }
}
