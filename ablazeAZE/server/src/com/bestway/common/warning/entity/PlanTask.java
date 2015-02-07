package com.bestway.common.warning.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
import com.bestway.common.authority.entity.AclUser;
/**
 * 计划任务
 * @author Administrator
 *
 */
public class PlanTask extends BaseScmEntity {
	private static final long	serialVersionUID	= CommonUtils
															.getSerialVersionUID();
	/**
	 * 录入人员对象
	 */
	private AclUser				aclUser;
	/**
	 * 任务名称
	 */
	private String				itemName;											// 任务名称
	/**
	 * 运行时间(年月日)
	 */
	private Date				timestamp;
	/**
	 * 每日,每周,每月,间隔时间
	 */
	private Integer				excuteKind;										// 每日,每周,每月,间隔时间
	/**
	 * 运行日期
	 */
	private String				excuteday;											// 运行日期
	/**
	 * 运行时间
	 */
	private String				excutetime;										// 运行时间
	/**
	 * 提示内容
	 */
	private String				content1;
	/**
	 * 提示内容2
	 */
	private String				content2;
	
	/**
	 * 提示内容3
	 */
	private String				content3;
	/**
	 * 提示内容4
	 */
	private String				content4;
	/**
	 * 提示内容5
	 */
	private String				content5;
	/**
	 * 提示内容
	 */
	private String				content;											// 提示内容
	/**
	 * 任务说明
	 */
	private String				note;												// 任务说明

	public String getExcuteday() {
		return excuteday;
	}

	public void setExcuteday(String excuteday) {
		this.excuteday = excuteday;
	}

	public Integer getExcuteKind() {
		return excuteKind;
	}

	public void setExcuteKind(Integer excuteKind) {
		this.excuteKind = excuteKind;
	}

	public String getExcutetime() {
		return excutetime;
	}

	public void setExcutetime(String excutetime) {
		this.excutetime = excutetime;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent1() {
		return content1;
	}

	public void setContent1(String sqlStr1) {
		this.content1 = sqlStr1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String sqlStr2) {
		this.content2 = sqlStr2;
	}

	public String getContent3() {
		return content3;
	}

	public void setContent3(String sqlStr3) {
		this.content3 = sqlStr3;
	}

	public String getContent4() {
		return content4;
	}

	public void setContent4(String sqlStr4) {
		this.content4 = sqlStr4;
	}

	public String getContent() {
		if (content1 == null) {
			content1 = "";
		}
		if (content2 == null) {
			content2 = "";
		}
		if (content3 == null) {
			content3 = "";
		}
		if (content4 == null) {
			content4 = "";
		}
		if (content5 == null) {
			content5 = "";
		}
		return this.content1 + this.content2 + this.content3 + this.content4
				+ this.content5;
	}

	public void setContent(String sqlStr) {
		if (sqlStr == null || "".equals(sqlStr.trim())) {
			return;
		}
		int length = sqlStr.length();
		if (length <= 225) {
			this.setContent1(sqlStr.substring(0, length));
			this.setContent2("");
			this.setContent3("");
			this.setContent4("");
			this.setContent5("");
		} else if (length <= 450) {
			this.setContent1(sqlStr.substring(0, 225));
			this.setContent2(sqlStr.substring(225, length));
			this.setContent3("");
			this.setContent4("");
			this.setContent5("");
		} else if (length <= 675) {
			this.setContent1(sqlStr.substring(0, 225));
			this.setContent2(sqlStr.substring(225, 450));
			this.setContent3(sqlStr.substring(450, length));
			this.setContent4("");
			this.setContent5("");
		} else if (length <= 900) {
			this.setContent1(sqlStr.substring(0, 225));
			this.setContent2(sqlStr.substring(225, 450));
			this.setContent3(sqlStr.substring(450, 675));
			this.setContent4(sqlStr.substring(675, length));
			this.setContent5("");
		} else if (length <= 1125) {
			this.setContent1(sqlStr.substring(0, 225));
			this.setContent2(sqlStr.substring(225, 450));
			this.setContent3(sqlStr.substring(450, 675));
			this.setContent4(sqlStr.substring(675, 900));
			this.setContent5(sqlStr.substring(900, length));
		}
	}

	public String getContent5() {
		return content5;
	}

	public void setContent5(String sqlStr5) {
		this.content5 = sqlStr5;
	}

	public AclUser getAclUser() {
		return aclUser;
	}

	public void setAclUser(AclUser aclUser) {
		this.aclUser = aclUser;
	}

	public Date getTimestamp() {
		if (timestamp == null) {
			return null;
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(bartDateFormat.format(timestamp));
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
