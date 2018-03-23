package com.daqsoft.config;

import java.lang.annotation.*;

/**
 * @author zhouq
 * @email zhouq@daqsoft.com
 * @date 2017-06-21 11:53
 * @Version:
 * @Describe: 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
