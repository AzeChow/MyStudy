/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;

/**
 * com.bestway.dzsc.contractstat.dao.DzscStatDao
 * 
 * @author Administrator
 */
public class DzscStatDao extends BaseDao {
	
	/**
	 * 查找已报关的商品
	 * 
	 * @param isImport 判断是否进口类型，true为进口
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param emsNo 手册号
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 * @author 石小凯
	 */
	public List findImpExpCommInfoList(boolean isMaterial, Integer seqNum,String ptName,
			String customer, String impExpType, Date beginDate, Date endDate,
			String emsNo, String billState) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a ";
		if (isMaterial) {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_IMPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			 parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_EXPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			 parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			 parameters.add(ImpExpType.REWORK_EXPORT);
		}
		if("1".equals(billState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(new Boolean(true));
		} else if("2".equals(billState)) {
			hsql += " and a.baseCustomsDeclaration.effective=? ";
			parameters.add(new Boolean(false));
		}
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		if (ptName != null&&  !ptName.trim().equals("")) {
			hsql += " and a.commName=? ";
			parameters.add(ptName);
		}
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}
	/**
	 * 查找已报关的商品
	 * 
	 * @param isImport 判断是否进口类型，true为进口
	 * @param seqNum 商品序号
	 * @param customer 客户
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param emsNo 手册号
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findImpExpCommInfoList(boolean isMaterial, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a ";
		if (isMaterial) {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_IMPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			 parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_EXPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			 parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			 parameters.add(ImpExpType.REWORK_EXPORT);
		}
		hsql += " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(new Boolean(true));
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (seqNum != null) {
			hsql += " and a.commSerialNo=? ";
			parameters.add(seqNum);
		}
		
		if (customer != null && !customer.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.customer.id=? ";
			parameters.add(customer);
		}
		if (impExpType != null && !impExpType.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.declarationDate,a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}
	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial 判断是否料件类型，true为料件
	 * @param emsNo 手册号
	 * @return List 存放了已报关商品的商品序号、商品编码、商品名称、商品规格
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial, String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commSerialNo,a.complex.code,a.commName,a.commSpec "
				+ " from DzscCustomsDeclarationCommInfo as a ";
		if (isMaterial) {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_IMPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			 parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_EXPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			 parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			 parameters.add(ImpExpType.REWORK_EXPORT);
		}
//		hsql += " and b.dzscEmsPorHead.declareState=? ";
//		parameters.add(DzscState.EXECUTE);
		hsql += " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(new Boolean(true));
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
//		if (isMaterial) {
//			// hsql += " and a.baseCustomsDeclaration.impExpType in
//			// (?,?,?,?,?)";
//			// parameters.add(ImpExpType.DIRECT_IMPORT);
//			// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
//			// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
//			// parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);
//			// parameters.add(ImpExpType.EQUIPMENT_IMPORT);
//			// hsql+=" and a.baseCustomsDeclaration.impExpFlag=? ";
//			// parameters.add(ImpExpFlag.IMPORT);
//		} else {
//			// hsql += " and a.baseCustomsDeclaration.impExpType in
//			// (?,?,?,?,?,?,?,?,?)";
//			// parameters.add(ImpExpType.DIRECT_EXPORT);
//			// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
//			// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
//			// parameters.add(ImpExpType.REWORK_EXPORT);
//			// parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
//			// parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
//			// parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
//			// parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
//			// parameters.add(ImpExpType.BACK_PORT_REPAIR);
//			// hsql+=" and a.baseCustomsDeclaration.impExpFlag=? ";
//			// parameters.add(ImpExpFlag.IMPORT);
//		}
		hsql += " order by a.commSerialNo";
		return this.find(hsql, parameters.toArray());
	}
	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial 判断是否料件类型，true为料件
	 * @param emsNo 手册号
	 * @return List 存放了已报关商品的商品序号、商品编码、商品名称、商品规格
	 * @author 石小凯
	 */
	public List<List> findCustomsDeclarationCommInfoNew(boolean isMaterial, String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.commName,a.commSpec "
			+ " from DzscCustomsDeclarationCommInfo as a ";
	if (isMaterial) {
		 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
		 parameters.add(ImpExpType.DIRECT_IMPORT);
		 parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
		 parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		 parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
		 parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
	} else {
		 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
		 parameters.add(ImpExpType.DIRECT_EXPORT);
		 parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
		 parameters.add(ImpExpType.BACK_FACTORY_REWORK);
		 parameters.add(ImpExpType.REWORK_EXPORT);
	}
	hsql += " and a.baseCustomsDeclaration.effective=? ";
	parameters.add(new Boolean(true));
	hsql += " and a.baseCustomsDeclaration.company.id=? ";
	parameters.add(CommonUtils.getCompany().getId());
	if (emsNo != null && !emsNo.trim().equals("")) {
		hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
		parameters.add(emsNo);
	}
	List list=this.find(hsql, parameters.toArray());
		return list;
	}
	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial 判断是否料件类型，true为料件
	 * @param impExpType 进出口类型
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param emsNo 手册号
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	  */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			String impExpType, String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "select a from DzscCustomsDeclarationCommInfo as a ";
		if (isMaterial) {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_IMPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			 parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_IMPORT);
			 parameters.add(ImpExpType.REMAIN_FORWARD_EXPORT);
		} else {
			 hsql += " where a.baseCustomsDeclaration.impExpType in (?,?,?,?)";
			 parameters.add(ImpExpType.DIRECT_EXPORT);
			 parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			 parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			 parameters.add(ImpExpType.REWORK_EXPORT);
		}
//		hsql += " and b.dzscEmsPorHead.declareState=? ";
//		parameters.add(DzscState.EXECUTE);
		hsql += " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(new Boolean(true));
		hsql += " and a.baseCustomsDeclaration.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (impExpType != null && !"".equals(impExpType)) {
			hsql += " and a.baseCustomsDeclaration.impExpType=? ";
			parameters.add(Integer.valueOf(impExpType));
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.commSerialNo,a.baseCustomsDeclaration.declarationDate";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport 判断是否进口类型，true为进口
	 * @param emsNo 手册号
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport, String emsNo) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select distinct a.customer from DzscCustomsDeclaration as a where  "
				+ " a.company.id=? ";
		parameters.add(CommonUtils.getCompany().getId());
		hsql += " and a.effective=? ";
		parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (isImport) {
			// hsql += " and a.impExpType in (?,?,?,?,?)";
			// parameters.add(ImpExpType.DIRECT_IMPORT);
			// parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			// parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			// parameters.add(ImpExpType.GENERAL_TRADE_IMPORT);
			// parameters.add(ImpExpType.EQUIPMENT_IMPORT);
			hsql += " and a.impExpFlag=? ";
			parameters.add(ImpExpFlag.IMPORT);
		} else {
			// hsql += " and a.impExpType in (?,?,?,?,?,?,?,?,?)";
			// parameters.add(ImpExpType.DIRECT_EXPORT);
			// parameters.add(ImpExpType.TRANSFER_FACTORY_EXPORT);
			// parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
			// parameters.add(ImpExpType.REWORK_EXPORT);
			// parameters.add(ImpExpType.REMIAN_MATERIAL_BACK_PORT);
			// parameters.add(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES);
			// parameters.add(ImpExpType.GENERAL_TRADE_EXPORT);
			// parameters.add(ImpExpType.EQUIPMENT_BACK_PORT);
			// parameters.add(ImpExpType.BACK_PORT_REPAIR);
			hsql += " and a.impExpFlag=? ";
			parameters.add(ImpExpFlag.IMPORT);
		}
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 核销单登记表
	 * 
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param emsNo 手册号
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findCancelAfterVerificationList(String emsNo, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a from DzscCustomsDeclarationCommInfo as a "
				+ " where a.baseCustomsDeclaration.company.id=? "
				+ " and a.baseCustomsDeclaration.impExpFlag=? "
				+ " and a.baseCustomsDeclaration.effective=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(ImpExpFlag.EXPORT);
		parameters.add(new Boolean(true));
		if (emsNo != null && !emsNo.trim().equals("")) {
			hsql += " and a.baseCustomsDeclaration.emsHeadH2k=? ";
			parameters.add(emsNo);
		}
		if (beginDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate>=? ";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.baseCustomsDeclaration.declarationDate<=? ";
			parameters.add(CommonUtils.getEndDate(endDate));
		}
		hsql += " order by a.baseCustomsDeclaration.customsDeclarationCode";
		// hsql += " group by
		// a.baseCustomsDeclaration.customsDeclarationCode,a.baseCustomsDeclaration.authorizeFile";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 成品、料件进出口报关清单明细
	 * 
	 * @param isMaterial 判断是否料件类型，true为料件
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param emsNo 手册号
	 * @return List list(0)是DzscBillListBeforeCommInfo型,报关清单归并前商品信息
	 *              list(1)是DzscCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findDzscBillListDeclaration(boolean isMaterial, String emsNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select a,b from DzscBillListBeforeCommInfo a,DzscCustomsDeclarationCommInfo b"
				+ " where a.afterComInfo.billList.id=b.baseCustomsDeclaration.billListId "
				+ " and a.afterComInfo.emsSerialNo=b.commSerialNo"
				+ " and a.afterComInfo.complex.code=b.complex.code"
				+ " and a.afterComInfo.billList.emsHeadH2k=? "
				+ " and a.afterComInfo.billList.company.id=? "
				+ " and b.baseCustomsDeclaration.company.id=?";
		parameters.add(emsNo);
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(CommonUtils.getCompany().getId());
		if (isMaterial) {// 如果是料件的话
			hql += " and a.afterComInfo.billList.impExpType in (?,?,?)";
			parameters.add(ImpExpType.DIRECT_IMPORT);
			parameters.add(ImpExpType.TRANSFER_FACTORY_IMPORT);
			parameters.add(ImpExpType.BACK_MATERIEL_EXPORT);
		} else {// 如果是成品
			hql += " and a.afterComInfo.billList.impExpType in (?,?,?)";
			parameters.add(ImpExpType.BACK_FACTORY_REWORK);
			parameters.add(ImpExpType.DIRECT_EXPORT);
			parameters.add(ImpExpType.REWORK_EXPORT);
		}
		if (beginDate != null) {
			hql += " and a.afterComInfo.billList.listDeclareDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.afterComInfo.billList.listDeclareDate<=?";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		hql += " order by a.afterComInfo.billList.listNo,"
				+ "a.afterComInfo.emsSerialNo";
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找所有被报关单调用的报关常用物料
	 * 
	 * @param isMaterial 判断是否料件类型，true为料件
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @param emsNo 手册号
	 * @return List 是materiel型，报关常用物料
	 */
	public List findAllMaterial(boolean isMaterial, String emsNo,
			Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hql = "select distinct a.materiel from DzscBillListBeforeCommInfo a "
				+ " where a.afterComInfo.billList.company.id=?"
				+ " and a.afterComInfo.billList.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		hql += " and a.afterComInfo.billList.materielProductFlag=?";
		if (isMaterial) {
			parameters.add(Integer.valueOf(MaterielType.MATERIEL));
		} else {
			parameters.add(Integer.valueOf(MaterielType.FINISHED_PRODUCT));
		}
		if (beginDate != null) {
			hql += " and a.afterComInfo.billList.listDeclareDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hql += " and a.afterComInfo.billList.listDeclareDate<=?";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		return this.find(hql, parameters.toArray());
	}

	/**
	 * 查找报关清单商品的申报数量
	 * 
	 * @param impExpFlag 物料标识
	 * @param impExpType 进出口类型
	 * @param tradeCodes 监管方式编码
	 * @param emsNo 手册号
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return List 存放了materiel的料号和对应的申报数量
	 */
	public List findMaterialTotalAmount(Integer impExpFlag, Integer impExpType,
			String[] tradeCodes, String emsNo, Date beginDate, Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select a.materiel.ptNo, sum(a.declaredAmount) "
				+ " from  DzscBillListBeforeCommInfo as a "
				+ " where a.afterComInfo.billList.company.id=?"
				+ " and a.afterComInfo.billList.emsHeadH2k=?";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		if (impExpFlag != null) {
			hsql += " and a.afterComInfo.billList.impExpFlag=?";
			parameters.add(impExpFlag);
		}
		if (impExpType != null) {
			hsql += " and a.afterComInfo.billList.impExpType=?";
			parameters.add(impExpType);
		}
		if (beginDate != null) {
			hsql += " and a.afterComInfo.billList.listDeclareDate>=?";
			parameters.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			hsql += " and a.afterComInfo.billList.listDeclareDate<=?";
			parameters.add(CommonUtils.getBeginDate(endDate));
		}
		if (tradeCodes != null && tradeCodes.length > 0) {
			hsql += " and ( ";
			for (int i = 0; i < tradeCodes.length; i++) {
				if (i == tradeCodes.length - 1) {
					hsql += " a.afterComInfo.billList.tradeMode.code=? ";
				} else {
					hsql += " a.afterComInfo.billList.tradeMode.code=? or ";
				}
				parameters.add(tradeCodes[i]);
			}
			hsql += " ) ";
		}
		hsql += " group by a.materiel.ptNo";
		return this.find(hsql, parameters.toArray());
	}

	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findCustomsEnvelopCommInfoCount(String emsNo, Date beginDate,
			Date endDate) {
		List<Object> parameters = new ArrayList<Object>();
		String hsql = " select b.seqNum,b.complex.code,sum(b.ownerQuantity) "
				+ " from CustomsEnvelopCommodityInfo as b"
				+ " where b.customsEnvelopBill.company.id=? "
				+ " and b.customsEnvelopBill.emsNo=? ";
		parameters.add(CommonUtils.getCompany().getId());
		parameters.add(emsNo);
		hsql += " group by b.seqNum,b.complex.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}
	/**
	 * 抓取关封数量
	 * 
	 * @param emsNo
	 *            手册号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 存放关封管理明细中的帐册序号、海关商品编码、本厂数量(申请数量)
	 */
	public List findFptAppItemCount(String emsNo, Date beginDate,
			Date endDate,boolean isMaterial) {
//		List<Object> parameters = new ArrayList<Object>();
//		String hsql = " select b.seqNum,b.complex.code,sum(b.ownerQuantity) "
//				+ " from CustomsEnvelopCommodityInfo as b"
//				+ " where b.customsEnvelopBill.company.id=? "
//				+ " and b.customsEnvelopBill.emsNo=? ";
//		parameters.add(CommonUtils.getCompany().getId());
//		parameters.add(emsNo);
//		hsql += " group by b.seqNum,b.complex.code ";
//		List list = this.find(hsql, parameters.toArray());
//		return list;
		List<Object> parameters = new ArrayList<Object>();
		String hsql = "";
		if (isMaterial) {// 料件
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.inEmsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.IN);
		} else {// 成品
			hsql = " select b.trNo,b.codeTs.code,sum(b.qty) "
					+ " from FptAppItem as b"
					+ " where b.fptAppHead.company.id=? and b.fptAppHead.emsNo=? "
					+ " and b.fptAppHead.inOutFlag=? ";
			parameters.add(CommonUtils.getCompany().getId());
			parameters.add(emsNo);
			parameters.add(FptInOutFlag.OUT);
		}
		hsql += " and b.fptAppHead.declareState=? ";
		parameters.add(DeclareState.PROCESS_EXE);
		hsql += " and b.inOutFlag=b.fptAppHead.inOutFlag ";
		hsql += " group by b.trNo,b.codeTs.code ";
		List list = this.find(hsql, parameters.toArray());
		return list;
	}
}
