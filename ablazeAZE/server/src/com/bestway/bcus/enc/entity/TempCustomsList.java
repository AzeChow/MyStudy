/*
 * Created on 2004-7-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.enc.entity;

import java.util.List;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * @author Administrator
 * 
 */
public class TempCustomsList extends BaseEntity implements Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	private Boolean isSelected = null;

	private AtcMergeAfterComInfo afterinfo = null; // 清单归并后

	private List list = null; // 清单归并前

	public AtcMergeAfterComInfo getAfterinfo() {
		return afterinfo;
	}

	public void setAfterinfo(AtcMergeAfterComInfo afterinfo) {
		this.afterinfo = afterinfo;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int compareTo(Object objs) {
		if (objs == null) {
			return 1;
		}
		TempCustomsList os = (TempCustomsList) objs;
		AtcMergeAfterComInfo o = os.getAfterinfo();
		
		if (o == null && this.getAfterinfo() == null) {
			return 0;
		} else if (o == null ) {
			return 1;
		} else if (this.getAfterinfo() == null) {
			return -1;
		}
		
		
		if (o.getEmsSerialNo() == null && this.getAfterinfo().getEmsSerialNo() == null) {
			return 0;
		} else if (o.getEmsSerialNo() == null) {
			return 1;
		} else if (this.getAfterinfo().getEmsSerialNo() == null) {
			return -1;
		}
		
		
		if (o.getEmsSerialNo() > this.getAfterinfo().getEmsSerialNo()) {
			return -1;
		} else if (o.getEmsSerialNo() == this.getAfterinfo().getEmsSerialNo()) {
			if ((o.getVersion() == null || "".equals(o.getVersion()))
					&& (this.getAfterinfo().getVersion() == null || "".equals(this.getAfterinfo().getVersion()))) {
				// 没有版本 则按原产国排序
				if(o.getCountry() == null && this.getAfterinfo().getCountry() == null) {
					return 0;
				} else if (o.getCountry() == null) {
					return 1;
				} else if (this.getAfterinfo().getCountry() == null) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getAfterinfo().getCountry().getCode()) > 0) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getAfterinfo().getCountry().getCode()) < 0) {
					return 1;
				} else {
					return 0;
				}
			} else if (o.getVersion() == null || "".equals(o.getVersion())) {
				return 1;
			} else if(this.getAfterinfo().getVersion() == null || "".equals(this.getAfterinfo().getVersion())) {
				return -1;
			}
			
			// 有版本按版本排序
			if (Integer.parseInt(o.getVersion()) > Integer.parseInt(this
					.getAfterinfo().getVersion())) {
				return -1;
			} else if (Integer.parseInt(o.getVersion()) == Integer
					.parseInt(this.getAfterinfo().getVersion())) {
				// 版本相同按原产国排序
				if(o.getCountry() == null && this.getAfterinfo().getCountry() == null) {
					return 0;
				} else if (o.getCountry() == null) {
					return 1;
				} else if (this.getAfterinfo().getCountry() == null) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getAfterinfo().getCountry().getCode()) > 0) {
					return -1;
				} else if (o.getCountry().getCode().compareTo(
						this.getAfterinfo().getCountry().getCode()) < 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return 1;
			}
		} else {
			return 1;
		}

	}
}