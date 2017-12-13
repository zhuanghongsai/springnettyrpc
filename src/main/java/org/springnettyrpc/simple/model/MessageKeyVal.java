package org.springnettyrpc.simple.model;

import java.util.Map;

/**
 *
 * rpc服务映射容器
 * @author ZHS
 * @create 2017-12-11 10:27
 */
public class MessageKeyVal {
    private Map<String, Object> messageKeyVal;

    public Map<String, Object> getMessageKeyVal() {
        return messageKeyVal;
    }
    public MessageKeyVal(){}

    public void setMessageKeyVal(Map<String, Object> messageKeyVal) {
        this.messageKeyVal = messageKeyVal;
    }
}
