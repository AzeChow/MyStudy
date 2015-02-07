package com.bestway.common.authority.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 群组用户信息
 * @author Administrator
 *
 */
public class AclGroupUser extends BaseScmEntity{
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 操作群组
	 */
	private AclGroup aclGroup = null;
	/**
	 * 操作者
	 */
	private AclUser aclUser = null;

/**
 * @return Returns the aclGroup.
 */
public AclGroup getAclGroup() {
	return aclGroup;
}
/**
 * @param aclGroup The aclGroup to set.
 */
public void setAclGroup(AclGroup aclGroup) {
	this.aclGroup = aclGroup;
}
/**
 * @return Returns the aclUser.
 */
public AclUser getAclUser() {
	return aclUser;
}
/**
 * @param aclUser The aclUser to set.
 */
public void setAclUser(AclUser aclUser) {
	this.aclUser = aclUser;
}
}
