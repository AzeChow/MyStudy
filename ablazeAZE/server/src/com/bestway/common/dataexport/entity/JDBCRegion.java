/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.dataexport.entity;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 域定义对应表主表
 * @author
 */
public class JDBCRegion extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 目的表
	 */
	private String				destTableName		= null;						// 目的表
	/**
	 * 目的数据源
	 */
	private JDBCDataSource		destJDBCDataSource	= null;						// 目的数据源
	/**
	 * 视图对象
	 */
	private JDBCView			srcJDBCView			= null;						// 视图对象(数据来源信息)
	/**
	 * 域名称
	 */
	private String				regionName			= null;						// 域名称
	/**
	 * 域描述
	 */
	private String				note				= null;						// 域描述
	/**
	 * 转换标致
	 */
	private String				gbkToBig5Flag		= null;						// GbkToBig5Flag
																				// class
																				// 转换标致

	/**
	 * @return Returns the dbView.
	 */
	public JDBCView getSrcJDBCView() {
		return srcJDBCView;
	}

	/**
	 * @param dbView
	 *            The dbView to set.
	 */
	public void setSrcJDBCView(JDBCView dbView) {
		this.srcJDBCView = dbView;
	}

	/**
	 * @return Returns the regionName.
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * @param regionName
	 *            The regionName to set.
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public JDBCDataSource getDestJDBCDataSource() {
		return destJDBCDataSource;
	}

	public void setDestJDBCDataSource(JDBCDataSource destJDBCDataSource) {
		this.destJDBCDataSource = destJDBCDataSource;
	}

	public String getDestTableName() {
		return destTableName;
	}

	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGbkToBig5Flag() {
		return gbkToBig5Flag;
	}

	public void setGbkToBig5Flag(String gbkToBig5Flag) {
		this.gbkToBig5Flag = gbkToBig5Flag;
	}
}
