package com.bestway.bcs.client.bcsinnermerge;


import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBcsCustomsBom extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JFormattedTextField tfWare = null;

	private JFormattedTextField tfUnitWare = null;

	private JFormattedTextField tfUnitDosage = null;

	private JButton btnOk = null;

	private JButton btnExit = null;

	private BcsInnerMergeAction bcsInnerMergeAction = null;

	private JTableListModel tableModel = null;

	private BcsCustomsBomDetail bom = null;

	private NumberFormatter numberFormatter = null;

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	private JLabel jLabel = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel6 = null;

	private JTextField tfExgSeqNum = null;

	private JTextField tfExgUnit = null;

	private JTextField tfExgName = null;

	private JTextField tfImgSeqNum = null;

	private JTextField tfImgUnit = null;

	private JTextField tfImgName = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;

	private int dataState = -1;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	public NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
			DecimalFormat decimalFormat = new DecimalFormat(); // @jve:decl-index=0:
			decimalFormat.setMaximumFractionDigits(999);
			decimalFormat.setGroupingSize(0);
			numberFormatter.setFormat(decimalFormat);
		}
		return numberFormatter;
	}

	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This is the default constructor
	 */
	public DgBcsCustomsBom() {
		super();
		initialize();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
	}

	private void setState() {

		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnOk.setEnabled(this.dataState != DataState.BROWSE);
		this.tfUnitWare.setEditable(this.dataState != DataState.BROWSE);
		this.tfWare.setEditable(this.dataState != DataState.BROWSE);

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public Double objToDouble(Object value) { // 转换strToDouble 填充数据
		return value == null ? 0.0 : Double.parseDouble(value.toString());
	}

	public String doubleToStr(Double value) { // 转换doubleToStr 取数据
		try {
			if (value == null || value.doubleValue() == 0) {
				return null;
			}
			getNumberFormatter().setValueClass(Double.class);
			return getNumberFormatter().valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setVisible(boolean b) {
		if (b) {
			if (tableModel.getCurrentRow() != null) {
				bom = (BcsCustomsBomDetail) tableModel.getCurrentRow();
				showData();
				setState();
			}
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(441, 310);
		this.setContentPane(getJContentPane());
		this.setTitle("修改料件的单耗情况");
	}

	private void showData() {
		bom = (BcsCustomsBomDetail) tableModel.getCurrentRow();
		this.tfExgSeqNum
				.setText(bom.getBcsCustomsBomExg().getTenSeqNum() == null ? ""
						: bom.getBcsCustomsBomExg().getTenSeqNum().toString());
		this.tfExgName.setText(bom.getBcsCustomsBomExg().getName());
		this.tfExgUnit
				.setText(bom.getBcsCustomsBomExg().getUnit() == null ? ""
						: bom.getBcsCustomsBomExg().getUnit().getName());
		this.tfImgSeqNum.setText(bom.getTenSeqNum() == null ? "" : bom
				.getTenSeqNum().toString());
		this.tfImgUnit.setText(bom.getUnit() == null ? "" : bom.getUnit()
				.getName());
		this.tfImgName.setText(bom.getName());
		tfUnitWare.setValue(bom.getUnitWare());
		tfUnitDosage.setValue(bom.getUnitDosage());
		tfWare.setValue(bom.getWare());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(212, 145, 48, 20));
			jLabel8.setForeground(Color.blue);
			jLabel8.setText("损耗率");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(406, 144, 12, 21));
			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel7.setForeground(Color.blue);
			jLabel7.setText("%");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(213, 31, 51, 22));
			jLabel6.setText("成品单位");
			jLabel6.setForeground(java.awt.Color.black);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(29, 31, 52, 24));
			jLabel3.setText("成品序号");
			jLabel3.setForeground(java.awt.Color.black);
			jLabel = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel9 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(29, 60, 52, 20);
			jLabel1.setText("成品名称");
			jLabel1.setForeground(java.awt.Color.black);
			jLabel2.setBounds(29, 117, 51, 21);
			jLabel2.setText("料件名称");
			jLabel2.setForeground(java.awt.Color.black);
			jLabel4.setBounds(29, 147, 31, 20);
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("单耗");
			jLabel9.setBounds(29, 172, 49, 22);
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel9.setText("单项用量");
			jLabel11.setBounds(211, 85, 52, 23);
			jLabel11.setText("料件单位");
			jLabel11.setForeground(java.awt.Color.black);
			jLabel12.setBounds(212, 145, 47, 21);
			jLabel12.setForeground(java.awt.Color.blue);
			jLabel12.setText("损耗率");
			jLabel.setBounds(29, 83, 52, 23);
			jLabel.setText("料件序号");
			jLabel.setForeground(java.awt.Color.black);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getTfWare(), null);
			jContentPane.add(getTfUnitWare(), null);
			jContentPane.add(getTfUnitDosage(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getTfExgSeqNum(), null);
			jContentPane.add(getTfExgUnit(), null);
			jContentPane.add(getTfExgName(), null);
			jContentPane.add(getTfImgSeqNum(), null);
			jContentPane.add(getTfImgUnit(), null);
			jContentPane.add(getTfImgName(), null);
			jContentPane.add(getBtnPrevious(), null);
			jContentPane.add(getBtnNext(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(jLabel8, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWare() {
		if (tfWare == null) {
			tfWare = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWare.setFormatterFactory(getDefaultFormatterFactory());
			tfWare.setBounds(264, 144, 143, 22);
			tfWare.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {

				}

				public void insertUpdate(DocumentEvent e) {
					try {
						tfWare.commitEdit();
					} catch (Exception ex) {
					}

					setTfUnitDosageAndTfAmountValue();
				}

				public void removeUpdate(DocumentEvent e) {
					try {
						tfWare.commitEdit();
					} catch (Exception ex) {
					}

					setTfUnitDosageAndTfAmountValue();
				}
			});
		}
		return tfWare;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitWare() {
		if (tfUnitWare == null) {
			tfUnitWare = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWare.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitWare.setBounds(83, 144, 118, 22);
			tfUnitWare.getDocument().addDocumentListener(
					new DocumentListener() {
						public void changedUpdate(DocumentEvent e) {
							// try {
							// tfUnitWare.commitEdit();
							// } catch (Exception ex) {
							// }
							// setTfUnitDosageAndTfAmountValue();
						}

						public void insertUpdate(DocumentEvent e) {
							try {
								tfUnitWare.commitEdit();
							} catch (Exception ex) {
							}
							setTfUnitDosageAndTfAmountValue();
						}

						public void removeUpdate(DocumentEvent e) {
							try {
								tfUnitWare.commitEdit();
							} catch (Exception ex) {
							}
							setTfUnitDosageAndTfAmountValue();
						}
					});
		}
		return tfUnitWare;
	}

	private void setTfUnitDosageAndTfAmountValue() {
		double unitware = new Double(this.tfUnitWare.getValue() == null ? "0.0"
				: this.tfUnitWare.getValue().toString());
		double ware = new Double(tfWare.getValue() == null ? "0.0" : tfWare
				.getValue().toString());
		if ((ware < 100 && ware >= 0)) {
			try {
				Double dou = unitware / (1 - ware / 100.0);
				this.tfUnitDosage.setValue(new Double(CommonVars
						.formatDoubleToString(Double
								.parseDouble(dou.toString()), 999, 6)));
			} catch (RuntimeException e) {

			}
		}
	}

	// 得到损耗
	private Double getsunhao() {
		double zong = 0;
		double danhao = 0;
		if (this.tfUnitDosage.getText() != null
				&& !this.tfUnitDosage.getText().equals("")) {
			zong = Double.parseDouble(this.tfUnitDosage.getText().toString());
		} else {
			return Double.valueOf(0);
		}
		if (this.tfUnitWare.getText() != null
				&& !this.tfUnitWare.getText().equals("")) {
			danhao = Double.parseDouble(this.tfUnitWare.getText().toString());
		}
		BigDecimal bd = new BigDecimal(1 - (danhao / zong));
		String totalPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalPrice);
	}

	// 得到单位用量
	private Double getTotalPrice() {
		double amount = 0;
		double unitPrice = 0;
		if (this.tfUnitWare.getText() != null
				&& !this.tfUnitWare.getText().equals("")) {
			amount = Double.parseDouble(this.tfUnitWare.getText().toString());
		}
		if (this.tfWare.getText() != null && !this.tfWare.getText().equals("")) {
			unitPrice = Double.parseDouble(this.tfWare.getText().toString());
		}
		if (unitPrice == 1) {
			return Double.valueOf(0);
		}
		BigDecimal bd = new BigDecimal(amount / (1 - unitPrice));
		String totalPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(totalPrice);
	}

	/**
	 * This method initializes jFormattedTextField3
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitDosage() {
		if (tfUnitDosage == null) {
			tfUnitDosage = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitDosage.setEditable(false);
			tfUnitDosage.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitDosage.setBounds(83, 172, 118, 22);
		}
		return tfUnitDosage;
	}

	// 得到单耗
	private Double getUnitPrice() {
		double sunhao = 0;
		double totalPrice = 0;
		if (this.tfWare.getText() != null && !this.tfWare.getText().equals("")) {
			sunhao = Double.parseDouble(this.tfWare.getText().toString());
		}
		if (this.tfUnitDosage.getText() != null
				&& !this.tfUnitDosage.getText().equals("")) {
			totalPrice = Double.parseDouble(this.tfUnitDosage.getText()
					.toString());
		}
		if (sunhao == 1) {
			return Double.valueOf(0);
		}
		BigDecimal bd = new BigDecimal(totalPrice * (1 - sunhao));
		String unitPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		return Double.valueOf(unitPrice);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(28, 227, 73, 25);
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfWare.getValue() != null) {
						if (Double.parseDouble(tfWare.getValue().toString()) >= 100) {
							JOptionPane.showMessageDialog(
									DgBcsCustomsBom.this, "损耗不能大于等于100", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (Double.parseDouble(tfWare.getValue().toString()) < 0) {
							JOptionPane.showMessageDialog(
									DgBcsCustomsBom.this, "损耗不能小于0", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					fillData();
					bom = bcsInnerMergeAction.saveBcsCustomsBomDetail(
							new Request(CommonVars.getCurrUser()), bom);
					tableModel.updateRow(bom);
					dataState = DataState.BROWSE;
					setState();
					// DgDzscEmsPorBomBill.this.dispose();
				}
			});
		}
		return btnOk;
	}

	private void fillData() {
		bom = (BcsCustomsBomDetail) tableModel.getCurrentRow();
		bom.setUnitWare(objToDouble(tfUnitWare.getValue()));
		bom.setUnitDosage(objToDouble(tfUnitDosage.getValue()));
		bom.setWare(objToDouble(tfWare.getValue()));
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(332, 227, 73, 25);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBcsCustomsBom.this.dispose();
				}
			});
		}
		return btnExit;
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

	private void calUnitUsed() {
		double unitWaste = this.tfUnitWare.getValue() == null ? 0 : Double
				.parseDouble(this.tfUnitWare.getValue().toString());
		double waste = this.tfWare.getValue() == null ? 0 : Double
				.parseDouble(this.tfWare.getValue().toString());
		double unitUsed = unitWaste / (1 - waste);
		this.tfUnitDosage.setValue(unitUsed);
	}

	class DocumentListenerAdapter implements DocumentListener {

		public void insertUpdate(DocumentEvent e) {
			calUnitUsed();
		}

		public void removeUpdate(DocumentEvent e) {
			calUnitUsed();
		}

		public void changedUpdate(DocumentEvent e) {
			calUnitUsed();
		}
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExgSeqNum() {
		if (tfExgSeqNum == null) {
			tfExgSeqNum = new JTextField();
			tfExgSeqNum.setBounds(new java.awt.Rectangle(83, 32, 119, 22));
			tfExgSeqNum.setEditable(false);
		}
		return tfExgSeqNum;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExgUnit() {
		if (tfExgUnit == null) {
			tfExgUnit = new JTextField();
			tfExgUnit.setBounds(new java.awt.Rectangle(264, 31, 143, 25));
			tfExgUnit.setEditable(false);
		}
		return tfExgUnit;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExgName() {
		if (tfExgName == null) {
			tfExgName = new JTextField();
			tfExgName.setBounds(new java.awt.Rectangle(84, 58, 323, 23));
			tfExgName.setEditable(false);
		}
		return tfExgName;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImgSeqNum() {
		if (tfImgSeqNum == null) {
			tfImgSeqNum = new JTextField();
			tfImgSeqNum.setBounds(new java.awt.Rectangle(83, 85, 119, 25));
			tfImgSeqNum.setEditable(false);
		}
		return tfImgSeqNum;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImgUnit() {
		if (tfImgUnit == null) {
			tfImgUnit = new JTextField();
			tfImgUnit.setBounds(new java.awt.Rectangle(264, 86, 143, 26));
			tfImgUnit.setEditable(false);
		}
		return tfImgUnit;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImgName() {
		if (tfImgName == null) {
			tfImgName = new JTextField();
			tfImgName.setBounds(new java.awt.Rectangle(83, 116, 324, 25));
			tfImgName.setEditable(false);
		}
		return tfImgName;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(new java.awt.Rectangle(131, 227, 73, 25));
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfWare.getValue() != null) {
						if (Double.parseDouble(tfWare.getValue().toString()) >= 100) {
							JOptionPane.showMessageDialog(
									DgBcsCustomsBom.this, "损耗不能大于等于1", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (Double.parseDouble(tfWare.getValue().toString()) < 0) {
							JOptionPane.showMessageDialog(
									DgBcsCustomsBom.this, "损耗不能小于0", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					fillData();
					bom = bcsInnerMergeAction.saveBcsCustomsBomDetail(
							new Request(CommonVars.getCurrUser()), bom);
					tableModel.updateRow(bom);
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(new java.awt.Rectangle(228, 227, 73, 25));
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tfWare.getValue() != null) {
						if (Double.parseDouble(tfWare.getValue().toString()) >= 100) {
							JOptionPane.showMessageDialog(
									DgBcsCustomsBom.this, "损耗不能大于等于100", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (Double.parseDouble(tfWare.getValue().toString()) < 0) {
							JOptionPane.showMessageDialog(
									DgBcsCustomsBom.this, "损耗不能小于0", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					fillData();
					bom = bcsInnerMergeAction.saveBcsCustomsBomDetail(
							new Request(CommonVars.getCurrUser()), bom);
					tableModel.updateRow(bom);
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
} // @jve:decl-index=0:visual-constraint="-4,13"
