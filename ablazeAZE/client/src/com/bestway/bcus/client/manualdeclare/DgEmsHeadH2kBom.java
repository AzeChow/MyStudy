/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;

import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.util.Date;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgEmsHeadH2kBom extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField5 = null;
	private JTextField jTextField7 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JFormattedTextField tfUnitWear = null;
	private JFormattedTextField tfWear = null;
	private ManualDeclareAction manualdeclearAction = null;
	private JTableListModel tableModel = null; // 成品BOM
	private EmsHeadH2k emsHeadH2k = null; // 帐册表头  //  @jve:decl-index=0:
	private EmsHeadH2kBom emsBom = null; // @jve:decl-index=0:
	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse
	private JTextField jTextField2 = null;
	private JTextField jTextField3 = null;
	private JTextField jTextField4 = null;
	private JTextField jTextField6 = null;
	private JLabel jLabel6 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private int totalCount = 0; // 总行
	private int rowCount = 0; // 当前行
	private JButton jButton4 = null;
	private int dataState = DataState.EDIT;
	private String isEmsH2kSendSign = "";

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kBom() {
		super();
		initialize();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		CustomFormattedTextFieldUtils.setFormatterFactory(tfUnitWear, 9);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfWear, 5);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(452, 251);
		this.setTitle("电子帐册成品单耗维护");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				isEmsH2kSendSign = manualdeclearAction.getBpara(new Request(
						CommonVars.getCurrUser()),
						BcusParameter.EmsEdiH2kSend_Sign);
				if (tableModel.getCurrentRow() != null) {
					totalCount = tableModel.getRowCount();
					// emsBom = (EmsHeadH2kBom) tableModel.getCurrentRow();
					rowCount = tableModel.getCurrRowCount();
					emsBom = (EmsHeadH2kBom) tableModel
							.getObjectByRow(rowCount);
					fillWindow();
					setState();
				}
			}
		});
	}

	private void fillWindow() {
		jTextField2.setText(String.valueOf(emsBom.getEmsHeadH2kExg()
				.getSeqNum()));
		jTextField3.setText(emsBom.getEmsHeadH2kVersion().getVersion()
				.toString());
		jTextField.setText(String.valueOf(emsBom.getSeqNum()));
		if (emsBom.getComplex() != null)
			jTextField1.setText(emsBom.getComplex().getCode());
		else
			jTextField1.setText("");
		jTextField4.setText(emsBom.getName());
		jTextField6.setText(emsBom.getSpec());
		tfUnitWear.setText(emsBom.getUnitWear() == null ? "" : String
				.valueOf(emsBom.getUnitWear()));
		tfWear.setText(emsBom.getWear() == null ? "" : String.valueOf(emsBom
				.getWear()));
		if (emsBom.getUnit() != null)
			jTextField5.setText(emsBom.getUnit().getName());
		else
			jTextField5.setText("");
		jTextField7.setText(emsBom.getNote());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel6 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel10 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料件序号");
			jLabel.setBounds(17, 48, 53, 18);
			jLabel1.setBounds(228, 50, 60, 17);
			jLabel1.setText("商品编码");
			jLabel2.setBounds(17, 106, 54, 20);
			jLabel2.setText("单耗");
			jLabel3.setBounds(17, 135, 59, 17);
			jLabel3.setText("损耗率");
			jLabel4.setBounds(228, 77, 53, 20);
			jLabel4.setText("型号规格");
			jLabel5.setBounds(228, 103, 61, 18);
			jLabel5.setText("申报单位");
			jLabel10.setBounds(17, 77, 60, 21);
			jLabel10.setText("商品名称");
			jLabel12.setBounds(228, 134, 65, 21);
			jLabel12.setText("备注");
			jLabel7.setBounds(17, 19, 54, 21);
			jLabel7.setText("成品序号");
			jLabel8.setBounds(228, 19, 50, 20);
			jLabel8.setText("成品版本");
			jLabel6.setBounds(191, 134, 17, 21);
			jLabel6.setForeground(java.awt.Color.red);
			jLabel6
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel6.setText("%");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getJTextField5(), null);
			jContentPane.add(getJTextField7(), null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getTfUnitWear(), null);
			jContentPane.add(getTfWear(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(jLabel8, null);
			jContentPane.add(getJTextField3(), null);
			jContentPane.add(getJTextField4(), null);
			jContentPane.add(getJTextField6(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJButton4(), null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(86, 48, 123, 22);
			jTextField.setForeground(java.awt.Color.red);
			jTextField.setEditable(false);
		}
		return jTextField;
	}

	/**
	 * 
	 * This method initializes jTextField1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(308, 49, 120, 22);
			jTextField1.setEditable(false);
		}
		return jTextField1;
	}

	/**
	 * 
	 * This method initializes jTextField5
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField5() {
		if (jTextField5 == null) {
			jTextField5 = new JTextField();
			jTextField5.setBounds(308, 102, 121, 22);
			jTextField5.setEditable(false);
		}
		return jTextField5;
	}

	/**
	 * 
	 * This method initializes jTextField7
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (jTextField7 == null) {
			jTextField7 = new JTextField();
			jTextField7.setBounds(308, 133, 121, 22);
			jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == 10) {
						if (rowCount < totalCount - 1) {
							nextData();
						} else if (rowCount == totalCount - 1) {
							saveData();
						}
					}
				}
			});
		}
		return jTextField7;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(184, 178, 70, 25);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkIsSendState()) {
						return;
					}
					saveData();
				}
			});

		}
		return jButton;
	}

	private void saveData() {
		fillData(emsBom);
		emsBom = manualdeclearAction.saveEmsHeadH2kBom(new Request(CommonVars
				.getCurrUser()), emsBom);
		tableModel.updateRow(emsBom);
		dataState = DataState.BROWSE;
		setState();
	}

	public void fillData(EmsHeadH2kBom emsBom) { // 保存
		EmsHeadH2kBom old = new EmsHeadH2kBom();
		old = (EmsHeadH2kBom) emsBom.clone();
		emsBom.setUnitWear(Double.valueOf(tfUnitWear.getText()));
		emsBom.setWear(Double.valueOf("".equals(tfWear.getText())?"0.0":tfWear.getText()));
		emsBom.setNote(jTextField7.getText());
		if (!emsBom.fullEquals(old)
				&& !emsBom.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsBom.getModifyMark().equals(ModifyMarkState.ADDED)) {
			emsBom.setModifyMark(ModifyMarkState.MODIFIED);
			if (isEmsH2kSendSign != null && "1".equals(isEmsH2kSendSign)) {
				emsBom.setSendState(1); // 准备申报
			}
			emsBom.setModifyTimes(emsBom.getEmsHeadH2kVersion()
					.getEmsHeadH2kExg().getEmsHeadH2k().getModifyTimes());
			emsBom.setChangeDate(new Date());
		}

	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(264, 178, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					fillWindow();
					dataState = DataState.BROWSE;
					setState();

				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes tfUnitWear
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitWear() {
		if (tfUnitWear == null) {
			tfUnitWear = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWear.setBounds(86, 106, 124, 22);
			// tfUnitWear.setFormatterFactory(getDefaultFormatterFactory());
			tfUnitWear.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == 10) {
						KeyAdapterControl.setAddListener(true);
						getTfWear().requestFocus();
					}
				}
			});
		}
		return tfUnitWear;
	}

	/**
	 * 
	 * This method initializes tfWear
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWear() {
		if (tfWear == null) {
			tfWear = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWear.setBounds(86, 134, 105, 22);
			// tfWear.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfWear;
	}

	// private Double strToDouble(String value) { //转换strToDouble 填充数据
	// try {
	// if (value == null || "".equals(value)) {
	// // return new Double("0");
	// return null;
	// }
	// getNumberFormatter().setValueClass(Double.class);
	// return (Double) getNumberFormatter().stringToValue(value);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// //return new Double("0");
	// return null;
	// }
	// }

	// private String doubleToStr(Double value) { //转换doubleToStr 取数据
	// try {
	// if (value == null || value.doubleValue() == 0) {
	// return null;
	// }
	// getNumberFormatter().setValueClass(Double.class);
	// return getNumberFormatter().valueToString(value);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
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
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	// private DefaultFormatterFactory getDefaultFormatterFactory() {
	// if (defaultFormatterFactory == null) {
	// defaultFormatterFactory = new DefaultFormatterFactory();
	// defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
	// defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
	// }
	// return defaultFormatterFactory;
	// }
	/**
	 * 
	 * This method initializes numberFormatter
	 * 
	 * 
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	// private NumberFormatter getNumberFormatter() {
	// if (numberFormatter == null) {
	// numberFormatter = new NumberFormatter();
	// DecimalFormat decimalFormat1 = new DecimalFormat(); // @jve:decl-index=0:
	// decimalFormat1.setGroupingSize(0);
	// decimalFormat1.setMaximumFractionDigits(9);
	// numberFormatter.setFormat(decimalFormat1);
	// }
	// return numberFormatter;
	// }
	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setBounds(86, 19, 123, 22);
			jTextField2.setEditable(false);
		}
		return jTextField2;
	}

	/**
	 * 
	 * This method initializes jTextField3
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setBounds(308, 18, 121, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**
	 * 
	 * This method initializes jTextField4
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(86, 77, 124, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**
	 * 
	 * This method initializes jTextField6
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(308, 76, 121, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**
	 * @return Returns the emsHeadH2k.
	 */
	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(21, 177, 70, 25);
			jButton2.setText("上一条");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					preData();
				}
			});
		}
		return jButton2;
	}

	private void preData() {
		RollSave();
		dataState = DataState.EDIT;
		rowCount--;
		emsBom = (EmsHeadH2kBom) tableModel.getObjectByRow(rowCount);
		fillWindow();
		setState();
		KeyAdapterControl.setAddListener(true);
		getTfUnitWear().requestFocus();
	}

	private void RollSave() {
		if (dataState == DataState.EDIT) {
			fillData(emsBom);
			emsBom = manualdeclearAction.saveEmsHeadH2kBom(new Request(
					CommonVars.getCurrUser()), emsBom);
			tableModel.updateRow(emsBom);
		}
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(343, 178, 75, 25);
			jButton3.setText("下一条");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nextData();
				}
			});
		}
		return jButton3;
	}

	private void nextData() {
		RollSave();
		dataState = DataState.EDIT;
		rowCount++;
		emsBom = (EmsHeadH2kBom) tableModel.getObjectByRow(rowCount);
		fillWindow();
		setState();
		KeyAdapterControl.setAddListener(true);
		getTfUnitWear().requestFocus();
	}

	private void setState() {
		jButton2.setEnabled(rowCount > 0);
		jButton3.setEnabled(rowCount < totalCount - 1);
		jButton4.setEnabled(dataState == DataState.BROWSE); // 修改
		jButton.setEnabled(dataState != DataState.BROWSE); // 保存
		jButton1.setEnabled(dataState != DataState.BROWSE); // 取消
		tfUnitWear.setEditable(dataState != DataState.BROWSE);
		tfWear.setEditable(dataState != DataState.BROWSE);
		jTextField7.setEditable(dataState != DataState.BROWSE);

	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(102, 178, 70, 25);
			jButton4.setText("修改");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return jButton4;
	}

	/**
	 * 判断此账册是否已经发送
	 * 
	 * @return
	 */
	private boolean checkIsSendState() {
		emsHeadH2k = manualdeclearAction.getH2k(new Request(CommonVars
				.getCurrUser()), emsHeadH2k.getId());
		if (DeclareState.WAIT_EAA.equals(emsHeadH2k.getDeclareState())) {
			JOptionPane.showMessageDialog(DgEmsHeadH2kBom.this,
					"此账册已经发送，正等待审批，不能执行此动作！");
			return true;
		}
		return false;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
