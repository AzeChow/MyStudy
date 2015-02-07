package com.bestway.bcus.message.logic;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;

import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.message.dao.MessageQueryDao;
import com.bestway.bcus.message.entity.CustomReceiptMessage;
import com.bestway.common.CommonUtils;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.TCSCommonCode;

public class TCSProccessReturnLogic {
	private static final Logger logger = Logger
			.getLogger(TCSProccessReturnLogic.class);

	/**
	 * 处理 TcsFlow201Response 类型回执
	 * 
	 * @param root
	 * @param projectType
	 * @param file
	 * @param messageTime
	 */
	static void proccessTcsFlow201Response(Element root, int projectType,
			File file, Date messageTime, EncDao encDao,
			MessageQueryDao messageQueryDao) {
		// 信息体
		Element elemBody = root.getChild("MessageBody");
		if (elemBody == null) {
			throw new RuntimeException("回执报文中没有包含：MessageBody");
		}
		Namespace ns = Namespace
				.getNamespace("http://www.chinaport.gov.cn/tcs/v2");

		// 信息体-201流程体响应
		Element elemTcsFlow201Response = elemBody.getChild(
				"TcsFlow201Response", ns);
		if (elemTcsFlow201Response == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201Response");
		}

		// 信息体-201流程体响应-头信息
		Element elemResponseHead = elemTcsFlow201Response.getChild(
				"ResponseHead", ns);
		if (elemResponseHead == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201Response-->ResponseHead");
		}

		// 信息体-201流程体响应-头信息-任务编号
		Element elemTaskId = elemResponseHead.getChild("TaskId", ns);
		if (elemTaskId == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201Response-->ResponseHead-->TaskId");
		}

		// 信息体-201流程体响应-头信息-任务编号-值
		String taskId = elemTaskId.getTextTrim();
		if (taskId.equals("")) {
			throw new RuntimeException(
					"回执报文中：MessageBody-->TcsFlow201Response-->ResponseHead-->TaskId 值为空");
		}

		// 信息体-201流程体响应-头信息-返回代码
		Element elemResultCode = elemResponseHead.getChild("ResultCode", ns);
		if (elemResultCode == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201Response-->ResponseHead-->ResultCode");
		}

		// 信息体-201流程体响应-头信息-返回内容-值
		String resultValue = "";
		Element elemResultValue = elemResponseHead.getChild("ResultValue", ns);
		if (elemResultValue != null) {
			resultValue = elemResultValue.getTextTrim();
		}

		// 信息体-201流程体响应-头信息-返回代码-值
		String resultCode = elemResultCode.getTextTrim();
		if (resultValue == null || resultValue.equals("")) {
			Element elemResultList = elemTcsFlow201Response.getChild(
					"ResponseList", ns);
			if (elemResultList == null) {
				throw new RuntimeException(
						"回执报文中没有包含：MessageBody-->TcsFlow201Response-->ResponseList");
			}

			Element elemActionResult = elemResultList.getChild("ActionResult",
					ns);
			if (elemActionResult == null) {
				throw new RuntimeException(
						"回执报文中没有包含：MessageBody-->TcsFlow201Response-->ResponseList-->ActionResult");
			}

			elemResultCode = elemActionResult.getChild("ResultCode", ns);
			if (elemResultCode == null) {
				throw new RuntimeException(
						"回执报文中没有包含：MessageBody-->TcsFlow201Response-->ResponseList-->ActionResult-->ResultCode");
			}
			elemResultValue = elemActionResult.getChild("ResultValue", ns);

			resultCode = elemResultCode.getTextTrim();
			resultValue = elemResultValue.getTextTrim();

			if (resultCode == null || resultCode.equals("")) {
				throw new RuntimeException("回执报文中：ResultCode 值为空");
			}
		}

		BaseCustomsDeclaration customsDeclaration = encDao
				.findCustomsDeclarationByTcsTaskId(projectType, taskId);
		if (customsDeclaration == null) {
			throw new RuntimeException("没有找到TaskId为：" + taskId + "的报关单");
		}

		customsDeclaration.setTcsResultTime(messageTime);
		customsDeclaration.setTcsResultMessage(resultValue);

		encDao.saveCustomsDeclaration(customsDeclaration);

		CustomReceiptMessage receiptMessage = new CustomReceiptMessage();

		receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
		receiptMessage.setProjectType(projectType);
		receiptMessage.setCompany(CommonUtils.getCompany());
		receiptMessage.setSuccess("[" + resultCode + "]:" + resultValue);
		receiptMessage.setReturnDate(new Date());
		receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCreateDate(new Date());
		receiptMessage.setFileName(file.getName());
		receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCustoms(customsDeclaration
				.getCustomsDeclarationCode());
		receiptMessage.setSeqNo(customsDeclaration.getCustomsDeclarationCode());
		receiptMessage.setYlrh(customsDeclaration
				.getPreCustomsDeclarationCode());
		receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
		receiptMessage.setUniteCode(customsDeclaration.getUnificationCode());

		logger.info("保存处理回执记录！");
		messageQueryDao.saveCustomReceiptMessage(receiptMessage);
	}

	/**
	 * 处理 TcsFlow201 类型回执
	 * 
	 * @param root
	 * @param projectType
	 * @param file
	 * @param messageTime
	 */
	static void proccessTcsFlow201(Element root, int projectType, File file,
			Date messageTime, EncDao encDao, MessageQueryDao messageQueryDao) {
		// 信息体
		Element elemBody = root.getChild("MessageBody");
		if (elemBody == null) {
			throw new RuntimeException("回执报文中没有包含：MessageBody");
		}
		Namespace ns = Namespace
				.getNamespace("http://www.chinaport.gov.cn/tcs/v2");

		Namespace ns_supret = Namespace
				.getNamespace("http://www.chinaport.gov.cn/lis/supret");

		// 信息体-201流程体
		Element elemTcsFlow201 = elemBody.getChild("TcsFlow201", ns);
		if (elemTcsFlow201 == null) {
			throw new RuntimeException("回执报文中没有包含：MessageBody-->TcsFlow201");
		}

		/*
		 * 	<TcsFlow> 
		 * 		<MessageId>TCS001-20120912100440310</MessageId> 
		 * 		<BpNo xsi:nil="true" /> 
		 * 		<ActionList> 
		 * 			<ActionId>a02</ActionId>
		 * 		</ActionList> 
		 * 		<TaskId>TX181609770020120911888888</TaskId> 
		 * 		<TaskNote xsi:nil="true" /> 
		 * 		<CorpTaskId xsi:nil="true" /> 
		 * 	</TcsFlow>
		 */
		// 信息体-201流程体-流程体
		Element elemTcsFlow = elemTcsFlow201.getChild("TcsFlow", ns);
		if (elemTcsFlow == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201-->TcsFlow");
		}

		// 信息体-201流程体-流程体-任务编号节点
		Element elemTaskId = elemTcsFlow.getChild("TaskId", ns);
		if (elemTaskId == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201-->TcsFlow-->TaskId");
		}

		// 信息体-201流程体-流程体-平台编号节点-值
		String taskId = elemTaskId.getTextTrim();
		if (taskId.equals("")) {
			throw new RuntimeException(
					"回执报文中：MessageBody-->TcsFlow201-->TcsFlow-->TaskId 值为空");
		}
		// 查询出报关单
		BaseCustomsDeclaration customsDeclaration = encDao
				.findCustomsDeclarationByTcsTaskId(projectType, taskId);
		
		if(customsDeclaration == null) {
			throw new RuntimeException("没有找到TaskId为：" + taskId + "的报关单。");
		}
		
		// 信息体-201流程体-tcs返回数据
		Element elemTcsData = elemTcsFlow201.getChild("TcsData", ns);
		if (elemTcsData == null) {
			throw new RuntimeException(
					"回执报文中没有包含：MessageBody-->TcsFlow201-->TcsData");
		}
		
		// 信息体-201流程体-tcs返回数据-？
		Element supDecResponseInformationElement = elemTcsData.getChild(
				"SupDecResponseInformation", ns_supret);
		if (supDecResponseInformationElement != null) {
			proccessTcsFlow201A02(supDecResponseInformationElement, projectType, file, messageTime,
					taskId, ns_supret, encDao, messageQueryDao);
			return;
		}
		/**
		 * 这里判断是否是转关的第八个回执 如果TcsData底下包含TRN_DATA元素，则表示是第八个回执。
		 * 如果是第八回执按第八个回执的格式特殊处理。 处理完以后直接返回。
		 */
		// 信息体-201流程体-tcs返回数据-转关结果
		Element elemTRN_DATA = elemTcsData.getChild("TRN_DATA");
		if (elemTRN_DATA != null) {
			Element elemTRN_RESULT = elemTRN_DATA.getChild("TRN_RESULT");
			if (elemTRN_RESULT == null) {
				throw new RuntimeException(
						"回执报文中没有包含：MessageBody-->TcsFlow201-->TcsData-->TRN_DATA-->TRN_RESULT");
			}

			String noticeDate = "";
			Element elemNoticeDate = elemTRN_RESULT.getChild("NOTICE_DATE");
			if (elemNoticeDate != null) {
				noticeDate = elemNoticeDate.getTextTrim();
			}

			String note = "";
			Element elemNote = elemTRN_RESULT.getChild("NOTE");
			if (elemNote != null) {
				note = elemNote.getTextTrim();
			}
			
			if (note == null) {
				note = "";
			} else {
				note = note.trim().replace(" ", "");
			}
			if (customsDeclaration.getTcsResultMessage() == null) {
				customsDeclaration.setTcsResultMessage(note);
			} else {
				
				if (!customsDeclaration.getTcsResultMessage().contains(note)) {
					customsDeclaration.setTcsResultMessage(customsDeclaration
							.getTcsResultMessage()
							+ "\n" + note);
					if (customsDeclaration.getTcsResultMessage().length() > 200) {
						customsDeclaration.setTcsResultMessage(customsDeclaration
								.getTcsResultMessage().substring(200));
					}
				}
			}
			
			

			encDao.saveCustomsDeclaration(customsDeclaration);

			CustomReceiptMessage receiptMessage = new CustomReceiptMessage();
			receiptMessage.setNoticeDate(noticeDate);
			receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
			receiptMessage.setProjectType(projectType);
			receiptMessage.setCompany(CommonUtils.getCompany());
			receiptMessage.setSuccess(note == null ? "" : note);
			receiptMessage.setReturnDate(new Date());
			receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptMessage.setCreateDate(new Date());
			receiptMessage.setFileName(file.getName());
			receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptMessage.setCustoms(customsDeclaration
					.getCustomsDeclarationCode());
			receiptMessage.setSeqNo(customsDeclaration
					.getCustomsDeclarationCode());
			receiptMessage.setYlrh(customsDeclaration
					.getPreCustomsDeclarationCode());
			receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
			receiptMessage
					.setUniteCode(customsDeclaration.getUnificationCode());
			messageQueryDao.saveCustomReceiptMessage(receiptMessage);

			return;
		}
		
	

		// 信息体-201流程体-tcs返回数据-申请结果
		Element elemENTRY_RESULT = elemTcsData.getChild("ENTRY_RESULT", ns);
		
		//SupDecReturnInformation
		// 信息体-201流程体-tcs返回数据-响应结果
		Element elemSupDecReturnInformation = elemTcsData.getChild("SupDecReturnInformation", ns_supret);
		
		if (elemSupDecReturnInformation == null
				&& elemENTRY_RESULT == null) {
			return ;
		}
			
		if (elemENTRY_RESULT != null) {
			// 信息体-201流程体-tcs返回数据-申请结果-响应通道
			Element elemResultCode = elemENTRY_RESULT.getChild("Channel", ns);
			if (elemResultCode == null) {
				throw new RuntimeException(
						"回执报文中没有包含：MessageBody-->TcsFlow201-->TcsData-->ENTRY_RESULT-->Channel");
			}

			// 信息体-201流程体-tcs返回数据-申请结果-响应通道-值
			String resultCode = elemResultCode.getTextTrim();
			if (resultCode.equals("")) {
				throw new RuntimeException(
						"回执报文中：MessageBody-->TcsFlow201-->TcsData-->ENTRY_RESULT-->Channel 值为空");
			}

			// 信息体-201流程体-tcs返回数据-申请结果-报关单号-值
			String entryNo = "";
			Element elemEntryNo = elemENTRY_RESULT.getChild("EntryNo", ns);
			if (elemEntryNo != null) {
				entryNo = elemEntryNo.getTextTrim();
			}

			// // 信息体-201流程体-tcs返回数据-申请结果-统一编号-值
			String eportNo = "";
			Element elemEportNo = elemENTRY_RESULT.getChild("EportNo", ns);
			if (elemEportNo != null) {
				eportNo = elemEportNo.getTextTrim();
			}

			// 信息体-201流程体-tcs返回数据-申请结果-回执日期-值
			String noticeDate = "";
			Element elemNoticeDate = elemENTRY_RESULT.getChild("NoticeDate", ns);
			if (elemNoticeDate != null) {
				noticeDate = elemNoticeDate.getTextTrim();
				if (noticeDate != null && !"".equals(noticeDate)) {
					noticeDate = noticeDate.replace("T", "");
					String format = null;
					if (noticeDate.length() == 14) {
						format = "yyyyMMddHHmmss";
					} else if (noticeDate.length() == 16) {
						format = "yyyyMMddHHmmssSS";
					} else {
						format = "yyyyMMdd";
					}
					DateFormat df = new SimpleDateFormat(format);
					Date date = null;
					try {
						date = df.parse(noticeDate);
						DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
						noticeDate = df1.format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(noticeDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			customsDeclaration.setDeclarationDate(date);
			// 信息体-201流程体-tcs返回数据-申请结果-回执信息-值
			String resultValue = "";
			Element elemResultValue = elemENTRY_RESULT.getChild(
					"ResultInformation", ns);
			if (elemResultValue != null) {
				resultValue = elemResultValue.getTextTrim();
			}

			// 如果取ResultInformation为空则取Note
			if ("".equals(resultValue)) {
				Element elemNote = elemENTRY_RESULT.getChild("Note", ns);
				if (elemNote != null) {
					resultValue = elemNote.getTextTrim();
				}
			}

			// 信息体-201流程体-tcs返回数据-申请结果-申报口岸代码-值
			String eportLocationCode = "";
			Element elemEportLocationCode = elemENTRY_RESULT.getChild(
					"EportLocationCode", ns);
			if (elemEportLocationCode != null) {
				eportLocationCode = elemEportLocationCode.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-申报单位-值
			String corporationName = "";
			Element elemCorporationName = elemENTRY_RESULT.getChild(
					"CorporationName", ns);
			if (elemCorporationName != null) {
				corporationName = elemCorporationName.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-报关员代码-值
			String entrydeclarantNo = "";
			Element elemEntrydeclarantNo = elemENTRY_RESULT.getChild(
					"EntrydeclarantNo", ns);
			if (elemEntrydeclarantNo != null) {
				entrydeclarantNo = elemEntrydeclarantNo.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-经营单位代码-值
			String corporationCustomsCode = "";
			Element elemCorporationCustomsCode = elemENTRY_RESULT.getChild(
					"CorporationCustomsCode", ns);
			if (elemCorporationCustomsCode != null) {
				corporationCustomsCode = elemCorporationCustomsCode.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-货场代码-值
			String cyNo = "";
			Element elemCyNo = elemENTRY_RESULT.getChild("CYNo", ns);
			if (elemCyNo != null) {
				cyNo = elemCyNo.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-保税仓库代码-值
			String warehouseNo = "";
			Element elemWarehouseNo = elemENTRY_RESULT.getChild("WarehouseNo", ns);
			if (elemWarehouseNo != null) {
				warehouseNo = elemWarehouseNo.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-进出口日期-值
			String importExportDate = "";
			Element elemImportExportDate = elemENTRY_RESULT.getChild(
					"ImportExportDate", ns);
			if (elemImportExportDate != null) {
				importExportDate = elemImportExportDate.getTextTrim().split("T")[0];
				if(importExportDate!=null
						&&StringUtils.isNotEmpty(importExportDate.trim())
						&&NumberUtils.isNumber(importExportDate)){
					if(importExportDate.length()>=8){
						importExportDate = importExportDate.substring(0, 8);
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
						try {
							Date impExpDate = simpleDateFormat.parse(importExportDate);
							customsDeclaration.setImpExpDate(impExpDate);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			// 信息体-201流程体-tcs返回数据-申请结果-件数-值
			String packages = "";
			Element elemPackages = elemENTRY_RESULT.getChild("Packages", ns);
			if (elemPackages != null) {
				packages = elemPackages.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-提单号-值
			String billOfLadingNo = "";
			Element elemBillOfLadingNo = elemENTRY_RESULT.getChild(
					"BillOfLadingNo", ns);
			if (elemBillOfLadingNo != null) {
				billOfLadingNo = elemBillOfLadingNo.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-运输方式-值
			String meansOfTransportMode = "";
			Element elemMeansOfTransportMode = elemENTRY_RESULT.getChild(
					"MeansOfTransportMode", ns);
			if (elemMeansOfTransportMode != null) {
				meansOfTransportMode = elemMeansOfTransportMode.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-航班号-值
			String meansOfTransportId = "";
			Element elemMeansOfTransportId = elemENTRY_RESULT.getChild(
					"MeansOfTransportId", ns);
			if (elemMeansOfTransportId != null) {
				meansOfTransportId = elemMeansOfTransportId.getTextTrim();
			}
			// 信息体-201流程体-tcs返回数据-申请结果-净重-值
			String netWeight = "";
			Element elemNetWeight = elemENTRY_RESULT.getChild("NetWeight", ns);
			if (elemNetWeight != null) {
				netWeight = elemNetWeight.getTextTrim();
			}

			// 信息体-201流程体-tcs返回数据-申请结果-毛重-值
			String grossWeight = "";
			Element elemGrossWeight = elemENTRY_RESULT.getChild("GrossWeight", ns);
			if (elemGrossWeight != null) {
				grossWeight = elemGrossWeight.getTextTrim();
			}

			// 信息体-201流程体-tcs返回数据-申请结果-申报时间-值
			String declareDate = "";
			Element elemDeclareDate = elemENTRY_RESULT.getChild("DeclareDate", ns);
			if (elemDeclareDate != null) {
				declareDate = elemDeclareDate.getTextTrim();
			}

			/*
			 * 回写报关单
			 */
			// 没有生效才回写，生效后不回写
			if (!new Boolean(true).equals(customsDeclaration.getEffective())) {
				// 回写统一编号
				if (eportNo != null && !"".equals(eportNo)) {
					customsDeclaration.setUnificationCode(eportNo);
				}
				// 回写报关单编号
				if (!"".equals(entryNo)) {
					customsDeclaration.setCustomsDeclarationCode(entryNo);
				}
			}

			// 回写回执时间
			customsDeclaration.setTcsResultTime(messageTime);
			// 回写回执结果
			String msg = ("[" + resultCode + "]:" + resultValue);
			if (customsDeclaration.getTcsResultMessage() == null) {
				customsDeclaration.setTcsResultMessage(msg);
			} else {
				if (!customsDeclaration.getTcsResultMessage().contains(msg)) {
					customsDeclaration.setTcsResultMessage(customsDeclaration
							.getTcsResultMessage()
							+ "\n" + msg);
					if (customsDeclaration.getTcsResultMessage().length() > 200) {
						customsDeclaration.setTcsResultMessage(customsDeclaration
								.getTcsResultMessage().substring(200));
					}
				}
			}

			// 011海关已接收
			
			// 016报关单已审结
			// 017放行交单

			// 022已结关
			// 023进出口审结/查验/放行通知

			// 025海关已放行
			if ((TCSCommonCode.CHANNEL_011.equals(resultCode))
					|| TCSCommonCode.CHANNEL_016.equals(resultCode)
					|| TCSCommonCode.CHANNEL_017.equals(resultCode)
					|| TCSCommonCode.CHANNEL_022.equals(resultCode)
					|| TCSCommonCode.CHANNEL_023.equals(resultCode)
					|| TCSCommonCode.CHANNEL_025.equals(resultCode)) {
				// 以上这几种情况要设置 报关单生效
				customsDeclaration.setEffective(true);
				
			}
//			// 025海关已放行
//			if ((TCSCommonCode.CHANNEL_011.equals(resultCode) && TCSCommonCode.TcsEntryType_noPaper
//					.equals(customsDeclaration.getTcsEntryType()))
//					|| TCSCommonCode.CHANNEL_016.equals(resultCode)
//					|| TCSCommonCode.CHANNEL_017.equals(resultCode)
//					|| TCSCommonCode.CHANNEL_022.equals(resultCode)
//					|| TCSCommonCode.CHANNEL_023.equals(resultCode)
//					|| TCSCommonCode.CHANNEL_025.equals(resultCode)) {
//				// 以上这几种情况要设置 报关单生效
//				customsDeclaration.setEffective(true);
//			}
			encDao.saveCustomsDeclaration(customsDeclaration);

			CustomReceiptMessage receiptMessage = new CustomReceiptMessage();
			receiptMessage.setNoticeDate(noticeDate);
			receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
			receiptMessage.setProjectType(projectType);
			receiptMessage.setCompany(CommonUtils.getCompany());
			receiptMessage.setSuccess("[" + resultCode + "]:" + resultValue);
			receiptMessage.setReturnDate(new Date());
			receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptMessage.setCreateDate(new Date());
			receiptMessage.setFileName(file.getName());
			receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptMessage.setCustoms(customsDeclaration
					.getCustomsDeclarationCode());
			receiptMessage.setSeqNo(customsDeclaration.getCustomsDeclarationCode());
			receiptMessage.setYlrh(customsDeclaration
					.getPreCustomsDeclarationCode());
			receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
			receiptMessage.setUniteCode(customsDeclaration.getUnificationCode());
			receiptMessage.setBillOfLadingNo(billOfLadingNo);
			receiptMessage.setCorporationCustomsCode(corporationCustomsCode);
			receiptMessage.setCorporationName(corporationName);
			receiptMessage.setCyNo(cyNo);
			receiptMessage.setEntrydeclarantNo(entrydeclarantNo);
			receiptMessage.setEportLocationCode(eportLocationCode);
			receiptMessage.setImportExportDate(importExportDate);
			receiptMessage.setMeansOfTransportId(meansOfTransportId);
			receiptMessage.setMeansOfTransportMode(meansOfTransportMode);
			receiptMessage.setNetWeight(netWeight);
			receiptMessage.setPackages(packages);
			receiptMessage.setWarehouseNo(warehouseNo);
			receiptMessage.setGrossWeight(grossWeight);
			receiptMessage.setDeclareDate(declareDate);

			logger.info("保存处理回执记录！");
			messageQueryDao.saveCustomReceiptMessage(receiptMessage);
		} else if(elemSupDecReturnInformation != null) {
			// 信息体-201流程体-tcs返回数据-响应结果-统一编号-值
			String eportNo = "";
			Element elemEportNo = elemSupDecReturnInformation.getChild("EportNo", ns_supret);
			if (elemEportNo != null) {
				eportNo = elemEportNo.getTextTrim();
			}
			
			// 信息体-201流程体-tcs返回数据-响应结果-报关单号-值
			String entryNo = "";
			Element elemEntryNo = elemSupDecReturnInformation.getChild("EntryId", ns_supret);
			if (elemEntryNo != null) {
				entryNo = elemEntryNo.getTextTrim();
			}
			
			// 信息体-201流程体-tcs返回数据-响应结果-响应通道-值
			String resultCode = "";
			Element elemChannel = elemSupDecReturnInformation.getChild("Channel", ns_supret);
			if (elemChannel != null) {
				resultCode = elemChannel.getTextTrim();
			}
			
			// 信息体-201流程体-tcs返回数据-响应结果-回执信息-值
			String resultValue = "";
			Element elemResultValue = elemSupDecReturnInformation.getChild(
					"Note", ns_supret);
			if (elemResultValue != null) {
				resultValue = elemResultValue.getTextTrim();
			}
			
			
			// 信息体-201流程体-tcs返回数据-申请结果-回执日期-值
			String noticeDate = "";
			Element elemNoticeDate = elemSupDecReturnInformation.getChild("NoticeDate", ns_supret);
			if (elemNoticeDate != null) {
				noticeDate = elemNoticeDate.getTextTrim();
				if (noticeDate != null && !"".equals(noticeDate)) {
					noticeDate = noticeDate.replace("T", "");
					String format = null;
					if (noticeDate.length() == 14) {
						format = "yyyyMMddHHmmss";
					} else if (noticeDate.length() == 16) {
						format = "yyyyMMddHHmmssSS";
					} else {
						format = "yyyyMMdd";
					}
					DateFormat df = new SimpleDateFormat(format);
					Date date = null;
					try {
						date = df.parse(noticeDate);
						DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
						noticeDate = df1.format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = sdf.parse(noticeDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			customsDeclaration.setDeclarationDate(date);
			/*
			 * 回写报关单
			 */
			
			// 没有生效才回写，生效后不回写
			if (!new Boolean(true).equals(customsDeclaration.getEffective())) {
				// 回写统一编号
				if (eportNo != null && !"".equals(eportNo)) {
					customsDeclaration.setUnificationCode(eportNo);
				}
				// 回写报关单编号
				if (!"".equals(entryNo)) {
					customsDeclaration.setCustomsDeclarationCode(entryNo);
				}
			}
			
			// 011海关已接收

			// 016报关单已审结
			// 017放行交单

			// 022已结关
			// 023进出口审结/查验/放行通知

			// 025海关已放行
			if ((TCSCommonCode.CHANNEL_011.equals(resultCode) && TCSCommonCode.TcsEntryType_noPaper
					.equals(customsDeclaration.getTcsEntryType()))
					|| TCSCommonCode.CHANNEL_016.equals(resultCode)
					|| TCSCommonCode.CHANNEL_017.equals(resultCode)
					|| TCSCommonCode.CHANNEL_022.equals(resultCode)
					|| TCSCommonCode.CHANNEL_023.equals(resultCode)
					|| TCSCommonCode.CHANNEL_025.equals(resultCode)) {
				// 以上这几种情况要设置 报关单生效
				customsDeclaration.setEffective(true);
			}
			
			// 回写回执时间
			customsDeclaration.setTcsResultTime(messageTime);
			// 回写回执结果
			String msg = ("[" + resultCode + "]:" + resultValue);
			if (customsDeclaration.getTcsResultMessage() == null) {
				customsDeclaration.setTcsResultMessage(msg);
			} else {
				if (!customsDeclaration.getTcsResultMessage().contains(msg)) {
					customsDeclaration.setTcsResultMessage(customsDeclaration
							.getTcsResultMessage()
							+ "\n" + msg);
					if (customsDeclaration.getTcsResultMessage().length() > 200) {
						customsDeclaration.setTcsResultMessage(customsDeclaration
								.getTcsResultMessage().substring(200));
					}
				}
				System.out.println(customsDeclaration.getTcsResultMessage().length());
			}
			
			
			encDao.saveCustomsDeclaration(customsDeclaration);
			
			
			CustomReceiptMessage receiptMessage = new CustomReceiptMessage();
			receiptMessage.setNoticeDate(noticeDate);
			receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
			receiptMessage.setProjectType(projectType);
			receiptMessage.setCompany(CommonUtils.getCompany());
			receiptMessage.setSuccess("[" + resultCode + "]:" + resultValue);
			receiptMessage.setReturnDate(new Date());
			receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptMessage.setCreateDate(new Date());
			receiptMessage.setFileName(file.getName());
			receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptMessage.setCustoms(customsDeclaration
					.getCustomsDeclarationCode());
			receiptMessage.setSeqNo(customsDeclaration.getCustomsDeclarationCode());
			receiptMessage.setYlrh(customsDeclaration
					.getPreCustomsDeclarationCode());
			receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
			receiptMessage.setUniteCode(customsDeclaration.getUnificationCode());

			logger.info("保存处理回执记录！");
			messageQueryDao.saveCustomReceiptMessage(receiptMessage);
		} else {
			throw new RuntimeException(
			"回执报文中没有包含：MessageBody-->TcsFlow201-->TcsData-->ENTRY_RESULT");
		}

		
	}

	/**
	 * 处理 TcsFlow201 类型回执 ao2
	 * 
	 * @param root
	 * @param projectType
	 * @param file
	 * @param messageTime
	 */
	private static void proccessTcsFlow201A02(Element supDecResponseInformationElement, int projectType, File file,
			Date messageTime,String taskId, Namespace ns_supret, EncDao encDao, MessageQueryDao messageQueryDao) {
		/*
		 * 	<TcsData> 
		 * 		<SupDecResponseInformation xmlns="http://www.chinaport.gov.cn/lis/supret">
		 * 			<TaskId>TX181609770020120911888888</TaskId>
		 * 			<ClientSeqNo>Z11000000000034673</ClientSeqNo> 
		 * 			<ResponseCode>0</ResponseCode>
		 * 			<ResponseInfo>调用成功</ResponseInfo> 
		 * 		</SupDecResponseInformation>
		 * 	</TcsData>
		 */
		/**
		 * 这里判断是否是转关的第二个回执 如果TcsData底下包含SupDecResponseInformation元素，则表示是第二个回执。
		 * 处理完以后直接返回。
		 */

		// 响应代码
		String responseCode = "";
		Element responseCodeElement = supDecResponseInformationElement
				.getChild("ResponseCode", ns_supret);
		if (responseCodeElement != null) {
			responseCode = responseCodeElement.getTextTrim();
		}
		
		// 响应文字
		String note = "";
		Element elemNote = supDecResponseInformationElement.getChild(
				"ResponseInfo", ns_supret);
		if (elemNote != null) {
			note = elemNote.getTextTrim();
		}

		BaseCustomsDeclaration customsDeclaration = encDao
				.findCustomsDeclarationByTcsTaskId(projectType, taskId);
		if (customsDeclaration == null) {
			throw new RuntimeException("没有找到TaskId为：" + taskId + "的报关单");
		}
		if (note == null) {
			note = "";
		} else {
			note = "[" + responseCode + "]" + note;
		}

		if (customsDeclaration.getTcsResultMessage() == null) {
			customsDeclaration.setTcsResultMessage("");
		}
		customsDeclaration.setTcsResultMessage(customsDeclaration
				.getTcsResultMessage()
				+ "\n" + note.replace(" ", ""));

		encDao.saveCustomsDeclaration(customsDeclaration);

		CustomReceiptMessage receiptMessage = new CustomReceiptMessage();
		// receiptMessage.setNoticeDate();
		receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
		receiptMessage.setProjectType(projectType);
		receiptMessage.setCompany(CommonUtils.getCompany());
		receiptMessage.setSuccess(note == null ? "" : note);
		receiptMessage.setReturnDate(new Date());
		receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCreateDate(new Date());
		receiptMessage.setFileName(file.getName());
		receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCustoms(customsDeclaration
				.getCustomsDeclarationCode());
		receiptMessage.setSeqNo(customsDeclaration
				.getCustomsDeclarationCode());
		receiptMessage.setYlrh(customsDeclaration
				.getPreCustomsDeclarationCode());
		receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
		receiptMessage
				.setUniteCode(customsDeclaration.getUnificationCode());
		messageQueryDao.saveCustomReceiptMessage(receiptMessage);

		return;
	}
	
	/**
	 * 成通普通报文回执报文结构-解析
	 * 
	 * @param root
	 * @param projectType
	 * @param file
	 * @param messageTime
	 */
	static void proccessDecResponseInformation(Element root, int projectType,
			File file, Date messageTime, EncDao encDao,
			MessageQueryDao messageQueryDao) {
		/*
		 * 	<DecResponseInformation> 
		 * 		<TaskId>任务编号</TaskId> 
		 * 		<ResponseCode>处理结果代码</ResponseCode>
		 * 		<ResponseInfo>处理结果文字信息</ResponseInfo> 
		 * 	</DecResponseInformation>
		 */
		// 回执响应信息
		Element decResponseInformationElement = root
				.getChild("DecResponseInformation");
		if (decResponseInformationElement == null) {
			throw new RuntimeException("回执报文中没有包含：DecResponseInformation");
		}

		// 回执响应信息 - 任务编号
		Element taskIdElement = decResponseInformationElement
				.getChild("taskId");
		if (taskIdElement == null) {
			throw new RuntimeException(
					"回执报文中没有包含：DecResponseInformation->taskId");
		}
		// 回执响应信息 - 处理结果代码
		Element responseCodeElement = decResponseInformationElement
				.getChild("ResponseCode");
		if (responseCodeElement == null) {
			throw new RuntimeException(
					"回执报文中没有包含：DecResponseInformation->ResponseCode");
		}

		// 回执响应信息 - 处理结果文字信息
		Element responseInfoElement = decResponseInformationElement
				.getChild("ResponseInfo");
		if (responseInfoElement == null) {
			throw new RuntimeException(
					"回执报文中没有包含：DecResponseInformation->ResponseInfo");
		}

		 
//		 	<DecReturnInformation> 
//			 	<TmpEportNo>报关单暂存号</TmpEportNo>
//			 	<EportNo>数据中心统一编号</EportNo> 
//			 	<TransitEportNo>转关单统一编号</TransitEportNo>
//			 	<EntryId>海关编号</EntryId> 
//			 	<NoticeDate>通知时间</NoticeDate>
//			 	<Channel>处理结果</Channel>
//			 	<Note>审核备注</Note> 
//			 	<EportLocationCode>申报口岸</EportLocationCode>
//			 	<CorporationName>申报单位</CorporationName>
//			 	<EntryDeclarantNo>报关员代码</EntryDeclarantNo>
//			 	<CorporationCustomsCode>经营单位代码</CorporationCustomsCode>
//			 	<CYNo>货场代码</CYNo>
//			 	<WarehouseNo>保税仓库代码</WarehouseNo>
//			 	<ImportExportDate>进出口日期</ImportExportDate>
//			 	<Packages>件数</Packages> <BillOfLadingNo>提单号</BillOfLadingNo>
//			 	<MeansOfTransportMode>运输方式</MeansOfTransportMode>
//			 	<MeansOfTransportId>航班号</MeansOfTransportId>
//			 	<NetWeight>净重</NetWeight>
//			 	<GrossWeight>毛重</GrossWeight> <DeclareDate>申报日期</DeclareDate>
//		 	</DecReturnInformation>
		 
		// 回执返回信息
		Element decReturnInformationElement = root
				.getChild("DecReturnInformation");
		if (decReturnInformationElement == null) {
			throw new RuntimeException("回执报文中没有包含：DecReturnInformation");
		}

		// 回执响应信息 - 统一编号
		Element eportNoElement = decReturnInformationElement
				.getChild("EportNo");
		if (eportNoElement == null) {
			throw new RuntimeException(
					"回执报文中没有包含：DecReturnInformation->EportNo");
		}
		// 回执响应信息 - 处理结果代码
		Element entryIdCodeElement = decReturnInformationElement
				.getChild("EntryId");
		if (entryIdCodeElement == null) {
			throw new RuntimeException(
					"回执报文中没有包含：DecReturnInformation->EntryId");
		}

		String taskId = taskIdElement.getTextTrim();// 任务编号
		String responseCode = responseCodeElement.getTextTrim();// 处理结果代码
		String responseInfo = responseInfoElement.getTextTrim();// 处理结果文字信息
		String eportNo = eportNoElement.getTextTrim();// 统一编号
		String entryId = entryIdCodeElement.getTextTrim();// 报关单编号

		BaseCustomsDeclaration customsDeclaration = encDao
				.findCustomsDeclarationByTcsTaskId(projectType, taskId);
		if (customsDeclaration == null) {
			throw new RuntimeException("没有找到TaskId为：" + taskId + "的报关单");
		}
		// 没有生效才回写，生效后不回写
		if (!new Boolean(true).equals(customsDeclaration.getEffective())) {
			// 回写统一编号
			if (eportNo != null && !"".equals(eportNo)) {
				customsDeclaration.setUnificationCode(eportNo);
			}
			// 回写报关单编号
			if (!"".equals(entryId)) {
				customsDeclaration.setCustomsDeclarationCode(entryId);
			}
		}

		// 回写回执时间
		customsDeclaration.setTcsResultTime(messageTime);
		// 回写回执结果
		customsDeclaration.setTcsResultMessage("[" + responseCode + "]:"
				+ responseInfo);
		encDao.saveCustomsDeclaration(customsDeclaration);

		CustomReceiptMessage receiptMessage = new CustomReceiptMessage();

		receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
		receiptMessage.setProjectType(projectType);
		receiptMessage.setCompany(CommonUtils.getCompany());
		receiptMessage.setSuccess("[" + responseCode + "]:" + responseInfo);
		receiptMessage.setReturnDate(new Date());
		receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCreateDate(new Date());
		receiptMessage.setFileName(file.getName());
		receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCustoms(customsDeclaration
				.getCustomsDeclarationCode());
		receiptMessage.setSeqNo(customsDeclaration.getCustomsDeclarationCode());
		receiptMessage.setYlrh(customsDeclaration
				.getPreCustomsDeclarationCode());
		receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
		receiptMessage.setUniteCode(customsDeclaration.getUnificationCode());

		logger.info("保存处理回执记录！");
		messageQueryDao.saveCustomReceiptMessage(receiptMessage);
	}

	static void proccessDEC_DATA(Element root, int projectType, File file,
			Date messageTime, EncDao encDao, MessageQueryDao messageQueryDao) {
//		 <DEC_RESULT> 
//			 <SEQ_NO>数据中心统一编号</SEQ_NO> 
//			 <ENTRY_ID>报关单号</ENTRY_ID>
//			 <NOTICE_DATE>通知时间</NOTICE_DATE> 
//			 <CHANNEL>处理结果</CHANNEL> 
//			 <NOTE>备注</NOTE>
//			 <DECL_PORT>申报口岸</DECL_PORT> 
//			 <AGENT_NAME>申报单位名称</AGENT_NAME>
//			 <DECLARE_NO></DECLARE_NO> 
//			 <TRADE_CO>经营单位编号</TRADE_CO>
//			 <CUSTOMS_FIELD></CUSTOMS_FIELD> 
//			 <BONDED_NO></BONDED_NO>
//			 <I_E_DATE>进出口日期</I_E_DATE> 
//			 <PACK_NO></PACK_NO> 
//			 <BILL_NO></BILL_NO>
//			 <TRAF_MODE></TRAF_MODE> 
//			 <VOYAGE_NO></VOYAGE_NO> 
//			 <NET_WT></NET_WT>
//			 <GROSS_WT></GROSS_WT> 
//			 <D_DATE>申报日期</D_DATE> 
//		 </DEC_RESULT>
		 
		// 回执响应信息
		Element decResultInformationElement = root.getChild("DEC_RESULT");
		if (decResultInformationElement == null) {
			throw new RuntimeException("回执报文中没有包含：DEC_RESULT");
		}

		// 回执响应信息 - 任务编号
		Element entryIdElement = decResultInformationElement
				.getChild("ENTRY_ID");
		if (entryIdElement == null) {
			throw new RuntimeException("回执报文中没有包含：DEC_RESULT->ENTRY_ID");
		}
		// 回执响应信息 - 处理结果代码
		Element channelCodeElement = decResultInformationElement
				.getChild("CHANNEL");
		if (channelCodeElement == null) {
			throw new RuntimeException("回执报文中没有包含：DEC_RESULT->CHANNEL");
		}

		// 回执响应信息 - 处理结果文字信息
		Element noteElement = decResultInformationElement.getChild("NOTE");
		if (noteElement == null) {
			throw new RuntimeException("回执报文中没有包含：DEC_RESULT->NOTE");
		}

		String entryId = entryIdElement.getTextTrim();// 报关单编号
		String channel = channelCodeElement.getTextTrim();// 处理结果代码
		String note = noteElement.getTextTrim();// 处理结果文字信息

		List<BaseCustomsDeclaration> list = encDao.findCustomsDeclarationByCode(entryId);
		BaseCustomsDeclaration customsDeclaration = null;
		if (list != null && list.size() > 0) {
			customsDeclaration = (BaseCustomsDeclaration) list.get(0);
		}

		if (customsDeclaration == null) {
			throw new RuntimeException("没有找到TaskId为：" + entryId + "的报关单");
		}

		// 回写回执结果
		customsDeclaration.setTcsResultMessage("[" + channel + "]:" + note);
		encDao.saveCustomsDeclaration(customsDeclaration);

		CustomReceiptMessage receiptMessage = new CustomReceiptMessage();
		receiptMessage.setTcsTaskId(customsDeclaration.getTcsTaskId());
		receiptMessage.setProjectType(projectType);
		receiptMessage.setCompany(CommonUtils.getCompany());
		receiptMessage.setSuccess("[" + channel + "]:" + note);
		receiptMessage.setReturnDate(new Date());
		receiptMessage.setReturnUser(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCreateDate(new Date());
		receiptMessage.setFileName(file.getName());
		receiptMessage.setCreatePeople(CommonUtils.getRequest().getUser()
				.getUserName());
		receiptMessage.setCustoms(customsDeclaration
				.getCustomsDeclarationCode());
		receiptMessage.setSeqNo(customsDeclaration.getCustomsDeclarationCode());
		receiptMessage.setYlrh(customsDeclaration
				.getPreCustomsDeclarationCode());
		receiptMessage.setEmsNo(customsDeclaration.getRelativeManualNo());
		receiptMessage.setUniteCode(customsDeclaration.getUnificationCode());

		logger.info("保存处理回执记录！");
		messageQueryDao.saveCustomReceiptMessage(receiptMessage);
	}

}
