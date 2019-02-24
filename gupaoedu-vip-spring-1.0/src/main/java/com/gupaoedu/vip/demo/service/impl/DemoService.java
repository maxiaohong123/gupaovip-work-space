package com.gupaoedu.vip.demo.service.impl;

import com.gupaoedu.vip.demo.service.IDemoService;
import com.gupaoedu.vip.spring.annotation.GPService;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
@GPService
public class DemoService implements IDemoService {
    @Override
    public String get(String name) {
        return "My name is " + name;
    }
}
