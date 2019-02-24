package com.gupaoedu.vip.spring.annotation;

import java.lang.annotation.*;

/**
 * Created by Maxiaohong on 2019-02-24.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default "";
}
