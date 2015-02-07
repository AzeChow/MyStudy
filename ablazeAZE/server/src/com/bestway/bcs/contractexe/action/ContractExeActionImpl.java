/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractexe.action;

import java.util.Date;
import java.util.List;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.dao.BcsImpExpRequestDao;
import com.bestway.bcs.contractexe.dao.ContractExeDao;
import com.bestway.bcs.contractexe.entity.BcsContractExeInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsFromMateriel;
import com.bestway.bcs.contractexe.entity.TempBcsImpExpCommodityInfo;
import com.bestway.bcs.contractexe.logic.BcsImpExpRequestLogic;
import com.bestway.bcs.contractexe.logic.ContractExeLogic;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.common.Request;
import com.bestway.customs.common.action.BaseEncActionImpl;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * com.bestway.bcs.contractexe.action.ContractExeActionImpl
 * 
 * @author refdom
 * 
 */
@SuppressWarnings("unchecked")
public class ContractExeActionImpl extends BaseEncActionImpl implements
		ContractExeAction {
	private BcsImpExpRequestDao bcsImpExpRequestDao = null;

	private BcsImpExpRequestLogic bcsImpExpRequestLogic = null;

	private ContractExeLogic contractExeLogic = null;

	private ContractExeDao contractExeDao = null;

	/**
	 * 获取bcsImpExpRequestDao
	 * 
	 * @return Returns the bcsImpExpRequestDao.
	 */
	public BcsImpExpRequestDao getBcsImpExpRequestDao() {
		return bcsImpExpRequestDao;
	}

	/**
	 * 设置bcsImpExpRequestDao
	 * 
	 * @param bcsImpExpRequestDao
	 *            The bcsImpExpRequestDao to set.
	 */
	public void setBcsImpExpRequestDao(BcsImpExpRequestDao bcsImpExpRequestDao) {
		this.bcsImpExpRequestDao = bcsImpExpRequestDao;
	}

	/**
	 * 获取bcsImpExpRequestLogic
	 * 
	 * @return Returns the bcsImpExpRequestLogic.
	 */
	public BcsImpExpRequestLogic getBcsImpExpRequestLogic() {
		return bcsImpExpRequestLogic;
	}

	/**
	 * 设置bcsImpExpRequestLogic
	 * 
	 * @param bcsImpExpRequestLogic
	 *            The bcsImpExpRequestLogic to set.
	 */
	public void setBcsImpExpRequestLogic(
			BcsImpExpRequestLogic bcsImpExpRequestLogic) {
		this.bcsImpExpRequestLogic = bcsImpExpRequestLogic;
	}

	/**
	 * 查找在物料与报关对应表对应在报关商品资料存在，且正在执行的合同备案料件
	 * 
	 * @param ptNo
	 *            料号
	 * @param contractId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同备案料件
	 */
	public List findContractImgByPtNo(Request request, String ptNo,
			String contractId) {
		return bcsImpExpRequestDao.findContractImgByPtNo(ptNo, contractId);
	}

	/**
	 * 查找在物料与报关对应表对应在报关商品资料存在，且正在执行的合同备案成品
	 * 
	 * @param ptNo
	 *            料号
	 * @param contractId
	 *            合同表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByPtNo(Request request, String ptNo,
			String contractId) {
		return bcsImpExpRequestDao.findContractExgByPtNo(ptNo, contractId);
	}

	/**
	 * 分页查找物料来自过滤纸质手册申请单明细
	 * 
	 * @param materielType
	 *            物料类别
	 * @param impExpRequestBill
	 *            申请单物料
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是型，
	 */
	public List findMaterielByBcsRequestBill(Request request,
			String materielType, ImpExpRequestBill impExpRequestBill,
			int index, int length, String property, Object value,
			Boolean isLike, boolean isFilter) {
		return bcsImpExpRequestLogic.findMaterielByBcsRequestBill(materielType,
				impExpRequestBill, index, length, property, value, isLike,
				isFilter);
	}
	/**
	 * 分页查找物料来自过滤纸质手册申请单明细(联胜)
	 * 
	 * @param materielType
	 *            物料类别
	 * @param impExpRequestBill
	 *            申请单物料
	 * @param index
	 *            开始数据下标
	 * @param length
	 *            数据长度
	 * @param property
	 *            对应表里的属性
	 * @param value
	 *            属性的值
	 * @param isLike
	 *            property在“where”里的判断是用“like”还是“＝”，true用“like”
	 * @return List 是型，
	 */
	public List findMaterielByBcsRequestBillLS(Request request,
			String materielType, ImpExpRequestBill impExpRequestBill,
			int index, int length, String property, Object value,
			Boolean isLike, boolean isFilter) {
		return bcsImpExpRequestLogic.findMaterielByBcsRequestBillLS(materielType,
				impExpRequestBill, index, length, property, value, isLike,
				isFilter);
	}
	/**
	 * 进出口申请单--->纸质手册报关单返回进出申请单已转列表，isNewRecord 是代表生成新的清单还是追加到原有的清单 isProduct
	 * 是成品还是料件
	 * 
	 * @param bcsCustomsDeclaration
	 *            报关单表头
	 * @param parentList
	 *            临时的进出口申请单表头
	 * @param dataSource
	 *            临时的进出口申请单表体资料
	 * @param isProduct
	 *            成品判断，true位成品
	 * @param netWeightParameter
	 *            净重参数
	 * @return List list.get(0)==BcsCustomsDeclaration
	 *         list.get(1)==进出口申请单生成报关单时,修改进出申请单的生成报关单字段为true的list
	 */
	
	public List makeBcsCustomsDeclaration(Request request,
			BcsCustomsDeclaration bcsCustomsDeclaration,
			List<TempImpExpRequestBill> parentList,
			List<TempBcsImpExpCommodityInfo> dataSource, boolean isProduct,
			Double netWeightParameter, boolean isPrice) {
		return bcsImpExpRequestLogic.makeBcsCustomsDeclaration(
				bcsCustomsDeclaration, parentList, dataSource, isProduct,
				netWeightParameter, isPrice);
	}
		

	/**
	 * 检查是否有归并
	 * 
	 * @param contract
	 * @param commInfoList
	 * @param isProduct
	 * @param tradeCode
	 * @param impExpType
	 * @return
	 */
	public List checkExistTempBcsImpExpCommodityInfoBcsTenInnerMergeByParent(Request request,
			Contract contract, List commInfoList){
		return bcsImpExpRequestLogic.checkExistTempBcsImpExpCommodityInfoBcsTenInnerMergeByParent(
				contract, commInfoList);
	}
	
	/**
	 * 检查临时的进出口申请单表体资料
	 * @param request
	 * @param contract
	 * @param commInfoList
	 * @return
	 */
	public List checkTempBcsImpExpCommodityInfo(Request request,
			Contract contract, List commInfoList){
		return bcsImpExpRequestLogic.checkTempBcsImpExpCommodityInfo(
				contract, commInfoList);
	}
	/**
	 * 获得进出口申请单商品信息来自父对象
	 * 
	 * @param emsNo
	 *            帐册编号
	 * @param parentList
	 *            临时的申请单表头
	 * @return List 是TempBcsImpExpCommodityInfo型，临时的进出口申请单表体资料
	 */
	public List findTempBcsImpExpCommodityInfoByParent(Request request,
			Contract contract, List commInfoList, boolean isProduct,String tradeCode,Integer impExpType) {
		return bcsImpExpRequestLogic.findTempBcsImpExpCommodityInfoByParent(
				contract, commInfoList, isProduct, tradeCode, impExpType);
	}
	
	
	/**
	 * 获得进出口申请单数据来来自选定用进出口类型，且生效、存在未转关封单据的商品 的单据 ATC 代表 apply to customs 报关清单
	 * 
	 * @param impExpType
	 *            进出口类型
	 * @return 取得临时的进出口申请单据信息
	 */
	public List findTempImpExpRequestBillByImpExpTypeToATC(Request request,
			List parentList) {
		return bcsImpExpRequestLogic
				.findTempImpExpRequestBillByImpExpTypeToATC(parentList);
	}

	/**
	 * 获取contractExeLogic
	 * 
	 * @return Returns the contractExeLogic.
	 */
	public ContractExeLogic getContractExeLogic() {
		return contractExeLogic;
	}

	/**
	 * 设置contractExeLogic
	 * 
	 * @param contractExeLogic
	 *            The contractExeLogic to set.
	 */
	public void setContractExeLogic(ContractExeLogic contractExeLogic) {
		this.contractExeLogic = contractExeLogic;
	}

	/**
	 * 查找其它参数设置里的重量参数
	 * 
	 * @return Double 重量参数
	 */
	public Double findweightpara(Request request) {
		return contractExeDao.findweightpara();
	}

	/**
	 * 获取contractExeDao
	 * 
	 * @return Returns the contractExeDao.
	 */
	public ContractExeDao getContractExeDao() {
		return contractExeDao;
	}

	/**
	 * 设置contractExeDao
	 * 
	 * @param contractExeDao
	 *            The contractExeDao to set.
	 */
	public void setContractExeDao(ContractExeDao contractExeDao) {
		this.contractExeDao = contractExeDao;
	}

	/**
	 * 进出口报关单－－商品信息－－内部商品新增报关单表体
	 * 
	 * @param commInfo
	 *            报关单商品信息
	 * @param customsDeclaration
	 *            报关单表头
	 * @param exgbill
	 *            合同备案成品
	 * @param imgbill
	 *            合同备案料件
	 */
	public BaseCustomsDeclarationCommInfo saveCustomsinfoFromBill(
			Request request, BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, ContractExg exgbill,
			ContractImg imgbill) {
		contractExeLogic.saveCustomsinfoFromBill(commInfo, customsDeclaration,
				exgbill, imgbill);
		return commInfo;
	}

	/**
	 * 保存合同备案物料与对应的第一层物料的资料
	 * 
	 * @param obj
	 *            合同备案物料与对应的第一层物料的资料
	 */
	public void SaveCustomsFromMateriel(Request request,
			BcsCustomsFromMateriel obj) {
		contractExeDao.SaveCustomsFromMateriel(obj);
	}

	/**
	 * 抓取报关单某项商品的合同定量,合同余量,当前余量
	 * 
	 * @param isMaterial
	 *            true代表料件，false代表成品
	 * @param impExpType
	 *            单据类型
	 * @param tradeCode
	 *            贸易方式编码
	 * @param contract
	 *            合同备案表头
	 * @param seqNum
	 *            物料序号
	 * @return BcsContractExeInfo 存放合同成品的合同定量、合同余量、当前余量资料
	 */
	public BcsContractExeInfo findBcsContractExeInfo(Request request,
			boolean isMaterial, int impExpType, String tradeCode,
			Contract contract, String seqNum, String customsEnvelopBillNo) {
		return this.contractExeLogic.findBcsContractExeInfo(isMaterial,
				impExpType, tradeCode, contract, Integer.valueOf(seqNum), customsEnvelopBillNo);
	}
	
	
	/**
	 * 检查商品明细中的数量是否大于当前余量(电子化手册)
	 * @param request
	 * @param commInfo 料件列表
	 * @param contract 合同备案表头
	 * @param isMaterial true代表料件，false代表成品
	 * @param impExpType 单据类型
	 * @param tradeCode 贸易方式编码
	 * @return
	 */
	public String checkCurrentRemainAmount(Request request,
			List<BaseCustomsDeclarationCommInfo> commInfo, Contract contract,
			boolean isMaterial, int impExpType, String tradeCode) {
		
		return contractExeLogic.checkCurrentRemainAmount(commInfo, contract, isMaterial, impExpType, tradeCode);
	}
			
	

	public List findTempImpExpGoodsBill(Request request, Boolean isLj) {
		return bcsImpExpRequestLogic.findTempImpExpGoodsBill(isLj);
	}

	public void saveImpExpGoodsBillList(Request request, List list) {
		bcsImpExpRequestDao.saveImpExpGoodsBillList(list);
	}

	public List findImpExpGoodsBillAll(Request request, Boolean isLj,
			Boolean isTcustoms, Date beginDate, Date endDate) {
		return bcsImpExpRequestDao.findImpExpGoodsBillAll(isLj, isTcustoms,
				beginDate, endDate);
	}

	public String impExpGoodsTransCustoms(Request request, List ls, boolean isLj) {
		return bcsImpExpRequestLogic.impExpGoodsTransCustoms(ls, isLj);
	}

	public void DeleteImpExpGoodsBillList(Request request, List list) {
		this.bcsImpExpRequestDao.DeleteImpExpGoodsBillList(list);
	}

	public List findDistinctImpExpGoodsBill(Request request) {
		return this.bcsImpExpRequestDao.findDistinctImpExpGoodsBill();
	}

	public List getRContract(Request request,
			List<TempBcsImpExpCommodityInfo> list, int impExpFlag) {
		return bcsImpExpRequestLogic.getRContract(list, impExpFlag);
	}
	/**
	 *  取得物料与报关对应表中料件
	 *@param reqeust
	 *请求控制
	 * @param isMaterial 是否料件
	 * @return
	 */
	public List getTempMaterielByTypeBcs(Request reqeust,boolean isMaterial){
		return this.bcsImpExpRequestLogic.getTempMaterielByTypeBcs(isMaterial);
	}
}
