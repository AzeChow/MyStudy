package com.bestway.bcus.client.common;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.text.NumberFormatter;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRField;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class CustomReportDataSource extends JREmptyDataSource  {
    private List list  = null;

    private int  index = -1;
    
    private Integer maximumFractionDigits = 6;
    
    
    
    public CustomReportDataSource(List list) {
        this.list = list;        
    }

    
    public CustomReportDataSource(List list,Integer maximumFractionDigits) {
        this.list = list;        
        this.maximumFractionDigits = maximumFractionDigits==null?0:maximumFractionDigits;
    }
    
    
    /**
     * has next data record
     */
    public boolean next()  {
        index++;
        return (index < list.size());
    }
    
    public void setFirst() {
    	index = -1;
    }

    public Object getFieldValue(JRField field) {
        String fieldDescription = field.getDescription();
        try {

            Object value = PropertyUtils.getNestedProperty(list.get(index),
                    fieldDescription);
            if (value == null) {
                return "";
            }

            if (value instanceof Date) {
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd");
                return dateFormat.format((Date) value);

            } else if (value instanceof Double || value instanceof Float) {
                Double valuesD = Double.valueOf(value.toString());
                return doubleToStr(valuesD);
            }
            return ConvertUtils.convert(value);
        } catch (Exception ex) {
            // ex.printStackTrace();
            return "";
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
}