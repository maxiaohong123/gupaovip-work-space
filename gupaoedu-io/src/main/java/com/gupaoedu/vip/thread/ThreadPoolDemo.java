package com.gupaoedu.vip.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo implements  Runnable{
    static ExecutorService executorService = Executors.newFixedThreadPool(100);
    static ExecutorService executorService1 = Executors.newSingleThreadExecutor();
    static ExecutorService executorService2 = Executors.newCachedThreadPool();
    static ExecutorService executorService3 = Executors.newScheduledThreadPool()


    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            executorService.execute(new ThreadPoolDemo());
            executorService2.execute(new ThreadPoolDemo());
        }
        executorService.shutdown();

    }
}
