/*
 * Created on 2004-6-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgShareCode extends JDialogBase {

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JTextField jtname = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private boolean isAdd = true;

	private ShareCode share = null;

	private JTextField jtcode = null;
	private String codeType = null;
	/**
	 * This is the default constructor
	 */
	public DgShareCode() {
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
		this.setSize(324, 187);
		this.setTitle("编辑");

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (isAdd == false) {
					if (tableModel.getCurrentRow() != null) {
						share = (ShareCode) tableModel.getCurrentRow();
					}
					fillWindow();
				}
			}
		});
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			java.awt.GridBagConstraints gridBagConstraints6 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints5 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints4 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints3 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jLabel.setText("代码");
			jLabel2.setText("名称");
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 10;
			gridBagConstraints1.insets = new java.awt.Insets(36,34,7,20);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipadx = 9;
			gridBagConstraints2.insets = new java.awt.Insets(7,34,15,21);
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.ipadx = 153;
			gridBagConstraints3.ipady = -1;
			gridBagConstraints3.insets = new java.awt.Insets(6,21,13,50);
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.ipadx = 15;
			gridBagConstraints4.insets = new java.awt.Insets(14,65,17,16);
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.ipadx = 25;
			gridBagConstraints5.insets = new java.awt.Insets(15,16,16,63);
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.gridwidth = 2;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.ipadx = 155;
			gridBagConstraints6.insets = new java.awt.Insets(33,21,6,48);
			jPanel2.add(jLabel, gridBagConstraints1);
			jPanel2.add(jLabel2, gridBagConstraints2);
			jPanel2.add(getJTextField2(), gridBagConstraints3);
			jPanel2.add(getJButton(), gridBagConstraints4);
			jPanel2.add(getJButton1(), gridBagConstraints5);
			jPanel2.add(getJTextField3(), gridBagConstraints6);
		}
		return jPanel2;
	}

	/**
	 * 
	 * This method initializes jTextField2
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getJTextField2() {
		if (jtname == null) {
			jtname = new JTextField();
		}
		return jtname;
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
						DgShareCode.this.dispose();
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

					DgShareCode.this.dispose();

				}

			});

		}
		return jButton1;
	}

	private void fillWindow() {
		if (share != null) {
			this.jtcode.setText(share.getCode());
			this.jtname.setText(share.getName());
		}
	}

	private void fillCalUnit(ShareCode share) {
		share.setCode(this.jtcode.getText().trim());
		share.setName(this.jtname.getText().trim());
		share.setTag(this.getCodeType().trim());
		share.setCompany(CommonVars.getCurrUser().getCompany()); //公司id
}
	private void clearData() {
		this.jtname.setText("");
		this.jtcode.setText("");
	}

	private boolean checkNull() {
		if (this.jtcode.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "编码不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (this.jtname.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		return false;
	}

	private boolean checkMultiple() {
		ShareCode sharecode = materialManageAction.findTaxaTionByCode(new Request(CommonVars
				.getCurrUser()),this.jtcode.getText().trim());
		if (sharecode != null) {
			if (!isAdd) {
				if (!sharecode.getId().equals(share.getId())) {
					JOptionPane.showMessageDialog(this, "编码不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "编码不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		ShareCode sharename = materialManageAction.findTaxaTionByName(new Request(CommonVars
				.getCurrUser()), this.jtname.getText().trim());
		if (sharename != null) {
			if (!isAdd) {
				if (!sharename.getId().equals(share.getId())) {
					JOptionPane.showMessageDialog(this, "名称不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "名称不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}		
		return false;
	}

	private void addData() {
		ShareCode share = new ShareCode();
		fillCalUnit(share);

		share = materialManageAction.saveShare(new Request(CommonVars
				.getCurrUser()), share);
		tableModel.addRow(share);

		System.out.println("保存成功！");
	}

	private void modifyData() {
		fillCalUnit(share);
		share = materialManageAction.saveShare(new Request(CommonVars
				.getCurrUser()), share);
		tableModel.updateRow(share);

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

	 * This method initializes jtcode	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField3() {
		if (jtcode == null) {
			jtcode = new JTextField();
		}
		return jtcode;
	}

	/**
	 * @param isAdd The isAdd to set.
	 */
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	/**
	 * @return Returns the codeType.
	 */
	public String getCodeType() {
		return codeType;
	}
	/**
	 * @param codeType The codeType to set.
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	
		
		
	}
	

	/**
	 * @return Returns the share.
	 */
	public ShareCode getShare() {
		return share;
	}
	/**
	 * @param share The share to set.
	 */
	public void setShare(ShareCode share) {
		this.share = share;
	}
 } //  @jve:decl-index=0:visual-constraint="10,10"
