/*
 * Created on 2005-4-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.contractcav.logic;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.TempBomFilingRealityCompareBill;
import com.bestway.bcs.contract.entity.TempContractCavDomesticPurchaseBill;
import com.bestway.bcs.contractcav.dao.ContractCavDao;
import com.bestway.bcs.contractcav.entity.BcsCustomsDeclarationCav;
import com.bestway.bcs.contractcav.entity.BcsQuantityCavType;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcs.contractcav.entity.ContractCavTotalValue;
import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcs.contractcav.entity.ContractUnitWasteCav;
import com.bestway.bcs.contractcav.entity.TempBcsExgImgCav;
import com.bestway.bcs.contractcav.entity.TempContractCheckCav;
import com.bestway.bcs.contractcav.entity.TempContractConsumptionUnitWasteCav;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.message.logic.BcsMessageLogic;
import com.bestway.bcus.custombase.entity.parametercode.Deduc;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.EquipMode;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.lowagie.tools.arguments.OptionArgument.Entry;

/**
 * checked by kcb 08-09-13 合同核销业务逻辑层
 * 
 * @author Administrator
 */
public class ContractCavLogic {
	/**
	 * 合同DAO
	 */
	private ContractDao contractDao;
	/**
	 * 合同核销DAO
	 */
	private ContractCavDao contractCavDao;
	/**
	 * 电子化手册合同报文发送及接受业务逻辑层
	 */
	private BcsMessageLogic bcsMessageLogic = null;

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
	 * 获取contractCavDao
	 * 
	 * @return contractCavDao.
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
	 * 获取bcsMessageLogic
	 */
	public BcsMessageLogic getBcsMessageLogic() {
		return bcsMessageLogic;
	}

	/**
	 * 设置bcsMessageLogic
	 * 
	 * @param bcsMessageLogic
	 */
	public void setBcsMessageLogic(BcsMessageLogic bcsExportMessageLogic) {
		this.bcsMessageLogic = bcsExportMessageLogic;
	}

	/**
	 * 重新计算自用核销表
	 * 
	 * @param contract
	 *            合同备案表头
	 */
	public void reMakeSelfuseContractCav(String emsNo, String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在重新计算自用核销表");
		}
		Contract contract = this.contractDao.findExingContractByEmsNo(emsNo);
		if (contract == null) {
			throw new RuntimeException("没有找到正在执行的手册号是" + emsNo + "的手册");
		}
		ContractCav contractCav = this.contractCavDao.findContractCav(emsNo,
				false);
		if (contractCav == null) {
			throw new RuntimeException("自用核销料件为空");
		}
		if (info != null) {
			info.setMethodName("正在删除旧的自用核销表资料");
		}
		this.contractCavDao.deleteCustomsDeclarationCav(contractCav);
		this.contractCavDao.deleteContractImgCav(contractCav);
		this.contractCavDao.deleteContractExgCav(contractCav);
		this.contractCavDao.deleteContractBomCav(contractCav);
		this.contractCavDao.delete(contractCav);
		cavContract(contract, false, info);
	}

	/**
	 * 重新计算海关核销表
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param isOnlyHead
	 *            是否只是表头
	 */
	public void reGetCustomsContractCav(String emsNo, boolean isOnlyHead,
			String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (isOnlyHead) {
			ContractCav customsContractCav = this.contractCavDao
					.findContractCav(emsNo, true);
			ContractCav selfContractCav = this.contractCavDao.findContractCav(
					emsNo, false);
			if (selfContractCav == null) {
				throw new RuntimeException("自用核销料件为空");
			}
			if (customsContractCav == null) {
				customsContractCav = new ContractCav();
				try {
					PropertyUtils.copyProperties(customsContractCav,
							selfContractCav);
				} catch (Exception e) {
					e.printStackTrace();
				}
				customsContractCav.setId(null);
			} else {
				String id = customsContractCav.getId();
				try {
					PropertyUtils.copyProperties(customsContractCav,
							selfContractCav);
				} catch (Exception e) {
					e.printStackTrace();
				}
				customsContractCav.setId(id);
			}
			customsContractCav.setIsCustoms(true);
			this.contractCavDao.saveContractCav(customsContractCav);
		} else {
			ContractCav contractCav = this.contractCavDao.findContractCav(
					emsNo, true);
			if (contractCav != null) {
				this.contractCavDao.deleteCustomsDeclarationCav(contractCav);
				this.contractCavDao.deleteContractImgCav(contractCav);
				this.contractCavDao.deleteContractExgCav(contractCav);
				this.contractCavDao.deleteContractBomCav(contractCav);
				// this.contractCavDao.deleteAll(this.contractCavDao
				// .findCustomsDeclarationCav(contractCav));
				// this.contractCavDao.deleteAll(this.contractCavDao
				// .findContractImgCav(contractCav));
				// this.contractCavDao.deleteAll(this.contractCavDao
				// .findContractExgCav(contractCav));
				// this.contractCavDao.deleteAll(this.contractCavDao
				// .findContractBomCav(contractCav));
				this.contractCavDao.delete(contractCav);
			}
			ContractCav selfContractCav = this.contractCavDao.findContractCav(
					emsNo, false);
			if (selfContractCav == null) {
				throw new RuntimeException("自用核销料件为空");
			}
			List<BcsCustomsDeclarationCav> lsSelfCustomsDeclarationCav = this.contractCavDao
					.findCustomsDeclarationCav(selfContractCav);
			List<ContractImgCav> lsSelfContractImg = this.contractCavDao
					.findContractImgCav(selfContractCav);
			List<ContractExgCav> lsSelfContractExg = this.contractCavDao
					.findContractExgCav(selfContractCav);
			List<ContractBomCav> lsSelfContractBom = this.contractCavDao
					.findContractBomCav(selfContractCav);
			makeCustomsContractCav(selfContractCav,
					lsSelfCustomsDeclarationCav, lsSelfContractImg,
					lsSelfContractExg, lsSelfContractBom, info);
		}
	}

	/**
	 * 核销计算(包含自用和海关)
	 * 
	 * @param contract
	 *            合同备案表头
	 */
	public void cavContract(String emsNo, String taskId) {
		Contract contract = this.contractDao.findExingContractByEmsNo(emsNo);
		if (contract == null) {
			throw new RuntimeException("没有找到正在执行的手册号是" + emsNo + "的手册");
		}
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		cavContract(contract, true, info);
	}

	// /**
	// * 核销其他计算(包含自用和海关)
	// *
	// * @param contract
	// * 合同备案表头
	// */
	// public Double cavContractValueAddRate(String emsNo, String taskId,
	// double impGoods, double expGoods, double followTax) {
	// Contract contract = this.contractDao.findExingContractByEmsNo(emsNo);
	// if (contract == null) {
	// throw new RuntimeException("没有找到正在执行的手册号是" + emsNo + "的手册");
	// }
	// ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
	// taskId);
	// // return calcContractCavImpAmount(contract, true, impGoods, expGoods,
	// // followTax);
	// return 0.0;
	// }

	/**
	 * 核销其他计算(包含自用和海关进口总金额)
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param isCustoms
	 *            自用核销、海关核销判断 true为海关核销
	 */
	private Double calcContractCavImpAmount(Contract contract) {

		/**
		 * 料件进口金额 (其中已包括余料结转进口金额)
		 */
		double directImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.DIRECT_IMPORT, ImpExpFlag.IMPORT, null);
		/**
		 * 料件转厂进口金额
		 */
		double transferFactoryImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.TRANSFER_FACTORY_IMPORT, ImpExpFlag.IMPORT,
						null);
		/**
		 * 余料结转进口金额
		 */
		double remainForwordImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.REMAIN_FORWARD_IMPORT, ImpExpFlag.IMPORT,
						null);// new
								// String[]
								// {
								// "0258",
								// "0657"
								// }
		/**
		 * 余料结转出口金额
		 */
		double remainForwordExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.REMAIN_FORWARD_EXPORT, ImpExpFlag.EXPORT,
						null);// new
								// String[]
								// {
								// "0258",
								// "0657"
								// }
		/**
		 * 退料出口金额(退换+复出) 料件退换出口
		 */
		double backMaterialExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.BACK_MATERIEL_EXPORT, ImpExpFlag.EXPORT,
						null);// new
								// String[]{"0300","0700"}
		/**
		 * 料件批准内销金额
		 */
		double internalSale = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.MATERIAL_DOMESTIC_SALES, ImpExpFlag.IMPORT,
						null);

		// 进口总值=料件进口+料件转厂+余料结转进口-退料出口（退还+复出）-余料结转出口（金额）-进料内销
		double impGood = CommonUtils.getDoubleByDigit(directImportMoney
				+ transferFactoryImportMoney + remainForwordImportMoney
				- backMaterialExportMoney - remainForwordExportMoney
				- internalSale, 5);

		return impGood;
	}

	/**
	 * 计算净重损之率
	 * 
	 * @param contract
	 *            合同备案表头
	 */
	public Double cavContractNetWeightLossRate(String emsNo, String taskId) {
		Contract contract = this.contractDao.findExingContractByEmsNo(emsNo);
		if (contract == null) {
			throw new RuntimeException("没有找到正在执行的手册号是" + emsNo + "的手册");
		}
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		return cavContractNetWeightLossRate(contract, true, info);
	}

	/**
	 * 计算净重损之率
	 * 
	 * @param contract
	 * @param isCustoms
	 * @param info
	 * @param impBackGoodsWeight
	 * @param expBackGoodsWeight
	 * @return 净重损失率
	 */
	private Double cavContractNetWeightLossRate(Contract contract,
			boolean isCustoms, ProgressInfo info) {
		if (info != null) {
			info.setMethodName("正在计算!");
		}
		String emsNo = contract.getEmsNo();
		double[] domesticSalesTotalWeights = this.contractCavDao
				.findImportDomesticSalesTotalWeights(emsNo);

		double[] impSumNetWeight = this.contractCavDao
				.findImportTotalWeights(emsNo);
		double[] expSumNetWeight = this.contractCavDao
				.findExportTotalWeights(emsNo);

		// 进口 退运货物（4561包括毛重和净重）
		double[] ImportReturnedGood = this.contractCavDao
				.findImportReturnedGoods(emsNo);

		// 进口 退运货物（4561）
		double ImportReturnedGoods = ImportReturnedGood[0];

		double domesticSalesTotalWeight = domesticSalesTotalWeights[1];
		// 进口净重
		double impNetWeight = impSumNetWeight[1] - domesticSalesTotalWeight
				- ImportReturnedGoods;

		// System.out.println("impNetWeight=" + impNetWeight);
		// 出口 退运货物（4561包括毛重和净重）
		double[] expReturnGoods = this.contractCavDao
				.findExportReturnGoods(emsNo);

		// 出口 退运货物（4561）
		double expReturnGood = expReturnGoods[1];

		// 出口净重
		double expNetWeight = expSumNetWeight[1] - expReturnGood;

		// System.out.println("expNetWeight=" + expNetWeight);

		double result = (impNetWeight - expNetWeight) / impNetWeight;

		// System.out.println("result=" + result);
		return result;
	}

	/**
	 * 核销计算 isCustoms:是否声称海关核销资料
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param isCustoms
	 *            自用核销、海关核销判断 true为海关核销
	 */
	private void cavContract(Contract contract, boolean isCustoms,
			ProgressInfo info) {
		if (info != null) {
			info.setMethodName("正在计算核销表表头");
		}
		// TODO "合同核销"
		ContractCav contractCav = new ContractCav();
		contractCav.setEmsNo(contract.getEmsNo());
		contractCav.setContractNo(contract.getImpContractNo());
		contractCav.setEndDate(contract.getEndDate());
		contractCav.setBeginDate(contract.getBeginDate());
		contractCav.setEmsApprNo(contract.getEmsApprNo());
		contractCav.setCopEmsNo(contract.getCopEmsNo());
		contractCav.setDeclareState(DeclareState.APPLY_POR);
		contractCav.setCreateDate(new Date());
		// contractCav.setCreateUser(CommonUtils.getAclUser());
		contractCav.setImpCDCount(this.contractCavDao
				.findCutomsDeclarationCountByContract(contract, true));
		/**
		 * 料件进口金额 (其中已包括余料结转进口金额)
		 */
		double directImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.DIRECT_IMPORT, ImpExpFlag.IMPORT, null);

		/**
		 * 料件转厂进口金额
		 */
		double transferFactoryImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.TRANSFER_FACTORY_IMPORT, ImpExpFlag.IMPORT,
						null);
		/**
		 * 余料结转进口金额
		 */
		double remainForwordImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.REMAIN_FORWARD_IMPORT, ImpExpFlag.IMPORT,
						null);// new
								// String[]
								// {
								// "0258",
								// "0657"
								// }
		/**
		 * 余料结转出口金额
		 */
		double remainForwordExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.REMAIN_FORWARD_EXPORT, ImpExpFlag.EXPORT,
						null);// new
								// String[]
								// {
								// "0258",
								// "0657"
								// }

		/**
		 * 余料结转出备注手册号
		 */
		String remainEmsNo = this.contractCavDao.findMemosByContract(contract,
				ImpExpType.REMAIN_FORWARD_EXPORT, ImpExpFlag.EXPORT, null);// new

		System.out.println(remainEmsNo);
		if (!"".equals(remainEmsNo)) {
			contractCav.setRemainEmsNo(remainEmsNo);
		}
		/**
		 * 退料出口金额【复出】
		 */
		double backMaterialExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.BACK_MATERIEL_EXPORT, ImpExpFlag.EXPORT,
						new String[]{"0265","0664" });
								                                  
		/**
		 * 退料出口金额【退换】
		 */
		double exchangeExportMoney = this.contractCavDao
		.findCutomsDeclarationMoneyByContract(contract,
				ImpExpType.BACK_MATERIEL_EXPORT, ImpExpFlag.EXPORT,
				new String[]{"0300","0700" });
		
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		
		/**
		 * 2014-8-19hwy咨询几个客户得知
		 * 核销料件表中，总进口总值= 直接进口 + 转厂进口 + 余料结转进口 - 退换出口【0700、0300】
		 * 所以表头总值中也需要扣减退换出口金额
		 */
		if (parameter.getCheckIsComputeReturn() != null
				&& parameter.getCheckIsComputeReturn()) {
			/**
			 * 进口总金额=料件进口+料件转厂+余料结转进口-退换出口 - 料件复出
			 */
			double impAmount = directImportMoney + transferFactoryImportMoney
					+ remainForwordImportMoney - exchangeExportMoney - backMaterialExportMoney;
			contractCav.setImpAmount(impAmount);
		} else {
			/**
			 * 2014-8-19 hwy
			 * 进口总金额=料件进口+料件转厂+余料结转进口 - 退换出口
			 */
			double impAmount = directImportMoney + transferFactoryImportMoney
					+ remainForwordImportMoney - exchangeExportMoney;
			contractCav.setImpAmount(impAmount);
		}

		contractCav.setExpCDCount(this.contractCavDao
				.findCutomsDeclarationCountByContract(contract, false));
		/**
		 * 成品出口金额
		 */
		double directExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.DIRECT_EXPORT, ImpExpFlag.EXPORT, null);
		/**
		 * 成品转厂出口金额
		 */
		double transferFactoryExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.TRANSFER_FACTORY_EXPORT, ImpExpFlag.EXPORT,
						null);
		/**
		 * 返工复出金额
		 */
		double reworkExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.REWORK_EXPORT, ImpExpFlag.EXPORT, null);

		/**
		 * 退厂返工金额
		 */
		double backFactoryReworkMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.BACK_FACTORY_REWORK, ImpExpFlag.IMPORT, null);
		/**
		 * 出口总金额=成品出口+转厂出口+返工复出-退厂返工(金额)
		 */
		double expAmount = CommonUtils.getDoubleByDigit(directExportMoney
				+ transferFactoryExportMoney + reworkExportMoney
				- backFactoryReworkMoney, 5);
		contractCav.setExpAmount(expAmount);
		/**
		 * 料件批准内销金额
		 */
		double internalSale = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(contract,
						ImpExpType.MATERIAL_DOMESTIC_SALES, ImpExpFlag.IMPORT,
						null);

		// // 进口总值=料件进口+料件转厂+余料结转进口-退料出口（退还+复出）-余料结转出口（金额）-进料内销
		// double impGood = CommonUtils.getDoubleByDigit(directImportMoney
		// + transferFactoryImportMoney + remainForwordImportMoney
		// - backMaterialExportMoney - remainForwordExportMoney
		// - internalSale, 5);
		// // 出口总值=成品出口+转厂出口+返工复出-退厂返工
		// double expGood = CommonUtils.getDoubleByDigit(directExportMoney
		// + transferFactoryExportMoney + reworkExportMoney
		// - backFactoryReworkMoney, 5);
		//
		// double ValueAddRate = 0.0;
		// if (impGood != 0) {
		// ValueAddRate = CommonUtils.getDoubleByDigit((expGood - impGood)
		// / impGood, 5);
		// }
		// /**
		// * 计算增值率
		// */
		// contractCav.setValueAddedRate(ValueAddRate);

		// 余料总金额=进口总金额-出口总金额-内销总金额---2009.4.8 edited by cjb
		// double remainMoney = impAmount - expAmount - internalSale;
		// 余料总金额=余料转出报关单金额---2009.4.8 edited by cjb
		contractCav.setRemainMoney(remainForwordExportMoney);// by kcb
		contractCav.setInternalSale(internalSale);
		contractCav.setCurr(contract.getCurr());
		contractCav.setCompany(contract.getCompany());
		contractCav.setIsCustoms(false);
		// 设置小数位
		Integer decimalLength = 2;
		if (parameter != null && parameter.getReportDecimalLength() != null) {
			decimalLength = parameter.getReportDecimalLength();
		}
		System.out.println("Bom 计算：取小数位=" + decimalLength);
		CommonUtils.formatDouble(contractCav, decimalLength);

		this.contractCavDao.saveContractCav(contractCav);
		this.cavCustomsDeclaration(contractCav, info);
		/**
		 * 先计算成品生成的资料下两步计算用
		 */
		System.out.println("contractCav.emsNo" + contractCav.getEmsNo());
		System.out.println("contract.getId()" + contract.getId());
		System.out.println("将要计算成品表!!!");
		List<ContractExgCav> lsCustomsContractExg = this.cavContractExg(
				contractCav, contract.getId(), info);
		System.out.println("将要计算料件表!!!");
		this.cavContractImg(contractCav, contract.getId(), info);
		System.out.println("将要计算单耗表!!!");
		this.cavContractBom(contractCav, lsCustomsContractExg, info,contract.getEquipMode());
		System.out.println("单耗表计算完成!!!");
		if (isCustoms) {
			List<BcsCustomsDeclarationCav> lsSelfCustomsDeclarationCav = this.contractCavDao
					.findCustomsDeclarationCav(contractCav);
			List<ContractImgCav> lsSelfContractImg = this.contractCavDao
					.findContractImgCav(contractCav);
			List<ContractExgCav> lsSelfContractExg = this.contractCavDao
					.findContractExgCav(contractCav);
			List<ContractBomCav> lsSelfContractBom = this.contractCavDao
					.findContractBomCav(contractCav);
			makeCustomsContractCav(contractCav, lsSelfCustomsDeclarationCav,
					lsSelfContractImg, lsSelfContractExg, lsSelfContractBom,
					info);
		}
	}

	/**
	 * 把海关用的核销数据保存到数据库。（包含核销单头、成品、料件、BOM）
	 * 
	 * @param selfContractCav
	 *            自用核销表
	 * @param lsCustomsDeclarationCav
	 *            存放BcsCustomsDeclarationCav的List
	 * @param lsSelfContractImg
	 *            存放ContractImgCav的List
	 * @param lsSelfContractExg
	 *            存放ContractExgCav的List
	 * @param lsSelfContractBom
	 *            存放ContractBomCav的List
	 */
	private void makeCustomsContractCav(ContractCav selfContractCav,
			List<BcsCustomsDeclarationCav> lsCustomsDeclarationCav,
			List<ContractImgCav> lsSelfContractImg,
			List<ContractExgCav> lsSelfContractExg,
			List<ContractBomCav> lsSelfContractBom, ProgressInfo info) {
		ContractCav customsContractCav = new ContractCav();
		try {
			PropertyUtils.copyProperties(customsContractCav, selfContractCav);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customsContractCav.setId(null);
		customsContractCav.setIsCustoms(true);
		this.contractCavDao.saveContractCav(customsContractCav);
		if (info != null) {
			info.setMethodName("正在获取海关核销用的报关单资料");
			info.setTotalNum(lsCustomsDeclarationCav.size());
			info.setCurrentNum(0);
		}
		for (BcsCustomsDeclarationCav selfDeclarationCav : lsCustomsDeclarationCav) {
			BcsCustomsDeclarationCav bcsCustomsDeclarationCav = new BcsCustomsDeclarationCav();
			try {
				PropertyUtils.copyProperties(bcsCustomsDeclarationCav,
						selfDeclarationCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bcsCustomsDeclarationCav.setId(null);
			bcsCustomsDeclarationCav.setContractCav(customsContractCav);
			bcsCustomsDeclarationCav.setIsCustoms(true);
			this.contractCavDao.saveOrUpdate(bcsCustomsDeclarationCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("正在获取海关核销用的料件资料");
			info.setTotalNum(lsSelfContractImg.size());
			info.setCurrentNum(0);
		}
		for (ContractImgCav selfContractImgCav : lsSelfContractImg) {
			ContractImgCav customsContractImgCav = new ContractImgCav();
			try {
				PropertyUtils.copyProperties(customsContractImgCav,
						selfContractImgCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			customsContractImgCav.setId(null);
			customsContractImgCav.setContractCav(customsContractCav);
			customsContractImgCav.setIsCustoms(true);
			this.contractCavDao.saveContractImgCav(customsContractImgCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("正在获取海关核销用的成品资料");
			info.setTotalNum(lsSelfContractExg.size());
			info.setCurrentNum(0);
		}
		for (ContractExgCav selfContractExgCav : lsSelfContractExg) {
			ContractExgCav customsContractExgCav = new ContractExgCav();
			try {
				PropertyUtils.copyProperties(customsContractExgCav,
						selfContractExgCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			customsContractExgCav.setId(null);
			customsContractExgCav.setContractCav(customsContractCav);
			customsContractExgCav.setIsCustoms(true);
			this.contractCavDao.saveContractExgCav(customsContractExgCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("正在获取海关核销用的单耗资料");
			info.setTotalNum(lsSelfContractBom.size());
			info.setCurrentNum(0);
		}
		for (ContractBomCav selfContractBomCav : lsSelfContractBom) {
			ContractBomCav customsContractBomCav = new ContractBomCav();
			try {
				PropertyUtils.copyProperties(customsContractBomCav,
						selfContractBomCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			customsContractBomCav.setId(null);
			customsContractBomCav.setContractCav(customsContractCav);
			customsContractBomCav.setIsCustoms(true);
			this.contractCavDao.saveContractBomCav(customsContractBomCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
	}

	/**
	 * 核销计算报关单
	 * 
	 * @param contractCav
	 *            电子手册合同核销电头
	 */
	public void cavCustomsDeclaration(ContractCav contractCav, ProgressInfo info) {
		this.contractCavDao.deleteAll(this.contractCavDao
				.findCustomsDeclarationCav(contractCav));
		List list = this.contractCavDao.findBcsCustomsDeclaration(contractCav
				.getEmsNo());
		if (info != null) {
			info.setMethodName("正在计算核销报关单的资料");
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		// List<DzscCustomsDeclarationCav> lsCustomsDeclarationCav = new
		// ArrayList<DzscCustomsDeclarationCav>();
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsDeclaration customsDeclaration = (BcsCustomsDeclaration) list
					.get(i);
			BcsCustomsDeclarationCav cav = new BcsCustomsDeclarationCav();
			cav.setContractCav(contractCav);
			cav.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			cav.setDeclarationCustoms(customsDeclaration
					.getDeclarationCustoms());
			cav.setDeclarationDate(customsDeclaration.getDeclarationDate());
			cav.setImpExpDate(customsDeclaration.getImpExpDate());
			cav.setImpExpFlag(customsDeclaration.getImpExpFlag());
			cav.setImpExpType(customsDeclaration.getImpExpType());
			cav.setModifyMark(ModifyMarkState.ADDED);
			cav.setIsCustoms(false);
			if (customsDeclaration.getTradeMode() == null) {
				throw new RuntimeException("没有找到报关单号"
						+ customsDeclaration.getCustomsDeclarationCode()
						+ "的贸易方式");
			}
			List lsDeduc = this.contractCavDao.findDeduc(customsDeclaration
					.getTradeMode().getCode(), cav.getCustomsImpExpType());
			if (lsDeduc.size() > 0) {
				cav.setDeduc((Deduc) lsDeduc.get(0));
			}
			this.contractCavDao.saveOrUpdate(cav);
			// lsCustomsDeclarationCav.add(cav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
	}

	/**
	 * 核销计算合同料件资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @param contractId
	 *            合同备案表头Id
	 */
	private void cavContractImg(ContractCav contractCav, String contractId,
			ProgressInfo info) {
		// List list = this.contractCavDao.findAllCommInfo(true, contractCav
		// .getEmsNo(), null, null);
		// 设置小数位
		Integer decimalLength = 2;
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		if (parameter != null && parameter.getReportDecimalLength() != null) {
			decimalLength = parameter.getReportDecimalLength();
		}
		List<ContractImg> list = this.contractDao
				.findContractImgByContractId(contractId);
		contractCav.setImgCount(list.size());
		if (info != null) {
			info.setMethodName("正在计算核销料件表的资料");
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		this.contractCavDao.saveContractCav(contractCav);
		// List<ContractImgCav> lsCustomsContractImg = new
		// ArrayList<ContractImgCav>();
		List<ContractExgCav> lsCustomsContractExg = this.contractCavDao
				.findContractExgCav(contractCav);
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		HashMap<String, Double> hmImgWaste = new HashMap<String, Double>();
		this.calContractImgUse(contractCav, lsCustomsContractExg, hmImgUse,
				hmImgWaste, info);

		for (ContractImg contractImg : list) {
			// String commSerialNo = list.get(i).toString();
			// ContractImg contractImg = this.contractCavDao
			// .findImgByCommSerialNo(contractCav.getEmsNo(), commSerialNo);
			if (contractImg != null) {
				ContractImgCav contractImgCav = new ContractImgCav();
				contractImgCav.setContractCav(contractCav);
				contractImgCav.setSeqNum(contractImg.getSeqNum());
				contractImgCav.setComplex(contractImg.getComplex());
				contractImgCav.setName(contractImg.getName());
				contractImgCav.setSpec(contractImg.getSpec());
				contractImgCav.setUnit(contractImg.getUnit());
				/**
				 * 进口数量 (包含料件退换进口数)
				 */
				double directImportAmount = this.contractCavDao
						.findCommInfoTotalAmount(contractImgCav.getSeqNum(),
								ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
								null, contractCav.getEmsNo(), null, null);
				contractImgCav.setDirectImport(CommonUtils.getDoubleByDigit(
						directImportAmount, 5));
				/**
				 * 进口净重 (包含料件退换)
				 */
				double directImportNetWeight = this.contractCavDao
						.findCommInfoTotalNetWeight(contractImgCav.getSeqNum(),
								ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
								null, contractCav.getEmsNo(), null, null);
				contractImgCav.setImpNetWeight(CommonUtils.getDoubleByDigit(
						directImportNetWeight, 5));
				/**
				 * 料件转厂
				 */
				contractImgCav.setTransferFactoryImport(CommonUtils
						.getDoubleByDigit(this.contractCavDao
								.findCommInfoTotalAmount(
										contractImgCav.getSeqNum(),
										ImpExpFlag.IMPORT,
										ImpExpType.TRANSFER_FACTORY_IMPORT,
										null, contractCav.getEmsNo(), null,
										null), 5));
				/**
				 * 余料进口 (余料结转进口)
				 */
				contractImgCav.setRemainImport(CommonUtils.getDoubleByDigit(
						this.contractCavDao.findCommInfoTotalAmount(
								contractImgCav.getSeqNum(), ImpExpFlag.IMPORT,
								ImpExpType.REMAIN_FORWARD_IMPORT, null,
								contractCav.getEmsNo(), null, null), 5));
				// new String[] { "0657", "0258" }
				/**
				 * 余料出口 (余料结转出口)
				 */
				contractImgCav.setRemainExport(CommonUtils.getDoubleByDigit(
						this.contractCavDao.findCommInfoTotalAmount(
								contractImgCav.getSeqNum(), ImpExpFlag.EXPORT,
								ImpExpType.REMAIN_FORWARD_EXPORT, null,
								contractCav.getEmsNo(), null, null), 5));
				/**
				 * 成品耗用
				 */
				contractImgCav.setProductWaste(CommonUtils.getDoubleByDigit(
						hmImgUse.get(contractImgCav.getSeqNum() == null ? ""
								: contractImgCav.getSeqNum().toString()), 5));
				/**
				 * 内销数量
				 */
				contractImgCav.setInternalAmount(CommonUtils.getDoubleByDigit(
						this.contractCavDao.findCommInfoTotalAmount(
								contractImgCav.getSeqNum(), null, null,
								new String[] { "0245", "0644" },
								contractCav.getEmsNo(), null, null), 5));

				/**
				 * 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
				 */
				contractImgCav.setBackExport(CommonUtils.getDoubleByDigit(
						this.contractCavDao.findCommInfoTotalAmount(
								contractImgCav.getSeqNum(), ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0265", "0664" },
								contractCav.getEmsNo(), null, null), 5));
				/**
				 * 边角料
				 */
				contractImgCav.setLeftoverMaterial(CommonUtils
						.getDoubleByDigit(hmImgWaste.get(contractImgCav
								.getSeqNum() == null ? "" : contractImgCav
								.getSeqNum().toString()), 5));
				/**
				 * 料件退换进口数
				 */
				// double exchangeImport = this.contractCavDao
				// .findCommInfoTotalAmount(contractImgCav.getSeqNum(),
				// ImpExpFlag.IMPORT, ImpExpType.DIRECT_IMPORT,
				// new String[] { "0300", "0700" }, contractCav
				// .getEmsNo(), null, null);
				/**
				 * 料件退换出口数
				 */
				double exchangeExport = this.contractCavDao
						.findCommInfoTotalAmount(contractImgCav.getSeqNum(),
								ImpExpFlag.EXPORT,
								ImpExpType.BACK_MATERIEL_EXPORT, new String[] {
										"0300", "0700" },
								contractCav.getEmsNo(), null, null);
				contractImgCav.setExchangeExport(CommonUtils.getDoubleByDigit(
						exchangeExport, 5));
				/**
				 * 进口总数量＝ 料件进口数＋ 料件转厂进口数+余料结转进口数－料件退换出口数//+ 料件退换进口数(已经在料件进口中)
				 */
				contractImgCav.setImpTotal(CommonUtils.getDoubleByDigit(
						directImportAmount
								+ (CommonUtils
										.getDoubleExceptNull(contractImgCav
												.getTransferFactoryImport()))
								+ (CommonUtils
										.getDoubleExceptNull(contractImgCav
												.getRemainImport()))
								- exchangeExport, 5));// +
				// exchangeImport
				/**
				 * 余料数量 ＝ 进口总数量1）－内销数量3）-退运出口数量（复出）－产品总耗用量2）-
				 * 余料结转出口数量(yp2008-12-19修改)
				 */
				contractImgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(
						CommonUtils.getDoubleExceptNull(contractImgCav
								.getImpTotal())
								- CommonUtils
										.getDoubleExceptNull(contractImgCav
												.getProductWaste())
								- CommonUtils
										.getDoubleExceptNull(contractImgCav
												.getBackExport())
								- CommonUtils
										.getDoubleExceptNull(contractImgCav
												.getInternalAmount())
								- CommonUtils
										.getDoubleExceptNull(contractImgCav
												.getRemainExport()), 5));
				// - (contractImgCav.getBackExport() == null ? 0.0
				// : contractImgCav.getBackExport()));
				contractImgCav.setModifyMark(ModifyMarkState.ADDED);
				contractImgCav.setIsCustoms(false);
				CommonUtils.formatDouble(contractImgCav, decimalLength);
				this.contractCavDao.saveContractImgCav(contractImgCav);
				// lsCustomsContractImg.add(contractImgCav);
				if (info != null) {
					info.setCurrentNum(info.getCurrentNum() + 1);
				}
			}
		}
	}

	public ContractExg getContractExgByContract(String contractId,
			Integer exgId, String contractNo) {
		return this.contractCavDao.getContractExgByContract(contractId, exgId,
				contractNo);
	}

	/**
	 * 核销计算合同成品资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @param contractId
	 *            合同备案表头Id
	 */
	private List<ContractExgCav> cavContractExg(ContractCav contractCav,
			String contractId, ProgressInfo info) {
		List<ContractExgCav> lsCustomsContractExg = new ArrayList<ContractExgCav>();
		// List list = this.contractCavDao.findAllCommInfo(false, contractCav
		// .getEmsNo(), null, null);
		// 设置小数位
		Integer decimalLength = 2;
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		if (parameter != null && parameter.getReportDecimalLength() != null) {
			decimalLength = parameter.getReportDecimalLength();
		}
		List<ContractExg> list = this.contractDao
				.findContractExgByParentId(contractId);
		contractCav.setExgCount(list.size());
		this.contractCavDao.saveContractCav(contractCav);
		if (info != null) {
			info.setMethodName("正在计算核销成品表的资料");
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		// List<ContractExgCav> lsCustomsContractExg = new
		// ArrayList<ContractExgCav>();
		double processTotalPrice = 0;// 加工费总价
		try {

			for (ContractExg contractExg : list) {
				// String commSerialNo = list.get(i).toString();
				// ContractExg contractExg = this.contractCavDao
				// .findExgByCommSerialNo(contractCav.getEmsNo(), commSerialNo);
				if (contractExg != null) {
					ContractExgCav contractExgCav = new ContractExgCav();

					contractExgCav.setContractCav(contractCav);
					contractExgCav.setSeqNum(contractExg.getSeqNum());
					contractExgCav.setComplex(contractExg.getComplex());
					contractExgCav.setName(contractExg.getName());
					contractExgCav.setSpec(contractExg.getSpec());
					contractExgCav.setUnit(contractExg.getUnit());
					/**
					 * 成品出口数量
					 */
					double directExportAmount = this.contractCavDao
							.findCommInfoTotalAmount(
									contractExgCav.getSeqNum(),
									ImpExpFlag.EXPORT,
									ImpExpType.DIRECT_EXPORT, null,
									contractCav.getEmsNo(), null, null);
					contractExgCav.setDirectExport(CommonUtils
							.getDoubleByDigit(directExportAmount, 5));
					/**
					 * 结转出口数量
					 */
					contractExgCav.setTransferExpAmount(CommonUtils
							.getDoubleByDigit(this.contractCavDao
									.findCommInfoTotalAmount(
											contractExgCav.getSeqNum(),
											ImpExpFlag.EXPORT,
											ImpExpType.TRANSFER_FACTORY_EXPORT,
											null, contractCav.getEmsNo(), null,
											null), 5));
					/**
					 * 退厂返工
					 */
					contractExgCav.setBackFactoryRework(CommonUtils
							.getDoubleByDigit(this.contractCavDao
									.findCommInfoTotalAmount(
											contractExgCav.getSeqNum(),
											ImpExpFlag.IMPORT,
											ImpExpType.BACK_FACTORY_REWORK,
											null, contractCav.getEmsNo(), null,
											null), 5));
					/**
					 * 返工复出
					 */
					contractExgCav.setReworkExport(CommonUtils
							.getDoubleByDigit(
									this.contractCavDao
											.findCommInfoTotalAmount(
													contractExgCav.getSeqNum(),
													ImpExpFlag.EXPORT,
													ImpExpType.REWORK_EXPORT,
													null,
													contractCav.getEmsNo(),
													null, null), 5));
					/**
					 * 出口总数量 = 成品出口数量＋ 成品转厂出口数量— 退厂返工数量+ 返工复出数量
					 */
					contractExgCav
							.setExpTotal(CommonUtils.getDoubleByDigit(
									directExportAmount
											+ (contractExgCav
													.getTransferExpAmount() == null ? 0.0
													: contractExgCav
															.getTransferExpAmount())
											- (contractExgCav
													.getBackFactoryRework() == null ? 0.0
													: contractExgCav
															.getBackFactoryRework())
											+ (contractExgCav.getReworkExport() == null ? 0.0
													: contractExgCav
															.getReworkExport()),
									5));

					contractExgCav.setModifyMark(ModifyMarkState.ADDED);
					contractExgCav.setIsCustoms(false);
					// System.out.println("成品取小数位：" + decimalLength);
					CommonUtils.formatDouble(contractExgCav, decimalLength);

					this.contractCavDao.saveContractExgCav(contractExgCav);
					lsCustomsContractExg.add(contractExgCav);
					// lsCustomsContractExg.add(contractExgCav);
					double processUnitPrice = CommonUtils
							.getDoubleExceptNull(contractExg
									.getProcessUnitPrice());// 加工费单价
					double expTotal = CommonUtils
							.getDoubleExceptNull(contractExgCav.getExpTotal());// 加工费单价
					processTotalPrice += (processUnitPrice * expTotal);
				}
				if (info != null) {
					info.setCurrentNum(info.getCurrentNum() + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contractCav.setProcessTotalPrice(CommonUtils.getDoubleByDigit(
				processTotalPrice, decimalLength));
		this.contractCavDao.saveContractCav(contractCav);
		return lsCustomsContractExg;
	}

	/**
	 * 删除合同核销料件资料
	 * 
	 * @param contractImgCav
	 *            合同核销料件
	 */
	public void deleteContractImgCav(ContractImgCav contractImgCav) {
		List list = this.contractCavDao.findContractBomCav(contractImgCav);
		for (int i = 0; i < list.size(); i++) {
			this.contractCavDao.delete(list.get(i));
		}
		this.contractCavDao.deleteContractImgCav(contractImgCav);
	}

	/**
	 * 新增合同核销BOM
	 * 
	 * @param contractCav
	 *            核销合同
	 * @param ContractImgCav
	 *            合同核销料件
	 */
	public void addContractBomCav(ContractCav contractCav, ContractImgCav img) {
		List listC = this.contractDao.findContractBomCavByImgSeqNum(
				contractCav.getId(), img.getSeqNum());
		for (int m = 0; m < listC.size(); m++) {
			ContractBomCav bom = (ContractBomCav) listC.get(m);
			ContractBomCav contractBomCav = new ContractBomCav();
			contractBomCav.setContractCav(contractCav);
			contractBomCav.setSeqMaterialNum(img.getSeqNum());
			contractBomCav.setMaterialName(img.getName());
			contractBomCav.setMaterialSpec(img.getSpec());
			List list = this.contractCavDao.findContractExgCav(
					contractCav.getId(), bom.getSeqProductNum());
			if (list.size() > 0) {
				ContractExgCav cexg = (ContractExgCav) list.get(0);
				contractBomCav.setExgExpTotalAmount(cexg.getExpTotal());
				contractBomCav.setSeqProductNum(cexg.getSeqNum());
				contractBomCav.setProductName(cexg.getName());
				contractBomCav.setProductSpec(cexg.getSpec());
			} else {
				continue;
			}
			contractBomCav.setModifyMark(ModifyMarkState.UNCHANGE);
			contractBomCav.setIsCustoms(true);
			this.contractCavDao.saveContractBomCav(contractBomCav);

		}
	}

	/**
	 * 核销计算合同BOM资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 */
	private void cavContractBom(ContractCav contractCav,
			List<ContractExgCav> lsCustomsContractExg, ProgressInfo info,
			String equipMode) {
		// List<ContractBomCav> lsCustomsContractBom = new
		// ArrayList<ContractBomCav>();
		// 设置小数位
		Integer decimalLength = 2;
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		
		int unitWasteLength =parameter.getReportDecimalLengthUnitWaste()==null?decimalLength: parameter.getReportDecimalLengthUnitWaste();
		int wasteLength = parameter.getWasteAmount()==null?decimalLength:parameter.getWasteAmount();
		int totalAmountLength = parameter.getTotalAmount()==null?decimalLength:parameter.getTotalAmount();
		
		System.out.println("计算单耗中抓取核销成品数量：" + lsCustomsContractExg.size());
		if (info != null) {
			info.setMethodName("正在计算核销单耗表的资料");
			info.setTotalNum(lsCustomsContractExg.size());
			info.setCurrentNum(0);
		}
		
		
		for (ContractExgCav contractExgCav : lsCustomsContractExg) {
			double exgAmount = contractExgCav.getExpTotal();// this.contractCavDao.findCommInfoTotalAmount(
			// contractExgCav.getSeqNum(), false, contractCav.getEmsNo(),
			// null, null);
			List<ContractBom> list = this.contractCavDao.findBomByExg(
					contractCav.getEmsNo(), contractExgCav.getSeqNum(),
					DeclareState.PROCESS_EXE);
			System.out.println("计算单耗中成品抓取BOM数量：" + list.size());
			for (ContractBom contractBom : list) {
				ContractBomCav contractBomCav = new ContractBomCav();
				contractBomCav.setContractCav(contractCav);
				contractBomCav.setSeqProductNum(contractExgCav.getSeqNum());
				contractBomCav.setProductName(contractExgCav.getName());
				contractBomCav.setProductSpec(contractExgCav.getSpec());
				contractBomCav.setSeqMaterialNum(contractBom
						.getContractImgSeqNum());
				contractBomCav.setMaterialName(contractBom.getName());
				contractBomCav.setMaterialSpec(contractBom.getSpec());
				contractBomCav.setUnitWaste(contractBom.getUnitWaste());
				contractBomCav.setWaste(CommonUtils.getDoubleByDigit(contractBom.getWaste(), unitWasteLength));
				contractBomCav.setNonMnlRatio(contractBom.getNonMnlRatio());
				if(EquipMode.PUT_ON_RECORD.equals(equipMode)){
					contractBomCav.setModifyMark(ModifyMarkState.MODIFIED);
				}else{
					contractBomCav.setModifyMark(ModifyMarkState.ADDED);
				}
				contractBomCav.setIsCustoms(false);
				contractBomCav.setExgExpTotalAmount(exgAmount);
				double imgAmount = CommonUtils
						.getDoubleByDigit(
								exgAmount
										* CommonUtils
												.getDoubleExceptNull(CommonUtils
														.getDoubleExceptNull(contractBom
																.getUnitWaste())
														/ (1 - CommonUtils
																.getDoubleExceptNull(contractBom
																		.getWaste()) / 100.0)),
								5);
//				CommonUtils.formatDouble(contractBomCav, decimalLength);
				//
				contractBomCav.setTotalAmount(CommonUtils.getDoubleByDigit(imgAmount, totalAmountLength));
				contractBomCav.setWasteAmount(CommonUtils.getDoubleByDigit(
						contractBomCav.getTotalAmount()* CommonUtils.getDoubleExceptNull(contractBom
										.getWaste()) / 100.0, 5));
				
				contractBomCav.setWasteAmount(CommonUtils.getDoubleByDigit(contractBomCav.getWasteAmount(), wasteLength));
				this.contractCavDao.saveContractBomCav(contractBomCav);
			}
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
	}

	/**
	 * 计算成品所有的料件的耗用总量
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @param lsContractExg
	 *            是List<ContractExgCav>型，合同核销成品资料
	 * @param hmImgUse
	 *            是HashMap<String, Double>型,Double存放料件的耗用总量
	 * @param hmImgWaste
	 *            是HashMap<String, Double>型,Double存放料件的损耗量
	 */
	private void calContractImgUse(ContractCav contractCav,
			List<ContractExgCav> lsContractExg,
			HashMap<String, Double> hmImgUse,
			HashMap<String, Double> hmImgWaste, ProgressInfo info) {
		if (info != null) {
			info.setMethodName("开始计算核销料件耗用和边角料");
			info.setTotalNum(lsContractExg.size());
			info.setCurrentNum(0);
		}
		Integer decimalLength = 2;
		BcsParameterSet parameter = this.contractDao.findBcsParameterSet();
		
		int unitWasteLength =parameter.getReportDecimalLengthUnitWaste()==null?decimalLength: parameter.getReportDecimalLengthUnitWaste();
		int wasteLength = parameter.getWasteAmount()==null?decimalLength:parameter.getWasteAmount();
		int totalAmountLength = parameter.getTotalAmount()==null?decimalLength:parameter.getTotalAmount();
		
		for (ContractExgCav contractExgCav : lsContractExg) {
			double exgAmount = CommonUtils.getDoubleExceptNull(contractExgCav
					.getExpTotal());
			List<ContractBom> list = this.contractCavDao.findBomByExg(
					contractCav.getEmsNo(), contractExgCav.getSeqNum(),
					DeclareState.PROCESS_EXE);
			for (ContractBom contractBom : list) {
				double unitwaste=CommonUtils.getDoubleByDigit(contractBom.getUnitWaste(), unitWasteLength);
				double imgAmount = exgAmount* CommonUtils.getDoubleExceptNull(
						CommonUtils.getDoubleExceptNull(unitwaste)
						/ (1 - CommonUtils.getDoubleExceptNull(contractBom.getWaste()) / 100.0));
				
				imgAmount=CommonUtils.getDoubleByDigit(imgAmount, totalAmountLength);
				
				double imgWaste = imgAmount * (CommonUtils.getDoubleExceptNull(contractBom
								.getWaste()) / 100.0);
				imgWaste=CommonUtils.getDoubleByDigit(imgWaste, wasteLength);
				
				double hasAmount = CommonUtils
						.getDoubleExceptNull(hmImgUse.get(contractBom
								.getContractImgSeqNum() == null ? ""
								: contractBom.getContractImgSeqNum().toString()));
				double hasWaste = CommonUtils
						.getDoubleExceptNull(hmImgWaste.get(contractBom
								.getContractImgSeqNum() == null ? ""
								: contractBom.getContractImgSeqNum().toString()));
				hmImgUse.put((contractBom.getContractImgSeqNum() == null ? ""
						: contractBom.getContractImgSeqNum().toString()),
						imgAmount + hasAmount);
				hmImgWaste.put((contractBom.getContractImgSeqNum() == null ? ""
						: contractBom.getContractImgSeqNum().toString()),
						imgWaste + hasWaste);
			}
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("计算核销料件耗用和边角料完成！");
		}
	}

	/**
	 * 重新计算边角料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 */
	public void recalRemainMaterialAmount(ContractCav contractCav) {
		List<ContractExgCav> lsExg = this.contractCavDao
				.findContractExgCav(contractCav);
		HashMap<String, Double> hmImgWaste = new HashMap<String, Double>();
		for (ContractExgCav contractExgCav : lsExg) {
			double exgAmount = CommonUtils.getDoubleExceptNull(contractExgCav
					.getExpTotal());
			List<ContractBomCav> list = this.contractCavDao
					.findContractBomCavByExgSeqNum(contractCav.getId(),
							contractExgCav.getSeqNum());
			for (ContractBomCav contractBomCav : list) {
				double imgAmount = exgAmount
						* CommonUtils.getDoubleExceptNull(CommonUtils
								.getDoubleExceptNull(contractBomCav
										.getUnitWaste())
								/ (1 - CommonUtils
										.getDoubleExceptNull(contractBomCav
												.getWaste()) / 100.0));
				double imgWaste = imgAmount
						* (CommonUtils.getDoubleExceptNull(contractBomCav
								.getWaste()) / 100.0);
				double hasWaste = CommonUtils
						.getDoubleExceptNull(hmImgWaste.get(contractBomCav
								.getSeqMaterialNum() == null ? ""
								: contractBomCav.getSeqMaterialNum().toString()));
				hmImgWaste.put((contractBomCav.getSeqMaterialNum() == null ? ""
						: contractBomCav.getSeqMaterialNum().toString()),
						imgWaste + hasWaste);
			}
		}
		List<ContractImgCav> lsImg = this.contractCavDao
				.findContractImgCav(contractCav);
		for (ContractImgCav contractImgCav : lsImg) {
			if (contractImgCav.getSeqNum() != null) {
				contractImgCav.setLeftoverMaterial(CommonUtils
						.getDoubleByDigit(hmImgWaste.get(contractImgCav
								.getSeqNum().toString()), 5));
				this.contractCavDao.saveContractImgCav(contractImgCav);
			}
		}
	}

	/**
	 * 核销表总计算
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return ContractCavTotalValue 存放纸质手册合同核销的进出口方面的总和 如进口总金额、出口总金额、出口总毛重.....
	 */
	public ContractCavTotalValue findCavTotalValue(ContractCav contractCav,
			Contract contract) {
		ContractCavTotalValue contractCavTotalValue = new ContractCavTotalValue();
		double impAmount = calcContractCavImpAmount(contract);// 重新计算进口金额
		contractCavTotalValue.setExpAmount(contractCav.getExpAmount());
		contractCavTotalValue.setImpAmount(impAmount);
		contractCavTotalValue.setProcessTotalPrice(contractCav
				.getProcessTotalPrice());
		contractCavTotalValue.setExpTotalPieces(this.contractCavDao
				.findExpTotalPieces(contractCav.getEmsNo()));
		double[] importWeight = this.contractCavDao
				.findImportTotalWeight(contractCav.getEmsNo());
		contractCavTotalValue.setImpTotalGrossWeight(importWeight[0]);
		contractCavTotalValue.setImpTotalNetWeight(importWeight[1]);
		double[] exportWeight = this.contractCavDao
				.findExportTotalWeight(contractCav.getEmsNo());
		contractCavTotalValue.setExpTotalGrossWeight(exportWeight[0]);
		contractCavTotalValue.setExpTotalNetWeight(exportWeight[1]);
		double ValueAddRate = 0.0;
		if (impAmount != Double.valueOf(0)) {
			ValueAddRate = CommonUtils.getDoubleByDigit(
					(contractCav.getExpAmount() - impAmount) / impAmount, 5);
		}
		contractCav.setValueAddedRate(ValueAddRate);
		contractCavDao.saveOrUpdate(contractCav);
		return contractCavTotalValue;
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合）
	 * list<ContractUnitWasteCav>(1) 单耗记录（集合）
	 * 
	 * @param contractCavId
	 *            合同核销表头Id
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0)成品（集合）,list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	public List<List> findContractUnitWasteCav(String contractCavId, int index,
			int length, boolean isShowZero, boolean isNoPrintProductZer) {
		List<ContractUnitWasteCav> contractUnitWasteListCav = new ArrayList<ContractUnitWasteCav>();
		List<ContractExgCav> contractExgCavList = null;
		List<ContractImgCav> contractImgCavList = null;
		Map<String, ContractBomCav> contractBomCavMap = new HashMap<String, ContractBomCav>();
		List<ContractBomCav> contractBomCavList = null;
		final int columnCount = 4;
		contractExgCavList = this.contractCavDao.findContractExgCav(
				contractCavId, index, length);
		if (isNoPrintProductZer) {
			List<ContractExgCav> contractBomCavLists = new ArrayList<ContractExgCav>();
			for (ContractExgCav cBom : contractExgCavList) {
				if (cBom.getExpTotal() > 0) {
					contractBomCavLists.add(cBom);
				}
			}
			contractExgCavList = contractBomCavLists;
		}
		int contractExgCavCount = contractExgCavList.size();
		contractImgCavList = this.contractCavDao
				.findContractImgCavByParentId(contractCavId);
		contractBomCavList = this.contractCavDao
				.findContractBomCavByContractParentId(contractCavId);
		for (int i = 0; i < contractBomCavList.size(); i++) {
			ContractBomCav contractBomCav = (ContractBomCav) contractBomCavList
					.get(i);
			/**
			 * 名称,规格, seqMaterialNum== key
			 */
			String productName = contractBomCav.getSeqProductNum() == null ? ""
					: contractBomCav.getSeqProductNum().toString();// 成品名称
			String seqMaterialNum = contractBomCav.getSeqMaterialNum() == null ? ""
					: contractBomCav.getSeqMaterialNum().toString();
			String mName = contractBomCav.getMaterialName() == null ? ""
					: contractBomCav.getMaterialName();// 加上料件名称，以防国内购买的
			String key = productName + "/" + seqMaterialNum + "/" + mName;
			contractBomCavMap.put(key, contractBomCav);
			// System.out.println("111: " + key);
		}
		/**
		 * 获得所有组数 并分组
		 */
		int groupCount = contractExgCavCount / columnCount
				+ ((contractExgCavCount % columnCount) > 0 ? 1 : 0);

		Collections.sort(contractExgCavList, new Comparator<ContractExgCav>() {
			public int compare(ContractExgCav o1, ContractExgCav o2) {
				if (o1.getSeqNum() == null && o2.getSeqNum() != null) {
					return -1;
				} else if (o1.getSeqNum() != null && o2.getSeqNum() == null) {
					return 1;
				} else if (o1.getSeqNum() == null && o2.getSeqNum() == null) {
					return 0;
				} else {
					Integer seqNum1 = Integer.valueOf(o1.getSeqNum());
					Integer seqNum2 = Integer.valueOf(o2.getSeqNum());
					if (seqNum1 > seqNum2) {
						return 1;
					} else if (seqNum1 == seqNum2) {
						return 0;
					} else if (seqNum1 < seqNum2) {
						return -1;
					}
				}
				return 0;
			}
		});
		Collections.sort(contractImgCavList, new Comparator<ContractImgCav>() {
			public int compare(ContractImgCav o1, ContractImgCav o2) {
				if (o1.getSeqNum() == null && o2.getSeqNum() != null) {
					return -1;
				} else if (o1.getSeqNum() != null && o2.getSeqNum() == null) {
					return 1;
				} else if (o1.getSeqNum() == null && o2.getSeqNum() == null) {
					return 0;
				} else {
					Integer seqNum1 = Integer.valueOf(o1.getSeqNum());
					Integer seqNum2 = Integer.valueOf(o2.getSeqNum());
					if (seqNum1 > seqNum2) {
						return 1;
					} else if (seqNum1 == seqNum2) {
						return 0;
					} else if (seqNum1 < seqNum2) {
						return -1;
					}
				}
				return 0;
			}
		});

		/**
		 * 以成品行数为4条记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExgCav> tempContractExgCavList = new ArrayList<ContractExgCav>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCavCount) {
					tempContractExgCavList.add(contractExgCavList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgCavList.size(); i++) {
				ContractImgCav contractImgCav = contractImgCavList.get(i);
				boolean isExist = false;
				ContractUnitWasteCav contractUnitWasteCav = new ContractUnitWasteCav();
				contractUnitWasteCav.setGroupId(g);
				contractUnitWasteCav.setSeqMaterialNum(contractImgCav
						.getSeqNum().toString());
				contractUnitWasteCav.setMaterialName(contractImgCav.getName());
				contractUnitWasteCav.setMaterialSpec(contractImgCav.getSpec());
				boolean isadd = false;// 判断BOM单耗是否存在，如果不存在，不用打印
				for (int j = 0; j < tempContractExgCavList.size(); j++) {
					ContractExgCav contractExgCav = tempContractExgCavList
							.get(j);
					/**
					 * 名称,规格, seqMaterialNum== key
					 */
					String productName = contractExgCav.getSeqNum() == null ? ""
							: contractExgCav.getSeqNum().toString();// 成品名称
					String seqMaterialNum = (contractImgCav.getSeqNum() == null ? ""
							: contractImgCav.getSeqNum().toString());
					String mName = contractImgCav.getName() == null ? ""
							: contractImgCav.getName();// 加上料件名称，以防国内购买的
					String key = productName + "/" + seqMaterialNum + "/"
							+ mName;
					// System.out.println("222: " + key);
					ContractBomCav contractBomCav = contractBomCavMap.get(key);
					// System.out.println("contractBomCav:" + contractBomCav);
					if (contractBomCav == null) {
						continue;
					}
					if (!isShowZero) {
						if (contractBomCav.getUnitWaste() != null
								&& contractBomCav.getUnitWaste() != 0) {
							isadd = true;
						}
					} else {
						isadd = true;
					}

					switch (j) {
					case 0:
						contractUnitWasteCav.setUnitWaste1(contractBomCav
								.getUnitWaste());
						contractUnitWasteCav.setWasteAmount1(contractBomCav
								.getWasteAmount());
						contractUnitWasteCav.setTotalAmount1(contractBomCav
								.getTotalAmount());
						isExist = true;
						break;
					case 1:
						contractUnitWasteCav.setUnitWaste2(contractBomCav
								.getUnitWaste());
						contractUnitWasteCav.setWasteAmount2(contractBomCav
								.getWasteAmount());
						contractUnitWasteCav.setTotalAmount2(contractBomCav
								.getTotalAmount());
						isExist = true;
						break;
					case 2:
						contractUnitWasteCav.setUnitWaste3(contractBomCav
								.getUnitWaste());
						contractUnitWasteCav.setWasteAmount3(contractBomCav
								.getWasteAmount());
						contractUnitWasteCav.setTotalAmount3(contractBomCav
								.getTotalAmount());
						isExist = true;
						break;
					case 3:

						contractUnitWasteCav.setUnitWaste4(contractBomCav
								.getUnitWaste());
						contractUnitWasteCav.setWasteAmount4(contractBomCav
								.getWasteAmount());
						contractUnitWasteCav.setTotalAmount4(contractBomCav
								.getTotalAmount());
						isExist = true;
						break;
					}
				}
				if (isExist && isadd) {
					contractUnitWasteListCav.add(contractUnitWasteCav);
				}
			}
		}
		List<List> returnList = new ArrayList<List>();
		returnList.add(contractExgCavList);
		returnList.add(contractUnitWasteListCav);
		return returnList;
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合）
	 * list<ContractUnitWasteCav>(1) 单耗记录（集合）
	 * 
	 * @param contractCavId
	 *            合同核销表头Id
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0)成品（集合）,list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	public List<List> findContractUnitWasteCav2(String parentId, String emsNo,
			int index, int length) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(2);
		nf.setMaximumIntegerDigits(10);
		nf.setGroupingUsed(false);
		NumberFormat nft = NumberFormat.getInstance();
		nft.setMinimumFractionDigits(1);
		nft.setMaximumFractionDigits(5);
		nft.setMaximumIntegerDigits(10);
		nft.setGroupingUsed(false);
		List<TempContractConsumptionUnitWasteCav> contractUnitWasteListCav = new ArrayList<TempContractConsumptionUnitWasteCav>();
		List<ContractExgCav> contractExgCavList = null;
		List<ContractImgCav> contractImgCavList = null;
		Map<String, ContractBomCav> contractBomCavMap = new HashMap<String, ContractBomCav>();
		List<ContractBomCav> contractBomCavList = null;
		final int columnCount = 6;
		contractExgCavList = this.contractCavDao.findContractExgCav(parentId,
				index, length);
		int contractExgCavCount = contractExgCavList.size();
		contractImgCavList = this.contractCavDao
				.findContractImgCavByParentId(parentId);
		contractBomCavList = this.contractCavDao
				.findContractBomCavByContractParentId(parentId);
		for (int i = 0; i < contractBomCavList.size(); i++) {
			ContractBomCav contractBomCav = (ContractBomCav) contractBomCavList
					.get(i);
			/**
			 * 名称,规格, seqMaterialNum== key
			 */
			String productName = contractBomCav.getSeqProductNum() == null ? ""
					: contractBomCav.getSeqProductNum().toString();// 成品名称
			String seqMaterialNum = contractBomCav.getSeqMaterialNum() == null ? ""
					: contractBomCav.getSeqMaterialNum().toString();
			String mName = contractBomCav.getMaterialName() == null ? ""
					: contractBomCav.getMaterialName();// 加上料件名称，以防国内购买的
			String key = productName + "/" + seqMaterialNum + "/" + mName;
			contractBomCavMap.put(key, contractBomCav);
		}
		/**
		 * 获得所有组数 并分组
		 */
		int groupCount = contractExgCavCount / columnCount
				+ ((contractExgCavCount % columnCount) > 0 ? 1 : 0);
		/**
		 * 以成品行数为6条记录进行--手动分组
		 */
		for (int g = 0; g < groupCount; g++) {
			/**
			 * 获取以列的个数分组的临时成品列表
			 */
			List<ContractExgCav> tempContractExgCavList = new ArrayList<ContractExgCav>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCavCount) {
					tempContractExgCavList.add(contractExgCavList.get(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < contractImgCavList.size(); i++) {
				ContractImgCav contractImgCav = contractImgCavList.get(i);
				boolean isExist = false;
				TempContractConsumptionUnitWasteCav contractUnitWasteCav = new TempContractConsumptionUnitWasteCav();
				contractUnitWasteCav.setGroupId(g);
				contractUnitWasteCav.setSeqMaterialNum(contractImgCav
						.getSeqNum().toString());
				Integer seqNum = contractImgCav.getSeqNum();
				ContractImg contractImg = this.contractCavDao.findContractImg(
						emsNo, seqNum);
				if (contractImgCav.getSpec() != null
						&& !contractImgCav.getSpec().equals("")) {
					contractUnitWasteCav.setPtName(contractImgCav.getName());
				} else {
					contractUnitWasteCav.setPtName(contractImgCav.getName()
							+ "/" + contractImgCav.getSpec());
				}
				if ("千克".equals(contractImgCav.getUnit().getName())) {
					/**
					 * 备案数量
					 */
					contractUnitWasteCav.setFileAmount(nf.format(contractImg
							.getAmount() == null ? 0.0 : contractImg
							.getAmount()));
					/**
					 * 单位
					 */
					contractUnitWasteCav.setUnit(contractImgCav.getUnit()
							.getName() == null ? "" : contractImgCav.getUnit()
							.getName());
					/**
					 * 总进数量
					 */
					contractUnitWasteCav
							.setTotalAmount(nf.format((contractImgCav
									.getDirectImport() == null ? 0.0
									: contractImgCav.getDirectImport())
									+ (contractImgCav
											.getTransferFactoryImport() == null ? 0.0
											: contractImgCav
													.getTransferFactoryImport())
									+ (contractImgCav.getRemainImport() == null ? 0.0
											: contractImgCav.getRemainImport())
									- (contractImgCav.getBackExport() == null ? 0.0
											: contractImgCav.getBackExport())
									- (contractImgCav.getRemainExport() == null ? 0.0
											: contractImgCav.getRemainExport())));
					/**
					 * 总耗合计
					 */
					contractUnitWasteCav
							.setTotalConsumption(contractUnitWasteCav
									.getTotalConsumption() == null
									|| "".equals(contractUnitWasteCav
											.getTotalConsumption()) ? "0.0"
									: contractUnitWasteCav
											.getTotalConsumption());
					/**
					 * 剩余料件数量合计
					 */
					contractUnitWasteCav
							.setTotalRemnantImg(nf.format(contractUnitWasteCav
									.getTotalAmount() == null
									|| "".equals(contractUnitWasteCav
											.getTotalAmount()) ? 0.0
									: Double.valueOf(contractUnitWasteCav
											.getTotalAmount())
											- (contractUnitWasteCav
													.getTotalConsumption() == null
													|| "".equals(contractUnitWasteCav
															.getTotalConsumption()) ? 0.0
													: Double.valueOf(contractUnitWasteCav
															.getTotalConsumption()
															.split("/")[0]))));
					/**
					 * 复出和余料结转出口合计
					 */
					contractUnitWasteCav
							.setTotalComebackAndExport(nf.format((contractImgCav
									.getBackExport() == null ? 0.0
									: contractImgCav.getBackExport())
									+ (contractImgCav.getRemainExport() == null ? 0.0
											: contractImgCav.getRemainExport())));
					/**
					 * 剩余残次品及边角料数量
					 */
					contractUnitWasteCav
							.setTotalRemnantImperfectionsAndScrap(nf.format(contractImgCav
									.getLeftoverMaterial() == null ? 0.0
									: contractImgCav.getLeftoverMaterial()));
					/**
					 * 内销数量合计
					 */
					contractUnitWasteCav
							.setTotalInternalAmount(nf.format(contractImgCav
									.getInternalAmount() == null ? 0.0
									: contractImgCav.getInternalAmount()));
					for (int j = 0; j < tempContractExgCavList.size(); j++) {
						ContractExgCav contractExgCav = tempContractExgCavList
								.get(j);
						/**
						 * 名称,规格, seqMaterialNum== key
						 */
						String productName = contractExgCav.getSeqNum() == null ? ""
								: contractExgCav.getSeqNum().toString();// 成品名称
						String seqMaterialNum = (contractImgCav.getSeqNum() == null ? ""
								: contractImgCav.getSeqNum().toString());
						String mName = contractImgCav.getName() == null ? ""
								: contractImgCav.getName();// 加上料件名称，以防国内购买的
						String key = productName + "/" + seqMaterialNum + "/"
								+ mName;
						ContractBomCav contractBomCav = contractBomCavMap
								.get(key);
						if (contractBomCav == null) {
							continue;
						}

						/**
						 * 成品数量
						 */
						double contractExgAmount = contractBomCav
								.getExgExpTotalAmount() == null ? 0.0
								: contractBomCav.getExgExpTotalAmount();
						/**
						 * 成品单耗
						 */
						double unitWaste = CommonUtils.getDoubleByDigit(
								contractBomCav.getUnitWaste() == null ? 0.0
										: contractBomCav.getUnitWaste(), 5);
						/**
						 * 成品总耗
						 */
						double totalWaste = CommonUtils.getDoubleByDigit(
								unitWaste * contractExgAmount, 2);
						switch (j) {
						case 0:
							contractUnitWasteCav.setUnitWaste1(nft
									.format(unitWaste));
							contractUnitWasteCav.setTotalWaste1(nf
									.format(totalWaste));
							isExist = true;
							break;
						case 1:
							contractUnitWasteCav.setUnitWaste2(nft
									.format(unitWaste));
							contractUnitWasteCav.setTotalWaste2(nf
									.format(totalWaste));
							isExist = true;
							break;
						case 2:
							contractUnitWasteCav.setUnitWaste3(nft
									.format(unitWaste));
							contractUnitWasteCav.setTotalWaste3(nf
									.format(totalWaste));
							isExist = true;
							break;
						case 3:
							contractUnitWasteCav.setUnitWaste4(nft
									.format(unitWaste));
							contractUnitWasteCav.setTotalWaste4(nf
									.format(totalWaste));
							isExist = true;
							break;
						case 4:
							contractUnitWasteCav.setUnitWaste5(nft
									.format(unitWaste));
							contractUnitWasteCav.setTotalWaste5(nf
									.format(totalWaste));
							isExist = true;
							break;
						case 5:
							contractUnitWasteCav.setUnitWaste6(nft
									.format(unitWaste));
							contractUnitWasteCav.setTotalWaste6(nf
									.format(totalWaste));
							isExist = true;
							break;
						}
					}
					if (isExist == true) {
						for (int k = 0; k < contractExgCavList.size(); k++) {
							ContractExgCav contractExgCav1 = contractExgCavList
									.get(k);
							switch (k) {
							case 0:
								contractUnitWasteCav
										.setExg1(contractExgCav1.getSeqNum()
												+ ":"
												+ (contractExgCav1.getName() != null ? contractExgCav1
														.getName() : ""));
								contractUnitWasteCav.setExg1(contractExgCav1
										.getName() != null ? contractExgCav1
										.getName() : "");
								contractUnitWasteCav
										.setProductAmount1(nf.format(contractExgCav1
												.getDirectExport() == null ? 0.0
												: contractExgCav1
														.getDirectExport()));
								contractUnitWasteCav
										.setUnit1(contractExgCav1.getUnit()
												.getName() != null ? contractExgCav1
												.getUnit().getName() : "");
								break;
							case 1:
								contractUnitWasteCav
										.setExg2(contractExgCav1.getSeqNum()
												+ ":"
												+ (contractExgCav1.getName() != null ? contractExgCav1
														.getName() : ""));
								contractUnitWasteCav.setExg2(contractExgCav1
										.getName() != null ? contractExgCav1
										.getName() : "");
								contractUnitWasteCav
										.setProductAmount2(nf.format(contractExgCav1
												.getDirectExport() == null ? 0.0
												: contractExgCav1
														.getDirectExport()));
								contractUnitWasteCav
										.setUnit2(contractExgCav1.getUnit()
												.getName() != null ? contractExgCav1
												.getUnit().getName() : "");
								break;
							case 2:
								contractUnitWasteCav
										.setExg3(contractExgCav1.getSeqNum()
												+ ":"
												+ (contractExgCav1.getName() != null ? contractExgCav1
														.getName() : ""));
								contractUnitWasteCav.setExg3(contractExgCav1
										.getName() != null ? contractExgCav1
										.getName() : "");
								contractUnitWasteCav
										.setProductAmount3(nf.format(contractExgCav1
												.getDirectExport() == null ? 0.0
												: contractExgCav1
														.getDirectExport()));
								contractUnitWasteCav
										.setUnit3(contractExgCav1.getUnit()
												.getName() != null ? contractExgCav1
												.getUnit().getName() : "");
								break;
							case 3:
								contractUnitWasteCav
										.setExg4(contractExgCav1.getSeqNum()
												+ ":"
												+ (contractExgCav1.getName() != null ? contractExgCav1
														.getName() : ""));
								contractUnitWasteCav.setExg4(contractExgCav1
										.getName() != null ? contractExgCav1
										.getName() : "");
								contractUnitWasteCav
										.setProductAmount4(nf.format(contractExgCav1
												.getDirectExport() == null ? 0.0
												: contractExgCav1
														.getDirectExport()));
								contractUnitWasteCav
										.setUnit4(contractExgCav1.getUnit()
												.getName() != null ? contractExgCav1
												.getUnit().getName() : "");
								break;
							case 4:
								contractUnitWasteCav
										.setExg5(contractExgCav1.getSeqNum()
												+ ":"
												+ (contractExgCav1.getName() != null ? contractExgCav1
														.getName() : ""));
								contractUnitWasteCav.setExg5(contractExgCav1
										.getName() != null ? contractExgCav1
										.getName() : "");
								contractUnitWasteCav
										.setProductAmount5(nf.format(contractExgCav1
												.getDirectExport() == null ? 0.0
												: contractExgCav1
														.getDirectExport()));
								contractUnitWasteCav
										.setUnit5(contractExgCav1.getUnit()
												.getName() != null ? contractExgCav1
												.getUnit().getName() : "");
								break;
							case 5:
								contractUnitWasteCav
										.setExg6(contractExgCav1.getSeqNum()
												+ ":"
												+ (contractExgCav1.getName() != null ? contractExgCav1
														.getName() : ""));
								contractUnitWasteCav.setExg6(contractExgCav1
										.getName() != null ? contractExgCav1
										.getName() : "");
								contractUnitWasteCav
										.setProductAmount6(nf.format(contractExgCav1
												.getDirectExport() == null ? 0.0
												: contractExgCav1
														.getDirectExport()));
								contractUnitWasteCav
										.setUnit6(contractExgCav1.getUnit()
												.getName() != null ? contractExgCav1
												.getUnit().getName() : "");
								break;
							}
						}
					}
				} else {
					/**
					 * 单重
					 */
					double netWeight = 0.0;
					List list = this.contractCavDao
							.findBcsCustomsDeclarationCommInfo(emsNo, seqNum);
					for (int k = 0; k < list.size(); k++) {
						Object[] obj = (Object[]) list.get(k);
						double commAmount = 0.0;
						String unit = null;
						double firstAmount = 0.0;
						String legalUnit = null;
						double secondAmount = 0.0;
						String secondLegalUnit = null;
						if (obj[1] != null) {
							commAmount = (Double) obj[1];
						}
						if (obj[2] != null) {
							unit = (String) obj[2];
						}
						if (obj[3] != null) {
							firstAmount = (Double) obj[3];
						}
						if (obj[4] != null) {
							legalUnit = (String) obj[4];
						}
						if (obj[5] != null) {
							secondAmount = (Double) obj[5];
						}
						if (obj[6] != null) {
							secondLegalUnit = (String) obj[6];
						}
						if (!"035".equals(unit) && "035".equals(legalUnit)) {
							netWeight = CommonUtils.getDoubleByDigit(
									firstAmount / commAmount, 5);
						}
						if (!"035".equals(unit)
								&& "035".equals(secondLegalUnit)) {
							netWeight = CommonUtils.getDoubleByDigit(
									secondAmount / commAmount, 5);

						}
						if (!"035".equals(unit) && !"035".equals(legalUnit)
								&& !"035".equals(secondLegalUnit)) {
							netWeight = 0.0;
						}
						if ("035".equals(unit)) {
							netWeight = 1.0;
						}
					}
					/**
					 * 单位
					 */
					contractUnitWasteCav.setUnit(contractImgCav.getUnit()
							.getName() + "/" + "千克");
					/**
					 * 备案数量
					 */
					contractUnitWasteCav.setFileAmount(nf.format(contractImg
							.getAmount() == null ? 0.0 : contractImg
							.getAmount())
							+ "/"
							+ nf.format(contractImg.getAmount() == null ? 0.0
									: contractImg.getAmount() * netWeight));
					/**
					 * 总进数量
					 */
					contractUnitWasteCav
							.setTotalAmount(nf.format((contractImgCav
									.getDirectImport() == null ? 0.0
									: contractImgCav.getDirectImport())
									+ (contractImgCav
											.getTransferFactoryImport() == null ? 0.0
											: contractImgCav
													.getTransferFactoryImport())
									+ (contractImgCav.getRemainImport() == null ? 0.0
											: contractImgCav.getRemainImport())
									- (contractImgCav.getBackExport() == null ? 0.0
											: contractImgCav.getBackExport())
									- (contractImgCav.getRemainExport() == null ? 0.0
											: contractImgCav.getRemainExport()))
									+ "/"
									+ nf.format(((contractImgCav
											.getDirectImport() == null ? 0.0
											: contractImgCav.getDirectImport())
											+ (contractImgCav
													.getTransferFactoryImport() == null ? 0.0
													: contractImgCav
															.getTransferFactoryImport())
											+ (contractImgCav.getRemainImport() == null ? 0.0
													: contractImgCav
															.getRemainImport())
											- (contractImgCav.getBackExport() == null ? 0.0
													: contractImgCav
															.getBackExport()) - (contractImgCav
											.getRemainExport() == null ? 0.0
												: contractImgCav
														.getRemainExport()))
											* netWeight));
					/**
					 * 总耗合计
					 */
					contractUnitWasteCav
							.setTotalConsumption((contractUnitWasteCav
									.getTotalConsumption() == null
									|| "".equals(contractUnitWasteCav
											.getTotalConsumption()) ? "0.0"
									: contractUnitWasteCav
											.getTotalConsumption().split("/")[0])
									+ "/"
									+ (contractUnitWasteCav
											.getTotalConsumption() == null
											|| "".equals(contractUnitWasteCav
													.getTotalConsumption()) ? "0.0"
											: nf.format(Double
													.valueOf(contractUnitWasteCav
															.getTotalConsumption()
															.split("/")[1])
													* netWeight)));

					/**
					 * 复出和余料结转出口合计
					 */
					contractUnitWasteCav
							.setTotalComebackAndExport(nf.format((contractImgCav
									.getBackExport() == null ? 0.0
									: contractImgCav.getBackExport())
									+ (contractImgCav.getRemainExport() == null ? 0.0
											: contractImgCav.getRemainExport()))
									+ "/"
									+ nf.format((contractImgCav.getBackExport() == null ? 0.0
											: contractImgCav.getBackExport())
											+ (contractImgCav.getRemainExport() == null ? 0.0
													: contractImgCav
															.getRemainExport())
											* netWeight));
					for (int j = 0; j < tempContractExgCavList.size(); j++) {
						ContractExgCav contractExgCav = tempContractExgCavList
								.get(j);
						/**
						 * 名称,规格, seqMaterialNum== key
						 */
						String productName = contractExgCav.getSeqNum() == null ? ""
								: contractExgCav.getSeqNum().toString();// 成品名称
						String seqMaterialNum = (contractImgCav.getSeqNum() == null ? ""
								: contractImgCav.getSeqNum().toString());
						String mName = contractImgCav.getName() == null ? ""
								: contractImgCav.getName();// 加上料件名称，以防国内购买的
						String key = productName + "/" + seqMaterialNum + "/"
								+ mName;
						ContractBomCav contractBomCav = contractBomCavMap
								.get(key);
						if (contractBomCav == null) {
							continue;
						}
						/**
						 * 成品数量
						 */
						double contractExgAmount = CommonUtils
								.getDoubleExceptNull(contractBomCav
										.getExgExpTotalAmount());
						/**
						 * 成品单耗
						 */
						double unitWaste = CommonUtils.getDoubleByDigit(
								CommonUtils.getDoubleExceptNull(contractBomCav
										.getUnitWaste()), 5);
						/**
						 * 成品总耗
						 */
						double totalWaste = CommonUtils.getDoubleByDigit(
								unitWaste * contractExgAmount, 2);

						switch (j) {
						case 0:
							contractUnitWasteCav.setUnitWaste1(nft
									.format(unitWaste)
									+ "/"
									+ nft.format(unitWaste * netWeight));
							contractUnitWasteCav.setTotalWaste1(nf
									.format(totalWaste)
									+ "/"
									+ nf.format(totalWaste * netWeight));
							isExist = true;
							break;
						case 1:
							contractUnitWasteCav.setUnitWaste2(nft
									.format(unitWaste)
									+ "/"
									+ nft.format(unitWaste * netWeight));
							contractUnitWasteCav.setTotalWaste2(nf
									.format(totalWaste)
									+ "/"
									+ nf.format(totalWaste * netWeight));
							isExist = true;
							break;
						case 2:
							contractUnitWasteCav.setUnitWaste3(nft
									.format(unitWaste)
									+ "/"
									+ nft.format(unitWaste * netWeight));
							contractUnitWasteCav.setTotalWaste3(nf
									.format(totalWaste)
									+ "/"
									+ nf.format(totalWaste * netWeight));
							isExist = true;
							break;
						case 3:
							contractUnitWasteCav.setUnitWaste4(nft
									.format(unitWaste)
									+ "/"
									+ nft.format(unitWaste * netWeight));
							contractUnitWasteCav.setTotalWaste4(nf
									.format(totalWaste)
									+ "/"
									+ nf.format(totalWaste * netWeight));
							isExist = true;
							break;
						case 4:
							contractUnitWasteCav.setUnitWaste5(nft
									.format(unitWaste)
									+ "/"
									+ nft.format(unitWaste * netWeight));
							contractUnitWasteCav.setTotalWaste5(nf
									.format(totalWaste)
									+ "/"
									+ nf.format(totalWaste * netWeight));
							isExist = true;
							break;
						case 5:
							contractUnitWasteCav.setUnitWaste6(nft
									.format(unitWaste)
									+ "/"
									+ nft.format(unitWaste * netWeight));
							contractUnitWasteCav.setTotalWaste6(nf
									.format(totalWaste)
									+ "/"
									+ nf.format(totalWaste * netWeight));
							isExist = true;
							break;
						}
					}
				}
				if (isExist) {
					contractUnitWasteListCav.add(contractUnitWasteCav);
				}
			}
		}
		List<List> returnList = new ArrayList<List>();
		returnList.add(contractExgCavList);
		returnList.add(contractUnitWasteListCav);
		return returnList;
	}

	/**
	 * 修改单耗，反算损耗量，总用量，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            合同核销BOM
	 */
	private void modifyUnitWasteWriteBack(ContractBomCav bomCav) {
		ContractBomCav oldBomCav = (ContractBomCav) this.contractCavDao.load(
				ContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		/*
		 * if (bomCav.getUnitWaste().equals(oldBomCav.getUnitWaste()) &&
		 * bomCav.getWaste().equals(oldBomCav.getWaste())) { return; }
		 */
		double unitWaste = CommonUtils.getDoubleExceptNull(bomCav
				.getUnitWaste());
		double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
		double exgAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getExgExpTotalAmount());
		if ((1 - waste) <= 0) {
			return;
		}
		// double unitDosage = CommonUtils.getDoubleByDigit(unitWaste
		// / (1 - waste), 5);
		// double imgAmount = CommonUtils.getDoubleByDigit(exgAmount *
		// unitDosage,
		// 3);
		double imgAmount = CommonUtils.getDoubleByDigit(
				exgAmount
						* CommonUtils.getDoubleExceptNull(unitWaste
								/ (1 - waste)), 5);
		bomCav.setWasteAmount(CommonUtils
				.getDoubleByDigit(imgAmount * waste, 5));
		bomCav.setTotalAmount(imgAmount);
		/**
		 * 计算出单耗改变前，后的单耗总用量的差值
		 */
		double diffTotalAmount = imgAmount
				- CommonUtils.getDoubleExceptNull(oldBomCav.getTotalAmount());
		double diffWasteAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getWasteAmount())
				- CommonUtils.getDoubleExceptNull(oldBomCav.getWasteAmount());
		if (diffTotalAmount == 0 && diffWasteAmount == 0) {
			return;
		}
		ContractImgCav imgCav = this.contractCavDao.findContractImgCavBySeqNum(
				bomCav.getContractCav().getId(), bomCav.getSeqMaterialNum(),
				bomCav.getMaterialName());
		if (imgCav == null) {
			return;
		}
		imgCav.setProductWaste(CommonUtils.getDoubleExceptNull(imgCav
				.getProductWaste()) + diffTotalAmount);
		imgCav.setLeftoverMaterial(CommonUtils.getDoubleExceptNull(imgCav
				.getLeftoverMaterial()) + diffWasteAmount);
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(imgCav.getImpTotal())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getProductWaste())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getInternalAmount()), 5));
		this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 修改总用量反算损耗量，单耗，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            合同核销BOM
	 */
	private void modifyTotalAmountWriteBack(ContractBomCav bomCav) {
		ContractBomCav oldBomCav = (ContractBomCav) this.contractCavDao.load(
				ContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		/*
		 * if (bomCav.getTotalAmount().equals(oldBomCav.getTotalAmount())) {
		 * return; }
		 */
		double oldTotalAmount = CommonUtils.getDoubleExceptNull(oldBomCav
				.getTotalAmount());
		// 获取bomCav总用量
		double newTotalAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getTotalAmount());
		// 获取成品出口总数量
		double exgAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getExgExpTotalAmount());
		if (exgAmount <= 0.0) {
			return;
		}
		double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
				/ exgAmount, 5);
		double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
		double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
				* (1 - waste), 5);
		bomCav.setUnitWaste(unitWaste);
		bomCav.setWasteAmount(CommonUtils.getDoubleByDigit(newTotalAmount
				* waste, 3));
		double diffAmount = newTotalAmount - oldTotalAmount;
		if (diffAmount == 0) {
			return;
		}

		ContractImgCav imgCav = this.contractCavDao.findContractImgCavBySeqNum(
				bomCav.getContractCav().getId(), bomCav.getSeqMaterialNum(),
				bomCav.getMaterialName());
		if (imgCav == null) {
			return;
		}
		// imgCav.setProductWaste((imgCav.getProductWaste() == null ? 0.0 :
		// imgCav
		// .getProductWaste())
		// + diffAmount);
		imgCav.setProductWaste(CommonUtils.getDoubleByDigit(
				(imgCav.getProductWaste() == null ? 0.0 : imgCav
						.getProductWaste()) + diffAmount, 0));
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(imgCav.getImpTotal())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getProductWaste())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getInternalAmount()), 5));
		this.contractCavDao.saveContractImgCav(imgCav);
		this.contractCavDao.saveContractBomCav(bomCav);

	}

	/**
	 * 修改损耗量不反算总用量，损耗，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            合同核销BOM
	 */
	private void modifyWasteAmount(ContractBomCav bomCav) {
		ContractBomCav oldBomCav = (ContractBomCav) this.contractCavDao.load(
				ContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		/*
		 * if (bomCav.getTotalAmount().equals(oldBomCav.getTotalAmount())) {
		 * return; }
		 */
		double oldTotalAmount = CommonUtils.getDoubleExceptNull(oldBomCav
				.getTotalAmount());
		// 获取bomCav总用量
		double newTotalAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getTotalAmount());
		// 获取成品出口总数量
		double exgAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getExgExpTotalAmount());
		if (exgAmount <= 0.0) {
			return;
		}
		//净耗
		double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
				/ exgAmount, 5);
		
		//损耗
		double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
		//单项用量
		double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
				* (1 - waste), 5);
		
		
		//损耗量 = 成品数量 * 损耗% HH 2013.10.28
		//double wasteAmount = CommonUtils.getDoubleByDigit(bomCav.getExgExpTotalAmount() * bomCav.getWaste(), 5);
		
		bomCav.setUnitWaste(unitWaste);
		
		//反算总用量HH 2013.10.28
		newTotalAmount += bomCav.getWasteAmount();
		bomCav.setTotalAmount(newTotalAmount);
		
		//成品耗用量 差额 （修改前 - 修改后）
		double diffAmount = newTotalAmount - oldTotalAmount;
		if (diffAmount == 0) {
			return;
		}

		//反算损耗率 总用量 = 净耗/（1-损耗） * 出口总数量HH 2013.10.28
		//1 - (净耗/(总用量/出口总数量))
		double newWaste = 1 - (unitDosage/(newTotalAmount / exgAmount));
		bomCav.setWaste(newWaste);
		
		ContractImgCav imgCav = this.contractCavDao.findContractImgCavBySeqNum(
				bomCav.getContractCav().getId(), bomCav.getSeqMaterialNum(),
				bomCav.getMaterialName());
		if (imgCav == null) {
			return;
		}
		//成品耗用量
		imgCav.setProductWaste(CommonUtils.getDoubleByDigit(
				(imgCav.getProductWaste() == null ? 0.0 : imgCav
						.getProductWaste()) + diffAmount, 0));
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(imgCav.getImpTotal())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getProductWaste())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getInternalAmount()), 5));
		this.contractCavDao.saveContractImgCav(imgCav);
		this.contractCavDao.saveContractBomCav(bomCav);

	}

	
	/**
	 * 修改损耗量不反算单耗，总用量，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            合同核销BOM
	 */
	private void modifyWasteAmountWriteBack(ContractBomCav bomCav) {
		ContractBomCav oldBomCav = (ContractBomCav) this.contractCavDao.load(
				ContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		if (bomCav.getWasteAmount().equals(oldBomCav.getWasteAmount())) {
			return;
		}
		double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
		double oldTotalAmount = CommonUtils.getDoubleExceptNull(oldBomCav
				.getTotalAmount());
		double newTotalAmount = CommonUtils
				.getDoubleByDigit((CommonUtils.getDoubleExceptNull(bomCav
						.getWasteAmount()) / waste), 5);
		bomCav.setTotalAmount(newTotalAmount);
		double exgAmount = bomCav.getExgExpTotalAmount();
		double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
				/ exgAmount, 5);
		double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
				* (1 - waste), 5);
		bomCav.setUnitWaste(unitWaste);
		double diffAmount = newTotalAmount - oldTotalAmount;
		if (diffAmount == 0) {
			return;
		}
		ContractImgCav imgCav = this.contractCavDao.findContractImgCavBySeqNum(
				bomCav.getContractCav().getId(), bomCav.getSeqMaterialNum(),
				bomCav.getMaterialName());
		if (imgCav == null) {
			return;
		}
		// imgCav.setProductWaste((imgCav.getProductWaste() == null ? 0.0 :
		// imgCav
		// .getProductWaste())
		// + diffAmount);
		imgCav.setProductWaste(CommonUtils.getDoubleByDigit(
				((imgCav.getProductWaste() == null ? 0.0 : imgCav
						.getProductWaste()) + diffAmount) + diffAmount, 0));
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(imgCav.getImpTotal())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getProductWaste())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getInternalAmount()), 5));
		this.contractCavDao.saveContractImgCav(imgCav);
		this.contractCavDao.saveContractBomCav(bomCav);

	}

	/**
	 * 修改成品耗用量反算损耗量，总用量，单耗和料件余量
	 * 
	 * @param imgCav
	 *            合同核销料件
	 * @param list
	 *            是List<ContractBomCav>型，合同核销BOM
	 */
	private void modifyProductUsedAmountWriteBack(ContractImgCav imgCav,
			List<ContractBomCav> list) {
		if (list.size() < 1) {
			return;
		}
		ContractImgCav oldImgCav = (ContractImgCav) this.contractCavDao.load(
				ContractImgCav.class, imgCav.getId());
		if (oldImgCav == null) {
			return;
		}
		if (imgCav.getProductWaste().equals(oldImgCav.getProductWaste())) {
			return;
		}
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(
				CommonUtils.getDoubleExceptNull(imgCav.getImpTotal())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getProductWaste())
						- CommonUtils.getDoubleExceptNull(imgCav
								.getInternalAmount()), 5));
		// 旧的成品耗用量
		double oldProductUsedAmount = CommonUtils.getDoubleExceptNull(oldImgCav
				.getProductWaste());
		// 修改之后的成品耗用量
		double newProductUsedAmount = CommonUtils.getDoubleExceptNull(imgCav
				.getProductWaste());
		// 修改前后成品耗用量差
		double diffAmount = newProductUsedAmount - oldProductUsedAmount;
		// 总用量和
		double totalUsedAmount = 0.0;
		for (ContractBomCav bomCav : list) {
			totalUsedAmount += CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount());
		}
		// 总用量小于等于0退出
		if (totalUsedAmount <= 0) {
			return;
		}
		// 计算单耗
		for (ContractBomCav bomCav : list) {
			// m=总用量/总用量和*修改前后成品耗用量差
			double m = CommonUtils
					.getDoubleByDigit(
							((CommonUtils.getDoubleExceptNull(bomCav
									.getTotalAmount()) / totalUsedAmount) * diffAmount),
							5);
			// 总用量＝总用量 ＋m
			bomCav.setTotalAmount(CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount()) + m);
			// 新总用量
			double newTotalAmount = CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount());
			// 成品出品总数量
			double exgAmount = CommonUtils.getDoubleExceptNull(bomCav
					.getExgExpTotalAmount());
			// 成品出品总数量退出
			if (exgAmount <= 0) {
				continue;
			}
			// 单项用量
			double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
					/ exgAmount, 5);
			// 损耗
			double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
			// 单耗
			double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
					* (1 - waste), 5);
			bomCav.setUnitWaste(unitWaste);
			bomCav.setWasteAmount(CommonUtils.getDoubleByDigit(newTotalAmount
					* waste, 5));
			this.contractCavDao.saveContractBomCav(bomCav);
		}
		this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 核销单数量取整
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @param isTotalAmount
	 *            true:修改总用量 false:修改总耗量
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	public void convertWasteAmountToInteger(ContractCav contractCav,
			boolean isTotalAmount, boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack) {
		List<ContractBomCav> list = this.contractCavDao
				.findContractBomCav(contractCav);
		for (ContractBomCav bomCav : list) {
			if (isTotalAmount) {
				bomCav.setTotalAmount(CommonUtils.getDoubleByDigit(
						bomCav.getTotalAmount(), 0));
				if (modifyTotalAmountNotWriteBack) {
					this.modifyTotalAmountWriteBack(bomCav);
				}
				this.contractCavDao.saveContractBomCav(bomCav);
			} else {
				bomCav.setWasteAmount(CommonUtils.getDoubleByDigit(
						bomCav.getWasteAmount(), 0));
				if (modifyWasteAmountNotWriteBack) {
					this.modifyWasteAmountWriteBack(bomCav);
				}
				this.contractCavDao.saveContractBomCav(bomCav);
			}
		}
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param bomCav
	 *            合同核销BOM
	 * @param modifyUnitWasteNotWriteBack
	 *            判断是否修改单耗后反写，true为修改
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 * @param modifyWasteAmount
	 *            判断是否修改损耗量后反写，true为修改
	 */
	public void saveContractBomCav(ContractBomCav bomCav,
			boolean modifyUnitWasteNotWriteBack,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack, boolean modifyWasteAmount) {
		if (!modifyUnitWasteNotWriteBack) {
			this.modifyUnitWasteWriteBack(bomCav);
		}
		if (!modifyTotalAmountNotWriteBack) {
			this.modifyTotalAmountWriteBack(bomCav);
		}
		// if (!modifyWasteAmountNotWriteBack) {
		// this.modifyWasteAmountWriteBack(bomCav);
		// }
		//HH 2013.11.28 添加反写损耗量的方法
		 if (!modifyWasteAmount) {
			 this.modifyWasteAmount(bomCav);
		 }
		ContractBomCav oldBomCav = (ContractBomCav) this.contractCavDao.load(
				ContractBomCav.class, bomCav.getId());
		if (oldBomCav != null) {
			if (!bomCav.getUnitWaste().equals(oldBomCav.getUnitWaste())
					|| !bomCav.getWaste().equals(oldBomCav.getWaste())) {
				bomCav.setModifyMark(ModifyMarkState.MODIFIED);
			}
		}
		this.contractCavDao.saveContractBomCav(bomCav);
	}

	/**
	 * 保存合同核销BOM资料
	 * 
	 * @param imgCav
	 *            合同核销料件
	 * @param list
	 *            是List型，合同核销BOM
	 * @param modifyProductUsedAmountWriteBack
	 *            判断是否修改成品耗用量后反写，true为修改
	 */
	public void saveContractImgCav(ContractImgCav imgCav, List list,
			boolean modifyProductUsedAmountWriteBack) {
		if (modifyProductUsedAmountWriteBack) {
			this.modifyProductUsedAmountWriteBack(imgCav, list);
		}
		this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 合同核销平衡检查
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return boolean 平衡返回true
	 */
	/*
	 * public boolean checkContractCavData(ContractCav contractCav) { List list
	 * = this.contractCavDao.findContractImgCav(contractCav); final String lgImg
	 * = "以下不符合公式：-1%=<[进口总数量-（产品总耗用量+内销数量+退运出口数量+余料数量）]/进口总数量<=1%\n"; final
	 * String lgBom = "以下不符合公式：-1%=<[料件耗用总量-（对应成品数量*单耗+损耗量）]/料件耗用量<=1%\n";
	 * StringBuffer logBom = new StringBuffer(""); int lt = 0; StringBuffer
	 * logImg = new StringBuffer(""); StringBuffer log = new StringBuffer("");
	 * for (int i = 0; i < list.size(); i++) { ContractImgCav imgCav =
	 * (ContractImgCav) list.get(i); double impTotal =
	 * CommonUtils.getDoubleExceptNull(imgCav .getImpTotal()); if (impTotal <=
	 * 0) { continue; } double productWaste =
	 * CommonUtils.getDoubleExceptNull(imgCav .getProductWaste()); double
	 * internalAmount = CommonUtils.getDoubleExceptNull(imgCav
	 * .getInternalAmount()); double backExport =
	 * CommonUtils.getDoubleExceptNull(imgCav .getBackExport()); // 改为余料结转数量
	 * edit 2009.4.23 by cjb double remainMaterial =
	 * CommonUtils.getDoubleExceptNull(imgCav .getRemainExport()); double
	 * errorScale = (impTotal - (productWaste + internalAmount + backExport +
	 * remainMaterial)) / impTotal; // String str =
	 * "-1%=<[进口总数量-（产品总耗用量+内销数量+退运出口数量+余料数量）]/进口总数量<=1%"; // if (errorScale >
	 * 0.01 || errorScale < -0.01) { // throw new RuntimeException("料件:" // +
	 * imgCav.getName() // + ((imgCav.getSpec() == null || "".equals(imgCav //
	 * .getSpec().trim())) ? "" : ("/" + imgCav // .getSpec())) + str +
	 * "\n不平衡"); // } if (errorScale > 0.01 || errorScale < -0.01) { if (lt ==
	 * 0) { logImg.append(lgImg); lt++; } String tr = imgCav.getName() +
	 * ((imgCav.getSpec() == null || "".equals(imgCav .getSpec().trim())) ? "" :
	 * ("/" + imgCav .getSpec())) + "\n"; logImg.append(tr); } } if
	 * (logImg.toString().length() > lgImg.length()) {
	 * log.append(logImg.toString()); } if (!log.toString().equals("")) { throw
	 * new RuntimeException(log.toString()); } list =
	 * this.contractCavDao.findContractBomCav(contractCav); double
	 * totalProductUsed = 0.0; double totalUsedAmount = 0.0; for (int i = 0; i <
	 * list.size(); i++) { ContractBomCav bomCav = (ContractBomCav) list.get(i);
	 * / 总耗量
	 */
	/*
	 * double totalAmount = CommonUtils.getDoubleExceptNull(bomCav
	 * .getTotalAmount()); if (totalAmount <= 0) { continue; } double exgTotal =
	 * CommonUtils.getDoubleExceptNull(bomCav .getExgExpTotalAmount()); double
	 * wasteAmount = CommonUtils.getDoubleExceptNull(bomCav .getWasteAmount());
	 * // double waste = // CommonUtils.getDoubleExceptNull(bomCav.getWaste());
	 * double unitWaste = CommonUtils.getDoubleExceptNull(bomCav
	 * .getUnitWaste()); double productUsed = exgTotal unitWaste + wasteAmount;
	 * totalProductUsed += productUsed; totalUsedAmount += totalAmount; double
	 * errorScale = (totalAmount - (productUsed)) / totalAmount; // String str =
	 * "-1%=<[料件耗用总量-（对应成品数量*单耗+损耗量）]/料件耗用量<=1%"; // if (errorScale > 0.01 ||
	 * errorScale < -0.01) { // throw new RuntimeException("料件:" // +
	 * bomCav.getMaterialName() // + ((bomCav.getMaterialSpec() == null || "" //
	 * .equals(bomCav.getMaterialSpec().trim())) ? "" // : ("/" +
	 * bomCav.getMaterialSpec())) // + bomCav.getMaterialSpec() // + " \n和对应成品:"
	 * // + bomCav.getProductName() // + ((bomCav.getProductSpec() == null ||
	 * "".equals(bomCav // .getProductSpec())) ? "" : ("/" + bomCav //
	 * .getProductSpec().trim())) + "\n" + str // + "\n不平衡"); // } if
	 * (errorScale > 0.01 || errorScale < -0.01) { if (lt == 0) {
	 * logImg.append(lgImg); lt++; } String tem = bomCav.getMaterialName() +
	 * ((bomCav.getMaterialSpec() == null || ""
	 * .equals(bomCav.getMaterialSpec().trim())) ? "" : ("/ " +
	 * bomCav.getMaterialSpec())) + " 和对应成品:" + bomCav.getProductName() +
	 * ((bomCav.getProductSpec() == null || "".equals(bomCav .getProductSpec()))
	 * ? "" : ("/" + bomCav .getProductSpec().trim())) + "\n";
	 * logBom.append(tem); } } if (logBom.toString().length() > lgBom.length())
	 * { log.append(logBom.toString()); } if (!log.toString().equals("")) {
	 * throw new RuntimeException(log.toString()); } if (totalUsedAmount <= 0) {
	 * throw new RuntimeException("料件总耗用量小于或等于零！"); } double errorScale =
	 * (totalUsedAmount - totalProductUsed) / totalUsedAmount; String str =
	 * "-1%=<[料件总耗用量-SUM(单项成品耗用量）]/料件总耗用量<=1%"; if (errorScale > 0.01 ||
	 * errorScale < -0.01) { throw new RuntimeException(str + "\n不平衡"); }
	 * 
	 * return true; }
	 */

	/**
	 * 合同核销平衡检查
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return boolean 平衡返回true
	 */
	// hwy2011-11-12
	// public boolean checkContractCavData(ContractCav contractCav) {
	// List list = this.contractCavDao.findContractImgCav(contractCav);
	// final String lgImg = "料件:余料/进口数量大于等于20%，不平衡!";
	// int lt = 0;
	// StringBuffer logImg = new StringBuffer("");
	// StringBuffer log = new StringBuffer("");
	// for (int i = 0; i < list.size(); i++) {
	// ContractImgCav imgCav = (ContractImgCav) list.get(i);
	// double impTotal = CommonUtils.getDoubleExceptNull(imgCav
	// .getImpTotal());
	// if (impTotal <= 0) {
	// continue;
	// }
	// double remainMaterial = CommonUtils.getDoubleExceptNull(imgCav
	// .getRemainMaterial());
	// double errorScale = remainMaterial / impTotal;
	// if (errorScale >= 0.2) {
	// if (lt == 0) {
	// logImg.append("料件：");
	// lt++;
	// }
	// String tr = imgCav.getSeqNum() + "、";
	// logImg.append(tr);
	// }
	// }
	// if (logImg.toString().length() > lgImg.length()) {
	// log.append(logImg.toString() + "余料/进口数量大于等于20%，不平衡!");
	// }
	// if (!log.toString().equals("")) {
	// throw new RuntimeException(log.toString());
	// }
	// return true;
	// }

	// hwy2012-11-12
	// 检查=（（料件耗用量-(成品对应数量*单耗+损耗量））/料件耗用量
	public StringBuffer checkContractCavData(ContractCav contractCav) {
		List list = this.contractCavDao.findContractBomCav(contractCav);
		Map<Integer, String> map = new HashMap<Integer, String>();

		StringBuffer logImg = new StringBuffer("");
		StringBuffer log = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			ContractBomCav imgCav = (ContractBomCav) list.get(i);
			Double impTotal = CommonUtils.getDoubleExceptNull(imgCav
					.getTotalAmount());
			if (impTotal <= Double.valueOf(0).doubleValue()) {
				continue;
			}
			double remainMaterial = CommonUtils.getDoubleExceptNull(imgCav
					.getExgExpTotalAmount()
					* imgCav.getUnitWaste()
					+ imgCav.getWasteAmount());
			double errorScale = (impTotal - remainMaterial) / impTotal;

			if (errorScale > 0.1 || errorScale < -0.1) {
				String str = map.put(imgCav.getSeqProductNum(),imgCav.getProductName());
				
				if (str == null || "".equals(str)) {
					logImg.append("成品：" + "【" + imgCav.getSeqProductNum() + "】"
							+ "序号" + "【" + imgCav.getProductName() + "】"
							+ " 中 \n " + "料件" + "序号" + "【"
							+ imgCav.getSeqMaterialNum() + "】" + "【"
							+ imgCav.getMaterialName() + "】" + " 的 \n " + "");
				} else {
					logImg.append("料件" + "序号" + "【"
							+ imgCav.getSeqMaterialNum() + "】" + "【"
							+ imgCav.getMaterialName() + "】" + "\n " + "");
				}

			}

		}
		StringBuffer logExg = new StringBuffer("");
		StringBuffer logg = new StringBuffer("");
		String a = "料件耗用量-(成品对应数量*单耗+损耗量))/料件耗用量)不在-1% - 1% 之间";
		logExg.append(a);
		if (logImg.toString().length() > 1) {
			logg.append(logImg.toString() + logExg.toString());
			log.append(logg);
		}
		return logg;
	}

	/**
	 * 重排国内购买，将国内购买的料件和排在它的相似料件的下面
	 * 
	 * @param list
	 *            是ContractImgCav型，合同核销料件
	 */
	private void resortDataForInteralBuy(List list) {
		List lsInteralBuy = new ArrayList();
		for (int i = list.size() - 1; i >= 0; i--) {
			ContractImgCav imgCav = (ContractImgCav) list.get(i);
			if (imgCav.getName().indexOf("(国内购买)") >= 0) {
				lsInteralBuy.add(imgCav);
				list.remove(i);
			}
		}
		// System.out.println("国内处理之中"+list.size());
		for (int i = 0; i < lsInteralBuy.size(); i++) {
			ContractImgCav interalBuy = (ContractImgCav) lsInteralBuy.get(i);
			String name = interalBuy.getName().substring(
					interalBuy.getName().indexOf("(国内购买)") + "(国内购买)".length());
			String seq = interalBuy.getSeqNum() == null ? "" : interalBuy
					.getSeqNum().toString();
			// System.out.println("name:"+name);
			//
			// System.out.println("seq:"+seq);

			for (int j = 0; j < list.size(); j++) {
				ContractImgCav imgCav = (ContractImgCav) list.get(j);
				// System.out.println("imgCav.getName():"+imgCav.getName());
				// System.out.println("imgCav.getSeqNum():"+imgCav.getSeqNum());
				// System.out.println(interalBuy.getComplex().equals(imgCav.getComplex()));
				if (interalBuy.getComplex().equals(imgCav.getComplex())
						&& name.equals(imgCav.getName())
						&& seq.equals(imgCav.getSeqNum() == null ? "" : imgCav
								.getSeqNum().toString())) {
					list.add(j + 1, interalBuy);
					break;
				}
			}
		}
		// System.out.println("国内处理之后"+list.size());
	}

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractImgCav型，合同核销料件
	 */
	public List findContractImgCav(ContractCav contractCav,
			boolean notShowAmountIsZero) {
		List<ContractImgCav> list = this.contractCavDao
				.findContractImgCav(contractCav);
		System.out.println("查询：" + list.size());
		if (notShowAmountIsZero) {
			for (int i = list.size() - 1; i >= 0; i--) {
				ContractImgCav imgCav = list.get(i);
				if (imgCav.getImpTotal() == null || imgCav.getImpTotal() <= 0) {
					list.remove(i);
				}
			}
		}
		Collections.sort(list, new Comparator<ContractImgCav>() {
			public int compare(ContractImgCav o1, ContractImgCav o2) {
				if (o1.getSeqNum() == null && o2.getSeqNum() != null) {
					return -1;
				} else if (o1.getSeqNum() != null && o2.getSeqNum() == null) {
					return 1;
				} else if (o1.getSeqNum() == null && o2.getSeqNum() == null) {
					return 0;
				} else {
					Integer seqNum1 = Integer.valueOf(o1.getSeqNum());
					Integer seqNum2 = Integer.valueOf(o2.getSeqNum());
					if (seqNum1 > seqNum2) {
						return 1;
					} else if (seqNum1 == seqNum2) {
						return 0;
					} else if (seqNum1 < seqNum2) {
						return -1;
					}
				}
				return 0;
			}
		});
		// System.out.println("查询："+list.size());
		// if (contractCav.getIsCustoms()) {
		// resortDataForInteralBuy(list);
		// }
		return list;
	}

	/**
	 * 取得报关单金额
	 * 
	 * @return 指定报关单的报关商品信息的统计情况
	 */
	public Map findCustomsDeclarationMoney(Integer impExpFlag) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = this.contractCavDao.findCustomsDeclarationMoney(impExpFlag);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null) {
				map.put(objs[0].toString().trim(), objs[1] == null ? 0.0
						: Double.parseDouble(objs[1].toString()));
			}
		}
		return map;
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param contractCav
	 *            合同核销表头
	 * @return List 是ContractExgCav型，合同核销成品
	 */
	public List findContractExgCav(ContractCav contractCav,
			boolean notShowAmountIsZero) {
		List<ContractExgCav> list = this.contractCavDao
				.findContractExgCav(contractCav);
		if (notShowAmountIsZero) {
			for (int i = list.size() - 1; i >= 0; i--) {
				ContractExgCav exgCav = list.get(i);
				if (exgCav.getExpTotal() == null || exgCav.getExpTotal() <= 0) {
					list.remove(i);
				}
			}
		}
		Collections.sort(list, new Comparator<ContractExgCav>() {
			public int compare(ContractExgCav o1, ContractExgCav o2) {
				if (o1.getSeqNum() == null && o2.getSeqNum() != null) {
					return -1;
				} else if (o1.getSeqNum() != null && o2.getSeqNum() == null) {
					return 1;
				} else if (o1.getSeqNum() == null && o2.getSeqNum() == null) {
					return 0;
				} else {
					Integer seqNum1 = Integer.valueOf(o1.getSeqNum());
					Integer seqNum2 = Integer.valueOf(o2.getSeqNum());
					if (seqNum1 > seqNum2) {
						return 1;
					} else if (seqNum1 == seqNum2) {
						return 0;
					} else if (seqNum1 < seqNum2) {
						return -1;
					}
				}
				return 0;
			}
		});
		return list;
	}

	/**
	 * 数据报核申报
	 * 
	 * @param contractCav
	 */
	public DeclareFileInfo applyContractCav(ContractCav contractCav,
			String taskId, boolean isAppBom) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<ContractCav> lsHead = new ArrayList<ContractCav>();
		List lsCustomsDeclarationCav = new ArrayList();
		List<TempBcsExgImgCav> lsBcsContractExgCav = new ArrayList<TempBcsExgImgCav>();
		List<TempBcsExgImgCav> lsBcsContractImgCav = new ArrayList<TempBcsExgImgCav>();
		List lsBcsContractBomCav = new ArrayList();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		Contract contract = this.contractDao.findContractByEmsNo(
				contractCav.getEmsNo(), DeclareState.PROCESS_EXE);
		if (contract == null) {
			throw new RuntimeException("系统找不到正在执行的纸质手册"
					+ contractCav.getEmsNo());
		}
		CspSignInfo signInfo = bcsMessageLogic.getCspPtsSignInfo(info,
				contract == null ? "" : contract.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(contractCav.getCopEmsNo());
		signInfo.setColDcrTime(1);
		signInfo.setSysType(BcsBusinessType.CANCEL_AFTER_VERIFICA);
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		contractCav.setInputER(signInfo.getIdCard().substring(
				signInfo.getIdCard().length() - 4));
		contractCav.setDeclareType("4");// 电子手册正式报核
		contractCav.setDeclareDate(new Date());
		contractCav.setDeclareState(DeclareState.WAIT_EAA);
		contractCav.setDeclareTimes(1);
		lsHead.add(contractCav);

		lsCustomsDeclarationCav = this.contractCavDao
				.findCustomsDeclarationCav(contractCav);
		TempBcsExgImgCav temp = null;
		List list = this.contractCavDao.findContractImgCav(contractCav);
		for (int i = 0; i < list.size(); i++) {
			ContractImgCav imgCav = (ContractImgCav) list.get(i);
			if (imgCav.getSeqNum() == null) {
				continue;
			}
			// 料件进口总数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.TOTAL_IMP_EXP);
			temp.setAmount(imgCav.getImpTotal());
			lsBcsContractImgCav.add(temp);
			// 深加工结转数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.TRANSFER_IMP_EXP);
			temp.setAmount(imgCav.getTransferFactoryImport());
			lsBcsContractImgCav.add(temp);
			// 产品总耗用量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.PRODUCT_USED);
			temp.setAmount(imgCav.getProductWaste());
			lsBcsContractImgCav.add(temp);
			// 内销数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.INNER_SALES);
			temp.setAmount(imgCav.getInternalAmount());
			lsBcsContractImgCav.add(temp);
			// 退运数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.BACK_MATERIEL_EXPORT);
			temp.setAmount(imgCav.getBackExport());
			lsBcsContractImgCav.add(temp);
			// 料件放弃数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.ABANDON_AMOUNT);
			temp.setAmount(0.0);
			lsBcsContractImgCav.add(temp);
			// 实际料件剩余数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.REMAIN_MATERIAL);
			temp.setAmount(imgCav.getRemainMaterial());
			lsBcsContractImgCav.add(temp);
			// 料件应剩余数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.SHOULD_REMIAN_MATERIAL);
			temp.setAmount(imgCav.getRemainMaterial());
			lsBcsContractImgCav.add(temp);
			// 边角料数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.LEFTOVER_MATERIAL);
			temp.setAmount(imgCav.getLeftoverMaterial());
			lsBcsContractImgCav.add(temp);
			// 余料转出数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.REMAIN_FORWARD_EXPORT);
			temp.setAmount(imgCav.getRemainExport());
			lsBcsContractImgCav.add(temp);
			// 企业库存数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.STOCK_AMOUNT);
			temp.setAmount(imgCav.getStockAmount());
			lsBcsContractImgCav.add(temp);
		}
		list = this.contractCavDao.findContractExgCav(contractCav);
		for (int i = 0; i < list.size(); i++) {
			ContractExgCav exgCav = (ContractExgCav) list.get(i);
			if (exgCav.getSeqNum() == null) {
				continue;
			}
			// 成品出口总数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.TOTAL_IMP_EXP);
			temp.setAmount(exgCav.getExpTotal());
			lsBcsContractExgCav.add(temp);
			// 深加工结转数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.TRANSFER_IMP_EXP);
			temp.setAmount(exgCav.getTransferExpAmount());
			lsBcsContractExgCav.add(temp);
			// 成品放弃数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.ABANDON_AMOUNT);
			temp.setAmount(0.0);
			lsBcsContractExgCav.add(temp);
			// 成品退换出口数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.REWORK_EXPORT);
			temp.setAmount(exgCav.getReworkExport());
			lsBcsContractExgCav.add(temp);
			// 成品退换进口数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.BACK_FACTORY_REWORK);
			temp.setAmount(exgCav.getBackFactoryRework());
			lsBcsContractExgCav.add(temp);
			// 企业库存数量
			temp = new TempBcsExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(BcsQuantityCavType.STOCK_AMOUNT);
			temp.setAmount(exgCav.getStockAmount());
			lsBcsContractExgCav.add(temp);
		}
		/**
		 * 单损耗
		 */
		if (isAppBom) {
			lsBcsContractBomCav = this.contractCavDao
					.findContractBomCavChanged(contractCav);
		}
		// Contract contract = this.contractDao
		// .findExingContractByEmsNo(contractCav.getEmsNo());

		String formatFile = "BcsContractCavFormat.xml";
		hmData.put("BcsPtsSignInfo", lsSignInfo);
		hmData.put("BcsContractCav", lsHead);
		hmData.put("BcsCustomsDeclarationCav", lsCustomsDeclarationCav);
		hmData.put("BcsContractExgCav", lsBcsContractExgCav);
		hmData.put("BcsContractImgCav", lsBcsContractImgCav);
		hmData.put("BcsContractBomCav", lsBcsContractBomCav);

		DeclareFileInfo fileInfo = bcsMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.contractCavDao.saveContractCav(contractCav);
		return fileInfo;
	}

	/**
	 * 核销处理回执
	 * 
	 * @param bcsContractCav
	 */
	public String processBcsContractCav(final ContractCav bcsContractCav,
			List lsReturnFile) {
		final Contract contract = contractDao.findContractByEmsNo(
				bcsContractCav.getEmsNo(), DeclareState.PROCESS_EXE);
		if (contract == null) {
			throw new RuntimeException("没有正在执行的手册:" + bcsContractCav.getEmsNo());
		}
		return bcsMessageLogic.processMessage(
				BcsBusinessType.CANCEL_AFTER_VERIFICA,
				bcsContractCav.getCopEmsNo(), new CspProcessMessage() {
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						bcsContractCav.setDeclareState(DeclareState.APPLY_POR);
						contractCavDao.saveContractCav(bcsContractCav);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						bcsContractCav
								.setDeclareState(DeclareState.CHANGING_CANCEL);
						contract.setDeclareState(DeclareState.CHANGING_CANCEL);
						contract.setIsCancel(true);
						contractDao.saveContract(contract);
						contractCavDao.saveContractCav(bcsContractCav);
					}
				}, lsReturnFile);
	}

	/**
	 * 改变通关手册核销表头的申报状态
	 * 
	 * @param head
	 * @param declareState
	 */
	public ContractCav changeContractCavDeclareState(ContractCav head,
			String declareState) {
		head = (ContractCav) this.contractCavDao.load(head.getClass(),
				head.getId());
		if (head == null
				|| !DeclareState.WAIT_EAA.equals(head.getDeclareState())) {
			return head;
		}
		head.setDeclareState(declareState);
		this.contractDao.saveOrUpdate(head);
		return head;
	}

	/**
	 * 查找一本合同中的报关单商品明细
	 * 
	 * @param contractID
	 *            合同ID
	 * @param isMateriel
	 *            为ture则料件，反之为成品
	 * @return 反回结果List
	 */
	public List findCustomsDeclaretionDetailByContract(String contractID,
			boolean isMateriel) {
		return this.contractDao.findCustomsDeclaretionDetailByContract(
				contractID, isMateriel);

	}

	/**
	 * 计算合同核销表
	 * 
	 * @param request
	 * @param emdNo
	 *            手册编号
	 * @param contractNo
	 *            合同号
	 * @return
	 */
	public TempContractCheckCav findCavContract(Contract contract) {
		// TODO Auto-generated method stub
		TempContractCheckCav contractCheckCav = new TempContractCheckCav();
		String emsNo = contract.getEmsNo();
		// 获取报关单
		List<BcsCustomsDeclaration> bcs = this.contractDao
				.findBcsCustomsDeclaration(emsNo);
		if (bcs.size() == 0) {
			return null;
		}
		// 统计报关单相关合同核销数据
		for (BcsCustomsDeclaration b : bcs) {

			if (b.getImpExpType() == ImpExpType.DIRECT_IMPORT
					&& b.getImpExpFlag() == ImpExpFlag.IMPORT) {// 料件进口报关单
																// 直接进口料件报关单
				contractCheckCav.setImpDirectCDCount(CommonUtils
						.getIntegerExceptNull(contractCheckCav
								.getImpDirectCDCount()) + 1);
				// 直接进口料件件数
				contractCheckCav
						.setImpDirectPieces(CommonUtils
								.getIntegerExceptNull(contractCheckCav
										.getImpDirectPieces())
								+ CommonUtils.getIntegerExceptNull(b
										.getCommodityNum()));
				// 直接进口料件毛重
				contractCheckCav.setImpDirectGrossWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpDirectGrossWeight())
						+ CommonUtils.getDoubleExceptNull(b.getGrossWeight()));
				// 直接进口料件净重
				contractCheckCav.setImpDirectNetWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpDirectNetWeight())
						+ CommonUtils.getDoubleExceptNull(b.getNetWeight()));

				// 总值
				ProcessAndTotal pt = metreTotalValue(b, contract);
				// 直接进口总值
				contractCheckCav.setImpDirectTotalValue(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpDirectTotalValue())
						+ CommonUtils.getDoubleExceptNull(pt.getTotalVale()));
				// 国内购料总值
				contractCheckCav.setTatalValeGuoLei(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getTatalValeGuoLei())
						+ CommonUtils.getDoubleExceptNull(pt
								.getTatalValeGuoLei()));
				// 国内购料总料重
				contractCheckCav.setNetWeightGuoLei(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getNetWeightGuoLei())
						+ CommonUtils.getDoubleExceptNull(pt
								.getNetWeightGuoLei()));
				// 国内购料包装重
				contractCheckCav.setPackWeightGuoLei(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getPackWeightGuoLei())
						+ CommonUtils.getDoubleExceptNull(pt
								.getPackWeightGuoLei()));
			} else if (b.getImpExpType() == 1 && b.getImpExpFlag() == 0) {// 料件进口转厂
																			// 转厂进口报关单
				contractCheckCav.setImpTransferCDCount(CommonUtils
						.getIntegerExceptNull(contractCheckCav
								.getImpTransferCDCount()) + 1);

				// 转厂进口件数
				contractCheckCav
						.setImpTransferPieces(CommonUtils
								.getIntegerExceptNull(contractCheckCav
										.getImpTransferPieces())
								+ CommonUtils.getIntegerExceptNull(b
										.getCommodityNum()));

				// 转厂进口毛重
				contractCheckCav.setImpTransferGrossWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpTransferGrossWeight())
						+ CommonUtils.getDoubleExceptNull(b.getGrossWeight()));

				// 转厂进口净重
				contractCheckCav.setImpTransferNetWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpTransferNetWeight())
						+ CommonUtils.getDoubleExceptNull(b.getNetWeight()));

				// 总值
				ProcessAndTotal pt = metreTotalValue(b, contract);
				// 转厂进口总值
				contractCheckCav.setImpTransferTotalValue(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpTransferTotalValue())
						+ CommonUtils.getDoubleExceptNull(pt.getTotalVale()));
				// 国内购料总值
				contractCheckCav.setTatalValeGuoLei(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getTatalValeGuoLei())
						+ CommonUtils.getDoubleExceptNull(pt
								.getTatalValeGuoLei()));
				// 国内购料总料重
				contractCheckCav.setNetWeightGuoLei(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getNetWeightGuoLei())
						+ CommonUtils.getDoubleExceptNull(pt
								.getNetWeightGuoLei()));
				// 国内购料包装重
				contractCheckCav.setPackWeightGuoLei(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getPackWeightGuoLei())
						+ CommonUtils.getDoubleExceptNull(pt
								.getPackWeightGuoLei()));

			} else if (b.getImpExpType() == 2 && b.getImpExpFlag() == 0) {// 退厂返工
																			// 退厂返工报关单
				contractCheckCav.setImpProcuderCDCount(CommonUtils
						.getIntegerExceptNull(contractCheckCav
								.getImpProcuderCDCount()) + 1);

				// 退厂返工件数
				contractCheckCav
						.setImpProcuderPieces(CommonUtils
								.getIntegerExceptNull(contractCheckCav
										.getImpProcuderPieces())
								+ CommonUtils.getIntegerExceptNull(b
										.getCommodityNum()));

				// 退厂返工毛重
				contractCheckCav.setImpProcuderGrossWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpProcuderGrossWeight())
						+ CommonUtils.getDoubleExceptNull(b.getGrossWeight()));

				// 退厂返工净重
				contractCheckCav.setImpProcuderNetWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpProcuderNetWeight())
						+ CommonUtils.getDoubleExceptNull(b.getNetWeight()));

				// 加工费和总值
				ProcessAndTotal pt = procederProcessAndTotal(b, contract);
				contractCheckCav.setImpProcuderTotalValue(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpProcuderTotalValue())
						+ CommonUtils.getDoubleExceptNull(pt.getTotalVale()));
				contractCheckCav.setImpProcuderProcessPrice(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getImpProcuderProcessPrice())
						+ CommonUtils.getDoubleExceptNull(pt.getProcessVale()));

			} else if (b.getImpExpType() == 4 && b.getImpExpFlag() == 1) {// 成品直接出口
																			// 成品直接出口报关单
				contractCheckCav.setExpDirectCDCount(CommonUtils
						.getIntegerExceptNull(contractCheckCav
								.getExpDirectCDCount()) + 1);

				// 成品直接出口毛重
				contractCheckCav
						.setExpDirectPieces(CommonUtils
								.getIntegerExceptNull(contractCheckCav
										.getExpDirectPieces())
								+ CommonUtils.getIntegerExceptNull(b
										.getCommodityNum()));

				// 成品直接出口毛重
				contractCheckCav.setExpDirectGrossWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpDirectGrossWeight())
						+ CommonUtils.getDoubleExceptNull(b.getGrossWeight()));

				// 成品直接出口净重
				contractCheckCav.setExpDirectNetWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpDirectNetWeight())
						+ CommonUtils.getDoubleExceptNull(b.getNetWeight()));

				// 加工费和总值
				ProcessAndTotal pt = procederProcessAndTotal(b, contract);
				contractCheckCav.setExpDirectTotalValue(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpDirectTotalValue())
						+ CommonUtils.getDoubleExceptNull(pt.getTotalVale()));
				contractCheckCav.setExpDirectProcessPrice(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpDirectProcessPrice())
						+ CommonUtils.getDoubleExceptNull(pt.getProcessVale()));

			} else if (b.getImpExpType() == 5 && b.getImpExpFlag() == 1) {// 转厂出口
																			// 成品转厂出口报关单
				contractCheckCav.setExpTransferCDCount(CommonUtils
						.getIntegerExceptNull(contractCheckCav
								.getExpTransferCDCount()) + 1);
				// 成品转厂出口件数
				contractCheckCav
						.setExpTransferPieces(CommonUtils
								.getIntegerExceptNull(contractCheckCav
										.getExpTransferPieces())
								+ CommonUtils.getIntegerExceptNull(b
										.getCommodityNum()));
				// 成品转厂出口毛重
				contractCheckCav.setExpTransferGrossWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpTransferGrossWeight())
						+ CommonUtils.getDoubleExceptNull(b.getGrossWeight()));
				// 成品转厂出口净重
				contractCheckCav.setExpTransferNetWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpTransferNetWeight())
						+ CommonUtils.getDoubleExceptNull(b.getNetWeight()));
				// 加工费和总值
				ProcessAndTotal pt = procederProcessAndTotal(b, contract);
				contractCheckCav.setExpTransferTotalValue(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpTransferTotalValue())
						+ CommonUtils.getDoubleExceptNull(pt.getTotalVale()));
				contractCheckCav.setExpTransferProcessPrice(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpTransferProcessPrice())
						+ CommonUtils.getDoubleExceptNull(pt.getProcessVale()));
			} else if (b.getImpExpType() == 6 && b.getImpExpFlag() == 1) {// 退料出口
																			// 退料出口报关单
				contractCheckCav.setExpQuitMeCDCount(CommonUtils
						.getIntegerExceptNull(contractCheckCav
								.getExpQuitMeCDCount()) + 1);
				// 退料出口件数
				contractCheckCav
						.setExpQuitMePieces(CommonUtils
								.getIntegerExceptNull(contractCheckCav
										.getExpQuitMePieces())
								+ CommonUtils.getIntegerExceptNull(b
										.getCommodityNum()));
				// 退料出口毛重
				contractCheckCav.setExpQuitGrossWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpQuitGrossWeight())
						+ CommonUtils.getDoubleExceptNull(b.getGrossWeight()));
				// 退料出口净重
				contractCheckCav.setExpQuitNetWeight(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpQuitNetWeight())
						+ CommonUtils.getDoubleExceptNull(b.getNetWeight()));

				// 总值
				ProcessAndTotal pt = metreTotalValue(b, contract);
				contractCheckCav.setExpQuitTotalvalue(CommonUtils
						.getDoubleExceptNull(contractCheckCav
								.getExpQuitTotalvalue())
						+ CommonUtils.getDoubleExceptNull(pt.getTotalVale()));
			}

			// 进口报关单总和
			contractCheckCav.setImpTotalCDCount(CommonUtils
					.getIntegerExceptNull(contractCheckCav
							.getImpDirectCDCount())
					+ CommonUtils.getIntegerExceptNull(contractCheckCav
							.getImpTransferCDCount())
					+ CommonUtils.getIntegerExceptNull(contractCheckCav
							.getImpProcuderCDCount()));

			// 进口件数总和
			contractCheckCav
					.setImpTotalPieces(CommonUtils
							.getIntegerExceptNull(contractCheckCav
									.getImpDirectPieces())
							+ CommonUtils.getIntegerExceptNull(contractCheckCav
									.getImpTransferPieces())
							+ CommonUtils.getIntegerExceptNull(contractCheckCav
									.getImpProcuderPieces()));

			// 进口毛重总和
			contractCheckCav.setImpTotalGrossWeight(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getImpDirectGrossWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpTransferGrossWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpProcuderGrossWeight()));

			// 进口净重总和
			contractCheckCav.setImpTotalNetWeight(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getImpDirectNetWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpTransferNetWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpProcuderNetWeight()));

			// 出口报关单总和
			contractCheckCav.setExpTotalCDCount(CommonUtils
					.getIntegerExceptNull(contractCheckCav
							.getExpDirectCDCount())
					+ CommonUtils.getIntegerExceptNull(contractCheckCav
							.getExpTransferCDCount())
					+ CommonUtils.getIntegerExceptNull(contractCheckCav
							.getExpQuitMeCDCount()));

			// 出口件数总和
			contractCheckCav
					.setExpTotalPieces(CommonUtils
							.getIntegerExceptNull(contractCheckCav
									.getExpDirectPieces())
							+ CommonUtils.getIntegerExceptNull(contractCheckCav
									.getExpTransferPieces())
							+ CommonUtils.getIntegerExceptNull(contractCheckCav
									.getExpQuitMePieces()));

			// 出口毛重总和
			contractCheckCav.setExpTotalGrossWeight(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getExpDirectGrossWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpTransferGrossWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpQuitGrossWeight()));

			// 出口净重总和
			contractCheckCav.setExpTotalNetWeight(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getExpDirectNetWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpTransferNetWeight())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpQuitNetWeight()));

			// //料件进口金额 (其中已包括余料结转进口金额)
			// double directImportMoney = this.contractCavDao
			// .findCutomsDeclarationMoneyByContract(contract,
			// ImpExpType.DIRECT_IMPORT, ImpExpFlag.IMPORT, null);
			// contractCheckCav.setImpDirectTotalValue(directImportMoney);
			//
			// //料件转厂进口金额
			// double transferFactoryImportMoney = this.contractCavDao
			// .findCutomsDeclarationMoneyByContract(contract,
			// ImpExpType.TRANSFER_FACTORY_IMPORT,
			// ImpExpFlag.IMPORT, null);
			// contractCheckCav.setImpTransferTotalValue(transferFactoryImportMoney);
			//
			// // 退厂返工金额
			// double backFactoryReworkMoney = this.contractCavDao
			// .findCutomsDeclarationMoneyByContract(contract,
			// ImpExpType.BACK_FACTORY_REWORK, ImpExpFlag.IMPORT,
			// null);
			// contractCheckCav.setImpProcuderTotalValue(backFactoryReworkMoney);

			// // 成品出口金额
			// double directExportMoney = this.contractCavDao
			// .findCutomsDeclarationMoneyByContract(contract,
			// ImpExpType.DIRECT_EXPORT, ImpExpFlag.EXPORT, null);
			// contractCheckCav.setExpDirectTotalValue(directExportMoney);
			//
			// //成品转厂出口金额
			// double transferFactoryExportMoney = this.contractCavDao
			// .findCutomsDeclarationMoneyByContract(contract,
			// ImpExpType.TRANSFER_FACTORY_EXPORT,
			// ImpExpFlag.EXPORT, null);
			// contractCheckCav.setExpTransferTotalValue(transferFactoryExportMoney);
			//
			// //退料出口金额(退换+复出)
			// double backMaterialExportMoney = this.contractCavDao
			// .findCutomsDeclarationMoneyByContract(contract,
			// ImpExpType.BACK_MATERIEL_EXPORT, ImpExpFlag.EXPORT,
			// null);// new String[]{"0300","0700"}
			// contractCheckCav.setExpReturnMeTotalValue(backMaterialExportMoney);

			// 进口总值
			contractCheckCav.setImpTotalvalue(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getImpDirectTotalValue())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpTransferTotalValue())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpProcuderTotalValue()));

			// 进口加工费
			contractCheckCav.setImpTotalProcessPrice(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getImpDirectProcessPrice())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpTransferProcessPrice())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getImpProcuderProcessPrice()));

			// 出口总值总和
			contractCheckCav.setExpTotalvalue(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getExpDirectTotalValue())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpTransferTotalValue())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpQuitTotalvalue()));

			// 出口加工费总和
			contractCheckCav.setExpTotalProcessPrice(CommonUtils
					.getDoubleExceptNull(contractCheckCav
							.getExpDirectProcessPrice())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpTransferProcessPrice())
					+ CommonUtils.getDoubleExceptNull(contractCheckCav
							.getExpQuitProcessPrice()));

		}
		return contractCheckCav;
	}

	/**
	 * 成品计算加工费及总值
	 */
	public ProcessAndTotal procederProcessAndTotal(
			BcsCustomsDeclaration bcsCustomsDeclaration, Contract contract) {
		// 取得报关单里的商品

		List<BcsCustomsDeclarationCommInfo> comms = this.contractDao
				.findCommInfoByBGDHead(bcsCustomsDeclaration);
		Double totalVale = 0d;
		Double processVale = 0d;
		for (BcsCustomsDeclarationCommInfo comm : comms) {
			ContractExg contractExg = contractDao.findContractExgByComplex(
					comm, contract);
			if (contractExg == null) {
				throw new RuntimeException("在手册：" + contract.getEmsNo()
						+ "中找不到成品序号是:" + comm.getCommSerialNo() + "的成品");
			}
			totalVale = totalVale
					+ CommonUtils.getDoubleExceptNull(comm.getCommAmount())
					* CommonUtils.getDoubleExceptNull(contractExg
							.getUnitPrice());
			processVale = processVale
					+ CommonUtils.getDoubleExceptNull(comm.getCommAmount())
					* CommonUtils.getDoubleExceptNull(contractExg
							.getProcessUnitPrice());
			System.out.println("加工费单价"
					+ CommonUtils.getDoubleExceptNull(contractExg
							.getProcessUnitPrice()));
		}
		return new ProcessAndTotal(totalVale, processVale);
	}

	/**
	 * 料件计算加工费及总值
	 */
	public ProcessAndTotal metreTotalValue(
			BcsCustomsDeclaration bcsCustomsDeclaration, Contract contract) {
		// 取得报关单里的商品
		List<BcsCustomsDeclarationCommInfo> listCommInfo = this.contractDao
				.findCommInfoByBGDHead(bcsCustomsDeclaration);
		Double totalVale = 0d;
		Double processVale = 0d;
		Double tatalValeGuoLei = 0d;
		Double netWeightGuoLei = 0d;
		Double packWeightGuoLei = 0d;
		for (BcsCustomsDeclarationCommInfo comm : listCommInfo) {
			ContractImg contractImg = contractDao.findComtractImgByComplex(
					comm, contract);
			if (contractImg == null) {
				throw new RuntimeException("在手册：" + contract.getEmsNo()
						+ "中找不到料件序号是:" + comm.getCommSerialNo() + "的料件");
			}
			totalVale = totalVale
					+ CommonUtils.getDoubleExceptNull(comm.getCommAmount())
					* CommonUtils.getDoubleExceptNull(contractImg
							.getDeclarePrice());
			if (comm.getCommName().indexOf("内购") > 0
					|| comm.getCommSpec().indexOf("内购") > 0) {
				tatalValeGuoLei += totalVale;
				netWeightGuoLei += CommonUtils.getDoubleExceptNull(comm
						.getNetWeight());
				packWeightGuoLei += CommonUtils.getDoubleExceptNull(comm
						.getGrossWeight())
						- CommonUtils.getDoubleExceptNull(comm.getNetWeight());
			}
		}
		ProcessAndTotal pt = new ProcessAndTotal(totalVale, processVale);
		pt.setTatalValeGuoLei(tatalValeGuoLei);
		pt.setNetWeightGuoLei(netWeightGuoLei);
		pt.setPackWeightGuoLei(packWeightGuoLei);
		return pt;
	}

	/**
	 * 加工费及总值
	 * 
	 * @author Administrator
	 * 
	 */
	class ProcessAndTotal {
		/**
		 * 总值
		 */
		Double totalVale = 0d;

		/**
		 * 加工费总值
		 */
		Double processVale = 0d;

		/**
		 * 国内购料总值
		 */
		Double tatalValeGuoLei = 0d;
		/**
		 * 国内购料净重
		 */
		Double netWeightGuoLei = 0d;
		/**
		 * 国内购料包装重
		 */
		Double packWeightGuoLei = 0d;

		public ProcessAndTotal(Double totalVale, Double processVale) {
			super();
			this.totalVale = totalVale;
			this.processVale = processVale;
		}

		public Double getTotalVale() {
			return totalVale;
		}

		public void setTotalVale(Double totalVale) {
			this.totalVale = totalVale;
		}

		public Double getProcessVale() {
			return processVale;
		}

		public void setProcessVale(Double processVale) {
			this.processVale = processVale;
		}

		public Double getTatalValeGuoLei() {
			return tatalValeGuoLei;
		}

		public void setTatalValeGuoLei(Double tatalValeGuoLei) {
			this.tatalValeGuoLei = tatalValeGuoLei;
		}

		public Double getNetWeightGuoLei() {
			return netWeightGuoLei;
		}

		public void setNetWeightGuoLei(Double netWeightGuoLei) {
			this.netWeightGuoLei = netWeightGuoLei;
		}

		public Double getPackWeightGuoLei() {
			return packWeightGuoLei;
		}

		public void setPackWeightGuoLei(Double packWeightGuoLei) {
			this.packWeightGuoLei = packWeightGuoLei;
		}

	}

	/**
	 * 合同国内购料清单表
	 */
	public List setContractCavDomesticPurchaseBill(String id) {
		List list1 = new ArrayList();
		List list = contractCavDao.findContractCavDomesticPurchaseBill(id);
		System.out.println("服务器端的list长度是" + list.size());
		// TempContractDomesticPurchaseBill tcb=null;
		Integer serialNumber = null;
		String imgName = null;
		String unit = null;
		double amountForDomesticPurchase = 0.0;
		double wightForExgDomesticPurchase = 0.0;
		double imgWight = 0.0;
		double totalPrice = 0.0;
		for (int i = 0; i < list.size(); i++) {
			TempContractCavDomesticPurchaseBill tcb = new TempContractCavDomesticPurchaseBill();
			Object[] obj = (Object[]) list.get(i);
			serialNumber = (Integer) obj[0];
			imgName = (String) obj[1];
			unit = (String) obj[2];
			amountForDomesticPurchase = (Double) obj[3];
			wightForExgDomesticPurchase = (Double) obj[4];
			// System.out.println("wightForExgDomesticPurchase="
			// + wightForExgDomesticPurchase);
			imgWight = (Double) obj[5];
			totalPrice = (Double) obj[6];
			tcb.setSerialNumber(serialNumber);
			tcb.setImgName(imgName);
			tcb.setUnit(unit);
			tcb.setAmountForDomesticPurchase(amountForDomesticPurchase);
			tcb.setWightForExgDomesticPurchase(wightForExgDomesticPurchase);
			tcb.setImgWight(imgWight);
			tcb.setTotalPrice(totalPrice);
			list1.add(tcb);
		}

		return list1;
	}

	/**
	 * 打印备案总耗与实际总耗对照表
	 */
	public List setBomFilingRealityCompareBill(String contractCavId) {
		List<TempBomFilingRealityCompareBill> list1 = new ArrayList<TempBomFilingRealityCompareBill>();
		List list = contractCavDao
				.findBomFilingRealityCompareBill(contractCavId);
		System.out.println("服务器端的list长度是" + list.size());
		Integer serialNumber = null;
		String imgName = null;
		String unit = null;
		double importAmount = 0.0;
		double filingConsumption = 0.0;
		double realityConsumption = 0.0;
		double difference = 0.0;
		String explain = null;
		for (int i = 0; i < list.size(); i++) {
			// System.out.println("服务器端的list长度是"+list.size());
			TempBomFilingRealityCompareBill temp = new TempBomFilingRealityCompareBill();
			Object[] obj = (Object[]) list.get(i);
			serialNumber = (Integer) obj[0];
			imgName = (String) obj[1];
			unit = (String) obj[2];
			filingConsumption = (Double) obj[3];
			realityConsumption = (Double) obj[4];
			// realityConsumption=0.0;
			importAmount = (Double) obj[5];
			difference = (Double) obj[6];
			explain = "";
			temp.setSerialNumber(serialNumber);
			temp.setImgName(imgName);
			temp.setUnit(unit);
			temp.setImportAmount(importAmount);
			temp.setFilingConsumption(filingConsumption);
			temp.setRealityConsumption(realityConsumption);
			temp.setDifference(difference);
			temp.setExplain(explain);
			list1.add(temp);
		}
		return list1;
	}

	/**
	 * 修改通关手册料件的修改标志
	 * 
	 * @param list
	 * @param modifyMark
	 */
	public void changeContractImgModifyMark(List list, String modifyMark) {
		if (ModifyMarkState.DELETED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractBomCav contractBomCav = (ContractBomCav) list.get(i);
				if (contractBomCav.getModifyMark().equals(modifyMark)) {
					continue;
				}
				// if
				// (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				// // this.contractDao.deleteContractImg(contractImg);
				// contractImg.setModifyMark(ModifyMarkState.DELETED);
				// this.contractDao.saveContractImg(contractImg);
				// } else {
				// int count = this.contractDao
				// .findBcsCustomsDeclarationCommInfoCount(contractImg
				// .getContract().getEmsNo(), contractImg
				// .getSeqNum(), true);
				// if (count > 0) {
				// throw new RuntimeException("已经有报关单用到此料件"
				// + contractImg.getSeqNum() + ":"
				// + contractImg.getComplex().getCode() + ",所以不能删除");
				// }
				contractBomCav.setModifyMark(ModifyMarkState.DELETED);
				this.contractCavDao.saveContractBomCav(contractBomCav);
				// }
				/**
				 * 查找合同BOM 来自 合同料件序号
				 */
				// List contractBomList = this.contractDao
				// .findContractBomByImgSeqNum(contractImg);
				// this.deleteContractBom(contractBomList);
			}

		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				// || ModifyMarkState.DELETED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				ContractBomCav contractBomCav = (ContractBomCav) list.get(i);
				// if (contractImg.getModifyMark().equals(modifyMark)) {
				// continue;
				// }
				// if
				// (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				// continue;
				// } else
				if (ModifyMarkState.DELETED.equals(contractBomCav
						.getModifyMark())) {
					contractBomCav.setModifyMark(modifyMark);
					this.contractCavDao.saveContractBomCav(contractBomCav);
					// /**
					// * 查找合同BOM 来自 合同料件序号
					// */
					// List contractBomList = this.contractDao
					// .findContractBomByImgSeqNum(contractImg);
					// this.rollbackContractExg(contractBomList, modifyMark);
				} else {
					contractBomCav.setModifyMark(modifyMark);
					this.contractCavDao.saveContractBomCav(contractBomCav);
				}
			}
		}
	}

	public DzscEmsExgBill getDzscEmsExgBillExgByContract(String contractId,
			Integer exgId, String contractNo) {
		return this.contractCavDao.getDzscEmsExgBillExgByContract(contractId,
				exgId, contractNo);
	}

}
