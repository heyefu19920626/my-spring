package org.example.controller;

import org.example.annotation.MyController;
import org.example.annotation.MyRequestMapping;

/**
 * Description:
 *
 * 测试自己的控制器
 *
 * @author heyefu
 * Create in: 2020-01-02
 * Time: 下午9:18
 **/
@MyController
public class TestController {

    @MyRequestMapping("test")
    public void testMapping() {

        System.out.println("MyRequestMapping");
    }
}
