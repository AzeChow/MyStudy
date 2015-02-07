/*
 * Created on 2004-8-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.message.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcs.message.entity.BcsMessageSend;
import com.bestway.bcs.message.entity.BcsReceiptResult;
import com.bestway.bcs.message.entity.BcsReceiptResultDetail;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.CspProcessResult;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspReceiptResultDetail;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspMessageLogic;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class BcsMessageLogic extends CspMessageLogic {

	@Override
	public String getForamtFilePath() {
		return "com/bestway/bcs/message/messageformat/";
	}

	@Override
	public String getFormatFileNameByType(String type) {
		String formatFileName = "";
		if (BcsBusinessType.EMS_POR_WJ.equals(type)) {
			formatFileName = "BcsDictPorFormat.xml";
		} else if (BcsBusinessType.EMS_POR_BILL.equals(type)) {
			formatFileName = "BcsContractFormat.xml";
		} else if (BcsBusinessType.CANCEL_AFTER_VERIFICA.equals(type)) {
			formatFileName = "BcsContractCavFormat.xml";
		}
		return formatFileName;
	}

	@Override
	protected String getReturnFormatFileName() {
		return "BcsReturnReceiptFormat.xml";
	}

	@Override
	protected Map<String, List> getDefaultReturnInfoData(File file, Map mapData) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		CspSignInfo signInfo = this.getCspPtsSignInfo(null,
				ManageObject.MANUFACTURE_COP);
		signInfo.setSignDate(new Date());
		signInfo.setCopNo("ssssssssss");
		signInfo.setColDcrTime(0);
//		signInfo.setSysType(DzscBusinessType.MATERIAL);
		lsSignInfo.add(signInfo);

		String emsNo = (mapData == null ? ""
				: (mapData.get("emsNo") == null ? "" : mapData.get("emsNo")
						.toString()));

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
			Element elemPtsResult = root.getChild("PTS_SIGN");
			if (elemPtsResult == null) {
				return hmData;
			}
			Element elemTradeCode = elemPtsResult.getChild("TRADE_CODE");
			if (elemTradeCode == null) {
				System.out.println("--------------------- TRADE_CODE is null");
				return hmData;
			}
			Element elemCopEmsNo = elemPtsResult.getChild("COP_NO");
			if (elemCopEmsNo == null) {
				System.out.println("--------------------- COP_NO is null");
				return hmData;
			}
			Element elemBusinessType = elemPtsResult.getChild("SYS_TYPE");
			if (elemBusinessType == null) {
				System.out.println("--------------------- SYS_TYPE is null");
				return hmData;
			}
			Element elemDeclareType = elemPtsResult.getChild("DECLARE_TYPE");
			if (elemDeclareType == null) {
				System.out
						.println("--------------------- DECLARE_TYPE is null");
				return hmData;
			}
			Element elemColDcrTime = elemPtsResult.getChild("COL_DCR_TIME");
			if (elemColDcrTime == null) {
				System.out
						.println("--------------------- COL_DCR_TIME is null");
				return hmData;
			}
			Element elemSeqNo = elemPtsResult.getChild("CERT_SEQ_NO");
			if (elemSeqNo == null) {
				System.out.println("--------------------- CERT_SEQ_NO is null");
				return hmData;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
			if (emsNo == null || "".equals(emsNo.trim())) {
				emsNo = "BS" + sdf.format(new Date());
			}
			String chkMark = CspProcessResult.CHECK_PASS;
//			if (DzscBusinessType.CUSTOMS_BILL_LIST.equals(elemBusinessType
//					.getTextTrim())) {
//				chkMark = CspProcessResult.QP_IMPDB_SUCCESSD;
//			}
			Date noticeDate = new Date();

			CspReceiptResult tempResult = new CspReceiptResult();
			tempResult.setTradeCode(elemTradeCode.getTextTrim());
			tempResult.setCopEmsNo(elemCopEmsNo.getTextTrim());
			tempResult.setApplType(elemBusinessType.getTextTrim());
			tempResult.setDeclareType(elemDeclareType.getTextTrim());
			tempResult.setColDcrTime(elemColDcrTime.getTextTrim());
			tempResult.setSeqNo(elemSeqNo.getTextTrim());
			tempResult.setEmsNo(emsNo);
			tempResult.setChkMark(chkMark);
			tempResult.setNoticeDate(noticeDate);
			tempResult.setNoticeTime(noticeDate);
			String corrEmsNo = "BS" + sdf.format(new Date());
			tempResult.setCorrEmsNo(corrEmsNo);
			List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
			lsReturnResult.add(tempResult);
			signInfo.setSysType(elemBusinessType.getTextTrim());
			hmData.put("BcsPtsSignInfo", lsSignInfo);
			hmData.put("BcsPtsResult", lsReturnResult);
		} catch (Exception e) {
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

	private boolean checkPass(String chkMark) {
		if (CspProcessResult.CHECK_PASS.equals(chkMark)) {
			return true;
		}
		return false;
	}

	private boolean checkFail(String chkMark) {
		if (CspProcessResult.BACK_BILL.equals(chkMark)
				|| CspProcessResult.RE_DECLARE.equals(chkMark)
				|| CspProcessResult.IMP_CENTDB_FAIL.equals(chkMark)
				|| CspProcessResult.SEND_CUSTOMS_FAIL.equals(chkMark)
				|| CspProcessResult.IMP_QPDB_FAIL.equals(chkMark)
				|| CspProcessResult.QP_FAIL.equals(chkMark)) {
			return true;
		}
		return false;
	}

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
			BcsMessageSend bcsMessageSend = new BcsMessageSend();
			doc = sax.build(inputStreamReader);
			Element root = doc.getRootElement();
			Element elemPtsResult = root.getChild("PTS_SIGN");
			Element elemResultField = elemPtsResult.getChild("TRADE_CODE");
			if (elemResultField != null) {
				// bcsMessageSend.setTradeCode(elemResultField.getTextTrim());
			}
			elemResultField = elemPtsResult.getChild("COP_NO");
			if (elemResultField != null) {
				bcsMessageSend.setCopEmsNo(elemResultField.getTextTrim());
			}
			elemResultField = elemPtsResult.getChild("SYS_TYPE");
			if (elemResultField != null) {
				bcsMessageSend.setSysType(elemResultField.getTextTrim());
			}
			elemResultField = elemPtsResult.getChild("DECLARE_TYPE");
			if (elemResultField != null) {
				bcsMessageSend.setMessageType(elemResultField.getTextTrim());
			}
			bcsMessageSend.setFileName(file.getName());
			bcsMessageSend.setSendRecvTime(new Date());
			bcsMessageSend.setSendRecvEr(CommonUtils.getRequest().getUser()
					.getUserName());
			this.getCspMessageDao().saveCspMessageSend(bcsMessageSend);
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

	@Override
	protected CspReceiptResult readReceiptResultHead(Element rootElement,
			String businessType, String copEmsNo) {
		CspReceiptResult receiptResult = new BcsReceiptResult();
		Element elemPtsResult = rootElement.getChild("PTS_RESULT");
		if (elemPtsResult == null) {
			return null;
		}
		Element elemResultField = elemPtsResult.getChild("TRADE_CODE");
		if (elemResultField != null) {
			receiptResult.setTradeCode(elemResultField.getTextTrim());
		}

		elemResultField = elemPtsResult.getChild("COP_EMS_NO");
		// 判断内部编号是否一样
		if (elemResultField != null) {
			if (copEmsNo != null && !"".equals(copEmsNo)
					&& !elemResultField.getTextTrim().equals(copEmsNo)) {
				return null;
			}
			receiptResult.setCopEmsNo(elemResultField.getTextTrim());
		}
		elemResultField = elemPtsResult.getChild("COL_DCR_TIME");
		if (elemResultField != null) {
			receiptResult.setColDcrTime(elemResultField.getTextTrim());
		}
		elemResultField = elemPtsResult.getChild("SEQ_NO");
		if (elemResultField != null) {
			receiptResult.setSeqNo(elemResultField.getTextTrim());
		}
		elemResultField = elemPtsResult.getChild("EMS_NO");
		if (elemResultField != null) {
			receiptResult.setEmsNo(elemResultField.getTextTrim());
		}
		elemResultField = elemPtsResult.getChild("APPL_TYPE");
		if (elemResultField != null) {
			// 判断业务类型是否一样
			if (businessType != null && !"".equals(businessType)
					&& !elemResultField.getTextTrim().equals(businessType)) {
				return null;
			}
			receiptResult.setApplType(elemResultField.getTextTrim());
		}
		elemResultField = elemPtsResult.getChild("DECLARE_TYPE");
		if (elemResultField != null) {
			receiptResult.setDeclareType(elemResultField.getTextTrim());
		}
		elemResultField = elemPtsResult.getChild("CHK_MARK");
		if (elemResultField != null) {
			receiptResult.setChkMark(elemResultField.getTextTrim());
		}
		String noticeDate = "";
		elemResultField = elemPtsResult.getChild("NOTICE_DATE");
		if (elemResultField != null) {
			noticeDate = elemResultField.getTextTrim();
			elemResultField = elemPtsResult.getChild("NOTICE_TIME");
			if (elemResultField != null) {
				noticeDate += elemResultField.getTextTrim();
			}
		}
		if (!"".equals(noticeDate)) {
			Date date = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssSS");
			try {
				date = dateFormat.parse(noticeDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			receiptResult.setNoticeDate(date);
			receiptResult.setNoticeTime(date);
		}
		Element elemEdiHeadEml = rootElement.getChild("TR_EDI_HEAD_EML");
		if (elemEdiHeadEml != null) {
			Element elemCorrEmsNo = elemEdiHeadEml.getChild("CORR_EMS_NO");
			if (elemCorrEmsNo != null) {
				receiptResult.setCorrEmsNo(elemCorrEmsNo.getTextTrim());
			}
		}
		return receiptResult;
	}

	@Override
	protected List<CspReceiptResultDetail> readReceiptResultDetail(
			Element rootElement) {
		List<CspReceiptResultDetail> lsResultInfo = new ArrayList<CspReceiptResultDetail>();
		List resultList = rootElement.getChild("PTS_RESULT_LIST").getChildren(
				"PTS_RESULT_LIST_DETAIL");
		for (int i = 0; i < resultList.size(); i++) {
			Element elemListDetail = (Element) resultList.get(i);
			if (elemListDetail != null) {
				List elemReturnInfos = elemListDetail
						.getChildren("RESULT_INFO");
				for (int j = 0; j < elemReturnInfos.size(); j++) {
					Element elemReturnInfo = (Element) elemReturnInfos.get(j);
					if (elemReturnInfo != null) {
						CspReceiptResultDetail resultInfo = new BcsReceiptResultDetail();
						resultInfo.setResultInfo(elemReturnInfo.getTextTrim());
						lsResultInfo.add(resultInfo);
					}
				}
			}
		}
		return lsResultInfo;
	}

	@Override
	protected String getBusinessTypeDesc(String businessType) {
		return BcsBusinessType.getBcsBusinessTypeDesc(businessType);
	}

	@Override
	protected String getConvertSignInfo(String fileContent,String signContent) {
		// TODO Auto-generated method stub
		return "zbsqg1/cYYjRFrt/8FcoUk2uIdAMTdqOYc5zrwe6H2grBmzuUYbHxa/C00I2imS5xDoh7/Jo81GXcHA7ujCrUnilS9M+JV8j/8Xa6Ypqn15b7E1YwvMQ8dghCrSZOzZPM765XHLvd/cZcPF56fT5fHaS8rnWSKSy9sBdmcBK6Iw=";
	}

}