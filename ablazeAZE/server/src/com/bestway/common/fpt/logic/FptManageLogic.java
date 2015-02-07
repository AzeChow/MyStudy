package com.bestway.common.fpt.logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.contract.dao.ContractDao;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.dao.BcsImpExpRequestDao;
import com.bestway.bcs.contractexe.entity.BcsContractExeInfo;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclarationCommInfo;
import com.bestway.bcs.contractexe.logic.ContractExeLogic;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.dao.CasDao;
import com.bestway.bcus.cas.entity.TempBillDetail;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoBase;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoByMateriel;
import com.bestway.bcus.cas.specificontrol.entity.MakeBillCorrespondingInfoByProduct;
import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.dao.CountryCodeDao;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.CustomsDeclarationCommInfo;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.innermerge.dao.CommonBaseCodeDao;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.ProcExeProgress;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.constant.AppClass;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.FptProcessResult;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.dao.FptManageDao;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptAppItemAndOrderDetail;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.FptBillPriceMaintenance;
import com.bestway.common.fpt.entity.FptCancelBill;
import com.bestway.common.fpt.entity.FptDownData;
import com.bestway.common.fpt.entity.FptEmail;
import com.bestway.common.fpt.entity.FptInitBill;
import com.bestway.common.fpt.entity.FptInitBillItem;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.FptReceiptResult;
import com.bestway.common.fpt.entity.FptSignInfo;
import com.bestway.common.fpt.entity.MakeFptBillCustomsDeclaration;
import com.bestway.common.fpt.entity.MakeFptBillFromCasBill;
import com.bestway.common.fpt.entity.TempCasBillTOFptTOCustomsReport;
import com.bestway.common.fpt.entity.TempCustomsEmsInfo;
import com.bestway.common.fpt.entity.TempCustomsEnvelopCommInfo;
import com.bestway.common.fpt.entity.TempCustomsEnvelopRequestCommodityInfo;
import com.bestway.common.fpt.entity.TempCustomsEnvelopRequetBill;
import com.bestway.common.fpt.entity.TempFptAppItem;
import com.bestway.common.fpt.entity.TempFptAppParam;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyes;
import com.bestway.common.fpt.entity.TempFptAppSpareAnalyesDetail;
import com.bestway.common.fpt.entity.TempFptAppheadAndOrder;
import com.bestway.common.fpt.entity.TempFptApplySurplus;
import com.bestway.common.fpt.entity.TempFptBillDictItem;
import com.bestway.common.fpt.entity.TempFptBillExeInfo;
import com.bestway.common.fpt.entity.TempFptBillHead;
import com.bestway.common.fpt.entity.TempFptBillHeadImportFromExcel;
import com.bestway.common.fpt.entity.TempFptBillItem;
import com.bestway.common.fpt.entity.TempFptBillItemForMakeBGD;
import com.bestway.common.fpt.entity.TempFptEmail;
import com.bestway.common.fpt.entity.TempFptExeInfo;
import com.bestway.common.fpt.entity.TempTransferFactoryImpExpGoodsList;
import com.bestway.common.fpt.entity.TempTransferStatusStat;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.tools.entity.TempResultSet;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.dzsc.contractexe.entity.DzscContractExeInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclarationCommInfo;
import com.bestway.dzsc.contractexe.logic.DzscContractExeLogic;
import com.bestway.dzsc.dzscmanage.dao.DzscDao;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeData;
import com.bestway.invoice.entity.TempCustomsDelcarationInfo;
import com.bestway.jptds.common.AppException;

public class FptManageLogic {
	private FptManageDao fptManageDao = null;

	private EmsEdiTrDao emsEdiTrDao = null;

	private EncDao encDao = null;

	private CasDao casDao = null;

	private CommonBaseCodeDao commonCodeDao = null;

	private ContractDao contractDao = null;

	private FptMessageLogic fptMessageLogic = null;

	private DzscDao dzscDao = null;

	private ContractExeLogic contractExeLogic = null;

	private DzscContractExeLogic dzscContractExeLogic = null;

	private Map map = null;

	public FptManageAction fptManageAction = null;
	/**
	 * 进出口申请单DAO
	 */
	private BcsImpExpRequestDao bcsImpExpRequestDao = null;
	/**
	 * baseCodeDao
	 */
	private BaseCodeDao baseCodeDao = null;
	private CountryCodeDao countryCodeDao = null;

	public CountryCodeDao getCountryCodeDao() {
		return countryCodeDao;
	}

	public void setCountryCodeDao(CountryCodeDao countryCodeDao) {
		this.countryCodeDao = countryCodeDao;
	}

	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	public BcsImpExpRequestDao getBcsImpExpRequestDao() {
		return bcsImpExpRequestDao;
	}

	public void setBcsImpExpRequestDao(BcsImpExpRequestDao bcsImpExpRequestDao) {
		this.bcsImpExpRequestDao = bcsImpExpRequestDao;
	}

	public CommonBaseCodeDao getCommonCodeDao() {
		return commonCodeDao;
	}

	/**
	 * @return the dzscContractExeLogic
	 */
	public DzscContractExeLogic getDzscContractExeLogic() {
		return dzscContractExeLogic;
	}

	/**
	 * @param dzscContractExeLogic
	 *            the dzscContractExeLogic to set
	 */
	public void setDzscContractExeLogic(
			DzscContractExeLogic dzscContractExeLogic) {
		this.dzscContractExeLogic = dzscContractExeLogic;
	}

	/**
	 * @return the contractExeLogic
	 */
	public ContractExeLogic getContractExeLogic() {
		return contractExeLogic;
	}

	/**
	 * @param contractExeLogic
	 *            the contractExeLogic to set
	 */
	public void setContractExeLogic(ContractExeLogic contractExeLogic) {
		this.contractExeLogic = contractExeLogic;
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

	public FptManageDao getFptManageDao() {
		return fptManageDao;
	}

	public void setFptManageDao(FptManageDao transferFactoryManageDao) {
		this.fptManageDao = transferFactoryManageDao;
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

	public FptMessageLogic getFptMessageLogic() {
		return fptMessageLogic;
	}

	public void setFptMessageLogic(FptMessageLogic fptMessageLogic) {
		this.fptMessageLogic = fptMessageLogic;
	}

	/**
	 * 获得最大的单据号来自进出货标志
	 */
	public long getMaxBillNoByImpExpGoodsFlag(boolean impExpGoodsFlag) {
		List list = this.fptManageDao
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
	 * 获得关封申请单商品信息记录来自数据是否正确的检验
	 */
	public List findCustomsEnvelopRequestCommodityInfoByCheck(String parentId) {
		List newList = new ArrayList();
		List list = this.fptManageDao.findFptAppItems(parentId);
		for (int i = 0; i < list.size(); i++) {
			FptAppItem fptAppItem = (FptAppItem) list.get(i);
			if (fptAppItem.getQty() == null
					|| fptAppItem.getQty().doubleValue() <= 0) {
				newList.add(fptAppItem);
			}
		}
		return newList;
	}

	/**
	 * 获得结转单据记录来自数据是否正确的检验
	 */
	public List findTransferFactoryCommodityInfoByCheck(String parentId) {
		List newList = new ArrayList();
		List oldList = this.fptManageDao.findFptBillItemByParentId(parentId);

		for (int i = 0; i < oldList.size(); i++) {
			FptBillItem transferFactoryCommodityInfo = (FptBillItem) oldList
					.get(i);
			if (transferFactoryCommodityInfo.getQty() == null
					|| transferFactoryCommodityInfo.getQty().doubleValue() <= 0
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
	 * 增加单据表体自然序号1
	 */
	public Integer addBillListNoInteger(FptBillHead head) {
		Integer lsResult = 0;
		try {
			lsResult = fptManageDao.getMaxFptBillItemListNo(head) + 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 单据回执处理
	 * 
	 * @param head
	 * @param exingHead
	 * @return
	 */
	public String processFptBillHead(final FptBillHead head, List lsReturnFile) {
		String copBillNo = "";
		if (FptBusinessType.FPT_BILL.equals(head.getSysType())) {
			if (FptInOutFlag.OUT.equals(head.getBillSort())) {
				copBillNo = head.getIssueCopBillNo();
			} else {
				copBillNo = head.getReceiveCopBillNo();
			}
			return this.fptMessageLogic.processMessage(
					FptBusinessType.FPT_BILL, copBillNo,
					new FptProcessMessage() {
						// public void failureHandling(
						// TempCspReceiptResultInfo tempReceiptResult) {
						// backBillContract(head, exingHead);
						// }
						//
						// public void successHandling(
						// TempCspReceiptResultInfo tempReceiptResult) {
						// CspReceiptResult receiptResult = tempReceiptResult
						// .getReceiptResult();
						// effectiveFptBillHead(head, exingHead, receiptResult);
						// }

						@Override
						public void processHandling(
								CspReceiptResult receiptResult) {
							String chkMark = receiptResult.getChkMark();
							if (FptProcessResult.IMP_CENTDB_SUCCESSD
									.equals(chkMark)) {
								wrieteFptBillHeadSeqNo(head, receiptResult);
							} else if (FptProcessResult.CHECK_PASS_ALL
									.equals(chkMark)) {
								effectiveFptBillHead(head, receiptResult);
							} else if (FptProcessResult.IMP_CENTDB_FAIL
									.equals(chkMark)
									|| FptProcessResult.IN_STOCK_FAIL
											.equals(chkMark)
									|| FptProcessResult.IMP_CENTDB_FAIL
											.equals(chkMark)
									|| FptProcessResult.CHECK_BACK
											.equals(chkMark)
									|| FptProcessResult.QP_FAIL.equals(chkMark)) {
								backBillFptBillHead(head);
							}
						}
					}, lsReturnFile);
		} else if (FptBusinessType.FPT_BILL_BACK.equals(head.getSysType())) {
			if (FptInOutFlag.OUT.equals(head.getBillSort())) {
				copBillNo = head.getIssueCopBillNo();
			} else {
				copBillNo = head.getReceiveCopBillNo();
			}
			return this.fptMessageLogic.processMessage(
					FptBusinessType.FPT_BILL_BACK, copBillNo,
					new FptProcessMessage() {
						// public void failureHandling(
						// TempCspReceiptResultInfo tempReceiptResult) {
						// backBillContract(head, exingHead);
						// }
						//
						// public void successHandling(
						// TempCspReceiptResultInfo tempReceiptResult) {
						// CspReceiptResult receiptResult = tempReceiptResult
						// .getReceiptResult();
						// effectiveFptBillHead(head, exingHead, receiptResult);
						// }

						@Override
						public void processHandling(
								CspReceiptResult receiptResult) {
							String chkMark = receiptResult.getChkMark();
							if (FptProcessResult.IMP_CENTDB_SUCCESSD
									.equals(chkMark)) {
								wrieteFptBillHeadSeqNo(head, receiptResult);
							} else if (FptProcessResult.CHECK_PASS_ALL
									.equals(chkMark)) {
								effectiveFptBillHead(head, receiptResult);
							} else if (FptProcessResult.IMP_CENTDB_FAIL
									.equals(chkMark)
									|| FptProcessResult.IN_STOCK_FAIL
											.equals(chkMark)
									|| FptProcessResult.IMP_CENTDB_FAIL
											.equals(chkMark)
									|| FptProcessResult.CHECK_BACK
											.equals(chkMark)
									|| FptProcessResult.QP_FAIL.equals(chkMark)) {
								backBillFptBillHead(head);
							}

						}
					}, lsReturnFile);
		}
		return null;
	}

	/**
	 * 单据 备案退单
	 * 
	 * @param contract
	 *            合同备案表头
	 * @param exingContract
	 *            变更后合同备案表头
	 */
	private void backBillFptBillHead(FptBillHead head) {
		head.setAppState(DeclareState.APPLY_POR);
		this.fptManageDao.saveFptBillHead(head);
	}

	private void wrieteFptBillHeadSeqNo(FptBillHead head,
			CspReceiptResult receiptResult) {
		if (receiptResult != null) {
			if (FptInOutFlag.OUT.equals(head.getBillSort())
					&& FptBusinessType.FPT_BILL.equals(head.getSysType())
					|| FptInOutFlag.IN.equals(head.getBillSort())
					&& FptBusinessType.FPT_BILL_BACK.equals(head.getSysType())) {
				head.setSeqNo(receiptResult.getSeqNo());
			}
		}
		this.fptManageDao.saveFptBillHead(head);

	}

	/**
	 * 单据合同数据
	 * 
	 * @param contract
	 *            单据表头
	 */
	private void effectiveFptBillHead(FptBillHead head,
			CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapImg = new HashMap<Integer, Complex>();
		head.setAppState(DeclareState.PROCESS_EXE);
		if (receiptResult != null) {
			// if (FptBusinessType.FPT_BILL.equals(head.getSysType())) {
			// if (FptInOutFlag.OUT.equals(head.getBillSort())) {
			// head.setSeqNo(receiptResult.getSeqNo());
			// head.setBillNo(((FptReceiptResult) receiptResult)
			// .getCustomsNo());
			// }
			// } else {
			// if (FptInOutFlag.IN.equals(head.getBillSort())) {
			// head.setBillNo(((FptReceiptResult) receiptResult)
			// .getCustomsNo());
			// }
			// }
			if (FptInOutFlag.OUT.equals(head.getBillSort())
					&& FptBusinessType.FPT_BILL.equals(head.getSysType())) {
				head.setSeqNo(receiptResult.getSeqNo());
				head.setBillNo(((FptReceiptResult) receiptResult)
						.getCustomsNo());
			} else if (FptInOutFlag.IN.equals(head.getBillSort())
					&& FptBusinessType.FPT_BILL_BACK.equals(head.getSysType())) {
				head.setBillNo(((FptReceiptResult) receiptResult)
						.getCustomsNo());
				head.setSeqNo(receiptResult.getSeqNo());
			}

		}
		this.fptManageDao.saveFptBillHead(head);
	}

	/**
	 * 获得关封申请单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findTempCustomsEnvelopRequestBillByScmCoc(String scmCocId) {
		List newList = new ArrayList();
		List oldList = this.fptManageDao
				.findCustomsEnvelopRequestBillByScmCocToCEB(scmCocId);
		for (int i = 0; i < oldList.size(); i++) {
			FptAppHead c = (FptAppHead) oldList.get(i);
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
		List oldList = this.fptManageDao
				.findCustomsEnvelopRequestCommodityInfoByParent(parentList);
		for (int i = 0; i < oldList.size(); i++) {
			FptAppItem c = (FptAppItem) oldList.get(i);
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
		List emsList = this.fptManageDao.findEmsEdiMergerExgBeforeByEms(emsH2k,
				emsEdiMergerHead);
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
			// Object ptNo = map.get(t.getC().getMateriel().getPtNo().trim()
			// .toUpperCase());
			// if (ptNo == null) {
			// newList.add(t);
			// }
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
		List emsList = this.fptManageDao.findEmsEdiMergerImgBeforeByEms(emsH2k,
				emsEdiMergerHead);
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
			// Object ptNo = map.get(t.getC().getMateriel().getPtNo().trim()
			// .toUpperCase());
			// if (ptNo == null) {
			// newList.add(t);
			// }
		}
		return newList;
	}

	/**
	 * 修改关封申请单的关封单据字段,和生成关封单据字段为true
	 */
	public List updateCustomsEnvelopRequestBill(String customsEnvelopBillId,
			List dataSource) {
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			FptAppHead data = ((TempCustomsEnvelopRequetBill) dataSource.get(i))
					.getCustomsEnvelopRequestBill();
			// data.setIsMakeCustomsEnvelop(new Boolean(true));
			this.fptManageDao.saveFptAppHead(data);
			list.add(data);
		}
		return list;
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
			FptAppItem c = (FptAppItem) list.get(i);
			// if (c.getIsTranCustomsEnvelop() != null) {
			// if (c.getIsTranCustomsEnvelop().booleanValue() == true) {
			// isFlag = false;
			// break;
			// }
			// }
		}
		return isFlag;
	}

	/**
	 * 新增结转单据表头
	 * 
	 * @return
	 */
	public FptBillHead newFptBillHead(String fptInOutFlag,
			String fptBusinessType) {// Integer inorOut, Integer billType
		FptBillHead head = new FptBillHead();
		Company com = this.dzscDao.findCompany();
		FptParameterSet parameterSet = this.fptManageDao.findFptParameterSet();
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		if (parameterSet.getProjectType() == null) {
			stringBuffer.append("请到转厂管理参数设置中,先设置项目类型\r\n");
		}
		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}
		head.setIssueTradeCod(com.getCode());
		head.setIssueTradeName(com.getName());
		head.setBillSort(fptInOutFlag);
		// if (FptInOutFlag.OUT.equals(fptInOutFlag)) {// 转出
		// head.setIssueTradeCod(com.getCode());
		// head.setIssueTradeName(com.getName());
		// head.setBillSort(FptInOutFlag.OUT);
		// } else {
		// head.setReceiveTradeCod(com.getCode());
		// head.setReceiveTradeName(com.getName());
		// head.setBillSort(FptInOutFlag.IN);
		// }
		if (FptBusinessType.FPT_BILL.equals(fptBusinessType)) {// index==0表示收发货标志
			// 当==1表示退货标志
			if (FptInOutFlag.OUT.equals(fptInOutFlag)) {// 转出
				head.setIssueCopBillNo(this.fptMessageLogic
						.getNewCopEntNoFptBill("FptBillHead", "issueCopBillNo",
								"F", FptBusinessType.FPT_BILL, FptInOutFlag.OUT));
			} else {
				head.setReceiveCopBillNo(this.fptMessageLogic
						.getNewCopEntNoFptBill("FptBillHead",
								"receiveCopBillNo", "F",
								FptBusinessType.FPT_BILL, FptInOutFlag.IN));
			}
			head.setSysType(FptBusinessType.FPT_BILL);
		} else {
			if (FptInOutFlag.OUT.equals(fptInOutFlag)) {// 转出
				head.setIssueCopBillNo(this.fptMessageLogic
						.getNewCopEntNoFptBill("FptBillHead", "issueCopBillNo",
								"F", FptBusinessType.FPT_BILL_BACK,
								FptInOutFlag.OUT));
			} else {
				head.setReceiveCopBillNo(this.fptMessageLogic
						.getNewCopEntNoFptBill("FptBillHead",
								"receiveCopBillNo", "F",
								FptBusinessType.FPT_BILL_BACK, FptInOutFlag.IN));
			}
			head.setSysType(FptBusinessType.FPT_BILL_BACK);
		}
		head.setAppState(String.valueOf(DeclareState.APPLY_POR));
		head.setCompany(com);
		// head.setInputDate(new Date());
		head.setAclUser(CommonUtils.getAclUser());
		head.setProjectType(parameterSet.getProjectType());
		List listNum = fptManageDao.findFptBillHeadSerialNumber(fptInOutFlag);
		if(listNum.size()>0&&listNum.get(0)!=null){
			head.setSerialNumber(Integer.parseInt(listNum.get(0).toString())+1);
		}else{
			head.setSerialNumber(1);
		}
		return this.fptManageDao.saveFptBillHead(head);
	}

	/**
	 * 变更
	 */
	public FptBillHead changingFptBill(FptBillHead fptBillHead) {
		FptBillHead c = new FptBillHead();
		try {
			PropertyUtils.copyProperties(c, fptBillHead);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/**
		 * 单据申报状态
		 */
		String declareState = c.getAppState();
		if (!DeclareState.PROCESS_EXE.equals(declareState)) {
			return null;
		}
		/**
		 * 存在已变更的记录
		 */
		FptBillHead fptbillheadTemp = this.fptManageDao.findfptBillHeadByEmsNo(
				c.getIssueCopBillNo(), DeclareState.CHANGING_EXE,
				c.getBillSort(), c.getSysType());
		if (fptbillheadTemp != null) {
			return null;
		}
		/**
		 * 存在已变更的记录
		 */
		FptBillHead fptbillheadTempa = this.fptManageDao
				.findfptBillHeadByEmsNo(c.getIssueCopBillNo(),
						DeclareState.APPLY_POR, c.getBillSort(), c.getSysType());
		if (fptbillheadTempa != null) {
			return null;
		}
		/**
		 * 单据表头Id
		 */
		String fptbillheadId = c.getId();
		/**
		 * 生成新的合同表头
		 */
		c.setId(null);
		c.setAppState(String.valueOf(DeclareState.CHANGING_EXE));
		fptManageDao.saveFptBillHead(c);
		Map<Integer, String> fptBillItemMap = new HashMap<Integer, String>();

		/**
		 * 查找发货单 来自 ID
		 */
		List fptBillItemList = this.fptManageDao
				.findFptBillItemByFptbillheadId(fptbillheadId, c.getBillSort());
		for (int j = 0; j < fptBillItemList.size(); j++) {
			FptBillItem fptBillItem = new FptBillItem();
			try {
				PropertyUtils.copyProperties(fptBillItem,
						(FptBillItem) fptBillItemList.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			/**
			 * 发货单
			 */
			fptBillItem.setId(null);
			fptBillItem.setFptBillHead(c);
			this.fptManageDao.saveFptBillItem(fptBillItem);
			//
			// 存入新的料件ID用于排序和变更时用
			//
			if (fptBillItem.getListNo() != null) {
				fptBillItemMap
						.put(fptBillItem.getListNo(), fptBillItem.getId());
			}
		}
		return c;
	}

	/**
	 * 转抄明细
	 */
	public List copyFptBillExpImpDetail(List<FptBillItem> list, FptBillHead head) {
		List<FptBillItem> lsResult = new ArrayList<FptBillItem>();
		Map<Integer, String> FptBillItemMap = new HashMap<Integer, String>();
		try {
			for (int i = 0; i < list.size(); i++) {
				FptBillItem c = (FptBillItem) BeanUtils.cloneBean(list.get(i));
				String fptbillItemId = c.getId();
				c.setListNo(fptManageDao.getMaxFptBillItemListNo(head) + i + 1);
				/**
				 * 转抄发货单
				 */
				c.setId(null);
				c.setTradeQty(0.0);
				c.setQty(0.0);

				this.fptManageDao.saveFptBillItem(c);
				//
				// 存入新的料件ID用于排序和变更时用
				//
				if (c.getListNo() != null) {
					FptBillItemMap.put(c.getListNo(), c.getId());
				}
				lsResult.add(c);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	// /**
	// * 判断归并后的资料是否是按申请表序号与交易单位来进行合并
	// *
	// * @author ower
	// *
	// */
	// public boolean isMergerCheckFptBillDictItem(String inOutFlag,
	// String parentId, String sysType) {
	// List<String> lsSeqNum = new ArrayList<String>();
	// List list = this.fptManageDao.findSumFptBillItemCommodityInfo(parentId,
	// inOutFlag, sysType);
	// for (int i = 0; i < list.size(); i++) {
	// Object[] c = (Object[]) list.get(i);
	//
	// if (lsSeqNum.contains(String.valueOf(c[0]) + "/" + c[5])) {
	// System.out.println("fdfd=" + String.valueOf(c[0]) + "/" + c[5]);
	// // System.out.println(lsSeqNum.set(index, element));
	// return true;
	// } else {
	// lsSeqNum.add(String.valueOf(c[0]) + "/" + c[5]);
	// }
	// }
	// return false;
	// }

	/**
	 * 查询结转单据中归并后的商品信息
	 * 
	 * @param request
	 * @param parentId
	 * @return
	 */
	public List findFptBillDictItemCommodityInfo(String parentId,
			String inOutFlag, String sysType) {
		// List lsResult = new ArrayList();
		// List ls = fptManageDao.findSumFptBillItemCommodityInfo(parentId,
		// inOutFlag, sysType);
		// for (int i = 0; i < ls.size(); i++) {
		// Object[] c = (Object[]) ls.get(i);
		// TempFptBillDictItem dictItem = new TempFptBillDictItem();
		// dictItem.setListNo(i + 1);
		// dictItem.setAppGNo((Integer) c[0]);
		// dictItem.setTrGno((Integer) c[1]);
		// dictItem.setComplex((Complex) fptManageDao
		// .findComplexByCode((String) c[2]));
		// dictItem.setCommName((String) c[3]);
		// dictItem.setCommSpec((String) c[4]);
		// dictItem.setTradeUnit((Unit) fptManageDao
		// .findUnitByName((String) c[5]));
		// dictItem.setTradeQty((Double) c[6]);
		// dictItem.setUnit((Unit) fptManageDao.findUnitByName((String) c[7]));
		// dictItem.setQty((Double) c[8]);
		// dictItem.setInEmsNo((String) c[9]);
		// if ((FptInOutFlag.IN.equals(inOutFlag) && FptBusinessType.FPT_BILL
		// .equals(sysType))
		// || (FptInOutFlag.OUT.equals(inOutFlag) &&
		// FptBusinessType.FPT_BILL_BACK
		// .equals(sysType))) {
		// dictItem.setOutNo((Integer) c[10]);
		// }
		// lsResult.add(dictItem);
		// }
		// return lsResult;
		return this.getFptBillDictItem(parentId, inOutFlag, sysType);
	}

	/**
	 * 查询结转单据流水号
	 * @return
	 */
	public List findFptBillHeadSerialNumber(String fptInOutFlag){
		return this.fptManageDao.findFptBillHeadSerialNumber(fptInOutFlag);
	}
	
	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */
	public List copyFptBillHead(List<FptBillHead> list, Integer isExg,
			Integer inorOut) {
		List<FptBillHead> lsResult = new ArrayList<FptBillHead>();
		try {
			for (int i = 0; i < list.size(); i++) {
				FptBillHead c = (FptBillHead) BeanUtils.cloneBean(list.get(i));
				String fptbillheadId = c.getId();
				String CopBillNo = "";
				if (isExg == 0) {// 成品0
					if (inorOut == 0) {//成品发货单			
						CopBillNo = this.fptMessageLogic.getNewCopEntNoFptBill(
								"FptBillHead", "issueCopBillNo", "F",
								FptBusinessType.FPT_BILL,
								String.valueOf(FptInOutFlag.OUT));
						c.setIssueCopBillNo(CopBillNo);
						c.setIssueIsDeclaDate(null);
//						c.setReceiveCopBillNo(c.getReceiveCopBillNo()); // 发货企业内部编号
						c.setReceiveCopBillNo(null); // 发货企业内部编号
						c.setReceiveIsDeclaDate(null);// 发货申报时间
						c.setReceiveAgentCode(c.getReceiveAgentCode());// 发货申报企业9位组织机构代码
						c.setReceiveAgentName(c.getReceiveAgentName());// 发货申报企业组织机构名称
						c.setReceiveNote(null); // 备注
						c.setReceiveDate(null); // 发货期
						c.setReceiveIsDeclaEm(null); // 发货人
					} else {//成品退货单
						
						CopBillNo = this.fptMessageLogic.getNewCopEntNoFptBill(
								"FptBillHead", "issueCopBillNo", "F",
								FptBusinessType.FPT_BILL_BACK,
								String.valueOf(FptInOutFlag.OUT));
						c.setReceiveIsDeclaDate(null);
						c.setReceiveCopBillNo(null);
						// 去掉转出的资料
						c.setIssueCopBillNo(CopBillNo); // 发货企业内部编号
						c.setIssueIsDeclaDate(null);// 发货申报时间
//						c.setIssueAgentCode(null);// 发货申报企业9位组织机构代码
//						c.setIssueAgentName(null);// 发货申报企业组织机构名称
						c.setReceiveAgentCode(c.getReceiveAgentCode());// 发货申报企业9位组织机构代码
						c.setReceiveAgentName(c.getReceiveAgentName());// 发货申报企业组织机构名称
						c.setIssueNote(null); // 备注
//						c.setIssueDate(null); // 发货期
//						c.setIssueIsDeclaEm(null); // 发货人
						c.setReceiveDate(null); // 发货期
						c.setReceiveIsDeclaEm(null); // 发货人
					}
				} else {//料件1
					if (inorOut == 0) {//料件收货单
						CopBillNo = this.fptMessageLogic.getNewCopEntNoFptBill("FptBillHead",
								"receiveCopBillNo", "F",FptBusinessType.FPT_BILL, FptInOutFlag.IN);
						
						c.setIssueIsDeclaDate(null);
						c.setIssueCopBillNo(null);
						// 去掉转入的资料
//						c.setReceiveCopBillNo(c.getReceiveAgentCode()); // 发货企业内部编号
						c.setReceiveCopBillNo(CopBillNo); // 发货企业内部编号
						c.setReceiveIsDeclaDate(null);// 发货申报时间
						c.setReceiveAgentCode(c.getReceiveAgentCode());// 发货申报企业9位组织机构代码
						c.setReceiveAgentName(c.getReceiveAgentName());// 发货申报企业组织机构名称
						c.setReceiveNote(null); // 备注
//						c.setReceiveDate(null); // 发货期
//						c.setReceiveIsDeclaEm(null); // 发货人
						c.setIssueDate(null); // 发货期
						c.setIssueIsDeclaEm(null); // 发货人
					} else {//料件退货单
						CopBillNo = this.fptMessageLogic
								.getNewCopEntNoFptBill("FptBillHead",
										"receiveCopBillNo", "F",
										FptBusinessType.FPT_BILL_BACK, FptInOutFlag.IN);
						c.setReceiveIsDeclaDate(null);
						c.setReceiveCopBillNo(CopBillNo);
						// 去掉转出的资料
						c.setIssueCopBillNo(null); // 发货企业内部编号
						c.setIssueIsDeclaDate(null);// 发货申报时间
						c.setIssueAgentCode(null);// 发货申报企业9位组织机构代码
						c.setIssueAgentName(null);// 发货申报企业组织机构名称
						c.setIssueNote(null); // 备注
						c.setIssueDate(null); // 发货期
						c.setIssueIsDeclaEm(null); // 发货人
					}
				}
				/**
				 * 转抄单据头
				 */
				c.setId(null);
				c.setBillNo(null);
				c.setSeqNo(null);
				c.setMakeCustomsDeclarationCode(null);
				c.setAclUser(CommonUtils.getAclUser());
				c.setAppState(DeclareState.APPLY_POR);
				this.fptManageDao.saveFptBillHead(c);

				/**
				 * 查找发货单 来自ID
				 */
				Map<Integer, String> FptBillItemMap = new HashMap<Integer, String>();
				List FptBillItemList = this.fptManageDao
						.findFptBillItemByFptbillheadId(fptbillheadId,
								String.valueOf(isExg));

				for (int j = 0; j < FptBillItemList.size(); j++) {
					FptBillItem fptBillItem = (FptBillItem) BeanUtils
							.cloneBean(FptBillItemList.get(j));
					/**
					 * 转抄发货单
					 */
					fptBillItem.setId(null);
					fptBillItem.setIsCustomsDeclaration(false);
					fptBillItem.setFptBillHead(c);
					fptBillItem.setTradeQty(0.0);
					fptBillItem.setQty(0.0);
					this.fptManageDao.saveFptBillItem(fptBillItem);
					//
					// 存入新的料件ID用于排序和变更时用
					//
					if (fptBillItem.getListNo() != null) {
						FptBillItemMap.put(fptBillItem.getListNo(),
								fptBillItem.getId());
					}
				}
				lsResult.add(c);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}
	

	
	/**
	 * 保存转厂初始化单据
	 */
	public void saveTransferFactoryInitBill(FptInitBill transferFactoryInitBill) {
		this.fptManageDao.saveTransferFactoryInitBill(transferFactoryInitBill);
	}

	/**
	 * 保存转厂初始化单据
	 */
	public FptInitBill saveTransferFactoryInitBillCommodityInfo(
			FptInitBill initBill, List list) {
		FptInitBillItem initBillCommodityInfo = null;
		for (int i = 0; i < list.size(); i++) {
			initBillCommodityInfo = (FptInitBillItem) list.get(i);
			this.fptManageDao
					.saveTransferFactoryInitBillCommodityInfo(initBillCommodityInfo);
		}
		initBill = this.fptManageDao.findTransferFactoryInitBillById(initBill);
		initBill.setItemCount(this.getTransferInitBillCommInfoCount(initBill));
		this.saveTransferFactoryInitBill(initBill);
		return initBill;
	}

	/**
	 * 保存转厂初始化单据商品信息
	 */
	public void saveTransferFactoryInitBillCommodityInfo(
			FptInitBillItem transferFactoryInitBillCommodityInfo) {
		this.fptManageDao
				.saveTransferFactoryInitBillCommodityInfo(transferFactoryInitBillCommodityInfo);
	}

	/**
	 * 删除转厂初始化单据
	 */
	public void deleteTransferFactoryInitBill(
			FptInitBill transferFactoryInitBill) {
		FptInitBillItem commInfo = null;
		List list = this.fptManageDao
				.findTransferFactoryInitCommodityInfo(transferFactoryInitBill
						.getId());
		this.deleteTransferFactoryInitBillCommodityInfo(list);
		this.fptManageDao
				.deleteTransferFactoryInitBill(transferFactoryInitBill);
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	private void deleteTransferFactoryInitBillCommodityInfo(List list) {
		FptInitBillItem commInfo = null;
		for (int i = 0; i < list.size(); i++) {
			commInfo = (FptInitBillItem) list.get(i);
			this.fptManageDao
					.deleteTransferFactoryInitBillCommodityInfo(commInfo);
		}
	}

	/**
	 * 删除转厂初始化单据商品信息
	 */
	public void deleteTransferFactoryInitBillCommodityInfo(
			FptInitBill initBill, List list) {
		FptInitBillItem commInfo = null;
		this.deleteTransferFactoryInitBillCommodityInfo(list);
		initBill.setItemCount(this.getTransferInitBillCommInfoCount(initBill));
		this.saveTransferFactoryInitBill(initBill);
	}

	private Integer getTransferInitBillCommInfoCount(FptInitBill initBill) {
		List list = this.findTransferFactoryInitCommodityInfo(initBill.getId());
		return new Integer(list.size());
	}

	/**
	 * 获得所有初始化结转单据
	 */
	public List findTransferFactoryInitBill() {
		return this.fptManageDao.findTransferFactoryInitBill();
	}

	/**
	 * 获得结转初始化结转单据-进货,或者出货
	 */
	public List findTransferFactoryInitBill(boolean isImpExpFlag) {
		return this.fptManageDao.findTransferFactoryInitBill(isImpExpFlag);
	}

	/**
	 * 取得最大转厂初始化单据号码+1
	 * 
	 * @return
	 */
	public String getTransferFactoryInitBillMaxCode() {
		return fptManageDao.getTransferFactoryInitBillMaxCode();
	}

	/**
	 * 获得转厂初始化单据信息加载子表的记录
	 */
	public List findTransferFactoryInitCommodityInfo(String parentId) {
		return this.fptManageDao.findTransferFactoryInitCommodityInfo(parentId);
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、未转关封的单据
	 */
	public List findFptBillMakeCustomsDeclaration(String inOutFlag,
			String appNo, Date beginDate, Date endDate) {
		List list = this.fptManageDao.findFptBillMakeCustomsDeclaration(
				inOutFlag, appNo, beginDate, endDate);
		return list;
	}

	/**
	 * 获得转厂单据来自选定用客户，且生效、存在未转报关清单的商品 的单据
	 */
	public List findTempTransferFactoryBillByScmCocNotATC(String scmCocId,
			String emsNo) {
		List newList = new ArrayList();
		List oldList = this.fptManageDao
				.findTransferFactoryBillByScmCocIdToATC(scmCocId, emsNo);
		for (int i = 0; i < oldList.size(); i++) {
			FptBillHead t = (FptBillHead) oldList.get(i);
			TempFptBillHead temp = new TempFptBillHead();
			temp.setT(t);
			temp.setIsSelected(new Boolean(false));
			newList.add(temp);
		}
		return newList;
	}

	/**
	 * 获得转厂单据商品信息来自父对象List
	 */
	public List findMakeSummaryFptBillCommodityInfoByParent(List parentList,
			String inOutFlag) {
		List newList = new ArrayList();
		List oldList = this.fptManageDao.findFptBillCommodityInfoByParent(
				parentList, inOutFlag);
		return oldList;
	}

	public List findFptBillCommodityInfoByHeadItem(List parentList,
			String sysType, String inOutFlag) {
		List oList = this.fptManageDao.findFptBillCommodityInfoByHeadItem(
				parentList, sysType, inOutFlag);
		return oList;
	}

	/**
	 * 拆分结转单据，将结转单据没有报关的数量，生成一个新的结转单据
	 * 
	 * @param transferFactoryBill
	 * @return
	 */
	public FptBillHead splitTransferFactoryBill(FptBillHead transferFactoryBill) {
		FptBillHead newTransferFactoryBill = new FptBillHead();
		List list = this.fptManageDao
				.findFptBillItemByParentId(transferFactoryBill.getId());
		int remainCount = 0;
		try {
			PropertyUtils.copyProperties(newTransferFactoryBill,
					transferFactoryBill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		newTransferFactoryBill.setId(null);
		newTransferFactoryBill.setIsCustomsDeclaration(false);
		this.fptManageDao.saveFptBillHead(newTransferFactoryBill);
		for (int i = 0; i < list.size(); i++) {
			FptBillItem oldCommInfo = (FptBillItem) list.get(i);
			double remainAmount = 0;
			if (remainAmount > 0) {
				FptBillItem newCommInfo = new FptBillItem();
				try {
					PropertyUtils.copyProperties(newCommInfo, oldCommInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				newCommInfo.setId(null);
				newCommInfo.setTransferFactoryBill(newTransferFactoryBill);
				this.fptManageDao.saveFptBillItem(newCommInfo);
				remainCount++;
			}
		}
		// if (remainCount <= 0) {
		// throw new RuntimeException("此结转单据:"
		// + transferFactoryBill.getTransferFactoryBillNo()
		// + "的数量已全部转厂，不能再拆分新的结转单据");
		// }

		return newTransferFactoryBill;
	}

	public void saveFptBillHead(FptBillHead transferFactoryBill) {
		// 暂时没有商品项数
		// transferFactoryBill.setItemCount(transferFactoryManageDao
		// .findTransferFactoryCommodityInfoCount(transferFactoryBill
		// .getId()));
		fptManageDao.saveFptBillHead(transferFactoryBill);
	}

	/**
	 * 判断内部编号号是否重复
	 * 
	 * @param head
	 * 
	 * @return boolean 手册号是否重复，true为重复
	 */
	public boolean checkFptBillEmsBillNoDuple(String fptInOutFlag,
			String fptBusinessType, FptBillHead head) {
		List list = this.fptManageDao.findFptBillHeadByCopBillNo(fptInOutFlag,
				fptBusinessType, head.getIssueCopBillNo());
		for (int i = 0; i < list.size(); i++) {
			FptBillHead hd = (FptBillHead) list.get(i);
			if (head.getIssueCopBillNo().equals(hd.getIssueCopBillNo())
					&& !head.getId().equals(hd.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断申请单编号是否存在
	 */
	public boolean checkAppNoInFptAppHead(String fptInOutFlag, String appNo) {
		List list = this.fptManageDao.findFptAppHeadAppNo(fptInOutFlag, appNo);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 保存结转单据商品信息数据
	 */
	public void saveFptBillItem(FptBillItem commInfo) {
		this.fptManageDao.saveFptBillItem(commInfo);
	}

	/**
	 * 查找结转单据商品的可待转数量还有多少
	 */
	public double findTransferFactoryCommodityInfoLeft(FptBillItem commInfo) {
		// double transedAmount = this.transferFactoryManageDao
		// .findTransFactCommInfoAmount(commInfo);
		double envelopAmount = 0.0;
		// CustomsEnvelopCommodityInfo envelopCommInfo =
		// this.transferFactoryManageDao
		// .findCustomsEnvelopCommodityInfo(commInfo
		// .getTransferFactoryBill().getAppNo(), commInfo
		// .getEmsNo(), commInfo.getListNo());
		// if (envelopCommInfo != null) {
		// envelopAmount = CommonUtils.getDoubleExceptNull(envelopCommInfo
		// .getOwnerQuantity());
		// }
		// BigDecimal bigEnv = new BigDecimal(envelopAmount * Math.pow(10.0,
		// 16));
		// BigDecimal bigTran = new BigDecimal(transedAmount * Math.pow(10.0,
		// 16));

		// double temp =
		// bigEnv.subtract(bigTran).movePointLeft(16).doubleValue();
		return 0.0;

		// return (envelopAmount - transedAmount);
	}

	/**
	 * 修改结转单据的关封申请单字段,和生成关封申请单据字段为true -->来自关封申请单
	 */
	public List updateTransferFactoryBillByCER(
			String customsEnvelopRequestBillId, List dataSource) {
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			FptBillHead data = ((TempFptBillHead) dataSource.get(i)).getT();
			// data.setCustomsEnvelopRequestBillId(customsEnvelopRequestBillId);
			this.fptManageDao.saveFptBillHead(data);
			list.add(data);
		}
		return list;
	}

	/**
	 * 删除关封单据
	 */
	public void deleteFptAppHead(FptAppHead fptAppHead) {
		String customsEnvelopRequestBillId = fptAppHead.getId();
		List list = this.fptManageDao
				.findFptAppItems(customsEnvelopRequestBillId);
		for (int i = 0; i < list.size(); i++) {
			FptAppItem data = (FptAppItem) list.get(i);
			// this.writeBackTransferFactoryBillToCustomsBillRequestId(data
			// .getId());
			this.fptManageDao.deleteFptAppItem(data);
		}
		this.fptManageDao.deleteFptAppHead(fptAppHead);
	}

	/**
	 * 删除关封申请单商品信息数据
	 */
	public void deleteFptAppItem(List list) {
		for (int i = 0; i < list.size(); i++) {
			FptAppItem data = (FptAppItem) list.get(i);
			this.fptManageDao.deleteFptAppItem(data);

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
			FptBillItem t = (FptBillItem) list.get(i);
		}
		return isFlag;
	}

	/**
	 * 修改结转单据的报关清单字段,和生成报关清单据字段为true -->来自报关清单Id
	 */
	public List updateTransFactBillByCustomsBillListId(
			String customsBillListId, List dataSource) {
		List list = new ArrayList();
		for (int i = 0; i < dataSource.size(); i++) {
			FptBillHead data = ((TempFptBillHead) dataSource.get(i)).getT();
			// data.setApplyToCustomsBillId(applyToCustomsBillId);
			this.fptManageDao.saveFptBillHead(data);
			list.add(data);
		}
		return list;
	}

	/**
	 * 由单据中心单据生成结转单据
	 */
	public FptBillHead makeFptBillFromCasBill(List billDetailList,
			String casBillCode, FptAppHead fptAppHead, String emsNo,
			boolean isComplex, boolean isNewFptBillHead,
			FptBillHead oldFptBillHead) {
		FptBillHead fptBillHead = null;
		if (isNewFptBillHead) {
			fptBillHead = this.makeFptBillHead(casBillCode, fptAppHead);
		} else {
			fptBillHead = oldFptBillHead;
		}
		if (fptBillHead == null) {
			throw new RuntimeException("结转收发货单的表头为空!");
		}
		this.fptManageDao.saveFptBillHead(fptBillHead);
		List<BillDetail> lsBillDetail = new ArrayList<BillDetail>();
		for (int i = 0; i < billDetailList.size(); i++) {
			BillDetail billDetail = ((TempBillDetail) billDetailList.get(i))
					.getBillDetail();
			lsBillDetail.add(billDetail);
		}
		this.makeFptBillItemInfo(lsBillDetail, fptBillHead, fptAppHead, emsNo,
				isComplex);
		return fptBillHead;
	}

	/**
	 * 生成转关单据表头
	 */
	private FptBillHead makeFptBillHead(String casBillCode,
			FptAppHead fptAppHead) {
		// 1004:结转进口
		// 1106:结转料件退货单
		// 2102:结转出口
		// 2009:结转成品退货单
		FptBillHead fptBillHead = new FptBillHead();
		fptBillHead.setProjectType(fptAppHead.getProjectType());
		fptBillHead.setAppNo(fptAppHead.getAppNo());
		fptBillHead.setAclUser(CommonUtils.getRequest().getUser());
		Company company = (Company) CommonUtils.getCompany();
		fptBillHead.setCompany(company);
		fptBillHead.setIssueTradeCod(fptAppHead.getOutTradeCode());
		fptBillHead.setIssueTradeName(fptAppHead.getOutTradeName());
		fptBillHead.setReceiveTradeCod(fptAppHead.getInTradeCode());
		fptBillHead.setReceiveTradeName(fptAppHead.getInTradeName());
		fptBillHead.setIssueAgentCode(fptAppHead.getOutAgentCode());
		fptBillHead.setIssueAgentName(fptAppHead.getOutAgentName());
		fptBillHead.setReceiveAgentCode(fptAppHead.getInAgentCode());
		fptBillHead.setReceiveAgentName(fptAppHead.getInAgentName());
		fptBillHead.setCustomer(fptAppHead.getScmCoc());
		if ("1004".equals(casBillCode)) {// 结转进口
			fptBillHead.setSysType(FptBusinessType.FPT_BILL);
			fptBillHead.setBillSort(FptInOutFlag.IN);
			fptBillHead.setReceiveCopBillNo(this.fptMessageLogic
					.getNewCopEntNoFptBill("FptBillHead", "receiveCopBillNo",
							"F", FptBusinessType.FPT_BILL,
							fptBillHead.getBillSort()));
		} else if ("1106".equals(casBillCode)) {// 结转料件退货单
			fptBillHead.setSysType(FptBusinessType.FPT_BILL_BACK);
			fptBillHead.setBillSort(FptInOutFlag.IN);
			fptBillHead.setIssueCopBillNo(this.fptMessageLogic
					.getNewCopEntNoFptBill("FptBillHead", "issueCopBillNo",
							"F", FptBusinessType.FPT_BILL_BACK,
							fptBillHead.getBillSort()));
		} else if ("2102".equals(casBillCode)) {// 结转出口
			fptBillHead.setSysType(FptBusinessType.FPT_BILL);
			fptBillHead.setBillSort(FptInOutFlag.OUT);
			fptBillHead.setIssueCopBillNo(this.fptMessageLogic
					.getNewCopEntNoFptBill("FptBillHead", "issueCopBillNo",
							"F", FptBusinessType.FPT_BILL,
							fptBillHead.getBillSort()));
			fptBillHead.setOutEmsNo(fptAppHead.getEmsNo());
		} else if ("2009".equals(casBillCode)) {// 结转成品退货单
			fptBillHead.setSysType(FptBusinessType.FPT_BILL_BACK);
			fptBillHead.setBillSort(FptInOutFlag.OUT);
			fptBillHead.setReceiveCopBillNo(this.fptMessageLogic
					.getNewCopEntNoFptBill("FptBillHead", "receiveCopBillNo",
							"F", FptBusinessType.FPT_BILL_BACK,
							fptBillHead.getBillSort()));
			fptBillHead.setOutEmsNo(fptAppHead.getEmsNo());
		}
		fptBillHead.setAppState(DeclareState.APPLY_POR);
		return fptBillHead;
	}

	/**
	 * 生成转关商品信息对象
	 */
	private void makeFptBillItemInfo(List lsBillDetail,
			FptBillHead fptBillHead, FptAppHead fptAppHead, String emsNo,
			boolean isComplex) {
		List lsFptAppItem = this.fptManageDao.findFptAppItems(fptAppHead
				.getId());
		Map<String, FptAppItem> hmFptAppItem = new HashMap<String, FptAppItem>();
		for (int i = 0; i < lsFptAppItem.size(); i++) {
			FptAppItem fptAppItem = (FptAppItem) lsFptAppItem.get(i);
			String key = "";
			if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
				key = (fptAppHead.getEmsNo() + fptAppItem.getTrNo());
			} else {
				key = (fptAppItem.getInEmsNo() + fptAppItem.getTrNo());
			}
			hmFptAppItem.put(key, fptAppItem);
		}
		int projectType = fptAppHead.getProjectType();
		if (projectType == ProjectType.BCUS) {
			this.getFptBillItemByBcus(lsBillDetail, fptBillHead, hmFptAppItem,
					emsNo, isComplex);
		} else if (projectType == ProjectType.BCS) {
			this.getFptBillItemByBcs(lsBillDetail, fptBillHead, hmFptAppItem,
					emsNo, isComplex);
		} else if (projectType == ProjectType.DZSC) {
			this.getFptBillItemByDzsc(lsBillDetail, fptBillHead, hmFptAppItem,
					emsNo, isComplex);
		}
	}

	private Map<String, FptBillItem> getFptBillItemByBcus(List lsBillDetail,
			FptBillHead fptBillHead, Map<String, FptAppItem> hmFptAppItem,
			String emsNo, boolean isComplex) {
		String emsFrom = "2";
		List list = this.fptManageDao.findNameValues(ParameterType.emsFrom);
		if (list.size() > 0 && list.get(0) != null) {
			emsFrom = ((ParameterSet) list.get(0)).getNameValues();
		}
		Map<String, FptBillItem> hmTFCommInfo = new HashMap<String, FptBillItem>();
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
				Integer seqNum = null;
				Object emsEdiMergerAfter = this.fptManageDao
						.findEmsEdiMergerAfterByBefore(emsEdiMergerHead,
								billDetail.getPtPart());
				if (emsEdiMergerAfter == null) {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "在(联网监管)归并关系备案中不存在");
				}
				Materiel materiel = this.commonCodeDao
						.findMaterieByPtNo(billDetail.getPtPart().trim());
				double unitConvert = materiel == null ? 1.0 : (materiel
						.getUnitConvert() == null ? 1.0 : materiel
						.getUnitConvert());
				if (emsEdiMergerAfter instanceof EmsEdiMergerImgAfter) {
					EmsEdiMergerImgAfter imgAfter = (EmsEdiMergerImgAfter) emsEdiMergerAfter;
					seqNum = imgAfter.getSeqNum();
				} else {
					EmsEdiMergerExgAfter exgAfter = (EmsEdiMergerExgAfter) emsEdiMergerAfter;
					seqNum = exgAfter.getSeqNum();
				}
				FptAppItem fptAppItem = hmFptAppItem.get(emsNo.trim()
						+ seqNum.toString());
				if (fptAppItem == null) {
					throw new RuntimeException("电子账册" + fptAppItem.getInEmsNo()
							+ "中，备案序号为" + seqNum + "的商品在结转申请表"
							+ fptBillHead.getAppNo() + "中不存在");
				}
				makeFptBillItem(fptBillHead, hmTFCommInfo, billDetail,
						fptAppItem, unitConvert, isComplex);
			}
		} else {
			for (int i = 0; i < lsBillDetail.size(); i++) {
				BillDetail billDetail = (BillDetail) lsBillDetail.get(i);
				InnerMergeData innerMergeData = this.fptManageDao
						.findInnerMergerByPtNo(billDetail.getPtPart());
				if (innerMergeData == null) {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "在电子账册企业内部归并中不存在");
				}
				double unitConvert = innerMergeData.getMateriel()
						.getUnitConvert() == null ? 1.0 : innerMergeData
						.getMateriel().getUnitConvert();
				Integer seqNum = innerMergeData.getHsAfterTenMemoNo();
				FptAppItem fptAppItem = hmFptAppItem.get(emsNo.trim()
						+ seqNum.toString());
				if (fptAppItem == null) {
					throw new RuntimeException("电子账册" + emsNo + "中，备案序号为"
							+ seqNum + "的商品在结转申请表" + fptBillHead.getAppNo()
							+ "中不存在");
				}
				makeFptBillItem(fptBillHead, hmTFCommInfo, billDetail,
						fptAppItem, unitConvert, isComplex);
			}
		}
		return hmTFCommInfo;
	}

	private Map<String, FptBillItem> getFptBillItemByDzsc(List lsBillDetail,
			FptBillHead fptBillHead, Map<String, FptAppItem> hmFptAppItem,
			String emsNo, boolean isComplex) {
		Map<String, FptBillItem> hmTFCommInfo = new HashMap<String, FptBillItem>();
		for (int i = 0; i < lsBillDetail.size(); i++) {
			BillDetail billDetail = (BillDetail) lsBillDetail.get(i);

			DzscInnerMergeData innerMergeData = this.fptManageDao
					.findDzscMergeAfterByBefore(billDetail.getPtPart());
			if (innerMergeData == null) {
				throw new RuntimeException("物料" + billDetail.getPtPart()
						+ "在(电子手册)归并关系备案中不存在");
			}
			double unitConvert = (innerMergeData.getUnitConvert() == null ? 1.0
					: innerMergeData.getUnitConvert());
			List list = this.fptManageDao.findDzscImgExgByInnerMergeSeqNum(
					innerMergeData.getDzscTenInnerMerge().getTenSeqNum(),
					emsNo, billDetail.getBillMaster().getBillType()
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
			FptAppItem fptAppItem = hmFptAppItem.get(emsNo.trim()
					+ seqNum.toString());
			if (fptAppItem == null) {
				throw new RuntimeException("电子手册" + emsNo + "中，备案序号为" + seqNum
						+ "的商品在关封" + fptBillHead.getAppNo() + "中不存在");
			}
			makeFptBillItem(fptBillHead, hmTFCommInfo, billDetail, fptAppItem,
					unitConvert, isComplex);
		}
		return hmTFCommInfo;
	}

	private Map<String, FptBillItem> getFptBillItemByBcs(List lsBillDetail,
			FptBillHead fptBillHead,
			Map<String, FptAppItem> hmEnvelopBillCommInfo, String emsNo,
			boolean isComplex) {
		Map<String, FptBillItem> hmTFCommInfo = new HashMap<String, FptBillItem>();
		for (int i = 0; i < lsBillDetail.size(); i++) {
			BillDetail billDetail = (BillDetail) lsBillDetail.get(i);
			BcsInnerMerge innerMergeData = this.fptManageDao
					.findBcsMergeAfterByBefore(billDetail.getPtPart());
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
			List list = fptManageDao.findContractImgByInnerMergeSeqNum(
					innerMergeData.getBcsTenInnerMerge().getSeqNum(), emsNo,
					billDetail.getBillMaster().getBillType().getProduceType(),
					isContractEms);
			if (list == null || list.size() <= 0) {
				if (isContractEms) {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "相对的归并后序号"
							+ innerMergeData.getBcsTenInnerMerge().getSeqNum()
							+ "在(备案资料库，电子化手册)" + emsNo + "中不存在");
				} else {
					throw new RuntimeException("物料" + billDetail.getPtPart()
							+ "相对的归并后序号"
							+ innerMergeData.getBcsTenInnerMerge().getSeqNum()
							+ "在(电子化手册)" + emsNo + "中不存在");
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
			FptAppItem fptAppItem = hmEnvelopBillCommInfo.get(String
					.valueOf(emsNo.trim() + seqNum.toString()));
			if (fptAppItem == null) {
				throw new RuntimeException("电子化手册" + emsNo + "中，备案序号为" + seqNum
						+ "的商品在结转申请表" + fptBillHead.getAppNo() + "中不存在");
			}
			makeFptBillItem(fptBillHead, hmTFCommInfo, billDetail, fptAppItem,
					unitConvert, isComplex);
		}
		return hmTFCommInfo;
	}

	private void makeFptBillItem(FptBillHead fptBillHead,
			Map<String, FptBillItem> hmTFCommInfo, BillDetail billDetail,
			FptAppItem fptAppItem, double unitConvert, boolean isComplex) {
		String key = "";
		if (isComplex) {// 如果是商品编码级
			if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
				key = (fptBillHead.getOutEmsNo() + fptAppItem.getTrNo());
			} else {
				key = (fptAppItem.getInEmsNo() + fptAppItem.getTrNo());
			}
		} else {// 如果是料号级
			key = billDetail.getPtPart().trim();
		}
		FptBillItem fptBillItem = hmTFCommInfo.get(key);
		if (fptBillItem == null) {
			fptBillItem = new FptBillItem();
			fptBillItem.setTransferFactoryBill(fptBillHead);
			fptBillItem.setBillSort(fptBillHead.getBillSort());
			Integer listNo = this.fptManageDao
					.getMaxFptBillItemListNo(fptBillHead);
			// 序号
			fptBillItem.setListNo(listNo + 1);
			// 手册号
			if (FptInOutFlag.IN.equals(fptAppItem.getFptAppHead()
					.getInOutFlag())) {
				fptBillItem.setEmsNo(fptAppItem.getInEmsNo());
			}
			// 申请表序号
			fptBillItem.setAppGNo(fptAppItem.getListNo());
			// 项号 对应申请表中表体的备案序号
			fptBillItem.setTrGno(fptAppItem.getTrNo());
			if (!isComplex) {// 如果是料号级
				fptBillItem.setCopGNo(billDetail.getPtPart());
				fptBillItem.setCopGName(billDetail.getPtName());
				fptBillItem.setCopGModel(billDetail.getPtSpec());
			}
			fptBillItem.setComplex(fptAppItem.getCodeTs());
			fptBillItem.setCommName(fptAppItem.getName());
			fptBillItem.setCommSpec(fptAppItem.getSpec());
			fptBillItem.setUnit(fptAppItem.getUnit());
			fptBillItem.setQty(CommonUtils.getDoubleExceptNull(billDetail
					.getPtAmount()) * unitConvert);
			fptBillItem.setTradeUnit(fptAppItem.getUnit());
			fptBillItem.setTradeQty(fptBillItem.getQty());
			hmTFCommInfo.put(key, fptBillItem);
		} else {
			fptBillItem.setQty(CommonUtils.getDoubleExceptNull(fptBillItem
					.getQty())
					+ CommonUtils.getDoubleExceptNull(billDetail.getPtAmount())
					* unitConvert);
			fptBillItem.setTradeQty(CommonUtils.getDoubleExceptNull(fptBillItem
					.getTradeQty())
					+ CommonUtils.getDoubleExceptNull(billDetail.getPtAmount())
					* unitConvert);
			hmTFCommInfo.put(key, fptBillItem);
		}
		this.fptManageDao.saveOrUpdate(fptBillItem);
		// 修改单据中心的单据明细中是否转结转单据标志位为true;
		billDetail.setIsTransferFactoryBill(true);
		this.casDao.saveBillDetail(billDetail);
		// 将申请表编号反写到单据中心单据的关封号
		BillMaster billMaster = billDetail.getBillMaster();
		billMaster.setEnvelopNo(fptBillItem.getFptBillHead().getAppNo());
		this.casDao.saveBillMaster(billMaster);
		// 保存所有中间表信息
		MakeFptBillFromCasBill makeFptBill = new MakeFptBillFromCasBill();
		makeFptBill.setBillDetailId(billDetail.getId());
		makeFptBill.setFptBillItemId(fptBillItem.getId());
		this.fptManageDao.saveOrUpdate(makeFptBill);
	}

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
	public void deleteFptBillHead(FptBillHead fptBillHead) {
		List list = this.fptManageDao.findFptBillItemByParentId(fptBillHead
				.getId());
		for (int i = 0; i < list.size(); i++) {
			FptBillItem data = (FptBillItem) list.get(i);
			//
			// 回写海关工厂商品信息设置其转为false
			//
			this.writeBackCasBillDetailByFptBillItemId(data.getId());
			this.fptManageDao.deleteFptBillItem(data);

		}
		this.fptManageDao.deleteFptBillHead(fptBillHead);
	}

	/**
	 * 删除结转单据商品信息数据
	 */
	public Map<Integer, List<FptBillItem>> deleteFptBillItem(
			List<FptBillItem> list) {
		Map<Integer, List<FptBillItem>> map = new HashMap<Integer, List<FptBillItem>>();
		List<FptBillItem> lsDelete = new ArrayList<FptBillItem>();
		List<FptBillItem> lsUpdate = new ArrayList<FptBillItem>();
		for (int i = 0; i < list.size(); i++) {
			FptBillItem fptBillItem = list.get(i);

			//
			// 回写海关工厂商品信息设置其转为false
			//
			this.writeBackCasBillDetailByFptBillItemId(fptBillItem.getId());
			this.fptManageDao.deleteFptBillItem(fptBillItem);
			lsDelete.add(fptBillItem);

		}
		map.put(DeleteResultMark.DELETE, lsDelete);
		map.put(DeleteResultMark.UPDATE, lsUpdate);
		return map;
	}

	/**
	 * 回写海关工厂信息设置其转为false
	 */
	public void writeBackCasBillDetailByFptBillItemId(String fptBillItemId) {
		List dataSource = this.fptManageDao
				.findMakeFptBillFromCasBillByFptBillItemId(fptBillItemId);
		if (dataSource == null || dataSource.size() <= 0) {
			return;
		}
		for (int i = 0; i < dataSource.size(); i++) {
			MakeFptBillFromCasBill m = (MakeFptBillFromCasBill) dataSource
					.get(i);
			//
			// 修改海关商品资料已转转厂单据为false
			//
			String casBillDetailId = m.getBillDetailId();

			List list = this.casDao.findBillDetailMaterielById(casBillDetailId);
			if (list != null && !list.isEmpty()) {
				for (int j = 0; j < list.size(); j++) {
					BillDetail billDetail = (BillDetail) list.get(j);
					billDetail.setIsTransferFactoryBill(new Boolean(false));
					this.casDao.saveBillDetail(billDetail);
				}
			}
			list = this.casDao.findBillDetailProductById(casBillDetailId);
			if (list != null && !list.isEmpty()) {
				for (int j = 0; j < list.size(); j++) {
					BillDetail billDetail = (BillDetail) list.get(j);
					billDetail.setIsTransferFactoryBill(new Boolean(false));
					this.casDao.saveBillDetail(billDetail);
				}
			}
			this.fptManageDao.deleteMakeFptBillFromCasBill(m);
		}
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
	public List findCustomsEnvelopSpareAnalyes(String commodityCode,
			String commodityName, String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo) {
		List oldList = this.getCustomsEnvelopSpareAnalyes(commodityCode,
				commodityName, seqNum, oppoisteEnterpriseName, isImportGoods,
				customsEnvelopBillNo);
		List newList = new ArrayList();
		// for (int i = 0; i < oldList.size(); i++) {
		// CustomsEnvelopCommodityInfo customsEnvelopCommodityInfo =
		// (CustomsEnvelopCommodityInfo) oldList
		// .get(i);
		// CustomsEnvelopBill customsEnvelopBill = customsEnvelopCommodityInfo
		// .getCustomsEnvelopBill();
		// CustomsEnvelopSpareAnalyes customsEnvelopSpareAnalyes = new
		// CustomsEnvelopSpareAnalyes();
		// customsEnvelopSpareAnalyes.setIsImpExpGoods(customsEnvelopBill
		// .getIsImpExpGoods());
		// customsEnvelopSpareAnalyes
		// .setCustomsEnvelopBillNo(customsEnvelopBill
		// .getCustomsEnvelopBillNo());
		// customsEnvelopSpareAnalyes.setComplex(customsEnvelopCommodityInfo
		// .getComplex());
		// customsEnvelopSpareAnalyes
		// .setScmCoc(customsEnvelopBill.getScmCoc());
		// customsEnvelopSpareAnalyes.setSpec(customsEnvelopCommodityInfo
		// .getPtSpec());
		// customsEnvelopSpareAnalyes.setSeqNum(String
		// .valueOf(customsEnvelopCommodityInfo.getSeqNum()));
		// customsEnvelopSpareAnalyes.setName(customsEnvelopCommodityInfo
		// .getPtName());
		// customsEnvelopSpareAnalyes
		// .setOwnerQuantity(customsEnvelopCommodityInfo
		// .getOwnerQuantity());// 关封数量
		// customsEnvelopSpareAnalyes.setEmsNo(customsEnvelopCommodityInfo
		// .getEmsNo());// 手册号或帐册号
		// // 送货数量
		// Double impExpGoodsQuantity = transferFactoryManageDao
		// .findTotalImpExpGoodsQuantity(
		// (isImportGoods ? Integer
		// .valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT)
		// : Integer.valueOf(ImpExpType.DIRECT_EXPORT)),
		// customsEnvelopCommodityInfo.getCustomsEnvelopBill()
		// .getCustomsEnvelopBillNo(),
		// customsEnvelopCommodityInfo.getEmsNo(),
		// customsEnvelopCommodityInfo.getSeqNum(),
		// (customsEnvelopCommodityInfo
		// .getCustomsEnvelopBill().getScmCoc() == null ? null
		// : customsEnvelopCommodityInfo
		// .getCustomsEnvelopBill()
		// .getScmCoc().getName())); // 送货数量
		// customsEnvelopSpareAnalyes
		// .setTransferFatoryCommodityInfoNum(impExpGoodsQuantity);
		// customsEnvelopSpareAnalyes
		// .setCustomsRemainNum((customsEnvelopCommodityInfo
		// .getOwnerQuantity() == null ? Double.valueOf(0)
		// : customsEnvelopCommodityInfo.getOwnerQuantity())
		// - impExpGoodsQuantity);
		// customsEnvelopSpareAnalyes
		// .setTransferQuantity(getCustomsEnvelopTransferQuantity(
		// customsEnvelopBill.getIsImpExpGoods(),
		// customsEnvelopCommodityInfo.getSeqNum(),
		// customsEnvelopCommodityInfo.getComplex(),
		// customsEnvelopBill.getCustomsEnvelopBillNo()));// 已转数量
		// customsEnvelopSpareAnalyes.setDispensabilityQuantity(CommonUtils
		// .getDoubleExceptNull(customsEnvelopSpareAnalyes
		// .getOwnerQuantity())
		// - CommonUtils
		// .getDoubleExceptNull(customsEnvelopSpareAnalyes
		// .getTransferQuantity()));// 可分配数量
		// newList.add(customsEnvelopSpareAnalyes);
		// }
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
	private List getCustomsEnvelopSpareAnalyes(String commodityCode,
			String commodityName, String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo) {
		String hsql = this.getCustomsEnvelopSpareAnalyesHsql(commodityCode,
				commodityName, seqNum, oppoisteEnterpriseName, isImportGoods,
				customsEnvelopBillNo);
		Object[] parameters = this.getCustomsEnvleopSpareAnalyesParameters(
				commodityCode, commodityName, seqNum, oppoisteEnterpriseName,
				isImportGoods, customsEnvelopBillNo);
		return this.fptManageDao.findCustomsEnvelopSpareAnalyes(hsql,
				parameters);
	}

	private double getCustomsEnvelopTransferQuantity(boolean isImport,
			Integer seqNum, Complex complex, String customsEnvelopNo) {
		double bcsAmount = this.fptManageDao.getCustomsEnvelopTransferQuantity(
				isImport, "BcsCustomsDeclarationCommInfo", seqNum, complex,
				customsEnvelopNo);
		double bcusAmount = this.fptManageDao
				.getCustomsEnvelopTransferQuantity(isImport,
						"CustomsDeclarationCommInfo", seqNum, complex,
						customsEnvelopNo);
		double dzscAmount = this.fptManageDao
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
	private String getCustomsEnvelopSpareAnalyesHsql(String commodityCode,
			String commodityName, String seqNum, String oppoisteEnterpriseName,
			boolean isImportGoods, String customsEnvelopBillNo) {
		String hsql = "from CustomsEnvelopCommodityInfo a left join fetch a.customsEnvelopBill b "
				+ " where a.customsEnvelopBill.company.id=? ";
		String condition = "";
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
		return hsql + condition;
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
	private Object[] getCustomsEnvleopSpareAnalyesParameters(
			String commodityCode, String commodityName, String seqNum,
			String oppoisteEnterpriseName, boolean isImportGoods,
			String customsEnvelopBillNo) {
		List list = new ArrayList();
		list.add(CommonUtils.getCompany().getId());
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
			Date endDate, String fptInOutFlag, String declareState,
			String fptBusinessType, String code, String name, Integer seqNum,
			String scmcoc) {
		return this.fptManageDao.findTransferFactoryImpExpGoodsList(beginDate,
				endDate, fptInOutFlag, declareState, fptBusinessType, code,
				name, seqNum, scmcoc);
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
	public List findTransferImpExpCustomsList(Date beginDate, Date endDate,
			Integer billType, String billCode, String materielCode,
			String materielName, String seqNum, String companyName) {
		List result = new ArrayList();
		List list = this.fptManageDao.findTransferImpExpCustomsList(beginDate,
				endDate, billType, billCode, materielCode, materielName,
				seqNum, companyName);
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
	// public List findStatisticTotalTransferStatusQuantity(Date beginDate,
	// Date endDate, Integer impExpType, String billCode,
	// String materielCode, String materielName, String materielSpec,
	// String companyName, String scmcode) {
	// List result = new ArrayList();
	// TempTransferStatisticTotalQuantity transferQuantity = null;
	// List materiels = this.transferFactoryManageDao
	// .findStatisticAnalyseMateriel(beginDate, endDate, impExpType,
	// billCode, materielCode, materielName, materielSpec,
	// companyName, scmcode);
	// HashMap hmPreImpExpGoodsQuantity = (beginDate == null ? new HashMap()
	// : toConvertListToHashMap(this.transferFactoryManageDao
	// .findStatisticTotalImpExpGoodsQuantity(null, beginDate,
	// impExpType, billCode, materielCode,
	// materielName, materielSpec, companyName,
	// scmcode)));
	// HashMap hmNowImpExpGoodsQuantity =
	// toConvertListToHashMap(this.transferFactoryManageDao
	// .findStatisticTotalImpExpGoodsQuantity(beginDate, endDate,
	// impExpType, billCode, materielCode, materielName,
	// materielSpec, companyName, scmcode));
	// HashMap hmPreTransferQuantity = (beginDate == null ? new HashMap()
	// : toConvertListToHashMap(this.transferFactoryManageDao
	// .findStatisticTotalTransferQuantity(null, beginDate,
	// impExpType, billCode, materielCode,
	// materielName, materielSpec, companyName,
	// scmcode, null)));
	// HashMap hmNowTransferQuantity =
	// toConvertListToHashMap(this.transferFactoryManageDao
	// .findStatisticTotalTransferQuantity(beginDate, endDate,
	// impExpType, billCode, materielCode, materielName,
	// materielSpec, companyName, scmcode, null));
	// HashMap hmPreInitQuantity = (beginDate == null ? new HashMap()
	// : toConvertListToHashMap(this.transferFactoryManageDao
	// .findStatisticTotalImpExpGoodsInitQuantity(null,
	// beginDate, impExpType, billCode, materielCode,
	// materielName, materielSpec, companyName,
	// scmcode)));
	// HashMap hmNowInitQuantity =
	// toConvertListToHashMap(this.transferFactoryManageDao
	// .findStatisticTotalImpExpGoodsInitQuantity(beginDate, endDate,
	// impExpType, billCode, materielCode, materielName,
	// materielSpec, companyName, scmcode));
	// Object[] objs = null;
	// Object objTemp = null;
	// for (int i = 0; i < materiels.size(); i++) {
	// transferQuantity = new TempTransferStatisticTotalQuantity();
	// objs = (Object[]) materiels.get(i);
	// String envlopNo = (objs[5] == null ? "" : objs[5].toString().trim());
	// String emsNo = (objs[6] == null ? "" : objs[6].toString());
	// String mCode = (objs[3] == null ? "" : objs[3].toString()) + "/"
	// + (emsNo) + "/" + (envlopNo == null ? "" : envlopNo) + "/"
	// + (objs[4] == null ? "" : objs[4].toString());
	// transferQuantity.setMaterielCode((String) objs[0]);
	// transferQuantity.setMaterielName((String) objs[1]);
	// transferQuantity.setUnit((String) objs[2]);
	// transferQuantity.setEmsNo(emsNo);
	// transferQuantity.setSeqNum(objs[3] == null ? "" : objs[3]
	// .toString());// 帐册序号
	// transferQuantity.setScmcocName((String) objs[4]);
	// transferQuantity.setEnvlopNo(envlopNo);
	// objTemp = hmPreImpExpGoodsQuantity.get(mCode);
	//
	// Double preImpExpGoodsQuantity = (objTemp == null ? new Double(0.0)
	// : Double.valueOf(objTemp.toString()));// 前期进出货累计
	// Double preTransferQuantity = (Double) hmPreTransferQuantity
	// .get(mCode);// 前期转厂累计
	// Double preInitQuantity = (Double) hmPreInitQuantity.get(mCode);//
	// // 前期初始化未转厂数量
	// Double preNoTransferQuantity = new Double(CommonUtils
	// .getDoubleExceptNull(preImpExpGoodsQuantity)
	// - CommonUtils.getDoubleExceptNull(preTransferQuantity)
	// + CommonUtils.getDoubleExceptNull(preInitQuantity));// 前期未转厂累计
	// objTemp = hmNowImpExpGoodsQuantity.get(mCode);
	// Double nowImpExpGoodsQuantity = objTemp == null ? new Double(0)
	// : Double.valueOf(objTemp.toString());// 本期进出货累计
	// Double nowTransferQuantity = (Double) hmNowTransferQuantity
	// .get(mCode);// 本期转厂累计
	// Double nowInitQuantity = (Double) hmNowInitQuantity.get(mCode);//
	// // 本期初始化未转厂数量
	// Double nowNoTransferQuantity = new Double(CommonUtils
	// .getDoubleExceptNull(nowImpExpGoodsQuantity)
	// - CommonUtils.getDoubleExceptNull(nowTransferQuantity)
	// + CommonUtils.getDoubleExceptNull(nowInitQuantity));// 本期未转厂累计
	// Double totalNoTransferQuantity = new Double(CommonUtils
	// .getDoubleExceptNull(preNoTransferQuantity)
	// + CommonUtils.getDoubleExceptNull(nowNoTransferQuantity));// 合计未转厂累计
	//
	// transferQuantity.setPreImpExpGoodsQuantity(preImpExpGoodsQuantity);
	// transferQuantity.setPreTransferQuantity(preTransferQuantity);
	// transferQuantity.setPreNoTransferQuantity(preNoTransferQuantity);
	// transferQuantity.setNowImpExpGoodsQuantity(nowImpExpGoodsQuantity);
	// transferQuantity.setNowTransferQuantity(nowTransferQuantity);
	// transferQuantity.setNowNoTransferQuantity(nowNoTransferQuantity);
	// transferQuantity
	// .setTotalNoTransferQuantity(totalNoTransferQuantity);
	// result.add(transferQuantity);
	// }
	// return result;
	// }
	// /**
	// * 转厂进出货状况表
	// *
	// * @param beginDate
	// * @param endDate
	// * @param impExpType
	// * @param billCode
	// * @param materielCode
	// * @param materielName
	// * @param emsNo
	// * @param companyName
	// * @return
	// */
	// public List findStatisticTotalTransferStatusQuantity(Date beginDate,
	// Date endDate, String fptInOutFlag, String declareState,
	// String fptBusinessType, String code, String name, Integer seqNum,
	// Integer projectType, String scmcocName) {
	// List rlist = new ArrayList();
	// // -----------------------------------------------------存放有时间
	// List customs = this.fptManageDao.findCustomsDeclarationAmount(
	// beginDate, endDate, projectType, fptInOutFlag);
	// Map customsMap = new HashMap();
	// for (int i = 0; i < customs.size(); i++) {
	// Object[] objs = (Object[]) customs.get(i);
	// String ob0 = objs[0] == null ? "/"
	// : (objs[0].toString().trim() + "/");
	// String ob1 = objs[1] == null ? "/"
	// : (objs[1].toString().trim() + "/");
	// String ob2 = objs[2] == null ? "/"
	// : (objs[2].toString().trim() + "/");
	// String key = ob0 + ob1 + ob2;
	// Double ob3 = objs[3] == null ? 0.0 : (Double.valueOf(objs[3]
	// .toString().trim()));
	// System.out.println(key + "->" + ob3);
	// customsMap.put(key, ob3);
	// }
	// // -----------------------------------------------------存放无时间
	// List customsNoDate = this.fptManageDao.findCustomsDeclarationAmount(
	// null, null, projectType, fptInOutFlag);
	// Map customsNoDateMap = new HashMap();
	// for (int i = 0; i < customsNoDate.size(); i++) {
	// Object[] objs = (Object[]) customsNoDate.get(i);
	// String ob0 = objs[0] == null ? "/"
	// : (objs[0].toString().trim() + "/");
	// String ob1 = objs[1] == null ? "/"
	// : (objs[1].toString().trim() + "/");
	// String ob2 = objs[2] == null ? "/"
	// : (objs[2].toString().trim() + "/");
	// String key = ob0 + ob1 + ob2;
	// Double ob3 = objs[3] == null ? 0.0 : (Double.valueOf(objs[3]
	// .toString().trim()));
	// System.out.println(key + "->" + ob3);
	// customsNoDateMap.put(key, ob3);
	// }
	// // -----------------------------------------------------存放无时间
	// List billitemsNoDate = this.fptManageDao.findFptBillItem(null, null,
	// fptInOutFlag, declareState, fptBusinessType, code, name,
	// seqNum, projectType, scmcocName);
	// Map billMapNoDate = new HashMap();
	// for (int i = 0; i < billitemsNoDate.size(); i++) {
	// Object[] objs = (Object[]) billitemsNoDate.get(i);
	// String ob0 = objs[0] == null ? "/"
	// : (objs[0].toString().trim() + "/");
	// String ob1 = objs[1] == null ? "/"
	// : (objs[1].toString().trim() + "/");
	// String ob2 = objs[2] == null ? "/"
	// : (objs[2].toString().trim() + "/");
	// String key = ob0 + ob1 + ob2;
	// // if (billMapNoDate.get(key) == null) {
	// System.out.println(key
	// + "->>"
	// + (objs[3] == null ? "0.0" : Double.valueOf(objs[3]
	// .toString())));
	// billMapNoDate.put(key, objs[3] == null ? 0.0 : Double
	// .valueOf(objs[3].toString()));
	// // } else {
	// // Double old = (billMapNoDate.get(key) == null ? 0.0 : Double
	// // .valueOf(billMapNoDate.get(key).toString()))
	// // + (objs[8] == null ? 0.0 : Double.valueOf(objs[8]
	// // .toString()));
	// // billMapNoDate.put(key, old);
	// // }
	//
	// }
	// // -----------------------------------------------------存放有时间
	// List nameSpec = this.fptManageDao.findFptBillItemNameSpec(beginDate,
	// endDate, fptInOutFlag, declareState, fptBusinessType, code,
	// name, seqNum, projectType, scmcocName);
	// Map nameSpecMap = new HashMap();
	// for (int i = 0; i < nameSpec.size(); i++) {
	// FptBillItem item = (FptBillItem) nameSpec.get(i);
	// String emsNo = null;
	// String emsseq = null;
	// String scmcoc = null;
	// String key = null;
	// if (fptInOutFlag.equals(FptInOutFlag.IN)) {
	// emsNo = item.getInEmsNo() == null ? "/"
	// : (item.getInEmsNo() + "/");
	// emsseq = item.getTrGno() == null ? "/" : (item.getTrGno()
	// .toString() + "/");
	// scmcoc = item.getFptBillHead().getReceiveTradeName() == null ? "/"
	// : (item.getFptBillHead().getReceiveTradeName() + "/");
	//
	// } else if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
	// emsNo = item.getFptBillHead().getOutEmsNo() == null ? "/"
	// : (item.getFptBillHead().getOutEmsNo() + "/");
	// emsseq = item.getTrGno() == null ? "/" : (item.getTrGno()
	// .toString() + "/");
	// scmcoc = item.getFptBillHead().getIssueTradeName() == null ? "/"
	// : (item.getFptBillHead().getIssueTradeName() + "/");
	// }
	// key = emsNo + emsseq + scmcoc;
	// System.out.println(key + "->>>");
	// nameSpecMap.put(key, item);
	// }
	// // ----------------------------------------------------
	//
	// List billitems = this.fptManageDao.findFptBillItem(beginDate, endDate,
	// fptInOutFlag, declareState, fptBusinessType, code, name,
	// seqNum, projectType, scmcocName);
	// Map billMap = new HashMap();
	// for (int i = 0; i < billitems.size(); i++) {
	// Object[] objs = (Object[]) billitems.get(i);
	// String ob0 = objs[0] == null ? "/"
	// : (objs[0].toString().trim() + "/");
	// String ob1 = objs[1] == null ? "/"
	// : (objs[1].toString().trim() + "/");
	// String ob2 = objs[2] == null ? "/"
	// : (objs[2].toString().trim() + "/");
	// String key = ob0 + ob1 + ob2;
	// System.out.println(key + "->>");
	// TempTransferStatusStat temp = new TempTransferStatusStat();
	// temp.setEmsNo(objs[0] == null ? null : objs[0].toString().trim());
	// temp.setEmsSeq(objs[1] == null ? null : Integer.valueOf(objs[1]
	// .toString()));
	// temp.setScmCoc(objs[2] == null ? "" : objs[2].toString().trim());
	//
	// FptBillItem item = (FptBillItem) nameSpecMap.get(key);
	// if (item != null) {
	// temp.setCode(item.getComplex() == null ? "" : item.getComplex()
	// .getCode());
	// temp.setName(item.getCommName());
	// temp.setSpec(item.getCommSpec());
	// temp.setUnit(item.getTradeUnit() == null ? "" : item
	// .getTradeUnit().getName());
	// temp.setBillNo(item.getFptBillHead().getBillNo());
	// }
	// // temp.setCode(objs[3] == null ? "" : objs[3].toString().trim());
	// // temp.setName(objs[4] == null ? "" : objs[4].toString().trim());
	// // temp.setSpec(objs[5] == null ? "" : objs[5].toString().trim());
	// // temp.setUnit(objs[6] == null ? "" : objs[6].toString().trim());
	// // temp.setBillNo(objs[7] == null ? "" : objs[7].toString().trim());
	// //
	// -----------------------------------------------------------------------
	// temp.setThisTransAmount(objs[3] == null ? 0.0 : Double
	// .valueOf(objs[3].toString()));
	// Double thYesTrans = customsMap.get(key) == null ? 0.0 : Double
	// .valueOf(customsMap.get(key).toString());
	// temp.setThYesTrans(thYesTrans);
	// temp.setThNoTrans(temp.getThisTransAmount() - temp.getThYesTrans());
	// //
	// -----------------------------------------------------------------------
	// Double transAmountNoDate = customsNoDateMap.get(key) == null ? 0.0
	// : Double.valueOf(customsNoDateMap.get(key).toString());// 报关单
	// Double impExpBillNoDate = billMapNoDate.get(key) == null ? 0.0
	// : Double.valueOf(billMapNoDate.get(key).toString());// 转厂单据
	// temp.setBeTransAmount(impExpBillNoDate - temp.getThisTransAmount());
	// temp.setBeYesTrans(transAmountNoDate - temp.getThYesTrans());
	// temp.setBeNoTrans(temp.getBeTransAmount() - temp.getBeYesTrans());
	// //
	// -----------------------------------------------------------------------
	// temp.setNoTransAmount(impExpBillNoDate - transAmountNoDate);
	// //
	// -----------------------------------------------------------------------
	// rlist.add(temp);
	// }
	// return rlist;
	// }

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
			Date endDate, String fptInOutFlag, String declareState,
			String fptBusinessType, String code, String name, Integer seqNum,
			Integer projectType, String scmcocName) {
		List rlist = new ArrayList();
		// double count = 0.0;
		// // -----------------------------------------------------存放有时间
		List customs = this.fptManageDao.findCustomsDeclarationAmount(
				beginDate, endDate, projectType, fptInOutFlag);
		Map customsMap = new HashMap();
		for (int i = 0; i < customs.size(); i++) {
			Object[] objs = (Object[]) customs.get(i);
			String ob0 = objs[0] == null ? "/"
					: (objs[0].toString().trim() + "/");
			String ob1 = objs[1] == null ? "/"
					: (objs[1].toString().trim() + "/");
			String ob2 = objs[2] == null ? "/"
					: (objs[2].toString().trim() + "/");
			String key = ob0 + ob1 + ob2;
			Double ob3 = objs[3] == null ? 0.0 : (Double.valueOf(objs[3]
					.toString().trim()));
			System.out.println(key + "->" + ob3);
			customsMap.put(key, ob3);
		}
		// // -----------------------------------------------------存放无时间
		List customsNoDate = this.fptManageDao.findCustomsDeclarationAmount(
				null, null, projectType, fptInOutFlag);
		Map customsNoDateMap = new HashMap();
		for (int i = 0; i < customsNoDate.size(); i++) {
			Object[] objs = (Object[]) customsNoDate.get(i);
			String ob0 = objs[0] == null ? "/"
					: (objs[0].toString().trim() + "/");
			String ob1 = objs[1] == null ? "/"
					: (objs[1].toString().trim() + "/");
			String ob2 = objs[2] == null ? "/"
					: (objs[2].toString().trim() + "/");
			String key = ob0 + ob1 + ob2;
			Double ob3 = objs[3] == null ? 0.0 : (Double.valueOf(objs[3]
					.toString().trim()));
			customsNoDateMap.put(key, ob3);
		}
		List nameSpec = this.fptManageDao.findFptBillItemNameSpec(beginDate,
				endDate, fptInOutFlag, declareState, fptBusinessType, code,
				name, seqNum, projectType, scmcocName);
		Map nameSpecMap = new HashMap();
		for (int i = 0; i < nameSpec.size(); i++) {
			FptBillItem item = (FptBillItem) nameSpec.get(i);
			String emsNo = null;
			String emsseq = null;
			String scmcoc = null;
			String key = null;
			if (fptInOutFlag.equals(FptInOutFlag.IN)) {
				emsNo = item.getInEmsNo() == null ? ""
						: (item.getInEmsNo() + "");
				emsseq = item.getTrGno() == null ? "" : (item.getTrGno()
						.toString());
				scmcoc = item.getFptBillHead().getReceiveTradeName() == null ? ""
						: (item.getFptBillHead().getReceiveTradeName() + "");

			} else if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
				emsNo = item.getFptBillHead().getOutEmsNo() == null ? ""
						: (item.getFptBillHead().getOutEmsNo());
				emsseq = item.getTrGno() == null ? "" : (item.getTrGno()
						.toString());
				scmcoc = item.getFptBillHead().getReceiveTradeName() == null ? ""
						: (item.getFptBillHead().getReceiveTradeName());
			}
			key = emsNo + emsseq + scmcoc;
			nameSpecMap.put(key, item);
		}

		// /////// 无时间限制的数据开始 //////////////////
		List<Object[]> list2 = getFptBillItems(null, null, fptInOutFlag,
				declareState, fptBusinessType, code, name, seqNum, projectType,
				scmcocName);
		Map<String, Double> notDate = new HashMap<String, Double>();
		for (int k = 0; k < list2.size(); k++) {
			Object[] obj = list2.get(k);
			notDate.put(obj[1].toString(),
					Double.parseDouble(obj[4].toString()));
		}
		// /////////// 无时间限制的数据 结束 /////////////////////

		List<Object[]> list = getFptBillItems(beginDate, endDate, fptInOutFlag,
				declareState, fptBusinessType, code, name, seqNum, projectType,
				scmcocName);
		for (int j = 0; j < list.size(); j++) {
			Object[] obj = list.get(j);
			TempTransferStatusStat ttss = new TempTransferStatusStat();
			String key = obj[0].toString() + obj[1].toString()
					+ obj[2].toString();
			FptBillItem item = (FptBillItem) nameSpecMap.get(key);
			if (item == null) {
				continue;
			}
			String tempCode = item.getComplex() == null ? "" : item
					.getComplex().getCode();
			ttss.setCode(tempCode);
			ttss.setName(item.getCommName());
			ttss.setSpec(item.getCommSpec() == null
					|| "".equals(item.getCommSpec()) ? "/" : item.getCommSpec());
			String tempUnit = item.getTradeUnit() == null ? "/" : item
					.getTradeUnit().getName();
			ttss.setUnit(tempUnit);
			String tempBillNo = item.getFptBillHead() == null ? "" : item
					.getFptBillHead().getAppNo();
			ttss.setBillNo(tempBillNo);
			ttss.setEmsNo(obj[0].toString());
			if ((obj[1] == null) || "".equals(obj[1].toString())) {
			} else {
				int tempEmsSeq = Integer.parseInt(obj[1].toString());
				ttss.setEmsSeq(tempEmsSeq);
			}
			ttss.setScmCoc(obj[2].toString());
			ttss.setThisTransAmount(Double.parseDouble(obj[4].toString()));
			ttss.setBeTransAmount(notDate.get(obj[1].toString())
					- Double.parseDouble(obj[4].toString()));
			double notDateCommAmount = customsNoDateMap.get(key) == null ? 0.0
					: Double.parseDouble(customsNoDateMap.get(key).toString());
			double existDaateCommAmount = customsMap.get(key) == null ? 0.0
					: Double.parseDouble(customsMap.get(key).toString());
			ttss.setBeYesTrans(notDateCommAmount - existDaateCommAmount);
			ttss.setThYesTrans(existDaateCommAmount);
			ttss.setBeNoTrans(ttss.getBeTransAmount() - ttss.getBeYesTrans());
			ttss.setThNoTrans(ttss.getThisTransAmount() - ttss.getThYesTrans());
			ttss.setNoTransAmount(ttss.getBeNoTrans() + ttss.getThNoTrans());
			rlist.add(ttss);
		}
		return rlist;
	}

	public List<Object[]> getFptBillItems(Date beginDate, Date endDate,
			String fptInOutFlag, String declareState, String fptBusinessType,
			String code, String name, Integer seqNum, Integer projectType,
			String scmcocName) {

		List fptBillItems = this.fptManageDao.findFptBillItem(beginDate,
				endDate, fptInOutFlag, declareState, fptBusinessType, code,
				name, seqNum, projectType, scmcocName);

		Map<String, Double> map = new HashMap<String, Double>();
		List<Object[]> list = new ArrayList<Object[]>();

		for (int j = 0; j < fptBillItems.size(); j++) {
			Object[] obj = (Object[]) fptBillItems.get(j);
			obj[0] = obj[0] == null ? "" : obj[0].toString();
			obj[1] = obj[1] == null ? "" : obj[1].toString();
			obj[2] = obj[2] == null ? "" : obj[2].toString();
			obj[3] = obj[3] == null ? "" : obj[3].toString();
			obj[4] = obj[4] == null ? 0.0 : Double.parseDouble(obj[4]
					.toString());
			if (map.get(obj[1].toString()) == null
					&& Integer.parseInt(obj[3].toString()) == 3) {
				map.put(obj[1].toString(),
						Double.parseDouble(obj[4].toString()));
			} else if (obj[3].toString().equals("2")) {
				double count = map.get(obj[1].toString()) == null ? 0.0 : map
						.get(obj[1].toString());
				obj[4] = Double.parseDouble(obj[4].toString()) - count;
				list.add(obj);
			}
		}
		return list;
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
	 * 根据系统类型抓取正在执行的账册号或手册号
	 */
	public BaseEmsHead findEmsHeadByProjectType(Integer projectType,
			String emsNo) {
		List list = new ArrayList();
		if (projectType == ProjectType.BCS) {
			list = this.contractDao.findContractByProcessExe();
			for (int i = 0; i < list.size(); i++) {
				Contract contract = (Contract) list.get(i);
				if (emsNo.equals(contract.getEmsNo())) {
					return contract;
				}
			}
		} else if (projectType == ProjectType.DZSC) {
			list = this.dzscDao.findDzscEmsPorHeadExcu();
			for (int i = 0; i < list.size(); i++) {
				DzscEmsPorHead contract = (DzscEmsPorHead) list.get(i);
				if (emsNo.equals(contract.getEmsNo())) {
					return contract;
				}
			}
		} else if (projectType == ProjectType.BCUS) {
			list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
			for (int i = 0; i < list.size(); i++) {
				EmsHeadH2k contract = (EmsHeadH2k) list.get(i);
				if (emsNo.equals(contract.getEmsNo())) {
					return contract;
				}
			}
		}
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
	 * 根据申请单编号获取申请单
	 * @param request
	 * @param isImportGoods
	 * @param list
	 * @return
	 */
	public List findFptAppHead(String isImportGoods,List<String> list){
		return this.fptManageDao.findFptAppHead(isImportGoods,list);
	}
	
	/**
	 * 保存转厂进出货单表头和表体
	 * @param list
	 */
	public void saveFptBillHeadsAndFptBillItems(List list,String fptBusinessType){
		
		if(list.size()>0&&(list.get(0) instanceof List)){
			List ls = (List)list.get(0);
			for (int j = 0; j < ls.size(); j++) {
				FptBillHead head = (FptBillHead)ls.get(j);
				FptParameterSet parameterSet = this.fptManageDao.findFptParameterSet();
				if(parameterSet.getProjectType()==null){
					throw new RuntimeException("请到转厂管理参数设置中,先设置项目类型!");
				}
				head.setProjectType(parameterSet.getProjectType());
				
				if(FptBusinessType.FPT_BILL_BACK.equals(fptBusinessType)){
					head.setReceiveCopBillNo(this.fptMessageLogic
							.getNewCopEntNoFptBill("FptBillHead",
									"receiveCopBillNo", "F",
									FptBusinessType.FPT_BILL_BACK, FptInOutFlag.IN));
				}else{
					head.setIssueCopBillNo(this.fptMessageLogic
							.getNewCopEntNoFptBill("FptBillHead", "issueCopBillNo",
									"F", FptBusinessType.FPT_BILL, FptInOutFlag.OUT));
				}
			}
			this.baseCodeDao.batchSaveOrUpdate(ls);
		}	
		if(list.size()>1&&(list.get(1) instanceof List)){
			List ls = (List)list.get(1);
			for (int j = 0; j < ls.size(); j++) {
				FptBillItem item = (FptBillItem)ls.get(j);
				item.setListNo(fptManageDao.getMaxFptBillItemListNo(item.getFptBillHead()) + j
						+ 1);
				
			}
			this.baseCodeDao.batchSaveOrUpdate(ls);
		}
	}
	

	/**
	 * 根据系统类型，账册号或手册号，物料和成品分类抓取转厂的物料信息
	 * 
	 * @param projectType
	 * @return
	 */
	public List findFptEmsNoCopBillNoToFptBillItemCopBillNo(int projectType,
			String isImportGoods, String appNo) {
		List lsCommInfo = new ArrayList();
		List list = new ArrayList();
		Map map = new HashMap();
		boolean isMaterial = FptInOutFlag.IN.equals(isImportGoods) ? true
				: false;
		switch (projectType) {
		case ProjectType.BCS:
			list = this.fptManageDao.findContractBcsInnerMergeByEmsNo(
					isMaterial, appNo, isImportGoods);
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
				commInfo.setListNo((Integer) obj[0]);
				commInfo.setCopNo((String) obj[1]);
				commInfo.setCopNoName((String) obj[2]);
				commInfo.setCopNoSpec((String) obj[3]);
				commInfo.setSeqNum((Integer) obj[4]);
				if (obj[5] != null) {
					commInfo.setComplex((String) obj[5]);
				}
				commInfo.setPtName((String) obj[6]);
				commInfo.setPtSpec((String) obj[7]);
				commInfo.setUnit((String) obj[8]);
				commInfo.setQty((Double) obj[9]);
				commInfo.setEmsNo((String) obj[10]);
				commInfo.setPtUnit((CalUnit) obj[11]);
				commInfo.setPtAmount((Double) obj[12]);
				lsCommInfo.add(commInfo);
			}
			break;
		case ProjectType.DZSC:
			list = this.fptManageDao.findContractDzscInnerMergeDataByEmsNo(
					isMaterial, appNo, isImportGoods);
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
				commInfo.setListNo((Integer) obj[0]);
				commInfo.setCopNo((String) obj[1]);
				commInfo.setCopNoName((String) obj[2]);
				commInfo.setCopNoSpec((String) obj[3]);
				commInfo.setSeqNum((Integer) obj[4]);
				if (obj[5] != null) {
					commInfo.setComplex((String) obj[5]);
				}
				commInfo.setPtName((String) obj[6]);
				commInfo.setPtSpec((String) obj[7]);
				commInfo.setUnit((String) obj[8]);
				commInfo.setQty((Double) obj[9]);
				commInfo.setEmsNo((String) obj[10]);
				commInfo.setPtUnit((CalUnit) obj[11]);
				commInfo.setPtAmount((Double) obj[12]);
				lsCommInfo.add(commInfo);
			}
			break;
		case ProjectType.BCUS:
			list = this.fptManageDao.findContractInnerMergeDataByEmsNo(
					isMaterial, appNo, isImportGoods);
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				TempCustomsEnvelopCommInfo commInfo = new TempCustomsEnvelopCommInfo();
				commInfo.setListNo((Integer) obj[0]);
				commInfo.setCopNo((String) obj[1]);
				commInfo.setCopNoName((String) obj[2]);
				commInfo.setCopNoSpec((String) obj[3]);
				commInfo.setSeqNum((Integer) obj[4]);
				if (obj[5] != null) {
					commInfo.setComplex((String) obj[5]);
				}
				commInfo.setPtName((String) obj[6]);
				commInfo.setPtSpec((String) obj[7]);
				commInfo.setUnit((String) obj[8]);
				commInfo.setQty((Double) obj[9]);
				commInfo.setEmsNo((String) obj[10]);
				commInfo.setPtUnit((CalUnit) obj[11]);
				commInfo.setPtAmount((Double) obj[12]);
				lsCommInfo.add(commInfo);
			}
			break;
		}
		return lsCommInfo;
	}

	/**
	 * 增加单据表体自然序号
	 * 
	 * @param head
	 */
	public List addBillListNo(FptBillHead head) {
		FptBillItem Item = new FptBillItem();
		List<FptBillItem> lsResult = new ArrayList<FptBillItem>();
		try {
			Item.setListNo(fptManageDao.getMaxFptBillItemListNo(head) + 1);
			Item.setFptBillHead(head);
			Item.setBillSort(head.getBillSort());
			this.fptManageDao.saveFptBillItem(Item);
			lsResult.add(Item);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 把查询出来的临时商品信息,保存为结转单据的商品信息
	 * 
	 * @param list
	 * @param envelopBill
	 * @return
	 */
	public List saveFptAppItemCommInfoToFptBillItemCommInfo(List list,
			FptBillHead head) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FptBillItem commInfo = new FptBillItem();
			FptAppItem tempCommInfo = (FptAppItem) list.get(i);
			commInfo.setListNo(fptManageDao.getMaxFptBillItemListNo(head) + i
					+ 1);
			commInfo.setFptBillHead(head);
			if (FptInOutFlag.IN.equals(head.getBillSort())) {
				commInfo.setInEmsNo(tempCommInfo.getInEmsNo());
			}
			commInfo.setBillSort(head.getBillSort());
			commInfo.setComplex(tempCommInfo.getCodeTs());
			commInfo.setCommName(tempCommInfo.getName());
			commInfo.setCommSpec(tempCommInfo.getSpec());
			commInfo.setUnit(tempCommInfo.getUnit());
			commInfo.setTradeUnit(tempCommInfo.getUnit1());
			commInfo.setAppGNo(Integer.valueOf(tempCommInfo.getListNo()));// 申请表序号
			commInfo.setTrGno(Integer.valueOf(tempCommInfo.getTrNo()));// 项号
			commInfo.setQty(0.0);
			// commInfo.setTradeQty(tempCommInfo.getQty());
			lsResult.add(commInfo);
		}
		this.fptManageDao.saveFptBillItem(lsResult);
		return lsResult;
	}

	/**
	 * 把查询出来的归并关系商品信息,保存为结转单据的商品信息
	 * 
	 * @param list
	 * @param envelopBill
	 * @return
	 */
	public List saveTempCustomsEnvelopCommInfoToFptBillItemCommInfo(List list,
			FptBillHead head) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FptBillItem commInfo = new FptBillItem();
			TempCustomsEnvelopCommInfo tempCommInfo = (TempCustomsEnvelopCommInfo) list
					.get(i);
			commInfo.setListNo(fptManageDao.getMaxFptBillItemListNo(head) + i
					+ 1);
			commInfo.setFptBillHead(head);
			commInfo.setBillSort(head.getBillSort());
			commInfo.setAppGNo(tempCommInfo.getListNo());
			commInfo.setCopGNo(tempCommInfo.getCopNo());
			commInfo.setCopGName(tempCommInfo.getCopNoName());
			commInfo.setCopGModel(tempCommInfo.getCopNoSpec());
			commInfo.setTrGno(tempCommInfo.getSeqNum());
			commInfo.setComplex((Complex) fptManageDao
					.findComplexByCode(tempCommInfo.getComplex()));
			commInfo.setCommName(tempCommInfo.getPtName());
			commInfo.setCommSpec(tempCommInfo.getPtSpec());
			commInfo.setUnit((Unit) fptManageDao.findUnitByName(tempCommInfo
					.getUnit()));
			commInfo.setQty(0.0);
			commInfo.setPtAmount(tempCommInfo.getPtAmount());
			commInfo.setPtUnit(tempCommInfo.getPtUnit());
			if (FptInOutFlag.IN.equals(head.getBillSort())) {
				commInfo.setInEmsNo(tempCommInfo.getEmsNo());
			}
			lsResult.add(commInfo);
		}
		this.fptManageDao.saveFptBillItem(lsResult);
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
			BaseCustomsDeclaration cm, String emsNO,
			MakeCusomsDeclarationParam param, List<Object> listSerialNumber,
			FptAppHead fptApphead, List<Object> overSerialNumber) {
		BaseCustomsDeclaration customsDeclaration = null;
		String emsNostring = "";
		if (cm == null) {
			FptBillHead fptBillHead = new FptBillHead();
			if (fptApphead.getProjectType() == null) {
				return null;
			}
			BaseEmsHead emsPorHead = findEmsHeadByProjectType(
					fptApphead.getProjectType(), emsNO);
			if (emsPorHead == null) {
				return null;
			}

			if (fptApphead.getProjectType() == ProjectType.BCUS) {
				emsNostring = "电子帐册";
				customsDeclaration = new CustomsDeclaration();
			} else if (fptApphead.getProjectType() == ProjectType.BCS) {
				emsNostring = "电子化手册";
				customsDeclaration = new BcsCustomsDeclaration();
			} else if (fptApphead.getProjectType() == ProjectType.DZSC) {
				customsDeclaration = new DzscCustomsDeclaration();
				emsNostring = "电子手册";
			}
			customsDeclaration.setEmsHeadH2k(emsPorHead.getEmsNo());
			customsDeclaration.setTradeCode(emsPorHead.getTradeCode());
			customsDeclaration.setTradeName(emsPorHead.getTradeName());
			customsDeclaration.setMachCode(emsPorHead.getTradeCode());
			customsDeclaration.setMachName(emsPorHead.getMachName());
			Integer serialNumber = null;
			boolean isImpGoods = true;
			if ("0".equals(fptApphead.getInOutFlag())) {
				isImpGoods = false;
			}
			int impExpFlag = (!isImpGoods ? ImpExpFlag.EXPORT
					: ImpExpFlag.IMPORT);
			int impExpType = (!isImpGoods ? ImpExpType.TRANSFER_FACTORY_EXPORT
					: ImpExpType.TRANSFER_FACTORY_IMPORT);
			serialNumber = this.encDao.getCustomsDeclarationSerialNumber(
					fptApphead.getProjectType(), impExpFlag,
					emsPorHead.getEmsNo());
			String customsSerialNo = (isImpGoods ? "进" : "出");
			listSerialNumber.add(emsNO + "/" + customsSerialNo + "/"
					+ serialNumber);
			overSerialNumber.add(emsNostring + ": " + emsNO + "\n报关单类型:"
					+ customsSerialNo + "口报关单" + "\n报关单流水号:" + serialNumber);
			customsDeclaration.setSerialNumber(serialNumber);
			customsDeclaration.setCompany(CommonUtils.getCompany());
			customsDeclaration.setCreater(CommonUtils.getRequest().getUser());
			customsDeclaration.setImpExpType(impExpType);
			customsDeclaration.setImpExpFlag(impExpFlag);
			customsDeclaration.setDeclarationDate(param.getDelcarationDate());
			customsDeclaration.setBillOfLading(param.getConveyance());
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
			customsDeclaration.setCustomsEnvelopBillNo(fptApphead.getAppNo());
			this.fptManageDao.saveOrUpdate(customsDeclaration);
		} else {
			Integer serialNumber = null;
			boolean isImpGoods = true;
			if (fptApphead.getProjectType() == ProjectType.BCUS) {
				emsNostring = "电子帐册";
			} else if (fptApphead.getProjectType() == ProjectType.BCS) {
				emsNostring = "电子化手册";
			} else if (fptApphead.getProjectType() == ProjectType.DZSC) {
				emsNostring = "电子手册";
			}
			if ("0".equals(fptApphead.getInOutFlag())) {
				isImpGoods = false;
			}
			int impExpFlag = (!isImpGoods ? ImpExpFlag.EXPORT
					: ImpExpFlag.IMPORT);
			int impExpType = (!isImpGoods ? ImpExpType.TRANSFER_FACTORY_EXPORT
					: ImpExpType.TRANSFER_FACTORY_IMPORT);
			serialNumber = cm.getSerialNumber();
			String customsSerialNo = (isImpGoods ? "进" : "出");
			listSerialNumber.add(emsNO + "/" + customsSerialNo + "/"
					+ serialNumber);
			overSerialNumber.add(emsNostring + ": " + emsNO + "\n报关单类型:"
					+ customsSerialNo + "口报关单" + "\n报关单流水号:" + serialNumber);
			customsDeclaration = cm;
		}
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
		return this.fptManageDao.getCustomsDeclarationCommInfoSerialNumber(
				projectType, customsDeclaration.getId());
	}

	/**
	 * 结转单据自动转报关单及其商品信息
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	public List makeCusomsDeclarationFromTransferBill(
			MakeCusomsDeclarationParam param, List billItem,
			FptAppHead fptAppHead, BaseCustomsDeclaration cm) {
		return makeCusomsDeclarationMerge(param, billItem, fptAppHead, cm);
	}

	public boolean checkmakeFptToBgdEmsH2k(String cm, List listcomminfo) {
		String emsNo = cm.substring(0, cm.indexOf("/"));
		boolean isSame = true;
		for (int i = 0; i < listcomminfo.size(); i++) {
			FptBillHead bill = (FptBillHead) listcomminfo.get(i);
			if (bill.getIsSelected() != null && bill.getIsSelected()) {
				isSame = emsNo.equals(bill.getOutEmsNo()) && isSame;
			}
		}
		return isSame;

	}

	public boolean checkmakeFptToBgdEmsH2kBill(String cm, List listcomminfo) {
		String emsNo = cm.substring(0, cm.indexOf("/"));

		boolean isSame = true;
		for (int i = 0; i < listcomminfo.size(); i++) {
			FptBillItem bill = (FptBillItem) listcomminfo.get(i);
			if (bill.getIsSelected() != null && bill.getIsSelected()) {
				isSame = emsNo.equals(bill.getInEmsNo()) && isSame;
			}
		}
		return isSame;

	}

	/**
	 * 将多个单据合并成一张报关单
	 * 
	 * @param lsBills
	 * @param param
	 * @return
	 */
	private List makeCusomsDeclarationMerge(MakeCusomsDeclarationParam param,
			List<TempFptBillItemForMakeBGD> billItemList,
			FptAppHead fptAppHead, BaseCustomsDeclaration cm) {

		// List<Object> lsResult = new ArrayList<Object>();
		// List<Object> overResult = new ArrayList<Object>();
		// Map<String, List<FptBillItem>> map = new HashMap<String,
		// List<FptBillItem>>();
		// Map<Integer, BaseCustomsDeclarationCommInfo> hmCommInfo = new
		// HashMap<Integer, BaseCustomsDeclarationCommInfo>();
		// BaseCustomsDeclaration customsDeclaration = null;
		// BaseCustomsDeclarationCommInfo commInfo = null;
		// FptBillHead fptBillHead = null;
		// for (FptBillItem item : billItem) {
		// if (FptInOutFlag.OUT.equals(item.getBillSort())) {
		// String emsNo = item.getFptBillHead().getOutEmsNo();
		// List<FptBillItem> list = map.get(emsNo);
		// if (list == null) {
		// list = new ArrayList<FptBillItem>();
		// map.put(emsNo, list);
		// }
		// list.add(item);
		// } else if (FptInOutFlag.IN.equals(item.getBillSort())) {
		// String emsNo = item.getInEmsNo();
		// List<FptBillItem> list = map.get(emsNo);
		// if (list == null) {
		// list = new ArrayList<FptBillItem>();
		// map.put(emsNo, list);
		// }
		// list.add(item);
		// }
		// }
		// Iterator iterator = map.keySet().iterator();
		// while (iterator.hasNext()) {
		// String emsNo = iterator.next().toString();
		// List<FptBillItem> list = map.get(emsNo);
		// // 添加到报关单头
		// customsDeclaration = makeCustomsDeclaration(cm, emsNo, param,
		// lsResult, fptAppHead, overResult);
		// String customsSerialNo = "";
		// for (int i = 0; i < lsResult.size(); i++) {
		// if (lsResult.get(i) != null) {
		// customsSerialNo += lsResult.get(i).toString() + ";";
		// }
		// }
		// // 生成表体
		// for (int i = 0; i < list.size(); i++) {
		// fptBillHead = ((FptBillItem) list.get(i)).getFptBillHead();
		// FptBillItem fptBillItem = (FptBillItem) list.get(i);
		//
		// commInfo = hmCommInfo.get((Integer) fptBillItem.getTrGno());
		//
		// if (fptAppHead.getProjectType() == ProjectType.BCUS) {
		// commInfo = new CustomsDeclarationCommInfo();
		// } else if (fptAppHead.getProjectType() == ProjectType.BCS) {
		// commInfo = new BcsCustomsDeclarationCommInfo();
		// } else if (fptAppHead.getProjectType() == ProjectType.DZSC) {
		// commInfo = new DzscCustomsDeclarationCommInfo();
		// }
		// commInfo.setBaseCustomsDeclaration(customsDeclaration);
		// commInfo.setSerialNumber(this
		// .getCustomsDeclarationCommInfoSerialNumber(fptAppHead
		// .getProjectType(), customsDeclaration));
		// commInfo.setCommSerialNo((Integer) fptBillItem.getTrGno());
		// commInfo.setComplex((Complex) fptManageDao
		// .findComplexByCode((String) fptBillItem.getComplex()
		// .getCode()));
		// commInfo.setCommName((String) fptBillItem.getCommName());
		// commInfo.setCommSpec((String) fptBillItem.getCommSpec());
		// commInfo.setUnit((Unit) fptManageDao
		// .findUnitByName((String) fptBillItem.getUnit()
		// .getCode()));
		// commInfo.setCommAmount((Double) fptBillItem.getQty());
		// hmCommInfo.put((Integer) fptBillItem.getTrGno(), commInfo);
		//
		// this.fptManageDao.saveOrUpdate(commInfo);
		// // 产生中进出货转厂单据与报关单的中间表
		// MakeFptBillCustomsDeclaration make = new
		// MakeFptBillCustomsDeclaration();
		// make.setProjectType(fptAppHead.getProjectType());
		// make.setCustomsDeclarationCommInId(commInfo.getId());
		// make.setFptBillHeadAppNo(fptBillHead.getAppNo());
		// make.setFptBillItemTrGno(fptBillItem.getTrGno());
		//
		// this.fptManageDao.saveOrUpdate(make);
		// List ls = fptManageDao
		// .findFptBillItemCommodityInfo(fptBillItem);
		// for (int j = 0; j < ls.size(); j++) {
		// fptBillItem = (FptBillItem) ls.get(j);
		// fptBillItem.setIsCustomsDeclaration(true);// 改变结转单据表体状态：已转报关单
		// this.fptManageDao.saveOrUpdate(fptBillItem);
		//
		// fptBillHead = fptBillItem.getFptBillHead();
		// if (!"".equals(fptBillHead.getMakeCustomsDeclarationCode())) {
		// // String serialNo =
		// // fptBillHead.getMakeCustomsDeclarationCode();
		// // if (customsSerialNo.indexOf(serialNo) > -1) {
		// // fptBillHead
		// // .setMakeCustomsDeclarationCode(customsSerialNo);
		// // } else {
		// //
		// fptBillHead.setMakeCustomsDeclarationCode(fptBillHead.getMakeCustomsDeclarationCode()+
		// // customsSerialNo);
		// // }
		// } else {
		// fptBillHead
		// .setMakeCustomsDeclarationCode(customsSerialNo);
		// }
		// this.fptManageDao.saveOrUpdate(fptBillHead);// 改变表头资料
		//
		// }
		//
		// }
		//
		// if (hmCommInfo.keySet().size() > 20) {
		// throw new RuntimeException("合并后的报关单超出20项");
		// }
		// Iterator key = hmCommInfo.keySet().iterator();
		// while (key.hasNext()) {
		// Integer key1 = Integer.parseInt(key.next().toString());
		// commInfo = hmCommInfo.get(key1);
		// // 当有单价时需要
		// if (commInfo != null) {
		// commInfo.setCommTotalPrice(CommonUtils
		// .getDoubleExceptNull(commInfo.getCommAmount())
		// * CommonUtils.getDoubleExceptNull(commInfo
		// .getCommUnitPrice()));
		// this.fptManageDao.saveOrUpdate(commInfo);
		// }
		// }
		// }
		// return overResult;
		if (billItemList.size() > 20) {
			throw new RuntimeException("合并后的报关单超出20项");
		}
		List<Object> lsResult = new ArrayList<Object>();
		List<Object> overResult = new ArrayList<Object>();
		BaseCustomsDeclaration customsDeclaration = null;
		String emsNo = "";
		if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
			emsNo = fptAppHead.getEmsNo();
		} else {
			emsNo = fptAppHead.getInEmsNo();
		}
		// 添加到报关单头
		customsDeclaration = makeCustomsDeclaration(cm, emsNo, param, lsResult,
				fptAppHead, overResult);
		String customsSerialNo = "";
		for (int i = 0; i < lsResult.size(); i++) {
			if (lsResult.get(i) != null) {
				customsSerialNo += lsResult.get(i).toString() + ";";
			}
		}
		// 生成表体
		for (int i = 0; i < billItemList.size(); i++) {
			TempFptBillItemForMakeBGD tempFptBillItem = (TempFptBillItemForMakeBGD) billItemList
					.get(i);
			BaseCustomsDeclarationCommInfo commInfo = null;
			if (fptAppHead.getProjectType() == ProjectType.BCUS) {
				commInfo = new CustomsDeclarationCommInfo();
			} else if (fptAppHead.getProjectType() == ProjectType.BCS) {
				commInfo = new BcsCustomsDeclarationCommInfo();
			} else if (fptAppHead.getProjectType() == ProjectType.DZSC) {
				commInfo = new DzscCustomsDeclarationCommInfo();
			}
			commInfo.setBaseCustomsDeclaration(customsDeclaration);
			commInfo.setSerialNumber(this
					.getCustomsDeclarationCommInfoSerialNumber(
							fptAppHead.getProjectType(), customsDeclaration));
			commInfo.setCommSerialNo((Integer) tempFptBillItem.getTrGno());
			Complex complex = (Complex) fptManageDao
					.findComplexByCode((String) tempFptBillItem
							.getComplexCode());
			commInfo.setComplex(complex);
			commInfo.setCommName((String) tempFptBillItem.getCommName());
			commInfo.setCommSpec((String) tempFptBillItem.getCommSpec());
			commInfo.setUnit(tempFptBillItem.getUnit());
			commInfo.setCommAmount((Double) tempFptBillItem.getQty());

			commInfo.setLegalUnit(complex == null ? null : complex
					.getFirstUnit());

			this.fptManageDao.saveOrUpdate(commInfo);
			// 产生中进出货转厂单据与报关单的中间表
			List<FptBillItem> listBillItem = tempFptBillItem
					.getFptBillItemSet();
			for (FptBillItem billItem : listBillItem) {
				MakeFptBillCustomsDeclaration make = new MakeFptBillCustomsDeclaration();
				// make.setProjectType(fptAppHead.getProjectType());
				make.setCustomsDeclarationCommInId(commInfo.getId());
				// make.setFptBillHeadAppNo(fptAppHead.getAppNo());
				// make.setFptBillItemTrGno(tempFptBillItem.getTrGno());
				make.setFptBillItemId(billItem.getId());
				this.fptManageDao.saveOrUpdate(make);
			}
			List ls = tempFptBillItem.getFptBillItemSet();
			for (int j = 0; j < ls.size(); j++) {
				FptBillItem fptBillItem = (FptBillItem) ls.get(j);
				fptBillItem.setIsCustomsDeclaration(true);// 改变结转单据表体状态：已转报关单
				this.fptManageDao.saveOrUpdate(fptBillItem);

				FptBillHead fptBillHead = fptBillItem.getFptBillHead();
				if (fptBillHead.getMakeCustomsDeclarationCode() == null
						|| "".equals(fptBillHead
								.getMakeCustomsDeclarationCode().trim())) {
					fptBillHead.setMakeCustomsDeclarationCode(customsSerialNo);
					this.fptManageDao.saveOrUpdate(fptBillHead);// 改变表头资料
				}
			}
		}

		return overResult;
	}

	public void delBillPriceMaintenance(List list) {
		for (int i = 0; i < list.size(); i++) {
			FptBillPriceMaintenance bpm = (FptBillPriceMaintenance) list.get(i);
			this.fptManageDao.delete(bpm);
		}
	}

	public List addBillPriceMaintence(List list, ScmCoc scmCoc,
			Boolean isCustomer) {
		List tempList = new ArrayList();
		// for (CustomsEnvelopCommodityInfo ceci : list) {
		// BillPriceMaintenance bpm = new BillPriceMaintenance();
		// bpm.setSeqNum(ceci.getSeqNum());
		// bpm.setComplex(ceci.getComplex());
		// bpm.setPtName(ceci.getPtName());
		// bpm.setPtSpec(ceci.getPtSpec());
		// bpm.setCurr(ceci.getCurr());
		// bpm.setUnitPrice(ceci.getUnitPrice());
		// bpm.setIsCustomer(isCustomer);
		// bpm.setScmCoc(scmCoc);
		// bpm
		// .setProjectType(ceci.getCustomsEnvelopBill() == null ? null
		// : (ceci.getCustomsEnvelopBill().getProjectType() == null ? 100
		// : ceci.getCustomsEnvelopBill()
		// .getProjectType()));
		// bpm.setCompany(ceci.getCompany());
		// this.transferFactoryManageDao.saveOrUpdate(bpm);
		// tempList.add(bpm);
		// }
		return tempList;
	}

	public void editBillPriceMaintenancePrice(ScmCoc scmCoc, Integer seqNum,
			String code, Double price, int projectType, Boolean isCustomer) {
		// 修改电子化手册转厂类型报关单表休中商品的单价
		if (projectType == ProjectType.BCS) {
			List<BaseCustomsDeclarationCommInfo> bcsList = this.fptManageDao
					.findBcsCustomsDeclarationCommInfo(scmCoc, seqNum, code);
			for (BaseCustomsDeclarationCommInfo data : bcsList) {
				data.setCommUnitPrice(price);
				data.setCommTotalPrice(data.getCommAmount() == null ? 0.0
						: data.getCommAmount() * price);
				this.fptManageDao.saveOrUpdate(data);
			}
		}
		// 修改电联网监管转厂类型报关单表休中商品的单价
		else if (projectType == ProjectType.BCUS) {
			List<BaseCustomsDeclarationCommInfo> bcusList = this.fptManageDao
					.findCustomsDeclarationCommInfo(scmCoc, seqNum, code);
			for (BaseCustomsDeclarationCommInfo data : bcusList) {
				data.setCommUnitPrice(price);
				data.setCommTotalPrice(data.getCommAmount() == null ? 0.0
						: data.getCommAmount() * price);
				this.fptManageDao.saveOrUpdate(data);
			}
		}
		// 修改电子手册转厂类型报关单表休中商品的单价
		else if (projectType == ProjectType.DZSC) {
			List<BaseCustomsDeclarationCommInfo> dascList = this.fptManageDao
					.findDzscCustomsDeclarationCommInfo(scmCoc, seqNum, code);
			for (BaseCustomsDeclarationCommInfo data : dascList) {
				data.setCommUnitPrice(price);
				data.setCommTotalPrice(data.getCommAmount() == null ? 0.0
						: data.getCommAmount() * price);
				this.fptManageDao.saveOrUpdate(data);
			}
		}
		// // 修改关封表休中商品的单价
		// List<CustomsEnvelopCommodityInfo> list =
		// this.transferFactoryManageDao
		// .findCustomsEnvelopCommodityInfoBySomCocSeqNumCode(scmCoc,
		// seqNum, code, isCustomer);
		//
		// for (CustomsEnvelopCommodityInfo data : list) {
		// data.setTotalPrice(data.getOwnerQuantity() == null ? 0.0 : data
		// .getOwnerQuantity()
		// * price);
		// data.setUnitPrice(price);
		// this.transferFactoryManageDao.saveOrUpdate(data);
		//
		// }
		// 修改转厂单据表休中商品的单价
		// List<String> slist = this.transferFactoryManageDao
		// .findCustomsEnvelopBillByNotEndCaseAndScmCoc(scmCoc, isCustomer);
		// for (String billNo : slist) {
		// List<FptBillItem> tlist = this.transferFactoryManageDao
		// .findTransferFactoryCommodityInfoByBillNo(billNo, seqNum,
		// code);
		// for (FptBillItem data : tlist) {
		// // data.setTotalPrice(data.getQuantity() == null ? 0.0 : data
		// // .getQuantity()
		// // * price);
		// // data.setUnitPrice(price);
		// }
		// }
	}

	/**
	 * 收/发货单海关申报
	 * 
	 * @param head
	 * 
	 * @return FptBillHead
	 */
	public DeclareFileInfo applyFptBill(FptBillHead head, String taskId) {
		head = this.fptManageDao.findFptBillHeadById(head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<FptBillHead> lsFptHead = new ArrayList<FptBillHead>();
		List lsFptBill = new ArrayList();
		List lsFptBillDict = new ArrayList();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		FptSignInfo signInfo = (FptSignInfo) fptMessageLogic.getCspPtsSignInfo(
				info, null);
		// signInfo.setCopNo(head.getCopNo());
		// signInfo.setTradeCode(head.getTradeCod());
		signInfo.setColDcrTime(0);
		if (FptBusinessType.FPT_BILL.equals(head.getSysType())) {
			signInfo.setSysType("0");
			// 在发货单和收货单报文中sign标签段中的企业内部编号和经营单位代码两个字段永远填发货方的信息。
			// 退货单、收退货单报文中sign标签段中的企业内部编号和经营单位代码两个字段永远填退货方的信息。
			signInfo.setCopNo(head.getIssueCopBillNo());
			signInfo.setTradeCode(head.getIssueTradeCod());
		} else {
			signInfo.setSysType("1");
			// 在发货单和收货单报文中sign标签段中的企业内部编号和经营单位代码两个字段永远填发货方的信息。
			// 退货单、收退货单报文中sign标签段中的企业内部编号和经营单位代码两个字段永远填退货方的信息。
			signInfo.setCopNo(head.getReceiveCopBillNo());
			signInfo.setTradeCode(head.getReceiveTradeCod());
		}
		signInfo.setAppNo(head.getAppNo());
		signInfo.setSeqNo(head.getSeqNo());
		signInfo.setInOutFlag(head.getBillSort());
		signInfo.setSignDate(new Date());
		if (DeclareState.CHANGING_EXE.equals(head.getAppState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		if (FptInOutFlag.OUT.equals(head.getBillSort())) {
			head.setIssueIsDeclaDate(new Date());
		} else {
			head.setReceiveIsDeclaDate(new Date());
		}
		head.setAppState(DeclareState.WAIT_EAA);
		lsFptHead.add(head);
		lsFptBill = this.fptManageDao.findFptBillItemStateChanged(head.getId(),
				head.getBillSort());
		lsFptBillDict = getFptBillDictItem(head.getId(), head.getBillSort(),
				head.getSysType());
		String formatFile = "FptBillFormat.xml";
		hmData.put("FptBillSign", lsSignInfo);
		hmData.put("FptBillHead", lsFptHead);
		hmData.put("FptBillItem", lsFptBill);
		hmData.put("FptBillDictItem", lsFptBillDict);
		DeclareFileInfo fileInfo = fptMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.fptManageDao.saveFptBillHead(head);
		return fileInfo;
	}

	private List getFptBillDictItem(String parentId, String inOutFlag,
			String sysType) {
		// List lsResult = new ArrayList();
		// List ls = fptManageDao.findFptBillDictItemStateChanged(parentId,
		// inOutFlag, sysType);
		// for (int i = 0; i < ls.size(); i++) {
		// Object[] c = (Object[]) ls.get(i);
		// TempFptBillDictItem dictItem = new TempFptBillDictItem();
		// dictItem.setListNo(i + 1);
		// dictItem.setAppGNo((Integer) c[0]);
		// dictItem.setTrGno((Integer) c[1]);
		// dictItem.setComplex((Complex) fptManageDao
		// .findComplexByCode((String) c[2]));
		// dictItem.setCommName((String) c[3]);
		// dictItem.setCommSpec((String) c[4]);
		// dictItem.setTradeUnit((Unit) fptManageDao
		// .findUnitByName((String) c[5]));
		// dictItem.setTradeQty((Double) c[6]);
		// dictItem.setUnit((Unit) fptManageDao.findUnitByName((String) c[7]));
		// dictItem.setQty((Double) c[8]);
		// dictItem.setInEmsNo((String) c[9]);
		// if ((FptInOutFlag.IN.equals(inOutFlag) && FptBusinessType.FPT_BILL
		// .equals(sysType))
		// || (FptInOutFlag.OUT.equals(inOutFlag) &&
		// FptBusinessType.FPT_BILL_BACK
		// .equals(sysType))) {
		// dictItem.setOutNo((Integer) c[10]);
		// }
		// lsResult.add(dictItem);
		// }
		// return lsResult;
		List lsResult = new ArrayList();
		Map<String, TempFptBillDictItem> map = new HashMap<String, TempFptBillDictItem>();
		List ls = this.fptManageDao.findFptBillItemByFptBillheadId(parentId,
				inOutFlag);
		for (int i = 0; i < ls.size(); i++) {
			FptBillItem fptBillItem = (FptBillItem) ls.get(i);
			StringBuilder keyBuilder = new StringBuilder();
			keyBuilder
					.append(fptBillItem.getAppGNo() == null ? "" : fptBillItem
							.getAppGNo().toString())
					.append("/")
					.append(fptBillItem.getInEmsNo() == null ? "" : fptBillItem
							.getInEmsNo().trim())
					.append("/")
					.append(fptBillItem.getUnit() == null ? "" : fptBillItem
							.getUnit().getCode())
					.append("/")
					.append((fptBillItem.getTradeUnit() == null ? ""
							: fptBillItem.getTradeUnit().getCode()))
					.append("/");
			if ((FptInOutFlag.IN.equals(inOutFlag) && FptBusinessType.FPT_BILL
					.equals(sysType))
					|| (FptInOutFlag.OUT.equals(inOutFlag) && FptBusinessType.FPT_BILL_BACK
							.equals(sysType))) {
				keyBuilder.append(
						fptBillItem.getOutNo() == null ? "" : fptBillItem
								.getOutNo().toString()).append("/");
			}
			String key = keyBuilder.toString();
			TempFptBillDictItem dictItem = map.get(key);
			if (dictItem == null) {
				dictItem = new TempFptBillDictItem();
				try {
					PropertyUtils.copyProperties(dictItem, fptBillItem);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				map.put(key, dictItem);
			} else {
				dictItem.setQty(CommonUtils.getDoubleExceptNull(dictItem
						.getQty())
						+ CommonUtils.getDoubleExceptNull(fptBillItem.getQty()));
				dictItem.setTradeQty(CommonUtils.getDoubleExceptNull(dictItem
						.getTradeQty())
						+ CommonUtils.getDoubleExceptNull(fptBillItem
								.getTradeQty()));
			}
		}
		lsResult.addAll(map.values());
		Collections.sort(lsResult, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				TempFptBillDictItem item1 = (TempFptBillDictItem) o1;
				TempFptBillDictItem item2 = (TempFptBillDictItem) o2;
				if (item1.getAppGNo() == null && item2.getAppGNo() == null) {
					return 0;
				}
				if (item1.getAppGNo() == null && item2.getAppGNo() != null) {
					return -1;
				}
				if (item1.getAppGNo() != null && item2.getAppGNo() == null) {
					return 1;
				}
				int appGNo1 = item1.getAppGNo();
				int appGNo2 = item2.getAppGNo();
				if ((appGNo1 - appGNo2) > 0) {
					return 1;
				} else if ((appGNo1 - appGNo2) == 0) {
					return 0;
				} else if ((appGNo1 - appGNo2) < 0) {
					return -1;
				}
				return 0;
			}
		});
		for (int i = 0; i < lsResult.size(); i++) {
			TempFptBillDictItem dictItem = (TempFptBillDictItem) lsResult
					.get(i);
			dictItem.setListNo(i + 1);
		}
		return lsResult;
	}

	/** 新增转厂申请单 */
	public FptAppHead newFptAppHead(String fptInOutFlag) {
		FptAppHead head = new FptAppHead();

		FptParameterSet parameterSet = this.fptManageDao.findFptParameterSet();
		Company com = this.dzscDao.findCompany();
		StringBuffer stringBuffer = new StringBuffer();
		if (com.getCode() == null || "".equals(com.getCode())) {
			stringBuffer.append("公司的加工单位编码不能为空\r\n");
		}
		if (com.getName() == null || "".equals(com.getName())) {
			stringBuffer.append("公司的加工单位名称不能为空\r\n");
		}
		if (parameterSet.getProjectType() == null) {
			stringBuffer.append("请到转厂管理参数设置中,先设置项目类型\r\n");
		}

		if (!stringBuffer.toString().trim().equals("")) {
			throw new RuntimeException(stringBuffer.toString());
		}

		/** 转出地 */
		District outDistrict = com.getOutDistrict();
		/** 申报企业9位组织机构代码 非空 转出方 */
		String inAgentCode = com.getInAgentCode();
		/** 申报企业组织机构名称 非空 */
		String inAgentName = com.getInAgentName();
		;
		/** 转入企业法人/联系电话 */
		String inCorp = com.getInCorp();
		/** 转入申报人/联系电话 */
		String inDecl = com.getInDecl();
		/** 转入申报企业代码 可空 */
		String inTradeCode2 = com.getInTradeCode2();
		/** 转入申报企业名称 可空 */
		String inTradeName2 = com.getInTradeName2();

		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
			head.setOutTradeCode(com.getCode());
			head.setOutTradeName(com.getName());
			head.setAppClass(AppClass.Z);
			head.setOutLiceNo("人工审批");
			head.setInOutFlag(FptInOutFlag.OUT);
			head.setOutCustoms(com.getMasterCustoms());
			head.setOutDistrict(outDistrict);
			head.setOutAgentCode(inAgentCode);
			head.setOutAgentName(inAgentName);
			head.setOutCorp(inCorp);
			head.setOutDecl(inDecl);
			head.setOutTradeCode2(inTradeCode2);
			head.setOutTradeName2(inTradeName2);

			head.setOutCopAppNo(this.fptMessageLogic.getNewCopEntNoFptBill(
					"FptAppHead", "outCopAppNo", "F", FptBusinessType.FPT_APP,
					fptInOutFlag));
			//
			// 初始化 ems no
			//
			if (ProjectType.BCUS == parameterSet.getProjectType().intValue()) {
				List list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
				for (int i = 0; i < list.size(); i++) {
					EmsHeadH2k contract = (EmsHeadH2k) list.get(i);
					head.setEmsNo(contract.getEmsNo());
				}
			}
		} else {
			head.setInTradeCode(com.getCode());
			head.setInTradeName(com.getName());
			head.setAppClass(AppClass.Z);
			head.setInLiceNo("人工审批");
			head.setInOutFlag(FptInOutFlag.IN);
			head.setInCustoms(com.getMasterCustoms());

			head.setInAgentCode(inAgentCode);
			head.setInAgentName(inAgentName);
			head.setInCorp(inCorp);
			head.setInDecl(inDecl);
			head.setInTradeCode2(inTradeCode2);
			head.setInTradeName2(inTradeName2);

			head.setInCopAppNo(this.fptMessageLogic.getNewCopEntNoFptBill(
					"FptAppHead", "inCopAppNo", "F", FptBusinessType.FPT_APP,
					fptInOutFlag));
		}
		head.setDelcareType(DelcareType.APPLICATION);
		head.setModifyMarkState(ModifyMarkState.ADDED);
		head.setDeclareState(DeclareState.APPLY_POR);
		head.setAclUser(CommonUtils.getAclUser());
		head.setCompany(com);
		head.setProjectType(parameterSet.getProjectType());
		this.fptManageDao.saveFptAppHead(head);
		return head;
	}

	/**
	 * 变更申请单 如果返回null就不能变量 否则 就变更一条新的记录
	 * 
	 * @param fptAppHead
	 *            申请单表头
	 * @return Contract 申请单表头
	 */
	public FptAppHead changingFptAppHead(FptAppHead fptAppHead) {
		FptAppHead head = new FptAppHead();
		try {
			PropertyUtils.copyProperties(head, fptAppHead);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		/**
		 * 申请单申报状态
		 */
		String declareState = head.getDeclareState();
		if (!DeclareState.PROCESS_EXE.equals(declareState)) {
			return null;
		}
		//
		// 转出 存在已变更的记录
		//
		FptAppHead fptAppHeadTemp = null;
		if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
			fptAppHeadTemp = this.fptManageDao.findFptAppHeadByOutCopAppNo(
					fptAppHead.getOutCopAppNo(), DeclareState.CHANGING_EXE);
		} else {// 转入
			fptAppHeadTemp = this.fptManageDao.findFptAppHeadByInCopAppNo(
					fptAppHead.getInCopAppNo(), DeclareState.CHANGING_EXE);
		}
		if (fptAppHeadTemp != null) {
			return null;
		}
		//
		// 存在已等待审批的记录
		//
		FptAppHead fptAppHeadTempa = null;
		if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
			fptAppHeadTempa = this.fptManageDao.findFptAppHeadByOutCopAppNo(
					fptAppHead.getOutCopAppNo(), DeclareState.WAIT_EAA);
		} else {// 转入
			fptAppHeadTempa = this.fptManageDao.findFptAppHeadByInCopAppNo(
					fptAppHead.getInCopAppNo(), DeclareState.WAIT_EAA);
		}
		if (fptAppHeadTempa != null) {
			return null;
		}
		/**
		 * 申请单表头Id
		 */
		String fptAppHeadId = head.getId();
		/**
		 * 生成新的申请单表头
		 */
		head.setId(null);
		head.setDeclareState(DeclareState.CHANGING_EXE);
		head.setDelcareType(DelcareType.MODIFY);
		this.fptManageDao.saveFptAppHead(head);
		Map<Integer, String> fptAppHeadImgMap = new HashMap<Integer, String>();

		/**
		 * 查找申请单料件 来自 申请单成品ID
		 */
		List fptAppItems = this.fptManageDao.findFptAppItems(fptAppHeadId);
		for (int j = 0; j < fptAppItems.size(); j++) {
			FptAppItem fptAppItem = new FptAppItem();
			try {
				PropertyUtils.copyProperties(fptAppItem,
						(FptAppItem) fptAppItems.get(j));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			//
			// 转抄料件
			//
			fptAppItem.setId(null);
			fptAppItem.setFptAppHead(head);
			this.fptManageDao.saveFptAppItem(fptAppItem);
		}
		return head;
	}

	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */
	public List copyFptAppHead(List<FptAppHead> list) {
		List<FptAppHead> lsResult = new ArrayList<FptAppHead>();
		try {
			for (int i = 0; i < list.size(); i++) {
				FptAppHead head = (FptAppHead) BeanUtils.cloneBean(list.get(i));
				String headId = head.getId();

				String fptInOutFlag = head.getInOutFlag();
				if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
					head.setOutCopAppNo(this.fptMessageLogic
							.getNewCopEntNoFptBill("FptAppHead", "outCopAppNo",
									"F", FptBusinessType.FPT_APP, fptInOutFlag));
					head.setOutDate(null);
				} else {
					head.setInCopAppNo(this.fptMessageLogic
							.getNewCopEntNoFptBill("FptAppHead", "inCopAppNo",
									"F", FptBusinessType.FPT_APP, fptInOutFlag));
					head.setInDate(null);
				}
				/**
				 * 转抄单据头
				 */
				head.setId(null);
				head.setDelcareType(DelcareType.APPLICATION);
				head.setAppNo(null);
				head.setSeqNo(null);
				head.setModifyMarkState(ModifyMarkState.ADDED);
				head.setDeclareState(DeclareState.APPLY_POR);
				head.setAclUser(CommonUtils.getAclUser());
				this.fptManageDao.saveFptAppHead(head);
				lsResult.add(head);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 转抄数据
	 * 
	 * @param list
	 * 
	 */
	public List copyFptAppHeadAll(List<FptAppHead> list) {
		List<FptAppHead> lsResult = new ArrayList<FptAppHead>();
		try {
			for (int i = 0; i < list.size(); i++) {
				FptAppHead head = (FptAppHead) BeanUtils.cloneBean(list.get(i));
				String headId = head.getId();
				String fptInOutFlag = head.getInOutFlag();
				if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
					head.setOutCopAppNo(this.fptMessageLogic
							.getNewCopEntNoFptBill("FptAppHead", "outCopAppNo",
									"F", FptBusinessType.FPT_APP, fptInOutFlag));
					head.setOutDate(null);
				} else {
					head.setInCopAppNo(this.fptMessageLogic
							.getNewCopEntNoFptBill("FptAppHead", "inCopAppNo",
									"F", FptBusinessType.FPT_APP, fptInOutFlag));
					head.setInDate(null);
				}
				// 申报类型 1－备案申请 2－变更申请
				/**
				 * 转抄单据头
				 */
				head.setId(null);
				head.setDelcareType(DelcareType.APPLICATION);
				head.setAppNo(null);
				head.setSeqNo(null);
				head.setModifyMarkState(ModifyMarkState.ADDED);
				head.setDeclareState(DeclareState.APPLY_POR);
				head.setAclUser(CommonUtils.getAclUser());
				this.fptManageDao.saveFptAppHead(head);

				List fptAppItems = this.fptManageDao.findFptAppItems(headId);

				for (int j = 0; j < fptAppItems.size(); j++) {
					FptAppItem ftpAppItem = (FptAppItem) BeanUtils
							.cloneBean(fptAppItems.get(j));
					ftpAppItem.setId(null);
					ftpAppItem.setFptAppHead(head);
					ftpAppItem.setQty(null);
					ftpAppItem.setQty1(null);
					//
					// 只改变当前的
					//
					if (fptInOutFlag.equals(ftpAppItem.getInOutFlag())) {
						ftpAppItem.setModifyMarkState(ModifyMarkState.ADDED);
					}
					this.fptManageDao.saveFptAppItem(ftpAppItem);
				}
				lsResult.add(head);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 转抄明细到另一个表头
	 */
	public List copyFptAppItemToHead(FptAppHead head, List<FptAppItem> list) {

		List<FptAppItem> lsResult = new ArrayList<FptAppItem>();
		try {
			int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
					head.getInOutFlag());
			for (int i = 0; i < list.size(); i++) {
				FptAppItem c = (FptAppItem) BeanUtils.cloneBean(list.get(i));
				c.setListNo(listNo + i + 1);
				c.setQty(null);
				c.setQty1(null);
				/**
				 * 转抄发货单
				 */
				c.setId(null);
				c.setFptAppHead(head);
				c.setModifyMarkState(ModifyMarkState.ADDED);
				this.fptManageDao.saveFptAppItem(c);
				lsResult.add(c);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return lsResult;
	}

	/**
	 * 转抄明细
	 */
	public FptAppHead copyFptAppItem(FptAppHead head, List<FptAppItem> list) {

		List<FptAppHead> oldList = new ArrayList<FptAppHead>();
		oldList.add(head);
		List<FptAppHead> newList = this.copyFptAppHead(oldList);
		FptAppHead newHead = newList.get(0);

		try {
			for (int i = 0; i < list.size(); i++) {
				FptAppItem c = (FptAppItem) BeanUtils.cloneBean(list.get(i));
				c.setQty(null);
				c.setQty1(null);
				c.setListNo(i + 1);
				c.setId(null);
				c.setModifyMarkState(ModifyMarkState.ADDED);
				c.setFptAppHead(newHead);
				this.fptManageDao.saveFptAppItem(c);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
		return newHead;
	}

	// //////////////////////////////////////////////////////////////////////////
	// 申请单备案方法
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 备案申请单数据
	 * 
	 * @param contract
	 *            申请单表头
	 */
	public void putOnRecordFptAppHead(FptAppHead fptAppHead) {
		FptAppHead fptAppHeadTemp = null;
		if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
			fptAppHeadTemp = this.fptManageDao.findFptAppHeadByOutCopAppNo(
					fptAppHead.getOutCopAppNo(), DeclareState.PROCESS_EXE);
		} else {
			fptAppHeadTemp = this.fptManageDao.findFptAppHeadByInCopAppNo(
					fptAppHead.getOutCopAppNo(), DeclareState.PROCESS_EXE);
		}

		this.effectiveFptAppHead(fptAppHead, fptAppHeadTemp, null);
	}

	/**
	 * 备案申请单数据
	 * 
	 * @param fptAppHead
	 *            申请单表头
	 */
	private void writeFptAppHeadSeqNo(FptAppHead fptAppHead,
			CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();

		if (receiptResult != null) {
			//
			// 转入的报文中没有,不用写入
			//
			if (receiptResult instanceof FptReceiptResult
					&& FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
				//
				// 电子口岸统一编号
				//
				fptAppHead.setSeqNo(receiptResult.getSeqNo());
				this.fptManageDao.saveFptAppHead(fptAppHead);
			}
		}

	}

	/**
	 * 备案申请单数据
	 * 
	 * @param fptAppHead
	 *            申请单表头
	 */
	private void effectiveFptAppHead(FptAppHead fptAppHead,
			FptAppHead exingFptAppHead, CspReceiptResult receiptResult) {
		Map<Integer, Complex> mapExg = new HashMap<Integer, Complex>();
		if (exingFptAppHead != null) {
			List lsTemp = this.fptManageDao.findFptAppItems(exingFptAppHead
					.getId());
			for (int i = 0; i < lsTemp.size(); i++) {
				FptAppItem img = (FptAppItem) lsTemp.get(i);
				mapExg.put(img.getListNo(), img.getCodeTs());
			}
			this.deleteFptAppHead(exingFptAppHead);
		} else {// 第一次申报的时候根据回执反写统一编号和申请表编号，变更的时候不反写 by yp
			if (receiptResult != null) {
				//
				// 进的报文中没有,不用写入
				//
				if (receiptResult instanceof FptReceiptResult
						&& FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
					//
					// 电子口岸统一编号
					//
					fptAppHead.setSeqNo(receiptResult.getSeqNo());
				}
				//
				// <APP_NO> 申请表编号 X(12) 不填
				//
				fptAppHead.setAppNo(((FptReceiptResult) receiptResult)
						.getCustomsNo());

			}
		}
		// 回写申请表的有效日志（通过海关回执将申请表有效期返回）
		String note = receiptResult.getNote();
		System.out.println("------------note:" + note);
		if (note != null && note.trim().length() == 8) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			try {
				Date endDate = dateFormat.parse(note.trim());
				if (endDate != null) {
					fptAppHead.setEndDate(endDate);
					fptAppHead.setDeclareState(DeclareState.PROCESS_EXE);//备注：申请表收到有效日期之后，再将状态进行改变。
					fptAppHead.setModifyMarkState(ModifyMarkState.UNCHANGE);
				}
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
		this.fptManageDao.saveFptAppHead(fptAppHead);
		List fptAppItems = this.fptManageDao.findFptAppItemsStateChanged(
				fptAppHead.getId(), fptAppHead.getInOutFlag());
		for (int i = 0; i < fptAppItems.size(); i++) {
			FptAppItem fptAppItem = (FptAppItem) fptAppItems.get(i);
			if (ModifyMarkState.DELETED.equals(fptAppItem.getModifyMarkState())) {
				this.fptManageDao.deleteFptAppItem(fptAppItem);
			} else {
				if (ModifyMarkState.MODIFIED.equals(fptAppItem
						.getModifyMarkState())) {
					// Complex oldComplex = mapExg.get(FptAppItem.getSeqNum());
					// // 变更编码
					// if (!FptAppItem.getComplex().equals(oldComplex)) {
					// // changeFptAppItemComplex(FptAppItem);
					// }
				}
				fptAppItem.setModifyMarkState(ModifyMarkState.UNCHANGE);
				this.fptManageDao.saveFptAppItem(fptAppItem);
			}
		}

	}

	/**
	 * 判断交易数量是否超量
	 */
	public TempFptBillExeInfo findFptBillExeInfoByFpt(FptBillItem item) {
		TempFptBillExeInfo info = new TempFptBillExeInfo();
		// 申请表中数量
		double fptBillAmount = fptManageDao.findFptAppItem(item);
		if (FptInOutFlag.OUT.equals(item.getBillSort())) {
			// 判断转出企业
			// 发货数量
			double fptbillIssueAmount = fptManageDao
					.findFptBillIssueOrReceiveAmount(item, FptInOutFlag.OUT);
			// 收退货的数量
			double fptReceiveAmount = fptManageDao.findFptBillReceiveAmount(
					item, FptInOutFlag.OUT);

			if (FptBusinessType.FPT_BILL.equals(item.getFptBillHead()
					.getSysType())) {
				info.setFptBillAmount(fptBillAmount);// 申报数量
				info.setFptbillRemain(fptbillIssueAmount - fptReceiveAmount);// 已发货数量=发货数量-发退货数量
				info.setFptbillcurrentRemain(fptBillAmount
						- (fptbillIssueAmount - fptReceiveAmount)); // 当前可发货数量=申报数量-已发货数量
				System.out.println("@@@@@@@@@@" + fptbillIssueAmount + "##"
						+ fptReceiveAmount);
			} else if (FptBusinessType.FPT_BILL_BACK.equals(item
					.getFptBillHead().getSysType())) {
				info.setFptBillAmount(fptbillIssueAmount);
				info.setFptbillRemain(fptReceiveAmount);
				//当前可发退货数量
				double isbillcurrentremain = 0.0;
				isbillcurrentremain = fptbillIssueAmount - fptReceiveAmount;
				info.setFptbillcurrentRemain(Double.valueOf(CommonUtils.formatDoubleByDigit(isbillcurrentremain, 5)));
			}
		} else {
			// 判断转入企业
			// 收货数量
			double fptbillReceiveAmount = fptManageDao
					.findFptBillIssueOrReceiveAmount(item, FptInOutFlag.IN);
			// 发退货的数量
			double fptIssueAmount = fptManageDao.findFptBillReceiveAmount(item,
					FptInOutFlag.IN);
			if (FptBusinessType.FPT_BILL.equals(item.getFptBillHead()
					.getSysType())) {
				info.setFptBillAmount(fptBillAmount);// 申请表数量
				info.setFptbillRemain(fptbillReceiveAmount - fptIssueAmount);// 已收货数量=收货数量-发退货数量
				System.out.println("@@@@@@@@@@" + fptbillReceiveAmount + "##"
						+ fptIssueAmount);
				info.setFptbillcurrentRemain(fptBillAmount
						- fptbillReceiveAmount + fptIssueAmount);// 当前可发货数量=申请表数量
																	// - 收货数量 +
																	// 发退货数量
			} else if (FptBusinessType.FPT_BILL_BACK.equals(item
					.getFptBillHead().getSysType())) {
				info.setFptBillAmount(fptbillReceiveAmount);
				info.setFptbillRemain(fptIssueAmount);
				//当前可发退货数量
				double isbillcurrentremain = 0.0;
				isbillcurrentremain = fptbillReceiveAmount - fptIssueAmount;
				info.setFptbillcurrentRemain(Double.valueOf(CommonUtils.formatDoubleByDigit(isbillcurrentremain, 5)));
			}
		}
		return info;

	}

	/**
	 * 判断申请单是否可以备案
	 * 
	 * @param contract
	 *            申请单表头
	 */
	public String checkFptAppHeadForPutOnRecord(FptAppHead fptAppHead) {
		System.out.println("1----------------------------");
		StringBuffer message = new StringBuffer("");
		List<FptAppItem> lsImg = this.fptManageDao.findFptAppItems(
				fptAppHead.getId(), fptAppHead.getInOutFlag());
		for (FptAppItem img : lsImg) {
			if (img.getQty() == null || img.getQty() <= 0) {
				message.append("料件" + img.getListNo().toString() + "  "
						+ img.getName() + " 数量为空或等于零 \n");
			}
		}
		return message.toString();
	}

	/**
	 * 删除进出货转厂撤消资料
	 * 
	 */
	public void deleteCanelInletOutletTransferFactoryBill(
			FptCancelBill fptCanelBill) {
		this.fptManageDao.deleteFptCancelBill(fptCanelBill);
	}

	/** 获得临时的 */
	public List findTempFptAppItemByParentId(FptAppHead head) {
		List returnList = new ArrayList();
		List list = this.fptManageDao.findFptAppItems(head.getId(),
				head.getInOutFlag());
		for (int i = 0; i < list.size(); i++) {
			FptAppItem c = (FptAppItem) list.get(i);
			TempFptAppItem t = new TempFptAppItem();
			t.setIsSelected(false);
			t.setFptAppItem(c);
			returnList.add(t);
		}
		return returnList;
	}

	/**
	 * 申请单料件数据取整
	 * 
	 * @param list
	 *            是FptAppItem型，申请单料件
	 */
	public void saveFptAppItemAmountInteger(List<FptAppItem> list) {

		for (int i = 0; i < list.size(); i++) {
			FptAppItem fptAppItem = (FptAppItem) list.get(i);
			if (fptAppItem.getQty() != null) {
				Double amount = Double.valueOf(String.valueOf(Math
						.round(fptAppItem.getQty())));
				fptAppItem.setQty(amount);
				if (fptAppItem.getModifyMarkState().equals(
						ModifyMarkState.UNCHANGE)) {
					fptAppItem.setModifyMarkState(ModifyMarkState.MODIFIED);
				}
			}
			// if (contractImg.getTotalPrice() != null) {
			// Double totalPrice = Double.valueOf(String.valueOf(Math
			// .round(contractImg.getTotalPrice())));
			// contractImg.setTotalPrice(totalPrice);
			// }
			// fptAppItem.setTotalPrice(CommonUtils
			// .getDoubleExceptNull(fptAppItem.getQty())
			// * CommonUtils.getDoubleExceptNull(fptAppItem
			// .getUnitPrice()));
			this.fptManageDao.saveFptAppItem(fptAppItem);
		}
		// if (list.size() > 0) {
		// Contract contract = list.get(0).getContract();
		// this.statFptAppItemMoney(contract);
		// }
	}

	/** 新增转厂申请单明细 */
	public FptAppItem newFptAppItem(FptAppHead head) {
		FptAppItem item = new FptAppItem();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());

		item.setListNo(listNo + 1);
		item.setCompany(CommonUtils.getCompany());
		item.setFptAppHead(head);
		// item.setInEmsNo(head.getEmsNo());
		item.setInOutFlag(head.getInOutFlag());
		item.setModifyMarkState(ModifyMarkState.ADDED);

		this.fptManageDao.saveFptAppItem(item);
		return item;
	}

	/**
	 * --------------------------------------------结转单据转入方下载备案资料----------------
	 * --------
	 */
	/**
	 * 删除备案下载资料
	 * 
	 * @param recordationDataDownLoad
	 */
	public void deleteRecordationDataDownLoad(
			FptDownData recordationDataDownLoad) {
		this.fptManageDao.deleteFptDownData(recordationDataDownLoad);
	}

	/**
	 * 收/发货单海关申报
	 * 
	 * @param head
	 * 
	 * @return RecordationDataDownLoad
	 */
	public DeclareFileInfo applyFptDownData(FptDownData head, String taskId) {
		head = this.fptManageDao.findFptDownDataById(head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<FptDownData> lsFptHead = new ArrayList<FptDownData>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		FptSignInfo signInfo = (FptSignInfo) fptMessageLogic.getCspPtsSignInfo(
				info, null);
		signInfo.setTradeCode(head.getTradeCode());
		signInfo.setColDcrTime(0);
		// if (DownLoadState.FPT_APP.equals(head.getDownLoadState())) {
		// signInfo.setSysType("A");
		// } else if (DownLoadState.FPT_BILL.equals(head.getDownLoadState())) {
		// signInfo.setSysType("C");
		// } else {
		// signInfo.setSysType("B");
		// }
		// signInfo.setSysType(head.getDownLoadState());
		signInfo.setSignDate(new Date());
		signInfo.setAppNo(head.getAppNo());
		signInfo.setCopNo(head.getOutCopNo());
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setDeclareState(DeclareState.WAIT_EAA);
		lsFptHead.add(head);
		String formatFile = "FptDownDataFormat.xml";
		hmData.put("FptDownDataSign", lsSignInfo);
		hmData.put("FptDownDataHead", lsFptHead);
		DeclareFileInfo fileInfo = fptMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.fptManageDao.saveFptDownData(head);
		return fileInfo;
	}

	/**
	 * 
	 * 转厂申请单报文生成
	 * 
	 * @param head
	 * 
	 * @return FptBillHead
	 */
	public DeclareFileInfo applyFptAppHead(FptAppHead head, String taskId) {
		head = this.fptManageDao.findFptAppHeadById(head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<FptAppHead> lsFptHead = new ArrayList<FptAppHead>();
		List<FptAppItem> lsFptBill = new ArrayList<FptAppItem>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		FptSignInfo signInfo = (FptSignInfo) fptMessageLogic.getCspPtsSignInfo(
				info, null);
		//
		// 获得转入转出标识
		//
		String fptInOutFlag = head.getInOutFlag();
		String declareState = head.getDeclareState();
		System.out.println("bbb" + fptInOutFlag);
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
			signInfo.setCopNo(head.getOutCopAppNo());
		} else {
			signInfo.setCopNo(head.getOutCopAppNo());// 转入方发送结转申请表时，内部编号及加工单位代码均取转出方
			signInfo.setTradeCode(head.getOutTradeCode());
		}
		// /**
		// * 变更报文,表头统一编号和申请表编号为空
		// */
		// if ("4".equals(head.getDeclareState())) {
		// signInfo.setSeqNo("");
		// }

		signInfo.setColDcrTime(0);

		signInfo.setAppNo(head.getAppNo());
		signInfo.setSeqNo(head.getSeqNo());
		signInfo.setInOutFlag(head.getInOutFlag());
		signInfo.setSignDate(new Date());
		if (DeclareState.CHANGING_EXE.equals(head.getDeclareState())) {
			signInfo.setDeclareType(DelcareType.MODIFY);
		}
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		if (FptInOutFlag.OUT.equals(fptInOutFlag)) {
			head.setOutDate(new Date());
		} else {
			head.setInDate(new Date());
		}
		// 申报类型 1－备案申请 2－变更申请
		// if (head.getDelcareType() == null ||
		// "".equals(head.getDelcareType())) {
		// head.setDelcareType(DelcareType.APPLICATION);
		// } else {
		// head.setDelcareType(DelcareType.MODIFY);
		// }
		/**
		 * 变更报文,表头统一编号和申请表编号为空
		 */
		// if ("4".equals(head.getDeclareState())) {
		// head.setSeqNo("");
		// head.setAppNo("");
		// }
		head.setDeclareState(DeclareState.WAIT_EAA);
		lsFptHead.add(head);
		lsFptBill = this.fptManageDao.findFptAppItemsStateChanged(head.getId(),
				fptInOutFlag);

		String formatFile = "FptAppInFormat.xml";// 转入
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {// 转出
			formatFile = "FptAppOutFormat.xml";
		}
		hmData.put("FptAppSign", lsSignInfo);
		hmData.put("FptAppHead", lsFptHead);
		hmData.put("FptAppItem", lsFptBill);
		DeclareFileInfo fileInfo = fptMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.fptManageDao.saveFptAppHead(head);
		return fileInfo;
	}

	/**
	 * 转厂申请单回执处理
	 * 
	 * @param fptAppHead
	 * @param existFptAppHead
	 * @return
	 */
	public String processFptAppHead(final FptAppHead fptAppHead,
			final FptAppHead existFptAppHead, List lsReturnFile) {
		String copAppNo = "";
		if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
			copAppNo = fptAppHead.getOutCopAppNo();
		} else {
			copAppNo = fptAppHead.getInCopAppNo();
		}
		return fptMessageLogic.processMessage(FptBusinessType.FPT_APP,
				copAppNo, new FptProcessMessage() {

					@Override
					public void processHandling(CspReceiptResult receiptResult) {
						String chkMark = receiptResult.getChkMark();
						if (FptProcessResult.IMP_CENTDB_SUCCESSD
								.equals(chkMark)) {
							writeFptAppHeadSeqNo(fptAppHead, receiptResult);
						} else if (FptProcessResult.CHECK_PASS_ALL
								.equals(chkMark) && checkReceiptNoteIsDate(receiptResult.getNote())) {
							effectiveFptAppHead(fptAppHead, existFptAppHead,
									receiptResult);
						} else if (FptProcessResult.IMP_CENTDB_FAIL
								.equals(chkMark)
								|| FptProcessResult.IN_STOCK_FAIL
										.equals(chkMark)
								|| FptProcessResult.IMP_CENTDB_FAIL
										.equals(chkMark)
								|| FptProcessResult.CHECK_BACK.equals(chkMark)
								|| FptProcessResult.QP_FAIL.equals(chkMark)) {
							resetFptAppHead(fptAppHead, existFptAppHead);
						}

					}
				}, lsReturnFile);
	}

	/** reset fptAppHead */
	private void resetFptAppHead(FptAppHead fptAppHead,
			FptAppHead existFptAppHead) {
		if (existFptAppHead == null) {
			fptAppHead.setDeclareState(DeclareState.APPLY_POR);
			if (FptInOutFlag.OUT.equals(fptAppHead.getInOutFlag())) {
				fptAppHead.setSeqNo(null);
			}
		} else {
			fptAppHead.setDeclareState(DeclareState.CHANGING_EXE);
		}
		this.fptManageDao.saveFptAppHead(fptAppHead);
	}

    /**
     * 判断回执中的备注是否是日期类型
     *
     * @param note
     * @return
     */
    private boolean checkReceiptNoteIsDate(String note) {
        if (note != null && note.trim().length() == 8) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                Date endDate = dateFormat.parse(note.trim());
                if (endDate != null) {
                    return true;
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

	/** 新增转厂申请单明细 来自 bcs 合同成品 */
	public List<FptAppItem> newFptAppItemByContractExg(FptAppHead head,
			List<ContractExg> contractExgs) {
		List<FptAppItem> list = new ArrayList<FptAppItem>();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());
		int index = 0;
		for (ContractExg c : contractExgs) {
			index++;
			FptAppItem f = new FptAppItem();
			f.setListNo(listNo + index);
			f.setCompany(CommonUtils.getCompany());
			f.setFptAppHead(head);
			//
			// 转入必填
			//
			if (FptInOutFlag.IN.equals(head.getInOutFlag())) {
				f.setInEmsNo(c.getContract().getEmsNo());
			}
			f.setTrNo(c.getSeqNum());
			f.setCodeTs(c.getComplex());
			f.setCurr(null);
			f.setName(c.getName());
			f.setSpec(c.getSpec());
			f.setUnit(c.getUnit());
			f.setUnit1(c.getComplex().getFirstUnit());
			f.setUnitPrice(c.getDeclarePrice());
			f.setInOutFlag(head.getInOutFlag());
			f.setModifyMarkState(ModifyMarkState.ADDED);
			this.fptManageDao.saveFptAppItem(f);
			list.add(f);
		}
		return list;
	}

	/** 新增转厂申请单明细 来自 bcs 合同料件 */
	public List<FptAppItem> newFptAppItemByContractImg(FptAppHead head,
			List<ContractImg> contractImgs) {
		List<FptAppItem> list = new ArrayList<FptAppItem>();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());
		int index = 0;
		for (ContractImg c : contractImgs) {
			index++;
			FptAppItem f = new FptAppItem();
			f.setListNo(listNo + index);
			f.setCompany(CommonUtils.getCompany());
			f.setFptAppHead(head);
			//
			// 转入必填
			//
			if (FptInOutFlag.IN.equals(head.getInOutFlag())) {
				f.setInEmsNo(c.getContract().getEmsNo());
			}
			f.setTrNo(c.getSeqNum());
			f.setCodeTs(c.getComplex());
			f.setCurr(null);
			f.setName(c.getName());
			f.setSpec(c.getSpec());
			f.setUnit(c.getUnit());
			f.setUnit1(c.getComplex().getFirstUnit());
			f.setUnitPrice(c.getDeclarePrice());
			f.setInOutFlag(head.getInOutFlag());
			f.setModifyMarkState(ModifyMarkState.ADDED);
			this.fptManageDao.saveFptAppItem(f);
			list.add(f);
		}
		return list;
	}

	/** 新增转厂申请单明细 来自 DZSC 正在执行的电子手册通关备案里的料件 */
	public List<FptAppItem> newFptAppItemByDzscEmsImgBill(FptAppHead head,
			List<DzscEmsImgBill> dzscEmsImgBills) {
		List<FptAppItem> list = new ArrayList<FptAppItem>();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());
		int index = 0;
		for (DzscEmsImgBill c : dzscEmsImgBills) {
			index++;
			FptAppItem f = new FptAppItem();
			f.setListNo(listNo + index);
			f.setCompany(CommonUtils.getCompany());
			f.setFptAppHead(head);
			//
			// 转入必填
			//
			if (FptInOutFlag.IN.equals(head.getInOutFlag())) {
				f.setInEmsNo(c.getDzscEmsPorHead().getEmsNo());
			}
			f.setTrNo(c.getSeqNum());
			f.setCodeTs(c.getComplex());
			f.setCurr(null);
			f.setName(c.getName());
			f.setSpec(c.getSpec());
			f.setUnit(c.getUnit());
			f.setUnit1(c.getComplex().getFirstUnit());
			// f.setUnitPrice(c.get);
			f.setInOutFlag(head.getInOutFlag());
			f.setModifyMarkState(ModifyMarkState.ADDED);
			this.fptManageDao.saveFptAppItem(f);
			list.add(f);
		}
		return list;
	}

	/** 新增转厂申请单明细 来自 DZSC 正在执行的电子手册通关备案里的成品 */
	public List<FptAppItem> newFptAppItemByDzscEmsExgBill(FptAppHead head,
			List<DzscEmsExgBill> dzscEmsExgBills) {
		List<FptAppItem> list = new ArrayList<FptAppItem>();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());
		int index = 0;
		for (DzscEmsExgBill c : dzscEmsExgBills) {
			index++;
			FptAppItem f = new FptAppItem();
			f.setListNo(listNo + index);
			f.setCompany(CommonUtils.getCompany());
			f.setFptAppHead(head);
			//
			// 转入必填
			//
			if (FptInOutFlag.IN.equals(head.getInOutFlag())) {
				f.setInEmsNo(c.getDzscEmsPorHead().getEmsNo());
			}
			f.setTrNo(c.getSeqNum());
			f.setCodeTs(c.getComplex());
			f.setCurr(null);
			f.setName(c.getName());
			f.setSpec(c.getSpec());
			f.setUnit(c.getUnit());
			f.setUnit1(c.getComplex().getFirstUnit());
			// f.setUnitPrice(c.get);
			f.setInOutFlag(head.getInOutFlag());
			f.setModifyMarkState(ModifyMarkState.ADDED);
			this.fptManageDao.saveFptAppItem(f);
			list.add(f);
		}
		return list;
	}

	/** 新增转厂申请单明细 来自 Bcus 正在执行的电子帐册里的成品 */
	public List<FptAppItem> newFptAppItemByEmsHeadH2kExg(FptAppHead head,
			List<EmsHeadH2kExg> emsHeadH2kExgs) {
		List<FptAppItem> list = new ArrayList<FptAppItem>();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());
		int index = 0;
		for (EmsHeadH2kExg c : emsHeadH2kExgs) {
			index++;
			FptAppItem f = new FptAppItem();
			f.setListNo(listNo + index);
			f.setCompany(CommonUtils.getCompany());
			f.setFptAppHead(head);
			//
			// 转入必填
			//
			if (FptInOutFlag.IN.equals(head.getInOutFlag())) {
				f.setInEmsNo(c.getEmsHeadH2k().getEmsNo());
			}
			f.setTrNo(c.getSeqNum());
			f.setCodeTs(c.getComplex());
			f.setCurr(c.getCurr());
			f.setName(c.getName());
			f.setSpec(c.getSpec());
			f.setUnit(c.getUnit());
			f.setUnit1(c.getComplex().getFirstUnit());
			f.setUnitPrice(c.getDeclarePrice());
			f.setInOutFlag(head.getInOutFlag());
			f.setModifyMarkState(ModifyMarkState.ADDED);
			this.fptManageDao.saveFptAppItem(f);
			list.add(f);
		}
		return list;
	}

	/** 新增转厂申请单明细 来自 Bcus 正在执行的电子帐册里的料件 */
	public List<FptAppItem> newFptAppItemByEmsHeadH2kImg(FptAppHead head,
			List<EmsHeadH2kImg> emsHeadH2kImgs) {
		List<FptAppItem> list = new ArrayList<FptAppItem>();
		int listNo = fptManageDao.findMaxValueByFptAppItem(head.getId(),
				head.getInOutFlag());
		int index = 0;
		for (EmsHeadH2kImg c : emsHeadH2kImgs) {
			index++;
			FptAppItem f = new FptAppItem();
			f.setListNo(listNo + index);
			f.setCompany(CommonUtils.getCompany());
			f.setFptAppHead(head);
			//
			// 转入必填
			//
			if (FptInOutFlag.IN.equals(head.getInOutFlag())) {
				f.setInEmsNo(c.getEmsHeadH2k().getEmsNo());
			}
			f.setTrNo(c.getSeqNum());
			f.setCodeTs(c.getComplex());
			f.setCurr(c.getCurr());
			f.setName(c.getName());
			f.setSpec(c.getSpec());
			f.setUnit(c.getUnit());
			f.setUnit1(c.getComplex().getFirstUnit());
			f.setUnitPrice(c.getDeclarePrice());
			f.setInOutFlag(head.getInOutFlag());
			f.setModifyMarkState(ModifyMarkState.ADDED);
			this.fptManageDao.saveFptAppItem(f);
			list.add(f);
		}
		return list;
	}

	public List findCustomOrderForToFptAppHead(List customOrders) {
		List rlist = new ArrayList();
		List list = this.fptManageDao
				.findCustomOrderForToFptAppHead(customOrders);
		for (int i = 0; i < list.size(); i++) {
			CustomOrderDetail cod = (CustomOrderDetail) list.get(i);
			TempFptAppheadAndOrder temp = new TempFptAppheadAndOrder();
			temp.setDetial(cod);
			temp.setIsSelected(false);
			temp.setTempAmount(cod.getAmount());
			rlist.add(temp);
		}
		return rlist;
	}

	/**
	 * 将相同料号订单体要转的数量累加
	 * 
	 * @param list
	 */
	public List combination(List<TempFptAppheadAndOrder> list) {
		Map<String, TempFptAppheadAndOrder> map = new HashMap();
		Map<String, List<CustomOrderDetail>> dmap = new HashMap();// 记录有重复的
		for (TempFptAppheadAndOrder data : list) {
			String ptno = data.getDetial().getMateriel().getPtNo();
			Double ss = data.getTempAmount() == null ? 0.0 : data
					.getTempAmount();
			if (map.get(ptno) == null) {
				map.put(ptno, data);
				List<CustomOrderDetail> nlist = new ArrayList();
				nlist.add(data.getDetial());
				dmap.put(ptno, nlist);
			} else {
				data = map.get(ptno);
				data.setTempAmount((data.getTempAmount() == null ? 0.0 : data
						.getTempAmount()) + ss);
				List nlist = dmap.get(ptno);
				nlist.add(data.getDetial());
			}
		}
		List rlist = new ArrayList();
		rlist.add(new ArrayList(map.values()));
		rlist.add(dmap);
		return rlist;
	}

	/**
	 * 获取内部归并,以料号为KEY,放入MAP中
	 * 
	 * @param projectType
	 * @param materielType
	 */
	private Map getInnerMap(int projectType, String materielType, String emsNo) {
		Map rmap = new HashMap();
		List inner = this.fptManageDao.findInnerMergeByProjectType(projectType,
				materielType, emsNo);// 获取内部归并
		for (int i = 0; i < inner.size(); i++) {
			Object[] objs = (Object[]) inner.get(i);
			if (objs[0] == null) {
				continue;
			}
			rmap.put(objs[0], objs[1]);
		}
		return rmap;
	}

	private String getErrorInfoByPtno(int projectType, String materielType,
			String emsNo) {
		List rList = this.fptManageDao.findSeqNumByProjectType(projectType,
				materielType, emsNo);
		switch (projectType) {
		case ProjectType.BCS:
			for (int i = 0; i < rList.size(); i++) {

			}
			break;
		case ProjectType.DZSC:

			break;
		case ProjectType.BCUS:

			break;

		default:
			break;
		}
		return "";
	}

	/**
	 * 订单转转厂申请单
	 * 
	 * @param list
	 */
	public List makeCustomsOrderToFptAppHead(FptAppHead head,
			List<TempFptAppheadAndOrder> list, int projectType,
			String materielType, String emsNo) {
		List returnList = new ArrayList();
		List<FptAppItem> detailList = new ArrayList();
		String error = "";
		List slist = combination(list);// 将相同料号订单体要转的数量累加
		List<TempFptAppheadAndOrder> nlist = (List<TempFptAppheadAndOrder>) slist
				.get(0);
		Map<String, List<CustomOrderDetail>> doubleMap = (Map<String, List<CustomOrderDetail>>) slist
				.get(1);
		Map innerMap = getInnerMap(projectType, materielType, emsNo);// 从工厂料号到报关十码
		if (materielType.equals(MaterielType.MATERIEL)) {
			head = newFptAppHead(FptInOutFlag.IN);
		} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
			head = newFptAppHead(FptInOutFlag.OUT);
		}
		head.setProjectType(projectType);
		head.setEmsNo(emsNo);
		int listNo = 0;
		switch (projectType) {
		case ProjectType.BCS:
			for (TempFptAppheadAndOrder data : nlist) {
				CustomOrderDetail cod = data.getDetial();
				FptAppItem item = new FptAppItem();
				if (materielType.equals(MaterielType.MATERIEL)) {
					ContractImg img = (ContractImg) innerMap.get(cod
							.getMateriel().getPtNo());
					if (img == null) {
						error += "料号：" + cod.getMateriel().getPtNo()
								+ "在（物料与报关对应、备案资料库、手册号为:" + emsNo
								+ " 的手册）中不存在！\n";
						continue;
					}
					listNo++;
					item.setListNo(listNo);
					item.setName(img.getName());
					item.setSpec(img.getSpec());
					item.setCodeTs(img.getComplex());
					item.setUnit(img.getUnit());
					item.setUnitPrice(cod.getUnitPrice());
					item.setQty(data.getTempAmount());
					item.setInEmsNo(emsNo);
					item.setInOutFlag(FptInOutFlag.IN);
					item.setTrNo(img.getSeqNum());
					item.setNote(cod.getId());// 暂时存放订单表体ID
					item.setId(cod.getMateriel().getPtNo());// 暂时存放订单表体物料料号

					detailList.add(item);
				} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
					ContractExg exg = (ContractExg) innerMap.get(cod
							.getMateriel().getPtNo());
					if (exg == null) {
						error += "料号：" + cod.getMateriel().getPtNo()
								+ "在（物料与报关对应、备案资料库、手册号为:" + emsNo
								+ " 的手册）中不存在！\n";
						continue;
					}
					listNo++;
					item.setListNo(listNo);
					item.setName(exg.getName());
					item.setSpec(exg.getSpec());
					item.setCodeTs(exg.getComplex());
					item.setUnit(exg.getUnit());
					item.setUnitPrice(cod.getUnitPrice());
					item.setQty(data.getTempAmount());
					item.setInOutFlag(FptInOutFlag.OUT);
					item.setTrNo(exg.getSeqNum());
					item.setNote(cod.getId());// 暂时存放订单表体ID
					item.setId(cod.getMateriel().getPtNo());// 暂时存放订单表体物料料号
					detailList.add(item);
				}
			}
			break;
		case ProjectType.DZSC:
			for (TempFptAppheadAndOrder data : nlist) {
				CustomOrderDetail cod = data.getDetial();
				FptAppItem item = new FptAppItem();
				if (materielType.equals(MaterielType.MATERIEL)) {
					DzscEmsImgBill img = (DzscEmsImgBill) innerMap.get(cod
							.getMateriel().getPtNo());
					if (img == null) {
						error += "料号：" + cod.getMateriel().getPtNo()
								+ "在（手册商品归并、手册号为:" + emsNo + " 的手册）中不存在！\n";
						continue;
					}
					listNo++;
					item.setListNo(listNo);
					item.setName(img.getName());
					item.setSpec(img.getSpec());
					item.setCodeTs(img.getComplex());
					item.setUnit(img.getUnit());
					item.setUnitPrice(cod.getUnitPrice());
					item.setQty(data.getTempAmount());
					item.setInEmsNo(emsNo);
					item.setInOutFlag(FptInOutFlag.IN);
					item.setTrNo(img.getSeqNum());
					item.setNote(cod.getId());// 暂时存放订单表体ID
					item.setId(cod.getMateriel().getPtNo());// 暂时存放订单表体物料料号
					detailList.add(item);
				} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
					DzscEmsImgBill exg = (DzscEmsImgBill) innerMap.get(cod
							.getMateriel().getPtNo());
					if (exg == null) {
						error += "料号：" + cod.getMateriel().getPtNo()
								+ "在（手册商品归并、手册号为:" + emsNo + " 的手册）中不存在！\n";
						continue;
					}
					listNo++;
					item.setListNo(listNo);
					item.setName(exg.getName());
					item.setSpec(exg.getSpec());
					item.setCodeTs(exg.getComplex());
					item.setUnit(exg.getUnit());
					item.setUnitPrice(cod.getUnitPrice());
					item.setQty(data.getTempAmount());
					item.setInOutFlag(FptInOutFlag.OUT);
					item.setTrNo(exg.getSeqNum());
					item.setNote(cod.getId());// 暂时存放订单表体ID
					item.setId(cod.getMateriel().getPtNo());// 暂时存放订单表体物料料号
					detailList.add(item);
				}
			}
			break;
		case ProjectType.BCUS:
			for (TempFptAppheadAndOrder data : nlist) {
				CustomOrderDetail cod = data.getDetial();
				FptAppItem item = new FptAppItem();
				if (materielType.equals(MaterielType.MATERIEL)) {
					EmsHeadH2kImg img = (EmsHeadH2kImg) innerMap.get(cod
							.getMateriel().getPtNo());
					if (img == null) {
						error += "料号：" + cod.getMateriel().getPtNo()
								+ "在（内部归并、归并关系、手册号为:" + emsNo + " 的手册）中不存在！\n";
						continue;
					}
					listNo++;
					item.setListNo(listNo);
					item.setName(img.getName());
					item.setSpec(img.getSpec());
					item.setCodeTs(img.getComplex());
					item.setUnit(img.getUnit());
					item.setUnitPrice(cod.getUnitPrice());
					item.setQty(data.getTempAmount());
					item.setInEmsNo(emsNo);
					item.setInOutFlag(FptInOutFlag.IN);
					item.setTrNo(img.getSeqNum());
					item.setNote(cod.getId());// 暂时存放订单表体ID
					item.setId(cod.getMateriel().getPtNo());// 暂时存放订单表体物料料号
					detailList.add(item);
				} else if (materielType.equals(MaterielType.FINISHED_PRODUCT)) {
					EmsHeadH2kExg exg = (EmsHeadH2kExg) innerMap.get(cod
							.getMateriel().getPtNo());
					if (exg == null) {
						error += "料号：" + cod.getMateriel().getPtNo()
								+ "在（内部归并、归并关系、手册号为:" + emsNo + " 的手册）中不存在！\n";
						continue;
					}
					listNo++;
					item.setListNo(listNo);
					item.setName(exg.getName());
					item.setSpec(exg.getSpec());
					item.setCodeTs(exg.getComplex());
					item.setUnit(exg.getUnit());
					item.setUnitPrice(cod.getUnitPrice());
					item.setQty(data.getTempAmount());
					item.setInOutFlag(FptInOutFlag.OUT);
					item.setTrNo(exg.getSeqNum());
					item.setNote(cod.getId());// 暂时存放订单表体ID
					item.setId(cod.getMateriel().getPtNo());// 暂时存放订单表体物料料号
					detailList.add(item);
				}
			}
			break;
		default:
			break;
		}
		if (error.equals("")) {
			saveFptAppHeadAndDetail(head, detailList, doubleMap);
			returnList.add(head);
			returnList.add(detailList);

		} else {
			returnList.add(null);
			returnList.add(error);
		}
		return returnList;
	}

	private void saveFptAppHeadAndDetail(FptAppHead head,
			List<FptAppItem> items,
			Map<String, List<CustomOrderDetail>> doubleMap) {
		this.fptManageDao.saveOrUpdate(head);
		for (FptAppItem item : items) {
			String ptno = item.getId();
			String customOrderDetailId = item.getNote();
			item.setId(null);
			item.setNote(null);
			item.setFptAppHead(head);
			this.fptManageDao.saveOrUpdate(item);
			if (doubleMap.get(ptno) != null) {
				List<CustomOrderDetail> dlist = doubleMap.get(ptno);
				for (CustomOrderDetail data : dlist) {
					FptAppItemAndOrderDetail fo = new FptAppItemAndOrderDetail();
					fo.setAmount(data.getAmount());
					fo.setTradeData(new Date());
					fo.setFptAppItemId(item.getId());
					fo.setOrderDetail(data.getId());
					data.setTransNum(data.getAmount());
					this.fptManageDao.saveOrUpdate(fo);
					this.fptManageDao.saveOrUpdate(data);
				}
			} else {
				System.out.println("kkkkkkkkkkkkkkkkkkkkkk!");
			}

		}
	}

	/**
	 * 申报撤消单数据
	 * 
	 * @param head
	 * @param taskId
	 * @return
	 */
	public DeclareFileInfo applyFptCancelBill(FptCancelBill head, String taskId) {
		head = (FptCancelBill) this.fptManageDao.load(FptCancelBill.class,
				head.getId());
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		List<FptCancelBill> lsFptHead = new ArrayList<FptCancelBill>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		if (info != null) {
			info.setMethodName("正在获取要申报的资料");
		}
		FptSignInfo signInfo = (FptSignInfo) fptMessageLogic.getCspPtsSignInfo(
				info, null);
		signInfo.setCopNo(head.getCopNo());
		signInfo.setColDcrTime(0);
		signInfo.setAppNo(head.getAppNo());
		signInfo.setSeqNo(head.getSeqNo());
		signInfo.setSignDate(new Date());
		lsSignInfo.add(signInfo);
		if (signInfo.getIdCard() == null
				|| "".equals(signInfo.getIdCard().trim())) {
			throw new RuntimeException("签名信息中操作员卡号为空");
		}
		if (signInfo.getIdCard().length() < 4) {
			throw new RuntimeException("签名信息中操作员卡号的长度小于4位");
		}
		head.setDeclareState(DeclareState.WAIT_EAA);
		lsFptHead.add(head);
		String formatFile = "FptCancelBillFormat.xml";
		hmData.put("FptCancelBillSign", lsSignInfo);
		hmData.put("FptCancelBillHead", lsFptHead);
		DeclareFileInfo fileInfo = fptMessageLogic.exportMessage(formatFile,
				hmData, info);
		this.fptManageDao.saveFptCancelBill(head);
		return fileInfo;
	}

	/**
	 * 撤消数据处理回执
	 * 
	 */
	public String processFptCancelBill(final FptCancelBill head,
			List lsReturnFile) {
		return this.fptMessageLogic.processMessage(
				FptBusinessType.FPT_CANCEL_BILL, head.getCopNo(),
				new FptProcessMessage() {
					// public void failureHandling(
					// TempCspReceiptResultInfo tempReceiptResult) {
					// head.setDeclareState(DeclareState.APPLY_POR);
					// fptManageDao.saveFptCancelBill(head);
					// }
					//
					// public void successHandling(
					// TempCspReceiptResultInfo tempReceiptResult) {
					// CspReceiptResult receiptResult = tempReceiptResult
					// .getReceiptResult();
					// head.setDeclareState(DeclareState.PROCESS_EXE);
					// fptManageDao.saveFptCancelBill(head);
					// FptBillHead fptBillHead = fptManageDao
					// .findFptBillHeadBySeqNo(receiptResult
					// .getSeqNo());
					// fptBillHead.setIsCancel(true);
					// fptManageDao.saveFptBillHead(fptBillHead);
					// //
					// transferFactoryManageDao.findFptBillHeadByCopBillNo(impExpGoodsFlag,
					// // index, copTrNo)(receiptResult.getSeqNo());
					// }

					@Override
					public void processHandling(CspReceiptResult receiptResult) {
						String chkMark = receiptResult.getChkMark();
						if (FptProcessResult.P.equals(chkMark)
								|| FptProcessResult.S.equals(chkMark)) {
							head.setDeclareState(DeclareState.PROCESS_EXE);
							fptManageDao.saveFptCancelBill(head);
							FptBillHead fptBillHead = fptManageDao
									.findFptBillHeadBySeqNo(receiptResult
											.getSeqNo());
							fptBillHead.setAppState(DeclareState.IS_CANCELED);
							fptManageDao.saveFptBillHead(fptBillHead);
						} else if (FptProcessResult.IMP_CENTDB_FAIL
								.equals(chkMark)
								|| FptProcessResult.IN_STOCK_FAIL
										.equals(chkMark)
								|| FptProcessResult.IMP_CENTDB_FAIL
										.equals(chkMark)
								|| FptProcessResult.CHECK_BACK.equals(chkMark)
								|| FptProcessResult.QP_FAIL.equals(chkMark)) {
							head.setDeclareState(DeclareState.APPLY_POR);
							fptManageDao.saveFptCancelBill(head);
						}

					}
				}, lsReturnFile);
	}

	/**
	 * 下载数据处理回执
	 * 
	 */
	public String processFptDownData(final FptDownData head, List lsReturnFile) {
		return this.fptMessageLogic.processMessage(
				FptBusinessType.FPT_DOWN_DATA, head.getOutCopNo(),
				new FptProcessMessage() {
					@Override
					public void processHandling(CspReceiptResult receiptResult) {
						String chkMark = receiptResult.getChkMark();
						if (FptProcessResult.X.equals(chkMark)) {
							effectiveDownData(head,
									(FptReceiptResult) receiptResult);
						} else if (FptProcessResult.IMP_CENTDB_FAIL
								.equals(chkMark)
								|| FptProcessResult.IN_STOCK_FAIL
										.equals(chkMark)
								|| FptProcessResult.IMP_CENTDB_FAIL
										.equals(chkMark)
								|| FptProcessResult.CHECK_BACK.equals(chkMark)
								|| FptProcessResult.QP_FAIL.equals(chkMark)) {
							head.setDeclareState(DeclareState.APPLY_POR);
							fptManageDao.saveFptDownData(head);
						}

					}
				}, lsReturnFile);
	}

	/**
	 * 下载数据成功
	 * 
	 * @param head
	 * @param mapDownData
	 */
	private void effectiveDownData(FptDownData head,
			FptReceiptResult receiptResult) {
		Map mapDownData = receiptResult.getMapDownData();
		head.setDeclareState(DeclareState.PROCESS_EXE);
		fptManageDao.saveFptDownData(head);
		if ("A".equals(head.getDownLoadState())) {// 申请表
			writeOutAppBill(mapDownData);
		} else if ("B".equals(head.getDownLoadState())) {// 退货单
			writeBillBack(mapDownData);
		} else if ("C".equals(head.getDownLoadState())) {// 收发货单
			writeBill(mapDownData);
		}
	}

	/**
	 * 反写转入方申请单的转出方数据
	 * 
	 * @param mapDownData
	 */
	private void writeOutAppBill(Map mapDownData) {
		Map<String, String> mapAppHeadData = (Map<String, String>) mapDownData
				.get("FPT_APP_OUT_HEAD");
		System.out.println("=======mapAppHeadData" + mapAppHeadData.size());
		if (mapAppHeadData != null && !mapAppHeadData.isEmpty()) {

			// 电子口岸统一编号
			String seqNo = mapAppHeadData.get("SEQ_NO");
			// 申请备案[初始状态]的申请单
			FptAppHead fptAppHead = this.fptManageDao
					.findFptAppHeadForDownData(seqNo, FptInOutFlag.IN,
							DeclareState.APPLY_POR);
			if (fptAppHead == null) {
				// [变更状态]的申请单
				fptAppHead = this.fptManageDao.findFptAppHeadForDownData(seqNo,
						FptInOutFlag.IN, DeclareState.CHANGING_EXE);
			}
			System.out.println("=======fptAppHead" + fptAppHead);
			if (fptAppHead == null) {
				// throw new RuntimeException("没有找到电子口岸统一编号为:" + seqNo
				// + "的初始状态或变更状态转入申请单");
				fptAppHead = new FptAppHead();
				fptAppHead.setDeclareState(DeclareState.APPLY_POR);
				fptAppHead.setSeqNo(seqNo);
				fptAppHead.setInOutFlag(FptInOutFlag.IN);
				// 转入内部编号
				String inCopAppNo = (this.fptMessageLogic
						.getNewCopEntNoFptBill("FptAppHead", "inCopAppNo", "F",
								FptBusinessType.FPT_APP, FptInOutFlag.IN));
				System.out.println("inCopAppNo" + inCopAppNo);
				fptAppHead.setInCopAppNo(inCopAppNo);
			}

			String outTradeCode = mapAppHeadData.get("OUT_TRADE_CODE");
			String outTradeName = mapAppHeadData.get("OUT_TRADE_NAME");
			
			Brief brief=null;
			List briefList=this.bcsImpExpRequestDao.findBriefByCode(outTradeCode);
			if(!briefList.isEmpty()){
				brief=(Brief)briefList.get(0);				
				if(!brief.getName().equals(outTradeName)){//如果海关注册公司编码一致，名称不一致，则修改名称。
					brief.setName(outTradeName);
					this.bcsImpExpRequestDao.saveOrUpdate(brief);
				}
			}else{
				briefList=this.bcsImpExpRequestDao.findBriefByName(outTradeName);
				if(!briefList.isEmpty()){//如果名称一致，编码不一致，则此海关注册公司进行作废。
					brief=(Brief)briefList.get(0);
					brief.setName(brief.getName()+"(已作废)");
					this.bcsImpExpRequestDao.saveOrUpdate(brief);
					brief=null;
				}
			}
			/**
			 * 如果海关注册公司不存在，则生成一个。
			 */
			if(brief==null){
				brief=new Brief();
				brief.setCode(outTradeCode);
				brief.setName(outTradeName);
				this.bcsImpExpRequestDao.saveOrUpdate(brief);
			}
			
			// HH2013.10.22 供应商
			List<ScmCoc> listCocsOUT = bcsImpExpRequestDao
					.findScmCocByNameAndType(
							mapAppHeadData.get("OUT_TRADE_NAME"), true, false);
			ScmCoc outScmCoc = listCocsOUT.isEmpty() ? null : listCocsOUT
					.get(0);
			if(outScmCoc==null){
				throw new RuntimeException("没有找到名称是："+outTradeName+"的供应商。");
			}else{
				outScmCoc.setBrief(brief);
				this.bcsImpExpRequestDao.saveOrUpdate(outScmCoc);
			}
			fptAppHead.setScmCoc(outScmCoc);
			// 项目类型
			FptParameterSet parameterSet = this.fptManageDao
					.findFptParameterSet();
			int projectType = parameterSet.getProjectType();
			fptAppHead.setProjectType(projectType);

			// 申请表编号
			String appNo = mapAppHeadData.get("APP_NO");
			fptAppHead.setAppNo(appNo);
			// 修改标志
			fptAppHead.setModifyMarkState(DeclareState.PROCESS_EXE);
			// 申请表类型
			String appClass = mapAppHeadData.get("APP_CLASS");
			fptAppHead.setAppClass(appClass);
			// 转出企业内部编号
			String copAppNo = mapAppHeadData.get("OUT_COP_APP_NO");
			fptAppHead.setOutCopAppNo(copAppNo);
			// 转出地海关
			String mastCust = mapAppHeadData.get("OUT_MAST_CUST");
			if (mastCust != null && !"".equals(mastCust.trim())) {
				fptAppHead.setOutCustoms(this.fptManageDao
						.findCustomsByCode(mastCust));
			}
			// 转出手册/账册编号
			String emsNo = mapAppHeadData.get("OUT_EMS_NO");
			fptAppHead.setEmsNo(emsNo);
			// 转出企业代码
			fptAppHead.setOutTradeCode(outTradeCode);
			// 转出企业名称
			fptAppHead.setOutTradeName(outTradeName);
			// 申报企业9位组织机构代码
			String agentCode = mapAppHeadData.get("OUT_AGENT_CODE");
			fptAppHead.setOutAgentCode(agentCode);
			// 申报企业组织机构名称
			String agentName = mapAppHeadData.get("OUT_AGENT_NAME");
			fptAppHead.setOutAgentName(agentName);
			// 转出企业法人/联系电话
			String corp = mapAppHeadData.get("OUT_CORP");
			fptAppHead.setOutCorp(corp);
			// 转出申报人/联系电话
			String decl = mapAppHeadData.get("OUT_DECL");
			fptAppHead.setOutDecl(decl);
			// 转出方机构代码 2013.10.11 HH
			String outAgentCode = mapAppHeadData.get("OUT_OWNER_CODE");
			fptAppHead.setOutAgentCode(outAgentCode);
			// 转出方机构名称 2013.10.11 HH
			String outAgentName = mapAppHeadData.get("OUT_OWNER_NAME");
			fptAppHead.setOutAgentName(outAgentName);
			// 转出地海关
			String distCode = mapAppHeadData.get("OUT_DIST_CODE");
			if (mastCust != null && !"".equals(mastCust.trim())) {
				fptAppHead.setOutDistrict(this.fptManageDao
						.findDistrictByCode(distCode));
			}
			// 企业合同号
			String contrNo = mapAppHeadData.get("CONTR_NO");
			fptAppHead.setContrNo(contrNo);
			// 转出企业批准证编号
			String liceNo = mapAppHeadData.get("OUT_LICE_NO");
			fptAppHead.setOutLiceNo(liceNo);
			// 转出申报日期
			String dDate = mapAppHeadData.get("OUT_D_DATE");
			if (dDate != null && !"".equals(dDate.trim())) {
				fptAppHead.setOutDate(CommonUtils.yyyyMMddHHmmSSToDate(dDate));
			}
			// 转出企业备注
			String note = mapAppHeadData.get("OUT_NOTE");
			fptAppHead.setOutNote(note);
			// 预计运输耗时（天）
			String conveyDay = mapAppHeadData.get("CONVEY_DAY");
			if (conveyDay != null && !"".equals(conveyDay.trim())) {
				fptAppHead.setConveyDay(Double.valueOf(conveyDay.trim())
						.intValue());
			}
			// 送货距离（公里）
			String conveySpa = mapAppHeadData.get("CONVEY_SPA");
			if (conveySpa != null && !"".equals(conveySpa.trim())) {
				fptAppHead.setConveySpa(Double.valueOf(conveySpa.trim())
						.intValue());
			}
			// 申报类型
			String declareType = mapAppHeadData.get("DECLARE_TYPE");
			if (declareType != null && !"".equals(declareType.trim())) {
				fptAppHead.setDelcareType(declareType);
			}
			// 转入企业代码
			String inTradeCode = mapAppHeadData.get("IN_TRADE_CODE");
			fptAppHead.setInTradeCode(inTradeCode);
			// 转入企业名称
			String inTradeName = mapAppHeadData.get("IN_TRADE_NAME");
			fptAppHead.setInTradeName(inTradeName);
			// 转入地
			String inDistrict = mapAppHeadData.get("IN_DIST_CODE");
			if (inDistrict != null && !"".equals(inDistrict.trim())) {
				fptAppHead.setInDistrict(this.fptManageDao
						.findDistrictByCode(inDistrict));
			}
			Company com = (Company) CommonUtils.getCompany();
			// 转入地海关
			fptAppHead.setInCustoms(com.getMasterCustoms());
			// 转入地组织机构代码
			fptAppHead.setInAgentCode(com.getInAgentCode());
			// 组织机构名称
			fptAppHead.setInAgentName(com.getInAgentName());

			// 2013.10.10 HH
			// 转入企业批准证编号
			fptAppHead.setInLiceNo("人工审批");
			// 转入企业申报代码
			fptAppHead.setInTradeCode2(com.getInTradeCode2());
			// 转入申报企业名称
			fptAppHead.setInTradeName2(com.getInTradeName2());
			// 转入企业法人电话
			fptAppHead.setInCorp(com.getInCorp());
			// 转入企业申报人电话
			fptAppHead.setInDecl(com.getInDecl());

			if (DeclareState.CHANGING_EXE.equals(fptAppHead.getDeclareState())) {
				fptAppHead.setModifyMarkState(ModifyMarkState.MODIFIED);
			}
			// 保存关封
			this.fptManageDao.saveFptAppHead(fptAppHead);

			List<Map<String, String>> listItem = (List<Map<String, String>>) mapDownData
					.get("FPT_APP_LIST");
			List listFptAppItem = this.fptManageDao.findFptAppItems(
					fptAppHead.getId(), FptInOutFlag.OUT);
			Map<String, FptAppItem> mapFptAppItem = new HashMap<String, FptAppItem>();
			for (int i = 0; i < listFptAppItem.size(); i++) {
				FptAppItem fptAppItem = (FptAppItem) listFptAppItem.get(i);
				mapFptAppItem
						.put(fptAppItem.getListNo().toString(), fptAppItem);
			}
			if (listItem != null && !listItem.isEmpty()) {
				for (Map<String, String> mapItem : listItem) {
					// 序号
					String listNo = mapItem.get("OUT_NO");// LIST_NO
					FptAppItem fptAppItem = mapFptAppItem.get(listNo.trim());
					if (fptAppItem == null) {
						fptAppItem = new FptAppItem();
						fptAppItem.setFptAppHead(fptAppHead);
						fptAppItem.setModifyMarkState(ModifyMarkState.UNCHANGE);
						if (listNo != null && !"".equals(listNo.trim())) {
							fptAppItem.setListNo(Double.valueOf(listNo)
									.intValue());
						} else {
							fptAppItem.setListNo(this.fptManageDao
									.findMaxValueByFptAppItem(
											fptAppHead.getId(),
											FptInOutFlag.OUT) + 1);
						}
						fptAppItem.setInOutFlag(FptInOutFlag.OUT);
					} else {
						fptAppItem.setModifyMarkState(ModifyMarkState.UNCHANGE);
						fptAppItem.setInOutFlag(FptInOutFlag.OUT);
					}
					// 项号
					String trNo = mapItem.get("OUT_TR_NO");
					fptAppItem.setTrNo(Double.valueOf(trNo).intValue());
					// 商品编码
					String codeTS = mapItem.get("OUT_CODE_T_S");
					fptAppItem.setCodeTs(this.fptManageDao
							.findComplexByCode(codeTS));
					// 商品名称
					String gName = mapItem.get("OUT_G_NAME");
					fptAppItem.setName(gName);
					// 规格型号
					String gModel = mapItem.get("OUT_G_MODEL");
					fptAppItem.setSpec(gModel);
					// 计量单位
					String unit = mapItem.get("OUT_UNIT");
					fptAppItem.setUnit(this.fptManageDao.findUnitByCode(unit));
					// 法定单位
					String unit1 = mapItem.get("OUT_UNIT_1");
					if (unit1 != null && !"".equals(unit1.trim())) {
						fptAppItem.setUnit1(this.fptManageDao
								.findUnitByCode(unit1));
					}
					// 申报数量
					String qty = mapItem.get("OUT_QTY");
					if (qty != null && !"".equals(qty.trim())) {
						fptAppItem.setQty(Double.valueOf(qty.trim()));
					}
					// 法定数量
					String qty1 = mapItem.get("OUT_QTY_1");
					if (qty1 != null && !"".equals(qty1.trim())) {
						fptAppItem.setQty1(Double.valueOf(qty1.trim()));
					}
					// 备注
					String itemNote = mapItem.get("OUT_NOTE");
					fptAppItem.setNote(itemNote);
					this.fptManageDao.saveFptAppItem(fptAppItem);
				}
			}
		}
	}

	/**
	 * 根据下载单据回写收货单
	 * 
	 * @param mapDownData
	 */
	private void writeBill(Map mapDownData) {
		// 收发货单
		Map<String, String> mapBillHeadData = (Map<String, String>) mapDownData
				.get("FPT_BILL_HEAD");
		if (mapBillHeadData != null && !mapBillHeadData.isEmpty()) {
			// 收发货单编号
			String billNo = mapBillHeadData.get("CON_BILL_NO");
			FptBillHead fptBillHead = this.fptManageDao
					.findfptBillHeadByBillNo(billNo, FptInOutFlag.IN);
			if (fptBillHead == null) {
				fptBillHead = new FptBillHead();
				String seqNo = mapBillHeadData.get("SEQ_NO");

				fptBillHead.setAppState(DeclareState.APPLY_POR);
				fptBillHead.setBillNo(billNo);
				fptBillHead.setSeqNo(seqNo);
				FptParameterSet parameterSet = this.fptManageDao
						.findFptParameterSet();
				fptBillHead.setProjectType(parameterSet.getProjectType());
				fptBillHead.setBillSort(FptInOutFlag.IN);
				fptBillHead.setSysType(FptBusinessType.FPT_BILL);
				String inCopAppNo = (this.fptMessageLogic
						.getNewCopEntNoFptBill("FptBillHead",
								"receiveCopBillNo", "F",
								FptBusinessType.FPT_BILL, FptInOutFlag.IN));
				fptBillHead.setReceiveCopBillNo(inCopAppNo);
				fptBillHead.setAppState("1");
				
				Company company=(Company)CommonUtils.getCompany();
				fptBillHead.setReceiveAgentCode(company.getCode());
                fptBillHead.setReceiveTradeName(company.getName());
                fptBillHead.setReceiveAgentCode(company.getInAgentCode());
                fptBillHead.setReceiveAgentName(company.getInAgentName());
				
			}
			// 申请表编号
			String appNo = mapBillHeadData.get("APP_NO");
			fptBillHead.setAppNo(appNo);
			// 根据料件收货回执的申请单编号，自动带出转入企业资料
			autoWriteCompanyByAppNo(fptBillHead, appNo);
			// System.out.println("-----------appNo:" + appNo);
			String appbillNo = mapBillHeadData.get("CON_BILL_NO");
			fptBillHead.setBillNo(appbillNo);
			// 发货企业内部编号
			String copBillNo = mapBillHeadData.get("OUT_COP_BILL_NO");
			fptBillHead.setIssueCopBillNo(copBillNo);
			System.out.println("-----------copBillNo:" + copBillNo);
			// 发货企业编码
			String tradeCode = mapBillHeadData.get("OUT_TRADE_CODE");
			fptBillHead.setIssueTradeCod(tradeCode);
			System.out.println("-----------tradeCode:" + tradeCode);
			// 转出账册编号
			String outEmsNo = mapBillHeadData.get("EMS_NO");
			fptBillHead.setOutEmsNo(outEmsNo);
			System.out.println("-----------outEmsNo:" + outEmsNo);
			// 发货企业名称
			String tradeName = mapBillHeadData.get("OUT_TRADE_NAME");
			fptBillHead.setIssueTradeName(tradeName);
			System.out.println("-----------tradeName:" + tradeName);
			// 发货日期
			String issueDate = mapBillHeadData.get("ISSUE_DATE");
			if (issueDate != null) {
				fptBillHead.setIssueDate(CommonUtils
						.yyyyMMddHHmmSSToDate(issueDate));
			}
			System.out.println("-----------issueDate:" + issueDate);
			// 发货申报人
			String isDeclaEm = mapBillHeadData.get("IS_DECLA_EM");
			fptBillHead.setIssueIsDeclaEm(isDeclaEm);
			// 发货申报时间
			String isDeclaDate = mapBillHeadData.get("IS_DECLA_DATE");
			if (isDeclaDate != null) {
				fptBillHead.setIssueIsDeclaDate(CommonUtils
						.yyyyMMddHHmmSSToDate(isDeclaDate));
			}
			// 发货申报企业9位组织机构代码
			String agentCode = mapBillHeadData.get("OUT_OWNER_CODE");
			fptBillHead.setIssueAgentCode(agentCode);
			// 发货申报企业组织机构名称
			String agentName = mapBillHeadData.get("OUT_OWNER_NAME");
			fptBillHead.setIssueAgentName(agentName);
			// 备注
			String note = mapBillHeadData.get("OUT_NOTE");
			fptBillHead.setReceiveNote(note);
			System.out.println("-----------save fptbillhead" + fptBillHead);
			// 保存表头
			this.fptManageDao.saveFptBillHead(fptBillHead);

			List listFptBillItem = this.fptManageDao
					.findFptBillItemCommodityInfo(fptBillHead.getId(),
							FptInOutFlag.OUT);
			Map<String, FptBillItem> mapFptBillItem = new HashMap<String, FptBillItem>();
			for (int i = 0; i < listFptBillItem.size(); i++) {
				FptBillItem fptBillItem = (FptBillItem) listFptBillItem.get(i);
				if (fptBillItem == null) {
					fptBillItem = new FptBillItem();
				}
				mapFptBillItem.put(fptBillItem.getListNo().toString(),
						fptBillItem);
			}
			List<Map<String, String>> listItem = (List<Map<String, String>>) mapDownData
					.get("FPT_BILL_LIST");
			if (listItem == null || listItem.isEmpty()) {
				listItem = (List<Map<String, String>>) mapDownData
						.get("FPT_BILL_DICT_LIST");
			}
			if (listItem != null && !listItem.isEmpty()) {
				for (Map<String, String> mapItem : listItem) {
					// 序号
					String listNo = mapItem.get("OUT_NO");
					System.out.println("-----------listNo:" + listNo);
					FptBillItem fptBillItem = mapFptBillItem.get(listNo.trim());
					if (fptBillItem == null) {
						fptBillItem = new FptBillItem();
						fptBillItem.setFptBillHead(fptBillHead);
						fptBillItem.setBillSort(FptInOutFlag.OUT);
						fptBillItem.setListNo(Double.valueOf(listNo.trim())
								.intValue());
					}
					// // 发货序号
					// String outNo = mapItem.get("APP_G_NO");
					// if (outNo != null && !"".equals(outNo.trim())) {
					// fptBillItem.setOutNo(Double.valueOf(outNo.trim())
					// .intValue());
					// System.out.println("-----------outNo:" + outNo);
					// }
					// 转入手册/账册号
					// String inEmsNo = mapItem.get("IN_EMS_NO");
					// fptBillItem.setInEmsNo(inEmsNo);
					// 料号
					String copGNo = mapItem.get("COP_G_NO");
					fptBillItem.setCopGNo(copGNo);
					// 归并前商品名称
					String copGName = mapItem.get("COP_G_NAME");
					fptBillItem.setCopGName(copGName);
					// 归并前商品规格
					String copGModel = mapItem.get("COP_G_MODEL");
					fptBillItem.setCopGModel(copGModel);
					// 申表请序号
					String appGNo = mapItem.get("APP_G_NO");
					if (appGNo != null && !"".equals(appGNo.trim())) {
						fptBillItem.setAppGNo(Double.valueOf(appGNo.trim())
								.intValue());
					}
					System.out.println("-----------appGNo:" + appGNo);
					// 项号
					String gNo = mapItem.get("OUT_G_NO");
					if (gNo != null && !"".equals(gNo.trim())) {
						fptBillItem.setTrGno(Double.valueOf(gNo.trim())
								.intValue());
					}
					// 商品编码
					String codeTS = mapItem.get("OUT_CODE_T_S");
					Complex complex = this.fptManageDao
							.findComplexByCode(codeTS);
					fptBillItem.setComplex(complex);
					// 商品名称
					String gName = mapItem.get("OUT_G_NAME");
					fptBillItem.setCommName(gName);
					// 商品规格
					String gModel = mapItem.get("OUT_G_MODEL");
					fptBillItem.setCommSpec(gModel);
					// 交易单位
					String tradeUnitCode = mapItem.get("TRADE_UNIT");
					Unit tradeUnit = this.fptManageDao
							.findUnitByCode(tradeUnitCode);
					fptBillItem.setTradeUnit(tradeUnit);
					// 交易数量
					String tradeQty = mapItem.get("TRADE_QTY");
					if (tradeQty != null && !"".equals(tradeQty.trim())) {
						fptBillItem
								.setTradeQty(Double.valueOf(tradeQty.trim()));
					}
					// 计量单位
					String unitCode = mapItem.get("UNIT");
					Unit unit = this.fptManageDao.findUnitByCode(unitCode);
					fptBillItem.setUnit(unit);
					// 申报数量
					String qty = mapItem.get("QTY");
					if (qty != null && !"".equals(qty.trim())) {
						fptBillItem.setQty(Double.valueOf(qty.trim()));
					}
					// 备注
					String itemNote = mapItem.get("OUT_NOTE");
					fptBillItem.setNote(itemNote);
					this.fptManageDao.saveOrUpdate(fptBillItem);
				}
			}
			// List<Map<String, String>> listDictItem = (List<Map<String,
			// String>>) mapDownData
			// .get("FPT_BILL_DICT_LIST");
			// if (listItem != null && !listItem.isEmpty()) {
			// for (Map<String, String> mapItem : listDictItem) {
			// FptBillItem fptBillItem = new FptBillItem();
			// fptBillItem.setFptBillHead(fptBillHead);
			// fptBillItem.setBillSort(FptInOutFlag.IN);
			// // 序号
			// String listNo = mapItem.get("LIST_NO");
			// fptBillItem.setListNo(Integer.valueOf(listNo.trim()));
			// // 发货序号
			// String outNo = mapItem.get("OUT_NO");
			// // if(outNo!=null&&!"".equals(outNo.trim())){
			// // fptBillItem.setOutNo(Integer.valueOf(outNo.trim()));
			// // }
			// // 转入手册/账册号
			// String inEmsNo = mapItem.get("IN_EMS_NO");
			// // fptBillItem.setInEmsNo(inEmsNo);
			// // 料号
			// String copGNo = mapItem.get("COP_G_NO");
			// fptBillItem.setCopGNo(copGNo);
			// // 归并前商品名称
			// String copGName = mapItem.get("COP_G_NAME");
			// fptBillItem.setCopGName(copGName);
			// // 归并前商品规格
			// String copGModel = mapItem.get("COP_G_MODEL");
			// fptBillItem.setCopGModel(copGModel);
			// // 申表请序号
			// String appGNo = mapItem.get("APP_G_NO");
			// if (appGNo != null && !"".equals(appGNo.trim())) {
			// fptBillItem.setAppGNo(Integer
			// .valueOf(appGNo.trim()));
			// }
			// // 项号
			// String gNo = mapItem.get("G_NO");
			// if (gNo != null && !"".equals(gNo.trim())) {
			// fptBillItem.setTrGno(Integer.valueOf(gNo.trim()));
			// }
			// // 商品编码
			// String codeTS = mapItem.get("CODE_T_S");
			// Complex complex = this.transferFactoryManageDao
			// .findComplexByCode(codeTS);
			// fptBillItem.setComplex(complex);
			// // 商品名称
			// String gName = mapItem.get("G_NAME");
			// fptBillItem.setCommName(gName);
			// // 商品规格
			// String gModel = mapItem.get("G_MODEL");
			// fptBillItem.setCommSpec(gModel);
			// // 交易单位
			// String tradeUnitCode = mapItem.get("TRADE_UNIT");
			// Unit tradeUnit = this.transferFactoryManageDao
			// .findUnitByCode(tradeUnitCode);
			// fptBillItem.setTradeUnit(tradeUnit);
			// // 交易数量
			// String tradeQty = mapItem.get("TRADE_QTY");
			// if (tradeQty != null && !"".equals(tradeQty.trim())) {
			// fptBillItem.setTradeQty(Double.valueOf(tradeQty
			// .trim()));
			// }
			// // 计量单位
			// String unitCode = mapItem.get("UNIT");
			// Unit unit = this.transferFactoryManageDao
			// .findUnitByCode(unitCode);
			// fptBillItem.setUnit(unit);
			// // 申报数量
			// String qty = mapItem.get("QTY");
			// if (qty != null && !"".equals(qty.trim())) {
			// fptBillItem.setQty(Double.valueOf(qty.trim()));
			// }
			// // 备注
			// String itemNote = mapItem.get("NOTE");
			// fptBillItem.setNote(itemNote);
			// this.transferFactoryManageDao.saveOrUpdate(fptBillItem);
			// }
			// }
		}
	}

	/**
	 * 根据料件收货回执的申请单编号，自动带出转入企业资料
	 */
	private void autoWriteCompanyByAppNo(FptBillHead fptBillHead, String appNo) {
		List list = this.fptManageDao.findFptAppHeadAppNo(FptInOutFlag.IN,
				appNo);
		if (!list.isEmpty()) {
			FptAppHead fptAppHead = (FptAppHead) list.get(0);
			fptBillHead.setCustomer(fptAppHead.getScmCoc());
			fptBillHead.setReceiveTradeCod(fptAppHead.getInTradeCode());
			fptBillHead.setReceiveTradeName(fptAppHead.getInTradeName());
			fptBillHead.setReceiveAgentCode(fptAppHead.getInAgentCode());
			fptBillHead.setReceiveAgentName(fptAppHead.getInAgentName());
			fptBillHead.setIssueTradeCod(fptAppHead.getOutTradeCode());
			fptBillHead.setIssueTradeName(fptAppHead.getOutTradeName());
		} else {
			throw new RuntimeException("系统中不存在申请表编号为：" + appNo + "的料件申请表。");
		}
	}

	/** 获得申请表标号下的所有申请单 */
	public FptAppHead findAllFptAppHeadByAppNo(String appNo) {
		return (FptAppHead) this.fptManageDao.findAllFptAppHeadByAppNo(appNo);
	}

	/**
	 * 根据下载单据回写收退货单
	 * 
	 * @param mapDownData
	 */
	private void writeBillBack(Map mapDownData) {
		Map<String, String> mapBillHeadData = (Map<String, String>) mapDownData
				.get("FPT_BILL_HEAD");
		if (mapBillHeadData != null && !mapBillHeadData.isEmpty()) {
			// 收发货单编号
			String billNo = mapBillHeadData.get("BACK_BILL_NO");
			FptBillHead fptBillHead = this.fptManageDao
					.findfptBillHeadByBillNo(billNo, FptInOutFlag.OUT);
			if (fptBillHead == null) {
				// throw new RuntimeException("没有找到单号为:" + billNo + "的收退货单据");
				fptBillHead = new FptBillHead();
				String seqNo = mapBillHeadData.get("SEQ_NO");

				fptBillHead.setAppState(DeclareState.APPLY_POR);
				fptBillHead.setBillNo(billNo);
				fptBillHead.setSeqNo(seqNo);
				FptParameterSet parameterSet = this.fptManageDao
						.findFptParameterSet();
				fptBillHead.setProjectType(parameterSet.getProjectType());
				fptBillHead.setBillSort(FptInOutFlag.OUT);
				fptBillHead.setSysType(FptBusinessType.FPT_BILL_BACK);
				
				Company company=(Company)CommonUtils.getCompany();
				fptBillHead.setIssueAgentCode(company.getCode());
                fptBillHead.setIssueTradeName(company.getName());
                fptBillHead.setIssueAgentCode(company.getInAgentCode());
                fptBillHead.setIssueAgentName(company.getInAgentName());
                //柯鹏程
                String receiveCopBillNo = this.fptMessageLogic.getNewCopEntNoFptBill("FptBillHead", "issueCopBillNo",
								"F", FptBusinessType.FPT_BILL_BACK,FptInOutFlag.OUT);
                fptBillHead.setIssueCopBillNo(receiveCopBillNo);
				
			}
			// 申请表编号
			// String appNo = mapBillHeadData.get("BILL_NO");
			// fptBillHead.setAppNo(appNo);
			// 发退货企业内部编号
			String copBillNo = mapBillHeadData.get("IN_COP_BILL_NO");
			fptBillHead.setReceiveCopBillNo(copBillNo);
			// 发退货企业编码
			String tradeCode = mapBillHeadData.get("IN_TRADE_CODE");
			fptBillHead.setReceiveTradeCod(tradeCode);
			// 发退货企业名称
			String tradeName = mapBillHeadData.get("IN_TRADE_NAME");
			fptBillHead.setReceiveTradeName(tradeName);
			// 发退货日期
			String issueDate = mapBillHeadData.get("ISSUE_DATE");
			if (issueDate != null) {
				fptBillHead.setReceiveDate(CommonUtils
						.yyyyMMddHHmmSSToDate(issueDate));
			}
			// 发退货申报人
			String isDeclaEm = mapBillHeadData.get("IS_DECLA_EM");
			fptBillHead.setReceiveIsDeclaEm(isDeclaEm);
			// 发退货申报时间
			String isDeclaDate = mapBillHeadData.get("IS_DECLA_DATE");
			if (isDeclaDate != null) {
				fptBillHead.setReceiveIsDeclaDate(CommonUtils
						.yyyyMMddHHmmSSToDate(isDeclaDate));
			}
			// 发退货申报企业9位组织机构代码
			String agentCode = mapBillHeadData.get("AGENT_CODE");
			fptBillHead.setReceiveAgentCode(agentCode);
			// 发退货申报企业组织机构名称
			String agentName = mapBillHeadData.get("AGENT_NAME");
			fptBillHead.setReceiveAgentName(agentName);
			// 备注
			String note = mapBillHeadData.get("IN_NOTE");
			fptBillHead.setReceiveNote(note);
			this.fptManageDao.saveFptBillHead(fptBillHead);
			List listFptBillItem = this.fptManageDao
					.findFptBillItemCommodityInfo(fptBillHead.getId(),
							FptInOutFlag.IN);
			Map<String, FptBillItem> mapFptBillItem = new HashMap<String, FptBillItem>();
			for (int i = 0; i < listFptBillItem.size(); i++) {
				FptBillItem fptBillItem = (FptBillItem) listFptBillItem.get(i);
				mapFptBillItem.put(fptBillItem.getListNo().toString(),
						fptBillItem);
			}
			List<Map<String, String>> listItem = (List<Map<String, String>>) mapDownData
					.get("FPT_BILL_LIST");
			if (listItem == null || listItem.isEmpty()) {
				listItem = (List<Map<String, String>>) mapDownData
						.get("FPT_BILL_DICT_LIST");
			}
			if (listItem != null && !listItem.isEmpty()) {
				for (Map<String, String> mapItem : listItem) {
					// 序号
					String listNo = mapItem.get("IN_NO");//LIST_NO
					System.out.println("-----------IN_NO:" + listNo);
					FptBillItem fptBillItem = mapFptBillItem.get(listNo.trim());
					if (fptBillItem == null) {
						fptBillItem = new FptBillItem();
						fptBillItem.setFptBillHead(fptBillHead);
						fptBillItem.setBillSort(FptInOutFlag.IN);
						fptBillItem.setListNo(Double.valueOf(listNo.trim())
								.intValue());
					}
					// 发货序号
					// String outNo = mapItem.get("OUT_NO");
					// if(outNo!=null&&!"".equals(outNo.trim())){
					// fptBillItem.setOutNo(Integer.valueOf(outNo.trim()));
					// }
					// 转入手册/账册号
					String inEmsNo = mapItem.get("IN_EMS_NO");
					fptBillItem.setInEmsNo(inEmsNo);
					// 料号
					String copGNo = mapItem.get("COP_G_NO");
					fptBillItem.setCopGNo(copGNo);
					// 归并前商品名称
					String copGName = mapItem.get("COP_G_NAME");
					fptBillItem.setCopGName(copGName);
					// 归并前商品规格
					String copGModel = mapItem.get("COP_G_MODEL");
					fptBillItem.setCopGModel(copGModel);
					// 申表请序号
					String appGNo = mapItem.get("APP_G_NO");
					if (appGNo != null && !"".equals(appGNo.trim())) {
						fptBillItem.setAppGNo(Double.valueOf(appGNo.trim())
								.intValue());
					}
					// 项号
					String gNo = mapItem.get("IN_G_NO");
					if (gNo != null && !"".equals(gNo.trim())) {
						fptBillItem.setTrGno(Double.valueOf(gNo.trim())
								.intValue());
					}
					// 商品编码
					String codeTS = mapItem.get("IN_CODE_T_S");
					Complex complex = this.fptManageDao
							.findComplexByCode(codeTS);
					fptBillItem.setComplex(complex);
					// 商品名称
					String gName = mapItem.get("IN_G_NAME");
					fptBillItem.setCommName(gName);
					// 商品规格
					String gModel = mapItem.get("IN_G_MODEL");
					fptBillItem.setCommSpec(gModel);
					// 交易单位
					String tradeUnitCode = mapItem.get("TRADE_UNIT");
					Unit tradeUnit = this.fptManageDao
							.findUnitByCode(tradeUnitCode);
					fptBillItem.setTradeUnit(tradeUnit);
					// 交易数量
					String tradeQty = mapItem.get("TRADE_QTY");
					if (tradeQty != null && !"".equals(tradeQty.trim())) {
						fptBillItem
								.setTradeQty(Double.valueOf(tradeQty.trim()));
					}
					// 计量单位
					String unitCode = mapItem.get("UNIT");
					Unit unit = this.fptManageDao.findUnitByCode(unitCode);
					fptBillItem.setUnit(unit);
					// 申报数量
					String qty = mapItem.get("QTY");
					if (qty != null && !"".equals(qty.trim())) {
						fptBillItem.setQty(Double.valueOf(qty.trim()));
					}
					// 备注
					String itemNote = mapItem.get("NOTE");
					fptBillItem.setNote(itemNote);
					this.fptManageDao.saveOrUpdate(fptBillItem);
				}
			}
			// List<Map<String, String>> listDictItem = (List<Map<String,
			// String>>) mapDownData
			// .get("FPT_BILL_DICT_LIST");
			// if (listItem != null && !listItem.isEmpty()) {
			// for (Map<String, String> mapItem : listDictItem) {
			// FptBillItem fptBillItem = new FptBillItem();
			// fptBillItem.setFptBillHead(fptBillHead);
			// fptBillItem.setBillSort(FptInOutFlag.IN);
			// // 序号
			// String listNo = mapItem.get("LIST_NO");
			// fptBillItem.setListNo(Integer.valueOf(listNo.trim()));
			// // 发货序号
			// String outNo = mapItem.get("OUT_NO");
			// // if(outNo!=null&&!"".equals(outNo.trim())){
			// // fptBillItem.setOutNo(Integer.valueOf(outNo.trim()));
			// // }
			// // 转入手册/账册号
			// String inEmsNo = mapItem.get("IN_EMS_NO");
			// // fptBillItem.setInEmsNo(inEmsNo);
			// // 料号
			// String copGNo = mapItem.get("COP_G_NO");
			// fptBillItem.setCopGNo(copGNo);
			// // 归并前商品名称
			// String copGName = mapItem.get("COP_G_NAME");
			// fptBillItem.setCopGName(copGName);
			// // 归并前商品规格
			// String copGModel = mapItem.get("COP_G_MODEL");
			// fptBillItem.setCopGModel(copGModel);
			// // 申表请序号
			// String appGNo = mapItem.get("APP_G_NO");
			// if (appGNo != null && !"".equals(appGNo.trim())) {
			// fptBillItem.setAppGNo(Integer
			// .valueOf(appGNo.trim()));
			// }
			// // 项号
			// String gNo = mapItem.get("G_NO");
			// if (gNo != null && !"".equals(gNo.trim())) {
			// fptBillItem.setTrGno(Integer.valueOf(gNo.trim()));
			// }
			// // 商品编码
			// String codeTS = mapItem.get("CODE_T_S");
			// Complex complex = this.transferFactoryManageDao
			// .findComplexByCode(codeTS);
			// fptBillItem.setComplex(complex);
			// // 商品名称
			// String gName = mapItem.get("G_NAME");
			// fptBillItem.setCommName(gName);
			// // 商品规格
			// String gModel = mapItem.get("G_MODEL");
			// fptBillItem.setCommSpec(gModel);
			// // 交易单位
			// String tradeUnitCode = mapItem.get("TRADE_UNIT");
			// Unit tradeUnit = this.transferFactoryManageDao
			// .findUnitByCode(tradeUnitCode);
			// fptBillItem.setTradeUnit(tradeUnit);
			// // 交易数量
			// String tradeQty = mapItem.get("TRADE_QTY");
			// if (tradeQty != null && !"".equals(tradeQty.trim())) {
			// fptBillItem.setTradeQty(Double.valueOf(tradeQty
			// .trim()));
			// }
			// // 计量单位
			// String unitCode = mapItem.get("UNIT");
			// Unit unit = this.transferFactoryManageDao
			// .findUnitByCode(unitCode);
			// fptBillItem.setUnit(unit);
			// // 申报数量
			// String qty = mapItem.get("QTY");
			// if (qty != null && !"".equals(qty.trim())) {
			// fptBillItem.setQty(Double.valueOf(qty.trim()));
			// }
			// // 备注
			// String itemNote = mapItem.get("NOTE");
			// fptBillItem.setNote(itemNote);
			// this.transferFactoryManageDao.saveOrUpdate(fptBillItem);
			// }
			// }
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	//
	// begin 申请表余量分析报表(总表)
	//
	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 获得申请单余量分析报表
	 */
	public List<TempFptAppSpareAnalyes> findTempFptAppSpareAnalyes(
			TempFptAppParam param) {
		List<TempFptAppSpareAnalyes> resultList = new ArrayList<TempFptAppSpareAnalyes>();
		if (param == null) {
			return resultList;
		}
		int projectType = param.getProjectType().intValue();

		//
		// select b.outTradeName,
		// a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)
		// 以 FptAppItem 表为基标的汇总统计
		//
		List list = this.fptManageDao.findFptAppItemsByParam(param);
		for (int i = 0; i < list.size(); i++) {
			TempFptAppSpareAnalyes t = new TempFptAppSpareAnalyes();
			t.setInOutFlag(param.getInOutFlag());
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String tradeName = objs[0] == null ? "" : (String) objs[0];
			String complex = objs[1] == null ? "" : (String) objs[1];
			String name = objs[2] == null ? "" : (String) objs[2];
			String spec = objs[3] == null ? "" : (String) objs[3];
			String unitName = objs[4] == null ? "" : (String) objs[4];
			Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
			t.setComplex(complex);
			t.setName(name);
			t.setTradeName(tradeName);
			t.setSpec(spec);
			t.setUnitName(unitName);
			// 3.申请表数量
			t.setF3(qty);
			//
			// 加入
			//
			resultList.add(t);
		}
		//
		// 1.合同定量(DZSC or BCS )
		//
		Map<String, Double> f1Map = this.getF1(param);
		//
		// 2.报关用量
		//
		Map<String, Double> f2Map = this.getF2(param);
		//
		// 4.累计申请表数量 获得 f4 map
		//
		Map<String, Double> f4Map = this.getF4(param);
		//
		// 7.已发送数量 （收发货)
		//
		Map<String, Double> f7Map = this.getF7(param, FptBusinessType.FPT_BILL);
		//
		// 8.未发送数量（收发货)
		//
		Map<String, Double> f8Map = this.getF8(param, FptBusinessType.FPT_BILL);

		//
		// 7.已发送数量
		//
		Map<String, Double> f7BackMap = this.getF7(param,
				FptBusinessType.FPT_BILL_BACK);
		//
		// 8.未发送数量1
		//
		Map<String, Double> f8BackMap = this.getF8(param,
				FptBusinessType.FPT_BILL_BACK);

		//
		// 10.已转厂数量(按客户分的)
		//
		Map<String, Double> f10Map = this.getF10(param);
		//
		// 填充其它栏位
		//
		for (TempFptAppSpareAnalyes t : resultList) {
			//
			// 商品编码+名称+规格+单位
			//
			String key = t.getComplex() + "/" + t.getName() + "/" + t.getSpec()
					+ "/" + t.getUnitName();
			String keyBreifName = key + "/" + t.getTradeName();
			//
			// 1.合同定量(DZSC or BCS )
			//
			Double f1 = f1Map.get(key) == null ? 0.0 : f1Map.get(key);
			t.setF1(f1);
			//
			// 2.报关用量
			//
			Double commonAmount = f2Map.get(key) == null ? 0.0 : f2Map.get(key);
			if (ProjectType.BCUS == projectType) {
				t.setF2(commonAmount);
			} else {
				t.setF2(f1 - commonAmount);
			}
			//
			// 4.累计申请表数量
			//
			t.setF4(f4Map.get(key) == null ? 0.0 : f4Map.get(key));
			//
			// 7.已发送数量
			//
			t.setF7(f7Map.get(keyBreifName) == null ? 0.0 : f7Map
					.get(keyBreifName));
			t.setF7(t.getF7()
					- (f7BackMap.get(keyBreifName) == null ? 0.0 : f7BackMap
							.get(keyBreifName)));
			//
			// 8.未发送数量
			//
			t.setF8(f8Map.get(keyBreifName) == null ? 0.0 : f8Map
					.get(keyBreifName));
			t.setF8(t.getF8()
					- (f8BackMap.get(keyBreifName) == null ? 0.0 : f8BackMap
							.get(keyBreifName)));
			//
			// 10.已转厂数量(按客户分的)
			//
			t.setF10(f10Map.get(keyBreifName) == null ? 0.0 : f10Map
					.get(keyBreifName));
		}
		return resultList;
	}

	/**
	 * 4.累计申请表数量
	 * 
	 * @return key = 商品编码+名称+规格+单位
	 */
	private Map<String, Double> getF4(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// select a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)
		//
		List list = this.fptManageDao.findFptAppItemsByParam2(param);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			Double qty = objs[4] == null ? 0.0 : (Double) objs[4];
			//
			// 商品编码+名称+规格+单位
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName;
			map.put(key, qty);
		}
		return map;
	}




	
	/**
	 * 1.合同定量(DZSC or BCS )
	 * 
	 * @return key = 商品编码+名称+规格+单位
	 */
	private Map<String, Double> getF1(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		int projectType = param.getProjectType().intValue();
		//
		// select a.complex.code,a.name,a.spec,a.unit.name,SUM(a.qty)
		//
		if (ProjectType.BCS == projectType) {
			list = this.fptManageDao.findContractAmoutByBcs(param);
		} else if (ProjectType.DZSC == projectType) {
			list = this.fptManageDao.findContractAmoutByDzsc(param);
		}
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			Double qty = objs[4] == null ? 0.0 : (Double) objs[4];
			//
			// 商品编码+名称+规格+单位
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,sum(a.commAmount)
	 */
	private Map<String, Double> getF2(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// a.complex.code,a.commName,a.commSpec,a.unit.Name,sum(a.commAmount)
		//
		list = this.fptManageDao.findCommInfoTotalAmount(param);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			Double qty = objs[4] == null ? 0.0 : (Double) objs[4];
			//
			// 商品编码+名称+规格+单位
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec
	 * ,a.unit.name,SUM(a.qty)
	 */
	private Map<String, Double> getF7(TempFptAppParam param,
			String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,
		// SUM(a.qty)
		//
		list = this.fptManageDao
				.findFptBillItemsByParam(param, fptBusinessType);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String tradeName = objs[0] == null ? "" : (String) objs[0];
			String complex = objs[1] == null ? "" : (String) objs[1];
			String name = objs[2] == null ? "" : (String) objs[2];
			String spec = objs[3] == null ? "" : (String) objs[3];
			String unitName = objs[4] == null ? "" : (String) objs[4];
			Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
			//
			// 商品编码+名称+规格+单位+客户名
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName
					+ "/" + tradeName;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec
	 * ,a.unit.name,SUM(a.qty)
	 */
	private Map<String, Double> getF8(TempFptAppParam param,
			String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,
		// SUM(a.qty)
		//
		list = this.fptManageDao.findFptBillItemsByParam2(param,
				fptBusinessType);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String tradeName = objs[0] == null ? "" : (String) objs[0];
			String complex = objs[1] == null ? "" : (String) objs[1];
			String name = objs[2] == null ? "" : (String) objs[2];
			String spec = objs[3] == null ? "" : (String) objs[3];
			String unitName = objs[4] == null ? "" : (String) objs[4];
			Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
			//
			// 商品编码+名称+规格+单位+客户名
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName
					+ "/" + tradeName;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得转厂已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,brief.name
	 * ,sum(a.commAmount)
	 */
	private Map<String, Double> getF10(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// a.complex.code,a.commName,a.commSpec,a.unit.Name,brief.name,sum(a.commAmount)
		//
		list = this.fptManageDao
				.findCommInfoTotalAmountByTransferFactory(param);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String breifName = objs[4] == null ? "" : (String) objs[4];
			Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
			//
			// 商品编码+名称+规格+单位+客户名
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName
					+ "/" + breifName;
			map.put(key, qty);
		}
		return map;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	//
	// end 申请表余量分析报表(总表)
	//
	// /////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	
	
	// /////////////////////////////////////////////////////////////////////////////////////
	// hyq
	// begin 深加工结转申请表收发货余量分析 
	// key=(申请表编号+申请表序号)
	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 转厂申请表收发货余量分析 hyq
	 */
	public List<TempFptApplySurplus> findTempFptApplySurplus(
			TempFptAppParam param) {
		List<TempFptApplySurplus> resultList = new ArrayList<TempFptApplySurplus>();
		if (param == null) {
			return resultList;
		}
		int projectType = param.getProjectType().intValue();
		
		List list = this.fptManageDao.findFptAppItemsApplyQty(param);
		for (int i = 0; i < list.size(); i++) {
			TempFptApplySurplus t = new TempFptApplySurplus();
			t.setInOutFlag(param.getInOutFlag());
			//
			// 申请表定量
			//
			
			Object[] objs = (Object[]) list.get(i);
			String emsNo = objs[0] == null ? "" : (String) objs[0];
			t.setEmsNo(emsNo);
			
			String appNo = objs[1] == null ? "" : (String) objs[1];
			t.setAppNo(appNo);
			
			Boolean isCollated = objs[2] == null ? false : (Boolean)objs[2];
			t.setIsCollated(isCollated);
			
			Date endDate = objs[3] == null ? null : (Date) objs[3];
			t.setEndDate(endDate);
			
			String tradeName = objs[4] == null ? "" : (String) objs[4];
			t.setTradeName(tradeName);
			
			int listNo = objs[5] == null ? 0 : (Integer)objs[5];
			t.setListNo(listNo);
			
			int trNo = objs[6] == null ? 0 : (Integer)objs[6];
			t.setTrNo(trNo);
			
			String complex = objs[7]==null ? "" : (String) objs[7];
			t.setComplex(complex);
			
			String name = objs[8]==null ? "" : (String) objs[8];
			t.setName(name);
			
			String spec = objs[9]==null ? "" : (String) objs[9];
			t.setSpec(spec);
			
			String unitName = objs[10]==null ? "" : (String) objs[10];
			t.setUnitName(unitName);
			
			Double fptHeadAty = objs[11] == null ? 0.0 : (Double) objs[11];
			t.setFptHeadQty(fptHeadAty);
			
			resultList.add(t);
		}
		
		//统计收送货单(收送货)数量
		Map<String, Double> fptBillItemsSendReceiveQtyMap = this.getFptBillItemsSendReceiveQty(param, FptBusinessType.FPT_BILL);

		//统计收送货单(退货)数量
		Map<String, Double> fptBillItemsBackQtyMap = this.getFptBillItemsBackQtyMap(param,FptBusinessType.FPT_BILL_BACK);
		
		// 填充其它栏位
		for (TempFptApplySurplus t : resultList) {
			String key = t.getAppNo() + "@" + t.getListNo();
			t.setSendReceiveQty(fptBillItemsSendReceiveQtyMap.get(key) == null ? 0.0 : fptBillItemsSendReceiveQtyMap.get(key));
			t.setReturnQty(fptBillItemsBackQtyMap.get(key) == null ? 0.0 : fptBillItemsBackQtyMap.get(key));
			t.setRemainQty(t.getFptHeadQty() - t.getSendReceiveQty() + t.getReturnQty());
		}
		return resultList;
	}
	
	/**
	 * 转厂申请表收发货余量分析 hyq
	 */
	public List<TempFptApplySurplus> findTempFptApplySurplus0(
			TempFptAppParam param) {
		List<TempFptApplySurplus> resultList = new ArrayList<TempFptApplySurplus>();
		if (param == null) {
			return resultList;
		}
		int projectType = param.getProjectType().intValue();
		
		List list = this.fptManageDao.findFptAppItemsApplyQty0(param);
		for (int i = 0; i < list.size(); i++) {
			TempFptApplySurplus t = new TempFptApplySurplus();
			t.setInOutFlag(param.getInOutFlag());
			//
			// 申请表定量
			//
			
			Object[] objs = (Object[]) list.get(i);
			String emsNo = objs[0] == null ? "" : (String) objs[0];
			t.setEmsNo(emsNo);
			
			String appNo = objs[1] == null ? "" : (String) objs[1];
			t.setAppNo(appNo);
			
			Boolean isCollated = objs[2] == null ? false : (Boolean)objs[2];
			t.setIsCollated(isCollated);
			
			Date endDate = objs[3] == null ? null : (Date) objs[3];
			t.setEndDate(endDate);
			
			String tradeName = objs[4] == null ? "" : (String) objs[4];
			t.setTradeName(tradeName);
			
			int listNo = objs[5] == null ? 0 : (Integer)objs[5];
			t.setListNo(listNo);
			
			int trNo = objs[6] == null ? 0 : (Integer)objs[6];
			t.setTrNo(trNo);
			
			String complex = objs[7]==null ? "" : (String) objs[7];
			t.setComplex(complex);
			
			String name = objs[8]==null ? "" : (String) objs[8];
			t.setName(name);
			
			String spec = objs[9]==null ? "" : (String) objs[9];
			t.setSpec(spec);
			
			String unitName = objs[10]==null ? "" : (String) objs[10];
			t.setUnitName(unitName);
			
			Double fptHeadAty = objs[11] == null ? 0.0 : (Double) objs[11];
			t.setFptHeadQty(fptHeadAty);
			
			resultList.add(t);
		}
		
		//统计收送货单(收送货)数量
		Map<String, Double> fptBillItemsSendReceiveQtyMap = this.getFptBillItemsSendReceiveQty(param, FptBusinessType.FPT_BILL);

		//统计收送货单(退货)数量
		Map<String, Double> fptBillItemsBackQtyMap = this.getFptBillItemsBackQtyMap(param,FptBusinessType.FPT_BILL_BACK);
		
		// 填充其它栏位
		for (TempFptApplySurplus t : resultList) {
			String key = t.getAppNo() + "@" + t.getListNo();
			t.setSendReceiveQty(fptBillItemsSendReceiveQtyMap.get(key) == null ? 0.0 : fptBillItemsSendReceiveQtyMap.get(key));
			t.setReturnQty(fptBillItemsBackQtyMap.get(key) == null ? 0.0 : fptBillItemsBackQtyMap.get(key));
			t.setRemainQty(t.getFptHeadQty() - t.getSendReceiveQty() + t.getReturnQty());
		}
		return resultList;
	}

	/**
	 * 统计收送货单(收送货)数量
	 * HYQ
	 * @return key = 申请表编号+申请表序号(appNo+appGno)
	 */
	private Map<String, Double> getFptBillItemsSendReceiveQty(TempFptAppParam param,String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// select a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)
		//
		List list = this.fptManageDao.findFptBillItemsSendReceiveQty(param,fptBusinessType);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String appNo = objs[0] == null ? "" : (String) objs[0];
			Integer appGNo = objs[1] == null ? 0 : (Integer) objs[1];
			Double qty = objs[2] == null ? 0.0 : (Double) objs[2];
			//
			// 商品编码+名称+规格+单位
			//
			String key = appNo + "@" + appGNo;
			map.put(key, qty);
		}
		return map;
	}
	
	/**
	 * 统计收送货单(收退货)数量
	 * HYQ
	 * @return key = 申请表编号+申请表序号(appNo+appGno)
	 */
	private Map<String, Double> getFptBillItemsBackQtyMap(TempFptAppParam param,String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		return getFptBillItemsSendReceiveQty(param,fptBusinessType);
	}
	
	// ///////////////////////////////////////////////////////////////////////////////////
	//
	// end 深加工结转申请表收发货余量分析HYQ
	//
	// /////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	

	// /////////////////////////////////////////////////////////////////////////////////////
	// HYQ
	// begin 深加工结转手册余量分析 
	// key=(手册号+手册商品序号)(key=emsNo+trGno)
	//
	// /////////////////////////////////////////////////////////////////////////////////////
	public List<TempFptApplySurplus> findTempFptApplyDifference(
			TempFptAppParam param) {
		List<TempFptApplySurplus> resultList = new ArrayList<TempFptApplySurplus>();
		if (param == null) {
			return resultList;
		}
		int projectType = param.getProjectType().intValue();
		
		List list = this.fptManageDao.findFptAppItemsApplyQtyByEmsNoTrNo(param);
		for (int i = 0; i < list.size(); i++) {
			TempFptApplySurplus t = new TempFptApplySurplus();
			t.setInOutFlag(param.getInOutFlag());
			//
			// 申请表定量b.emsNo,a.trNo,SUM(a.qty)
			//
			
			Object[] objs = (Object[]) list.get(i);
			String emsNo = objs[0] == null ? "" : (String) objs[0];
			t.setEmsNo(emsNo);
			
			int trNo = objs[1] == null ? 0 : (Integer)objs[1];
			t.setTrNo(trNo);
			
			Double fptHeadAty = objs[2] == null ? 0.0 : (Double) objs[2];
			t.setFptHeadQty(fptHeadAty);
			
			resultList.add(t);
		}
		
		//统计收送货单(收送货)数量
		Map<String,Double> fptBillItemsSendReceiveQtyMapByEmsNoTrNo = this.getFptBillItemsSendReceiveQtyByEmsNoTrNo(param, FptBusinessType.FPT_BILL);

		//统计收送货单(退货)数量
		Map<String,Double> fptBillItemsBackQtyMapByEmsNoTrNo = this.getFptBillItemsBackQtyMapByEmsNoTrNo(param,FptBusinessType.FPT_BILL_BACK);

		//合同定量
		Map<String,TempFptApplySurplus> ontractByEmsNoSeqNum = this.getContractByEmsNoSeqNum(param);
		
		//结转报关单数量
		Map<String,Double> declarationTransferFactoryByEmsNoSeqNum = this.getDeclarationTransferFactoryByEmsNoSeqNum(param);
		
		//
		// 填充其它栏位
		//
		List<TempFptApplySurplus> resultList2 = new ArrayList<TempFptApplySurplus>();
		for (TempFptApplySurplus t : resultList) {
			String key = t.getEmsNo() + "@" + t.getTrNo();
			if(ontractByEmsNoSeqNum.get(key)!=null){
				resultList2.add(t);
			}
			
			t.setSendReceiveQty(fptBillItemsSendReceiveQtyMapByEmsNoTrNo.get(key) == null ? 0.0 : fptBillItemsSendReceiveQtyMapByEmsNoTrNo.get(key));
			t.setReturnQty(fptBillItemsBackQtyMapByEmsNoTrNo.get(key) == null ? 0.0 : fptBillItemsBackQtyMapByEmsNoTrNo.get(key));
			t.setRemainQty(t.getFptHeadQty() - t.getSendReceiveQty() + t.getReturnQty());
			
			//合同
			t.setCredenceNo(ontractByEmsNoSeqNum.get(key) == null ? 0 : ontractByEmsNoSeqNum.get(key).getCredenceNo());
			t.setContractEndDate(ontractByEmsNoSeqNum.get(key) == null ? null : ontractByEmsNoSeqNum.get(key).getContractEndDate());
			t.setComplex(ontractByEmsNoSeqNum.get(key) == null ? "" : ontractByEmsNoSeqNum.get(key).getComplex());
			t.setName(ontractByEmsNoSeqNum.get(key) == null ? "" : ontractByEmsNoSeqNum.get(key).getName());
			t.setSpec(ontractByEmsNoSeqNum.get(key) == null ? "" : ontractByEmsNoSeqNum.get(key).getSpec());
			t.setUnitName(ontractByEmsNoSeqNum.get(key) == null ? "" : ontractByEmsNoSeqNum.get(key).getUnitName());
			t.setContractQty(ontractByEmsNoSeqNum.get(key) == null ? 0.0 : ontractByEmsNoSeqNum.get(key).getContractQty());
			
			//报关单
			t.setDeclarationTransferFactoryQty(declarationTransferFactoryByEmsNoSeqNum.get(key) == null ? 0.0 : declarationTransferFactoryByEmsNoSeqNum.get(key));
			
			//差额表
			t.setFptDifferenceQty(t.getSendReceiveQty() -  t.getReturnQty() - t.getDeclarationTransferFactoryQty());

		}
		
		
		
		return resultList2;
	}

	/**
	 * HYQ
	 * 统计收送货单(收送货)数量
	 * key = 手册序号+手册流水号(emsNo+trGno)
	 */
	private Map<String, Double> getFptBillItemsSendReceiveQtyByEmsNoTrNo(TempFptAppParam param,String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// select a.codeTs.code,a.name,a.spec,a.unit.name,SUM(a.qty)
		//
		List list = this.fptManageDao.findFptBillItemsSendReceiveQtyByEmsNoTrNo(param,fptBusinessType);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String emsNo = objs[0] == null ? "" : (String) objs[0];
			Integer trGno = objs[1] == null ? 0 : (Integer) objs[1];
			Double declarationTransferFactoryQty = objs[2] == null ? 0.0 : (Double) objs[2];
			//
			// 手册号+手册序号
			//
			String key = emsNo + "@" + trGno;
			map.put(key, declarationTransferFactoryQty);
		}
		return map;
	}
	
	/**
	 * HYQ
	 * 统计收送货单(收退货)数量
	 * key = 手册序号+手册流水号(emsNo+trGno)
	 */
	private Map<String, Double> getFptBillItemsBackQtyMapByEmsNoTrNo(TempFptAppParam param,String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		return getFptBillItemsSendReceiveQtyByEmsNoTrNo(param,fptBusinessType);
	}


	/**
	 * 合同定量(DZSC or BCS )
	 * HYQ
	 * @return key = 手册+帐册编号
	 */
	private Map<String,TempFptApplySurplus> getContractByEmsNoSeqNum(TempFptAppParam param) {
		Map<String,TempFptApplySurplus> map = new HashMap<String,TempFptApplySurplus>();
		List list = new ArrayList();
		int projectType = param.getProjectType().intValue();
		//
		// select b.emsNo,SUM(a.qty)
		//
		if (ProjectType.BCS == projectType) {
			list = this.fptManageDao.findBcsByEmsNoSeqNum(param);
		} else if (ProjectType.DZSC == projectType) {
			list = this.fptManageDao.findDzscByEmsNoSeqNum(param);
		}
		for (int i = 0; i < list.size(); i++) {
			TempFptApplySurplus t = new TempFptApplySurplus();
			t.setInOutFlag(param.getInOutFlag());
			//
			// 填充分组后的值
			//
			
			Object[] objs = (Object[]) list.get(i);
			String emsNo = objs[0] == null ? "" : (String) objs[0];
			t.setEmsNo(emsNo);
			
			Integer seqNum = objs[1] == null ? 0 : (Integer) objs[1];
			t.setTrNo(seqNum);
			
			Integer credenceNo = objs[2] == null ? 0 : (Integer) objs[2];
			t.setCredenceNo(credenceNo);
			
			Date contractEndDate = objs[3] == null ? null : (Date) objs[3];
			t.setContractEndDate(contractEndDate);
			
			String complex = objs[4] == null ? "" : (String) objs[4];
			t.setComplex(complex);
			
			String name = objs[5] == null ? "" : (String) objs[5];
			t.setName(name);
			
			String spec = objs[6] == null ? "" : (String) objs[6];
			t.setSpec(spec);
			
			String unitName = objs[7] == null ? "" : (String) objs[7];
			t.setUnitName(unitName);

			Double contractQty = objs[8] == null ? 0.0 : (Double) objs[8];
			t.setContractQty(contractQty);

			String key = emsNo + "@" + seqNum ;
			map.put(key, t);
		}
		return map;
	}
	
	
	/**
	 * HYQ
	 * 报关单 (转厂已报关的数量)
	 * key=(手册号+序号)
	 */
	private Map<String, Double> getDeclarationTransferFactoryByEmsNoSeqNum(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		list = this.fptManageDao
				.findDeclarationTransferFactoryByEmsNoSeqNum(param);
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			String emsNo = objs[0] == null ? "" : (String) objs[0];
			Integer seqNum = objs[1] == null ? 0 : (Integer) objs[1];
			Double declarationTransferFactoryQty = objs[2] == null ? 0.0 : (Double) objs[2];
			String key = emsNo + "@" + seqNum;
			map.put(key, declarationTransferFactoryQty);
		}
		return map;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	//
	// end 深加工结转手册余量分析 hyq
	//
	// /////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	// ///////////////////////////////////////////////////////////////////////////////////
	//
	// begin 申请表余量分析报表(明细)
	//
	// /////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 获得申请单余量分析报表(明细)
	 */
	public List<TempFptAppSpareAnalyesDetail> findTempFptAppSpareAnalyesDetail(
			TempFptAppParam param) {
		List<TempFptAppSpareAnalyesDetail> resultList = new ArrayList<TempFptAppSpareAnalyesDetail>();
		if (param == null) {
			return resultList;
		}
		int projectType = param.getProjectType().intValue();
		//
		// select b.outTradeName,
		// a.codeTs.code,a.name,a.spec,a.unit.name,b.appNo,a.inEmsNo
		// 以 FptAppItem 表为基标的汇总统计
		//
		// 手册数据
		Map<String, TempFptAppSpareAnalyesDetail> mapByContract = this
				.getTempFptAppSpareAnalyesDetailByContract(param);
		Map<String, String> existMapByFpt = new HashMap<String, String>();
		// 关封数据
		List list = this.fptManageDao.findFptAppItemsDetailByParam(param);
		Map<String, TempFptAppSpareAnalyesDetail> mapByfpt = new HashMap<String, TempFptAppSpareAnalyesDetail>();

		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String tradeName = objs[0] == null ? "" : (String) objs[0];
			String complex = objs[1] == null ? "" : (String) objs[1];
			String name = objs[2] == null ? "" : (String) objs[2];
			String spec = objs[3] == null ? "" : (String) objs[3];
			String unitName = objs[4] == null ? "" : (String) objs[4];
			String appNo = objs[5] == null ? "" : (String) objs[5];
			String emsNo = objs[6] == null ? "" : (String) objs[6];
			Integer trNo = (Integer) objs[7]; // 项号 HH2013.11.19
			Double qty = objs[8] == null ? 0.0 : (Double) objs[8];

			//
			// 商品编码+名称+规格+单位+手册/帐册编号
			//
			// String key = complex + "/" + name + "/" + spec + "/" + unitName +
			// "/" + emsNo;
			// 咨询 小蓉 后 key = 项号+手册/帐册编号 HH2013.11.19
			String key = trNo + "/" + emsNo;
			/**
			 * TempFptAppSpareAnalyesDetail t = mapByContract.get(key); if (t ==
			 * null) { t = new TempFptAppSpareAnalyesDetail(); }
			 **/
			TempFptAppSpareAnalyesDetail t = new TempFptAppSpareAnalyesDetail();
			t.setInOutFlag(param.getInOutFlag());
			t.setComplex(complex);
			t.setName(name);
			t.setTradeName(tradeName);
			t.setSpec(spec);
			t.setUnitName(unitName);
			t.setAppNo(appNo);
			t.setEmsNo(emsNo);

			t.setTradeCode(trNo.toString());
			// 3.申请表数量
			t.setF3(qty);
			//
			// 加入
			//
			resultList.add(t);
			existMapByFpt.put(key, key);
		}

		Iterator<Map.Entry<String, TempFptAppSpareAnalyesDetail>> iterator = mapByContract
				.entrySet().iterator();
		for (; iterator.hasNext();) {
			Map.Entry<String, TempFptAppSpareAnalyesDetail> e = iterator.next();
			String key = e.getKey();
			TempFptAppSpareAnalyesDetail t = e.getValue();
			//
			// 加入
			//
			if (!existMapByFpt.containsKey(key)) {
				resultList.add(t);
			}
		}

		//
		// 1.合同定量(DZSC or BCS )
		//
		Map<String, Double> f1Map = this.getF1ByDetail(mapByContract);
		//
		// 2.报关用量
		//
		Map<String, Double> f2Map = this.getF2ByDetail(param);
		//
		// 4.累计申请表数量 获得 f4 map
		//
		Map<String, Double> f4Map = this.getF4ByDetail(param);
		//
		// 7.已发送数量 （收发货)
		//
		Map<String, Double> f7Map = this.getF7ByDetail(param,
				FptBusinessType.FPT_BILL);
		//
		// 8.未发送数量（收发货)
		//
		Map<String, Double> f8Map = this.getF8ByDetail(param,
				FptBusinessType.FPT_BILL);

		//
		// 7.已发送数量
		//
		Map<String, Double> f7BackMap = this.getF7ByDetail(param,
				FptBusinessType.FPT_BILL_BACK);
		//
		// 8.未发送数量1
		//
		Map<String, Double> f8BackMap = this.getF8ByDetail(param,
				FptBusinessType.FPT_BILL_BACK);

		//
		// 10.已转厂数量(按客户分的) + emsNo
		//
		Map<String, Double> f10Map = this.getF10ByDetail(param);
		//
		// 填充其它栏位
		//
		for (TempFptAppSpareAnalyesDetail t : resultList) {
			//
			// 商品编码+名称+规格+单位+手册号/帐册号
			//
			// String key = t.getComplex() + "/" + t.getName() + "/" +
			// t.getSpec() + "/" + t.getUnitName() + "/" + t.getEmsNo();
			String key = t.getTradeCode() + "/" + t.getEmsNo();
			//
			// 商品编码+名称+规格+单位+emsNo+客户名
			//
			String keyBreifName = key + "/" + t.getTradeName();
			//
			// 商品编码+名称+规格+单位+emsNo+客户名+appNo
			//
			String key7Or8 = key + "/" + t.getTradeName() + "/" + t.getAppNo();
			//
			// 1.合同定量(DZSC or BCS )
			//
			Double f1 = f1Map.get(key) == null ? 0.0 : f1Map.get(key);
			t.setF1(f1);
			//
			// 2.报关用量
			//
			Double commonAmount = f2Map.get(key) == null ? 0.0 : f2Map.get(key);
			if (ProjectType.BCUS == projectType) {
				t.setF2(commonAmount);
			} else {
				t.setF2(f1 - commonAmount);
			}
			//
			// 4.累计申请表数量
			//
			t.setF4(f4Map.get(key) == null ? 0.0 : f4Map.get(key));
			//
			// 7.已发送数量
			//
			t.setF7(f7Map.get(key7Or8) == null ? 0.0 : f7Map.get(key7Or8));
			t.setF7(t.getF7()
					- (f7BackMap.get(key7Or8) == null ? 0.0 : f7BackMap
							.get(key7Or8)));
			//
			// 8.未发送数量
			//
			t.setF8(f8Map.get(key7Or8) == null ? 0.0 : f8Map.get(key7Or8));
			t.setF8(t.getF8()
					- (f8BackMap.get(key7Or8) == null ? 0.0 : f8BackMap
							.get(key7Or8)));
			//
			// 10.已转厂数量(按客户分的)
			//
			t.setF10(f10Map.get(keyBreifName) == null ? 0.0 : f10Map
					.get(keyBreifName));
		}
		return resultList;
	}

	/**
	 * 4.累计申请表数量
	 * 
	 * @return key = 商品编码+名称+规格+单位+手册/帐册编号
	 */
	private Map<String, Double> getF4ByDetail(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		//
		// select a.codeTs.code,a.name,a.spec,a.unit.name,b.emsNo,SUM(a.qty)
		//
		List list = this.fptManageDao.findFptAppItemsDetailByParam2(param);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String emsNo = objs[4] == null ? "" : (String) objs[4];
			Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
			Integer trNo = (Integer) objs[6];
			//
			// 商品编码+名称+规格+单位+手册/帐册编号
			//
			// String key = complex + "/" + name + "/" + spec + "/" + unitName +
			// "/" + emsNo;
			String key = trNo + "/" + emsNo;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 1.合同定量(DZSC or BCS )
	 * 
	 * @return key = 商品编码+名称+规格+单位+手册/帐册编号
	 */
	private Map<String, TempFptAppSpareAnalyesDetail> getTempFptAppSpareAnalyesDetailByContract(
			TempFptAppParam param) {
		Map<String, TempFptAppSpareAnalyesDetail> map = new HashMap<String, TempFptAppSpareAnalyesDetail>();
		List list = new ArrayList();
		int projectType = param.getProjectType().intValue();
		//
		// select a.complex.code,a.name,a.spec,a.unit.name,b.emsNo,SUM(a.qty)
		//
		if (ProjectType.BCS == projectType) {
			list = this.fptManageDao.findSingleContractAmoutByBcs(param);
		} else if (ProjectType.DZSC == projectType) {
			list = this.fptManageDao.findSingleContractAmoutByDzsc(param);
		}
		for (int i = 0; i < list.size(); i++) {
			TempFptAppSpareAnalyesDetail t = new TempFptAppSpareAnalyesDetail();
			t.setInOutFlag(param.getInOutFlag());
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String emsNo = objs[4] == null ? "" : (String) objs[4];
			int credenceNo = (Integer) objs[5];// 料件序号
			Double qty = objs[5] == null ? 0.0 : (Double) objs[6];
			t.setComplex(complex);
			t.setName(name);
			// t.setTradeName(tradeName);
			t.setSpec(spec);
			t.setUnitName(unitName);
			// t.setAppNo(appNo);
			t.setEmsNo(emsNo);
			t.setF1(qty);

			//
			// 商品编码+名称+规格+单位+手册/帐册编号
			//
			// String key = complex + "/" + name + "/" + spec + "/" + unitName +
			// "/" + emsNo;
			//
			// key = 料件序号+手册/帐册编号 HH2013.11.18 小蓉说的
			//
			String key = credenceNo + "/" + emsNo;
			map.put(key, t);
		}
		return map;
	}

	/**
	 * 1.合同定量(DZSC or BCS )
	 * 
	 * @return key = 商品编码+名称+规格+单位
	 */
	private Map<String, Double> getF1ByDetail(
			Map<String, TempFptAppSpareAnalyesDetail> mapByContract) {
		Map<String, Double> map = new HashMap<String, Double>();

		Iterator<Map.Entry<String, TempFptAppSpareAnalyesDetail>> iterator = mapByContract
				.entrySet().iterator();
		for (; iterator.hasNext();) {
			Map.Entry<String, TempFptAppSpareAnalyesDetail> e = iterator.next();
			String key = e.getKey();
			Double qty = e.getValue().getF1();
			map.put(key, qty);
		}
		// List list = new ArrayList();
		// for (int i = 0; i < list.size(); i++) {
		// //
		// // 填充分组后的值
		// //
		// Object[] objs = (Object[]) list.get(i);
		// String complex = objs[0] == null ? "" : (String) objs[0];
		// String name = objs[1] == null ? "" : (String) objs[1];
		// String spec = objs[2] == null ? "" : (String) objs[2];
		// String unitName = objs[3] == null ? "" : (String) objs[3];
		// String emsNo = objs[4] == null ? "" : (String) objs[4];
		// Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
		// //
		// // 商品编码+名称+规格+单位+手册/帐册编号
		// //
		// String key = complex + "/" + name + "/" + spec + "/" + unitName
		// + "/" + emsNo;
		// map.put(key, qty);
		// }
		return map;
	}

	/**
	 * 获得已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,sum(a.commAmount)
	 */
	private Map<String, Double> getF2ByDetail(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// a.complex.code,a.commName,a.commSpec,a.unit.Name,b.emsNo,sum(a.commAmount)
		//
		list = this.fptManageDao.findSingleCommInfoTotalAmount(param);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String emsNo = objs[4] == null ? "" : (String) objs[4];
			Double qty = objs[5] == null ? 0.0 : (Double) objs[5];
			Integer commSerialNo = (Integer) objs[6];
			//
			// 商品编码+名称+规格+单位+手册/帐册编号
			//
			String key = commSerialNo + "/" + emsNo;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec
	 * ,a.unit.name,SUM(a.qty)
	 */
	private Map<String, Double> getF7ByDetail(TempFptAppParam param,
			String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,b.appNo,b.outEmsNo,
		// SUM(a.qty)
		//
		list = this.fptManageDao.findFptBillItemsDetailByParam(param,
				fptBusinessType);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String tradeName = objs[0] == null ? "" : (String) objs[0];
			String complex = objs[1] == null ? "" : (String) objs[1];
			String name = objs[2] == null ? "" : (String) objs[2];
			String spec = objs[3] == null ? "" : (String) objs[3];
			String unitName = objs[4] == null ? "" : (String) objs[4];
			String appNo = objs[5] == null ? "" : (String) objs[5];
			String emsNo = objs[6] == null ? "" : (String) objs[6];
			Double qty = objs[7] == null ? 0.0 : (Double) objs[7];
			//
			// 商品编码+名称+规格+单位+emsNo+客户名+appNo
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName
					+ "/" + emsNo + "/" + tradeName + "/" + appNo;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得转厂送货自参数对象 数据列如下: select
	 * b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,
	 * b.appNo,a.inEmsNo,SUM(a.qty)
	 */
	private Map<String, Double> getF8ByDetail(TempFptAppParam param,
			String fptBusinessType) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// b.issueTradeName,a.complex.code,a.commName,a.commSpec,a.unit.name,
		// b.appNo,a.inEmsNo,SUM(a.qty)
		//
		list = this.fptManageDao.findFptBillItemsDetailByParam2(param,
				fptBusinessType);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String tradeName = objs[0] == null ? "" : (String) objs[0];
			String complex = objs[1] == null ? "" : (String) objs[1];
			String name = objs[2] == null ? "" : (String) objs[2];
			String spec = objs[3] == null ? "" : (String) objs[3];
			String unitName = objs[4] == null ? "" : (String) objs[4];
			String appNo = objs[5] == null ? "" : (String) objs[5];
			String emsNo = objs[6] == null ? "" : (String) objs[6];
			Double qty = objs[7] == null ? 0.0 : (Double) objs[7];
			//
			// 商品编码+名称+规格+单位+emsNo+客户名+appNo
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName
					+ "/" + emsNo + "/" + tradeName + "/" + appNo;
			map.put(key, qty);
		}
		return map;
	}

	/**
	 * 获得转厂已报关的数量 select
	 * a.complex.code,a.commName,a.commSpec,a.unit.Name,brief.name
	 * ,sum(a.commAmount)
	 */
	private Map<String, Double> getF10ByDetail(TempFptAppParam param) {
		Map<String, Double> map = new HashMap<String, Double>();
		List list = new ArrayList();
		//
		// select
		// a.complex.code,a.commName,a.commSpec,a.unit.Name,brief.name,sum(a.commAmount)
		//
		list = this.fptManageDao
				.findSingleCommInfoTotalAmountByTransferFactory(param);
		for (int i = 0; i < list.size(); i++) {
			//
			// 填充分组后的值
			//
			Object[] objs = (Object[]) list.get(i);
			String complex = objs[0] == null ? "" : (String) objs[0];
			String name = objs[1] == null ? "" : (String) objs[1];
			String spec = objs[2] == null ? "" : (String) objs[2];
			String unitName = objs[3] == null ? "" : (String) objs[3];
			String breifName = objs[4] == null ? "" : (String) objs[4];
			String emsNo = objs[5] == null ? "" : (String) objs[5];
			Double qty = objs[6] == null ? 0.0 : (Double) objs[6];
			//
			// 商品编码+名称+规格+单位+emsNo+客户名
			//
			String key = complex + "/" + name + "/" + spec + "/" + unitName
					+ "/" + emsNo + "/" + breifName;
			map.put(key, qty);
		}
		return map;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	//
	// end 申请表余量分析报表(明细)
	//
	// /////////////////////////////////////////////////////////////////////////////////////

	/**
	 * -----------------------------------------结转单据对应报表查询----------------------
	 * *
	 */

	public List<TempObject> findBomNoByPara(boolean impExpFlagCode,
			String billTypeCode) {
		List<TempObject> list = new ArrayList<TempObject>();
		List sourceList = this.fptManageDao.findBomNoByPara(impExpFlagCode);
		for (int i = 0; i < sourceList.size(); i++) {
			Object[] objs = (Object[]) sourceList.get(i);
			if (objs[0] == null || ((String) objs[0]).trim().equals("")) {
				continue;
			}
			TempObject temp = new TempObject();
			temp.setObject((String) objs[0]); // 料号
			temp.setObject1((String) objs[1]);// 名称
			list.add(temp);
		}
		return list;
	}

	/**
	 * 取单据中心转结转单转报关单
	 * 
	 * @param inOutFlag
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @param billNo
	 * @param bomNo
	 * @return
	 */
	public List getMakeFptBillFromCasBill(boolean inOutFlag, ScmCoc scmCoc,
			Date beginDate, Date endDate, String billNo, String bomNo) {
		List arrayList = new ArrayList();
		List list = fptManageDao.findMakeFptBillFromCasBill(inOutFlag, scmCoc,
				beginDate, endDate, billNo, bomNo);
		if (list.isEmpty())
			return arrayList;
		for (int i = 0; i < list.size(); i++) {
			BillDetail billDetail = (BillDetail) list.get(i);

			String fptBillItemId = fptManageDao.getfptBillItemString(billDetail
					.getId());
			if (fptBillItemId == null || "".equals(fptBillItemId))
				continue;
			FptBillItem fptBillItem = fptManageDao
					.getfptBillItemId(fptBillItemId);
			TempCasBillTOFptTOCustomsReport dg = new TempCasBillTOFptTOCustomsReport();

			dg.setInOutFlag(inOutFlag ? "1" : "0");
			dg.setBillDetail(billDetail);
			dg.setFptBillItem(fptBillItem);
			MakeFptBillCustomsDeclaration CommInfo = fptManageDao
					.getCustomsFromMakeListToCustomsId(fptBillItemId);
			if (CommInfo != null) {
				BaseCustomsDeclarationCommInfo customsCommInfo = fptManageDao
						.getCustomsFromMakeListToCustoms(
								CommInfo.getCustomsDeclarationCommInId(),
								CommInfo.getProjectType());
				if (customsCommInfo != null) {
					dg.setCustomsCommInfo(customsCommInfo);
				}
			} else {
				dg.setCustomsCommInfo(null);
			}
			arrayList.add(dg);
		}
		return arrayList;
	}

	/**
	 * 取单据中心转结转单转报关单
	 * 
	 * @param inOutFlag
	 * @param scmCoc
	 * @param beginDate
	 * @param endDate
	 * @param billNo
	 * @param bomNo
	 * @return
	 */
	public List getMakeFptBillCustomsDeclaration(boolean inOutFlag,
			String appNo, Date beginDate, Date endDate, ScmCoc scmCoc,
			String emsNo) {
		List arrayList = new ArrayList();
		List list = fptManageDao.findMakeFptBillCustomsDeclaration(inOutFlag,
				appNo, beginDate, endDate, scmCoc, emsNo);
		if (list.isEmpty())
			return arrayList;
		for (int i = 0; i < list.size(); i++) {
			FptBillItem fptBillItem = (FptBillItem) list.get(i);
			TempCasBillTOFptTOCustomsReport dg = new TempCasBillTOFptTOCustomsReport();
			dg.setInOutFlag(inOutFlag ? "1" : "0");
			dg.setFptBillItem(fptBillItem);
			MakeFptBillCustomsDeclaration CommInfo = fptManageDao
					.getCustomsFromMakeListToCustomsId(fptBillItem
							.getFptBillHead().getAppNo());
			if (CommInfo != null) {
				BaseCustomsDeclarationCommInfo customsCommInfo = fptManageDao
						.getCustomsFromMakeListToCustoms(
								CommInfo.getCustomsDeclarationCommInId(),
								CommInfo.getProjectType());
				if (customsCommInfo != null) {
					dg.setCustomsCommInfo(customsCommInfo);
				}
			} else {
				dg.setCustomsCommInfo(null);
			}
			arrayList.add(dg);
		}
		return arrayList;
	}

	/**
	 * 根据深加工转厂单据转成的报关单来取消结转单据报关单号
	 * 
	 */
	public void cancelCustomsDeclarationCode(
			List<TempCasBillTOFptTOCustomsReport> list) {
		//
		// 删除中间表信息
		//
		List<MakeBillCorrespondingInfoBase> deleteList = new ArrayList<MakeBillCorrespondingInfoBase>();
		for (int i = 0; i < list.size(); i++) {

			TempCasBillTOFptTOCustomsReport m = list.get(i);

			MakeBillCorrespondingInfoBase c = fptManageDao
					.findMakeBillCorrespondingInfoBase(m.getBillDetail()
							.getId(), m.getCustomsCommInfo()
							.getBaseCustomsDeclaration().getImpExpType());

			double deleteAlreadyCorrespondingAmount = c.getAmount() == null ? 0.0
					: c.getAmount();
			//
			// 修改单据信息
			//
			double customNum = m.getBillDetail().getCustomNum() == null ? 0.0
					: m.getBillDetail().getCustomNum();
			m.getBillDetail().setCustomNum(
					customNum - deleteAlreadyCorrespondingAmount);
			//
			// 为了去掉对应表中的单据的对应关系
			//
			String oldCustomNo = m.getBillDetail().getCustomNo() == null ? ""
					: m.getBillDetail().getCustomNo().trim();
			String newCustomNo = "";
			String deleteCustomNo = m.getCustomsCommInfo()
					.getBaseCustomsDeclaration().getCustomsDeclarationCode()
					+ "("
					+ m.getCustomsCommInfo().getBaseCustomsDeclaration()
							.getEmsHeadH2k() + ")";

			String[] strArray = oldCustomNo.split(",");
			for (int j = 0; j < strArray.length; j++) {
				if (!deleteCustomNo.trim().equals(strArray[j].trim())) {
					newCustomNo += strArray[j].trim() + ",";
				}
			}
			if (newCustomNo != null && !newCustomNo.equals("")) {
				// 去掉最后一个逗号
				newCustomNo = newCustomNo
						.substring(0, newCustomNo.length() - 1);
			}
			m.getBillDetail().setCustomNo(
					newCustomNo == null ? "" : newCustomNo);

			this.fptManageDao.getHibernateTemplate().saveOrUpdate(
					m.getBillDetail());
			//
			// 中间对应表
			//
			deleteList.add(c);
		}
		this.contractDao.deleteAll(deleteList);
	}

	/**
	 * 根据深加工转厂单据转成的报关单来对应结转单据报关单号
	 * 
	 */

	public void reciveCustomsDeclarationCode(
			List<TempCasBillTOFptTOCustomsReport> listC) {
		//
		// 修改单据信息
		//
		for (int k = 0; k < listC.size(); k++) {
			TempCasBillTOFptTOCustomsReport c = listC.get(k);

			// 无对应的报关单
			if (c.getCustomsCommInfo() == null) {
				throw new RuntimeException("无对应的报关单!");
			} else if (c.getCustomsCommInfo().getBaseCustomsDeclaration()
					.getCustomsDeclarationCode() == null
					|| "".equals(c.getCustomsCommInfo()
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode())) {
				throw new RuntimeException("无对应的报关单号!");
			}
			double tranferAmount = 0.0;
			tranferAmount = c.getCustomsCommInfo().getCommAmount();
			double oldCustomNum = c.getBillDetail().getCustomNum() == null ? 0.0
					: c.getBillDetail().getCustomNum();
			c.getBillDetail().setCustomNum(oldCustomNum + tranferAmount);
			String newCustomNo = c.getCustomsCommInfo()
					.getBaseCustomsDeclaration().getCustomsDeclarationCode()
					+ "("
					+ c.getCustomsCommInfo().getBaseCustomsDeclaration()
							.getEmsHeadH2k() + ")";
			String oldCustomNo = c.getBillDetail().getCustomNo() == null ? ""
					: c.getBillDetail().getCustomNo().trim();
			c.getBillDetail().setCustomNo(
					("".equals(oldCustomNo) ? "" : oldCustomNo + ",")
							+ newCustomNo);

			this.contractDao.saveOrUpdate(c.getBillDetail());
			//
			// 产生中间信息 (一个成品表,一个料件表)
			//
			this.makeMakeBillCorrspondingInfo(c, tranferAmount);
		}

	}

	/**
	 * 单据对应时产生的中间信息
	 * 
	 * @param c
	 *            报关单商品信息与海关帐单据的对应
	 * @param billDetail
	 *            单据明细
	 * @param tranferAmount
	 *            结转数量
	 * @return 生产单据对应的中间信息
	 */
	private MakeBillCorrespondingInfoBase makeMakeBillCorrspondingInfo(
			TempCasBillTOFptTOCustomsReport c, Double tranferAmount) {

		int impExpType = c.getCustomsCommInfo().getBaseCustomsDeclaration()
				.getImpExpType();
		String materielType = BillUtil.getMaterielTypeByImpExpType(impExpType);// 获取（料件/成品/半成品）

		MakeBillCorrespondingInfoBase temp = null;
		if (materielType.equalsIgnoreCase(MaterielType.MATERIEL)) {
			temp = new MakeBillCorrespondingInfoByMateriel();

		} else if (materielType.equalsIgnoreCase(MaterielType.FINISHED_PRODUCT)) {
			temp = new MakeBillCorrespondingInfoByProduct();
		} else {
			return null;
		}

		temp.setBillDetailId(c.getBillDetail().getId());
		temp.setBillNo(c.getBillDetail().getBillMaster().getBillNo());
		temp.setImpExpType(impExpType);
		temp.setCompany(CommonUtils.getCompany());
		temp.setCommName(c.getCustomsCommInfo().getCommName());
		temp.setCommSpec(c.getCustomsCommInfo().getCommSpec());
		temp.setCustomsDeclarationCode(c.getCustomsCommInfo()
				.getBaseCustomsDeclaration().getCustomsDeclarationCode());
		temp.setCustomsDeclarationCommInfoId(c.getCustomsCommInfo().getId());
		temp.setCustomsDeclarationId(c.getCustomsCommInfo()
				.getBaseCustomsDeclaration().getId());
		temp.setEmsHeadH2k(c.getCustomsCommInfo().getBaseCustomsDeclaration()
				.getEmsHeadH2k());
		temp.setPtName(c.getBillDetail().getPtName());
		temp.setPtPart(c.getBillDetail().getPtPart());
		temp.setPtSpec(c.getBillDetail().getPtSpec());
		temp.setValidDate(c.getBillDetail().getBillMaster().getValidDate());
		temp.setScmCoc(c.getCustomsCommInfo().getBaseCustomsDeclaration()
				.getCustomer());
		temp.setAmount(tranferAmount);
		temp.setBillTypeName(c.getBillDetail().getBillMaster().getBillType()
				.getName());
		this.fptManageDao.getHibernateTemplate().saveOrUpdate(temp);
		return temp;
	}

	/**
	 * 获得转厂余量数据信息
	 * 
	 * @param projectType
	 * @param inOutFlag
	 * @param emsNo
	 * @param seqNum
	 * @param fptAppItem
	 * @return
	 */
	public TempFptExeInfo findContractExeInfoByFpt(int projectType,
			String inOutFlag, String emsNo, int seqNum, FptAppItem fptAppItem) {
		TempFptExeInfo info = new TempFptExeInfo();
		//
		// bcs
		//
		if (ProjectType.BCS == projectType) {
			if (FptInOutFlag.IN.equals(inOutFlag)) { // 进口(料件)
				ContractImg img = contractDao.findContractImgByEmsNoSeqNum(
						emsNo, seqNum);
				if (img == null) {
					return info;
				}
				info.setContractAmount(img.getAmount() == null ? 0.0 : img
						.getAmount());
			} else { // 出口(成品)
				ContractExg exg = contractDao.findContractExgByEmsNoSeqNum(
						emsNo, seqNum);
				if (exg == null) {
					return info;
				}
				info.setContractAmount(exg.getExportAmount() == null ? 0.0
						: exg.getExportAmount());
			}
		} else if (ProjectType.DZSC == projectType) {
			if (FptInOutFlag.IN.equals(inOutFlag)) { // 进口(料件)
				DzscEmsImgBill img = dzscDao.findDzscEmsImgBillByEmsNoSeqNum(
						emsNo, seqNum);
				// 合同定量
				info.setContractAmount(img.getAmount() == null ? 0.0 : img
						.getAmount());
			} else { // 出口(成品)
				DzscEmsExgBill exg = dzscDao.findDzscEmsExgBillByEmsNoSeqNum(
						emsNo, seqNum);
				if (exg == null) {
					return info;
				}
				info.setContractAmount(exg.getAmount() == null ? 0.0 : exg
						.getAmount());
			}
		}
		//
		// 查找转厂正在执行已使用的量 by emsNo,seqNum (备案序号)
		//
		Double useAmoutByProcessExe = this.fptManageDao
				.findUseAmoutByProcessExe(inOutFlag, emsNo, seqNum);
		//
		// 查找转厂已使用的量 by emsNo,seqNum (备案序号)
		//
		Double useAmoutByAll = this.fptManageDao.findUseAmoutBySeqNum(
				inOutFlag, emsNo, seqNum, fptAppItem);
		if (ProjectType.BCUS == projectType) {
			info.setContractRemain(useAmoutByProcessExe);
			info.setCurrentRemain(useAmoutByAll);
		} else {
			info.setContractRemain(info.getContractAmount()
					- useAmoutByProcessExe);
			info.setCurrentRemain(info.getContractAmount() - useAmoutByAll);
		}
		//
		// 删掉报关单的使用量
		//
		if (ProjectType.BCS == projectType) {
			boolean isMateriel = false; // 成品
			if (FptInOutFlag.IN.equals(inOutFlag)) { // 料件
				isMateriel = true;
			}
			BcsContractExeInfo bcsInfo = this.contractExeLogic
					.findBcsContractExeInfo2(isMateriel, emsNo, seqNum);
			//成品：合同余量 = 合同定量 -正在执行的关封数量- （已生效的成品出口+返工复出 -退厂返工【非转出出口数】）
			info.setContractRemain(info.getContractRemain()
					- bcsInfo.getContractRemain());
			//当前余量 = 合同定量 - 正在执行和已结案关封数量 -（已生效和未生效的成品出口+返工复出 -退厂返工【非转出出口数】）
			info.setCurrentRemain(info.getCurrentRemain()
					- bcsInfo.getCurrentRemain());
			info.setApplyRemain(info.getContractAmount()
					- bcsInfo.getApplyRemain());
		} else if (ProjectType.DZSC == projectType) {
			boolean isMateriel = false; // 成品
			if (FptInOutFlag.IN.equals(inOutFlag)) { // 料件
				isMateriel = true;
			}
			DzscContractExeInfo dzscInfo = this.dzscContractExeLogic
					.findDzscContractExeInfo2(isMateriel, emsNo, seqNum);
			info.setContractRemain(info.getContractRemain()
					- dzscInfo.getContractRemain());
			info.setCurrentRemain(info.getCurrentRemain()
					- dzscInfo.getCurrentRemain());
		}
		
		//
		//hyq
		//减去收货或送货,加上收退货或退数
		//
		TempFptAppParam param = new TempFptAppParam();
		param.setProjectType(projectType);
		param.setInOutFlag(inOutFlag);
		param.setEmsNo(emsNo);
		param.setTrGno(seqNum);
		
		Double fptBillItemsSendReceive = 0.0;
		Double fptBillItemsSendReceiveQty = 0.0;
		
		//收货或送货
		List list = this.fptManageDao.findFptBillItemsSendReceiveQtyByEmsNoTrNo(param,FptBusinessType.FPT_BILL);
		if(list!=null && list.size()==1){
			Object[] objs = (Object[]) list.get(0);
			fptBillItemsSendReceive = objs[2] == null ? 0.0 : (Double) objs[2];
		}
		//收退货或退数
		List list2 = this.fptManageDao.findFptBillItemsSendReceiveQtyByEmsNoTrNo(param,FptBusinessType.FPT_BILL_BACK);
		if(list!=null && list2.size()==1){
			Object[] objs = (Object[]) list2.get(0);
			fptBillItemsSendReceiveQty = objs[2] == null ? 0.0 : (Double) objs[2];
		}
		
		info.setApplyRemain(info.getApplyRemain()-fptBillItemsSendReceive + fptBillItemsSendReceiveQty);
		
		

		info.setContractAmount(CommonUtils.getDoubleByDigit(
				info.getContractAmount(), 2));
		info.setContractRemain(CommonUtils.getDoubleByDigit(
				info.getContractRemain(), 2));
		info.setCurrentRemain(CommonUtils.getDoubleByDigit(
				info.getCurrentRemain(), 2));
		info.setApplyRemain(CommonUtils.getDoubleByDigit(
				info.getApplyRemain(), 2));

		return info;
		
		
		
		
	}

	/**
	 * 收关货单据导出资料
	 */
	public TempResultSet exportFptBillItemToExcel(String parentId,
			String inOutFlag) {
		List item = new ArrayList();
		List<List> rows = new ArrayList<List>();
		String billHead[] = { "收货企业内部编号", "收货企业编码", "收货企业名称", "收货日期", "收货申报人",
				"收货申报时间", "收货申报企业9位组织机构代码", "发货申报企业组织机构名称", "备注", "转入转出标志",
				"序号", "发货序号", "手册号", "料号", "归并前商品名称", "归并前规格型号", "申表请序号", "项号",
				"海关商品编码", "商品名称", "商品规格", "交易单位", "交易数量", "计量单位", "申报数量", "备注" };
		// 表头
		FptBillHead head = fptManageDao.findFptBillHeadById(parentId);
		item.add(head.getReceiveCopBillNo());
		item.add(head.getReceiveTradeCod());
		item.add(head.getReceiveTradeName());
		item.add(head.getReceiveDate());
		item.add(head.getReceiveIsDeclaEm());
		item.add(head.getReceiveIsDeclaDate());
		item.add(head.getReceiveAgentCode());
		item.add(head.getReceiveAgentName());
		item.add(head.getReceiveNote());
		// 表体
		List ls = fptManageDao.findFptBillItemByFptbillheadId(parentId,
				inOutFlag);
		for (int i = 0; i < ls.size(); i++) {
			FptBillItem fptitem = (FptBillItem) ls.get(i);
			item.add(fptitem.getBillSort());
			item.add(fptitem.getListNo());
			item.add(fptitem.getOutNo());//
			item.add(fptitem.getEmsNo());
			item.add(fptitem.getCopGNo());
			item.add(fptitem.getCopGName());
			item.add(fptitem.getCopGModel());
			item.add(fptitem.getAppGNo());
			item.add(fptitem.getTrGno());
			item.add(fptitem.getComplex().getCode());
			item.add(fptitem.getCommName());
			item.add(fptitem.getCommSpec());
			item.add(fptitem.getTradeUnit().getName());
			item.add(fptitem.getTradeQty());
			item.add(fptitem.getUnit().getName());
			item.add(fptitem.getQty());
			item.add(fptitem.getNote());
		}
		TempResultSet temp = new TempResultSet();
		temp.setColumnNames(billHead);
		temp.setRows(item);
		return temp;
	}

	/**
	 * 收送货单据导入对方的资料
	 * 
	 * @param listHead
	 * @param listItem
	 */
	public void saveFptBillItemFormOtherSide(List listHead, List listItem,
			boolean isOverwrite) {
		if (listHead.size() <= 0) {
			return;
		}
		FptBillHead fptBillHead = ((TempFptBillHead) listHead.get(0)).getT();
		this.fptManageDao.saveFptBillHead(fptBillHead);
		String inOutFlag = null;
		if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
			inOutFlag = FptInOutFlag.IN;
		} else {
			inOutFlag = FptInOutFlag.OUT;
		}
		Map<Integer, FptBillItem> map = new HashMap<Integer, FptBillItem>();
		List list = this.fptManageDao.findFptBillItemByFptbillheadId(
				fptBillHead.getId(), inOutFlag);
		for (int i = 0; i < list.size(); i++) {
			FptBillItem item = (FptBillItem) list.get(i);
			map.put(item.getListNo(), item);
		}
		for (int i = 0; i < listItem.size(); i++) {
			FptBillItem item = ((TempFptBillItem) listItem.get(i)).getT();
			FptBillItem fptBillItem = map.get(item.getListNo());
			if (fptBillItem == null) {
				this.fptManageDao.saveFptBillItem(item);
			} else {
				if (isOverwrite) {
					String id = fptBillItem.getId();
					try {
						PropertyUtils.copyProperties(fptBillItem, item);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					fptBillItem.setId(id);
					this.fptManageDao.saveFptBillItem(fptBillItem);
				}
			}
		}
	}

	/** ---------------------------------------------邮件----------------* */
	/**
	 * 查询申请表与结转单据转出资料
	 */
	public List findAppBillToFptEmail(String sysType, String state,
			String inoutFlag, String makeinout) {
		List temp = new ArrayList();
		List ls = null;
		if ("1".equals(makeinout)) { // 转出
			if (FptBusinessType.FPT_APP.equals(sysType)) {
				ls = fptManageDao.findAppBillToFptEmail(state, inoutFlag);
				for (int i = 0; i < ls.size(); i++) {
					FptAppHead head = (FptAppHead) ls.get(i);
					TempFptEmail email = new TempFptEmail();
					email.setEmsNo(head.getEmsNo());
					email.setSeqNo(head.getSeqNo());
					email.setAppNo(head.getAppNo());
					email.setOutCopAppNo(head.getOutCopAppNo());
					email.setAppState(head.getDeclareState());
					email.setOutName(head.getOutTradeName());
					email.setOutCode(head.getOutTradeCode());
					email.setInOutFlag(head.getInOutFlag());
					email.setBillNo(null);
					email.setSysType(FptBusinessType.FPT_APP);
					temp.add(email);
				}
			} else {// 转出
				ls = fptManageDao.findBillToFptEmail(sysType, state, inoutFlag,
						makeinout);
				if (FptBusinessType.FPT_BILL.equals(sysType)) {
					for (int i = 0; i < ls.size(); i++) {
						FptBillHead head = (FptBillHead) ls.get(i);
						TempFptEmail email = new TempFptEmail();
						email.setEmsNo(head.getOutEmsNo());
						email.setSeqNo(head.getSeqNo());
						email.setAppNo(head.getAppNo());
						email.setBillNo(head.getBillNo());
						email.setOutCopAppNo(head.getIssueCopBillNo());
						email.setAppState(head.getAppState());
						email.setOutName(head.getIssueTradeName());
						email.setOutCode(head.getIssueTradeCod());
						email.setInOutFlag(head.getBillSort());
						email.setSysType(head.getSysType());
						temp.add(email);
					}
				} else {// 转入
					for (int i = 0; i < ls.size(); i++) {
						FptBillHead head = (FptBillHead) ls.get(i);
						TempFptEmail email = new TempFptEmail();
						email.setSeqNo(head.getSeqNo());
						email.setAppNo(head.getAppNo());
						email.setBillNo(head.getBillNo());
						email.setOutCopAppNo(head.getReceiveCopBillNo());
						email.setAppState(head.getAppState());
						email.setOutName(head.getReceiveTradeName());
						email.setOutCode(head.getReceiveTradeCod());
						email.setInOutFlag(head.getBillSort());
						email.setSysType(head.getSysType());
						temp.add(email);
					}
				}
			}
		} else {
			if (FptBusinessType.FPT_APP.equals(sysType)) {
				ls = fptManageDao.findAppBillToFptEmail(state, inoutFlag);
				for (int i = 0; i < ls.size(); i++) {
					FptAppHead head = (FptAppHead) ls.get(i);
					TempFptEmail email = new TempFptEmail();
					email.setInOutFlag(head.getInOutFlag());
					email.setSeqNo(head.getSeqNo());
					email.setAppNo(head.getAppNo());
					email.setOutCopAppNo(head.getInCopAppNo());
					email.setAppState(head.getDeclareState());
					email.setOutName(head.getInTradeName());
					email.setOutCode(head.getInTradeCode());
					email.setSysType(FptBusinessType.FPT_APP);
					temp.add(email);
				}
			} else {
				ls = fptManageDao.findBillToFptEmail(sysType, state, inoutFlag,
						makeinout);
				if (FptBusinessType.FPT_BILL.equals(sysType)) {
					for (int i = 0; i < ls.size(); i++) {
						FptBillHead head = (FptBillHead) ls.get(i);
						TempFptEmail email = new TempFptEmail();
						email.setInOutFlag(head.getBillSort());
						email.setSeqNo(head.getSeqNo());
						email.setAppNo(head.getAppNo());
						email.setBillNo(head.getBillNo());
						email.setOutCopAppNo(head.getReceiveCopBillNo());
						email.setAppState(head.getAppState());
						email.setOutName(head.getReceiveTradeName());
						email.setOutCode(head.getReceiveTradeCod());
						email.setSysType(head.getSysType());
						temp.add(email);
					}
				} else {
					for (int i = 0; i < ls.size(); i++) {
						FptBillHead head = (FptBillHead) ls.get(i);
						TempFptEmail email = new TempFptEmail();
						email.setInOutFlag(head.getBillSort());
						email.setSeqNo(head.getSeqNo());
						email.setAppNo(head.getAppNo());
						email.setBillNo(head.getBillNo());
						email.setOutCopAppNo(head.getIssueCopBillNo());
						email.setAppState(head.getAppState());
						email.setOutName(head.getIssueTradeName());
						email.setOutCode(head.getIssueTradeCod());
						email.setSysType(head.getSysType());
						temp.add(email);
					}
				}
			}

		}

		return temp;
	}

	public static String[] split(String sourceStr, String beginmatchstr,
			String matchStr) {
		if (sourceStr == null) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		while (sourceStr.indexOf(matchStr) != -1) {
			String tempS = sourceStr.substring(
					sourceStr.indexOf(beginmatchstr) + 1,
					sourceStr.indexOf(matchStr));
			sourceStr = sourceStr.substring(sourceStr.indexOf(matchStr)
					+ matchStr.length(), sourceStr.length());
			if ("".equals(tempS)) {
				continue;
			}
			list.add(tempS);
		}
		list.add(sourceStr);
		String[] str = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			str[i] = list.get(i);
		}
		return str;
	}

	/**
	 * 处理邮件
	 * 
	 * @param id
	 */
	public void excEmail(FptEmail email, TempFptEmail temp) {
		FptEmail fp = fptManageDao.findFptEmailParamId(email.getId());
		String mailBody = fp.getMailbody();
		System.out.println("mailBody=" + mailBody);
		if (mailBody == null || "".equals(mailBody)) {
			throw new RuntimeException("没有找到邮件的正件,接收邮件可能有误!");
		}
		String[] over = split(mailBody, "[", "]");
		if (temp.getSysType().equals(FptBusinessType.FPT_APP)) {
			FptAppHead head = fptManageDao.findfptAppHeadByCopBill(
					temp.getOutCopAppNo(), temp.getInOutFlag());
			head.setSeqNo(over[2]);// 统一编号
			head.setAppNo(over[3]);// 申请表编号
			fptManageDao.saveFptAppHead(head);
		} else {
			FptBillHead head = fptManageDao.findfptBillHeadByCopBill(
					temp.getOutCopAppNo(), temp.getInOutFlag());
			head.setSeqNo(over[2]);// 统一编号
			head.setAppNo(over[3]);// 申请表编号
			head.setBillNo(over[5]); // 发退货编号
			fptManageDao.saveFptBillHead(head);
		}
		/**
		 * 处理备案资料下载
		 */
		// 先获取已有的数据
		FptDownData lsImgBill = this.fptManageDao
				.findFptDownDataCopBillNO(over[1]);
		if (lsImgBill == null) {
			FptDownData data = new FptDownData();
			data.setOutCopNo(over[1]); // 内部编号
			data.setSeqNo(over[2]);// 统一编号
			data.setAppNo(over[3]);// 申请表编号
			data.setTradeCode(over[4]);// 企业编号
			if (over[0].equals("结转申请表")) {
				data.setDownLoadState("A");
			} else if (over[0].equals("收发货单")) {
				data.setDownLoadState("C");
			} else if (over[0].equals("退货单")) {
				data.setDownLoadState("B");
			}
			data.setDeclareState(DeclareState.APPLY_POR);
			fptManageDao.saveFptDownData(data);
		} else {
			lsImgBill.setOutCopNo(over[1]); // 内部编号
			lsImgBill.setSeqNo(over[2]);// 统一编号
			lsImgBill.setAppNo(over[3]);// 申请表编号
			lsImgBill.setTradeCode(over[4]);// 企业编号
			if (over[0].equals("结转申请表")) {
				lsImgBill.setDownLoadState("A");
			} else if (over[0].equals("收发货单")) {
				lsImgBill.setDownLoadState("C");
			} else if (over[0].equals("退货单")) {
				lsImgBill.setDownLoadState("B");
			}
			lsImgBill.setDeclareState(DeclareState.APPLY_POR);
			fptManageDao.saveFptDownData(lsImgBill);
		}
		email.setIsCancel(true);
		fptManageDao.saveFptEmail(email);
	}

	/**
	 * 删除多个对像
	 */
	public void deleteObjects(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.fptManageDao.delete(list.get(i));
		}

	}

	/**
	 * 保存多个对像
	 */
	public List saveObjects(List list) {
		for (int i = 0; i < list.size(); i++) {
			this.fptManageDao.saveOrUpdate(list.get(i));
		}
		return list;
	}

	/**
	 * 保存深加工结转-进出货转厂单据Excel导入信息
	 */
	public void saveFptBillHeadFromImport(
			List<TempFptBillHeadImportFromExcel> ls, boolean isOverwrite,
			String inOutFlag) {
		FptBillHead head = null;
		if (ls.size() > 0) {
			head = ((TempFptBillHeadImportFromExcel) ls.get(0))
					.getFptBillItem().getFptBillHead();
		} else {
			return;
		}
		// 先获取已有的数据
		List billItemList = this.fptManageDao.findFptBillItemByFptbillheadId(
				head.getId(), inOutFlag);
		Map<Integer, FptBillItem> billItemMap = new HashMap<Integer, FptBillItem>();
		for (int i = 0; i < billItemList.size(); i++) {
			FptBillItem billItem = (FptBillItem) billItemList.get(i);
			billItemMap.put(billItem.getListNo(), billItem);
		}
		for (int i = 0; i < ls.size(); i++) {
			TempFptBillHeadImportFromExcel tmp = (TempFptBillHeadImportFromExcel) ls
					.get(i);
			FptBillItem billItem = billItemMap.get(tmp.getFptBillItem()
					.getListNo());
			if (billItem == null) {
				// 如果不存在，则直接保存
				billItem = tmp.getFptBillItem();

				int listNo = fptManageDao.findMaxValueByFptBillItem(
						head.getId(), inOutFlag);

				billItem.setListNo(listNo + 1);
				this.fptManageDao.saveOrUpdate(billItem);
			} else {
				if (isOverwrite) {
					FptBillItem billItems = ((TempFptBillHeadImportFromExcel) ls
							.get(i)).getFptBillItem();
					billItem.setAppGNo(billItems.getAppGNo());
					billItem.setBillSort(billItems.getBillSort());
					billItem.setCommName(billItems.getCommName());
					billItem.setCommSpec(billItems.getCommSpec());
					billItem.setComplex(billItems.getComplex());
					billItem.setCopGModel(billItems.getCopGModel());
					billItem.setCopGName(billItems.getCopGName());
					billItem.setCopGNo(billItems.getCopGNo());
					billItem.setListNo(billItems.getListNo());
					billItem.setTradeQty(billItems.getTradeQty());
					billItem.setTradeUnit(billItems.getTradeUnit());
					billItem.setUnit(billItems.getUnit());
					billItem.setNote(billItems.getNote());
					this.fptManageDao.saveOrUpdate(billItem);
				} else {
					continue;
				}
			}
		}
	}

	private Object getExcelCellValue(Sheet sheet, int columnIndex, int rowIndex) {
		Cell cell = sheet.getCell(columnIndex, rowIndex);
		Object returnValue = null;
		DecimalFormat df = new DecimalFormat("#.#########");
		if (cell.getType() == CellType.LABEL) {
			LabelCell labelCell = (LabelCell) cell;
			returnValue = labelCell.getString();
		} else if (cell.getType() == CellType.NUMBER) {
			NumberCell numberCell = (NumberCell) cell;
			Double value = numberCell.getValue();
			numberCell.getNumberFormat().setMinimumFractionDigits(9);
			returnValue = df.format(value);
			String tempValue = returnValue.toString().trim();
			if (tempValue.length() > 2
					&& tempValue.substring(tempValue.length() - 2,
							tempValue.length()).equals(".0")) {
				returnValue = String.valueOf(Double.valueOf(tempValue)
						.intValue());
			}
		} else if (cell.getType() == CellType.DATE) {
			DateCell dateCell = (DateCell) cell;
			Date date = dateCell.getDate();
			if (date != null) {
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				returnValue = bartDateFormat.format((Date) date);
			}
		} else {
			returnValue = cell.getContents();
		}
		return returnValue;
	}

	private List getRowData(Sheet sheet, int rowIndex, int colCount) {
		List list = new ArrayList();
		for (int i = 0; i < colCount; i++) {
			Object excelValue = getExcelCellValue(sheet, i, rowIndex);
			list.add(excelValue);
		}
		return list;
	}

	private void checkExcelHead(Sheet sheet) {
		// 每行的第一列为客户名称，忽略该列
		// 获取第一行，并检查表头是否相符
		List rowData = getRowData(sheet, 0, 13);
		// 若行数不符合
		if (rowData.size() < 13) {// 共12行
			throw new RuntimeException("表头行数不符合，请按照模板格式进行导入。");
		}
		// 若个列名不符合，抛异常
		for (int j = 1; j < 13; j++) {
			switch (j) {
			case 1:
				if (!getDataFromList(rowData, j).toString().contains("客户")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 2:
				if (!getDataFromList(rowData, j).toString().contains("转出申报日期")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 3:
				if (!getDataFromList(rowData, j).toString()
						.contains("转出企业内部编号")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 4:
				if (!getDataFromList(rowData, j).toString()
						.contains("电子口岸统一编码")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 5:
				if (!getDataFromList(rowData, j).toString().contains("申请表编号")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 6:
				if (!getDataFromList(rowData, j).toString().contains("转出手册编号")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 7:
				if (!getDataFromList(rowData, j).toString()
						.contains("转出申报企业代码")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 8:
				if (!getDataFromList(rowData, j).toString().contains("转入地")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 9:
				if (!getDataFromList(rowData, j).toString().contains("序号")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 10:
				if (!getDataFromList(rowData, j).toString().contains("商品项号")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 11:
				if (!getDataFromList(rowData, j).toString().contains("申报数量")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			case 12:
				if (!getDataFromList(rowData, j).toString().contains("备注")) {
					throw new RuntimeException("表头第" + j
							+ "列的列名不符，请按照模板格式进行导入。");
				}
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 检查某单元格是否为空
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param value
	 */
	private void checkNull(int rowNum, int colNum, Object value) {
		if (value == null || value.toString().trim().equals("")) {
			throw new RuntimeException("第" + (rowNum + 1) + "行" + "第"
					+ (colNum + 1) + "列数据不能为空，请检查。");
		}
	}

	/**
	 * 检查某单元格是否为空返回 boolean
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param value
	 */
	private boolean checkisNull(List<Object[]> list, String value, String coulmn) {
		if (value == null || "".equals(value)) {
			String info = value + "数据不能为空！";
			list.add(new String[] { coulmn, info });
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param list
	 * @param index
	 * @return
	 */
	private Object getDataFromList(List list, int index) {
		if (index >= list.size()) {
			return null;
		}
		return list.get(index);
	}

	/**
	 * 判断是否已经存在 KEY=申请表编号+电子口岸统一编号 的关封申请表数据
	 * 
	 * @param excelFileContent
	 * @param ioFlag
	 * @return
	 */
	public List<FptAppHead> importFptAppisExists(byte[] excelFileContent,
			String ioFlag) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				excelFileContent);
		Workbook workbook = null;
		List<FptAppHead> list = new ArrayList<FptAppHead>();
		WorkbookSettings wbs = new WorkbookSettings();
		try {
			workbook = Workbook.getWorkbook(byteArrayInputStream, wbs);
			Sheet sheet = workbook.getSheet(0);
			int totalCount = sheet.getRows();
			// 申请表编号 appNo
			String appNo = (String) getExcelCellValue(sheet, 2, 2);

			// 电子口岸统一编号 seqNo
			String seqNo = (String) getExcelCellValue(sheet, 6, 2);
			list = this.fptManageDao.importFptAppisExists(appNo, seqNo, ioFlag);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param excelFileContent
	 */
	public List<Object[]> importFptApp(byte[] excelFileContent, String taskId,
			String ioFlag, List<FptAppHead> listExists ,Date endDate) {
		// 获得最大逻辑行号，设置进度info
		StringBuilder err = new StringBuilder();
		List<Object[]> errList = new ArrayList<Object[]>();
		ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
				taskId);
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setEncoding("GBK");
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				excelFileContent);
		Workbook workbook = null;
		try {

			workbook = Workbook.getWorkbook(byteArrayInputStream, wbs);
			Sheet sheet = workbook.getSheet(0);
			int totalCount = sheet.getRows();

			if (info != null) {
				info.setBeginTime(System.currentTimeMillis());
				info.setTotalNum(totalCount);
				info.setCurrentNum(0);
				info.setMethodName("系统正在导入文件资料");
			}

			FptParameterSet parameterSet = this.fptManageDao
					.findFptParameterSet();
			Company com = this.dzscDao.findCompany();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
			Map appHeadMapBySeqNo = new HashMap();// 以统一编号缓存表头
			Map appHeadMapByAppNo = new HashMap();// 以申请表编号缓存表头
			Map<String, FptAppItem> itemMap = new HashMap();// 缓存转入
			Map<String, FptAppItem> outItemMap = new HashMap();// 缓存转出
			if (!com.getName().equals(
					((String) getExcelCellValue(sheet, 1, 4)).trim())) {
				if (FptInOutFlag.OUT.equals(ioFlag)) {
					errList.add(new String[] {
							"提示",
							((String) getExcelCellValue(sheet, 1, 4)).trim()
									+ "，不是当前公司名称，请核对！" });
					return errList;
				}
			} else if (!com.getName().equals(
					((String) getExcelCellValue(sheet, 7, 4)).trim())) {
				if (FptInOutFlag.IN.equals(ioFlag)) {
					errList.add(new String[] {
							"提示",
							((String) getExcelCellValue(sheet, 7, 4)).trim()
									+ "，不是当前公司名称，请核对！" });
					return errList;
				}
			}

			if (listExists.isEmpty())
				listExists.add(new FptAppHead());
			for (FptAppHead fptAppHead : listExists) {
				FptAppHead head = fptAppHead;

				ScmCoc outScmCoc = null; // 客户
				ScmCoc inScmCoc = null; // 供应商
				Brief outBrief = null;
				Brief inBrief = null;
				// FptAppHead head = new FptAppHead();
				// 第3 行数据 申请表编号【3C】 电子口岸统一编号【3G】
				// 申请表编号 appNo
				String appNo = (String) getExcelCellValue(sheet, 2, 2);
				checkisNull(errList, appNo, "申请表编号");
				refSetValue(head, "setAppNo", appNo);

				// 电子口岸统一编号 seqNo
				String seqNo = (String) getExcelCellValue(sheet, 6, 2);
				checkisNull(errList, seqNo, "电子口岸统一编号");
				refSetValue(head, "setSeqNo", seqNo);
				/**
				 * 这里在导入之前已经判断过了，所以暂时注释 //获得当前表头，查看数据库中是否存在，存在则查看缓存中是否存在，不存在则抛异常
				 * FptAppHead existedHead =
				 * this.fptManageDao.findFptAppHeadBySeqNo(seqNo);//根据统一编号查询表头
				 * if (existedHead != null && (appHeadMapBySeqNo.get(seqNo) ==
				 * null && appHeadMapByAppNo.get(appNo) == null)) {
				 * err.append("电子口岸统一编号为：【" + seqNo + "】的申请表已经存在于系统中，请检查。\n");
				 * errList.add(new String[]{"电子口岸统一编号","已经存在于系统中，请检查。"}); }
				 * FptAppHead fptAppHead =
				 * fptManageDao.findAllFptAppHeadByAppNo(appNo);//根据统一编号查询表头 if
				 * (fptAppHead != null && (appHeadMapBySeqNo.get(seqNo) == null
				 * && appHeadMapByAppNo.get(appNo) == null)) {
				 * err.append("申请表编号为：【" + appNo + "】的申请表已经存在于系统中，请检查。\n");
				 * errList.add(new String[]{"申请表编号","申请表已经存在于系统中"}); }
				 **/
				// 转出手册/账册编号
				String emsNo = null;
				emsNo = (String) getExcelCellValue(sheet, 9, 11);
				checkisNull(errList, emsNo, "转出手册/账册编号");
				refSetValue(head, "setEmsNo", emsNo);
				// 转入手册/账册编号
				String inEmsNo = null;
				inEmsNo = (String) getExcelCellValue(sheet, 10, totalCount - 2);
				checkisNull(errList, inEmsNo, "转入手册/账册编号");
				refSetValue(head, "setInEmsNo", inEmsNo);
				// 判断转出手册
				Object contract = null;
				if (ProjectType.BCUS == parameterSet.getProjectType()
						.intValue()) {
					List list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
					if (!list.isEmpty()) {
						for (int j = 0; j < list.size(); j++) {
							contract = list.get(j);
							if (contract != null)
								refSetValue(head, "setEmsNo",
										((EmsHeadH2k) contract).getEmsNo());
						}
					}
				} else {
					if (FptInOutFlag.OUT.equals(ioFlag)) {
						contract = this.contractDao
								.findAllContractByEmsNo(emsNo);// 检查是否存在于系统中
						if (contract != null) {
							refSetValue(head, "setEmsNo",
									((Contract) contract).getEmsNo());
						}
					} else if (FptInOutFlag.IN.equals(ioFlag)) {
						contract = this.contractDao
								.findAllContractByEmsNo(inEmsNo);// 检查是否存在于系统中
						if (contract != null) {
							refSetValue(head, "setInEmsNo",
									((Contract) contract).getEmsNo());
						}
					}
				}

				if (contract == null) {
					if (ProjectType.BCUS == parameterSet.getProjectType()
							.intValue()) {
						errList.add(new String[] {
								"手册/账册号",
								FptInOutFlag.OUT.equals(ioFlag) ? emsNo
										: inEmsNo + "不存在与系统中，请检查。" });
					} else {
						errList.add(new String[] {
								"手册/账册号",
								FptInOutFlag.OUT.equals(ioFlag) ? emsNo
										: inEmsNo + "不存在与系统中，请检查。" });
					}
				}

				// 客户
				String scmCocStrIN = null;
				scmCocStrIN = (String) getExcelCellValue(sheet, 7, 4);
				checkisNull(errList, scmCocStrIN, "转入企业");
				List<ScmCoc> listCocsIN = bcsImpExpRequestDao
						.findScmCocByNameAndType(scmCocStrIN, false, true);
				inScmCoc = listCocsIN.isEmpty() ? null : listCocsIN.get(0);

				// 判断客户存在
				if (inScmCoc == null) {
					errList.add(new String[] { "转入企业",
							scmCocStrIN + "，在物流基础资料--工厂通用代码---客户供应商不存在，请核对。" });
				} else {
					inBrief = inScmCoc.getBrief();
					if (inBrief == null) {
						errList.add(new String[] {
								"转入企业",
								scmCocStrIN
										+ "，在物流基础资料--工厂通用代码---客户供应商不存在，请核对。" });
					} else {
						// 转入企业
						refSetValue(head, "setInTradeName", inBrief.getName());
						// 转入企业代码
						refSetValue(head, "setInTradeCode", inBrief.getCode());
					}
				}

				// 供应商
				String scmCocStrOUT = null;
				scmCocStrOUT = (String) getExcelCellValue(sheet, 1, 4);
				checkisNull(errList, scmCocStrOUT, "转出企业");
				List<ScmCoc> listCocsOUT = bcsImpExpRequestDao
						.findScmCocByNameAndType(scmCocStrOUT, true, false);
				outScmCoc = listCocsOUT.isEmpty() ? null : listCocsOUT.get(0);

				// 判断供应商存在
				if (outScmCoc == null) {
					errList.add(new String[] {
							"转出企业",
							"转出企业：" + scmCocStrOUT
									+ "，在物流基础资料--工厂通用代码---客户供应商不存在，请核对。" });
				} else {
					outBrief = outScmCoc.getBrief();
					if (outBrief == null) {
						errList.add(new String[] {
								"转出企业",
								"转出企业：" + scmCocStrOUT
										+ "，在物流基础资料--工厂通用代码---客户供应商不存在，请核对。" });
					} else {
						// 转出企业
						refSetValue(head, "setOutTradeName", outBrief.getName());
						// 转出企业代码
						refSetValue(head, "setOutTradeCode", outBrief.getCode());
					}
				}

				// ScmCoc
				if (FptInOutFlag.IN.equals(ioFlag)) {
					refSetValue(head, "setScmCoc", outScmCoc);
				}

				if (FptInOutFlag.OUT.equals(ioFlag)) {
					refSetValue(head, "setScmCoc", inScmCoc);
				}
				// 第6-9行数据 转出地申报【6A-F】转入地申报【6G-L】
				/** 转出地申报【6A-F】 **/
				// 转出企业
				// String outTradeName = null;
				// outTradeName = (String)getExcelCellValue(sheet,1,4);
				// checkisNull(errList,outTradeName,"转出企业");
				// refSetValue(head,"setOutTradeName",outTradeName);

				// 主管海关
				String outCustoms = null;
				outCustoms = (String) getExcelCellValue(sheet, 3, 4);
				checkisNull(errList, outCustoms, "主管海关");
				List<Customs> listCustoms = bcsImpExpRequestDao
						.findCustomsList(outCustoms);
				Customs outCustomso = listCustoms.isEmpty() ? null
						: listCustoms.get(0);
				if (outCustomso == null && "".equals(outCustomso)) {
					errList.add(new String[] { "转出主管海关",
							outCustoms + "在海关基础资料--国家地区--关区代码不存" });
				} else {
					head.setOutCustoms(outCustomso);
				}
				// 转出地
				String outDistrict = null;
				outDistrict = (String) getExcelCellValue(sheet, 5, 4);
				checkisNull(errList, outDistrict, "转出地");
				List<District> listDistrict = bcsImpExpRequestDao
						.findDistrictList(outDistrict);
				District outDistricto = listDistrict.isEmpty() ? null
						: listDistrict.get(0);
				if (outDistricto == null || "".equals(outDistricto)) {
					errList.add(new String[] { "转出地",
							outDistrict + "在海关基础资料--国家地区--地区代码不存" });
				}
				refSetValue(head, "setOutDistrict", outDistricto);
				// 转出企业内部编号
				String outCopAppNo = null;
				outCopAppNo = (String) getExcelCellValue(sheet, 1, 5);
				checkisNull(errList, outCopAppNo, "转出企业内部编号");
				refSetValue(head, "setOutCopAppNo", outCopAppNo);
				// 转出批准证编号
				head.setOutLiceNo("人工审批");
				// 转出企业法人/联系电话
				String outCorp = null;
				outCorp = (String) getExcelCellValue(sheet, 1, 6);
				refSetValue(head, "setOutCorp", outCorp);
				// 转出申报企业****
				String outTradeName2 = null;
				outTradeName2 = (String) getExcelCellValue(sheet, 3, 6);
				checkisNull(errList, outTradeName2, "转出申报企业");
				refSetValue(head, "setOutTradeName2", outTradeName2);
				// 申报日期
				String outDate = null; // 2,8
				outDate = (String) getExcelCellValue(sheet, 1, 7);
				try {
					refSetValue(head, "setOutDate", (Date) sdf.parse(outDate));
				} catch (Exception e1) {
					errList.add(new String[] { "转出日期",
							outDate + "转出日期不符合日期格式，请检查。" });
				}
				// 审批日期 *- 实体无此字段

				// 申请表类型
				String appClass = null;
				appClass = AppClass.getCode((String) getExcelCellValue(sheet,
						1, 8));
				head.setAppClass("Z");
				checkisNull(errList, appClass, "申请表类型");
				// 企业合同号
				String contrNo = null;
				contrNo = (String) getExcelCellValue(sheet, 3, 8);
				refSetValue(head, "setContrNo", contrNo);

				// 转出申报企业组织机构名称
				refSetValue(head, "setOutAgentName",
						(String) getExcelCellValue(sheet, 3, 6));
				
				
				//转出申报企业9位组织机构代码
				if(FptInOutFlag.OUT.equals(ioFlag)){
					head.setOutAgentCode(com.getInAgentCode());
				}
				
				/**
				 * 暂时不用 if(FptInOutFlag.OUT.equals(ioFlag)){ //转出申报企业9位组织机构代码
				 * head.setOutAgentCode(com.getInAgentCode()); //转出申报企业组织机构名称
				 * head.setOutAgentName(com.getInAgentName()); //转出企业代码
				 * head.setOutTradeCode(com.getInTradeCode2()); //转出企业名称
				 * head.setOutTradeName(com.getInTradeName2()); //转入申报人/联系电话
				 * head.setInDecl(com.getInDecl());
				 * 
				 * }
				 **/

				/** 转入地申报【6G-L】 **/
				// 转入企业
				// String inTradeName = null;
				// inTradeName = (String)getExcelCellValue(sheet,7,4);
				// checkisNull(errList,inTradeName,"转入企业");
				// refSetValue(head,"setInTradeName",inTradeName);

				// 主管海关
				String inCustoms = null;
				inCustoms = (String) getExcelCellValue(sheet, 9, 4);
				checkisNull(errList, inCustoms, "转入主管海关");
				List<Customs> listCustomso = bcsImpExpRequestDao
						.findCustomsList(inCustoms);
				Customs inCustomso = listCustomso.isEmpty() ? null
						: listCustomso.get(0);
				if (inCustomso == null || "".equals(inCustomso)) {
					errList.add(new String[] { "转入主管海关",
							inCustoms + "在海关基础资料--国家地区--关区代码不存" });
				}
				refSetValue(head, "setInCustoms", inCustomso);
				// 转入地
				String inDistrict = null;
				inDistrict = (String) getExcelCellValue(sheet, 11, 4);
				checkisNull(errList, inDistrict, "转入地");
				List<District> listDistrictso = bcsImpExpRequestDao
						.findDistrictList(inDistrict);
				District inDistricto = listDistrictso.isEmpty() ? null
						: listDistrictso.get(0);
				if (inDistricto == null || "".equals(inDistricto)) {
					errList.add(new String[] { "转入地",
							outDistrict + "在海关基础资料--国家地区--地区代码不存" });
				}
				refSetValue(head, "setInDistrict", inDistricto);
				// 转入企业内部编号
				String inCopAppNo = null;
				inCopAppNo = (String) getExcelCellValue(sheet, 7, 5);
				checkisNull(errList, inCopAppNo, "转入企业内部编号");
				refSetValue(head, "setInCopAppNo", inCopAppNo);
				// 转入批准证编号
				String inLiceNo = null;
				inLiceNo = (String) getExcelCellValue(sheet, 9, 5);
				refSetValue(head, "setInLiceNo", inLiceNo);
				// 转入企业法人/联系电话
				String inCorp = null;
				inCorp = (String) getExcelCellValue(sheet, 7, 6);
				refSetValue(head, "setInCorp", inCorp);
				// 转入申报企业
				String inTradeName2 = null;
				inTradeName2 = (String) getExcelCellValue(sheet, 9, 6);
				checkisNull(errList, inTradeName2, "转入申报企业");
				refSetValue(head, "setInTradeName2", inTradeName2);

				// 转入申报企业代码
				// head.setInTradeCode2(com.getInTradeCode2());

				// 申报日期
				String inDate = null;
				inDate = (String) getExcelCellValue(sheet, 7, 7);
				try {
					refSetValue(head, "setInDate", (Date) sdf.parse(inDate));
				} catch (ParseException e) {
					errList.add(new String[] { "转出日期", "转出日期不符合日期格式，请检查。" });
				}

				// 审批日期 *-实体无此字段

				// 送货距离(公里)
				String sConveySpa = null;
				sConveySpa = (String) getExcelCellValue(sheet, 7, 8);
				if (sConveySpa != null && !"".equals(sConveySpa)) {
					Integer conveySpa = Integer.parseInt(sConveySpa);
					head.setConveySpa(conveySpa);
				}
				// 预计运输耗时(天)
				String sConveyDay = null;
				sConveyDay = (String) getExcelCellValue(sheet, 9, 8);
				if (sConveyDay != null && !"".equals(sConveyDay)) {
					Integer conveyDay = Integer.parseInt(sConveyDay);
					head.setConveyDay(conveyDay);
				}

				// 申报企业组织机构名称
				refSetValue(head, "setInAgentName",
						(String) getExcelCellValue(sheet, 9, 6));
				
				//申报企业9位组织机构代码
				if(FptInOutFlag.IN.equals(ioFlag)){
					head.setInAgentCode(com.getInAgentCode());
				}
				
				/**
				 * 暂时先不用 if(FptInOutFlag.IN.equals(ioFlag)){ //申报企业9位组织机构代码
				 * head.setInAgentCode(com.getInAgentCode()); //申报企业组织机构名称
				 * head.setInAgentName(com.getInAgentName()); //转入企业代码
				 * head.setInTradeCode(com.getInTradeCode2()); //* //转入企业名称
				 * head.setInTradeName(com.getInTradeName2()); //* //转入申报人/联系电话
				 * head.setInDecl(com.getInDecl()); }
				 */

				// 设置转出入标志
				head.setInOutFlag(ioFlag);
				head.setDelcareType(DelcareType.APPLICATION);
				head.setAclUser(CommonUtils.getAclUser());
				head.setProjectType(parameterSet.getProjectType());
				head.setDeclareState(DeclareState.PROCESS_EXE);
				head.setModifyMarkState(ModifyMarkState.UNCHANGE);

				appHeadMapBySeqNo.put(seqNo, head);
				appHeadMapByAppNo.put(appNo, head);

				// 第10行开始为进出数据 1.结转出口货物情况 2.结转进口货物情况 商品数据 从12行开始
				List items = null;
				List<FptAppItem> saveList = new ArrayList<FptAppItem>();
				// 先删除 表头下 料件
				int success = fptManageDao.deleteFptAppItem(head);

				String iioFlag = FptInOutFlag.OUT;
				for (int i = 11; i < totalCount; i++) {

					if ("转出地海关批注"
							.equals((String) getExcelCellValue(sheet, 0, i))) {
						break;
					}

					if ("结转进口货物情况".equals((String) getExcelCellValue(sheet, 0,
							i))) {
						iioFlag = FptInOutFlag.IN;
						i++;
						continue;
					}
					// 获得商品
					FptAppItem item = null;
					item = new FptAppItem();

					// item.setFptAppHead(head);
					refSetValue(item, "setFptAppHead", head);
					// 序号1
					Integer listNo = Integer
							.parseInt((String) getExcelCellValue(sheet, 0, i));
					refSetValue(item, "setListNo", listNo);
					// 商品项号2
					Integer trNo = Integer.parseInt((String) getExcelCellValue(
							sheet, 1, i));
					refSetValue(item, "setTrNo", trNo);

					// 验证物品是否存在
					Complex complex = null;
					Unit unit = null;
					if (ProjectType.BCUS == parameterSet.getProjectType()
							.intValue()) {
						// BCUS
						// 转出方
						if (iioFlag.equals(FptInOutFlag.OUT)) {
							items = fptManageDao
									.findBcusEms2kDetailByProcessExeForImport(
											head.getId(), emsNo, ioFlag, -1,
											-1, "seqNum", trNo, false);
							// EmsHeadH2kExg
							if (!items.isEmpty()) {
								EmsHeadH2kExg emsHeadH2kExg = (EmsHeadH2kExg) items
										.get(0);
								complex = emsHeadH2kExg.getComplex();
								unit = emsHeadH2kExg.getUnit();
							}
						} else if (!iioFlag.equals(FptInOutFlag.OUT)) {
							// 海关商品编码里面找
							List iitems = fptManageDao
									.findComplexList((String) getExcelCellValue(
											sheet, 2, i));
							if (!iitems.isEmpty()) {
								complex = (Complex) iitems.get(0);
								unit = complex.getFirstUnit() == null ? complex
										.getSecondUnit() : complex
										.getFirstUnit();
							}
						}

						// 转入方
						if (iioFlag.equals(FptInOutFlag.IN)) {
							items = fptManageDao
									.findBcusEms2kDetailByProcessExeForImport(
											head.getId(), inEmsNo, ioFlag, -1,
											-1, "seqNum", trNo, false);
							// EmsHeadH2kImg
							if (!items.isEmpty()) {
								EmsHeadH2kImg emsHeadH2kImg = (EmsHeadH2kImg) items
										.get(0);
								complex = emsHeadH2kImg.getComplex();
								unit = emsHeadH2kImg.getUnit();
							}
						} else if (!iioFlag.equals(FptInOutFlag.IN)) {
							// 海关商品编码里面找 totalCount -2
							List iitems = fptManageDao
									.findComplexList((String) getExcelCellValue(
											sheet, 2, i));
							if (!items.isEmpty()) {
								complex = (Complex) iitems.get(0);
								unit = complex.getFirstUnit() == null ? complex
										.getSecondUnit() : complex
										.getFirstUnit();
							}
						}

					} else {

						// BCS
						// 转出方
						if (iioFlag.equals(FptInOutFlag.OUT)) {
							items = fptManageDao
									.findBcsContractDetailByProcessExeForImport(
											head.getId(), emsNo, iioFlag, -1,
											-1, "seqNum", trNo, false);
							// ContractExg
							if (!items.isEmpty()) {
								ContractExg ContractExg = (ContractExg) items
										.get(0);
								complex = ContractExg.getComplex();
								unit = ContractExg.getUnit();
							}
						} else if (!iioFlag.equals(FptInOutFlag.OUT)) {
							// 海关商品编码里面找
							List iitems = fptManageDao
									.findComplexList((String) getExcelCellValue(
											sheet, 2, i));
							if (!iitems.isEmpty()) {
								complex = (Complex) iitems.get(0);
								unit = complex.getFirstUnit() == null ? complex
										.getSecondUnit() : complex
										.getFirstUnit();
							}
						}

						// 转入方
						if (iioFlag.equals(FptInOutFlag.IN)) {
							items = fptManageDao
									.findBcsContractDetailByProcessExeForImport(
											head.getId(), inEmsNo, iioFlag, -1,
											-1, "seqNum", trNo, false);
							// ContractImg
							if (!items.isEmpty()) {
								ContractImg contractImg = (ContractImg) items
										.get(0);
								complex = contractImg.getComplex();
								unit = contractImg.getUnit();
							}
						} else if (!iioFlag.equals(FptInOutFlag.IN)) {
							// 海关商品编码里面找
							List iitems = fptManageDao
									.findComplexList((String) getExcelCellValue(
											sheet, 2, i));
							if (!iitems.isEmpty()) {
								complex = (Complex) iitems.get(0);
								unit = complex.getFirstUnit() == null ? complex
										.getSecondUnit() : complex
										.getFirstUnit();
							}
						}

					}
					// 导入方只验证导入方的数据
					if (ioFlag.equals(iioFlag)) {
						if (items == null || items.size() == 0) {
							errList.add(new String[] {
									"第" + (i + 1) + "行 商品项号不存在",
									"商品项号"
											+ trNo
											+ "不存在 "
											+ (iioFlag.equals(FptInOutFlag.IN) ? "转入手册号："
													: "转出手册号：" + emsNo)
											+ " 中下存在，请检查。" });
						}
					}

					// 商品编码3
					refSetValue(item, "setCodeTs", complex);
					// 商品名称4
					String name = null;
					name = (String) getExcelCellValue(sheet, 3, i);
					refSetValue(item, "setName", name);
					// 规格型号5
					String spec = null;
					spec = (String) getExcelCellValue(sheet, 4, i);
					refSetValue(item, "setSpec", spec);
					// 计量单位6
					// String unit = (String)getExcelCellValue(sheet,5,i);
					Unit unit2 = fptManageDao.findUnitList(
							(String) getExcelCellValue(sheet, 5, i)).get(0);
					// item.setUnit(unit2);
					refSetValue(item, "setUnit", unit2);
					// 数量7
					String qtyString = null;
					qtyString = (String) getExcelCellValue(sheet, 6, i);
					Double qty = Double.parseDouble(qtyString);
					refSetValue(item, "setQty", qty);
					// 法定单位8
					// String unit1 = (String)getExcelCellValue(sheet,7,i);
					Unit unit3 = fptManageDao.findUnitList(
							(String) getExcelCellValue(sheet, 7, i)).get(0);
					// item.setUnit1(unit3);
					refSetValue(item, "setUnit1", unit3);
					// 法定数量9
					String qtyString1 = null;
					qtyString1 = (String) getExcelCellValue(sheet, 8, i);
					Double qty1 = Double.parseDouble(qtyString1);
					refSetValue(item, "setQty1", qty1);
					/** out - 转出手册号 in - 转出序号10 **/
					if (FptInOutFlag.OUT.equals(iioFlag)) {
						// 转出手册号
						String itemInEmsNo = null;
						itemInEmsNo = (String) getExcelCellValue(sheet, 9, i);
						refSetValue(item, "setInEmsNo", itemInEmsNo);
						item.setInOutFlag(FptInOutFlag.OUT);
					} else {
						// 转出序号
						String ioNO = (String) getExcelCellValue(sheet, 9, i);
						Integer itemInOutNo = Integer.parseInt(ioNO);
						refSetValue(item, "setInOutNo", itemInOutNo);
						// 转入手册号11 实体无此字段
						inEmsNo = (String) getExcelCellValue(sheet, 10, i);
						refSetValue(item, "setInEmsNo", inEmsNo);
						item.setInOutFlag(FptInOutFlag.IN);
					}
					item.setModifyMarkState(ModifyMarkState.UNCHANGE);
					saveList.add(item);
				}
				
				//有效日，从导入的窗口传入hyq
				head.setEndDate(endDate);

				// 保存
				if (errList != null && !errList.isEmpty()) {
					return errList;
					/*
					 * 以下保存代码 测试用 int x = 0;
					 * this.fptManageDao.saveFptAppHead(head); for (FptAppItem
					 * fptAppItem : saveList) {
					 * this.fptManageDao.saveFptAppItem(fptAppItem);
					 * info.setCurrentNum(x++); } 以上保存代码 测试用
					 */
				} else {
					int n = 0;
					this.fptManageDao.saveFptAppHead(head);
					for (FptAppItem fptAppItem : saveList) {
						this.fptManageDao.saveFptAppItem(fptAppItem);
						info.setCurrentNum(n++);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			workbook.close();
			try {
				byteArrayInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// return Collections.EMPTY_LIST; //测试的时候用
		return errList;
	}

	/**
	 * 反射设置属性值表体
	 * 
	 * @param fptAppHead
	 * @param mathod
	 * @param value
	 */
	private <T extends Object> void refSetValue(T fptAppHeadOrItem,
			String methodName, Object value) {
		if (value == null)
			return;
		Class<T> clazz = (Class<T>) fptAppHeadOrItem.getClass();
		try {
			Method method = clazz.getMethod(methodName, value.getClass());
			method.setAccessible(true);
			method.invoke(fptAppHeadOrItem, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param excelFileContent
	 * @param taskId
	 */
	public void importFptApp(byte[] excelFileContent, String taskId) {
		// TODO Auto-generated method stub
		try {
			// 获得最大逻辑行号，设置进度info
			ProgressInfo info = ProcExeProgress.getInstance().getProgressInfo(
					taskId);
			WorkbookSettings wbs = new WorkbookSettings();
			wbs.setEncoding("GBK");
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					excelFileContent);
			Workbook workbook = Workbook.getWorkbook(byteArrayInputStream, wbs);
			Sheet sheet = workbook.getSheet(0);
			int totalCount = sheet.getRows();
			if (info != null) {
				info.setBeginTime(System.currentTimeMillis());
				info.setTotalNum(totalCount);
				info.setCurrentNum(0);
				info.setMethodName("系统正在导入文件资料");
			}

			// this.checkExcelHead(sheet);//检查excel列名 由于格式变化 不做检查
			FptParameterSet parameterSet = this.fptManageDao
					.findFptParameterSet();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
			Map appHeadMapBySeqNo = new HashMap();// 以统一编号缓存表头
			Map appHeadMapByAppNo = new HashMap();// 以申请表编号缓存表头
			Map itemAmoutAndListNoMap = new HashMap();// 缓存数量
			for (int i = 1; i < totalCount; i++) {// 根据最大行号逐行取出来，每行进行对应逻辑检查，检查不通过抛出appException
				List rowData = getRowData(sheet, i, 13);// 每行的第一列为客户名称，忽略该列
				if (rowData == null) {
					continue;// 忽略空行
				}

				FptAppHead head = new FptAppHead();
				// 前8行获得表头信息，并逻辑检查
				String customCode = (String) getDataFromList(rowData, 1);
				checkNull(i, 1, customCode);// 非空检查
				// 检查当前客户是否存在
				ScmCoc scmCoc = bcsImpExpRequestDao
						.findScmCocByCode(customCode);
				if (scmCoc == null) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 2
							+ "列,该客户信息不存在与系统中，请检查。");
				}
				head.setScmCoc(scmCoc);
				head.setInTradeCode(scmCoc.getCode());
				head.setInTradeName(scmCoc.getName());

				Object outDate = getDataFromList(rowData, 2);
				checkNull(i, 2, outDate);// 非空检查
				try {
					outDate = sdf.parse(outDate.toString());
				} catch (Exception e) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 3
							+ "列,转出日期不符合日期格式，请检查。");
				}
				head.setOutDate((Date) outDate);

				String outCopAppNo = (String) getDataFromList(rowData, 3);
				checkNull(i, 3, outCopAppNo);// 非空检查
				head.setOutCopAppNo(outCopAppNo);

				String seqNo = getDataFromList(rowData, 4).toString();
				checkNull(i, 4, seqNo);// 非空检查
				// 若少于18位在前面补0
				int length = 18 - seqNo.length();
				if (length > 0) {// 这一方式下字符串是否数字都可以补0
					seqNo = String.format("%0" + length + "d", 0) + seqNo;
				}
				head.setSeqNo(seqNo);

				String appNo = getDataFromList(rowData, 5).toString();
				checkNull(i, 5, seqNo);// 非空检查
				head.setAppNo(appNo);

				String emsNo = getDataFromList(rowData, 6).toString();
				checkNull(i, 6, emsNo);// 非空检查
				Object contract = null;
				if (ProjectType.BCUS == parameterSet.getProjectType()
						.intValue()) {
					List list = this.emsEdiTrDao.findEmsHeadH2kInExecuting();
					for (int j = 0; j < list.size(); j++) {
						contract = list.get(j);
						if (contract != null)
							head.setEmsNo(((EmsHeadH2k) contract).getEmsNo());
					}
				} else {
					contract = this.contractDao.findAllContractByEmsNo(emsNo);// 检查是否存在于系统中
					if (contract != null)
						head.setEmsNo(((Contract) contract).getEmsNo());
				}
				if (contract == null) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 7
							+ "列,该手册/账册不存在与系统中，请检查。");
				}
				head.setEmsNo(emsNo);
				String outTradeCode2 = getDataFromList(rowData, 7).toString();
				checkNull(i, 7, outTradeCode2);// 非空检查
				List agentCorps = baseCodeDao.getBrief("code", outTradeCode2);
				if (agentCorps == null || agentCorps.size() == 0) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 8
							+ "列,该申报企业不存在与系统中，请检查。");
				}
				head.setOutTradeCode2(outTradeCode2);
				head.setOutTradeName2(((Brief) agentCorps.get(0)).getName());

				String inDistrictCode = getDataFromList(rowData, 8).toString();
				checkNull(i, 8, inDistrictCode);// 非空检查
				List inDistrictList = countryCodeDao.findDistrict("code",
						inDistrictCode);// 检查转入地是否存在
				if (inDistrictList.size() == 0) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 9
							+ "列,该转入地不存在与系统中，请检查。");
				}
				head.setInDistrict((District) inDistrictList.get(0));

				// 获得当前表头，查看数据库中是否存在，存在则查看缓存中是否存在，不存在则抛异常
				FptAppHead existedHead = this.fptManageDao
						.findFptAppHeadBySeqNo(seqNo);// 根据统一编号查询表头
				if (existedHead != null
						&& (appHeadMapBySeqNo.get(seqNo) == null && appHeadMapByAppNo
								.get(appNo) == null)) {
					throw new RuntimeException("第" + (i + 1) + "行,电子口岸统一编号为：【"
							+ seqNo + "】的申请表已经存在于系统中，请检查。");
				}
				FptAppHead fptAppHead = fptManageDao
						.findAllFptAppHeadByAppNo(appNo);// 根据统一编号查询表头
				if (fptAppHead != null
						&& (appHeadMapBySeqNo.get(seqNo) == null && appHeadMapByAppNo
								.get(appNo) == null)) {
					throw new RuntimeException("第" + (i + 1) + "行,申请表编号为：【"
							+ appNo + "】的申请表已经存在于系统中，请检查。");
				}
				Company com = this.dzscDao.findCompany();
				// 查看缓存中是否存在，若存在则对比是否相同（key = 统一编号 or 申请表标号），不相同抛异常
				if (appHeadMapBySeqNo.get(seqNo) != null
						|| appHeadMapByAppNo.get(appNo) != null) {// 检查导入的8个栏位是否一致
					FptAppHead oldHead = (FptAppHead) (appHeadMapBySeqNo
							.get(seqNo) == null ? appHeadMapByAppNo.get(appNo)
							: appHeadMapBySeqNo.get(seqNo));
					boolean isSame = true;
					isSame = head.getScmCoc().getCode()
							.equals(oldHead.getScmCoc().getCode()) ? isSame
							: false;
					isSame = head.getOutDate().equals(oldHead.getOutDate()) ? isSame
							: false;
					isSame = head.getOutCopAppNo().equals(
							oldHead.getOutCopAppNo()) ? isSame : false;
					isSame = head.getSeqNo().equals(oldHead.getSeqNo()) ? isSame
							: false;
					isSame = head.getAppNo().equals(oldHead.getAppNo()) ? isSame
							: false;
					isSame = head.getEmsNo().equals(oldHead.getEmsNo()) ? isSame
							: false;
					isSame = head.getOutTradeCode2().equals(
							oldHead.getOutTradeCode2()) ? isSame : false;
					isSame = head.getInDistrict().getCode()
							.equals(oldHead.getInDistrict().getCode()) ? isSame
							: false;
					if (!isSame) {
						throw new RuntimeException(
								"第"
										+ (i + 1)
										+ "行,申请表信息与Excel文件中相同申请表编号或相同统一编号的另一申请表信息不相同，请检查。");
					}
					head = oldHead;
				} else {// 新增表头记录，带出默认栏位
					// 默认设置为正在执行，修改状态为未修改
					head.setDeclareState(DeclareState.PROCESS_EXE);
					head.setModifyMarkState(ModifyMarkState.UNCHANGE);
					StringBuffer stringBuffer = new StringBuffer();
					if (com.getCode() == null || "".equals(com.getCode())) {
						stringBuffer.append("公司的加工单位编码不能为空\r\n");
					}
					if (com.getName() == null || "".equals(com.getName())) {
						stringBuffer.append("公司的加工单位名称不能为空\r\n");
					}
					if (parameterSet.getProjectType() == null) {
						stringBuffer.append("请到转厂管理参数设置中,先设置项目类型\r\n");
					}
					if (!stringBuffer.toString().trim().equals("")) {
						throw new RuntimeException(stringBuffer.toString());
					}
					/**
					 * 转出地
					 */
					District outDistrict = com.getOutDistrict();
					/**
					 * 申报企业9位组织机构代码 非空 转出方
					 */
					String inAgentCode = com.getInAgentCode();
					/**
					 * 申报企业组织机构名称 非空
					 */
					String inAgentName = com.getInAgentName();
					/**
					 * 转入企业法人/联系电话
					 */
					String inCorp = com.getInCorp();
					/**
					 * 转入申报人/联系电话
					 */
					String inDecl = com.getInDecl();
					head.setCompany(com);
					head.setOutTradeCode(com.getCode());
					head.setOutTradeName(com.getName());
					head.setAppClass(AppClass.Z);
					head.setOutLiceNo("人工审批");
					head.setInOutFlag(FptInOutFlag.OUT);
					head.setOutCustoms(com.getMasterCustoms());
					head.setOutDistrict(outDistrict);
					head.setOutAgentCode(inAgentCode);
					head.setOutAgentName(inAgentName);
					head.setOutCorp(inCorp);
					head.setOutDecl(inDecl);
					// head.setOutCopAppNo(this.exportMessageLogic.getNewCopEntNoFptBill(
					// "FptAppHead", "outCopAppNo", "G",
					// FptBusinessType.FPT_APP,
					// FptInOutFlag.OUT));

					head.setDelcareType(DelcareType.APPLICATION);
					head.setAclUser(CommonUtils.getAclUser());
					head.setProjectType(parameterSet.getProjectType());
					this.fptManageDao.saveFptAppHead(head); // 保存当前表头
					appHeadMapBySeqNo.put(seqNo, head);
					appHeadMapByAppNo.put(appNo, head);
				}

				// 9-11行为表体的值
				FptAppItem item = new FptAppItem();

				Integer listNo = null;
				Object tempListNo = getDataFromList(rowData, 9);
				checkNull(i, 9, tempListNo);// 非空检查
				try {
					listNo = Integer.valueOf(tempListNo.toString());
				} catch (Exception e) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 10
							+ "列,该序号无法转换为整形数据，请检查。");
				}
				item.setListNo(listNo);

				Integer trNo = null;
				Object tempTrNo = getDataFromList(rowData, 10);
				checkNull(i, 10, tempTrNo);// 非空检查
				try {
					trNo = Integer.valueOf(tempTrNo.toString());
				} catch (Exception e) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 11
							+ "列,该商品项号无法转换为整形数据，请检查。");
				}

				List items = null;
				if (ProjectType.BCUS == parameterSet.getProjectType()
						.intValue()) {
					items = fptManageDao.findBcusEms2kDetailByProcessExe(
							head.getId(), emsNo, FptInOutFlag.OUT, -1, -1,
							"seqNum", trNo, false);
				} else {
					items = fptManageDao.findBcsContractDetailByProcessExe(
							head.getId(), emsNo, FptInOutFlag.OUT, -1, -1,
							"seqNum", trNo, false);
				}

				if (items == null || items.size() == 0) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 11
							+ "列,该商品项号不存在与对应手册/账册中或同一关封下存在相同商品项号的成品，请检查。");
				}
				item.setTrNo(trNo);

				Double qty = null;
				Object tempQty = getDataFromList(rowData, 11);
				checkNull(i, 11, tempQty);// 非空检查
				try {
					qty = Double.valueOf(tempQty.toString());
				} catch (Exception e) {
					throw new RuntimeException("第" + (i + 1) + "行" + "第" + 12
							+ "列,该序号无法转换为数字，请检查。");
				}
				if (ProjectType.BCS == parameterSet.getProjectType().intValue()) {
					// 检查是否超出备案数量
					ContractExg c = (ContractExg) items.get(0);
					Double maxAmout = c.getExportAmount();
					if (itemAmoutAndListNoMap.get(emsNo + "," + trNo + ","
							+ appNo) != null) {
						Object temp = itemAmoutAndListNoMap.get(emsNo + ","
								+ trNo + "," + appNo);
						Double curAmout = qty + Double.valueOf(temp + "");
						if (curAmout > maxAmout) {
							throw new AppException("第" + (i + 1) + "行" + "第"
									+ 12 + "列,关封号【" + appNo + "】，手册号【" + emsNo
									+ "】，成品序号为【" + trNo + "】的总成品数量超出备案数量请检查。");
						}
						itemAmoutAndListNoMap.put(emsNo + "," + trNo + ","
								+ appNo, curAmout);
					} else {
						Double curAmout = qty;
						if (curAmout > maxAmout) {
							throw new AppException("第" + (i + 1) + "行" + "第"
									+ 12 + "列,关封号【" + appNo + "】，手册号【" + emsNo
									+ "】，成品序号为【" + trNo + "】的总成品数量超出备案数量请检查。");
						}
						itemAmoutAndListNoMap.put(emsNo + "," + trNo + ","
								+ appNo, qty);// 以手册号，序号，关封号为key保存表体数量
					}
					item.setQty(qty);
					item.setQty1(qty);// 使得法定数量默认与申报数量一样
					// 增加料件
					Object note = getDataFromList(rowData, 12);
					item.setNote(note == null ? "" : note + "");
					item.setCompany(com);
					item.setTrNo(c.getSeqNum());
					item.setCodeTs(c.getComplex());
					item.setCurr(null);
					item.setName(c.getName());
					item.setSpec(c.getSpec());
					item.setUnit(c.getUnit());
					item.setUnit1(c.getComplex().getFirstUnit());
					item.setUnitPrice(c.getDeclarePrice());
					item.setInOutFlag(head.getInOutFlag());
					item.setFptAppHead(head);
					item.setModifyMarkState(ModifyMarkState.UNCHANGE);// 默认设置未修改
				} else {
					// 检查是否超出备案数量
					EmsHeadH2kExg c = (EmsHeadH2kExg) items.get(0);
					// 电子账册忽略余量是否超量的检查
					item.setQty(qty);
					item.setQty1(qty);// 使得法定数量默认与申报数量一样
					// 增加料件
					Object note = getDataFromList(rowData, 12);
					item.setNote(note == null ? "" : note + "");
					item.setCompany(com);
					item.setTrNo(c.getSeqNum());
					item.setCodeTs(c.getComplex());
					item.setCurr(null);
					item.setName(c.getName());
					item.setSpec(c.getSpec());
					item.setUnit(c.getUnit());
					item.setUnit1(c.getComplex().getFirstUnit());
					item.setUnitPrice(c.getDeclarePrice());
					item.setInOutFlag(head.getInOutFlag());
					item.setFptAppHead(head);
					item.setModifyMarkState(ModifyMarkState.UNCHANGE);// 默认设置未修改

				}
				this.fptManageDao.saveFptAppItem(item);
				info.setCurrentNum(i + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
		}
	}
	
	
	/**
     * 查询是否存在
     * @param seqNo 电子口岸统一编号
     * @param AppNo 申请表编号
     * @param fptInOutFlag
     * @return
     */
    public boolean findExistsSeqNoOrAppNo(String seqNo, String appNo, String fptInOutFlag){
    	List<Object[]> list = fptManageDao.findExistsSeqNoOrAppNo(seqNo, appNo, fptInOutFlag);
    	return (list.isEmpty()) ? true : false;
    }
    

}
