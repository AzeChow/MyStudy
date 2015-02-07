/*
 * Created on 2004-8-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxm // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Receipt {
	private List lines;
	private List<String> imgs;
	ResultLine resultLine;
	String sCheckInfo;
	String billseqNo;// 清单中数据中心统一编号lxr

	public Receipt(List<String> messageLines) {
		resultLine = null;
		sCheckInfo = "";

		this.lines = messageLines;

		for (int i = 0; i < messageLines.size(); i++) {
			String sLine = messageLines.get(i);
			String sElement = sLine.substring(0, 22);
			if (sElement.equals("<EMS_EDI_RESULT      >")) {
				resultLine = new ResultLine(sLine);
				continue;
			}
			if (sElement.equals("<EMS_EDI_CHK_INFO    >")) {
				if (getMessageType().equals("EMS317")) {
					billseqNo = new String(sLine.getBytes(), 0 + 22 + 37, 18)
							.trim();
					System.out.println(billseqNo);
				}
				if (sCheckInfo.equals("")) {
					sCheckInfo = new String(sLine.getBytes(), 22, sLine
							.getBytes().length - 22);
				} else {
					sCheckInfo += " \n"
							+ new String(sLine.getBytes(), 22,
									sLine.getBytes().length - 22);
				}
				continue;

			}
			
			if(sElement.equals("<EMS_EDI_DCR_IMG     >")){
				if(imgs==null){
					imgs = new ArrayList<String>();
				}
				imgs.add(new String(sLine.getBytes(), 22, sLine
						.getBytes().length - 22));
			}
		}
	}

	public List getImgs(){
		return this.imgs;
	}
	
	public String getMessageType() {
		String sTemp = (String) lines.get(0);
		return new String(sTemp.getBytes(), 0, 6);
	}

	/*
	 * public String getCopEmsNo(){ String sTemp = (String) lines.get(3); return
	 * substring(sTemp, 32, sTemp.getBytes().length); }
	 */

	private String substring(String str, int start, int end) {
		if (str == null)
			return null;
		if (str.getBytes().length >= end) {
			return new String(str.getBytes(), start, end - start);
		} else {
			return new String(str.getBytes(), start, str.getBytes().length
					- start);
		}
	}

	/**
	 * @return Returns the resultLine.
	 */
	public ResultLine getResultLine() {
		return resultLine;
	}

	/**
	 * @return Returns the sCheckInfo.
	 */
	public String getCheckInfo() {
		return sCheckInfo;
	}

	/**
	 * @return Returns the lines.
	 */
	public List getLines() {
		return lines;
	}

	public String getBillseqNo() {
		return billseqNo;
	}

}
