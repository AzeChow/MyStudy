/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.TxtDBTask;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class DgTxtTaskEdit extends JDialogBase {

	private JPanel				jPanel				= null;
	private JPanel				jPanel2				= null;
	private JButton				jButton				= null;
	private JButton				jButton1			= null;
	private JLabel				jLabel				= null;
	private JTextField			tfTaskName			= null;
	private TxtDBTask			txtDBTask			= null;
	private DataExportAction	dataExportAction	= null;
	private boolean				isAdd				= true;
	private JTableListModel		tableModel			= null;
	private JScrollPane			jScrollPane1		= null;
	private JTextArea			jTextArea1			= null;
	private JLabel				jLabel41			= null;
	private JCheckBox			cbIsParentTask		= null;

	/**
	 * 
	 */
	public DgTxtTaskEdit() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
	}

	private void initialize() {
		this.setForeground(java.awt.SystemColor.windowText);
		this.setContentPane(getJPanel());
		this.setTitle("文本任务修改");
		this.setSize(472, 344);

	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			if (isAdd == false) {
				if (tableModel.getCurrentRow() != null) {
					txtDBTask = (TxtDBTask) tableModel.getCurrentRow();
				}
				showData();
			} else {
				//
				// add data ...
				//				
			}
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
	 * This method initializes cbIsParentTask
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsParentTask() {
		if (cbIsParentTask == null) {
			cbIsParentTask = new JCheckBox();
			cbIsParentTask.setBounds(new Rectangle(53, 255, 104, 24));
			cbIsParentTask.setText("是否为父任务");
		}
		return cbIsParentTask;
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
			jLabel41.setBounds(new Rectangle(30, 49, 59, 28));
			jLabel41.setText("任务备注");
			jLabel = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(30, 17, 59, 21);
			jLabel.setText("任务名称");
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfTaskName(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJScrollPane1(), null);
			jPanel2.add(jLabel41, null);
			jPanel2.add(getCbIsParentTask(), null);
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
			jButton.setBounds(290, 255, 61, 24);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (checkNull()) {
						return;
					}
					saveData();
					DgTxtTaskEdit.this.dispose();
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
			jButton1.setBounds(358, 255, 61, 24);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DgTxtTaskEdit.this.dispose();
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(95, 49, 324, 194));
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

	}

	/** 显示数据 */
	private void showData() {
		tfTaskName.setText(txtDBTask.getTaskname());
		this.jTextArea1.setText(txtDBTask.getNote());
		cbIsParentTask.setSelected(txtDBTask.getIsParentTask() == null ? false
				: txtDBTask.getIsParentTask());
	}

	private boolean checkNull() {
		if (this.tfTaskName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "任务名称不能为空，请输入!", "提示", 1);
			return true;
		}
		return false;
	}

	private void saveData() {
		if (isAdd) {
			TxtDBTask txtDBTask = new TxtDBTask();
			fillData(txtDBTask);

			txtDBTask = dataExportAction.saveTxtDBTask(new Request(CommonVars
					.getCurrUser()), txtDBTask);
			tableModel.addRow(txtDBTask);
		} else {
			fillData(txtDBTask);
			txtDBTask = dataExportAction.saveTxtDBTask(new Request(CommonVars
					.getCurrUser()), txtDBTask);
			tableModel.updateRow(txtDBTask);
		}
	}

	private void fillData(TxtDBTask jdbcTask) {
		jdbcTask.setTaskname(tfTaskName.getText());
		jdbcTask.setCompany(CommonVars.getCurrUser().getCompany());
		jdbcTask.setNote(this.jTextArea1.getText());
		jdbcTask.setIsParentTask(cbIsParentTask.isSelected());
	}

}
