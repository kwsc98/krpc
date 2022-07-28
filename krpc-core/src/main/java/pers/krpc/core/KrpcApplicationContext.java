package pers.krpc.core;


import java.util.HashMap;
import java.util.Map;

/**
 * krpc
 * 2022/7/25 15:31
 *
 * @author wangsicheng
 * @since
 **/
public class KrpcApplicationContext {

    private final Map<String, InterfaceContext> context;

    public KrpcApplicationContext() {
        this.context = new HashMap<>();
    }

    public InterfaceContext getInterfaceContext(String interfaceName) {
        return context.get(interfaceName);
    }

    public InterfaceContext getInterfaceContext(Class<?> interfaceName) {
        return context.get(interfaceName.getName());
    }

    public Object getService(InterfaceInfo interfaceInfo) {
        InterfaceContext interfaceContext = context.get(interfaceInfo.getInterfaceClass().getName());
        interfaceContext.getObject(interfaceInfo);
        return  interfaceContext.getObject(interfaceInfo);
    }


}
