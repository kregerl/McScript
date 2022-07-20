package com.loucaskreger.mcscript.util;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
}
