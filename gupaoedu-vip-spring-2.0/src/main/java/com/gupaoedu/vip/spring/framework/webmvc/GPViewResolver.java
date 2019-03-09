package com.gupaoedu.vip.spring.framework.webmvc;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public String viewResolver(GPModelAndView mv) throws Exception
    {
        StringBuffer sb = new StringBuffer();
        RandomAccessFile ra = new RandomAccessFile(this.templateFile,"r");
        String line = null;
        while (null!=(line=ra.readLine())){
            Matcher m = matcher(line);
            while (m.find()){
                for(int i=1;i<m.groupCount();i++){
                    //要把￥{}中间的这个字符串给取出来
                    String paramName = m.group(i);
                    Object paramValue = mv.getModel().get(paramName);
                    if(null==paramName)continue;
                    line = line.replaceAll("￥\\{"+paramName+"\\}",paramValue.toString());

                }
            }
            sb.append(line);
        }
        return  null;
    }

    private Matcher matcher(String str){
        Pattern pattern = Pattern.compile("￥\\{(.+?)\\}",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return  matcher;
    }
}
