package com.bestway.common;

import java.io.Serializable;

import com.bestway.common.authority.entity.AclUser;

public class Request implements Serializable {

	private AclUser user = null;

	// 任务ID
	private String taskId = null;

	// 是否允许不检查
	private boolean allowNoCheck = false;// 如果为true 的话，不经过权限检测，直接通过

	private String moduleName = null;

	// 模块标题 前缀
	private String moduleCaption = null;

	// 允许
	private String permission = null;

	private String ipAddresss = null;

	public Request() {
	}

	public Request(AclUser user) {
		this.user = user;
	}

	public Request(AclUser user, boolean allowNoCheck) {
		this.user = user;
		this.allowNoCheck = allowNoCheck;
	}

	public Request(AclUser user, String moduleName, String permission) {
		this.user = user;
		this.moduleName = moduleName;
		this.permission = permission;
	}

	public AclUser getUser() {
		return user;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPermission() {
		return permission;
	}

	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @return Returns the allowNoCheck.
	 */
	public boolean isAllowNoCheck() {
		return allowNoCheck;
	}

	/**
	 * @param allowNoCheck
	 *            The allowNoCheck to set.
	 */
	public void setAllowNoCheck(boolean allowNoCheck) {
		this.allowNoCheck = allowNoCheck;
	}

	/**
	 * @return Returns the moduleCaption.
	 */
	public String getModuleCaption() {
		return moduleCaption;
	}

	/**
	 * @param moduleCaption
	 *            The moduleCaption to set.
	 */
	public void setModuleCaption(String moduleCaption) {
		this.moduleCaption = moduleCaption;
	}

	public void setUser(AclUser user) {
		this.user = user;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getIpAddresss() {
		return ipAddresss;
	}

	public void setIpAddresss(String ipAddresss) {
		this.ipAddresss = ipAddresss;
	}

}
