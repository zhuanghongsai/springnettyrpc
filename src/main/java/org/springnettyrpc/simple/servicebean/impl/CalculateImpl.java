package org.springnettyrpc.simple.servicebean.impl;

import org.springnettyrpc.simple.servicebean.Calculate;

/**
 * @author ZHS
 * @create 2017-12-11 17:07
 */
public class CalculateImpl implements Calculate {
    //两数相加
    public int add(int a, int b) {
        return a + b;
    }
}
