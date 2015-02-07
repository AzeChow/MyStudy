/*
 * Created on 2005-3-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.bestway.bcus.client.common.AutoCalcListener;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.SwingConstants;
import java.awt.Dimension;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscEmsPorBomBill extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JFormattedTextField tfWare = null;

	private JFormattedTextField tfUnitWare = null;

	private JFormattedTextField tfUnitDosage = null;

	private JButton btnOk = null;

	private JButton btnExit = null;

	private DzscAction dzscAction = null;

	private JTableListModel tableModel = null;

	private DzscEmsBomBill bom = null;

	private JFormattedTextField tfAmount = null;

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

	private DzscEmsExgBill dzscEmsExgBill = null;  //  @jve:decl-index=0:

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel51 = null;

	private JFormattedTextField tfnonMnlRatio = null;

	private JLabel jLabel1211 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	public DzscEmsExgBill getDzscEmsExgBill() {
		return dzscEmsExgBill;
	}

	public void setDzscEmsExgBill(DzscEmsExgBill dzscEmsExgBill) {
		this.dzscEmsExgBill = dzscEmsExgBill;
	}

	/**
	 * This is the default constructor
	 */
	public DgDzscEmsPorBomBill() {
		super();
		initialize();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initUIComponents();
	}

	private void setState() {
		this.btnPrevious.setEnabled(tableModel.hasPreviousRow());
		this.btnNext.setEnabled(tableModel.hasNextRow());
		this.btnOk.setEnabled(this.dataState != DataState.BROWSE);
		this.tfUnitWare.setEditable(this.dataState != DataState.BROWSE);
		this.tfWare.setEditable(this.dataState != DataState.BROWSE);
		this.tfnonMnlRatio.setEditable(this.dataState != DataState.BROWSE);
	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 初始化国
		// this.cbbCountry.setModel(CustomBaseModel.getInstance()
		// .getCountryModel());
		// this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCountry);
		// this.cbbCountry.setSelectedItem(null);
		// // 初始化物料类型
		// cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL,
		// "主料"));
		// cbbMaterielType.addItem(new ItemProperty(
		// MaterielType.ASSISTANT_MATERIAL, "辅料"));
		// cbbMaterielType.addItem(new ItemProperty(MaterielType.MACHINE,
		// "设备"));
		// cbbMaterielType.addItem(new ItemProperty(MaterielType.WASTE_MATERIAL,
		// "消耗料"));
		// this.cbbMaterielType.setSelectedItem(null);
		// // 单位
		// this.cbbUnit.setModel(CustomBaseModel.getInstance().getUnitModel());
		// this.cbbUnit.setRenderer(CustomBaseRender.getInstance().getRender());
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUnit);
		// this.cbbUnit.setSelectedItem(null);
		// 这里是控制焦点的顺序，以方便键盘的输！
		List components = new ArrayList();
		components.add(this.tfUnitWare);
		components.add(null);
		components.add(this.tfWare);
		components.add(this.btnOk);
		components.add(this.btnNext);
		this.setComponentFocusList(components);

		// 单耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitWare, 9);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfUnitWare,
				new AutoCalcListener() {
					public void run() {
						// setUnitDosage();
						// setMaterielAmount();
						// setTotalPrice();
						setTfUnitDosageAndTfAmountValue();
					}
				});
		// 损耗
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfWare, 5);
		CustomFormattedTextFieldUtils.addAutoCalcListener(tfWare,
				new AutoCalcListener() {
					public void run() {
						// setUnitDosage();
						// setMaterielAmount();
						// setTotalPrice();
						setTfUnitDosageAndTfAmountValue();
					}
				});
		// 单项用量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitDosage, 5);
		// 料件数量
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfAmount, 5);
		/**
		 * 非保税料件比例
		 */
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfnonMnlRatio, 5);
		// 单量
//		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfUnitWeight, 5);
		// // 单项用量
		// CustomFormattedTextFieldUtils.setFormatterFactory(this.tfTotalMoney,
		// 5);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	public double getFormattedTextFieldValue(JFormattedTextField tf) { // 转换strToDouble
		// 填充数据
		// return value == null ? 0.0 : Double.parseDouble(value.toString());
		return CustomFormattedTextFieldUtils.getFormattedTextFieldValue(tf);
	}

	public void setVisible(boolean b) {
		if (b) {
			if (tableModel.getCurrentRow() != null) {
				bom = (DzscEmsBomBill) tableModel.getCurrentRow();
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
		this.setSize(441, 323);
		this.setContentPane(getJContentPane());
		this.setTitle("修改料件的单耗情况");
	}

	private void showData() {
		bom = (DzscEmsBomBill) tableModel.getCurrentRow();
		this.tfExgSeqNum
				.setText(bom.getDzscEmsExgBill().getSeqNum() == null ? "" : bom
						.getDzscEmsExgBill().getSeqNum().toString());
		this.tfExgName.setText(bom.getDzscEmsExgBill().getName());
		this.tfExgUnit.setText(bom.getDzscEmsExgBill().getUnit() == null ? ""
				: bom.getDzscEmsExgBill().getUnit().getName());
		this.tfImgSeqNum.setText(bom.getImgSeqNum() == null ? "" : bom
				.getImgSeqNum().toString());
		this.tfImgUnit.setText(bom.getUnit() == null ? "" : bom.getUnit()
				.getName());
		this.tfImgName.setText(bom.getName());
		// 非保税料件比例
		if (this.bom.getNonMnlRatio() != null) {
			this.tfnonMnlRatio.setValue(this.bom.getNonMnlRatio());
		} else {
			this.tfnonMnlRatio.setValue(new Double(0));
		}
//		tfUnitWeight.setValue(bom.getUnitWeight());
		tfUnitWare.setValue(bom.getUnitWare());
		tfUnitDosage.setValue(bom.getUnitDosage());
		tfAmount.setValue(bom.getAmount());
		tfWare.setValue(bom.getWare());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel16 = new JLabel();
			jLabel16.setBounds(new Rectangle(12, 28, 411, 18));
			jLabel16.setText("\u82e5\u5355\u8017/\u51c0\u8017\u680f\u7533\u62a5\u5185\u5bb9\u4e3a\u5355\u8017\uff0c\u5219\u4e0d\u5f97\u91cd\u590d\u7533\u62a5\u635f\u8017\u7387\u6570\u636e\uff0c\u635f\u8017\u7387\u680f\u5e94\u4e3a\u7a7a");
			jLabel16.setForeground(Color.red);
			jLabel15 = new JLabel();
			jLabel15.setBounds(new Rectangle(12, 8, 303, 18));
			jLabel15.setText("\u82e5\u5355\u8017/\u51c0\u8017\u680f\u7533\u62a5\u5185\u5bb9\u4e3a\u51c0\u8017\uff0c\u5219\u9700\u7533\u62a5\u76f8\u5e94\u635f\u8017\u7387\u6570\u636e");
			jLabel15.setForeground(Color.red);
			jLabel1211 = new JLabel();
			jLabel1211.setBounds(new Rectangle(196, 223, 11, 18));
			jLabel1211.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel1211.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel1211.setText("%");
			jLabel1211.setForeground(Color.blue);
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(28, 223, 84, 18));
			jLabel51.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel51.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel51.setText("\u975e\u4fdd\u7a0e\u6599\u4ef6\u6bd4\u4f8b");
			jLabel51.setForeground(Color.blue);
			jLabel8 = new JLabel();
			jLabel8.setBounds(new Rectangle(211, 168, 48, 20));
			jLabel8.setForeground(Color.blue);
			jLabel8.setText("损耗率");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(405, 167, 12, 21));
			jLabel7.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel7.setForeground(Color.blue);
			jLabel7.setText("%");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(212, 54, 51, 22));
			jLabel6.setText("成品单位");
			jLabel6.setForeground(java.awt.Color.black);
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(28, 54, 52, 24));
			jLabel3.setText("成品序号");
			jLabel3.setForeground(java.awt.Color.black);
			jLabel = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(28, 83, 52, 20);
			jLabel1.setText("成品名称");
			jLabel1.setForeground(java.awt.Color.black);
			jLabel2.setBounds(28, 140, 51, 21);
			jLabel2.setText("料件名称");
			jLabel2.setForeground(java.awt.Color.black);
			jLabel4.setBounds(28, 170, 56, 20);
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("单耗/净耗");
			jLabel9.setBounds(28, 195, 49, 22);
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel9.setText("单项用量");
			jLabel10.setBounds(211, 194, 49, 24);
			jLabel10.setText("料件数量");
			jLabel11.setBounds(210, 108, 52, 23);
			jLabel11.setText("料件单位");
			jLabel11.setForeground(java.awt.Color.black);
			jLabel.setBounds(28, 106, 52, 23);
			jLabel.setText("料件序号");
			jLabel.setForeground(java.awt.Color.black);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel9, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(getTfWare(), null);
			jContentPane.add(getTfUnitWare(), null);
			jContentPane.add(getTfUnitDosage(), null);
			jContentPane.add(getBtnOk(), null);
			jContentPane.add(getBtnExit(), null);
			jContentPane.add(getTfAmount(), null);
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
			jContentPane.add(jLabel51, null);
			jContentPane.add(getTfnonMnlRatio(), null);
			jContentPane.add(jLabel1211, null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel16, null);
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
			tfWare.setBounds(263, 167, 143, 22);
			// tfWare.getDocument().addDocumentListener(new DocumentListener() {
			// public void changedUpdate(DocumentEvent e) {
			//
			// }
			//
			// public void insertUpdate(DocumentEvent e) {
			// try {
			// tfWare.commitEdit();
			// } catch (Exception ex) {
			// }
			//
			// setTfUnitDosageAndTfAmountValue();
			// }
			//
			// public void removeUpdate(DocumentEvent e) {
			// try {
			// tfWare.commitEdit();
			// } catch (Exception ex) {
			// }
			//
			// setTfUnitDosageAndTfAmountValue();
			// }
			// });
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
			tfUnitWare.setBounds(82, 167, 118, 22);
			// tfUnitWare.getDocument().addDocumentListener(
			// new DocumentListener() {
			// public void changedUpdate(DocumentEvent e) {
			// // try {
			// // tfUnitWare.commitEdit();
			// // } catch (Exception ex) {
			// // }
			// // setTfUnitDosageAndTfAmountValue();
			// }
			//
			// public void insertUpdate(DocumentEvent e) {
			// try {
			// tfUnitWare.commitEdit();
			// } catch (Exception ex) {
			// }
			// setTfUnitDosageAndTfAmountValue();
			// }
			//
			// public void removeUpdate(DocumentEvent e) {
			// try {
			// tfUnitWare.commitEdit();
			// } catch (Exception ex) {
			// }
			// setTfUnitDosageAndTfAmountValue();
			// }
			// });
		}
		return tfUnitWare;
	}

	private void setTfUnitDosageAndTfAmountValue() {
		double unitware = getFormattedTextFieldValue(this.tfUnitWare);
		double ware = getFormattedTextFieldValue(tfWare);
		if ((ware < 100 && ware >= 0)) {
			try {
				Double dou = unitware / (1 - ware / 100.0);
				this.tfUnitDosage.setValue(new Double(CommonVars
						.formatDoubleToString(Double
								.parseDouble(dou.toString()), 999, 6)));
			} catch (RuntimeException e) {

			}

			double amount = Double
					.parseDouble(this.tfUnitDosage.getValue() == null ? "0.0"
							: this.tfUnitDosage.getValue().toString())
					* Double
							.parseDouble(this.getDzscEmsExgBill().getAmount() == null ? "0.0"
									: this.getDzscEmsExgBill().getAmount()
											.toString());

			this.tfAmount.setValue(new Double(CommonVars.formatDoubleToString(
					amount, 999, 6)));
		}
	}

	// // 得到损耗
	// private Double getsunhao() {
	// double zong = 0;
	// double danhao = 0;
	// if (this.tfUnitDosage.getText() != null
	// && !this.tfUnitDosage.getText().equals("")) {
	// zong = Double.parseDouble(this.tfUnitDosage.getText().toString());
	// } else {
	// return Double.valueOf(0);
	// }
	// if (this.tfUnitWare.getText() != null
	// && !this.tfUnitWare.getText().equals("")) {
	// danhao = Double.parseDouble(this.tfUnitWare.getText().toString());
	// }
	// BigDecimal bd = new BigDecimal(1 - (danhao / zong));
	// String totalPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	// return Double.valueOf(totalPrice);
	// }

	// // 得到单位用量
	// private Double getTotalPrice() {
	// double amount = 0;
	// double unitPrice = 0;
	// if (this.tfUnitWare.getText() != null
	// && !this.tfUnitWare.getText().equals("")) {
	// amount = Double.parseDouble(this.tfUnitWare.getText().toString());
	// }
	// if (this.tfWare.getText() != null && !this.tfWare.getText().equals("")) {
	// unitPrice = Double.parseDouble(this.tfWare.getText().toString());
	// }
	// if (unitPrice == 1) {
	// return Double.valueOf(0);
	// }
	// BigDecimal bd = new BigDecimal(amount / (1 - unitPrice));
	// String totalPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	// return Double.valueOf(totalPrice);
	// }

	// // 得到料件数量
	// private Double getshuliang() {
	// double chenpinamount = 0;
	// double zong = 0;
	// if (this.bom.getAmount() != null) {
	// chenpinamount = Double.parseDouble(bom.getAmount().toString());
	// }
	// if (this.tfUnitDosage.getText() != null
	// && !this.tfUnitDosage.getText().equals("")) {
	// zong = Double.parseDouble(this.tfUnitDosage.getText().toString());
	// }
	// BigDecimal bd = new BigDecimal(chenpinamount * zong);
	// String totalPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	// return Double.valueOf(totalPrice);
	// }

	/**
	 * This method initializes jFormattedTextField3
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitDosage() {
		if (tfUnitDosage == null) {
			tfUnitDosage = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitDosage.setEditable(false);
			tfUnitDosage.setBounds(82, 195, 118, 22);
		}
		return tfUnitDosage;
	}

	//
	// // 得到单耗
	// private Double getUnitPrice() {
	// double sunhao = 0;
	// double totalPrice = 0;
	// if (this.tfWare.getText() != null && !this.tfWare.getText().equals("")) {
	// sunhao = Double.parseDouble(this.tfWare.getText().toString());
	// }
	// if (this.tfUnitDosage.getText() != null
	// && !this.tfUnitDosage.getText().equals("")) {
	// totalPrice = Double.parseDouble(this.tfUnitDosage.getText()
	// .toString());
	// }
	// if (sunhao == 1) {
	// return Double.valueOf(0);
	// }
	// BigDecimal bd = new BigDecimal(totalPrice * (1 - sunhao));
	// String unitPrice = bd.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	// return Double.valueOf(unitPrice);
	// }

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setBounds(28, 254, 73, 25);
			btnOk.setText("保存");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfWare.getValue() != null) {
						if (Double.parseDouble(tfWare.getValue().toString()) >= 100) {
							JOptionPane.showMessageDialog(
									DgDzscEmsPorBomBill.this, "损耗不能大于等于100",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (Double.parseDouble(tfWare.getValue().toString()) < 0) {
							JOptionPane.showMessageDialog(
									DgDzscEmsPorBomBill.this, "损耗不能小于0", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					if (((Number) tfnonMnlRatio.getValue()).doubleValue() > 99
							|| ((Number) tfnonMnlRatio.getValue()).doubleValue() < 0) {
						JOptionPane.showMessageDialog(DgDzscEmsPorBomBill.this, "非保税料件比例范围大于等于0且小于等于99", "警告！",
								JOptionPane.INFORMATION_MESSAGE);
						return ;
					}
					fillData();
					bom = dzscAction.saveDzscEmsBomBill(new Request(CommonVars
							.getCurrUser()), bom);
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
		bom = (DzscEmsBomBill) tableModel.getCurrentRow();
//		bom.setUnitWeight(getFormattedTextFieldValue(tfUnitWeight));
		bom.setUnitWare(getFormattedTextFieldValue(tfUnitWare));
		bom.setUnitDosage(getFormattedTextFieldValue(tfUnitDosage));
		bom.setAmount(getFormattedTextFieldValue(tfAmount));
		bom.setWare(getFormattedTextFieldValue(tfWare));
		bom.setMoney(CommonVars.getDoubleByDigit(CommonVars
				.getDoubleExceptNull(bom.getAmount())
				* CommonVars.getDoubleExceptNull(bom.getPrice()), 5));
		// 非保税料件比例%
		this.bom
				.setNonMnlRatio(getFormattedTextFieldValue(this.tfnonMnlRatio));	
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(332, 254, 73, 25);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscEmsPorBomBill.this.dispose();
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

	/**
	 * This method initializes jFormattedTextField4
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfAmount.setEditable(false);
			tfAmount.setBounds(263, 194, 142, 22);
		}
		return tfAmount;
	}

	// private void calUnitUsed() {
	// double unitWaste = this.tfUnitWare.getValue() == null ? 0 : Double
	// .parseDouble(this.tfUnitWare.getValue().toString());
	// double waste = this.tfWare.getValue() == null ? 0 : Double
	// .parseDouble(this.tfWare.getValue().toString());
	// double unitUsed = unitWaste / (1 - waste);
	// this.tfUnitDosage.setValue(unitUsed);
	// }

	// class DocumentListenerAdapter implements DocumentListener {
	//
	// public void insertUpdate(DocumentEvent e) {
	// calUnitUsed();
	// }
	//
	// public void removeUpdate(DocumentEvent e) {
	// calUnitUsed();
	// }
	//
	// public void changedUpdate(DocumentEvent e) {
	// calUnitUsed();
	// }
	// }

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExgSeqNum() {
		if (tfExgSeqNum == null) {
			tfExgSeqNum = new JTextField();
			tfExgSeqNum.setBounds(new Rectangle(82, 55, 119, 22));
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
			tfExgUnit.setBounds(new Rectangle(263, 54, 143, 25));
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
			tfExgName.setBounds(new Rectangle(83, 81, 323, 23));
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
			tfImgSeqNum.setBounds(new Rectangle(82, 108, 119, 25));
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
			tfImgUnit.setBounds(new Rectangle(263, 109, 143, 26));
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
			tfImgName.setBounds(new Rectangle(82, 139, 324, 25));
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
			btnPrevious.setBounds(new Rectangle(131, 254, 73, 25));
			btnPrevious.setText("上笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tfWare.getValue() != null) {
						if (Double.parseDouble(tfWare.getValue().toString()) >= 100) {
							JOptionPane.showMessageDialog(
									DgDzscEmsPorBomBill.this, "损耗不能大于等于1",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (Double.parseDouble(tfWare.getValue().toString()) < 0) {
							JOptionPane.showMessageDialog(
									DgDzscEmsPorBomBill.this, "损耗不能小于0", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					fillData();
					bom = dzscAction.saveDzscEmsBomBill(new Request(CommonVars
							.getCurrUser()), bom);
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
			btnNext.setBounds(new Rectangle(228, 254, 73, 25));
			btnNext.setText("下笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tfWare.getValue() != null) {
						if (Double.parseDouble(tfWare.getValue().toString()) >= 100) {
							JOptionPane.showMessageDialog(
									DgDzscEmsPorBomBill.this, "损耗不能大于等于100",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (Double.parseDouble(tfWare.getValue().toString()) < 0) {
							JOptionPane.showMessageDialog(
									DgDzscEmsPorBomBill.this, "损耗不能小于0", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}

					fillData();
					bom = dzscAction.saveDzscEmsBomBill(new Request(CommonVars
							.getCurrUser()), bom);
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

	/**
	 * This method initializes tfnonMnlRatio	
	 * 	
	 * @return javax.swing.JFormattedTextField	
	 */
	private JFormattedTextField getTfnonMnlRatio() {
		if (tfnonMnlRatio == null) {
			tfnonMnlRatio = new JFormattedTextField();
			tfnonMnlRatio.setBounds(new Rectangle(112, 221, 84, 22));
		}
		return tfnonMnlRatio;
	}
} // @jve:decl-index=0:visual-constraint="-4,13"
