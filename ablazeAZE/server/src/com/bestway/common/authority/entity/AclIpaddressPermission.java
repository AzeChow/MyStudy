package com.bestway.common.authority.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 用户IP地址权限
 * @author Administrator
 *
 */
public class AclIpaddressPermission extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();
	/**
	 * 操作动作
	 */
	private String permission;
	/**
	 * 摸块名称
	 */
	private String moduleName;
	/**
	 * ＩＰ用户
	 */
	private AclIPaddress ipuser;


	public AclIPaddress getIpuser() {
		return ipuser;
	}

	public void setIpuser(AclIPaddress ipuser) {
		this.ipuser = ipuser;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
