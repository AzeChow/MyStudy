/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrCode;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCurrCode extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private CurrCode curr = null;  //企业
	private Curr customcurr = null; //海关

	private JTextField tfCode = null;
	private JTextField tfName = null;
	private JTextField tfSign = null;
	private JTextField jtcustomcurr = null;
	private JButton jButton2 = null;
	/**
	 * This is the default constructor
	 */
	public DgCurrCode() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel2());
		this.setSize(415, 236);
		this.setTitle("币别代码编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						curr = (CurrCode) tableModel.getCurrentRow();
						customcurr = curr.getCurr();
					}
					fillWindow();
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			java.awt.GridBagConstraints gridBagConstraints26 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints25 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints24 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints23 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints22 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints21 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints20 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints19 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints18 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints17 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints16 = new GridBagConstraints();

			javax.swing.JLabel jLabel1 = new JLabel();
			jLabel1.setVisible(false);

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jLabel.setText("币别编码");
			jLabel2.setText("币别名称");
			jLabel5.setText("币别符号");
			jLabel1.setText("关务币别");
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.gridwidth = 2;
			gridBagConstraints16.ipadx = 12;
			gridBagConstraints16.ipady = 5;
			gridBagConstraints16.insets = new java.awt.Insets(27,63,5,20);
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 1;
			gridBagConstraints17.ipadx = 9;
			gridBagConstraints17.ipady = 5;
			gridBagConstraints17.insets = new java.awt.Insets(5,63,5,0);
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.gridy = 2;
			gridBagConstraints18.gridwidth = 2;
			gridBagConstraints18.ipadx = 11;
			gridBagConstraints18.ipady = 9;
			gridBagConstraints18.insets = new java.awt.Insets(4,64,5,20);
			gridBagConstraints19.gridx = 1;
			gridBagConstraints19.gridy = 4;
			gridBagConstraints19.gridwidth = 2;
			gridBagConstraints19.ipadx = 13;
			gridBagConstraints19.ipady = -2;
			gridBagConstraints19.insets = new java.awt.Insets(14,1,17,15);
			gridBagConstraints20.gridx = 3;
			gridBagConstraints20.gridy = 4;
			gridBagConstraints20.ipadx = 12;
			gridBagConstraints20.ipady = -3;
			gridBagConstraints20.insets = new java.awt.Insets(15,15,17,21);
			gridBagConstraints21.gridx = 2;
			gridBagConstraints21.gridy = 0;
			gridBagConstraints21.gridwidth = 3;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.ipadx = 175;
			gridBagConstraints21.insets = new java.awt.Insets(27,20,6,65);
			gridBagConstraints22.gridx = 2;
			gridBagConstraints22.gridy = 1;
			gridBagConstraints22.gridwidth = 3;
			gridBagConstraints22.weightx = 1.0;
			gridBagConstraints22.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints22.ipadx = 176;
			gridBagConstraints22.insets = new java.awt.Insets(8,20,3,64);
			gridBagConstraints23.gridx = 2;
			gridBagConstraints23.gridy = 2;
			gridBagConstraints23.gridwidth = 3;
			gridBagConstraints23.weightx = 1.0;
			gridBagConstraints23.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints23.ipadx = 176;
			gridBagConstraints23.insets = new java.awt.Insets(7,20,7,64);
			gridBagConstraints24.gridx = 0;
			gridBagConstraints24.gridy = 3;
			gridBagConstraints24.ipadx = 9;
			gridBagConstraints24.ipady = 5;
			gridBagConstraints24.insets = new java.awt.Insets(6,63,13,0);
			gridBagConstraints25.gridx = 2;
			gridBagConstraints25.gridy = 3;
			gridBagConstraints25.gridwidth = 2;
			gridBagConstraints25.weightx = 1.0;
			gridBagConstraints25.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.ipadx = 146;
			gridBagConstraints25.insets = new java.awt.Insets(7,20,13,0);
			gridBagConstraints26.gridx = 4;
			gridBagConstraints26.gridy = 3;
			gridBagConstraints26.ipadx = -13;
			gridBagConstraints26.ipady = -7;
			gridBagConstraints26.insets = new java.awt.Insets(7,1,14,63);
			jPanel2.add(jLabel, gridBagConstraints16);
			jPanel2.add(jLabel2, gridBagConstraints17);
			jPanel2.add(jLabel5, gridBagConstraints18);
			jPanel2.add(getJButton(), gridBagConstraints19);
			jPanel2.add(getJButton1(), gridBagConstraints20);
			jPanel2.add(getTfCode(), gridBagConstraints21);
			jPanel2.add(getTfName(), gridBagConstraints22);
			jPanel2.add(getTfSign(), gridBagConstraints23);
			jPanel2.add(jLabel1, gridBagConstraints24);
			jPanel2.add(getJtcustomcurr(), gridBagConstraints25);
			jPanel2.add(getJButton2(), gridBagConstraints26);
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
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()){
						return;
					}
					if (isAdd == true) {
						addData();
						clearData();
					} else {
						modifyData();
						DgCurrCode.this.dispose();
					}

				}
			});

		}
		return jButton;
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
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCurrCode.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (curr != null) {
			this.tfCode.setText(curr.getCode());
			this.tfName.setText(curr.getName());
			this.tfSign.setText(curr.getCurrSign());
			if (this.customcurr != null)
			{
				this.jtcustomcurr.setText(customcurr.getName());
			}
			else
			{
				this.jtcustomcurr.setText("");
			}
		}
	}

	private void fillCurrCode(CurrCode curr) {
		curr.setCode(this.tfCode.getText().trim());
		curr.setName(this.tfName.getText().trim());
		curr.setCurrSign(this.tfSign.getText().trim());
		curr.setCompany(CommonVars.getCurrUser().getCompany()); 
		if (customcurr != null) {
			curr.setCurr(customcurr);
		}
}
	private void clearData() {
		this.tfCode.setText("");
		this.tfName.setText("");
		this.tfSign.setText("");
		this.jtcustomcurr.setText("");
		this.customcurr = null;
		
	}

	private boolean checkNull() {
		if (this.tfCode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "币别编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.tfName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "币别名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
	}

	private boolean checkMultiple() {
		CurrCode currcode = materialManageAction.findCurrByCode(new Request(CommonVars
		.getCurrUser()), this.tfCode.getText().trim());
		if (currcode != null) {
			if (!isAdd) {
				if (!currcode.getId().equals(curr.getId())) {
					JOptionPane.showMessageDialog(this, "币别编号不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "仓库编号不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		CurrCode currname = materialManageAction.findCurrByName(new Request(CommonVars
				.getCurrUser()), this.tfName.getText().trim());
		if (currname != null) {
			if (!isAdd) {
				if (!currname.getId().equals(curr.getId())) {
					JOptionPane.showMessageDialog(this, "币别名称不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "仓库名称不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}		
		return false;
	}

	private void addData() {
		CurrCode curr = new CurrCode();
		fillCurrCode(curr);

		curr = materialManageAction.saveCurr(new Request(CommonVars
				.getCurrUser()), curr);
		tableModel.addRow(curr);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillCurrCode(curr);
		curr = materialManageAction.saveCurr(new Request(CommonVars
				.getCurrUser()), curr);
		tableModel.updateRow(curr);

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
	 * @return Returns the curr.
	 */
	public CurrCode getCurr() {
		return curr;
	}
	/**
	 * @param curr The curr to set.
	 */
	public void setCurr(CurrCode curr) {
		this.curr = curr;
	}
	/**

	 * This method initializes tfCode	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
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
		}
		return tfName;
	}

	/**

	 * This method initializes tfSign	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getTfSign() {
		if (tfSign == null) {
			tfSign = new JTextField();
		}
		return tfSign;
	}

	/**

	 * This method initializes jtcustomcurr	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJtcustomcurr() {
		if (jtcustomcurr == null) {
			jtcustomcurr = new JTextField();
			jtcustomcurr.setVisible(false);
			jtcustomcurr.setEditable(false);
		}
		return jtcustomcurr;
	}

	/**

	 * This method initializes jButton2	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("...");
			jButton2.setVisible(false);
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					Curr c = (Curr) CommonQuery.getInstance().getCustomCurr();
					if (c != null) {
						customcurr = c;
						getJtcustomcurr().setText(c.getName());
					}

				}
			});

		}
		return jButton2;
	}

      } //  @jve:decl-index=0:visual-constraint="10,10"
