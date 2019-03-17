package com.gupaoedu.vip.spring.framework.beans;

import com.gupaoedu.vip.spring.framework.aop.GPAopConfig;
import com.gupaoedu.vip.spring.framework.aop.GPAopProxy;
import com.gupaoedu.vip.spring.framework.core.GPFactoryBean;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
public class GPBeanWrapper extends GPFactoryBean {
    private GPAopProxy aopProxy = new GPAopProxy();

    //还会用到观察者，模式
    //1、支持事件响应，会有一个监听
    private GPBeanPostProcessor beanPostProcessor;
    private Object  wrapperInstance;
    //这是原始的通过反射new出来，要把它包装，存下来。
    private Object originalInstance;
    public GPBeanWrapper(Object instance) {
        //从这里开始，我们要把动态代理的代码添加进来。
        //1、springmvc2.0 没有加入AOP之前时，原生对象和wrapper对象是一样的。
       //this.wrapperInstance = instance;
        //2、springmvc2.0 加入AOP之后时，wrapper就是代理对象
        this.wrapperInstance = aopProxy.getProxy(instance);
       this.originalInstance = instance;
    }

    public Object getWrappedInstance(){
        return  this.wrapperInstance;
    }

    //返回代理以后的Class
    //可能会是$Proxy0
    public Class<?> getWrappedClass(){
        return  this.wrapperInstance.getClass();
    }

    public GPBeanPostProcessor getBeanPostProcessor() {
        return beanPostProcessor;
    }

    public void setBeanPostProcessor(GPBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessor = beanPostProcessor;
    }

    public void setAopConfig(GPAopConfig config){
        aopProxy.setConfig(config);
    }

    public Object getOriginalInstance() {
        return originalInstance;
    }
}
