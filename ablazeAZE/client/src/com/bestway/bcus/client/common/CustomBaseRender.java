/*
 * Created on 2004-6-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.common;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import org.apache.commons.beanutils.BeanUtils;

import com.bestway.bcus.custombase.entity.CustomBaseEntity;

/**
 * @author 001
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class CustomBaseRender {
	private static CustomBaseRender customBaseRender = null;

	private static KeyValueListCellRenderer render = null;// DefaultListCellRenderer
															// BasicComboBoxRenderer

	public static CustomBaseRender getInstance() {
		if (customBaseRender == null) {
			customBaseRender = new CustomBaseRender();
		}
		return customBaseRender;
	}

	public KeyValueListCellRenderer getRender() {
		render = new KeyValueListCellRenderer();
		return render;
	}

	/**
	 * 获得呈现对象 并设置第一列和第二列的显示宽度
	 */
	public KeyValueListCellRenderer getRender(int firstColumnWidth,
			int secondColumnWidth) {
		return new KeyValueListCellRenderer(firstColumnWidth, secondColumnWidth);
	}

	public KeyValueListCellRenderer getRender(String codeField, String nameField) {
		render = new KeyValueListCellRenderer(codeField, nameField);
		return render;
	}

	/**
	 * 获得呈现对象 并设置第一列和第二列的显示宽度
	 */
	public KeyValueListCellRenderer getRender(String codeField,
			String nameField, int firstColumnWidth, int secondColumnWidth) {
		render = new KeyValueListCellRenderer(codeField, nameField,
				firstColumnWidth, secondColumnWidth);
		return render;
	}

	public class KeyValueListCellRenderer extends DefaultListCellRenderer {

		private String codeField = "";

		private String nameField = "";

		private JLabel lbKey = null;

		private JLabel lbValue = null;

		public KeyValueListCellRenderer() {
			this.setLayout(null);
			lbKey = new JLabel("");
			lbKey.setBounds(0, 0, 50, 20);
			lbValue = new JLabel("");
			lbValue.setBounds(50, 0, 100, 20);
			this.add(lbKey);
			this.add(lbValue);
		}

		public KeyValueListCellRenderer(int firstColumnWidth,
				int secondColumnWidth) {
			this.setLayout(null);
			lbKey = new JLabel("");
			lbKey.setBounds(0, 0, firstColumnWidth, 20);
			lbValue = new JLabel("");
			lbValue.setBounds(firstColumnWidth, 0, secondColumnWidth, 20);
			this.add(lbKey);
			this.add(lbValue);
		}

		public KeyValueListCellRenderer(String codeField, String nameField) {
			this.setLayout(null);
			lbKey = new JLabel("");
			lbKey.setBounds(0, 0, 50, 20);
			lbValue = new JLabel("");
			lbValue.setBounds(50, 0, 100, 20);
			this.add(lbKey);
			this.add(lbValue);
			this.codeField = codeField;
			this.nameField = nameField;
		}

		public KeyValueListCellRenderer(String codeField, String nameField,
				int firstColumnWidth, int secondColumnWidth) {
			this.setLayout(null);
			lbKey = new JLabel("");

			// lbKey.setForeground(java.awt.Color.darkGray);
			// lbKey.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
			// 16));

			lbKey.setBounds(0, 0, firstColumnWidth, 20);
			lbValue = new JLabel("");
			// lbValue.setForeground(java.awt.Color.red);
			// lbValue.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
			// 16));

			lbValue.setBounds(firstColumnWidth, 0, secondColumnWidth, 20);
			this.add(lbKey);
			this.add(lbValue);
			this.codeField = codeField;
			this.nameField = nameField;
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);

			if (value != null) {

				Object obj = value;

				// Object obj=list.getModel().getElementAt(index);

				if (obj instanceof CustomBaseEntity) {

					lbValue.setText(((CustomBaseEntity) obj).getName());

					lbKey.setText(((CustomBaseEntity) obj).getCode());

				} else if (obj instanceof ItemProperty) {

					lbValue.setText(((ItemProperty) obj).getName());

					lbKey.setText(((ItemProperty) obj).getCode());

				} else if ((nameField != null) && (!nameField.equals(""))) {

					// System.out.println("____________________________________");

					try {

						String[] strs = nameField.split("\\+");

						int size = strs.length;

						if (size > 1) {

							String lbText = "";

							for (int i = 0; i < size; i++) {

								if (i == 0) {

									lbText = BeanUtils
											.getProperty(obj, strs[i]);

								} else {

									lbText += "/"
											+ BeanUtils.getProperty(obj,
													strs[i]);

								}
							}
							lbValue.setText(lbText);
							// System.out.println("多"+lbText);
						} else {
							lbValue.setText(BeanUtils.getProperty(obj,
									nameField));
							// System.out.println("一"+lbValue.getText());
						}
						lbKey.setText(BeanUtils.getProperty(obj, codeField));
					} catch (Exception e) {
						e.printStackTrace();
					}
					// catch (InvocationTargetException e) {
					// e.printStackTrace();
					// } catch (NoSuchMethodException e) {
					// e.printStackTrace();
					// }catch(Exception e){
					// e.printStackTrace();
					// }
				} else {
					lbKey.setBounds(0, 0, 150, 20);
					lbValue.setBounds(150, 0, 0, 20);
					lbKey.setText(obj.toString());
				}
				lbKey.setVerticalAlignment(SwingConstants.TOP);
				lbValue.setVerticalAlignment(SwingConstants.TOP);
				// setText(((CustomBaseEntity) value).getName());
				// ((JComboBox)this.getParent()).setUI(new SteppedComboBoxUI());
			} else {
				// setText(null);
			}
			setText(" ");
			// this.setBackground(this.lbKey.getBackground());
			// setIcon(CommonVars.getInstance().getRcpIconSource().getIcon("misc.rep_editors_view.icon"));
			return this;

		}

		public KeyValueListCellRenderer setForegroundColor(java.awt.Color color) {
			this.lbKey.setForeground(color);
			this.lbValue.setForeground(color);
			return this;
		}

		public KeyValueListCellRenderer setBackgroundColor(java.awt.Color color) {
			this.lbKey.setBackground(color);
			this.lbValue.setBackground(color);
			return this;
		}
	}

}
