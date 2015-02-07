package com.bestway.ui.winuicontrol;

import javax.swing.JTable;

public class JTableBase extends JTable implements Comparable {

	private static final long	serialVersionUID	= 1L;
	
	
	private Integer	jarDataSaveOrder	= 0;

	public JTableBase() {
		super();
	}

	public Integer getJarDataSaveOrder() {
		return jarDataSaveOrder;
	}

	public void setJarDataSaveOrder(Integer jarDataSaveOrder) {
		this.jarDataSaveOrder = jarDataSaveOrder;
	}
	
	public int compareTo(Object o) {
		JTableBase a = (JTableBase) o;
		if (a == null) {
			return 1;
		}
		//
		// ASC
		//
		if (this.jarDataSaveOrder.intValue()  > a.getJarDataSaveOrder().intValue()) {
			return 1;
		} else if (this.jarDataSaveOrder.intValue()  > a.getJarDataSaveOrder().intValue()) {
			return -1;
		}
		return 0;
	}
}