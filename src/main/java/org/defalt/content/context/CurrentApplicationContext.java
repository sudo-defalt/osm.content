package org.defalt.content.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CurrentApplicationContext implements ApplicationContextAware {
    private static final CurrentApplicationContext instance = new CurrentApplicationContext();
    private ApplicationContext context;

    public static <T> T getBean(Class<T> type) {
        return instance.context.getBean(type);
    }

    public static String getProperty(String key) {
        return instance.context.getEnvironment().getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        instance.context = applicationContext;
    }
}
