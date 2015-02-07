/*
 * Created on 2004-8-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.message.logic;

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

import com.bestway.common.CommonUtils;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.CspProcessResult;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspReceiptResultDetail;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspMessageLogic;
import com.bestway.dzsc.message.entity.DzscMessageSend;
import com.bestway.dzsc.message.entity.DzscReceiptResult;
import com.bestway.dzsc.message.entity.DzscReceiptResultDetail;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DzscMessageLogic extends CspMessageLogic {

	@Override
	public boolean checkIsFailure(String businessType,
			List<CspReceiptResult> list, TempCspReceiptResultInfo info) {
		for (CspReceiptResult receiptResult : list) {
			String result = receiptResult.getChkMark();
			if (checkFail(businessType, result)) {
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
			if (checkPass(businessType, result)) {
				if (info != null) {
					info.setReceiptResult(receiptResult);
				}
				return true;
			}
		}
		return false;
	}

	private boolean checkPass(String businessType, String chkMark) {
		if (DzscBusinessType.CUSTOMS_BILL_LIST.equals(businessType)) {
			if (CspProcessResult.QP_IMPDB_SUCCESSD.equals(chkMark)) {
				return true;
			}
		}
		if (CspProcessResult.CHECK_PASS.equals(chkMark)) {
			return true;
		}
		return false;
	}

	private boolean checkFail(String businessType, String chkMark) {
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
	protected Map<String, List> getDefaultReturnInfoData(File file, Map mapData) {
		Map<String, List> hmData = new HashMap<String, List>();
		List<CspSignInfo> lsSignInfo = new ArrayList<CspSignInfo>();
		CspSignInfo signInfo = this.getCspPtsSignInfo(null,
				ManageObject.MANUFACTURE_COP);
		signInfo.setSignDate(new Date());
		signInfo.setCopNo("ssssssssss");
		signInfo.setColDcrTime(0);
		signInfo.setSysType(DzscBusinessType.MATERIAL);
		lsSignInfo.add(signInfo);

		String emsNo = (mapData == null ? ""
				: (mapData.get("emsNo") == null ? "" : mapData.get("emsNo")
						.toString()));
		String corrEmsNo = (mapData == null ? ""
				: (mapData.get("corrEmsNo") == null ? "" : mapData.get(
						"corrEmsNo").toString()));
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
			if (DzscBusinessType.CUSTOMS_BILL_LIST.equals(elemBusinessType
					.getTextTrim())) {
				chkMark = CspProcessResult.QP_IMPDB_SUCCESSD;
			}
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
			if (corrEmsNo == null || "".equals(corrEmsNo.trim())) {
				corrEmsNo = "BS" + sdf.format(new Date());
			}
			tempResult.setCorrEmsNo(corrEmsNo);
			List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
			lsReturnResult.add(tempResult);
			signInfo.setSysType(elemBusinessType.getTextTrim());
			hmData.put("PtsSignInfo", lsSignInfo);
			hmData.put("PtsResult", lsReturnResult);
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

	@Override
	public String getForamtFilePath() {
		// TODO Auto-generated method stub
		return "com/bestway/dzsc/message/messageformat/";
	}

	@Override
	public String getFormatFileNameByType(String type) {
		String formatFileName = "";
		if (DzscBusinessType.MATERIAL.equals(type)) {
			formatFileName = "MaterialApplyFormat.xml";
		} else if (DzscBusinessType.MATERIAL_BOM.equals(type)) {
			formatFileName = "MaterialBOMApplyFormat.xml";
		} else if (DzscBusinessType.INNER_MERGE.equals(type)) {
			formatFileName = "DzscInnerMergeFormat.xml";
		} else if (DzscBusinessType.EMS_POR_WJ.equals(type)) {
			formatFileName = "DzscEmsPorWjFormat.xml";
		} else if (DzscBusinessType.EMS_POR_BILL.equals(type)) {
			formatFileName = "DzscEmsPorFormat.xml";
		} else if (DzscBusinessType.FASCICULE.equals(type)) {
			formatFileName = "DzscEmsPorFasFormat.xml";
		} else if (DzscBusinessType.CUSTOMS_BILL_LIST.equals(type)) {
			formatFileName = "DzscCustomsBillListFormat.xml";
		} else if (DzscBusinessType.CANCEL_AFTER_VERIFICA.equals(type)) {
			formatFileName = "DzscContractCavFormat.xml";
		} else if (DzscBusinessType.CHECK.equals(type)) {
			formatFileName = "DzscCheckFormat.xml";
		}
		return formatFileName;
	}

	@Override
	protected String getReturnFormatFileName() {
		return "DzscReturnReceiptFormat.xml";
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
			// Class messageSendClass
			// =this.getCspMessageDao().getMessageSendClass();
			// if (messageSendClass == null) {
			// return;
			// }
			// CspMessageSend bcsMessageSend = (CspMessageSend) messageSendClass
			// .newInstance();
			DzscMessageSend dzscMessageSend = new DzscMessageSend();
			doc = sax.build(inputStreamReader);
			Element root = doc.getRootElement();
			Element elemPtsResult = root.getChild("PTS_SIGN");
			Element elemResultField = elemPtsResult.getChild("TRADE_CODE");
			if (elemResultField != null) {
				// bcsMessageSend.setTradeCode(elemResultField.getTextTrim());
			}
			elemResultField = elemPtsResult.getChild("COP_NO");
			if (elemResultField != null) {
				dzscMessageSend.setCopEmsNo(elemResultField.getTextTrim());
			}
			elemResultField = elemPtsResult.getChild("SYS_TYPE");
			if (elemResultField != null) {
				dzscMessageSend.setSysType(elemResultField.getTextTrim());
			}
			elemResultField = elemPtsResult.getChild("DECLARE_TYPE");
			if (elemResultField != null) {
				dzscMessageSend.setMessageType(elemResultField.getTextTrim());
			}
			dzscMessageSend.setFileName(file.getName());
			dzscMessageSend.setSendRecvTime(new Date());
			dzscMessageSend.setSendRecvEr(CommonUtils.getRequest().getUser()
					.getUserName());
			this.getCspMessageDao().saveCspMessageSend(dzscMessageSend);
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
		CspReceiptResult receiptResult = new DzscReceiptResult();
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
						CspReceiptResultDetail resultInfo = new DzscReceiptResultDetail();
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
		return DzscBusinessType.getBusinessTypeDesc(businessType);
	}

	@Override
	protected String getConvertSignInfo(String fileContent,String signContent) {
		return "zbsqg1/cYYjRFrt/8FcoUk2uIdAMTdqOYc5zrwe6H2grBmzuUYbHxa/C00I2imS5xDoh7/Jo81GXcHA7ujCrUnilS9M+JV8j/8Xa6Ypqn15b7E1YwvMQ8dghCrSZOzZPM765XHLvd/cZcPF56fT5fHaS8rnWSKSy9sBdmcBK6Iw=";
	}

}