package com.bestway.bcus.system.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.bestway.bcus.system.entity.CommonCheckNull;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.bcus.system.entity.ReportControl;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;

/**
 * com.bestway.bcus.system.dao.CompanyDao
 * 
 * @author refdom
 * 
 */
public class CompanyDao extends BaseDao {

	/**
	 * 查找所有公司资料
	 * 
	 * @return List 是Company型，公司资料
	 * @throws HibernateException
	 *             抛出的异常
	 */
	public List findCompanies() {
		return this.find("select a from Company a ");
	}
	/**
	 * 查找启用的所有公司资料
	 */
	public List findEnableCompanies() {
		List<Company> list = this.find("select a from Company a where (a.isEnable = ? or a.isEnable is null)",new Object[]{true});
		return list;
	}

	/**
	 * 查找当前公司的资料
	 * 
	 * @return List 是Company型，公司资料
	 * @throws HibernateException
	 *             抛出的异常
	 */
	public List findCompaniesCurr() {
		return this.find("select a from Company a where a.isCurrCompany = ?",
				new Object[] { new Boolean(true) });
	}

	/**
	 * 根据经营单位名称查找公司资料
	 * 
	 * @param buName
	 *            经营单位名称
	 * @return Company 公司资料
	 */
	public Company findCompaniesByName(String buName) {
		if (buName == null || buName.equals("")) {
			return null;
		}
		List list = this.find("select a from Company a where a.buName=?",
				new Object[] { buName });
		if (list.size() > 0) {
			return (Company) list.get(0);
		}
		return null;
	}

	/**
	 * 根据经营单位海关编码查找公司资料
	 * 
	 * @param buCode
	 *            经营单位海关编码
	 * @return Company 公司资料
	 */
	public Company findCompaniesByCode(String buCode) {
		if (buCode == null || buCode.equals("")) {
			return null;
		}
		List list = this.find("select a from Company a where a.buCode=?",
				new Object[] { buCode });
		if (list.size() > 0) {
			return (Company) list.get(0);
		}
		return null;
	}

	/**
	 * 保存公司资料
	 * 
	 * @param company
	 *            公司资料
	 * @return Company 要保存的公司资料
	 */
	public Company saveCompany(Company company) {
		this.saveOrUpdate(company);
		return company;
	}

	/**
	 * 保存多个公司资料
	 * 
	 * @param companies
	 *            是List型，多个公司的资料
	 * @throws HibernateException
	 *             抛出的异常
	 */
	public void saveCompanies(List companies) throws HibernateException {
		Session sess = getSession();
		for (int i = 0; i < companies.size(); i++) {
			sess.saveOrUpdate(companies.get(i));
		}
		sess.flush();
		sess.close();
	}

	/**
	 * 手册Object
	 * 
	 * @param obj
	 *            是Object型
	 */
	public void deleteCompany(Object obj) {
		this.delete(obj);
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
	 * 保存其他参数设置
	 * 
	 * @param obj
	 *            是CompanyOther型，其他参数设置资料
	 */
	public void saveCompanyOther(CompanyOther obj) {
		if (obj.getCompany() == null) {
			obj.setCompany(CommonUtils.getCompany());
		}
		try {
			this.getHibernateTemplate().saveOrUpdate(obj);
			this.getHibernateTemplate().flush();
		} catch (HibernateOptimisticLockingFailureException ex) {// StaleObjectStateException
			throw new RuntimeException(
					"其他参数的数据已经被其他客户修改，不能保存！\n请重新打开此窗口进行数据修改保存!");
		}
		System.out.println("--------------2:" + obj.getOptLock());
	}

	/**
	 * 保存数据检查选项
	 */
	public void saveCommonCheckNull(CommonCheckNull obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 保存用户资料
	 * 
	 * @param obj
	 *            是AclUser型，用户资料
	 */
	public void saveAclUser(AclUser obj) {
		this.saveOrUpdate(obj);
	}

	public List findCustomsDeclarationSet() {
		return this.find(
				"select a from CustomsDeclarationSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	public void saveCustomsSet(CustomsDeclarationSet obj) {
		this.saveOrUpdate(obj);
	}

	public void deleteCustomsSet(List list) {
		this.deleteAll(list);
	}

	public CustomsDeclarationSet findCustomsSet(Integer type) {
		if (type != null) {
			List list = this
					.find(
							"select a from CustomsDeclarationSet a where a.impType = ? and a.company.id = ?",
							new Object[] { type,
									CommonUtils.getCompany().getId() });
			if (list != null && list.size() > 0) {
				return (CustomsDeclarationSet) list.get(0);
			}
		}
		return null;
	}

	public List findCommonCheckNull() {
		return this
				.find(
						"select a from CommonCheckNull a where a.company.id = ? order by a.className",
						new Object[] { CommonUtils.getCompany().getId() });
	}

	public void deleteLs(List ls) {
		this.deleteAll(ls);
	}

	public List findClassByName(String className) {
		return this
				.find(
						"select a from CommonCheckNull a where a.company.id=? and a.className = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								className });
	}

	public List findInputTableColumnSet(String tableFlag) {
		return this.find(
				"select a from InputTableColumnSet a where a.company.id=? "
						+ " and a.tableFlag=? order by a.sortNo ",
				new Object[] { CommonUtils.getCompany().getId(), tableFlag });
	}
	
	/**
	 * 保存报关栏位设置参数
	 * @param obj
	 */
	public void saveReportControl(ReportControl reportControl) {
		this.saveOrUpdate(reportControl);
	}
	/**
	 * 查找保存报关栏位设置参数资料
	 * 
	 * @return List 是CompanyOther型，其他参数设置资料
	 */
	public List findReportControl() {
//		System.out.println("1111111111111111");
//		System.out.println("CommonUtils.getCompany().getId()="+CommonUtils.getCompany().getId());
		List list = this.find(
				"select a from ReportControl a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		
//		System.out.println("list"+list.size());
		if (list.size() == 0) {
//			System.out.println("CommonUtils.getCompany()="+CommonUtils.getCompany());
			ReportControl reportControl=new ReportControl();
			reportControl.setCompany(CommonUtils.getCompany());
			this.saveOrUpdate(reportControl);
			list = this.find(
					"select a from ReportControl a where a.company.id=?",
					new Object[] { CommonUtils.getCompany().getId() });
//			System.out.println("list"+list.size());
		}
//		System.out.println("22222222222");
		return list;
	}
	
}
