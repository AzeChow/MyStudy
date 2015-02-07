package com.bestway.common.authority.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.common.BaseDao;
import com.bestway.common.authority.entity.AclGroupPermission;

public class AclGroupPermissionDao extends BaseDao {

	public List findGroupPermissions() {
		return this.find("select a from AclGroupPermission a");
	}

	public AclGroupPermission findGroupPermissionById(String id) {
		return (AclGroupPermission) this.load(AclGroupPermission.class, id);
	}

	public List findGroupPermission(String groupId, String moduleName) {
		if (groupId == null) {
			return null;
		}
		if (moduleName == null || moduleName.equals("")) {
			return this.find(
					"select a from AclGroupPermission a where a.group.id=?",
					new Object[] { groupId });
		} else {
			return this
					.find(
							"select a from AclGroupPermission a where a.moduleName=? and a.group.id=?",
							new Object[] { moduleName, groupId });
		}
	}

	public List findGroupPermissionByGroupId(String groupId) {
		List result = this.find(
				"select a from AclGroupPermission as a where a.group.id = ?",
				groupId);
		return result;
	}

	public void saveGroupPermission(AclGroupPermission groupPermission)
			throws DataAccessException {
		this.saveOrUpdate(groupPermission);
	}

	public void deleteGroupPermission(AclGroupPermission groupPermission) {
		this.delete(groupPermission);
	}

}