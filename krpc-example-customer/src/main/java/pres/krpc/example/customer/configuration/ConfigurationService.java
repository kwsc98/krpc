package pres.krpc.example.customer.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.KrpcBuilderFactory;
import pers.krpc.core.registry.RegistryBuilderFactory;
import pers.krpc.core.registry.RegistryClientInfo;
import pres.krpc.exampe.ExampeService;

/**
 * krpc
 * 2022/7/27 16:10
 *
 * @author wangsicheng
 * @since
 **/
@Configuration
public class ConfigurationService {

    @Bean
    public KrpcApplicationContext init(){
        return KrpcBuilderFactory.builder()
                .setRegistryBuilderFactory(
                        RegistryBuilderFactory.builder(RegistryClientInfo.build().setIp("127.0.0.1:2181").setClient(RegistryClientInfo.Client.Zookeeper)))
                .setPort("8081")
                .build();
    }


}
