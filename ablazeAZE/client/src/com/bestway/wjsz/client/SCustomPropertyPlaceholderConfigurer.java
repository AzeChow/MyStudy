package com.bestway.wjsz.client;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SCustomPropertyPlaceholderConfigurer  extends PropertyPlaceholderConfigurer{
	 protected void processProperties(
	            ConfigurableListableBeanFactory beanFactory, Properties props)
	            throws BeansException {
	        String wjszServerName = System.getProperty("wjszServerName");
	        if (wjszServerName == null || "".equals(wjszServerName)) {
	            wjszServerName = "127.0.0.1";
	        }
	        props.setProperty("wjszServerName", wjszServerName);
	        String wjszHttpPort = System.getProperty("wjszHttpPort");
	        if (wjszHttpPort == null || "".equals(wjszHttpPort)) {
	            wjszHttpPort = "8080";
	        }
	        props.setProperty("wjszHttpPort", wjszHttpPort);
	        String wjszContextPath = "/jptds-war"; 
	        props.setProperty("wjszContextPath", wjszContextPath);
	        System.out.println("-----------wjszServerName:" + props.getProperty("wjszServerName"));
	        System.out.println("-----------wjszHttpPort:" + props.getProperty("wjszHttpPort"));
	        System.out.println("-----------wjszContextPath:" + props.getProperty("wjszContextPath"));
	        super.processProperties(beanFactory, props);
	    }
}
