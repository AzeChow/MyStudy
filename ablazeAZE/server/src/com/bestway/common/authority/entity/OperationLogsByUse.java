package com.bestway.common.authority.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 操作日志信息
 * @author Administrator
 *
 */
public class OperationLogsByUse extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 操作者
	 */
	private AclUser user = null;
	/**
	 * 操作模组
	 */
	private String moduleCaption = null;
	/**
	 * 操作动作
	 */
	private String permission = null;
	/**
	 * 操作时间
	 */
	private String operateDate = null;
	/**
	 * 操作IP
	 */
	private String operateIP = null;

	public String getModuleCaption() {
		return moduleCaption;
	}

	public void setModuleCaption(String operateAction) {
		this.moduleCaption = operateAction;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public AclUser getUser() {
		return user;
	}

	public void setUser(AclUser user) {
		this.user = user;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getOperateIP() {
		return operateIP;
	}

	public void setOperateIP(String operateIP) {
		this.operateIP = operateIP;
	}
}
