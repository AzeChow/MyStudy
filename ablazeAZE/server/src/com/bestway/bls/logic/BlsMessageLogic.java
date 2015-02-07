package com.bestway.bls.logic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.dao.BlsMessageDao;
import com.bestway.bls.entity.BlsCollateBindResultType;
import com.bestway.bls.entity.BlsDeliveryResultType;
import com.bestway.bls.entity.BlsEntranceMessageResultType;
import com.bestway.bls.entity.BlsMessageSend;
import com.bestway.bls.entity.BlsParameterSet;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsReceiptResultType;
import com.bestway.bls.entity.BlsServiceID;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.common.CommonServerBig5GBConverter;
import com.bestway.common.CommonUtils;
import com.bestway.common.ProgressInfo;
import com.bestway.common.message.entity.TempCspMessageTreeInfo;
import com.bestway.common.message.entity.TempCspSignFieldInfo;

public class BlsMessageLogic {

	private BlsMessageDao blsMessageDao;

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public BlsMessageDao getBlsMessageDao() {
		return blsMessageDao;
	}

	public void setBlsMessageDao(BlsMessageDao blsMessageDao) {
		this.blsMessageDao = blsMessageDao;
	}

	/**
	 * 取得报税物流的参数设置
	 * 
	 * @return
	 */
	private BlsParameterSet getBlsParameterSet(Company company) {
		return this.blsMessageDao.findBlsParameterSet(company);
	}

	private String getBlsSendDir() {
		return System.getProperty("user.dir") + File.separator + "bls"
				+ File.separator + "sendDir";
	}

	/**
	 * 判断是否是内部自己使用
	 * 
	 * @return
	 */
	private boolean checkIsInnerUse(Company company) {
		BlsParameterSet parameterSet = this.getBlsParameterSet(company);
		if (parameterSet.getIsInnerUse() == null
				|| !parameterSet.getIsInnerUse()) {
			return false;
		} else {
			return true;
		}
	}

	public String getBlsServiceInfo(String serviceId, Company company) {
		// BlsParameterSet blsParameterSet = this.getBlsParameterSet();
		StringBuffer serviceInfo = new StringBuffer();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer contentInfo = new StringBuffer();
		serviceInfo.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		serviceInfo.append("<ServiceInfo>");
		serviceInfo.append("	<ServiceID>" + serviceId + "</ServiceID>");
		serviceInfo.append("	<RequestorDept>" + company.getCode()
				+ "</RequestorDept>");
		serviceInfo.append("	<Role>MnlManifestRole</Role>");
		String formatDate = dateFormat.format(new Date());
		serviceInfo.append("	<TimeStamp>" + formatDate + "</TimeStamp>");
		String keyCode = "MnlEpz";
		serviceInfo.append("	<Key>" + keyCode + "</Key>");
		// String icCode = BswHpServiceClient.getInstance(blsParameterSet)
		// .getIcCode();
		String icCode = "12345678";
		serviceInfo.append("	<CertificateID>" + "" + "</CertificateID>");
		contentInfo.append(serviceId);
		contentInfo.append(company.getCode());// ((Company)
		// CommonUtils.getCompany())
		contentInfo.append("MnlManifestRole");
		contentInfo.append(formatDate);
		contentInfo.append(keyCode);
		contentInfo.append(icCode);
		// String keySignature = BswHpServiceClient.getInstance(blsParameterSet)
		// .signData(contentInfo.toString());
		String keySignature = "12345678";
		serviceInfo
				.append("	<KeySignature>" + keySignature + "</KeySignature>");
		serviceInfo.append("	<FullSignature></FullSignature>");
		serviceInfo.append("</ServiceInfo>");
		return serviceInfo.toString();
	}

	/**
	 * 生成并发送报文
	 * 
	 * @param serviceType
	 * @param keyCode
	 * @param formatFileName
	 * @param queryValues
	 * @param hmData
	 * @param info
	 * @return
	 */
	public synchronized BlsReceiptResult declareMessage(String serviceType,
			String relateID, String keyCode, Map<String, String> queryValues,
			Map<String, List> hmData, ProgressInfo info, Company company) {
		String formatFileName = this.getFormatFileNameByType(serviceType);
		String msgType = getMsgType(formatFileName);

		InputStream formatStream = getFormatFile(formatFileName); // 得到相应的报文格式
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		String fileName = this.genFileName(msgType);
		String fileContent = this.exportMessage(company, formatStream,
				queryValues, hmData, info);
		fileName = createFile(fileName, fileContent);
		saveMessageSendInfo(company,fileName, serviceType, keyCode);
		String resultInfo = "";
		// 判断是否是内部自己使用
		boolean isInnerUse = this.checkIsInnerUse(company);
		if (isInnerUse) {// 如果内部使用
			resultInfo = this
					.makeDefaultReturnInfoMessage(serviceType, keyCode);
		} else {// 如果是海关申报
			String serviceId = BlsServiceType.getServiceIdByType(serviceType);
			String serviceInfo = getBlsServiceInfo(serviceId, company);
			resultInfo = hpServiceExec(serviceInfo, fileContent, company);
		}
		if (resultInfo == null) {
			throw new RuntimeException("申报返回值为空，向海关申报失败！");
		}
		BlsReceiptResult receiptResult = saveReceiptResult(serviceType,
				resultInfo, relateID, company);
		return receiptResult;
	}

	/**
	 * 调用海关的接口，进行报文的发送以及回执状态的查询
	 * 
	 * @param serviceInfostring
	 * @param billinfostring
	 * @return
	 */
	private String hpServiceExec(String serviceInfostring,
			String billinfostring, Company company) {
		BlsParameterSet blsParameterSet = this.getBlsParameterSet(company);
		return BswHpServiceClient.getInstance(blsParameterSet).hpServiceExec(
				serviceInfostring, billinfostring);
	}

	/**
	 * 抓取报文类型 ，为生成文件名称所用
	 * 
	 * @param formatStream
	 * @return
	 */
	private String getMsgType(String formatFileName) {
		InputStream formatStream = getFormatFile(formatFileName); // 得到相应的报文格式
		if (formatStream == null) {
			throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
		}
		SAXBuilder sax = new SAXBuilder();
		Document doc;
		try {
			doc = sax.build(formatStream);
			Element root = doc.getRootElement();
			Element message = root;
			String msgType = message.getAttributeValue("type");
			return msgType;
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
	}

	/**
	 * 生成报文
	 * 
	 * @param head
	 *            要生成报文的表头
	 * @param declareType
	 *            申报类型
	 * @return 生成的报文的全称（包括路径）
	 */
	private String exportMessage(Company company, InputStream formatStream,
			Map<String, String> queryValues, Map<String, List> hmData,
			ProgressInfo info) {
		// this.checkMessageDirSet();
		// this.startSendMail();
		// this.startReceiveMail();

		// if (info != null) {
		// int totalSize = 0;
		// Iterator iterator = hmData.values().iterator();
		// while (iterator.hasNext()) {
		// List list = (List) iterator.next();
		// totalSize += list.size();
		// }
		// info.setBeginTime(System.currentTimeMillis());
		// info.setTotalNum(totalSize);
		// info.setMethodName("正在生成文件,请稍候......");
		// System.out.println("totalSize:" + totalSize);
		// }
		System.out.println("begin create file:" + System.currentTimeMillis());
		int layer = 0;
		StringBuffer fileContent = new StringBuffer();
		StringBuffer signContent = new StringBuffer();
		SAXBuilder sax = new SAXBuilder();

		Document doc;
		try {
			doc = sax.build(formatStream);
			Element root = doc.getRootElement();
			Element message = root;

			fileContent.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>\n");
			// 获取section-flag
			// Element sectionFlag = message.getChild("section-flag");
			// fileContent.append("<" + sectionFlag.getTextTrim()
			// + " xmlns=\"un:epz\"\n");
			// for (int i = 0; i < message.getChildren().size(); i++) { //
			// <message
			// Element section = ((Element) message.getChildren().get(i));
			// makeFileContent(section, fileContent, signContent, queryValues,
			// hmData, layer, "", info);
			makeFileContent(company, message, fileContent, signContent,
					queryValues, hmData, layer, "", info);
			// }
			// fileContent.append("</" + sectionFlag.getTextTrim() + ">\n");

			String fileContentTemp = fileContent.toString();
			String signContentTemp = signContent.toString();
			// fileContentTemp = new String(fileContentTemp.getBytes(),
			// "GB2312");// "GB2312"
			// signContentTemp = new String(signContentTemp.getBytes(),
			// "GB2312");
			// System.out.println("signContentTemp------------"
			// + signContentTemp);
			// String signInfo = getConvertSignInfo(signContentTemp);
			// if (signInfo != null) {
			// int indexBegin = fileContentTemp.indexOf("<SIGN>");
			// int indexEnd = fileContentTemp.indexOf("</SIGN>");
			// if (indexBegin >= 0 && indexEnd >= 0) {
			// signInfo = CspMessageFunctions.formatVariant(signInfo,
			// "X(380)");
			// String oldSign = fileContentTemp.substring(indexBegin,
			// indexEnd + "</SIGN>".length());
			// fileContentTemp = fileContentTemp.replace(oldSign, "<SIGN>"
			// + signInfo + "</SIGN>");
			// }
			// }

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
		return fileContent.toString();
	}

	/**
	 * 取得一个字符串的签名信息
	 * 
	 * @param signContent
	 * @return
	 */
	private String getConvertSignInfo(String signContent) {
		// CspParameterSet dirSet = this.getCspParameterSet();
		// if (dirSet.getReadFromCard() == null || !dirSet.getReadFromCard()) {
		// return "";
		// } else {
		// // System.out.println("----------signContent:"+signContent);
		// ICCardInfo cardInfo = new ICCardInfo();
		// if (dirSet.getRemoteSignData() != null
		// && dirSet.getRemoteSignData()) {
		// if (dirSet.getRemoteDxpMail() != null
		// && dirSet.getRemoteDxpMail()) {
		// BswMailQpClient serviceClient = getDxpEusServiceClient(dirSet);
		// String[] strs = serviceClient.signMsgData(signContent);
		// if (strs != null) {
		// if (strs[1] != null && !"".equals(strs[1].trim())) {
		// throw new RuntimeException(strs[1]);
		// }
		// cardInfo.setSignData(strs[0]);
		// }
		// } else {
		// CspCardServer service = getCspCardServer(dirSet);
		// cardInfo = service.signMsgData(dirSet.getSeqNo(), dirSet
		// .getPwd(), signContent);
		// }
		// } else {
		// CSPCardUtils.signMsgData(dirSet.getSeqNo(), dirSet.getPwd(),
		// signContent, cardInfo);
		// }
		// //
		// System.out.println("----------getSignData:"+cardInfo.getSignData());
		// return cardInfo.getSignData();
		// }
		return "";
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
	private void makeFileContent(Company company, Element section,
			StringBuffer fileContent, StringBuffer signContent,
			Map<String, String> queryValues, Map<String, List> hmData,
			int layer, String parentdesc, ProgressInfo info) {
		if (section.getName().equals("section")) {
			List datas = null;
			Element description = section.getChild("description");
			Element query = section.getChild("query");
			if (query != null) {
				datas = this.queryData(company, query, queryValues,
						description == null ? "" : description.getTextTrim());
				if (datas == null || datas.size() <= 0) {
					return;
				}
			} else {
				Element dataId = section.getChild("data-id");
				if (dataId != null) {
					if (hmData != null && !dataId.getText().trim().equals("")) {
						datas = hmData.get(dataId.getText().trim());
						if (datas == null || datas.size() <= 0) {
							return;
						}
					} else {
						return;
					}
				}
			}
			Element sectionFlag = section.getChild("section-flag");
			Element sectionAttribute = section.getChild("section-attribute");
			fileContent.append(getPreSpaceString(layer)
					+ "<"
					+ sectionFlag.getTextTrim()
					+ (sectionAttribute == null ? "" : ("  " + sectionAttribute
							.getTextTrim())) + ">\n");

			String subDesc = parentdesc;
			if (description != null) {
				subDesc = (parentdesc + "-" + description.getTextTrim());
			}

			List<Element> fields = new ArrayList<Element>();
			List<Element> childs = section.getChildren();
			List<Element> listSection = new ArrayList<Element>();
			List<Element> listSubSection = new ArrayList<Element>();
			// 得到字段列表
			for (int j = 0; j < childs.size(); j++) { // <section
				Element childElement = ((Element) childs.get(j));
				if (childElement.getName().equals("field")) {
					fields.add(childElement);
				} else {
					if (childElement.getName().equals("section")) {
						listSection.add(childElement);
					} else if (childElement.getName().equals("sub-section")) {
						listSubSection.add(childElement);
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
			if (datas != null) {
				for (int i = 0; i < datas.size(); i++) {
					if (i != 0) {
						fileContent.append(getPreSpaceString(layer) + "<"
								+ sectionFlag.getTextTrim() + ">\n");
					}
					Object obj = datas.get(i);
					List<TempCspSignFieldInfo> lsSignInfo = new ArrayList<TempCspSignFieldInfo>();
					for (int k = 0; k < fields.size(); k++) {
						String format = ((Element) fields.get(k))
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
						// Object oValue1 = specialProcessValue(oValue,
						// fieldName,
						// format, fieldFlag);// 特殊处理日期
						// String sValue = null;
						// try {
						// if (oValue instanceof Date && format.equals("Z(8)"))
						// {
						// sValue = String.valueOf(oValue1);
						// } else {
						// sValue = CspMessageFunctions.formatVariant(
						// oValue1, format); // 进行格式处理
						// }
						// // System.out.println("小:" + sValue);
						// sValue = CommonServerBig5GBConverter.getInstance()
						// .big5ConvertToGB(sValue);
						// } catch (Exception ex) {
						// // System.out.println("格式化变量时出错，字段：" + fieldName
						// // + ",值：" + oValue + "格式：" + format);
						// // throw ex;
						// }
						String sValue = formatMessageValue(oValue, format);
						// if(oValue)
						fileContent.append(getPreSpaceString(layer + 1)
								+ "<"
								+ fieldFlag.trim()
								+ ">"
								+ (sValue == null ? ""
										: replaceSpecialChar(sValue)).trim()
								+ "</" + fieldFlag.trim() + ">\n");
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

					if (info != null) {
						info.setCurrentNum(info.getCurrentNum() + 1);
						// System.out.println("currentNum:"+info.getCurrentNum());
					}
					Map<String, String> mapValues = this
							.getNextLayerQueryValues(obj, section,
									description == null ? "" : description
											.getTextTrim());
					if (listSection.size() > 0) {
						for (Element childElement : listSection) {
							int newLayer = layer + 1;
							makeFileContent(company, childElement, fileContent,
									signContent, mapValues, hmData, newLayer,
									subDesc, info);
						}
					}
					if (listSubSection.size() > 0) {
						for (Element childElement : listSection) {
							String subFileName = "";
							Element subFile = childElement
									.getChild("file-name");
							if (subFile != null) {
								subFileName = subFile.getTextTrim();
							}
							if (subFileName == null
									|| "".equals(subFileName.trim())) {
								continue;
							}
							analyseSubSection(company, fileContent,
									signContent, mapValues, hmData, layer,
									subFileName, subDesc, info);
						}
					}
					if (i != datas.size() - 1) {
						fileContent.append(getPreSpaceString(layer) + "</"
								+ sectionFlag.getTextTrim() + ">\n");
					}
				}
			} else {
				if (listSection.size() > 0) {
					for (Element childElement : listSection) {
						int newLayer = layer + 1;
						makeFileContent(company, childElement, fileContent,
								signContent, queryValues, hmData, newLayer,
								subDesc, info);
					}
				}
				if (listSubSection.size() > 0) {
					for (Element childElement : listSection) {
						String subFileName = "";
						Element subFile = childElement.getChild("file-name");
						if (subFile != null) {
							subFileName = subFile.getTextTrim();
						}
						if (subFileName == null
								|| "".equals(subFileName.trim())) {
							continue;
						}
						analyseSubSection(company, fileContent, signContent,
								queryValues, hmData, layer, subFileName,
								subDesc, info);
					}
				}
			}
			fileContent.append(getPreSpaceString(layer) + "</"
					+ sectionFlag.getTextTrim() + ">\n");
		} else if (section.getName().equals("sub-section")) {
			String subFileName = "";
			Element subFile = section.getChild("file-name");
			if (subFile != null) {
				subFileName = subFile.getTextTrim();
			}
			if (subFileName == null || "".equals(subFileName.trim())) {
				return;
			}
			analyseSubSection(company, fileContent, signContent, queryValues,
					hmData, layer, subFileName, parentdesc, info);
		}
	}

	/**
	 * 格式化从实体对象中读取的数据
	 * 
	 * @param value
	 * @return
	 */
	private String formatMessageValue(Object value, String format) {
		if (value == null) {
			return "";
		}
		if (value instanceof String) {
			return CommonServerBig5GBConverter.getInstance().big5ConvertToGB(
					value.toString());
		} else if (value instanceof Date) {
			if (format != null && !"".equals(format.trim())) {
				DateFormat dateFormatTemp = new SimpleDateFormat(format);
				return dateFormatTemp.format(value);
			}
			String temp = "";
			String str = dateFormat.format(value);
			temp = str.substring(0, 10).trim() + "T" + str.substring(11).trim()
					+ ".0000000+08:00";
			return temp;
		} else if (value instanceof Double) {
			BigDecimal b = new BigDecimal((Double) value);
			NumberFormat numberFormat = new DecimalFormat("#.######");
			String numValue = numberFormat.format(b.setScale(4,
					BigDecimal.ROUND_HALF_UP).doubleValue());
			return numValue;
		}
		return value.toString();
	}

	/**
	 * 查询生成报文所需要的数据
	 * 
	 * @param query
	 * @param queryValues
	 * @param description
	 * @return
	 */
	private List queryData(Company company, Element query,
			Map<String, String> queryValues, String description) {
		Element entityClassElement = query.getChild("entity-class");
		String hsql = "";
		List<Object> parameters = new ArrayList<Object>();
		if (entityClassElement == null
				|| "".equals(entityClassElement.getTextTrim())) {
			throw new RuntimeException(description + "没有查询的实体类");
		}
		hsql += " select a from " + entityClassElement.getTextTrim()
				+ " as a where 2>1 ";
		Element queryFields = query.getChild("query-fields");
		if (queryFields != null) {
			List<Element> listQueryField = queryFields
					.getChildren("query-field");
			for (Element queryField : listQueryField) {
				String fieldName = queryField.getTextTrim();
				if (fieldName == null || "".equals(fieldName)) {
					throw new RuntimeException(description + "设置的查询参数中字段名有空值存在");
				}
				String queryValue = queryValues.get(fieldName);
				if (queryValue == null || "".equals(queryValue)) {
					throw new RuntimeException(description + "设置的查询参数中字"
							+ fieldName + "的查询值存在");
				}
				hsql += " and a." + fieldName + "=? ";
				parameters.add(queryValue);
			}
		} else {
			throw new RuntimeException(description + "没有设置查询的参数");
		}
		hsql += " and a.company.id=? ";
		parameters.add(company.getId());
		return this.blsMessageDao.findNoCache(hsql, parameters.toArray());
	}

	/**
	 * 为下一级查询获取所需要的参数
	 * 
	 * @param entity
	 * @param element
	 * @param description
	 * @return
	 */
	private Map<String, String> getNextLayerQueryValues(Object entity,
			Element element, String description) {
		Element nextLayerQueryValue = element
				.getChild("next-layer-query-values");
		if (nextLayerQueryValue != null) {
			List<Element> listQueryValue = nextLayerQueryValue
					.getChildren("query-value");
			Map<String, String> mapValues = new HashMap<String, String>();
			for (Element queryVlaue : listQueryValue) {
				String nextLayerFieldName = queryVlaue
						.getAttributeValue("next-layer-field-name");
				if (nextLayerFieldName == null || "".equals(nextLayerFieldName)) {
					throw new RuntimeException(description
							+ "设置的参数值获取中字段名1有空值存在");
				}
				String valueType = queryVlaue.getAttributeValue("value-type");
				if ("FIELD_VALUE".equals(valueType)) {
					String fieldName = queryVlaue.getTextTrim();
					if (fieldName == null || "".equals(fieldName)) {
						throw new RuntimeException(description
								+ "设置的参数值获取中字段名2有空值存在");
					}
					Object oValue = null;
					try {
						// 由字段名得到值
						oValue = PropertyUtils.getNestedProperty(entity,
								fieldName.trim()); // 由字段名得到值
					} catch (Exception ex) {
						// System.out.println("[生成报文信息] 未能获取字段:"
						// + fieldName + ",描述：" + fielddesc
						// + ", 使用缺省值null。");
						// System.out.println(ex.getMessage());
						oValue = null;
					}
					mapValues.put(nextLayerFieldName.trim(),
							oValue == null ? null : oValue.toString().trim());
				} else if ("CONSTANT".equals(valueType)) {
					String fieldValue = queryVlaue.getTextTrim();
					mapValues.put(nextLayerFieldName.trim(), fieldValue);
				}
			}
			return mapValues;
		} else {
			return null;
		}
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
	private void analyseSubSection(Company company, StringBuffer fileContent,
			StringBuffer signContent, Map<String, String> queryValues,
			Map<String, List> hmData, int layer, String subFileName,
			String pdesc, ProgressInfo info) {
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
					makeFileContent(company, subsection, fileContent,
							signContent, queryValues, hmData, layer, pdesc,
							info);
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
	private String genFileName(String msgType) {
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
			sb.append("  ");
		}
		return sb.toString();
	}

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
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				getForamtFilePath() + fileName);
		System.out.println("-----------------格式文件：" + getForamtFilePath()
				+ fileName);
		return is;
	}

	/**
	 * 获取报文格式文件的路径
	 * 
	 * @return
	 */
	public String getForamtFilePath() {
		return "com/bestway/bls/message/format/";
	}

	/**
	 * 在硬盘上建立指定的文件
	 * 
	 * @param fileName
	 *            不带路径的文件名
	 * @return 返回带路径的文件全名。
	 */
	private String createFile(String fileName, String fileContent) {
		String messageSendFileName = "";
		try {
			File dirFile = new File(getBlsSendDir());
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			messageSendFileName = getBlsSendDir() + File.separator + fileName;
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(messageSendFileName), "GB2312");
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// saveMessageSendInfo(messageSendFileName);
		// DeclareFileInfo fileInfo = new DeclareFileInfo();
		// fileInfo.setFileName(fileName);
		// fileInfo.setFileSize((new File(messageSendFileName)).length());
		return messageSendFileName;
	}

	/**
	 * 保存报文发送信息
	 * 
	 * @param fileName
	 * @param serviceType
	 * @param keyCode
	 */
	protected void saveMessageSendInfo(Company company,String fileName, String serviceType,
			String keyCode) {
		if (fileName == null || "".equals(fileName)) {
			return;
		}
		BlsMessageSend blsMessageSend = new BlsMessageSend();
		blsMessageSend.setFileName(fileName);
		blsMessageSend.setSendTime(new Date());
		blsMessageSend.setMessageType(serviceType);
		blsMessageSend.setKeyCode(keyCode);
		blsMessageSend.setSendEr(CommonUtils.getAclUser() == null ? ""
				: CommonUtils.getAclUser().getUserName());
		blsMessageSend.setCompany(company);
		this.blsMessageDao.saveBlsMessageSend(blsMessageSend);
	}

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
		File file = new File(getBlsSendDir() + File.separator + messageFileName);
		if (file.exists()) {
			return file;
		}
		return null;
	}

	/**
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	public Map<String, Object> readBlsMessageFileContent(String formatFileName,
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
			// Element elemSectionFlag = root.getChild("section-flag");
			// String sectionFlag = elemSectionFlag.getTextTrim();
			// if (elemSectionFlag != null) {
			// sectionFlag = elemSectionFlag.getTextTrim();
			// }
			// String sectionFlag ="";
			// Element elemDescription = root.getChild("description");
			// String description = "";
			// if (elemDescription != null) {
			// description = elemDescription.getTextTrim();
			// }
			// getMessageTreeInfo(lsTree, root, null);
			// for (int i = 0; i < root.getChildren().size(); i++) {
			// Element section = ((Element) root.getChildren().get(i));
			readBlsMessageFileFormat(root, lsTree, fieldFlagMap, fieldDescMap,
					"", "");
			// }
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
	 * 读取报文格式文件内容
	 * 
	 * @param elemFormat
	 * @param lsTree
	 * @param fieldFlagMap
	 * @param fieldDescMap
	 * @param patentSectionFlag
	 * @param patentDesc
	 */
	private void readBlsMessageFileFormat(Element elemFormat, List lsTree,
			Map<String, List> fieldFlagMap, Map<String, List> fieldDescMap,
			String patentSectionFlag, String patentDesc) {
		if (elemFormat.getName().equals("section")) {
			getMessageTreeInfo(lsTree, elemFormat, patentDesc);
			Element elemSectionFlag = elemFormat.getChild("section-flag");
			String sectionFlag = patentSectionFlag;
			if (elemSectionFlag != null) {
				if (sectionFlag != null && !"".equals(sectionFlag.trim())) {
					sectionFlag = (sectionFlag + "-" + elemSectionFlag
							.getTextTrim());
				} else {
					sectionFlag = (elemSectionFlag.getTextTrim());
				}
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
						readBlsMessageFileFormat(childElement, lsTree,
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
				readBlsMessageFileFormat(subsection, lsTree, fieldFlag,
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
				List lsFiledFlag = fieldFlagMap.get(fieldFlags);
				List lsFieldValue = new ArrayList();
				String[] fieldFlagArray = fieldFlags.split("-");
				List<Element> lsElement = new ArrayList<Element>();
				lsElement.add(root);
				List<Element> lsFieldParent = getMessageField(fieldFlagArray,
						lsElement, 0);
				for (int i = 0; i < lsFieldParent.size(); i++) {
					Element parentElement = (Element) lsFieldParent.get(i);
					String[] values = new String[lsFiledFlag.size()];
					for (int j = 0; j < lsFiledFlag.size(); j++) {
						String fieldFlag = lsFiledFlag.get(j).toString();
						Element fieldElement = parentElement
								.getChild(fieldFlag);
						if (fieldElement != null) {
							values[j] = fieldElement.getTextTrim().trim();
						}
					}
					lsFieldValue.add(values);
				}
				fieldContentMap.put(fieldFlags, lsFieldValue);
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
		if (layer == fieldFlags.length - 1) {
			return lsElement;
		} else {
			int childLayer = layer + 1;
			String fieldFlag = fieldFlags[childLayer];
			List lsChildElement = new ArrayList();
			for (int i = 0; i < lsElement.size(); i++) {
				Element element = (Element) lsElement.get(i);
				lsChildElement.addAll(element.getChildren(fieldFlag.trim()));
			}
			return getMessageField(fieldFlags, lsChildElement, childLayer);
		}
	}

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
	 * 读取报文内容
	 * 
	 * @param formatFileName
	 * @param messageFileName
	 * @return
	 */
	public String readBlsMessageFlatFileContent(String messageFileName) {
		File file = this.getMessageFile(messageFileName);
		if (file == null) {
			return "";
		}
		return readXmlData(file);
	}

	/**
	 * 读取XML文件内容
	 */
	private String readXmlData(File file) {
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"GB2312"));
			StringBuffer sb = new StringBuffer();
			try {
				String line = "";
				int tagcount = 0;// 表示
				Map<String, String> map = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
			} finally {
				br.close();
				in.close();
				return sb.toString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 将结果字符串转换成BlsReceiptResult对象
	 * 
	 * @param resultInfo
	 * @return
	 */
	private BlsReceiptResult convertResultInfoToBlsReceiptResult(
			String messageType, String resultInfo, String relateID,
			Company company) {
		BlsReceiptResult receiptResult = null;
		SAXBuilder sax = new SAXBuilder();
		InputStream formatStream = new ByteArrayInputStream(resultInfo
				.getBytes());
		Document doc;
		try {
			doc = sax.build(formatStream);
			Element root = doc.getRootElement();
			receiptResult = new BlsReceiptResult();
			// Element elementMessageType = root.getChild("MessageType");
			// if (elementMessageType != null) {
			// receiptResult.setMessageType(BlsServiceID
			// .getServiceTypeByID(elementMessageType.getTextTrim()));
			// }
			receiptResult.setMessageType(messageType);
			Element elementMessageCode = root.getChild("MessageCode");
			if (elementMessageCode != null) {
				receiptResult.setMessageCode(elementMessageCode.getTextTrim());
			}
			Element elementKeyCode = root.getChild("KeyCode");
			if (elementKeyCode != null) {
				receiptResult.setKeyCode(elementKeyCode.getTextTrim());
			}
			Element elementDescription = root.getChild("Description");
			if (elementDescription != null) {
				receiptResult.setDescription(elementDescription.getTextTrim());
			}
			Element elementServiceStatus = root.getChild("ServiceStatus");
			if (elementServiceStatus != null) {
				receiptResult.setServiceStatus(elementServiceStatus
						.getTextTrim());
			}
			Element elementServiceHandle = root.getChild("ServiceHandle");
			if (elementServiceHandle != null) {
				receiptResult.setServiceHandle(elementServiceHandle
						.getTextTrim());
			}
			receiptResult.setNoticeDate(new Date());
			receiptResult.setRelateID(relateID);
			receiptResult.setCompany(company);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				formatStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return receiptResult;
	}

	/**
	 * 保存海关回执内容
	 * 
	 * @param resultInfo
	 * @return
	 */
	private BlsReceiptResult saveReceiptResult(String serviceType,
			String resultInfo, String relateID, Company company) {
		BlsReceiptResult receiptResult = this
				.convertResultInfoToBlsReceiptResult(serviceType, resultInfo,
						relateID, company);
		if (receiptResult != null) {
			receiptResult.setCompany(company);
			this.blsMessageDao.saveBlsReceiptResult(receiptResult);
		}
		return receiptResult;
	}

	/**
	 * 生成模拟回执
	 * 
	 * @param serviceType
	 * @param keyCode
	 * @return
	 */
	private String makeDefaultReturnInfoMessage(String serviceType,
			String keyCode) {
		StringBuffer serviceInfo = new StringBuffer();
		serviceInfo.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		serviceInfo.append("<ServiceInterface>");
		serviceInfo.append("	<MessageType>"
				+ BlsServiceType.getServiceIdByType(serviceType)
				+ "</MessageType>");
		serviceInfo.append("	<MessageCode>" + "" + "</MessageCode>");
		// String keyCode = "MnlEpz";
		serviceInfo.append("	<KeyCode>" + keyCode + "</KeyCode>");
		serviceInfo.append("	<Description>" + "申报成功" + "</Description>");
		String serviceStatus = BlsReceiptResultType.SUCCESS;
		if (BlsServiceType.MANIFEST_DECLARE.equals(serviceType)) {
			serviceStatus = String.valueOf(BlsDeliveryResultType.R1);
		} else if (BlsServiceType.COLLATEBIND_DECLARE.equals(serviceType)) {
			serviceStatus = String.valueOf(BlsCollateBindResultType.R1);
		} else if (BlsServiceType.FREIGHTAGE_DECLARE.equals(serviceType)) {
			serviceStatus = String
					.valueOf(BlsEntranceMessageResultType.SUCCESS);
		}
		serviceInfo.append("	<ServiceStatus>" + serviceStatus
				+ "</ServiceStatus>");
		serviceInfo.append("	<ServiceHandle>" + "" + "</ServiceHandle>");
		serviceInfo.append("</ServiceInterface>");
		return serviceInfo.toString();
	}

	/**
	 * 根据系统类型获取报文格式
	 * 
	 * @param type
	 * @return
	 */
	public String getFormatFileNameByType(String type) {
		String formatFileName = "";
		if (BlsServiceType.MANIFEST_DECLARE.equals(type)) {
			formatFileName = "DeliveryFormat.xml";
		} else if (BlsServiceType.FREIGHTAGE_DECLARE.equals(type)) {
			formatFileName = "FreightageDeclareFormat.xml";
		} else if (BlsServiceType.COLLATEBIND_DECLARE.equals(type)) {
			formatFileName = "CollatebindDeclareFormat.xml";
		} else if (BlsServiceType.BILLCANCEL_APPLY.equals(type)) {
			formatFileName = "BillSpecialApplyFormat.xml";
		} else if (BlsServiceType.CARGOREPLACE_APPLY.equals(type)) {
			formatFileName = "BillSpecialApplyFormat.xml";
		} else if (BlsServiceType.PROCESS_APPLY.equals(type)) {
			formatFileName = "BillSpecialApplyFormat.xml";
		} else if (BlsServiceType.TRN_APPLY.equals(type)) {
			formatFileName = "BillSpecialApplyFormat.xml";
		} else if (BlsServiceType.CARGOABANDON_APPLY.equals(type)) {
			formatFileName = "BillSpecialApplyFormat.xml";
		} else if (BlsServiceType.IMPORT_APPLY.equals(type)) {
			formatFileName = "MaterielImportApplyFormat.xml";
		}
		return formatFileName;
	}

	/**
	 * 从海关查询回执状态
	 * 
	 * @param messageType
	 * @param relateID
	 * @return
	 */
	public BlsReceiptResult findBlsReceiptResultFromHG(String messageType,
			String relateID, Company company) {
		BlsReceiptResult lastReceiptResult = this.blsMessageDao
				.findBlsLastNotCompleteReceiptResult(company, messageType,
						relateID);
		if (lastReceiptResult == null) {
			return null;
		}
		StringBuffer serviceQuery = new StringBuffer();
		serviceQuery.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		serviceQuery.append("<ServiceQuery>");
		serviceQuery.append("	<ServiceHandle>"
				+ lastReceiptResult.getServiceHandle() + "</ServiceHandle>");
		serviceQuery.append("</ServiceQuery>");
		// String serviceId = BlsServiceType.getServiceIdByType(messageType);
		String serviceInfo = getBlsServiceInfo(BlsServiceID.GET_STATUS, company);
		String resultInfo = this.hpServiceExec(serviceInfo, serviceQuery
				.toString(), company);
		if(resultInfo==null||"".equals(resultInfo)){
			throw new RuntimeException("从海关系统获取回执状态出错!");
		}
		return convertResultInfoToBlsReceiptResult(messageType, resultInfo,
				relateID, company);
	}

	/**
	 * 报文回执处理
	 * 
	 * @param messageStream
	 * @return CheckInfo
	 */
	public void processMessage(BlsReceiptResult receiptResult) {
		// if (BlsReceiptResultType.SUCCESS.equals(receiptResult
		// .getServiceStatus())
		// || BlsReceiptResultType.FAILURE.equals(receiptResult
		// .getServiceStatus())) {
		this.blsMessageDao.saveBlsReceiptResult(receiptResult);
		// }
	}
}
