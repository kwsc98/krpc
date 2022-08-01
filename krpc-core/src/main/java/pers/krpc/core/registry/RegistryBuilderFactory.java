package pers.krpc.core.registry;

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
            case Zookeeper:
                registryService = RegistryService.build(new ZookeeperClient());
                registryService.init(registryClientInfo);
                return registryService;
            default:
                throw new RuntimeException();
        }
    }

    public RegistryBuilderFactory setRegistryClientInfo(RegistryClientInfo registryClientInfo) {
        this.registryClientInfo = registryClientInfo;
        return this;
    }
}
