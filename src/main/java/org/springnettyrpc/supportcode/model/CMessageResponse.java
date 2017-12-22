package org.springnettyrpc.supportcode.model;

import java.io.Serializable;

/**
 * rpc服务应答结构
 * @author ZHS
 * @create 2017-12-15 09:40
 */
public class CMessageResponse implements Serializable {
    private String messageId;
    private String error;
    private Object resultDesc;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(Object resultDesc) {
        this.resultDesc = resultDesc;
    }

    @Override
    public String toString() {
        return "CMessageResponse{" +
                "messageId='" + messageId + '\'' +
                ", error='" + error + '\'' +
                ", resultDesc=" + resultDesc +
                '}';
    }
}
