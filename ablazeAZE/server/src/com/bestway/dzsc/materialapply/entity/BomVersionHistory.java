package com.bestway.dzsc.materialapply.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.MaterialBomMaster;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

/**
 * BOM版本历史
 * @author yp
 */
public class BomVersionHistory extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * 版本Id
	 */
	private String bomVersionId;
	
	/**
	 * 报关常用BOM－成品表
	 */
	private MaterialBomMaster master; 
	
	/**
	 * 版本号
	 */
	private Integer version; 
	
	/**
	 * 开始有效期
	 */
	private Date beginDate;
	
	/**
	 * 结束有效期
	 */
	private Date endDate;
	
	/**
	 * 获取报关常用BOM－成品表
	 * 
	 * @return master 报关常用BOM－成品表
	 */
	public MaterialBomMaster getMaster() {
		return master;
	}
	
	/**
	 * 设置报关常用BOM－成品表
	 * 
	 * @param master 报关常用BOM－成品表
	 */
	public void setMaster(MaterialBomMaster master) {
		this.master = master;
	}
	
	/**
	 * 获取版本号
	 * 
	 * @return version 版本号
	 */
	public Integer getVersion() {
		return version;
	}
	
	/**
	 * 设置版本号
	 * 
	 * @param version 版本号
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		return this.getVersion().compareTo(((MaterialBomVersion)arg0).getVersion());		
	}
	public Date getBeginDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(beginDate));
		}
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (endDate != null) {
			return java.sql.Date.valueOf(dateFormat.format(endDate));
		}
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBomVersionId() {
		return bomVersionId;
	}
	public void setBomVersionId(String bomVersionId) {
		this.bomVersionId = bomVersionId;
	}
}
