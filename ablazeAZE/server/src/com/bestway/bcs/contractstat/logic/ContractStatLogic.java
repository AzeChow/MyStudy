/*
 * Created on 2005-5-18
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractstat.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.entity.MakeBcsCustomsDeclaration;
import com.bestway.bcs.contractstat.dao.ContractStatDao;
import com.bestway.bcs.contractstat.entity.CancelAfterVerification;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcs.contractstat.entity.ExpProductStatResult;
import com.bestway.bcs.contractstat.entity.ExpScheduleDetail;
import com.bestway.bcs.contractstat.entity.ImpExpCustomsDeclarationCommInfo;
import com.bestway.bcs.contractstat.entity.ImpMaterialStat;
import com.bestway.bcs.contractstat.entity.ImpMaterialStatResult;
import com.bestway.bcs.contractstat.entity.ImpScheduleDetail;
import com.bestway.bcs.contractstat.entity.StockStatisticsBill;
import com.bestway.bcs.contractstat.entity.TempBcsCustomsDeclarCommInfo;
import com.bestway.bcs.contractstat.entity.TempImpExpCommodityInfo;
import com.bestway.bcs.contractstat.entity.TempMateriDetail;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.MaterialBomDetail;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * checked by kcb 2008-9-20 合同统计逻辑层
 * 
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class ContractStatLogic {
	/**
	 * 合同核销DAO
	 */
	private ContractCavDao contractCavDao;
	/**
	 * 合同统计DAO
	 */
	private ContractStatDao contractStatDao;
	/**
	 * 合同管理DAO
	 */
	private ContractDao contractDao;

	/**
	 * 获取contractStatDao
	 * 
	 * @return Returns the contractqryDao.
	 */
	public ContractStatDao getContractStatDao() {
		return contractStatDao;
	}

	/**
	 * 设置contractStatDao
	 * 
	 * @param contractqryDao
	 *            The contractqryDao to set.
	 */
	public void setContractStatDao(ContractStatDao contractqryDao) {
		this.contractStatDao = contractqryDao;
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

	/**
	 * 获取contractDao
	 * 
	 * @return contractDao
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
	 * 计算进口料件报关总值
	 * 
	 * @param list
	 *            是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @return ImpMaterialStatResult 存放统计报表中的进口料件报关情况表－－统计数据资料
	 */
	public ImpMaterialStatResult impMaterialStat(List<ImpMaterialStat> list) {
		ImpMaterialStatResult statResult = new ImpMaterialStatResult();
		// 进口总量
		double impTotalMoney = 0.0;
		// 进口合同金额
		double impContractMoney = 0.0;
		// 余料总值
		double impRemainMoney = 0.0;

		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		for (ImpMaterialStat impMaterialStat : list) {
			impContractMoney += (impMaterialStat.getContractAmount() == null ? 0.0
					: impMaterialStat.getContractAmount())
					* (impMaterialStat.getUnitPrice() == null ? 0.0
							: impMaterialStat.getUnitPrice());
			// 此处的impTotalMoney已是折算合同币制汇率
			if (parameter.getCheckIsComputeReturn() != null
					&& parameter.getCheckIsComputeReturn()) {

				impTotalMoney += (impMaterialStat.getImpTotalMoney() == null ? 0.0
						: impMaterialStat.getImpTotalMoney()
								- (impMaterialStat.getFuchuExport() == null ? 0.0
										: impMaterialStat.getFuchuExport()));
			} else {
				impTotalMoney += impMaterialStat.getImpTotalMoney() == null ? 0.0
						: impMaterialStat.getImpTotalMoney();
			}

			impRemainMoney += (impMaterialStat.getRemainMoney() == null ? 0.0
					: impMaterialStat.getRemainMoney());
		}

		statResult.setImpTotalMoney(CommonUtils.getDoubleByDigit(impTotalMoney,
				3));
		statResult.setContractMoney(CommonUtils.getDoubleByDigit(
				impContractMoney, 3));
		statResult.setRemainMoney(CommonUtils.getDoubleByDigit(impRemainMoney,
				3));
		statResult.setImpTotalScale(CommonUtils.getDoubleByDigit(
				impContractMoney == 0 ? 0.0
						: (impTotalMoney / impContractMoney) * 100, 3));
		statResult.setRemainScale(CommonUtils.getDoubleByDigit(
				impContractMoney == 0 ? 0.0
						: (impRemainMoney / impContractMoney) * 100, 3));
		return statResult;
	}

	/**
	 * 进口料件情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param isCountInvoice
	 *            是否统计发票数量
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	public List<ImpMaterialStat> findImpMaterialStat(Contract contract,
			Date beginDate, Date endDate, boolean isCountInvoice) {
		return this.findImpMaterialStat(contract, beginDate, endDate,
				CustomsDeclarationState.ALL, isCountInvoice);
	}

	/**
	 * 进口料件情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            合同状态
	 * @param isCountInvoice
	 *            是否统计发票数量
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	/**
	 * @param contracts
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @param isCountInvoice
	 * @param isDetachCompute
	 * @return
	 */
	public List<ImpMaterialStat> findImpMaterialStatByContracts(List contracts,
			Date beginDate, Date endDate, int state, boolean isCountInvoice,
			boolean isDetachCompute) {
		if (isDetachCompute) {
			System.out.println("分开统计工作");
			List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
				List<ImpMaterialStat> lsData = findImpMaterialStat(contract,
						beginDate, endDate, state, isCountInvoice);
				for (ImpMaterialStat ims : lsData) {
					ims.setEmsNo(contract.getEmsNo());
					ims.setImpContractNo(contract.getImpContractNo());
					lsResult.add(ims);
				}
			}
			return lsResult;
		} else {
			if (contracts.size() == 1) {
				Contract contract = (Contract) contracts.get(0);
				List<ImpMaterialStat> lsData = findImpMaterialStatForOne(
						contract, beginDate, endDate, state, isCountInvoice);
				for (ImpMaterialStat ims : lsData) {
					ims.setEmsNo("");
				}
				return lsData;
			} else {
				Map map = new HashMap();
				for (int i = 0; i < contracts.size(); i++) {
					Contract contract = (Contract) contracts.get(i);
					List<ImpMaterialStat> lsData = findImpMaterialStat(
							contract, beginDate, endDate, state, isCountInvoice);
					for (ImpMaterialStat newData : lsData) {
						newData.setEmsNo("");
						String key = (newData.getComplex().getCode().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						newData.setCommodityInfoRemain(null);
						// newData.setCredenceNo(null);
						/**
						 * 进口料件情况统计表单独查询一本手册，或者查询多 本手册但是分开统计时，可以带出手册里面料件的说明栏位
						 * 所以多本查询时，去掉备注
						 */
						newData.setNote("");
						if (map.get(key) == null) {
							if (contracts.size() > 1) {
								newData.setSerialNo(null);
							}
							map.put(key, newData);
						} else {
							ImpMaterialStat oldData = (ImpMaterialStat) map
									.get(key);
							/**
							 * 未转报关单数量
							 */
							oldData.setNoTranCustomsNum((oldData
									.getNoTranCustomsNum() == null ? 0.0
									: oldData.getNoTranCustomsNum())
									+ (newData.getNoTranCustomsNum() == null ? 0.0
											: newData.getNoTranCustomsNum()));
							/**
							 * 余料进口
							 */
							oldData.setRemainImport((oldData.getRemainImport() == null ? 0.0
									: oldData.getRemainImport())
									+ (newData.getRemainImport() == null ? 0.0
											: newData.getRemainImport()));
							/**
							 * 余料转出
							 */
							oldData.setRemainForward((oldData
									.getRemainForward() == null ? 0.0 : oldData
									.getRemainForward())
									+ (newData.getRemainForward() == null ? 0.0
											: newData.getRemainForward()));
							/**
							 * 内销数量
							 */
							oldData.setInternalAmount((oldData
									.getInternalAmount() == null ? 0.0
									: oldData.getInternalAmount())
									+ (newData.getInternalAmount() == null ? 0.0
											: newData.getInternalAmount()));
							/**
							 * 料件退换进口数
							 */
							oldData.setExchangeImport((oldData
									.getExchangeImport() == null ? 0.0
									: oldData.getExchangeImport())
									+ (newData.getExchangeImport() == null ? 0.0
											: newData.getExchangeImport()));
							/**
							 * 边角料复出
							 */
							oldData.setLeftovermaterialExport((oldData
									.getLeftovermaterialExport() == null ? 0.0
									: oldData.getLeftovermaterialExport())
									+ (newData.getLeftovermaterialExport() == null ? 0.0
											: newData
													.getLeftovermaterialExport()));
							/**
							 * 边角料内销
							 */
							oldData.setLeftovermaterial((oldData
									.getLeftovermaterial() == null ? 0.0
									: oldData.getLeftovermaterial())
									+ (newData.getLeftovermaterial() == null ? 0.0
											: newData.getLeftovermaterial()));
							/**
							 * 总边角料内销
							 */
							oldData.setAllTotalleftovermaterial((oldData
									.getAllTotalleftovermaterial() == null ? 0.0
									: oldData.getAllTotalleftovermaterial())
									+ (newData.getAllTotalleftovermaterial() == null ? 0.0
											: newData
													.getAllTotalleftovermaterial()));
							/**
							 * 总边角料余量
							 */
							oldData.setLeftovermaterialremain((oldData
									.getLeftovermaterialremain() == null ? 0.0
									: oldData.getLeftovermaterialremain())
									+ (newData.getLeftovermaterialremain() == null ? 0.0
											: newData
													.getLeftovermaterialremain()));
							/**
							 * 退换出口量
							 */
							oldData.setExchangeExport((oldData
									.getExchangeExport() == null ? 0.0
									: oldData.getExchangeExport())
									+ (newData.getExchangeExport() == null ? 0.0
											: newData.getExchangeExport()));
							/**
							 * 成品使用金额
							 */
							oldData.setProductUseMoney((oldData
									.getProductUseMoney() == null ? 0.0
									: oldData.getProductUseMoney())
									+ (newData.getProductUseMoney() == null ? 0.0
											: newData.getProductUseMoney()));
							/**
							 * 关封余量
							 */
							oldData.setCustomsEnvelopRemain((oldData
									.getCustomsEnvelopRemain() == null ? 0.0
									: oldData.getCustomsEnvelopRemain())
									+ (newData.getCustomsEnvelopRemain() == null ? 0.0
											: newData.getCustomsEnvelopRemain()));
							/**
							 * 可直接进口量
							 */
							oldData.setCanDirectImportAmount((oldData
									.getCanDirectImportAmount() == null ? 0.0
									: oldData.getCanDirectImportAmount())
									+ (newData.getCanDirectImportAmount() == null ? 0.0
											: newData
													.getCanDirectImportAmount()));
							/**
							 * 总进口量
							 */
							oldData.setAllImpTotalAmont(oldData
									.getAllImpTotalAmont() == null ? 0.0
									: oldData.getAllImpTotalAmont()
											+ (newData.getAllImpTotalAmont() == null ? 0.0
													: newData
															.getAllImpTotalAmont()));
							/**
							 * 合同定量
							 */
							oldData.setContractAmount(checkNullForDouble(oldData
									.getContractAmount())
									+ checkNullForDouble(newData
											.getContractAmount()));
							/**
							 * 本期总进口量
							 */
							oldData.setImpTotalAmont(checkNullForDouble(oldData
									.getImpTotalAmont()
									+ newData.getImpTotalAmont()));
							/**
							 * 本期总进口金额
							 */
							oldData.setImpTotalMoney(checkNullForDouble(oldData
									.getImpTotalMoney()
									+ newData.getImpTotalMoney()));
							/**
							 * 报关单进口量
							 */
							oldData.setImpCDAmount(checkNullForDouble(oldData
									.getImpCDAmount())
									+ checkNullForDouble(newData
											.getImpCDAmount()));
							/**
							 * 料件进口量
							 */
							oldData.setDirectImport(checkNullForDouble(oldData
									.getDirectImport())
									+ checkNullForDouble(newData
											.getDirectImport()));
							/**
							 * 转厂进口量
							 */
							oldData.setTransferFactoryImport(checkNullForDouble(oldData
									.getTransferFactoryImport())
									+ checkNullForDouble(newData
											.getTransferFactoryImport()));

							/**
							 * 退料退换量
							 */
							oldData.setBackMaterialExchange(checkNullForDouble(oldData
									.getBackMaterialExchange())
									+ checkNullForDouble(newData
											.getBackMaterialExchange()));
							/**
							 * 退料复出量
							 */
							oldData.setBackMaterialReturn(checkNullForDouble(oldData
									.getBackMaterialReturn())
									+ checkNullForDouble(newData
											.getBackMaterialReturn()));
							/**
							 * 退料出口量
							 */
							oldData.setBackMaterialExport(checkNullForDouble(oldData
									.getBackMaterialExport())
									+ checkNullForDouble(newData
											.getBackMaterialExport()));
							/**
							 * 成品使用量
							 */
							oldData.setProductUse(checkNullForDouble(oldData
									.getProductUse())
									+ checkNullForDouble(newData
											.getProductUse()));
							/**
							 * 余量
							 */
							oldData.setRemainAmount(checkNullForDouble(oldData
									.getRemainAmount())
									+ checkNullForDouble(newData
											.getRemainAmount()));
							/**
							 * 缺量
							 */
							oldData.setUllage(checkNullForDouble(oldData
									.getUllage())
									+ checkNullForDouble(newData.getUllage()));
							/**
							 * 库存
							 */
							oldData.setStockAmount(checkNullForDouble(oldData
									.getStockAmount())
									+ checkNullForDouble(newData
											.getStockAmount()));
							/**
							 * 可进口量
							 */
							oldData.setCanImportAmount(checkNullForDouble(oldData
									.getCanImportAmount())
									+ checkNullForDouble(newData
											.getCanImportAmount()));
							/**
							 * 余料金额
							 */
							oldData.setRemainMoney(checkNullForDouble(oldData
									.getRemainMoney())
									+ checkNullForDouble(newData
											.getRemainMoney()));
							/**
							 * 单价
							 */
							oldData.setUnitPrice(null);
						}
					}
				}
				List lsResult = new ArrayList(map.values());
				Collections.sort(lsResult);
				return lsResult;
			}
		}
	}

	/**
	 * 检查是否为空，如果是则返回0
	 * 
	 * @param dou
	 *            小数Double
	 * @return
	 */
	private Double checkNullForDouble(Double dou) {
		return dou == null ? 0.0 : dou;
	}

	/**
	 * 进口料件情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	public List<ImpMaterialStat> findImpMaterialStat(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice) {
		List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		// List<Integer> list = this.contractCavDao.findAllCommInfo(true, emsNo,
		// beginDate, endDate, state);
		List list = this.contractDao
				.findContractImgAndSeqNoByContractId(contract.getId());
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		HashMap<String, Double> AllleftovermaterialUse = new HashMap<String, Double>();
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate, state,
				AllleftovermaterialUse);
		System.out.println("innerMergeSeqNum List=" + list.size());
		/**
		 * 关封余量-深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTableBySecond(this.contractStatDao
				.findFptAppItemCount(emsNo, true, beginDate, endDate));

		/**
		 * 可签关封
		 */
		HashMap<String, Double> hmCommodityInfoRemain = converListToHashTableBySecond(this.contractStatDao
				.findCommodityInfoRemain(emsNo, true, beginDate, endDate));

		/**
		 * 关封余量-转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTableBySecond(this.contractStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, true, beginDate,
						endDate));
		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
		for (int i = 0; i < nset.size(); i++) {
			String key = nset.get(i) == null ? "" : nset.get(i).toString();
			if (hmCustomsEnvelop.get(key) == null) {
				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
						hmCustomsEnvelopTrans.get(key));
			} else {
				Double ss = hmCustomsEnvelop.get(key);
				Double dd = hmCustomsEnvelopTrans.get(key);
				hmCustomsEnvelop.put(key, (ss + dd));
			}
		}
		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmDirectImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 进口数量金额=进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmDirectImportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));

		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmTransferFactoryImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 转厂进口量金额=转厂进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmTransferFactoryImportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));

		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmRemainImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));// new String[] { "0657",
		// "0258" }
		/**
		 * 余料进口金额=余料进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmRemainImportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 余料出口 (余料结转出口)
		 */
		HashMap<String, Double> hmRemainForward = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
						beginDate, endDate, state));// new String[] {
		// "0657","0258" }

		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmExchangeImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, beginDate,
						endDate, state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmExchangeExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件退换出口数金额=料件退换出口数量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmExchangeExportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		 */
		HashMap<String, Double> hmBackMaterialReturn = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件复出金额=料件复出出口数量 × 折算合同币制汇率 × 单价
		 */
		HashMap<String, Double> hmFuchuExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate, state));
		/**
		 * 退料出口量
		 */
		HashMap<String, Double> hmBackMaterialExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
						beginDate, endDate, state));
		// hwy2013-4-18，为是总进口量与数据报核中料件表总进口量一致，减去退料出口量和余料结转出口量
		/**
		 * 退料出口量（根据合同查询）
		 */
		HashMap<String, Double> hmBackMaterialExportN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo, null,
						null, state));
		/**
		 * 余料结转出口 (根据合同查询)
		 */
		HashMap<String, Double> hmRemainForwardN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo, null,
						null, state));
		// HashMap<String, Double> hmAllImgUse = new HashMap<String, Double>();
		// this.calContractImgUse(contract, hmAllImgUse, null, null, state);
		// 关封余量
		// HashMap<String, Double> hmAllCustomsEnvelop =
		// converListToHashTable(this.contractStatDao
		// .findCustomsEnvelopCommInfoCount(emsNo, null,null));
		// List l = this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
		// ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES, new String[] {
		// "0844", "0845" }, emsNo, beginDate, endDate,
		// state);

		/**
		 * 边角料内销海关批准内销--进出口报关单
		 */
		HashMap<String, Double> leftovermaterial = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0844", "0845" }, emsNo, beginDate, endDate,
						state));
		/**
		 * 边角料内销海关批准内销 --特殊报关单 edit by cjb 2009.11.30
		 */
		HashMap<String, Double> leftovermaterialSpecial = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
						ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES,
						new String[] { "0844", "0845" }, emsNo, beginDate,
						endDate, state));

		// System.out.println("size="+l.size());
		// for(String key : leftovermaterial.keySet()){
		// System.out.println("key="+key+"  value=" +leftovermaterial.get(key));
		// }
		/**
		 * 本期边角料复出 2014-5-10 从出口报关单退料出口类型，改为【特殊报关单】进出口类型为【边角料退港】贸易方式【边角料复出】
		 */
		HashMap<String, Double> leftovermaterialExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
						ImpExpType.REMIAN_MATERIAL_BACK_PORT, new String[] {
								"0864", "0865" }, emsNo, beginDate, endDate,
						state));

		/**
		 * 料件内销
		 */
		HashMap<String, Double> internalAmount = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0644", "0245" }, emsNo, beginDate, endDate,
						state));

		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmAllDirectImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));

		/**
		 * 料件进口数量（只根据合同号查询）
		 */
		HashMap<String, Double> hmAllDirectImportN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmountContract(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
						state));
		/**
		 * 转厂进口量（只根据合同号查询）
		 */
		HashMap<String, Double> hmTransferFactoryImportN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmountContract(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo, null,
						null, state));

		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmAllTransferFactoryImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		// /**
		// * 余料进口 (余料结转进口)（只根据合同号查询）
		// */
		// HashMap<String, Double> hmAllRemainImport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo, null,
		// null, state));
		/**
		 * 未转报关单数量
		 */
		HashMap<String, Double> notranCustomsNum = converListToHashTable(this.contractCavDao
				.findNoTransCustomsNum(new Boolean(true), emsNo));

		// /**
		// * 余料出口 (余料结转出口)
		// */
		// HashMap<String, Double> hmAllRemainForward =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo, null,
		// null, state));// new String[] { "0657","0258" }

		// /**
		// * 料件退换进口数
		// */
		// HashMap<String, Double> hmAllExchangeImport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.DIRECT_IMPORT,
		// new String[] { "0300", "0700" }, emsNo, null, null,
		// state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmAllExchangeExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		// /**
		// * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		// */
		// HashMap<String, Double> hmAllBackMaterialReturn =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
		// "0664" }, emsNo, null, null, state));
		// /**
		// * 退料出口量
		// */
		// HashMap<String, Double> hmAllBackMaterialExport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo, null,
		// null, state));

		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			ContractImg contractImg = (ContractImg) object[0];
			Integer innerMergeSeqNum = (Integer) object[1];

			if (contractImg != null && contractImg.getSeqNum() != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				ImpMaterialStat impMaterialStat = new ImpMaterialStat();
				impMaterialStat.setSerialNo(contractImg.getSeqNum().toString());
				impMaterialStat.setComplex(contractImg.getComplex());
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getDeclarePrice());
				/**
				 * 未转报关单数量
				 */
				impMaterialStat.setNoTranCustomsNum(notranCustomsNum
						.get(commSerialNo.toString()));
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(hmDirectImport.get(commSerialNo
						.toString()));
				/**
				 * 总边角料
				 */
				impMaterialStat
						.setAllTotalleftovermaterial(AllleftovermaterialUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString()));
				/**
				 * 边角料内销
				 */
				impMaterialStat.setLeftovermaterial(CommonUtils
						.getDoubleExceptNull(leftovermaterial.get(commSerialNo
								.toString()))
						+ CommonUtils
								.getDoubleExceptNull(leftovermaterialSpecial
										.get(commSerialNo.toString())));
				/**
				 * 边角料复出
				 */
				impMaterialStat
						.setLeftovermaterialExport(leftovermaterialExport
								.get(commSerialNo.toString()));
				/**
				 * 料件内销
				 * 
				 */
				impMaterialStat.setInternalAmount(internalAmount
						.get(commSerialNo.toString()));
				/**
				 * 边角料余量
				 */
				double leftovermaterialremain = (CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getAllTotalleftovermaterial())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterial()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterialExport()));
				impMaterialStat
						.setLeftovermaterialremain(leftovermaterialremain);
				/**
				 * 转厂进口量
				 */
				impMaterialStat
						.setTransferFactoryImport(hmTransferFactoryImport
								.get(commSerialNo.toString()));
				/**
				 * 料件复出金额
				 */
				impMaterialStat.setFuchuExport(hmFuchuExport.get(commSerialNo
						.toString()));
				/**
				 * 余料进口 (余料结转进口)（只根据合同查询）
				 */
				impMaterialStat.setRemainImport(hmRemainImport.get(commSerialNo
						.toString()));

				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(hmRemainForward
						.get(commSerialNo.toString()));

				/**
				 * 料件退换进口数
				 */
				impMaterialStat.setExchangeImport(hmExchangeImport
						.get(commSerialNo.toString()));

				/**
				 * 料件退换出口数
				 */
				impMaterialStat.setExchangeExport(hmExchangeExport
						.get(commSerialNo.toString()));

				/**
				 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
				 */
				impMaterialStat.setBackMaterialReturn(hmBackMaterialReturn
						.get(commSerialNo.toString()));
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(hmBackMaterialExport
						.get(commSerialNo.toString()));
				/**
				 * 料件进口数量
				 */
				Double allDirectImport = hmAllDirectImport.get(commSerialNo
						.toString());
				/**
				 * 料件进口数量（只根据合同查询）
				 */
				Double allDirectImportN = hmAllDirectImportN.get(commSerialNo
						.toString());
				/**
				 * 料件转厂数量（只根据合同查询）
				 */
				Double transferFactoryImportN = hmTransferFactoryImportN
						.get(commSerialNo.toString());
				/**
				 * 余料结转进口（只根据合同查询）
				 */
				Double remainImportN = hmRemainImport.get(commSerialNo
						.toString());
				/**
				 * 退料出口（只根据合同查询）
				 */
				Double BackMaterialExportN = hmBackMaterialExportN
						.get(commSerialNo.toString());

				/**
				 * 余料结转出口（只根据合同查询）
				 */
				Double RemainForwardN = hmRemainForwardN.get(commSerialNo
						.toString());

				Double allTransferFactoryImport = hmAllTransferFactoryImport
						.get(commSerialNo.toString());
				Double allExchangeExport = hmAllExchangeExport.get(commSerialNo
						.toString());

				Double DirectImportMoney = hmDirectImportMoney.get(commSerialNo
						.toString());
				Double RemainImportMoney = hmRemainImportMoney.get(commSerialNo
						.toString());
				Double TransferFactoryImportMoney = hmTransferFactoryImportMoney
						.get(commSerialNo.toString());
				Double ExchangeExportMoney = hmExchangeExportMoney
						.get(commSerialNo.toString());
				/**
				 * 本期总进口量=本期料件进口量+本期转厂进口量+本期料件退换进口量+本期余料进口-本期料件退换出口量(
				 * 料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						// + (impMaterialStat.getExchangeImport() == null ? 0.0
						// : impMaterialStat.getExchangeImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getExchangeExport()));
				/**
				 * 本期进口总金额
				 */
				impMaterialStat
						.setImpTotalMoney(CommonUtils
								.getDoubleExceptNull(DirectImportMoney)// 直接进口
								+ CommonUtils
										.getDoubleExceptNull(RemainImportMoney)// 余料结进
								+ CommonUtils
										.getDoubleExceptNull(TransferFactoryImportMoney)// 转厂
								- CommonUtils
										.getDoubleExceptNull(ExchangeExportMoney));// 退换
				/**
				 * 总进口量
				 */
				// impMaterialStat.setAllImpTotalAmont(CommonUtils
				// .getDoubleExceptNull(allDirectImport)
				// + CommonUtils.getDoubleExceptNull(allRemainImport)
				// + CommonUtils
				// .getDoubleExceptNull(allTransferFactoryImport)-
				// // + CommonUtils.getDoubleExceptNull(allExchangeImport)
				// - CommonUtils.getDoubleExceptNull(allExchangeExport));

				impMaterialStat.setAllImpTotalAmont(CommonUtils
						.getDoubleExceptNull(allDirectImportN)
						+ CommonUtils
								.getDoubleExceptNull(transferFactoryImportN)
						+ CommonUtils.getDoubleExceptNull(remainImportN));
				// - CommonUtils.getDoubleExceptNull(BackMaterialExportN)
				// - CommonUtils.getDoubleExceptNull(RemainForwardN));

				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(hmImgUse
						.get(commSerialNo == null ? "" : commSerialNo
								.toString()));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 成品使用金额
				 */
				impMaterialStat.setProductUseMoney((impMaterialStat
						.getProductUse() == null ? 0.0 : impMaterialStat
						.getProductUse())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				//
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getInternalAmount()));
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-内销数量
				 */
				impMaterialStat.setRemainAmount(remainAmount);
				// if (remainAmount > 0) {
				// // 余量=进口总量-成品使用量
				// impMaterialStat.setRemainAmount(remainAmount);
				// } else {
				// // 不足量=成品使用量-进口总量
				// impMaterialStat.setUllage(-remainAmount);
				// }
				/**
				 * 库存量=余量-余料结转出口
				 */
				impMaterialStat.setStockAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainForward()));
				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount((impMaterialStat
						.getContractAmount() == null ? 0.0 : impMaterialStat
						.getContractAmount())
						- (impMaterialStat.getImpTotalAmont() == null ? 0.0
								: impMaterialStat.getImpTotalAmont()));

				/**
				 * 比例=(可进口量/合同定量)*100
				 */
				impMaterialStat
						.setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount()) / (impMaterialStat
								.getContractAmount() == null ? 0.0
								: impMaterialStat.getContractAmount())) * 100);
				/**
				 * 余料金额=余量*单价
				 */
				impMaterialStat.setRemainMoney((impMaterialStat
						.getRemainAmount() == null ? 0.0 : impMaterialStat
						.getRemainAmount())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				impMaterialStat.setCountry(contractImg.getCountry());
				// impMaterialStat.setMaterialType(contractImg.getMaterialType());
				// impMaterialStat
				// .setSerialNo(contractImg.getCredenceNo() == null ? ""
				// : contractImg.getCredenceNo().toString());
				Double customsEnvelopCount = hmCustomsEnvelopTrans
						.get(commSerialNo.toString());
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				// System.out.println("申请总量:"+customsEnvelopCount+"----"+impMaterialStat.getTransferFactoryImport()+" 转厂数量");
				/**
				 * 可签关封=料件合同定量-已签关封数量
				 */
				impMaterialStat.setCommodityInfoRemain(CommonUtils
						.getDoubleExceptNull(contractImg.getAmount())
						- CommonUtils.getDoubleExceptNull(hmCommodityInfoRemain
								.get(commSerialNo.toString())));
				/**
				 * 可直接进口量=可进口量-关封余量
				 */
				impMaterialStat
						.setCanDirectImportAmount((impMaterialStat
								.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount())
								- (impMaterialStat.getCustomsEnvelopRemain() == null ? 0.0
										: impMaterialStat
												.getCustomsEnvelopRemain()));
				/**
				 * 报关单进口量==料件进口量+转厂进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));

				/**
				 * 预计边角料征税量=总边角料数量*（总进口数量/总出口成品使用量）
				 */
				if (impMaterialStat.getProductUse() != null
						&& impMaterialStat.getProductUse() != 0.0) {
					impMaterialStat.setEstimateOvermaterial(CommonUtils
							.getDoubleExceptNull(impMaterialStat
									.getAllTotalleftovermaterial())
							* (CommonUtils.getDoubleExceptNull(impMaterialStat
									.getImpTotalAmont()) / CommonUtils
									.getDoubleExceptNull(impMaterialStat
											.getProductUse())));
				} else {
					impMaterialStat.setEstimateOvermaterial(0.0);
				}

				/**
				 * 是否统计发票数量
				 */
				if (isCountInvoice) {
					System.out.println("this is test brgin");
					Double invoiceCount = this.contractCavDao
							.findCasInvoiceInfoNum(contract.getEmsNo(),
									commSerialNo);
					impMaterialStat.setStockAmount((impMaterialStat
							.getStockAmount() == null ? 0.0 : impMaterialStat
							.getStockAmount())
							+ invoiceCount);
					impMaterialStat.setRemainAmount((impMaterialStat
							.getRemainAmount() == null ? 0.0 : impMaterialStat
							.getRemainAmount()));
					// + invoiceCount);
					impMaterialStat.setInvoiceNum(invoiceCount);
				}

				/**
				 * 进口料件情况统计表单独查询一本手册，或者查询多本手册但是分开统计时，可以带出手册里面料件的说明栏位
				 */
				impMaterialStat.setNote(contractImg.getNote());

				impMaterialStat.setEmsNo(contract.getEmsNo());
				impMaterialStat.setCredenceNo(contractImg.getCredenceNo());// 凭证序号
				// System.out.println("innerMergeSeqNum=" + innerMergeSeqNum);
				impMaterialStat.setInnerMergeSeqNum(innerMergeSeqNum);
				lsResult.add(impMaterialStat);
			}
		}
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * 进口料件情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	public List<ImpMaterialStat> findImpMaterialStatForOne(Contract contract,
			Date beginDate, Date endDate, int state, boolean isCountInvoice) {
		List<ImpMaterialStat> lsResult = new ArrayList<ImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		// List<Integer> list = this.contractCavDao.findAllCommInfo(true, emsNo,
		// beginDate, endDate, state);
		List list = this.contractDao
				.findContractImgAndSeqNoByContractId(contract.getId());
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		HashMap<String, Double> AllleftovermaterialUse = new HashMap<String, Double>();
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate, state,
				AllleftovermaterialUse);
		/**
		 * 关封余量
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTableBySecond(this.contractStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, true, beginDate,
						endDate));

		/**
		 * 可签关封
		 */
		HashMap<String, Double> hmCommodityInfoRemain = converListToHashTableBySecond(this.contractStatDao
				.findCommodityInfoRemain(emsNo, true, beginDate, endDate));
		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmDirectImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 料件进口数量（根据合同号查询）
		 */
		HashMap<String, Double> hmDirectImportN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmountContract(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
						state));
		/**
		 * 进口数量金额=进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmDirectImportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmTransferFactoryImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 转厂进口量金额=转厂进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmTransferFactoryImportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 余料进口 (余料结转进口)
		 */
		HashMap<String, Double> hmRemainImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));// new String[] { "0657",
		// "0258" }
		/**
		 * 余料进口金额=余料进口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmRemainImportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 余料出口 (余料结转出口)
		 */
		HashMap<String, Double> hmRemainForward = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
						beginDate, endDate, state));// new String[] {
		// "0657","0258" }

		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmExchangeImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, beginDate,
						endDate, state));

		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmExchangeExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件退换出口数金额=料件退换出口数量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmExchangeExportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		 */
		HashMap<String, Double> hmBackMaterialReturn = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate, state));
		/**
		 * 料件复出出口数金额=料件退换出口数量 × 折算合同币制汇率 × 单价
		 */
		HashMap<String, Double> hmFuchuExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate, state));
		/**
		 * 退料出口量
		 */
		HashMap<String, Double> hmBackMaterialExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
						beginDate, endDate, state));

		/**
		 * 边角料内销海关批准内销--进出口报关单
		 */
		HashMap<String, Double> leftovermaterial = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0844", "0845" }, emsNo, beginDate, endDate,
						state));
		/**
		 * 边角料内销海关批准内销--特殊报关单
		 */
		HashMap<String, Double> leftovermaterialSpecial = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
						ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 本期边角料复出
		 */
		HashMap<String, Double> leftovermaterialExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.SPECIAL,
						ImpExpType.REMIAN_MATERIAL_BACK_PORT, new String[] {
								"0864", "0865" }, emsNo, beginDate, endDate,
						state));

		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmAllDirectImport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate, state));

		/**
		 * 料件内销
		 */
		HashMap<String, Double> internalAmount = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
								"0644", "0245" }, emsNo, beginDate, endDate,
						state));

		// /**
		// * 转厂进口量
		// */
		// HashMap<String, Double> hmAllTransferFactoryImport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo, beginDate,
		// endDate, state));

		/*
		 * 料件转厂进口量（根据合同号查询-开始日期和结束日期为空）
		 */
		HashMap<String, Double> hmAllTransferFactoryImportN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo, null,
						null, state));

		// /**
		// * 余料进口 (余料结转进口)
		// */
		// HashMap<String, Double> hmAllRemainImport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo, beginDate,
		// endDate, state));
		/**
		 * 余料进口（根据合同查询）
		 */
		HashMap<String, Double> hmAllRemainImportN = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmountContract(ImpExpFlag.IMPORT,
						ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo, null,
						null, state));
		/**
		 * 未转报关单数量
		 */
		HashMap<String, Double> notranCustomsNum = converListToHashTable(this.contractCavDao
				.findNoTransCustomsNum(new Boolean(true), emsNo));

		// /**
		// * 余料出口 (余料结转出口)
		// */
		// HashMap<String, Double> hmAllRemainForward =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo, null,
		// null, state));// new String[] { "0657","0258" }

		// /**
		// * 料件退换进口数
		// */
		// HashMap<String, Double> hmAllExchangeImport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.DIRECT_IMPORT,
		// new String[] { "0300", "0700" }, emsNo, null, null,
		// state));
		//
		// /**
		// * 料件退料出口数
		// */
		// HashMap<String, Double> hmAllExchangeExport =
		// converListToHashTable(this.contractCavDao
		// .findCommInfoTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo, null, null, state));

		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			ContractImg contractImg = (ContractImg) object[0];
			if (contractImg != null && contractImg.getSeqNum() != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				ImpMaterialStat impMaterialStat = new ImpMaterialStat();
				impMaterialStat.setSerialNo(contractImg.getSeqNum().toString());
				impMaterialStat.setComplex(contractImg.getComplex());
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getDeclarePrice());
				/**
				 * 未转报关单数量
				 */
				impMaterialStat.setNoTranCustomsNum(notranCustomsNum
						.get(commSerialNo.toString()));
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(hmDirectImport.get(commSerialNo
						.toString()));

				/**
				 * 转厂进口量
				 */
				impMaterialStat
						.setTransferFactoryImport(hmTransferFactoryImport
								.get(commSerialNo.toString()));

				/**
				 * 余料进口 (余料结转进口)
				 */
				impMaterialStat.setRemainImport(hmRemainImport.get(commSerialNo
						.toString()));

				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(hmRemainForward
						.get(commSerialNo.toString()));

				/**
				 * 料件退换进口数
				 */
				impMaterialStat.setExchangeImport(hmExchangeImport
						.get(commSerialNo.toString()));
				/**
				 * 料件内销
				 */
				impMaterialStat.setInternalAmount(internalAmount
						.get(commSerialNo.toString()));
				/**
				 * 料件复出出口数金额
				 */
				impMaterialStat.setFuchuExport(hmFuchuExport.get(commSerialNo
						.toString()));
				/**
				 * 料件退换出口数
				 */
				impMaterialStat.setExchangeExport(hmExchangeExport
						.get(commSerialNo.toString()));

				/**
				 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
				 */
				impMaterialStat.setBackMaterialReturn(hmBackMaterialReturn
						.get(commSerialNo.toString()));
				/**
				 * 总边角料
				 */
				impMaterialStat
						.setAllTotalleftovermaterial(AllleftovermaterialUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString()));
				/**
				 * 边角料内销
				 */
				impMaterialStat.setLeftovermaterial(CommonUtils
						.getDoubleExceptNull(leftovermaterial.get(commSerialNo
								.toString()))
						+ CommonUtils
								.getDoubleExceptNull(leftovermaterialSpecial
										.get(commSerialNo.toString())));
				/**
				 * 边角料复出
				 */
				impMaterialStat
						.setLeftovermaterialExport(leftovermaterialExport
								.get(commSerialNo.toString()) == null ? 0.0
								: leftovermaterialExport.get(commSerialNo
										.toString()));
				/**
				 * 边角料余量
				 */
				double leftovermaterialremain = (CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getAllTotalleftovermaterial())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterial()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getLeftovermaterialExport()));
				impMaterialStat
						.setLeftovermaterialremain(leftovermaterialremain);
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(hmBackMaterialExport
						.get(commSerialNo.toString()));

				/**
				 * 料件进口量（根据合同号查询）
				 */
				Double allDirectImport = hmDirectImportN.get(commSerialNo
						.toString());

				// Double allDirectImportnew =
				// hmAllDirectImportN.get(commSerialNo
				// .toString());
				/**
				 * 转厂进口量（根据合同号查询）
				 */
				Double allTransferFactoryImport = hmAllTransferFactoryImportN
						.get(commSerialNo.toString());
				/**
				 * 余料结转进口（根据合同号查询）
				 */
				Double allRemainImport = hmAllRemainImportN.get(commSerialNo
						.toString());
				// /**
				// * 退料出口量（根据合同号查询）
				// */
				// Double allExchangeExport =
				// hmAllExchangeExport.get(commSerialNo
				// .toString());
				// /**
				// * 余料结转出口量（根据合同号查询）
				// */
				// Double hmRemainForwardh =
				// hmAllRemainForward.get(commSerialNo.toString());

				Double DirectImportMoney = hmDirectImportMoney.get(commSerialNo
						.toString());
				Double RemainImportMoney = hmRemainImportMoney.get(commSerialNo
						.toString());
				Double TransferFactoryImportMoney = hmTransferFactoryImportMoney
						.get(commSerialNo.toString());
				Double ExchangeExportMoney = hmExchangeExportMoney
						.get(commSerialNo.toString());

				/**
				 * 本期进口总量=料件进口量+转厂进口量+余料进口-料件退换出口量(料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getExchangeExport()));
				/**
				 * 本期进口总金额
				 */
				impMaterialStat
						.setImpTotalMoney(CommonUtils
								.getDoubleExceptNull(DirectImportMoney)
								+ CommonUtils
										.getDoubleExceptNull(RemainImportMoney)
								+ CommonUtils
										.getDoubleExceptNull(TransferFactoryImportMoney)
								- CommonUtils
										.getDoubleExceptNull(ExchangeExportMoney));
				// - CommonUtils
				// .getDoubleExceptNull(FuchuExport));
				/**
				 * 总进口量＝合同的所有直接进口＋合同的所有余料进口＋合同的所有转厂进口（除去时间段的,与数据报核料件表保持一致）
				 */
				impMaterialStat.setAllImpTotalAmont(CommonUtils
						.getDoubleExceptNull(allDirectImport)
						+ CommonUtils.getDoubleExceptNull(allRemainImport)
						+ CommonUtils
								.getDoubleExceptNull(allTransferFactoryImport));
				// - CommonUtils.getDoubleExceptNull(hmRemainForwardh)
				// - CommonUtils.getDoubleExceptNull(allExchangeExport));
				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(hmImgUse
						.get(commSerialNo == null ? "" : commSerialNo
								.toString()));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 成品使用金额
				 */
				impMaterialStat.setProductUseMoney((impMaterialStat
						.getProductUse() == null ? 0.0 : impMaterialStat
						.getProductUse())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getInternalAmount()));
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-料件内销
				 */
				impMaterialStat.setRemainAmount(remainAmount);
				/**
				 * 库存量=余量-余料结转出口
				 */
				impMaterialStat.setStockAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainForward()));
				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount((impMaterialStat
						.getContractAmount() == null ? 0.0 : impMaterialStat
						.getContractAmount())
						- (impMaterialStat.getImpTotalAmont() == null ? 0.0
								: impMaterialStat.getImpTotalAmont()));

				/**
				 * 比例
				 */
				impMaterialStat
						.setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount()) / (impMaterialStat
								.getContractAmount() == null ? 0.0
								: impMaterialStat.getContractAmount())) * 100);
				/**
				 * 余料金额(折算汇率的金额)
				 */
				impMaterialStat.setRemainMoney((impMaterialStat
						.getRemainAmount() == null ? 0.0 : impMaterialStat
						.getRemainAmount())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));

				impMaterialStat.setCountry(contractImg.getCountry());
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString());
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				/**
				 * 可签关封=料件合同定量-已签关封数量
				 */
				impMaterialStat.setCommodityInfoRemain(CommonUtils
						.getDoubleExceptNull(contractImg.getAmount())
						- CommonUtils.getDoubleExceptNull(hmCommodityInfoRemain
								.get(commSerialNo.toString())));
				/**
				 * 可直接进口量=可进口量-关封余量
				 */
				impMaterialStat
						.setCanDirectImportAmount((impMaterialStat
								.getCanImportAmount() == null ? 0.0
								: impMaterialStat.getCanImportAmount())
								- (impMaterialStat.getCustomsEnvelopRemain() == null ? 0.0
										: impMaterialStat
												.getCustomsEnvelopRemain()));
				/**
				 * 报关单进口量==料件进口量+转厂进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));
				/**
				 * 预计边角料征税量=总边角料数量*（总进口数量/总出口成品使用量）
				 */
				if (impMaterialStat.getProductUse() == null
						|| impMaterialStat.getProductUse() == 0.0) {
					impMaterialStat.setEstimateOvermaterial(0.0);
				} else {
					impMaterialStat.setEstimateOvermaterial(CommonUtils
							.getDoubleExceptNull(impMaterialStat
									.getAllTotalleftovermaterial())
							* (CommonUtils.getDoubleExceptNull(impMaterialStat
									.getImpTotalAmont()) / CommonUtils
									.getDoubleExceptNull(impMaterialStat
											.getProductUse())));
				}
				/**
				 * 是否统计发票数量
				 */
				if (isCountInvoice) {
					System.out.println("this is end");
					Double invoiceCount = this.contractCavDao
							.findCasInvoiceInfoNum(contract.getEmsNo(),
									commSerialNo);
					System.out.println("invoiceCount=" + invoiceCount);
					impMaterialStat.setStockAmount((impMaterialStat
							.getStockAmount() == null ? 0.0 : impMaterialStat
							.getStockAmount())
							+ invoiceCount);
					System.out.println("stockAmount="
							+ (impMaterialStat.getStockAmount() == null ? 0.0
									: impMaterialStat.getStockAmount())
							+ invoiceCount);
					impMaterialStat.setRemainAmount((impMaterialStat
							.getRemainAmount() == null ? 0.0 : impMaterialStat
							.getRemainAmount()));

					impMaterialStat.setInvoiceNum(invoiceCount);
				}
				/**
				 * 进口料件情况统计表单独查询一本手册，或者查询多本手册但是分开统计时，可以带出手册里面料件的说明栏位
				 */
				impMaterialStat.setNote(contractImg.getNote());
				impMaterialStat.setEmsNo(contract.getEmsNo());
				impMaterialStat.setCredenceNo(contractImg.getCredenceNo());// 凭证序号
				lsResult.add(impMaterialStat);
			}
		}
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> converListToHashTable(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[1] == null ? 0 : Double.parseDouble(objs[1]
					.toString());
			// System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
	}

	/**
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> converListToHashTableBySecond(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[2] == null ? 0 : Double.parseDouble(objs[2]
					.toString());
			// System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
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
	private void calContractImgUse(Contract contract,
			HashMap<String, Double> hmImgUse, Date beginDate, Date endDate,
			int state, HashMap<String, Double> AllleftovermaterialUse) {
		List<ExpProductStat> lsProductStat = this.findExpProductStat(contract,
				beginDate, endDate, state);
		String declareState = "";
		for (ExpProductStat productStat : lsProductStat) {
			double exgAmount = CommonUtils.getDoubleExceptNull(productStat
					.getExpTotalAmont());
			// System.out.println("sssssssssss:" +productStat.getCommName()+
			// exgAmount);
			if ("5".equals(contract.getDeclareState())) {
				declareState = DeclareState.CHANGING_CANCEL;
			} else if (!"".equals(contract.getDeclareState())
					&& !"5".equals(contract.getDeclareState())) {
				declareState = DeclareState.CHANGING_EXE;
			}
			List<ContractBom> list = this.contractCavDao.findBomByExg(
					contract.getEmsNo(),
					Integer.valueOf(productStat.getSerialNo()), declareState);
			for (ContractBom contractBom : list) {
				if (contractBom.getContractImgSeqNum() == null) {
					// System.out.println("eeeeeeeeeeeeeeeeeeee: is null");
					continue;
				}
				// double imgAmount = exgAmount
				// * CommonUtils.getDoubleExceptNull(contractBom
				// .getUnitDosage());
				double imgAmount = exgAmount
						* CommonUtils
								.getDoubleExceptNull(CommonUtils
										.getDoubleExceptNull(contractBom
												.getUnitWaste())
										/ (1 - CommonUtils
												.getDoubleExceptNull(contractBom
														.getWaste()) / 100.0));
				double hasAmount = CommonUtils
						.getDoubleExceptNull(hmImgUse.get(contractBom
								.getContractImgSeqNum() == null ? ""
								: contractBom.getContractImgSeqNum().toString()));
				double haswear = CommonUtils
						.getDoubleExceptNull(AllleftovermaterialUse.get(contractBom
								.getContractImgSeqNum() == null ? ""
								: contractBom.getContractImgSeqNum().toString()));
				hmImgUse.put((contractBom.getContractImgSeqNum() == null ? ""
						: contractBom.getContractImgSeqNum().toString()),
						imgAmount + hasAmount);
				AllleftovermaterialUse
						.put((contractBom.getContractImgSeqNum() == null ? ""
								: contractBom.getContractImgSeqNum().toString()),
								imgAmount
										* (CommonUtils
												.getDoubleExceptNull(contractBom
														.getWaste()) / 100.0)
										+ haswear);
			}
		}
	}

	/**
	 * 计算合同料件剩余量
	 * 
	 * @param contract
	 *            合同
	 * @param allleftovermaterialUse
	 *            key 合同单耗对应料件序号 value 数量
	 * @param beginDate
	 *            开始有效日期
	 * @param endDate
	 *            结束有效日期
	 * @param state
	 *            合同状态
	 */
	private void calContractleftovermaterialUse(Contract contract,
			HashMap<String, Double> allleftovermaterialUse, Date beginDate,
			Date endDate, int state) {
		List<ExpProductStat> lsProductStat = this.findExpProductStat(contract,
				beginDate, endDate, state);
		for (ExpProductStat productStat : lsProductStat) {
			double exgAmount = CommonUtils.getDoubleExceptNull(productStat
					.getExpTotalAmont());
			List<ContractBom> list = this.contractCavDao.findBomByExg(
					contract.getEmsNo(),
					Integer.valueOf(productStat.getSerialNo()),
					DeclareState.PROCESS_EXE);
			for (ContractBom contractBom : list) {
				if (contractBom.getContractImgSeqNum() == null) {
					continue;
				}
				double imgAmount = exgAmount
						* CommonUtils
								.getDoubleExceptNull(CommonUtils
										.getDoubleExceptNull(contractBom
												.getUnitWaste())
										/ (1 - CommonUtils
												.getDoubleExceptNull(contractBom
														.getWaste()) / 100.0));
				double hasAmount = CommonUtils
						.getDoubleExceptNull(allleftovermaterialUse
								.get(contractBom.getContractImgSeqNum()
										.toString()));
				allleftovermaterialUse.put(
						contractBom.getContractImgSeqNum().toString(),
						imgAmount
								* (CommonUtils.getDoubleExceptNull(contractBom
										.getWaste()) / 100.0) + hasAmount);
			}
		}
	}

	/**
	 * 计算出口成品总值
	 * 
	 * @param list
	 *            是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 * @return List 是ExpProductStatResult型，存放统计报表中的出口成品报关情况表－－统计数据资料
	 */
	public ExpProductStatResult expProductStat(List<ExpProductStat> list) {
		ExpProductStatResult statResult = new ExpProductStatResult();
		double expTotalMoney = 0.0;
		double expContractMoney = 0.0;
		for (ExpProductStat expProductStat : list) {
			expContractMoney += (expProductStat.getContractAmount() == null ? 0.0
					: expProductStat.getContractAmount())
					* (expProductStat.getUnitPrice() == null ? 0.0
							: expProductStat.getUnitPrice());
			// 总金额＝总出口量×单价×折算合同汇率，这里的getExpTotalMoney已经是折算后的总金额
			expTotalMoney += (expProductStat.getExpTotalMoney() == null ? 0.0
					: expProductStat.getExpTotalMoney());
			// expTotalMoney += (expProductStat.getExpTotalAmont() == null ? 0.0
			// : expProductStat.getExpTotalAmont())
			// * (expProductStat.getUnitPrice() == null ? 0.0
			// : expProductStat.getUnitPrice());
		}
		statResult.setExpTotalMoney(CommonUtils.getDoubleByDigit(expTotalMoney,
				3));
		statResult.setContractMoney(CommonUtils.getDoubleByDigit(
				expContractMoney, 3));
		statResult.setScale(CommonUtils.getDoubleByDigit(
				expContractMoney == 0 ? 0
						: (expTotalMoney / expContractMoney) * 100, 3));
		return statResult;
	}

	/**
	 * 出口成品情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	public List<ExpProductStat> findExpProductStatByContracts(List contracts,
			Date beginDate, Date endDate, int state, boolean isDetachCompute) {

		if (isDetachCompute) {
			List<ExpProductStat> lsResult = new ArrayList<ExpProductStat>();
			for (int i = 0; i < contracts.size(); i++) {
				Contract contract = (Contract) contracts.get(i);
				List<ExpProductStat> data = this.findExpProductStat(contract,
						beginDate, endDate, state);
				for (ExpProductStat eps : data) {
					eps.setEmsNo(contract.getEmsNo());
					eps.setImpContractNo(contract.getImpContractNo());
					lsResult.add(eps);
				}
			}
			return lsResult;
		} else {
			if (contracts.size() == 1) {
				Contract contract = (Contract) contracts.get(0);
				List<ExpProductStat> data = this.findExpProductStat(contract,
						beginDate, endDate, state);
				for (ExpProductStat eps : data) {
					eps.setEmsNo("");
				}
				return data;

			} else {
				Map map = new HashMap();
				for (int i = 0; i < contracts.size(); i++) {
					Contract contract = (Contract) contracts.get(i);
					List<ExpProductStat> data = this.findExpProductStat(
							contract, beginDate, endDate, state);
					for (ExpProductStat newData : data) {
						newData.setEmsNo("");
						String key = (newData.getComplex().getCode().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						// newData.setCredenceNo(null);
						if (map.get(key) == null) {
							if (contracts.size() > 1) {
								newData.setSerialNo(null);
							}
							map.put(key, newData);
						} else {
							ExpProductStat oldData = (ExpProductStat) map
									.get(key);
							/**
							 * 未转报关单数量
							 */
							oldData.setNoTranCustomsNum(checkNullForDouble(oldData
									.getNoTranCustomsNum())
									+ checkNullForDouble(newData
											.getNoTranCustomsNum()));
							/**
							 * 总出口量
							 */
							oldData.setAllExpTotalAmont(checkNullForDouble(oldData
									.getAllExpTotalAmont())
									+ checkNullForDouble(newData
											.getAllExpTotalAmont()));
							/**
							 * 报关单出口量
							 */
							oldData.setExpCDAmount(checkNullForDouble(oldData
									.getExpCDAmount())
									+ checkNullForDouble(newData
											.getExpCDAmount()));
							/**
							 * 关封余量
							 */
							oldData.setCustomsEnvelopRemain(checkNullForDouble(oldData
									.getCustomsEnvelopRemain())
									+ checkNullForDouble(newData
											.getCustomsEnvelopRemain()));
							/**
							 * 可直接出口量
							 */
							oldData.setCanDirectExportAmount(checkNullForDouble(oldData
									.getCanDirectExportAmount())
									+ checkNullForDouble(newData
											.getCanDirectExportAmount()));
							/**
							 * 合同定量
							 */
							oldData.setContractAmount(checkNullForDouble(oldData
									.getContractAmount())
									+ checkNullForDouble(newData
											.getContractAmount()));
							/**
							 * 总出口量
							 */
							oldData.setExpTotalAmont(checkNullForDouble(oldData
									.getExpTotalAmont())
									+ checkNullForDouble(newData
											.getExpTotalAmont()));
							/**
							 * 本期总出口金额
							 */
							oldData.setExpTotalMoney(checkNullForDouble(oldData
									.getExpTotalMoney())
									+ checkNullForDouble(newData
											.getExpTotalMoney()));
							/**
							 * 成品出口量
							 */
							oldData.setDirectExport(checkNullForDouble(oldData
									.getDirectExport())
									+ checkNullForDouble(newData
											.getDirectExport()));
							/**
							 * 转厂出口
							 */
							oldData.setTransferFactoryExport(checkNullForDouble(oldData
									.getTransferFactoryExport())
									+ checkNullForDouble(newData
											.getTransferFactoryExport()));
							/**
							 * 可出口量
							 */
							oldData.setCanExportAmount(checkNullForDouble(oldData
									.getCanExportAmount())
									+ checkNullForDouble(newData
											.getCanExportAmount()));
							/**
							 * 超量
							 */
							oldData.setOverAmount(checkNullForDouble(oldData
									.getOverAmount())
									+ checkNullForDouble(newData
											.getOverAmount()));
							/**
							 * 退厂返工数
							 */
							oldData.setBackFactoryRework(checkNullForDouble(oldData
									.getBackFactoryRework())
									+ checkNullForDouble(newData
											.getBackFactoryRework()));
							/**
							 * 返工复出数
							 */
							oldData.setReworkExport(checkNullForDouble(oldData
									.getReworkExport())
									+ checkNullForDouble(newData
											.getReworkExport()));
							/**
							 * 加工费总价
							 */
							oldData.setProcessTotalPrice(checkNullForDouble(oldData
									.getProcessTotalPrice())
									+ checkNullForDouble(newData
											.getProcessTotalPrice()));
							oldData.setUnitPrice(null);
						}
					}
				}
				// return new ArrayList(map.values());
				List lsResult = new ArrayList(map.values());
				Collections.sort(lsResult);
				return lsResult;
			}
		}
	}

	/**
	 * 出口成品情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	public List<ExpProductStat> findExpProductStat(Contract contract,
			Date beginDate, Date endDate, int state) {
		List<ExpProductStat> lsResult = new ArrayList<ExpProductStat>();
		String emsNo = contract.getEmsNo();
		// List list = this.contractCavDao.findAllCommInfo(false, emsNo,
		// beginDate, endDate, state);
		List list = this.contractDao.findContractExgSeqNoByParentId(contract
				.getId());
		/**
		 * 关封余量－深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTableBySecond(this.contractStatDao
				.findFptAppItemCount(emsNo, false, beginDate, endDate));
		/**
		 * 关封余量-转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTableBySecond(this.contractStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, false, beginDate,
						endDate));
		List nset = new ArrayList(hmCustomsEnvelopTrans.keySet());
		for (int i = 0; i < nset.size(); i++) {
			String key = nset.get(i) == null ? "" : nset.get(i).toString();
			if (hmCustomsEnvelop.get(key) == null) {
				hmCustomsEnvelop.put(key == null ? "" : key.toString(),
						hmCustomsEnvelopTrans.get(key));
			} else {
				Double ss = hmCustomsEnvelop.get(key);
				Double dd = hmCustomsEnvelopTrans.get(key);
				hmCustomsEnvelop.put(key, (ss + dd));
			}
		}
		/**
		 * 成品出口数量
		 */
		HashMap<String, Double> hmDirectExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 出口数量金额=出口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmDirectExportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 转厂出口数量
		 */
		HashMap<String, Double> hmTransferFactoryExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 转厂出口量金额=转厂出口量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmTransferFactoryExportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
						beginDate, endDate, state));
		/**
		 * 退厂返工数量
		 */
		HashMap<String, Double> hmBackFactoryRework = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 退厂返工金额=退厂返工数量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmBackFactoryReworkMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 返工复出数量
		 */
		HashMap<String, Double> hmReworkExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 返工复出金额=返工复量×折算合同币制汇率×单价
		 */
		HashMap<String, Double> hmReworkExportMoney = converListToHashTable(this.contractCavDao
				.findCommInfoTotalMoney(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, beginDate,
						endDate, state));
		/**
		 * 成品出口数量
		 */
		HashMap<String, Double> hmAllDirectExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, null, null,
						state));
		/**
		 * 转厂出口数量
		 */
		HashMap<String, Double> hmAllTransferFactoryExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo, null,
						null, state));
		/**
		 * 退厂返工数量
		 */
		HashMap<String, Double> hmAllBackFactoryRework = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, null,
						null, state));
		/**
		 * 返工复出数量
		 */
		HashMap<String, Double> hmAllReworkExport = converListToHashTable(this.contractCavDao
				.findCommInfoTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, null, null,
						state));

		/**
		 * 未转报关单数量
		 */
		HashMap<String, Double> notranCustomsNum = converListToHashTable(this.contractCavDao
				.findNoTransCustomsNum(new Boolean(false), emsNo));

		for (int i = 0; i < list.size(); i++) {
			Object[] object = (Object[]) list.get(i);
			ContractExg contractExg = (ContractExg) object[0];
			Integer innerMergeSeqNum = (Integer) object[1];
			if (contractExg != null && contractExg.getSeqNum() != null) {
				Integer commSerialNo = contractExg.getSeqNum();
				ExpProductStat expProductStat = new ExpProductStat();
				expProductStat.setSerialNo(contractExg.getSeqNum().toString());
				expProductStat.setComplex(contractExg.getComplex());
				expProductStat.setCommName(contractExg.getName());
				expProductStat.setCommSpec(contractExg.getSpec());
				expProductStat.setUnit(contractExg.getUnit());
				expProductStat.setUnitPrice(contractExg.getUnitPrice());
				/**
				 * 未转报关单数量
				 */
				expProductStat.setNoTranCustomsNum(notranCustomsNum
						.get(commSerialNo.toString()));

				/**
				 * 合同定量
				 */
				expProductStat.setContractAmount(contractExg.getExportAmount());
				/**
				 * 成品出口
				 */
				expProductStat.setDirectExport(hmDirectExport.get(commSerialNo
						.toString()));
				/**
				 * 转厂出口
				 */
				expProductStat.setTransferFactoryExport(hmTransferFactoryExport
						.get(commSerialNo.toString()));
				/**
				 * 退厂返工数
				 */
				expProductStat.setBackFactoryRework(hmBackFactoryRework
						.get(commSerialNo.toString()));
				/**
				 * 返工复出数
				 */
				expProductStat.setReworkExport(hmReworkExport.get(commSerialNo
						.toString()));
				Double allDirectExport = hmAllDirectExport.get(commSerialNo
						.toString());
				Double allTransferFactoryExport = hmAllTransferFactoryExport
						.get(commSerialNo.toString());
				Double allBackFactoryRework = hmAllBackFactoryRework
						.get(commSerialNo.toString());
				Double allReworkExport = hmAllReworkExport.get(commSerialNo
						.toString());

				Double DirectExportMoney = hmDirectExportMoney.get(commSerialNo
						.toString());
				Double TransferFactoryExportMoney = hmTransferFactoryExportMoney
						.get(commSerialNo.toString());
				Double BackFactoryReworkMoney = hmBackFactoryReworkMoney
						.get(commSerialNo.toString());
				Double ReworkExportMoney = hmReworkExportMoney.get(commSerialNo
						.toString());
				CompanyOther other = CommonUtils.getOther();

				/**
				 * 本期总出口量=报关单直接出口量+转厂出口-退厂返工量+返工复出数
				 */
				expProductStat
						.setExpTotalAmont((expProductStat.getDirectExport() == null ? 0.0
								: expProductStat.getDirectExport())
								+ (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport())
								- (expProductStat.getBackFactoryRework() == null ? 0.0
										: expProductStat.getBackFactoryRework())
								+ (expProductStat.getReworkExport() == null ? 0.0
										: expProductStat.getReworkExport()));
				/**
				 * 本期总出口金额
				 */
				expProductStat
						.setExpTotalMoney(CommonUtils
								.getDoubleExceptNull(DirectExportMoney)
								+ CommonUtils
										.getDoubleExceptNull(TransferFactoryExportMoney)
								- CommonUtils
										.getDoubleExceptNull(BackFactoryReworkMoney)
								+ CommonUtils
										.getDoubleExceptNull(ReworkExportMoney));

				/**
				 * 总出口量
				 */
				expProductStat.setAllExpTotalAmont(CommonUtils
						.getDoubleExceptNull(allDirectExport)
						+ CommonUtils
								.getDoubleExceptNull(allTransferFactoryExport)
						- CommonUtils.getDoubleExceptNull(allBackFactoryRework)
						+ CommonUtils.getDoubleExceptNull(allReworkExport));
				// 比例=(总出口量/合同定量)*100
				if (expProductStat.getContractAmount() != null
						&& expProductStat.getContractAmount() != 0) {
					expProductStat
							.setScale(((expProductStat.getExpTotalAmont() == null ? 0.0
									: expProductStat.getExpTotalAmont()) / expProductStat
									.getContractAmount()) * 100);
				}
				// double canExportAmount =
				/**
				 * 可出口量 = 合同定量-本期总出口量
				 */
				expProductStat.setCanExportAmount((expProductStat
						.getContractAmount() == null ? 0.0 : expProductStat
						.getContractAmount())
						- (expProductStat.getExpTotalAmont() == null ? 0.0
								: expProductStat.getExpTotalAmont()));
				// if (canExportAmount >= 0) {
				// /**
				// * 可出口量 = 合同定量-报关单出口量
				// */
				// expProductStat.setCanExportAmount(canExportAmount);
				// } else {
				// /**
				// * 超量
				// */
				// expProductStat.setOverAmount(-canExportAmount);
				// }
				expProductStat.setProcessUnitPrice(contractExg
						.getProcessUnitPrice());
				expProductStat.setProcessTotalPrice(CommonUtils
						.getDoubleExceptNull(expProductStat.getExpTotalAmont())
						* CommonUtils.getDoubleExceptNull(expProductStat
								.getProcessUnitPrice()));
				expProductStat.setLegalAmount(contractExg.getLegalAmount());
				expProductStat.setLegalUnit(contractExg.getComplex()
						.getFirstUnit());
				expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
				expProductStat.setUnitGrossWeight(contractExg
						.getUnitGrossWeight());
				expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
				expProductStat.setLevyMode(contractExg.getLevyMode());
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString());
				/**
				 * 关封余量=关封总量-转厂出口量
				 */
				expProductStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport()));

				/**
				 * 报关单出口量=成品出口量+转厂出口量
				 */
				expProductStat.setExpCDAmount(CommonUtils
						.getDoubleExceptNull(expProductStat.getDirectExport())
						+ CommonUtils.getDoubleExceptNull(expProductStat
								.getTransferFactoryExport()));

				/**
				 * 可直接出口量=合同定量-报关单出口量-关封余量
				 */
				expProductStat
						.setCanDirectExportAmount((expProductStat
								.getContractAmount() == null ? 0.0
								: expProductStat.getContractAmount())
								- (expProductStat.getExpCDAmount() == null ? 0.0
										: expProductStat.getExpCDAmount())
								- (expProductStat.getCustomsEnvelopRemain() == null ? 0.0
										: expProductStat
												.getCustomsEnvelopRemain()));

				expProductStat.setEmsNo(contract.getEmsNo());
				expProductStat.setCredenceNo(contractExg.getCredenceNo());
				expProductStat.setInnerMergeSeqNum(innerMergeSeqNum);
				lsResult.add(expProductStat);
			}
		}
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * 出口成品情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	public List<ExpProductStat> findExpProductStat(Contract contract,
			Date beginDate, Date endDate) {
		return this.findExpProductStat(contract, beginDate, endDate,
				CustomsDeclarationState.ALL);
	}

	/**
	 * 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
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
	public List findImpExpCommInfoList(boolean isMaterial, Integer seqNum,
			String code, String name, String customer, String impExpType,
			Date beginDate, Date endDate, List contract, int state,
			int impExpFlag) {
		List<ImpExpCustomsDeclarationCommInfo> lsResult = new ArrayList<ImpExpCustomsDeclarationCommInfo>();

		List<Contract> contracts = contract;

		List listPROCESS_EXE = new ArrayList();// 正在执行
		List listCHANGING_CANCEL = new ArrayList();// 已经核销
		// 判断是否正在执行或已经核销..再掉用不同的DAO取出合同报关单料件
		for (Contract c : contracts) {
			if (c.getDeclareState().equals("3")) {
				listPROCESS_EXE.add(c);

			} else if (c.getDeclareState().equals("5")) {
				listCHANGING_CANCEL.add(c);
			}
		}
		// 正在执行
		List list1 = new ArrayList();
		if (listPROCESS_EXE.size() != 0) {
			list1 = this.contractStatDao.findImpExpCommInfoListContract(
					isMaterial, seqNum, code, name, customer, impExpType,
					beginDate, endDate, listPROCESS_EXE, state, impExpFlag);
		}
		// 已经核销
		List list2 = new ArrayList();
		if (listCHANGING_CANCEL.size() != 0) {
			list2 = this.contractStatDao
					.findImpExpCommInfoListContractCHANGING(isMaterial, seqNum,
							code, name, customer, impExpType, beginDate,
							endDate, listCHANGING_CANCEL, state, impExpFlag);
		}

		list1.addAll(list2);
		// // -------------为了把相同报关单号的放在一起
		Map<String, Double> map = new HashMap<String, Double>();
		for (int i = 0; i < list1.size(); i++) {
			ImpExpCustomsDeclarationCommInfo tempCommInfo = new ImpExpCustomsDeclarationCommInfo();
			Object[] objs = (Object[]) list1.get(i);
			BcsCustomsDeclarationCommInfo commInfo = (BcsCustomsDeclarationCommInfo) objs[0];
			ContractExg exg = null;
			ContractImg img = null;
			if (!isMaterial) {
				exg = (ContractExg) objs[1];
				tempCommInfo
						.setProcessUnitPrice(exg.getProcessUnitPrice() == null ? 0.0
								: exg.getProcessUnitPrice());
				tempCommInfo
						.setContractUnitPrice(exg.getUnitPrice() == null ? 0.0
								: exg.getUnitPrice());
			} else {
				img = (ContractImg) objs[1];
				tempCommInfo
						.setContractUnitPrice(img.getDeclarePrice() == null ? 0.0
								: img.getDeclarePrice());
			}

			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addupAmount = map.get(tempCommInfo.getCommSerialNo()
					.toString()) == null ? 0.0 : Double.parseDouble(map.get(
					tempCommInfo.getCommSerialNo().toString()).toString());
			Integer type = tempCommInfo.getBaseCustomsDeclaration()
					.getImpExpType();
			if (isMaterial) {
				if (type == ImpExpType.DIRECT_IMPORT
						|| type == ImpExpType.TRANSFER_FACTORY_IMPORT
						|| type == ImpExpType.REMAIN_FORWARD_IMPORT) {
					addupAmount += (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
				} else {
					addupAmount -= (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
				}
			} else {
				if (type == ImpExpType.DIRECT_EXPORT
						|| type == ImpExpType.TRANSFER_FACTORY_EXPORT
						|| type == ImpExpType.REWORK_EXPORT) {
					addupAmount += (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
				} else {
					addupAmount -= (tempCommInfo.getCommAmount() == null ? 0.0
							: tempCommInfo.getCommAmount());
				}
			}
			tempCommInfo.setCommAddUpAmount(addupAmount);
			tempCommInfo.getBaseCustomsDeclaration().setCurrency(
					commInfo.getBaseCustomsDeclaration().getCurrency());
			map.put(tempCommInfo.getCommSerialNo().toString(), addupAmount);
			lsResult.add(tempCommInfo);
		}
		return lsResult;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			Contract contract, int state) {
		List<TempBcsCustomsDeclarCommInfo> lsResult = new ArrayList<TempBcsCustomsDeclarCommInfo>();
		String emsNo = (contract == null ? "" : contract.getEmsNo());
		List list = this.contractStatDao.findCustomsDeclarationCommInfo(
				isMaterial, emsNo, state);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempBcsCustomsDeclarCommInfo commInfo = new TempBcsCustomsDeclarCommInfo();
			commInfo.setSeqNum((Integer) objs[0]);
			if (objs[1] != null) {
				commInfo.setCode(objs[1].toString());
			}
			if (objs[2] != null) {
				commInfo.setName(objs[2].toString());
			}
			if (objs[3] != null) {
				commInfo.setSpec(objs[3].toString());
			}
			if (objs[4] != null) {
				commInfo.setUnitName(objs[4].toString());
			}
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			List lsContract, int state) {
		List<TempBcsCustomsDeclarCommInfo> lsResult = new ArrayList<TempBcsCustomsDeclarCommInfo>();
		List list = this.contractStatDao.findCustomsDeclarationCommInfo(
				isMaterial, lsContract, state);

		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempBcsCustomsDeclarCommInfo commInfo = new TempBcsCustomsDeclarCommInfo();
			commInfo.setSeqNum((Integer) objs[0]);
			if (objs[1] != null) {
				commInfo.setCode(objs[1].toString());
			}
			if (objs[2] != null) {
				commInfo.setName(objs[2].toString());
			}
			if (objs[3] != null) {
				commInfo.setSpec(objs[3].toString());
			}
			if (objs[4] != null) {
				commInfo.setUnitName(objs[4].toString());
			}
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * 
	 * @return List 是TempBcsCustomsDeclarCommInfo型，
	 */
	public List findSpecialCustomsDeclarationCommInfo(int impExpFlag, int state) {
		List<TempBcsCustomsDeclarCommInfo> lsResult = new ArrayList<TempBcsCustomsDeclarCommInfo>();

		List list = this.contractStatDao.findSpecialCustomsDeclarationCommInfo(
				impExpFlag, state);

		// // -------------为了把相同报关单号的放在一起
		// Map hmap = new HashMap();
		// for (int i = 0; i < list.size(); i++) {
		// Object[] objs = (Object[]) list.get(i);
		// BcsCustomsDeclarationCommInfo info = (BcsCustomsDeclarationCommInfo)
		// objs[0];
		// String key = info.getBaseCustomsDeclaration()
		// .getCustomsDeclarationCode();
		// if (hmap.get(key) == null) {
		// List alist = new ArrayList();
		// alist.add(list.get(i));
		// hmap.put(key, alist);
		// } else {
		// List blist = (List) hmap.get(key);
		// blist.add(list.get(i));
		// }
		// }
		// list.clear();
		// List tlist = new ArrayList(hmap.values());
		// for (int i = 0; i < tlist.size(); i++) {
		// List olist = (List) tlist.get(i);
		// for (int j = 0; j < olist.size(); j++) {
		// list.add(olist.get(j));
		// }
		// }
		// // -------------为了把相同报关单号的放在一起
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempBcsCustomsDeclarCommInfo commInfo = new TempBcsCustomsDeclarCommInfo();
			commInfo.setSeqNum((Integer) objs[0]);
			if (objs[1] != null) {
				commInfo.setCode(objs[1].toString());
			}
			if (objs[2] != null) {
				commInfo.setName(objs[2].toString());
			}
			if (objs[3] != null) {
				commInfo.setSpec(objs[3].toString());
			}
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport
	 *            进口判断，true为进口
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport,
			Contract contract, int state) {
		List lsResult = new ArrayList();
		String contractNo = null;
		String emsNo = null;
		if (contract != null) {
			contractNo = contract.getImpContractNo();
			emsNo = contract.getEmsNo();
		}
		return this.contractStatDao.findCustomsDeclarationCustomer(isImport,
				emsNo, state);
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport
	 *            进口判断，true为进口
	 * @param lsContract
	 *            是Contract型，合同备案表头
	 * @param state
	 *            生效类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport,
			List lsContract, int state) {
		return this.contractStatDao.findCustomsDeclarationCustomer(isImport,
				lsContract, state);
	}

	/**
	 * 料件，成品执行进度明细
	 * 
	 * @param isImport
	 *            进口判断，true为进口
	 * @param impExpType
	 *            进出口类型
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件时是ImpScheduleDetail型，存放统计报表中的料件执行进度明细资料
	 *         成品时是ExpScheduleDetail型，存放统计报表中的成品执行进度明细资料
	 */
	public List findImpExpScheduleDetail(boolean isImport, String impExpType,
			Contract contract, Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		List list = this.contractStatDao.findCustomsDeclarationCommInfo(
				isImport, impExpType, contract.getEmsNo(), beginDate, endDate);
		if (isImport) {
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				ImpScheduleDetail detail = new ImpScheduleDetail();
				detail.setCommCode(commInfo.getComplex().getCode());
				detail.setCommName(commInfo.getCommName());
				detail.setCommSpec(commInfo.getCommSpec());
				ContractImg contractImg = this.contractCavDao
						.findImgByCommSerialNo(contract.getEmsNo(), commInfo
								.getCommSerialNo().toString());
				if (contractImg != null) {
					detail.setContractAmount(contractImg.getAmount());
				}
				detail.setDeclarationDate(commInfo.getBaseCustomsDeclaration()
						.getDeclarationDate());
				detail.setConveyance(commInfo.getBaseCustomsDeclaration()
						.getConveyance());
				detail.setUnit(commInfo.getUnit());
				int type = commInfo.getBaseCustomsDeclaration().getImpExpType();
				switch (type) {
				case ImpExpType.DIRECT_IMPORT:
					detail.setDirectImport(commInfo.getCommAmount());
					break;
				case ImpExpType.TRANSFER_FACTORY_IMPORT:
					detail.setTransferFactoryImport(commInfo.getCommAmount());
					break;
				case ImpExpType.BACK_MATERIEL_EXPORT:
					detail.setBackMaterialExport(commInfo.getCommAmount());
					break;
				}
				detail.setCustomsDeclarationCode(commInfo
						.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode());
				detail.setCustomer(commInfo.getBaseCustomsDeclaration()
						.getCustomer());
				detail.setSecondAmount(commInfo.getSecondAmount());
				detail.setSecondLegalUnit(commInfo.getSecondLegalUnit());
				detail.setUnitWeight(commInfo.getUnitWeight());
				lsResult.add(detail);
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				ExpScheduleDetail detail = new ExpScheduleDetail();
				detail.setCommCode(commInfo.getComplex().getCode());
				detail.setCommName(commInfo.getCommName());
				detail.setCommSpec(commInfo.getCommSpec());
				ContractImg contractImg = this.contractCavDao
						.findImgByCommSerialNo(contract.getEmsNo(), commInfo
								.getCommSerialNo().toString());
				if (contractImg != null) {
					detail.setContractAmount(contractImg.getAmount());
				}
				detail.setDeclarationDate(commInfo.getBaseCustomsDeclaration()
						.getDeclarationDate());
				detail.setConveyance(commInfo.getBaseCustomsDeclaration()
						.getConveyance());
				detail.setUnit(commInfo.getUnit());
				int type = commInfo.getBaseCustomsDeclaration().getImpExpType();
				switch (type) {
				case ImpExpType.DIRECT_EXPORT:
					detail.setDirectExport(commInfo.getCommAmount());
					break;
				case ImpExpType.TRANSFER_FACTORY_EXPORT:
					detail.setTransferFactoryExport(commInfo.getCommAmount());
					break;
				case ImpExpType.BACK_FACTORY_REWORK:
					detail.setBackFactoryRework(commInfo.getCommAmount());
					break;
				case ImpExpType.REWORK_EXPORT:
					detail.setReworkExport(commInfo.getCommAmount());
					break;
				}
				detail.setCustomsDeclarationCode(commInfo
						.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode());
				detail.setCustomer(commInfo.getBaseCustomsDeclaration()
						.getCustomer());
				detail.setSecondAmount(commInfo.getSecondAmount());
				detail.setSecondLegalUnit(commInfo.getSecondLegalUnit());
				detail.setUnitWeight(commInfo.getUnitWeight());
				lsResult.add(detail);
			}
		}
		return lsResult;
	}

	/**
	 * 核销单登记表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是CancelAfterVerification型，存放核销单登记表资料
	 */
	public List findCancelAfterVerificationList(List contracts, Date beginDate,
			Date endDate) {
		List<CancelAfterVerification> lsResult = new ArrayList<CancelAfterVerification>();
		HashMap<String, CancelAfterVerification> hm = new HashMap<String, CancelAfterVerification>();
		for (int m = 0; m < contracts.size(); m++) {
			Contract contract = (Contract) contracts.get(m);
			List list = this.contractStatDao.findCancelAfterVerificationList(
					contract.getEmsNo(), beginDate, endDate);
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				if (commInfo.getBaseCustomsDeclaration().getAuthorizeFile() == null
						|| "".equals(commInfo.getBaseCustomsDeclaration()
								.getAuthorizeFile())) {
					continue;
				}
				List lsExg = this.contractDao.findContractExgBySeqNum(contract
						.getEmsNo(), commInfo.getSerialNumber() == null ? ""
						: commInfo.getSerialNumber().toString());
				if (lsExg.size() <= 0) {
					continue;
				}
				ContractExg exg = (ContractExg) lsExg.get(0);
				CancelAfterVerification cav = null;
				if (hm.get(commInfo.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode()) == null) {
					cav = new CancelAfterVerification();
					cav.setCustomsDeclarationNo(commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
					cav.setCavNo(commInfo.getBaseCustomsDeclaration()
							.getAuthorizeFile());
				} else {
					cav = (CancelAfterVerification) hm.get(commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
				}
				cav.setTotalPrice(CommonUtils.getDoubleExceptNull(cav
						.getTotalPrice())
						+ CommonUtils.getDoubleExceptNull(commInfo
								.getCommTotalPrice()));
				cav.setProcessPrice(CommonUtils.getDoubleExceptNull(cav
						.getProcessPrice())
						+ CommonUtils.getDoubleExceptNull(exg
								.getProcessUnitPrice())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getCommAmount()));
				cav.setMaterialPrice(CommonUtils.getDoubleExceptNull(cav
						.getTotalPrice())
						- CommonUtils.getDoubleExceptNull(cav.getProcessPrice()));
				hm.put(commInfo.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode(), cav);
			}
		}
		// return new ArrayList();
		Iterator iterator = hm.keySet().iterator();

		while (iterator.hasNext()) {
			lsResult.add((CancelAfterVerification) hm.get(iterator.next()
					.toString()));
		}
		return lsResult;
	}

	/**
	 * 根据进出口类型，查找报关单
	 * 
	 * @param impExpFlag
	 *            出口类型
	 * @return
	 */
	public List findSpecialImpExpType(Integer impExpFlag, Integer state) {
		return this.contractStatDao.findSpecialImpExpType(impExpFlag, state);
	}

	/**
	 * 根据进出口类型，查找报关单
	 * 
	 * @param impExpFlag
	 *            出口类型
	 * @return
	 */
	public List findDZSCSpecialImpExpType(Integer impExpFlag, Integer state) {
		return this.contractStatDao
				.findDZSCSpecialImpExpType(impExpFlag, state);
	}

	/**
	 * 根据条件查找报关单表体商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商号编码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户
	 * @param beginDate
	 *            开始有效日期
	 * @param endDate
	 *            结束有效日期
	 * @param contracts
	 *            合同
	 * @param state
	 *            状态
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单表体商品信息
	 */
	public List findSpecialCustomsDeclarationCommInfo(Integer seqNum,
			String code, String name, String customer, Date beginDate,
			Date endDate, List contracts, int state, int impExpFlag) {
		return this.contractStatDao.findSpecialCustomsDeclarationCommInfo(
				seqNum, code, name, customer, beginDate, endDate, contracts,
				state, impExpFlag);
	}

	/**
	 * 根据条件查找报关单表体商品信息
	 * 
	 * @param seqNum
	 *            商品序号
	 * @param code
	 *            商号编码
	 * @param name
	 *            商品名称
	 * @param customer
	 *            客户
	 * @param beginDate
	 *            开始有效日期
	 * @param endDate
	 *            结束有效日期
	 * @param contracts
	 *            合同
	 * @param state
	 *            状态
	 * @param impExpFlag
	 *            进出口标志
	 * @return 报关单表体商品信息
	 */
	public List findSpecialDzscCustomsDeclarationCommInfo(Integer seqNum,
			String code, String name, String customer, Date beginDate,
			Date endDate, List contracts, int state, int impExpFlag) {
		return this.contractStatDao.findSpecialDzscCustomsDeclarationCommInfo(
				seqNum, code, name, customer, beginDate, endDate, contracts,
				state, impExpFlag);
	}

	/**
	 * 查询特殊报关的客户
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param state
	 *            状态类型
	 * @return List 是customer型，存放了已报关的客户
	 */
	public List findSpecialCustomsDeclarationCustomer(Integer impExpFlag,
			int state) {

		return this.contractStatDao.findSpecialCustomsDeclarationCustomer(
				impExpFlag, state);
	}

	/**
	 * 查询进出口申请单报表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillList(Date beginDate, Date endDate, String billType,
			int state) {
		List returnList = new ArrayList();
		List billList = this.contractStatDao.findBillToCustomsList(beginDate,
				endDate, billType, state);
		for (int i = 0; i < billList.size(); i++) {
			ImpExpCommodityInfo info = (ImpExpCommodityInfo) billList.get(i);
			double unitConvert = 1.0;
			if (info.getMateriel() != null
					&& info.getMateriel().getUnitConvert() != null
					&& info.getMateriel().getUnitConvert() != 0.0) {
				unitConvert = info.getMateriel().getUnitConvert();
			}
			// 柯鹏程 info.setTempAmount(info.getQuantity() * unitConvert);
			double quantity = info.getQuantity() == null ? 0.0 : info
					.getQuantity();
			info.setTempAmount(quantity * unitConvert);
			MakeBcsCustomsDeclaration makeBcs = new MakeBcsCustomsDeclaration();
			makeBcs.setImpExpCommodityInfo(info);
			List listCustom = this.contractStatDao
					.findMakeBcsCustomsDeclarationByImpExg(info.getId());
			if (listCustom != null && listCustom.size() > 0) {
				for (int j = 0; j < listCustom.size(); j++) {
					MakeBcsCustomsDeclaration make = (MakeBcsCustomsDeclaration) listCustom
							.get(j);
					BcsCustomsDeclarationCommInfo commInfo = make
							.getBcsCustomsDeclarationCommInfo();
					if (commInfo != null) {
						makeBcs.setBcsCustomsDeclarationCommInfo(commInfo);
					}
				}
			}
			returnList.add(makeBcs);
		}
		return returnList;
	}

	/**
	 * 查询进出口报关单总值（寮步外经办用） 先将报关单币转为合同币值(港币)，然后再转为万美元
	 */
	public ImpMaterialStat findIoMoneyValue(Date beginDate, Date endDate) {
		ImpMaterialStat impMaterialStat = new ImpMaterialStat();

		// 汇率
		Double rate = this.contractStatDao.findCurrRate();

		/**
		 * 直接进口总值
		 */
		impMaterialStat.setImpdirectMoneywj(HKDChangeUSD(
				this.contractStatDao.findImpdirectMoneywj(beginDate, endDate),
				rate));

		/**
		 * 黄埔关区内 直接进口总值
		 */
		impMaterialStat
				.setImpdirectMoneywjIn(HKDChangeUSD(this.contractStatDao
						.findImpdirectMoneywjIn(beginDate, endDate), rate));

		/**
		 * 黄埔关区外 直接进口总值
		 */
		impMaterialStat.setImpdirectMoneywjOut(HKDChangeUSD(
				this.contractStatDao
						.findImpdirectMoneywjOut(beginDate, endDate), rate));

		/**
		 * 结转进口关区外总值
		 */
		impMaterialStat.setImptransEMoneywj(HKDChangeUSD(
				this.contractStatDao.findimptransEMoneywj(beginDate, endDate),
				rate));
		/**
		 * 结转进口关区内总值
		 */
		impMaterialStat.setImptransIMoneywj(HKDChangeUSD(
				this.contractStatDao.findImptransIMoneywj(beginDate, endDate),
				rate));
		/**
		 * 直接出口总值
		 */
		impMaterialStat.setOutdirectMoneywj(HKDChangeUSD(
				this.contractStatDao.findOutdirectMoneywj(beginDate, endDate),
				rate));
		/**
		 * 黄埔关区外 直接出口总值
		 */
		impMaterialStat.setOutdirectMoneywjOut(HKDChangeUSD(
				this.contractStatDao
						.findOutdirectMoneywjOut(beginDate, endDate), rate));
		/**
		 * 黄埔关区内 直接出口总值
		 */
		impMaterialStat
				.setOutdirectMoneywjIn(HKDChangeUSD(this.contractStatDao
						.findOutdirectMoneywjIn(beginDate, endDate), rate));

		/**
		 * 结转出口关区外总值
		 */
		impMaterialStat.setOuttransEMoneywj(HKDChangeUSD(
				this.contractStatDao.findouttransEMoneywj(beginDate, endDate),
				rate));
		/**
		 * 结转出口关区内总值
		 */
		impMaterialStat.setOuttransIMoneywj(HKDChangeUSD(
				this.contractStatDao.findouttransIMoneywj(beginDate, endDate),
				rate));
		/**
		 * 月份
		 */
		impMaterialStat.setMonth(this.contractStatDao.findMonth(beginDate,
				endDate));

		return impMaterialStat;
	}

	/**
	 * 港币转美元
	 * 
	 * @param d
	 *            港币
	 * @param rate
	 *            汇率
	 * @return
	 */
	private Double HKDChangeUSD(Double d, Double rate) {
		return CaleUtil.dividend(CaleUtil.multiply(d, rate, 5), 10000.0, 5);
	}

	/**
	 * 查询申请单转报关单
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param state
	 * @return
	 */
	public List findBillToCustomsList(Date beginDate, Date endDate, int state) {
		List returnList = new ArrayList();
		List billList = this.contractStatDao.findBillToCustomsList(beginDate,
				endDate, null, state);
		for (int i = 0; i < billList.size(); i++) {
			ImpExpCommodityInfo info = (ImpExpCommodityInfo) billList.get(i);
			MakeBcsCustomsDeclaration makeBcs = new MakeBcsCustomsDeclaration();
			makeBcs.setImpExpCommodityInfo(info);
			List listCustom = this.contractStatDao
					.findMakeBcsCustomsDeclarationByImpExg(info.getId());
			if (listCustom != null && listCustom.size() > 0) {
				for (int j = 0; j < listCustom.size(); j++) {
					MakeBcsCustomsDeclaration make = (MakeBcsCustomsDeclaration) listCustom
							.get(j);
					BcsCustomsDeclarationCommInfo commInfo = make
							.getBcsCustomsDeclarationCommInfo();
					if (commInfo != null) {
						makeBcs.setBcsCustomsDeclarationCommInfo(commInfo);
					}
				}
			}
			returnList.add(makeBcs);
		}
		return returnList;
	}

	/**
	 * 根据报关单商品资料查询申请单商品对应的料号
	 * 
	 * @param reqeust
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	public List findMaterielByImpExpCommodityInfoToCustoms(boolean isImport,
			List lsContract, int state) {
		List ptnoList = new ArrayList();
		ptnoList = this.contractStatDao
				.findMaterielByImpExpCommodityInfoToCustoms(isImport,
						lsContract, state);
		return ptnoList;
	}

	/**
	 * 查找报关单中的料号（针对直接新增的报关单，而不通过申请单转报关单）
	 * 
	 * @param reqeust
	 * @param isImport
	 * @param lsContract
	 * @param state
	 * @return
	 */
	public List findMaterielByImpExpCommodityInfo(boolean isImport,
			List lsContract, int state) {
		List ptnoList = new ArrayList();
		ptnoList = this.contractStatDao.findMaterielByImpExpCommodityInfo(
				isImport, lsContract, state);
		List newPtnoList = new ArrayList();
		// 报关单中的料号栏位是记录多个料号的，用逗号分开
		if (ptnoList != null && ptnoList.size() > 0) {
			for (int i = 0; i < ptnoList.size(); i++) {
				if (ptnoList.get(i) != null) {
					String ptnoStr = ptnoList.get(i).toString();
					String[] ptnos = CommonUtils.split(ptnoStr, ",");
					for (int j = 0; j < ptnos.length; j++) {
						if (!newPtnoList.contains(ptnos[j])) {
							newPtnoList.add(ptnos[j]);
						}
					}
				}
			}
		}
		return newPtnoList;
	}

	/**
	 * 根据报关单通过中间表查找申请单
	 * 
	 * @param reqeust
	 *            请求控制
	 * 
	 * @param isImport
	 *            判断是否料件，true为料件
	 * 
	 * @param seqNum商品序号
	 * 
	 * @param commCode
	 *            商品编码
	 * 
	 * @param name
	 *            商品名称
	 * 
	 * @param customer
	 *            客户
	 * 
	 * @param impExpType
	 *            能是多个进出口类型例如："1,2,3,4" 进出口类型
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param contract
	 *            合同备案表头
	 * @param state
	 *            生效类型
	 * @param ptno
	 *            料号
	 * @return List进出口报关登记表
	 */
	public List findImpExpCommodityInfoToCustoms(boolean isImport,
			Integer seqNum, String code, String name, String customer,
			String impExpType, Date beginDate, Date endDate, List contracts,
			int state, int impExpFlag, String ptno) {
		List<TempImpExpCommodityInfo> tempImpExpCommodityInfoList = new ArrayList<TempImpExpCommodityInfo>();
		// 先区分出合同核销与和正执行的再调用不同的DAO方法取出合同报关商品
		List<Contract> contractList = contracts;
		List listPROCESS_EXE = new ArrayList();// 正在执行
		List listCHANGING_CANCEL = new ArrayList();// 已经核销
		for (Contract c : contractList) {
			if (c.getDeclareState().equals("3")) {
				listPROCESS_EXE.add(c);
			} else if (c.getDeclareState().equals("5")) {
				listCHANGING_CANCEL.add(c);
			}
		}
		// 取出正在执行的
		List<BcsCustomsDeclarationCommInfo> list1 = new ArrayList<BcsCustomsDeclarationCommInfo>();
		if (listPROCESS_EXE.size() != 0) {
			list1 = this.contractStatDao
					.findCustomsDeclarationCommInfoContract(isImport, seqNum,
							code, name, customer, impExpType, beginDate,
							endDate, listPROCESS_EXE, state, impExpFlag);
		}
		// 已经核销
		List<BcsCustomsDeclarationCommInfo> list2 = new ArrayList<BcsCustomsDeclarationCommInfo>();
		if (listCHANGING_CANCEL.size() != 0) {
			list2 = this.contractStatDao
					.findCustomsDeclarationCommInfoContract(isImport, seqNum,
							code, name, customer, impExpType, beginDate,
							endDate, listCHANGING_CANCEL, state, impExpFlag);
		}
		if (list2.size() > 0) {
			for (BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo : list2) {
				list1.add(bcsCustomsDeclarationCommInfo);
			}
		}
		// 取得每一笔报关单的所对应的申请单
		for (BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo : list1) {
			List<ImpExpCommodityInfo> impExpCommodityInfoList = new ArrayList();
			impExpCommodityInfoList = this.contractStatDao
					.findImpExpCommodityInfoByCustomsInfo(bcsCustomsDeclarationCommInfo);
			// 把每一笔申请单资料转为临时表
			BaseCustomsDeclaration baseCustomsDeclaration = bcsCustomsDeclarationCommInfo
					.getBaseCustomsDeclaration();
			for (ImpExpCommodityInfo impExpCommodityInfo : impExpCommodityInfoList) {
				TempImpExpCommodityInfo tempImpExpCommodityInfo = new TempImpExpCommodityInfo();
				// PropertyUtils.copyProperties()在性能上有点差
				try {
					PropertyUtils.copyProperties(tempImpExpCommodityInfo,
							impExpCommodityInfo);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (baseCustomsDeclaration.getEmsHeadH2k() != null) {
					tempImpExpCommodityInfo
							.setEmsHeadH2k(baseCustomsDeclaration
									.getEmsHeadH2k());
				}
				if (baseCustomsDeclaration.getDeclarationDate() != null) {
					tempImpExpCommodityInfo
							.setDeclarationDate(baseCustomsDeclaration
									.getDeclarationDate());
				}
				if (baseCustomsDeclaration.getCustomsDeclarationCode() != null) {
					tempImpExpCommodityInfo
							.setCustomsDeclarationCode(baseCustomsDeclaration
									.getCustomsDeclarationCode());
				}
				if (baseCustomsDeclaration.getSerialNumber() != null) {
					tempImpExpCommodityInfo
							.setSerialNumber(baseCustomsDeclaration
									.getSerialNumber());
				}
				if (baseCustomsDeclaration.getEffective() != null) {
					tempImpExpCommodityInfo.setEffective(baseCustomsDeclaration
							.getEffective());
				}
				// 存放备注栏而不是详细规格
				if (bcsCustomsDeclarationCommInfo.getDetailNote() != null) {
					tempImpExpCommodityInfo
							.setDetailNote(bcsCustomsDeclarationCommInfo
									.getMemo());
				}
				if (baseCustomsDeclaration.getTradeMode() != null) {
					tempImpExpCommodityInfo.setTradeMode(baseCustomsDeclaration
							.getTradeMode());
				}
				// 进口料件报关登记表中的数量为报关数量:数量*单位折算系数
				if (isImport) {
					System.out.println(tempImpExpCommodityInfo.getMateriel()
							.getPtNo()
							+ tempImpExpCommodityInfo.getQuantity().toString()
							+ tempImpExpCommodityInfo.getMateriel()
									.getUnitConvert());
					if (tempImpExpCommodityInfo.getQuantity() != null) {
						tempImpExpCommodityInfo
								.setQuantity(tempImpExpCommodityInfo
										.getQuantity()
										* tempImpExpCommodityInfo.getMateriel()
												.getUnitConvert());
					} else {
						tempImpExpCommodityInfo.setQuantity(Double.valueOf(0));
					}
				}
				if (tempImpExpCommodityInfo.getImpExpRequestBill() != null) {
					tempImpExpCommodityInfo.setTradeMode(baseCustomsDeclaration
							.getTradeMode());
				}
				// 以下对临时表数据根据查询条件进行匹配，筛选
				// 料号
				if (ptno != null && !"".equals(ptno)) {
					if (!tempImpExpCommodityInfo.getMateriel().getPtNo()
							.equals(ptno)) {
						continue;
					}
				}
				// 检索商品
				if (name != null && !"".equals(name)) {
					if (!tempImpExpCommodityInfo.getAfterName().equals(name)) {
						continue;
					}
				}
				// 名称==检索商品
				if (name != null && !"".equals(name)) {
					if (!tempImpExpCommodityInfo.getAfterName().equals(name)) {
						continue;
					}
				}
				// 编码
				if (code != null && !"".equals(code)) {
					if (!tempImpExpCommodityInfo.getMateriel().getComplex()
							.getCode().equals(code)) {
						continue;
					}
				}
				// 客户
				if (customer != null && !"".equals(customer)) {
					if (!tempImpExpCommodityInfo.getImpExpRequestBill()
							.getCompany().getName().equals(customer)) {
						continue;
					}
				}
				// 序号
				if (seqNum != null && !"".equals(seqNum)) {
					if (!tempImpExpCommodityInfo.getSerialNumber().equals(
							seqNum)) {
						continue;
					}
				}
				tempImpExpCommodityInfoList.add(tempImpExpCommodityInfo);
				Collections.sort(tempImpExpCommodityInfoList);// 排序
			}
		}

		// 根据料号累计数量
		// 料件数量累计=料件进口+料件转厂+余料结转进口-料件内销-余料结转出口-退料出口
		// 成品数量累计=成品出口+结转出口-退场返工+返工付出
		if (isImport) {
			TempImpExpCommodityInfo info = null;
			TempImpExpCommodityInfo beforeInfo = null;// 记录当前料号
			for (int i = 0; i < tempImpExpCommodityInfoList.size(); i++) {
				info = (TempImpExpCommodityInfo) tempImpExpCommodityInfoList
						.get(i);
				double amount = 0.0;
				if (beforeInfo != null
						&& beforeInfo.getMateriel().getPtNo()
								.equals(info.getMateriel().getPtNo())) {
					amount = beforeInfo.getCommAddUpAmount();
				}

				switch (info.getImpExpRequestBill().getBillType()) {
				case ImpExpType.DIRECT_IMPORT:
				case ImpExpType.TRANSFER_FACTORY_IMPORT:
				case ImpExpType.REMAIN_FORWARD_IMPORT:
					info.setCommAddUpAmount(amount + info.getQuantity());
					break;
				case ImpExpType.BACK_MATERIEL_EXPORT:
				case ImpExpType.REMAIN_FORWARD_EXPORT:
				case ImpExpType.MATERIAL_DOMESTIC_SALES:
					info.setCommAddUpAmount(amount - info.getQuantity());
					break;
				}
				beforeInfo = info;
			}
		} else {
			TempImpExpCommodityInfo info = null;
			TempImpExpCommodityInfo beforeInfo = null;// 记录当前料号
			for (int i = 0; i < tempImpExpCommodityInfoList.size(); i++) {
				info = (TempImpExpCommodityInfo) tempImpExpCommodityInfoList
						.get(i);
				double amount = 0.0;
				if (beforeInfo != null
						&& beforeInfo.getMateriel().getPtNo()
								.equals(info.getMateriel().getPtNo())) {
					amount = beforeInfo.getQuantity();
				}

				switch (info.getImpExpRequestBill().getBillType()) {
				case ImpExpType.DIRECT_EXPORT:
				case ImpExpType.TRANSFER_FACTORY_EXPORT:
				case ImpExpType.REWORK_EXPORT:
					info.setCommAddUpAmount(amount + info.getQuantity());
					break;
				case ImpExpType.BACK_FACTORY_REWORK:
					info.setCommAddUpAmount(amount - info.getQuantity());
					break;
				}

				beforeInfo = info;
			}
		}

		return tempImpExpCommodityInfoList;
	}

	/**
	 * 查出报关单根据条件获取数量，查找报关常用BOM-KEY=料号+版本号，取得耗用料件，折算数量
	 * 进口总数=料件直接进口+料件转厂+余料结转进口-退运出口 料件直接进口=sum（进口类型为0）申报数量 料件转厂=sum（进口类型为1）申报数量
	 * 余料结转进口=sum（进口类型为21）申报数量 退运出口=sum（出口类型为6）申报数量 余料结转出口=sum（出口类型为14）申报数量
	 * 成品耗用=sum（直接出口耗用）+sum（结转出口耗用）+sum(返工复出耗用)-sum(退厂返工耗用) 查找报关常用BOM-KEY=料号+版本号
	 * 直接出口耗用=sum（在查询时间段内直接出口类型为4的报关单商品申报数量）*（对应成品料号在常用BOM中，对应料件料号的单项用量）
	 * 结转出口耗用=sum（在查询时间段内出口类型为5的报关单商品申报数量）*（对应成品料号在常用BOM中，对应料件料号的单项用量）
	 * 返工复出耗用=sum（在查询时间段内出口类型为7的报关单商品申报数量）*（对应成品料号在常用BOM中，对应料件料号的单项用量）
	 * 退厂返工耗用=sum（在查询时间段内进口类型为2的报关单商品申报数量）*（对应成品料号在常用BOM中，对应料件料号的单项用量）
	 * 内销数量=sum（进口类型17）申报数量 边角料=
	 * sum（在查询时间段内直接出口类型为4+5+7-2的报关单商品申报数量）*（对应成品料号在常用BOM中，对应料件料号的损耗）
	 * 余料=进口总数-成品耗用-内销数量 库存量=余料-余料结转出口数量-内销数量
	 * 
	 * @param state
	 * @param ptNo
	 * @param begin
	 * @param end
	 * @param map
	 *            计算结果Map
	 * @param isGroupEms
	 */
	public List findMaterialStockStatistic(List contracts, int state,
			String ptNo, Date begin, Date end, boolean IsGroupEms) {
		// 1、查询报关单料件 料件直接进口、料件转厂、余料结转进口、退运出口、内销数量
		List<Object[]> materialList = contractStatDao.findCaleMaterialNum(
				state, ptNo, begin, end, contracts, IsGroupEms);

		// 2、根据条件查询统计报关单商品，用成品料号关联bom折料计算料件耗用量
		// 2、查询报关单成品 直接出口耗用 结转出口耗用 返工复出耗用 退厂返工耗用
		List<Object[]> produceList = contractStatDao
				.findCaleMaterialNumRelationBOM(state, ptNo, begin, end,
						contracts, IsGroupEms);

		// 3、合并list

		materialList.addAll(produceList);

		// 4、根据报关单商品料件单种类型耗用情况 汇总计算 料件直接进口、料件转厂、余料结转进口、退运出口、直接出口耗用 结转出口耗用 返工复出耗用
		// 退厂返工耗用
		// 4.1、根据报关单商品列表组装《料号》-《汇总性息》的Map
		Map<String, StockStatisticsBill> map = new HashMap<String, StockStatisticsBill>();

		StockStatisticsBill bill = null;// 临时计算引用对象
		Object[] info = null; // 临时循环取值对象

		// 4.2、循环list以key=料号，组装map，计算 料件直接进口、料件转厂、余料结转进口、退运出口
		for (int i = 0; i < materialList.size(); i++) {
			info = materialList.get(i);
			bill = map.get(info[0]);

			// 如果bill=null说明是第一次计算该料号。直接新增计算对象
			if (bill == null) {
				bill = new StockStatisticsBill();
				bill.setPtNo((String) info[0]);// 料号
				bill.setHsCode((String) info[2]);// 商品编号
				bill.setHsName((String) info[3]);// 商品名称
				bill.setHsSpec((String) info[4]);// 商品规格
				bill.setHsUnit((String) info[5]);// 单位名称
				bill.setPtName((String) info[7]);// 工厂名称
				bill.setPtSpec((String) info[8]);// 工厂规格
				bill.setPtUnit((String) info[9]);// 工厂单位
				bill.init();
				if (info[10] != null) {
					bill.setUnitConvert((Double) info[10]);
				}
				bill.setEmsHeadH2k((String) info[11]); // 手册号
				bill.setContract((String) info[12]);// 合同号
			}

			// 把报关单商品info中的信息 设置 到 计算对象中去
			this.setMaterialBillValue(bill, info);

			map.put(bill.getPtNo(), bill);
		}

		return new ArrayList<StockStatisticsBill>(map.values());
	}

	/**
	 * 把查询到的报关单商品统计info中的信息 设置 到 计算对象中去
	 * 
	 * @param bill
	 * @param info
	 */
	private void setMaterialBillValue(StockStatisticsBill bill, Object[] info) {
		Integer impExpType = (Integer) info[1]; // 进出口类型
		Double amount = (Double) info[6]; // 数量

		// 根据单据类型设置计算数据
		switch (impExpType) {
		case ImpExpType.DIRECT_IMPORT:
			bill.setNum3(bill.getNum3() + amount * bill.getUnitConvert());// 料件直接进口
			// 1进口总数（工厂）=3+4+5-6
			bill.setNum1(bill.getNum1() + amount);
			break;
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
			bill.setNum4(bill.getNum4() + amount * bill.getUnitConvert());// 料件转厂
			// 1进口总数（工厂）=3+4+5-6
			bill.setNum1(bill.getNum1() + amount);
			break;
		case ImpExpType.REMAIN_FORWARD_IMPORT:
			bill.setNum5(bill.getNum5() + amount * bill.getUnitConvert());// 余料结转进口
			// 1进口总数（工厂）=3+4+5-6
			bill.setNum1(bill.getNum1() + amount);
			break;
		case ImpExpType.BACK_MATERIEL_EXPORT:
			bill.setNum6(bill.getNum6() + amount * bill.getUnitConvert());// 退运出口
			// 1进口总数（工厂）=3+4+5-6
			bill.setNum1(bill.getNum1() - amount);
			break;
		case ImpExpType.REMAIN_FORWARD_EXPORT:
			bill.setNum7(bill.getNum7() + amount * bill.getUnitConvert());// 余料结转出口
			break;
		case ImpExpType.DIRECT_EXPORT:
			bill.setNum9(bill.getNum9() + amount);// 直接出口耗用
			bill.setNum14(bill.getNum14() + (Double) info[13]);// 累加 直接出口损耗量
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
			bill.setNum10(bill.getNum10() + amount);// 结转出口耗用（转厂）
			bill.setNum14(bill.getNum14() + (Double) info[13]);// 累加 结转出口损耗量
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
			bill.setNum11(bill.getNum11() + amount);// 退厂返工耗用
			bill.setNum14(bill.getNum14() - (Double) info[13]);// 累减 退厂返工损耗量
			break;
		case ImpExpType.REWORK_EXPORT:
			bill.setNum12(bill.getNum12() + amount);// 返工复出耗用
			bill.setNum14(bill.getNum14() + (Double) info[13]);// 累加 返工复出损耗量
			break;
		case ImpExpType.MATERIAL_DOMESTIC_SALES:
			bill.setNum13(bill.getNum13() + amount * bill.getUnitConvert());// 内销数量（海关批准内销数量）
			break;
		default:
			break;
		}

		// 8成品耗用=9+10+12-11
		bill.setNum8(bill.getNum9() + bill.getNum10() + bill.getNum12()
				- bill.getNum11());

		// 2进口总数（报关）= 1 * unitConvert
		bill.setNum2(bill.getNum1() * bill.getUnitConvert());

		// 15余料=2-8-13
		bill.setNum15(bill.getNum2() - bill.getNum8() - bill.getNum13());

		// 16库存数量=15-7
		bill.setNum16(bill.getNum15() - bill.getNum7());

		// 18工厂库存量 = 16 / unitConvert
		bill.setNum18(bill.getNum16() / bill.getUnitConvert());
	}

	/**
	 * 计算料件耗用明细
	 * 
	 * @param isImport
	 *            判断是否料件，true为料件
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
	 * @param ptno
	 *            料号
	 * @param isMerger
	 *            是否合并显示
	 * @return List 是TempMateriDetail型
	 */
	public List findMaterialDetail(boolean isImport, String impExpType,
			Date beginDate, Date endDate, List contracts, int state,
			String ptno, boolean isMerger) {
		// 1.取得合同核销与和正执行的合同中的报关商品
		List<Contract> contractList = contracts;
		List listPROCESS_EXE = new ArrayList();// 正在执行
		List listCHANGING_CANCEL = new ArrayList();// 已经核销
		List<ImpExpCommodityInfo> impExpCommodityInfoList2 = new ArrayList<ImpExpCommodityInfo>();
		for (Contract c : contractList) {
			if (c.getDeclareState().equals("3")) {
				listPROCESS_EXE.add(c);
			} else if (c.getDeclareState().equals("5")) {
				listCHANGING_CANCEL.add(c);
			}
		}
		// 取出正在执行的
		List<BcsCustomsDeclarationCommInfo> list1 = new ArrayList<BcsCustomsDeclarationCommInfo>();
		if (listPROCESS_EXE.size() != 0) {
			list1 = this.contractStatDao
					.findCustomsDeclarationCommInfoContract(isImport, null,
							null, null, null, impExpType, beginDate, endDate,
							listPROCESS_EXE, state, null);
		}
		// 已经核销
		List<BcsCustomsDeclarationCommInfo> list2 = new ArrayList<BcsCustomsDeclarationCommInfo>();
		if (listCHANGING_CANCEL.size() != 0) {
			list2 = this.contractStatDao
					.findCustomsDeclarationCommInfoContract(isImport, null,
							null, null, null, impExpType, beginDate, endDate,
							listCHANGING_CANCEL, state, null);
		}
		if (list2.size() > 0) {
			for (BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo : list2) {
				list1.add(bcsCustomsDeclarationCommInfo);
			}
		}
		// 2.对报关商品通过中间表获得申请单
		List<TempMateriDetail> materiDetailList = new ArrayList<TempMateriDetail>();
		for (BcsCustomsDeclarationCommInfo bcsCustomsDeclarationCommInfo : list1) {
			List<ImpExpCommodityInfo> impExpCommodityInfoList = new ArrayList();
			impExpCommodityInfoList = this.contractStatDao
					.findImpExpCommodityInfoByCustomsInfo(bcsCustomsDeclarationCommInfo);
			// 3.根据申请单中的商品的料号，查找报关常用工厂BOM，KEY=料号+版本号，获得成品的子料件，对商品进行折算
			for (ImpExpCommodityInfo impExpCommodityInfo : impExpCommodityInfoList) {
				// 获得商品对应子料件,对料件耗用进行计算
				List materialDetails = this.contractStatDao
						.findCaleMaterialNumByPtnoVersion(impExpCommodityInfo);
				List newMaterialDetails = new ArrayList();
				if (ptno != null && !"".equals(ptno)) {
					String[] ptnos = CommonUtils.split(ptno, ",");
					for (int i = 0; i < ptnos.length; i++) {
						for (int j = 0; j < materialDetails.size(); j++) {
							MaterialBomDetail materialBomDetail = (MaterialBomDetail) materialDetails
									.get(j);
							if (materialBomDetail.getMateriel().getPtNo()
									.equals(ptnos[i])) {
								newMaterialDetails.add(materialBomDetail);
							}
						}
					}
				} else {
					newMaterialDetails = materialDetails;
				}
				for (int i = 0; i < newMaterialDetails.size(); i++) {
					MaterialBomDetail newMaterialBomDetail = (MaterialBomDetail) newMaterialDetails
							.get(i);
					TempMateriDetail materiDetail = new TempMateriDetail();
					List<BcsTenInnerMerge> bcsTenInnerMergeList = this.contractStatDao
							.findBcsInnerMergeImgByPtNo(newMaterialBomDetail
									.getMateriel().getPtNo());
					BcsTenInnerMerge bcsTenInnerMerge = new BcsTenInnerMerge();
					if (bcsTenInnerMergeList.size() > 0) {
						bcsTenInnerMerge = bcsTenInnerMergeList.get(0);
					}
					materiDetail.setCpseqNum(bcsTenInnerMerge.getSeqNum());
					materiDetail.setImgCommName(bcsTenInnerMerge.getName());
					materiDetail.setImgCommSpec(bcsTenInnerMerge.getSpec());
					materiDetail.setImgUnit(bcsTenInnerMerge.getComUnit());
					materiDetail.setImgSeqNum(newMaterialBomDetail
							.getMateriel().getPtNo());
					materiDetail.setExgSeqNum(bcsCustomsDeclarationCommInfo
							.getPtNo());
					materiDetail.setExgCommName(bcsCustomsDeclarationCommInfo
							.getCommName());
					materiDetail.setExgCommSpec(bcsCustomsDeclarationCommInfo
							.getCommSpec());
					materiDetail.setExgUnit(bcsCustomsDeclarationCommInfo
							.getUnit());
					materiDetail.setHsVersion(impExpCommodityInfo.getVersion());
					double quantity = impExpCommodityInfo.getQuantity() == null ? 0.0
							: impExpCommodityInfo.getQuantity();
					double unitUsed = newMaterialBomDetail.getUnitUsed() == null ? 0.0
							: newMaterialBomDetail.getUnitUsed();
					materiDetail.setExportNum(quantity);
					materiDetail.setExportSumNum(quantity * unitUsed);
					materiDetail
							.setCustomsDeclarationCode(bcsCustomsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getCustomsDeclarationCode());
					materiDetail
							.setDeclarationDate(bcsCustomsDeclarationCommInfo
									.getBaseCustomsDeclaration()
									.getDeclarationDate());
					materiDetail.setImpExpType(bcsCustomsDeclarationCommInfo
							.getBaseCustomsDeclaration().getImpExpType());
					materiDetail.setBillNo(impExpCommodityInfo
							.getImpExpRequestBill().getBillNo());
					double unitWaste = newMaterialBomDetail.getUnitWaste() == null ? 0.0
							: newMaterialBomDetail.getUnitWaste();
					double waste = newMaterialBomDetail.getWaste() == null ? 0.0
							: newMaterialBomDetail.getWaste();
					materiDetail.setUnitWaste(unitWaste);
					materiDetail.setWaste(waste);
					materiDetailList.add(materiDetail);
					System.out.println(materiDetail.getImgSeqNum()
							+ "计算完成。。。。。。。。。。");
				}
			}
		}
		// 5.是否合并显示(KEY=料号+版本号)
		// 出口量=sum本时间段内。(直接出口数量+结转出口数量+返工复出-退厂返工 )
		// 出口耗用量=出口总数*（对应成品料号常用BOM的单项用量）
		if (isMerger) {
			List<TempMateriDetail> newMateriDetailList = new ArrayList<TempMateriDetail>();
			Map<String, TempMateriDetail> map = new HashMap<String, TempMateriDetail>();
			Iterator it = materiDetailList.iterator();
			while (it.hasNext()) {
				TempMateriDetail info = (TempMateriDetail) it.next();
				String key = "";
				if (info.getImgSeqNum() != null && info.getHsVersion() != null) {
					key = info.getImgSeqNum() + info.getHsVersion();
				} else if (info.getImgSeqNum() != null) {
					key = info.getImgSeqNum();
				} else if (info.getHsVersion() != null) {
					key = info.getHsVersion();
				}
				if (map.get(key) == null) {
					map.put(key, info);
				} else {
					TempMateriDetail info1 = (TempMateriDetail) map.get(key);
					switch (info.getImpExpType()) {
					// (直接出口数量+结转出口数量+返工复出-退厂返工 )
					case ImpExpType.DIRECT_EXPORT:
						info1.setExportNum(info1.getExportNum()
								+ info.getExportNum());
						info1.setExportSumNum(info1.getExportSumNum()
								+ info.getExportSumNum());
						break;
					case ImpExpType.TRANSFER_FACTORY_EXPORT:
						info1.setExportNum(info1.getExportNum()
								+ info.getExportNum());
						info1.setExportSumNum(info1.getExportSumNum()
								+ info.getExportSumNum());
						break;
					case ImpExpType.BACK_FACTORY_REWORK:
						info1.setExportNum(info1.getExportNum()
								- info.getExportNum());
						info1.setExportSumNum(info1.getExportSumNum()
								- info.getExportSumNum());
						break;
					case ImpExpType.REWORK_EXPORT:
						info1.setExportNum(info1.getExportNum()
								+ info.getExportNum());
						info1.setExportSumNum(info1.getExportSumNum()
								+ info.getExportSumNum());
						break;
					}
				}
			}
			for (String key : map.keySet()) {
				newMateriDetailList.add(map.get(key));
			}
			// 更换原来列表数据
			materiDetailList.clear();
			materiDetailList = newMateriDetailList;
		}
		Collections.sort(materiDetailList);
		return materiDetailList;
	}

	/**
	 * 出口报关单 进项：KEY=料号 出口总数=直接出口数量+结转出口数量+返工复出-退厂返工
	 * 直接出口数量=sum（在查询时间段内直接出口类型为4的报关单商品申报数量） 结转出口数量=sum（在查询时间段内出口类型为5的报关单商品申报数量）
	 * 返工复出=sum（在查询时间段内出口类型为7的报关单商品申报数量） 退厂返工=sum（在查询时间段内进口类型为2的报关单商品申报数量）
	 * 库存数量=为空，不统计
	 * 
	 * @param state
	 * @param ptNo
	 * @param begin
	 * @param end
	 * @param map
	 *            计算结果Map
	 * @param isGroupEms
	 */
	public List findProduceStockStatistic(List contracts, int state,
			String ptNo, Date begin, Date end, boolean isGroupEms) {

		// 1、根据条件查询统计申请单已转报关单商品数量，
		// 1、查询报关单成品 直接出口 结转出口 返工复出 退厂返工
		List<Object[]> produceList = contractStatDao.findCaleProduceNum(state,
				ptNo, begin, end, contracts, isGroupEms);

		// 2、根据报关单商品料件单种类型耗用情况 汇总计算 直接出口 结转出口 返工复出 退厂返工
		// 2.1、根据报关单商品列表组装《料号》-《汇总性息》的Map
		Map<String, StockStatisticsBill> map = new HashMap<String, StockStatisticsBill>();

		StockStatisticsBill bill = null;// 临时计算引用对象
		Object[] info = null; // 临时循环取值对象

		// 2.2、循环list以key=料号，组装map，计算 直接进口、料件转厂、余料结转进口、退运出口
		for (int i = 0; i < produceList.size(); i++) {
			info = produceList.get(i);
			bill = map.get(info[0]);

			// 如果bill=null说明是第一次计算该料号。直接新增计算对象
			if (bill == null) {
				bill = new StockStatisticsBill();
				bill.setEmsHeadH2k((String) info[0]);// 成品货号
				bill.setSeqNum((Integer) info[1]);//
				bill.setPtNo((String) info[2]);// 成品货号
				bill.setHsCode((String) info[4]);// 商品编号
				bill.setHsName((String) info[5]);// 商品名称
				bill.setHsSpec((String) info[6]);// 商品规格
				bill.setHsUnit((String) info[7]);// 单位名称
				bill.setHsUnit((String) info[9]);// 合同号
				bill.init();
			}

			// 把报关单商品info中的信息 设置 到 计算对象中去
			this.setProduceBillValue(bill, info);

			map.put(bill.getPtNo(), bill);
		}

		return new ArrayList<StockStatisticsBill>(map.values());
	}

	/**
	 * 把查询到的报关单商品统计info中的信息 设置 到 计算对象中去
	 * 
	 * @param bill
	 * @param info
	 */
	private void setProduceBillValue(StockStatisticsBill bill, Object[] info) {
		Integer impExpType = (Integer) info[3]; // 进出口类型
		Double amount = (Double) info[8]; // 数量

		// 根据单据类型设置计算数据
		switch (impExpType) {
		case ImpExpType.DIRECT_EXPORT:
			bill.setNum8(bill.getNum8() + amount);// 直接出口
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
			bill.setNum9(bill.getNum9() + amount);// 结转出口（转厂）
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
			bill.setNum10(bill.getNum10() + amount);// 退厂返工
			break;
		case ImpExpType.REWORK_EXPORT:
			bill.setNum11(bill.getNum11() + amount);// 返工复出
			break;
		default:
			break;
		}

		// 7成品出口=8+9+11-10
		bill.setNum7(bill.getNum8() + bill.getNum9() + bill.getNum11()
				- bill.getNum10());

		// 15库存数量=14-6
		bill.setNum15(bill.getNum14() - bill.getNum6());
	}

	/**
	 * 取得进口料件报关登记表或出口成品报关登记表资料的总金额, 报关单份数
	 * 
	 * @param list
	 *            报关登记表
	 * @return 总金额, 报关单份数
	 */
	public List getTotal(List list) {
		// Collections.sort(list);
		Hashtable hs = new Hashtable();
		List paraList = new ArrayList();
		Integer piece = 0;
		// 根据报关单流水号统计出每个流水号报关单的份数
		for (int i = 0; i < list.size(); i++) {
			ImpExpCustomsDeclarationCommInfo info = (ImpExpCustomsDeclarationCommInfo) list
					.get(i);
			// key=报关单号+流水号
			String key = info.getBaseCustomsDeclaration().getSerialNumber()
					+ info.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode();
			if (hs.get(key) == null) {
				piece = piece + 1;
				info.setBgdNum(piece);
				hs.put(key, info);
			}
			paraList.add(info);
		}
		Double money = Double.valueOf(0);
		Integer piece2 = 0;
		ImpExpCustomsDeclarationCommInfo obj = new ImpExpCustomsDeclarationCommInfo();
		for (int i = 0; i < paraList.size(); i++) {
			ImpExpCustomsDeclarationCommInfo objj = (ImpExpCustomsDeclarationCommInfo) paraList
					.get(i);
			money += fd(objj.getCommTotalPrice());
			Integer num = objj.getBgdNum() == null ? Integer.valueOf(0) : objj
					.getBgdNum();
			if (num > piece2) {
				piece2 = num;
			}
		}
		obj.setCommTotalPrice(CommonUtils.getDoubleByDigit(money, 4));
		List hslist = new Vector(hs.values());
		obj.setCommName("报关单份数:"
				+ (hslist == null ? "0" : String.valueOf(piece2)));
		paraList.add(obj);
		return paraList;
	}

	/**
	 * 查询报关单申报登记表
	 */

	public List findCustomsBrokerList(Date beginDate, Date endDate,
			String customsbrokerName) {
		List list = this.contractDao.findCustomsBrokerList(beginDate, endDate,
				customsbrokerName);
		return list;
	}

	/**
	 * 如果为空，返回零
	 * 
	 * @param d
	 * @return
	 */
	private Double fd(Double d) {
		if (d == null) {
			return Double.valueOf(0);
		}
		return d;
	}

	/**
	 * 检查是否有在备案库不存在的合同料件记录号
	 * 
	 * @param d
	 * @return
	 */
	public boolean ischeckImgExg(List contracts, String materialType) {
		for (int i = 0; i < contracts.size(); i++) {
			Contract contractlist = (Contract) contracts.get(i);
			String parentId = contractlist.getId();
			List isImging = contractDao.finddictporImgExgNo(parentId,
					materialType);
			if (isImging.size() > 0) {
				return true;
			}
		}
		return false;
	}

}
