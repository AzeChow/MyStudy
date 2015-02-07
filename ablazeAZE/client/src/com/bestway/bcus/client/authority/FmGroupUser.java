/*
 * Created on 2004-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupUser;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmGroupUser extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JComboBox cbGroup = null;

	private JList lstUserSource = null;

	private JList lstUserDest = null;

	private JButton btnAdd = null;

	private JButton btnRemove = null;

	private List groups;

	private AuthorityAction authorityAction = null;

	private AclGroup currentGroup = null;

	private Vector vectorSource = new Vector();

	private Vector vectorDest = new Vector();

	private JScrollPane jScrollPane = null;

	private JScrollPane jScrollPane1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JLabel jLabel2 = null;

	/**
	 * This is the default constructor
	 */
	public FmGroupUser() {
		super();
		initialize();
		lstUserSource.setBorder(BorderFactory.createEtchedBorder());
		lstUserDest.setBorder(BorderFactory.createEtchedBorder());
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		this.lstUserDest.setCellRenderer(new UserListCellRenderer());
		this.lstUserSource.setCellRenderer(new UserListCellRenderer());

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("用户群组管理");
		this.setHelpId("groupUser");
		this.setSize(639, 514);
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameClosed(
							javax.swing.event.InternalFrameEvent e) {
						FmGroupUser.this.authorityAction.clearAclCache();
					}

					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {

						fillGroup(); // 群组到combobox
					}
				});

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {

			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());

			jContentPane.add(getJPanel0(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private void fillGroup() {
		groups = authorityAction.findGroups(new Request(CommonVars
				.getCurrUser()));
		cbGroup.removeAllItems();
		for (int i = 0; i < groups.size(); i++) {
			cbGroup.addItem(groups.get(i));
		}
		cbGroup.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				if (value != null) {
					setText(((AclGroup) value).getGroupName());
				} else {
					setText("");
				}
				setIcon(CommonVars.getRcpIconSource().getIcon(
						"misc.rep_editors_view.icon"));
				return this;
			}
		});
		if(cbGroup.getItemCount()>0){
			cbGroup.setSelectedIndex(0);
		}
	}

	void fillSelectedUserByGroup(AclGroup group) { // 已选择
		/*
		 * Iterator iterator = currentGroup.getAclUsers().iterator(); while
		 * (iterator.hasNext()) { AclUser user = (AclUser) iterator.next();
		 * vectorDest.add(user); }
		 */
		List list = authorityAction.findUserFromGroup(new Request(CommonVars
				.getCurrUser()), group);
		for (int i = 0; i < list.size(); i++) {
			AclGroupUser groupUser = (AclGroupUser) list.get(i);
			vectorDest.add(groupUser.getAclUser());
		}
		lstUserDest.setListData(vectorDest);

	}

	void fillUnSelectedUserByGroup(AclGroup group) { // 未选择
		/*
		 * List users = this.authorityAction.findUsers(new Request(CommonVars
		 * .getCurrUser())); for (int i = 0; i < users.size(); i++) { AclUser
		 * user = (AclUser) users.get(i); if
		 * (!group.getAclUsers().contains(user)) { vectorSource.add(user); } }
		 */
		List list = this.authorityAction.findNoSelectUser(new Request(
				CommonVars.getCurrUser()), group);
		for (int i = 0; i < list.size(); i++) {
			AclUser user = (AclUser) list.get(i);
			vectorSource.add(user);
		}
		this.lstUserSource.setListData(vectorSource);
	}

	void addUserToGroup() {
		if (this.lstUserSource.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "请选择要新增的用户!", "提示", 1);
			return;
		}
		currentGroup = authorityAction.findGroup(new Request(CommonVars
				.getCurrUser()), currentGroup.getGroupName());
		Object[] selectedUsers = this.lstUserSource.getSelectedValues();
		for (int i = 0; i < selectedUsers.length; i++) {
			AclUser user = (AclUser) selectedUsers[i];
			/*
			 * if (!currentGroup.getAclUsers().contains(user)) {
			 * currentGroup.getAclUsers().add(user); }
			 */
			AclGroupUser groupUser = new AclGroupUser();
			groupUser.setAclGroup(currentGroup);
			groupUser.setAclUser(user);
			groupUser.setCompany(CommonVars.getCurrUser().getCompany());
			authorityAction.saveGroupUser(
					new Request(CommonVars.getCurrUser()), groupUser);
			vectorDest.addElement(user);
			vectorSource.removeElement(user);
		}
		/*
		 * currentGroup = authorityAction.saveGroup(new Request(CommonVars
		 * .getCurrUser()), currentGroup);
		 */
		groups.set(cbGroup.getSelectedIndex(), currentGroup);

		this.lstUserSource.setListData(vectorSource);
		this.lstUserDest.setListData(vectorDest);
	}

	void removeUserFromGroup() {
		if (this.lstUserDest.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "请选择要删除的用户!", "提示", 1);
			return;
		}
		currentGroup = authorityAction.findGroup(new Request(CommonVars
				.getCurrUser()), currentGroup.getGroupName());
		Object[] removedUsers = this.lstUserDest.getSelectedValues();
		for (int i = 0; i < removedUsers.length; i++) {
			AclUser user = (AclUser) removedUsers[i];
			/*
			 * if (currentGroup.getAclUsers().contains(user)) {
			 * currentGroup.getAclUsers().remove(user); }
			 */
			authorityAction.deleteGroupUser(new Request(CommonVars
					.getCurrUser()), user,currentGroup);
			vectorSource.addElement(user);
			vectorDest.removeElement(user);
		}

		/*
		 * currentGroup = authorityAction.saveGroup(new Request(CommonVars
		 * .getCurrUser()), currentGroup);
		 */
		groups.set(cbGroup.getSelectedIndex(), currentGroup);

		this.lstUserSource.setListData(vectorSource);
		this.lstUserDest.setListData(vectorDest);

	}

	/**
	 * 
	 * This method initializes cbGroup
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbGroup() {
		if (cbGroup == null) {
			cbGroup = new JComboBox();
			cbGroup.setBounds(175, 16, 322, 27);
			cbGroup.setBackground(java.awt.Color.white);
			cbGroup.addItemListener(new java.awt.event.ItemListener() {

				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						currentGroup = (AclGroup) groups.get(cbGroup
								.getSelectedIndex());
						FmGroupUser.this.vectorDest.removeAllElements();
						FmGroupUser.this.vectorSource.removeAllElements();
						FmGroupUser.this.lstUserDest
								.setListData(FmGroupUser.this.vectorDest);
						FmGroupUser.this.lstUserSource
								.setListData(FmGroupUser.this.vectorSource);
						fillUnSelectedUserByGroup(currentGroup);
						fillSelectedUserByGroup(currentGroup);
					}
				}
			});

		}
		return cbGroup;
	}

	/**
	 * 
	 * This method initializes lstUserSource
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getLstUserSource() {
		if (lstUserSource == null) {
			lstUserSource = new JList();
			lstUserSource.setBounds(19, 78, 230, 320);
			lstUserSource.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						addUserToGroup();
					}
				}
			});

		}
		return lstUserSource;
	}

	/**
	 * 
	 * This method initializes lstUserDest
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getLstUserDest() {
		if (lstUserDest == null) {
			lstUserDest = new JList();
			lstUserDest.setBounds(337, 78, 230, 320);
			lstUserDest.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						removeUserFromGroup();
					}
				}
			});

		}
		return lstUserDest;
	}

	/**
	 * 
	 * This method initializes btnAdd
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			// btnAdd.setIcon(CommonVars.getIconSource().getIcon(
			// "selectOne.icon"));
			btnAdd.setText(">");
			btnAdd
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							14));
			btnAdd.setBounds(262, 158, 63, 24);
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					addUserToGroup();
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes btnRemove
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton();
			// btnRemove.setIcon(CommonVars.getIconSource().getIcon(
			// "removeOne.icon"));
			btnRemove.setText("<");
			btnRemove.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			btnRemove.setBounds(262, 239, 63, 24);
			btnRemove.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					removeUserFromGroup();
				}
			});

		}
		return btnRemove;
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((AclUser) value).getUserName();
				setIcon(CommonVars.getRcpIconSource().getIcon(
						"bullets.brkp_obj.icon"));
			}
			setText(s);
			return this;
		}
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getLstUserSource());
			jScrollPane.setBounds(19, 78, 230, 320);
			jScrollPane
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jScrollPane.setBackground(java.awt.Color.white);
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getLstUserDest());
			jScrollPane1.setBounds(337, 78, 230, 320);
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jScrollPane1.setBackground(java.awt.Color.white);
		}
		return jScrollPane1;
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
			// jButton.setIcon(CommonVars.getIconSource().getIcon(
			// "selectAll.icon"));
			jButton.setText(">>");
			jButton
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							14));
			jButton.setBounds(262, 199, 63, 24);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					int count = FmGroupUser.this.lstUserSource.getModel()
							.getSize();
					int[] indices = new int[count];
					for (int i = 0; i < count; i++) {
						indices[i] = i;
					}
					FmGroupUser.this.lstUserSource.setSelectedIndices(indices);
					addUserToGroup();
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
			// jButton1.setIcon(CommonVars.getIconSource().getIcon(
			// "removeAll.icon"));
			jButton1.setText("<<");
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
					14));
			jButton1.setBounds(262, 279, 63, 24);
			jButton1.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					int count = FmGroupUser.this.lstUserDest.getModel()
							.getSize();
					int[] indices = new int[count];
					for (int i = 0; i < count; i++) {
						indices[i] = i;
					}
					FmGroupUser.this.lstUserDest.setSelectedIndices(indices);
					removeUserFromGroup();
				}
			});

		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jPanel = new JPanel();
			jLabel = new JLabel();
			jPanel.setLayout(null);
			jLabel.setText("系统中已经建立的群组");
			// jLabel.setForeground(new java.awt.Color(227,145,0));
			jLabel.setBounds(19, 16, 148, 27);
			jLabel
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			jLabel6.setBounds(338, 52, 98, 21);
			// jLabel6.setForeground(new java.awt.Color(227,145,0));
			jLabel6.setText("已被选择的用户");
			jLabel6
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			jLabel7.setBounds(19, 52, 98, 21);
			jLabel7.setText("未被选择的用户");
			jLabel7
					.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
							12));
			// jLabel7.setForeground(new java.awt.Color(227,145,0));
			jPanel.setBackground(new java.awt.Color(238, 238, 238));
			jPanel.add(getCbGroup(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getBtnAdd(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnRemove(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJScrollPane1(), null);
			jPanel.add(getBtnRefresh(), null);
		}
		return jPanel;
	}

	private JPanel jPanel0 = null; // @jve:decl-index=0:visual-constraint="97,69"

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnRefresh = null;

	private JPanel getJPanel0() {
		if (jPanel0 == null) {
			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jPanel0 = new JPanel();
			jPanel0.setLayout(new BorderLayout());
			jLabel2.setText(" 用户群组管理");
			jLabel2.setForeground(new java.awt.Color(227, 145, 0));
			jLabel2
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel3.setText("");
			jLabel3.setIcon(CommonVars.getIconSource().getIcon("titlepic.jpg"));
			// jLabel3.setIcon(new
			// ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel4.setText("");
			jLabel4.setIcon(CommonVars.getIconSource()
					.getIcon("titlepoint.jpg"));
			// jLabel4.setIcon(new
			// ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel0.setBackground(java.awt.Color.white);
			jPanel0
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel0.add(jLabel2, java.awt.BorderLayout.CENTER);
			jPanel0.add(jLabel3, java.awt.BorderLayout.EAST);
			jPanel0.add(jLabel4, java.awt.BorderLayout.WEST);
		}
		return jPanel0;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJPanel());
			jScrollPane2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setBounds(new java.awt.Rectangle(497,16,69,27));
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AclGroup group=(AclGroup)cbGroup.getSelectedItem();
					fillGroup();
					if(group!=null){
						cbGroup.setSelectedItem(group);
					}
				}
			});
		}
		return btnRefresh;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
