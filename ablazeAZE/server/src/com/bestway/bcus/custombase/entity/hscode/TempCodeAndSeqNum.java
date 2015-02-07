package com.bestway.bcus.custombase.entity.hscode;

import java.io.Serializable;

public class TempCodeAndSeqNum implements Serializable, Comparable<Object> {
	/**
	 * 版本信息
	 */
	private static final long serialVersionUID = 1L;
	private String complexCode = null;
	private Integer seqNum = null;

	public String getComplexCode() {
		return complexCode;
	}

	public void setComplexCode(String complexCode) {
		this.complexCode = complexCode;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public int compareTo(Object obj) {
		if (obj == null || !(obj instanceof TempCodeAndSeqNum)) {
			return -1;
		}
		TempCodeAndSeqNum temp = (TempCodeAndSeqNum) obj;
		if (temp.getSeqNum() == null) {
			return -1;
		}
		if (this.getSeqNum() == null) {
			return 1;
		}
		if (temp.getSeqNum() > this.getSeqNum()) {
			return 1;
		} else {
			if (temp.getSeqNum() < this.getSeqNum()) {
				return -1;
			} else if (temp.getSeqNum() == this.getSeqNum()) {
				return 0;
			}
		}
		return 1;
	}
}
