package com.bestway.bcus.client.common;

import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFormattedTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class CustomFormattedTextFieldUtils {

	private static Map<Integer, DefaultFormatterFactory> map = new HashMap<Integer, DefaultFormatterFactory>();

	private static Double oldValue = null;

	public static void setFormatterFactory(JFormattedTextField tf, int digitNum) {

		DefaultFormatterFactory formatterFactory = map.get(digitNum);
		if (formatterFactory == null) {
			NumberFormatter numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(digitNum);
			decimalFormat.setGroupingSize(0);
			decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
			numberFormatter.setFormat(decimalFormat);
			formatterFactory = new DefaultFormatterFactory();
			formatterFactory.setDisplayFormatter(numberFormatter);
			formatterFactory.setEditFormatter(numberFormatter);
			formatterFactory.setDefaultFormatter(numberFormatter);
			formatterFactory.setNullFormatter(numberFormatter);
			map.put(digitNum, formatterFactory);
		}
		tf.setFormatterFactory(formatterFactory);
	}

	public static void addAutoCalcListener(final JFormattedTextField tf,
			final AutoCalcListener callBack) {
		tf.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							tf.commitEdit();
						} catch (Exception ex) {
						}
						if (callBack != null) {
							callBack.run();
						}
					}
				});
			}
		});
		tf.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						oldValue = (tf.getValue() == null ? 0.0 : Double
								.valueOf(tf.getValue().toString()));
					}
				});
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							tf.commitEdit();
						} catch (Exception ex) {
						}
						Double newVaule = (tf.getValue() == null ? 0.0 : Double
								.valueOf(tf.getValue().toString()));
						if (newVaule == null || oldValue == null) {
							return;
						}
						if (newVaule != null && oldValue == null
								|| newVaule == null && oldValue != null
								|| !newVaule.equals(oldValue)) {
							if (callBack != null) {
								callBack.run(); 
							}
						}
					}
				});
			}
		});
	}

	/**
	 * 根据设定的小数位来获取正确位数的数值
	 * 
	 * @param tf
	 * @return
	 */
	public static double getFormattedTextFieldValue(JFormattedTextField tf) {
		if (tf.getFormatterFactory() != null
				&& tf.getFormatterFactory() instanceof DefaultFormatterFactory) {
			DefaultFormatterFactory formatterFactory = (DefaultFormatterFactory) tf
					.getFormatterFactory();
			if (formatterFactory.getEditFormatter() != null
					&& formatterFactory.getEditFormatter() instanceof NumberFormatter) {
				NumberFormatter numberFormatter = (NumberFormatter) formatterFactory
						.getEditFormatter();
				if (numberFormatter.getFormat() != null
						&& numberFormatter.getFormat() instanceof DecimalFormat) {
					DecimalFormat decimalFormat = (DecimalFormat) numberFormatter
							.getFormat();
					int digitNum = decimalFormat.getMaximumFractionDigits();
					Double value = (tf.getValue() == null ? 0.0 : Double
							.parseDouble(tf.getValue().toString()));
					BigDecimal b = new BigDecimal(value);
					value = b.setScale(digitNum, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					return value;
				}
			}
		}
		return (tf.getValue() == null ? 0.0 : Double.parseDouble(tf.getValue()
				.toString()));
	}
}
