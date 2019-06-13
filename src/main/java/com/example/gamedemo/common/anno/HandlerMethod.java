package com.example.gamedemo.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: wengj
 * @date: 2019/5/7
 * @description: 定义请求处理注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HandlerMethod {
    String cmd();
}
