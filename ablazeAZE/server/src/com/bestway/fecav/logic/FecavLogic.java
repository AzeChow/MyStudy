package com.bestway.fecav.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contractstat.entity.CancelAfterVerification;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.FecavState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.fecav.dao.FecavDao;
import com.bestway.fecav.entity.BillOfExchange;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fecav.entity.FecavBillStrike;
import com.bestway.fecav.entity.FecavParameters;
import com.bestway.fecav.entity.ImpCustomsDeclaration;
import com.bestway.fecav.entity.StrikeBillOfExchange;
import com.bestway.fecav.entity.StrikeImpCustomsDeclaration;
import com.bestway.fecav.entity.TempBillOfExchangeForFecav;
import com.bestway.fecav.entity.TempCancelContractStat;
import com.bestway.fecav.entity.TempCancelSignInStat;
import com.bestway.fecav.entity.TempCustomsDeclarationInfoForFecav;
import com.bestway.fecav.entity.TempFecavBillUsedStat;

/**
 * 外汇核销logic checked by cjb 2009.9.9
 * 
 * @author Administrator
 * 
 */
public class FecavLogic {
	/**
	 * 外汇核销Dao
	 */
	private FecavDao fecavDao = null;

	/**
	 * 取得外汇核销Dao内容
	 * 
	 * @return 外汇核销Dao
	 */
	public FecavDao getFecavDao() {
		return fecavDao;
	}

	/**
	 * 设计外汇核销Dao内容
	 * 
	 * @param fecavDao
	 *            外汇核销Dao
	 */
	public void setFecavDao(FecavDao fecavDao) {
		this.fecavDao = fecavDao;
	}

	/**
	 * 删除进口报关单冲销信息
	 * 
	 * @param customsDeclarationId
	 *            报关单id
	 */
	public void deleteImpCustomsDeclaration(String customsDeclarationId) {
		this.fecavDao.deleteImpCustomsDeclaration(customsDeclarationId);
	}

	/**
	 * 判断是否是Integer形
	 * 
	 * @param s
	 *            字符型参数
	 * @return ture为真 false为否
	 */
	private boolean checkIsInteger(String s) {
		try {
			Integer.valueOf(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 根据核销单开始号码和份数取得核销单结束号码
	 * 
	 * @param beginNo
	 *            开始号码
	 * @param num
	 *            份数
	 * @return 核销单结束号码
	 */
	public String getEndFecavBillNo(String beginNo, int num) {
		if (beginNo != null && beginNo.trim().length() > 9) {
			throw new RuntimeException("核销单开始号不能大于9位,请检查输入");
		}
		List<FecavBill> list = this.fecavDao.findFecavBill();
		for (FecavBill element : list) {
			if (element.getCode().equals(beginNo)) {
				throw new RuntimeException("核销单开始号:" + beginNo + " 已存在,请检查输入");
			}
		}
		String numberPart = "";
		String prefix = "";
		for (int i = beginNo.length() - 1; i >= 0; i--) {
			String s = beginNo.substring(i, i + 1);
			// System.out.println(s);
			if (this.checkIsInteger(s)) {
				numberPart = s + numberPart;
			} else {
				prefix = beginNo.substring(0, i + 1);
				// System.out.println("prefix:"+prefix);0
				break;
			}
		}
		if (numberPart.length() <= 0) {
			throw new RuntimeException("此开始核销单号:" + beginNo + " 不是以数字结尾");
		}
		Integer bno = Integer.valueOf(numberPart);
		Integer nlen = numberPart.length();
		String code = prefix
				+ CommonUtils.convertIntToStringByLength(bno + num - 1, nlen);
		return code;
	}

	/**
	 * 生成核销单号
	 * 
	 * @param beginNo
	 *            核销单开始号
	 * @param num
	 *            核销单份数
	 * @return 核销单号
	 */
	private List generateFecavBillNo(String beginNo, int num) {
		List lsResult = new ArrayList();
		String numberPart = "";
		String prefix = "";
		for (int i = beginNo.length() - 1; i >= 0; i--) {
			String s = beginNo.substring(i, i + 1);
			// System.out.println(s);
			if (this.checkIsInteger(s)) {
				numberPart = s + numberPart;
			} else {
				prefix = beginNo.substring(0, i + 1);
				// System.out.println("prefix:"+prefix);
				break;
			}
		}
		if (numberPart.length() <= 0) {
			throw new RuntimeException("此开始核销单号:" + beginNo + " 不是以数字结尾");
		}
		if (num <= 0) {
			return lsResult;
		} else {
			for (int i = 0; i < num; i++) {
				Integer bno = Integer.valueOf(numberPart);
				Integer nlen = numberPart.length();
				String code = prefix
						+ CommonUtils.convertIntToStringByLength(bno + i, nlen);
				lsResult.add(code);
			}

		}
		return lsResult;
	}

	/**
	 * 批量外部领用外汇核销单据
	 * 
	 * @param beginNo
	 *            外部领用核销单开始号
	 * @param num
	 *            核销单份数
	 * @param outerObtain
	 *            外部核销单领用人
	 * @param outerObtainDate
	 *            领用日期
	 * @return 外部领用外汇核销单据
	 */
	public List batchOuterObtainFecavBill(String beginNo, int num,
			String outerObtain, Date outerObtainDate) {
		List lsResult = new ArrayList();
		List lsCode = generateFecavBillNo(beginNo, num);
		for (int i = 0; i < lsCode.size(); i++) {
			String code = lsCode.get(i).toString();
			if (this.fecavDao.findFecavBillCount(code) > 0) {
				continue;
			}
			FecavBill bill = new FecavBill();
			bill.setCompany(CommonUtils.getCompany());
			bill.setCode(code);
			bill.setOuterOperator(CommonUtils.getRequest().getUser()
					.getUserName());
			bill.setOuterOperatorDate(new Date());
			bill.setOuterObtain(outerObtain);
			bill.setOuterObtainDate(outerObtainDate);
			bill.setBillState(FecavState.OUTER_OBTAIN);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 删除外汇核销单
	 * 
	 * @param list
	 *            要批量删除的外汇核销单
	 */
	public void deleteFecavBill(List list) {
		this.fecavDao.deleteAll(list);
	}

	/**
	 * 批量内部领用外汇核销单
	 * 
	 * @param beginNo
	 *            内部领用核销单开始号
	 * @param num
	 *            核销单份数
	 * @param innerObtain
	 *            内部领用人
	 * @param innerObtainDate
	 *            内部领用日期
	 * @return 内部领用外汇核销单
	 */
	public List batchInnerObtainFecavBill(String beginNo, int num,
			String innerObtain, Date innerObtainDate) {
		List lsResult = new ArrayList();
		List list = generateFecavBillNo(beginNo, num);
		for (int i = 0; i < list.size(); i++) {
			String code = list.get(i).toString();
			this.fecavDao.findFecavBill(code, FecavState.OUTER_OBTAIN);
			FecavBill bill = this.fecavDao.findFecavBill(code,
					FecavState.OUTER_OBTAIN);// (FecavBill) list.get(i);
			if (bill == null) {
				continue;
			}
			bill.setInnerOperator(CommonUtils.getRequest().getUser()
					.getUserName());
			bill.setInnerOperatorDate(new Date());
			bill.setInnerObtain(innerObtain);
			bill.setInnerObtainDate(innerObtainDate);
			bill.setBillState(FecavState.INNER_OBTAIN);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}

		return lsResult;
	}

	/**
	 * 批量内部领用外汇核销单
	 * 
	 * @param beginNo
	 *            内部领用核销单开始号
	 * @param num
	 *            核销单份数
	 * @param innerObtain
	 *            内部领用人
	 * @param innerObtainDate
	 *            内部领用日期
	 * @return 内部领用外汇核销单
	 */
	public List batchInnerObtainFecavBill(List list, String innerObtain,
			Date innerObtainDate) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			if (bill == null) {
				continue;
			}
			bill.setInnerOperator(CommonUtils.getRequest().getUser()
					.getUserName());
			bill.setInnerOperatorDate(new Date());
			// bill.setInnerObtain(innerObtain);
			// bill.setInnerObtainDate(innerObtainDate);
			bill.setInnerObtain(CommonUtils.getRequest().getUser()
					.getUserName());
			bill.setInnerObtainDate(new Date());
			bill.setBillState(FecavState.INNER_OBTAIN);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}

		return lsResult;
	}

	/**
	 * 批量取消内部领用
	 * 
	 * @param list
	 *            要取消的内部领用
	 * @return 取消后的内部领用 状态改为外部领用
	 */
	public List batchUndoInnerObtainFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setInnerOperator(null);
			bill.setInnerOperatorDate(null);
			bill.setInnerObtain(null);
			bill.setInnerObtainDate(null);
			bill.setBillState(FecavState.OUTER_OBTAIN);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取得未与外汇核销单建立联系的报关单信息
	 * 
	 * @return 未与外汇核销单建立联系的报关单(联网监管 电子手册 纸质手册)信息
	 */
	public List findCustomsDeclarationInfoForFecav() {
		List lsResult = new ArrayList();
		combineInfoList(this.fecavDao.findCustomsDeclaration(ProjectType.BCUS),
				lsResult, ProjectType.BCUS);
		combineInfoList(this.fecavDao.findCustomsDeclaration(ProjectType.DZSC),
				lsResult, ProjectType.DZSC);
		combineInfoList(this.fecavDao.findCustomsDeclaration(ProjectType.BCS),
				lsResult, ProjectType.BCS);
		return lsResult;
	}

	/**
	 * 整合报关单信息
	 * 
	 * @param lsInfo
	 *            报关单信息
	 * @param lsResult
	 *            临时的报关单信息
	 * @param projectType
	 *            模块
	 */
	private void combineInfoList(List lsInfo, List lsResult, int projectType) {
		for (int i = 0; i < lsInfo.size(); i++) {
			TempCustomsDeclarationInfoForFecav temp = new TempCustomsDeclarationInfoForFecav();
			BaseCustomsDeclaration base = (BaseCustomsDeclaration) lsInfo
					.get(i);
			temp.setCustomsDeclarationId(base.getId());
			temp.setCustomsDeclarationCode(base.getCustomsDeclarationCode());
			temp.setExportDate(base.getImpExpDate());
			temp.setDeclareDate(base.getDeclarationDate());
			temp.setCurr(base.getCurrency());
			temp.setContractNo(base.getContract());
			temp.setEmsNo(base.getEmsHeadH2k());
			temp.setTotalPrice(this.fecavDao.findCustomsDeclarationMoney(
					projectType, base.getId()));
			temp.setProjectType(projectType);
			lsResult.add(temp);
		}
	}

	/**
	 * 取消和出口报关单的对应
	 * 
	 * @param list
	 *            外汇核销单信息
	 * @return 取消和出口报关单对应后的报关单信息
	 */
	public List undoParallelFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			fecavBill.setCustomsDeclarationId(null);
			fecavBill.setCustomsDeclarationCode(null);
			fecavBill.setExportDate(null);
			fecavBill.setDeclareDate(null);
			fecavBill.setContractNo(null);
			fecavBill.setEmsNo(null);
			fecavBill.setCurr(null);
			fecavBill.setTotalPrice(null);
			fecavBill.setIsPrintWhite(null);
			fecavBill.setIsPrintYellow(null);
			this.fecavDao.saveFecavBill(fecavBill);
			lsResult.add(fecavBill);
		}
		return lsResult;
	}

	/**
	 * 核销单使用
	 * 
	 * @param list
	 *            核销单
	 * @return 使用状态的核销单
	 */
	public List useFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setBillState(FecavState.USED);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单使用
	 * 
	 * @param list
	 *            核销单
	 * @return 取消使用的外部核销单 改状态为内部领用
	 */
	public List undoUseFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setBillState(FecavState.INNER_OBTAIN);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 核销单退税联签收
	 * 
	 * @param list
	 *            核销单
	 * @param man
	 *            退税联签收人
	 * @param date
	 *            退税联签收日期
	 * @return 核销单退税联签收内容
	 */
	public List debentureSignInFecavBill(List list, String man, Date date) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setBillState(FecavState.DEBENTURE_SIGN_IN);
			bill.setDebentureSignInMan(man);
			bill.setDebentureSignInDate(date);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单退税联签收
	 * 
	 * @param list
	 *            核销单
	 * @return 取消后的核销单退税联签收单据 该状态为使用
	 */
	public List undoDebentureSignInFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setBillState(FecavState.USED);
			bill.setDebentureSignInMan(null);
			bill.setDebentureSignInDate(null);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 核销单交单明细
	 * 
	 * @param list
	 *            外汇核销单
	 * @param man
	 *            核销单交单人
	 * @param date
	 *            核销单交单日期
	 * @return 核销单交单明细
	 */
	public List handBillFecavBill(List list, String man, Date date) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setBillState(FecavState.HAND_IN_BILL);
			bill.setHandInBillDate(date);
			bill.setHandInBillMan(man);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单交单明细
	 * 
	 * @param list
	 *            外汇核销单
	 * @return 取消的核销单交单 改其状态为退税签收
	 */
	public List undoHandBillFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setBillState(FecavState.DEBENTURE_SIGN_IN);
			bill.setHandInBillDate(null);
			bill.setHandInBillMan(null);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	// /**
	// * 核销单管制
	// *
	// * @param list
	// * @return
	// */
	// public List cavSignInFecavBill(List list) {
	// List lsResult = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	// FecavBill bill = (FecavBill) list.get(i);
	// bill.setBillState(FecavState.CONTROL);
	// this.fecavDao.saveFecavBill(bill);
	// lsResult.add(bill);
	// }
	// return lsResult;
	// }
	//
	// /**
	// * 取消核销单管制
	// *
	// * @param list
	// * @return
	// */
	// public List undoCavSignInFecavBill(List list) {
	// List lsResult = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	// FecavBill bill = (FecavBill) list.get(i);
	// bill.setBillState(FecavState.HAND_IN_BILL);
	// this.fecavDao.saveFecavBill(bill);
	// lsResult.add(bill);
	// }
	// return lsResult;
	// }
	/**
	 * 核销单核销
	 * 
	 * @param list
	 *            核销单冲销
	 * @param signInMan
	 *            核销人
	 * @param signInDate
	 *            核销日期
	 * @return 核销后的核销单
	 */
	public List cancelAfterVerificationFecavBill(List list, String signInMan,
			Date signInDate) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.CANCEL);
			bill.setCancelDate(signInDate);
			bill.setCancelMan(signInMan);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.CANCEL);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单核销
	 * 
	 * @param list
	 *            核销单冲销
	 * @return 取消核销单核销 改状态为冲销
	 */
	public List undoCancelAfterVerificationFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.STRIKE_BALANCE);
			bill.setCancelDate(null);
			bill.setCancelMan(null);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.STRIKE_BALANCE);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 核销单财务签收
	 * 
	 * @param list
	 *            核销单冲销
	 * @param signInMan
	 *            财务签收者
	 * @param signInDate
	 *            财务签收日期
	 * @return 财务签收的核销单
	 */
	public List financeSignInFecavBill(List list, String signInMan,
			Date signInDate) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.FINANCE_SIGN_IN);
			bill.setFinanceSignInDate(signInDate);
			bill.setFinanceSignInMan(signInMan);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.FINANCE_SIGN_IN);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单财务签收
	 * 
	 * @param list
	 *            核销单冲销
	 * @return 取消要签收的核销单 改状态为核销
	 */
	public List undoFinanceSignInFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.CANCEL);
			bill.setFinanceSignInDate(null);
			bill.setFinanceSignInMan(null);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.CANCEL);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 核销单关帐
	 * 
	 * @param list
	 *            外汇核销单冲销
	 * @param closeAccountMan
	 *            关帐人
	 * @param closeAccountDate
	 *            关帐日期
	 * @return 核销单关帐的单据
	 */
	public List closeAccountFecavBill(List list, String closeAccountMan,
			Date closeAccountDate) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.CLOSE_ACCOUNT);
			bill.setCloseAccountMan(closeAccountMan);
			bill.setCloseAccountDate(closeAccountDate);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.CLOSE_ACCOUNT);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单关帐
	 * 
	 * @param list
	 *            核销单冲销
	 * @return 取消要关帐的核销单 改状态为签收
	 */
	public List undoCloseAccountFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.FINANCE_SIGN_IN);
			bill.setCloseAccountMan(null);
			bill.setCloseAccountDate(null);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.FINANCE_SIGN_IN);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 核销单冲销
	 * 
	 * @param list
	 *            核销单冲销
	 * @param strikeMan
	 *            冲销者
	 * @param strikeDate
	 *            冲销期
	 * @return 核销单的冲销单据
	 */
	public List strikeBalanceFecavBill(List list, String strikeMan,
			Date strikeDate) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.STRIKE_BALANCE);
			bill.setStrikeDate(strikeDate);
			bill.setStrikeMan(strikeMan);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.STRIKE_BALANCE);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			this.fecavDao.saveFecavBillStrike(bill);
			List lsStrikeImp = this.fecavDao
					.findBrikeImpCustomsDeclaration(bill);
			for (int j = 0; j < lsStrikeImp.size(); j++) {
				StrikeImpCustomsDeclaration strikeImp = (StrikeImpCustomsDeclaration) lsStrikeImp
						.get(j);
				writeBackImpCustDeclaSignInNo(strikeImp);
			}
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单冲销
	 * 
	 * @param list
	 *            核销单冲销
	 * @return 取消核销单冲销 改状态为管制
	 */
	public List undoStrikeBalanceFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBillStrike bill = (FecavBillStrike) list.get(i);
			bill.setFecavState(FecavState.CONTROL);
			bill.setStrikeDate(null);
			bill.setStrikeMan(null);
			List lsFecavBill = this.fecavDao.findFecavBillByStrike(bill);
			for (int j = 0; j < lsFecavBill.size(); j++) {
				FecavBill fecavBill = (FecavBill) lsFecavBill.get(j);
				fecavBill.setBillState(FecavState.CONTROL);
				this.fecavDao.saveFecavBill(fecavBill);
			}
			List lsStrikeImp = this.fecavDao
					.findBrikeImpCustomsDeclaration(bill);
			for (int j = 0; j < lsStrikeImp.size(); j++) {
				StrikeImpCustomsDeclaration strikeImp = (StrikeImpCustomsDeclaration) lsStrikeImp
						.get(j);
				String customsDeclarationId = strikeImp
						.getCustomsDeclarationId();
				ImpCustomsDeclaration imp = this.fecavDao
						.findImpCustomsDeclaration(customsDeclarationId);
				if (imp != null) {
					String signInNo = imp.getSignInNo();
					if (signInNo != null) {
						signInNo = signInNo.replaceAll(bill.getSignInNo()
								.trim()
								+ ";", "");
						imp.setSignInNo(signInNo);
						this.fecavDao.saveImpCustomsDeclaration(imp);
					}
				}
			}
			this.fecavDao.saveFecavBillStrike(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 核销单作废
	 * 
	 * @param list
	 *            外汇核销单
	 * @param reason
	 *            作废原因
	 * @param date
	 *            作废日期
	 * @return 作废后的外汇核销单
	 */
	public List blankOutFecavBill(List list, String reason, Date date) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setIsBlankOut(true);
			bill.setBlankOutReason(reason);
			bill.setBlankOutDate(new Date());
			bill.setBlankOutMan(CommonUtils.getRequest().getUser()
					.getLoginName());
			bill.setCheckoutdate(date);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 取消核销单作废
	 * 
	 * @param list
	 *            核销单
	 * @return 取消作废的外汇核销单
	 */
	public List undoBlankOutFecavBill(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill bill = (FecavBill) list.get(i);
			bill.setIsBlankOut(false);
			bill.setBlankOutReason("");
			bill.setBlankOutDate(null);
			bill.setBlankOutMan(null);
			this.fecavDao.saveFecavBill(bill);
			lsResult.add(bill);
		}
		return lsResult;
	}

	/**
	 * 更新旧数据
	 * 
	 */
	public void deleteOldImpCustomsDeclaration() {
		this.fecavDao.deleteOldImpCustomsDeclaration();
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param lsImp
	 *            外汇核销单冲销
	 */
	public List<StrikeImpCustomsDeclaration> addStrikeImpCustomsDeclaration(
			FecavBillStrike fecavBillStrike, List<ImpCustomsDeclaration> lsImp) {
		List<StrikeImpCustomsDeclaration> lsResult = new ArrayList<StrikeImpCustomsDeclaration>();
		fecavBillStrike = this.fecavDao.findFecavBillStrikeById(fecavBillStrike
				.getId());
		if (fecavBillStrike == null) {
			throw new RuntimeException("此核销单签收单单头已被删除");
		}
		double remainStrikeImportMoney = CommonUtils
				.getDoubleExceptNull(fecavBillStrike
						.getRemainStrikeImportMoney());
		for (int i = 0; i < lsImp.size(); i++) {
			StrikeImpCustomsDeclaration strikeImp = new StrikeImpCustomsDeclaration();
			ImpCustomsDeclaration imp = (ImpCustomsDeclaration) lsImp.get(i);
			try {
				PropertyUtils.copyProperties(strikeImp, imp);
			} catch (Exception e) {
				e.printStackTrace();
			}
			strikeImp.setId(null);
			strikeImp.setFecavBillStrike(fecavBillStrike);
			double converUSDMoney = imp.getConverUSDMoney();
			remainStrikeImportMoney -= converUSDMoney;

			// if (remainStrikeImportMoney == 0.0) {
			// break;
			// } else

			if (remainStrikeImportMoney < 0) {
				converUSDMoney = (converUSDMoney + remainStrikeImportMoney);
				if (converUSDMoney <= 0) {
					break;
				}
				double rate = this.findExchangeRate(imp.getCurr(),
						fecavBillStrike.getCurr(), imp.getDeclareDate());
				double strikeMoney = CommonUtils.getDoubleByDigit(
						converUSDMoney / rate, 4);
				strikeImp.setStrikeMoney(strikeMoney);
				strikeImp.setRemainMoney(0.0);
				strikeImp.setConverUSDMoney(converUSDMoney);
			} else {
				strikeImp.setStrikeMoney(imp.getRemainMoney());
				strikeImp.setRemainMoney(0.0);
				strikeImp.setConverUSDMoney(converUSDMoney);
			}
			this.fecavDao.saveBrikeImpCustomsDeclaration(strikeImp);
			writeBackImpCustDeclaRemainMoney(strikeImp);
			//
			// in lousheng
			//
			lsResult.add(strikeImp);

		}
		writeBackFecavBillBrikeMoney(fecavBillStrike);
		return lsResult;
	}

	/**
	 * 取得外汇核销单已经冲销的进口报关单金额
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 已冲销的进口报关单金额
	 */
	public double findFecavStrikeImpMoney(FecavBillStrike fecavBillStrike) {
		double r = 0.0;
		double rate = 1;
		List list = this.fecavDao
				.findBrikeImpCustomsDeclaration(fecavBillStrike);
		for (int i = 0; i < list.size(); i++) {
			StrikeImpCustomsDeclaration imp = (StrikeImpCustomsDeclaration) list
					.get(i);
			rate = findExchangeRate(imp.getCurr(), fecavBillStrike.getCurr(),
					imp.getDeclareDate());
			r += CommonUtils.getDoubleByDigit(imp.getStrikeMoney() * rate, 2);
		}
		return r;
	}

	/**
	 * 取得外汇核销单已经冲销的汇票金额
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @return 已冲销的汇票金额
	 */
	public double findFecavStrikeExchangeMoney(FecavBillStrike fecavBillStrike) {
		double r = 0.0;
		double rate = 1;
		List list = this.fecavDao.findBrikeBillOfExchange(fecavBillStrike);
		for (int i = 0; i < list.size(); i++) {
			StrikeBillOfExchange exchange = (StrikeBillOfExchange) list.get(i);
			rate = findExchangeRate(exchange.getCurr(), fecavBillStrike
					.getCurr(), exchange.getEndDate());
			r += CommonUtils.getDoubleByDigit(exchange.getStrikeMoney() * rate,
					2);
		}
		return r;
	}

	/**
	 * 查询可以冲销的进口报关单
	 * 
	 * @return 没有冲销的进口报关单
	 */
	public List findNotUseImpCustomsDeclaration() {
		List lsResult = new ArrayList();
		findNotBrikeImpCustomsDeclaration(lsResult, ProjectType.BCUS);
		findNotBrikeImpCustomsDeclaration(lsResult, ProjectType.BCS);
		findNotBrikeImpCustomsDeclaration(lsResult, ProjectType.DZSC);
		return lsResult;
	}

	/**
	 * 查询没有冲销的进口报关单
	 * 
	 * @param lsResult
	 *            进口报关单信息
	 * @param projectType
	 *            模块信息
	 */
	private void findNotBrikeImpCustomsDeclaration(List lsResult,
			int projectType) {
		HashMap hmImpMoney = new HashMap();
		List list = this.fecavDao.findNotUseImpCustomsDeclaration(projectType);
		List lsMoney = this.fecavDao
				.findImpCustomsDeclarationMoney(projectType);
		organiseImp(lsMoney, hmImpMoney);
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclaration base = (BaseCustomsDeclaration) list.get(i);
			double impMoney = hmImpMoney.get(base.getId()) == null ? 0.0
					: Double.parseDouble(hmImpMoney.get(base.getId())
							.toString());

			TempCustomsDeclarationInfoForFecav temp = new TempCustomsDeclarationInfoForFecav();
			temp.setCustomsDeclarationId(base.getId());
			temp.setCustomsDeclarationCode(base.getCustomsDeclarationCode());
			temp.setExportDate(base.getImpExpDate());
			temp.setDeclareDate(base.getDeclarationDate());
			temp.setCurr(base.getCurrency());
			temp.setContractNo(base.getContract());
			temp.setEmsNo(base.getEmsHeadH2k());
			temp.setTotalPrice(impMoney);
			temp.setStrikeMoney(0.0);
			temp.setRemainMoney(impMoney);
			temp.setProjectType(projectType);
			temp.setPreCustomsDeclarationCode(base
					.getPreCustomsDeclarationCode());// 预录入号
			temp.setTradeMode(base.getTradeMode() == null ? "" : base
					.getTradeMode().getName());// 贸易方式
			lsResult.add(temp);
		}
	}
	
	/**
	 * 查询可以冲销的白单
	 * 
	 * @return 没有冲销的进口报关单
	 */
	public List findNotStrikeImpCustomsDeclaration(
			FecavBillStrike fecavBillStrike) {
		List<ImpCustomsDeclaration> lsResult = new ArrayList<ImpCustomsDeclaration>();
		HashMap hmStrikeMoney = new HashMap();
		List list = this.fecavDao.findCanStrikeImpCustomsDeclaration();
		List lsMoney = this.fecavDao.findStrikeImpCustomsDeclarationMoney();
		organiseImp(lsMoney, hmStrikeMoney);
		for (int i = 0; i < list.size(); i++) {
			ImpCustomsDeclaration imp = (ImpCustomsDeclaration) list.get(i);
			if (imp.getWhiteBillNo() == null
					|| "".equals(imp.getWhiteBillNo().trim())) {
				continue;
			}
			double strikeMoney = (hmStrikeMoney.get(imp
					.getCustomsDeclarationId()) == null ? 0.0 : Double
					.parseDouble(hmStrikeMoney.get(
							imp.getCustomsDeclarationId()).toString()));
			imp.setStrikeMoney(strikeMoney);
			imp.setRemainMoney(CommonUtils.getDoubleExceptNull(imp
					.getTotalMoney())
					- CommonUtils.getDoubleExceptNull(imp.getStrikeMoney()));
			if (imp.getCurr() == null) {
				throw new RuntimeException("进口白单 " + imp.getWhiteBillNo()
						+ " 的币别为空");
			}
			double rate = this.findExchangeRate(imp.getCurr(), fecavBillStrike
					.getCurr(), imp.getDeclareDate());
			imp.setConverUSDMoney(CommonUtils.getDoubleExceptNull(imp
					.getRemainMoney())
					* rate);
			if (imp.getRemainMoney() > 0) {
				lsResult.add(imp);
			}
		}
		return lsResult;
	}

	/**
	 * 把以key value形式数组对象的List转成HashMap形式
	 * 
	 * @param list
	 * @param hm
	 */
	private void organiseImp(List list, HashMap hm) {
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			if (objs[0] != null && objs[1] != null) {
				hm.put(objs[0].toString(), Double.valueOf(objs[1].toString()));
			}
		}
	}

	/**
	 * 查询可以冲销的汇票
	 * 
	 * @return 没有冲销的汇票
	 */
	public List findNotBrikeBillOfExchange(FecavBillStrike fecavBillStrike) {
		List lsResult = new ArrayList();
		List lsExchange = this.fecavDao.findBillOfExchange();
		HashMap hmExchangeMoney = new HashMap();
		organiseImp(this.fecavDao.findFecavStrikeExchangeMoney(),
				hmExchangeMoney);
		for (int i = 0; i < lsExchange.size(); i++) {
			BillOfExchange bill = (BillOfExchange) lsExchange.get(i);
			double exchangeMoney = bill.getExchangeMoney();
			double exchangeStrikeMoney = hmExchangeMoney.get(bill.getId()) == null ? 0.0
					: Double.parseDouble(hmExchangeMoney.get(bill.getId())
							.toString());
			double remainMoney = (exchangeMoney - exchangeStrikeMoney);
			if (bill.getCurr() == null) {
				throw new RuntimeException("水单 " + bill.getCode() + " 的币别为空");
			}
			double rate = this.findExchangeRate(bill.getCurr(), fecavBillStrike
					.getCurr(), bill.getEndDate());
			// double exchangeMoney = bill.getExchangeMoney();
			// double exchangeStrikeMoney = CommonUtils.getDoubleExceptNull(bill
			// .getExchangeMoney())
			// - CommonUtils.getDoubleExceptNull(bill.getCanStrikeMoney());
			// double remainMoney = CommonUtils.getDoubleExceptNull(bill
			// .getCanStrikeMoney());
			if (remainMoney > 0) {
				TempBillOfExchangeForFecav temp = new TempBillOfExchangeForFecav();
				temp.setBillOfExchangeId(bill.getId());
				temp.setBillOfExchangeCode(bill.getCode());
				temp.setEndDate(bill.getEndDate());
				temp.setExchangeMoney(exchangeMoney);
				temp.setExchangeRate(bill.getExchangeRate());
				temp.setStrikeMoney(exchangeStrikeMoney);
				temp.setRemainMoney(remainMoney);
				temp.setCurr(bill.getCurr());
				temp.setConverUSDMoney(remainMoney * rate);
				lsResult.add(temp);
			}
		}
		return lsResult;
	}

	/**
	 * 新增进口报关单冲销
	 * 
	 * @param list
	 *            进口报关单冲销
	 * @return 进口报关单冲销信息
	 */
	public List<ImpCustomsDeclaration> addImpCustomsDeclaration(
			List<TempCustomsDeclarationInfoForFecav> list) {
		List<ImpCustomsDeclaration> lsResult = new ArrayList<ImpCustomsDeclaration>();
		for (TempCustomsDeclarationInfoForFecav temp : list) {
			ImpCustomsDeclaration impCustomsDeclaration = new ImpCustomsDeclaration();
			impCustomsDeclaration.setCustomsDeclarationId(temp
					.getCustomsDeclarationId());
			impCustomsDeclaration.setCustomsDeclarationCode(temp
					.getCustomsDeclarationCode());
			impCustomsDeclaration.setContractNo(temp.getContractNo());
			impCustomsDeclaration.setEmsNo(temp.getEmsNo());
			impCustomsDeclaration.setTotalMoney(CommonUtils
					.getDoubleExceptNull(temp.getTotalPrice()));
			impCustomsDeclaration.setStrikeMoney(0.0);
			impCustomsDeclaration.setCompany(CommonUtils.getCompany());
			impCustomsDeclaration.setRemainMoney(CommonUtils
					.getDoubleExceptNull(impCustomsDeclaration.getTotalMoney())
					- CommonUtils.getDoubleExceptNull(impCustomsDeclaration
							.getStrikeMoney()));
			impCustomsDeclaration.setCurr(temp.getCurr());
			impCustomsDeclaration.setDeclareDate(temp.getDeclareDate());
			impCustomsDeclaration.setTradeMode(temp.getTradeMode());
			this.fecavDao.saveImpCustomsDeclaration(impCustomsDeclaration);
			lsResult.add(impCustomsDeclaration);
		}
		// writeBackFecavBillBrikeMoney(fecavBillStrike);
		return lsResult;
	}

	// /**
	// * 新增进口报关单冲销
	// *
	// * @param fecavBillStrike
	// * 外汇核销单冲销
	// * @param list
	// * 临时的外汇核销单的报关单信息
	// * @return 进口报关单冲销
	// */
	// public List addBrikeImpCustomsDeclaration(FecavBillStrike
	// fecavBillStrike,
	// List list) {
	// List lsResult = new ArrayList();
	// for (int i = 0; i < list.size(); i++) {
	// TempCustomsDeclarationInfoForFecav temp =
	// (TempCustomsDeclarationInfoForFecav) list
	// .get(i);
	// StrikeImpCustomsDeclaration brikeImpCustomsDeclaration = new
	// StrikeImpCustomsDeclaration();
	// brikeImpCustomsDeclaration.setFecavBillStrike(fecavBillStrike);
	// brikeImpCustomsDeclaration.setCompany(CommonUtils.getCompany());
	// brikeImpCustomsDeclaration.setCustomsDeclarationId(temp
	// .getCustomsDeclarationId());
	// brikeImpCustomsDeclaration.setCustomsDeclarationCode(temp
	// .getCustomsDeclarationCode());
	// brikeImpCustomsDeclaration.setContractNo(temp.getContractNo());
	// brikeImpCustomsDeclaration.setEmsNo(temp.getEmsNo());
	// brikeImpCustomsDeclaration.setTotalMoney(temp.getTotalPrice());
	// brikeImpCustomsDeclaration.setStrikeMoney(temp.getRemainMoney());
	// brikeImpCustomsDeclaration
	// .setRemainMoney(brikeImpCustomsDeclaration.getTotalMoney()
	// - brikeImpCustomsDeclaration.getStrikeMoney());
	// brikeImpCustomsDeclaration.setCurr(temp.getCurr());
	// brikeImpCustomsDeclaration.setDeclareDate(temp.getDeclareDate());
	// brikeImpCustomsDeclaration.setTradeMode(temp.getTradeMode());
	// this.fecavDao
	// .saveBrikeImpCustomsDeclaration(brikeImpCustomsDeclaration);
	// lsResult.add(brikeImpCustomsDeclaration);
	// }
	// writeBackFecavBillBrikeMoney(fecavBillStrike);
	// return lsResult;
	// }

	/**
	 * 根据汇票id返写汇票金额
	 * 
	 * @param billOfExchangeId
	 *            汇票id
	 */
	private void writeBackBillOfExchangeMoney(String billOfExchangeId,
			FecavBillStrike fecavBillStrike) {
		BillOfExchange billOfExchange = this.fecavDao
				.findBillOfExchangeById(billOfExchangeId);
		double usedMoney = this.fecavDao
				.findFecavStrikeExchangeMoney(billOfExchangeId);
		billOfExchange.setCanStrikeMoney(CommonUtils
				.getDoubleExceptNull(billOfExchange.getExchangeMoney())
				- usedMoney);
		if (billOfExchange.getCurr() == null) {
			throw new RuntimeException("水单 " + billOfExchange.getCode()
					+ " 的币别为空");
		}
		double rate = findExchangeRate(billOfExchange.getCurr(),
				fecavBillStrike.getCurr(), billOfExchange.getEndDate());
		billOfExchange.setConverUSDMoney(CommonUtils
				.getDoubleExceptNull(billOfExchange.getCanStrikeMoney())
				* rate);
		this.fecavDao.saveBillOfExchange(billOfExchange);
	}

	/**
	 * 根据外汇核销单冲销单返写冲销金额
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 */
	private void writeBackFecavBillBrikeMoney(FecavBillStrike fecavBillStrike) {
		if (fecavBillStrike != null) {
			fecavBillStrike.setStrikedImportMoney(CommonUtils.getDoubleByDigit(
					this.findFecavStrikeImpMoney(fecavBillStrike), 2));
			fecavBillStrike.setStrikedExchangeMoney(CommonUtils
					.getDoubleByDigit(this
							.findFecavStrikeExchangeMoney(fecavBillStrike), 2));
			this.fecavDao.saveFecavBillStrike(fecavBillStrike);
		}
	}

	/**
	 * 回写进口白单的核销编号
	 * 
	 * @param strikeImp
	 */
	private void writeBackImpCustDeclaSignInNo(
			StrikeImpCustomsDeclaration strikeImp) {
		String customsDeclarationId = strikeImp.getCustomsDeclarationId();
		ImpCustomsDeclaration imp = this.fecavDao
				.findImpCustomsDeclaration(customsDeclarationId);
		if (imp != null) {
			List list = this.fecavDao
					.findStrikeBillStrikeSignInNo(customsDeclarationId);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i) == null ? ""
						: (list.get(i).toString() + ";"));
			}
			imp.setSignInNo(sb.toString());
			this.fecavDao.saveImpCustomsDeclaration(imp);
		}
	}

	/**
	 * 回写进口白单的余额
	 * 
	 * @param strikeImp
	 */
	private void writeBackImpCustDeclaRemainMoney(ImpCustomsDeclaration imp,
			Curr usdCurr) {
		String customsDeclarationId = imp.getCustomsDeclarationId();
		double strikeMoney = this.fecavDao
				.findStrikeImpCustomsDeclarationMoney(customsDeclarationId);
		if (imp != null) {
			imp.setStrikeMoney(strikeMoney);
			imp.setRemainMoney(CommonUtils.getDoubleExceptNull(imp
					.getTotalMoney())
					- CommonUtils.getDoubleExceptNull(imp.getStrikeMoney()));
			if (imp.getCurr() == null) {
				throw new RuntimeException("进口白单 " + imp.getWhiteBillNo()
						+ " 的币别为空");
			}
			if (CommonUtils.getDoubleExceptNull(imp.getRemainMoney()) >= CommonUtils
					.getDoubleExceptNull(imp.getTotalMoney())) {
				imp.setCanStrike(true);
			} else {
				FecavParameters fecavParameters = this.fecavDao
						.findFecavParameters();
				if (fecavParameters != null) {
					if (fecavParameters.getImpWhiteBillUseOnce() != null
							&& fecavParameters.getImpWhiteBillUseOnce()) {
						imp.setCanStrike(false);
					} else {
						if (CommonUtils.getDoubleExceptNull(imp
								.getRemainMoney()) > CommonUtils
								.getDoubleExceptNull(fecavParameters
										.getImpLowestMoney())) {
							imp.setCanStrike(true);
						} else {
							imp.setCanStrike(false);
						}
					}
				}
			}
			if (usdCurr == null) {
				throw new RuntimeException("美元币别为空");
			}
			double rate = findExchangeRate(imp.getCurr(), usdCurr, imp
					.getDeclareDate());
			imp.setConverUSDMoney(CommonUtils.getDoubleExceptNull(imp
					.getRemainMoney())
					* rate);
			this.fecavDao.saveImpCustomsDeclaration(imp);
		}
	}

	/**
	 * 回写进口白单的余额
	 * 
	 * @param strikeImp
	 */
	private void writeBackImpCustDeclaRemainMoney(
			StrikeImpCustomsDeclaration strikeImp) {
		String customsDeclarationId = strikeImp.getCustomsDeclarationId();
		// double strikeMoney = this.fecavDao
		// .findStrikeImpCustomsDeclarationMoney(customsDeclarationId);
		ImpCustomsDeclaration imp = this.fecavDao
				.findImpCustomsDeclaration(customsDeclarationId);
		writeBackImpCustDeclaRemainMoney(imp, strikeImp.getFecavBillStrike()
				.getCurr());
		// if (imp != null) {
		// imp.setStrikeMoney(strikeMoney);
		// imp.setRemainMoney(CommonUtils.getDoubleExceptNull(imp
		// .getTotalMoney())
		// - CommonUtils.getDoubleExceptNull(imp.getStrikeMoney()));
		// if (imp.getCurr() == null) {
		// throw new RuntimeException("进口白单 " + imp.getWhiteBillNo()
		// + " 的币别为空");
		// }
		// if (CommonUtils.getDoubleExceptNull(imp.getRemainMoney()) >=
		// CommonUtils
		// .getDoubleExceptNull(imp.getTotalMoney())) {
		// imp.setCanStrike(true);
		// } else {
		// FecavParameters fecavParameters = this.fecavDao
		// .findFecavParameters();
		// if (fecavParameters != null) {
		// if (fecavParameters.getImpWhiteBillUseOnce() != null
		// && fecavParameters.getImpWhiteBillUseOnce()) {
		// imp.setCanStrike(false);
		// } else {
		// if (CommonUtils.getDoubleExceptNull(imp
		// .getRemainMoney()) > CommonUtils
		// .getDoubleExceptNull(fecavParameters
		// .getImpLowestMoney())) {
		// imp.setCanStrike(true);
		// } else {
		// imp.setCanStrike(false);
		// }
		// }
		// }
		// }
		// double rate = findExchangeRate(imp.getCurr(), strikeImp
		// .getFecavBillStrike().getCurr(), imp.getDeclareDate());
		// imp.setConverUSDMoney(CommonUtils.getDoubleExceptNull(imp
		// .getRemainMoney())
		// * rate);
		// this.fecavDao.saveImpCustomsDeclaration(imp);
		// }
	}

	/**
	 * 更新进口白单的余额
	 * 
	 * @param imp
	 */
	public void updateRemainImpCustomsDecMoney(ImpCustomsDeclaration imp) {
		Curr usdCurr = this.fecavDao.findUsdCurr();
		writeBackImpCustDeclaRemainMoney(imp, usdCurr);
	}

	/**
	 * 新增汇票冲销
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            临时汇票单据
	 * @return 汇票冲销
	 */
	public List addBrikeBillOfExchange(FecavBillStrike fecavBillStrike,
			List list) {
		List lsResult = new ArrayList();
		fecavBillStrike = this.fecavDao.findFecavBillStrikeById(fecavBillStrike
				.getId());
		if (fecavBillStrike == null) {
			throw new RuntimeException("此核销单签收单单头已被删除");
		}
		// 核销单剩余汇票可冲销金额
		double remainStrikeExchangeMoney = CommonUtils
				.getDoubleExceptNull(fecavBillStrike
						.getRemainStrikeExchangeMoney());
		for (int i = 0; i < list.size(); i++) {
			if (remainStrikeExchangeMoney <= 0) {
				break;
			}
			TempBillOfExchangeForFecav temp = (TempBillOfExchangeForFecav) list
					.get(i);
			StrikeBillOfExchange brikeBillOfExchange = new StrikeBillOfExchange();
			brikeBillOfExchange.setCompany(CommonUtils.getCompany());
			brikeBillOfExchange.setFecavBillStrike(fecavBillStrike);
			brikeBillOfExchange.setBillOfExchangeId(temp.getBillOfExchangeId());
			brikeBillOfExchange.setBillOfExchangeCode(temp
					.getBillOfExchangeCode());
			brikeBillOfExchange.setCurr(temp.getCurr());
			brikeBillOfExchange.setEndDate(temp.getEndDate());
			brikeBillOfExchange.setExchangeRate(temp.getExchangeRate());
			brikeBillOfExchange.setExchangeMoney(temp.getExchangeMoney());
			// 汇票美元金额
			double converUSDMoney = temp.getConverUSDMoney();
			remainStrikeExchangeMoney -= converUSDMoney;
			if (remainStrikeExchangeMoney == 0) {//核销单剩余汇票可冲销金额 等于 汇票美元金额
				brikeBillOfExchange.setStrikeMoney(temp.getRemainMoney());
				brikeBillOfExchange.setRemainMoney(0.0);
				brikeBillOfExchange.setConverUSDMoney(temp.getConverUSDMoney());
			} else if (remainStrikeExchangeMoney < 0) {//核销单剩余汇票可冲销金额 小于 汇票美元金额
				//得到可冲销的汇票金额
				converUSDMoney = (converUSDMoney + remainStrikeExchangeMoney);
				if (converUSDMoney <= 0) {
					break;
				}
				double rate = this.findExchangeRate(brikeBillOfExchange
						.getCurr(), fecavBillStrike.getCurr(),
						brikeBillOfExchange.getEndDate());
				double strikeMoney = CommonUtils.getDoubleByDigit(
						converUSDMoney / rate, 4);
				brikeBillOfExchange.setStrikeMoney(strikeMoney);
				brikeBillOfExchange.setRemainMoney(0.0);
				brikeBillOfExchange.setConverUSDMoney(converUSDMoney);
			} else {//核销单剩余汇票可冲销金额 大于 汇票美元金额
				brikeBillOfExchange.setStrikeMoney(temp.getRemainMoney());
				brikeBillOfExchange.setRemainMoney(brikeBillOfExchange
						.getExchangeMoney()
						- brikeBillOfExchange.getStrikeMoney());
				brikeBillOfExchange.setConverUSDMoney(temp.getConverUSDMoney());
			}
			this.fecavDao.saveBrikeBillOfExchange(brikeBillOfExchange);
			writeBackBillOfExchangeMoney(temp.getBillOfExchangeId(),
					fecavBillStrike);
			lsResult.add(brikeBillOfExchange);
		}
		writeBackFecavBillBrikeMoney(fecavBillStrike);
		return lsResult;
	}

	/**
	 * 判断白单号是否重复
	 * 
	 * @param imp
	 *            进口报关单冲销
	 * @param whiteBillNo
	 *            白单号
	 * @return false 白单号不重复
	 */
	public boolean checkImpWhiteBillNoIsDuple(ImpCustomsDeclaration imp,
			String whiteBillNo) {
		List list = this.fecavDao
				.findImpCustomsDeclarationByWhiteBillNo(whiteBillNo);
		for (int i = 0; i < list.size(); i++) {
			ImpCustomsDeclaration temp = (ImpCustomsDeclaration) list.get(i);
			if (!temp.getId().equals(imp.getId())) {
				// throw new RuntimeException("白单号不能重复");
				return true;
			}
		}
		return false;
	}

	/**
	 * 保存进口报关单冲销
	 * 
	 * @param imp
	 *            进口报关单冲销
	 */
	public void saveBrikeImpCustomsDeclaration(StrikeImpCustomsDeclaration imp) {
		this.fecavDao.saveBrikeImpCustomsDeclaration(imp);
		FecavBillStrike fecavBillStrike = imp.getFecavBillStrike();
		writeBackImpCustDeclaRemainMoney(imp);
		writeBackFecavBillBrikeMoney(fecavBillStrike);
	}

	/**
	 * 保存汇票冲销
	 * 
	 * @param exchange
	 *            汇票冲销
	 */
	public void saveBrikeBillOfExchange(StrikeBillOfExchange exchange) {
		String billOfExchangeId = exchange.getBillOfExchangeId();
		double strikeMoney = this.fecavDao
				.findStrikedExchangeMoney(billOfExchangeId);
		if (exchange.getId() != null && !exchange.getId().equals("")) {
			StrikeBillOfExchange oldExchange = (StrikeBillOfExchange) this.fecavDao
					.load(StrikeBillOfExchange.class, exchange.getId());
			if (oldExchange != null) {
				if (CommonUtils
						.getDoubleExceptNull(exchange.getExchangeMoney())
						- (CommonUtils.getDoubleExceptNull(exchange
								.getStrikeMoney())
								+ strikeMoney - CommonUtils
								.getDoubleExceptNull(oldExchange
										.getStrikeMoney())) < 0) {
					throw new RuntimeException("此汇票的冲销金额已超出汇票总金额");
				}
			}
		} else {
			if (CommonUtils.getDoubleExceptNull(exchange.getExchangeMoney())
					- (CommonUtils.getDoubleExceptNull(exchange
							.getStrikeMoney()) + strikeMoney) < 0) {
				throw new RuntimeException("此汇票的冲销金额已超出汇票总金额");
			}
		}
		if (exchange.getCurr() == null) {
			throw new RuntimeException("汇票 " + exchange.getBillOfExchangeCode()
					+ " 的币别为空");
		}
		double rate = findExchangeRate(exchange.getCurr(), exchange
				.getFecavBillStrike().getCurr(), exchange.getEndDate());
		exchange.setConverUSDMoney(CommonUtils.getDoubleExceptNull(exchange
				.getStrikeMoney())
				* rate);

		this.fecavDao.saveBrikeBillOfExchange(exchange);
		FecavBillStrike fecavBillStrike = exchange.getFecavBillStrike();
		writeBackFecavBillBrikeMoney(fecavBillStrike);
		writeBackBillOfExchangeMoney(exchange.getBillOfExchangeId(), exchange
				.getFecavBillStrike());
	}

	// /**
	// * 删除进口报关单冲销
	// *
	// * @param imp
	// */
	// public void deleteBrikeImpCustomsDeclaration(List lsImp) {
	// FecavBillStrike fecavBillStrike = null;
	// for (int i = 0; i < lsImp.size(); i++) {
	// StrikeImpCustomsDeclaration imp = (StrikeImpCustomsDeclaration) lsImp
	// .get(i);
	// if (i == 0) {
	// fecavBillStrike = imp.getFecavBillStrike();
	//				
	// }
	// this.fecavDao.deleteBrikeImpCustomsDeclaration(imp);
	// }
	// writeBackFecavBillBrikeMoney(fecavBillStrike);
	// }

	/**
	 * 删除进口报关单冲销
	 * 
	 * @param lsImp
	 *            进口报关单冲销
	 */
	public void deleteBrikeImpCustomsDeclaration(List lsImp) {
		FecavBillStrike fecavBillStrike = null;
		for (int i = 0; i < lsImp.size(); i++) {
			StrikeImpCustomsDeclaration strikeImp = (StrikeImpCustomsDeclaration) lsImp
					.get(i);
			// ImpCustomsDeclaration imp = (ImpCustomsDeclaration) this.fecavDao
			// .findImpCustomsDeclaration(strikeImp
			// .getCustomsDeclarationId());
			if (i == 0) {
				fecavBillStrike = strikeImp.getFecavBillStrike();
			}
			this.fecavDao.deleteBrikeImpCustomsDeclaration(strikeImp);
			this.writeBackImpCustDeclaRemainMoney(strikeImp);
		}
		writeBackFecavBillBrikeMoney(fecavBillStrike);
	}

	/**
	 * 删除汇票冲销
	 * 
	 * @param lsExchange
	 *            汇票冲销
	 */
	public void deleteBrikeBillOfExchange(List lsExchange) {
		FecavBillStrike fecavBillStrike = null;
		for (int i = 0; i < lsExchange.size(); i++) {
			StrikeBillOfExchange exchange = (StrikeBillOfExchange) lsExchange
					.get(i);
			if (i == 0) {
				fecavBillStrike = exchange.getFecavBillStrike();
			}
			this.fecavDao.deleteBrikeBillOfExchange(exchange);
			this.writeBackBillOfExchangeMoney(exchange.getBillOfExchangeId(),
					exchange.getFecavBillStrike());
		}
		writeBackFecavBillBrikeMoney(fecavBillStrike);
	}

	/**
	 * 删除签收单头
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 */
	public void deleteFecavBillStrike(FecavBillStrike fecavBillStrike) {
		List list = this.fecavDao.findFecavBillByStrike(fecavBillStrike);
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			fecavBill.setFecavBillStrike(null);
			fecavBill.setBillState(FecavState.HAND_IN_BILL);
			this.fecavDao.saveFecavBill(fecavBill);
		}
		this.deleteBrikeImpCustomsDeclaration(this.fecavDao
				.findBrikeImpCustomsDeclaration(fecavBillStrike));
		this.deleteBrikeBillOfExchange(this.fecavDao
				.findBrikeBillOfExchange(fecavBillStrike));
		this.fecavDao.deleteFecavBillStrike(fecavBillStrike);
	}

	/**
	 * 新增外汇核销单（核销签收）
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            外汇核销单
	 * @return 增加后的外汇核销单内容
	 */
	public List addFecavBillForBrike(FecavBillStrike fecavBillStrike, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			fecavBill.setBillState(FecavState.CONTROL);
			fecavBill.setFecavBillStrike(fecavBillStrike);
			if (fecavBill.getCurr() == null) {
				throw new RuntimeException("核销单 " + fecavBill.getCode()
						+ " 的币别为空");
			}
			double rate = findExchangeRate(fecavBill.getCurr(), fecavBillStrike
					.getCurr(), fecavBill.getDeclareDate());
			double converUSDMoney = CommonUtils.getDoubleByDigit(CommonUtils
					.getDoubleExceptNull(fecavBill.getTotalPrice())
					* rate, 4);
			fecavBill.setConverUSDMoney(converUSDMoney);
			// this.fecavDao.saveFecavBill(fecavBill);
			lsResult.add(fecavBill);
		}

		this.fecavDao.saveFecavBill2(lsResult);
		writeBackFecavBillTotalMoney(fecavBillStrike);
		return lsResult;
	}

	/**
	 * 根据状态查询外汇核销单没有被核销的
	 * 
	 * @param trade
	 *            贸易方式
	 * @param itemCount
	 *            没有被核销和项数
	 * @return 没有被核销的外汇核销单
	 */
	public List findFecavBillNotStrike(FecavBillStrike fecavBillStrike,
			Integer itemCount) {
		List<Object> lsTemp = new ArrayList<Object>();
		List list = this.fecavDao.findFecavBillNotStrike();
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			if (fecavBillStrike != null) {
				if (!checkFecavBillTrade(fecavBillStrike, this.fecavDao
						.findCustomsDeclarationByFecavBill(fecavBill))) {
					continue;
				}
			}
			lsTemp.add(fecavBill);
		}
		list.clear();
		if (itemCount != null && itemCount > 0) {
			if (lsTemp.size() <= itemCount) {
				list.addAll(lsTemp);
			} else {
				for (int i = 0; i < itemCount; i++) {
					list.add(lsTemp.get(i));
				}
			}
		} else {
			list.addAll(lsTemp);
		}
		return list;
	}

	/**
	 * 检查外汇核销单贸易方式
	 * 
	 * @param trade
	 *            贸易方式
	 * @param lsExpCustoms
	 *            进出口报关信息
	 * @return 外汇核销单的贸易方式和报关单的贸易方式相同为true 反之为false
	 */
	private boolean checkFecavBillTrade(FecavBillStrike fecavBillStrike,
			List lsExpCustoms) {
		for (int i = 0; i < lsExpCustoms.size(); i++) {
			BaseCustomsDeclaration base = (BaseCustomsDeclaration) lsExpCustoms
					.get(i);
			if (!fecavBillStrike.getTradeMode().equals(base.getTradeMode())) {
				return false;
			}
			if (!fecavBillStrike.getEmsNo().equals(base.getEmsHeadH2k())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 新增外汇核销单（核销签收）
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param num
	 *            外汇核销单份数
	 * @return 外汇核销单
	 */
	public List addFecavBillForBrike(FecavBillStrike fecavBillStrike,
			Integer num) {
		List lsResult = new ArrayList();
		// List list =
		// this.findFecavBillNotStrike(fecavBillStrike.getTradeMode());
		// if (num > list.size()) {
		// num = list.size();
		// }
		// for (int i = 0; i < num; i++) {
		// FecavBill fecavBill = (FecavBill) list.get(i);
		// fecavBill.setBillState(FecavState.CONTROL);
		// fecavBill.setFecavBillStrike(fecavBillStrike);
		// this.fecavDao.saveFecavBill(fecavBill);
		// lsResult.add(fecavBill);
		// }
		// writeBackFecavBillTotalMoney(fecavBillStrike);
		return lsResult;
	}

	/**
	 * 返写外汇核销单金额
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 */
	private void writeBackFecavBillTotalMoney(FecavBillStrike fecavBillStrike) {
		if (fecavBillStrike != null) {
			List list = this.fecavDao.findFecavBillByStrike(fecavBillStrike);
			int digitNum = 0;
			FecavParameters fecavParameters = this.fecavDao
					.findFecavParameters();
			if (fecavParameters != null
					&& fecavParameters.getFecavControlDigitsNum() != null) {
				digitNum = fecavParameters.getFecavControlDigitsNum();
			}
			double strikeRate = (fecavBillStrike.getStrikeRate() == null ? 0.0
					: fecavBillStrike.getStrikeRate() / 100.0);
			fecavBillStrike.setCanStrikeTotalMoney(CommonUtils
					.getDoubleByDigit(findFecavBillTotalMoney(fecavBillStrike,
							list), digitNum));
			fecavBillStrike.setCanStrikeImportMoney(CommonUtils
					.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(fecavBillStrike
									.getCanStrikeTotalMoney())
							* (1 - strikeRate), digitNum));
			fecavBillStrike.setCanStrikeExchangeMoney(CommonUtils
					.getDoubleByDigit(CommonUtils
							.getDoubleExceptNull(fecavBillStrike
									.getCanStrikeTotalMoney())
							- CommonUtils.getDoubleExceptNull(fecavBillStrike
									.getCanStrikeImportMoney()), digitNum));
			if ((CommonUtils.getDoubleExceptNull(fecavBillStrike
					.getCanStrikeExchangeMoney()) - CommonUtils
					.getDoubleExceptNull(fecavBillStrike
							.getStrikedExchangeMoney())) < 0) {
				throw new RuntimeException("可冲销结汇金额不能小于已冲销结汇金额");
			}
			if ((CommonUtils.getDoubleExceptNull(fecavBillStrike
					.getCanStrikeImportMoney()) - CommonUtils
					.getDoubleExceptNull(fecavBillStrike
							.getStrikedImportMoney())) < 0) {
				throw new RuntimeException("可冲销料值不能小于已冲销料值");
			}
			fecavBillStrike.setPieces(list.size());
			this.fecavDao.saveFecavBillStrike(fecavBillStrike);
		}
	}

	/**
	 * 取得外汇核销单总金额
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            外汇核销单
	 * @return 外汇核销单总金额
	 */
	private double findFecavBillTotalMoney(FecavBillStrike fecavBillStrike,
			List list) {
		double r = 0.0;
		double rate = 1;
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			rate = findExchangeRate(fecavBill.getCurr(), fecavBillStrike
					.getCurr(), fecavBill.getDeclareDate());
			r += (CommonUtils.getDoubleExceptNull(fecavBill.getTotalPrice()) * rate);
			// r += (CommonUtils
			// .getDoubleExceptNull(fecavBill.getConverUSDMoney()));
		}
		return r;
	}

	/**
	 * 取得汇率
	 * 
	 * @param sourCurr
	 *            本地汇率
	 * @param destCurr
	 *            外地汇率
	 * @param createDate
	 *            创建日期
	 * @return 汇率
	 */
	public double findExchangeRate(Curr sourCurr, Curr destCurr, Date createDate) {
		if (sourCurr.equals(destCurr)) {
			return 1.0;
		}
		if (sourCurr == null) {
			throw new RuntimeException("本币币别为空,查不到其汇率");
		}
		if (destCurr == null) {
			throw new RuntimeException("外币币别为空,查不到其汇率");
		}
		// List list = this.fecavDao.findExchangeRate(sourCurr, destCurr,
		// createDate);
		List list = this.fecavDao.findExchangeRate(destCurr, sourCurr,
				createDate);
		if (list.size() < 1 || list.get(0) == null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = "";
			if (createDate != null) {
				date = format.format(createDate);
			}
			throw new RuntimeException("没有 " + destCurr.getName() + " 和 "
					+ sourCurr.getName() + "在日期" + date + "之前 的汇率设定");
		}
		CurrRate currRate = (CurrRate) list.get(0);
		return currRate.getRate();
	}

	/**
	 * 移除外汇核销单（核销签收）
	 * 
	 * @param fecavBillStrike
	 *            外汇核销单冲销
	 * @param list
	 *            外汇核销单
	 */
	public void removeFecavBillForBrike(FecavBillStrike fecavBillStrike,
			List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			FecavBill fecavBill = (FecavBill) list.get(i);
			fecavBill.setFecavBillStrike(null);
			fecavBill.setBillState(FecavState.HAND_IN_BILL);
			this.fecavDao.saveFecavBill(fecavBill);
			lsResult.add(fecavBill);
		}
		writeBackFecavBillTotalMoney(fecavBillStrike);
	}

	/**
	 * 获得最大的核销单编号
	 * 
	 * @return 不为空则为最大的核销单编号 为空返回0001
	 */
	public String getMaxSignInNo() {
		List list = this.fecavDao.getMaxSignInNo();
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
	 * 类型转换
	 */
	private String replace(String replaceChar, int count) {
		String str = "";
		for (int i = 0; i < count; i++) {
			str += replaceChar;
		}
		return str;
	}

	/**
	 * 取得核销单编号
	 * 
	 * @param suffix
	 *            后缀
	 * @return 字符型的日期+后缀(指定的)
	 */
	private String getBillNo(String suffix) {
		return this.convertDateToString() + suffix;
	}

	/**
	 * 把日期转换为字符型
	 * 
	 * @return 转换为字符的日期
	 */
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
	 * 外汇核销单映射内容
	 */
	public Map<String, List<FecavBill>> getFecavBillMap() {
		Map<String, List<FecavBill>> map = new HashMap<String, List<FecavBill>>();
		List listKey = this.fecavDao.findFecavBillEmsNo();
		List<FecavBill> listFecavBill = this.fecavDao.findFecavBillNotCancel();
		for (int i = 0; i < listKey.size(); i++) {
			String key = (String) listKey.get(i);
			List<FecavBill> tempList = new ArrayList<FecavBill>();
			for (FecavBill f : listFecavBill) {
				if (key.equalsIgnoreCase(f.getEmsNo())) {
					tempList.add(f);
				}
			}
			map.put(key, tempList);
		}
		return map;
	}

	/**
	 * 根据外汇核销单抓取已冲销的汇票(所有)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 所有已冲销的汇票
	 */
	public List<TempBillOfExchangeForFecav> findBrikeBillOfExchange(
			Date beginDate, Date endDate) {
		List<TempBillOfExchangeForFecav> lsResult = new ArrayList<TempBillOfExchangeForFecav>();
		List<BillOfExchange> list = this.fecavDao.findBrikeBillOfExchange(
				beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			BillOfExchange billOfExchange = (BillOfExchange) list.get(i);
			TempBillOfExchangeForFecav temp = new TempBillOfExchangeForFecav();
			try {
				PropertyUtils.copyProperties(temp, billOfExchange);
			} catch (Exception e) {
				e.printStackTrace();
			}
			temp.setBillOfExchangeCode(billOfExchange.getCode());
			String billOfExchangeId = billOfExchange.getId();
			double usedMoney = this.fecavDao
					.findFecavStrikeExchangeMoney(billOfExchangeId);
			temp.setStrikeMoney(usedMoney);
			temp.setRemainMoney(CommonUtils.getDoubleExceptNull(billOfExchange
					.getExchangeMoney())
					- usedMoney);
			lsResult.add(temp);
		}
		//
		// 加一条空的记录
		//
		lsResult.add(new TempBillOfExchangeForFecav());
		//
		// 加入统计记录
		//
		TempBillOfExchangeForFecav tempS = makeTotalStrikeBillOfExchange(lsResult);
		lsResult.add(tempS);
		return lsResult;
	}

	/**
	 * 生成统计数据
	 * 
	 * @param list
	 *            汇票冲销
	 * @return 统计结汇金额 冲销金额 剩余金额
	 */
	private TempBillOfExchangeForFecav makeTotalStrikeBillOfExchange(
			List<TempBillOfExchangeForFecav> list) {
		TempBillOfExchangeForFecav s = new TempBillOfExchangeForFecav();
		s.setBillOfExchangeCode("合计");
		double exchangeMoney = 0.0;// 结汇金额
		double strikeMoney = 0.0;// 冲销金额
		double remainMoney = 0.0;// 剩余金额
		for (TempBillOfExchangeForFecav temp : list) {
			exchangeMoney += (temp.getExchangeMoney() == null ? 0 : temp
					.getExchangeMoney());
			strikeMoney += (temp.getStrikeMoney() == null ? 0 : temp
					.getStrikeMoney());
			remainMoney += (temp.getRemainMoney() == null ? 0 : temp
					.getRemainMoney());
		}
		s.setExchangeMoney(exchangeMoney);
		s.setStrikeMoney(strikeMoney);
		s.setRemainMoney(remainMoney);
		return s;
	}

	/**
	 * 查询银行汇票没有被使用的记录(所有)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内结汇金额 冲销金额 剩余金额的统计情况
	 */
	public List<TempBillOfExchangeForFecav> findBillOfExchangeNotUse(
			Date beginDate, Date endDate) {
		List<TempBillOfExchangeForFecav> lsResult = new ArrayList<TempBillOfExchangeForFecav>();
		List<BillOfExchange> list = this.fecavDao.findBillOfExchangeNotUse(
				beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			BillOfExchange billOfExchange = (BillOfExchange) list.get(i);
			TempBillOfExchangeForFecav temp = new TempBillOfExchangeForFecav();
			try {
				PropertyUtils.copyProperties(temp, billOfExchange);
			} catch (Exception e) {
				e.printStackTrace();
			}
			temp.setBillOfExchangeCode(billOfExchange.getCode());
			temp.setStrikeMoney(0.0);
			temp.setRemainMoney(CommonUtils.getDoubleExceptNull(billOfExchange
					.getExchangeMoney()));
			lsResult.add(temp);
		}
		//
		// 加一条空的记录
		//
		lsResult.add(new TempBillOfExchangeForFecav());
		//
		// 加入统计记录
		//
		TempBillOfExchangeForFecav tempS = makeTotalStrikeBillOfExchange(lsResult);
		lsResult.add(tempS);
		return lsResult;
	}

	/**
	 * 根据外汇核销单来源与已核销签收(核销,财务签收,关帐)统计表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 已核销签收(核销,财务签收,关帐)统计表
	 */
	public List<TempCancelSignInStat> findTempCancelSignInStat(Date beginDate,
			Date endDate) {
		List<TempCancelSignInStat> returnList = new ArrayList<TempCancelSignInStat>();
		List<FecavBill> list = this.fecavDao.findFecavBillByCancel(beginDate,
				endDate);
		Map<String, TempCancelSignInStat> map = new HashMap<String, TempCancelSignInStat>();
		for (FecavBill fecavBill : list) {
			if (fecavBill.getFecavBillStrike() == null) {
				continue;
			}
			// 签收编码 就是批次
			String key = fecavBill.getFecavBillStrike().getSignInNo();
			if (map.get(key) == null) { // 不存在
				TempCancelSignInStat temp = new TempCancelSignInStat();
				temp.setBatchNo(fecavBill.getFecavBillStrike().getSignInNo()); // 批次号
				temp.setBeginBillNo(fecavBill.getCode()); // 开始核销单号
				temp.setCount(1); // 当前份数为1
				temp.setMoney(fecavBill.getFecavBillStrike()
						.getCanStrikeTotalMoney() == null ? 0 : fecavBill
						.getFecavBillStrike().getCanStrikeTotalMoney()); // 为当前核销值
				map.put(key, temp);
			} else { // 存在
				TempCancelSignInStat temp = map.get(key);
				temp.setEndBillNo(fecavBill.getCode()); // 结束核销单号
				int count = temp.getCount() + 1;
				temp.setCount(count); // 当前份数加1
				double money = fecavBill.getFecavBillStrike()
						.getCanStrikeTotalMoney() == null ? 0 : fecavBill
						.getFecavBillStrike().getCanStrikeTotalMoney();
				money += temp.getMoney();
				temp.setMoney(money);
			}
		}
		returnList.addAll(map.values());
		//
		// 加一条空的记录
		//
		returnList.add(new TempCancelSignInStat());
		//
		// 生成最后一条统计数据
		//
		TempCancelSignInStat temp = makeTotalTempCancelSignInStat(returnList);
		returnList.add(temp);
		return returnList;
	}

	/**
	 * 保存外汇核销单并且反写报关单的出口日期
	 * 
	 * @param b
	 *            外汇核销单
	 */
	public void saveFecavBillAndUpdateCustomsDeclaration(FecavBill b) {
		this.fecavDao.saveFecavBill(b);
		List list = this.fecavDao.findCustomsDeclarationByFecavBill(b);
		if (list.size() > 0) {
			BaseCustomsDeclaration c = (BaseCustomsDeclaration) list.get(0);
			c.setImpExpDate(b.getExportDate());
			this.fecavDao.saveOrUpdate(c);
		}

	}

	/**
	 * 更新外汇核销单进口报关单号
	 * 
	 * @param b
	 *            外汇核销单
	 */
	public void updateFecavBillCustomsDeclarationCode() {
		List lsFecavBill = this.fecavDao.findFecavBillNotUsed("",
				new ArrayList());
		for (int i = 0; i < lsFecavBill.size(); i++) {
			FecavBill fecavBill = (FecavBill) lsFecavBill.get(i);
			if (fecavBill.getCustomsDeclarationCode() != null
					&& !"".equals(fecavBill.getCustomsDeclarationCode().trim())) {
				continue;
			}
			List list = this.fecavDao
					.findCustomsDeclarationByFecavBill(fecavBill);
			if (list.size() <= 0) {
				list = this.fecavDao
						.findCustomsDeclarationByFecavBillCode(fecavBill);
			}
			if (list.size() > 0) {
				BaseCustomsDeclaration c = (BaseCustomsDeclaration) list.get(0);
				fecavBill.setCustomsDeclarationCode(c
						.getCustomsDeclarationCode());
				fecavBill.setContractNo(c.getContract());
				this.fecavDao.saveFecavBill(fecavBill);
			}
		}
	}

	/**
	 * 生成统计数据
	 * 
	 * @param list
	 *            临时的已核销签收的内容
	 * @return 临时的已核销签收(核销,财务签收,关帐)统计表
	 */
	private TempCancelSignInStat makeTotalTempCancelSignInStat(
			List<TempCancelSignInStat> list) {
		TempCancelSignInStat temp = new TempCancelSignInStat();
		temp.setBatchNo("合计");
		int totalCount = 0;// 份数
		double totalMoney = 0.0;// 总金额
		for (TempCancelSignInStat tempC : list) {
			totalCount += (tempC.getCount() == null ? 0 : tempC.getCount());
			totalMoney += (tempC.getMoney() == null ? 0 : tempC.getMoney());
		}
		temp.setCount(totalCount);
		temp.setMoney(totalMoney);
		return temp;
	}

	/**
	 * 根据外汇核销单来自源于[交单末核销]的单据(合同汇总表)
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内[交单末核销]的单据(合同汇总表)
	 */
	public List<TempCancelContractStat> findTempCancelContractStat(
			Date beginDate, Date endDate) {
		List<TempCancelContractStat> returnList = new ArrayList<TempCancelContractStat>();
		List<FecavBill> list = this.fecavDao.findFecavBillNoCancel(beginDate,
				endDate);
		Map<String, TempCancelContractStat> map = new HashMap<String, TempCancelContractStat>();
		for (FecavBill fecavBill : list) {
			if (fecavBill.getFecavBillStrike() == null) {
				continue;
			}
			// 合同号为key
			String key = fecavBill.getContractNo();
			if (map.get(key) == null) { // 不存在
				TempCancelContractStat temp = new TempCancelContractStat();
				//
				// 合同号
				//
				temp.setContractNo(fecavBill.getContractNo());
				//
				// 当前份数为1
				//
				temp.setCount(1);
				//
				// 出口总金额
				//
				temp.setExportMoney(fecavBill.getTotalPrice() == null ? 0
						: fecavBill.getTotalPrice());
				//
				// 币制名称
				//
				temp.setCurrName(fecavBill.getCurr().getName());
				//
				// 可抵扣料件金额
				//
				temp.setMaterielMoney(fecavBill.getFecavBillStrike()
						.getCanStrikeImportMoney() == null ? 0 : fecavBill
						.getFecavBillStrike().getCanStrikeImportMoney());
				map.put(key, temp);
			} else { // 存在
				TempCancelContractStat temp = map.get(key);
				//
				// 当前份数加1
				//				
				int count = temp.getCount() + 1;
				temp.setCount(count);
				//
				// 出口总金额
				//
				double exportMoney = fecavBill.getTotalPrice() == null ? 0
						: fecavBill.getTotalPrice();
				exportMoney += temp.getExportMoney();
				temp.setExportMoney(exportMoney);
				//
				// 可抵扣料件金额
				//
				double materielMoney = fecavBill.getFecavBillStrike()
						.getCanStrikeImportMoney() == null ? 0 : fecavBill
						.getFecavBillStrike().getCanStrikeImportMoney();
				materielMoney += temp.getMaterielMoney();
				temp.setMaterielMoney(materielMoney);
			}
		}
		returnList.addAll(map.values());
		//
		// 加一条空的记录
		//
		returnList.add(new TempCancelContractStat());
		//
		// 生成最后一条统计数据
		//
		TempCancelContractStat temp = makeTotalTempCancelContractStat(returnList);
		returnList.add(temp);
		return returnList;
	}

	/**
	 * 生成统计数据
	 * 
	 * @param list
	 *            外汇合同核销汇总表信息
	 * @return 外汇合同核销汇总表的统计数据
	 */
	private TempCancelContractStat makeTotalTempCancelContractStat(
			List<TempCancelContractStat> list) {
		TempCancelContractStat temp = new TempCancelContractStat();
		temp.setContractNo("合计");
		int totalCount = 0;// 份数
		double totalExportMoney = 0.0;// 出口总金额
		double totalMaterielMoney = 0.0;// 料件总金额
		for (TempCancelContractStat tempC : list) {
			totalCount += (tempC.getCount() == null ? 0 : tempC.getCount());
			totalExportMoney += (tempC.getExportMoney() == null ? 0 : tempC
					.getExportMoney());
			totalMaterielMoney += (tempC.getMaterielMoney() == null ? 0 : tempC
					.getMaterielMoney());
		}
		temp.setCount(totalCount);
		temp.setExportMoney(totalExportMoney);
		temp.setMaterielMoney(totalMaterielMoney);
		return temp;
	}

	/**
	 * 出口核销单使用状况表
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            截止日期
	 * @return 有效期内出口核销单使用情况
	 */
	public List findFecavBillForUsedStat(Date beginDate, Date endDate) {
		List lsResult = new ArrayList();
		List lsOuterObtain = this.fecavDao.findOuterObtainFecavForStat(
				beginDate, endDate);
		List lsUsed = this.fecavDao.findUsedFecavForStat(beginDate, endDate);
		List lsBlankOut = this.fecavDao.findBlankOutFecavForStat(beginDate,
				endDate);
		/**
		 * 从外汇局领用情况
		 */
		int iCount = 0;
		int index = -1;
		String man = "", beginCode = "", endCode = "";
		Date date = null;
		FecavBill preFecavBill = null;
		for (int i = 0; i < lsOuterObtain.size(); i++) {
			if (preFecavBill == null) {
				preFecavBill = (FecavBill) lsOuterObtain.get(i);
				iCount++;
				index++;
				beginCode = preFecavBill.getCode();
				endCode = preFecavBill.getCode();
				date = preFecavBill.getOuterObtainDate();
				man = preFecavBill.getOuterObtain();
				if (lsResult.size() <= index) {
					TempFecavBillUsedStat temp = new TempFecavBillUsedStat();
					lsResult.add(temp);
				}
				if (lsOuterObtain.size() == 1) {
					TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
							.get(index);
					temp.setOuterObtainDate(date);
					temp.setOuterObtainMan(man);
					temp.setOuterObtainBeginCode(beginCode);
					temp.setOuterObtainEndCode(endCode);
					temp.setOuterObtainPieces(iCount);
				}
				continue;
			}
			FecavBill fecavBill = (FecavBill) lsOuterObtain.get(i);
			if (!checkOuterObtainFecavBillIsSequence(preFecavBill, fecavBill)) {
				index++;
				if (lsResult.size() <= index) {
					TempFecavBillUsedStat temp = new TempFecavBillUsedStat();
					lsResult.add(temp);
				}
				endCode = preFecavBill.getCode();
				date = preFecavBill.getOuterObtainDate();
				man = preFecavBill.getOuterObtain();
				TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
						.get(index - 1);
				temp.setOuterObtainDate(date);
				temp.setOuterObtainMan(man);
				temp.setOuterObtainBeginCode(beginCode);
				temp.setOuterObtainEndCode(endCode);
				temp.setOuterObtainPieces(iCount);
				iCount = 0;
				beginCode = fecavBill.getCode();
			}
			preFecavBill = (FecavBill) lsOuterObtain.get(i);
			iCount++;
			if (i == lsOuterObtain.size() - 1) {
				endCode = fecavBill.getCode();
				date = preFecavBill.getOuterObtainDate();
				man = preFecavBill.getOuterObtain();
				TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
						.get(index);
				temp.setOuterObtainDate(date);
				temp.setOuterObtainMan(man);
				temp.setOuterObtainBeginCode(beginCode);
				temp.setOuterObtainEndCode(endCode);
				temp.setOuterObtainPieces(iCount);
			}
		}
		/**
		 * 使用情况
		 */
		iCount = 0;
		index = -1;
		String innerMan = "";
		man = "";
		beginCode = "";
		endCode = "";
		date = null;
		preFecavBill = null;
		for (int i = 0; i < lsUsed.size(); i++) {
			if (preFecavBill == null) {
				preFecavBill = (FecavBill) lsUsed.get(i);
				iCount++;
				index++;
				beginCode = preFecavBill.getCode();
				endCode = preFecavBill.getCode();
				date = preFecavBill.getUsedDate();
				man = preFecavBill.getUsedMan();
				innerMan = preFecavBill.getInnerObtain();
				if (lsResult.size() <= index) {
					TempFecavBillUsedStat temp = new TempFecavBillUsedStat();
					lsResult.add(temp);
				}
				if (lsUsed.size() == 1) {
					TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
							.get(index);
					temp.setUsedDate(date);
					temp.setUsedMan(man);
					temp.setUsedBeginCode(beginCode);
					temp.setUsedEndCode(endCode);
					temp.setUsedPieces(iCount);
					temp.setInnerObtainMan(innerMan);
				}
				continue;
			}
			FecavBill fecavBill = (FecavBill) lsUsed.get(i);
			if (!checkUsedFecavBillIsSequence(preFecavBill, fecavBill)) {
				index++;
				if (lsResult.size() <= index) {
					TempFecavBillUsedStat temp = new TempFecavBillUsedStat();
					lsResult.add(temp);
				}
				endCode = preFecavBill.getCode();
				date = preFecavBill.getUsedDate();
				man = preFecavBill.getUsedMan();
				innerMan = preFecavBill.getInnerObtain();
				TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
						.get(index - 1);
				temp.setUsedDate(date);
				temp.setUsedMan(man);
				temp.setUsedBeginCode(beginCode);
				temp.setUsedEndCode(endCode);
				temp.setUsedPieces(iCount);
				temp.setInnerObtainMan(innerMan);
				iCount = 0;
				beginCode = fecavBill.getCode();

			}
			preFecavBill = (FecavBill) lsUsed.get(i);
			iCount++;
			if (i == lsUsed.size() - 1) {
				endCode = fecavBill.getCode();
				date = preFecavBill.getUsedDate();
				man = preFecavBill.getUsedMan();
				innerMan = preFecavBill.getInnerObtain();
				TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
						.get(index);
				temp.setUsedDate(date);
				temp.setUsedMan(man);
				temp.setUsedBeginCode(beginCode);
				temp.setUsedEndCode(endCode);
				temp.setUsedPieces(iCount);
				temp.setInnerObtainMan(innerMan);
			}
		}
		/**
		 * 遗失作废情况
		 */
		iCount = 0;
		index = -1;
		man = "";
		beginCode = "";
		endCode = "";
		date = null;
		preFecavBill = null;
		for (int i = 0; i < lsBlankOut.size(); i++) {
			if (preFecavBill == null) {
				preFecavBill = (FecavBill) lsBlankOut.get(i);
				iCount++;
				index++;
				beginCode = preFecavBill.getCode();
				endCode = preFecavBill.getCode();
				date = preFecavBill.getBlankOutDate();
				man = preFecavBill.getBlankOutMan();
				if (lsResult.size() <= index) {
					TempFecavBillUsedStat temp = new TempFecavBillUsedStat();
					lsResult.add(temp);
				}
				if (lsBlankOut.size() == 1) {
					TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
							.get(index);
					temp.setBlankOutDate(date);
					temp.setBlankOutMan(man);
					temp.setBlankOutBeginCode(beginCode);
					temp.setBlankOutEndCode(endCode);
					temp.setBlankOutPieces(iCount);
				}
				continue;
			}
			FecavBill fecavBill = (FecavBill) lsBlankOut.get(i);
			if (!checkUsedFecavBillIsSequence(preFecavBill, fecavBill)) {
				index++;
				if (lsResult.size() <= index) {
					TempFecavBillUsedStat temp = new TempFecavBillUsedStat();
					lsResult.add(temp);
				}
				endCode = preFecavBill.getCode();
				date = preFecavBill.getBlankOutDate();
				man = preFecavBill.getBlankOutMan();
				TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
						.get(index - 1);
				temp.setBlankOutDate(date);
				temp.setBlankOutMan(man);
				temp.setBlankOutBeginCode(beginCode);
				temp.setBlankOutEndCode(endCode);
				temp.setBlankOutPieces(iCount);
				iCount = 0;
				beginCode = fecavBill.getCode();
			}
			preFecavBill = (FecavBill) lsBlankOut.get(i);
			iCount++;
			if (i == lsBlankOut.size() - 1) {
				endCode = fecavBill.getCode();
				date = preFecavBill.getBlankOutDate();
				man = preFecavBill.getBlankOutMan();
				TempFecavBillUsedStat temp = (TempFecavBillUsedStat) lsResult
						.get(index);
				temp.setBlankOutDate(date);
				temp.setBlankOutMan(man);
				temp.setBlankOutBeginCode(beginCode);
				temp.setBlankOutEndCode(endCode);
				temp.setBlankOutPieces(iCount);
			}
		}
		return lsResult;
	}

	/**
	 * 提取字符串中的数字组成整形数据
	 * 
	 * @param no
	 * @return
	 */
	private Integer getNumberPart(String no) {
		String numberPart = "";
		for (int i = no.length() - 1; i >= 0; i--) {
			String s = no.substring(i, i + 1);
			if (this.checkIsInteger(s)) {
				numberPart = s + numberPart;
			} else {
				break;
			}
		}
		if (numberPart.trim().equals("")) {
			return 0;
		} else {
			return Integer.valueOf(numberPart);
		}
	}

	/**
	 * 取得编号前缀
	 * 
	 * @param code
	 *            编号
	 * @return 编号前缀
	 */
	private String getCodePrefix(String code) {
		String prefix = "";
		for (int i = code.length() - 1; i >= 0; i--) {
			String s = code.substring(i, i + 1);
			if (this.checkIsInteger(s)) {
			} else {
				prefix = code.substring(0, i + 1);
				break;
			}
		}
		return prefix;
	}

	/**
	 * 判断两个发票号码是不是连续的(从外汇局领用)
	 * 
	 * @param preFecavBill
	 *            以前的外汇核销单
	 * @param currentFecavBill
	 *            目前的外汇核销单
	 * @return 如果连续为true 反之为false
	 */
	private boolean checkOuterObtainFecavBillIsSequence(FecavBill preFecavBill,
			FecavBill currentFecavBill) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String preOuterObtainDate = dateFormat.format(preFecavBill
				.getOuterObtainDate());
		String nextOuterObtainDate = dateFormat.format(currentFecavBill
				.getOuterObtainDate());
		if (!preOuterObtainDate.equals(nextOuterObtainDate)) {
			return false;
		}
		if (!getCodePrefix(preFecavBill.getCode()).equals(
				getCodePrefix(currentFecavBill.getCode()))) {
			return false;
		}
		int preNum = getNumberPart(preFecavBill.getCode());
		int nextNum = getNumberPart(currentFecavBill.getCode());
		if (preNum != nextNum && preNum + 1 != nextNum) {
			return false;
		}
		if (!preFecavBill.getOuterObtain().equals(
				currentFecavBill.getOuterObtain())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断两个发票号码是不是连续的（使用）
	 * 
	 * @param preFecavBill
	 *            以前的外汇核销单
	 * @param currentFecavBill
	 *            目前的外汇核销单
	 * @return 如果连续为true 反之为false
	 */
	private boolean checkUsedFecavBillIsSequence(FecavBill preFecavBill,
			FecavBill currentFecavBill) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String preOuterObtainDate = dateFormat.format(preFecavBill
				.getUsedDate());
		String nextOuterObtainDate = dateFormat.format(currentFecavBill
				.getUsedDate());
		if (!preOuterObtainDate.equals(nextOuterObtainDate)) {
			return false;
		}
		if (!getCodePrefix(preFecavBill.getCode()).equals(
				getCodePrefix(currentFecavBill.getCode()))) {
			return false;
		}
		int preNum = getNumberPart(preFecavBill.getCode());
		int nextNum = getNumberPart(currentFecavBill.getCode());
		// int preNum = Integer.parseInt(preFecavBill.getCode());
		// int nextNum = Integer.parseInt(currentFecavBill.getCode());
		if (preNum != nextNum && preNum + 1 != nextNum) {
			return false;
		}
		if (!preFecavBill.getUsedMan().equals(currentFecavBill.getUsedMan())) {
			return false;
		}
		if (!preFecavBill.getInnerObtain().equals(
				currentFecavBill.getInnerObtain())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断两个发票号码是不是连续的（遗失作废情况）
	 * 
	 * @param preFecavBill
	 *            以前的外汇核销单
	 * @param currentFecavBill
	 *            目前的外汇核销单
	 * @return 如果连续为true 反之为false
	 */
	private boolean checkBlankOutFecavBillIsSequence(FecavBill preFecavBill,
			FecavBill currentFecavBill) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String preOuterObtainDate = dateFormat.format(preFecavBill
				.getBlankOutDate());
		String nextOuterObtainDate = dateFormat.format(currentFecavBill
				.getBlankOutDate());
		if (!preOuterObtainDate.equals(nextOuterObtainDate)) {
			return false;
		}
		if (!getCodePrefix(preFecavBill.getCode()).equals(
				getCodePrefix(currentFecavBill.getCode()))) {
			return false;
		}
		int preNum = getNumberPart(preFecavBill.getCode());
		int nextNum = getNumberPart(currentFecavBill.getCode());
		// int preNum = Integer.parseInt(preFecavBill.getCode());
		// int nextNum = Integer.parseInt(currentFecavBill.getCode());
		if (preNum != nextNum && preNum + 1 != nextNum) {
			return false;
		}
		if (!preFecavBill.getBlankOutMan().equals(
				currentFecavBill.getBlankOutMan())) {
			return false;
		}
		return true;
	}

	/**
	 * 删除进口白单，如果白单的进口报关单号在外汇核销单报关单号中存在，则不删除。
	 * 
	 * @param icd
	 *            ImpCustomsDeclaration 进口白单
	 * 
	 * @return 如果删除则为true 反之为false
	 */
	public boolean deleteImpCustomsDeclaration(ImpCustomsDeclaration icd) {
		List list = fecavDao.findImpCustomsDeclarationIsCancelCount(icd
				.getCustomsDeclarationCode());
		int m = (list.get(0) == null ? 0 : (Integer.valueOf(list.get(0)
				.toString()).intValue()));
		if (m > 0) {
			return false;
		} else
			this.fecavDao.delete(icd);
		return true;

	}

	/**
	 * 根据系统类型抓取正在执行的报关单
	 * 
	 * @param contracts
	 * @param beginDate
	 * @param endDate
	 * @param projectType
	 * @return
	 */
	public List findCustomsDeclarationByProjectType(List contracts,
			Date beginDate, Date endDate, Integer projectType) {
		List result = new ArrayList();
		if (projectType == ProjectType.DZSC) {
			result = findDzcscCancelAfterVerificationList(contracts, beginDate,
					endDate);
		} else if (projectType == ProjectType.BCS) {
			result = findBcsCancelAfterVerificationList(contracts, beginDate,
					endDate);
		} else if (projectType == ProjectType.BCUS) {
			result = findJbcusCancelAfterVerificationList(contracts, beginDate,
					endDate);
		}
		return result;
	}

	/**
	 * 核销单登记表
	 * 
	 * @param contract
	 *            手册通关备案表头
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return List 是DzscCancelAfterVerification型，存放统计报表中的核销单登记表资料
	 */
	public List findDzcscCancelAfterVerificationList(List contracts,
			Date beginDate, Date endDate) {
		HashMap<String, CancelAfterVerification> hm = new HashMap<String, CancelAfterVerification>();
		List<CancelAfterVerification> lsResult = new ArrayList<CancelAfterVerification>();
		for (int m = 0; m < contracts.size(); m++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(m);
			List list = this.fecavDao.findDzscCancelAfterVerificationList(
					contract.getEmsNo(), beginDate, endDate);

			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				if (commInfo.getBaseCustomsDeclaration().getAuthorizeFile() == null
						|| "".equals(commInfo.getBaseCustomsDeclaration()
								.getAuthorizeFile())) {
					continue;
				}
				List lsExg = this.fecavDao.findDzscEmsExgBillBySeqNum(contract
						.getId(), commInfo.getSerialNumber());
				if (lsExg.size() <= 0) {
					continue;
				}
				DzscEmsExgBill exg = (DzscEmsExgBill) lsExg.get(0);
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
			lsResult.add((CancelAfterVerification) hm.get(iterator.next()
					.toString()));
		}
		return lsResult;
	}

	/**
	 * 获取bcs核销单登记表
	 * 
	 * @param contracts
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findBcsCancelAfterVerificationList(List contracts,
			Date beginDate, Date endDate) {
		List<CancelAfterVerification> lsResult = new ArrayList<CancelAfterVerification>();

		HashMap<String, CancelAfterVerification> hm = new HashMap<String, CancelAfterVerification>();
		for (int m = 0; m < contracts.size(); m++) {
			Contract contract = (Contract) contracts.get(m);
			List list = this.fecavDao.findBcsCancelAfterVerificationList(
					contract.getEmsNo(), beginDate, endDate);
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				if (commInfo.getBaseCustomsDeclaration().getAuthorizeFile() == null
						|| "".equals(commInfo.getBaseCustomsDeclaration()
								.getAuthorizeFile())) {
					continue;
				}
				List lsExg = this.fecavDao.findBcsContractExgBySeqNum(contract
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
						- CommonUtils
								.getDoubleExceptNull(cav.getProcessPrice()));
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
	 * 获取bcus核销单登记表
	 * 
	 * @param contracts
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findJbcusCancelAfterVerificationList(List contracts,
			Date beginDate, Date endDate) {
		List<CancelAfterVerification> lsResult = new ArrayList<CancelAfterVerification>();
		HashMap<String, CancelAfterVerification> hm = new HashMap<String, CancelAfterVerification>();
		for (int m = 0; m < contracts.size(); m++) {
			EmsHeadH2k contract = (EmsHeadH2k) contracts.get(m);
			List list = this.fecavDao.findJbcusCancelAfterVerificationList(
					contract.getEmsNo(), beginDate, endDate);
			for (int i = 0; i < list.size(); i++) {
				BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
						.get(i);
				if (commInfo.getBaseCustomsDeclaration().getAuthorizeFile() == null
						|| "".equals(commInfo.getBaseCustomsDeclaration()
								.getAuthorizeFile())) {
					continue;
				}
				List lsExg = this.fecavDao.findJbcusContractExgBySeqNum(
						contract.getEmsNo(),
						commInfo.getSerialNumber() == null ? "" : commInfo
								.getSerialNumber().toString());
				if (lsExg.size() <= 0) {
					continue;
				}
				EmsHeadH2kExg exg = (EmsHeadH2kExg) lsExg.get(0);
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
				// cav.setProcessPrice(CommonUtils.getDoubleExceptNull(cav
				// .getProcessPrice())
				// + CommonUtils.getDoubleExceptNull(exg
				// .getProcessUnitPrice())
				// * CommonUtils.getDoubleExceptNull(commInfo
				// .getCommAmount()));
				cav.setMaterialPrice(CommonUtils.getDoubleExceptNull(cav
						.getTotalPrice())
						- CommonUtils
								.getDoubleExceptNull(cav.getProcessPrice()));
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
	 * 查找是否有汇票已被冲消
	 * 
	 * @param List
	 *            已冲销的汇票List
	 * @return Boolean
	 */
	public Boolean isBillOfExchangeIsUer(List list) {
		BillOfExchange billOfExchange;
		for (int i = 0; i < list.size(); i++) {
			billOfExchange = (BillOfExchange) list.get(i);
			List list1 = this.fecavDao.findStrikeBillOfExchange(billOfExchange);
			if (!list1.isEmpty())
				return true;
		}
		return false;
	}

	/**
	 * 为所有外汇核销单设置海关关区
	 * 
	 * @param customs
	 * @return
	 */
	public Boolean findCustomsInofFecavBill(Customs customs) {
		List result = new ArrayList();
		List list = fecavDao.findCustomsInofFecavBill();
		if (list == null || list.size() < 0) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			FecavBill c = (FecavBill) list.get(i);
			c.setImpExpCIQ(customs);
			this.fecavDao.saveOrUpdate(c);
		}
		return true;
	}
	/**
	 * 保存从内部领单中获得的核销单
	 * @param list
	 * @return
	 */
	public List<FecavBill> addFecavBill(
			List<FecavBill> list) {
		List<FecavBill> lsResult = new ArrayList<FecavBill>();
		for (FecavBill temp : list) {
		temp.setBillState(FecavState.USED);
			this.fecavDao.saveFecavBill(temp);
			lsResult.add(temp);
		}
		return lsResult;
	}


}
