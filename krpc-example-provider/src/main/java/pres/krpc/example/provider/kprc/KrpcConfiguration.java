package pres.krpc.example.provider.kprc;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.KrpcBuilderFactory;
import pers.krpc.core.registry.RegistryBuilderFactory;
import pers.krpc.core.registry.RegistryClientInfo;

/**
 * krpc
 * 2022/7/27 16:10
 *
 * @author wangsicheng
 * @since
 **/
@Configuration
public class KrpcConfiguration {

    @Value("${krpc.registeredPath}")
    private String registeredPath;

    @Value("${krpc.port}")
    private String port;

    @Value("${krpc.registeredType}")
    private String registeredType;


    @Bean
    public KrpcApplicationContext init() {
        return KrpcBuilderFactory.builder()
                .setRegistryBuilderFactory(
                        RegistryBuilderFactory.builder(RegistryClientInfo.build().setServerAddr(registeredPath).setClient(registeredType)))
                .setPort(port)
                .build();
    }


}
