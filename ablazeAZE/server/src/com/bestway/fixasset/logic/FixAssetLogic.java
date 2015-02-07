package com.bestway.fixasset.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.AgreementGroupState;
import com.bestway.common.constant.AgreementState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.fixasset.dao.FixAssetDao;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.fixasset.entity.AgreementGroupDetail;
import com.bestway.fixasset.entity.AgreementGroupHead;
import com.bestway.fixasset.entity.AgreementInvoiceDetail;
import com.bestway.fixasset.entity.AgreementInvoiceHead;
import com.bestway.fixasset.entity.AgreementWarDetail;
import com.bestway.fixasset.entity.AgreementWarHead;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixasset.entity.DeleteAgreementCommInfo;
import com.bestway.fixasset.entity.DeleteAgreementCommInfoHistory;
import com.bestway.fixasset.entity.DutyCertDetail;
import com.bestway.fixasset.entity.DutyCertHead;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixasset.entity.FixassetLocationChangeBillInfo;
import com.bestway.fixasset.entity.FixassetLocationResultInfo;
import com.bestway.fixasset.entity.ImpAgreementCommInfo;
import com.bestway.fixasset.entity.ImpAgreementCommInfoHistory;
import com.bestway.fixasset.entity.TempCustomsDeclarationFixasset;
import com.bestway.fixasset.entity.TempCustomsFixAssetRemainMoney;
import com.bestway.fixasset.entity.TempFactoryFixAssetRemainMoney;
import com.bestway.fixasset.entity.TempFixassetLocationChangeInfo;
import com.bestway.fixasset.entity.TempFixassetStatusInfo;
import com.bestway.fixasset.entity.TempGroupImpCommInfo;
import com.bestway.fixasset.entity.TempImportAgreementCommInfo;
import com.bestway.fixasset.entity.TempOtherBillInfo;
import com.bestway.fixtureonorder.entity.FixtureContractItems;

public class FixAssetLogic {
	private FixAssetDao fixAssetDao;

	public FixAssetDao getFixAssetDao() {
		return fixAssetDao;
	}

	public void setFixAssetDao(FixAssetDao fixAssetDao) {
		this.fixAssetDao = fixAssetDao;
	}

	/**
	 * 新增协议批文
	 * 
	 * @return
	 */
	public Agreement addAgreement() {
		Agreement agreement = new Agreement();
		Company company = (Company) CommonUtils.getCompany();
		agreement.setTradeCode(company.getBuCode());
		agreement.setTradeName(company.getBuName());
		agreement.setMachCode(company.getCode());
		agreement.setMachName(company.getName());
		agreement.setChangedTimes(0);
		agreement.setDeclareState(AgreementState.ADDED);
		this.fixAssetDao.saveAgreement(agreement);
		return agreement;
	}

	/**
	 * 新增协议批文商品明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addAgreementCommInfo(Agreement agreement, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			AgreementCommInfo commInfo = new AgreementCommInfo();
			commInfo.setAgreement(agreement);
			commInfo.setComplex(complex);
			commInfo.setCommName(complex.getName());
			commInfo.setUnit(complex.getFirstUnit());
			commInfo.setStateMark(AgreementState.ADDED);
			commInfo.setMainNo(this.fixAssetDao
					.findMaxAgreementCommInfoNo(agreement) + 1);
			this.fixAssetDao.saveAgreementCommInfo(commInfo);
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 删除批文协议
	 * 
	 * @param agreement
	 */
	public void deleteAgreement(Agreement agreement) {
		List list = this.fixAssetDao.findAgreementGroupHeadByState(agreement,
				AgreementGroupState.CERT_HANDIN);
		if (list.size() > 0) {
			throw new RuntimeException("此批文已经有递单，所以不能删除");
		}
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findAgreementGroupHead(agreement));
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findAgreementGroupDetailNotHandIn(agreement));
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findImpAgreementCommInfo(agreement));
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findDeleteAgreementCommInfo(agreement));
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findAgreementCommInfo(agreement));
		this.fixAssetDao.deleteAgreement(agreement);
	}

	/**
	 * 删除批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public List deleteAgreementCommInfo(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			AgreementCommInfo commInfo = (AgreementCommInfo) list.get(i);
			if (commInfo.getStateMark() == AgreementState.ADDED) {
				this.fixAssetDao.deleteAgreementCommInfo(commInfo);
				lsResult.add(commInfo);
			}
		}
		return lsResult;
	}

	/**
	 * 保存批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void saveAgreementCommInfo(AgreementCommInfo commInfo) {
		this.fixAssetDao.saveAgreementCommInfo(commInfo);
		this.writeBackFixassetMoney(commInfo.getAgreement());
	}

	/**
	 * 保存取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void saveDeleteAgreementCommInfo(DeleteAgreementCommInfo delCommInfo) {
		AgreementCommInfo commInfo = this.fixAssetDao
				.findAgreementCommInfoByNo(delCommInfo.getAgreement(),
						delCommInfo.getMainNo());
		if (delCommInfo.getAmount() > commInfo.getAmount()) {
			throw new RuntimeException("取消的序号为" + delCommInfo.getMainNo()
					+ "的设备的数量不能大于其备案数量");
		}
		this.fixAssetDao.saveDeleteAgreementCommInfo(delCommInfo);
	}

	/**
	 * 保存新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void saveImpAgreementCommInfo(ImpAgreementCommInfo commInfo) {
		this.fixAssetDao.saveImpAgreementCommInfo(commInfo);
		if (!checkAgreementMoneyOutRange(commInfo.getAgreement())) {
			throw new RuntimeException("新增的设备金额已超出范围");
		}
	}

	/**
	 * 协议备案
	 * 
	 * @param agreement
	 */
	public void agreementPutOnRecord(Agreement agreement) {
		agreement.setDeclareState(AgreementState.PUT_ON_RECORD);
		this.fixAssetDao.saveAgreement(agreement);
		List lsCommInfo = this.fixAssetDao.findAgreementCommInfo(agreement);
		for (int i = 0; i < lsCommInfo.size(); i++) {
			AgreementCommInfo commInfo = (AgreementCommInfo) lsCommInfo.get(i);
			commInfo.setStateMark(AgreementState.PUT_ON_RECORD);
			this.fixAssetDao.saveAgreementCommInfo(commInfo);
		}
	}

	/**
	 * 删除取消的协议批文商品明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addDeleteAgreementCommInfo(Agreement agreement, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			AgreementCommInfo commInfo = (AgreementCommInfo) list.get(i);
			DeleteAgreementCommInfo deleteCommInfo = new DeleteAgreementCommInfo();
			try {
				PropertyUtils.copyProperties(deleteCommInfo, commInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			deleteCommInfo.setId(null);
			// deleteCommInfo.setUnitPrice(0.0);
			// deleteCommInfo.setTotalPrice(0.0);
			this.fixAssetDao.saveDeleteAgreementCommInfo(deleteCommInfo);
			lsResult.add(deleteCommInfo);
		}
		return lsResult;
	}

	/**
	 * 进口备案设备明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addPORAgreementCommInfo(Agreement agreement, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			AgreementCommInfo commInfo = (AgreementCommInfo) list.get(i);
			ImpAgreementCommInfo impCommInfo = new ImpAgreementCommInfo();
			try {
				PropertyUtils.copyProperties(impCommInfo, commInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			impCommInfo.setId(null);
			impCommInfo.setStateMark(AgreementState.PUT_ON_RECORD);
			this.fixAssetDao.saveImpAgreementCommInfo(impCommInfo);
			lsResult.add(impCommInfo);
		}
		return lsResult;
	}

	/**
	 * 进口新设备明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addNewAgreementCommInfo(Agreement agreement, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			ImpAgreementCommInfo commInfo = new ImpAgreementCommInfo();
			commInfo.setAgreement(agreement);
			commInfo.setComplex(complex);
			commInfo.setCommName(complex.getName());
			commInfo.setUnit(complex.getFirstUnit());
			commInfo.setStateMark(AgreementState.ADDED);
			int maxNo = this.fixAssetDao.findMaxAgreementCommInfoNo(agreement);
			int maxImpNo = this.fixAssetDao
					.findMaxImpAgreementCommInfoNo(agreement);
			commInfo.setMainNo((maxImpNo > maxNo ? maxImpNo : maxNo) + 1);
			this.fixAssetDao.saveImpAgreementCommInfo(commInfo);
			lsResult.add(commInfo);
		}
		return lsResult;
	}

	/**
	 * 设备物品集结
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List groupImpAgreementCommInfo(Agreement agreement, List list,
			String groupNo) {
		List lsResult = new ArrayList();
		AgreementGroupHead groupHead = this.fixAssetDao
				.findAgreementGroupHeadByNo(agreement, groupNo);
		if (groupHead == null) {
			groupHead = new AgreementGroupHead();
			groupHead.setAgreement(agreement);
			groupHead.setGroupNo(groupNo);
			groupHead.setStateMark(AgreementGroupState.RECEIVE);
			groupHead.setCreateDate(new Date());
			String maxNo = this.fixAssetDao.findAgreementGroupMaxInvoiceNo();
			groupHead.setInvoiceNo(maxNo);
			groupHead.setCertNo(maxNo);
			groupHead.setWarNo(maxNo);
			this.fixAssetDao.saveAgreementGroupHead(groupHead);
		}
		for (int i = 0; i < list.size(); i++) {
			TempGroupImpCommInfo tempCommInfo = (TempGroupImpCommInfo) list
					.get(i);
			AgreementGroupDetail groupDetail = new AgreementGroupDetail();
			groupDetail.setMainNo(tempCommInfo.getMainNo());
			groupDetail.setGroupHead(groupHead);
			groupDetail.setComplex(tempCommInfo.getComplex());
			groupDetail.setCommName(tempCommInfo.getCommName());
			groupDetail.setCommSpec(tempCommInfo.getCommSpec());
			groupDetail.setUnit(tempCommInfo.getUnit());
			groupDetail.setUnitPrice(tempCommInfo.getUnitPrice());
			groupDetail.setAmount(tempCommInfo.getAllotAmout());
			groupDetail.setTotalPrice(tempCommInfo.getAllotTotalPrice());
			groupDetail.setCountry(tempCommInfo.getCountry());
			this.fixAssetDao.saveAgreementGroupDetail(groupDetail);
			ImpAgreementCommInfo impCommInfo = this.fixAssetDao
					.findImpAgreementCommInfoByNo(agreement, tempCommInfo
							.getMainNo());
			if (impCommInfo != null) {
				impCommInfo.setAllotAmout(CommonUtils
						.getDoubleExceptNull(impCommInfo.getAllotAmout())
						+ CommonUtils.getDoubleExceptNull(tempCommInfo
								.getAllotAmout()));
				impCommInfo.setAllotTotalPrice(CommonUtils
						.getDoubleExceptNull(impCommInfo.getAllotAmout())
						* CommonUtils.getDoubleExceptNull(impCommInfo
								.getUnitPrice()));
				this.fixAssetDao.saveImpAgreementCommInfo(impCommInfo);
			}
			lsResult.add(groupDetail);
		}
		return lsResult;
	}

	/**
	 * 取消设备物品集结
	 * 
	 * @param list
	 */
	public void removeGroupAgreementCommInfo(List list) {
		List lsGroupHead = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			AgreementGroupDetail groupDetail = (AgreementGroupDetail) list
					.get(i);
			if (!lsGroupHead.contains(groupDetail.getGroupHead())) {
				lsGroupHead.add(groupDetail.getGroupHead());
			}
			ImpAgreementCommInfo impCommInfo = this.fixAssetDao
					.findImpAgreementCommInfoByNo(groupDetail.getGroupHead()
							.getAgreement(), groupDetail.getMainNo());
			if (impCommInfo != null) {
				impCommInfo.setAllotAmout(CommonUtils
						.getDoubleExceptNull(impCommInfo.getAllotAmout())
						- CommonUtils.getDoubleExceptNull(groupDetail
								.getAmount()));
				impCommInfo.setAllotTotalPrice(CommonUtils
						.getDoubleExceptNull(impCommInfo.getAllotAmout())
						* CommonUtils.getDoubleExceptNull(impCommInfo
								.getUnitPrice()));
				this.fixAssetDao.saveImpAgreementCommInfo(impCommInfo);
			}
			this.fixAssetDao.deleteAgreementGroupDetail(groupDetail);
		}
		for (int i = 0; i < lsGroupHead.size(); i++) {
			AgreementGroupHead groupHead = (AgreementGroupHead) lsGroupHead
					.get(i);
			if (this.fixAssetDao.findAgreementGroupDetail(groupHead).size() <= 0) {
				this.fixAssetDao.deleteAgreementGroupHead(groupHead);
			}
			String scancEmsNo = groupHead.getAgreement().getSancEmsNo();
			String invoiceNo = groupHead.getInvoiceNo();
			String warNo = groupHead.getWarNo();
			String certNo = groupHead.getCertNo();
			AgreementInvoiceHead invoiceHead = this.fixAssetDao
					.findAgreementInvoiceHeadByNo(scancEmsNo, invoiceNo);
			if (invoiceHead != null) {
				this.deleteAgreementInvoiceHead(invoiceHead);
			}
			AgreementWarHead warHead = this.fixAssetDao
					.findAgreementWarHeadByNo(scancEmsNo, warNo);
			if (warHead != null) {
				this.deleteAgreementWarHead(warHead);
			}
			DutyCertHead certHead = this.fixAssetDao.findDutyCertHeadByNo(
					scancEmsNo, certNo);
			if (certHead != null) {
				this.deleteDutyCertHead(certHead);
			}
		}
	}

	/**
	 * 删除发票
	 * 
	 * @param invoiceHead
	 */
	public void deleteAgreementInvoiceHead(AgreementInvoiceHead invoiceHead) {
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findAgreementInvoiceDetail(invoiceHead));
		this.fixAssetDao.deleteAgreementInvoiceHead(invoiceHead);
	}

	/**
	 * 删除新设备保证书
	 * 
	 * @param warHead
	 */
	public void deleteAgreementWarHead(AgreementWarHead warHead) {
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findAgreementWarDetail(warHead));
		this.fixAssetDao.deleteAgreementWarHead(warHead);
	}

	/**
	 * 删除征免税证
	 * 
	 * @param dutyCertHead
	 */
	public void deleteDutyCertHead(DutyCertHead dutyCertHead) {
		this.fixAssetDao.deleteAll(this.fixAssetDao
				.findDutyCertDetail(dutyCertHead));
		this.fixAssetDao.deleteDutyCertHead(dutyCertHead);
	}

	/**
	 * 自动产生发票，保证书，和征免税证明
	 * 
	 * @param agreement
	 */
	public void makeOtherBill(Agreement agreement, boolean isMakeInvoice,
			boolean isMakeWar, boolean isMakeDutyCert) {
		List list = this.fixAssetDao.findAgreementGroupHeadByState(agreement,
				AgreementGroupState.RECEIVE);
		if (isMakeInvoice) {
			makeInvoiceBill(list);
		}
		if (isMakeWar) {
			makeWarBill(list);
		}
		if (isMakeDutyCert) {
			makeDutyCertBill(list);
		}
	}

	/**
	 * 生成发票单据
	 * 
	 */
	private void makeInvoiceBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			AgreementGroupHead groupHead = (AgreementGroupHead) list.get(i);
			if (this.fixAssetDao.findAgreementInvoiceHeadByNo(groupHead
					.getAgreement().getSancEmsNo(), groupHead.getInvoiceNo()) != null) {
				continue;
			}
			AgreementInvoiceHead invoiceHead = new AgreementInvoiceHead();
			invoiceHead.setSancEmsNo(groupHead.getAgreement().getSancEmsNo());
			invoiceHead.setInvoiceCode(groupHead.getInvoiceNo());
			invoiceHead.setInvoiceDate(new Date());
			this.fixAssetDao.saveAgreementInvoiceHead(invoiceHead);
			List lsDetail = this.fixAssetDao
					.findAgreementGroupDetail(groupHead);
			for (int j = 0; j < lsDetail.size(); j++) {
				AgreementGroupDetail groupDetail = (AgreementGroupDetail) lsDetail
						.get(j);
				AgreementInvoiceDetail invoiceDetail = new AgreementInvoiceDetail();
				invoiceDetail.setInvoiceHead(invoiceHead);
				invoiceDetail.setMainNo(groupDetail.getMainNo());
				invoiceDetail.setComplex(groupDetail.getComplex());
				invoiceDetail.setCommName(groupDetail.getCommName());
				invoiceDetail.setCommSpec(groupDetail.getCommSpec());
				invoiceDetail.setUnit(groupDetail.getUnit());
				invoiceDetail.setUnitPrice(groupDetail.getUnitPrice());
				invoiceDetail.setAmount(groupDetail.getAmount());
				invoiceDetail.setTotalPrice(groupDetail.getTotalPrice());
				this.fixAssetDao.saveAgreementInvoiceDetail(invoiceDetail);
			}
		}
	}

	/**
	 * 生成全新设备保证书
	 * 
	 */
	private void makeWarBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			AgreementGroupHead groupHead = (AgreementGroupHead) list.get(i);
			if (this.fixAssetDao.findAgreementWarHeadByNo(groupHead
					.getAgreement().getSancEmsNo(), groupHead.getWarNo()) != null) {
				continue;
			}
			AgreementWarHead warHead = new AgreementWarHead();
			warHead.setSancEmsNo(groupHead.getAgreement().getSancEmsNo());
			warHead.setWarNo(groupHead.getWarNo());
			this.fixAssetDao.saveAgreementWarHead(warHead);
			List lsDetail = this.fixAssetDao
					.findAgreementGroupDetail(groupHead);
			for (int j = 0; j < lsDetail.size(); j++) {
				AgreementGroupDetail groupDetail = (AgreementGroupDetail) lsDetail
						.get(j);
				AgreementWarDetail warDetail = new AgreementWarDetail();
				warDetail.setWarHead(warHead);
				warDetail.setMainNo(groupDetail.getMainNo());
				warDetail.setComplex(groupDetail.getComplex());
				warDetail.setCommName(groupDetail.getCommName());
				warDetail.setCommSpec(groupDetail.getCommSpec());
				warDetail.setUnit(groupDetail.getUnit());
				warDetail.setUnitPrice(groupDetail.getUnitPrice());
				warDetail.setAmount(groupDetail.getAmount());
				warDetail.setTotalPrice(groupDetail.getTotalPrice());
				warDetail.setCountry(groupDetail.getCountry());
				this.fixAssetDao.saveAgreementWarDetail(warDetail);
			}
		}
	}

	/**
	 * 生成征免税证明
	 * 
	 */
	private void makeDutyCertBill(List list) {
		for (int i = 0; i < list.size(); i++) {
			AgreementGroupHead groupHead = (AgreementGroupHead) list.get(i);
			if (this.fixAssetDao.findDutyCertHeadByNo(groupHead.getAgreement()
					.getSancEmsNo(), groupHead.getCertNo()) != null) {
				continue;
			}
			DutyCertHead certHead = new DutyCertHead();
			certHead.setSancEmsNo(groupHead.getAgreement().getSancEmsNo());
			certHead.setCertNo(groupHead.getWarNo());
			this.fixAssetDao.saveDutyCertHead(certHead);
			List lsDetail = this.fixAssetDao
					.findAgreementGroupDetail(groupHead);
			for (int j = 0; j < lsDetail.size(); j++) {
				AgreementGroupDetail groupDetail = (AgreementGroupDetail) lsDetail
						.get(j);
				DutyCertDetail certDetail = new DutyCertDetail();
				certDetail.setCertHead(certHead);
				certDetail.setMainNo(groupDetail.getMainNo());
				certDetail.setComplex(groupDetail.getComplex());
				certDetail.setCommName(groupDetail.getCommName());
				certDetail.setCommSpec(groupDetail.getCommSpec());
				certDetail.setUnit(groupDetail.getUnit());
				certDetail.setUnitPrice(groupDetail.getUnitPrice());
				certDetail.setAmount(groupDetail.getAmount());
				certDetail.setTotalPrice(groupDetail.getTotalPrice());
				certDetail.setCountry(groupDetail.getCountry());
				this.fixAssetDao.saveDutyCertDetail(certDetail);
			}
		}
	}

	/**
	 * 向海关递单
	 * 
	 * @param agreement
	 */
	public void handInBill(Agreement agreement, List lsGroup) {
		if (this.fixAssetDao.findImpCommInfoNotGroup(agreement).size() > 0) {
			throw new RuntimeException("分组集结尚未完成，不能递单");
		}
		List lsImpCommInfo = this.fixAssetDao
				.findImpAgreementCommInfo(agreement);
		for (int i = 0; i < lsImpCommInfo.size(); i++) {
			ImpAgreementCommInfo impCommInfo = (ImpAgreementCommInfo) lsImpCommInfo
					.get(i);
			AgreementCommInfo commInfo = this.fixAssetDao
					.findAgreementCommInfoByNo(agreement, impCommInfo
							.getMainNo());
			if (commInfo == null) {
				commInfo = new AgreementCommInfo();
				commInfo.setAgreement(agreement);
				commInfo.setMainNo(impCommInfo.getMainNo());
				commInfo.setComplex(impCommInfo.getComplex());
				commInfo.setCommName(impCommInfo.getCommName());
				commInfo.setCommSpec(impCommInfo.getCommSpec());
				commInfo.setUnit(impCommInfo.getUnit());
				commInfo.setUnitPrice(impCommInfo.getUnitPrice());
				commInfo.setAmount(impCommInfo.getAmount());
				commInfo.setTotalPrice(impCommInfo.getTotalPrice());
				commInfo.setCountry(impCommInfo.getCountry());
				commInfo.setImpedAmount(impCommInfo.getAmount());
				commInfo.setImpedTotalPrice(impCommInfo.getTotalPrice());
				commInfo.setStateMark(AgreementState.EXECUTING);
			} else {
				commInfo.setImpedAmount(CommonUtils
						.getDoubleExceptNull(commInfo.getImpedAmount())
						+ CommonUtils.getDoubleExceptNull(impCommInfo
								.getAmount()));
				commInfo.setImpedTotalPrice(CommonUtils
						.getDoubleExceptNull(commInfo.getImpedAmount())
						* CommonUtils.getDoubleExceptNull(impCommInfo
								.getUnitPrice()));
			}
			this.fixAssetDao.saveAgreementCommInfo(commInfo);
			this.fixAssetDao.deleteImpAgreementCommInfo(impCommInfo);
		}
		agreement.setDeclareState(AgreementState.EXECUTING);
		this.fixAssetDao.saveAgreement(agreement);
		List lsDelCommInfo = this.fixAssetDao
				.findDeleteAgreementCommInfo(agreement);
		for (int i = 0; i < lsDelCommInfo.size(); i++) {
			DeleteAgreementCommInfo delCommInfo = (DeleteAgreementCommInfo) lsDelCommInfo
					.get(i);
			AgreementCommInfo commInfo = this.fixAssetDao
					.findAgreementCommInfoByNo(agreement, delCommInfo
							.getMainNo());
			if (commInfo != null) {
				commInfo.setAmount(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						- CommonUtils.getDoubleExceptNull(delCommInfo
								.getAmount()));
				commInfo.setTotalPrice(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
				this.fixAssetDao.saveAgreementCommInfo(commInfo);
			}
			this.fixAssetDao.deleteDeleteAgreementCommInfo(delCommInfo);
		}
		int changedTimes = this.fixAssetDao
				.findAgreementGroupMaxChangedTimes(agreement);
		// List lsGroup = this.fixAssetDao.findAgreementGroupHead(agreement);
		for (int i = 0; i < lsGroup.size(); i++) {
			AgreementGroupHead groupHead = (AgreementGroupHead) lsGroup.get(i);
			String groupNo = this.fixAssetDao.findAgreementGroupMaxGroupNo();
			groupHead.setGroupNo(groupNo);
			groupHead.setStateMark(AgreementGroupState.CERT_HANDIN);
			if (groupHead.getChangedTimes() == null) {
				groupHead.setChangedTimes(changedTimes);
			} else {
				changedTimes = groupHead.getChangedTimes();
			}
			this.fixAssetDao.saveAgreementGroupHead(groupHead);
		}
		for (int i = 0; i < lsImpCommInfo.size(); i++) {
			ImpAgreementCommInfo impCommInfo = (ImpAgreementCommInfo) lsImpCommInfo
					.get(i);
			ImpAgreementCommInfoHistory history = new ImpAgreementCommInfoHistory();
			try {
				PropertyUtils.copyProperties(history, impCommInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			history.setId(null);
			history.setChangedTimes(changedTimes);
			this.fixAssetDao.saveOrUpdate(history);
		}
		for (int i = 0; i < lsDelCommInfo.size(); i++) {
			DeleteAgreementCommInfo delCommInfo = (DeleteAgreementCommInfo) lsDelCommInfo
					.get(i);
			DeleteAgreementCommInfoHistory history = new DeleteAgreementCommInfoHistory();
			try {
				PropertyUtils.copyProperties(history, delCommInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			history.setId(null);
			history.setChangedTimes(changedTimes);
			this.fixAssetDao.saveOrUpdate(history);
		}
		double deleteMoney = this.fixAssetDao
				.findDeleteAgreementCommInfoMoney(agreement);
		double addMoney = this.fixAssetDao
				.findAddAgreementCommInfoMoney(agreement);
		agreement = this.fixAssetDao.findAgreement(agreement);
		agreement.setChangeRemainMoney(CommonUtils
				.getDoubleExceptNull(agreement.getChangeRemainMoney())
				+ (deleteMoney - addMoney));
		this.fixAssetDao.saveAgreement(agreement);
	}

	/**
	 * 取消递单
	 * 
	 * @param agreement
	 */
	public void cancelHandInBill(Agreement agreement, Integer changedTimes) {
		List lsGroup = this.fixAssetDao.findAgreementGroupHead(agreement,
				changedTimes);
		for (int i = 0; i < lsGroup.size(); i++) {
			AgreementGroupHead groupHead = (AgreementGroupHead) lsGroup.get(i);
			// List lsGroupDetail = this.fixAssetDao
			// .findAgreementGroupDetail(groupHead);
			// for (int i = 0; i < lsGroupDetail.size(); i++) {
			// AgreementGroupDetail groupDetail = (AgreementGroupDetail)
			// lsGroupDetail
			// .get(i);
			// AgreementCommInfo commInfo = this.fixAssetDao
			// .findAgreementCommInfoByNo(groupHead.getAgreement(),
			// groupDetail.getMainNo());
			// if (commInfo != null) {
			// commInfo.setImpedAmount(CommonUtils
			// .getDoubleExceptNull(commInfo.getImpedAmount())
			// - CommonUtils.getDoubleExceptNull(groupDetail
			// .getAmount()));
			// commInfo.setImpedTotalPrice(CommonUtils
			// .getDoubleExceptNull(commInfo.getImpedAmount())
			// * CommonUtils.getDoubleExceptNull(groupDetail
			// .getTotalPrice()));
			// this.fixAssetDao.saveAgreementCommInfo(commInfo);
			// }
			// ImpAgreementCommInfo impCommInfo = this.fixAssetDao
			// .findImpAgreementCommInfoByNo(groupHead.getAgreement(),
			// groupDetail.getMainNo());
			// if (impCommInfo == null) {
			// impCommInfo = new ImpAgreementCommInfo();
			// impCommInfo.setAgreement(groupDetail.getGroupHead()
			// .getAgreement());
			// impCommInfo.setMainNo(groupDetail.getMainNo());
			// impCommInfo.setComplex(groupDetail.getComplex());
			// impCommInfo.setCommName(groupDetail.getCommName());
			// impCommInfo.setCommSpec(groupDetail.getCommSpec());
			// impCommInfo.setUnit(groupDetail.getUnit());
			// impCommInfo.setCountry(groupDetail.getCountry());
			// impCommInfo.setUnitPrice(groupDetail.getUnitPrice());
			// impCommInfo.setAmount(groupDetail.getAmount());
			// impCommInfo.setAllotAmout(groupDetail.getAmount());
			// impCommInfo.setTotalPrice(CommonUtils
			// .getDoubleExceptNull(impCommInfo.getAmount())
			// * CommonUtils.getDoubleExceptNull(impCommInfo
			// .getUnitPrice()));
			// impCommInfo.setAllotTotalPrice(CommonUtils
			// .getDoubleExceptNull(impCommInfo.getAllotAmout())
			// * CommonUtils.getDoubleExceptNull(impCommInfo
			// .getUnitPrice()));
			// impCommInfo.setStateMark(AgreementState.PUT_ON_RECORD);
			// } else {
			// impCommInfo.setAmount(CommonUtils
			// .getDoubleExceptNull(groupDetail.getAmount())
			// + CommonUtils.getDoubleExceptNull(impCommInfo
			// .getAmount()));
			// impCommInfo.setAllotAmout(CommonUtils
			// .getDoubleExceptNull(groupDetail.getAmount())
			// + CommonUtils.getDoubleExceptNull(impCommInfo
			// .getAllotAmout()));
			// impCommInfo.setTotalPrice(CommonUtils
			// .getDoubleExceptNull(impCommInfo.getAmount())
			// * CommonUtils.getDoubleExceptNull(impCommInfo
			// .getUnitPrice()));
			// impCommInfo.setAllotTotalPrice(CommonUtils
			// .getDoubleExceptNull(impCommInfo.getAllotAmout())
			// * CommonUtils.getDoubleExceptNull(impCommInfo
			// .getUnitPrice()));
			// }
			// this.fixAssetDao.saveImpAgreementCommInfo(impCommInfo);
			// }
			groupHead.setStateMark(AgreementGroupState.RECEIVE);
			this.fixAssetDao.saveAgreementGroupHead(groupHead);
		}
		List lsImpCommInfoHistory = this.fixAssetDao
				.findImpAgreementCommInfoHistory(agreement, changedTimes);
		for (int i = 0; i < lsImpCommInfoHistory.size(); i++) {
			ImpAgreementCommInfoHistory history = (ImpAgreementCommInfoHistory) lsImpCommInfoHistory
					.get(i);
			AgreementCommInfo commInfo = this.fixAssetDao
					.findAgreementCommInfoByNo(history.getAgreement(), history
							.getMainNo());
			if (commInfo != null) {
				commInfo.setImpedAmount(CommonUtils
						.getDoubleExceptNull(commInfo.getImpedAmount())
						- CommonUtils.getDoubleExceptNull(history.getAmount()));
				commInfo.setImpedTotalPrice(CommonUtils
						.getDoubleExceptNull(commInfo.getImpedAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
				this.fixAssetDao.saveAgreementCommInfo(commInfo);
			}
			ImpAgreementCommInfo impCommInfo = new ImpAgreementCommInfo();
			try {
				PropertyUtils.copyProperties(impCommInfo, history);
			} catch (Exception e) {
				e.printStackTrace();
			}
			impCommInfo.setId(null);
			this.fixAssetDao.saveOrUpdate(impCommInfo);
			this.fixAssetDao.delete(history);
		}
		List lsDelCommInfoHistory = this.fixAssetDao
				.findDeleteAgreementCommInfoHistory(agreement, changedTimes);
		for (int i = 0; i < lsDelCommInfoHistory.size(); i++) {
			DeleteAgreementCommInfoHistory history = (DeleteAgreementCommInfoHistory) lsDelCommInfoHistory
					.get(i);
			AgreementCommInfo commInfo = this.fixAssetDao
					.findAgreementCommInfoByNo(history.getAgreement(), history
							.getMainNo());
			if (commInfo != null) {
				commInfo.setAmount(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						+ CommonUtils.getDoubleExceptNull(history.getAmount()));
				commInfo.setTotalPrice(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
				this.fixAssetDao.saveAgreementCommInfo(commInfo);
			}
			DeleteAgreementCommInfo delCommInfo = new DeleteAgreementCommInfo();
			try {
				PropertyUtils.copyProperties(delCommInfo, history);
			} catch (Exception e) {
				e.printStackTrace();
			}
			delCommInfo.setId(null);
			this.fixAssetDao.saveOrUpdate(delCommInfo);
			this.fixAssetDao.delete(history);
		}
		double deleteMoney = this.fixAssetDao
				.findDeleteAgreementCommInfoMoney(agreement);
		double addMoney = this.fixAssetDao
				.findAddAgreementCommInfoMoney(agreement);
		agreement = this.fixAssetDao.findAgreement(agreement);
		agreement.setChangeRemainMoney(CommonUtils
				.getDoubleExceptNull(agreement.getChangeRemainMoney())
				- (deleteMoney - addMoney));
		this.fixAssetDao.saveAgreement(agreement);
	}

	/**
	 * 保存批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	public void saveAgreementInvoiceHead(Agreement agreement,
			AgreementInvoiceHead invoiceHead) {
		AgreementInvoiceHead oldInvoiceHead = (AgreementInvoiceHead) this.fixAssetDao
				.get(AgreementInvoiceHead.class, invoiceHead.getId());
		if (oldInvoiceHead != null) {
			AgreementGroupHead groupHead = this.fixAssetDao
					.findAgreementGroupHeadByInvoiceNo(agreement,
							oldInvoiceHead.getInvoiceCode());
			if (groupHead != null) {
				groupHead.setInvoiceNo(invoiceHead.getInvoiceCode());
				this.fixAssetDao.saveAgreementGroupHead(groupHead);
			}
		}
		this.fixAssetDao.saveAgreementInvoiceHead(invoiceHead);
	}

	/**
	 * 保存批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	public void saveAgreementWarHead(Agreement agreement,
			AgreementWarHead warHead) {
		AgreementWarHead oldWarHead = (AgreementWarHead) this.fixAssetDao.get(
				AgreementWarHead.class, warHead.getId());
		if (oldWarHead != null) {
			AgreementGroupHead groupHead = this.fixAssetDao
					.findAgreementGroupHeadByWarNo(agreement, oldWarHead
							.getWarNo());
			if (groupHead != null) {
				groupHead.setWarNo(warHead.getWarNo());
				this.fixAssetDao.saveAgreementGroupHead(groupHead);
			}
		}
		this.fixAssetDao.saveAgreementWarHead(warHead);
	}

	/**
	 * 保存批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	public void saveDutyCertHead(Agreement agreement, DutyCertHead certHead) {
		DutyCertHead oldcertHead = (DutyCertHead) this.fixAssetDao.get(
				DutyCertHead.class, certHead.getId());
		if (oldcertHead != null) {
			AgreementGroupHead groupHead = this.fixAssetDao
					.findAgreementGroupHeadByCertNo(agreement, oldcertHead
							.getCertNo());
			if (groupHead != null) {
				groupHead.setCertNo(certHead.getCertNo());
				this.fixAssetDao.saveAgreementGroupHead(groupHead);
			}
		}
		this.fixAssetDao.saveDutyCertHead(certHead);
	}

	/**
	 * 设备状态查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findFixassetStatus(String agreementNo, Date beginDate,
			Date endDate) {
		List lsResult = new ArrayList();
		List list = this.fixAssetDao.findAgreementGroupDetail(agreementNo,
				beginDate, endDate);
		for (int i = 0; i < list.size(); i++) {
			AgreementGroupDetail groupDetail = (AgreementGroupDetail) list
					.get(i);
			String certNo = groupDetail.getGroupHead().getCertNo();
			Integer mainNo = groupDetail.getMainNo();
			List lsCustomsBill = findCustomsDeclarationStat(certNo, mainNo);
			if (lsCustomsBill.size() <= 0) {
				TempFixassetStatusInfo temp = new TempFixassetStatusInfo();
				temp.setStateMark(groupDetail.getGroupHead().getStateMark());
				temp.setGroupNo(groupDetail.getGroupHead().getGroupNo());
				temp.setApplier(groupDetail.getGroupHead().getApplier());
				temp.setInvoiceNo(groupDetail.getGroupHead().getInvoiceNo());
				temp.setCertNo(groupDetail.getGroupHead().getCertNo());
				temp.setWarNo(groupDetail.getGroupHead().getWarNo());
				temp.setCreateDate(groupDetail.getGroupHead().getCreateDate());
				temp.setDutyCertApplyDate(groupDetail.getGroupHead()
						.getDutyCertApplyDate());
				temp.setDutyCertApproveDate(groupDetail.getGroupHead()
						.getDutyCertApproveDate());
				temp.setPrepExportNoteDate(groupDetail.getGroupHead()
						.getPrepExportNoteDate());
				temp.setArriveDGDate(groupDetail.getGroupHead()
						.getArriveDGDate());
				temp.setArriveHKDate(groupDetail.getGroupHead()
						.getArriveHKDate());
				temp
						.setInspectDate(groupDetail.getGroupHead()
								.getInspectDate());
				temp
						.setDeclareDate(groupDetail.getGroupHead()
								.getDeclareDate());
				temp.setInFactDate(groupDetail.getGroupHead().getInFactDate());
				temp.setComplexCode(groupDetail.getComplex().getCode());
				temp.setCommName(groupDetail.getCommName());
				temp.setCommSpec(groupDetail.getCommSpec());
				lsResult.add(temp);
			} else {
				for (int j = 0; j < lsCustomsBill.size(); j++) {
					BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) lsCustomsBill
							.get(j);
					TempFixassetStatusInfo temp = new TempFixassetStatusInfo();
					temp
							.setStateMark(groupDetail.getGroupHead()
									.getStateMark());
					temp.setGroupNo(groupDetail.getGroupHead().getGroupNo());
					temp.setApplier(groupDetail.getGroupHead().getApplier());
					temp
							.setInvoiceNo(groupDetail.getGroupHead()
									.getInvoiceNo());
					temp.setCertNo(groupDetail.getGroupHead().getCertNo());
					temp.setWarNo(groupDetail.getGroupHead().getWarNo());
					temp.setCreateDate(groupDetail.getGroupHead()
							.getCreateDate());
					temp.setDutyCertApplyDate(groupDetail.getGroupHead()
							.getDutyCertApplyDate());
					temp.setDutyCertApproveDate(groupDetail.getGroupHead()
							.getDutyCertApproveDate());
					temp.setPrepExportNoteDate(groupDetail.getGroupHead()
							.getPrepExportNoteDate());
					temp.setArriveDGDate(groupDetail.getGroupHead()
							.getArriveDGDate());
					temp.setArriveHKDate(groupDetail.getGroupHead()
							.getArriveHKDate());
					temp.setInspectDate(groupDetail.getGroupHead()
							.getInspectDate());
					temp.setDeclareDate(groupDetail.getGroupHead()
							.getDeclareDate());
					temp.setInFactDate(groupDetail.getGroupHead()
							.getInFactDate());
					temp.setCustomsDeclarationCode(commInfo
							.getBaseCustomsDeclaration()
							.getCustomsDeclarationCode());
					temp.setComplexCode(commInfo.getComplex().getCode());
					temp.setCommName(commInfo.getCommName());
					temp.setCommSpec(commInfo.getCommSpec());
					lsResult.add(temp);
				}
			}
		}
		return lsResult;

	}

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List findCustomsDeclarationStat(String certNo, Integer mainNo) {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT };
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"CustomsDeclarationCommInfo", impExpTypes, certNo, mainNo));
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"BcsCustomsDeclarationCommInfo", impExpTypes, certNo, mainNo));
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"DzscCustomsDeclarationCommInfo", impExpTypes, certNo, mainNo));
		return lsResult;
	}

	/**
	 * 工厂设备余额查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findFixassetFactoryRemainMoney(String agreementNo) {
		List lsResult = new ArrayList();
		List list = this.fixAssetDao.findAgreementCommInfo(agreementNo);
		for (int i = 0; i < list.size(); i++) {
			AgreementCommInfo commInfo = (AgreementCommInfo) list.get(i);
			TempFactoryFixAssetRemainMoney remainMoney = new TempFactoryFixAssetRemainMoney();
			
			remainMoney.setMainNo(commInfo.getMainNo());
			remainMoney.setComplex(commInfo.getComplex());
			remainMoney.setCommName(commInfo.getCommName());
			remainMoney.setCommSpec(commInfo.getCommSpec());
			remainMoney.setUnit(commInfo.getUnit());
			remainMoney.setPorAmount(commInfo.getAmount());
			remainMoney.setPorMoney(commInfo.getTotalPrice());
			remainMoney.setListAmount(CommonUtils
					.getDoubleExceptNull(remainMoney.getPorAmount())
					- CommonUtils.getDoubleExceptNull(remainMoney
							.getChangedAmount())
					- CommonUtils.getDoubleExceptNull(remainMoney
							.getChangingAmount()));
			remainMoney.setFactoryMoney(CommonUtils
					.getDoubleExceptNull(remainMoney.getPorMoney())
					- CommonUtils.getDoubleExceptNull(remainMoney
							.getChangedMoney())
					- CommonUtils.getDoubleExceptNull(remainMoney
							.getChangingMoney()));
			lsResult.add(remainMoney);
		}
		return lsResult;
	}

	/**
	 * 海关设备余额查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findFixassetCustomsRemainMoney(String agreementNo) {
		List lsResult = new ArrayList();
		List list = this.fixAssetDao.findAgreementCommInfo(agreementNo);
		for (int i = 0; i < list.size(); i++) {
			AgreementCommInfo commInfo = (AgreementCommInfo) list.get(i);
			TempCustomsFixAssetRemainMoney remainMoney = new TempCustomsFixAssetRemainMoney();
			remainMoney.setMainNo(commInfo.getMainNo());
			remainMoney.setComplex(commInfo.getComplex());
			remainMoney.setCommName(commInfo.getCommName());
			remainMoney.setCommSpec(commInfo.getCommSpec());
			remainMoney.setUnit(commInfo.getUnit());
			remainMoney.setPorAmount(commInfo.getAmount());
			remainMoney.setPorMoney(commInfo.getTotalPrice());
			remainMoney.setCustomsMoney(CommonUtils
					.getDoubleExceptNull(remainMoney.getPorMoney())
					- CommonUtils.getDoubleExceptNull(remainMoney
							.getDutyCertedMoney())
					- CommonUtils.getDoubleExceptNull(remainMoney
							.getDutyCertingMoney()));
			lsResult.add(remainMoney);
		}
		return lsResult;
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclarationStat(String agreementNo, Date beginDate,
			Date endDate, boolean isImport) {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		if (isImport) {
			impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT };
		} else {
			impExpTypes = new Integer[] { ImpExpType.BACK_PORT_REPAIR,
					ImpExpType.EQUIPMENT_BACK_PORT };
		}
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"CustomsDeclarationCommInfo", impExpTypes, agreementNo,
				beginDate, endDate));
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"BcsCustomsDeclarationCommInfo", impExpTypes, agreementNo,
				beginDate, endDate));
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"DzscCustomsDeclarationCommInfo", impExpTypes, agreementNo,
				beginDate, endDate));
		return lsResult;
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List findCustomsDeclarationStat() {
		List lsResult = new ArrayList();
		Integer[] impExpTypes = null;
		impExpTypes = new Integer[] { ImpExpType.EQUIPMENT_IMPORT };
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"CustomsDeclarationCommInfo", impExpTypes));
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"BcsCustomsDeclarationCommInfo", impExpTypes));
		lsResult.addAll(this.fixAssetDao.findCustomsDeclaration(
				"DzscCustomsDeclarationCommInfo", impExpTypes));
		return lsResult;
	}

	/**
	 * 抓取进口报关单项目
	 * 
	 * @return
	 */
	public List findCustomsDeclarationFixasset() {
		List lsResult = new ArrayList();
		List list = this.findCustomsDeclarationStat();
		List lsCustomsInfo = this.fixAssetDao.findExistFixassetCustomsInfo();
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) objs[0];
			DutyCertHead dutyCertHead = (DutyCertHead) objs[1];
			String temp = (commInfo.getBaseCustomsDeclaration()
					.getCustomsDeclarationCode() == null ? "" : commInfo
					.getBaseCustomsDeclaration().getCustomsDeclarationCode()
					.trim())
					+ "-"
					+ (commInfo.getSerialNumber() == null ? "" : commInfo
							.getSerialNumber().toString());
			if (lsCustomsInfo.contains(temp)) {
				continue;
			}
			TempCustomsDeclarationFixasset tempFixasset = new TempCustomsDeclarationFixasset();
			tempFixasset.setAgreementNo(dutyCertHead.getSancEmsNo());
			tempFixasset.setCustomsDeclarationCode(commInfo
					.getBaseCustomsDeclaration().getCustomsDeclarationCode());
			tempFixasset.setImpExpDate(commInfo.getBaseCustomsDeclaration()
					.getImpExpDate());
			tempFixasset.setCustomsBillSeqNo(commInfo
					.getBaseCustomsDeclaration().getSerialNumber().toString());
			tempFixasset.setCustomsDeclaItemNo(commInfo.getSerialNumber()
					.toString());
			tempFixasset.setComplex(commInfo.getComplex());
			tempFixasset.setAmount(commInfo.getCommAmount());
			tempFixasset.setCommName(commInfo.getCommName());
			tempFixasset.setCommSpec(commInfo.getCommSpec());
			lsResult.add(tempFixasset);
		}
		return lsResult;
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixassetLocationChangeInfoFromCustomsBill(
			List<TempCustomsDeclarationFixasset> list,
			FixassetLocation newLocation, Integer changeType) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempCustomsDeclarationFixasset tempFixasset = (TempCustomsDeclarationFixasset) list
					.get(i);
			TempFixassetLocationChangeInfo tempChangeInfo = new TempFixassetLocationChangeInfo();
			tempChangeInfo.setAgreementNo(tempFixasset.getAgreementNo());
			tempChangeInfo.setCustomsDeclarationCode(tempFixasset
					.getCustomsDeclarationCode());
			tempChangeInfo.setImpExpDate(tempFixasset.getImpExpDate());
			tempChangeInfo.setCustomsBillSeqNo(tempFixasset
					.getCustomsBillSeqNo());
			tempChangeInfo.setCustomsDeclaItemNo(tempFixasset
					.getCustomsDeclaItemNo());
			tempChangeInfo.setComplex(tempFixasset.getComplex());
			tempChangeInfo.setAmount(tempFixasset.getAmount());
			tempChangeInfo.setCommName(tempFixasset.getCommName());
			tempChangeInfo.setCommSpec(tempFixasset.getCommSpec());
			tempChangeInfo.setNewLocation(newLocation);
			tempChangeInfo.setChangeType(changeType);
			lsResult.add(tempChangeInfo);
		}
		return lsResult;
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixassetLocationChangeInfoFromFactory(
			List<AgreementCommInfo> list, FixassetLocation newLocation,
			Integer changeType) {
		List lsResult = new ArrayList();
		list = this.fixAssetDao.findAgreementCommInfo();
		for (int i = 0; i < list.size(); i++) {
			AgreementCommInfo agreementCommInfo = (AgreementCommInfo) list
					.get(i);
			TempFixassetLocationChangeInfo tempChangeInfo = new TempFixassetLocationChangeInfo();
			tempChangeInfo.setAgreementNo(agreementCommInfo.getAgreement()
					.getSancEmsNo());
			tempChangeInfo.setComplex(agreementCommInfo.getComplex());
			tempChangeInfo.setAmount(agreementCommInfo.getAmount());
			tempChangeInfo.setCommName(agreementCommInfo.getCommName());
			tempChangeInfo.setCommSpec(agreementCommInfo.getCommSpec());
			tempChangeInfo.setNewLocation(newLocation);
			tempChangeInfo.setChangeType(changeType);
			lsResult.add(tempChangeInfo);
		}
		return lsResult;
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixassetLocationChangeInfoFromResultInfo(
			FixassetLocation newLocation, Integer changeType) {
		List lsResult = new ArrayList();
		List list = new ArrayList();
		if (changeType == ChangeLocaOptionParam.FACT_SUBTRACT) {
			list = this.fixAssetDao.findFixassetLocationResultInfo();
		} else if (changeType == ChangeLocaOptionParam.FACT_CHANGE_LOCATION) {
			list = this.fixAssetDao
					.findFixassetLocationResultInfoNotInTheLocation(newLocation);
		}
		for (int i = 0; i < list.size(); i++) {
			FixassetLocationResultInfo resultInfo = (FixassetLocationResultInfo) list
					.get(i);
			TempFixassetLocationChangeInfo tempChangeInfo = new TempFixassetLocationChangeInfo();
			tempChangeInfo.setAgreementNo(resultInfo.getAgreementNo());
			tempChangeInfo.setCustomsDeclarationCode(resultInfo
					.getCustomsDeclarationCode());
			tempChangeInfo.setImpExpDate(resultInfo.getImpExpDate());
			tempChangeInfo
					.setCustomsBillSeqNo(resultInfo.getCustomsBillSeqNo());
			tempChangeInfo.setCustomsDeclaItemNo(resultInfo
					.getCustomsDeclaItemNo());
			tempChangeInfo.setComplex(resultInfo.getComplex());
			tempChangeInfo.setAmount(resultInfo.getAmount());
			tempChangeInfo.setCommName(resultInfo.getCommName());
			tempChangeInfo.setCommSpec(resultInfo.getCommSpec());
			tempChangeInfo.setOldLocation(resultInfo.getLocation());
			tempChangeInfo.setNewLocation(newLocation);
			tempChangeInfo.setChangeType(changeType);
			lsResult.add(tempChangeInfo);
		}
		return lsResult;
	}

	/**
	 * 取得最终异动单据资料
	 * 
	 * @param list
	 * @param otherInfo
	 * @return
	 */
	public List getFixassetLocationChangeBillInfo(List list,
			TempOtherBillInfo otherInfo) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempFixassetLocationChangeInfo tempChangeInfo = (TempFixassetLocationChangeInfo) list
					.get(i);
			FixassetLocationChangeBillInfo changeBillInfo = new FixassetLocationChangeBillInfo();
			changeBillInfo.setAgreementNo(tempChangeInfo.getAgreementNo());
			changeBillInfo.setCustomsDeclarationCode(tempChangeInfo
					.getCustomsDeclarationCode());
			changeBillInfo.setImpExpDate(tempChangeInfo.getImpExpDate());
			changeBillInfo.setCustomsBillSeqNo(tempChangeInfo
					.getCustomsBillSeqNo());
			changeBillInfo.setCustomsDeclaItemNo(tempChangeInfo
					.getCustomsDeclaItemNo());
			changeBillInfo.setComplex(tempChangeInfo.getComplex());
			changeBillInfo.setAmount(tempChangeInfo.getAmount());
			changeBillInfo.setCommName(tempChangeInfo.getCommName());
			changeBillInfo.setCommSpec(tempChangeInfo.getCommSpec());
			changeBillInfo.setOldLocation(tempChangeInfo.getOldLocation());
			changeBillInfo.setNewLocation(tempChangeInfo.getNewLocation());
			changeBillInfo.setBillCode(otherInfo.getBillCode());
			changeBillInfo.setHandMan(otherInfo.getHandMan());
			changeBillInfo.setChangeType(tempChangeInfo.getChangeType());
			changeBillInfo.setChangeDate(new Date());
			lsResult.add(changeBillInfo);
		}
		return lsResult;
	}

	/**
	 * 保存最终异动单据资料
	 * 
	 * @param list
	 */
	public void saveFixassetLocationChangeBillInfo(List list, int changeType) {
		for (int i = 0; i < list.size(); i++) {
			FixassetLocationChangeBillInfo changeBillInfo = (FixassetLocationChangeBillInfo) list
					.get(i);
			this.fixAssetDao.saveOrUpdate(changeBillInfo);
			List lsTemp = this.fixAssetDao.findFixassetLocationResultInfo(
					changeBillInfo.getCustomsDeclarationCode(), changeBillInfo
							.getCustomsDeclaItemNo());
			if (lsTemp.size() <= 0) {
				FixassetLocationResultInfo resultInfo = new FixassetLocationResultInfo();
				resultInfo.setAgreementNo(changeBillInfo.getAgreementNo());
				resultInfo.setCustomsDeclarationCode(changeBillInfo
						.getCustomsDeclarationCode());
				resultInfo.setImpExpDate(changeBillInfo.getImpExpDate());
				resultInfo.setCustomsBillSeqNo(changeBillInfo
						.getCustomsBillSeqNo());
				resultInfo.setCustomsDeclaItemNo(changeBillInfo
						.getCustomsDeclaItemNo());
				resultInfo.setComplex(changeBillInfo.getComplex());
				resultInfo.setAmount(changeBillInfo.getAmount());
				resultInfo.setCommName(changeBillInfo.getCommName());
				resultInfo.setCommSpec(changeBillInfo.getCommSpec());
				resultInfo.setLocation(changeBillInfo.getNewLocation());
				this.fixAssetDao.saveOrUpdate(resultInfo);
			} else {
				if (changeType == ChangeLocaOptionParam.FACT_SUBTRACT) {
					FixassetLocationResultInfo resultInfo = (FixassetLocationResultInfo) lsTemp
							.get(0);
					this.fixAssetDao.delete(resultInfo);
				} else {
					FixassetLocationResultInfo resultInfo = (FixassetLocationResultInfo) lsTemp
							.get(0);
					resultInfo.setLocation(changeBillInfo.getNewLocation());
					this.fixAssetDao.saveOrUpdate(resultInfo);
				}
			}
		}
	}

	/**
	 * 回写设备金额
	 * 
	 * @param agreement
	 */
	private void writeBackFixassetMoney(Agreement agreement) {
		agreement.setTotalMoney(this.fixAssetDao
				.findAgreementFixassetMoney(agreement));
		this.fixAssetDao.saveAgreement(agreement);
	}

	public List checkImportAgreementCommInfo(List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempImportAgreementCommInfo tempCommInfo = (TempImportAgreementCommInfo) list
					.get(i);
		}
		return lsResult;
	}

	/**
	 * 检查新增的设备金额是否超范围
	 * 
	 * @param agreement
	 * @return
	 */
	private boolean checkAgreementMoneyOutRange(Agreement agreement) {
		agreement = this.fixAssetDao.findAgreement(agreement);
		double changeRemainMoney = CommonUtils.getDoubleExceptNull(agreement
				.getChangeRemainMoney());
		double deleteMoney = this.fixAssetDao
				.findDeleteAgreementCommInfoMoney(agreement);
		double addMoney = this.fixAssetDao
				.findAddAgreementCommInfoMoney(agreement);
		if (addMoney > deleteMoney + changeRemainMoney) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 从文件导入设备明细资料
	 * 
	 * @param agreement
	 * @param list
	 * @param isChange
	 */
	public void importAgreementCommInfo(Agreement agreement, List list,
			boolean isChange, boolean isAddMoney) {
		List lsExistMainNo = this.fixAssetDao
				.findExistFixassetCommInfo(agreement);
		if (isChange) {
			List lsExistImpCommInfo = this.fixAssetDao
					.findExistFixassetImpCommInfo(agreement);
			List lsCommInfo = this.mergeImportAddAgreementCommInfo(list);
			for (int i = 0; i < lsCommInfo.size(); i++) {
				ImpAgreementCommInfo commInfo = (ImpAgreementCommInfo) lsCommInfo
						.get(i);
				if (lsExistMainNo.contains(commInfo.getMainNo())) {
					throw new RuntimeException("主序号" + commInfo.getMainNo()
							+ "已经存在于设备批文中");
				}
				if (lsExistImpCommInfo.contains(commInfo.getMainNo())) {
					throw new RuntimeException("主序号" + commInfo.getMainNo()
							+ "已经存在于增加的设备批文中");
				}
				commInfo.setAgreement(agreement);
				commInfo.setStateMark(AgreementState.PUT_ON_RECORD);
				this.fixAssetDao.saveImpAgreementCommInfo(commInfo);
			}
			if (!checkAgreementMoneyOutRange(agreement)) {
				throw new RuntimeException("新增的设备金额已超出范围");
			}
		} else {
			double totalMoney = 0.0;
			List lsCommInfo = this.mergeImportAgreementCommInfo(list);
			for (int i = 0; i < lsCommInfo.size(); i++) {
				AgreementCommInfo commInfo = (AgreementCommInfo) lsCommInfo
						.get(i);
				if (lsExistMainNo.contains(commInfo.getMainNo())) {
					throw new RuntimeException("主序号" + commInfo.getMainNo()
							+ "已经存在于设备批文中");
				}
				commInfo.setAgreement(agreement);
				if (isAddMoney) {
					commInfo.setStateMark(AgreementState.PUT_ON_RECORD);
				} else {
					commInfo.setStateMark(AgreementState.ADDED);
				}
				this.fixAssetDao.saveAgreementCommInfo(commInfo);
				totalMoney += CommonUtils.getDoubleExceptNull(commInfo
						.getTotalPrice());
			}
			if (isAddMoney) {
				agreement = this.fixAssetDao.findAgreement(agreement);
				agreement.setTotalMoney(CommonUtils
						.getDoubleExceptNull(agreement.getTotalMoney())
						+ totalMoney);
				this.fixAssetDao.saveAgreement(agreement);
			} else {
				this.writeBackFixassetMoney(agreement);
			}
		}
	}

	/**
	 * 将设备明细资料根据主序号合并起来
	 * 
	 * @param list
	 * @return
	 */
	private List mergeImportAgreementCommInfo(List list) {
		List lsResult = new ArrayList();
		Map<String, Complex> complexMap = new HashMap<String, Complex>();
		List lsComplex = fixAssetDao.findComplex();
		for (int i = 0, n = lsComplex.size(); i < n; i++) {
			Complex complex = (Complex) lsComplex.get(i);
			complexMap.put(complex.getCode().trim(), complex);
		}
		Map<String, Unit> unitMap = new HashMap<String, Unit>();
		List lsUnit = fixAssetDao.findUnit();
		for (int i = 0, n = lsUnit.size(); i < n; i++) {
			Unit unit = (Unit) lsUnit.get(i);
			unitMap.put(unit.getName().trim(), unit);
		}
		Map<String, Country> countryMap = new HashMap<String, Country>();
		List lsCountry = fixAssetDao.findCountry();
		for (int i = 0, n = lsCountry.size(); i < n; i++) {
			Country country = (Country) lsCountry.get(i);
			countryMap.put(country.getName().trim(), country);
		}
		Map<Integer, AgreementCommInfo> hmCommInfo = new HashMap<Integer, AgreementCommInfo>();
		for (int i = 0; i < list.size(); i++) {
			TempImportAgreementCommInfo tempCommInfo = (TempImportAgreementCommInfo) list
					.get(i);
			AgreementCommInfo commInfo = hmCommInfo.get(Integer
					.valueOf(tempCommInfo.getMainNo()));
			if (commInfo == null) {
				commInfo = new AgreementCommInfo();
				commInfo.setMainNo(Integer.valueOf(tempCommInfo.getMainNo()));
				Complex complex = complexMap.get(tempCommInfo.getCommCode()
						.trim());
				if (complex == null) {
					throw new RuntimeException("在自用商品编码库中没有"
							+ tempCommInfo.getCommCode().trim() + "的商品");
				}
				commInfo.setComplex(complex);
				commInfo.setCommName(tempCommInfo.getCommName());
				commInfo.setCommSpec(tempCommInfo.getCommSpec());
				if (tempCommInfo.getUnit() != null) {
					commInfo
							.setUnit(unitMap.get(tempCommInfo.getUnit().trim()));
				}
				commInfo.setAmount(Double.valueOf(tempCommInfo.getAmount()));
				commInfo.setUnitPrice(Double.valueOf(tempCommInfo
						.getUnitPrice()));
				// commInfo.setTotalPrice(Double.valueOf(tempCommInfo
				// .getTotalPrice()));
				commInfo.setTotalPrice(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
				if (tempCommInfo.getCountry() != null) {
					commInfo.setCountry(countryMap.get(tempCommInfo
							.getCountry().trim()));
				}
				hmCommInfo.put(Integer.valueOf(tempCommInfo.getMainNo()),
						commInfo);
			} else {
				commInfo.setAmount(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						+ Double.valueOf(tempCommInfo.getAmount()));
				commInfo.setTotalPrice(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
			}
		}
		lsResult.addAll(hmCommInfo.values());
		return lsResult;
	}

	/**
	 * 将设备明细资料根据主序号合并起来
	 * 
	 * @param list
	 * @return
	 */
	private List mergeImportAddAgreementCommInfo(List list) {
		List lsResult = new ArrayList();
		Map<String, Complex> complexMap = new HashMap<String, Complex>();
		List lsComplex = fixAssetDao.findComplex();
		for (int i = 0, n = lsComplex.size(); i < n; i++) {
			Complex complex = (Complex) lsComplex.get(i);
			complexMap.put(complex.getCode().trim(), complex);
		}
		Map<String, Unit> unitMap = new HashMap<String, Unit>();
		List lsUnit = fixAssetDao.findUnit();
		for (int i = 0, n = lsUnit.size(); i < n; i++) {
			Unit unit = (Unit) lsUnit.get(i);
			unitMap.put(unit.getName().trim(), unit);
		}
		Map<String, Country> countryMap = new HashMap<String, Country>();
		List lsCountry = fixAssetDao.findCountry();
		for (int i = 0, n = lsCountry.size(); i < n; i++) {
			Country country = (Country) lsCountry.get(i);
			countryMap.put(country.getName().trim(), country);
		}
		Map<Integer, ImpAgreementCommInfo> hmCommInfo = new HashMap<Integer, ImpAgreementCommInfo>();
		for (int i = 0; i < list.size(); i++) {
			TempImportAgreementCommInfo tempCommInfo = (TempImportAgreementCommInfo) list
					.get(i);
			ImpAgreementCommInfo commInfo = hmCommInfo.get(Integer
					.valueOf(tempCommInfo.getMainNo()));
			if (commInfo == null) {
				commInfo = new ImpAgreementCommInfo();
				commInfo.setMainNo(Integer.valueOf(tempCommInfo.getMainNo()));
				Complex complex = complexMap.get(tempCommInfo.getCommCode()
						.trim());
				if (complex == null) {
					throw new RuntimeException("在自用商品编码库中没有"
							+ tempCommInfo.getCommCode().trim() + "的商品");
				}
				commInfo.setComplex(complex);
				commInfo.setCommName(tempCommInfo.getCommName());
				commInfo.setCommSpec(tempCommInfo.getCommSpec());
				commInfo.setAmount(Double.valueOf(tempCommInfo.getAmount()));
				if (tempCommInfo.getUnit() != null) {
					commInfo
							.setUnit(unitMap.get(tempCommInfo.getUnit().trim()));
				}
				commInfo.setUnitPrice(Double.valueOf(tempCommInfo
						.getUnitPrice()));
				commInfo.setTotalPrice(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
				// commInfo.setTotalPrice(Double.valueOf(tempCommInfo
				// .getTotalPrice()));
				if (tempCommInfo.getCountry() != null) {
					commInfo.setCountry(countryMap.get(tempCommInfo
							.getCountry().trim()));
				}
				hmCommInfo.put(Integer.valueOf(tempCommInfo.getMainNo()),
						commInfo);
			} else {
				commInfo.setAmount(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						+ Double.valueOf(tempCommInfo.getAmount()));
				commInfo.setTotalPrice(CommonUtils.getDoubleExceptNull(commInfo
						.getAmount())
						* CommonUtils.getDoubleExceptNull(commInfo
								.getUnitPrice()));
			}
		}
		lsResult.addAll(hmCommInfo.values());
		return lsResult;
	}

	/**
	 * 变更合同料件序号
	 * 
	 * @param img
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeAgreementCommInfoSeqNum(AgreementCommInfo img,
			Integer newSeqNum) {
		if (this.fixAssetDao.findAgreementCommInfo(img.getAgreement().getId(),
				newSeqNum.toString()) != null) {
			if (newSeqNum < img.getMainNo()) {
				List list = this.fixAssetDao.findAgreementCommInfoBeginAndEndSeqNum(
						img.getAgreement().getId(), newSeqNum,
						img.getMainNo() - 1);
				for (int i = 0; i < list.size(); i++) {
					AgreementCommInfo contractImg = (AgreementCommInfo) list
							.get(i);
					contractImg.setMainNo(contractImg.getMainNo() + 1);
					this.fixAssetDao.saveAgreementCommInfo(contractImg);
				}
			} else {
				List list = this.fixAssetDao.findAgreementCommInfoBeginAndEndSeqNum(
						img.getAgreement().getId(), img.getMainNo() + 1,
						newSeqNum);
				for (int i = 0; i < list.size(); i++) {
					AgreementCommInfo contractImg = (AgreementCommInfo) list
							.get(i);
					contractImg.setMainNo(contractImg.getMainNo() + 1);
					this.fixAssetDao.saveAgreementCommInfo(contractImg);

				}
			}
		}
		img.setMainNo(newSeqNum);
		this.fixAssetDao.saveAgreementCommInfo(img);
	}
	
	/**
	 * 保存合同料件
	 * 
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public void saveAgreementCommInfo(List<AgreementCommInfo> list) {
		for (AgreementCommInfo img : list) {
			this.fixAssetDao.saveAgreementCommInfo(img);

		}
	}
}
