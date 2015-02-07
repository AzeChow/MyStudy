/*
 * Created on 2004-8-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.bestway.bcus.checkcancel.dao.CheckCancelDao;
import com.bestway.bcus.checkcancel.entity.CancelCusHead;
import com.bestway.bcus.checkcancel.entity.CancelHead;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.enc.dao.EncDao;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeBeforeComInfo;
import com.bestway.bcus.manualdeclare.dao.EmsEdiTrDao;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kFas;
import com.bestway.bcus.message.MessageFunctions;
import com.bestway.bcus.message.dao.MessageQueryDao;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.common.CommonUtils;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.RrportDelcareType;

/**
 * 报文生成、处理逻辑 2010-08-03check by hcl
 * 
 * @author xxm
 * 
 */
public class ExportMessageLogic {
	EmsEdiTrDao emsEdiTrDao = null;// 经营范围Dao

	CheckCancelDao checkCancelDao = null;// 中期核销Dao

	EncDao encDao = null; // EncDao

	MessageQueryDao messageQueryDao = null;// 报文信息处理Dao
	private Hashtable gbHash = null;

	/**
	 * 传入List得到繁转简数据
	 * 
	 * @param list
	 */
	private void infTojHsTable(List list) {
		if (gbHash == null) {
			gbHash = new Hashtable();
			for (int i = 0; i < list.size(); i++) {
				Gbtobig gb = (Gbtobig) list.get(i);
				gbHash.put(gb.getBigname(), gb.getName());// 繁转简
				// gbHash.put(gb.getName(), gb.getBigname());//简转繁
			}
		}
	}

	/**
	 * 电子帐册核销--生成报文
	 * 
	 * @param head
	 *            要生成报文的表头
	 * @param declareType
	 *            申报类型
	 * @return 生成的报文的全称（包括路径）
	 */
	public String[] exportCancelCusMessage(Object head, int declareType,
			List ls, HashMap<String, Boolean> parameter) { // 数组长度为2
		// 读取xml格式
		infTojHsTable(ls);
		SAXBuilder sax = new SAXBuilder();
		InputStream[] formatStreams = getFormatFile(head.getClass(),
				declareType); // 得到相应的报文格式
		String[] fileNames = new String[formatStreams.length];
		for (int index = 0; index < formatStreams.length; index++) { // 循环输出报文个数（归并关系有两个）
			InputStream formatStream = formatStreams[index];

			if (formatStream == null) {
				throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
			}
			List lines = new Vector();
			try {
				Document doc = sax.build(formatStream);
				Element root = doc.getRootElement();
				Element message = root;
				String msgType = message.getAttributeValue("type"); //
				for (int i = 0; i < message.getChildren().size(); i++) { // <message
					// version="1.0"
					// type="EMS211">
					// 内的循环
					Element section = ((Element) message.getChildren().get(i));
					if (!section.getName().equals("section")) {
						continue;
					}
					// 获取section-flag, 并得到表名
					Element sectionFlag = section.getChild("section-flag");

					// 得到字段列表
					List fields = section.getChildren();
					for (int j = fields.size() - 1; j >= 0; j--) { // <section
						Element field = ((Element) fields.get(j));
						if (!field.getName().equals("field")) {
							fields.remove(j);
						}
					}
					// ---------
					// 根据表名读取数据(List)

					List datas = getCancelCusData(head, sectionFlag, parameter); // 得到表头，表体数据根据<section-flag>

					// 根据数据循环格式化成字符串， 并写入到List中
					for (int j = 0; j < datas.size(); j++) {
						Object obj = datas.get(j);
						String sLine = sectionFlag.getText();
						for (int k = 0; k < fields.size(); k++) {
							String format = ((Element) fields.get(k)) // 分解<field
									// field/>
									.getChildText("format");
							String field = ((Element) fields.get(k))
									.getChildText("field-name");
							String desc = ((Element) fields.get(k))
									.getChildText("description");
							Object oValue = null;
							try {
								//判断录入员一定要为整型
								/*if(field=="Inputuser"){
									oValue = PropertyUtils.getProperty(obj, field); // 由字段名得到值
									break;
									//Pattern pattern=Pattern.compile("[0-9]{1,}");
								}*/
								oValue = PropertyUtils.getProperty(obj, field); // 由字段名得到值
							} catch (Exception ex) {
								/*
								 * System.out.println("[生成报文信息] 未能获取字段:" + field
								 * + ",描述：" + desc + ", 使用缺省值null。");
								 * System.out.println(ex.getMessage());
								 */
								oValue = null;
							}
							Object oValue1 = specialProcessValue(oValue, field,
									format, obj);// 特殊处理日期
							try {
								String sValue = null;
								if (oValue instanceof Date
										&& format.equals("Z(8)")) {
									sValue = String.valueOf(oValue1);
								} else {
									sValue = MessageFunctions.formatVariant(
											oValue1, format); // 进行格式处理
								}
								// 把List的中字符写入到文件中
								sLine += sValue;
								int length = MessageFunctions
										.GetFormatLength(format);
								/*
								 * System.out.println("FieldName:" + field +
								 * "; " + "Format:" + format + "; " + "Length:"
								 * + Integer.valueOf(length) + "; " +
								 * "OldValue:" + oValue + "; " + "NewValue:" +
								 * sValue);
								 */
							} catch (Exception ex) {
								/*
								 * System.out.println("格式化变量时出错，字段：" + field +
								 * ",值：" + oValue + "格式：" + format);
								 */
								throw ex;
							}
						}
						// 繁转简
						// if (CommonUtils.getIsBigToGBK()!=null &&
						// CommonUtils.getIsBigToGBK().equals("1")){
						sLine = changeStr(sLine);
						// }
						lines.add(sLine); // 输出到报文中
					}
					// ------------------------------------
				}

				String fileName = genFileName(msgType);
				createFile(fileName, lines);
				fileNames[index] = fileName;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} // for(index)
		return fileNames;
	}

	/**
	 * 电子帐册--生成报文
	 * 
	 * @param head
	 *            要生成报文的表头
	 * @param declareType
	 *            申报类型
	 * @return 生成的报文的全称（包括路径）
	 */
	public String[] exportMessage(Object head, int declareType, List ls) { // 数组长度为2
		// 读取xml格式
		infTojHsTable(ls);
		SAXBuilder sax = new SAXBuilder();
		InputStream[] formatStreams = getFormatFile(head.getClass(),
				declareType); // 得到相应的报文格式
		String[] fileNames = new String[formatStreams.length];
		for (int index = 0; index < formatStreams.length; index++) { // 循环输出报文个数（归并关系有两个）
			InputStream formatStream = formatStreams[index];

			if (formatStream == null) {
				throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
			}
			List lines = new Vector();
			try {
				Document doc = sax.build(formatStream);
				Element root = doc.getRootElement();
				Element message = root;
				String msgType = message.getAttributeValue("type"); //
				for (int i = 0; i < message.getChildren().size(); i++) { // <message
					// version="1.0"
					// type="EMS211">
					// 内的循环
					Element section = ((Element) message.getChildren().get(i));
					if (!section.getName().equals("section")) {
						continue;
					}
					// 获取section-flag, 并得到表名
					Element sectionFlag = section.getChild("section-flag");

					// 得到字段列表
					List fields = section.getChildren();
					for (int j = fields.size() - 1; j >= 0; j--) { // <section
						// multiple="false"
						// required="true">内循环
						Element field = ((Element) fields.get(j));
						if (!field.getName().equals("field")) {
							fields.remove(j);
						}
					}
					// ---------
					// 根据表名读取数据(List)
					List datas = getData(head, sectionFlag); // 得到表头，表体数据根据<section-flag>

					// 根据数据循环格式化成字符串， 并写入到List中
					for (int j = 0; j < datas.size(); j++) {
						Object obj = datas.get(j);
						String sLine = sectionFlag.getText();
						for (int k = 0; k < fields.size(); k++) {
							String format = ((Element) fields.get(k)) // 分解<field
									// field/>
									.getChildText("format");
							String field = ((Element) fields.get(k))
									.getChildText("field-name");
							String desc = ((Element) fields.get(k))
									.getChildText("description");
							Object oValue = null;
							try {
								oValue = PropertyUtils.getProperty(obj, field); // 由字段名得到值
							} catch (Exception ex) {
								/*
								 * System.out.println("[生成报文信息] 未能获取字段:" + field
								 * + ",描述：" + desc + ", 使用缺省值null。");
								 * System.out.println(ex.getMessage());
								 */
								oValue = null;
							}
							Object oValue1 = specialProcessValue(oValue, field,
									format, obj);// 特殊处理日期
							try {
								String sValue = null;
								if (oValue instanceof Date
										&& format.equals("Z(8)")) {
									sValue = String.valueOf(oValue1);
								} else {
									sValue = MessageFunctions.formatVariant(
											oValue1, format); // 进行格式处理
								}
								// 把List的中字符写入到文件中
								sLine += sValue;
								int length = MessageFunctions
										.GetFormatLength(format);
								/*
								 * System.out.println("FieldName:" + field +
								 * "; " + "Format:" + format + "; " + "Length:"
								 * + Integer.valueOf(length) + "; " +
								 * "OldValue:" + oValue + "; " + "NewValue:" +
								 * sValue);
								 */
							} catch (Exception ex) {
								/*
								 * System.out.println("格式化变量时出错，字段：" + field +
								 * ",值：" + oValue + "格式：" + format);
								 */
								throw ex;
							}
						}
						// 繁转简
						// if (CommonUtils.getIsBigToGBK()!=null &&
						// CommonUtils.getIsBigToGBK().equals("1")){
						sLine = changeStr(sLine);
						// }
						lines.add(sLine); // 输出到报文中
					}
					// ------------------------------------
				}

				String fileName = genFileName(msgType);
				createFile(fileName, lines);
				fileNames[index] = fileName;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} // for(index)
		return fileNames;
	}

	/**
	 * 输入字符串繁体转简体
	 * 
	 * @param s
	 * @return
	 */
	private String changeStr(String s) {
		String yy = "";
		char[] xxx = s.toCharArray();
		for (int i = 0; i < xxx.length; i++) {
			String z = String.valueOf(xxx[i]);
			if (String.valueOf(xxx[i]).getBytes().length == 2) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}

	/**
	 * 
	 * 特殊处理数据日期
	 * 
	 * @param oValue
	 * @param field
	 * @param format
	 * @param obj
	 * @return
	 */

	private Object specialProcessValue(Object oValue, String field,
			String format, Object obj) {
		if (oValue instanceof Date && format.equals("Z(8)")) {
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSS");
			oValue = sdf.format(oValue);
		}
		return oValue;
	}

	/**
	 *以报文类型+时间， 获取文件名称
	 * 
	 * @param msgType
	 * @return
	 */
	private String genFileName(String msgType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sFileName = msgType + "_" + sdf.format(new Date()) + ".ems";
		return sFileName;
	}

	/*
	 * private String getMsgType(Object head, int declareType) { String msgType
	 * = null; if (head instanceof EmsEdiTrHead) { if (declareType == 1) {
	 * msgType = "EMS211"; } else if (declareType == 2) { msgType = "EMS221"; }
	 * } else if (head instanceof EmsEdiMergerHead) { if (declareType == 1) {
	 * msgType = "EMS212"; } else if (declareType == 2) { msgType = "EMS222"; }
	 * } else if (head instanceof EmsHeadH2k) { if (declareType == 1) { msgType
	 * = "EMS213"; } else if (declareType == 2) { msgType = "EMS223"; } } else
	 * throw new RuntimeException("无法识别的对象类型:" + msgType.getClass().getName());
	 * return msgType; }
	 */

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
	 * 获取归并关系是否分批发送
	 * 
	 * @return
	 */
	private boolean getIsSend() {
		String isSend = emsEdiTrDao.getBpara(BcusParameter.EmsSEND);
		if (isSend != null && "1".equals(isSend)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取电子帐册是否分批发送
	 * 
	 * @return
	 */
	private boolean getIsEmsH2kSend() {
		String isSend = emsEdiTrDao.getBpara(BcusParameter.EmsEdiH2kSend);
		if (isSend != null && "1".equals(isSend)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 按照传入参数判断类型并 得到表头，表体报文数据 （经营范围,归并关系,电子账册,核查,核销,电子分册）
	 * 
	 * @param head
	 * @param sectionFlag
	 * @return
	 */
	private List getCancelCusData(Object head, Element sectionFlag,
			HashMap<String, Boolean> parameter) {

		boolean isCalcImg = (parameter==null||parameter.size() <= 0 ? false : parameter
				.get("isCalcImg"));
		boolean isSelectExg = (parameter==null||parameter.size() <= 0 ? false : parameter
				.get("isSelectExg"));
		boolean isCalcExg = (parameter==null||parameter.size() <= 0 ? false : parameter
				.get("isCalcExg"));
		boolean isSelectImg = (parameter==null||parameter.size() <= 0 ? false : true);//预录入报文不生成报核料件

		if (sectionFlag.getText().equals("<EMS_EDI_DCR_HEAD    >")//电子帐册报核表头
				&& (head instanceof CancelHead)) {
			List result = new Vector();
			result.add(head);
			return result;
		} else if (sectionFlag.getText().equals("<EMS_EDI_DCR_IMG     >")//电子帐册报核料件
				&& isSelectImg) {
			return checkCancelDao.findCancelImgResult((CancelHead) head, false);
		} else if (sectionFlag.getText().equals("<EMS_EDI_DCR_EXG     >")
				&& isSelectExg) {
			return checkCancelDao.findCancelExgResult((CancelHead) head, false);
		} else if (sectionFlag.getText().equals("<EMS_EDI_CLR_IMG     >")//电子帐册核算料件
				&& isCalcImg) {
			return checkCancelDao.findCancelImgBefore((CancelHead) head, false);
		} else if (sectionFlag.getText().equals("<EMS_EDI_CLR_EXG     >")
				&& isCalcExg) {
			return checkCancelDao.findCancelExgBefore((CancelHead) head, false);
		} else if (sectionFlag.getText().equals("<EMS_EDI_DCR_ENT     >")) {//电子帐册报核报关单号
			CancelHead cancel = (CancelHead) head;
			if (RrportDelcareType.BEGINDELCARE.equals(cancel.getDeclareType())) {
				return checkCancelDao.findCancelCustomsDeclara(
						(CancelHead) head, false);
			} else {
				return new Vector();
			}
		}
		return new Vector();
	}

	/**
	 * 
	 * 按照传入参数判断类型并 得到表头，表体报文数据 （经营范围,归并关系,电子账册,核查,核销,电子分册）
	 * 
	 * @param head
	 * @param sectionFlag
	 * @return
	 */
	private List getData(Object head, Element sectionFlag) {
		if (head instanceof EmsEdiTrHead) {
			if (sectionFlag.getText().equals("<EMS_EDI_TR_HEAD     >")) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EMS_EDI_TR_EXG      >")) {
				return emsEdiTrDao.findEmsEdiTrExgToBaowen((EmsEdiTrHead) head);
			} else if (sectionFlag.getText().equals("<EMS_EDI_TR_IMG      >")) {
				return emsEdiTrDao.findEmsEdiTrImgToBaowen((EmsEdiTrHead) head);
			}
		}
		// 归并关系
		else if (head instanceof EmsEdiMergerHead) {
			if (sectionFlag.getText().equals("<EMS_EDI_HEAD_H2K    >")) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EMS_EDI_ORG_EXG     >")) { // 归并前
				if (getIsSend()) {
					return emsEdiTrDao
							.findEmsEdiMergerExgBeforeToBaowenBySendState((EmsEdiMergerHead) head);
				} else {
					return emsEdiTrDao
							.findEmsEdiMergerExgBeforeToBaowen((EmsEdiMergerHead) head);
				}
			} else if (sectionFlag.getText().equals("<EMS_EDI_ORG_IMG     >")) {
				if (getIsSend()) {
					return emsEdiTrDao
							.findEmsEdiMergerImgBeforeToBaowenBySendState((EmsEdiMergerHead) head);
				} else {
					return emsEdiTrDao
							.findEmsEdiMergerImgBeforeToBaowen((EmsEdiMergerHead) head);
				}
			} else if (sectionFlag.getText().equals("<EMS_EDI_ORG_BOM     >")) {
				if (getIsSend()) {
					return emsEdiTrDao
							.findEmsEdiMergerBomToBaowenBySendState((EmsEdiMergerHead) head);
				} else {
					return emsEdiTrDao
							.findEmsEdiMergerBomToBaowen((EmsEdiMergerHead) head);
				}
			} else if (sectionFlag.getText().equals("<EMS_EDI_EXG         >")) { // 归并后
				if (getIsSend()) {
					return emsEdiTrDao
							.findEmsEdiMergerExgAfterToBaowenBySendState((EmsEdiMergerHead) head);
				} else {
					return emsEdiTrDao
							.findEmsEdiMergerExgAfterToBaowen((EmsEdiMergerHead) head);
				}
			} else if (sectionFlag.getText().equals("<EMS_EDI_IMG         >")) {
				if (getIsSend()) {
					return emsEdiTrDao
							.findEmsEdiMergerImgAfterToBaowenBySendState((EmsEdiMergerHead) head);
				} else {
					return emsEdiTrDao
							.findEmsEdiMergerImgAfterToBaowen((EmsEdiMergerHead) head);
				}
			}
		}
		// 电子账册
		else if (head instanceof EmsHeadH2k) {
			if (sectionFlag.getText().equals("<EMS_EDI_HEAD_H2K    >")
					&& (head instanceof EmsHeadH2k)) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EMS_EDI_EXG         >")) {
				if (getIsEmsH2kSend()) {
					return emsEdiTrDao
							.findEmsHeadH2kExgToBaowenBySendState((EmsHeadH2k) head);
				} else {
					return emsEdiTrDao
							.findEmsHeadH2kExgToBaowen((EmsHeadH2k) head);
				}
			} else if (sectionFlag.getText().equals("<EMS_EDI_IMG         >")) {
				if (getIsEmsH2kSend()) {
					return emsEdiTrDao
							.findEmsHeadH2kImgToBaowenBySendSate((EmsHeadH2k) head);
				} else {
					return emsEdiTrDao
							.findEmsHeadH2kImgToBaowen((EmsHeadH2k) head);
				}
			} else if (sectionFlag.getText().equals("<EMS_EDI_CM          >")) {// 电子帐册归并后单损耗
				if (getIsEmsH2kSend()) {
					return emsEdiTrDao
							.findEmsHeadH2kBomToBaowenBySendState((EmsHeadH2k) head);
				} else {
					return emsEdiTrDao
							.findEmsHeadH2kBomToBaowen((EmsHeadH2k) head);
				}
			}// 核查
		} else if (head instanceof CheckHead) {
			if (sectionFlag.getText().equals("<EMS_EDI_COL_HEAD    >")
					&& (head instanceof CheckHead)) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EMS_EDI_COL_IMG     >")) {
				return checkCancelDao.findCheckImg((CheckHead) head);
			} else if (sectionFlag.getText().equals("<EMS_EDI_COL_EXG     >")) {
				return checkCancelDao.findCheckExg((CheckHead) head);
			}// 核销
		} else if (head instanceof CancelHead) {
			if (sectionFlag.getText().equals("<EMS_EDI_DCR_HEAD    >")
					&& (head instanceof CancelHead)) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EMS_EDI_DCR_IMG     >")) {
				return checkCancelDao.findCancelImgResult((CancelHead) head,
						false);
			} else if (sectionFlag.getText().equals("<EMS_EDI_DCR_EXG     >")) {
				return checkCancelDao.findCancelExgResult((CancelHead) head,
						false);
			} else if (sectionFlag.getText().equals("<EMS_EDI_CLR_IMG     >")) {
				return checkCancelDao.findCancelImgBefore((CancelHead) head,
						false);
			} else if (sectionFlag.getText().equals("<EMS_EDI_CLR_EXG     >")) {
				return checkCancelDao.findCancelExgBefore((CancelHead) head,
						false);
			} else if (sectionFlag.getText().equals("<EMS_EDI_DCR_ENT     >")) {
				// edit by cjb 2009.9.17 2009090104 10.9改回
				CancelHead cancel = (CancelHead) head;
				if (RrportDelcareType.BEGINDELCARE.equals(cancel
						.getDeclareType())) {
					// edit by lxr 2011.05.23当为正式报核时不用报报关单
					return checkCancelDao.findCancelCustomsDeclara(
							(CancelHead) head, false);
				} else {
					return new Vector();
				}
				// List list = checkCancelDao.findCancelCustomsDeclara(
				// (CancelHead) head, false);
				// List returnList = new ArrayList();
				// if(list!=null && list.size()>1){
				// returnList.add(list.get(0));
				// return returnList;
				// }
				// return returnList;
			}
		} else // 电子分册
		if (head instanceof EmsHeadH2kFas) {
			if (sectionFlag.getText().equals("<EMS_EDI_FAS_HEAD    >")) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EMS_EDI_FAS_IMG     >")) {
				return emsEdiTrDao
						.findEmsHeadH2kFasImgToBaowen((EmsHeadH2kFas) head);
			} else if (sectionFlag.getText().equals("<EMS_EDI_FAS_EXG     >")) {
				return emsEdiTrDao
						.findEmsHeadH2kFasExgToBaowen((EmsHeadH2kFas) head);
			}
		} else if (head instanceof ApplyToCustomsBillList) { // 清单
			if (sectionFlag.getText().equals("<EDI_CL_HEAD         >")) {
				List result = new Vector();
				result.add(head);
				return result;
			} else if (sectionFlag.getText().equals("<EDI_CL_LIST         >")) {
				ApplyToCustomsBillList hd = (ApplyToCustomsBillList) head;
				String str = hd.getImgExgFlag();
				Integer flag = null;
				try {
					flag = Integer.parseInt(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				List list = encDao
						.findApplyToCustomsBillListToBaowen((ApplyToCustomsBillList) head);
				return sortListByCheckupComplex(list, flag);
			}
		}
		throw new RuntimeException("未能识别的标志：" + sectionFlag.getText());
	}

	/**
	 * 
	 * 按照检查成品的报关编码与类型过滤、排序List
	 * 
	 * @param list
	 * @param flag
	 * @return
	 */
	public List sortListByCheckupComplex(List list, Integer flag) {
		List yesList = new ArrayList();
		List rlist = this.encDao.findCheckupComplexByFlag(flag);
		List noList = new ArrayList();
		List relist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			AtcMergeBeforeComInfo aft = (AtcMergeBeforeComInfo) list.get(i);
			if (aft.getAfterComInfo() == null
					|| aft.getAfterComInfo().getComplex() == null
					|| aft.getAfterComInfo().getComplex().getCode() == null) {
				noList.add(aft);
				continue;
			}
			if (rlist.contains(aft.getAfterComInfo().getComplex().getCode())) {
				yesList.add(aft);
			} else {
				noList.add(aft);
			}
		}
		for (int i = 0; i < yesList.size(); i++) {
			relist.add(yesList.get(i));
		}
		for (int i = 0; i < noList.size(); i++) {
			relist.add(noList.get(i));
		}
		return relist;
	}

	/**
	 * 根据表头的类，获取对应的格式文件 注意：回执处理也可使用该函数
	 * 
	 * @param headClass
	 * @param declareType
	 *            1.申报 2.变更
	 * @return
	 */
	public InputStream[] getFormatFile(Class headClass, int declareType) {
		String fileName = null;
		String fileName2 = null;
		if (declareType == 1) {
			if (headClass.equals(EmsEdiTrHead.class)) {// 经营范围
				fileName = "Msg_EMS211.xml";
			} else if (headClass.equals(EmsEdiMergerHead.class)) {// 归并关系
				fileName = "Msg_EMS212.xml";
				fileName2 = "Msg_EMS215.xml";
			} else if (headClass.equals(EmsHeadH2k.class)) {// 电子帐册
				fileName = "Msg_EMS213.xml";
			} else if (headClass.equals(CheckHead.class)) {// 中期核查
				fileName = "Msg_EMS230.xml";
			} else if (headClass.equals(CancelCusHead.class)) {// 核销报文
				fileName = "Msg_EMS231.xml";
			} else if (headClass.equals(EmsHeadH2kFas.class)) {// 分册
				fileName = "Msg_EMS214.xml";
			} else if (headClass.equals(ApplyToCustomsBillList.class)) {// 清单
				fileName = "Msg_EMS217.xml";
			} else {
				return null;
			}
		} else if (declareType == 2) {
			if (headClass.equals(EmsEdiTrHead.class)) {
				fileName = "Msg_EMS221.xml";
			} else if (headClass.equals(EmsEdiMergerHead.class)) {
				fileName = "Msg_EMS222.xml";
				fileName2 = "Msg_EMS225.xml";
			} else if (headClass.equals(EmsHeadH2k.class)) {
				fileName = "Msg_EMS223.xml";
			} else if (headClass.equals(EmsHeadH2kFas.class)) {
				fileName = "Msg_EMS224.xml";
			} else if (headClass.equals(ApplyToCustomsBillList.class)) {
				fileName = "Msg_EMS217.xml";
			} else {
				return null;
			}
		} else
			throw new RuntimeException("未知的申报类型：" + declareType);

		InputStream[] result = null;
		if (fileName2 != null) { // fileName ,fileName2 为报文格式名称
			result = new InputStream[2];
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream(
							"com/bestway/bcus/message/messageformat/"
									+ fileName2);
			result[1] = is;
		} else {
			result = new InputStream[1];
		}
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(
				"com/bestway/bcus/message/messageformat/" + fileName);
		result[0] = is;

		return result;
	}

	/**
	 * 在硬盘上建立指定的文件
	 * 
	 * @param fileName
	 *            不带路径的文件名
	 * @return 返回带路径的文件全名。
	 */
	public void createFile(String fileName, List lines) {
		/**
		 * 修改
		 */
		// FileWriter writer;
		try {
			// writer = new FileWriter(CommonUtils.getSendDir() + "/" +
			// fileName);
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(CommonUtils.getSendDir() + "/"
							+ fileName), "GBK");
			// BufferedWriter bw = new BufferedWriter(writer);
			BufferedWriter bw = new BufferedWriter(out);
			for (int i = 0; i < lines.size(); i++) {
				bw.write((String) lines.get(i) + "\n");
				// bw.write(new String(((String) lines.get(i) +
				// "\n").getBytes(), "GBK"));
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		/**
		 * 修改
		 */
		/*
		 * FileWriter writer; try { writer = new
		 * FileWriter(CommonUtils.getSendDir() + "/" + fileName);
		 * //OutputStreamWriter out = new OutputStreamWriter( //new
		 * FileOutputStream(CommonUtils.getSendDir() + "/" // + fileName),
		 * "GBK"); BufferedWriter bw = new BufferedWriter(writer);
		 * //BufferedWriter bw = new BufferedWriter(out); for (int i = 0; i <
		 * lines.size(); i++) { bw.write((String) lines.get(i) + "\n");
		 * //bw.write(new String(((String) lines.get(i) + "\n").getBytes(),
		 * "GBK")); } bw.flush(); bw.close(); } catch (Exception e) { throw new
		 * RuntimeException(e); }
		 */

	}

	/**
	 * 获取经营范围Dao
	 * 
	 * @return Returns the emsEdiTrDao.
	 */
	public EmsEdiTrDao getEmsEdiTrDao() {
		return emsEdiTrDao;
	}

	/**
	 * 设置经营范围Dao
	 * 
	 * @param emsEdiTrDao
	 *            The emsEdiTrDao to set.
	 */
	public void setEmsEdiTrDao(EmsEdiTrDao emsEdiTrDao) {
		this.emsEdiTrDao = emsEdiTrDao;
	}

	/**
	 * 获取核销与数据报核Dao
	 * 
	 * @return Returns the checkCancelDao.
	 */
	public CheckCancelDao getCheckCancelDao() {
		return checkCancelDao;
	}

	/**
	 * 设置核销与数据报核Dao
	 * 
	 * @param checkCancelDao
	 *            The checkCancelDao to set.
	 */
	public void setCheckCancelDao(CheckCancelDao checkCancelDao) {
		this.checkCancelDao = checkCancelDao;
	}

	/**
	 * 获取报文消息管理Dao
	 * 
	 * @return Returns the messageQueryDao.
	 */
	public MessageQueryDao getMessageQueryDao() {
		return messageQueryDao;
	}

	/**
	 * 设置报文消息管理Dao
	 * 
	 * @param messageQueryDao
	 *            The messageQueryDao to set.
	 */
	public void setMessageQueryDao(MessageQueryDao messageQueryDao) {
		this.messageQueryDao = messageQueryDao;
	}

	/**
	 * 保存电子帐册申报的报文信息查询
	 * 
	 */
	public void saveMessageQuery(int sendRecvType, int ediType,
			String messageType, String fileName, String copEmsNo,
			String messageCode, int checkResult) {

		Date now = new Date();
		// SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String defaultDate = bartDateFormat.format(now);

		MessageQuery messageQuery = new MessageQuery();
		messageQuery.setSendRecvType(Integer.valueOf(sendRecvType));
		messageQuery.setEdiType(Integer.valueOf(ediType));
		messageQuery.setComputerName(getComputerName());
		// messageQuery.setSendRecvTime(java.sql.Date.valueOf(defaultDate));
		messageQuery.setSendRecvTime(now);
		messageQuery.setMessageType(messageType);
		messageQuery.setFileName(fileName);
		messageQuery.setCopEmsNo(copEmsNo);
		messageQuery.setMessageCode(messageCode);
		messageQuery.setSendRecvEr(CommonUtils.getRequest().getUser()
				.getUserName());
		messageQuery.setCheckResult(Integer.valueOf(checkResult));
		messageQuery.setCompany(CommonUtils.getCompany());
		messageQueryDao.saveMessage(messageQuery);
	}

	/**
	 * 保存报关单报文信息查询
	 * 
	 */
	public void saveCustomsMessageQuery(int sendRecvType, String fileName,
			String copEmsNo, String messageCode, String ylrh, String uniteCode,
			String customs, String returnNote) {
		Date now = new Date();
		// SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String defaultDate = bartDateFormat.format(now);

		MessageQuery messageQuery = new MessageQuery();
		messageQuery.setSendRecvType(Integer.valueOf(sendRecvType)); // 指的是（发送，接收）
		messageQuery.setEdiType(Integer.valueOf(EdiType.CustomsDeclare));// 报关单
		messageQuery.setComputerName(getComputerName()); // 计算机名称
		// messageQuery.setSendRecvTime(java.sql.Date.valueOf(defaultDate));//
		// 接受时间
		messageQuery.setSendRecvTime(now);
		messageQuery.setFileName(fileName);// 回执文件名称
		messageQuery.setCopEmsNo(copEmsNo);// 内部编号（报关单无）
		messageQuery.setMessageCode(messageCode);// 报文代码
		messageQuery.setSendRecvEr(CommonUtils.getRequest().getUser()
				.getUserName());// 发送接受人
		messageQuery.setYlrh(ylrh); // 预录入号
		messageQuery.setUniteCode(uniteCode); // 统一编号
		messageQuery.setCustoms(customs); // 报关单号
		messageQuery.setReturnNote(returnNote); // 回执内容
		// /电子口岸报关单入库回执,H2000报关单入库回执
		messageQuery.setCompany(CommonUtils.getCompany());
		messageQueryDao.saveMessage(messageQuery);
	}

	/**
	 * 根据预录入号查询发送报文信息
	 * 
	 * @return
	 */
	public MessageQuery getMessageQueryByYlrh(String ylrh, String customs) {
		return messageQueryDao.getMessageQueryByYlrh(ylrh, customs);
	}

	/**
	 * 保存报关单报文信息查询
	 * 
	 * @param bigSeqnum
	 *            大单序号
	 */
	public void saveCustomsMessageQuery(int sendRecvType, String fileName,
			String copEmsNo, String messageCode, String ylrh, String uniteCode,
			String customs, String returnNote, String bigSeqnum) {
		Date now = new Date();
		// SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// String defaultDate = bartDateFormat.format(now);

		MessageQuery messageQuery = new MessageQuery();
		messageQuery.setSendRecvType(Integer.valueOf(sendRecvType)); // 指的是（发送，接收）
		messageQuery.setEdiType(Integer.valueOf(EdiType.CustomsDeclare));// 报关单
		messageQuery.setComputerName(getComputerName()); // 计算机名称
		// messageQuery.setSendRecvTime(java.sql.Date.valueOf(defaultDate));//
		// 接受时间
		messageQuery.setSendRecvTime(now);
		messageQuery.setFileName(fileName);// 回执文件名称
		messageQuery.setCopEmsNo(copEmsNo);// 内部编号（报关单无）
		messageQuery.setMessageCode(messageCode);// 报文代码
		messageQuery.setSendRecvEr(CommonUtils.getRequest().getUser()
				.getUserName());// 发送接受人
		messageQuery.setYlrh(ylrh); // 预录入号
		messageQuery.setUniteCode(uniteCode); // 统一编号
		messageQuery.setCustoms(customs); // 报关单号
		messageQuery.setReturnNote(returnNote); // 回执内容
		messageQuery.setBigSeqnum(bigSeqnum);// 大单序号
		// /电子口岸报关单入库回执,H2000报关单入库回执
		messageQuery.setCompany(CommonUtils.getCompany());
		messageQueryDao.saveMessage(messageQuery);
	}

	/**
	 * 得到计算机名和IP地址
	 * 
	 * @return
	 */
	private String getComputerName() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			// System.out.println("localhost: "+localhost.getHostAddress());
			return localhost.getHostName();
		} catch (UnknownHostException uhe) {
			return "";
		}
	}

	/**
	 * 获取encDao
	 * 
	 * @return
	 */
	public EncDao getEncDao() {
		return encDao;
	}

	/**
	 * 设置encDao
	 * 
	 * @param encDao
	 */
	public void setEncDao(EncDao encDao) {
		this.encDao = encDao;
	}
}