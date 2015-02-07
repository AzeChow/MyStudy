/*
 * Created on 2004-9-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;

import java.util.Date;

import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;
/**
 * 域定义设置
 * @author
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */ 
public class DBFormatSetup extends BaseScmEntity  {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 域定义对应表主表
	 */
    private DBFormat dbFormat;  
	/**
	 * 目的表字段中文名称
	 */
	private String aimName;             
	/**
	 * 目的表字段名称
	 */
	private String aimFieldName;       
	/**
	 * 目的表字段类型
	 */
	private String aimFieldType;       
	/**
	 * 目的表字段长度
	 */
	private Integer aimFieldLen;       
	/**
	 * 是否主键
	 */
	private Boolean iskey=false;             
	/**
	 * 源表字段名称
	 */
	private String sourceFieldName;      
	/**
	 * 源表字段类型
	 */
	private String sourceFieldType;      
	/**
	 * 源表字段长度
	 */
	private Integer sourceFieldLen;      
	/**
	 * 源表字段排列次序
	 */
	private Integer orderNo;             
	/**
	 * 是否匹配
	 */
	private Boolean isMatch=false;   
	/**
	 * 格式创建者
	 */
	private String creator;     
	/**
	 * 建立日期
	 */
	private Date createdate;    
	/**
	 * 转换方式
	 */
	private String gbflag;      
	/**
	 * 对象类型名称
	 */
	private ClassList objName;  
	/**
	 * 转换项
	 */
	private FieldList gbStr;   
	/**
	 * 子转换项
	 */
	private FieldList gbStrChild;
	/**
	 * @return Returns the aimFieldLen.
	 */
	public Integer getAimFieldLen() {
		return aimFieldLen;
	}
	/**
	 * @param aimFieldLen The aimFieldLen to set.
	 */
	public void setAimFieldLen(Integer aimFieldLen) {
		this.aimFieldLen = aimFieldLen;
	}
	/**
	 * @return Returns the aimFieldName.
	 */
	public String getAimFieldName() {
		return aimFieldName;
	}
	/**
	 * @param aimFieldName The aimFieldName to set.
	 */
	public void setAimFieldName(String aimFieldName) {
		this.aimFieldName = aimFieldName;
	}
	/**
	 * @return Returns the aimFieldType.
	 */
	public String getAimFieldType() {
		return aimFieldType;
	}
	/**
	 * @param aimFieldType The aimFieldType to set.
	 */
	public void setAimFieldType(String aimFieldType) {
		this.aimFieldType = aimFieldType;
	}
	/**
	 * @return Returns the aimName.
	 */
	public String getAimName() {
		return aimName;
	}
	/**
	 * @param aimName The aimName to set.
	 */
	public void setAimName(String aimName) {
		this.aimName = aimName;
	}
	/**
	 * @return Returns the createdate.
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	/**
	 * @return Returns the creator.
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param creator The creator to set.
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return Returns the dbFormat.
	 */
	public DBFormat getDbFormat() {
		return dbFormat;
	}
	/**
	 * @param dbFormat The dbFormat to set.
	 */
	public void setDbFormat(DBFormat dbFormat) {
		this.dbFormat = dbFormat;
	}
	/**
	 * @return Returns the gbflag.
	 */
	public String getGbflag() {
		return gbflag;
	}
	/**
	 * @param gbflag The gbflag to set.
	 */
	public void setGbflag(String gbflag) {
		this.gbflag = gbflag;
	}
	/**
	 * @return Returns the gbStr.
	 */
	public FieldList getGbStr() {
		return gbStr;
	}
	/**
	 * @param gbStr The gbStr to set.
	 */
	public void setGbStr(FieldList gbStr) {
		this.gbStr = gbStr;
	}
	/**
	 * @return Returns the objName.
	 */
	public ClassList getObjName() {
		return objName;
	}
	/**
	 * @param objName The objName to set.
	 */
	public void setObjName(ClassList objName) {
		this.objName = objName;
	}
	/**
	 * @return Returns the sourceFieldLen.
	 */
	public Integer getSourceFieldLen() {
		return sourceFieldLen;
	}
	/**
	 * @param sourceFieldLen The sourceFieldLen to set.
	 */
	public void setSourceFieldLen(Integer sourceFieldLen) {
		this.sourceFieldLen = sourceFieldLen;
	}
	/**
	 * @return Returns the sourceFieldName.
	 */
	public String getSourceFieldName() {
		return sourceFieldName;
	}
	/**
	 * @param sourceFieldName The sourceFieldName to set.
	 */
	public void setSourceFieldName(String sourceFieldName) {
		this.sourceFieldName = sourceFieldName;
	}
	/**
	 * @return Returns the sourceFieldType.
	 */
	public String getSourceFieldType() {
		return sourceFieldType;
	}
	/**
	 * @param sourceFieldType The sourceFieldType to set.
	 */
	public void setSourceFieldType(String sourceFieldType) {
		this.sourceFieldType = sourceFieldType;
	}

	/**
	 * @return Returns the iskey.
	 */
	public Boolean getIskey() {
		return iskey;
	}
	/**
	 * @param iskey The iskey to set.
	 */
	public void setIskey(Boolean iskey) {
		this.iskey = iskey;
	}
	/**
	 * @return Returns the isMatch.
	 */
	public Boolean getIsMatch() {
		return isMatch;
	}
	/**
	 * @param isMatch The isMatch to set.
	 */
	public void setIsMatch(Boolean isMatch) {
		this.isMatch = isMatch;
	}
	/**
	 * @return Returns the orderNo.
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * @param orderNo The orderNo to set.
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return Returns the gbStrChild.
	 */
	public FieldList getGbStrChild() {
		return gbStrChild;
	}
	/**
	 * @param gbStrChild The gbStrChild to set.
	 */
	public void setGbStrChild(FieldList gbStrChild) {
		this.gbStrChild = gbStrChild;
	}
}
