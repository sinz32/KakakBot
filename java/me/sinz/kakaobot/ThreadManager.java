package me.sinz.kakaobot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private static ThreadManager instance = null;

    private final ExecutorService pool;

    private ThreadManager() {
        pool = Executors.newCachedThreadPool();
    }

    public static ThreadManager getInstance() {
        if (instance == null) instance = new ThreadManager();
        return instance;
    }

    public void execute(Runnable runnable) {
        pool.execute(runnable);
    }


}
