/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.bestway.bcus.client.cas.parameter.PnBillCorrespondingControl;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmErpBillCenterParameterSet extends JInternalFrameBase {
	private javax.swing.JPanel jContentPane = null;
	private JPanel jPanel1 = null;
	private JTree jTree = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 单据选项
	 */
	private final static int BILL_OPTION = 1;
	/**
	 * 制单号控制
	 */
	private final static int LOT_CONTROL = 2;

	/**
	 * 订单管理参数设置
	 */
	private final static int ORDER_CONTROL = 3;

	/**
	 * 单据对应控制
	 */
	private final static int BILL_CORRESPONDING_CONTROL = 4;

	/**
	 * This is the default constructor
	 */
	public FmErpBillCenterParameterSet() {
		super();
		//
		// check authority
		//
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkErpBillCenterParameterByBrowse(new Request(
				CommonVars.getCurrUser()));

		initialize();
		TreePath treePath = jTree.getPathForRow(0);
		if (treePath != null) {
			jTree.setSelectionPath(treePath);
			jTree.scrollPathToVisible(treePath);
		}
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("单据中心参数设置");
		this.setSize(693, 474);
		this.setContentPane(getJContentPane());

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
			jContentPane.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.WEST);
		}
		return jContentPane;
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
			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			jPanel1.setLayout(new BorderLayout());
			jLabel17.setText("");
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("单据中心参数设置");
			jLabel18
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel1.add(jLabel18, java.awt.BorderLayout.CENTER);
			jPanel1.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree());
			jScrollPane.setPreferredSize(new java.awt.Dimension(155, 363));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree() {
		if (jTree == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("单据类型");
			createNodes(root);
			jTree = new JTree(root);
			jTree.setRootVisible(false);
			jTree
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							valueChange(jTree);
							// System.out.println("tree selection event value
							// change is --");
						}
					});
			jTree.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			ToolTipManager.sharedInstance().registerComponent(jTree);
			jTree.setCellRenderer(new DefaultTreeCellRenderer() {
				public Component getTreeCellRendererComponent(JTree tree,
						Object value, boolean sel, boolean expanded,
						boolean leaf, int row, boolean hasFocus) {

					super.getTreeCellRendererComponent(tree, value, sel,
							expanded, leaf, row, hasFocus);

					setToolTipText(value.toString());
					Double y = this.getBounds().getY();
					this.setBounds(25, y.intValue(), WIDTH, HEIGHT);
					return this;
				}
			});
		}
		return jTree;
	}

	/**
	 * 创建树的所有结点
	 * 
	 * @param category
	 */
	private void createNodes(DefaultMutableTreeNode root) {
		DefaultMutableTreeNode note = null;
		note = new DefaultMutableTreeNode(new NoteInfo(BILL_OPTION, "单据选项"));
		root.add(note);
		note = new DefaultMutableTreeNode(new NoteInfo(LOT_CONTROL, "制单号控制"));
		root.add(note);
		note = new DefaultMutableTreeNode(new NoteInfo(ORDER_CONTROL,
				"订单管理参数设置"));
		root.add(note);
		note = new DefaultMutableTreeNode(new NoteInfo(
				BILL_CORRESPONDING_CONTROL, "单据对应控制"));
		root.add(note);
	}

	/**
	 * 节点值改变
	 */
	private void valueChange(JTree jTree) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree
				.getSelectionPath().getLastPathComponent();
		if (selectedNode == null) {
			return;
		}
		if (!(selectedNode.getUserObject() instanceof NoteInfo)) {
			return;
		}
		NoteInfo noteInfo = (NoteInfo) selectedNode.getUserObject();
		if (noteInfo == null) {
			return;
		}
		showPanelByTreeSelectedCode(noteInfo.getCode());
	}

	/**
	 * 显示面板
	 * 
	 * @param selectedCode
	 */
	private void showPanelByTreeSelectedCode(int selectedCode) {
		JPanelBase panel = null;
		switch (selectedCode) {
		case BILL_OPTION:
			panel = PnBillOption.getInstance();
			break;
		case LOT_CONTROL:
			panel = PnLotControl.getInstance();
			break;
		case ORDER_CONTROL:
			panel = PnOrderOption.getInstance();
			break;
		case BILL_CORRESPONDING_CONTROL:
			panel = PnBillCorrespondingControl.getInstance();
			break;
		}
		if (panel != null) {
			for (int i = 0; i < this.jContentPane.getComponentCount(); i++) {
				Component component = this.jContentPane.getComponent(i);
				if (component instanceof JPanelBase) {
					if ("tag"
							.equals((String) ((JPanelBase) component).getTag())) {
						this.jContentPane.remove(component);
					}
				}
			}
			panel.setTag("tag");
			this.jContentPane.add(panel, java.awt.BorderLayout.CENTER);
			this.jContentPane.repaint();
			this.jContentPane.validate();
		}
	}

	class NoteInfo {
		private int code = -1;
		private String name = "";

		/**
		 * @return Returns the i.
		 */
		public int getCode() {
			return code;
		}

		/**
		 * @return Returns the name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param i
		 *            The i to set.
		 */
		public void setCode(int code) {
			this.code = code;
		}

		/**
		 * @param name
		 *            The name to set.
		 */
		public void setName(String name) {
			this.name = name;
		}

		public NoteInfo(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

}
