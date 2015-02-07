/*
 * Created on 2004-8-24
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.parameter;

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

import com.bestway.bcus.cas.authority.CasParameterAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Font;

/**
 * 海关帐系统参数设置
 * 
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmCasParameterSet extends JInternalFrameBase {
    private javax.swing.JPanel pnContent               = null;
    private JPanel             pn                     = null;
    private JPanel             pnTop                    = null;
    private JTree              jTree                      = null;
    private JScrollPane        spn                = null;
    /**
     * 单据对应控制
     */
//    private final static int   BILL_CORRESPONDING_CONTROL = 3;
    /**
     * 记帐年度
     */
    private final static int   WRITE_ACCOUNT_YEARL        = 5;
    /**
     * 单据选项
     */
    private final static int   OTHER_OPTION               = 6;

    private CasParameterAction casParameterAction         = null;

    /**
     * This is the default constructor
     */
    public FmCasParameterSet() {
        super();
        //
        // check authority
        //
        casParameterAction = (CasParameterAction) CommonVars.getApplicationContext()
            .getBean("casParameterAction");
        casParameterAction.checkCasParameterByBrowse(new Request(CommonVars.getCurrUser()));        
        
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
        this.setTitle("系统参数设置");
        this.setSize(693, 474);
        this.setContentPane(getPnContent());

    }

    /**
     * This method initializes pnContent
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getPnContent() {
        if (pnContent == null) {
            pnContent = new javax.swing.JPanel();
            pnContent.setLayout(new BorderLayout());
            pnContent.setBorder(javax.swing.BorderFactory
                    .createCompoundBorder(null, null));
            pnContent.add(getPnTop(), java.awt.BorderLayout.NORTH);
            pnContent.add(getSpn(), java.awt.BorderLayout.WEST);
        }
        return pnContent;
    }

    /**
     * 
     * This method initializes pnTop
     * 
     * 
     * 
     * @return javax.swing.JPanel
     * 
     */
    private JPanel getPnTop() {
        if (pnTop == null) {
            pnTop = new JPanel();
            javax.swing.JLabel jLabel17 = new JLabel();

            javax.swing.JLabel jLabel18 = new JLabel();

            javax.swing.JLabel jLabel19 = new JLabel();

            pnTop.setLayout(new BorderLayout());
            jLabel17.setText("");
            jLabel17
                    .setIcon(new ImageIcon(
                            getClass()
                                    .getResource(
                                            "/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
            jLabel18.setText("海关帐系统参数设置");
            jLabel18
                    .setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
            jLabel18.setForeground(new java.awt.Color(255, 153, 0));
            jLabel19.setText("");
            jLabel19.setIcon(new ImageIcon(getClass().getResource(
                    "/com/bestway/bcus/client/resources/images/titlepic.jpg")));
            pnTop
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            pnTop.setBackground(java.awt.Color.white);
            pnTop.add(jLabel17, java.awt.BorderLayout.WEST);
            pnTop.add(jLabel18, java.awt.BorderLayout.CENTER);
            pnTop.add(jLabel19, java.awt.BorderLayout.EAST);
        }
        return pnTop;
    }

    /**
     * This method initializes spn
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getSpn() {
        if (spn == null) {
            spn = new JScrollPane();
            spn.setViewportView(getJTree());
            spn.setPreferredSize(new java.awt.Dimension(155, 363));
        }
        return spn;
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
            // jTree.getco
            jTree.setRootVisible(false);
            jTree
                    .addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

                        public void valueChanged(
                                javax.swing.event.TreeSelectionEvent e) {
                            valueChange(jTree);
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
        note = new DefaultMutableTreeNode(new NoteInfo(WRITE_ACCOUNT_YEARL,
                "记帐年度"));
        root.add(note);
//        note = new DefaultMutableTreeNode(new NoteInfo(
//                BILL_CORRESPONDING_CONTROL, "单据对应控制"));
//        root.add(note);
         note = new DefaultMutableTreeNode(new NoteInfo(OTHER_OPTION,
         "其它选项"));
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
//        case BILL_CORRESPONDING_CONTROL:
//            panel = PnBillCorrespondingControl.getInstance();
//            break;
        case WRITE_ACCOUNT_YEARL:
            panel = new PnWriteAccountYear();
            break;
        case OTHER_OPTION:
            panel = PnOther.getInstance();
            break;
        }
        if (panel != null) {
            for (int i = 0; i < this.pnContent.getComponentCount(); i++) {
                Component component = this.pnContent.getComponent(i);
                if (component instanceof JPanelBase) {
                    if ("tag"
                            .equals((String) ((JPanelBase) component).getTag())) {
                        this.pnContent.remove(component);
                    }
                }
            }
            panel.setTag("tag");
            this.pnContent.add(panel, java.awt.BorderLayout.CENTER);
            this.pnContent.repaint();
            this.pnContent.validate();
        }
    }

    class NoteInfo {
        private int    code = -1;
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

} // @jve:decl-index=0:visual-constraint="10,16"
