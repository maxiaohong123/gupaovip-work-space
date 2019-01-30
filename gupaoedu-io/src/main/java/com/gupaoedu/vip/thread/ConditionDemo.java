package com.gupaoedu.vip.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo   {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new ConditionAwaitDemo(lock,condition){}.start();
        new ConditionSignalDemo(lock,condition){}.start();
    }
}
