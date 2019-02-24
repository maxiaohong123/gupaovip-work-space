package com.gupaoedu.vip.spring.framework.beans;

import com.gupaoedu.vip.spring.framework.core.FactoryBean;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
public class BeanWrapper extends FactoryBean {
    //还会用到观察者，模式
    //1、支持事件响应，会有一个监听
    private BeanPostProcessor beanPostProcessor;
    private Object  wrapperInstance;
    //这是原始的通过反射new出来，要把它包装，存下来。
    private Object originalInstance;
    public BeanWrapper(Object instance) {
       this.wrapperInstance = instance;
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

    public BeanPostProcessor getBeanPostProcessor() {
        return beanPostProcessor;
    }

    public void setBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessor = beanPostProcessor;
    }
}
