package com.bestway.bcus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class HttpWebApplicationContext extends XmlWebApplicationContext {

	/**
	 * The default location for the root context is
	 * "/WEB-INF/applicationContext.xml", and "/WEB-INF/test-servlet.xml" for a
	 * context with the namespace "test-servlet" (like for a DispatcherServlet
	 * instance with the servlet-name "test").
	 */
	protected String[] getDefaultConfigLocations() {
		return new String[] {};
	}

	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)
			throws BeansException, IOException {
		ClassPathResource resource = new ClassPathResource("http-servlet.xml");
		reader.loadBeanDefinitions(resource);
	}

}
