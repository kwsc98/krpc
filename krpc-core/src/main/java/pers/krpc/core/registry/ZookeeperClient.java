package pers.krpc.core.registry;


import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;
import pers.krpc.core.role.Role;


import java.util.*;
import java.util.stream.Collectors;

/**
 * krpc
 * 2022/7/28 15:56
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class ZookeeperClient implements RegistryClient, CuratorCacheListener, ConnectionStateListener {


    private CuratorFramework curatorFramework;

    private Map<String, InterfaceContextDetails> interfaceCache;

    private static final String ROOT_PATH = "krpcApplication";

    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        try {
            log.info("开始初始化zookeeper注册中心");
            this.interfaceCache = new LinkedHashMap<>();
            //创建curator客户端
            this.curatorFramework = CuratorFrameworkFactory.newClient(registryClientInfo.getIp(), 5000, 20000, new ExponentialBackoffRetry(1000, 100));
            curatorFramework.start();
            if (curatorFramework.checkExists().forPath("/" + ROOT_PATH) == null) {
                curatorFramework.create().creatingParentsIfNeeded().forPath("/" + ROOT_PATH);
            }
            curatorFramework = curatorFramework.usingNamespace(ROOT_PATH);
            curatorFramework.getConnectionStateListenable().addListener(this);
            CuratorCache curatorCache = CuratorCache.build(curatorFramework, "/");
            CuratorCacheListener listener = CuratorCacheListener.builder().forAll(this).build();
            curatorCache.listenable().addListener(listener);
            curatorCache.start();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException();
        }
    }

    @Override
    public void event(Type type, ChildData oldData, ChildData data) {
        ChildData childData = data;
        if (Type.NODE_DELETED.equals(type)) {
            childData = oldData;
        }
        String[] pathArray;
        if ((pathArray = childData.getPath().split("/")).length < 5) {
            return;
        }
        log.info("监听到节点变更[{}],旧节点[{}],新节点[{}]", type, oldData, data);
        String interfacePath = "/" + pathArray[1] + "/" + pathArray[2];
        InterfaceContextDetails interfaceContextDetails = interfaceCache.get(interfacePath);
        if (interfaceContextDetails == null) {
            return;
        }
        try {
            if (curatorFramework.checkExists().forPath(interfacePath + "/" + Role.Customer) != null) {
                List<String> customerNode = curatorFramework.getChildren().forPath(interfacePath + "/" + Role.Customer);
                if (customerNode != null) {
                    interfaceContextDetails.setCustomerList(customerNode.stream().map(Customer::build).collect(Collectors.toList()));
                }
            }
            if (curatorFramework.checkExists().forPath(interfacePath + "/" + Role.Provider) != null) {
                List<String> providerNode = curatorFramework.getChildren().forPath(interfacePath + "/" + Role.Provider);
                if (providerNode != null) {
                    interfaceContextDetails.setProviderList(providerNode.stream().map(Provider::build).collect(Collectors.toList()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role) {
        try {
            InterfaceContextDetails interfaceContextDetails = new InterfaceContextDetails().setInterfaceInfo(interfaceInfo).setRole(role);
            this.interfaceCache.put(interfaceContextDetails.getPreNodePath(), interfaceContextDetails);
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(interfaceContextDetails.getNodePath());
            return interfaceContextDetails;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void stateChanged(CuratorFramework client, ConnectionState newState) {
        log.info("监听到连接变动:client:[{}]  ConnectionState:[{}]", client, newState);
        if (ConnectionState.RECONNECTED.equals(newState)) {
            log.info("检测到连接重连，开始进行节点重新注册");
            for (InterfaceContextDetails interfaceContextDetails : interfaceCache.values()) {
                try {
                    curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(interfaceContextDetails.getNodePath());
                } catch (Exception e) {
                    log.info("重新注册接口异常[{}]", interfaceContextDetails.getNodePath());
                }
            }
        }
    }
}
