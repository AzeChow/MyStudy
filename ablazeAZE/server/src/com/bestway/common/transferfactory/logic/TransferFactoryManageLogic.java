package com.bestway.common.transferfactory.logic;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.cas.invoice.entity.CustomsEnvelopCommodityInfoEntity;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgBefore;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SBillType;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.dao.TransferFactoryManageDao;
import com.bestway.common.transferfactory.entity.BillPriceMaintenance;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopCommodityInfo;
import com.bestway.common.transferfactory.entity.CustomsEnvelopRequestBill;
import com.bestway.common.transferfactory.entity.CustomsEnvelopRequestCommodityInfo;
import com.bestway.common.transferfactory.entity.CustomsEnvelopSpareAnalyes;
import com.bestway.common.transferfactory.entity.MakeApplyToCustomsInfo;
import com.bestway.common.transferfactory.entity.MakeCustomsDeclaration;
import com.bestway.common.transferfactory.entity.MakeCustomsEnvelop;
import com.bestway.common.transferfactory.entity.MakeCustomsEnvelopRequest;
import com.bestway.common.transferfactory.entity.MakeTransferFactoryBill;
import com.bestway.common.transferfactory.entity.TempCustomsEmsInfo;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopCommInfo;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopRequestCommodityInfo;
import com.bestway.common.transferfactory.entity.TempCustomsEnvelopRequetBill;
import com.bestway.common.transferfactory.entity.TempTransferFactoryBill;
import com.bestway.common.transferfactory.entity.TempTransferFactoryCommodityInfo;
import com.bestway.common.transferfactory.entity.TempTransferFactoryImpExpGoodsList;
import com.bestway.common.transferfactory.entity.TempTransferStatisticTotalQuantity;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBill;
import com.bestway.common.transferfactory.entity.TransferFactoryInitBillCommodityInfo;
import com.bestway.customs.common.entity.BaseContractHead;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListAfterCommInfo;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.dzsc.customslist.entity.DzscCustomsBillList;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.invoice.entity.TempCustomsDelcarationInfo;

@SuppressWarnings("unchecked")
public class TransferFactoryManageLogic {
	private TransferFactoryManageDao transferFactoryManageDao = null;

	private EmsEdiTrDao emsEdiTrDao = null;

	private EncDao encDao = null;

	private CasDao casDao = null;

	private CommonBaseCodeDao commonCodeDao = null;

	private ContractDao contractDao = null;

	private DzscDao dzscDao = null;

	private Map map = null;

	public CommonBaseCodeDao getCommonCodeDao() {
		return commonCodeDao;
	}

	public void setCommonCodeDao(CommonBaseCodeDao commonCodeDao) {
		this.commonCodeDao = commonCodeDao;
	}

	public CasDao getCasDao() {
		return casDao;
	}

	public void setCasDao(CasDao casDao) {
		this.casDao = casDao;
	}

	public TransferFactoryManageDao getTransferFactoryManageDao() {
		return transferFactoryManageDao;
	}

	public void setTransferFactoryManageDao(
			TransferFactoryManageDao transferFactoryManageDao) {
		this.transferFactoryManageDao = transferFactoryManageDao;
	}

	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	public EncDao getEncDao() {
		return encDao;
	}

	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}

	public ContractDao getContractDao() {
		return contractDao;
	}

	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public DzscDao getDzscDao() {
		return dzscDao;
	}

	public void setDzscDao(DzscDao dascDao) {
		this.dzscDao = dascDao;
	}

	/**
	 * 获取供货商关封明细表
	 * 
	 * @param casCondition
	 * @param billDetail
	 * @return
	 * @author 石小凯
	 */
	public List getEnvelopBillDetail(List<Condition> conditions,
			List<Condition> conditions1) {
		// 最终返回关封明细中间类
		List<CustomsEnvelopCommodityInfoEntity> list = new ArrayList<CustomsEnvelopCommodityInfoEntity>();

		// 或许关封管理中的单据
		List<CustomsEnvelopCommodityInfo> ms = null;
		ms = casDao.commonSearch("", "CustomsEnvelopCommodityInfo", conditions,
				"", "");

		CustomsEnvelopCommodityInfoEntity cecie = null;

		// 时间类型转换
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		// 填充中间类
		if (ms.size() > 0) {
			for (CustomsEnvelopCommodityInfo cec : ms) {
				cecie = new CustomsEnvelopCommodityInfoEntity();
				cecie.setBeginAvailability(format.format(cec
						.getCustomsEnvelopBill().getBeginAvailability()));
				cecie.setCustomsEnvelopBillNo(cec.getCustomsEnvelopBill()
						.getCustomsEnvelopBillNo());

				cecie.setScmCoc(cec.getCustomsEnvelopBill().getScmCoc());

				cecie.setScmCocName(cec.getCustomsEnvelopBill().getScmCoc() == null ? ""
						: cec.getCustomsEnvelopBill().getScmCoc().getName());
				cecie.setPurchaseAndSaleContractNo(cec.getCustomsEnvelopBill()
						.getPurchaseAndSaleContractNo());
				cecie.setCarryForwardApplyToCustomsBillNo(cec
						.getCustomsEnvelopBill()
						.getCarryForwardApplyToCustomsBillNo());
				cecie.setComplexCode(cec.getComplex() == null ? "" : cec
						.getComplex().getCode());
				cecie.setPtName(cec.getPtName());
				cecie.setPtSpec(cec.getPtSpec());
				cecie.setUnitName(cec.getUnit() == null ? "" : cec.getUnit()
						.getName());
				cecie.setOwnerQuantity(cec.getOwnerQuantity() == null ? 0.0
						: cec.getOwnerQuantity());
				list.add(cecie);
			}
		}

		// 获取转厂申请表的单据
		List<FptAppItem> ms1 = casDao.commonSearch("", "FptAppItem",
				conditions1, "", "");
		// 填充中间类
		for (FptAppItem m : ms1) {
			cecie = new CustomsEnvelopCommodityInfoEntity();
			cecie.setBeginAvailability(format.format(m.getFptAppHead()
					.getInDate() == null ? "" : m.getFptAppHead().getInDate()));
			cecie.setCustomsEnvelopBillNo(m.getFptAppHead().getAppNo());
			cecie.setScmCocName(m.getFptAppHead().getScmCoc() == null ? "" : m
					.getFptAppHead().getScmCoc().getName());
			cecie.setPurchaseAndSaleContractNo(m.getFptAppHead().getContrNo());
			cecie.setComplexCode(m.getCodeTs() == null ? "" : m.getCodeTs()
					.getName());
			cecie.setPtName(m.getName());
			cecie.setPtSpec(m.getSpec());
			cecie.setUnitName(m.getUnit() == null ? "" : m.getUnit().getName());
			cecie.setOwnerQuantity(m.getQty() == null ? 0.0 : m.getQty());

			list.add(cecie);
		}
		return list;

	}

	/**
	 * 获取客户关封明细表
	 * 
	 * @param casCondition
	 * @param billDetail
	 * @return
	 * @author 石小凯
	 */
	public List getEnvelopBillDetailout(List<Condition> conditions,
			List<Condition> conditions1) {
		// 最终返回关封明细中间类
		List<CustomsEnvelopCommodityInfoEntity> list = new ArrayList<CustomsEnvelopCommodityInfoEntity>();

		// 或许关封管理中的单据
		List<CustomsEnvelopCommodityInfo> ms = null;
		ms = casDao.commonSearch("", "CustomsEnvelopCommodityInfo", conditions,
				"", "");

		CustomsEnvelopCommodityInfoEntity cecie = null;
		// 时间类型转换
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		// 填充中间类
		if (ms.size() > 0) {

			for (CustomsEnvelopCommodityInfo cec : ms) {
				cecie = new CustomsEnvelopCommodityInfoEntity();
				cecie.setBeginAvailability(format.format(cec
						.getCustomsEnvelopBill().getBeginAvailability()));
				cecie.setCustomsEnvelopBillNo(cec.getCustomsEnvelopBill()
						.getCustomsEnvelopBillNo());

				cecie.setScmCoc(cec.getCustomsEnvelopBill().getScmCoc());

				cecie.setScmCocName(cec.getCustomsEnvelopBill().getScmCoc() == null ? ""
						: cec.getCustomsEnvelopBill().getScmCoc().getName());
				cecie.setPurchaseAndSaleContractNo(cec.getCustomsEnvelopBill()
						.getPurchaseAndSaleContractNo());
				cecie.setCarryForwardApplyToCustomsBillNo(cec
						.getCustomsEnvelopBill()
						.getCarryForwardApplyToCustomsBillNo());
				cecie.setComplexCode(cec.getComplex() == null ? "" : cec
						.getComplex().getCode());
				cecie.setPtName(cec.getPtName());
				cecie.setPtSpec(cec.getPtSpec());
				cecie.setUnitName(cec.getUnit() == null ? "" : cec.getUnit()
						.getName());
				cecie.setOwnerQuantity(cec.getOwnerQuantity() == null ? 0.0
						: cec.getOwnerQuantity());
				list.add(cecie);
			}
		}
		// 获取转厂申请表的单据
		List<FptAppItem> ms1 = casDao.commonSearch("", "FptAppItem",
				conditions1, "", "");

		for (FptAppItem m : ms1) {
			cecie = new CustomsEnvelopCommodityInfoEntity();
			cecie.setBeginAvailability(format
					.format(m.getFptAppHead().getOutDate() == null ? "" : m
							.getFptAppHead().getOutDate()));
			cecie.setCustomsEnvelopBillNo(m.getFptAppHead().getAppNo());
			cecie.setScmCocName(m.getFptAppHead().getScmCoc() == null ? "" : m
					.getFptAppHead().getScmCoc().getName());
			cecie.setPurchaseAndSaleContractNo(m.getFptAppHead().getContrNo());
			cecie.setComplexCode(m.getCodeTs() == null ? "" : m.getCodeTs()
					.getName());
			cecie.setPtName(m.getName());
			cecie.setPtSpec(m.getSpec());
			cecie.setUnitName(m.getUnit() == null ? "" : m.getUnit().getName());
			cecie.setOwnerQuantity(m.getQty() == null ? 0.0 : m.getQty());

			list.add(cecie);
		}
		return list;

	}

	public void saveCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill) {
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopBill(customsEnvelopBill
						.getCustomsEnvelopBillNo());
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopBill temp = (CustomsEnvelopBill) list.get(i);
			if (!temp.getId().equals(customsEnvelopBill.getId())) {
				throw new RuntimeException("关封号"
						+ customsEnvelopBill.getCustomsEnvelopBillNo()
						+ "重复，不能保存");
			}
		}
		transferFactoryManageDao.saveCustomsEnvelopBill(customsEnvelopBill);
	}

	/**
	 * 获得最大的单据号来自进出货标志
	 */
	public long getMaxBillNoByImpExpGoodsFlag(boolean impExpGoodsFlag) {
		List list = this.transferFactoryManageDao
				.getMaxBillNoByImpExpGoodsFlag(impExpGoodsFlag);
		if (list.size() <= 0 || list.get(0) == null) {
			return getBillNo("0001");
		}
		String billNoStr = list.get(0).toString().trim();
		String prefix = billNoStr.substring(0, 8);
		if (prefix.equals(convertDateToString())) {
			String suffix = billNoStr.substring(billNoStr.length() - 4);
			String suffixNumber = String.valueOf(Integer.parseInt(suffix) + 1);
			suffix = replace("0", 4 - suffixNumber.length()) + suffixNumber;
			return getBillNo(suffix);
		} else {
			return getBillNo("0001");
		}
	}

	/**
	 * 获得最大的结转单据号来自进出货标志
	 */
	public long getMaxTransferFactoryBillNoByImpExpGoodsFlag(
			boolean impExpGoodsFlag) {
		String pattern = this.convertDateToString();
		List list = this.transferFactoryManageDao
				.getMaxTransferFactoryBillNoByImpExpGoodsFlag(impExpGoodsFlag,
						pattern);
		if (list.size() <= 0 || list.get(0) == null) {
			return getBillNo("0001");
		}
		String billNoStr = list.get(0).toString().trim();
		String prefix = billNoStr.substring(0, 8);
		if (prefix.equals(convertDateToString())) {
			String suffix = billNoStr.substring(billNoStr.length() - 4);
			String suffixNumber = String.valueOf(Integer.parseInt(suffix) + 1);
			suffix = replace("0", 4 - suffixNumber.length()) + suffixNumber;
			return getBillNo(suffix);
		} else {
			return getBillNo("0001");

		}
	}

	private String replace(String replaceChar, int count) {
		String str = "";
		for (int i = 0; i < count; i++) {
			str += replaceChar;
		}
		return str;
	}

	private long getBillNo(String suffix) {
		try {
			Integer.parseInt(suffix);
		} catch (Exception ee) {
			return -1;
		}
		return Long.parseLong(this.convertDateToString() + suffix);
	}

	private String convertDateToString() {
		String yearMonthDay = "";
		String month = "";
		String day = "";
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		yearMonthDay += calendar.get(Calendar.YEAR);
		month += calendar.get(Calendar.MONTH) + 1;
		yearMonthDay += replace("0", 2 - month.length()) + month;
		day += calendar.get(Calendar.DAY_OF_MONTH);
		yearMonthDay += replace("0", 2 - day.length()) + day;
		return yearMonthDay;
	}

	/**
	 * 获得关封单据信息加载子表的记录
	 */
	public List findCustomsEnvelopCommodityInfo(String parentId) {
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfo(parentId);
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo commInfo = (CustomsEnvelopCommodityInfo) list
					.get(i);
			double transAmount = getCustomsEnvelopTransferQuantity(commInfo
					.getCustomsEnvelopBill().getIsImpExpGoods(),
					commInfo.getSeqNum(), commInfo.getComplex(), commInfo
							.getCustomsEnvelopBill().getCustomsEnvelopBillNo());
			commInfo.setTransferQuantity(transAmount);
		}
		return list;
	}

	/**
	 * 获得关封申请单商品信息记录来自数据是否正确的检验
	 */
	public List findCustomsEnvelopRequestCommodityInfoByCheck(String parentId) {
		List newList = new ArrayList();
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopRequestCommodityInfo(parentId);
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopRequestCommodityInfo customsEnvelopRequestCommodityInfo = (CustomsEnvelopRequestCommodityInfo) list
					.get(i);
			if (customsEnvelopRequestCommodityInfo.getRequestQuantity() == null
					|| customsEnvelopRequestCommodityInfo.getRequestQuantity()
							.doubleValue() <= 0) {
				newList.add(customsEnvelopRequestCommodityInfo);
			}
		}
		return newList;
	}

	/**
	 * 获得关封单据商品信息记录来自数据是否正确的检验
	 */
	public List findCustomsEnvelopCommodityInfoByCheck(String parentId) {
		List newList = new ArrayList();
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfo(parentId);
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = (CustomsEnvelopCommodityInfo) list
					.get(i);
			if (customsEnvelopCommodityInfo.getOwnerQuantity() == null
					|| customsEnvelopCommodityInfo.getOwnerQuantity()
							.doubleValue() <= 0) {
				newList.add(customsEnvelopCommodityInfo);
			}
		}
		return newList;
	}

	/**
	 * 获得结转单据记录来自数据是否正确的检验
	 */
	public List findTransferFactoryCommodityInfoByCheck(String parentId) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findTransferFactoryCommodityInfo(parentId);

		for (int i = 0; i < oldList.size(); i++) {
			TransferFactoryCommodityInfo transferFactoryCommodityInfo = (TransferFactoryCommodityInfo) oldList
					.get(i);
			if (transferFactoryCommodityInfo.getQuantity() == null
					|| transferFactoryCommodityInfo.getQuantity().doubleValue() <= 0
			/*
			 * || transferFactoryCommodityInfo.getUnitPrice() == null ||
			 * transferFactoryCommodityInfo.getUnitPrice() .doubleValue() <= 0
			 */) {
				newList.add(transferFactoryCommodityInfo);
			}
		}
		return newList;
	}

	/**
	 * 获得关封申请单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findTempCustomsEnvelopRequestBillByScmCoc(String scmCocId) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findCustomsEnvelopRequestBillByScmCocToCEB(scmCocId);
		for (int i = 0; i < oldList.size(); i++) {
			CustomsEnvelopRequestBill c = (CustomsEnvelopRequestBill) oldList
					.get(i);
			TempCustomsEnvelopRequetBill temp = new TempCustomsEnvelopRequetBill();
			temp.setCustomsEnvelopRequestBill(c);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 获得关封申请单据商品信息来自父对象List
	 */
	public List findTempCustomsEnvelopRequestCommodityInfoByParent(
			List parentList) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findCustomsEnvelopRequestCommodityInfoByParent(parentList);
		for (int i = 0; i < oldList.size(); i++) {
			CustomsEnvelopRequestCommodityInfo c = (CustomsEnvelopRequestCommodityInfo) oldList
					.get(i);
			TempCustomsEnvelopRequestCommodityInfo temp = new TempCustomsEnvelopRequestCommodityInfo();
			temp.setC(c);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 关封申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(成品)
	 */
	public List checkTempCustomsEnvelopRequestCommodityInfoByFinishProduct(
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead) {
		List newList = new ArrayList();
		List emsList = this.transferFactoryManageDao
				.findEmsEdiMergerExgBeforeByEms(emsH2k, emsEdiMergerHead);
		Map map = new HashMap();
		if (emsList.size() <= 0) {
			return list;
		}
		for (int i = 0; i < emsList.size(); i++) {
			String ptNo = emsList.get(i).toString().toUpperCase().trim();
			map.put(ptNo, ptNo);
		}
		for (int i = 0; i < list.size(); i++) {
			TempCustomsEnvelopRequestCommodityInfo t = (TempCustomsEnvelopRequestCommodityInfo) list
					.get(i);
			Object ptNo = map.get(t.getC().getMateriel().getPtNo().trim()
					.toUpperCase());
			if (ptNo == null) {
				newList.add(t);
			}
		}
		return newList;
	}

	/**
	 * 关封申请单转关封时商品信息的检查--返回没有在电子帐册中备案的数据(料件)
	 */
	public List checkTempCustomsEnvelopRequestCommodityInfoByMateriel(
			List list, EmsHeadH2k emsH2k, EmsEdiMergerHead emsEdiMergerHead) {
		List newList = new ArrayList();
		Map map = new HashMap();
		List emsList = this.transferFactoryManageDao
				.findEmsEdiMergerImgBeforeByEms(emsH2k, emsEdiMergerHead);
		if (emsList.size() <= 0) {
			return list;
		}
		for (int i = 0; i < emsList.size(); i++) {
			String ptNo = emsList.get(i).toString().toUpperCase().trim();
			map.put(ptNo, ptNo);
		}
		for (int i = 0; i < list.size(); i++) {
			TempCustomsEnvelopRequestCommodityInfo t = (TempCustomsEnvelopRequestCommodityInfo) list
					.get(i);
			Object ptNo = map.get(t.getC().getMateriel().getPtNo().trim()
					.toUpperCase());
			if (ptNo == null) {
				newList.add(t);
			}
		}
		return newList;
	}

	/**
	 * 关封申请单-->返回已生成关封单据的关封申请单列表
	 */
	public List makeCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill,
			List dataSource) {
		List customsEnvelopCommodityInfoList = new ArrayList();
		List customsEnvelopRequestCommodityInfoList = new ArrayList();
		List makeCustomsEnvelopList = new ArrayList();
		Set set = new HashSet();
		List customsEnvelopRequestBillList = new ArrayList();
		//
		// 保存关封表头
		//
		this.transferFactoryManageDao
				.saveCustomsEnvelopBill(customsEnvelopBill);
		for (int i = 0; i < dataSource.size(); i++) {
			boolean isExist = false;
			if (!(dataSource.get(i) instanceof TempCustomsEnvelopRequestCommodityInfo)) {
				continue;
			}
			TempCustomsEnvelopRequestCommodityInfo t = (TempCustomsEnvelopRequestCommodityInfo) dataSource
					.get(i);
			customsEnvelopRequestCommodityInfoList.add(t.getC());
			String ptNo = t.getC().getMateriel().getPtNo();
			//
			// 保存关封申请单商品信息的父对象(set 不会保会相同的对象)
			//
			set.add(t.getC().getCustomsEnvelopRequestBill());
			//
			// 正在执行的电子帐册表头
			//
			EmsHeadH2k emsHeadH2k = (EmsHeadH2k) this.emsEdiTrDao
					.findEmsHeadH2kInExecuting().get(0);
			//
			// 根据物料号获得对应的 电子帐册的备案对象
			// 是进货保存物料，反之保存成品
			//
			if (customsEnvelopBill.getIsImpExpGoods().booleanValue() == true) {
				EmsHeadH2kImg emsHeadH2kImg = this.transferFactoryManageDao
						.findEmsHeadH2kImgByPtNo(emsHeadH2k, ptNo);
				if (i == 0) {
					CustomsEnvelopCommodityInfo c = new CustomsEnvelopCommodityInfo();
					c.setCustomsEnvelopBill(customsEnvelopBill);
					// c.setOwnerEmsHeadH2kImg(emsHeadH2kImg);
					c.setOwnerQuantity(t.getC().getRequestQuantity());
					customsEnvelopCommodityInfoList.add(c);
					//
					// 生成中间表信息
					//
					makeCustomsEnvelopList.add(this.makeMakeCustomsEnvelop(
							t.getC(), c));
				} else {
					//
					// 如果料号相同的话,那么合成一条关封商品信息
					//
					for (int j = 0; j < customsEnvelopCommodityInfoList.size(); j++) {
						CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) customsEnvelopCommodityInfoList
								.get(j);
						if (emsHeadH2kImg.getSeqNum().equals(c.getSeqNum())) {
							Double quantity = Double.valueOf(c
									.getOwnerQuantity().doubleValue()
									+ t.getC().getRequestQuantity()
											.doubleValue());
							c.setOwnerQuantity(quantity);
							//
							// 生成中间表信息
							//
							makeCustomsEnvelopList.add(this
									.makeMakeCustomsEnvelop(t.getC(), c));
							isExist = true;
							break;
						}
					}
					if (isExist == false) {
						CustomsEnvelopCommodityInfo c = new CustomsEnvelopCommodityInfo();
						c.setCustomsEnvelopBill(customsEnvelopBill);

						c.setComplex(emsHeadH2kImg.getComplex());
						c.setCurr(emsHeadH2kImg.getCurr());
						c.setPtName(emsHeadH2kImg.getName());
						c.setPtSpec(emsHeadH2kImg.getSpec());
						c.setSeqNum(emsHeadH2kImg.getSeqNum());
						c.setUnit(emsHeadH2kImg.getUnit());

						c.setOwnerQuantity(t.getC().getRequestQuantity());
						customsEnvelopCommodityInfoList.add(c);
						//
						// 生成中间表信息
						//
						makeCustomsEnvelopList.add(this.makeMakeCustomsEnvelop(
								t.getC(), c));
					}
				}
			} else {
				EmsHeadH2kExg emsHeadH2kExg = this.transferFactoryManageDao
						.findEmsHeadH2kExgByPtNo(emsHeadH2k, ptNo);
				if (i == 0) {
					CustomsEnvelopCommodityInfo c = new CustomsEnvelopCommodityInfo();
					c.setCustomsEnvelopBill(customsEnvelopBill);
					c.setComplex(emsHeadH2kExg.getComplex());
					c.setCurr(emsHeadH2kExg.getCurr());
					c.setPtName(emsHeadH2kExg.getName());
					c.setPtSpec(emsHeadH2kExg.getSpec());
					c.setSeqNum(emsHeadH2kExg.getSeqNum());
					c.setUnit(emsHeadH2kExg.getUnit());

					c.setOwnerQuantity(t.getC().getRequestQuantity());
					customsEnvelopCommodityInfoList.add(c);
					//
					// 生成中间表信息
					//
					makeCustomsEnvelopList.add(this.makeMakeCustomsEnvelop(
							t.getC(), c));
				} else {
					for (int j = 0; j < customsEnvelopCommodityInfoList.size(); j++) {
						CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) customsEnvelopCommodityInfoList
								.get(j);
						if (emsHeadH2kExg.getSeqNum().equals(c.getSeqNum())) {
							Double quantity = Double.valueOf(c
									.getOwnerQuantity().doubleValue()
									+ t.getC().getRequestQuantity()
											.doubleValue());
							c.setOwnerQuantity(quantity);
							//
							// 生成中间表信息
							//
							makeCustomsEnvelopList.add(this
									.makeMakeCustomsEnvelop(t.getC(), c));
							isExist = true;
							break;
						}
					}
					if (isExist == false) {
						CustomsEnvelopCommodityInfo c = new CustomsEnvelopCommodityInfo();
						c.setCustomsEnvelopBill(customsEnvelopBill);
						c.setComplex(emsHeadH2kExg.getComplex());
						c.setCurr(emsHeadH2kExg.getCurr());
						c.setPtName(emsHeadH2kExg.getName());
						c.setPtSpec(emsHeadH2kExg.getSpec());
						c.setSeqNum(emsHeadH2kExg.getSeqNum());
						c.setUnit(emsHeadH2kExg.getUnit());
						c.setOwnerQuantity(t.getC().getRequestQuantity());
						customsEnvelopCommodityInfoList.add(c);
						//
						// 生成中间表信息
						//
						makeCustomsEnvelopList.add(this.makeMakeCustomsEnvelop(
								t.getC(), c));
					}
				}
			}

		}
		//
		// 保存关封商品信息列表
		//
		this.transferFactoryManageDao
				.saveCustomsEnvelopCommodityInfo(customsEnvelopCommodityInfoList);
		//
		// 保存生成关封中间表信息
		//
		this.transferFactoryManageDao
				.saveMakeCustomsEnvelop(makeCustomsEnvelopList);
		//
		// 回写关封申请单商品信息(设置转关为true)
		//
		for (int i = 0; i < customsEnvelopRequestCommodityInfoList.size(); i++) {
			CustomsEnvelopRequestCommodityInfo c = (CustomsEnvelopRequestCommodityInfo) customsEnvelopRequestCommodityInfoList
					.get(i);
			c.setIsTranCustomsEnvelop(new Boolean(true));
		}
		this.transferFactoryManageDao
				.saveCustomsEnvelopRequestCommodityInfo(customsEnvelopRequestCommodityInfoList);
		//
		// 修改关封申请单的已转关封字段
		//
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			CustomsEnvelopRequestBill c = (CustomsEnvelopRequestBill) iterator
					.next();
			c.setIsMakeCustomsEnvelop(new Boolean(true));
			this.transferFactoryManageDao.saveCustomsEnvelopRequestBill(c);
			customsEnvelopRequestBillList.add(c);
		}
		//
		// 返回已生成关封单据的关封申请单列表
		//
		return customsEnvelopRequestBillList;
	}

	/**
	 * 生成关封单据时的中间表信息
	 * 
	 * @param cerCommodityInfo
	 * @param ceCommodityInfo
	 * @return
	 */
	private MakeCustomsEnvelop makeMakeCustomsEnvelop(
			CustomsEnvelopRequestCommodityInfo cerCommodityInfo,
			CustomsEnvelopCommodityInfo ceCommodityInfo) {
		MakeCustomsEnvelop m = new MakeCustomsEnvelop();
		m.setCompany(CommonUtils.getCompany());
		m.setCeCommodityInfo(ceCommodityInfo);
		m.setCerCommodityInfo(cerCommodityInfo);
		return m;
	}

	/**
	 * 修改关封申请单的关封单据字段,和生成关封单据字段为true
	 */
	public List updateCustomsEnvelopRequestBill(String customsEnvelopBillId,
			List dataSource) {
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			CustomsEnvelopRequestBill data = ((TempCustomsEnvelopRequetBill) dataSource
					.get(i)).getCustomsEnvelopRequestBill();
			data.setIsMakeCustomsEnvelop(new Boolean(true));
			this.transferFactoryManageDao.saveCustomsEnvelopRequestBill(data);
			list.add(data);
		}
		return list;
	}

	/**
	 * 删除关封单据
	 */
	public void deleteCustomsEnvelopBill(CustomsEnvelopBill customsEnvelopBill) {
		String customsEnvelopBillId = customsEnvelopBill.getId();
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfo(customsEnvelopBillId);
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) list
					.get(i);
			this.writeBackCustomsEnvelopRequestBillToCustomsBillId(c.getId());
			this.transferFactoryManageDao.deleteCustomsEnvelopCommodityInfo(c);
		}
		this.transferFactoryManageDao
				.deleteCustomsEnvelopBill(customsEnvelopBill);
	}

	/**
	 * 删除关封单据商品信息数据
	 */
	public void deleteCustomsEnvelopCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo data = (CustomsEnvelopCommodityInfo) list
					.get(i);
			this.writeBackCustomsEnvelopRequestBillToCustomsBillId(data.getId());
			this.transferFactoryManageDao
					.deleteCustomsEnvelopCommodityInfo(data);
		}
	}

	/**
	 * 当关封被删除时回写关封申请单--->并修改是否生成关封单据为false
	 */
	public void writeBackCustomsEnvelopRequestBillToCustomsBillId(
			String customsEnvelopCommodityInfoId) {
		List dataSource = this.transferFactoryManageDao
				.findMakeCustomsEnvelopByCustomsEnvelopCommodityInfo(customsEnvelopCommodityInfoId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			//
			// 获得生成关封单据中间表对象
			//
			MakeCustomsEnvelop makeCustomsEnvelop = (MakeCustomsEnvelop) dataSource
					.get(i);
			//
			// 修改关封申请单商品信息是转关封单据为false
			//
			CustomsEnvelopRequestCommodityInfo c = (CustomsEnvelopRequestCommodityInfo) makeCustomsEnvelop
					.getCerCommodityInfo();

			c.setIsTranCustomsEnvelop(new Boolean(false));
			List list = new ArrayList();
			list.add(c);
			this.transferFactoryManageDao
					.saveCustomsEnvelopRequestCommodityInfo(list);
			//
			// 检查关封申请单是否是转关封
			//
			list.clear();
			list = this.transferFactoryManageDao
					.findCustomsEnvelopRequestCommodityInfo(c
							.getCustomsEnvelopRequestBill().getId());
			boolean isModify = this
					.validateCusotmsEnvelopCommodityInfoAllDataIsFalse(list);
			if (isModify == true) {
				if (list == null || list.size() <= 0) {
					return;
				}
				CustomsEnvelopRequestBill customsEnvelopRequestBill = c
						.getCustomsEnvelopRequestBill();
				customsEnvelopRequestBill.setIsMakeCustomsEnvelop(new Boolean(
						false));
				this.transferFactoryManageDao
						.saveCustomsEnvelopRequestBill(customsEnvelopRequestBill);
			}
			//
			// 删除中间表信息
			//
			this.transferFactoryManageDao
					.deleteMakeCustomsEnvelop(makeCustomsEnvelop);
		}
	}

	/**
	 * 验证所有关封申请单商品信息的isTransferCustomsEnvelop(转报关封单)否都为false
	 * 
	 * @param list
	 * @return
	 */
	private boolean validateCusotmsEnvelopCommodityInfoAllDataIsFalse(List list) {
		boolean isFlag = true;
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopRequestCommodityInfo c = (CustomsEnvelopRequestCommodityInfo) list
					.get(i);
			if (c.getIsTranCustomsEnvelop() != null) {
				if (c.getIsTranCustomsEnvelop().booleanValue() == true) {
					isFlag = false;
					break;
				}
			}
		}
		return isFlag;
	}

	/**
	 * 保存转厂初始化单据
	 */
	public void saveTransferFactoryInitBill(
			TransferFactoryInitBill transferFactoryInitBill) {
		this.transferFactoryManageDao
				.saveTransferFactoryInitBill(transferFactoryInitBill);
	}

	/**
	 * 保存转厂初始化单据
	 */
	public TransferFactoryInitBill saveTransferFactoryInitBillCommodityInfo(
			TransferFactoryInitBill initBill, List list) {
		TransferFactoryInitBillCommodityInfo initBillCommodityInfo = null;
		for (int i = 0; i < list.size(); i++) {
			initBillCommodityInfo = (TransferFactoryInitBillCommodityInfo) list
					.get(i);
			this.transferFactoryManageDao
					.saveTransferFactoryInitBillCommodityInfo(initBillCommodityInfo);
		}
		initBill = this.transferFactoryManageDao
				.findTransferFactoryInitBillById(initBill);
		initBill.setItemCount(this.getTransferInitBillCommInfoCount(initBill));
		this.saveTransferFactoryInitBill(initBill);
		return initBill;
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	public void saveTransferFactoryInitBillCommodityInfo(
			TransferFactoryInitBillCommodityInfo transferFactoryInitBillCommodityInfo) {
		this.transferFactoryManageDao
				.saveTransferFactoryInitBillCommodityInfo(transferFactoryInitBillCommodityInfo);
	}

	/**
	 * 删除转厂初始化单据
	 */
	public void deleteTransferFactoryInitBill(
			TransferFactoryInitBill transferFactoryInitBill) {
		TransferFactoryInitBillCommodityInfo commInfo = null;
		List list = this.transferFactoryManageDao
				.findTransferFactoryInitCommodityInfo(transferFactoryInitBill
						.getId());
		this.deleteTransferFactoryInitBillCommodityInfo(list);
		this.transferFactoryManageDao
				.deleteTransferFactoryInitBill(transferFactoryInitBill);
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	private void deleteTransferFactoryInitBillCommodityInfo(List list) {
		TransferFactoryInitBillCommodityInfo commInfo = null;
		for (int i = 0; i < list.size(); i++) {
			commInfo = (TransferFactoryInitBillCommodityInfo) list.get(i);
			this.transferFactoryManageDao
					.deleteTransferFactoryInitBillCommodityInfo(commInfo);
		}
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	public void deleteTransferFactoryInitBillCommodityInfo(
			TransferFactoryInitBill initBill, List list) {
		TransferFactoryInitBillCommodityInfo commInfo = null;
		this.deleteTransferFactoryInitBillCommodityInfo(list);
		initBill.setItemCount(this.getTransferInitBillCommInfoCount(initBill));
		this.saveTransferFactoryInitBill(initBill);
	}

	private Integer getTransferInitBillCommInfoCount(
			TransferFactoryInitBill initBill) {
		List list = this.findTransferFactoryInitCommodityInfo(initBill.getId());
		return new Integer(list.size());
	}

	/**
	 * 获得所有初始化结转单据
	 */
	public List findTransferFactoryInitBill() {
		return this.transferFactoryManageDao.findTransferFactoryInitBill();
	}

	/**
	 * 获得结转初始化结转单据-进货,或者出货
	 */
	public List findTransferFactoryInitBill(boolean isImpExpFlag) {
		return this.transferFactoryManageDao
				.findTransferFactoryInitBill(isImpExpFlag);
	}

	/**
	 * 取得最大转厂初始化单据号码+1
	 * 
	 * @return
	 */
	public String getTransferFactoryInitBillMaxCode() {
		return transferFactoryManageDao.getTransferFactoryInitBillMaxCode();
	}

	/**
	 * 获得转厂初始化单据信息加载子表的记录
	 */
	public List findTransferFactoryInitCommodityInfo(String parentId) {
		return this.transferFactoryManageDao
				.findTransferFactoryInitCommodityInfo(parentId);
	}

	/**
	 * 查询不在转厂起初单的商品编码
	 * 
	 * @param initBillId
	 * @return
	 */
	public List findComplexNotInInitBill(String initBillId,
			String customsEnvelopBillCode) {
		List list = this.transferFactoryManageDao
				.findTransferFactoryCommInfoByEnvelpCode(customsEnvelopBillCode);
		List lsInitBill = this.transferFactoryManageDao
				.findTransferFactoryInitCommodityInfo(initBillId);
		List<String> lsTemp = new ArrayList<String>();
		for (int i = 0; i < lsInitBill.size(); i++) {
			TransferFactoryInitBillCommodityInfo initCommInfo = (TransferFactoryInitBillCommodityInfo) lsInitBill
					.get(i);
			lsTemp.add((initCommInfo.getEmsNo() == null ? "" : initCommInfo
					.getEmsNo())
					+ (initCommInfo.getSeqNum() == null ? "" : initCommInfo
							.getSeqNum().toString()));
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			CustomsEnvelopCommodityInfo commInfo = (CustomsEnvelopCommodityInfo) list
					.get(i);
			if (lsTemp.contains((commInfo.getEmsNo() == null ? "" : commInfo
					.getEmsNo())
					+ (commInfo.getSeqNum() == null ? "" : commInfo.getSeqNum()
							.toString()))) {
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findTempTransferFactoryBillByScmCocNotCER(String scmCocId) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findTransferFactoryBillByScmCocIdToCER(scmCocId);
		for (int i = 0; i < oldList.size(); i++) {
			TransferFactoryBill t = (TransferFactoryBill) oldList.get(i);
			TempTransferFactoryBill temp = new TempTransferFactoryBill();
			temp.setT(t);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、存在未转报关清单的商品 的单据
	 */
	public List findTempTransferFactoryBillByScmCocNotATC(String scmCocId,
			String emsNo) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findTransferFactoryBillByScmCocIdToATC(scmCocId, emsNo);
		for (int i = 0; i < oldList.size(); i++) {
			TransferFactoryBill t = (TransferFactoryBill) oldList.get(i);
			TempTransferFactoryBill temp = new TempTransferFactoryBill();
			temp.setT(t);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 获得转厂单据商品信息来自父对象List
	 */
	public List findTempTransferFactoryCommodityInfoByParent(List parentList) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findTransferFactoryCommodityInfoByParent(parentList);
		for (int i = 0; i < oldList.size(); i++) {
			TransferFactoryCommodityInfo t = (TransferFactoryCommodityInfo) oldList
					.get(i);
			TempTransferFactoryCommodityInfo temp = new TempTransferFactoryCommodityInfo();
			temp.setT(t);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 获得转厂单据商品信息来自父对象List 用于转换报关清单过程中
	 */
	public List findTempTransferFactoryCommodityInfoByParentToACT(
			List parentList) {
		List newList = new ArrayList();
		List oldList = this.transferFactoryManageDao
				.findTransferFactoryCommodityInfoByParentToATC(parentList);
		for (int i = 0; i < oldList.size(); i++) {
			TransferFactoryCommodityInfo t = (TransferFactoryCommodityInfo) oldList
					.get(i);
			TempTransferFactoryCommodityInfo temp = new TempTransferFactoryCommodityInfo();
			temp.setT(t);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 拆分结转单据，将结转单据没有报关的数量，生成一个新的结转单据
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	public TransferFactoryBill splitTransferFactoryBill(
			TransferFactoryBill transferFactoryBill) {
		TransferFactoryBill newTransferFactoryBill = new TransferFactoryBill();
		List list = this.transferFactoryManageDao
				.findTransferFactoryCommodityInfo(transferFactoryBill.getId());
		int remainCount = 0;
		try {
			PropertyUtils.copyProperties(newTransferFactoryBill,
					transferFactoryBill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		newTransferFactoryBill.setId(null);
		newTransferFactoryBill
				.setTransferFactoryBillNo(String.valueOf(this
						.getMaxTransferFactoryBillNoByImpExpGoodsFlag(transferFactoryBill
								.getIsImpExpGoods())));
		newTransferFactoryBill.setFromTransFactBillNo(transferFactoryBill
				.getTransferFactoryBillNo());
		newTransferFactoryBill.setIsAvailability(false);
		newTransferFactoryBill.setIsApplyToCustomsBill(false);
		newTransferFactoryBill.setIsCustomsEnvelopRequestBill(false);
		newTransferFactoryBill.setIsCustomsDeclaration(false);
		this.transferFactoryManageDao
				.saveTransferFactoryBill(newTransferFactoryBill);
		for (int i = 0; i < list.size(); i++) {
			TransferFactoryCommodityInfo oldCommInfo = (TransferFactoryCommodityInfo) list
					.get(i);
			double remainAmount = CommonUtils.getDoubleExceptNull(oldCommInfo
					.getQuantity())
					- CommonUtils.getDoubleExceptNull(oldCommInfo
							.getTransFactAmount());
			if (remainAmount > 0) {
				TransferFactoryCommodityInfo newCommInfo = new TransferFactoryCommodityInfo();
				try {
					PropertyUtils.copyProperties(newCommInfo, oldCommInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				newCommInfo.setId(null);
				newCommInfo.setTransFactAmount(remainAmount);
				newCommInfo.setTransferFactoryBill(newTransferFactoryBill);
				this.transferFactoryManageDao
						.saveTransferFactoryCommodityInfo(newCommInfo);
				remainCount++;
			}
		}
		if (remainCount <= 0) {
			throw new RuntimeException("此结转单据:"
					+ transferFactoryBill.getTransferFactoryBillNo()
					+ "的数量已全部转厂，不能再拆分新的结转单据");
		}

		return newTransferFactoryBill;
	}

	public void saveTransferFactoryBill(TransferFactoryBill transferFactoryBill) {
		transferFactoryBill.setItemCount(transferFactoryManageDao
				.findTransferFactoryCommodityInfoCount(transferFactoryBill
						.getId()));
		transferFactoryManageDao.saveTransferFactoryBill(transferFactoryBill);
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public void saveTransferFactoryCommodityInfo(
			TransferFactoryCommodityInfo commInfo) {
		// double transedAmount = this.transferFactoryManageDao
		// .findTransFactCommInfoAmount(commInfo);
		// double envelopAmount = 0.0;
		// CustomsEnvelopCommodityInfo envelopCommInfo =
		// this.transferFactoryManageDao
		// .findCustomsEnvelopCommodityInfo(commInfo
		// .getTransferFactoryBill().getEnvelopNo(), commInfo
		// .getEmsNo(), commInfo.getSeqNum());
		// if (envelopCommInfo != null) {
		// envelopAmount = CommonUtils.getDoubleExceptNull(envelopCommInfo
		// .getOwnerQuantity());
		// }
		// double currentAmount = CommonUtils.getDoubleExceptNull(commInfo
		// .getTransFactAmount());
		// if (currentAmount > (envelopAmount - transedAmount)) {
		// throw new RuntimeException("转厂数量已超出关封数量，不能保存!  本商品只剩下"
		// + (envelopAmount - transedAmount) + "可转");
		// }
		this.transferFactoryManageDao
				.saveTransferFactoryCommodityInfo(commInfo);
	}

	/**
	 * 查找结转单据商品的可待转数量还有多少
	 */
	public double findTransferFactoryCommodityInfoLeft(
			TransferFactoryCommodityInfo commInfo) {
		double transedAmount = this.transferFactoryManageDao
				.findTransFactCommInfoAmount(commInfo.getTransferFactoryBill()
						.getEnvelopNo(), commInfo.getEmsNo(), commInfo
						.getSeqNum(), commInfo.getId());
		double envelopAmount = 0.0;
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfoNew(commInfo
						.getTransferFactoryBill().getEnvelopNo(), commInfo
						.getEmsNo(), commInfo.getSeqNum());
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) list
					.get(i);
			if (null != c && null != c.getOwnerQuantity()) {
				envelopAmount += CommonUtils.getDoubleExceptNull(c
						.getOwnerQuantity());
			}
		}
		BigDecimal bigEnv = new BigDecimal(envelopAmount * Math.pow(10.0, 16));
		BigDecimal bigTran = new BigDecimal(transedAmount * Math.pow(10.0, 16));

		double temp = bigEnv.subtract(bigTran).movePointLeft(16).doubleValue();
		return temp;
	}

	public List saveTransferFactoryCommodityInfo(
			TransferFactoryBill transferFactoryBill, List list) {
		List lsCommInfo = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TransferFactoryCommodityInfo data = new TransferFactoryCommodityInfo();
			CustomsEnvelopCommodityInfo temp = (CustomsEnvelopCommodityInfo) list
					.get(i);
			// data.setMateriel(tempMateriel.getMateriel());
			data.setTransferFactoryBill(transferFactoryBill);
			data.setEmsNo(temp.getEmsNo());
			data.setSeqNum(temp.getSeqNum());
			data.setComplex(temp.getComplex());
			data.setCommName(temp.getPtName());
			data.setCommSpec(temp.getPtSpec());
			data.setUnit(temp.getUnit());
			data.setUnitPrice(temp.getUnitPrice());
			data.setCurr(temp.getCurr());
			data.setIsTransferATC(new Boolean(false));
			data.setQuantity(temp.getOwnerQuantity());
			// data.setIsPutOnRecord(tempMateriel.getIsMemo());
			lsCommInfo.add(data);
		}
		transferFactoryManageDao.saveTransferFactoryCommodityInfo(lsCommInfo);
		transferFactoryBill.setItemCount(transferFactoryManageDao
				.findTransferFactoryCommodityInfoCount(transferFactoryBill
						.getId()));
		this.transferFactoryManageDao
				.saveTransferFactoryBill(transferFactoryBill);
		return lsCommInfo;
	}

	/**
	 * 结转单据--->返回已生成关封申请单据的结转单据列表
	 */
	public List makeCustomsEnvelopRequestBill(
			CustomsEnvelopRequestBill customsEnvelopRequestBill, List dataSource) {
		Map map = new HashMap();
		Set set = new HashSet();
		List transferFactoryBillList = new ArrayList();
		List makeCustomsEnvelopRequestList = new ArrayList();
		List transferFactoryCommodityInfoList = new ArrayList();
		//
		// 保存关封申请单表头
		//
		this.transferFactoryManageDao
				.saveCustomsEnvelopRequestBill(customsEnvelopRequestBill);
		for (int i = 0; i < dataSource.size(); i++) {
			if (!(dataSource.get(i) instanceof TempTransferFactoryCommodityInfo)) {
				continue;
			}
			TempTransferFactoryCommodityInfo t = (TempTransferFactoryCommodityInfo) dataSource
					.get(i);
			set.add(t.getT().getTransferFactoryBill());
			transferFactoryCommodityInfoList.add(t.getT());
			if (i == 0) {
				CustomsEnvelopRequestCommodityInfo c = new CustomsEnvelopRequestCommodityInfo();
				c.setCustomsEnvelopRequestBill(customsEnvelopRequestBill);
				// c.setMateriel(t.getT().getMateriel());
				c.setRequestQuantity(t.getT().getQuantity());
				c.setUnitPrice(t.getT().getUnitPrice());
				map.put(c.getMateriel().getPtNo().trim(), c);
				//
				// 生成中间信息表 makeCustomsEnvelopRequest
				//
				makeCustomsEnvelopRequestList.add(this
						.makeMakeCustomsEnvelopRequest(c, t.getT()));
			} else {
				// Object obj =
				// map.get(t.getT().getMateriel().getPtNo().trim());
				// if (obj != null) {
				// CustomsEnvelopRequestCommodityInfo c =
				// (CustomsEnvelopRequestCommodityInfo) obj;
				// Double quantity = Double.valueOf(c.getRequestQuantity()
				// .doubleValue()
				// + t.getT().getQuantity().doubleValue());
				// c.setRequestQuantity(quantity);
				// //
				// // 生成中间信息表 makeCustomsEnvelopRequest
				// //
				// makeCustomsEnvelopRequestList.add(this
				// .makeMakeCustomsEnvelopRequest(c, t.getT()));
				// } else {
				// CustomsEnvelopRequestCommodityInfo c = new
				// CustomsEnvelopRequestCommodityInfo();
				// c.setCustomsEnvelopRequestBill(customsEnvelopRequestBill);
				// c.setMateriel(t.getT().getMateriel());
				// c.setRequestQuantity(t.getT().getQuantity());
				// c.setUnitPrice(t.getT().getUnitPrice());
				// map.put(c.getMateriel().getPtNo().trim(), c);
				// //
				// // 生成中间信息表 makeCustomsEnvelopRequest
				// //
				// makeCustomsEnvelopRequestList.add(this
				// .makeMakeCustomsEnvelopRequest(c, t.getT()));
				// }
			}
		}
		//
		// 保存生成的关封申请单商品信息列表
		//
		List customsEnvelopRequestCommodityInfoList = new ArrayList();
		customsEnvelopRequestCommodityInfoList.addAll(map.values());
		this.transferFactoryManageDao
				.saveCustomsEnvelopRequestCommodityInfo(customsEnvelopRequestCommodityInfoList);
		//
		// 修改转厂商品信息列表设置isTransferCustomsEnvelopRequest 为 true
		//
		for (int i = 0; i < transferFactoryCommodityInfoList.size(); i++) {
			TransferFactoryCommodityInfo t = (TransferFactoryCommodityInfo) transferFactoryCommodityInfoList
					.get(i);
			t.setIsTransferCustomsEnvelopReqeust(new Boolean(true));
		}
		this.transferFactoryManageDao
				.saveTransferFactoryCommodityInfo(transferFactoryCommodityInfoList);
		//
		// 保存生成的中间信息表
		//
		this.transferFactoryManageDao
				.saveMakeCustomsEnvelopRequest(makeCustomsEnvelopRequestList);
		//
		// 修改结转单据的已转关封申请单字段
		//
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			TransferFactoryBill t = (TransferFactoryBill) iterator.next();
			t.setIsCustomsEnvelopRequestBill(new Boolean(true));
			this.transferFactoryManageDao.saveTransferFactoryBill(t);
			transferFactoryBillList.add(t);
		}
		//
		// 返回已生成关封申请单据的结转单据列表
		//
		return transferFactoryBillList;
	}

	/**
	 * 生成关封申请单据时的中间表信息
	 * 
	 * @param cerCommodityInfo
	 * @param tfCommodityInfo
	 * @return
	 */
	private MakeCustomsEnvelopRequest makeMakeCustomsEnvelopRequest(
			CustomsEnvelopRequestCommodityInfo cerCommodityInfo,
			TransferFactoryCommodityInfo tfCommodityInfo) {
		MakeCustomsEnvelopRequest m = new MakeCustomsEnvelopRequest();
		m.setCompany(CommonUtils.getCompany());
		m.setTfCommodityInfo(tfCommodityInfo);
		m.setCerCommodityInfo(cerCommodityInfo);
		return m;
	}

	/**
	 * 修改结转单据的关封申请单字段,和生成关封申请单据字段为true -->来自关封申请单
	 */
	public List updateTransferFactoryBillByCER(
			String customsEnvelopRequestBillId, List dataSource) {
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			TransferFactoryBill data = ((TempTransferFactoryBill) dataSource
					.get(i)).getT();
			// data.setCustomsEnvelopRequestBillId(customsEnvelopRequestBillId);
			data.setIsCustomsEnvelopRequestBill(new Boolean(true));
			this.transferFactoryManageDao.saveTransferFactoryBill(data);
			list.add(data);
		}
		return list;
	}

	/**
	 * 删除关封单据
	 */
	public void deleteCustomsEnvelopRequestBill(
			CustomsEnvelopRequestBill customsEnvelopRequestBill) {
		String customsEnvelopRequestBillId = customsEnvelopRequestBill.getId();
		List list = this.transferFactoryManageDao
				.findCustomsEnvelopRequestCommodityInfo(customsEnvelopRequestBillId);
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopRequestCommodityInfo data = (CustomsEnvelopRequestCommodityInfo) list
					.get(i);
			this.writeBackTransferFactoryBillToCustomsBillRequestId(data
					.getId());
			this.transferFactoryManageDao.delete(data);
		}
		this.transferFactoryManageDao
				.deleteCustomsEnvelopRequestBill(customsEnvelopRequestBill);
	}

	/**
	 * 删除关封申请单商品信息数据
	 */
	public void deleteCustomsEnvelopRequestCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			CustomsEnvelopRequestCommodityInfo data = (CustomsEnvelopRequestCommodityInfo) list
					.get(i);
			this.writeBackTransferFactoryBillToCustomsBillRequestId(data
					.getId());
			this.transferFactoryManageDao
					.deleteCustomsEnvelopRequestCommodityInfo(data);
		}
	}

	/**
	 * 当关封申请单被删除时回写结转单据--->并修改是否生成关封申请单据为false
	 */
	public void writeBackTransferFactoryBillToCustomsBillRequestId(
			String customsEnvelopRequestCommodityInfoId) {
		List dataSource = this.transferFactoryManageDao
				.findMakeCustomsEnvelopRequestByCustomsEnvelopRequestCommodityInfo(customsEnvelopRequestCommodityInfoId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			MakeCustomsEnvelopRequest m = (MakeCustomsEnvelopRequest) dataSource
					.get(i);
			//
			// 修改结转单据商品信息是否转关封申请单为false
			//
			TransferFactoryCommodityInfo t = m.getTfCommodityInfo();
			t.setIsTransferCustomsEnvelopReqeust(new Boolean(false));
			List list = new ArrayList();
			list.add(t);
			this.transferFactoryManageDao
					.saveTransferFactoryCommodityInfo(list);
			//
			// 修改结转单据是否转关封申请单为真
			//
			list.clear();
			list = this.transferFactoryManageDao
					.findTransferFactoryCommodityInfo(t
							.getTransferFactoryBill().getId());
			boolean isModify = this
					.validateTransferFactoryCommodityInfoAllDataIsFalse(list);
			if (isModify == true) {
				if (list == null || list.size() <= 0) {
					return;
				}
				TransferFactoryBill transferFactoryBill = t
						.getTransferFactoryBill();
				transferFactoryBill.setIsCustomsEnvelopRequestBill(new Boolean(
						false));
				this.transferFactoryManageDao
						.saveTransferFactoryBill(transferFactoryBill);
			}
			//
			// 删除中间表信息
			//
			this.transferFactoryManageDao.deleteMakeCustomsEnvelopRequest(m);
		}
	}

	/**
	 * 验证所有转厂单据商品信息的isTransferCustomsEnvelopRequest(转报关申请单)否都为false
	 * 
	 * @param list
	 * @return
	 */
	private boolean validateTransferFactoryCommodityInfoAllDataIsFalse(List list) {
		boolean isFlag = true;
		for (int i = 0; i < list.size(); i++) {
			TransferFactoryCommodityInfo t = (TransferFactoryCommodityInfo) list
					.get(i);
			if (t.getIsTransferCustomsEnvelopReqeust() != null) {
				if (t.getIsTransferCustomsEnvelopReqeust().booleanValue() == true) {
					isFlag = false;
					break;
				}
			}
		}
		return isFlag;
	}

	/**
	 * 结转单转报关清单时商品信息的检查--返回没有在归并关系中备案的数据(料件)
	 */
	public List checkTempTransferFactoryCommodityInfoByFinishProduct(List list,
			EmsEdiMergerHead emsEdiMergerHead) {
		List newList = new ArrayList();
		List emsList = this.emsEdiTrDao
				.findEmsEdiMergerExgBeforeByEdiMerger(emsEdiMergerHead);
		Map map = new HashMap();
		if (emsList.size() <= 0) {
			return list;
		}
		//
		// 把所有的emsList存入map 之中
		//
		for (int i = 0; i < emsList.size(); i++) {
			map.put(emsList.get(i).toString().trim(), emsList.get(i));
		}
		for (int i = 0; i < list.size(); i++) {
			// TempTransferFactoryCommodityInfo t =
			// (TempTransferFactoryCommodityInfo) list
			// .get(i);
			// //
			// // 利用hash的快速查找
			// //
			// Object obj = map.get(t.getT().getMateriel().getPtNo().trim());
			// if (obj == null) {
			// newList.add(t);
			// }
		}
		return newList;
	}

	/**
	 * 结转单转报关清单时商品信息的检查--返回没有在归并关系中备案的数据(料件)
	 */
	public List checkTempTransferFactoryCommodityInfoByMateriel(List list,
			EmsEdiMergerHead emsEdiMergerHead) {
		List newList = new ArrayList();
		List emsList = this.emsEdiTrDao
				.findEmsEdiMergerImgBeforeByEdiMerger(emsEdiMergerHead);
		Map map = new HashMap();
		if (emsList.size() <= 0) {
			return list;
		}
		//
		// 把所有的emsList存入map 之中
		//
		for (int i = 0; i < emsList.size(); i++) {
			map.put(emsList.get(i).toString().trim(), emsList.get(i));
		}
		for (int i = 0; i < list.size(); i++) {
			// TempTransferFactoryCommodityInfo t =
			// (TempTransferFactoryCommodityInfo) list
			// .get(i);
			// Object obj = map.get(t.getT().getMateriel().getPtNo().trim());
			// if (obj == null) {
			// newList.add(t);
			// }
		}
		return newList;
	}

	/**
	 * 结转单据--->报关清单返回报关清单 id isNewRecord 是代表生成新的报关单还是追加到原有的报关单 isImportGoods
	 * 是进货还是出货(出口还是进口)
	 * 
	 * @return 结转单据生成报关清单时,修改结转单据的生成关封申请单字段为true
	 */
	public List makeDzscCustomsBillList(DzscCustomsBillList customsBillList,
			List lsTransFactCommInfo) {
		List lsCustomsBillList = new ArrayList();
		List lsMakeInfo = new ArrayList();
		if (lsTransFactCommInfo.size() > 0) {
			TempTransferFactoryCommodityInfo temp = (TempTransferFactoryCommodityInfo) lsTransFactCommInfo
					.get(0);
			customsBillList.setCustomsEnvelopBillNo(temp.getT()
					.getTransferFactoryBill().getEnvelopNo());
		}
		this.transferFactoryManageDao.saveOrUpdate(customsBillList);
		Set<TransferFactoryBill> hsTransFactBill = new HashSet<TransferFactoryBill>();
		lsCustomsBillList.add(customsBillList);
		for (int i = 0; i < lsTransFactCommInfo.size(); i++) {
			TempTransferFactoryCommodityInfo temp = (TempTransferFactoryCommodityInfo) lsTransFactCommInfo
					.get(i);
			TransferFactoryCommodityInfo commInfo = temp.getT();
			DzscBillListAfterCommInfo after = new DzscBillListAfterCommInfo();
			after.setBillList(customsBillList);
			after.setEmsSerialNo(commInfo.getSeqNum());
			after.setComplex(commInfo.getComplex());
			after.setComplexName(commInfo.getCommName());
			after.setComplexSpec(commInfo.getCommSpec());
			after.setDeclaredAmount(commInfo.getTransFactAmount() == null ? 0.0
					: commInfo.getTransFactAmount());
			after.setUnit(commInfo.getUnit());
			this.transferFactoryManageDao.saveOrUpdate(after);
			List lsBeforeMaterial = this.transferFactoryManageDao
					.findDzscBeforeMaterialByAfter(commInfo.getSeqNum(),
							commInfo.getComplex().getCode());
			for (int j = 0; j < lsBeforeMaterial.size(); j++) {
				DzscInnerMergeData data = (DzscInnerMergeData) lsBeforeMaterial
						.get(j);
				Double unitConvert = (data.getUnitConvert() == null ? 1.0
						: data.getUnitConvert());
				DzscBillListBeforeCommInfo before = new DzscBillListBeforeCommInfo();
				before.setAfterComInfo(after);
				before.setMateriel(data.getMateriel());
				before.setDeclaredAmount(CommonUtils.getDoubleByDigit(
						after.getDeclaredAmount() / unitConvert, 4));
				this.transferFactoryManageDao.saveOrUpdate(before);
			}
			commInfo.setIsTransferATC(true);
			this.transferFactoryManageDao.saveOrUpdate(commInfo);
			MakeApplyToCustomsInfo makeInfo = new MakeApplyToCustomsInfo();
			makeInfo.setMergeAfterCommInfoId(after.getId());
			makeInfo.setTransFactCommInfo(commInfo.getId());
			lsMakeInfo.add(makeInfo);
			// writeBackTransferFactoryBill(commInfo.getTransferFactoryBill());
			hsTransFactBill.add(commInfo.getTransferFactoryBill());
		}
		this.transferFactoryManageDao.saveMakeApplyToCustomsInfo(lsMakeInfo);
		List<TransferFactoryBill> lsTransferFactoryBill = new ArrayList<TransferFactoryBill>();
		lsTransferFactoryBill.addAll(hsTransFactBill);
		for (int i = 0; i < lsTransferFactoryBill.size(); i++) {
			writeBackTransferFactoryBill((TransferFactoryBill) lsTransferFactoryBill
					.get(i));
		}
		return lsCustomsBillList;
	}

	/**
	 * 结转单据--->报关清单返回报关清单 id isNewRecord 是代表生成新的报关单还是追加到原有的报关单 isImportGoods
	 * 是进货还是出货(出口还是进口)
	 * 
	 * @return 结转单据生成报关清单时,修改结转单据的生成关封申请单字段为true
	 */
	public List makeApplyToCustomsBill(ApplyToCustomsBillList customsBillList,
			List lsTransFactCommInfo, EmsEdiMergerHead emsEdiMergerHead) {
		List lsCustomsBillList = new ArrayList();
		List lsMakeInfo = new ArrayList();
		if (lsTransFactCommInfo.size() > 0) {
			TempTransferFactoryCommodityInfo temp = (TempTransferFactoryCommodityInfo) lsTransFactCommInfo
					.get(0);
			customsBillList.setCustomsEnvelopBillNo(temp.getT()
					.getTransferFactoryBill().getEnvelopNo());
		}
		this.transferFactoryManageDao.saveOrUpdate(customsBillList);
		Set<TransferFactoryBill> hsTransFactBill = new HashSet<TransferFactoryBill>();
		lsCustomsBillList.add(customsBillList);
		for (int i = 0; i < lsTransFactCommInfo.size(); i++) {
			TempTransferFactoryCommodityInfo temp = (TempTransferFactoryCommodityInfo) lsTransFactCommInfo
					.get(i);
			TransferFactoryCommodityInfo commInfo = temp.getT();
			AtcMergeAfterComInfo after = new AtcMergeAfterComInfo();
			after.setBillList(customsBillList);
			after.setEmsSerialNo(commInfo.getSeqNum());
			after.setComplex(commInfo.getComplex());
			after.setComplexName(commInfo.getCommName());
			after.setComplexSpec(commInfo.getCommSpec());
			after.setDeclaredAmount(commInfo.getTransFactAmount() == null ? 0.0
					: commInfo.getTransFactAmount());
			after.setUnit(commInfo.getUnit());
			this.transferFactoryManageDao.saveOrUpdate(after);
			List lsBeforeMaterial = this.transferFactoryManageDao
					.findEmsEdiMergerBeforeByHead(emsEdiMergerHead, commInfo
							.getSeqNum(), commInfo.getComplex().getCode());
			for (int j = 0; j < lsBeforeMaterial.size(); j++) {
				// InnerMergeData data = (InnerMergeData)
				// lsBeforeMaterial.get(j);

				Double unitConvert = 1.0;// (data.getUnitConvert() == null ?
				// 1.0
				// : data.getUnitConvert());
				AtcMergeBeforeComInfo before = new AtcMergeBeforeComInfo();
				before.setAfterComInfo(after);
				String ptNo = "";
				if (lsBeforeMaterial.get(j) instanceof EmsEdiMergerImgBefore) {
					ptNo = ((EmsEdiMergerImgBefore) lsBeforeMaterial.get(j))
							.getPtNo();

				} else if (lsBeforeMaterial.get(j) instanceof EmsEdiMergerExgBefore) {
					ptNo = ((EmsEdiMergerExgBefore) lsBeforeMaterial.get(j))
							.getPtNo();
				}
				Materiel materiel = null;// commonCodeDao.findMateriel(ptNo);
				if (materiel == null) {
					throw new RuntimeException("料号" + ptNo + "在企业常用物料中不存在");
				}
				before.setMateriel(materiel);
				before.setDeclaredAmount(CommonUtils.getDoubleByDigit(
						after.getDeclaredAmount() / unitConvert, 4));
				this.transferFactoryManageDao.saveOrUpdate(before);
			}
			commInfo.setIsTransferATC(true);
			this.transferFactoryManageDao.saveOrUpdate(commInfo);
			MakeApplyToCustomsInfo makeInfo = new MakeApplyToCustomsInfo();
			makeInfo.setMergeAfterCommInfoId(after.getId());
			makeInfo.setTransFactCommInfo(commInfo.getId());
			lsMakeInfo.add(makeInfo);
			// writeBackTransferFactoryBill(commInfo.getTransferFactoryBill());
			hsTransFactBill.add(commInfo.getTransferFactoryBill());
		}
		this.transferFactoryManageDao.saveMakeApplyToCustomsInfo(lsMakeInfo);
		List<TransferFactoryBill> lsTransferFactoryBill = new ArrayList<TransferFactoryBill>();
		lsTransferFactoryBill.addAll(hsTransFactBill);
		for (int i = 0; i < lsTransferFactoryBill.size(); i++) {
			writeBackTransferFactoryBill((TransferFactoryBill) lsTransferFactoryBill
					.get(i));
		}
		return lsCustomsBillList;
	}

	/**
	 * 修改结转单据的报关清单字段,和生成报关清单据字段为true -->来自报关清单Id
	 */
	public List updateTransFactBillByCustomsBillListId(
			String customsBillListId, List dataSource) {
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			TransferFactoryBill data = ((TempTransferFactoryBill) dataSource
					.get(i)).getT();
			// data.setApplyToCustomsBillId(applyToCustomsBillId);
			data.setIsApplyToCustomsBill(new Boolean(true));
			this.transferFactoryManageDao.saveTransferFactoryBill(data);
			list.add(data);
		}
		return list;
	}

	/**
	 * 当报关清单的归并前商品信息被删除时---> 1.删除其生成的临时信息 2.修改关封中商品信息的已结转数量
	 * 3.修改转厂进货数据(isTransferATC == false)如果所有都为false那么修改结转单据是否转报关清单为false
	 */
	public void writeBackTransFactBillWhenDelAfterCommInfo(
			String afterCommInfoId) {
		List dataSource = this.transferFactoryManageDao
				.findMakeApplyToCustomsInfoByAfterCommInfoId(afterCommInfoId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			MakeApplyToCustomsInfo data = (MakeApplyToCustomsInfo) dataSource
					.get(i);
			this.transferFactoryManageDao.deleteMakeApplyToCustomsInfo(data);
			String transFactCommInfoId = data.getTransFactCommInfo();
			TransferFactoryCommodityInfo transFactCommInfo = (TransferFactoryCommodityInfo) this.transferFactoryManageDao
					.get(TransferFactoryCommodityInfo.class,
							transFactCommInfoId);
			if (transFactCommInfo != null) {
				transFactCommInfo.setIsTransferATC(new Boolean(false));
				this.transferFactoryManageDao.saveOrUpdate(transFactCommInfo);
				TransferFactoryBill transferFactoryBill = transFactCommInfo
						.getTransferFactoryBill();
				writeBackTransferFactoryBill(transferFactoryBill);
			}
		}
	}

	private void writeBackTransferFactoryBill(
			TransferFactoryBill transferFactoryBill) {
		int makeCount = this.transferFactoryManageDao
				.findTransFactCommInfoToCustomsBillList(transferFactoryBill
						.getId());
		int itemCount = (transferFactoryBill.getItemCount() == null ? 0
				: transferFactoryBill.getItemCount());
		if (makeCount != itemCount && itemCount > 0) {
			transferFactoryBill.setIsApplyToCustomsBill(false);
		} else if (makeCount == itemCount && itemCount > 0) {
			transferFactoryBill.setIsApplyToCustomsBill(true);
		}
		String customsBillListCode = "";
		List list = this.transferFactoryManageDao
				.findBcusCustomsBillListByTransFactBill(transferFactoryBill);
		if (list.size() > 0) {
			customsBillListCode = "联网监管清单号:";
			for (int i = 0; i < list.size(); i++) {
				ApplyToCustomsBillList billList = (ApplyToCustomsBillList) list
						.get(i);
				customsBillListCode += (billList.getListNo() + ";");
			}
		}
		list = this.transferFactoryManageDao
				.findDzscCustomsBillListByTransFactBill(transferFactoryBill);
		if (list.size() > 0) {
			customsBillListCode += "电子手册清单号:";
			for (int i = 0; i < list.size(); i++) {
				DzscCustomsBillList billList = (DzscCustomsBillList) list
						.get(i);
				customsBillListCode += (billList.getListNo() + ";");
			}
		}
		transferFactoryBill.setMakeCustomsBillListCode(customsBillListCode);
		transferFactoryBill.setMakeBillListItemCount(makeCount);
		this.transferFactoryManageDao
				.saveTransferFactoryBill(transferFactoryBill);
	}

	/**
	 * 根据关封单据id获得其计算后的detail
	 */
	public List findCustomsEnvelopCommodityInfoByTempTransferFactoryCommodityInfo(
			String parentId, boolean isImportGoods,
			List tempTransferFactoryCommodityInfoList) {
		Map hashMap = new HashMap();
		List customsEnvelopCommodityInfoList = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfo(parentId);
		int temp = 0;
		for (int i = 0; i < tempTransferFactoryCommodityInfoList.size(); i++) {
			TempTransferFactoryCommodityInfo t = (TempTransferFactoryCommodityInfo) tempTransferFactoryCommodityInfoList
					.get(i);
			boolean isExist = false;
			for (int j = 0; j < customsEnvelopCommodityInfoList.size(); j++) {
				CustomsEnvelopCommodityInfo c = (CustomsEnvelopCommodityInfo) customsEnvelopCommodityInfoList
						.get(j);
				String seqNum = "";
				if (isImportGoods == true) {
					seqNum = c.getSeqNum().toString();
				} else {
					seqNum = c.getSeqNum().toString();
				}
				if (t.getSeqNum().equals(seqNum)) {
					double currentTransferQuantity = 0;
					if (c.getCurrentTransferQuantity() != null) {
						currentTransferQuantity = c
								.getCurrentTransferQuantity().doubleValue();
					}
					currentTransferQuantity += t.getT().getQuantity()
							.doubleValue();
					c.setCurrentTransferQuantity(new Double(
							currentTransferQuantity));
					//
					// 把相同的关封商品信息都只存一条!!
					//
					hashMap.put(new Integer(j), c);
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				CustomsEnvelopCommodityInfo c = new CustomsEnvelopCommodityInfo();
				c.setCurrentTransferQuantity(t.getT().getQuantity());
				c.setOwnerQuantity(new Double(0));
				c.setDispensabilityQuantity(new Double(0));
				hashMap.put(new Integer(--temp), c);
			}
		}
		if (!hashMap.isEmpty()) {
			List list = new ArrayList();
			list.addAll(hashMap.values());
			return list;
		}
		return null;
	}

	/**
	 * 生成结转单据
	 */
	public void makeTransferFactoryBill(List billMasterList,
			String envelopBillCode, String emsNo) {

		List<MakeTransferFactoryBill> makeTransferFactoryBillList = new ArrayList<MakeTransferFactoryBill>();
		for (int i = 0; i < billMasterList.size(); i++) {
			BillMaster billMaster = ((TempBillMaster) billMasterList.get(i))
					.getBillMaster();
			envelopBillCode = billMaster.getEnvelopNo();
			TransferFactoryBill transferFactoryBill = this
					.makeTransferFactoryBill(billMaster);

			List<TempBillMaster> lsParent = new ArrayList<TempBillMaster>();
			lsParent.add((TempBillMaster) billMasterList.get(i));
			List lsBillDetail = this.casDao.findBillDetailByParent(lsParent);
			List tempBillDetailList = makeTransferFactoryCommodityInfo(
					lsBillDetail, transferFactoryBill, envelopBillCode, emsNo);
			// transferFactoryBill.setWareSet(((TempBillMaster)
			// billMasterList.get(i)).getWareSet());
			transferFactoryBill.setItemCount(tempBillDetailList.size());// new
			this.transferFactoryManageDao
					.saveTransferFactoryBill(transferFactoryBill);
			//
			// 再次保存结转商品对象个数的信息
			//
			this.transferFactoryManageDao
					.saveTransferFactoryCommodityInfo(tempBillDetailList);

			MakeTransferFactoryBill makeTransferFactoryBill = new MakeTransferFactoryBill();
			makeTransferFactoryBill.setBillMasterId(billMaster.getId());
			makeTransferFactoryBill.setTransFactBillId(transferFactoryBill
					.getId());
			makeTransferFactoryBillList.add(makeTransferFactoryBill);
		}
		//
		// 保存所有中间表信息
		//
		this.transferFactoryManageDao
				.saveMakeTransferFactoryBill(makeTransferFactoryBillList);

	}

	/**
	 * 生成转关对象
	 */
	private TransferFactoryBill makeTransferFactoryBill(BillMaster billMaster) {
		TransferFactoryBill t = new TransferFactoryBill();
		t.setAclUser(CommonUtils.getRequest().getUser());
		t.setCompany(CommonUtils.getCompany());
		t.setScmCoc(billMaster.getScmCoc());
		t.setEnvelopNo(billMaster.getEnvelopNo());
		t.setBeginAvailability(billMaster.getValidDate());
		t.setIsAvailability(new Boolean(false));
		t.setIsApplyToCustomsBill(new Boolean(false));
		t.setIsCustomsEnvelopRequestBill(new Boolean(false));
		t.setTransferFactoryBillNo(billMaster.getBillNo());
		if (billMaster.getBillType().getBillType().intValue() == SBillType.MATERIEL_IN) {
			// t.setTransferFactoryBillNo(String.valueOf(this
			// .getMaxTransferFactoryBillNoByImpExpGoodsFlag(true)));
			t.setIsImpExpGoods(new Boolean(true));
		} else if (billMaster.getBillType().getBillType().intValue() == SBillType.PRODUCE_OUT) {
			// t.setTransferFactoryBillNo(String.valueOf(this
			// .getMaxTransferFactoryBillNoByImpExpGoodsFlag(false)));
			t.setIsImpExpGoods(new Boolean(false));
		}
		return t;
	}

	/**
	 * 生成转关商品信息对象
	 */
	private List makeTransferFactoryCommodityInfo(List lsBillDetail,
			TransferFactoryBill transferFactoryBill, String envelopBillCode,
			String emsNo) {
		List lsResult = new ArrayList();
		Map<Integer, TransferFactoryCommodityInfo> hmTFCommInfo = new HashMap<Integer, TransferFactoryCommodityInfo>();
		List lsCustomsEnvelopBill = this.transferFactoryManageDao
				.findCustomsEnvelopBill(envelopBillCode);
		if (lsCustomsEnvelopBill.size() <= 0) {
			throw new RuntimeException("没有关封号为" + envelopBillCode + "的关封");
		}
		CustomsEnvelopBill customsEnvelopBill = (CustomsEnvelopBill) lsCustomsEnvelopBill
				.get(0);
		// transferFactoryBill.setEnvelopNo(customsEnvelopBill
		// .getCustomsEnvelopBillNo());
		// transferFactoryBill.setEnvelopId(customsEnvelopBill.getId());
		List lsEnvelopBillCommInfo = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfo(customsEnvelopBill.getId(),
						emsNo);
		Map<Integer, CustomsEnvelopCommodityInfo> hmEnvelopBillCommInfo = new HashMap<Integer, CustomsEnvelopCommodityInfo>();
		for (int i = 0; i < lsEnvelopBillCommInfo.size(); i++) {
			CustomsEnvelopCommodityInfo enveCommInfo = (CustomsEnvelopCommodityInfo) lsEnvelopBillCommInfo
					.get(i);
			hmEnvelopBillCommInfo.put(enveCommInfo.getSeqNum(), enveCommInfo);
		}
		/*
		 * Set<String> hsEnvelopBillCommInfo = new HashSet<String>(); for (int i
		 * = 0; i < lsEnvelopBillCommInfo.size(); i++) {
		 * CustomsEnvelopCommodityInfo enveCommInfo =
		 * (CustomsEnvelopCommodityInfo) lsEnvelopBillCommInfo .get(i);
		 * hsEnvelopBillCommInfo.add(enveCommInfo.getSeqNum() +
		 * enveCommInfo.getComplex().getCode()); }
		 */
		int projectType = customsEnvelopBill.getProjectType();
		if (projectType == ProjectType.BCUS) {
			hmTFCommInfo = this.getTransFactCommInfoByBcus(lsBillDetail,
					transferFactoryBill, hmEnvelopBillCommInfo, emsNo);
		} else if (projectType == ProjectType.BCS) {
			hmTFCommInfo = this.getTransFactCommInfoByBcs(lsBillDetail,
					transferFactoryBill, hmEnvelopBillCommInfo,
					customsEnvelopBill, emsNo);
		} else if (projectType == ProjectType.DZSC) {
			hmTFCommInfo = this.getTransFactCommInfoByDzsc(lsBillDetail,
					transferFactoryBill, hmEnvelopBillCommInfo, emsNo);
		}
		lsResult.addAll(hmTFCommInfo.values());
		return lsResult;
	}

	private Map<Integer, TransferFactoryCommodityInfo> getTransFactCommInfoByBcus(
			List lsBillDetail, TransferFactoryBill transferFactoryBill,
			Map<Integer, CustomsEnvelopCommodityInfo> hmEnvelopBillCommInfo,
			String emsNo) {
		String emsFrom = "2";
		List list = this.transferFactoryManageDao
				.findNameValues(ParameterType.emsFrom);
		if (list.size() > 0 && list.get(0) != null) {
			emsFrom = ((ParameterSet) list.get(0)).getNameValues();
		}
		Map<Integer, TransferFactoryCommodityInfo> hmTFCommInfo = new HashMap<Integer, TransferFactoryCommodityInfo>();
		if ("1".equals(emsFrom) || "2".equals(emsFrom)) {
			EmsEdiMergerHead emsEdiMergerHead = null;
			if ("2".equals(emsFrom)) {
				emsEdiMergerHead = (EmsEdiMergerHead) this.emsEdiTrDao
						.findEmsEdiMergerHeadByDeclareState(DeclareState.APPLY_POR);
				if (emsEdiMergerHead == null) {
					throw new RuntimeException("系统没有正在申请备案的(联网监管)归并关系备案");
				}
			} else {
				emsEdiMergerHead = (EmsEdiMergerHead) this.emsEdiTrDao
						.findEmsEdiMergerHeadByDeclareState(DeclareState.PROCESS_EXE);
				if (emsEdiMergerHead == null) {
					throw new RuntimeException("系统没有正在执行的(联网监管)归并关系备案");
				}
			}
			for (int i = 0; i < lsBillDetail.size(); i++) {
				BillDetail billDetail = (BillDetail) lsBillDetail.get(i);
				Object emsEdiMergerAfter = this.transferFactoryManageDao
						.findEmsEdiMergerAfterByBefore(emsEdiMergerHead,
								billDetail.getPtPart());
				if (emsEdiMergerAfter == null) {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "在(联网监管)归并关系备案中不存在");
				}
				Integer seqNum = null;
				double unitConvert = 1.0;
				// String commName = "";
				// String commSpec = "";
				if (emsEdiMergerAfter instanceof EmsEdiMergerImgAfter) {
					EmsEdiMergerImgAfter imgAfter = (EmsEdiMergerImgAfter) emsEdiMergerAfter;
					seqNum = imgAfter.getSeqNum();
					// commName = imgAfter.getName();
					// commSpec = imgAfter.getSpec();
				} else {
					EmsEdiMergerExgAfter exgAfter = (EmsEdiMergerExgAfter) emsEdiMergerAfter;
					seqNum = exgAfter.getSeqNum();
					// commName = exgAfter.getName();
					// commSpec = exgAfter.getSpec();
				}
				CustomsEnvelopCommodityInfo envelopCommInfo = hmEnvelopBillCommInfo
						.get(seqNum);
				if (envelopCommInfo == null) {
					throw new RuntimeException("电子帐册" + emsNo + "中，备案序号为"
							+ seqNum + "的商品在关封"
							+ transferFactoryBill.getEnvelopNo() + "中不存在");
				}
				makeTransFactCommInfo(transferFactoryBill, hmTFCommInfo,
						billDetail, seqNum, unitConvert, envelopCommInfo);
			}
		} else {
			for (int i = 0; i < lsBillDetail.size(); i++) {
				BillDetail billDetail = (BillDetail) lsBillDetail.get(i);
				InnerMergeData innerMergeData = this.transferFactoryManageDao
						.findInnerMergerByPtNo(billDetail.getPtPart());
				if (innerMergeData == null) {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "在电子帐册企业内部归并中不存在");
				}
				Integer seqNum = innerMergeData.getHsAfterTenMemoNo();
				double unitConvert = 1.0;
				CustomsEnvelopCommodityInfo envelopCommInfo = hmEnvelopBillCommInfo
						.get(seqNum);
				if (envelopCommInfo == null) {
					throw new RuntimeException("电子帐册" + emsNo + "中，备案序号为"
							+ seqNum + "的商品在关封"
							+ transferFactoryBill.getEnvelopNo() + "中不存在");
				}
				makeTransFactCommInfo(transferFactoryBill, hmTFCommInfo,
						billDetail, seqNum, unitConvert, envelopCommInfo);
			}
		}
		return hmTFCommInfo;
	}

	private void makeTransFactCommInfo(TransferFactoryBill transferFactoryBill,
			Map<Integer, TransferFactoryCommodityInfo> hmTFCommInfo,
			BillDetail billDetail, Integer seqNum, double unitConvert,
			CustomsEnvelopCommodityInfo envelopCommInfo) {
		if (hmTFCommInfo.get(seqNum) == null) {
			TransferFactoryCommodityInfo transferFactoryCommodityInfo = new TransferFactoryCommodityInfo();
			// transferFactoryCommodityInfo.setCurr(billDetail.getCurr());
			transferFactoryCommodityInfo.setComplex(envelopCommInfo
					.getComplex());
			// transferFactoryCommodityInfo.setQuantity(CommonUtils
			// .getDoubleExceptNull(billDetail.getPtAmount())
			// * unitConvert);
			// transferFactoryCommodityInfo
			// .setTransFactAmount(transferFactoryCommodityInfo
			// .getQuantity());
			transferFactoryCommodityInfo.setQuantity(envelopCommInfo
					.getOwnerQuantity());
			// 为什么要从单据转过来的料资还要去归并关系中的折算，应该取单据中心的对应关系中的折算
			transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
					.getDoubleExceptNull(billDetail.getHsAmount()));
			// transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
			// .getDoubleExceptNull(billDetail.getPtAmount())
			// * unitConvert);
			transferFactoryCommodityInfo.setTotalPrice(CommonUtils
					.getDoubleExceptNull(billDetail.getMoney()));
			// transferFactoryCommodityInfo.setUnitPrice(envelopCommInfo
			// .getUnitPrice());
			transferFactoryCommodityInfo
					.setSourceBill(billDetail.getCustomNo());
			transferFactoryCommodityInfo.setNetWeight(billDetail.getNetWeight()
					* unitConvert);

			transferFactoryCommodityInfo
					.setUnitPrice(transferFactoryCommodityInfo
							.getTransFactAmount() == 0.0 ? 0.0 : CommonUtils
							.getDoubleByDigit(
									transferFactoryCommodityInfo
											.getTotalPrice()
											/ transferFactoryCommodityInfo
													.getTransFactAmount(), 5));
			// ptPart
			transferFactoryCommodityInfo.setIsTransferATC(new Boolean(false));
			transferFactoryCommodityInfo.setCommName(envelopCommInfo
					.getPtName());
			transferFactoryCommodityInfo.setCommSpec(envelopCommInfo
					.getPtSpec());
			// transferFactoryCommodityInfo.setMateriel(this
			// .getMaterielByPtNo(billDetail.getPtPart()));
			transferFactoryCommodityInfo
					.setTransferFactoryBill(transferFactoryBill);
			transferFactoryCommodityInfo.setEmsNo(envelopCommInfo.getEmsNo());
			transferFactoryCommodityInfo.setSeqNum(envelopCommInfo.getSeqNum());
			transferFactoryCommodityInfo.setUnit(envelopCommInfo.getUnit());

			hmTFCommInfo.put(seqNum, transferFactoryCommodityInfo);

		} else {
			TransferFactoryCommodityInfo transferFactoryCommodityInfo = (TransferFactoryCommodityInfo) hmTFCommInfo
					.get(seqNum);
			// transferFactoryCommodityInfo.setQuantity(CommonUtils
			// .getDoubleExceptNull(transferFactoryCommodityInfo
			// .getQuantity())
			// + CommonUtils.getDoubleExceptNull(billDetail.getPtAmount())
			// * unitConvert);
			// transferFactoryCommodityInfo
			// .setTransFactAmount(transferFactoryCommodityInfo
			// .getQuantity());
			// 为什么要从单据转过来的料资还要去归并关系中的折算，应该取单据中心的对应关系中的折算
			transferFactoryCommodityInfo
					.setTransFactAmount(CommonUtils
							.getDoubleExceptNull(transferFactoryCommodityInfo
									.getTransFactAmount())
							+ CommonUtils.getDoubleExceptNull(billDetail
									.getHsAmount()));
			// transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
			// .getDoubleExceptNull(transferFactoryCommodityInfo
			// .getTransFactAmount())
			// + CommonUtils.getDoubleExceptNull(billDetail.getPtAmount())
			// * unitConvert);
			transferFactoryCommodityInfo.setTotalPrice(CommonUtils
					.getDoubleExceptNull(transferFactoryCommodityInfo
							.getTotalPrice())
					+ CommonUtils.getDoubleExceptNull(billDetail.getMoney()));
			transferFactoryCommodityInfo
					.setUnitPrice(transferFactoryCommodityInfo
							.getTransFactAmount() == 0.0 ? 0.0 : CommonUtils
							.getDoubleByDigit(
									transferFactoryCommodityInfo
											.getTotalPrice()
											/ transferFactoryCommodityInfo
													.getTransFactAmount(), 5));
			hmTFCommInfo.put(seqNum, transferFactoryCommodityInfo);
		}
	}

	private Map<Integer, TransferFactoryCommodityInfo> getTransFactCommInfoByDzsc(
			List lsBillDetail, TransferFactoryBill transferFactoryBill,
			Map<Integer, CustomsEnvelopCommodityInfo> hmEnvelopBillCommInfo,
			String emsNo) {
		Map<Integer, TransferFactoryCommodityInfo> hmTFCommInfo = new HashMap<Integer, TransferFactoryCommodityInfo>();
		for (int i = 0; i < lsBillDetail.size(); i++) {
			BillDetail billDetail = (BillDetail) lsBillDetail.get(i);

			DzscInnerMergeData innerMergeData = this.transferFactoryManageDao
					.findDzscMergeAfterByBefore(billDetail.getPtPart());
			if (innerMergeData == null) {
				throw new RuntimeException("物料" + billDetail.getPtPart()
						+ "在(电子手册)归并关系备案中不存在");
			}
			double unitConvert = (innerMergeData.getUnitConvert() == null ? 1.0
					: innerMergeData.getUnitConvert());
			List list = this.transferFactoryManageDao
					.findDzscImgExgByInnerMergeSeqNum(innerMergeData
							.getDzscTenInnerMerge().getTenSeqNum(), emsNo,
							billDetail.getBillMaster().getBillType()
									.getProduceType());
			if (list == null || list.size() <= 0) {
				throw new RuntimeException("物料" + billDetail.getPtPart()
						+ "相对的归并后序号在(电子手册)" + emsNo + "中不存在");
			}
			Integer seqNum = null;
			if (billDetail.getBillMaster().getBillType().getProduceType() == 2) {
				DzscEmsImgBill imgBill = (DzscEmsImgBill) list.get(0);
				seqNum = imgBill.getSeqNum();
			} else if (billDetail.getBillMaster().getBillType()
					.getProduceType() == 0) {
				DzscEmsExgBill exgBill = (DzscEmsExgBill) list.get(0);
				seqNum = exgBill.getSeqNum();
			}
			CustomsEnvelopCommodityInfo envelopCommInfo = hmEnvelopBillCommInfo
					.get(seqNum);
			if (envelopCommInfo == null) {
				throw new RuntimeException("电子手册" + emsNo + "中，备案序号为" + seqNum
						+ "的商品在关封" + transferFactoryBill.getEnvelopNo()
						+ "中不存在");
			}
			makeTransFactCommInfo(transferFactoryBill, hmTFCommInfo,
					billDetail, seqNum, unitConvert, envelopCommInfo);
			// if (hmTFCommInfo.get(seqNumCode) == null) {
			// TransferFactoryCommodityInfo transferFactoryCommodityInfo = new
			// TransferFactoryCommodityInfo();
			// // transferFactoryCommodityInfo.setCurr(billDetail.getCurr());
			// transferFactoryCommodityInfo.setComplex(envelopCommInfo
			// .getComplex());
			// // transferFactoryCommodityInfo.setQuantity(CommonUtils
			// // .getDoubleExceptNull(billDetail.getPtAmount())
			// // * unitConvert);
			// // transferFactoryCommodityInfo
			// // .setTransFactAmount(transferFactoryCommodityInfo
			// // .getQuantity());
			// transferFactoryCommodityInfo.setQuantity(envelopCommInfo
			// .getOwnerQuantity());
			// transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
			// .getDoubleExceptNull(billDetail.getPtAmount())
			// * unitConvert);
			// transferFactoryCommodityInfo.setUnitPrice(envelopCommInfo
			// .getUnitPrice());
			// // transferFactoryCommodityInfo.setSourceBill(billDetail
			// // .getCustomNo());
			// transferFactoryCommodityInfo.setNetWeight(billDetail
			// .getNetWeight()
			// * unitConvert);
			// transferFactoryCommodityInfo.setCommName(envelopCommInfo
			// .getPtName());
			// transferFactoryCommodityInfo.setCommSpec(envelopCommInfo
			// .getPtSpec());
			// // ptPart
			// transferFactoryCommodityInfo
			// .setIsTransferATC(new Boolean(false));
			// // transferFactoryCommodityInfo.setMateriel(this
			// // .getMaterielByPtNo(billDetail.getPtPart()));
			// transferFactoryCommodityInfo
			// .setTransferFactoryBill(transferFactoryBill);
			// transferFactoryCommodityInfo.setSeqNum(envelopCommInfo
			// .getSeqNum());
			// transferFactoryCommodityInfo.setUnit(envelopCommInfo.getUnit());
			// hmTFCommInfo.put(seqNumCode, transferFactoryCommodityInfo);
			//
			// } else {
			// TransferFactoryCommodityInfo transferFactoryCommodityInfo =
			// (TransferFactoryCommodityInfo) hmTFCommInfo
			// .get(seqNumCode);
			// // transferFactoryCommodityInfo.setQuantity(CommonUtils
			// // .getDoubleExceptNull(transferFactoryCommodityInfo
			// // .getQuantity())
			// // + CommonUtils.getDoubleExceptNull(billDetail
			// // .getPtAmount()) * unitConvert);
			// // transferFactoryCommodityInfo
			// // .setTransFactAmount(transferFactoryCommodityInfo
			// // .getQuantity());
			// transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
			// .getDoubleExceptNull(transferFactoryCommodityInfo
			// .getTransFactAmount())
			// + CommonUtils.getDoubleExceptNull(billDetail
			// .getPtAmount()) * unitConvert);
			// hmTFCommInfo.put(seqNumCode, transferFactoryCommodityInfo);
			// }
		}
		return hmTFCommInfo;
	}

	private Map<Integer, TransferFactoryCommodityInfo> getTransFactCommInfoByBcs(
			List lsBillDetail, TransferFactoryBill transferFactoryBill,
			Map<Integer, CustomsEnvelopCommodityInfo> hmEnvelopBillCommInfo,
			CustomsEnvelopBill customsEnvelopBill, String emsNo) {
		Map<Integer, TransferFactoryCommodityInfo> hmTFCommInfo = new HashMap<Integer, TransferFactoryCommodityInfo>();
		for (int i = 0; i < lsBillDetail.size(); i++) {
			BillDetail billDetail = (BillDetail) lsBillDetail.get(i);
			BcsInnerMerge innerMergeData = this.transferFactoryManageDao
					.findBcsMergeAfterByBefore(billDetail.getPtPart().trim());
			if (innerMergeData == null) {
				throw new RuntimeException("物料" + billDetail.getPtPart()
						+ "在(电子化手册)归并关系中不存在");
			}
			double unitConvert = (innerMergeData.getUnitConvert() == null ? 1.0
					: innerMergeData.getUnitConvert());
			Contract contract = this.contractDao
					.findExingContractByEmsNo(emsNo);
			if (contract == null) {
				throw new RuntimeException("没有找到正在执行的手册号：" + emsNo);
			}
			boolean isContractEms = (contract.getIsContractEms() == null ? false
					: contract.getIsContractEms());
			List list = transferFactoryManageDao
					.findContractImgByInnerMergeSeqNum(innerMergeData
							.getBcsTenInnerMerge().getSeqNum(), emsNo,
							billDetail.getBillMaster().getBillType()
									.getProduceType(), isContractEms);
			if (list == null || list.size() <= 0) {
				if (isContractEms) {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "相对的归并后序号在(备案资料库，电子化手册)" + emsNo + "中不存在");
				} else {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "相对的归并后序号在(电子化手册)" + emsNo + "中不存在");
				}
			}
			Integer seqNum = null;
			if (billDetail.getBillMaster().getBillType().getProduceType() == 2) {
				ContractImg contractImg = (ContractImg) list.get(0);
				seqNum = contractImg.getSeqNum();
			} else if (billDetail.getBillMaster().getBillType()
					.getProduceType() == 0) {
				ContractExg contractExg = (ContractExg) list.get(0);
				seqNum = contractExg.getSeqNum();
			}
			CustomsEnvelopCommodityInfo envelopCommInfo = hmEnvelopBillCommInfo
					.get(seqNum);
			if (envelopCommInfo == null) {
				throw new RuntimeException("电子化手册" + emsNo + "中，备案序号为" + seqNum
						+ "的商品在关封" + transferFactoryBill.getEnvelopNo()
						+ "中不存在");
			}
			makeTransFactCommInfo(transferFactoryBill, hmTFCommInfo,
					billDetail, seqNum, unitConvert, envelopCommInfo);
			// if (hmTFCommInfo.get(seqNum) == null) {
			// TransferFactoryCommodityInfo transferFactoryCommodityInfo = new
			// TransferFactoryCommodityInfo();
			// // transferFactoryCommodityInfo.setCurr(billDetail.getCurr());
			// transferFactoryCommodityInfo.setComplex(envelopCommInfo
			// .getComplex());
			// // transferFactoryCommodityInfo.setQuantity(CommonUtils
			// // .getDoubleExceptNull(billDetail.getPtAmount())
			// // * unitConvert);
			// // transferFactoryCommodityInfo
			// // .setTransFactAmount(transferFactoryCommodityInfo
			// // .getQuantity());
			// transferFactoryCommodityInfo.setQuantity(envelopCommInfo
			// .getOwnerQuantity());
			// transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
			// .getDoubleExceptNull(billDetail.getPtAmount())
			// * unitConvert);
			// // transferFactoryCommodityInfo.setUnitPrice(envelopCommInfo
			// // .getUnitPrice());
			// // transferFactoryCommodityInfo.setUnitPrice(billDetail
			// // .getPtPrice()
			// // * unitConvert);
			// // transferFactoryCommodityInfo.setSourceBill(billDetail
			// // .getCustomNo());
			// transferFactoryCommodityInfo.setNetWeight(billDetail
			// .getNetWeight()
			// * unitConvert);
			// transferFactoryCommodityInfo.setCommName(envelopCommInfo
			// .getPtName());
			// transferFactoryCommodityInfo.setCommSpec(envelopCommInfo
			// .getPtSpec());
			// // ptPart
			// transferFactoryCommodityInfo
			// .setIsTransferATC(new Boolean(false));
			// // transferFactoryCommodityInfo.setMateriel(this
			// // .getMaterielByPtNo(billDetail.getPtPart()));
			// transferFactoryCommodityInfo
			// .setTransferFactoryBill(transferFactoryBill);
			// transferFactoryCommodityInfo.setSeqNum(envelopCommInfo
			// .getSeqNum());
			// transferFactoryCommodityInfo.setUnit(envelopCommInfo.getUnit());
			//
			// hmTFCommInfo.put(seqNum, transferFactoryCommodityInfo);
			//
			// } else {
			// TransferFactoryCommodityInfo transferFactoryCommodityInfo =
			// (TransferFactoryCommodityInfo) hmTFCommInfo
			// .get(seqNum);
			// // transferFactoryCommodityInfo.setQuantity(CommonUtils
			// // .getDoubleExceptNull(transferFactoryCommodityInfo
			// // .getQuantity())
			// // + CommonUtils.getDoubleExceptNull(billDetail
			// // .getPtAmount()) * unitConvert);
			// // transferFactoryCommodityInfo
			// // .setTransFactAmount(transferFactoryCommodityInfo
			// // .getQuantity());
			// transferFactoryCommodityInfo.setTransFactAmount(CommonUtils
			// .getDoubleExceptNull(transferFactoryCommodityInfo
			// .getTransFactAmount())
			// + CommonUtils.getDoubleExceptNull(billDetail
			// .getPtAmount()) * unitConvert);
			// hmTFCommInfo.put(seqNum, transferFactoryCommodityInfo);
			// }
		}
		return hmTFCommInfo;
	}

	// /**
	// * 生成转关商品信息对象
	// */
	// private TransferFactoryCommodityInfo makeTransferFactoryCommodityInfo(
	// TransferFactoryBill t, BillDetail billDetail) {
	// TransferFactoryCommodityInfo transferFactoryCommodityInfo = new
	// TransferFactoryCommodityInfo();
	// // transferFactoryCommodityInfo.setCurr(billDetail.getCurr());
	// transferFactoryCommodityInfo.setQuantity(billDetail.getPtAmount());
	// transferFactoryCommodityInfo.setUnitPrice(billDetail.getPtPrice());
	// transferFactoryCommodityInfo.setSourceBill(billDetail.getCustomNo());
	// transferFactoryCommodityInfo.setNetWeight(billDetail.getNetWeight());
	// // ptPart
	// transferFactoryCommodityInfo.setIsTransferATC(new Boolean(false));
	// // transferFactoryCommodityInfo.setMateriel(this
	// // .getMaterielByPtNo(billDetail.getPtPart()));
	// transferFactoryCommodityInfo.setTransferFactoryBill(t);
	// List list = new ArrayList();
	// list.add(transferFactoryCommodityInfo);
	// this.transferFactoryManageDao.saveTransferFactoryCommodityInfo(list);
	// return transferFactoryCommodityInfo;
	// }

	/**
	 * 通过ptNo来获得Materiel对象
	 */
	private Materiel getMaterielByPtNo(String ptNo) {
		if (map == null) {
			map = new HashMap();
			List list = this.commonCodeDao.findMateriel();
			for (int i = 0; i < list.size(); i++) {
				Materiel m = (Materiel) list.get(i);
				map.put(m.getPtNo(), m);
			}
		}
		return (Materiel) map.get(ptNo);
	}

	/**
	 * 删除结转单据
	 */
	public void deleteTransferFactoryBill(
			TransferFactoryBill transferFactoryBill) {
		List list = this.transferFactoryManageDao
				.findTransferFactoryCommodityInfo(transferFactoryBill.getId());
		for (int i = 0; i < list.size(); i++) {
			TransferFactoryCommodityInfo data = (TransferFactoryCommodityInfo) list
					.get(i);
			this.transferFactoryManageDao
					.deleteTransferFactoryCommodityInfo(data);
		}
		//
		// 回写海关工厂商品信息设置其转为
		//
		this.writeBackBillDetailToCasFactoryInfoId(transferFactoryBill.getId());
		this.transferFactoryManageDao
				.deleteTransferFactoryBill(transferFactoryBill);
	}

	/**
	 * 删除结转单据商品信息数据
	 */
	public void deleteTransferFactoryCommodityInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			TransferFactoryCommodityInfo data = (TransferFactoryCommodityInfo) list
					.get(i);
			// //
			// // 回写海关工厂商品信息设置其转为
			// //
			// this.writeBackBillDetailToCasFactoryCommodityInfoId(data.getId());
			this.transferFactoryManageDao
					.deleteTransferFactoryCommodityInfo(data);
		}
	}

	/**
	 * 回写海关工厂信息设置其转为
	 */
	public void writeBackBillDetailToCasFactoryInfoId(
			String transferFactoryInfoId) {
		List dataSource = this.transferFactoryManageDao
				.findMakeTransferFactoryBillByTransferFactoryInfoId(transferFactoryInfoId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			MakeTransferFactoryBill m = (MakeTransferFactoryBill) dataSource
					.get(i);
			// //
			// // 修改海关商品资料已转转厂单据为false
			// //
			// String casBillMasterId = m.getBillMasterId();
			// BillDetail billDetail = null;
			// // --------------------------------
			// // 这里是转厂有问题(暂时没时间修改)
			// // --------------------------------
			// // BillDetail billDetail = this.casDao
			// // .findBillDetailById(casFactoryCommodityInfoId);
			// if (billDetail != null) {
			// billDetail.setIsTransferFactoryBill(new Boolean(false));
			// this.casDao.saveBillDetail(billDetail);
			// }
			//
			// 删除中间表信息
			//
			this.transferFactoryManageDao.deleteMakeTransferFactoryBill(m);
		}
	}

	// /**
	// * 回写海关工厂商品信息设置其转为
	// */
	// public void writeBackBillDetailToCasFactoryCommodityInfoId(
	// String transferFactoryCommodityInfoId) {
	// List dataSource = this.transferFactoryManageDao
	// .findMakeTransferFactoryBillByTransferFactoryInfoId(transferFactoryCommodityInfoId);
	// if (dataSource == null || dataSource.size() <= 0) {
	// return;
	// }
	// for (int i = 0; i < dataSource.size(); i++) {
	// MakeTransferFactoryBill m = (MakeTransferFactoryBill) dataSource
	// .get(i);
	// //
	// // 修改海关商品资料已转转厂单据为false
	// //
	// String casFactoryCommodityInfoId = m.getBillMasterId();
	// BillDetail billDetail = null;
	// // --------------------------------
	// // 这里是转厂有问题(暂时没时间修改)
	// // --------------------------------
	// // BillDetail billDetail = this.casDao
	// // .findBillDetailById(casFactoryCommodityInfoId);
	// if (billDetail != null) {
	// billDetail.setIsTransferFactoryBill(new Boolean(false));
	// this.casDao.saveBillDetail(billDetail);
	// }
	// //
	// // 删除中间表信息
	// //
	// this.transferFactoryManageDao.deleteMakeTransferFactoryBill(m);
	// }
	// }

	/**
	 * 获取查询list
	 * 
	 * @param commodityCode
	 *            商品编码
	 * @param commodityName
	 *            商品名称
	 * @param seqNum
	 *            帐册序号
	 * @param oppoisteEnterpriseName
	 *            对方企事业名称
	 * @param isImportGoods
	 *            转入/转出
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return
	 */
	public List findCustomsEnvelopSpareAnalyes(Date beginDate, Date endDate,
			String commodityCode, String commodityName, String seqNum,
			String oppoisteEnterpriseName, boolean isImportGoods,
			String customsEnvelopBillNo, boolean isshow) {
		List oldList = this.getCustomsEnvelopSpareAnalyes(beginDate, endDate,
				commodityCode, commodityName, seqNum, oppoisteEnterpriseName,
				isImportGoods, customsEnvelopBillNo, isshow);
		List newList = new ArrayList();
		for (int i = 0; i < oldList.size(); i++) {
			CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo = (CustomsEnvelopCommodityInfo) oldList
					.get(i);
			CustomsEnvelopBill customsEnvelopBill = customsEnvelopCommodityInfo
					.getCustomsEnvelopBill();
			CustomsEnvelopSpareAnalyes customsEnvelopSpareAnalyes = new CustomsEnvelopSpareAnalyes();
			customsEnvelopSpareAnalyes.setIsImpExpGoods(customsEnvelopBill
					.getIsImpExpGoods());
			// 正在执行的标志（==!已经案）
			customsEnvelopSpareAnalyes.setBeginAvailability(customsEnvelopBill
					.getBeginAvailability());
			customsEnvelopSpareAnalyes.setEff(!customsEnvelopBill
					.getIsEndCase());
			customsEnvelopSpareAnalyes.setEndDate(CommonUtils.getDate(
					customsEnvelopBill.getEndAvailability(), "yyyy-MM-dd"));
			customsEnvelopSpareAnalyes.setIsEndCase(customsEnvelopBill
					.getIsEndCase());
			customsEnvelopSpareAnalyes
					.setCustomsEnvelopBillNo(customsEnvelopBill
							.getCustomsEnvelopBillNo());
			customsEnvelopSpareAnalyes.setComplex(customsEnvelopCommodityInfo
					.getComplex());
			customsEnvelopSpareAnalyes.setCeseqNum(customsEnvelopCommodityInfo
					.getCeseqNum());
			customsEnvelopSpareAnalyes
					.setScmCoc(customsEnvelopBill.getScmCoc());
			customsEnvelopSpareAnalyes.setSpec(customsEnvelopCommodityInfo
					.getPtSpec());
			customsEnvelopSpareAnalyes.setSeqNum(String
					.valueOf(customsEnvelopCommodityInfo.getSeqNum()));
			customsEnvelopSpareAnalyes.setName(customsEnvelopCommodityInfo
					.getPtName());
			customsEnvelopSpareAnalyes
					.setOwnerQuantity(customsEnvelopCommodityInfo
							.getOwnerQuantity());// 关封数量
			customsEnvelopSpareAnalyes.setEmsNo(customsEnvelopCommodityInfo
					.getEmsNo());// 手册号或帐册号

			List lsit = transferFactoryManageDao
					.getContractOrEmsHeakOrDzscEmsPorHead(
							customsEnvelopBill.getProjectType(),
							customsEnvelopCommodityInfo.getEmsNo());
			if (lsit != null && lsit.size() > 0) {
				BaseContractHead contract = (BaseContractHead) lsit.get(0);
				customsEnvelopSpareAnalyes.setBeginEmsAvailability(contract
						.getEndDate());// 手册有效期
			} else {
				customsEnvelopSpareAnalyes.setBeginEmsAvailability(null);// 手册有效期
			}

			// 送货数量
			Double impExpGoodsQuantity = transferFactoryManageDao
					.findTotalImpExpGoodsQuantity(
							(isImportGoods ? Integer
									.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT)
									: Integer.valueOf(ImpExpType.DIRECT_EXPORT)),
							customsEnvelopCommodityInfo.getCustomsEnvelopBill()
									.getCustomsEnvelopBillNo(),
							customsEnvelopCommodityInfo.getEmsNo(),
							customsEnvelopCommodityInfo.getSeqNum(),
							(customsEnvelopCommodityInfo
									.getCustomsEnvelopBill().getScmCoc() == null ? null
									: customsEnvelopCommodityInfo
											.getCustomsEnvelopBill()
											.getScmCoc().getName())); // 送货数量
			customsEnvelopSpareAnalyes
					.setTransferFatoryCommodityInfoNum(impExpGoodsQuantity);
			customsEnvelopSpareAnalyes
					.setCustomsRemainNum((customsEnvelopCommodityInfo
							.getOwnerQuantity() == null ? Double.valueOf(0)
							: customsEnvelopCommodityInfo.getOwnerQuantity())
							- impExpGoodsQuantity);
			customsEnvelopSpareAnalyes
					.setTransferQuantity(getCustomsEnvelopTransferQuantity(
							customsEnvelopBill.getIsImpExpGoods(),
							customsEnvelopCommodityInfo.getSeqNum(),
							customsEnvelopCommodityInfo.getComplex(),
							customsEnvelopBill.getCustomsEnvelopBillNo()));// 已转数量
			customsEnvelopSpareAnalyes.setDispensabilityQuantity(CommonUtils
					.getDoubleExceptNull(customsEnvelopSpareAnalyes
							.getOwnerQuantity())
					- CommonUtils
							.getDoubleExceptNull(customsEnvelopSpareAnalyes
									.getTransferQuantity()));// 可分配数量
			customsEnvelopSpareAnalyes.setFactoryNo(customsEnvelopCommodityInfo
					.getOppositeEmsNo());
			// 修改 BY 石小凯 在报表备案单位
			customsEnvelopSpareAnalyes.setUnit(customsEnvelopCommodityInfo
					.getUnit());
			newList.add(customsEnvelopSpareAnalyes);
		}
		return newList;
	}

	/**
	 * 获取查询list
	 * 
	 * @param commodityCode
	 *            商品编码
	 * @param commodityName
	 *            商品名称
	 * @param seqNum
	 *            帐册序号
	 * @param oppoisteEnterpriseName
	 *            对方企事业名称
	 * @param isImportGoods
	 *            转入/转出
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return
	 */
	private List getCustomsEnvelopSpareAnalyes(Date beginDate, Date endDate,
			String commodityCode, String commodityName, String seqNum,
			String oppoisteEnterpriseName, boolean isImportGoods,
			String customsEnvelopBillNo, boolean isshow) {
		String hsql = this.getCustomsEnvelopSpareAnalyesHsql(beginDate,
				endDate, commodityCode, commodityName, seqNum,
				oppoisteEnterpriseName, isImportGoods, customsEnvelopBillNo,
				isshow);
		Object[] parameters = this.getCustomsEnvleopSpareAnalyesParameters(
				beginDate, endDate, commodityCode, commodityName, seqNum,
				oppoisteEnterpriseName, isImportGoods, customsEnvelopBillNo);
		return this.transferFactoryManageDao.findCustomsEnvelopSpareAnalyes(
				hsql, parameters);
	}

	private double getCustomsEnvelopTransferQuantity(boolean isImport,
			Integer seqNum, Complex complex, String customsEnvelopNo) {
		double bcsAmount = this.transferFactoryManageDao
				.getCustomsEnvelopTransferQuantity(isImport,
						"BcsCustomsDeclarationCommInfo", seqNum, complex,
						customsEnvelopNo);
		double bcusAmount = this.transferFactoryManageDao
				.getCustomsEnvelopTransferQuantity(isImport,
						"CustomsDeclarationCommInfo", seqNum, complex,
						customsEnvelopNo);
		double dzscAmount = this.transferFactoryManageDao
				.getCustomsEnvelopTransferQuantity(isImport,
						"DzscCustomsDeclarationCommInfo", seqNum, complex,
						customsEnvelopNo);
		return bcsAmount + bcusAmount + dzscAmount;
	}

	/**
	 * 获得查询语句
	 * 
	 * @param commodityCode
	 *            商品编码
	 * @param commodityName
	 *            商品名称
	 * @param seqNum
	 *            帐册序号
	 * @param oppoisteEnterpriseName
	 *            对方企事业名称
	 * @param isImportGoods
	 *            转入/转出
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return
	 */
	private String getCustomsEnvelopSpareAnalyesHsql(Date beginDate,
			Date endDate, String commodityCode, String commodityName,
			String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo, boolean isshow) {
		String hsql = "from CustomsEnvelopCommodityInfo a left join fetch a.customsEnvelopBill b "
				+ " where a.customsEnvelopBill.company.id=? ";
		String condition = "";

		if (beginDate != null) {
			condition += " and a.customsEnvelopBill.beginAvailability>=?  ";
		}
		if (endDate != null) {
			condition += " and a.customsEnvelopBill.beginAvailability<=? ";
		}

		if (commodityCode != null) {
			if (!commodityCode.equals("")) {
				condition += " and a.complex.code = ?  ";
			}
		}

		if (commodityName != null) {
			if (!commodityName.equals("")) {
				condition += " and ( a.ptName = ? or "
						+ " a.oppositeName = ? ) ";
			}
		}
		if (seqNum != null) {
			if (!seqNum.equals("")) {
				condition += " and  ( a.seqNum = ? or "
						+ " a.oppositeEmsNo = ? ) ";
			}
		}
		if (oppoisteEnterpriseName != null) {
			if (!oppoisteEnterpriseName.equals("")) {
				condition += " and b.scmCoc.id = ? ";
			}
		}
		// if (isImportGoods != null) {
		condition += " and b.isImpExpGoods = ? ";
		// }
		if (customsEnvelopBillNo != null) {
			if (!customsEnvelopBillNo.equals("")) {
				condition += " and b.customsEnvelopBillNo = ? ";
			}
		}

		if (Boolean.TRUE.equals(isshow)) {
			condition += " and b.customsEnvelopBillNo.isEndCase != true ";
		}
		return hsql + condition
				+ " order by b.customsEnvelopBillNo,a.seqNum,a.ceseqNum ";
	}

	/**
	 * 获得查询对象
	 * 
	 * @param commodityCode
	 *            商品编码
	 * @param commodityName
	 *            商品名称
	 * @param seqNum
	 *            帐册序号
	 * @param oppoisteEnterpriseName
	 *            对方企事业名称
	 * @param isImportGoods
	 *            转入/转出
	 * @param customsEnvelopBillNo
	 *            关封号
	 * @return
	 */
	private Object[] getCustomsEnvleopSpareAnalyesParameters(Date beginDate,
			Date endDate, String commodityCode, String commodityName,
			String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo) {
		List list = new ArrayList();
		list.add(CommonUtils.getCompany().getId());
		if (beginDate != null) {
			list.add(CommonUtils.getBeginDate(beginDate));
		}
		if (endDate != null) {
			list.add(CommonUtils.getEndDate(endDate));
		}

		if (commodityCode != null) {
			if (!commodityCode.equals("")) {
				list.add(commodityCode);
				// list.add(commodityCode);
			}
		}
		if (commodityName != null) {
			if (!commodityName.equals("")) {
				list.add(commodityName);
				list.add(commodityName);
			}
		}
		if (seqNum != null) {
			Integer seqNumber = 0;
			try {
				seqNumber = Integer.valueOf(seqNum);
			} catch (Exception ex) {

			}
			if (!seqNum.equals("")) {
				list.add(seqNumber);
				list.add(seqNum);
			}
		}
		if (oppoisteEnterpriseName != null) {
			if (!oppoisteEnterpriseName.equals("")) {
				list.add(oppoisteEnterpriseName);
			}
		}
		// if (isImportGoods != null) {
		list.add(isImportGoods);
		// }
		if (customsEnvelopBillNo != null) {
			if (!customsEnvelopBillNo.equals("")) {
				list.add(customsEnvelopBillNo);
			}
		}
		return list.toArray();
	}

	/**
	 * 转厂进出货明细表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billType
	 *            单据类型
	 * @param billCode
	 *            单据号码
	 * @param materielCode
	 *            料号
	 * @param materielName
	 *            名称
	 * @param seqNum
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */
	public List findTransferFactoryImpExpGoodsList(Date beginDate,
			Date endDate, Integer billType, String billCode,
			String materielCode, String materielName, String seqNum,
			String companyName) {
		List result = new ArrayList();
		List list = this.transferFactoryManageDao
				.findTransferFactoryImpExpGoodsList(beginDate, endDate,
						billType, billCode, materielCode, materielName, seqNum,
						companyName);
		Object[] tempObjs = null;
		for (int i = 0; i < list.size(); i++) {
			tempObjs = (Object[]) list.get(i);
			TempTransferFactoryImpExpGoodsList tempGoodsList = new TempTransferFactoryImpExpGoodsList();
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			tempGoodsList.setBillDate(tempObjs[0] != null ? dateFormat
					.format((Date) tempObjs[0]) : "");
			tempGoodsList.setBillType(tempObjs[1] == null ? ""
					: ((Boolean) tempObjs[1]).booleanValue() ? "转厂进口" : "转厂出口");
			tempGoodsList.setBillCode(tempObjs[2] == null ? "" : tempObjs[2]
					.toString());
			tempGoodsList.setEmsNo(tempObjs[3] == null ? "" : tempObjs[3]
					.toString());
			tempGoodsList.setMaterielCode((String) tempObjs[4]);
			tempGoodsList.setMaterielName((String) tempObjs[5]);
			tempGoodsList.setTransferQuantity(tempObjs[6] == null ? new Double(
					0) : Double.valueOf(tempObjs[6].toString()));
			tempGoodsList.setCompanyName((String) tempObjs[7]);
			tempGoodsList.setUnit(tempObjs[8].toString());
			/*
			 * 新增 BY SXK
			 */
			tempGoodsList.setEnvelopNo((String) tempObjs[9]);
			tempGoodsList.setScmCoc((String) tempObjs[10]);
			result.add(tempGoodsList);
		}
		return result;
	}

	/**
	 * 结转进出口明细表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param billType
	 *            单据类型
	 * @param billCode
	 *            单据号码
	 * @param materielCode
	 *            料号
	 * @param materielName
	 *            名称
	 * @param seqNum
	 *            帐册序号
	 * @param companyName
	 *            公司名称
	 * @return
	 */
	public List findTransferImpExpCustomsList(Date beginDate, Date endDate,
			Integer billType, String billCode, String materielCode,
			String materielName, String seqNum, String companyName) {
		List result = new ArrayList();
		List list = this.transferFactoryManageDao
				.findTransferImpExpCustomsList(beginDate, endDate, billType,
						billCode, materielCode, materielName, seqNum,
						companyName);
		Object[] tempObjs = null;
		for (int i = 0; i < list.size(); i++) {
			tempObjs = (Object[]) list.get(i);
			TempTransferFactoryImpExpGoodsList tempGoodsList = new TempTransferFactoryImpExpGoodsList();
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			tempGoodsList.setBillDate(tempObjs[0] != null ? dateFormat
					.format((Date) tempObjs[0]) : "");
			tempGoodsList
					.setBillType(tempObjs[1] == null ? ""
							: ((Integer) tempObjs[1]).intValue() == ImpExpType.TRANSFER_FACTORY_IMPORT ? "料件转厂"
									: "转厂出口");
			tempGoodsList.setBillCode(tempObjs[2] == null ? "" : tempObjs[2]
					.toString());
			tempGoodsList.setEmsNo(tempObjs[3] == null ? "" : tempObjs[3]
					.toString());
			tempGoodsList.setMaterielCode((String) tempObjs[4]);
			tempGoodsList.setMaterielName((String) tempObjs[5]);
			tempGoodsList.setTransferQuantity(tempObjs[6] == null ? new Double(
					0) : Double.valueOf(tempObjs[6].toString()));
			tempGoodsList.setUnit((String) tempObjs[7]);
			tempGoodsList.setCompanyName((String) tempObjs[8]);
			/*
			 * 修改 BY SXK
			 */
			tempGoodsList.setCustomsDeclarationCode((String) tempObjs[9]);
			tempGoodsList.setScmCoc((String) tempObjs[10]);
			result.add(tempGoodsList);
		}
		return result;
	}

	/**
	 * 转厂进出货状况表
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param impExpType
	 * @param billCode
	 * @param materielCode
	 * @param materielName
	 * @param emsNo
	 * @param companyName
	 * @return
	 */
	public List findStatisticTotalTransferStatusQuantity(Date beginDate,
			Date endDate, Integer impExpType, String billCode,
			String materielCode, String materielName, String materielSpec,
			String companyName, String scmcode) {
		List result = new ArrayList();
		TempTransferStatisticTotalQuantity transferQuantity = null;
		List materiels = this.transferFactoryManageDao
				.findStatisticAnalyseMateriel(beginDate, endDate, impExpType,
						billCode, materielCode, materielName, materielSpec,
						companyName, scmcode);
		HashMap hmPreImpExpGoodsQuantity = (beginDate == null ? new HashMap()
				: toConvertListToHashMap(this.transferFactoryManageDao
						.findStatisticTotalImpExpGoodsQuantity(null, beginDate,
								impExpType, billCode, materielCode,
								materielName, materielSpec, companyName,
								scmcode)));
		HashMap hmNowImpExpGoodsQuantity = toConvertListToHashMap(this.transferFactoryManageDao
				.findStatisticTotalImpExpGoodsQuantity(beginDate, endDate,
						impExpType, billCode, materielCode, materielName,
						materielSpec, companyName, scmcode));
		HashMap hmPreTransferQuantity = (beginDate == null ? new HashMap()
				: toConvertListToHashMap(this.transferFactoryManageDao
						.findStatisticTotalTransferQuantity(null, beginDate,
								impExpType, billCode, materielCode,
								materielName, materielSpec, companyName,
								scmcode, null)));
		HashMap hmNowTransferQuantity = toConvertListToHashMap(this.transferFactoryManageDao
				.findStatisticTotalTransferQuantity(beginDate, endDate,
						impExpType, billCode, materielCode, materielName,
						materielSpec, companyName, scmcode, null));
		HashMap hmPreInitQuantity = (beginDate == null ? new HashMap()
				: toConvertListToHashMap(this.transferFactoryManageDao
						.findStatisticTotalImpExpGoodsInitQuantity(null,
								beginDate, impExpType, billCode, materielCode,
								materielName, materielSpec, companyName,
								scmcode)));
		HashMap hmNowInitQuantity = toConvertListToHashMap(this.transferFactoryManageDao
				.findStatisticTotalImpExpGoodsInitQuantity(beginDate, endDate,
						impExpType, billCode, materielCode, materielName,
						materielSpec, companyName, scmcode));
		Object[] objs = null;
		Object objTemp = null;
		for (int i = 0; i < materiels.size(); i++) {
			transferQuantity = new TempTransferStatisticTotalQuantity();
			objs = (Object[]) materiels.get(i);
			String envlopNo = (objs[5] == null ? "" : objs[5].toString().trim());
			String emsNo = (objs[6] == null ? "" : objs[6].toString());
			String mCode = (objs[3] == null ? "" : objs[3].toString()) + "/"
					+ (emsNo) + "/" + (envlopNo == null ? "" : envlopNo) + "/"
					+ (objs[4] == null ? "" : objs[4].toString());
			transferQuantity.setMaterielCode((String) objs[0]);
			transferQuantity.setMaterielName((String) objs[1]);
			transferQuantity.setUnit((String) objs[2]);
			transferQuantity.setEmsNo(emsNo);
			transferQuantity.setSeqNum(objs[3] == null ? "" : objs[3]
					.toString());// 帐册序号
			transferQuantity.setScmcocName((String) objs[4]);
			transferQuantity.setEnvlopNo(envlopNo);
			objTemp = hmPreImpExpGoodsQuantity.get(mCode);

			Double preImpExpGoodsQuantity = (objTemp == null ? new Double(0.0)
					: Double.valueOf(objTemp.toString()));// 前期进出货累计
			Double preTransferQuantity = (Double) hmPreTransferQuantity
					.get(mCode);// 前期转厂累计
			Double preInitQuantity = (Double) hmPreInitQuantity.get(mCode);//
			// 前期初始化未转厂数量
			Double preNoTransferQuantity = new Double(
					CommonUtils.getDoubleExceptNull(preImpExpGoodsQuantity)
							- CommonUtils
									.getDoubleExceptNull(preTransferQuantity)
							+ CommonUtils.getDoubleExceptNull(preInitQuantity));// 前期未转厂累计
			objTemp = hmNowImpExpGoodsQuantity.get(mCode);
			Double nowImpExpGoodsQuantity = objTemp == null ? new Double(0)
					: Double.valueOf(objTemp.toString());// 本期进出货累计
			Double nowTransferQuantity = (Double) hmNowTransferQuantity
					.get(mCode);// 本期转厂累计
			Double nowInitQuantity = (Double) hmNowInitQuantity.get(mCode);//
			// 本期初始化未转厂数量
			Double nowNoTransferQuantity = new Double(
					CommonUtils.getDoubleExceptNull(nowImpExpGoodsQuantity)
							- CommonUtils
									.getDoubleExceptNull(nowTransferQuantity)
							+ CommonUtils.getDoubleExceptNull(nowInitQuantity));// 本期未转厂累计
			Double totalNoTransferQuantity = new Double(
					CommonUtils.getDoubleExceptNull(preNoTransferQuantity)
							+ CommonUtils
									.getDoubleExceptNull(nowNoTransferQuantity));// 合计未转厂累计

			transferQuantity.setPreImpExpGoodsQuantity(preImpExpGoodsQuantity);
			transferQuantity.setPreTransferQuantity(preTransferQuantity);
			transferQuantity.setPreNoTransferQuantity(preNoTransferQuantity);
			transferQuantity.setNowImpExpGoodsQuantity(nowImpExpGoodsQuantity);
			transferQuantity.setNowTransferQuantity(nowTransferQuantity);
			transferQuantity.setNowNoTransferQuantity(nowNoTransferQuantity);
			transferQuantity
					.setTotalNoTransferQuantity(totalNoTransferQuantity);
			result.add(transferQuantity);
		}
		return result;
	}

	private HashMap toConvertListToHashMap(List list) {
		HashMap<String, Double> hashMap = new HashMap<String, Double>();
		Object[] objs = null;
		for (int i = 0; i < list.size(); i++) {
			objs = (Object[]) list.get(i);
			String mcode = (objs[0] == null ? "" : objs[0].toString()) + "/"
					+ (objs[1] == null ? "" : objs[1].toString()) + "/"
					+ (objs[2] == null ? "" : objs[2].toString()) + "/"
					+ (objs[3] == null ? "" : objs[3].toString());
			if (mcode != null && !mcode.trim().equals("")) {
				if (hashMap.get(mcode) == null) {
					hashMap.put(
							mcode,
							objs[4] == null ? 0.0 : Double.valueOf(objs[4]
									.toString()));
				} else {
					hashMap.put(
							mcode,
							Double.valueOf(hashMap.get(mcode).toString())
									+ (objs[4] == null ? 0.0 : Double
											.valueOf(objs[4].toString())));
				}
			}
		}
		return hashMap;
	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public List findExportCustomsDeclaration(
			CustomsEnvelopBill customsEnvelopBill) {
		List lsResult = new ArrayList();
		List list = this.transferFactoryManageDao
				.findExportCustomsDeclaration(ProjectType.BCUS
				// , invoice.getBeginDate(), invoice.getEndDate()
				);
		organizeTempInfo(lsResult, list, ProjectType.BCUS);
		list = this.transferFactoryManageDao
				.findExportCustomsDeclaration(ProjectType.BCS
				// ,invoice.getBeginDate(), invoice.getEndDate()
				);
		organizeTempInfo(lsResult, list, ProjectType.BCS);
		return lsResult;
	}

	private void organizeTempInfo(List lsResult, List lsTemp,
			Integer projectType) {
		for (int i = 0; i < lsTemp.size(); i++) {
			Object[] objs = (Object[]) lsTemp.get(i);
			TempCustomsDelcarationInfo temp = new TempCustomsDelcarationInfo();
			if (objs[1] == null || "".equals(objs[1].toString().trim())) {
				continue;
			}
			if (objs[0] != null) {
				temp.setCustomsDeclarationId(objs[0].toString());
			}
			if (objs[1] != null) {
				temp.setCustomsDeclarationCode(objs[1].toString());
			}
			if (objs[2] != null) {
				temp.setMoney(Double.valueOf(objs[2].toString()));
			}
			temp.setProjectType(projectType);
			lsResult.add(temp);
		}
	}

	/**
	 * 抓取报关单资料
	 * 
	 * @param projectType
	 * @return
	 */
	public List findImportCustomsDeclaration(
			CustomsEnvelopBill customsEnvelopBill) {
		List lsResult = new ArrayList();
		List list = this.transferFactoryManageDao
				.findImportCustomsDeclaration(ProjectType.BCUS
				// , invoice.getBeginDate(), invoice.getEndDate()
				);
		organizeTempInfo(lsResult, list, ProjectType.BCUS);
		list = this.transferFactoryManageDao
				.findImportCustomsDeclaration(ProjectType.BCS
				// ,invoice.getBeginDate(), invoice.getEndDate()
				);
		organizeTempInfo(lsResult, list, ProjectType.BCS);
		return lsResult;
	}

	// private void organizeTempInfo(List lsResult, List lsTemp,
	// Integer projectType) {
	// for (int i = 0; i < lsTemp.size(); i++) {
	// Object[] objs = (Object[]) lsTemp.get(i);
	// TempCustomsDelcarationInfo temp = new TempCustomsDelcarationInfo();
	// if (objs[1] == null || "".equals(objs[1].toString().trim())) {
	// continue;
	// }
	// if (objs[0] != null) {
	// temp.setCustomsDeclarationId(objs[0].toString());
	// }
	// if (objs[1] != null) {
	// temp.setCustomsDeclarationCode(objs[1].toString());
	// }
	// if (objs[2] != null) {
	// temp.setMoney(Double.valueOf(objs[2].toString()));
	// }
	// temp.setProjectType(projectType);
	// lsResult.add(temp);
	// }
	// }

	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	public BaseEmsHead findEmsHeadByProjectType(Integer projectType,
			String emsNo) {
		// List lsEmsHead = new ArrayList();
		// BaseEmsHead emsHead=null;
		List list = new ArrayList();
		switch (projectType) {
		case ProjectType.BCS:
			list = this.contractDao.findContractByProcessExe();
			for (int i = 0; i < list.size(); i++) {
				Contract contract = (Contract) list.get(i);
				// TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
				// emsInfo.setProjectType(projectType);
				// emsInfo.setEmsNo(contract.getEmsNo());
				if (emsNo.equals(contract.getEmsNo())) {
					return contract;
				}
				// lsEmsHead.add(emsInfo);
			}
			break;
		case ProjectType.DZSC:
			list = this.dzscDao.findDzscEmsPorHeadExcu();
			for (int i = 0; i < list.size(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) list.get(i);
				// TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
				// emsInfo.setProjectType(projectType);
				// emsInfo.setEmsNo(contract.getEmsNo());
				// lsEmsHead.add(emsInfo);
				if (emsNo.equals(contract.getEmsNo())) {
					return contract;
				}
			}
			break;
		case ProjectType.BCUS:
			list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2k contract = (EmsHeadH2k) list.get(i);
				// TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
				// emsInfo.setProjectType(projectType);
				// emsInfo.setEmsNo(contract.getEmsNo());
				// lsEmsHead.add(emsInfo);
				if (emsNo.equals(contract.getEmsNo())) {
					return contract;
				}
			}
			break;
		}
		// return lsEmsHead;
		return null;
	}

	/**
	 * 抓取电子化手册合同号
	 */
	public Contract findEmsHeadByProject(String emsNo) {
		// List lsEmsHead = new ArrayList();
		// BaseEmsHead emsHead=null;
		List list = new ArrayList();
		list = this.contractDao.findContractByProcessExe();
		for (int i = 0; i < list.size(); i++) {
			Contract contract = (Contract) list.get(i);
			// TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
			// emsInfo.setProjectType(projectType);
			// emsInfo.setEmsNo(contract.getEmsNo());
			if (emsNo.equals(contract.getEmsNo())) {
				return contract;
			}
			// lsEmsHead.add(emsInfo);
		}
		// return lsEmsHead;
		return null;
	}

	/**
	 * 根据手册号码抓取正在执行的电子手册
	 * 
	 * @param emsNo
	 * @return
	 */
	public DzscEmsPorHead findDzscEmsPorHeadExcu(String emsNo) {
		List list = this.dzscDao.findDzscEmsPorHeadExcu(emsNo);
		if (list.size() > 0) {
			return (DzscEmsPorHead) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据账册册号码抓取正在执行的电子账册
	 * 
	 * @param emsNo
	 * @return
	 */
	public EmsHeadH2k findEmsHeadH2kInExecuting(String emsNo) {
		List list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
		for (int i = 0; i < list.size(); i++) {
			EmsHeadH2k contract = (EmsHeadH2k) list.get(i);
			if (contract.getEmsNo().equals(emsNo)) {
				return contract;
			}
		}
		return null;
	}

	/**
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	public List findEmsHeadByProjectType(Integer projectType) {
		List lsEmsHead = new ArrayList();
		List list = new ArrayList();
		switch (projectType) {
		case ProjectType.BCS:
			list = this.contractDao.findContractByProcessExe();
			for (int i = 0; i < list.size(); i++) {
				Contract contract = (Contract) list.get(i);
				TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
				emsInfo.setProjectType(projectType);
				emsInfo.setEmsNo(contract.getEmsNo());
				lsEmsHead.add(emsInfo);
			}
			break;
		case ProjectType.DZSC:
			list = this.dzscDao.findDzscEmsPorHeadExcu();
			for (int i = 0; i < list.size(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) list.get(i);
				TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
				emsInfo.setProjectType(projectType);
				emsInfo.setEmsNo(contract.getEmsNo());
				lsEmsHead.add(emsInfo);
			}
			break;
		case ProjectType.BCUS:
			list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2k contract = (EmsHeadH2k) list.get(i);
				TempCustomsEmsInfo emsInfo = new TempCustomsEmsInfo();
				emsInfo.setProjectType(projectType);
				emsInfo.setEmsNo(contract.getEmsNo());
				lsEmsHead.add(emsInfo);
			}
			break;
		}
		return lsEmsHead;
	}

	/**
	 * 根据系统类型，账册号或手册号，物料和成品分类抓取转厂的物料信息
	 * 
	 * @param projectType
	 * @return
	 */
	public List findTempCustomsEnvelopRequestCommInfo(Integer projectType,
			String emsNo, CustomsEnvelopBill customsEnvelopBill,
			boolean isMaterial) {
		List lsCommInfo = new ArrayList();
		List list = new ArrayList();
		Map map = new HashMap();
		List<BillPriceMaintenance> rList = this.transferFactoryManageDao
				.findBillPriceMaintenanceByProjectType(projectType);
		for (BillPriceMaintenance data : rList) {
			String key = data.getSeqNum().toString() + data.getPtName()
					+ data.getPtSpec();
			Double value = data.getUnitPrice();
			map.put(key, value);
		}
		String[] emsNoArray = emsNo.split(";");
		switch (projectType) {
		case ProjectType.BCS:
			if (isMaterial) {
				for (int m = 0; m < emsNoArray.length; m++) {
					if (emsNoArray[m] == null
							|| "".equals(emsNoArray[m].trim())) {
						continue;
					}
					list = this.transferFactoryManageDao
							.findContractImgByEmsNo(emsNoArray[m],
									customsEnvelopBill.getId());
					for (int i = 0; i < list.size(); i++) {
						ContractImg img = (ContractImg) list.get(i);
						TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
						// commInfo.setProjectType(projectType);
						commInfo.setEmsNo(emsNoArray[m]);
						commInfo.setSeqNum(img.getSeqNum());
						if (img.getComplex() != null) {
							commInfo.setComplex(img.getComplex().getCode());
						}
						commInfo.setPtName(img.getName());
						commInfo.setPtSpec(img.getSpec());
						commInfo.setUnit(img.getUnit());
						commInfo.setCurr(img.getContract().getCurr());
						String key = commInfo.getSeqNum().toString()
								+ commInfo.getPtName() + commInfo.getPtSpec();
						if (map.containsKey(key)) {
							commInfo.setPrice((Double) map.get(key));
						} else {
							commInfo.setPrice(img.getDeclarePrice());
						}
						lsCommInfo.add(commInfo);
					}
				}
			} else {
				for (int m = 0; m < emsNoArray.length; m++) {
					if (emsNoArray[m] == null
							|| "".equals(emsNoArray[m].trim())) {
						continue;
					}
					list = this.transferFactoryManageDao
							.findContractExgByEmsNo(emsNoArray[m],
									customsEnvelopBill.getId());
					for (int i = 0; i < list.size(); i++) {
						ContractExg exg = (ContractExg) list.get(i);
						TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
						// commInfo.setProjectType(projectType);
						commInfo.setEmsNo(emsNoArray[m]);
						commInfo.setSeqNum(exg.getSeqNum());
						if (exg.getComplex() != null) {
							commInfo.setComplex(exg.getComplex().getCode());
						}
						commInfo.setPtName(exg.getName());
						commInfo.setPtSpec(exg.getSpec());
						commInfo.setUnit(exg.getUnit());
						commInfo.setCurr(exg.getContract().getCurr());
						String key = commInfo.getSeqNum().toString()
								+ commInfo.getPtName() + commInfo.getPtSpec();
						if (map.containsKey(key)) {
							commInfo.setPrice((Double) map.get(key));
						} else {
							commInfo.setPrice(exg.getDeclarePrice());
						}
						lsCommInfo.add(commInfo);
					}
				}
			}
			break;
		case ProjectType.DZSC:

			if (isMaterial) {
				for (int m = 0; m < emsNoArray.length; m++) {
					if (emsNoArray[m] == null
							|| "".equals(emsNoArray[m].trim())) {
						continue;
					}
					list = this.transferFactoryManageDao
							.findDzscEmsImgBillByEmsNo(emsNoArray[m],
									customsEnvelopBill.getId());
					for (int i = 0; i < list.size(); i++) {
						DzscEmsImgBill img = (DzscEmsImgBill) list.get(i);
						TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
						// commInfo.setProjectType(projectType);
						commInfo.setEmsNo(emsNoArray[m]);
						commInfo.setSeqNum(img.getSeqNum());
						commInfo.setComplex(img.getComplex().getCode());
						commInfo.setPtName(img.getName());
						commInfo.setPtSpec(img.getSpec());
						commInfo.setUnit(img.getUnit());
						commInfo.setCurr(img.getDzscEmsPorHead().getCurr());
						String key = ((commInfo.getSeqNum() == null ? ""
								: commInfo.getSeqNum().toString())
								+ commInfo.getPtName() + commInfo.getPtSpec());
						if (map.containsKey(key)) {
							commInfo.setPrice((Double) map.get(key));
						} else {
							commInfo.setPrice(img.getPrice());
						}
						lsCommInfo.add(commInfo);
					}
				}
			} else {
				for (int m = 0; m < emsNoArray.length; m++) {
					if (emsNoArray[m] == null
							|| "".equals(emsNoArray[m].trim())) {
						continue;
					}
					list = this.transferFactoryManageDao
							.findDzscEmsExgBillByEmsNo(emsNoArray[m],
									customsEnvelopBill.getId());
					for (int i = 0; i < list.size(); i++) {
						DzscEmsExgBill exg = (DzscEmsExgBill) list.get(i);
						TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
						// commInfo.setProjectType(projectType);
						commInfo.setEmsNo(emsNoArray[m]);
						commInfo.setSeqNum(exg.getSeqNum());
						commInfo.setComplex(exg.getComplex().getCode());
						commInfo.setPtName(exg.getName());
						commInfo.setPtSpec(exg.getSpec());
						commInfo.setUnit(exg.getUnit());
						String key = commInfo.getSeqNum().toString()
								+ commInfo.getPtName() + commInfo.getPtSpec();
						if (map.containsKey(key)) {
							commInfo.setPrice((Double) map.get(key));
						} else {
							commInfo.setPrice(exg.getPrice());
						}
						commInfo.setCurr(exg.getDzscEmsPorHead().getCurr());
						lsCommInfo.add(commInfo);
					}
				}
			}
			break;
		case ProjectType.BCUS:
			if (isMaterial) {
				for (int m = 0; m < emsNoArray.length; m++) {
					if (emsNoArray[m] == null
							|| "".equals(emsNoArray[m].trim())) {
						continue;
					}
					list = this.transferFactoryManageDao
							.findEmsHeadH2kImgByEmsNo(emsNoArray[m],
									customsEnvelopBill.getId());
					for (int i = 0; i < list.size(); i++) {
						EmsHeadH2kImg img = (EmsHeadH2kImg) list.get(i);
						TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
						// commInfo.setProjectType(projectType);
						commInfo.setEmsNo(emsNoArray[m]);
						commInfo.setSeqNum(img.getSeqNum());
						commInfo.setComplex(img.getComplex().getCode());
						commInfo.setPtName(img.getName());
						commInfo.setPtSpec(img.getSpec());
						commInfo.setUnit(img.getUnit());
						commInfo.setCurr(img.getCurr());
						String key = commInfo.getSeqNum().toString()
								+ commInfo.getPtName() + commInfo.getPtSpec();
						if (map.containsKey(key)) {
							commInfo.setPrice((Double) map.get(key));
						} else {
							commInfo.setPrice(CommonUtils.getEmsImgPrice(img));
						}
						lsCommInfo.add(commInfo);
					}
				}
			} else {
				for (int m = 0; m < emsNoArray.length; m++) {
					if (emsNoArray[m] == null
							|| "".equals(emsNoArray[m].trim())) {
						continue;
					}
					list = this.transferFactoryManageDao
							.findEmsHeadH2kExgByEmsNo(emsNoArray[m],
									customsEnvelopBill.getId());
					for (int i = 0; i < list.size(); i++) {
						EmsHeadH2kExg exg = (EmsHeadH2kExg) list.get(i);
						TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
						// commInfo.setProjectType(projectType);
						commInfo.setEmsNo(emsNoArray[m]);
						commInfo.setSeqNum(exg.getSeqNum());
						commInfo.setComplex(exg.getComplex().getCode());
						commInfo.setPtName(exg.getName());
						commInfo.setPtSpec(exg.getSpec());
						commInfo.setUnit(exg.getUnit());
						commInfo.setCurr(exg.getCurr());
						String key = commInfo.getSeqNum().toString()
								+ commInfo.getPtName() + commInfo.getPtSpec();
						if (map.containsKey(key)) {
							commInfo.setPrice((Double) map.get(key));
						} else {
							commInfo.setPrice(exg.getDeclarePrice());
						}

						lsCommInfo.add(commInfo);
					}
				}
			}
			break;
		}
		return lsCommInfo;
	}

	/**
	 * 把查询出来的临时商品信息,保存为关封的商品信息
	 * 
	 * @param list
	 * @param envelopBill
	 * @return
	 */
	public List saveCustomsEnvelopRequestCommInfo(
			List<TempCustomsEnvelopCommInfo> list,
			CustomsEnvelopBill envelopBill) {
		List lsResult = new ArrayList();
		for (TempCustomsEnvelopCommInfo tempCommInfo : list) {
			CustomsEnvelopCommodityInfo commInfo = new CustomsEnvelopCommodityInfo();
			commInfo.setCustomsEnvelopBill(envelopBill);
			commInfo.setEmsNo(tempCommInfo.getEmsNo());
			commInfo.setSeqNum(tempCommInfo.getSeqNum());
			// commInfo.setComplex((Complex) this.commonCodeDao.load(
			// Complex.class, tempCommInfo.getComplex()));
			commInfo.setComplex(this.transferFactoryManageDao
					.findComplexByCode(tempCommInfo.getComplex()));
			commInfo.setPtName(tempCommInfo.getPtName());
			commInfo.setPtSpec(tempCommInfo.getPtSpec());
			commInfo.setUnit(tempCommInfo.getUnit());
			commInfo.setCurr(tempCommInfo.getCurr());
			commInfo.setUnitPrice(tempCommInfo.getPrice());
			lsResult.add(commInfo);
		}
		this.transferFactoryManageDao.saveCustomsEnvelopCommodityInfo(lsResult);
		return lsResult;
	}

	/**
	 * 报关清单生成报关单的基本信息 (表头信息)
	 * 
	 * @param transferFactoryBill
	 *            报关清单表头
	 * @param param
	 *            报关清单自动生成报关单时的参数
	 * @param listSerialNumber
	 *            生成的报关单流水号
	 * @return DzscCustomsDeclaration 报关单表头
	 */
	private BaseCustomsDeclaration makeCustomsDeclaration(
			CustomsEnvelopBill customsEnvelopBill,
			MakeCusomsDeclarationParam param, List<Object> listSerialNumber) {
		if (customsEnvelopBill.getProjectType() == null) {
			return null;
		}
		String[] ems = customsEnvelopBill.getEmsNo().split(";");
		BaseEmsHead emsPorHead = findEmsHeadByProjectType(
				customsEnvelopBill.getProjectType(), ems[0]);
		if (emsPorHead == null) {
			return null;
		}
		BaseCustomsDeclaration customsDeclaration = null;
		String project = "";
		switch (customsEnvelopBill.getProjectType()) {
		case ProjectType.BCUS:
			customsDeclaration = new CustomsDeclaration();
			project = "联网监管";
			break;
		case ProjectType.BCS:
			customsDeclaration = new BcsCustomsDeclaration();
			project = "纸质手册";
			break;
		case ProjectType.DZSC:
			customsDeclaration = new DzscCustomsDeclaration();
			project = "电子手册";
			break;
		}
		if (project.equals("纸质手册")) {
			Contract contract = findEmsHeadByProject(ems[0]);
			if (contract.getImpContractNo() == null) {
				customsDeclaration.setContract(contract.getExpContractNo());
			} else {
				customsDeclaration.setContract(contract.getImpContractNo());
			}
		}
		customsDeclaration.setEmsHeadH2k(emsPorHead.getEmsNo());
		customsDeclaration.setTradeCode(emsPorHead.getTradeCode());
		customsDeclaration.setTradeName(emsPorHead.getTradeName());
		customsDeclaration.setMachCode(emsPorHead.getTradeCode());
		customsDeclaration.setMachName(emsPorHead.getMachName());
		// customsDeclaration.setBillListId(billList.getId());
		// customsDeclaration.setBillListId(billList.getListNo());
		Integer serialNumber = null;
		boolean isImpGoods = (customsEnvelopBill.getIsImpExpGoods() == null ? false
				: customsEnvelopBill.getIsImpExpGoods());
		int impExpFlag = (!isImpGoods ? ImpExpFlag.EXPORT : ImpExpFlag.IMPORT);
		int impExpType = (!isImpGoods ? ImpExpType.TRANSFER_FACTORY_EXPORT
				: ImpExpType.TRANSFER_FACTORY_IMPORT);
		serialNumber = this.encDao.getCustomsDeclarationSerialNumber(
				customsEnvelopBill.getProjectType(), impExpFlag,
				emsPorHead.getEmsNo());
		String customsSerialNo = (isImpGoods ? "进口报关单流水号" : "出口报关单流水号");
		listSerialNumber.add(project + ":" + customsSerialNo + "："
				+ serialNumber);
		customsDeclaration.setSerialNumber(serialNumber);
		customsDeclaration.setCompany(CommonUtils.getCompany());
		customsDeclaration.setCreater(CommonUtils.getRequest().getUser());
		customsDeclaration.setImpExpType(impExpType);
		customsDeclaration.setImpExpFlag(impExpFlag);
		customsDeclaration.setDeclarationDate(param.getDelcarationDate());
		customsDeclaration.setConveyance(param.getConveyance());
		customsDeclaration.setLevyKind(param.getLevyKind());
		customsDeclaration.setPerTax(param.getPerTax());
		customsDeclaration.setLicense(param.getLicense());
		customsDeclaration.setCountryOfLoadingOrUnloading(param
				.getCountryOfLoadingOrUnloading());
		customsDeclaration.setDomesticDestinationOrSource(param
				.getDomesticDestinationOrSource());
		customsDeclaration.setPortOfLoadingOrUnloading(param
				.getPortOfLoadingOrUnloading());
		customsDeclaration.setTransac(param.getTransac());
		customsDeclaration.setWrapType(param.getWrapType());
		customsDeclaration.setCustomer(param.getCustomer());
		customsDeclaration.setCustomsEnvelopBillNo(customsEnvelopBill
				.getCustomsEnvelopBillNo());
		this.transferFactoryManageDao.saveOrUpdate(customsDeclaration);
		// MakeCustomsDeclaration make = new MakeCustomsDeclaration();
		// make.setCustomsDeclarationId(customsDeclaration.getId());
		// make.setTransFactBillId(transferFactoryBill.getId());
		// this.transferFactoryManageDao.saveOrUpdate(make);
		// this.transferFactoryManageDao
		// .saveTransferFactoryBill(transferFactoryBill);
		return customsDeclaration;
	}

	/**
	 * 报关清单生成报关单的基本信息 (表头信息)
	 * 
	 * @param transferFactoryBill
	 *            报关清单表头
	 * @param param
	 *            报关清单自动生成报关单时的参数
	 * @param listSerialNumber
	 *            生成的报关单流水号
	 * @return DzscCustomsDeclaration 报关单表头
	 */
	private BaseCustomsDeclaration makeCustomsDeclaration(
			CustomsEnvelopBill customsEnvelopBill,
			MakeCusomsDeclarationParam param, List<Object> listSerialNumber,
			TransferFactoryBill transferFactoryBill) {
		// EmsHeadH2k emsHeadH2k = (EmsHeadH2k) emsEdiTrDao
		// .findEmsHeadH2kInExecuting().get(0);
		// CustomsEnvelopBill customsEnvelopBill = this.transferFactoryManageDao
		// .findCustomsEnvelopBillByCode(transferFactoryBill
		// .getTransferFactoryBillNo());
		// BaseEmsHead dzscEmsPorHead = (DzscEmsPorHead) dzscDao
		// .findDzscEmsPorHeadExcu().get(0);
		if (customsEnvelopBill.getProjectType() == null) {
			return null;
		}
		BaseEmsHead emsPorHead = findEmsHeadByProjectType(
				customsEnvelopBill.getProjectType(),
				customsEnvelopBill.getEmsNo());
		if (emsPorHead == null) {
			return null;
		}
		BaseCustomsDeclaration customsDeclaration = null;
		String project = "";
		switch (customsEnvelopBill.getProjectType()) {
		case ProjectType.BCUS:
			customsDeclaration = new CustomsDeclaration();
			project = "联网监管";
			break;
		case ProjectType.BCS:
			customsDeclaration = new BcsCustomsDeclaration();
			project = "纸质手册";
			break;
		case ProjectType.DZSC:
			customsDeclaration = new DzscCustomsDeclaration();
			project = "电子手册";
			break;
		}
		if (project.equals("纸质手册")) {
			Contract contract = findEmsHeadByProject(customsEnvelopBill
					.getEmsNo());
			if (contract.getImpContractNo() == null) {
				customsDeclaration.setContract(contract.getExpContractNo());
			} else {
				customsDeclaration.setContract(contract.getImpContractNo());
			}
		}

		customsDeclaration.setEmsHeadH2k(emsPorHead.getEmsNo());
		customsDeclaration.setTradeCode(emsPorHead.getTradeCode());
		customsDeclaration.setTradeName(emsPorHead.getTradeName());

		customsDeclaration.setMachCode(emsPorHead.getTradeCode());
		customsDeclaration.setMachName(emsPorHead.getMachName());

		// customsDeclaration.setBillListId(billList.getId());
		// customsDeclaration.setBillListId(billList.getListNo());
		Integer serialNumber = null;
		boolean isImpGoods = (transferFactoryBill.getIsImpExpGoods() == null ? false
				: transferFactoryBill.getIsImpExpGoods());
		int impExpFlag = (!isImpGoods ? ImpExpFlag.EXPORT : ImpExpFlag.IMPORT);
		int impExpType = (!isImpGoods ? ImpExpType.TRANSFER_FACTORY_EXPORT
				: ImpExpType.TRANSFER_FACTORY_IMPORT);
		serialNumber = this.encDao.getCustomsDeclarationSerialNumber(
				customsEnvelopBill.getProjectType(), impExpFlag,
				emsPorHead.getEmsNo());
		String customsSerialNo = (isImpGoods ? "进口报关单流水号" : "出口报关单流水号");
		listSerialNumber.add(project + ":" + customsSerialNo + "："
				+ serialNumber);
		if (transferFactoryBill.getMakeCustomsBillListCode() == null) {
			transferFactoryBill.setMakeCustomsDeclarationCode(project + ":"
					+ customsSerialNo + "：" + serialNumber + ";");
		} else {
			transferFactoryBill
					.setMakeCustomsDeclarationCode(transferFactoryBill
							.getMakeCustomsDeclarationCode()
							+ project
							+ ":"
							+ customsSerialNo + "：" + serialNumber + ";");
		}
		customsDeclaration.setSerialNumber(serialNumber);
		customsDeclaration.setCompany(CommonUtils.getCompany());
		// customsDeclaration.setManageCompany(getBrief(((Company) CommonUtils
		// .getCompany()).getBuCode()));
		// customsDeclaration
		// .setAcceptGoodsCompany(getBrief(((Company) CommonUtils
		// .getCompany()).getBuCode()));
		// customsDeclaration
		// .setDeclarationCompany(getBrief(((Company) CommonUtils
		// .getCompany()).getCode()));
		// customsDeclaration.setManufacturer(getBrief(((Company) CommonUtils
		// .getCompany()).getCode()));
		customsDeclaration.setCreater(CommonUtils.getRequest().getUser());
		customsDeclaration.setImpExpType(impExpType);
		customsDeclaration.setImpExpFlag(impExpFlag);
		customsDeclaration.setDeclarationDate(param.getDelcarationDate());
		// customsDeclaration.setImpExpDate(param.getImpExpDate());
		customsDeclaration.setConveyance(param.getConveyance());
		customsDeclaration.setLevyKind(param.getLevyKind());
		customsDeclaration.setPerTax(param.getPerTax());
		customsDeclaration.setLicense(param.getLicense());
		customsDeclaration.setCountryOfLoadingOrUnloading(param
				.getCountryOfLoadingOrUnloading());
		customsDeclaration.setDomesticDestinationOrSource(param
				.getDomesticDestinationOrSource());
		customsDeclaration.setPortOfLoadingOrUnloading(param
				.getPortOfLoadingOrUnloading());
		customsDeclaration.setTransac(param.getTransac());
		customsDeclaration.setWrapType(param.getWrapType());
		customsDeclaration.setCustomer(param.getCustomer());
		customsDeclaration.setCustomsEnvelopBillNo(transferFactoryBill
				.getEnvelopNo());
		this.transferFactoryManageDao.saveOrUpdate(customsDeclaration);
		MakeCustomsDeclaration make = new MakeCustomsDeclaration();
		make.setCustomsDeclarationId(customsDeclaration.getId());
		make.setTransFactBillId(transferFactoryBill.getId());
		this.transferFactoryManageDao.saveOrUpdate(make);
		this.transferFactoryManageDao
				.saveTransferFactoryBill(transferFactoryBill);
		return customsDeclaration;
	}

	/**
	 * 取得报关单商品流水号
	 * 
	 * @param customsDeclaration
	 *            报关单表头
	 * @return Integer 报关单商品流水号
	 */
	public Integer getCustomsDeclarationCommInfoSerialNumber(int projectType,
			BaseCustomsDeclaration customsDeclaration) {
		return this.transferFactoryManageDao
				.getCustomsDeclarationCommInfoSerialNumber(projectType,
						customsDeclaration.getId());
	}

	/**
	 * 结转单据自动转报关单及其商品信息
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	public List makeCusomsDeclarationFromTransferBill(List lsBills,
			MakeCusomsDeclarationParam param, boolean isMergeOne) {
		if (isMergeOne) {
			return makeCusomsDeclarationMerge(lsBills, param);
		} else {
			return makeCusomsDeclarationSingle(lsBills, param);
		}
	}

	/**
	 * 将多个单据合并成一张报关单
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	private List makeCusomsDeclarationMerge(List lsBills,
			MakeCusomsDeclarationParam param) {
		Map<Integer, BaseCustomsDeclarationCommInfo> hmCommInfo = new HashMap<Integer, BaseCustomsDeclarationCommInfo>();
		TransferFactoryBill transferFactoryBill = null;
		BaseCustomsDeclaration customsDeclaration = null;
		BaseCustomsDeclarationCommInfo commInfo = null;
		TransferFactoryCommodityInfo transFactCommInfo = null;
		ScmCoc scmCoc = null;
		String envelopNo = null;
		for (int i = 0; i < lsBills.size(); i++) {
			transferFactoryBill = (TransferFactoryBill) lsBills.get(i); // 结转单据
			if (i == 0) {
				scmCoc = transferFactoryBill.getScmCoc();
				envelopNo = transferFactoryBill.getEnvelopNo();
			} else {
				boolean isImport = transferFactoryBill.getIsImpExpGoods();
				if (!transferFactoryBill.getScmCoc().equals(scmCoc)) {
					throw new RuntimeException("选择的单据的"
							+ (isImport ? "供应商" : "客户") + "不是全部相同");
				}
				if (!transferFactoryBill.getEnvelopNo().equals(envelopNo)) {
					throw new RuntimeException("选择的单据的关封不是全部相同");
				}
			}
		}
		List<Object> listSerialNumber = new ArrayList<Object>();
		CustomsEnvelopBill customsEnvelopBill = this.transferFactoryManageDao
				.findCustomsEnvelopBillByCode(envelopNo);
		if (customsEnvelopBill == null
				|| customsEnvelopBill.getProjectType() == null) {
			throw new RuntimeException("无此关封:" + envelopNo);
		}
		customsDeclaration = makeCustomsDeclaration(customsEnvelopBill, param, // 添加到报关单头
				listSerialNumber);
		String customsSerialNo = "";
		for (int i = 0; i < listSerialNumber.size(); i++) {
			if (listSerialNumber.get(i) != null) {
				customsSerialNo += listSerialNumber.get(i).toString() + ";";
			}
		}
		double totalGrossWeight = 0.0;
		double totalNetWeight = 0.0;
		// 报关单参数
		CompanyOther co = (CompanyOther) transferFactoryManageDao
				.findCompanyOther().get(0);

		for (int i = 0; i < lsBills.size(); i++) {
			transferFactoryBill = (TransferFactoryBill) lsBills.get(i); // 结转单据
			transferFactoryBill.setIsCustomsDeclaration(true);// 结转单据状态：已转报关单
			transferFactoryBill.setMakeCustomsDeclarationCode(customsSerialNo);
			this.transferFactoryManageDao.saveOrUpdate(transferFactoryBill);
			List list = this.transferFactoryManageDao
					.findTransferFactoryCommodityInfo(transferFactoryBill
							.getId());

			for (int j = 0; j < list.size(); j++) {
				transFactCommInfo = (TransferFactoryCommodityInfo) list.get(j);
				commInfo = hmCommInfo.get(transFactCommInfo.getSeqNum());
				if (commInfo == null) {
					switch (customsEnvelopBill.getProjectType()) {
					case ProjectType.BCUS:
						commInfo = new CustomsDeclarationCommInfo();
						break;
					case ProjectType.BCS:
						commInfo = new BcsCustomsDeclarationCommInfo();
						break;
					case ProjectType.DZSC:
						commInfo = new DzscCustomsDeclarationCommInfo();
						break;
					}
					commInfo.setBaseCustomsDeclaration(customsDeclaration);
					commInfo.setSerialNumber(this
							.getCustomsDeclarationCommInfoSerialNumber(
									customsEnvelopBill.getProjectType(),
									customsDeclaration));
					commInfo.setCommSerialNo(transFactCommInfo.getSeqNum());
					commInfo.setComplex(transFactCommInfo.getComplex());
					commInfo.setCommName(transFactCommInfo.getCommName());
					commInfo.setCommSpec(transFactCommInfo.getCommSpec());
					commInfo.setUnit(transFactCommInfo.getUnit());
					commInfo.setCommUnitPrice(transFactCommInfo.getUnitPrice());
					commInfo.setCommAmount(transFactCommInfo
							.getTransFactAmount());
					commInfo.setLegalUnit(transFactCommInfo.getUnit());
					commInfo.setGrossWeight(transFactCommInfo.getGrossWeight());
					commInfo.setNetWeight(transFactCommInfo.getNetWeight());
					commInfo.setCommTotalPrice(transFactCommInfo
							.getTotalPrice());
					hmCommInfo.put(transFactCommInfo.getSeqNum(), commInfo);
				} else {
					commInfo.setCommAmount(CommonUtils
							.getDoubleExceptNull(commInfo.getCommAmount())
							+ CommonUtils.getDoubleExceptNull(transFactCommInfo
									.getTransFactAmount()));
					commInfo.setGrossWeight(CommonUtils
							.getDoubleExceptNull(commInfo.getGrossWeight())
							+ CommonUtils.getDoubleExceptNull(transFactCommInfo
									.getGrossWeight()));
					commInfo.setNetWeight(CommonUtils
							.getDoubleExceptNull(commInfo.getNetWeight())
							+ CommonUtils.getDoubleExceptNull(transFactCommInfo
									.getNetWeight()));

					commInfo.setCommTotalPrice(CommonUtils
							.getDoubleExceptNull(commInfo.getCommTotalPrice())
							+ CommonUtils.getDoubleExceptNull(transFactCommInfo
									.getTotalPrice()));

					commInfo.setCommUnitPrice(CaleUtil.dividend(
							commInfo.getCommTotalPrice(),
							commInfo.getCommAmount()));
				}

				totalGrossWeight = CaleUtil.add(totalGrossWeight,
						transFactCommInfo.getGrossWeight());
				totalNetWeight = CaleUtil.add(totalNetWeight,
						transFactCommInfo.getNetWeight());

				// 计算第一法定数量、第二法定数量:
				// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
				// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
				// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
				// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
				if (transFactCommInfo.getComplex().getFirstUnit() != null) {
					String unitName = transFactCommInfo.getUnit() == null ? ""
							: transFactCommInfo.getUnit().getName();
					String firstUnitName = transFactCommInfo.getComplex()
							.getFirstUnit().getName();

					if (unitName.equals(firstUnitName)) {// 条件1
						commInfo.setFirstAmount(commInfo.getCommAmount());
					} else if ("千克".equals(firstUnitName)) {// 条件2
						commInfo.setFirstAmount(commInfo.getNetWeight());
					} else if (getUnitRateMap().get(
							unitName + "+" + firstUnitName) != null) {// 条件3
						Double unitRate = Double
								.parseDouble(getUnitRateMap().get(
										unitName + "+" + firstUnitName)
										.toString());
						commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(
								commInfo.getCommAmount()
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								co.getCustomAmountNum()));
					}
				}

				if (transFactCommInfo.getComplex().getSecondUnit() != null) {
					// 当“计量单位”=“第二法定数量” 第二法定数量=数量
					String unitName = transFactCommInfo.getUnit() == null ? ""
							: transFactCommInfo.getUnit().getName();
					String secondUnitName = transFactCommInfo.getComplex()
							.getSecondUnit().getName();
					if (unitName.equals(secondUnitName)) {// 条件1
						commInfo.setSecondAmount(commInfo.getCommAmount());
					} else if ("千克".equals(secondUnitName)) {// 条件2
						commInfo.setSecondAmount(commInfo.getNetWeight());
					} else if (getUnitRateMap().get(
							unitName + "+" + secondUnitName) != null) {// 条件3
						Double unitRate = Double.parseDouble(getUnitRateMap()
								.get(unitName + "+" + secondUnitName)
								.toString());

						commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
								commInfo.getCommAmount()
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								co.getCustomAmountNum()));
					}
				}

			}

			customsDeclaration.setGrossWeight(totalGrossWeight);
			customsDeclaration.setNetWeight(totalNetWeight);
			this.transferFactoryManageDao.saveOrUpdate(customsDeclaration);

			MakeCustomsDeclaration make = new MakeCustomsDeclaration();
			make.setCustomsDeclarationId(customsDeclaration.getId());
			make.setTransFactBillId(transferFactoryBill.getId());
			this.transferFactoryManageDao.saveOrUpdate(make);
		}
		if (hmCommInfo.keySet().size() > 20) {
			throw new RuntimeException("合并后的报关单超出20项");
		}
		Iterator key = hmCommInfo.keySet().iterator();
		while (key.hasNext()) {
			Integer seqNum = Integer.parseInt(key.next().toString());
			commInfo = hmCommInfo.get(seqNum);
			if (commInfo != null) {
				commInfo.setCommTotalPrice(CommonUtils
						.getDoubleExceptNull(commInfo.getCommAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getCommUnitPrice()));
				this.transferFactoryManageDao.saveOrUpdate(commInfo);
			}
		}
		return listSerialNumber;
	}

	/**
	 * 将一个单据生成一张报关单
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	private List makeCusomsDeclarationSingle(List lsBills,
			MakeCusomsDeclarationParam param) {
		TransferFactoryBill transferFactoryBill = null;
		BaseCustomsDeclaration customsDeclaration = null;
		BaseCustomsDeclarationCommInfo commInfo = null;
		TransferFactoryCommodityInfo transFactCommInfo = null;

		List<Object> listSerialNumber = new ArrayList<Object>();
		for (int i = 0; i < lsBills.size(); i++) {
			transferFactoryBill = (TransferFactoryBill) lsBills.get(i); // 结转单据
			CustomsEnvelopBill customsEnvelopBill = this.transferFactoryManageDao
					.findCustomsEnvelopBillByCode(transferFactoryBill
							.getEnvelopNo());
			if (customsEnvelopBill == null
					|| customsEnvelopBill.getProjectType() == null) {
				System.out
						.println(transferFactoryBill.getEnvelopNo() + " 无此关封");
				continue;
			}
			transferFactoryBill.setIsCustomsDeclaration(true);// 结转单据状态：已转报关单
			this.transferFactoryManageDao.saveOrUpdate(transferFactoryBill);
			customsDeclaration = makeCustomsDeclaration(customsEnvelopBill,
					param, // 添加到报关单头
					listSerialNumber, transferFactoryBill);
			// List list = findAtcMergeAfterComInfoByListID(billList);
			List list = this.transferFactoryManageDao
					.findTransferFactoryCommodityInfo(transferFactoryBill
							.getId());

			double totalGrossWeight = 0.0;
			double totalNetWeight = 0.0;
			// 报关单参数
			CompanyOther co = (CompanyOther) transferFactoryManageDao
					.findCompanyOther().get(0);

			for (int j = 0; j < list.size(); j++) {
				if ((j != 0) && ((j + 1) % 20 == 0)) {
					customsDeclaration = makeCustomsDeclaration(
							customsEnvelopBill, param, listSerialNumber,
							transferFactoryBill);
				}
				transFactCommInfo = (TransferFactoryCommodityInfo) list.get(j);
				switch (customsEnvelopBill.getProjectType()) {
				case ProjectType.BCUS:
					commInfo = new CustomsDeclarationCommInfo();
					break;
				case ProjectType.BCS:
					commInfo = new BcsCustomsDeclarationCommInfo();
					break;
				case ProjectType.DZSC:
					commInfo = new DzscCustomsDeclarationCommInfo();
					break;
				}
				commInfo.setBaseCustomsDeclaration(customsDeclaration);
				commInfo.setSerialNumber(this
						.getCustomsDeclarationCommInfoSerialNumber(
								customsEnvelopBill.getProjectType(),
								customsDeclaration));
				commInfo.setCommSerialNo(transFactCommInfo.getSeqNum());
				commInfo.setComplex(transFactCommInfo.getComplex());
				commInfo.setCommName(transFactCommInfo.getCommName());
				commInfo.setCommSpec(transFactCommInfo.getCommSpec());
				commInfo.setUnit(transFactCommInfo.getUnit());
				commInfo.setCommAmount(transFactCommInfo.getTransFactAmount());
				commInfo.setLegalUnit(transFactCommInfo.getUnit());
				// commInfo.setFirstAmount(mergeAfterComInfo.getLegalAmount());
				// commInfo.setSecondLegalUnit(mergeAfterComInfo
				// ());
				// commInfo.setSecondAmount(mergeAfterComInfo
				// .getSecondLegalAmount());
				commInfo.setGrossWeight(transFactCommInfo.getGrossWeight());
				commInfo.setNetWeight(transFactCommInfo.getNetWeight());

				// 计算第一法定数量、第二法定数量:
				// 条件1：当申报单位与第一法定单位、第二法定单位相同时第一法定数量与第二法定数量等于申报数量
				// 条件2：当申报单位不等于千克，第一法定数量为千克，第二法定数量为千克时第一法定数量与第二法定数量等于净重
				// 条件3：当条件1与条件2不满足时，申报单位、第一法定单位，第二法定单位根据UnitCollate(计量单折算)实体得出相应的比例
				// 条件4: 当条件1与条件2、条件3都不满足时，申报单位、第一法定单位，第二法定单位根据帐册备的比例因子来计算
				if (transFactCommInfo.getComplex().getFirstUnit() != null) {
					String unitName = transFactCommInfo.getUnit() == null ? ""
							: transFactCommInfo.getUnit().getName();
					String firstUnitName = transFactCommInfo.getComplex()
							.getFirstUnit().getName();

					if (unitName.equals(firstUnitName)) {// 条件1
						commInfo.setFirstAmount(commInfo.getCommAmount());
					} else if ("千克".equals(firstUnitName)) {// 条件2
						commInfo.setFirstAmount(commInfo.getNetWeight());
					} else if (getUnitRateMap().get(
							unitName + "+" + firstUnitName) != null) {// 条件3
						Double unitRate = Double
								.parseDouble(getUnitRateMap().get(
										unitName + "+" + firstUnitName)
										.toString());
						commInfo.setFirstAmount(CommonUtils.getDoubleByDigit(
								transFactCommInfo.getTransFactAmount()
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								co.getCustomAmountNum()));
					}
				}

				if (transFactCommInfo.getComplex().getSecondUnit() != null) {
					// 当“计量单位”=“第二法定数量” 第二法定数量=数量
					String unitName = transFactCommInfo.getUnit() == null ? ""
							: transFactCommInfo.getUnit().getName();
					String secondUnitName = transFactCommInfo.getComplex()
							.getSecondUnit().getName();
					if (unitName.equals(secondUnitName)) {// 条件1
						commInfo.setSecondAmount(commInfo.getCommAmount());
					} else if ("千克".equals(secondUnitName)) {// 条件2
						commInfo.setSecondAmount(commInfo.getNetWeight());
					} else if (getUnitRateMap().get(
							unitName + "+" + secondUnitName) != null) {// 条件3
						Double unitRate = Double.parseDouble(getUnitRateMap()
								.get(unitName + "+" + secondUnitName)
								.toString());

						commInfo.setSecondAmount(CommonUtils.getDoubleByDigit(
								transFactCommInfo.getTransFactAmount()
										* CommonUtils
												.getDoubleExceptNull(unitRate),
								co.getCustomAmountNum()));
					}
				}

				totalGrossWeight = CaleUtil.add(totalGrossWeight,
						commInfo.getGrossWeight());
				totalNetWeight = CaleUtil.add(totalNetWeight,
						commInfo.getNetWeight());

				this.transferFactoryManageDao.saveOrUpdate(commInfo);
			}

			customsDeclaration.setGrossWeight(totalGrossWeight);
			customsDeclaration.setNetWeight(totalNetWeight);
			this.transferFactoryManageDao.saveOrUpdate(customsDeclaration);
		}
		return listSerialNumber;
	}

	public Map getUnitRateMap() {
		Map<String, Double> map = new HashMap<String, Double>();
		map.put("克+千克", 0.001);
		map.put("个+千个", 0.001);
		map.put("支+千支", 0.001);
		map.put("块+千块", 0.001);
		map.put("米+千米", 0.001);
		map.put("千克+克", 1000.0);
		map.put("千个+个", 1000.0);
		map.put("千支+支", 1000.0);
		map.put("千块+块", 1000.0);
		map.put("千米+米", 1000.0);
		return map;
	}

	public List findCustomsEnvelopCommodityInfoByScmCoc(Request request,
			ScmCoc scmcoc, Boolean isCustomer) {
		HashMap map = new HashMap();
		Set set = new HashSet();
		List<CustomsEnvelopBill> list = transferFactoryManageDao
				.findCustomsEnvelopBillBySomCoc(scmcoc);
		List<BillPriceMaintenance> bList = this.transferFactoryManageDao
				.findBillPriceMaintenanceByScmCoc(scmcoc, isCustomer);
		for (BillPriceMaintenance data : bList) {
			String key = data.getSeqNum().toString() + data.getPtName()
					+ data.getPtSpec();
			// + ((Integer) data.getProjectType()).toString();
			// set.add(key);
			set.add(key);
		}
		for (CustomsEnvelopBill customsEnvelopBill : list) {
			List<CustomsEnvelopCommodityInfo> rlist = this.transferFactoryManageDao
					.findCustomsEnvelopCommodityInfoBySomCoc(
							customsEnvelopBill, isCustomer);
			for (CustomsEnvelopCommodityInfo data : rlist) {
				String key = data.getSeqNum().toString() + data.getPtName()
						+ data.getPtSpec();
				// + (data.getCustomsEnvelopBill().getProjectType() == null ?
				// "0"
				// : data.getCustomsEnvelopBill().getProjectType()
				// .toString());
				if (set.contains(key)) {
					continue;
				}
				map.put(key, data);
			}
		}
		return new ArrayList(map.values());
	}

	public void delBillPriceMaintenance(List list) {
		for (int i = 0; i < list.size(); i++) {
			BillPriceMaintenance bpm = (BillPriceMaintenance) list.get(i);
			this.transferFactoryManageDao.delete(bpm);
		}
	}

	public List addBillPriceMaintence(List<CustomsEnvelopCommodityInfo> list,
			ScmCoc scmCoc, Boolean isCustomer) {
		List tempList = new ArrayList();
		for (CustomsEnvelopCommodityInfo ceci : list) {
			BillPriceMaintenance bpm = new BillPriceMaintenance();
			bpm.setSeqNum(ceci.getSeqNum());
			bpm.setComplex(ceci.getComplex());
			bpm.setPtName(ceci.getPtName());
			bpm.setPtSpec(ceci.getPtSpec());
			bpm.setCurr(ceci.getCurr());
			bpm.setUnitPrice(ceci.getUnitPrice());
			bpm.setIsCustomer(isCustomer);
			bpm.setScmCoc(scmCoc);
			bpm.setProjectType(ceci.getCustomsEnvelopBill() == null ? null
					: (ceci.getCustomsEnvelopBill().getProjectType() == null ? 100
							: ceci.getCustomsEnvelopBill().getProjectType()));
			bpm.setCompany(ceci.getCompany());
			this.transferFactoryManageDao.saveOrUpdate(bpm);
			tempList.add(bpm);
		}
		return tempList;
	}

	public void editBillPriceMaintenancePrice(ScmCoc scmCoc, Integer seqNum,
			String code, Double price, int projectType, Boolean isCustomer) {
		// 修改纸质手册转厂类型报关单表休中商品的单价
		if (projectType == ProjectType.BCS) {
			List<BaseCustomsDeclarationCommInfo> bcsList = this.transferFactoryManageDao
					.findBcsCustomsDeclarationCommInfo(scmCoc, seqNum, code);
			for (BaseCustomsDeclarationCommInfo data : bcsList) {
				data.setCommUnitPrice(price);
				data.setCommTotalPrice(data.getCommAmount() == null ? 0.0
						: data.getCommAmount() * price);
				this.transferFactoryManageDao.saveOrUpdate(data);
			}
		}
		// 修改电联网监管转厂类型报关单表休中商品的单价
		else if (projectType == ProjectType.BCUS) {
			List<BaseCustomsDeclarationCommInfo> bcusList = this.transferFactoryManageDao
					.findCustomsDeclarationCommInfo(scmCoc, seqNum, code);
			for (BaseCustomsDeclarationCommInfo data : bcusList) {
				data.setCommUnitPrice(price);
				data.setCommTotalPrice(data.getCommAmount() == null ? 0.0
						: data.getCommAmount() * price);
				this.transferFactoryManageDao.saveOrUpdate(data);
			}
		}
		// 修改电子手册转厂类型报关单表休中商品的单价
		else if (projectType == ProjectType.DZSC) {
			List<BaseCustomsDeclarationCommInfo> dascList = this.transferFactoryManageDao
					.findDzscCustomsDeclarationCommInfo(scmCoc, seqNum, code);
			for (BaseCustomsDeclarationCommInfo data : dascList) {
				data.setCommUnitPrice(price);
				data.setCommTotalPrice(data.getCommAmount() == null ? 0.0
						: data.getCommAmount() * price);
				this.transferFactoryManageDao.saveOrUpdate(data);
			}
		}
		// 修改关封表休中商品的单价
		List<CustomsEnvelopCommodityInfo> list = this.transferFactoryManageDao
				.findCustomsEnvelopCommodityInfoBySomCocSeqNumCode(scmCoc,
						seqNum, code, isCustomer);

		for (CustomsEnvelopCommodityInfo data : list) {
			data.setTotalPrice(data.getOwnerQuantity() == null ? 0.0 : data
					.getOwnerQuantity() * price);
			data.setUnitPrice(price);
			this.transferFactoryManageDao.saveOrUpdate(data);

		}
		// 修改转厂单据表休中商品的单价
		List<String> slist = this.transferFactoryManageDao
				.findCustomsEnvelopBillByNotEndCaseAndScmCoc(scmCoc, isCustomer);
		for (String billNo : slist) {
			List<TransferFactoryCommodityInfo> tlist = this.transferFactoryManageDao
					.findTransferFactoryCommodityInfoByBillNo(billNo, seqNum,
							code);
			for (TransferFactoryCommodityInfo data : tlist) {
				data.setTotalPrice(data.getQuantity() == null ? 0.0 : data
						.getQuantity() * price);
				data.setUnitPrice(price);
			}
		}
	}

	/**
	 * 根据单据号查询关封商品明细
	 * 
	 * @param customsEnvelopBillCode
	 * @return
	 */
	public List findTempTransferFactoryCommInfo(boolean isImport, ScmCoc scmCoc) {

		List returnList = new ArrayList();

		List list = transferFactoryManageDao.findTempTransferFactoryCommInfo(
				isImport, scmCoc);

		for (int i = 0; i < list.size(); i++) {

			// 自定义关封商品信息列表
			CustomsEnvelopCommodityInfo info = (CustomsEnvelopCommodityInfo) list
					.get(i);

			// 转厂数量
			double transedAmount = transferFactoryManageDao
					.findTransFactCommInfoAmount(info.getCustomsEnvelopBill()
							.getCustomsEnvelopBillNo(), info.getEmsNo(), info
							.getSeqNum(), null);

			double envelopAmount = 0.0;

			// 关封数量
			envelopAmount = transferFactoryManageDao
					.sumCustomsEnvelopCommodityAmount(info
							.getCustomsEnvelopBill().getCustomsEnvelopBillNo(),
							info.getEmsNo(), info.getSeqNum(), info
									.getCeseqNum());

			BigDecimal bigEnv = new BigDecimal(envelopAmount
					* Math.pow(10.0, 16));

			BigDecimal bigTran = new BigDecimal(transedAmount
					* Math.pow(10.0, 16));

			double temp = bigEnv.subtract(bigTran).movePointLeft(16)
					.doubleValue();

			info.setCurrentTransferQuantity(temp);

			returnList.add(info);
		}
		return returnList;
	}

	/**
	 * 转抄关封资料
	 * 
	 * @param customsEnvelopBill
	 *            要转抄的关封表头
	 * @param copyInfo
	 *            是否也要转抄表体
	 * @param billNo
	 *            关封号
	 * @return 新的申请单表头
	 */
	public CustomsEnvelopBill copyCustomsEnvelopBillAndCommodityInfo(
			CustomsEnvelopBill customsEnvelopBill, Boolean copyInfo,
			String billNo) {
		CustomsEnvelopBill newCustomsEnvelopBill = new CustomsEnvelopBill();
		/**
		 * 表头转抄内容:购销合同编号,是进货还是出货,审批海关,生效日期(今天),
		 * 有效截止日期(有效日期+90天),客户供应商,转厂公司,项目类型,账册/手册号, 备注,创建日期,结案,是否生效
		 */
		newCustomsEnvelopBill.setPurchaseAndSaleContractNo(customsEnvelopBill
				.getPurchaseAndSaleContractNo());
		newCustomsEnvelopBill.setIsImpExpGoods(customsEnvelopBill
				.getIsImpExpGoods());
		newCustomsEnvelopBill.setCustoms(customsEnvelopBill.getCustoms());
		newCustomsEnvelopBill.setBeginAvailability(new Date());
		newCustomsEnvelopBill.setCreateDate(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, 90);
		newCustomsEnvelopBill.setEndAvailability(cal.getTime());
		newCustomsEnvelopBill.setScmCoc(customsEnvelopBill.getScmCoc());
		newCustomsEnvelopBill.setTransCompany(customsEnvelopBill
				.getTransCompany());
		newCustomsEnvelopBill.setProjectType(customsEnvelopBill
				.getProjectType());
		newCustomsEnvelopBill.setEmsNo(customsEnvelopBill.getEmsNo());
		newCustomsEnvelopBill.setMemo(customsEnvelopBill.getMemo());
		newCustomsEnvelopBill.setIsEndCase(false);
		customsEnvelopBill.setIsAvailability(new Boolean(false));
		List arrayList = new ArrayList();
		if (copyInfo) {
			List paraList = transferFactoryManageDao
					.findCustomsEnvelopCommodityInfo(customsEnvelopBill.getId());
			for (int i = 0; i < paraList.size(); i++) {
				CustomsEnvelopCommodityInfo commInfo = (CustomsEnvelopCommodityInfo) paraList
						.get(i);
				CustomsEnvelopCommodityInfo newCommInfo = (CustomsEnvelopCommodityInfo) commInfo
						.clone();
				newCommInfo.setId(null);
				newCommInfo.setCustomsEnvelopBill(newCustomsEnvelopBill);
				arrayList.add(newCommInfo);
			}
		}
		transferFactoryManageDao.saveCustomsEnvelopBill(newCustomsEnvelopBill);
		if (!arrayList.isEmpty()) {
			transferFactoryManageDao.saveCustomsEnvelopCommodityInfo(arrayList);
		}
		return newCustomsEnvelopBill;
	}
}
