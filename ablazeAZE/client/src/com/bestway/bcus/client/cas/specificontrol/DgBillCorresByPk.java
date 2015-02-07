
/*
 * Created on 2005-11-14
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author wp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgBillCorresByPk extends JDialogBase {

    private javax.swing.JPanel jContentPane           = null;
    private JPanel             jPanel                 = null;
    private JButton            btnAutoCorresponding   = null;
    private CasAction          casAction              = null;
    private JButton            btnImportCorresponding = null;
    private JComboBox          cbbImpExpType          = null;
    private JLabel             jLabel                 = null;
    private JTableListModel    tableModelDetail       = null;
    private JSplitPane         jSplitPane1            = null;
    private JPanel             jPanel2                = null;
    private JPanel             jPanel3                = null;
    private JTable             tbTop                  = null;
    private JScrollPane        jScrollPane            = null;
    private JTable             tbBottom               = null;
    private JScrollPane        jScrollPane1           = null;
    private JTableListModel    tableModelBill         = null;
    private JButton            btnExit                = null;
    private JToolBar           jToolBar               = null;
    private JPanel             jPanel1                = null;
    private JToolBar           jToolBar1              = null;
    private BillType           billType               = null; // 单据类型对象
    private PnCommonQueryPage  pnCommonQueryPage      = null; // 公共查询
    private JCalendarComboBox  cbbBeginDate           = null;
    private JCalendarComboBox  cbbEndDate             = null;
    private JLabel             jLabel1                = null;
    private JLabel             jLabel2                = null;
	private Integer 		   maximumFractionDigits  = CasCommonVars.getOtherOption()
	.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
	.getOtherOption().getInOutMaximumFractionDigits();
    /**
     * This is the default constructor
     */
    public DgBillCorresByPk() {
        super(false);
        casAction = (CasAction) CommonVars.getApplicationContext().getBean(
                "casAction");
        initialize();
        this.cbbBeginDate.setDate(java.sql.Date
                .valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
                        .getYear()
                        + "-01-01"));
        this.cbbEndDate.setDate(java.sql.Date
                .valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
                        .getYear()
                        + "-12-31"));
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(755, 539);
        this.setContentPane(getJContentPane());
        this.setTitle("单据对应");
        initUIComponets();
        initTbTop(new ArrayList());
        initTbBottom(new ArrayList());

    }

    /**
     * 初始化组件
     */
    private void initUIComponets() {
        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
                this.cbbImpExpType);
        this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
                .getRender());

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
            jContentPane.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
            jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
        }
        return jContentPane;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jLabel2 = new JLabel();
            jLabel2.setBounds(new java.awt.Rectangle(416, 4, 14, 23));
            jLabel2.setForeground(new java.awt.Color(51, 51, 255));
            jLabel2.setText("到");
            jLabel1 = new JLabel();
            jLabel1.setBounds(new java.awt.Rectangle(187, 4, 137, 23));
            jLabel1.setForeground(new java.awt.Color(51, 51, 255));
            jLabel1.setText("申请单转报关单时间段:从");
            jLabel = new JLabel();
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jLabel.setBounds(10, 7, 49, 17);
            jLabel.setText("单据类型");
            jPanel.add(getCbbImpExpType(), null);
            jPanel.add(jLabel, null);
            jPanel.add(getCbbBeginDate(), null);
            jPanel.add(getCbbEndDate(), null);
            jPanel.add(jLabel1, null);
            jPanel.add(jLabel2, null);
        }
        return jPanel;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnAutoCorresponding() {
        if (btnAutoCorresponding == null) {
            btnAutoCorresponding = new JButton();
            btnAutoCorresponding.setText("直接出口对应");
            btnAutoCorresponding.setEnabled(false);
            btnAutoCorresponding
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            new autoCorrespondingThread().start();
                        }
                    });
        }
        return btnAutoCorresponding;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnImportCorresponding() {
        if (btnImportCorresponding == null) {
            btnImportCorresponding = new JButton();
            btnImportCorresponding.setText("直接进口对应");
            btnImportCorresponding.setEnabled(false);
            btnImportCorresponding
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            new importCorrespondingThread().start();
                        }
                    });
        }
        return btnImportCorresponding;
    }

    /**
     * This method initializes jComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCbbImpExpType() {
        if (cbbImpExpType == null) {
            cbbImpExpType = new JComboBox();
            cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
                    ImpExpType.DIRECT_IMPORT).toString(), "直接进口"));
            cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
                    ImpExpType.DIRECT_EXPORT).toString(), "直接出口"));
            this.cbbImpExpType.setSelectedItem(null);
            cbbImpExpType.setBounds(60, 4, 121, 23);
            cbbImpExpType.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Integer impExpType = Integer
                                .valueOf(((ItemProperty) cbbImpExpType
                                        .getSelectedItem()).getCode());// 单据类型代码
                        if (impExpType.intValue() == ImpExpType.DIRECT_IMPORT) {// 直接进口
                            setState(true);
                            DgBillCorresByPk.this.billType = casAction
                                    .findBillTypeByCode(new Request(CommonVars
                                            .getCurrUser()), "1003");// 直接进口类型
                        } else if (impExpType.intValue() == ImpExpType.DIRECT_EXPORT) {// 直接出口
                            setState(false);
                            DgBillCorresByPk.this.billType = casAction
                                    .findBillTypeByCode(new Request(CommonVars
                                            .getCurrUser()), "2101");// 直接出口类型
                        }
                        pnCommonQueryPage.setInitState();
                    }
                }
            });
        }
        return cbbImpExpType;
    }

    /**
     * This method initializes jSplitPane1
     * 
     * @return javax.swing.JSplitPane
     */
    private JSplitPane getJSplitPane1() {
        if (jSplitPane1 == null) {
            jSplitPane1 = new JSplitPane();
            jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane1.setDividerLocation(180);
            jSplitPane1.setDividerSize(5);
            jSplitPane1.setTopComponent(getJPanel2());
            jSplitPane1.setBottomComponent(getJPanel3());
        }
        return jSplitPane1;
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
            jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return jPanel2;
    }

    /**
     * This method initializes jPanel3
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel3() {
        if (jPanel3 == null) {
            jPanel3 = new JPanel();
            jPanel3.setLayout(new BorderLayout());
            jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
        }
        return jPanel3;
    }

    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getTbTop() {
        if (tbTop == null) {
            tbTop = new JTable();
        }
        tbTop.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) {
                            return;
                        }
                        JTableListModel tableModel = (JTableListModel) tbTop
                                .getModel();

                        if (tableModel == null) {
                            return;
                        }
                        ListSelectionModel lsm = (ListSelectionModel) e
                                .getSource();
                        if (!lsm.isSelectionEmpty()) {
                            List detailList = new ArrayList();
                            BillMaster bm = (BillMaster) tableModel
                                    .getCurrentRow();
                            if (bm != null) {
                                detailList = casAction.findBillDetail(
                                        new Request(CommonVars.getCurrUser(),
                                                true), bm.getId(), billType
                                                .getBillType());
                            }
                            initTbBottom(detailList);
                        }
                    }
                });
        return tbTop;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTbTop());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jTable1
     * 
     * @return javax.swing.JTable
     */
    private JTable getTbBottom() {
        if (tbBottom == null) {
            tbBottom = new JTable();
        }
        return tbBottom;
    }

    /**
     * This method initializes jScrollPane1
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setViewportView(getTbBottom());
        }
        return jScrollPane1;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnExit() {
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
     * 设置状态
     * 
     * @param isImport
     */
    private void setState(boolean isImport) {
        btnImportCorresponding.setEnabled(isImport);
        btnAutoCorresponding.setEnabled(!isImport);
    }

    /**
     * 初始化 tbTop
     * 
     * @param list
     */
    private JTableListModel initTbTop(List list) {
        tableModelBill = new JTableListModel(tbTop, list,
                new JTableListModelAdapter() {
                    public List<JTableListColumn> InitColumns() {
                        List<JTableListColumn> list = new Vector<JTableListColumn>();
                        list.add(addColumn("类别", "billType.name", 100, false));
                        list.add(addColumn("单据号", "billNo", 100));
                        list.add(addColumn("客户名称", "scmCoc.name", 100));
                        list.add(addColumn("有效", "isValid", 50));
                        list.add(addColumn("生效日期", "validDate", 100));
                        list.add(addColumn("是否记帐", "keepAccounts", 50));
                        return list;
                    }
                });
        tbTop.getColumnModel().getColumn(4).setCellRenderer(
                new TableCheckBoxRender());
        tbTop.getColumnModel().getColumn(6).setCellRenderer(
                new TableCheckBoxRender());
        tbTop.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (list.size() > 0) {
            tbTop.setRowSelectionInterval(0, 0);
        } else {
            initTbBottom(new ArrayList());
        }
        return tableModelBill;
    }

    /**
     * 初始化tbBottom
     * 
     * @param list
     */
    private void initTbBottom(final List list) {
        tableModelDetail = new JTableListModel(tbBottom, list,
                new JTableListModelAdapter(maximumFractionDigits) {
                    public List<JTableListColumn> InitColumns() {
                        List<JTableListColumn> list = new Vector<JTableListColumn>();
                        list.add(addColumn("商品料号", "ptPart", 100));
                        list.add(addColumn("商品名称", "ptName", 100));
                        list.add(addColumn("商品规格", "ptSpec", 100));
                        list.add(addColumn("商品版本", "version", 100));
                        list.add(addColumn("仓库名称", "wareSet.name", 100));
                        list.add(addColumn("仓库代码", "wareSet.code", 80));
                        list.add(addColumn("数量", "ptAmount", 60));
                        list.add(addColumn("单价", "ptPrice", 60));
                        list.add(addColumn("金额", "money", 60));
                        list.add(addColumn("制单号", "productNo", 80));
                        list.add(addColumn("报关单号", "customNo", 80));
                        list.add(addColumn("对应报关数量", "customNum", 120));
                        list.add(addColumn("商品海关编码", "complex.code", 120));
                        list.add(addColumn("报关商品名称", "hsName", 120));
                        list.add(addColumn("报关商品规格 ", "hsSpec", 120));
                        list.add(addColumn("折算报关数量", "hsAmount", 120));
                        list.add(addColumn("报关单价", "hsPrice", 80));
                        list.add(addColumn("备注", "note", 80));
                        return list;
                    }
                });
    }

    /**
     * 直接进口对应
     * 
     * @author Administrator
     * 
     */
    class importCorrespondingThread extends Thread {
        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID(); 
            final String flag = uuid.toString() ;
            try {
                Date beginDate = cbbBeginDate.getDate();
                Date endDate = cbbEndDate.getDate();
                if (beginDate != null && endDate != null) {
                    if (beginDate.after(endDate)) {
                        JOptionPane.showMessageDialog(DgBillCorresByPk.this,
                                "开始日期大于结束日期!!", "提示",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                CommonProgress.showProgressDialog(flag,DgBillCorresByPk.this);
                CommonProgress.setMessage(flag,"系统正获取数据，请稍后...");
                btnImportCorresponding.setEnabled(false);
                casAction.saveBillCorresByContainer(new Request(CommonVars
                        .getCurrUser()), beginDate, endDate);
                CommonProgress.closeProgressDialog(flag);
            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane.showMessageDialog(DgBillCorresByPk.this, "获取数据失败：！"
                        + e.getMessage(), "提示", 2);
            }
            btnImportCorresponding.setEnabled(true);
        }
    }

    /**
     * 自动对应
     * 
     * @author Administrator
     * 
     */
    class autoCorrespondingThread extends Thread {
        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID(); 
            final String flag = uuid.toString() ;
            try {
                Date beginDate = cbbBeginDate.getDate();
                Date endDate = cbbEndDate.getDate();
                if (beginDate != null && endDate != null) {
                    if (beginDate.after(endDate)) {
                        JOptionPane.showMessageDialog(DgBillCorresByPk.this,
                                "开始日期大于结束日期!!", "提示",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                CommonProgress.showProgressDialog(flag,DgBillCorresByPk.this);
                CommonProgress.setMessage(flag,"系统正获取数据，请稍后...");
                btnAutoCorresponding.setEnabled(false);
                casAction.saveBillCorresByPK(new Request(CommonVars.getCurrUser()),
                        beginDate, endDate);
                CommonProgress.closeProgressDialog(flag);
            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane.showMessageDialog(DgBillCorresByPk.this,
                        "获取数据失败！！！" + e.getMessage(), "提示", 2);
            }
            btnAutoCorresponding.setEnabled(true);
        }
    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.add(getJPanel());
            jToolBar.add(getBtnImportCorresponding());
            jToolBar.add(getBtnAutoCorresponding());
            jToolBar.add(getBtnExit());
        }
        return jToolBar;
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
            jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
            jPanel1.add(getJToolBar1(), java.awt.BorderLayout.CENTER);
        }
        return jPanel1;
    }

    /**
     * This method initializes jToolBar1
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar1() {
        if (jToolBar1 == null) {
            jToolBar1 = new JToolBar();
            jToolBar1.add(getPnCommonQueryPage());
        }
        return jToolBar1;
    }

    /**
     * 获得数据源
     * 
     * @param index
     * @param length
     * @param property
     * @param value
     * @param isLike
     * @return
     */
    public List getDataSource(int index, int length, String property,
            Object value, boolean isLike) {
        if (billType == null) {
            return new ArrayList();
        }
        return this.casAction.findBillMaster(new Request(CommonVars
                .getCurrUser()), billType, index, length, property, value,
                isLike);
    }

    /**
     * 公共查询组件
     * 
     * @return
     */
    private PnCommonQueryPage getPnCommonQueryPage() {
        if (pnCommonQueryPage == null) {
            pnCommonQueryPage = new PnCommonQueryPage() {

                @Override
                public JTableListModel initTable(List dataSource) {
                    return DgBillCorresByPk.this.initTbTop(dataSource);
                }

                @Override
                public List getDataSource(int index, int length,
                        String property, Object value, boolean isLike) {
                    return DgBillCorresByPk.this.getDataSource(index, length,
                            property, value, isLike);
                }

            };
        }
        return pnCommonQueryPage;
    }

    /**
     * This method initializes cbbBeginDate
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getCbbBeginDate() {
        if (cbbBeginDate == null) {
            cbbBeginDate = new JCalendarComboBox();
            cbbBeginDate.setBounds(new java.awt.Rectangle(326, 4, 86, 23));
        }
        return cbbBeginDate;
    }

    /**
     * This method initializes jCalendarComboBox
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getCbbEndDate() {
        if (cbbEndDate == null) {
            cbbEndDate = new JCalendarComboBox();
            cbbEndDate.setBounds(new java.awt.Rectangle(430, 4, 86, 23));
        }
        return cbbEndDate;
    }

}
