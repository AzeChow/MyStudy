/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgWraps extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private CustomBaseAction customBaseAction = null;

	private boolean isAdd = true;

	private JTextField tfCode = null;
	private JTextField tfName = null;
	private String scode = "";
 	
	private Wrap wrap = null;
	/**
	 * This is the default constructor
	 */
	public DgWraps() {
		super();
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
		.getApplicationContext().getBean("customBaseAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(348, 197);
		this.setTitle("加工种类编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						wrap = (Wrap) tableModel.getCurrentRow();
						scode = wrap.getCode();
					}
					fillWindow();
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setText("编码");
			jLabel.setBounds(63, 24, 31, 23);
			jLabel2.setText("名称");
			jLabel2.setBounds(63, 57, 32, 23);
			jPanel2.add(jLabel, null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJButton(), null);
			jPanel2.add(getJButton1(), null);
			jPanel2.add(getTfCode(), null);
			jPanel2.add(getTfName(), null);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.setBounds(75, 114, 71, 26);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					
					if (isAdd == true) {
						Wrap wrap = new Wrap();
						fillCurrCode(wrap);
						if (isRe(DgWraps.this.tfCode.getText())){
							return;
						}
						wrap = customBaseAction.SaveWrap(wrap);
						tableModel.addRow(wrap);

						System.out.println("保存成功！");
						clearData();
					} else {
						fillCurrCode(wrap);
						if (isRe(wrap.getCode())){
							return;
						}
						wrap = customBaseAction.SaveWrap(wrap);
						tableModel.updateRow(wrap);
						DgWraps.this.dispose();
					}

				}
			});

		}
		return jButton;
	}
	private boolean isRe(String data){
    	if (customBaseAction.isReMerger(data,scode)){
			JOptionPane.showMessageDialog(this, "编号重复！", "提示",2);
			return true;
		}
    	return false;
    }
	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("取消");
			jButton1.setBounds(176, 115, 70, 25);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgWraps.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (wrap != null) {
			this.tfCode.setText(wrap.getCode());
			this.tfName.setText(wrap.getName());
		}
	}

	private void fillCurrCode(Wrap wrap) {
		wrap.setCode(this.tfCode.getText().trim());
		wrap.setName(this.tfName.getText().trim());
}
	private void clearData() {
		this.tfCode.setText("");
		this.tfName.setText("");
		
	}

	private boolean checkNull() {
		if (this.tfCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "加工种类编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "加工种类名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
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
	 * @return Returns the isAdd.
	 */
	public boolean isAdd() {
		return isAdd;
	}

	/**
	 * @param isAdd
	 *            The isAdd to set.
	 */
	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	/**

	 * This method initializes tfCode	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(103, 22, 179, 22);
		}
		return tfCode;
	}

	/**

	 * This method initializes tfName	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(103, 58, 179, 22);
		}
		return tfName;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
