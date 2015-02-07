/*
 * Created on 2005-3-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Color;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

/**
 * @author // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgUnitWaste extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfSeqNum = null;

	private JTextField tfName = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JTextField tfComplex = null; // @jve:decl-index=0:

	private JFormattedTextField tfWaste = null;

	private JFormattedTextField tfUnitPrice = null;

	private JFormattedTextField tfUnitCost = null;

	private JTableListModel tableModel = null;

	private int dataState = -1;

	private ContractAction contractAction = null; // 合同

	private ContractBom contractBom = null; // 合同成品对象

	private JLabel jLabel8 = null;

	private JFormattedTextField tfAmount = null;

	private JFormattedTextField tfMaterielAmount = null;

	private JLabel jLabel14 = null;

	private JTextField tfMemo = null;

	private JFormattedTextField tfUnitDosage = null;

	private JFormattedTextField tfTotalMoney = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel11 = null;

	private JPanel jPanel = null;

	private JCheckBox cbCalculateFinishProductSum = null;

	private JCheckBox cbCalculateMaterielSum = null;

	private JTableListModel tableModelExg = null;

	private Complex complex = null;

	private ContractExg contractExg = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JTextField tfLegalUnit = null;

	private JFormattedTextField tfLegalAmount = null;

	private JButton btnEdit = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	public Contract contract = null; // @jve:decl-index=0:

	private JButton btnCancel = null;

	private boolean isEditable = false;

	private boolean isShowDataChange = false;

	private JLabel jLabel61 = null;

	private JLabel jLabel121 = null;

	private JLabel jLabel611 = null;

	private JLabel jLabel51 = null;

	private JFormattedTextField tfnonMnlRatio = null;

	private JLabel jLabel1211 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	public void setIsEditable(boolean is) {
		this.isEditable = is;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgUnitWaste() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("单耗修改");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(520, 425);

	}

	public void setVisible(boolean b) {
		if (b) {
			if (this.dataState == DataState.EDIT) {
				this.contractBom = (ContractBom) this.tableModel
						.getCurrentRow();
			}
			showData();
			setState();
		}
		super.setVisible(b);

	}

	/**
	 * @return Returns the contractExg.
	 */
	public ContractExg getContractExg() {
		return contractExg;
	}

	/**
	 * @param contractExg
	 *            The contractExg to set.
	 */
	public void setContractExg(ContractExg contractExg) {
		this.contractExg = contractExg;
	}

	/**
	 * @return Returns the tableModelExg.
	 */
	public JTableListModel getTableModelExg() {
		return tableModelExg;
	}

	/**
	 * @param tableModelExg
	 *            The tableModelExg to set.
	 */
	public void setTableModelExg(JTableListModel tableModelExg) {
		this.tableModelExg = tableModelExg;
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
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(42, 361, 427, 25));
			jLabel16.setText("若单耗/净耗栏申报内容为单耗，则不得重复申报损耗率数据，损耗率栏应为空");
			jLabel16.setForeground(java.awt.Color.red);
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(42, 339, 427, 25));
			jLabel15.setText("若单耗/净耗栏申报内容为净耗，则需申报相应损耗率数据");
			jLabel15.setForeground(java.awt.Color.red);
			jLabel1211 = new JLabel();
			jLabel1211.setBounds(new Rectangle(472, 200, 11, 18));
			jLabel1211.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1211.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel1211.setText("%");
			jLabel1211.setForeground(Color.blue);
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(237, 201, 101, 18));
			jLabel51.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel51.setText("非保税料件比例");
			jLabel51.setForeground(java.awt.Color.blue);
			jLabel51.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel611 = new JLabel();
			jLabel611.setBounds(new Rectangle(42, 311, 427, 25));
			jLabel611.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel611.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel611.setText("净耗最多允许保留9位小数，损耗率最多允许保留5位小数");
			jLabel611.setForeground(Color.blue);
			jLabel121 = new JLabel();
			jLabel121.setBounds(new Rectangle(469, 168, 13, 20));
			jLabel121.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel121.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel121.setText("%");
			jLabel121.setForeground(Color.blue);
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(42, 285, 427, 25));
			jLabel61.setHorizontalAlignment(SwingConstants.LEFT);
			jLabel61.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel61.setText("如果此料件的损耗是2%的话，那么在此损耗栏位填写的是2");
			jLabel61.setForeground(Color.blue);
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel11 = new JLabel();
			jLabel3 = new JLabel();
			jLabel14 = new JLabel();
			jLabel8 = new JLabel();
			jContentPane = new JPanel();
			jLabel = new JLabel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel4 = new JLabel();
			jLabel5 = new JLabel();
			jLabel6 = new JLabel();
			jLabel7 = new JLabel();
			jLabel9 = new JLabel();
			jLabel10 = new JLabel();
			jLabel12 = new JLabel();
			jLabel13 = new JLabel();
			jContentPane.setLayout(null);
			jLabel.setBounds(20, 63, 75, 23);
			jLabel.setText("序号");
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(20, 92, 75, 23);
			jLabel1.setText("商品名称");
			jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setBounds(22, 142, 75, 23);
			jLabel2.setText("单价");
			jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(261, 62, 75, 23);
			jLabel4.setText("商品编码");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setBounds(20, 195, 75, 23);
			jLabel5.setText("单量用量");
			jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(21, 168, 75, 23);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setText("单耗/净耗");
			jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(22, 117, 75, 23);
			jLabel7.setText("型号规格");
			jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(421, 382, 75, 23);
			jLabel9.setText("单位净重");
			jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setVisible(false);
			jLabel10.setBounds(261, 115, 75, 23);
			jLabel10.setText("料件数量");
			jLabel10
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setBounds(260, 167, 75, 23);
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel12.setText("损耗率");
			jLabel12
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setBounds(261, 143, 75, 23);
			jLabel13.setText("料件总金额");
			jLabel13
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(172, 409, 75, 23);
			jLabel8.setText("成品数量");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setVisible(false);
			jLabel14.setBounds(22, 229, 75, 23);
			jLabel14.setText("备注");
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setBounds(42, 259, 190, 25);
			jLabel3.setText("单量用量=净耗 / (1-损耗/100)");
			jLabel3.setForeground(new java.awt.Color(227, 145, 0));
			jLabel11.setBounds(284, 259, 186, 25);
			jLabel11.setText("料件数量=单量用量 * 成品数量");
			jLabel11.setForeground(new java.awt.Color(227, 145, 0));
			jLabel17.setBounds(172, 353, 75, 23);
			jLabel17.setText("法定单位");
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setVisible(false);
			jLabel18.setBounds(423, 355, 75, 23);
			jLabel18.setText("法定数量");
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel18.setVisible(false);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfWaste(), null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getTfUnitCost(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getTfMaterielAmount(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(getTfUnitDosage(), null);
			jContentPane.add(getTfTotalMoney(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getTfLegalUnit(), null);
			jContentPane.add(getTfLegalAmount(), null);
			jContentPane.add(jLabel61, null);
			jContentPane.add(jLabel121, null);
			jContentPane.add(jLabel611, null);
			jContentPane.add(jLabel51, null);
			jContentPane.add(getTfnonMnlRatio(), null);
			jContentPane.add(jLabel1211, null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel16, null);
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
			jToolBar.setBounds(4, 2, 532, 33);
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJPanel());
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
					if (validateData() == true) {
						saveData();
						dataState = DataState.BROWSE;
						setState();
						// dataState = DataState.BROWSE;
						// setState();
					}
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgUnitWaste.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tfCredenceNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(98, 63, 133, 23);
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

	/**
	 * This method initializes tfName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setEditable(false);
			tfName.setBounds(98, 92, 373, 23);
			// tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setEditable(false);
			tfSpec.setBounds(98, 117, 133, 23);
			// tfSpec.setEditable(false);
		}
		return tfSpec;
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
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(getCbCalculateFinishProductSum(), null);
			jPanel.add(getCbCalculateMaterielSum(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(339, 62, 133, 23);
			tfComplex.setEditable(false);
		}
		return tfComplex;
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWaste.setBounds(339, 167, 133, 23);
			tfWaste.setValue(new Double(0));
		}
		return tfWaste;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(98, 142, 133, 23);
			tfUnitPrice.setValue(new Double(0));
			tfUnitPrice.setEditable(false);
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setBounds(250, 409, 133, 23);
			tfAmount.setValue(new Double(0));
			tfAmount.setEditable(false);
			tfAmount.setVisible(false);
		}
		return tfAmount;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMaterielAmount() {
		if (tfMaterielAmount == null) {
			tfMaterielAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMaterielAmount.setBounds(339, 115, 133, 23);
			tfMaterielAmount.setEditable(false);
			tfMaterielAmount.setValue(new Double(0));
		}
		return tfMaterielAmount;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(98, 229, 374, 25);
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitCost() {
		if (tfUnitCost == null) {
			tfUnitCost = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitCost.setBounds(98, 168, 133, 23);
			tfUnitCost.setValue(new Double(0));
		}
		return tfUnitCost;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCalculateFinishProductSum() {
		if (cbCalculateFinishProductSum == null) {
			cbCalculateFinishProductSum = new JCheckBox();
			cbCalculateFinishProductSum.setBounds(4, 2, 128, 22);
			cbCalculateFinishProductSum.setText("自动计算成品总额");
			cbCalculateFinishProductSum.setSelected(true);
			cbCalculateFinishProductSum.setVisible(false);
		}
		return cbCalculateFinishProductSum;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBoxf
	 */
	private JCheckBox getCbCalculateMaterielSum() {
		if (cbCalculateMaterielSum == null) {
			cbCalculateMaterielSum = new JCheckBox();
			cbCalculateMaterielSum.setBounds(128, 2, 131, 21);
			cbCalculateMaterielSum.setText("自动计算料件总额");
			cbCalculateMaterielSum.setSelected(true);
			cbCalculateMaterielSum.setVisible(false);
		}
		return cbCalculateMaterielSum;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalMoney() {
		if (tfTotalMoney == null) {
			tfTotalMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalMoney.setBounds(339, 143, 133, 23);
			tfTotalMoney.setValue(new Double(0));
			tfTotalMoney.setEditable(false);
		}
		return tfTotalMoney;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitDosage() {
		if (tfUnitDosage == null) {
			tfUnitDosage = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitDosage.setBounds(98, 195, 133, 23);
			tfUnitDosage.setEditable(false);
			tfUnitDosage.setValue(new Double(0));
		}
		return tfUnitDosage;
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 这里是控制焦点的顺序，以方便键盘的输！
		List components = new ArrayList();
		components.add(this.tfUnitCost);
		components.add(null);
		components.add(this.tfMemo);
		components.add(this.btnSave);
		components.add(this.btnNext);
		this.setComponentFocusList(components);

		// 净耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitCost, 9);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfUnitCost,
				new AutoCalcListener() {
					public void run() {
						setUnitDosage();
						setMaterielAmount();
						setTotalPrice();
					}
				});
		// 损耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfWaste, 5);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfWaste,
				new AutoCalcListener() {
					public void run() {
						setUnitDosage();
						setMaterielAmount();
						setTotalPrice();
					}
				});
		// 单耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitDosage, 9);
		// 料件数量
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfMaterielAmount, 5);
		// 单价
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice, 5);
		// 金额
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfTotalMoney, 5);
		/**
		 * 非保税料件比例
		 */
		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfnonMnlRatio, 5);
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		isShowDataChange = true;
		this.contractBom = (ContractBom) this.tableModel.getCurrentRow();
		if (this.contractBom == null) {
			this.tfSeqNum.setText(String.valueOf(this.contractAction
					.getMaxContractBomSeqNum(new Request(CommonVars
							.getCurrUser()), contractExg.getId()) + 1));
			// 成品数量
			if (this.contractExg.getExportAmount() != null) {
				this.tfAmount.setValue(this.contractExg.getExportAmount());
			} else {
				this.tfAmount.setValue(new Double(0));
			}
			return;
		}

		// 序号
		if (this.contractBom.getSeqNum() != null) {
			this.tfSeqNum.setText(this.contractBom.getSeqNum().toString());
		} else {
			this.tfSeqNum.setText("");
		}
		// 物料名称
		if (this.contractBom.getName() != null) {
			this.tfName.setText(this.contractBom.getName());
		} else {
			this.tfName.setText("");
		}

		// 物料规格
		if (this.contractBom.getSpec() != null) {
			this.tfSpec.setText(this.contractBom.getSpec());
		} else {
			this.tfSpec.setText("");
		}
		// 商品编码
		if (this.contractBom.getComplex() != null) {
			this.tfComplex.setText(this.contractBom.getComplex().getCode());
			this.complex = this.contractBom.getComplex();
		} else {
			this.tfComplex.setText("");
		}
		// 第一法定单位
		if (this.contractBom.getComplex() != null
				&& this.contractBom.getComplex().getFirstUnit() != null) {
			this.tfLegalUnit.setText(this.contractBom.getComplex()
					.getFirstUnit().getName());
		} else {
			this.tfLegalUnit.setText("");
		}
		// 法定数量
		if (this.contractBom.getLegalAmount() != null) {
			this.tfLegalAmount.setValue(this.contractBom.getLegalAmount());
		} else {
			this.tfLegalAmount.setValue(new Double(0));
		}
		// 损耗
		if (this.contractBom.getWaste() != null) {
			this.tfWaste.setValue(this.contractBom.getWaste());
		} else {
			this.tfWaste.setValue(new Double(0));
		}
		// 非保税料件比例
		if (this.contractBom.getNonMnlRatio() != null) {
			this.tfnonMnlRatio.setValue(this.contractBom.getNonMnlRatio());
		} else {
			this.tfnonMnlRatio.setValue(new Double(0));
		}
		// 料件数量
		if (this.contractBom.getAmount() != null) {
			this.tfMaterielAmount.setValue(this.contractBom.getAmount());
		} else {
			this.tfMaterielAmount.setValue(new Double(0));
		}
		// 申报单价
		if (this.contractBom.getDeclarePrice() != null) {
			this.tfUnitPrice.setValue(this.contractBom.getDeclarePrice());
		} else {
			this.tfUnitPrice.setValue(new Double(0));
		}
		// 单位用量
		if (this.contractBom.getUnitDosage() != null) {
			this.tfUnitDosage.setValue(this.contractBom.getUnitDosage());
		} else {
			this.tfUnitDosage.setValue(new Double(0));
		}
		// 单耗
		if (this.contractBom.getUnitWaste() != null) {
			this.tfUnitCost.setValue(this.contractBom.getUnitWaste());
		} else {
			this.tfUnitCost.setValue(new Double(0));
		}

		// 成品数量
		if (this.contractBom.getContractExg().getExportAmount() != null) {
			this.tfAmount.setValue(this.contractBom.getContractExg()
					.getExportAmount());
		} else {
			this.tfAmount.setValue(new Double(0));
		}

		// 料件数量
		if (this.contractBom.getAmount() != null) {
			this.tfMaterielAmount.setValue(this.contractBom.getAmount());
		} else {
			this.tfMaterielAmount.setValue(new Double(0));
		}
		// 料件总金额
		if (this.contractBom.getTotalPrice() != null) {
			this.tfTotalMoney.setValue(this.contractBom.getTotalPrice());
		} else {
			this.tfTotalMoney.setValue(new Double(0));
		}

		if (this.contractBom.getNote() != null) {
			this.tfMemo.setText(this.contractBom.getNote());
		} else {
			this.tfMemo.setText(null);
		}
		isShowDataChange = false;
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		if (this.contractBom == null) {
			contractBom = new ContractBom();
			contractBom.setCompany(CommonVars.getCurrUser().getCompany());
			contractBom.setContractExg(this.contractExg);
			contractBom
					.setSeqNum(this.contractAction.getMaxContractBomSeqNum(
							new Request(CommonVars.getCurrUser()), contractExg
									.getId()) + 1);
		}
		// 商品编码
		this.contractBom.setComplex(complex);
		// 商品名称
		contractBom.setName(this.tfName.getText());
		// 商品规格
		contractBom.setSpec(this.tfSpec.getText());
		// 第一法定单位
		// this.contractBom.setLegalUnit(complex.getFirstUnit());

		// 法定数量
		this.contractBom
				.setLegalAmount(getFormattedTextFieldValue(this.tfLegalAmount));
		// 单耗
		this.contractBom
				.setUnitWaste(getFormattedTextFieldValue(this.tfUnitCost));
		// 损耗
		this.contractBom.setWaste(getFormattedTextFieldValue(this.tfWaste));
		// 单项用量
		this.contractBom
				.setUnitDosage(getFormattedTextFieldValue(this.tfUnitDosage));
		// 料件数量
		this.contractBom
				.setAmount(getFormattedTextFieldValue(this.tfMaterielAmount));
		// 单价
		this.contractBom
				.setDeclarePrice(getFormattedTextFieldValue(this.tfUnitPrice));
		// 总金额
		this.contractBom
				.setTotalPrice(getFormattedTextFieldValue(this.tfTotalMoney));
		// 非保税料件比例%
		this.contractBom
				.setNonMnlRatio(getFormattedTextFieldValue(this.tfnonMnlRatio));
		// 备注
		this.contractBom.setNote(this.tfMemo.getText());
		// if
		// (ModifyMarkState.UNCHANGE.equals(this.contractBom.getModifyMark())) {
		// this.contractBom.setModifyMark(ModifyMarkState.MODIFIED);
		// }
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData() {
		if (this.dataState == DataState.ADD) {// 新增
			this.fillData();
			contractBom = this.contractAction.saveContractBom(new Request(
					CommonVars.getCurrUser()), this.contractBom);
			this.tableModel.addRow(contractBom);
			this.tableModelExg.updateRow(contractBom.getContractExg());

		} else if (this.dataState == DataState.EDIT) { // 修改
			this.fillData();
			contractBom = this.contractAction.saveContractBom(new Request(
					CommonVars.getCurrUser()), this.contractBom);
			this.tableModelExg.updateRow(contractBom.getContractExg());
			this.tableModel.updateRow(contractBom);
		}
		// DgUnitWaste.this.dispose();
	}

	private double getFormattedTextFieldValue(JFormattedTextField tf) {
		return CustomFormattedTextFieldUtils.getFormattedTextFieldValue(tf);
	}

	/**
	 * 验证数据
	 * 
	 * @return
	 */
	private boolean validateData() {
		if (Double.valueOf(this.tfnonMnlRatio.getText()).doubleValue() >=100
				|| Double.valueOf(this.tfnonMnlRatio.getText()).doubleValue() < 0) {
			JOptionPane.showMessageDialog(this, "非保税料件比例范围大于等于0且小于100", "警告！",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// if (this.complex == null) {
		// JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// if (this.cbbMaterielType.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "主料辅料不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// if (this.cbbUnit.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "单位不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// if (this.cbbCountry.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "产销国不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		return true;
	}

	/**
	 * 设置料件总金额
	 */
	private void setTotalPrice() {
		double exgAmount = contractBom.getContractExg().getExportAmount() == null ? 0
				: contractBom.getContractExg().getExportAmount();
		double unitDosage = getFormattedTextFieldValue(this.tfUnitCost)
				/ (1 - Double.parseDouble(this.tfWaste.getValue().toString()) / 100.0);
		double amount = unitDosage * exgAmount;
		double totalPrice = amount * getFormattedTextFieldValue(tfUnitPrice);
		tfTotalMoney.setValue(CommonVars.getDoubleByDigit(totalPrice, 5));
	}

	/**
	 * 设置料件单项用量
	 */
	private void setUnitDosage() {
		double waste = getFormattedTextFieldValue(tfWaste);
		if (waste >= 100) {
			JOptionPane.showMessageDialog(this, "损耗不能大于100，请重新输入!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			this.tfWaste.setValue(new Double(0));
			return;
		}
		double amount = getFormattedTextFieldValue(this.tfUnitCost)
				/ (1 - waste / 100.0);
		this.tfUnitDosage.setValue(CommonVars.getDoubleByDigit(amount, 9));
	}

	/**
	 * 设置料件总数
	 */
	private void setMaterielAmount() {
		double exgAmount = contractBom.getContractExg().getExportAmount() == null ? 0
				: contractBom.getContractExg().getExportAmount();
		double unitDosage = getFormattedTextFieldValue(this.tfUnitCost)
				/ (1 - getFormattedTextFieldValue(this.tfWaste) / 100.0);
		double amount = unitDosage * exgAmount;
		this.tfMaterielAmount.setValue(CommonVars.getDoubleByDigit(amount, 5));
	}

	// /**
	// * 通过单项用量反算单耗
	// */
	// private void setUnitCost() {
	// try {
	// double waste = Double.parseDouble(tfWaste.getValue().toString());
	//
	// if (waste >= 1) {
	// JOptionPane.showMessageDialog(this, "损耗不能大于1，请重新输入!!!", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// this.tfWaste.setValue(new Double(0));
	// return;
	// }
	// double amount = Double.parseDouble(this.tfUnitDosage.getValue()
	// .toString())
	// * (1 - waste);
	// BigDecimal bd = new BigDecimal(amount);
	// double unitCost = Double.parseDouble(tfUnitCost.getValue()
	// .toString());
	// this.tfUnitCost.setValue(bd.setScale(6, BigDecimal.ROUND_HALF_UP)
	// .doubleValue());
	// } catch (Exception ex) {
	// }
	//
	// }

	// /**
	// * This method initializes jComboBox
	// *
	// * @return javax.swing.JComboBox
	// */
	// private JComboBox getCbbCountry() {
	// if (cbbCountry == null) {
	// cbbCountry = new JComboBox();
	// cbbCountry.setBounds(250, 324, 133, 23);
	// cbbCountry.setVisible(false);
	// }
	// return cbbCountry;
	// }

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(250, 353, 133, 23);
			tfLegalUnit.setEditable(false);
			tfLegalUnit.setVisible(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setBounds(500, 355, 133, 23);
			tfLegalAmount.setValue(new Double(0));
			tfLegalAmount.setVisible(false);
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes btnEdit
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
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (validateData() == true) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					// tableModel.previousRow();
					showData();
					dataState = DataState.EDIT;
					setState();
					// }
				}
			});

		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (validateData() == true) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					// tableModel.nextRow();
					showData();
					dataState = DataState.EDIT;
					setState();
					// }
				}
			});
		}
		return btnNext;
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
					dataState = DataState.BROWSE;
					showData();
					setState();
				}
			});
		}
		return btnCancel;
	}

	private void setState() {
		// boolean isChanging = false;
		// if (this.contract != null) {
		// if (DeclareState.CHANGING_EXE.equals(this.contract
		// .getDeclareState())) {
		// isChanging = true;
		// }
		// }
		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(this.tableModel.hasPreviousRow());
		this.btnNext.setEnabled(this.tableModel.hasNextRow());
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		this.tfUnitCost.setEditable(this.dataState != DataState.BROWSE);
		this.tfWaste.setEditable(this.dataState != DataState.BROWSE);
		// this.tfUnitDosage.setEditable(this.dataState != DataState.BROWSE);
		// this.tfUnitPrice.setEditable(this.dataState != DataState.BROWSE);
		this.tfMemo.setEditable(this.dataState != DataState.BROWSE);
		this.tfnonMnlRatio.setEditable(this.dataState != DataState.BROWSE);
		// this.tfMaterielAmount.setEditable(this.dataState !=
		// DataState.BROWSE);
		// tfName.setEditable(((!isChanging) || this.isEditable)
		// && (dataState != DataState.BROWSE));
		// tfSpec.setEditable(((!isChanging) || this.isEditable)
		// && (dataState != DataState.BROWSE));

	}

	/**
	 * This method initializes tfnonMnlRatio
	 * 
	 * @return javax.swing.JTextField
	 */
	private JFormattedTextField getTfnonMnlRatio() {
		if (tfnonMnlRatio == null) {
			tfnonMnlRatio = new JFormattedTextField();
			tfnonMnlRatio.setBounds(new Rectangle(339, 200, 133, 23));
		}
		return tfnonMnlRatio;
	}
} // @jve:decl-index=0:visual-constraint="-8,3"
