package com.gupaoedu.vip.demo.mvc.action;

import com.gupaoedu.vip.demo.service.IDemoService;
import com.gupaoedu.vip.spring.annotation.GPAutowired;
import com.gupaoedu.vip.spring.annotation.GPController;
import com.gupaoedu.vip.spring.annotation.GPRequestMapping;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
@GPController
public class MyAction {

       @GPAutowired
        IDemoService demoService;

        @GPRequestMapping("/index.html")
        public void query(){

        }

    }


