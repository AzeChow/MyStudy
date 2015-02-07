package com.bestway.fixasset.action;

import java.util.Date;
import java.util.List;

import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.fixasset.entity.FixassetLocation;
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
import com.bestway.fixasset.entity.TempOtherBillInfo;

public interface FixAssetAction {

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	List findAgreement(Request request);

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	Agreement findAgreement(Request request, Agreement agreement);

	/**
	 * 查询批文协议
	 * 
	 * @return
	 */
	List findAgreementExcuting(Request request);

	/**
	 * 保存批文协议
	 * 
	 * @param agreement
	 */
	Agreement saveAgreement(Request request, Agreement agreement);

	/**
	 * 删除批文协议
	 * 
	 * @param agreement
	 */
	void deleteAgreement(Request request, Agreement agreement);

	/**
	 * 查询批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementCommInfo(Request request, Agreement agreement);

	/**
	 * 保存批文协议设备明细
	 * 
	 * @param commInfo
	 */
	AgreementCommInfo saveAgreementCommInfo(Request request,
			AgreementCommInfo commInfo);

	/**
	 * 删除批文协议设备明细
	 * 
	 * @param commInfo
	 */
	List deleteAgreementCommInfo(Request request, List list);

	/**
	 * 查询新增的批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findImpAgreementCommInfo(Request request, Agreement agreement);

	/**
	 * 保存新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	ImpAgreementCommInfo saveImpAgreementCommInfo(Request request,
			ImpAgreementCommInfo commInfo);

	/**
	 * 删除新增的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	void deleteImpAgreementCommInfo(Request request,
			List<ImpAgreementCommInfo> list);

	/**
	 * 查询取消的批文协议设备明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findDeleteAgreementCommInfo(Request request, Agreement agreement);

	/**
	 * 保存取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	DeleteAgreementCommInfo saveDeleteAgreementCommInfo(Request request,
			DeleteAgreementCommInfo commInfo);

	/**
	 * 删除取消的批文协议设备明细
	 * 
	 * @param commInfo
	 */
	void deleteDeleteAgreementCommInfo(Request request,
			List<DeleteAgreementCommInfo> list);

	/**
	 * 协议备案
	 * 
	 * @param agreement
	 */
	Agreement agreementPutOnRecord(Request request, Agreement agreement);

	/**
	 * 查询批文协议设备分组表头
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementGroupHead(Request request, Agreement agreement);

	/**
	 * 保存批文协议设备分组表头
	 * 
	 * @param commInfo
	 */
	AgreementGroupHead saveAgreementGroupHead(Request request,
			AgreementGroupHead groupHead);

	/**
	 * 删除批文协议设备分组表头
	 * 
	 * @param commInfo
	 */
	void deleteAgreementGroupHead(Request request, AgreementGroupHead groupHead);

	/**
	 * 查询批文协议设备明细分组
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementGroupDetail(Request request, AgreementGroupHead groupHead);

	/**
	 * 查询批文协议设备明细分组
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementGroupDetailNotHandIn(Request request, Agreement agreement);

	/**
	 * 保存批文协议设备明细分组
	 * 
	 * @param commInfo
	 */
	AgreementGroupDetail saveAgreementGroupDetail(Request request,
			AgreementGroupDetail groupDetail);

	/**
	 * 删除批文协议设备明细分组
	 * 
	 * @param commInfo
	 */
	void deleteAgreementGroupDetail(Request request,
			AgreementGroupDetail groupDetail);

	/**
	 * 查询批文协议设备发票表头
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementInvoiceHead(Request request, String sancEmsNo);

	/**
	 * 保存批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	AgreementInvoiceHead saveAgreementInvoiceHead(Request request,
			Agreement agreement, AgreementInvoiceHead invoiceHead);

	/**
	 * 删除批文协议设备发票表头
	 * 
	 * @param commInfo
	 */
	void deleteAgreementInvoiceHead(Request request,
			AgreementInvoiceHead invoiceHead);

	/**
	 * 查询批文协议设备发票明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementInvoiceDetail(Request request,
			AgreementInvoiceHead invoiceHead);

	/**
	 * 保存批文协议设备发票明细
	 * 
	 * @param commInfo
	 */
	AgreementInvoiceDetail saveAgreementInvoiceDetail(Request request,
			AgreementInvoiceDetail invoiceDetail);

	/**
	 * 删除批文协议设备发票明细
	 * 
	 * @param commInfo
	 */
	void deleteAgreementInvoiceDetail(Request request,
			AgreementInvoiceDetail invoiceDetail);

	/**
	 * 查询批文协议设备保证书表头
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementWarHead(Request request, String sancEmsNo);

	/**
	 * 保存批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	AgreementWarHead saveAgreementWarHead(Request request, Agreement agreement,
			AgreementWarHead warHead);

	/**
	 * 删除批文协议设备保证书表头
	 * 
	 * @param commInfo
	 */
	void deleteAgreementWarHead(Request request, AgreementWarHead warHead);

	/**
	 * 查询批文协议设备保证书明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findAgreementWarDetail(Request request, AgreementWarHead warHead);

	/**
	 * 保存批文协议设备保证书明细
	 * 
	 * @param commInfo
	 */
	AgreementWarDetail saveAgreementWarDetail(Request request,
			AgreementWarDetail warDetail);

	/**
	 * 删除批文协议设备保证书明细
	 * 
	 * @param commInfo
	 */
	void deleteAgreementWarDetail(Request request, AgreementWarDetail warDetail);

	/**
	 * 查询批文协议设备保证书表头
	 * 
	 * @param agreement
	 * @return
	 */
	List findDutyCertHead(Request request, String sancEmsNo);

	/**
	 * 保存批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	DutyCertHead saveDutyCertHead(Request request, Agreement agreement,
			DutyCertHead certHead);

	/**
	 * 删除批文协议设备征免税证表头
	 * 
	 * @param commInfo
	 */
	void deleteDutyCertHead(Request request, DutyCertHead certHead);

	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findDutyCertDetail(Request request, DutyCertHead certHead);

	/**
	 * 查询批文协议设备征免税证明细
	 * 
	 * @param agreement
	 * @return
	 */
	List findDutyCertDetailByCertNo(Request request,
			BaseCustomsDeclaration baseCustomsDeclaration);

	/**
	 * 保存批文协议设备征免税证明细
	 * 
	 * @param commInfo
	 */
	DutyCertDetail saveDutyCertDetail(Request request, DutyCertDetail certDetail);

	/**
	 * 删除批文协议设备征免税证明细
	 * 
	 * @param commInfo
	 */
	void deleteDutyCertDetail(Request request, DutyCertDetail certDetail);

	/**
	 * 新增协议批文
	 * 
	 * @return
	 */
	Agreement addAgreement(Request request);

	/**
	 * 新增协议批文商品明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	List addAgreementCommInfo(Request request, Agreement agreement, List list);

	/**
	 * 抓取不在设备批文中的商品
	 * 
	 * @param agreement
	 * @return
	 */
	List findComplexNotInAgreement(Request request, Agreement agreement);

	/**
	 * 删除取消的协议批文商品明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	List addDeleteAgreementCommInfo(Request request, Agreement agreement,
			List list);

	/**
	 * 进口备案设备明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	List addPORAgreementCommInfo(Request request, Agreement agreement, List list);

	/**
	 * 进口新设备明细
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	List addNewAgreementCommInfo(Request request, Agreement agreement, List list);

	/**
	 * 查询未集结的商品
	 * 
	 * @param agreement
	 * @return
	 */
	List findImpCommInfoNotGroup(Request request, Agreement agreement);

	/**
	 * 设备物品集结
	 * 
	 * @param agreement
	 * @param list
	 * @return
	 */
	List groupImpAgreementCommInfo(Request request, Agreement agreement,
			List list, String groupNo);

	/**
	 * 取消设备物品集结
	 * 
	 * @param list
	 */
	void removeGroupAgreementCommInfo(Request request, List list);

	/**
	 * 自动产生发票，保证书，和征免税证明
	 * 
	 * @param agreement
	 */
	void makeOtherBill(Request request, Agreement agreement,
			boolean isMakeInvoice, boolean isMakeWar, boolean isMakeDutyCert);

	/**
	 * 向海关递单
	 * 
	 * @param agreement
	 */
	void handInBill(Request request, Agreement agreement, List lsGroup);

	/**
	 * 根据主序号查询处于"收件"状态的集结物品项数
	 * 
	 * @param agreement
	 * @param mainNo
	 * @return
	 */
	int findAgreementGroupCountNoHandIn(Request request, Agreement agreement,
			Integer mainNo);

	/**
	 * 取消递单
	 * 
	 * @param agreement
	 */
	void cancelHandInBill(Request request, Agreement agreement,
			Integer changedTimes);

	/**
	 * 抓取正在执行的征免税
	 * 
	 * @return
	 */
	List findDutyCertHeadExecuting(Request request);

	/**
	 * 设备状态查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List findFixassetStatus(Request request, String agreementNo,
			Date beginDate, Date endDate);

	/**
	 * 设备报关单查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List findCustomsDeclarationStat(Request request, String agreementNo,
			Date beginDate, Date endDate, boolean isImport);

	/**
	 * 工厂设备余额查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List findFixassetFactoryRemainMoney(Request request, String agreementNo);

	/**
	 * 海关设备余额查询
	 * 
	 * @param agreementNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List findFixassetCustomsRemainMoney(Request request, String agreementNo);

	/**
	 * 存放位置表查询存放位置表
	 * 
	 * @return
	 */
	List findFixassetLocation(Request request);

	/**
	 * 保存存放位置表
	 * 
	 * @param location
	 */
	FixassetLocation saveFixassetLocation(Request request,
			FixassetLocation location);

	/**
	 * 删除存放位置表
	 * 
	 * @param location
	 */
	void deleteFixassetLocation(Request request, FixassetLocation location);

	/**
	 * 抓取进口报关单项目
	 * 
	 * @return
	 */
	List findCustomsDeclarationFixasset(Request request);

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	List getFixassetLocationChangeInfoFromCustomsBill(Request request,
			List<TempCustomsDeclarationFixasset> list,
			FixassetLocation newLocation, Integer changeType);

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	List getFixassetLocationChangeInfoFromFactory(Request request,
			List<AgreementCommInfo> list, FixassetLocation newLocation,
			Integer changeType);

	/**
	 * 取得异动设备及其数量
	 * 
	 * @param list
	 * @param newLocation
	 * @return
	 */
	List getFixassetLocationChangeInfoFromResultInfo(Request request,
			FixassetLocation newLocation, Integer changeType);

	/**
	 * 取得最终异动单据资料
	 * 
	 * @param list
	 * @param otherInfo
	 * @return
	 */
	List getFixassetLocationChangeBillInfo(Request request, List list,
			TempOtherBillInfo otherInfo);

	/**
	 * 保存最终异动单据资料
	 * 
	 * @param list
	 */
	void saveFixassetLocationChangeBillInfo(Request request, List list,
			int changeType);

	/**
	 * 查询设备异动单据
	 * 
	 * @return
	 */
	List findFixassetLocationChangeBillInfo(Request request);

	/**
	 * 查询设备位置状况
	 * 
	 * @return
	 */
	List findFixassetLocationResultInfo(Request request);

	/**
	 * 从文件导入设备明细资料
	 * 
	 * @param agreement
	 * @param list
	 * @param isChange
	 */
	void importAgreementCommInfo(Request request, Agreement agreement,
			List list, boolean isChange, boolean isAddMoney);
	
	/**
	 * 查找设备批文表体 来自 设备批文表头Id
	 * 
	 * @param parentId
	 *            设备批文表头Id
	 * @return List 是AgreementCommInfo型，设备批文表体
	 */
	public List findAgreementCommInfoByParentId(Request request,String parentId) ;
	
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
			AgreementCommInfo item, Integer newSeqNum) ;
	
	/**
	 * 保存合同料件
	 * 
	 * @param request
	 *            请求控制
	 * @param list
	 *            是ContractImg型，合同料件
	 */
	public List saveAgreementCommInfo(Request request, List list);
}
