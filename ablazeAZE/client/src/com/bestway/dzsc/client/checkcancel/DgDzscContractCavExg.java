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
import com.bestway.dzsc.checkcancel.entity.DzscContractExgCav;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Dimension;

public class DgDzscContractCavExg extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnSave = null;

	private JButton btnCancel = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JTextField tfName = null;

	private JLabel jLabel1 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JCustomFormattedTextField tfTotalAmount = null;

	private JCustomFormattedTextField tfTransferFactoryAmount = null;

	private JCustomFormattedTextField tfBackFactoryRework = null;

	private JCustomFormattedTextField tfReworkExport = null;

	private JLabel jLabel6 = null;

	private JTextField tfUnit = null;

	private JButton btnEdit = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JTableListModel tableModel = null;

	private DzscContractCavAction contractCavAction = null;

	private int dataState = DataState.BROWSE;

	private boolean isShowDataChange = false;

	private JLabel jLabel61 = null;

	private JCustomFormattedTextField tfStockAmount = null;

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
	public DgDzscContractCavExg() {
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
		this.setSize(new Dimension(416, 351));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("成品核销修改");
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
			jLabel61.setBounds(new Rectangle(49, 241, 78, 19));
			jLabel61.setText("库存数量");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(49, 212, 78, 19));
			jLabel6.setText("返工复出");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(49, 180, 78, 19));
			jLabel5.setText("退厂返工");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(49, 149, 78, 19));
			jLabel4.setText("结转出口数量");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(49, 117, 78, 19));
			jLabel3.setText("出口总数");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(49, 87, 78, 19));
			jLabel2.setText("计量单位");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(49, 54, 78, 19));
			jLabel1.setText("规格型号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(49, 25, 78, 19));
			jLabel.setText("商品名称");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getTfName(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfSpec(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfTotalAmount(), null);
			jPanel.add(getTfTransferFactoryAmount(), null);
			jPanel.add(getTfBackFactoryRework(), null);
			jPanel.add(getTfReworkExport(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfUnit(), null);
			jPanel.add(jLabel61, null);
			jPanel.add(getTfStockAmount(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new java.awt.Rectangle(134, 22, 217, 23));
			tfName.setEditable(false);
		}
		return tfName;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setBounds(new java.awt.Rectangle(135, 53, 217, 23));
			tfSpec.setEditable(false);
		}
		return tfSpec;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalAmount() {
		if (tfTotalAmount == null) {
			tfTotalAmount = new JCustomFormattedTextField();
			tfTotalAmount.setBounds(new java.awt.Rectangle(135, 116, 217, 23));
			tfTotalAmount.setEditable(true);
			tfTotalAmount.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTotalAmount;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTransferFactoryAmount() {
		if (tfTransferFactoryAmount == null) {
			tfTransferFactoryAmount = new JCustomFormattedTextField();
			tfTransferFactoryAmount.setBounds(new java.awt.Rectangle(135, 148,
					217, 23));
			tfTransferFactoryAmount.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfTransferFactoryAmount.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}

						public void removeUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfTransferFactoryAmount.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}

						}

						public void changedUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfTransferFactoryAmount.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}
					});
		}
		return tfTransferFactoryAmount;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfBackFactoryRework() {
		if (tfBackFactoryRework == null) {
			tfBackFactoryRework = new JCustomFormattedTextField();
			tfBackFactoryRework.setBounds(new java.awt.Rectangle(136, 179, 217,
					23));
			tfBackFactoryRework.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfBackFactoryRework.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}

						public void removeUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfBackFactoryRework.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}

						public void changedUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfBackFactoryRework.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}
					});
		}
		return tfBackFactoryRework;
	}

	/**
	 * This method initializes jCustomFormattedTextField3
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfReworkExport() {
		if (tfReworkExport == null) {
			tfReworkExport = new JCustomFormattedTextField();
			tfReworkExport.setBounds(new java.awt.Rectangle(136, 211, 217, 23));
			tfReworkExport.getDocument().addDocumentListener(
					new DocumentListener() {
						public void insertUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfReworkExport.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}

						public void removeUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfReworkExport.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}

						public void changedUpdate(DocumentEvent e) {

							if (!isShowDataChange) {
								try {
									tfReworkExport.commitEdit();
								} catch (ParseException e1) {
								}
								calExpTotalAmount();
							}
						}
					});
		}
		return tfReworkExport;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfUnit() {
		if (tfUnit == null) {
			tfUnit = new JTextField();
			tfUnit.setBounds(new java.awt.Rectangle(135, 84, 217, 23));
			tfUnit.setEditable(false);
		}
		return tfUnit;
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

	private DzscContractExgCav getContractExgCav() {
		return (DzscContractExgCav) tableModel.getCurrentRow();
	}

	private void fillData(DzscContractExgCav exgCav) {
		if (exgCav == null) {
			return;
		}
		exgCav
				.setTransferExpAmount(convertObjToDouble(this.tfTransferFactoryAmount
						.getValue()));
		exgCav.setBackFactoryRework(convertObjToDouble(this.tfBackFactoryRework
				.getValue()));
		exgCav.setReworkExport(convertObjToDouble(this.tfReworkExport
				.getValue()));
		exgCav.setExpTotal(convertObjToDouble(this.tfTotalAmount.getValue()));
		exgCav.setStockAmount(convertObjToDouble(this.tfStockAmount.getValue()));
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
		DzscContractExgCav exgCav = this.getContractExgCav();
		// List list=contractAction.findContractExgBySeqNum(new Request(
		// CommonVars.getCurrUser(),true),exgCav.getContractCav().getEmsNo(),exgCav.getSeqNum());
		// double directExport=0.0;
		// if(list.size()>0){
		// ContractExg exg=(ContractExg)list.get(0);
		// if(exg!=null){
		// directExport=(exg.getExportAmount()==null?0.0:exg.getExportAmount());
		// }
		// }
		// double directExport = getDoubleExceptNull(exgCav.getExpTotal())
		// - (getDoubleExceptNull(exgCav.getTransferExpAmount())
		// - getDoubleExceptNull(exgCav.getBackFactoryRework()) +
		// getDoubleExceptNull(exgCav
		// .getReworkExport()));
		double directExport = (exgCav.getDirectExport() == null ? 0.0 : exgCav
				.getDirectExport());
		double totalAmount = directExport
				+ convertObjToDouble(this.tfTransferFactoryAmount.getValue())
				- convertObjToDouble(this.tfBackFactoryRework.getValue())
				+ convertObjToDouble(this.tfReworkExport.getValue());
		this.tfTotalAmount.setValue(totalAmount);
	}

	private void showData() {
		isShowDataChange = true;
		DzscContractExgCav exgCav = this.getContractExgCav();
		if (exgCav != null) {
			this.tfName.setText(exgCav.getName());
			this.tfSpec.setText(exgCav.getSpec());
			this.tfUnit.setText(exgCav.getUnit() == null ? "" : exgCav
					.getUnit().getName());
			this.tfTotalAmount.setValue(exgCav.getExpTotal());
			this.tfBackFactoryRework.setValue(exgCav.getBackFactoryRework());
			this.tfReworkExport.setValue(exgCav.getReworkExport());
			this.tfTransferFactoryAmount
					.setValue(exgCav.getTransferExpAmount());
			this.tfStockAmount.setValue(exgCav.getStockAmount());
		} else {
			this.tfName.setText("");
			this.tfSpec.setText("");
			this.tfUnit.setText("");
			this.tfTotalAmount.setValue(null);
			this.tfBackFactoryRework.setValue(null);
			this.tfReworkExport.setValue(null);
			this.tfTransferFactoryAmount.setValue(null);
			this.tfStockAmount.setValue(null);
		}
		isShowDataChange = false;
	}

	private void setState() {
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnCancel.setEnabled(dataState != DataState.BROWSE);
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.tfTransferFactoryAmount.setEditable(dataState != DataState.BROWSE);
		this.tfBackFactoryRework.setEditable(dataState != DataState.BROWSE);
		this.tfReworkExport.setEditable(dataState != DataState.BROWSE);
		this.tfStockAmount.setEditable(dataState != DataState.BROWSE);
	}

	private void saveData() {
		DzscContractExgCav exgCav = this.getContractExgCav();
		this.fillData(exgCav);
		exgCav = this.contractCavAction.saveContractExgCav(new Request(
				CommonVars.getCurrUser()), exgCav);
		tableModel.updateRow(exgCav);
	}

	/**
	 * This method initializes tfReworkExport1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfStockAmount() {
		if (tfStockAmount == null) {
			tfStockAmount = new JCustomFormattedTextField();
			tfStockAmount.setBounds(new Rectangle(136, 240, 217, 23));
		}
		return tfStockAmount;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
