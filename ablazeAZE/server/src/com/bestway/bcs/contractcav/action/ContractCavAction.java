/*
 * Created on 2005-4-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.action;

import java.util.List;
import java.util.Map;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcs.contractcav.entity.ContractCavTotalValue;
import com.bestway.bcs.contractcav.entity.TempContractCheckCav;
import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcs.contractcav.entity.ContractUnitWasteCav;
import com.bestway.bcs.contractcav.entity.TempContractConsumptionUnitWasteCav;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;

/**
 * com.bestway.bcs.contractcav.action.ContractCavAction
 * 
 * @author Administrator
 */
public interface ContractCavAction {
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
	ContractCav findContractCav(Request request, String emsNo, boolean isCustoms);

	/**
	 * 保存合同核销表头
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 */
	ContractCav saveContractCav(Request request, ContractCav contractCav);

	/**
	 * 删除合同核销表头
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 */
	void deleteContractCav(Request request, ContractCav contractCav);

	/**
	 * 抓取合同核销报关单
	 * 
	 * @param contractCav
	 *            电子帐册合同核销单头
	 */
	List findCustomsDeclarationCav(Request request, ContractCav contractCav);

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractExgCav型，合同核销成品
	 */
	List findContractExgCav(Request request, ContractCav contractCav,
			boolean notShowAmountIsZero);

	/**
	 * 保存合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgCav
	 *            合同核销成品
	 */
	ContractExgCav saveContractExgCav(Request request,
			ContractExgCav contractExgCav);

	/**
	 * 删除合同核销成品资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgCav
	 *            合同核销成品
	 */
	void deleteContractExgCav(Request request, ContractExgCav contractExgCav);

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	List findContractImgCav(Request request, ContractCav contractCav,
			boolean notShowAmountIsZero);

	/**
	 * 抓取合同核销单耗资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractUnitWasteCav型，存放分组后的单耗、损耗量、总用量
	 */
	List findContractUnitWasteCav(Request request, ContractCav contractCav);

	/**
	 * 保存合同核销单耗资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractUnitWasteCav
	 *            存放分组后的单耗、损耗量、总用量
	 */
	ContractUnitWasteCav saveContractUnitWasteCav(Request request,
			ContractUnitWasteCav contractUnitWasteCav);

	/**
	 * 删除合同核销单耗资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractUnitWasteCav
	 *            存放分组后的单耗、损耗量、总用量
	 */
	void deleteContractUnitWasteCav(Request request,
			ContractUnitWasteCav contractUnitWasteCav);

	/**
	 * 保存合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgCav
	 *            合同核销料件
	 */
	ContractImgCav saveContractImgCav(Request request,
			ContractImgCav contractImgCav);

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImgCav
	 *            合同核销料件
	 */
	void deleteContractImgCav(Request request, ContractImgCav contractImgCav);

	/**
	 * 抓取合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractBomCav型，合同核销BOM
	 */
	List findContractBomCav(Request request, ContractCav contractCav,
			boolean notShowAmountIsZero);

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM
	 */
	ContractBomCav saveContractBomCav(Request request,
			ContractBomCav contractBomCav);

	/**
	 * 删除合同核销Bom资料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractBomCav
	 *            合同核销BOM
	 */
	void deleteContractBomCav(Request request, ContractBomCav contractBomCav);

	/**
	 * 重新计算自用核销表
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 */
	void reMakeSelfuseContractCav(Request request, String emsNo);

	/**
	 * 重新计算海关核销表
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 */
	void reGetCustomsContractCav(Request request, String emsNo,
			boolean isOnlyHead);

	/**
	 * 核销计算(包含自用和海关)
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 */
	void cavContract(Request request, String emsNo);

	/**
	 * 重新计算边角料
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 */
	void recalRemainMaterialAmount(Request request, ContractCav contractCav);

	/**
	 * 核销表总计算
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return ContractCavTotalValue 存放纸质手册合同核销的进出口方面的总和 如进口总金额、出口总金额、出口总毛重.....
	 */
	ContractCavTotalValue findCavTotalValue(Request request,
			ContractCav contractCav,Contract contract);
	
	/**
	 * 从数据库中重新load
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object load (Request request,Class entityClass,String id);
	/**
	 * 抓取合同报关单
	 * 
	 * @param contractCav
	 *            电子化手册合同报关单
	 */
	List findBcsCustomsDeclarationCav(Request request,
			ContractCav contractCav);

	/**
	 * 取得报关单金额
	 * 
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	Map<String, Double> findCustomsDeclarationMoney(Request request,
			Integer impExpFlag);
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
	List<List> findContractUnitWasteCav(Request request, String parentId,
			int index, int length, boolean isShowZero,boolean isNoPrintProductZero);

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
			String contractCavId,String emsNo, int index, int length);

	/**
	 * 权限控制用
	 */
	void controlContractCav(Request request);

	/**
	 * 根据合同核销表头查找合同核销成品表里有多少条数据
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCavId
	 *            合同核销表头ID
	 * @return int 数据条数
	 */
	int findContractExgCavCount(Request request, String contractCavId);

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
	void convertWasteAmountToInteger(Request request, ContractCav contractCav,
			boolean isTotalAmount, boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack);

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
	ContractBomCav saveContractBomCav(Request request, ContractBomCav bomCav,
			boolean modifyUnitWasteNotWriteBack,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack, boolean modifyWasteAmount);

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
	ContractImgCav saveContractImgCav(Request request, ContractImgCav imgCav,
			List list, boolean modifyProductUsedAmountWriteBack);

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
	List findContractBomCavByImgSeqNum(Request request, String contractCavId,
			String imgSeqNum);

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
	List findContractBomCavByImgSeqNum(Request request, String contractCavId,
			Integer imgSeqNum);

	/**
	 * 合同核销平衡检查
	 * 
	 * @param request
	 *            请求控制
	 * @param contractCav
	 *            合同核销表头
	 * @return boolean 平衡返回true
	 */
	StringBuffer checkContractCavData(Request request, ContractCav contractCav);

	ContractCav findContractCavById(Request request, String id);

	/**
	 * 数据报核申报
	 * 
	 * @param contractCav
	 */
	DeclareFileInfo applyContractCav(Request request, ContractCav contractCav,boolean isAppBom);

	/**
	 * 核销处理回执
	 * 
	 * @param bcsContractCav
	 */
	String processContractCav(Request request, ContractCav bcsContractCav,
			List lsReturnFile);

	void addContractBomCav(Request request, ContractCav contractCav,
			ContractImgCav img);

	List findCavContractByEmsNo(Request request, String emsNo);

	public List findCustomsDeclaretionDetailByContract(Request request,
			String contractID, boolean isMateriel);

	public List findContractImgCavByBuiner(Request request, String cavheadId);

	/**
	 * 改变通关手册核销表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	ContractCav changeContractCavDeclareState(Request request,
			ContractCav head, String declareState);

	/**
	 * 
	 * @param request
	 * @param emdNo
	 * @param contractNo
	 * @return
	 */
	public TempContractCheckCav findCavContract(Request request,
			Contract contract);

	/**
	 * 合同国内购料清单表
	 */
	public List setContractCavDomesticPurchaseBill(Request request, String id);

	/**
	 * 打印备案总耗与实际总耗对照表
	 */
	public List setBomFilingRealityCompareBill(Request request,
			String contractCavId);
	
//	/**
//	 * 核销其他计算(包含自用和海关)
//	 * 
//	 * @param contract
//	 *            合同备案表头
//	 */
//	public Double cavContractValueAddRate(Request request, String emsNo,
//			double impGoods, double expGoods,double followTax);
//	
	/**
	 * 计算净重损之率
	 * 
	 * @param contract
	 *            合同备案表头
	 */
	public Double cavContractNetWeightLossRate(Request request, String emsNo /*,
			double impBackGoodsWeight, double expBackGoodsWeight*/);
	
	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	void changeContractImgModifyMark(Request request, List list,
			String modifyMark);
	
	public ContractExg getContractExgByContract(String contractId,Integer exgId,String contractNo);
	
	public DzscEmsExgBill getDzscEmsExgBillExgByContract(String contractId,Integer exgId,String contractNo);
}
