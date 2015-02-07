/*
 * Created on 2004-8-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.message;

/*
 * Created on 2004-8-3
 * 
 * // Window -
 * Preferences - Java - Code Style - Code Templates
 */
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageFunctions {
	/**
	 * 功能 ： 格式化字符串，可格式化DateTime值 <param> obj : 要格式化的对像 </param> <param> sFormat:
	 * 格式字符 </param> <param> string: 经过格式化过的字串 X 表示字符串，左对齐，右边不足填空格（特殊说明除外）； 9
	 * 表示数字，右对齐，左边不足部分填“0”； Z 表示数字，右对齐，左边不足部分填空格； .
	 * 表示小数点，其前面部分为整数部分，后面部分为小数部分，小数点本身占一位； V
	 * 表示小数点，其前面部分为整数部分，后面部分为小数部分，小数点不占位置； YYYYMMDD,YYYY-MM_DD,HH:MM:SS等表示日期时间格式
	 */

	public static String formatVariant(Object Value, String ValueFmt)
			throws Exception {
		String mFmt;
		String mStr;
		String mStr0;
		String mtempFmt;
		Date dt;
		String result = null;

		mFmt = ValueFmt.toUpperCase();
		if (mFmt.equals("YYYYMMDDHHMMSS")) {
			mFmt = "yyyyMMddHHmmss";
		} else if (mFmt.equals("YYYYMMDD")) {
			mFmt = "yyyyMMdd";
		} else if (mFmt.equals("YYYYMMDDHHMM")) {
			mFmt = "yyyyMMddHHmm";
		}

		while (mFmt.indexOf("(") >= 0) {
			int i = mFmt.indexOf("(");
			int j = mFmt.indexOf(")");
			int no = Integer.parseInt(mFmt.substring(i + 1, j));
			mStr0 = mFmt.substring(i - 1, i);
			mStr = mStr0;
			for (int k = 1; k < no; k++) {
				mStr = mStr + mStr0;
			}
			mFmt = mFmt.substring(0, i - 1) + mStr
					+ mFmt.substring(j + 1, mFmt.length());
		}
		// 右取数据值
		if (mFmt.charAt(0) == 'R') {
			if (Value == null || Value.equals(""))
				Value = "";
			result = rightCut(Value, mFmt.length());
		} else {
			// X(3)XX => XXXXX Z(4)9.9(2) => ZZZZ9.99 Z(3)9(2) => ZZZ99 YYYYMMDD
			// => YYYYMMDD Z(4)9V9(2) => ZZZZ9V99
			mStr = "";
			for (int i = 0; i < 255; i++) {
				mStr = mStr + " ";
			}

			if (mFmt.charAt(0) == 'X') {
				if (Value == null || Value.equals(""))
					Value = " ";
				// result=new
				// String((Value+mStr).getBytes(),0,mFmt.length());//edit by xxm
				// 2005/12/29
				result = cut(Value + mStr, mFmt.length());
			} else if (mFmt.charAt(0) == 'y') {
				dt = (java.util.Date) Value;
				SimpleDateFormat sdf = new SimpleDateFormat(mFmt);
				if (dt == null) { // 填全零
					result = sdf.format(new Date());
					for (int i = 0; i <= 9; i++)
						// result = result.replaceAll(String.valueOf(i),"0");
						result = result.replaceAll(String.valueOf(i), " ");
				} else {
					result = sdf.format(dt);
				}
			} else {
				if (Value == null || Value.equals("")) {
					if (checkFormatIsAllZChar(mFmt)) {
						String temp = "";
						for (int i = 0; i < mFmt.length(); i++) {
							temp += " ";
						}
						return temp;
					}
					Value = "0";
				}
				mtempFmt = mFmt;
				mtempFmt = mtempFmt.replaceAll("Z", "#");
				mtempFmt = mtempFmt.replaceAll("9", "0");
				mtempFmt = mtempFmt.replaceAll("V", ".");
				DecimalFormat df = new DecimalFormat(mtempFmt);
				try{
				result = df.format(Double.parseDouble(Value.toString()));
				}catch (Exception e) {
					throw	new NumberFormatException("数据报核表头录入员应为'数字型,如0123,请修改!'");
				}
				if (mFmt.indexOf("V") >= 0) {
					result = result.replaceAll("\\.", "");
					String sTemp = (mStr + result);
					result = new String(sTemp.getBytes(), sTemp.length()
							- mFmt.length() + 1, mFmt.length() - 1);
				} else {
					String sTemp = (mStr + result);
					result = new String(sTemp.getBytes(), sTemp.length()
							- mFmt.length(), mFmt.length());
				}
			}
		}
		return result;
	}

	private static boolean checkFormatIsAllZChar(String mFmt) {
		boolean result = false;
		if (mFmt != null&&!"".equals(mFmt.trim())) {
			result = true;
			for (int i = 0; i < mFmt.length(); i++) {
				if (mFmt.charAt(i) != 'Z') {
					result = false;
				}
			}
		}
		return result;
	}

	public static String rightCut(Object str, int bytesCount) {
		if (str == null || str.toString().trim().equals("")
				|| str.toString().getBytes().length < 9) {
			return "         ";
		}
		return new String(str.toString().getBytes(),
				str.toString().getBytes().length - bytesCount, bytesCount);
	}

	public static String cut(String str, int bytesCount) {
		String xx = "";
		if (str == null || str.equals("")) {
			return str;
		}
		byte[] bytes = str.getBytes();
		char[] chars = new String(bytes, 0, bytesCount).toCharArray();
		char[] charsPlus = new String(bytes, 0, bytesCount + 1).toCharArray();
		if (chars.length == charsPlus.length) {
			xx = new String(bytes, 0, bytesCount - 1);
		} else {
			xx = new String(bytes, 0, bytesCount);
		}
		String mStr = "";
		for (int i = 0; i < 255; i++) {
			mStr = mStr + " ";
		}
		return new String((xx + mStr).getBytes(), 0, bytesCount);
	}

	public static int GetLen(String s, int[] pos) {
		int iLen = 0;
		if (s == null) {
			pos[0] = 0;
		} else if (s.equals("")) {
			iLen = 1;
			pos[0] = 0;
		} else {
			if (s.charAt(0) == '(') {
				pos[0] = s.indexOf(")");
				if (pos[0] > 0) {
					iLen = Integer.parseInt(s.substring(1, pos[0]));
				}
			} else {
				iLen = 1;
				pos[0] = 0;
			}
		}
		return iLen;
	}

	public static String GetDateFormat(String sFormat) {
		String sFrm;

		sFrm = sFormat.trim();
		// 替换正确的年，日，秒，毫秒
		sFrm = sFrm.replaceAll("Y", "y");
		sFrm = sFrm.replaceAll("D", "d");
		sFrm = sFrm.replaceAll("S", "s");
		sFrm = sFrm.replaceAll("m", "M");
		sFrm = sFrm.replaceAll("h", "H");
		sFrm = sFrm.replaceAll("F", "f");

		String sValue = "";
		boolean bAfterHour = false;
		for (int i = 0; i < sFrm.length(); i++) {
			if (sFrm.charAt(i) == 'y') {
				bAfterHour = false;
			}
			if (sFrm.charAt(i) == 'H') {
				bAfterHour = true;
			}
			if (sFrm.charAt(i) == 'M') { // 月或分,小写为分(m)，大写为月(M)
				if (bAfterHour) {
					sValue = sValue + "m";
				} else {
					sValue = sValue + "M";
				}
			} else {
				sValue = sValue + sFrm.charAt(i);
			}
		}
		return sValue;
	}

	public static String getStringFormat(String sFormat) {
		String sValue = null;
		String sFrm = null;
		String sLeft = null;

		sValue = "";
		sFrm = sFormat.trim().toUpperCase();
		sLeft = sFrm;

		while (sFrm.length() > 0) {
			int i = 0;
			int iLen = 0;
			int[] pos = new int[] { 0 };

			// 取得剩余的字串
			char cFirst = sFrm.charAt(0);
			sLeft = sFrm.substring(i + 1, sFrm.length());

			switch (cFirst) {
			case 'X':
				iLen = GetLen(sLeft, pos);
				for (int j = 0; j < iLen; j++)
					sValue = sValue + " ";
				break;
			case 'Z':
				iLen = GetLen(sLeft, pos);
				for (int j = 0; j < iLen; j++)
					sValue = sValue + " ";
				break;
			case '9':
				iLen = GetLen(sLeft, pos);
				for (int j = 0; j < iLen; j++)
					sValue = sValue + "0";
				break;
			case '.':
				sValue = sValue + ".";
				break;
			case 'V':
				sValue = sValue + "V";
				break;
			default:
				sValue = sValue + sFrm.charAt(i);
			}

			if (pos[0] != 0)
				sFrm = sLeft.substring(pos[0] + 1, sLeft.length());
			else
				sFrm = sLeft;
		}

		return sValue;
	}

	public static int GetFormatLength(String sFormat) {
		String sValue = getStringFormat(sFormat);
		sValue = sValue.replaceAll("V", "");
		sValue = sValue.replaceAll("v", "");
		return sValue.length();
	}

	// 读取报文时使用，根据指定的报文格式，返回对应的对象
	public static Object GetFormatObject(String str, String sFormat) {
		Object _obj = null;
		String sFrm = "";
		int[] pos = new int[] { 0 };
		int iPointLen;
		int iValue;
		double fValue;
		double iPer;

		sFrm = "";

		if (str.equals("")
				|| (str.equals("99991231") && sFormat.equals("YYYYMMDD"))) {
			return null;
		}
		try {
			// 用sFrm模拟格式
			sFrm = getStringFormat(sFormat);

			if (sFrm.indexOf("YY") >= 0 || sFrm.indexOf("HH") >= 0
					|| sFrm.indexOf("MM") >= 0) { // 日期时间数据
				if (str.trim().equals("") || str == null) {
					_obj = null;
				} else {
					_obj = EncodeDateTime(str, sFrm);
				}
			} else if (sFrm.indexOf("V") >= 0 || sFrm.indexOf(".") >= 0) // 小数
			{
				if (sFrm.indexOf("V") >= 0) // 小数点不占位
				{
					pos[0] = sFrm.indexOf("V");
					iPointLen = sFrm.length() - pos[0] - 1;
					// iValue = strtointDef(str.trim(),0);
					iValue = Integer.parseInt(str.trim());
					iPer = 1.0;
					for (int j = 0; j < iPointLen; j++) {
						iPer = iPer * 10;
					}
					fValue = iValue / iPer;
				} else { // 小数点占位
					// fValue = strToFloatDef(trim(str), 0);
					if (str.trim().equals("")) {
						str = "0";
					}
					fValue = Double.parseDouble(str.trim());
				}
				_obj = new Double(fValue);
			} else if (sFormat.toUpperCase().indexOf("X") >= 0) {// 字符串
				_obj = str.trim();
			} else { // 整数
				if (str.trim().equals("")) {
					_obj = null;
				} else {
					_obj = Integer.valueOf(str.trim());
				}

			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return _obj;
	}

	public static java.util.Date EncodeDateTime(String str, String sFrm) {
		java.util.Date dDate = null;
		int iYear;
		int iMonth;
		int iDay;
		int iHour;
		int iMinute;
		int iSecond;
		int iMillSecond;

		String sYear;
		String sMonth;
		String sDay;
		String sHour;
		String sMinute;
		String sSecond;
		String sMillSecond;

		String sFormat;

		dDate = new Date(1);

		Calendar c = Calendar.getInstance();
		c.setTime(dDate);
		iYear = c.get(Calendar.YEAR);
		iMonth = c.get(Calendar.MONTH);
		iDay = c.get(Calendar.DATE);

		iHour = 0;
		iMinute = 0;
		iSecond = 0;
		iMillSecond = 0;
		sYear = "";
		sMonth = "";
		sDay = "";
		sHour = "";
		sMinute = "";
		sSecond = "";
		sMillSecond = "";
		sFormat = GetDateFormat(sFrm);

		// 取年、月、日、时、分、秒、毫秒
		sYear = getSameStr(str, sFormat, 'y');
		sMonth = getSameStr(str, sFormat, 'M');
		sDay = getSameStr(str, sFormat, 'd');
		sHour = getSameStr(str, sFormat, 'H');
		sMinute = getSameStr(str, sFormat, 'm');
		sSecond = getSameStr(str, sFormat, 's');
		sMillSecond = getSameStr(str, sFormat, 'f');
		// 如果是两位年
		if (sYear.length() == 2)
			sYear = "20" + sYear;
		// 如果毫秒不够位
		for (int i = 0; i < (3 - sMillSecond.length()); i++) {
			sMillSecond = "0";
		}
		// 转换年、月、日、时、分、秒、毫秒
		if (sYear != "") {
			iYear = Integer.parseInt(sYear);
		}
		if (sMonth != "") {
			iMonth = Integer.parseInt(sMonth);
		}
		if (sDay != "") {
			iDay = Integer.parseInt(sDay);
		}
		if (sHour != "")
			iHour = Integer.parseInt(sHour);
		if (sMinute != "")
			iMinute = Integer.parseInt(sMinute);
		if (sSecond != "")
			iSecond = Integer.parseInt(sSecond);
		if (sMillSecond != "")
			iMillSecond = Integer.parseInt(sMillSecond);
		// 返回日期时间
		if (sFormat.indexOf("y") >= 0 && sFormat.indexOf("M") >= 0
				&& sFormat.indexOf("d") >= 0) {
			if (iYear == 0 || iMonth == 0 || iDay == 0) {
				return null;
			} else {
				c = Calendar.getInstance();
				c.set(Calendar.YEAR, iYear);
				c.set(Calendar.MONTH, iMonth);
				c.set(Calendar.DATE, iDay);
				return c.getTime();
			}
		} else if (sFormat.indexOf("H") >= 0) {
			c = Calendar.getInstance();
			c.set(Calendar.HOUR, iHour);
			c.set(Calendar.MINUTE, iMinute);
			c.set(Calendar.SECOND, iSecond);
			c.set(Calendar.MILLISECOND, iMillSecond);
			return c.getTime();
		}
		return null;
	}

	public static String getSameStr(String str, String sFrm, char c) {
		String sResult;
		int iFirst;
		int iLen;

		sResult = "";
		iFirst = 0;
		iLen = 0;
		iFirst = sFrm.indexOf(c); // 字串是从1开始的
		if (iFirst >= 0) {
			for (int i = iFirst; i < sFrm.length(); i++) {
				if (sFrm.charAt(i) != c)
					break;
				iLen++;
			}
			sResult = new String(str.getBytes(), iFirst, iLen);
		}
		return sResult;
	}
}
