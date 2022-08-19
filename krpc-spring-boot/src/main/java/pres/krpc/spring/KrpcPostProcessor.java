package pres.krpc.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.KrpcApplicationContext;
import pres.krpc.spring.annotation.KrpcService;
import pres.krpc.spring.annotation.KrpcResource;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * krpc
 * 2022/7/27 10:43
 *
 * @author wangsicheng
 * @since
 **/
public class KrpcPostProcessor implements BeanPostProcessor {


    private final KrpcApplicationContext krpcApplicationContext;


    KrpcPostProcessor(KrpcApplicationContext krpcApplicationContext) {
        this.krpcApplicationContext = krpcApplicationContext;
    }


    @Override
    public Object postProcessBeforeInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
        assert beanName != null;
        assert bean != null;
        KrpcService krpcService = AnnotationUtils.findAnnotation(bean.getClass(), KrpcService.class);
        if (Objects.nonNull(krpcService)) {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if(interfaces.length == 0){
                interfaces = new Class<?>[]{bean.getClass()};
            }
            for(Class<?> classz : interfaces){
                krpcApplicationContext.setService(InterfaceInfo.build().setInterfaceClass(classz).setVersion(krpcService.version()).setTimeout(krpcService.timeout()), bean);
            }
        }
        Class<?> classz = bean.getClass();
        Field[] fields = classz.getDeclaredFields();
        for (Field field : fields) {
            KrpcResource krpcResource = field.getAnnotation(KrpcResource.class);
            if (Objects.nonNull(krpcResource)) {
                Object object = krpcApplicationContext.getService(InterfaceInfo.build().setInterfaceClass(field.getType()).setVersion(krpcResource.version()).setTimeout(krpcResource.timeout()));
                field.setAccessible(true);
                try {
                    field.set(bean, object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }
}
