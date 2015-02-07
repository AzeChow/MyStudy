/*
 * Created on 2005-3-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.contractexe.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.CustomsDeclarationState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.entity.FptBillPriceMaintenance;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.transferfactory.entity.BillPriceMaintenance;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.logic.BaseEncLogic;
import com.bestway.dzsc.checkcancel.dao.DzscContractCavDao;
import com.bestway.dzsc.contractexe.dao.DzscContractExeDao;
import com.bestway.dzsc.contractexe.entity.DzscContractExeInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.dao.DzscListDao;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.entity.DzscParameterSet;

// import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;

/**
 * com.bestway.dzsc.contractexe.logic.DzscContractExeLogic
 * 
 * @author Administrator
 */
public class DzscContractExeLogic extends BaseEncLogic {
	private DzscDao dzscDao = null;

	private DzscListDao dzscListDao = null;
	private DzscContractCavDao dzscCavDao;

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
	 * 获取dzscListDao
	 * 
	 * @return dzscListDao
	 */
	public DzscListDao getDzscListDao() {
		return dzscListDao;
	}

	/**
	 * 设置dzscListDao
	 * 
	 * @param dzscListDao
	 */
	public void setDzscListDao(DzscListDao dzscListDao) {
		this.dzscListDao = dzscListDao;
	}

	public List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration, String sfield,
			Object svalues) {
		return null;
	}

	/**
	 * 取得报关单商品临时信息(如果在报关单中已存在的话，将其过滤)
	 * 
	 * @param isMaterial
	 *            true代表料件，false代表成品
	 * @param customsDeclaration
	 *            报关单表头
	 * @return List 是TempCustomsDeclarationCommInfo型，报关单商品临时信息
	 */
	public List getTempCustomsDeclarationCommInfo(boolean isMaterial,
			BaseCustomsDeclaration customsDeclaration) {
		List<TempCustomsDeclarationCommInfo> result = new ArrayList<TempCustomsDeclarationCommInfo>();
		TempCustomsDeclarationCommInfo commInfo = null;
		List list = new ArrayList();
		Map map = new HashMap();
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			List<BillPriceMaintenance> blist = this.getBaseEncDao()
					.findBillPriceMaintenance(ProjectType.DZSC,
							customsDeclaration.getCustomer());
			for (BillPriceMaintenance data : blist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
			List<FptBillPriceMaintenance> clist = this.getBaseEncDao()
					.findFptBillPriceMaintenance(ProjectType.DZSC,
							customsDeclaration.getCustomer());
			for (FptBillPriceMaintenance data : clist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
		}
		if (isMaterial) {
			list = ((DzscContractExeDao) this.getBaseEncDao())
					.findDzscMaterialInfo(customsDeclaration);
		} else {
			list = ((DzscContractExeDao) this.getBaseEncDao())
					.findDzscProductInfo(customsDeclaration);
		}
		CustomsDeclarationSet other = this.findCustomsSet(customsDeclaration
				.getImpExpType());
		for (int i = 0; i < list.size(); i++) {
			commInfo = new TempCustomsDeclarationCommInfo();
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null) {
				commInfo.setEmsSerialNo(objs[0].toString());
			}
			Complex complex = (Complex) objs[1];
			// Complex complex = ((DzscContractExeDao) this.getBaseEncDao())
			// .findComplexByCode(objs[1].toString().trim());
			// if (objs[1] != null && !"".equals(objs[1].toString().trim())) {
			commInfo.setComplex(complex);
			// }
			if (objs[2] != null) {
				commInfo.setUnit((Unit) objs[2]);
			}
			if (complex != null) {
				commInfo.setLegalUnit(complex.getFirstUnit());
			}
			if (complex != null) {
				commInfo.setLegalUnit2(complex.getSecondUnit());
			}
			if (objs[3] != null) {
				commInfo.setName(objs[3].toString());
			}
			if (objs[4] != null) {
				commInfo.setSpec(objs[4].toString());
			}
			if (objs[5] != null) {
				commInfo.setPrice(Double.parseDouble(objs[5].toString()));
				/*
				 * 判断该商品在转厂单价维护当中是否存在,如果存在,则该商品单价设了转厂单价维护当中商品单价.
				 */
				if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
						|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
					if (objs[0] != null && objs[1] != null) {
						String str = objs[0].toString()
								+ ((Complex) objs[1]).getCode();
						if (map.containsKey(str)) {
							commInfo.setPrice((Double) map.get(str));
						}
					}
				}

			}
			// 默认设置
			if (other != null) {
				commInfo.setUses(other.getUses());
				commInfo.setLevyMode(other.getLevyMode());
			}
			if (isMaterial && objs[6] != null) {
				commInfo.setDetailNote(objs[6].toString());
			}

			if (objs[7] != null) {// 第一法定比例因子
				commInfo.setLegalAmount((Double) objs[7]);
			}

			if (objs[8] != null) {
				commInfo.setSecondAmount((Double) objs[8]);
			}
			result.add(commInfo);
		}
		return result;
	}

	/**
	 * 保存报关单商品信息
	 * 
	 * @param isMaterial
	 *            判断是否料件，true时为料件
	 * @param tempCommInfoList
	 *            报关单商品临时信息
	 * @param customsDeclaration
	 *            报关单表头
	 * @see com.bestway.customs.common.logic.BaseEncLogic#saveCustomsDeclarationCommInfo(boolean,
	 *      java.util.List,
	 *      com.bestway.customs.common.entity.BaseCustomsDeclaration)
	 */
	public void saveCustomsDeclarationCommInfo(boolean isMaterial,
			List tempCommInfoList, BaseCustomsDeclaration customsDeclaration) {
		DzscCustomsDeclarationCommInfo commInfo = null;
		TempCustomsDeclarationCommInfo tempCommInfo = null;
		for (int i = 0; i < tempCommInfoList.size(); i++) {
			tempCommInfo = (TempCustomsDeclarationCommInfo) tempCommInfoList
					.get(i);
			commInfo = new DzscCustomsDeclarationCommInfo();
			commInfo.setBaseCustomsDeclaration(customsDeclaration);
			commInfo
					.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
			commInfo.setCommSerialNo(Integer.valueOf(tempCommInfo
					.getEmsSerialNo()));
			commInfo.setComplex(tempCommInfo.getComplex());
			commInfo.setCommName(tempCommInfo.getName());
			commInfo.setCommSpec(tempCommInfo.getSpec());
			commInfo.setCommUnitPrice(tempCommInfo.getPrice());
			commInfo.setUnit(tempCommInfo.getUnit());
			commInfo.setLegalUnit(tempCommInfo.getLegalUnit());
			commInfo.setSecondLegalUnit(tempCommInfo.getLegalUnit2());
			commInfo.setCommSpec(tempCommInfo.getSpec());
			
			// 规范申报规格
			if (CommonUtils.isEmpty(commInfo.getDeclareSpec())) {
				commInfo.setDeclareSpec(commInfo.getCommSpec());
			}
			// 默认设置
			commInfo.setUses(tempCommInfo.getUses());
			commInfo.setLevyMode(tempCommInfo.getLevyMode());
			commInfo.setCommUnitPrice(tempCommInfo.getPrice());
			commInfo.setLegalUnitGene(tempCommInfo.getLegalAmount());
			commInfo.setLegalUnit2Gene(tempCommInfo.getSecondAmount());
			((DzscContractExeDao) this.getBaseEncDao())
					.saveCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 保存报关单商品信息来自pk单据
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 */
	public void saveCustomsCommInfoFromPk(
			BaseCustomsDeclaration customsDeclaration) {
		DzscCustomsDeclarationCommInfo commInfo = null;
		List list = null;
		for (int i = 0; i < list.size(); i++) {
			commInfo = new DzscCustomsDeclarationCommInfo();
			commInfo.setBaseCustomsDeclaration(customsDeclaration);
			commInfo
					.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));

			((DzscContractExeDao) this.getBaseEncDao())
					.saveCustomsDeclarationCommInfo(commInfo);
		}
	}

	/**
	 * 内部商品新增报关单表体
	 * 
	 * @param commInfo
	 *            报关单商品
	 * @param customsDeclaration
	 *            报关单表头
	 * @param exgbill
	 *            手册通过备案成品
	 * @param imgbill
	 *            手册通过备案料件
	 */
	public void saveCustomsinfoFromBill(
			BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, DzscEmsExgBill exgbill,
			DzscEmsImgBill imgbill) {
		commInfo.setBaseCustomsDeclaration(customsDeclaration);
		commInfo.setSerialNumber(this
				.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
		CustomsDeclarationSet other = this.findCustomsSet(customsDeclaration
				.getImpExpType());
		commInfo.setCompany(CommonUtils.getCompany());
		if (exgbill != null) {// 出口
			commInfo.setCommName(exgbill.getName());
			commInfo.setCommSpec(exgbill.getSpec());
			commInfo.setCommSerialNo(exgbill.getSeqNum());
			commInfo.setComplex(exgbill.getComplex());
			commInfo.setCommUnitPrice(exgbill.getPrice());
			double price = 0;
			if (exgbill.getPrice() != null) {
				price = exgbill.getPrice();
			}
			commInfo
					.setCommTotalPrice(forNum(price * commInfo.getCommAmount()));
			commInfo.setUnit(exgbill.getUnit());
			commInfo.setLegalUnit(exgbill.getComplex().getFirstUnit());
			commInfo.setSecondLegalUnit(exgbill.getComplex().getSecondUnit());
			commInfo.setFirstAmount(commInfo.getCommAmount());
			commInfo.setSecondAmount(commInfo.getCommAmount());
			if (customsDeclaration.getCustomer() != null) {
				commInfo.setCountry(customsDeclaration.getCustomer()
						.getCountry2());
			}
			if (other != null) {
				commInfo.setUses(other.getUses());
				commInfo.setLevyMode(other.getLevyMode());
			}
		} else {
			commInfo.setCommName(imgbill.getName());
			commInfo.setCommSpec(imgbill.getSpec());
			commInfo.setCommSerialNo(imgbill.getSeqNum());
			commInfo.setComplex(imgbill.getComplex());
			commInfo.setCommUnitPrice(imgbill.getPrice());
			double price = 0;
			if (imgbill.getPrice() != null) {
				price = imgbill.getPrice();
			}
			commInfo
					.setCommTotalPrice(forNum(price * commInfo.getCommAmount()));
			commInfo.setUnit(imgbill.getUnit());
			commInfo.setLegalUnit(imgbill.getComplex().getFirstUnit());
			commInfo.setSecondLegalUnit(imgbill.getComplex().getSecondUnit());
			commInfo.setFirstAmount(commInfo.getCommAmount());
			commInfo.setSecondAmount(commInfo.getCommAmount());
			if (customsDeclaration.getCustomer() != null) {
				commInfo.setCountry(customsDeclaration.getCustomer()
						.getCountry2());
			}
			if (other != null) {
				commInfo.setUses(other.getUses());
				commInfo.setLevyMode(other.getLevyMode());
			}
		}
		((DzscContractExeDao) this.getBaseEncDao())
				.saveCustomsDeclarationCommInfo(commInfo);
	}

	/**
	 * 数据变换
	 * 
	 * @param shuliang
	 * @return Double
	 */
	private Double forNum(double shuliang) {
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(6, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}

	// /**
	// * 抓取报关单某项商品的合同定额,合同余额,当前余额
	// *
	// * @param isMaterial
	// * @param contract
	// * @param seqNum
	// * @return
	// */
	// public DzscContractExeInfo findDzscContractExeInfo(boolean isMaterial,
	// int impExpType, String tradeCode, DzscEmsPorHead contract,
	// Integer seqNum) {
	// DzscContractExeInfo info = new DzscContractExeInfo();
	// DzscContractExeDao dzscContractExeDao = ((DzscContractExeDao) this
	// .getBaseEncDao());
	// if (isMaterial) {
	// DzscEmsImgBill img = null;
	// List list = dzscDao.findDzscEmsImgBill(contract.getId(), seqNum);
	// if (list.size() <= 0 || list.get(0) == null) {
	// return info;
	// } else {
	// img = (DzscEmsImgBill) list.get(0);
	// }
	// // 合同定额
	// info.setContractMoney(img.getMoney() == null ? 0.0 : img
	// .getMoney());
	//
	// // 已生效料件进口金额
	// double effectiveDirectImport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.IMPORT,
	// ImpExpType.DIRECT_IMPORT, null,
	// contract.getEmsNo(), null, null,
	// CustomsDeclarationState.EFFECTIVED);
	// // 已生效转厂进口金额
	// double effectiveTransferFactoryImport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.IMPORT,
	// ImpExpType.TRANSFER_FACTORY_IMPORT, null, contract
	// .getEmsNo(), null, null,
	// CustomsDeclarationState.EFFECTIVED);
	// // 已生效料件退换出口金额
	// double effectiveExchangeExport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.EXPORT,
	// ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
	// "0300", "0700" }, contract.getEmsNo(),
	// null, null, CustomsDeclarationState.EFFECTIVED);
	//
	// // 全部(已生效+未生效)料件进口金额
	// double allDirectImport = dzscContractExeDao.findCommInfoTotalMoney(
	// seqNum, ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT, null,
	// contract.getEmsNo(), null, null, -1);
	// // 全部(已生效+未生效)转厂进口金额
	// double allTransferFactoryImport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.IMPORT,
	// ImpExpType.TRANSFER_FACTORY_IMPORT, null, contract
	// .getEmsNo(), null, null, -1);
	// // 全部(已生效+未生效)料件退换出口金额
	// double allExchangeExport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.EXPORT,
	// ImpExpType.BACK_MATERIEL_EXPORT, null, contract
	// .getEmsNo(),// new String[] {"0300", "0700"
	// // }
	// null, null, -1);
	//
	// // 退料出口(退换)
	// if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// &&
	// // ("0300".equals(tradeCode)
	// // ||
	// // "0700".equals(tradeCode))
	// info.setCurrentRemain(allDirectImport
	// + allTransferFactoryImport - allExchangeExport);
	// } else {
	// info
	// .setContractRemain(info.getContractMoney()
	// - (effectiveDirectImport
	// + effectiveTransferFactoryImport - effectiveExchangeExport));
	// info
	// .setCurrentRemain(info.getContractMoney()
	// - (allDirectImport + allTransferFactoryImport - allExchangeExport));
	// }
	// } else {
	// DzscEmsExgBill exg = null;
	// List list = dzscDao.findDzscEmsExgBill(contract.getId(), seqNum);
	// if (list.size() <= 0 || list.get(0) == null) {
	// return info;
	// } else {
	// exg = (DzscEmsExgBill) list.get(0);
	// }
	// // 合同定额
	// info.setContractMoney(exg.getMoney() == null ? 0.0 : exg
	// .getMoney());
	// // 已生效成品出口金额
	// double effectiveDirectExport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.EXPORT,
	// ImpExpType.DIRECT_EXPORT, null,
	// contract.getEmsNo(), null, null,
	// CustomsDeclarationState.EFFECTIVED);
	// // 已生效转厂出口金额
	// double effectiveTransferFactoryExport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.EXPORT,
	// ImpExpType.TRANSFER_FACTORY_EXPORT, null, contract
	// .getEmsNo(), null, null,
	// CustomsDeclarationState.EFFECTIVED);
	// // 已生效成品退厂返工金额
	// double effectiveBackFactoryRework = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.IMPORT,
	// ImpExpType.BACK_FACTORY_REWORK, null, contract
	// .getEmsNo(), null, null,
	// CustomsDeclarationState.EFFECTIVED);
	// // 已生效成品返工复出金额
	// double effectiveReworkExport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.EXPORT,
	// ImpExpType.REWORK_EXPORT, null,
	// contract.getEmsNo(), null, null,
	// CustomsDeclarationState.EFFECTIVED);
	// info
	// .setContractRemain(info.getContractMoney()
	// - (effectiveDirectExport
	// + effectiveTransferFactoryExport
	// - effectiveBackFactoryRework + effectiveReworkExport));
	// // 全部(已生效+未生效)成品出口金额
	// double allDirectExport = dzscContractExeDao.findCommInfoTotalMoney(
	// seqNum, ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT, null,
	// contract.getEmsNo(), null, null, -1);
	// // 全部(已生效+未生效)转厂出口金额
	// double allTransferFactoryExport = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.EXPORT,
	// ImpExpType.TRANSFER_FACTORY_EXPORT, null, contract
	// .getEmsNo(), null, null, -1);
	// // 全部(已生效+未生效)成品退厂返工金额
	// double allBackFactoryRework = dzscContractExeDao
	// .findCommInfoTotalMoney(seqNum, ImpExpFlag.IMPORT,
	// ImpExpType.BACK_FACTORY_REWORK, null, contract
	// .getEmsNo(), null, null, -1);
	// // 全部(已生效+未生效)成品返工复出金额
	// double allReworkExport = dzscContractExeDao.findCommInfoTotalMoney(
	// seqNum, ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT, null,
	// contract.getEmsNo(), null, null, -1);
	// if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {// 可退厂返工额
	// info.setCurrentRemain(allDirectExport
	// + allTransferFactoryExport - allBackFactoryRework
	// + allReworkExport);
	// } else if (impExpType == ImpExpType.REWORK_EXPORT) {// 可返工复出额
	// info.setCurrentRemain(allBackFactoryRework - allReworkExport);
	// } else {// 其他
	// info.setCurrentRemain(info.getContractMoney()
	// - (allDirectExport + allTransferFactoryExport
	// - allBackFactoryRework + allReworkExport));
	// }
	// }
	// info.setContractMoney(CommonUtils.getDoubleByDigit(info
	// .getContractMoney(), 2));
	// info.setContractRemain(CommonUtils.getDoubleByDigit(info
	// .getContractRemain(), 2));
	// info.setCurrentRemain(CommonUtils.getDoubleByDigit(info
	// .getCurrentRemain(), 2));
	// return info;
	// }

	/**
	 * 抓取报关单某项商品的合同定量,合同余量,当前余量
	 * 
	 * @param isMaterial
	 *            判断是否料件，true时为料件
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCode
	 *            贸易方式编码
	 * @param contract
	 *            手册通关备案表头
	 * @param seqNum
	 *            凭证序号
	 * @return DzscContractExeInfo 报关单某项商品的合同定量,合同余量,当前余量
	 */
	public DzscContractExeInfo findDzscContractExeInfo(boolean isMaterial,
			int impExpType, String tradeCode, DzscEmsPorHead contract,
			Integer seqNum) {
		DzscContractExeInfo info = new DzscContractExeInfo();
		DzscContractExeDao dzscContractExeDao = ((DzscContractExeDao) this
				.getBaseEncDao());
		if (isMaterial) {
			DzscEmsImgBill img = null;
			List list = dzscDao.findDzscEmsImgBillBySeqNum(contract.getId(),
					seqNum);
			if (list.size() <= 0 || list.get(0) == null) {
				return info;
			} else {
				img = (DzscEmsImgBill) list.get(0);
			}
			/**
			 * 合同定量
			 */
			info.setContractAmount(img.getAmount() == null ? 0.0 : img
					.getAmount());

			/**
			 * 已生效料件进口数量
			 */
			double effectiveDirectImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null,
							contract.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);
			/**
			 * 已生效转厂进口数量
			 */
			double effectiveTransferFactoryImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, contract
									.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);
			/**
			 * 已生效余料转入数量
			 */
			double effectiveRemainForwardImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, contract
									.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);
			/**
			 * 已生效料件退换出口数量
			 */
			double effectiveExchangeExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, contract.getEmsNo(),
							null, null, CustomsDeclarationState.EFFECTIVED);

			/**
			 * 全部(已生效+未生效)料件内销数量
			 */
			double alllefovMateriaImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
									"0644", "0245" }, contract.getEmsNo(),
							null, null, -1);

			/**
			 * 全部(已生效+未生效)料件进口数量
			 */
			double allDirectImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double allRemainForwardImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, contract
									.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)余料结转出口数
			 */
			double allRemainForwardExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REMAIN_FORWARD_EXPORT, null, contract
									.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂进口数量
			 */
			double allTransferFactoryImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, contract
									.getEmsNo(), null, null, -1);

			/**
			 * 全部(已生效+未生效)料件退换出口数量
			 */
			double allExchangeExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, contract.getEmsNo(),
							null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还进口数量(退料退还 已包含括在料件进口数量中 );
			 */
			double allExchangeImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, new String[] { "0300",
									"0700" }, contract.getEmsNo(), null, null,
							-1);
			/**
			 * 全部(已生效+未生效)退料退还出口数量(退料复出);
			 */
			double allBackExportNotImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0664", "0265" }, contract.getEmsNo(),
							null, null, -1);
			/**
			 * 已生效料件退换出口数量
			 */
			double effectiveExchangeExports = dzscContractExeDao
					.findCommInfoTotalAmountxiaokaitest(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT,null, contract.getEmsNo(),
							null, null);
			if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// 可退料出口的数量
				System.out.println("xiaok allDirectImport="+allDirectImport);
				System.out.println("xiaok allTransferFactoryImport="+allTransferFactoryImport);
				System.out.println("xiaok allRemainForwardImport="+allRemainForwardImport);
				System.out.println("xiaok allExchangeExport="+allExchangeExport);
				System.out.println("xiaok allBackExportNotImport="+allBackExportNotImport);
				System.out.println("xiaok allRemainForwardExport="+allRemainForwardExport);
				System.out.println("xiaok effectiveExchangeExports="+effectiveExchangeExports);
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- allRemainForwardExport);
//						- effectiveExchangeExports);
				
				System.out.println("info.setCurrentRemain="+info.getCurrentRemain());
				// - this.getProductUsedAmount(img));
			} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {// 可余料转出的数量
				
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- allRemainForwardExport
						- this.getProductUsedAmount(img));
			} else if (impExpType == ImpExpType.DIRECT_IMPORT
					&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {// 可退料退还进口数量
				info.setCurrentRemain(allExchangeExport - allExchangeImport);
			} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES
					&& (tradeCode.equals("0644") || tradeCode.equals("0245"))) {// 海关批准内销料件内销
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- alllefovMateriaImport
						- this.getProductUsedAmount(img));

			} else {// 可直接进口/可转厂进口数量/可余料结转转入数量
				info
						.setContractRemain(info.getContractAmount()
								- (effectiveDirectImport
										+ effectiveTransferFactoryImport
										+ effectiveRemainForwardImport - effectiveExchangeExport));
				info.setCurrentRemain(info.getContractAmount()
						- (allDirectImport + allTransferFactoryImport
								+ allRemainForwardImport - allExchangeExport));
			}
		} else {
			DzscEmsExgBill exg = null;
			List list = dzscDao.findDzscEmsExgBillBySeqNum(contract.getId(),
					seqNum);
			if (list.size() <= 0 || list.get(0) == null) {
				return info;
			} else {
				exg = (DzscEmsExgBill) list.get(0);
			}

			/**
			 * 合同定量
			 */
			info.setContractAmount(exg.getAmount() == null ? 0.0 : exg
					.getAmount());

			/**
			 * 已生效成品出口数量
			 */
			double effectiveDirectExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null,
							contract.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);

			/**
			 * 已生效转厂出口数量
			 */
			double effectiveTransferFactoryExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, contract
									.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);

			/**
			 * 已生效成品退厂返工数量
			 */
			double effectiveBackFactoryRework = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, contract
									.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);

			/**
			 * 已生效成品返工复出数量
			 */
			double effectiveReworkExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null,
							contract.getEmsNo(), null, null,
							CustomsDeclarationState.EFFECTIVED);
			CompanyOther other = CommonUtils.getOther();
			if (other == null || !other.getIsCludeReturn()) {
				info
				.setContractRemain(info.getContractAmount()
						- (effectiveDirectExport
								+ effectiveTransferFactoryExport));
			}else{
				info
				.setContractRemain(info.getContractAmount()
						- (effectiveDirectExport
								+ effectiveTransferFactoryExport
								- effectiveBackFactoryRework + effectiveReworkExport));
			}
			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null,
							contract.getEmsNo(), null, null, -1);

			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, contract
									.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, contract
									.getEmsNo(), null, null, -1);

			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null,
							contract.getEmsNo(), null, null, -1);

			
			/**
			 * 可退厂返工量
			 */
			if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
				info.setCurrentRemain(allDirectExport
						+ allTransferFactoryExport - allBackFactoryRework
						+ allReworkExport);
			}

			/**
			 * 可返工复出量
			 */
			else if (impExpType == ImpExpType.REWORK_EXPORT) {
				info.setCurrentRemain(allBackFactoryRework - allReworkExport);

			}
			/**
			 * 其他
			 */
			else {
				if (other == null || !other.getIsCludeReturn()) {
					info.setCurrentRemain(info.getContractAmount()
							- (allDirectExport + allTransferFactoryExport));
				}else{

					info.setCurrentRemain(info.getContractAmount()
							- (allDirectExport + allTransferFactoryExport
									- allBackFactoryRework + allReworkExport));
				}
			}

		}
		info.setContractAmount(CommonUtils.getDoubleByDigit(info
				.getContractAmount(), 5));
		info.setContractRemain(CommonUtils.getDoubleByDigit(info
				.getContractRemain(), 5));
		info.setCurrentRemain(CommonUtils.getDoubleByDigit(info
				.getCurrentRemain(), 5));
		return info;
	}

	/**
	 * 抓取报关单某项商品的合同定量,合同余量,当前余量
	 * 
	 * @param isMaterial
	 *            判断是否料件，true时为料件
	 * @param impExpType
	 *            进出口类型
	 * @param tradeCode
	 *            贸易方式编码
	 * @param contract
	 *            手册通关备案表头
	 * @param seqNum
	 *            凭证序号
	 * @return DzscContractExeInfo 报关单某项商品的合同定量,合同余量,当前余量
	 */
	public DzscContractExeInfo findDzscContractExeInfo2(boolean isMaterial,
			String emsNo, Integer seqNum) {
		DzscContractExeInfo info = new DzscContractExeInfo();
		DzscContractExeDao dzscContractExeDao = ((DzscContractExeDao) this
				.getBaseEncDao());
		if (isMaterial) {

			/**
			 * 已生效料件进口数量
			 */
			double effectiveDirectImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
							CustomsDeclarationState.EFFECTIVED);
			/**
			 * 已生效转厂进口数量
			 */
			double effectiveTransferFactoryImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
							null, null, CustomsDeclarationState.EFFECTIVED);
			/**
			 * 已生效余料转入数量
			 */
			double effectiveRemainForwardImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
							null, null, CustomsDeclarationState.EFFECTIVED);
			/**
			 * 已生效料件退换出口数量
			 */
			double effectiveExchangeExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, emsNo, null, null,
							CustomsDeclarationState.EFFECTIVED);

			/**
			 * 全部(已生效+未生效)料件进口数量
			 */
			double allDirectImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
							-1);
			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double allRemainForwardImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
							null, null, -1);

			/**
			 * 全部(已生效+未生效)转厂进口数量
			 */
			double allTransferFactoryImport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
							null, null, -1);

			/**
			 * 全部(已生效+未生效)料件退换出口数量
			 */
			double allExchangeExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, emsNo, null, null, -1);

			{// 可直接进口/可转厂进口数量/可余料结转转入数量
				info
						.setContractRemain((effectiveDirectImport //+ effectiveTransferFactoryImport
								+ effectiveRemainForwardImport - effectiveExchangeExport));
				info
						.setCurrentRemain((allDirectImport //allTransferFactoryImport
								+ allRemainForwardImport - allExchangeExport));
			}
		} else {

			/**
			 * 已生效成品出口数量
			 */
			double effectiveDirectExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, emsNo, null, null,
							CustomsDeclarationState.EFFECTIVED);

			/**
			 * 已生效转厂出口数量
			 */
			double effectiveTransferFactoryExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
							null, null, CustomsDeclarationState.EFFECTIVED);

			/**
			 * 已生效成品退厂返工数量
			 */
			double effectiveBackFactoryRework = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, emsNo, null,
							null, CustomsDeclarationState.EFFECTIVED);

			/**
			 * 已生效成品返工复出数量
			 */
			double effectiveReworkExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, emsNo, null, null,
							CustomsDeclarationState.EFFECTIVED);
			info.setContractRemain((effectiveDirectExport //+ effectiveTransferFactoryExport
					- effectiveBackFactoryRework + effectiveReworkExport));

			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, emsNo, null, null,
							-1);

			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
							null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, emsNo, null,
							null, -1);

			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, emsNo, null, null,
							-1);

			info.setCurrentRemain((allDirectExport //+ allTransferFactoryExport
					- allBackFactoryRework + allReworkExport));
		}

		return info;
	}

	/**
	 * 获取成品耗用量
	 * 
	 * @param emsNo
	 * @param seqNum
	 */
	private double getProductUsedAmount(DzscEmsImgBill img) {
		DzscContractExeDao dzscContractExeDao = ((DzscContractExeDao) this
				.getBaseEncDao());
		double totalUsedAmount = 0.0;
		List lsBom = this.dzscDao.findImgFromBom(img);
		for (int i = 0; i < lsBom.size(); i++) {
			DzscEmsBomBill bom = (DzscEmsBomBill) lsBom.get(i);
			DzscEmsExgBill exg = bom.getDzscEmsExgBill();
			Integer seqNum = exg.getSeqNum();
			DzscEmsPorHead contract = exg.getDzscEmsPorHead();
			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, contract
									.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, contract
									.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = dzscContractExeDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null,
							contract.getEmsNo(), null, null, -1);

			double totalExgAmount = allDirectExport + allTransferFactoryExport
					- allBackFactoryRework + allReworkExport;
			// totalUsedAmount += (totalExgAmount * CommonUtils
			// .getDoubleExceptNull(bom.getUnitDosage()));
			totalUsedAmount += (totalExgAmount * CommonUtils
					.getDoubleExceptNull(CommonUtils.getDoubleExceptNull(bom
							.getUnitWare())
							/ (1 - CommonUtils.getDoubleExceptNull(bom
									.getWare()) / 100.0)));
		}
		return totalUsedAmount;
	}

	/**
	 * 报关清单转换为报关单后把isGenerateDeclaration设为false
	 * 
	 * @param baseCustomsDeclaration
	 *            报关单表头
	 * @see com.bestway.customs.common.logic.BaseEncLogic#writeBackCustomsBillList(com.bestway.customs.common.entity.BaseCustomsDeclaration)
	 */
	@Override
	protected void writeBackCustomsBillList(
			BaseCustomsDeclaration baseCustomsDeclaration) {
		// super.writeBackCustomsBillList(baseCustomsDeclaration);
		String billListId = baseCustomsDeclaration.getBillListId();
		if (billListId != null && !"".equals(billListId)) {
			// List list =
			// dzscListDao.findApplyToCustomsBillListById(billListId);
			List list = dzscListDao
					.findApplyToCustomsBillListByListNo(billListId);
			for (int i = 0; i < list.size(); i++) {
				DzscCustomsBillList billList = (DzscCustomsBillList) list
						.get(i);
				billList.setIsGenerateDeclaration(false);
				this.dzscListDao.saveApplyToCustomsBillList(billList);
			}
		}
	}

	@Override
	protected String getQPBGDXmlPath() {
		DzscParameterSet parameter = ((DzscContractExeDao) this.getBaseEncDao())
				.findDzscParameterSet();
		if (parameter != null) {
			return parameter.getLoadQPDataXmlDir();
		}
		return "";
	}

	@Override
	protected CspParameterSet getCspParameterSet() {
		DzscParameterSet parameter = ((DzscContractExeDao) this.getBaseEncDao())
				.findDzscParameterSet();
		return parameter;
	}
}