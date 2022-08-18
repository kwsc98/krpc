package pers.krpc.core;

import pers.krpc.core.registry.RegistryBuilderFactory;

/**
 * krpc构建工厂
 * 2022/7/28 15:32
 *
 * @author lanhaifeng
 **/
public class KrpcBuilderFactory {

    private RegistryBuilderFactory registryBuilderFactory;

    public String port;

    public static KrpcBuilderFactory builder() {
        return new KrpcBuilderFactory();
    }

    public KrpcApplicationContext build(){
        return new KrpcApplicationContext(this.registryBuilderFactory.build(), port);
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
