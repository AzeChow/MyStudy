/*
 * Created on 2004-7-22
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbill;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.OrderDate;
import com.bestway.common.erpbill.entity.OrderDateIndex;
import com.bestway.dzsc.innermerge.action.DzscInnerMergeAction;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeDataOrder;
import com.bestway.dzsc.innermerge.entity.DzscInnerMergeFileData;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Font;
import java.awt.Dimension;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgOrderCorrespondFile extends JDialogBase {

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JComboBox cbbScmCoc = null;

	private JComboBox cbbBillCode = null;

	private JComboBox cbbIfzc = null;

	private JComboBox cbbUnit = null;

	private int fileColumnCount = 0;

	private Hashtable<Integer, Integer> selectedValues = null; // @jve:decl-index=0:

	private JButton btnOK = null;

	private JButton btnCancel = null;

	private JButton btnRestoreInitValues = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbSalesDate = null;

	private JComboBox cbbPtNo = null;

	private JComboBox cbbAmount = null;

	private OrderCommonAction orderCommonAction = null;
	
	private OrderDateIndex order = null;

	private JLabel jLabel21 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel3 = null;

	private JComboBox cbbState = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbOrerId = null;

	/**
	 * @return Returns the selectedValues.
	 */
	public Hashtable getSelectedValues() {
		return selectedValues;
	}

	/**
	 * @param selectedValues
	 *            The selectedValues to set.
	 */
	public void setSelectedValues(Hashtable selectedValues) {
		this.selectedValues = selectedValues;
	}

	/**
	 * @return Returns the fileColumnCount.
	 */
	public int getFileColumnCount() {
		return fileColumnCount;
	}

	/**
	 * @param fileColumnCount
	 *            The fileColumnCount to set.
	 */
	public void setFileColumnCount(int fileColumnCount) {
		this.fileColumnCount = fileColumnCount;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgOrderCorrespondFile() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
		.getApplicationContext().getBean("orderCommonAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("设定导入资料与文件资料的列对应关系");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(400, 350);
	}

	public void setVisible(boolean b) {
		if (b) {
			fillAllComboBox();
			restoreInitValues();
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
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel2(), null);
			jContentPane.add(getBtnOK(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getBtnRestoreInitValues(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel21 = new JLabel();
			jLabel21.setBounds(new Rectangle(180, 70, 50, 25));
			jLabel21.setText("订单日期");
			jPanel = new JPanel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			jPanel.setLayout(null);
			jPanel.setBounds(29, 18, 331, 117);
			jPanel.setBorder(BorderFactory.createTitledBorder(null, "\u8ba2\u5355\u8868\u5934", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
					"订单表头",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("客户代码");
			jLabel.setBounds(15,30, 50, 25);
			jLabel1.setBounds(15, 70, 50, 25);
			jLabel1.setText("订单编号");
			jLabel2.setBounds(180, 30, 50, 25);
			jLabel2.setText("是否转厂");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCbbBillCode(), null);
			jPanel.add(getCbbIfzc(), null);
			jPanel.add(jLabel21, null);
			jPanel.add(getJComboBox(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(180, 90, 50, 25));
			jLabel4.setText("订单ID");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(180, 55, 50, 25));
			jLabel3.setText("订单状态");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(180, 20, 50, 25));
			jLabel5.setText("交货日期");
			jPanel2 = new JPanel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(30, 143, 331, 129);
			jPanel2.setBorder(BorderFactory.createTitledBorder(null, "\u8ba2\u5355\u660e\u7ec6", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.PLAIN, 12), new Color(51, 51, 51)));
			jLabel11.setBounds(15, 20, 50, 25);
			jLabel11.setText("工厂料号");
			jLabel12.setBounds(15, 55, 50, 25);
			jLabel12.setText("数量");
			jLabel13.setBounds(15, 90, 50, 25);
			jLabel13.setText("单位");
			jPanel2.add(jLabel11, null);
			jPanel2.add(jLabel12, null);
			jPanel2.add(jLabel13, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getCbbPtNo(), null);
			jPanel2.add(getCbbAmount(), null);
			jPanel2.add(getCbbUnit(), null);
			jPanel2.add(getCbbSalesDate(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getCbbState(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getCbbOrerId(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(65, 30, 85, 25);
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes cbbBillCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillCode() {
		if (cbbBillCode == null) {
			cbbBillCode = new JComboBox();
			cbbBillCode.setBounds(65, 70, 85, 25);
		}
		return cbbBillCode;
	}

	/**
	 * This method initializes cbbIfzc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIfzc() {
		if (cbbIfzc == null) {
			cbbIfzc = new JComboBox();
			cbbIfzc.setBounds(230, 30, 85, 25);
			cbbIfzc
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbIfzc.getSelectedIndex() > 0) {
//								cbbPtNo.setSelectedIndex(-1);
//								cbbAmount.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbIfzc;
	}

	/**
	 * This method initializes cbbBeforeLegalUnit
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbUnit() {
		if (cbbUnit == null) {
			cbbUnit = new JComboBox();
			cbbUnit.setBounds(new Rectangle(65, 90, 85, 25));
		}
		return cbbUnit;
	}

	private void fillComboBox(JComboBox comboBox, int selectedIndex) {
		comboBox.addItem(new ItemProperty(String.valueOf(0), "  "));
		for (int i = 0; i < fileColumnCount + 4; i++) {
			comboBox.addItem(new ItemProperty(String.valueOf(i + 1), "第"
					+ (i + 1) + "列"));
		}
		// comboBox.setRenderer(CustomBaseRender.getInstance()
		// .getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(comboBox);
	}

	private void fillAllComboBox() {
		fillComboBox(this.cbbScmCoc, OrderDate.order_scmCoc);
		fillComboBox(this.cbbBillCode,OrderDate.order_billCode);
		fillComboBox(this.cbbIfzc,OrderDate.order_ifzc);
		fillComboBox(this.jComboBox,OrderDate.order_orderDate);
		fillComboBox(this.cbbPtNo,OrderDate.order_ptNo);
		fillComboBox(this.cbbAmount,OrderDate.order_bgAmount);
		fillComboBox(this.cbbUnit, OrderDate.order_bgUnit);
		fillComboBox(this.cbbSalesDate,OrderDate.order_salesDate);
		fillComboBox(this.cbbState,OrderDate.order_state);
		fillComboBox(this.cbbOrerId,OrderDate.order_erpId);

	}

	private void getSelectedValue() {
		if (selectedValues == null) {
			selectedValues = new Hashtable<Integer, Integer>();
		}
		selectedValues.put(Integer.valueOf(OrderDate.order_scmCoc),
				getComboBoxSelectValue(this.cbbScmCoc));
		selectedValues.put(Integer
				.valueOf(OrderDate.order_billCode),
				getComboBoxSelectValue(this.cbbBillCode));
		selectedValues.put(Integer
				.valueOf(OrderDate.order_ifzc),
				getComboBoxSelectValue(this.cbbIfzc));
		selectedValues.put(Integer
				.valueOf(OrderDate.order_orderDate),
				getComboBoxSelectValue(this.jComboBox));		
		selectedValues.put(Integer
				.valueOf(OrderDate.order_ptNo),
				getComboBoxSelectValue(this.cbbPtNo));
		selectedValues.put(Integer
				.valueOf(OrderDate.order_bgAmount),
				getComboBoxSelectValue(this.cbbAmount));
		selectedValues.put(Integer.valueOf(OrderDate.order_bgUnit),
				getComboBoxSelectValue(this.cbbUnit));
		selectedValues.put(Integer
				.valueOf(OrderDate.order_salesDate),
				getComboBoxSelectValue(this.cbbSalesDate));
		selectedValues.put(Integer
				.valueOf(OrderDate.order_state),
				getComboBoxSelectValue(this.cbbState));
		selectedValues.put(Integer.valueOf(OrderDate.order_erpId),
				getComboBoxSelectValue(this.cbbOrerId));


	}

	private Integer getComboBoxSelectValue(JComboBox comboBox) {
		// return ((ItemProperty) comboBox.getSelectedItem())
		// .getCode()
		if (comboBox.getSelectedItem() == null) {
			return -1;
		}
		return Integer.valueOf(((ItemProperty) comboBox.getSelectedItem())
				.getCode()) - 1;
	}

	private void setComboBoxInitValue(JComboBox comboBox, int selectedIndex) {
		if (selectedIndex > comboBox.getItemCount() - 1) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedIndex(selectedIndex);
		}
	}

	private void restoreInitValues() {
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(OrderDate.order_scmCoc)) == null) {
			setComboBoxInitValue(this.cbbScmCoc,
					OrderDate.order_scmCoc);
		} else {
			this.cbbScmCoc.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(OrderDate.order_scmCoc)) + 1),
					this.cbbScmCoc));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(OrderDate.order_billCode)) == null) {
			setComboBoxInitValue(this.cbbBillCode,
					OrderDate.order_billCode);
		} else {
			this.cbbBillCode
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_billCode)) + 1),
									this.cbbBillCode));
		}

		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(OrderDate.order_ifzc)) == null) {
			setComboBoxInitValue(this.cbbIfzc,
					OrderDate.order_ifzc);
		} else {
			this.cbbIfzc
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_ifzc)) + 1),
									this.cbbIfzc));
		}
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(OrderDate.order_orderDate)) == null) {
			setComboBoxInitValue(this.jComboBox,
					OrderDate.order_orderDate);
		} else {
			this.jComboBox
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_orderDate)) + 1),
									this.jComboBox));
		}
		
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(OrderDate.order_ptNo)) == null) {
			setComboBoxInitValue(this.cbbPtNo,
					OrderDate.order_ptNo);

		} else {
			this.cbbPtNo
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_ptNo)) + 1),
									this.cbbPtNo));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(OrderDate.order_bgAmount)) == null) {
			 setComboBoxInitValue(this.cbbAmount,
					 OrderDate.order_bgAmount);
		} else {
			this.cbbAmount
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_bgAmount)) + 1),
									this.cbbAmount));
		}
		if (selectedValues == null
				|| selectedValues.get(Integer
						.valueOf(OrderDate.order_bgUnit)) == null) {
			setComboBoxInitValue(this.cbbUnit,
					OrderDate.order_bgUnit);
		} else {
			this.cbbUnit.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(selectedValues.get(Integer
							.valueOf(OrderDate.order_bgUnit)) + 1),
					this.cbbUnit));
		}
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(OrderDate.order_salesDate)) == null) {
			setComboBoxInitValue(this.cbbSalesDate,
					OrderDate.order_salesDate);
		} else {
			this.cbbSalesDate
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_salesDate)) + 1),
									this.cbbSalesDate));
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(OrderDate.order_state)) == null) {
			setComboBoxInitValue(this.cbbState,
					OrderDate.order_state);
		} else {
			this.cbbState
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_state)) + 1),
									this.cbbState));
		}
	}
		if (selectedValues == null
				|| selectedValues
						.get(Integer
								.valueOf(OrderDate.order_erpId)) == null) {
			setComboBoxInitValue(this.cbbOrerId,
					OrderDate.order_erpId);
		} else {
			this.cbbOrerId
					.setSelectedIndex(ItemProperty
							.getIndexByCode(
									String
											.valueOf(selectedValues
													.get(Integer
															.valueOf(OrderDate.order_erpId)) + 1),
									this.cbbOrerId));
		}
	
	
	}

	/**
	 * This method initializes btnOK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setBounds(53, 285, 68, 25);
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					order = orderCommonAction
							.findOrderDateIndex(new Request(CommonVars
									.getCurrUser(), true));
					fillData();
					orderCommonAction.saveOrderDateIndex(
							new Request(CommonVars.getCurrUser(), true), order);
					getSelectedValue();
					DgOrderCorrespondFile.this.dispose();
				}
			});
		}
		return btnOK;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(141, 285, 68, 25);
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
	 * This method initializes btnRestoreInitValues
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRestoreInitValues() {
		if (btnRestoreInitValues == null) {
			btnRestoreInitValues = new JButton();
			btnRestoreInitValues.setBounds(234, 285, 97, 25);
			btnRestoreInitValues.setText("恢复初始值");
			btnRestoreInitValues
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							restoreInitValues();
						}
					});
		}
		return btnRestoreInitValues;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSalesDate() {
		if (cbbSalesDate == null) {
			cbbSalesDate = new JComboBox();
			cbbSalesDate.setBounds(new Rectangle(230, 20, 85, 25));
		}
		return cbbSalesDate;
	}

	/**
	 * This method initializes cbbBeforeMaterielNameSpec1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPtNo() {
		if (cbbPtNo == null) {
			cbbPtNo = new JComboBox();
			cbbPtNo.setBounds(new Rectangle(65, 20, 85, 25));
			cbbPtNo
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbPtNo.getSelectedIndex() > 0) {
//								cbbIfzc.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbPtNo;
	}

	/**
	 * This method initializes cbbBeforeMaterielNameSpec2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbAmount() {
		if (cbbAmount == null) {
			cbbAmount = new JComboBox();
			cbbAmount.setBounds(new Rectangle(65, 55, 85, 25));
			cbbAmount
					.addItemListener(new java.awt.event.ItemListener() {
						public void itemStateChanged(java.awt.event.ItemEvent e) {
							if (cbbAmount.getSelectedIndex() > 0) {
//								cbbIfzc.setSelectedIndex(-1);
							}
						}
					});
		}
		return cbbAmount;
	}

	private void fillData() {

		if (this.cbbScmCoc.getSelectedItem() != null) {
			order.setScmCoc(Integer.valueOf(((ItemProperty) this.cbbScmCoc
					.getSelectedItem()).getCode()) - 1);
		} else {
			order.setScmCoc(-1);
		}
		if (this.cbbBillCode.getSelectedItem() != null) {
			order.setBillCode(Integer
					.valueOf(((ItemProperty) this.cbbBillCode
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBillCode(-1);
		}

		if (this.cbbIfzc.getSelectedItem() != null) {
			order.setIfzc(Integer
					.valueOf(((ItemProperty) this.cbbIfzc
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setIfzc(-1);
		}
		
		if (this.jComboBox.getSelectedItem() != null) {
			order.setOrderDate(Integer
					.valueOf(((ItemProperty) this.jComboBox
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setOrderDate(-1);
		}
		if (this.cbbPtNo.getSelectedItem() != null) {
			order.setPtNo(Integer
					.valueOf(((ItemProperty) this.cbbPtNo
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setPtNo(-1);
		}
		if (this.cbbAmount.getSelectedItem() != null) {
			order.setBgAmount(Integer
					.valueOf(((ItemProperty) this.cbbAmount
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBgAmount(-1);
		}
		if (this.cbbUnit.getSelectedItem() != null) {
			order.setBgUnit(Integer
					.valueOf(((ItemProperty) this.cbbUnit
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setBgUnit(-1);
		}
		if (this.cbbSalesDate.getSelectedItem() != null) {
			order.setSalesDate(Integer
					.valueOf(((ItemProperty) this.cbbSalesDate
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setSalesDate(-1);
		}
		if (this.cbbState.getSelectedItem() != null) {
			order.setState(Integer
					.valueOf(((ItemProperty) this.cbbState
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setState(-1);
		}
		if (this.cbbOrerId.getSelectedItem() != null) {
			order.setOrderERPId(Integer
					.valueOf(((ItemProperty) this.cbbOrerId
							.getSelectedItem()).getCode()) - 1);
		} else {
			order.setOrderERPId(-1);
		}
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(230, 70, 85, 25));
		}
		return jComboBox;
	}

	/**
	 * This method initializes cbbState	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbState() {
		if (cbbState == null) {
			cbbState = new JComboBox();
			cbbState.setBounds(new Rectangle(230, 55, 85, 25));
		}
		return cbbState;
	}

	/**
	 * This method initializes cbbOrerId	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbOrerId() {
		if (cbbOrerId == null) {
			cbbOrerId = new JComboBox();
			cbbOrerId.setBounds(new Rectangle(230, 90, 85, 25));
		}
		return cbbOrerId;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
