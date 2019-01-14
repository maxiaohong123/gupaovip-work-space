package com.gupaoedu.vip.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo1 {
      static Lock lock = new ReentrantLock();
      private static int count = 0;
      public   static  void incr(){
          lock.lock();
          try {
              Thread.sleep(2);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }

          count++;
          lock.unlock();
      }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<1000;i++){
           new Thread(){
               @Override
               public void run() {
                   AQSDemo1.incr();
               }
           }.start();
        }

        Thread.sleep(3000);
        System.out.println(AQSDemo1.count);
    }
}
