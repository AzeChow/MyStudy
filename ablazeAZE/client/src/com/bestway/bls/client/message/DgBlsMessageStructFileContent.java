package com.bestway.bls.client.message;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.message.entity.TempCspMessageTreeInfo;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBlsMessageStructFileContent extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton1 = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTree jTree = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable = null;

	private Map<String, List> fieldDescMap = new HashMap<String, List>(); // @jve:decl-index=0:

	private Map<String, List> fieldValueMap = new HashMap<String, List>(); // @jve:decl-index=0:

	private BlsMessageAction blsMessageAction = null;

	private String formatFileName; // @jve:decl-index=0:

	private String messageFileName;  //  @jve:decl-index=0:

	private JButton jButton = null;

	public void setFormatFileName(String formatFileName) {
		this.formatFileName = formatFileName;
	}

	public void setMessageFileName(String messageFileName) {
		this.messageFileName = messageFileName;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBlsMessageStructFileContent() {
		super();
		initialize();
		blsMessageAction = (BlsMessageAction) CommonVars
				.getApplicationContext().getBean("blsMessageAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			showTreeData();
		}
		super.setVisible(b);
	}

	private void showTreeData() {
		Map map = blsMessageAction.readBlsMessageFileContent(new Request(
				CommonVars.getCurrUser(), true), formatFileName,
				messageFileName);
		List lsTree = (List) map.get("MessageTree");
		fieldDescMap = (Map) map.get("FieldDesc");
		fieldValueMap = (Map) map.get("FieldVaue");
		for (int i = 0; i < lsTree.size(); i++) {
			TempCspMessageTreeInfo info = (TempCspMessageTreeInfo) lsTree
					.get(i);
//			System.out.println("----parent:" + info.getParentDesc()
//					+ "---child:" + info.getChildDesc() + "-----sectionFlag:"
//					+ info.getSectionFlag());
		}
		TempCspMessageTreeInfo rootInfo = getRootMessageInfo(lsTree);
		if (rootInfo == null) {
			return;
		}
		MessageTreeNode rootNode = new MessageTreeNode(rootInfo
				.getSectionFlag(), rootInfo.getChildDesc());
		makeTreeNode(rootNode, lsTree);
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		jTree.setModel(model);
		expandTreeNode(jTree, rootNode);
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

	private TempCspMessageTreeInfo getRootMessageInfo(List list) {
		for (int i = 0; i < list.size(); i++) {
			TempCspMessageTreeInfo info = (TempCspMessageTreeInfo) list
					.get(i);
			if ((info.getParentDesc() == null || info.getParentDesc().equals(
					""))
					&& info.getChildDesc() != null
					&& !info.getChildDesc().equals("")) {
				return info;
			}
		}
		return null;
	}

	private List getChildTreeInfo(String parentInfo, List list) {
		List lsResult = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TempCspMessageTreeInfo temp = (TempCspMessageTreeInfo) list
					.get(i);
			if (temp.getParentDesc() != null
					&& temp.getParentDesc().equals(parentInfo)) {
				lsResult.add(temp);
			}
		}
		return lsResult;
	}

	private void makeTreeNode(MessageTreeNode treeNode, List list) {
		String caption = treeNode.getCaption();
		List lsChind = getChildTreeInfo(caption, list);
		for (int i = 0; i < lsChind.size(); i++) {
			TempCspMessageTreeInfo temp = (TempCspMessageTreeInfo) lsChind
					.get(i);
			MessageTreeNode childNode = new MessageTreeNode(temp
					.getSectionFlag(), temp.getChildDesc());
			treeNode.add(childNode);
			makeTreeNode(childNode, list);
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(716, 539));
		this.setTitle("报文内容读取");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jToolBar.add(getJButton1());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(160);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJScrollPane1());
			jSplitPane.setDividerSize(6);
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
			jScrollPane.setViewportView(getJTree());
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
			jTree = new JTree();
			jTree.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {
					JTree tree = (JTree) e.getSource();
					tree.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					if (tree.getRowForLocation(e.getX(), e.getY()) == -1) {
						tree.setCursor(new Cursor(Cursor.HAND_CURSOR));
						return;
					}
					TreePath curPath = tree.getPathForLocation(e.getX(), e
							.getY());
					MessageTreeNode treeNode = (MessageTreeNode) curPath
							.getLastPathComponent();
					try {
						String sectionFlag = getSelectedSectionFlag(treeNode,
								treeNode.getSectionFlag());
//						System.out.println("---------sectionFlag:"
//								+ sectionFlag);
						List lsFieldValue = (fieldValueMap == null ? new ArrayList()
								: fieldValueMap.get(sectionFlag));
						if(lsFieldValue==null){
							lsFieldValue=new ArrayList();
						}
						List lsFieldDesc = (fieldDescMap == null ? new ArrayList()
								: fieldDescMap.get(sectionFlag));
						if(lsFieldDesc==null){
							lsFieldDesc=new ArrayList();
						}
						initTable(lsFieldDesc, lsFieldValue);
					} finally {
						tree.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}
				}
			});

		}
		return jTree;
	}

	private String getSelectedSectionFlag(MessageTreeNode treeNode,
			String childSectionFlag) {
		MessageTreeNode parentNode = (MessageTreeNode) treeNode.getParent();
		if (parentNode != null && parentNode.getSectionFlag() != null) {
			String sectionFlag = (parentNode.getSectionFlag() + "-" + childSectionFlag);
			return getSelectedSectionFlag(parentNode, sectionFlag);
		}
		return childSectionFlag;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	class MessageTreeNode extends DefaultMutableTreeNode {
		private String sectionFlag;

		private String caption;

		public MessageTreeNode(String sectionFlag, String caption) {
			super(caption);
			this.sectionFlag = sectionFlag;
			this.caption = caption;
		}

		public String getSectionFlag() {
			return sectionFlag;
		}

		public String getCaption() {
			return caption;
		}

	}

	private void initTable(final List lsFieldDesc, final List objList) {
		JTableListModel tableModel = new JTableListModel(getJTable(), objList,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						if (lsFieldDesc == null || lsFieldDesc.size() <= 0) {
//							list.add(addColumn("", 300));
						} else {
							for (int i = 0; i < lsFieldDesc.size(); i++) {
								list.add(addColumn(lsFieldDesc.get(i)
										.toString(), 100));
							}
						}
						return list;
					}

				});
		tableModel.setMiRenderColumnEnabled(false);
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("查看文本内容");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBlsMessageFlatFileContent dg=new DgBlsMessageFlatFileContent();
					dg.setMessageFileName(messageFileName);
					dg.setVisible(true);
				}
			});
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
