package com.gupaoedu.vip.spring.framework.webmvc.servlet;


import com.gupaoedu.vip.spring.framework.annotation.GPController;
import com.gupaoedu.vip.spring.framework.annotation.GPRequestMapping;
import com.gupaoedu.vip.spring.framework.annotation.GPRequestParam;
import com.gupaoedu.vip.spring.framework.context.GPApplicationContext;
import com.gupaoedu.vip.spring.framework.webmvc.GPHandlerAdapter;
import com.gupaoedu.vip.spring.framework.webmvc.GPHandlerMapping;
import com.gupaoedu.vip.spring.framework.webmvc.GPModelAndView;
import com.gupaoedu.vip.spring.framework.webmvc.GPViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maxiaohong on 2019-02-23.
 * @desc  这是手写spring的mini版本1.0  ，描述了spring框架中的定位、加载 、注册、依赖注入的全过程，虽然没有spring的详细，但是整体体现了spring框架的定位、加载、注册、依赖注入的全过程。
 */
public class DispatchServlet extends HttpServlet {

    private final String  LOCATION = "contextConfigLocation";

  //  private Map<String,GPHandlerMapping> handlerMapping = new HashMap<String, GPHandlerMapping>();

    //HandlerMapping是SpringMVC最核心的设计 ，也是最 经典的。
    //因为它直接替换了Struts \Webwork  等MVC框架
    private List<GPHandlerMapping> handlerMappings = new ArrayList<GPHandlerMapping>();

    private Map<GPHandlerMapping ,GPHandlerAdapter> handlerAdapters = new HashMap<GPHandlerMapping,GPHandlerAdapter>();
    private List<GPViewResolver> viewResolvers = new ArrayList<GPViewResolver>();




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
       //看到07（下） 46：32 了
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//  这是纯Servlet访问方式
      //  resp.getWriter().write("hello,gupao visionvera.com");
        //------------------------------------
//        String url = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        url = url.replace(contextPath,"").replaceAll("/+","/");
//       GPHandlerMapping handler = handlerMapping.get(url);
//
//        try {
//            GPModelAndView mv = (GPModelAndView) handler.getMethod().invoke(handler.getController(),null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        //这是结合spring实现的springMVC方式
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("500 Exception,Detailes:\r\n"+Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]","").replaceAll("\\s","\r\n")+"@GupaoMVC");
        }


    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
            GPHandlerMapping handler = getHandler(req);
             if(handler==null){
                 resp.getWriter().write("404 Not Found\r\n @GupaoMVC");
                 return;
             }
            GPHandlerAdapter ha = getHandlerAdapter(handler);

             //调用handle方法，得到返回值
            GPModelAndView mv = ha.handle(req,resp,handler);
            //返回给客户端
            processDispatchResult(resp,mv);

    }

    private void processDispatchResult(HttpServletResponse resp, GPModelAndView mv) throws Exception{
        //调用viewResolver的resolveView方法
        if(null==mv) return;
        if(this.viewResolvers.isEmpty()){
            return;
        }
        for(GPViewResolver viewResolver:this.viewResolvers){
            if(!mv.getViewName().equals(viewResolver.getViewName())){
               continue;
            }
            String out = viewResolver.viewResolver(mv);
            if(out!=null){
                resp.getWriter().write(out);
                break;
            }
        }

    }

    private GPHandlerAdapter getHandlerAdapter(GPHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){
            return  null;
        }
        return  this.handlerAdapters.get(handler);
    }

    private GPHandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()){return  null;}
         String url = req.getRequestURI();
         String contextPath = req.getContextPath();
         url = url.replace(contextPath,"").replaceAll("/+","/");

         for(GPHandlerMapping handlerMapping:this.handlerMappings){
              Matcher matcher = handlerMapping.getPattern().matcher(url);
              if(!matcher.matches())continue;
              return handlerMapping;
         }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
//        System.out.println("调用init方法");
       // 开始初始化过程(相当于SpringIOC容器初始化了)
        GPApplicationContext context = new GPApplicationContext(config.getInitParameter(LOCATION));

        //初始化九大组件
        initStrategies(context);
    }

    


    protected void initStrategies(GPApplicationContext context) {

        //==============这就是传说中的SpringMVC中的9大组件==================
        initMultipartResolver(context);//文件上传解析，如果请求类型是multipart，将通过MultipartResolver进行文件上传解析
        initLocaleResolver(context);//本地化解析
        initThemeResolver(context);//主题解析

        //自己实现
        //用来保存Controller配置的RequestMapping和Method的一个对应关系
        initHandlerMappings(context);//通过HandlerMapping将请求映射到处理器
        //自己实现
        //用来动态匹配Method参数，包括类型转换，动态赋值
        initHandlerAdapters(context);//通过HandlerAdapter进行动态参数赋值



        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);


        //自己实现
        //通过veiwResolver来实现动态模板的解析
        initViewResolvers(context);//通过viewResolver解析逻辑视图到具体视图实现





        initFlashMapManager(context);//flash映射管理器
    }

    private void initFlashMapManager(GPApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {
    }
    private void initRequestToViewNameTranslator(GPApplicationContext context) {
    }

    private void initThemeResolver(GPApplicationContext context) {
    }

    private void initLocaleResolver(GPApplicationContext context) {
    }

    private void initMultipartResolver(GPApplicationContext context) {
    }


    //=============以下为自己实现的方法==================
    private void initHandlerAdapters(GPApplicationContext context) {
        //在初始化阶段，我们能做的就是将这些参数的名字或者类型按一定的顺序保存下来。
        //因为后面反射调用的时候，传的形参是一个数组
        //可以通过记录这些参数的位置index,挨个从数组中填值，这样的话，就与顺序无关了。
        for(GPHandlerMapping handlerMapping:handlerMappings){
            //每一个方法都 有一个参数列表， 这里保存的是形参列表
            Map<String,Integer> paramMapping = new HashMap<String, Integer>();

            //这里只是处理命名参数
            Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
            for(int i=0;i<pa.length;i++){
                for(Annotation a:pa[i]){
                    if(a instanceof GPRequestParam){
                        String paramName = ((GPRequestParam) a).value();
                        if(!"".equals(paramName)){
                            paramMapping.put(paramName,i);
                        }
                    }
                }
            }

            //处理命名参数，只处理request和response
            Class<?>[] paramTypes = handlerMapping.getMethod().getParameterTypes();
            for(int i=0;i<paramTypes.length;i++){
                Class<?> type = paramTypes[i];
                //注意：Object判断是否属于某个类，用instanceof   ;Class 判断是否属于某个类，用==号
                if(type==HttpServletRequest.class||type==HttpServletResponse.class){
                    paramMapping.put(type.getName(),i);
                }
            }

            this.handlerAdapters.put(handlerMapping,new GPHandlerAdapter(paramMapping));




        }

    }
    private void initHandlerMappings(GPApplicationContext context) {
     //按照我们通常的理解应该是一个Map
        //Map<String,Method> map   ,map.put(url,method);
        //-------------以上是Map<String,Method> 的实现方法,这种方法有缺陷的-------------------------
     // ----------------以下开始List<GPHandlerMapping>的写法-------------
        //1、 首先从容器中获取所有实例
        String [] beanNames = context.getBeanDefinitionNames();
        for(String beanName:beanNames){
            Object controller = context.getBean(beanName);
            Class<?> clazz = controller.getClass();
            if(!clazz.isAnnotationPresent(GPController.class)) continue;
            String baseUrl = "";

            if(clazz.isAnnotationPresent(GPRequestMapping.class)){
                GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //2.获取到所有Controller的方法
            Method[] methods = clazz.getMethods();
            for(Method method:methods){
                if(!method.isAnnotationPresent(GPRequestMapping.class)){continue;}
                GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
                String regex =("/" + baseUrl +requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                Pattern pattern = Pattern.compile(regex);
                //3. 将Controller-->method中的url拼接起来封装为一个Pattern，Controller,method,pattern三者封装为一个GPHandlerMapping放入handlerMapping集合中。
                this.handlerMappings.add(new GPHandlerMapping(pattern,controller,method));
                System.out.println("Mapping:"+regex+","+method);
            }
        }

    }

    private void initViewResolvers(GPApplicationContext context) {
       //在页面敲一个http://localhost:8080/first.html
        //解决页面名字和模板文件关联的问题
        String templateRoot = context.getConfig().getProperty("templateRoot");
       String templateRootPath =  this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);
        for(File file:templateRootDir.listFiles()){
         this.viewResolvers.add(new GPViewResolver(file.getName(),file));
        }



    }


    private String lowerFirst(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }
}
