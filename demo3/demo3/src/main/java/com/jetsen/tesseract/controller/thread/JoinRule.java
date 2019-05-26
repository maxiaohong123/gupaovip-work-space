package com.jetsen.tesseract.controller.thread;

/**
 * Created by Maxiaohong on 2019-05-19.
 */
public class JoinRule {
    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("t1");
            }
        };

        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println("t2");
            }
        };

        Thread t3 = new Thread(){
            @Override
            public void run() {
                System.out.println("t3");
            }
        };

        t1.start();
        t2.start();
        t3.start();
    }
}
