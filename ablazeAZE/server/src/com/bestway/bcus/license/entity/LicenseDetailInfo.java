/*
 * Created on 2004-12-3
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.license.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * licese明细信息
 * 
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LicenseDetailInfo implements Serializable {
	private static final long serialVersionUID = 3689913980965500976L;// CommonUtils.getSerialVersionUID();

	public final static int PASSED = 0;// 通过

	public final static int NO_LICENSE = -1;// 无license文件

	public final static int DATE_OUT = -2;// 使用期限已过

	public final static int CLINT_NUM_OUT = -3;// 客户端数量已超过允许数量

	/**
	 * 开始使用日期
	 */
	private Date beginDate;

	/**
	 * 到期日
	 */
	private Date maturityDate;

	/**
	 * 允许的客户端数量
	 */
	private Integer clientNum;

	/**
	 * 给客户授权的模块
	 */
	private List hasModules;

	/**
	 * 是否是试用版本
	 */
	private Boolean isProbationalEdition = false;

	/**
	 * 深加工结转类型 0：内部管理 + 海关接口传输 1：海关接口传输
	 */
	private int fptManageType = 0;

	/**
	 * 数据导入接口类型 0:全部 1:DB导入 2:文本导入
	 */
	private int dataImportType = 0;

	/**
	 * @return Returns the isProbationalEdition.
	 */
	public Boolean getIsProbationalEdition() {
		return isProbationalEdition;
	}

	/**
	 * @param isProbationalEdition
	 *            The isProbationalEdition to set.
	 */
	public void setIsProbationalEdition(Boolean isProbationalEdition) {
		this.isProbationalEdition = isProbationalEdition;
	}

	/**
	 * @return Returns the beginDate.
	 */
	public Date getBeginDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(beginDate));
		}
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            The beginDate to set.
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return Returns the clientNum.
	 */
	public Integer getClientNum() {
		return clientNum;
	}

	/**
	 * @param clientNum
	 *            The clientNum to set.
	 */
	public void setClientNum(Integer clientNum) {
		this.clientNum = clientNum;
	}

	/**
	 * @return Returns the maturityDate.
	 */
	public Date getMaturityDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (maturityDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(maturityDate));
		}
		return maturityDate;
	}

	/**
	 * @param maturityDate
	 *            The maturityDate to set.
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public List getHasModules() {
		return hasModules;
	}

	public void setHasModules(List exceptingModules) {
		this.hasModules = exceptingModules;
	}

	public int getDataImportType() {
		return dataImportType;
	}

	public void setDataImportType(int dataImportType) {
		this.dataImportType = dataImportType;
	}

	public int getFptManageType() {
		return fptManageType;
	}

	public void setFptManageType(int fptType) {
		this.fptManageType = fptType;
	}

}
