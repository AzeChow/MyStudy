/*
 * Created on 2004-10-12
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.UnitWaste;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmUnitWaste extends JInternalFrameBase {
    private JPanel               jPanel               = null;
    private JSplitPane           jSplitPane           = null;
    private JPanel               jPanel1              = null;
    private JPanel               jPanel2              = null;
    private JTable               jTable               = null;
    private JScrollPane          jScrollPane          = null;
    private JToolBar             jToolBar1            = null;
    private JButton              btnAddBom            = null;
    private JButton              btnDeleteBom         = null;
    private ButtonGroup          buttonGroup          = new ButtonGroup();

    private JTableListModel      tableModel           = null;
    private JTableListModel      tableModel1          = null;
    private JTableListModel      tableModel2          = null;
    private CommonBaseCodeAction commonBaseCodeAction = null;
    private MaterialManageAction materialManageAction = null;
    private Vector               versions             = new Vector();
//    private MaterielBomVersion   currVersion          = null;
    private JTabbedPane          jTabbedPane          = null;
    private JPanel               jPanel4              = null;
    private JPanel               jPanel5              = null;
    private JTable               jTable1              = null;
    private JScrollPane          jScrollPane1         = null;
    private JTable               jTable2              = null;
    private JScrollPane          jScrollPane2         = null;
    private List                 list                 = null;
    private JToolBar             jToolBar             = null;
    private JButton              jButton              = null;
    private JPanel               jPanel3              = null;
    private JTextField           jTextField           = null;
    private JLabel               jLabel               = null;
    private JButton              jButton1             = null;
    private JButton              jButton2             = null;
    private JRadioButton         jRadioButton         = null;
    private JRadioButton         jRadioButton1        = null;

    /**
     * This method initializes
     * 
     */
    public FmUnitWaste() {
        super();
        commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
                .getApplicationContext().getBean("commonBaseCodeAction");
        materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
        commonBaseCodeAction.checkUnitWasteBrowseAuthority(new Request(
                CommonVars.getCurrUser()));
        initialize();
        buttonGroup.add(getJRadioButton());
        buttonGroup.add(getJRadioButton1());
        jRadioButton.setSelected(true);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("成品单耗管理");
        this.setContentPane(getJPanel());
        this.setSize(773, 469);
        this
                .addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                    public void internalFrameOpened(
                            javax.swing.event.InternalFrameEvent e) {
                        initTable(new Vector());
                        initTable1(new Vector());
                        initTable2(new Vector());
                    }
                });
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
        }
        return jPanel;
    }

    /**
     * This method initializes jSplitPane
     * 
     * @return javax.swing.JSplitPane
     */
    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setDividerLocation(200);
            jSplitPane.setDividerSize(0);
            jSplitPane.setTopComponent(getJPanel1());
            jSplitPane.setBottomComponent(getJPanel2());
        }
        return jSplitPane;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
            jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
        }
        return jPanel1;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setLayout(new BorderLayout());
            jPanel2.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jPanel2.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
            jPanel2.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
        }
        return jPanel2;
    }

    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();
            jTable.getSelectionModel().addListSelectionListener(
                    new ListSelectionListener() {
                        public void valueChanged(ListSelectionEvent e) {
                            if (e.getValueIsAdjusting()) {
                                return;
                            }
                            if (tableModel == null)
                                return;
                            if (tableModel.getCurrentRow() == null)
                                return;
                            Materiel bom = (Materiel) tableModel
                                    .getCurrentRow();
                            List detail = materialManageAction.findUnitWaste(
                                    new Request(CommonVars.getCurrUser()), bom
                                            .getPtNo());
                            if (detail != null && !detail.isEmpty()) {
                                initTable1(detail);
                                initTable2(detail);
                            } else {
                                initTable1(new Vector());
                                initTable2(new Vector());
                            }
                        }
                    });
        }
        return jTable;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jToolBar1
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar1() {
        if (jToolBar1 == null) {
            jToolBar1 = new JToolBar();
            jToolBar1.add(getJRadioButton());
            jToolBar1.add(getJRadioButton1());
            jToolBar1.add(getBtnAddBom());
            jToolBar1.add(getBtnDeleteBom());
        }
        return jToolBar1;
    }

    /**
     * This method initializes btnAddBom
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnAddBom() {
        if (btnAddBom == null) {
            btnAddBom = new JButton();
            btnAddBom.setText("计算单耗");
            btnAddBom.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (jRadioButton.isSelected()) {

                    } else {
                        if (tableModel.getCurrentRow() == null) {
                            JOptionPane.showMessageDialog(FmUnitWaste.this,
                                    "请选择成品！", "提示", 2);
                            return;
                        }
                        Materiel exg = (Materiel) tableModel.getCurrentRow();
                        // Delete exist UnitWaste
                        materialManageAction.deleteAllBom(new Request(
                                CommonVars.getCurrUser()), exg.getPtNo());
                        commonBaseCodeAction.findMaterielFromBomManage(
                                new Request(CommonVars.getCurrUser()), exg
                                        .getPtNo());
                        List factortyList = materialManageAction.findUnitWaste(
                                new Request(CommonVars.getCurrUser()), exg
                                        .getPtNo());
                        // fill factortyTable
                        initTable1(factortyList);
                        // fill customTable
                        initTable2(factortyList);
                    }
                }
            });
        }
        return btnAddBom;
    }

    /**
     * This method initializes btnDeleteBom
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnDeleteBom() {
        if (btnDeleteBom == null) {
            btnDeleteBom = new JButton();
            btnDeleteBom.setText("删除");
            btnDeleteBom.setVisible(false);
            btnDeleteBom.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (tableModel1.getCurrentRow() == null) {
                        JOptionPane.showMessageDialog(FmUnitWaste.this,
                                "请选择要修改的单耗明细.");
                        return;
                    }
                    if (JOptionPane
                            .showConfirmDialog(FmUnitWaste.this,
                                    "确认要删除当前当单耗明细吗？", "删除记录",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        UnitWaste currDetail = (UnitWaste) tableModel1
                                .getCurrentRow();
                        materialManageAction.deleteBomImgDetail(new Request(
                                CommonVars.getCurrUser()), currDetail);
                        tableModel1.deleteRow(currDetail);
                    }

                }
            });
        }
        return btnDeleteBom;
    }

    // 成品
    private void initTable(final List list) {
        tableModel = new JTableListModel(jTable, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List<JTableListColumn> list = new Vector<JTableListColumn>();
                        list.add(addColumn("品号", "ptNo", 100));
                        list.add(addColumn("商品编码", "complex.code", 80));
                        list.add(addColumn("工厂名称", "factoryName", 100));
                        list.add(addColumn("型号规格", "factorySpec", 100));
                        list.add(addColumn("工厂单位", "calUnit.name", 100));
                        list.add(addColumn("报关单位", "ptUnit.name", 100));
                        list.add(addColumn("单位折算", "unitConvert", 100));
                        list.add(addColumn("法定单位", "complex.firstUnit.name",
                                100));
                        list.add(addColumn("法定单位二", "complex.secondUnit.name",
                                100));
                        list.add(addColumn("单重", "ptNetWeight", 80));
                        return list;
                    }
                });
    }

    // 单耗
    private void initTable1(final List list) {
        tableModel1 = new JTableListModel(jTable1, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List<JTableListColumn> list = new Vector<JTableListColumn>();
                        list.add(addColumn("料号", "ptNo", 80));
                        list.add(addColumn("商品编码", "ptName", 100));
                        list.add(addColumn("报关名称", "ptSpec", 100));
                        list.add(addColumn("报关规格型号", "calUnit.name", 120));
                        list.add(addColumn("报关单位", "unitWaste", 80));

                        list.add(addColumn("报关单耗", "unitWaste", 80));
                        list.add(addColumn("损耗率%", "waste", 80));

                        list.add(addColumn("工厂品名", "netWeight", 80));
                        list.add(addColumn("工厂规格型号", "grossWeight", 120));
                        list.add(addColumn("工厂单位", "netWeight", 80));
                        list.add(addColumn("计量单位", "netWeight", 80));
                        list.add(addColumn("工厂单耗", "netWeight", 80));
                        list.add(addColumn("单重", "netWeight", 80));
                        list.add(addColumn("法定单位", "netWeight", 80));
                        list.add(addColumn("法定单位二", "netWeight", 100));

                        list.add(addColumn("是否主料", "isMainMateriel", 80));
                        list.add(addColumn("材料来源", "meterSource", 80));
                        return list;
                    }
                });
    }

    private void initTable2(final List list) {
        tableModel2 = new JTableListModel(jTable2, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List<JTableListColumn> list = new Vector<JTableListColumn>();
                        list.add(addColumn("内部报关代码", "ptNo", 120));
                        list.add(addColumn("商品编码", "ptName", 100));
                        list.add(addColumn("报关名称", "ptSpec", 100));
                        list.add(addColumn("报关规格型号", "calUnit.name", 120));
                        list.add(addColumn("报关单位", "unitWaste", 80));

                        list.add(addColumn("报关单耗", "unitWaste", 80));
                        list.add(addColumn("损耗率%", "waste", 80));

                        list.add(addColumn("工厂品名", "netWeight", 80));
                        list.add(addColumn("工厂规格型号", "grossWeight", 120));
                        list.add(addColumn("工厂单位", "netWeight", 80));
                        list.add(addColumn("计量单位", "netWeight", 80));
                        list.add(addColumn("工厂单耗", "netWeight", 80));
                        list.add(addColumn("单重", "netWeight", 80));
                        list.add(addColumn("法定单位", "netWeight", 80));
                        list.add(addColumn("法定单位二", "netWeight", 100));

                        list.add(addColumn("是否主料", "isMainMateriel", 80));
                        list.add(addColumn("材料来源", "meterSource", 80));
                        return list;
                    }
                });
    }

    /*
     * private void fillData(String MaterielType){ //initTable List masters =
     * commonBaseCodeAction.findBomImg(new
     * Request(CommonVars.getCurrUser()),MaterielType); if (masters != null &&
     * !masters.isEmpty()){ initTable(masters); BomImg bom = (BomImg)
     * masters.get(0); List detail = commonBaseCodeAction.findBomImgDetail(new
     * Request(CommonVars.getCurrUser()),bom); if (detail != null &&
     * !detail.isEmpty()){ initTable1(detail); } else { initTable1(new
     * Vector()); } } else { initTable(new Vector()); initTable1(new Vector()); } }
     */
    class ImportDataRunnable extends Thread {
        public void run() {
            try {
                CommonProgress.showProgressDialog();
                CommonProgress.setMessage("系统正在导入资料，请稍后...");
                /*
                 * if (FmOrgUnitWaste.this.rbProduct.isSelected()){
                 * commonBaseCodeAction.findMaterielFromBomManage(new
                 * Request(CommonVars.getCurrUser()),MaterielType.FINISHED_PRODUCT);
                 * fillData(MaterielType.FINISHED_PRODUCT); } else {
                 * commonBaseCodeAction.findMaterielFromBomManage(new
                 * Request(CommonVars.getCurrUser()),MaterielType.SEMI_FINISHED_PRODUCT);
                 * fillData(MaterielType.SEMI_FINISHED_PRODUCT); }
                 */
            } finally {
                CommonProgress.closeProgressDialog();

            }

        }
    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
            jTabbedPane.addTab("工厂单耗", null, getJPanel4(), null);
            jTabbedPane.addTab("报关单耗", null, getJPanel5(), null);
        }
        return jTabbedPane;
    }

    /**
     * This method initializes jPanel4
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            jPanel4 = new JPanel();
            jPanel4.setLayout(new BorderLayout());
            jPanel4.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
        }
        return jPanel4;
    }

    /**
     * This method initializes jPanel5
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel5() {
        if (jPanel5 == null) {
            jPanel5 = new JPanel();
            jPanel5.setLayout(new BorderLayout());
            jPanel5.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
        }
        return jPanel5;
    }

    /**
     * This method initializes jTable1
     * 
     * @return javax.swing.JTable
     */
    private JTable getJTable1() {
        if (jTable1 == null) {
            jTable1 = new JTable();
        }
        return jTable1;
    }

    /**
     * This method initializes jScrollPane1
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getJTable1());
        }
        return jScrollPane1;
    }

    /**
     * This method initializes jTable2
     * 
     * @return javax.swing.JTable
     */
    private JTable getJTable2() {
        if (jTable2 == null) {
            jTable2 = new JTable();
        }
        return jTable2;
    }

    /**
     * This method initializes jScrollPane2
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane2() {
        if (jScrollPane2 == null) {
            jScrollPane2 = new JScrollPane();
            jScrollPane2.setViewportView(getJTable2());
        }
        return jScrollPane2;
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
            jToolBar.add(getJPanel3());
        }
        return jToolBar;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setText("显示全部");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    list = commonBaseCodeAction.findMaterielWhereCp(
                            new Request(CommonVars.getCurrUser()), "");
                    initTable(list);
                }
            });
        }
        return jButton;
    }

    /**
     * This method initializes jPanel3
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jLabel = new JLabel();
            jPanel3 = new JPanel();
            jPanel3.setLayout(null);
            jLabel.setBounds(7, 3, 40, 24);
            jLabel.setText("品号：");
            jPanel3.add(getJTextField(), null);
            jPanel3.add(jLabel, null);
            jPanel3.add(getJButton1(), null);
            jPanel3.add(getJButton2(), null);
        }
        return jPanel3;
    }

    /**
     * This method initializes jTextField
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setBounds(49, 5, 106, 22);
        }
        return jTextField;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton();
            jButton1.setBounds(155, 4, 72, 23);
            jButton1.setText("查询");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (jTextField.getText().equals("")) {
                        JOptionPane.showMessageDialog(FmUnitWaste.this,
                                "请输入品名！", "提示", 2);
                        return;
                    }
                    list = commonBaseCodeAction.findMaterielWhereCp(
                            new Request(CommonVars.getCurrUser()), jTextField
                                    .getText().trim());
                    initTable(list);
                }
            });
        }
        return jButton1;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton2() {
        if (jButton2 == null) {
            jButton2 = new JButton();
            jButton2.setBounds(229, 4, 72, 23);
            jButton2.setText("关闭");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    FmUnitWaste.this.dispose();
                }
            });
        }
        return jButton2;
    }

    /**
     * This method initializes jRadioButton
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getJRadioButton() {
        if (jRadioButton == null) {
            jRadioButton = new JRadioButton();
            jRadioButton.setText("全部成品");
        }
        return jRadioButton;
    }

    /**
     * This method initializes jRadioButton1
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getJRadioButton1() {
        if (jRadioButton1 == null) {
            jRadioButton1 = new JRadioButton();
            jRadioButton1.setText("当前成品");
        }
        return jRadioButton1;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
