/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsFromMateriel;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.materialbase.entity.UnitCollate;
import com.bestway.customs.common.dao.BaseEncDao;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * com.bestway.bcs.contractexe.dao.ContractExeDao
 * 
 * @author Administrator
 */
public class ContractExeDao extends BaseEncDao {

	/**
	 * 查询没有报关的合同料件
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List 存放合同料件的一些资料
	 */
	public List findContractMaterialInfo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = " select a.seqNum,a.complex,a.unit,a.complex.firstUnit,"
				+ " a.name,a.spec,a.credenceNo,a.declarePrice,a.complex.secondUnit,a.detailNote,a.legalUnitGene,a.legalUnit2Gene from "
				+ " ContractImg as a "
				+ " left outer join a.unit "
				+ " left outer join a.complex"
				+ " left outer join a.complex.firstUnit"
				+ " left outer join a.complex.secondUnit"
				+ " where  a.contract.emsNo=? "
				+ " and a.contract.company.id=? and  a.contract.isCancel=? and a.contract.declareState = ?"
				+ " and (a.isForbid<> ? or a.isForbid=null)"
				+ " order by a.seqNum";
		return this.find(
				hql,
				new Object[] {
						((BcsCustomsDeclaration) baseCustomsDeclaration)
								.getEmsHeadH2k(),
						CommonUtils.getCompany().getId(), new Boolean(false),
						DeclareState.PROCESS_EXE, new Boolean(true) });
	}

	/**
	 * 查询没有报关的合同成品
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @return List 存放合同成品的一些资料
	 */
	public List findContractProductInfo(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		String hql = " select a.seqNum,a.complex,a.unit,a.complex.firstUnit,a.name,"
				+ " a.spec,a.credenceNo,a.unitPrice,a.complex.secondUnit,a.id,a.legalUnitGene,a.legalUnit2Gene,a.processTotalPrice from "
				+ " ContractExg as a "
				+ " left outer join  a.unit "
				+ " left outer join  a.complex"
				+ " left outer join  a.complex.firstUnit"
				+ " left outer join  a.complex.secondUnit"
				+ " where  a.contract.emsNo=? "
				+ " and a.contract.company.id=? and  a.contract.isCancel=? and a.contract.declareState = ?"
				+ " and (a.isForbid<> ? or a.isForbid=null)"
				+ " order by a.seqNum";
		return this.find(
				hql,
				new Object[] {
						((BcsCustomsDeclaration) baseCustomsDeclaration)
								.getEmsHeadH2k(),
						CommonUtils.getCompany().getId(), new Boolean(false),
						DeclareState.PROCESS_EXE, new Boolean(true) });
	}

	/**
	 * 查找其它参数设置
	 * 
	 * @return CompanyOther 其它参数设置
	 */
	public CompanyOther getSysCompanyOther() {
		List list = this.find(
				"select a from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list != null && list.size() > 0) {
			return (CompanyOther) list.get(0);
		}
		return null;
	}

	/**
	 * 查找其它参数设置里的重量参数
	 * 
	 * @return Double 重量参数
	 */
	public Double findweightpara() {
		List list = this.find(
				"select a.weightPara from CompanyOther a where a.company.id=?",
				new Object[] { CommonUtils.getCompany().getId() });
		if (list.get(0) != null) {
			return (Double) list.get(0);
		}
		return Double.valueOf(0);
	}

	/**
	 * 保存合同备案物料与对应的第一层物料的资料
	 * 
	 * @param obj
	 *            合同备案物料与对应的第一层物料的资料
	 */
	public void SaveCustomsFromMateriel(BcsCustomsFromMateriel obj) {
		this.saveOrUpdate(obj);
	}

	/**
	 * 查找返回报关与工厂的折算比例
	 * 
	 * @param isProduct成品还是料件
	 * @param newTemp
	 * @return
	 */
	public Double findUnitConvert(boolean isProduct,
			TempBcsImpExpCommodityInfo newTemp) {
		String hsql = "select a.unitConvert from BcsInnerMerge a where a.materiel.ptNo=? and a.isUsing = ? ";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(newTemp.getImpExpCommodityInfo().getMateriel().getPtNo());
		paramters.add(Boolean.TRUE);
		List list = this.find(hsql, paramters.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0) != null ? (Double) list.get(0) : 0.0;
		}
		return 0.0;
	}

	public String findComUnit(boolean isProduct,
			TempBcsImpExpCommodityInfo newTemp) {
		String hsql = "select a.bcsTenInnerMerge.comUnit.name from BcsInnerMerge a where a.materiel.ptNo = ? and a.isUsing = ?";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(newTemp.getImpExpCommodityInfo().getMateriel().getPtNo());
		paramters.add(Boolean.TRUE);
		List list = this.find(hsql, paramters.toArray());
		if (list != null && list.size() > 0) {
			return (String) list.get(0);
		}
		return null;
	}

	/**
	 * 查找返回报关与工厂的折算比例
	 * 
	 * @param isProduct成品还是料件
	 * @param newTemp
	 * @return
	 */
	public Double findUnitConvert(Integer credenceNo,
			TempBcsImpExpCommodityInfo newTemp, Boolean is) {
		// String hsql =
		// "select a.unitConvert from BcsInnerMerge a where a.materiel.ptNo=? and a.bcsTenInnerMerge.seqNum=?";

		StringBuffer sb = new StringBuffer(
				"select a.unitConvert from BcsInnerMerge a,");
		if (is) {
			sb.append("BcsDictPorExg b ");
		} else {
			sb.append(" BcsDictPorImg b ");
		}
		sb.append(" where a.bcsTenInnerMerge.seqNum = b.innerMergeSeqNum ");
		sb.append(" and b.seqNum = ? ");
		sb.append(" and a.materiel.ptNo = ? ");
		sb.append(" and b.dictPorHead.declareState=? ");
		sb.append(" and a.company.id=? ");
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(credenceNo);
		paramters.add(newTemp.getImpExpCommodityInfo().getMateriel().getPtNo());
		paramters.add(DeclareState.PROCESS_EXE);// 正在执行的备案资料库
		paramters.add(CommonUtils.getCompany().getId());

		System.out.println("----------------------" + sb.toString());
		List list = this.find(sb.toString(), paramters.toArray());
		if (list != null && list.size() > 0) {
			return list.get(0) != null ? (Double) list.get(0) : 0.0;
		}
		return 0.0;
	}

	/**
	 * 根据报告单表头获取所有报关单表体
	 * 
	 * @param bcsCustomsDeclaration
	 * @return
	 */
	public List getBcsCustomsDeclarationCommInfo(
			BcsCustomsDeclaration bcsCustomsDeclaration) {

		String hsql = " select a from BcsCustomsDeclarationCommInfo a where a.baseCustomsDeclaration.serialNumber=? "
				+ "and a.baseCustomsDeclaration.emsHeadH2k= ? and a.baseCustomsDeclaration.impExpFlag=? ";

		List<Object> paramters = new ArrayList<Object>();

		System.out.println("bcsCustomsDeclaration.serialNumber()="
				+ bcsCustomsDeclaration.getSerialNumber());

		paramters.add(bcsCustomsDeclaration.getSerialNumber());

		paramters.add(bcsCustomsDeclaration.getEmsHeadH2k());

		paramters.add(bcsCustomsDeclaration.getImpExpFlag());

		List list = this.find(hsql, paramters.toArray());

		System.out.println("hsql=" + hsql);

		return list;
	}

	public List findUnitPrice(Integer seqNum,
			BcsCustomsDeclaration bcsCustomsDeclaration, boolean isProduct) {
		String tableName = "";
		if (isProduct)
			tableName = "ContractExg";
		else
			tableName = "ContractImg";
		String hsql = "select a.declarePrice from " + tableName
				+ " a where a.seqNum=? and a.contract.emsNo=?";
		List<Object> paramters = new ArrayList<Object>();
		paramters.add(seqNum);
		paramters.add(bcsCustomsDeclaration.getEmsHeadH2k());
		System.out.println("hsql=" + hsql);
		List list = this.find(hsql, paramters.toArray());
		return list;
	}

	public Map findAllUnitRateMap() {
		Map map = new HashMap();
		List list = this.find(
				"select a from UnitCollate a where  a.company.id = ?",
				new Object[] { CommonUtils.getCompany().getId() });
		for (int k = 0; k < list.size(); k++) {
			UnitCollate uc = (UnitCollate) list.get(k);
			String u = uc.getUnitName();
			String u1 = uc.getUnitName1();
			Double rate = uc.getUnitRate();
			String key = u + "+" + u1;
			map.put(key, rate);
		}
		return map;
	}
}
