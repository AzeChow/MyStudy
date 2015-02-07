package com.bestway.common;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.bestway.bcus.custombase.dao.BaseCodeDao;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;

/**
 * 只允许在服务器端被调用，不能在客户端被调用
 * 
 * @author Administrator
 * 
 */
public class CommonServerBig5GBConverter {
	private static CommonServerBig5GBConverter commonBig5ToGB = null;

	private char[] big5CharArray = null;

	private char[] gbCharArray = null;

	private char big5Max = Character.MIN_VALUE;

	private char big5Min = Character.MAX_VALUE;

	private char gbMax = Character.MIN_VALUE;

	private char gbMin = Character.MAX_VALUE;

	private CommonServerBig5GBConverter() {
		BaseCodeDao baseCodeDao = (BaseCodeDao) CommonUtils
				.getBeanInApplicationContext("baseCodeDao");
		List list = baseCodeDao.findGbtobig("", "");// CustomBaseList.getInstance().getGbtobigs();
		big5CharArray = new char[list.size()];

		gbCharArray = new char[list.size()];

		for (int i = 0; i < list.size(); i++) {
			Gbtobig gb = (Gbtobig) list.get(i);
			big5CharArray[i] = gb.getBigname().charAt(0);
			gbCharArray[i] = gb.getName().charAt(0);
			if (big5CharArray[i] > big5Max) {
				big5Max = big5CharArray[i];
			}
			if (big5CharArray[i] < big5Min) {
				big5Min = big5CharArray[i];
			}

			if (gbCharArray[i] > gbMax) {
				gbMax = gbCharArray[i];
			}
			if (gbCharArray[i] < gbMin) {
				gbMin = gbCharArray[i];
			}
		}
	}

	public static CommonServerBig5GBConverter getInstance() {
		if (commonBig5ToGB == null) {
			commonBig5ToGB = new CommonServerBig5GBConverter();
		}
		return commonBig5ToGB;
	}

	/**
	 * 繁体转换成简体
	 * 
	 * @param sour
	 * @return
	 */
	public String big5ConvertToGB(String sour) {
		if (sour == null) {
			return null;
		}
		StringBuffer dest = new StringBuffer();
		char[] sourCharArray = sour.toCharArray();
		for (int i = 0; i < sourCharArray.length; i++) {
			char temp = sourCharArray[i];
			if (temp >= big5Min && temp <= big5Max) {
				int index = ArrayUtils.indexOf(big5CharArray, temp);
				if (index >= 0) {
					dest.append(gbCharArray[index]);
				} else {
					dest.append(temp);
				}
			} else {
				dest.append(temp);
			}
		}
		return dest.toString();
	}

	/**
	 * 简体转换成繁体
	 * 
	 * @param sour
	 * @return
	 */
	public String gbConvertToBig5(String sour) {
		if (sour == null) {
			return null;
		}
		StringBuffer dest = new StringBuffer();
		char[] sourCharArray = sour.toCharArray();
		for (int i = 0; i < sourCharArray.length; i++) {
			char temp = sourCharArray[i];
			if (temp >= gbMin && temp <= gbMax) {
				int index = ArrayUtils.indexOf(gbCharArray, temp);
				if (index >= 0) {
					dest.append(big5CharArray[index]);
				} else {
					dest.append(temp);
				}
			} else {
				dest.append(temp);
			}
		}
		return dest.toString();
	}

}
