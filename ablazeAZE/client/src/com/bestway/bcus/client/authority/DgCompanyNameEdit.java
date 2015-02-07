package com.bestway.bcus.client.authority;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DgCompanyNameEdit extends JDialogBase{

	private JPanel panel = null;
	private JLabel label = null;
	private JTextField tfName = null;
	private JButton btnSave = null;
	
	private Company company = null;
	
	private SystemAction systemAction = null;

	/**
	 * This method initializes 
	 * 
	 */
	public DgCompanyNameEdit(Company company) {		
		this.company=company;
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		                 .getBean("systemAction");
		initialize();
		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(319, 200));
        this.setContentPane(getPanel());
        this.setTitle("修改公司名称");
		this.setResizable(false);	
	}

	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private JPanel getPanel() {
		if (panel == null) {
			label = new JLabel();
			label.setBounds(new Rectangle(7, 31, 68, 27));
			label.setText("公司名称：");
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(label, null);
			panel.add(getTfName(), null);
			panel.add(getBtnSave(), null);
		}
		return panel;
	}

	/**
	 * This method initializes tfName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();
			tfName.setBounds(new Rectangle(80, 31, 216, 27));
			tfName.setText(company.getName().trim());
		}
		return tfName;
	}

	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(221, 115, 75, 29));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name = tfName.getText().trim();
					if(name==null || "".equals(name)){
						if(JOptionPane.showConfirmDialog(DgCompanyNameEdit.this, "公司名称为空!\n不保存当前值!", "提示", JOptionPane.OK_OPTION)
								==JOptionPane.OK_OPTION){
							DgCompanyNameEdit.this.dispose();
						}
						return;
					}
					//保存数据
					company.setName(name);
					systemAction.saveCompany(company);
					DgCompanyNameEdit.this.dispose();
				}
			});
		}
		return btnSave;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
