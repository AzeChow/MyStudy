package com.bestway.bcus.client.common;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;

/**
 * 只允许在客户端被调用，不能在服务器被调用
 * 
 * @author Administrator
 * 
 */
public class CommonClientBig5GBConverter {
	private static CommonClientBig5GBConverter commonBig5ToGB = null;

	private CustomBaseAction customBaseAction = (CustomBaseAction) CommonVars
			.getApplicationContext().getBean("customBaseAction");

	private char[] big5CharArray = null;

	private char[] gbCharArray = null;

	private char big5Max = Character.MIN_VALUE;

	private char big5Min = Character.MAX_VALUE;

	private char gbMax = Character.MIN_VALUE;

	private char gbMin = Character.MAX_VALUE;

	private CommonClientBig5GBConverter() {
		// List list = CustomBaseList.getInstance().getGbtobigs();
		List list = customBaseAction.findGbtobig("", "");
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

	public static CommonClientBig5GBConverter getInstance() {
		if (commonBig5ToGB == null) {
			commonBig5ToGB = new CommonClientBig5GBConverter();
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
	/**
     * 获得并没有在简繁对照表里繁体字
     *
     * @param sour
     * @return
     */
    public String getNoBig5(String sour) {
        if (sour == null || "".equals(sour)) {
            return "";
        }
        StringBuffer dest = new StringBuffer();
        for (int i = 0; i < sour.length(); i++) {
            char temp = sour.charAt(i);
            if (!Charset.forName("GB2312").newEncoder().canEncode(temp)) {//判断不是GB2312就是繁体字
                int index = ArrayUtils.indexOf(big5CharArray, temp);//判断是否在本地的繁简对照表中存在
                if (index < 0) {//不存在
                    dest.append(temp);
                }
            }
        }
        return dest.toString();
    }
}
