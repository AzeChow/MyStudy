package com.bestway.client.custom.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bestway.bcus.message.entity.Message;

/**
 * 报关单ＥＤＩ报文解析
 * 
 * @author Administrator
 * 
 */
public class ParseEdiCustomsDeclaration {
	private List<Message> messageHead = new ArrayList<Message>(); // @jve:decl-index=0:
	private List<Message> customsDeclarationHead = new ArrayList<Message>(); // @jve:decl-index=0:
	private List<Message> customsDeclarationBody = new ArrayList<Message>(); // @jve:decl-index=0:
	private List<Message> freeText = new ArrayList<Message>(); // @jve:decl-index=0:
	// private List<Message>
	private List<List> customsDeclarationBodys = new ArrayList<List>();
	private static String content = "";
	/**
	 * 集装箱号信息2010-09-14 hcl add
	 */
	private String containerHead="";
	private String containerText="";
	private static ParseEdiCustomsDeclaration parse = null;

	private ParseEdiCustomsDeclaration() {
		this.content = content;

		/**
		 * 报文头
		 */
		messageHead = new ArrayList<Message>();
		messageHead.add(new Message("报文类型","MESSAGE_TYPE", 6));
		messageHead.add(new Message("发送方代码","SENDER_ID", 18));
		messageHead.add(new Message("接收方代码", "RECEIVER_ID",18));
		messageHead.add(new Message("发送日期", "SEND_DATE",8));
		messageHead.add(new Message("发送时间","SEND_TIME", 4));
		messageHead.add(new Message("报文版本","VERSION", 3));
		messageHead.add(new Message("报文参考号","MESSREF_NO", 14));

		/**
		 * 报关单单头
		 */
		customsDeclarationHead = new ArrayList<Message>();
		customsDeclarationHead.add(new Message("预申报编号","PRE_ENTRY_ID", 19));
		customsDeclarationHead.add(new Message("海关编号","", 15));
		customsDeclarationHead.add(new Message("运输方式代码", "TRAF_MODE",1));
		customsDeclarationHead.add(new Message("进出口岸", "I_E_PORT",4));
		customsDeclarationHead.add(new Message("指运港","DISTINATE_", 4));
		customsDeclarationHead.add(new Message("运输工具代码及名称", "TRAF_NAME",26));
		customsDeclarationHead.add(new Message("经营单位编号","TRADE_CO", 10));
		customsDeclarationHead.add(new Message("经营单位名称","TRADE_NAME", 30));
		customsDeclarationHead.add(new Message("企业性质","CO_OWNER", 1));
		customsDeclarationHead.add(new Message("货主单位地区代码","DISTRICT_C", 5));
		customsDeclarationHead.add(new Message("收货单位编码","", 10));
		customsDeclarationHead.add(new Message("货主单位名称","OWNER_NAME", 30));
		customsDeclarationHead.add(new Message("申报单位编码","", 10));
		customsDeclarationHead.add(new Message("申报单位名称","AGENT_NAME", 30));
		customsDeclarationHead.add(new Message("合同号", "CONTR_NO",20));
		customsDeclarationHead.add(new Message("内销比率","IN_RATIO", 5));
		customsDeclarationHead.add(new Message("提单或运单号","BILL_NO", 20));
		customsDeclarationHead.add(new Message("贸易国别","TRADE_COUN", 3));
		customsDeclarationHead.add(new Message("贸易方式","TRADE_MODE", 4));
		customsDeclarationHead.add(new Message("征免性质分类","CUT_MODE", 3));
		customsDeclarationHead.add(new Message("纳税方式","PAY_MODE", 1));
		customsDeclarationHead.add(new Message("成交方式","TRANS_MODE", 1));
		customsDeclarationHead.add(new Message("外汇来源","EX_SOURCE", 2));
		customsDeclarationHead.add(new Message("收结汇方式","PAY_WAY", 1));
		customsDeclarationHead.add(new Message("运费标记","FEE_MARK", 1));
		customsDeclarationHead.add(new Message("杂费标记", "OTHER_MARK",1));
		customsDeclarationHead.add(new Message("保险费标记","INSUR_MARK", 1));
		customsDeclarationHead.add(new Message("运费币制","FEE_CURR", 3));
		customsDeclarationHead.add(new Message("运费/率","FEE_RATE", 10));
		customsDeclarationHead.add(new Message("杂费币制","OTHER_CURR", 3));
		customsDeclarationHead.add(new Message("杂费/率","OTHER_RATE", 10));
		customsDeclarationHead.add(new Message("保险费币制","INSUR_CURR", 3));
		customsDeclarationHead.add(new Message("保险费/率","INSUR_RATE", 10));
		customsDeclarationHead.add(new Message("件数","PACK_NO", 10));
		customsDeclarationHead.add(new Message("毛重", "GROSS_WT",10));
		customsDeclarationHead.add(new Message("净重", "NET_WT",10));
		customsDeclarationHead.add(new Message("许可证编号", "LICENSE_NO",20));
		customsDeclarationHead.add(new Message("批准文号", "APPR_NO",20));
		customsDeclarationHead.add(new Message("备案号","MANUAL_NO", 12));
		customsDeclarationHead.add(new Message("进出日期", "I_E_DATE",10));
		customsDeclarationHead.add(new Message("录入员编号","TYPE_ER", 5));
		customsDeclarationHead.add(new Message("包装种类","WRAP_TYPE", 6));
		customsDeclarationHead.add(new Message("随附单据","CERT_MARK", 7));
		customsDeclarationHead.add(new Message("备注", "NOTE_S",70));
		customsDeclarationHead.add(new Message("商品项数","ITEMS_NO", 5));
		customsDeclarationHead.add(new Message("申报日期","D_DATE", 10));
		customsDeclarationHead.add(new Message("缴款单位标识", "GIVETAX",1));

		/**
		 * 报关单单体
		 */
		customsDeclarationBody.add(new Message("商品序号", 5));
		customsDeclarationBody.add(new Message("附加编号", 8));
		customsDeclarationBody.add(new Message("商品编号", 8));
		customsDeclarationBody.add(new Message("归类标志", 1));
		customsDeclarationBody.add(new Message("商品名称", 30));
		customsDeclarationBody.add(new Message("商品规格、型号", 30));
		customsDeclarationBody.add(new Message("产销国", 3));
		customsDeclarationBody.add(new Message("手册商品项目序号", 5));
		customsDeclarationBody.add(new Message("数量", 19));
		customsDeclarationBody.add(new Message("申报计量单位", 3));
		customsDeclarationBody.add(new Message("申报计量单位与法定单位比例因子",

		11));
		customsDeclarationBody.add(new Message("申报数量", 19));
		customsDeclarationBody.add(new Message("第二数量", 19));
		customsDeclarationBody.add(new Message("第二计量单位", 3));
		customsDeclarationBody.add(new Message("成交币制", 3));
		customsDeclarationBody.add(new Message("申报单价", 19));
		customsDeclarationBody.add(new Message("成交总价", 19));
		customsDeclarationBody.add(new Message("征减免税方式", 1));
		customsDeclarationBody.add(new Message("用途", 2));
		customsDeclarationBody.add(new Message("第一计量单位", 3));
		customsDeclarationBody.add(new Message("货号", 30));
		customsDeclarationBody.add(new Message("版本", 10));

		/**
		 * 自有文本中文描述
		 */

	}

	public static ParseEdiCustomsDeclaration getInstance(String content) {
		if (parse == null) {
			parse = new ParseEdiCustomsDeclaration();
		} else {
			parse.customsDeclarationBodys.clear();
		}
		ParseEdiCustomsDeclaration.content = content;
		return parse;
	}

	/**
	 * 解析报文
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void parseMessage() throws UnsupportedEncodingException {
		/**
		 * 开始解析 content
		 */
		Hashtable<String, String> l = new Hashtable<String, String>();
		l.put("RELATIVE_ID", "18位关联报关单号");
		l.put("RELATIVE_MANUAL_NO", "12位关联手册号");
		l.put("HPH2KENTRYID", "18位H2000报关单号");
		l.put("HP_NO_PAPER_FLAG", "1位标志");
		l.put("HP_NOTE_S", "255位note_s");
		l.put("HP_BONDEDNO", "32位保税仓库");
		l.put("HPCUSFIE", "装卸码头（2）");
		l.put("HP_TRAF", "50位运输工具名称");
		l.put("HP_VOYAGENO", "32位航次号");
		l.put("FORM_CERTIFICATE", "预录入号+ 一位随附单证类型代码 + 32位随附单证号");
		l.put("DECRNAME", "报关员姓名（10）");
		l.put("DECRTEL", "报关员电话（15）");
		l.put("OWNERNAME", "企业负责人（10）");
		l.put("ENTTEL", "企业联系电话（15）");
		l.put("OTRAFCODE", "境外运输工具编码（15）");
		l.put("OTRAFNAME", "境外运输工具名称（30）");
		l.put("OTRAFVOYNO", "境外运输工具航次（12）");
		l.put("OTRAFBILNO", "境外运输工具提单号（20）");
		l.put("ITRAFMODE", "境内运输方式（1）");
		l.put("ITRAFCODE", "境内运输工具编码（13）");
		l.put("ITRAFNAME", "境内运输工具名称（30）");
		l.put("ITRAFVOYNO", "境内运输工具航次（12）");
		l.put("HPCUSFIE", "装卸码头（2）");
		l.put("TRACTYPE", "车辆拖架类型（5）");
		l.put("TRACICNO", "0000000000(10)");
		String[] lines = content.split("\n");
		int messageHeadSize = 0;
		int customsDeclarationHeadSize = 0;
		int customsDeclarationBodySize = 0;
		// 报文头
		for (Message message : messageHead) {
			messageHeadSize = messageHeadSize + message.getLength();
		}

		// 报关单头
		for (Message message : customsDeclarationHead) {
			customsDeclarationHeadSize = customsDeclarationHeadSize
					+ message.getLength();
		}
		System.out.println("customsDeclarationHeadSize:" +

		customsDeclarationHeadSize);

		// 报关单表体
		for (Message message : customsDeclarationBody) {
			customsDeclarationBodySize = customsDeclarationBodySize
					+ message.getLength();
		}
		System.out.println("customsDeclarationBodySize:" +

		customsDeclarationBodySize);
		for (int i = 0; i < lines.length; i++) {
			// int i=0;
			String line = lines[i];
			//报关单、报关单表体要编码格式“GBK”转成“ISO8859-1”进行长度判断
			line=new String(line.getBytes("GBK"),"ISO8859-1");
			int lineLength = line.getBytes("ISO8859-1").length;
			System.out.println("linelength="+lineLength);
			if (line.startsWith("<")) {
				line = lines[i];//是自由文本的话不用转换编码
				Pattern pattern = Pattern.compile("<(.*?)>(.*?)<");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find()) {
					// for(int i=0;i<lines.length)
					// {}
					Message message = new Message(matcher.group(1) + ":  "
							+ l.get(matcher.group(1)), matcher.group(1)
							.length());
					message.setValue(matcher.group(2).trim());
					freeText.add(message);
					String sss = l.get(matcher.group(1));
					System.out.println(sss);
				}
			} else if (lineLength == messageHeadSize) {
				int pos = 0;
				for (Message message : messageHead) {
					String newLine = new String(line.getBytes(),
					"ISO8859-1");
					message.setValue(new String(newLine.substring(pos,
							message.getLength() + pos).getBytes
					("ISO8859-1"), "GBK").trim());
					pos = pos + message.getLength();
				}
			} else if (lineLength == customsDeclarationHeadSize) {
				int pos = 0;
				System.out.println("is 报关单头："+line);
				for (Message message : customsDeclarationHead) {
					String newLine = new String(line.getBytes(),
					"ISO8859-1");
					byte[] lineByte=line.getBytes("ISO8859-1");
					
					message.setValue(new String(lineByte,pos,
							message.getLength(),"GBK").trim());
					pos = pos + message.getLength();
				}
			} else if (lineLength == customsDeclarationBodySize) {
				System.out.println("is 报关单体："+line);
				int pos = 0;
				List msgs = new ArrayList();
				for (Message message : customsDeclarationBody) {
					Message msg = new Message(message.getTitle(),
					message.getLength());
					String newLine = new String(line.getBytes(),
					"ISO8859-1");
					byte[] lineByte=line.getBytes("ISO8859-1");
					
					msg.setValue(new String(lineByte,pos,
							message.getLength(),"GBK").trim());
					pos = pos + message.getLength();
					msgs.add(msg);
				}
				customsDeclarationBodys.add(msgs);
			//处理集装箱号信息
			}else if(line.startsWith("CONTAINER")||lineLength==12){
				if(lineLength!=12){
					containerHead=line.replace("CONTAINER", "");
				}else{
					containerText+=(line+"\n");
				}
			}

		}
	}

	public List<Message> getMessageHead() {
		return messageHead;
	}

	public void setMessageHead(List<Message> messageHead) {
		this.messageHead = messageHead;
	}

	public List<Message> getCustomsDeclarationHead() {
		return customsDeclarationHead;
	}

	public void setCustomsDeclarationHead(List<Message> customsDeclarationHead) {
		this.customsDeclarationHead = customsDeclarationHead;
	}

	public List<Message> getCustomsDeclarationBody() {
		return customsDeclarationBody;
	}

	public void setCustomsDeclarationBody(List<Message> customsDeclarationBody) {
		this.customsDeclarationBody = customsDeclarationBody;
	}

	public List<Message> getFreeText() {
		return freeText;
	}

	public void setFreeText(List<Message> freeText) {
		this.freeText = freeText;
	}

	public List<List> getCustomsDeclarationBodys() {
		return customsDeclarationBodys;
	}

	public void setCustomsDeclarationBodys(List<List> customsDeclarationBodys) {
		this.customsDeclarationBodys = customsDeclarationBodys;
	}

	public String getContainerHead() {
		return containerHead;
	}

	public void setContainerHead(String containerHead) {
		this.containerHead = containerHead;
	}

	public String getContainerText() {
		return containerText;
	}

	public void setContainerText(String containerText) {
		this.containerText = containerText;
	}

}
