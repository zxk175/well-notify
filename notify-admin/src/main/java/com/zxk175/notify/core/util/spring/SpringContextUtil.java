package com.zxk175.notify.core.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 以静态变量保存Spring ApplicationContext,
 * 可在任何代码任何地方任何时候中取出ApplicationContext.
 *
 * @author zxk175
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
     *
     * @param applicationContext context对象
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        setAppContext(applicationContext);
    }

    private void setAppContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    private ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    public <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public boolean containsBean(String name) {
        return getApplicationContext().containsBean(name);
    }

    public boolean isSingleton(String name) {
        return getApplicationContext().isSingleton(name);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext未注入,请在配置中定义SpringContextUtil");
        }
    }
}
