package com.gupaoedu.vip.thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(){
            @Override
            public void run() {
                System.out.println("11");
                countDownLatch.countDown();
                System.out.println("1");
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("22");
                countDownLatch.countDown();
                System.out.println("2");
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("33");
                countDownLatch.countDown();
                System.out.println("3");
            }
        }.start();


        countDownLatch.await();
        System.out.println("执行完毕");
    }
}
