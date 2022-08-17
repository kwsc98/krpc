package pers.krpc.core.registry;

import pers.krpc.core.registry.impl.NacosClient;
import pers.krpc.core.registry.impl.ZookeeperClient;

/**
 * krpc
 * 2022/7/25 16:03
 *
 * @author lanhaifeng
 * @since
 **/
public class RegistryBuilderFactory {

    private RegistryClientInfo registryClientInfo;

    public static RegistryBuilderFactory builder(RegistryClientInfo registryClientInfo) {
        return new RegistryBuilderFactory().setRegistryClientInfo(registryClientInfo);
    }

    public RegistryService build() {
        RegistryService registryService = null;
        switch (registryClientInfo.getClient()) {
            case Nacos:
                registryService = RegistryService.build(new NacosClient());
                break;
            case Zookeeper:
                registryService = RegistryService.build(new ZookeeperClient());
                break;
            default:
                throw new RuntimeException();
        }
        registryService.init(registryClientInfo);
        return registryService;
    }

    public RegistryBuilderFactory setRegistryClientInfo(RegistryClientInfo registryClientInfo) {
        this.registryClientInfo = registryClientInfo;
        return this;
    }
}
