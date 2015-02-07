/*
 * Created on 2005-5-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractcav;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgContractInteralBuy extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JButton btnCommCode = null;
	private JLabel jLabel1 = null;
	private JTextField tfCommCode = null;
	private JLabel jLabel2 = null;
	private JTextField tfCommName = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTextField tfCommSpec = null;
	private JFormattedTextField tfAmount = null;
	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:
	private JComboBox cbbUnit = null;
	private JButton btnOK = null;
	private JButton btnCancel = null;
	private ContractCavAction contractCavAction = null;
	private ContractCav contractCav = null;
	private ContractImgCav contractImgCav = null;
	private Complex complex = null;
	private JTableListModel tableModel = null;
	private JLabel jLabel = null;
	private JTextField tfSequm = null;

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
	 * @return Returns the contractCav.
	 */
	public ContractCav getContractCav() {
		return contractCav;
	}

	/**
	 * @param contractCav
	 *            The contractCav to set.
	 */
	public void setContractCav(ContractCav contractCav) {
		this.contractCav = contractCav;
	}

	/**
	 * This is the default constructor
	 */
	public DgContractInteralBuy() {
		super();
		initialize();
		initUIComponents();
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("增加料件");
		this.setSize(323, 234);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(181, 96, 28, 22)); // Generated
			jLabel.setText("序号"); // Generated
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(37, 21, 53, 22);
			jLabel1.setText("商品编码");
			jLabel2.setBounds(37, 46, 53, 22);
			jLabel2.setText("料件名称");
			jLabel3.setBounds(37, 71, 53, 22);
			jLabel3.setText("型号规格");
			jLabel4.setBounds(37, 96, 53, 22);
			jLabel4.setText("单位");
			jLabel5.setBounds(37, 122, 53, 22);
			jLabel5.setText("数量");
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnCommCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfCommCode(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfCommName(), null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getTfCommSpec(), null);
			jContentPane.add(getTfAmount(), null);
			jContentPane.add(getCbbUnit(), null);
			jContentPane.add(jLabel, null); // Generated
			jContentPane.add(getTfSequm(), null); // Generated
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCommCode() {
		if (btnCommCode == null) {
			btnCommCode = new JButton();
			btnCommCode.setBounds(254, 21, 23, 22);
			btnCommCode.setText("...");
			btnCommCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ContractImgCav contractImgCav = (ContractImgCav) CommonQuery
							.getInstance().getContractImgCav(
									tableModel.getList());
					if (contractImgCav != null) {
						complex = contractImgCav.getComplex();
						tfCommCode.setText(complex.getCode());
						tfCommName.setText(contractImgCav.getName());
						tfCommSpec.setText(contractImgCav.getSpec());
						cbbUnit.setSelectedItem(contractImgCav.getUnit());
						getTfSequm()
								.setText(
										contractImgCav.getSeqNum() == null ? ""
												: contractImgCav.getSeqNum()
														.toString());
					}
				}
			});
		}
		return btnCommCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommCode() {
		if (tfCommCode == null) {
			tfCommCode = new JTextField();
			tfCommCode.setBounds(92, 21, 163, 22);
			tfCommCode.setEditable(false);
			tfCommCode.setBackground(java.awt.Color.white);
		}
		return tfCommCode;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(92, 46, 185, 22);
			tfCommName.setEditable(false);
			tfCommName.setBackground(java.awt.Color.white);
		}
		return tfCommName;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommSpec() {
		if (tfCommSpec == null) {
			tfCommSpec = new JTextField();
			tfCommSpec.setBounds(92, 71, 185, 22);
			tfCommSpec.setEditable(false);
			tfCommSpec.setBackground(java.awt.Color.white);
		}
		return tfCommSpec;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setBounds(92, 122, 185, 22);
			tfAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfAmount;
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(92, 96, 83, 22);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(39, 158, 68, 24);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (complex == null) {
						JOptionPane.showMessageDialog(
								DgContractInteralBuy.this, "请选择商品编码", "提示",
								JOptionPane.OK_OPTION);
						return;
					}
					contractImgCav = new ContractImgCav();
					contractImgCav.setContractCav(contractCav);
					contractImgCav.setIsCustoms(new Boolean(true));
					contractImgCav.setComplex(complex);
					contractImgCav.setName("(国内购买)" + tfCommName.getText());
					contractImgCav.setSpec(tfCommSpec.getText());
					contractImgCav.setUnit((Unit) cbbUnit.getSelectedItem());
					contractImgCav
							.setImpTotal(tfAmount.getValue() == null ? new Double(
									0)
									: Double.parseDouble(tfAmount.getValue()
											.toString()));

					try {
						Integer.parseInt(tfSequm.getText());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(
								DgContractInteralBuy.this, "序号不合法！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					contractImgCav.setSeqNum(Integer
							.parseInt(tfSequm.getText()));
					contractImgCav = contractCavAction.saveContractImgCav(
							new Request(CommonVars.getCurrUser()),
							contractImgCav);
					contractCavAction.addContractBomCav(new Request(CommonVars
							.getCurrUser()), contractCav, contractImgCav);
					dispose();
				}
			});
		}
		return btnOK;
	}

	public ContractImgCav getContractImgCav() {
		return contractImgCav;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(203, 158, 68, 24);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private void initUIComponents() {
		// 初始化单位
		this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUnit);
	}

	/**
	 * This method initializes tfSequm
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSequm() {
		if (tfSequm == null) {
			tfSequm = new JTextField();
			tfSequm.setBounds(new Rectangle(212, 97, 65, 22)); // Generated
			tfSequm.setEditable(false);
			tfSequm.setBackground(java.awt.Color.white);
		}
		return tfSequm;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
