package com.cmccarthy.common.config;

import com.cmccarthy.common.utils.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

@Component
@Lazy
@ContextConfiguration(classes = {DriverFactory.class})
public class PageObjectBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    @Lazy
    public AppiumDriver mobileDriver;

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(PageObject.class)) {
            PageFactory.initElements(new AppiumFieldDecorator(mobileDriver, Duration.ofSeconds(10)), bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        return bean;
    }
}
