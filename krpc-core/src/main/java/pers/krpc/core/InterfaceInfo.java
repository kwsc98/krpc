package pers.krpc.core;


import lombok.Data;

/**
 * krpc
 * 2022/7/28 14:12
 *
 * @author wangsicheng
 * @since
 **/
@Data
public class InterfaceInfo {

    private Class<?> interfaceClass;

    private String version;

    private long timeout;

    public static InterfaceInfo build(){
        return new InterfaceInfo();
    }

    public InterfaceInfo setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        return this;
    }

    public InterfaceInfo setVersion(String version) {
        this.version = version;
        return this;
    }

    public InterfaceInfo setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }
}
