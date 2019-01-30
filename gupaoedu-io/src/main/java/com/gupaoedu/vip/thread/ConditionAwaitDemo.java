package com.gupaoedu.vip.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ConditionAwaitDemo extends Thread {
    private Lock lock ;
    private Condition condition;

    public ConditionAwaitDemo(Lock lock, Condition condition) {
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("begin await...");
            condition.await();
            System.out.println("end await");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
