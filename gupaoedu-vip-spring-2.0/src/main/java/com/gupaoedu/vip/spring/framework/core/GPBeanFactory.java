package com.gupaoedu.vip.spring.framework.core;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
public interface GPBeanFactory {
    //从IOC容器之中获得一个实例bean
    Object getBean(String beanName);
}
