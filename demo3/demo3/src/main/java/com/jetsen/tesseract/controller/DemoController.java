package com.jetsen.tesseract.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class DemoController {

    @RequestMapping("/demo1")
    public String demo(String name){
        String str = "hello:"+name;
        System.out.println("hello:"+name);
        return  str;
    }
}
