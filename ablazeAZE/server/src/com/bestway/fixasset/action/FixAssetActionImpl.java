package com.bestway.fixasset.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementGroupState;
import com.bestway.common.constant.AgreementState;
import com.bestway.common.constant.ImpExpType;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.fixasset.dao.FixAssetDao;
import com.bestway.fixasset.entity.ChangeLocaOptionParam;
import com.bestway.fixasset.entity.FixassetLocation;
import com.bestway.fixasset.entity.FixassetLocationChangeBillInfo;
import com.bestway.fixasset.entity.FixassetLocationResultInfo;
import com.bestway.fixasset.entity.ImpAgreementCommInfo;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.fixasset.entity.AgreementGroupDetail;
import com.bestway.fixasset.entity.AgreementGroupHead;
import com.bestway.fixasset.entity.AgreementInvoiceDetail;
import com.bestway.fixasset.entity.AgreementInvoiceHead;
import com.bestway.fixasset.entity.AgreementWarDetail;
import com.bestway.fixasset.entity.AgreementWarHead;
import com.bestway.fixasset.entity.DeleteAgreementCommInfo;
import com.bestway.fixasset.entity.DutyCertDetail;
import com.bestway.fixasset.entity.DutyCertHead;
import com.bestway.fixasset.entity.TempCustomsDeclarationFixasset;
import com.bestway.fixasset.entity.TempCustomsFixAssetRemainMoney;
import com.bestway.fixasset.entity.TempFactoryFixAssetRemainMoney;
import com.bestway.fixasset.entity.TempFixassetLocationChangeInfo;
import com.bestway.fixasset.entity.TempGroupImpCommInfo;
import com.bestway.fixasset.entity.TempOtherBillInfo;
import com.bestway.fixasset.logic.FixAssetLogic;
import com.bestway.fixtureonorder.entity.FixtureContractItems;

public class FixAssetActionImpl implements FixAssetAction {
	private FixAssetDao fixAssetDao;

	private FixAssetLogic fixAssetLogic;

	public FixAssetDao getFixAssetDao() {
		return fixAssetDao;
	}

	public void setFixAssetDao(FixAssetDao fixAssetDao) {
		this.fixAssetDao = fixAssetDao;
	}

	public FixAssetLogic getFixAssetLogic() {
		return fixAssetLogic;
	}

	public void setFixAssetLogic(FixAssetLogic fixAssetLogic) {
		this.fixAssetLogic = fixAssetLogic;
	}

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	public List findAgreement(Request request) {
		return this.fixAssetDao.findAgreement();
	}

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	public Agreement findAgreement(Request request, Agreement agreement) {
		return this.fixAssetDao.findAgreement(agreement);
	}

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	public List findAgreementExcuting(Request request) {
		return this.fixAssetDao.findAgreementExcuting();
	}

	/**
	 * 保存批文协议
	 * 
	 * @param agreement
	 */
	public Agreement saveAgreement(Request request, Agreement agreement) {
		this.fixAssetDao.saveAgreement(agreement);
		return agreement;
	}

	/**
	 * 删除批文协议
	 * 
	 * @param agreement
	 */
	public void deleteAgreement(Request request, Agreement agreement) {
		this.fixAssetLogic.deleteAgreement(agreement);
	}

	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementCommInfo(Request request, Agreement agreement) {
		return this.fixAssetDao.findAgreementCommInfo(agreement);
	}

	/**
	 * 保存批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public AgreementCommInfo saveAgreementCommInfo(Request request,
			AgreementCommInfo commInfo) {
		this.fixAssetLogic.saveAgreementCommInfo(commInfo);
		return commInfo;
	}

	/**
	 * 删除批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public List deleteAgreementCommInfo(Request request, List list) {
		return this.fixAssetLogic.deleteAgreementCommInfo(list);
	}

	/**
	 * 查询新增的批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findImpAgreementCommInfo(Request request, Agreement agreement) {
		return this.fixAssetDao.findImpAgreementCommInfo(agreement);
	}

	/**
	 * 保存新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public ImpAgreementCommInfo saveImpAgreementCommInfo(Request request,
			ImpAgreementCommInfo commInfo) {
		this.fixAssetLogic.saveImpAgreementCommInfo(commInfo);
		return commInfo;
	}

	/**
	 * 协议备案
	 * 
	 * @param agreement
	 */
	public Agreement agreementPutOnRecord(Request request, Agreement agreement) {
		this.fixAssetLogic.agreementPutOnRecord(agreement);
		return agreement;
	}

	/**
	 * 删除新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void deleteImpAgreementCommInfo(Request request,
			List<ImpAgreementCommInfo> list) {
		this.fixAssetDao.deleteAll(list);
	}

	/**
	 * 查询取消的批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDeleteAgreementCommInfo(Request request, Agreement agreement) {
		return this.fixAssetDao.findDeleteAgreementCommInfo(agreement);
	}

	/**
	 * 保存取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public DeleteAgreementCommInfo saveDeleteAgreementCommInfo(Request request,
			DeleteAgreementCommInfo commInfo) {
		this.fixAssetLogic.saveDeleteAgreementCommInfo(commInfo);
		return commInfo;
	}

	/**
	 * 删除取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	public void deleteDeleteAgreementCommInfo(Request request,
			List<DeleteAgreementCommInfo> list) {
		this.fixAssetDao.deleteAll(list);
	}

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupHead(Request request, Agreement agreement) {
		return this.fixAssetDao.findAgreementGroupHead(agreement);
	}

	/**
	 * 保存批文协议设备分组表头
	 * 
	 * @param commInfo
	 */
	public AgreementGroupHead saveAgreementGroupHead(Request request,
			AgreementGroupHead groupHead) {
		this.fixAssetDao.saveAgreementGroupHead(groupHead);
		return groupHead;
	}

	/**
	 * 删除批文协议设备分组表头
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementGroupHead(Request request,
			AgreementGroupHead groupHead) {
		this.fixAssetDao.deleteAgreementGroupHead(groupHead);
	}

	/**
	 * 查询批文协议设备明细分组
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupDetail(Request request,
			AgreementGroupHead groupHead) {
		return this.fixAssetDao.findAgreementGroupDetail(groupHead);
	}

	/**
	 * 查询批文协议设备明细分组
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementGroupDetailNotHandIn(Request request,
			Agreement agreement) {
		return this.fixAssetDao.findAgreementGroupDetailNotHandIn(agreement);
	}

	/**
	 * 保存批文协议设备明细分组
	 * 
	 * @param commInfo
	 */
	public AgreementGroupDetail saveAgreementGroupDetail(Request request,
			AgreementGroupDetail groupDetail) {
		this.fixAssetDao.saveAgreementGroupDetail(groupDetail);
		return groupDetail;
	}

	/**
	 * 删除批文协议设备明细分组
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementGroupDetail(Request request,
			AgreementGroupDetail groupDetail) {
		this.fixAssetDao.deleteAgreementGroupDetail(groupDetail);
	}

	/**
	 * 查询批文协议设备发票表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementInvoiceHead(Request request, String sancEmsNo) {
		return this.fixAssetDao.findAgreementInvoiceHead(sancEmsNo);
	}

	/**
	 * 保存批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	public AgreementInvoiceHead saveAgreementInvoiceHead(Request request,
			Agreement agreement, AgreementInvoiceHead invoiceHead) {
		this.fixAssetLogic.saveAgreementInvoiceHead(agreement, invoiceHead);
		return invoiceHead;
	}

	/**
	 * 删除批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementInvoiceHead(Request request,
			AgreementInvoiceHead invoiceHead) {
		this.fixAssetDao.deleteAgreementInvoiceHead(invoiceHead);
	}

	/**
	 * 查询批文协议设备发票明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementInvoiceDetail(Request request,
			AgreementInvoiceHead invoiceHead) {
		return this.fixAssetDao.findAgreementInvoiceDetail(invoiceHead);
	}

	/**
	 * 保存批文协议设备发票明细
	 * 
	 * @param commInfo
	 */
	public AgreementInvoiceDetail saveAgreementInvoiceDetail(Request request,
			AgreementInvoiceDetail invoiceDetail) {
		this.fixAssetDao.saveAgreementInvoiceDetail(invoiceDetail);
		return invoiceDetail;
	}

	/**
	 * 删除批文协议设备发票明细
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementInvoiceDetail(Request request,
			AgreementInvoiceDetail invoiceDetail) {
		this.fixAssetDao.deleteAgreementInvoiceDetail(invoiceDetail);
	}

	/**
	 * 查询批文协议设备保证书表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementWarHead(Request request, String sancEmsNo) {
		return this.fixAssetDao.findAgreementWarHead(sancEmsNo);
	}

	/**
	 * 保存批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	public AgreementWarHead saveAgreementWarHead(Request request,
			Agreement agreement, AgreementWarHead warHead) {
		this.fixAssetLogic.saveAgreementWarHead(agreement, warHead);
		return warHead;
	}

	/**
	 * 删除批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementWarHead(Request request, AgreementWarHead warHead) {
		this.fixAssetDao.deleteAgreementWarHead(warHead);
	}

	/**
	 * 查询批文协议设备保证书明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findAgreementWarDetail(Request request, AgreementWarHead warHead) {
		return this.fixAssetDao.findAgreementWarDetail(warHead);
	}

	/**
	 * 保存批文协议设备保证书明细
	 * 
	 * @param commInfo
	 */
	public AgreementWarDetail saveAgreementWarDetail(Request request,
			AgreementWarDetail warDetail) {
		this.fixAssetDao.saveAgreementWarDetail(warDetail);
		return warDetail;
	}

	/**
	 * 删除批文协议设备保证书明细
	 * 
	 * @param commInfo
	 */
	public void deleteAgreementWarDetail(Request request,
			AgreementWarDetail warDetail) {
		this.fixAssetDao.deleteAgreementWarDetail(warDetail);
	}

	/**
	 * 查询批文协议设备保证书表头
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertHead(Request request, String sancEmsNo) {
		return this.fixAssetDao.findDutyCertHead(sancEmsNo);
	}

	/**
	 * 保存批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	public DutyCertHead saveDutyCertHead(Request request, Agreement agreement,
			DutyCertHead certHead) {
		this.fixAssetLogic.saveDutyCertHead(agreement, certHead);
		return certHead;
	}

	/**
	 * 删除批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	public void deleteDutyCertHead(Request request, DutyCertHead certHead) {
		this.fixAssetDao.deleteDutyCertHead(certHead);
	}

	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertDetail(Request request, DutyCertHead certHead) {
		return this.fixAssetDao.findDutyCertDetail(certHead);
	}

	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	public List findDutyCertDetailByCertNo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration) {
		return this.fixAssetDao
				.findDutyCertDetailByCertNo(baseCustomsDeclaration);
	}

	/**
	 * 保存批文协议设备征免税证明细
	 * 
	 * @param commInfo
	 */
	public DutyCertDetail saveDutyCertDetail(Request request,
			DutyCertDetail certDetail) {
		this.fixAssetDao.saveDutyCertDetail(certDetail);
		return certDetail;
	}

	/**
	 * 删除批文协议设备征免税证明细
	 * 
	 * @param commInfo
	 */
	public void deleteDutyCertDetail(Request request, DutyCertDetail certDetail) {
		this.fixAssetDao.deleteDutyCertDetail(certDetail);
	}

	/**
	 * 新增协议批文
	 * 
	 * @return
	 */
	public Agreement addAgreement(Request request) {
		return this.fixAssetLogic.addAgreement();
	}

	/**
	 * 新增协议批文商品明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addAgreementCommInfo(Request request, Agreement agreement,
			List list) {
		return this.fixAssetLogic.addAgreementCommInfo(agreement, list);
	}

	/**
	 * 根据主序号查询处于"收件"状态的集结物品项数
	 * 
	 * @param agreement
	 * @param mainNo
	 * @return
	 */
	public int findAgreementGroupCountNoHandIn(Request request,
			Agreement agreement, Integer mainNo) {
		return this.fixAssetDao.findAgreementGroupCountNoHandIn(agreement,
				mainNo);
	}

	/**
	 * 抓取不在设备批文中的商品
	 * 
	 * @param agreement
	 * @return
	 */
	public List findComplexNotInAgreement(Request request, Agreement agreement) {
		return this.fixAssetDao.findComplexNotInAgreement(agreement);
	}

	/**
	 * 删除取消的协议批文商品明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addDeleteAgreementCommInfo(Request request,
			Agreement agreement, List list) {
		return this.fixAssetLogic.addDeleteAgreementCommInfo(agreement, list);
	}

	/**
	 * 进口备案设备明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addPORAgreementCommInfo(Request request, Agreement agreement,
			List list) {
		return this.fixAssetLogic.addPORAgreementCommInfo(agreement, list);
	}

	/**
	 * 进口新设备明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List addNewAgreementCommInfo(Request request, Agreement agreement,
			List list) {
		return this.fixAssetLogic.addNewAgreementCommInfo(agreement, list);
	}

	/**
	 * 查询未集结的商品
	 * 
	 * @param agreement
	 * @return
	 */
	public List findImpCommInfoNotGroup(Request request, Agreement agreement) {
		return this.fixAssetDao.findImpCommInfoNotGroup(agreement);
	}

	/**
	 * 设备物品集结
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	public List groupImpAgreementCommInfo(Request request, Agreement agreement,
			List list, String groupNo) {
		return this.fixAssetLogic.groupImpAgreementCommInfo(agreement, list,
				groupNo);
	}

	/**
	 * 取消设备物品集结
	 * 
	 * @param list
	 */
	public void removeGroupAgreementCommInfo(Request request, List list) {
		this.fixAssetLogic.removeGroupAgreementCommInfo(list);
	}

	/**
	 * 自动产生发票，保证书，和征免税证明
	 * 
	 * @param agreement
	 */
	public void makeOtherBill(Request request, Agreement agreement,
			boolean isMakeInvoice, boolean isMakeWar, boolean isMakeDutyCert) {
		this.fixAssetLogic.makeOtherBill(agreement, isMakeInvoice, isMakeWar,
				isMakeDutyCert);
	}

	/**
	 * 向海关递单
	 * 
	 * @param agreement
	 */
	public void handInBill(Request request, Agreement agreement, List lsGroup) {
		this.fixAssetLogic.handInBill(agreement, lsGroup);
	}

	/**
	 * 取消递单
	 * 
	 * @param agreement
	 */
	public void cancelHandInBill(Request request, Agreement agreement,
			Integer changedTimes) {
		this.fixAssetLogic.cancelHandInBill(agreement, changedTimes);
	}

	/**
	 * 抓取正在执行的征免税
	 * 
	 * @return
	 */
	public List findDutyCertHeadExecuting(Request request) {
		return this.fixAssetDao.findDutyCertHeadExecuting();
	}

	/**
	 * 设备状态查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findFixassetStatus(Request request, String agreementNo,
			Date beginDate, Date endDate) {
		return this.fixAssetLogic.findFixassetStatus(agreementNo, beginDate,
				endDate);
	}

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findCustomsDeclarationStat(Request request, String agreementNo,
			Date beginDate, Date endDate, boolean isImport) {
		return this.fixAssetLogic.findCustomsDeclarationStat(agreementNo,
				beginDate, endDate, isImport);
	}

	/**
	 * 工厂设备余额查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findFixassetFactoryRemainMoney(Request request,
			String agreementNo) {
		return this.fixAssetLogic.findFixassetFactoryRemainMoney(agreementNo);
	}

	/**
	 * 海关设备余额查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List findFixassetCustomsRemainMoney(Request request,
			String agreementNo) {
		return this.fixAssetLogic.findFixassetCustomsRemainMoney(agreementNo);
	}

	/**
	 * 存放位置表查询存放位置表
	 * 
	 * @return
	 */
	public List findFixassetLocation(Request request) {
		return this.fixAssetDao.findFixassetLocation();
	}

	/**
	 * 保存存放位置表
	 * 
	 * @param location
	 */
	public FixassetLocation saveFixassetLocation(Request request,
			FixassetLocation location) {
		this.fixAssetDao.saveFixassetLocation(location);
		return location;
	}

	/**
	 * 删除存放位置表
	 * 
	 * @param location
	 */
	public void deleteFixassetLocation(Request request,
			FixassetLocation location) {
		this.fixAssetDao.deleteFixassetLocation(location);
	}

	/**
	 * 抓取进口报关单项目
	 * 
	 * @return
	 */
	public List findCustomsDeclarationFixasset(Request request) {
		return this.fixAssetLogic.findCustomsDeclarationFixasset();
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixassetLocationChangeInfoFromCustomsBill(Request request,
			List<TempCustomsDeclarationFixasset> list,
			FixassetLocation newLocation, Integer changeType) {
		return this.fixAssetLogic.getFixassetLocationChangeInfoFromCustomsBill(
				list, newLocation, changeType);
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixassetLocationChangeInfoFromFactory(Request request,
			List<AgreementCommInfo> list, FixassetLocation newLocation,
			Integer changeType) {
		return this.fixAssetLogic.getFixassetLocationChangeInfoFromFactory(
				list, newLocation, changeType);
	}

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	public List getFixassetLocationChangeInfoFromResultInfo(Request request,
			FixassetLocation newLocation, Integer changeType) {
		return this.fixAssetLogic.getFixassetLocationChangeInfoFromResultInfo(
				newLocation, changeType);
	}

	/**
	 * 取得最终异动单据资料
	 * 
	 * @param list
	 * @param otherInfo
	 * @return
	 */
	public List getFixassetLocationChangeBillInfo(Request request, List list,
			TempOtherBillInfo otherInfo) {
		return this.fixAssetLogic.getFixassetLocationChangeBillInfo(list,
				otherInfo);
	}

	/**
	 * 保存最终异动单据资料
	 * 
	 * @param list
	 */
	public void saveFixassetLocationChangeBillInfo(Request request, List list,
			int changeType) {
		this.fixAssetLogic.saveFixassetLocationChangeBillInfo(list, changeType);
	}

	/**
	 * 查询设备异动单据
	 * 
	 * @return
	 */
	public List findFixassetLocationChangeBillInfo(Request request) {
		return this.fixAssetDao.findFixassetLocationChangeBillInfo();
	}

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	public List findFixassetLocationResultInfo(Request request) {
		return this.fixAssetDao.findFixassetLocationResultInfo();
	}

	/**
	 * 从文件导入设备明细资料
	 * 
	 * @param agreement
	 * @param list
	 * @param isChange
	 */
	public void importAgreementCommInfo(Request request, Agreement agreement,
			List list, boolean isChange, boolean isAddMoney) {
		this.fixAssetLogic.importAgreementCommInfo(agreement, list, isChange,
				isAddMoney);
	}
	
	/**
	 * 查找设备批文表体 来自 设备批文表头Id
	 * 
	 * @param parentId
	 *            设备批文表头Id
	 * @return List 是AgreementCommInfo型，设备批文表体
	 */
	public List findAgreementCommInfoByParentId(Request request,String parentId) {
		return fixAssetDao.findAgreementCommInfoByParentId(parentId);
	}
	
	/**
	 * 变更合同料件序号
	 * 
	 * @param request
	 *            请求控制
	 * @param item
	 *            合同料件
	 * @param newSeqNum
	 *            新序号
	 */
	public void changeAgreementCommInfoSeqNum(Request request,
			AgreementCommInfo item, Integer newSeqNum) {
		this.fixAssetLogic.changeAgreementCommInfoSeqNum(item, newSeqNum);
		
	}
	
	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public List saveAgreementCommInfo(Request request, List list) {
		this.fixAssetLogic.saveAgreementCommInfo(list);
		return list;
	}
}
