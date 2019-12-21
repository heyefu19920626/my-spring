package org.example.annotation;

import java.lang.annotation.*;

/**
 * Description:
 *
 * @author heyefu
 * Create in: 2019-12-16
 * Time: 11:15
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {

    /**
     * 参数别名，必填.
     */
    String value();
}
