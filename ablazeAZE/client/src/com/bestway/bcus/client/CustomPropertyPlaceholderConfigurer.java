package com.bestway.bcus.client;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.bestway.bcus.client.common.CommonVars;

public class CustomPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		if (props.getProperty("jnlp.serverName") == null
				|| props.getProperty("jnlp.serverName").equals("")) {
			String serverName = System.getProperty("jnlp.serverName");
			if (serverName == null || "".equals(serverName)) {
				serverName = "127.0.0.1";
			}
			props.setProperty("jnlp.serverName", serverName);
		}
		if (props.getProperty("jnlp.httpPort") == null
				|| props.getProperty("jnlp.httpPort").equals("")) {
			String httpPort = System.getProperty("jnlp.httpPort");
			if (httpPort == null || "".equals(httpPort)) {
				httpPort = "8080";
			}
			props.setProperty("jnlp.httpPort", httpPort);
		}
		if (props.getProperty("jnlp.rmiPort") == null
				|| props.getProperty("jnlp.rmiPort").equals("")) {
			String rmiPort = System.getProperty("jnlp.rmiPort");
			if (rmiPort == null || "".equals(rmiPort)) {
				rmiPort = "1099";
			}
			props.setProperty("jnlp.rmiPort", rmiPort);
		}
		if (props.getProperty("jnlp.contextPath") == null
				|| props.getProperty("jnlp.contextPath").equals("")) {
			String contextPath = System.getProperty("jnlp.contextPath");
			if (contextPath == null || "".equals(contextPath)) {
				contextPath = "";
			} else {
				contextPath = "/" + contextPath;
			}
			props.setProperty("jnlp.contextPath", contextPath);
		}
		if (props.getProperty("jnlp.protocol") == null
				|| props.getProperty("jnlp.protocol").equals("")) {
			String protocol = System.getProperty("jnlp.protocol");
			// if (contextPath == null || "".equals(contextPath)) {
			if (protocol == null) {
				protocol = "";
			}
			props.setProperty("jnlp.protocol", protocol);
		}

		CommonVars.setServerName(props.getProperty("jnlp.serverName"));
		CommonVars.setPort(props.getProperty("jnlp.httpPort"));

		super.processProperties(beanFactory, props);
	}
}
