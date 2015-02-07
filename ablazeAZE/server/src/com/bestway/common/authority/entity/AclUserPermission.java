package com.bestway.common.authority.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 用户权限
 * @author Administrator
 *
 */
public class AclUserPermission
    extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
  /**
   * 权限
   */
	private String permission;
  
	/**
	 * 模块名称
	 */
	private String moduleName;
	  /**
	   * 用户名
	   */
	private AclUser user;
  public String getPermission() {
    return permission;
  }

  public AclUser getUser() {
    return user;
  }

  public String getModuleName() {
    return moduleName;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public void setUser(AclUser user) {
    this.user = user;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  /** default constructor */
  public AclUserPermission() {
  }
}
