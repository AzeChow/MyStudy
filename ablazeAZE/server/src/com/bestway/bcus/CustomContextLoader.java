package com.bestway.bcus;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bestway.common.CommonUtils;

public class CustomContextLoader extends ContextLoader {

	protected WebApplicationContext createWebApplicationContext(
			ServletContext servletContext, ApplicationContext parent)
			throws BeansException {
		Class contextClass = CustomWebApplicationContext.class;
		ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
				.instantiateClass(contextClass);
		wac.setParent(parent);
		wac.setServletContext(servletContext);
		wac.refresh();
		return wac;
	}
}
