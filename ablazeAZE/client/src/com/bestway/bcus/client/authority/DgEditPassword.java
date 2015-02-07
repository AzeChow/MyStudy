package com.bestway.bcus.client.authority;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Point;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;

public class DgEditPassword extends JDialogBase {

	private JPanel jContentPane = null;
	private JLabel lbOldPassword = null;
	private JLabel lbNewPassword = null;
	private JLabel lbAgainPassword = null;
	private JButton btnResetPassword = null;
	private JButton btnSave = null;
	private JButton btnResetAll = null;
	private JTableListModel tableModel = null;
	private AuthorityAction authorityAction;
	private AclUser currentUser = null;  //  @jve:decl-index=0:
	public AclUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(AclUser currentUser) {
		this.currentUser = currentUser;
	}

	private JPasswordField tfOldPassword = null;
	private JPasswordField tfNewPassword = null;
	private JPasswordField tfAgainPassword = null;
	private JLabel lbDigestType = null;
	private JComboBox cbbDigestType = null;
	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes 
	 * 
	 */
	public DgEditPassword() {
		super();
		initialize();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
		.getBean("authorityAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(351, 271));
        this.setContentPane(getJContentPane());
        this.setTitle("修改密码");
      
	}
	public void setVisible(boolean b) {
	    fillWondow();
		super.setVisible(b);
	}
	/**
	 * 初始化栏位数值
	 */
	private void fillWondow() {
		btnResetPassword.setVisible(false);
		AclUser oldcurrentUser=CommonVars.getCurrUser();
		String flag=oldcurrentUser.getUserFlag();
		if(null!=flag && flag.equals("S")){
			btnResetPassword.setVisible(true);
		}
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lbDigestType = new JLabel();
			lbDigestType.setBounds(new Rectangle(52, 145, 59, 18));
			lbDigestType.setText("加密类型");
			lbAgainPassword = new JLabel();
			lbAgainPassword.setText("重复新密码");
			lbAgainPassword.setLocation(new Point(53, 106));
			lbAgainPassword.setSize(new Dimension(62, 18));
			lbNewPassword = new JLabel();
			lbNewPassword.setBounds(new Rectangle(53, 65, 37, 18));
			lbNewPassword.setText("新密码");
			lbOldPassword = new JLabel();
			lbOldPassword.setBounds(new Rectangle(53, 23, 55, 21));
			lbOldPassword.setText("原密码");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lbOldPassword, null);
			jContentPane.add(lbNewPassword, null);
			jContentPane.add(lbAgainPassword, null);
			jContentPane.add(getBtnResetPassword(), null);
			jContentPane.add(getBtnSave(), null);
			jContentPane.add(getBtnResetAll(), null);
			jContentPane.add(getTfOldPassword(), null);
			jContentPane.add(getTfNewPassword(), null);
			jContentPane.add(getTfAgainPassword(), null);
			jContentPane.add(lbDigestType, null);
			jContentPane.add(getCbbDigestType(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnResetPassword	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnResetPassword() {
		if (btnResetPassword == null) {
			btnResetPassword = new JButton();
			btnResetPassword.setBounds(new Rectangle(230, 188, 83, 25));
			btnResetPassword.setText("重置密码");
			btnResetPassword.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					reset();
					DgEditPassword.this.dispose();
				}
			});
		}
		return btnResetPassword;
	}

	private void reset(){
		currentUser.setPassword("admin");
		currentUser = authorityAction.saveUser(new Request(CommonVars
				.getCurrUser()), currentUser);
		JOptionPane.showMessageDialog(this, "用户密码已经重置！",
				"提示!", 1);
	}
	/**
	 * This method initializes btnSave	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("确定");
			btnSave.setBounds(new Rectangle(21, 188, 73, 25));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(checkPassword(currentUser)){
						return ;
					}
					modifyUser();
					DgEditPassword.this.dispose();
				}
			});
		}
		return btnSave;
	}

	public boolean checkPassword(AclUser currentUser){
		if (!tfNewPassword.getText().equals(tfAgainPassword.getText())) {
			JOptionPane.showMessageDialog(this, "您输入的新密码不一致，请重新输入！",
					"提示!", 1);
			return true;
		}
		if (tfAgainPassword.getText()==null || tfAgainPassword.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "用户密码为空!", "提示!", 1);
			return true;
		}
		String oldpass=currentUser.getPassword();
		if(oldpass.contains("{")){ 	//	经过加密
			String[] old = oldpass.split("}");
			String array = old[0];
			String oldPasswordType=array.substring(1);
			String newPassword=CommonVars.messageDigest(oldPasswordType.trim(), tfOldPassword.getText());
			if(!newPassword.equals(oldpass)){
				JOptionPane.showMessageDialog(this, "您输入的原密码错误，请重新输入！",
						"提示!", 1);
				return true;
			}
		}
		else{ 	//	无加密
			if( !tfOldPassword.getText().equals(oldpass)){
				JOptionPane.showMessageDialog(this, "您输入的原密码错误，请重新输入！",
						"提示!", 1);
				return true;
			}
		}
		return false;
	}
	private void modifyUser() {
		String tfpassw = String.valueOf(tfAgainPassword.getPassword()).trim();
		String digestType = cbbDigestType.getSelectedItem().toString();
		currentUser.setPassword(CommonVars.messageDigest(digestType, tfpassw));
		currentUser = authorityAction.saveUser(new Request(CommonVars
				.getCurrUser()), currentUser);
		JOptionPane.showMessageDialog(this, "用户资料已经修改！",
				"提示!", 1);
	}
	/**
	 * This method initializes btnResetAll	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnResetAll() {
		if (btnResetAll == null) {
			btnResetAll = new JButton();
			btnResetAll.setText("取消");
			btnResetAll.setBounds(new Rectangle(122, 188, 83, 25));
			btnResetAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgEditPassword.this.dispose();
				}
			});
		}
		return btnResetAll;
	}

	/**
	 * This method initializes tfOldPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getTfOldPassword() {
		if (tfOldPassword == null) {
			tfOldPassword = new JPasswordField();
			tfOldPassword.setBounds(new Rectangle(151, 23, 98, 22));
		}
		return tfOldPassword;
	}

	/**
	 * This method initializes tfNewPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getTfNewPassword() {
		if (tfNewPassword == null) {
			tfNewPassword = new JPasswordField();
			tfNewPassword.setBounds(new Rectangle(151, 64, 98, 22));
		}
		return tfNewPassword;
	}

	/**
	 * This method initializes tfAgainPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getTfAgainPassword() {
		if (tfAgainPassword == null) {
			tfAgainPassword = new JPasswordField();
			tfAgainPassword.setBounds(new Rectangle(151, 103, 98, 22));
		}
		return tfAgainPassword;
	}

	/**
	 * This method initializes cbbDigestType	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCbbDigestType() {
		if (cbbDigestType == null) {
			cbbDigestType = new JComboBox();
			cbbDigestType.setBounds(new Rectangle(150, 142, 98, 22));
			cbbDigestType.addItem("SHA-512");
			cbbDigestType.addItem("MD5");
			cbbDigestType.addItem("SHA-1");
			cbbDigestType.addItem("SHA-256");
			cbbDigestType.addItem("不加密");
			cbbDigestType.setSelectedItem("SHA-512");
		}
		return cbbDigestType;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
