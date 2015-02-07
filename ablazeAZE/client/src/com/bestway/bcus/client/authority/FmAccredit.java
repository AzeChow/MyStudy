/*
 * Created on 2004-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.authority;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.authority.AuthorityFunctionAnnotation;
import com.bestway.common.authority.acl.CustomPermission;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclGroup;
import com.bestway.common.authority.entity.AclGroupPermission;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.authority.entity.AclUserPermission;
import com.bestway.common.authority.entity.AuthorityModule;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.JTristateCheckBox;
import com.bestway.common.authority.acl.CustomPermission;

/**
 * @author 001
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmAccredit extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JSplitPane jSplitPane = null;

	private JToolBar jToolBar = null;

	private JButton btnExpandTree = null;

	private JButton btnCollapseTree = null;

	private JButton jButton2 = null;

	private Vector moduleVector = new Vector();

	private JScrollPane jScrollPane = null;

	private JTree jTree = null;

	private AuthorityAction authorityAction = null;

	private AuthorityModule[] authorityModules = null;

	private JList lstIPUser = null;

	private JList lstGroup = null;

	private Object authorityOwner = null; // @jve:decl-index=0:

	private JButton btnRefresh = null;

	private Map<String, Enumeration> map = new HashMap<String, Enumeration>(); // @jve:decl-index=0:

	private List lsAddPermission = new ArrayList(); // @jve:decl-index=0:

	private List lsDelPermission = new ArrayList(); // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;
	public static List permissions;

	/**
	 * This is the default constructor
	 */
	public FmAccredit() {
		super();
		initialize();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		authorityModules = authorityAction.getModules();
		setTreeModel();
		fillUser();
		fillGroup();
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("授权管理");
		this.setHelpId("accredit");
		this.setSize(496, 321);
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

					public void internalFrameClosed(
							javax.swing.event.InternalFrameEvent e) {
						// FmAccredit.this.authorityAction.clearAclCache();
					}

					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {

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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("用户", null, getJPanel(), null);
			jTabbedPane.addTab("群组", null, getJPanel1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {

							if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 0) {
								if (lstIPUser.getModel().getSize() > 0
										&& lstIPUser.getSelectedIndex() < 0) {
									lstIPUser.setSelectedIndex(0);
								} else {
									if (lstIPUser.getSelectedValue() == null) {
										FmAccredit.this.setAuthorityOwner(null);
										uncheckAllTreeNode();
									} else {
										FmAccredit.this
												.setAuthorityOwner((AclUser) lstIPUser
														.getSelectedValue());

										SwingUtilities
												.invokeLater(showUserPermissionRunnable);
									}
								}
							} else if (((JTabbedPane) e.getSource())
									.getSelectedIndex() == 1) {
								if (lstGroup.getModel().getSize() > 0
										&& lstGroup.getSelectedIndex() < 0) {
									lstGroup.setSelectedIndex(0);
								} else {
									if (lstGroup.getSelectedValue() == null) {
										FmAccredit.this.setAuthorityOwner(null);
										uncheckAllTreeNode();
									} else {
										FmAccredit.this
												.setAuthorityOwner((AclGroup) lstGroup
														.getSelectedValue());

										SwingUtilities
												.invokeLater(showGroupPermissionRunnable);
									}
								}
							}
							setState();
						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJTabbedPane());
			jSplitPane.setDividerSize(5);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setRightComponent(getJScrollPane());
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnExpandTree());
			jToolBar.add(getBtnCollapseTree());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
			jToolBar.add(getCbShowUserAllPermission());
		}
		return jToolBar;
	}

	/**
	 * @return Returns the authorityOwner.
	 */
	public Object getAuthorityOwner() {
		return authorityOwner;
	}

	/**
	 * @param authorityOwner
	 *            The authorityOwner to set.
	 */
	public void setAuthorityOwner(Object authorityOwner) {
		this.authorityOwner = authorityOwner;
	}

	// 展开树
	private void expandTreeNode(JTree aTree, DefaultMutableTreeNode aNode) {
		if (aNode.isLeaf()) {
			return;
		}
		aTree.expandPath(new TreePath(((DefaultMutableTreeNode) aNode)
				.getPath()));
		int n = aNode.getChildCount();
		for (int i = 0; i < n; i++) {
			expandTreeNode(aTree, (DefaultMutableTreeNode) aNode.getChildAt(i));
		}
	}

	private JButton getBtnExpandTree() {
		if (btnExpandTree == null) {
			btnExpandTree = new JButton();
			btnExpandTree.setText("展开树");
			btnExpandTree
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							expandTreeNode(jTree, (CheckNode) jTree.getModel()
									.getRoot());

						}
					});

		}
		return btnExpandTree;
	}

	// 收缩树
	private void collapseTreeNode(JTree aTree, DefaultMutableTreeNode aNode) {
		if (aNode.isLeaf()) {
			return;
		}
		aTree.collapsePath(new TreePath(((DefaultMutableTreeNode) aNode)
				.getPath()));
		int n = aNode.getChildCount();
		for (int i = 0; i < n; i++) {
			collapseTreeNode(aTree, (DefaultMutableTreeNode) aNode
					.getChildAt(i));
		}
	}

	private JButton getBtnCollapseTree() {
		if (btnCollapseTree == null) {
			btnCollapseTree = new JButton();
			btnCollapseTree.setText("收缩树");
			btnCollapseTree
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {
							collapseTreeNode(jTree, (CheckNode) jTree
									.getModel().getRoot());
						}
					});

		}
		return btnCollapseTree;
	}

	/**
	 * @return Returns the moduleVector.
	 */
	private Vector getModuleVector() {
		return moduleVector;
	}

	/**
	 * @param moduleVector
	 *            The moduleVector to set.
	 */
	private void setModuleVector(Vector moduleVector) {
		this.moduleVector = moduleVector;
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
			jScrollPane.setViewportView(getJTree());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTree
	 * 
	 * 
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();
		}
		return jTree;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void setTreeModel() {
		CheckNode rootNode = new CheckNode("全部权限");
		for (int i = 0; i < authorityModules.length; i++) {
			CheckNode node = null;
			boolean flag = true;
			if (rootNode.getChildCount() > 0) {
				for (int l = 0; l < rootNode.getChildCount(); l++) {
					CheckNode cnode = (CheckNode) rootNode.getChildAt(l);
					if (((AuthorityModule) cnode.getUserObject()).getCaption()
							.equalsIgnoreCase(authorityModules[i].getCaption())) {

						node = (CheckNode) rootNode.getChildAt(l);
						flag = false;
						break;
					} else {
						node = new CheckNode(authorityModules[i]);
					}
				}
			}
			if (rootNode.getChildCount() == 0) {
				node = new CheckNode(authorityModules[i]);
			}

			if (authorityModules[i].getPermissions() != null) {
				for (int j = 0; j < authorityModules[i].getPermissions().size(); j++) {
					CheckNode childNode = new CheckNode(authorityModules[i]
							.getPermissions().get(j));

					node.add(childNode);
				}
			}
			/**
			 * @author zyy 树节点序列化
			 */
			HashSet hsModules = new HashSet();
			if (node != null) {
				for (int m = 0; m < node.getChildCount(); m++) {
					CustomPermission childNode = (CustomPermission) ((CheckNode) node
							.getChildAt(m)).getUserObject();
					hsModules.add(childNode);
				}
			}
			List ls = new ArrayList();
			ls.addAll(hsModules);
			Collections.sort(ls);
			node.removeAllChildren();
			for (int n = 0; n < ls.size(); n++) {
				CheckNode childNode = new CheckNode(ls.get(n));
				node.add(childNode);
			}

			if (flag)
				rootNode.add(node);
		}
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		jTree.setModel(treeModel);
		jTree.setCellRenderer(new CheckRenderer());
		if (!checkMouseListener()) {
			jTree.addMouseListener(new NodeSelectionListener(jTree));
		}
	}

	private boolean checkMouseListener() {
		MouseListener[] listeners = jTree.getMouseListeners();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] instanceof NodeSelectionListener) {
				return true;
			}
		}
		return false;
	}

	void fillUser() {
		Iterator iterator = authorityAction.findUsers(
				new Request(CommonVars.getCurrUser())).iterator();
		Vector vector = new Vector();
		while (iterator.hasNext()) {
			AclUser user = (AclUser) iterator.next();
			if (user.getUserFlag() == null || !user.getUserFlag().equals("S")) {
				vector.add(user);
			}
		}

		lstIPUser.setListData(vector);
		if (jTabbedPane.getSelectedIndex() == 0
				&& lstIPUser.getModel().getSize() > 0) {
			lstIPUser.setSelectedIndex(0);
		}
	}

	void fillGroup() {
		Iterator iterator = authorityAction.findGroups(
				new Request(CommonVars.getCurrUser())).iterator();
		Vector vector = new Vector();
		while (iterator.hasNext()) {
			AclGroup group = (AclGroup) iterator.next();
			vector.add(group);
		}
		lstGroup.setListData(vector);
		if (jTabbedPane.getSelectedIndex() == 1
				&& lstGroup.getModel().getSize() > 0) {
			lstGroup.setSelectedIndex(0);
		}
	}

	/**
	 * 
	 * This method initializes lstUser
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getLstUser() {
		if (lstIPUser == null) {
			lstIPUser = new JList();
			lstIPUser.setCellRenderer(new ListCellRenderer());
			lstIPUser
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true) {
								return;
							}
							FmAccredit.this.authorityOwner = ((JList) e
									.getSource()).getSelectedValue();
							// showUserPermission();

							if (cbShowUserAllPermission.isSelected()) {
								SwingUtilities
										.invokeLater(showUserAllPermissionRunnable);
							} else {
								SwingUtilities
										.invokeLater(showUserPermissionRunnable);
							}
						}
					});

		}
		return lstIPUser;
	}

	private Runnable showUserPermissionRunnable = new Runnable() {
		public void run() {
			FmAccredit.this.setCursor(new Cursor(Cursor.WAIT_CURSOR)); // @jve:decl-index=0:
			try {
				showUserPermission(); // @jve:decl-index=0:
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(FmAccredit.this, ex.getMessage(),
						"提示", 0);
				FmAccredit.this.doDefaultCloseAction();
			} finally {
				FmAccredit.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	private void showUserPermission() {
		uncheckAllTreeNode();
		AclUser user = (AclUser) lstIPUser.getSelectedValue();
		List list = authorityAction.findUserPermission(new Request(CommonVars
				.getCurrUser()), user.getId(), null);
		for (int i = 0; i < list.size(); i++) {
			AclUserPermission permission = (AclUserPermission) list.get(i);
			Enumeration enumPermission = getPermissionNodes(permission
					.getModuleName());
			if (enumPermission == null) {
				continue;
			}
			CheckNode node = null;
			while (enumPermission.hasMoreElements()) {
				node = (CheckNode) enumPermission.nextElement();
				CustomPermission customPermission = (CustomPermission) node
						.getUserObject();
				if (customPermission == null) {
					continue;
				}
				if (customPermission.toString().toLowerCase().equals(
						permission.getPermission().toLowerCase())) {
					node.setState(JTristateCheckBox.SELECTED);
					node.setAclPermission(permission);
					((DefaultTreeModel) jTree.getModel()).nodeChanged(node);
					break;
				}
			}
			if (node != null) {
				setParentNodeState(node);
			}
		}
	}

	private Runnable showUserAllPermissionRunnable = new Runnable() {
		public void run() {
			FmAccredit.this.setCursor(new Cursor(Cursor.WAIT_CURSOR)); // @jve:decl-index=0:
			showUserAllPermission(); // @jve:decl-index=0:
			FmAccredit.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	};

	private void showUserAllPermission() {
		uncheckAllTreeNode();
		AclUser user = (AclUser) lstIPUser.getSelectedValue();
		List list = authorityAction.findUserAllPermission(new Request(
				CommonVars.getCurrUser()), user);
		for (int i = 0; i < list.size(); i++) {
			Object permission = null;
			String moduleName = "";
			String permissionName = "";
			if (list.get(i) instanceof AclUserPermission) {
				permission = (AclUserPermission) list.get(i);
				moduleName = ((AclUserPermission) list.get(i)).getModuleName();
				permissionName = ((AclUserPermission) list.get(i))
						.getPermission();
			} else if (list.get(i) instanceof AclGroupPermission) {
				permission = (AclGroupPermission) list.get(i);
				moduleName = ((AclGroupPermission) list.get(i)).getModuleName();
				permissionName = ((AclGroupPermission) list.get(i))
						.getPermission();
			} else {
				continue;
			}
			Enumeration enumPermission = getPermissionNodes(moduleName);
			if (enumPermission == null) {
				continue;
			}
			CheckNode node = null;
			while (enumPermission.hasMoreElements()) {
				node = (CheckNode) enumPermission.nextElement();
				CustomPermission customPermission = (CustomPermission) node
						.getUserObject();
				if (customPermission == null) {
					continue;
				}
				if (customPermission.toString().toLowerCase().equals(
						permissionName.toLowerCase())) {
					node.setState(JTristateCheckBox.SELECTED);
					node.setAclPermission(permission);
					((DefaultTreeModel) jTree.getModel()).nodeChanged(node);
					break;
				}
			}
			if (node != null) {
				setParentNodeState(node);
			}
		}
	}

	private Enumeration getPermissionNodes(String moduleName) {
		CheckNode rootNode = (CheckNode) jTree.getModel().getRoot();
		for (int i = 0; i < rootNode.getChildCount(); i++) {
			CheckNode node = (CheckNode) rootNode.getChildAt(i);
			for (int l = 0; l < node.getChildCount(); l++) {
				CheckNode cnode = (CheckNode) node.getChildAt(l);
				CustomPermission permission = ((CustomPermission) cnode
						.getUserObject());
				if (permission.getName().toLowerCase().equals(
						moduleName.toLowerCase())) {
					map.put(moduleName.toLowerCase(), node.children());
					return node.children();
				}
			}

		}
		return null;
	}

	private Runnable showGroupPermissionRunnable = new Runnable() {
		public void run() {
			FmAccredit.this.setCursor(new Cursor(Cursor.WAIT_CURSOR)); // @jve:decl-index=0:
			showGroupPermission(); // @jve:decl-index=0:
			FmAccredit.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	};

	private JButton btnEdit = null;

	private JButton btnUndo = null;

	private JButton btnSave = null;

	private JButton btnClose = null;

	private JCheckBox cbShowUserAllPermission = null;

	private JScrollPane jScrollPane1 = null;

	private JScrollPane jScrollPane2 = null;

	private void showGroupPermission() {
		uncheckAllTreeNode();
		AclGroup group = (AclGroup) lstGroup.getSelectedValue();
		List list = authorityAction.findGroupPermission(new Request(CommonVars
				.getCurrUser()), group.getId(), null);
		for (int i = 0; i < list.size(); i++) {
			AclGroupPermission permission = (AclGroupPermission) list.get(i);
			Enumeration enumPermission = getPermissionNodes(permission
					.getModuleName());
			if (enumPermission == null) {
				continue;
			}
			CheckNode node = null;
			while (enumPermission.hasMoreElements()) {
				node = (CheckNode) enumPermission.nextElement();
				CustomPermission customPermission = (CustomPermission) node
						.getUserObject();
				if (customPermission == null) {
					continue;
				}
				if (customPermission.toString().toLowerCase().equals(
						permission.getPermission().toLowerCase())) {
					node.setState(JTristateCheckBox.SELECTED);
					node.setAclPermission(permission);
					((DefaultTreeModel) jTree.getModel()).nodeChanged(node);
					break;
				}
			}
			if (node != null) {
				setParentNodeState(node);
			}
		}
	}

	private void uncheckAllTreeNode() {
		CheckNode rootNode = (CheckNode) jTree.getModel().getRoot();
		setTreeNodeUnChecked(rootNode);
	}

	private void setTreeNodeUnChecked(CheckNode node) {
		node.setState(JTristateCheckBox.NOT_SELECTED);
		node.setAclPermission(null);
		((DefaultTreeModel) jTree.getModel()).nodeChanged(node);
		for (int i = 0; i < node.getChildCount(); i++) {
			setTreeNodeUnChecked((CheckNode) node.getChildAt(i));
		}
	}

	/**
	 * 
	 * This method initializes lstGroup
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getLstGroup() {
		if (lstGroup == null) {
			lstGroup = new JList();
			lstGroup.setCellRenderer(new ListCellRenderer());
			lstGroup
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true) {
								return;
							}
							FmAccredit.this.authorityOwner = ((JList) e
									.getSource()).getSelectedValue();
							// showGroupPermission();
							SwingUtilities
									.invokeLater(showGroupPermissionRunnable);
						}
					});
		}
		return lstGroup;
	}

	private void setParentNodeState(CheckNode node) {
		if (node == null) {
			return;
		}
		if (node.equals(jTree.getModel().getRoot())) {
			return;
		}
		CheckNode parentNode = (CheckNode) node.getParent();
		int iSelectCount = 0;
		int iDontCareCount = 0;
		for (int i = 0; i < parentNode.getChildCount(); i++) {
			if (((CheckNode) parentNode.getChildAt(i)).getState() == JTristateCheckBox.SELECTED) {
				iSelectCount++;
			} else if (((CheckNode) parentNode.getChildAt(i)).getState() == JTristateCheckBox.DONT_CARE) {
				iDontCareCount++;
			}
		}
		if (iSelectCount > 0 && iSelectCount == parentNode.getChildCount()) {
			parentNode.setSelected(true);
			parentNode.setState(JTristateCheckBox.SELECTED);
		} else if (iSelectCount > 0
				&& iSelectCount < parentNode.getChildCount()
				|| iDontCareCount > 0) {
			parentNode.setSelected(true);
			parentNode.setState(JTristateCheckBox.DONT_CARE);
		} else if (iSelectCount == 0 && iDontCareCount == 0) {
			parentNode.setState(JTristateCheckBox.NOT_SELECTED);
		}
		((DefaultTreeModel) jTree.getModel()).nodeChanged(parentNode);
		setParentNodeState(parentNode);
	}

	class CheckNode extends DefaultMutableTreeNode {

		public final static int SINGLE_SELECTION = 0;

		public final static int DIG_IN_SELECTION = 4;

		protected int selectionMode;

		protected boolean isSelected;

		private JTristateCheckBox.State state = JTristateCheckBox.NOT_SELECTED;

		private Object aclPermission = null;

		private String id;

		public CheckNode() {
			this(null);
		}

		public CheckNode(Object userObject) {
			this(userObject, true, false);
		}

		public CheckNode(Object userObject, boolean allowsChildren,
				boolean isSelected) {
			super(userObject, allowsChildren);
			this.isSelected = isSelected;
			setSelectionMode(DIG_IN_SELECTION);
			id = UUID.randomUUID().toString();
		}

		public void setSelectionMode(int mode) {
			selectionMode = mode;
		}

		public int getSelectionMode() {
			return selectionMode;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;

			if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
				Enumeration enums = children.elements();
				while (enums.hasMoreElements()) {
					CheckNode node = (CheckNode) enums.nextElement();
					node.setSelected(isSelected);
				}
			}
		}

		public boolean isSelected() {
			return isSelected;
		}

		// If you want to change "isSelected" by CellEditor,
		/*
		 * public void setUserObject(Object obj) { if (obj instanceof Boolean) {
		 * setSelected(((Boolean)obj).booleanValue()); } else {
		 * super.setUserObject(obj); } }
		 */

		/**
		 * @return Returns the aclPermission.
		 */
		public Object getAclPermission() {
			return aclPermission;
		}

		/**
		 * @param aclPermission
		 *            The aclPermission to set.
		 */
		public void setAclPermission(Object aclPermission) {
			this.aclPermission = aclPermission;
		}

		/**
		 * @return Returns the state.
		 */
		public JTristateCheckBox.State getState() {
			return state;
		}

		/**
		 * @param state
		 *            The state to set.
		 */
		public void setState(JTristateCheckBox.State state) {
			this.state = state;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean equals(Object other) {
			if (other == null)
				return false;
			if (!(other.getClass().equals(this.getClass())))
				return false;
			CheckNode castOther = (CheckNode) other;
			if (this.getId() == null && castOther.getId() == null) {
				return super.equals(other);
			} else {
				return new EqualsBuilder().append(this.getId(),
						castOther.getId()).isEquals();
			}
		}
	}

	class CheckRenderer extends JPanel implements TreeCellRenderer {
		protected JTristateCheckBox check;

		protected TreeLabel label;

		/**
		 * @return Returns the check.
		 */
		public JCheckBox getCheck() {
			return check;
		}

		/**
		 * @return Returns the label.
		 */
		public TreeLabel getLabel() {
			return label;
		}

		public CheckRenderer() {
			setLayout(null);
			add(check = new JTristateCheckBox());
			add(label = new TreeLabel());
			check.setBackground(UIManager.getColor("Tree.textBackground"));
			label.setForeground(UIManager.getColor("Tree.textForeground"));
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean isSelected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			// String stringValue = tree.convertValueToText(value, isSelected,
			// expanded, leaf, row, hasFocus);
			// setEnabled(tree.isEnabled());
			setEnabled(dataState != DataState.BROWSE);
			CheckNode node = (CheckNode) value;

			check.setState(node.getState());
			// check.setEnabled(tree.isEnabled());
			// check.setEnabled(dataState != DataState.BROWSE);

			label.setFont(tree.getFont());

			if (node.getUserObject() instanceof AuthorityModule) {
				label.setText(((AuthorityModule) node.getUserObject())
						.getCaption());
			} else if (node.getUserObject() instanceof CustomPermission) {
				label.setText(((CustomPermission) node.getUserObject())
						.toString());
			} else {
				label.setText(node.getUserObject().toString());
			}
			label.setSelected(isSelected);
			label.setFocus(hasFocus);
			if (leaf) {
				label.setIcon(UIManager.getIcon("Tree.leafIcon"));
			} else if (expanded) {
				label.setIcon(UIManager.getIcon("Tree.openIcon"));
			} else {
				label.setIcon(UIManager.getIcon("Tree.closedIcon"));
			}
			return this;
		}

		public Dimension getPreferredSize() {
			Dimension d_check = check.getPreferredSize();
			Dimension d_label = label.getPreferredSize();
			return new Dimension(d_check.width + d_label.width,
					(d_check.height < d_label.height ? d_label.height
							: d_check.height));
		}

		public void doLayout() {
			Dimension d_check = check.getPreferredSize();
			Dimension d_label = label.getPreferredSize();
			int y_check = 0;
			int y_label = 0;
			if (d_check.height < d_label.height) {
				y_check = (d_label.height - d_check.height) / 2;
			} else {
				y_label = (d_check.height - d_label.height) / 2;
			}
			check.setLocation(0, y_check);
			check.setBounds(0, y_check, d_check.width, d_check.height);
			label.setLocation(d_check.width, y_label);
			label.setBounds(d_check.width, y_label, d_label.width,
					d_label.height);
		}

		public void setBackground(Color color) {
			if (color instanceof ColorUIResource)
				color = null;
			super.setBackground(color);
		}

		public class TreeLabel extends JLabel {
			boolean isSelected;

			boolean hasFocus;

			public TreeLabel() {
			}

			public void setBackground(Color color) {
				if (color instanceof ColorUIResource)
					color = null;
				super.setBackground(color);
			}

			public void paint(Graphics g) {
				String str;
				if ((str = getText()) != null) {
					if (0 < str.length()) {
						if (isSelected) {
							g.setColor(UIManager
									.getColor("Tree.selectionBackground"));
						} else {
							g.setColor(UIManager
									.getColor("Tree.textBackground"));
						}
						Dimension d = getPreferredSize();
						int imageOffset = 0;
						Icon currentI = getIcon();
						if (currentI != null) {
							imageOffset = currentI.getIconWidth()
									+ Math.max(0, getIconTextGap() - 1);
						}
						g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
								d.height);
						if (hasFocus) {
							g.setColor(UIManager
									.getColor("Tree.selectionBorderColor"));
							g.drawRect(imageOffset, 0, d.width - 1
									- imageOffset, d.height - 1);
						}
					}
				}
				super.paint(g);
			}

			public Dimension getPreferredSize() {
				Dimension retDimension = super.getPreferredSize();
				if (retDimension != null) {
					retDimension = new Dimension(retDimension.width + 3,
							retDimension.height);
				}
				return retDimension;
			}

			public void setSelected(boolean isSelected) {
				this.isSelected = isSelected;
			}

			public void setFocus(boolean hasFocus) {
				this.hasFocus = hasFocus;
			}
		}
	}

	class NodeSelectionListener extends MouseAdapter {
		JTree tree;

		NodeSelectionListener(JTree tree) {
			this.tree = tree;
		}

		public void mouseClicked(MouseEvent e) {
			if (dataState == DataState.BROWSE) {
				return;
			}
			int x = e.getX();
			int y = e.getY();
			int row = tree.getRowForLocation(x, y);

			TreePath path = tree.getPathForRow(row);
			Rectangle pathBounds = tree.getPathBounds(path);
			if (path != null) {
				CheckNode node = (CheckNode) path.getLastPathComponent();
				CheckRenderer renderer = (CheckRenderer) tree.getCellRenderer()
						.getTreeCellRendererComponent(tree, node, true, true,
								true, 0, true);
				Point point = renderer.getCheck().getParent().getLocation();
				int checkLen = renderer.getCheck().getWidth();
				if (x > pathBounds.x + checkLen) {
					return;
				}
				boolean isSelected = false;
				if (node.getState() == JTristateCheckBox.SELECTED
						|| node.getState() == JTristateCheckBox.DONT_CARE) {
					isSelected = false;
					System.out.println("===============");
				} else {
					isSelected = true;
				}
				if (FmAccredit.this.getAuthorityOwner() == null) {
					if (FmAccredit.this.getJTabbedPane().getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(FmAccredit.this,
								"请先选择要修改的用户！", "提示", 0);
						return;
					} else if (FmAccredit.this.getJTabbedPane()
							.getSelectedIndex() == 1) {
						JOptionPane.showMessageDialog(FmAccredit.this,
								"请先选择要修改的群组！", "提示", 0);
						return;
					}
				}
				Object obj = node.getUserObject();
				if (node.equals(jTree.getModel().getRoot())) {
					for (int i = 0; i < node.getChildCount(); i++) {
						CheckNode moduleNode = (CheckNode) node.getChildAt(i);
						CheckNode permissionNode = null;

						for (int j = 0; j < moduleNode.getChildCount(); j++) {
							permissionNode = (CheckNode) moduleNode
									.getChildAt(j);
							changeChildAclPermission(permissionNode, isSelected);
							setNodeState(permissionNode, isSelected);
							((DefaultTreeModel) tree.getModel())
									.nodeChanged(permissionNode);
						}
						setParentNodeState(permissionNode);
					}
				} else if (obj instanceof AuthorityModule) {
					for (int i = 0; i < node.getChildCount(); i++) {
						CheckNode permissionNode = (CheckNode) node
								.getChildAt(i);
						changeChildAclPermission(permissionNode, isSelected);
						setNodeState(permissionNode, isSelected);
						((DefaultTreeModel) tree.getModel())
								.nodeChanged(permissionNode);
					}
				} else if (obj instanceof CustomPermission) {
					changeChildAclPermission(node, isSelected);
					setNodeState(node, isSelected);
					((DefaultTreeModel) tree.getModel()).nodeChanged(node);
					changeAssociatedNode(node,isSelected);
				}
				setNodeState(node, isSelected);
				setParentNodeState(node);
				((DefaultTreeModel) tree.getModel()).nodeChanged(node);
				
				// I need revalidate if node is root. but why?
				if (row == 0) {
					tree.revalidate();
					tree.repaint();
				}
			}
		}
		
		/**
		 * 更改相关联的节点的权限
		 * @param node
		 */
        private void changeAssociatedNode(CheckNode node,boolean isSelected){
        	//1.【保存-报关单商品信息】时，表头【保存-报关单】，自动勾选
        	Object obj = node.getUserObject();
        	if (obj instanceof CustomPermission) {
        		CustomPermission tempObj = (CustomPermission)obj;
            	String name =tempObj.getName();
            	String permission = tempObj.getPermission();
                CheckNode changeNode =null;
            	//电子化手册
                //电子化手册--@AuthorityFunctionAnnotation(caption = "进口报关单--保存报关单商品信息", index = 9.96)
            	if ("com.bestway.customs.common.action.BCSImpCustomsAuthorityActionImpl"
						.equals(name)
						&& "进口报关单--保存报关单商品信息".equals(permission)) {
            		//@AuthorityFunctionAnnotation(caption = "进口报关单--保存报关单", index = 9.2)
					changeNode = getPermissionNode(
							"com.bestway.customs.common.action.BCSImpCustomsAuthorityActionImpl",
							"进口报关单--保存报关单");
				////出口报关单--保存报关单商品信息,ndex = 10.96
				} else if ("com.bestway.customs.common.action.BCSExpCustomsAuthorityActionImpl"
						.equals(name)
						&& "出口报关单--保存报关单商品信息".equals(permission)) {
					changeNode = getPermissionNode(
							"com.bestway.customs.common.action.BCSExpCustomsAuthorityActionImpl",
							"出口报关单--保存报关单");
				}
            	//电子账册
            	//电子账册caption = "电子帐册通关--进口报关单-保存报关单商品信息", index = 3.3)
            	if ("com.bestway.customs.common.action.ImpCustomsAuthorityActionImpl"
						.equals(name)
						&& "电子帐册通关--进口报关单-保存报关单商品信息".equals(permission)) {
					changeNode = getPermissionNode(
							"com.bestway.customs.common.action.ImpCustomsAuthorityActionImpl",
							"电子帐册通关--进口报关单-保存报关单");
					
				//(caption = "电子帐册通关--出口报关单-保存报关单商品信息", index = 3.4)
				} else if ("com.bestway.customs.common.action.ExpCustomsAuthorityActionImpl"
						.equals(name)
						&& "电子帐册通关--出口报关单-保存报关单商品信息".equals(permission)) {
					changeNode = getPermissionNode(
							"com.bestway.customs.common.action.ExpCustomsAuthorityActionImpl",
							"电子帐册通关--出口报关单-保存报关单");
				}
            	//电子手册
            	//(caption = "进口报关单--保存报关单商品信息", index = 8.1)
            	if ("com.bestway.customs.common.action.DZSCImpCustomsAuthorityActionImpl"
						.equals(name)
						&& "进口报关单--保存报关单商品信息".equals(permission)) {
					changeNode = getPermissionNode(
							"com.bestway.customs.common.action.DZSCImpCustomsAuthorityActionImpl",
							"进口报关单--保存报关单");
					
				//(caption = "出口报关单--保存报关单商品信息", index = 8.2)
				} else if ("com.bestway.customs.common.action.DZSCExpCustomsAuthorityActionImpl"
						.equals(name)
						&& "出口报关单--保存报关单商品信息".equals(permission)) {
					changeNode = getPermissionNode(
							"com.bestway.customs.common.action.DZSCExpCustomsAuthorityActionImpl",
							"出口报关单--保存报关单");
				}
            	if(changeNode!=null){
            		if(isSelected){
            			if(changeNode.getState() != JTristateCheckBox.SELECTED){
            				changeChildAclPermission(changeNode, isSelected);
    						setNodeState(changeNode, isSelected);
    						setParentNodeState(changeNode);
    						((DefaultTreeModel) tree.getModel())
    								.nodeChanged(changeNode);
            			}
            		}
				}
        	}
        }
        /**
		 * 获得权限某个节点
		 * @param node
		 */
    	private CheckNode getPermissionNode(String permissionName,String permission) {
    		CheckNode rootNode = (CheckNode) jTree.getModel().getRoot();
    		for (int i = 0; i < rootNode.getChildCount(); i++) {
    			CheckNode node = (CheckNode) rootNode.getChildAt(i);
    			for (int l = 0; l < node.getChildCount(); l++) {
    				CheckNode cnode = (CheckNode) node.getChildAt(l);
    				CustomPermission customPermission = ((CustomPermission) cnode
    						.getUserObject());
    				if (permissionName.equals(customPermission.getName())
							&& permission.equals(customPermission
									.getPermission())) {
						return cnode;
					}
    			}
    		}
    		return null;
    	}
		/**
		 * @param node
		 * @param isSelected
		 */
		private void setNodeState(CheckNode node, boolean isSelected) {
			if (isSelected) {
				node.setState(JTristateCheckBox.SELECTED);
			} else {
				node.setState(JTristateCheckBox.NOT_SELECTED);
			}
		}

		/**
		 * @param node
		 * @param isSelected
		 */
		private void changeChildAclPermission(CheckNode node, boolean isSelected) {
			// if (authorityOwner instanceof AclUser) {
			// if (isSelected) {
			// if (node.getAclPermission() != null) {
			// return;
			// }
			// AclUserPermission permission = new AclUserPermission();
			// AuthorityModule authorityModule = (AuthorityModule) ((CheckNode)
			// node
			// .getParent()).getUserObject();
			// CustomPermission cutomPermission = (CustomPermission)
			// ((CheckNode) node)
			// .getUserObject();
			// permission.setModuleName(authorityModule.getName().trim());
			// permission.setPermission(cutomPermission.toString().trim());
			// permission.setUser((AclUser) authorityOwner);
			// permission = authorityAction.saveUserPermission(
			// new Request(CommonVars.getCurrUser()), permission);
			// node.setAclPermission(permission);
			// } else {
			// if (node.getAclPermission() == null) {
			// return;
			// }
			// authorityAction.deleteUserPermission(new Request(CommonVars
			// .getCurrUser()), (AclUserPermission) node
			// .getAclPermission());
			// node.setAclPermission(null);
			// }
			// } else if (authorityOwner instanceof AclGroup) {
			// if (isSelected) {
			// if (node.getAclPermission() != null) {
			// return;
			// }
			// AclGroupPermission permission = new AclGroupPermission();
			// AuthorityModule authorityModule = (AuthorityModule) ((CheckNode)
			// node
			// .getParent()).getUserObject();
			// CustomPermission cutomPermission = (CustomPermission)
			// ((CheckNode) node)
			// .getUserObject();
			// permission.setModuleName(authorityModule.getName().trim());
			// permission.setPermission(cutomPermission.toString().trim());
			// permission.setGroup((AclGroup) authorityOwner);
			// permission = authorityAction.saveGroupPermission(
			// new Request(CommonVars.getCurrUser()), permission);
			// node.setAclPermission(permission);
			// } else {
			// if (node.getAclPermission() == null) {
			// return;
			// }
			// authorityAction.deleteGroupPermission(new Request(
			// CommonVars.getCurrUser()),
			// (AclGroupPermission) node.getAclPermission());
			// node.setAclPermission(null);
			// }
			// }
			if (isSelected) {
				lsDelPermission.remove(node);
				lsAddPermission.add(node);
				System.out.println("----- add:" + node.toString());
			} else {
				lsAddPermission.remove(node);
				lsDelPermission.add(node);
				System.out.println("----- del:" + node.toString());
			}
		}
	}

	class ListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			if (value != null) {
				String s = "";
				if (value instanceof AclUser) {
					s = ((AclUser) value).getUserName();
					setIcon(CommonVars.getRcpIconSource().getIcon(
							"bullets.brkp_obj.icon"));
				} else if (value instanceof AclGroup) {
					s = ((AclGroup) value).getGroupName();
					setIcon(CommonVars.getRcpIconSource().getIcon(
							"misc.rep_editors_view.icon"));
				}
				setText(s);
			}
			return this;
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					authorityModules = authorityAction.getModules();
					if (FmAccredit.this.jTabbedPane.getSelectedIndex() == 0) {
						fillUser();
					} else {
						fillGroup();
					}
					setTreeModel();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (FmAccredit.this.getAuthorityOwner() == null) {
						if (FmAccredit.this.getJTabbedPane().getSelectedIndex() == 0) {
							JOptionPane.showMessageDialog(FmAccredit.this,
									"请先选择要修改的用户！", "提示", 0);
							return;
						} else if (FmAccredit.this.getJTabbedPane()
								.getSelectedIndex() == 1) {
							JOptionPane.showMessageDialog(FmAccredit.this,
									"请先选择要修改的群组！", "提示", 0);
							return;
						}
					}
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.BROWSE;
						setState();
						SwingUtilities.invokeLater(showUserPermissionRunnable);
					} else {
						dataState = DataState.BROWSE;
						setState();
						SwingUtilities.invokeLater(showGroupPermissionRunnable);
					}
					lsAddPermission.clear();
					lsDelPermission.clear();
				}
			});
		}
		return btnUndo;
	}

	class SavePermissionData extends Thread {
		public void run() {
			CommonStepProgress.showStepProgressDialog(null);
			CommonStepProgress.setStepMessage("系统正在保存权限数据，请稍后...");
			int maxSize = lsAddPermission.size() + lsDelPermission.size();
			CommonStepProgress.setStepProgressMaximum(maxSize);
			int num = 0;
			try {
				List<CustomPermission> lsModule = new ArrayList<CustomPermission>();
				if (authorityOwner instanceof AclUser) {
					for (int i = 0; i < lsAddPermission.size(); i++) {
						num++;
						CommonStepProgress.setStepProgressValue(num);
						CheckNode node = (CheckNode) lsAddPermission.get(i);
						if (node.getAclPermission() != null) {
							continue;
						}
						AclUserPermission permission = new AclUserPermission();
						AuthorityModule authorityModule = (AuthorityModule) ((CheckNode) node
								.getParent()).getUserObject();

						CustomPermission cutomPermission = (CustomPermission) ((CheckNode) node)
								.getUserObject();
						permission.setModuleName(cutomPermission.getName()
								.trim());
						permission.setPermission(cutomPermission.toString()
								.trim());
						permission.setUser((AclUser) authorityOwner);
						permission = authorityAction.saveUserPermission(
								new Request(CommonVars.getCurrUser()),
								permission);
						node.setAclPermission(permission);

						if (!lsModule.contains(cutomPermission)) {
							authorityAction.clearAclCache(cutomPermission
									.getName(), (Principal) authorityOwner);
							lsModule.add(cutomPermission);
						}
					}
					for (int i = 0; i < lsDelPermission.size(); i++) {
						num++;
						CommonStepProgress.setStepProgressValue(num);
						CheckNode node = (CheckNode) lsDelPermission.get(i);
						if (node.getAclPermission() == null) {
							continue;
						}
						// AuthorityModule authorityModule = (AuthorityModule)
						// ((CheckNode) node
						// .getParent()).getUserObject();
						CustomPermission cutomPermission = (CustomPermission) ((CheckNode) node)
								.getUserObject();
						authorityAction.deleteUserPermission(new Request(
								CommonVars.getCurrUser()),
								(AclUserPermission) node.getAclPermission());
						node.setAclPermission(null);
						if (!lsModule.contains(cutomPermission)) {
							authorityAction.clearAclCache(cutomPermission
									.getName(), (Principal) authorityOwner);
							lsModule.add(cutomPermission);
						}
					}
				} else if (authorityOwner instanceof AclGroup) {
					for (int i = 0; i < lsAddPermission.size(); i++) {
						num++;
						CommonStepProgress.setStepProgressValue(num);
						CheckNode node = (CheckNode) lsAddPermission.get(i);
						if (node.getAclPermission() != null) {
							continue;
						}
						AclGroupPermission permission = new AclGroupPermission();
						AuthorityModule authorityModule = (AuthorityModule) ((CheckNode) node
								.getParent()).getUserObject();
						CustomPermission cutomPermission = (CustomPermission) ((CheckNode) node)
								.getUserObject();
						permission.setModuleName(cutomPermission.getName()
								.trim());
						permission.setPermission(cutomPermission.toString()
								.trim());
						permission.setGroup((AclGroup) authorityOwner);
						permission = authorityAction.saveGroupPermission(
								new Request(CommonVars.getCurrUser()),
								permission);
						node.setAclPermission(permission);
						if (!lsModule.contains(cutomPermission)) {
							authorityAction.clearAclCache(cutomPermission
									.getName(), (Principal) authorityOwner);
							lsModule.add(cutomPermission);
						}
					}
					for (int i = 0; i < lsDelPermission.size(); i++) {
						num++;
						CommonStepProgress.setStepProgressValue(num);
						CheckNode node = (CheckNode) lsDelPermission.get(i);
						if (node.getAclPermission() == null) {
							continue;
						}
						CustomPermission cutomPermission = (CustomPermission) ((CheckNode) node)
								.getUserObject();
						// AuthorityModule authorityModule = (AuthorityModule)
						// ((CheckNode) node
						// .getParent()).getUserObject();
						authorityAction.deleteGroupPermission(new Request(
								CommonVars.getCurrUser()),
								(AclGroupPermission) node.getAclPermission());
						node.setAclPermission(null);
						if (!lsModule.contains(cutomPermission)) {
							authorityAction.clearAclCache(cutomPermission
									.getName(), (Principal) authorityOwner);
							lsModule.add(cutomPermission);
						}
					}
				}
			} catch (Exception ex) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmAccredit.this, ex.getMessage(),
						"提示", 0);
				if (jTabbedPane.getSelectedIndex() == 0) {
					dataState = DataState.BROWSE;
					setState();
					SwingUtilities.invokeLater(showUserPermissionRunnable);
				} else {
					dataState = DataState.BROWSE;
					setState();
					SwingUtilities.invokeLater(showGroupPermissionRunnable);
				}
				lsAddPermission.clear();
				lsDelPermission.clear();
			} finally {
				lsAddPermission.clear();
				lsDelPermission.clear();
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SavePermissionData().start();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	private void setState() {
		this.jTabbedPane.setEnabled(dataState == DataState.BROWSE);
		this.btnEdit
				.setEnabled(dataState == DataState.BROWSE
						&& (jTabbedPane.getSelectedIndex() == 0 ? !this.cbShowUserAllPermission
								.isSelected()
								: true));
		this.lstIPUser.setEnabled(dataState == DataState.BROWSE);
		this.lstGroup.setEnabled(dataState == DataState.BROWSE);
		this.btnRefresh.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.btnUndo.setEnabled(dataState != DataState.BROWSE);
		// this.jTree.setEnabled(dataState!=DataState.BROWSE);
		this.cbShowUserAllPermission.setEnabled(dataState == DataState.BROWSE);
		this.cbShowUserAllPermission
				.setVisible(jTabbedPane.getSelectedIndex() == 0);
		jTree.revalidate();
		jTree.repaint();
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmAccredit.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbShowUserAllPermission() {
		if (cbShowUserAllPermission == null) {
			cbShowUserAllPermission = new JCheckBox();
			cbShowUserAllPermission.setText("显示用户所有权限");
			cbShowUserAllPermission
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (FmAccredit.this.getAuthorityOwner() == null) {
								if (FmAccredit.this.getJTabbedPane()
										.getSelectedIndex() == 0) {
									JOptionPane.showMessageDialog(
											FmAccredit.this, "请先选择要修改的用户！",
											"提示", 0);
									return;
								} else if (FmAccredit.this.getJTabbedPane()
										.getSelectedIndex() == 1) {
									JOptionPane.showMessageDialog(
											FmAccredit.this, "请先选择要修改的群组！",
											"提示", 0);
									return;
								}
							}
							if (cbShowUserAllPermission.isSelected()) {
								SwingUtilities
										.invokeLater(showUserAllPermissionRunnable);
							} else {
								SwingUtilities
										.invokeLater(showUserPermissionRunnable);
							}
							setState();
						}
					});
		}
		return cbShowUserAllPermission;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getLstUser());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getLstGroup());
		}
		return jScrollPane2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

