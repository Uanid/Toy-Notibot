package com.uanid.myserver.notibot.sender.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author uanid
 * @since 2019-11-16
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class TelegramConfig {

    @Autowired
    private DefaultListableBeanFactory ctx;

    @Bean
    public Map<String, TelegramCommand> telegramCommands() throws ClassNotFoundException {
        Map<String, TelegramCommand> commandMap = new HashMap<>();
        Set<BeanDefinition> beanDefs = createComponentScanner().findCandidateComponents("com.uanid.myserver.notibot");

        for (BeanDefinition beanDef : beanDefs) {
            Class<?> controllerClz = Class.forName(beanDef.getBeanClassName());
            ctx.registerBeanDefinition(beanDef.getBeanClassName(), beanDef);

            for (Method method : controllerClz.getMethods()) {
                if (method.isAnnotationPresent(TelegramRequestMapping.class)) {
                    TelegramRequestMapping requestMapping = method.getAnnotation(TelegramRequestMapping.class);

                    TelegramCommand.TelegramCommandBuilder builder = TelegramCommand.builder()
                            .controller(controllerClz)
                            .handlerMethod(method)
                            .instance(ctx.getBean(beanDef.getBeanClassName()));

                    String name = requestMapping.value().isEmpty() ? requestMapping.name() : requestMapping.value();
                    commandMap.put(name, builder.name(name).build());
                    log.info(String.format("TelegramRequestMapping [%s] has mapped to %s", name, controllerClz.getCanonicalName()));
                    for (String alias : requestMapping.alias()) {
                        commandMap.put(alias, builder.name(alias).build());
                        log.info(String.format("TelegramRequestMapping Alias [%s] has mapped to %s", alias, controllerClz.getCanonicalName()));
                    }
                }
            }
        }
        return commandMap;
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(TelegramController.class));
        return provider;
    }
}
