package pers.krpc.core.registry;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import pers.krpc.core.InterfaceContext;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * krpc
 * 2022/7/28 15:56
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class ZookeeperClient implements RegistryClient {


    private CuratorFramework curatorFramework;


    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        try {
            String rootPath = "/krpcApplication";
            //创建curator客户端
            this.curatorFramework = CuratorFrameworkFactory.newClient(registryClientInfo.getIp(), 5000, 20000, new ExponentialBackoffRetry(1000, 3));
            curatorFramework.start();
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(rootPath);
            curatorFramework.usingNamespace(rootPath);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public InterfaceContextDetails addListener(InterfaceInfo interfaceInfo) {
        try {
            String className = interfaceInfo.getClass().getName();
            String interfacePath = className + "/" + interfaceInfo.getVersion();
            InterfaceContextDetails interfaceContextDetails = new InterfaceContextDetails();
            registerPathChildListener(curatorFramework, shardingPath);

        } catch (Exception e) {

        }
    }

    private String getAddrHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress() + RandomStringUtils.randomAlphanumeric(10);
    }



}
