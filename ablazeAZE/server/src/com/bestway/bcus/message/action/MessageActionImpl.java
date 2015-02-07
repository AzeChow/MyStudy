/*
 * Created on 2004-8-7
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
import com.bestway.bcus.message.dao.MessageQueryDao;
import com.bestway.bcus.message.dao.ReceiptResultDao;
import com.bestway.bcus.message.entity.CustomReceiptMessage;
import com.bestway.bcus.message.entity.CustomSendMessage;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.message.logic.ExportMessageLogic;
import com.bestway.bcus.message.logic.ExportReceiptCustomLogic;
import com.bestway.bcus.message.logic.ProcessMessageLogic;
import com.bestway.bcus.message.logic.TCSMessageLogic;
import com.bestway.common.BaseActionImpl;
import com.bestway.common.MyFile;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityClassAnnotation;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
// 报文管理与通关申报管理
@AuthorityClassAnnotation(caption = "电子帐册", index = 6)
public class MessageActionImpl extends BaseActionImpl implements MessageAction {
	ExportMessageLogic exportMessageLogic;
	ProcessMessageLogic processMessageLogic;
	ReceiptResultDao receiptResultDao = null;
	TCSMessageLogic tcsMessageLogic;

	public void setTcsMessageLogic(TCSMessageLogic tcsMessageLogic) {
		this.tcsMessageLogic = tcsMessageLogic;
	}

	private MessageQueryDao messageQueryDao = null;
	private ExportReceiptCustomLogic customBillLogic = null;

	public MessageActionImpl() {
		super.setModuleName("Message");
	}

	/**
	 * 生成报文
	 * 
	 * @param request
	 * @param head
	 * @param declareType
	 */
	// @AuthorityFunctionAnnotation(caption = "帐册报文申报", index = 1)
	public String[] exportMessage(Request request, Object head,
			int declareType, List ls) {
		return exportMessageLogic.exportMessage(head, declareType, ls);
	}

	/**
	 * 生成报文
	 * 
	 * @param request
	 * @param head
	 * @param declareType
	 */
	// @AuthorityFunctionAnnotation(caption = "帐册报文申报", index = 1)
	public String[] exportCancelCusMessage(Request request, Object head,
			int declareType, List ls, HashMap<String, Boolean> parameter) {
		return exportMessageLogic.exportCancelCusMessage(head, declareType, ls,
				parameter);
	}

	/**
	 * 发送报关单报文
	 * 
	 * @param id
	 * @return
	 */
	public String sendMessages(Request request, String id, int projectType,
			String supplementListId) {
		return tcsMessageLogic.buildMessage(id, projectType, supplementListId);
	}

	/**
	 * @param exportMessageLogic
	 *            The exportMessageLogic to set.
	 */
	public void setExportMessageLogic(ExportMessageLogic exportMessageLogic) {
		this.exportMessageLogic = exportMessageLogic;
	}

	/**
	 * @return Returns the processMessageLogic.
	 */
	public ProcessMessageLogic getProcessMessageLogic() {
		return processMessageLogic;
	}

	/**
	 * @param processMessageLogic
	 *            The processMessageLogic to set.
	 */
	public void setProcessMessageLogic(ProcessMessageLogic processMessageLogic) {
		this.processMessageLogic = processMessageLogic;
	}

	/**
	 * 回执处理
	 */
	public String processMessage(Request request, File file) {
		return processMessageLogic.processMessage(file);
	}

	public List<MyFile> getUnProcessFiles(Request request, String type) {
		return processMessageLogic.getUnProcessFiles(type);
	}

	public boolean moveFileToProcessedDir(Request request, File file) {
		return processMessageLogic.moveFileToProcessedDir(file);
	}

	public Map<String, String> compareBomMessage(Request request) {
		return processMessageLogic.compareBomMessage();
	}

	/**
	 * 更新bom
	 * 
	 * @param keys
	 */
	public void updateEmsHead2kBomStateByBomKeys(List<Object[]> keys) {
		processMessageLogic.updateEmsHead2kBomStateByBomKeys(keys);
	}

	public List findReceiptResults(Request request) {
		return this.receiptResultDao.findReceiptResults();
	}

	/**
	 * 得到未处理报文
	 */
	public List getUnprocess(Request request, String fileName, String type) {
		return this.receiptResultDao.getUnprocess(fileName, type);
	}

	/**
	 * 得到处理报文
	 */
	public List getprocess(Request request, String fileName) {
		return this.receiptResultDao.getprocess(fileName);
	}

	/**
	 * @return Returns the receiptResultDao.
	 */
	public ReceiptResultDao getReceiptResultDao() {
		return receiptResultDao;
	}

	/**
	 * @param receiptResultDao
	 *            The receiptResultDao to set.
	 */
	public void setReceiptResultDao(ReceiptResultDao receiptResultDao) {
		this.receiptResultDao = receiptResultDao;
	}

	/**
	 * @return Returns the exportMessageLogic.
	 */
	public ExportMessageLogic getExportMessageLogic() {
		return exportMessageLogic;
	}

	/**
	 * @return Returns the messageQueryDao.
	 */
	public MessageQueryDao getMessageQueryDao() {
		return messageQueryDao;
	}

	/**
	 * @param messageQueryDao
	 *            The messageQueryDao to set.
	 */
	public void setMessageQueryDao(MessageQueryDao messageQueryDao) {
		this.messageQueryDao = messageQueryDao;
	}

	public List findMessageSend(Request request, int ediType, Date beginDate,
			Date endDate) {
		return messageQueryDao.findMessageSend(ediType, beginDate, endDate);
	}

	public List findMessageRecv(Request request, int ediType, Date beginDate,
			Date endDate) {
		return messageQueryDao.findMessageRecv(ediType, beginDate, endDate);
	}

	@AuthorityFunctionAnnotation(caption = "电子帐册申报--报文收发信息查询-删除报文", index = 2.99)
	public void deleteMessage(Request request, MessageQuery messageQuery) {
		messageQueryDao.deleteMessage(messageQuery);
	}

	public void deleteAllMessageSend(Request request, int ediType) {
		messageQueryDao.deleteAllMessageSend(ediType);
	}

	public void deleteAllMessageRecv(Request request, int ediType) {
		messageQueryDao.deleteAllMessageRecv(ediType);
	}

	public void saveMessage(Request request, MessageQuery messageQuery)
			throws DataAccessException {
		messageQueryDao.saveMessage(messageQuery);
	}

	// @AuthorityFunctionAnnotation(caption = "报关单报文申报", index = 2)
	public File exportCustomBill(Request request, BaseCustomsDeclaration head,
			String billType, List ls) {
		try {
			return customBillLogic.exportCustomBill(head, billType, ls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return Returns the customBillLogic.
	 */
	public ExportReceiptCustomLogic getCustomBillLogic() {
		return customBillLogic;
	}

	/**
	 * @param customBillLogic
	 *            The customBillLogic to set.
	 */
	public void setCustomBillLogic(ExportReceiptCustomLogic customBillLogic) {
		this.customBillLogic = customBillLogic;
	}

	public void saveMessageQuery(Request request, int sendRecvType,
			int ediType, String messageType, String fileName, String copEmsNo,
			String messageCode, int checkResult) {
		exportMessageLogic.saveMessageQuery(sendRecvType, ediType, messageType,
				fileName, copEmsNo, messageCode, checkResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.message.action.MessageAction#ftpUpload(java.lang.String
	 * [])
	 */
	// public void ftpUpload(Request request, List files, int projectType) {
	// this.customBillLogic.ftpUpload(files, projectType);
	//
	// }
	//

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.message.action.MessageAction#ftpDownload(java.lang.String
	 * [])
	 */
	//
	// @AuthorityFunctionAnnotation(caption = "电子帐册申报--经营范围管理-回执处理", index =
	// 2.1)
	// public List ftpDownload(Request request, int projectType) {
	// return this.customBillLogic.ftpDownload(projectType);
	// }

	// public List ftpDownloadAdviceInfo(Request request, int projectType,
	// String storefile) {
	// return this.customBillLogic.ftpDownloadAdviceInfo(projectType,
	// storefile);
	// }
	//
	// public String checkFtpAdviceInfo(Request request, int projectType) {
	// return this.customBillLogic.checkFtpAdviceInfo(projectType);
	// }

	public void processReturnAdvoiceMessage(Request request, File file,
			int projectType) {
		this.customBillLogic.processReturnAdvoiceMessage(file, projectType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.message.action.MessageAction#processReturnMessage(java
	 * .io.File)
	 */
	public void processReturnMessage(Request request, File file, int projectType) {
		this.customBillLogic.processReturnMessage(file, projectType);
	}

	public void processTcsReturnMessage(Request request, File file,
			int projectType) {
		this.tcsMessageLogic.proccessTCSReturnMessage(file, projectType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bestway.bcus.message.action.MessageAction#saveCustomsMessageQuery
	 * (int, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public void saveCustomsMessageQuery(Request request, int sendRecvType,
			String fileName, String copEmsNo, String messageCode, String ylrh,
			String uniteCode, String customs, String returnNote) {
		exportMessageLogic.saveCustomsMessageQuery(sendRecvType, fileName,
				copEmsNo, messageCode, ylrh, uniteCode, customs, returnNote);

	}

	public File[] getCustomBillSendFiles(Request request) {
		return this.customBillLogic.getCustomBillSendFiles();
	}

	public File[] getCustomBillRecvFiles(Request request) {
		return this.customBillLogic.getCustomBillRecvFiles();
	}

	/**
	 * 浏览报文收发信息
	 * 
	 * @param request
	 */
	@AuthorityFunctionAnnotation(caption = "电子帐册申报--报文收发信息查询", index = 2.99)
	public void checkMessageBrowseAuthority(Request request) {

	}

	public List exportCustom(Request request, BaseCustomsDeclaration head,
			String billType, List ls) throws Exception {
		return this.customBillLogic.exportCustom(head, billType, ls);
	}

	/**
	 * 得到回执内容
	 */
	public List getMessageInfo(Request request, String fileName) {
		return customBillLogic.getMessageInfo(fileName);
	}

	// @AuthorityFunctionAnnotation(caption = "报关单报文申报", index = 2)
	public void checkquanxian(Request request) {

	}

	@AuthorityFunctionAnnotation(caption = "报关单异常回执处理", index = 3)
	public void exceptionfilesdeal(Request request, String filename) {
		this.customBillLogic.exceptionfilesdeal(filename);
	}

	// /**
	// * 集成通报文申报
	// */
	// public String sendMessages(Request request, String id, int projectType,
	// String supplementListId) {
	// return tcsMessageLogic.buildMessage(id, projectType, supplementListId);
	// }

	public List checkTcsParameter(Request request) {
		return tcsMessageLogic.checkTcsParameter();
	}

//	/**
//	 * 上传报文
//	 * 
//	 * @param request
//	 * @param projectType
//	 */
//	public void httpUploadTCS(Request request, int projectType) {
//		this.tcsMessageLogic.httpUpload(projectType);
//
//	}

	/**
	 * 下载回执
	 * 
	 * @param projectType
	 * @return
	 */
	public List httpDownloadTCS(Request request, int projectType,
			Set<String> taskIds) {
		return tcsMessageLogic.httpDownload(projectType, taskIds);
	}

	/**
	 * 下载平台上回执
	 * 
	 * @return
	 */
	public String customsBrokerDownload() {
		return tcsMessageLogic.customsBrokerDownload();
	}

	public List<CustomReceiptMessage> findReceiptMessages(Request request,
			int projectType, Date beginDate, Date endDate) {
		return messageQueryDao.findAllCustomReceiptMessage(projectType,
				beginDate, endDate);
	}

	public List<CustomSendMessage> findSendMessages(Request request,
			int projectType, Date beginDate, Date endDate) {
		return messageQueryDao.findAllCustomSendMessage(projectType, beginDate,
				endDate);
	}

	public BaseCustomsDeclarationCommInfo findBaseCustomsDeclarationCommInfoById(
			Request request, int projectType, String id) {
		return tcsMessageLogic.findBaseCustomsDeclarationCommInfoById(id);
	}

	@Override
	public void customsBrokerDeclaretion(Request request, String id,
			String supplementListId, int projectType,
			CustomsBroker customsBroker) {
		tcsMessageLogic.customsBrokerDeclaretion(id, supplementListId,
				projectType, customsBroker);
	}

	public Boolean testConnect() {
		return tcsMessageLogic.testConnect();
	}
}
