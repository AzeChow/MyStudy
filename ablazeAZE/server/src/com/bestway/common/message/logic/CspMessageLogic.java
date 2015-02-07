/*
 * Created on 2004-8-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.message.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bswmail.qp.action.BswMailQpClient;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.CspProcessResult;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.message.CspMessageFunctions;
import com.bestway.common.message.dao.CspMessageDao;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspReceiptResultDetail;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspMessageTreeInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.entity.TempCspSignFieldInfo;
import com.bestway.cspcard.CSPCardUtils;
import com.bestway.cspcard.entity.ICCardInfo;
import com.bestway.cspcard.service.client.CspCardServer;
import com.bestway.cspcard.service.client.CspCardServerService;

/**
 * 主要是负责产生报文，处理回执，以及反向解析报文内容 第一次检查日期 2008/08/28
 * 
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public abstract class CspMessageLogic {

	private CspMessageDao cspMessageDao = null;

	private BaseCodeDao baseCodeDao = null;

	/**
	 * 报文子目录，暂时未用
	 */
	private String subDir = "";

	private CspParameterSet cspParameterSet = null;

	public CspMessageDao getCspMessageDao() {
		return cspMessageDao;
	}

	public void setCspMessageDao(CspMessageDao messageQueryDao) {
		this.cspMessageDao = messageQueryDao;
	}

	public BaseCodeDao getBaseCodeDao() {
		return baseCodeDao;
	}

	public void setBaseCodeDao(BaseCodeDao baseCodeDao) {
		this.baseCodeDao = baseCodeDao;
	}

	/**
	 * 查询出实体{entityName}最大的内部编号
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return
	 */
	public String getContractNewCopEntNo(String entityName,
			String propertyName, String flag, String sysType) {

		Company company = (Company) CommonUtils.getCompany();

		String tradeCode = company.getCode();

		if (tradeCode == null || "".equals(tradeCode.trim())) {

			throw new RuntimeException("请在公司资料设定里面先设定好加工单位和经营单位");
		}

		// flag : "C" / sysType:"2"
		String prefix = tradeCode + flag + sysType;// "ENT" +

		// 最大 的 内部编码
		String maxCopEntNo = this.cspMessageDao.findContractMaxCopEntNo(
				entityName, propertyName, prefix);

		String newCopEntNO = "";

		// 如果没有最大的内部编码 那么就直接返回 000001
		if ("".equals(maxCopEntNo.trim())) {

			newCopEntNO = prefix + CommonUtils.convertIntToStr(1, 6);

		} else {

			int maxNo = 0;
			// hwy 2014-9-22 因大部分企业在旧系统中，prefix 是加了ENT导致获取内部编号太长，
			// 获取尾数可时能会包括字母的情况，导致转化为整型时报错，考虑到这种情况，现退后2位，获取后6位数为新手册最大内部编号；
			String serialNo = maxCopEntNo.substring(prefix.length() + 2);

			try {

				if ("".equals(serialNo)) {

					serialNo = serialNo + 1;

				}

				maxNo = Integer.parseInt(serialNo);

			} catch (Exception ex) {
				throw new RuntimeException("在获取" + entityName + "最大内部编号出错，"
						+ serialNo + "不是一个有效的整数");
			}

			newCopEntNO = prefix
					+ CommonUtils.convertIntToStr(maxNo + 1,
							serialNo.length() + 2);

		}
		return newCopEntNO;
	}

	/**
	 * 查询出实体{entityName}最大的内部编号
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return
	 */
	public String getNewCopEntNo(String entityName, String propertyName,
			String flag, String sysType) {
		Company company = (Company) CommonUtils.getCompany();
		String tradeCode = company.getCode();
		if (tradeCode == null || "".equals(tradeCode.trim())) {
			throw new RuntimeException("请在公司资料设定里面先设定好加工单位和经营单位");
		}
		String prefix = tradeCode + flag + sysType;// "ENT" +
		System.out.println("!!!!" + tradeCode + "!!!" + flag + "!!!" + prefix);
		String maxCopEntNo = this.cspMessageDao.findMaxCopEntNo(entityName,
				propertyName, prefix);
		String newCopEntNO = "";
		if ("".equals(maxCopEntNo.trim())) {
			newCopEntNO = prefix + CommonUtils.convertIntToStr(1, 6);
		} else {
			int maxNo = 0;
			String serialNo = maxCopEntNo.substring(prefix.length());
			try {
				if ("".equals(serialNo)) {
					serialNo = serialNo + 1;
				}
				maxNo = Integer.parseInt(serialNo);
			} catch (Exception ex) {
				throw new RuntimeException("在获取" + entityName + "最大内部编号出错，"
						+ serialNo + "不是一个有效的整数");
			}
			newCopEntNO = prefix + CommonUtils.convertIntToStr(maxNo + 1, 6);
		}
		return newCopEntNO;
	}

	/**
	 * 查找出该合同
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return
	 */
	public String findOldCopEntNo(String entityName, String propertyName,
			String flag, String sysType) {
		Company company = (Company) CommonUtils.getCompany();
		String tradeCode = company.getCode();
		if (tradeCode == null || "".equals(tradeCode.trim())) {
			throw new RuntimeException("请在公司资料设定里面先设定好加工单位和经营单位");
		}
		String prefix = tradeCode + flag + sysType;// "ENT" +
		String maxCopEntNo = this.cspMessageDao.findMaxCopEntNo(entityName,
				propertyName, prefix);
		String newCopEntNO = "";
		if ("".equals(maxCopEntNo.trim())) {
			newCopEntNO = prefix + CommonUtils.convertIntToStr(1, 6);
		} else {
			int maxNo = 0;
			String serialNo = maxCopEntNo.substring(prefix.length());
			try {
				maxNo = Integer.parseInt(serialNo);
			} catch (Exception ex) {
				throw new RuntimeException("在获取" + entityName + "最大内部编号出错，"
						+ serialNo + "不是一个有效的整数");
			}
			newCopEntNO = prefix + CommonUtils.convertIntToStr(maxNo + 1, 6);
		}
		return newCopEntNO;
	}

	/**
	 * 查询出实体{entityName}最大的内部编号
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return 石小凯
	 */
	public void checkNewCopEntNo(String entityName, String propertyName,
			String flag, String sysType, String copTrNo) {
		Company company = (Company) CommonUtils.getCompany();
		String tradeCode = company.getCode();
		if (tradeCode == null || "".equals(tradeCode.trim())) {
			throw new RuntimeException("请在公司资料设定里面先设定好加工单位和经营单位");
		}
		String prefix = tradeCode + flag + sysType;// "ENT" +

		if (copTrNo.length() > prefix.length()) {

			String copTrNoPrefix = copTrNo.substring(prefix.length());
			String prefixs = copTrNo.substring(0, 12);
			String s = "";

			int No = 0;

			if (prefixs.equals(prefix)) {
				if (copTrNo.length() < 18 || copTrNo.length() > 18) {
					throw new RuntimeException("企业内部编号出错，请输入17个字符");
				}
				s = copTrNo.substring(12, 17);
				try {

					No = Integer.parseInt(s);
					System.out.println("No=" + No);
				} catch (Exception ex) {
					System.out.println("catch=" + ex);
					throw new RuntimeException("在获取" + entityName + "最大内部编号出错，"
							+ s + "不是一个有效的整数");
				}
			}
		}

	}

	public void checkCopEntNoOnly(String copTrNo) {
		System.out.println("进入相同编号判断 ");
		List AllCopEntNo = this.cspMessageDao.findAllCopEntNo(
				"DzscEmsPorWjHead", "copTrNo", copTrNo);
		if (AllCopEntNo.size() > 0) {
			throw new RuntimeException("你输入的企业内部编号重复，请重新输入");
		}
	}

	/**
	 * 电子手册-生成报文
	 * 
	 * @param head
	 *            要生成报文的表头
	 * @param declareType
	 *            申报类型
	 * @return 生成的报文的全称（包括路径）
	 */
	public synchronized DeclareFileInfo exportMessage(String formatFileName,
			Map<String, List> hmData, ProgressInfo info) {
		this.checkMessageDirSet();
		// this.startSendMail();
		// this.startReceiveMail();
		if (info != null) {
			int totalSize = 0;
			Iterator iterator = hmData.values().iterator();
			while (iterator.hasNext()) {
				List list = (List) iterator.next();
				totalSize += list.size();
			}
			info.setBeginTime(System.currentTimeMillis());
			info.setTotalNum(totalSize);
			info.setMethodName("正在生成文件,请稍候......");
			System.out.println("totalSize:" + totalSize);
		}
		System.out.println("begin create file:" + System.currentTimeMillis());
		int layer = 1;
		StringBuffer fileContent = new StringBuffer();
		StringBuffer signContent = new StringBuffer();
		SAXBuilder sax = new SAXBuilder();

		InputStream formatStream = getFormatFile(formatFileName); // 得到相应的报文格式
		// String returnFileName = "";
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		DeclareFileInfo fileInfo = null;
		Document doc;
		try {
			doc = sax.build(formatStream);
			Element root = doc.getRootElement();
			Element message = root;
			String msgType = message.getAttributeValue("type");
			fileContent.append("<?xml version=\"1.0\" encoding=\"GBK\"?>"
					+ getNewlineChar());
			// 获取section-flag
			Element sectionFlag = message.getChild("section-flag");
			fileContent.append("<" + sectionFlag.getTextTrim()
					+ " version=\"1.2\" sendId=\"\" receiveId=\"\">"
					+ getNewlineChar());
			for (int i = 0; i < message.getChildren().size(); i++) { // <message
				Element section = ((Element) message.getChildren().get(i));
				makeFileContent(section, fileContent, signContent, hmData,
						layer, "", info);
			}
			fileContent.append("</" + sectionFlag.getTextTrim() + ">");// +getNewlineChar()
			String fileName = genFileName(msgType);
			String fileContentTemp = fileContent.toString();
			String signContentTemp = signContent.toString();
			// System.out.println("fileContentTemp:"+fileContentTemp);
			// System.out.println("signContentTemp:"+signContentTemp);
			// fileContentTemp = new String(fileContentTemp.getBytes(),
			// "GB2312");// "GB2312"
			// signContentTemp = new String(signContentTemp.getBytes(),
			// "GB2312");
			// System.out.println("signContentTemp------------"
			// + signContentTemp);

			// 余鹏 2012年11月1日屏蔽，因为黄埔海关叶坤和北京数据中心沟通结果是：北京不进行报文的加签验证。
			// 2012年月5日，黄埔海关会议，深加工结转系统的报文需要进行加签
			String signInfo = getConvertSignInfo(fileContentTemp,
					signContentTemp);
			if (signInfo != null && !"".equals(signInfo.trim())) {
				int indexBegin = fileContentTemp.indexOf("<SIGN>");
				int indexEnd = fileContentTemp.indexOf("</SIGN>");
				if (indexBegin >= 0 && indexEnd >= 0) {
					String oldSign = fileContentTemp.substring(indexBegin,
							indexEnd + "</SIGN>".length());
					fileContentTemp = fileContentTemp.replace(oldSign, "<SIGN>"
							+ signInfo + "</SIGN>");
				}
			}

			fileInfo = createFile(fileName, fileContentTemp);
			// returnFileName = fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				formatStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("End create file:" + System.currentTimeMillis());
		return fileInfo;
	}

	/**
	 * 取得一个字符串的签名信息
	 * 
	 * @param signContent
	 * @return
	 */
	protected abstract String getConvertSignInfo(String fileContent,
			String signContent);

	// CspParameterSet dirSet = this.getCspParameterSet();
	// if (dirSet.getReadFromCard() == null || !dirSet.getReadFromCard()) {
	// return "";
	// } else {
	// // System.out.println("----------signContent:"+signContent);
	// ICCardInfo cardInfo = new ICCardInfo();
	// // if (dirSet.getRemoteSignData() != null
	// // && dirSet.getRemoteSignData()) {
	// // if (dirSet.getRemoteDxpMail() != null
	// // && dirSet.getRemoteDxpMail()) {
	// // BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// // String[] strs = serviceClient.signMsgData(signContent);
	// // if (strs != null) {
	// // if (strs[1] != null && !"".equals(strs[1].trim())) {
	// // throw new RuntimeException(strs[1]);
	// // }
	// // cardInfo.setSignData(strs[0]);
	// // }
	// // } else {
	// // CspCardServer service = getCspCardServer(dirSet);
	// // cardInfo = service.signMsgData(dirSet.getSeqNo(), dirSet
	// // .getPwd(), signContent);
	// // }
	// // } else {
	// CSPCardUtils.signMsgData(dirSet.getSeqNo(), dirSet.getPwd(),
	// signContent, cardInfo);
	// // }
	// // System.out.println("----------getSignData:"+cardInfo.getSignData());
	// return cardInfo.getSignData();
	// }

	/**
	 * 实例化一个远程加签服务器实例
	 * 
	 * @param dirSet
	 * @return
	 */
	private CspCardServer getCspCardServer(CspParameterSet dirSet) {
		if (dirSet.getRemoteHostAddress() == null
				|| "".equals(dirSet.getRemoteHostAddress().trim())) {
			throw new RuntimeException("远程加签服务器地址");
		}
		CspCardServerService serverService = CspCardServerService
				.getCspCardServerService(dirSet.getRemoteHostAddress().trim());
		CspCardServer service = serverService.getCspCardServerPort();
		return service;
	}

	/**
	 * 生成报文的子方法
	 * 
	 * @param section
	 * @param fileContent
	 * @param signContent
	 * @param hmData
	 * @param layer
	 * @param parentdesc
	 * @param info
	 */
	private void makeFileContent(Element section, StringBuffer fileContent,
			StringBuffer signContent, Map<String, List> hmData, int layer,
			String parentdesc, ProgressInfo info) {
		if (section.getName().equals("section")) {
			List datas = null;
			Element dataId = section.getChild("data-id");
			if (dataId != null) {
				if (!dataId.getText().trim().equals("")) {
					datas = hmData.get(dataId.getText().trim());
					if (datas == null || datas.size() <= 0) {
						return;
					}
				} else {
					return;
				}
			}
			Element sectionFlag = section.getChild("section-flag");
			fileContent.append(getPreSpaceString(layer) + "<"
					+ sectionFlag.getTextTrim() + ">" + getNewlineChar());
			Element description = section.getChild("description");
			String subDesc = parentdesc;
			if (description != null) {
				subDesc = (parentdesc + "-" + description.getTextTrim());
			}

			List<Element> fields = new ArrayList<Element>();
			List childs = section.getChildren();
			// 得到字段列表
			for (int j = 0; j < childs.size(); j++) { // <section
				Element childElement = ((Element) childs.get(j));
				if (childElement.getName().equals("field")) {
					fields.add(childElement);
				} else {
					if (childElement.getName().equals("section")) {
						int newLayer = layer + 1;
						makeFileContent(childElement, fileContent, signContent,
								hmData, newLayer, subDesc, info);
					} else if (childElement.getName().equals("sub-section")) {
						String subFileName = "";
						Element subFile = childElement.getChild("file-name");
						if (subFile != null) {
							subFileName = subFile.getTextTrim();
						}
						if (subFileName == null
								|| "".equals(subFileName.trim())) {
							continue;
						}
						analyseSubSection(fileContent, signContent, hmData,
								layer, subFileName, subDesc, info);
					} else if (childElement.getName().equals("sub-fields")) {
						String subFileName = "";
						Element subFile = childElement.getChild("file-name");
						if (subFile != null) {
							subFileName = subFile.getTextTrim();
						}
						if (subFileName == null
								|| "".equals(subFileName.trim())) {
							continue;
						}
						getSubFields(fields, subFileName);
					}
				}
			}
			if (datas != null && fields.size() > 0) {
				for (int i = 0; i < datas.size(); i++) {
					if (i != 0) {
						fileContent.append(getPreSpaceString(layer) + "<"
								+ sectionFlag.getTextTrim() + ">"
								+ getNewlineChar());
					}
					Object obj = datas.get(i);
					List<TempCspSignFieldInfo> lsSignInfo = new ArrayList<TempCspSignFieldInfo>();
					for (int k = 0; k < fields.size(); k++) {
						String format = ((Element) fields.get(k)) // 分解<field
								.getChildText("format");
						String fieldName = ((Element) fields.get(k))
								.getChildText("field-name");
						String fieldFlag = ((Element) fields.get(k))
								.getChildText("field-flag");
						String fielddesc = ((Element) fields.get(k))
								.getChildText("description");
						String nullpara = ((Element) fields.get(k))
								.getAttributeValue("nullable");
						String signNo = ((Element) fields.get(k))
								.getAttributeValue("signNo");
						boolean nullable = ((nullpara == null || ""
								.equals(nullpara.trim())) ? true : Boolean
								.valueOf(nullpara));
						Object oValue = null;
						if (fieldName != null && !"".equals(fieldName.trim())) {
							try {
								// oValue = PropertyUtils.getProperty(obj,
								// field);
								// //
								// 由字段名得到值
								oValue = PropertyUtils.getNestedProperty(obj,
										fieldName.trim()); // 由字段名得到值
							} catch (Exception ex) {
								// System.out.println("[生成报文信息] 未能获取字段:"
								// + fieldName + ",描述：" + fielddesc
								// + ", 使用缺省值null。");
								// System.out.println(ex.getMessage());
								oValue = null;
							}
						}
						if (oValue == null) {
							if (!nullable) {
								throw new RuntimeException(subDesc + "-"
										+ fielddesc + "的值不允许为空，但是在数据中存在空值");
							}
						} else {
							if (oValue instanceof String) {
								if ("".equals(oValue) && !nullable) {
									throw new RuntimeException(subDesc + "-"
											+ fielddesc + "的值不允许为空，但是在数据中存在空值");
								}
							}
						}
						Object oValue1 = specialProcessValue(oValue, fieldName,
								format, fieldFlag);// 特殊处理日期
						String sValue = null;
						try {
							if (oValue instanceof Date && format.equals("Z(8)")) {
								sValue = String.valueOf(oValue1);
							} else {
								sValue = CspMessageFunctions.formatVariant(
										oValue1, format); // 进行格式处理
							}
							// System.out.println("小:" + sValue);
							// 繁体转简体 2013年10月15日，由于客户原因 屏蔽掉。
							// sValue=CommonServerBig5GBConverter.getInstance().big5ConvertToGB(sValue);
						} catch (Exception ex) {
							// System.out.println("格式化变量时出错，字段：" + fieldName
							// + ",值：" + oValue + "格式：" + format);
							// throw ex;
						}
						fileContent.append(getPreSpaceString(layer + 1)
								+ "<"
								+ fieldFlag.trim()
								+ ">"
								+ (sValue == null ? ""
										: replaceSpecialChar(sValue)).trim()
								+ "</" + fieldFlag.trim() + ">"
								+ getNewlineChar());
						if (signNo != null && !"".equals(signNo.trim())) {
							TempCspSignFieldInfo tempSignFieldInfo = new TempCspSignFieldInfo();
							tempSignFieldInfo.setNo(Integer.parseInt(signNo));
							tempSignFieldInfo.setValue((sValue == null ? ""
									: sValue.trim()));
							lsSignInfo.add(tempSignFieldInfo);
						}
						// int length = CspMessageFunctions
						// .getFormatLength(format);
						// System.out.println("FieldName:" + fieldName + "; "
						// + "Format:" + format + "; " + "Length:"
						// + Integer.valueOf(length) + "; " + "OldValue:"
						// + oValue + "; " + "NewValue:" + sValue);

					}
					Collections.sort(lsSignInfo);
					for (int m = 0; m < lsSignInfo.size(); m++) {
						TempCspSignFieldInfo tempSignFieldInfo = lsSignInfo
								.get(m);
						signContent.append(tempSignFieldInfo.getValue().trim());
					}
					// 繁转简
					// if (CommonUtils.getIsBigToGBK()!=null &&
					// CommonUtils.getIsBigToGBK().equals("1")){
					if (i != datas.size() - 1) {
						fileContent.append(getPreSpaceString(layer) + "</"
								+ sectionFlag.getTextTrim() + ">"
								+ getNewlineChar());
					}
					if (info != null) {
						info.setCurrentNum(info.getCurrentNum() + 1);
						// System.out.println("currentNum:"+info.getCurrentNum());
					}
				}
			}
			fileContent.append(getPreSpaceString(layer) + "</"
					+ sectionFlag.getTextTrim() + ">" + getNewlineChar());
		} else if (section.getName().equals("sub-section")) {
			String subFileName = "";
			Element subFile = section.getChild("file-name");
			if (subFile != null) {
				subFileName = subFile.getTextTrim();
			}
			if (subFileName == null || "".equals(subFileName.trim())) {
				return;
			}
			analyseSubSection(fileContent, signContent, hmData, layer,
					subFileName, parentdesc, info);
		}
	}

	private String getNewlineChar() {
		// return "";
		return "\n";
	}

	/**
	 * 替换特殊字符以符合XML标准格式
	 * 
	 * @param str
	 * @return
	 */
	private String replaceSpecialChar(String str) {
		// 只替换 & 和 < 两种特殊符号即可
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;");
		// .replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'",
		// "&apos;")
		// return str.replaceAll("<", "<![CDATA[<]]>").replaceAll("&",
		// "<![CDATA[&]]>");
	}

	/**
	 * 解析报文内容。然后将内容放在HashMap中返回到客户端进行显示
	 * 
	 * @param fileContent
	 * @param signContent
	 * @param hmData
	 * @param layer
	 * @param subFileName
	 * @param pdesc
	 * @param info
	 */
	private void analyseSubSection(StringBuffer fileContent,
			StringBuffer signContent, Map<String, List> hmData, int layer,
			String subFileName, String pdesc, ProgressInfo info) {
		// System.out.println("-----------" + subFileName);
		InputStream[] formatStreams = getFormatFile(new String[] { subFileName });
		for (int index = 0; index < formatStreams.length; index++) {
			InputStream formatStream = formatStreams[index];
			if (formatStream == null) {
				throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
			}
			Document doc;
			SAXBuilder sax = new SAXBuilder();
			try {
				doc = sax.build(formatStream);
				Element message = doc.getRootElement();
				for (int i = 0; i < message.getChildren().size(); i++) { // <message
					Element subsection = ((Element) message.getChildren()
							.get(i));
					makeFileContent(subsection, fileContent, signContent,
							hmData, layer, pdesc, info);
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				try {
					formatStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析报文内容。然后将内容放在HashMap中返回到客户端进行显示
	 * 
	 * @param fields
	 * @param fileName
	 */
	private void getSubFields(List fields, String fileName) {
		// System.out.println("-----------" + fileName);
		InputStream[] formatStreams = getFormatFile(new String[] { fileName });
		for (int index = 0; index < formatStreams.length; index++) {
			InputStream formatStream = formatStreams[index];
			if (formatStream == null) {
				throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
			}
			Document doc;
			SAXBuilder sax = new SAXBuilder();
			try {
				doc = sax.build(formatStream);
				Element message = doc.getRootElement();
				for (int i = 0; i < message.getChildren().size(); i++) {
					Element subElement = ((Element) message.getChildren()
							.get(i));
					if (subElement.getName().equals("field")) {
						fields.add(subElement);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} finally {
				try {
					formatStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 特殊处理数据日期
	 * 
	 * @param oValue
	 * @param field
	 * @param format
	 * @param fieldFlag
	 * @return
	 */
	private Object specialProcessValue(Object oValue, String field,
			String format, String fieldFlag) {
		if (oValue instanceof Date) {
			if (format.equals("Z(8)")) {
				if (fieldFlag.indexOf("TIME") >= 0) {
					SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
					oValue = sdf.format(oValue) + "00";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					oValue = sdf.format(oValue);
				}
			} else if (format.equals("Z(14)")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				oValue = sdf.format(oValue);
			}
		}
		return oValue;
	}

	/**
	 * 生成报文文件号码
	 * 
	 * @param msgType
	 * @return
	 */
	protected String genFileName(String msgType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sFileName = msgType + "_" + sdf.format(new Date()) + ".ems";
		return sFileName;
	}

	/**
	 * 该方法用于对特殊类型的值做特殊处理，如值可能是我们的某个业务对象
	 * 
	 * @param value
	 * @return
	 */
	private String convertValueToString(Object value) {
		if (value == null)
			return "";
		if (value instanceof Unit) {
			return ((Unit) value).getCode();
		} else
			return value.toString();
	}

	/**
	 * 定义前缀
	 * 
	 * @param len
	 * @return
	 */
	private String getPreSpaceString(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			sb.append("	");
		}
		return sb.toString();
		// return "";
	}

	/**
	 * 读取卡中的数据，用来生成报文的签名信息
	 * 
	 * @param info
	 * @param manageObject
	 * @return
	 */
	public CspSignInfo getCspPtsSignInfo(ProgressInfo info, String manageObject) {
		Company company = (Company) CommonUtils.getCompany();
		// if (signInfo == null) {
		// signInfo = new CspPtsSignInfo();
		// }
		Class cls = this.cspMessageDao.getSignInfoClass();
		CspSignInfo signInfo = null;
		try {
			signInfo = (CspSignInfo) cls.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (signInfo == null) {
			throw new RuntimeException("初始化加签信息类失败！");
		}
		CspParameterSet dirSet = this.getCspParameterSet();
		// 余鹏 2012年11月1日屏蔽，因为黄埔海关叶坤和北京数据中心沟通结果是：北京不进行报文的加签验证。
		// 2013年月5日，黄埔海关会议，深加工结转系统的报文需要进行加签
		// if (dirSet.getReadFromCard() == null || !dirSet.getReadFromCard()) {
		signInfo.setCertSeqNo("5c2077");
		signInfo.setIdCard(dirSet.getIdCard());
		// } else {
		// if (info != null) {
		// info.setMethodName("正在读卡,请稍候......");
		// }
		// ICCardInfo cardInfo = new ICCardInfo();
		// String seqNo = dirSet.getSeqNo();
		// String pwd = dirSet.getPwd();
		// if (dirSet.getRemoteSignData() != null
		// && dirSet.getRemoteSignData()) {
		// if (dirSet.getRemoteDxpMail() != null
		// && dirSet.getRemoteDxpMail()) {
		// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
		// String[] strs = serviceClient.getICCardInfo();
		// if (strs != null) {
		// if (strs[1] != null && !"".equals(strs[1].trim())) {
		// throw new RuntimeException(strs[1]);
		// }
		// signInfo.setIdCard(strs[0]);
		// }
		// strs = serviceClient.getAllUnitInfo();
		// if (strs != null) {
		// if (strs[5] != null && !"".equals(strs[5].trim())) {
		// throw new RuntimeException(strs[5]);
		// }
		// signInfo.setCertSeqNo(strs[0]);
		// }
		// } else {
		// CspCardServer service = getCspCardServer(dirSet);
		// cardInfo = service.getICCardInfo(seqNo, pwd);
		// signInfo.setIdCard(cardInfo.getIcCode());
		// cardInfo = service.getUnitInfo(seqNo, pwd, 1);
		// signInfo.setCertSeqNo(cardInfo.getCertSeqNo());
		// }
		// } else {
		// CSPCardUtils.getICCardInfo(seqNo, pwd, cardInfo);
		// CSPCardUtils.getUnitInfo(seqNo, pwd, 1, cardInfo);
		// signInfo.setIdCard(cardInfo.getIcCode());
		// signInfo.setCertSeqNo(cardInfo.getCertSeqNo());
		// }
		// }
		if (manageObject == null || "".equals(manageObject.trim())) {
			signInfo.setTradeCode(company.getCode());
		} else {
			if (ManageObject.MANUFACTURE_COP.equals(manageObject)) {
				signInfo.setTradeCode(company.getCode());
			} else if (ManageObject.MANAGE_COP.equals(manageObject)) {
				signInfo.setTradeCode(company.getBuCode());
			} else {
				throw new RuntimeException("获取签名信息出错：未知的管理对象类型：" + manageObject);
			}
		}
		signInfo.setSign("temp");// 先临时产生，生成报文结束时，根据报文内容具体替换。
		// signInfo.setSign("zbsqg1/cYYjRFrt/8FcoUk2uIdAMTdqOYc5zrwe6H2grBmzuUYbHxa/C00I2imS5xDoh7/Jo81GXcHA7ujCrUnilS9M+JV8j/8Xa6Ypqn15b7E1YwvMQ8dghCrSZOzZPM765XHLvd/cZcPF56fT5fHaS8rnWSKSy9sBdmcBK6Iw=");
		signInfo.setDeclareType(DelcareType.APPLICATION);
		return signInfo;
	}

	/**
	 * 获取卡信息
	 * 
	 * @param seqNo
	 * @param pwd
	 * @return
	 */
	public ICCardInfo getICCardInfo(String seqNo, String pwd) {
		if (seqNo == null || "".equals(seqNo.trim())) {
			throw new RuntimeException("唯一号不能为空");
		}
		if (pwd == null || "".equals(pwd.trim())) {
			throw new RuntimeException("密码不能为空");
		}
		ICCardInfo cardInfo = new ICCardInfo();
		CspParameterSet dirSet = this.getCspParameterSet();
		// if (dirSet.getRemoteSignData() != null && dirSet.getRemoteSignData())
		// {
		// if (dirSet.getRemoteDxpMail() != null && dirSet.getRemoteDxpMail()) {
		// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
		// String[] strs = serviceClient.getICCardInfo();
		// if (strs != null) {
		// if (strs[1] != null && !"".equals(strs[1].trim())) {
		// throw new RuntimeException(strs[1]);
		// }
		// cardInfo.setIcCode(strs[0]);
		// }
		// strs = serviceClient.getAllUnitInfo();
		// if (strs != null) {
		// if (strs[5] != null && !"".equals(strs[5].trim())) {
		// throw new RuntimeException(strs[5]);
		// }
		// cardInfo.setCertSeqNo(strs[0]);
		// cardInfo.setApplier(strs[1]);
		// cardInfo.setTradeName(strs[2]);
		// cardInfo.setTradeType(strs[3]);
		// cardInfo.setTradeCode(strs[4]);
		// }
		// strs = serviceClient.signMsgData("中华人民共和国");
		// if (strs != null) {
		// if (strs[1] != null && !"".equals(strs[1].trim())) {
		// throw new RuntimeException(strs[1]);
		// }
		// cardInfo.setSignData(strs[0]);
		// }
		// } else {
		// CspCardServer service = getCspCardServer(dirSet);
		// cardInfo = service.getAllUnitInfo(seqNo, pwd);
		// }
		// } else {
		CSPCardUtils.getICCardInfo(seqNo, pwd, cardInfo);
		CSPCardUtils.getUnitInfo(seqNo, pwd, 1, cardInfo);
		CSPCardUtils.getUnitInfo(seqNo, pwd, 2, cardInfo);
		CSPCardUtils.getUnitInfo(seqNo, pwd, 3, cardInfo);
		CSPCardUtils.getUnitInfo(seqNo, pwd, 4, cardInfo);
		CSPCardUtils.getUnitInfo(seqNo, pwd, 5, cardInfo);
		CSPCardUtils.signMsgData(seqNo, pwd, "中华人民共和国", cardInfo);
		// }
		return cardInfo;
	}

	/**
	 * 获取远程邮箱程序的客户端实例
	 * 
	 * @param dirSet
	 * @return
	 */
	private BswMailQpClient getDxpEusServiceClient(CspParameterSet dirSet) {
		return BswMailQpClient.getInstance(dirSet);
	}

	/**
	 * 获取报文格式文件的路径
	 * 
	 * @return
	 */
	public abstract String getForamtFilePath();

	/**
	 * 根据表头的类，获取对应的格式文件 注意：回执处理也可使用该函数
	 * 
	 * @param headClass
	 * @param declareType
	 *            1.申报 2.变更
	 * @return
	 */
	public InputStream[] getFormatFile(String[] fileNames) {
		InputStream[] result = new InputStream[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			String fileName = fileNames[i];
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream(getForamtFilePath() + fileName);// "com/bestway/bcs/message/messageformat/"
			result[i] = is;
		}
		return result;
	}

	/**
	 * 根据表头的类，获取对应的格式文件 注意：回执处理也可使用该函数
	 * 
	 * @param headClass
	 * @param declareType
	 *            1.申报 2.变更
	 * @return
	 */
	public InputStream getFormatFile(String fileName) {
		InputStream is = this.getClass().getClassLoader()
				.getResourceAsStream(getForamtFilePath() + fileName);// "com/bestway/bcs/message/messageformat/"
		return is;
	}

	/**
	 * 检查报文发送接受目录
	 * 
	 */
	protected void checkMessageDirSet() {
		CspParameterSet dirSet = this.getCspParameterSet();
		String sendDir = (dirSet == null ? "" : dirSet.getSendDir());
		CspMessageFunctions.checkSendDirIsAvaliable(sendDir);
		String recvDir = (dirSet == null ? "" : dirSet.getRecvDir());
		CspMessageFunctions.checkRecvDirIsAvaliable(recvDir);
		String finallyDir = (dirSet == null ? "" : dirSet.getFinallyDir());
		CspMessageFunctions.checkFinallyDirIsAvaliable(finallyDir);
	}

	/**
	 * 在硬盘上建立指定的文件
	 * 
	 * @param fileName
	 *            不带路径的文件名
	 * @return 返回带路径的文件全名。
	 */
	private DeclareFileInfo createFile(String fileName, String fileContent) {
		String messageSendFileName = "";
		try {
			CspParameterSet dirSet = this.getCspParameterSet();
			String sendDir = (dirSet == null ? "" : dirSet.getSendDir());
			CspMessageFunctions.checkSendDirIsAvaliable(sendDir);
			File dirFile = new File(sendDir + File.separator + subDir);
			// File dirFile = new File(CommonUtils.getSendDir() + File.separator
			// + subDir);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			messageSendFileName = sendDir
					+ File.separator
					+ (subDir.trim().equals("") ? ""
							: (subDir + File.separator)) + fileName;
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(messageSendFileName), "GBK");
			// BufferedWriter bw = new BufferedWriter(writer);
			BufferedWriter bw = new BufferedWriter(out);
			// for (int i = 0; i < lines.size(); i++) {
			// bw.write((String) lines.get(i) + "\n");
			// // bw.write(new String(((String) lines.get(i) +
			// // "\n").getBytes(), "GBK"));
			// }
			bw.write(fileContent);
			bw.flush();
			bw.close();
			File parentFile = (new File(sendDir)).getParentFile();
			if (parentFile != null) {
				String backupDir = parentFile.getPath()
						+ File.separator
						+ (subDir.trim().equals("") ? ""
								: (subDir + File.separator)) + "backupDir";
				File backupDirFile = new File(backupDir);
				if (!backupDirFile.exists()) {
					backupDirFile.mkdir();
				}
				CspMessageFunctions.copyFile(new File(messageSendFileName),
						backupDir);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (fileName.indexOf("ReturnReceipt") < 0) {
			saveMessageSendInfo(messageSendFileName);
		}
		DeclareFileInfo fileInfo = new DeclareFileInfo();
		fileInfo.setFileName(fileName);
		fileInfo.setFileSize((new File(messageSendFileName)).length());
		return fileInfo;
	}

	/**
	 * 重新申报
	 */
	public boolean reSendFile(File file) {
		CspParameterSet dirSet = this.getCspParameterSet();
		if (dirSet == null || dirSet.getSendDir() == null
				|| dirSet.getSendDir().trim().equals("")) {
			throw new RuntimeException("报文路径设置不正确，请先设置路径！");
		}
		String sendDir = dirSet.getSendDir();
		return CspMessageFunctions.copyFile(file, sendDir);
	}

	/**
	 * 保存发送报文信息
	 * 
	 * @param fileName
	 */
	protected abstract void saveMessageSendInfo(String fileName);

	/**
	 * 获取报文文件
	 * 
	 * @param messageFileName
	 * @return
	 */
	protected File getMessageFile(String messageFileName) {
		if (messageFileName.indexOf(File.separator) >= 0) {
			int lastIndex = messageFileName.lastIndexOf(File.separator);
			messageFileName = messageFileName.substring(lastIndex + 1);
		}
		CspParameterSet dirSet = this.getCspParameterSet();
		String sendDir = (dirSet == null ? "" : dirSet.getSendDir());
		File file = new File(sendDir + File.separator
				+ (subDir.trim().equals("") ? "" : (subDir + File.separator))
				+ messageFileName);
		if (file.exists()) {
			return file;
		} else {
			File parentFile = (new File(sendDir)).getParentFile();
			file = new File(parentFile.getPath()
					+ File.separator
					+ (subDir.trim().equals("") ? ""
							: (subDir + File.separator)) + "backupDir"
					+ File.separator + messageFileName);
			if (file.exists()) {
				return file;
			}
		}
		return null;
	}

	/**
	 * 生成模拟回执
	 * 
	 * @param fileName
	 * @param mapData
	 */
	public void makeDefaultReturnInfoMessage(String fileName, Map mapData) {
		// File file = new File(fileName);!file.exists()
		File file = getMessageFile(fileName);
		if (file == null || !file.exists()) {
			throw new RuntimeException("文件" + fileName + "不存在");
		}
		makeDefaultReturnInfoMessage(file, mapData);
	}

	/**
	 * 取得回执报文的格式文件名
	 * 
	 * @return
	 */
	protected abstract String getReturnFormatFileName();

	/**
	 * 取得产生回执报文的数据
	 * 
	 * @param file
	 * @param emsNo
	 * @return
	 */
	protected abstract Map<String, List> getDefaultReturnInfoData(File file,
			Map mapData);

	/**
	 * 生成模拟回执
	 * 
	 */
	private void makeDefaultReturnInfoMessage(File file, Map mapData) {
		String fileName = getReturnFormatFileName();
		Map<String, List> hmData = this.getDefaultReturnInfoData(file, mapData);
		DeclareFileInfo fileInfo = this.exportMessage(fileName, hmData, null);
		CspParameterSet dirSet = this.getCspParameterSet();
		String recvDir = (dirSet == null ? "" : dirSet.getRecvDir());
		CspMessageFunctions.checkRecvDirIsAvaliable(recvDir);
		String sendDir = (dirSet == null ? "" : dirSet.getSendDir());
		CspMessageFunctions.checkSendDirIsAvaliable(sendDir);
		File dirFile = new File(recvDir + File.separator + subDir);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File returnFile = new File(sendDir + File.separator
				+ (subDir.trim().equals("") ? "" : (subDir + File.separator))
				+ fileInfo.getFileName());
		CspMessageFunctions.moveFile(returnFile, recvDir + File.separator
				+ subDir);
	}

	// /**
	// * 电子手册-生成QP导入需要的xml文件
	// *
	// * @param head
	// * 要生成报文的表头
	// * @param declareType
	// * 申报类型
	// * @return 生成的报文的全称（包括路径）
	// */
	// public synchronized void exportQPMessage(String messageFileName,
	// String sysType, String formatFileName, Map<String, List> hmData,
	// ProgressInfo info) {
	// InputStream formatStream = this
	// .getClass()
	// .getClassLoader()
	// .getResourceAsStream(
	// "com/bestway/bcs/message/qpmsgformat/" + formatFileName);
	// if (formatStream == null) {
	// throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
	// }
	// SAXBuilder sax = new SAXBuilder();
	// StringBuffer fileContent = new StringBuffer();
	// Document doc;
	// try {
	// doc = sax.build(formatStream);
	// Element message = doc.getRootElement();
	// List datas = null;
	// String dataId = message.getAttributeValue("data-id");
	// if (dataId != null) {
	// datas = hmData.get(dataId);
	// if (datas == null || datas.size() <= 0) {
	// throw new RuntimeException("系统找不到需要生成文件的数据！");
	// }
	// }
	// List<Element> fields = new ArrayList<Element>();
	// for (int i = 0; i < message.getChildren().size(); i++) { // <message
	// Element childElement = ((Element) message.getChildren().get(i));
	// if (childElement.getName().equals("field")) {
	// fields.add(childElement);
	// }
	// }
	// if (datas != null && fields.size() > 0) {
	// for (int i = 0; i < datas.size(); i++) {
	// Object obj = datas.get(i);
	// for (int k = 0; k < fields.size(); k++) {
	// String format = ((Element) fields.get(k)) // 分解<field
	// .getChildText("format");
	// String fieldName = ((Element) fields.get(k))
	// .getChildText("field-name");
	// String fieldFlag = ((Element) fields.get(k))
	// .getChildText("field-flag");
	// String fielddesc = ((Element) fields.get(k))
	// .getChildText("description");
	// String nullpara = ((Element) fields.get(k))
	// .getAttributeValue("nullable");
	// boolean nullable = ((nullpara == null || ""
	// .equals(nullpara.trim())) ? true : Boolean
	// .valueOf(nullpara));
	// Object oValue = null;
	// if (fieldName != null && !"".equals(fieldName.trim())) {
	// try {
	// // oValue = PropertyUtils.getProperty(obj,
	// // field);
	// // //
	// // 由字段名得到值
	// oValue = PropertyUtils.getNestedProperty(obj,
	// fieldName.trim()); // 由字段名得到值
	// } catch (Exception ex) {
	// // System.out.println("[生成报文信息] 未能获取字段:"
	// // + fieldName + ",描述：" + fielddesc
	// // + ", 使用缺省值null。");
	// // System.out.println(ex.getMessage());
	// oValue = null;
	// }
	// }
	// if (oValue == null) {
	// if (!nullable) {
	// throw new RuntimeException(fieldName + "-"
	// + fielddesc + "的值不允许为空，但是在数据中存在空值");
	// }
	// } else {
	// if (oValue instanceof String) {
	// if ("".equals(oValue) && !nullable) {
	// throw new RuntimeException(fieldName + "-"
	// + fielddesc + "的值不允许为空，但是在数据中存在空值");
	// }
	// }
	// }
	// Object oValue1 = specialProcessValue(oValue, fieldName,
	// format, fieldFlag);// 特殊处理日期
	// String sValue = null;
	// try {
	// if (oValue instanceof Date && format.equals("Z(8)")) {
	// sValue = String.valueOf(oValue1);
	// } else {
	// sValue = CspMessageFunctions.formatVariant(
	// oValue1, format); // 进行格式处理
	// }
	// // System.out.println("小:" + sValue);
	// sValue = changeStr(sValue);
	// } catch (Exception ex) {
	// // System.out.println("格式化变量时出错，字段：" + fieldName
	// // + ",值：" + oValue + "格式：" + format);
	// // throw ex;
	// }
	// fileContent.append((sValue == null ? ""
	// : replaceSpecialChar(sValue)).trim());
	// if (k != fields.size() - 1) {
	// fileContent.append("||");
	// } else {
	// fileContent.append("\n");
	// }
	// }
	// // 繁转简
	// // if (CommonUtils.getIsBigToGBK()!=null &&
	// // CommonUtils.getIsBigToGBK().equals("1")){
	// if (info != null) {
	// info.setCurrentNum(info.getCurrentNum() + 1);
	// // System.out.println("currentNum:"+info.getCurrentNum());
	// }
	// }
	// }
	// } catch (Exception e) {
	// throw new RuntimeException(e.getMessage());
	// } finally {
	// try {
	// formatStream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// OutputStreamWriter out = null;
	// try {
	// // String dirName = File.separator+"QPMessage" + File.separator +
	// // "bcs";
	// File dirFile = new File("QPMessage");
	// if (!dirFile.exists()) {
	// dirFile.mkdir();
	// }
	// dirFile = new File("QPMessage" + File.separator + "bcs");
	// if (!dirFile.exists()) {
	// dirFile.mkdir();
	// }
	// // messageSendFileName = sendDir
	// // + File.separator
	// // + (subDir.trim().equals("") ? ""
	// // : (subDir + File.separator)) + fileName;
	// out = new OutputStreamWriter(new FileOutputStream(new File(
	// "QPMessage" + File.separator + "bcs" + File.separator
	// + messageFileName)), "GB2312");
	// // BufferedWriter bw = new BufferedWriter(writer);
	// BufferedWriter bw = new BufferedWriter(out);
	// // for (int i = 0; i < lines.size(); i++) {
	// // bw.write((String) lines.get(i) + "\n");
	// // // bw.write(new String(((String) lines.get(i) +
	// // // "\n").getBytes(), "GBK"));
	// // }
	// bw.write(fileContent.toString());
	// bw.flush();
	// bw.close();
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// } finally {
	// try {
	// out.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// File file = new File("QPMessage" + File.separator + "bcs"
	// + File.separator + messageFileName);
	// CspExportQPMessageInfo messageInfo = new CspExportQPMessageInfo();
	// messageInfo.setFileName(messageFileName);
	// messageInfo.setSysType(sysType);
	// if (file.exists()) {
	// messageInfo.setFileSize(file.length() / 1000.0);
	// }
	// this.cspMessageDao.saveOrUpdate(messageInfo);
	// }
	//
	// /**
	// * 从服务器端下载文件
	// *
	// * @param backupFileInfo
	// * @return
	// */
	// public byte[] downloadMessageFile(CspExportQPMessageInfo messageInfo) {
	// String fileName = "QPMessage" + File.separator + "bcs" + File.separator
	// + messageInfo.getFileName();
	// File file = new File(fileName);
	// FileInputStream fileInputStream = null;
	// try {
	// fileInputStream = new FileInputStream(file);
	// byte[] fileContent = new byte[(int) file.length()];
	// fileInputStream.read(fileContent);
	// return fileContent;
	// } catch (Exception e) {
	// throw new RuntimeException(e.getMessage());
	// } finally {
	// try {
	// fileInputStream.close();
	// } catch (IOException e1) {
	// throw new RuntimeException(e1.getMessage());
	// }
	// }
	// }
	//
	// /**
	// * 删处QP导入文件信息
	// *
	// * @param messageInfo
	// */
	// public void deleteCspExportQPMessageInfo(CspExportQPMessageInfo
	// messageInfo) {
	// String fileName = messageInfo.getFileName();
	// String dirName = "QPMessage" + File.separator + "bcs";
	// File file = new File(dirName + File.separator + fileName);
	// if (file.exists()) {
	// file.delete();
	// }
	// this.cspMessageDao.deleteCspExportQPMessageInfo(messageInfo);
	// }
	/**
	 * 根据回执报文，判断申报是否成功
	 */
	public abstract boolean checkIsSuccess(String businessType,
			List<CspReceiptResult> list, TempCspReceiptResultInfo info);

	/**
	 * 根据回执报文，判断申报是否失败
	 * 
	 * @param list
	 * @return
	 */
	public abstract boolean checkIsFailure(String businessType,
			List<CspReceiptResult> list, TempCspReceiptResultInfo info);

	/**
	 * 取得业务类型中文描述
	 * 
	 * @param businessType
	 * @return
	 */
	protected abstract String getBusinessTypeDesc(String businessType);

	/**
	 * 报文回执处理
	 * 
	 * @param messageStream
	 * @return CheckInfo
	 */
	public String processMessage(String businessType, String copEmsNo,
			CspProcessMessage process, List<CspReceiptResult> lsReturnFile) {
		System.out.println("报文回执处理");
		System.out.println("lsReturnFile=" + lsReturnFile.size());
		this.checkMessageDirSet();
		StringBuffer sb = new StringBuffer();
		if (lsReturnFile.size() <= 0) {
			throw new RuntimeException("企业内部编号为:" + copEmsNo + "的"
					+ getBusinessTypeDesc(businessType) + "没有回执报文");
		}
		for (int i = 0; i < lsReturnFile.size(); i++) {
			CspReceiptResult receiptResult = (CspReceiptResult) lsReturnFile
					.get(i);
			String result = processSingleFile(receiptResult);
			if (result != null && !"".equals(result.trim())) {
				sb.append("<" + result + ">"
						+ CspProcessResult.getResultDesc(result) + "\n");
			}
		}
		TempCspReceiptResultInfo info = new TempCspReceiptResultInfo();
		if (this.checkIsSuccess(businessType, lsReturnFile, info)) {// list
			System.out.println("进入成功");
			process.successHandling(info);
		} else if (this.checkIsFailure(businessType, lsReturnFile, info)) {// list
			System.out.println("进入失败");
			process.failureHandling(info);
		}
		// 将回执文件移动到回执处理完成的存放路径
		System.out.println("将回执文件移动到回执处理完成的存放路径");
		for (int i = 0; i < lsReturnFile.size(); i++) {
			CspReceiptResult receiptResult = (CspReceiptResult) lsReturnFile
					.get(i);
			File file = receiptResult.getFile();
			if (!moveFileToProcessedDir(file)) {
				throw new RuntimeException("移除已处理的回执文件失败,可能是回执处理完成的存放路径设置不正确");
			}
		}
		System.out.println("sb.toString()=" + sb.toString());
		return sb.toString();
	}

	/**
	 * 读取回执报文的报文头信息
	 * 
	 * @param rootElement
	 * @param businessType
	 * @param copEmsNo
	 * @return
	 */
	protected abstract CspReceiptResult readReceiptResultHead(
			Element rootElement, String businessType, String copEmsNo);

	/**
	 * 读取回执的明细信息
	 * 
	 * @param rootElement
	 * @return
	 */
	protected abstract List<CspReceiptResultDetail> readReceiptResultDetail(
			Element rootElement);

	/**
	 * 处理回执
	 * 
	 * @param process
	 * @param file
	 */
	protected String processSingleFile(CspReceiptResult receiptResult) {
		writeReceiveInfo(receiptResult,
				receiptResult.getReceiptResultDetailList(),
				receiptResult.getFileName());
		return receiptResult.getChkMark();
	}

	/**
	 * 将收到的回执信息记录到数据库中
	 * 
	 * @param receiptResult
	 * @param lsResultInfo
	 * @param fileName
	 */
	private void writeReceiveInfo(CspReceiptResult receiptResult,
			List<CspReceiptResultDetail> lsResultInfo, String fileName) {
		try {
			receiptResult.setUserName(CommonUtils.getRequest().getUser()
					.getUserName());
			receiptResult.setCompany(CommonUtils.getCompany());
			receiptResult.setFileName(fileName);
			cspMessageDao.saveReceiptResult(receiptResult);
			if (lsResultInfo != null) {
				for (CspReceiptResultDetail resultInfo : lsResultInfo) {
					if (resultInfo != null) {
						resultInfo.setCspReceiptResult(receiptResult);
						cspMessageDao.saveReceiptResultDetail(resultInfo);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将处理完的回执移动到处理完的目录下
	 * 
	 * @param file
	 * @return
	 */
	protected boolean moveFileToProcessedDir(File file) {
		try {
			CspParameterSet dirSet = this.getCspParameterSet();
			String finallyDir = (dirSet == null ? "" : dirSet.getFinallyDir());
			CspMessageFunctions.checkFinallyDirIsAvaliable(finallyDir);
			File destFile = new File(finallyDir
					+ File.separator
					+ (subDir.trim().equals("") ? ""
							: (subDir + File.separator)) + file.getName());
			if (destFile.exists()) {
				destFile.delete();
			}
			if (moveFile(file, destFile)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 移动文件
	 */
	private boolean moveFile(File fSrc, File fDest) {
		try {
			FileUtils.copyFile(fSrc, fDest);
			if (!fSrc.delete()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 取得没有处理的回执
	 * 
	 * @param lsResult
	 * @param fileDir
	 * @param businessType
	 * @param copEmsNo
	 */
	protected void getNotProcessReturnFile(List lsResult, File fileDir,
			String businessType, String copEmsNo) {
		File[] files = fileDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isFile()) {
				if (files[i].isDirectory()) {
					// System.out.println("Directory:" + files[i].getName());
					getNotProcessReturnFile(lsResult, files[i], businessType,
							copEmsNo);
				}
				continue;
			}
			InputStream messageStream;
			try {
				messageStream = new FileInputStream(files[i]);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			SAXBuilder sax = new SAXBuilder();
			Document doc;
			try {
				doc = sax.build(messageStream);
				Element root = doc.getRootElement();
				CspReceiptResult result = this.readReceiptResultHead(root,
						businessType, copEmsNo);
				if (result == null) {
					continue;
				}
				result.setNoticeDate(new Date(files[i].lastModified()));
				result.setFileName(files[i].getName());
				result.setFile(files[i]);
				result.setIsSelected(true);
				result.setReceiptResultDetailList(this
						.readReceiptResultDetail(root));
				lsResult.add(result);
			} catch (Exception e) {
				// throw new RuntimeException(e.getMessage());
				e.printStackTrace();
				System.out.println(files[i].getName() + "不是一个有效的XML格式文件！");
			} finally {
				try {
					messageStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// private Date getDatatime() {
	// File file = new File("d:/test.txt");
	// long modifiedTime = file.lastModified();
	// Date d = new Date(modifiedTime);
	// //long newModifiedTime = System.currentTimeMillis();
	// //boolean success = file.setLastModified(newModifiedTime);
	// return new Date(file.lastModified());
	//
	// }

	/**
	 * 查询没有处理的回执文件
	 * 
	 * @return
	 */
	public List findNotProcessReturnFile(String businessType, String copEmsNo) {
		List lsResult = new ArrayList();
		CspParameterSet dirSet = this.getCspParameterSet();
		String recvDir = (dirSet == null ? "" : dirSet.getRecvDir());
		CspMessageFunctions.checkRecvDirIsAvaliable(recvDir);
		File path = new File(recvDir + File.separator + subDir);
		if (!path.exists()) {
			path.mkdir();
		}
		getNotProcessReturnFile(lsResult, path, businessType, copEmsNo);
		Collections.sort(lsResult);
		return lsResult;
	}

	/**
	 * 删除接收到的回执文件
	 * 
	 * @param cspReceiptResult
	 */
	public void deleteNotProcessReturnFile(CspReceiptResult cspReceiptResult) {
		if (cspReceiptResult.getFile().exists()) {
			cspReceiptResult.getFile().delete();
		}
	}

	/**
	 * 根据模块类型获取报文格式模板文件
	 * 
	 * @param type
	 * @return
	 */
	public abstract String getFormatFileNameByType(String type);

	// /**
	// * 根据模块类型获取报文格式模板文件
	// *
	// * @param type
	// * @return
	// */
	// public String getFormatFileNameByType(String type) {
	// String formatFileName = "";
	// if (CspBusinessType.EMS_POR_WJ.equals(type)) {
	// formatFileName = "CspDictPorFormat.xml";
	// } else if (CspBusinessType.EMS_POR_BILL.equals(type)) {
	// formatFileName = "CspContractFormat.xml";
	// } else if (CspBusinessType.CANCEL_AFTER_VERIFICA.equals(type)) {
	// formatFileName = "CspContractCavFormat.xml";
	// }
	// return formatFileName;
	// }

	// /**
	// * 根据表头的类，获取对应的格式文件 注意：回执处理也可使用该函数
	// *
	// * @param headClass
	// * @param declareType
	// * 1.申报 2.变更
	// * @return
	// */
	// private InputStream getFormatFile(String fileName) {
	// InputStream is = this.getClass().getClassLoader().getResourceAsStream(
	// "com/bestway/bcs/message/messageformat/" + fileName);
	// return is;
	// }

	// private File getMessageFile(String messageFileName) {
	// if (messageFileName.indexOf(File.separator) >= 0) {
	// int lastIndex = messageFileName.lastIndexOf(File.separator);
	// messageFileName = messageFileName.substring(lastIndex + 1);
	// }
	// CspParameterSet dirSet = this.cspMessageQueryDao.findCspParameterSet();
	// String sendDir = (dirSet == null ? "" : dirSet.getSendDir());
	// File file = new File(sendDir + File.separator
	// + (subDir.trim().equals("") ? "" : (subDir + File.separator))
	// + messageFileName);
	// if (file.exists()) {
	// return file;
	// } else {
	// File parentFile = (new File(sendDir)).getParentFile();
	// file = new File(parentFile.getPath()
	// + File.separator
	// + (subDir.trim().equals("") ? ""
	// : (subDir + File.separator)) + "backupDir"
	// + File.separator + messageFileName);
	// if (file.exists()) {
	// return file;
	// }
	// }
	// return null;
	// }
	/**
	 * 抓取报文备份
	 */
	private File getMessageFileBak(String messageFileName) {
		if (messageFileName.indexOf(File.separator) >= 0) {
			int lastIndex = messageFileName.lastIndexOf(File.separator);
			messageFileName = messageFileName.substring(lastIndex + 1);
		}
		CspParameterSet dirSet = this.getCspParameterSet();
		String sendDir = (dirSet == null ? "" : dirSet.getSendDir());
		File file = new File(sendDir + File.separator
				+ (subDir.trim().equals("") ? "" : (subDir + File.separator))
				+ messageFileName);
		// if (file.exists()) {
		// return file;
		// } else {
		File parentFile = (new File(sendDir)).getParentFile();
		file = new File(parentFile.getPath() + File.separator
				+ (subDir.trim().equals("") ? "" : (subDir + File.separator))
				+ "backupDir" + File.separator + messageFileName);
		if (file.exists()) {
			return file;
		}
		// }
		return null;
	}

	/**
	 * 抓取报文备份
	 */
	public File getSendFileByName(String fName) {
		return this.getMessageFileBak(fName);
	}

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	public Map<String, Object> readCspMessageFileContent(String formatFileName,
			String messageFileName) {
		Map<String, Object> result = new HashMap<String, Object>();
		SAXBuilder sax = new SAXBuilder();
		InputStream formatStream = getFormatFile(formatFileName); // 得到相应的报文格式
		System.out.println("------------formatFileName:" + formatFileName);
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		Document doc;
		List lsTree = new ArrayList();
		Map<String, List> fieldFlagMap = new HashMap<String, List>();
		Map<String, List> fieldDescMap = new HashMap<String, List>();
		try {
			doc = sax.build(formatStream);
			Element root = doc.getRootElement();
			Element elemSectionFlag = root.getChild("section-flag");
			String sectionFlag = elemSectionFlag.getTextTrim();
			if (elemSectionFlag != null) {
				sectionFlag = elemSectionFlag.getTextTrim();
			}
			// String sectionFlag ="";
			Element elemDescription = root.getChild("description");
			String description = "";
			if (elemDescription != null) {
				description = elemDescription.getTextTrim();
			}
			getMessageTreeInfo(lsTree, root, null);
			for (int i = 0; i < root.getChildren().size(); i++) {
				Element section = ((Element) root.getChildren().get(i));
				readCspMessageFileFormat(section, lsTree, fieldFlagMap,
						fieldDescMap, sectionFlag, description);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				formatStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File massageFile = getMessageFile(messageFileName);
		if (massageFile == null) {
			throw new RuntimeException("没有找到文件：" + messageFileName);
		}
		Map<String, List> filedVauleMap = getMessageFileContent(fieldFlagMap,
				massageFile);
		result.put("MessageTree", lsTree);
		result.put("FieldDesc", fieldDescMap);
		result.put("FieldVaue", filedVauleMap);
		return result;
	}

	/**
	 * 读取报文内容
	 * 
	 * @param fieldFlagMap
	 * @param messageFile
	 * @return
	 */
	private Map<String, List> getMessageFileContent(
			Map<String, List> fieldFlagMap, File messageFile) {
		Map<String, List> fieldContentMap = new HashMap<String, List>();
		SAXBuilder sax = new SAXBuilder();
		InputStream formatStream = null;
		try {
			formatStream = new FileInputStream(messageFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Document doc;
		try {
			doc = sax.build(formatStream);
			Element root = doc.getRootElement();
			Iterator iterator = fieldFlagMap.keySet().iterator();
			while (iterator.hasNext()) {
				String fieldFlags = iterator.next().toString();
				// System.out.println("=============="+fieldFlags);
				List lsFiledFlag = fieldFlagMap.get(fieldFlags);
				List lsFieldValue = new ArrayList();
				String[] fieldFlagArray = fieldFlags.split("-");
				List<Element> lsElement = new ArrayList<Element>();
				lsElement.add(root);
				List<Element> lsFieldParent = getMessageField(fieldFlagArray,
						lsElement, 1);
				for (int i = 0; i < lsFieldParent.size(); i++) {
					Element childElement = (Element) lsFieldParent.get(i);
					String[] values = new String[lsFiledFlag.size()];
					for (int j = 0; j < lsFiledFlag.size(); j++) {
						String fieldFlag = lsFiledFlag.get(j).toString();
						Element fieldElement = childElement.getChild(fieldFlag);
						if (fieldElement != null) {
							values[j] = fieldElement.getTextTrim().trim();
						}
					}
					// System.out.println("ArrayList:"+values);
					lsFieldValue.add(values);
				}
				fieldContentMap.put(fieldFlags, lsFieldValue);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				formatStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fieldContentMap;
	}

	/**
	 * 取得报文的字段信息
	 * 
	 * @param fieldFlags
	 * @param lsElement
	 * @param layer
	 * @return
	 */
	private List<Element> getMessageField(String[] fieldFlags, List lsElement,
			int layer) {
		List lsChildElement = new ArrayList();
		String fieldFlag = fieldFlags[layer];
		for (int i = 0; i < lsElement.size(); i++) {
			Element element = (Element) lsElement.get(i);
			lsChildElement.addAll(element.getChildren(fieldFlag.trim()));
		}
		if (layer == fieldFlags.length - 1) {
			return lsChildElement;
		} else {
			int childLayer = layer + 1;
			return getMessageField(fieldFlags, lsChildElement, childLayer);
		}
	}

	// private void getMessageTreeInfo(List lsTree,Element elemFormat){
	// Element parent = elemFormat.getParent();
	// TempCspMessageTreeInfo tempTreeInfo = new TempCspMessageTreeInfo();
	// if (parent != null) {
	// Element parentDesc = parent.getChild("description");
	// if (parentDesc != null) {
	// tempTreeInfo.setParentDesc(parentDesc.getTextTrim());
	// }
	// }
	// Element childDesc = elemFormat.getChild("description");
	// if (childDesc != null) {
	// tempTreeInfo.setChildDesc(childDesc.getTextTrim());
	// }
	// Element elemSectionFlag = elemFormat.getChild("section-flag");
	// tempTreeInfo.setSectionFlag(elemSectionFlag.getTextTrim());
	// lsTree.add(tempTreeInfo);
	// }
	/**
	 * 读取报文内容
	 */
	private void getMessageTreeInfo(List lsTree, Element elemFormat,
			String parentDesc) {
		// Element parent = elemFormat.getParent();
		TempCspMessageTreeInfo tempTreeInfo = new TempCspMessageTreeInfo();
		// if (parent != null) {
		// Element parentDesc = parent.getChild("description");
		// if (parentDesc != null) {
		// tempTreeInfo.setParentDesc(parentDesc.getTextTrim());
		// }
		// }
		tempTreeInfo.setParentDesc(parentDesc);
		Element childDesc = elemFormat.getChild("description");
		if (childDesc != null) {
			tempTreeInfo.setChildDesc(childDesc.getTextTrim());
		}
		Element elemSectionFlag = elemFormat.getChild("section-flag");
		tempTreeInfo.setSectionFlag(elemSectionFlag.getTextTrim());
		lsTree.add(tempTreeInfo);
	}

	/**
	 * 读取报文格式文件内容
	 * 
	 * @param elemFormat
	 * @param lsTree
	 * @param fieldFlagMap
	 * @param fieldDescMap
	 * @param patentSectionFlag
	 * @param patentDesc
	 */
	private void readCspMessageFileFormat(Element elemFormat, List lsTree,
			Map<String, List> fieldFlagMap, Map<String, List> fieldDescMap,
			String patentSectionFlag, String patentDesc) {
		if (elemFormat.getName().equals("section")) {
			getMessageTreeInfo(lsTree, elemFormat, patentDesc);
			Element elemSectionFlag = elemFormat.getChild("section-flag");
			String sectionFlag = patentSectionFlag;
			if (elemSectionFlag != null) {
				sectionFlag = (sectionFlag + "-" + elemSectionFlag
						.getTextTrim());
			}
			Element elemDescription = elemFormat.getChild("description");
			String childDesc = (elemDescription == null ? "" : elemDescription
					.getTextTrim());
			List<Element> fields = new ArrayList<Element>();
			List childs = elemFormat.getChildren();
			// 得到字段列表
			for (int j = 0; j < childs.size(); j++) { // <section
				Element childElement = ((Element) childs.get(j));
				if (childElement.getName().equals("field")) {
					fields.add(childElement);
				} else {
					if (childElement.getName().equals("section")) {
						readCspMessageFileFormat(childElement, lsTree,
								fieldFlagMap, fieldDescMap, sectionFlag,
								childDesc);
					} else if (childElement.getName().equals("sub-section")) {
						String subFileName = "";
						Element subFile = childElement.getChild("file-name");
						if (subFile != null) {
							subFileName = subFile.getTextTrim();
						}
						if (subFileName == null
								|| "".equals(subFileName.trim())) {
							continue;
						}
						readSubSectionFormat(lsTree, fieldFlagMap,
								fieldDescMap, sectionFlag, childDesc,
								subFileName);
					} else if (childElement.getName().equals("sub-fields")) {
						String subFileName = "";
						Element subFile = childElement.getChild("file-name");
						if (subFile != null) {
							subFileName = subFile.getTextTrim();
						}
						if (subFileName == null
								|| "".equals(subFileName.trim())) {
							continue;
						}
						readSubFormatFields(fields, subFileName);
					}
				}
			}
			List<String> lsFieldFlag = new ArrayList<String>();
			List<String> lsFieldDesc = new ArrayList<String>();
			for (int i = 0; i < fields.size(); i++) {
				String fieldFlag = ((Element) fields.get(i)).getChildText(
						"field-flag").trim();
				String fieldDesc = ((Element) fields.get(i)).getChildText(
						"description").trim();
				lsFieldFlag.add(fieldFlag);
				lsFieldDesc.add(fieldDesc);
			}
			fieldFlagMap.put(sectionFlag, lsFieldFlag);
			fieldDescMap.put(sectionFlag, lsFieldDesc);
		} else if (elemFormat.getName().equals("sub-section")) {
			String subFileName = "";
			Element subFile = elemFormat.getChild("file-name");
			if (subFile != null) {
				subFileName = subFile.getTextTrim();
			}
			if (subFileName == null || "".equals(subFileName.trim())) {
				return;
			}
			readSubSectionFormat(lsTree, fieldFlagMap, fieldDescMap,
					patentSectionFlag, patentDesc, subFileName);
		}
	}

	/**
	 * 读取报文格式文件内容
	 * 
	 * @param lsTree
	 * @param fieldFlag
	 * @param fieldDesc
	 * @param patentSectionFlag
	 * @param parentDesc
	 * @param subFileName
	 */
	private void readSubSectionFormat(List lsTree, Map fieldFlag,
			Map fieldDesc, String patentSectionFlag, String parentDesc,
			String subFileName) {
		// System.out.println("-----------" + subFileName);
		InputStream formatStream = getFormatFile(subFileName);
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		Document doc;
		SAXBuilder sax = new SAXBuilder();
		try {
			doc = sax.build(formatStream);
			Element message = doc.getRootElement();
			for (int i = 0; i < message.getChildren().size(); i++) { // <message
				Element subsection = ((Element) message.getChildren().get(i));
				readCspMessageFileFormat(subsection, lsTree, fieldFlag,
						fieldDesc, patentSectionFlag, parentDesc);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				formatStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取Element下所有子节点的信息
	 * 
	 * @param rootElement
	 * @return
	 */
	protected Map<String, String> getElementChildData(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		List<Element> list = element.getChildren();
		for (Element childElem : list) {
			String key = childElem.getName().trim();
			String value = childElem.getTextTrim();
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 获取Element下所有子节点的信息
	 * 
	 * @param rootElement
	 * @return
	 */
	protected Map<String, String> getElementChildData(Element root,
			String elementFlag) {
		Map<String, String> map = new HashMap<String, String>();
		Element element = root.getChild(elementFlag);
		if (element != null) {
			return this.getElementChildData(element);
		}
		return map;
	}

	/**
	 * 读取报文格式文件内容
	 * 
	 * @param fields
	 * @param fileName
	 */
	private void readSubFormatFields(List fields, String fileName) {
		// System.out.println("-----------" + fileName);
		InputStream formatStream = getFormatFile(fileName);
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		Document doc;
		SAXBuilder sax = new SAXBuilder();
		try {
			doc = sax.build(formatStream);
			Element message = doc.getRootElement();
			for (int i = 0; i < message.getChildren().size(); i++) {
				Element subElement = ((Element) message.getChildren().get(i));
				if (subElement.getName().equals("field")) {
					fields.add(subElement);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				formatStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取报文的申报类型
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
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
			Element elemPtsResult = root.getChild("PTS_SIGN");
			if (elemPtsResult == null) {
				return declareType;
			}
			Element elemDeclareType = elemPtsResult.getChild("DECLARE_TYPE");
			if (elemDeclareType != null) {
				declareType = elemDeclareType.getTextTrim();
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

	// /**
	// * 开始发送邮件
	// *
	// */
	// public void startSendMail() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// if (dirSet.getRemoteSignData() != null && dirSet.getRemoteSignData()) {
	// if (dirSet.getRemoteDxpMail() != null && dirSet.getRemoteDxpMail()) {
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.startSendMail();
	// }
	// }
	// }

	// /**
	// * 开始接收邮件
	// *
	// */
	// public void startReceiveMail() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// if (dirSet.getRemoteSignData() != null && dirSet.getRemoteSignData()) {
	// if (dirSet.getRemoteDxpMail() != null && dirSet.getRemoteDxpMail()) {
	// if (dirSet.getPort9097IsOpen() != null
	// && dirSet.getPort9097IsOpen()) {
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.startPushMailReceive();
	// }
	// }
	// }
	// }
	//
	// /**
	// * 停止发送邮件
	// *
	// */
	// public void stopSendMail() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.stopSendMail();
	// }

	// /**
	// * 停止接收邮件
	// *
	// */
	// public void stopPushReceiveMail() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.stopPushReceiveMail();
	// }

	// /**
	// * 紧急获取邮件
	// */
	// public void activeGet() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.activeGet();
	// }

	// /**
	// * 获取服务器上回执文件个数
	// *
	// * @return
	// */
	// public int getReturnFileCount() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getReturnFileCount();
	// }

	// /**
	// * 测试邮件服务器和中国电子口岸的连接是否正常
	// *
	// * @return
	// */
	// public boolean testServiceConnection() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.testServiceConnection();
	// }

	// /**
	// * 取得发送邮件的状态
	// *
	// * @return
	// */
	// public int getSendMailStatus() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getSendMailStatus();
	// }

	// /**
	// * 取得接收邮件的状态
	// *
	// * @return
	// */
	// public int getPushReceiveMailStatus() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getPushReceiveMailStatus();
	// }

	/**
	 * 验证IC及密码
	 * 
	 * @param pwd
	 * @return
	 */
	public String testIcCard() {
		CspParameterSet dirSet = this.getCspParameterSet();
		BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
		return serviceClient.testIcCard();
	}

	// /**
	// * 取得邮件服务器发送的状态信息
	// *
	// * @return
	// */
	// public MailSendStatusInfo getMailSendStatusInfo() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getMailSendStatusInfo();
	// }

	// /**
	// * 取得邮件服务器接收的状态信息
	// *
	// * @return
	// */
	// public MailReceiveStatusInfo getMailReceiveStatusInfo() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getMailReceiveStatusInfo();
	// }

	// /**
	// * 清空发送文件列表
	// *
	// *
	// */
	// public void clearMailSendFileList() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.clearMailSendFileList();
	// }
	//
	// /**
	// * 请空接收文件列表
	// *
	// */
	// public void clearMailReceiveFileList() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// serviceClient.clearMailReceiveFileList();
	// }
	//
	// /**
	// *
	// * 读取文件发送的日志
	// *
	// */
	// public String getSendLogFileContent(String logFileName) {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getSendLogFileContent(logFileName);
	// }
	//
	// /**
	// *
	// * 读取回执接收的日志
	// *
	// */
	// public String getReceiveLogFileContent(String logFileName) {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getReceiveLogFileContent(logFileName);
	// }
	//
	// /**
	// * 读取发送日志文件名
	// *
	// * @param beginDate
	// * @param endDate
	// * @return
	// */
	// public List getSendLogFileNameList(String beginDate, String endDate) {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getSendLogFileNameList(beginDate, endDate);
	// }
	//
	// /**
	// * 读取接收日志文件名
	// *
	// */
	// public List getReceiveLogFileNameList(String beginDate, String endDate) {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getReceiveLogFileNameList(beginDate, endDate);
	// }
	//
	// /**
	// *
	// * 获取服务器的连接地址
	// */
	// public String getConnectionStr() {
	// CspParameterSet dirSet = this.getCspParameterSet();
	// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
	// return serviceClient.getConnectionStr();
	// }

	/**
	 * 抓取邮箱参数设置
	 * 
	 * @return
	 */
	public CspParameterSet getCspParameterSet() {
		if (this.cspParameterSet != null
				&& this.cspParameterSet.getId() != null
				&& !"".equals(this.cspParameterSet.getId())
				&& this.cspParameterSet.getCompany().getId()
						.equals(CommonUtils.getAclUser().getCompany().getId())) {
			this.cspParameterSet = (CspParameterSet) this.cspMessageDao.load(
					this.cspParameterSet.getClass(),
					this.cspParameterSet.getId());
		} else {
			this.cspParameterSet = this.cspMessageDao.findCspParameterSet();
		}
		return this.cspParameterSet;
	}
}