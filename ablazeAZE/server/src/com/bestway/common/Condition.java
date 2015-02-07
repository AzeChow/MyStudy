/*
 * Created on 2004-9-21
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Condition implements Serializable{
	/**
	 * 逻辑关系(and or !)
	 */
	private String logic;
	/**
	 * 
	 */
	private String beginBracket;	
	private String fieldName;
	private String operator;
	private Object value;	
	private String endBracket;

	public Condition(String logic, String beginBracket, String fieldName, String operator,
			Object value, String endBracket){
		this.logic = logic;
		this.beginBracket = beginBracket;
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
		this.endBracket = endBracket;
	}
	/**
	 * @return Returns the beginBracket.
	 */
	public String getBeginBracket() {
		return beginBracket;
	}
	/**
	 * @param beginBracket The beginBracket to set.
	 */
	public void setBeginBracket(String beginBracket) {
		this.beginBracket = beginBracket;
	}
	/**
	 * @return Returns the endBracket.
	 */
	public String getEndBracket() {
		return endBracket;
	}
	/**
	 * @param endBracket The endBracket to set.
	 */
	public void setEndBracket(String endBracket) {
		this.endBracket = endBracket;
	}
	/**
	 * @return Returns the fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName The fieldName to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return Returns the operator.
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator The operator to set.
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * @return Returns the logic.
	 */
	public String getLogic() {
		return logic;
	}
	/**
	 * @param logic The logic to set.
	 */
	public void setLogic(String logic) {
		this.logic = logic;
	}
}
