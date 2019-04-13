package com.gupaoedu.vip;

import com.gupaoedu.vip.spring.framework.context.GPApplicationContext;

/**
 * Created by Maxiaohong on 2019-04-13.
 */
public class Test {
    public static void main(String[] args) {
        GPApplicationContext context = new GPApplicationContext("classpath:application.properties");
        System.out.println(context);
    }
}
