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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.bestway.bcus.client.authority.FmAccredit.CheckNode;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.authority.acl.CustomPermission;
import com.bestway.common.authority.action.AuthorityAction;
import com.bestway.common.authority.entity.AclIPaddress;
import com.bestway.common.authority.entity.AclIpaddressPermission;
import com.bestway.common.authority.entity.AuthorityModule;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.JTristateCheckBox;

public class FmIPAccredit extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JButton btnCancel = null;

	private JButton btnCollapseTree = null;

	private JButton btnExpandTree = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JList jList = null;

	private JScrollPane jScrollPane1 = null;

	private JTree jTree = null;

	private AuthorityAction authorityAction = null;

	private AuthorityModule[] authorityModules = null;

	private AclIPaddress selectedaclIPaddress = null; // @jve:decl-index=0:

	private List lsAddPermission = new ArrayList(); // @jve:decl-index=0:

	private List lsDelPermission = new ArrayList(); // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private Map<String, Enumeration> map = new HashMap<String, Enumeration>(); // @jve:decl-index=0:

	public FmIPAccredit() {
		super();
		authorityAction = (AuthorityAction) CommonVars.getApplicationContext()
				.getBean("authorityAction");
		authorityModules = authorityAction.getModules();
		authorityAction.brown(new Request(CommonVars.getCurrUser()));
		initialize();
		fillIPScope();
		setTreeModel();
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(741, 561));
		this.setTitle("IP地址授权");
		this.setContentPane(getJContentPane());

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton5());
			jToolBar.add(getJButton1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillIPScope();
					jList.repaint();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	private Runnable showUserPermissionRunnable = new Runnable() {
		public void run() {
			FmIPAccredit.this.setCursor(new Cursor(Cursor.WAIT_CURSOR)); // @jve:decl-index=0:
			try {
				showIPUserPermission(); // @jve:decl-index=0:
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(FmIPAccredit.this, ex
						.getMessage(), "提示!", 0);
				FmIPAccredit.this.doDefaultCloseAction();
			} finally {
				FmIPAccredit.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
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

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
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

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
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
		this.btnEdit.setEnabled(dataState == DataState.BROWSE);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);
		this.jTree.setEnabled(dataState != DataState.BROWSE);
		this.jList.setEnabled(dataState == DataState.BROWSE);
		jTree.revalidate();
		jTree.repaint();
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(180);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJScrollPane1());
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setCellRenderer(new ListCellRender());
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							selectedaclIPaddress = (AclIPaddress) jList
									.getSelectedValue();
							SwingUtilities
									.invokeLater(showUserPermissionRunnable);
						}
					});

		}
		return jList;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTree());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();
		}
		return jTree;
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

	/**
	 * 
	 */
	private void setTreeModel() {
		CheckNode rootNode = new CheckNode("全部权限");
//		for (int i = 0; i < authorityModules.length; i++) {
//			CheckNode node = new CheckNode(authorityModules[i]);
//			if (authorityModules[i].getPermissions() != null) {
//				for (int j = 0; j < authorityModules[i].getPermissions().size(); j++) {
//					CheckNode childNode = new CheckNode(authorityModules[i]
//							.getPermissions().get(j));
//					node.add(childNode);
//				}
//			}
//			rootNode.add(node);
//		}
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

	private void fillIPScope() {
		List vector = new Vector();
		vector = authorityAction.findIPaddress(new Request(CommonVars
				.getCurrUser(),true));
		this.jList.setListData(vector.toArray());
		if (jList.getModel().getSize() > 0) {
			jList.setSelectedIndex(0);
			SwingUtilities.invokeLater(showUserPermissionRunnable);
		}
	}

	private void showIPUserPermission() {
		// AclIpaddressPermission
		uncheckAllTreeNode();
		AclIPaddress user = (AclIPaddress) jList.getSelectedValue();
		List list = authorityAction.findAclIpaddressPermissionByuserId(
				new Request(CommonVars.getCurrUser(),true), user.getId(), null);
		for (int i = 0; i < list.size(); i++) {
			AclIpaddressPermission permission = (AclIpaddressPermission) list
					.get(i);
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

	private Enumeration getPermissionNodes(String moduleName) {
		CheckNode rootNode = (CheckNode) jTree.getModel().getRoot();
		for (int i = 0; i < rootNode.getChildCount(); i++) {
			CheckNode node = (CheckNode) rootNode.getChildAt(i);
			if (((AuthorityModule) node.getUserObject()).getName()
					.toLowerCase().equals(moduleName.toLowerCase())) {
				map.put(moduleName.toLowerCase(), node.children());
				return node.children();
			}
		}
		return null;
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
				} else {
					isSelected = true;
				}
				if (FmIPAccredit.this.getSelectedaclIPaddress() == null) {
					JOptionPane.showMessageDialog(FmIPAccredit.this,
							"请先选择要修改的IP段！", "提示", 0);
					return;
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

	class ListCellRender extends DefaultListCellRenderer {
		public ListCellRender() {
			super();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setComponentOrientation(list.getComponentOrientation());
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			if (value == null) {
				setText("");
			} else {
				String text = "[" + ((AclIPaddress) value).getBegianIP() + "]"
						+ "-[" + ((AclIPaddress) value).getEndIP() + "]";
				setText(text);
			}
			setIcon(null);
			setEnabled(list.isEnabled());
			setFont(list.getFont());

			Border border = null;
			if (cellHasFocus) {
				if (isSelected) {
					border = UIManager
							.getBorder("List.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager
							.getBorder("List.focusCellHighlightBorder");
				}
			} else {
				border = noFocusBorder;
			}
			setBorder(border);

			return this;
		}
	}

	public AclIPaddress getSelectedaclIPaddress() {
		return selectedaclIPaddress;
	}

	public void setSelectedaclIPaddress(AclIPaddress selectedaclIPaddress) {
		this.selectedaclIPaddress = selectedaclIPaddress;
	}

	class SavePermissionData extends Thread {
		public void run() {
			CommonStepProgress.showStepProgressDialog(null);
			CommonStepProgress.setStepMessage("系统正在保存权限数据，请稍后...");
			int maxSize = lsAddPermission.size() + lsDelPermission.size();
			CommonStepProgress.setStepProgressMaximum(maxSize);
			int num = 0;
			try {
				List<AuthorityModule> lsModule = new ArrayList<AuthorityModule>();
				if (selectedaclIPaddress instanceof AclIPaddress) {
					for (int i = 0; i < lsAddPermission.size(); i++) {
						num++;
						CommonStepProgress.setStepProgressValue(num);
						CheckNode node = (CheckNode) lsAddPermission.get(i);
						if (node.getAclPermission() != null) {
							continue;
						}
						AclIpaddressPermission permission = new AclIpaddressPermission();
						AuthorityModule authorityModule = (AuthorityModule) ((CheckNode) node
								.getParent()).getUserObject();
						CustomPermission cutomPermission = (CustomPermission) ((CheckNode) node)
								.getUserObject();
						permission.setModuleName(authorityModule.getName()
								.trim());
						permission.setPermission(cutomPermission.toString()
								.trim());
						permission
								.setIpuser((AclIPaddress) selectedaclIPaddress);
						permission = authorityAction
								.saveAclIpaddressPermission(new Request(
										CommonVars.getCurrUser()), permission);
						node.setAclPermission(permission);
					}
					for (int i = 0; i < lsDelPermission.size(); i++) {
						num++;
						CommonStepProgress.setStepProgressValue(num);
						CheckNode node = (CheckNode) lsDelPermission.get(i);
						if (node.getAclPermission() == null) {
							continue;
						}
						AuthorityModule authorityModule = (AuthorityModule) ((CheckNode) node
								.getParent()).getUserObject();
						authorityAction.deleteAclIpaddressPermission(
								new Request(CommonVars.getCurrUser()),
								(AclIpaddressPermission) node
										.getAclPermission());
						node.setAclPermission(null);
						// if (!lsModule.contains(authorityModule)) {
						// authorityAction.clearAclCache(authorityModule
						// .getName(), (Principal) authorityOwner);
						// lsModule.add(authorityModule);
						// }
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmIPAccredit.this, ex
						.getMessage(), "提示", 0);
				lsAddPermission.clear();
				lsDelPermission.clear();
			} finally {
				lsAddPermission.clear();
				lsDelPermission.clear();
				authorityAction.clearIPcache(new Request(CommonVars
						.getCurrUser(),true));
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
