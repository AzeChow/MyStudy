/*
 * Created on 2004-8-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.action;

import java.util.List;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface ContractSystemAction {
	/**
	 * 浏览系统参数设置权限控制
	 * 
	 * @param request
	 *            请求控制
	 */
	/**
	 * 权限控制重复
	 */
//	void checkSystemParameterAuthority(Request request);
	
	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	List findnameValues(Request request, int type);
	
	/**
	 * 保存系统参数设置资料
	 * 
	 * @param request
	 *            请求控制
	 * @param parameterSet
	 *            系统参数设置资料
	 */
	void saveValues(Request request, ParameterSet parameterSet);
	/**
	 * 保存用户资料
	 * 
	 * @param request
	 *            请求控制
	 * @param obj
	 *            是AclUser型，用户资料
	 */
	void saveAclUser(Request request, AclUser obj);

	
}
