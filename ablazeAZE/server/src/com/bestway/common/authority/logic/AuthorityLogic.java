package com.bestway.common.authority.logic;

import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.AclEntry;
import java.security.acl.NotOwnerException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AclManager;
import com.bestway.common.authority.AuthorityException;
import com.bestway.common.authority.acl.CustomPermission;
import com.bestway.common.authority.dao.AclGroupDao;
import com.bestway.common.authority.dao.AclGroupPermissionDao;
import com.bestway.common.authority.dao.AclIPDao;
import com.bestway.common.authority.dao.AclUserDao;
import com.bestway.common.authority.dao.AclUserPermissionDao;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupPermission;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.common.authority.entity.AclIpaddressPermission;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.AclUserPermission;
import com.bestway.common.authority.entity.AuthorityModule;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.authority.entity.OperationLogs;
import com.bestway.common.authority.entity.OperationLogsByUse;

public class AuthorityLogic {

	private AclUserDao userDao;

	private AclGroupDao groupDao;

	private AclUserPermissionDao userPermissionDao;

	private AclGroupPermissionDao groupPermissionDao;

	private AclIPDao aclIPDao;

	public void setUserDao(AclUserDao userDao) {
		this.userDao = userDao;
	}

	public void setGroupDao(AclGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public AclUserDao getUserDao() {
		return userDao;
	}

	public void saveGroupUser(AclGroupUser object) throws DataAccessException {
		List<Principal> list = new ArrayList<Principal>();
		list.add(object.getAclGroup());
		list.add(object.getAclUser());
		clearAclCache(list);
		groupDao.saveGroupUser(object);
	}

	// public void deleteGroupUser(AclUser user) {
	// List list = groupDao.findUserFromUser(user);
	// List<Principal> lsUser = new ArrayList<Principal>();
	// for (int i = 0; i < list.size(); i++) {
	// AclGroupUser groupUser = (AclGroupUser) list.get(i);
	// lsUser.add(groupUser.getAclGroup());
	// lsUser.add(groupUser.getAclUser());
	// }
	// clearAclCache(lsUser);
	// groupDao.deleteGroupUser(user);
	// }

	/**
	 * 只删除当前组的用户
	 */
	public void deleteGroupUser(AclUser user, AclGroup group) {
		List list = groupDao.findAclGroupUser(user, group);
		List<Principal> lsUser = new ArrayList<Principal>();
		if (list != null && list.size() > 0) {
			AclGroupUser groupUser = (AclGroupUser) list.get(0);
			lsUser.add(groupUser.getAclGroup());
			lsUser.add(groupUser.getAclUser());
			clearAclCache(lsUser);
			groupDao.deleteGroupUser(groupUser);
		}
	}

	public void deleteGroupUser(AclGroupUser groupUser) {
		List<Principal> lsUser = new ArrayList<Principal>();
		lsUser.add(groupUser.getAclGroup());
		lsUser.add(groupUser.getAclUser());
		clearAclCache(lsUser);
		groupDao.deleteGroupUser(groupUser);
	}

	public List<AclGroupUser> findAclGroupUser(AclUser user) {
		return this.groupDao.findUserFromUser(user);
	}

	public List<AclGroup> findAclGroupNotInUser(AclUser user) {
		return this.groupDao.findAclGroupNotInUser(user);
	}

	public AclGroupDao getGroupDao() {
		return groupDao;
	}

	public AuthorityLogic() {
	}

	public AclUser checkUser(String loginName, String password,
			BaseCompany company) {
		List users = userDao.getUsersByLoginName(loginName);
		if (users == null || users.isEmpty()) {
			return null;
		}
		for (int i = 0; i < users.size(); i++) {
			AclUser user = ((AclUser) users.get(i));
			// Edit by xxm 2005.2.19
			/*
			 * if(user.getPassword().equals(password) &&
			 * user.getCompany().equals(company)){
			 */
			if (user.getPassword().equals(password)
					&& user.getCompany().equals(company)) {
				return user;
			}
		}
		return null;

	}

	public String findDigestType(String userId, BaseCompany company) {
		List list = this.groupDao.findDigestType(userId, company);
		String type = null;
		if (list.size() != 0) {
			AclUser user = (AclUser) list.get(0);
			String pw = user.getPassword();
			if (pw == null) {
				return type;
			}
			if (pw.startsWith("{SHA-512}")) {
				type = "SHA-512";
			} else if (pw.startsWith("{MD5}")) {
				type = "MD5";
			} else if (pw.startsWith("{SHA-1}")) {
				type = "SHA-1";
			} else if (pw.startsWith("{SHA-256}")) {
				type = "SHA-256";
			}
		}
		return type;
	}

	/**
	 * 判断acl中是否包含有某user的entry
	 * 
	 * @param user
	 * @param acl
	 * @return
	 */
	private boolean checkEntryIsInAcl(Principal user, Acl acl) {
		for (Enumeration e = acl.entries(); e.hasMoreElements();) {
			AclEntry entry = (AclEntry) e.nextElement();
			if (entry.getPrincipal().equals(user)) {
				return true;
			}
		}
		return false;
	}

	// 客户端检测权限
	public boolean checkPermission(Request request) {
		Principal principal = request.getUser();
		if (principal == null)
			throw new AuthorityException("匿名用户被拒绝请求!");
		// 如果用户为超级用户时,不作处理,注意还有待改进.
		if (request.getUser().getUserFlag() != null
				&& request.getUser().getUserFlag().equals("S")) {
			return true;
		}
		// 如果为true 的话，不经过权限检测，直接通过
		if (request.isAllowNoCheck()) {
			return true;
		}

		Acl acl = AclManager.getInstance().getAcl(request.getModuleName());
		if (acl == null || !checkEntryIsInAcl(principal, acl)) {
			InitializeAclLogic initializeAclLogic = (InitializeAclLogic) CommonUtils
					.getContext().getBean("initializeAclLogic");
			try {
				initializeAclLogic.initModuleAcl(request.getModuleName(),
						principal);

				acl = AclManager.getInstance().getAcl(request.getModuleName());
			} catch (NotOwnerException e) {
				throw new RuntimeException("NotOwnerException:"
						+ e.getMessage());
			}
		}

		if (acl == null) {
			return false;
		}

		boolean isAcl = acl.checkPermission(principal, new CustomPermission(
				request.getPermission().trim()));
		return isAcl;
	}

	// 客户端检测权限
	public boolean checkipPermission(Request request) {
		String ipuser = request.getIpAddresss();
		if (ipuser == null)
			return false;
		// throw new AuthorityException("匿名IP被拒绝请求!");

		// 如果为true 的话，不经过权限检测，直接通过
		if (request.isAllowNoCheck()) {
			return true;
		}
		Hashtable hat = AclManager.getInstance().getHashIPpermission();
		if (hat.get(ipuser) == null) {
			InitializeAclLogic initializeAclLogic = (InitializeAclLogic) CommonUtils
					.getContext().getBean("initializeAclLogic");
			try {
				initializeAclLogic.initIPPermission(request, hat);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());

			}
		}
		if (hat.get(ipuser) == null) {
			return false;
		}
		List permlist = (List) hat.get(ipuser);
		if (permlist.contains(request.getPermission())) {
			return true;
		}

		return false;
	}

	public AclUser findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}

	public AclUser findUsersByLoginNames(String loginName) {
		List list = userDao.getUsersByLoginName(loginName);
		AclUser aclUser = null;
		if (list.size() > 0) {
			aclUser = (AclUser) list.get(0);
		}
		return aclUser;
	}

	public List findUsers() {
		AclUser user = CommonUtils.getAclUser();
		if ("S".equals(user.getUserFlag())) {
			return userDao.findUsers();
		}
		return userDao.findAclUserById(user.getId());
	}

	public List findNoSelectUser(AclGroup group) {
		return groupDao.findNoSelectUser(group);
	}

	public List findUserFromGroup(AclGroup group) {
		return groupDao.findUserFromGroup(group);
	}

	public void saveUser(AclUser user) {
		userDao.saveUser(user);
	}

	public void deleteUser(AclUser user) {
		userDao.deleteUser(user);
	}

	public List findGroups() {
		return groupDao.findGroups();
	}

	public AclGroup findGroupById(String id) {
		return groupDao.findGroupById(id);
	}

	public AclGroup findGroupByGroupName(String groupName) {
		return groupDao.findGroupByGroupName(groupName);
	}

	public void saveGroup(AclGroup group) throws DataAccessException {
		groupDao.saveGroup(group);
	}

	public void deleteGroup(AclGroup group) {
		groupDao.deleteGroup(group);
	}

	public List findGroupPermissions() {
		return groupPermissionDao.findGroupPermissions();
	}

	public AclGroupPermission findGroupPermissionById(String id) {
		return groupPermissionDao.findGroupPermissionById(id);
	}

	public List findGroupPermission(String groupId, String moduleName) {
		return groupPermissionDao.findGroupPermission(groupId, moduleName);
	}

	public void saveGroupPermission(AclGroupPermission groupPermission)
			throws DataAccessException {
		groupPermissionDao.saveGroupPermission(groupPermission);
	}

	public void deleteGroupPermission(AclGroupPermission groupPermission) {
		groupPermissionDao.deleteGroupPermission(groupPermission);
	}

	public List findUserPermissions() {
		return userPermissionDao.findUserPermissions();
	}

	public AclUserPermission findUserPermissionById(String id) {
		return userPermissionDao.findUserPermissionById(id);
	}

	public List findUserPermission(String userId, String moduleName) {
		return userPermissionDao.findUserPermission(userId, moduleName);
	}

	public void saveUserPermission(AclUserPermission userPermission)
			throws DataAccessException {
		userPermissionDao.saveUserPermission(userPermission);
	}

	public void deleteUserPermission(AclUserPermission userPermission) {
		userPermissionDao.deleteUserPermission(userPermission);
	}

	public List findCompanies() {
		return userPermissionDao.findCompanies();
	}

	public AuthorityModule[] getAuthorityModules() {
		return AuthorityModulesLogic.getInstance().getModules();
	}

	/**
	 * @return Returns the groupPermissionDao.
	 */
	public AclGroupPermissionDao getGroupPermissionDao() {
		return groupPermissionDao;
	}

	/**
	 * @param groupPermissionDao
	 *            The groupPermissionDao to set.
	 */
	public void setGroupPermissionDao(AclGroupPermissionDao groupPermissionDao) {
		this.groupPermissionDao = groupPermissionDao;
	}

	/**
	 * @return Returns the userPermissionDao.
	 */
	public AclUserPermissionDao getUserPermissionDao() {
		return userPermissionDao;
	}

	/**
	 * @param userPermissionDao
	 *            The userPermissionDao to set.
	 */
	public void setUserPermissionDao(AclUserPermissionDao userPermissionDao) {
		this.userPermissionDao = userPermissionDao;
	}

	public void clearAclCache() {
		AclManager.getInstance().clearAclCache();
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache(String moduleName, Principal user) {
		AclManager.getInstance().clearAclCache(moduleName, user);
	}

	/**
	 * 清除模块权限的缓存
	 * 
	 * @param moduleName
	 */
	public void clearAclCache(List lsUser) {
		AclManager.getInstance().clearAclCache(lsUser);
	}

	/**
	 * 抓取用活的所有权限(包括用户的权限和用户所属群组的权限)
	 * 
	 * @param user
	 * @return
	 */
	public List findUserAllPermission(AclUser user) {
		List lsResult = new ArrayList();
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.userPermissionDao.findUserPermissionByUserId(user
				.getId());
		getDistinctPermission(map, list);
		List lsGroup = this.groupDao.findUserFromUser(user);
		for (int i = 0; i < lsGroup.size(); i++) {
			AclGroupUser groupUser = (AclGroupUser) lsGroup.get(i);
			list = this.groupPermissionDao
					.findGroupPermissionByGroupId(groupUser.getAclGroup()
							.getId());
			getDistinctPermission(map, list);
		}
		lsResult.addAll(map.values());
		return lsResult;
	}

	private void getDistinctPermission(Map<String, Object> map, List list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof AclUserPermission) {
				AclUserPermission userPermission = (AclUserPermission) list
						.get(i);
				if (map.get(userPermission.getModuleName().trim()
						+ userPermission.getPermission().trim()) == null) {
					map.put(userPermission.getModuleName().trim()
							+ userPermission.getPermission().trim(),
							userPermission);
				}
			} else if (list.get(i) instanceof AclGroupPermission) {
				AclGroupPermission groupPermission = (AclGroupPermission) list
						.get(i);
				if (map.get(groupPermission.getModuleName().trim()
						+ groupPermission.getPermission().trim()) == null) {
					map.put(groupPermission.getModuleName().trim()
							+ groupPermission.getPermission().trim(),
							groupPermission);
				}
			}
		}
	}

	/**
	 * 保存操作日志
	 * 
	 * @param logs
	 */
	public void saveOperationLogs(OperationLogs logs) {
		this.userDao.saveOperationLogs(logs);
	}

	/**
	 * 删除操作日志
	 * 
	 * @param logs
	 */
	public void deleteOperationLogs(List logs) {
		this.userDao.deleteAll(logs);
	}

	/**
	 * 删除操作日志
	 * 
	 * @param user
	 * @param beginDate
	 * @param endDate
	 */
	public void deleteOperationLogs(AclUser user, Date beginDate, Date endDate) {
		this.userDao.deleteOperationLogs(user, beginDate, endDate);
	}

	/**
	 * 查询操作日志
	 * 
	 * @param user
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findOperationLogs(AclUser user, Date beginDate, Date endDate) {

		List<OperationLogs> list = userDao.findOperationLogs(user, beginDate,
				endDate);

		List result = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			OperationLogs operationLogs = (OperationLogs) list.get(i);

			OperationLogsByUse operationLogsByUse = new OperationLogsByUse();

			operationLogsByUse.setUser(operationLogs.getUser());

			operationLogsByUse.setModuleCaption(operationLogs
					.getModuleCaption());

			operationLogsByUse.setPermission(operationLogs.getPermission());

			operationLogsByUse.setOperateIP(operationLogs.getOperateIP());

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			if (null != operationLogs.getOperateDate()) {
				operationLogsByUse.setOperateDate(df.format(operationLogs
						.getOperateDate()));
			}

			result.add(operationLogsByUse);
		}
		return result;
	}

	/**
	 * 录入名
	 * 
	 * @param userName
	 * @param loginName
	 * @return
	 */
	public List getUserNameByLoginName(BaseCompany company, String loginName) {
		return userDao.getUserNameByLoginName(company, loginName);
	}

	public List findIPaddress() {
		return this.aclIPDao.findIPaddress();
	}

	public AclIPaddress saveIPaddress(AclIPaddress data) {
		return this.aclIPDao.saveIPaddress(data);
	}

	public void deleteIPaddress(AclIPaddress data) {
		List<AclIpaddressPermission> list = this.aclIPDao
				.findAclIpaddressPermissionByuserId(data.getId(), null);
		for (AclIpaddressPermission aclp : list) {
			this.aclIPDao.deleteAclIpaddressPermission(aclp);
		}
		this.aclIPDao.deleteIPaddress(data);

	}

	public AclIpaddressPermission saveAclIpaddressPermission(
			AclIpaddressPermission data) {
		this.aclIPDao.saveAclIpaddressPermission(data);
		return data;
	}

	public List findAclIPaddressByipAddress(String ipAddress) {
		return this.aclIPDao.findAclIPaddressByipAddress(ipAddress);
	}

	public void deleteAclIpaddressPermission(AclIpaddressPermission data) {
		this.aclIPDao.deleteAclIpaddressPermission(data);
	}

	public List findAclIpaddressPermissionByuserId(String userId,
			String moduleName) {

		return this.aclIPDao.findAclIpaddressPermissionByuserId(userId,
				moduleName);
	}

	public void clearIPcache() {
		AclManager.setHashIPpermission(null);
	}

	public AclIPDao getAclIPDao() {
		return aclIPDao;
	}

	public void setAclIPDao(AclIPDao aclIPDao) {
		this.aclIPDao = aclIPDao;
	}
}