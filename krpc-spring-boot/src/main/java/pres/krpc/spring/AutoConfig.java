package pres.krpc.spring;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import pers.krpc.core.KrpcApplicationContext;
import pers.krpc.core.KrpcBuilderFactory;
import pers.krpc.core.registry.RegistryBuilderFactory;
import pers.krpc.core.registry.RegistryClientInfo;

/**
 * krpc
 * 2022/8/18 17:54
 *
 * @author wangsicheng
 * @since
 **/
@Configuration
@ConditionalOnClass(KrpcProperties.class)
@EnableConfigurationProperties(KrpcProperties.class)
@Slf4j
@ConditionalOnProperty(name = "krpc.registeredPath", matchIfMissing = false)
public class AutoConfig {

    @Bean(name = "krpcApplicationContext")
    @ConditionalOnMissingBean
    public KrpcApplicationContext init(KrpcProperties krpcProperties) {
        log.info("Krpc开始初始化");
        return KrpcBuilderFactory.builder()
                .setRegistryBuilderFactory(
                        RegistryBuilderFactory.builder(RegistryClientInfo.build(krpcProperties.getRegisteredPath())))
                .setPort(krpcProperties.getPort())
                .build();
    }

    @Bean
    @DependsOn({"krpcApplicationContext"})
    @ConditionalOnMissingBean
    public KrpcPostProcessor krpcPostProcessor(@Qualifier("krpcApplicationContext") KrpcApplicationContext krpcApplicationContext) {
        return new KrpcPostProcessor(krpcApplicationContext);
    }


}
