/*
 * Created on 2005-5-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractstat.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contractstat.entity.ExpProductStat;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.checkcancel.dao.DzscContractCavDao;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.dao.DzscStatDao;
import com.bestway.dzsc.contractstat.entity.DzscBillListDeclaration;
import com.bestway.dzsc.contractstat.entity.DzscBillListExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscBillListImpMaterialStat;
import com.bestway.dzsc.contractstat.entity.DzscCancelAfterVerification;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStat;
import com.bestway.dzsc.contractstat.entity.DzscExpProductStatResult;
import com.bestway.dzsc.contractstat.entity.DzscExpScheduleDetail;
import com.bestway.dzsc.contractstat.entity.DzscImpExpCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStat;
import com.bestway.dzsc.contractstat.entity.DzscImpMaterialStatResult;
import com.bestway.dzsc.contractstat.entity.DzscImpScheduleDetail;
import com.bestway.dzsc.contractstat.entity.TempDzscCustomsDeclarCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;

/**
 * 电子手册统计报表logic
 * 
 * com.bestway.dzsc.contractstat.logic.DzscStatLogic
 * 
 * @author Administrator
 * 
 *         lm checked by 2010-09-07
 */
public class DzscStatLogic {
	private DzscContractCavDao dzscCavDao;

	private DzscStatDao dzscStatDao;

	private DzscDao dzscDao;

	/**
	 * 获取dzscStatDao
	 * 
	 * @return Returns the contractqryDao.
	 */
	public DzscStatDao getDzscStatDao() {
		return dzscStatDao;
	}

	/**
	 * 设置dzscStatDao
	 * 
	 * @param contractqryDao
	 *            The contractqryDao to set.
	 */
	public void setDzscStatDao(DzscStatDao contractqryDao) {
		this.dzscStatDao = contractqryDao;
	}

	/**
	 * 获取dzscCavDao
	 * 
	 * @return Returns the contractCavDao.
	 */
	public DzscContractCavDao getDzscCavDao() {
		return dzscCavDao;
	}

	/**
	 * 设置dzscCavDao
	 * 
	 * @param contractCavDao
	 *            The contractCavDao to set.
	 */
	public void setDzscCavDao(DzscContractCavDao contractCavDao) {
		this.dzscCavDao = contractCavDao;
	}

	/**
	 * 获取dzscDao
	 * 
	 * @return dzscDao
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
	}

	/**
	 * 设置dzscDao
	 * 
	 * @param dzscDao
	 */
	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	/**
	 * 计算进口料件报关总值
	 * 
	 * @param list
	 *            是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 * @return DzscImpMaterialStatResult 存放统计报表中的进口料件报关情况表－－统计数据资料
	 */
	public DzscImpMaterialStatResult impMaterialStat(
			List<DzscImpMaterialStat> list) {
		DzscImpMaterialStatResult statResult = new DzscImpMaterialStatResult();
		double impTotalMoney = 0.0;
		double impContractMoney = 0.0;
		double impRemainMoney = 0.0;
		for (DzscImpMaterialStat impMaterialStat : list) {
			impContractMoney += (impMaterialStat.getContractAmount() == null ? 0.0
					: impMaterialStat.getContractAmount())
					* (impMaterialStat.getUnitPrice() == null ? 0.0
							: impMaterialStat.getUnitPrice());
			impTotalMoney += (impMaterialStat.getImpTotalAmont() == null ? 0.0
					: impMaterialStat.getImpTotalAmont())
					* (impMaterialStat.getUnitPrice() == null ? 0.0
							: impMaterialStat.getUnitPrice());
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
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @return List 是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	public List<DzscImpMaterialStat> findImpMaterialStat(
			DzscEmsPorHead contract, Date beginDate, Date endDate, int state) {
		List<DzscImpMaterialStat> lsResult = new ArrayList<DzscImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		/**
		 * 关封余量--深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.dzscStatDao
				.findFptAppItemCount(emsNo, beginDate, endDate, true));
		/**
		 * 关封余量--转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.dzscStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
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

		// List<Integer> list = this.dzscCavDao.findAllCommInfo(true, emsNo,
		// beginDate, endDate);
		List<DzscEmsImgBill> list = this.dzscDao.findDzscEmsImgBill(contract
				.getId());
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate);
		for (DzscEmsImgBill contractImg : list) {
			// DzscEmsImgBill contractImg =
			// this.dzscCavDao.findImgByCommSerialNo(
			// emsNo, commSerialNo.toString());
			if (contractImg != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				DzscImpMaterialStat impMaterialStat = new DzscImpMaterialStat();
				impMaterialStat.setSeqNum(contractImg.getSeqNum());
				if (contractImg.getComplex() != null) {
					impMaterialStat.setComplex(contractImg.getComplex()
							.getCode());
				}
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getPrice());
				impMaterialStat.setCredenceSerialNo(contractImg.getSeqNum());
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
								null, emsNo, beginDate, endDate, state));
				/**
				 * 转厂进口量
				 */
				impMaterialStat.setTransferFactoryImport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, null,
								emsNo, beginDate, endDate, state));
//				/**
//				 * 统计第一法定数量与第二法定数量
//				 */
//				List listLegalAmountAndSendAmount = this.dzscCavDao
//						.findCommInfoLegalAmountAndSecondAmount(commSerialNo,
//								ImpExpFlag.IMPORT,emsNo, beginDate, endDate, state);

//				/**
//				 * 第一法定数量
//				 */
//				impMaterialStat.setLegalAmount(0.0);
//				/**
//				 * 第二法定数量
//				 */
//				impMaterialStat.setSecondAmount(0.0);
				impMaterialStat
						.setLegalUnit(contractImg.getComplex() == null ? null
								: contractImg.getComplex().getFirstUnit());
				impMaterialStat
						.setSecondLegalUnit(contractImg.getComplex() == null ? null
								: contractImg.getComplex().getSecondUnit());
				//不正确，不知道要不要参数计算
//				for (int i = 0; i < listLegalAmountAndSendAmount.size(); i++) {
//					Object[] obj = (Object[]) listLegalAmountAndSendAmount
//							.get(i);
//					/**
//					 * 第一法定数量
//					 */
//					impMaterialStat.setLegalAmount(obj[0] == null ? 0.0
//							: Double.valueOf(obj[0].toString()));
//					/**
//					 * 第二法定数量
//					 */
//					impMaterialStat.setSecondAmount(obj[1] == null ? 0.0
//							: Double.valueOf(obj[1].toString()));
//
//				}

				/**
				 * 大单进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
								beginDate, endDate, state));
				/**
				 * 退料复出
				 */
				impMaterialStat.setBackMaterialReturn(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0265", "0664" }, emsNo, beginDate,
								endDate, state));
				/**
				 * 退料退换
				 */
				impMaterialStat.setBackMaterialExchange(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0300", "0700" }, emsNo, beginDate,
								endDate, state));

				/**
				 * 余料进口 (余料结转进口)
				 */
				impMaterialStat.setRemainImport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
								beginDate, endDate, state));
				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
								beginDate, endDate, state));
				// 进口总量=报关单进口量-退料出口量
				// impMaterialStat
				// .setImpTotalAmont((impMaterialStat.getImpCDAmount() == null ?
				// 0.0
				// : impMaterialStat.getImpCDAmount())
				// - (impMaterialStat.getBackMaterialExport() == null ? 0.0
				// : impMaterialStat
				// .getBackMaterialExport()));
				/**
				 * 进口总量=直接进口量+转厂进口量+余料进口-退料退换(料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialExchange()));
				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(CommonUtils
						.getDoubleExceptNull(hmImgUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString())));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-余料结转出口
				 */
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainForward()));
				// if (remainAmount > 0) {
				// /**
				// * 余量=进口总量-成品使用量
				// */
				impMaterialStat.setRemainAmount(remainAmount);
				// } else {
				// /**
				// * 不足量=成品使用量-进口总量
				// */
				// impMaterialStat.setUllage(-remainAmount);
				// }
				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getContractAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getImpTotalAmont()));
				/**
				 * 比例
				 */
				impMaterialStat
						.setScale(CommonUtils
								.getDoubleByDigit(
										((impMaterialStat.getCanImportAmount() == null ? 0.0
												: impMaterialStat
														.getCanImportAmount()) / (impMaterialStat
												.getContractAmount() == null ? 1.0
												: impMaterialStat
														.getContractAmount())) * 100,
										2));
				/**
				 * 余料金额
				 */
				impMaterialStat.setRemainMoney((impMaterialStat
						.getRemainAmount() == null ? 0.0 : impMaterialStat
						.getRemainAmount())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString()
						+ (contractImg.getComplex() == null ? "" : contractImg
								.getComplex().getCode()));
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				impMaterialStat.setCountry(contractImg.getCountry());
				// impMaterialStat.setMaterialType(contractImg.get());
				// impMaterialStat
				// .setCredenceSerialNo(contractImg.get() == null ? ""
				// : contractImg.getCredenceNo().toString());
				lsResult.add(impMaterialStat);
			}
		}
		// if (!hmImgUse.isEmpty()) {
		// Iterator iterator = hmImgUse.keySet().iterator();
		// while (iterator.hasNext()) {
		// String commSerialNo = iterator.next().toString();
		// DzscEmsImgBill contractImg = this.dzscCavDao
		// .findImgByCommSerialNo(emsNo, commSerialNo.toString());
		// if (contractImg != null) {
		// DzscImpMaterialStat impMaterialStat = new DzscImpMaterialStat();
		// impMaterialStat.setSeqNum(contractImg.getTenSeqNum());
		// impMaterialStat.setComplex(contractImg.getComplex()
		// .getCode());
		// impMaterialStat.setCommName(contractImg.getName());
		// impMaterialStat.setCommSpec(contractImg.getSpec());
		// impMaterialStat.setUnit(contractImg.getUnit());
		// impMaterialStat.setUnitPrice(contractImg.getPrice());
		// /**
		// * 合同定量
		// */
		// impMaterialStat.setContractAmount(contractImg.getAmount());
		// /**
		// * 成品使用量
		// */
		// impMaterialStat.setProductUse(CommonUtils
		// .getDoubleExceptNull(hmImgUse
		// .get(commSerialNo == null ? ""
		// : commSerialNo.toString())));
		// double remainAmount = (CommonUtils
		// .getDoubleExceptNull(impMaterialStat
		// .getImpTotalAmont()) - CommonUtils
		// .getDoubleExceptNull(impMaterialStat
		// .getProductUse()));
		// if (remainAmount > 0) {
		// /**
		// * 余量=进口总量-成品使用量
		// */
		// impMaterialStat.setRemainAmount(remainAmount);
		// } else {
		// /**
		// * 不足量=成品使用量-进口总量
		// */
		// impMaterialStat.setUllage(-remainAmount);
		// }
		// /**
		// * 可进口量 = 合同定量-总进口量
		// */
		// impMaterialStat.setCanImportAmount(CommonUtils
		// .getDoubleExceptNull(impMaterialStat
		// .getContractAmount())
		// - CommonUtils.getDoubleExceptNull(impMaterialStat
		// .getImpTotalAmont())
		// + CommonUtils.getDoubleExceptNull(impMaterialStat
		// .getBackMaterialReturn()));
		// /**
		// * 比例
		// */
		// impMaterialStat
		// .setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
		// : impMaterialStat.getCanImportAmount()) / (impMaterialStat
		// .getContractAmount() == null ? 0.0
		// : impMaterialStat.getContractAmount())) * 100);
		// /**
		// * 余料金额
		// */
		// impMaterialStat.setRemainMoney((impMaterialStat
		// .getRemainAmount() == null ? 0.0 : impMaterialStat
		// .getRemainAmount())
		// * (impMaterialStat.getUnitPrice() == null ? 0.0
		// : impMaterialStat.getUnitPrice()));
		// impMaterialStat.setCountry(contractImg.getCountry());
		// // impMaterialStat.setMaterialType(contractImg.get());
		// // impMaterialStat
		// // .setCredenceSerialNo(contractImg.get() == null ? ""
		// // : contractImg.getCredenceNo().toString());
		// lsResult.add(impMaterialStat);
		// }
		// }
		// }
		return lsResult;
	}

	/**
	 * 进口料件报关情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscImpMaterialStat型，存放统计报表中的进口料件报关情况表资料
	 */
	public List<DzscImpMaterialStat> findImpMaterialStat(
			DzscEmsPorHead contract, Date beginDate, Date endDate) {
		List<DzscImpMaterialStat> lsResult = new ArrayList<DzscImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		/**
		 * 关封余量--深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.dzscStatDao
				.findFptAppItemCount(emsNo, beginDate, endDate, true));
		/**
		 * 关封余量--转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.dzscStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
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

		// List<Integer> list = this.dzscCavDao.findAllCommInfo(true, emsNo,
		// beginDate, endDate);
		List<DzscEmsImgBill> list = this.dzscDao.findDzscEmsImgBill(contract
				.getId());
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		this.calContractImgUse(contract, hmImgUse, beginDate, endDate);
		for (DzscEmsImgBill contractImg : list) {
			// DzscEmsImgBill contractImg =
			// this.dzscCavDao.findImgByCommSerialNo(
			// emsNo, commSerialNo.toString());
			if (contractImg != null) {
				Integer commSerialNo = contractImg.getSeqNum();
				DzscImpMaterialStat impMaterialStat = new DzscImpMaterialStat();
				impMaterialStat.setSeqNum(contractImg.getSeqNum());
				if (contractImg.getComplex() != null) {
					impMaterialStat.setComplex(contractImg.getComplex()
							.getCode());
				}
				impMaterialStat.setCommName(contractImg.getName());
				impMaterialStat.setCommSpec(contractImg.getSpec());
				impMaterialStat.setUnit(contractImg.getUnit());
				impMaterialStat.setUnitPrice(contractImg.getPrice());
				impMaterialStat.setCredenceSerialNo(contractImg.getSeqNum());
				/**
				 * 合同定量
				 */
				impMaterialStat.setContractAmount(contractImg.getAmount());
				/**
				 * 料件进口量
				 */
				impMaterialStat.setDirectImport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
								null, emsNo, beginDate, endDate));
				/**
				 * 转厂进口量
				 */
				impMaterialStat.setTransferFactoryImport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, null,
								emsNo, beginDate, endDate));
				/**
				 * 大单进口量
				 */
				impMaterialStat.setImpCDAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport()));
				/**
				 * 退料出口量
				 */
				impMaterialStat.setBackMaterialExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
								beginDate, endDate));
				/**
				 * 退料复出
				 */
				impMaterialStat.setBackMaterialReturn(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0265", "0664" }, emsNo, beginDate,
								endDate));
				/**
				 * 退料退换
				 */
				impMaterialStat.setBackMaterialExchange(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0300", "0700" }, emsNo, beginDate,
								endDate));

				/**
				 * 余料进口 (余料结转进口)
				 */
				impMaterialStat.setRemainImport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
								beginDate, endDate));
				/**
				 * 余料出口 (余料结转出口)
				 */
				impMaterialStat.setRemainForward(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.REMAIN_FORWARD_EXPORT, null, emsNo,
								beginDate, endDate));
				// 进口总量=报关单进口量-退料出口量
				// impMaterialStat
				// .setImpTotalAmont((impMaterialStat.getImpCDAmount() == null ?
				// 0.0
				// : impMaterialStat.getImpCDAmount())
				// - (impMaterialStat.getBackMaterialExport() == null ? 0.0
				// : impMaterialStat
				// .getBackMaterialExport()));
				/**
				 * 进口总量=直接进口量+转厂进口量+余料进口-退料退换(料件退换进口量已经包含在料件进口量中)
				 */
				impMaterialStat.setImpTotalAmont(CommonUtils
						.getDoubleExceptNull(impMaterialStat.getDirectImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getTransferFactoryImport())
						+ CommonUtils.getDoubleExceptNull(impMaterialStat
								.getRemainImport())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialExchange()));
				/**
				 * 成品使用量
				 */
				impMaterialStat.setProductUse(CommonUtils
						.getDoubleExceptNull(hmImgUse
								.get(commSerialNo == null ? "" : commSerialNo
										.toString())));
				// hmImgUse.remove(commSerialNo == null ? "" : commSerialNo
				// .toString());
				/**
				 * 余量=进口总量-退运出口(复出)-成品使用量-余料结转出口
				 */
				double remainAmount = (CommonUtils
						.getDoubleExceptNull(impMaterialStat.getImpTotalAmont())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getProductUse())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getBackMaterialReturn()) - CommonUtils
						.getDoubleExceptNull(impMaterialStat.getRemainForward()));
				// if (remainAmount > 0) {
				// /**
				// * 余量=进口总量-成品使用量
				// */
				impMaterialStat.setRemainAmount(remainAmount);
				// } else {
				// /**
				// * 不足量=成品使用量-进口总量
				// */
				// impMaterialStat.setUllage(-remainAmount);
				// }
				/**
				 * 可进口量 = 合同定量-总进口量
				 */
				impMaterialStat.setCanImportAmount(CommonUtils
						.getDoubleExceptNull(impMaterialStat
								.getContractAmount())
						- CommonUtils.getDoubleExceptNull(impMaterialStat
								.getImpTotalAmont()));
				/**
				 * 比例
				 */
				impMaterialStat
						.setScale(CommonUtils
								.getDoubleByDigit(
										((impMaterialStat.getCanImportAmount() == null ? 0.0
												: impMaterialStat
														.getCanImportAmount()) / (impMaterialStat
												.getContractAmount() == null ? 1.0
												: impMaterialStat
														.getContractAmount())) * 100,
										2));
				/**
				 * 余料金额
				 */
				impMaterialStat.setRemainMoney((impMaterialStat
						.getRemainAmount() == null ? 0.0 : impMaterialStat
						.getRemainAmount())
						* (impMaterialStat.getUnitPrice() == null ? 0.0
								: impMaterialStat.getUnitPrice()));
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString()
						+ (contractImg.getComplex() == null ? "" : contractImg
								.getComplex().getCode()));
				/**
				 * 关封余量=关封申请总量—转厂进口量
				 */
				impMaterialStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (impMaterialStat.getTransferFactoryImport() == null ? 0.0
										: impMaterialStat
												.getTransferFactoryImport()));
				impMaterialStat.setCountry(contractImg.getCountry());
				// impMaterialStat.setMaterialType(contractImg.get());
				// impMaterialStat
				// .setCredenceSerialNo(contractImg.get() == null ? ""
				// : contractImg.getCredenceNo().toString());
				lsResult.add(impMaterialStat);
			}
		}
		// if (!hmImgUse.isEmpty()) {
		// Iterator iterator = hmImgUse.keySet().iterator();
		// while (iterator.hasNext()) {
		// String commSerialNo = iterator.next().toString();
		// DzscEmsImgBill contractImg = this.dzscCavDao
		// .findImgByCommSerialNo(emsNo, commSerialNo.toString());
		// if (contractImg != null) {
		// DzscImpMaterialStat impMaterialStat = new DzscImpMaterialStat();
		// impMaterialStat.setSeqNum(contractImg.getTenSeqNum());
		// impMaterialStat.setComplex(contractImg.getComplex()
		// .getCode());
		// impMaterialStat.setCommName(contractImg.getName());
		// impMaterialStat.setCommSpec(contractImg.getSpec());
		// impMaterialStat.setUnit(contractImg.getUnit());
		// impMaterialStat.setUnitPrice(contractImg.getPrice());
		// /**
		// * 合同定量
		// */
		// impMaterialStat.setContractAmount(contractImg.getAmount());
		// /**
		// * 成品使用量
		// */
		// impMaterialStat.setProductUse(CommonUtils
		// .getDoubleExceptNull(hmImgUse
		// .get(commSerialNo == null ? ""
		// : commSerialNo.toString())));
		// double remainAmount = (CommonUtils
		// .getDoubleExceptNull(impMaterialStat
		// .getImpTotalAmont()) - CommonUtils
		// .getDoubleExceptNull(impMaterialStat
		// .getProductUse()));
		// if (remainAmount > 0) {
		// /**
		// * 余量=进口总量-成品使用量
		// */
		// impMaterialStat.setRemainAmount(remainAmount);
		// } else {
		// /**
		// * 不足量=成品使用量-进口总量
		// */
		// impMaterialStat.setUllage(-remainAmount);
		// }
		// /**
		// * 可进口量 = 合同定量-总进口量
		// */
		// impMaterialStat.setCanImportAmount(CommonUtils
		// .getDoubleExceptNull(impMaterialStat
		// .getContractAmount())
		// - CommonUtils.getDoubleExceptNull(impMaterialStat
		// .getImpTotalAmont())
		// + CommonUtils.getDoubleExceptNull(impMaterialStat
		// .getBackMaterialReturn()));
		// /**
		// * 比例
		// */
		// impMaterialStat
		// .setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
		// : impMaterialStat.getCanImportAmount()) / (impMaterialStat
		// .getContractAmount() == null ? 0.0
		// : impMaterialStat.getContractAmount())) * 100);
		// /**
		// * 余料金额
		// */
		// impMaterialStat.setRemainMoney((impMaterialStat
		// .getRemainAmount() == null ? 0.0 : impMaterialStat
		// .getRemainAmount())
		// * (impMaterialStat.getUnitPrice() == null ? 0.0
		// : impMaterialStat.getUnitPrice()));
		// impMaterialStat.setCountry(contractImg.getCountry());
		// // impMaterialStat.setMaterialType(contractImg.get());
		// // impMaterialStat
		// // .setCredenceSerialNo(contractImg.get() == null ? ""
		// // : contractImg.getCredenceNo().toString());
		// lsResult.add(impMaterialStat);
		// }
		// }
		// }
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
			String key = (objs[0] == null ? "" : objs[0].toString())
					+ (objs[1] == null ? "" : objs[1].toString());
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
	 *            手册通关备案表头
	 * @param hmImgUse
	 *            手册通过备案的BOM资料对应的数量
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	private void calContractImgUse(DzscEmsPorHead contract,
			HashMap<String, Double> hmImgUse, Date beginDate, Date endDate) {
		List<DzscExpProductStat> lsProductStat = this.findExpProductStat(
				contract, beginDate, endDate);
		String declareState="";
		for (DzscExpProductStat productStat : lsProductStat) {
			double exgAmount = CommonUtils.getDoubleExceptNull(productStat
					.getExpTotalAmont());
			//电子手册手册状态为：4=核销，3=是变更，2=正在执行
			if ("4".equals(contract.getDeclareState())) {
				declareState = DeclareState.CHANGING_CANCEL;
			} else if (!"".equals(contract.getDeclareState())
					&& "3".equals(contract.getDeclareState())) {
				declareState = DeclareState.CHANGING_EXE;
			}else if (!"".equals(contract.getDeclareState())
					&& "2".equals(contract.getDeclareState())) {
				declareState = DeclareState.PROCESS_EXE;
			}
			List<DzscEmsBomBill> list = this.dzscCavDao.findBomByExg(contract
					.getEmsNo(), productStat.getSeqNum(),declareState);
			for (DzscEmsBomBill contractBom : list) {
				if (contractBom.getImgSeqNum() == null) {
					continue;
				}
				// double imgAmount = exgAmount
				// * CommonUtils.getDoubleExceptNull(contractBom
				// .getUnitDosage());
				double imgAmount = exgAmount
						* CommonUtils.getDoubleExceptNull(CommonUtils
								.getDoubleExceptNull(contractBom.getUnitWare())
								/ (1 - CommonUtils
										.getDoubleExceptNull(contractBom
												.getWare()) / 100.0));
				// System.out.println(productStat.getCommName()+"::"+contractBom.getName()+"imgAmount:"+imgAmount);
				// double imgWaste = imgAmount
				// * ((contractBom.getWare() == null ? 0.0 : contractBom
				// .getWare()) / 100.0);
				double hasAmount = CommonUtils.getDoubleExceptNull(hmImgUse
						.get(contractBom.getImgSeqNum().toString()));
				// System.out.println(productStat.getCommName()+"::"+contractBom.getName()+"hasAmount:"+hasAmount);
				hmImgUse.put(contractBom.getImgSeqNum().toString(), imgAmount
						+ hasAmount);
			}
		}
	}

	/**
	 * 计算出口成品总值
	 * 
	 * @param list
	 *            是DzscExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 * @return DzscExpProductStatResult 存放统计报表中的出口成品报关情况表－－统计数据资料
	 */
	public DzscExpProductStatResult expProductStat(List<DzscExpProductStat> list) {
		DzscExpProductStatResult statResult = new DzscExpProductStatResult();
		double expTotalMoney = 0.0;
		double expContractMoney = 0.0;
		for (DzscExpProductStat expProductStat : list) {
			expContractMoney += (expProductStat.getContractAmount() == null ? 0.0
					: expProductStat.getContractAmount())
					* (expProductStat.getUnitPrice() == null ? 0.0
							: expProductStat.getUnitPrice());
			expTotalMoney += (expProductStat.getExpTotalAmont() == null ? 0.0
					: expProductStat.getExpTotalAmont())
					* (expProductStat.getUnitPrice() == null ? 0.0
							: expProductStat.getUnitPrice());
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
	 * 检查是否为空，如果是则返回0
	 * 
	 * @param dou
	 *            小数Double
	 * @return Double
	 */
	private Double checkNullForDouble(Double dou) {
		return dou == null ? 0.0 : dou;
	}

	/**
	 * 料件进口情况统计表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param state
	 *            生效类型
	 * @param isDetachCompute
	 *            是否分开统计
	 * @return List 是ExpProductStat型，存放统计报表中的料件进口情况表资料
	 */
	public List<DzscImpMaterialStat> findImpMaterialStatByDzscEmsPorHeads(
			List<DzscEmsPorHead> heads, Date beginDate, Date endDate,
			int state, boolean isDetachCompute) {
		if (isDetachCompute) {
			List<DzscImpMaterialStat> lsResult = new ArrayList<DzscImpMaterialStat>();
			for (int i = 0; i < heads.size(); i++) {
				DzscEmsPorHead head = (DzscEmsPorHead) heads.get(i);
				List<DzscImpMaterialStat> data = this.findImpMaterialStat(head,
						beginDate, endDate, state);
				for (DzscImpMaterialStat eps : data) {
					eps.setEmsNo(head.getEmsNo());
					eps.setIeContractNo(head.getIeContractNo());
					lsResult.add(eps);
				}
			}
			return lsResult;
		} else {
			if (heads.size() == 1) {
				DzscEmsPorHead head = (DzscEmsPorHead) heads.get(0);
				List<DzscImpMaterialStat> data = this.findImpMaterialStat(head,
						beginDate, endDate, state);
				for (DzscImpMaterialStat eps : data) {
					eps.setEmsNo("");
				}
				return data;

			} else {
				Map map = new HashMap();
				for (int i = 0; i < heads.size(); i++) {
					DzscEmsPorHead head = (DzscEmsPorHead) heads.get(i);
					List<DzscImpMaterialStat> data = this.findImpMaterialStat(
							head, beginDate, endDate, state);
					for (DzscImpMaterialStat newData : data) {
						newData.setEmsNo("");
						String key = (newData.getComplex().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						// newData.setCredenceNo(null);
						if (map.get(key) == null) {
							if (heads.size() > 1) {
								newData.setSeqNum(null);
							}
							map.put(key, newData);
						} else {
							DzscImpMaterialStat oldData = (DzscImpMaterialStat) map
									.get(key);
							/**
							 * 第一法定数量
							 */
							oldData
									.setLegalAmount(checkNullForDouble(oldData
											.getLegalAmount())
											+ checkNullForDouble(newData
													.getLegalAmount()));
							/**
							 * 第二法定数量
							 */
							oldData
									.setSecondAmount(checkNullForDouble(oldData
											.getSecondAmount())
											+ checkNullForDouble(newData
													.getSecondAmount()));

							/**
							 * 合同定量
							 */
							oldData
									.setContractAmount(checkNullForDouble(oldData
											.getContractAmount())
											+ checkNullForDouble(newData
													.getContractAmount()));

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
							oldData
									.setTransferFactoryImport(checkNullForDouble(oldData
											.getTransferFactoryImport())
											+ checkNullForDouble(newData
													.getTransferFactoryImport()));

							/**
							 * 大单进口量
							 */
							oldData.setImpCDAmount(checkNullForDouble(oldData
									.getImpCDAmount())
									+ checkNullForDouble(newData
											.getImpCDAmount()));

							/**
							 * 退料出口量
							 */
							oldData
									.setBackMaterialExport(checkNullForDouble(oldData
											.getBackMaterialExport())
											+ checkNullForDouble(newData
													.getBackMaterialExport()));

							/**
							 * 退料退换量
							 */
							oldData
									.setBackMaterialExchange(checkNullForDouble(oldData
											.getBackMaterialExchange())
											+ checkNullForDouble(newData
													.getBackMaterialExchange()));
							/**
							 * 退料复出量
							 */
							oldData
									.setBackMaterialReturn(checkNullForDouble(oldData
											.getBackMaterialReturn())
											+ checkNullForDouble(newData
													.getBackMaterialReturn()));

							/**
							 * 余料进口
							 */
							oldData
									.setRemainImport((oldData.getRemainImport() == null ? 0.0
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
							 * 总进口量
							 */
							oldData.setImpTotalAmont(checkNullForDouble(oldData
									.getImpTotalAmont()
									+ newData.getImpTotalAmont()));

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
							 * 可进口量
							 */
							oldData
									.setCanImportAmount(checkNullForDouble(oldData
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
							 * 关封余量
							 */
							oldData
									.setCustomsEnvelopRemain((oldData
											.getCustomsEnvelopRemain() == null ? 0.0
											: oldData.getCustomsEnvelopRemain())
											+ (newData
													.getCustomsEnvelopRemain() == null ? 0.0
													: newData
															.getCustomsEnvelopRemain()));

							/**
							 * 单价
							 */
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
	 *@param isDetachCompute
	 *            是否分开统计
	 * @return List 是ExpProductStat型，存放统计报表中的出口成品报关情况表资料
	 */
	public List<DzscExpProductStat> findExpProductStatByDzscEmsPorHeads(
			List<DzscEmsPorHead> heads, Date beginDate, Date endDate,
			int state, boolean isDetachCompute) {
		if (isDetachCompute) {
			List<DzscExpProductStat> lsResult = new ArrayList<DzscExpProductStat>();
			for (int i = 0; i < heads.size(); i++) {
				DzscEmsPorHead head = (DzscEmsPorHead) heads.get(i);
				List<DzscExpProductStat> data = this.findExpProductStat(head,
						beginDate, endDate, state);
				for (DzscExpProductStat eps : data) {
					eps.setEmsNo(head.getEmsNo());
					eps.setIeContractNo(head.getIeContractNo());
					lsResult.add(eps);
				}
			}
			return lsResult;
		} else {
			if (heads.size() == 1) {
				DzscEmsPorHead head = (DzscEmsPorHead) heads.get(0);
				List<DzscExpProductStat> data = this.findExpProductStat(head,
						beginDate, endDate, state);
				for (DzscExpProductStat eps : data) {
					eps.setEmsNo("");
				}
				return data;

			} else {
				Map map = new HashMap();
				for (int i = 0; i < heads.size(); i++) {
					DzscEmsPorHead head = (DzscEmsPorHead) heads.get(i);
					List<DzscExpProductStat> data = this.findExpProductStat(
							head, beginDate, endDate, state);
					for (DzscExpProductStat newData : data) {
						newData.setEmsNo("");
						String key = (newData.getComplex().trim())
								+ (newData.getCommName() == null ? "" : newData
										.getCommName().trim())
								+ (newData.getCommSpec() == null ? "" : newData
										.getCommSpec().trim())
								+ (newData.getUnit() == null ? "" : newData
										.getUnit().getName().trim());
						newData.setUnitPrice(null);
						// newData.setCredenceNo(null);
						if (map.get(key) == null) {
							if (heads.size() > 1) {
								newData.setSeqNum(null);
							}
							map.put(key, newData);
						} else {
							DzscExpProductStat oldData = (DzscExpProductStat) map
									.get(key);
							// /**
							// * 未转报关单数量
							// */
							// oldData
							// .setNoTranCustomsNum(checkNullForDouble(oldData
							// .getNoTranCustomsNum())
							// + checkNullForDouble(newData
							// .getNoTranCustomsNum()));
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
							oldData
									.setTransferFactoryExport(checkNullForDouble(oldData
											.getTransferFactoryExport())
											+ checkNullForDouble(newData
													.getTransferFactoryExport()));

							/**
							 * 退厂返工数
							 */
							oldData
									.setBackFactoryRework(checkNullForDouble(oldData
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
							 * 总出口量
							 */
							oldData.setExpTotalAmont(checkNullForDouble(oldData
									.getExpTotalAmont())
									+ checkNullForDouble(newData
											.getExpTotalAmont()));
							/**
							 * 可直接出口量
							 */
							oldData
									.setCanExportAmount(checkNullForDouble(oldData
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
							 * 关封余量
							 */
							oldData
									.setCustomsEnvelopRemain(checkNullForDouble(oldData
											.getCustomsEnvelopRemain())
											+ checkNullForDouble(newData
													.getCustomsEnvelopRemain()));
							// /**
							// * 报关单出口量
							// */
							// oldData.setExpCDAmount(checkNullForDouble(oldData
							// .getExpCDAmount())
							// + checkNullForDouble(newData
							// .getExpCDAmount()));

							/**
							 * 合同定量
							 */
							oldData
									.setContractAmount(checkNullForDouble(oldData
											.getContractAmount())
											+ checkNullForDouble(newData
													.getContractAmount()));

							/**
							 * 本期总出口金额
							 */
							// oldData.setExpTotalMoney(checkNullForDouble(oldData
							// .getExpTotalMoney())
							// + checkNullForDouble(newData
							// .getExpTotalMoney()));
							/**
							 * 可出口量
							 */
							oldData
									.setCanExportAmount(checkNullForDouble(oldData
											.getCanExportAmount())
											+ checkNullForDouble(newData
													.getCanExportAmount()));

							/**
							 * 加工费总价
							 */
							oldData
									.setProcessTotalPrice(checkNullForDouble(oldData
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
	 * 出口成品报关情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param date
	 *            生效类型
	 * @return List 是DzscExpProductStat型， 存放统计报表中的出口成品报关情况表资料
	 */
	public List<DzscExpProductStat> findExpProductStat(DzscEmsPorHead contract,
			Date beginDate, Date endDate, int date) {

		List<DzscExpProductStat> lsResult = new ArrayList<DzscExpProductStat>();
		String emsNo = contract.getEmsNo();
		/**
		 * 关封余量－深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.dzscStatDao
				.findFptAppItemCount(emsNo, beginDate, endDate, false));
		/**
		 * 关封余量－转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.dzscStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
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
		// List list = this.dzscCavDao.findAllCommInfo(false, emsNo, beginDate,
		// endDate);
		List<DzscEmsExgBill> list = this.dzscDao.findDzscEmsExgBill(contract);
		for (DzscEmsExgBill contractExg : list) {
			Integer commSerialNo = contractExg.getSeqNum();
			// DzscEmsExgBill contractExg =
			// this.dzscCavDao.findExgByCommSerialNo(
			// emsNo, commSerialNo);
			if (contractExg != null) {
				DzscExpProductStat expProductStat = new DzscExpProductStat();
				expProductStat.setSeqNum(contractExg.getSeqNum());
				expProductStat.setComplex(contractExg.getComplex().getCode());
				expProductStat.setCommName(contractExg.getName());
				expProductStat.setCommSpec(contractExg.getSpec());
				expProductStat.setUnit(contractExg.getUnit());
				expProductStat.setUnitPrice(contractExg.getPrice());
				/**
				 * 合同定量
				 */
				expProductStat.setContractAmount(contractExg.getAmount());
				/**
				 * 成品出口量
				 */
				expProductStat.setDirectExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT,
								null, emsNo, beginDate, endDate, date));
				/**
				 * 转厂出口
				 */
				expProductStat.setTransferFactoryExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.TRANSFER_FACTORY_EXPORT, null,
								emsNo, beginDate, endDate, date));
				/**
				 * 退厂返工数
				 */
				expProductStat.setBackFactoryRework(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.BACK_FACTORY_REWORK, null, emsNo,
								beginDate, endDate, date));
				/**
				 * 返工复出数
				 */
				expProductStat.setReworkExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT,
								null, emsNo, beginDate, endDate, date));
				/**
				 * 总出口量=成品出口量+转厂出口量-退厂返工量+返工复出数
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
				double canExportAmount = (expProductStat.getContractAmount() == null ? 0.0
						: expProductStat.getContractAmount())
						- (expProductStat.getExpTotalAmont() == null ? 0.0
								: expProductStat.getExpTotalAmont());
				if (canExportAmount > 0) {
					/**
					 * 可出口量 = 合同定量-总进口量
					 */
					expProductStat.setCanExportAmount(canExportAmount);
				} else {
					/**
					 * 超量
					 */
					expProductStat.setOverAmount(-canExportAmount);
				}
				/**
				 * 比例
				 */
				expProductStat
						.setScale(CommonUtils
								.getDoubleByDigit(
										((expProductStat.getCanExportAmount() == null ? 0.0
												: expProductStat
														.getCanExportAmount()) / (expProductStat
												.getContractAmount() == null ? 1.0
												: expProductStat
														.getContractAmount())) * 100,
										2));
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString()
						+ contractExg.getComplex().getCode());
				/**
				 * 关封余量=关封总量-转厂出口量
				 */
				expProductStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport()));
				expProductStat
						.setProcessUnitPrice(contractExg.getMachinPrice());
				expProductStat.setProcessTotalPrice(CommonUtils
						.getDoubleExceptNull(expProductStat
								.getProcessUnitPrice())
						* CommonUtils.getDoubleExceptNull(expProductStat
								.getExpTotalAmont()));
				expProductStat.setLegalAmount(contractExg.getAmount());
				expProductStat.setLegalUnit(contractExg.getUnit());
				expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
				expProductStat.setUnitGrossWeight(contractExg
						.getUnitGrossWeight());
				expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
				expProductStat.setLevyMode(contractExg.getLevyMode());
				expProductStat.setNote(contractExg.getNote());
				lsResult.add(expProductStat);
			}
		}
		return lsResult;

	}

	/**
	 * 出口成品报关情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscExpProductStat型， 存放统计报表中的出口成品报关情况表资料
	 */
	public List<DzscExpProductStat> findExpProductStat(DzscEmsPorHead contract,
			Date beginDate, Date endDate) {
		List<DzscExpProductStat> lsResult = new ArrayList<DzscExpProductStat>();
		String emsNo = contract.getEmsNo();
		/**
		 * 关封余量－深加工结转
		 */
		HashMap<String, Double> hmCustomsEnvelop = converListToHashTable(this.dzscStatDao
				.findFptAppItemCount(emsNo, beginDate, endDate, false));
		/**
		 * 关封余量－转厂
		 */
		HashMap<String, Double> hmCustomsEnvelopTrans = converListToHashTable(this.dzscStatDao
				.findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
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
		// List list = this.dzscCavDao.findAllCommInfo(false, emsNo, beginDate,
		// endDate);
		List<DzscEmsExgBill> list = this.dzscDao.findDzscEmsExgBill(contract);
		for (DzscEmsExgBill contractExg : list) {
			Integer commSerialNo = contractExg.getSeqNum();
			// DzscEmsExgBill contractExg =
			// this.dzscCavDao.findExgByCommSerialNo(
			// emsNo, commSerialNo);
			if (contractExg != null) {
				DzscExpProductStat expProductStat = new DzscExpProductStat();
				expProductStat.setSeqNum(contractExg.getSeqNum());
				expProductStat.setComplex(contractExg.getComplex().getCode());
				expProductStat.setCommName(contractExg.getName());
				expProductStat.setCommSpec(contractExg.getSpec());
				expProductStat.setUnit(contractExg.getUnit());
				expProductStat.setUnitPrice(contractExg.getPrice());
				/**
				 * 合同定量
				 */
				expProductStat.setContractAmount(contractExg.getAmount());
				/**
				 * 成品出口量
				 */
				expProductStat.setDirectExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT,
								null, emsNo, beginDate, endDate));
				/**
				 * 转厂出口
				 */
				expProductStat.setTransferFactoryExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT,
								ImpExpType.TRANSFER_FACTORY_EXPORT, null,
								emsNo, beginDate, endDate));
				/**
				 * 退厂返工数
				 */
				expProductStat.setBackFactoryRework(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.IMPORT,
								ImpExpType.BACK_FACTORY_REWORK, null, emsNo,
								beginDate, endDate));
				/**
				 * 返工复出数
				 */
				expProductStat.setReworkExport(this.dzscCavDao
						.findCommInfoTotalAmount(commSerialNo,
								ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT,
								null, emsNo, beginDate, endDate));
				/**
				 * 总出口量=成品出口量+转厂出口量-退厂返工量+返工复出数
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
				double canExportAmount = (expProductStat.getContractAmount() == null ? 0.0
						: expProductStat.getContractAmount())
						- (expProductStat.getExpTotalAmont() == null ? 0.0
								: expProductStat.getExpTotalAmont());
				if (canExportAmount > 0) {
					/**
					 * 可出口量 = 合同定量-总进口量
					 */
					expProductStat.setCanExportAmount(canExportAmount);
				} else {
					/**
					 * 超量
					 */
					expProductStat.setOverAmount(-canExportAmount);
				}
				/**
				 * 比例
				 */
				expProductStat
						.setScale(CommonUtils
								.getDoubleByDigit(
										((expProductStat.getCanExportAmount() == null ? 0.0
												: expProductStat
														.getCanExportAmount()) / (expProductStat
												.getContractAmount() == null ? 1.0
												: expProductStat
														.getContractAmount())) * 100,
										2));
				Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
						.toString()
						+ contractExg.getComplex().getCode());
				/**
				 * 关封余量=关封总量-转厂出口量
				 */
				expProductStat
						.setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
								: customsEnvelopCount)
								- (expProductStat.getTransferFactoryExport() == null ? 0.0
										: expProductStat
												.getTransferFactoryExport()));
				expProductStat
						.setProcessUnitPrice(contractExg.getMachinPrice());
				expProductStat.setProcessTotalPrice(CommonUtils
						.getDoubleExceptNull(expProductStat
								.getProcessUnitPrice())
						* CommonUtils.getDoubleExceptNull(expProductStat
								.getExpTotalAmont()));
				expProductStat.setLegalAmount(contractExg.getAmount());
				expProductStat.setLegalUnit(contractExg.getUnit());
				expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
				expProductStat.setUnitGrossWeight(contractExg
						.getUnitGrossWeight());
				expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
				expProductStat.setLevyMode(contractExg.getLevyMode());
				expProductStat.setNote(contractExg.getNote());
				lsResult.add(expProductStat);
			}
		}
		return lsResult;
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
	 *            手册通关备案表头
	 * @return List 是DzscImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 */
	public List findImpExpCommInfoList(boolean isMaterial, Integer seqNum,
			String customer, String impExpType, Date beginDate, Date endDate,
			DzscEmsPorHead contract) {
		List<DzscImpExpCustomsDeclarationCommInfo> lsResult = new ArrayList<DzscImpExpCustomsDeclarationCommInfo>();
		String emsNo = contract.getEmsNo();
			emsNo = contract.getEmsNo();
		List list = this.dzscStatDao.findImpExpCommInfoList(isMaterial, seqNum,
				customer, impExpType, beginDate, endDate, emsNo);
		Map<String, Double> map = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			DzscImpExpCustomsDeclarationCommInfo tempCommInfo = new DzscImpExpCustomsDeclarationCommInfo();
			DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) list
					.get(i);
			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addupAmount = 0.0;
			if (tempCommInfo.getComplex() != null) {
				addupAmount = map.get(tempCommInfo.getComplex().getCode()) == null ? 0.0
						: Double
								.parseDouble(map.get(
										tempCommInfo.getComplex().getCode())
										.toString());
			}
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
			// sxk
			if (tempCommInfo.getComplex() != null) {
				map.put(tempCommInfo.getComplex().getCode(), addupAmount);
			}

			// map.put(tempCommInfo.getComplex().getCode(), addupAmount);
			lsResult.add(tempCommInfo);
			
		}
		
		return lsResult;
	}

	/**
	 * 最新修改后的方法，获取所选合同的进出口商品 进口料件报关登记表isImport=true，出口成品报关登记表isImport=false
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
	 * @param contracts
	 *            手册通关备案表头List
	 * @param billState
	 *            报关单状态
	 * @return List 是DzscImpExpCustomsDeclarationCommInfo型，进出口报关登记表
	 * @author 石小凯
	 */
	public List findImpExpCommInfoListNew(boolean isMaterial, Integer seqNum,
			String ptName, String customer, String impExpType, Date beginDate,
			Date endDate, List<DzscEmsPorHead> contracts, String billState) {
		List<DzscImpExpCustomsDeclarationCommInfo> lsResult = new ArrayList<DzscImpExpCustomsDeclarationCommInfo>();
		List lists = new ArrayList();
		List exgLists = new ArrayList();
		// 遍历多本合同
		for (DzscEmsPorHead d : contracts) {
			String emsNo = d.getEmsNo();
			// 获取单本合同的进出口商品
			List list = this.dzscStatDao.findImpExpCommInfoList(isMaterial,
					seqNum, ptName, customer, impExpType, beginDate, endDate,
					emsNo, billState);
			lists.addAll(list);
			//获取单本手册通关备案的备案成品,目的单单为DzscImpExpCustomsDeclarationCommInfo添加补充说明栏位（栢能）
			List exgList = dzscDao.findDzscEmsExgBill(d);
			exgLists.add(exgList);
		}
		Map<String, Double> map = new HashMap<String, Double>();
		for (int i = 0; i < lists.size(); i++) {
			// 中间实体
			DzscImpExpCustomsDeclarationCommInfo tempCommInfo = new DzscImpExpCustomsDeclarationCommInfo();
			DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) lists
					.get(i);
			try {
				PropertyUtils.copyProperties(tempCommInfo, commInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			double addupAmount = 0.0;
			// tempCommInfo.getComplex()商品编号
			if (tempCommInfo.getComplex() != null) {
				addupAmount = map.get(tempCommInfo.getComplex().getCode()) == null ? 0.0
						: Double
								.parseDouble(map.get(
										tempCommInfo.getComplex().getCode())
										.toString());
			}
			Integer type = tempCommInfo.getBaseCustomsDeclaration()
					.getImpExpType();
			// 计算数量累计
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
			map.put(tempCommInfo.getComplex().getCode(), addupAmount);
			lsResult.add(tempCommInfo);
		}
		
		//取手册通关备案【成品及单耗】中的【补充说明】栏位,添加到中间实体中（栢能）
		Map hashMap = new HashMap<String, String>();
		for (int j = 0; j < exgLists.size(); j++) {
			List list = (List)exgLists.get(j);
			for (int k = 0; k < list.size(); k++) {
				DzscEmsExgBill bill = (DzscEmsExgBill)list.get(k);
				String key = bill.getDzscEmsPorHead().getEmsNo()+bill.getSeqNum();
				hashMap.put(key, bill.getNote());
			}
			
		}
		
		for (DzscImpExpCustomsDeclarationCommInfo commInfo : lsResult) {
			//key = 手册编号+商品序号
			String key = commInfo.getBaseCustomsDeclaration().getEmsHeadH2k()
					+ commInfo.getCommSerialNo();
			if(hashMap.containsKey(key)){
				commInfo.setNote(hashMap.get(key)==null?"":hashMap.get(key).toString());
			}
		}
		return lsResult;
	}

	/**
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param contract
	 *            手册通关备案表头
	 * @return List 是TempDzscCustomsDeclarCommInfo型，
	 */
	public List findCustomsDeclarationCommInfo(boolean isMaterial,
			DzscEmsPorHead contract) {
		List<TempDzscCustomsDeclarCommInfo> lsResult = new ArrayList<TempDzscCustomsDeclarCommInfo>();
		String emsNo = (contract == null ? "" : contract.getEmsNo());
		List list = this.dzscStatDao.findCustomsDeclarationCommInfo(isMaterial,
				emsNo);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			TempDzscCustomsDeclarCommInfo commInfo = new TempDzscCustomsDeclarCommInfo();
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
	 * 查询已报关的商品
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param contract
	 *            手册通关备案表头
	 * @return List 是TempDzscCustomsDeclarCommInfo型，
	 */
	public List<TempObject> findCustomsDeclarationCommInfoNew(
			boolean isMaterial, DzscEmsPorHead contract) {
		// String emsNo = (contract == null ? "" : contract.getEmsNo());
		// List<DzscCustomsDeclarationCommInfo> list =
		// this.dzscStatDao.findCustomsDeclarationCommInfoNew(isMaterial,
		// emsNo);
		//	
		// return list;
		List<TempObject> list = new ArrayList<TempObject>();
		// List sourceList = this.casBillDao
		// .findHsNameFromFactoryAndFactualCustomsRalation(materielType,
		// index, length, property, value, isLike);
		String emsNo = (contract == null ? "" : contract.getEmsNo());
		List sourceList = this.dzscStatDao.findCustomsDeclarationCommInfoNew(
				isMaterial, emsNo);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject((String) objs[0]); // 名称

			list.add(temp);
		}
		return list;
	}

	/**
	 * 查询已报关的客户
	 * 
	 * @param isImport
	 *            判断是否进口，true时为进口
	 * @param contract
	 *            手册通关备案表头
	 * @return List 存放了已报关的客户
	 */
	public List findCustomsDeclarationCustomer(boolean isImport,
			DzscEmsPorHead contract) {
		List lsResult = new ArrayList();
		String contractNo = null;
		String emsNo = null;
		if (contract != null) {
			contractNo = contract.getIeContractNo();
			emsNo = contract.getEmsNo();
		}
		return this.dzscStatDao.findCustomsDeclarationCustomer(isImport, emsNo);
	}

	/**
	 * 料件，成品执行进度明细
	 * 
	 * @param isMaterial
	 *            判断是否料件，true时为料件
	 * @param impExpType
	 *            物料类别
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 料件时是DzscImpScheduleDetail型，存放统计报表中的料件执行进度明细资料
	 *         成品时是DzscExpScheduleDetail型，存放统计报表中的成品执行进度明细资料
	 */
	public List findImpExpScheduleDetail(boolean isMaterial, String impExpType,
			DzscEmsPorHead contract, Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		List list = this.dzscStatDao.findCustomsDeclarationCommInfo(isMaterial,
				impExpType, contract.getEmsNo(), beginDate, endDate);
		if (isMaterial) {
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				DzscImpScheduleDetail detail = new DzscImpScheduleDetail();
				if (commInfo.getComplex() != null) {
					detail.setCommCode(commInfo.getComplex().getCode());
				}
				detail.setCommName(commInfo.getCommName());
				detail.setCommSpec(commInfo.getCommSpec());
				DzscEmsImgBill contractImg = this.dzscCavDao
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
				DzscExpScheduleDetail detail = new DzscExpScheduleDetail();
				detail.setCommCode(commInfo.getComplex() == null ? ""
						: (commInfo.getComplex() == null ? "" : commInfo
								.getComplex().getCode()));
				detail.setCommName(commInfo.getCommName());
				detail.setCommSpec(commInfo.getCommSpec());
				DzscEmsImgBill contractImg = this.dzscCavDao
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
	 * @param contracts
	 *            手册通关备案表头List
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscCancelAfterVerification型，存放统计报表中的核销单登记表资料
	 */
	public List findCancelAfterVerificationList(List contracts, Date beginDate,
			Date endDate) {
		HashMap<String, DzscCancelAfterVerification> hm = new HashMap<String, DzscCancelAfterVerification>();
		List<DzscCancelAfterVerification> lsResult = new ArrayList<DzscCancelAfterVerification>();
		for (int m = 0; m < contracts.size(); m++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(m);
			List list = this.dzscStatDao.findCancelAfterVerificationList(
					contract.getEmsNo(), beginDate, endDate);

			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				if (commInfo.getBaseCustomsDeclaration().getAuthorizeFile() == null
						|| "".equals(commInfo.getBaseCustomsDeclaration()
								.getAuthorizeFile())) {
					continue;
				}
				List lsExg = this.dzscDao.findDzscEmsExgBillBySeqNum(contract
						.getId(), commInfo.getSerialNumber());
				if (lsExg.size() <= 0) {
					continue;
				}
				DzscEmsExgBill exg = (DzscEmsExgBill) lsExg.get(0);
				DzscCancelAfterVerification cav = null;
				if (hm.get(commInfo.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode()) == null) {
					cav = new DzscCancelAfterVerification();
					cav.setCustomsDeclarationNo(commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
					cav.setCavNo(commInfo.getBaseCustomsDeclaration()
							.getAuthorizeFile());
				} else {
					cav = (DzscCancelAfterVerification) hm.get(commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
				}
				cav.setTotalPrice(CommonUtils.getDoubleExceptNull(cav
						.getTotalPrice())
						+ CommonUtils.getDoubleExceptNull(commInfo
								.getCommTotalPrice()));
				cav.setProcessPrice(CommonUtils.getDoubleExceptNull(cav
						.getProcessPrice())
						+ CommonUtils.getDoubleExceptNull(exg.getMachinPrice())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getCommAmount()));
				cav.setMaterialPrice(CommonUtils.getDoubleExceptNull(cav
						.getTotalPrice())
						- CommonUtils
								.getDoubleExceptNull(cav.getProcessPrice()));
				hm.put(commInfo.getBaseCustomsDeclaration()
						.getCustomsDeclarationCode(), cav);
			}
		}
		Iterator iterator = hm.keySet().iterator();
		while (iterator.hasNext()) {
			lsResult.add((DzscCancelAfterVerification) hm.get(iterator.next()
					.toString()));
		}
		return lsResult;
	}

	/**
	 * 成品料件进出口报关清单明细
	 * 
	 * @param isMaterial
	 *            判断是否料件，true为料件
	 * @param emsNo
	 *            手册编号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscBillListDeclaration型，存放统计报表中的进口料件、出口成品清单明细表的资料
	 */
	public List findDzscBillListDeclaration(boolean isMaterial, String emsNo,
			Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		List list = this.dzscStatDao.findDzscBillListDeclaration(isMaterial,
				emsNo, beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			DzscBillListBeforeCommInfo beforeCommInfo = (DzscBillListBeforeCommInfo) objs[0];
			DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) objs[1];
			DzscBillListDeclaration billList = new DzscBillListDeclaration();
			billList.setListDeclareDate(beforeCommInfo.getAfterComInfo()
					.getBillList().getListDeclareDate());
			billList.setListImpExpType(beforeCommInfo.getAfterComInfo()
					.getBillList().getImpExpType());
			billList.setListNo(beforeCommInfo.getAfterComInfo().getBillList()
					.getListNo());
			billList.setListPtNo(beforeCommInfo.getMateriel().getPtNo());
			billList.setListPtName(beforeCommInfo.getMateriel()
					.getFactoryName());
			billList.setListPtSpec(beforeCommInfo.getMateriel()
					.getFactorySpec());
			billList.setListPtUnit(beforeCommInfo.getMateriel().getPtUnit());
			billList.setDeclaredPrice(beforeCommInfo.getDeclaredPrice());
			billList.setDeclaredAmount(beforeCommInfo.getDeclaredAmount());

			billList.setCustomsDeclarationCode(commInfo
					.getBaseCustomsDeclaration().getCustomsDeclarationCode());
			billList.setCommCode(commInfo.getComplex().getCode());
			billList.setCommName(commInfo.getCommName());
			billList.setCommSpec(commInfo.getCommSpec());
			billList.setCommAmount(commInfo.getCommAmount());
			billList.setUnit(commInfo.getUnit());
			lsResult.add(billList);
		}
		return lsResult;
	}

	/**
	 * 报关清单进口料件情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscBillListImpMaterialStat型，存放统计报表中的进口料件清单情况统计表资料
	 */
	public List<DzscBillListImpMaterialStat> findBillListImpMaterialStat(
			DzscEmsPorHead contract, Date beginDate, Date endDate) {
		List<DzscBillListImpMaterialStat> lsResult = new ArrayList<DzscBillListImpMaterialStat>();
		String emsNo = contract.getEmsNo();
		List<Materiel> list = this.dzscStatDao.findAllMaterial(true, emsNo,
				beginDate, endDate);
		// HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		// this.calContractImgUse(contract, hmImgUse, beginDate, endDate);
		// 关封余量
		// HashMap<String, Double> hmCustomsEnvelop =
		// converListToHashTable(this.contractStatDao
		// .findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
		/**
		 * 料件进口数量
		 */
		HashMap<String, Double> hmDirectImport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT, null, emsNo, beginDate,
						endDate));
		/**
		 * 转厂进口量
		 */
		HashMap<String, Double> hmTransferFactoryImport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
						beginDate, endDate));
		// 余料进口 (余料结转进口)
		// HashMap<String, Double> hmRemainImport =
		// converListToHashTable(this.dzscStatDao
		// .findMaterialTotalAmount(ImpExpFlag.IMPORT,
		// ImpExpType.DIRECT_IMPORT,
		// new String[] { "0657", "0285" }, emsNo, beginDate,
		// endDate));
		// 余料出口 (余料结转出口)
		// HashMap<String, Double> hmRemainForward =
		// converListToHashTable(this.dzscStatDao
		// .findMaterialTotalAmount(ImpExpFlag.EXPORT,
		// ImpExpType.REMAIN_FORWARD, new String[] { "0657",
		// "0285" }, emsNo, beginDate, endDate));
		/**
		 * 料件退换进口数
		 */
		HashMap<String, Double> hmExchangeImport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.DIRECT_IMPORT,
						new String[] { "0300", "0700" }, emsNo, beginDate,
						endDate));
		/**
		 * 料件退换出口数
		 */
		HashMap<String, Double> hmExchangeExport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0300",
								"0700" }, emsNo, beginDate, endDate));
		/**
		 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
		 */
		HashMap<String, Double> hmBackMaterialReturn = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, new String[] { "0265",
								"0664" }, emsNo, beginDate, endDate));
		/**
		 * 退料出口量
		 */
		HashMap<String, Double> hmBackMaterialExport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.BACK_MATERIEL_EXPORT, null, emsNo,
						beginDate, endDate));
		for (Materiel materiel : list) {
			// if (contractImg != null) {
			DzscBillListImpMaterialStat impMaterialStat = new DzscBillListImpMaterialStat();
			// impMaterialStat.setSerialNo(commSerialNo.toString());
			impMaterialStat.setCommCode(materiel.getPtNo());
			impMaterialStat.setCommName(materiel.getFactoryName());
			impMaterialStat.setCommSpec(materiel.getFactorySpec());
			impMaterialStat.setUnit(materiel.getPtUnit());
			impMaterialStat.setUnitPrice(materiel.getPtPrice());
			// 合同定量
			// impMaterialStat.setContractAmount(contractImg.getAmount());
			// 报关单进口量
			// impMaterialStat.setImpCDAmount(this.contractCavDao
			// .findCommInfoTotalAmount(commSerialNo.toString(),
			// ImpExpFlag.IMPORT, null, null, contract
			// .getEmsNo(), beginDate, endDate));
			/**
			 * 料件进口量
			 */
			impMaterialStat.setDirectImport(hmDirectImport.get(materiel
					.getPtNo()));
			/**
			 * 转厂进口量
			 */
			impMaterialStat.setTransferFactoryImport(hmTransferFactoryImport
					.get(materiel.getPtNo()));

			// 余料进口 (余料结转进口)
			// impMaterialStat.setRemainImport(hmRemainImport.get(commSerialNo
			// .toString()
			// + contractImg.getComplex().getCode()));

			// 余料出口 (余料结转出口)
			// impMaterialStat.setRemainForward(hmRemainForward.get(commSerialNo
			// .toString()
			// + contractImg.getComplex().getCode()));

			/**
			 * 料件退换进口数
			 */
			impMaterialStat.setExchangeImport(hmExchangeImport.get(materiel
					.getPtNo()));

			/**
			 * 料件退换出口数
			 */
			impMaterialStat.setExchangeExport(hmExchangeExport.get(materiel
					.getPtNo()));

			/**
			 * 料件复出数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
			 */
			impMaterialStat.setBackMaterialReturn(hmBackMaterialReturn
					.get(materiel.getPtNo()));
			/**
			 * 退料出口量
			 */
			impMaterialStat.setBackMaterialExport(hmBackMaterialExport
					.get(materiel.getPtNo()));
			/**
			 * 进口总量=料件进口量+转厂进口量+料件退换进口量-料件退换出口量
			 */
			impMaterialStat
					.setImpTotalAmont((impMaterialStat.getDirectImport() == null ? 0.0
							: impMaterialStat.getDirectImport())
							+ (impMaterialStat.getTransferFactoryImport() == null ? 0.0
									: impMaterialStat
											.getTransferFactoryImport())
							+ (impMaterialStat.getExchangeImport() == null ? 0.0
									: impMaterialStat.getExchangeImport())
							- (impMaterialStat.getExchangeExport() == null ? 0.0
									: impMaterialStat.getExchangeExport()));
			// 成品使用量
			// impMaterialStat.setProductUse(hmImgUse.get(commSerialNo));

			// 成品使用金额
			// impMaterialStat
			// .setProductUseMoney((impMaterialStat.getProductUse() == null ?
			// 0.0
			// : impMaterialStat.getProductUse())
			// * (impMaterialStat.getUnitPrice() == null ? 0.0
			// : impMaterialStat.getUnitPrice()));
			// double remainAmount = (impMaterialStat.getImpTotalAmont() == null
			// ? 0.0
			// : impMaterialStat.getImpTotalAmont())
			// - (impMaterialStat.getProductUse() == null ? 0.0
			// : impMaterialStat.getProductUse());
			// if (remainAmount > 0) {
			// // 余量=进口总量-成品使用量
			// impMaterialStat.setRemainAmount(remainAmount);
			// } else {
			// // 不足量=成品使用量-进口总量
			// impMaterialStat.setUllage(-remainAmount);
			// }
			// // 可进口量 = 合同定量-总进口量
			// impMaterialStat.setCanImportAmount((impMaterialStat
			// .getContractAmount() == null ? 0.0 : impMaterialStat
			// .getContractAmount())
			// - (impMaterialStat.getImpTotalAmont() == null ? 0.0
			// : impMaterialStat.getImpTotalAmont()));
			//
			// // 比例
			// impMaterialStat
			// .setScale(((impMaterialStat.getCanImportAmount() == null ? 0.0
			// : impMaterialStat.getCanImportAmount()) / (impMaterialStat
			// .getContractAmount() == null ? 0.0
			// : impMaterialStat.getContractAmount())) * 100);
			// // 余料金额
			// impMaterialStat
			// .setRemainMoney((impMaterialStat.getRemainAmount() == null ? 0.0
			// : impMaterialStat.getRemainAmount())
			// * (impMaterialStat.getUnitPrice() == null ? 0.0
			// : impMaterialStat.getUnitPrice()));
			// impMaterialStat.setCountry(contractImg.getCountry());
			// impMaterialStat.setMaterialType(contractImg.getMaterialType());
			// impMaterialStat
			// .setSerialNo(contractImg.getCredenceNo() == null ? ""
			// : contractImg.getCredenceNo().toString());
			// Double customsEnvelopCount = hmCustomsEnvelop.get(contractImg
			// .getSeqNum()
			// + contractImg.getComplex().getCode());
			// /**
			// * 关封余量
			// */
			// impMaterialStat
			// .setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
			// : customsEnvelopCount)
			// - (impMaterialStat.getTransferFactoryImport() == null ? 0.0
			// : impMaterialStat
			// .getTransferFactoryImport()));
			// // 可直接进口量=可进口量-关封余量
			// impMaterialStat.setCanDirectImportAmount((impMaterialStat
			// .getCanImportAmount() == null ? 0.0 : impMaterialStat
			// .getCanImportAmount())
			// - (impMaterialStat.getCustomsEnvelopRemain() == null ? 0.0
			// : impMaterialStat.getCustomsEnvelopRemain()));
			lsResult.add(impMaterialStat);
			// }
		}
		return lsResult;
	}

	/**
	 * 报关清单出口成品情况统计表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscBillListExpProductStat型，存放统计报表中的出口成品清单情况统计表
	 */
	public List<DzscBillListExpProductStat> findBillListExpProductStat(
			DzscEmsPorHead contract, Date beginDate, Date endDate) {
		List<DzscBillListExpProductStat> lsResult = new ArrayList<DzscBillListExpProductStat>();
		String emsNo = contract.getEmsNo();
		List<Materiel> list = this.dzscStatDao.findAllMaterial(false, emsNo,
				beginDate, endDate);
		// 关封余量
		// HashMap<String, Double> hmCustomsEnvelop =
		// converListToHashTable(this.dzscStatDao
		// .findCustomsEnvelopCommInfoCount(emsNo, beginDate, endDate));
		/**
		 * 成品出口数量
		 */
		HashMap<String, Double> hmDirectExport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.DIRECT_EXPORT, null, emsNo, beginDate,
						endDate));
		/**
		 * 转厂出口数量
		 */
		HashMap<String, Double> hmTransferFactoryExport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
						beginDate, endDate));
		/**
		 * 退厂返工数量
		 */
		HashMap<String, Double> hmBackFactoryRework = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.IMPORT,
						ImpExpType.BACK_FACTORY_REWORK, null, emsNo, beginDate,
						endDate));
		/**
		 * 返工复出数量
		 */
		HashMap<String, Double> hmReworkExport = customsListToHashMap(this.dzscStatDao
				.findMaterialTotalAmount(ImpExpFlag.EXPORT,
						ImpExpType.REWORK_EXPORT, null, emsNo, beginDate,
						endDate));
		for (Materiel materiel : list) {
			// String commSerialNo = list.get(i) == null ? "" : list.get(i)
			// .toString();
			DzscBillListExpProductStat expProductStat = new DzscBillListExpProductStat();
			expProductStat.setCommCode(materiel.getPtNo());
			expProductStat.setCommName(materiel.getFactoryName());
			expProductStat.setCommSpec(materiel.getFactorySpec());
			expProductStat.setUnit(materiel.getPtUnit());
			expProductStat.setUnitPrice(materiel.getPtPrice());
			// 合同定量
			// expProductStat.setContractAmount(contractExg.getExportAmount());
			// // 报关单出口量
			// expProductStat.setExpCDAmount(this.contractCavDao
			// .findCommInfoTotalAmount(commSerialNo,
			// ImpExpFlag.EXPORT, null, null, emsNo,
			// beginDate, endDate));
			/**
			 * 成品出口
			 */
			expProductStat.setDirectExport(hmDirectExport.get(materiel
					.getPtNo()));
			/**
			 * 转厂出口
			 */
			expProductStat.setTransferFactoryExport(hmTransferFactoryExport
					.get(materiel.getPtNo()));
			/**
			 * 退厂返工数
			 */
			expProductStat.setBackFactoryRework(hmBackFactoryRework
					.get(materiel.getPtNo()));
			/**
			 * 返工复出数
			 */
			expProductStat.setReworkExport(hmReworkExport.get(materiel
					.getPtNo()));
			/**
			 * 总出口量=报关单出口量+转厂出口-退厂返工量+返工复出数
			 */
			expProductStat
					.setExpTotalAmont((expProductStat.getDirectExport() == null ? 0.0
							: expProductStat.getDirectExport())
							+ (expProductStat.getTransferFactoryExport() == null ? 0.0
									: expProductStat.getTransferFactoryExport())
							- (expProductStat.getBackFactoryRework() == null ? 0.0
									: expProductStat.getBackFactoryRework())
							+ (expProductStat.getReworkExport() == null ? 0.0
									: expProductStat.getReworkExport()));
			// 比例
			// expProductStat
			// .setScale(((expProductStat.getDirectExport() == null ? 0.0
			// : expProductStat.getDirectExport()) / (expProductStat
			// .getExpTotalAmont() == null ? 1.0
			// : expProductStat.getExpTotalAmont())) * 100);
			// double canExportAmount = (expProductStat.getContractAmount() ==
			// null ? 0.0
			// : expProductStat.getContractAmount())
			// - (expProductStat.getExpTotalAmont() == null ? 0.0
			// : expProductStat.getExpTotalAmont());
			// if (canExportAmount > 0) {
			// // 可出口量 = 合同定量-报关单出口量
			// expProductStat.setCanExportAmount(canExportAmount);
			// } else {
			// // 超量
			// expProductStat.setOverAmount(-canExportAmount);
			// }
			// expProductStat.setProcessUnitPrice(contractExg
			// .getProcessUnitPrice());
			// expProductStat.setProcessTotalPrice(contractExg
			// .getProcessTotalPrice());
			// expProductStat.setLegalAmount(contractExg.getLegalAmount());
			// expProductStat.setLegalUnit(contractExg.getLegalUnit());
			// expProductStat.setUnitWeight(contractExg.getUnitGrossWeight());
			// expProductStat.setUnitGrossWeight(contractExg
			// .getUnitGrossWeight());
			// expProductStat.setUnitNetWeight(contractExg.getUnitNetWeight());
			// expProductStat.setLevyMode(contractExg.getLevyMode());
			// Double customsEnvelopCount = hmCustomsEnvelop.get(commSerialNo
			// .toString()
			// + contractExg.getComplex().getCode());
			// // 关封余量=关封总量-转厂出口量
			// expProductStat
			// .setCustomsEnvelopRemain((customsEnvelopCount == null ? 0.0
			// : customsEnvelopCount)
			// - (expProductStat.getTransferFactoryExport() == null ? 0.0
			// : expProductStat
			// .getTransferFactoryExport()));
			// // 可直接出口量=可出口量-关封余量
			// expProductStat
			// .setCanDirectExportAmount(canExportAmount
			// - (expProductStat.getCustomsEnvelopRemain() == null ? 0.0
			// : expProductStat
			// .getCustomsEnvelopRemain()));
			lsResult.add(expProductStat);
		}
		// }
		return lsResult;
	}

	/**
	 * 把list转换为HashMap，list(o)为key,list(1)value
	 * 
	 * @param list
	 *            要转换的list
	 * @return HashMap
	 */
	private HashMap<String, Double> customsListToHashMap(List list) {
		HashMap<String, Double> hm = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String key = (objs[0] == null ? "" : objs[0].toString());
			Double value = objs[1] == null ? 0 : Double.parseDouble(objs[1]
					.toString());
			System.out.println("::::::::" + key + "|||||||" + value);
			hm.put(key, value);
		}
		return hm;
	}

}
