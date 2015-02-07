package com.bestway.bcus.message.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import com.bestway.bcus.message.entity.EMSDataType;
import com.bestway.bcus.message.entity.EMSMessageType;

public class EMSMessageUtil {

	/**
	 * 报文内容（行）
	 */
	private List<String> messageLines;
	/**
	 * 报文标签
	 */
	private List<String> tags;
	/**
	 * 报文格式文件
	 */
	private MessageFormat format;

	private EMSMessageUtil() {
		super();
	}

	/**
	 * 获得 i行String
	 * 
	 * @param index
	 * @return
	 */
	public String getLine(Integer index) {
		return messageLines.get(index);
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}
	

	/**
	 * 获得某段数据
	 * @param index
	 * @param begin
	 * @param end
	 * @return
	 */
	public String get(Integer index, Integer begin, Integer end) {
		return messageLines.get(index).substring(begin, end);
	}

	public static EMSMessageUtil create(File file) {
		EMSMessageUtil util = new EMSMessageUtil();
		util.messageLines = new ArrayList<String>();

		try {

			/*
			 * 获取报文内容 转换为string
			 */
			InputStream messageStream;
			messageStream = new FileInputStream(file);
			InputStreamReader fr = new InputStreamReader(messageStream, "GBK");
			LineNumberReader lnr = new LineNumberReader(fr);
			String line = null;
			String tag = null;
			util.tags = new ArrayList<String>();
			while ((line = lnr.readLine()) != null) {
				if (line.trim().equals("")) {
					continue;
				}
				tag = line.substring(0, 22);
				util.tags.add(tag);
				util.messageLines.add(line);
			}
			fr.close();
			lnr.close();

			int declareType = 0;
//			int emsMergerType = 0;
			// 报文类型
			String messageType = new String(util.messageLines.get(0)
					.getBytes(), 0, 6);

			if (!messageType.equals("EMS317")) {// 因为清单中的declareType是B
				declareType = getDeclareType(util.messageLines);
//				emsMergerType = getEmsMergerHeadH2kType(util.messageLines);// 区分归并关系还是帐册
			}

			InputStream formatStream = getFormatFile(messageType, declareType);// 取得各报文的对应的格式
			util.format = new MessageFormat(formatStream);
			if (formatStream == null) {
				throw new RuntimeException("未能获取到对应的报文格式，可能是系统资料不全！");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return util;
	}
	
	
	public static String[] formatData(String data) {
		StringBuilder s = new StringBuilder(data);
		int i = 0;
		while(true) {
			i = s.indexOf("  ");
			if(i == -1) {
				break;
			}
			s.replace(i, i + 2, " ");
		}
		return s.toString().split(" ");
	}

	public static int getDeclareType(List<String> lines) {
		String str = null;
		for (int i = 0; i < lines.size(); i++) {
			if ((lines.get(i)).indexOf(EMSDataType.EMS_EDI_RESULT) > -1) {
				str = lines.get(i);
				String sDeclareType = new String(str.getBytes(), (1 + 22 + 10
						+ 20 + 18 + 12 + 1) - 1, 1);
				try {
					return Integer.parseInt(sDeclareType);
				} catch (Exception ex) {
					throw new RuntimeException("获取回执的申报类型时出错,可能是报文格式不正确");
				}
			}
		}
		throw new RuntimeException("未能发现标记为<EMS_EDI_RESULT      >的行，可能是回执文件不正确");
	}

	// 区分归并关系还是电子帐册
	public static int getEmsMergerHeadH2kType(List<String> lines) {
		String str = null;
		for (int i = 0; i < lines.size(); i++) {
			if ((lines.get(i)).indexOf(EMSDataType.EMS_EDI_RESULT) > -1) {
				str = lines.get(i);
				String sDeclareType = new String(str.getBytes(), (1 + 22 + 10
						+ 20 + 18 + 12 + 1) - 1 - 1, 1);
				try {
					return Integer.parseInt(sDeclareType);
				} catch (Exception ex) {
					throw new RuntimeException(
							"获取回执的申报类型时出错(归并关系与电子帐册区分),可能是报文格式不正确");
				}
			}
		}
		throw new RuntimeException("未能发现标记为<EMS_EDI_RESULT      >的行，可能是回执文件不正确");
	}

	public static InputStream getFormatFile(String sMessageType,
			int declareType) {
		String sFormatFile = null;
		if (declareType == 1) {
			if (sMessageType.equals(EMSMessageType.EMS311)) { // 经营范围
				sFormatFile = "Msg_EMS211.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS313)) { // 电子帐册(归并后部分)
				sFormatFile = "Msg_EMS213.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS314)) { // 电子分册
				sFormatFile = "Msg_EMS214.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS312)) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS212.xml";
			} else if (sMessageType.equals("EMS315")) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS215.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS331)) { // 报核(只有备案,没有变更)
				sFormatFile = "Msg_EMS231.xml";
			}
		} else if (declareType == 2) { // 1为备案, 2为变更
			if (sMessageType.equals(EMSMessageType.EMS321)) { // 经营范围
				sFormatFile = "Msg_EMS221.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS323)) { // 电子帐册(归并后部分)
				sFormatFile = "Msg_EMS223.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS324)) { // 电子分册
				sFormatFile = "Msg_EMS224.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS322)) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS222.xml";
			} else if (sMessageType.equals("EMS325")) { // 电子账册归并关系(归并前部分)
				sFormatFile = "Msg_EMS225.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS331)) { // 报核(只有备案,没有变更)
				sFormatFile = "Msg_EMS231.xml";
			}
		} else if (sMessageType.equals(EMSMessageType.EMS330)
				&& sMessageType.equals(EMSMessageType.EMS331)) {
			if (sMessageType.equals(EMSMessageType.EMS330)) { // 电子账册联网核查(只有备案,没有变更)
				sFormatFile = "Msg_EMS230.xml";
			} else if (sMessageType.equals(EMSMessageType.EMS331)) { // 报核(只有备案,没有变更)
				sFormatFile = "Msg_EMS231.xml";
			}
		} else {
			if (sMessageType.equals("EMS317")) {
				sFormatFile = "Msg_EMS217.xml"; // 取得清单的报文
			}
		}
		if (sFormatFile == null) {
			throw new RuntimeException("未知的申报类型：" + declareType + "及报文类型："
					+ sMessageType);
		}
		InputStream is = EMSMessageUtil.class
				.getClassLoader()
				.getResourceAsStream(
						"com/bestway/bcus/message/messageformat/" + sFormatFile);
		return is;
	}
	
	
	public static void main(String[] args) {
		StringBuilder s = new StringBuilder("dddd            ss");
		int i = 0;
		while(true) {
			i = s.indexOf("  ");
			System.out.println(i + ":" + s);
			if(i == -1) {
				break;
			}
			s.replace(i, i + 2, " ");
		}
		System.out.println(s);
		
		File file = new File("E:\\workspace\\文档\\电子账册报文\\技嘉\\DATA\\download\\DownLoadEms\\EMS323H2K5204CUS52042013022511083145996824685.EMS");
		EMSMessageUtil util = EMSMessageUtil.create(file);
		
		String tag = null;
		for (int j = 0; j < util.messageLines.size(); j++) {
			tag = util.tags.get(j);
			System.out.println(tag);
			
			if(EMSDataType.EMS_EDI_EXG.equals(tag)) {
				System.out.println("seqNum:" + util.get(j, 24, 33));
			}
		}
	}
}
