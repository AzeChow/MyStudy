package com.bestway.common.authority.action;

import java.security.Principal;
import java.util.Hashtable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupPermission;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.common.authority.entity.AclIpaddressPermission;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.AclUserPermission;
import com.bestway.common.authority.entity.AuthorityModule;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.authority.logic.AuthorityLogic;

@AuthorityClassAnnotation(caption = "权限管理", index = 1)
public class AuthorityActionImpl extends BaseActionImpl implements
		AuthorityAction {
	private AuthorityLogic authorityLogic = null;

	public void setAuthorityLogic(AuthorityLogic authorityLogic) {
		this.authorityLogic = authorityLogic;
	}

	public AuthorityLogic getAuthorityLogic() {
		return authorityLogic;
	}

	public AuthorityActionImpl() {
		this.setModuleName("Authority");
	}

	@AuthorityFunctionAnnotation(caption = "群组用户关系维护", index = 8)
	public void deleteGroupUser(Request request, AclUser user, AclGroup group) {
		authorityLogic.deleteGroupUser(user, group);
	}

	public void deleteGroupUser(Request request, AclGroupUser groupUser) {
		authorityLogic.deleteGroupUser(groupUser);
	}

	@AuthorityFunctionAnnotation(caption = "群组用户关系维护", index = 8)
	public AclGroupUser saveGroupUser(Request request, AclGroupUser object)
			throws DataAccessException {
		authorityLogic.saveGroupUser(object);
		return object;
	}

	public AclUser findUsersByLoginName(Request request, String loginName) {
		return authorityLogic.findUsersByLoginNames(loginName);
	}

	public Hashtable checkUser(String userCode, String password,
			BaseCompany company) {
		AclUser user = authorityLogic.checkUser(userCode, password, company);
		if (user == null) {
			return null;
		}
		Hashtable htResult = new Hashtable();
		htResult.put("user", user);
		return htResult;
	}

	public String findDigestType(String userId, BaseCompany company) {
		return authorityLogic.findDigestType(userId, company);
	}

	public boolean checkPermission(Request request) {
		return this.authorityLogic.checkPermission(request);
	}

	public AclUser saveUser(Request request, AclUser user) {
		authorityLogic.saveUser(user);
		return user;
	}

	@AuthorityFunctionAnnotation(caption = "修改密码", index = 3.01)
	public void checkAuthorityUserByUpdatePW(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "浏览用户", index = 1)
	public List findUsers(Request request) {
		return authorityLogic.findUsers();
	}

	@AuthorityFunctionAnnotation(caption = "浏览用户", index = 1)
	public AclUser findUser(Request request, String loginName) {
		return authorityLogic.findUserByLoginName(loginName);
	}

	@AuthorityFunctionAnnotation(caption = "删除用户", index = 3)
	public void deleteUser(Request request, AclUser user) {
		authorityLogic.deleteUser(user);
	}

	@AuthorityFunctionAnnotation(caption = "删除群组", index = 6)
	public void deleteGroup(Request request, AclGroup group) {
		authorityLogic.deleteGroup(group);
	}

	@AuthorityFunctionAnnotation(caption = "浏览群组", index = 4)
	public AclGroup findGroup(Request request, String groupName) {
		return authorityLogic.findGroupByGroupName(groupName);
	}

	@AuthorityFunctionAnnotation(caption = "浏览用户", index = 1)
	public List findNoSelectUser(Request request, AclGroup group) {
		return authorityLogic.findNoSelectUser(group);
	}

	@AuthorityFunctionAnnotation(caption = "浏览群组", index = 4)
	public List findGroups(Request request) {
		return authorityLogic.findGroups();
	}

	@AuthorityFunctionAnnotation(caption = "保存群组", index = 5)
	public AclGroup saveGroup(Request request, AclGroup group) {
		authorityLogic.saveGroup(group);
		return group;
	}

	@AuthorityFunctionAnnotation(caption = "浏览群组权限", index = 6.1)
	public List findGroupPermissions(Request request) {
		return authorityLogic.findGroupPermissions();
	}

	@AuthorityFunctionAnnotation(caption = "浏览群组权限", index = 6.1)
	public AclGroupPermission findGroupPermissionById(Request request, String id) {
		return authorityLogic.findGroupPermissionById(id);
	}

	@AuthorityFunctionAnnotation(caption = "浏览群组权限", index = 6.1)
	public List findGroupPermission(Request request, String groupId,
			String moduleName) {
		return authorityLogic.findGroupPermission(groupId, moduleName);
	}

	@AuthorityFunctionAnnotation(caption = "编辑群组权限", index = 6.2)
	public AclGroupPermission saveGroupPermission(Request request,
			AclGroupPermission groupPermission) throws DataAccessException {
		authorityLogic.saveGroupPermission(groupPermission);
		return groupPermission;
	}

	@AuthorityFunctionAnnotation(caption = "编辑群组权限", index = 6.2)
	public void deleteGroupPermission(Request request,
			AclGroupPermission groupPermission) {
		authorityLogic.deleteGroupPermission(groupPermission);
	}

	@AuthorityFunctionAnnotation(caption = "浏览用户权限", index = 3.1)
	public List findUserPermissions(Request request) {
		return authorityLogic.findUserPermissions();
	}

	@AuthorityFunctionAnnotation(caption = "浏览用户权限", index = 3.1)
	public AclUserPermission findUserPermissionById(Request request, String id) {
		return authorityLogic.findUserPermissionById(id);
	}

	@AuthorityFunctionAnnotation(caption = "浏览用户权限", index = 3.1)
	public List findUserPermission(Request request, String userId,
			String moduleName) {
		return authorityLogic.findUserPermission(userId, moduleName);
	}

	@AuthorityFunctionAnnotation(caption = "编辑用户权限", index = 3.2)
	public AclUserPermission saveUserPermission(Request request,
			AclUserPermission userPermission) throws DataAccessException {
		authorityLogic.saveUserPermission(userPermission);
		return userPermission;
	}

	@AuthorityFunctionAnnotation(caption = "编辑用户权限", index = 3.2)
	public void deleteUserPermission(Request request,
			AclUserPermission userPermission) {
		authorityLogic.deleteUserPermission(userPermission);
	}

	@AuthorityFunctionAnnotation(caption = "浏览群组用户关系", index = 7)
	public List findUserFromGroup(Request request, AclGroup group) {
		return authorityLogic.findUserFromGroup(group);
	}

	public List findCompanies(Request request) {
		return authorityLogic.findCompanies();
	}

	public AuthorityModule[] getModules() {
		return authorityLogic.getAuthorityModules();
	}

	public List getUserNameByLoginName(BaseCompany company, String loginName) {
		return authorityLogic.getUserNameByLoginName(company, loginName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.authority.action.AuthorityAction#refreshAcl()
	 */
	public void clearAclCache() {
		authorityLogic.clearAclCache();
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache(String moduleName, Principal user) {
		this.authorityLogic.clearAclCache(moduleName, user);
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache(List lsUser) {
		this.authorityLogic.clearAclCache(lsUser);
	}

	public String clearAcl(Integer i) {
		return i.toString();
	}

	public void clearAcl(String s) {
		System.out.print(s);
	}

	/**
	 * 空方法，用做用户管理新增的控制
	 * 
	 * @see com.bestway.common.authority.action.AuthorityAction#checkAuthorityUserByAdd(com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "新增用户", index = 2)
	public void checkAuthorityUserByAdd(Request request) {

	}

	/**
	 * 空方法，用做用户管理修改的控制
	 * 
	 * @see com.bestway.common.authority.action.AuthorityAction#checkAuthorityUserByUpdate(com.bestway.common.Request)
	 */
	@AuthorityFunctionAnnotation(caption = "修改用户", index = 2.1)
	public void checkAuthorityUserByUpdate(Request request) {

	}

	public List<AclGroupUser> findAclGroupUser(Request request, AclUser user) {
		return this.authorityLogic.findAclGroupUser(user);
	}

	public List<AclGroup> findAclGroupNotInUser(Request request, AclUser user) {
		return this.authorityLogic.findAclGroupNotInUser(user);
	}

	/**
	 * 抓取用活的所有权限(包括用户的权限和用户所属群组的权限)
	 * 
	 * @param user
	 * @return
	 */
	public List findUserAllPermission(Request request, AclUser user) {
		return this.authorityLogic.findUserAllPermission(user);
	}

	@AuthorityFunctionAnnotation(caption = "IP地址管理-浏览", index = 13)
	public List findIPaddress(Request request) {
		return this.authorityLogic.findIPaddress();
	}

	@AuthorityFunctionAnnotation(caption = "IP地址管理-编辑", index = 14)
	public AclIPaddress saveIPaddress(Request request, AclIPaddress data) {
		return this.authorityLogic.saveIPaddress(data);
	}

	@AuthorityFunctionAnnotation(caption = "IP地址管理-删除", index = 15)
	public void deleteIPaddress(Request request, AclIPaddress data) {
		this.authorityLogic.deleteIPaddress(data);
	}

	@AuthorityFunctionAnnotation(caption = "IP地址授权管理-编辑", index = 16)
	public AclIpaddressPermission saveAclIpaddressPermission(Request request,
			AclIpaddressPermission data) {
		return this.authorityLogic.saveAclIpaddressPermission(data);
	}

	// @AuthorityFunctionAnnotation(caption = "IP地址授权管理-浏览", index = 17)
	public List findAclIPaddressByipAddress(Request request, String ipAddress) {
		return this.authorityLogic.findAclIPaddressByipAddress(ipAddress);
	}

	@AuthorityFunctionAnnotation(caption = "IP地址授权管理-编辑", index = 18)
	public void deleteAclIpaddressPermission(Request request,
			AclIpaddressPermission data) {
		this.authorityLogic.deleteAclIpaddressPermission(data);
	}

	@AuthorityFunctionAnnotation(caption = "IP地址授权管理-编辑", index = 18)
	public List findAclIpaddressPermissionByuserId(Request request,
			String userId, String moduleName) {
		return this.authorityLogic.findAclIpaddressPermissionByuserId(userId,
				moduleName);

	}

	@AuthorityFunctionAnnotation(caption = "IP地址授权管理-编辑", index = 19)
	public void clearIPcache(Request request) {
		this.authorityLogic.clearIPcache();
	}

	@AuthorityFunctionAnnotation(caption = "IP地址授权管理-浏览", index = 17)
	public void brown(Request request) {
		// TODO Auto-generated method stub

	}
}
