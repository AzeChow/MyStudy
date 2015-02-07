/*
 * Created on 2004-8-5
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.fpt.logic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.util.Assert;

import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.common.CommonUtils;
import com.bestway.common.MessageHttpUtil;
import com.bestway.common.ProgressInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.FptProcessResult;
import com.bestway.common.constant.HttpMsgTransType;
import com.bestway.common.fpt.dao.FptMessageDao;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptMessageSend;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.fpt.entity.FptReceiptResult;
import com.bestway.common.fpt.entity.FptReceiptResultDetail;
import com.bestway.common.message.entity.CspParameterSet;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.common.message.entity.CspReceiptResultDetail;
import com.bestway.common.message.entity.CspSignInfo;
import com.bestway.common.message.entity.TempCspReceiptResultInfo;
import com.bestway.common.message.logic.CspMessageLogic;
import com.bestway.common.message.logic.EncodingDetect;
import com.bestway.sca.qp.action.ScaCardQpClient;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FptMessageLogic extends CspMessageLogic {

	private static final Logger logger = Logger
			.getLogger(FptMessageLogic.class);

	@Override
	public String getForamtFilePath() {
		return "com/bestway/common/fpt/messageformat/";
	}

	@Override
	public String getFormatFileNameByType(String type) {
		// String formatFileName = "";
		// if (FptBusinessType.FPT_APP.equals(type)) {
		// formatFileName = "FptAppOutFormat.xml";
		// } else if (BcsBusinessType.EMS_POR_BILL.equals(type)) {
		// formatFileName = "BcsContractFormat.xml";
		// } else if (BcsBusinessType.CANCEL_AFTER_VERIFICA.equals(type)) {
		// formatFileName = "BcsContractCavFormat.xml";
		// }
		// return formatFileName;
		return "";
	}

	@Override
	protected String genFileName(String msgType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sFileName = msgType + "_" + sdf.format(new Date()) + ".xml";
		return sFileName;
	}

	@Override
	public CspSignInfo getCspPtsSignInfo(ProgressInfo info, String manageObject) {
		// TODO Auto-generated method stub
		CspSignInfo signInfo = super.getCspPtsSignInfo(info, manageObject);
		CspParameterSet dirSet = this.getCspParameterSet();
		if (dirSet == null) {
			throw new RuntimeException("请先设置报文参数！");
		}
		if (dirSet.getIdCard() == null || "".equals(dirSet.getIdCard().trim())) {
			throw new RuntimeException("请先设置卡号！");
		}
		if (dirSet.getPwd() != null && !"".equals(dirSet.getPwd())) {
			String[] cardId = ScaCardQpClient.getInstance(dirSet).getICCardID();
			if (cardId[0] != null && !"".equals(cardId[0].trim())) {
				if (!cardId[0].trim().equals(dirSet.getIdCard().trim())) {
					throw new RuntimeException("设置的卡号与卡的实际号码不一致！实际卡号为："
							+ cardId[0]);
				}
			} else {
				throw new RuntimeException("系统读取卡号出错，" + cardId[1]);
			}
			String[] certNo = ScaCardQpClient.getInstance(dirSet).getCertNo();
			if (certNo[0] != null && !"".equals(certNo[0].trim())) {
				signInfo.setCertSeqNo(certNo[0].trim());
			} else {
				throw new RuntimeException("系统读取证书号出错，" + certNo[1]);
			}
		} else {
			signInfo.setCertSeqNo("5c2077");
		}
		signInfo.setIdCard(dirSet.getIdCard().trim());
		return signInfo;
	}

	/**
	 * 查询出实体{entityName}最大的内部编号+转入转出(针对转厂)
	 * 
	 * @param entityName
	 * @param propertyName
	 * @param prefix
	 * @return
	 */
	public String getNewCopEntNoFptBill(String entityName, String propertyName,
			String flag, String sysType, String inOrout) {
		Company company = (Company) CommonUtils.getCompany();
		String tradeCode = company.getCode();
		if (tradeCode == null || "".equals(tradeCode.trim())) {
			throw new RuntimeException("请在公司资料设定里面先设定好加工单位和经营单位");
		}
		String prefix = tradeCode + flag + sysType + inOrout;// "ENT" +
		String maxCopEntNo = this.getCspMessageDao().findMaxCopEntNo(
				entityName, propertyName, prefix);
		String newCopEntNO = "";
		if ("".equals(maxCopEntNo.trim())) {
			newCopEntNO = prefix + CommonUtils.convertIntToStr(1, 7);
		} else {
			int maxNo = 0;
			String serialNo = maxCopEntNo.substring(prefix.length());
			try {
				maxNo = Integer.parseInt(serialNo);
			} catch (Exception ex) {
				throw new RuntimeException("在获取" + entityName + "最大内部编号出错，"
						+ serialNo + "不是一个有效的整数");
			}
			newCopEntNO = prefix + CommonUtils.convertIntToStr(maxNo + 1, 7);
		}
		return newCopEntNO;
	}

	/**
	 * 报文回执处理
	 * 
	 * @param messageStream
	 * @return CheckInfo
	 */
	public String processMessage(String businessType, String copEmsNo,
			FptProcessMessage process, List<CspReceiptResult> lsReturnFile) {
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
						+ FptProcessResult.getResultDesc(result) + "\n");
			}
			process.processHandling(receiptResult);
		}
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
	 * 由于海关转发回来的回执xml编码是UTF-8 + BOM头，所以做如下特殊处理。
	 */
	@Override
	protected void getNotProcessReturnFile(List lsResult, File fileDir,
			String businessType, String copEmsNo) {
		// TODO Auto-generated method stub
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
			System.out.println("file name:" + files[i].getName());
			// if (files[i].getName().endsWith("result")) {
			// files[i].delete();
			// continue;
			// }
			String fileEncode = EncodingDetect
					.getJavaEncode(files[i].getPath());// 判断回执报文的文件编码格式
			String fileContent = null;
			try {
//				String targetFile = files[i].getAbsolutePath();
//				String storeFile = files[i].getParentFile().getParent()+File.separator+"temporaryFile"+File.separator+files[i].getName();
//				copyFile(targetFile,storeFile,fileEncode);
//				File file = new File(targetFile);
				fileContent = FileUtils.readFileToString(files[i], fileEncode);// 按照文件的编码格式读取文件内容到字符串
				
				fileContent = fileContent.replace("<IN_G_NAME>", "<IN_G_NAME><![CDATA[");
	            fileContent = fileContent.replace("</IN_G_NAME>", "]]></IN_G_NAME>");
	            fileContent = fileContent.replace("<IN_G_MODEL>", "<IN_G_MODEL><![CDATA[");
	            fileContent = fileContent.replace("</IN_G_MODEL>", "]]></IN_G_MODEL>");
	            
	            fileContent = fileContent.replace("<OUT_G_NAME>", "<OUT_G_NAME><![CDATA[");
	            fileContent = fileContent.replace("</OUT_G_NAME>", "]]></OUT_G_NAME>");
	            fileContent = fileContent.replace("<OUT_G_MODEL>", "<OUT_G_MODEL><![CDATA[");
	            fileContent = fileContent.replace("</OUT_G_MODEL>", "]]></OUT_G_MODEL>");
	            
//				file.delete();
//				fileContent = FileUtils.readFileToString(files[i], fileEncode);// 按照文件的编码格式读取文件内容到字符串
	            FileUtils.writeStringToFile(new File("D:\\tt.xml"),fileContent, "UTF-8");
//	            System.out.println(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println(fileContent);
			byte[] b;
			try {
				b = fileContent.getBytes(fileEncode);
				if (b[0] == (byte) 0xEF && b[1] == (byte) 0xBB// 判断文件是否含有BOM头
						&& b[2] == (byte) 0xBF) {
					fileContent = new String(b, 3, b.length - 3, fileEncode); // 去掉BOM头
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// -------------读取报文xml编码格式
//			String regEx = "^encoding=\"(.*)\"$";
//			Pattern pat = Pattern.compile(regEx);
//			Matcher mat = pat.matcher(fileContent);
//			String xmlEncode = "GB2312";
//			boolean rs = mat.find();
//			if (rs) {
//				String temp = (mat.group(1) == null ? "" : mat.group(1)
//						.toString().trim());
//				if (!"".equals(temp)) {
//					xmlEncode = temp;
//				}
//				System.out.println(xmlEncode);
//			}
			//余总 说先指定GBK
			String xmlEncode = "GBK";
			// -------------------
			InputStream messageStream;
			try {
				System.out.println("xmlEncode:" + xmlEncode);
				String msg=new String(fileContent.getBytes(xmlEncode));
				msg=msg.replaceAll("encoding=\"GB2312\"", "encoding=\"GBK\"");
				
				messageStream = new ByteArrayInputStream(
						msg.getBytes(xmlEncode));// 按照报文xml编码格式解析报文
			} catch (Exception e) {
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
				// result.setNoticeDate(new Date(files[i].lastModified()));

				result.setFileName(files[i].getName());
				result.setFile(files[i]);
				result.setIsSelected(true);
				result.setReceiptResultDetailList(this
						.readReceiptResultDetail(root));
				lsResult.add(result);
			} catch (Exception e) {
				// throw new RuntimeException(e.getMessage());
				e.printStackTrace();
				System.out.println(files[i].getName() + "不是一个有效的XML格式文件！"
						+ e.getMessage());
			} finally {
				try {
					messageStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void copyFile(String targetFile,String storeFile,String fileEncode){
		File file = null;
		FileInputStream in = null;
		InputStreamReader fr = null;
		BufferedReader br = null;
		
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		try {
			file = new File(targetFile);
			in = new FileInputStream(file);
			fr = new InputStreamReader(in,fileEncode);
			br = new BufferedReader(fr);
			File fileOut = new File(storeFile);
			if(!fileOut.getParentFile().isDirectory()){
				fileOut.getParentFile().mkdir();
				fileOut.createNewFile();
			}
			out = new FileOutputStream(fileOut);
			osw = new OutputStreamWriter(out,fileEncode);
			
			String line = null;
			while((line = br.readLine())!=null){
				
				line = replaceString(line);
				osw.write(line);
				osw.write(13);
//				osw.write(10);
			}
			osw.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null){
					br.close();
				}
				
				if(osw!=null){
					osw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	
	public String replaceString(String stu){
		String str = stu;
		if(stu.indexOf('>')!=-1&&stu.lastIndexOf('<')!=-1
				&&stu.lastIndexOf('<')>stu.indexOf('>')){
			stu = stu.substring(stu.indexOf('>')+1,stu.lastIndexOf('<'));
			String oldStr = stu;
			String newStr = null;
			
			StringBuffer sb = new StringBuffer();
			char[] c = stu.toCharArray();
			for (int i = 0; i < c.length; i++) {
				switch (c[i]) {
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\'':
					sb.append("&apos;");
					break;
				case '\"':
					sb.append("&quot;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				default:
					sb.append(c[i]);
					break;
				}
			}
			newStr = sb.toString();
			return str.replaceFirst(oldStr, newStr);
		}else{
			return stu;
		}
	}
	

	/**
	 * 获取报文格式
	 * 
	 * @param sysType
	 * @param messageFileName
	 * @return
	 */
	public String getFormatFileNameByType(String sysType, String messageFileName) {
		String formatFileName = "";
		if (FptBusinessType.FPT_APP.equals(sysType)) {
			String inOutFlag = getInOutFlag(FptBusinessType.FPT_APP,
					messageFileName);
			if (FptInOutFlag.IN.equals(inOutFlag)) {
				formatFileName = "FptAppInFormat.xml";
			} else if (FptInOutFlag.OUT.equals(inOutFlag)) {
				formatFileName = "FptAppOutFormat.xml";
			}
		} else if (FptBusinessType.FPT_BILL.equals(sysType)) {
			formatFileName = "FptBillFormat.xml";
		} else if (FptBusinessType.FPT_BILL_BACK.equals(sysType)) {
			formatFileName = "FptBillFormat.xml";
		} else if (FptBusinessType.FPT_CANCEL_BILL.equals(sysType)) {
			formatFileName = "FptCancelBillFormat.xml";
		} else if (FptBusinessType.FPT_DOWN_DATA.equals(sysType)) {
			formatFileName = "FptDownDataFormat.xml";
		}
		return formatFileName;
	}

	/**
	 * 获取申请单/收送货/退货报文的转入转出标志
	 * 
	 * @param messageFileName
	 * @return
	 */
	public String getInOutFlag(String businessType, String messageFileName) {
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
		String inOutFlag = null;
		try {
			doc = sax.build(messageStream);
			Element root = doc.getRootElement();
			if (FptBusinessType.FPT_APP.equals(businessType)) {
				Map<String, String> map = this.getElementChildData(root,
						"FPT_APP_SIGN");
				if (map != null) {
					return map.get("APP_SORT");
				}
			} else if (FptBusinessType.FPT_BILL.equals(businessType)
					|| FptBusinessType.FPT_BILL_BACK.equals(businessType)) {
				Map<String, String> map = this.getElementChildData(root,
						"FPT_BILL_SIGN");
				if (map != null) {
					return map.get("BILL_SORT");
				}
			}
			// Element elemPtsResult = root.getChild("FPT_APP_SIGN");
			// if (elemPtsResult == null) {
			// return inOutFlag;
			// }
			// Element elemAppSort = elemPtsResult.getChild("APP_SORT");
			// if (elemAppSort != null) {
			// inOutFlag = elemAppSort.getTextTrim();
			// }
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
		return inOutFlag;
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
			Element elemPtsResult = root.getChild("FPT_APP_OUT_HEAD");
			if (elemPtsResult != null) {
				Element elemDeclareType = elemPtsResult
						.getChild("DECLARE_TYPE");
				if (elemDeclareType != null) {
					declareType = elemDeclareType.getTextTrim();
				}
			} else {
				elemPtsResult = root.getChild("FPT_APP_IN_HEAD");
				if (elemPtsResult != null) {
					Element elemDeclareType = elemPtsResult
							.getChild("DECLARE_TYPE");
					if (elemDeclareType != null) {
						declareType = elemDeclareType.getTextTrim();
					}
				}
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

	@Override
	protected String getReturnFormatFileName() {
		return "FptResultFormat.xml";
	}

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
			Element elemFptResult = root.getChild("FPT_APP_SIGN");
			if (elemFptResult != null) {
				lsReturnResult = this.getFptAppDefaultResultData(elemFptResult,
						mapData);
				hmData.put("FptResult", lsReturnResult);
				return hmData;
			}
			elemFptResult = root.getChild("FPT_BILL_SIGN");
			if (elemFptResult != null) {
				lsReturnResult = this.getFptBillDefaultResultData(
						elemFptResult, mapData);
				hmData.put("FptResult", lsReturnResult);
				return hmData;
			}
			elemFptResult = root.getChild("FPT_CANCEL_BILL_SIGN");
			if (elemFptResult != null) {
				lsReturnResult = this.getFptCancelDefaultResultData(
						elemFptResult, mapData);
				hmData.put("FptResult", lsReturnResult);
				return hmData;
			}
			elemFptResult = root.getChild("FPT_DOWN_DATA_SIGN");
			if (elemFptResult != null) {
				lsReturnResult = this.getFptDownDataDefaultResultData(
						elemFptResult, mapData);
				hmData.put("FptResult", lsReturnResult);
				if (!lsReturnResult.isEmpty()) {
					FptReceiptResult tempResult = (FptReceiptResult) lsReturnResult
							.get(0);
					Map<String, String> mapDownDataHead = this
							.getElementChildData(root, "FPT_DOWN_DATA_HEAD");
					String downSort = mapDownDataHead.get("DOWN_SORT");
					if ("A".equals(downSort)) {// 申请表下载
						List lsFptHead = new ArrayList();
						FptAppHead head = ((FptMessageDao) this
								.getCspMessageDao()).findFptAppHead(
								tempResult.getCopEmsNo(), FptInOutFlag.OUT,
								DeclareState.PROCESS_EXE);
						if (head == null) {
							throw new RuntimeException("系统没有内部编号是："
									+ tempResult.getSeqNo() + "的生效的转出申请表！");
						}
						lsFptHead.add(head);
						List lsFptBill = ((FptMessageDao) this
								.getCspMessageDao()).findFptAppItem(head
								.getId());
						hmData.put("FptAppHead", lsFptHead);
						hmData.put("FptAppItem", lsFptBill);
					} else if ("B".equals(downSort)) {// 退货单下载
						List lsFptHead = new ArrayList();
						FptBillHead head = ((FptMessageDao) this
								.getCspMessageDao()).findFptBillHead(
								tempResult.getCopEmsNo(), FptInOutFlag.IN,
								DeclareState.PROCESS_EXE);
						if (head == null) {
							throw new RuntimeException("系统没有内部编号是："
									+ tempResult.getSeqNo() + "的生效的退货单据！");
						}
						lsFptHead.add(head);
						List lsFptBill = ((FptMessageDao) this
								.getCspMessageDao()).findFptBillItem(head
								.getId());
						hmData.put("FptBillHead", lsFptHead);
						hmData.put("FptBillItem", lsFptBill);
					} else if ("C".equals(downSort)) {// 收发货单下载
						List lsFptHead = new ArrayList();
						FptBillHead head = ((FptMessageDao) this
								.getCspMessageDao()).findFptBillHead(
								tempResult.getCopEmsNo(), FptInOutFlag.OUT,
								DeclareState.PROCESS_EXE);
						if (head == null) {
							throw new RuntimeException("系统没有内部编号是："
									+ tempResult.getSeqNo() + "的生效的送货单据！");
						}
						lsFptHead.add(head);
						List lsFptBill = ((FptMessageDao) this
								.getCspMessageDao()).findFptBillItem(head
								.getId());
						hmData.put("FptBillHead", lsFptHead);
						hmData.put("FptBillItem", lsFptBill);
					}
				}
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

	private List getFptAppDefaultResultData(Element signElement,
			Map<String, Object> mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		FptReceiptResult tempResult = new FptReceiptResult();
		Map<String, String> map = getElementChildData(signElement);
		// 转入转出标记
		tempResult.setInOutFlag(map.get("APP_SORT"));
		if (FptInOutFlag.OUT.equals(tempResult.getInOutFlag())) {
			if (mapData.get("seqNo") != null
					&& !"".equals(mapData.get("seqNo"))) {
				tempResult.setSeqNo((String) mapData.get("seqNo"));
			} else {
				tempResult.setSeqNo(map.get("SEQ_NO"));
			}
		} else {
			tempResult.setSeqNo(map.get("SEQ_NO"));
		}
		if (mapData.get("customsNo") != null
				&& !"".equals(mapData.get("customsNo"))) {
			tempResult.setCustomsNo((String) mapData.get("customsNo"));
			System.out.println("----------1:" + mapData.get("customsNo"));
		} else {
			Map<String, String> mapTemp = getElementChildData(
					signElement.getParent(), "FPT_APP_OUT_HEAD");
			tempResult.setCustomsNo(mapTemp.get("APP_NO"));
			System.out.println("----------2:" + mapTemp.get("APP_NO"));
		}
		// 状态标志
		// tempResult.setChkMark(FptProcessResult.CHECK_PASS);
		tempResult.setChkMark((String) mapData.get("chkMark"));
		// }
		// 企业内部编号
		if (FptInOutFlag.OUT.equals(tempResult.getInOutFlag())) {
			tempResult.setCopEmsNo(map.get("COP_APP_NO"));
		} else {
			Map<String, String> mapTemp = getElementChildData(
					signElement.getParent(), "FPT_APP_IN_HEAD");
			tempResult.setCopEmsNo(mapTemp.get("COP_APP_NO"));
		}
		// 企业代码
		tempResult.setTradeCode(map.get("TRADE_CODE"));
		// 回执类型
		tempResult.setApplType(FptBusinessType.FPT_APP);
		// 序号
		tempResult.setRetNo("1");
		// 通知时间
		tempResult.setNoticeDate(new Date());
		// 申请表有效期
		Date endDate = (Date) mapData.get("endDate");
		if (endDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			tempResult.setNote(dateFormat.format(endDate));
		}
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}

	private List getFptBillDefaultResultData(Element signElement,
			Map<String, String> mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		FptReceiptResult tempResult = new FptReceiptResult();
		Map<String, String> map = getElementChildData(signElement);
		// 回执类型
		String sysType = map.get("SYS_TYPE");
		if ("0".equals(sysType)) {
			tempResult.setApplType(FptBusinessType.FPT_BILL);
		} else if ("1".equals(sysType)) {
			tempResult.setApplType(FptBusinessType.FPT_BILL_BACK);
		}
		// 转入转出标记
		tempResult.setInOutFlag(map.get("BILL_SORT"));
		if ((FptBusinessType.FPT_BILL.equals(tempResult.getApplType()) && FptInOutFlag.OUT
				.equals(tempResult.getInOutFlag()))
				|| (FptBusinessType.FPT_BILL_BACK.equals(tempResult
						.getApplType()) && FptInOutFlag.IN.equals(tempResult
						.getInOutFlag()))) {
			if (mapData.get("customsNo") != null
					&& !"".equals(mapData.get("customsNo"))) {
				// 收送货单编号
				tempResult.setCustomsNo(mapData.get("customsNo"));
			}
		}
		// 状态标志
		// tempResult.setChkMark(FptProcessResult.CHECK_PASS);
		tempResult.setChkMark(mapData.get("chkMark"));
		// 电子口岸统一编号
		// if (mapData.get("seqNo") != null && !"".equals(mapData.get("seqNo")))
		// {
		tempResult.setSeqNo(mapData.get("seqNo"));
		// }
		// 企业内部编号
		if ("0".equals(sysType)) {
			if (FptInOutFlag.OUT.equals(tempResult.getInOutFlag())) {
				tempResult.setCopEmsNo(map.get("COP_BILL_NO"));
			} else {
				Map<String, String> mapTemp = getElementChildData(
						signElement.getParent(), "FPT_BILL_HEAD");
				tempResult.setCopEmsNo(mapTemp.get("COP_BILL_NO"));
			}
		} else if ("1".equals(sysType)) {
			if (FptInOutFlag.IN.equals(tempResult.getInOutFlag())) {
				tempResult.setCopEmsNo(map.get("COP_BILL_NO"));
			} else {
				Map<String, String> mapTemp = getElementChildData(
						signElement.getParent(), "FPT_BILL_HEAD");
				tempResult.setCopEmsNo(mapTemp.get("COP_BILL_NO"));
			}
		}

		// 企业代码
		tempResult.setTradeCode(((Company) CommonUtils.getCompany()).getCode());
		// 序号
		tempResult.setRetNo("1");
		// 通知时间
		tempResult.setNoticeDate(new Date());
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}

	private List getFptCancelDefaultResultData(Element elemFptResult,
			Map mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		FptReceiptResult tempResult = new FptReceiptResult();
		Map<String, String> map = getElementChildData(elemFptResult);
		// 电子口岸统一编号
		Map<String, String> mapItem = getElementChildData(elemFptResult
				.getParent().getChild("FPT_CANCEL_BILL_HEAD"));
		if (mapItem != null) {
			tempResult.setSeqNo(mapItem.get("SEQ_NO"));
		}
		// 企业内部编号
		tempResult.setCopEmsNo(map.get("OUT_COP_BILL_NO"));
		// 企业代码
		tempResult.setTradeCode(((Company) CommonUtils.getCompany()).getCode());
		// 回执类型
		tempResult.setApplType(FptBusinessType.FPT_CANCEL_BILL);
		// 转入转出标记
		// String appNo = map.get("APP_NO");
		tempResult.setInOutFlag(FptInOutFlag.OUT);
		// 序号
		tempResult.setRetNo("1");
		// 状态标志
		// tempResult.setChkMark(FptProcessResult.CHECK_PASS);
		tempResult.setChkMark((String) mapData.get("chkMark"));
		// 通知时间
		tempResult.setNoticeDate(new Date());
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}

	private List getFptDownDataDefaultResultData(Element elemFptResult,
			Map mapData) {
		List<CspReceiptResult> lsReturnResult = new ArrayList<CspReceiptResult>();
		FptReceiptResult tempResult = new FptReceiptResult();
		Map<String, String> map = getElementChildData(elemFptResult);
		// 电子口岸统一编号
		Map<String, String> mapItem = getElementChildData(elemFptResult
				.getParent().getChild("FPT_DOWN_DATA_HEAD"));
		if (mapItem != null) {
			tempResult.setSeqNo(mapItem.get("SEQ_NO"));
		}
		// 企业内部编号
		tempResult.setCopEmsNo(map.get("OUT_COP_NO"));
		// 企业代码
		tempResult.setTradeCode(((Company) CommonUtils.getCompany()).getCode());
		// 回执类型
		tempResult.setApplType(FptBusinessType.FPT_DOWN_DATA);
		// 转入转出标记
		// String appNo = map.get("APP_NO");
		tempResult.setInOutFlag(FptInOutFlag.OUT);
		// 序号
		tempResult.setRetNo("1");
		// 状态标志
		tempResult.setChkMark(FptProcessResult.CHECK_PASS);
		// 通知时间
		tempResult.setNoticeDate(new Date());
		lsReturnResult.add(tempResult);
		return lsReturnResult;
	}

	private boolean checkPass(String chkMark) {
		if (FptProcessResult.X.equals(chkMark)
				|| FptProcessResult.CHECK_PASS_ALL.equals(chkMark)) {
			return true;
		}
		return false;
	}

	private boolean checkFail(String chkMark) {
		if (FptProcessResult.IMP_CENTDB_FAIL.equals(chkMark)
				|| FptProcessResult.IN_STOCK_FAIL.equals(chkMark)
				|| FptProcessResult.IMP_CENTDB_FAIL.equals(chkMark)
				|| FptProcessResult.CHECK_BACK.equals(chkMark)
				|| FptProcessResult.QP_FAIL.equals(chkMark)) {
			return true;
		}
		return false;
	}

	/**
	 * 此方法作废不用
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
	 * 此方法作废不用
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

	private String getBusinessType(Element rootElement) {
		Element element = rootElement.getChild("FPT_APP_SIGN");
		if (element != null) {
			return FptBusinessType.FPT_APP;
		}
		element = rootElement.getChild("FPT_BILL_SIGN");
		if (element != null) {
			Element sysTypeElement = element.getChild("SYS_TYPE");
			if (sysTypeElement != null) {
				if ("0".equals(sysTypeElement.getTextTrim())) {
					return FptBusinessType.FPT_BILL;
				} else if ("1".equals(sysTypeElement.getTextTrim())) {
					return FptBusinessType.FPT_BILL_BACK;
				}
			}
		}
		element = rootElement.getChild("FPT_CANCEL_BILL_SIGN");
		if (element != null) {
			return FptBusinessType.FPT_CANCEL_BILL;
		}
		element = rootElement.getChild("FPT_DOWN_DATA_SIGN");
		if (element != null) {
			return FptBusinessType.FPT_DOWN_DATA;
		}
		return null;
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
			FptMessageSend fptMessageSend = new FptMessageSend();
			doc = sax.build(inputStreamReader);
			Element root = doc.getRootElement();
			String sysType = getBusinessType(root);
			if (sysType != null) {
				if (FptBusinessType.FPT_APP.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT_APP_SIGN");
					if (map != null) {
						fptMessageSend.setSysType(sysType);
						String inOutFlag = map.get("APP_SORT");
						fptMessageSend.setInOutFlag(inOutFlag);
						if (FptInOutFlag.OUT.equals(inOutFlag)) {
							fptMessageSend.setCopEmsNo(map.get("COP_APP_NO"));
						} else {
							Map<String, String> mapTemp = getElementChildData(
									root, "FPT_APP_IN_HEAD");
							if (mapTemp != null) {
								fptMessageSend.setCopEmsNo(mapTemp
										.get("COP_APP_NO"));
							}
						}

					}
				} else if (FptBusinessType.FPT_BILL.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT_BILL_SIGN");
					if (map != null) {
						fptMessageSend.setSysType(sysType);
						fptMessageSend.setInOutFlag(map.get("BILL_SORT"));
						String inOutFlag = map.get("BILL_SORT");
						if (FptInOutFlag.OUT.equals(inOutFlag)) {
							fptMessageSend.setCopEmsNo(map.get("COP_BILL_NO"));
						} else {
							Map<String, String> mapTemp = getElementChildData(
									root, "FPT_BILL_HEAD");
							if (mapTemp != null) {
								fptMessageSend.setCopEmsNo(mapTemp
										.get("COP_BILL_NO"));
							}
						}

					}
				} else if (FptBusinessType.FPT_BILL_BACK.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT_BILL_SIGN");
					if (map != null) {
						fptMessageSend.setSysType(sysType);
						fptMessageSend.setInOutFlag(map.get("BILL_SORT"));
						String inOutFlag = map.get("BILL_SORT");
						if (FptInOutFlag.IN.equals(inOutFlag)) {
							fptMessageSend.setCopEmsNo(map.get("COP_BILL_NO"));
						} else {
							Map<String, String> mapTemp = getElementChildData(
									root, "FPT_BILL_HEAD");
							if (mapTemp != null) {
								fptMessageSend.setCopEmsNo(mapTemp
										.get("COP_BILL_NO"));
							}
						}

					}

				} else if (FptBusinessType.FPT_CANCEL_BILL.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT_CANCEL_BILL_SIGN");
					if (map != null) {
						fptMessageSend.setCopEmsNo(map.get("OUT_COP_BILL_NO"));
						fptMessageSend.setSysType(sysType);
					}
				} else if (FptBusinessType.FPT_DOWN_DATA.equals(sysType)) {
					Map<String, String> map = getElementChildData(root,
							"FPT_DOWN_DATA_SIGN");
					if (map != null) {
						fptMessageSend.setCopEmsNo(map.get("OUT_COP_NO"));
						fptMessageSend.setSysType(sysType);
					}
				}
			}
			fptMessageSend.setFileName(file.getName());
			fptMessageSend.setSendRecvTime(new Date());
			fptMessageSend.setSendRecvEr(CommonUtils.getRequest().getUser()
					.getUserName());
			this.getCspMessageDao().saveCspMessageSend(fptMessageSend);
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
		FptReceiptResult receiptResult = new FptReceiptResult();
		Map<String, String> map = this.getElementChildData(rootElement,
				"FPT_RESULT");
		if (map == null) {
			return null;
		}
		String retType = map.get("RET_TYPE");
		// 判断业务类型是否一样
		if (businessType != null && !"".equals(businessType)
				&& !businessType.equals(retType)) {
			return null;
		}
		receiptResult.setApplType(retType);
		receiptResult.setTradeCode(map.get("TRADE_CODE"));
		receiptResult.setCustomsNo(map.get("CUSTOMS_NO"));
		String seqNo = map.get("SEQ_NO");
		String inOutFlag = map.get("SORT_FLAG");
		String copNo = map.get("COP_NO");
		// 如果是转入方申请表，用统一编号进行判断过滤
		if ((FptBusinessType.FPT_APP.equals(retType) && FptInOutFlag.IN
				.equals(inOutFlag))
				|| FptBusinessType.FPT_DOWN_DATA.equals(retType)) {
			if (seqNo != null && copEmsNo != null && !"".equals(copEmsNo)
					&& !copEmsNo.equals(seqNo)) {
				return null;
			}
		} else {// 其他类型，则判断内部编号是否一样
			// if (FptBusinessType.FPT_APP.equals(businessType)
			// || FptBusinessType.FPT_BILL.equals(businessType)
			// || FptBusinessType.FPT_BILL_BACK.equals(businessType)) {
			if (copNo != null && copEmsNo != null && !"".equals(copEmsNo)
					&& !copEmsNo.equals(copNo)) {
				return null;
			}
		}

		// }
		receiptResult.setCopEmsNo(copNo);
		receiptResult.setSeqNo(seqNo);
		// if (FptBusinessType.FPT_CANCEL_BILL.equals(businessType)
		// || FptBusinessType.FPT_DOWN_DATA.equals(businessType)) {
		// if (seqNo != null && copEmsNo != null && !"".equals(copEmsNo)
		// && !copEmsNo.equals(seqNo)) {
		// return null;
		// }
		// }
		// receiptResult.setSeqNo(map.get("SEQ_NO"));

		receiptResult.setChkMark(map.get("CHK_STATUS"));
		String noticeDate = map.get("NOTICE_DATE");
		if (!"".equals(noticeDate)) {
			// Date date = null;
			// SimpleDateFormat dateFormat = new SimpleDateFormat(
			// "yyyyMMddHHmmssSS");
			// try {
			// date = dateFormat.parse(noticeDate);
			// } catch (Exception ex) {
			// ex.printStackTrace();
			// }
			// receiptResult.setNoticeDate(CommonUtils
			// .yyyyMMddHHmmSSToDate(noticeDate));
			// receiptResult.setNoticeTime(CommonUtils
			// .yyyyMMddHHmmSSToDate(noticeDate));
			Date date = null;
			String formatStr = "yyyyMMddHHmmss";
			if (noticeDate.length() == 8) {
				formatStr = "yyyyMMdd";
			} else if (noticeDate.length() == 12) {
				formatStr = "yyyyMMddHHmm";
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
			try {
				date = dateFormat.parse(noticeDate);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			receiptResult.setNoticeDate(date);
		}
		receiptResult.setInOutFlag(inOutFlag);
		receiptResult.setRetNo(map.get("RET_NO"));
		receiptResult.setNote(map.get("NOTE"));
		// 抓取下载的单据信息
		if (FptBusinessType.FPT_DOWN_DATA.equals(retType)) {
			Map<String, Object> mapDownData = new HashMap<String, Object>();
			Element elementDownData = rootElement.getChild("FPT_DOWN_DATA");
			if (elementDownData != null) {
				Element elementAppOutHead = elementDownData
						.getChild("FPT_APP_OUT_HEAD");
				if (elementAppOutHead != null) {
					Map mapAppOutHead = this
							.getElementChildData(elementAppOutHead);
					mapDownData.put("FPT_APP_OUT_HEAD", mapAppOutHead);
					if (elementDownData.getChild("FPT_APP_LIST") != null) {
						List<Element> listAppItemElement = elementDownData
								.getChild("FPT_APP_LIST").getChildren(
										"FPT_APP_ITEM");
						if (listAppItemElement != null
								&& !listAppItemElement.isEmpty()) {
							List<Map> listAppItemData = new ArrayList<Map>();
							for (Element elementAppItem : listAppItemElement) {
								listAppItemData.add(this
										.getElementChildData(elementAppItem));
							}
							mapDownData.put("FPT_APP_LIST", listAppItemData);
						}
					}
				}

				Element elementBillHead = elementDownData
						.getChild("FPT_BILL_HEAD");
				if (elementBillHead != null) {
					Map mapBillHead = this.getElementChildData(elementBillHead);
					mapDownData.put("FPT_BILL_HEAD", mapBillHead);
					if (elementDownData.getChild("FPT_BILL_LIST") != null) {
						List<Element> listBillItemElement = elementDownData
								.getChild("FPT_BILL_LIST").getChildren(
										"FPT_BILL_ITEM");
						if (listBillItemElement != null
								&& !listBillItemElement.isEmpty()) {
							List<Map> listBillItemData = new ArrayList<Map>();
							for (Element elementBillItem : listBillItemElement) {
								listBillItemData.add(this
										.getElementChildData(elementBillItem));
							}
							mapDownData.put("FPT_BILL_LIST", listBillItemData);
						}
					}
					if (elementDownData.getChild("FPT_BILL_DICT_LIST") != null) {
						List<Element> listBillDictItemElement = elementDownData
								.getChild("FPT_BILL_DICT_LIST").getChildren(
										"FPT_BILL_DICT_ITEM");
						if (listBillDictItemElement != null
								&& !listBillDictItemElement.isEmpty()) {
							List<Map> listBillDictItemData = new ArrayList<Map>();
							for (Element elementBillItem : listBillDictItemElement) {
								listBillDictItemData.add(this
										.getElementChildData(elementBillItem));
							}
							mapDownData.put("FPT_BILL_DICT_LIST",
									listBillDictItemData);
						}
					}
				}
				receiptResult.setMapDownData(mapDownData);
			}
		}
		return receiptResult;
	}

	@Override
	protected List<CspReceiptResultDetail> readReceiptResultDetail(
			Element rootElement) {
		List<CspReceiptResultDetail> lsResultInfo = new ArrayList<CspReceiptResultDetail>();
		List resultList = rootElement.getChildren("FPT_RESULT_LIST");
		for (int i = 0; i < resultList.size(); i++) {
			Element elemListDetail = (Element) resultList.get(i);
			if (elemListDetail != null) {
				List elemReturnInfos = elemListDetail
						.getChildren("RET_CONTENT");
				if (elemReturnInfos.size() > 0) {
					for (int j = 0; j < elemReturnInfos.size(); j++) {
						Element elemReturnInfo = (Element) elemReturnInfos
								.get(j);
						if (elemReturnInfo != null) {
							CspReceiptResultDetail resultInfo = new FptReceiptResultDetail();
							resultInfo.setResultInfo(elemReturnInfo
									.getTextTrim());
							lsResultInfo.add(resultInfo);
						}
					}
				}else{
					List elemReturnListDetail = elemListDetail
							.getChildren("FPT_RESULT_LIST_DETAIL");
					for(int m=0;m<elemReturnListDetail.size();m++){
						elemReturnInfos = ((Element) elemReturnListDetail
								.get(m)).getChildren("RET_CONTENT");
						for (int n = 0; n < elemReturnInfos.size(); n++) {
							Element elemReturnInfo = (Element) elemReturnInfos
									.get(n);
							if (elemReturnInfo != null) {
								CspReceiptResultDetail resultInfo = new FptReceiptResultDetail();
								resultInfo.setResultInfo(elemReturnInfo
										.getTextTrim());
								lsResultInfo.add(resultInfo);
							}
						}
					}
				}
			}
		}
		return lsResultInfo;
	}

	@Override
	protected String getBusinessTypeDesc(String businessType) {
		return FptBusinessType.getFptBusinessTypeDesc(businessType);
	}

	/**
	 * 下载报文
	 * 
	 * @param projectType
	 *            项目类型
	 * @return
	 */
	public synchronized List httpDownload(String copEmsNo) {
		// 参数
		FptParameterSet p = (FptParameterSet) this.getCspMessageDao()
				.findCspParameterSet();

//		// 系统参数
//		CompanyOther companyOther = this.getCspMessageDao().findCompanyOther();
//
//		Assert.notNull(p, "报文FTP配置参数未设置");

		String downDir = "FPT/Download";

//		Boolean isFtpPASV = true;// 默认为PASV被动模式
//		Integer ftpTimeOut = 300 * 1000;// 默认时间为5分钟

//		if (null != companyOther && null != companyOther.getIsFtpPASV()
//				&& companyOther.getIsFtpPASV() == false) {
//			isFtpPASV = false;
//		}

//		if (null != companyOther && companyOther.getFtpTimeOut() != null &&
//				companyOther.getFtpTimeOut()!=0) {
//			ftpTimeOut = companyOther.getFtpTimeOut() * 1000;
//		}

		return httpDownload(downDir, p.getRecvDir(), copEmsNo);
	}

	/**
	 * 下载报文
	 * 
	 * @param projectType
	 *            项目类型
	 * @param addree
	 *            EDI地址
	 * @param ediuser
	 *            EDI用户名
	 * @param edipassword
	 *            EDI密码
	 * @param downDir
	 *            ftp下载回执目录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized List httpDownload(String downDir, String saveDir,
			String copEmsNo) {
		StringBuilder sb = new StringBuilder();// 日志
//		String replyString = null;

		List downloadFiles = new Vector();
//		FTPClient ftpClient =  FptClientFactory.getFTPClient(addree, 21, ediuser, edipassword,isFtpPASV,CommonUtils.getAclUser().getId());//new FTPClient();
		try {
//					logger.info("---------enterLocalPassiveMode");
//
//			logger.info("切换工作目录：" + downDir + " ...");
//			sb.append("\n" + "切换工作目录：" + downDir + " ...");
//			if (ftpClient.changeWorkingDirectory(downDir)) {
//				replyString = ftpClient.getReplyString();
//				logger.info(replyString);
//				sb.append("\n" + replyString);

				logger.info("列出所有文件：");
				sb.append("\n" + "列出所有文件：");
//				FTPFile[] ftpAllFiles = ftpClient.listFiles();
				List<String> listAllFiles=MessageHttpUtil.listFiles(HttpMsgTransType.FPT,downDir);
				List<String> ftpFiles = new ArrayList();
				for (int i = 0; i < listAllFiles.size(); i++) {
					String ftpFileName = listAllFiles.get(i);
					if (ftpFileName.indexOf(copEmsNo) >= 0) {
						ftpFiles.add(ftpFileName);
					}
				}
//				replyString = ftpClient.getReplyString();
//				logger.info(replyString);
//				sb.append("\n" + replyString);

				logger.info("发现:" + (ftpFiles != null ? ftpFiles.size() : 0)
						+ "个报文需要下载");
				sb.append("\n" + "发现:"
						+ (ftpFiles != null ? ftpFiles.size() : 0) + "个报文需要下载");

//				logger.info("列出所有文件名：");
//				sb.append("\n" + "列出所有文件名：");
//				String[] names = ftpClient.listNames();
//				replyString = ftpClient.getReplyString();
//				logger.info(replyString);
//				sb.append("\n" + replyString);
//
//				logger.info("显示需要下载的所有文件名：");
//				sb.append("\n" + "显示需要下载的所有文件名：");
//				for (int i = 0; i < ftpFiles.size(); i++) {
//					String ftpFileName = ftpFiles.get(i).getName();
//					System.out.println(ftpFileName);
//					sb.append("\n" + ftpFileName);
//				}

//				Thread.currentThread().sleep(3000);

				logger.info("下载所有文件：");
				sb.append("\n" + "下载所有文件：");
				for (int i = 0; i < ftpFiles.size(); i++) {

//					// 不是文件跳过
//					if (!ftpFiles.get(i).isFile()) {
//						continue;
//					}

					String ftpFileName = ftpFiles.get(i);
					MessageHttpUtil.download(HttpMsgTransType.FPT,downDir, ftpFileName, saveDir);
					MessageHttpUtil.delete(HttpMsgTransType.FPT,downDir, ftpFileName);
					logger.info("成功下载" + ftpFileName + "文件。");
					sb.append("\n" + "成功下载" + ftpFileName + "文件。");
//					File file = new File(saveDir + File.separator
//							+ ftpFiles.get(i).getName());
//
//					logger.info("准备在本地建立回执文件：" + file.getName() + " ...");
//					sb.append("\n" + "准备在本地建立回执文件：" + file.getName() + " ...");
//					// 回执文件不存在就下载
//					if (!file.exists()) {
//						if (!file.createNewFile()) {
//							throw new RuntimeException("未能建立新文件："
//									+ file.getName() + "。");
//						}
//					} else {
//						logger.info("本地已经存在回执文件，不再建立回执文件！");
//						sb.append("\n" + "本地已经存在回执文件，不再建立回执文件！");
//					}
//					FileOutputStream fos = new FileOutputStream(file);
//					// 文件下载
//					int num = 1;// 下载次数，下载回执不合要求，请求三次
//					logger.info("开始下载回执文件,每个回执不合要求，请求三次...");
//					sb.append("\n" + "开始下载回执文件,每个回执不合要求，请求三次...");
//
//					try {
//						do {
//							logger.info("第" + num + "次下载请求...");
//							sb.append("\n" + "第" + num + "次下载请求...");
//							if (ftpClient.retrieveFile(ftpFileName, fos)) {
//								replyString = ftpClient.getReplyString();
//								logger.info(replyString);
//								sb.append("\n" + replyString);
//								// 判断下载成功的回执行数是否大于要求行5
//								if (getFileCount(file) < 5) {
//									num++;
//								} else {
//									num = 4;
//									logger.info("已下载回执文件！");
//									sb.append("\n" + "已下载回执文件！");
//									logger.info("删除ftp服务器的回执文件" + ftpFileName
//											+ "...");
//									sb.append("\n" + "删除ftp服务器的回执文件"
//											+ ftpFileName + "...");
//									ftpClient.deleteFile(ftpFileName);
//									replyString = ftpClient.getReplyString();
//									logger.info(replyString);
//									sb.append("\n" + replyString);
//
//									downloadFiles.add(file);
//
//									logger.info("成功下载" + ftpFileName + "文件。");
//									sb.append("\n" + "成功下载" + ftpFileName
//											+ "文件。");
//									break;
//								}
//							} else {
//								logger.info("第" + num + "次请求下载回执文件 失败！");
//								sb.append("\n" + "第" + num + "次请求下载回执文件 失败！");
//								replyString = ftpClient.getReplyString();
//								logger.info(replyString);
//								sb.append("\n" + replyString);
//								num++;
//							}
//
//							if (num == 3) {
//								logger.info("回执下载失败：" + ftpFileName);
//								sb.append("\n" + "回执下载失败：" + ftpFileName);
//							}
//						} while (num < 3);
//					} catch (Exception e) {
//						throw e;
//					} finally {
//						fos.close();
//						if (getFileCount(file) < 5) {
//							file.delete();
//						}
//					}
				}
//			} else {
//				logger.info("未能变更目录到：" + downDir);
//				sb.append("\n" + "未能变更目录到：" + downDir);
//				replyString = ftpClient.getReplyString();
//				logger.info(replyString);
//				sb.append("\n" + replyString);
//				throw new RuntimeException("未能变更目录到：" + downDir);
//			}
			downloadFiles.add(sb);
			return downloadFiles;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 取得文件行数
	 * 
	 * @param file
	 * @return
	 */
	public static int getFileCount(File file) {
		BufferedReader fileReader = null;
		try {
			fileReader = new BufferedReader(new FileReader(file));
			int count = 0;
			while (fileReader.readLine() != null) {
				count++;
			}
			return count;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 上传报文到远程ftp
	 * 
	 * @param projectType
	 */
	public int httpUpload() {
		// 参数
		FptParameterSet p = (FptParameterSet) this.getCspMessageDao()
				.findCspParameterSet();

		// 系统参数
//		CompanyOther companyOther = this.getCspMessageDao().findCompanyOther();

		Assert.notNull(p, "报文FTP配置参数未设置");

//		String tempDir = "FPT/UploadTemp";
		String uploadDir = "FPT/Upload";

//		Boolean isFtpPASV = true;// 默认为PASV被动模式
//		Integer ftpTimeOut = 300 * 1000;// 默认时间为5分钟

//		if (null != companyOther && null != companyOther.getIsFilter()
//				&& companyOther.getIsFilter() == false) {
//			isFtpPASV = false;
//		}

//		if (null != companyOther && companyOther.getFtpTimeOut() != null&&
//				companyOther.getFtpTimeOut() != 0) {
//			ftpTimeOut = companyOther.getFtpTimeOut() * 1000;
//		}

		return httpUpload(uploadDir, p.getSendDir(),
				p.getFinallyDir());

	}

	/**
	 * 
	 * 上传报文
	 * 
	 * @param projectType
	 *            项目类型
	 * @param addree
	 *            EDI地址
	 * @param ediuser
	 *            EDI用户名
	 * @param edipassword
	 *            EDI密码
	 * @param tempDir
	 *            ftp上传临时目录
	 * @param uploadDir
	 *            ftp上传目录
	 * 
	 */
	public static int httpUpload(String uploadDir,
			String localUploadDir, String localFinallyDir) {

		int uploadCount = 0;

//		Assert.notNull(addree, "ftp地址为空！");
//		Assert.notNull(ediuser, "ftp用户为空！");
//		Assert.notNull(edipassword, "ftp密码为空！");

		File path = new File(localUploadDir);
		File[] files = path.listFiles();
//		FTPClient ftpClient = FptClientFactory.getFTPClient(addree, 21, ediuser, edipassword,isFtpPASV,CommonUtils.getAclUser().getId());// new FTPClient();
		try {
//			logger.info("---------enterLocalPassiveMode");
			if (files == null || files.length < 0) {
				logger.info("上传文件为空！，操作被终止。!");
				throw new RuntimeException("上传文件为空！，操作被终止。");
			}

			for (int i = 0; i < files.length; i++) {
				File file =  (File) files[i];
				if(!file.isFile()){
					continue;
				}
				MessageHttpUtil.upload(HttpMsgTransType.FPT,uploadDir, file.getName(), localUploadDir);
				uploadCount++;
//				logger.info("开始切换目录：" + tempDir);
//				ftpClient.changeWorkingDirectory(tempDir);
//				logger.info(ftpClient.getReplyString());
//				logger.info("结束切换目录，当前工作目录" + ftpClient.printWorkingDirectory());
//				file = (File) files[i];
//				InputStream is = new FileInputStream(file);
//				logger.info("开始保存文件" + file.getName());
//				ftpClient.storeFile(file.getName(), is);
//				is.close();
//				logger.info("结束保存文件" + file.getName());
//				if (ftpClient.changeWorkingDirectory("/")) {
//					logger.info(ftpClient.getReplyString());
//					logger.info("切换到父级目录，当前工作目录："
//							+ ftpClient.printWorkingDirectory());
//
//					String src = tempDir + "/" + file.getName();
//					String dest = uploadDir + "/" + file.getName();
//
//					logger.info("把上传文件" + file.getName() + "从临时目录" + tempDir
//							+ "移动到正式上次目录" + uploadDir);
//					if (!ftpClient.rename(src, dest)) {
//
//						logger.info(ftpClient.getReplyString());
//						logger.info("移动上传文件时失败。");
//						throw new RuntimeException("移动上传文件时失败。");
//					}
//
//					logger.info(ftpClient.getReplyString());
//					logger.info("移动上传文件成功。");
//					logger.info("成功上传了文件：" + file.getName() + "。");
//
//					uploadCount++;
//
					// 移动文件 Edit by xxm 2006-11-24 将发送的报文备份到（回执处理完的目录下）
					File destFile = new File(localFinallyDir + File.separator
							+ file.getName());
					logger.info("将发送的报文：" + file.getName() + "备份到（处理完的目录下）"
							+ localFinallyDir);

					moveFile(file, destFile);
					logger.info("备份成功！");
//				} else {
//					logger.info("changeToParentDirectory error 未能切换到父级目录，上传文件");
//					throw new RuntimeException("未能切换到父级目录，上传文件："
//							+ file.getName() + "时失败。");
//				}
			}

//			logger.info(ftpClient.getReplyString());

			return uploadCount;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	// 移动文件
	private static void moveFile(File fSrc, File fDest) {
		try {
			if (fSrc.exists()) {
				if (fDest.exists()) {
					fDest.delete();
				}
				FileUtils.copyFile(fSrc, fDest);
//				fSrc.delete();
				FileUtils.forceDelete(fSrc);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected String getConvertSignInfo(String fileContent, String signContent) {
		// TODO Auto-generated method stub
		int indexBegin = fileContent.indexOf("<SIGN>");
		int indexEnd = fileContent.indexOf("</SIGN>");
		if (indexBegin >= 0 && indexEnd >= 0) {
			signContent = (new StringBuffer(fileContent).replace(indexBegin,
					indexEnd + "</SIGN>".length(), "<SIGN></SIGN>")).toString();
		}
		CspParameterSet dirSet = this.getCspParameterSet();
		if (dirSet.getRemoteHostAddress() != null
				&& !"".equals(dirSet.getRemoteHostAddress().trim())) {
			String[] signData = ScaCardQpClient.getInstance(dirSet)
					.signMsgData(signContent);
			if (signData[0] != null && !"".equals(signData[0].trim())) {
				return signData[0].trim();
			} else {
				throw new RuntimeException("系统加签出错，" + signData[1]);
			}
		} else {
			return "zbsqg1/cYYjRFrt/8FcoUk2uIdAMTdqOYc5zrwe6H2grBmzuUYbHxa/C00I2imS5xDoh7/Jo81GXcHA7ujCrUnilS9M+JV8j/8Xa6Ypqn15b7E1YwvMQ8dghCrSZOzZPM765XHLvd/cZcPF56fT5fHaS8rnWSKSy9sBdmcBK6Iw=";
		}
	}

	/**
	 * 读卡加签测试
	 * 
	 * @param signContent
	 * @return
	 */
	public String testReadCard(String signContent) {
		CspParameterSet dirSet = this.getCspParameterSet();
		if (dirSet.getRemoteHostAddress() != null
				&& !"".equals(dirSet.getRemoteHostAddress().trim())) {
			String[] signData = ScaCardQpClient.getInstance(dirSet)
					.signMsgData(signContent);
			if (signData[0] != null && !"".equals(signData[0].trim())) {
				return signData[0].trim();
			} else {
				throw new RuntimeException("系统加签出错，" + signData[1]);
			}
		}
		return "";
	}
}