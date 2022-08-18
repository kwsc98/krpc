package pres.krpc.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pres.krpc.spring.annotation.KrpcService;
import pres.krpc.spring.annotation.KrpcResource;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * krpc
 * 2022/7/27 10:43
 *
 * @author wangsicheng
 * @since
 **/
public class KrpcPostProcessor implements ApplicationContextAware, BeanPostProcessor {


    private final KrpcApplicationContext krpcApplicationContext;

    private ApplicationContext applicationContext;

    KrpcPostProcessor(KrpcApplicationContext krpcApplicationContext) {
        this.krpcApplicationContext = krpcApplicationContext;
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        assert beanName != null;
        assert bean != null;
        KrpcResource krpcResource = applicationContext.findAnnotationOnBean(beanName, KrpcResource.class);
        if (Objects.nonNull(krpcResource)) {
            return krpcApplicationContext.getService(InterfaceInfo.build().setInterfaceClass(bean.getClass()).setVersion(krpcResource.version()).setTimeout(krpcResource.timeout()));
        }

        KrpcService krpcService = AnnotationUtils.findAnnotation(bean.getClass(), KrpcService.class);
        if (Objects.nonNull(krpcService)) {
            krpcApplicationContext.setService(InterfaceInfo.build().setInterfaceClass(bean.getClass()).setVersion(krpcService.version()).setTimeout(krpcService.timeout()), bean);
        }
        return bean;
    }

}
