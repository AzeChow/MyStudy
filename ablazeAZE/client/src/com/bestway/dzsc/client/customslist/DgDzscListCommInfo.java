/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.customslist;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.dzsc.customslist.action.DzscListAction;
import com.bestway.dzsc.customslist.entity.DzscBillListBeforeCommInfo;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscListCommInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfMaterielCode = null;

	private JTextField tfComplexName = null;

	private JTextField tfUnit = null;

	private JTextField tfSecondLegalUnit = null;

	private JComboBox cbbUses = null;

	private JTextField tfMemo = null;

	private JTextField tfEmsSerialNo = null;

	private JTextField tfComplexCode = null;

	private JTextField tfComplexSpec = null;

	private JTextField tfLegalUnit = null;

	private JComboBox cbbCurrency = null;

	private JComboBox cbbLevyModel = null;

	private JFormattedTextField tfNetWeight = null;

	private JFormattedTextField tfDeclaredAmount = null;

	private JFormattedTextField tfSecondLegalAmount = null;

	private JFormattedTextField tfGrossWeight = null;

	private JFormattedTextField tfDeclaredPrice = null;

	private JFormattedTextField tfLegalAmount = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JTableListModel tableModel; // @jve:decl-index=0:visual-constraint="501,12"

	private DzscBillListBeforeCommInfo beforeCommInfo = null; // @jve:decl-index=0:

	private DzscListAction dzscListAction = null;

	private boolean isOk = false;

	private boolean isMakeCustomsDeclaration = false;

	private JLabel jLabel29 = null;

	private JTextField tfBomVersion = null;

	private JComboBox cbbCountry = null;

	private JLabel jLabel231 = null;

	private JCustomFormattedTextField tfTotalPrice = null;

	private JButton btnPrevious = null;
	
	private JButton btnNext = null;
	/**
	 * @return Returns the isMakeCustomsDeclaration.
	 */
	public boolean isMakeCustomsDeclaration() {
		return isMakeCustomsDeclaration;
	}

	/**
	 * @param isMakeCustomsDeclaration
	 *            The isMakeCustomsDeclaration to set.
	 */
	public void setMakeCustomsDeclaration(boolean isMakeCustomsDeclaration) {
		this.isMakeCustomsDeclaration = isMakeCustomsDeclaration;
	}

	/**
	 * @return Returns the isOk.
	 */
	public boolean isOk() {
		return isOk;
	}

	/**
	 * @param isOk
	 *            The isOk to set.
	 */
	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscListCommInfo() {
		super();
		initialize();
		dzscListAction = (DzscListAction) CommonVars.getApplicationContext()
				.getBean("dzscListAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("报关清单商品信息编辑");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(573, 388);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				if (tableModel.getCurrentRow() != null) {
					beforeCommInfo = (DzscBillListBeforeCommInfo) tableModel
							.getCurrentRow();
					showData(beforeCommInfo);
					setState();
				}
			}
		});
	}

	private void setState() {
		this.tfDeclaredAmount.setEditable(!this.isMakeCustomsDeclaration);
		this.tfDeclaredPrice.setEditable(!this.isMakeCustomsDeclaration);
		this.tfLegalAmount.setEditable(!this.isMakeCustomsDeclaration);
		this.tfSecondLegalAmount.setEditable(!this.isMakeCustomsDeclaration);
		this.tfNetWeight.setEditable(!this.isMakeCustomsDeclaration);
		this.tfGrossWeight.setEditable(!this.isMakeCustomsDeclaration);
		this.tfBomVersion.setEditable(!this.isMakeCustomsDeclaration);
		this.tfMemo.setEditable(!this.isMakeCustomsDeclaration);
		this.cbbCurrency.setEnabled(!this.isMakeCustomsDeclaration);
		this.cbbLevyModel.setEnabled(!this.isMakeCustomsDeclaration);
		this.cbbUses.setEnabled(!this.isMakeCustomsDeclaration);
		this.cbbCountry.setEnabled(!this.isMakeCustomsDeclaration);
		this.btnSave.setEnabled(!this.isMakeCustomsDeclaration);
	}
	public JButton getBtnPrevious() {
		if(btnPrevious == null){
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(beforeCommInfo == null){
						return;
					}
					
					fillData(beforeCommInfo);
					saveData();
					
					tableModel.updateRow(beforeCommInfo);
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					
					beforeCommInfo = (DzscBillListBeforeCommInfo) tableModel.getCurrentRow();
					
					
					showData(beforeCommInfo);
					
					setState();
				}
			});
		}
		return btnPrevious;
	}

	public JButton getBtnNext() {
		if(btnNext == null){
			btnNext = new JButton();
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(beforeCommInfo == null){
						return;
					}
					
					fillData(beforeCommInfo);
					saveData();
					
					tableModel.updateRow(beforeCommInfo);
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					
					beforeCommInfo = (DzscBillListBeforeCommInfo) tableModel.getCurrentRow();
					
					
					showData(beforeCommInfo);
					
					setState();
				}
			});
		}
		return btnNext;
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel231 = new JLabel();
			jLabel231.setBounds(new Rectangle(293, 164, 82, 21));
			jLabel231.setText("总价");
			jLabel231.setForeground(Color.blue);
			jLabel29 = new JLabel();
			jLabel29.setBounds(new Rectangle(294, 217, 88, 19));
			jLabel29.setText("成品版本号");
			jLabel29.setForeground(Color.blue);
			jPanel = new JPanel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel19 = new JLabel();
			javax.swing.JLabel jLabel20 = new JLabel();
			javax.swing.JLabel jLabel21 = new JLabel();
			javax.swing.JLabel jLabel22 = new JLabel();
			javax.swing.JLabel jLabel23 = new JLabel();
			javax.swing.JLabel jLabel24 = new JLabel();
			javax.swing.JLabel jLabel25 = new JLabel();
			javax.swing.JLabel jLabel26 = new JLabel();
			javax.swing.JLabel jLabel27 = new JLabel();
			javax.swing.JLabel jLabel28 = new JLabel();
			jPanel.setLayout(null);
			jLabel10.setBounds(19, 19, 90, 18);
			jLabel10.setText("商品货号");
			jLabel10.setForeground(Color.blue);
			jLabel11.setBounds(19, 42, 90, 21);
			jLabel11.setText("商品名称");
			jLabel11.setForeground(Color.blue);
			jLabel12.setBounds(19, 66, 90, 21);
			jLabel12.setText("计量单位");
			jLabel12.setForeground(Color.blue);
			jLabel13.setBounds(293, 89, 82, 21);
			jLabel13.setText("申报数量");
			jLabel13.setForeground(Color.blue);
			jLabel14.setBounds(19, 113, 90, 22);
			jLabel14.setText("法定第二单位");
			jLabel15.setBounds(293, 113, 82, 18);
			jLabel15.setText("第二法定数量");
			jLabel16.setBounds(19, 189, 90, 22);
			jLabel16.setText("产销国（地区）");
			jLabel16.setForeground(Color.blue);
			jLabel17.setBounds(18, 217, 90, 22);
			jLabel17.setText("用途代码");
			jLabel17.setForeground(Color.blue);
			jLabel18.setBounds(18, 241, 90, 20);
			jLabel18.setText("毛重");
			jLabel19.setBounds(26, 268, 83, 20);
			jLabel19.setText("备注");
			jLabel20.setBounds(293, 19, 82, 22);
			jLabel20.setText("手册序号");
			jLabel20.setForeground(Color.blue);
			jLabel21.setBounds(293, 42, 82, 22);
			jLabel21.setText("商品编码");
			jLabel21.setForeground(Color.blue);
			jLabel22.setBounds(293, 66, 82, 22);
			jLabel22.setText("商品规格");
			jLabel23.setBounds(19, 163, 90, 22);
			jLabel23.setText("企业申报单价");
			jLabel23.setForeground(Color.blue);
			jLabel24.setBounds(19, 136, 90, 22);
			jLabel24.setText("法定计量单位");
			jLabel24.setForeground(Color.blue);
			jLabel25.setBounds(293, 136, 82, 22);
			jLabel25.setText("法定数量");
			jLabel25.setForeground(Color.blue);
			jLabel26.setBounds(19, 88, 90, 22);
			jLabel26.setText("币制");
			jLabel26.setForeground(Color.blue);
			jLabel27.setBounds(293, 189, 82, 22);
			jLabel27.setText("征免方式");
			jLabel27.setForeground(Color.blue);
			jLabel28.setBounds(292, 241, 82, 22);
			jLabel28.setText("净重");
			jPanel.add(jLabel10, null);
			jPanel.add(jLabel11, null);
			jPanel.add(jLabel12, null);
			jPanel.add(jLabel13, null);
			jPanel.add(jLabel14, null);
			jPanel.add(jLabel15, null);
			jPanel.add(jLabel16, null);
			jPanel.add(jLabel17, null);
			jPanel.add(jLabel18, null);
			jPanel.add(jLabel19, null);
			jPanel.add(jLabel20, null);
			jPanel.add(jLabel21, null);
			jPanel.add(jLabel22, null);
			jPanel.add(jLabel23, null);
			jPanel.add(jLabel24, null);
			jPanel.add(jLabel25, null);
			jPanel.add(jLabel26, null);
			jPanel.add(jLabel27, null);
			jPanel.add(jLabel28, null);
			jPanel.add(getTfMaterielCode(), null);
			jPanel.add(getTfComplexName(), null);
			jPanel.add(getTfUnit(), null);
			jPanel.add(getTfSecondLegalUnit(), null);
			jPanel.add(getCbbUses(), null);
			jPanel.add(getTfMemo(), null);
			jPanel.add(getTfEmsSerialNo(), null);
			jPanel.add(getTfComplexCode(), null);
			jPanel.add(getTfComplexSpec(), null);
			jPanel.add(getTfLegalUnit(), null);
			jPanel.add(getCbbCurrency(), null);
			jPanel.add(getCbbLevyModel(), null);
			jPanel.add(getTfNetWeight(), null);
			jPanel.add(getTfDeclaredAmount(), null);
			jPanel.add(getTfSecondLegalAmount(), null);
			jPanel.add(getTfGrossWeight(), null);
			jPanel.add(getTfDeclaredPrice(), null);
			jPanel.add(getTfLegalAmount(), null);
			jPanel.add(jLabel29, null);
			jPanel.add(getTfBomVersion(), null);
			jPanel.add(getCbbCountry(), null);
			jPanel.add(jLabel231, null);
			jPanel.add(getTfTotalPrice(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnPrevious(), null);
			jToolBar.add(getBtnNext(), null);
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (beforeCommInfo != null) {
						if (!checkData()) {
							return;
						}
						saveData();
					}
					setOk(true);
					dispose();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes tfMaterielCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterielCode() {
		if (tfMaterielCode == null) {
			tfMaterielCode = new JTextField();
			tfMaterielCode.setBounds(110, 19, 158, 20);
			tfMaterielCode.setEditable(false);
		}
		return tfMaterielCode;
	}

	/**
	 * This method initializes tfComplexName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexName() {
		if (tfComplexName == null) {
			tfComplexName = new JTextField();
			tfComplexName.setBounds(110, 42, 158, 20);
			tfComplexName.setEditable(false);
		}
		return tfComplexName;
	}

	/**
	 * This method initializes tfUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(110, 66, 158, 20);
			tfUnit.setEditable(false);
		}
		return tfUnit;
	}

	/**
	 * This method initializes tfSecondLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSecondLegalUnit() {
		if (tfSecondLegalUnit == null) {
			tfSecondLegalUnit = new JTextField();
			tfSecondLegalUnit.setBounds(110, 113, 158, 20);
			tfSecondLegalUnit.setEditable(false);
		}
		return tfSecondLegalUnit;
	}

	/**
	 * This method initializes cbbUses
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUses() {
		if (cbbUses == null) {
			cbbUses = new JComboBox();
			cbbUses.setBounds(109, 217, 158, 20);
		}
		return cbbUses;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(109, 268, 434, 20);
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfEmsSerialNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsSerialNo() {
		if (tfEmsSerialNo == null) {
			tfEmsSerialNo = new JTextField();
			tfEmsSerialNo.setBounds(378, 19, 164, 20);
			tfEmsSerialNo.setEditable(false);
		}
		return tfEmsSerialNo;
	}

	/**
	 * This method initializes tfComplexCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexCode() {
		if (tfComplexCode == null) {
			tfComplexCode = new JTextField();
			tfComplexCode.setBounds(378, 42, 164, 20);
			tfComplexCode.setEditable(false);
		}
		return tfComplexCode;
	}

	/**
	 * This method initializes tfComplexSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplexSpec() {
		if (tfComplexSpec == null) {
			tfComplexSpec = new JTextField();
			tfComplexSpec.setBounds(378, 66, 164, 20);
			tfComplexSpec.setEditable(false);
		}
		return tfComplexSpec;
	}

	/**
	 * This method initializes tfLegalUnit
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(110, 136, 158, 20);
			tfLegalUnit.setEditable(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes cbbCurrency
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(110, 88, 158, 20);
		}
		return cbbCurrency;
	}

	/**
	 * This method initializes cbbLevyModel
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyModel() {
		if (cbbLevyModel == null) {
			cbbLevyModel = new JComboBox();
			cbbLevyModel.setBounds(378, 189, 164, 20);
		}
		return cbbLevyModel;
	}

	/**
	 * This method initializes tfNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(378, 241, 164, 20);
			tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes tfDeclaredAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDeclaredAmount() {
		if (tfDeclaredAmount == null) {
			tfDeclaredAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeclaredAmount.setBounds(378, 89, 164, 20);
			tfDeclaredAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfDeclaredAmount;
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfSecondLegalAmount() {
		if (tfSecondLegalAmount == null) {
			tfSecondLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfSecondLegalAmount.setBounds(378, 113, 164, 20);
			tfSecondLegalAmount
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfSecondLegalAmount;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(109, 241, 158, 20);
			tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes tfDeclaredPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDeclaredPrice() {
		if (tfDeclaredPrice == null) {
			tfDeclaredPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDeclaredPrice.setBounds(110, 163, 158, 20);
			tfDeclaredPrice.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfDeclaredPrice;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setBounds(378, 136, 164, 20);
			tfLegalAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
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
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * 将窗口控件上的数据付值到data实体上，以用来保存
	 * 
	 * @param data
	 */
	private void fillData(DzscBillListBeforeCommInfo data) {
		if (this.tfDeclaredAmount.getValue() != null) {
			data.setDeclaredAmount(Double.valueOf(this.tfDeclaredAmount
					.getValue().toString()));
		}
		if (this.tfDeclaredPrice.getValue() != null) {
			data.setDeclaredPrice(Double.valueOf(this.tfDeclaredPrice
					.getValue().toString()));
		}
		if (this.tfLegalAmount.getValue() != null) {
			data.setLegalAmount(Double.valueOf(this.tfLegalAmount.getValue()
					.toString()));
		}
		if (this.tfSecondLegalAmount.getValue() != null) {
			data.setSecondLegalAmount(Double.valueOf(this.tfSecondLegalAmount
					.getValue().toString()));
		}
		if (this.tfGrossWeight.getValue() != null) {
			data.setGrossWeight(Double.valueOf(this.tfGrossWeight.getValue()
					.toString()));
		}
		if (this.tfNetWeight.getValue() != null) {
			data.setNetWeight(Double.valueOf(this.tfNetWeight.getValue()
					.toString()));
		}
		data.setSalesCountry((Country) this.cbbCountry.getSelectedItem());
		data.setCurrency((Curr) this.cbbCurrency.getSelectedItem());
		data.setUsesCode((Uses) this.cbbUses.getSelectedItem());
		data.setLevyMode((LevyMode) this.cbbLevyModel.getSelectedItem());
		if (this.tfBomVersion.getText().trim().equals("")) {
			data.setBomVersion(null);
		} else {
			data.setBomVersion(Integer.parseInt(this.tfBomVersion.getText()
					.trim()));
		}
	}

	/**
	 * 将实体内的数据显示到窗口上
	 * 
	 * @param data
	 */
	private void showData(DzscBillListBeforeCommInfo data) {
		this.tfMaterielCode.setText(data.getMateriel().getPtNo());
		if (data.getAfterComInfo() != null) {
			this.tfEmsSerialNo.setText(data.getAfterComInfo().getEmsSerialNo()
					.toString());
			this.tfComplexCode.setText(data.getAfterComInfo().getComplex()
					.getCode());
			this.tfComplexName.setText(data.getAfterComInfo().getComplexName());
			this.tfComplexSpec.setText(data.getAfterComInfo().getComplexSpec());
			if (data.getAfterComInfo().getUnit() != null) {
				this.tfUnit.setText(data.getAfterComInfo().getUnit().getName());
			} else {
				this.tfUnit.setText("");
			}
			if (data.getAfterComInfo().getComplex() != null
					&& data.getAfterComInfo().getComplex().getFirstUnit() != null) {
				this.tfLegalUnit.setText((data.getAfterComInfo().getComplex()
						.getFirstUnit().getName()));
			} else {
				this.tfLegalUnit.setText("");
			}
			if (data.getAfterComInfo().getComplex() != null
					&& data.getAfterComInfo().getComplex().getSecondUnit() != null) {
				this.tfSecondLegalUnit.setText((data.getAfterComInfo()
						.getComplex().getSecondUnit().getName()));
			} else {
				this.tfSecondLegalUnit.setText("");
			}
		}
		this.tfDeclaredAmount.setValue(data.getDeclaredAmount());
		this.tfDeclaredPrice.setValue(data.getDeclaredPrice());
		this.tfSecondLegalAmount.setValue(data.getSecondLegalAmount());
		this.tfLegalAmount.setValue(data.getLegalAmount());
		this.cbbCountry.setSelectedItem(data.getSalesCountry());
		this.cbbCurrency.setSelectedItem(data.getCurrency());
		this.cbbUses.setSelectedItem(data.getUsesCode());
		this.cbbLevyModel.setSelectedItem(data.getLevyMode());
		this.tfGrossWeight.setValue(data.getGrossWeight());
		this.tfNetWeight.setValue(data.getNetWeight());
		this.tfMemo.setText(data.getMemos());
		if (data.getBomVersion() != null) {
			this.tfBomVersion.setText(data.getBomVersion().toString());
		} else {
			this.tfBomVersion.setText("");
		}
	}

	private boolean checkData() {
		if (MaterielType.FINISHED_PRODUCT.equals(beforeCommInfo
				.getAfterComInfo().getBillList().getMaterielProductFlag())) {
			if (this.tfBomVersion.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(DgDzscListCommInfo.this,
						"成品的版本号不能为空", "提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			try {
				Integer.parseInt(this.tfBomVersion.getText().trim());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(DgDzscListCommInfo.this,
						"成品的版本号必须是整数", "提示", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private void saveData() {
		fillData(beforeCommInfo);
		beforeCommInfo = dzscListAction.saveAtcMergeBeforeComInfo(new Request(
				CommonVars.getCurrUser()), beforeCommInfo);
		tableModel.updateRow(beforeCommInfo);
	}

	private void initUIComponents() {
		// 初始化货币
		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCurrency);
		this.cbbCurrency.setSelectedIndex(-1);
		// 产销国
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCountry);
		this.cbbCountry.setSelectedIndex(-1);
		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUses);
		this.cbbUses.setSelectedIndex(-1);
		// 初始化征免方式
		this.cbbLevyModel.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyModel.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyModel);
		this.cbbLevyModel.setSelectedIndex(-1);
		
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDeclaredAmount,
				new AutoCalcListener() {
					public void run() {
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						double unitPrice = (tfDeclaredPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredPrice.getValue()
										.toString()));
						double totalPrice = CommonVars.getDoubleByDigit(
								unitPrice * amount, 5);
						tfTotalPrice.setValue(totalPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfDeclaredPrice,
				new AutoCalcListener() {
					public void run() {
						double unitPrice = (tfDeclaredPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredPrice.getValue()
										.toString()));
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						double totalPrice = CommonVars.getDoubleByDigit(
								unitPrice * amount, 5);
						tfTotalPrice.setValue(totalPrice);
					}
				});

		CustomFormattedTextFieldUtils.addAutoCalcListener(tfTotalPrice,
				new AutoCalcListener() {
					public void run() {
						double totalPrice = (tfTotalPrice.getValue() == null ? 0.0
								: Double.parseDouble(tfTotalPrice
										.getValue().toString()));
						double amount = (tfDeclaredAmount.getValue() == null ? 0.0
								: Double.parseDouble(tfDeclaredAmount
										.getValue().toString()));
						if (amount == 0) {
							amount = 1.0;
						}
						double unitPrice = CommonVars.getDoubleByDigit(
								totalPrice / amount, 5);
						tfDeclaredPrice.setValue(unitPrice);
					}
				});
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBomVersion() {
		if (tfBomVersion == null) {
			tfBomVersion = new JTextField();
			tfBomVersion.setBounds(new Rectangle(378, 217, 164, 21));
		}
		return tfBomVersion;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new Rectangle(110, 189, 156, 21));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes tfDeclaredPrice1	
	 * 	
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField	
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new Rectangle(380, 162, 161, 23));
			tfTotalPrice.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTotalPrice;
	}
} // @jve:decl-index=0:visual-constraint="-29,5"
