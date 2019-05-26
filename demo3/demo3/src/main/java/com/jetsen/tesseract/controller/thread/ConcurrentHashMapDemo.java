package com.jetsen.tesseract.controller.thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maxiaohong on 2019-05-19.
 */
public class ConcurrentHashMapDemo {

    private  static  ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

    public static  void on(){
        concurrentHashMap.put("name","tom");
    }
}
