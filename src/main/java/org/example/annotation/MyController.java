package org.example.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author heyefu
 * Create in: 2019-12-16
 * Time: 11:06
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {

    String value() default "";
}
