package com.gupaoedu.vip.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Car extends Thread {
    private int num;
    private Semaphore semaphore;
    public Car(int num,Semaphore semaphore){
        this.num = num;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("第"+num+"个车位占用");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("第"+num+"个车离开");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<20;i++){
            new Car(i,semaphore).start();
        }
    }

}
