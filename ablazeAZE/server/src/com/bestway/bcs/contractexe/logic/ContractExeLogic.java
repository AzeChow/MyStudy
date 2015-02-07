/*
 * Created on 2005-3-29
 *
 * 
 * Window - Preferences - Java - Code Style - Code TemplatesfindBcsContractExeInfo
 */
package com.bestway.bcs.contractexe.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcs.contractexe.dao.ContractExeDao;
import com.bestway.bcs.contractexe.entity.BcsContractExeInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
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

// import com.bestway.bcus.enc.entity.CustomsDeclarationContainer;

/**
 * com.bestway.bcs.contractexe.logic.ContractExeLogic
 * 
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class ContractExeLogic extends BaseEncLogic {
	private ContractDao contractDao;

	private ContractCavDao contractCavDao;

	/**
	 * 获取contractCavDao
	 * 
	 * @return contractCavDao
	 */
	public ContractCavDao getContractCavDao() {
		return contractCavDao;
	}

	/**
	 * 设置contractCavDao
	 * 
	 * @param contractCavDao
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
		Map map = new HashMap();
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			List<BillPriceMaintenance> blist = this.getBaseEncDao()
					.findBillPriceMaintenance(ProjectType.BCS,
							customsDeclaration.getCustomer());
			for (BillPriceMaintenance data : blist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
			List<FptBillPriceMaintenance> clist = this.getBaseEncDao()
					.findFptBillPriceMaintenance(ProjectType.BCS,
							customsDeclaration.getCustomer());
			for (FptBillPriceMaintenance data : clist) {
				String key = data.getSeqNum().toString()
						+ data.getComplex().getCode();
				map.put(key, data.getUnitPrice());
			}
		}
		List list = new ArrayList();
		if (isMaterial) {
			list = ((ContractExeDao) this.getBaseEncDao())
					.findContractMaterialInfo(customsDeclaration);
		} else {
			list = ((ContractExeDao) this.getBaseEncDao())
					.findContractProductInfo(customsDeclaration);
		}
		for (int i = 0; i < list.size(); i++) {
			commInfo = new TempCustomsDeclarationCommInfo();
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null) {
				commInfo.setEmsSerialNo(objs[0].toString());
			}
			if (objs[1] != null) {
				commInfo.setComplex((Complex) objs[1]);
			}
			if (objs[2] != null) {
				commInfo.setUnit((Unit) objs[2]);
			}
			if (objs[3] != null) {// 第一法定单位
				commInfo.setLegalUnit((Unit) objs[3]);
			}
			if (objs[4] != null) {
				commInfo.setName((String) objs[4]);
			}
			if (objs[5] != null) {
				commInfo.setSpec((String) objs[5]);
			}
			if (objs[6] != null) {
				commInfo.setCredenceNo(Integer.valueOf(objs[6].toString()));
			}

			if (objs[7] != null) {
				commInfo.setPrice((Double) objs[7]);
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
			if (objs[8] != null) {// 第二法定单位
				commInfo.setLegalUnit2((Unit) objs[8]);
			}
			// 详细型号规格
			if (isMaterial && objs[9] != null) {
				commInfo.setDetailNote((String) objs[9]);
			}

			if (objs[10] != null) {// 第一法定数量(比例因子)
				commInfo.setLegalAmount((Double) objs[10]);
			}

			if (objs[11] != null) {// 第二法定数量(比例因子)
				commInfo.setSecondAmount((Double) objs[11]);
			}
			if (!isMaterial) {
				if (objs[12] != null) {// 加工费总金额
					commInfo.setWorkUsd((Double) objs[12]);
				}
			}
			result.add(commInfo);
		}
		return result;
	}

	/**
	 * 
	 */
	/**
	 * 纸质手册保存报关单商品信息
	 * 
	 * @param isMaterial
	 *            true代表料件，false代表成品
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
		BcsCustomsDeclarationCommInfo commInfo = null;
		TempCustomsDeclarationCommInfo tempCommInfo = null;
		for (int i = 0; i < tempCommInfoList.size(); i++) {
			tempCommInfo = (TempCustomsDeclarationCommInfo) tempCommInfoList
					.get(i);
			commInfo = new BcsCustomsDeclarationCommInfo();
			commInfo.setBaseCustomsDeclaration(customsDeclaration);
			commInfo.setCommSerialNo(Integer.valueOf(tempCommInfo
					.getEmsSerialNo()));
			commInfo.setComplex(tempCommInfo.getComplex());
			commInfo.setCommName(tempCommInfo.getName());
			commInfo.setCommSpec(tempCommInfo.getSpec());
			if (isMaterial) {
				commInfo.setDetailNote(tempCommInfo.getDetailNote());// 详细型号规格
			}
			commInfo.setUnit(tempCommInfo.getUnit());
			commInfo.setLegalUnit(tempCommInfo.getLegalUnit());
			commInfo.setSecondLegalUnit(tempCommInfo.getLegalUnit2());
			commInfo.setCommUnitPrice(tempCommInfo.getPrice());// 单价
			commInfo.setCredenceNo(tempCommInfo.getCredenceNo());

			// 规范申报规格
			if (CommonUtils.isEmpty(commInfo.getDeclareSpec())) {
				commInfo.setDeclareSpec(commInfo.getCommSpec());
			}

			commInfo.setLegalUnitGene(tempCommInfo.getLegalAmount());
			commInfo.setLegalUnit2Gene(tempCommInfo.getSecondAmount());
			commInfo.setWorkUsd(tempCommInfo.getWorkUsd());// 加工费总价
			CustomsDeclarationSet other = this
					.findCustomsSet(customsDeclaration.getImpExpType());
			if (other != null) {
				commInfo.setUses(other.getUses());
				commInfo.setLevyMode(other.getLevyMode());
				commInfo.setCountry(other.getCountry());
			}

			this.saveCustomsDeclarationCommInfo(commInfo);
		}
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
	public void saveCustomsinfoFromBill(
			BaseCustomsDeclarationCommInfo commInfo,
			BaseCustomsDeclaration customsDeclaration, ContractExg exgbill,
			ContractImg imgbill) {
		CustomsDeclarationSet other = this.findCustomsSet(customsDeclaration
				.getImpExpType());
		commInfo.setBaseCustomsDeclaration(customsDeclaration);
		commInfo.setSerialNumber(this
				.getCustomsDeclarationCommInfoSerialNumber(customsDeclaration));
		commInfo.setCompany(CommonUtils.getCompany());
		if (exgbill != null) {// 出口
			commInfo.setCommName(exgbill.getName());
			commInfo.setCommSpec(exgbill.getSpec());
			commInfo.setCommSerialNo(exgbill.getSeqNum());
			commInfo.setComplex(exgbill.getComplex());
			commInfo.setCommUnitPrice(exgbill.getDeclarePrice());
			double price = 0;
			if (exgbill.getDeclarePrice() != null) {
				price = exgbill.getDeclarePrice();
			}
			commInfo.setCommTotalPrice(forNum(price * commInfo.getCommAmount()));
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
			commInfo.setDetailNote(imgbill.getDetailNote()); // 详细型号规格
			commInfo.setCommSpec(imgbill.getSpec());
			commInfo.setCommSerialNo(imgbill.getSeqNum());
			commInfo.setComplex(imgbill.getComplex());
			commInfo.setCommUnitPrice(imgbill.getDeclarePrice());
			double price = 0;
			if (imgbill.getDeclarePrice() != null) {
				price = imgbill.getDeclarePrice();
			}
			commInfo.setCommTotalPrice(forNum(price * commInfo.getCommAmount()));
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
		((ContractExeDao) this.getBaseEncDao())
				.saveCustomsDeclarationCommInfo(commInfo);
	}

	/**
	 * @param shuliang
	 * @return
	 */
	private Double forNum(double shuliang) {
		BigDecimal bd = new BigDecimal(shuliang);
		String totalshuliang = bd.setScale(6, BigDecimal.ROUND_HALF_UP)
				.toString();
		return Double.valueOf(totalshuliang);
	}

	/**
	 * 获取成品耗用量
	 * 
	 * @param img
	 *            料件
	 * @return
	 */
	private double getProductUsedAmount(ContractImg img) {
		double totalUsedAmount = 0.0;
		long t = System.currentTimeMillis();
		// 成品出口 成品资料 ---成品 ，转厂出口数量，成品退厂返工数量，成品返工复出数量，成品退厂返工数量
		Integer[] impExpTypes = new Integer[] { ImpExpType.DIRECT_EXPORT,
				ImpExpType.TRANSFER_FACTORY_EXPORT, ImpExpType.REWORK_EXPORT,
				ImpExpType.BACK_FACTORY_REWORK };
		List<Object[]> usedLs = contractDao.getProductUsedAmountByImpExpTypes(
				img, impExpTypes);
		for (Object[] used : usedLs) {
			// 成品退厂返工数量
			if (((Integer) used[0]).intValue() == ImpExpType.BACK_FACTORY_REWORK) {
				totalUsedAmount -= CommonUtils
						.getDoubleExceptNull((Double) used[1]);
			} else {
				totalUsedAmount += CommonUtils
						.getDoubleExceptNull((Double) used[1]);
			}
		}

		return totalUsedAmount;
	}

	/**
	 * 检查商品明细中的数量是否大于当前余量(电子化手册)
	 * 
	 * @param commInfo
	 *            料件列表
	 * @param contract
	 *            合同备案表头
	 * @param isMaterial
	 *            true代表料件，false代表成品
	 * @param impExpType
	 *            单据类型
	 * @param tradeCode
	 *            贸易方式编码
	 * @return
	 */
	public String checkCurrentRemainAmount(
			List<BaseCustomsDeclarationCommInfo> commInfo, Contract contract,
			boolean isMaterial, int impExpType, String tradeCode) {
		String str = "";

		boolean isTrue = false;// 是否存在数量大于当前余量的
		str = "报关单商品序号:";
		// 进口报关单，且报关单类型不等于海关批准内销的报关单，进行控制
		if (commInfo == null) {
			return str;
		}

		long b = System.currentTimeMillis();
		Integer seqNum = null;
		// 当前可用余量
		Double currentRemain = null;
		// 合同定量
		Double contractAmount = null;
		for (int i = 0; i < commInfo.size(); i++) {
			BaseCustomsDeclarationCommInfo customsDeclarationCommInfo = commInfo
					.get(i);
			seqNum = customsDeclarationCommInfo.getCommSerialNo();
			if (isMaterial) {

				// 海关批准内销
				if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {
					double[] amounts = this.getRemainNum(contract, seqNum,
							null, null);
					currentRemain = amounts[0];
				}

				ContractImg img = contractDao.findContractImgByEmsNoSeqNum(
						contract.getEmsNo(), seqNum);
				if (img == null) {
					return null;
				}

				// 合同定量
				contractAmount = CommonUtils.getDoubleExceptNull(img
						.getAmount());

				// 全部(已生效+未生效)料件进口数量
				double allDirectImport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.DIRECT_IMPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)料件内销数量
				double alllefovMateriaImport = contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.MATERIAL_DOMESTIC_SALES,
								new String[] { "0644", "0245" },
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效)料件内销数量
				double allEffecMateriaImport = contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.MATERIAL_DOMESTIC_SALES,
								new String[] { "0644", "0245" },
								contract.getEmsNo(), null, null, 0);

				// 全部(已生效+未生效)余料结转进口数
				double allRemainForwardImport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.REMAIN_FORWARD_IMPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)余料结转出口数
				double allRemainForwardExport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
								ImpExpType.REMAIN_FORWARD_EXPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)转厂进口数量
				double allTransferFactoryImport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.TRANSFER_FACTORY_IMPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)退料退还出口数量(退料退还);
				double allExchangeExport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0300", "0700" }, contract.getEmsNo(),
								null, null, -1);
				// 全部(已生效+未生效)退料退还进口数量(退料退还);
				double allExchangeImport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.DIRECT_IMPORT, new String[] {
										"0300", "0700" }, contract.getEmsNo(),
								null, null, -1);
				// 全部(已生效+未生效)退料退还出口数量(退料复出);
				double allBackExportNotImport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0664", "0265" }, contract.getEmsNo(),
								null, null, -1);

				if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {// 可退料出口的数量
					currentRemain = allDirectImport + allTransferFactoryImport
							+ allRemainForwardImport - allExchangeExport
							- allBackExportNotImport - allRemainForwardExport;
					// - this.getProductUsedAmount(img));
				} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {// 可余料转出的数量
					currentRemain = allDirectImport + allTransferFactoryImport
							+ allRemainForwardImport - allExchangeExport
							- allBackExportNotImport - allRemainForwardExport
							- allEffecMateriaImport
							- this.getProductUsedAmount(img);
				} else if (impExpType == ImpExpType.DIRECT_IMPORT
						&& tradeCode != null
						&& (("0300").equals(tradeCode) || ("0700")
								.equals(tradeCode))) {// 可退料出口
					System.out.println("=====可退料出口====");
					currentRemain = allExchangeExport - allExchangeImport;
				} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES
						&& tradeCode != null
						&& (("0644").equals(tradeCode) || ("0245")
								.equals(tradeCode))) {// 海关批准内销料件内销
					currentRemain = allDirectImport + allTransferFactoryImport
							+ allRemainForwardImport - allExchangeExport
							- allBackExportNotImport - alllefovMateriaImport
							- this.getProductUsedAmount(img);

				} else {// 可直接进口/可转厂进口数量/可余料结转转入数量
					currentRemain = contractAmount
							- (allDirectImport + allTransferFactoryImport
									+ allRemainForwardImport - allExchangeExport);
				}
			} else {
				ContractExg exg = contractDao.findContractExgByEmsNoSeqNum(
						contract.getEmsNo(), seqNum);
				if (exg == null) {
					return null;
				}
				// 合同定量
				contractAmount = CommonUtils.getDoubleExceptNull(exg
						.getExportAmount());
				CompanyOther other = CommonUtils.getOther();

				// 全部(已生效+未生效)成品出口数量
				double allDirectExport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
								ImpExpType.DIRECT_EXPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)转厂出口数量
				double allTransferFactoryExport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
								ImpExpType.TRANSFER_FACTORY_EXPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)成品退厂返工数量
				double allBackFactoryRework = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
								ImpExpType.BACK_FACTORY_REWORK, null,
								contract.getEmsNo(), null, null, -1);
				// 全部(已生效+未生效)成品返工复出数量
				double allReworkExport = this.contractCavDao
						.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
								ImpExpType.REWORK_EXPORT, null,
								contract.getEmsNo(), null, null, -1);
				// 可退厂返工量
				if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
					currentRemain = allDirectExport + allTransferFactoryExport
							- allBackFactoryRework + allReworkExport;
				}
				// 可返工复出量
				else if (impExpType == ImpExpType.REWORK_EXPORT) {
					currentRemain = allBackFactoryRework - allReworkExport;
				}
				// 其他
				else {
					if (other == null || !other.getIsCludeReturn()) {
						currentRemain = contractAmount
								- (allDirectExport + allTransferFactoryExport);
					} else {
						currentRemain = contractAmount
								- (allDirectExport + allTransferFactoryExport
										- allBackFactoryRework + allReworkExport);
					}
				}
			}

			currentRemain = currentRemain
					+ CommonUtils
							.getDoubleExceptNull(customsDeclarationCommInfo
									.getCommAmount());

			double commAmount = customsDeclarationCommInfo.getCommAmount();

			if (CommonUtils.getDoubleByDigit(commAmount, 5) > CommonUtils
					.getDoubleByDigit(currentRemain, 5)) {
				str += customsDeclarationCommInfo.getCommSerialNo() + ",";
				isTrue = true;
			}
		}

		if (impExpType == ImpExpType.BACK_FACTORY_REWORK) {
			str += "数量不能大于可退厂返工量";
		} else if (impExpType == ImpExpType.DIRECT_IMPORT
				&& (tradeCode.equals("0300") || tradeCode.equals("0700"))) {
			str += "数量不能大于可退料退还进口数";
		} else {
			str += "数量不能大于当前余量";
		}

		long e = System.currentTimeMillis();
		System.out.println("for 使用时间：" + (e - b));

		if (isTrue) {
			return str;
		} else {
			return "";
		}
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
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return BcsContractExeInfo 存放合同成品的合同定量、合同余量、当前余量资料
	 */
	public BcsContractExeInfo findBcsContractExeInfo(boolean isMaterial,
			int impExpType, String tradeCode, Contract contract,
			Integer seqNum, String customsEnvelopBillNo) {

		BcsContractExeInfo info = new BcsContractExeInfo();

		if (isMaterial) {

			// 海关批准内销
			if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES) {

				double[] amounts = this.getRemainNum(contract, seqNum, null,
						null);

				info.setCurrentRemain(amounts[0]);

				info.setCanInSell(amounts[1]);

				return info;
			}

			ContractImg img = contractDao.findContractImgByEmsNoSeqNum(
					contract.getEmsNo(), seqNum);

			if (img == null) {

				return info;

			}

			/**
			 * 合同定量
			 */
			info.setContractAmount(img.getAmount() == null ? 0.0 : img
					.getAmount());

			/**
			 * 已生效料件进口数量
			 */
			double effectiveDirectImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null,
							contract.getEmsNo(), null, null);
			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double effectiveRemainForwardImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null,
							contract.getEmsNo(), null, null);
			/**
			 * 全部(已生效+未生效)余料结转出口数
			 */
			// double effectiveRemainForwardExport = this.contractCavDao
			// .findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
			// ImpExpType.REMAIN_FORWARD_EXPORT, null, contract
			// .getEmsNo(), null, null);
			/**
			 * 已生效转厂进口数量
			 */
			double effectiveTransferFactoryImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							contract.getEmsNo(), null, null);
			/**
			 * 已生效料件退换出口数量
			 */
			double effectiveExchangeExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, contract.getEmsNo(),
							null, null);
			/**
			 * 全部(已生效+未生效)料件进口数量
			 */
			double allDirectImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)料件内销数量
			 */
			double alllefovMateriaImport = contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
									"0644", "0245" }, contract.getEmsNo(),
							null, null, -1);
			/**
			 * 全部(已生效)料件内销数量
			 */
			double allEffecMateriaImport = contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.MATERIAL_DOMESTIC_SALES, new String[] {
									"0644", "0245" }, contract.getEmsNo(),
							null, null, 0);

			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double allRemainForwardImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)余料结转出口数
			 */
			double allRemainForwardExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REMAIN_FORWARD_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂进口数量
			 */
			double allTransferFactoryImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还出口数量(退料退还);
			 */
			double allExchangeExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, contract.getEmsNo(),
							null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还进口数量(退料退还);
			 */
			double allExchangeImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, new String[] { "0300",
									"0700" }, contract.getEmsNo(), null, null,
							-1);
			/**
			 * 全部(已生效+未生效)退料退还出口数量(退料复出);
			 */
			double allBackExportNotImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0664", "0265" }, contract.getEmsNo(),
							null, null, -1);
			/**
			 * 可退料出口的数量 = 直接进口 + 转厂进口+ 余料结转进口 - 料件退换 - 退料出口 - 余料结转出口
			 */
			if (impExpType == ImpExpType.BACK_MATERIEL_EXPORT) {
				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- allRemainForwardExport);
				// - this.getProductUsedAmount(img));

			} else if (impExpType == ImpExpType.REMAIN_FORWARD_EXPORT) {// 可余料转出的数量

				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- allRemainForwardExport - allEffecMateriaImport
						- this.getProductUsedAmount(img));

			} else if (impExpType == ImpExpType.DIRECT_IMPORT
					&& tradeCode != null
					&& (("0300").equals(tradeCode) || ("0700")
							.equals(tradeCode))) {// 可退料出口

				System.out.println("=====可退料出口====");

				info.setCurrentRemain(allExchangeExport - allExchangeImport);

			} else if (impExpType == ImpExpType.MATERIAL_DOMESTIC_SALES
					&& tradeCode != null
					&& (("0644").equals(tradeCode) || ("0245")
							.equals(tradeCode))) {// 海关批准内销料件内销

				info.setCurrentRemain(allDirectImport
						+ allTransferFactoryImport + allRemainForwardImport
						- allExchangeExport - allBackExportNotImport
						- alllefovMateriaImport
						- this.getProductUsedAmount(img));

			} else {// 可直接进口/可转厂进口数量/可余料结转转入数量

				info.setContractRemain(info.getContractAmount()
						- (effectiveDirectImport
								+ effectiveTransferFactoryImport
								+ effectiveRemainForwardImport - effectiveExchangeExport));
				// -effectiveRemainForwardExport
				info.setCurrentRemain(info.getContractAmount()
						- (allDirectImport + allTransferFactoryImport
								+ allRemainForwardImport - allExchangeExport));
				// -allRemainForwardExport
			}

		} else {

			ContractExg exg = contractDao.findContractExgByEmsNoSeqNum(
					contract.getEmsNo(), seqNum);

			if (exg == null) {
				return info;
			}

			/**
			 * 合同定量
			 */
			info.setContractAmount(exg.getExportAmount() == null ? 0.0 : exg
					.getExportAmount());
			/**
			 * 已生效成品出口数量
			 */
			double effectiveDirectExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null,
							contract.getEmsNo(), null, null);
			/**
			 * 已生效转厂出口数量
			 */
			double effectiveTransferFactoryExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							contract.getEmsNo(), null, null);
			/**
			 * 已生效成品退厂返工数量
			 */
			double effectiveBackFactoryRework = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null,
							contract.getEmsNo(), null, null);

			/**
			 * 已生效成品返工复出数量
			 */
			double effectiveReworkExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null,
							contract.getEmsNo(), null, null);

			CompanyOther other = CommonUtils.getOther();

			if (other == null || !other.getIsCludeReturn()) {

				info.setContractRemain(info.getContractAmount()
						- (effectiveDirectExport + effectiveTransferFactoryExport));

			} else {

				info.setContractRemain(info.getContractAmount()
						- (effectiveDirectExport
								+ effectiveTransferFactoryExport
								- effectiveBackFactoryRework + effectiveReworkExport));
			}

			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null,
							contract.getEmsNo(), null, null, -1);
			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = this.contractCavDao
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
				} else {
					info.setCurrentRemain(info.getContractAmount()
							- (allDirectExport + allTransferFactoryExport
									- allBackFactoryRework + allReworkExport));
				}
			}
		}
		// 关封数量
		double customsNum = contractDao.getCustomsNum(contract.getEmsNo(),
				isMaterial, seqNum, customsEnvelopBillNo);

		info.setCustomsNum(customsNum);

		// 转厂数量
		double transferNum = contractDao.getTransferNum(isMaterial, seqNum,
				contract.getEmsNo(), ProjectType.BCS, customsEnvelopBillNo);

		// 关封余量
		info.setCustomsRemain(CommonUtils.getDoubleByDigit(customsNum
				- transferNum, 5));

		info.setContractAmount(CommonUtils.getDoubleByDigit(
				info.getContractAmount(), 5));

		info.setContractRemain(CommonUtils.getDoubleByDigit(
				info.getContractRemain(), 5));

		info.setCurrentRemain(CommonUtils.getDoubleByDigit(
				info.getCurrentRemain(), 5));

		return info;

	}

	/**
	 * 余料=直接进口+结转进口+余料结转进口-退运出口(进料料件复出)-成品使用量-料件内销 【可内销数量】=【余料】-未生效【海关批准内销】报关单
	 * 
	 * @return
	 */
	private double[] getRemainNum(Contract contract, Integer seqNum,
			Date beginDate, Date endDate) {

		// 直接进口
		Double direct = contractCavDao.getImpExpTypeNum(contract.getEmsNo(),
				seqNum, beginDate, endDate, ImpExpFlag.IMPORT,
				ImpExpType.DIRECT_IMPORT, null,
				CustomsDeclarationState.EFFECTIVED);

		// 结转进口
		Double transfer = contractCavDao.getImpExpTypeNum(contract.getEmsNo(),
				seqNum, beginDate, endDate, ImpExpFlag.IMPORT,
				ImpExpType.TRANSFER_FACTORY_IMPORT, null,
				CustomsDeclarationState.EFFECTIVED);

		// 余料结转进口
		Double remainTransfer = contractCavDao.getImpExpTypeNum(
				contract.getEmsNo(), seqNum, beginDate, endDate,
				ImpExpFlag.IMPORT, ImpExpType.REMAIN_FORWARD_IMPORT, null,
				CustomsDeclarationState.EFFECTIVED);

		// 退运出口(进料料件复出)
		Double backMaterialReturn = contractCavDao.getImpExpTypeNum(
				contract.getEmsNo(), seqNum, beginDate, endDate,
				ImpExpFlag.EXPORT, ImpExpType.BACK_MATERIEL_EXPORT,
				new String[] { "0265", "0664" },
				CustomsDeclarationState.EFFECTIVED);

		// 料件内销
		Double internalAmount = contractCavDao.getImpExpTypeNum(
				contract.getEmsNo(), seqNum, beginDate, endDate,
				ImpExpFlag.IMPORT, ImpExpType.MATERIAL_DOMESTIC_SALES,
				new String[] { "0644", "0245" },
				CustomsDeclarationState.EFFECTIVED);

		// 成品耗用
		Double produceUseAmount = this.getProduceUseAmount(contract, seqNum,
				beginDate, endDate);

		direct = direct == null ? 0.0 : direct;

		transfer = transfer == null ? 0.0 : transfer;

		remainTransfer = remainTransfer == null ? 0.0 : remainTransfer;

		backMaterialReturn = backMaterialReturn == null ? 0.0
				: backMaterialReturn;

		internalAmount = internalAmount == null ? 0.0 : internalAmount;

		produceUseAmount = produceUseAmount == null ? 0.0 : produceUseAmount;

		// 余料=直接进口+结转进口+余料结转进口-退运出口(进料料件复出)-成品使用量-料件内销
		Double remainAmount = direct + transfer + remainTransfer
				- backMaterialReturn - produceUseAmount - internalAmount;

		// 未生效料件内销
		Double unInternalAmount = contractCavDao.getImpExpTypeNum(
				contract.getEmsNo(), seqNum, beginDate, endDate,
				ImpExpFlag.IMPORT, ImpExpType.MATERIAL_DOMESTIC_SALES,
				new String[] { "0644", "0245" },
				CustomsDeclarationState.NOT_EFFECTIVED);

		unInternalAmount = unInternalAmount == null ? 0.0 : unInternalAmount;

		// 【可内销数量】=【余料】-未生效【海关批准内销】报关单
		Double canAmount = remainAmount - unInternalAmount;

		return new double[] { remainAmount, canAmount };
	}

	private Double getProduceUseAmount(Contract contract, Integer seqNum,
			Date beginDate, Date endDate) {

		// 根据料件序号查询有哪些成品耗用到改料件
		List<ContractBom> boms = contractDao.findContractBom(contract.getId(),
				seqNum);

		if (boms == null) {
			return 0.0;
		}
		for (int i = 0; i < boms.size(); i++) {
			ContractBom bom = boms.get(i);
			System.out.println("bom.getContractExg().getSeqNum()===="
					+ bom.getContractExg().getSeqNum());
		}

		// 总出口量 = 报关单直接出口量+转厂出口-退厂返工量+返工复出数
		// 累加 计算料件成品耗用量
		Double totalUse = 0.0;

		Double totalExport = null;

		Double direct = null;

		Double transfer = null;

		Double backFactory = null;

		Double reworkExport = null;

		ContractBom bom = null;

		Map<String, Double> mapCommAmount = getImpExpCommAmount(
				contract.getEmsNo(), beginDate, endDate, ImpExpFlag.EXPORT,
				CustomsDeclarationState.EFFECTIVED);

		for (int i = 0; i < boms.size(); i++) {

			bom = boms.get(i);

			// // 成品出口数量
			// direct = contractCavDao.getImpExpTypeNum(contract.getEmsNo(), bom
			// .getContractExg().getSeqNum(), beginDate, endDate,
			// ImpExpFlag.EXPORT, ImpExpType.DIRECT_EXPORT, null,
			// CustomsDeclarationState.EFFECTIVED);
			direct = mapCommAmount.get(bom.getContractExg().getSeqNum() + "/"
					+ ImpExpType.DIRECT_EXPORT);
			// 转厂出口数量
			// transfer = contractCavDao.getImpExpTypeNum(contract.getEmsNo(),
			// bom
			// .getContractExg().getSeqNum(), beginDate, endDate,
			// ImpExpFlag.EXPORT, ImpExpType.TRANSFER_FACTORY_EXPORT,
			// null, CustomsDeclarationState.EFFECTIVED);
			transfer = mapCommAmount.get(bom.getContractExg().getSeqNum() + "/"
					+ ImpExpType.TRANSFER_FACTORY_EXPORT);

			// 退厂返工数量
			// backFactory =
			// contractCavDao.getImpExpTypeNum(contract.getEmsNo(),
			// bom.getContractExg().getSeqNum(), beginDate, endDate,
			// ImpExpFlag.IMPORT, ImpExpType.BACK_FACTORY_REWORK, null,
			// CustomsDeclarationState.EFFECTIVED);
			backFactory = mapCommAmount.get(bom.getContractExg().getSeqNum()
					+ "/" + ImpExpType.BACK_FACTORY_REWORK);

			// 返工复出数量
			// reworkExport =
			// contractCavDao.getImpExpTypeNum(contract.getEmsNo(),
			// bom.getContractExg().getSeqNum(), beginDate, endDate,
			// ImpExpFlag.EXPORT, ImpExpType.REWORK_EXPORT, null,
			// CustomsDeclarationState.EFFECTIVED);
			reworkExport = mapCommAmount.get(bom.getContractExg().getSeqNum()
					+ "/" + ImpExpType.REWORK_EXPORT);

			direct = direct == null ? 0.0 : direct;

			transfer = transfer == null ? 0.0 : transfer;

			backFactory = backFactory == null ? 0.0 : backFactory;

			reworkExport = reworkExport == null ? 0.0 : reworkExport;

			// 总出口量 = 报关单直接出口量+转厂出口-退厂返工量+返工复出数
			totalExport = direct + transfer - backFactory + reworkExport;

			// System.out.println("当前bom id：" +
			// bom.getContractExg().getSeqNum());
			// System.out.println(direct);
			// System.out.println(transfer);
			// System.out.println(backFactory);
			// System.out.println(reworkExport);
			// System.out.println("当前bom出口数量" + totalExport);
			// System.out.println("当前bom出口耗用量" + totalExport *
			// (bom.getUnitWaste()/(1 -
			// CommonUtils.getDoubleExceptNull(bom.getWaste()/100))));
			// System.out.println("前总耗用量" + totalUse);

			// 累加计算料件成品耗用量 = 总出口量 * (单耗/(1-损耗/100))
			totalUse = totalUse
					+ (totalExport * (bom.getUnitWaste() / (1 - CommonUtils
							.getDoubleExceptNull(bom.getWaste() / 100))));
			//
			// System.out.println("总耗用量" + totalUse);
			// System.out.println("-------------------------");
		}

		return totalUse;
	}

	private Map<String, Double> getImpExpCommAmount(String emsNo,
			Date beginDate, Date endDate, Integer impExpFlag, Integer state) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = contractCavDao.getImpExpByTypeNum(emsNo, beginDate,
				endDate, impExpFlag, state);
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			obj[0] = obj[0] == null ? "" : obj[0].toString();// 商品序号
			obj[1] = obj[1] == null ? "" : obj[1].toString();// 进出口类型
			obj[2] = obj[2] == null ? "0.0" : obj[2].toString();// 数量
			String key = obj[0].toString() + "/" + obj[1].toString();
			map.put(key, NumberUtils.toDouble(obj[2].toString()));
		}
		return map;
	}

	/**
	 * 专为转厂申请表服务的方法
	 * 
	 * @param isMaterial
	 * @param contract
	 * @param seqNum
	 * @return
	 */
	public BcsContractExeInfo findBcsContractExeInfo2(boolean isMaterial,
			String emsNo, Integer seqNum) {
		BcsContractExeInfo info = new BcsContractExeInfo();
		if (isMaterial) {
			/**
			 * 已生效料件进口数量
			 */
			double effectiveDirectImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null, emsNo, null, null);
			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double effectiveRemainForwardImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
							null, null);
			/**
			 * 已生效转厂进口数量
			 */
			double effectiveTransferFactoryImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
							null, null);
			/**
			 * 已生效料件退换出口数量
			 */
			double effectiveExchangeExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, emsNo, null, null);
			/**
			 * 全部(已生效+未生效)料件进口数量
			 */
			double allDirectImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.DIRECT_IMPORT, null, emsNo, null, null,
							-1);

			/**
			 * 全部(已生效+未生效)余料结转进口数
			 */
			double allRemainForwardImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.REMAIN_FORWARD_IMPORT, null, emsNo,
							null, null, -1);

			/**
			 * 全部(已生效+未生效)转厂进口数量
			 */
			double allTransferFactoryImport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.TRANSFER_FACTORY_IMPORT, null, emsNo,
							null, null, -1);
			/**
			 * 全部(已生效+未生效)退料退还出口数量(退料退还);
			 */
			double allExchangeExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
									"0300", "0700" }, emsNo, null, null, -1);

			// 可直接进口/可转厂进口数量/可余料结转转入数量
			info.setContractRemain((effectiveDirectImport // +
															// effectiveTransferFactoryImport
					+ effectiveRemainForwardImport - effectiveExchangeExport));
			// -effectiveRemainForwardExport
			info.setCurrentRemain((allDirectImport // + allTransferFactoryImport
					+ allRemainForwardImport - allExchangeExport));
			// -allRemainForwardExport
			// 不计算转厂数
			info.setApplyRemain((allDirectImport // + allTransferFactoryImport
					+ allRemainForwardImport - allExchangeExport));

		} else {

			/**
			 * 已生效成品出口数量
			 */
			double effectiveDirectExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, emsNo, null, null);
			/**
			 * 已生效转厂出口数量
			 */
			double effectiveTransferFactoryExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
							null, null);
			/**
			 * 已生效成品退厂返工数量
			 */
			double effectiveBackFactoryRework = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, emsNo, null,
							null);
			/**
			 * 已生效成品返工复出数量
			 */
			double effectiveReworkExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, emsNo, null, null);
			// 成品出口总量
			info.setContractRemain((effectiveDirectExport // +effectiveTransferFactoryExport
					- effectiveBackFactoryRework + effectiveReworkExport));
			/**
			 * 全部(已生效+未生效)成品出口数量
			 */
			double allDirectExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.DIRECT_EXPORT, null, emsNo, null, null,
							-1);
			/**
			 * 全部(已生效+未生效)转厂出口数量
			 */
			double allTransferFactoryExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.TRANSFER_FACTORY_EXPORT, null, emsNo,
							null, null, -1);
			/**
			 * 全部(已生效+未生效)成品退厂返工数量
			 */
			double allBackFactoryRework = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.IMPORT,
							ImpExpType.BACK_FACTORY_REWORK, null, emsNo, null,
							null, -1);
			/**
			 * 全部(已生效+未生效)成品返工复出数量
			 */
			double allReworkExport = this.contractCavDao
					.findCommInfoTotalAmount(seqNum, ImpExpFlag.EXPORT,
							ImpExpType.REWORK_EXPORT, null, emsNo, null, null,
							-1);

			info.setCurrentRemain((allDirectExport // + allTransferFactoryExport
					- allBackFactoryRework + allReworkExport));
			info.setApplyRemain((allDirectExport // + allTransferFactoryExport
					- allBackFactoryRework + allReworkExport));

		}
		return info;
	}

	@Override
	protected String getQPBGDXmlPath() {
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		if (parameter != null) {
			return parameter.getLoadQPDataXmlDir();
		}
		return "";
	}

	@Override
	protected CspParameterSet getCspParameterSet() {
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		return parameter;
	}

}
