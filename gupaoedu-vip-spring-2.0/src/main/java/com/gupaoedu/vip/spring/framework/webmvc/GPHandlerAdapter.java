package com.gupaoedu.vip.spring.framework.webmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maxiaohong on 2019-03-03.
 */
public class GPHandlerAdapter {
   private Map<String,Integer> paramMapping = new HashMap<String, Integer>();

    public GPHandlerAdapter(Map<String, Integer> paramMapping) {
        this.paramMapping = paramMapping;
    }

    /**
     *
     * @param req
     * @param resp
     * @param handler 为什么要把Handler 传进来，因为handler中包括了Controller,Method ,url信息
     * @return
     */
    public GPModelAndView handle(HttpServletRequest req, HttpServletResponse resp, GPHandlerMapping handler) {

        //根据用户请求的参数信息，跟method中的参数信息进行动态匹配
        //resq: 传进来的目的只有一个，只是为了将其赋值给方法参数，仅此而已。因为req,resp是不能new 的，只能是来回传递。

        //GPModelAndView什么时间new呢？只有当用户 传过来的ModelAndView,才会new一个默认的。

      return  null;
    }
}
