package org.springnettyrpc.supportcode;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ZHS
 * @create 2017-12-15 11:24
 */
public class CSpringMain {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("supportcode/rpc-invoke-config-jdknative.xml");
        new ClassPathXmlApplicationContext("supportcode/rpc-invoke-config-kryo.xml");
        new ClassPathXmlApplicationContext("supportcode/rpc-invoke-config-hessian.xml");
    }
}
