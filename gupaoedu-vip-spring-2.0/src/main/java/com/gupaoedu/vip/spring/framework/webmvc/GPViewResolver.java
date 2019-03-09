package com.gupaoedu.vip.spring.framework.webmvc;

import java.io.File;

/**
 * Created by Maxiaohong on 2019-03-03.
 * 设计这个类的主要目的是：
 * 1、将一个静态文件变化一个动态文件
 * 2、根据用户传递的参数 不同，产生不同的结果
 * 3\、将结果转换为字符串，交给Response输出
 */
public class GPViewResolver {

    private String viewName;
    private File templateFile;
    public GPViewResolver(String viewName,File template) {
        this.viewName = viewName;
        this.templateFile = template;
    }

    public String viewResolver(GPModelAndView mv){
        return  null;
    }
}
