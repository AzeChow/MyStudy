package com.bestway.client.excel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.text.NumberFormatter;
import org.apache.commons.beanutils.PropertyUtils;

public class DefaultExcelDataSource {
	protected List list = null;
	protected int index = -1;
	protected int count = 0;	
	private Integer maximumFractionDigits = 6;
	
	
	public DefaultExcelDataSource(List list) {
		this.list = list;	
		this.count = list.size();
	}

	
	public DefaultExcelDataSource(List list,List<String> fieldNames,Integer maximumFractionDigits) {
		this(list);
		this.maximumFractionDigits = maximumFractionDigits;
	}
	
	
	/**
	 * has next data record
	 */
	public boolean next() {
		index++;
		return (index < list.size());
	}

	public Object getFieldValue(String field) {
		String fieldDescription = field;
		try {
			Object value = PropertyUtils.getNestedProperty(list.get(index),
					fieldDescription);
			if (value instanceof Date) {
				java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
						"yyyy-MM-dd");
				return dateFormat.format((Date) value);

			} else if (value instanceof Double || value instanceof Float) {
				Double valuesD = Double.valueOf(value.toString());
				String valueS = doubleToStr(valuesD);
				if(valueS!=null){
					return new Double(valueS);
				}
				return null;
			}
			return value;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	//
	// 设置小数位为6位
	//
	private NumberFormatter numberFormatter = null;

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat();
			decimalFormat1.setMaximumFractionDigits(maximumFractionDigits);
			decimalFormat1.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat1);
		}
		return numberFormatter;
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
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

	public Integer getMaximumFractionDigits() {
		return maximumFractionDigits;
	}

	public void setMaximumFractionDigits(Integer maximumFractionDigits) {
		this.maximumFractionDigits = maximumFractionDigits;
	}
	
	public int getCount(){
		return count;
	}
	
	public int getIndex(){
		return index;
	}
}