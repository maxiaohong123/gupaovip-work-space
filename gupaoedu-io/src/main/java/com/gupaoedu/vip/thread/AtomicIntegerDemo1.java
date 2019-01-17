package com.gupaoedu.vip.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo1 {
   // private static    int count = 0;
    private static  AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args)  throws Exception{
       Thread t1 =  new Thread(){
            @Override
            public void run() {
                for(int i=0;i<1000;i++){
                  //  count++;
                    count.incrementAndGet();
                }
            }
        };

       Thread t2 =  new Thread(){
            @Override
            public void run() {
                for(int i=0;i<1000;i++){
                   // count++;
                    count.incrementAndGet();
                }
            }
        };
       t1.start();
       t2.start();
       t1.join();
       t2.join();

        System.out.println("count的最终值；"+count);
    }
}
