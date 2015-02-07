package com.bestway.client.fixasset;

import java.awt.Dialog;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgAgreementCommInfo extends JDialogBase {

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JTextField tfComplex = null;

	private JTextField tfCommName = null;

	private JTextField tfCommSpec = null;

	private JCustomFormattedTextField tfAmount = null;

	private JCustomFormattedTextField tfUnitPrice = null;

	private JCustomFormattedTextField tfTotalPrice = null;

	private JComboBox cbbUnit = null;

	private JComboBox cbbCountry = null;

	private JButton btnOk = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private FixAssetAction fixAssetAction;

	private int dataState = DataState.BROWSE;

	public DgAgreementCommInfo() {
		super();
		initialize();
	}

	public DgAgreementCommInfo(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgAgreementCommInfo(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgAgreementCommInfo(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(430, 255));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("设备明细");
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			showData();
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
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(215, 131, 51, 21));
			jLabel7.setText("原产地");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(215, 98, 51, 21));
			jLabel6.setText("金额");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(22, 133, 55, 20));
			jLabel5.setText("单位");
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(22, 96, 55, 20));
			jLabel4.setText("单价");
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(215, 63, 51, 21));
			jLabel3.setText("数量");
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(22, 63, 55, 20));
			jLabel2.setText("设备规格");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(215, 28, 51, 21));
			jLabel1.setText("设备名称");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(22, 28, 55, 20));
			jLabel.setText("商品编码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getTfCommName(), null);
			jContentPane.add(getTfCommSpec(), null);
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
			tfComplex.setBounds(new java.awt.Rectangle(78, 28, 125, 25));
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
			tfCommName.setBounds(new java.awt.Rectangle(267, 27, 130, 24));
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
			tfCommSpec.setBounds(new java.awt.Rectangle(78, 62, 125, 25));
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
			tfAmount.setBounds(new java.awt.Rectangle(267, 62, 130, 24));
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

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new JCustomFormattedTextField();
			tfUnitPrice.setBounds(new java.awt.Rectangle(78, 94, 125, 25));
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitPrice.getDocument().addDocumentListener(
					new DocumentListener() {

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
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalPrice() {
		if (tfTotalPrice == null) {
			tfTotalPrice = new JCustomFormattedTextField();
			tfTotalPrice.setBounds(new java.awt.Rectangle(267, 97, 130, 24));
			tfTotalPrice.setEditable(true);
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
			cbbUnit.setBounds(new java.awt.Rectangle(78, 130, 125, 25));
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
			cbbCountry.setBounds(new java.awt.Rectangle(267, 130, 130, 24));
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
			btnOk.setBounds(new java.awt.Rectangle(98, 176, 65, 24));
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(!checkDate())
						return;
					AgreementCommInfo commInfo = (AgreementCommInfo) tableModel
							.getCurrentRow();
					fillData(commInfo);
					commInfo = fixAssetAction.saveAgreementCommInfo(
							new Request(CommonVars.getCurrUser()), commInfo);
					tableModel.updateRow(commInfo);
					dispose();
				}
			});
		}
		return btnOk;
	}

	private boolean checkDate() {
		if (tfAmount.getText().equals("") || tfAmount.getText() == null) {
			JOptionPane.showMessageDialog(this, "数量不可为空", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (tfUnitPrice.getText().equals("") || tfUnitPrice.getText() == null) {
			JOptionPane.showMessageDialog(this, "单价不可为空", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (cbbUnit.getSelectedItem()== null) {
			JOptionPane.showMessageDialog(this, "单位不可为空", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		return true;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(246, 176, 65, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
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
		}
		return numberFormatter;
	}

	public void showData() {
		AgreementCommInfo commInfo = (AgreementCommInfo) tableModel
				.getCurrentRow();
		this.tfComplex.setText(commInfo.getComplex().getCode());
		this.tfCommName.setText(commInfo.getCommName());
		this.tfCommSpec.setText(commInfo.getCommSpec());
		this.tfAmount.setValue(commInfo.getAmount());
		this.tfUnitPrice.setValue(commInfo.getUnitPrice());
		this.tfTotalPrice.setValue(commInfo.getTotalPrice());
		this.cbbUnit.setSelectedItem(commInfo.getUnit());
		this.cbbCountry.setSelectedItem(commInfo.getCountry());
	}

	public void fillData(AgreementCommInfo commInfo) {
		if (commInfo == null) {
			return;
		}
		commInfo.setCommName(this.tfCommName.getText());
		commInfo.setCommSpec(this.tfCommSpec.getText());
		commInfo.setAmount(this.tfAmount.getValue() == null ? 0 : Double
				.parseDouble(this.tfAmount.getValue().toString()));
		commInfo.setUnitPrice(this.tfUnitPrice.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitPrice.getValue().toString()));
		commInfo.setTotalPrice(this.tfTotalPrice.getValue() == null ? 0
				: Double.parseDouble(this.tfTotalPrice.getValue().toString()));
		commInfo.setUnit((Unit) this.cbbUnit.getSelectedItem());
		commInfo.setCountry((Country) this.cbbCountry.getSelectedItem());
	}

	private Double getTotalPrice() {
		double unitPrice = (this.tfUnitPrice.getValue() == null ? 0 : Double
				.valueOf(this.tfUnitPrice.getValue().toString()));
		double amount = (this.tfAmount.getValue() == null ? 0 : Double
				.valueOf(this.tfAmount.getValue().toString()));
		return unitPrice * amount;
	}

	private void initUIComponents() {
		// 原产地
		List lsCountry = CustomBaseList.getInstance().getCountrys();
		cbbCountry.setModel(new DefaultComboBoxModel(lsCountry.toArray()));
		cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry,
				"code", "name");
		cbbCountry.setSelectedIndex(-1);

		// 单位
		List lsUnit = CustomBaseList.getInstance().getUnits();
		cbbUnit.setModel(new DefaultComboBoxModel(lsUnit.toArray()));
		cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender("code",
				"name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit,
				"code", "name");
		cbbUnit.setSelectedIndex(-1);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
