package pres.krpc.example.customer.krpc;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.krpc.core.InterfaceInfo;
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
public class KrpcConfiguration {

    @Value("${krpc.registeredPath}")
    private String registeredPath;

    @Value("${krpc.registeredType}")
    private String registeredType;

    @Bean("krpcApplicationContext")
    public KrpcApplicationContext init() {
        return KrpcBuilderFactory.builder()
                .setRegistryBuilderFactory(
                        RegistryBuilderFactory.builder(RegistryClientInfo.build().setServerAddr(registeredPath).setClient(registeredType)))
                .build();
    }

    @Bean
    public ExampeService getExampeService(@Qualifier("krpcApplicationContext") KrpcApplicationContext krpcApplicationContext) {
        return krpcApplicationContext.getService(InterfaceInfo.build().setInterfaceClass(ExampeService.class).setVersion("1.0.0").setTimeout(1000));
    }


}
