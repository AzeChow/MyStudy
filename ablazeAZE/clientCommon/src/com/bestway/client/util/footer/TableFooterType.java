package com.bestway.client.util.footer;

public class TableFooterType implements java.io.Serializable {
	/**
	 * 求和
	 */
	public static final int SUM = 0;

	/**
	 * 求平均值
	 */
	public static final int AVG = 1;

	/**
	 * 求个数（除去重复值）
	 */
	public static final int COUNT = 2;

	/**
	 * 求最大值
	 */
	public static final int MAX = 3;

	/**
	 * 求最小值
	 */
	public static final int MIN = 4;

	/**
	 * 常量
	 */
	public static final int CONSTANT = 5;

	private Integer columnNo;

	private int type;

	private String preFix;
	//
	// luosheng 2008/12/23 加入
	//
	private int fractionCount = 9;

	
	public TableFooterType(Integer columnNo, int type, String preFix,int fractionCount) {
		this(columnNo, type, preFix);
		this.fractionCount = fractionCount;
	}
	
	public TableFooterType(Integer columnNo, int type, String preFix) {
		this.columnNo = columnNo;
		this.type = type;
		this.preFix = preFix;
	}

	public Integer getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(Integer columnNo) {
		this.columnNo = columnNo;
	}

	public String getPreFix() {
		return preFix;
	}

	public void setPreFix(String preFix) {
		this.preFix = preFix;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getFractionCount() {
		return fractionCount;
	}

	public void setFractionCount(int fractionCount) {
		this.fractionCount = fractionCount;
	}

}
