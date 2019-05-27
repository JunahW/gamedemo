package com.example.gamedemo.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: wengj
 * @date: 2019/5/7
 * @description: 标明Excel的column
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    /**
     * 列的名称
     *
     * @return
     */
    String columnName();
}
