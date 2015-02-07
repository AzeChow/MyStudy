/*
 * Created on 2004-9-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.dataimport.entity;


import com.bestway.common.BaseScmEntity;
import com.bestway.common.CommonUtils;

/**
 * 类列表
 * @author 
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClassList extends BaseScmEntity {
    private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
	/**
	 * 序号
	 */
    private Integer sortno;    
	/**
	 * 表中文名称
	 */
    private String name;       
	/**
	 * 类路径
	 */
    private String classPath;   
	/**
	 * 是否存在公司id
	 */
    private Boolean isCoid=false;  

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
	 * @return Returns the classPath.
	 */
	public String getClassPath() {
		return classPath;
	}
	/**
	 * @param classPath The classPath to set.
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
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
	 * @return Returns the isCoid.
	 */
	public Boolean getIsCoid() {
		return isCoid;
	}
	/**
	 * @param isCoid The isCoid to set.
	 */
	public void setIsCoid(Boolean isCoid) {
		this.isCoid = isCoid;
	}
}
