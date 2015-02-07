package com.bestway.common.authority.logic;

import java.security.Principal;
import java.security.acl.Acl;
import java.security.acl.AclEntry;
import java.security.acl.Group;
import java.security.acl.NotOwnerException;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.Request;
import com.bestway.common.authority.AclManager;
import com.bestway.common.authority.acl.CustomAcl;
import com.bestway.common.authority.acl.CustomAclEntry;
import com.bestway.common.authority.acl.CustomPermission;
import com.bestway.common.authority.dao.AclGroupDao;
import com.bestway.common.authority.dao.AclGroupPermissionDao;
import com.bestway.common.authority.dao.AclIPDao;
import com.bestway.common.authority.dao.AclUserDao;
import com.bestway.common.authority.dao.AclUserPermissionDao;
import com.bestway.common.authority.dao.BaseCompanyDao;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupPermission;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.common.authority.entity.AclIpaddressPermission;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.AclUserPermission;
import com.bestway.common.authority.entity.BaseCompany;

public class InitializeAclLogic {
	/**
	 * Log4J Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(InitializeAclLogic.class);

	private AclUserDao userDao = null;

	private AclGroupDao groupDao = null;

	private AclIPDao aclIPDao = null;

	private AclUserPermissionDao userPermissionDao = null;

	private AclGroupPermissionDao groupPermissionDao = null;

	private BaseCompanyDao BaseCompanyDao = null;

	// private List users = null;
	//
	// private List groups = null;

	public AclGroupDao getGroupDao() {
		return groupDao;
	}

	public void setUserDao(AclUserDao userDao) {
		this.userDao = userDao;
	}

	public void setGroupDao(AclGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setUserPermissionDao(AclUserPermissionDao userPermissionDao) {
		this.userPermissionDao = userPermissionDao;
	}

	public void setGroupPermissionDao(AclGroupPermissionDao groupPermissionDao) {
		this.groupPermissionDao = groupPermissionDao;
	}

	public AclUserDao getUserDao() {
		return userDao;
	}

	public AclUserPermissionDao getUserPermissionDao() {
		return userPermissionDao;
	}

	public AclGroupPermissionDao getGroupPermissionDao() {
		return groupPermissionDao;
	}

	private void checkUserisEmpty() {

		List companies = BaseCompanyDao.findCompanies();
		if (companies == null || companies.isEmpty()) {
			BaseCompany company = new BaseCompany();
			company.setName("缺省公司");
			BaseCompanyDao.saveCompany(company);
		}
		companies = BaseCompanyDao.findCompanies();
		List users = userDao.findUsers();
		if (users == null || users.isEmpty()) {
			AclUser aclUser = new AclUser();
			aclUser.setCreateDate(new Date());
			aclUser.setEmail("local@local");
			aclUser.setLoginName("001");
			aclUser.setUserName("admin");
			aclUser.setPassword("admin");
			aclUser.setUserFlag("S");
			aclUser.setCompany((BaseCompany) companies.get(0));
			userDao.saveUser(aclUser);
		}

	}

	public void init() {
		// SessionFactory sf = userDao.getSessionFactory();
		// Session session;
		// try {
		// session = sf.openSession();
		// CommonUtils.setCommonSession(session);
		// CommonUtils.setSessionFactory(sf);
		// } catch (HibernateException e) {
		// throw new RuntimeException(e.getMessage());
		// }
		// TransactionSynchronizationManager.bindResource(sf, new
		// SessionHolder(session));
		checkUserisEmpty();
	}

	/**
	 * 
	 * @param moduleName
	 * @throws NotOwnerException
	 */
	public void initModuleAcl(String moduleName) throws NotOwnerException {
		List users = userDao.findUsers();
		List groups = groupDao.findGroups();
//		List ipusers = aclIPDao.findIPaddress();
		AclUser owner = new AclUser();
		owner.setId("1"); // OwnerName是随意写的

		AclManager aclManager = AclManager.getInstance();

		Acl acl = new CustomAcl(owner, moduleName);
		if (aclManager.getAcl(moduleName) == null) {
			addUserEntry(owner, users, moduleName, acl);
			addGroupEntry(owner, groups, moduleName, acl);
			aclManager.addAcl(moduleName, acl);
		}
	}

	public synchronized void initModuleAcl(String moduleName, Principal user)
			throws NotOwnerException {
		AclUser owner = new AclUser();
		owner.setId("1"); // OwnerName是随意写的
		AclManager aclManager = AclManager.getInstance();
		Acl acl = aclManager.getAcl(moduleName);
		if (acl == null) {
			acl = new CustomAcl(owner, moduleName);
			addUserEntry(owner, (AclUser) user, moduleName, acl);
			List list = this.groupDao.findUserFromUser((AclUser) user);
			List<AclGroup> lsGroup = new ArrayList<AclGroup>();
			for (int i = 0; i < list.size(); i++) {
				lsGroup.add(((AclGroupUser) list.get(i)).getAclGroup());
			}
			addGroupEntry(owner, lsGroup, moduleName, acl);
			aclManager.addAcl(moduleName, acl);
		} else {
			if (checkEntryIsInAcl(user, acl)) {
				return;
			}
			addUserEntry(owner, (AclUser) user, moduleName, acl);
			List list = this.groupDao.findUserFromUser((AclUser) user);
			List<AclGroup> lsGroup = new ArrayList<AclGroup>();
			for (int i = 0; i < list.size(); i++) {
				lsGroup.add(((AclGroupUser) list.get(i)).getAclGroup());
			}
			addGroupEntry(owner, lsGroup, moduleName, acl);
		}
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

	private void addGroupEntry(Principal owner, List groups, String moduleName,
			Acl acl) throws NotOwnerException {
		for (int j = 0; j < groups.size(); j++) {
			addGroupEntry(owner, (AclGroup) groups.get(j), moduleName, acl);
		}
	}

	private void addUserEntry(Principal owner, List users, String moduleName,
			Acl acl) throws NotOwnerException {
		for (int j = 0; j < users.size(); j++) {
			addUserEntry(owner, (AclUser) users.get(j), moduleName, acl);
		}
	}

	private void addGroupEntry(Principal owner, AclGroup aclGroup,
			String moduleName, Acl acl) throws NotOwnerException {
		if (checkEntryIsInAcl(aclGroup, acl)) {
			return;
		}
		String groupId = aclGroup.getId();
		List groupPermissions = groupPermissionDao.findGroupPermission(groupId,
				moduleName);
		Group currGroup = aclGroup;
		/**
		 * 将user加入到group中，为了acl计算所用
		 */
		List lsGroupUser = this.groupDao
				.findUserFromGroup((AclGroup) currGroup);
		for (int n = 0; n < lsGroupUser.size(); n++) {
			AclGroupUser groupUser = (AclGroupUser) lsGroupUser.get(n);
			currGroup.addMember(groupUser.getAclUser());
		}

		AclEntry entry = new CustomAclEntry(currGroup);
		// AclEntry entry = new AclEntryImpl(currGroup);
		for (int m = 0; m < groupPermissions.size(); m++) {
			String methodName = ((AclGroupPermission) groupPermissions.get(m))
					.getPermission();
			Permission permission = (Permission) AuthorityModulesLogic
					.getInstance().getPermissions().get(methodName);
			if (permission == null)
				permission = new CustomPermission(methodName);
			entry.addPermission(permission);
		}
		acl.addEntry(owner, entry);
	}

	private void addUserEntry(Principal owner, AclUser aclUser,
			String moduleName, Acl acl) throws NotOwnerException {
		String userId = aclUser.getId();
		List userPermissions = userPermissionDao.findUserPermission(userId,
				moduleName);
		AclUser currUser = aclUser;
		AclEntry entry = new CustomAclEntry(currUser);
		// AclEntry entry = new AclEntryImpl(currUser);
		for (int m = 0; m < userPermissions.size(); m++) {
			String methodName = ((AclUserPermission) userPermissions.get(m))
					.getPermission();
			Permission permission = (Permission) AuthorityModulesLogic
					.getInstance().getPermissions().get(methodName);
			if (permission == null) {
				permission = new CustomPermission(methodName);
			}
			entry.addPermission(permission);
		}
		acl.addEntry(owner, entry);
	}

	public Hashtable initIPPermission(Request request, Hashtable hat) {
		String moduleName = request.getModuleName();
		String ipAddresss = request.getIpAddresss();
		List slist = new ArrayList();
		List<AclIPaddress> list = this.aclIPDao
				.findAclIPaddressByipAddress(ipAddresss);
		for (AclIPaddress data : list) {
//			List<AclIPaddress> perlist = this.aclIPDao
//					.findAclIPaddressByipAddress(ipAddresss);
			for (AclIPaddress addata : list) {
				List<AclIpaddressPermission> ipperlist = this.aclIPDao
						.findAclIpaddressPermissionByuserId(addata.getId(),
								null);
				for (AclIpaddressPermission aclp : ipperlist) {
					slist.add(aclp.getPermission());
					System.out.println(aclp.getPermission());
				}
			}
		}
		hat.put(ipAddresss, slist);
		return hat;
	}

	public BaseCompanyDao getBaseCompanyDao() {
		return BaseCompanyDao;
	}

	public void setBaseCompanyDao(BaseCompanyDao baseCompanyDao) {
		this.BaseCompanyDao = baseCompanyDao;
	}

	public AclIPDao getAclIPDao() {
		return aclIPDao;
	}

	public void setAclIPDao(AclIPDao aclIPDao) {
		this.aclIPDao = aclIPDao;
	}
}