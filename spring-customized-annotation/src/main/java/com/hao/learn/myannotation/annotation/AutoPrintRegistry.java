package com.hao.learn.myannotation.annotation;

import java.util.List;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Configuration
public class AutoPrintRegistry implements BeanDefinitionRegistryPostProcessor {

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);

    scanner.addIncludeFilter(new AnnotationTypeFilter(AutoPrint.class));

    BeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();
    Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(getBasePackage());
    for (BeanDefinition definition : candidateComponents) {
      String beanName = nameGenerator.generateBeanName(definition, registry);
      registry.registerBeanDefinition(beanName, definition);
    }
  }

  @Override
  public void postProcessBeanFactory(
      ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    // do nothing
  }

  private String getBasePackage() {
    return "com.hao.learn";
  }

}
