package com.bestway.bls.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.entity.BlsAutoBackBillInfo;
import com.bestway.bls.entity.BlsMessageSend;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.CollateBind;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;

/**
 * 保税物流报文参数操作接口 
 * @author Administrator
 *
 */
public interface BlsMessageAction {
//	/**
//	 * 取得连接地址
//	 */
//	String getServiceUrl(Request request);

	/**
	 * 查询保税物流参数参数
	 * 
	 * @param parameter
	 */
	BlsParameterSet findBlsParameterSet(Request request);

	/**
	 * 保税物流参数设置保存
	 * 
	 * @param parameterSet
	 */
	BlsParameterSet saveBlsParameterSet(Request request,
			BlsParameterSet parameterSet);

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	Map<String, Object> readBlsMessageFileContent(Request request,
			String formatFileName, String messageFileName);

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	String readBlsMessageFlatFileContent(Request request, String messageFileName);

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	List findBlsMessageSend(Request request, String ediType, Date beginDate,
			Date endDate);

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	BlsMessageSend findLastBlsMessageSend(Request request, String ediType,
			String copEmsNo);

	/**
	 * 查询回执信息
	 * 
	 * @return
	 */
	List findBlsReceiptResult(Request request, String ediType, Date beginDate,
			Date endDate, String keyCode);

	/**
	 * 根据系统类型获取报文格式
	 * 
	 * @param type
	 * @return
	 */
	String getFormatFileNameByType(Request request, String type);

	/**
	 * 从海关查询回执状态
	 * 
	 * @param messageType
	 * @param keyCode
	 * @return
	 */
	BlsReceiptResult findBlsReceiptResultFromHG(Request request,
			String messageType, String relateID);
	
	/**
	 * 刷新排程参数
	 */
	void refreshCronExpression(Request request);
	
	/**
	 * 查询自动申报或回执处理记录
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	List findBlsAutoDeclareProcessInfo(Request request,String serviceType,
			String inOut,String billCode,Date beginDate,Date endDate);
	
	/**
	 * 查询自动申报退单记录
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	List findBlsAutoBackBillInfo(Request request,String serviceType,
			String inOut,String billCode);
	
	/**
	 * 删除自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	void deleteBlsAutoDeclareProcessInfo(Request request,
			Date beginDate, Date endDate) ;
	/**
	 * 删除自动申报或回执处理记录
	 * 
	 * @param company
	 * @param serviceType
	 * @param inOut
	 * @param billCode
	 * @return
	 */
	void deleteBlsAutoDeclareProcessInfo(Request request,List list) ;
	/**
	 * 删除自动申报退单记录
	 * 
	 * @param blsAutoBackBillInfo
	 */
	void deleteBlsAutoBackBillInfo(Request request,
			BlsAutoBackBillInfo blsAutoBackBillInfo) ;
	
	/**
	 * 删除自动申报退单记录
	 * 
	 * @param blsAutoBackBillInfo
	 */
	void deleteBlsAutoBackBillInfo(Request request,
			List list);
	
	/**
	 * 判断是否是自动申报
	 * @param request
	 * @return
	 */
	boolean checkIsAutoDeclare(Request request) ;
}
