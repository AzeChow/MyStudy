/*
 * Created on 2004-9-6
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.custombase.entity.parametercode.CustomsBroker;
import com.bestway.bcus.message.entity.CustomReceiptMessage;
import com.bestway.bcus.message.entity.CustomSendMessage;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.common.MyFile;
import com.bestway.common.Request;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("rawtypes")
public interface MessageAction {
	/**
	 * 生成报文
	 * 
	 * @param request
	 * @param head
	 * @param declareType
	 */
	String[] exportMessage(Request request, Object head, int declareType,
			List ls);

	String[] exportCancelCusMessage(Request request, Object head,
			int declareType, List ls, HashMap<String, Boolean> parameter);

	String processMessage(Request request, File file);

	List<MyFile> getUnProcessFiles(Request request, String type);

	boolean moveFileToProcessedDir(Request request, File file);
	
	Map<String, String> compareBomMessage(Request request);
	/**
	 * 更新bom
	 * @param keys
	 */
	void updateEmsHead2kBomStateByBomKeys(List<Object[]> keys);

	List findReceiptResults(Request request);

	List findMessageSend(Request request, int ediType, Date beginDate,
			Date endDate);

	List findMessageRecv(Request request, int ediType, Date beginDate,
			Date endDate);

	void deleteMessage(Request request, MessageQuery messageQuery);

	void deleteAllMessageSend(Request request, int ediType);

	void deleteAllMessageRecv(Request request, int ediType);

	void saveMessage(Request request, MessageQuery messageQuery)
			throws DataAccessException;

	File exportCustomBill(Request request, BaseCustomsDeclaration head,
			String billType, List ls);

	void saveMessageQuery(Request request, int sendRecvType, int ediType,
			String messageType, String fileName, String copEmsNo,
			String messageCode, int checkResult);
//
//	void ftpUpload(Request request, List files, int projectType);
//
//	List ftpDownload(Request request, int projectType);

//	List ftpDownloadAdviceInfo(Request request, int projectType,
//			String storefile);
//
//	String checkFtpAdviceInfo(Request request, int projectType);

	void processReturnAdvoiceMessage(Request request, File file, int projectType);

	void processReturnMessage(Request request, File file, int projectType);

	void processTcsReturnMessage(Request request, File file, int projectType);

	File[] getCustomBillSendFiles(Request request);

	File[] getCustomBillRecvFiles(Request request);

	void saveCustomsMessageQuery(Request request, int sendRecvType,
			String fileName, String copEmsNo, String messageCode, String ylrh,
			String uniteCode, String customs, String returnNote);

	/**
	 * 浏览报文收发信息
	 * 
	 * @param request
	 */
	void checkMessageBrowseAuthority(Request request);

	List exportCustom(Request request, BaseCustomsDeclaration head,
			String billType, List ls) throws Exception;

	List getMessageInfo(Request request, String fileName);

	List getUnprocess(Request request, String fileName, String type);

	List getprocess(Request request, String fileName);

	void checkquanxian(Request request);

	void exceptionfilesdeal(Request request, String filename);

	/**
	 * 集成通报文申报
	 */
	String sendMessages(Request request, String id, int projectType,String supplementListId);

	public List checkTcsParameter(Request request);

//	void httpUploadTCS(Request request, int projectType);

	List httpDownloadTCS(Request request, int projectType, Set<String> taskIds);
	
	String customsBrokerDownload();

	List<CustomReceiptMessage> findReceiptMessages(Request request,
			int projectType, Date beginDate, Date endDate);

	List<CustomSendMessage> findSendMessages(Request request, int projectType,
			Date beginDate, Date endDate);
	BaseCustomsDeclarationCommInfo findBaseCustomsDeclarationCommInfoById(Request request,int projectType,String id);
	
	void customsBrokerDeclaretion(Request request, String id,
			String supplementListId, int projectType, CustomsBroker customsBroker);
	
	Boolean testConnect();
}