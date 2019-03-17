package com.gupaoedu.vip.spring.framework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Maxiaohong on 2019-03-17.
 */
//默认用JDK动态代理
public class GPAopProxy implements InvocationHandler {
    private GPAopConfig config;
    private  Object target;

   //把原生的对象传进来
    public Object getProxy(Object instance){
        this.target = instance;
        Class<?> clazz = instance.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public  void setConfig(GPAopConfig config){
        this.config = config;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());
        //在原始方法调用之前要执行的增加代码
        //通过原生方法去找，通过代理 方法是找不到的。
        if(config.contains(m)){
            GPAopConfig.GPAspect aspect = config.get(m);
            aspect.getPoints()[0].invoke(aspect.getAspect());

        }
        //反射调用原始的方法
        Object obj = method.invoke(this.target,args);
        //在原始方法调用之后要执行的增加代码
        if(config.contains(m)){
            GPAopConfig.GPAspect aspect = config.get(m);
            aspect.getPoints()[1].invoke(aspect.getAspect());

        }

        //将最原始的返回值返回去
        return obj;
    }
}


