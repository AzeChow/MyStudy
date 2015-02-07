/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.customs.common.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.CheckupComplex;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.MakeApplyToCustoms;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.message.entity.DecSupplementList;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SendState;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.MakeFptBillCustomsDeclaration;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.MakeCustomsDeclaration;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.customs.common.entity.BaseContractHead;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseCustomsFromMateriel;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.BaseExportInvoiceItem;
import com.bestway.customs.common.logic.BaseEncUtils;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * 基础DAO
 * 
 * @author Administrator
 * @param <CustomsDeclarationDeleteinfo>
 *            lm checked by 2010-05-10
 */
@SuppressWarnings("unchecked")
public class BaseEncDao extends BaseDao {
	/**
	 * 模块类型 分为：BCUS 0,BCS 1,DZBA 2
	 */
	public int projectType = ProjectType.BCUS;

	/**
	 * 取得模块类型 分为：BCUS 0,BCS 1,DZBA 2
	 * 
	 * @return 模块类型 分为：BCUS 0,BCS 1,DZBA 2
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * 设置模块类型 分为：BCUS 0,BCS 1,DZBA 2
	 * 
	 * @param moduleType
	 *            模块类型 分为：BCUS 0,BCS 1,DZBA 2
	 */
	public void setProjectType(int moduleType) {
		this.projectType = moduleType;
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param code
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List<BaseCustomsDeclaration> findCustomsDeclarationByCode(String code) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.company.id=? " + " and a.customsDeclarationCode=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				code });
	}

	/**
	 * 取得报关单
	 * 
	 * @param code
	 *            报关单预录入号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List findCustomsDeclarationByPreCode(String preCode) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.company.id=? "
				+ " and a.preCustomsDeclarationCode=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				preCode });
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param id
	 *            报关单ID
	 */
	public BaseCustomsDeclaration findCustomsDeclarationById(String id) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.company.id=? and a.id=? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), id });
		if (!list.isEmpty()) {
			return (BaseCustomsDeclaration) list.get(0);
		}
		return null;
	}

	/**
	 * 取得报关单明细
	 * 
	 * @param master
	 *            报关单表头
	 * @return 返回报关单明细列表（用于出口收汇核销单打印）
	 */
	public List findCustomsDeclarationInfo(BaseCustomsDeclaration master) {
		List list = null;
		String select = "";
		String where = " where a.baseCustomsDeclaration.id=?"
				+ " order by a.serialNumber asc ";
		if (list == null || list.size() == 0) {
			select = "select a from CustomsDeclarationCommInfo as a";
			list = this.find(select + where, master.getId());
		}
		if (list == null || list.size() == 0) {
			select = "select a from BcsCustomsDeclarationCommInfo as a";
			list = this.find(select + where, master.getId());
		}
		if (list == null || list.size() == 0) {
			select = "select a from DzscCustomsDeclarationCommInfo as a";
			list = this.find(select + where, master.getId());
		}
		return list;
	}
	
	/**
	 * 取得报关单明细
	 * 
	 * @param baseID 表头ID
	 * @return 返回报关单明细列表
	 */
	public List findCustomsDeclarationInfos(List<String> baseID,Integer projectType){
		
		
		String tableName = "";
		switch (projectType) {
		case ProjectType.BCUS:
			tableName = " CustomsDeclarationCommInfo ";
			break;
		case ProjectType.BCS:
			tableName = " BcsCustomsDeclarationCommInfo ";
			break;
		case ProjectType.DZSC:
			tableName = " DzscCustomsDeclarationCommInfo ";
			break;
		default:
			tableName = " CustomsDeclarationCommInfo ";
			break;
		}
		
		String sql = "select a from "+tableName+"a where ";
		
		List list = new ArrayList();
		for (int i = 0; i < baseID.size(); i+=1000) {
			int maxIndex = i+1000 > baseID.size() ? baseID.size() : i+1000;
			String ql = sql+"a.baseCustomsDeclaration.id in ('"+
					StringUtils.join(baseID.subList(i, maxIndex).toArray(),"','")+"') order by a.serialNumber";
			list.addAll(this.find(ql));
		}
		
		return list;
	}

	/**
	 * 取得报关单
	 * 
	 * @param unificationCode
	 *            统一编号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List findCustomsDeclarationByUnificationCode(String unificationCode) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.company.id=? " + " and a.unificationCode=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				unificationCode });
	}

	/**
	 * 检查发票，核销单号，司机纸号码是否重复
	 * 
	 * @param projectType
	 *            模块类型 分为：BCUS 0,BCS 1,DZBA 2
	 * @param serialNumber
	 *            报关单流水号
	 * @param fields
	 *            字段
	 * @param checkValue
	 *            对应的值
	 * @return
	 */
	public boolean isReCustoms(int projectType, Integer serialNumber,
			String fields, String checkValue) {

		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.company.id=? and a.serialNumber <> ? and  a."
				+ fields + " = ?";
		System.out.println("sql:" + hql);
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), serialNumber, checkValue });
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 保存报关单表头
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头信息
	 */
	public void saveCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>dao  >>>>>>>>>>>>" + baseCustomsDeclaration);
		this.saveOrUpdate(baseCustomsDeclaration);
	}

	
	/**
	 * 找申报公司
	 * @param company
	 * @return
	 */
	public List<Company> findCompany(String company) {
		String hsql = "select a from Company a where a.code =?";
		return this.find(hsql, new Object[]{company});
	}
//	/**
//	 * 找商品编码
//	 * @param company
//	 * @return
//	 */
//	public List<Complex> findCommCode(String commCode) {
//		String hsql = "select a from Complex a  where a.code =?";
//		return this.find(hsql, new Object[]{commCode});
//	}
	/**
	 * 保存实体对象
	 * 
	 * @param obj
	 *            数据保存对象
	 * @throws DataAccessException
	 *             数据存储异常
	 */
	public void saveObject(Object obj) throws DataAccessException {
		this.getHibernateTemplate().save(obj);
	}

	/**
	 * 删除报关单表头
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头信息
	 */
	public void deleteCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		this.delete(baseCustomsDeclaration);
	}

	/**
	 * 查询进出货转厂单据
	 */
	public List<TransferFactoryBill> findTransferFactoryBill(BaseCustomsDeclaration baseCustomsDeclaration){
		List<Object> param = new ArrayList<Object>();
		String hql = "select a from TransferFactoryBill a where a.company.id= ? " +
				"and a.id in (select a.transFactBillId from MakeCustomsDeclaration a where a.customsDeclarationId = ? ) ";
		param.add(CommonUtils.getCompany().getId());
		param.add(baseCustomsDeclaration.getId());
		
		List transfer = this.find(hql,param.toArray());
		return transfer;
	}
	
	/**
	 * 取得报关单
	 * 
	 * @param id
	 *            报关单id
	 * @return 与指定id匹配的报关单
	 */
	public BaseCustomsDeclaration findCustomsDeclaration(String id) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where  a.company.id=? and a.id = ? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), id });
		if (list.size() > 0) {
			return (BaseCustomsDeclaration) list.get(0);
		}
		return null;
	}

	/**
	 * 取得报关单
	 * 
	 * @param projectType
	 *            模块类型
	 * @param id
	 *            报关单id
	 * @return 在指定的模块中与指定的报关单id匹配的报关单
	 */
	public BaseCustomsDeclaration findCustomsDeclaration(int projectType,
			String id) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += " where  a.company.id=? and a.id = ? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), id });
		if (list.size() > 0) {
			return (BaseCustomsDeclaration) list.get(0);
		}
		return null;
	}

	/**
	 * 取得报关单
	 * 
	 * @param projectType
	 *            模块类型
	 * @param taskId
	 *            报关单id
	 * @return 在指定的模块中与指定的报关单id匹配的报关单
	 */
	public BaseCustomsDeclaration findCustomsDeclarationByTcsTaskId(
			int projectType, String taskId) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += " where  a.company.id=? and a.tcsTaskId = ? ";
		List list = this.find(hql, new Object[] {
				CommonUtils.getCompany().getId(), taskId });
		if (list.size() > 0) {
			return (BaseCustomsDeclaration) list.get(0);
		}
		return null;
	}

	/**
	 * 取得未生效报关单来自进出口类型
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 与指定的进出口类型匹配没有生效的报关单
	 */
	public List<BaseCustomsDeclaration> findCustomsDeclarationByImpExpType(
			int impExpType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where  a.company.id=? and a.impExpType = ? and ( a.effective = ? or a.effective is null)";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				impExpType, false });

	}

	/**
	 * 取得生效报关单来自进出口类型
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @param@param isEffective 是否生效
	 * @return 与指定的进出口类型匹配没有生效的报关单
	 */
	public List<BaseCustomsDeclaration> findCustomsDeclarationByImpExpFlag(
			int impExpFlag, Boolean isEffective) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		if (isEffective == null) {
			hql += " where  a.company.id=? and a.impExpFlag = ? ";
			return this.find(hql, new Object[] {
					CommonUtils.getCompany().getId(), impExpFlag });
		} else if (isEffective.booleanValue() == false) {
			hql += " where  a.company.id=? and a.impExpFlag = ? and ( a.effective = ? or a.effective is null) ";
		} else {
			hql += " where  a.company.id=? and a.impExpFlag = ? and ( a.effective = ? ) ";
		}
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				impExpFlag, isEffective });

	}

	/**
	 * 取得进口报关单
	 * 
	 * @return 进口报关单
	 */
	public List findImportCustomsDeclaration() {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.IMPORT),
				CommonUtils.getCompany().getId() });
	}
	
	
	/**
	 * 取得已检查进口报关单
	 * 
	 * @return 进口报关单
	 */
	public List findCheckedImportCustomsDeclaration() {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? and a.isCheck = ? ";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.IMPORT),
				CommonUtils.getCompany().getId(), Boolean.TRUE });
	}
	
	
	/**
	 * 取得一个月内已生成taskId的报关单
	 * 
	 * @return 进口报关单
	 */
	public List<BaseCustomsDeclaration> findDeclarationHasTaskIdUnEffect() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		StringBuilder hql = new StringBuilder();
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("select a  from CustomsDeclaration as a");
			break;
		case ProjectType.BCS:
			hql.append("select a  from BcsCustomsDeclaration as a");
			break;
		case ProjectType.DZSC:
			hql.append("select a  from DzscCustomsDeclaration as a");
			break;
		default:
			hql.append("select a  from CustomsDeclaration as a");
			break;
		}
		hql.append(" where a.impExpDate > ? and a.tcsTaskId is not null and a.company.id=? ");
		List<BaseCustomsDeclaration> list = this.find(hql.toString(), new Object[] { cal.getTime(), 
			CommonUtils.getCompany().getId() });
		List<BaseCustomsDeclaration> result = new ArrayList<BaseCustomsDeclaration>();
		if(list != null && !list.isEmpty()) {
			BaseCustomsDeclaration d = null;
			for (int i = 0; i < list.size(); i++) {
				d = list.get(i);
				if(CommonUtils.notEmpty(d.getTcsTaskId())) {
					result.add(d);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 取得进口报关单BY 申报日期
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在有效期内与指定的手册号匹配的进口报关单
	 */
	public List findImportCustomsDeclaration(String emsNo, Date beginDate,
			Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? ";
		parameters.add(Integer.valueOf(ImpExpFlag.IMPORT));
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hql += " and (a.declarationDate>=?  or a.declarationDate is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.declarationDate<=?  or a.declarationDate is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null || endDate != null) {
			hql += " order by a.serialNumber,a.declarationDate desc";
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param code
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public BaseCustomsDeclaration findExportCustomsDeclarationByCode(String code) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.impExpFlag=? and a.company.id=? "
				+ " and a.customsDeclarationCode=? ";
		List list = this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId(), code });
		if (list.size() > 0) {
			return (BaseCustomsDeclaration) list.get(0);
		}
		return null;
	}

	/**
	 * 取得出口报关单
	 * 
	 * @return 出口报关单
	 */
	public List findExportCustomsDeclaration() {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.impExpFlag=? and a.company.id=?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId() });
	}
	
	/**
	 * 取得已检查的出口报关单
	 * 
	 * @return 出口报关单
	 */
	public List findCheckedExportCustomsDeclaration() {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.impExpFlag=? and a.company.id=? and a.isCheck = ?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId(), Boolean.TRUE});
	}

	/**
	 * 查询进出口报关单删单信息
	 * 
	 * @param typeModel
	 *            项目类型:BCUS = 0;电子帐册,BCS = 1;电子化手册,DZSC = 3;电子手册
	 * @param emsNo
	 *            电子帐册号码
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 进出口报关单删单信息
	 */
	public List findCustomsDeclarationDelete(int typeModel, String emsNo,
			Date beginDate, Date endDate) {
		String hql = " select a from CustomsDeclarationDelete a  ";
		List<Object> parameters = new ArrayList<Object>();
		hql += " where a.typeModel=?  and a.company.id=?  ";
		parameters.add(typeModel);
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hql += " and (a.workDate>=? or a.workDate is null) ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hql += " and (a.workDate<=? or a.workDate is null) ";
			parameters.add(endDate);
		}
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		hql += " order by a.workDate";

		return this.find(hql, parameters.toArray());
	}

	/**
	 * 删除进出口报关单
	 * 
	 * @param typeModel
	 *            模块类型
	 * @param customsId
	 *            报关单头ID
	 * @return 删除符合条件的进出口报关单
	 */
	public List findImExportCustomsDeclarationDelete(int typeModel,
			String customsId) {
		String hql = "select a from CustomsDeclarationDeleteCommInfo as a  ";
		List<Object> parameters = new ArrayList<Object>();
		hql += " where a.baseCustomsDeclaration.typeModel=?  and a.company.id=?  and a.baseCustomsDeclaration.id=? ";
		parameters.add(typeModel);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(customsId);
		hql += " order by a.baseCustomsDeclaration.workDate,a.commSerialNo";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得出口报关单 BY 申报日期
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在有效期内与指定的手册号匹配的出口报关单
	 */
	public List findExportCustomsDeclaration(String emsNo, Date beginDate,
			Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=?  and a.company.id=? ";
		parameters.add(Integer.valueOf(ImpExpFlag.EXPORT));
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hql += " and (a.declarationDate>=? or a.declarationDate is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.declarationDate<=? or a.declarationDate is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null || endDate != null) {
			hql += " order by a.serialNumber,a.declarationDate desc ";
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得特殊报关单
	 * 
	 * @return 特殊报关单
	 */
	public List findSpecialCustomsDeclaration() {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.SPECIAL),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得特殊报关单BY 申报日期
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在有效期内与指定的手册号匹配的特殊报关单
	 */
	public List findSpecialCustomsDeclaration(String emsNo, Date beginDate,
			Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? ";
		parameters.add(Integer.valueOf(ImpExpFlag.SPECIAL));
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hql += " and (a.declarationDate>=?  or a.declarationDate is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.declarationDate<=?  or a.declarationDate is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null || endDate != null) {
			hql += " order by a.serialNumber,a.declarationDate desc ";
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得特殊报关单BY 申报日期
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 在有效期内与指定的手册号匹配的特殊报关单
	 */
	@SuppressWarnings("rawtypes")
	public List findSpecialCustomsDeclaration(Date beginDate, Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? ";
		parameters.add(Integer.valueOf(ImpExpFlag.SPECIAL));
		parameters.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			hql += " and (a.declarationDate>=?  or a.declarationDate is null) ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and (a.declarationDate<=?  or a.declarationDate is null) ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		if (beginDate != null || endDate != null) {
			hql += " order by a.serialNumber,a.declarationDate desc ";
		}
		return this.find(hql, parameters.toArray());
	}

	protected String organizeStr(int m, int length) {
		String s = String.valueOf(m);
		int n = length - s.length();
		for (int i = 0; i < n; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 取得报关单预计录入号
	 * 
	 * @param projectType
	 *            模块类型
	 * @return 最大的报关单预录入号
	 */
	public String getMaxPreCustomsDeclarationCode() {
		// String code = "";
		// String hql = "";
		// switch (projectType) {
		// case ProjectType.BCUS:
		// hql = " select max(a.preCustomsDeclarationCode) from
		// CustomsDeclaration as a ";
		// break;
		// case ProjectType.BCS:
		// hql = " select max(a.preCustomsDeclarationCode) from
		// BcsCustomsDeclaration as a ";
		// break;
		// case ProjectType.DZSC:
		// hql = " select max(a.preCustomsDeclarationCode) from
		// DzscCustomsDeclaration as a ";
		// break;
		// default:
		// hql = " select max(a.preCustomsDeclarationCode) from
		// CustomsDeclaration as a ";
		// break;
		// }
		// List list = this.find(hql);
		// if (list.size() < 1) {
		// code = "000001";
		// } else if (list.get(0) == null) {
		// code = "000001";
		// } else {
		// String temp = list.get(0).toString();
		// if (temp.trim().equals("")) {
		// code = "000001";
		// } else {
		// int n = Integer.parseInt(temp) + 1;
		// code = organizeStr(n, 6);
		// }
		// }
		// return code;
		// synchronized (mutex) {
		//				
		// }

		synchronized (CommonUtils.localForPreCustomsDeclarationCode) {
			boolean isNotGetCode = true;
			String code = "000001";
			List list = this.find(
					"select a from CompanyOther a where a.company.id=?",
					new Object[] { CommonUtils.getCompany().getId() });
			if (list.size() <= 0 || list.get(0) == null) {
				CompanyOther companyOther = new CompanyOther();
				companyOther.setPreCustomsDeclarationCode(code);
				companyOther.setCompany(CommonUtils.getCompany());
				this.getHibernateTemplate().saveOrUpdate(companyOther);
				System.out.println("--------------5:"
						+ companyOther.getOptLock());
			} else {
				while (isNotGetCode) {
					CompanyOther companyOther = (CompanyOther) list.get(0);
					String temp = companyOther.getPreCustomsDeclarationCode();
					if (temp == null || temp.trim().equals("")) {
						code = "000001";
					} else {
						int n = Integer.parseInt(temp) + 1;
						code = organizeStr(n, 6);
					}
					companyOther.setPreCustomsDeclarationCode(code);
					try {
						this.getHibernateTemplate().saveOrUpdate(companyOther);
						this.getHibernateTemplate().flush();
						System.out.println("--------------3:"
								+ companyOther.getOptLock());
						isNotGetCode = false;
					} catch (HibernateOptimisticLockingFailureException ex) {// StaleObjectStateException
						isNotGetCode = true;
						list = this
								.find(
										"select a from CompanyOther a where a.company.id=?",
										new Object[] { CommonUtils.getCompany()
												.getId() });
					}
				}
			}
			return code;
		}

	}

	/**
	 * 取得报关单流水号
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return 与指定的进出口标志匹配的报关单流水号
	 */
	public Integer getCustomsDeclarationSerialNumber(int impExpFlag,
			String emsHeadH2k) {
		return BaseEncUtils.getMaxCustomsDeclarationSerialNumber(projectType,
				impExpFlag, emsHeadH2k, this);
	}

	/**
	 * 取得报关单流水号
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return 与指定的进出口标志匹配的报关单流水号
	 */
	public Integer getCustomsDeclarationSerialNumber(int projectType,
			int impExpFlag, String emsHeadH2k) {
		return BaseEncUtils.getMaxCustomsDeclarationSerialNumber(projectType,
				impExpFlag, emsHeadH2k, this);
	}

	/**
	 * 取得报关单流水号
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsHeadH2k
	 *            电子帐册表头
	 * @return 与指定的进出口标志匹配的报关单流水号
	 */
	public Integer findMaxSerialNumberFromDB(int impExpFlag, String emsHeadH2k) {
		Integer no = new Integer(0);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select max(a.serialNumber) from CustomsDeclaration as a ";
			break;
		case ProjectType.BCS:
			hql = " select max(a.serialNumber) from BcsCustomsDeclaration as a  ";
			break;
		case ProjectType.DZSC:
			hql = " select max(a.serialNumber) from DzscCustomsDeclaration as a  ";
			break;
		default:
			hql = " select max(a.serialNumber) from CustomsDeclaration as a  ";
			break;
		}
		// 现在手册、帐册号条件去掉，因为现在特殊表关单要求流水号依次排弃
		if (impExpFlag == ImpExpFlag.SPECIAL) {
			hql += " where a.impExpFlag=? and '1'=? and a.company.id = ?";
			emsHeadH2k = "1";
		} else {
			hql += " where a.impExpFlag=? and a.emsHeadH2k = ? and a.company.id = ?";
		}

		List list = this.find(hql, new Object[] { new Integer(impExpFlag),
				emsHeadH2k, CommonUtils.getCompany().getId() });
		if (list.size() < 1 || list.get(0) == null
				|| "".equals(list.get(0).toString().trim())) {
			no = new Integer(1);

		} else {
			String temp = list.get(0).toString();
			// int n = Integer.parseInt(temp) + 1;
			// no = new Integer(n);
			no = Integer.parseInt(temp);
		}
//		@SuppressWarnings("unused")
//		Object object = CommonUtils.getCompany().getId();
		return no;
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param baseCustomsDeclarationCommInfo
	 *            报关单商品信息
	 */
	public void saveCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		this.saveOrUpdate(baseCustomsDeclarationCommInfo);
	}

	public List findIsSpecification() {
		List specification =  new ArrayList();
		List  list = this.find(
				"select a.isSpecification from CompanyOther a where a.company.id = ? ",
				new Object[] { CommonUtils.getCompany().getId()});
		if(list.size() > 0 ){
			Object o = list.get(0);
			if(o instanceof Boolean){
				Boolean boo =(Boolean) o;
				specification.add(boo);
				return specification;
			}		
		}
		specification.add(false);
		return specification;
	}
	
	/**
	 * 保存报关单表头信息
	 * 
	 * @param customsDeclaration
	 *            报关单表头信息
	 */
	public void saveCustomsDeclarationHead(
			BaseCustomsDeclaration customsDeclaration) {
		this.saveOrUpdate(customsDeclaration);
	}

	/**
	 * 删除报关单物料信息
	 * 
	 * @param fromMateriel
	 *            报关单物料信息
	 */
	public void deletefromMateriel(BaseCustomsFromMateriel fromMateriel) {
		this.delete(fromMateriel);
	}

	/**
	 * 删除报关单商品信息
	 * 
	 * @param baseCustomsDeclarationCommInfo
	 *            报关单商品信息
	 */
	public void deleteCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		this.delete(baseCustomsDeclarationCommInfo);
	}

	/**
	 * 查找电子备案中间表信息来自报关单商品信息
	 * 
	 * @param id
	 *            电子备案报关单商品信息id
	 * @return 中间信息
	 */
	public List findMakeDzbaCustomsDeclarationByCommInfo(String id) {
		return this.find(
				"select m from MakeDzbaCustomsDeclaration m where m.company.id = ? "
						+ " and m.dzbaCustomsDeclarationCommInfo.id = ?   ",
				new Object[] { CommonUtils.getCompany().getId(), id });
	}
	
	

	/**
	 * 查找纸质手册中间表信息来自报关单商品信息
	 * 
	 * @param id
	 *            纸质手册报关单商品信息id
	 * @return 纸质手册中间信息
	 */
	public List findMakeBcsCustomsDeclarationByCommInfo(String id) {
		return this.find(
				"select m from MakeBcsCustomsDeclaration m where m.company.id = ? "
						+ " and m.bcsCustomsDeclarationCommInfo.id = ?   ",
				new Object[] { CommonUtils.getCompany().getId(), id });
	}

	/**
	 * 根据相关条件查询物料类型
	 * 
	 * @param id
	 *            报关单商品信息Id
	 * @param impExpType
	 *            进出口类型 是否通过深加工结转单生成的报关单并作了对应并进行查询
	 * @return 物料类型
	 */
	public List findMakeBillCorrespondingInfoBase(String id, int impExpType) {
		List parameters = new ArrayList();
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）
		String tableName = BillUtil
				.getMakeBillCorrespondingInfoTableName(materielType);
		if (tableName == null || "".equals(tableName.trim())) {
			return new ArrayList();
		}
		String hsql = " select a from "
				+ tableName
				+ " a where a.company.id = ? and a.customsDeclarationCommInfoId=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(id);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 中间信息来自报关单商品信息
	 * 
	 * @param id
	 *            报关单商品信息id
	 * @return 中间信息
	 */
	public List findMakeListToCustomsByCommInfo(String id) {
		return this.find(
				"select a  from MakeListToCustoms a where a.company.id = ?"
						+ " and a.customsInfo.id = ? ", new Object[] {
						CommonUtils.getCompany().getId(), id });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?"
				+ " order by a.serialNumber asc ";//order by a.commSerialNo,a.country.code asc
		System.out.println(baseCustomsDeclaration+" >>>>>>>>>>>>>>>>>>>>>> "+hql);
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * 
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByEmsNo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.find(" select a from DzscCustomsDeclarationCommInfo a "
				+ " where a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.baseCustomsDeclaration.id=? "
				+ " and a.baseCustomsDeclaration.company.id =? "
				+ "	order by a.commSerialNo ", new Object[] {
				baseCustomsDeclaration.getEmsHeadH2k(),
				baseCustomsDeclaration.getId(),
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * 
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoLegalAmountByEmsNo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "select a ";
		switch (projectType) {
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql += " from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.baseCustomsDeclaration.id=? "
				+ " and a.baseCustomsDeclaration.company.id =? "
				+ " order by a.serialNumber ";
		return this.find(hql, new Object[] {
				baseCustomsDeclaration.getEmsHeadH2k(),
				baseCustomsDeclaration.getId(),
				CommonUtils.getCompany().getId() });
		// return this
		// .find(
		// "select a.serialNumber,a.commAmount,a.unit.name,a.firstAmount,a.legalUnit.name,a.secondAmount,a.secondLegalUnit.name "
		// + " from BcsCustomsDeclarationCommInfo a "
		// + " where a.baseCustomsDeclaration.emsHeadH2k=? "
		// + " and a.baseCustomsDeclaration.id=? "
		// + " and a.baseCustomsDeclaration.company.id =? "
		// + " order by a.serialNumber ", new Object[] {
		// baseCustomsDeclaration.getEmsHeadH2k(),
		// baseCustomsDeclaration.getId(),
		// CommonUtils.getCompany().getId() });

	}

	/**
	 * 查询没有报关的手册通关备案料件
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @param seqNum
	 *            备案序号
	 * @param name
	 *            料件名称
	 * @param spec
	 *            型号规格
	 * @return List 存放了通关备案料件的一些资料
	 */
	public List findDzscMaterialInfo(
			BaseCustomsDeclaration baseCustomsDeclaration, Integer seqNum,
			String name, String spec) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a from " + " DzscEmsImgBill as a where "
				+ " a.dzscEmsPorHead.emsNo=? "
				+ " and a.dzscEmsPorHead.declareState=? "
				+ " and a.dzscEmsPorHead.company.id=? "
				+ " and a.seqNum=? and a.name=? ";
		parameters.add(baseCustomsDeclaration.getEmsHeadH2k());
		parameters.add(DzscState.EXECUTE);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(name);
		if (spec != null && !"".equals(spec.trim())) {
			hql += "and a.spec=? ";
			parameters.add(spec.trim());
		} else {
			hql += "and (a.spec=? or a.spec is null)";
			parameters.add("");
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查询没有报关的手册通关备案成品
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @param seqNum
	 *            备案序号
	 * @param name
	 *            料件名称
	 * @param spec
	 *            型号规格
	 * @return List 存放了通关备案成品的一些资料
	 */
	public List findDzscProductInfo(
			BaseCustomsDeclaration baseCustomsDeclaration, Integer seqNum,
			String name, String spec) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = " select a from " + " DzscEmsExgBill as a "
				+ " where a.dzscEmsPorHead.emsNo=? "
				+ " and a.dzscEmsPorHead.declareState=? "
				+ " and a.dzscEmsPorHead.company.id=? "
				+ " and a.seqNum=? and a.name=? ";
		parameters.add(baseCustomsDeclaration.getEmsHeadH2k());
		parameters.add(DzscState.EXECUTE);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(name);
		if (spec != null && !"".equals(spec.trim())) {
			hql += "and a.spec=? ";
			parameters.add(spec.trim());
		} else {
			hql += "and (a.spec=? or a.spec is null)";
			parameters.add("");
		}

		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByIsEffective(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=? and a.baseCustomsDeclaration.effective=? "
				+ " order by a.serialNumber asc ";
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId(),
				true });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByNoEffective(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=? and a.baseCustomsDeclaration.effective=? "
				+ " order by a.serialNumber asc ";
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId(),
				false });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            运输工具
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByConveyance(String conveyance,
			int impExpFlag) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.conveyance = ? and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and ( a.baseCustomsDeclaration.cancel is null or a.baseCustomsDeclaration.cancel = ? ) "
				+ " order by a.serialNumber ";
		System.out.println(hql);
		return this.find(hql, new Object[] { conveyance, impExpFlag, false });
	}
	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            运输工具
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoByConveyance(String conveyance,
			Integer impExpFlag,Integer projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.conveyance = ? and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and ( a.baseCustomsDeclaration.cancel is null or a.baseCustomsDeclaration.cancel = ? ) "
				+ " order by a.serialNumber ";
		System.out.println(hql);
		return this.find(hql, new Object[] { conveyance, impExpFlag, false });
	}

	/**
	 * 取得进口报关单
	 * 
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 进口报关单
	 */
	public List findCustomsDeclarationByConveyance(String conveyance,
			int impExpFlag) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=?  and a.conveyance=? "
				+ " and ( a.cancel is null or a.cancel = ? )";
		parameters.add(impExpFlag);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(conveyance);
		parameters.add(false);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param conveyance
	 *            运输工具
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单商品信息
	 */
	public List findMergerCustomsDeclarationCommInfoByConveyance(
			String conveyance, int impExpFlag) {
		String hql = "select  a ";
		switch (projectType) {
		case ProjectType.BCUS:
			hql += "  from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql += "  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.conveyance = ? and a.baseCustomsDeclaration.impExpFlag = ? "
				+ " and ( a.baseCustomsDeclaration.cancel is null or a.baseCustomsDeclaration.cancel = ? ) ";
		hql += "  order by a.complex.code";

		return this.find(hql, new Object[] { conveyance, impExpFlag, false });
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoOrderByCommSerialNo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?"
				+ " order by a.commSerialNo,a.version";
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	}

	/**
	 * 取得报关单商品信息来自报关单头
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @param projectType
	 *            模块信息
	 * @return 与指定的报关单头信息匹配的报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo(
			BaseCustomsDeclaration baseCustomsDeclaration, int projectType) {
		if(baseCustomsDeclaration==null){
			return null;
		}
		System.out.println("--projectType:" + projectType);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?"
				+ " order by a.serialNumber ";
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	}

	/**
	 * 取得报关单金额
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @return 与指定的报关单头匹配的报关单的金额
	 */
	public Double findCustomsDeclarationMoney(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = " select sum(a.dollarTotalPrice) ";
		switch (projectType) {
		case ProjectType.BCUS:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql += " from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql += " from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql += " from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?";
		List list = this.find(hql, new Object[] { baseCustomsDeclaration
				.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Double.valueOf(list.get(0).toString());
		} else {
			return new Double(0.0);
		}
	}

	/**
	 * 取得报关单商品信息
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 符合指定的进出口标志的报关单商品信息
	 */
	public List findCustomsDeclarationCommInfo(int impExpFlag) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclarationCommInfo a ";
			break;
		default:
			hql = "select a from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ "  order by a.baseCustomsDeclaration.id,a.serialNumber ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				new Integer(impExpFlag) });
	}

	/**
	 * 根据报关单ID取得报关单商品信息
	 * 
	 * @param customsDeclarationID
	 *            报关单id
	 * @return 与指定报关单id匹配的报关单商品信息
	 */
	public List findCommInfoByCustomsDeclarationID(String customsDeclarationID) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a ";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclarationCommInfo a ";
			break;
		default:
			hql = "select a from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=? "
				+ " order by a.serialNumber  ";
		return this.find(hql, new Object[] { customsDeclarationID });
	}

	/**
	 * 取得集装箱号来自报关单头
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return 与指定的报关单表头匹配的报关单中的集装箱号
	 */
	public List findContainerByCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		if ((baseCustomsDeclaration.getId() != null)
				&& (!baseCustomsDeclaration.getId().equals(""))) {
			String hql = "";
			switch (projectType) {
			case ProjectType.BCUS:
				hql = "select a from CustomsDeclarationContainer as a ";
				break;
			case ProjectType.BCS:
				hql = "select a from BcsCustomsDeclarationContainer as a ";
				break;
			case ProjectType.DZSC:
				hql = "select a from DzscCustomsDeclarationContainer as a ";
				break;
			default:
				hql = "select a from CustomsDeclarationContainer as a ";
				break;
			}
			hql += "where a.baseCustomsDeclaration.id=?";
			return this.find(hql,
					new Object[] { baseCustomsDeclaration.getId() });
		} else {
			return new ArrayList();
		}
	}
	
	/**
	 * 查询报关行
	 */
	public List findcustomsbrokerList() {
		return this.find( "select a from CustomsBroker a where a.company.id" +
				"order by code ",CommonUtils.getCompany().getId());
	}
	

	/**
	 * 取得集装箱号来自报关单头
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @param projectType
	 *            模块信息
	 * @return 指定模块中的与指定报关单头匹配的报关单中集装箱号
	 */
	public List findContainerByCustomsDeclaration(
			BaseCustomsDeclaration baseCustomsDeclaration, int projectType) {
		if ((baseCustomsDeclaration.getId() != null)
				&& (!baseCustomsDeclaration.getId().equals(""))) {
			String hql = "";
			switch (projectType) {
			case ProjectType.BCUS:
				hql = "select a from CustomsDeclarationContainer as a ";
				break;
			case ProjectType.BCS:
				hql = "select a from BcsCustomsDeclarationContainer as a ";
				break;
			case ProjectType.DZSC:
				hql = "select a from DzscCustomsDeclarationContainer as a ";
				break;
			default:
				hql = "select a from CustomsDeclarationContainer as a ";
				break;
			}
			hql += "where a.baseCustomsDeclaration.id=?";
			return this.find(hql,
					new Object[] { baseCustomsDeclaration.getId() });
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 查询报关单集装箱数据
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 符合指定进出口标志的报关单集装箱数据
	 */
	public List findCustomsDeclarationContainer(int impExpFlag) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclarationContainer as a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationContainer as a ";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclarationContainer as a ";
			break;
		default:
			hql = "select a from CustomsDeclarationContainer as a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id=?"
				+ " and a.baseCustomsDeclaration.impExpFlag=?";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				new Integer(impExpFlag) });
	}

	/**
	 * 保存集装箱信息
	 * 
	 * @param baseCustomsDeclarationContainer
	 *            报关单集装箱信息
	 */
	public void saveCustomsDeclarationContainer(
			BaseCustomsDeclarationContainer baseCustomsDeclarationContainer) {
		this.saveOrUpdate(baseCustomsDeclarationContainer);
	}

	/**
	 * 保存集装箱信息
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @param baseCustomsDeclarationContainerList
	 *            报关单集装箱信息
	 */
	public void saveCustomsDeclarationContainer(
			BaseCustomsDeclaration baseCustomsDeclaration,
			List baseCustomsDeclarationContainerList) {
		for (int i = 0; i < baseCustomsDeclarationContainerList.size(); i++) {
			BaseCustomsDeclarationContainer b = (BaseCustomsDeclarationContainer) baseCustomsDeclarationContainerList
					.get(i);
			b.setBaseCustomsDeclaration(baseCustomsDeclaration);
			this.saveOrUpdate(b);
		}
	}

	/**
	 * 删除集装箱信息
	 * 
	 * @param baseCustomsDeclarationContainer
	 *            报关单集装箱信息
	 */
	public void deleteCustomsDeclarationContainer(
			BaseCustomsDeclarationContainer baseCustomsDeclarationContainer) {
		this.delete(baseCustomsDeclarationContainer);
	}

	/**
	 * 获取出口发票项目来自出口报关单Id
	 * 
	 * @param customsDeclarationId
	 *            出口报关单id
	 * @return 与指定报关单id匹配的报关单中的出口发票信息
	 */
	public List findExportInvoiceItemByCustomsDeclarationId(
			String customsDeclarationId) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from ExportInvoiceItem a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsExportInvoiceItem a ";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscExportInvoiceItem a ";
			break;
		default:
			hql = "select a from ExportInvoiceItem a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=? and a.company.id=?";
		return this.find(hql, new Object[] { customsDeclarationId,
				CommonUtils.getCompany().getId() });
	}

	/**
	 * 取得报关单商品流水号
	 * 
	 * @param customsDeclarationId
	 *            报关单id
	 * @return 与指定报关单id匹配的报关单的报关单流水号
	 */
	public Integer getCustomsDeclarationCommInfoSerialNumber(
			String customsDeclarationId) {
		Integer no = new Integer(1);
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select max(a.serialNumber) from CustomsDeclarationCommInfo as a ";
			break;
		case ProjectType.BCS:
			hql = " select max(a.serialNumber) from BcsCustomsDeclarationCommInfo as a  ";
			break;
		case ProjectType.DZSC:
			hql = " select max(a.serialNumber) from DzscCustomsDeclarationCommInfo as a  ";
			break;
		default:
			hql = " select max(a.serialNumber) from CustomsDeclarationCommInfo as a  ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?";
		List list = this.find(hql, new Object[] { customsDeclarationId });
		if (list.size() > 0) {
			if (list.size() < 1) {
				no = new Integer(1);
			} else if (list.get(0) == null) {
				no = new Integer(1);
			} else {
				String temp = list.get(0).toString();
				if (temp.trim().equals("")) {
					no = new Integer(1);
				} else {
					int n = Integer.parseInt(temp) + 1;
					no = new Integer(n);
				}
			}
		}
		return no;
	}

	/**
	 * 取得物料内容来自报关单商品信息
	 * 
	 * @param info
	 *            报关单商品信息
	 * @return 报关单商品信息中物料内容
	 */
	public List getMaterielByCustoms(BaseCustomsDeclarationCommInfo info) {
		List list = new ArrayList();
		switch (projectType) {
		case ProjectType.BCUS:
			list = this
					.find(
							"select a from CustomsFromMateriel a where a.company.id = ? and a.infoid = ?",
							new Object[] { CommonUtils.getCompany().getId(),
									info.getId() });
			break;
		case ProjectType.BCS:
			list = this
					.find(
							"select a from BcsCustomsFromMateriel a where a.company.id = ? and a.infoid = ?",
							new Object[] { CommonUtils.getCompany().getId(),
									info.getId() });
			break;
		case ProjectType.DZSC:
			// list = this.find("select a from DzscCustomsFromMateriel a where
			// a.company.id = ? and a.infoid = ?",
			// new Object[]{CommonUtils.getCompany().getId(),info.getId()});
			break;
		}
		return list;

	}

	/**
	 * 查看申请单转报关单明细
	 * 
	 * @param info
	 *            报关单明细
	 * @return List申请单转报关单明细
	 */

	public List getMaterielByCustomsMaterielIsNull(
			BaseCustomsDeclarationCommInfo info) {
		List list = new ArrayList();
		switch (projectType) {
		case ProjectType.BCUS:
			list = this
					.find(
							"select a from CustomsFromMateriel a "
									+ " where a.company.id = ? and a.infoid = ? and a.materiel.id is null",
							new Object[] { CommonUtils.getCompany().getId(),
									info.getId() });
			break;
		case ProjectType.BCS:
			list = this
					.find(
							"select a from BcsCustomsFromMateriel a "
									+ " where a.company.id = ? and a.infoid = ? and a.materiel.id is null",
							new Object[] { CommonUtils.getCompany().getId(),
									info.getId() });
			break;
		case ProjectType.DZSC:
			// list = this.find("select a from DzscCustomsFromMateriel a where
			// a.company.id = ? and a.infoid = ?",
			// new Object[]{CommonUtils.getCompany().getId(),info.getId()});
			break;
		}
		return list;

	}

	/**
	 * 取得中间信息来自报关单商品信息
	 * 
	 * @param info
	 *            报关单商品信息
	 * @return 中间信息
	 */
	public List getMakeListToCustoms(BaseCustomsDeclarationCommInfo info) {
		return this
				.find(
						"select a from MakeListToCustoms a where a.company.id=? and a.customsInfo.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								info.getId() });
	}

	/**
	 * 删除出口发票项目
	 * 
	 * @param baseExportInvoiceItem
	 *            出口发票项目
	 */
	public void deleteExportInvoiceItem(
			BaseExportInvoiceItem baseExportInvoiceItem) {
		this.delete(baseExportInvoiceItem);
	}

	/**
	 * 保存或修改出口发票项目
	 * 
	 * @param baseExportInvoiceItem
	 *            出口发票项目
	 */
	public void saveExportInvoiceItem(
			BaseExportInvoiceItem baseExportInvoiceItem) {
		this.saveOrUpdate(baseExportInvoiceItem);
	}

	/**
	 * 检查是否存在批准文号
	 * 
	 * @param projectType
	 *            模块类型
	 * @param authorizeFile
	 *            批准文号
	 * @param id
	 *            报关单id
	 * @return 指定报关单id匹配的报关单中的批准文号
	 */
	public List isExistAuthorizeFile(int projectType, String authorizeFile,
			String id) {
		String hql = "";

		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a ";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a  ";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a ";
			break;
		}
		if (id == null || "".equals(id)) {
			hql += " where a.company.id = ? and a.authorizeFile = ? ";
			return this.find(hql, new Object[] {
					CommonUtils.getCompany().getId(), authorizeFile.trim() });
		} else {
			hql += " where a.company.id = ? and a.authorizeFile = ? and a.id != ? ";
			return this.find(hql,
					new Object[] { CommonUtils.getCompany().getId(),
							authorizeFile.trim(), id });
		}

	}

	/**
	 * 查询报关清单来自清单编号
	 * 
	 * @param listno
	 *            清单号码
	 * @return 与指定的清单号码匹配的报关清单
	 */
	public List findBillForbillNo(String listno) {
		return this
				.find(
						"select a from ApplyToCustomsBillList a where a.company.id=? and a.listNo = ?",
						new Object[] { CommonUtils.getCompany().getId(), listno });

	}

	/**
	 * 保存报关清单
	 * 
	 * @param obj
	 *            报关清单
	 */
	public void saveApplyToCustomsBillList(ApplyToCustomsBillList obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 返回系统参数设置中的其他设置
	 * 
	 * @return 系统参数设置中的其他设置
	 */
	public CompanyOther findCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (CompanyOther) list.get(0);
		}
		return null;
	}

	/**
	 * 根据选项查找系统参数设置资料
	 * 
	 * @param type
	 *            项查
	 * @return List 是ParameterSet型，系统参数设置资料
	 */
	public List findnameValues(int type) {
		return this
				.find(
						"select a from ParameterSet a left join fetch a.company where a.company= ? and a.type=?",
						new Object[] { CommonUtils.getCompany(), type });
	}

	/**
	 * 取得进出口申请单的份数
	 * 
	 * @param head
	 *            进出口申请单
	 * @return 结转单据的份数
	 */
	public Integer getInfoForToCustom(ImpExpRequestBill head) {
		List list = this
				.find(
						"select count(a) from ImpExpCommodityInfo a where "
								+ "a.impExpRequestBill.id = ? and a.isTransferCustomsBill = ? ",
						new Object[] { head.getId(), new Boolean(true) });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}
	
	public ImpExpRequestBill findImpExpRequestBillById(String id){
		List list = this.find("select a from ImpExpRequestBill a where a.id = ?",new Object[] {id});
		if(list.size()>0){
			return (ImpExpRequestBill)list.get(0);
		}
		return null;
	}

	/**
	 * 保存进出口申请单数据
	 * 
	 * @param impExpRequestBill
	 *            进出口申请单据
	 */
	public void saveImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.saveOrUpdate(impExpRequestBill);
	}

	/**
	 * 删除进出口商品信息数据
	 * 
	 * @param imp
	 *            进出口商品信息
	 */
	public void deleteImpExpCommodityInfo(ImpExpCommodityInfo imp) {
		this.delete(imp);
	}

	/**
	 * 保存进出口商品信息数据
	 * 
	 * @param list
	 *            进出口商品信息
	 */
	public void saveImpExpCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			ImpExpCommodityInfo data = (ImpExpCommodityInfo) list.get(i);
			this.saveOrUpdate(data);
		}
	}

	/**
	 * 获得当前进出口的商品信息的个数
	 * 
	 * @param parentId
	 *            进出口申请单id
	 * @return 当前进出口商品信息的个数
	 */
	public int findImpExpCommodityInfoCount(String parentId) {
		List list = this.find("select count(*) from ImpExpCommodityInfo b  "
				+ " where b.impExpRequestBill.id = ? ",
				new Object[] { parentId });
		if (list == null || list.size() <= 0) {
			return 0;
		}
		return Integer.parseInt(list.get(0).toString());
	}

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param parentList
	 *            临时的进出口申请单
	 * @return 非结转进出口商品信息
	 */
	public List findImpExpCommodityInfoByParent(List parentList) {
		String hsql = "select b from ImpExpCommodityInfo b left outer join fetch b.materiel "
				+ "where ( b.isTransferCustomsBill = ? or b.isTransferCustomsBill is null ) ";
		String condition = "";
		List objs = new ArrayList();
		objs.add(new Boolean(false));
		for (int i = 0; i < parentList.size(); i++) {
			if (parentList.get(i) instanceof TempImpExpRequestBill) {
				TempImpExpRequestBill t = (TempImpExpRequestBill) parentList
						.get(i);
				if (t.getIsSelected() == null || t.getIsSelected() == false) {
					continue;
				}
				if (i == 0) {
					condition += "  b.impExpRequestBill.id = ? ";
				} else {
					condition += "  or  b.impExpRequestBill.id = ? ";
				}
				objs.add(t.getImpExpRequestBill().getId());
			}
		}
		if (condition.equals("") == false) {
			hsql += " and (" + condition + " )";
		}
		return this.find(hsql, objs.toArray());
	}

	/**
	 * 获得进出口商品信息来于明细对象
	 * 
	 * @param parentList
	 *            临时的进出口申请单
	 * @return 非结转进出口商品信息
	 */
	public List findImpExpCommodityInfoByCommInfo(List parentList) {
		String hsql = "select b from ImpExpCommodityInfo b left outer join fetch b.materiel "
				+ "where ( b.isTransferCustomsBill = ? or b.isTransferCustomsBill is null ) ";
		String condition = "";
		List objs = new ArrayList();
		objs.add(new Boolean(false));
		for (int i = 0; i < parentList.size(); i++) {
			if (parentList.get(i) instanceof TempBcsImpExpCommodityInfo) {
				TempBcsImpExpCommodityInfo t = (TempBcsImpExpCommodityInfo) parentList
						.get(i);
				if (i == 0) {
					condition += "  b.id = ? ";
				} else {
					condition += "  or  b.id = ? ";
				}
				objs.add(t.getImpExpCommodityInfo().getId());
			}
		}
		if (condition.equals("") == false) {
			hsql += " and (" + condition + " )";
		}
		// System.out.println("===parentList="+parentList.size());
		// System.out.println("===hsql="+hsql);
		return this.find(hsql, objs.toArray());
	}

	/**
	 * 取得报关单表头信息统计件数，净，毛重
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头信息
	 * @return List指定报关单的报关商品信息的统计情况
	 */
	public List getInfoSum(BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select sum(a.pieces),sum(a.grossWeight),sum(a.netWeight) from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select sum(a.pieces),sum(a.grossWeight),sum(a.netWeight) from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select sum(a.pieces),sum(a.grossWeight),sum(a.netWeight) from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select sum(a.pieces),sum(a.grossWeight),sum(a.netWeight) from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?";
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	}

	public List getAttachedBill(BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select distinct b.complex.ccontrol  from CustomsDeclarationCommInfo a , CheckupComplex b where a.complex.code=b.complex.code  ";
			break;
		case ProjectType.BCS:
			hql = " select distinct b.complex.ccontrol  from  BcsCustomsDeclarationCommInfo a , CheckupComplex b where a.complex.code=b.complex.code  ";
			break;
		case ProjectType.DZSC:
			hql = " select  distinct b.complex.ccontrol  from DzscCustomsDeclarationCommInfo a , CheckupComplex b where a.complex.code=b.complex.code   ";
			break;
		default:
			hql = " select distinct b.complex.ccontrol from CustomsDeclarationCommInfo a , CheckupComplex b where a.complex.code=b.complex.code   ";
			break;
		}
		hql += " and a.baseCustomsDeclaration.id=?";
		// System.out.println("========hsql=="+hql);
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	}

	// /**
	// * 取得报关单表头的随附单据
	// *
	// * @param baseCustomsDeclaration
	// * 报关单表头信息
	// * @return List 报关单表头的随附单据
	// */
	// public List getAttachedBill(BaseCustomsDeclaration
	// baseCustomsDeclaration) {
	// String hql = "";
	// switch (projectType) {
	// case ProjectType.BCUS:
	// hql =
	// " select distinct c.ccontrol from CustomsDeclarationCommInfo a , Complex b,CustomsComplex c  where a.complex.code=b.code and b.code=c.code ";
	// break;
	// case ProjectType.BCS:
	// hql =
	// " select distinct c.ccontrol from  BcsCustomsDeclarationCommInfo a , Complex b,CustomsComplex c where a.complex.code=b.code and b.code=c.code ";
	// break;
	// case ProjectType.DZSC:
	// hql =
	// " select  distinct c.ccontrol from DzscCustomsDeclarationCommInfo a , Complex b,CustomsComplex c where a.complex.code=b.code and b.code=c.code  ";
	// break;
	// default:
	// hql =
	// " select distinct c.ccontrol from CustomsDeclarationCommInfo a , Complex b,CustomsComplex c  where a.complex.code=b.code and b.code=c.code  ";
	// break;
	// }
	// hql += " and a.baseCustomsDeclaration.id=?";
	// return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	// }

	/**
	 * 显示归并关系物料主档对象
	 * 
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            第一个索引
	 * @param ptNo
	 *            料号
	 * @return 归并关系物料主档对象
	 */
	public List findMaterielFromInner(String type, int firstIndex, String ptNo) {
		List<Object> paramters = new ArrayList<Object>();
		String sql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			sql = "select a.materiel from InnerMergeData a "
					+ " where a.imrType = ? and a.company.id = ? ";
			break;
		case ProjectType.BCS:
			sql = "select a.materiel from BcsInnerMerge a "
					+ " where a.materielType = ? and a.company.id = ? ";
			break;
		case ProjectType.DZSC:
			sql = "select a.materiel from DzscInnerMergeData a "
					+ " where a.imrType = ? and a.company.id = ? ";
			break;
		}
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		if (ptNo != null) {
			sql += " and a.materiel.ptNo like '%" + ptNo + "%'";
		}
		return this.findPageList(sql, paramters.toArray(), firstIndex, 100);
	}

	/**
	 * 显示归并关系物料主档对象
	 * 
	 * @param type
	 *            物料类型
	 * @param firstIndex
	 *            第一个索引
	 * @param obj
	 *            查询的值
	 *@param sFields
	 *            查询的属性
	 * @return 归并关系物料主档对象
	 */
	public List findMaterielFromInner(String type, int firstIndex, Object obj,
			String sFields) {
		List<Object> paramters = new ArrayList<Object>();
		String sql = "";
		sql = "select a.materiel from InnerMergeData a "
				+ " where a.imrType = ? and a.company.id = ?  and (a.isForbid is null or a.isForbid=?)  ";
		paramters.add(type);
		paramters.add(CommonUtils.getCompany().getId());
		paramters.add(Boolean.valueOf(false));
		if (obj != null) {
			if (sFields.equals("ptNo")) {
				sql += " and a.materiel.ptNo= ?";
				paramters.add(obj.toString());
			}
			if (sFields.equals("factoryName")) {
				sql += " and a.materiel.factoryName = ?";
				paramters.add(obj.toString());
			}
			if (sFields.equals("factorySpec")) {
				sql += " and a.materiel.factorySpec = ?";
				paramters.add(obj.toString());
			}
			if (sFields.equals("complex.code")) {
				sql += " and a.hsAfterComplex.code = ?";
				paramters.add(obj.toString());
			}
			if (sFields.equals("seqNum")) {
				sql += " and a.hsAfterTenMemoNo = ?";
				paramters.add(Integer.parseInt(obj.toString()));
			}
		}
		System.out.println("sql--------" + sql);
		return this.findPageList(sql, paramters.toArray(), firstIndex, 100);
	}

	/**
	 * 找到归并关系十码序号
	 * 
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return 与指定的料号匹配的商品信息在归并关系中的十码序号
	 */
	public Integer findSeqNumByPtNo(String ptNo, String type) {
		List list = null;
		switch (projectType) {
		case ProjectType.BCUS:
			list = this
					.find(
							"select a.hsAfterTenMemoNo from InnerMergeData a "
									+ " where a.imrType = ? and a.company.id = ? and a.materiel.ptNo = ?",
							new Object[] { type,
									CommonUtils.getCompany().getId(), ptNo });
			break;
		case ProjectType.BCS:
			list = this
					.find(
							"select a.bcsTenInnerMerge.seqNum from BcsInnerMerge a "
									+ " where a.materielType = ? and a.company.id = ? and a.materiel.ptNo = ?",
							new Object[] { type,
									CommonUtils.getCompany().getId(), ptNo });
			break;
		case ProjectType.DZSC:
			list = this
					.find(
							"select a.tenComplex.seqNum from DzscInnerMergeData a "
									+ " where a.imrType = ? and a.company.id = ? and a.materiel.ptNo = ? ",
							new Object[] { type,
									CommonUtils.getCompany().getId(), ptNo });
			break;
		}
		if (list != null && list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return null;
	}

	/**
	 * 找到归并关系十码序号
	 * 
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return 与指定的料号匹配的商品信息在归并关系中的十码序号
	 */
	public List findAllSeqNumByPtNo(String ptNo, String type) {
		List list = null;
		switch (projectType) {
		case ProjectType.BCUS:
			list = this
					.find(
							"select a.hsAfterTenMemoNo from InnerMergeData a "
									+ " where a.imrType = ? and a.company.id = ? and a.materiel.ptNo = ?",
							new Object[] { type,
									CommonUtils.getCompany().getId(), ptNo });
			break;
		case ProjectType.BCS:
			list = this
					.find(
							"select a.bcsTenInnerMerge.seqNum from BcsInnerMerge a "
									+ " where a.materielType = ? and a.company.id = ? and a.materiel.ptNo = ?",
							new Object[] { type,
									CommonUtils.getCompany().getId(), ptNo });
			break;
		case ProjectType.DZSC:
			list = this
					.find(
							"select a.tenComplex.seqNum from DzscInnerMergeData a "
									+ " where a.imrType = ? and a.company.id = ? and a.materiel.ptNo = ? ",
							new Object[] { type,
									CommonUtils.getCompany().getId(), ptNo });
			break;
		}
		return list;
	}

	/**
	 * 根据参数设置电子账册是否分批发送
	 * 
	 * @return 电子账册是否分批发送
	 */
	private boolean getIsEmsH2kSend() {
		List list = this
				.find(
						"select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsEdiH2kSend,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通过帐册序号返回帐册信息
	 * 
	 * @param seqNum
	 *            序号
	 * @param type
	 *            物料类型
	 * @param emsNo
	 *            手册号
	 * @return 与指定的序号手册号匹配的帐册信息
	 */
	public List findImgExgBillBySeqNum(Integer seqNum, String type, String emsNo) {
		String obj = "";
		List list = null;
		switch (projectType) {
		case ProjectType.BCUS:
			if (type.equals(MaterielType.MATERIEL)) {
				obj = "EmsHeadH2kImg";
			} else {
				obj = "EmsHeadH2kExg";
			}

			if (getIsEmsH2kSend()) {
				String sql = "from "
						+ obj
						+ " a where a.emsHeadH2k.company.id=? and a.seqNum = ? "
						+ " and a.sendState = ? and a.emsHeadH2k.historyState=?";
				// System.out.println("=sql1="+sql+"=seqNum="+seqNum+"=id="+CommonUtils.getCompany().getId()+" ="+new
				// Boolean(false));
				list = this.find(sql, new Object[] {
						CommonUtils.getCompany().getId(), seqNum,
						Integer.valueOf(SendState.SEND), new Boolean(false) });
			} else {
				String sql = "from "
						+ obj
						+ " a where a.emsHeadH2k.company.id=? and a.seqNum = ? "
						+ " and a.emsHeadH2k.declareState = ? and a.emsHeadH2k.historyState=?";
				// System.out.println("=sql2="+sql +"=seqNum="+seqNum);
				list = this.find(sql, new Object[] {
						CommonUtils.getCompany().getId(), seqNum,
						DeclareState.PROCESS_EXE, new Boolean(false) });
			}

			break;

		case ProjectType.BCS:
			if (type.equals(MaterielType.MATERIEL)) {
				obj = "ContractImg";
			} else {
				obj = "ContractExg";
			}
			list = this
					.find(
							"select a from "
									+ obj
									+ " a where a.contract.emsNo=? "
									+ " and a.contract.company.id=? and a.credenceNo = ? and a.contract.declareState=? ",
							new Object[] { emsNo,
									CommonUtils.getCompany().getId(), seqNum,
									DeclareState.PROCESS_EXE });
			break;
		case ProjectType.DZSC:
			if (type.equals(MaterielType.MATERIEL)) {
				obj = "DzscEmsImgBill";
			} else {
				obj = "DzscEmsExgBill";
			}
			list = this.find("select a from " + obj
					+ " a where a.dzscEmsPorHead.emsNo=? "
					+ " and a.dzscEmsPorHead.company.id=? and a.seqNum = ? "
					+ " and a.dzscEmsPorHead.declareState = ?", new Object[] {
					emsNo, CommonUtils.getCompany().getId(), seqNum,
					DzscState.EXECUTE });
			break;
		}
		return list;
	}

	/**
	 * 查询电子账册历版本信息
	 * 
	 * @param exg
	 *            电子账册成品
	 * @return List 电子账册历版本信息
	 */
	public List findEmsHeadH2kVersionByExgSeqNum(EmsHeadH2kExg exg) {
		if (getIsEmsH2kSend()) {
			return this
					.find(
							"select distinct a.emsHeadH2kVersion from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.id= ? " +
							" and a.company.id = ? and a.sendState =? and a.emsHeadH2kVersion.emsHeadH2kExg.sendState =?",
							new Object[] { exg.getId(),
									CommonUtils.getCompany().getId(),
									Integer.valueOf(SendState.SEND),
									Integer.valueOf(SendState.SEND) });
		} else {
			return this
					.find(
							"select distinct a.emsHeadH2kVersion from EmsHeadH2kBom a where a.emsHeadH2kVersion.emsHeadH2kExg.id= ? " +
							" and a.company.id = ? and a.sendState =?",
							new Object[] { exg.getId(),
									CommonUtils.getCompany().getId(),
							Integer.valueOf(SendState.SEND)});
		}

	}

	/**
	 * 报关清单来自报关商品信息
	 * 
	 * @param customs
	 *            报关商品信息
	 * @return 报关清单内容
	 */
	public List findAppFromMaterielByCustomsInfo(
			BaseCustomsDeclarationCommInfo customs) {
		return this
				.find(
						"select a.impExpCommodityInfo from MakeApplyToCustoms a "
								+ " where a.company.id = ? and a.atcMergeBeforeComInfo.afterComInfo.id in "
								+ "(select c.atcMergeAfterComInfo.id from MakeListToCustoms c where c.customsInfo.id = ?)",
						new Object[] { CommonUtils.getCompany().getId(),
								customs.getId() });
	}

	/**
	 * 返回进出口类型为直接出口的申请单商品信息
	 * 
	 * @param info
	 *            进出口申请单表体资料
	 */
	public void DeleteMakeApplyToCustomsByImpExp(ImpExpCommodityInfo info) {
		MakeApplyToCustoms obj = null;
		List list = this
				.find(
						"select a from MakeApplyToCustoms a where a.company.id = ? and a.impExpCommodityInfo.id = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								info.getId() });
		if (list != null && list.size() > 0) {
			obj = (MakeApplyToCustoms) list.get(0);
			this.delete(obj);
		}

	}

	/**
	 * 保存报关清单归并后商品信息
	 * 
	 * @param atcMergeAfterComInfo
	 *            归并后的商品信息
	 */
	public void saveAtcMergeAfterComInfo(
			AtcMergeAfterComInfo atcMergeAfterComInfo) {
		this.saveOrUpdate(atcMergeAfterComInfo);
	}

	/**
	 * 取得进口报关单
	 * 
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 进口报关单
	 */
	public List findImportCustomsDeclaration(String condition,
			List<Object> parameters) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? ";
		parameters.add(0, Integer.valueOf(ImpExpFlag.IMPORT));
		parameters.add(1, CommonUtils.getCompany().getId());
		hql += condition;
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param condition
	 *            查询条件
	 * @param parameters
	 *            参数
	 * @return 出口报关单
	 */
	public List findExportCustomsDeclaration(String condition,
			List<Object> parameters) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? ";
		parameters.add(0, Integer.valueOf(ImpExpFlag.EXPORT));
		parameters.add(1, CommonUtils.getCompany().getId());
		hql += condition;
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 得到申请单号通过清单号码
	 * 
	 * @param billNo
	 *            单据号
	 * @return 进出口申请单单据号
	 */
	public String getImpExpNoByBillNo(String billNo) {
		if (billNo == null || "".equals(billNo.trim())) {
			return null;
		}
		List list = this
				.find(
						"select a.billNo from ImpExpRequestBill a where a.allBillNo = ? and a.company.id = ?",
						new Object[] { billNo, CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return String.valueOf(list.get(0));
		}
		return null;
	}
	/**
	 * 得到多个申请单号通过报关清单号码
	 * 
	 * @param request
	 *            请求控制
	 * @param billNo
	 *            报关清单号码
	 * @return 进出口申请单单据号
	 */
	public List getImpExpMultiNoByBillNo(String billNo){
		if (billNo == null || "".equals(billNo.trim())) {
			return null;
		}
		List list = this
				.find(
						"select a.billNo from ImpExpRequestBill a where a.allBillNo = ? and a.company.id = ?",
						new Object[] { billNo, CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return list;
		}
		return null;
	}
	/**
	 * 取得报关单商品信息来自序号
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param baseCustomsDeclaration
	 *            报关单头
	 * @param version
	 *            版本号
	 * @param contry
	 *            原产国 or 最终目的产国
	 * @return 与指定的报关单商品序号匹配的报关单商品信息
	 */
	public BaseCustomsDeclarationCommInfo findBaseCustomsDeclarationCommInfoBySequm(
			Integer seqNum, BaseCustomsDeclaration baseCustomsDeclaration,
			String version, Country contry) {
		List<Object> paramters = new ArrayList<Object>();
		String hsql = "select a from CustomsDeclarationCommInfo a where a.commSerialNo = ? "
				+ "and a.baseCustomsDeclaration.id = ? ";
		paramters.add(seqNum);
		paramters.add(baseCustomsDeclaration.getId());
		if (version != null && !"".equals(version)) {
			hsql += " and a.version = ? ";
			paramters.add(version);
		}
		if (contry != null) {
			hsql += " and a.country = ? ";
			paramters.add(contry);
		}
		List list = this.find(hsql, paramters.toArray());
		if (list != null && list.size() > 0) {
			return (BaseCustomsDeclarationCommInfo) list.get(0);
		}
		return null;
	}

	/**
	 * 根据报关单查询由转厂管理的结转单据生成报关单的过程中产生的中间表
	 * 
	 * @param commInfo
	 *            报关单表体
	 * @return 由结转单据生成报关单的中间表数
	 */
	public int findTransFactMakeCustomsDeclarationCount(
			BaseCustomsDeclarationCommInfo commInfo) {
		List list = this
				.find(
						"select count(a) from MakeFptBillCustomsDeclaration a where a.customsDeclarationCommInId = ? ",
						new Object[] { commInfo.getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 根据报关单删除由转厂管理的结转单据生成报关单的过程中产生的中间表
	 * 
	 * @param commInfo
	 *            报关单表体
	 * @return 生成报关单过程的中间表
	 */
	public List findTransFactMakeCustomsDeclaration(
			BaseCustomsDeclarationCommInfo commInfo) {
		return this
				.find(
						"select a from MakeFptBillCustomsDeclaration a where a.customsDeclarationCommInId = ? ",
						new Object[] { commInfo.getId() });
	}
	
//	public List getFptBillItemIdByMakeCustomsDeclarationAppNo(MakeFptBillCustomsDeclaration make){
//		return this.find("select  a from FptBillItem a where a.fptBillHead.appNo=? and a.trGno=?",
//				new Object[]{make.getFptBillHeadAppNo(),make.getFptBillItemTrGno()});
//	}

    public List findFptBillItemByCustomsDeclaration(FptBillHead fptBillHead, BaseCustomsDeclaration customsDeclaration) {
    	String commInfoTableNames="CustomsDeclarationCommInfo";
    	switch (projectType) {
		case ProjectType.BCUS:
			commInfoTableNames = "CustomsDeclarationCommInfo";
			break;
		case ProjectType.BCS:
			commInfoTableNames = "BcsCustomsDeclarationCommInfo";
			break;
		case ProjectType.DZSC:
			commInfoTableNames = "DzscCustomsDeclarationCommInfo";
			break;
		default:
			commInfoTableNames = "CustomsDeclarationCommInfo";
			break;
		}
        return this
                .find(
                "select distinct a from FptBillItem a,MakeFptBillCustomsDeclaration b,"+commInfoTableNames+" c "
                + " where a.id=b.fptBillItemId and b.customsDeclarationCommInId=c.id "
                + " and a.fptBillHead.id = ? and c.baseCustomsDeclaration.id=? ",
                new Object[]{fptBillHead.getId(), customsDeclaration.getId()});
    }
	/**
	 * 删除报关单时，如果此报关单是由转厂单据转过来的话，反写转厂单据的转报关单标志位
	 * 
	 * @param BillId
	 *            转厂单据id
	 */
	public void updateTransferFactoryBillCommInfo(String billItemId) {
		this
				.batchUpdateOrDelete(
						" update FptBillItem a set a.isCustomsDeclaration=? where a.id=? ",
						new Object[] { false, billItemId });
	}

	/**
	 * 返回结转单据商品信息
	 * 
	 * @param emsNO
	 *            转出企业手册/账册号
	 * @param impExpFlag
	 *            进出口标志
	 * @return List 结转单据商品信息
	 */
	public List findTransferFactoryBillId(String emsNO, String impExpFlag) {
		if (impExpFlag.equals(ImpExpFlag.EXPORT)) {
			return this
					.find(
							"select a from FptBillItem a where  a.fptBillHead.outEmsNo = ? ",
							new Object[] { emsNO });
		} else if (impExpFlag.equals(ImpExpFlag.IMPORT)) {
			return this.find(
					"select a from FptBillItem a where  a.inEmsNo = ? ",
					new Object[] { emsNO });
		}
		return null;
	}

//	/**
//	 * 返回转厂单据信息
//	 * 
//	 * @param billid
//	 *            转厂单据id
//	 * @return List 转厂单据信息
//	 */
//	public List findTransferFactoryBillId(MakeFptBillCustomsDeclaration mfp) {
//		return this.find("select a from FptBillItem a where  a.fptBillHead.appNo= ?  and a.trGno=? ",
//				new Object[] { mfp.getFptBillHeadAppNo(),mfp.getFptBillItemTrGno() });
//	}

	/**
	 * 更新转厂进出货单信息
	 * 
	 * @param emsNoCode
	 *            生成的报关单号码
	 * @param BillId
	 *            转厂单据id
	 */
	public void updateTransferFactoryBill(String emsNoCode, String BillId) {
		this
				.batchUpdateOrDelete(
						"update FptBillHead set makeCustomsDeclarationCode=? where id = ? ",
						new Object[] { emsNoCode, BillId });
	}

	/**
	 * 取得关封申请单的关封号
	 * 
	 * @param appNo
	 * 
	 *            申请表编号
	 * @return FptAppHead 关封申请单的关封号
	 */
	public FptAppHead findFptAppHeadByAppNo(String appNo) {
		String hsql = "select a from FptAppHead a "
				+ " where a.appNo = ? and a.declareState=? "
				+ " and a.company.id = ? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(appNo);
		paramters.add(DeclareState.PROCESS_EXE);
		paramters.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, paramters.toArray());
		if (list.size() > 0) {
			return (FptAppHead) list.get(0);
		}
		return null;
	}

	/**
	 * 查找关封单据管理中的关封号
	 * 
	 * @param envelopBillNo
	 *            关封号
	 * @return CustomsEnvelopBill 关封单据管理中的关封号
	 */
	public CustomsEnvelopBill findCustomsEnvelopBillByCustomsDeclarationNo(
			String envelopBillNo) {
		String hsql = "select a from CustomsEnvelopBill a "
				+ " where a.customsEnvelopBillNo = ?"
				+ " and a.company.id = ? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(envelopBillNo);
		paramters.add(CommonUtils.getCompany().getId());
		List list = this.find(hsql, paramters.toArray());
		if (list.size() > 0) {
			return (CustomsEnvelopBill) list.get(0);
		}
		return null;
	}

	/**
	 * 海关注册公司
	 * 
	 * @param sFields
	 *            字段
	 * @param sValue
	 *            字段值
	 * @return List 海关注册公司
	 */
	public List getBrief(String sFields, String sValue) {
		return findCustoms("Brief", sFields, sValue);
	}

	/**
	 * 返回商品单价信息
	 * 
	 * @param projectType
	 *            项目类型
	 * @param scmCoc
	 *            客户供应商资料
	 * @return List 商品单价信息
	 */
	public List findBillPriceMaintenance(int projectType, ScmCoc scmCoc) {
		return this.find("select  a from  BillPriceMaintenance a  "
				+ "where a.company.id =?  and a.projectType=? and a.scmCoc=?",
				new Object[] { CommonUtils.getCompany().getId(), projectType,
						scmCoc });
	}

	/**
	 * 返回商品单价维护信息
	 * 
	 * @param projectType
	 *            项目类型
	 * @param scmCoc
	 *            客户供应商资料
	 * @return List 商品单价维护信息
	 */
	public List findFptBillPriceMaintenance(int projectType, ScmCoc scmCoc) {
		return this.find("select  a from  FptBillPriceMaintenance a  "
				+ "where a.company.id =?  and a.projectType=? and a.scmCoc=?",
				new Object[] { CommonUtils.getCompany().getId(), projectType,
						scmCoc });
	}

	/**
	 * 返回其它参数设置资料信息
	 * 
	 * @param type
	 *            进出口类型
	 * @return CustomsDeclarationSet 其它参数设置资料
	 */
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

	/**
	 * 保存报关清单归并前商品信息
	 * 
	 * @param info
	 *            报关清单归并前商品信息
	 */
	public void saveAtcMergeBeforeComInfo(AtcMergeBeforeComInfo info) {
		this.saveOrUpdate(info);
	}

	/**
	 * 取得报关单金额
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	public List findCustomsDeclarationMoney(Integer impExpFlag) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a.baseCustomsDeclaration.id,sum(a.commTotalPrice) from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.company.id =? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " group by a.baseCustomsDeclaration.id";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				impExpFlag });
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List findLoadBGDFromQPXml(Integer impExpFlag) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from LoadBGDFromQPXml as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscLoadBGDFromQPXml as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsLoadBGDFromQPXml as a";
			break;
		default:
			hql = " select a from LoadBGDFromQPXml as a";
			break;
		}
		hql += "  where a.company.id=? and a.impExpFlag=? "
				+ " order by a.date desc ";
		return this.findPageList(hql, new Object[] { CommonUtils.getCompany().getId(),
				impExpFlag },0,1);
	}

	/**
	 * 根据报关单号取得所有的从QP倒出的报关单
	 * 
	 * @param customsDeclarationCode
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public List findAllLoadBGDFromQPXml(String customsDeclarationCode) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from LoadBGDFromQPXml as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscLoadBGDFromQPXml as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsLoadBGDFromQPXml as a";
			break;
		default:
			hql = " select a from LoadBGDFromQPXml as a";
			break;
		}
		hql += "where a.company.id=?" + "where a.customsDeclarationCode=?";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				customsDeclarationCode });
	}

	/**
	 * 报关单通过币制获取汇率
	 * 
	 * @param curr
	 *            币制
	 * @param date
	 *            日期
	 * @return Double 汇率
	 */
	public Double getCurrRateByCurr(Curr curr, Date date) {
		if (curr == null) {
			return Double.valueOf(0.0);
		}
		List ls = this
				.find(
						"select a from CurrRate a where a.company.id = ? and a.curr.code = ? and a.curr1.code = ?",
						new Object[] { CommonUtils.getCompany().getId(), "502",
								curr.getCode() });
		if (ls != null && ls.size() > 0) {
			return ((CurrRate) ls.get(0)).getRate();
		}
		return Double.valueOf(0.0);
	}

	/**
	 * 根据料号，查找归并关系归并前料件表的归并序号
	 * 
	 * @param ptNo
	 *            料号
	 * @return List 归并关系归并前料件表
	 */
	public List findEmsEdiMergerImgBeforeByGNo(String ptNo) {
		EmsEdiMergerHead head = findEmsEdiMergerHeadByDeclareState();
		return this
				.find(
						"select  distinct a.seqNum from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
								+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=?",
						new Object[] { ptNo, CommonUtils.getCompany().getId(),
								head.getId() });
	}

	/**
	 * 根据料号，物料类型查询归并关系归并前料件表OR归并关系归并前成品表相关信息
	 * 
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类型
	 * @return List 归并关系归并前料件表OR归并关系归并前成品表相关信息
	 */
	public List findEmsEdiMergerImgBeforeByGNo(String ptNo, String type) {
		EmsEdiMergerHead head = findEmsEdiMergerHeadByDeclareState();
		System.out.println("head.getId()====" + head.getId());
		if (type.equals(MaterielType.MATERIEL)) {
			if (getIsMergerSend()) {
				return this
						.find(
								"select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
										+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=? and a.sendState = ?",
								new Object[] { ptNo,
										CommonUtils.getCompany().getId(),
										head.getId(),
										Integer.valueOf(SendState.SEND) });
			} else {
				return this
						.find(
								"select a from EmsEdiMergerImgBefore a where a.ptNo=? and a.company.id= ?"
										+ " and a.emsEdiMergerImgAfter.emsEdiMergerHead.id=?",
								new Object[] { ptNo,
										CommonUtils.getCompany().getId(),
										head.getId() });
			}
		} else {
			if (getIsMergerSend()) {
				return this
						.find(
								"select a from EmsEdiMergerExgBefore a where a.ptNo=? and a.company.id= ?"
										+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=? and a.sendState = ?",
								new Object[] { ptNo,
										CommonUtils.getCompany().getId(),
										head.getId(),
										Integer.valueOf(SendState.SEND) });
			} else {
				return this
						.find(
								"select a from EmsEdiMergerExgBefore a where a.ptNo=? and a.company.id= ?"
										+ " and a.emsEdiMergerExgAfter.emsEdiMergerHead.id=?",
								new Object[] { ptNo,
										CommonUtils.getCompany().getId(),
										head.getId() });
			}
		}
	}

	/**
	 * 返回电子账册归并表头信息
	 * 
	 * @return EmsEdiMergerHead 电子账册归并表头信息
	 */
	public EmsEdiMergerHead findEmsEdiMergerHeadByDeclareState() {
		if (getIsMergerSend()) {// 分批发送
			List list = this.find(
					"select a from EmsEdiMergerHead a where a.company.id= ? "
							+ "and a.historyState=?  ", new Object[] {
							CommonUtils.getCompany().getId(),
							new Boolean(false) });
			if (list == null || list.size() <= 0) {
				return null;
			}
			return (EmsEdiMergerHead) list.get(0);
		} else {
			List list = this
					.find(
							"select a from EmsEdiMergerHead a where a.company.id= ? "
									+ "and a.historyState=? "
									+ "and a.declareState=? ", new Object[] {
									CommonUtils.getCompany().getId(),
									new Boolean(false),
									DeclareState.PROCESS_EXE });
			if (list == null || list.size() <= 0) {
				return null;
			}
			return (EmsEdiMergerHead) list.get(0);
		}
	}

	/**
	 * 查询转厂管理参数设定
	 * 
	 * @return FptParameterSet 转厂管理参数设定
	 */
	public FptParameterSet findTransParameterSet() {
		List list = this.find(
				"select a from FptParameterSet a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0) {
			return (FptParameterSet) list.get(0);
		}
		return null;
	}

	/**
	 * 查询BCUS参数
	 * 
	 * @return boolean 电子账册是否分批发送
	 */
	private boolean getIsMergerSend() {
		List list = this
				.find(
						"select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { BcusParameter.EmsSEND,
								CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			if (obj.getStrValue() != null && "1".equals(obj.getStrValue())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据报关单资料查找设备表体数量
	 * 
	 * @param baseCustomsDeclarationCommInfo
	 *            内部商品新增报关单表体
	 * @return Double 设备表体数量
	 */
	public Double findFixItemAmount(
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		List list;
		if (baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration()
				.getFixType() == 2) {
			list = find(
					"select a.amount from FixtureContractItems a where "
							+ " a.company.id=? and a.contract.emsNo= ? and a.contract.declareState=? "
							+ " and a.seqNum=? )", new Object[] {
							CommonUtils.getCompany().getId(),
							baseCustomsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getEmsHeadH2k(), DeclareState.PROCESS_EXE,
							baseCustomsDeclarationCommInfo.getCommSerialNo() });
		} else {
			list = find("select a.amount from DutyCertDetail a where "
					+ " a.company.id=? and a.certHead.certNo= ? "
					+ " and a.mainNo=? )", new Object[] {
					CommonUtils.getCompany().getId(),
					baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration()
							.getEmsHeadH2k(),
					baseCustomsDeclarationCommInfo.getCommSerialNo() });
		}
		if (list.size() < 1)
			return 0.0;
		return (Double) list.get(0);

	}

	/**
	 * 根据报关单资料查找报关单数据
	 * 
	 * @param baseCustomsDeclarationCommInfo
	 *            报关单表体信息
	 * @return List 报关单表体数据
	 */
	public List findCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo) {
		String customsDeclarationCommInfo = "CustomsDeclarationCommInfo";
		if (baseCustomsDeclarationCommInfo instanceof CustomsDeclarationCommInfo) {
			customsDeclarationCommInfo = "CustomsDeclarationCommInfo";
		} else if (baseCustomsDeclarationCommInfo instanceof BcsCustomsDeclarationCommInfo) {
			customsDeclarationCommInfo = "BcsCustomsDeclarationCommInfo";
		} else if (baseCustomsDeclarationCommInfo instanceof DzscCustomsDeclarationCommInfo) {
			customsDeclarationCommInfo = "DzscCustomsDeclarationCommInfo";
		}
		return this.find("select a from " + customsDeclarationCommInfo
				+ " a where "
				+ " a.company.id=? and a.baseCustomsDeclaration.emsHeadH2k=? "
				+ " and a.commSerialNo=? ", new Object[] {
				CommonUtils.getCompany().getId(),
				baseCustomsDeclarationCommInfo.getBaseCustomsDeclaration()
						.getEmsHeadH2k(),
				baseCustomsDeclarationCommInfo.getCommSerialNo() });

	}

	// /**
	// * 根据报关单物料查找关封物料
	// *
	// * @param customsDeclarationCommInfo
	// * @return
	// */
	// public CustomsEnvelopCommodityInfo getCustomsEnvelopCommodityInfo(
	// BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {
	// List list = this
	// .find(
	// "select a from CustomsEnvelopCommodityInfo a where a.company.id = ? "
	// + " and a.emsNo=? and a.customsEnvelopBill.customsEnvelopBillNo=? "
	// + " and a.seqNum=? and a.customsEnvelopBill.isAvailability=? ",
	// new Object[] {
	// CommonUtils.getCompany().getId(),
	// customsDeclarationCommInfo
	// .getBaseCustomsDeclaration()
	// .getEmsHeadH2k(),
	// customsDeclarationCommInfo
	// .getBaseCustomsDeclaration()
	// .getCustomsEnvelopBillNo(),
	// customsDeclarationCommInfo.getCommSerialNo(),
	// true });
	// if (list.isEmpty()) {
	// return null;
	// }
	// return (CustomsEnvelopCommodityInfo) list.get(0);
	// }

	/**
	 * 根据报关单物料查找关封物料
	 * 
	 * @param customsDeclarationCommInfo
	 *            报关单表体信息
	 * @return BaseCustomsDeclarationCommInfo 报关单表体
	 */
	public BaseCustomsDeclarationCommInfo getCustomsDeclarationCommInfo(
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.id=?" + " order by a.serialNumber asc ";
		List list = this.find(hql, new Object[] { customsDeclarationCommInfo
				.getId() });
		if (list.isEmpty())
			return null;
		return (BaseCustomsDeclarationCommInfo) list.get(0);
	}

	/**
	 * 显示报关员设置所有资料
	 * 
	 * @return List 报关员设置
	 */
	public List findCustomsUser() {
		return this.find("select a from CustomsUser a where a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
	}

	/**
	 * 用EmsNo查询正在执行的手册或账册本数
	 * 
	 * @param emsNo
	 *            手册号
	 * @return int 报关单商品信息
	 */
	public int findExingEMSCountByEmsNo(String emsNo) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			if (getIsEmsH2kSend()) {
				hql = " select count(a.id) from EmsHeadH2k a where a.company.id=? "
						+ " and a.emsNo=?  and a.historyState=? ";
				parameters.add(CommonUtils.getCompany().getId());
				parameters.add(emsNo);
				parameters.add(new Boolean(false));
			} else {
				hql = " select count(a.id) from EmsHeadH2k a where a.company.id=? "
						+ " and a.emsNo=?  and a.declareState=? and a.historyState=? ";
				parameters.add(CommonUtils.getCompany().getId());
				parameters.add(emsNo);
				parameters.add(DeclareState.PROCESS_EXE);
				parameters.add(new Boolean(false));
			}
			break;
		case ProjectType.BCS:
			hql = " select count(a.id) from Contract a where a.company.id=?"
					+ " and a.emsNo=? and a.declareState=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(DeclareState.PROCESS_EXE);
			break;
		case ProjectType.DZSC:
			hql = " select count(a.id) from DzscEmsPorHead a where a.company.id=?"
					+ " and a.emsNo=? and a.declareState=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(DzscState.EXECUTE);
			break;
		default:
			return 0;
		}
		List list = this.find(hql, parameters.toArray());
		if (list.size() > 0 && list.get(0) != null) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	/**
	 * 根据手册号及报关单号查找报关单
	 * 
	 * @param emsno
	 *            手册号
	 * @param customCode
	 *            报关单号
	 * @return List 报关单
	 */
	public List findAllCustoms(String emsno, String customCode) {
		List para = new ArrayList();
		String hsql = "select a from CustomsDeclaration a where a.company.id = ? "
				+ " and a.emsHeadH2k =? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(emsno);
		if (customCode != null && !customCode.equals("")) {
			hsql += " and a.customsDeclarationCode=?  ";
			para.add(customCode);
		}
		List lbcus = this.find(hsql, para.toArray());
		if (lbcus.size() > 0) {
			return lbcus;
		}
		// ------------------------------------------------
		List paras = new ArrayList();
		String hsqls = "select a from BcsCustomsDeclaration a where a.company.id = ? "
				+ " and a.emsHeadH2k =? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(emsno);
		if (customCode != null && !customCode.equals("")) {
			hsql += " and a.customsDeclarationCode=?  ";
			para.add(customCode);
		}
		List lbcs = this.find(hsqls, para.toArray());
		if (lbcs.size() > 0) {
			return lbcs;
		}
		// ------------------------------------------------
		List par = new ArrayList();
		String hsq = "select a from DzscCustomsDeclaration a where a.company.id = ? "
				+ " and a.emsHeadH2k =? ";
		para.add(CommonUtils.getCompany().getId());
		para.add(emsno);
		if (customCode != null && !customCode.equals("")) {
			hsql += " and a.customsDeclarationCode=?  ";
			para.add(customCode);
		}
		List ldzsc = this.find(hsq, para.toArray());
		if (ldzsc.size() > 0) {
			return ldzsc;
		}
		// ------------------------------------------------

		return new ArrayList();
	}

	/**
	 * 返回BCUS参数设定
	 * 
	 * @param type
	 *            参数类型
	 * @return String BCUS参数设定
	 */
	public String getBpara(int type) {
		List list = this
				.find(
						"select a from BcusParameter a where a.type = ? and a.company.id = ?",
						new Object[] { type, CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			BcusParameter obj = (BcusParameter) list.get(0);
			return obj.getStrValue();
		}
		return null;
	}

	/**
	 * 根据手册/账册号返回合同备案表头
	 * 
	 * @param emsNo
	 *            手册/账册号
	 * @return List合同备案表头
	 */
	public List findAllEmsHeadH2k(String emsNo) {
		List lbcs = this
				.find(
						"select a from Contract a where a.company.id = ?  and a.emsNo =? )",
						new Object[] { CommonUtils.getCompany().getId(), emsNo });
		if (lbcs.size() > 0) {
			return lbcs;
		}
		// -----------------------------------------------------------------------------------
		String isEmsH2kSend = this.getBpara(BcusParameter.EmsEdiH2kSend);
		if (isEmsH2kSend != null && "1".equals(isEmsH2kSend)) {
			List tlbcus = this
					.find(

							"select a from EmsHeadH2k a where a.company.id = ?  and a.historyState=? and a.emsNo =?  ",
							new Object[] { CommonUtils.getCompany().getId(),
									new Boolean(false), emsNo });
			if (tlbcus.size() > 0) {
				return tlbcus;
			}

		} else {
			List lbcus = this
					.find(
							"select a from EmsHeadH2k a where a.company.id = ?  and a.emsNo =? ",
							new Object[] { CommonUtils.getCompany().getId(),
									emsNo });
			if (lbcus.size() > 0) {
				return lbcus;
			}
		}
		// -----------------------------------------------------------------------------------
		List ldzsc = this
				.find(
						"select a from DzscEmsPorHead a where a.company.id = ?    and a.emsNo =? ",
						new Object[] { CommonUtils.getCompany().getId(), emsNo });
		if (ldzsc.size() > 0) {
			return ldzsc;
		}
		// -----------------------------------------------------------------------------------
		return new ArrayList();

	}

	/**
	 * 返回汇率
	 * 
	 * @param currcode
	 *            本地币制
	 * @param curr
	 *            外地币制
	 * @param date
	 *            日期
	 * @return Double 汇率设置
	 */
	public Double findCurrRateByM(String currcode, String curr, Date date) // 显示本币为美元的汇率表
	{
		// List list = this
		// .find(
		// "select a from CurrRate a where a.company.id = ? "
		// + " and a.curr.code=? and a.curr1.code=? and a.createDate>= ?"
		// + "order by a.createDate desc ", new Object[] {
		// CommonUtils.getCompany().getId(), currcode,
		// curr, date });
		// if (list.size() != 0) {
		// CurrRate cr = (CurrRate) list.get(list.size() - 1);
		// return cr.getRate() == null ? 0.0 : cr.getRate();
		// } else {
		// List lists = this
		// .find(
		// "select a from CurrRate a where a.company.id = ? "
		// + " and a.curr.code=? and a.curr1.code=? and a.createDate < ?"
		// + "order by a.createDate desc ",
		// new Object[] { CommonUtils.getCompany().getId(),
		// currcode, curr, date });
		// if (lists.size() != 0) {
		// CurrRate cr = (CurrRate) lists.get(0);
		// return cr.getRate() == null ? 0.0 : cr.getRate();
		// } else {
		// List lit = this.find(
		// "select a from CurrRate a where a.company.id = ? "
		// + " and a.curr.code=? and a.curr1.code=? "
		// + "order by a.createDate desc ", new Object[] {
		// CommonUtils.getCompany().getId(), currcode,
		// curr });
		// if (lit.size() != 0) {
		// CurrRate cr = (CurrRate) lit.get(0);
		// return cr.getRate() == null ? 0.0 : cr.getRate();
		// } else {
		// return 0.0;
		// }
		// }
		//
		// }
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		java.sql.Date.valueOf(sdf.format(date))
		List list = this
				.find(
						"select a from CurrRate a where a.company.id = ? "
								+ " and a.curr.code=? and a.curr1.code=?  and a.createDate<= ?"
								+ "order by a.createDate desc ", new Object[] {
								CommonUtils.getCompany().getId(), currcode,
								curr,date});
		if (list.size() != 0) {
			CurrRate cr = (CurrRate) list.get(0); 
			// CurrRate cr = (CurrRate) list.get(list.size() - 1);
			return cr.getRate() == null ? 0.0 : cr.getRate();
		} else {
			return 0.0;
		}
	}

	/**
	 * 获取报关单头的运输工具相同的所有集装箱号
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List获取报关单头的运输工具相同的所有集装箱号
	 */
	public List findAllContainerByConveyance(
			BaseCustomsDeclaration baseCustomsDeclaration) {

		if ((baseCustomsDeclaration.getId() != null)
				&& (!baseCustomsDeclaration.getId().equals(""))) {
			List paras = new ArrayList();
			String hql = "";
			switch (projectType) {
			case ProjectType.BCUS:
				hql = "select a from CustomsDeclarationContainer as a ";
				break;
			case ProjectType.BCS:
				hql = "select a from BcsCustomsDeclarationContainer as a ";
				break;
			case ProjectType.DZSC:
				hql = "select a from DzscCustomsDeclarationContainer as a ";
				break;
			default:
				hql = "select a from CustomsDeclarationContainer as a ";
				break;
			}
			if (baseCustomsDeclaration.getConveyance() == null
					|| baseCustomsDeclaration.getConveyance().equals("")) {
				hql += " where a.baseCustomsDeclaration.id=? ";
				paras.add(baseCustomsDeclaration.getId());
			} else {
				hql += " where a.baseCustomsDeclaration.conveyance=? order by a.containerCode ";
				paras.add(baseCustomsDeclaration.getConveyance());
			}

			return this.find(hql, paras.toArray());
		} else {
			return new ArrayList();
		}
	}

	/**
	 * 返回报关单商品信息
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfoForExport(int impExpFlag,
			Date beginDate, Date endDate) {
		String hql = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.company.id = ? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (impExpFlag != -1) {
			hql += " and a.baseCustomsDeclaration.impExpFlag = ? ";
			parameters.add(Integer.valueOf(impExpFlag));
		}

		if (beginDate != null) {
			hql += " and (a.baseCustomsDeclaration.declarationDate>=? or a.baseCustomsDeclaration.declarationDate is null) ";
			parameters.add(beginDate);
		}
		if (endDate != null) {
			hql += " and (a.baseCustomsDeclaration.declarationDate<=? or a.baseCustomsDeclaration.declarationDate is null) ";
			parameters.add(endDate);
		}
		hql += " order by a.baseCustomsDeclaration.impExpFlag,a.baseCustomsDeclaration.serialNumber,"
				+ " a.baseCustomsDeclaration.impExpType,a.baseCustomsDeclaration.declarationDate ";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找繁简体对照表资料
	 * 
	 * @param sFields
	 *            字段
	 * @param sValue
	 *            字段的值
	 * @return List繁简体对照表资料
	 */
	public List findGbtobig(String sFields, String sValue) {
		return find("Gbtobig", sFields, sValue);
	}

	/**
	 * 返回报关单头
	 * 
	 * @param flag
	 *            进出口标志
	 * @return List 报关单头
	 */
	public List findCustomsDeclarationByImpExpFlag(int flag) {
		return this
				.find(
						" select a from  CustomsDeclaration  a where  a.company.id= ? "
								+ "  and  a.impExpFlag =?   and  ( a.effective=?  or  a.effective  is null ) "
								+ "    order  by   a.serialNumber  ",
						new Object[] { CommonUtils.getCompany().getId(), flag,
								new Boolean(false) });

	}

	/**
	 * 根据清单号查询已转报关单的清单
	 * 
	 * @param listNo
	 *            清单号
	 * @return List 报关清单表头
	 */
	public List findDzscCustomsBillList(String listNo) {
		return this
				.find(
						" select a from  DzscCustomsBillList  a where  a.company.id= ? "
								+ "  and  a.listNo =?  and  a.isGenerateDeclaration=?  ",
						new Object[] { CommonUtils.getCompany().getId(),
								listNo, new Boolean(true) });

	}

	/**
	 * 返回电子化手册合同备案or电子手册通关备案or电子账册表头信息
	 * 
	 * @param type
	 *            项目类型
	 * @return List 电子化手册合同备案or电子手册通关备案or电子账册表头信息
	 */
	public List findEmsByProjectType(int type) {
		String table = " Contract ";
		if (type == ProjectType.BCS) {
			table = " Contract ";
		} else if (type == ProjectType.DZSC) {
			table = " DzscEmsPorHead ";
		} else if (type == ProjectType.BCUS) {
			table = " EmsHeadH2k ";
		}
		String hsql = " select  a from  " + table
				+ "  a   where a.company.id=? and a.declareState=? ";
		return this.find(hsql,
				new Object[] { CommonUtils.getCompany().getId(), DzscState.EXECUTE});
	}
	/**
	 * 返回正在执行的电子化手册合同备案or电子手册通关备案or电子账册表头信息
	 * 
	 * @param type
	 *            项目类型
	 * @return List 电子化手册合同备案or电子手册通关备案or电子账册表头信息
	 */
	public List findExcuteEmsByProjectType(int type) {
		String table = " Contract ";
		List para = new ArrayList();
		if (type == ProjectType.BCS) {
			table = " Contract ";
			para.add(DeclareState.PROCESS_EXE);
		} else if (type == ProjectType.DZSC) {
			table = " DzscEmsPorHead ";
			para.add(DzscState.EXECUTE);
		} else if (type == ProjectType.BCUS) {
			table = " EmsHeadH2k ";
			para.add(DeclareState.PROCESS_EXE);
		}
		String hsql = " select  a from  " + table
				+ "  a   where a.declareState=? and a.company.id=?  ";
		para.add(CommonUtils.getCompany().getId());
		return this.find(hsql,para.toArray());
	}
	/**
	 * 返回相关表信息
	 * 
	 * @param headid
	 *            相关表表头ID
	 * @param type
	 *            项目类型
	 * @param impExpFlag
	 *            进出扣标志
	 * @param couId
	 *            原产国/产销国ID
	 * @param code
	 *            商品编码
	 * @return List 相关表信息
	 */
	public List findImgOrExg(String headid, int type, int impExpFlag,
			String couId, String code) {
		String table = "";
		if (type == ProjectType.BCS) {
			if (ImpExpFlag.IMPORT == impExpFlag) {
				table = " ContractImg   a  where  a.contract.id =? ";
			} else {
				table = " ContractExg   a  where  a.contract.id =? ";
			}
		} else if (type == ProjectType.DZSC) {
			if (ImpExpFlag.IMPORT == impExpFlag) {
				table = " DzscEmsImgBill   a  where  a.dzscEmsPorHead.id =? ";
			} else {
				table = " DzscEmsExgBill   a  where  a.dzscEmsPorHead.id =? ";
			}
		} else if (type == ProjectType.BCUS) {
			if (ImpExpFlag.IMPORT == impExpFlag) {
				table = " EmsHeadH2kImg  a  where  a.emsHeadH2k.id =? ";
			} else {
				table = " EmsHeadH2kExg  a  where  a.emsHeadH2k.id =? ";
			}
		}
		String hsql = " select  a from  " + table + "  and a.company.id=? ";
		List para = new ArrayList();
		para.add(headid);
		para.add(CommonUtils.getCompany().getId());

		if (couId != null && !couId.trim().equals("")) {
			if (type != ProjectType.BCUS) {
				hsql += " and   a.country.code =? ";
				para.add(couId);
			}
		}
		if (code != null && !code.trim().equals("")) {
			hsql += " and   a.complex.code = ? ";
			para.add(code);
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 返回商品编码
	 * 
	 * @param impexpfalg
	 *            进出口标志
	 * @return List 商品编码资料
	 */
	public List findComplexByFlag(Integer impexpfalg) {
		List para = new ArrayList();
		String hsql = " select  a.complex.code  from CheckupComplex  a    ";
		if (impexpfalg != null) {
			hsql += " where  a.impExpFlag = ? ";
			para.add(impexpfalg);
		}
		return this.find(hsql, para.toArray());
	}

	/**
	 * 返回报关单体商品序号及数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param ptype
	 *            项目类型
	 * @param flag
	 *            进出口标志
	 * @return Map 报关单体商品序号及数量
	 */
	public Map findBaseCustomsDeclarationCommInfo(String emsNo, int ptype,
			int flag) {
		if (ProjectType.BCS == ptype) {
			List para = new ArrayList();
			String str = " select a.commSerialNo, SUM(a.commAmount) from BcsCustomsDeclarationCommInfo a  left join a.baseCustomsDeclaration b"
					+ " where a.company.id= ?   and  b.impExpType  in (?,?,?,?,?) ";
			para.add(CommonUtils.getCompany().getId());// 直接进口
			if (flag == ImpExpFlag.IMPORT) {
				para.add(ImpExpType.DIRECT_IMPORT);// 直接进口
				para.add(ImpExpType.TRANSFER_FACTORY_IMPORT);// 转厂进口
				para.add(ImpExpType.REMAIN_FORWARD_IMPORT);// 余料转进
				// para.add(ImpExpType);
				// para.add(ImpExpType.DIRECT_IMPORT);
			}
			str += " group by a.commSerialNo ";
		}
		HashMap map = new HashMap();
		return map;
	}

	/**
	 * 统计申请单转报关单中间表资料ID
	 * 
	 * @param listid
	 *            申请单物料
	 * @return Integer 申请单转报关单中间表资料ID
	 */
	public Integer findMakeBcsCustomsDeclarationCountByListId(List listid) {
		List para = new ArrayList();
		String hsql = " select COUNT(a.id) from MakeBcsCustomsDeclaration a  where 1=1 ";
		for (int i = 0; i < listid.size(); i++) {
			ImpExpRequestBill info = (ImpExpRequestBill) listid.get(i);
			hsql += " and a.impExpCommodityInfo.impExpRequestBill.id = ? ";
			para.add(info.getId());
		}
		List list = this.find(hsql, para.toArray());
		Integer rt = 0;
		if (list.size() > 0) {
			rt = Integer.parseInt(list.get(0) == null ? "0" : list.get(0)
					.toString());
		}
		return rt;
	}

	/**
	 * 找到录入了QP系统导出的进口报关单
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param condition
	 *            报关单号
	 * @param parameters
	 *            参数
	 * @return List 与指定的报关单号匹配的出口报关单
	 */
	public List findBGDFromQPXml(Integer impExpFlag, String condition,
			List<Object> parameters) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from LoadBGDFromQPXml as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscLoadBGDFromQPXml as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsLoadBGDFromQPXml as a";
			break;
		default:
			hql = " select a from LoadBGDFromQPXml as a";
			break;
		}
		hql += " where a.company.id=? and a.impExpFlag=? ";
		parameters.add(0, CommonUtils.getCompany().getId());
		parameters.add(1, impExpFlag);
		// Integer.valueOf(ImpExpFlag.IMPORT)
		hql += condition;
		return this.find(hql, parameters.toArray());
	}

	// /**
	// * 找到录入了QP系统导出的出口报关单
	// *
	// * @param code
	// * 报关单号
	// * @return 与指定的报关单号匹配的出口报关单
	// */
	// public List findExportBGDFromQPXml(Integer impExpFlag,String condition,
	// List<Object> parameters){
	// String hql = "";
	// switch(projectType)
	// {
	// case ProjectType.BCUS:
	// hql = "select a from LoadBGDFromQPXml as a";
	// break;
	// case ProjectType.DZSC:
	// hql = " select a from DzscLoadBGDFromQPXml as a";
	// break;
	// case ProjectType.BCS:
	// hql = " select a from BcsLoadBGDFromQPXml as a";
	// break;
	// default:
	// hql = " select a from LoadBGDFromQPXml as a";
	// break;
	// }
	// hql += " where a.company.id=? and a.impExpFlag=? "+ " order by a.date
	// desc ";
	// parameters.add(0,CommonUtils.getCompany().getId());
	// parameters.add(1,Integer.valueOf(ImpExpFlag.EXPORT));
	// hql += condition;
	// return this.find(hql, new Object[]{
	// CommonUtils.getCompany().getId(),impExpFlag });
	// }
	/**
	 * 根据类型返回商品禁用信息
	 * 
	 * @param type
	 *            类型
	 * @return List 商品禁用信息
	 */
	public List findCommdityForbid(String type) {
		List list = this
				.find(
						"select distinct a from CommdityForbid a   where a.company.id= ?  and a.type=? ",
						new Object[] { CommonUtils.getCompany().getId(), type });
		return list;

	}

	/**
	 * 取得进口报关单
	 * 
	 *@param str1
	 *            账册号
	 *@param str2
	 *            合同号
	 * @return List进口报关单
	 */
	public List findImportCustomsDeclaration(String str1, String str2) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? and a.emsHeadH2k=?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.IMPORT),
				CommonUtils.getCompany().getId(), str1 });
	}

	/**
	 * 取得出口报关单
	 * 
	 * @param str1
	 *            账册号
	 *@param str2
	 *            合同号
	 * @return List出口报关单
	 */
	public List findExportCustomsDeclaration(String str1, String str2) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += "  where a.impExpFlag=? and a.company.id=? and a.emsHeadH2k=?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.EXPORT),
				CommonUtils.getCompany().getId(), str1 });
	}

	/**
	 * 取得特殊报关单
	 * 
	 * @param str1
	 *            账册号
	 *@param str2
	 *            合同号
	 * @return List 特殊报关单
	 */
	public List findSpecialCustomsDeclaration(String str1, String str2) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = "select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a from CustomsDeclaration as a";
			break;
		}
		hql += " where a.impExpFlag=? and a.company.id=? and a.emsHeadH2k=?";
		return this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.SPECIAL),
				CommonUtils.getCompany().getId(), str1 });
	}

	/**
	 * 根据当前商品编码查询对应的海关商品编码
	 * 
	 * @param code
	 *            代码
	 * @return CustomsComplex 海关商品编码
	 */
	public CheckupComplex findCheckupComplexByCode(String code, int impExpFlag) {
		String hsql = "from CheckupComplex as a where a.complex.code=? and a.impExpFlag=? ";
		List list = this.find(hsql, new Object[] { code, impExpFlag });
		if (list != null && list.size() > 0) {
			return (CheckupComplex) list.get(0);
		}
		return null;
	}

	/**
	 * 根据名称查询客户供应商资料
	 */
	public ScmCoc findScmCocByCode(String code) {
		List list = this
				.find(
						" select a from ScmCoc a where a.company.id = ? and a.code= ? ",
						new Object[] { CommonUtils.getCompany().getId(), code });
		if (list.size() > 0) {
			return (ScmCoc) list.get(0);
		}
		return null;
	}

	/**
	 * 查找进口报关单流水号重复的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @return
	 */
	public List findRepeatImportCustomsDeclarationByEmsNo(String emsNo) {
		String hql = "";
		String obj = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a ";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		switch (projectType) {
		case ProjectType.BCUS:
			obj = " CustomsDeclaration ";
			break;
		case ProjectType.BCS:
			obj = " BcsCustomsDeclaration ";
			break;
		case ProjectType.DZSC:
			obj = " DzscCustomsDeclaration";
			break;
		default:
			obj = " CustomsDeclaration ";
			break;
		}
		hql += " where a.serialNumber in (select serialNumber from " + obj
				+ " where emsHeadH2k=? and impExpFlag=? and a.company.id=? "
				+ " group by serialNumber having(count(*)>1) ) "
				+ "and a.impExpFlag=? and a.company.id=? ";
		parameters.add(emsNo);
		parameters.add(Integer.valueOf(ImpExpFlag.IMPORT));
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(ImpExpFlag.IMPORT));
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		hql += " order by a.serialNumber";
		System.out.println("------" + hql);
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找出口报关单流水号重复的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册号
	 * @return
	 */
	public List findRepeatExportCustomsDeclarationByEmsNo(String emsNo) {
		String hql = "";
		String obj = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		switch (projectType) {
		case ProjectType.BCUS:
			obj = " CustomsDeclaration ";
			break;
		case ProjectType.BCS:
			obj = " BcsCustomsDeclaration ";
			break;
		case ProjectType.DZSC:
			obj = " DzscCustomsDeclaration";
			break;
		default:
			obj = " CustomsDeclaration ";
			break;
		}
		hql += " where a.serialNumber in (select serialNumber from "
				+ obj
				+ " where emsHeadH2k=? and impExpFlag=? and a.company.id=? "
				+ " group by serialNumber having(count(*)>1)) and a.impExpFlag=? and a.company.id=? ";
		parameters.add(emsNo);
		parameters.add(Integer.valueOf(ImpExpFlag.EXPORT));
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(ImpExpFlag.EXPORT));
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !"".equals(emsNo.trim())) {
			hql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		hql += " order by a.serialNumber";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找特殊报关单流水号重复的记录
	 * 
	 * @param request
	 *            请求控制
	 * @return
	 */
	public List findRepeatSpecialCustomsDeclarationByEmsNo() {
		String hql = "";
		String obj = "";
		List<Object> parameters = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = "select a  from CustomsDeclaration as a";
			break;
		}
		switch (projectType) {
		case ProjectType.BCUS:
			obj = " CustomsDeclaration ";
			break;
		case ProjectType.BCS:
			obj = " BcsCustomsDeclaration ";
			break;
		case ProjectType.DZSC:
			obj = " DzscCustomsDeclaration";
			break;
		default:
			obj = " CustomsDeclaration ";
			break;
		}
		hql += " where a.serialNumber in (select serialNumber from "
				+ obj
				+ " where  impExpFlag=? and a.company.id=?"
				+ " group by serialNumber having(count(*)>1)) and a.impExpFlag=? and a.company.id=? ";
		parameters.add(Integer.valueOf(ImpExpFlag.SPECIAL));
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(Integer.valueOf(ImpExpFlag.SPECIAL));
		parameters.add(CommonUtils.getCompany().getId());
		hql += " order by a.serialNumber";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 计算报关单头件，毛，净重
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头Base
	 */
	public void getPiceAndWeight(BaseCustomsDeclaration baseCustomsDeclaration) {
		CompanyOther other = findCompanyOther();
		int fraction = 4;
		if (other != null && other.getIsAutoWeightRound() != null
				&& other.getIsAutoWeightRound()) {
			fraction = 0;
		}

		List list = getInfoSum(baseCustomsDeclaration);
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			/**
			 * 件数
			 */
			if (obj[0] != null
					&& !"".equals(obj[0])
					&& Double.valueOf(obj[0].toString()).doubleValue() != Double
							.valueOf(0)) {
				baseCustomsDeclaration.setCommodityNum(Integer
						.valueOf(formatBig(obj[0], 0)));
			}
			/**
			 * 净重
			 */
			if (obj[2] != null
					&& !"".equals(obj[2])
					&& Double.valueOf(obj[2].toString()).doubleValue() != Double
							.valueOf(0)) {
				baseCustomsDeclaration.setNetWeight(Double.valueOf(formatBig(
						obj[2], fraction)));
			}
			/**
			 * 毛重
			 */
			if (obj[1] != null
					&& !"".equals(obj[1])
					&& Double.valueOf(obj[1].toString()).doubleValue() != Double
							.valueOf(0)) {
				baseCustomsDeclaration.setGrossWeight(Double.valueOf(formatBig(
						obj[1], fraction)));
			}
			saveCustomsDeclaration(baseCustomsDeclaration);
		}
	}

	/**
	 * 把相应的数据变为BigDecimal，并返回指定的位数
	 * 
	 * @param amount
	 *            要改变的数据
	 * @param bigD
	 *            要返回的位数
	 * @return String 变更后的数据
	 */
	public static String formatBig(Object amount, int bigD) {
		if (amount == null || "".equals(amount)) {
			amount = "0";
		}
		if (bigD == 0 && Double.valueOf(amount.toString()).doubleValue() < 0.5) {
			return "1";
		}
		BigDecimal bd = new BigDecimal(amount.toString());
		String amountStr = bd.setScale(bigD, BigDecimal.ROUND_HALF_UP)
				.toString();
		return amountStr;
	}

	public List<BaseCustomsDeclarationCommInfo> findCustomsDeclarationsForPrint(
			String traffic) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo as a";
			break;
		case ProjectType.BCS:
			hql = " select a from BcsCustomsDeclarationCommInfo as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclarationCommInfo as a";
			break;
		default:
			hql = "select a from CustomsDeclarationCommInfo as a";
			break;
		}
		hql += " where a.baseCustomsDeclaration.conveyance = ?";
		hql += "  order by a.serialNumber ";
		return find(hql, traffic);
	}

	/**
	 * 取得备案资料库的归并关系
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findBcsDictPorImgOrExgInnerMergeSeqNum(String declareState,
			Boolean isMaterial, Integer seqNum) {
		List parameters = new ArrayList();
		String tableName = "";
		if (isMaterial) {
			tableName = " BcsDictPorImg ";
		} else {
			tableName = " BcsDictPorExg ";
		}
		String hsql = "select distinct a.innerMergeSeqNum  from "
				+ tableName
				+ " a where  a.company.id=? and a.dictPorHead.declareState=? and a.seqNum=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(declareState);
		parameters.add(seqNum);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 取得大批量修改商品编的内部归并资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findBcsDictPorImgOrExgComplex(String declareState,
			Boolean isMaterial) {
		List parameters = new ArrayList();
		String tableName = "";
		if (isMaterial) {
			tableName = " BcsDictPorImg ";
		} else {
			tableName = " BcsDictPorExg ";
		}
		String hsql = "select distinct a.seqNum,a.complex,a.commName,a.innerMergeSeqNum  from "
				+ tableName
				+ " a where  a.company.id=? and a.dictPorHead.declareState=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(declareState);
		return find(hsql, parameters.toArray());
	}

	/**
	 * 取得大批量修改商品编的内部归并资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findControntImgOrExgComplex(String declareState,
			String contractNo, Boolean isMaterial) {
		List parameters = new ArrayList();
		String tableName = "";
		if (isMaterial) {
			tableName = " ContractImg ";
		} else {
			tableName = " ContractExg ";
		}
		String hsql = "select distinct a.seqNum,a.complex,a.name,a.credenceNo  from "
				+ tableName
				+ " a where  a.company.id=? and a.contract.declareState=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(declareState);
		if (contractNo != null) {
			hsql += " and a.contract.emsNo=? ";
			parameters.add(contractNo);
		}
		return find(hsql, parameters.toArray());
	}

	/**
	 * 取得大批量修改商品编的内部归并资料
	 * 
	 * @param isMaterial
	 * @return
	 */
	public List findBcsTenInnerMergeComplex(Boolean isMaterial) {
		List parameters = new ArrayList();
		String hsql = "select distinct a.bcsTenInnerMerge.seqNum,a.bcsTenInnerMerge.complex,"
				+ " a.bcsTenInnerMerge.name,a.bcsTenInnerMerge.spec "
				+ " from BcsInnerMerge a " + " where a.company.id=?";
		parameters.add(CommonUtils.getCompany().getId());
		if (isMaterial) {// 料件
			hsql += " and   a.materielType=? ";
			parameters.add("2");
		} else {// 成品
			hsql += " and   a.materielType=? ";
			parameters.add("0");
		}
		return find(hsql, parameters.toArray());
	}

	/**
	 * 更新合同备案的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllContractComplex(String contractId, Boolean isMaterial,
			Complex complex, String oldComplexId, Integer seqNum, boolean isSendData) {
		String tableName = "";
		if (isMaterial) {
			tableName = " ContractImg ";
		} else {
			tableName = " ContractExg ";
		}
		String hsql = "update " + tableName + " a set a.complex.id = ? ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(complex.getId());
		if (isSendData) {
			hsql += ", a.modifyMark=? ";
			parameters.add(ModifyMarkState.MODIFIED);
		}
		hsql += " where a.complex.id = ? and a.company.id = ? and a.seqNum=?"
				+ " and a.complex.id <>? and a.contract.id=? ";
		parameters.add(oldComplexId);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(complex.getId());
		parameters.add(contractId);
		// System.out.println("==hsql==" + hsql);
		this.batchUpdateOrDelete(hsql, parameters.toArray());
		
		
		if (isMaterial) {
			List<Object> paras = new ArrayList<Object>();
			hsql = "select a from ContractBom a where  a.complex.id = ? and a.company.id = ? and a.contractImgSeqNum=? and a.contractExg.contract.id=? ";
			paras.add(oldComplexId);
			paras.add(CommonUtils.getCompany().getId());
			paras.add(seqNum);
			paras.add(contractId);
			List<ContractBom> contractBomList = this.find(hsql, paras.toArray());
			for (ContractBom contractBom : contractBomList) {
				contractBom.setComplex(complex);
//				if (isSendData) {
//					contractBom.setModifyMark(ModifyMarkState.MODIFIED);
//				}
				this.saveOrUpdate(contractBom);
			}
		}
	}

	/**
	 * 更新所有备案资料库的商品编码
	 * 
	 * @param isMaterial
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllBcsDictPorComplex(String dictPorHeadId,
			Boolean isMaterial, Complex complex, String oldComplexId, Integer seqNum,
			boolean isSendData) {
		String tableName = "";
		if (isMaterial) {
			tableName = " BcsDictPorImg ";
		} else {
			tableName = " BcsDictPorExg ";
		}
		String hsqlAfter = "update " + tableName + " a set a.complex.id = ?";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(complex.getId());
		if (isSendData) {
			hsqlAfter += ", a.modifyMark=? ";
			parameters.add(ModifyMarkState.MODIFIED);
		}
		hsqlAfter += " where   a.company.id = ? and a.seqNum=? "
				+ " and a.complex.id <>? and  a.dictPorHead.id=? and a.complex.id = ?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		parameters.add(complex.getId());
		parameters.add(dictPorHeadId);
		parameters.add(oldComplexId);
		this.batchUpdateOrDelete(hsqlAfter, parameters.toArray());

	}

	/**
	 * 更新所有内部归并的商品编码
	 * 
	 * @param complex
	 * @param seqNum
	 */
	public void updateAllBcsInnerMergeDataComplex(Boolean isMaterial,
			Complex complex, Integer seqNum) {
		String hsql = "update BcsTenInnerMerge a set a.complex.id = ?"
				+ " where a.seqNum=? and a.complex.id <>? and a.company.id = ?  ";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(complex.getId());
		parameters.add(seqNum);
		parameters.add(complex.getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (isMaterial) {// 料件
			hsql += " and  a.scmCoi=?";
			parameters.add("2");
		} else {// 成品
			hsql += " and  a.scmCoi=?";
			parameters.add("0");
		}
		this.batchUpdateOrDelete(hsql, parameters.toArray());
		// ==========修改对应关系中的物 料主档的报关商品编码==
		parameters.clear();
		String sql = "select a.materiel from BcsInnerMerge a where a.company.id = ?  and a.bcsTenInnerMerge.seqNum =? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(seqNum);
		if (isMaterial) {// 料件
			sql += " and  a.materielType=?";
			parameters.add("2");
		} else {// 成品
			sql += " and  a.materielType=?";
			parameters.add("0");
		}
		List lbcs = this.find(sql, parameters.toArray());
		for (int i = 0; i < lbcs.size(); i++) {
			Materiel materiel = (Materiel) lbcs.get(i);
			this.batchUpdateOrDelete(
					" update Materiel a set complex.id=? where a.ptNo=?",
					new Object[] { complex.getId(), materiel.getPtNo() });

		}
	}

	/**
	 * 查询所有状态的合同
	 * 
	 * @param declareState
	 * @return
	 */
	public List findContractByDeclareState(String declareState) {
		List lbcs = this
				.find(
						"select a from Contract a where a.company.id = ?  and a.declareState =? ",
						new Object[] { CommonUtils.getCompany().getId(),
								declareState });
		return lbcs;
	}

	/**
	 * 查询所有状态的备案资料库
	 * 
	 * @param declareState
	 * @return
	 */
	public List findBcsDictPorHeadByDeclareState(String declareState) {
		List lbcs = this
				.find(
						"select a from BcsDictPorHead a where a.company.id = ?  and a.declareState =? ",
						new Object[] { CommonUtils.getCompany().getId(),
								declareState });
		return lbcs;
	}
	
	/**
	 * 查询未复进数据报表。
	 * @return
	 */
	public List queryReturnImportReport(Date begin, 
			Date end, int impExpType) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"a.commSerialNo," + //备案序号
					"a.commName," +//商品名称
					"c," +//商品id
					"'' as version," +//版本号
					"sum(a.commAmount)," +//统计数量
					"sum(a.commTotalPrice)," +//统计金额
					"a.commSpec," +//商品规格
					"u" +//单位
					" from ");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclarationCommInfo as a");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclarationCommInfo as a");
			break;
		default:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		}
		hql.append(" join a.complex c join a.unit u where ")
			.append("	a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ?")
			.append("	and a.baseCustomsDeclaration.impExpType = ? and (a.baseCustomsDeclaration.tradeMode = '0700' or a.baseCustomsDeclaration.tradeMode = '0300') ")
			.append("group by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ")
			.append("order by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ");
		params.add(begin);
		params.add(end);
		params.add(impExpType);
		
		return this.find(hql.toString(), params.toArray());
	}
	
	
	/**
	 * 查询未复进数据报表。
	 * @return
	 */
	public List queryReturnImportReport(Date begin, 
			Date end, int impExpType,String emsNo,int projectType) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"a.commSerialNo," + //备案序号
					"a.commName," +//商品名称
					"c," +//商品id
					"'' as version," +//版本号
					"sum(a.commAmount)," +//统计数量
					"sum(a.commTotalPrice)," +//统计金额
					"a.commSpec," +//商品规格
					"u" +//单位
					" from ");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclarationCommInfo as a");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclarationCommInfo as a");
			break;
		default:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		}
		hql.append(" join a.complex c join a.unit u left join a.baseCustomsDeclaration.tradeMode t where ");
		if(null!=emsNo){
			hql.append("a.baseCustomsDeclaration.emsHeadH2k = ? and ");
			params.add(emsNo);
		}
		if(null != begin){
			hql.append(" a.baseCustomsDeclaration.declarationDate >= ? and");
			params.add(begin);
		}
		if(null!=end){
			hql.append(" a.baseCustomsDeclaration.declarationDate <= ? and");
			params.add(end);
		}
		
		hql.append(" a.baseCustomsDeclaration.impExpType = ? and (t.code = '0700' or t.code = '0300')  ");
		hql.append("group by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ");
		hql.append("order by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ");
		params.add(impExpType);
		return this.find(hql.toString(), params.toArray());
	}
	
	/**
	 * 查询未复出数据报表。
	 * @return
	 */
	public List queryReturnExportReport(Date begin, 
			Date end, int impExpType) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"a.commSerialNo," + //备案序号
					"a.commName," +//商品名称
					"c," +//商品id
					"a.version," +//版本号
					"sum(a.commAmount)," +//统计数量
					"sum(a.commTotalPrice)," +//统计金额
					"a.commSpec," +//商品规格
					"u" +//单位
					" from ");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclarationCommInfo as a");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclarationCommInfo as a");
			break;
		default:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		}
		hql.append(" join a.complex c join a.unit u where ")
			.append("	a.baseCustomsDeclaration.declarationDate >= ? and a.baseCustomsDeclaration.declarationDate <= ?")
			.append("	and a.baseCustomsDeclaration.impExpType = ? ")
			.append("group by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange,a.version," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ")
			.append("order by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange,a.version," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ");
		params.add(begin);
		params.add(end);
		params.add(impExpType);
		
		return this.find(hql.toString(), params.toArray());
	}
	
	////////////////////////////////////////////////////////////////////////
	/**
	 * 查询未复出数据报表。
	 * @return
	 */
	public List queryReturnExportReport(Date begin, 
			Date end, int impExpType,String emsHeadH2k,int projectType) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"a.commSerialNo," + //备案序号
					"a.commName," +//商品名称
					"c," +//商品id
					"a.version," +//版本号
					"sum(a.commAmount)," +//统计数量
					"sum(a.commTotalPrice)," +//统计金额
					"a.commSpec," +//商品规格
					"u" +//单位
					" from ");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclarationCommInfo as a");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclarationCommInfo as a");
			break;
		default:
			hql.append("CustomsDeclarationCommInfo as a");
			break;
		}
		hql.append(" join a.complex c join a.unit u where ");
		hql.append(" a.baseCustomsDeclaration.impExpType = ? ");
		params.add(impExpType);
		if(emsHeadH2k != null){
			hql.append(" and a.baseCustomsDeclaration.emsHeadH2k = ? ");
			params.add(emsHeadH2k);
		}
		if(begin!=null){
			hql.append(" and a.baseCustomsDeclaration.declarationDate >= ? ");
			params.add(begin);
		}
		if(end!=null){
			hql.append(" and a.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}
		hql.append(" group by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange,a.version," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ");
		hql.append("order by a.commSerialNo,a.commName,a.commSpec,c.id,c.code," +
					"c.name,c.note,c.firstUnit,c.secondUnit,c.lowrate,c.highrate," +
					"c.ccontrol,c.isOut,c.isChange,a.version," +
					"u.code,u.name,u.isOut,u.unitRatio,u.isMustInt ");
		
		return this.find(hql.toString(), params.toArray());
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 查询统计退厂返工数据或返工复出数据。
	 * 1
	 * 2
	 * 3
	 * 4
	 * 
	 * @param begin
	 * @param end
	 * @param complexId
	 * @param impExpType
	 * 
	 * @return
	 */
	public Double countReturnNumbers(Date begin, 
			Date end, String complexId, int impExpType,String version,int commSerialNo) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"sum(a.commAmount)" +//统计数量
					" from CustomsDeclarationCommInfo as a where 1=1");
		// 料件退换出口，料件退换进口。要加贸易方式的条件
		if(ImpExpType.BACK_MATERIEL_EXPORT == impExpType || ImpExpType.DIRECT_IMPORT == impExpType) {
			hql.append(" and (a.baseCustomsDeclaration.tradeMode = '0700' or a.baseCustomsDeclaration.tradeMode = '0300')");
		}
			hql.append(" and a.commSerialNo = ?");
			params.add(commSerialNo);
		if(version != null) {
			hql.append(" and a.version = ?");
			params.add(version);
		}
		if(begin != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate >= ?");
			params.add(begin);
		}
		if(end != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}
		hql.append(" and a.baseCustomsDeclaration.impExpType = ? and a.complex.id = ?");
		params.add(impExpType);
		params.add(complexId);
		List list = this.find(hql.toString(), params.toArray());
		Double nums = 0.0;
		if(list != null && list.size() >0 && list.get(0)!=null) {
			nums = new Double(list.get(0).toString());
		}
		System.out.println(hql);
		return nums;
	}
	
	public BaseCustomsDeclaration findCustomsDeclarationByCustomsDeclarationCode(
			String customsDeclarationCode, boolean impExpFlag) {
		StringBuilder hql = new StringBuilder("select a from ");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclaration as a");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclaration as a");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclaration as a");
			break;
		default:
			hql.append("CustomsDeclaration as a");
			break;
		}
		hql.append(" where a.customsDeclarationCode = ? and a.impExpFlag = ?");
		List list = this.find(hql.toString(), new Object[] {
				customsDeclarationCode, impExpFlag ? 0 : 1 });
		if (list != null && list.size() > 0) {
			return (BaseCustomsDeclaration) list.get(0);
		}

		return null;
	}

	
	/**
	 * 根据清单编号查询报关单
	 * @param listNo
	 * @return
	 */
	public List findCustomsDeclarationByListNo(String listNo) {
		String hql = "select a  from CustomsDeclaration as a";
		hql += "  where a.company.id=? "
				+ " and a.billListId=? ";
		return this.find(hql, new Object[] { CommonUtils.getCompany().getId(),
				listNo });
	}

	/**
	 * 根据统一编号查询清单
	 * 
	 * @param declareState
	 * @return
	 */
	public List findBcusApplyToCustomsBillList(String listUniteNo) {
		List lBcus = this
				.find(
						"select a from ApplyToCustomsBillList a where a.company.id = ?  and a.listUniteNo =? ",
						new Object[] { CommonUtils.getCompany().getId(),
								listUniteNo });
		return lBcus;
	}
	/**
	 * 通过归并前的商品信息获得申请单的商品信息
	 * 
	 * @param atcMergeBeforeComInfo
	 * @param billType
	 *            单据类型
	 * @return
	 */
	public List<ImpExpCommodityInfo> findImpExpCommodityInfoByAtcMergeBeforeComInfo(
			AtcMergeBeforeComInfo atcMergeBeforeComInfo, Integer billType) {
		List params = new ArrayList();
		String hsql = "select b.impExpCommodityInfo from MakeApplyToCustoms b "
				+ "where b.company.id = ? and b.atcMergeBeforeComInfo = ? and  b.impExpCommodityInfo.impExpRequestBill.billType = ? ";
		params.add(CommonUtils.getCompany().getId());
		params.add(atcMergeBeforeComInfo);
		params.add(billType);
		List list = this.find(hsql.toString(), params.toArray());
		return list;
	}
	/**
	 * 查询统计进口料件数,出口成品
	 * @param begin
	 * @param end
	 * @param emsHeadH2
	 * @param complexId
	 * @param impExpFlag
	 * @param version
	 * @param commSerialNo
	 * @return
	 */
	public Double countRetreatNumbers(Date begin, 
			Date end,String emsHeadH2k,String complexId, int impExpType,String version,int commSerialNo){
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"sum(a.commAmount)" +//统计数量
					" from CustomsDeclarationCommInfo as a where 1=1");
		// 进口料件,出口成品。要加贸易方式的条件:来料加工【0214】，进料对口【0615】，进料非对口【0715】
		if(ImpExpType.DIRECT_IMPORT == impExpType||ImpExpType.DIRECT_EXPORT == impExpType) {
			hql.append(" and (a.baseCustomsDeclaration.tradeMode = '0214' or a.baseCustomsDeclaration.tradeMode = '0615' or a.baseCustomsDeclaration.tradeMode = '0715')");
		}
			hql.append(" and a.commSerialNo = ?");
			params.add(commSerialNo);
		if(emsHeadH2k!=null){
			hql.append(" and a.baseCustomsDeclaration.emsHeadH2k = ?");
			params.add(emsHeadH2k);
		}
		if(version != null) {
			hql.append(" and a.version = ?");
			params.add(version);
		}
		if(begin != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate >= ?");
			params.add(begin);
		}
		if(end != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}
		hql.append(" and a.baseCustomsDeclaration.impExpType = ? and a.complex.id = ?");
		params.add(impExpType);
		params.add(complexId);
		List list = this.find(hql.toString(), params.toArray());
		Double nums = 0.0;
		if(list != null && list.size() >0 && list.get(0)!=null) {
			nums = new Double(list.get(0).toString());
		}
		return nums;
	}
	/**
	 * 统计可退换料件出口数
	 * @param begin
	 * @param end
	 * @param complexId
	 * @param impExpType
	 * @param emsHeadH2k
	 * @return
	 */
	public Double countReturnNumbers(Date begin, 
			Date end,String emsHeadH2k, String complexId, int impExpType,String version,int commSerialNo) {
		List params = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"select " +
					"sum(a.commAmount)" +//统计数量
					" from CustomsDeclarationCommInfo as a where 1=1");
		// 料件退换出口，料件退换进口。要加贸易方式的条件
		if(ImpExpType.BACK_MATERIEL_EXPORT == impExpType || ImpExpType.DIRECT_IMPORT == impExpType) {
			hql.append(" and (a.baseCustomsDeclaration.tradeMode = '0700' or a.baseCustomsDeclaration.tradeMode = '0300')");
		}
			hql.append(" and a.commSerialNo = ?");
			params.add(commSerialNo);
		if(emsHeadH2k !=null){
			hql .append("  and a.baseCustomsDeclaration.emsHeadH2k = ?");
			params.add(emsHeadH2k);
		}
		if(version != null) {
			hql.append(" and a.version = ?");
			params.add(version);
		}
		if(begin != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate >= ?");
			params.add(begin);
		}
		if(end != null) {
			hql.append(" and a.baseCustomsDeclaration.declarationDate <= ?");
			params.add(end);
		}
		hql.append(" and a.baseCustomsDeclaration.impExpType = ? and a.complex.id = ?");
		params.add(impExpType);
		params.add(complexId);
		List list = this.find(hql.toString(), params.toArray());
		Double nums = 0.0;
		if(list != null && list.size() >0 && list.get(0)!=null) {
			nums = new Double(list.get(0).toString());
		}
		System.out.println(hql);
		return nums;
	}
	
	/**
	 * 根据 
	 * 手册号,补充报关单类型,流水号,
	 * 开始日期（进出口日期在该日期之后）,
	 * 结束日期（进出口日期在该日期之前）, 
	 * 查询报关单
	 * @param ems
	 * @param supplmentType
	 * @param serialNumber
	 * @param begin
	 * @param end
	 * @return
	 */
	public List getCustomsDeclaration(String ems, Integer supplmentType, 
			Integer serialNumber, Date begin, Date end) {
		StringBuffer hql = new StringBuffer();
		List params = new ArrayList();
		
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("select a  from CustomsDeclaration as a");
			break;
		case ProjectType.BCS:
			hql.append("select a  from BcsCustomsDeclaration as a");
			break;
		case ProjectType.DZSC:
			hql.append("select a  from DzscCustomsDeclaration as a");
			break;
		default:
			hql.append("select a  from CustomsDeclaration as a");
			break;
		}
		hql.append(" where a.emsHeadH2k = ? ");
		params.add(ems);
		if(supplmentType != null ) {
			hql.append(" and a.supplmentType = ?");
			params.add(supplmentType);
		}
		if(serialNumber != null) {
			hql.append(" and a.serialNumber = ?");
			params.add(serialNumber);
		}
		if(begin != null) {
			hql.append(" and a.impExpDate > ?");
			params.add(begin);
		}
		if(end != null) {
			hql.append(" and a.impExpDate < ?");
			params.add(end);
		}
		
		return this.find(hql.toString(), params.toArray());
	}
	
	/**
	 * 保存补充报关单信息
	 * @param decSupplementList
	 */
	public String saveDecSupplementList(
			DecSupplementList decSupplementList) {
		this.saveOrUpdate(decSupplementList);
		return decSupplementList.getId();
	}
	/**
	 * 通过获得补充报关单信息
	 * @param decSupplementList
	 */
	public List getDecSupplementList(String supType,String baseCustomsDeclarationCommInfo) {
		StringBuffer hql = new StringBuffer();
		List params = new ArrayList();
			hql.append("select a  from DecSupplementList as a where a.supType = ?  and a.baseCustomsDeclarationCommInfo = ? ");
		    params.add(supType);
		    params.add(baseCustomsDeclarationCommInfo);
		    return this.find(hql.toString(), params.toArray());
	}
	
	/**
	 * 通过报关单id获得报关单底下的补充报关单信息列表
	 * @param decSupplementList
	 * @return
	 */
	public List getDecSupplementList(String baseCustomsDeclarationId) {
		StringBuffer hql = new StringBuffer();
		List params = new ArrayList();
		hql.append("select a from DecSupplementList as a ,");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclarationCommInfo as b");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclarationCommInfo as b");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclarationCommInfo as b");
			break;
		default:
			hql.append("CustomsDeclarationCommInfo as b");
			break;
		}
		
		hql.append(" where a.baseCustomsDeclarationCommInfo = b.id");
		hql.append(" and b.baseCustomsDeclaration.id = ? ");
		params.add(baseCustomsDeclarationId);
		
		return this.find(hql.toString(), params.toArray());
	}
	
	/**
	 * 根据时间 补充报关单
	 * @param request
	 * @param begin
	 * @param end
	 * @return
	 */
	public List queryDecSupplementList(Date begin, Date end){
		StringBuffer hql = new StringBuffer();
		List params = new ArrayList();
		hql.append("select b.baseCustomsDeclaration.serialNumber,b.baseCustomsDeclaration.customsDeclarationCode,b.baseCustomsDeclaration.supplmentType,");
		hql.append("a.supType,b.baseCustomsDeclaration.impExpType,b.commSerialNo,b.complex.code,b.commName,b.commSpec,a.inputDate,");
		hql.append("b.baseCustomsDeclaration.emsHeadH2k,a.isSend,a.baseCustomsDeclarationCommInfo,a.id,b.baseCustomsDeclaration from DecSupplementList as a ,");
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append("CustomsDeclarationCommInfo as b");
			break;
		case ProjectType.BCS:
			hql.append("BcsCustomsDeclarationCommInfo as b");
			break;
		case ProjectType.DZSC:
			hql.append("DzscCustomsDeclarationCommInfo as b");
			break;
		default:
			hql.append("CustomsDeclarationCommInfo as b");
			break;
		}
		hql.append(" where a.baseCustomsDeclarationCommInfo = b.id");
		hql.append(" and a.inputDate >=? ");
		hql.append(" and a.inputDate <=? ");
	    params.add(CommonUtils.getBeginDate(begin));
	    params.add(CommonUtils.getEndDate(end));
	    return this.find(hql.toString(), params.toArray());
	}
	/**
	 * 根据全部补充报关单
	 * @param request
	 * @param begin
	 * @param end
	 * @return
	 */
	
	//2012-9-14
	public List queryDecSupplementListAll(){
		StringBuffer hql = new StringBuffer();
		List params = new ArrayList();
			hql.append("select b.serialNumber,b.baseCustomsDeclaration.customsDeclarationCode,b.baseCustomsDeclaration.supplmentType,");
			hql.append("a.supType,b.baseCustomsDeclaration.impExpType,b.commSerialNo,b.complex.code,b.commName,b.commSpec,a.inputDate,");
			hql.append("b.baseCustomsDeclaration.emsHeadH2k,a.isSend,a.baseCustomsDeclarationCommInfo,a.id,b.baseCustomsDeclaration from DecSupplementList as a ,");
			switch (projectType) {
			case ProjectType.BCUS:
				hql.append("CustomsDeclarationCommInfo as b");
				break;
			case ProjectType.BCS:
				hql.append("BcsCustomsDeclarationCommInfo as b");
				break;
			case ProjectType.DZSC:
				hql.append("DzscCustomsDeclarationCommInfo as b");
				break;
			default:
				hql.append("CustomsDeclarationCommInfo as b");
				break;
			}
			hql.append(" where a.baseCustomsDeclarationCommInfo = b.id");
			List list = this.find(hql.toString(), params.toArray()); 
		    return list;
	}
	/**
	 * 删除补充报关单
	 * @param request
	 * @param date
	 * @return
	 */
	public void deleteDecSupplementList(String id) {
		    DecSupplementList obj = null;
			List list = this
					.find(
							"select a from DecSupplementList a where a.id = ?",
							new Object[] { id });
			if (list != null && list.size() > 0) {
				obj = (DecSupplementList) list.get(0);
				this.delete(obj);
			}
	}
	/**
	 * 根据ID查找补充报关单
	 * @param request
	 * @param date
	 * @return
	 */
	public List getDecSupplementListById(String id){
		List list = this
				.find(
						"select a from DecSupplementList a where a.id = ?",
						new Object[] { id });
		return list;
	}
	/**
	 * 根据ID查找内部商品新增补充报关单表体
	 * @param request
	 * @param date
	 * @return
	 */
	public BaseCustomsDeclarationCommInfo getBaseCustomsDeclarationCommInfoById(String id){
		StringBuffer hql = new StringBuffer();
		switch (1) {
		case ProjectType.BCUS:
			hql.append("select a  from CustomsDeclarationCommInfo as a");
			break;
		case ProjectType.BCS:
			hql.append("select a  from BcsCustomsDeclarationCommInfo as a");
			break;
		case ProjectType.DZSC:
			hql.append("select a  from DzscCustomsDeclarationCommInfo as a");
			break;
		default:
			hql.append("select a  from CustomsDeclarationCommInfo as a");
			break;
		}
		hql.append(" where a.id=? ");
		List list = this.find(hql.toString(), new Object[] {id});
		if(list != null && list.size() > 0) {
			return (BaseCustomsDeclarationCommInfo) list.get(0);
		}
		return null;
	}
	/**
	 * 查找所有未发送的补充申报单
	 * @param request
	 * @param isSend
	 * @return
	 */
	public List findDecSupplementListByIsSend(String isSend){
		StringBuffer hql = new StringBuffer();
		List params = new ArrayList();
			hql.append("select b.baseCustomsDeclaration.emsHeadH2k,b.baseCustomsDeclaration.serialNumber,");
			hql.append("b.baseCustomsDeclaration.customsDeclarationCode,a.inputDate,a.isCheck ,b.baseCustomsDeclaration.impExpType,a.id,a.baseCustomsDeclarationCommInfo ");
			hql.append(" from DecSupplementList as a ,");
			switch (projectType) {
			case ProjectType.BCUS:
				hql.append("CustomsDeclarationCommInfo as b");
				break;
			case ProjectType.BCS:
				hql.append("BcsCustomsDeclarationCommInfo as b");
				break;
			case ProjectType.DZSC:
				hql.append("DzscCustomsDeclarationCommInfo as b");
				break;
			default:
				hql.append("CustomsDeclarationCommInfo as b");
				break;
			}
			hql.append(" where a.baseCustomsDeclarationCommInfo = b.id and a.isSend = ?");
			params.add(isSend);
		    return this.find(hql.toString(), params.toArray());
	}
	
	
	/**
	 * 查找报关单底下所有商品所有的主动补充申报单
	 * @param ids
	 * @return
	 */
	public List<DecSupplementList> findDecSupplementListByCustomsDeclarationComminfoIds(String[] ids){
		StringBuffer hql = new StringBuffer("Select a from DecSupplementList a where a.isSend != 1 and a.baseCustomsDeclarationCommInfo in (");
		for (int i = 0; i < ids.length; i++) {
			if(i != 0) {
				hql.append(",");
			} 
			hql.append("'" + ids[i] + "'");
		}
		hql.append(")");
		
		return this.find(hql.toString());
	}
	/**
	 *  申请单商品信息来自申请单转报关单中间表
	 * 
	 * @param customs
	 *            报关商品信息
	 * @return 报关清单内容
	 */
	public List findImpExpCommodityInfoByCustomsInfo(
			BaseCustomsDeclarationCommInfo customs) {
		return this
				.find(
						"select a.impExpCommodityInfo from MakeBcsCustomsDeclaration a "
								+ " where a.company.id = ? and a.bcsCustomsDeclarationCommInfo.id  = ?",
						new Object[] { CommonUtils.getCompany().getId(),
								customs.getId() });
	}
	/**
	 * 获得多个申请单,报关单通过申请单转报关单中间表获得申请单
	 * @param custom 报关单
	 * @return
	 */
	public List getMultiImpExpBillByCustomsDeclaration(
			BaseCustomsDeclaration custom) {
		return this
				.find(
						" select distinct b.impExpCommodityInfo.impExpRequestBill from "
								+ " MakeApplyToCustoms b where b.atcMergeBeforeComInfo in "
								+ " (select a from AtcMergeBeforeComInfo as a  where a.afterComInfo in "
								+ " (select a.atcMergeAfterComInfo from MakeListToCustoms a  where a.customsInfo in "
								+ " (select a from CustomsDeclarationCommInfo a where a.baseCustomsDeclaration.company.id=? " 
								+ " and a.baseCustomsDeclaration.id=?)))" 
								+ " order by b.impExpCommodityInfo.impExpRequestBill.billNo ",
						new Object[] { CommonUtils.getCompany().getId(),
										custom.getId() });
	}
	
	
	/**
	 * 根据手册号来抓取合同备案表头
	 * 
	 * @param emsNo
	 *            手册编号
	 * @return List 是DzscEmsPorWjHead型，合同备案表头
	 */
	public DzscEmsPorHead findExingDzscEmsPorHeadByEmsNo(String emsNo) {
		String hql = " select a from DzscEmsPorHead a "
				+ " where a.emsNo=? and a.company.id=? "
				+ " and a.declareState=? ";
		List list = this.find(hql, new Object[] { emsNo,
				CommonUtils.getCompany().getId(), DzscState.EXECUTE });
		if (list.size() > 0) {
			return (DzscEmsPorHead) list.get(0);
		}
		return null;
	}
	/**
	 * 取得进口报关单
	 * 
	 * @param code
	 *            报关单号
	 * @return 与指定的报关单号匹配的出口报关单
	 */
	public BaseCustomsDeclaration findImportCustomsDeclarationByCode(String code,int projectType) {
		String hql = "";
		switch (projectType) {
		
		case ProjectType.BCUS:
			hql = "select a  from CustomsDeclaration as a";
			break;
		case ProjectType.BCS:
			hql = "select a  from BcsCustomsDeclaration as a";
			break;
		case ProjectType.DZSC:
			hql = " select a from DzscCustomsDeclaration as a";
			break;
		default:
			hql = " select a from CustomsDeclaration as a";
			break;
		}
		
		String ss = CommonUtils.getCompany().getId();
		hql += "  where a.impExpFlag=? and a.company.id=? "
				+ " and a.customsDeclarationCode=? ";
		List list = this.find(hql, new Object[] {
				Integer.valueOf(ImpExpFlag.IMPORT),
				CommonUtils.getCompany().getId(), code });
		if (list.size() > 0) {
			return (BaseCustomsDeclaration) list.get(0);
		}
		return null;
	}
	/**
	 * 取得报关单商品信息(电子化手册)
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return 报关单商品信息
	 */
	public List findCustomsDeclarationCommInfos(
			BaseCustomsDeclaration baseCustomsDeclaration,int projectType) {
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = " select a from CustomsDeclarationCommInfo a ";
			break;
		case ProjectType.BCS:
			hql = "select a from BcsCustomsDeclarationCommInfo a  ";
			break;
		case ProjectType.DZSC:
			hql = "select a  from DzscCustomsDeclarationCommInfo a  ";
			break;
		default:
			hql = "select a  from CustomsDeclarationCommInfo a ";
			break;
		}
		hql += " where a.baseCustomsDeclaration.id=?"
				+ " order by a.serialNumber asc ";//order by a.commSerialNo,a.country.code asc
		return this.find(hql, new Object[] { baseCustomsDeclaration.getId() });
	}
	/**
	 * 取得报关单商品信
	 * @return 报关单
	 */
	public List<BaseCustomsDeclaration> findCustomsDeclaration(Integer impExpFlag,Date beginDate,Date endDate,
			String customsDecCode,Integer projectType,String emsNo,List impExpTypeList) {
		StringBuffer hql = new StringBuffer(" select a from ");
		List<Object> params = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append(" CustomsDeclaration a ");
			break;
		case ProjectType.BCS:
			hql.append(" BcsCustomsDeclaration a ");
			break;
		case ProjectType.DZSC:
			hql.append(" DzscCustomsDeclaration a ");
			break;
		default:
			hql.append(" CustomsDeclaration a ");
			break;
		}
		hql.append(" where a.company.id = ? ");
		params.add(CommonUtils.getCompany().getId());
		
		if(impExpFlag!=null){
			hql.append(" and a.impExpFlag = ? ");
			params.add(impExpFlag);
		}
		
		if (beginDate != null) {
			hql.append(" and (a.declarationDate>=? or a.declarationDate is null) ");
			params.add(CommonUtils.getBeginDate(beginDate));
		}
		
		if (endDate != null) {
			hql.append(" and (a.declarationDate<=? or a.declarationDate is null) ");
			params.add(CommonUtils.getEndDate(endDate));
		}
		
		if(customsDecCode!=null&&!"".equals(customsDecCode.trim())){
			hql.append(" and a.customsDeclarationCode = ? ");
			params.add(customsDecCode);
		}
		
		if(emsNo!=null&&!"".equals(emsNo.trim())){
			hql.append(" and a.emsHeadH2k = ? ");
			params.add(emsNo);
		}
		
		if(!impExpTypeList.isEmpty()){
			hql.append(" and a.impExpType in ("+impExpTypeList.get(0)+","+impExpTypeList.get(1)+","+impExpTypeList.get(2)+","+impExpTypeList.get(3)+","+impExpTypeList.get(4)+""
					+ ","+impExpTypeList.get(5)+","+impExpTypeList.get(6)+","+impExpTypeList.get(7)+","+impExpTypeList.get(8)+")");
			
		}
		hql.append(" order by a.serialNumber asc ");
		System.out.println(hql.toString());
		
		return this.find(hql.toString(),params.toArray());
	}
	
	/**
	 * 查询报关单
	 * @param impExpFlag
	 * @param beginDate
	 * @param endDate
	 * @param customsDecCode
	 * @param projectType
	 * @param emsNo
	 * @return
	 */
	public List<BaseCustomsDeclaration> findCustomsDeclaration(Integer impExpFlag,Date beginDate,Date endDate,
			String customsDecCode,Integer projectType,String emsNo) {
		StringBuffer hql = new StringBuffer(" select a from ");
		List<Object> params = new ArrayList<Object>();
		switch (projectType) {
		case ProjectType.BCUS:
			hql.append(" CustomsDeclaration a ");
			break;
		case ProjectType.BCS:
			hql.append(" BcsCustomsDeclaration a ");
			break;
		case ProjectType.DZSC:
			hql.append(" DzscCustomsDeclaration a ");
			break;
		default:
			hql.append(" CustomsDeclaration a ");
			break;
		}
		hql.append(" where a.company.id = ? ");
		params.add(CommonUtils.getCompany().getId());
		
		if(impExpFlag!=null){
			hql.append(" and a.impExpFlag = ? ");
			params.add(impExpFlag);
		}
		
		if (beginDate != null) {
			hql.append(" and (a.declarationDate>=? or a.declarationDate is null) ");
			params.add(CommonUtils.getBeginDate(beginDate));
		}
		
		if (endDate != null) {
			hql.append(" and (a.declarationDate<=? or a.declarationDate is null) ");
			params.add(CommonUtils.getEndDate(endDate));
		}
		
		if(customsDecCode!=null&&!"".equals(customsDecCode.trim())){
			hql.append(" and a.customsDeclarationCode = ? ");
			params.add(customsDecCode);
		}
		
		if(emsNo!=null&&!"".equals(emsNo.trim())){
			hql.append(" and a.emsHeadH2k = ? ");
			params.add(emsNo);
		}
		
		hql.append(" order by a.serialNumber asc ");
		System.out.println(hql.toString());
		
		return this.find(hql.toString(),params.toArray());
	}

	public List findCustomsDeclaration(Integer projectType,Date date,Integer impExpFlag){
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a.customsDeclarationCode  from CustomsDeclaration  a";
			break;
		case ProjectType.BCS:
			hql = "select a.customsDeclarationCode  from BcsCustomsDeclaration  a";
			break;
		case ProjectType.DZSC:
			hql="select a.customsDeclarationCode  from DzscCustomsDeclaration  a";
			break;
		default:
			hql = "select a.customsDeclarationCode  from CustomsDeclaration  a";
			break;
		}
		hql+=" where a.company.id = ? and a.declarationDate = ? and a.impExpFlag = ?";
		return this.find(hql,new Object[] {CommonUtils.getCompany().getId(),date,impExpFlag});
	}
	/**
	 * 查询所有报告单号
	 * @param projectType
	 * @param impExpFlag
	 * @return
	 */
	public List findAllCustomsDeclaration(Integer projectType,Integer impExpFlag){
		List para = new ArrayList();
		String hql = "";
		switch (projectType) {
		case ProjectType.BCUS:
			hql = "select a.customsDeclarationCode  from CustomsDeclaration  a";
			break;
		case ProjectType.BCS:
			hql = "select a.customsDeclarationCode  from BcsCustomsDeclaration  a";
			break;
		case ProjectType.DZSC:
			hql="select a.customsDeclarationCode  from DzscCustomsDeclaration  a";
			break;
		default:
			hql = "select a.customsDeclarationCode  from CustomsDeclaration  a";
			break;
		}
		hql+=" where a.company.id = ?";
		para.add(CommonUtils.getCompany().getId());
		if(impExpFlag!=null){
			hql+=" and a.impExpFlag = ? ";
			para.add(impExpFlag);
		}
		return this.find(hql,para.toArray());
	}
	
	public List findContractHead(Integer projectType,BaseCustomsDeclaration bgdhead){
		if(projectType==null||bgdhead==null){
			return new ArrayList();
		}
		String hql = "";
		List params = new ArrayList();
		if (projectType == ProjectType.BCS) {
			hql = "select a from Contract a where a.company.id = ? and a.declareState = ? and a.emsNo = ?";
			params.add(CommonUtils.getCompany().getId());
			params.add(DeclareState.PROCESS_EXE);
			params.add(bgdhead.getEmsHeadH2k());
		} else if (projectType == ProjectType.DZSC) {
			hql = "select a from DzscEmsPorHead a where a.company.id = ? and a.declareState = ? and a.emsNo = ?";
			params.add(CommonUtils.getCompany().getId());
			params.add(DeclareState.PROCESS_EXE);
			params.add(bgdhead.getEmsHeadH2k());
		} else if (projectType == ProjectType.BCUS) {
			hql = "select a from EmsHeadH2k a where a.company.id = ? and a.historyState=?  and a.emsNo = ? order by a.modifyTimes DESC";
			params.add(CommonUtils.getCompany().getId());
			params.add(new Boolean(false));
			params.add(bgdhead.getEmsHeadH2k());
		}
		
		return this.find(hql,params.toArray());
	}
	
	public List findBaseEmsImg(Integer projectType,BaseEmsHead baseContractHead){
		if(projectType==null){
			return new ArrayList();
		}
		String tableName = "";
		if (projectType == ProjectType.BCS) {
			tableName = " ContractImg ";
		} else if (projectType == ProjectType.DZSC) {
			tableName = " DzscEmsImgBill ";
		} else if (projectType == ProjectType.BCUS) {
			tableName = " EmsHeadH2kImg ";
		}
		List params = new ArrayList();
		String hql = "select a from "+tableName+" a where a.company.id=? ";
		params.add(CommonUtils.getCompany().getId());
		if(baseContractHead!=null&&baseContractHead.getId()!=null){
			
			if (projectType == ProjectType.BCS) {
				hql+=" and a.contract.id = ? ";
			} else if (projectType == ProjectType.DZSC) {
				hql+=" and a.dzscEmsPorHead.id = ? ";
			} else if (projectType == ProjectType.BCUS) {
				hql+=" and a.emsHeadH2k.id = ? ";
			}
			
			params.add(baseContractHead.getId());
		}
		return this.find(hql,params.toArray());
	}
	
	public List findBaseEmsExg(Integer projectType,BaseEmsHead baseContractHead){
		if(projectType==null){
			return new ArrayList();
		}
		String tableName = "";
		if (projectType == ProjectType.BCS) {
			tableName = " ContractExg ";
		} else if (projectType == ProjectType.DZSC) {
			tableName = " DzscEmsExgBill ";
		} else if (projectType == ProjectType.BCUS) {
			tableName = " EmsHeadH2kExg ";
		}
		List params = new ArrayList();
		String hql = "select a from "+tableName+" a where a.company.id=? ";
		params.add(CommonUtils.getCompany().getId());
		if(baseContractHead!=null&&baseContractHead.getId()!=null){
			if (projectType == ProjectType.BCS) {
				hql+=" and a.contract.id = ? ";
			} else if (projectType == ProjectType.DZSC) {
				hql+=" and a.dzscEmsPorHead.id = ? ";
			} else if (projectType == ProjectType.BCUS) {
				hql+=" and a.emsHeadH2k.id = ? ";
			}
			params.add(baseContractHead.getId());
		}
		return this.find(hql,params.toArray());
	}
	
	public String getReceiverUnit() {
		List list = this
				.find(
						"select a.name from Company a where id = ?",
						new Object[] { CommonUtils.getCompany().getId() });
		if (list.size() > 0 && list.get(0) != null) {
			return String.valueOf(list.get(0));
		}
		return null;
	}
	
	/**
	 * 查找【出口装箱单/出口加工发票】打印显示购货单位/发货单位
	 * @return
	 */
	public List findExportPackinglistOrInvoice(){
		return this.find("select a.isExportPackinglistOrInvoice from CompanyOther a where a.company= ?",
				new Object[] { CommonUtils.getCompany()});
	}
}
	
