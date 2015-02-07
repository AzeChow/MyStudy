
/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.Color;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.fpt.entity.TempFptBillItem;
import com.bestway.common.materialbase.entity.ScmCoc;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnMakeCustomsBillList3 extends PnCommon {

    private PnMakeCustomsBillList3 pnMakeCustomsEnvelopBill2 = null;
    private ScmCoc                    scmCoc                    = null;
    private JTableListModel           tableModel                = null;
    private JTable                    jTable                    = null;
    private JScrollPane               jScrollPane               = null;
    private List                      parentList                = null;
    private List                      colorList                 = null;

    private JButton                   btnSelectAll              = null;
    private JButton                   btnNotSelectAll           = null;

    /**
     * This is the default constructor
     */
    public PnMakeCustomsBillList3() {
        super();
        initialize();
    }

    @Override
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
        jLabel.setText("可供选择的商品信息");
        this.add(jLabel, null);
        this.add(getJScrollPane(), null);
        this.add(getBtnSelectAll(), null);
        this.add(getBtnNotSelectAll(), null);
    }

    /**
     * @return Returns the scmCoc.
     */
    public ScmCoc getScmCoc() {
        return scmCoc;
    }

    /**
     * @return Returns the pnMakeCustomsEnvelopBill2.
     */
    public PnMakeCustomsBillList3 getPnMakeCustomsEnvelopBill2() {
        return pnMakeCustomsEnvelopBill2;
    }

    /**
     * @param pnMakeCustomsEnvelopBill2
     *            The pnMakeCustomsEnvelopBill2 to set.
     */
    public void setPnMakeCustomsEnvelopBill2(
            PnMakeCustomsBillList3 pnMakeCustomsEnvelopBill2) {
        this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
    }

    /**
     * @return Returns the parentList.
     */
    public List getParentList() {
        return parentList;
    }

    /**
     * @param parentList
     *            The parentList to set.
     */
    public void setParentList(List parentList) {
        this.parentList = parentList;
    }

    /**
     * @param scmCoc
     *            The scmCoc to set.
     */
    public void setScmCoc(ScmCoc scmCoc) {
        this.scmCoc = scmCoc;
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
            if (list.get(i) instanceof TempFptBillItem) {
                TempFptBillItem temp = (TempFptBillItem) list
                        .get(i);
                temp.setIsSelected(new Boolean(isSelected));
            }
        }
        tableModel.updateRows(list);
    }

    /**
     * 初始化数据Table
     */
    private void initTable() {
        List list = new ArrayList();
//        EmsEdiMergerHead emsEdiMergerHead = manualDeclearAction
//                .findEmsEdiMergerHeadByDeclareState(new Request(CommonVars
//                        .getCurrUser()), DeclareState.PROCESS_EXE);
//        if (emsEdiMergerHead == null) {
//            JOptionPane.showMessageDialog(this, "归并关系中没有正在执行的状态记录!!", "提示",
//                    JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            list = super.transferFactoryManageAction
//                    .findTempTransferFactoryCommodityInfoByParentToACT(
//                            new Request(CommonVars.getCurrUser()),
//                            this.parentList);
//        }
        JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            @Override
			public List InitColumns() {
                List list = new Vector();
                list.add(addColumn("选择", "isSelected", 100));
                list.add(addColumn("归并序号", "t.seqNum", 100));
                list.add(addColumn("商品编码", "t.complex", 90));
                list.add(addColumn("商品名称", "t.commName", 120));
                list.add(addColumn("规格型号", "t.commSpec", 120));
                list.add(addColumn("单位", "t.unit.name", 60));
                list.add(addColumn("数量", "t.quantity", 60));
                list.add(addColumn("转厂数量", "t.transFactAmount", 60));
                list.add(addColumn("单价", "t.unitPrice", 60));
                list.add(addColumn("总价", "t.totalPrice", 60));
                list.add(addColumn("币制", "t.curr.name", 60));
                list.add(addColumn("毛重", "t.grossWeight", 60));
                list.add(addColumn("净重", "t.netWeight", 60));
                list.add(addColumn("产销国", "t.country.name", 60));
                return list;
            }
        };
        jTableListModelAdapter.setEditableColumn(1);
        tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
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

        @Override
		public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            if (value == null) {
                return this;
            }
            if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                checkBox.setSelected(Boolean.parseBoolean(value.toString()));
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
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

        @Override
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
            cb.setHorizontalAlignment(SwingConstants.CENTER);
            cb.addActionListener(this);
            this.table = table;
            return cb;
        }

        @Override
		public Object getCellEditorValue() {
            cb.removeActionListener(this);
            return cb;
        }

        public void actionPerformed(ActionEvent e) {
            JTableListModel tableModel = (JTableListModel) this.table
                    .getModel();
            Object obj = tableModel.getCurrentRow();
            if (obj instanceof TempFptBillItem) {
                TempFptBillItem temp = (TempFptBillItem) obj;
                temp.setIsSelected(new Boolean(cb.isSelected()));
                tableModel.updateRow(obj);
            }
            fireEditingStopped();
        }

    }

    /**
     * 获得选中关封申请单中商品信息的信息
     */
    public List getCommodityList() {
        if (this.tableModel == null) {
            return null;
        }
        List list = tableModel.getList();
        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempFptBillItem) {
                TempFptBillItem temp = (TempFptBillItem) list
                        .get(i);
                if (temp.getIsSelected().booleanValue() == true) {
                    newList.add(temp);
                }
            }
        }
        return newList;
    }

    /**
     * 设置无效数据着色
     */
    public void setValidateDataColor(List checkList) {
        this.colorList = checkList;
        for (int i = 2; i < this.tableModel.getColumnCount(); i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(
                    new ColorTableCellRenderer());
        }
        //        jTable.getColumnModel().getColumn(1).setCellRenderer(
        //                new MultiRenderer());
        jTable.repaint();
        jTable.validate();
    }

    /**
     * render table color row
     */
    class ColorTableCellRenderer extends DefaultTableCellRenderer {
        @Override
		public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);
            if (checkValue(table, row, column)) {
                c.setBackground(Color.BLUE);
                c.setForeground(Color.WHITE);
            } else {
                if (isSelected) {
                    c.setForeground(table.getSelectionForeground());
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setForeground(table.getForeground());
                    c.setBackground(table.getBackground());
                }
            }
            return c;
        }
    }

    private boolean checkValue(JTable table, int row, int column) {
        if (colorList == null) {
            return false;
        }
        TempFptBillItem data = (TempFptBillItem) tableModel
                .getObjectByRow(row);
        for (int i = 0; i < colorList.size(); i++) {
            TempFptBillItem c = (TempFptBillItem) colorList
                    .get(i);
            if (data.getT().getId().equals(c.getT().getId()) == true) {
                return true;
            }
        }
        return false;
    }

}

