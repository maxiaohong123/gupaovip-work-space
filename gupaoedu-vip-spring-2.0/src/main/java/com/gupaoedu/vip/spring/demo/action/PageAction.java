package com.gupaoedu.vip.spring.demo.action;

import com.gupaoedu.vip.spring.demo.service.IQueryService;
import com.gupaoedu.vip.spring.framework.annotation.GPAutowired;
import com.gupaoedu.vip.spring.framework.annotation.GPController;
import com.gupaoedu.vip.spring.framework.annotation.GPRequestMapping;
import com.gupaoedu.vip.spring.framework.annotation.GPRequestParam;
import com.gupaoedu.vip.spring.framework.webmvc.GPModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maxiaohong on 2019-03-02.
 */
@GPController
@GPRequestMapping("/")
public class PageAction {

    @GPAutowired
    private IQueryService iQueryService;

    @GPRequestMapping("/first.html")
    public GPModelAndView query(@GPRequestParam("teacher") String teacher){
        String result = iQueryService.query(teacher);
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("teacher", teacher);
        model.put("data", result);
        model.put("token", "123456");
        return new GPModelAndView("first.html",model);
    }

}
