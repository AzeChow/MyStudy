/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JLabel;
import javax.swing.JComboBox;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceBatchWriteAccount extends JDialogBase {

    private JPanel      jContentPane          = null;
    private JTabbedPane jTabbedPane           = null;
    private JToolBar    jToolBar              = null;
    private JButton     btnSearch             = null;
    private JButton     btnSelectAll          = null;
    private JButton     btnWriteAccount       = null;
    private JButton     btnUnreel             = null;
    private JButton     btnExit               = null;
    private JTable      tb1                   = null;
    private JScrollPane jScrollPane           = null;
   private JTable      tb2                   = null;
    private JScrollPane jScrollPane1          = null;
    private JButton     btnReCalculateAccount = null;
    private CasAction   casAction             = null;
    private JLabel      jLabel                = null;
    private JPanel      jPanel                = null;
    private JLabel      jLabel1               = null;
    private JComboBox   cbbType               = null;

    /**
     * This method initializes
     * 
     */
    public DgFinanceBatchWriteAccount() {
        super(false);
        initialize();
        casAction = (CasAction) CommonVars.getApplicationContext().getBean(
                "casAction");
        initUIComponents();

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("财务成批记帐和回卷");
        this.setContentPane(getJContentPane());
        this.setSize(755, 539);
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
            jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
            jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
        }
        return jContentPane;
    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
            jTabbedPane.addTab("已生效未记帐单据", null, getJScrollPane(), null);
            jTabbedPane.addTab("已生效已记帐单据", null, getJScrollPane1(), null);
            jTabbedPane
                    .addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent e) {
                            setState();
                        }
                    });
        }
        return jTabbedPane;
    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jLabel = new JLabel();
            jToolBar = new JToolBar();
            jLabel.setText("                                                                              ");
            jToolBar.add(getJPanel());
            jToolBar.add(getJButton());
            jToolBar.add(getJButton1());
            jToolBar.add(getJButton2());
            jToolBar.add(getJButton3());
            jToolBar.add(getJButton5());
            jToolBar.add(getJButton4());
            jToolBar.add(jLabel);
        }
        return jToolBar;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (btnSearch == null) {
            btnSearch = new JButton();
            btnSearch.setText("查询");
            btnSearch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    search();
                }
            });
        }
        return btnSearch;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton1() {
        if (btnSelectAll == null) {
            btnSelectAll = new JButton();
            btnSelectAll.setText("全选");
            btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    selectAll();
                }
            });
        }
        return btnSelectAll;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton2() {
        if (btnWriteAccount == null) {
            btnWriteAccount = new JButton();
            btnWriteAccount.setText("记帐");
            btnWriteAccount
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            writeAccount();
                        }
                    });
        }
        return btnWriteAccount;
    }

    /**
     * This method initializes jButton3
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton3() {
        if (btnUnreel == null) {
            btnUnreel = new JButton();
            btnUnreel.setText("回卷");
            btnUnreel.setEnabled(false);
            btnUnreel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    unreel();
                }

            });
        }
        return btnUnreel;
    }

    /**
     * This method initializes jButton4
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton4() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setText("关闭");
            btnExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return btnExit;
    }

    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getTb1() {
        if (tb1 == null) {
            tb1 = new JTable();
        }
        return tb1;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTb1());
            jScrollPane.setToolTipText("未生效记帐单据");
        }
        return jScrollPane;
    }

    /**
     * This method initializes jTable1
     * 
     * @return javax.swing.JTable
     */
    private JTable getTb2() {
        if (tb2 == null) {
            tb2 = new JTable();
        }
        return tb2;
    }

    /**
     * This method initializes jScrollPane1
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getTb2());
            jScrollPane1.setToolTipText("已生效记帐单据");
        }
        return jScrollPane1;
    }

    /**
     * This method initializes jButton5
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton5() {
        if (btnReCalculateAccount == null) {
            btnReCalculateAccount = new JButton();
            btnReCalculateAccount.setText("重新计算财务帐");
            btnReCalculateAccount
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            reCalculateAccount();
                        }
                    });
        }
        return btnReCalculateAccount;
    }

    /**
     * 初始化数据
     * 
     */
    private void initUIComponents() {
        initJTable(new ArrayList(), tb1);
        initJTable(new ArrayList(), tb2);
    }

    /**
     * 初始化BillMaster
     * 
     */
    private void initJTable(List list, JTable tb) {
        new JTableListModel(tb, list, new JTableListModelAdapter() {
            public List InitColumns() {
                List<JTableListColumn> list = new Vector<JTableListColumn>();
                list.add(addColumn("类别", "billType.name", 100));
                list.add(addColumn("单据号", "billNo", 100));
                list.add(addColumn("仓库", "wareSet.name", 80));
                list.add(addColumn("客户名称", "scmCoc.name", 100));
                list.add(addColumn("有效", "isValid", 50));
                list.add(addColumn("生效日期", "validDate", 100));
                list.add(addColumn("是否记帐", "keepAccounts", 50));
                return list;
            }
        });
        tb.getColumnModel().getColumn(5).setCellRenderer(
                new TableCheckBoxRender());
        tb.getColumnModel().getColumn(7).setCellRenderer(
                new TableCheckBoxRender());
        tb.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    /**
     * 查询
     */
    private void search() {
        ItemProperty  item = (ItemProperty)cbbType.getSelectedItem();
        if (this.jTabbedPane.getSelectedIndex() == 0) {
            List list = this.casAction.findBillMaster(new Request(CommonVars
                    .getCurrUser()),item.getCode(), false);
            initJTable(list, tb1);
        } else if (this.jTabbedPane.getSelectedIndex() == 1) {
            List list = this.casAction.findBillMaster(new Request(CommonVars
                    .getCurrUser()),item.getCode(), true);
            initJTable(list, tb2);
        }
    }

    /**
     * 全选
     */
    private void selectAll() {
        if (this.jTabbedPane.getSelectedIndex() == 0) {
            tb1.selectAll();
        } else if (this.jTabbedPane.getSelectedIndex() == 1) {
            tb2.selectAll();
        }
    }

    /**
     * 记帐
     * 
     */
    private void writeAccount() {
        JTableListModel tableModel = (JTableListModel) tb1.getModel();
        if (tableModel.getCurrentRow() == null) {
            JOptionPane.showMessageDialog(this, "请选择要记帐的单据!!", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List list = tableModel.getCurrentRows();
        this.casAction.saveBillMaster(new Request(CommonVars.getCurrUser()),
                list, true);
        tableModel.deleteRows(list);
    }

    /**
     * 回卷
     * 
     */
    private void unreel() {
        JTableListModel tableModel = (JTableListModel) tb2.getModel();
        if (tableModel.getCurrentRow() == null) {
            JOptionPane.showMessageDialog(this, "请选择要回卷的单据!!", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List list = tableModel.getCurrentRows();
        //
        // 回卷的单据中是否可以进行回卷
        //
        this.casAction.saveBillMaster(new Request(CommonVars.getCurrUser()),
                list, false);
        tableModel.deleteRows(list);
    }

    /**
     * 设置状态
     * 
     */
    private void setState() {
        btnUnreel.setEnabled(jTabbedPane.getSelectedIndex() == 1);
        btnWriteAccount.setEnabled(jTabbedPane.getSelectedIndex() == 0);
    }

    /**
     * 重新计算财务帐
     * 
     */
    private void reCalculateAccount() {

    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jLabel1 = new JLabel();
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jLabel1.setBounds(11, 3, 57, 23);
            jLabel1.setText("商品类型:");
            jPanel.add(jLabel1, null);
            jPanel.add(getCbbType(), null);
        }
        return jPanel;
    }

    /**
     * This method initializes jComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCbbType() {
        if (cbbType == null) {
            cbbType = new JComboBox();
            cbbType.setBounds(74, 3, 143, 23);
            cbbType.addItem(new ItemProperty(MaterielType.FINISHED_PRODUCT,
                    "成品"));
            cbbType.addItem(new ItemProperty(
                    MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
            cbbType.addItem(new ItemProperty(MaterielType.MATERIEL, "料件"));
            cbbType.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
            cbbType.addItem(new ItemProperty(MaterielType.REMAIN_MATERIEL,
                    "边角料"));
            cbbType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT, "残次品"));
        }
        return cbbType;
    }
}
