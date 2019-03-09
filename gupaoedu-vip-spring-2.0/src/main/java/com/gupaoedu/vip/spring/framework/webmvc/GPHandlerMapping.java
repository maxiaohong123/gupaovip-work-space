package com.gupaoedu.vip.spring.framework.webmvc;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by Maxiaohong on 2019-03-03.
 */
public class GPHandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern;

    public GPHandlerMapping( Pattern url,Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = url;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
