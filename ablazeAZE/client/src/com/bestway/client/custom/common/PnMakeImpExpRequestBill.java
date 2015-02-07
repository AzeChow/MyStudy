/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempBillMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JPanelBase;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeImpExpRequestBill extends JPanelBase {

    private PnMakeImpExpRequestBill pnMakeCustomsEnvelopBill2 = null;
    private JTableListModel           tableModel                = null;
    private JTable                    jTable                    = null;
    private JScrollPane               jScrollPane               = null;
    private JButton                   btnSelectAll              = null;
    private JButton                   btnNotSelectAll           = null;
    private List                      billMasterList            = null;
    private int                       impExpType                = -1;
    private CasAction                 casAction                 = null;

    /**
     * This is the default constructor
     */
    public PnMakeImpExpRequestBill() {
        super();
        initialize();
        casAction = (CasAction) CommonVars.getApplicationContext().getBean(
                "casAction");
    }

    public void setVisible(boolean isFlag) {
        if (isFlag == true) {
            initTable();
        }
        super.setVisible(isFlag);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        javax.swing.JLabel jLabel = new JLabel();
        this.setLayout(null);
        this.setSize(500, 248);
        jLabel.setBounds(13, 11, 132, 21);
        jLabel.setText("可供选择的海关单据");
        this.add(jLabel, null);
        this.add(getJScrollPane(), null);
        this.add(getBtnSelectAll(), null);
        this.add(getBtnNotSelectAll(), null);
    }

    /**
     * @return Returns the pnMakeCustomsEnvelopBill2.
     */
    public PnMakeImpExpRequestBill getPnMakeCustomsEnvelopBill2() {
        return pnMakeCustomsEnvelopBill2;
    }

    /**
     * @param pnMakeCustomsEnvelopBill2
     *            The pnMakeCustomsEnvelopBill2 to set.
     */
    public void setPnMakeCustomsEnvelopBill2(
            PnMakeImpExpRequestBill pnMakeCustomsEnvelopBill2) {
        this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
    }

    /**
     * @return Returns the parentList.
     */
    public List getBillMasterList() {
        return billMasterList;
    }

    /**
     * @param parentList
     *            The parentList to set.
     */
    public void setBillMasterList(List parentList) {
        this.billMasterList = parentList;
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
            jScrollPane.setBounds(2, 39, 497, 209);
            jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

    /**
     * This method initializes btnSelectAll
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSelectAll() {
        if (btnSelectAll == null) {
            btnSelectAll = new JButton();
            btnSelectAll.setBounds(364, 11, 61, 21);
            btnSelectAll.setText("全选");
            btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    selectAll(true);
                }
            });
        }
        return btnSelectAll;
    }

    /**
     * This method initializes btnNotSelectAll
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnNotSelectAll() {
        if (btnNotSelectAll == null) {
            btnNotSelectAll = new JButton();
            btnNotSelectAll.setBounds(431, 11, 61, 21);
            btnNotSelectAll.setText("不选");
            btnNotSelectAll
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            selectAll(false);
                        }
                    });
        }
        return btnNotSelectAll;
    }

    /**
     * 选中所有 or 清除所有选择
     */
    private void selectAll(boolean isSelected) {
        if (this.tableModel == null) {
            return;
        }
        List list = tableModel.getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempBillMaster) {
                TempBillMaster temp = (TempBillMaster) list.get(i);
                temp.setIsSelected(new Boolean(isSelected));
            }
        }
        tableModel.updateRows(list);
    }

    /**
     * 初始化数据Table
     */
    private void initTable() {
        List list = null;
        list = this.casAction.findBillMasterIsAvailabilityToIXRB(
                new Request(CommonVars.getCurrUser(),true), this.impExpType);
        JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            public List InitColumns() {
                List list = new Vector();
                list.add(addColumn("选择", "isSelected", 100));
                list.add(addColumn("类别", "billMaster.billType.name", 100));
                list.add(addColumn("单据号", "billMaster.billNo", 100));
//                list.add(addColumn("仓库", "wareSet.name", 80));
                list.add(addColumn("对应报关单号", "billMaster.customNo", 100));
                list.add(addColumn("有效", "billMaster.isValid", 50));
                list.add(addColumn("生效日期", "billMaster.validDate", 80));
                list.add(addColumn("是否记帐", "billMaster.keepAccounts", 50));
                return list;
            }
        };
        jTableListModelAdapter.setEditableColumn(1);
        tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
        jTable.getColumnModel().getColumn(5).setCellRenderer(
                new MultiRenderer());
        jTable.getColumnModel().getColumn(7).setCellRenderer(
                new MultiRenderer());
        jTable.getColumnModel().getColumn(1).setCellRenderer(
                new MultiRenderer());
        jTable.getColumnModel().getColumn(1).setCellEditor(
                new CheckBoxEditor(new JCheckBox()));
    }

    /**
     * render table JchcckBox 列
     */
    class MultiRenderer extends DefaultTableCellRenderer {
        JCheckBox checkBox = new JCheckBox();

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            if (value == null) {
                return this;
            }
            if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                checkBox.setSelected(Boolean.parseBoolean(value.toString()));
                checkBox.setHorizontalAlignment(JLabel.CENTER);
                checkBox.setBackground(table.getBackground());
                if (isSelected) {
                    checkBox.setForeground(table.getSelectionForeground());
                    checkBox.setBackground(table.getSelectionBackground());
                } else {
                    checkBox.setForeground(table.getForeground());
                    checkBox.setBackground(table.getBackground());
                }
                //                if (checkValue(table, row, column)) {
                //                    checkBox.setBackground(Color.BLUE);
                //                    checkBox.setForeground(Color.WHITE);
                //                }
                return checkBox;
            }
            String str = (value == null) ? "" : value.toString();
            return super.getTableCellRendererComponent(table, str, isSelected,
                    hasFocus, row, column);
        }
    }

    /**
     * 编辑列
     */

    class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
        private JCheckBox cb;
        private JTable    table = null;

        public CheckBoxEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            if (value == null) {
                return null;
            }
            if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                cb = new JCheckBox();
                cb
                        .setSelected(Boolean.valueOf(value.toString())
                                .booleanValue());
            }
            cb.setHorizontalAlignment(JLabel.CENTER);
            cb.addActionListener(this);
            this.table = table;
            return cb;
        }

        public Object getCellEditorValue() {
            cb.removeActionListener(this);
            return cb;
        }

        public void actionPerformed(ActionEvent e) {
            JTableListModel tableModel = (JTableListModel) this.table
                    .getModel();
            Object obj = tableModel.getCurrentRow();
            if (obj instanceof TempBillMaster) {
                TempBillMaster temp = (TempBillMaster) obj;
                temp.setIsSelected(new Boolean(cb.isSelected()));
                tableModel.updateRow(obj);
            }
            fireEditingStopped();
        }

    }

    /**
     * 获得选中的海关商品单据
     */
    public List getMasterList() {
        if (this.tableModel == null) {
            return null;
        }
        List list = tableModel.getList();
        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempBillMaster) {
                TempBillMaster temp = (TempBillMaster) list.get(i);
                if (temp.getIsSelected().booleanValue() == true) {
                    newList.add(temp);
                }
            }
        }
        return newList;
    }

    

    /**
     * @return Returns the impExpType.
     */
    public int getImpExpType() {
        return impExpType;
    }

    /**
     * @param impExpType
     *            The impExpType to set.
     */
    public void setImpExpType(int impExpType) {
        this.impExpType = impExpType;
    }
}

