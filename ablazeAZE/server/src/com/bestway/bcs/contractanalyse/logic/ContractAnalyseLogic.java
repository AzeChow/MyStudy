/*
 * Created on 2005-6-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractanalyse.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractanalyse.dao.ContractAnalyseDao;
import com.bestway.bcs.contractanalyse.entity.CommInfoImpExp;
import com.bestway.bcs.contractanalyse.entity.ContractStat;
import com.bestway.bcs.contractanalyse.entity.ExpFinishProductProgressDetail;
import com.bestway.bcs.contractanalyse.entity.ExpFinishProductProgressTotal;
import com.bestway.bcs.contractanalyse.entity.ExpFinishProductStat;
import com.bestway.bcs.contractanalyse.entity.ImpExpCustomsDeclarationList;
import com.bestway.bcs.contractanalyse.entity.ImpMaterielExeDetail;
import com.bestway.bcs.contractanalyse.entity.ImpMaterielExeStat;
import com.bestway.bcs.contractanalyse.entity.ImpMaterielExeState;
import com.bestway.bcs.contractanalyse.entity.StorageContractStat;
import com.bestway.bcs.contractanalyse.entity.StorageStat;
import com.bestway.bcs.contractanalyse.entity.TempContractImg;
import com.bestway.bcs.contractanalyse.entity.TempExpContractStat;
import com.bestway.bcs.contractanalyse.entity.TempFinishProduct;
import com.bestway.bcs.contractanalyse.entity.TempImpContractStat;
import com.bestway.bcs.contractanalyse.entity.TempImpMaterielExeStat;
import com.bestway.bcs.contractanalyse.entity.WrapStat;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractstat.dao.ContractStatDao;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.logic.ContractStatLogic;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.SearchType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;

/**
 * 报关分析Logic checked by kcb 2008-9-9
 * 
 * @author kcb
 */
public class ContractAnalyseLogic {
	/**
	 * 合同统计DAO
	 */
	private ContractStatDao contractStatDao = null;
	/**
	 * 合同分析DAO
	 */
	private ContractAnalyseDao contractAnalyseDao = null;
	/**
	 * 合同管理DAO
	 */
	private ContractDao contractDao = null;
	/**
	 * 合同统计业务逻辑层
	 */
	private ContractStatLogic contractStatLogic = null;

	/**
	 * 获取contractStatDao
	 * 
	 * @return Returns the contractStatDao.
	 */
	public ContractStatDao getContractStatDao() {
		return contractStatDao;
	}

	/**
	 * 获取contractDao
	 * 
	 * @return Returns the contractDao.
	 */
	public ContractDao getContractDao() {
		return contractDao;
	}

	/**
	 * 设置contractDao
	 * 
	 * @param contractDao
	 */
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	/**
	 * 设置contractStatDao
	 * 
	 * @param contractStatDao
	 */
	public void setContractStatDao(ContractStatDao contractStatDao) {
		this.contractStatDao = contractStatDao;
	}

	/**
	 * 获取contractAnalyseDao
	 * 
	 * @return Returns the contractAnalyseDao.
	 */
	public ContractAnalyseDao getContractAnalyseDao() {
		return contractAnalyseDao;
	}

	/**
	 * 设置contractAnalyseDao
	 * 
	 * @param contractAnalyseDao
	 */
	public void setContractAnalyseDao(ContractAnalyseDao contractAnalyseDao) {
		this.contractAnalyseDao = contractAnalyseDao;
	}

	/**
	 * 获取 contractStatLogic
	 * 
	 * @return
	 */
	public ContractStatLogic getContractStatLogic() {
		return contractStatLogic;
	}

	/**
	 * 设置 contractStatLogic
	 * 
	 * @return
	 */
	public void setContractStatLogic(ContractStatLogic contractStatLogic) {
		this.contractStatLogic = contractStatLogic;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isImport
	 *            判断是否进口类型，true为进口
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
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findCommInfoImpExpList(boolean isImport, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			Contract contract) {

		List<CommInfoImpExp> returnList = new ArrayList<CommInfoImpExp>();
		String emsNo = null;
		if (contract != null) {
			emsNo = contract.getEmsNo();
		}
		List list = this.contractStatDao.findImpExpCommInfoList(isImport,
				seqNum, customer, impExpType, beginDate, endDate, emsNo,
				CustomsDeclarationState.EFFECTIVED);
		for (int i = 0; i < list.size(); i++) {
			CommInfoImpExp commInfoImpExp = new CommInfoImpExp();
			BcsCustomsDeclarationCommInfo bcdc = (BcsCustomsDeclarationCommInfo) list
					.get(i);
			commInfoImpExp.setComplex(bcdc.getComplex());
			commInfoImpExp.setCommName(bcdc.getCommName());
			commInfoImpExp.setCommSpec(bcdc.getCommSpec());
			commInfoImpExp.setContract(bcdc.getBaseCustomsDeclaration()
					.getContract());
			commInfoImpExp.setImpExpType(bcdc.getBaseCustomsDeclaration()
					.getImpExpType());
			commInfoImpExp.setDeclarationDate(bcdc.getBaseCustomsDeclaration()
					.getDeclarationDate());
			commInfoImpExp.setCommAmount(bcdc.getCommAmount());
			commInfoImpExp.setUnitName(bcdc.getUnit());
			commInfoImpExp.setCommTotalPrice(bcdc.getCommTotalPrice());
			commInfoImpExp.setCustomsDeclarationCode(bcdc
					.getBaseCustomsDeclaration().getCustomsDeclarationCode());
			commInfoImpExp.setScmCoc(bcdc.getBaseCustomsDeclaration()
					.getCustomer());
			commInfoImpExp.setConveyance(bcdc.getBaseCustomsDeclaration()
					.getConveyance());
			commInfoImpExp.setUnitWeight(bcdc.getUnitWeight());
			commInfoImpExp.setSecondAmount(bcdc.getSecondAmount());
			commInfoImpExp.setSecondLegalUnit(bcdc.getSecondLegalUnit());
			commInfoImpExp.setCommSerialNo(bcdc.getCommSerialNo());
			commInfoImpExp.setWrapType(bcdc.getBaseCustomsDeclaration()
					.getWrapType());
			commInfoImpExp.setGrossWeight(bcdc.getGrossWeight());
			commInfoImpExp.setNetWeight(bcdc.getNetWeight());
			BaseCustomsDeclaration baseCustomsDeclaration = bcdc
					.getBaseCustomsDeclaration();
			if (baseCustomsDeclaration != null) {
				commInfoImpExp.setAuthorizeFile(baseCustomsDeclaration
						.getAuthorizeFile());
				ContractExg contractExg = contractDao.findContractExgByBcs(
						bcdc, baseCustomsDeclaration.getEmsHeadH2k());
				if (contractExg != null) {
					Double processUnitPrice = CommonUtils
							.getDoubleExceptNull(contractExg
									.getProcessUnitPrice());
					commInfoImpExp.setProcessUnitPrice(processUnitPrice);
					commInfoImpExp.setProcessTotalPrice(processUnitPrice
							* CommonUtils.getDoubleExceptNull(bcdc
									.getCommAmount()));
				}
			}
			returnList.add(commInfoImpExp);// TODO
		}

		/**
		 * 加一条空的记录
		 */
		returnList.add(new CommInfoImpExp());
		/**
		 * 加入统计记录
		 */
		CommInfoImpExp ciie = makeCommInfoImpExp(returnList);
		returnList.add(ciie);
		return returnList;
	}

	/**
	 * 存放料件分析的资料
	 * 
	 * @param returnList
	 *            是List<CommInfoImpExp>型，
	 * @return List 是CommInfoImpExp型，存放料件分析统计的资料
	 */
	private CommInfoImpExp makeCommInfoImpExp(List<CommInfoImpExp> returnList) {

		// 数量
		Double commAmount = 0.0;
		// 毛重
		Double grossWeight = 0.0;
		// 净重
		Double netWeight = 0.0;
		// 价值
		Double commTotalPrice = 0.0;
		// 加工费总值
		Double processTotal = 0.0;
		// 合同金额
		Double currencyCommTotalPrice = 0.0;

		for (int i = 0; i < returnList.size(); i++) {
			CommInfoImpExp commInfoImpExp = (CommInfoImpExp) returnList.get(i);
			if (commInfoImpExp.getImpExpType() != null
					&& commInfoImpExp.getImpExpType() == ImpExpType.REMAIN_FORWARD_EXPORT) {
				commAmount -= commInfoImpExp.getCommAmount() == null ? 0.0
						: commInfoImpExp.getCommAmount(); // 数量
				commTotalPrice -= commInfoImpExp.getCommTotalPrice() == null ? 0.0
						: commInfoImpExp.getCommTotalPrice(); // 价值
				grossWeight -= commInfoImpExp.getGrossWeight() == null ? 0.0
						: commInfoImpExp.getGrossWeight(); // 毛重
				netWeight -= commInfoImpExp.getNetWeight() == null ? 0.0
						: commInfoImpExp.getNetWeight(); // 净重

			} else {// 在余料结转出口报关单情况下，减去相关数量---
				commAmount += commInfoImpExp.getCommAmount() == null ? 0.0
						: commInfoImpExp.getCommAmount(); // 数量
				commTotalPrice += commInfoImpExp.getCommTotalPrice() == null ? 0.0
						: commInfoImpExp.getCommTotalPrice(); // 价值
				grossWeight += commInfoImpExp.getGrossWeight() == null ? 0.0
						: commInfoImpExp.getGrossWeight(); // 毛重
				netWeight += commInfoImpExp.getNetWeight() == null ? 0.0
						: commInfoImpExp.getNetWeight(); // 净重
			}
			processTotal += CommonUtils.getDoubleExceptNull(commInfoImpExp
					.getProcessTotalPrice());
			currencyCommTotalPrice += commInfoImpExp
					.getCurrencyCommTotalPrice() == null ? 0.0 : commInfoImpExp
					.getCurrencyCommTotalPrice();
			// System.out.println( canDirectImpAmount);
		}
		CommInfoImpExp commInfoImpExp = new CommInfoImpExp();
		commInfoImpExp.setCommName("合计");
		commInfoImpExp.setCommAmount(commAmount);
		commInfoImpExp.setCommTotalPrice(commTotalPrice);
		commInfoImpExp.setGrossWeight(grossWeight);
		commInfoImpExp.setNetWeight(netWeight);
		commInfoImpExp.setProcessTotalPrice(processTotal);
		commInfoImpExp.setCurrencyCommTotalPrice(currencyCommTotalPrice);
		return commInfoImpExp;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isImport
	 *            判断是否进口类型，true为进口
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
	 * @param lsContract
	 *            是List型，合同备案表头
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclarationCommInfo型，报关单物料资料
	 */
	public List findCommInfoImpExpList(boolean isImport, String code,
			String name, String spec, String unitName, Integer seqNo,
			String customer, String impExpType, Date beginDate, Date endDate,
			List lsContract, int state) {

		List<CommInfoImpExp> returnList = new ArrayList<CommInfoImpExp>();

		List list = this.contractStatDao.findImpExpCommInfoList(isImport, code,
				name, spec, unitName, seqNo, customer, impExpType, beginDate,
				endDate, lsContract, state);
		for (int i = 0; i < list.size(); i++) {
			CommInfoImpExp commInfoImpExp = new CommInfoImpExp();
			BcsCustomsDeclarationCommInfo bcdc = (BcsCustomsDeclarationCommInfo) list
					.get(i);

			commInfoImpExp.setComplex(bcdc.getComplex());
			commInfoImpExp.setCommName(bcdc.getCommName());
			commInfoImpExp.setCommSpec(bcdc.getCommSpec());
			commInfoImpExp.setContract(bcdc.getBaseCustomsDeclaration()
					.getContract());
			commInfoImpExp.setImpExpType(bcdc.getBaseCustomsDeclaration()
					.getImpExpType());
			commInfoImpExp.setDeclarationDate(bcdc.getBaseCustomsDeclaration()
					.getDeclarationDate());
			commInfoImpExp.setCommAmount(bcdc.getCommAmount());
			commInfoImpExp.setUnitName(bcdc.getUnit());
			commInfoImpExp.setCommTotalPrice(bcdc.getCommTotalPrice());
			commInfoImpExp.setCustomsDeclarationCode(bcdc
					.getBaseCustomsDeclaration().getCustomsDeclarationCode());
			commInfoImpExp.setScmCoc(bcdc.getBaseCustomsDeclaration()
					.getCustomer());
			commInfoImpExp.setConveyance(bcdc.getBaseCustomsDeclaration()
					.getConveyance());
			commInfoImpExp.setUnitWeight(bcdc.getUnitWeight());
			commInfoImpExp.setSecondAmount(bcdc.getSecondAmount());
			commInfoImpExp.setSecondLegalUnit(bcdc.getSecondLegalUnit());
			commInfoImpExp.setCommSerialNo(bcdc.getCommSerialNo());
			commInfoImpExp.setWrapType(bcdc.getBaseCustomsDeclaration()
					.getWrapType());
			commInfoImpExp.setGrossWeight(bcdc.getGrossWeight());
			commInfoImpExp.setNetWeight(bcdc.getNetWeight());
			commInfoImpExp.setFirstAmount(bcdc.getFirstAmount());
			commInfoImpExp.setFirstLegalUnit(bcdc.getLegalUnit());
			commInfoImpExp.setInvoiceCode(bcdc.getBaseCustomsDeclaration()
					.getInvoiceCode());
			if (null != bcdc.getBaseCustomsDeclaration().getCurrency()) {
				commInfoImpExp.setCurrency(bcdc.getBaseCustomsDeclaration()
						.getCurrency().getName());
			}
			if (null != bcdc.getCommTotalPrice()) {
				commInfoImpExp
						.setCurrencyCommTotalPrice(bcdc.getCommTotalPrice()
								* (bcdc.getBaseCustomsDeclaration()
										.getExchangeRate() == null ? 1 : bcdc
										.getBaseCustomsDeclaration()
										.getExchangeRate()));
			} else {
				commInfoImpExp.setCurrencyCommTotalPrice(0.0);
			}
			BaseCustomsDeclaration baseCustomsDeclaration = bcdc
					.getBaseCustomsDeclaration();
			if (baseCustomsDeclaration != null) {
				commInfoImpExp.setAuthorizeFile(baseCustomsDeclaration
						.getAuthorizeFile());
				commInfoImpExp.setInvoiceCode(baseCustomsDeclaration
						.getInvoiceCode() == null ? "0"
						: baseCustomsDeclaration.getInvoiceCode());
				ContractExg contractExg = contractDao.findContractExgByBcs(
						bcdc, baseCustomsDeclaration.getEmsHeadH2k());
				if (contractExg != null) {
					Double processUnitPrice = CommonUtils
							.getDoubleExceptNull(contractExg
									.getProcessUnitPrice());
					commInfoImpExp.setProcessUnitPrice(processUnitPrice);
					commInfoImpExp.setProcessTotalPrice(processUnitPrice
							* CommonUtils.getDoubleExceptNull(bcdc
									.getCommAmount()));
				}
			}
			returnList.add(commInfoImpExp);// TODO
		}

		/**
		 * 加一条空的记录
		 */
		returnList.add(new CommInfoImpExp());
		/**
		 * 加入统计记录
		 */
		CommInfoImpExp ciie = makeCommInfoImpExp(returnList);
		returnList.add(ciie);
		return returnList;
	}

	/**
	 * 进口包装统计
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param wrapCode
	 *            包装种类编码
	 * @param state
	 *            状态类型
	 * @return List 是WrapStat型，进口包装统计
	 */
	public List findImportWrapStat(List contracts, Date beginDate,
			Date endDate, String wrapCode, int state) {
		List list = this.contractAnalyseDao.findImportWrapStat(contracts,
				beginDate, endDate, wrapCode, state);
		List<WrapStat> lsResult = new ArrayList<WrapStat>();
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration declaration = (BaseCustomsDeclaration) list
					.get(i);
			Wrap wrap = declaration.getWrapType();
			WrapStat wrapStat = new WrapStat();
			wrapStat.setWrapName(wrap.getName());// 包装名
			Double grossWeight = declaration.getGrossWeight() == null ? 0
					: declaration.getGrossWeight();// 毛重
			Double netWeight = declaration.getNetWeight() == null ? 0
					: declaration.getNetWeight();// 净重
			wrapStat.setWrapWeight(grossWeight - netWeight);
			wrapStat.setWrapUnit("千克");
			wrapStat.setEmsNo(declaration.getEmsHeadH2k());// 手册号
			wrapStat.setCustomsDeclarationCode(declaration
					.getCustomsDeclarationCode());// 报关单号
			wrapStat.setMemos(declaration.getMemos());
			lsResult.add(wrapStat);
		}
		return lsResult;
	}

	/**
	 * 报关单预录入库查询
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是BcsCustomsDeclaration型，报关单表头
	 */
	public List findPreCustomsDeclaration(List contracts, Date beginDate,
			Date endDate, int state) {
		return this.contractAnalyseDao.findPreCustomsDeclaration(contracts,
				beginDate, endDate, state);
	}

	/**
	 * 库存统计 纵向显示
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param materielType
	 *            物料类型
	 * @param state
	 *            状态类型
	 * @return List 是StorageContractStat型，库存统计 纵向显示
	 */
	public List findStorageContractStat(List contracts, Date beginDate,
			Date endDate, String materielType, int state) {
		List tempContractStatList = new ArrayList<StorageContractStat>();
		if (materielType.equals(MaterielType.MATERIEL)) {
			List contractImgList = this.contractAnalyseDao
					.findContractImgByParentId(contracts);
			for (int j = 0; j < contracts.size(); j++) {
				HashMap<String, ExpProductStat> hmImgUse = new HashMap<String, ExpProductStat>();
				Contract contract = (Contract) contracts.get(j);
				List contractList = new ArrayList();
				contractList.add(contract);
				this.calContractImgUse(contractList, hmImgUse, beginDate,
						endDate, SearchType.NAME_SPEC_CODE_UNIT_SEQNUM, state);
				for (int i = 0; i < contractImgList.size(); i++) {
					ContractImg contractImg = (ContractImg) contractImgList
							.get(i);

					if (contract.getId().equals(
							contractImg.getContract().getId())) {
						StorageContractStat storageContractStat = new StorageContractStat();
						storageContractStat.setCredenceNo(contractImg.getCredenceNo());
						storageContractStat.setComplexCode(contractImg
								.getComplex().getCode());
						storageContractStat
								.setName((contractImg.getName() == null ? ""
										: contractImg.getName()));
						storageContractStat
								.setSpec((contractImg.getSpec() == null ? ""
										: contractImg.getSpec()));
						storageContractStat.setUnit(contractImg.getUnit());
						storageContractStat.setImpContractNo(contract
								.getImpContractNo());
						storageContractStat = makeStoreImpContractStat(
								beginDate, endDate, contractImg, contract,
								storageContractStat, hmImgUse, state);
						tempContractStatList.add(storageContractStat);
					}
				}
			}

		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			List contractExgList = this.contractAnalyseDao
					.findContractExgByParentId(contracts);

			for (int j = 0; j < contracts.size(); j++) {
				for (int i = 0; i < contractExgList.size(); i++) {
					ContractExg contractExg = (ContractExg) contractExgList
							.get(i);
					StorageContractStat storageContractStat = new StorageContractStat();
					storageContractStat.setCredenceNo(contractExg.getCredenceNo());
					storageContractStat.setComplexCode(contractExg.getComplex()
							.getCode());
					storageContractStat
							.setName((contractExg.getName() == null ? ""
									: contractExg.getName()));
					storageContractStat
							.setSpec((contractExg.getSpec() == null ? ""
									: contractExg.getSpec()));
					storageContractStat.setUnit(contractExg.getUnit());

					Contract contract = (Contract) contracts.get(j);

					if (contract.getId().equals(
							contractExg.getContract().getId())) {
						storageContractStat.setExpContractNo(contract
								.getExpContractNo());
						storageContractStat = makeStoreExgContractStat(
								beginDate, endDate, contractExg, contract,
								storageContractStat, state);
						tempContractStatList.add(storageContractStat);
					}
				}
			}
		}
		return tempContractStatList;
	}

	/**
	 * 库存统计 横向显示
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param materielType
	 *            物料类型
	 * @param state
	 *            状态类型
	 * @return List 是StorageStat型，库存统计 横向显示
	 */
	public List findStorageStat(List contracts, Date beginDate, Date endDate,
			String materielType, int state) {
		Map<String, StorageStat> storageStatMap = new HashMap<String, StorageStat>();
		List returnList = new ArrayList();

		if (materielType.equals(MaterielType.MATERIEL)) {
			Map<String, Map<String, ExpProductStat>> imgUseMap = new HashMap<String, Map<String, ExpProductStat>>();
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
				HashMap<String, ExpProductStat> hmImgUse = new HashMap<String, ExpProductStat>();
				List contractList = new ArrayList();
				contractList.add(contract);
				this.calContractImgUse(contractList, hmImgUse, beginDate,
						endDate, SearchType.NAME_SPEC_CODE_UNIT_CREDENCENO, state);
				imgUseMap.put(contract.getId(), hmImgUse);
			}

			List contractImgList = this.contractAnalyseDao
					.findContractImgByParentId(contracts);

			for (int i = 0; i < contractImgList.size(); i++) {
				ContractImg contractImg = (ContractImg) contractImgList.get(i);
				/**
				 * 编码相同,名称,规格,单位,记录号相同 == key
				 */
				String key = contractImg.getComplex().getCode()
						+ (contractImg.getName() == null ? "" : contractImg
								.getName().trim())
						+ (contractImg.getSpec() == null ? "" : contractImg
								.getSpec().trim())
						+ (contractImg.getCredenceNo() == null ? "" : contractImg
								.getCredenceNo())
						+ (contractImg.getUnit() == null ? "" : contractImg
								.getUnit().getCode());

				if (storageStatMap.get(key) == null) {
					StorageStat storageStat = new StorageStat();
					storageStat.setCredenceNo(contractImg.getCredenceNo());
					storageStat.setComplexCode(contractImg.getComplex()
							.getCode());
					storageStat.setName((contractImg.getName() == null ? ""
							: contractImg.getName()));
					storageStat.setSpec((contractImg.getSpec() == null ? ""
							: contractImg.getSpec()));

					storageStat.setUnit(contractImg.getUnit());
					

					List<TempImpContractStat> tempContractStatList = new ArrayList<TempImpContractStat>();
					for (int j = 0; j < contracts.size(); j++) {
						Contract contract = (Contract) contracts.get(j);
						HashMap<String, ExpProductStat> hmImgUse = (HashMap<String, ExpProductStat>) imgUseMap
								.get(contract.getId());
						// if (hmImgUse == null) {
						// hmImgUse = new HashMap<String, Double>();
						// List contractList = new ArrayList();
						// contractList.add(contract);
						// this.calContractImgUse(contractList, hmImgUse,
						// beginDate, endDate,
						// SearchType.NAME_SPEC_CODE_UNIT, state);
						// imgUseMap.put(contract.getId(), hmImgUse);
						// }
						TempImpContractStat tempImpContractStat = new TempImpContractStat();
						tempImpContractStat.setContractId(contract.getId());
						if (contract.getId().equals(
								contractImg.getContract().getId())) {
							tempImpContractStat = makeTempImpContractStat(
									// 计算
									beginDate, endDate, contractImg, contract,
									tempImpContractStat, hmImgUse, state);

						}

						/**
						 * 加入到List中
						 */
						tempContractStatList.add(tempImpContractStat);
					}
					storageStat.setTempContractStatList(tempContractStatList);

					storageStatMap.put(key, storageStat);
				}
				/**
				 * 存在key
				 */
				else {
					StorageStat storageStat = storageStatMap.get(key);
					List tempContractStatList = storageStat
							.getTempContractStatList();
					for (int j = 0; j < tempContractStatList.size(); j++) {
						TempImpContractStat tempImpContractStat = (TempImpContractStat) tempContractStatList
								.get(j);
						if (tempImpContractStat.getContractId().equals(
								contractImg.getContract().getId())) {
							HashMap<String, ExpProductStat> hmImgUse = (HashMap<String, ExpProductStat>) imgUseMap
									.get(tempImpContractStat.getContractId());
							// if (hmImgUse == null) {
							// hmImgUse = new HashMap<String, Double>();
							// List contractList = new ArrayList();
							// contractList.add(contractImg.getContract());
							// this.calContractImgUse(contractList, hmImgUse,
							// beginDate, endDate,
							// SearchType.NAME_SPEC_CODE_UNIT, state);
							// imgUseMap.put(tempImpContractStat
							// .getContractId(), hmImgUse);
							// }
							tempImpContractStat = makeTempImpContractStat(
									beginDate, endDate, contractImg,
									contractImg.getContract(),
									tempImpContractStat, hmImgUse, state);
							break;
						}
					}
				}
				Iterator values = storageStatMap.values().iterator();
				while (values.hasNext()) {
					Double totals = 0.0;
					StorageStat storageStat = (StorageStat) values.next();
					for (int q = 0; q < storageStat.getTempContractStatList()
							.size(); q++) {
						TempImpContractStat tempImpContractStat = (TempImpContractStat) storageStat
								.getTempContractStatList().get(q);
						//hwy2014-9-4当为横向显示时，总和=每本合同的余料之和
						//余料库存 = 总进口量 - 成品使用量 - 余料结转之合 - 内销数量 - 料件复出数量
//						totals += (CommonUtils
//								.getDoubleExceptNull(tempImpContractStat
//										.getTotalImpAmount()) - CommonUtils
//								.getDoubleExceptNull(tempImpContractStat
//										.getFinishProductDosageAmount()));
						totals += CommonUtils.getDoubleExceptNull(tempImpContractStat.getRemainStorageAmount());
					}
					storageStat.setTotalize(totals);
				}

			}

		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			List contractExgList = this.contractAnalyseDao
					.findContractExgByParentId(contracts);
			for (int i = 0; i < contractExgList.size(); i++) {
				ContractExg contractExg = (ContractExg) contractExgList.get(i);
				/**
				 * 编码相同,名称,规格,单位相同 == key
				 */
				String key = contractExg.getComplex().getCode()
						+ (contractExg.getName() == null ? "" : contractExg
								.getName().trim())
						+ (contractExg.getSpec() == null ? "" : contractExg
								.getSpec().trim())
						+ (contractExg.getUnit() == null ? "" : contractExg
								.getUnit().getCode());
				if (storageStatMap.get(key) == null) {
					StorageStat storageStat = new StorageStat();
					storageStat.setCredenceNo(contractExg.getCredenceNo());
					storageStat.setComplexCode(contractExg.getComplex()
							.getCode());
					storageStat.setName((contractExg.getName() == null ? ""
							: contractExg.getName()));
					storageStat.setSpec((contractExg.getSpec() == null ? ""
							: contractExg.getSpec()));
					storageStat.setUnit(contractExg.getUnit());
					List<TempExpContractStat> tempContractStatList = new ArrayList<TempExpContractStat>();
					for (int j = 0; j < contracts.size(); j++) {
						Contract contract = (Contract) contracts.get(j);
						TempExpContractStat tempExpContractStat = new TempExpContractStat();
						tempExpContractStat.setContractId(contract.getId());
						if (contract.getId().equals(
								contractExg.getContract().getId())) {
							tempExpContractStat = makeTempExgContractStat(
									beginDate, endDate, contractExg, contract,
									tempExpContractStat, state);

						}
						tempContractStatList.add(tempExpContractStat);
					}
					storageStat.setTempContractStatList(tempContractStatList);
					storageStatMap.put(key, storageStat);
				} else {
					StorageStat storageStat = storageStatMap.get(key);
					List tempContractStatList = storageStat
							.getTempContractStatList();
					for (int j = 0; j < tempContractStatList.size(); j++) {
						TempExpContractStat tempExpContractStat = (TempExpContractStat) tempContractStatList

						.get(j);
						if (tempExpContractStat.getContractId().equals(
								contractExg.getContract().getId())) {
							tempExpContractStat = makeTempExgContractStat(
									beginDate, endDate, contractExg,
									contractExg.getContract(),
									tempExpContractStat, state);
						}
					}
				}

			}
		}
		returnList.addAll(storageStatMap.values());
		return returnList;
	}

	/**
	 * 生成库存统计成品列表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contractExg
	 *            合同备案成品
	 * @param contract
	 *            合同备案表头
	 * @param tempExpContractStat
	 *            存放报关分析－－库存分析资料、成品
	 * @param state
	 *            状态类型
	 * @return TempExpContractStat 存放报关分析－－库存分析资料、成品
	 */
	private TempExpContractStat makeTempExgContractStat(Date beginDate,
			Date endDate, ContractExg contractExg, Contract contract,
			TempExpContractStat tempExpContractStat, int state) {
		/**
		 * 大单出口量 = 所有出口报关单数量之和(成品出口,转厂出口) 生效的报关单
		 */
		Double orderExpAmount = this.contractAnalyseDao.findOrderExpAmount(
				contract.getEmsNo(), contractExg.getSeqNum(), beginDate,
				endDate, state);
		tempExpContractStat.setOrderExpAmount(orderExpAmount);
		/**
		 * 返工数量 = 所有返工复出类型数量之和
		 */
		Double returnAmount = this.contractAnalyseDao.findReturnAmount(
				contract.getEmsNo(), contractExg.getSeqNum(), beginDate,
				endDate, state);
		tempExpContractStat.setReturnAmount(returnAmount);
		/**
		 * 总出口量 = 大单出口量 - 返工数量
		 */
		Double totalExpAmount = orderExpAmount - returnAmount;
		tempExpContractStat.setTotalExpAmount(totalExpAmount);
		/**
		 * 合同定量 = 合同成品数量
		 */
		Double contractRation = contractExg.getExportAmount();
		tempExpContractStat.setContractRation(contractRation);
		/**
		 * 可出口余数 = 合同定量 - 总出口量
		 */
		tempExpContractStat.setCanExpRemain(contractRation - totalExpAmount);
		//
		// 现出口数量
		//
		//
		// 大单出口量 = 所有出口报关单数量之和(成品出口,转厂出口) 生效的报关单
		//

		// Double orderExpAmountNoEffective = this.contractAnalyseDao
		// .findOrderExpAmount(contract.getEmsNo(), contractExg
		// .getSeqNum(), beginDate, endDate, state);
		tempExpContractStat.setNowExpAmount(contractRation - totalExpAmount);
		// - orderExpAmountNoEffective);
		return tempExpContractStat;
	}

	/**
	 * 统计库存分析 纵向显示
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contractExg
	 *            合同备案成品
	 * @param contract
	 *            合同备案表头
	 * @param storageContractStat
	 *            用于报关分析－－库存分析 纵向显示
	 * @param state
	 *            状态类型
	 * @return StorageContractStat 库存分析 纵向显示
	 */
	private StorageContractStat makeStoreExgContractStat(Date beginDate,
			Date endDate, ContractExg contractExg, Contract contract,
			StorageContractStat storageContractStat, int state) {
		/**
		 * 大单出口量 = 所有出口报关单数量之和(成品出口,转厂出口) 生效的报关单
		 */
		Double orderExpAmount = this.contractAnalyseDao.findOrderExpAmount(
				contract.getEmsNo(), contractExg.getSeqNum(), beginDate,
				endDate, state);
		storageContractStat.setOrderExpAmount(orderExpAmount);
		/**
		 * 返工复出 = 所有返工复出类型数量之和
		 */
		Double returnAmount = this.contractAnalyseDao.findReturnAmount(
				contract.getEmsNo(), contractExg.getSeqNum(), beginDate,
				endDate, state);
		/**
		 * 退厂返工 = 所有退厂返工类型数量之和
		 */
		Double backfactory = this.contractAnalyseDao.findBackReturnAmount(
				contract.getEmsNo(), contractExg.getSeqNum(), beginDate,
				endDate, state);
		storageContractStat.setReturnAmount(backfactory);
		/**
		 * 总出口量 = 大单出口量-退厂返工+返工复出
		 */
		Double totalExpAmount = orderExpAmount - backfactory + returnAmount;
		storageContractStat.setTotalExpAmount(totalExpAmount);
		/**
		 * 合同定量 = 合同成品数量
		 */
		Double contractRation = contractExg.getExportAmount();
		storageContractStat.setContractRation(contractRation);
		/**
		 * 可出口余数 = 合同定量 - 总出口量
		 */
		storageContractStat.setCanExpRemain(contractRation - totalExpAmount);
		//
		// 现出口数量
		//
		//
		// 大单出口量 = 所有出口报关单数量之和(成品出口,转厂出口) 生效的报关单
		//

		// Double orderExpAmountNoEffective = this.contractAnalyseDao
		// .findOrderExpAmount(contract.getEmsNo(), contractExg
		// .getSeqNum(), beginDate, endDate, false);
		storageContractStat.setNowExpAmount(contractRation - totalExpAmount);
		// - orderExpAmountNoEffective);
		return storageContractStat;
	}

	/**
	 * 生成库存统计料件列表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contractImg
	 *            合同备案料件
	 * @param contract
	 *            合同备案表头
	 * @param tempImpContractStat
	 *            存放报关分析－－库存分析资料、料件
	 * @param state
	 *            状态类型
	 * @return TempImpContractStat 存放报关分析－－库存分析资料、料件
	 */
	private TempImpContractStat makeTempImpContractStat(Date beginDate,
			Date endDate, ContractImg contractImg, Contract contract,
			TempImpContractStat tempImpContractStat,
			HashMap<String, ExpProductStat> hmImgUse, int state) {

		// /**
		// * 退厂返工
		// */
		// Double backFactoryReworkDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.BACK_FACTORY_REWORK, state);
		// /**
		// * 出口成品
		// */
		// Double directExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.DIRECT_EXPORT, state);
		// /**
		// * 转厂出口
		// */
		// Double transferFactoryExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.TRANSFER_FACTORY_EXPORT, state);
		// /**
		// * 返工复出
		// */
		// Double reworkExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.REWORK_EXPORT, state);

		/**
		 * 料件合同定量=合同料件数量
		 */
		Double contractRation = contractImg.getAmount();
		tempImpContractStat.setContractRation(contractRation);

		String key = contractImg.getComplex().getCode()
				+ (contractImg.getName() == null ? "" : "/"
						+ contractImg.getName())
				+ (contractImg.getSpec() == null ? "" : "/"
						+ contractImg.getSpec())
//				+ (contractImg.getSeqNum() == null ? "" : "/"
//						+ contractImg.getSeqNum())
				//2014-8-25横向显示时，key序号改为记录号
				+ (contractImg.getCredenceNo() == null ? "" : "/"
				+ contractImg.getCredenceNo())
				+ "/"
				+ (contractImg.getUnit() == null ? "" : contractImg.getUnit()
						.getCode());
		/**
		 * 成品使用量
		 */
		Double finishProductDosageAmount = CommonUtils
				.getDoubleExceptNull(hmImgUse.get(key) == null ? 0.0 : hmImgUse
						.get(key).getExpTotalAmont());
		tempImpContractStat
				.setFinishProductDosageAmount(finishProductDosageAmount);

		/**
		 * 退料出口量 = 所有使用该合同出口报关单数量之和(退料出口类型)
		 */
		Double backMaterielExpAmount = this.contractAnalyseDao
				.findBackMaterielExpAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		tempImpContractStat.setBackMaterielExpAmount(backMaterielExpAmount);
		/**
		 * 退料出口量 =退料退换数量
		 */
		Double backMaterielExchangeAmount = this.contractAnalyseDao
				.findBackMaterielExchangeAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		/**
		 * 进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型，余料进口)
		 */
		Double impAmount = this.contractAnalyseDao.findImpAmount(
				contract.getEmsNo(), contractImg.getSeqNum(), beginDate,
				endDate, state);
		/**
		 * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型)
		 * 
		 */
		Double orderImpAmount = this.contractAnalyseDao.findOrderImpAmount(
				contract.getEmsNo(), contractImg.getSeqNum(), beginDate,
				endDate, state);
		tempImpContractStat.setOrderImpAmount(orderImpAmount);
		// /**
		// * 余料转入的数量
		// */
		// Double remainForwardImportAmount = this.contractAnalyseDao
		// .findRemainForwardImportAmount(contract.getEmsNo(), contractImg
		// .getSeqNum(), beginDate, endDate, state);
		/**
		 * 总进口量 = 进口量 - 退料出口量()退料退换
		 */
		Double totalImpAmount = impAmount - backMaterielExchangeAmount;
		// + remainForwardImportAmount;
		tempImpContractStat.setTotalImpAmount(totalImpAmount);
		/**
		 * 现进口量 = 合同备案数量 - 总进口量
		 */
		tempImpContractStat.setNowImpAmount(contractImg.getAmount()
				- totalImpAmount);
		/**
		 * 余料结转之合
		 */
		Double remainForwardAmount = this.contractAnalyseDao
				.findRemainForwardExportAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		/**
		 * 海关批准内销数量
		 */
		Double leiXiaoAmount = this.contractAnalyseDao.findLeiXiaoExportAmount(
				contract.getEmsNo(), contractImg.getSeqNum(), beginDate,
				endDate, state);

		/**
		 * hwy2013-5-2修改，进口料件情况表库存公式保持一致，库存需扣减内销数量 余料库存 = 总进口量 - 成品使用量 - 余料结转之合
		 * - 内销数量 - 料件复出数量
		 */
		tempImpContractStat.setRemainStorageAmount(totalImpAmount
				- finishProductDosageAmount - remainForwardAmount
				- leiXiaoAmount);
		return tempImpContractStat;
	}

	/**
	 * 统计库存分析 纵向显示
	 * 
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contractImg
	 *            合同备案料件
	 * @param contract
	 *            合同备案表头
	 * @param storageContractStat
	 *            用于报关分析－－库存分析 纵向显示
	 * @param state
	 *            状态类型
	 * @return List 是StorageContractStat型，用于报关分析－－库存分析 纵向显示
	 */
	private StorageContractStat makeStoreImpContractStat(Date beginDate,
			Date endDate, ContractImg contractImg, Contract contract,
			StorageContractStat storageContractStat,
			HashMap<String, ExpProductStat> hmImgUse, int state) {

		/**
		 * 退料出口量 = 所有使用该合同出口报关单数量之和(退料出口类型)
		 */
		Double backMaterielExpAmount = this.contractAnalyseDao
				.findBackMaterielExpAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		storageContractStat.setBackMaterielExpAmount(backMaterielExpAmount);

		// /**
		// * 退厂返工
		// */
		// Double backFactoryReworkDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.BACK_FACTORY_REWORK, state);
		// /**
		// * 出口成品
		// */
		// Double directExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.DIRECT_EXPORT, state);
		// /**
		// * 转厂出口
		// */
		// Double transferFactoryExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.TRANSFER_FACTORY_EXPORT, state);
		// /**
		// * 返工复出
		// */
		// Double reworkExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, contractImg,
		// ImpExpType.REWORK_EXPORT, state);

		/**
		 * 料件合同定量=合同料件数量
		 */
		Double contractRation = contractImg.getAmount();
		storageContractStat.setContractRation(contractRation);
		String key = contractImg.getComplex().getCode()
				+ (contractImg.getName() == null ? "" : "/"
						+ contractImg.getName())
				+ (contractImg.getSpec() == null ? "" : "/"
						+ contractImg.getSpec())
				+ (contractImg.getSeqNum() == null ? "" : "/"
						+ contractImg.getSeqNum())
				+ "/"
				+ (contractImg.getUnit() == null ? "" : contractImg.getUnit()
						.getCode());
		/**
		 * 成品使用量
		 */
		Double finishProductDosageAmount = CommonUtils
				.getDoubleExceptNull(hmImgUse.get(key) == null ? 0.0 : hmImgUse
						.get(key).getExpTotalAmont());
		storageContractStat
				.setFinishProductDosageAmount(finishProductDosageAmount);
		/**
		 * 进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型，余料进口)
		 */
		Double impAmount = this.contractAnalyseDao.findImpAmount(
				contract.getEmsNo(), contractImg.getSeqNum(), beginDate,
				endDate, state);
		/**
		 * 大单进口量 = 所有使用该合同进口报关单数量之和(料件进口,料件转厂类型)
		 */
		Double orderImpAmount = this.contractAnalyseDao.findOrderImpAmount(
				contract.getEmsNo(), contractImg.getSeqNum(), beginDate,
				endDate, state);
		storageContractStat.setOrderImpAmount(orderImpAmount);
		/**
		 * 退料出口量 =退料退换数量
		 */
		Double backMaterielExchangeAmount = this.contractAnalyseDao
				.findBackMaterielExchangeAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		/**
		 * 总进口量 = 大单进口量 - 退料出口量 (退换)
		 */
		Double totalImpAmount = impAmount - backMaterielExchangeAmount;// +
		// remainForwardImportAmount;
		storageContractStat.setTotalImpAmount(totalImpAmount);

		/**
		 * 现进口量 = 合同备案数量 - 总进口量
		 */
		storageContractStat.setNowImpAmount(contractImg.getAmount()
				- totalImpAmount);
		/**
		 * 余料结转数量
		 */
		Double remainForwardAmount = this.contractAnalyseDao
				.findRemainForwardExportAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		// 料件复出数量
		Double remainOutAmount = this.contractAnalyseDao
				.findRemaOutExportAmount(contract.getEmsNo(),
						contractImg.getSeqNum(), beginDate, endDate, state);
		Double leiXiaoAmount = this.contractAnalyseDao.findLeiXiaoExportAmount(
				contract.getEmsNo(), contractImg.getSeqNum(), beginDate,
				endDate, state);
		// 余料库存=总进口量 - 成品使用量 - 余料结转之合-料件复出之和-料件内销 edit by cjb 2009.6.8
		storageContractStat.setRemainStorageAmount(totalImpAmount
				- finishProductDosageAmount - remainForwardAmount
				- remainOutAmount - leiXiaoAmount);
		return storageContractStat;
	}

	/**
	 * 获得所有的合同料件,来自编码,单位,名称,规格,单位重量不相同的记录
	 * 
	 * @return List 是TempContractImg型，存放合同料件的临时资料
	 */
	public List findTempContractImg() {
		List<TempContractImg> returnList = new ArrayList<TempContractImg>();
		List tempContractImgList = this.contractAnalyseDao
				.findTempContractImg();
		for (int i = 0; i < tempContractImgList.size(); i++) {
			Object[] objects = (Object[]) tempContractImgList.get(i);
			TempContractImg temp = new TempContractImg();
			temp.setComplexCode(objects[0] == null ? ""
					: ((Complex) objects[0]).getCode());
			temp.setName(objects[1] == null ? "" : objects[1].toString());
			temp.setSpec(objects[2] == null ? "" : objects[2].toString());
			temp.setUnit((Unit) objects[3]);
			temp.setUnitWeight(objects[4] == null ? null : Double
					.valueOf(objects[4].toString()));
			returnList.add(temp);
		}
		return returnList;
	}

	/**
	 * 查找料件执行情况
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeState型，存放报关分析－－料件执行情况分析－－料件执行情况
	 */
	public List findImpMaterielExeState(List contracts,
			TempContractImg tempContractImg, int state) {
		List<ImpMaterielExeState> returnList = new ArrayList<ImpMaterielExeState>();
		this.findImpMaterielExeStateByImportMateriel(contracts,
				tempContractImg, returnList, state);
		this.findImpMaterielExeStateByExportMateriel(contracts,
				tempContractImg, returnList, state);
		Map<String, Double> map = this
				.getUnitDosage(contracts, tempContractImg);
		long ss = new Date().getTime();
		this.findImpMaterielExeStateByImportProduct(contracts, tempContractImg,
				map, returnList, state);
		this.findImpMaterielExeStateByExportProduct(contracts, tempContractImg,
				map, returnList, state);
		System.out.println("findImpMaterielExeStateByImportProduct 用时: "
				+ (new Date().getTime() - ss));
		return returnList;
	}

	/**
	 * 查找料件执行情况 来自进口料件(料件进口,料件转厂,余料结转进口)类型
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @param returnList
	 *            存放报关分析－－料件执行情况分析－－料件执行情况
	 * @param state
	 *            状态类型(料件进口,料件转厂,余料结转进口)
	 */
	private void findImpMaterielExeStateByImportMateriel(List contracts,
			TempContractImg tempContractImg,
			List<ImpMaterielExeState> returnList, int state) {
		List importMaterielList = this.contractAnalyseDao
				.findImpMaterielExeStateByImportMateriel(contracts,
						tempContractImg, state);
		// System.out.println("料件进口,料件转厂,余料结转进口=" + importMaterielList);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeState> map = new HashMap<String, ImpMaterielExeState>();
		for (int i = 0; i < importMaterielList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) importMaterielList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String customCode = baseCustomsDeclaration
					.getCustomsDeclarationCode();
			String code = bcsCustomsDeclarationCommInfo.getComplex().getCode();
			String name = bcsCustomsDeclarationCommInfo.getCommName();
			String spec = bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
					: bcsCustomsDeclarationCommInfo.getCommSpec();
			String unitCode = bcsCustomsDeclarationCommInfo.getUnit() == null ? ""
					: bcsCustomsDeclarationCommInfo.getUnit().getCode();
			String key = baseCustomsDeclaration.getEmsHeadH2k() + code + name
					+ spec + unitCode + customCode;
			if (map.get(key) == null) {
				ImpMaterielExeState impMaterielExeState = new ImpMaterielExeState();
				impMaterielExeState
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeState.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				impMaterielExeState.setEmsNo(baseCustomsDeclaration
						.getEmsHeadH2k());
				impMaterielExeState.setImpAmount(bcsCustomsDeclarationCommInfo
						.getCommAmount());
				impMaterielExeState
						.setExplain(bcsCustomsDeclarationCommInfo.getCommName()
								+ (bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
										: "/"
												+ bcsCustomsDeclarationCommInfo
														.getCommSpec()));
				impMaterielExeState.setImpExpType(baseCustomsDeclaration
						.getImpExpType());
				if (bcsCustomsDeclarationCommInfo.getCommAmount() != null)
					if (bcsCustomsDeclarationCommInfo.getCommAmount() > 0)
						map.put(key, impMaterielExeState);
			} else {
				ImpMaterielExeState temp = map.get(key);
				temp.setImpAmount((temp.getImpAmount() == null ? 0.0 : temp
						.getImpAmount())
						+ (bcsCustomsDeclarationCommInfo.getCommAmount() == null ? 0.0
								: bcsCustomsDeclarationCommInfo.getCommAmount()));
			}
		}
		returnList.addAll(map.values());
	}

	/**
	 * 查找料件执行情况 来自出口料件(退料出口,余料结转出口)类型
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @param returnList
	 *            存放报关分析－－料件执行情况分析－－料件执行情况
	 * @param state
	 *            状态类型(退料出口,余料结转出口)
	 */
	private void findImpMaterielExeStateByExportMateriel(List contracts,
			TempContractImg tempContractImg,
			List<ImpMaterielExeState> returnList, int state) {

		List exportMaterielList = this.contractAnalyseDao
				.findImpMaterielExeStateByExportMateriel(contracts,
						tempContractImg, state);
		// System.out.println("退料出口,余料结转出口=" + exportMaterielList);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeState> map = new HashMap<String, ImpMaterielExeState>();
		for (int i = 0; i < exportMaterielList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) exportMaterielList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String customCode = baseCustomsDeclaration
					.getCustomsDeclarationCode();
			String code = bcsCustomsDeclarationCommInfo.getComplex().getCode();
			String name = bcsCustomsDeclarationCommInfo.getCommName();
			String spec = bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
					: bcsCustomsDeclarationCommInfo.getCommSpec();
			String unitCode = bcsCustomsDeclarationCommInfo.getUnit() == null ? ""
					: bcsCustomsDeclarationCommInfo.getUnit().getCode();
			String key = baseCustomsDeclaration.getEmsHeadH2k() + code + name
					+ spec + unitCode + customCode;
			if (map.get(key) == null) {
				ImpMaterielExeState impMaterielExeState = new ImpMaterielExeState();
				impMaterielExeState
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeState.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				impMaterielExeState.setEmsNo(baseCustomsDeclaration
						.getEmsHeadH2k());
				impMaterielExeState.setExpAmount(bcsCustomsDeclarationCommInfo
						.getCommAmount());
				impMaterielExeState
						.setExplain(bcsCustomsDeclarationCommInfo.getCommName()
								+ (bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
										: "/"
												+ bcsCustomsDeclarationCommInfo
														.getCommSpec()));
				impMaterielExeState.setImpExpType(baseCustomsDeclaration
						.getImpExpType());
				if (bcsCustomsDeclarationCommInfo.getCommAmount() != null)
					if (bcsCustomsDeclarationCommInfo.getCommAmount() > 0)
						map.put(key, impMaterielExeState);
			} else {
				ImpMaterielExeState temp = map.get(key);
				temp.setExpAmount((temp.getExpAmount() == null ? 0.0 : temp
						.getExpAmount())
						+ (bcsCustomsDeclarationCommInfo.getCommAmount() == null ? 0.0
								: bcsCustomsDeclarationCommInfo.getCommAmount()));
			}

		}
		returnList.addAll(map.values());
	}

	/**
	 * key = 合同手册号 + [成品编码+成品名称+成品规格+成品单位]
	 * 
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @return Map<String, Double> String为帐册号＋序号+商品编码＋商品名称＋规格序号＋计量单位编码；
	 *         Double为单项用量
	 */
	private Map<String, Double> getUnitDosage(List<Contract> contractList,
			TempContractImg tempContractImg) {
		Map<String, Double> map = new HashMap<String, Double>();
		List<ContractBom> list = this.contractAnalyseDao.getUnitDosage(
				contractList, tempContractImg);
		for (ContractBom bom : list) {
			String emsNo = bom.getContractExg().getContract().getEmsNo();
			ContractExg contractExg = bom.getContractExg();
			Object seqNum = contractExg.getSeqNum() == null ? "" : contractExg
					.getSeqNum();
			String productCode = contractExg.getComplex() == null ? ""
					: contractExg.getComplex().getCode();
			String productName = contractExg.getName();
			String productSpec = contractExg.getSpec() == null ? ""
					: contractExg.getSpec();
			String productUnitCode = contractExg.getUnit() == null ? ""
					: contractExg.getUnit().getCode();
			double unitDosage = bom.getUnitDosage() == null ? 0.0 : bom
					.getUnitDosage();
			String key = emsNo + seqNum + productCode + productName
					+ productSpec + productUnitCode;
			if (map.get(key) == null) {
				map.put(key, unitDosage);
			}
		}
		return map;
	}

	/**
	 * 查找料件执行情况 来自进口成品(退厂返工类型)
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @param unitDosageMap
	 *            为Map<String, Double>型，String为帐册号＋商品编码＋商品名称＋规格序号＋计量单位编码；
	 *            Double为单项用量
	 * @param returnList
	 *            存放报关分析－－料件执行情况分析－－料件执行情况
	 * @param state
	 *            状态类型(退厂返工类型)
	 */
	private void findImpMaterielExeStateByImportProduct(List contracts,
			TempContractImg tempContractImg, Map<String, Double> unitDosageMap,
			List<ImpMaterielExeState> returnList, int state) {
		List importProductList = this.contractAnalyseDao
				.findImpMaterielExeStateByImportProduct(contracts,
						tempContractImg, state);
		// System.out.println("退厂返工=" + importProductList);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeState> map = new HashMap<String, ImpMaterielExeState>();
		for (int i = 0; i < importProductList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) importProductList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String customCode = baseCustomsDeclaration
					.getCustomsDeclarationCode();
			Integer seqNum = bcsCustomsDeclarationCommInfo.getCommSerialNo();
			String code = bcsCustomsDeclarationCommInfo.getComplex().getCode();
			String name = bcsCustomsDeclarationCommInfo.getCommName();
			String spec = bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
					: bcsCustomsDeclarationCommInfo.getCommSpec();
			String unitCode = bcsCustomsDeclarationCommInfo.getUnit() == null ? ""
					: bcsCustomsDeclarationCommInfo.getUnit().getCode();
			String key = baseCustomsDeclaration.getEmsHeadH2k() + seqNum + code
					+ name + spec + unitCode;
			Double unitDosage = unitDosageMap.get(key) == null ? 0.0
					: unitDosageMap.get(key);
			key += customCode;
			if (map.get(key) == null) {
				ImpMaterielExeState impMaterielExeState = new ImpMaterielExeState();
				impMaterielExeState.setApplyToCustomBillNo(customCode);
				impMaterielExeState.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				impMaterielExeState.setEmsNo(baseCustomsDeclaration
						.getEmsHeadH2k());
				/**
				 * 料件数量 = 成品数量 * 单项用量
				 */
				Double productAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeState.setImpAmount(productAmount * unitDosage);
				impMaterielExeState
						.setExplain(bcsCustomsDeclarationCommInfo.getCommName()
								+ (bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
										: "/"
												+ bcsCustomsDeclarationCommInfo
														.getCommSpec()));
				impMaterielExeState.setImpExpType(baseCustomsDeclaration
						.getImpExpType());
				if (productAmount * unitDosage > 0)
					map.put(key, impMaterielExeState);
			} else {
				ImpMaterielExeState temp = map.get(key);
				/**
				 * 料件数量 = 成品数量 * 单项用量
				 */
				Double productAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setImpAmount((temp.getImpAmount() == null ? 0.0 : temp
						.getImpAmount()) + (productAmount * unitDosage));
			}
		}
		returnList.addAll(map.values());
		// System.out.println("----------qqq----------");
	}

	/**
	 * 查找料件执行情况 来自出口成品(返工复出类型,成品出口类型,转厂出口类型)
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param tempContractImg
	 *            存放合同料件的临时资料
	 * @param unitDosageMap
	 *            为Map<String, Double>型，String为帐册号＋商品序号+商品编码＋商品名称＋规格序号＋计量单位编码；
	 *            Double为单项用量
	 * @param returnList
	 *            存放报关分析－－料件执行情况分析－－料件执行情况
	 * @param state
	 *            状态类型(返工复出类型)
	 */
	private void findImpMaterielExeStateByExportProduct(List contracts,
			TempContractImg tempContractImg, Map<String, Double> unitDosageMap,
			List<ImpMaterielExeState> returnList, int state) {
		List exportProductList = this.contractAnalyseDao
				.findImpMaterielExeStateByExportProduct(contracts,
						tempContractImg, state);
		// System.out.println("返工复出,成品出口类型,转厂出口类型=" + exportProductList);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeState> map = new HashMap<String, ImpMaterielExeState>();
		for (int i = 0; i < exportProductList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) exportProductList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String customCode = baseCustomsDeclaration
					.getCustomsDeclarationCode();
			Integer seqNum = bcsCustomsDeclarationCommInfo.getCommSerialNo();
			String code = bcsCustomsDeclarationCommInfo.getComplex().getCode();
			String name = bcsCustomsDeclarationCommInfo.getCommName();
			String spec = bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
					: bcsCustomsDeclarationCommInfo.getCommSpec();
			String unitCode = bcsCustomsDeclarationCommInfo.getUnit() == null ? ""
					: bcsCustomsDeclarationCommInfo.getUnit().getCode();
			String key = baseCustomsDeclaration.getEmsHeadH2k() + seqNum + code
					+ name + spec + unitCode;
			Double unitDosage = unitDosageMap.get(key) == null ? 0.0
					: unitDosageMap.get(key);
			key += customCode;
			if (map.get(key) == null) {
				ImpMaterielExeState impMaterielExeState = new ImpMaterielExeState();
				impMaterielExeState
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeState.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				impMaterielExeState.setEmsNo(baseCustomsDeclaration
						.getEmsHeadH2k());
				/**
				 * 料件数量 = 成品数量 * 单项用量
				 */
				Double productAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeState.setExpAmount(productAmount * unitDosage);
				impMaterielExeState
						.setExplain(bcsCustomsDeclarationCommInfo.getCommName()
								+ (bcsCustomsDeclarationCommInfo.getCommSpec() == null ? ""
										: "/"
												+ bcsCustomsDeclarationCommInfo
														.getCommSpec()));
				impMaterielExeState.setImpExpType(baseCustomsDeclaration
						.getImpExpType());
				if (productAmount * unitDosage > 0)
					map.put(key, impMaterielExeState);
			} else {
				ImpMaterielExeState temp = map.get(key);
				/**
				 * 料件数量 = 成品数量 * 单项用量
				 */
				Double productAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setExpAmount((temp.getExpAmount() == null ? 0.0 : temp
						.getExpAmount()) + (productAmount * unitDosage));
			}
		}
		returnList.addAll(map.values());
		// System.out.println("----------wwww----------");
	}

	/**
	 * 查找料件执行明细情况 来自(进口料件,料件转厂,退料出口)
	 * 
	 * @param contractImg
	 *            合同备案料件
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeDetail型，存放报关分析－－料件执行情况分析－－料件执行明细资料
	 */
	public List findImpMaterielExeDetail(ContractImg contractImg,
			int impExpType, Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		Contract contract = contractImg.getContract();
		if (impExpType == -1) {
			returnList.addAll(findImpMaterielExeDetailByDirectImport(contract,
					contractImg, beginDate, endDate, state));
			returnList.addAll(findImpMaterielExeDetailByTransferFactoryImport(
					contract, contractImg, beginDate, endDate, state));
			returnList.addAll(findImpMaterielExeDetailByBackMaterielExport(
					contract, contractImg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.DIRECT_IMPORT) {
			returnList.addAll(findImpMaterielExeDetailByDirectImport(contract,
					contractImg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			returnList.addAll(findImpMaterielExeDetailByTransferFactoryImport(
					contract, contractImg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
			returnList.addAll(findImpMaterielExeDetailByBackMaterielExport(
					contract, contractImg, beginDate, endDate, state));
		}
		return returnList;
	}

	/**
	 * 获得料件直接进口
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractImg
	 *            合同备案料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeDetail型，存放报关分析－－料件执行情况分析－－料件执行明细资料
	 */
	private List findImpMaterielExeDetailByDirectImport(Contract contract,
			ContractImg contractImg, Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findImpMaterielExeDetail(
				contract, contractImg, ImpExpType.DIRECT_IMPORT, beginDate,
				endDate, state);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeDetail> map = new HashMap<String, ImpMaterielExeDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ImpMaterielExeDetail impMaterielExeDetail = new ImpMaterielExeDetail();
				impMaterielExeDetail
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeDetail
						.setApplyToCustomDate(baseCustomsDeclaration
								.getDeclarationDate());
				impMaterielExeDetail.setConveyance(baseCustomsDeclaration
						.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeDetail
						.setMaterielImportAmount(materielImportAmount);
				impMaterielExeDetail.setCustomer(baseCustomsDeclaration
						.getCustomer() == null ? "" : baseCustomsDeclaration
						.getCustomer().getName());
				map.put(key, impMaterielExeDetail);
			} else {
				ImpMaterielExeDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setMaterielImportAmount((temp.getMaterielImportAmount() == null ? 0.0
						: temp.getMaterielImportAmount())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得料件料件转厂
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractImg
	 *            合同备案料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeDetail型，存放报关分析－－料件执行情况分析－－料件执行明细资料
	 */
	private List findImpMaterielExeDetailByTransferFactoryImport(
			Contract contract, ContractImg contractImg, Date beginDate,
			Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findImpMaterielExeDetail(
				contract, contractImg, ImpExpType.TRANSFER_FACTORY_IMPORT,
				beginDate, endDate, state);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeDetail> map = new HashMap<String, ImpMaterielExeDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ImpMaterielExeDetail impMaterielExeDetail = new ImpMaterielExeDetail();
				impMaterielExeDetail
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeDetail
						.setApplyToCustomDate(baseCustomsDeclaration
								.getDeclarationDate());
				impMaterielExeDetail.setConveyance(baseCustomsDeclaration
						.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeDetail
						.setTransferFactoryAmount(materielImportAmount);
				impMaterielExeDetail.setCustomer(baseCustomsDeclaration
						.getCustomer() == null ? "" : baseCustomsDeclaration
						.getCustomer().getName());
				map.put(key, impMaterielExeDetail);
			} else {
				ImpMaterielExeDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setTransferFactoryAmount((temp.getTransferFactoryAmount() == null ? 0.0
						: temp.getTransferFactoryAmount())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得料料来自退料出口
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractImg
	 *            合同备案料件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeDetail型，存放报关分析－－料件执行情况分析－－料件执行明细资料
	 */
	private List findImpMaterielExeDetailByBackMaterielExport(
			Contract contract, ContractImg contractImg, Date beginDate,
			Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findImpMaterielExeDetail(
				contract, contractImg, ImpExpType.BACK_MATERIEL_EXPORT,
				beginDate, endDate, state);
		/**
		 * 把相同报关单,相同料件序号的料件 SUM()
		 */
		Map<String, ImpMaterielExeDetail> map = new HashMap<String, ImpMaterielExeDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ImpMaterielExeDetail impMaterielExeDetail = new ImpMaterielExeDetail();
				impMaterielExeDetail
						.setApplyToCustomBillNo(baseCustomsDeclaration
								.getCustomsDeclarationCode());
				impMaterielExeDetail
						.setApplyToCustomDate(baseCustomsDeclaration
								.getDeclarationDate());
				impMaterielExeDetail.setConveyance(baseCustomsDeclaration
						.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				impMaterielExeDetail
						.setBackMaterielExportAmount(materielImportAmount);
				impMaterielExeDetail.setCustomer(baseCustomsDeclaration
						.getCustomer() == null ? "" : baseCustomsDeclaration
						.getCustomer().getName());
				map.put(key, impMaterielExeDetail);
			} else {
				ImpMaterielExeDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setTransferFactoryAmount((temp
						.getBackMaterielExportAmount() == null ? 0.0 : temp
						.getBackMaterielExportAmount())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 计算成品所有的料件的耗用总量
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param hmImgUse
	 *            合同备案BOM资料对应的数量
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 */
	private void calContractImgUse(List contracts,
			HashMap<String, ExpProductStat> hmImgUse, Date beginDate,
			Date endDate, int searchType, int state) {
		for (int i = 0; i < contracts.size(); i++) {
			// 取得合同表头
			Contract contract = (Contract) contracts.get(i);
			// 查询本合同在一段时间内统计报表中的出口成品报关情况表资料
			List<ExpProductStat> lsProductStat = this.contractStatLogic
					.findExpProductStat(contract, beginDate, endDate, state);
			// 查找合同料件 来自 合同备案表头Id
			List<ContractImg> listImg = this.contractDao
					.findContractImgByContractId(contract.getId());
			// 对应商品编码对应合同工备案料件资料
			Map<Integer, ContractImg> mapImg = new HashMap<Integer, ContractImg>();
			for (ContractImg img : listImg) {
				mapImg.put(img.getSeqNum(), img);
			}
			for (ExpProductStat productStat : lsProductStat) {
				double imgAmount = CommonUtils.getDoubleExceptNull(productStat
						.getExpTotalAmont());
				double directExport = CommonUtils
						.getDoubleExceptNull(productStat.getDirectExport());
				double transferFactoryExport = CommonUtils
						.getDoubleExceptNull(productStat
								.getTransferFactoryExport());
				double backFactoryRework = CommonUtils
						.getDoubleExceptNull(productStat.getBackFactoryRework());
				double reworkExport = CommonUtils
						.getDoubleExceptNull(productStat.getReworkExport());
				// System.out.println("sssssssssss:" +productStat.getCommName()+
				// exgAmount);
				List<ContractBom> list = this.contractDao
						.findContractBomByExgSeqNum(contract,
								Integer.valueOf(productStat.getSerialNo()));
				for (ContractBom contractBom : list) {
					if (contractBom.getContractImgSeqNum() == null) {
						// System.out.println("eeeeeeeeeeeeeeeeeeee: is null");
						continue;
					}
					String key = "";
					ContractImg img = mapImg.get(contractBom
							.getContractImgSeqNum());
					if (img == null) {
						continue;
					}
					if (searchType == SearchType.NAME) {
						// impMaterielExeStat.setNameSpec(objects[0] == null ?
						// "" : objects[0]
						// .toString());
						key = (img.getName() == null ? "" : img.getName());
					} else if (searchType == SearchType.NAME_SPEC) {
						key = (img.getName() == null ? "" : img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec());
					} else if (searchType == SearchType.NAME_SPEC_CODE) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec());
					} else if (searchType == SearchType.CODE) {
						key = img.getComplex().getCode();
					} else if (searchType == SearchType.CODE_NAME) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName());
					} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec())
								+ "/"
								+ (img.getUnit() == null ? "" : img.getUnit()
										.getCode());
					} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT_SEQNUM) {
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec())
								+ (img.getSeqNum() == null ? "" : "/"
										+ img.getSeqNum())
								+ "/"
								+ (img.getUnit() == null ? "" : img.getUnit()
										.getCode());
					}else if(searchType == SearchType.NAME_SPEC_CODE_UNIT_CREDENCENO){
						key = img.getComplex().getCode()
								+ (img.getName() == null ? "" : "/"
										+ img.getName())
								+ (img.getSpec() == null ? "" : "/"
										+ img.getSpec())
								+ (img.getCredenceNo() == null ? "" : "/"
										+ img.getCredenceNo())
								+ "/"
								+ (img.getUnit() == null ? "" : img.getUnit()
										.getCode());
					}

					// double imgAmount = exgAmount
					// * CommonUtils.getDoubleExceptNull(contractBom
					// .getUnitDosage());

					double imgAmount1 = imgAmount
							* CommonUtils.getDoubleExceptNull(CommonUtils
									.getDoubleExceptNull(contractBom
											.getUnitWaste())
									/ (1 - CommonUtils
											.getDoubleExceptNull(contractBom
													.getWaste()) / 100.0));
					// System.out.println("unitWaste:"+CommonUtils
					// .getDoubleExceptNull(contractBom
					// .getUnitWaste()));
					// System.out.println("Waste:"+CommonUtils
					// .getDoubleExceptNull(contractBom
					// .getWaste()));
					// System.out.println("unitWaste/(1-Waste)"+CommonUtils.getDoubleExceptNull(CommonUtils
					// .getDoubleExceptNull(contractBom
					// .getUnitWaste())
					// / (1 - CommonUtils
					// .getDoubleExceptNull(contractBom
					// .getWaste()) / 100.0)));

					double directExport1 = directExport
							* CommonUtils.getDoubleExceptNull(CommonUtils
									.getDoubleExceptNull(contractBom
											.getUnitWaste())
									/ (1 - CommonUtils
											.getDoubleExceptNull(contractBom
													.getWaste()) / 100.0));

					double transferFactoryExport1 = transferFactoryExport
							* CommonUtils.getDoubleExceptNull(CommonUtils
									.getDoubleExceptNull(contractBom
											.getUnitWaste())
									/ (1 - CommonUtils
											.getDoubleExceptNull(contractBom
													.getWaste()) / 100.0));

					double backFactoryRework1 = backFactoryRework
							* CommonUtils.getDoubleExceptNull(CommonUtils
									.getDoubleExceptNull(contractBom
											.getUnitWaste())
									/ (1 - CommonUtils
											.getDoubleExceptNull(contractBom
													.getWaste()) / 100.0));

					double reworkExport1 = reworkExport
							* CommonUtils.getDoubleExceptNull(CommonUtils
									.getDoubleExceptNull(contractBom
											.getUnitWaste())
									/ (1 - CommonUtils
											.getDoubleExceptNull(contractBom
													.getWaste()) / 100.0));

					// double hasAmount =
					// CommonUtils.getDoubleExceptNull(hmImgUse
					// .get(key));
					ExpProductStat p = hmImgUse.get(key);
					if (p == null)
						p = new ExpProductStat();
					p.setExpTotalAmont(CommonUtils.getDoubleExceptNull(p
							.getExpTotalAmont()) + imgAmount1);
					p.setDirectExport(CommonUtils.getDoubleExceptNull(p
							.getDirectExport()) + directExport1);
					p.setTransferFactoryExport(CommonUtils
							.getDoubleExceptNull(p.getTransferFactoryExport())
							+ transferFactoryExport1);
					p.setBackFactoryRework(CommonUtils.getDoubleExceptNull(p
							.getBackFactoryRework()) + backFactoryRework1);
					p.setReworkExport(CommonUtils.getDoubleExceptNull(p
							.getReworkExport()) + reworkExport1);
					// System.out.println(productStat.getCommName()+"::"+contractBom.getName()+"hasAmount:"+hasAmount);
					hmImgUse.put(key, p);
					// System.out.println("ddddddddddddddd: is "
					// + (imgAmount + hasAmount));
				}
			}
		}
		// List<ExpProductStat> lsProductStat =
		// this.findExpProductStat(contract,
		// beginDate, endDate, state);

	}

	/**
	 * 查找进出口料件统计
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @param state
	 *            状态类型
	 * @return List 是ImpMaterielExeStat型，存放报关分析－－料件执行情况分析－－进口料件统计
	 */
	public List findImpMaterielExeStat(List contracts, Date beginDate,
			Date endDate, int searchType, int state) {
		// 定义返回列表
		List<ImpMaterielExeStat> returnList = new ArrayList<ImpMaterielExeStat>();
		// 对应料件----存放统计报表中的出口成品报关情况表资料
		HashMap<String, ExpProductStat> hmImgUse = new HashMap<String, ExpProductStat>();
		// 计算成品所有的料件的耗用总量
		calContractImgUse(contracts, hmImgUse, beginDate, endDate, searchType,
				state);
		// 获得所有的合同备案料件,来自编码,单位,名称,规格,单位重量不相同的记录
		List contractImgList = this.contractAnalyseDao.findContractImg(
				searchType, contracts);
		// //TODO TEST
		// Set<String> keys = hmImgUse.keySet();
		// for(String k : keys){
		// ExpProductStat p = hmImgUse.get(k);
		// System.out.println("-------------------------");
		// System.out.println(k);
		// System.out.println("总出口量:"+p.getExpTotalAmont());
		// System.out.println("成品出口量："+p.getDirectExport());
		// System.out.println("转厂出口："+p.getTransferFactoryExport());
		// System.out.println("退厂返工："+p.getBackFactoryRework());
		// System.out.println("返工复出："+p.getReworkExport());
		// System.out.println("总出口量："+(p.getDirectExport()+p.getTransferFactoryExport()+p.getReworkExport()-p.getBackFactoryRework()));
		// System.out.println();
		// }
		// //TODO
		Map<String, ContractImg> mapImg = getMapImg(contracts);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + contractImgList.size());
		for (int i = 0; i < contractImgList.size(); i++) {
			ImpMaterielExeStat impMaterielExeStat = new ImpMaterielExeStat();
			Object[] objects = null;
			if (contractImgList.get(i).getClass().isArray()) {
				objects = (Object[]) contractImgList.get(i);
			} else {
				objects = new Object[] { contractImgList.get(i) };
			}
			impMaterielExeStat = this.makeImpMaterielExeStat(contracts,
					objects, impMaterielExeStat, hmImgUse, searchType, state,
					beginDate, endDate, mapImg);
			returnList.add(impMaterielExeStat);
		}
		/**
		 * 加一条空的记录
		 */
		returnList.add(new ImpMaterielExeStat());
		/**
		 * 加入统计记录
		 */
		ImpMaterielExeStat impMaterielExeStatTotal = makeTotalImpMaterielExeStat(returnList);
		returnList.add(impMaterielExeStatTotal);
		return returnList;
	}

	/**
	 * 获取料件（仅当只选中一本合同时用）
	 * 
	 * @param contracts
	 * @return
	 */
	private Map<String, ContractImg> getMapImg(List contracts) {
		Map<String, ContractImg> mapImg = new HashMap<String, ContractImg>();
		if (contracts.size() > 1) {
			return mapImg;
		}
		// 取得合同表头
		Contract contract = (Contract) contracts.get(0);
		// 查找合同料件 来自 合同备案表头Id
		List<ContractImg> listImg = this.contractDao
				.findContractImgByContractId(contract.getId());
		// 对应商品编码对应合同工备案料件资料
		for (ContractImg img : listImg) {
			String key = img.getComplex().getCode()
					+ (img.getName() == null ? "" : "/" + img.getName())
					+ (img.getSpec() == null ? "" : "/" + img.getSpec()) + "/"
					+ (img.getUnit() == null ? "" : img.getUnit().getCode());
			mapImg.put(key, img);
		}
		return mapImg;
	}

	/**
	 * 对进口料件进行统计
	 * 
	 * @param returnList
	 *            是List<ImpMaterielExeStat>型，存放报关分析－－料件执行情况分析－－进口料件统计
	 * @return List 是ImpMaterielExeStat型，存放报关分析－－料件执行情况分析－－进口料件统计
	 */
	private ImpMaterielExeStat makeTotalImpMaterielExeStat(
			List<ImpMaterielExeStat> returnList) {
		Double contractTotalAmount = 0.0; // 合同总订量
		Double totalImpAmount = 0.0; // 总进口量
		Double orderImpAmount = 0.0; // 大单进口量
		Double materielImpAmount = 0.0; // 料件进口总量
		Double transferImpAmount = 0.0; // 转厂进口总量
		Double backMaterielExpAmount = 0.0; // 退料出口总量
		Double reworkExpAmount = 0.0; // 退料复出总量
		Double backMaterielExchange = 0.0; // 退料退换总量
		Double expFinishProductAmount = 0.0; // 出口成品使用总量
		Double remainStat = 0.0; // 余料情况
		Double lackStat = 0.0; // 缺料情况
		Double canImpAmount = 0.0; // 可进口总量
		Double remainForwordExpAmount = 0.0; // 余料结转转出
		Double remainForwordImpAmount = 0.0; // 余料结转转入
		Double customsEnvelopRemainAmount = 0.0; // 关封余量
		Double canDirectImpAmount = 0.0; // 可直接进口量
		for (int i = 0; i < returnList.size(); i++) {
			ImpMaterielExeStat impMaterielExeStat = (ImpMaterielExeStat) returnList
					.get(i);
			contractTotalAmount += impMaterielExeStat.getContractTotalAmount() == null ? 0.0
					: impMaterielExeStat.getContractTotalAmount(); // 合同总订量
			totalImpAmount += impMaterielExeStat.getTotalImpAmount() == null ? 0.0
					: impMaterielExeStat.getTotalImpAmount();// 总进口量
			orderImpAmount += impMaterielExeStat.getOrderImpAmount() == null ? 0.0
					: impMaterielExeStat.getOrderImpAmount(); // 大单进口量
			materielImpAmount += impMaterielExeStat.getMaterielImpAmount() == null ? 0.0
					: impMaterielExeStat.getMaterielImpAmount(); // 料件进口总量
			transferImpAmount += impMaterielExeStat.getTransferImpAmount() == null ? 0.0
					: impMaterielExeStat.getTransferImpAmount(); // 转厂进口总量
			backMaterielExpAmount += impMaterielExeStat
					.getBackMaterielExpAmount() == null ? 0.0
					: impMaterielExeStat.getBackMaterielExpAmount(); // 退料出口总量
			reworkExpAmount += impMaterielExeStat.getReworkExpAmount() == null ? 0.0
					: impMaterielExeStat.getReworkExpAmount(); // 退料复出总量
			backMaterielExchange += impMaterielExeStat
					.getBackMaterielExchange() == null ? 0.0
					: impMaterielExeStat.getBackMaterielExchange(); // 退料退换总量
			expFinishProductAmount += impMaterielExeStat
					.getExpFinishProductAmount() == null ? 0.0
					: impMaterielExeStat.getExpFinishProductAmount(); // 出口成品使用总量
			remainStat += impMaterielExeStat.getRemainStat() == null ? 0.0
					: impMaterielExeStat.getRemainStat(); // 余料情况
			lackStat += impMaterielExeStat.getLackStat() == null ? 0.0
					: impMaterielExeStat.getLackStat(); // 缺料情况
			canImpAmount += impMaterielExeStat.getCanImpAmount() == null ? 0.0
					: impMaterielExeStat.getCanImpAmount(); // 可进口总量
			remainForwordExpAmount += impMaterielExeStat
					.getRemainForwordExpAmount() == null ? 0.0
					: impMaterielExeStat.getRemainForwordExpAmount(); // 余料结转转出
			remainForwordImpAmount += impMaterielExeStat
					.getRemainForwordImpAmount() == null ? 0.0
					: impMaterielExeStat.getRemainForwordImpAmount(); // 余料结转转入
			customsEnvelopRemainAmount += impMaterielExeStat
					.getCustomsEnvelopRemainAmount() == null ? 0.0
					: impMaterielExeStat.getCustomsEnvelopRemainAmount();// 关封余量
			canDirectImpAmount += impMaterielExeStat.getCanDirectImpAmount() == null ? 0.0
					: impMaterielExeStat.getCanDirectImpAmount();// 可直接进口量
			// System.out.println( canDirectImpAmount);
		}
		ImpMaterielExeStat impMaterielExeStatTotal = new ImpMaterielExeStat();
		impMaterielExeStatTotal.setComplexCode("合计");
		impMaterielExeStatTotal.setContractTotalAmount(contractTotalAmount);
		impMaterielExeStatTotal.setTotalImpAmount(totalImpAmount);
		impMaterielExeStatTotal.setOrderImpAmount(orderImpAmount);
		impMaterielExeStatTotal.setMaterielImpAmount(materielImpAmount);
		impMaterielExeStatTotal.setTransferImpAmount(transferImpAmount);
		impMaterielExeStatTotal.setBackMaterielExpAmount(backMaterielExpAmount);
		impMaterielExeStatTotal.setReworkExpAmount(reworkExpAmount);
		impMaterielExeStatTotal.setBackMaterielExchange(backMaterielExchange);
		impMaterielExeStatTotal
				.setExpFinishProductAmount(expFinishProductAmount);
		impMaterielExeStatTotal.setRemainStat(remainStat);
		impMaterielExeStatTotal.setLackStat(lackStat);
		impMaterielExeStatTotal.setCanImpAmount(canImpAmount);
		impMaterielExeStatTotal
				.setRemainForwordExpAmount(remainForwordExpAmount);
		impMaterielExeStatTotal
				.setRemainForwordImpAmount(remainForwordImpAmount);
		impMaterielExeStatTotal
				.setCustomsEnvelopRemainAmount(customsEnvelopRemainAmount);
		impMaterielExeStatTotal.setCanDirectImpAmount(canDirectImpAmount);
		return impMaterielExeStatTotal;
	}

	/**
	 * 统计进口料件统计报表
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param impMaterielExeStat
	 *            存放报关分析－－料件执行情况分析－－进口料件统计
	 * @param searchType
	 *            设置要查找的属性
	 * @param state
	 *            状态类型
	 * @return ImpMaterielExeStat 存放报关分析－－料件执行情况分析－－进口料件统计
	 */
	private ImpMaterielExeStat makeImpMaterielExeStat(List contracts,
			Object[] objects, ImpMaterielExeStat impMaterielExeStat,
			HashMap<String, ExpProductStat> hmImgUse, int searchType,
			int state, Date beginDate, Date endDate,
			Map<String, ContractImg> mapImg) {

		/**
		 * 成品使用量
		 */
		Double exgUsed = 0.0;
		String key = "";
		if (searchType == SearchType.NAME) {
			impMaterielExeStat.setNameSpec(objects[0] == null ? "" : objects[0]
					.toString());
			key = impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.NAME_SPEC) {
			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			key = impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			impMaterielExeStat.setComplexCode(objects[2] == null ? ""
			// : ((Complex) objects[2]).getCode());
					: objects[2].toString());
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.CODE) {
			impMaterielExeStat.setComplexCode(objects[0] == null ? ""
			// : ((Complex) objects[0]).getCode());
					: objects[0].toString());
			key = impMaterielExeStat.getComplexCode();
		} else if (searchType == SearchType.CODE_NAME) {
			impMaterielExeStat.setComplexCode(objects[0] == null ? ""
			// : ((Complex) objects[0]).getCode());
					: objects[0].toString());
			impMaterielExeStat.setNameSpec(objects[1] == null ? "" : objects[1]
					.toString());
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec();
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			impMaterielExeStat.setComplexCode(objects[2] == null ? ""
			// : ((Complex) objects[2]).getCode());
					: objects[2].toString());
			impMaterielExeStat.setUnit((Unit) objects[3]);
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec() + "/"
					+ impMaterielExeStat.getUnit().getCode();
			if (mapImg.get(key) != null) {
				Integer credenceNo = mapImg.get(key).getCredenceNo();// 记录号
				Integer seqNum = mapImg.get(key).getSeqNum();// 料件序号
				if (credenceNo != null) {
					impMaterielExeStat.setCredenceNo(credenceNo);
				}
				if (seqNum != null) {
					impMaterielExeStat.setSeqNum(seqNum);
				}
			}
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {

			impMaterielExeStat.setNameSpec((objects[0] == null ? ""
					: objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			impMaterielExeStat.setComplexCode(objects[2] == null ? ""
			// : ((Complex) objects[2]).getCode());
					: objects[2].toString());
			impMaterielExeStat.setUnit((Unit) objects[3]);
			impMaterielExeStat.setSeqNum((Integer) objects[4]);
			key = impMaterielExeStat.getComplexCode() + "/"
					+ impMaterielExeStat.getNameSpec() + "/"
					+ impMaterielExeStat.getSeqNum() + "/"
					+ impMaterielExeStat.getUnit().getCode();

			if (mapImg.get(key) != null) {
				Integer credenceNo = mapImg.get(key).getCredenceNo();// 记录号
				Integer seqNum = mapImg.get(key).getSeqNum();// 料件序号
				if (credenceNo != null) {
					impMaterielExeStat.setCredenceNo(credenceNo);
				}
				if (seqNum != null) {
					impMaterielExeStat.setSeqNum(seqNum);
				}
			}

		}
		ExpProductStat exp = hmImgUse.get(key);
		exgUsed = CommonUtils.getDoubleExceptNull(exp == null ? 0.0 : exp
				.getExpTotalAmont());
		// --------------------------------------------------------------------------------------------
		/**
		 * 料件进口总量 = 所有进口报关单 [料件进口类型] 之和
		 */
		Double directImportAmount = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.DIRECT_IMPORT, null, null, null, state,
						beginDate, endDate);
		impMaterielExeStat.setMaterielImpAmount(directImportAmount);
		// --------------------------------------------------------------------------------------------
		/**
		 * 转厂进口总量 = 所有进口报关单 [料件转厂类型] 之和
		 */
		Double transferFacotryImportAmount = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, null, null,
						state, beginDate, endDate);
		impMaterielExeStat.setTransferImpAmount(transferFacotryImportAmount);
		// --------------------------------------------------------------------------------------------
		/**
		 * 退料复出 = 所有进口报关单 [退料出口类型] 并且贸易方式为 来料料件复出,进料料件复出
		 */
		Double backMaterielReExport = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.BACK_MATERIEL_EXPORT, true, null, null,
						state, beginDate, endDate);
		impMaterielExeStat.setReworkExpAmount(backMaterielReExport);
		// --------------------------------------------------------------------------------------------
		/**
		 * 退料退换出口 = 所有出口报关单 [退料出口类型] 并且贸易方式为 来料料件退换,进料料件退换
		 */
		Double backMaterielExchange = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.BACK_MATERIEL_EXPORT, false, null, null,
						state, beginDate, endDate);
		impMaterielExeStat.setBackMaterielExchange(backMaterielExchange);
		// --------------------------------------------------------------------------------------------
		/**
		 * 退料退换进口 = 所有进口报关单 [直接进口类型] 并且贸易方式为 来料料件退换,进料料件退换
		 */
		Double backMaterielExchangeImp = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.DIRECT_IMPORT, null, null, true, state,
						beginDate, endDate);
		impMaterielExeStat.setBackMaterielExchangeImp(backMaterielExchangeImp);
		// --------------------------------------------------------------------------------------------
		/**
		 * 退料出口 = 退料复出 + 退料退换出口
		 */
		Double backMaterielExpAmount = backMaterielReExport
				+ backMaterielExchange;
		impMaterielExeStat.setBackMaterielExpAmount(backMaterielExpAmount);

		// --------------------------------------------------------------------------------------------
		/**
		 * 大单进口量 = 料件进口总量 +转厂进口总量
		 */
		Double orderImpAmount = directImportAmount
				+ transferFacotryImportAmount;
		impMaterielExeStat.setOrderImpAmount(orderImpAmount);
		// --------------------------------------------------------------------------------------------
		/**
		 * 余料结转(转入) = 所有进口报关单 [料件进口类型] 并且贸易方式为 来料余料结转,进料余料结转
		 */
		Double remainForwardImpAmount = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.REMAIN_FORWARD_IMPORT, true, true, null,
						state, beginDate, endDate);
		impMaterielExeStat.setRemainForwordImpAmount(remainForwardImpAmount);
		// --------------------------------------------------------------------------------------------

		/**
		 * 总进口量 = 料件进口总量 +转厂进口总量 - 退料出口+余料结转转入
		 */
		Double totalImpAmount = directImportAmount
				+ transferFacotryImportAmount - backMaterielExchange// backMaterielExpAmount
				+ remainForwardImpAmount;
		impMaterielExeStat.setTotalImpAmount(totalImpAmount);
		// --------------------------------------------------------------------------------------------
		/**
		 * 余料结转(转出) = 所有出口报关单 [余料结转类型] 并且贸易方式为 来料余料结转,进料余料结转
		 */
		Double remainForwardExpAmount = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.REMAIN_FORWARD_EXPORT, true, true, null,
						state, beginDate, endDate);
		impMaterielExeStat.setRemainForwordExpAmount(remainForwardExpAmount);
		// --------------------------------------------------------------------------------------------
		/**
		 * 合同进口料件总订量 = 所有合同的进口料件总量
		 */
		Double contractImportAmount = this.contractAnalyseDao
				.findContractImportAmount(contracts, searchType, objects);
		impMaterielExeStat.setContractTotalAmount(contractImportAmount);
		// --------------------------------------------------------------------------------------------
		/**
		 * 出口成品使用量
		 */
		// Double exportProductDosageAmount = 0.0;
		//
		// for (int i = 0; i < contracts.size(); i++) {
		// Contract contract = (Contract) contracts.get(i);
		// /**
		// * 退厂返工
		// */
		// Double backFactoryReworkDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.BACK_FACTORY_REWORK);
		// /**
		// * 出口成品
		// */
		// Double directExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.DIRECT_EXPORT);
		// /**
		// * 转厂出口
		// */
		// Double transferFactoryExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.TRANSFER_FACTORY_EXPORT);
		// /**
		// * 返工复出
		// */
		// Double reworkExportDosageAmount = this.contractAnalyseDao
		// .findFinishProductDosageAmount(contract, searchType,
		// objects, ImpExpType.REWORK_EXPORT);
		// /**
		// * 成品使用量 = 出口成品 + 转厂出口 + 返工复出 - 退厂返工
		// */
		// exportProductDosageAmount += (directExportDosageAmount
		// + transferFactoryExportDosageAmount
		// + reworkExportDosageAmount - backFactoryReworkDosageAmount);
		//
		// }
		impMaterielExeStat.setExpFinishProductAmount(exgUsed);
		impMaterielExeStat.setExpProductStat(hmImgUse.get(key));
		// 料件批准内销
		Double materialDomesticSales = this.contractAnalyseDao
				.findImpMaterielExeStat(contracts, searchType, objects,
						ImpExpType.MATERIAL_DOMESTIC_SALES, null, null, null,
						state, beginDate, endDate);
		impMaterielExeStat.setSaleInAmount(materialDomesticSales);
		/**
		 * 余料(缺料)情况 = 总进量 - 成品使用量 -料件复出-料件批准内销
		 */
		Double remainStat = totalImpAmount - exgUsed - backMaterielReExport
				- materialDomesticSales;
		// if (remainStat >= 0) {
		impMaterielExeStat.setRemainStat(remainStat);
		// } else {
		// impMaterielExeStat.setLackStat(remainStat);
		// }
		/**
		 * 库存数量 = 余料数量 - 余料转出（余料数量＝料件进口量－成品使用量）
		 */
		impMaterielExeStat.setStoreAmount(remainStat - remainForwardExpAmount);
		/**
		 * 可进口数量 = 合同总订量 - 总进口量
		 */
		Double canImpAmount = contractImportAmount - totalImpAmount;
		impMaterielExeStat.setCanImpAmount(canImpAmount);
		/**
		 * 比例 = 余料情况 / 总进口量
		 */
		if (remainStat >= 0) {
			if (totalImpAmount > 0 && remainStat >= 0) {
				double amount = CommonUtils.getDoubleByDigit(
						(remainStat / totalImpAmount) * 100, 2);
				impMaterielExeStat.setProportion(amount + "%");

				// System.out.println("totalImpAmount : " + totalImpAmount);
				// System.out.println("remainStat : " + remainStat);
			}
		} else {// 状态缺料为*
			impMaterielExeStat.setState("★");
		}
		/**
		 * 关封余量
		 */
		Double customsEnvelopRemainAmount = 0.0;
		impMaterielExeStat
				.setCustomsEnvelopRemainAmount(customsEnvelopRemainAmount);
		/**
		 * 可直接进口量 = 可进口数量-关封余量
		 */
		Double canDirectImpAmount = canImpAmount - customsEnvelopRemainAmount;
		impMaterielExeStat.setCanDirectImpAmount(canDirectImpAmount);
		return impMaterielExeStat;
	}

	/**
	 * 查找成品执行明细情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contractExg
	 *            合同备案成品
	 * @param impExpType
	 *            进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	public List findExpProductExeDetail(ContractExg contractExg,
			int impExpType, Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		Contract contract = contractExg.getContract();
		if (impExpType == -1) {
			returnList.addAll(findExpProductExeDetailByDirectExport(contract,
					contractExg, beginDate, endDate, state));
			returnList.addAll(this.findExpProductExeDetailByBackFactoryRework(
					contract, contractExg, beginDate, endDate, state));
			returnList.addAll(this
					.findExpProductExeDetailByTransferFactoryExport(contract,
							contractExg, beginDate, endDate, state));
			returnList.addAll(this.findExpProductExeDetailByReworkExport(
					contract, contractExg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.DIRECT_EXPORT) {
			returnList.addAll(findExpProductExeDetailByDirectExport(contract,
					contractExg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			returnList.addAll(this.findExpProductExeDetailByBackFactoryRework(
					contract, contractExg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT) {
			returnList.addAll(this
					.findExpProductExeDetailByTransferFactoryExport(contract,
							contractExg, beginDate, endDate, state));
		} else if (impExpType == ImpExpType.REWORK_EXPORT) {
			returnList.addAll(this.findExpProductExeDetailByReworkExport(
					contract, contractExg, beginDate, endDate, state));
		}
		return returnList;
	}

	/**
	 * 获得成品成品出口
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractExg
	 *            合同备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByDirectExport(Contract contract,
			ContractExg contractExg, Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findExpProductExeDetail(
				contract, contractExg, ImpExpType.DIRECT_EXPORT, beginDate,
				endDate, state);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, ExpFinishProductProgressDetail> map = new HashMap<String, ExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ExpFinishProductProgressDetail temp = new ExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setDirectExportAmount(materielImportAmount);
				temp.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
						: baseCustomsDeclaration.getCustomer().getName());
				map.put(key, temp);
			} else {
				ExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setDirectExportAmount((temp.getDirectExportAmount() == null ? 0.0
						: temp.getDirectExportAmount())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得成品转厂出口
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractExg
	 *            合同备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByTransferFactoryExport(
			Contract contract, ContractExg contractExg, Date beginDate,
			Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findExpProductExeDetail(
				contract, contractExg, ImpExpType.TRANSFER_FACTORY_EXPORT,
				beginDate, endDate, state);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, ExpFinishProductProgressDetail> map = new HashMap<String, ExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ExpFinishProductProgressDetail temp = new ExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setTransferFactoryExport(materielImportAmount);
				temp.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
						: baseCustomsDeclaration.getCustomer().getName());
				map.put(key, temp);
			} else {
				ExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setTransferFactoryExport((temp.getTransferFactoryExport() == null ? 0.0
						: temp.getTransferFactoryExport())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得成品退厂返工
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractExg
	 *            合同备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByBackFactoryRework(Contract contract,
			ContractExg contractExg, Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findExpProductExeDetail(
				contract, contractExg, ImpExpType.BACK_FACTORY_REWORK,
				beginDate, endDate, state);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, ExpFinishProductProgressDetail> map = new HashMap<String, ExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ExpFinishProductProgressDetail temp = new ExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setBackFactoryRework(materielImportAmount);
				temp.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
						: baseCustomsDeclaration.getCustomer().getName());
				map.put(key, temp);
			} else {
				ExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setBackFactoryRework((temp.getBackFactoryRework() == null ? 0.0
						: temp.getBackFactoryRework())
						+ materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 获得成品返工复出
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param contractExg
	 *            合同备案成品
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressDetail型，出口成品执行进度明细资料
	 */
	private List findExpProductExeDetailByReworkExport(Contract contract,
			ContractExg contractExg, Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		List tempList = this.contractAnalyseDao.findExpProductExeDetail(
				contract, contractExg, ImpExpType.REWORK_EXPORT, beginDate,
				endDate, state);
		/**
		 * 把相同报关单,相同成品序号的成品 SUM()
		 */
		Map<String, ExpFinishProductProgressDetail> map = new HashMap<String, ExpFinishProductProgressDetail>();
		for (int i = 0; i < tempList.size(); i++) {
			BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo = (BcsCustomsDeclarationCommInfo) tempList
					.get(i);
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			String key = baseCustomsDeclaration.getEmsHeadH2k()
					+ bcsCustomsDeclarationCommInfo.getCommSerialNo();
			if (map.get(key) == null) {
				ExpFinishProductProgressDetail temp = new ExpFinishProductProgressDetail();
				temp.setApplyToCustomBillNo(baseCustomsDeclaration
						.getCustomsDeclarationCode());
				temp.setApplyToCustomDate(baseCustomsDeclaration
						.getDeclarationDate());
				temp.setConveyance(baseCustomsDeclaration.getConveyance());
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setReworkExport(materielImportAmount);
				temp.setCustomer(baseCustomsDeclaration.getCustomer() == null ? ""
						: baseCustomsDeclaration.getCustomer().getName());
				map.put(key, temp);
			} else {
				ExpFinishProductProgressDetail temp = map.get(key);
				Double materielImportAmount = bcsCustomsDeclarationCommInfo
						.getCommAmount() == null ? 0.0
						: bcsCustomsDeclarationCommInfo.getCommAmount();
				temp.setReworkExport((temp.getReworkExport() == null ? 0.0
						: temp.getReworkExport()) + materielImportAmount);
			}
		}
		returnList.addAll(map.values());
		return returnList;
	}

	/**
	 * 查找成品执行总表情况 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductProgressTotal型，出口成品执行进度总表资料
	 */
	public List findExpProductExeTotal(Contract contract, Date beginDate,
			Date endDate, int state) {
		List returnList = new ArrayList();
		List contractExgList = this.contractDao
				.findContractExgByParentId(contract.getId());
		for (int i = 0; i < contractExgList.size(); i++) {
			ContractExg contractExg = (ContractExg) contractExgList.get(i);
			ExpFinishProductProgressTotal temp = new ExpFinishProductProgressTotal();
			temp.setContractExg(contractExg);
			/**
			 * 成品出口数量
			 */
			Double finishProductExpAmount = this.contractAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.DIRECT_EXPORT, beginDate, endDate, state);
			temp.setFinishProductExpAmount(finishProductExpAmount);
			/**
			 * 转厂出口数量
			 */
			Double transferExpAmount = this.contractAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.TRANSFER_FACTORY_EXPORT, beginDate,
							endDate, state);
			temp.setTransferExpAmount(transferExpAmount);
			/**
			 * 退厂返工总量
			 */
			Double backFactoryRework = this.contractAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.BACK_FACTORY_REWORK, beginDate, endDate,
							state);
			temp.setBackFactoryRework(backFactoryRework);
			/**
			 * 返工复出总量
			 */
			Double reworkExpAmount = this.contractAnalyseDao
					.findExpProductExeTotal(contract, contractExg,
							ImpExpType.REWORK_EXPORT, beginDate, endDate, state);
			temp.setReworkExpAmount(reworkExpAmount);
			/**
			 * 出口合计 = 成品出口数量 + 转厂出口数量 +返工复出总量 - 退厂返工总量
			 */
			Double expTotal = finishProductExpAmount + transferExpAmount
					+ reworkExpAmount - backFactoryRework; // 出口合计
			temp.setExpTotal(expTotal);
			/**
			 * 可出口总量 = 合同总定量 - 出口合计
			 */
			Double exportAmount = contractExg.getExportAmount() == null ? 0.0
					: contractExg.getExportAmount();
			Double canExpAmount = exportAmount - expTotal; // 可出口总量
			temp.setCanExpAmount(canExpAmount);
			returnList.add(temp);

		}
		return returnList;
	}

	/**
	 * 查找出口成品统计 来自[成品出口,转厂出口,退厂返工,返工复出]
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param searchType
	 *            设置要查找的属性
	 * @param state
	 *            状态类型
	 * @return List 是ExpFinishProductStat型，存放报关分析－－成品执行情况分析－－出口成品统计资料
	 */
	public List findExpFinishProductStat(List contracts, Date beginDate,
			Date endDate, int searchType, int state) {
		List<ExpFinishProductStat> returnList = new ArrayList<ExpFinishProductStat>();
		List contractExgList = this.contractAnalyseDao.findContractExg(
				searchType, contracts);
		for (int i = 0; i < contractExgList.size(); i++) {
			ExpFinishProductStat temp = new ExpFinishProductStat();
			Object[] objects = null;
			if (contractExgList.get(i).getClass().isArray()) {
				objects = (Object[]) contractExgList.get(i);
			} else {
				objects = new Object[] { contractExgList.get(i) };
			}
			temp = this.makeExpProductExeStat(contracts, objects, temp,
					searchType, state, beginDate, endDate);
			returnList.add(temp);
		}
		/**
		 * 加一条空的记录
		 */
		returnList.add(new ExpFinishProductStat());
		/**
		 * 加入统计记录
		 */
		ExpFinishProductStat temp = makeTotalExpFinishProductStat(returnList);
		returnList.add(temp);

		return returnList;
	}

	/**
	 * 生成成品统计对象
	 * 
	 * @param contracts
	 *            合同备案表头
	 * @param objects
	 *            searchType里查找的属性对应的值
	 * @param temp
	 *            是ExpFinishProductStat型，存放报关分析－－成品执行情况分析－－出口成品统计资料
	 * @param searchType
	 *            设置要查找的属性
	 * @param state
	 *            状态类型
	 * @return ExpFinishProductStat 存放报关分析－－成品执行情况分析－－出口成品统计资料
	 */
	private ExpFinishProductStat makeExpProductExeStat(List contracts,
			Object[] objects, ExpFinishProductStat temp, int searchType,
			int state, Date beginDate, Date endDate) {
		if (searchType == SearchType.NAME) {
			temp.setNameSpec(objects[0] == null ? "" : objects[0].toString());
		} else if (searchType == SearchType.NAME_SPEC) {
			temp.setNameSpec((objects[0] == null ? "" : objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
		} else if (searchType == SearchType.NAME_SPEC_CODE) {
			temp.setNameSpec((objects[0] == null ? "" : objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			temp.setComplexCode(objects[2] == null ? ""
			// : ((Complex) objects[2]).getCode());
					: (objects[2]).toString());
		} else if (searchType == SearchType.CODE) {
			temp.setComplexCode(objects[0] == null ? ""
			// : ((Complex) objects[0]).getCode());
					: (objects[0]).toString());
		} else if (searchType == SearchType.CODE_NAME) {
			temp.setComplexCode(objects[0] == null ? ""
			// : ((Complex) objects[0]).getCode());
					: (objects[0]).toString());
			temp.setNameSpec(objects[1] == null ? "" : objects[1].toString());
		} else if (searchType == SearchType.NAME_SPEC_CODE_UNIT) {
			temp.setNameSpec((objects[0] == null ? "" : objects[0].toString())
					+ (objects[1] == null ? "" : "/" + objects[1].toString()));
			temp.setComplexCode(objects[2] == null ? ""
			// : ((Complex) objects[2]).getCode());
					: (objects[2]).toString());
			temp.setUnit((Unit) objects[3]);
		}
		/**
		 * 合同总订量
		 */
		Double contractTotalAmount = this.contractAnalyseDao
				.findContractExportAmount(contracts, searchType, objects);
		temp.setContractTotalAmount(contractTotalAmount);
		/**
		 * 成品出口总量
		 */
		Double finishProductExpAmount = this.contractAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.DIRECT_EXPORT, state, beginDate, endDate);
		temp.setFinishProductExpAmount(finishProductExpAmount);
		/**
		 * 转厂出口总量
		 */
		Double transferExpAmount = this.contractAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.TRANSFER_FACTORY_EXPORT, state, beginDate,
						endDate);
		temp.setTransferExpAmount(transferExpAmount);
		/**
		 * 退厂返工总量
		 */
		Double backFactoryRework = this.contractAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.BACK_FACTORY_REWORK, state, beginDate,
						endDate);
		temp.setBackFactoryRework(backFactoryRework);
		/**
		 * 返工复出总量
		 */
		Double reworkExpAmount = this.contractAnalyseDao
				.findExpFinishProductStat(contracts, searchType, objects,
						ImpExpType.REWORK_EXPORT, state, beginDate, endDate);
		temp.setReworkExpAmount(reworkExpAmount);
		/**
		 * 总出口量 = 成品出口总量 + 转厂出口总量 + 返工复出总量 - 退厂返工总量
		 */
		Double totalExpAmount = finishProductExpAmount + transferExpAmount
				+ reworkExpAmount - backFactoryRework; // 出口合计
		temp.setTotalExpAmount(totalExpAmount);
		/**
		 * 可出口总量 = 合同总订量 - 总出口量
		 */
		Double canExpAmount = contractTotalAmount - totalExpAmount;
		/**
		 * 关封余量
		 */
		Double customsEnvelopRemainAmount = 0.0;
		temp.setCustomsEnvelopRemainAmount(customsEnvelopRemainAmount);
		/**
		 * 可直接进口量 = 可进口数量-关封余量
		 */
		Double canDirectExpAmount = canExpAmount - customsEnvelopRemainAmount;
		temp.setCanDirectExpAmount(canDirectExpAmount);

		temp.setCanExpAmount(canExpAmount);
		return temp;
	}

	/**
	 * 生成成品统计记录
	 * 
	 * @param returnList
	 *            存放报关分析－－成品执行情况分析－－出口成品统计资料
	 * @return ExpFinishProductStat 存放报关分析－－成品执行情况分析－－出口成品统计资料
	 */
	private ExpFinishProductStat makeTotalExpFinishProductStat(
			List<ExpFinishProductStat> returnList) {
		ExpFinishProductStat temp = new ExpFinishProductStat();
		/**
		 * 合同总订量
		 */
		Double contractTotalAmount = 0.0;
		/**
		 * 成品出口总量
		 */
		Double finishProductExpAmount = 0.0;
		/**
		 * 转厂出口总量
		 */
		Double transferExpAmount = 0.0;
		/**
		 * 退厂返工总量
		 */
		Double backFactoryRework = 0.0;
		/**
		 * 返工复出总量
		 */
		Double reworkExpAmount = 0.0;
		/**
		 * 总出口量
		 */
		Double totalExpAmount = 0.0;
		/**
		 * 可进口总量
		 */
		Double canExpAmount = 0.0;
		/**
		 * 关封余量
		 */
		Double customsEnvelopRemainAmount = 0.0;
		/**
		 * 可直接进口量
		 */
		Double canDirectExpAmount = 0.0;

		for (int i = 0; i < returnList.size(); i++) {
			ExpFinishProductStat e = returnList.get(i);
			/**
			 * 合同总订量
			 */
			contractTotalAmount += e.getContractTotalAmount() == null ? 0.0 : e
					.getContractTotalAmount();
			/**
			 * 成品出口总量
			 */
			finishProductExpAmount += e.getFinishProductExpAmount() == null ? 0.0
					: e.getFinishProductExpAmount();
			/**
			 * 转厂出口总量
			 */
			transferExpAmount += e.getTransferExpAmount() == null ? 0.0 : e
					.getTransferExpAmount();
			/**
			 * 退厂返工总量
			 */
			backFactoryRework += e.getBackFactoryRework() == null ? 0.0 : e
					.getBackFactoryRework();
			/**
			 * 返工复出总量
			 */
			reworkExpAmount += e.getReworkExpAmount() == null ? 0.0 : e
					.getReworkExpAmount();

			/**
			 * 总出口量
			 */
			totalExpAmount += e.getTotalExpAmount() == null ? 0.0 : e
					.getTotalExpAmount();
			/**
			 * 可进口总量
			 */
			canExpAmount += e.getCanExpAmount() == null ? 0.0 : e
					.getCanExpAmount();
			/**
			 * 关封余量
			 */
			customsEnvelopRemainAmount += e.getCustomsEnvelopRemainAmount() == null ? 0.0
					: e.getCustomsEnvelopRemainAmount();
			/**
			 * 可直接进口量
			 */
			canDirectExpAmount += e.getCanDirectExpAmount() == null ? 0.0 : e
					.getCanDirectExpAmount();
		}
		/**
		 * 合同总订量
		 */
		temp.setContractTotalAmount(contractTotalAmount);
		/**
		 * 成品出口总量
		 */
		temp.setFinishProductExpAmount(finishProductExpAmount);
		/**
		 * 转厂出口总量
		 */
		temp.setTransferExpAmount(transferExpAmount);
		/**
		 * 退厂返工总量
		 */
		temp.setBackFactoryRework(backFactoryRework);
		/**
		 * 返工复出总量
		 */
		temp.setReworkExpAmount(reworkExpAmount);
		/**
		 * 总出口量
		 */
		temp.setTotalExpAmount(totalExpAmount);
		/**
		 * 可进口总量
		 */
		temp.setCanExpAmount(canExpAmount);
		/**
		 * 关封余量
		 */
		temp.setCustomsEnvelopRemainAmount(customsEnvelopRemainAmount);
		/**
		 * 可直接进口量
		 */
		temp.setCanDirectExpAmount(canDirectExpAmount);

		temp.setComplexCode("合计");
		return temp;
	}

	/**
	 * 各合同执行状况表
	 * 
	 * @param state
	 *            状态类型
	 * @return List 是ContractStat型，存放报关分析－－各合同执行状况表资料
	 */
	public List findContractAnalyseStat(int state) {
		List lsResult = new ArrayList();
		List lsContract = contractDao.findContractByProcessExe();
		for (int i = 0; i < lsContract.size(); i++) {
			ContractStat stat = new ContractStat();
			Contract contract = (Contract) lsContract.get(i);
			String emsNo = contract.getEmsNo();
			stat.setContractNo(contract.getImpContractNo());
			stat.setEmsNo(contract.getEmsNo());
			stat.setImgAmount(contract.getImgAmount());
			stat.setExgAmount(contract.getExgAmount());
			stat.setAvailabilityDate(contract.getEndDate());
			// GregorianCalendar deferDate =new GregorianCalendar();
			// deferDate.setTime(contract.getDeferDate());
			// deferDate.()
			// stat.setDelayTerm(contract.getDeferDate()-contract.getAvailabilityDate());
			/**
			 * 料件进口值
			 */
			double directImport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null, emsNo, state);
			/**
			 * 转厂进口值
			 */
			double transferFactoryImport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
							state);
			/**
			 * 余料进口值
			 */
			double remainForwardImport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
							state);
			/**
			 * 余料出口值
			 */
			double remainForwardExport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
							ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
							state);
			// /**
			// * 料件退换进口值
			// */
			// double exchangeImport = this.contractAnalyseDao
			// .findCommInfoTotalMoney(ImpExpFlag.IMPORT,
			// ImpExpType.DIRECT_IMPORT, new String[] { "0300",
			// "0700" }, emsNo, state);
			/**
			 * 料件退换出口值
			 */
			double exchangeExport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, emsNo, state);
			/**
			 * 进口总值=料件进口值+转厂进口值+料件退换进口值(包含在料件直接进口中)+余料进口-料件退换出口值-余料出口
			 */
			double impTotalMoney = directImport + transferFactoryImport
					+ remainForwardImport - exchangeExport
					- remainForwardExport;
			stat.setImpTotalMoney(impTotalMoney);
			double imgAmount = (stat.getImgAmount() == null ? 0 : stat
					.getImgAmount());
			double impScale = (imgAmount == 0 ? 0 : (impTotalMoney / imgAmount));
			stat.setImpScale(impScale * 100);
			/**
			 * 成品出口值
			 */
			double directExport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, emsNo, state);
			double transferFactoryExport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
							state);
			double backFactoryRework = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, emsNo, state);
			double reworkExport = this.contractAnalyseDao
					.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, emsNo, state);

			/**
			 * 总出口值=成品出口值+转厂出口值-退厂返工值+返工复出值
			 */
			double expTotalMoney = directExport + transferFactoryExport
					- backFactoryRework + reworkExport;
			stat.setExpTotalMoney(expTotalMoney);
			double exgAmount = stat.getExgAmount() == null ? 0 : stat
					.getExgAmount();
			double expScale = (exgAmount == 0 ? 0 : (expTotalMoney / exgAmount));
			stat.setExpScale(expScale * 100);
			lsResult.add(stat);
		}
		return lsResult;
	}

	/**
	 * 根据合同、和临时的合同料件资料 查找合同成品资料
	 * 
	 * @param contractImgList
	 *            存放合同料件的临时资料
	 * @param contractList
	 *            是List<Contract>型，合同备案表头
	 * @return List<TempFinishProduct> 存放临时的成品资料
	 */
	public List<TempFinishProduct> findContractBomByContractImg(
			List<TempContractImg> contractImgList, List<Contract> contractList) {
		List<TempFinishProduct> returnList = new ArrayList<TempFinishProduct>();
		List dataSource = this.contractAnalyseDao.findContractBomByContractImg(
				contractImgList, contractList);
		for (int i = 0; i < dataSource.size(); i++) {
			Object[] objs = (Object[]) dataSource.get(i);
			TempFinishProduct temp = new TempFinishProduct();
			temp.setUnitDosage((Double) objs[0]);
			temp.setProductName((String) objs[1]);
			returnList.add(temp);
		}
		return returnList;
	}

	/**
	 * 进出口报关单查询
	 * 
	 * @param lsContract
	 *            是List型，合同备案表头
	 * @param impExpFlag
	 *            进出口标志
	 * @param impExpType
	 *            进出口类型
	 * @param customsDeclarationCode
	 *            预录入号或报关单号或入库报关单号
	 * @param fecavBillCode
	 *            批准文号(外汇核销单号)
	 * @param containerCode
	 *            集装箱号码
	 * @param trade
	 *            贸易方式
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            状态类型
	 * @return List 是ImpExpCustomsDeclarationList型，存放报关分析－－进、出口报关单列表资料
	 */
	public List findImpExpCustomsDeclarationList(List lsContract,
			Integer impExpFlag, Integer impExpType,
			String customsDeclarationCode, String fecavBillCode,
			String containerCode, Trade trade, Date beginDate, Date endDate,
			int state) {
		List lsResult = new ArrayList();
		List list = this.contractAnalyseDao.findImpExpCustomsDeclarationList(
				lsContract, impExpFlag, impExpType, customsDeclarationCode,
				fecavBillCode, containerCode, trade, beginDate, endDate, state);
		Map<String, List> hm = new HashMap<String, List>();
		// if (containerCode != null && (!"".equals(containerCode.trim()))) {
		hm = this.contractAnalyseDao.findImpExpContainerCodeList(lsContract,
				impExpFlag, impExpType, containerCode, beginDate, endDate,
				state);
		// }
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String cid = objs[0].toString();
			List lsContainerCode = hm.get(cid);
			if ((lsContainerCode == null)
					&& (containerCode != null && (!containerCode.equals("")))) {
				continue;
			}
			ImpExpCustomsDeclarationList impExpList = new ImpExpCustomsDeclarationList();
			BcsCustomsDeclaration c = (BcsCustomsDeclaration) this.contractStatDao
					.load(BcsCustomsDeclaration.class, cid);
			impExpList.setContractNo(c.getContract());
			impExpList.setEmsNo(c.getEmsHeadH2k());
			impExpList.setCustomsDeclarationDate(c.getDeclarationDate());
			impExpList.setCustomsDeclarationCode(c.getCustomsDeclarationCode());
			impExpList.setTotalMoney(objs[2] == null ? 0 : Double
					.parseDouble(objs[2].toString()));
			int commInfoNum = objs[1] == null ? 0 : Integer.parseInt(objs[1]
					.toString());
			int pageCount = commInfoNum / 5;
			if (commInfoNum % 5 > 0) {
				pageCount++;
			}
			impExpList.setCurr(c.getCurrency());
			impExpList.setCommodityNum(c.getCommodityNum());
			impExpList.setPageCount(pageCount);
			impExpList.setGrossWeight(c.getGrossWeight());
			impExpList.setNetWeight(c.getNetWeight());
			impExpList.setCustomer(c.getCustomer());
			impExpList.setWrapType(c.getWrapType());
			impExpList.setTradeMode(c.getTradeMode());
			impExpList.setIseffective(c.getEffective());
			impExpList.setFecavBillCode(c.getAuthorizeFile());
			impExpList.setContainerCode(c.getContainerNum());
			if ((lsContainerCode != null) && (lsContainerCode.size() > 0)) {
				String containrCode = "";
				for (int j = 0; j < lsContainerCode.size(); j++) {
					containrCode += (lsContainerCode.get(j) == null ? "/"
							: lsContainerCode.get(j).toString() + "/");

				}
				impExpList.setContainerCode(containrCode);

			} else {
				impExpList.setContainerCode(impExpList.getContainerCode());
			}
			lsResult.add(impExpList);
		}
		return lsResult;
	}

	/**
	 * 查询成品物料的出口数量
	 * 
	 * @param ptNo
	 * @param versionNo
	 * @param contractList
	 * @param beginDate
	 * @param endDate
	 * @param impExpTypes
	 * @return
	 */
	public List findFinishedProductExportAmount(List lsMateriel,
			List<Contract> contractList, Date beginDate, Date endDate) {
		List<TempImpMaterielExeStat> lsResult = new ArrayList<TempImpMaterielExeStat>();
		List<ContractBom> lsContractBom = new ArrayList<ContractBom>();
		for (int i = 0; i < lsMateriel.size(); i++) {
			ContractImg contractImg = (ContractImg) lsMateriel.get(i);
			// String contractId = contractImg.getContract().getId();
			// Integer seqNum = contractImg.getSeqNum();
			List list = this.contractDao.findContractBom(contractList,
					contractImg);

			for (int j = 0; j < list.size(); j++) {
				ContractBom contractBom = (ContractBom) list.get(j);
				lsContractBom.add(contractBom);
			}

		}

		for (int i = 0; i < lsContractBom.size(); i++) {
			ContractBom contractBom = (ContractBom) lsContractBom.get(i);
			TempImpMaterielExeStat tempImpMaterielExeStat = new TempImpMaterielExeStat();
			tempImpMaterielExeStat.setContractBom(contractBom);
			tempImpMaterielExeStat
					.setComplexCode(contractBom.getComplex() == null ? ""
							: contractBom.getComplex().getCode());
			tempImpMaterielExeStat.setName(contractBom.getName());
			tempImpMaterielExeStat.setSpec(contractBom.getSpec());
			tempImpMaterielExeStat.setUnit(contractBom.getUnit());
			tempImpMaterielExeStat
					.setUnitDosage(contractBom.getUnitDosage() == null ? 0.0
							: contractBom.getUnitDosage());
			tempImpMaterielExeStat
					.setUnitWaste(contractBom.getUnitWaste() == null ? 0.0
							: contractBom.getUnitWaste());
			tempImpMaterielExeStat
					.setWaste(contractBom.getWaste() == null ? 0.0
							: contractBom.getWaste());

			/**
			 * 成品出口总量
			 */
			// hwy 2012-09-26
			Double finishProductExpAmount = this.contractAnalyseDao
					.findFinishedProductExportAmount(contractBom
							.getContractExg().getSeqNum(), contractBom
							.getContractExg().getComplex().getCode(),
							contractBom.getContractExg().getName(), contractBom
									.getContractExg().getSpec(), contractBom
									.getContractExg().getUnit(), contractBom
									.getContractExg().getContract(), beginDate,
							endDate, new Integer[] { ImpExpType.DIRECT_EXPORT });

			/**
			 * 转厂出口总量
			 */
			Double transferExpAmount = this.contractAnalyseDao
					.findFinishedProductExportAmount(
							contractBom.getContractExg().getSeqNum(),
							contractBom.getContractExg().getComplex().getCode(),
							contractBom.getContractExg().getName(),
							contractBom.getContractExg().getSpec(),
							contractBom.getContractExg().getUnit(),
							contractBom.getContractExg().getContract(),
							beginDate,
							endDate,
							new Integer[] { ImpExpType.TRANSFER_FACTORY_EXPORT });
			/**
			 * 退厂返工总量
			 */
			Double backFactoryRework = this.contractAnalyseDao
					.findFinishedProductExportAmount(contractBom
							.getContractExg().getSeqNum(), contractBom
							.getContractExg().getComplex().getCode(),
							contractBom.getContractExg().getName(), contractBom
									.getContractExg().getSpec(), contractBom
									.getContractExg().getUnit(), contractBom
									.getContractExg().getContract(), beginDate,
							endDate,
							new Integer[] { ImpExpType.BACK_FACTORY_REWORK });
			/**
			 * 返工复出总量
			 */
			Double reworkExpAmount = this.contractAnalyseDao
					.findFinishedProductExportAmount(contractBom
							.getContractExg().getSeqNum(), contractBom
							.getContractExg().getComplex().getCode(),
							contractBom.getContractExg().getName(), contractBom
									.getContractExg().getSpec(), contractBom
									.getContractExg().getUnit(), contractBom
									.getContractExg().getContract(), beginDate,
							endDate, new Integer[] { ImpExpType.REWORK_EXPORT });
			/**
			 * 总出口数量
			 */
			tempImpMaterielExeStat.setExportAmount(finishProductExpAmount
					+ transferExpAmount + reworkExpAmount - backFactoryRework);
			/**
			 * 单项用量
			 */
			Double unitDosage = CommonUtils.getDoubleExceptNull(tempImpMaterielExeStat.getUnitWaste()) 
					/ (1 - CommonUtils.getDoubleExceptNull(tempImpMaterielExeStat.getWaste()) * 0.01);
			
			/**
			 * 总耗用量
			 */
			tempImpMaterielExeStat.setUsedAmount(CommonUtils.getDoubleExceptNull(tempImpMaterielExeStat.getExportAmount()) 
					* unitDosage);
			/**
			 * 结转出口耗用量
			 */
			tempImpMaterielExeStat.setTransferExpUsedAmount(transferExpAmount * unitDosage);
			/**
			 * 直接出口耗用量
			 */
			tempImpMaterielExeStat.setDirectExpUsedAmount(finishProductExpAmount * unitDosage);

			lsResult.add(tempImpMaterielExeStat);
		}

		return lsResult;
	}
}
