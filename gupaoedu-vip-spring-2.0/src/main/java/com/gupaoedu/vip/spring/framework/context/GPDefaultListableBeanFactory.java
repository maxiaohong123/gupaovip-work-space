package com.gupaoedu.vip.spring.framework.context;

import com.gupaoedu.vip.spring.framework.beans.GPBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maxiaohong on 2019-03-17.
 */
public class GPDefaultListableBeanFactory  extends GPAbstractApplicationContext{
    //beanDefinitionMap就是IOC容器中用来存放BeanDefinition的一个Map容器，用来保存bean的配置信息
    protected Map<String,GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, GPBeanDefinition>();
    protected  void onRefresh(){

    }
    @Override
    protected void refreshBeanFactory() {

    }
}
