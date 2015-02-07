package com.bestway.common.authority.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 用户群组权限
 * @author Administrator
 *
 */
public class AclGroupPermission
    extends BaseScmEntity {
  /*
    private Long groupId;
   */
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 操作动作
	 */
	private String permission;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 操作群组
	 */
	private AclGroup group;

  public void setGroup(AclGroup group) {
    this.group = group;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public AclGroup getGroup() {
    return group;
  }

  public String getPermission() {
    return permission;
  }

  public String getModuleName() {
    return moduleName;
  }

  /** default constructor */
  public AclGroupPermission() {
  }

}
