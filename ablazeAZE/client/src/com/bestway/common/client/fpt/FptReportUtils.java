package com.bestway.common.client.fpt;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.NumberFormatter;

import org.apache.commons.beanutils.ConvertUtils;

import com.bestway.bcus.client.common.CustomReportDataSource;

/**
 * 用于提供收发货报表中的子报表获取数据
 *
 * @author Administrator
 */
public class FptReportUtils {

	private static List inList;
	private static List outList;
	private static volatile int reportCurPageCount = 1;
	private static boolean hasInGroup;// 是否已经取了一组子报表数据
	private static boolean hasOutGroup;// 是否已经取了一组子报表数据

	public static List getInList() {
		return inList;
	}

	public static void setInList(List inList) {
		FptReportUtils.inList = inList;
	}

	public static List getOutList() {
		return outList;
	}

	public static void setOutList(List outList) {
		FptReportUtils.outList = outList;
	}

	public static int getReportCurPageCount() {
		return reportCurPageCount;
	}

	public static void setReportCurPageCount(int reportCurPageCount) {
		FptReportUtils.reportCurPageCount = reportCurPageCount;
	}

	/**
	 * 使用反射把实体当中的所有属性加入map
	 */
	public static void getParamMapFromBean(Map params, Object entity,
			String name) {
		// com.bestway.common.fpt.entity
		// com.bestway.common.fpt.entity.FptBillHead
		// Pattern p = Pattern.compile("(?i)com[.]bsw[.].*[.]entity.*");
		Pattern p = Pattern.compile("com.bestway.common.fpt.entity.*");

		Field[] fields = null;

		Class curClass = entity.getClass();

		while (p.matcher(curClass.getName()).find()) {

			Field[] curfields = curClass.getDeclaredFields();

			if (fields == null) {
				fields = curfields;
				continue;
			}

			Field[] targets = new Field[(fields == null ? 0 : fields.length)
					+ (curfields == null ? 0 : curfields.length)];

			System.arraycopy(curfields, 0, targets, 0, fields.length);

			System.arraycopy(curfields, 0, targets, fields.length,
					curfields.length);

			fields = targets;

			curClass = curClass.getSuperclass();
		}
		if (fields == null || fields.length <= 0) {
			return;
		}
		for (Field field : fields) {

			Matcher matcher = p.matcher(field.getType().getName());

			field.setAccessible(true);// 不然不能访问私有变量

			try {

				Object val = field.get(entity);

				if (matcher.find()) {

					if (val != null) {
						getParamMapFromBean(params, val, name + field.getName()
								+ ".");
					} else {
						params.put(name + field.getName(), "");

					}
				} else {
					params.put(name + field.getName(), fromOtherToString(val));

				}

			} catch (IllegalArgumentException ex) {
				Logger.getLogger(FmFptImgAppHead.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(FmFptImgAppHead.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	public static String fromOtherToString(Object value) {
		try {
			if (value == null) {
				return "";
			}
			if (value instanceof Date) {
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
						"yyyy-MM-dd ");
				return dateFormat.format((Date) value);

			} else if (value instanceof Double || value instanceof Float) {
				Double valuesD = Double.valueOf(value.toString());
				return doubleToStr(valuesD);
			}
			return ConvertUtils.convert(value);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	private static String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null) {
				return null;
			}
			if (value.doubleValue() == 0) {
				return "0.0";
			}
			getNumberFormatter().setValueClass(Double.class);
			//
			// 无穷大，NaN 都清0
			//
			if (value.equals(Double.NEGATIVE_INFINITY)
					|| value.equals(Double.POSITIVE_INFINITY)
					|| value.equals(Double.NaN)) {
				return "0.0";
			}
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static NumberFormatter getNumberFormatter() {
		NumberFormatter numberFormatter = new NumberFormatter();
		DecimalFormat decimalFormat1 = new DecimalFormat();
		decimalFormat1.setMaximumFractionDigits(3);
		decimalFormat1.setGroupingSize(0);
		numberFormatter.setFormat(decimalFormat1);
		return numberFormatter;
	}

	public static CustomReportDataSource getSubData(String biilSort) {
		int curGroupCount = (reportCurPageCount - 1) * 5;
		List result = new ArrayList();
		List curList;
		List OtherList;
		if (biilSort.equals("0")) {
			curList = inList;
			OtherList = outList;
		} else {
			curList = outList;
			OtherList = inList;
		}
		int maxFullGroupCount = OtherList.size() / 5;
		maxFullGroupCount = curList.size() / 5 > maxFullGroupCount ? maxFullGroupCount
				: curList.size() / 5;
		if (maxFullGroupCount >= reportCurPageCount) {
			result = getListIfHas(curList, curGroupCount, 5);
		} else if (maxFullGroupCount == reportCurPageCount - 1) {
			if (curList.size() / 5 > maxFullGroupCount) {
				result = getListIfHas(curList, curGroupCount,
						5 + 5 - (OtherList.size() - curGroupCount - 1));
			} else {
				result = getListIfHas(curList, curGroupCount, 5);
			}
		} else {
			if (curList.size() / 5 > maxFullGroupCount) {
				result = getListIfHas(curList, curGroupCount, 10);
			}
		}

		if (biilSort.equals("1")) {
			hasInGroup = true;
		} else {
			hasOutGroup = true;
		}
		if (hasInGroup && hasOutGroup) {
			reportCurPageCount++;
			hasInGroup = false;
			hasOutGroup = false;
		}
		return new CustomReportDataSource(result);
	}

	/**
	 * 获取list中的指定部分，超过max后只取存在的部分
	 *
	 * @param target
	 * @param begin
	 * @param max
	 * @return
	 */
	public static List getListIfHas(List target, int begin, int max) {

		if (target.size() - 1 - begin >= max) {
			return target.subList(begin, begin + max);
		} else if (target.size() - 1 - begin >= 0) {
			return target.subList(begin, target.size());
		} else {
			return new ArrayList();
		}

	}

	public static CustomReportDataSource getParentDs() {
		int size = (outList.size() + inList.size()) % 10 == 0 ? (outList.size() + inList
				.size()) / 10 : (outList.size() + inList.size()) / 10 + 1;
		List result = new ArrayList();
		for (int i = 0; i < size; i++) {
			result.add(0);
		}
		return new CustomReportDataSource(result);
	}
}
