package com.bestway.bcus.system.entity;

import com.bestway.common.BaseEntity;
import com.bestway.common.CommonUtils;

/**
 * 存放临时的企业性质资料
 * <p>Title:企业性质 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class OwnerType extends BaseEntity {
		private static final long serialVersionUID = CommonUtils.getSerialVersionUID();
		
		/**
		 * 属性代码
		 */
		private String code;  
		
	    /**
	     * 属性名称
	     */
	    private String attrName; 

	  /**
	   * 获取属性名称
	   *  
	   * @return attrName 属性名称
	   */
	  public String getAttrName() {
	    return attrName;
	  }

	  /**
	   * 设置属性代码
	   * 
	   * @param code 属性代码
	   */
	  public void setCode(String code) {
	    this.code = code;
	  }

	  /**
	   * 设置属性名称
	   * 
	   * @param attrName 属性名称
	   */
	  public void setAttrName(String attrName) {
	    this.attrName = attrName;
	  }
	
	  /**
	   * 获取属性代码
	   * 
	   * @return code 属性代码
	   */
	  public String getCode() {
	    return code;
	  }

	 
	  public OwnerType() {
	  }


}
