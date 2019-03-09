package com.gupaoedu.vip.spring.framework.context;

import com.gupaoedu.vip.spring.demo.action.MyAction;
import com.gupaoedu.vip.spring.framework.annotation.GPAutowired;
import com.gupaoedu.vip.spring.framework.annotation.GPController;
import com.gupaoedu.vip.spring.framework.annotation.GPService;
import com.gupaoedu.vip.spring.framework.beans.BeanDefinition;
import com.gupaoedu.vip.spring.framework.beans.BeanPostProcessor;
import com.gupaoedu.vip.spring.framework.beans.BeanWrapper;
import com.gupaoedu.vip.spring.framework.context.support.BeanDefinitionReader;
import com.gupaoedu.vip.spring.framework.core.BeanFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maxiaohong on 2019-02-24.
 * @desc:这是IOC容器的高级工厂，相当于Spring中的AbstractApplicationContext
 */
public class GPApplicationContext implements BeanFactory {
    private String [] configLocations;

    private BeanDefinitionReader reader;

    //beanDefinitionMap就是IOC容器中用来存放BeanDefinition的一个Map容器，用来保存bean的配置信息
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    //用来保证注册式单例的容器
    private Map<String,Object> beanCacheMap = new HashMap<String, Object>();

    //用来存储所有的被代理过的对象
    private Map<String,BeanWrapper> beanWrapperMap = new ConcurrentHashMap<String, BeanWrapper>();
   public GPApplicationContext(String ... locations){
       this.configLocations = locations;
       refresh();
   }

    public void refresh(){
        //定位
       this.reader = new BeanDefinitionReader(configLocations);

        //加载
        List<String> beanDefinitions =  reader.loadBeanDefinitions();

        //注册
        doRegistry(beanDefinitions);
        //依赖注入（lazy-init=false 要执行依赖注入，也就是自动调用getBean方法）

        doAutowired();

//        MyAction myAction = (MyAction) this.getBean("myAction");
//         myAction.query(null,null,"tom");
    }

    //开始执行自动化的依赖注入
    private void doAutowired() {
        //1、在Spring中，当我们向spring容器调用getBean方法的时候，spring才开始实例化容器中的Bean实例 。所以先实例化所有Bean。
        for(Map.Entry<String,BeanDefinition> beanDefinitionEntry:this.beanDefinitionMap.entrySet()){
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }

        //2、再注入属性。
        for(Map.Entry<String,BeanWrapper> beanWrapperEntry:this.beanWrapperMap.entrySet()){
           populateBean(beanWrapperEntry.getKey(),beanWrapperEntry.getValue().getWrappedInstance());
        }


    }

    public void populateBean(String beanName,Object instance){
        Class clazz = instance.getClass();

        //只有标GPController和GPService的类，才进行实例化，否则，我不认识，不进行实例化。
        if(!(clazz.isAnnotationPresent(GPController.class)||clazz.isAnnotationPresent(GPService.class))){
          return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for(Field filed:fields){
            if(!filed.isAnnotationPresent(GPAutowired.class))continue;
             GPAutowired autowired = filed.getAnnotation(GPAutowired.class);
             String autowiredBeanName = autowired.value();
             if("".equals(autowiredBeanName)){
                 autowiredBeanName = filed.getType().getName();

             }
             filed.setAccessible(true);
            try {
                System.out.println("============="+instance+","+autowiredBeanName+","+this.beanWrapperMap.get(autowiredBeanName));
                filed.set(instance,this.beanWrapperMap.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    //真正的将BeanDefinition注册到beanDefinitionMap中。
    private void doRegistry(List<String> beanDefinitions) {
        try{
            //1 beanName有三种情况
            //第一种：默认是类名首字母小写
            //第二种：自定义名称
            //第三种：接口注入
            for(String className:beanDefinitions){
                Class<?> beanClass =Class.forName(className);
                //如果是一个接口，是不能实例化的，用它的实现类来实例化
                if(beanClass.isInterface())continue;
                BeanDefinition beanDefinition = reader.registerBean(className);
                if(beanDefinition!=null){
                    this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
                }

                Class<?>[] instfaces = beanClass.getInterfaces();
                for(Class<?> i:instfaces){
                    //如果是多个实现类，只能覆盖
                    //因为Spring没有那么智能，就是这么傻。
                    //这个时候，可以自定义名字
                    this.beanDefinitionMap.put(i.getName(),beanDefinition);
                }
               //到这里为止，容器初始化完毕。
            }

        }
        catch (Exception e){

        }

    }

    //通过读取ＢeanDefinition中的信息
    //然后通过反射机制创建一个实例并返回
    //Spring的做法是，不会把原始的对象放出去，会用一个BeanWrapper来进行一次包装。
    //包装器模式：1、保留原来的OOP关系 2、我需要对他进行扩展、增强(为了以后的AOP打基础)
    @Override
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        String className = beanDefinition.getBeanClassName();
        try {
            //生成通知事件
            BeanPostProcessor beanPostProcessor = new BeanPostProcessor();

            Object instance = instantionBean(beanDefinition);
            if(null==instance){
                return null;
            }
            //在实例初始化以前调用一次
            beanPostProcessor.postProcessAfterInitialization(instance,beanName);
            BeanWrapper beanWrapper = new BeanWrapper(instance);
            beanWrapper.setBeanPostProcessor(beanPostProcessor);
            this.beanWrapperMap.put(beanName,beanWrapper);

            //在实例初始化以后调用一次
            beanPostProcessor.postProcessAfterInitialization(instance,beanName);
           // populateBean(beanName,instance); //此处还不能注入Bean,因为有可能注入的那个实例属性还没有实例化
            //通过这样一调用，相当于给我们自己留有了可操作的空间。
            return this.beanWrapperMap.get(beanName).getWrappedInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //传入一个BeanDefinition，就返回一个实例Bean
    private Object instantionBean(BeanDefinition beanDefinition){
        Object instance = null;
        String className = beanDefinition.getBeanClassName();
        try {

           if(this.beanCacheMap.containsKey(className)){
               instance = this.beanCacheMap.get(className);
           }else{
               Class<?> clazz = Class.forName(className);
               instance = clazz.newInstance();
               this.beanCacheMap.put(className,instance);
           }
           return  instance;
        }catch (Exception e){

        }
        return null;
    }

    public int getBeanDefinitionCount() {

        return this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {

        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }
}
