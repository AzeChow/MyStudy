package com.bestway.customs.common.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bestway.common.BaseScmEntity;
/**
 * 从QP中导入报关单到ＪＢＣＵＳ系统中的记录
 * @author Administrator
 *
 */
public class BaseLoadBGDFromQPXml extends BaseScmEntity {
	/**
	 * 报关单xml文件名
	 */
	private String fileName;
	/**
	 * 报关单申报日期
	 */
	private Date date;
	/**
	 * 进出口标志
	 * IMPORT=0;	进口标志
	 * EXPORT=1;	出口标志
	 * SPECIAL=2;	特殊报关单
	 */
	private Integer impExpFlag;  //  @jve:decl-index=0:
	/**
	 * 报关单号码
	 */
	private String customsDeclarationCode;

	/**
	 * 报关单流水号
	 */
	private Integer serialNumber;

	public Date getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			return java.sql.Date.valueOf(dateFormat.format(date));
		}
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(Integer impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public String getCustomsDeclarationCode() {
		return customsDeclarationCode;
	}

	public void setCustomsDeclarationCode(String customsDeclarationCode) {
		this.customsDeclarationCode = customsDeclarationCode;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
