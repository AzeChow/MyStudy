/*
 * Created on 2004-6-25
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgScmCoi extends JDialogBase {

	private JPanel jContentPane = null;

	private JButton btnSave = null;

	private JTextField tfCoiCode = null;

	private JTextField tfCoiName = null;

	private JButton btnCancel = null;

	private JTableListModel tableModel = null;

	private boolean isAdd = true;

	private ScmCoi currentScmCoi = null;

	private MaterialManageAction materialManageAction = null;

	private JComboBox cbbCoiProperty = null;

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
	public DgScmCoi() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
	}

	private void fillWindow() {
		if (currentScmCoi != null) {
			this.tfCoiCode.setText(currentScmCoi.getCode());
			this.tfCoiName.setText(currentScmCoi.getName());
			this.cbbCoiProperty.setSelectedIndex(getCurrentIndex(currentScmCoi
					.getCoiProperty()));
		}
	}

	private int getCurrentIndex(String value) {
		for (int i = 0; i < this.cbbCoiProperty.getItemCount(); i++) {
			if (((ItemProperty) this.cbbCoiProperty.getItemAt(i)).getCode()
					.equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("物料类别编辑");
		this.setSize(353, 178);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						currentScmCoi = (ScmCoi) tableModel.getCurrentRow();
					}
					fillWindow();
				} else {
					DgScmCoi.this.cbbCoiProperty.setSelectedIndex(-1);
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
			jLabel.setText("物料类别编号");
			jLabel1.setBounds(44, 49, 96, 27);
			jLabel1.setText("物料类别名称");
			jLabel2.setBounds(42, 79, 102, 22);
			jLabel2.setText("物料类别属性");
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getTfCoiCode(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getTfCoiName(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbCoiProperty(), null);
		}
		return jContentPane;
	}

	private void fillData(ScmCoi coi) {
		if (CommonVars.getCurrUser().getCompany() != null) {
			coi.setCompany(CommonVars.getCurrUser().getCompany());
		}
		coi.setCode(tfCoiCode.getText().trim());
		coi.setName(tfCoiName.getText().trim());
		if (this.cbbCoiProperty.getSelectedItem() != null) {
			coi.setCoiProperty(((ItemProperty) this.cbbCoiProperty
					.getSelectedItem()).getCode());
		}
	}

	private void saveData() {
		if (isAdd()) {
			ScmCoi coi = new ScmCoi();
			fillData(coi);
			coi = materialManageAction.saveScmCoi(new Request(CommonVars
					.getCurrUser()), coi);
			tableModel.addRow(coi);
			emptyWindowData();
		} else {
			ScmCoi coi = (ScmCoi) tableModel.getCurrentRow();
			fillData(coi);
			coi = materialManageAction.saveScmCoi(new Request(CommonVars
					.getCurrUser()), coi);
			tableModel.updateRow(coi);
			this.dispose();
		}
	}

	private boolean checkNull() {
		if (tfCoiCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "物料类别编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (tfCoiName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "物料类别名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (cbbCoiProperty.getSelectedItem()==null){
			JOptionPane.showMessageDialog(this, "物料类别属性不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
	}

	private boolean checkMultiple() {
		ScmCoi coi = materialManageAction.findScmCoiByCode(new Request(
				CommonVars.getCurrUser()), tfCoiCode.getText().trim());
		if (coi != null) {
			if (!isAdd) {
				if (!coi.getId().equals(currentScmCoi.getId())) {
					JOptionPane.showMessageDialog(this, "物料类别编号不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "物料类别编号不能重复,请重新输入!", "提示!",
						1);
				return true;
			}
			
			if(DgScmCoi.this.cbbCoiProperty.getSelectedItem() == null){
					JOptionPane.showMessageDialog(DgScmCoi.this,
							"物料类别属性不能为空！", "提示", 1);
			    return true;
				}
			}
		
		return false;
	}

	private void emptyWindowData() {
		this.tfCoiCode.setText("");
		this.tfCoiName.setText("");
		this.cbbCoiProperty.setSelectedIndex(-1);

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
			btnSave.setBounds(70, 112, 74, 26);
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
	 * This method initializes tfCoiCode
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfCoiCode() {
		if (tfCoiCode == null) {
			tfCoiCode = new JTextField();
			tfCoiCode.setBounds(158, 19, 143, 26);
		}
		return tfCoiCode;
	}

	/**
	 * 
	 * This method initializes tfCoiName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfCoiName() {
		if (tfCoiName == null) {
			tfCoiName = new JTextField();
			tfCoiName.setBounds(159, 50, 141, 24);
		}
		return tfCoiName;
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
			btnCancel.setBounds(176, 112, 74, 26);
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgScmCoi.this.dispose();
				}
			});

		}
		return btnCancel;
	}

	/**
	 * 
	 * This method initializes cbbCoiProperty
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 *  
	 */
	private JComboBox getCbbCoiProperty() {
		if (cbbCoiProperty == null) {
//			  JOptionPane.showMessageDialog(null, "物料类别属性为空，请选择!", "提示!", 2);
//		   	   return cbbCoiProperty;
//		  }
			cbbCoiProperty = new JComboBox();
			cbbCoiProperty.setBounds(160, 77, 138, 22);
			cbbCoiProperty.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			cbbCoiProperty.addItem(new ItemProperty(
					MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbCoiProperty
					.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
			cbbCoiProperty.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			cbbCoiProperty.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbCoiProperty.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));
			cbbCoiProperty.addItem(new ItemProperty(null,""));
		}

		return cbbCoiProperty;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
