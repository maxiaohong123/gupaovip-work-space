package com.jetsen.tesseract.controller.thread;

/**
 * Created by Maxiaohong on 2019-05-19.
 */
public class StoreBufferTest extends Thread {
    int value = 3;
    boolean isFinsh;
    public static void main(String[] args) {
      for(int i=0;i<2;i++){
          new StoreBufferTest().start();
      }
    }

    @Override
    public void run() {
//      cpu0();
      cpu1();
    }

    public void cpu0(){
        value = 10;
        isFinsh = true;
    }
    public void cpu1(){
        if(isFinsh){
            System.out.println(222);
            System.out.println(value==10);
        }
    }

}
