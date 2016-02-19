package net.liuxuan.spring.mvc;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.MvcBeanPostProcessor
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/19 9:40
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/19 |    Moses       |     Created
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Component
public class MvcBeanPostProcessor implements BeanPostProcessor {

    private static Logger log =  LoggerFactory.getLogger(MvcBeanPostProcessor.class);
    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * @param bean the new bean instance
     * @param name the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws org.springframework.beans.BeansException in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     */
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        if (bean instanceof RequestMappingHandlerAdapter) {
            // Modify properties of the adapter
            log.debug("===MvcBeanPostProcessor noticed =RequestMappingHandlerAdapter= created===");
        }
        return bean;
    }

    /**
     * Apply this BeanPostProcessor to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
     * instance and the objects created by the FactoryBean (as of Spring 2.0). The
     * post-processor can decide whether to apply to either the FactoryBean or created
     * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
     * <p>This callback will also be invoked after a short-circuiting triggered by a
     * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
     * in contrast to all other BeanPostProcessor callbacks.
     * @param bean the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws org.springframework.beans.BeansException in case of errors
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet
     * @see org.springframework.beans.factory.FactoryBean
     */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException{
        if (bean instanceof RequestMappingHandlerAdapter) {
            // Modify properties of the adapter
        }
        return bean;
    }

}
