package com.bestway.common.message.entity;

import java.io.Serializable;

public class TempCspSignFieldInfo implements Serializable, Comparable {
	private Integer no;

	private String value;

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 重compareTo方法，用serialNo（序号）进行比较
	 * 
	 * @param o
	 *            要比较的
	 * @return int 比o小时返回-1，相等时返回0，大于时返回1
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		TempCspSignFieldInfo info = (TempCspSignFieldInfo) o;
		if (this.getNo() == null && info.getNo() != null) {
			return -1;
		}
		if (this.getNo() != null && info.getNo() == null) {
			return 1;
		}
		int serialNo1 = Integer.valueOf(this.getNo());
		int serialNo2 = Integer.valueOf(info.getNo());
		if ((serialNo1 - serialNo2) > 0) {
			return 1;
		} else if ((serialNo1 - serialNo2) == 0) {
			return 0;
		} else if ((serialNo1 - serialNo2) < 0) {
			return -1;
		}
		return 0;
	}
}
