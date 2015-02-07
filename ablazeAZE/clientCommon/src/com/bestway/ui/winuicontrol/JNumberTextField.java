package com.bestway.ui.winuicontrol;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;

import org.apache.axis2.description.ClientUtils;
import org.apache.tools.ant.util.StringUtils;

public class JNumberTextField extends JTextField {

	/**
	 * 指定小数点后面的长度
	 *
	 * @param docLenght
	 */
	private Integer decimalLenght = Integer.MAX_VALUE;
	/**
	 * 最小值
	 */
	private Double minValue = null;
	/**
	 * 最大值
	 */
	private Double maxValue = null;

	/**
	 * 只是显示bean属性值，并不回写到bean
	 *
	 * @param isOnlyShow
	 *            true 只是显示，false,null显示并回写
	 */
	private Boolean isOnlyShow = false;
	/**
	 * 背景颜色
	 */
	private Color defaultBackColor = getBackground();

	/**
	 * 允许负数
	 */
	private boolean allowNegative = false;

	public boolean isAllowNegative() {
		return allowNegative;
	}

	public void setAllowNegative(boolean allowNegative) {
		this.allowNegative = allowNegative;
		((JNumberDocument) this.getDocument()).setAllowNegative(allowNegative);
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
		((JNumberDocument) this.getDocument()).setMinValue(minValue);
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
		((JNumberDocument) this.getDocument()).setMaxValue(maxValue);
	}

	public JNumberTextField() {
		super();
		this.setDocument(new JNumberDocument());
		this.addPropertyChangeListener("editable",new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (!(Boolean) evt.getNewValue()) {
					dispatchEvent(new FocusEvent(JNumberTextField.this,
							FocusEvent.FOCUS_LOST));
				}
			}
		});
	}

	/**
	 * 指定小数点后面的长度
	 *
	 * @param dotLen
	 * @param allowNegative
	 */
	public JNumberTextField(int dotLen, boolean allowNegative) {
		super();
		this.decimalLenght = dotLen;
		JNumberDocument numberDocument = new JNumberDocument();
		numberDocument.setDocLenght(dotLen);
		numberDocument.setAllowNegative(allowNegative);
		this.setDocument(numberDocument);

		this.addPropertyChangeListener("editable",new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (!(Boolean) evt.getNewValue()) {
					dispatchEvent(new FocusEvent(JNumberTextField.this,
							FocusEvent.FOCUS_LOST));
				}
			}
		});
	}


	public Integer getDecimalLenght() {
		return decimalLenght;
	}

	public void setDecimalLenght(Integer docLenght) {
		this.decimalLenght = docLenght;
		// setDocument(new JNumberDocument(docLenght, allowNegative));
		((JNumberDocument) this.getDocument()).setDocLenght(docLenght);
	}
	
}
