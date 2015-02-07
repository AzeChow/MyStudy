/*
 * Created on 2004-7-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.manualdeclare.entity;


import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 经营范围料件
 * @author Administrator
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EmsEdiTrImg extends BaseScmEntity {
	private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 经营范围表头
	 */
	private EmsEdiTrHead emsEdiTrHead;
	/**
	 * 商品编码
	 */
	private String complex;     
	/**
	 * 料件序号
	 */
	private Integer ptNo;       
	/**
	 * 商品名称
	 */
	private String name;     
	/**
	 * 备注
	 */
	private String note;       
	/**
	 * 变更次数
	 */
	private Integer modifyTimes; 
    /**
	 * 修改标志
	 * UNCHANGE = "0";	未修改
	 * MODIFIED = "1";	已修改
	 * DELETED = "2";	已删除
	 * ADDED = "3";	新增
	 */
	private String modifyMark;  
	/**
	 * 变更日期
	 */
	private Date changeDate; 
	/**
	 * @return Returns the emsEdiTrHead.
	 */
	public EmsEdiTrHead getEmsEdiTrHead() {
		return emsEdiTrHead;
	}
	/**
	 * @param emsEdiTrHead The emsEdiTrHead to set.
	 */
	public void setEmsEdiTrHead(EmsEdiTrHead emsEdiTrHead) {
		this.emsEdiTrHead = emsEdiTrHead;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return Returns the ptNo.
	 */
	public Integer getPtNo() {
		return ptNo;
	}
	/**
	 * @param ptNo The ptNo to set.
	 */
	public void setPtNo(Integer ptNo) {
		this.ptNo = ptNo;
	}
	/**
	 * @return Returns the complex.
	 */
	public String getComplex() {
		return complex;
	}
	/**
	 * @param complex The complex to set.
	 */
	public void setComplex(String complex) {
		this.complex = complex;
	}
	/**
	 * @return Returns the modifyMark.
	 */
	public String getModifyMark() {
		return modifyMark;
	}
	/**
	 * @param modifyMark The modifyMark to set.
	 */
	public void setModifyMark(String modifyMark) {
		this.modifyMark = modifyMark;
	}
	/**
	 * @return Returns the modifyTimes.
	 */
	public Integer getModifyTimes() {
		return modifyTimes;
	}
	/**
	 * @param modifyTimes The modifyTimes to set.
	 */
	public void setModifyTimes(Integer modifyTimes) {
		this.modifyTimes = modifyTimes;
	}
	/**
	 * 返回更改次数（海关申报用，固定为0）
	 */
	public Integer getModifyTimesMark() {
		return 0;
	}
	public Date getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
}
