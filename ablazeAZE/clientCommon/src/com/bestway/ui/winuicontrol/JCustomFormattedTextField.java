/*
 * Created on 2005-6-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.ui.winuicontrol;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class JCustomFormattedTextField extends JFormattedTextField {

	public JCustomFormattedTextField() {
		super();
		if (this.getFormatterFactory() == null) {
			this.setFormatterFactory(getDefaultFormatterFactory());
		}

		this.addFocusListener(new FocusListener() {

			public void focusGained(final FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						((JTextField) e.getSource()).selectAll();
					}
				});
			}

			public void focusLost(FocusEvent e) {
				setTextToZero();
			}

		});
	}

	/**
	 * 设置文本为0
	 * 
	 */
	private void setTextToZero() {
		AbstractFormatterFactory abstractFormatterFactory = this
				.getFormatterFactory();
		if (abstractFormatterFactory == null
				|| !(abstractFormatterFactory instanceof DefaultFormatterFactory)) {
			return;
		}
		DefaultFormatterFactory defaultFormatterFacotry = (DefaultFormatterFactory) abstractFormatterFactory;
		AbstractFormatter abstractDefalultFormatter = defaultFormatterFacotry
				.getDefaultFormatter();
		AbstractFormatter abstractDisplayFormatter = defaultFormatterFacotry
				.getDisplayFormatter();
		AbstractFormatter abstractEditFormatter = defaultFormatterFacotry
				.getEditFormatter();

		if ((abstractDefalultFormatter == null || !(abstractDefalultFormatter instanceof NumberFormatter))
				&& (abstractDisplayFormatter == null || !(abstractDisplayFormatter instanceof NumberFormatter))
				&& (abstractEditFormatter == null || !(abstractEditFormatter instanceof NumberFormatter))) {
			return;
		}
//		if (this.getText() == null) {
//			return;
//		}
		if (this.getText().trim().equals("")) {
//			this.setText("0");
			this.setValue(new Double(0.0));
		}

		/*
		 * if (!this.getText().trim().equals("")){ try {
		 * getNumberFormatter().setValueClass(Double.class); Double x = (Double)
		 * getNumberFormatter().stringToValue(this.getText().trim()); if
		 * (!Double.valueOf(this.getText().trim()).equals(x)){
		 * this.setText("0"); this.setValue(new Double(0)); throw new
		 * RuntimeException("您输入的数值类型错误，请重新输入!"); } } catch (Exception e){
		 * this.setText("0"); this.setValue(new Double(0)); throw new
		 * RuntimeException("您输入的数值类型错误，请重新输入!"); } }
		 */

	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory	defaultFormatterFactory	= null;

	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter	numberFormatter	= null;

	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setGroupingSize(0);
			decimalFormat.setMaximumFractionDigits(9);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

}
