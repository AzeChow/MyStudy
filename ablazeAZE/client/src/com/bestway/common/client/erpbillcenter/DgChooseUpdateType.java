package com.bestway.common.client.erpbillcenter;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class DgChooseUpdateType extends JDialogBase {
	/**
	 * 更新未生效单据
	 */
	public static final int BillUpdateType_NotEffective = 0;
	/**
	 * 更新已生效未记账单据
	 */
	public static final int BillUpdateType_NotBookkeeping = 1;
	/**
	 * 更新已记账单据
	 */
	public static final int BillUpdateType_Bookkeeping = 2;
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JButton btnSave = null;

	private JButton btnExit = null;

	/**
	 * 是否更新相应单据
	 */
	private boolean isOk = false;
	/**
	 * 更新类型
	 */
	private String updateType = null;  //  @jve:decl-index=0:

	private JCheckBox cbNotEffective = null;

	private JCheckBox cbNotBookkeeping = null;

	private JCheckBox cbBookkeeping = null;

	private JLabel lbCommit = null;
	

	/**
	 * This method initializes
	 * 
	 */
	public DgChooseUpdateType() {
		super();
		initialize();
	}

	@Override
	public void setVisible(boolean f) {

		if (f) {
			initComponents();
		}
		super.setVisible(f);
	}

	private void initComponents() {
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(350, 280));
		this.setContentPane(getJPanel());
		this.setTitle("请选择单据更新的方式");
		this.setContentPane(getJPanel());
		this.setResizable(false);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			lbCommit = new JLabel();
			lbCommit.setBounds(new Rectangle(33, 15, 250, 79));
			lbCommit.setText("<html><body><font color='blue'>使用说明：<br/>1、全选时更新所有单据。<br/>2、根据实际情况选择所需要的更新方式。<br/>3、不更新请点击【关闭】。<font/></body></html>");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getBtnSave(), null);
			jPanel.add(getBtnExit(), null);
			jPanel.add(getCbNotEffective(), null);
			jPanel.add(getCbNotBookkeeping(), null);
			jPanel.add(getCbBookkeeping(), null);
			jPanel.add(lbCommit, null);
		}
		return jPanel;
	}


	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(new Rectangle(100, 200, 64, 26));
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(true);
					dispose();
				}
			});
		}
		return btnSave;
	}


	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(180, 200, 64, 26));
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setOk(false);
					dispose();
				}
			});
		}
		return btnExit;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public String getUpdateType() {
		return updateType;
	}

	private void setUpdateType() {
		String updateType = "";
		if(cbNotEffective.isSelected()) {
			updateType += DgChooseUpdateType.BillUpdateType_NotEffective;
		}
		if(cbNotBookkeeping.isSelected()) {
			updateType += DgChooseUpdateType.BillUpdateType_NotBookkeeping;
		}
		if(cbBookkeeping.isSelected()) {
			updateType += DgChooseUpdateType.BillUpdateType_Bookkeeping;
		}
		
		this.updateType = updateType;
	}

	/**
	 * This method initializes cbNotEffective	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbNotEffective() {
		if (cbNotEffective == null) {
			cbNotEffective = new JCheckBox();
			cbNotEffective.setBounds(new Rectangle(33, 100, 200, 28));
			cbNotEffective.setText("更新未生效单据");
			cbNotEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setUpdateType();
				}
			});
		}
		return cbNotEffective;
	}

	/**
	 * This method initializes cbNotBookkeeping	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbNotBookkeeping() {
		if (cbNotBookkeeping == null) {
			cbNotBookkeeping = new JCheckBox();
			cbNotBookkeeping.setBounds(new Rectangle(33, 130, 200, 28));
			cbNotBookkeeping.setText("更新已生效未记账单据");
			cbNotBookkeeping.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setUpdateType();
				}
			});
		}
		return cbNotBookkeeping;
	}

	/**
	 * This method initializes cbBookkeeping	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbBookkeeping() {
		if (cbBookkeeping == null) {
			cbBookkeeping = new JCheckBox();
			cbBookkeeping.setBounds(new Rectangle(33, 160, 200, 28));
			cbBookkeeping.setText("更新已记账单据");
			cbBookkeeping.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setUpdateType();
				}
			});
		}
		return cbBookkeeping;
	}
} // @jve:decl-index=0:visual-constraint="224,29"
