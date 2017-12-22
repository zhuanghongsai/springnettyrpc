package org.springnettyrpc.supportcode.servicebean.impl;

import org.springnettyrpc.supportcode.servicebean.CCalculate;

/**
 * @author ZHS
 * @create 2017-12-11 17:07
 */
public class CCalculateImpl implements CCalculate {
    //两数相加
    public int add(int a, int b) {
        return a + b;
    }
}
