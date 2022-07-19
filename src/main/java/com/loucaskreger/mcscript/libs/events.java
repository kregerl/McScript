package com.loucaskreger.mcscript.libs;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class events extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue arg1, LuaValue arg2) {
        return null;
    }

//    public events() {
//    }
//
//    @Override
//    public LuaValue call(LuaValue modname, LuaValue env) {
//        System.out.println("HERE: " + modname);
//        LuaValue lib = tableOf();
//        lib.set("register", new register());
//        env.set("events", lib);
//        return lib;
//    }
//
//    static class register extends TwoArgFunction {
//
//        @Override
//        public LuaValue call(LuaValue arg1, LuaValue arg2) {
//            Optional<EventType> name = EventType.eventFromName(arg1.tojstring());
//            if (name.isPresent()) {
//                EventHandler.getInstance().addEventListener(name.get(), arg2.checkfunction());
//                return LuaValue.valueOf(true);
//            }
//            return LuaValue.valueOf(false);
////            McScript.LOGGER.info("Before: " + EventSubscriber.test.get());
////            EventSubscriber.test.getAndIncrement();
////            McScript.LOGGER.info("After: " + EventSubscriber.test.get());
////            System.out.println("IN JAVA1: " + arg1.tojstring());
////            System.out.println("IN JAVA2: " + arg2.tojstring());
////            System.out.println(arg1 instanceof LuaString && arg2 instanceof LuaClosure);
////            // Use 1 based indexing
////            if (arg1 instanceof LuaString && arg2 instanceof LuaClosure) {
////                // TODO: Throw error if there's not the correct amount of args
////                System.out.println("ID: " + EventType.valueOf(arg1.tojstring()) + " Number of args: " + countArgs((LuaClosure) arg2));
////                arg2.call("testing");
////                McScript.LOGGER.info(Thread.currentThread());
//////                EventHandler.addEventCallback(EventType.valueOf(arg1.tojstring()), (LuaClosure) arg2);
//////                EventHandler.getInstance().addEventCallback(EventType.valueOf(arg1.tojstring()), (LutestaClosure) arg2);
//////                EventSubscriber.callbacks.put(EventType.valueOf(arg1.tojstring()), (LuaClosure) arg2);
////            }
////            return LuaValue.valueOf(1);
//        }
//
//        private int countArgs(LuaFunction fn) {
//            int index = 1;
//            while (fn.arg(index) != LuaValue.NIL) {
//                index++;
//            }
//            return index - 1;
//        }
//    }
}
