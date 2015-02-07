/*
 * Created on 2005-3-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contract.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsCommdityForbid;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcs.contract.entity.ImgExgObject;
import com.bestway.bcs.contract.logic.BcsImportQPDataLogic;
import com.bestway.bcs.contract.logic.ContractLogic;
import com.bestway.bcs.contract.logic.ReceiveContractLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.ModifyMarkState;

/**
 * lm checked by 2010-01-07
 * 
 * 电子化手册-通关手册-ContractActionImpl
 * 
 * com.bestway.bcs.contract.action.ContractActionImpl
 * 
 * @author yp
 */
// -通关手册
@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class ContractActionImpl extends BaseActionImpl implements
		ContractAction {
	/** 合同操作logic */
	private ContractLogic contractLogic = null;
	/** 合同操作Dao */
	private ContractDao contractDao = null;
	/** 合同接收回执操作logic */
	private ReceiveContractLogic receiveContractLogic = null;
	/** BCS导入QP数据操作logic */
	private BcsImportQPDataLogic bcsImportQPDataLogic = null;

	/**
	 * 获取contractDao
	 * 
	 * @return Returns the contractDao.
	 */
	public ContractDao getContractDao() {
		return contractDao;
	}

	/**
	 * 获取contractLogic
	 * 
	 * @return Returns the contractLogic.
	 */
	public ContractLogic getContractLogic() {
		return contractLogic;
	}

	/**
	 * 获取bcsImportQPDataLogic
	 * 
	 * @return Returns the bcsImportQPDataLogic.
	 */
	public BcsImportQPDataLogic getBcsImportQPDataLogic() {
		return bcsImportQPDataLogic;
	}

	/**
	 * 设置bcsImportQPDataLogic
	 * 
	 * @return Returns the bcsImportQPDataLogic.
	 */
	public void setBcsImportQPDataLogic(
			BcsImportQPDataLogic bcsImportQPDataLogic) {
		this.bcsImportQPDataLogic = bcsImportQPDataLogic;
	}

	/**
	 * 设置contractDao
	 * 
	 * @param contractDao
	 *            The contractDao to set.
	 */
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	/**
	 * 设置contractLogic
	 * 
	 * @param contractLogic
	 *            The contractLogic to set.
	 */
	public void setContractLogic(ContractLogic contractLogic) {
		this.contractLogic = contractLogic;
	}

	/**
	 * 获取receiveContractLogic
	 * 
	 * @return receiveContractLogic
	 */
	public ReceiveContractLogic getReceiveContractLogic() {
		return receiveContractLogic;
	}

	/**
	 * 设置receiveContractLogic
	 * 
	 * @param receiveContractLogic
	 */
	public void setReceiveContractLogic(
			ReceiveContractLogic receiveContractLogic) {
		this.receiveContractLogic = receiveContractLogic;
	}

	/**
	 * 保存BCS参数
	 * 
	 * @param parameter
	 */
	public void saveBcsParameterSet(Request request, BcsParameterSet parameter) {
		this.contractDao.saveBcsParameterSet(parameter);
	}

	/**
	 * 查询BCS参数
	 * 
	 * @param request
	 *            请求控制
	 */

	@AuthorityFunctionAnnotation(caption = "通关手册备案--浏览", index = 7)
	public BcsParameterSet findBcsParameterSet(Request request) {
		return this.contractDao.findBcsParameterSet();
	}

	/**
	 * 查询是否已备案
	 * 
	 * @param request
	 * @return
	 */
	public List findEquipModeState(Request request, String emsNo) {
		return this.contractDao.findEquipModeState(emsNo);
	}

	/**
	 * 新增合同备案表头
	 * 
	 * @return DzscEmsPorHead 通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--新增", index = 7.5)
	public Contract newContract(Request request) {
		return this.contractLogic.newContract();
	}

	/**
	 * 通关手册导入
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--导入", index = 7.5)
	public void importContract(Request request) {
		return;
	}

	/**
	 * 从缓存中加载类对象
	 */
	public Object load(Class cls, String id) {
		return this.contractDao.load(cls, id);
	}

	/**
	 * 查找所有的合同
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--浏览", index = 7)
	public List findContract(Request request) {
		return this.contractDao.findContract();
	}

	/**
	 * 根据ID查找合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--浏览", index = 7)
	public Contract findContractById(Request request, String id) {
		return this.contractDao.findContractById(id);
	}

	/**
	 * 根据EMS_NO查找正在执行的合同
	 * 
	 * @return List 是Contract型，合同备案表头
	 */
	public Contract findExingContractByEmsNo(Request request, String emsNo) {
		return this.contractDao.findExingContractByEmsNo(emsNo);
	}

	public Contract findContractByEmsNO(Request req, String emsNo) {
		return this.contractDao.findContractByEmsNO(emsNo);
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
	// @AuthorityFunctionAnnotation(caption = "通关手册备案--浏览", index = 7)
	public List findContract(Request request, boolean isCancel) {
		return this.contractDao.findContract(isCancel);
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
	// @AuthorityFunctionAnnotation(caption = "通关手册备案--浏览", index = 7)
	public List findContractInCav(Request request, boolean isCancel) {
		return this.contractDao.findContractInCav(isCancel);
	}

	/**
	 * 保存合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--保存资料", index = 7.1)
	public Contract saveContract(Request request, Contract contract) {
		if (contract.getIsContractEms() == null || contract.getIsContractEms()) {
			List list = this.contractDao.findContractByCopEmsNo(contract
					.getCopEmsNo());
			if (ModifyMarkState.ADDED.equals(contract.getModifyMark())) {
				for (int i = 0; i < list.size(); i++) {
					Contract c = (Contract) list.get(i);
					if (!c.getId().equals(contract.getId())) {
						throw new RuntimeException("通关手册内部编号重复！");
					}
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					Contract c = (Contract) list.get(i);
					if (!c.getId().equals(contract.getId())
							&& !c.getEmsNo().equals(contract.getEmsNo())) {
						throw new RuntimeException("通关手册内部编号重复！");
					}
				}
			}
		}
		this.contractDao.saveContract(contract);
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
	@AuthorityFunctionAnnotation(caption = "通关手册备案--删除", index = 7.2)
	public void deleteContract(Request request, Contract contract) {
		this.contractLogic.deleteContract(contract);
	}

	/**
	 * 批量保存合同
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--保存资料", index = 7.1)
	public List saveContract(Request request, List list) {
		this.contractDao.saveContract(list);
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
	@AuthorityFunctionAnnotation(caption = "通关手册备案--删除", index = 7.2)
	public void deleteContract(Request request, List<Contract> list) {
		this.contractLogic.deleteContract(list);
	}

	/**
	 * 删除合同表头
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--删除已核销的合同", index = 7)
	public void deleteCavContract(Request request, List<Contract> list) {
		this.contractLogic.deleteContract(list);
	}

	/**
	 * 批量修改商品编码与法定单位权限控制
	 */
	@AuthorityFunctionAnnotation(caption = "批量修改商品编码与法定单位", index = 13)
	public void brownUpdateComplex(Request request) {
	}

	/**
	 * 查找所有的合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBom(Request request) {
		return this.contractDao.findContractBom();
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByParentId(Request request, String parentId) {
		return this.contractDao.findContractBomByExgId(parentId);
	}

	/**
	 * 查找合同BOM 来自 合同成品ID
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同成品Id
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByContractId(Request request, String parentId) {
		return this.contractDao.findContractBomByContractId(parentId);
	}

	/**
	 * 批量保存合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同BOM
	 */
	public List saveContractBom(Request request, List list) {
		this.contractDao.saveContractBom(list);
		return list;
	}

	/**
	 * 删除合同BOM
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractBom型，合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public Map<Integer, List<ContractBom>> deleteContractBom(Request request,
			List list) {
		return this.contractLogic.deleteContractBom(list);
	}

	/**
	 * 删除单耗为空或者为零的记录
	 * 
	 * @param contract
	 */
	public void deleteContractBomUnitWasteIsNull(Request request,
			Contract contract) {
		this.contractLogic.deleteContractBomUnitWasteIsNull(contract);
	}

	/**
	 * 查找所有的合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImg(Request request) {
		return this.contractDao.findContractImg();
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
	public List findContractImgByParentId(Request request, String parentId) {
		return this.contractDao.findContractImgByContractId(parentId);
	}

	/**
	 * 通过合同号查找合同料件
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgByContractExpContractNo(Request request,
			String expContractNo) {
		return this.contractDao
				.findContractImgByContractExpContractNo(expContractNo);
	}

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractImgByContractIdByModifyMark(Request request,
			String parentId) {
		return this.contractDao.findContractImgByContractIdByModifyMark(
				request, parentId);
	}

	/**
	 * 查找 合同成品 根据 备案编号 / 备案序号 (记录号)
	 * 
	 * @param request
	 * @param corrEmsNo
	 * @param credenceNo
	 * @return
	 */
	public ContractExg findCurrContractExgByCredenceNo(Request request,
			String contractId, String corrEmsNo, String credenceNo) {

		return contractDao.findContractExgByContractIdAndSeqnumAndCorrnum(
				contractId, corrEmsNo, credenceNo);

	}

	/**
	 * 根据修改状态查询合同料件
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	public List findContractImgByModifyMark(Request request, Contract contract,
			String modifyMark) {
		return this.contractDao.findContractImgByModifyMark(contract,
				modifyMark);
	}

	/**
	 * 查询合同的最大序号，除去新增状态的料件
	 * 
	 * @param contract
	 * @return
	 */
	public int findMaxContractImgSeqNumExceptAdd(Request request,
			Contract contract) {
		return this.contractDao.findMaxContractImgSeqNumExceptAdd(contract);
	}

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同料件
	 */
	public ContractImg saveContractImg(Request request, ContractImg contractImg) {
		this.contractLogic.saveContractImg(contractImg);
		return contractImg;
	}

	//
	// /**
	// * 变更料件的商品编码
	// *
	// * @param contractImg
	// * @param complex
	// */
	// public ContractImg changeContractImgComplex(Request request,
	// ContractImg contractImg, Complex complex) {
	// this.contractLogic.changeContractImgComplex(contractImg, complex);
	// return contractImg;
	// }
	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同料件
	 */
	public ContractImg saveContractImg(Request request,
			ContractImg contractImg, List<ContractBom> list) {
		this.contractLogic.saveContractImg(contractImg, list);
		return contractImg;
	}

	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public List saveContractImg(Request request, List list) {
		this.contractLogic.saveContractImg(list);
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
	public Map<Integer, List<ContractImg>> deleteContractImg(Request request,
			List list) {
		return this.contractLogic.deleteContractImg(list);
	}

	public List findContractBomImg(Request request, List list) {

		return this.contractLogic.findContractBomImg(list);
	}

	/**
	 * 查找所有的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExg(Request request) {
		return this.contractDao.findContractExg();
	}

	/**
	 * 根据修改状态查询合同成品
	 * 
	 * @param contract
	 * @param modifyMark
	 * @return
	 */
	public List findContractExgByModifyMark(Request request, Contract contract,
			String modifyMark) {
		return this.contractDao.findContractExgByModifyMark(contract,
				modifyMark);
	}

	/**
	 * 查询合同的最大序号，除去新增状态的成品
	 * 
	 * @param contract
	 * @return
	 */
	public int findMaxContractExgSeqNumExceptAdd(Request request,
			Contract contract) {
		return this.contractDao.findMaxContractExgSeqNumExceptAdd(contract);
	}

	/**
	 * 保存合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 */
	public ContractExg saveContractExg(Request request, ContractExg contractExg) {
		this.contractLogic.saveContractExg(contractExg);
		return contractExg;
	}

	// /**
	// * 变更成品编码
	// *
	// * @param contractExg
	// * 合同成品
	// */
	// public ContractExg changeContractExgComplex(Request request,
	// ContractExg contractExg, Complex complex) {
	// this.contractLogic.changeContractExgComplex(contractExg, complex);
	// return contractExg;
	// }
	/**
	 * 查找合同成品 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByParentId(Request request, String parentId) {
		return this.contractDao.findContractExgByParentId(parentId);
	}

	/**
	 * 通过出口合同号查找合同成品
	 * 
	 * @param expContractNo
	 *            出口合同号
	 * @return List 是ContractExg型，合同成品
	 * @author sxk
	 */
	public List findContractExgByExpContractNo(Request request,
			String expContractNo) {
		return this.contractDao.findContractExgByExpContractNo(expContractNo);
	}

	/**
	 * 查找合同成品 来自 合同表头Id,修改标志以外的
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param modifyMark
	 *            修改标志
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgByParentIdByModifyMark(Request request,
			String parentId) {
		return this.contractDao.findContractExgByParentIdByModifyMark(request,
				parentId);
	}

	/**
	 * 删除合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractExg型，合同成品
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public Map<Integer, List<ContractExg>> deleteContractExg(Request request,
			List<ContractExg> list) {
		return this.contractLogic.deleteContractExg(list);
	}

	/**
	 * 转抄合同数据
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是Contract型，合同表头
	 */
	public List<Contract> copyContract(Request request, List<Contract> list) {
		return this.contractLogic.copyContract(list);
	}

	/**
	 * 判断合同是否可以备案
	 * 
	 * @param contract
	 *            合同表头
	 */
	public String checkContractForPutOnRecord(Request request, Contract contract) {
		return this.contractLogic.checkContractForPutOnRecord(contract);
	}

	/**
	 * 判断合同料件表、成品表、及单耗表的数量是否必需取整
	 * 
	 * @param contract
	 *            合同表头
	 */
	public String checkContractIsAmountToInteger(Request request,
			Contract contract) {
		return this.contractLogic.checkContractIsAmountToInteger(contract);
	}

	/**
	 * 备案合同数据
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public Contract putOnRecordContract(Request request, Contract contract) {
		this.contractLogic.putOnRecordContract(contract);
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
	public Contract changingContract(Request request, Contract contract) {
		return this.contractLogic.changingContract(contract);
	}

	/**
	 * 保存合同成品来自凭证成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param innerMergeList
	 *            报关商品资料
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveContractExg(Request request, Contract contract,
			List innerMergeList) {
		return this.contractLogic.saveContractExg(contract, innerMergeList);
	}

	/**
	 * 保存合同成品来自凭证成品
	 * 
	 * @param contract
	 *            合同表头
	 * @param dictPorExgList
	 *            报关商品资料
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveContractExgFromDictPorExg(Request request,
			Contract contract, List dictPorExgList) {
		return this.contractLogic.saveContractExgFromDictPorExg(contract,
				dictPorExgList);
	}

	/**
	 * 批量保存合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            合同成品
	 */
	public List saveContractExg(Request request, List list) {
		this.contractDao.saveContractExg(list);
		return list;
	}

	/**
	 * 保存合同Bom来自凭证料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 * @param credenceList
	 *            报关商品资料
	 * @return List 是ContractBom型，合同BOM
	 */
	public List saveContractBom(Request request, ContractExg contractExg,
			List credenceList) {
		return this.contractLogic.saveContractBom(contractExg, credenceList);
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	public List findContractByProcessExe(Request request) {
		return this.contractDao.findContractByProcessExe();
	}

	/**
	 * 查找所有的合同来自正在执行的且来源于相同备案库
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同表头
	 */
	public List findContractByProcessExecorrEmsNo(Request request,
			String corrEmsNo) {
		return this.contractDao.findContractByProcessExecorrEmsNo(corrEmsNo);
	}

	/**
	 * 查找所有的合同来自正在执行的
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是Contract型，合同备案表头
	 */
	public List findContractByProcessExeEndDate(Request request) {
		return this.contractDao.findContractByProcessExeEndDate();
	}

	/**
	 * 查找合同BOM 来自 料件凭证号
	 * 
	 * @param request
	 *            请求控制
	 * @param contractImg
	 *            合同料件
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findContractBomByCredenceNo(Request request,
			ContractImg contractImg) {
		return this.contractDao.findContractBomByImgSeqNum(contractImg);
	}

	/**
	 * 查找合同BOM 来自 料件，成品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param exgSeqNum
	 *            成品序号
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return List 是ContractBom型，合同BOM
	 */
	public ContractBom findContractBomByImgExgSeqNum(Request request,
			String emsNo, String exgSeqNum, String imgSeqNum) {
		return this.contractDao.findContractBomByImgExgSeqNum(emsNo, exgSeqNum,
				imgSeqNum);
	}

	/**
	 * 获得合同成品原料费用
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgId
	 *            合同成品Id
	 * @return Double 合同成品原料费用
	 */
	public Double getFinishProductMaterielFee(Request request,
			String contractExgId) {
		return this.contractLogic.getFinishProductMaterielFee(contractExgId);
	}

	/**
	 * 获得合同成品单位净重
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgId
	 *            合同成品Id
	 * @return Double 合同成品单位净重
	 */
	public Double getFinishProductUnitWeight(Request request,
			String contractExgId) {
		return this.contractLogic.getFinishProductUnitWeight(contractExgId);
	}

	/**
	 * 保存合同Bom资料
	 * 
	 * @param request
	 *            请求控制
	 * @param newContractBom
	 *            新合同BOM
	 * @param oldCotnractBom
	 *            旧合同BOM
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public ContractBom saveContractBom(Request request,
			ContractBom newContractBom) {
		this.contractLogic.saveContractBom(newContractBom);
		return newContractBom;
	}

	/**
	 * 获得最大的成品序号来自当前合同
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 最大的成品序号
	 */
	public int getMaxContractExgSeqNum(Request request, String contractId) {
		return this.contractDao.getMaxContractExgSeqNum(contractId);
	}

	/**
	 * 获得最大的Bom序号来自当前成品
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExgId
	 *            合同成品Id
	 * @return int 最大的Bom序号
	 */
	public int getMaxContractBomSeqNum(Request request, String contractExgId) {
		return this.contractDao.getMaxContractBomSeqNum(contractExgId);
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
	public int getMaxContractImgSeqNum(Request request, String contractId) {
		return this.contractDao.getMaxContractImgSeqNum(contractId);
	}

	/**
	 * 获得最大的合同流水号
	 * 
	 * @param request
	 *            请求控制
	 * @return String 最大的合同流水号
	 */
	public String getMaxPreContractCodeSuffix(Request request) {
		return this.contractDao.getMaxPreContractCodeSuffix();
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWaste(Request request,
			String parentId, int index, int length) {
		return this.contractLogic
				.findContractUnitWaste(parentId, index, length);
	}

	/**
	 * 获得合同单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * 
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteAll(Request request,
			String parentId) {
		return this.contractLogic.findContractUnitWasteAll(parentId);
	}

	/**
	 * 获得合同单耗---(不分组)
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * 
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ImgExgObject> findContractUnitWasteAllNoGroup(Request request,
			String parentId) {
		return this.contractLogic.findContractUnitWasteAllNoGroup(parentId);
	}

	/**
	 * 获得合同未修改单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteByModifyMark(
			Request request, String parentId, int index, int length) {
		return this.contractLogic.findContractUnitWasteByModifyMark(request,
				parentId, index, length);
	}

	/**
	 * 获得合同单耗来自分页,并在打印报表的时候跟据页面大小分组
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @param index
	 *            是开始下标
	 * @param length
	 *            数据长度
	 * @return List 是ContractUnitWaste型，合同成品对应料件单耗表数据
	 */
	public List<ContractUnitWaste> findContractUnitWasteChange(Request request,
			String parentId, int index, int length) {
		return this.contractLogic.findContractUnitWasteChange(parentId, index,
				length);
	}

	/**
	 * 当前合同成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 合同成品记录总数
	 */
	public int findContractExgCount(Request request, String contractId) {
		return this.contractDao.findContractExgCount(contractId);
	}

	/**
	 * 当前合同未修改成品记录总数
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return int 合同成品记录总数
	 */
	public int findContractExgCountByModifyMark(Request request,
			String contractId) {
		return this.contractDao.findContractExgCountByModifyMark(contractId);
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
	public boolean isExistContractByEmsNo(Request request, Contract contract) {
		return this.contractDao.isExistContractByEmsNo(contract);
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
	public Double getImgAmountSum(Request request, String contractId) {
		// 柯鹏程 根据电子化手册的参数设置中的“是否统计备案料件内购金额”进行统计金额
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();

		if (parameter.getIsTotalMoney() == null) {// 等于空时 不统计内购的数量
			return this.contractDao.findContractImgMoney(contractId);
		} else if (parameter.getIsTotalMoney() == true) {// 等于true时 不统计内购的数量
			return this.contractDao.findContractImgTotalMoney(contractId);
		} else {
			return this.contractDao.findContractImgMoney(contractId);
		}

	}

	/**
	 * 获得合同出口总金额
	 * 
	 * @param request
	 *            请求控制
	 * @param contractId
	 *            合同表头Id
	 * @return Double 合同出口总金额
	 */
	public Double getExpAmountSum(Request request, String contractId) {
		return this.contractDao.findContractExgMoney(contractId);
	}

	/**
	 * 保存合同Bom来自凭料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 * @param contractImgList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	public List saveContractBomFromContractImg(Request request,
			ContractExg contractExg, List contractImgList) {
		return this.contractLogic.saveContractBomFromContractImg(contractExg,
				contractImgList);
	}

	/**
	 * 查找所有的合同料件来自不在Bom中存在的
	 * 
	 * @param request
	 *            请求控制
	 * @param contractExg
	 *            合同成品
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgNoInContractBom(Request request,
			ContractExg contractExg) {
		return this.contractDao.findContractImgNoInContractBom(contractExg);
	}

	/**
	 * 合同料件数据取整
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public List saveContractImgAmountInteger(Request request,
			List<ContractImg> list) {
		this.contractLogic.saveContractImgAmountInteger(list);
		return list;
	}

	/**
	 * 查找不存在报关与物料对照表的物料来自料件类型
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param materielType
	 *            物料类别
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
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMergeNotContract(
			Request request, Contract contract, String materielType, int index,
			int length, String property, Object value, Boolean isLike,
			boolean isInnerMerge) {
		return this.contractDao.findBcsTenInnerMergeNotContract(contract,
				materielType, index, length, property, value, isLike,
				isInnerMerge);
	}

	/**
	 * 查找报关与物料对照表
	 * 
	 * @param isInnerMerge
	 * 
	 * @return List 是BcsTenInnerMerge型，报关商品资料
	 */
	public List<BcsTenInnerMerge> findBcsTenInnerMerge(Request request,
			boolean isInnerMerge) {
		return this.contractDao.findBcsTenInnerMerge(isInnerMerge);
	}

	/**
	 * 保存合同料件来自凭证料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param bcsTenInnerMergeList
	 *            报关商品资料
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveContractImg(Request request, Contract contract,
			List bcsTenInnerMergeList) {
		return this.contractLogic.saveContractImg(contract,
				bcsTenInnerMergeList);
	}

	/**
	 * 保存合同料件来自凭证料件
	 * 
	 * @param contract
	 *            合同表头
	 * @param bcsInnerMergeList
	 *            报关商品资料
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveContractImgFromDictPorImg(Request request,
			Contract contract, List dictPorImgList) {
		return this.contractLogic.saveContractImgFromDictPorImg(contract,
				dictPorImgList);
	}

	/**
	 * 保存合同Bom来自报关商品
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param innerMergeList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	public List saveContractBomFromInnerMerge(Request request,
			ContractExg contractExg, List innerMergeList) {
		return this.contractLogic.saveContractBomFromInnerMerge(contractExg,
				innerMergeList);
	}

	/**
	 * 保存合同Bom来自报关商品
	 * 
	 * @param contractExg
	 *            合同成品
	 * @param dictPorImgList
	 *            合同料件
	 * @return List 是ContractBom型，合同Bom
	 */
	public List saveContractBomFromDictPorImg(Request request,
			ContractExg contractExg, List dictPorImgList) {
		return this.contractLogic.saveContractBomFromDictPorImg(contractExg,
				dictPorImgList);
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
	public int findContractImgCount(Request request, String contractId) {
		return this.contractDao.findContractImgCount(contractId);
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
	public int findBcsDictPorImgCount(Request request, String contractId) {
		return this.contractDao.findBcsDictPorImgCount(contractId);
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
	public int findBcsDictPorExgCount(Request request, String contractId) {
		return this.contractDao.findBcsDictPorExgCount(contractId);
	}

	/**
	 * 只显示物料四码不为空的数据
	 * 
	 * @param request
	 *            请求控制
	 * @param type
	 *            物料类别
	 * @param firstIndex
	 *            数据开始下标
	 * @param ptNo
	 *            料号
	 * @return List 是materiel型，报关常用物料
	 */
	public List findMaterielFromInner(Request request, String type,
			int firstIndex, String ptNo) {
		return this.contractDao.findMaterielFromInner(type, firstIndex, ptNo);
	}

	/**
	 * 通过报关商品资料的凭证序号查找合同料件或合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param ptNo
	 *            料号
	 * @param type
	 *            物料类别
	 * @return List 是ContractImg或ContractExg型，合同料件或合同成品
	 */
	public List findBillByMaterielPtNo(Request request, String emsNo,
			String ptNo, String type) {
		return this.contractLogic.findBillByMaterielPtNo(emsNo, ptNo, type);
	}

	/**
	 * 返回合同的总耗用量
	 * 
	 * @param request
	 *            请求控制
	 * @param head
	 *            合同表头
	 * @param imgSeqNum
	 *            料件总表序号
	 * @return double 合同的总耗用量
	 */
	public Double findEmsPorImgTotalNum(Request request, Contract head,
			Integer imgSeqNum) {
		return this.contractDao.findEmsPorImgTotalNum(head, imgSeqNum);
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
	public List saveComtractImgByComplex(Request request, Contract contract,
			List list) {
		return this.contractLogic.saveComtractImgByComplex(contract, list);
	}

	/**
	 * 保存合同成品来自商品编码
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractExg型，合同成品
	 */
	public List saveComtractExgByComplex(Request request, Contract contract,
			List list) {
		return this.contractLogic.saveComtractExgByComplex(contract, list);
	}

	/**
	 * 保存合同BOM来自商品编码
	 * 
	 * @param contract
	 *            合同表头
	 * @param list
	 *            是Complex型，自用商品编码
	 * @return List 是ContractImg型，合同料件
	 */
	public List saveComtractBomByComplex(Request request,
			ContractExg contractExg, List list) {
		return this.contractLogic.saveComtractBomByComplex(contractExg, list);
	}

	/**
	 * 处理合同回执
	 * 
	 * @param request
	 *            请求控制
	 * @param fileName
	 *            回执名称
	 */
	public void processContractData(Request request, String fileName) {
		this.receiveContractLogic.processContractData(fileName);
	}

	/**
	 * 读取回执里的内容
	 * 
	 * @param request
	 *            请求控制
	 * @param fileName
	 *            回执名称
	 * @return List 是String型，存放了回执里的内容
	 */
	public List<String> getContractFileList(Request request, String fileName) {
		return this.receiveContractLogic.getContractFileList(fileName);
	}

	/**
	 * 返回合同单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param exgList
	 *            是ContractExg型，合同成品
	 * @return List 是ContractBom型，合同BOM
	 */
	public List findUnitWaste(Request request, List exgList) {
		return this.contractLogic.findUnitWaste(exgList);
	}

	/**
	 * 从设置ftp地址、用户、密码然后下载
	 * 
	 * @param request
	 *            请求控制
	 * @return List 下载的文件
	 */
	public List ftpDownload(Request request) {
		return this.receiveContractLogic.ftpDownload();
	}

	/**
	 * 从设置ftp地址、用户、密码然后下载
	 * 
	 * @param request
	 *            请求控制
	 * @return List 下载的文件
	 */
	public List ftpContractDownload(Request request) {
		return this.receiveContractLogic.ftpContractDownload();
	}

	/**
	 * 查找未处理的回执
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是String型，回执名称
	 * 
	 */
	public List findNotProcessedFile(Request request) {
		return this.receiveContractLogic.findNotProcessedFile();
	}

	/**
	 * 查找已处理的回执
	 * 
	 * @param request
	 *            请求控制
	 * @return List 是String型，回执名称
	 */
	public List findProcessedFile(Request request) {
		return this.receiveContractLogic.findProcessedFile();
	}

	/**
	 * 变更合同料件序号
	 * 
	 * @param request
	 *            请求控制
	 * @param img
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeContractImgSeqNum(Request request, Contract contract,
			ContractImg img, Integer newSeqNum, String modifyMark) {
		this.contractLogic.changeContractImgSeqNum(contract, img, newSeqNum,
				modifyMark);
		// return img;
	}

	/**
	 * 变更合同成品序号
	 * 
	 * @param request
	 *            请求控制
	 * @param exg
	 *            合同成品
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeContractExgSeqNum(Request request, Contract contract,
			ContractExg exg, Integer newSeqNum, String modifyMark) {
		this.contractLogic.changeContractExgSeqNum(contract, exg, newSeqNum,
				modifyMark);
		// return exg;
	}

	/**
	 * 转抄合同成品单耗
	 * 
	 * @param request
	 *            请求控制
	 * @param exgFrom
	 *            要被拷贝的合同成品
	 * @param exgTo
	 *            要拷贝的合同成品
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void copyContractUnitWaste(Request request, ContractExg exgFrom,
			ContractExg exgTo) {
		this.contractLogic.copyContractUnitWaste(exgFrom, exgTo);
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgBySeqNum(Request request, String emsNo,
			String seqNum) {
		return this.contractDao.findContractExgBySeqNum(emsNo, seqNum);
	}

	/**
	 * 根据帐册编号、成品序号查找执行的合同成品
	 * 
	 * @param request
	 *            请求控制
	 * @param emsNo
	 *            帐册编号
	 * @param seqNum
	 *            成品序号
	 * @return List 是ContractExg型，合同成品
	 */
	public List findDzscEmsExgBillBySeqNum(Request request, String emsNo,
			String seqNum) {
		return this.contractDao.findDzscEmsExgBillBySeqNum(emsNo, seqNum);
	}

	/**
	 * 根据合同单耗自动反算成品，料件的数量，金额等
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 * @param isCalculateFinishProductSum
	 *            是否要反算成品总金额，true为要反算
	 * @param isCalculateMaterielSum
	 *            是否要反算成品总金额，true为要反算
	 */
	public void reCalContractUnitWaste(Request request, Contract contract) {
		this.contractLogic.reCalContractUnitWaste(contract);
	}

	/**
	 * 更改合同商品的名称规格
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
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
			Contract contract, boolean isMaterial, Integer seqNum, String name,
			String spec) {
		return this.contractLogic.changeContractCommNameSpec(contract,
				isMaterial, seqNum, name, spec);
	}

	/**
	 * 查找合同成品 来自 合同表头Id
	 * 
	 * @param request
	 *            请求控制
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractExg型，合同成品
	 */
	public List findTempContractExgByParentId(Request request, String parentId) {
		return this.contractLogic.findTempContractExgByParentId(parentId);
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
	public List findTempContractImgByParentId(Request request, String parentId) {
		return this.contractLogic.findTempContractImgByParentId(parentId);
	}

	/**
	 * 转抄合同成品(包括BOM)数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public Contract copyContractProduct(Request request, Contract contract,
			List<ContractExg> contractExgList) {
		return this.contractLogic
				.copyContractProduct(contract, contractExgList);
	}

	/**
	 * 转抄合同成品料件(不包括BOM)数据
	 * 
	 * @param list
	 *            是Contract型，合同表头
	 */
	public Contract copyContractProductMateriel(Request request,
			Contract contract, List<ContractExg> contractExgList,
			List<ContractImg> contractImgList) {
		return this.contractLogic.copyContractProductMateriel(contract,
				contractExgList, contractImgList);
	}

	/**
	 * 合同备案海关申报
	 * 
	 * @param head
	 *            通关备案表头
	 * @return DzscEmsPorHead 通关备案表头
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--海关申报", index = 7.3)
	public DeclareFileInfo applyContract(Request request, Contract head) {
		return this.contractLogic.applyContract(head, request.getTaskId());
	}

	/**
	 * 合同备案回执处理
	 * 
	 * @param contract
	 * @param exingContract
	 * @return String是String型，合同备案回执
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--处理回执", index = 7.4)
	public void applyContractcheck(Request request) {

	}

	public String processContract(Request request, final Contract contract,
			final Contract exingContract, List lsReturnFile) {
		return this.contractLogic.processContract(contract, exingContract,
				lsReturnFile);
	}

	/**
	 * 查询不在合同里面的备案资料库的商品资料
	 * 
	 * @param materielType
	 * @param contractId
	 * @param dictPorEmsNo
	 * @return
	 */
	public List findDictPorImgExgNotInContract(Request request,
			String materielType, String contractId, String dictPorEmsNo,
			boolean isFilt) {
		return this.contractLogic.findDictPorImgExgNotInContract(materielType,
				contractId, dictPorEmsNo, isFilt);
	}

	/**
	 * 查询不在合同BOM里面的备案资料库的商品资料
	 * 
	 * @param materielType
	 * @param contractId
	 * @param dictPorEmsNo
	 * @return
	 */
	public List findDictPorImgNotInContractBom(Request request, String exgId,
			String dictPorEmsNo) {
		return this.contractLogic.findDictPorImgNotInContractBom(exgId,
				dictPorEmsNo);
	}

	/**
	 * 根据商品编码，名称，规格查询合同成品
	 * 
	 * @param request
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	public List findContractExgByname(Request request, Contract head,
			String code, String name, String spec) {
		return contractDao.findContractExgByname(head, code, name, spec);
	}

	/**
	 * 根据料号查询BOM
	 * 
	 * @param request
	 * @param ptno
	 * @return
	 */
	public List findBcsInnerMergeByptno(Request request, String ptno) {
		return contractDao.findBcsInnerMergeByptno(ptno);
	}

	/**
	 * 根据商品编码，名称，规格查询合同料件
	 * 
	 * @param request
	 * @param code
	 * @param name
	 * @param spec
	 * @return
	 */
	public List findContractImgByname(Request request, Contract head,
			String code, String name, String spec) {
		return contractDao.findContractImgByname(head, code, name, spec);
	}

	/**
	 * 通过成品以及料件总表序号查询BOM
	 * 
	 * @param contractExg
	 * @param contractImgSeqNum
	 * @return ContractBom 是ContractBom型,合同Bom
	 */
	public ContractBom findContractBom(Request request,
			ContractExg contractExg, Integer contractImgSeqNum) {
		return contractDao.findContractBom(contractExg, contractImgSeqNum);
	}

	/**
	 * 通过状态查询合同
	 * 
	 * @param declareState
	 * @return List是Contract，合同备案表头
	 */
	public List findcontract(Request request, String declareState) {
		return contractDao.findcontract(declareState);
	}

	/**
	 * 取得物料信息
	 * 
	 * @param materieltype
	 * @return List是BcsInnerMerge， BCS内部归并对应表
	 */
	public List findBcsInnerMerge(Request request, String materieltype) {
		return contractDao.findBcsInnerMerge(materieltype);
	}

	/**
	 * 合同料件进出平衡检查
	 * 
	 * @param contract
	 * @return List 是TempImgAndExgUsedDiffAmount型
	 */
	public List findContractImgAndExgUsedDiffAmount(Request request,
			Contract contract) {
		return this.contractLogic.findContractImgAndExgUsedDiffAmount(contract);
	}

	/**
	 * 查询变更的进口料件
	 * 
	 * @param contract
	 * @return List[]
	 */
	public List[] findChangedContractImg(Request request, Contract contract) {
		return this.contractLogic.findChangedContractImg(contract);
	}

	/**
	 * 查询变更的出口成品
	 * 
	 * @param contract
	 * 
	 * @return List[]
	 */
	public List[] findChangedContractExg(Request request, Contract contract) {
		return this.contractLogic.findChangedContractExg(contract);
	}

	/**
	 * 导入料件
	 * 
	 * @param ls
	 * 
	 * @param isOverwrite
	 * 
	 * @return
	 */
	public void saveBcsImgFromImport(Request request, List ls,
			boolean isOverwrite) {
		contractLogic.saveBcsImgFromImport(ls, isOverwrite);
	}

	/**
	 * 导入成品
	 * 
	 * @param ls
	 * @param isOverwrite
	 * @return
	 */
	public void saveBcsExgFromImport(Request request, List ls,
			boolean isOverwrite) {
		contractLogic
				.saveBcsExgFromImport(ls, isOverwrite, request.getTaskId());
	}

	/**
	 * 导入单耗
	 * 
	 * @param ls
	 * @param isOverwrite
	 * @return
	 */
	public void saveContractEmsBomFromImport(Request request, List ls,
			boolean isOverwrite) {
		this.contractLogic.saveContractEmsBomFromImport(ls, isOverwrite);
	}

	/**
	 * 统计出口总金额
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public void getTotalPriceBExport(Request request, Contract contract) {
		this.contractLogic.getTotalPriceBExport(contract);
	}

	/**
	 * 统计进口总金额
	 * 
	 * @param request
	 * @param contract
	 * @return
	 */
	public void getTotalPriceBImport(Request request, Contract contract) {
		this.contractLogic.getTotalPriceBImport(contract);
	}

	/**
	 * 查询通关合同备案中的BOM料件
	 * 
	 * @param request
	 * @param contract
	 * @return List是ContractBom,合同BOM
	 */
	public List findBomDetailMaterialInContract(Request request, List list) {
		return this.contractDao.findBomDetailMaterialInContract(list);
	}

	/**
	 * 查询通关合同备案中的料件
	 * 
	 * @param request
	 * @param list
	 * @return List 是ContractImg，合同料件
	 */
	public List findMaterialInContract(Request request, List list) {
		return this.contractLogic.findMaterialInContract(list);
	}

	/**
	 * 根据起止成品序号查询合同BOM资料,去除未修改的
	 * 
	 * @param contract
	 * @param be
	 * @param en
	 * @param declareStateCHANGING_EXE
	 * @return List是ContractBom,合同BOM
	 */
	public List findContractBomBySeq(Request request, Contract contract,
			Integer be, Integer en, Boolean declareStateCHANGING_EXE,
			Boolean declareStateWAIT_EAA) {
		return this.contractDao.findContractBomBySeq(contract, be, en,
				declareStateCHANGING_EXE, declareStateWAIT_EAA);
	}

	/**
	 * 根据报关单头手册号,凭证序号,商品编码,查询报关单表头流水号
	 * 
	 * @param id
	 * @param number
	 * @param code
	 * @return List 是BcsCustomsDeclarationCommInfo，BCS报关单体
	 */
	public List findBcsCustomsDeclarationCommInfo(Request request, String id,
			Integer commSerialNo, String complexCode, Integer impExpFlag,
			Integer impExpType) {
		return this.contractDao.findBcsCustomsDeclarationCommInfo(id,
				commSerialNo, complexCode, impExpFlag, impExpType);
	}

	/**
	 * 改变通关手册表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--改变申报状态", index = 7)
	public Contract changeContractDeclareState(Request request, Contract head,
			String declareState) {
		return this.contractLogic
				.changeContractDeclareState(head, declareState);
	}

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--改变修改标志", index = 7)
	public void changeContractImgModifyMark(Request request, List list,
			String modifyMark) {
		this.contractLogic.changeContractImgModifyMark(list, modifyMark);
	}

	/**
	 * 修改通关手册成品的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--改变修改标志", index = 7)
	public void changeContractExgModifyMark(Request request, List list,
			String modifyMark) {
		this.contractLogic.changeContractExgModifyMark(list, modifyMark);
	}

	/**
	 * 修改通关手册单耗的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	@AuthorityFunctionAnnotation(caption = "通关手册备案--改变修改标志", index = 7)
	public void changeContractBomModifyMark(Request request, List list,
			String modifyMark) {
		this.contractLogic.changeContractBomModifyMark(list, modifyMark);
	}

	/**
	 * 查找合同料件 来自 合同表头ID,修改状态为正在变更的
	 * 
	 * @param parentId
	 *            合同表头Id
	 * @param declareStateCHANGING_EXE
	 *            合同状态
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractExgBy(Request request, String parentId,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA) {
		return this.contractDao.findContractExgBy(parentId,
				declareStateCHANGING_EXE, declareStateWAIT_EAA);
	}

	/**
	 * 查找合同料件 来自 合同表头ID,修改状态为正在变更的
	 * 
	 * @param parentId
	 *            合同表头Id
	 * @param declareStateCHANGING_EXE
	 *            合同状态
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgBy(Request request, String parentId,
			Boolean declareStateCHANGING_EXE, Boolean declareStateWAIT_EAA) {
		return this.contractDao.findContractImgBy(parentId,
				declareStateCHANGING_EXE, declareStateWAIT_EAA);
	}

	/**
	 * 从QP的导出文件中导入通关手册资料
	 * 
	 * @param excelFileContent
	 * @param declareState
	 * @param dictPorEmsNo
	 * @param limitFlag
	 * @param taskId
	 * @return Contract类型，合同备案表头
	 */
	public Contract importContractFromQPExportFile(Request request,
			byte[] excelFileContent, String declareState, String dictPorEmsNo,
			String limitFlag, String taskId, boolean isCover) {
		return this.contractLogic.importContractFromQPExportFile(
				excelFileContent, declareState, dictPorEmsNo, limitFlag,
				taskId, isCover);
	}

	/**
	 * 查找国内购买料件
	 * 
	 * @param parentId
	 *            合同表头Id
	 * @return List 是ContractBom型，合同Bom
	 */
	public List findContractDomesticPurchaseBill(Request request,
			String parentId) {
		return this.contractDao.findContractDomesticPurchaseBill(parentId);
	}

	/**
	 * 设置合同国内购料清单表
	 * 
	 * @param parentId
	 *            合同表头Id
	 * @return List 是TempContractDomesticPurchaseBill型，存放合同国内购料清单的临时实体类
	 */
	public List setContractDomesticPurchaseBill(Request request, String parentId) {
		return this.contractLogic.setContractDomesticPurchaseBill(parentId);
	}

	/**
	 * 从QP中查询电子化手册备案资料库表头
	 * 
	 * @param request
	 * @return
	 */
	public List findBcsQPDictPorHead(Request request) {
		return this.bcsImportQPDataLogic.findBcsQPDictPorHead();
	}

	/**
	 * 从QP中查询电子化手册通关备案表头
	 * 
	 * @param request
	 * 
	 * @return
	 */
	public List findBcsQPContract(Request request) {
		return this.bcsImportQPDataLogic.findBcsQPContract();
	}

	/**
	 * 从QP中导入电子化手册备案资料库
	 * 
	 * @param listPtsEmsWjHead
	 * 
	 * @param isOverWrite
	 * 
	 * @return
	 */
	public void importBcsDictPorHeadFromQP(Request request,
			List listPtsEmsWjHead, boolean isOverWrite) {
		this.bcsImportQPDataLogic.importBcsDictPorHeadFromQP(listPtsEmsWjHead,
				isOverWrite);
	}

	/**
	 * 从QP中导入电子化手册通关备案
	 * 
	 * @param listPtsEmsHead
	 * 
	 * @param isOverWrite
	 * 
	 * @return
	 */
	public void importContractFromQP(Request request, List listPtsEmsHead,
			boolean isOverWrite) {
		this.bcsImportQPDataLogic.importContractFromQP(listPtsEmsHead,
				isOverWrite);
	}

	/**
	 * 查找合同变更料件
	 * 
	 * @param request
	 * @param contract
	 * @return List 是ContractExg型，合同成品
	 */
	public List findConractImgChangedByContract(Request request,
			Contract contract) {
		return this.contractLogic.findConractImgChangedByContract(contract);
	}

	/**
	 * 查找合同变更成品
	 * 
	 * @param request
	 * @param contract
	 * @return List 是ContractExg型，合同成品
	 */
	public List findConractExgChangedByContract(Request request,
			Contract contract) {
		return this.contractLogic.findConractExgChangedByContract(contract);
	}

	/**
	 * 查找合同变更后成品
	 * 
	 * @param request
	 * @param contract
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgChangeAfterByContract(Request request,
			Contract contract) {
		return contractLogic.findContractExgChangeAfterByContract(request,
				contract);
	}

	/**
	 * 查找合同变更前成品
	 * 
	 * @param request
	 * @param contract
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractExgChangeBeforeByContract(Request request,
			Contract contract) {
		return contractLogic.findContractExgChangeBeforeByContract(request,
				contract);
	}

	/**
	 * 查找合同变更后料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgChangeAfterByContract(Request request,
			Contract contract) {
		return contractLogic.findContractImgChangeAfterByContract(request,
				contract);
	}

	/**
	 * 查找合同变更前料件
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同备案表头
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgChangeBeforeByContract(Request request,
			Contract contract) {
		return contractLogic.findContractImgChangeBeforeByContract(request,
				contract);
	}

	/**
	 * 查询合同料件根据序号
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * 
	 * @return List 是ContractImg型，合同料件
	 */
	public List findContractImgBySeqNum(Request request, String parentId) {
		return this.contractDao.findContractImgBySeqNum(parentId);
	}

	/**
	 * 反写入料件总表同时反写BOM
	 * 
	 * @param parentId
	 *            合同备案表头Id
	 * @param parentId
	 *            合同成品
	 * @return List 是ContractExg型，合同成品
	 */
	public List writeToImgAndBomTable(Request request, String parentId,
			ContractExg exg, List bomList) {
		return this.contractLogic.writeToImgAndBomTable(parentId, exg, bomList);
	}

	/**
	 * 查询合同Bom中的料件总表序号
	 * 
	 * @param exg
	 *            合同成品
	 * @return List 是ContractExg型，合同成品
	 */
	public List findContractImgSeqNumByExgAndContract(Request request,
			ContractExg exg) {
		return this.contractDao.findContractImgSeqNumByExgAndContract(exg);
	}

	/**
	 * 判断货币
	 */
	public List findCurrRateByCurr(Request request, String curr1) {

		return this.contractDao.findCurrRateByCurr(curr1);
	}

	/**
	 * 判断货币
	 */
	public List findCurrRate(Request request, String curr, String curr1) {

		return this.contractDao.findCurrRate(curr, curr1);
	}

	/**
	 * 判断货币
	 */
	public List findCurrRateByDeclarationDate(Request request, String curr,
			String curr1, Date declarationDate) {

		return this.contractDao.findCurrRateByDeclarationDate(curr, curr1,
				declarationDate);
	}

	/**
	 * 获取手册商品
	 * 
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public List getTempContractForBid(Request request, boolean isMaterial,
			String emsNo) {
		return this.contractLogic.getTempContractForBid(isMaterial, emsNo);
	}

	/**
	 * 新增禁用商品
	 * 
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public List addCommdityForbid(Request request, List list,
			boolean isMaterial, String emsNo) {
		return this.contractLogic.addCommdityForbid(list, isMaterial, emsNo);
	}

	/**
	 * 恢复手册商品
	 * 
	 * @param request
	 * @param emsNo
	 * @param seqNum
	 * @param isMaterial
	 * @param b
	 * @param version
	 */
	public void changeEmsEdiForbid(Request request, String emsNo,
			String seqNum, boolean isMaterial) {
		this.contractLogic.changeEmsEdiForbid(emsNo, seqNum, isMaterial, false);
	}

	/**
	 * 删除禁用商品
	 * 
	 * @param isMaterial
	 * @param emsNo
	 * @return
	 */
	public void deleteCommdityForbid(Request request, BcsCommdityForbid obj) {
		this.contractLogic.deleteCommdityForbid(obj);
	}

	/**
	 * 查找禁用商品
	 * 
	 * @param request
	 * @param isMateriel
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	public List findCommdityForbid(Request request, String emsNo,
			boolean isMateriel, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return contractDao.findCommdityForbid(emsNo, isMateriel, beginDate,
				endDate);
	}

	@AuthorityFunctionAnnotation(caption = "报关单删单查询", index = 12)
	public void brownDeleteCustoms(Request request) {
		// TODO Auto-generated method stub

	}

	@AuthorityFunctionAnnotation(caption = "进出口申请单", index = 8)
	public void findImpExpRequestBill(Request request) {
		// TODO Auto-generated method stub
	}

	/**
	 * 判断合同成品表、及单耗表的修改标志是否一致
	 * 
	 * @param request
	 *            请求控制
	 * @param contract
	 *            合同表头
	 */
	public String checkContractIsUnitModifyMarkExgBom(Request request,
			Contract contract) {
		return this.contractLogic.checkContractIsUnitModifyMarkExgBom(contract);
	}

	/**
	 * 判断进口合同号是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号
	 */
	public boolean checkImpContractNoIsExist(Request request, String contractNo) {
		return this.contractLogic.checkImpContractNoIsExist(contractNo);
	}

	/**
	 * 判断出口合同号是否存在
	 * 
	 * @param request
	 *            请求控制
	 * @param contractNo
	 *            合同号
	 */
	public boolean checkExpContractNoIsExist(Request request, String contractNo) {
		return this.contractLogic.checkExpContractNoIsExist(contractNo);
	}

	/**
	 * 查询是否显示出口装箱单或者发票
	 */
	public List findExportPackinglistOrInvoice(Request request) {
		return this.contractDao.findExportPackinglistOrInvoice();
	}

}
