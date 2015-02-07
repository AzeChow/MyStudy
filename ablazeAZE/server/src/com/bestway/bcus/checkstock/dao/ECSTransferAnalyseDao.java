package com.bestway.bcus.checkstock.dao;

import java.util.List;

import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.common.BaseDao;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.fpt.entity.FptBillItem;

/**
 * 结转分析数据操作类
 * @author xc
 * @version 1.0
 * @since 2013-09-05
 */
@SuppressWarnings("unchecked")
public class ECSTransferAnalyseDao extends BaseDao {

	
	/**
	 * 查询深加工结转收货数据
	 * @param section
	 * @return
	 */
	
	public List<FptBillItem> findInFptBillItem(ECSSection section){
		 
		String hql= " select a from FptBillItem a " +
				" where a.fptBillHead.receiveDate>=?  and a.fptBillHead.receiveDate<=? " +
				" and a.fptBillHead.appState=? and a.billSort=a.fptBillHead.billSort "+ 
				" and a.fptBillHead.billSort=? and a.company.id=? ";
		return super.find(hql, new Object[]{CommonUtils.getBeginDate(section.getBeginDate()),CommonUtils.getEndDate(section.getEndDate()),
							DeclareState.PROCESS_EXE,FptInOutFlag.IN,CommonUtils.getCompany().getId()});
	}
	/**
	 * 查询深加工结转发货数据
	 * @param section
	 * @return
	 */
	public List<FptBillItem> findOutFptBillItem(ECSSection section){
		String hql = "select a from FptBillItem a " 
					+ " where a.fptBillHead.issueDate>=? and a.fptBillHead.issueDate<=? " 
					+ " and a.fptBillHead.appState=? and a.billSort=a.fptBillHead.billSort "
					+ " and a.fptBillHead.billSort= ? "
					+ " and a.company.id =? ";
		return super.find(hql, new Object[]{CommonUtils.getBeginDate(section.getBeginDate()),CommonUtils.getEndDate(section.getEndDate()),
				DeclareState.PROCESS_EXE,FptInOutFlag.OUT,CommonUtils.getCompany().getId()});
	}
	/**
	 * 获取结转进口报关单项目
	 * @param section
	 * @return
	 */
	public List<CustomsDeclarationCommInfo> findImpFptBcsCustomsDeclarationCommInfo(ECSSection section){
		String hql= " select a from CustomsDeclarationCommInfo a" +
				" where a.baseCustomsDeclaration.impExpDate>=? and a.baseCustomsDeclaration.impExpDate<=? " +
				" and a.baseCustomsDeclaration.effective=? " +
				" and a.baseCustomsDeclaration.impExpFlag=? " +
				" and a.baseCustomsDeclaration.impExpType=? " +
				" and a.company.id=? ";
		return super.find(hql, new Object[]{CommonUtils.getBeginDate(section.getBeginDate()),
						CommonUtils.getEndDate(section.getEndDate()),Boolean.TRUE,
						ImpExpFlag.IMPORT,ImpExpType.TRANSFER_FACTORY_IMPORT,CommonUtils.getCompany().getId()});
	}
	
	/**
	 * 获取结转出口报关单项目
	 * @param section
	 * @return
	 */
	public List<CustomsDeclarationCommInfo> findExpFptCustomsDeclarationCommInfo(ECSSection section){
		String hql= " select a from CustomsDeclarationCommInfo a " +
				" where a.baseCustomsDeclaration.impExpDate>=? and a.baseCustomsDeclaration.impExpDate<=? " + 
				" and a.baseCustomsDeclaration.effective=?" +
				" and a.baseCustomsDeclaration.impExpFlag=? " +
				" and a.baseCustomsDeclaration.impExpType=? " +
				" and a.company.id =? ";
		
		return super.find(hql, new Object[]{CommonUtils.getBeginDate(section.getBeginDate()),
				CommonUtils.getEndDate(section.getEndDate()),Boolean.TRUE,
				ImpExpFlag.EXPORT,ImpExpType.TRANSFER_FACTORY_EXPORT,CommonUtils.getCompany().getId()});
	}
	
}
