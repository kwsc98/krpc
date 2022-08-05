package pers.krpc.core.registry;


import pers.krpc.core.InterfaceContext;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.role.Role;

import java.util.Map;

/**
 * krpc
 * 2022/7/28 15:53
 *
 * @author wangsicheng
 * @since
 **/
public interface RegistryClient {


    public void init(RegistryClientInfo registryClientInfo);

    public InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role);



}
