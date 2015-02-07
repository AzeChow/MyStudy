/*
 * Created on 2004-9-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.bcus.dataimport.entity.DBTaskSel;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.text.DateFormatter;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDBTaskAdd extends JDialogBase {

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JTextField tfTxtTaskName = null;

	private JLabel jLabel4 = null;

	private DBTaskEx dbTaskEx = null; // 任务主表

	private DBTaskSel dbTaskSel = null; // 任务明细表

	private DataImportAction dataImportAction = null;

	private boolean isAdd = true;

	private JTableListModel tableModel = null;

	private JLabel jLabel5 = null;

	private DateFormatter dateFormatter = null; // @jve:decl-index=0:

	private JComboBox jComboBox = null;

	private JTextField jTextField = null;

	private JComboBox jComboBox1 = null;

	private JComboBox jComboBox2 = null;

	private JTextArea jTextArea = null;

	private JScrollPane jScrollPane = null;

	private JComboBox jComboBox3 = null;

	private JLabel jLabel7 = null;

	private JComboBox jComboBox4 = null;

	private JCheckBox cbIsParentTask = null;

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
	 * 
	 */
	public DgDBTaskAdd() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
	}

	private void initialize() {
		this.setForeground(java.awt.SystemColor.windowText);
		this.setContentPane(getJPanel());
		this.setTitle("DB导入任务设置");
		this.setSize(422, 315);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
					}
					initUI();
					fillWindow();
				} else {
					initUI();
				}
				setState();
			}
		});

	}

	private void initUI() {
		jTextField.setText("00:00:00");
		// 执行频率
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty("0", "手动"));
		this.jComboBox.addItem(new ItemProperty("1", "每日"));
		this.jComboBox.addItem(new ItemProperty("2", "每周"));
		this.jComboBox.addItem(new ItemProperty("3", "每月"));
		this.jComboBox.addItem(new ItemProperty("4", "间隔"));
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance()
				.setComboBoxEditor(this.jComboBox);
		// 导入选项
		this.jComboBox2.removeAllItems();
		//this.jComboBox2.addItem(new ItemProperty("0", "不处理重复数据"));
		//this.jComboBox2.addItem(new ItemProperty("1", "覆盖重复数据"));
		this.jComboBox2.addItem(new ItemProperty("3", "直接保存"));
		this.jComboBox2.addItem(new ItemProperty("2", "更新重复数据"));
		this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox2);
		// 导入处理选项
		this.jComboBox3.removeAllItems();
		this.jComboBox3.addItem("忽略");
		this.jComboBox3.addItem("终止");
		this.jComboBox3.setUI(new CustomBaseComboBoxUI(84));
		// 执行频率
		this.jComboBox4.removeAllItems();
		this.jComboBox4.addItem(new ItemProperty("0", "未执行"));
		this.jComboBox4.addItem(new ItemProperty("1", "正在执行"));
		this.jComboBox4.addItem(new ItemProperty("2", "执行结束"));
		this.jComboBox4.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox4);

	}

	private void fillWindow() {
		
		// 执行频率
		if (this.dbTaskEx.getExcutekind() != null) {
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.dbTaskEx.getExcutekind()), jComboBox));
			// 执行日期
			if ((((ItemProperty) jComboBox.getSelectedItem()).getCode())
					.equals("2")) {
				getriqi(1);
				jComboBox1.setEnabled(true);
				this.jTextField.setEditable(true);
			} else if ((((ItemProperty) jComboBox.getSelectedItem()).getCode())
					.equals("3")) {
				getriqi(2);
				jComboBox1.setEnabled(true);
				this.jTextField.setEditable(true);
			} else if ((((ItemProperty) jComboBox.getSelectedItem()).getCode())
					.equals("0")) {
				jComboBox1.setEnabled(false);
				this.jTextField.setEditable(false);
				this.jComboBox1.setSelectedIndex(-1);
			} else {
				jComboBox1.setEnabled(false);
				this.jTextField.setEditable(true);
				this.jComboBox1.setSelectedIndex(-1);
			}
		} else {
			jComboBox.setSelectedIndex(-1);
		}
		// 导入选项
		if (this.dbTaskEx.getInputSelect() != null) {
			jComboBox2.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.dbTaskEx.getInputSelect()), jComboBox2));
		} else {
			jComboBox2.setSelectedIndex(-1);
		}
		if (this.dbTaskEx.getExecuteState() != null) {
			jComboBox4.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.dbTaskEx.getExecuteState()), jComboBox4));
		} else {
			jComboBox4.setSelectedIndex(-1);
		}
		tfTxtTaskName.setText(dbTaskEx.getTaskname());
		/*
		 * if (dbTaskEx.getIseffice().equals(new Boolean(true))){
		 * jCheckBox.setSelected(true); } else { jCheckBox.setSelected(false); }
		 */
		this.jTextArea.setText(dbTaskEx.getNote());
		this.jTextField.setText(dbTaskEx.getExcutetime());
		if (dbTaskEx.getDeal() != null && !dbTaskEx.getDeal().equals("")) {
			this.jComboBox3.setSelectedItem(dbTaskEx.getDeal());
		}
		if (dbTaskEx.getIsParentTask()!=null&&dbTaskEx.getIsParentTask()){
			cbIsParentTask.setSelected(true);
		}else{
			cbIsParentTask.setSelected(false);
		}
	}

	private void getriqi(int yy) {
		// 执行日期
		this.jComboBox1.removeAllItems();
		if (yy == 1) {
			this.jComboBox1.addItem(new ItemProperty("2", "星期一"));
			this.jComboBox1.addItem(new ItemProperty("3", "星期二"));
			this.jComboBox1.addItem(new ItemProperty("4", "星期三"));
			this.jComboBox1.addItem(new ItemProperty("5", "星期四"));
			this.jComboBox1.addItem(new ItemProperty("6", "星期五"));
			this.jComboBox1.addItem(new ItemProperty("7", "星期六"));
			this.jComboBox1.addItem(new ItemProperty("1", "星期天"));
		} else if (yy == 2) {
			//
			// 初始化本月的最后一天
			//				
			for (int i = 1; i <= 32; i++) {
				if(i == 32){
					this.jComboBox1.addItem(new ItemProperty("L" //L在 cronExpression 中代表每月的最后一天
							 ,"月底日"));
				}else{
					this.jComboBox1.addItem(new ItemProperty(String.valueOf(i),
						String.valueOf(i) + "日"));
				}
			}
		}
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1);
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
			jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();

			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(19, 18, 58, 23);
			jLabel.setText("任务名称");
			jLabel1.setBounds(19, 51, 60, 21);
			jLabel1.setText("执行频率");
			jLabel2.setBounds(228, 51, 59, 22);
			jLabel2.setText("运行日期");
			jLabel3.setBounds(19, 118, 60, 20);
			jLabel3.setText("导入选项");
			jLabel4.setBounds(18, 156, 59, 21);
			jLabel4.setText("任务说明");
			jLabel5.setBounds(18, 84, 64, 22);
			jLabel5.setText("执行时间");
			jLabel6.setBounds(228, 84, 76, 26);
			jLabel6.setText("导入处理选项");
			jLabel7.setBounds(229, 117, 60, 21);
			jLabel7.setText("执行状态");
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getTfTxtTaskName(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(getJComboBox1(), null);
			jPanel2.add(getJComboBox2(), null);
			jPanel2.add(getJScrollPane(), null);
			jPanel2.add(jLabel6, null);
			jPanel2.add(getJComboBox3(), null);
			jPanel2.add(jLabel7, null);
			jPanel2.add(getJComboBox4(), null);
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
			jButton.setBounds(222, 240, 61, 24);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					if (checkNull()) {
						return;
					}
					if (isAdd()) {
						addDBTaskEx();
					} else {
						modifyDBTaskEx();
					}
					DgDBTaskAdd.this.dispose();
				}
			});
		}
		return jButton;
	}

	private boolean checkNull() {
		if (this.tfTxtTaskName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "任务名称不能为空，请输入!", "提示", 1);
			return true;
		}
		try {
			Time.valueOf(this.jTextField.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "时间格式不正确，请重新输入!", "提示", 1);
			return true;
		}
		return false;
	}

	private void addDBTaskEx() {
		DBTaskEx dbtaskex = new DBTaskEx();
		fillData(dbtaskex);
		dbtaskex = dataImportAction.saveDBTaskEx(new Request(CommonVars
				.getCurrUser()), dbtaskex);
		tableModel.addRow(dbtaskex);

	}

	private void modifyDBTaskEx() {
		fillData(dbTaskEx);
		dbTaskEx = dataImportAction.saveDBTaskEx(new Request(CommonVars
				.getCurrUser()), dbTaskEx);
		tableModel.updateRow(dbTaskEx);
	}

	private void fillData(DBTaskEx dbtaskex) {
		dbtaskex.setTaskname(tfTxtTaskName.getText());
		if (jComboBox.getSelectedItem() != null) {
			dbtaskex.setExcutekind(((ItemProperty) jComboBox.getSelectedItem())
					.getCode());
		}
		if (jComboBox1.getSelectedItem() != null) {
			dbtaskex.setExcuteday(((ItemProperty) jComboBox1.getSelectedItem())
					.getCode());
		}
		if (jComboBox4.getSelectedItem() != null) {
			dbtaskex.setExecuteState(Integer.valueOf(((ItemProperty) jComboBox4
					.getSelectedItem()).getCode()));
		}
		if (jComboBox2.getSelectedItem() != null) {
			dbtaskex.setInputSelect(((ItemProperty) jComboBox2
					.getSelectedItem()).getCode());
		}
		// txttaskex.setExcutetime()
		dbtaskex.setNote(this.jTextArea.getText());
		/*
		 * if (this.jCheckBox.isSelected()){ dbtaskex.setIseffice(new
		 * Boolean(true)); dbtaskex.setEfficeDate(new Date()); } else {
		 * dbtaskex.setIseffice(new Boolean(false)); }
		 */
		dbtaskex.setCompany(CommonVars.getCurrUser().getCompany());
		dbtaskex.setCreatedate(CommonVars.dateToStandDate(new Date()));
		dbtaskex.setCreateuser(CommonVars.getCurrUser().getUserName());
		dbtaskex.setExcutetime(this.jTextField.getText());
		dbtaskex.setIsParentTask(cbIsParentTask.isSelected());
		if (this.jComboBox3.getSelectedItem() != null) {
			dbtaskex.setDeal(this.jComboBox3.getSelectedItem().toString());
		}
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
			jButton1.setBounds(309, 240, 61, 24);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {

					DgDBTaskAdd.this.dispose();

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
	private JTextField getTfTxtTaskName() {
		if (tfTxtTaskName == null) {
			tfTxtTaskName = new JTextField();
			tfTxtTaskName.setBounds(96, 17, 300, 24);
		}
		return tfTxtTaskName;
	}

	/**
	 * This method initializes dateFormatter
	 * 
	 * @return javax.swing.text.DateFormatter
	 */
	private DateFormatter getDateFormatter() {
		if (dateFormatter == null) {
			dateFormatter = new DateFormatter();
		}
		return dateFormatter;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(96, 50, 115, 22);
			jComboBox.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (DgDBTaskAdd.this.jComboBox.getSelectedItem() != null) {
						if ((((ItemProperty) DgDBTaskAdd.this.jComboBox
								.getSelectedItem()).getCode()).equals("2")) {
							getriqi(1);
							jLabel5.setText("执行时间");
							DgDBTaskAdd.this.jComboBox1.setEnabled(true);
							DgDBTaskAdd.this.jTextField.setEditable(true);
						} else if ((((ItemProperty) DgDBTaskAdd.this.jComboBox
								.getSelectedItem()).getCode()).equals("3")) {
							jLabel5.setText("执行时间");
							getriqi(2);
							DgDBTaskAdd.this.jComboBox1.setEnabled(true);
							DgDBTaskAdd.this.jTextField.setEditable(true);
						} else if ((((ItemProperty) DgDBTaskAdd.this.jComboBox
								.getSelectedItem()).getCode()).equals("0")) {
							jLabel5.setText("执行时间");
							DgDBTaskAdd.this.jComboBox1.setEnabled(false);
							DgDBTaskAdd.this.jTextField.setEditable(false);
							DgDBTaskAdd.this.jComboBox1.setSelectedIndex(-1);
						} else if ((((ItemProperty) DgDBTaskAdd.this.jComboBox
								.getSelectedItem()).getCode()).equals("4")) {
							jLabel5.setText("间隔时间");
							DgDBTaskAdd.this.jComboBox1.setEnabled(false);
							DgDBTaskAdd.this.jTextField.setEditable(true);
							DgDBTaskAdd.this.jComboBox1.setSelectedIndex(-1);
						} else {
							jLabel5.setText("执行时间");
							DgDBTaskAdd.this.jComboBox1.setEnabled(false);
							DgDBTaskAdd.this.jTextField.setEditable(true);
							DgDBTaskAdd.this.jComboBox1.setSelectedIndex(-1);
						}
					}
				}
			});

		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(95, 83, 116, 22);
		}
		return jTextField;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(292, 50, 102, 22);
		}
		return jComboBox1;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(96, 117, 115, 22);
		}
		return jComboBox2;
	}

	/**
	 * 
	 * This method initializes jTextArea
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextArea
	 * 
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(96, 151, 295, 72);
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jComboBox3
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(310, 84, 84, 23);
		}
		return jComboBox3;
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setBounds(292, 117, 102, 22);
		}
		return jComboBox4;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsParentTask() {
		if (cbIsParentTask == null) {
			cbIsParentTask = new JCheckBox();
			cbIsParentTask.setBounds(new java.awt.Rectangle(96, 239, 101, 25));
			cbIsParentTask.setText("是否是父任务");
		}
		return cbIsParentTask;
	}

	private void setState() {
		if(dbTaskEx==null||dbTaskEx.getId()==null){
			return;
		}
		List list = dataImportAction.findDBTaskSel(new Request(CommonVars
				.getCurrUser()), dbTaskEx);
		cbIsParentTask.setEnabled(list.size() <= 0);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
