package pers.krpc.core.protocol;


import lombok.Getter;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * krpc
 * 2022/8/12 15:47
 *
 * @author wangsicheng
 * @since
 **/
@Getter
public class KrpcMsg {

    private String uniqueIdentifier;
    private String version;
    private String className;
    private String methodName;
    private Object[] params;
    private Object object;

    public static KrpcMsg build(Class<?> className, Method method, Object[] args, String version) {
        return new KrpcMsg().setVersion(version)
                .setUniqueIdentifier(UUID.randomUUID().toString())
                .setClassName(className.getName())
                .setMethodName(method.getName())
                .setParams(args);
    }

    public KrpcMsg setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
        return this;
    }

    public KrpcMsg setVersion(String version) {
        this.version = version;
        return this;
    }

    public KrpcMsg setClassName(String className) {
        this.className = className;
        return this;
    }

    public KrpcMsg setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public KrpcMsg setParams(Object[] params) {
        this.params = params;
        return this;
    }

    public KrpcMsg setObject(Object object) {
        this.object = object;
        return this;
    }
}



