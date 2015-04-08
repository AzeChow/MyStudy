package com.bestway.pis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.pis.entity.BrokerCorp;
import com.bestway.pis.entity.EspAuthorityBrokerCorp;

public class PisDao extends BaseDao {

	/**
	 * 根据ID查找用户
	 * 
	 * @param id
	 * @return
	 */
	public AclUser findAclUserById(String id) {
		List list = this.find("select a from AclUser a where a.id=? ",
				new Object[] { id });
		if (list.size() > 0) {
			return (AclUser) list.get(0);
		} else {
			return new AclUser();
		}
	}

	/**
	 * 查询代理公司
	 * 
	 * @return
	 */
	public List findBrokerCorp() {
		String hql = " select a from BrokerCorp a where a.company.id=?";
		return this.find(hql, CommonUtils.getCompany().getId());
	}

	/**
	 * 查询已授权的代理公司
	 * 
	 * @return
	 */
	public List findBrokerCorpByMainBusiness(String mainBusiness) {
//		return this
//				.find(" select distinct a.espAuthorityBrokerCorp.brokerCorp "
//						+ " from EspAuthorityBrokerCorpItem a "
//						+ " where a.espAuthorityBrokerCorp.brokerCorp.company.id = ? "
//						+ " and a.mainBusiness=?  ", new Object[] {
//						CommonUtils.getCompany().getId(), mainBusiness });
		return this.find("select a from BrokerCorp a where a.company = ? ", new Object[]{CommonUtils.getCompany()});
	}

	// ///////////////////////////////////////PisDecDao//////////////////////////////////////////////
	/**
	 * 查找报关单
	 * 
	 * @param impExpFlag
	 * @param beginDate
	 * @param endDate
	 * @param statuses
	 * @return
	 */
	public List<BaseCustomsDeclaration> findDecHead(Integer impExpFlag,
			String emsNo, Date beginDate, Date endDate, Boolean isCheck,
			Boolean isSend, Integer projectType) {

		List<Object> params = new ArrayList<Object>();

		String tableName = "BcsCustomsDeclaration";

		// 电子化手册
		if (ProjectType.BCS == projectType) {

			tableName = "BcsCustomsDeclaration";

		} else if (ProjectType.BCUS == projectType) {

			tableName = "CustomsDeclaration";

		} else if (ProjectType.DZSC == projectType) {

			tableName = "DzscCustomsDeclaration";
		}

		String hql = "select a from " + tableName
				+ " a where a.impExpFlag=? and a.company.id=? ";

		params.add(0, impExpFlag);

		params.add(1, CommonUtils.getCompany().getId());

		if (emsNo != null && !"".equals(emsNo.trim())) {

			hql += " and a.emsHeadH2k=? ";

			params.add(emsNo.trim());

		}
		if (beginDate != null) {
			hql += " and (a.declarationDate>=?  or a.declarationDate is null) ";
			params.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.declarationDate<=?  or a.declarationDate is null) ";
			params.add(CommonUtils.getEndDate(endDate));
		}
		// 当选择的是已检查的时候，查询一检查为发送；当选择的是已经发送的时候查询已发送且已经检查
		if (isCheck != null && isCheck) {
			hql += " and a.isCheck=? and a.isSend=? ";
			params.add(true);
			params.add(false);
		} else if (isSend != null && isSend) {
			hql += " and a.isSend=? and a.isCheck=? ";
			params.add(true);
			params.add(true);
		}

		hql += " and a.effective=?";
		params.add(false);

		hql += " order by a.serialNumber desc";
		System.out.println(hql);
		return this.find(hql, params.toArray());
	}

	/**
	 * 查询报关单表体 按照箱号版本号排序
	 * 
	 * @param head
	 * @return
	 */
	public List<BaseCustomsDeclarationCommInfo> findDecItem(
			BaseCustomsDeclaration head, Integer projectType) {
		String tableName = "BcsCustomsDeclarationCommInfo";
		if (ProjectType.BCS == projectType) {
			tableName = "BcsCustomsDeclarationCommInfo";
		} else if (ProjectType.BCUS == projectType) {
			tableName = "CustomsDeclarationCommInfo";
		} else if (ProjectType.DZSC == projectType) {
			tableName = "DzscCustomsDeclarationCommInfo";
		}
		String hql = " select a from "
				+ tableName
				+ " a where a.baseCustomsDeclaration.id=? order by a.commSerialNo ";
		return this.find(hql, head.getId());
	}

	/**
	 * 取得集装箱号来自报关单头
	 * 
	 * @param head
	 *            报关单表头
	 * @return 与指定的报关单表头匹配的报关单中的集装箱号
	 */
	public List<BaseCustomsDeclarationContainer> findDecContainer(
			BaseCustomsDeclaration head, Integer projectType) {

		String tableName = "BcsCustomsDeclarationContainer";
		if (ProjectType.BCS == projectType) {
			tableName = "BcsCustomsDeclarationContainer";
		} else if (ProjectType.BCUS == projectType) {
			tableName = "CustomsDeclarationContainer";
		} else if (ProjectType.DZSC == projectType) {
			tableName = "DzscCustomsDeclarationContainer";
		}

		if ((head.getId() != null) && (!head.getId().equals(""))) {
			String hql = "select a from " + tableName + " as a ";
			hql += "where a.baseCustomsDeclaration.id=?";
			return this.find(hql, head.getId());
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 查找基础参数
	 * 
	 * @param entityClassName
	 * @return
	 */
	public List findCustomBaseEntityList(String entityClassName) {
		String hql = "select a from " + entityClassName + " a ";
		List list = this.find(hql);
		return list;
	}

	// //////////////////////////////////////PisGtsDecDao//////////////////////////////////////////
	// //////////////////////////////////////PisSyncDao//////////////////////////////////////////
	public CompanyOther findCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id=?",
				CommonUtils.getCompany().getId());
		CompanyOther companyOther = list.size() > 0 ? (CompanyOther) list
				.get(0) : null;
		if (companyOther == null) {
			companyOther = new CompanyOther();
			// coMsgParam.initDefault();
			this.saveOrUpdate(companyOther);
		}
		return companyOther;
	}

	/**
	 * 清除esp服务器信息
	 */
	public void clearPisEspServer() {
		this.batchUpdateOrDelete(
				"delete from PisEspServer a where a.company.id = ? "
						+ " and not exists ( select b.id from BrokerCorp b where b.pisEspServer.id = a.id)",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 清除esp服务器信息
	 *
	 * @param brokerCorp
	 */
	public void clearBrokerCorp(BrokerCorp brokerCorp) {
		this.batchUpdateOrDelete(
				"delete from EspAuthorityBrokerCorpItem a where a.espAuthorityBrokerCorp.id in"
						+ " ( select e.id from EspAuthorityBrokerCorp e where e.brokerCorp.id = ? ) ",
				brokerCorp.getId());
		this.batchUpdateOrDelete(
				"delete from EspAuthorityBrokerCorp a where a.brokerCorp.id = ? ",
				brokerCorp.getId());
		this.batchUpdateOrDelete("delete from BrokerCorp a where a.id = ? ",
				brokerCorp.getId());
	}

	/**
	 * 查找同步账号esp
	 *
	 * @return
	 */
	public List findPisSyncAclUser() {

		return find("select a from PisSyncAclUser a where a.company.id = ?  ",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查找同步申报单位esp
	 *
	 * @return
	 */
	public List findPisSyncAgentCorp() {
		return this.find(
				"select a from PisSyncAgentCorp a where a.company.id = ? ",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查找同步账号esp
	 *
	 * @return
	 */
	public List findAclUser() {
		return find("select a from AclUser a where a.company.id = ? ",
				CommonUtils.getCompany().getId());
	}

	public List findAclUserByLoginName(final Object[] params, Class entityName) {

		String name = entityName.getSimpleName();

		StringBuffer hql = new StringBuffer("select a from " + name
				+ " a where");

		for (int i = 0; i < params.length - 1; i++) {

			hql.append(" a.loginName = ? ");

			if (i != params.length - 2) {

				hql.append(" or ");

			}

		}

		hql.append(" and a.company.id = ? ");

		System.out.println(hql.toString());

		return find(hql.toString(), params);

	}

	/**
	 * 查找 被选择的 同步帐号 esp
	 * 
	 * @param params
	 * @return
	 */
	public List findPisSyncAcUserBySelected(final Object[] params) {

		StringBuffer hql = new StringBuffer(
				"select a from PisSyncAclUser a where");

		for (int i = 0; i < params.length - 1; i++) {

			hql.append(" a.loginName = ? ");

			if (i != params.length - 2) {

				hql.append(" or ");

			}

		}

		hql.append(" and a.company.id = ? ");

		System.out.println(hql.toString());

		return find(hql.toString(), params);

	}

	/**
	 * 查找申报单位
	 *
	 * @return
	 */
	public List findCompany() {
		return this.find("select a from Company a");
	}

	/**
	 * 删除PisSyncAgentCorp
	 */
	public void clearPisSyncAgentCorp() {
		this.batchUpdateOrDelete(
				"delete from PisSyncAgentCorp p where p.company.id = ?",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查找esp服务器
	 *
	 * @return
	 */
	public List findPisEspServer() {
		return this.find(
				"select a from PisEspServer a where a.company.id = ? ",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查找授权的esp服务器
	 *
	 * @return
	 */
	public List findEspAuthorityBrokerCorpPisEspServer() {
		return find(
				"select distinct a.brokerCorp.pisEspServer from EspAuthorityBrokerCorp a where a.brokerCorp.company.id = ? ",
				CommonUtils.getCompany().getId());
	}

	/**
	 * 查找授权代理公司
	 *
	 * @return
	 */
	public List findEspAuthorityBrokerCorp() {
		return this
				.find("select a from EspAuthorityBrokerCorp a where a.brokerCorp.company.id = ? ",
						CommonUtils.getCompany().getId());
	}

	/**
	 * 查找授权代理公司明细
	 *
	 * @param espAuthorityBrokerCorp
	 * @return
	 */
	public List findEspAuthorityBrokerCorpItem(
			EspAuthorityBrokerCorp espAuthorityBrokerCorp) {
		return this
				.find("select a from EspAuthorityBrokerCorpItem a where a.espAuthorityBrokerCorp = ? ",
						espAuthorityBrokerCorp);
	}

	/**
	 * 删除已授权代理公司业务明细信息
	 *
	 * @param companyBrokerCorpList
	 */
	public void delEspAuthorityBrokerCorpItem(
			List<EspAuthorityBrokerCorp> companyBrokerCorpList) {
		List companyBrokerCorpIdList = new ArrayList();
		for (EspAuthorityBrokerCorp companyBrokerCorp : companyBrokerCorpList) {
			companyBrokerCorpIdList.add(companyBrokerCorp.getId());
		}
		if (!companyBrokerCorpIdList.isEmpty()) {
			String hql = "delete from EspAuthorityBrokerCorpItem c where c.espAuthorityBrokerCorp.id in ('"
					.concat(StringUtils.join(
							companyBrokerCorpIdList.iterator(), "','")).concat(
							"')");
			this.batchUpdateOrDelete(hql);
		}

	}

	/**
	 * 查询当前登录公司
	 */
	public Company findIsCurrCompany() {
		String hql = "select a from Company a where a.isCurrCompany=?";
		List list = this.find(hql, new Object[] { true });
		if (list.size() > 0) {
			return (Company) list.get(0);
		}
		return new Company();
	}

	public List findBaseEmsHead(Integer projectType) {

		String hql = "";

		List list = new ArrayList();

		if (ProjectType.BCS == projectType) {
			hql = "select a from Contract a where a.company.id = ?  and a.declareState=?";
			list = this.find(hql,
					new Object[] { CommonUtils.getCompany().getId(),
							DeclareState.PROCESS_EXE });

		} else if (ProjectType.BCUS == projectType) {
			hql = "select a from EmsHeadH2k a where a.company.id = ? and a.historyState=? order by a.modifyTimes DESC";
			list = this.find(hql, new Object[] {
					CommonUtils.getCompany().getId(), new Boolean(false) });
		}

		return list;

	}

	/**
	 * 查询合同
	 * 
	 * @param emsNo
	 *            合同号
	 * @param projectType
	 * @return
	 */
	public BaseEmsHead findBaseEmsHeadByEmsNo(String emsNo, Integer projectType) {
		String hql = "";
		List list = new ArrayList();
		if (ProjectType.BCS == projectType) {
			hql = "select a from Contract a where a.company.id = ? and a.emsNo=? and a.declareState=?";
			list = this.find(hql, new Object[] {
					CommonUtils.getCompany().getId(), emsNo,
					DeclareState.PROCESS_EXE });
		} else if (ProjectType.BCUS == projectType) {
			hql = "select a from EmsHeadH2k a where a.company.id = ? and a.emsNo=? and a.historyState=? order by a.modifyTimes DESC";
			list = this.find(hql,
					new Object[] { CommonUtils.getCompany().getId(), emsNo,
							new Boolean(false) });
		}
		if (list.size() > 0) {
			return (BaseEmsHead) list.get(0);
		}
		return new BaseEmsHead();
	}

	public Integer findProjectType() {
		List bcsList = this
				.find("select count(a.id) from BcsCustomsDeclaration a ");
		List bcusList = this
				.find("select count(a.id) from CustomsDeclaration a ");
		int bcs = 0;
		int bcus = 0;
		if (bcsList.size() > 0) {
			bcs = Integer.parseInt(bcsList.get(0).toString());
		}
		if (bcusList.size() > 0) {
			bcus = Integer.parseInt(bcusList.get(0).toString());
		}
		return bcs > bcus ? ProjectType.BCS : ProjectType.BCUS;
		// List bcsList = this.find("select a.emsNo from Contract a ");
		// if(bcsList.size()>0){
		// String emsNo = (String)bcsList.get(0);
		// if(emsNo.indexOf("C")==0){
		// return ProjectType.BCS;
		// }
		// }
		// return ProjectType.BCUS;
	}

	public List findBaseCustomsDeclarationCommInfo(
			BaseCustomsDeclaration decHead, Integer projectType) {
		List list = new ArrayList();
		if (ProjectType.BCS == projectType.intValue()) {
			list = this
					.find("select a from BcsCustomsDeclarationCommInfo a where a.baseCustomsDeclaration.id=?",
							new Object[] { decHead.getId() });
		} else if (ProjectType.BCUS == projectType.intValue()) {
			list = this
					.find("select a from CustomsDeclarationCommInfo a where a.baseCustomsDeclaration.id=?",
							new Object[] { decHead.getId() });
		}
		return list;
	}

	public Company findCompany(String code) {
		List list = this.find("select a from Company a where a.code = ?",
				new Object[] { code });
		if (list.size() > 0) {
			return (Company) list.get(0);
		}
		return null;
	}

}
