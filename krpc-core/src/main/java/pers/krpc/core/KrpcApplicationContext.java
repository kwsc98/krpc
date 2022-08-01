package pers.krpc.core;


import lombok.Getter;
import pers.krpc.core.registry.RegistryService;
import pers.krpc.core.role.ServerInfo;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * krpc
 * 2022/7/25 15:31
 *
 * @author wangsicheng
 * @since
 **/
public class KrpcApplicationContext {

    private final Map<String, InterfaceContext> context;

    private RegistryService registryService;

    @Getter
    private static ServerInfo serverInfo;

    public KrpcApplicationContext(RegistryService registryService) {
        this(registryService, null);
    }

    public KrpcApplicationContext(RegistryService registryService, String port) {
        try {
            serverInfo = ServerInfo.build().setPort(InetAddress.getLocalHost().getHostAddress()).setPort(port);
            this.context = new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public InterfaceContext getInterfaceContext(String interfaceName) {
        return context.get(interfaceName);
    }

    public InterfaceContext getInterfaceContext(Class<?> interfaceName) {
        return context.get(interfaceName.getName());
    }

    public Object getService(InterfaceInfo interfaceInfo) {
        InterfaceContext interfaceContext = context.get(interfaceInfo.getInterfaceClass().getName());
        if (Objects.isNull(interfaceContext)) {
            interfaceInfo temp = InterfaceContext.build(interfaceInfo.getInterfaceClass());
            context.put(interfaceInfo.getInterfaceClass().getName(),temp);
        }
        interfaceContext.getObject(interfaceInfo);
        return interfaceContext.getObject(interfaceInfo);
    }


}
