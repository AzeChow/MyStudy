/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import javax.swing.JComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgSysArea extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JTextField tfAreaCode = null;

	private JTextField tfAreaName = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;

	private boolean isAdd = true;

	private SysArea currentArea = null; // qiye

	private MaterialManageAction materialManageAction = null;

	private JComboBox cbbCountry = null;

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
	public DgSysArea() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initUI();
	}

	private void fillWindow() {		
		if (currentArea != null) {
			this.tfAreaCode.setText(currentArea.getCode());
			this.tfAreaName.setText(currentArea.getName());
			this.cbbCountry.setSelectedItem(currentArea.getCountry());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("国家区域编辑");
		this.setSize(367, 181);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						currentArea = (SysArea) tableModel.getCurrentRow();
					}
					fillWindow();
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
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(43, 18, 97, 26);
			jLabel.setText("国家区域编号");
			jLabel1.setBounds(44, 49, 96, 27);
			jLabel1.setText("国家区域名称");
			jLabel2.setBounds(44, 81, 94, 26);
			jLabel2.setText("关务国家资料");
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfAreaCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfAreaName(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbCountry(), null);
		}
		return jContentPane;
	}

	private void fillData(SysArea area) {
		area.setCode(tfAreaCode.getText().trim());
		area.setName(tfAreaName.getText().trim());
		if (CommonVars.getCurrUser().getCompany() != null) {
			area.setCompany(CommonVars.getCurrUser().getCompany());
		}
		area.setCountry((Country) this.cbbCountry.getSelectedItem());
	}

	private void saveData() {
		if (isAdd()) {
			SysArea area = new SysArea();
			fillData(area);
			area = materialManageAction.saveSysArea(new Request(CommonVars.getCurrUser()), area);
			tableModel.addRow(area);
			emptyWindowData();
		} else {
			SysArea area = (SysArea) tableModel.getCurrentRow();
			fillData(area);
			area = materialManageAction.saveSysArea(new Request(CommonVars.getCurrUser()), area);
			tableModel.updateRow(area);
			this.dispose();
		}
	}

	private boolean checkNull() {
		if (tfAreaCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "国家区域编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (tfAreaName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "国家区域名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
	}

	private boolean checkMultiple() {
		SysArea area = materialManageAction.findSysAreaByCode(new Request(
				CommonVars.getCurrUser()), tfAreaCode.getText()
				.trim());
		if (area != null) {
			if (!isAdd) {
				if (!area.getCode().equals(currentArea.getCode())) {
					JOptionPane.showMessageDialog(this, "国家区域编号不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "国家区域编号不能重复,请重新输入!", "提示!",
						1);
				return true;
			}
		}
		return false;
	}

	private void emptyWindowData() {
		this.tfAreaCode.setText("");
		this.tfAreaName.setText("");
		this.cbbCountry.setSelectedItem(null);
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
			btnSave.setBounds(69, 116, 74, 26);
			btnSave.setText("确定");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					saveData();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 
	 * This method initializes tfAreaCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfAreaCode() {
		if (tfAreaCode == null) {
			tfAreaCode = new JTextField();
			tfAreaCode.setBounds(158, 19, 143, 26);
		}
		return tfAreaCode;
	}

	/**
	 * 
	 * This method initializes tfAreaName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfAreaName() {
		if (tfAreaName == null) {
			tfAreaName = new JTextField();
			tfAreaName.setBounds(159, 50, 141, 24);
		}
		return tfAreaName;
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
			btnCancel.setBounds(175, 116, 74, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgSysArea.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCountry() {
		if (cbbCountry == null) {
			cbbCountry = new JComboBox();
			cbbCountry.setBounds(new Rectangle(159, 79, 141, 24));
		}
		return cbbCountry;
	}

	private void initUI() {
		// 关务国家资料
		this.cbbCountry.setModel(new DefaultComboBoxModel(CustomBaseList
				.getInstance().getCountrys().toArray()));
		this.cbbCountry.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCountry, "code", "name");
		cbbCountry.setSelectedIndex(-1);

	}
} // @jve:decl-index=0:visual-constraint="10,10"
