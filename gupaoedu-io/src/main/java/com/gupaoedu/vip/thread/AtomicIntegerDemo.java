package com.gupaoedu.vip.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
     private static AtomicInteger count = new AtomicInteger(0);
     private static int count1;
     public static synchronized void incr(){
         try {
             Thread.sleep(1);
         } catch (Exception e) {
             e.printStackTrace();
         }
        // count.getAndIncrement();
         count1++;
     }

    public static void main(String[] args) throws Exception{
        for(int i=0;i<1000;i++){
            new Thread(){
                @Override
                public void run() {
                    AtomicIntegerDemo.incr();
                }
            }.start();
        }

        Thread.sleep(4000);
        System.out.println(count1);
    }

}
