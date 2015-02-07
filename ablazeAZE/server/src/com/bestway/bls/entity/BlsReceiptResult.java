/*
 * Created on 2004-8-17
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.entity;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 报文信息接收
 * 
 * @author yp // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class BlsReceiptResult extends BaseScmEntity implements Comparable {
	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * 数据类型
	 */
	private String messageType;

	/**
	 * 单证号码
	 */
	private String keyCode;

	/**
	 * 信息代码
	 */
	private String messageCode;

	/**
	 * 服务状态
	 */
	private String serviceStatus;
	/**
	 * 服务句柄
	 */
	private String serviceHandle;
	/**
	 * 通知日期
	 */
	private Date noticeDate;
	/**
	 * 信息描述
	 */
	private String description;

	/**
	 * 相关联对象(车次，货到报告，关系捆绑核销)的ID
	 */
	private String relateID;

	/**
	 * @return Returns the applType.
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * @param applType
	 *            The applType to set.
	 */
	public void setMessageType(String applType) {
		this.messageType = applType;
	}

	/**
	 * @return Returns the chkMark.
	 */
	public String getServiceStatus() {
		return serviceStatus;
	}

	/**
	 * @param chkMark
	 *            The chkMark to set.
	 */
	public void setServiceStatus(String chkMark) {
		this.serviceStatus = chkMark;
	}

	/**
	 * @return Returns the copEmsNo.
	 */
	public String getKeyCode() {
		return keyCode;
	}

	/**
	 * @param copEmsNo
	 *            The copEmsNo to set.
	 */
	public void setKeyCode(String copEmsNo) {
		this.keyCode = copEmsNo;
	}

	/**
	 * @return Returns the declareType.
	 */
	public String getMessageCode() {
		return messageCode;
	}

	/**
	 * @param declareType
	 *            The declareType to set.
	 */
	public void setMessageCode(String declareType) {
		this.messageCode = declareType;
	}

	/**
	 * @return Returns the note.
	 */
	public String getServiceHandle() {
		return serviceHandle;
	}

	/**
	 * @param note
	 *            The note to set.
	 */
	public void setServiceHandle(String note) {
		this.serviceHandle = note;
	}

	/**
	 * @return Returns the noticeDate.
	 */
	public Date getNoticeDate() {
		return noticeDate;
	}

	/**
	 * @param noticeDate
	 *            The noticeDate to set.
	 */
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	/**
	 * 重compareTo方法，用noticeDate（通知日期）进行比较
	 * 
	 * @param o
	 *            要比较的
	 * @return int 比o小时返回-1，相等时返回0，大于时返回1
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return 1;
		}
		BlsReceiptResult result = (BlsReceiptResult) o;
		if (this.getNoticeDate() == null && result.getNoticeDate() != null) {
			return -1;
		}
		if (this.getNoticeDate() != null && result.getNoticeDate() == null) {
			return 1;
		}
		long sub = this.getNoticeDate().getTime()
				- result.getNoticeDate().getTime();
		if ((sub) > 0) {
			return 1;
		} else if ((sub) == 0) {
			return 0;
		} else if ((sub) < 0) {
			return -1;
		}
		return 0;
	}

	public String getFormatedNoticeDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSS");
		if (this.noticeDate != null) {
			return dateFormat.format(noticeDate);
		} else {
			return "";
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description =this.getFieldValueByLen(description,200);
	}

	public String getRelateID() {
		return relateID;
	}

	public void setRelateID(String relateID) {
		this.relateID = relateID;
	}

}
