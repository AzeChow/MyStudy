/*
 * Created on 2004-8-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmCustomsDeclarationDiffAnalyse extends JInternalFrameBase {

    private JPanel          jContentPane                 = null;
    private JTabbedPane     jTabbedPane                  = null;
    private JPanel          jPanel1                      = null;
    private JTable          tbDiffrenceAnalyseCommInfo   = null;
    private JScrollPane     jScrollPane                  = null;
    private JSplitPane      jSplitPane                   = null;
    private JTable          tbCustomsDeclaration         = null;
    private JScrollPane     jScrollPane1                 = null;
    private JTable          tbCustomsDeclarationBillList = null;
    private JScrollPane     jScrollPane2                 = null;
    private JTableListModel tableModel                   = null;
    private EncAction       encAction                    = null;
    private boolean         selectedFirstRow             = true;

    /**
     * This method initializes
     *  
     */
    public FmCustomsDeclarationDiffAnalyse() {
        super();
        initialize();
        encAction = (EncAction) CommonVars.getApplicationContext().getBean(
                "encAction");
    }

    public void setVisible(boolean isFlag) {
        if (isFlag) {
            tbCustomsDeclaration.getSelectionModel().addListSelectionListener(
                    new ListSelectionListener() {
                        public void valueChanged(ListSelectionEvent e) {
                            if (e.getValueIsAdjusting()) {
                                return;
                            }
                        	tableModel = (JTableListModel)tbCustomsDeclaration.getModel();
                            if (tableModel == null) {
                                return;
                            }                           
                            ListSelectionModel lsm = (ListSelectionModel) e
                                    .getSource();
                            if (!lsm.isSelectionEmpty()) {
                                //			                    int selectedRow = lsm.getMinSelectionIndex();
                                initTbCustomsDeclarationBillList();
                            }
                        }
                    });
            initTbCustomsDeclaration();
        }
        super.setVisible(isFlag);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setContentPane(getJContentPane());
        this.setTitle("大小清单差异分析");
        this.setSize(623, 408);

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
            jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
            jTabbedPane.addTab("报关清单信息", null, getJSplitPane(), null);
            jTabbedPane.addTab("数量差异分析", null, getJPanel1(), null);
            jTabbedPane
                    .addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent e) {
                            if (jTabbedPane.getSelectedIndex() == 0) {
                                //						initTbCustomsDeclarationBillList();
                            } else if (jTabbedPane.getSelectedIndex() == 1) {
                                initTbDiffrenceAnalyseCommInfo();
                            }
                        }
                    });
            jSplitPane.setTopComponent(getJScrollPane1());
            jSplitPane.setBottomComponent(getJScrollPane2());
        }
        return jTabbedPane;
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
            jPanel1
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "商品差异分析表",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    null, null));
            jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return jPanel1;
    }

    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getTbDiffrenceAnalyseCommInfo() {
        if (tbDiffrenceAnalyseCommInfo == null) {
            tbDiffrenceAnalyseCommInfo = new JTable();
        }
        return tbDiffrenceAnalyseCommInfo;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTbDiffrenceAnalyseCommInfo());
        }
        return jScrollPane;
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
            jSplitPane.setDividerLocation(192);
            jSplitPane.setDividerSize(8);
            jSplitPane.setTopComponent(getJScrollPane1());
            jSplitPane.setBottomComponent(getJScrollPane2());
        }
        return jSplitPane;
    }

    /**
     * This method initializes jTable1
     * 
     * @return javax.swing.JTable
     */
    private JTable getTbCustomsDeclaration() {
        if (tbCustomsDeclaration == null) {
            tbCustomsDeclaration = new JTable();
            tbCustomsDeclaration
                    .addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                            initTbCustomsDeclarationBillList();
                        }
                    });
        }
        return tbCustomsDeclaration;
    }

    /**
     * This method initializes jScrollPane1
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "选择您要进行差异分析的报关清单",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12),
                                    new java.awt.Color(51, 51, 51)));
            jScrollPane1.setViewportView(getTbCustomsDeclaration());
        }
        return jScrollPane1;
    }

    /**
     * This method initializes jTable2
     * 
     * @return javax.swing.JTable
     */
    private JTable getTbCustomsDeclarationBillList() {
        if (tbCustomsDeclarationBillList == null) {
            tbCustomsDeclarationBillList = new JTable();
        }
        return tbCustomsDeclarationBillList;
    }

    /**
     * This method initializes jScrollPane2
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane2() {
        if (jScrollPane2 == null) {
            jScrollPane2 = new JScrollPane();
            jScrollPane2
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "报关清单对应报关单列表",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12),
                                    new java.awt.Color(51, 51, 51)));
            jScrollPane2.setViewportView(getTbCustomsDeclarationBillList());
        }
        return jScrollPane2;
    }

    /**
     * 获取数据源-->查找已转报关单的报关清单
     */
    private List getDataSource() {
        List list = null;
        list = encAction.findBillListMakedCustomsDeclaration(new Request(
                CommonVars.getCurrUser()));
        return list;
    }

    /**
     * 初始化已转报关单的报关清单Table1
     */
    private void initTbCustomsDeclaration() {
        List list = this.getDataSource();
        tableModel = new JTableListModel(tbCustomsDeclaration, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("进出口类型", "impExpType", 100));
                        list.add(addColumn("清单状态", "listState", 100));
                        list.add(addColumn("电子帐册编号", "emsHeadH2k", 80));
                        list.add(addColumn("清单编号", "listNo", 100));
                        list.add(addColumn("经营单位编码", "tradeCode",
                                100));
                        list
                                .add(addColumn("经营单位名称",
                                        "tradeName", 50));
                        list.add(addColumn("商品项数", "materialNum", 50));
                        list.add(addColumn("清单申报日期", "listDeclareDate", 50));
                        list.add(addColumn("报关单申报日期", "customsDeclarationDate",
                                50));
                        list.add(addColumn("报关单预录入编号",
                                "customsDeclarationCode", 50));
                        list.add(addColumn("录入日期", "createdDate", 50));
                        list
                                .add(addColumn("录入员名称", "createdUser.userName",
                                        50));
                        list.add(addColumn("进出口岸", "impExpCIQ.name", 50));
                        list.add(addColumn("申报地海关", "declareCIQ.name", 50));
                        list
                                .add(addColumn("料件/成品标志",
                                        "materielProductFlag", 50));
                        list.add(addColumn("运输方式", "transportMode.name", 50));
                        list.add(addColumn("监管方式", "tradeMode.name", 50));
                        list
                                .add(addColumn("录入单位代号", "createdCompany.code",
                                        50));
                        list
                                .add(addColumn("录入单位名称", "createdCompany.name",
                                        50));
                        list.add(addColumn("备注", "memos", 50));
                        return list;
                    }
                });
        tbCustomsDeclaration.getColumnModel().getColumn(1).setCellRenderer(
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        String str = "";
                        if (value != null) {
                            switch (Integer.parseInt(value.toString())) {
                            case ImpExpType.DIRECT_IMPORT:
                                str = "料件进口";
                                break;
                            case ImpExpType.TRANSFER_FACTORY_IMPORT:
                                str = "料件转厂";
                                break;
                            case ImpExpType.BACK_FACTORY_REWORK:
                                str = "退厂返工";
                                break;
                            case ImpExpType.GENERAL_TRADE_IMPORT:
                                str = "一般贸易进口";
                                break;
                            case ImpExpType.DIRECT_EXPORT:
                                str = "成品出口";
                                break;
                            case ImpExpType.TRANSFER_FACTORY_EXPORT:
                                str = "转厂出口";
                                break;
                            case ImpExpType.BACK_MATERIEL_EXPORT:
                                str = "退料出口";
                                break;
                            case ImpExpType.REWORK_EXPORT:
                                str = "返工复出";
                                break;
                            case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
                                str = "边角料退港";
                                break;
                            case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
                                str = "边角料内销";
                                break;
                            case ImpExpType.GENERAL_TRADE_EXPORT:
                                str = "一般贸易出口";
                                break;
                            }
                        }
                        this.setText(str);
                        return this;
                    }
                });
        tbCustomsDeclaration.getColumnModel().getColumn(2).setCellRenderer(
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        String str = "";
                        if (value != null) {
                            switch (Integer.parseInt(value.toString())) {
                            case ApplyToCustomsBillList.DRAFT:
                                str = "草稿";
                                break;
                            case ApplyToCustomsBillList.ALREADY_SEND:
                                str = "审批通过";
                                break;
                            case ApplyToCustomsBillList.PASSED:
                                str = "审批未通过";
                                break;
//                            case ApplyToCustomsBillList.GENERATED_CUSTOMS_DECLARATION:
//                                str = "已转报关单";
//                                break;
                            }
                        }
                        this.setText(str);
                        return this;
                    }
                });
        tbCustomsDeclaration.getColumnModel().getColumn(15).setCellRenderer(
                new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        String str = "";
                        if (value != null) {
                            String tempStr = value.toString();
                            if (tempStr.equals(MaterielType.MATERIEL)) {
                                str = "料件";
                            } else if (tempStr
                                    .equals(MaterielType.FINISHED_PRODUCT)) {
                                str = "成品";
                            }
                        }
                        this.setText(str);
                        return this;
                    }
                });
    }

    /**
     * 初始化报关清单表选择行
     */
    private void initSelectedRow(int row) {
        if (tbCustomsDeclaration.getRowCount() > row && row >= 0) {
            tbCustomsDeclaration.setRowSelectionInterval(row, row);
        }
    }

    /**
     * 初始化报关清单表
     */
    private void initTbCustomsDeclarationBillList() {
        List list = new ArrayList();
        if (this.tableModel.getCurrentRow() != null) {
            list = this.encAction.findCustomsDeclarationByBillList(new Request(
                    CommonVars.getCurrUser()),
                    (ApplyToCustomsBillList) this.tableModel.getCurrentRow());
        }
        JTableListModel tableModel = new JTableListModel(
                tbCustomsDeclarationBillList, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("流水号", "serialNumber", 50));
                        list.add(addColumn("报关单号", "customsDeclarationCode",
                                100));
                        list.add(addColumn("申报日期", "declarationDate", 100));
                        list.add(addColumn("是否生效", "effective", 80));
                        list.add(addColumn("是否作废", "cancel", 80));
                        list.add(addColumn("转关确认", "confirmTransferCIQ", 100));
                        list.add(addColumn("帐册编号", "emsHeadH2k", 100));
                        //						list.add(addColumn("帐册流水号", "", 50));
                        //						list.add(addColumn("帐册协议号", "", 50));
                        list.add(addColumn("备注", "memos", 100));
                        list.add(addColumn("进口类型", "impExpType", 100));
                        list.add(addColumn("件数", "commodityNum", 100));
                        list.add(addColumn("毛重", "grossWeight", 100));
                        list.add(addColumn("净重", "netWeight", 100));
                        list.add(addColumn("进出口岸", "customs.name", 100));
                        list.add(addColumn("起运国",
                                "countryOfLoadingOrUnloading.name", 100));
                        list.add(addColumn("运输方式", "transferMode.name", 100));
                        list.add(addColumn("运输工具", "conveyance", 100));
                        list.add(addColumn("收货单位", "acceptGoodsCompany", 100));
                        list.add(addColumn("贸易方式", "tradeMode.name", 100));
                        list.add(addColumn("装货港",
                                "portOfLoadingOrUnloading.name", 100));
                        list.add(addColumn("境内目的地",
                                "domesticDestinationOrSource.name", 100));
                        list.add(addColumn("集装箱号", "containerNum", 100));
                        list.add(addColumn("用途", "uses.name", 100));
                        list
                                .add(addColumn("经营单位", "tradeName",
                                        100));
                        return list;
                    }
                });
        tbCustomsDeclarationBillList.getColumnModel().removeColumn(
                tbCustomsDeclarationBillList.getColumnModel().getColumn(0));
        tbCustomsDeclarationBillList.getColumnModel().getColumn(2)
                .setCellRenderer(new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        String str = "";
                        if (value != null) {
                            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
                                    "yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = dateFormat.parse(value.toString());
                                str = dateFormat.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        this.setText(str);
                        return this;
                    }
                });
        tbCustomsDeclarationBillList.getColumnModel().getColumn(3)
                .setCellRenderer(new checkBoxRenderer());
        tbCustomsDeclarationBillList.getColumnModel().getColumn(4)
                .setCellRenderer(new checkBoxRenderer());
        tbCustomsDeclarationBillList.getColumnModel().getColumn(5)
                .setCellRenderer(new checkBoxRenderer());
        tbCustomsDeclarationBillList.getColumnModel().getColumn(8)
                .setCellRenderer(new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus, row, column);
                        String str = "";
                        if (value != null) {
                            switch (Integer.parseInt(value.toString())) {
                            case ImpExpType.DIRECT_IMPORT:
                                str = "料件进口";
                                break;
                            case ImpExpType.TRANSFER_FACTORY_IMPORT:
                                str = "料件转厂";
                                break;
                            case ImpExpType.BACK_FACTORY_REWORK:
                                str = "退厂返工";
                                break;
                            case ImpExpType.GENERAL_TRADE_IMPORT:
                                str = "一般贸易进口";
                                break;
                            }
                        }
                        this.setText(str);
                        return this;
                    }
                });
    }

    class checkBoxRenderer extends DefaultTableCellRenderer {
        JCheckBox checkBox = new JCheckBox();

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            if (value == null || value.equals("") == true) {
                checkBox.setSelected(false);
            } else if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                checkBox.setSelected(Boolean.parseBoolean(value.toString()));
            }
            checkBox.setHorizontalAlignment(JLabel.CENTER);
            checkBox.setBackground(table.getBackground());
            if (isSelected) {
                checkBox.setForeground(table.getSelectionForeground());
                checkBox.setBackground(table.getSelectionBackground());
            } else {
                checkBox.setForeground(table.getForeground());
                checkBox.setBackground(table.getBackground());
            }
            checkBox.setFocusable(hasFocus);
            return checkBox;
        }
    }

    /**
     * 初始化报商品信息差异表Table
     */
    private void initTbDiffrenceAnalyseCommInfo() {
        List list = new ArrayList();
        if (this.tableModel.getCurrentRow() != null) {
            list = this.encAction.findDiffrenceAnalyseCommInfo(new Request(
                    CommonVars.getCurrUser()),
                    (ApplyToCustomsBillList) this.tableModel.getCurrentRow());
        }
        JTableListModel tableModel = new JTableListModel(
                tbDiffrenceAnalyseCommInfo, list, new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("商品序号", "commSerialNo", 100));
                        list.add(addColumn("商品编号", "complex.code", 100));
                        list.add(addColumn("商品名称", "commName", 80));
                        list.add(addColumn("型号规格", "commSpec", 100));
                        list.add(addColumn("单位", "unit.name", 100));
                        list
                                .add(addColumn("报关清单归并后数量", "afterMergeAmount",
                                        100));
                        list.add(addColumn("报关单数量", "customsDelarationAmount",
                                100));
                        list.add(addColumn("差异数量", "diffrenceAmount", 100));
                        return list;
                    }
                });
        tbDiffrenceAnalyseCommInfo
                .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
}

//  @jve:decl-index=0:visual-constraint="10,10"
