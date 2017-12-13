package org.springnettyrpc.simple;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ZHS
 * @create 2017-12-11 17:16
 */
public class SpringMain {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("simple/rpc-invoke-config.xml");

    }
}
