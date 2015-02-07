/*
 * Created on 2004-7-26
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.transferfactory;

import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.transferfactory.action.TransferFactoryManageAction;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
import com.bestway.common.transferfactory.entity.TransferFactoryCommodityInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import java.awt.Rectangle;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgTransferFactoryCommodityInfo extends DgCommon {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JTextField tfName = null;

	private JTextField tfMemo = null;

	private JTextField tfTransferFactoryBillNo = null;

	private JTextField tfSpec = null;

	private JTextField tfComplex = null;

	private JButton btnCountry = null;

	private JButton btnCurrency = null;

	private JTextField tfUnitName = null;

	private JFormattedTextField tfQuantity = null;

	private JFormattedTextField tfUnitPrice = null;

	private JTextField tfAmountPrice = null;

	private JTextField tfVersion = null;

	private JTextField tfSourceBill = null;

	private JComboBox cbbCountry = null;

	private JComboBox cbbCurr = null;

	private JTableListModel tableModel = null;

	private TransferFactoryBill transferFactoryBill = null;  //  @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private boolean isImportGoods = true;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse,visual-constraint="519,8"

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	private JFormattedTextField tfNetWeight = null;

	private JFormattedTextField tfCubage = null;

	private JFormattedTextField tfGrossWeight = null;

	private JCustomFormattedTextField tfTransFactAmount = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel191 = null;

	private JLabel jLabel192 = null;

	private JLabel jLabel193 = null;

	private JLabel lbLeftAmount = null;

	private Double leftAmount = 0.0;  //  @jve:decl-index=0:

	private JLabel jLabel194 = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel1941 = null;

	private JTextField tfSeqNum = null;

	public DgTransferFactoryCommodityInfo() {
		super();
		initialize();
		initComponents();
	}

	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("结转单据---商品信息表");
		this.setSize(503, 381);

	}

	@Override
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (dataState == DataState.ADD) {
				// emptyData();
				// fillMateriel();
				// ok!!
			} else if (dataState == DataState.EDIT) {
				TransferFactoryCommodityInfo transferFactoryCommodityInfo = (TransferFactoryCommodityInfo) tableModel
						.getCurrentRow();
				showData(transferFactoryCommodityInfo);
			}
			this.jLabel192.setText("收/送货数量");
			if (isImportGoods() == true) {
				this.setTitle("结转单据---物料商品信息表");
			} else {
				this.setTitle("结转单据---成品商品信息表");
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * @return Returns the transferFactoryManageAction.
	 */
	public TransferFactoryManageAction getTransferFactoryManageAction() {
		return transferFactoryManageAction;
	}

	/**
	 * @param transferFactoryManageAction
	 *            The transferFactoryManageAction to set.
	 */
	public void setTransferFactoryManageAction(
			TransferFactoryManageAction transferFactoryManageAction) {
		this.transferFactoryManageAction = transferFactoryManageAction;
	}

	/**
	 * @return Returns the leftAmount.
	 */
	public Double getLeftAmount() {
		return leftAmount;
	}

	/**
	 * @param leftAmount
	 *            The leftAmount to set.
	 */
	public void setLeftAmount(Double leftAmount) {
		this.leftAmount = leftAmount;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @return Returns the isImportGoods.
	 */
	public boolean isImportGoods() {
		return isImportGoods;
	}

	/**
	 * @param isImportGoods
	 *            The isImportGoods to set.
	 */
	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public TransferFactoryBill getTransferFactoryBill() {
		return transferFactoryBill;
	}

	/**
	 * @param impExpRequestBill
	 *            The impExpRequestBill to set.
	 */
	public void setTransferFactoryBill(TransferFactoryBill impExpRequestBill) {
		this.transferFactoryBill = impExpRequestBill;
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

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel1941 = new JLabel();
			jLabel1941.setBounds(new Rectangle(254, 81, 72, 23));
			jLabel1941.setText("备案序号");
			jLabel194 = new JLabel();
			jLabel194.setBounds(new Rectangle(23, 82, 72, 23));
			jLabel194.setText("手册号");
			lbLeftAmount = new JLabel();
			lbLeftAmount.setBounds(new Rectangle(246, 302, 163, 36));
			lbLeftAmount.setForeground(java.awt.Color.blue);
			lbLeftAmount.setFont(new java.awt.Font("Dialog",
					java.awt.Font.BOLD, 14));
			jLabel193 = new JLabel();
			jLabel193.setBounds(new Rectangle(52, 302, 189, 36));
			jLabel193.setText(" 该商品还可以转厂的数量为:");
			jLabel193.setForeground(java.awt.Color.blue);
			jLabel193.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD,
					14));
			jLabel192 = new JLabel();
			jLabel192.setBounds(new Rectangle(23, 152, 72, 22));
			jLabel192.setText("转厂数量");
			jLabel192.setForeground(java.awt.Color.blue);
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			// java.awt.GridBagConstraints gridBagConstraints11 = new
			// GridBagConstraints();
			// java.awt.GridBagConstraints gridBagConstraints10 = new
			// GridBagConstraints();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			jContentPane = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			jLabel10.setText("名称");
			jLabel11.setText("型号规格");
			jLabel13.setText("关封数量");
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel14.setText("总价");
			jContentPane.setLayout(null);
			jLabel.setText("商品号码");
			jLabel.setBounds(23, 105, 72, 22);
			jLabel1.setText("单据号");
			jLabel1.setBounds(23, 57, 72, 22);
			jLabel2.setText("单位");
			jLabel2.setBounds(254, 105, 72, 22);
			jLabel6.setText("单价");
			jLabel6.setBounds(23, 175, 72, 22);
			jLabel7.setText("备注");
			jLabel7.setBounds(23, 274, 72, 22);
			// gridBagConstraints10.gridx = 3;
			// gridBagConstraints10.gridy = 7;
			// gridBagConstraints10.ipadx = -24;
			// gridBagConstraints10.ipady = -8;
			// gridBagConstraints10.insets = new java.awt.Insets(3, 1, 2, 31);
			// gridBagConstraints11.gridx = 3;
			// gridBagConstraints11.gridy = 6;
			// gridBagConstraints11.ipadx = -24;
			// gridBagConstraints11.ipady = -8;
			// gridBagConstraints11.insets = new java.awt.Insets(5, 1, 1, 31);
			jLabel10.setBounds(254, 57, 72, 22);
			jLabel11.setBounds(254, 129, 72, 22);
			jLabel11.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					12));
			jLabel13.setBounds(23, 129, 72, 22);
			jLabel14.setBounds(254, 153, 72, 22);
			jLabel8.setBounds(23, 200, 72, 22);
			jLabel8.setText("币制");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(23, 224, 72, 22);
			jLabel9.setText("产销国(地区)");
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel12.setBounds(23, 249, 72, 22);
			jLabel12.setText("体积");
			jLabel15.setBounds(254, 247, 72, 22);
			jLabel15.setText("来源单据号");
			jLabel16.setBounds(254, 177, 72, 22);
			jLabel16.setText("毛重");
			jLabel16.setForeground(java.awt.Color.blue);
			jLabel17.setBounds(254, 201, 72, 22);
			jLabel17.setText("净重");
			jLabel17.setForeground(java.awt.Color.blue);
			jLabel18.setBounds(254, 226, 72, 22);
			jLabel18.setText("版本");
			jContentPane.add(getTfName(), null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(getTfTransferFactoryBillNo(), null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(gettfUnitName(), null);
			jContentPane.add(getTfNetWeight(), null);
			jContentPane.add(getJFormattedTextField3(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(jLabel17, null);
			jContentPane.add(jLabel18, null);
			jContentPane.add(getTfAmountPrice(), null);
			jContentPane.add(getTfVersion(), null);
			jContentPane.add(getTfSourceBill(), null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(getCbbCurr(), null);
			jContentPane.add(getTfCubage(), null);
			jContentPane.add(getTfQuantity(), null);
			jContentPane.add(getTfGrossWeight(), null);
			jContentPane.add(getTfTransFactAmount(), null);
			jContentPane.add(jLabel192, null);
			jContentPane.add(jLabel193, null);
			jContentPane.add(lbLeftAmount, null);
			jContentPane.add(jLabel194, null);
			jContentPane.add(getTfEmsNo(), null);
			jContentPane.add(jLabel1941, null);
			jContentPane.add(getTfSeqNum(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * jContentPane.add(jLabel191, null);
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
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
	 * This method initializes tfNetWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(327, 201, 136, 22);
			tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	/**
	 * This method initializes tfCubage
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfCubage() {
		if (tfCubage == null) {
			tfCubage = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCubage.setBounds(98, 249, 144, 22);
			tfCubage.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCubage;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(327, 177, 136, 22);
			tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(1, 2, 493, 35);
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
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
					if (validateDataIsNull()) {
						return;
					}
					if (validateOther()) {
						return;
					}
					saveData();
					DgTransferFactoryCommodityInfo.this.dispose();
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
		}
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				DgTransferFactoryCommodityInfo.this.dispose();
			}
		});
		return btnCancel;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JTextField gettfUnitName() {
		if (tfUnitName == null) {
			tfUnitName = new JTextField();
			tfUnitName.setBounds(327, 105, 136, 22);
			tfUnitName.setEditable(false);
		}
		return tfUnitName;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfQuantity() {
		if (tfQuantity == null) {
			tfQuantity = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfQuantity.setFormatterFactory(getDefaultFormatterFactory());
			tfQuantity.setBounds(98, 129, 144, 22);
			tfQuantity.setEditable(false);
		}
		return tfQuantity;
	}

	/**
	 * This method initializes tfGrossWeight
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getJFormattedTextField3() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setEditable(true);
			tfUnitPrice.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitPrice.setBounds(98, 175, 144, 22);
		}
		return tfUnitPrice;
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
			tfName.setBounds(327, 57, 136, 22);
		}
		return tfName;
	}

	/**
	 * This method initializes tfMemo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(98, 274, 367, 22);
		}
		return tfMemo;
	}

	/**
	 * This method initializes tfAmountPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAmountPrice() {
		if (tfAmountPrice == null) {
			tfAmountPrice = new JTextField();
			tfAmountPrice.setBounds(327, 153, 136, 22);
			tfAmountPrice.setEditable(false);
		}
		return tfAmountPrice;
	}

	/**
	 * This method initializes tfVersion
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfVersion() {
		if (tfVersion == null) {
			tfVersion = new JTextField();
			tfVersion.setBounds(327, 226, 136, 22);
		}
		return tfVersion;
	}

	/**
	 * This method initializes tfSourceBill
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSourceBill() {
		if (tfSourceBill == null) {
			tfSourceBill = new JTextField();
			tfSourceBill.setBounds(327, 248, 136, 22);
		}
		return tfSourceBill;
	}

	/**
	 * This method initializes cbbCountry
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(98, 224, 144, 22);
		}
		return cbbCountry;
	}

	/**
	 * This method initializes cbbCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(98, 200, 144, 22);
		}
		return cbbCurr;
	}

	/**
	 * This method initializes tfTransferFactoryBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTransferFactoryBillNo() {
		if (tfTransferFactoryBillNo == null) {
			tfTransferFactoryBillNo = new JTextField();
			tfTransferFactoryBillNo.setEditable(false);
			tfTransferFactoryBillNo.setBounds(98, 57, 144, 22);
		}
		return tfTransferFactoryBillNo;
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
			tfSpec.setBounds(327, 129, 136, 22);
		}
		return tfSpec;
	}

	/**
	 * This method initializes tfMaterielNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setEditable(false);
			tfComplex.setBounds(98, 105, 144, 22);
		}
		return tfComplex;
	}

	/**
	 * 显示数据
	 */
	private void showData(TransferFactoryCommodityInfo data) {
		this.tfEmsNo.setText(data.getEmsNo());
		this.tfSeqNum.setText(data.getSeqNum() == null ? "" : data.getSeqNum()
				.toString());
		if (data.getComplex() != null) {
			this.tfComplex.setText(data.getComplex().getCode());
		} else {
			this.tfComplex.setText("");
		}
		if (data.getCommName() != null) {
			this.tfName.setText(data.getCommName());
		} else {
			this.tfName.setText("");
		}
		if (data.getCommSpec() != null) {
			this.tfSpec.setText(data.getCommSpec());
		} else {
			this.tfSpec.setText("");
		}
		if (data.getUnit() != null) {
			if (data.getUnit().getName() != null) {
				this.tfUnitName.setText(data.getUnit().getName());
			} else {
				this.tfUnitName.setText("");
			}
		} else {
			this.tfUnitName.setText("");
		}

		if (this.transferFactoryBill != null) {
			this.tfTransferFactoryBillNo.setText(this.transferFactoryBill
					.getTransferFactoryBillNo().toString());
		} else {
			this.tfTransferFactoryBillNo.setText("");
		}
		if (data.getMemo() != null) {
			this.tfMemo.setText(data.getMemo());
		} else {
			this.tfMemo.setText("");
		}
		if (data.getQuantity() != null) {
			this.tfQuantity.setValue(data.getQuantity());
		} else {
			this.tfQuantity.setValue(new Integer(0));
		}
		if (data.getUnitPrice() != null) {
			this.tfUnitPrice.setValue(data.getUnitPrice());
		} else {
			this.tfUnitPrice.setValue(new Double(0));
		}
		if (data.getTotalPrice() != null) {
			this.tfAmountPrice.setText(data.getTotalPrice().toString());
		} else {
			this.tfAmountPrice.setText("0.0");
		}
		if (data.getGrossWeight() != null) {
			this.tfGrossWeight.setValue(data.getGrossWeight());
		} else {
			this.tfGrossWeight.setValue(new Double(0));
		}
		if (data.getNetWeight() != null) {
			this.tfNetWeight.setValue(data.getNetWeight());
		} else {
			this.tfNetWeight.setValue(new Double(0));
		}
		if (data.getVersion() != null) {
			this.tfVersion.setText(data.getVersion());
		} else {
			this.tfVersion.setText("");
		}
		if (data.getSourceBill() != null) {
			this.tfSourceBill.setText(data.getSourceBill());
		} else {
			this.tfSourceBill.setText("");
		}
		if (data.getCubage() != null) {
			this.tfCubage.setValue(data.getCubage());
		} else {
			this.tfCubage.setValue(new Double(0));
		}
		if (data.getTransFactAmount() != null) {
			this.tfTransFactAmount.setValue(data.getTransFactAmount());
		} else {
			this.tfTransFactAmount.setValue(new Double(0));
		}
		this.cbbCurr.setSelectedItem(data.getCurr());
		if (data.getCountry() != null) {
			this.cbbCountry.setSelectedItem(data.getCountry());
		}
		String tfTransFactAmounts =String.valueOf(tfTransFactAmount.getValue());
		this.lbLeftAmount.setText(String.valueOf((leftAmount-Double.valueOf(tfTransFactAmounts))));
	}

	/**
	 * 填充数据
	 */
	private void fillData(TransferFactoryCommodityInfo data) {
		data.setMemo(this.tfMemo.getText());
		data.setQuantity(Double.valueOf(this.tfQuantity.getValue().toString()));
		data.setUnitPrice(Double
				.valueOf(this.tfUnitPrice.getValue().toString()));
		data.setCountry((Country) cbbCountry.getSelectedItem());
		data.setCurr((Curr) cbbCurr.getSelectedItem());
		data.setCubage(Double.valueOf(tfCubage.getValue().toString()));
		data
				.setGrossWeight(Double.valueOf(tfGrossWeight.getValue()
						.toString()));
		data.setNetWeight(Double.valueOf(tfNetWeight.getValue().toString()));
		data.setSourceBill(tfSourceBill.getText());
		data.setVersion(tfVersion.getText());
		data.setTransFactAmount(Double.valueOf(this.tfTransFactAmount
				.getValue().toString()));
	}

	private void saveData() {
		if (dataState == DataState.ADD) {
			TransferFactoryCommodityInfo data = new TransferFactoryCommodityInfo();
			fillData(data);
			data = this.transferFactoryManageAction
					.saveTransferFactoryCommodityInfo(new Request(CommonVars
							.getCurrUser()), data);
			tableModel.addRow(data);
		} else if (dataState == DataState.EDIT) {
			TransferFactoryCommodityInfo data = (TransferFactoryCommodityInfo) tableModel
					.getCurrentRow();
			fillData(data);
			data = this.transferFactoryManageAction
					.saveTransferFactoryCommodityInfo(new Request(CommonVars
							.getCurrUser()), data);
			tableModel.updateRow(data);
		}
	}

	private boolean validateOther() {
		if (Double.parseDouble(this.tfGrossWeight.getValue().toString()) < Double
				.parseDouble(this.tfNetWeight.getValue().toString())) {
			JOptionPane.showMessageDialog(null, "毛重不可小于净重!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if(null!=leftAmount){
			if (leftAmount<Double
					.parseDouble(this.tfTransFactAmount.getValue().toString())) {
				JOptionPane.showMessageDialog(null, "转厂数量不能大于可转厂数量!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	private boolean validateDataIsNull() {
		if (this.tfQuantity.getText().trim().equals("")
				|| this.tfQuantity.getText().trim().equals("0")) {
			JOptionPane.showMessageDialog(null, "数量不可为空", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfUnitPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "金额不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbCurr.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "币制不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.cbbCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "产销国(地区)不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfGrossWeight.getValue().toString() == null
				|| "".equals(this.tfGrossWeight.getValue().toString())) {
			JOptionPane.showMessageDialog(null, "毛重不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		if (this.tfNetWeight.getValue().toString() == null 
				|| "".equals(this.tfNetWeight.getValue().toString())) {
			JOptionPane.showMessageDialog(null, "净重不可为空!!", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * 统计数据
	 */
	private String getAmountStr() {
		String amountStr = "0";
		try {
			double amount = Double.parseDouble(this.tfTransFactAmount
					.getValue().toString())
					* Double.parseDouble(tfUnitPrice.getValue().toString());
			BigDecimal bd = new BigDecimal(amount);
			amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception ex) {
		}
		return amountStr;
	}

	/**
	 * 文档事件监听类
	 */
	class DocumentListenerAdapter implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			tfAmountPrice.setText(getAmountStr());
			tfGrossWeight.setValue(tfTransFactAmount.getValue());
			tfNetWeight.setValue(tfTransFactAmount.getValue());
		}

		public void removeUpdate(DocumentEvent e) {
			tfAmountPrice.setText(getAmountStr());
			tfGrossWeight.setValue(tfTransFactAmount.getValue());
			tfNetWeight.setValue(tfTransFactAmount.getValue());
		}

		public void changedUpdate(DocumentEvent e) {
			// tfAmountPrice.setText(getAmountStr());
		}

	}

	private void initComponents() {
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurr,
				"code", "name", 200);
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 150));
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry,
				"code", "name", 200);
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 150));

		// 国家默认为中国
		int count = this.cbbCountry.getItemCount();
		for (int i = 0; i < count; i++) {
			Country country = (Country) this.cbbCountry.getItemAt(i);
			if ("142".equals(country.getCode())) {
				this.cbbCountry.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTransFactAmount() {
		if (tfTransFactAmount == null) {
			tfTransFactAmount = new JCustomFormattedTextField();
			tfTransFactAmount.setBounds(new Rectangle(98, 152, 144, 22));
			tfTransFactAmount.setFormatterFactory(defaultFormatterFactory);
			tfUnitPrice.getDocument().addDocumentListener(
					new DocumentListenerAdapter());
		}
		return tfTransFactAmount;
	}

	/**
	 * This method initializes tfTransferFactoryBillNo1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new Rectangle(98, 81, 144, 22));
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfTransferFactoryBillNo11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSeqNum() {
		if (tfSeqNum == null) {
			tfSeqNum = new JTextField();
			tfSeqNum.setBounds(new Rectangle(327, 80, 136, 24));
			tfSeqNum.setEditable(false);
		}
		return tfSeqNum;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
