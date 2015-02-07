package com.bestway.client.util;

import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * <p>
 * Title: JTableListColumn
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

public class JTableListColumn extends TableColumn {
	private String customProperty = null;
	private boolean showSearch = true;
	private Integer fractionCount = null;// Double , double保留小数位

	private boolean showThousandthsign = false; // 设置Double , double以千分号显示
	private String caption;
	private String property;
	private int width;
	private Class classType;
	private int dataType = DataType.NULL;

	private String dataFormat = null;

	public String getCaption() {
		return caption;
	}

	/**
	 * JTableColumn
	 * 
	 * @param caption
	 *            String
	 * @param width
	 *            int
	 */
	public JTableListColumn(String caption, String property, int width,
			Class classType) {

		this.setCaption(caption);

		this.setWidth(width);

		this.setProperty(property);

		this.setClassType(classType);

		this.setCellRenderer(new DefaultTableCellRenderer() {

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {

				super.getTableCellRendererComponent(table, value, isSelected,

				hasFocus, row, column);

				if (value != null) {// 设置Double数据的显示格式

					if (value instanceof Double) {

						Double tmp = Double.parseDouble((String) value);

						DecimalFormat df = new DecimalFormat("###");

						this.setText("" + df.format(tmp));
					}
				}
				return this;
			}
		});
	}

	private void setRenderer() {

	}

	public JTableListColumn(String caption, String property, int width,
			Class classType, boolean showSearchField) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
		this.setClassType(classType);
		this.setShowSearch(showSearchField);
	}

	public JTableListColumn(String caption, String property, int width) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
	}

	public JTableListColumn(String caption, String property, int width,
			int fractionCount) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
		this.setFractionCount(fractionCount);
	}

	public JTableListColumn(String caption, String property,
			String customProperty, int width) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
		this.setCustomProperty(customProperty);
	}

	public JTableListColumn(String caption, String property,
			String customProperty, int dataType, int width) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
		this.setCustomProperty(customProperty);
		this.setDataType(dataType);
	}

	public JTableListColumn(String caption, String property, int width,
			boolean showSearchField) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
		this.setShowSearch(showSearchField);
	}

	public JTableListColumn(String caption, String property,
			String customProperty, String dataFormat, int width) {
		this.setCaption(caption);
		this.setProperty(property);
		this.setCustomProperty(customProperty);
		this.setDataFormat(dataFormat);
		this.setDataType(dataType);
		this.setWidth(width);
	}

	/**
	 * 
	 * @param caption
	 *            列名
	 * @param property
	 *            对象属性名
	 * @param width
	 *            列宽
	 * @param listIndex
	 *            list 字段顺序
	 * @param listPropertyName
	 *            list 字段名称
	 */
	public JTableListColumn(String caption, String property, int width,
			int listIndex, String listPropertyName) {
		this.setCaption(caption);
		this.setWidth(width);
		this.setProperty(property);
		this.setListIndex(listIndex);
		this.setListPropertyName(listPropertyName);
	}

	public JTableListColumn(String caption, int width) {
		this.setCaption(caption);
		this.setWidth(width);
	}

	public String getProperty() {
		return property;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setClassType(Class classType) {
		this.classType = classType;
	}

	public int getWidth() {
		return width;
	}

	public Class getClassType() {
		if (classType == null)
			classType = String.class;
		return classType;
	}

	/**
	 * c
	 * 
	 * @return Returns the listIndex.
	 */
	public int getListIndex() {
		return listIndex;
	}

	/**
	 * @return Returns the listPropertyName.
	 */
	public String getListPropertyName() {
		return listPropertyName;
	}

	/**
	 * @param listIndex
	 *            The listIndex to set.
	 */
	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}

	/**
	 * @param listPropertyName
	 *            The listPropertyName to set.
	 */
	public void setListPropertyName(String listPropertyName) {
		this.listPropertyName = listPropertyName;
	}

	public JTableListColumn() {
	}

	//
	// 针对有list属性的对象字段
	//
	private int listIndex = -1;
	private String listPropertyName = null;

	public String getCustomProperty() {
		return customProperty;
	}

	public void setCustomProperty(String customProperty) {
		this.customProperty = customProperty;
	}

	/**
	 * @return Returns the showSearch.
	 */
	public boolean isShowSearch() {
		return showSearch;
	}

	/**
	 * @param showSearch
	 *            The showSearch to set.
	 */
	public void setShowSearch(boolean showSearch) {
		this.showSearch = showSearch;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Integer getFractionCount() {
		return fractionCount;
	}

	/**
	 * 设置小数位
	 * 
	 * @param fractionCount
	 */
	public void setFractionCount(Integer fractionCount) {
		this.fractionCount = fractionCount;
	}

	public boolean isShowThousandthsign() {
		return showThousandthsign;
	}

	public void setShowThousandthsign(boolean showThousandthsign) {
		this.showThousandthsign = showThousandthsign;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String valueFormat) {
		this.dataFormat = valueFormat;
	}
}
