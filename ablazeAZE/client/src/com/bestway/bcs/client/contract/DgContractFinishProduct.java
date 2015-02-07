/*
 * Created on 2005-3-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DocumentControlByGbkByte;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DutyRateType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Point;
import java.awt.Dimension;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgContractFinishProduct extends JDialogBase {
	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JTextField tfSeqNum = null;

	private JTextField tfName = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbLevyMode = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JComboBox cbbCountry = null;

	private JLabel jLable = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JTextField tfComplex = null;

	private JFormattedTextField tfUnitGrossWeight = null;

	private JFormattedTextField tfUnitPrice = null;

	private JFormattedTextField tfUnitNetWeight = null;

	private JFormattedTextField tfProcessUnitPrice = null;

	private JButton btnNetGrossWeightCalculate = null;

	private JButton btnUnitPrice = null;

	private JTableListModel tableModel = null;

	private int dataState = DataState.BROWSE;

	private ContractAction contractAction = null; // 合同

	private ContractExg contractExg = null; // 合同成品对象

	private JLabel jLabel8 = null;

	private JFormattedTextField tfAmount = null;

	private JTextField tfLegalUnit = null;

	private JFormattedTextField tfCredenceNo = null;

	private JLabel jLabel14 = null;

	private JTextField tfMemo = null;

	private JFormattedTextField tfMaterielFee = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JFormattedTextField tfTotalPrice = null;

	private JFormattedTextField tfProcessTotalPrice = null;

	private JLabel jLabel9 = null;

	private JComboBox cbbUnit = null;

	private JLabel jLabel17 = null;

	private JFormattedTextField tfLegalAmount = null;

	private Complex complex = null;

	private Contract contract = null; // @jve:decl-index=0:

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnCancel = null;

	private JButton btnEdit = null;

	private JButton btnComplex = null;

	private BcsParameterSet parameterSet = null; // @jve:decl-index=0:

	private JLabel jLabel18 = null;

	private JTextField tfModeNote = null;

	private JLabel jLabel101 = null;

	private JLabel jLabel1011 = null;

	private JCustomFormattedTextField tflegalUnitGene = null;

	private JCustomFormattedTextField tflegalUnit2Gene = null;

	private JLabel jLabel1012 = null;

	private JLabel jLabel10121 = null;

	private JTextField tfLegalUnit2 = null;

	private JLabel jLabel19 = null;

	private JComboBox cbbDutyRate = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgContractFinishProduct() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		initialize();
		initUIComponents();
		if (parameterSet.getIsControlLength() != null
				&& parameterSet.getIsControlLength()) {
			tfName.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength()));
			tfSpec.setDocument(new DocumentControlByGbkByte(parameterSet
					.getBytesLength()));
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("成品修改");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(588, 395);

	}

	public void setVisible(boolean b) {
		if (b) {
			if (this.dataState == DataState.EDIT) {
				this.contractExg = (ContractExg) this.tableModel
						.getCurrentRow();
			}
			showData();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
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
			jLabel19 = new JLabel();
			jLabel19.setBounds(new Rectangle(269, 303, 96, 18));
			jLabel19.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel19.setText("申报状态");
			jLabel19.setForeground(java.awt.Color.blue);
			jLabel10121 = new JLabel();
			jLabel10121.setBounds(new Rectangle(16, 279, 84, 18));
			jLabel10121.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel10121.setText("第二法定单位");
			jLabel10121.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1012 = new JLabel();
			jLabel1012.setBounds(new Rectangle(14, 257, 86, 18));
			jLabel1012.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel1012.setText("第一法定单位");
			jLabel1012.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1011 = new JLabel();
			jLabel1011.setBounds(new Rectangle(269, 277, 96, 18));
			jLabel1011.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel1011.setText("第二法定比例因子");
			jLabel1011.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(266, 254, 96, 18));
			jLabel101.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel101.setText("第一法定比例因子");
			jLabel101.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel18 = new JLabel();
			jLabel18.setBounds(new Rectangle(37, 306, 63, 18));
			jLabel18.setText("方式");
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17 = new JLabel();
			jLabel9 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel8 = new JLabel();
			jContentPane = new JPanel();
			jLabel = new JLabel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel3 = new JLabel();
			jLabel4 = new JLabel();
			jLabel5 = new JLabel();
			jLabel6 = new JLabel();
			jLabel7 = new JLabel();
			jLable = new JLabel();
			jLabel10 = new JLabel();
			jLabel11 = new JLabel();
			jLabel12 = new JLabel();
			jLabel13 = new JLabel();
			jContentPane.setLayout(null);
			jLabel.setText("成品序号");
			jLabel.setSize(new Dimension(63, 18));
			jLabel.setLocation(new Point(37, 41));
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(37, 68, 63, 18);
			jLabel1.setText("商品名称");
			jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setBounds(37, 149, 63, 18);
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("单价");
			jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setBounds(268, 94, 96, 18);
			jLabel3.setText("单位");
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(268, 41, 96, 18);
			jLabel4.setText("商品编码");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setBounds(268, 117, 96, 18);
			jLabel5.setText("原料费");
			jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(37, 122, 63, 18);
			jLabel6.setText("加工费单价");
			jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(268, 68, 96, 18);
			jLabel7.setText("型号规格");
			jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLable.setBounds(37, 203, 63, 18);
			jLable.setForeground(java.awt.Color.blue);
			jLable.setText("总金额");
			jLable.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(37, 230, 63, 18);
			jLabel10.setText("记录号");
			jLabel10
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setBounds(37, 176, 63, 18);
			jLabel11.setText("消费国");
			jLabel11
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setBounds(268, 169, 96, 18);
			jLabel12.setText("加工费总价");
			jLabel12
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setBounds(268, 141, 96, 18);

			jLabel13.setText("征免方式");
			jLabel13
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(36, 94, 63, 18);
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel8.setText("数量");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel14.setBounds(38, 328, 63, 18);
			jLabel14.setText("补充说明");
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel15.setBounds(268, 197, 96, 18);
			jLabel15.setText("单位毛重");
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel16.setBounds(268, 223, 96, 18);
			jLabel16.setText("单位净重");
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(93, 399, 75, 22);
			jLabel9.setText("法定单位");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setVisible(false);
			jLabel17.setBounds(357, 397, 74, 24);
			jLabel17.setText("法定数量");
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setVisible(false);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbLevyMode(), null);
			jContentPane.add(getTfUnitGrossWeight(), null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getTfUnitNetWeight(), null);
			jContentPane.add(getTfProcessUnitPrice(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(jLable, null);
			jContentPane.add(getTfTotalPrice(), null);
			jContentPane.add(getTfProcessTotalPrice(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getTfLegalUnit(), null);
			jContentPane.add(getTfCredenceNo(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(getTfMaterielFee(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(getTfLegalAmount(), null);
			jContentPane.add(getBtnComplex(), null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel101, null);
			jContentPane.add(jLabel1011, null);
			jContentPane.add(getTflegalUnitGene(), null);
			jContentPane.add(getTflegalUnit2Gene(), null);
			jContentPane.add(jLabel1012, null);
			jContentPane.add(jLabel10121, null);
			jContentPane.add(getTfLegalUnit(), null);
			jContentPane.add(getTfLegalUnit2(), null);
			jContentPane.add(jLabel19, null);
			jContentPane.add(getCbbDutyRate(), null);
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
			jToolBar.setBounds(2, 0, 576, 33);
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnNetGrossWeightCalculate());
			jToolBar.add(getBtnUnitPrice());
			jToolBar.add(getBtnPrevious());
			jToolBar.add(getBtnNext());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnExit());
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
					if (!validateData()) {
						return;
					}
					fillData();
					saveData();					
					dataState = DataState.BROWSE;
					setState();

					// DgContractFinishProduct.this.dispose();

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
					DgContractFinishProduct.this.dispose();
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
			tfSeqNum.setEditable(false);
			tfSeqNum.setSize(new Dimension(152, 22));
			tfSeqNum.setLocation(new Point(110, 41));
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
			tfName.setBounds(110, 68, 152, 22);
			// tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes cbbMaterielType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyMode() {
		if (cbbLevyMode == null) {
			cbbLevyMode = new JComboBox();
			cbbLevyMode.setBounds(374, 141, 159, 22);
		}
		return cbbLevyMode;
	}

	/**
	 * This method initializes cbbCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(110, 175, 152, 22);
		}
		return cbbCountry;
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
			tfSpec.setBounds(374, 64, 159, 22);
			// tfSpec.setEditable(false);
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfComplex
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setPreferredSize(new Dimension(4, 23));
			tfComplex.setLocation(new Point(374, 41));
			tfComplex.setSize(new Dimension(141, 22));
			tfComplex.setEditable(false);
		}
		return tfComplex;
	}

	/**
	 * This method initializes tfSecondLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitGrossWeight() {
		if (tfUnitGrossWeight == null) {
			tfUnitGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitGrossWeight.setBounds(374, 197, 159, 22);
			// tfUnitGrossWeight.setValue(new Double(0));
		}
		return tfUnitGrossWeight;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(110, 144, 152, 22);
			tfUnitPrice.setEditable(true);
			tfUnitPrice.setValue(new Double(0));
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes btnComplex
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNetGrossWeightCalculate() {
		if (btnNetGrossWeightCalculate == null) {
			btnNetGrossWeightCalculate = new JButton();
			btnNetGrossWeightCalculate.setText("净毛重计算");
			btnNetGrossWeightCalculate.setVisible(false);
			btnNetGrossWeightCalculate
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							//
							// 计算净毛重
							//
							Double unitNetWeight = contractAction
									.getFinishProductUnitWeight(new Request(
											CommonVars.getCurrUser()),
											contractExg.getId());
							tfUnitNetWeight.setValue(unitNetWeight);
							tfUnitGrossWeight.setValue(unitNetWeight);
						}
					});
		}
		return btnNetGrossWeightCalculate;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUnitPrice() {
		if (btnUnitPrice == null) {
			btnUnitPrice = new JButton();
			btnUnitPrice.setText("单价计算");
			btnUnitPrice.setVisible(false);
			btnUnitPrice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//
					// 单价计算
					//
					Double materielFee = contractAction
							.getFinishProductMaterielFee(new Request(CommonVars
									.getCurrUser()), contractExg.getId());
					//
					// 原料费用通过事件自动更新单价
					//
					tfMaterielFee.setValue(materielFee);
				}
			});
		}
		return btnUnitPrice;
	}

	/**
	 * This method initializes tfUnitWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitNetWeight() {
		if (tfUnitNetWeight == null) {
			tfUnitNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitNetWeight.setBounds(374, 223, 159, 22);
		}
		return tfUnitNetWeight;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setBounds(110, 90, 152, 22);
			tfAmount.setValue(new Double(0));
		}
		return tfAmount;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLegalUnit() {
		if (tfLegalUnit == null) {
			tfLegalUnit = new JTextField();
			tfLegalUnit.setBounds(109, 251, 155, 22);
			tfLegalUnit.setEditable(false);
		}
		return tfLegalUnit;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfCredenceNo() {
		if (tfCredenceNo == null) {
			tfCredenceNo = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCredenceNo.setBounds(110, 224, 152, 22);
			tfCredenceNo.setEditable(false);
			tfCredenceNo.setValue(new Integer(0));
		}
		return tfCredenceNo;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			// cbbUnit.setEnabled(false);
			cbbUnit.setBounds(374, 90, 159, 22);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfLegalAmount() {
		if (tfLegalAmount == null) {
			tfLegalAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfLegalAmount.setValue(new Double(0));
			tfLegalAmount.setBounds(434, 397, 133, 23);
			tfLegalAmount.setVisible(false);
		}
		return tfLegalAmount;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setLocation(new Point(109, 327));
			tfMemo.setSize(new Dimension(426, 22));
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfLegalAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfProcessUnitPrice() {
		if (tfProcessUnitPrice == null) {
			tfProcessUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfProcessUnitPrice.setBounds(110, 115, 152, 22);
			tfProcessUnitPrice.setValue(new Double(0));
		}
		return tfProcessUnitPrice;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMaterielFee() {
		if (tfMaterielFee == null) {
			tfMaterielFee = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMaterielFee.setBounds(374, 117, 159, 22);
			tfMaterielFee.setValue(new Double(0));
		}
		return tfMaterielFee;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JFormattedTextField();
			tfTotalPrice.setBounds(110, 201, 152, 22);
			tfTotalPrice.setEditable(false);
		}
		return tfTotalPrice;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfProcessTotalPrice() {
		if (tfProcessTotalPrice == null) {
			tfProcessTotalPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfProcessTotalPrice.setBounds(374, 169, 159, 22);
			tfProcessTotalPrice.setEditable(false);
		}
		return tfProcessTotalPrice;
	}

	// /**
	// * 设置加工费单价
	// *
	// */
	// private void setProcessUnitPrice() {
	// if (this.cbMultiple.isSelected() == false) {
	// return;
	// }
	// Double amountNew = new Double(0);
	// try {
	// double materielFee = (this.tfMaterielFee.getValue() == null ? 0.0
	// : Double.parseDouble(this.tfMaterielFee.getValue()
	// .toString()));
	// double multiple = (this.tfMultiple.getValue() == null ? 0.0
	// : Double.parseDouble(this.tfMultiple.getValue().toString()));
	// double amount = materielFee * multiple;
	// BigDecimal bd = new BigDecimal(amount);
	// amountNew = Double.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_UP)
	// .toString());
	// } catch (Exception ex) {
	// }
	// this.tfProcessUnitPrice.setValue(amountNew);
	// }

	/**
	 * 设置料件单价 = 原料费 + 加工费单价
	 * 
	 */
	private void setUnitPrice() {
		Double amountNew = new Double(0);
		try {
			double materielFee = (this.tfMaterielFee.getValue() == null ? 0.0
					: Double.parseDouble(this.tfMaterielFee.getValue()
							.toString()));
			double processUnitPrice = (this.tfProcessUnitPrice.getValue() == null ? 0.0
					: Double.parseDouble(this.tfProcessUnitPrice.getValue()
							.toString()));
			double amount = materielFee + processUnitPrice;
			BigDecimal bd = new BigDecimal(amount);
			amountNew = Double.valueOf(bd.setScale(6, BigDecimal.ROUND_HALF_UP)
					.toString());
		} catch (Exception ex) {
		}
		this.tfUnitPrice.setValue(amountNew);
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {

		int countBit = parameterSet.getCountBit() == null ? 5 : parameterSet
				.getCountBit();
		int priceBit = parameterSet.getPriceBit() == null ? 5 : parameterSet
				.getPriceBit();
		int moneyBit = parameterSet.getMoneyBit() == null ? 5 : parameterSet
				.getMoneyBit();
		// 初始化国
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry);
		this.cbbCountry.setSelectedItem(null);
		// 征减免税方式
		this.cbbLevyMode.setModel(CustomBaseModel.getInstance()
				.getLevymodeModel());
		this.cbbLevyMode
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLevyMode);
		this.cbbLevyMode.setSelectedItem(null);
		// 单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		this.cbbUnit.setSelectedItem(null);
		// 这里是控制焦点的顺序，以方便键盘的输！
		List<Object> components = new ArrayList<Object>();
		components.add(this.tfName);
		components.add(null);
		components.add(this.tfMemo);
		components.add(this.btnSave);
		components.add(this.btnNext);
		this.setComponentFocusList(components);

		// 数量
		CustomFormattedTextFieldUtils.setFormatterFactory(tfAmount, countBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfAmount,
				new AutoCalcListener() {
					public void run() {
						setTotalPrice();
						setProcessTotalPrice();
					}
				});
		// 加工费单价
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfProcessUnitPrice, priceBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(
				this.tfProcessUnitPrice, new AutoCalcListener() {
					public void run() {
						setUnitPrice();
						setTotalPrice();
						setProcessTotalPrice();
					}
				});
		// // 加工费倍数
		// CustomFormattedTextFieldUtils.setFormatterFactory(this.tfMultiple,
		// 5);
		// CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfMultiple,
		// new CustomFormattedTextFieldUtils.AutoCalcListener() {
		// public void run() {
		// // setProcessUnitPrice();
		// setUnitPrice();
		// setTotalPrice();
		// setProcessTotalPrice();
		// }
		// });
		// 原料费
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfMaterielFee,
				moneyBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfMaterielFee,
				new AutoCalcListener() {
					public void run() {
						// setProcessUnitPrice();
						setUnitPrice();
						setTotalPrice();
					}
				});
		// 单价
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitPrice,
				priceBit);
		CustomFormattedTextFieldUtils.addAutoCalcListener(this.tfUnitPrice,
				new AutoCalcListener() {
					public void run() {
						setTotalPrice();
						setProcessTotalPrice();
					}
				});
		// 加工费总价
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tfProcessTotalPrice, moneyBit);
		// 金额
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfTotalPrice,
				moneyBit);
		// 单位重量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitNetWeight,
				5);
		// 件数
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitNetWeight,
				5);
		// 法定单位比例因子
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tflegalUnitGene,
				9);
		// 第二法定单位比例因子
		CustomFormattedTextFieldUtils.setFormatterFactory(
				this.tflegalUnit2Gene, 9);
		//申报状态
		cbbDutyRate.removeAllItems();
		cbbDutyRate.addItem(new ItemProperty(DutyRateType.NO_DECLARE,
				"企业不申报"));
		cbbDutyRate.addItem(new ItemProperty(DutyRateType.DECLARE,
				"企业申报"));
		cbbDutyRate.addItem(new ItemProperty(DutyRateType.CHECK_AND_RATIFY,
				"已核定"));
		cbbDutyRate.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbDutyRate);
	}

	// private Double getJianshu() {
	// double shuliang = 0;
	// double jinzhong = 0;
	// if (contractExg.getExportAmount() != null) {
	// shuliang = contractExg.getExportAmount().doubleValue();
	// }
	// if (contractExg.getUnitNetWeight() != null) {
	// jinzhong = contractExg.getUnitNetWeight().doubleValue();
	// }
	// double jianshu = 0;
	// if (contractExg.getUnit() != null
	// && (contractExg.getUnit().getName().trim().equals("公斤") || contractExg
	// .getUnit().getName().trim().equals("千克"))) {
	// if (jinzhong == 0.0) {
	// jianshu = shuliang;
	// } else {
	// jianshu = shuliang / jinzhong;
	// }
	// } else if (contractExg.getUnit() != null
	// && contractExg.getUnit().getName().trim().equals("个")) {
	// jianshu = shuliang;
	// }
	// BigDecimal bd = new BigDecimal(jianshu);
	// String totalPrice = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
	// return Double.valueOf(totalPrice);
	// }

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		contractExg = (ContractExg) tableModel.getCurrentRow();
		if (this.contractExg == null) {
			this.tfSeqNum.setText(String.valueOf(this.contractAction
					.getMaxContractExgSeqNum(new Request(CommonVars
							.getCurrUser()), contract.getId()) + 1));
			return;
		}
		// 序号
		if (this.contractExg.getSeqNum() != null) {
			this.tfSeqNum.setText(this.contractExg.getSeqNum().toString());
		} else {
			this.tfSeqNum.setText("");
		}
		// 商品名称
		if (this.contractExg.getName() != null) {
			this.tfName.setText(this.contractExg.getName());
		} else {
			this.tfName.setText("");
		}
		// 规格型号
		if (this.contractExg.getSpec() != null) {
			this.tfSpec.setText(this.contractExg.getSpec());
		} else {
			this.tfSpec.setText("");
		}
		// 商品编码
		if (this.contractExg.getComplex() != null) {
			this.tfComplex.setText(this.contractExg.getComplex().getCode());
			complex = this.contractExg.getComplex();
		} else {
			this.tfComplex.setText("");
		}
		// 单位
		if (this.contractExg.getUnit() != null) {
			this.cbbUnit.setSelectedItem(this.contractExg.getUnit());
		} else {
			this.cbbUnit.setSelectedItem(null);
		}
		// 法定单位
		if (this.contractExg.getComplex() != null
				&& this.contractExg.getComplex().getFirstUnit() != null) {
			this.tfLegalUnit.setText(this.contractExg.getComplex()
					.getFirstUnit().getName());
		} else {
			this.tfLegalUnit.setText("");
		}
		if (this.contractExg.getComplex() != null
				&& this.contractExg.getComplex().getSecondUnit() != null) {
			tfLegalUnit2.setText(contractExg.getComplex() == null ? ""
					: contractExg.getComplex().getSecondUnit().getName());
		} else {
			this.tfLegalUnit2.setText("");
		}
		// 单价
		if (this.contractExg.getUnitPrice() != null) {
			this.tfUnitPrice.setValue(this.contractExg.getUnitPrice());
		} else {
			this.tfUnitPrice.setValue(new Double(0));
		}
		// 数量
		if (this.contractExg.getExportAmount() != null) {
			this.tfAmount.setValue(this.contractExg.getExportAmount());
		} else {
			this.tfAmount.setValue(new Double(0));
		}
		// 总金额
		if (this.contractExg.getTotalPrice() != null) {
			this.tfTotalPrice.setValue(this.contractExg.getTotalPrice());
		} else {
			this.tfTotalPrice.setValue(new Double(0));
		}
		// 法定数量
		if (this.contractExg.getLegalAmount() != null) {
			this.tfLegalAmount.setValue(this.contractExg.getLegalAmount());
		} else {
			this.tfLegalAmount.setValue(new Double(0));
		}
		// 单位净重
		if (this.contractExg.getUnitNetWeight() != null) {
			this.tfUnitNetWeight.setValue(this.contractExg.getUnitNetWeight());
		} else {
			this.tfUnitNetWeight.setValue(new Double(0));
		}
		// 加工费单价
		if (this.contractExg.getProcessUnitPrice() != null) {
			this.tfProcessUnitPrice.setValue(this.contractExg
					.getProcessUnitPrice());
		} else {
			this.tfProcessUnitPrice.setValue(new Double(0));
		}
		// 加工费总价
		if (this.contractExg.getProcessTotalPrice() != null) {
			this.tfProcessTotalPrice.setValue(this.contractExg
					.getProcessTotalPrice());
		} else {
			this.tfProcessTotalPrice.setValue(new Double(0));
		}
		// 单位毛重
		if (this.contractExg.getUnitGrossWeight() != null) {
			this.tfUnitGrossWeight.setValue(this.contractExg
					.getUnitGrossWeight());
		} else {
			this.tfUnitGrossWeight.setValue(new Double(0));
		}
		// 消费国
		this.cbbCountry.setSelectedItem(this.contractExg.getCountry());
		// 原料费
		if (this.contractExg.getMaterialFee() != null) {
			this.tfMaterielFee.setValue(this.contractExg.getMaterialFee());
		} else {
			this.tfMaterielFee.setValue(new Double(0));
		}
		// 征减免税方式
		this.cbbLevyMode.setSelectedItem(this.contractExg.getLevyMode());
		// 凭证号码
		if (this.contractExg.getCredenceNo() != null) {
			this.tfCredenceNo.setValue(this.contractExg.getCredenceNo());
		} else {
			this.tfCredenceNo.setValue(new Double(0));
		}
		// 备注
		if (this.contractExg.getNote() != null) {
			this.tfMemo.setText(this.contractExg.getNote());
		} else {
			this.tfMemo.setText(null);
		}
		// 第一比例因子
		if (this.contractExg.getLegalUnitGene() != null) {
			this.tflegalUnitGene.setValue(this.contractExg.getLegalUnitGene());
		} else {
			this.tflegalUnitGene.setValue(new Double(0));
		}
		// 第二比例因子
		if (this.contractExg.getLegalUnit2Gene() != null) {
			this.tflegalUnit2Gene.setValue(this.contractExg.getLegalUnit2Gene());
		} else {
			this.tflegalUnit2Gene.setValue(new Double(0));
		}
		//方式
		if(this.contractExg.getMaterialFee()!=null){
			this.tfModeNote.setText(this.contractExg.getMannerNote());
		}else{
			this.tfModeNote.setText(null);
		}
		//申报状态
		if(contractExg.getDutyRate() != null){
			Integer dutyRate = contractExg.getDutyRate().intValue();			
		    cbbDutyRate.setSelectedIndex(ItemProperty.getIndexByCode(String.valueOf(dutyRate)
				, cbbDutyRate));//TODO No1
		}else{
			 cbbDutyRate.setSelectedIndex(ItemProperty.getIndexByCode("1"
						, cbbDutyRate));
		}
		// jFormattedTextField.setValue(getJianshu());
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		// if (this.contractExg == null) {
		// contractExg = new ContractExg();
		// contractExg.setCompany(CommonVars.getCurrUser().getCompany());
		// contractExg.setContract(this.contract);
		// contractExg
		// .setSeqNum(this.contractAction.getMaxContractExgSeqNum(
		// new Request(CommonVars.getCurrUser()), contract
		// .getId()) + 1);
		// }
		// 商品编码
		this.contractExg.setComplex(complex);
		// 商品名称
		this.contractExg.setName(this.tfName.getText());
		// 商品规格型号
		this.contractExg.setSpec(this.tfSpec.getText());
		// 法定单位
		// this.contractExg.setLegalUnit(complex.getFirstUnit());
		// 单价
		this.contractExg.setUnitPrice(Double.valueOf(this.tfUnitPrice
				.getValue().toString()));
		// 数量
		this.contractExg.setExportAmount(Double.valueOf(this.tfAmount
				.getValue().toString()));
		// 总金额
		this.contractExg
				.setTotalPrice(this.tfTotalPrice.getValue() == null ? 0.0
						: Double.valueOf(this.tfTotalPrice.getValue()
								.toString()));
		// 单位
		this.contractExg.setUnit((Unit) this.cbbUnit.getSelectedItem());
		// 法定数量
		this.contractExg.setLegalAmount(Double.valueOf(this.tfLegalAmount
				.getValue().toString()));
		// 单位净重
		this.contractExg.setUnitNetWeight(Double.valueOf(this.tfUnitNetWeight
				.getValue().toString()));

		// 加工费单价
		this.contractExg.setProcessUnitPrice(Double
				.valueOf(this.tfProcessUnitPrice.getValue().toString()));
		// 加工费总价
		this.contractExg.setProcessTotalPrice(this.tfProcessTotalPrice
				.getValue() == null ? 0.0 : Double
				.valueOf(this.tfProcessTotalPrice.getValue().toString()));
		// 单位毛重
		this.contractExg.setUnitGrossWeight(Double
				.valueOf(this.tfUnitGrossWeight.getValue().toString()));
		// 消费国
		this.contractExg
				.setCountry((Country) this.cbbCountry.getSelectedItem());
		// 原料费
		this.contractExg.setMaterialFee(Double.valueOf(this.tfMaterielFee
				.getValue().toString()));
		// 征减免税方式
		this.contractExg.setLevyMode((LevyMode) this.cbbLevyMode
				.getSelectedItem());
		// 凭证号码
		this.contractExg.setCredenceNo(Double.valueOf(
				this.tfCredenceNo.getValue().toString()).intValue());
		// 备注
		this.contractExg.setNote(this.tfMemo.getText());
		// 第一比例因子
		this.contractExg.setLegalUnitGene(Double.valueOf(this.tflegalUnitGene
				.getValue().toString()));
		// 第二比例因子
		this.contractExg.setLegalUnit2Gene(Double.valueOf(this.tflegalUnit2Gene
				.getValue().toString()));
		//方式
		this.contractExg.setMannerNote(this.tfModeNote.getText());
		if (ModifyMarkState.UNCHANGE.equals(this.contractExg.getModifyMark())) {
			this.contractExg.setModifyMark(ModifyMarkState.MODIFIED);
		}
		//申报状态
		contractExg.setDutyRate(Double.valueOf(((ItemProperty)cbbDutyRate.getSelectedItem()).getCode()));//TODO No.2
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData() {
		if (this.dataState == DataState.ADD) {
			int seqNum = this.contractAction.getMaxContractExgSeqNum(
					new Request(CommonVars.getCurrUser()), contract.getId()) + 1;
			contractExg.setSeqNum(seqNum);
			contractExg = this.contractAction.saveContractExg(new Request(
					CommonVars.getCurrUser()), this.contractExg);
			this.tableModel.addRow(contractExg);
		} else if (this.dataState == DataState.EDIT) {
			contractExg = this.contractAction.saveContractExg(new Request(
					CommonVars.getCurrUser()), this.contractExg);
			this.tableModel.updateRow(contractExg);
		}
	}

	/**
	 * 验证数据
	 * 
	 * @return
	 */
	private boolean validateData() {
		if (this.complex == null) {
			JOptionPane.showMessageDialog(this, "商品编码不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.cbbUnit.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "单位不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.tfUnitPrice.getValue() == null) {
			JOptionPane.showMessageDialog(this, "单价不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} 
//		else if (validate(this.tfUnitPrice.getValue().toString().trim(),
//				parameterSet.getPriceBit())) {
//			JOptionPane.showMessageDialog(this,
//					"单价小数位不能超过海关允许最大小数位（海关允许最大值为５）!!", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//			return false;
//		}
		if (this.tfProcessUnitPrice.getValue() == null) {
			JOptionPane.showMessageDialog(this, "加工费单价不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (validate(this.tfProcessUnitPrice.getValue().toString()
				.trim(), parameterSet.getPriceBit())) {
			JOptionPane.showMessageDialog(this,
					"加工费单价小数位不能超过海关允许最大小数位（海关允许最大值为５）!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (this.tfAmount.getValue() == null) {
			JOptionPane.showMessageDialog(this, "数量不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (validate(this.tfAmount.getValue().toString().trim(),
				parameterSet.getCountBit())) {
			JOptionPane.showMessageDialog(this,
					"数量小数位不能超过海关允许最大小数位（海关允许最大值为５）!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// if (this.cbbCountry.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(this, "消费国不可为空!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		if (this.tfAmount.getValue() != null
				&& Double.parseDouble(this.tfAmount.getValue().toString()) < 0) {
			JOptionPane.showMessageDialog(this, "数量不能小于零!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		double grossWeight = this.tfUnitGrossWeight.getValue() == null ? 0.0
				: new Double(this.tfUnitGrossWeight.getValue().toString());
		double netWeight = this.tfUnitNetWeight.getValue() == null ? 0.0
				: new Double(this.tfUnitNetWeight.getValue().toString());
		if (grossWeight != 0.0 && netWeight != 0.0) {

			if (grossWeight < netWeight) {
				JOptionPane.showMessageDialog(this, "净重不能大于毛重!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断输入数值的小数是否与参数设定中小数位一致
	 */
	private boolean validate(String text, Integer bitValue) {
		if (bitValue ==null) {
			bitValue =5;
		}else if(bitValue==0){
			bitValue++;
		}
		Double value = Double.valueOf(text);
		DecimalFormat format = new DecimalFormat();
		format.setGroupingSize(999);
		format.setMaximumFractionDigits(999);
		String test = format.format(value);
		String[] strs = test.split("\\.");//正则表达式
//        BigDecimal   bd=new   BigDecimal(String.valueOf(value.doubleValue()));
//		int count1 = test.length();
//		int count2 = test.indexOf(".") + 1;
//		int margin = count1 - count2;
//		int margin = bd.scale();
		if (strs.length >= 2) {
			int margin = strs[1].length();
			if (margin > bitValue.intValue()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 设置合同总金额
	 */
	private void setTotalPrice() {
		int moneyBit = parameterSet.getMoneyBit() == null ? 5 : parameterSet
				.getMoneyBit();
		double amount = (this.tfAmount.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfAmount.getValue().toString()));
		double unitPrice = (this.tfUnitPrice.getValue() == null ? 0.0 : Double
				.parseDouble(tfUnitPrice.getValue().toString()));
		double totalPrice = amount * unitPrice;
		BigDecimal bd = new BigDecimal(totalPrice);
		this.tfTotalPrice.setValue(bd.setScale(moneyBit, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

	/**
	 * 获得合同
	 * 
	 * @return
	 */
	private void setProcessTotalPrice() {
		double amount = (this.tfAmount.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfAmount.getValue().toString()));
		double unitPrice = (this.tfProcessUnitPrice.getValue() == null ? 0.0
				: Double.parseDouble(tfProcessUnitPrice.getValue().toString()));
		double totalPrice = amount * unitPrice;
		BigDecimal bd = new BigDecimal(totalPrice);
		this.tfProcessTotalPrice.setValue(bd.setScale(4,
				BigDecimal.ROUND_HALF_UP).doubleValue());
	}

	/**
	 * 设置状态
	 * 
	 */

	private void setState() {
		ContractExg contractExg = (ContractExg) tableModel.getCurrentRow();
		if (contractExg == null) {
			return;
		}
		boolean isChanging = false;
		if (this.contract != null) {
			if (DeclareState.CHANGING_EXE.equals(this.contract
					.getDeclareState())) {
				isChanging = true;
			}
		}
		btnNetGrossWeightCalculate.setEnabled(this.contractExg != null
				&& dataState != DataState.BROWSE);
		btnUnitPrice.setEnabled(this.contractExg != null
				&& dataState != DataState.BROWSE);
		btnSave.setEnabled(dataState != DataState.BROWSE);
		btnCancel.setEnabled(dataState != DataState.BROWSE);
		btnPrevious.setEnabled(tableModel.hasPreviousRow());
		btnNext.setEnabled(tableModel.hasNextRow());
		btnEdit.setEnabled(dataState == DataState.BROWSE);
		btnComplex.setEnabled(dataState != DataState.BROWSE);
//		tfName.setEditable(ModifyMarkState.ADDED.equals(contractExg
//				.getModifyMark())
//				&& (dataState != DataState.BROWSE));
//		tfSpec.setEditable(ModifyMarkState.ADDED.equals(contractExg
//				.getModifyMark())
//				&& dataState != DataState.BROWSE);
		tfAmount.setEditable(dataState != DataState.BROWSE);
		tfProcessUnitPrice.setEditable(dataState != DataState.BROWSE);
		// tfMultiple.setEditable(dataState != DataState.BROWSE);
		tfMaterielFee.setEditable(dataState != DataState.BROWSE);
		tfUnitPrice.setEditable(dataState != DataState.BROWSE);
		tfUnitGrossWeight.setEditable(dataState != DataState.BROWSE);
		tfUnitNetWeight.setEditable(dataState != DataState.BROWSE);
		// jFormattedTextField.setEditable(dataState != DataState.BROWSE);
		// tfCredenceNo.setEditable(dataState != DataState.BROWSE);
		tfMemo.setEditable(dataState != DataState.BROWSE);
		this.tfModeNote.setEditable(dataState != DataState.BROWSE);
		cbbUnit.setEnabled(dataState != DataState.BROWSE);
		cbbCountry.setEnabled(dataState != DataState.BROWSE);
		cbbLevyMode.setEnabled(dataState != DataState.BROWSE);
		cbbDutyRate.setEnabled(dataState != DataState.BROWSE);
		// cbMultiple.setEnabled(dataState != DataState.BROWSE);
		this.tflegalUnitGene.setEnabled(this.dataState != DataState.BROWSE);
		this.tflegalUnit2Gene.setEnabled(this.dataState != DataState.BROWSE);
	}

	// // 得到数量
	// private Double getTotalNum() {
	// double jingzhong = 0;
	// double jianshu = 0;
	// if (this.tfUnitNetWeight.getText() != null
	// && !this.tfUnitNetWeight.getText().equals("")) {
	// jingzhong = Double.parseDouble(this.tfUnitNetWeight.getText()
	// .toString());
	// }
	// if (this.jFormattedTextField.getText() != null
	// && !this.jFormattedTextField.getText().equals("")) {
	// jianshu = Double.parseDouble(this.jFormattedTextField.getText()
	// .toString());
	// }
	// double shuliang = 0;
	// if (cbbUnit.getSelectedItem() != null
	// && (((Unit) cbbUnit.getSelectedItem()).getName().trim().equals(
	// "公斤") || ((Unit) cbbUnit.getSelectedItem()).getName()
	// .trim().equals("千克"))) {
	// shuliang = jingzhong * jianshu;
	// } else if (cbbUnit.getSelectedItem() != null
	// && ((Unit) cbbUnit.getSelectedItem()).getName().trim().equals(
	// "个")) {
	// shuliang = jianshu;
	// }
	// BigDecimal bd = new BigDecimal(shuliang);
	// String totalPrice = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
	// return Double.valueOf(totalPrice);
	// }

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			btnPrevious
					.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			btnPrevious
					.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					contractExg = (ContractExg) tableModel.getCurrentRow();
					showData();
					dataState = DataState.EDIT;
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
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					contractExg = (ContractExg) tableModel.getCurrentRow();
					showData();
					dataState = DataState.EDIT;
					setState();

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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnComplex() {
		if (btnComplex == null) {
			btnComplex = new JButton();
			btnComplex.setBounds(new Rectangle(513, 41, 20, 22));
			btnComplex.setText("...");
			btnComplex.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Complex obj = (Complex) CommonQuery.getInstance()
							.getComplex();
					if (obj != null) {
						complex = obj;
						tfComplex.setText(complex.getCode());
						tfLegalUnit.setText(complex.getFirstUnit().getName());
						tfLegalUnit2
								.setText(complex.getSecondUnit() == null ? ""
										: complex.getSecondUnit().getName());
						ContractExg exg = (ContractExg) tableModel
								.getCurrentRow();
						String modifyMark = exg.getModifyMark();
						if (ModifyMarkState.ADDED.equals(modifyMark)) {
							tfName.setText(complex.getName());
							// tfSpec.setText(complex.getName());
						}
					}
				}
			});
		}
		return btnComplex;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (tfModeNote == null) {
			tfModeNote = new JTextField();
			tfModeNote.setBounds(new Rectangle(109, 303, 152, 22));
		}
		return tfModeNote;
	}

	/**
	 * This method initializes tflegalUnitGene	
	 * 	
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField	
	 */
	private JCustomFormattedTextField getTflegalUnitGene() {
		if (tflegalUnitGene == null) {
			tflegalUnitGene = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tflegalUnitGene.setBounds(new Rectangle(374, 248, 159, 22));
			tflegalUnitGene.setValue(new Double(0));
		}
		return tflegalUnitGene;
	}

	/**
	 * This method initializes tflegalUnit2Gene	
	 * 	
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField	
	 */
	private JCustomFormattedTextField getTflegalUnit2Gene() {
		if (tflegalUnit2Gene == null) {
			tflegalUnit2Gene =new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tflegalUnit2Gene.setBounds(new Rectangle(374, 276, 159, 22));
			tflegalUnit2Gene.setValue(new Double(0));
		}
		return tflegalUnit2Gene;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */

	/**
	 * This method initializes tfLegalUnit2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfLegalUnit2() {
		if (tfLegalUnit2 == null) {
			tfLegalUnit2 = new JTextField();
			tfLegalUnit2.setBounds(new Rectangle(109, 278, 152, 22));
			tfLegalUnit2.setEditable(false);
		}
		return tfLegalUnit2;
	}

	/**
	 * This method initializes cbbDutyRate	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbDutyRate() {
		if (cbbDutyRate == null) {
			cbbDutyRate = new JComboBox();
			cbbDutyRate.setBounds(new Rectangle(374, 300, 159, 22));
		}
		return cbbDutyRate;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
