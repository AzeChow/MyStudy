/*
 * Created on 2004-5-26
 *
 * // Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
package com.bestway.common;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author 001
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class BaseEntity implements Serializable, Cloneable {

	/**
	 * 表中唯一标识 id
	 */
	private String id;

	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 创建人姓名
	 */
	private String createPeople;

	/**
	 * 修改日期
	 */
	private Date modifyDate;
	
	/**
	 * 修改人姓名
	 */
	private String modifyPeople;

	/**
	 * 是否选中.用在对像与组件的绑定
	 */
	private Boolean isSelected;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id:", getId()).toString();
	}

	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (!(other.getClass().equals(this.getClass())))
			return false;
		BaseEntity castOther = (BaseEntity) other;
		if (this.getId() == null && castOther.getId() == null) {
			return super.equals(other);
		} else {
			return new EqualsBuilder().append(this.getId(), castOther.getId())
					.isEquals();
		}
	}

	public boolean fullEquals(Object other) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(this);
		for (int i = 0; i < pds.length; i++) {
			try {
				Object thisValue = PropertyUtils.getProperty(this, pds[i]
						.getName());
				Object otherValue = PropertyUtils.getProperty(other, pds[i]
						.getName());
				if (thisValue == null) {
					if (otherValue != null && !otherValue.equals("")
							&& !otherValue.equals(Double.valueOf(0))) {
						return false;
					}
				}
				if (otherValue == null) {
					if (thisValue != null && !thisValue.equals("")
							&& !thisValue.equals(Double.valueOf(0))) {
						return false;
					}
				}
				if (thisValue != null && !thisValue.equals("")
						&& !thisValue.equals(Double.valueOf(0))
						&& !thisValue.equals(otherValue)) {
					return false;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return true;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public Boolean getIsSelected() {
		if(isSelected==null)
			return false;
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * 将fieldValue拆成多个Field，进行保存
	 * 
	 * @param fieldValue
	 * @param subFields
	 */
	public void setMutipFieldValue(String fieldValue, String... subFields) {
		if (subFields == null || subFields.length <= 0) {
			return;
		}
		if (fieldValue == null || "".equals(fieldValue.trim())) {
			for (int i = 0; i < subFields.length; i++) {
				String fieldName = subFields[i];
				try {
					PropertyUtils.setProperty(this, fieldName, "");// setNestedProperty
				} catch (IllegalAccessException e) {
					// 
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// 
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// 
					e.printStackTrace();
				}
			}
			return;
		}
		int fieldNo = 0;
		int subLen = 0;
		StringBuffer sb = new StringBuffer();
		char[] charValues = fieldValue.toCharArray();
		for (int i = 0; i < charValues.length; i++) {
			int charLen = String.valueOf(charValues[i]).getBytes().length;
			if (subLen + charLen <= 255) {
				sb.append(charValues[i]);
				subLen += charLen;
			} else {
				if (fieldNo > subFields.length - 1) {
					return;
				}
				String fieldName = subFields[fieldNo];
				try {
					PropertyUtils.setProperty(this, fieldName, sb.toString());// setNestedProperty
				} catch (IllegalAccessException e) {
					// 
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				sb = new StringBuffer(charValues[i]);
				fieldNo++;
				subLen = charLen;
			}
		}
		if (fieldNo > subFields.length - 1) {
			return;
		}
		String fieldName = subFields[fieldNo];
		try {
			PropertyUtils.setProperty(this, fieldName, sb.toString());// setNestedProperty
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将栏位值按照给定的长度进行截取
	 * 
	 * @param fieldValue
	 * @param len
	 * @return
	 */
	protected String getFieldValueByLen(String fieldValue, int len) {
		if (fieldValue == null) {
			return fieldValue;
		}
		if (fieldValue.getBytes().length <= len) {
			return fieldValue;
		}
		int subLen = 0;
		StringBuffer sb = new StringBuffer();
		char[] charValues = fieldValue.toCharArray();
		for (int i = 0; i < charValues.length; i++) {
			int charLen = String.valueOf(charValues[i]).getBytes().length;
			if (subLen + charLen <= 255) {
				sb.append(charValues[i]);
				subLen += charLen;
			}
		}
		return sb.toString();
	}

	public String getCreatePeople() {
		return createPeople;
	}

	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}

	public String getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(String modifyPeople) {
		this.modifyPeople = modifyPeople;
	}
}