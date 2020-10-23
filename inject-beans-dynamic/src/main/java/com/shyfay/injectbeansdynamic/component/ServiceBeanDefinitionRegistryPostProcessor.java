package com.shyfay.injectbeansdynamic.component;

import com.shyfay.injectbeansdynamic.factory.ServiceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * @Notes
 * @Author muxue
 * @Since 7/23/2020
 */
@Component
public class ServiceBeanDefinitionRegistryPostProcessor implements ApplicationContextAware, ResourceLoaderAware, BeanDefinitionRegistryPostProcessor {

    private ApplicationContext applicationContext;

    private ResourcePatternResolver resourcePatternResolver;

    private MetadataReaderFactory metadataReaderFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        Set<Class<?>> classSet = this.getClasSet("com.shyfay.injectbeansdynamic.service");
        for(Class clazz : classSet){
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition)builder.getRawBeanDefinition();
            beanDefinition.setBeanClass(ServiceFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinitionRegistry.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private Set<Class<?>> getClasSet(String scanPackage){
        Set<Class<?>> resultSet = new HashSet<Class<?>>();
        StringBuilder sb = new StringBuilder(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX);
        sb.append(ClassUtils.convertClassNameToResourcePath(this.applicationContext.getEnvironment().resolvePlaceholders(scanPackage)));
        sb.append("/*.class");
        Resource[] resources = null;
        try {
            resources = this.resourcePatternResolver.getResources(sb.toString());
        } catch (IOException e) {
            return null;
        }
        Class<?> clazz;
        for(Resource resource : resources){
            try {
                clazz = Class.forName(metadataReaderFactory.getMetadataReader(resource).getClassMetadata().getClassName());
                resultSet.add(clazz);
            } catch (Exception e) {
                return null;
            }
        }
        return resultSet;
    }

}
