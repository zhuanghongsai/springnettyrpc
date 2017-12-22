package org.springnettyrpc.supportcode.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * rpc服务请求结构
 * @author ZHS
 * @create 2017-12-15 09:37
 */
public class CMessageRequest implements Serializable{
    private String messageId;
    private String className;
    private String methodName;
    private Class<?>[] typeParameters;
    private Object[] parametersVal;

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

    @Override
    public String toString() {
        return "CMessageRequest{" +
                "messageId='" + messageId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", typeParameters=" + Arrays.toString(typeParameters) +
                ", parametersVal=" + Arrays.toString(parametersVal) +
                '}';
    }

    public Object[] getParameters() {
        return parametersVal;
    }

    public void setParameters(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }
}
