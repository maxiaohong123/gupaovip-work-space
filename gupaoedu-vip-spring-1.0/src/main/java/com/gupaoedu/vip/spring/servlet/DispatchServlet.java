package com.gupaoedu.vip.spring.servlet;

import com.gupaoedu.vip.demo.mvc.action.DemoAction;
import com.gupaoedu.vip.spring.annotation.GPAutowired;
import com.gupaoedu.vip.spring.annotation.GPController;
import com.gupaoedu.vip.spring.annotation.GPService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maxiaohong on 2019-02-23.
 * @desc  这是手写spring的mini版本1.0  ，描述了spring框架中的定位、加载 、注册、依赖注入的全过程，虽然没有spring的详细，但是整体体现了spring框架的定位、加载、注册、依赖注入的全过程。
 */
public class DispatchServlet extends HttpServlet {
    private Properties contextConfig = new Properties();
    private Map<String,Object> beanMap = new ConcurrentHashMap<String,Object>();
    private List<String> classNames = new ArrayList<String>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用doPost方法1111111");
        resp.getWriter().write("hello:maxiaohong");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("调用init方法");
        //开始初始化过程
         
        //定位
         doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //加载
        doScanner(contextConfig.getProperty("scanPackage"));
        //注册
        doRegistry();
        //自动依赖注入
        //在Spring中是通过调 用getBean方法才触发依赖注入的。
        doAutowired();

        DemoAction action = (DemoAction) beanMap.get("demoAction");
        action.query(null,null,"tom");
        //如果是springmvc，会多设计一个HandlerMapping
        //将@RequestMapping中的配置的url和一个Method关联上，以便于从浏览器获得用户输入的url以后，能够 找到具体执行的Method通过反射去调用。
        //关于springmvc的访问在此版本中不实现，在手写2.0版本中实现
        initHandlerMapping();
    }

    private void initHandlerMapping() {
    }

    private void doRegistry() {
        if(classNames.isEmpty())return;
        try{
           for(String className:classNames){
               Class<?> clazz = Class.forName(className);
               //在spring中用得多个子方法来处理的
               if(clazz.isAnnotationPresent(GPController.class)){
                   String beanName = lowerFirst(clazz.getSimpleName());
                   //在Spring中在这个阶段是不会直接put instance，这里put的是BeanDefinition
                   beanMap.put(beanName,clazz.newInstance());
               }
               else if(clazz.isAnnotationPresent(GPService.class)){
                 GPService service = clazz.getAnnotation(GPService.class);
                 //默认用类名首字母注入
                //如果自己定义了beanName，那么优先使用自己定义的beanName
                //如果是一个接口，使用接口的类型自动注入

                //在Spring中，同样会分别调用不同的方法autowriedByName autowriedByType
                   String beanName = service.value();
                   if("".equals(beanName.trim())){
                       beanName = lowerFirst(clazz.getSimpleName());
                   }
                   Object instance = clazz.newInstance();
                   beanMap.put(beanName,instance);

                   Class<?>[] interfaces = clazz.getInterfaces();
                   for(Class<?> i:interfaces){
                       beanMap.put(i.getName(),instance);
                   }
               }else{
                   continue;
               }
           }
        }
        catch (Exception e){

        }
    }

    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.","/"));
        File classDir = new File(url.getFile());
        for(File file: classDir.listFiles()){
            if(file.isDirectory()){
                doScanner(packageName+"."+file.getName());
            }else{
                classNames.add(packageName+"."+file.getName().replace(".class",""));
            }
        }
    }

    private void doLoadConfig(String location) {
        //在Spring中，是通过Reader去查找和定位。
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath:",""));
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doAutowired() {
        if(beanMap.isEmpty())return;
        for(Map.Entry<String,Object> entry:beanMap.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for(Field field:fields){
                if(field.isAnnotationPresent(GPAutowired.class)){
                    GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                    String beanName = autowired.value().trim();
                    if("".equals(beanName)){
                        beanName = field.getType().getName();
                    }
                    field.setAccessible(true);
                    try {
                        //相当于给DemoAction设置属性值：如demo.setService(new ServiceImpl())
                        field.set(entry.getValue(),beanMap.get(beanName));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String lowerFirst(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }
}
