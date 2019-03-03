package com.gupaoedu.vip.spring.framework.webmvc.servlet;


import com.gupaoedu.vip.spring.framework.context.GPApplicationContext;

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

    private final String  LOCATION = "contextConfigLocation";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     //  doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("调用doPost方法1111111");
//        resp.getWriter().write("hello:maxiao");
        this.doGet(req,resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
//        System.out.println("调用init方法");
        //开始初始化过程
        GPApplicationContext context = new GPApplicationContext(config.getInitParameter(LOCATION));
         
        //看到spring(05下) 44：46分钟了，打算调试的那个时间点。
        initStrategies(context);
    }

    


    protected void initStrategies(GPApplicationContext context) {
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        //自己实现
        initHandlerMappings(context);
        //自己实现 
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        //自己实现 
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initFlashMapManager(GPApplicationContext context) {
    }

    private void initViewResolvers(GPApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(GPApplicationContext context) {
    }

    private void initHandlerAdapters(GPApplicationContext context) {
    }

    private void initHandlerMappings(GPApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(GPApplicationContext context) {
    }

    private void initThemeResolver(GPApplicationContext context) {
    }

    private void initLocaleResolver(GPApplicationContext context) {
    }

    private void initMultipartResolver(GPApplicationContext context) {
    }

    private String lowerFirst(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }
}
