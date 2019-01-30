package com.gupaoedu.vip.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Waiter  {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println("1");


            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println("2");

            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                countDownLatch.countDown();
                System.out.println("3");

            }
        }.start();
       countDownLatch.await();
        System.out.println("这是main线程");
        System.out.println("这是main线程1");

    }

}
