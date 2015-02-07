package com.bestway.util;

import java.math.BigDecimal;

/**
 * 计算工具类
 *
 * @author chl
 *
 */
public class CalculateUtils {

	/**
	 * 默认长度
	 */
	public static final int DEFAULT_SCALE_LEN = 9;

	/**
	 * 取绝对值
	 *
	 * @param d
	 * @return
	 */
	public static Double abs(Double d) {
		BigDecimal dec = new BigDecimal(d == null ? 0 : d);
		return dec.abs().doubleValue();
	}

	/**
	 * 取绝对值
	 *
	 * @param d
	 * @return
	 */
	public static Double abs(Float d) {
		BigDecimal dec = new BigDecimal(d == null ? 0 : d);
		return dec.abs().doubleValue();
	}

	/**
	 * 取绝对值
	 *
	 * @param d
	 * @return
	 */
	public static Double abs(Integer d) {
		BigDecimal dec = new BigDecimal(d == null ? 0 : d);
		return dec.abs().doubleValue();
	}

	/**
	 * 加法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整 @param d1 @par
	 *
	 * a m
	 *
	 * d2 @param len @return
	 */
	public static Double add(Double d1, Double d2, int len) {
		Double doubleValue = null;
		if (d1 == null) {
			d1 = 0d;
		}
		if (d2 == null) {
			d2 = 0d;
		}

		if (len < 0) {
			len = DEFAULT_SCALE_LEN;
		}
		doubleValue = new BigDecimal(d1).add(new BigDecimal(d2))
				.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
		return doubleValue;
	}

	/**
	 * 加法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Integer add(Integer d1, Integer d2) {
		if (d1 == null) {
			d1 = 0;
		}
		if (d2 == null) {
			d2 = 0;
		}
		return d1 + d2;
	}

	/**
	 * 加法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double add(Double d1, Double d2) {
		if (d1 == null) {
			d1 = 0d;
		}
		if (d2 == null) {
			d2 = 0d;
		}
		return add(d1, d2, -1);
	}

	/**
	 * 减法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整
	 * 
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double subtract(Double d1, Double d2, int len) {
		Double doubleValue = null;
		if (d1 == null) {
			d1 = 0d;
		}
		if (d2 == null) {
			d2 = 0d;
		}
		if (len < 0) {
			len = DEFAULT_SCALE_LEN;
		}

		doubleValue = new BigDecimal(d1).subtract(new BigDecimal(d2))
				.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
		return doubleValue;
	}

	/**
	 * 减法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double subtract(Double d1, Double d2) {
		if (d1 == null) {
			d1 = 0d;
		}
		if (d2 == null) {
			d2 = 0d;
		}
		return subtract(d1, d2, -1);
	}

	/**
	 * 乘法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 *
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double multiply(Double d1, Double d2, int len) {
		Double doubleValue = null;
		if (d1 == null) {
			d1 = 0d;
		}
		if (d2 == null) {
			d2 = 0d;
		}
		if (len < 0) {
			len = DEFAULT_SCALE_LEN;
		}
		doubleValue = new BigDecimal(d1).multiply(new BigDecimal(d2))
				.setScale(len, BigDecimal.ROUND_HALF_UP).doubleValue();
		return doubleValue;
	}

	/**
	 * 乘法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double multiply(Double d1, Double d2) {
		return multiply(d1, d2, -1);
	}

	/**
	 * 除法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 *
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double dividend(Double d1, Double d2, int len) {
		Double doubleValue = null;
		if (d2 != 0.0) {
			if (len < 0) {
				len = DEFAULT_SCALE_LEN;
			}
			doubleValue = new BigDecimal(d1).divide(new BigDecimal(d2), len,
					BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return doubleValue;
	}

	/**
	 * 除法
	 *
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double dividend(Double d1, Double d2) {
		return dividend(d1, d2, -1);
	}

	/**
	 * 加法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整 @param d1 @par
	 *
	 * a m
	 *
	 * d2 @param len @return
	 */
	public static Float add(Float d1, Float d2, int len) {
		Float floatValue = null;
		d1 = (d1 == null ? 0 : d1);
		d2 = (d2 == null ? 0 : d2);
		if (len < 0) {
			len = DEFAULT_SCALE_LEN;
		}
		floatValue = new BigDecimal(d1).add(new BigDecimal(d2))
				.setScale(len, BigDecimal.ROUND_HALF_UP).floatValue();
		return floatValue;
	}

	/**
	 * 加法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float add(Float d1, Float d2) {
		return add(d1, d2, -1);
	}

	/**
	 * 减法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整 @param d1 @par
	 *
	 * a m
	 *
	 * d2 @param len @return
	 */
	public static Float subtract(Float d1, Float d2, int len) {
		Float floatValue = null;
		d1 = (d1 == null ? 0 : d1);
		d2 = (d2 == null ? 0 : d2);
		if (len < 0) {
			len = DEFAULT_SCALE_LEN;
		}
		floatValue = new BigDecimal(d1).subtract(new BigDecimal(d2))
				.setScale(len, BigDecimal.ROUND_HALF_UP).floatValue();
		return floatValue;
	}

	/**
	 * 减法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float subtract(Float d1, Float d2) {
		d1 = (d1 == null ? 0 : d1);
		d2 = (d2 == null ? 0 : d2);
		return subtract(d1, d2, -1);
	}

	/**
	 * 乘法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 *
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Float multiply(Float d1, Float d2, int len) {
		Float floatValue = null;
		d1 = (d1 == null ? 0 : d1);
		d2 = (d2 == null ? 0 : d2);
		if (len < 0) {
			len = DEFAULT_SCALE_LEN;
		}
		floatValue = new BigDecimal(d1).multiply(new BigDecimal(d2))
				.setScale(len, BigDecimal.ROUND_HALF_UP).floatValue();
		return floatValue;
	}

	/**
	 * 乘法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float multiply(Float d1, Float d2) {
		return multiply(d1, d2, -1);
	}

	/**
	 * 除法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 *
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Float dividend(Float d1, Float d2, int len) {
		Float floatValue = null;
		if (d2 != 0.0) {
			if (len < 0) {
				len = DEFAULT_SCALE_LEN;
			}
			floatValue = new BigDecimal(d1).divide(new BigDecimal(d2), len,
					BigDecimal.ROUND_HALF_UP).floatValue();
		}
		return floatValue;
	}

	/**
	 * 除法
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float dividend(Float d1, Float d2) {
		return dividend(d1, d2, -1);
	}

	/**
	 * 空值时默认值
	 *
	 * @param <T>
	 *            数字
	 * @param n
	 *            原值
	 * @param defaultVal
	 *            默认值
	 * @return
	 */
	public static <T extends Number> T nullToDefault(T n, T defaultVal) {
		if (n == null) {
			return defaultVal;
		}
		return n;
	}

	/**
	 * 四舍五入
	 *
	 * @param len
	 * @param d
	 * @return
	 */
	public static Double round(Double d, Integer len) {
		if (len != null) {
			d = nullToDefault(d, 0d);
			BigDecimal decimal = new BigDecimal(d);
			decimal = decimal.setScale(len, BigDecimal.ROUND_HALF_UP);
			return decimal.doubleValue();
		}
		return d;
	}

	public static String format(Number n, Integer len) {
		n = nullToDefault(n, 0d);
		BigDecimal decimal = new BigDecimal(n.doubleValue());
		if (len != null) {
			decimal = decimal.setScale(len, BigDecimal.ROUND_HALF_UP);
		}
		return decimal.stripTrailingZeros().toPlainString();
	}

	/**
	 * 四舍五入
	 *
	 * @param len
	 * @param d
	 * @return
	 */
	public static Float round(Float d, int len) {
		d = nullToDefault(d, 0f);
		BigDecimal decimal = new BigDecimal(d);
		decimal = decimal.setScale(len, BigDecimal.ROUND_HALF_UP);
		return decimal.floatValue();
	}

	public static void main(String[] args) {
		System.out.println(round(1.5132342341, 0));
	}
}
