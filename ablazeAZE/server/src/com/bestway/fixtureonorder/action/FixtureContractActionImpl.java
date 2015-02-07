/*
 * Created on 2005-3-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.fixtureonorder.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

import com.bestway.fixtureonorder.dao.FixtureContractDao;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.fixtureonorder.entity.FixtureLocation;
import com.bestway.fixtureonorder.entity.TempOthersBillInfo;
import com.bestway.fixtureonorder.logic.FixtureContractLogic;

/**
 * com.bestway.bcs.contract.action.FixtureContractActionImpl
 * 
 * @author fhz
 */
public class FixtureContractActionImpl extends BaseActionImpl implements
		FixtureContractAction {

	private FixtureContractLogic fixtureContractLogic = null;

	private FixtureContractDao fixtureContractDao = null;

	/**
	 * 获取contractDao
	 * 
	 * @return Returns the contractDao.
	 */
	public FixtureContractDao getFixtureContractDao() {
		return fixtureContractDao;
	}

	/**
	 * 获取contractLogic
	 * 
	 * @return Returns the contractLogic.
	 */
	public FixtureContractLogic getFixtureContractLogic() {
		return fixtureContractLogic;
	}

	/**
	 * 设置contractDao
	 * 
	 * @param contractDao
	 *            The contractDao to set.
	 */
	public void setFixtureContractDao(FixtureContractDao fixtureContractDao) {
		this.fixtureContractDao = fixtureContractDao;
	}

	/**
	 * 设置contractLogic
	 * 
	 * @param contractLogic
	 *            The contractLogic to set.
	 */
	public void setFixtureContractLogic(
			FixtureContractLogic fixtureContractLogic) {
		this.fixtureContractLogic = fixtureContractLogic;
	}

	// ///////////
	// 合同
	// ///////////
	/**
	 * 查找所有的合同
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	public List findContract(Request request) {
		return this.fixtureContractDao.findContract();
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public FixtureContract findContractById(Request request, String id) {
		return this.fixtureContractDao.findContractById(id);
	}

	/**
	 * 查找所有的合同是否已审核
	 * 
	 * @param request
	 *            请求控制
	 * @param isCancel
	 *            审核判断，true表示已审核
	 * @return List 是Contract型，合同表头
	 */
	public List findContract(Request request, boolean isCancel) {
		return this.fixtureContractDao.findContract(isCancel);
	}

	/**
	 * 保存合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public FixtureContract saveContract(Request request,
			FixtureContract contract) {
		this.fixtureContractDao.saveContract(contract);
		return contract;
	}

	/**
	 * 删除合同表头
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public void deleteContract(Request request, FixtureContract contract) {
		this.fixtureContractLogic.deleteContract(contract, true);
	}

	/**
	 * 批量保存合同
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同表头
	 */
	public List saveContract(Request request, List list) {
		this.fixtureContractDao.saveContract(list);
		return list;
	}

	/**
	 * 删除合同表头
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	public void deleteContract(Request request, List<FixtureContract> list) {
		this.fixtureContractLogic.deleteContract(list, true);
	}

	public void deleteCavContract(Request request, List<FixtureContract> list) {
		this.fixtureContractLogic.deleteContract(list, false);
	}

	// ///////////
	// 合同料件
	// ///////////
	/**
	 * 查找所有的合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItem(Request request) {
		return this.fixtureContractDao.findContractItem();
	}

	/**
	 * 查找合同料件 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItemByParentId(Request request, String parentId) {
		return this.fixtureContractDao.findContractItemByParentId(parentId);
	}
	
	/**
	 * 查找合同料件 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItem(Request request, String ptNo,String parentId,String secendNo,String name,String spec,Date startDate,Date endDate){
		return this.fixtureContractDao.findContractItem(ptNo,parentId,secendNo,name,spec,startDate,endDate);
	}

	/**
	 * 查找合同料件 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractItemByParentIdAndChange(Request request,
			String parentId) {
		return this.fixtureContractDao
				.findContractItemByParentIdAndChange(parentId);
	}

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractItem
	 *            合同料件
	 */
	public FixtureContractItems saveContractImg(Request request,
			FixtureContractItems contractItem) {
		this.fixtureContractLogic.saveContractImg(contractItem);
		return contractItem;
	}

	/**
	 * 变更料件的商品编码
	 * 
	 * @param ContractItems
	 * @param complex
	 */
	public FixtureContractItems changeContractItemComplex(Request request,
			FixtureContractItems ContractItems, Complex complex) {
		this.fixtureContractLogic.changeContractItemComplex(ContractItems,
				complex);
		return ContractItems;
	}

	/**
	 * 删除合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractItem
	 *            合同料件
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void deleteContractItem(Request request,
			FixtureContractItems contractItem) {
		List<FixtureContractItems> list = new ArrayList<FixtureContractItems>();
		list.add(contractItem);
		this.fixtureContractLogic.deleteContractItem(list);
	}

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public List saveContractItem(Request request, List list) {
		this.fixtureContractLogic.saveContractItem(list);
		return list;
	}

	/**
	 * 删除合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void deleteContractItem(Request request, List list) {
		this.fixtureContractLogic.deleteContractItem(list);
	}

	/**
	 * 转抄合同数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	public List<FixtureContract> copyContract(Request request,
			List<FixtureContract> list) {
		return this.fixtureContractLogic.copyContract(list);
	}

	/**
	 * 判断合同是否可以备案
	 * 
	 * @param contract
	 *            合同表头
	 */
	public String checkContractForPutOnRecord(Request request,
			FixtureContract contract) {
		return this.fixtureContractLogic.checkContractForPutOnRecord(contract);
	}

	/**
	 * 备案合同数据
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public FixtureContract putOnRecordContract(Request request,
			FixtureContract contract) {
		this.fixtureContractLogic.putOnRecordContract(contract);
		return contract;
	}

	/**
	 * 变更合同 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @return Contract 合同表头
	 */
	public FixtureContract changingContract(Request request,
			FixtureContract contract) {
		return this.fixtureContractLogic.changingContract(contract);
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	public List findContractByProcessExe(Request request) {
		return this.fixtureContractDao.findContractByProcessExe();
	}

	/**
	 * 获得最大的料件总表序号来自当前合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 最大的料件总表序号
	 */
	public int getMaxContractItemSeqNum(Request request, String contractId) {
		return this.fixtureContractDao.getMaxContractItemSeqNum(contractId);
	}

	/**
	 * 获得最大的合同流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的合同流水号
	 */
	public String getMaxPreContractCodeSuffix(Request request) {
		return this.fixtureContractDao.getMaxPreContractCodeSuffix();
	}

	/**
	 * 该帐册编号的合同是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @return boolean 帐册编号的合同是否存在，存在为true，否则为false
	 */
	public boolean isExistContractByEmsNo(Request request,
			FixtureContract contract) {
		return this.fixtureContractDao.isExistContractByEmsNo(contract);
	}

	/**
	 * 获得合同的进口总金额
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return Double 合同的进口总金额
	 */
	public Double getItemAmountSum(Request request, String contractId) {
		return this.fixtureContractDao.getItemAmountSum(contractId);
	}

	/**
	 * 合同料件数据取整
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public List saveContractItemAmountInteger(Request request,
			List<FixtureContractItems> list) {
		this.fixtureContractLogic.saveContractItemAmountInteger(list);
		return list;
	}

	/**
	 * 当前合同料件记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 合同料件记录总数
	 */
	public int findContractItemCount(Request request, String contractId) {
		return this.fixtureContractDao.findContractItemCount(contractId);
	}

	/**
	 * 变更合同料件序号
	 * 
	 * @param request
	 *            请求控制
	 * @param item
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeContractItemSeqNum(Request request,
			FixtureContractItems item, Integer newSeqNum) {
		this.fixtureContractLogic.changeContractItemSeqNum(item, newSeqNum);
		// return img;
	}

	/**
	 * 保存合同料件来自商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveComtractItemByComplex(Request request,
			FixtureContract contract, List list) {
		return this.fixtureContractLogic.saveComtractItemByComplex(contract,
				list);
	}

	/**
	 * 更改合同商品的名称规格
	 * 
	 * @param request
	 *            请求控制
	 * @param fixtureContract
	 *            合同表头
	 * @param isMaterial
	 *            料件判断，true为料件
	 * @param seqNum
	 *            料件序号
	 * @param name
	 *            商品名称
	 * @param spec
	 *            商品规格
	 * @return List 是ContractExg或ContractImg型，合同成品或合同料件
	 */
	public Object changeContractCommNameSpec(Request request,
			FixtureContract fixtureContract,
			FixtureContractItems contractItems, Integer seqNum, String name,
			String spec) {
		return this.fixtureContractLogic.changeContractCommNameSpec(
				fixtureContract, contractItems, seqNum, name, spec);
	}

	/**
	 * 查询来料设备合同明显
	 * 
	 * @param baseCustomsDeclaration
	 * @return
	 */
	public List findFixtureContractItemsByCustom(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.fixtureContractDao
				.findFixtureContractItemsByCustom(baseCustomsDeclaration);
	}

	/**
	 * 存放位置表查询存放位置表
	 * 
	 * @return
	 */
	public List findFixtureLocation(Request request) {
		return this.fixtureContractDao.findFixtureLocation();
	}

	/**
	 * 删除存放位置表
	 * 
	 * @param location
	 */
	public void deleteFixtureLocation(Request request, FixtureLocation location) {
		this.fixtureContractDao.deleteFixtureLocation(location);
	}

	/**
	 * 保存存放位置表
	 * 
	 * @param location
	 */
	public FixtureLocation saveFixtureLocation(Request request,
			FixtureLocation location) {
		this.fixtureContractDao.saveFixtureLocation(location);
		return location;
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixtureLocationResultInfo(Request request) {
		return this.fixtureContractDao.findFixtureLocationResultInfo();
	}

	/**
	 * 查询设备异动单据
	 * 
	 * @return
	 */
	public List findFixtureLocationChangeBillInfo(Request request) {
		return this.fixtureContractDao.findFixtureLocationChangeBillInfo();
	}

	/**
	 * 抓取进口报关单项目
	 * 
	 * @return
	 */
	public List findCustomsDeclarationFixture(Request request) {
		return this.fixtureContractLogic.findCustomsDeclarationFixture();
	}

	/**
	 * 保存最终异动单据资料
	 * 
	 * @param list
	 */
	public void saveFixtureLocationChangeBillInfo(Request request, List list,
			int changeType) {
		this.fixtureContractLogic.saveFixtureLocationChangeBillInfo(list,
				changeType);
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixtureLocationChangeInfoFromCustomsBill(Request request,
			List list, FixtureLocation newLocation, Integer changeType) {
		return this.fixtureContractLogic
				.getFixtureLocationChangeInfoFromCustomsBill(list, newLocation,
						changeType);

	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixtureLocationChangeInfoFromResultInfo(Request request,
			FixtureLocation newLocation, Integer changeType) {
		return this.fixtureContractLogic
				.getFixtureLocationChangeInfoFromResultInfo(newLocation,
						changeType);
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixtureLocationChangeInfoFromFactory(Request request,
			List list, FixtureLocation newLocation, Integer changeType) {
		return this.fixtureContractLogic
				.getFixtureLocationChangeInfoFromFactory(list, newLocation,
						changeType);
	}

	/**
	 * 取得最终异动单据资料
	 * 
	 * @param list
	 * @param otherInfo
	 * @return
	 */
	public List getFixtureLocationChangeBillInfo(Request request,
			List contralDataList, List lsChangeFixture,
			TempOthersBillInfo otherInfo) {
		return this.fixtureContractLogic.getFixtureLocationChangeBillInfo(
				contralDataList, lsChangeFixture, otherInfo);
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclarationStat(Request request, String agreementNo,
			Date beginDate, Date endDate, boolean isImport) {
		return this.fixtureContractLogic.findCustomsDeclarationStat(
				agreementNo, beginDate, endDate, isImport);
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            判断是否料件，true为料件
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	public List findCustomsDeclarationCommInfo(Request reqeust,
			FixtureContract fixtureContract, int state) {
		return this.fixtureContractLogic.findCustomsDeclarationCommInfo(
				fixtureContract, state);
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param isImport
	 *            进口判断，true为进口
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(Request reqeust,
			FixtureContract fixtureContract, int state) {
		return this.fixtureContractLogic.findCustomsDeclarationCustomer(
				fixtureContract, state);
	}

	/**
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param reqeust
	 *            请求控制
	 * @param seqNum
	 *            商品序号
	 * @param customer
	 *            客户
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是ImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	public List findImpExpCommInfoList(Request reqeust, Integer seqNum,
			String code, String name, String customer, String impExpType,
			Date beginDate, Date endDate, FixtureContract fixtureContract,
			int state) {
		return this.fixtureContractLogic.findImpExpCommInfoList(seqNum, code,
				name, customer, impExpType, beginDate, endDate,
				fixtureContract, state);

	}

	/**
	 * 判断位置是否在被使用状态
	 * 
	 * @param reqeust
	 * @param location
	 * @return
	 */
	public Boolean isInUseFixtureLocation(Request reqeust,
			FixtureLocation location) {
		return fixtureContractLogic.isInUseFixtureLocation(location);
	}

	/**
	 * 按照条件查询设备所在的位置
	 * 
	 * @param reqeust 请求控制
	 * @param seqNum 商品序号
	 * @param code 商品编码
	 * @param name 商品名称
	 * @param fixtureContract 设备协议
	 * @param fixtureLocation 位置存放表
	 * @return 
	 */
	public List findFixtureLocationResultInfo(Request reqeust, Integer seqNum,
			String code, String name, FixtureContract fixtureContract,
			FixtureLocation fixtureLocation,Date beginDate, Date endDate,String spec) {
		return this.fixtureContractDao.findFixtureLocationResultInfo(seqNum,
				code, name, fixtureContract, fixtureLocation,beginDate, endDate,spec);

	}
	
	/**
	 * 从文件导入数据
	 * @param ls
	 * @param importApplyProperty
	 * @param cbIsOverwrite
	 */
	public void importDataFromFile(Request reqeust,List ls,boolean cbIsOverwrite,FixtureContract fixtureContract){
		fixtureContractLogic.importDataFromFile(ls,	cbIsOverwrite,fixtureContract);
	}
	
	/**
	 * 判断料号seqNum在设备里是否存在
	 * @param reqeust
	 * @param seqNum
	 * @return
	 */
	public boolean isExistFixtureContractItems(Request reqeust,Integer seqNum,String fixtureContractId){
		return this.fixtureContractDao.isExistFixtureContractItems(seqNum,fixtureContractId);
	}
	
	public Complex findComplexByCode(Request reqeust,String complexCode){
		return this.fixtureContractDao.findComplexByCode(complexCode);
	}

}
