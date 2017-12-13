package org.springnettyrpc.simple.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 服务请求结构
 * @author ZHS
 * @create 2017-12-11 10:19
 */
public class MessageRequest implements Serializable {
    private String messageId;
    private String className;
    private String methodName;
    private Class<?>[] typeParameters;
    private Object[] parametersVal;

    public MessageRequest(){}

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParametersVal() {
        return parametersVal;
    }

    public void setParametersVal(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("messageId", messageId).append("className", className)
                .append("methodName", methodName).toString();
    }

    public void setParameters(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }

    public Object[] getParameters() {
        return parametersVal;
    }
}
