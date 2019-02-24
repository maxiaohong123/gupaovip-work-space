package com.gupaoedu.vip.spring.framework.context.support;

import com.gupaoedu.vip.spring.framework.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
//作用：用来对配置文件进行查找、读取、解析
public class BeanDefinitionReader {

    private Properties config = new Properties();

    private List<String> registryBeanClasses = new ArrayList<String>();

    //在配置文件中，用来获取自动扫描的包名key
    private static final String SCAN_PACKAGE = "scanPackage";
    public BeanDefinitionReader(String ... locations){
        //在Spring中，是通过Reader去查找和定位。
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
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

        doScanner(config.getProperty(SCAN_PACKAGE));

    }

    public List<String> loadBeanDefinitions(){
        return null;
    }

    //每注册一个className,就返回一个BeanDefinition自己包装。
   public BeanDefinition registerBean(String className){
        if(this.registryBeanClasses.contains(className)){
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(lowerFirst(className.substring(className.lastIndexOf(".")+1)));
            return  beanDefinition;
        }
       return null;
   }

   public Properties getConfig(){
       return  this.config;
   }

    private String lowerFirst(String str){
        char[] chars = str.toCharArray();
        chars[0]+=32;
        return String.valueOf(chars);
    }

   //递归扫描所有的相关的class,并且将扫描到的该包下的所有类名全称放到一个list中。
    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.","/"));
        File classDir = new File(url.getFile());
        for(File file: classDir.listFiles()){
            if(file.isDirectory()){
                doScanner(packageName+"."+file.getName());
            }else{
                registryBeanClasses.add(packageName+"."+file.getName().replace(".class",""));
            }
        }
    }
}
