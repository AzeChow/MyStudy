package com.bestway.common.authority.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.BaseCompany;

public class AclGroupDao extends BaseDao {

	public List findGroups() {
		return this.find("select a from AclGroup a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public AclGroup findGroupById(String id) {
		return (AclGroup) this.load(AclGroup.class, id);
	}

	public AclGroup findGroupByGroupName(String groupName) {
		List result = this
				.find(
						"select a from AclGroup a where a.groupName = ? and a.company.id=?",
						new Object[] { groupName,
								CommonUtils.getCompany().getId() });
		if (result.size() > 0) {
			return (AclGroup) result.get(0);
		} else {
			return null;
		}
	}

	public List findUserFromGroup(AclGroup group) {
		return this
				.find(
						"select a from AclGroupUser a where a.company.id=? and a.aclGroup=?",
						new Object[] { CommonUtils.getCompany().getId(), group });
	}

	public List findUserFromUser(AclUser user) {
		return this
				.findNoCache(
						"select a from AclGroupUser a where a.company.id=? and a.aclUser=?",
						new Object[] { CommonUtils.getCompany().getId(), user });
	}

	public List findAclGroupUser(AclUser user, AclGroup group) {
		return this
				.findNoCache(
						"select a from AclGroupUser a where a.company.id=? and a.aclUser=? and a.aclGroup = ? ",
						new Object[] { CommonUtils.getCompany().getId(), user,
								group });
	}

	public void deleteGroupUser(AclUser user) {
		this.deleteAll(findUserFromUser(user));
	}

	public List findNoSelectUser(AclGroup group) {
		return this
				.find(
						"select a from AclUser a where a.loginName not in (select b.aclUser.loginName from AclGroupUser b where b.company.id=? and b.aclGroup=?) and a.company.id=?",
						new Object[] { CommonUtils.getCompany().getId(), group,
								CommonUtils.getCompany().getId() });
	}

	public void saveGroup(AclGroup group) throws DataAccessException {
		this.saveOrUpdate(group);
	}

	public void saveGroupUser(AclGroupUser object) throws DataAccessException {
		this.saveOrUpdate(object);
	}

	public void deleteGroupUser(AclGroupUser object) throws DataAccessException {
		this.delete(object);
	}

	public void deleteGroup(AclGroup group) {
		this.delete(group);
	}

	public List<AclGroup> findAclGroupNotInUser(AclUser user) {
		return this
				.findNoCache(
						"select a from AclGroup a where a.company.id=? and a.id not in ("
								+ " select b.aclGroup.id from AclGroupUser b where b.company.id=? and b.aclUser=? ) ",
						new Object[] { CommonUtils.getCompany().getId(),
								CommonUtils.getCompany().getId(), user });
	}

	public List findDigestType(String userId, BaseCompany company) {
		return this
				.find(
						" select a from AclUser a where a.loginName=?  and a.company.id=? ",
						new Object[] { userId, company.getId() });
	}

}
