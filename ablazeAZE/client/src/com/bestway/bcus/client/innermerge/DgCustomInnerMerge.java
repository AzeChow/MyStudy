/*
 * Created on 2004-7-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.CustomInnerMergeCondition;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCustomInnerMerge extends JDialogBase {
	

    private JPanel               jContentPane         = null;
    private JTable               jTable               = null;
    private JScrollPane          jScrollPane          = null;
    private JPanel               jPanel1              = null;
    private JButton              btnSave              = null;
    private JButton              btnEdit              = null;
    private JButton              btnDelete            = null;
    private JPanel               jPanel6              = null;
    private JTextField           tfCommodityCode      = null;
    private JTextField           tfMaterielName       = null;
    private JTextField           tfMaterielSpec       = null;
    private JTextField           tfUpExceedPrice      = null;
    private JTextField           tfLegalUnit          = null;
    private JTextField           tfMaterielNo         = null;
    private JTextField           tfCommodityName      = null;
    private JTextField           tfMemoUnit           = null;
    private JComboBox            cbbMaterielType      = null;
    private JButton              btnLegalUnit         = null;
    private JButton              btnCommodityCode     = null;
    private JButton              btnCustomInnerMerge  = null;
    private JButton              btnMemoUnit          = null;
    private JTableListModel      tableModel           = null;
    private List                 dataSource           = null;
    private Complex              complex              = null;
    private Unit                 legalUnit            = null;
    private Unit                 memoUnit             = null;

    private JButton              btnClose             = null;
    private JTextField           tfLowestPrice        = null;
    private CommonBaseCodeAction commonBaseCodeAction = null;
    private int                  dataState            = DataState.BROWSE;

    private JButton              btnAdd               = null;

    private JButton              btnCancel            = null;
    private boolean              isBeginCustomInnerOk = false;
    private JToolBar             jToolBar             = null;

    /**
     * This method initializes
     *  
     */
    public DgCustomInnerMerge() {
        super();
        initialize();
        commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
                .getApplicationContext().getBean("commonBaseCodeAction");
        jTable = new GroupableHeaderTable();
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editData();
                }
            }
        });
        jTable.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) {
                            return;
                        }
                        JTableListModel tableModel = (JTableListModel)jTable.getModel();
                        if (tableModel == null) {
                            return;
                        }
                        if (tableModel.getCurrentRow() != null) {
                            showData((CustomInnerMergeCondition) tableModel
                                    .getCurrentRow());
                        }
                    }
                });
        jScrollPane.setViewportView(jTable);
        dataSource = commonBaseCodeAction
                .findCustomInnerMergeCondition(new Request(CommonVars
                        .getCurrUser()));
        initTable(dataSource);
        setState();
    }

    /**
     * This method initializes this
     *  
     */
    private void initialize() {
        this.setTitle("自定义归并");
        this.setContentPane(getJContentPane());
        this.setSize(602, 458);
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.gridy = 2;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.weighty = 1.0;
            gridBagConstraints5.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints5.ipadx = 120;
            gridBagConstraints5.ipady = -206;
            gridBagConstraints5.insets = new java.awt.Insets(1, 3, 0, 3);
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.gridy = 0;
            gridBagConstraints6.gridheight = 2;
            gridBagConstraints6.ipadx = 578;
            gridBagConstraints6.ipady = 87;
            gridBagConstraints6.insets = new java.awt.Insets(33, 0, 65, 0);
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.gridy = 1;
            gridBagConstraints7.ipadx = 578;
            gridBagConstraints7.ipady = 66;
            gridBagConstraints7.insets = new java.awt.Insets(42, 0, 0, 0);
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.gridy = 0;
            gridBagConstraints8.weightx = 1.0;
            gridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.ipadx = 282;
            gridBagConstraints8.ipady = 2;
            gridBagConstraints8.insets = new java.awt.Insets(0, 1, 41, 2);
            jContentPane.add(getJScrollPane(), null);
            jContentPane.add(getJPanel1(), null);
            jContentPane.add(getJPanel6(), null);
            jContentPane.add(getJToolBar(), null);
        }
        return jContentPane;
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

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTable());
            jScrollPane.setBounds(1, 216, 594, 215);
        }
        return jScrollPane;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            javax.swing.JLabel jLabel14 = new JLabel();
            javax.swing.JLabel jLabel13 = new JLabel();
            javax.swing.JLabel jLabel12 = new JLabel();
            javax.swing.JLabel jLabel9 = new JLabel();
            javax.swing.JLabel jLabel8 = new JLabel();
            javax.swing.JLabel jLabel7 = new JLabel();
            javax.swing.JLabel jLabel6 = new JLabel();
            javax.swing.JLabel jLabel3 = new JLabel();
            javax.swing.JLabel jLabel2 = new JLabel();
            javax.swing.JLabel jLabel1 = new JLabel();
            javax.swing.JLabel jLabel = new JLabel();
            jPanel1 = new JPanel();
            jPanel1.setLayout(null);
            jLabel.setText("物料最低价格");
            jLabel.setBounds(43, 67, 74, 20);
            jLabel1.setText("物料名称");
            jLabel1.setBounds(353, 20, 50, 20);
            jLabel2.setText("最大上浮百分比");
            jLabel2.setBounds(0, 0, 0, 0);
            jLabel3.setText("物料编号");
            jLabel3.setBounds(354, 44, 49, 20);
            jLabel6.setText("JLabel");
            jLabel7.setText("JLabel");
            jLabel8.setText("物料规格");
            jLabel8.setBounds(67, 44, 50, 20);
            jLabel9.setText("类别");
            jLabel9.setBounds(91, 20, 26, 20);
            jLabel12.setText("备案单位");
            jLabel12.setBounds(139, 11, 48, 18);
            jLabel13.setText("%");
            jLabel13.setBounds(537, 67, 14, 20);
            jLabel14.setText("最大上浮百分比");
            jLabel14.setBounds(316, 67, 87, 20);
            jPanel1
                    .setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "归并条件设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12), new java.awt.Color(227,145,0)));
            jPanel1.setBounds(4, 33, 588, 102);
            jPanel1.add(jLabel, null);
            jPanel1.add(jLabel2, null);
            jPanel1.add(jLabel3, null);
            jPanel1.add(jLabel1, null);
            jPanel1.add(jLabel8, null);
            jPanel1.add(jLabel9, null);
            jPanel1.add(getTfMaterielName(), null);
            jPanel1.add(getTfMaterielSpec(), null);
            jPanel1.add(getTfUpExceedPrice(), null);
            jPanel1.add(getTfLowestPrice(), null);
            jPanel1.add(getTfMaterielNo(), null);
            jPanel1.add(getCbbMaterielType(), null);
            jPanel1.add(jLabel13, null);
            jPanel1.add(jLabel14, null);
        }
        return jPanel1;
    }

    /**
     * This method initializes btnSave
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSave() {
        if (btnSave == null) {
            btnSave = new JButton();
            btnSave.setText("保存");
            btnSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (validatorDataIsNull()) {
                        return;
                    }
                    if (!validatorDataIsNumber()) {
                        return;
                    }
                    saveData();
                }
            });
        }
        return btnSave;
    }

    /**
     * This method initializes btnEdit
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnEdit() {
        if (btnEdit == null) {
            btnEdit = new JButton();
            btnEdit.setText("修改");
            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    editData();
                }
            });
        }
        return btnEdit;
    }

    private void editData() {
        if (!jTable.isEnabled()) {
            return;
        }
        if (tableModel.getCurrentRow() != null) {
            CustomInnerMergeCondition data = (CustomInnerMergeCondition) tableModel
                    .getCurrentRow();
            showData(data);
            complex = data.getAfterComplex();
            legalUnit = data.getAfterLegalUnit();
            memoUnit = data.getAfterMemoUnit();
            dataState = DataState.EDIT;
            setState();
        } else {
            JOptionPane.showMessageDialog(null, "请选择要修改的数据行!!!", "提示", 1);
        }
    }

    /**
     * This method initializes btnDelete
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnDelete() {
        if (btnDelete == null) {
            btnDelete = new JButton();
            btnDelete.setText("删除");
            btnDelete.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (tableModel.getCurrentRow() != null) {
                        if (JOptionPane
                                .showConfirmDialog(DgCustomInnerMerge.this,
                                        "是否确定删除数据!!!", "提示", 0) != 0) {
                            return;
                        }
                        commonBaseCodeAction.deleteCustomInnerMergeCondition(
                                new Request(CommonVars.getCurrUser()),
                                (CustomInnerMergeCondition) tableModel
                                        .getCurrentRow());
                        tableModel.deleteRow(tableModel.getCurrentRow());
                    } else {
                        JOptionPane.showMessageDialog(null, "请选择要删除的数据行!!!",
                                "提示", 0);
                    }
                }
            });
        }
        return btnDelete;
    }

    /**
     * This method initializes jPanel6
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel6() {
        if (jPanel6 == null) {
            javax.swing.JLabel jLabel4 = new JLabel();
            javax.swing.JLabel jLabel15 = new JLabel();
            javax.swing.JLabel jLabel11 = new JLabel();
            javax.swing.JLabel jLabel10 = new JLabel();
            jPanel6 = new JPanel();
            jPanel6.setLayout(null);
            jLabel10.setText("商品名称");
            jLabel10.setBounds(68, 46, 50, 20);
            jLabel11.setText("法定单位");
            jLabel11.setBounds(352, 22, 49, 20);
            jLabel15.setText("备案单位");
            jLabel15.setBounds(352, 46, 49, 20);
            jLabel4.setText("商品编码");
            jLabel4.setBounds(69, 22, 48, 20);
            jPanel6
                    .setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "归并后基本信息", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12), new java.awt.Color(227,145,0)));
            jPanel6.setBounds(4, 135, 588, 79);
            jPanel6.add(jLabel10, null);
            jPanel6.add(jLabel11, null);
            jPanel6.add(getTfCommodityName(), null);
            jPanel6.add(getTfMemoUnit(), null);
            jPanel6.add(getTfLegalUnit(), null);
            jPanel6.add(getBtnLegalUnit(), null);
            jPanel6.add(getTfCommodityCode(), null);
            jPanel6.add(getBtnCommodityCode(), null);
            jPanel6.add(jLabel15, null);
            jPanel6.add(jLabel4, null);
            jPanel6.add(getBtnMemoUnit(), null);
        }
        return jPanel6;
    }

    /**
     * This method initializes tfCommodityCode
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfCommodityCode() {
        if (tfCommodityCode == null) {
            tfCommodityCode = new JTextField();
            tfCommodityCode.setEditable(false);
            tfCommodityCode.setBounds(118, 22, 123, 20);
            tfCommodityCode.setBackground(java.awt.Color.white);
        }
        return tfCommodityCode;
    }

    /**
     * This method initializes tfMaterielName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMaterielName() {
        if (tfMaterielName == null) {
            tfMaterielName = new JTextField();
            tfMaterielName.setBounds(403, 20, 135, 20);
            tfMaterielName.setEditable(false);
            tfMaterielName.setBackground(java.awt.Color.white);
        }
        return tfMaterielName;
    }

    /**
     * This method initializes tfMaterielSpec
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMaterielSpec() {
        if (tfMaterielSpec == null) {
            tfMaterielSpec = new JTextField();
            tfMaterielSpec.setBounds(120, 44, 145, 20);
            tfMaterielSpec.setEditable(false);
            tfMaterielSpec.setBackground(java.awt.Color.white);
        }
        return tfMaterielSpec;
    }

    /**
     * This method initializes tfUpExceedPrice
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfUpExceedPrice() {
        if (tfUpExceedPrice == null) {
            tfUpExceedPrice = new JTextField();
            tfUpExceedPrice.setText("30");
            tfUpExceedPrice.setBounds(403, 67, 135, 20);
            tfUpExceedPrice.setEditable(false);
            tfUpExceedPrice.setBackground(java.awt.Color.white);
        }
        return tfUpExceedPrice;
    }

    /**
     * This method initializes tfLegalUnit
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfLegalUnit() {
        if (tfLegalUnit == null) {
            tfLegalUnit = new JTextField();
            tfLegalUnit.setEditable(false);
            tfLegalUnit.setBounds(401, 22, 116, 20);
            tfLegalUnit.setBackground(java.awt.Color.white);
        }
        return tfLegalUnit;
    }

    /**
     * This method initializes tfMaterielNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMaterielNo() {
        if (tfMaterielNo == null) {
            tfMaterielNo = new JTextField();
            tfMaterielNo.setBounds(403, 44, 135, 20);
            tfMaterielNo.setEditable(false);
            tfMaterielNo.setBackground(java.awt.Color.white);
        }
        return tfMaterielNo;
    }

    /**
     * This method initializes tfCommodityName
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfCommodityName() {
        if (tfCommodityName == null) {
            tfCommodityName = new JTextField();
            tfCommodityName.setEditable(false);
            tfCommodityName.setBounds(119, 46, 143, 20);
            tfCommodityName.setBackground(java.awt.Color.white);
        }
        return tfCommodityName;
    }

    /**
     * This method initializes tfMemoUnit
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfMemoUnit() {
        if (tfMemoUnit == null) {
            tfMemoUnit = new JTextField();
            tfMemoUnit.setEditable(false);
            tfMemoUnit.setBounds(401, 46, 116, 20);
            tfMemoUnit.setBackground(java.awt.Color.white);
        }
        return tfMemoUnit;
    }

    /**
     * This method initializes cbbMaterielType
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCbbMaterielType() {
        if (cbbMaterielType == null) {
            cbbMaterielType = new JComboBox();
            cbbMaterielType.setBounds(119, 20, 145, 20);
            cbbMaterielType.addItem(new ItemProperty(
                    MaterielType.FINISHED_PRODUCT, "成品"));
            cbbMaterielType.addItem(new ItemProperty(
                    MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
            cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL,
                    "料件"));
            cbbMaterielType
                    .addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
            cbbMaterielType.addItem(new ItemProperty(
                    MaterielType.REMAIN_MATERIEL, "边角料"));
            cbbMaterielType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
                    "残次品"));
        }
        return cbbMaterielType;
    }

    /**
     * This method initializes btnLegalUnit
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnLegalUnit() {
        if (btnLegalUnit == null) {
            btnLegalUnit = new JButton();
            btnLegalUnit.setText("...");
            btnLegalUnit.setBounds(517, 22, 21, 20);
            btnLegalUnit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Unit legalUnit = (Unit) CommonQuery.getInstance()
                            .getCustomUnit();
                    if (legalUnit != null) {
                        setLegalUnit(legalUnit);
                        tfLegalUnit.setText(legalUnit.getName());
                    }
                }
            });
        }
        return btnLegalUnit;
    }

    /**
     * This method initializes btnCommodityCode
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCommodityCode() {
        if (btnCommodityCode == null) {
            btnCommodityCode = new JButton();
            btnCommodityCode.setText("...");
            btnCommodityCode.setBounds(242, 22, 21, 20);
            btnCommodityCode
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            Complex complex = (Complex) CommonQuery
                                    .getInstance().getComplex();
                            if (complex != null) {
                                setComplex(complex);
                                tfCommodityCode.setText(complex.getCode());
                            }
                        }
                    });
        }
        return btnCommodityCode;
    }

    /**
     * This method initializes btnCustomInnerMerge
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCustomInnerMerge() {
        if (btnCustomInnerMerge == null) {
            btnCustomInnerMerge = new JButton();
            btnCustomInnerMerge.setText("开始归并");
            btnCustomInnerMerge
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            new CustomInnerMergeDataThread().start();
                            if(DgCustomInnerMerge.this.jTable.getRowCount() > 0){
                                DgCustomInnerMerge.this.isBeginCustomInnerOk = true;
                            }
                        }
                    });
        }
        return btnCustomInnerMerge;
    }

    class CustomInnerMergeDataThread extends Thread {
        public void run() {
            try {
                DgCustomInnerMerge.this.btnCustomInnerMerge.setEnabled(false);
                CommonProgress.showProgressDialog(DgCustomInnerMerge.this);
                CommonProgress.setMessage("正在归并数据,请稍候...");
                try {
                    DgCustomInnerMerge.this.commonBaseCodeAction
                            .customInnerMerge(new Request(CommonVars
                                    .getCurrUser()));                    
                    //					CustomInnerMergeDataThread.sleep(6000);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "自定义归并条件可能有错!!",
                            "信息!!", 0);
                }
                //						JOptionPane.showMessageDialog(null,"自动归并完成!!","信息!!",1);
            } finally {
                CommonProgress.closeProgressDialog();
                DgCustomInnerMerge.this.btnCustomInnerMerge.setEnabled(true);
            }
        }
    }

    /**
     * This method initializes btnMemoUnit
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnMemoUnit() {
        if (btnMemoUnit == null) {
            btnMemoUnit = new JButton();
            btnMemoUnit.setText("...");
            btnMemoUnit.setBounds(517, 46, 21, 20);
            btnMemoUnit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Unit memoUnit = (Unit) CommonQuery.getInstance()
                            .getCustomUnit();
                    if (memoUnit != null) {
                        setMemoUnit(memoUnit);
                        tfMemoUnit.setText(memoUnit.getName());
                    }
                }
            });
        }
        return btnMemoUnit;
    }

    /**
     * 初始化数据表
     */
    private void initTable(List dataSource) {
        tableModel = new JTableListModel((GroupableHeaderTable) jTable,
                dataSource, new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("物料类别", "materielType", 80));
                        list.add(addColumn("物料名称", "conMaterielName", 100));
                        list.add(addColumn("物料规格", "conMaterielSpec", 80));
                        list.add(addColumn("物料编号", "conMaterielCode", 100));
                        list.add(addColumn("物料最低价格", "conLowestPrice", 100));
                        list.add(addColumn("最大上浮百分比", "conPriceUpExceed", 100));

                        /*
                         * 
                         * private String conMaterielCode; //物料编号 归并条件 private
                         * String conMaterielName;//物料名称 归并条件 private String
                         * conMaterielSpec;//物料规格 归并条件 private Double
                         * conLowestPrice;//物料最低价格 归并条件 private Double
                         * conPriceUpExceed;//物料价格最高上浮 归并条件
                         * 
                         * private Complex afterComplex; //归并后的商品编码 private
                         * String afterMaterielName;//归并后的商品名称 private Unit
                         * afterLegalUnit;//归并后的法定单位 private Unit
                         * afterMemoUnit;//归并后的备案单位
                         */
                        list.add(addColumn("商品编码", "afterComplex.code", 50));
                        list.add(addColumn("商品名称", "afterMaterielName", 80));
                        list.add(addColumn("法定单位", "afterLegalUnit.name", 80));
                        list.add(addColumn("备案单位", "afterMemoUnit.name", 80));

                        return list;
                    }
                });
        jTable.getColumnModel().getColumn(1).setCellRenderer(
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        super.setText((value == null) ? "" : castValue(value));
                        return this;
                    }

                    private String castValue(Object value) {
                        if (String.valueOf(value).trim().equals("")) {
                            return "";
                        }
                        String s = String.valueOf(value).trim();
                        if (s.equals(MaterielType.FINISHED_PRODUCT)) {
                            return "成品";
                        } else if (s.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
                            return "半成品";
                        } else if (s.equals(MaterielType.MATERIEL)) {
                            return "料件";
                        } else if (s.equals(MaterielType.MACHINE)) {
                            return "设备";
                        } else if (s.equals(MaterielType.REMAIN_MATERIEL)) {
                            return "边角料";
                        } else if (s.equals(MaterielType.BAD_PRODUCT)) {
                            return "残次品";
                        }
                        return "";
                    }
                });
        TableColumnModel cm = jTable.getColumnModel();
        ColumnGroup gBefore = new ColumnGroup("归并条件");
        gBefore.add(cm.getColumn(1));
        gBefore.add(cm.getColumn(2));
        gBefore.add(cm.getColumn(3));
        gBefore.add(cm.getColumn(4));
        gBefore.add(cm.getColumn(5));
        gBefore.add(cm.getColumn(6));
        ColumnGroup gAfter = new ColumnGroup("归并后基本信息");
        gAfter.add(cm.getColumn(7));
        gAfter.add(cm.getColumn(8));
        gAfter.add(cm.getColumn(9));
        gAfter.add(cm.getColumn(10));
        GroupableTableHeader header = (GroupableTableHeader) jTable
                .getTableHeader();
        header.addColumnGroup(gBefore);
        header.addColumnGroup(gAfter);
    }

    public Complex getComplex() {
        return complex;
    }

    public void setComplex(Complex complex) {
        this.complex = complex;
    }

    public Unit getLegalUnit() {
        return legalUnit;
    }

    public void setLegalUnit(Unit legalUnit) {
        this.legalUnit = legalUnit;
    }

    public Unit getMemoUnit() {
        return memoUnit;
    }

    public void setMemoUnit(Unit memoUnit) {
        this.memoUnit = memoUnit;
    }

    /**
     * This method initializes btnClose
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnClose() {
        if (btnClose == null) {
            btnClose = new JButton();
            btnClose.setText("关闭");
            btnClose.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DgCustomInnerMerge.this.dispose();
                }
            });
        }
        return btnClose;
    }

    /**
     * 验证是否为数字
     */
    private boolean validatorDataIsNumber() {
        if (!this.tfLowestPrice.getText().equals("")) {
            if (!validatorDataIsNumber(this.tfLowestPrice.getText())) {
                JOptionPane.showMessageDialog(null, "物料价格不是有效的数据格式", "警告", 1);
                return false;
            }
        }
        if (!this.tfUpExceedPrice.getText().equals("")) {
            if (!validatorDataIsNumber(this.tfUpExceedPrice.getText())) {
                JOptionPane.showMessageDialog(null, "最大上浮动百分比不是有效的数据格式", "警告",
                        1);
                return false;
            }
        }
        return true;
    }

    /**
     * 验证是否为数字
     */
    private boolean validatorDataIsNumber(String number) {
        boolean isNumber = true;
        try {
            Double.parseDouble(number);
        } catch (Exception ex) {
            isNumber = false;
        }
        return isNumber;
    }

    /**
     * 验证是否为空
     */
    private boolean validatorDataIsNull() {
        if (this.cbbMaterielType.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "类别名称不可为空", "警告", 1);
            return true;
        }
        if (tfCommodityCode.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "商品编码不可为空", "警告", 1);
            return true;
        }
        if (tfLegalUnit.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "法定单位不可为空!!", "警告", 1);
            return true;
        }
        if (tfCommodityName.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "商品名称不可为空!!", "警告", 1);
            return true;
        }
        if (tfMemoUnit.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "备案单位不可为空", "警告", 1);
            return true;
        }
        if (tfMaterielSpec.getText().trim().equals("")
                && tfMaterielName.getText().trim().equals("")
                && tfMaterielNo.getText().trim().equals("")
                && tfLowestPrice.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "归并条件至小一个不为空!!!", "警告", 1);
            return true;
        }
        return false;
    }

    //	private boolean val

    /**
     * This method initializes tfLowestPrice
     * 
     * @return javax.swing.JFormattedTextField
     */
    private JTextField getTfLowestPrice() {
        if (tfLowestPrice == null) {
            tfLowestPrice = new JTextField();
            tfLowestPrice.setBounds(120, 67, 145, 20);
            tfLowestPrice.setEditable(false);
            tfLowestPrice.setBackground(java.awt.Color.white);
        }
        return tfLowestPrice;
    }

    private void fillData(CustomInnerMergeCondition data) {
        data
                .setMaterielType(String
                        .valueOf(((ItemProperty) this.cbbMaterielType
                                .getSelectedItem()).getCode()));
        data.setAfterComplex(complex);
        data.setAfterLegalUnit(legalUnit);
        data.setAfterMaterielName(this.tfCommodityName.getText());
        data.setAfterMemoUnit(memoUnit);
        if (!tfLowestPrice.getText().equals("")) {
            data.setConLowestPrice(Double.valueOf(tfLowestPrice.getText()));
        }
        data.setConMaterielCode(tfMaterielNo.getText());
        data.setConMaterielName(tfMaterielName.getText());
        data.setConMaterielSpec(tfMaterielSpec.getText());
        if (!tfUpExceedPrice.getText().equals("")) {
            data.setConPriceUpExceed(Double.valueOf(tfUpExceedPrice.getText()));
        }
        if (CommonVars.getCurrUser().getCompany() != null) {
            data.setCompany(CommonVars.getCurrUser().getCompany());
        }
        ;
    }

    private int getCbbIndexByCode(String code) {
        int index = -1;
        for (int i = 0; i < cbbMaterielType.getItemCount(); i++) {
            ItemProperty coiProperty = (ItemProperty) this.cbbMaterielType
                    .getItemAt(i);
            if (coiProperty.getCode().equals(code)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void showData(CustomInnerMergeCondition data) {
        if (data.getMaterielType() == null) {
            this.cbbMaterielType.setSelectedIndex(-1);
        } else {
            if (!data.getMaterielType().equals("")) {
                this.cbbMaterielType.setSelectedIndex(getCbbIndexByCode(data
                        .getMaterielType()));
            }
        }
        if (data.getAfterComplex() != null) {
            this.tfCommodityCode.setText(data.getAfterComplex().getCode());
        } else {
            this.tfCommodityCode.setText("");
        }
        if (data.getAfterMaterielName() != null) {
            this.tfCommodityName.setText(data.getAfterMaterielName());
        } else {
            this.tfCommodityName.setText("");
        }
        if (data.getAfterLegalUnit() != null) {
            this.tfLegalUnit.setText(data.getAfterLegalUnit().getName());
        } else {
            this.tfLegalUnit.setText("");
        }
        if (data.getAfterMemoUnit() != null) {
            this.tfMemoUnit.setText(data.getAfterMemoUnit().getName());
        } else {
            this.tfMemoUnit.setText("");
        }
        if (data.getConLowestPrice() != null) {
            this.tfLowestPrice.setText(data.getConLowestPrice().toString());
        } else {
            this.tfLowestPrice.setText("");
        }
        if (data.getConMaterielCode() != null) {
            this.tfMaterielNo.setText(data.getConMaterielCode());
        } else {
            this.tfMaterielNo.setText("");
        }
        if (data.getConMaterielName() != null) {
            this.tfMaterielName.setText(data.getConMaterielName());
        } else {
            this.tfMaterielName.setText("");
        }
        if (data.getConMaterielSpec() != null) {
            this.tfMaterielSpec.setText(data.getConMaterielSpec());
        } else {
            this.tfMaterielSpec.setText("");
        }
        if (data.getConPriceUpExceed() != null) {
            this.tfUpExceedPrice.setText(data.getConPriceUpExceed().toString());
        } else {
            this.tfUpExceedPrice.setText("");
        }
    }

    private void emptyData() {
        this.cbbMaterielType.setSelectedIndex(-1);
        this.tfCommodityCode.setText("");
        this.tfCommodityName.setText("");
        this.tfLegalUnit.setText("");
        this.tfLowestPrice.setText("");
        this.tfMaterielName.setText("");
        this.tfMaterielNo.setText("");
        this.tfMaterielSpec.setText("");
        this.tfMemoUnit.setText("");
        this.tfUpExceedPrice.setText("30");
    }

    private void setState() {
        btnSave.setEnabled(dataState != DataState.BROWSE);
        btnEdit.setEnabled(dataState == DataState.BROWSE);
        btnAdd.setEnabled(dataState == DataState.BROWSE);
        btnCancel.setEnabled(dataState != DataState.BROWSE);
        btnDelete.setEnabled(dataState == DataState.BROWSE);
        this.btnCommodityCode.setEnabled(dataState != DataState.BROWSE);
        this.btnLegalUnit.setEnabled(dataState != DataState.BROWSE);
        this.btnMemoUnit.setEnabled(dataState != DataState.BROWSE);
        this.cbbMaterielType.setEnabled(dataState != DataState.BROWSE);
        cbbMaterielType.setForeground(Color.BLACK);
        this.tfCommodityName.setEditable(dataState != DataState.BROWSE);
        this.tfLowestPrice.setEditable(dataState != DataState.BROWSE);
        this.tfMaterielName.setEditable(dataState != DataState.BROWSE);
        this.tfMaterielNo.setEditable(dataState != DataState.BROWSE);
        this.tfMaterielSpec.setEditable(dataState != DataState.BROWSE);
        this.tfUpExceedPrice.setEditable(dataState != DataState.BROWSE);
        this.jTable.setEnabled(dataState == DataState.BROWSE);
    }

    private void saveData() {
        if (dataState == DataState.ADD) {
            CustomInnerMergeCondition data = new CustomInnerMergeCondition();
            fillData(data);
            commonBaseCodeAction.saveCustomInnerMergeCondition(new Request(
                    CommonVars.getCurrUser()), data);
            tableModel.addRow(data);
            emptyData();
        } else if (dataState == DataState.EDIT) {
            CustomInnerMergeCondition data = (CustomInnerMergeCondition) tableModel
                    .getCurrentRow();
            fillData(data);
            commonBaseCodeAction.saveCustomInnerMergeCondition(new Request(
                    CommonVars.getCurrUser()), data);
            tableModel.updateRow(data);
        }
        dataState = DataState.BROWSE;
        setState();
    }

    /**
     * This method initializes btnAdd
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnAdd() {
        if (btnAdd == null) {
            btnAdd = new JButton();
            btnAdd.setText("新增");
            btnAdd.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dataState = DataState.ADD;
                    emptyData();
                    setState();
                }
            });
        }
        return btnAdd;
    }

    /**
     * This method initializes btnCancel
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setText("取消");
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dataState = DataState.BROWSE;
                    setState();
                }
            });
        }
        return btnCancel;
    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.setBounds(1, 0, 573, 36);
            jToolBar.add(getBtnAdd());
            jToolBar.add(getBtnEdit());
            jToolBar.add(getBtnSave());
            jToolBar.add(getBtnDelete());
            jToolBar.add(getBtnCancel());
            jToolBar.add(getBtnCustomInnerMerge());
            jToolBar.add(getBtnClose());
        }
        return jToolBar;
    }
    /**
     * @return Returns the isBeginCustomInnerOk.
     */
    public boolean isBeginCustomInnerOk() {
        return isBeginCustomInnerOk;
    }
    /**
     * @param isBeginCustomInnerOk The isBeginCustomInnerOk to set.
     */
    public void setBeginCustomInnerOk(boolean isBeginCustomInnerOk) {
        this.isBeginCustomInnerOk = isBeginCustomInnerOk;
    }
} //  @jve:decl-index=0:visual-constraint="10,-20"
