/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.checkcancel.logic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractcav.entity.ContractExgCav;
import com.bestway.bcus.custombase.entity.parametercode.Deduc;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspProcessMessage;
import com.bestway.common.tools.logic.ToolsLogic;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.checkcancel.dao.DzscContractCavDao;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.checkcancel.entity.DzscCheckImg;
import com.bestway.dzsc.checkcancel.entity.DzscContractBomCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractCavTotalValue;
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractImgCav;
import com.bestway.dzsc.checkcancel.entity.DzscContractUnitWasteCav;
import com.bestway.dzsc.checkcancel.entity.DzscCustomsDeclarationCav;
import com.bestway.dzsc.checkcancel.entity.DzscQuantityCavType;
import com.bestway.dzsc.checkcancel.entity.DzscQuantityCheckType;
import com.bestway.dzsc.checkcancel.entity.TempDzscCheckImgDetil;
import com.bestway.dzsc.checkcancel.entity.TempDzscExgImgCav;
import com.bestway.dzsc.checkcancel.entity.TempExpQPCavMsgSelectParam;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.dzsc.message.logic.DzscMessageLogic;

/**
 * com.bestway.dzsc.checkcancel.logic.DzscContractCavLogic
 * 
 * @author Administrator
 */
public class DzscContractCavLogic {
	private DzscContractCavDao contractCavDao;

	private DzscDao dzscDao;

	private DzscMessageLogic dzscMessageLogic;

	private ToolsLogic toolsLogic = null;

	public DzscMessageLogic getDzscMessageLogic() {
		return dzscMessageLogic;
	}

	public void setDzscMessageLogic(DzscMessageLogic dzscExportMessageLogic) {
		this.dzscMessageLogic = dzscExportMessageLogic;
	}

	/**
	 * 获取 DzscContractCavDao
	 * 
	 * @return DzscContractCavDao
	 */
	public DzscContractCavDao getContractCavDao() {
		return contractCavDao;
	}

	/**
	 * set DzscContractCavDao
	 * 
	 * @param contractCavDao
	 */
	public void setContractCavDao(DzscContractCavDao contractCavDao) {
		this.contractCavDao = contractCavDao;
	}

	/**
	 * 获取 DzscDao
	 * 
	 * @return DzscDao
	 */
	public DzscDao getDzscDao() {
		return dzscDao;
	}

	/**
	 * set DzscDao
	 * 
	 * @param dzscDao
	 */
	public void setDzscDao(DzscDao dzscDao) {
		this.dzscDao = dzscDao;
	}

	/**
	 * @return the toolsLogic
	 */
	public ToolsLogic getToolsLogic() {
		return toolsLogic;
	}

	/**
	 * @param toolsLogic
	 *            the toolsLogic to set
	 */
	public void setToolsLogic(ToolsLogic toolsLogic) {
		this.toolsLogic = toolsLogic;
	}

	/**
	 * 电子手册核销计算(包含自用和海关)，计算进出口总金额
	 * 
	 * @param contract
	 *            电子手册手册表头
	 */
	public void cavContract(String emsNo, String taskId) {
		List list = this.dzscDao.findDzscEmsPorHeadExcu(emsNo);
		if (list.size() <= 0 || list.get(0) == null) {
			throw new RuntimeException("没有找到正在执行的手册号是" + emsNo + "的手册");
		}
		DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) list.get(0);
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		cavContract(dzscEmsPorHead, true, info);
	}

	/**
	 * 重新计算自用核销表
	 * 
	 * @param contract
	 *            电子手册手册表头
	 */
	public void reMakeSelfuseContractCav(String emsNo, String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在重新计算自用核销表");
		}
		List list = this.dzscDao.findDzscEmsPorHeadExcu(emsNo);
		if (list.size() <= 0 || list.get(0) == null) {
			throw new RuntimeException("没有找到正在执行的手册号是" + emsNo + "的手册");
		}
		DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) list.get(0);
		list = this.contractCavDao.findContractCav(emsNo, false);
		if (list.size() < 1) {
			throw new RuntimeException("自用核销表为空");
		}
		DzscContractCav contractCav = (DzscContractCav) list.get(0);
		if (contractCav == null) {
			throw new RuntimeException("自用核销表为空");
		}
		if (info != null) {
			info.setMethodName("正在删除旧的自用核销表资料");
		}
		this.contractCavDao.deleteCustomsDeclarationCav(contractCav);
		this.contractCavDao.deleteContractExgCav(contractCav);
		this.contractCavDao.deleteContractImgCav(contractCav);
		this.contractCavDao.deleteContractBomCav(contractCav);
		// -----------------------------------------------------------------
		// this.contractCavDao.deleteAll(this.contractCavDao
		// .findCustomsDeclarationCav(contractCav));
		// this.contractCavDao.deleteAll(this.contractCavDao
		// .findContractImgCav(contractCav));
		// this.contractCavDao.deleteAll(this.contractCavDao
		// .findContractExgCav(contractCav));
		// this.contractCavDao.deleteAll(this.contractCavDao
		// .findContractBomCav(contractCav));
		// -------------------------------------------------------------
		this.contractCavDao.delete(contractCav);// 删除电子手册核销合同
		cavContract(dzscEmsPorHead, false, info);
	}

	/**
	 * 电子手册核销计算，计算进出口总金额
	 * 
	 * @param dzscEmsPorHead
	 *            电子手册手册表头
	 * @param isCustoms
	 *            自用核销、海关核销判断 true为海关核销
	 */
	private void cavContract(DzscEmsPorHead dzscEmsPorHead, boolean isCustoms,
			ProgressInfo info) {
		if (info != null) {
			info.setMethodName("正在计算核销表表头");
		}
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		DzscParameterSet dzscParameterSet = null;
		dzscParameterSet = dzscDao.findDzscParameterSet();		
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null){
			decimalLength = dzscParameterSet.getReportDecimalLength();
		}
		DzscContractCav contractCav = new DzscContractCav();
		contractCav.setEmsNo(dzscEmsPorHead.getEmsNo());
		contractCav.setCopEmsNo(dzscEmsPorHead.getCopTrNo());
		contractCav.setContractNo(dzscEmsPorHead.getIeContractNo());
		contractCav.setEndDate(dzscEmsPorHead.getEndDate());
		contractCav.setBeginDate(dzscEmsPorHead.getBeginDate());
		contractCav.setEmsApprNo(dzscEmsPorHead.getEmsApprNo());
		contractCav.setCreateDate(new Date());
		contractCav.setImpCDCount(this.contractCavDao
				.findCutomsDeclarationCountByContract(dzscEmsPorHead, true));
		/**
		 * 料件进口金额 (其中已包括余料结转进口金额)
		 */
		double directImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.DIRECT_IMPORT, ImpExpFlag.IMPORT, null);
		/**
		 * 料件转厂进口金额
		 */
		double transferFactoryImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.TRANSFER_FACTORY_IMPORT, ImpExpFlag.IMPORT,
						null);
		// /**
		// * 余料结转出口金额
		// */
		// double remainForwordExportMoney = this.contractCavDao
		// .findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
		// ImpExpType.REMAIN_FORWARD_EXPORT, ImpExpFlag.EXPORT,
		// new String[] { "0258", "0657" });
		/**
		 * 余料结转进口金额
		 */
		double remainForwordImportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.REMAIN_FORWARD_IMPORT, ImpExpFlag.IMPORT,
						null);// new String[] { "0258", "0657" }
		/**
		 * 余料结转出口金额
		 */
		double remainForwordExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.REMAIN_FORWARD_EXPORT, ImpExpFlag.EXPORT,
						null);// new String[] { "0258", "0657" }
		/**
		 * 退料出口金额(退换)
		 */
		double backMaterialExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.BACK_MATERIEL_EXPORT, ImpExpFlag.EXPORT,
						new String[] { "0300", "0700" });
		
		/**
		 * 其它(9900)
		 */
		double otherExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						null, ImpExpFlag.IMPORT,
						new String[] { "9900"});
		// contractCav.setImpAmount(directImportMoney +
		// transferFactoryImportMoney
		// - remainForwordExportMoney - backMaterialExportMoney);
		//进口总金额    edit by cjb 2009.8.12
		//进口总金额＝来料加工(0214)/进料对口(0615)+来/进料深加工(0255/0654)+来/进余料结转进口(0258/0657)+其他(9900)-来/进料件退换(0300/0700)
		contractCav.setImpAmount(directImportMoney + transferFactoryImportMoney
				+ remainForwordImportMoney +otherExportMoney - backMaterialExportMoney);
		/**
		 * 出口报关单总数
		 */
		contractCav.setExpCDCount(this.contractCavDao
				.findCutomsDeclarationCountByContract(dzscEmsPorHead, false));
		/**
		 * 成品出口金额
		 */
		double directExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.DIRECT_EXPORT, ImpExpFlag.EXPORT, null);
		/**
		 * 成品转厂出口金额
		 */
		double transferFactoryExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.TRANSFER_FACTORY_EXPORT, ImpExpFlag.EXPORT,
						null);
		/**
		 * 返工复出金额
		 */
		double reworkExportMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.REWORK_EXPORT, ImpExpFlag.EXPORT, null);
		/**
		 * 退厂返工金额
		 */
		double backFactoryReworkMoney = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.BACK_FACTORY_REWORK, ImpExpFlag.IMPORT, null);
		contractCav.setExpAmount(directExportMoney + transferFactoryExportMoney
				+ reworkExportMoney - backFactoryReworkMoney);

		/**
		 * 料件批准内销金额
		 */
		double internalSale = this.contractCavDao
				.findCutomsDeclarationMoneyByContract(dzscEmsPorHead,
						ImpExpType.MATERIAL_DOMESTIC_SALES, ImpExpFlag.IMPORT,
						null);
		contractCav.setInternalSale(internalSale);

		//余料总金额=余料转出报关单金额---2009.5.19 edited by cjb
		contractCav.setRemainMoney(remainForwordExportMoney);// by kcb
		
		contractCav.setCurr(dzscEmsPorHead.getCurr());
		contractCav.setCompany(dzscEmsPorHead.getCompany());
		contractCav.setIsCustoms(false);
		//设置小数位
		CommonUtils.formatDouble(contractCav, decimalLength);
		this.contractCavDao.saveContractCav(contractCav);
		this.cavCustomsDeclaration(contractCav, info);
		/**
		 * 先计算成品生成的资料下两步计算用
		 */
		this.cavContractExg(contractCav, dzscEmsPorHead, info);
		this.cavContractImg(contractCav, dzscEmsPorHead, info);
		this.cavContractBom(contractCav, info);
		if (isCustoms) {
			List<DzscCustomsDeclarationCav> lsCustomsDeclarationCav = this.contractCavDao
					.findCustomsDeclarationCav(contractCav);
			List<DzscContractImgCav> lsCustomsContractImg = this.contractCavDao
					.findContractImgCav(contractCav);
			List<DzscContractExgCav> lsCustomsContractExg = this.contractCavDao
					.findContractExgCav(contractCav);
			List<DzscContractBomCav> lsCustomsContractBom = this.contractCavDao
					.findContractBomCav(contractCav);
			makeCustomsContractCav(contractCav, lsCustomsDeclarationCav,
					lsCustomsContractImg, lsCustomsContractExg,
					lsCustomsContractBom, info);

		}
	}

	/**
	 * 重新获取海关核销表资料
	 * 
	 * @param contract
	 *            电子手册手册表头
	 */
	public void reGetCustomsContractCav(String emsNo, String taskId) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		List list = this.contractCavDao.findContractCav(emsNo, true);
		if (list.size() > 0) {
			if (info != null) {
				info.setMethodName("正在删除旧海关核销表资料");
			}
			DzscContractCav contractCav = (DzscContractCav) list.get(0);
			// this.contractCavDao.deleteAll(this.contractCavDao
			// .findCustomsDeclarationCav(contractCav));
			// this.contractCavDao.deleteAll(this.contractCavDao
			// .findContractImgCav(contractCav));
			// this.contractCavDao.deleteAll(this.contractCavDao
			// .findContractExgCav(contractCav));
			// this.contractCavDao.deleteAll(this.contractCavDao
			// .findContractBomCav(contractCav));
			this.contractCavDao.deleteCustomsDeclarationCav(contractCav);
			this.contractCavDao.deleteContractExgCav(contractCav);
			this.contractCavDao.deleteContractImgCav(contractCav);
			this.contractCavDao.deleteContractBomCav(contractCav);
			this.contractCavDao.delete(contractCav);
		}
		list = this.contractCavDao.findContractCav(emsNo, false);
		if (list.size() < 1) {
			throw new RuntimeException("自用核销料件为空");
		}
		DzscContractCav customsContractCav = (DzscContractCav) list.get(0);
		List<DzscCustomsDeclarationCav> lsCustomsDeclarationCav = this.contractCavDao
				.findCustomsDeclarationCav(customsContractCav);
		List<DzscContractImgCav> lsCustomsContractImg = this.contractCavDao
				.findContractImgCav(customsContractCav);
		List<DzscContractExgCav> lsCustomsContractExg = this.contractCavDao
				.findContractExgCav(customsContractCav);
		List<DzscContractBomCav> lsCustomsContractBom = this.contractCavDao
				.findContractBomCav(customsContractCav);
		makeCustomsContractCav(customsContractCav, lsCustomsDeclarationCav,
				lsCustomsContractImg, lsCustomsContractExg,
				lsCustomsContractBom, info);
	}

	/**
	 * 把海关用的核销数据保存到数据库。（包含核销单头、成品、料件、BOM）
	 * 
	 * @param customsContractCav
	 *            电子手册合同核销单头
	 */
	private void makeCustomsContractCav(DzscContractCav selfContractCav,
			List<DzscCustomsDeclarationCav> lsCustomsDeclarationCav,
			List<DzscContractImgCav> lsCustomsContractImg,
			List<DzscContractExgCav> lsCustomsContractExg,
			List<DzscContractBomCav> lsCustomsContractBom, ProgressInfo info) {
		DzscContractCav customsContractCav = new DzscContractCav();
		try {
			PropertyUtils.copyProperties(customsContractCav, selfContractCav);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customsContractCav.setId(null);
		customsContractCav.setIsCustoms(true);
		contractCavDao.saveContractCav(customsContractCav);
		if (info != null) {
			info.setMethodName("正在获取海关核销用的报关单资料");
			info.setTotalNum(lsCustomsDeclarationCav.size());
			info.setCurrentNum(0);
		}
		for (DzscCustomsDeclarationCav selfDeclarationCav : lsCustomsDeclarationCav) {
			DzscCustomsDeclarationCav customsDeclarationCav = new DzscCustomsDeclarationCav();
			try {
				PropertyUtils.copyProperties(customsDeclarationCav,
						selfDeclarationCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			customsDeclarationCav.setId(null);
			customsDeclarationCav.setContractCav(customsContractCav);
			customsDeclarationCav.setIsCustoms(true);
			this.contractCavDao
					.saveCustomsDeclarationCav(customsDeclarationCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("正在获取海关核销用的料件资料");
			info.setTotalNum(lsCustomsContractImg.size());
			info.setCurrentNum(0);
		}
		for (DzscContractImgCav selfContractImgCav : lsCustomsContractImg) {
			DzscContractImgCav contractImgCav = new DzscContractImgCav();
			try {
				PropertyUtils
						.copyProperties(contractImgCav, selfContractImgCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			contractImgCav.setId(null);
			contractImgCav.setContractCav(customsContractCav);
			contractImgCav.setIsCustoms(true);
			this.contractCavDao.saveContractImgCav(contractImgCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("正在获取海关核销用的成品资料");
			info.setTotalNum(lsCustomsContractExg.size());
			info.setCurrentNum(0);
		}
		for (DzscContractExgCav selfContractExgCav : lsCustomsContractExg) {
			DzscContractExgCav contractExgCav = new DzscContractExgCav();
			try {
				PropertyUtils
						.copyProperties(contractExgCav, selfContractExgCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			contractExgCav.setId(null);
			contractExgCav.setContractCav(customsContractCav);
			contractExgCav.setIsCustoms(true);
			this.contractCavDao.saveContractExgCav(contractExgCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		if (info != null) {
			info.setMethodName("正在获取海关核销用的单耗资料");
			info.setTotalNum(lsCustomsContractBom.size());
			info.setCurrentNum(0);
		}
		for (DzscContractBomCav selfContractBomCav : lsCustomsContractBom) {
			DzscContractBomCav contractBomCav = new DzscContractBomCav();
			try {
				PropertyUtils
						.copyProperties(contractBomCav, selfContractBomCav);
			} catch (Exception e) {
				e.printStackTrace();
			}
			contractBomCav.setId(null);
			contractBomCav.setContractCav(customsContractCav);
			contractBomCav.setIsCustoms(true);
			this.contractCavDao.saveContractBomCav(contractBomCav);
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
	public void cavCustomsDeclaration(DzscContractCav contractCav,
			ProgressInfo info) {
		this.contractCavDao.deleteCustomsDeclarationCav(contractCav);
		List list = this.contractCavDao.findDzscCustomsDeclaration(contractCav
				.getEmsNo());
		if (info != null) {
			info.setMethodName("正在计算核销报关单的资料");
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		// List<DzscCustomsDeclarationCav> lsCustomsDeclarationCav = new
		// ArrayList<DzscCustomsDeclarationCav>();
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsDeclaration customsDeclaration = (DzscCustomsDeclaration) list
					.get(i);
			DzscCustomsDeclarationCav cav = new DzscCustomsDeclarationCav();
			cav.setContractCav(contractCav);
			cav.setCustomsDeclarationCode(customsDeclaration
					.getCustomsDeclarationCode());
			cav.setDeclarationCustoms(customsDeclaration
					.getDeclarationCustoms());
			cav.setDeclarationDate(customsDeclaration.getDeclarationDate());
			cav.setImpExpDate(customsDeclaration.getImpExpDate());
			cav.setImpExpFlag(customsDeclaration.getImpExpFlag());
			cav.setImpExpType(customsDeclaration.getImpExpType());
			cav.setIsCustoms(false);
			List lsDeduc = this.contractCavDao.findDeduc(customsDeclaration
					.getTradeMode().getCode(), cav.getCustomsImpExpType());
			if (lsDeduc.size() > 0) {
				cav.setDeduc((Deduc) lsDeduc.get(0));
			}
			this.contractCavDao.saveCustomsDeclarationCav(cav);
			// lsCustomsDeclarationCav.add(cav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
	}

	/**
	 * 核销计算合同料件资料，料件进口总数量和余料数量
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @param contract
	 *            电子手册手册表头
	 */
	public void cavContractImg(DzscContractCav contractCav,
			DzscEmsPorHead contract, ProgressInfo info) {
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		DzscParameterSet dzscParameterSet = null;
		dzscParameterSet = dzscDao.findDzscParameterSet();		
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null){
			decimalLength = dzscParameterSet.getReportDecimalLength();
		}
		
		this.contractCavDao.deleteContractImgCav(contractCav);
		// List list = this.contractCavDao.findAllCommInfo(true, contractCav
		// .getEmsNo(), null, null);
		List<DzscEmsImgBill> list = this.dzscDao.findEmsPorImgBill(contract);
		contractCav.setImgCount(list.size());
		this.contractCavDao.saveContractCav(contractCav);
		List<DzscContractExgCav> lsCustomsContractExg = this.contractCavDao
				.findContractExgCav(contractCav);
		// lsCustomsContractImg = new ArrayList<DzscContractImgCav>();
		HashMap<String, Double> hmImgUse = new HashMap<String, Double>();
		HashMap<String, Double> hmImgWaste = new HashMap<String, Double>();
		this.calContractImgUse(contractCav, lsCustomsContractExg, hmImgUse,
				hmImgWaste, info);
		if (info != null) {
			info.setMethodName("开始计算核销料件表的资料");
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		Integer impExpType[] = new Integer[] { ImpExpType.DIRECT_IMPORT,
				ImpExpType.TRANSFER_FACTORY_IMPORT,
				ImpExpType.REMAIN_FORWARD_IMPORT,
				ImpExpType.REMAIN_FORWARD_EXPORT,
				ImpExpType.BACK_MATERIEL_EXPORT,
				ImpExpType.MATERIAL_DOMESTIC_SALES};
		Map commInfoListMap = getCommInfoListMap(impExpType, contractCav
				.getEmsNo());// 报关单表体资料
		for (DzscEmsImgBill contractImg : list) {
			DzscContractImgCav contractImgCav = new DzscContractImgCav();
			contractImgCav.setContractCav(contractCav);
			contractImgCav.setSeqNum(contractImg.getSeqNum());
			contractImgCav.setComplex(contractImg.getComplex());
			contractImgCav.setName(contractImg.getName());
			contractImgCav.setSpec(contractImg.getSpec());
			contractImgCav.setUnit(contractImg.getUnit());

			List commInfoList = (List) commInfoListMap.get(contractImgCav
					.getSeqNum());
			double directImportAmount = 0.0;// 进口数量 (其中已包括余料结转数)
			double transferFactoryImport = 0.0;// 料件转厂
			double remainImport = 0.0;// 余料进口 (余料结转进口)
			double remainExport = 0.0;// 余料出口 (余料结转出口)
			double internalAmount = 0.0;// 内销数量
			double backExport = 0.0;// 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
			double exchangeExport = 0.0;// 料件退换出口数

			if (commInfoList != null)
				for (int j = 0; j < commInfoList.size(); j++) {
					BaseCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) commInfoList
							.get(j);
					String tradeModeCod = null;

					if (commInfo.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.IMPORT) {// 进口
						if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.DIRECT_IMPORT) {
							directImportAmount += commInfo.getCommAmount();// 进口数量
							// (其中已包括余料结转数)
						} else if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
							transferFactoryImport += commInfo.getCommAmount();// 料件转厂
						} else if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.REMAIN_FORWARD_IMPORT) {
							remainImport += commInfo.getCommAmount();// 余料进口
							// (余料结转进口)
						} 
					} else {// 出口
						if (commInfo.getBaseCustomsDeclaration().getTradeMode() != null)
							tradeModeCod = commInfo.getBaseCustomsDeclaration()
									.getTradeMode().getCode();
						if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.BACK_MATERIEL_EXPORT) {
							if (tradeModeCod != null
									&& (tradeModeCod.equals("0265") || tradeModeCod
											.equals("0664"))) {
								backExport += commInfo.getCommAmount();// 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265）
							}
						}
						if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.BACK_MATERIEL_EXPORT) {
							if (tradeModeCod != null
									&& (tradeModeCod.equals("0300") || tradeModeCod
											.equals("0700"))) {
								exchangeExport += commInfo.getCommAmount();// 料件退换出口数
							}
						}
						if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.REMAIN_FORWARD_EXPORT) {
							remainExport += commInfo.getCommAmount();// 余料出口
							// (余料结转出口)
						}
					}

					if (commInfo.getBaseCustomsDeclaration().getTradeMode() != null) {
						tradeModeCod = commInfo.getBaseCustomsDeclaration()
								.getTradeMode().getCode();
						if (tradeModeCod != null
								&& (tradeModeCod.equals("0245") || tradeModeCod
										.equals("0644"))) {
							internalAmount += commInfo.getCommAmount();
						}
					}
				}
			
			/**
			 * 直接进口数量
			 */
			contractImgCav.setDirectImport(directImportAmount);
			/**
			 * 料件转厂
			 */
			contractImgCav.setTransferFactoryImport(transferFactoryImport);
			/**
			 * 余料进口 (余料结转进口)
			 */
			contractImgCav.setRemainImport(remainImport);
			/**
			 * 余料出口 (余料结转出口)
			 */
			contractImgCav.setRemainExport(remainExport);

			/**
			 * 成品耗用
			 */
			contractImgCav.setProductWaste(CommonUtils
					.getDoubleExceptNull(hmImgUse.get(contractImgCav
							.getSeqNum().toString())));

			/**
			 * 内销数量
			 */
			contractImgCav.setInternalAmount(internalAmount);

			/**
			 * 退运出口数量：指该项料件复运出口数量（进料料件复出0664／来料料件复出0265） //
			 */
			contractImgCav.setBackExport(backExport);
			/**
			 * 边角料
			 */
			contractImgCav.setLeftoverMaterial(CommonUtils
					.getDoubleExceptNull(hmImgWaste.get(contractImgCav
							.getSeqNum().toString())));

			/**
			 * 进口总数量＝ 料件进口数+余料结转进口数＋ 料件转厂进口数－料件退换出口数//+料件退换进口数(已经在料件进口中)
			 */
			contractImgCav.setImpTotal(directImportAmount
					+ CommonUtils.getDoubleExceptNull(contractImgCav
							.getTransferFactoryImport())
					+ CommonUtils.getDoubleExceptNull(contractImgCav
							.getRemainImport()) - exchangeExport);
			/**
			 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）－退运出口数量（复出）4）-余料结转出口数量(yp2008-12-19修改)
			 */
			contractImgCav.setRemainMaterial(CommonUtils
					.getDoubleExceptNull(contractImgCav.getImpTotal())
					- CommonUtils.getDoubleExceptNull(contractImgCav
							.getProductWaste())
					- CommonUtils.getDoubleExceptNull(contractImgCav
							.getInternalAmount())
					- CommonUtils.getDoubleExceptNull(contractImgCav
							.getBackExport())
					- CommonUtils.getDoubleExceptNull(contractImgCav
							.getRemainExport()));
			contractImgCav.setIsCustoms(false);
			CommonUtils.formatDouble(contractImgCav, decimalLength);
			this.contractCavDao.saveContractImgCav(contractImgCav);
			// lsCustomsContractImg.add(contractImgCav);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		commInfoListMap.clear();
		list.clear();
	}

	/**
	 * 电子手册核销计算合同成品资料，成品出口总数量
	 * 
	 * @param contractCav
	 *            电子手册核销表头
	 * @param contract
	 *            电子手册手册单头
	 */
	public void cavContractExg(DzscContractCav contractCav,
			DzscEmsPorHead contract, ProgressInfo info) {
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		DzscParameterSet dzscParameterSet = null;
		dzscParameterSet = dzscDao.findDzscParameterSet();		
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null){
			decimalLength = dzscParameterSet.getReportDecimalLength();
		}
		
		this.contractCavDao.deleteContractExgCav(contractCav);
		List<DzscEmsExgBill> list = this.dzscDao.findDzscEmsExgBill(contract);
		contractCav.setExgCount(list.size());
		this.contractCavDao.saveContractCav(contractCav);
		if (info != null) {
			info.setMethodName("正在计算核销成品表的资料");
			info.setTotalNum(list.size());
			info.setCurrentNum(0);
		}
		double processTotalPrice = 0;// 加工费总价
		Integer impExpType[] = new Integer[] { ImpExpType.DIRECT_EXPORT,
				ImpExpType.TRANSFER_FACTORY_EXPORT,
				ImpExpType.BACK_FACTORY_REWORK, ImpExpType.REWORK_EXPORT };
		Map commInfoListMap = getCommInfoListMap(impExpType, contractCav
				.getEmsNo());// 报关单表体资料
		for (DzscEmsExgBill contractExg : list) {
			DzscContractExgCav contractExgCav = new DzscContractExgCav();
			contractExgCav.setContractCav(contractCav);
			contractExgCav.setSeqNum(contractExg.getSeqNum());
			contractExgCav.setComplex(contractExg.getComplex());
			contractExgCav.setName(contractExg.getName());
			contractExgCav.setSpec(contractExg.getSpec());
			contractExgCav.setUnit(contractExg.getUnit());

			List commInfoList = (List) commInfoListMap.get(contractExgCav
					.getSeqNum());
			double directExportAmount = 0.0;// 成品出口数量
			double transferExpAmount = 0.0;// 结转出口数量
			double backFactoryRework = 0.0;// 退厂返工
			double reworkExport = 0.0;// 返工复出

			if (commInfoList != null)
				for (int j = 0; j < commInfoList.size(); j++) {
					BaseCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) commInfoList
							.get(j);
					if (commInfo.getBaseCustomsDeclaration().getImpExpFlag() == ImpExpFlag.EXPORT) {
						if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.DIRECT_EXPORT) {
							directExportAmount += commInfo.getCommAmount();// 成品出口数量
						} else if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT) {
							transferExpAmount += commInfo.getCommAmount();// 结转出口数量
						} else if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.REWORK_EXPORT) {
							reworkExport += commInfo.getCommAmount();// 返工复出
						}

					} else {
						if (commInfo.getBaseCustomsDeclaration()
								.getImpExpType() == ImpExpType.BACK_FACTORY_REWORK) {
							backFactoryRework += commInfo.getCommAmount();// 退厂返工
						}
					}
				}
			/**
			 * 成品出口数量
			 */
			contractExgCav.setDirectExport(CommonUtils.getDoubleByDigit(
					directExportAmount, 3));

			/**
			 * 结转出口数量
			 */
			contractExgCav.setTransferExpAmount(transferExpAmount);

			/**
			 * 退厂返工
			 */
			contractExgCav.setBackFactoryRework(backFactoryRework);

			/**
			 * 返工复出
			 */
			contractExgCav.setReworkExport(reworkExport);

			/**
			 * 出口总数量 = 成品出口数量＋ 成品转厂出口数量— 退厂返工数量+ 返工复出数量
			 */
			contractExgCav.setExpTotal(directExportAmount
					+ (contractExgCav.getTransferExpAmount() == null ? 0.0
							: contractExgCav.getTransferExpAmount())
					- (contractExgCav.getBackFactoryRework() == null ? 0.0
							: contractExgCav.getBackFactoryRework())
					+ (contractExgCav.getReworkExport() == null ? 0.0
							: contractExgCav.getReworkExport()));
			contractExgCav.setIsCustoms(false);
			CommonUtils.formatDouble(contractExgCav, decimalLength);
			this.contractCavDao.saveContractExgCav(contractExgCav);
			double processUnitPrice = CommonUtils
					.getDoubleExceptNull(contractExg.getMachinPrice());// 加工费单价
			double expTotal = CommonUtils.getDoubleExceptNull(contractExgCav
					.getExpTotal());// 加工费单价
			processTotalPrice += (processUnitPrice * expTotal);
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
		contractCav.setProcessTotalPrice(CommonUtils.getDoubleByDigit(
				processTotalPrice, decimalLength));
		this.contractCavDao.saveContractCav(contractCav);
		commInfoListMap.clear();
		list.clear();

	}

	/**
	 * 电子手册核销计算合同BOM资料，BOM的总数量和耗用量
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 */
	public void cavContractBom(DzscContractCav contractCav, ProgressInfo info) {
		// 显示小数位,默认2位
		Integer decimalLength = 2;
		DzscParameterSet dzscParameterSet = null;
		dzscParameterSet = dzscDao.findDzscParameterSet();		
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null){
			decimalLength = dzscParameterSet.getReportDecimalLength();
		}
		
		this.contractCavDao.deleteContractBomCav(contractCav);
		List<DzscContractExgCav> lsCustomsContractExg = this.contractCavDao
				.findContractExgCav(contractCav);
		if (info != null) {
			info.setMethodName("正在计算核销单耗表的资料");
			info.setTotalNum(lsCustomsContractExg.size());
			info.setCurrentNum(0);
		}
		String declarstate = "2";
		// lsCustomsContractBom = new ArrayList<DzscContractBomCav>();
		for (DzscContractExgCav contractExgCav : lsCustomsContractExg) {
			double exgAmount = contractExgCav.getExpTotal();// this.contractCavDao.findCommInfoTotalAmount(
			// contractExgCav.getSeqNum(), false, contractCav.getEmsNo(),
			// null, null);
			//5是核销状态，但在实际通关合同表头中，实际核销状态是4表示
			List<DzscEmsBomBill> list = this.contractCavDao.findBomByExg(
					contractCav.getEmsNo(), contractExgCav.getSeqNum(),declarstate);
			for (DzscEmsBomBill contractBom : list) {
				DzscContractBomCav contractBomCav = new DzscContractBomCav();
				contractBomCav.setContractCav(contractCav);
				contractBomCav.setSeqProductNum(contractExgCav.getSeqNum());
				contractBomCav.setProductName(contractExgCav.getName());
				contractBomCav.setProductSpec(contractExgCav.getSpec());
				contractBomCav.setSeqMaterialNum(contractBom.getImgSeqNum());
				contractBomCav.setMaterialName(contractBom.getName());
				contractBomCav.setMaterialSpec(contractBom.getSpec());
				contractBomCav.setUnitWaste(contractBom.getUnitWare());
				contractBomCav.setExgExpTotalAmount(exgAmount);
				contractBomCav.setWaste(contractBom.getWare());

				/**
				 * BOM的总数量
				 */
				// double imgAmount = CommonUtils.getDoubleByDigit(exgAmount
				// * CommonUtils.getDoubleExceptNull(contractBom
				// .getUnitDosage()), 3);
				double imgAmount = CommonUtils.getDoubleByDigit(exgAmount
						* CommonUtils.getDoubleExceptNull(CommonUtils
								.getDoubleExceptNull(contractBom.getUnitWare())
								/ (1 - CommonUtils
										.getDoubleExceptNull(contractBom
												.getWare()) / 100.0)), 3);
				/**
				 * BOM的耗用量
				 */
				contractBomCav.setWasteAmount(CommonUtils.getDoubleByDigit(
						imgAmount
								* CommonUtils.getDoubleExceptNull(contractBom
										.getWare()) / 100.0, 3));

				contractBomCav.setTotalAmount(imgAmount);
				contractBomCav.setModifyMark(ModifyMarkState.UNCHANGE);
				// contractBomCav.setIsCustoms(false);
				CommonUtils.formatDouble(contractBomCav, decimalLength);
				this.contractCavDao.saveContractBomCav(contractBomCav);
				// lsCustomsContractBom.add(contractBomCav);
			}
			if (info != null) {
				info.setCurrentNum(info.getCurrentNum() + 1);
			}
		}
	}

	/**
	 * 计算成品所有的料件的耗用总量和损耗量
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @param lsContractExg
	 *            是List<DzscContractExgCav>型，电子手册合同核销成品资料
	 * @param hmImgUse
	 *            是HashMap<String, Double>型 Double存放料件的耗用总量
	 * @param hmImgWaste
	 *            是HashMap<String, Double>型 Double存放料件的损耗量
	 */
	private void calContractImgUse(DzscContractCav contractCav,
			List<DzscContractExgCav> lsContractExg,
			HashMap<String, Double> hmImgUse,
			HashMap<String, Double> hmImgWaste, ProgressInfo info) {
		if (info != null) {
			info.setMethodName("开始计算核销料件耗用和边角料");
			info.setTotalNum(lsContractExg.size());
			info.setCurrentNum(0);
		}
		String declareState = "2";
		for (DzscContractExgCav contractExgCav : lsContractExg) {
			double exgAmount = CommonUtils.getDoubleExceptNull(contractExgCav
					.getExpTotal());// this.contractCavDao.findCommInfoTotalAmount(
			// contractExgCav.getSeqNum(), false, contractCav.getEmsNo(),
			// null, null);
			
			List<DzscEmsBomBill> list = this.contractCavDao.findBomByExg(
					contractCav.getEmsNo(), contractExgCav.getSeqNum(),declareState);
			for (DzscEmsBomBill contractBom : list) {

				// double imgAmount = exgAmount
				// * CommonUtils.getDoubleExceptNull(contractBom
				// .getUnitDosage());
				double imgAmount = exgAmount
						* CommonUtils.getDoubleExceptNull(CommonUtils
								.getDoubleExceptNull(contractBom.getUnitWare())
								/ (1 - CommonUtils
										.getDoubleExceptNull(contractBom
												.getWare()) / 100.0));
				double imgWaste = imgAmount
						* (CommonUtils.getDoubleExceptNull(contractBom
								.getWare()) / 100.0);
				double hasAmount = CommonUtils.getDoubleExceptNull(hmImgUse
						.get(CommonUtils.getStringFromInteger(contractBom
								.getImgSeqNum())));
				double hasWaste = CommonUtils.getDoubleExceptNull(hmImgWaste
						.get(CommonUtils.getStringFromInteger(contractBom
								.getImgSeqNum())));
				hmImgUse.put(CommonUtils.getStringFromInteger(contractBom
						.getImgSeqNum()), imgAmount + hasAmount);
				hmImgWaste.put(CommonUtils.getStringFromInteger(contractBom
						.getImgSeqNum()), imgWaste + hasWaste);
				// System.out.println("------------"
				// + CommonUtils.getStringFromInteger(contractBom
				// .getImgSeqNum()) + "---------"
				// + (imgAmount + hasAmount) + "-----------"
				// + (imgWaste + hasWaste));
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
	 * 根据手册号和进出口标志查找报报关单表体Map
	 * 
	 * @param impExpFlag
	 *            进出口标志
	 * @param emsNo
	 *            手册号
	 * @return
	 */
	public Map getCommInfoListMap(Integer[] impExpType, String emsNo) {
		List list = dzscDao.findDzscCustomsDeclarationCommInfo(impExpType,
				emsNo);
		Map CommInfoListMap = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			DzscCustomsDeclarationCommInfo commInfo = (DzscCustomsDeclarationCommInfo) list
					.get(i);
			if (CommInfoListMap.get(commInfo.getCommSerialNo()) == null) {
				List commInfoList = new ArrayList();
				commInfoList.add(commInfo);
				CommInfoListMap.put(commInfo.getCommSerialNo(), commInfoList);
			} else {
				List commInfoList = (List) CommInfoListMap.get(commInfo
						.getCommSerialNo());
				commInfoList.add(commInfo);
			}
		}

		return CommInfoListMap;

	}

	/**
	 * 重新计算边角料数量
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 */
	public void recalRemainMaterialAmount(DzscContractCav contractCav) {
		List<DzscContractExgCav> lsExg = this.contractCavDao
				.findContractExgCav(contractCav);
		HashMap<String, Double> hmImgWaste = new HashMap<String, Double>();
		for (DzscContractExgCav contractExgCav : lsExg) {
			double exgAmount = CommonUtils.getDoubleExceptNull(contractExgCav
					.getExpTotal());
			List<DzscContractBomCav> list = this.contractCavDao
					.findContractBomCavByExgSeqNum(contractCav.getId(),
							contractExgCav.getSeqNum());
			for (DzscContractBomCav contractBomCav : list) {
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
						.getDoubleExceptNull(hmImgWaste
								.get(contractBomCav.getSeqMaterialNum() == null ? ""
										: contractBomCav.getSeqMaterialNum()
												.toString()));
				hmImgWaste.put((contractBomCav.getSeqMaterialNum() == null ? ""
						: contractBomCav.getSeqMaterialNum().toString()),
						imgWaste + hasWaste);
			}
		}
		List<DzscContractImgCav> lsImg = this.contractCavDao
				.findContractImgCav(contractCav);
		for (DzscContractImgCav contractImgCav : lsImg) {
			contractImgCav.setLeftoverMaterial(hmImgWaste.get(contractImgCav
					.getSeqNum().toString()));
			this.contractCavDao.saveContractImgCav(contractImgCav);
		}
	}

	/**
	 * 核销表总计算
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return contractCavTotalValue 存放了核销的各种总计
	 */
	public DzscContractCavTotalValue findCavTotalValue(
			DzscContractCav contractCav) {
		DzscContractCavTotalValue contractCavTotalValue = new DzscContractCavTotalValue();
		contractCavTotalValue.setExpAmount(contractCav.getExpAmount());
		contractCavTotalValue.setImpAmount(contractCav.getImpAmount());
		contractCavTotalValue.setProcessTotalPrice(CommonUtils
				.getDoubleByDigit(contractCav.getProcessTotalPrice(), 2));
		contractCavTotalValue.setExpTotalPieces(CommonUtils.getDoubleByDigit(
				this.contractCavDao.findExpTotalPieces(contractCav.getEmsNo()),
				2));
		double[] importWeight = this.contractCavDao
				.findImportTotalWeight(contractCav.getEmsNo());
		contractCavTotalValue.setImpTotalGrossWeight(CommonUtils
				.getDoubleByDigit(importWeight[0], 2));
		contractCavTotalValue.setImpTotalNetWeight(CommonUtils
				.getDoubleByDigit(importWeight[1], 2));
		double[] exportWeight = this.contractCavDao
				.findExportTotalWeight(contractCav.getEmsNo());
		contractCavTotalValue.setExpTotalGrossWeight(CommonUtils
				.getDoubleByDigit(exportWeight[0], 2));
		contractCavTotalValue.setExpTotalNetWeight(CommonUtils
				.getDoubleByDigit(exportWeight[1], 2));
		return contractCavTotalValue;
	}

	/**
	 * 修改单耗，反算损耗量，总用量，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            电子手册核销BOM资料
	 */
	private void modifyUnitWasteWriteBack(DzscContractBomCav bomCav) {
		DzscContractBomCav oldBomCav = (DzscContractBomCav) this.contractCavDao
				.load(DzscContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		if (bomCav.getUnitWaste().equals(oldBomCav.getUnitWaste())) {
			return;
		}
		double unitWaste = (bomCav.getUnitWaste() == null ? 0.0 : bomCav
				.getUnitWaste());
		double waste = (bomCav.getWaste() == null ? 0.0 : bomCav.getWaste()) / 100.0;
		double exgAmount = bomCav.getExgExpTotalAmount();
		// double unitDosage = CommonUtils.getDoubleByDigit(unitWaste
		// / (1 - waste / 100.0), 5);
		// double imgAmount = CommonUtils.getDoubleByDigit(exgAmount *
		// unitDosage,
		// 3);
		double imgAmount = CommonUtils.getDoubleByDigit(exgAmount
				* CommonUtils.getDoubleExceptNull(unitWaste / (1 - waste)), 3);
		bomCav.setWasteAmount(CommonUtils
				.getDoubleByDigit(imgAmount * waste, 3));
		bomCav.setTotalAmount(imgAmount);
		// 计算出单耗改变前，后的单耗总用量的差值
		double diffAmount = imgAmount
				- (oldBomCav.getTotalAmount() == null ? 0.0 : oldBomCav
						.getTotalAmount());
		double diffWasteAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getWasteAmount())
				- CommonUtils.getDoubleExceptNull(oldBomCav.getWasteAmount());
		if (diffAmount == 0 && diffWasteAmount == 0) {
			return;
		}
		DzscContractImgCav imgCav = this.contractCavDao
				.findContractImgCavBySeqNum(bomCav.getContractCav().getId(),
						bomCav.getSeqMaterialNum());
		if (imgCav == null) {
			return;
		}
		imgCav.setProductWaste((imgCav.getProductWaste() == null ? 0.0 : imgCav
				.getProductWaste())
				+ diffAmount);
		imgCav.setProductWaste(CommonUtils.getDoubleByDigit(imgCav
				.getProductWaste(), 5));
		imgCav.setLeftoverMaterial(CommonUtils.getDoubleExceptNull(imgCav
				.getLeftoverMaterial())
				+ diffWasteAmount);
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(imgCav.getImpTotal())
				- CommonUtils.getDoubleExceptNull(imgCav.getProductWaste())
				- CommonUtils.getDoubleExceptNull(imgCav.getInternalAmount()),
				3));
		this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 修改单耗，反算损耗量，总用量，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            电子手册核销BOM资料
	 */
	private void modifyWriteBack(DzscContractBomCav bomCav, Double old) {

		// DzscContractBomCav oldBomCav = (DzscContractBomCav)
		// this.contractCavDao
		// .load(DzscContractBomCav.class, bomCav.getId());
		if (old == null) {
			old = 0.0;
		}
		double diffAmount = (bomCav.getTotalAmount() == null ? 0.0 : bomCav
				.getTotalAmount())
				- old;
		if (diffAmount == 0) {
			return;
		}
		DzscContractImgCav imgCav = this.contractCavDao
				.findContractImgCavBySeqNum(bomCav.getContractCav().getId(),
						bomCav.getSeqMaterialNum());
		if (imgCav == null) {
			return;
		}
		imgCav.setProductWaste((imgCav.getProductWaste() == null ? 0.0 : imgCav
				.getProductWaste())
				+ diffAmount);
		imgCav.setProductWaste(CommonUtils.getDoubleByDigit(imgCav
				.getProductWaste(), 3));
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(imgCav.getImpTotal())
				- CommonUtils.getDoubleExceptNull(imgCav.getProductWaste())
				- CommonUtils.getDoubleExceptNull(imgCav.getInternalAmount()),
				3));
		this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 修改总用量反算损耗量，单耗，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            电子手册核销BOM资料
	 */
	private void modifyTotalAmountWriteBack(DzscContractBomCav bomCav,
			DzscContractBomCav oldBomCav) {
		// DzscContractBomCav oldBomCav = (DzscContractBomCav)
		// this.contractCavDao
		// .get(DzscContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		if ((bomCav.getTotalAmount() == null ? 0.0 : bomCav.getTotalAmount()) == (oldBomCav
				.getTotalAmount() == null ? 0.0 : oldBomCav.getTotalAmount())) {
			return;
		}
		double oldTotalAmount = CommonUtils.getDoubleExceptNull(oldBomCav
				.getTotalAmount());
		double newTotalAmount = CommonUtils.getDoubleExceptNull(bomCav
				.getTotalAmount());
		// Double exgAmount = bomCav.getExgExpTotalAmount();
		// if (exgAmount == null || exgAmount == 0) {
		// return;
		// }
		// double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
		// / exgAmount, 5);
		// double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) /
		// 100.0;
		// if (waste == 1) {
		// return;
		// }
		// double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
		// * (1 - waste), 5);
		// bomCav.setUnitWaste(unitWaste);
		// bomCav.setWasteAmount(CommonUtils.getDoubleByDigit(newTotalAmount
		// * waste, 3));
		double diffAmount = newTotalAmount - oldTotalAmount;
		if (diffAmount == 0) {
			return;
		}
		this.contractCavDao.saveContractBomCav(bomCav);
		DzscContractImgCav imgCav = this.contractCavDao
				.findContractImgCavBySeqNum(bomCav.getContractCav().getId(),
						bomCav.getSeqMaterialNum());
		if (imgCav == null) {
			return;
		}
		imgCav.setProductWaste((imgCav.getProductWaste() == null ? 0.0 : imgCav
				.getProductWaste())
				+ diffAmount);
		imgCav.setProductWaste(CommonUtils.getDoubleByDigit(imgCav
				.getProductWaste(), 0));

		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(imgCav.getImpTotal())
				- CommonUtils.getDoubleExceptNull(imgCav.getProductWaste())
				- CommonUtils.getDoubleExceptNull(imgCav.getInternalAmount()),
				3));
		bomCav.setModifyMark(ModifyMarkState.MODIFIED);
		this.contractCavDao.saveContractImgCav(imgCav);
		this.contractCavDao.saveContractBomCav(bomCav);
	}

	/**
	 * 修改损耗量反算单耗，总用量，成品耗用量和料件余量
	 * 
	 * @param bomCav
	 *            电子手册核销BOM资料
	 */
	private void modifyWasteAmountWriteBack(DzscContractBomCav bomCav,
			DzscContractBomCav oldBomCav) {
		// DzscContractBomCav oldBomCav = (DzscContractBomCav)
		// this.contractCavDao
		// .get(DzscContractBomCav.class, bomCav.getId());
		if (oldBomCav == null) {
			return;
		}
		if ((bomCav.getWasteAmount() == null ? 0.0 : bomCav.getWasteAmount()) == (oldBomCav
				.getWasteAmount() == null ? 0.0 : oldBomCav.getWasteAmount())) {
			return;
		}
		double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
		if (waste == 1) {
			return;
		}
		double oldTotalAmount = CommonUtils.getDoubleExceptNull(oldBomCav
				.getTotalAmount());
		double newTotalAmount = CommonUtils.getDoubleByDigit((CommonUtils
				.getDoubleExceptNull(bomCav.getWasteAmount()) / waste), 3);
		bomCav.setTotalAmount(newTotalAmount);
		Double exgAmount = bomCav.getExgExpTotalAmount();
		if (exgAmount == null || exgAmount == 0) {
			return;
		}
		// double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
		// / exgAmount, 5);
		//
		// double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
		// * (1 - waste), 5);
		// bomCav.setUnitWaste(unitWaste);
		double diffAmount = newTotalAmount - oldTotalAmount;
		if (diffAmount == 0) {
			return;
		}

		DzscContractImgCav imgCav = this.contractCavDao
				.findContractImgCavBySeqNum(bomCav.getContractCav().getId(),
						bomCav.getSeqMaterialNum());
		if (imgCav == null) {
			return;
		}
		imgCav.setProductWaste((imgCav.getProductWaste() == null ? 0.0 : imgCav
				.getProductWaste())
				+ diffAmount);
		// imgCav.setProductWaste(CommonUtils.getDoubleByDigit(imgCav
		// .getProductWaste(), 0));
		bomCav.setModifyMark(ModifyMarkState.MODIFIED);
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(imgCav.getImpTotal())
				- CommonUtils.getDoubleExceptNull(imgCav.getProductWaste())
				- CommonUtils.getDoubleExceptNull(imgCav.getInternalAmount()),
				3));
		this.contractCavDao.saveContractImgCav(imgCav);
		this.contractCavDao.saveContractBomCav(bomCav);
	}

	/**
	 * 修改成品耗用量反算损耗量，总用量，单耗和料件余量
	 * 
	 * @param imgCav
	 *            电子手册核销料件资料
	 * @param list
	 *            是DzscContractBomCav类型，电子手册核销BOM资料
	 */
	private void modifyProductUsedAmountWriteBack(DzscContractImgCav imgCav,
			List<DzscContractBomCav> list) {
		if (list.size() < 1) {
			return;
		}
		double dou = imgCav.getBackExport();
		imgCav.setBackExport(8888.0);
		System.out.println("new " + imgCav.getBackExport());
		DzscContractImgCav oldImgCav = (DzscContractImgCav) this.contractCavDao
				.load(DzscContractImgCav.class, imgCav.getId());
		System.out.println("new " + oldImgCav.getBackExport());
		if (oldImgCav == null) {
			return;
		}
		if (imgCav.getProductWaste().equals(oldImgCav.getProductWaste())) {
			return;
		}
		imgCav.setBackExport(dou);
		/**
		 * 余料数量 ＝ 进口总数量1）－内销数量3）－产品总耗用量2）
		 */
		imgCav.setRemainMaterial(CommonUtils.getDoubleByDigit(CommonUtils
				.getDoubleExceptNull(imgCav.getImpTotal())
				- CommonUtils.getDoubleExceptNull(imgCav.getProductWaste())
				- CommonUtils.getDoubleExceptNull(imgCav.getInternalAmount()),
				3));
		double oldProductUsedAmount = CommonUtils.getDoubleExceptNull(oldImgCav
				.getProductWaste());
		double newProductUsedAmount = CommonUtils.getDoubleExceptNull(imgCav
				.getProductWaste());
		double diffAmount = newProductUsedAmount - oldProductUsedAmount;
		double totalUsedAmount = 0.0;
		for (DzscContractBomCav bomCav : list) {
			totalUsedAmount += CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount());
		}
		if (totalUsedAmount <= 0) {
			return;
		}
		for (DzscContractBomCav bomCav : list) {
			double m = CommonUtils
					.getDoubleByDigit(
							((CommonUtils.getDoubleExceptNull(bomCav
									.getTotalAmount()) / totalUsedAmount) * diffAmount),
							3);
			bomCav.setTotalAmount(CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount())
					+ m);
			double newTotalAmount = CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount());
			double exgAmount = bomCav.getExgExpTotalAmount();
			double unitDosage = CommonUtils.getDoubleByDigit(newTotalAmount
					/ exgAmount, 5);
			double waste = CommonUtils.getDoubleExceptNull(bomCav.getWaste()) / 100.0;
			double unitWaste = CommonUtils.getDoubleByDigit(unitDosage
					* (1 - waste), 5);
			bomCav.setUnitWaste(unitWaste);
			bomCav.setWasteAmount(CommonUtils.getDoubleByDigit(newTotalAmount
					* waste, 3));
			this.contractCavDao.saveContractBomCav(bomCav);
		}
		// this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 核销单数量取整
	 * 
	 * @param contractCav
	 *            电子手册合同核销表头
	 * @param isTotalAmount
	 *            true:修改总用量 false:修改总耗量
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	public void convertWasteAmountToInteger(DzscContractCav contractCav,
			boolean isTotalAmount, boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack) {
		List<DzscContractBomCav> list = this.contractCavDao
				.findContractBomCav(contractCav);
		for (DzscContractBomCav bomCav : list) {

			DzscContractBomCav oldBomCav = new DzscContractBomCav();
			try {
				BeanUtilsBean.getInstance().copyProperties(oldBomCav, bomCav);
				// DzscContractBomCav test = (DzscContractBomCav)
				// this.contractCavDao
				// .load(DzscContractBomCav.class, bomCav.getId());
				// bomCav.setWaste(888.8);
				// System.out.println("new "+bomCav.getWaste());
				// System.out.println("test "+bomCav.getWaste());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (isTotalAmount) {

				bomCav.setTotalAmount(CommonUtils.getDoubleByDigit(bomCav
						.getTotalAmount(), 0));
				// if (!modifyTotalAmountNotWriteBack) {
				this.modifyTotalAmountWriteBack(bomCav, oldBomCav);

				// }
				// this.contractCavDao.saveContractBomCav(bomCav);
			} else {
				bomCav.setWasteAmount(CommonUtils.getDoubleByDigit(bomCav
						.getWasteAmount(), 0));
				// if (!modifyWasteAmountNotWriteBack) {
				this.modifyWasteAmountWriteBack(bomCav, oldBomCav);
				// }
				this.contractCavDao.saveContractBomCav(bomCav);
			}
		}
	}

	/**
	 * 保存电子手册核销BOM资料
	 * 
	 * @param bomCav
	 *            电子手册核销BOM资料
	 * @param modifyUnitWasteNotWriteBack
	 *            判断是否修改单耗后反写，true为修改
	 * @param modifyTotalAmountNotWriteBack
	 *            判断是否修改总用量后反写，true为修改
	 * @param modifyWasteAmountNotWriteBack
	 *            判断是否修改耗用量后反写，true为修改
	 */
	public void saveContractBomCav(DzscContractBomCav bomCav,
			boolean modifyUnitWasteNotWriteBack,
			boolean modifyTotalAmountNotWriteBack,
			boolean modifyWasteAmountNotWriteBack) {
		if (!modifyUnitWasteNotWriteBack) {
			this.modifyUnitWasteWriteBack(bomCav);
		}
		DzscContractBomCav oldBomCav = (DzscContractBomCav) this.contractCavDao
				.load(DzscContractBomCav.class, bomCav.getId());
		if (oldBomCav != null) {
			if (!bomCav.getUnitWaste().equals(oldBomCav.getUnitWaste())
					|| !bomCav.getWaste().equals(oldBomCav.getWaste())) {
				bomCav.setModifyMark(ModifyMarkState.MODIFIED);
			}
		}
		this.contractCavDao.saveContractBomCav(bomCav);
	}

	/**
	 * 保存电子手册核销BOM资料
	 * 
	 * @param bomCav
	 *            电子手册核销BOM资料
	 * @param modifyUnitWasteNotWriteBack
	 *            判断是否修改单耗后反写，true为修改
	 */
	public void saveContractBomCav(DzscContractBomCav bomCav) {
		DzscContractBomCav oldBomCav = null;
		oldBomCav = contractCavDao.findDzscContractBomCavByIdNoCache(bomCav
				.getId());

		if (oldBomCav == null) {
			this.contractCavDao.saveContractBomCav(bomCav);
			return;
		}
		this.modifyWriteBack(bomCav, oldBomCav.getTotalAmount());
		if (oldBomCav != null) {
			if (!bomCav.getUnitWaste().equals(oldBomCav.getUnitWaste())
					|| !bomCav.getWaste().equals(oldBomCav.getWaste())) {
				bomCav.setModifyMark(ModifyMarkState.MODIFIED);
			}
		}
		this.contractCavDao.saveContractBomCav(bomCav);
	}

	/**
	 * 保存电子手册核销料件资料
	 * 
	 * @param imgCav
	 *            电子手册核销料件资料
	 * @param list
	 *            要是DzscContractBomCav类型，电子手册核销BOM资料
	 * @param modifyProductUsedAmountWriteBack
	 *            判断是否修改成品耗用量后反写，true为修改
	 */
	public void saveContractImgCav(DzscContractImgCav imgCav, List list,
			boolean modifyProductUsedAmountWriteBack) {
		if (modifyProductUsedAmountWriteBack) {
			this.modifyProductUsedAmountWriteBack(imgCav, list);
		}
		this.contractCavDao.saveContractImgCav(imgCav);
	}

	/**
	 * 获得帐册成品单耗,并在打印报表的时候跟据页面大小分组 list<ContractExgCav>(0) 成品（集合）
	 * list<ContractUnitWasteCav>(1) 单耗记录（集合）
	 * 
	 * @param contractCavId
	 *            电子手册合同核销单头ID
	 * @param index
	 *            数据开始的下标
	 * @param length
	 *            数据长度
	 * @return List list<ContractExgCav>(0) 成品（集合） list<ContractUnitWasteCav>(1)
	 *         单耗
	 */
	public List<List> findDzscContractUnitWasteCav(
			DzscContractCav dzscContractCav, int index, int length) {
		List<DzscContractUnitWasteCav> contractUnitWasteListCav = new ArrayList<DzscContractUnitWasteCav>();
		List<DzscContractExgCav> contractExgCavList = null;
		List<DzscContractImgCav> contractImgCavList = null;
		Map<String, DzscContractBomCav> contractBomCavMap = new HashMap<String, DzscContractBomCav>();
		List<DzscContractBomCav> contractBomCavList = null;
		final int columnCount = 4;
		contractExgCavList = this.findContractExgCav(dzscContractCav);
		// Collections.sort(contractExgCavList, new
		// Comparator<DzscContractExgCav>() {
		// public int compare(DzscContractExgCav o1, DzscContractExgCav o2) {
		// if (o1.getSeqNum() == null && o2.getSeqNum() != null) {
		// return -1;
		// } else if (o1.getSeqNum() != null && o2.getSeqNum() == null) {
		// return 1;
		// } else if (o1.getSeqNum() == null && o2.getSeqNum() == null) {
		// return 0;
		// } else {
		// Integer seqNum1 = Integer.valueOf(o1.getSeqNum());
		// Integer seqNum2 = Integer.valueOf(o2.getSeqNum());
		// if (seqNum1 > seqNum2) {
		// return 1;
		// } else if (seqNum1 == seqNum2) {
		// return 0;
		// } else if (seqNum1 < seqNum2) {
		// return -1;
		// }
		// }
		// return 0;
		// }
		// });

		int contractExgCavCount = contractExgCavList.size();
		System.out.println(contractExgCavCount + "   fffffffffffffffffffff");
		contractImgCavList = this.contractCavDao
				.findDzscContractImgCavByParentId(dzscContractCav.getId());
		contractBomCavList = this.contractCavDao
				.findDzscContractBomCavByContractParentId(dzscContractCav
						.getId());
		for (int i = 0; i < contractBomCavList.size(); i++) {
			DzscContractBomCav contractBomCav = (DzscContractBomCav) contractBomCavList
					.get(i);
			//
			// 名称,规格, seqMaterialNum== key
			//
			String productName = contractBomCav.getSeqMaterialNum() == null ? ""
					: contractBomCav.getSeqMaterialNum().toString();// 料件序号
			String productSpec = contractBomCav.getSeqProductNum() == null ? ""
					: contractBomCav.getSeqProductNum().toString();// 成品序号
			// String seqMaterialNum = (contractBomCav.getSeqMaterialNum() ==
			// null ? ""
			// : contractBomCav.getSeqMaterialNum().toString());
			String key = productName + "/" + productSpec;
			contractBomCavMap.put(key, contractBomCav);

			// System.out.println("Bom key ------------- " + key);
		}
		//
		// 获得所有组数 并分组
		//
		int groupCount = contractExgCavCount / columnCount
				+ ((contractExgCavCount % columnCount) > 0 ? 1 : 0);
		Collections.sort(contractImgCavList,
				new Comparator<DzscContractImgCav>() {
					public int compare(DzscContractImgCav o1,
							DzscContractImgCav o2) {
						if (o1.getSeqNum() == null && o2.getSeqNum() != null) {
							return -1;
						} else if (o1.getSeqNum() != null
								&& o2.getSeqNum() == null) {
							return 1;
						} else if (o1.getSeqNum() == null
								&& o2.getSeqNum() == null) {
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
		//
		// 以成品行数为4条记录进行--手动分组
		//
		for (int g = 0; g < groupCount; g++) {
			//
			// 获取以列的个数分组的临时成品列表
			//
			List<DzscContractExgCav> tempContractExgCavList = new ArrayList<DzscContractExgCav>();
			for (int i = g * columnCount; i < (g + 1) * columnCount; i++) {
				if (i < contractExgCavCount) {
					tempContractExgCavList.add(contractExgCavList.get(i));
				} else {
					break;
				}
			}

			for (int i = 0; i < contractImgCavList.size(); i++) {
				DzscContractImgCav contractImgCav = contractImgCavList.get(i);
				boolean isExist = false;
				DzscContractUnitWasteCav contractUnitWasteCav = new DzscContractUnitWasteCav();
				contractUnitWasteCav.setGroupId(g);
				contractUnitWasteCav.setSeqMaterialNum(contractImgCav
						.getSeqNum().toString());
				contractUnitWasteCav.setMaterialName(contractImgCav.getName());
				contractUnitWasteCav.setMaterialSpec(contractImgCav.getSpec());
				for (int j = 0; j < tempContractExgCavList.size(); j++) {
					DzscContractExgCav contractExgCav = tempContractExgCavList
							.get(j);
					//
					// 名称,规格, seqMaterialNum== key
					//
					String productName = contractImgCav.getSeqNum() == null ? ""
							: contractImgCav.getSeqNum().toString();// 料件序号
					String productSpec = contractExgCav.getSeqNum() == null ? ""
							: contractExgCav.getSeqNum().toString();// 成品序号

					String key = productName + "/" + productSpec;
					DzscContractBomCav contractBomCav = contractBomCavMap
							.get(key);
					if (contractBomCav == null) {
						continue;
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
				if (isExist == true) {
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
	 * 合同核销平衡检查
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return boolean true表示核销平衡
	 */
/*	public boolean checkDzscContractCavData(DzscContractCav contractCav) {
		List list = this.contractCavDao.findContractImgCav(contractCav);
		for (int i = 0; i < list.size(); i++) {
			DzscContractImgCav imgCav = (DzscContractImgCav) list.get(i);
			double impTotal = CommonUtils.getDoubleExceptNull(imgCav
					.getImpTotal());
			if (impTotal <= 0) {
				continue;
			}
			double productWaste = CommonUtils.getDoubleExceptNull(imgCav
					.getProductWaste());
			double internalAmount = CommonUtils.getDoubleExceptNull(imgCav
					.getInternalAmount());
			double backExport = CommonUtils.getDoubleExceptNull(imgCav
					.getBackExport());
			double remainMaterial = CommonUtils.getDoubleExceptNull(imgCav
					.getRemainMaterial());
			double errorScale = (impTotal - (productWaste + internalAmount
					+ backExport + remainMaterial))
					/ impTotal;
			String str = "-1%=<[进口总数量-（产品总耗用量+内销数量+退运出口数量+余料数量）]/进口总数量<=1%";
			if (errorScale > 0.01 || errorScale < -0.01) {
				throw new RuntimeException("料件:"
						+ imgCav.getName()
						+ ((imgCav.getSpec() == null || "".equals(imgCav
								.getSpec().trim())) ? "" : ("/" + imgCav
								.getSpec())) + str + "\n不平衡");
			}
		}

		list = this.contractCavDao.findContractBomCav(contractCav);
		double totalProductUsed = 0.0;
		double totalUsedAmount = 0.0;
		for (int i = 0; i < list.size(); i++) {
			DzscContractBomCav bomCav = (DzscContractBomCav) list.get(i);
			// 总耗量
			double totalAmount = CommonUtils.getDoubleExceptNull(bomCav
					.getTotalAmount());
			if (totalAmount <= 0) {
				continue;
			}
			double exgTotal = CommonUtils.getDoubleExceptNull(bomCav
					.getExgExpTotalAmount());
			double wasteAmount = CommonUtils.getDoubleExceptNull(bomCav
					.getWasteAmount());
			// double waste =
			// CommonUtils.getDoubleExceptNull(bomCav.getWaste());
			double unitWaste = CommonUtils.getDoubleExceptNull(bomCav
					.getUnitWaste());
			double productUsed = exgTotal * (unitWaste) + wasteAmount;
			totalProductUsed += productUsed;
			totalUsedAmount += totalAmount;
			double errorScale = (totalAmount - (productUsed)) / totalAmount;
			String str = "-1%=<[料件耗用总量-（对应成品数量*单耗+损耗量）]/料件耗用量<=1%";
			if (errorScale > 0.01 || errorScale < -0.01) {
				throw new RuntimeException("料件:"
						+ bomCav.getMaterialName()
						+ ((bomCav.getMaterialSpec() == null || ""
								.equals(bomCav.getMaterialSpec().trim())) ? ""
								: ("/" + bomCav.getMaterialSpec()))
						+ bomCav.getMaterialSpec()
						+ " \n和对应成品:"
						+ bomCav.getProductName()
						+ ((bomCav.getProductSpec() == null || "".equals(bomCav
								.getProductSpec())) ? "" : ("/" + bomCav
								.getProductSpec().trim())) + "\n" + str
						+ "\n不平衡");
			}
		}
		if (totalUsedAmount <= 0) {
			return false;
		}
		double errorScale = (totalUsedAmount - totalProductUsed)
				/ totalUsedAmount;
		String str = "-1%=<[料件总耗用量-SUM(单项成品耗用量）]/料件总耗用量<=1%";
		if (errorScale > 0.01 || errorScale < -0.01) {
			throw new RuntimeException(str + "\n不平衡");
		}
		return true;
	}*/
	
	/**
	 * 合同核销平衡检查
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return boolean true表示核销平衡
	 */
	public boolean checkDzscContractCavData(DzscContractCav contractCav) {
		List list = this.contractCavDao.findContractImgCav(contractCav);
		final String lgImg = "料件:余料/进口数量大于等于20%，不平衡!";
		int lt = 0;
		StringBuffer logImg = new StringBuffer("");
		StringBuffer log = new StringBuffer("");
		for (int i = 0; i < list.size(); i++) {
			DzscContractImgCav imgCav = (DzscContractImgCav) list.get(i);
			double impTotal = CommonUtils.getDoubleExceptNull(imgCav
					.getImpTotal());
			if (impTotal <= 0) {
				continue;
			}
			double remainMaterial = CommonUtils.getDoubleExceptNull(imgCav
					.getRemainMaterial());
			double errorScale = remainMaterial/ impTotal;
			if (errorScale >= 0.2) {
				if (lt == 0) {
					logImg.append("料件：");
					lt++;
				}
				String tr = imgCav.getSeqNum()+ "、";
				logImg.append(tr);
			}
		}
		if (logImg.toString().length() > lgImg.length()) {
			log.append(logImg.toString()+"余料/进口数量大于等于20%，不平衡!");
		}
		if (!log.toString().equals("")) {
			throw new RuntimeException(log.toString());
		}
		return true;
	}


	/**
	 * 重排国内购买，将国内购买的料件和排在它的相似料件的下面
	 * 
	 * @param list
	 *            是DzscContractImgCav，电子手册核销料件资料
	 */
	private void resortDataForInteralBuy(List<DzscContractImgCav> list) {
		List lsInteralBuy = new ArrayList();
		for (int i = list.size() - 1; i >= 0; i--) {
			DzscContractImgCav imgCav = (DzscContractImgCav) list.get(i);
			if (imgCav.getName().indexOf("(国内购买)") >= 0) {
				lsInteralBuy.add(imgCav);
				list.remove(i);
			}
		}
		for (int i = 0; i < lsInteralBuy.size(); i++) {
			DzscContractImgCav interalBuy = (DzscContractImgCav) lsInteralBuy
					.get(i);
			String name = interalBuy.getName().substring(
					interalBuy.getName().indexOf("(国内购买)") + "(国内购买)".length());
			for (int j = 0; j < list.size(); j++) {
				DzscContractImgCav imgCav = (DzscContractImgCav) list.get(j);
				if (interalBuy.getComplex().equals(imgCav.getComplex())
						&& name.equals(imgCav.getName())) {
					list.add(j + 1, interalBuy);
					break;
				}
			}
		}
	}

	/**
	 * 抓取合同核销料件资料
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractImgCav型，电子手册核销料件资料
	 */
	public List findContractImgCav(DzscContractCav contractCav) {
		List<DzscContractImgCav> list = this.contractCavDao
				.findContractImgCav(contractCav);
		Collections.sort(list, new Comparator<DzscContractImgCav>() {
			public int compare(DzscContractImgCav o1, DzscContractImgCav o2) {
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
		if (contractCav.getIsCustoms()) {
			resortDataForInteralBuy(list);
		}
		return list;
	}

	/**
	 * 抓取合同核销成品资料
	 * 
	 * @param contractCav
	 *            电子手册合同核销单头
	 * @return List 是DzscContractExgCav型，电子手册核销成品资料
	 */
	public List findContractExgCav(DzscContractCav contractCav) {
		List<DzscContractExgCav> list = this.contractCavDao
				.findContractExgCav(contractCav);
		Collections.sort(list, new Comparator<DzscContractExgCav>() {
			public int compare(DzscContractExgCav o1, DzscContractExgCav o2) {
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
	 * 删除中期核查表头
	 * 
	 * @param head
	 *            电子手册中期核查表头
	 */
	public void deleteDzscCheckHead(DzscCheckHead head) {
		this.contractCavDao.deleteAll(this.contractCavDao
				.findDzscCheckImg(head));
		this.contractCavDao.deleteDzscCheckHead(head);
	}

	/**
	 * 数据报核申报
	 * 
	 * @param dzscContractCav
	 */
	public DeclareFileInfo applyDzscContractCav(
			DzscContractCav dzscContractCav, String taskId) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscContractCav> lsHead = new ArrayList<DzscContractCav>();
		List lsCustomsDeclarationCav = new ArrayList();
		List<TempDzscExgImgCav> lsDzscContractExgCav = new ArrayList<TempDzscExgImgCav>();
		List<TempDzscExgImgCav> lsDzscContractImgCav = new ArrayList<TempDzscExgImgCav>();
		List lsDzscContractBomCav = new ArrayList();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		List lsDzscEmsPorHead = this.dzscDao
				.findDzscEmsPorHeadExcu(dzscContractCav.getEmsNo());
		DzscEmsPorHead dzscEmsPorHead = lsDzscEmsPorHead.size() > 0 ? (DzscEmsPorHead) lsDzscEmsPorHead
				.get(0)
				: null;
		if (dzscEmsPorHead == null) {
			throw new RuntimeException("系统找不到正在执行的手册"
					+ dzscContractCav.getEmsNo());
		}
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscEmsPorHead.getManageObject());
		signInfo.setSignDate(new Date());
		signInfo.setCopNo(dzscContractCav.getCopEmsNo());
		// signInfo.setCopNo(((DzscEmsPorHead)lsDzscEmsPorHead.get(0)).getCopTrNo());
		signInfo.setColDcrTime(1);
		signInfo.setSysType(DzscBusinessType.CANCEL_AFTER_VERIFICA);
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		dzscContractCav.setInputER(signInfo.getIdCard().substring(
				signInfo.getIdCard().length() - 4));
		dzscContractCav.setDeclareType("4");// 电子手册正式报核
		dzscContractCav.setDeclareDate(new Date());
		dzscContractCav.setStateMark(DzscState.APPLY);
		dzscContractCav.setDeclareTimes(1);
		this.contractCavDao.saveContractCav(dzscContractCav);
		lsHead.add(dzscContractCav);
		lsCustomsDeclarationCav = this.contractCavDao
				.findCustomsDeclarationCav(dzscContractCav);
		TempDzscExgImgCav temp = null;
		List list = this.contractCavDao.findContractImgCav(dzscContractCav);
		for (int i = 0; i < list.size(); i++) {
			DzscContractImgCav imgCav = (DzscContractImgCav) list.get(i);
			if (imgCav.getSeqNum() == null) {
				continue;
			}
			// 料件进口总数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.TOTAL_IMP_EXP);
			temp.setAmount(imgCav.getImpTotal());
			lsDzscContractImgCav.add(temp);
			// 深加工结转数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.TRANSFER_IMP_EXP);
			temp.setAmount(imgCav.getTransferFactoryImport());
			lsDzscContractImgCav.add(temp);
			// 产品总耗用量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.PRODUCT_USED);
			temp.setAmount(imgCav.getProductWaste());
			lsDzscContractImgCav.add(temp);
			// 内销数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.INNER_SALES);
			temp.setAmount(imgCav.getInternalAmount());
			lsDzscContractImgCav.add(temp);
			// 退运数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.BACK_MATERIEL_EXPORT);
			temp.setAmount(imgCav.getBackExport());
			lsDzscContractImgCav.add(temp);
			// 料件放弃数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.ABANDON_AMOUNT);
			temp.setAmount(0.0);
			lsDzscContractImgCav.add(temp);
			// 实际料件剩余数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.REMAIN_MATERIAL);
			temp.setAmount(imgCav.getRemainMaterial());
			lsDzscContractImgCav.add(temp);
			// 料件应剩余数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.SHOULD_REMIAN_MATERIAL);
			temp.setAmount(imgCav.getRemainMaterial());
			lsDzscContractImgCav.add(temp);
			// 边角料数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.LEFTOVER_MATERIAL);
			temp.setAmount(imgCav.getLeftoverMaterial());
			lsDzscContractImgCav.add(temp);
			// 余料转出数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.REMAIN_FORWARD_EXPORT);
			temp.setAmount(imgCav.getRemainExport());
			lsDzscContractImgCav.add(temp);
			// 企业库存数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(imgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.STOCK_AMOUNT);
			temp.setAmount(imgCav.getStockAmount());
			lsDzscContractImgCav.add(temp);

		}
		list = this.contractCavDao.findContractExgCav(dzscContractCav);
		for (int i = 0; i < list.size(); i++) {
			DzscContractExgCav exgCav = (DzscContractExgCav) list.get(i);
			if (exgCav.getSeqNum() == null) {
				continue;
			}
			// 成品出口总数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.TOTAL_IMP_EXP);
			temp.setAmount(exgCav.getExpTotal());
			lsDzscContractExgCav.add(temp);
			// 深加工结转数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.TRANSFER_IMP_EXP);
			temp.setAmount(exgCav.getTransferExpAmount());
			lsDzscContractExgCav.add(temp);
			// 成品放弃数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.ABANDON_AMOUNT);
			temp.setAmount(0.0);
			lsDzscContractExgCav.add(temp);
			// 成品退换出口数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.REWORK_EXPORT);
			temp.setAmount(exgCav.getReworkExport());
			lsDzscContractExgCav.add(temp);
			// 成品退换进口数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.BACK_FACTORY_REWORK);
			temp.setAmount(exgCav.getBackFactoryRework());
			lsDzscContractExgCav.add(temp);
			// 企业库存数量
			temp = new TempDzscExgImgCav();
			temp.setSeqNum(Integer.valueOf(exgCav.getSeqNum()));
			temp.setQtyType(DzscQuantityCavType.STOCK_AMOUNT);
			temp.setAmount(exgCav.getStockAmount());
			lsDzscContractExgCav.add(temp);
		}
		/**
		 * 单损耗
		 */
		lsDzscContractBomCav = this.contractCavDao
				.findContractBomCavChanged(dzscContractCav);

		String formatFile = "DzscContractCavFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("DzscContractCav", lsHead);
		hmData.put("DzscCustomsDeclarationCav", lsCustomsDeclarationCav);
		hmData.put("DzscContractExgCav", lsDzscContractExgCav);
		hmData.put("DzscContractImgCav", lsDzscContractImgCav);
		hmData.put("DzscContractBomCav", lsDzscContractBomCav);

		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	public DeclareFileInfo applyDzscCheck(DzscCheckHead dzscCheckHead,
			String taskId) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<DzscCheckHead> lsDzscCheckHead = new ArrayList<DzscCheckHead>();
		List<TempDzscCheckImgDetil> lsTemp = new ArrayList<TempDzscCheckImgDetil>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		dzscCheckHead.setDeclareDate(new Date());
		dzscCheckHead.setIsDeclare(true);
		dzscCheckHead.setDeclareState(DzscState.APPLY);
		this.contractCavDao.saveDzscCheckHead(dzscCheckHead);
		lsDzscCheckHead.add(dzscCheckHead);
		TempDzscCheckImgDetil temp = null;
		List list = this.contractCavDao.findDzscCheckImg(dzscCheckHead);
		for (int i = 0; i < list.size(); i++) {
			DzscCheckImg chekImg = (DzscCheckImg) list.get(i);
			if (chekImg.getSeqNum() == null
					|| "".equals(chekImg.getSeqNum().trim())) {
				continue;
			}
			// 原料在途数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.materByWay);
			temp.setAmount(chekImg.getMaterByWay());
			lsTemp.add(temp);
			// 原料库存数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.materStock);
			temp.setAmount(chekImg.getMaterStock());
			lsTemp.add(temp);
			// 废料数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.depose);
			temp.setAmount(chekImg.getDepose());
			lsTemp.add(temp);
			// 在线数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.onlines);
			temp.setAmount(chekImg.getOnlines());
			lsTemp.add(temp);
			// 边角料数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.overMater);
			temp.setAmount(chekImg.getOverMater());
			lsTemp.add(temp);
			// 本期原料入库数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.thisMaterInWare);
			temp.setAmount(chekImg.getThisMaterInWare());
			lsTemp.add(temp);
			// 原料领料数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.materGet);
			temp.setAmount(chekImg.getMaterGet());
			lsTemp.add(temp);
			// 本期原料内销数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.thisMaterCancel);
			temp.setAmount(chekImg.getThisMaterCancel());
			lsTemp.add(temp);
			// 本期放弃料件数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.thisThrow);
			temp.setAmount(chekImg.getThisThrow());
			lsTemp.add(temp);
			// 合格成品耗用数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.passExgUse);
			temp.setAmount(chekImg.getPassExgUse());
			lsTemp.add(temp);
			// 废品,残次品折料数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.otherConvert);
			temp.setAmount(chekImg.getOtherConvert());
			lsTemp.add(temp);
			// 本期放弃残次品折料
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.materReuse);
			temp.setAmount(chekImg.getMaterReuse());
			lsTemp.add(temp);
			// 原料退换
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.materExitChange);
			temp.setAmount(chekImg.getMaterExitChange());
			lsTemp.add(temp);
			// 半成品折料数量
			temp = new TempDzscCheckImgDetil();
			temp.setPtNum(chekImg.getPtNum());
			temp.setQtyType(DzscQuantityCheckType.halfExgConvert);
			temp.setAmount(chekImg.getHalfExgConvert());
			lsTemp.add(temp);
		}
		List lsDzscEmsPorHead = this.dzscDao
				.findDzscEmsPorHeadExcu(dzscCheckHead.getEmsNo());
		DzscEmsPorHead dzscEmsPorHead = lsDzscEmsPorHead.size() > 0 ? (DzscEmsPorHead) lsDzscEmsPorHead
				.get(0)
				: null;
		if (dzscEmsPorHead == null) {
			throw new RuntimeException("系统找不到正在执行的手册"
					+ dzscCheckHead.getEmsNo());
		}
		CspSignInfo signInfo = dzscMessageLogic.getCspPtsSignInfo(info,
				dzscEmsPorHead.getManageObject());
		// signInfo.setTradeCode(dzscCheckHead.getTradeCode());// 企业编码
		signInfo.setCopNo(dzscCheckHead.getCopEmsNo());// 企业内部编号
		signInfo.setSysType(DzscBusinessType.CHECK);// 业务类型
		signInfo.setColDcrTime(dzscCheckHead.getColShdulTime());// 核查次数
		signInfo.setSignDate(new Date());
		lsSignInfo.add(signInfo);
		String formatFile = "DzscCheckFormat.xml";
		hmData.put("PtsSignInfo", lsSignInfo);
		hmData.put("DzscCheckHead", lsDzscCheckHead);
		hmData.put("DzscCheckImg", lsTemp);
		return dzscMessageLogic.exportMessage(formatFile, hmData, info);
	}

	/**
	 * 核销处理回执
	 * 
	 * @param dzscContractCav
	 */
	public String processDzscContractCav(final DzscContractCav dzscContractCav,
			List lsReturnFile) {
		List list = this.dzscDao.findDzscEmsPorHeadExcu(dzscContractCav
				.getEmsNo());
		if (list.size() <= 0 || list.get(0) == null) {
			throw new RuntimeException("没有正在执行的手册:"
					+ dzscContractCav.getEmsNo());
		}
		final DzscEmsPorHead dzscEmsPorHead = (DzscEmsPorHead) list.get(0);
		final DzscEmsPorWjHead dzscEmsPorWjHead = this.dzscDao
				.findExingDzscEmsPorWjHeadByEmsNo(dzscEmsPorHead.getCorrEmsNo());
		return dzscMessageLogic.processMessage(
				DzscBusinessType.CANCEL_AFTER_VERIFICA, dzscContractCav
						.getCopEmsNo(), new CspProcessMessage() {

					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						dzscContractCav.setStateMark(DzscState.ORIGINAL);
						contractCavDao.saveContractCav(dzscContractCav);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						dzscEmsPorHead.setDeclareState(DzscState.CHECK_CANCEL);
						dzscDao.saveDzscEmsPorHead(dzscEmsPorHead);
						dzscContractCav.setStateMark(DzscState.CHECK_CANCEL);
						if (dzscEmsPorWjHead != null) {
							dzscEmsPorWjHead
									.setDeclareState(DzscState.CHECK_CANCEL);
							dzscDao.saveEmsPorWjHead(dzscEmsPorWjHead);
						}
						contractCavDao.saveContractCav(dzscContractCav);
					}
				}, lsReturnFile);
	}

	public String processDzscCheckHead(final DzscCheckHead dzscCheckHead,
			List lsReturnFile) {
		return dzscMessageLogic.processMessage(DzscBusinessType.CHECK,
				dzscCheckHead.getCopEmsNo(), new CspProcessMessage() {
					public void failureHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						dzscCheckHead.setDeclareState(DzscState.ORIGINAL);
						contractCavDao.saveDzscCheckHead(dzscCheckHead);
					}

					public void successHandling(
							TempCspReceiptResultInfo tempReceiptResult) {
						dzscCheckHead.setDeclareState(DzscState.EXECUTE);
						contractCavDao.saveDzscCheckHead(dzscCheckHead);
					}
				}, lsReturnFile);
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPCavMessage(String taskId, String messageFileName,
			TempExpQPCavMsgSelectParam param) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要要导出的资料");
		}
		List list = param.getLsPartData();
		System.out.println("Begin get data:" + System.currentTimeMillis());

		String formatFile = (param.isExg() ? "CavExgFormat.xml"
				: "CavImgFormat.xml");
		Map<String, List> hmData = new HashMap<String, List>();
		hmData.put("DataId", list);
		// dzscMessageLogic.exportQPMessage(messageFileName,
		// BusinessType.CANCEL_AFTER_VERIFICA, formatFile, hmData, info);
	}

	/**
	 * 导出文件（QP导入需要）
	 * 
	 * @param request
	 * @param param
	 */
	public void exportQPDzscCheckHeadMessage(String taskId,
			String messageFileName, List list) {
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要要导出的资料");
		}
		System.out.println("Begin get data:" + System.currentTimeMillis());

		String formatFile = "DzscCheckImgFormat.xml";
		Map<String, List> hmData = new HashMap<String, List>();
		hmData.put("CheckImgId", list);
		// dzscMessageLogic.exportQPMessage(messageFileName, BusinessType.CHECK,
		// formatFile, hmData, info);
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
				DzscContractBomCav contractBomCav = (DzscContractBomCav) list.get(i);
				if (contractBomCav.getModifyMark().equals(modifyMark)) {
					continue;
				}
				// if
				// (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				// // this.contractDao.deleteContractImg(contractImg);
				// contractImg.setModifyMark(ModifyMarkState.DELETED);
				// this.contractDao.saveContractImg(contractImg);
				// } else {
//				int count = this.contractDao
//						.findBcsCustomsDeclarationCommInfoCount(contractImg
//								.getContract().getEmsNo(), contractImg
//								.getSeqNum(), true);
//				if (count > 0) {
//					throw new RuntimeException("已经有报关单用到此料件"
//							+ contractImg.getSeqNum() + ":"
//							+ contractImg.getComplex().getCode() + ",所以不能删除");
//				}
				contractBomCav.setModifyMark(ModifyMarkState.DELETED);
				this.contractCavDao.saveContractBomCav(contractBomCav);
				// }
				/**
				 * 查找合同BOM 来自 合同料件序号
				 */
//				List contractBomList = this.contractDao
//						.findContractBomByImgSeqNum(contractImg);
//				this.deleteContractBom(contractBomList);
			}

		} else if (ModifyMarkState.UNCHANGE.equals(modifyMark)
				|| ModifyMarkState.MODIFIED.equals(modifyMark)
				// || ModifyMarkState.DELETED.equals(modifyMark)
				|| ModifyMarkState.ADDED.equals(modifyMark)) {
			for (int i = 0; i < list.size(); i++) {
				DzscContractBomCav contractBomCav = (DzscContractBomCav) list.get(i);
				// if (contractImg.getModifyMark().equals(modifyMark)) {
				// continue;
				// }
				// if
				// (ModifyMarkState.ADDED.equals(contractImg.getModifyMark())) {
				// continue;
				// } else
				if (ModifyMarkState.DELETED.equals(contractBomCav.getModifyMark())) {
					contractBomCav.setModifyMark(modifyMark);
					this.contractCavDao.saveContractBomCav(contractBomCav);
//					/**
//					 * 查找合同BOM 来自 合同料件序号
//					 */
//					List contractBomList = this.contractDao
//							.findContractBomByImgSeqNum(contractImg);
//					this.rollbackContractExg(contractBomList, modifyMark);
				} else {
					contractBomCav.setModifyMark(modifyMark);
					this.contractCavDao.saveContractBomCav(contractBomCav);
				}
			}
		}		
	}

}
