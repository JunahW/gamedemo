package com.example.gamedemo.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: wengj
 * @date: 2019/6/11
 * @description: 事件处理注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReceiverHandler {}
