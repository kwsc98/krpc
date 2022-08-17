package pers.krpc.core.registry;


import pers.krpc.core.InterfaceContext;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.role.Role;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * krpc
 * 2022/7/28 15:53
 *
 * @author wangsicheng
 * @since
 **/
public interface RegistryClient {

    public static final String ROOT_PATH = "krpcApplication";

    public Map<String, InterfaceContextDetails> INTERFACE_CACHE = new ConcurrentHashMap<>(8);

    public void init(RegistryClientInfo registryClientInfo);

    public InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role);



}
