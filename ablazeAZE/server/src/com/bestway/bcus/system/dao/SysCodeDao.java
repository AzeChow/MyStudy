/*
 * Created on 2004-7-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.system.dao;

import java.util.List;

import com.bestway.bcus.system.entity.ApplyCustomBillParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.bcus.system.entity.RenderDataColumn;
import com.bestway.bcus.system.entity.SysCode;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ProjectDept;

/**
 * com.bestway.bcus.system.dao.SysCodeDao 现在不在使用
 * 
 * @author 001
 * 
 */
public class SysCodeDao extends BaseDao {

	/**
	 * 根据entityClassName查找SysCode
	 * 
	 * @param entityClassName
	 * @return SysCode
	 */
	public SysCode getSysCodeEntityByName(String entityClassName) {
		List sysCodeEntities = this.find(
				"select a from SysCode a where a.entityClassName =? ",
				new Object[] { entityClassName });
		if (sysCodeEntities == null || sysCodeEntities.isEmpty()) {
			return null;
		}
		return (SysCode) sysCodeEntities.get(0);
	}
	
	public CustomsDeclarationSet findCustomsSet(Integer type){
		if (type != null){
			List list = this.find("select a from CustomsDeclarationSet a where a.impType = ? and a.company.id = ?",
					new Object[]{type,CommonUtils.getCompany().getId()});
			if (list != null && list.size()>0){
				return (CustomsDeclarationSet) list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 更新SysCode
	 * 
	 * @param sysCodeEntity
	 */
	public void update(SysCode sysCodeEntity) {
		this.saveOrUpdate(sysCodeEntity);
	}

	/**
	 * 保存SysCode
	 * 
	 * @param sysCodeEntity
	 */
	public void save(SysCode sysCodeEntity) {
		this.saveOrUpdate(sysCodeEntity);
	}
	


	/**
	 * 查找所有存在的 Render Data Column
	 * 
	 * @return
	 */
	public ApplyCustomBillParameter findApplyCustomBillParameter() {
		List list = super.findNoCache(
				"from ApplyCustomBillParameter a where a.company = ? ",
				new Object[] { CommonUtils.getCompany() });
		if (list != null && list.size() > 0) {
			return (ApplyCustomBillParameter) (list.get(0));
		}
		return null;
	}
	
	/**
	 * 查找其他参数设置资料
	 * 
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	public List findCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		while (list.size() <= 0) {
			CompanyOther companyOther = new CompanyOther();
			companyOther.setCompany(CommonUtils.getCompany());
			this.getHibernateTemplate().saveOrUpdate(companyOther);
			System.out.println("--------------1:" + companyOther.getOptLock());
			list = this.find(
					"select a from CompanyOther a where a.company.id=?",
					new Object[] { CommonUtils.getCompany().getId() });
		}
		return list;
	}

	/**
	 * 保存呈现数据列表
	 * 
	 * @param list
	 */
	public void saveApplyCustomBillParameter(ApplyCustomBillParameter a) {
		this.saveOrUpdate(a);
	}

}
