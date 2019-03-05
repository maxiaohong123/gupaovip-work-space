package com.gupaoedu.vip.spring.annotation;

import java.lang.annotation.*;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
