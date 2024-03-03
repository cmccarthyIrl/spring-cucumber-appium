package com.cmccarthy.common.config;

import com.cmccarthy.common.utils.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;

@Component
@ContextConfiguration(classes = {MobileDriverFactory.class})
public class PageObjectBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    public AppiumDriver<MobileElement> mobileDriver;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(PageObject.class)) {
            PageFactory.initElements(new AppiumFieldDecorator(mobileDriver, Duration.ofSeconds(10)), bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
