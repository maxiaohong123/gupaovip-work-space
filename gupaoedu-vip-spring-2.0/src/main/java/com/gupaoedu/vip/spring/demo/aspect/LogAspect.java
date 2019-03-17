package com.gupaoedu.vip.spring.demo.aspect;

/**
 * Created by Maxiaohong on 2019-03-17.
 * 说明：这个类相当于spring事务中的trancationManager，在执行sql之前建立连接，在执行sql之后关闭连接。
 */
public class LogAspect {

    //在调用一个方法之前 ，执行这个方法
    public  void before(){
        //这个方法的逻辑，由自己实现
        System.out.println("Invoker Before Method");
    }

    //在调用一个方法之后 ，执行这个方法
    public  void after(){
        //这个方法的逻辑，由自己实现
        System.out.println("Invoker After Method");
    }
}
