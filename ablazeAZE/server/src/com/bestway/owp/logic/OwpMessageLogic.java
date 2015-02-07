/*
 * Created on 2004-8-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.owp.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcus.system.entity.Company;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.OwpBusinessType;
import com.bestway.common.constant.OwpInOutFlag;
import com.bestway.common.constant.OwpProcessResult;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspReceiptResultDetail;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspMessageLogic;
import com.bestway.owp.dao.OwpMessageDao;
import com.bestway.owp.entity.OwpMessageSend;
import com.bestway.owp.entity.OwpReceiptResult;
import com.bestway.owp.entity.OwpReceiptResultDetail;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class OwpMessageLogic extends CspMessageLogic {

	private OwpMessageDao owpMessageDao;

	public OwpMessageDao getOwpMessageDao() {
		return owpMessageDao;
	}

	public void setOwpMessageDao(OwpMessageDao owpDao) {
		this.owpMessageDao = owpDao;
	}

	@Override
	public String getForamtFilePath() {
		return "com/bestway/owp/messageformat/";
	}

	@Override
	public String getFormatFileNameByType(String type) {
		String formatFileName = "";
		if (OwpBusinessType.OWP_APP.equals(type)) {
			formatFileName = "OwpAppFormat.xml";
		} else if (OwpBusinessType.OWP_BILL_SEND.equals(type)
				|| OwpBusinessType.OWP_BILL_RECV.equals(type)) {
			formatFileName = "OwpBillFormat.xml";
		} else if (OwpBusinessType.CANCEL_BILL.equals(type)) {
			formatFileName = "OwpCancelBillFormat.xml";
		}
		return formatFileName;
	}	
	
	/**
	 * 取得申报类型
	 */
	@Override
	public String getCspMessageDeclareType(String messageFileName) {
		File massageFile = getMessageFile(messageFileName);
		if (massageFile == null) {
			throw new RuntimeException("没有找到文件：" + messageFileName);
		}
		InputStream messageStream;
		try {
			messageStream = new FileInputStream(massageFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		SAXBuilder sax = new SAXBuilder();
		Document doc;
		String declareType = null;
		try {
			doc = sax.build(messageStream);
			Element root = doc.getRootElement();
			Element elemPtsResult = root.getChild("FPT2_APP_OUT_HEAD");
			if (elemPtsResult != null) {
				Element elemDeclareType = elemPtsResult
						.getChild("DECLARE_TYPE");
				if (elemDeclareType != null) {
					declareType = elemDeclareType.getTextTrim();
				}
				return declareType;
			} 				
		} catch (Exception e) {
			// throw RuntimeException(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				messageStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return declareType;
	}

	
	/**
	 * 生成模拟回执的模板文件
	 */
	@Override
	protected String getReturnFormatFileName() {
		return "OwpResultFormat.xml";
	}
	/**
	 * 生成模拟回执的数据
	 */
	@Override
	protected Map<String, List> getDefaultReturnInfoData(File file, Map mapData) {
		Map<String, List> hmData = new HashMap<String, List>();
		InputStream messageStream;
		InputStreamReader inputStreamReader;
		try {
			messageStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(messageStream, "GB2312");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		SAXBuilder sax = new SAXBuilder();
		Document doc;
		try {
			doc = sax.build(inputStreamReader);
			Element root = doc.getRootElement();
			List lsReturnResult = new ArrayList();
			Element elemOwpResult = root.getChild("FPT2_APP_SIGN");
			if (elemOwpResult != null) {
				lsReturnResult = this.getOwpAppDefaultResultData(elemOwpResult,
						mapData);
				hmData.put("OwpResult", lsReturnResult);
				return hmData;
			}
			elemOwpResult = root.getChild("FPT2_BILL_SIGN");
			if (elemOwpResult != null) {
				lsReturnResult = this.getOwpBillDefaultResultData(
						elemOwpResult, mapData);
				hmData.put("OwpResult", lsReturnResult);
				return hmData;
			}
			elemOwpResult = root.getChild("FPT2_CANCEL_BILL_SIGN");
			if (elemOwpResult != null) {
				lsReturnResult = this.getOwpCancelDefaultResultData(
						elemOwpResult, mapData);
				hmData.put("OwpResult", lsReturnResult);
				return hmData;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				messageStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return hmData;
	}
	/**
	 * 获取生成申请表模拟回执的数据
	 * @param rootElement
	 * @param mapData
	 * @return
	 */
	private List getOwpAppDefaultResultData(Element rootElement,
			Map<String, String> mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		OwpReceiptResult tempResult = new OwpReceiptResult();
		Map<String, String> map = getElementChildData(rootElement);
		// 转入转出标记
		tempResult.setInOutFlag(OwpInOutFlag.APP);
			if (mapData.get("seqNo") != null
					&& !"".equals(mapData.get("seqNo"))) {
				tempResult.setSeqNo(mapData.get("seqNo"));
			} else {
				tempResult.setSeqNo(map.get("SEQ_NO"));
			}
		if (mapData.get("customsNo") != null
				&& !"".equals(mapData.get("customsNo"))) {
			tempResult.setEmsNo(mapData.get("customsNo"));
			System.out.println("----------1:" + mapData.get("customsNo"));
		} else {
			Map<String, String> mapTemp = getElementChildData(rootElement
					.getParent(), "FPT2_APP_OUT_HEAD");
			tempResult.setEmsNo(mapTemp.get("APP_NO"));
			System.out.println("----------2:" + mapTemp.get("APP_NO"));
		}
		// 状态标志
		tempResult.setChkMark(OwpProcessResult.CHECK_PASS);
		// }
		// 企业内部编号
		tempResult.setCopEmsNo(map.get("COP_APP_NO"));
		// 企业代码
		tempResult.setTradeCode(map.get("TRADE_CODE"));
		// 回执类型
		tempResult.setApplType(OwpBusinessType.OWP_APP);
		// 序号
		tempResult.setRetNo("1");
		// 通知时间
		tempResult.setNoticeDate(new Date());
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}
	/**
	 * 获取生成收发货单模拟回执的数据
	 * @param elemFptResult
	 * @param mapData
	 * @return
	 */
	private List getOwpBillDefaultResultData(Element elemFptResult,
			Map<String, String> mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		OwpReceiptResult tempResult = new OwpReceiptResult();
		Map<String, String> map = getElementChildData(elemFptResult);
		// 回执类型
		String sysType = map.get("SYS_TYPE");
		if ("0".equals(sysType)) {
			tempResult.setApplType(OwpBusinessType.OWP_BILL_SEND);
			// 转入转出标记
			tempResult.setInOutFlag(OwpInOutFlag.SEND);
		} else if ("1".equals(sysType)) {
			tempResult.setApplType(OwpBusinessType.OWP_BILL_RECV);
			// 转入转出标记
			tempResult.setInOutFlag(OwpInOutFlag.RECV);
		}
		if (mapData.get("customsNo") != null
				&& !"".equals(mapData.get("customsNo"))) {
			// 收送货单编号
			tempResult.setEmsNo(mapData.get("customsNo"));
		}
		// 状态标志
		tempResult.setChkMark(OwpProcessResult.CHECK_PASS);
		// 电子口岸统一编号
		tempResult.setSeqNo(mapData.get("seqNo"));
		// 企业内部编号
		tempResult.setCopEmsNo(map.get("COP_BILL_NO"));
		// 企业代码
		tempResult.setTradeCode(((Company) CommonUtils.getCompany()).getCode());
		// 序号
		tempResult.setRetNo("1");
		// 状态标志
		tempResult.setChkMark(OwpProcessResult.CHECK_PASS);
		// 通知时间
		tempResult.setNoticeDate(new Date());
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}
	/**
	 * 获取生成撤销申请单模拟回执的数据
	 * @param elemFptResult
	 * @param mapData
	 * @return
	 */
	private List getOwpCancelDefaultResultData(Element elemFptResult,
			Map mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		OwpReceiptResult tempResult = new OwpReceiptResult();
		Map<String, String> map = getElementChildData(elemFptResult);
		// 电子口岸统一编号
		Map<String, String> mapItem = getElementChildData(elemFptResult
				.getParent().getChild("FPT2_CANCEL_BILL_HEAD"));
		if (mapItem != null) {
			tempResult.setSeqNo(mapItem.get("SEQ_NO"));
		}
		// 企业内部编号
		tempResult.setCopEmsNo(map.get("OUT_COP_BILL_NO"));
		// 企业代码
		tempResult.setTradeCode(((Company) CommonUtils.getCompany()).getCode());
		// 回执类型
		tempResult.setApplType(OwpBusinessType.CANCEL_BILL);
		// 转入转出标记
		// String appNo = map.get("APP_NO");
		// 撤销类型
		String sysType = map.get("CANCEL _SORT");
		if ("0".equals(sysType)) {
			tempResult.setInOutFlag(OwpInOutFlag.SEND);
		} else if ("1".equals(sysType)) {
			tempResult.setInOutFlag(OwpInOutFlag.RECV);
		}
		// 序号
		tempResult.setRetNo("1");
		// 状态标志
		tempResult.setChkMark(OwpProcessResult.CHECK_PASS);
		// 通知时间
		tempResult.setNoticeDate(new Date());
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}
	/**
	 * 判断回执状态是否通过
	 * @param chkMark
	 * @return
	 */
	private boolean checkPass(String chkMark) {
		if (OwpProcessResult.CHECK_PASS.equals(chkMark)) {
			return true;
		}
		return false;
	}
	/**
	 * 判断回执状态是否退单
	 * @param chkMark
	 * @return
	 */
	private boolean checkFail(String chkMark) {
		if (OwpProcessResult.IMP_CENTDB_FAIL.equals(chkMark)
				|| OwpProcessResult.IN_STOCK_FAIL.equals(chkMark)
				|| OwpProcessResult.IMP_CENTDB_FAIL.equals(chkMark)
				|| OwpProcessResult.CHECK_BACK.equals(chkMark)
				|| OwpProcessResult.QP_FAIL.equals(chkMark)) {
			return true;
		}
		return false;
	}
	/**
	 * 判断回执状态是否通过
	 * @param chkMark
	 * @return
	 */
	@Override
	public boolean checkIsFailure(String businessType,
			List<CspReceiptResult> list, TempCspReceiptResultInfo info) {
		for (CspReceiptResult receiptResult : list) {
			String result = receiptResult.getChkMark();
			if (checkFail(result)) {
				if (info != null) {
					info.setReceiptResult(receiptResult);
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断回执状态是否退单
	 * @param chkMark
	 * @return
	 */
	@Override
	public boolean checkIsSuccess(String businessType,
			List<CspReceiptResult> list, TempCspReceiptResultInfo info) {
		for (CspReceiptResult receiptResult : list) {
			String result = receiptResult.getChkMark();
			if (checkPass(result)) {
				if (info != null) {
					info.setReceiptResult(receiptResult);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取系统类型
	 * @param rootElement
	 * @return
	 */
	private String getBusinessType(Element rootElement) {
		Element element = rootElement.getChild("FPT2_APP_SIGN");
		if (element != null) {
			return OwpBusinessType.OWP_APP;
		}
		element = rootElement.getChild("FPT2_BILL_SIGN");
		if (element != null) {
			Element sysTypeElement = element.getChild("SYS_TYPE");
			if (sysTypeElement != null) {
				if ("0".equals(sysTypeElement.getTextTrim())) {
					return OwpBusinessType.OWP_BILL_SEND;
				} else if ("1".equals(sysTypeElement.getTextTrim())) {
					return OwpBusinessType.OWP_BILL_RECV;
				}
			}
		}
		element = rootElement.getChild("FPT2_CANCEL_BILL_SIGN");
		if (element != null) {
			return OwpBusinessType.CANCEL_BILL;
		}
		return null;
	}
	/**
	 * 保存报文发送信息
	 */
	@Override
	protected void saveMessageSendInfo(String fileName) {
		if (fileName == null || "".equals(fileName)) {
			return;
		}
		InputStream messageStream;
		InputStreamReader inputStreamReader;
		File file = new File(fileName);
		try {
			messageStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(messageStream, "GB2312");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		SAXBuilder sax = new SAXBuilder();
		Document doc;
		try {
			OwpMessageSend owpMessageSend = new OwpMessageSend();
			doc = sax.build(inputStreamReader);
			Element root = doc.getRootElement();
			String sysType = getBusinessType(root);
			System.out.println("------systype:"+sysType);
			if (sysType != null) {
				if (OwpBusinessType.OWP_APP.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT2_APP_SIGN");
					if (map != null) {
						System.out.println("COP_APP_NO"+map.get("COP_APP_NO"));
						owpMessageSend.setCopEmsNo(map.get("COP_APP_NO"));
						owpMessageSend.setSysType(sysType);
						owpMessageSend.setInOutFlag(OwpInOutFlag.APP);
					}
				} else if (OwpBusinessType.OWP_BILL_SEND.equals(sysType)
						|| OwpBusinessType.OWP_BILL_RECV.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT2_BILL_SIGN");
					if (map != null) {
						System.out.println("COP_BILL_NO"+map.get("COP_BILL_NO"));
						owpMessageSend.setCopEmsNo(map.get("COP_BILL_NO"));
						owpMessageSend.setSysType(sysType);
						if(OwpBusinessType.OWP_BILL_SEND.equals(sysType)){
						owpMessageSend.setInOutFlag(OwpInOutFlag.SEND);
						}else if(OwpBusinessType.OWP_BILL_RECV.equals(sysType)){
						owpMessageSend.setInOutFlag(OwpInOutFlag.RECV);
						}
					}
				} else if (OwpBusinessType.CANCEL_BILL.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT2_CANCEL_BILL_SIGN");
					if (map != null) {
						System.out.println("OUT_COP_BILL_NO"+map.get("OUT_COP_BILL_NO"));
						owpMessageSend.setCopEmsNo(map.get("OUT_COP_BILL_NO"));
						owpMessageSend.setSysType(sysType);
					}
				} 
			}
			owpMessageSend.setFileName(file.getName());
			owpMessageSend.setSendRecvTime(new Date());
			owpMessageSend.setSendRecvEr(CommonUtils.getRequest().getUser()
					.getUserName());
			this.getCspMessageDao().saveCspMessageSend(owpMessageSend);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				inputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 读取回执信息
	 */
	@Override
	protected CspReceiptResult readReceiptResultHead(Element rootElement,
			String businessType, String copEmsNo) {
		OwpReceiptResult receiptResult = new OwpReceiptResult();
		Map<String, String> map = this.getElementChildData(rootElement,
				"FPT2_RESULT");
		if (map == null) {
			return null;
		}
		receiptResult.setTradeCode(map.get("TRADE_CODE"));
		System.out.println("bill_no:"+map.get("BILL_NO"));
		receiptResult.setEmsNo(map.get("BILL_NO"));
		System.out.println("COP_NO:"+map.get("COP_NO"));
		
		System.out.println("copEmsNo:"+copEmsNo);
		// 判断内部编号是否一样
		String copNo = map.get("COP_NO");
		if (copNo != null && copEmsNo != null && !"".equals(copEmsNo)
				&& !copEmsNo.equals(copNo)) {
			return null;
		}
		System.out.println("copNo:"+copNo);
		receiptResult.setCopEmsNo(copNo);
		String seqNo = map.get("SEQ_NO");
		receiptResult.setSeqNo(seqNo);
		// 判断业务类型是否一样
		String retType = map.get("RET_TYPE");
		if (businessType != null && !"".equals(businessType)
				&& !businessType.equals(retType)) {
			return null;
		}
		receiptResult.setApplType(retType);
		receiptResult.setChkMark(map.get("CHK_STATUS"));
		String noticeDate = map.get("NOTICE_DATE");
		if (!"".equals(noticeDate)) {
			receiptResult.setNoticeDate(CommonUtils
					.yyyyMMddHHmmSSToDate(noticeDate));
			receiptResult.setNoticeTime(CommonUtils
					.yyyyMMddHHmmSSToDate(noticeDate));
		}
		receiptResult.setInOutFlag(map.get("SORT_FLAG"));
		receiptResult.setRetNo(map.get("RET_NO"));
		return receiptResult;
	}
	/**
	 * 读取回执信息明细
	 */
	@Override
	protected List<CspReceiptResultDetail> readReceiptResultDetail(
			Element rootElement) {
		List<CspReceiptResultDetail> lsResultInfo = new ArrayList<CspReceiptResultDetail>();
		List resultList = rootElement.getChildren("FPT2_RESULT_LIST");
		for (int i = 0; i < resultList.size(); i++) {
			Element elemListDetail = (Element) resultList.get(i);
			if (elemListDetail != null) {
				List elemReturnInfos = elemListDetail
						.getChildren("RET_CONTENT");
				for (int j = 0; j < elemReturnInfos.size(); j++) {
					Element elemReturnInfo = (Element) elemReturnInfos.get(j);
					if (elemReturnInfo != null) {
						CspReceiptResultDetail resultInfo = new OwpReceiptResultDetail();
						resultInfo.setResultInfo(elemReturnInfo.getTextTrim());
						lsResultInfo.add(resultInfo);
					}
				}
			}
		}
		return lsResultInfo;
	}
	/**
	 * 获取业务类型描述
	 */
	@Override
	protected String getBusinessTypeDesc(String businessType) {
		return OwpBusinessType.getOwpBusinessTypeDesc(businessType);
	}

	@Override
	protected String getConvertSignInfo(String fileContent,String signContent) {
		return "zbsqg1/cYYjRFrt/8FcoUk2uIdAMTdqOYc5zrwe6H2grBmzuUYbHxa/C00I2imS5xDoh7/Jo81GXcHA7ujCrUnilS9M+JV8j/8Xa6Ypqn15b7E1YwvMQ8dghCrSZOzZPM765XHLvd/cZcPF56fT5fHaS8rnWSKSy9sBdmcBK6Iw=";
	}

}