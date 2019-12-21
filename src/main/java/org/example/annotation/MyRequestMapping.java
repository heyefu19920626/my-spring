package org.example.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author heyefu
 * Create in: 2019-12-16
 * Time: 11:10
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {

    String value() default "";
}
