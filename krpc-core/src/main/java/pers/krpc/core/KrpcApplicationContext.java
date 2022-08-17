package pers.krpc.core;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import pers.krpc.core.protocol.netty.NettyApplicationContext;
import pers.krpc.core.proxy.ProxyService;
import pers.krpc.core.registry.RegistryService;
import pers.krpc.core.role.Role;
import pers.krpc.core.role.ServerInfo;
import java.net.InetAddress;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * KrpcApplicationContext
 * 2022/7/25 15:31
 * @author wangsicheng
 **/
public class KrpcApplicationContext {

    private final Map<String, InterfaceContext> context;

    private final RegistryService registryService;
    @Getter
    private final NettyApplicationContext nettyApplicationContext;

    @Getter
    private static ServerInfo serverInfo;

    public KrpcApplicationContext(RegistryService registryService) {
        this(registryService, null);
    }

    public KrpcApplicationContext(RegistryService registryService, String port) {
        try {
            serverInfo = ServerInfo.build().setIp(InetAddress.getLocalHost().getHostAddress()).setPort(port);
            this.context = new ConcurrentHashMap<>();
            this.registryService = registryService;
            this.nettyApplicationContext = new NettyApplicationContext();
            if(StringUtils.isNotBlank(port)){
                this.nettyApplicationContext.initService(Integer.parseInt(port),this);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public InterfaceContext getInterfaceContext(String interfaceName) {
        return context.get(interfaceName);
    }


    @SuppressWarnings("unchecked")
    public <T> T getService(InterfaceInfo interfaceInfo) {
        InterfaceContext interfaceContext = context.get(interfaceInfo.getInterfaceClass().getName());
        if (Objects.isNull(interfaceContext)) {
            interfaceContext = InterfaceContext.build(interfaceInfo.getInterfaceClass());
            context.put(interfaceInfo.getInterfaceClass().getName(), interfaceContext);
        }
        if(!interfaceContext.contains(interfaceInfo)){
            InterfaceContextDetails interfaceContextDetails = registryService.registerInterface(interfaceInfo, Role.Customer);
            interfaceContextDetails.setObject(ProxyService.getProxy(interfaceContextDetails));
            interfaceContext.put(interfaceInfo.getVersion(), interfaceContextDetails);
        }
        return (T) interfaceContext.getObject(interfaceInfo);
    }

    public void setService(InterfaceInfo interfaceInfo,Object object) {
        InterfaceContext interfaceContext = context.get(interfaceInfo.getInterfaceClass().getName());
        if (Objects.isNull(interfaceContext)) {
            interfaceContext = InterfaceContext.build(interfaceInfo.getInterfaceClass());
            context.put(interfaceInfo.getInterfaceClass().getName(), interfaceContext);
        }
        if(!interfaceContext.contains(interfaceInfo)){
            InterfaceContextDetails interfaceContextDetails = registryService.registerInterface(interfaceInfo, Role.Provider);
            interfaceContextDetails.setObject(object);
            interfaceContext.put(interfaceInfo.getVersion(), interfaceContextDetails);
        }
    }


}
