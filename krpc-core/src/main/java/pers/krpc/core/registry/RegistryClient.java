package pers.krpc.core.registry;


import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.role.Role;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * krpc注册中心接口
 * 2022/7/28 15:53
 *
 * @author wangsicheng
 **/
public interface RegistryClient {

    String ROOT_PATH = "krpcApplication";
    /**
     * 接口实例缓存
     **/
    Map<String, InterfaceContextDetails> INTERFACE_CACHE = new ConcurrentHashMap<>(8);

    /**
     * 注册中心初始化方法
     **/
    void init(RegistryClientInfo registryClientInfo);

    /**
     * 注册服务方法
     **/
    InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role);



}
