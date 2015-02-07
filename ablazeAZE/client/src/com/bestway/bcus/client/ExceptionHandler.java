/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client;

import javax.swing.JOptionPane;

import com.bestway.client.windows.form.FmMain;
import com.bestway.ui.message.JMessage;

/**
 * @author wp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ExceptionHandler {

	public void handle(Throwable e) {
		e.printStackTrace();
		try {
			String errorMsg = e.getMessage();
			String errorType = e.getClass().getName();
			String localMessage = e.getLocalizedMessage();
			if (errorType.equals("java.lang.ArrayIndexOutOfBoundsException")) {
				if (localMessage.split(">=").length > 0) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					return;
				}
			}// 处理0>=0的异常

			if (errorMsg != null) {
				int index = errorMsg.indexOf("|");
				//
				// 权限 Exception handler
				//
				if (index != -1) {
					errorMsg = errorMsg.substring(index + 1, errorMsg.length());
				} else if (errorMsg.indexOf("Cannot connect to") != -1) {
					errorMsg = "JBCUS远程服务器可能已经关闭."
							+ "\n如果你正在升级或重启服务器,那么可能还没有重启完 \n请稍后!!";
				} else { // 
					errorMsg = getSplitStrByCount(errorMsg, 75);
				}
			} else {// 这时将打印完整的跟踪信息:
				errorMsg = "应用程序异常,错误信息请查看明细!!!";
			}
			JMessage.showMessageDialog(FmMain.getInstance(), errorMsg,
					JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, e);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmMain.getInstance(),
					ex.getMessage(), "信息!!", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * 获得分行字符串
	 * 
	 * @param sourceString
	 *            源字符串
	 * @param rowCount
	 *            每行的个数
	 * @return
	 */
	private String getSplitStrByCount(String sourceString, int rowCount) {
		StringBuffer strBuf = new StringBuffer();
		if (sourceString == null || sourceString.equals("")) {
			return "";
		}
		for (int i = 0; i < sourceString.length(); i++) {
			if (i == rowCount) {
				strBuf.append(sourceString.substring(0, rowCount) + "\n");
				sourceString = sourceString.substring(rowCount, sourceString
						.length());
				i = 0;
			}
		}
		strBuf.append(sourceString);
		return strBuf.toString();
	}

}
