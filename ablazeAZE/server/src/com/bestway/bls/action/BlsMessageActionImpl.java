package com.bestway.bls.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BlsMessageDao;
import com.bestway.bls.entity.BlsAutoBackBillInfo;
import com.bestway.bls.entity.BlsMessageSend;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.logic.BlsMessageLogic;
import com.bestway.bls.schedule.AutoDeclareJobLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
//保税物流-报文信息
@AuthorityClassAnnotation(caption = "保税仓管理", index = 17)
public class BlsMessageActionImpl extends BaseActionImpl implements
		BlsMessageAction {
	private BlsMessageDao blsMessageDao;
	private BlsMessageLogic blsMessageLogic;

	private AutoDeclareJobLogic autoDeclareJobLogic;

	public BlsMessageDao getBlsMessageDao() {
		return blsMessageDao;
	}

	public void setBlsMessageDao(BlsMessageDao blsMessageDao) {
		this.blsMessageDao = blsMessageDao;
	}

	public BlsMessageLogic getBlsMessageLogic() {
		return blsMessageLogic;
	}

	public void setBlsMessageLogic(BlsMessageLogic blsMessageLogic) {
		this.blsMessageLogic = blsMessageLogic;
	}

	// /**
	// * 取得连接地址
	// */
	// public String getServiceUrl(Request request) {
	// return BswHpServiceClient.getInstance(
	// this.blsMessageDao.findBlsParameterSet()).getServiceUrl();
	// }

	public AutoDeclareJobLogic getAutoDeclareJobLogic() {
		return autoDeclareJobLogic;
	}

	public void setAutoDeclareJobLogic(AutoDeclareJobLogic autoDeclareJobLogic) {
		this.autoDeclareJobLogic = autoDeclareJobLogic;
	}

	/**
	 * 查询保税物流参数参数
	 * 
	 * @param parameter
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流参数参数-查询", index = 1)
	public BlsParameterSet findBlsParameterSet(Request request) {
		return this.blsMessageDao.findBlsParameterSet();
	}

	/**
	 * 保税物流参数设置保存
	 * 
	 * @param parameterSet
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流参数设置保存", index = 2)
	public BlsParameterSet saveBlsParameterSet(Request request,
			BlsParameterSet parameterSet) {
		this.blsMessageDao.saveBlsParameterSet(parameterSet);
		return parameterSet;
	}

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "读取报文内容", index = 3)
	public Map<String, Object> readBlsMessageFileContent(Request request,
			String formatFileName, String messageFileName) {
		return this.blsMessageLogic.readBlsMessageFileContent(formatFileName,
				messageFileName);
	}

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "读取报文内容", index = 4)
	public String readBlsMessageFlatFileContent(Request request,
			String messageFileName) {
		return this.blsMessageLogic
				.readBlsMessageFlatFileContent(messageFileName);
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-查询", index = 20)
	public List findBlsMessageSend(Request request, String ediType,
			Date beginDate, Date endDate) {
		return this.blsMessageDao.findBlsMessageSend(ediType, beginDate,
				endDate);
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-查询", index = 20)
	public BlsMessageSend findLastBlsMessageSend(Request request,
			String ediType, String copEmsNo) {
		return this.blsMessageDao.findLastBlsMessageSend(ediType, copEmsNo);
	}

	/**
	 * 查询回执信息
	 * 
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-查询", index = 20)
	public List findBlsReceiptResult(Request request, String ediType,
			Date beginDate, Date endDate, String keyCode) {
		return this.blsMessageDao.findBlsReceiptResult(ediType, beginDate,
				endDate, keyCode);
	}

	/**
	 * 根据系统类型获取报文格式
	 * 
	 * @param type
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "根据系统类型获取报文格式", index = 8)
	public String getFormatFileNameByType(Request request, String type) {
		return this.blsMessageLogic.getFormatFileNameByType(type);
	}

	/**
	 * 从海关查询回执状态
	 * 
	 * @param messageType
	 * @param keyCode
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-查询", index = 20)
	public BlsReceiptResult findBlsReceiptResultFromHG(Request request,
			String messageType, String relateID) {
		return this.blsMessageLogic.findBlsReceiptResultFromHG(messageType,
				relateID, (Company) CommonUtils.getCompany());
	}

	/**
	 * 刷新排程参数
	 */
	@AuthorityFunctionAnnotation(caption = "刷新排程参数", index = 10)
	public void refreshCronExpression(Request request) {
		this.autoDeclareJobLogic.refreshCronExpression((Company) CommonUtils
				.getCompany());
	}

	/**
	 * 查询自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-查询", index = 20)
	public List findBlsAutoDeclareProcessInfo(Request request,
			String serviceType, String inOut, String billCode, Date beginDate,
			Date endDate) {
		return this.blsMessageDao.findBlsAutoDeclareProcessInfo(
				(Company) CommonUtils.getCompany(), serviceType, inOut,
				billCode, beginDate, endDate);
	}

	/**
	 * 查询自动申报退单记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-查询", index = 20)
	public List findBlsAutoBackBillInfo(Request request, String serviceType,
			String inOut, String billCode) {
		return this.blsMessageDao.findBlsAutoBackBillInfo((Company) CommonUtils
				.getCompany(), serviceType, inOut, billCode);
	}

	/**
	 * 删除自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-删除", index = 13)
	public void deleteBlsAutoDeclareProcessInfo(Request request,
			Date beginDate, Date endDate) {
		this.blsMessageDao.deleteBlsAutoDeclareProcessInfo(
				(Company) CommonUtils.getCompany(), beginDate, endDate);
	}

	/**
	 * 删除自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-删除", index = 13)
	public void deleteBlsAutoDeclareProcessInfo(Request request, List list) {
		this.blsMessageDao.deleteBlsAutoDeclareProcessInfo(list);
	}

	/**
	 * 删除自动申报退单记录
	 * 
	 * @param blsAutoBackBillInfo
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-删除", index = 13)
	public void deleteBlsAutoBackBillInfo(Request request,
			BlsAutoBackBillInfo blsAutoBackBillInfo) {
		this.blsMessageDao.deleteBlsAutoBackBillInfo(blsAutoBackBillInfo);
	}

	/**
	 * 删除自动申报退单记录
	 * 
	 * @param blsAutoBackBillInfo
	 */
	@AuthorityFunctionAnnotation(caption = "保税物流报文-删除", index = 13)
	public void deleteBlsAutoBackBillInfo(Request request, List list) {
		this.blsMessageDao.deleteAll(list);
	}
	/**
	 * 判断是否是自动申报
	 * @param request
	 * @return
	 */
	@AuthorityFunctionAnnotation(caption = "判断是否是自动申报", index = 17)
	public boolean checkIsAutoDeclare(Request request) {
		BlsParameterSet blsParameterSet = blsMessageDao
				.findBlsParameterSet((Company) CommonUtils.getCompany());
		return (blsParameterSet.getIsAutoDeclare() == null ? false
				: blsParameterSet.getIsAutoDeclare());
	}
}
