package com.bestway.client.util;

/**
 * <p>
 * Title: JTableListModelAdapter
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: bestway
 * </p>
 * 
 * @author xj
 * @version 1.0
 */

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.text.NumberFormatter;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.client.common.CommonVariables;

public abstract class JTableListModelAdapter {
	private Log log = LogFactory.getLog(JTableListModelAdapter.class);

	private List<JTableListColumn> columns = new ArrayList<JTableListColumn>();

	private List<Integer> editColumns = new ArrayList<Integer>();

	private boolean hasListProperty = false;

	private JTable table = null;

	public JTableListModelAdapter() {
		SerialColumn serialColumn = new SerialColumn();
		columns.add(serialColumn);
		List<JTableListColumn> tempColumns = InitColumns();
		if (tempColumns != null) {
			for (int i = 0; i < tempColumns.size(); i++) {
				columns.add(tempColumns.get(i));
			}
		}
	}

	public JTableListModelAdapter(Integer maximumFractionDigits) {
		this.maximumFractionDigits = maximumFractionDigits == null ? 0
				: maximumFractionDigits;
		SerialColumn serialColumn = new SerialColumn();
		columns.add(serialColumn);
		List<JTableListColumn> tempColumns = InitColumns();
		if (tempColumns != null) {
			for (int i = 0; i < tempColumns.size(); i++) {
				columns.add(tempColumns.get(i));
			}
		}
	}

	public JTableListModelAdapter(boolean hasListProperty) {
		this.hasListProperty = hasListProperty;
		SerialColumn serialColumn = new SerialColumn();
		columns.add(serialColumn);
		List<JTableListColumn> tempColumns = InitColumns();
		for (int i = 0; i < tempColumns.size(); i++) {
			columns.add(tempColumns.get(i));
		}
	}

	public abstract List<JTableListColumn> InitColumns();

	public JTableListColumn addColumn(String caption, int width) {
		return (new JTableListColumn(caption, width));
	}

	public JTableListColumn addColumn(String caption, String property, int width) {
		return (new JTableListColumn(caption, property, width));
	}

	public JTableListColumn addColumn(String caption, String property,
			int width, int fractionCount) {
		return (new JTableListColumn(caption, property, width, fractionCount));
	}

	public JTableListColumn addColumn(String caption, String property,
			int width, boolean showSearchField) {
		return (new JTableListColumn(caption, property, width, showSearchField));
	}

	public JTableListColumn addColumn(String caption, String property,
			String customProperty, int width) {
		return (new JTableListColumn(caption, property, customProperty, width));
	}

	public JTableListColumn addColumn(String caption, String property,
			String customProperty, String dataFormat, int width) {
		return (new JTableListColumn(caption, property, customProperty,
				dataFormat, width));
	}

	public JTableListColumn addColumn(String caption, String property,
			String customProperty, int dataType, int width) {
		return (new JTableListColumn(caption, property, customProperty,
				dataType, width));
	}

	public JTableListColumn addColumn(String caption, String property,
			int width, int listIndex, String listPropertyName) {
		return (new JTableListColumn(caption, property, width, listIndex,
				listPropertyName));
	}

	public JTableListColumn addColumn(String caption, String property,
			int width, Class cl) {
		if (cl == null)
			cl = String.class;
		return (new JTableListColumn(caption, property, width, cl));
	}

	public JTableListColumn addColumn(String caption, String property,
			int width, Class cl, boolean showSearchField) {
		if (cl == null)
			cl = String.class;
		return (new JTableListColumn(caption, property, width, cl,
				showSearchField));
	}

	public JTableListColumn addColumn(String caption, String property,
			int width, String classType) {
		Class cl = null;
		try {
			cl = Class.forName(classType);
		} catch (ClassNotFoundException ex) {
			try {
				cl = Class.forName("java.lang." + classType);
			} catch (ClassNotFoundException e) {
			}
		}
		return (new JTableListColumn(caption, property, width, cl));
	}

	public String getColumnProperty(int col) {
		if (columns == null || columns.size() <= 0) {
			return null;
		}
		return ((JTableListColumn) columns.get(col)).getProperty();
	}

	public void setCellData(Object obj, Object value, int col) {
		if (col == 0) {
			return;
		}
		if (obj.getClass().isArray()) {
			Object[] mobj = (Object[]) obj;
			mobj[col - 1] = value;
		} else {
			String sProp = ((JTableListColumn) columns.get(col)).getProperty();
			try {
				PropertyUtils.setNestedProperty(obj, sProp, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 取得CELL的值
	 * 
	 * @param obj
	 *            Object： List中的对像(实体类对象，也可以是临时对象)，应用时可转换成具体的业务实体
	 * @param col
	 *            int ：column的栏位序号（从0开始）
	 * @return Object ：返回对像对应列的属性值
	 */
	public Object getCellData(Object obj, int col) {
		if (col == 0 || obj == null) {
			return obj;
		}

		//
		// 针对实体类有List型的属性的对象进行显示数据
		//
		if (hasListProperty) {
			try {
				//
				// 获取填入的字段名字
				//
				String sProp = ((JTableListColumn) columns.get(col))
						.getProperty();
				//
				// 获得填入的list属性字段的对象位置 ,默认为 -1
				// 
				int listIndex = ((JTableListColumn) columns.get(col))
						.getListIndex();
				//
				// 获得填入实体类中list型的属性字段的对象类型 ,默认为 null
				//
				String listPropertyName = ((JTableListColumn) columns.get(col))
						.getListPropertyName();
				//
				// 获
				//
				if (listIndex != -1 && listPropertyName != null) {
					//
					// 获取实体类中所有list型的属性
					//                    
					PropertyDescriptor[] pros = PropertyUtils
							.getPropertyDescriptors(obj.getClass());
					for (int i = 0; i < pros.length; i++) {
						String fieldDescription = pros[i].getName();
						Object value = PropertyUtils.getNestedProperty(obj,
								fieldDescription);
						if (value instanceof List
								&& fieldDescription.equals(listPropertyName)) {
							List list = (List) value;
							return getObjectPropertyValue(list.get(listIndex),
									sProp);
						}
					}
					return null;
				} else {
					return getObjectPropertyValue(obj, sProp);
				}
			} catch (Exception ex) {
				log.info(ex);
				return null;
			}
		}
		if (CommonVariables.isSingleBasicType(obj.getClass().getName())) {
			return obj;
		} else if (obj.getClass().isArray()) {
			Object[] mobj = (Object[]) obj;
			return mobj[col - 1];
		} else {
			JTableListColumn jtc = (JTableListColumn) columns.get(col);
			// String sProp = jtc.getProperty();
			return getObjectPropertyValue(obj, jtc);
		}
	}

	private String getObjectPropertyValue(Object obj, String property) {
		try {
			Object value = PropertyUtils.getNestedProperty(obj, property);
			if (value != null) {
				if (value instanceof Double || value instanceof Float) {
					Double valuesD = Double.valueOf(value.toString());
					valuesD = getDoubleByDigit(valuesD, 9);
					if (0.0 - valuesD == 0.0) {
						valuesD = 0.0;// 为了处理出现-0.0000之类的情况
					}
					return doubleToStr(valuesD);
				}
				return value.toString();
			}
			return null;
		} catch (Exception ex) {
			// log.error("获取" + property + "属性出错：\n" + ex);
			return null;
		}
	}

	private Object getObjectPropertyValue(Object obj, JTableListColumn jtc) {
		try {
			String property = jtc.getProperty();
			Object value = PropertyUtils.getNestedProperty(obj, property);
			// getNestedProperty():获取嵌套的javaBean属性，比如，
			// PropertyUtils.getNestedProperty(obj, obj.s);
			if (value != null) {
				if (value instanceof Double || value instanceof Float) {
					Double valuesD = Double.valueOf(value.toString());
					// valuesD = getDoubleByDigit(valuesD, 9);
					valuesD = getDoubleByDigit(valuesD,
							jtc.getFractionCount() == null ? 9 : jtc
									.getFractionCount());
					if (jtc.getFractionCount() != null
							&& jtc.getFractionCount() > 0) {
						valuesD = getDoubleByDigit(valuesD, jtc
								.getFractionCount());
					}
					if (jtc.isShowThousandthsign()) {// 以千分数显示
						DecimalFormat nf = new DecimalFormat();
						nf.setMaximumFractionDigits(jtc.getFractionCount());
						nf.setDecimalSeparatorAlwaysShown(true);
						return nf.format(valuesD);
					}
					if (0.0 - valuesD == 0.0) {
						valuesD = 0.0;// 为了处理出现-0.0000之类的情况
					}
					return doubleToStr(valuesD);
				} else if (value instanceof Date) {
					String dataFormat = jtc.getDataFormat();
					if (dataFormat == null || "".equals(dataFormat.trim())) {
						dataFormat = "yyyy-MM-dd";
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							dataFormat);
					// return java.sql.Date.valueOf(dateFormat.format(value));
					return dateFormat.format(value);
				}
				return value.toString();
			}
			return null;
		} catch (Exception ex) {
			// ex.printStackTrace();
			// log.error("获取" + property + "属性出错：\n" + ex);
			return null;
		}
	}

	public Object getValue(Object obj, int col) {
		if (columns.get(col) instanceof SerialColumn) {
			return obj;
		}
		String sProp = ((JTableListColumn) columns.get(col)).getProperty();
		try {
			Object value = PropertyUtils.getNestedProperty(obj, sProp);
			if (value != null && value instanceof Date) {
				// java.text.SimpleDateFormat dateFormat = new
				// java.text.SimpleDateFormat(
				// "yyyy/MM/dd");
				// return dateFormat.format((Date) value);
				String dataFormat = ((JTableListColumn) columns.get(col))
						.getDataFormat();
				if (dataFormat == null || "".equals(dataFormat.trim())) {
					dataFormat = "yyyy-MM-dd";
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
				// return java.sql.Date.valueOf(dateFormat.format(value));
				return dateFormat.format(value);
			}
			if (value instanceof Double || value instanceof Float) {
				Double valuesD = Double.valueOf(value.toString());
				valuesD = getDoubleByDigit(valuesD, 9);
				if (0.0 - valuesD == 0.0) {
					valuesD = 0.0;// 为了处理出现-0.0000之类的情况
				}
				return doubleToStr(valuesD);
			}
			return value;
		} catch (Exception ex) {
			// log.error("获取" + sProp + "属性出错：\n" + ex);
			return null;
		}
	}

	public final TableColumn getColumn(int i) {
		// if (i == 0)
		// return "序号";
		// else
		if (i < 0 || i > columns.size() - 1) {
			return null;
		}
		return (TableColumn) columns.get(i);
	}

	/**
	 * 获取字段属性
	 * 
	 * @param cls
	 * @param col
	 * @return
	 */
	public Class getTypeByColumn(Class cls, int col) {
		String sProp = ((JTableListColumn) columns.get(col)).getProperty();
		return CommonVariables.getTypeByField(cls, sProp);
	}

	public final int getColumnCount() {
		return this.columns.size();
	}

	public final String getColumnCaption(int i) {
		// if (i == 0)
		// return "序号";
		// else
		return ((JTableListColumn) columns.get(i)).getCaption();
	}

	public final int getColumnWidth(int i) {
		if (i == 0) {
			int rowCount = table.getModel().getRowCount();
			int width = table.getFontMetrics(table.getFont()).stringWidth(
					String.valueOf(rowCount)) + 3;
			return (width > 28) ? width : 28;
		}
		return ((JTableListColumn) columns.get(i)).getWidth();
	}

	public final Class getColumnClass(int i) {
		if (i > 0) {
			return ((JTableListColumn) columns.get(i)).getClassType();
		} else {
			return columns.get(i).getClass();
		}
	}

	public final String getPropertyName(int i) {
		if (i > 0) {
			return ((JTableListColumn) columns.get(i)).getProperty();
		}
		return null;
	}

	/**
	 * @return Returns the table.
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @param table
	 *            The table to set.
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	public void setEditableColumn(int col) {
		if (col < 0 && col >= this.getColumnCount()) {
			return;
		}
		editColumns.add(Integer.valueOf(col));
	}

	public void setEditableColumn(List<Integer> editColumns) {
		this.editColumns = editColumns;
	}

	public List<Integer> getEditableColumn() {
		return editColumns;
	}

	public List<JTableListColumn> getColumns() {
		return columns;
	}

	// /////////////////////////////////////////////////////////////
	// 默认小数位 == 设置小数位为6位
	// 可以通过这个自定义构造函数小数位
	// new JTableListModelAdapter(int maximumFractionDigits)
	// /////////////////////////////////////////////////////////////
	private Integer maximumFractionDigits = 999;

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

	public Double getDoubleByDigit(Double sourceDouble, int n) {
		if (sourceDouble == null) {
			return 0.0;
		}
		BigDecimal b = new BigDecimal(sourceDouble);
		return b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
