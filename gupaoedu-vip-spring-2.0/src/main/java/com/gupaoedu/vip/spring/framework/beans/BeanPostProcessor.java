package com.gupaoedu.vip.spring.framework.beans;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
//作用：用于做事件监听的
public class BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)  {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)  {
        return bean;
    }
}
