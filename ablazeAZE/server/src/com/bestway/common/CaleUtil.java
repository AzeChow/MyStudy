package com.bestway.common;

import java.math.BigDecimal;
import java.util.Dictionary;

/**
 * 计算工具类
 * 
 * @author chl
 * 
 */
public class CaleUtil {
	
	public static void main(String[] args) {
		System.out.println(abs(-555.001));
		System.out.println(abs(-555.001f));
		System.out.println(abs(-555));
	}
	/**
	 * 取绝对值
	 * @param d
	 * @return
	 */
	public static Double abs(Double d){
		BigDecimal dec = new BigDecimal(d == null ? 0 : d);
		return dec.abs().doubleValue();
	}
	/**
	 * 取绝对值
	 * @param d
	 * @return
	 */
	public static Double abs(Float d){
		BigDecimal dec = new BigDecimal(d == null ? 0 : d);
		return dec.abs().doubleValue();
	}
	/**
	 * 取绝对值
	 * @param d
	 * @return
	 */
	public static Double abs(Integer d){
		BigDecimal dec = new BigDecimal(d == null ? 0 : d);
		return dec.abs().doubleValue();
	}
	
	/**
	 * 加法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double add(Double d1, Double d2, int len) {
		Double doubleValue = null;
		d1 = CommonUtils.getDoubleExceptNull(d1);
		d2 = CommonUtils.getDoubleExceptNull(d2);
		if(len<0){
			doubleValue = new BigDecimal(d1).add(new BigDecimal(d2)).doubleValue();
		}else if(len>=0){
			doubleValue = new BigDecimal(d1).add(new BigDecimal(d2)).setScale(len, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
		}
		return doubleValue;
	}
	/**
	 * 加法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double add(Double d1, Double d2) {
		d1 = CommonUtils.getDoubleExceptNull(d1);
		d2 = CommonUtils.getDoubleExceptNull(d2);
		return add(d1, d2,-1);
	}
	/**
	 * 减法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double subtract(Double d1, Double d2, int len) {
		Double doubleValue = null;
		d1 = CommonUtils.getDoubleExceptNull(d1);
		d2 = CommonUtils.getDoubleExceptNull(d2);
		if(len<0){
			doubleValue = new BigDecimal(d1).subtract(new BigDecimal(d2)).doubleValue();
		}else if(len>=0){
			doubleValue = new BigDecimal(d1).subtract(new BigDecimal(d2)).setScale(len, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
		}
		return doubleValue;
	}
	/**
	 * 减法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double subtract(Double d1, Double d2) {
		d1 = CommonUtils.getDoubleExceptNull(d1);
		d2 = CommonUtils.getDoubleExceptNull(d2);
		return subtract(d1,d2,-1);
	}
	/**
	 * 乘法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double multiply(Double d1, Double d2, int len) {
		Double doubleValue = null;
		d1 = CommonUtils.getDoubleExceptNull(d1);
		d2 = CommonUtils.getDoubleExceptNull(d2);
		if(len<0){
			doubleValue = new BigDecimal(d1).multiply(new BigDecimal(d2)).doubleValue();
		}else if(len>=0){
			doubleValue = new BigDecimal(d1).multiply(new BigDecimal(d2)).setScale(len, BigDecimal.ROUND_HALF_UP)
			.doubleValue();
		}
		return doubleValue;
	}
	/**
	 * 乘法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double multiply(Double d1, Double d2) {		
		return multiply(d1, d2, -1);
	}
	/**
	 * 除法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double dividend(Double d1, Double d2, int len) {
		Double doubleValue = null;		
		if(d2!=0.0){
			if(len<0){
				doubleValue = new BigDecimal(d1).divide(new BigDecimal(d2),0, BigDecimal.ROUND_HALF_UP).doubleValue();
			}else if(len>=0){
				doubleValue = new BigDecimal(d1).divide(new BigDecimal(d2),len, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
			}
		}
		return doubleValue;
	}
	/**
	 * 除法
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Double dividend(Double d1, Double d2) {
		return dividend( d1,  d2, -1);
	}
	/**
	 * 加法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Float add(Float d1, Float d2, int len) {
		Float floatValue = null;
		d1 = (d1 == null ? 0: d1); 
		d2 = (d2 == null ? 0: d2);
		if(len<0){
			floatValue = new BigDecimal(d1).add(new BigDecimal(d2)).floatValue();
		} else if(len>=0){
			floatValue = new BigDecimal(d1).add(new BigDecimal(d2)).setScale(len, BigDecimal.ROUND_HALF_UP)
			.floatValue();
		}
		return floatValue;
	}
	/**
	 * 加法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float add(Float d1, Float d2) {
		return add( d1,  d2, -1);
	}
	/**
	 * 减法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Float subtract(Float d1, Float d2, int len) {
		Float floatValue = null;
		d1 = (d1 == null ? 0: d1); 
		d2 = (d2 == null ? 0: d2);
		if(len<0){
			floatValue = new BigDecimal(d1).subtract(new BigDecimal(d2)).floatValue();
		}else if(len>=0){
			floatValue = new BigDecimal(d1).subtract(new BigDecimal(d2)).setScale(len, BigDecimal.ROUND_HALF_UP)
			.floatValue();
		}
		return floatValue;
	}
	/**
	 * 减法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float subtract(Float d1, Float d2) {
		d1 = (d1 == null ? 0: d1); 
		d2 = (d2 == null ? 0: d2);
		return subtract( d1,  d2, -1);
	}
	/**
	 * 乘法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Float multiply(Float d1, Float d2, int len) {
		Float floatValue = null;
		d1 = (d1 == null ? 0: d1); 
		d2 = (d2 == null ? 0: d2);
		if(len<0){
			floatValue = new BigDecimal(d1).multiply(new BigDecimal(d2)).floatValue();
		}else if(len>=0){
			floatValue = new BigDecimal(d1).multiply(new BigDecimal(d2)).setScale(len, BigDecimal.ROUND_HALF_UP)
			.floatValue();
		}
		return floatValue;
	}
	
	/**
	 * 乘法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float multiply(Float d1, Float d2) {
		return multiply( d1,  d2,-1);
	}
	/**
	 * 除法,取len位数小数,四舍五入,len < 0 不进行四舍五入，len = 0 是取整,len>0进行四舍五入
	 * @param d1
	 * @param d2
	 * @param len
	 * @return
	 */
	public static Float dividend(Float d1, Float d2, int len) {
		Float floatValue = null;
		if(d2!=0.0){
			if(len<0){
				floatValue = new BigDecimal(d1).divide(new BigDecimal(d2)).floatValue();
			}else if(len>=0){
				floatValue = new BigDecimal(d1).divide(new BigDecimal(d2),len, BigDecimal.ROUND_HALF_UP)
				.floatValue();
			}
		}
		return floatValue;
	}
	/**
	 * 除法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Float dividend(Float d1, Float d2) {
		return dividend( d1,  d2,-1);
	}
}
