package com.loucaskreger.mcscript.lua;

import com.loucaskreger.mcscript.McScript;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LuaExecutor {

    private static int THREAD_COUNT = 4;
    private int threadCount;
    private Optional<ExecutorService> executor;
    private static final LuaExecutor INSTANCE = new LuaExecutor(THREAD_COUNT);
    private Optional<Future<?>> loopFuture = Optional.ofNullable(null);

    public static LuaExecutor getInstance() {
        return INSTANCE;
    }

    public LuaExecutor(int threadCount) {
        this.threadCount = threadCount;
        this.executor = Optional.of(Executors.newFixedThreadPool(threadCount));
    }

    public void setLoopFuture(Future<?> loopFuture) {
        this.loopFuture = Optional.ofNullable(loopFuture);
    }

    public void cancelActiveLoop() {
        if (loopFuture.isPresent()) {
            loopFuture.get().cancel(true);
            setLoopFuture(null);
        }
    }

    public void newExecutor() {
        executor = Optional.of(Executors.newFixedThreadPool(threadCount));
    }

    public Optional<ExecutorService> getExecutor() {
        synchronized (executor) {
            return executor;
        }
    }

    public Future<?> submit(Callable<?> callable) {
        McScript.LOGGER.info("Lua Executor Thread: " + Thread.currentThread());
//        McScript.LOGGER.info("Here");
        synchronized (executor) {
//            McScript.LOGGER.info("Synced");
            if (!executor.isPresent()) {
                McScript.LOGGER.info("Not Present in Callable");
            }
            return executor.get().submit(callable);
        }
    }

    public Future<?> submit(Runnable run) {
        McScript.LOGGER.info("Lua Executor Thread: " + Thread.currentThread());
//        McScript.LOGGER.info("Here");
        synchronized (executor) {
//            McScript.LOGGER.info("Synced");
            if (!executor.isPresent()) {
                McScript.LOGGER.info("Not Present in Runnable");
            }
            return executor.get().submit(run);
        }
    }
}
