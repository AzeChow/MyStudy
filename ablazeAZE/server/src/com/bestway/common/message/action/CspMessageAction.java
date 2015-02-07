/*
 * Created on 2004-9-6
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.message.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bestway.bswmail.qp.entity.MailReceiveStatusInfo;
import com.bestway.bswmail.qp.entity.MailSendStatusInfo;
import com.bestway.common.Request;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.cspcard.entity.ICCardInfo;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public interface CspMessageAction {

	/**
	 * 查询报文收发存放路径设定
	 * 
	 * @return
	 */
	CspParameterSet findCspMessageDirSet(Request request);

	/**
	 * 获取卡信息
	 * 
	 * @param seqNo
	 * @param pwd
	 * @return
	 */
	ICCardInfo getICCardInfo(Request request, String seqNo, String pwd);

	/**
	 * 保存 报文收发存放路径设定
	 * 
	 * @param dirSet
	 */
	CspParameterSet saveCspMessageDirSet(Request request, CspParameterSet dirSet);

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	List findCspMessageSend(Request request, String ediType, Date beginDate,
			Date endDate);

	/**
	 * 查询回执信息
	 * 
	 * @return
	 */
	List findCspReceiptResult(Request request, String ediType, Date beginDate,
			Date endDate, String sendFileName);

	/**
	 * 查询回执明细信息
	 * 
	 * @param cspReceiptResult
	 * @return
	 */
	List findCspReceiptResultDetail(Request request,
			CspReceiptResult cspReceiptResult);

	/**
	 * 生成模拟回执
	 * 
	 */
	void makeDefaultReturnInfoMessage(Request request, String fileName,
			Map mapData);

	/**
	 * 查询没有处理的回执文件
	 * 
	 * @return
	 */
	List findNotProcessReturnFile(Request request, String businessType,
			String copEmsNo);

	/**
	 * 删除接收到的回执文件
	 * 
	 * @param cspReceiptResult
	 */
	void deleteNotProcessReturnFile(Request request,
			CspReceiptResult cspReceiptResult);

	// /**
	// * 查询生成QP导入文件信息
	// *
	// * @param sysType
	// * @return
	// */
	// List findCspExportQPMessageInfo(Request request, String sysType);

	// /**
	// * 从服务器端下载文件
	// *
	// * @param backupFileInfo
	// * @return
	// */
	// byte[] downloadMessageFile(Request request,
	// CspExportQPMessageInfo messageInfo);
	//
	// /**
	// * 删处QP导入文件信息
	// *
	// * @param messageInfo
	// */
	// void deleteCspExportQPMessageInfo(Request request,
	// CspExportQPMessageInfo messageInfo);

	/**
	 * 根据模块类型获取报文格式模板文件
	 * 
	 * @param type
	 * @return
	 */
	String getFormatFileNameByType(Request request, String type);

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	Map<String, Object> readCspMessageFileContent(Request request,
			String formatFileName, String messageFileName);

	/**
	 * 读取报文的申报类型
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	String getCspMessageDeclareType(Request request, String messageFileName);

	/**
	 * 重新申报
	 * 
	 * @param file
	 *            要申报的文件
	 * @return
	 */
//	boolean reSendFile(Request request, File file);

	File getSendFileByName(Request request, String fName);

	boolean checkIsSuccess(Request request, String businessType,
			List<CspReceiptResult> list);

	boolean checkIsFailure(Request request, String businessType,
			List<CspReceiptResult> list);

//	/**
//	 * 开始发送邮件
//	 * 
//	 */
//	void startSendMail(Request request);
//
//	/**
//	 * 开始接收邮件
//	 * 
//	 */
//	void startReceiveMail(Request request);
//
//	/**
//	 * 停止发送邮件
//	 * 
//	 */
//	void stopSendMail(Request request);
//
//	/**
//	 * 停止接收邮件
//	 * 
//	 */
//	void stopPushReceiveMail(Request request);
//
//	/**
//	 * 获取服务器上回执文件个数
//	 * 
//	 * @return
//	 */
//	int getReturnFileCount(Request request);
//
//	/**
//	 * 紧急获取邮件
//	 */
//	void activeGet(Request request);
//
//	/**
//	 * 测试邮件服务器和中国电子口岸的连接是否正常
//	 * 
//	 * @return
//	 */
//	boolean testServiceConnection(Request request);
//
//	/**
//	 * 取得发送邮件的状态
//	 * 
//	 * @return
//	 */
//	int getSendMailStatus(Request request);
//
//	/**
//	 * 取得接收邮件的状态
//	 * 
//	 * @return
//	 */
//	int getPushReceiveMailStatus(Request request);

	/**
	 * 验证IC及密码
	 * 
	 * @param pwd
	 * @return
	 */
	String testIcCard(Request request);

//	/**
//	 * 取得邮件服务器发送的状态信息
//	 * 
//	 * @return
//	 */
//	MailSendStatusInfo getMailSendStatusInfo(Request request);
//
//	/**
//	 * 取得邮件服务器接收的状态信息
//	 * 
//	 * @return
//	 */
//	MailReceiveStatusInfo getMailReceiveStatusInfo(Request request);

	/**
	 * 获取邮箱的参数设置
	 * 
	 * @return
	 */
	CspParameterSet getCspParameterSet(Request request);

//	/**
//	 * 清空发送文件列表
//	 * 
//	 * 
//	 */
//	void clearMailSendFileList(Request request);
//
//	/**
//	 * 请空接收文件列表
//	 * 
//	 */
//	void clearMailReceiveFileList(Request request);
//
//	/**
//	 * 
//	 * 读取文件发送的日志
//	 * 
//	 */
//	String getSendLogFileContent(Request request, String logFileName);
//
//	/**
//	 * 
//	 * 读取回执接收的日志
//	 * 
//	 */
//	String getReceiveLogFileContent(Request request, String logFileName);
//
//	/**
//	 * 读取发送日志文件名
//	 * 
//	 * @param beginDate
//	 * @param endDate
//	 * @return
//	 */
//	List getSendLogFileNameList(Request request, String beginDate,
//			String endDate);
//
//	/**
//	 * 读取接收日志文件名
//	 * 
//	 */
//	List getReceiveLogFileNameList(Request request, String beginDate,
//			String endDate);

//	/**
//	 * 
//	 * 获取服务器的连接地址
//	 */
//	String getConnectionStr(Request request);
}