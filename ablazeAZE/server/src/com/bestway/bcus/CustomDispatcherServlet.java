/*
 * Created on 2005-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomDispatcherServlet extends DispatcherServlet {

	protected WebApplicationContext createWebApplicationContext(
			WebApplicationContext parent) throws BeansException {
		ConfigurableWebApplicationContext wac = (ConfigurableWebApplicationContext) BeanUtils
				.instantiateClass(HttpWebApplicationContext.class);
		wac.setParent(parent);
		wac.setServletContext(getServletContext());
		wac.refresh();
		return wac;
	}

	protected void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ipaddress = request.getRemoteAddr();
		CommonUtils.setIp(ipaddress);
		super.doService(request, response);

	}

}
