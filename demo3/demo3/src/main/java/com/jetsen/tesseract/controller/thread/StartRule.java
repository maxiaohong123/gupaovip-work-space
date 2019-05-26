package com.jetsen.tesseract.controller.thread;

/**
 * Created by Maxiaohong on 2019-05-19.
 */
public class StartRule {
    static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println(x);
            }
        };
        x = 100;
        t1.start();
    }
}
