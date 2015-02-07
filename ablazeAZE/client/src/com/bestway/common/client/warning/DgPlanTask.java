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
import com.bestway.common.warning.entity.PlanTask;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgPlanTask extends JDialogBase {

	private JPanel				jPanel			= null;
	private JPanel				jPanel2			= null;
	private JButton				jButton			= null;
	private JButton				jButton1		= null;
	private JLabel				jLabel			= null;
	private JLabel				jLabel1			= null;
	private JLabel				jLabel2			= null;
	private JTextField			tfTaskName		= null;
	private JLabel				jLabel4			= null;
	private PlanTask			planTask		= null;
	private WarningAction		warningAction	= null;
	private boolean				isAdd			= true;
	private JTableListModel		tableModel		= null;
	private JLabel				jLabel5			= null;
	private JComboBox			cbbExecuteKind	= null;
	private JTextField			tfTime			= null;
	private JComboBox			cbbMonth		= null;
	private JScrollPane			jScrollPane1	= null;
	private JTextArea			jTextArea1		= null;
	private JLabel				jLabel41		= null;
	private JTextField			tfNote			= null;
	private JLabel				jLabel3			= null;
	private JCalendarComboBox	cbbTimePoint	= null;

	/**
	 * 
	 */
	public DgPlanTask() {
		super();
		warningAction = (WarningAction) CommonVars.getApplicationContext()
				.getBean("warningAction");
		initialize();
	}

	private void initialize() {
		this.setForeground(java.awt.SystemColor.windowText);
		this.setContentPane(getJPanel());
		this.setTitle("任务计划提示内容");
		this.setSize(472, 402);

	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					planTask = (PlanTask) tableModel.getCurrentRow();
				}
				showData();
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
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(30, 72, 59, 21));
			jLabel3.setText("执行时间");
			jLabel41 = new JLabel();
			jLabel41.setBounds(new Rectangle(30, 137, 59, 21));
			jLabel41.setText("任务内容");
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(30, 17, 59, 21);
			jLabel.setText("任务名称");
			jLabel1.setBounds(30, 44, 59, 21);
			jLabel1.setText("执行频率");
			jLabel2.setBounds(228, 44, 59, 22);
			jLabel2.setText("运行日期");
			jLabel4.setBounds(30, 101, 59, 21);
			jLabel4.setText("任务说明");
			jLabel5.setBounds(227, 73, 59, 21);
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
			jPanel2.add(getTfNote(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getCbbTimePoint(), null);
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
			jButton.setBounds(290, 317, 61, 24);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if (checkNull()) {
						return;
					}
					saveData();
					DgPlanTask.this.dispose();
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
			jButton1.setBounds(358, 317, 61, 24);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {

					DgPlanTask.this.dispose();

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
			cbbExecuteKind.setBounds(96, 44, 115, 22);
			cbbExecuteKind
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (DgPlanTask.this.cbbExecuteKind
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
			tfTime.setBounds(292, 73, 127, 22);
		}
		return tfTime;
	}

	/**
	 * This method initializes cbbMonth
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMonth() {
		if (cbbMonth == null) {
			cbbMonth = new JComboBox();
			cbbMonth.setBounds(292, 44, 127, 22);
		}
		return cbbMonth;
	}

	private void setState() {
		if (planTask == null || planTask.getId() == null) {
			return;
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
			jScrollPane1.setBounds(new Rectangle(95, 137, 324, 167));
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
	/**
	 * This method initializes cbbTimePoint
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbTimePoint() {
		if (cbbTimePoint == null) {
			cbbTimePoint = new JCalendarComboBox();
			cbbTimePoint.setBounds(new Rectangle(96, 72, 115, 23));
		}
		return cbbTimePoint;
	}
	/**
	 * This method initializes tfNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNote() {
		if (tfNote == null) {
			tfNote = new JTextField();
			tfNote.setBounds(new Rectangle(96, 101, 323, 22));
		}
		return tfNote;
	}

	private void initUIComponents() {
		tfTime.setText("00:00:00");
		// 执行频率
		this.cbbExecuteKind.removeAllItems();
		this.cbbExecuteKind.addItem(new ItemProperty(String
				.valueOf(ExcuteKind.TIMESTAMP), "时间点"));
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
		if (this.planTask.getExcuteKind() != null) {
			int executeKind = this.planTask.getExcuteKind();
			cbbExecuteKind.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(executeKind), cbbExecuteKind));
			showDataAndState(executeKind);
		} else {
			cbbExecuteKind.setSelectedIndex(-1);
		}
		tfTaskName.setText(planTask.getItemName());
		this.tfTime.setText(planTask.getExcutetime());
		this.tfNote.setText(planTask.getNote());
		this.jTextArea1.setText(planTask.getContent());
		this.cbbTimePoint.setDate(planTask.getTimestamp());
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
			DgPlanTask.this.cbbMonth.setEnabled(true);
			DgPlanTask.this.cbbTimePoint.setEnabled(false);
			DgPlanTask.this.cbbTimePoint.setDate(null);
		} else if (executeKind == ExcuteKind.DAY
				|| executeKind == ExcuteKind.INTERVAL) {
			jLabel5.setText("执行时间");
			DgPlanTask.this.cbbTimePoint.setEnabled(false);
			DgPlanTask.this.cbbTimePoint.setDate(null);
			DgPlanTask.this.cbbMonth.setEnabled(false);
			DgPlanTask.this.cbbMonth.setSelectedItem(null);
		} else if (executeKind == ExcuteKind.TIMESTAMP) {
			jLabel5.setText("执行时间");
			DgPlanTask.this.cbbTimePoint.setEnabled(true);
			DgPlanTask.this.cbbMonth.setEnabled(false);
			DgPlanTask.this.cbbMonth.setSelectedItem(null);
		}
	}

	private boolean checkNull() {
		if (this.tfTaskName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "任务名称不能为空，请输入!", "提示", 1);
			return true;
		}
		if (DgPlanTask.this.cbbExecuteKind.getSelectedItem() != null) {
			int executeKind = Integer.parseInt((((ItemProperty) cbbExecuteKind
					.getSelectedItem()).getCode()));
			if (executeKind == ExcuteKind.TIMESTAMP) {
				if (this.cbbTimePoint.getDate() == null) {
					JOptionPane.showMessageDialog(this, "年月日日期不能为空，请重新输入!",
							"提示", 1);
					return true;
				}
			}
		}
		try {
			Time.valueOf(this.tfTime.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "时间格式不正确，请重新输入!", "提示", 1);
			return true;
		}
		return false;
	}

	private void saveData() {
		if (isAdd) {
			PlanTask dbtaskex = new PlanTask();
			fillData(dbtaskex);
			dbtaskex = warningAction.savePlanTask(new Request(CommonVars
					.getCurrUser()), dbtaskex);
			tableModel.addRow(dbtaskex);
		} else {
			fillData(planTask);
			planTask = warningAction.savePlanTask(new Request(CommonVars
					.getCurrUser()), planTask);
			tableModel.updateRow(planTask);
		}
	}

	private void fillData(PlanTask planTask) {
		planTask.setItemName(tfTaskName.getText());
		if (cbbExecuteKind.getSelectedItem() != null) {
			planTask.setExcuteKind(Integer
					.parseInt(((ItemProperty) cbbExecuteKind.getSelectedItem())
							.getCode()));
		}
		if (cbbMonth.getSelectedItem() != null) {
			planTask.setExcuteday(((ItemProperty) cbbMonth.getSelectedItem())
					.getCode());
		}
		planTask.setCompany(CommonVars.getCurrUser().getCompany());
		planTask.setExcutetime(this.tfTime.getText());
		planTask.setNote(this.tfNote.getText());
		planTask.setAclUser(CommonVars.getCurrUser());
		planTask.setContent(this.jTextArea1.getText());
		planTask.setTimestamp(this.cbbTimePoint.getDate());
	}

	

}
