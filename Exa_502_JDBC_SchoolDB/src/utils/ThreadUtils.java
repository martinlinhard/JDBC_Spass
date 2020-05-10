/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadUtils {

    private final ThreadPoolExecutor tps;
    private static final ThreadUtils UTILS;

    static {
        UTILS = new ThreadUtils(Runtime.getRuntime().availableProcessors());
    }

    public static void executeAsync(Runnable r) {
        UTILS.tps.execute(r);
    }

    public ThreadUtils(int number) {
        this.tps = (ThreadPoolExecutor) Executors.newFixedThreadPool(number);
    }

    public static void close() {
        UTILS.tps.shutdownNow();
    }
}
