/*
 * Created on 2005-4-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.action;

import java.security.acl.Acl;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcs.contractcav.entity.ContractCavTotalValue;
import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcs.contractcav.entity.ContractUnitWasteCav;
import com.bestway.bcs.contractcav.entity.TempContractCheckCav;
import com.bestway.bcs.contractcav.logic.ContractCavLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;

/**
 * com.bestway.bcs.contractcav.action.ContractCavActionImpl
 * 
 * @author Administrator
 */
@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class ContractCavActionImpl extends BaseActionImpl implements
		ContractCavAction {
	private ContractCavDao contractCavDao;
	private ContractCavLogic contractCavLogic;

	/**
	 * 获取contractCavLogic
	 * 
	 * @return Returns the contractCavLogic.
	 */
	public ContractCavLogic getContractCavLogic() {
		return contractCavLogic;
	}

	/**
	 * 设置contractCavLogic
	 * 
	 * @param contractCavLogic
	 *            The contractCavLogic to set.
	 */
	public void setContractCavLogic(ContractCavLogic contractCavLogic) {
		this.contractCavLogic = contractCavLogic;
	}

	/**
	 * 获取contractCavDao
	 * 
	 * @return Returns the contractCavDao.
	 */
	public ContractCavDao getContractCavDao() {
		return contractCavDao;
	}

	/**
	 * 设置contractCavDao
	 * 
	 * @param contractCavDao
	 *            The contractCavDao to set.
	 */
	public void setContractCavDao(ContractCavDao contractCavDao) {
		this.contractCavDao = contractCavDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#getAcl()
	 */
	public Acl getAcl() {
		// 
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#getModuleName()
	 */
	public String getModuleName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bestway.common.BaseAction#setModuleName(java.lang.String)
	 */
	public void setModuleName(String moduleName) {
	}

	/**
	 * 根据手册编码查询合同核销表头，包括自用和海关用
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            手册编号
	 * @param isCustoms
	 *            是海关用还是自用，true代表是海关用
	 * @return List 是ContractCav型，合同核销表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public ContractCav findContractCav(Request request, String emsNo,
			boolean isCustoms) {
		return this.contractCavDao.findContractCav(emsNo, isCustoms);
	}

	/**
	 * 抓取合同报关单
	 * 
	 * @param contractCav
	 *            电子化手册合同报关单
	 */
	public List findBcsCustomsDeclarationCav(Request request,
			ContractCav contractCav){
		return this.contractCavDao.findBcsCustomsDeclarationCav(contractCav);
	}
	/**
	 * 取得报关单金额
	 * 
	 * @param request
	 *            请求控制
	 *@param impExpFlag
	 *            进出口标志
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	public Map findCustomsDeclarationMoney(Request request, Integer impExpFlag) {
		return this.contractCavLogic.findCustomsDeclarationMoney(impExpFlag);
	}
	/**
	 * 保存合同核销表头
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public ContractCav saveContractCav(Request request, ContractCav contractCav) {
		contractCavDao.saveContractCav(contractCav);
		return contractCav;
	}

	/**
	 * 权限控制用
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--浏览", index = 14)
	public void controlContractCav(Request request) {
	}

	/**
	 * 删除合同核销表头
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void deleteContractCav(Request request, ContractCav contractCav) {
		this.contractCavDao.deleteContractCav(contractCav);
	}

	/**
	 * 抓取合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	public List findCustomsDeclarationCav(Request request,
			ContractCav contractCav) {
		return this.contractCavDao.findCustomsDeclarationCav(contractCav);
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractExgCav型，合同核销成品
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public List findContractExgCav(Request request, ContractCav contractCav,
			boolean notShowAmountIsZero) {
		return this.contractCavLogic.findContractExgCav(contractCav,
				notShowAmountIsZero);
	}

	/**
	 * 保存合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgCav
	 *            合同核销成品
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public ContractExgCav saveContractExgCav(Request request,
			ContractExgCav contractExgCav) {
		this.contractCavDao.saveContractExgCav(contractExgCav);
		return contractExgCav;
	}

	/**
	 * 删除合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgCav
	 *            合同核销成品
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void deleteContractExgCav(Request request,
			ContractExgCav contractExgCav) {
		this.contractCavDao.deleteContractExgCav(contractExgCav);
	}

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public List findContractImgCav(Request request, ContractCav contractCav,
			boolean notShowAmountIsZero) {
		return this.contractCavLogic.findContractImgCav(contractCav,
				notShowAmountIsZero);
	}

	/**
	 * 抓取合同核销单耗资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractUnitWasteCav型，存放分组后的单耗、损耗量、总用量
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public List findContractUnitWasteCav(Request request,
			ContractCav contractCav) {
		return this.contractCavDao.findContractUnitWasteCav(contractCav);
	}

	/**
	 * 保存合同核销单耗资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractUnitWasteCav
	 *            存放分组后的单耗、损耗量、总用量
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public ContractUnitWasteCav saveContractUnitWasteCav(Request request,
			ContractUnitWasteCav contractUnitWasteCav) {
		this.contractCavDao.saveContractUnitWasteCav(contractUnitWasteCav);
		return contractUnitWasteCav;
	}

	/**
	 * 删除合同核销单耗资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractUnitWasteCav
	 *            存放分组后的单耗、损耗量、总用量
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void deleteContractUnitWasteCav(Request request,
			ContractUnitWasteCav contractUnitWasteCav) {
		this.contractCavDao.deleteContractUnitWasteCav(contractUnitWasteCav);
	}

	/**
	 * 保存合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgCav
	 *            合同核销料件
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public ContractImgCav saveContractImgCav(Request request,
			ContractImgCav contractImgCav) {
		this.contractCavDao.saveContractImgCav(contractImgCav);
		return contractImgCav;
	}

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgCav
	 *            合同核销料件
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void deleteContractImgCav(Request request,
			ContractImgCav contractImgCav) {
		this.contractCavLogic.deleteContractImgCav(contractImgCav);
	}

	/**
	 * 抓取合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public List findContractBomCav(Request request, ContractCav contractCav,
			boolean notShowAmountIsZero) {
		return this.contractCavDao.findContractBomCav(contractCav,
				notShowAmountIsZero);
	}

	public void addContractBomCav(Request request, ContractCav contractCav,
			ContractImgCav img) {
		this.contractCavLogic.addContractBomCav(contractCav, img);
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public ContractBomCav saveContractBomCav(Request request,
			ContractBomCav contractBomCav) {
		this.contractCavDao.saveContractBomCav(contractBomCav);
		return contractBomCav;
	}

	/**
	 * 删除合同核销Bom资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void deleteContractBomCav(Request request,
			ContractBomCav contractBomCav) {
		this.contractCavDao.deleteContractBomCav(contractBomCav);
	}

	/**
	 * 重新计算自用核销表
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void reMakeSelfuseContractCav(Request request, String emsNo) {
		this.contractCavLogic.reMakeSelfuseContractCav(emsNo, request
				.getTaskId());
	}

	/**
	 * 重新计算海关核销表
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void reGetCustomsContractCav(Request request, String emsNo,
			boolean isOnlyHead) {
		this.contractCavLogic.reGetCustomsContractCav(emsNo, isOnlyHead,
				request.getTaskId());
	}

	/**
	 * 核销计算(包含自用和海关)
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void cavContract(Request request, String emsNo) {
		this.contractCavLogic.cavContract(emsNo, request.getTaskId());
	}

//	/**
//	 * 核销其他计算(包含自用和海关)
//	 * 
//	 * @param contract
//	 *            合同备案表头
//	 */
//	public Double cavContractValueAddRate(Request request, String emsNo, double impGoods,
//			double expGoods, double followTax) {
//		return this.contractCavLogic.cavContractValueAddRate(emsNo, request.getTaskId(),
//				impGoods, expGoods, followTax);
//	}

	/**
	 * 重新计算边角料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算编辑", index = 14)
	public void recalRemainMaterialAmount(Request request,
			ContractCav contractCav) {
		this.contractCavLogic.recalRemainMaterialAmount(contractCav);
	}

	/**
	 * 核销表总计算
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return ContractCavTotalValue 存放纸质手册合同核销的进出口方面的总和 如进口总金额、出口总金额、出口总毛重.....
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public ContractCavTotalValue findCavTotalValue(Request request,
			ContractCav contractCav,Contract contract) {
		return this.contractCavLogic.findCavTotalValue(contractCav, contract);
	}

	/**
	 * 从数据库中重新load
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object load (Request request,Class entityClass,String id){
		return contractCavDao.load(entityClass, id);
	}
	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合）
	 * list<ContractUnitWasteCav>(1) 单耗记录（集合）
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同核销表头Id
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0)成品（集合）,list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--核销计算", index = 14)
	public List<List> findContractUnitWasteCav(Request request,
			String parentId, int index, int length, boolean isShowZero,
			boolean isNoPrintProductZer) {
		return this.contractCavLogic.findContractUnitWasteCav(parentId, index,
				length, isShowZero, isNoPrintProductZer);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合）
	 * list<ContractUnitWasteCav>(1) 单耗记录（集合）
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同核销表头Id
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0)成品（集合）,list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	public List<List> findContractUnitWasteCav2(Request request,
			String parentId,String emsNo, int index, int length) {
		return this.contractCavLogic.findContractUnitWasteCav2(parentId,emsNo,
				index, length);
	}

	/**
	 * 根据合同核销表头查找合同核销成品表里有多少条数据
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCavId
	 *            合同核销表头ID
	 * @return int 数据条数
	 */
	public int findContractExgCavCount(Request request, String contractCavId) {
		return this.contractCavDao.findContractExgCavCount(contractCavId);
	}

	/**
	 * 核销单数量取整
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @param isTotalAmount
	 *            true:修改总用量 false:修改总耗量
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	public void convertWasteAmountToInteger(Request request,
			ContractCav contractCav, boolean isTotalAmount,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack) {
		this.contractCavLogic.convertWasteAmountToInteger(contractCav,
				isTotalAmount, modifyTotalAmountNotWriteBack,
				modifyWasteAmountNotWriteBack);
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param bomCav
	 *            合同核销BOM
	 * @param modifyUnitWasteNotWriteBack
	 *            判断是否修改单耗后反写，true为修改
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	public ContractBomCav saveContractBomCav(Request request,
			ContractBomCav bomCav, boolean modifyUnitWasteNotWriteBack,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack, boolean modifyWasteAmount) {
		this.contractCavLogic.saveContractBomCav(bomCav,
				modifyUnitWasteNotWriteBack, modifyTotalAmountNotWriteBack,
				modifyWasteAmountNotWriteBack, modifyWasteAmount);
		return bomCav;
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param imgCav
	 *            合同核销料件
	 * @param list
	 *            是List型，合同核销BOM
	 * @param modifyProductUsedAmountWriteBack
	 *            判断是否修改成品耗用量后反写，true为修改
	 */
	public ContractImgCav saveContractImgCav(Request request,
			ContractImgCav imgCav, List list,
			boolean modifyProductUsedAmountWriteBack) {
		this.contractCavLogic.saveContractImgCav(imgCav, list,
				modifyProductUsedAmountWriteBack);
		return imgCav;
	}

	/**
	 * 查找合同核销BOM 来自 合同核销表头ID和商品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCavId
	 *            合同核销表头ID
	 * @param imgSeqNum
	 *            商品序号
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCavByImgSeqNum(Request request,
			String contractCavId, Integer imgSeqNum) {
		return this.contractCavDao.findContractBomCavByImgSeqNum(contractCavId,
				imgSeqNum);
	}

	/**
	 * 查找合同核销BOM 来自 合同核销表头ID和商品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCavId
	 *            合同核销表头ID
	 * @param imgSeqNum
	 *            商品序号
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	public List findContractBomCavByImgSeqNum(Request request,
			String contractCavId, String imgSeqNum) {
		return this.contractCavDao.findContractBomCavByImgSeqNum(contractCavId,
				imgSeqNum);
	}

	/**
	 * 合同核销平衡检查
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return boolean 平衡返回true
	 */
	public StringBuffer checkContractCavData(Request request, ContractCav contractCav) {
		return this.contractCavLogic.checkContractCavData(contractCav);
	}

	public ContractCav findContractCavById(Request request, String id) {
		return (ContractCav) this.contractCavDao.load(ContractCav.class, id);
	}

	/**
	 * 数据报核申报
	 * 
	 * @param contractCav
	 */
	public DeclareFileInfo applyContractCav(Request request,
			ContractCav contractCav,boolean isAppBom) {
		return this.contractCavLogic.applyContractCav(contractCav, request
				.getTaskId(),isAppBom);
	}

	/**
	 * 核销处理回执
	 * 
	 * @param bcsContractCav
	 */
	public String processContractCav(Request request,
			ContractCav bcsContractCav, List lsReturnFile) {
		return this.contractCavLogic.processBcsContractCav(bcsContractCav,
				lsReturnFile);
	}

	public List findCavContractByEmsNo(Request request, String emsNo) {
		return this.contractCavDao.findCavContractByEmsNo(emsNo);
	}

	public List findContractImgCavByBuiner(Request request, String cavheadId) {
		return this.contractCavDao.findContractImgCavByBuiner(cavheadId);
	}

	public List findCustomsDeclaretionDetailByContract(Request request,
			String contractID, boolean isMateriel) {
		return this.contractCavLogic.findCustomsDeclaretionDetailByContract(
				contractID, isMateriel);
	}

	/**
	 * 改变通关手册核销表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	@AuthorityFunctionAnnotation(caption = "数据报核--改变手册核销的申报状态", index = 14)
	public ContractCav changeContractCavDeclareState(Request request,
			ContractCav head, String declareState) {
		return this.contractCavLogic.changeContractCavDeclareState(head,
				declareState);
	}

	/**
	 * 
	 * @param request
	 * @param emdNo
	 *            鎵嬪唽缂栧彿
	 * @param contractNo
	 *            鍚堝悓鍙? * @return
	 */
	public TempContractCheckCav findCavContract(Request request,
			Contract contract) {
		// TODO Auto-generated method stub
		return this.contractCavLogic.findCavContract(contract);
	}

	/**
	 * 合同国内购料清单表
	 */
	public List setContractCavDomesticPurchaseBill(Request request, String id) {
		return this.contractCavLogic.setContractCavDomesticPurchaseBill(id);
	}

	/**
	 * 打印备案总耗与实际总耗对照表
	 */
	public List setBomFilingRealityCompareBill(Request request,
			String contractCavId) {
		return this.contractCavLogic
				.setBomFilingRealityCompareBill(contractCavId);
	}

	/**
	 * 计算净重损之率
	 * 
	 * @param contract
	 *            合同备案表头
	 */
	public Double cavContractNetWeightLossRate(Request request, String emsNo /*,
			 double impBackGoodsWeight, double expBackGoodsWeight*/) {
		return this.contractCavLogic.cavContractNetWeightLossRate(emsNo,
				request.getTaskId() /*, impBackGoodsWeight, expBackGoodsWeight*/);
	}
	
	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	//@AuthorityFunctionAnnotation(caption = "改变通关手册的修改标志", index = 7.1)
	public void changeContractImgModifyMark(Request request, List list,
			String modifyMark) {
		this.contractCavLogic.changeContractImgModifyMark(list, modifyMark);
	}
	
	public ContractExg getContractExgByContract(String contractId,Integer exgId,String contractNo){
		return this.contractCavLogic.getContractExgByContract(contractId, exgId,contractNo);
	}
	
	
	public DzscEmsExgBill getDzscEmsExgBillExgByContract(String contractId,
			Integer exgId, String contractNo){
		return this.contractCavLogic.getDzscEmsExgBillExgByContract(contractId,exgId,contractNo);
	}
	

}
