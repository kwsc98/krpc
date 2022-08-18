package pers.krpc.core.registry.impl;


import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.registry.RegistryClient;
import pers.krpc.core.registry.RegistryClientInfo;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;
import pers.krpc.core.role.Role;
import pers.krpc.core.role.ServerInfo;
import java.util.*;
import java.util.stream.Collectors;

/**
 * krpc
 * 2022/8/17 14:12
 *
 * @author wangsicheng
 **/
@Slf4j
public class NacosClient implements RegistryClient, EventListener {

    private NamingService namingService;

    @Override
    public void init(RegistryClientInfo registryClientInfo) {
        try {
            this.namingService = NamingFactory.createNamingService(registryClientInfo.getServerAddr());
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InterfaceContextDetails registerInterface(InterfaceInfo interfaceInfo, Role role) {
        try {
            InterfaceContextDetails interfaceContextDetails = new InterfaceContextDetails().setInterfaceInfo(interfaceInfo).setRole(role);
            INTERFACE_CACHE.put(interfaceContextDetails.getPreNodePath(), interfaceContextDetails);
            this.namingService.subscribe(interfaceContextDetails.getPreNodePath(), ROOT_PATH, this);
            ServerInfo serverInfo = KrpcApplicationContext.getServerInfo().getCopyByTimeOut(interfaceInfo.getTimeout());
            Instance instance = new Instance();
            instance.setIp(serverInfo.getIp());
            instance.setPort(serverInfo.getPortByInt());
            instance.addMetadata(role.name(), interfaceContextDetails.getInterfaceInfo().getTimeout());
            this.namingService.registerInstance(interfaceContextDetails.getPreNodePath(), ROOT_PATH, instance);
            return interfaceContextDetails;
        } catch (Exception e) {
            log.info(e.toString());
            throw new RuntimeException();
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof NamingEvent) {
            NamingEvent namingEvent = (NamingEvent) event;
            String[] pathArray = namingEvent.getServiceName().split("/");
            String interfacePath = "/" + pathArray[1] + "/" + pathArray[2];
            log.info("监控到服务变动[{}]", interfacePath);
            InterfaceContextDetails interfaceContextDetails = INTERFACE_CACHE.get(interfacePath);
            if (interfaceContextDetails == null) {
                return;
            }
            List<Instance> instanceList = namingEvent.getInstances();
            Map<Role, List<Instance>> roleListMap = instanceList.stream().collect(
                    Collectors.groupingBy(e -> {
                        if (e.containsMetadata(Role.Customer.name())) {
                            return Role.Customer;
                        } else {
                            return Role.Provider;
                        }
                    }));
            List<Instance> customerList = roleListMap.get(Role.Customer);
            customerList = Objects.nonNull(customerList) ? customerList : new ArrayList<>();
            interfaceContextDetails.setCustomerList(customerList.stream().map(Customer::build).collect(Collectors.toList()));

            List<Instance> providerList = roleListMap.get(Role.Provider);
            providerList = Objects.nonNull(providerList) ? providerList : new ArrayList<>();
            interfaceContextDetails.setProviderList(providerList.stream().map(Provider::build).collect(Collectors.toList()));

        }
    }
}
