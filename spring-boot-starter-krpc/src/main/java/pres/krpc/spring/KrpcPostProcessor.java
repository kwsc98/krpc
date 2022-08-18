package pres.krpc.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import pres.krpc.spring.annotation.KrpcProvider;
import pres.krpc.spring.annotation.KrpcService;

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
public class KrpcPostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ClassLoader classLoader;
    private ApplicationContext applicationContext;

    @Resource

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                ReflectionUtils.doWithFields(clazz, this::krpcDoRegistered);
            }
        }
    }


    private void krpcDoRegistered(Field field) {
        doKrpcService(field);
        doKrpcProvider(field);
    }

    private void doKrpcService(Field field) {
        KrpcService krpcServiceAnnotation = AnnotationUtils.getAnnotation(field, KrpcService.class);
        if (Objects.nonNull(krpcServiceAnnotation)) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CustomerBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("version", krpcServiceAnnotation.version());
            builder.addPropertyValue("timeout", krpcServiceAnnotation.timeout());
            BeanDefinition beanDefinition = builder.getBeanDefinition();

        }
    }

    private void doKrpcProvider(Field field) {
        KrpcProvider krpcProviderAnnotation = AnnotationUtils.getAnnotation(field, KrpcProvider.class);
    }


}
