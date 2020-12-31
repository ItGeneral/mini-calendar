package com.mini.db.annotation;

import com.mini.db.handler.RecDBRegistrar;
import org.springframework.context.annotation.Import;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RecDBRegistrar.class})
public @interface EnableRecDB {

    String name() default "";

    /**
     * mapper 扫描包路径
     * @return
     */
    String[] basePackages() default {};
}
