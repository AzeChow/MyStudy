package com.bestway.dzsc.materialapply.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.MaterialBomVersion;

/**
 * 存放电子手册－单耗申报的版本资料
 * 
 * @author yp
 */
public class MaterialBomVersionApply extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	
	/**
	 * BOM备案记录
	 */
	private MaterialBomMasterApply bomMasterApply;
	
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
	 *  是否选中
	 */
	private Boolean isSelected=false;
	
	/**
	 * 获取开始有效期
	 * 
	 * @return beginDate 开始有效期
	 */
	public Date getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置开始有效期
	 * 
	 * @param beginDate 开始有效期
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取结束有效期
	 * 
	 * @return endDate 结束有效期
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置结束有效期
	 * 
	 * @param endDate 结束有效期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	/**
	 * 获取BOM备案记录
	 * 
	 * @return  bomMasterApply BOM备案记录
	 */
	public MaterialBomMasterApply getBomMasterApply() {
		return bomMasterApply;
	}
	
	/**
	 * 设置BOM备案记录
	 * 
	 * @param bomMasterApply BOM备案记录
	 */
	public void setBomMasterApply(MaterialBomMasterApply bomMasterApply) {
		this.bomMasterApply = bomMasterApply;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
//	/**
//	 * 获取状态标志
//	 * 
//	 * @return stateMark 状态标志
//	 */
//	public String getStateMark() {
//		return stateMark;
//	}
//	
//	/**
//	 * 设置状态标志
//	 * 
//	 * @param stateMark 状态标志
//	 */
//	public void setStateMark(String stateMark) {
//		this.stateMark = stateMark;
//	}
}
