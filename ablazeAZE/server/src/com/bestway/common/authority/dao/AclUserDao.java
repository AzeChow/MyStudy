package com.bestway.common.authority.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.BaseCompany;
import com.bestway.common.authority.entity.OperationLogs;

//注意:在该程序中所有的find开头的是通过Company过滤过的,以get开头的是没有Company条件过滤的.
public class AclUserDao extends BaseDao {

	public List findUsers() {
		if (CommonUtils.getCompany() != null) {
			return this.find("select a from AclUser a where a.company.id = ?",
					new Object[] { CommonUtils.getCompany().getId() });
		} else {
			return this.find("select a from AclUser a");
		}
	}

	public List findUsers(Company company) {
		return this.find("select a from AclUser a where a.company.id = ?",
				new Object[] { company.getId() });
	}

	public AclUser findUserById(String id) {
		List users = null;
		if (CommonUtils.getCompany() != null) {
			users = this
					.find("select t1 from AclUser t1 where id = ? and t1.company.id = ?",
							new Object[] { id, CommonUtils.getCompany().getId() });
		} else {
			users = this.find("select a from AclUser a where a.id = ?", id);
		}
		if (users == null || users.isEmpty()) {
			return null;
		} else {
			return (AclUser) users.get(0);
		}
	}

	public List findAclUserById(String id) {
		List list = this.find("select a from AclUser a where a.id = ?", id);
		return list;
	}

	public AclUser findUserByLoginName(String loginName) {
		System.out.println("进入FIND里面");
		List users = null;
		if (CommonUtils.getCompany() != null) {
			users = this
					.find("select t1 from AclUser t1 where t1.loginName = ? and t1.company.id = ?",
							new Object[] { loginName,
									CommonUtils.getCompany().getId() });
		} else {
			users = this.find("select a from AclUser a where a.loginName = ? ",
					loginName);
		}

		if (users == null || users.isEmpty()) {
			return null;
		} else {
			return (AclUser) users.get(0);
		}
	}

	public List getUsersByLoginName(String loginName) {
		return this.find("select a from AclUser a where a.loginName = ? ",
				loginName);
	}

	public void saveUser(AclUser user) throws DataAccessException {
		saveOrUpdate(user);
	}

	public void deleteUser(AclUser user) {
		this.delete(user);
	}

	public void saveOperationLogs(OperationLogs logs) {
		this.saveOrUpdate(logs);
	}

	public void deleteOperationLogs(OperationLogs logs) {
		this.delete(logs);
	}

	public void deleteOperationLogs(AclUser user, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "delete OperationLogs where company=? ";
		parameters.add(CommonUtils.getCompany());
		if (user != null) {
			hql += " and user=? ";
			parameters.add(user);
		}
		if (beginDate != null) {
			hql += " and operateDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and operateDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		this.batchUpdateOrDelete(hql, parameters.toArray());
	}

	public List findOperationLogs(AclUser user, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from OperationLogs a where a.company.id=?  ";
		parameters.add(CommonUtils.getCompany().getId());
		if (user != null) {
			hql += " and a.user.id=? ";
			parameters.add(user.getId());
		}
		if (beginDate != null) {
			hql += " and a.operateDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.operateDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hql += " order by a.operateDate desc";
		return this.find(hql, parameters.toArray());
	}

	public List getUserNameByLoginName(BaseCompany company, String loginName) {

		return find(
				"select a from AclUser a where a.loginName = ? and a.company.id = ?",
				new Object[] { loginName, company.getId() });
	}
}