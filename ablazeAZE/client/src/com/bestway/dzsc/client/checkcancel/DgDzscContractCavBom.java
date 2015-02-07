package com.bestway.dzsc.client.checkcancel;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscContractBomCav;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;

public class DgDzscContractCavBom extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JTextField tfSeqProductNum = null;

	private JLabel jLabel1 = null;

	private JTextField tfProductName = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JCustomFormattedTextField tfUnitWaste = null;

	private JTextField tfProductSpec = null;

	private JButton btnEdit = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JTableListModel tableModel = null;

	private DzscContractCavAction contractCavAction = null;

	private int dataState = DataState.BROWSE;

	private boolean isShowDataChange = false;

	private JLabel jLabel7 = null;

	private JTextField tfSeqMaterialNum = null;

	private JTextField tfMaterialName = null;

	private JTextField tfMaterialSpec = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel8 = null;

	private boolean modifyUnitWasteNotWriteBack;

	private boolean modifyTotalAmountNotWriteBack;

	private boolean modifyWasteAmountNotWriteBack;

	private JCustomFormattedTextField tfWaste = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel52 = null;

	public boolean isModifyTotalAmountNotWriteBack() {
		return modifyTotalAmountNotWriteBack;
	}

	public void setModifyTotalAmountNotWriteBack(
			boolean modifyTotalAmountNotWriteBack) {
		this.modifyTotalAmountNotWriteBack = modifyTotalAmountNotWriteBack;
	}

	public boolean isModifyUnitWasteNotWriteBack() {
		return modifyUnitWasteNotWriteBack;
	}

	public void setModifyUnitWasteNotWriteBack(
			boolean modifyUnitWasteNotWriteBack) {
		this.modifyUnitWasteNotWriteBack = modifyUnitWasteNotWriteBack;
	}

	public boolean isModifyWasteAmountNotWriteBack() {
		return modifyWasteAmountNotWriteBack;
	}

	public void setModifyWasteAmountNotWriteBack(
			boolean modifyWasteAmountNotWriteBack) {
		this.modifyWasteAmountNotWriteBack = modifyWasteAmountNotWriteBack;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscContractCavBom() {
		super();
		initialize();
		contractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.showData();
			this.setState();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(498, 238));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("单耗核销修改");
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState == DataState.EDIT) {
						saveData();
					} else {
						dataState = DataState.EDIT;
					}
					tableModel.previousRow();
					showData();
					setState();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState == DataState.EDIT) {
						saveData();
					} else {
						dataState = DataState.EDIT;
					}
					tableModel.nextRow();
					showData();
					setState();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
					dataState = DataState.BROWSE;
					showData();
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel52 = new JLabel();
			jLabel52.setBounds(new Rectangle(250, 121, 51, 15));
			jLabel52.setForeground(Color.blue);
			jLabel52.setText("损耗率%");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(291, 230, 36, 15));
			jLabel51.setText("\u635f\u8017\u91cf");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(246, 83, 56, 19));
			jLabel8.setText("料件规格");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(246, 54, 56, 19));
			jLabel3.setText("料件名称");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(246, 22, 56, 19));
			jLabel7.setText("料件序号");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(26, 123, 57, 19));
			jLabel4.setForeground(Color.blue);
			jLabel4.setText("单耗");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(26, 87, 57, 19));
			jLabel2.setText("成品规格");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(26, 54, 57, 19));
			jLabel1.setText("成品名称");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(26, 25, 57, 19));
			jLabel.setText("成品序号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfSeqProductNum(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfProductName(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getTfUnitWaste(), null);
			jPanel.add(getTfProductSpec(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfSeqMaterialNum(), null);
			jPanel.add(getTfMaterialName(), null);
			jPanel.add(getTfMaterialSpec(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel8, null);
			jPanel.add(getTfWaste(), null);
			jPanel.add(jLabel51, null);
			jPanel.add(jLabel52, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqProductNum() {
		if (tfSeqProductNum == null) {
			tfSeqProductNum = new JTextField();
			tfSeqProductNum.setBounds(new java.awt.Rectangle(82, 22, 150, 23));
			tfSeqProductNum.setEditable(false);
		}
		return tfSeqProductNum;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductName() {
		if (tfProductName == null) {
			tfProductName = new JTextField();
			tfProductName.setBounds(new java.awt.Rectangle(82, 53, 150, 23));
			tfProductName.setEditable(false);
		}
		return tfProductName;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitWaste() {
		if (tfUnitWaste == null) {
			tfUnitWaste = new JCustomFormattedTextField();
			tfUnitWaste.setBounds(new java.awt.Rectangle(82, 122, 150, 23));
			tfUnitWaste.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitWaste.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfUnitWaste.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}

						public void removeUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfUnitWaste.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}

						}

						public void changedUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfUnitWaste.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}
					});
		}
		return tfUnitWaste;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProductSpec() {
		if (tfProductSpec == null) {
			tfProductSpec = new JTextField();
			tfProductSpec.setBounds(new java.awt.Rectangle(82, 84, 150, 23));
			tfProductSpec.setEditable(false);
		}
		return tfProductSpec;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
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
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setMaximumFractionDigits(999);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	private DzscContractBomCav getContractBomCav() {
		return (DzscContractBomCav) tableModel.getCurrentRow();
	}

	private void fillData(DzscContractBomCav bomCav) {
		if (bomCav == null) {
			return;
		}
		bomCav.setUnitWaste(convertObjToDouble(this.tfUnitWaste.getValue()));
		bomCav.setWaste(convertObjToDouble(this.tfWaste.getValue()));
//		bomCav
//				.setWasteAmount(convertObjToDouble(this.tfWasteAmount
//						.getValue()));
//		bomCav
//				.setTotalAmount(convertObjToDouble(this.tfTotalAmount
//						.getValue()));
	}

	private double getDoubleExceptNull(Double num) {
		if (num == null) {
			return 0.0;
		}
		return num;
	}

	private double convertObjToDouble(Object obj) {
		if (obj == null) {
			return 0.0;
		}
		return Double.parseDouble(obj.toString());
	}

	private void calExpTotalAmount() {
		// ContractBomCav bomCav = this.getContractBomCav();
		// double exgAmount = (bomCav.getExgExpTotalAmount() == null ? 0.0
		// : bomCav.getExgExpTotalAmount());
		// double waste = (bomCav.getWaste() == null ? 0.0 : bomCav.getWaste());
		// double unitDosage = CommonVars.getDoubleByDigit(
		// convertObjToDouble(this.tfUnitWaste.getValue()) / (1 - waste),
		// 5);
		// double totalAmount = CommonVars.getDoubleByDigit(
		// exgAmount * unitDosage, 3);
		// double wasteAmount = CommonVars
		// .getDoubleByDigit(totalAmount * waste, 3);
		// this.tfWasteAmount.setValue(wasteAmount);
		// this.tfTotalAmount.setValue(totalAmount);
	}

	private void showData() {
		isShowDataChange = true;
		DzscContractBomCav bomCav = this.getContractBomCav();
		if (bomCav != null) {
			this.tfSeqProductNum.setText(bomCav.getSeqProductNum() == null ? ""
					: bomCav.getSeqProductNum().toString());
			this.tfProductName.setText(bomCav.getProductName());
			this.tfProductSpec.setText(bomCav.getProductSpec());
			this.tfSeqMaterialNum
					.setText(bomCav.getSeqMaterialNum() == null ? "" : bomCav
							.getSeqMaterialNum().toString());
			this.tfMaterialName.setText(bomCav.getMaterialName());
			this.tfMaterialSpec.setText(bomCav.getMaterialSpec());
//			this.tfWasteAmount.setValue(bomCav.getWasteAmount());
//			this.tfTotalAmount.setValue(bomCav.getTotalAmount());
			this.tfUnitWaste.setValue(bomCav.getUnitWaste());
		} else {
			this.tfSeqProductNum.setText("");
			this.tfProductName.setText("");
			this.tfProductSpec.setText("");
			this.tfSeqMaterialNum.setText("");
			this.tfMaterialName.setText("");
			this.tfMaterialSpec.setText("");
//			this.tfWasteAmount.setValue(null);
//			this.tfTotalAmount.setValue(null);
			this.tfUnitWaste.setValue(null);
		}
		isShowDataChange = false;
	}

	private void setState() {
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.tfUnitWaste.setEditable(dataState != DataState.BROWSE);
//		this.tfWasteAmount.setEditable(dataState != DataState.BROWSE);
//		this.tfTotalAmount.setEditable(dataState != DataState.BROWSE);
	}

	private void saveData() {
		DzscContractBomCav bomCav = this.getContractBomCav();
		this.fillData(bomCav);
		bomCav = this.contractCavAction.saveContractBomCav(new Request(
				CommonVars.getCurrUser()), bomCav,
				this.modifyUnitWasteNotWriteBack,
				this.modifyTotalAmountNotWriteBack,
				this.modifyWasteAmountNotWriteBack);
		tableModel.updateRow(bomCav);
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqMaterialNum() {
		if (tfSeqMaterialNum == null) {
			tfSeqMaterialNum = new JTextField();
			tfSeqMaterialNum
					.setBounds(new java.awt.Rectangle(303, 21, 157, 23));
			tfSeqMaterialNum.setEditable(false);
		}
		return tfSeqMaterialNum;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialName() {
		if (tfMaterialName == null) {
			tfMaterialName = new JTextField();
			tfMaterialName.setBounds(new java.awt.Rectangle(303, 53, 157, 24));
			tfMaterialName.setEditable(false);
		}
		return tfMaterialName;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMaterialSpec() {
		if (tfMaterialSpec == null) {
			tfMaterialSpec = new JTextField();
			tfMaterialSpec.setBounds(new java.awt.Rectangle(303, 84, 157, 21));
			tfMaterialSpec.setEditable(false);
		}
		return tfMaterialSpec;
	}

	/**
	 * This method initializes tfWasteAmount1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new JCustomFormattedTextField();
			tfWaste.setBounds(new Rectangle(305, 118, 155, 22));
			tfWaste.setFormatterFactory(getDefaultFormatterFactory());
			tfWaste.setEditable(true);
		}
		return tfWaste;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
