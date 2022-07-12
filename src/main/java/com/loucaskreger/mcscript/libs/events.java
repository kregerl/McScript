package com.loucaskreger.mcscript.libs;

import com.loucaskreger.mcscript.events.EventHandler;
import com.loucaskreger.mcscript.events.EventSubscriber;
import com.loucaskreger.mcscript.events.EventType;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class events extends TwoArgFunction {

    public events() {
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        System.out.println("HERE: " + modname);
        LuaValue lib = tableOf();
        lib.set("register", new register());
        env.set("events", lib);
        return lib;
    }

    static class register extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue arg1, LuaValue arg2) {
            System.out.println("IN JAVA1: " + arg1.tojstring());
            System.out.println("IN JAVA2: " + arg2.tojstring());
            System.out.println(arg1 instanceof LuaString && arg2 instanceof LuaClosure);
            // Use 1 based indexing
            if (arg1 instanceof LuaString && arg2 instanceof LuaClosure) {
                // TODO: Throw error if there's not the correct amount of args
                System.out.println("ID: " + EventType.valueOf(arg1.tojstring()) + " Number of args: " + countArgs((LuaClosure) arg2));
                arg2.call("testing");
                EventHandler.getInstance().addEventCallback(EventType.valueOf(arg1.tojstring()), (LuaClosure) arg2);
            }
            return LuaValue.valueOf(1);
        }

        private int countArgs(LuaFunction fn) {
            int index = 1;
            while (fn.arg(index) != LuaValue.NIL) {
                index++;
            }
            return index - 1;
        }
    }
}
