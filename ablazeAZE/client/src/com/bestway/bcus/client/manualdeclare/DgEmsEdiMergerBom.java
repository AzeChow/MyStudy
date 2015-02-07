/*
 * Created on 2004-7-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.math.RoundingMode;
import java.text.ParseException;

import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerExgBom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;

import com.bestway.client.util.JTableListModel;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.SendState;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
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
public class DgEmsEdiMergerBom extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField5 = null;
	private JTextField jTextField7 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JComboBox jComboBox = null;
	private JFormattedTextField tfUnitWaste = null;
	private JFormattedTextField tfWaste = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private ManualDeclareAction manualdeclearAction = null;
	private JTableListModel tableModel = null;
	private EmsEdiMergerExgBom emsBom = null;
	private EmsEdiMergerHead emsEdiMergerHead = null;
	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse
	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse
	private JLabel jLabel7 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;

	private int totalCount = 0; // 总行
	private int rowCount = 0; // 当前行

	private String isSendSign = "";

	/**
	 * This is the default constructor
	 */
	public DgEmsEdiMergerBom() {
		super();
		initialize();
		manualdeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(451, 239);
		this.setTitle("编辑BOM");
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				isSendSign = manualdeclearAction.getBpara(new Request(
						CommonVars.getCurrUser()), BcusParameter.EmsSEND_Sign);
				if (tableModel.getCurrentRow() != null) {
					totalCount = tableModel.getRowCount(); // 总记录数
					emsBom = (EmsEdiMergerExgBom) tableModel.getCurrentRow();
					rowCount = tableModel.getCurrRowCount();// 当前记录
					initUI();
					fillWindow();
					setstate();
				}
			}
		});
	}

	private void initUI() {
		jComboBox.addItem("半成品单耗");
		jComboBox.addItem("最终成品单耗");
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty("1", "半成品单耗"));
		this.jComboBox.addItem(new ItemProperty("2", "最终成品单耗"));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.jComboBox);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfUnitWaste, 9);
		CustomFormattedTextFieldUtils.setFormatterFactory(tfWaste, 5);
	}

	private void fillWindow() {
		jTextField.setText(emsBom.getPtNo());
		jTextField1.setText(emsBom.getName());
		tfUnitWaste.setText(emsBom.getUnitWaste()==null?"":CommonUtils.formatDoubleByDigit(emsBom.getUnitWaste(),6));
		tfWaste.setText(emsBom.getWaste() == null ? "" : String.valueOf(emsBom
				.getWaste()));
		if (emsBom.getUnit() != null)
			jTextField5.setText(emsBom.getUnit().getName());
		else
			jTextField5.setText("");
		// jCalendarComboBox.setDate(emsBom.getBeginDate());
		jCalendarComboBox1.setDate(emsBom.getEndDate());
		jTextField7.setText(emsBom.getNote());
		if (emsBom.getMachinKind() != null) {
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(emsBom.getMachinKind()), jComboBox));
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel12 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("料件货号");
			jLabel.setBounds(19, 22, 53, 18);
			jLabel1.setBounds(227, 24, 60, 17);
			jLabel1.setText("料件名称");
			jLabel2.setBounds(19, 48, 54, 20);
			jLabel2.setText("单耗");
			jLabel3.setBounds(227, 50, 59, 17);
			jLabel3.setText("损耗率");
			jLabel4.setBounds(18, 75, 53, 20);
			jLabel4.setText("加工性质");
			jLabel5.setBounds(228, 77, 61, 18);
			jLabel5.setText("计量单位");
			jLabel12.setBounds(17, 133, 65, 21);
			jLabel12.setText("备注");
			jLabel6.setBounds(17, 100, 63, 21);
			jLabel6.setText("归并后版本");
			jLabel7.setBounds(409, 49, 17, 21);
			jLabel7.setForeground(java.awt.Color.red);
			jLabel7
					.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel7.setText("%");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
			jContentPane.add(jLabel12, null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel6, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getTfUnitWaste(), null);
			jContentPane.add(getTfWaste(), null);
			jContentPane.add(getJCalendarComboBox1(), null);
			jContentPane.add(jLabel7, null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
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
			jTextField.setBounds(91, 22, 123, 22);
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
			jTextField1.setBounds(306, 23, 120, 22);
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
			jTextField5.setBounds(306, 76, 121, 22);
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
			jTextField7.setBounds(89, 133, 338, 22);
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
			jButton.setBounds(154, 173, 70, 25);
			jButton.setText("保存");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isEmpty()) {
						return;
					}
					fillData(emsBom);
					emsBom = manualdeclearAction.saveEmsEdiMergerExgBom(
							new Request(CommonVars.getCurrUser()), emsBom);
					tableModel.updateRow(emsBom);
					DgEmsEdiMergerBom.this.dispose();

				}
			});

		}
		return jButton;
	}

	private boolean isEmpty() {
		if (tfUnitWaste.getText().equals("")) {
			JOptionPane.showMessageDialog(DgEmsEdiMergerBom.this, "单耗不能为空!",
					"提示", 2);
			return true;
		}
		return false;
	}

	public void fillData(EmsEdiMergerExgBom emsBom) { // 保存
		EmsEdiMergerExgBom old = new EmsEdiMergerExgBom();
		old = (EmsEdiMergerExgBom) emsBom.clone();
		emsBom.setUnitWaste(Double.valueOf(tfUnitWaste.getText()));
		emsBom.setWaste(Double.valueOf(tfWaste.getText()));
		if (jComboBox.getSelectedItem() != null) {
			emsBom.setMachinKind(((ItemProperty) this.jComboBox
					.getSelectedItem()).getCode());
		}
		// emsBom.setEndDate(jCalendarComboBox1.getDate());
		emsBom.setNote(jTextField7.getText());

		if (!emsBom.fullEquals(old)
				&& !emsBom.getModifyMark().equals(ModifyMarkState.DELETED)
				&& !emsBom.getModifyMark().equals(ModifyMarkState.ADDED)
				&& emsBom.getEmsEdiMergerVersion().getEmsEdiMergerBefore()
						.getEmsEdiMergerExgAfter().getEmsEdiMergerHead()
						.getDeclareType().equals(DelcareType.MODIFY)) {
			emsBom.setModifyMark(ModifyMarkState.MODIFIED); // 已修改
			if (isSendSign != null && "1".equals(isSendSign)) {
				emsBom.setSendState(Integer.valueOf(SendState.WAIT_SEND)); // 准备申报
			}
			emsBom.setChangeDate(new Date());
			emsBom.setModifyTimes(emsEdiMergerHead.getModifyTimes());
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
			jButton1.setBounds(228, 173, 69, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEmsEdiMergerBom.this.dispose();

				}
			});

		}
		return jButton1;
	}

	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(91, 74, 124, 20);
		}
		return jComboBox;
	}

	/**
	 * 
	 * This method initializes tfUnitWaste
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfUnitWaste() {
		if (tfUnitWaste == null) {
			tfUnitWaste = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfUnitWaste.setBounds(92, 48, 124, 22);
			// tfUnitWaste.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfUnitWaste;
	}

	/**
	 * 
	 * This method initializes tfWaste
	 * 
	 * 
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWaste() {
		if (tfWaste == null) {
			tfWaste = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWaste.setBounds(306, 50, 102, 22);
			// tfWaste.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfWaste;
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
	 * 
	 * This method initializes jCalendarComboBox1
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1.setBounds(91, 100, 124, 22);
			jCalendarComboBox1.setEnabled(false);
		}
		return jCalendarComboBox1;
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
	 * 
	 * This method initializes defaultFormatterFactory
	 * 
	 * 
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 * 
	 *         //
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
	// DecimalFormat decimalFormat = new DecimalFormat();
	// decimalFormat.setMaximumFractionDigits(9);
	// decimalFormat.setGroupingSize(0);
	// decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
	// numberFormatter.setFormat(decimalFormat);
	// }
	// return numberFormatter;
	// }
	/**
	 * @return Returns the emsEdiMergerHead.
	 */
	public EmsEdiMergerHead getEmsEdiMergerHead() {
		return emsEdiMergerHead;
	}

	/**
	 * @param emsEdiMergerHead
	 *            The emsEdiMergerHead to set.
	 */
	public void setEmsEdiMergerHead(EmsEdiMergerHead emsEdiMergerHead) {
		this.emsEdiMergerHead = emsEdiMergerHead;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(70, 173, 80, 25);
			jButton2.setText("上一条");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isEmpty()) {
						return;
					}
					fillData(emsBom);
					emsBom = manualdeclearAction.saveEmsEdiMergerExgBom(
							new Request(CommonVars.getCurrUser()), emsBom);
					tableModel.updateRow(emsBom);
					rowCount--;
					emsBom = (EmsEdiMergerExgBom) tableModel
							.getObjectByRow(rowCount);
					fillWindow();
					setstate();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(301, 173, 80, 25);
			jButton3.setText("下一条");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isEmpty()) {
						return;
					}
					fillData(emsBom);
					emsBom = manualdeclearAction.saveEmsEdiMergerExgBom(
							new Request(CommonVars.getCurrUser()), emsBom);
					tableModel.updateRow(emsBom);
					rowCount++;
					emsBom = (EmsEdiMergerExgBom) tableModel
							.getObjectByRow(rowCount);
					fillWindow();
					setstate();
				}
			});
		}
		return jButton3;
	}

	private void setstate() {
		jButton2.setEnabled(rowCount > 0);
		jButton3.setEnabled(rowCount < totalCount - 1);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
