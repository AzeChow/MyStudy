/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administratorf
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgFactoryUnitConver extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton btnUpdataMetrial = null;

	private JButton btnClose = null;

	private MaterialManageAction materialManageAction = null; // @jve:decl-index=0:
	private JFormattedTextField tfUnitConvert = null;
	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse
	private JLabel jLabel8 = null;
	private JLabel jLabel12 = null;
	private JFormattedTextField tfPtNetWeight = null;

	private Boolean isOk=false; // @jve:decl-index=0:

	private JPanel jPanel = null;
	private List passlist = null; // @jve:decl-index=0:

	public Boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(Boolean isOk) {
		this.isOk = isOk;
	}

	public List getPasslist() {
		return passlist;
	}

	public void setPasslist(List passlist) {
		this.passlist = passlist;
	}

	/**
	 * This is the default constructor
	 */
	public DgFactoryUnitConver() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(466, 233);
		this.setTitle("报关/工厂资料编辑");
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel12 = new JLabel();
			jLabel8 = new JLabel();
			java.awt.GridBagConstraints gridBagConstraints10 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints9 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints8 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints7 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.ipadx = 106;
			gridBagConstraints2.insets = new java.awt.Insets(25, 1, 5, 44);
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.ipadx = 9;
			gridBagConstraints3.ipady = 5;
			gridBagConstraints3.insets = new java.awt.Insets(5, 42, 6, 25);
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.weightx = 1.0;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.ipadx = 105;
			gridBagConstraints4.insets = new java.awt.Insets(7, 1, 5, 45);
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 3;
			gridBagConstraints5.ipadx = 13;
			gridBagConstraints5.ipady = -2;
			gridBagConstraints5.insets = new java.awt.Insets(8, 48, 18, 5);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.ipadx = 12;
			gridBagConstraints6.ipady = -3;
			gridBagConstraints6.insets = new java.awt.Insets(9, 37, 18, 0);
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 2;
			gridBagConstraints7.ipadx = 10;
			gridBagConstraints7.ipady = 4;
			gridBagConstraints7.insets = new java.awt.Insets(6, 41, 8, 1);
			gridBagConstraints8.gridx = 1;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints8.ipadx = 104;
			gridBagConstraints8.insets = new java.awt.Insets(5, 2, 9, 45);
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.ipadx = -18;
			gridBagConstraints9.ipady = -7;
			gridBagConstraints9.insets = new java.awt.Insets(5, 1, 10, 22);
			gridBagConstraints10.gridx = 1;
			gridBagConstraints10.gridy = 0;
			gridBagConstraints10.gridwidth = 2;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.ipadx = 104;
			gridBagConstraints10.insets = new java.awt.Insets(23, 1, 7, 46);

			jLabel8.setText("注：单位折算=报关数量/工厂数量");
			jLabel8.setBounds(new Rectangle(17, 75, 200, 21));
			jLabel8.setForeground(new java.awt.Color(51, 0, 255));
			jLabel12.setText("单重");
			jLabel12.setBounds(new Rectangle(197, 39, 48, 18));
			jPanel2.add(getBtnUpdataMetrial(), null);
			jPanel2.add(getBtnClose(), null);
			jPanel2.add(getJPanel(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes btnUpdataMetrial
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnUpdataMetrial() {
		if (btnUpdataMetrial == null) {
			btnUpdataMetrial = new JButton();
			btnUpdataMetrial.setText("更新工厂物料");
			btnUpdataMetrial.setBounds(114, 160, 108, 23);
			btnUpdataMetrial
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
						
							if (tfUnitConvert.getText() == null
									|| ""
											.equals(tfUnitConvert.getText()
													.trim())) {
								JOptionPane.showMessageDialog(
										DgFactoryUnitConver.this, "单位折算为空，确定吗？",
										"提示", JOptionPane.INFORMATION_MESSAGE);
							}
							if (tfPtNetWeight.getText() == null
									|| ""
											.equals(tfPtNetWeight.getText()
													.trim())) {
								JOptionPane.showMessageDialog(
										DgFactoryUnitConver.this, "单重为空，确定吗？",
										"提示", JOptionPane.INFORMATION_MESSAGE);
							}
							if (JOptionPane.showConfirmDialog(
									DgFactoryUnitConver.this, "确定要更新工厂物料吗？",
									"提示", JOptionPane.YES_NO_OPTION) == 1) {
								return;
							}
							passlist = materialManageAction.updateMateriel(
									new Request(CommonVars.getCurrUser()),
									passlist, strToDouble(tfUnitConvert
											.getText()),
									strToDouble(tfPtNetWeight.getText()));
							DgFactoryUnitConver.this.dispose();
							isOk = true;
						}
					});

		}
		return btnUpdataMetrial;
	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(287, 160, 78, 23);
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgFactoryUnitConver.this.dispose();
					isOk = false;
				}

			});

		}
		return btnClose;
	}

	/**
	 * @return Returns the materialManageAction.
	 */
	public MaterialManageAction getCommonBaseCodeObj() {
		return materialManageAction;
	}

	/**
	 * @param materialManageAction
	 *            The materialManageAction to set.
	 */
	public void setCommonBaseCodeObj(MaterialManageAction materialManageAction) {
		this.materialManageAction = materialManageAction;
	}

	private Double strToDouble(String value) { // 转换strToDouble 填充数据
		try {
			if (value == null || "".equals(value)) {
				// return new Double("0");
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return (Double) getNumberFormatter().stringToValue(value);
		} catch (ParseException e) {
			e.printStackTrace();
			// return new Double("0");
			return null;
		}
	}

	private String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * This method initializes tfUnitConvert
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitConvert() {
		if (tfUnitConvert == null) {
			tfUnitConvert = new JFormattedTextField();
			tfUnitConvert.setBounds(new Rectangle(73, 35, 116, 22));
			tfUnitConvert.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfUnitConvert;
	}

	/**
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setGroupingSize(0);
			decimalFormat.setMaximumFractionDigits(19);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	/**
	 * This method initializes tfPtNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfPtNetWeight() {
		if (tfPtNetWeight == null) {
			tfPtNetWeight = new JFormattedTextField();
			tfPtNetWeight.setBounds(new Rectangle(248, 38, 139, 22));
			tfPtNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfPtNetWeight;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(19, 25, 418, 117));
			jPanel.setBorder(BorderFactory.createTitledBorder(null,
					"\u5de5\u5382\u7269\u6599\u8d44\u6599",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), Color.red));
			javax.swing.JLabel jLabel3 = new JLabel();
			jLabel3.setText("单位折算");
			jLabel3.setBounds(new Rectangle(12, 35, 48, 18));
			jPanel.add(jLabel3, null);
			jPanel.add(getTfUnitConvert(), null);
			jPanel.add(jLabel12, null);
			jPanel.add(getTfPtNetWeight(), null);
			jPanel.add(jLabel8, null);
		}
		return jPanel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
