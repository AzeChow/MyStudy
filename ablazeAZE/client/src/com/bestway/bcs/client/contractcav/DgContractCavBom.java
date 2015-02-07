package com.bestway.bcs.client.contractcav;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class DgContractCavBom extends JDialogBase {

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
	private JCustomFormattedTextField tfSunMeasure = null;
	private JTextField tfProductSpec = null;

	private JButton btnEdit = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JTableListModel tableModel = null;

	private ContractCavAction contractCavAction = null;

	private ContractAction contractAction = null;

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
	private boolean modifyWasteAmount;
	private boolean modifyWasteAmountTwo;
	private JLabel jLabel41 = null;

	public boolean isModifyWasteAmount() {
		return modifyWasteAmount;
	}

	public void setModifyWasteAmount(boolean modifyWasteAmount,boolean modifyWasteAmountTwo) {
		this.modifyWasteAmount = modifyWasteAmount;
		this.modifyWasteAmountTwo = modifyWasteAmountTwo;
		if(!this.modifyWasteAmount){
			tfWasteAmount.setEnabled(false);
			tfSunMeasure.setEnabled(true);
		}else{
			tfWasteAmount.setEnabled(true);
			tfSunMeasure.setEnabled(false);
		}
	}

	private JCustomFormattedTextField tfWaste = null;

	private JLabel jLabel61 = null;
	
	private JTextField textField = null;
	
	private JTextField tfExgExpTotalAmount = null;
	
	private JLabel jlblexgExpTotalAmount = null;
	private JCustomFormattedTextField tfWasteAmount;
	
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
	public DgContractCavBom() {
		super();
		initialize();
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		// 这里是控制焦点的顺序，以方便键盘的输！
		List components = new ArrayList();
		components.add(this.tfUnitWaste);
		components.add(this.tfWaste);
		components.add(this.btnSave);
		components.add(this.btnNext);
		components.add(this.tfSunMeasure);
		this.setComponentFocusList(components);

		// 单耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitWaste, 5);
		// 损耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfWaste, 5);
		//总用量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfSunMeasure, 5);
		//耗用量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfWasteAmount, 5);
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
		this.setSize(new Dimension(498, 296));
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(26, 208, 431, 22));
			jLabel61.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel61.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel61.setText("\u5982\u679c\u6b64\u6599\u4ef6\u7684\u635f\u8017\u662f5%\u7684\u8bdd\uff0c\u90a3\u4e48\u5728\u6b64\u635f\u8017\u680f\u4f4d\u586b\u5199\u7684\u662f5");
			jLabel61.setForeground(Color.blue);
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(245, 115, 51, 21));
			jLabel41.setForeground(Color.blue);
			jLabel41.setText("损耗%");
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
			jLabel4.setBounds(new Rectangle(26, 116, 57, 19));
			jLabel4.setForeground(Color.blue);
			jLabel4.setText("净耗");
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
			jPanel.add(getJlblSunMeasure(),null);
			jPanel.add(getTfProductSpec(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfSeqMaterialNum(), null);
			jPanel.add(getTfMaterialName(), null);
			jPanel.add(getTfMaterialSpec(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel41, null);
			jPanel.add(getTfWaste(), null);
			jPanel.add(jLabel61, null);
			
			JLabel JlblSunMeasure = new JLabel();
			JlblSunMeasure.setText("总用量");
			JlblSunMeasure.setForeground(Color.BLUE);
			JlblSunMeasure.setBounds(new Rectangle(26, 123, 57, 19));
			JlblSunMeasure.setBounds(26, 145, 57, 19);
			jPanel.add(JlblSunMeasure);
			
			jlblexgExpTotalAmount = new JLabel();
			jlblexgExpTotalAmount.setText("成品数量");
			jlblexgExpTotalAmount.setBounds(new Rectangle(246, 83, 56, 19));
			jlblexgExpTotalAmount.setBounds(246, 149, 56, 19);
			jPanel.add(jlblexgExpTotalAmount);
			
			
			jPanel.add(gettfExgExpTotalAmount(),null);
			
			tfWasteAmount = new JCustomFormattedTextField();
			
			tfWasteAmount.setBounds(82, 177, 150, 21);
			jPanel.add(tfWasteAmount);
			tfWasteAmount.setColumns(10);
			
			JLabel label = new JLabel("损耗量");
			label.setForeground(Color.BLUE);
			label.setBounds(26, 183, 54, 15);
			jPanel.add(label);
			
			
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
			tfUnitWaste.setBounds(new Rectangle(82, 114, 150, 23));
		}
		return tfUnitWaste;
	}
	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	public JTextField gettfExgExpTotalAmount(){
		if(tfExgExpTotalAmount==null){
			tfExgExpTotalAmount = new JTextField();
			tfExgExpTotalAmount.setEditable(false);
			tfExgExpTotalAmount.setBounds(new Rectangle(303, 148, 157, 21));
		}
		return tfExgExpTotalAmount;
	}
	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	public JCustomFormattedTextField getJlblSunMeasure(){
		if(tfSunMeasure==null){
			tfSunMeasure = new JCustomFormattedTextField();
			tfSunMeasure.setBounds(new Rectangle(82, 145, 150, 23));
		}
		return tfSunMeasure;
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

	private ContractBomCav getContractBomCav() {
		return (ContractBomCav) tableModel.getCurrentRow();
	}

	private void fillData(ContractBomCav bomCav) {
		if (bomCav == null) {
			return;
		}
		bomCav.setUnitWaste(convertObjToDouble(this.tfUnitWaste.getValue()));
		bomCav.setWaste(convertObjToDouble(this.tfWaste.getValue()));
		bomCav.setTotalAmount(convertObjToDouble(this.tfSunMeasure.getValue()));
		bomCav.setWasteAmount(Double.parseDouble(tfWasteAmount.getText()));
		// bomCav
		// .setTotalAmount(convertObjToDouble(this.tfTotalAmount
		// .getValue()));
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
		ContractBomCav bomCav = this.getContractBomCav();
		if (bomCav != null) {
			this.tfSeqProductNum.setText(bomCav.getSeqProductNum() == null ? ""
					: bomCav.getSeqProductNum().toString());
			this.tfProductName.setText(bomCav.getProductName());
			this.tfProductSpec.setText(bomCav.getProductSpec());
			this.tfSeqMaterialNum.setText(bomCav.getSeqMaterialNum() == null ? ""
					: bomCav.getSeqMaterialNum().toString());
			this.tfMaterialName.setText(bomCav.getMaterialName());
			this.tfMaterialSpec.setText(bomCav.getMaterialSpec());
			// this.tfWasteAmount.setValue(bomCav.getWasteAmount());
			// this.tfTotalAmount.setValue(bomCav.getTotalAmount());
			this.tfWasteAmount.setText(bomCav.getWasteAmount().toString());
			this.tfUnitWaste.setValue(bomCav.getUnitWaste());
			this.tfWaste.setValue(bomCav.getWaste());
			this.tfSunMeasure.setValue(bomCav.getTotalAmount());
			this.tfExgExpTotalAmount.setText(bomCav.getExgExpTotalAmount() == null ? ""
					:  bomCav.getExgExpTotalAmount().toString());
		} else {
			this.tfSeqProductNum.setText("");
			this.tfProductName.setText("");
			this.tfProductSpec.setText("");
			this.tfSeqMaterialNum.setText("");
			this.tfMaterialName.setText("");
			this.tfMaterialSpec.setText("");
			// this.tfWasteAmount.setValue(null);
			// this.tfTotalAmount.setValue(null);
			this.tfUnitWaste.setValue(null);
			this.tfWaste.setValue(null);
			this.tfSunMeasure.setValue(null);
			this.tfExgExpTotalAmount.setText("");
			this.tfWasteAmount.setText("");
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
		this.tfWaste.setEditable(dataState != DataState.BROWSE);
		this.tfSunMeasure.setEditable(dataState != DataState.BROWSE);
		this.tfWasteAmount.setEditable(dataState != DataState.BROWSE);
		// this.tfTotalAmount.setEditable(dataState != DataState.BROWSE);
	}

	private void saveData() {
		ContractBomCav bomCav = this.getContractBomCav();
		
		this.fillData(bomCav);
		bomCav = this.contractCavAction.saveContractBomCav(new Request(
				CommonVars.getCurrUser()), bomCav,
				this.modifyUnitWasteNotWriteBack,
				this.modifyTotalAmountNotWriteBack,
				this.modifyWasteAmountNotWriteBack, this.modifyWasteAmountTwo);
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
			tfSeqMaterialNum.setBounds(new java.awt.Rectangle(303, 21, 157, 23));
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
	 * This method initializes tfWaste
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new JCustomFormattedTextField();
			tfWaste.setBounds(new Rectangle(303, 114, 157, 24));
		}
		return tfWaste;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
