package com.bestway.common.authority.dao;

import java.util.ArrayList;
import java.util.List;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.common.authority.entity.AclIpaddressPermission;

public class AclIPDao extends BaseDao {
	public List findIPaddress() {
		if (CommonUtils.getCompany() != null) {
			return this.find(
					"select a from AclIPaddress a where a.company.id = ?",
					new Object[] { CommonUtils.getCompany().getId() });
		} else {
			return this.find("select a from AclIPaddress a");
		}
	}

	public AclIPaddress saveIPaddress(AclIPaddress data) {
		this.saveOrUpdate(data);
		return data;
	}

	public void deleteIPaddress(AclIPaddress data) {
		this.delete(data);
	}

	public void saveAclIpaddressPermission(AclIpaddressPermission data) {
		this.saveOrUpdate(data);
	}

	public List findAclIPaddressByipAddress(String ipAddress) {
		return this.find("  select  a from AclIPaddress  a where a.company.id=? "
				+ "  and  a.begianIP<=? and a.endIP>=? ", new Object[] {
				CommonUtils.getCompany().getId(), ipAddress, ipAddress });
	}

	public List findAclIpaddressPermissionByuserId(String userId,
			String moduleName) {
		if (moduleName == null || moduleName.trim().equals("")) {
			return this.find(
					" select a from  AclIpaddressPermission a where a.company.id=? "
							+ " and a.ipuser.id=?", new Object[] {
							CommonUtils.getCompany().getId(), userId });
		} else {

			return this.find(
					" select a from  AclIpaddressPermission a where a.company.id=? "
							+ " and a.ipuser.id=? and a.moduleName=? ",
					new Object[] { CommonUtils.getCompany().getId(), userId,
							moduleName });
		}
	}

	public void deleteAclIpaddressPermission(AclIpaddressPermission data) {
		this.delete(data);
	}
}
