package com.gupaoedu.vip.spring.framework.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maxiaohong on 2019-03-17.
 * 说明：只是针对application.properties中的expression的封装
 * 目标代理对象的一个方法要增强
 * 由用户自己实现的业务逻辑去增强
 * 配置文件的目的告诉spring,哪些类的哪些方法需要增强，增强的内容是什么
 * 对配置文件中所体现的内容进行封装
 */
public class GPAopConfig {

    //以目标对象的需要增强的Method作为key,需要增强的代码内容作为value
    private Map<Method,GPAspect> points = new HashMap<Method,GPAspect>();

    public  void put(Method target,Object aspect,Method[] points){
        this.points.put(target,new GPAspect(aspect,points));
    }

    public  GPAspect get(Method method){
        return this.points.get(method);
    }

    public  boolean contains(Method method){
        return this.points.containsKey(method);
    }

    //对增加的代码的封装
    public  class GPAspect{
        private  Object aspect; //将LogAspect这个对象赋值给它
        private  Method[] points;//将LogAspect的before和after方法赋值给它，所以是个Method数组

        public GPAspect(Object aspect,Method[] points){
            this.aspect = aspect;
            this.points = points;
        }

        public Object getAspect() {
            return aspect;
        }

        public Method[] getPoints() {
            return points;
        }
    }
}
