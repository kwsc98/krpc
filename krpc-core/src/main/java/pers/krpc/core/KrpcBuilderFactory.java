package pers.krpc.core;

import pers.krpc.core.registry.RegistryBuilderFactory;

/**
 * krpc
 * 2022/7/28 15:32
 *
 * @author lanhaifeng
 * @since
 **/
public class KrpcBuilderFactory {

    private KrpcApplicationContext krpcApplicationContext;

    private RegistryBuilderFactory registryBuilderFactory;

    public String port;

    public static KrpcBuilderFactory builder() {
        return new KrpcBuilderFactory();
    }

    public KrpcApplicationContext build(){
        krpcApplicationContext = new KrpcApplicationContext(this.registryBuilderFactory.build(),port);
        return krpcApplicationContext;
    }

    public KrpcBuilderFactory setRegistryBuilderFactory(RegistryBuilderFactory registryBuilderFactory) {
        this.registryBuilderFactory = registryBuilderFactory;
        return this;
    }

    public KrpcBuilderFactory setPort(String port) {
        this.port = port;
        return this;
    }

}
