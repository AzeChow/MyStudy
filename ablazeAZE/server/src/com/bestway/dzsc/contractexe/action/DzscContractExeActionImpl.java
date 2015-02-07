/*
 * Created on 2005-3-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.action;

import java.util.List;

import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncActionImpl;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.dao.DzscContractExeDao;
import com.bestway.dzsc.contractexe.dao.DzscImpExpRequestDao;
import com.bestway.dzsc.contractexe.entity.DzscContractExeInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.TempDzscImpExpCommodityInfo;
import com.bestway.dzsc.contractexe.logic.DzscContractExeLogic;
import com.bestway.dzsc.contractexe.logic.DzscImpExpRequestLogic;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * com.bestway.dzsc.contractexe.action.DzscContractExeActionImpl
 * 
 * @author Administrator
 */
public class DzscContractExeActionImpl extends BaseEncActionImpl implements
		DzscContractExeAction {

	private DzscContractExeDao dzscContractExeDao = null;

	private DzscImpExpRequestLogic dzscImpExpRequestLogic = null;

	private DzscImpExpRequestDao dzscImpExpRequestDao = null;

	private DzscContractExeLogic dzscContractExeLogic = null;

	/**
	 * 查询没有报关的手册通关备案料件
	 * 
	 * @param request 请求控制
	 * @param baseCustomsDeclaration 报关单表头
	 * @return List 存放了通关备案料件的一些资料
	 */
	public List findDzscMaterialInfo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return dzscContractExeDao.findDzscMaterialInfo(baseCustomsDeclaration);
	}

	/**
	 * 查询没有报关的手册通关备案成品
	 * 
	 * @param request 请求控制
	 * @param baseCustomsDeclaration 报关单表头
	 * @return List 存放了通关备案成品的一些资料
	 */
	public List findDzscProductInfo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return dzscContractExeDao.findDzscProductInfo(baseCustomsDeclaration);
	}
	
	/**
	 * 返回报关单中的最大商品序号
	 * 
	 * @param request 请求控制
	 * @param head 报关单表头
	 * @return String 最大商品序号
	 */
	public String getNum(Request request, DzscCustomsDeclaration head) {
		return dzscContractExeDao.getNum(head);
	}

	/**
	 * 获取dzscImpExpRequestDao
	 * 
	 * @return dzscImpExpRequestDao
	 */
	public DzscImpExpRequestDao getDzscImpExpRequestDao() {
		return dzscImpExpRequestDao;
	}

	/**
	 * 设置dzscImpExpRequestDao
	 * 
	 * @param dzscImpExpRequestDao
	 */
	public void setDzscImpExpRequestDao(
			DzscImpExpRequestDao dzscImpExpRequestDao) {
		this.dzscImpExpRequestDao = dzscImpExpRequestDao;
	}

	/**
	 * 返回报关单中的商品信息
	 * 
	 * @param request 请求控制
	 * @param complexNo 商品编码
	 * @param info 报关单表头
	 * @return List 是DzscCustomsDeclarationCommInfo型，报关单物料
	 */
	public List getRepeatComplex(Request request, String complexNo,
			DzscCustomsDeclaration info) {
		return dzscContractExeDao.getRepeatComplex(complexNo, info);
	}

	
	/**
	 * 获取dzscContractExeDao
	 * 
	 * @return dzscContractExeDao
	 */
	public DzscContractExeDao getDzscContractExeDao() {
		return dzscContractExeDao;
	}

	/**
	 * 获取dzscImpExpRequestLogic
	 * 
	 * @return dzscImpExpRequestLogic
	 */
	public DzscImpExpRequestLogic getDzscImpExpRequestLogic() {
		return dzscImpExpRequestLogic;
	}

	/**
	 * 设置dzscImpExpRequestLogic
	 * 
	 * @param dzscImpExpRequestLogic
	 */
	public void setDzscImpExpRequestLogic(
			DzscImpExpRequestLogic dzscImpExpRequestLogic) {
		this.dzscImpExpRequestLogic = dzscImpExpRequestLogic;
	}

	/**
	 * 设置dzscContractExeDao
	 * 
	 * @param contractExeDao 
	 */
	public void setDzscContractExeDao(DzscContractExeDao contractExeDao) {
		this.dzscContractExeDao = contractExeDao;
	}

	/**
	 * 分页查找物料来自过滤Dzsc申请单明细
	 * 
	 * @param request 请求控制
	 * @param materielType 物料类别
	 * @param impExpRequestBill 申请单表头
	 * @param index 数据的开始下表
     * @param length 数据的长度
     * @param property 属性
     * @param value 属性的值
     * @param isLike 用“like”还是用“＝”，当为true是用“like”
	 * @return List 是TempMateriel型，临时物料
	 */
	public List findMaterielByDzscRequestBill(Request request,
			String materielType, ImpExpRequestBill impExpRequestBill,
			int index, int length, String property, Object value, Boolean isLike) {
		return this.dzscImpExpRequestLogic.findMaterielByDzscRequestBill(
				materielType, impExpRequestBill, index, length, property,
				value, isLike);
	}

	/**
	 * 进出口申请单--->电子备案报关单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isProduct
	 * 是成品还是料件
	 * 
	 * @param request 请求控制
	 * @param dzscCustomsDeclaration 报关单表头
	 * @param parentList 临时申请单表头
	 * @param dataSource 临时的报关单商品信息
	 * @param isProduct 判断是否成品，true时为成品
	 * @param netWeightParameter 净重参数
	 * @return list.get(0)==DzscCustomsDeclaration
	 *         list.get(1)==进出口申请单生成报关单时,修改进出申请单的生成报关单字段为true的list
	 */
	public List makeDzscCustomsDeclaration(Request request,
			DzscCustomsDeclaration dzscCustomsDeclaration,
			List<TempImpExpRequestBill> parentList,
			List<TempDzscImpExpCommodityInfo> dataSource, boolean isProduct,
			Double netWeightParameter) {
		return this.dzscImpExpRequestLogic.makeDzscCustomsDeclaration(
				dzscCustomsDeclaration, parentList, dataSource, isProduct,
				netWeightParameter);
	}

	/**
     * 查找通关备案料件来自进出口申请单 料号
     * 
	 * @param request 请求控制
     * @param ptNo 料号
     * @param dzscEmsPorHeadId 手册通关备案表头Id
     * @return List 是DzscEmsImgBill型，通关备案料件
     */
	public List findDzscEmsImgBillByPtNo(Request request, String ptNo,
			String dzscEmsPorHeadId) {
		return this.dzscImpExpRequestDao.findDzscEmsImgBillByPtNo(ptNo,
				dzscEmsPorHeadId);
	}
	
	 /**
     * 获取料件，成品对应的归并序号
     * @param ptNo
     * @param materielType
     * @return
     */
    public Integer findTenSeqNum(Request request, String ptNo,String materielType){
    	return this.dzscImpExpRequestDao.findTenSeqNum( ptNo, materielType);
    }

	/**
     * 查找通关备案成品来自进出口申请单 料号
     * 
	 * @param request 请求控制
     * @param ptNo 料号
     * @param dzscEmsPorHeadId 手册通关备案表头Id
     * @return List 是DzscEmsImgBill型，通关备案料件
     */
	public List findDzscEmsExgBillByPtNo(Request request, String ptNo,
			String dzscEmsPorHeadId) {
		return this.dzscImpExpRequestDao.findDzscEmsExgBillByPtNo(ptNo,
				dzscEmsPorHeadId);
	}

	/**
	 * 获得进出口商品信息来自父对象
	 * 
	 * @param request 请求控制
	 * @param parentList 临时申请单表头
	 * @return List 是TempDzscImpExpCommodityInfo型，临时的报关单商品信息
	 */
	public List findTempDzscImpExpCommodityInfoByParent(Request request,
			List parentList) {
		return this.dzscImpExpRequestLogic
				.findTempDzscImpExpCommodityInfoByParent(parentList);
	}

	/**
	 * 获取dzscContractExeLogic
	 * 
	 * @return dzscContractExeLogic
	 */
	public DzscContractExeLogic getDzscContractExeLogic() {
		return dzscContractExeLogic;
	}

	/**
	 * 设置dzscContractExeLogic
	 * 
	 * @param dzscContractExeLogic
	 */
	public void setDzscContractExeLogic(DzscContractExeLogic dzscContractExeLogic) {
		this.dzscContractExeLogic = dzscContractExeLogic;
	}

	/**
	 * 获取其它参数设置中的重量参数
	 * 
	 * @param request 请求控制
	 * @return Double 重量参数
	 */
	public Double findweightpara(Request request) {
		return this.dzscContractExeDao.findweightpara();
	}

	/**
	 * 内部商品新增报关单表体
	 * 
	 * @param request 请求控制
	 * @param commInfo 报关单商品
	 * @param customsDeclaration 报关单表头
	 * @param exgbill 手册通过备案成品
	 * @param imgbill 手册通过备案料件
	 */
	public void saveCustomsinfoFromBill(Request request,
			BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, DzscEmsExgBill exgbill,
			DzscEmsImgBill imgbill) {
		dzscContractExeLogic.saveCustomsinfoFromBill(commInfo,
				customsDeclaration, exgbill, imgbill);
	}

	/**
	 * 抓取报关单某项商品的合同定量,合同余量,当前余量
	 * 
	 * @param request 请求控制
	 * @param isMaterial 判断是否料件，true时为料件
	 * @param impExpType 进出口类型
	 * @param tradeCode 贸易方式编码
	 * @param contract 手册通关备案表头
	 * @param seqNum 凭证序号
	 * @return  DzscContractExeInfo 报关单某项商品的合同定量,合同余量,当前余量
	 */
	public DzscContractExeInfo findDzscContractExeInfo(Request request,
			boolean isMaterial, int impExpType, String tradeCode,
			DzscEmsPorHead contract, Integer seqNum) {
		return this.dzscContractExeLogic.findDzscContractExeInfo(isMaterial,
				impExpType, tradeCode, contract, seqNum);
	}
	
	/**
	 * 通过归并序号获取通关手册料件
	 * @param request
	 * @param dzscEmsPorHead
	 * @param temSeqNum
	 */
	public DzscEmsImgBill findDzscEmsImgBillBytenSeqNum(Request request, DzscEmsPorHead dzscEmsPorHead,Integer temSeqNum){
		return this.dzscContractExeDao.findDzscEmsImgBillBytenSeqNum( dzscEmsPorHead, temSeqNum);
	}
	
	/**
	 * 通过归并序号获取通关手册料件
	 * @param request
	 * @param dzscEmsPorHead
	 * @param temSeqNum
	 */
	public DzscEmsExgBill findDzscEmsExgBillBytenSeqNum(Request request, DzscEmsPorHead dzscEmsPorHead,Integer temSeqNum){
		return this.dzscContractExeDao.findDzscEmsExgBillBytenSeqNum( dzscEmsPorHead, temSeqNum);
	}
	
	/**
	 * 统计手册合同料件数量，金额余额
	 * @param request
	 * @param imgBill
	 * @return
	 */
	public Double[] processImgRemainContractMountAndMoney(Request request,
			DzscEmsImgBill imgBill){
		return this.dzscImpExpRequestLogic.processImgRemainContractMountAndMoney(imgBill);
	}

	/**
	 * 统计手册合同成品数量，金额余额
	 * @param request
	 * @param imgBill
	 * @return
	 */
	public Double[] processImgRemainContractMountAndMoney(Request request,
			DzscEmsExgBill exgBill){
		return this.dzscImpExpRequestLogic.processExgRemainContractMountAndMoney(exgBill);
	}
	
	/**
	 * 申请单转报关单
	 * @param request
	 * @param dzscCustomsDeclaration
	 * @param temps
	 * @param isProduct
	 */
	public String makeDzscCustomsDeclarationFromTemp(Request request,
			DzscCustomsDeclaration dzscCustomsDeclaration,
			List<TempDzscImpExpCommodityInfo> temps, boolean isProduct,boolean isNewRecord){
		return this.dzscImpExpRequestLogic.makeDzscCustomsDeclarationFromTemp(dzscCustomsDeclaration,temps,isProduct, isNewRecord);
	}
	
}
