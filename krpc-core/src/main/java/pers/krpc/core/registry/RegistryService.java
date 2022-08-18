package pers.krpc.core.registry;


import lombok.Setter;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.role.Role;

/**
 * krpc注册中心处理Service
 * 2022/7/28 15:50
 *
 * @author wangsicheng
 **/
public class RegistryService  {

    @Setter
    private RegistryClient registryClient;

    public static RegistryService build(RegistryClient registryClient){
        RegistryService registryService = new RegistryService();
        registryService.registryClient = registryClient;
        return registryService;
    }

    public void init(RegistryClientInfo registryClientInfo) {
        this.registryClient.init(registryClientInfo);
    }

    public InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role) {
        return this.registryClient.registerInterface(interfaceInfo, role);
    }
}
