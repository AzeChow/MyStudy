package com.bestway.bswmail.qp.action;

import java.util.List;

import com.bestway.bswmail.qp.entity.MailReceiveStatusInfo;
import com.bestway.bswmail.qp.entity.MailSendStatusInfo;
import com.bestway.bswmail.qp.entity.StatusInfo;

public interface BswMailAction {
	String helloWorld(String name);

	void startMailSend(String pwd);

	void stopMailSend();

	void startPushMailReceive(String pwd);

	void stopPushMailReceive();

	int getReturnFileCount();

	boolean canActiveGet();

	void activeGet(String pwd);

	boolean testServiceConnection();

	int getSendMailStatus();

	int getPushReceiveMailStatus();

	String[] getICCardInfo(String seqNo, String pwd);

	String[] getAllUnitInfo(String seqNo, String pwd);

	String[] signMsgData(String seqNo, String pwd, String msgContent);
	/**
	 * 
	 * 获取服务器的连接地址
	*/
	String getConnectionStr();
	
	String getCurrentInfo();

	String testIcCard(String pwd);

	StatusInfo getStatusInfo();

	MailSendStatusInfo getMailSendStatusInfo();

	MailReceiveStatusInfo getMailReceiveStatusInfo();

	/**
	 * 清空发送文件列表
	 * 
	 * 
	 */
	void clearMailSendFileList();

	/**
	 * 请空接收文件列表
	 * 
	 */
	void clearMailReceiveFileList();

	/**
	 * 
	 * 读取文件发送的日志
	 * 
	 */
	String getSendLogFileContent(String logFileName);

	/**
	 * 
	 * 读取回执接收的日志
	 * 
	 */
	String getReceiveLogFileContent(String logFileName);

	/**
	 * 读取发送日志文件名
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List getSendLogFileNameList(String beginDate, String endDate);

	/**
	 * 读取接收日志文件名
	 * 
	 */
	List getReceiveLogFileNameList(String beginDate, String endDate);
	
	/**
	 * 调用海关service
	 */
	String hpServiceExec(String serviceInfostring,String handlestring);
	
	/**
	 * 取得IC卡号
	 */
	String getIcCode();
	/**
	 * 加签数据
	 */
	String signData(String userData,String password);
}
