package org.springnettyrpc.simple.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 服务应答结构
 * @author ZHS
 * @create 2017-12-11 10:25
 */
public class MessageResponse implements Serializable {
    private String messageId;
    private String error;
    private Object resultDesc;
    public MessageResponse(){}
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("messageId", messageId).append("error", error).toString();
    }
    public Object getResult() {
        return resultDesc;
    }

    public void setResult(Object resultDesc) {
        this.resultDesc = resultDesc;
    }
}
