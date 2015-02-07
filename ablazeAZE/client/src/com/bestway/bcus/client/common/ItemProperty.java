/*
 * Created on 2004-7-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JComboBox;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ItemProperty {
	private String code;

	private String name;

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof ItemProperty))
			return false; 
		ItemProperty tmp = (ItemProperty)o;
		if(tmp.code != null && tmp.code.equals(code) 
				&& tmp.name != null && tmp.name.equals(name))
			return true;
		return false;
	}

	public ItemProperty(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getIndexByCode(String code, JComboBox ccb) {
		if (ccb == null || code == null || code.equals("")
				|| ccb.getItemCount() <= 0) {
			return -1;
		}
		if (ccb.getItemAt(0) instanceof ItemProperty) {
			for (int i = 0; i < ccb.getItemCount(); i++) {
				if (((ItemProperty) ccb.getItemAt(i)).getCode().equals(code)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int getIndexByName(String name, JComboBox ccb) {
		if (ccb == null || name.equals("") || ccb.getItemCount() <= 0) {
			return -1;
		}
		if (ccb.getItemAt(0) instanceof ItemProperty) {
			for (int i = 0; i < ccb.getItemCount(); i++) {
				if (((ItemProperty) ccb.getItemAt(i)).getName().equals(name)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int getIndexByValue(String fieldName, Object value,
			JComboBox ccb) {
		if (ccb == null || value == null || value.equals("")
				|| ccb.getItemCount() <= 0) {
			return -1;
		}
		for (int i = 0; i < ccb.getItemCount(); i++) {
			Object obj = ccb.getItemAt(i);
			Object currValue = null;
			try {
				currValue = PropertyUtils.getNestedProperty(obj, fieldName);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			if (value.equals(currValue)) {
				return i;
			}
		}
		return -1;
	}
	
	public static String getItemPropertyValue(JComboBox cbb) {
		ItemProperty item = (ItemProperty) (cbb.getSelectedIndex() > -1 ? cbb
				.getSelectedItem() : null);
		if(item != null) {
			return item.getCode();
		} else {
			return null;
		}
	}
}