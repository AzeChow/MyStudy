/*
 * Created on 2004-8-7
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
import com.bestway.common.BaseActionImpl;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.message.dao.CspMessageDao;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.logic.CspMessageLogic;
import com.bestway.cspcard.entity.ICCardInfo;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
//@AuthorityClassAnnotation(caption = "电子化手册", index = 5)
public class CspMessageActionImpl extends BaseActionImpl implements
		CspMessageAction {
	private CspMessageLogic cspMessageLogic;

	private CspMessageDao cspMessageDao;

	public CspMessageDao getCspMessageDao() {
		return cspMessageDao;
	}

	public void setCspMessageDao(CspMessageDao bcsMessageQueryDao) {
		this.cspMessageDao = bcsMessageQueryDao;
	}

	public CspMessageLogic getCspMessageLogic() {
		return cspMessageLogic;
	}

	public void setCspMessageLogic(CspMessageLogic bcsExportMessageLogic) {
		this.cspMessageLogic = bcsExportMessageLogic;
	}

	/**
	 * 查询报文收发存放路径设定
	 * 
	 * @return
	 */
	public CspParameterSet findCspMessageDirSet(Request request) {
		return this.cspMessageDao.findCspParameterSet();
	}

	/**
	 * 获取卡信息
	 * 
	 * @param seqNo
	 * @param pwd
	 * @return
	 */
	public ICCardInfo getICCardInfo(Request request, String seqNo, String pwd) {
		return this.cspMessageLogic.getICCardInfo(seqNo, pwd);
	}

	/**
	 * 保存 报文收发存放路径设定
	 * 
	 * @param dirSet
	 */
	public CspParameterSet saveCspMessageDirSet(Request request,
			CspParameterSet dirSet) {
		this.cspMessageDao.saveCspMessageDirSet(dirSet);
		return dirSet;
	}

	/**
	 * 生成模拟回执
	 * 
	 */
	public void makeDefaultReturnInfoMessage(Request request, String fileName,
			Map mapData) {
		this.cspMessageLogic.makeDefaultReturnInfoMessage(fileName, mapData);
	}

	/**
	 * 查询发送报文信息
	 * 
	 * @return
	 */
	public List findCspMessageSend(Request request, String ediType,
			Date beginDate, Date endDate) {
		return this.cspMessageDao.findCspMessageSend(ediType, beginDate,
				endDate);
	}

	/**
	 * 查询回执信息
	 * 
	 * @return
	 */
	public List findCspReceiptResult(Request request, String ediType,
			Date beginDate, Date endDate, String sendFileName) {
		return this.cspMessageDao.findCspReceiptResult(ediType, beginDate,
				endDate, sendFileName);
	}

	/**
	 * 查询回执明细信息
	 * 
	 * @param bcsReceiptResult
	 * @return
	 */
	public List findCspReceiptResultDetail(Request request,
			CspReceiptResult bcsReceiptResult) {
		return this.cspMessageDao.findCspReceiptResultDetail(bcsReceiptResult);
	}

	/**
	 * 查询没有处理的回执文件
	 * 
	 * @return
	 */
	public List findNotProcessReturnFile(Request request, String businessType,
			String copEmsNo) {
		return this.cspMessageLogic.findNotProcessReturnFile(businessType,
				copEmsNo);
	}

	/**
	 * 删除接收到的回执文件
	 * 
	 * @param cspReceiptResult
	 */
	public void deleteNotProcessReturnFile(Request request,
			CspReceiptResult cspReceiptResult) {
		this.cspMessageLogic.deleteNotProcessReturnFile(cspReceiptResult);
	}

	//
	// /**
	// * 查询生成QP导入文件信息
	// *
	// * @param sysType
	// * @return
	// */
	// public List findCspExportQPMessageInfo(Request request, String sysType) {
	// return this.cspMessageDao.findCspExportQPMessageInfo(sysType);
	// }
	//
	// /**
	// * 从服务器端下载文件
	// *
	// * @param backupFileInfo
	// * @return
	// */
	// public byte[] downloadMessageFile(Request request,
	// CspExportQPMessageInfo messageInfo) {
	// return this.cspMessageLogic.downloadMessageFile(messageInfo);
	// }

	// /**
	// * 删处QP导入文件信息
	// *
	// * @param messageInfo
	// */
	// public void deleteCspExportQPMessageInfo(Request request,
	// CspExportQPMessageInfo messageInfo) {
	// this.cspMessageLogic.deleteCspExportQPMessageInfo(messageInfo);
	// }

	/**
	 * 根据模块类型获取报文格式模板文件
	 * 
	 * @param type
	 * @return
	 */
	public String getFormatFileNameByType(Request request, String type) {
		return this.cspMessageLogic.getFormatFileNameByType(type);
	}

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	public Map<String, Object> readCspMessageFileContent(Request request,
			String formatFileName, String messageFileName) {
		return this.cspMessageLogic.readCspMessageFileContent(formatFileName,
				messageFileName);
	}

	/**
	 * 读取报文的申报类型
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	public String getCspMessageDeclareType(Request request,
			String messageFileName) {
		return this.cspMessageLogic.getCspMessageDeclareType(messageFileName);
	}

//	/**
//	 * 重新申报
//	 */
//	@AuthorityFunctionAnnotation(caption = "重新申报", index = 1)
//	public boolean reSendFile(Request request, File file) {
//		return this.cspMessageLogic.reSendFile(file);
//	}

	public File getSendFileByName(Request request, String fName) {
		return this.cspMessageLogic.getSendFileByName(fName);
	}

	public boolean checkIsSuccess(Request request, String businessType,
			List<CspReceiptResult> list) {
		return this.cspMessageLogic.checkIsSuccess(businessType, list, null);
	}

	public boolean checkIsFailure(Request request, String businessType,
			List<CspReceiptResult> list) {
		return this.cspMessageLogic.checkIsFailure(businessType, list, null);
	}

//	/**
//	 * 开始发送邮件
//	 * 
//	 */
//	public void startSendMail(Request request) {
//		this.cspMessageLogic.startSendMail();
//	}
//
//	/**
//	 * 开始接收邮件
//	 * 
//	 */
//	public void startReceiveMail(Request request) {
//		this.cspMessageLogic.startReceiveMail();
//	}
//
//	/**
//	 * 停止发送邮件
//	 * 
//	 */
//	public void stopSendMail(Request request) {
//		this.cspMessageLogic.stopSendMail();
//	}
//
//	/**
//	 * 停止接收邮件
//	 * 
//	 */
//	public void stopPushReceiveMail(Request request) {
//		this.cspMessageLogic.stopPushReceiveMail();
//	}
//
//	/**
//	 * 紧急获取邮件
//	 */
//	public void activeGet(Request request) {
//		this.cspMessageLogic.activeGet();
//	}
//
//	/**
//	 * 获取服务器上回执文件个数
//	 * 
//	 * @return
//	 */
//	public int getReturnFileCount(Request request) {
//		return this.cspMessageLogic.getReturnFileCount();
//	}
//
//	/**
//	 * 测试邮件服务器和中国电子口岸的连接是否正常
//	 * 
//	 * @return
//	 */
//	public boolean testServiceConnection(Request request) {
//		return this.cspMessageLogic.testServiceConnection();
//	}
//
//	/**
//	 * 取得发送邮件的状态
//	 * 
//	 * @return
//	 */
//	public int getSendMailStatus(Request request) {
//		return this.cspMessageLogic.getSendMailStatus();
//	}
//
//	/**
//	 * 取得接收邮件的状态
//	 * 
//	 * @return
//	 */
//	public int getPushReceiveMailStatus(Request request) {
//		return this.cspMessageLogic.getPushReceiveMailStatus();
//	}

	/**
	 * 验证IC及密码
	 * 
	 * @param pwd
	 * @return
	 */
	public String testIcCard(Request request) {
		return this.cspMessageLogic.testIcCard();
	}

//	/**
//	 * 取得邮件服务器发送的状态信息
//	 * 
//	 * @return
//	 */
//	public MailSendStatusInfo getMailSendStatusInfo(Request request) {
//		return this.cspMessageLogic.getMailSendStatusInfo();
//	}
//
//	/**
//	 * 取得邮件服务器接收的状态信息
//	 * 
//	 * @return
//	 */
//	public MailReceiveStatusInfo getMailReceiveStatusInfo(Request request) {
//		return this.cspMessageLogic.getMailReceiveStatusInfo();
//	}

	/**
	 * 获取邮箱的参数设置
	 * 
	 * @return
	 */
	public CspParameterSet getCspParameterSet(Request request) {
		return this.cspMessageLogic.getCspParameterSet();
	}

//	/**
//	 * 清空发送文件列表
//	 * 
//	 * 
//	 */
//	public void clearMailSendFileList(Request request) {
//		this.cspMessageLogic.clearMailSendFileList();
//	}
//
//	/**
//	 * 请空接收文件列表
//	 * 
//	 */
//	public void clearMailReceiveFileList(Request request) {
//		this.cspMessageLogic.clearMailReceiveFileList();
//	}
//
//	/**
//	 * 
//	 * 读取文件发送的日志
//	 * 
//	 */
//	public String getSendLogFileContent(Request request, String logFileName) {
//		return this.cspMessageLogic.getSendLogFileContent(logFileName);
//	}
//
//	/**
//	 * 
//	 * 读取回执接收的日志
//	 * 
//	 */
//	public String getReceiveLogFileContent(Request request, String logFileName) {
//		return this.cspMessageLogic.getReceiveLogFileContent(logFileName);
//	}
//
//	/**
//	 * 读取发送日志文件名
//	 * 
//	 * @param beginDate
//	 * @param endDate
//	 * @return
//	 */
//	public List getSendLogFileNameList(Request request, String beginDate,
//			String endDate) {
//		return this.cspMessageLogic.getSendLogFileNameList(beginDate, endDate);
//	}
//
//	/**
//	 * 读取接收日志文件名
//	 * 
//	 */
//	public List getReceiveLogFileNameList(Request request, String beginDate,
//			String endDate) {
//		return this.cspMessageLogic.getReceiveLogFileNameList(beginDate,
//				endDate);
//	}
//
//	/**
//	 * 
//	 * 获取服务器的连接地址
//	 */
//	public String getConnectionStr(Request request) {
//		return this.cspMessageLogic.getConnectionStr();
//	}
}
