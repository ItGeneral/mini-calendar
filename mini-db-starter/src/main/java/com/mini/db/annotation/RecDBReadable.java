package com.mini.db.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在某类上添加了@RecDB注解，但是未添加@RecDBWritable注解
 * 默认是添加了@RecDBWritable注解，并且会使用从库去注册数据源
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RecDBReadable {



}
