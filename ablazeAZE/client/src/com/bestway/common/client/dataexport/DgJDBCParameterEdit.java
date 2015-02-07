/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataexport;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.dataexport.action.DataExportAction;
import com.bestway.common.dataexport.entity.JDBCParameter;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;


/**
 * @author luosheng 2006/9/1
 * 
 */
public class DgJDBCParameterEdit extends JDialogBase {

	private JPanel						jContentPane		= null;
	private JButton						btnSave				= null;
	private JTextField					tfName				= null;
	private JTextField					tfValue				= null;
	private JButton						btnCancel			= null;
	private JTableListModel				tableModel			= null;
	private boolean						isAdd				= true;
	private DataExportAction			dataExportAction	= null;
	private JDBCParameter				obj					= null;
	private JCheckBox					cbCurrentDate		= null;
	private JCustomFormattedTextField	tfAddSubtractDay	= null;
	private JLabel						s					= null;
	private JLabel						s1					= null;

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
	 * This method initializes
	 * 
	 */
	public DgJDBCParameterEdit() {
		super();
		dataExportAction = (DataExportAction) CommonVars
				.getApplicationContext().getBean("dataExportAction");
		initialize();
		setState();
	}

	private void fillWindow() {
		if (obj != null) {
			this.tfName.setText(obj.getPname());
			this.tfValue.setText(obj.getPvalue());
			this.cbCurrentDate.setSelected(obj.getIsNowDate() != null
					&& obj.getIsNowDate());
			this.tfAddSubtractDay.setValue(obj.getAddSubtractDay());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("视图参数编辑");
		this.setSize(353, 243);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						obj = (JDBCParameter) tableModel.getCurrentRow();
					}
					fillWindow();
				} else {

				}
			}
		});

	}

	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			s1 = new JLabel();
			s1.setBounds(new Rectangle(236, 117, 21, 20));
			s1.setText("天");
			s = new JLabel();
			s.setBounds(new Rectangle(76, 117, 81, 23));
			s.setText("当前日期增加");
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(72, 25, 57, 26);
			jLabel.setText("参数名称");
			jLabel1.setBounds(72, 55, 57, 27);
			jLabel1.setText("参数值");
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfName(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfValue(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getCbCurrentDate(), null);
			jContentPane.add(getTfAddSubtractDay(), null);
			jContentPane.add(s, null);
			jContentPane.add(s1, null);
		}
		return jContentPane;
	}

	private void fillData(JDBCParameter obj) {
		obj.setCompany(CommonVars.getCurrUser().getCompany());
		obj.setPname(this.tfName.getText());
		obj.setPvalue(this.tfValue.getText());
		obj.setIsNowDate(this.cbCurrentDate != null
				&& this.cbCurrentDate.isSelected());
		obj.setAddSubtractDay(Double.valueOf(
				tfAddSubtractDay.getValue().toString()).intValue());
	}

	private void saveData() {
		if (isAdd()) {
			JDBCParameter obj = new JDBCParameter();
			fillData(obj);
			obj = dataExportAction.saveJDBCParameter(new Request(CommonVars
					.getCurrUser()), obj);
			tableModel.addRow(obj);
			emptyWindowData();
		} else {
			fillData(obj);
			obj = dataExportAction.saveJDBCParameter(new Request(CommonVars
					.getCurrUser()), obj);
			tableModel.updateRow(obj);
			this.dispose();
		}
	}

	private void emptyWindowData() {
		this.tfName.setText("");
		this.tfValue.setText("");

	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(83, 161, 74, 26);
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes tfName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(140, 26, 143, 26);
		}
		return tfName;
	}

	/**
	 * 
	 * This method initializes tfValue
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfValue() {
		if (tfValue == null) {
			tfValue = new JTextField();
			tfValue.setBounds(141, 57, 141, 24);
		}
		return tfValue;
	}

	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(189, 161, 74, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgJDBCParameterEdit.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * This method initializes cbCurrentDate
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCurrentDate() {
		if (cbCurrentDate == null) {
			cbCurrentDate = new JCheckBox();
			cbCurrentDate.setBounds(136, 91, 148, 21);
			cbCurrentDate.setText("当前日期");
			cbCurrentDate
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setState();
						}
					});
		}
		return cbCurrentDate;
	}

	private void setState() {
		boolean isSelected = cbCurrentDate.isSelected();
		s.setEnabled(isSelected);
		s1.setEnabled(isSelected);
		tfAddSubtractDay.setEnabled(isSelected);
	}

	/**
	 * This method initializes tfAddSubtractDay
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfAddSubtractDay() {
		if (tfAddSubtractDay == null) {
			tfAddSubtractDay = new JCustomFormattedTextField();
			tfAddSubtractDay.setBounds(new Rectangle(160, 117, 72, 22));
			tfAddSubtractDay.setValue(new Double(0));
		}
		return tfAddSubtractDay;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
