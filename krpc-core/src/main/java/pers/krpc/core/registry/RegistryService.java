package pers.krpc.core.registry;


import lombok.Setter;
import pers.krpc.core.KrpcApplicationContext;

/**
 * krpc
 * 2022/7/28 15:50
 *
 * @author wangsicheng
 * @since
 **/
public class RegistryService {

    @Setter
    private KrpcApplicationContext krpcApplicationContext;

    @Setter
    private RegistryClient registryClient;

    public void init(){

    }


}
