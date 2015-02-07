package com.bestway.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author chl
 * 
 */
public class RegexUtil {

	/**
	 * 返回匹配次数
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static int match(String regex, String str) {
		int n = 0;

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			n++;
		}

		return n;
	}

	/**
	 * 检查 字符串str 是否匹配 正则表达式regex
	 * 
	 * @param str
	 *            要匹配的字符串
	 * @param regex
	 *            正则表达式
	 * @return
	 */
	public static boolean checkMatch(String str, String regex) {
		boolean res = false;
		if (match(regex, str) == 1) {
			res = true;
		}
		return res;
	}

	/**
	 * 检测字符串长度是否符合 l
	 * 
	 * @param str
	 *            字符串
	 * @param l
	 *            预定长度 可以有多个长度
	 * @return
	 */
	public static boolean checkEqualSomeLength(String str, int... l) {
		String regex = "^";
		for (int i = 0; i < l.length; i++) {
			if (i == 0) {
				regex = regex + ".{" + l[i] + "}";
			} else {
				regex = regex + "|.{" + l[i] + "}";
			}
		}
		regex = regex + "$";

		return checkMatch(str, regex);
	}

	/**
	 * 检测字符串最小长度是否 >= l
	 * 
	 * @param str
	 *            字符串
	 * @param l
	 *            预定长度
	 * @return
	 */
	public static boolean checkMinLength(String str, int l) {
		String regex = "^.{" + l + "}.*$";
		return checkMatch(str, regex);
	}

	/**
	 * 检测字符串是否以“startStr”开始
	 * 
	 * @param str
	 *            字符串
	 * @param startStr
	 *            开始字符串
	 * @return
	 */
	public static boolean checkStartsWith(String str, String startStr) {
		String regex = "^" + startStr + ".*$";

		return checkMatch(str, regex);
	}

	/**
	 * 检测数字字符串精度(小数位数)是否小于等于 l
	 * 
	 * @param str
	 *            字符串
	 * @param l
	 *            预定长度
	 * @return
	 */
	public static boolean checkMaxAccuracy(String str, int l) {
		String regex = null;

		if (l == 0) {
			regex = "^(\\+|-)?([1-9]+[0-9]*|[0])$";
		} else if (l == 1) {
			regex = "^(\\+|-)?([1-9]+[0-9]*|[0])($|\\.\\d{1}$)";
		} else if (l > 1) {
			regex = "^(\\+|-)?([1-9]+[0-9]*|[0])($|\\.\\d{1," + l + "}$)";
		} else {
			throw new RuntimeException("请输入一个有效的长度，您的输入为：" + l);
		}

		return checkMatch(str, regex);
	}

	/**
	 * 判断是否为实数的regex
	 */
	public static final String CHECK_FLOAT = "^(\\+|-)?([1-9]+[0-9]*|[0])($|\\.\\d+$)";

	/**
	 * 判断是否为实数，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkFloat(String str) {
		return checkMatch(str, CHECK_FLOAT);
	}

	/**
	 * 判断是否为整数的regex
	 */
	public static final String CHECK_INTEGER = "^(\\+|-)?\\d+$";

	/**
	 * 判断是否为整数，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkInteger(String str) {
		return checkMatch(str, CHECK_INTEGER);
	}

	/**
	 * 判断是否为自然数的regex
	 */
	public static final String CHECK_NATURAL_NUMBER = "^[1-9]+[0-9]*$";

	/**
	 * 判断是否为自然数，是则返回true,否则返回false 0 是否为自然数？这里不算。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkNaturalNumber(String str) {
		return checkMatch(str, CHECK_NATURAL_NUMBER);
	}

	/**
	 * 判断是否为数字的regex
	 */
	public static final String CHECK_NUMBER = "^\\d+$";

	/**
	 * 判断是否为数字，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkNumber(String str) {
		return checkMatch(str, CHECK_NUMBER);
	}

	/**
	 * 判断email格式是否正确regex
	 */
	public static final String CHECK_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	/**
	 * 判断email格式是否正确，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkEMail(String str) {
		return checkMatch(str, CHECK_EMAIL);
	}

	/**
	 * 判断手机格式是否正确regex
	 */
	public static final String CHECK_PHONE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

	/**
	 * 判断手机格式是否正确，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkPhone(String str) {
		return checkMatch(str, CHECK_PHONE);
	}

	/**
	 * 判断是否为正浮点数的regex
	 */
	public static final String CHECK_PFPINTEGER = "^[1-9]\\d*.\\d*|0.\\d*[1-9]d*|0?.0+|0$";

	/**
	 * 判断是否为正浮点数，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkPFPInteger(String str) {
		return checkMatch(str, CHECK_PFPINTEGER);
	}

	/**
	 * 判断是否为FTP的regex
	 */
	public static final String CHECK_FTP = "^ftp://((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";

	/**
	 * 判断是否为FTP，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsFTP(String str) {
		return checkMatch(str, CHECK_FTP);
	}

	/**
	 * 判断是否为IP的regex
	 */
	public static final String CHECK_IP = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";

	/**
	 * 判断是否为IP，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkIsIP(String str) {
		return checkMatch(str, CHECK_IP);
	}

	// 判断是否为bool的regex
	public static final String CHECK_BOOLEAN = "^true$|^false$";

	/**
	 * 判断是否为bool，是则返回true,否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkBoolean(String str) {
		return checkMatch(str, CHECK_BOOLEAN);
	}
}
