/*
 * Created on 2004-7-2
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.authority.action;

import java.security.Principal;
import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupPermission;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.common.authority.entity.AclIpaddressPermission;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.AclUserPermission;
import com.bestway.common.authority.entity.AuthorityModule;
import com.bestway.common.authority.entity.BaseCompany;

/**
 * @author 001
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface AuthorityAction {
	Hashtable checkUser(String userCode, String password, BaseCompany company);

	boolean checkPermission(Request request);

	AclUser saveUser(Request request, AclUser user);

	List findUsers(Request request);
	
	AclUser findUsersByLoginName(Request request, String loginName);

	AclUser findUser(Request request, String loginName);

	void deleteUser(Request request, AclUser user);

	void deleteGroup(Request request, AclGroup group);

	AclGroup findGroup(Request request, String groupName);

	List findGroups(Request request);

	AclGroup saveGroup(Request request, AclGroup group);

	List findGroupPermissions(Request request);

	AclGroupPermission findGroupPermissionById(Request request, String id);

	List findGroupPermission(Request request, String groupId, String moduleName);

	AclGroupPermission saveGroupPermission(Request request,
			AclGroupPermission groupPermission) throws DataAccessException;

	void deleteGroupPermission(Request request,
			AclGroupPermission groupPermission);

	List findUserPermissions(Request request);

	AclUserPermission findUserPermissionById(Request request, String id);

	List findUserPermission(Request request, String userId, String moduleName);

	AclUserPermission saveUserPermission(Request request,
			AclUserPermission userPermission) throws DataAccessException;

	void deleteUserPermission(Request request, AclUserPermission userPermission);

	List findCompanies(Request request);

	AuthorityModule[] getModules();

	public List findUserFromGroup(Request request, AclGroup group);

	List getUserNameByLoginName(BaseCompany company, String loginName);

	/**
	 * 清除所有模块权限的缓存
	 * 
	 * @param moduleName
	 */
	void clearAclCache();

	String clearAcl(Integer i);

	void clearAcl(String s);

	public List findNoSelectUser(Request request, AclGroup group);

	public AclGroupUser saveGroupUser(Request request, AclGroupUser object)
			throws DataAccessException;

	public void deleteGroupUser(Request request, AclUser user, AclGroup group);

	public void deleteGroupUser(Request request, AclGroupUser groupUser);

	/**
	 * 空方法，用做用户管理新增的控制
	 * 
	 * @param request
	 */
	public void checkAuthorityUserByAdd(Request request);

	/**
	 * 空方法，用做用户管理修改的控制
	 * 
	 * @param request
	 */
	public void checkAuthorityUserByUpdate(Request request);

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	void clearAclCache(String moduleName, Principal user);

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	void clearAclCache(List lsUser);

	List<AclGroupUser> findAclGroupUser(Request request, AclUser user);

	List<AclGroup> findAclGroupNotInUser(Request request, AclUser user);

	/**
	 * 抓取用活的所有权限(包括用户的权限和用户所属群组的权限)
	 * 
	 * @param user
	 * @return
	 */
	List findUserAllPermission(Request request, AclUser user);

	List findIPaddress(Request request);

	AclIPaddress saveIPaddress(Request request, AclIPaddress data);

	void deleteIPaddress(Request request, AclIPaddress data);

	AclIpaddressPermission saveAclIpaddressPermission(Request request,
			AclIpaddressPermission data);

	List findAclIPaddressByipAddress(Request request, String ipAddress);

	List findAclIpaddressPermissionByuserId(Request request, String userId,
			String moduleName);

	void deleteAclIpaddressPermission(Request request,
			AclIpaddressPermission data);

	public String findDigestType(String userId, BaseCompany company);

	void clearIPcache(Request request);

	void checkAuthorityUserByUpdatePW(Request request);

	void brown(Request request);
}