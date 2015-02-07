package com.bestway.client.fixasset;

import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.DeleteAgreementCommInfo;
import com.bestway.fixasset.entity.ImpAgreementCommInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.text.ParseException;

public class DgImpDelAgreementCommInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTextField tfComplex = null;

	private JTextField tfCommName = null;

	private JTextField tfCommSpec = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JCustomFormattedTextField tfAmount = null;

	private JCustomFormattedTextField tfUnitPrice = null;

	private JCustomFormattedTextField tfTotalPrice = null;

	private JComboBox cbbUnit = null;

	private JComboBox cbbCountry = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JTableListModel tableModel = null;

	private FixAssetAction fixAssetAction; // @jve:decl-index=0:
	
	private int dataState=DataState.BROWSE;

	private boolean isAdd = false;

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public DgImpDelAgreementCommInfo() {
		super();
		initialize();
	}

	public DgImpDelAgreementCommInfo(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgImpDelAgreementCommInfo(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgImpDelAgreementCommInfo(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(376, 303));
		this.setTitle("商品信息");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			Object obj = tableModel.getCurrentRow();
			if (obj != null) {
				if (obj instanceof DeleteAgreementCommInfo) {
					showData((DeleteAgreementCommInfo) obj);
				} else if (obj instanceof ImpAgreementCommInfo) {
					showData((ImpAgreementCommInfo) obj);
				}
			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(33, 182, 44, 25));
			jLabel8.setText("金额");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(33, 148, 44, 25));
			jLabel7.setText("单价");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(173, 149, 44, 25));
			jLabel6.setText("原产地");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(173, 114, 44, 25));
			jLabel4.setText("单位");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(33, 114, 30, 25));
			jLabel3.setText("数量");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(33, 79, 57, 23));
			jLabel2.setText("商品规格");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(33, 47, 57, 23));
			jLabel1.setText("商品名称");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(33, 15, 57, 25));
			jLabel.setText("商品编码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getTfCommName(), null);
			jContentPane.add(getTfCommSpec(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(getTfTotalPrice(), null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnCancel(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfComplex() {
		if (tfComplex == null) {
			tfComplex = new JTextField();
			tfComplex.setBounds(new Rectangle(88, 15, 242, 25));
			tfComplex.setEditable(false);
		}
		return tfComplex;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(new java.awt.Rectangle(88, 47, 242, 23));
			tfCommName.setEditable(false);
		}
		return tfCommName;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommSpec() {
		if (tfCommSpec == null) {
			tfCommSpec = new JTextField();
			tfCommSpec.setBounds(new java.awt.Rectangle(88, 79, 242, 23));
			tfCommSpec.setEditable(false);
		}
		return tfCommSpec;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new JCustomFormattedTextField();
			tfAmount.setBounds(new java.awt.Rectangle(88, 114, 73, 25));
			tfAmount.setFormatterFactory(getDefaultFormatterFactory());
			tfAmount.getDocument().addDocumentListener(new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					try {
						tfAmount.commitEdit();
					} catch (ParseException e1) {
					}
					tfTotalPrice.setValue(getTotalPrice());

				}

				public void removeUpdate(DocumentEvent e) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					try {
						tfAmount.commitEdit();
					} catch (ParseException e1) {
					}
					tfTotalPrice.setValue(getTotalPrice());

				}

				public void changedUpdate(DocumentEvent e) {

				}

			});
		}
		return tfAmount;
	}
	
	private Double getTotalPrice() {
		double unitPrice = (this.tfUnitPrice.getValue() == null ? 0 : Double
				.valueOf(this.tfUnitPrice.getValue().toString()));
		double amount = (this.tfAmount.getValue() == null ? 0 : Double
				.valueOf(this.tfAmount.getValue().toString()));
		return unitPrice * amount;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new JCustomFormattedTextField();
			tfUnitPrice.setBounds(new java.awt.Rectangle(88, 148, 73, 25));
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitPrice.getDocument().addDocumentListener(new DocumentListener() {

				public void insertUpdate(DocumentEvent e) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					try {
						tfUnitPrice.commitEdit();
					} catch (ParseException e1) {
					}
					tfTotalPrice.setValue(getTotalPrice());

				}

				public void removeUpdate(DocumentEvent e) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					try {
						tfUnitPrice.commitEdit();
					} catch (ParseException e1) {
					}
					tfTotalPrice.setValue(getTotalPrice());

				}

				public void changedUpdate(DocumentEvent e) {

				}

			});
		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes jCustomFormattedTextField3
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new java.awt.Rectangle(89, 182, 128, 25));
			tfTotalPrice.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTotalPrice;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new java.awt.Rectangle(216, 114, 113, 25));
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new java.awt.Rectangle(216, 149, 113, 25));
		}
		return cbbCountry;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(new java.awt.Rectangle(152, 227, 67, 26));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object obj = tableModel.getCurrentRow();
					if (obj != null) {
						if (obj instanceof DeleteAgreementCommInfo) {
							fillData((DeleteAgreementCommInfo) obj);
							obj=fixAssetAction.saveDeleteAgreementCommInfo(
									new Request(CommonVars.getCurrUser()),
									(DeleteAgreementCommInfo) obj);							
						} else if (obj instanceof ImpAgreementCommInfo) {
							fillData((ImpAgreementCommInfo) obj);
							obj=fixAssetAction.saveImpAgreementCommInfo(
									new Request(CommonVars.getCurrUser()),
									(ImpAgreementCommInfo) obj);
						}
						tableModel.updateRow(obj);
					}
					dispose();
				}
			});
		}
		return btnOk;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(267, 227, 65, 26));
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
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
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
		}
		return numberFormatter;
	}

	public boolean isAdd() {
		return isAdd;
	}

	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	private void setState() {

	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	private void showData(DeleteAgreementCommInfo commInfo) {
		if (commInfo == null) {
			return;
		}
		this.tfComplex.setText(commInfo.getComplex().getCode());
		this.tfCommName.setText(commInfo.getCommName());
		this.tfCommSpec.setText(commInfo.getCommSpec());
		this.tfAmount.setValue(commInfo.getAmount());
		this.tfUnitPrice.setValue(commInfo.getUnitPrice());
		this.tfTotalPrice.setValue(commInfo.getTotalPrice());
		this.cbbUnit.setSelectedItem(commInfo.getUnit());
		this.cbbCountry.setSelectedItem(commInfo.getCountry());
	}

	private void showData(ImpAgreementCommInfo commInfo) {
		if (commInfo == null) {
			return;
		}
		this.tfComplex.setText(commInfo.getComplex().getCode());
		this.tfCommName.setText(commInfo.getCommName());
		this.tfCommSpec.setText(commInfo.getCommSpec());
		this.tfAmount.setValue(commInfo.getAmount());
		this.tfUnitPrice.setValue(commInfo.getUnitPrice());
		this.tfTotalPrice.setValue(commInfo.getTotalPrice());
		this.cbbUnit.setSelectedItem(commInfo.getUnit());
		this.cbbCountry.setSelectedItem(commInfo.getCountry());
	}

	private void fillData(DeleteAgreementCommInfo commInfo) {
		if (commInfo == null) {
			return;
		}
		commInfo.setAmount(this.tfAmount.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfAmount.getValue().toString()));
		commInfo.setUnitPrice(this.tfUnitPrice.getValue() == null ? 0.0
				: Double.parseDouble(this.tfUnitPrice.getValue().toString()));
		commInfo.setTotalPrice(this.tfTotalPrice.getValue() == null ? 0.0
				: Double.parseDouble(this.tfTotalPrice.getValue().toString()));
		commInfo.setUnit((Unit) this.cbbUnit.getSelectedItem());
		commInfo.setCountry((Country) this.cbbCountry.getSelectedItem());
	}

	private void fillData(ImpAgreementCommInfo commInfo) {
		if (commInfo == null) {
			return;
		}
		commInfo.setAmount(this.tfAmount.getValue() == null ? 0.0 : Double
				.parseDouble(this.tfAmount.getValue().toString()));
		commInfo.setUnitPrice(this.tfUnitPrice.getValue() == null ? 0.0
				: Double.parseDouble(this.tfUnitPrice.getValue().toString()));
		commInfo.setTotalPrice(this.tfTotalPrice.getValue() == null ? 0.0
				: Double.parseDouble(this.tfTotalPrice.getValue().toString()));
		commInfo.setUnit((Unit) this.cbbUnit.getSelectedItem());
		commInfo.setCountry((Country) this.cbbCountry.getSelectedItem());
	}
	
	private void initUIComponents() {
		//初始化单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance()
				.getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbUnit);
		//初始化原产地
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCountry);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
