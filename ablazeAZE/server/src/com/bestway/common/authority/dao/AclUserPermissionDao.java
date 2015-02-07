package com.bestway.common.authority.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.common.BaseDao;
import com.bestway.common.authority.entity.AclUserPermission;

public class AclUserPermissionDao extends BaseDao {

	public List findUserPermissions() {
		return this.find("select a from AclUserPermission a");
	}

	public AclUserPermission findUserPermissionById(String id) {
		return (AclUserPermission) this.load(AclUserPermission.class, id);
	}

	public List findUserPermissionByUserId(String userId) {
		List result = this.find(
				"select a from AclUserPermission as a where a.user.id = ?",
				userId);
		return result;
	}

	public AclUserPermission findUserPermissionByLoginName(String loginName) {
		List result = this
				.find(
						"select a from AclUserPermission as a where a.user.loginName = ?",
						loginName);
		if (result.size() > 0) {
			return (AclUserPermission) result.get(0);
		} else {
			return null;
		}
	}

	public List findUserPermission(String userId, String moduleName) {
		if (userId == null) {
			return null;
		}
		if (moduleName == null || moduleName.equals("")) {
			return this.find(
					"select a from AclUserPermission a where a.user.id=?",
					new Object[] { userId });
		} else {
			return this
					.find(
							"select a from AclUserPermission a where a.moduleName=? and a.user.id=?",
							new Object[] { moduleName, userId });
		}
	}

	public void saveUserPermission(AclUserPermission userPermission)
			throws DataAccessException {
		this.saveOrUpdate(userPermission);

	}

	public void deleteUserPermission(AclUserPermission UserPermission) {
		this.delete(UserPermission);
	}

	public List findCompanies() {
		return this.find("select a from Company a");
	}

}