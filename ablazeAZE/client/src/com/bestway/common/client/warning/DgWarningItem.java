/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.warning;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.sql.Time;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.constant.ExcuteKind;
import com.bestway.common.warning.action.WarningAction;
import com.bestway.common.warning.entity.TempWarningItem;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgWarningItem extends JDialogBase {

	private JPanel						jPanel			= null;
	private JPanel						jPanel2			= null;
	private JButton						jButton			= null;
	private JButton						jButton1		= null;
	private JLabel						jLabel			= null;
	private JLabel						jLabel1			= null;
	private JLabel						jLabel2			= null;
	private JTextField					tfTaskName		= null;
	private JLabel						jLabel4			= null;
	private WarningItem					warningItem		= null;
	private TempWarningItem				tempWarningItem	= null;  //  @jve:decl-index=0:
	private WarningAction				warningAction	= null;
	private boolean						isAdd			= true;
	private JTableListModel				tableModel		= null;
	private JLabel						jLabel5			= null;
	private JComboBox					cbbExecuteKind	= null;
	private JTextField					tfTime			= null;
	private JComboBox					cbbMonth		= null;
	private JScrollPane					jScrollPane1	= null;
	private JTextArea					jTextArea1		= null;
	private JLabel						jLabel41		= null;
	private JCustomFormattedTextField	tfAmount		= null;

	/**
	 * 
	 */
	public DgWarningItem() {
		super();
		warningAction = (WarningAction) CommonVars.getApplicationContext()
				.getBean("warningAction");
		initialize();
	}

	private void initialize() {
		this.setForeground(java.awt.SystemColor.windowText);
		this.setContentPane(getJPanel());
		this.setTitle("预警项目");
		this.setSize(472, 385);

	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					warningItem = (WarningItem) tableModel.getCurrentRow();
				}
				showData();
			} else {
				//
				// add data ...
				//
				if (tempWarningItem != null) {
					this.tfTaskName.setText(tempWarningItem.getItemName());
				}
			}
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public void setTempWarningItem(TempWarningItem tempWarningItem) {
		this.tempWarningItem = tempWarningItem;
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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
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
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(30, 115, 59, 21));
			jLabel41.setText("任务备注");
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(30, 17, 59, 21);
			jLabel.setText("任务名称");
			jLabel1.setBounds(30, 50, 59, 21);
			jLabel1.setText("执行频率");
			jLabel2.setBounds(228, 50, 59, 22);
			jLabel2.setText("运行日期");
			jLabel4.setBounds(228, 80, 59, 21);
			jLabel4.setText("X  数量");
			jLabel5.setBounds(30, 80, 59, 21);
			jLabel5.setText("执行时间");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getTfTaskName(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getCbbExecuteKind(), null);
			jPanel2.add(getTfTime(), null);
			jPanel2.add(getCbbMonth(), null);
			jPanel2.add(getJScrollPane1(), null);
			jPanel2.add(jLabel41, null);
			jPanel2.add(getTfAmount(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setBounds(290, 297, 61, 24);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if (checkNull()) {
						return;
					}
					saveData();
					DgWarningItem.this.dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取 消");
			jButton1.setBounds(358, 297, 61, 24);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {

					DgWarningItem.this.dispose();

				}

			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTaskName() {
		if (tfTaskName == null) {
			tfTaskName = new JTextField();
			tfTaskName.setBounds(96, 17, 325, 22);
			tfTaskName.setEditable(false);
		}
		return tfTaskName;
	}

	/**
	 * This method initializes cbbExecuteKind
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbExecuteKind() {
		if (cbbExecuteKind == null) {
			cbbExecuteKind = new JComboBox();
			cbbExecuteKind.setBounds(96, 50, 115, 22);
			cbbExecuteKind
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (DgWarningItem.this.cbbExecuteKind
									.getSelectedItem() != null) {
								int executeKind = Integer
										.parseInt((((ItemProperty) cbbExecuteKind
												.getSelectedItem()).getCode()));
								showDataAndState(executeKind);
							}
						}
					});

		}
		return cbbExecuteKind;
	}

	/**
	 * This method initializes tfTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTime() {
		if (tfTime == null) {
			tfTime = new JTextField();
			tfTime.setBounds(95, 80, 116, 22);
		}
		return tfTime;
	}

	/**
	 * This method initializes tfAmount
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JCustomFormattedTextField getTfAmount() {
		if (tfAmount == null) {
			tfAmount = new JCustomFormattedTextField();
			tfAmount.setBounds(new Rectangle(292, 80, 127, 22));
		}
		return tfAmount;
	}

	/**
	 * This method initializes cbbMonth
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMonth() {
		if (cbbMonth == null) {
			cbbMonth = new JComboBox();
			cbbMonth.setBounds(292, 50, 127, 22);
		}
		return cbbMonth;
	}

	private void setState() {
		if (this.tfTaskName.getText().indexOf("X") > -1) {
			this.tfAmount.setEnabled(true);
		} else {
			this.tfAmount.setEnabled(false);
		}
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(95, 115, 324, 167));
			jScrollPane1.setViewportView(getJTextArea1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
		}
		return jTextArea1;
	}

	private void initUIComponents() {
		tfTime.setText("00:00:00");
		// 执行频率
		this.cbbExecuteKind.removeAllItems();
		this.cbbExecuteKind.addItem(new ItemProperty(String
				.valueOf(ExcuteKind.DAY), "每日"));
		this.cbbExecuteKind.addItem(new ItemProperty(String
				.valueOf(ExcuteKind.WEEK), "每周"));
		this.cbbExecuteKind.addItem(new ItemProperty(String
				.valueOf(ExcuteKind.MONTH), "每月"));
		this.cbbExecuteKind.addItem(new ItemProperty(String
				.valueOf(ExcuteKind.INTERVAL), "间隔"));
		this.cbbExecuteKind.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbExecuteKind);
	}

	/** 显示数据 */
	private void showData() {
		if (this.warningItem.getExcuteKind() != null) {
			int executeKind = this.warningItem.getExcuteKind();
			cbbExecuteKind.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(executeKind), cbbExecuteKind));
			showDataAndState(executeKind);
		} else {
			cbbExecuteKind.setSelectedIndex(-1);
		}
		tfTaskName.setText(warningItem.getItemName());
		this.tfTime.setText(warningItem.getExcutetime());
		this.jTextArea1.setText(warningItem.getNote());
		this.tfAmount.setValue(warningItem.getAmount() == null ? 0.0
				: warningItem.getAmount());

	}

	/** 显示数据 */
	private void showDataAndState(int executeKind) {
		// 执行日期
		this.cbbMonth.removeAllItems();
		if (executeKind == ExcuteKind.WEEK) {
			this.cbbMonth.addItem(new ItemProperty("2", "星期一"));
			this.cbbMonth.addItem(new ItemProperty("3", "星期二"));
			this.cbbMonth.addItem(new ItemProperty("4", "星期三"));
			this.cbbMonth.addItem(new ItemProperty("5", "星期四"));
			this.cbbMonth.addItem(new ItemProperty("6", "星期五"));
			this.cbbMonth.addItem(new ItemProperty("7", "星期六"));
			this.cbbMonth.addItem(new ItemProperty("1", "星期天"));
		} else if (executeKind == ExcuteKind.MONTH) {
			//
			// 初始化本月的最后一天
			//				
			for (int i = 1; i <= 32; i++) {
				if (i == 32) {
					this.cbbMonth.addItem(new ItemProperty("L" // L在
							// cronExpression
							// 中代表每月的最后一天
							, "月底日"));
				} else {
					this.cbbMonth.addItem(new ItemProperty(String.valueOf(i),
							String.valueOf(i) + "日"));
				}
			}
		}
		this.cbbMonth.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbMonth);

		//
		// state
		//
		if (executeKind == ExcuteKind.WEEK || executeKind == ExcuteKind.MONTH) {
			jLabel5.setText("执行时间");
			DgWarningItem.this.cbbMonth.setEnabled(true);
			DgWarningItem.this.tfTime.setEditable(true);
		} else if (executeKind == ExcuteKind.DAY
				|| executeKind == ExcuteKind.INTERVAL) {
			jLabel5.setText("执行时间");
			DgWarningItem.this.cbbMonth.setEnabled(false);
			DgWarningItem.this.tfTime.setEditable(true);
			DgWarningItem.this.cbbMonth.setSelectedItem(null);
		}
	}

	private boolean checkNull() {
		if (this.tfTaskName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "任务名称不能为空，请输入!", "提示", 1);
			return true;
		}
		try {
			Time.valueOf(this.tfTime.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "时间格式不正确，请重新输入!", "提示", 1);
			return true;
		}
		if (this.tfAmount.getValue() == null || Double.valueOf(this.tfAmount.getValue().toString()) <= 0) {
			JOptionPane.showMessageDialog(this, "X 数值必须大于 0 ，请重新输入!", "提示", 1);
			return true;
		}
		return false;
	}

	private void saveData() {
		if (isAdd) {
			WarningItem warningItem = new WarningItem();
			fillData(warningItem);
			//
			// add data ...
			//
			if (tempWarningItem != null) {
				warningItem.setWarningItemFlag(tempWarningItem
						.getWarningItemFlag());
			}
			warningItem = warningAction.saveWarningItem(new Request(CommonVars
					.getCurrUser()), warningItem);
			tableModel.addRow(warningItem);
		} else {
			fillData(warningItem);
			warningItem = warningAction.saveWarningItem(new Request(CommonVars
					.getCurrUser()), warningItem);
			tableModel.updateRow(warningItem);
		}
	}

	private void fillData(WarningItem warningItem) {
		warningItem.setItemName(tfTaskName.getText());
		if (cbbExecuteKind.getSelectedItem() != null) {
			warningItem.setExcuteKind(Integer
					.parseInt(((ItemProperty) cbbExecuteKind.getSelectedItem())
							.getCode()));
		}
		if (cbbMonth.getSelectedItem() != null) {
			warningItem
					.setExcuteday(((ItemProperty) cbbMonth.getSelectedItem())
							.getCode());
		}
		warningItem.setCompany(CommonVars.getCurrUser().getCompany());
		warningItem.setExcutetime(this.tfTime.getText());
		warningItem.setNote(this.jTextArea1.getText());
		if (this.tfAmount.getValue() != null) {
			warningItem.setAmount(Double.parseDouble(this.tfAmount.getValue()
					.toString()));
		}
	}

}
