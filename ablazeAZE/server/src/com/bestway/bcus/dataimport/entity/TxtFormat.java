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
 * 文本格式
 * @author
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */ 
public class TxtFormat extends BaseScmEntity  {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 文本任务
	 */
    private TxtTask txttask;
	/**
	 * 字段序号
	 */
	private Integer sortno;     
	/**
	 * 字段中文名称
	 */
	private String name;        
	/**
	 * 字段名称
	 */
	private String fieldname;   
	/**
	 * 字段长度
	 */
	private Integer fieldlen;  
	/**
	 * 字段类型
	 */
	private String fieldtype;   
	/**
	 * 字段说明
	 */
	private String fielddesc;   
	/**
	 * 是否主键
	 */
	private Boolean iskey=false;      
	/**
	 * 是否为空
	 */
	private Boolean isNull=false;     
	/**
	 * 格式创建者
	 */
	private String creator;   
	/**
	 * 格式修改者
	 */
	private String editor;      
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
	 * @return Returns the editor.
	 */
	public String getEditor() {
		return editor;
	}
	/**
	 * @param editor The editor to set.
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/**
	 * @return Returns the fielddesc.
	 */
	public String getFielddesc() {
		return fielddesc;
	}
	/**
	 * @param fielddesc The fielddesc to set.
	 */
	public void setFielddesc(String fielddesc) {
		this.fielddesc = fielddesc;
	}
	/**
	 * @return Returns the fieldlen.
	 */
	public Integer getFieldlen() {
		return fieldlen;
	}
	/**
	 * @param fieldlen The fieldlen to set.
	 */
	public void setFieldlen(Integer fieldlen) {
		this.fieldlen = fieldlen;
	}
	/**
	 * @return Returns the fieldname.
	 */
	public String getFieldname() {
		return fieldname;
	}
	/**
	 * @param fieldname The fieldname to set.
	 */
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	/**
	 * @return Returns the fieldtype.
	 */
	public String getFieldtype() {
		return fieldtype;
	}
	/**
	 * @param fieldtype The fieldtype to set.
	 */
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
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
	 * @return Returns the sortno.
	 */
	public Integer getSortno() {
		return sortno;
	}
	/**
	 * @param sortno The sortno to set.
	 */
	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}
	/**
	 * @return Returns the txttask.
	 */
	public TxtTask getTxttask() {
		return txttask;
	}
	/**
	 * @param txttask The txttask to set.
	 */
	public void setTxttask(TxtTask txttask) {
		this.txttask = txttask;
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
	 * @return Returns the isNull.
	 */
	public Boolean getIsNull() {
		return isNull;
	}
	/**
	 * @param isNull The isNull to set.
	 */
	public void setIsNull(Boolean isNull) {
		this.isNull = isNull;
	}
}
