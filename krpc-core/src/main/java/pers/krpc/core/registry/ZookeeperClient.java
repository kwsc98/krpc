package pers.krpc.core.registry;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import pers.krpc.core.InterfaceContext;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;
import pers.krpc.core.role.ServerInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * krpc
 * 2022/7/28 15:56
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class ZookeeperClient implements RegistryClient, CuratorCacheListener {


    private CuratorFramework curatorFramework;

    private Map<String, InterfaceContextDetails> interfaceCache;


    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        try {
            String rootPath = "/krpcApplication";
            //创建curator客户端
            this.curatorFramework = CuratorFrameworkFactory.newClient(registryClientInfo.getIp(), 5000, 20000, new ExponentialBackoffRetry(1000, 3));
            curatorFramework.start();
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(rootPath);
            curatorFramework.usingNamespace(rootPath);
            CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/", CuratorCache.Options.SINGLE_NODE_CACHE);
            CuratorCacheListener listener = CuratorCacheListener.builder().forAll(this).build();
            curatorCache.listenable().addListener(listener);
            curatorCache.start();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void event(Type type, ChildData oldData, ChildData data) {
        log.debug("监听到节点变更[{}]", type);
        String path = data.getPath();
        String[] pathArray = path.split("/");
        String interfacePath = "/" + pathArray[1] + "/" + pathArray[2];
        InterfaceContextDetails interfaceContextDetails = interfaceCache.get(interfacePath);
        try {
            List<String> customerNode = curatorFramework.getChildren().forPath(interfacePath + "/" + Role.Customer);
            List<String> providerNode = curatorFramework.getChildren().forPath(interfacePath + "/" + Role.Provider);
            interfaceContextDetails.setCustomerList(customerNode.stream().map(Customer::build).collect(Collectors.toList()));
            interfaceContextDetails.setProviderList(providerNode.stream().map(Provider::build).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role) {
        try {
            String className = interfaceInfo.getClass().getName();
            String interfacePath = "/"+className + "/" + interfaceInfo.getVersion();
            InterfaceContextDetails interfaceContextDetails = new InterfaceContextDetails().setInterfaceInfo(interfaceInfo);
            this.interfaceCache.put(interfacePath, interfaceContextDetails);
            ServerInfo serverInfo = KrpcApplicationContext.getServerInfo().getCopyByTimeOut(interfaceInfo.getTimeout());
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(interfacePath+"/"+role.toString()+"/"+serverInfo.toString());
            return interfaceContextDetails;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
