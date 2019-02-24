package com.gupaoedu.vip.demo.mvc.action;

import com.gupaoedu.vip.demo.service.IDemoService;
import com.gupaoedu.vip.spring.annotation.*;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@GPController
@GPRequestMapping("/demo")
public class DemoAction {

    @GPAutowired private IDemoService demoService;

    @GPRequestMapping("/query.json")
    public void query(HttpServletRequest req,HttpServletResponse resp,
                      @GPRequestParam("name") String name){
        String result = demoService.get(name);
        System.out.println(result);
//		try {
//			resp.getWriter().write(result);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }

    @GPRequestMapping("/edit.json")
    public void edit(HttpServletRequest req,HttpServletResponse resp,Integer id){

    }

}
