/*
 * Created on 2005-3-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.fixtureonorder.entity.FixtureContractItems;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Dimension;

/**
 * @author fhz
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFixtureContractItems extends JDialogBase {

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

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel10 = null;

	private JTextField tfSpec = null;

	private JLabel jLabel13 = null;

	private JTextField tfComplex = null;

	private JFormattedTextField tfUnitPrice = null;

	private DefaultFormatterFactory defaultFormatterFactory = null;

	private NumberFormatter numberFormatter = null;  //  @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private int dataState = -1;

	private FixtureContractAction fixtureContractAction = null; // 合同

	private FixtureContractItems fixtureContractItems = null; // 合同料件对象

	private JFormattedTextField tfMaterielAmount = null;

	private JLabel jLabel14 = null;

	private JTextField tfMemo = null;

	private NumberFormatter numberFormatter1 = null;

	private JFormattedTextField tfTotalMoney = null;

	private JComboBox cbbCountry = null;

	private NumberFormatter numberFormatter2 = null; // @jve:decl-index=0:

	private DefaultFormatterFactory defaultFormatterFactory1 = null; // @jve:decl-index=0:

	private JComboBox cbbLevyMode = null;

	private JLabel jLabel5 = null;

	private FixtureContract fixtureContract = null; // 合同对象

	private Complex complex = null;

	private JLabel jLabel11 = null;

	private JComboBox cbbUnit = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private JButton btnEdit = null;

	private JButton btnCancel = null;

	private boolean isEditable = false;

	private boolean isShowDataChange = false;

	private JLabel jLabel15 = null;

	private JTextField jTextField1 = null;

	private double originalMount = 0.0; // @jve:decl-index=0:

	private JComboBox cbbKind = null;

	private JLabel jLabel111 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel8 = null;

	private JTextField tfProtocol = null;
	
	private JCalendarComboBox cbbDeclareDate = null;

	public void setIsEditable(boolean is) {
		this.isEditable = is;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgFixtureContractItems() {
		super();
		initialize();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("合同设备");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(560, 310);
	}

	public void setVisible(boolean b) {
		if (b) {
			showData();
			setState();

		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the contract.
	 */
	public FixtureContract getFixtureContract() {
		return fixtureContract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setFixtureContract(FixtureContract fixtureContract) {
		this.fixtureContract = fixtureContract;
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
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(302, 94, 72, 23));
			jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel8.setText("进口日期");
			jLabel8.setForeground(Color.BLUE);
			jLabel3 = new JLabel();			
			jLabel3.setBounds(new Rectangle(38, 94, 72, 23));
			jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel3.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel3.setText("分协议号");
			jLabel3.setForeground(Color.BLUE);
			jLabel111 = new JLabel();
			jLabel111.setBounds(new Rectangle(38, 210, 72, 23));
			jLabel111.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel111.setText("设备类型");
			jLabel111.setForeground(Color.blue);
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(38, 241, 72, 23));
			jLabel15.setText("详细型号规格");
			jLabel11 = new JLabel();
			jLabel5 = new JLabel();
			jLabel14 = new JLabel();
			jContentPane = new JPanel();
			jLabel = new JLabel();
			jLabel1 = new JLabel();
			jLabel2 = new JLabel();
			jLabel4 = new JLabel();
			jLabel6 = new JLabel();
			jLabel7 = new JLabel();
			jLabel10 = new JLabel();
			jLabel13 = new JLabel();
			jContentPane.setLayout(null);
			jLabel.setBounds(38, 40, 72, 23);
			jLabel.setText("序号");
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(38, 67, 72, 23);
			jLabel1.setText("商品名称");
			jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setBounds(38, 151, 72, 23);
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("单价");
			jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(302, 40, 72, 23);
			jLabel4.setText("商品编码");
			jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(302, 123, 72, 23);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setText("单位");
			jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel7.setBounds(302, 67, 72, 23);
			jLabel7.setText("型号规格");
			jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(38, 123, 72, 23);
			jLabel10.setForeground(java.awt.Color.blue);
			jLabel10.setText("数量");
			jLabel10
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setBounds(302, 151, 72, 23);
			jLabel13.setForeground(java.awt.Color.blue);
			jLabel13.setText("总金额");
			jLabel13
					.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel14.setBounds(302, 180, 72, 23);
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel14.setText("征免方式");
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setBounds(302, 210, 72, 23);
			jLabel5.setText("备注");
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setBounds(38, 180, 72, 23);
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel11.setText("原产国");
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfSeqNum(), null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getTfUnitPrice(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(getTfSpec(), null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(getTfComplex(), null);
			jContentPane.add(getTfMaterielAmount(), null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(getTfMemo(), null);
			jContentPane.add(getTfTotalMoney(), null);
			jContentPane.add(getCbbCountry(), null);
			jContentPane.add(getCbbLevyMode(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getCbbKind(), null);
			jContentPane.add(jLabel111, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getTfProtocol(), null);
			jContentPane.add(getCbbDeclareDate(), null);
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
			jToolBar.setBounds(4, 2, 607, 33);
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
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
					if (validateData() == false) {
						return;
					}
					fillData();
					if (dataState == DataState.EDIT
							&& fixtureContractItems.getContract()
									.getDeclareState().equals(
											DeclareState.CHANGING_EXE)) {
						if (fixtureContractItems.getChangeAmount() != null
								&& (Double.valueOf(tfMaterielAmount.getValue()
										.toString()) - fixtureContractItems
										.getChangeAmount()) != 0) {
							fixtureContractItems.setIsChange(true);
						} else if (fixtureContractItems.getChangeAmount() == null) {
							fixtureContractItems.setIsChange(true);
							fixtureContractItems.setChangeAmount(Double
									.valueOf(tfMaterielAmount.getValue()
											.toString()));
						}
					}
					saveData();
					dataState = DataState.BROWSE;
					setState();
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
					DgFixtureContractItems.this.dispose();
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
			tfSeqNum.setBounds(116, 40, 146, 23);
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
			tfName.setEditable(true);
			tfName.setBounds(116, 67, 146, 23);
		}
		return tfName;
	}
	
	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeclareDate() {
		if (cbbDeclareDate == null) {
			cbbDeclareDate = new JCalendarComboBox();
			cbbDeclareDate.setDate(null);
			cbbDeclareDate.setBounds(383, 94, 146, 23);			
		}
		return cbbDeclareDate;
	}

	/**
	 * This method initializes tfSpec
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSpec() {
		if (tfSpec == null) {
			tfSpec = new JTextField();
			tfSpec.setEditable(true);
			tfSpec.setBounds(383, 67, 146, 23);
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
			tfComplex.setBounds(383, 40, 146, 23);
			tfComplex.setEditable(false);
		}
		return tfComplex;
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
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			// cbbUnit.setEnabled(false);
			cbbUnit.setBounds(383, 123, 146, 23);
		}
		return cbbUnit;
	}

	/**
	 * This method initializes tfUnitPrice
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitPrice() {
		if (tfUnitPrice == null) {
			tfUnitPrice = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitPrice.setBounds(116, 151, 146, 23);
			tfUnitPrice.setValue(new Double(0));
			tfUnitPrice.setFormatterFactory(getDefaultFormatterFactory1());
			tfUnitPrice.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							if (isShowDataChange) {
								return;
							}
							try {
								tfUnitPrice.commitEdit();
							} catch (ParseException e1) {
							}
							tfTotalMoney.setValue(getTotalPrice());

						}

						public void removeUpdate(DocumentEvent e) {
							try {
								tfUnitPrice.commitEdit();
							} catch (ParseException e1) {
							}
							tfTotalMoney.setValue(getTotalPrice());
						}

						public void changedUpdate(DocumentEvent e) {
							try {
								tfUnitPrice.commitEdit();
							} catch (ParseException e1) {
							}
							tfTotalMoney.setValue(getTotalPrice());
						}
					});

		}
		return tfUnitPrice;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfMaterielAmount() {
		if (tfMaterielAmount == null) {
			tfMaterielAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfMaterielAmount.setBounds(116, 123, 146, 23);
			tfMaterielAmount.setValue(new Double(0));
			tfMaterielAmount.setFormatterFactory(getDefaultFormatterFactory1());
			tfMaterielAmount.getDocument().addDocumentListener(
					new DocumentListener() {
						public void insertUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							if (isShowDataChange) {
								return;
							}
							try {
								tfMaterielAmount.commitEdit();
							} catch (ParseException e1) {
							}
							tfTotalMoney.setValue(getTotalPrice());

						}

						public void removeUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							if (isShowDataChange) {
								return;
							}
							try {
								tfMaterielAmount.commitEdit();
							} catch (ParseException e1) {
							}
							tfTotalMoney.setValue(getTotalPrice());
						}

						public void changedUpdate(DocumentEvent e) {
							if (dataState == DataState.BROWSE) {
								return;
							}
							if (isShowDataChange) {
								return;
							}
							try {
								tfMaterielAmount.commitEdit();
							} catch (ParseException e1) {
							}
							tfTotalMoney.setValue(getTotalPrice());
						}
					});
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
			tfMemo.setBounds(383, 210, 146, 23);
		}
		return tfMemo;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfTotalMoney() {
		if (tfTotalMoney == null) {
			tfTotalMoney = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfTotalMoney.setBounds(383, 151, 146, 23);
			tfTotalMoney.setFormatterFactory(getDefaultFormatterFactory1());
			tfTotalMoney.setValue(new Double(0));
			// tfTotalMoney.setEditable(false);
		}
		return tfTotalMoney;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(116, 180, 146, 23);
		}
		return cbbCountry;
	}

	/**
	 * This method initializes numberFormatter1
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter1() {
		if (numberFormatter1 == null) {
			numberFormatter1 = new NumberFormatter();
		}
		return numberFormatter1;
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb.isSelected();
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			fireEditingStopped();
		}
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 初始化国
		this.cbbCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry);
		this.cbbCountry.setSelectedItem(null);

		// 设备类型
		this.cbbKind.addItem("不作价设备");
		this.cbbKind.addItem("借用设备");
		this.cbbKind.setSelectedIndex(0);

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
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		isShowDataChange = true;
		fixtureContractItems = (FixtureContractItems) tableModel
				.getCurrentRow();
		if (this.dataState == DataState.ADD) {
			// int seqNum = this.fixtureContractAction.getMaxContractImgSeqNum(
			// new Request(CommonVars.getCurrUser()), fixtureContract
			// .getId()) + 1;
			this.tfSeqNum.setText(fixtureContractItems.getSeqNum().toString());
			this.tfName.setText(this.fixtureContractItems.getName());
			complex = this.fixtureContractItems.getComplex();
			this.tfComplex.setText(this.fixtureContractItems.getComplex()
					.getCode());

		} else if (this.dataState == DataState.EDIT
				|| this.dataState == DataState.BROWSE) {
			// this.fixtureContractItems = (FixtureContractItems)
			// this.tableModel
			// .getCurrentRow();
			this.fixtureContract = fixtureContractItems.getContract();
			if (this.fixtureContractItems.getUnit() != null) {
				this.cbbUnit.setSelectedItem((Unit) fixtureContractItems
						.getUnit());
			} else {
				this.cbbUnit.setSelectedItem(null);
			}

			// 序号
			if (this.fixtureContractItems.getSeqNum() != null) {
				this.tfSeqNum.setText(this.fixtureContractItems.getSeqNum()
						.toString());
			} else {
				this.tfSeqNum.setText("");
			}
			// 商品名称
			if (this.fixtureContractItems.getName() != null) {
				this.tfName.setText(this.fixtureContractItems.getName());
			} else {
				this.tfName.setText("");
			}
			// 商品规格
			if (this.fixtureContractItems.getSpec() != null) {
				this.tfSpec.setText(this.fixtureContractItems.getSpec());
			} else {
				this.tfSpec.setText("");
			}
			// 详细型号规格
			if (this.fixtureContractItems.getDetailNote() != null) {
				this.jTextField1.setText(this.fixtureContractItems
						.getDetailNote());
			} else {
				this.jTextField1.setText("");
			}
			// 商品编码
			if (this.fixtureContractItems.getComplex() != null) {
				complex = this.fixtureContractItems.getComplex();
				this.tfComplex.setText(this.fixtureContractItems.getComplex()
						.getCode());
			} else {
				this.tfComplex.setText("");
			}
			// 单位净重
			// 原产地
			if (fixtureContractItems.getCountry() != null) {
				this.cbbCountry.setSelectedItem(this.fixtureContractItems
						.getCountry());
			} else {
				this.cbbCountry.setSelectedIndex(-1);
			}

			// 设备类型
			if (fixtureContractItems.getFixKind() != null) {
				this.cbbKind.setSelectedIndex(this.fixtureContractItems
						.getFixKind().intValue());
			} else {
				this.cbbKind.setSelectedIndex(-1);
			}

			// 料件数量
			if (this.fixtureContractItems.getAmount() != null) {
				this.tfMaterielAmount.setValue(this.fixtureContractItems
						.getAmount());
			} else {
				this.tfMaterielAmount.setValue(new Double(0));
			}
			// 申报单价
			if (this.fixtureContractItems.getDeclarePrice() != null) {
				this.tfUnitPrice.setValue(this.fixtureContractItems
						.getDeclarePrice());
			} else {
				this.tfUnitPrice.setValue(new Double(0));
			}
			// 总金额
			if (this.fixtureContractItems.getTotalPrice() != null) {
				this.tfTotalMoney.setValue(this.fixtureContractItems
						.getTotalPrice());
			} else {
				this.tfTotalMoney.setValue(new Double(0));
			}
			// 征减免税方式
			if (this.fixtureContractItems.getLevyMode() != null) {
				this.cbbLevyMode.setSelectedItem(this.fixtureContractItems
						.getLevyMode());
			} else {
				this.cbbLevyMode.setSelectedItem(null);
			}

			// 说明
			if (this.fixtureContractItems.getNote() != null) {
				this.tfMemo.setText(this.fixtureContractItems.getNote());
			} else {
				this.tfMemo.setText(null);
			}
			
			this.tfProtocol.setText(fixtureContractItems.getSecondProtocol());			
			this.cbbDeclareDate.setDate(fixtureContractItems.getImportDate());

		}
		isShowDataChange = false;
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		if (this.fixtureContractItems == null) {
			fixtureContractItems = new FixtureContractItems();
			fixtureContractItems.setContract(fixtureContract);
			fixtureContractItems.setCompany(CommonVars.getCurrUser()
					.getCompany());
		}
		this.fixtureContractItems
				.setUnit((Unit) this.cbbUnit.getSelectedItem());
		// 商品编码
		this.fixtureContractItems.setComplex(complex);
		// 商品名称
		fixtureContractItems.setName(this.tfName.getText());
		// 详细型号规格
		fixtureContractItems.setDetailNote(this.jTextField1.getText());
		// 商品规格
		fixtureContractItems.setSpec(this.tfSpec.getText());
		// 原产地
		this.fixtureContractItems.setCountry((Country) this.cbbCountry
				.getSelectedItem());
		// 设备类型
		this.fixtureContractItems.setFixKind(new Integer(this.cbbKind
				.getSelectedIndex()));
		// 料件数量
		this.fixtureContractItems.setAmount(Double
				.valueOf(this.tfMaterielAmount.getValue().toString()));
		// 申报单价
		this.fixtureContractItems.setDeclarePrice(Double
				.valueOf(this.tfUnitPrice.getValue().toString()));
		// 合同总价
		this.fixtureContractItems.setTotalPrice(Double
				.valueOf(this.tfTotalMoney.getValue().toString()));
		// 征减免税方式
		this.fixtureContractItems.setLevyMode((LevyMode) this.cbbLevyMode
				.getSelectedItem());
		// 说明
		this.fixtureContractItems.setNote(this.tfMemo.getText());
		//分协议
		this.fixtureContractItems.setSecondProtocol(this.tfProtocol.getText().trim());
		//进口日期
		this.fixtureContractItems.setImportDate(this.cbbDeclareDate.getDate());
	}

	/**
	 * 保存数据
	 * 
	 */
	private void saveData() {
		// if (this.dataState == DataState.ADD) {
		// int seqNum = this.fixtureContractAction.getMaxContractImgSeqNum(
		// new Request(CommonVars.getCurrUser()), fixtureContract
		// .getId()) + 1;
		// fixtureContractItems.setSeqNum(seqNum);
		// fixtureContractItems = this.fixtureContractAction.saveContractImg(
		// new Request(CommonVars.getCurrUser()),
		// this.fixtureContractItems);
		// this.tableModel.addRow(fixtureContractItems);
		// } else if (this.dataState == DataState.EDIT) {
		fixtureContractItems = this.fixtureContractAction.saveContractImg(
				new Request(CommonVars.getCurrUser()),
				this.fixtureContractItems);
		this.tableModel.updateRow(fixtureContractItems);
		// }
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
		if (this.cbbCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "产销国不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (this.cbbCountry.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "设备类型不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * This method initializes numberFormatter2
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter2() {
		if (numberFormatter2 == null) {
			numberFormatter2 = new NumberFormatter();
			DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat1.setMaximumFractionDigits(5);
			// decimalFormat1.setGroupingSize(0);
			numberFormatter2.setFormat(decimalFormat1);
		}
		return numberFormatter2;
	}

	/**
	 * This method initializes defaultFormatterFactory1
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory1() {
		if (defaultFormatterFactory1 == null) {
			defaultFormatterFactory1 = new DefaultFormatterFactory();
			defaultFormatterFactory1.setDefaultFormatter(getNumberFormatter2());
			defaultFormatterFactory1.setDisplayFormatter(getNumberFormatter2());
			defaultFormatterFactory1.setEditFormatter(getNumberFormatter2());
		}
		return defaultFormatterFactory1;
	}

	/**
	 * This method initializes cbbUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyMode() {
		if (cbbLevyMode == null) {
			cbbLevyMode = new JComboBox();
			cbbLevyMode.setBounds(383, 180, 146, 23);
		}
		return cbbLevyMode;
	}

	/**
	 * 设置料件总金额
	 */
	private double getTotalPrice() {
		try {
			double amount = Double
					.parseDouble(this.tfMaterielAmount.getValue() == null ? "0"
							: this.tfMaterielAmount.getValue().toString())
					* Double.parseDouble(tfUnitPrice.getValue() == null ? "0"
							: tfUnitPrice.getValue().toString());
			BigDecimal bd = new BigDecimal(amount);
			return bd.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception ex) {
		}
		return 0;
	}

	private String formatNum(Double num) {
		String bdStr = "";
		try {
			BigDecimal bd = new BigDecimal(num);
			bdStr = bd.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception e) {
		}
		return bdStr;

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!tableModel.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}

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

					showData();
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnNext;
	}

	private void setState() {
		boolean isChanging = false;
		if (this.fixtureContract != null) {
			if (DeclareState.CHANGING_EXE.equals(this.fixtureContract
					.getDeclareState())) {
				isChanging = true;
			}
		}
		this.btnEdit.setEnabled(this.dataState == DataState.BROWSE);
		this.btnCancel.setEnabled(this.dataState != DataState.BROWSE);
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnSave.setEnabled(this.dataState != DataState.BROWSE);
		tfName.setEditable(((!isChanging) || this.isEditable)
				&& (dataState != DataState.BROWSE));
		tfSpec.setEditable(((!isChanging) || this.isEditable)
				&& (dataState != DataState.BROWSE));
		// 详细型号规格
		jTextField1.setEditable(this.dataState != DataState.BROWSE);
		this.tfMaterielAmount.setEditable(this.dataState != DataState.BROWSE);
		this.tfUnitPrice.setEditable(this.dataState != DataState.BROWSE);
		this.tfTotalMoney.setEditable(this.dataState != DataState.BROWSE);
		this.tfMemo.setEditable(this.dataState != DataState.BROWSE);
		this.cbbCountry.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbKind.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbLevyMode.setEnabled(this.dataState != DataState.BROWSE);
		this.cbbUnit.setEnabled(this.dataState != DataState.BROWSE);
		this.tfProtocol.setEditable(this.dataState != DataState.BROWSE);
		this.cbbDeclareDate.setEnabled(this.dataState != DataState.BROWSE);
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
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(116, 242, 413, 23));
		}
		return jTextField1;
	}

	/**
	 * This method initializes cbbKind
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbKind() {
		if (cbbKind == null) {
			cbbKind = new JComboBox();
			cbbKind.setBounds(new Rectangle(116, 210, 146, 23));
		}
		return cbbKind;
	}

	/**
	 * This method initializes tfProtocol	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfProtocol() {
		if (tfProtocol == null) {
			tfProtocol = new JTextField();
			tfProtocol.setBounds(new Rectangle(116, 94, 146, 23));
		}
		return tfProtocol;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
