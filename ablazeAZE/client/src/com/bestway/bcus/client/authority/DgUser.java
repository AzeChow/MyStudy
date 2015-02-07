/*
 * Created on 2004-6-8
 * editby xxm
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.InitialFocusSetter;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgUser extends JDialogBase {
	/**
	 * Commons Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DgUser.class);

	private javax.swing.JPanel jContentPane = null;

	private JTextField tfLoginName = null;

	private JTextField tfUserName = null;

	private JTextField tfEmail = null;

	private JButton btnDone = null;

	private JButton btnCancel = null;

	private JPasswordField tfPassword = null;

	private JTableListModel tableModel = null;

	private boolean isAdd = true;

	private AuthorityAction authorityAction;

	private AclUser currentUser = null; // @jve:decl-index=0:

	private JCheckBox cbIsSuperUser = null;

	private JComboBox cbCompany = null;

	private JCheckBox cbIsForbid = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbDigestType = null;

	private JLabel jLabel32 = null;

	private JPasswordField tfNewPassword = null;

	private JLabel lbNewPassword = null;

	/**
	 * @param arg0
	 * @throws java.awt.HeadlessException
	 */

	public boolean isIsAdd() {
		return isAdd;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public void setIsAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * This is the default constructor
	 */
	public DgUser() {
		super();
		initialize();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */

	private void initialize() {

		setSize(399, 266);

		setTitle("用户编辑");

		setHelpId("user");

		setContentPane(getJContentPane());

		addWindowListener(new WindowAdapter() {

			public void windowOpened(WindowEvent e) {

				fillCbCompany(); // 填入公司到combobox

				if (isAdd == false) {
					cbbDigestType.setVisible(false);
					jLabel5.setVisible(false);
					tfNewPassword.setVisible(false);
					jLabel32.setVisible(false);
					tfPassword.setVisible(false);
					lbNewPassword.setVisible(false);
					// tfPassword.setEnabled(false);
					// tfNewPassword.setEnabled(false);
					if (tableModel.getCurrentRow() != null) {
						currentUser = (AclUser) tableModel.getCurrentRow();
					}
					fillWindow();
					// if (currentUser.getLoginName().equals("admin")) {
					// tfLosinName.setEditable(false);
					// cbIsSuperUser.setEnabled(false);
					// }
				} else {
					tfPassword.setEnabled(false);
				}
			}
		});

	}

	public void setVisible(boolean b) {

		setState();

		// 设置最初的焦点
		InitialFocusSetter.setInitialFocus(this, tfLoginName);

		super.setVisible(b);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			lbNewPassword = new JLabel();
			lbNewPassword.setBounds(new Rectangle(18, 158, 37, 18));
			lbNewPassword.setText("新密码");
			jLabel32 = new JLabel();
			jLabel32.setBounds(new Rectangle(208, 156, 69, 18));
			jLabel32.setText("重复新密码");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(209, 90, 58, 21));
			jLabel5.setText("加密类型");
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setText("登陆编号");
			jLabel.setBounds(19, 31, 75, 20);
			jLabel1.setText("用户名称");
			jLabel1.setBounds(19, 60, 64, 20);
			jLabel2.setText("所属公司");
			jLabel2.setBounds(19, 123, 75, 20);
			jLabel4.setText("Email");
			jLabel4.setBounds(19, 90, 56, 20);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(getTfLoginName(), null);
			jContentPane.add(getTfUserName(), null);
			jContentPane.add(getTfPassword(), null);
			jContentPane.add(getTfEmail(), null);
			jContentPane.add(getBtnDone(), null);
			jContentPane.add(getBtnCancel(), null);
			jContentPane.add(getCbCompany(), null);
			jContentPane.add(jLabel5, null);
			jContentPane.add(getCbbDigestType(), null);
			jContentPane.add(getCbIsForbid(), null);
			jContentPane.add(getCbIsSuperUser(), null);
			jContentPane.add(jLabel32, null);
			jContentPane.add(getTfNewPassword(), null);
			jContentPane.add(lbNewPassword, null);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes tfLoginName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfLoginName() {
		if (tfLoginName == null) {
			tfLoginName = new JTextField();
			tfLoginName.setBounds(98, 31, 98, 21);
		}
		return tfLoginName;
	}

	/**
	 * 初始化用户名框
	 * 
	 * @return JTextField
	 */
	private JTextField getTfUserName() {
		if (tfUserName == null) {
			tfUserName = new JTextField();
			tfUserName.setBounds(97, 59, 98, 21);
		}
		return tfUserName;
	}

	/**
	 * 初始化 邮箱框
	 * 
	 * @return JTextField
	 */
	private JTextField getTfEmail() {
		if (tfEmail == null) {
			tfEmail = new JTextField();
			tfEmail.setBounds(97, 89, 96, 21);
		}
		return tfEmail;
	}

	private boolean checkNull() {
		if (tfLoginName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "登陆编号不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (tfUserName.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(this, "用户名称不能为空,请输入!", "提示!", 1);
			return true;
		}
		// if ((tfPassword.getText() == null ? "" : tfPassword.getText().trim())
		// .equals("")) {
		// JOptionPane.showMessageDialog(this, "用户密码为空!", "提示!", 1);
		// return true;
		// }
		if (cbCompany.getSelectedIndex() == -1) {
			/*
			 * && !tfLoginName.getText().trim (). toLowerCase().equals("admin")
			 */
			JOptionPane.showMessageDialog(this, "所属工厂不能为空,请输入!", "提示!", 1);
			return true;
		}
		if (!tfEmail.getText().trim().equals("")) {
			if (!tfEmail
					.getText()
					.matches(
							"^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*$")) {
				JOptionPane
						.showMessageDialog(this, "邮件地址格式有错,请重新输入!", "提示!", 1);
				return true;
			}
		}

		return false;
	}

	private boolean checkMultiple() {
		AclUser user = authorityAction.findUser(
				new Request(CommonVars.getCurrUser()), tfLoginName.getText()
						.trim());
		AclUser currentUser1 = authorityAction.findUser(
				new Request(CommonVars.getCurrUser()), CommonVars.getCurrUser()
						.getLoginName());
		if (null == currentUser1.getUserFlag()
				|| !currentUser1.getUserFlag().equals("S")) {
			if (cbIsSuperUser.isSelected()) {
				JOptionPane.showMessageDialog(this, "普通用户不能新增超级用户,请重新输入!",
						"提示!", 1);
				return true;
			}
			if (cbIsForbid.isSelected()) {
				JOptionPane.showMessageDialog(this, "普通用户不能禁用用户,请重新输入!", "提示!",
						1);
				return true;
			}
		}
		if (user != null) {
			if (!isAdd) {
				if (!user.getId().equals(currentUser.getId())) {
					JOptionPane.showMessageDialog(this, "登陆名不能重复,请重新输入!",
							"提示!", 1);
					return true;
				}
			} else {
				JOptionPane.showMessageDialog(this, "登陆名不能重复,请重新输入!", "提示!", 1);
				return true;
			}
		}
		return false;
	}

	private void fillCbCompany() {

		List Companies = authorityAction.findCompanies(new Request(CommonVars
				.getCurrUser()));

		cbCompany.removeAllItems();

		for (int i = 0; i < Companies.size(); i++) {
			cbCompany.addItem(Companies.get(i));
		}

		cbCompany.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null) {
					setText(((Company) value).getName());
				} else {
					setText("");
				}
				return this;
			}
		});
		cbCompany.setSelectedItem(CommonVars.getCurrUser().getCompany());
	}

	private void fillUser(AclUser user) {

		user.setLoginName(tfLoginName.getText().trim());

		user.setUserName(tfUserName.getText().trim());

		String tfpassw = String.valueOf(tfPassword.getPassword()).trim();

		String digestType = cbbDigestType.getSelectedItem().toString();

		if (isAdd == true) {

			user.setPassword(CommonVars.messageDigest(digestType, tfpassw));

		}
		// else{
		// if(!tfOldPassword.getText().equals("")){
		// user.setPassword(CommonVars.messageDigest(digestType, tfpassw));
		// }
		// else{
		// AclUser currentUser1 = authorityAction.findUser(new Request(
		// CommonVars.getCurrUser()), currentUser.getLoginName());
		// user.setPassword(currentUser1.getPassword());
		// }
		// }

		user.setEmail(tfEmail.getText().trim());

		if (cbIsSuperUser.isSelected()) {

			user.setUserFlag("S");

		} else {

			user.setUserFlag(null);

		}

		user.setIsForbid(cbIsForbid.isSelected());

		user.setCompany((Company) cbCompany.getItemAt(cbCompany
				.getSelectedIndex()));
	}

	private void addUser() {
		AclUser user = new AclUser();
		fillUser(user);
		user = authorityAction.saveUser(new Request(CommonVars.getCurrUser()),
				user);
		JOptionPane.showMessageDialog(this, "已成功新增用户！", "提示!", 1);
		tableModel.addRow(user);
	}

	/**
	 * 判断新旧密码
	 * 
	 * @param user
	 * @author zcj
	 */
	private boolean checkPassword(AclUser user) {

		if (isAdd == true) {

			// 新密码
			char[] newPasswords = tfNewPassword.getPassword();

			// 首先判断密码是否为空
			if (newPasswords.length <= 0) {

				JOptionPane.showMessageDialog(this, "用户密码为空", "提示", 1);

				return true;

			}

			// 再次密码
			char[] repeatPasswords = tfPassword.getPassword();

			// 对比两个密码
			boolean doPwEqual = Arrays.equals(newPasswords, repeatPasswords);

			if (!doPwEqual) {

				JOptionPane.showMessageDialog(this, "您输入的新密码不一致，请重新输入！", "提示",
						1);

				return true;

			}

		}

		return false;

	}

	private void modifyUser() {
		fillUser(currentUser);
		currentUser = authorityAction.saveUser(
				new Request(CommonVars.getCurrUser()), currentUser);
		JOptionPane.showMessageDialog(this, "用户资料已经修改！", "提示!", 1);
		tableModel.updateRow(currentUser);
	}

	private void setReadOnly(boolean readOnly) {
		if (currentUser != null) {
			this.tfLoginName.setEditable(!readOnly);
			this.tfUserName.setEditable(!readOnly);
			this.tfEmail.setEditable(!readOnly);
			this.cbCompany.setEnabled(!readOnly);
			this.tfPassword.setEditable(!readOnly);
			this.cbIsSuperUser.setEnabled(!readOnly);
			this.cbIsForbid.setEnabled(!readOnly);
		}
	}

	private void fillWindow() {
		if (currentUser != null) {
			this.tfLoginName.setText(currentUser.getLoginName());
			this.tfUserName.setText(currentUser.getUserName());
			this.tfEmail.setText(currentUser.getEmail());
			if (currentUser.getCompany() != null)
				this.cbCompany.setSelectedItem(currentUser.getCompany());
			this.tfPassword.setText("");
			if (currentUser.getUserFlag() != null)
				this.cbIsSuperUser.setSelected(currentUser.getUserFlag()
						.equals("S"));
			this.cbIsForbid.setSelected(currentUser.getIsForbid() != null
					&& currentUser.getIsForbid());
		}
	}

	/**
	 * 
	 * This method initializes btnDone
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDone() {
		if (btnDone == null) {
			btnDone = new JButton();
			btnDone.setText("确定");
			btnDone.setBounds(91, 186, 84, 23);
			btnDone.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkNull()) {
						return;
					}
					if (checkMultiple()) {
						return;
					}
					if (checkPassword(currentUser)) {
						return;
					}
					if (isAdd == true) {
						addUser();
					} else {
						modifyUser();
					}
					DgUser.this.dispose();
				}
			});

		}
		return btnDone;
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

			btnCancel.setText("取消");

			btnCancel.setBounds(210, 187, 86, 23);

			btnCancel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgUser.this.dispose();

				}
			});

		}

		return btnCancel;
	}

	/**
	 * 
	 * This method initializes tfPassword
	 * 
	 * 
	 * 
	 * @return javax.swing.JPasswordField
	 * 
	 */
	private JPasswordField getTfPassword() {
		if (tfPassword == null) {
			tfPassword = new JPasswordField();
			tfPassword.setBounds(282, 155, 92, 21);
		}
		return tfPassword;
	}

	/**
	 * 
	 * This method initializes cbIsSuperUser
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbIsSuperUser() {
		if (cbIsSuperUser == null) {
			cbIsSuperUser = new JCheckBox();
			cbIsSuperUser.setText("是超级用户");
			cbIsSuperUser.setBounds(new Rectangle(207, 28, 85, 26));
			cbIsSuperUser
					.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
		}
		return cbIsSuperUser;
	}

	/**
	 * 
	 * This method initializes cbCompany
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbCompany() {
		if (cbCompany == null) {
			cbCompany = new JComboBox();
			cbCompany.setBounds(97, 123, 278, 20);
		}
		return cbCompany;
	}

	/**
	 * 当不是超级用户要修改用户信息时，下面的控件不可用
	 */
	public void canNotEditSomeWidget() {

		cbIsSuperUser.setEnabled(false);
		cbCompany.setEnabled(false);
		cbIsForbid.setEnabled(false);

	}

	/**
	 * 控件的状态控制
	 */
	private void setState() {

		if (isAdd == true) {
			cbIsSuperUser.setEnabled(true);
			cbCompany.setEnabled(true);
		} else if (isAdd == false) {
			if (CommonVars.getCurrUser().getUserFlag() == null) {
				cbIsSuperUser.setEnabled(false);
				cbCompany.setEnabled(false);
			} else
				cbCompany.setEnabled(false);
		}

	}

	/**
	 * This method initializes cbIsSuperUser1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsForbid() {
		if (cbIsForbid == null) {
			cbIsForbid = new JCheckBox();
			cbIsForbid.setText("是否禁用");
			cbIsForbid.setBounds(new Rectangle(207, 55, 73, 26));
			cbIsForbid.setHorizontalAlignment(SwingConstants.LEADING);
		}
		return cbIsForbid;
	}

	/**
	 * This method initializes cbbDigestType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDigestType() {
		if (cbbDigestType == null) {
			cbbDigestType = new JComboBox();
			cbbDigestType.setBounds(new Rectangle(284, 90, 91, 21));
			cbbDigestType.addItem("SHA-512");
			cbbDigestType.addItem("MD5");
			cbbDigestType.addItem("SHA-1");
			cbbDigestType.addItem("SHA-256");
			cbbDigestType.addItem("不加密");
			cbbDigestType.setSelectedItem("SHA-512");
		}
		return cbbDigestType;
	}

	/**
	 * 初始化 新密码
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getTfNewPassword() {

		if (tfNewPassword == null) {

			tfNewPassword = new JPasswordField();

			tfNewPassword.setBounds(new Rectangle(97, 156, 98, 21));

			tfNewPassword.getDocument().addDocumentListener(
					new DocumentListener() {

						private void setRepeatPasswordState() {

							char[] newPassword = tfNewPassword.getPassword();

							// 是否开启重复密码框可用
							tfPassword.setEnabled(!(newPassword.length <= 0));

						}

						public void insertUpdate(DocumentEvent e) {

							setRepeatPasswordState();

						}

						public void removeUpdate(DocumentEvent e) {

							setRepeatPasswordState();

						}

						public void changedUpdate(DocumentEvent e) {

							setRepeatPasswordState();

						}

					});
		}
		return tfNewPassword;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
