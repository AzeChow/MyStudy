/*
 * Created on 2004-10-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.CheckDetail;
import com.bestway.bcus.cas.entity.CheckMaster;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceCheckEdit extends JDialogBase {

    private javax.swing.JPanel   jContentPane         = null;

    private JSplitPane           jSplitPane           = null;
    private JPanel               jPanel               = null;
    private JPanel               jPanel1              = null;
    private JComboBox            jComboBox            = null;
    private JButton              jButton1             = null;
    private JButton              jButton3             = null;
    private MaterialManageAction materialManageAction = null;
    private CasAction            casAction            = null;
    private JTableListModel      tableModel           = null;
    private List                 list                 = null;
    private String               materielType         = null;
    private String               materielTypeCode     = null;
    private JToolBar             jToolBar             = null;
    private JButton              jButton5             = null;
    private JButton              jButton6             = null;
    private JButton              jButton7             = null;
    private JButton              jButton8             = null;
    private JTable               jTable               = null;
    private JScrollPane          jScrollPane          = null;
    private JTextField           jTextField           = null;
    private JCalendarComboBox    jCalendarComboBox    = null;
    private JTextField           jTextField1          = null;
    private JTextField           jTextField2          = null;
    private JTableListModel      tableModelMaster     = null;
    private CheckMaster          checkMaster          = null;
    private int                  dataState            = DataState.EDIT;
    private JButton              jButton              = null;
    private String               reportTitle          = null;

    /**
     * This is the default constructor
     */
    public DgFinanceCheckEdit() {
        super();
        materialManageAction = (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
        casAction = (CasAction) CommonVars.getApplicationContext().getBean(
                "casAction");
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(733, 541);
        this.setTitle("盘点资料");
        this.setContentPane(getJContentPane());
       
    }
    
    
    public void setVisible(boolean isFlag){
        if(isFlag){
            initUI();
            checkMaster = (CheckMaster) tableModelMaster.getCurrentRow();
            list = casAction.findCheckDetail(new Request(CommonVars
                    .getCurrUser()), checkMaster);
            if (list != null && !list.isEmpty()) {
                initTable(list);
            } else {
                initTable(new Vector());
            }
            initData();
            state();
        }
        super.setVisible(isFlag);
    }

    private void initData() {
        this.jTextField.setText(String.valueOf(checkMaster.getNum()));
        this.jTextField1.setText(this.getMaterielType());
        this.setReportTitle(this.getMaterielType() + "库存盘点表");
        if (checkMaster.getEndDate() != null) {
            this.jCalendarComboBox.setDate(CommonVars
                    .dateToStandDate(checkMaster.getEndDate()));
        }
        this.jTextField2.setText(checkMaster.getNote());
        if (checkMaster.getWareSet() != null) {
            this.jComboBox.setSelectedItem(checkMaster.getWareSet());
        } else {
            this.jComboBox.setSelectedItem(null);
        }
    }

    private void initTable(final List list) {
        tableModel = new JTableListModel(jTable, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("商品编号", "ptPart", 80));
                        list.add(addColumn("品名", "ptName", 100));
                        list.add(addColumn("规格", "ptSpec", 100));
                        list.add(addColumn("单位", "ptUnit", 80));
                        list.add(addColumn("库存数量", "ptAmount", 100));
                        list.add(addColumn("实际库存数量", "factPtAmount", 100));
                        list.add(addColumn("差值", "removeAmount", 100));
                        return list;
                    }
                });
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(new java.awt.BorderLayout());
            jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * 
     * This method initializes jSplitPane
     * 
     * 
     * 
     * @return javax.swing.JSplitPane
     * 
     */
    private JSplitPane getJSplitPane() {
        if (jSplitPane == null) {
            jSplitPane = new JSplitPane();
            jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
            jSplitPane.setDividerSize(2);
            jSplitPane.setDividerLocation(100);
            jSplitPane.setTopComponent(getJPanel());
            jSplitPane.setBottomComponent(getJPanel1());
        }
        return jSplitPane;
    }

    /**
     * 
     * This method initializes jPanel
     * 
     * 
     * 
     * @return javax.swing.JPanel
     * 
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            javax.swing.JLabel jLabel4 = new JLabel();

            javax.swing.JLabel jLabel3 = new JLabel();

            javax.swing.JLabel jLabel2 = new JLabel();

            javax.swing.JLabel jLabel1 = new JLabel();

            javax.swing.JLabel jLabel = new JLabel();

            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    null, null));
            jPanel.setBackground(new java.awt.Color(238, 238, 238));
            jLabel.setBounds(27, 11, 65, 21);
            jLabel.setText("盘点序号");
            jLabel1.setBounds(246, 11, 64, 23);
            jLabel1.setText("截止日期");
            jLabel2.setBounds(27, 41, 65, 20);
            jLabel2.setText("单据类型");
            jLabel3.setBounds(246, 41, 67, 23);
            jLabel3.setText("仓库名称");
            jLabel4.setBounds(26, 72, 47, 20);
            jLabel4.setText("备注");
            jPanel.add(getJComboBox(), null);
            jPanel.add(jLabel, null);
            jPanel.add(getJTextField(), null);
            jPanel.add(jLabel1, null);
            jPanel.add(getJCalendarComboBox(), null);
            jPanel.add(jLabel2, null);
            jPanel.add(getJTextField1(), null);
            jPanel.add(jLabel3, null);
            jPanel.add(jLabel4, null);
            jPanel.add(getJTextField2(), null);
            jPanel.add(getJButton1(), null);
            jPanel.add(getJButton3(), null);
            jPanel.add(getJButton(), null);
        }
        return jPanel;
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
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
            jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return jPanel1;
    }

    /**
     * 
     * This method initializes jComboBox
     * 
     * 
     * 
     * @return javax.swing.JComboBox
     * 
     */
    private JComboBox getJComboBox() {
        if (jComboBox == null) {
            jComboBox = new JComboBox();
            jComboBox.setBounds(316, 41, 109, 22);

        }
        return jComboBox;
    }

    /**
     * 
     * This method initializes jButton1
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton1() {
        if (jButton1 == null) {
            jButton1 = new JButton();
            jButton1.setBounds(457, 40, 75, 23);
            jButton1.setText("保存");
            jButton1.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    checkMaster
                            .setEndDate(DgFinanceCheckEdit.this.jCalendarComboBox
                                    .getDate());
                    checkMaster
                            .setWareSet(((WareSet) DgFinanceCheckEdit.this.jComboBox
                                    .getSelectedItem()));
                    checkMaster.setNote(jTextField2.getText());
                    checkMaster = casAction.saveCheckMasters(new Request(
                            CommonVars.getCurrUser()), checkMaster);
                    tableModelMaster.updateRow(checkMaster);
                    dataState = DataState.BROWSE;
                    state();
                }
            });

        }
        return jButton1;
    }

    private void initUI() {
        // 初始化仓库
        List list = materialManageAction.findWareSet(new Request(CommonVars
                .getCurrUser()));
        this.jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
        this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
                "code", "name"));
        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
                this.jComboBox, "code", "name");
        Date date = new Date();
        this.jCalendarComboBox.setDate(CommonVars.dateToStandDate(date));
        this.jComboBox.setSelectedIndex(-1);
    }

    /**
     * 
     * This method initializes jButton3
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton3() {
        if (jButton3 == null) {
            jButton3 = new JButton();
            jButton3.setBounds(457, 71, 75, 23);
            jButton3.setText("关闭");
            jButton3.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    DgFinanceCheckEdit.this.dispose();

                }
            });

        }
        return jButton3;
    }

    /**
     * @return Returns the materielType.
     */
    public String getMaterielType() {
        return materielType;
    }

    /**
     * @param materielType
     *            The materielType to set.
     */
    public void setMaterielType(String materielType) {
        this.materielType = materielType;
    }

    /**
     * 
     * This method initializes jToolBar
     * 
     * 
     * 
     * @return javax.swing.JToolBar
     * 
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.add(getJButton5());
            jToolBar.add(getJButton6());
            jToolBar.add(getJButton7());
            jToolBar.add(getJButton8());
        }
        return jToolBar;
    }

    /**
     * 
     * This method initializes jButton5
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton5() {
        if (jButton5 == null) {
            jButton5 = new JButton();
            jButton5.setText("获取商品");
            jButton5.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    List tempList = null;
                    casAction.deleteCheckDetail(new Request(CommonVars
                            .getCurrUser()), checkMaster);
                    tableModel.getList().clear();
                    tableModel.setList(null);
                    if (DgFinanceCheckEdit.this.jComboBox.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(DgFinanceCheckEdit.this,
                                "请选择仓库", "提示！", 2);
                        return;
                    }

                    String className = BillUtil
                            .getBillDetailTableNameByMaterielType(DgFinanceCheckEdit.this
                                    .getMaterielTypeCode());

                    String inSelects = "select a.ptPart,a.ptName,a.ptSpec,a.ptUnit.name,sum(a.ptAmount)";
                    String groupBy = "group by a.ptPart,a.ptName,a.ptSpec,a.ptUnit.name order by a.ptPart";
                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(new Condition("and", null,
                            "billMaster.keepAccounts", "=", new Boolean(true),
                            null));
                    conditions.add(new Condition("and", null,
                            "wareSet.code", "=",
                            ((WareSet) DgFinanceCheckEdit.this.jComboBox
                                    .getSelectedItem()).getCode(), null));
                    
                    conditions.add(new Condition("and", null,
                            "billMaster.validDate", ">=", java.sql.Date
                                    .valueOf("2004-01-01"), null));// 开始日期
                    if (jCalendarComboBox.getDate() != null) // 结束日期
                        conditions.add(new Condition("and", null,
                                "billMaster.validDate", "<=", CommonVars
                                        .dateToStandDate(jCalendarComboBox
                                                .getDate()), null));
                    tempList = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), inSelects, className, conditions,
                            groupBy,null);
                    if (tempList != null && !tempList.isEmpty()) {
                        for (int i = 0; i < tempList.size(); i++) {
                            CheckDetail checkDetail = new CheckDetail();
                            Object[] obj = (Object[]) tempList.get(i);
                            checkDetail.setPtPart((String) (obj[0]));
                            checkDetail.setPtName((String) (obj[1]));
                            checkDetail.setPtSpec((String) (obj[2]));
                            checkDetail.setPtUnit((String) (obj[3]));
                            checkDetail.setCheckMaster(checkMaster);
                            checkDetail.setCompany(CommonVars.getCurrUser()
                                    .getCompany());
                            checkDetail.setPtAmount(CommonVars
                                    .formatDouble((Double) (obj[4])));
                            checkDetail.setFactPtAmount(new Double(0));
                            checkDetail.setRemoveAmount(CommonVars
                                    .formatDouble((Double) (obj[4])));
                            casAction.saveCheckDetails(new Request(CommonVars
                                    .getCurrUser()), checkDetail);
                        }
                    }
                    list = casAction.findCheckDetail(new Request(CommonVars
                            .getCurrUser()), checkMaster);
                    if (list != null && !list.isEmpty()) {
                        initTable(list);
                    } else {
                        initTable(new Vector());
                        JOptionPane.showMessageDialog(DgFinanceCheckEdit.this,
                                "没有商品可以获得。", "提示！", 2);

                    }
                }
            });

        }
        return jButton5;
    }

    /**
     * 
     * This method initializes jButton6
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton6() {
        if (jButton6 == null) {
            jButton6 = new JButton();
            jButton6.setText("修改商品");
            jButton6.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    if (tableModel.getCurrentRow() == null) {
                        JOptionPane.showMessageDialog(DgFinanceCheckEdit.this,
                                "请选择你将要修改的记录", "提示！", 0);
                        return;
                    }
                    DgFinanceCheckEditEdit dg = new DgFinanceCheckEditEdit();
                    dg.setTableModel(tableModel);
                    dg.setVisible(true);
                }
            });

        }
        return jButton6;
    }

    /**
     * 
     * This method initializes jButton7
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton7() {
        if (jButton7 == null) {
            jButton7 = new JButton();
            jButton7.setText("清空商品");
            jButton7.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    casAction.deleteCheckDetail(new Request(CommonVars
                            .getCurrUser()), checkMaster);
                    initTable(new Vector());

                }
            });

        }
        return jButton7;
    }

    /**
     * 
     * This method initializes jButton8
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton8() {
        if (jButton8 == null) {
            jButton8 = new JButton();
            jButton8.setText("打印");
            jButton8.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (list != null && !list.isEmpty()) {
                        CommonDataSource imgExgDS = new CommonDataSource(list);
                        List company = new Vector();
                        company.add(CommonVars.getCurrUser().getCompany());
                        CommonDataSource companyDS = new CommonDataSource(
                                company);

                        InputStream masterReportStream = DgFinanceCheckEdit.class
                                .getResourceAsStream("report/FinanceCheckReport.jasper");
                        InputStream detailReportStream = DgFinanceCheckEdit.class
                                .getResourceAsStream("report/FinanceCheckReportSubb.jasper");
                        try {
                            JasperReport detailReport = (JasperReport) JRLoader
                                    .loadObject(detailReportStream);

                            Map parameters = new HashMap();
                            parameters.put("imgExgDS", imgExgDS);
                            parameters.put("detailReport", detailReport);
                            parameters.put("title", DgFinanceCheckEdit.this
                                    .getReportTitle());
                            parameters
                                    .put(
                                            "riqi",
                                            CommonVars
                                                    .dateToString(DgFinanceCheckEdit.this.jCalendarComboBox
                                                            .getDate()));
                            if (DgFinanceCheckEdit.this.jComboBox
                                    .getSelectedItem() != null)
                                parameters
                                        .put(
                                                "wareSet",
                                                ((WareSet) DgFinanceCheckEdit.this.jComboBox
                                                        .getSelectedItem())
                                                        .getName());
                            else
                                parameters.put("wareSet", "");
                            JasperPrint jasperPrint;
                            jasperPrint = JasperFillManager.fillReport(
                                    masterReportStream, parameters, companyDS);
                            DgReportViewer viewer = new DgReportViewer(
                                    jasperPrint);
                            viewer.setVisible(true);
                        } catch (JRException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            });

        }
        return jButton8;
    }

    /**
     * 
     * This method initializes jTable
     * 
     * 
     * 
     * @return javax.swing.JTable
     * 
     */
    private JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();
        }
        return jTable;
    }

    /**
     * 
     * This method initializes jScrollPane
     * 
     * 
     * 
     * @return javax.swing.JScrollPane
     * 
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

    /**
     * 
     * This method initializes jTextField
     * 
     * 
     * 
     * @return javax.swing.JTextField
     * 
     */
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setEditable(false);
            jTextField.setBounds(105, 11, 92, 22);
        }
        return jTextField;
    }

    /**
     * 
     * This method initializes jCalendarComboBox
     * 
     * 
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     * 
     */
    private JCalendarComboBox getJCalendarComboBox() {
        if (jCalendarComboBox == null) {
            jCalendarComboBox = new JCalendarComboBox();
            jCalendarComboBox.setBounds(316, 11, 108, 22);
        }
        return jCalendarComboBox;
    }

    /**
     * 
     * This method initializes jTextField1
     * 
     * 
     * 
     * @return javax.swing.JTextField
     * 
     */
    private JTextField getJTextField1() {
        if (jTextField1 == null) {
            jTextField1 = new JTextField();
            jTextField1.setEditable(false);
            jTextField1.setBounds(105, 41, 89, 22);
        }
        return jTextField1;
    }

    /**
     * 
     * This method initializes jTextField2
     * 
     * 
     * 
     * @return javax.swing.JTextField
     * 
     */
    private JTextField getJTextField2() {
        if (jTextField2 == null) {
            jTextField2 = new JTextField();
            jTextField2.setBounds(104, 71, 323, 22);
        }
        return jTextField2;
    }

    /**
     * @return Returns the tableModelMaster.
     */
    public JTableListModel getTableModelMaster() {
        return tableModelMaster;
    }

    /**
     * @param tableModelMaster
     *            The tableModelMaster to set.
     */
    public void setTableModelMaster(JTableListModel tableModelMaster) {
        this.tableModelMaster = tableModelMaster;
    }

    /**
     * @return Returns the checkMaster.
     */
    public CheckMaster getCheckMaster() {
        return checkMaster;
    }

    /**
     * @param checkMaster
     *            The checkMaster to set.
     */
    public void setCheckMaster(CheckMaster checkMaster) {
        this.checkMaster = checkMaster;
    }

    private void state() {
        this.jCalendarComboBox.setEnabled(!(dataState == DataState.BROWSE));
        this.jTextField2.setEditable(!(dataState == DataState.BROWSE));
        this.jComboBox.setEnabled(!(dataState == DataState.BROWSE));
        this.jButton.setEnabled((dataState == DataState.BROWSE)); // 修改
        this.jButton1.setEnabled(!(dataState == DataState.BROWSE)); // 保存
        this.jButton5.setEnabled((dataState == DataState.BROWSE));
        this.jButton6.setEnabled((dataState == DataState.BROWSE));
        this.jButton7.setEnabled((dataState == DataState.BROWSE));
        this.jButton8.setEnabled((dataState == DataState.BROWSE));
    }

    /**
     * 
     * This method initializes jButton
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setBounds(457, 10, 75, 23);
            jButton.setText("修改");
            jButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dataState = DataState.EDIT;
                    state();
                }
            });

        }
        return jButton;
    }

    /**
     * @return Returns the materielTypeCode.
     */
    public String getMaterielTypeCode() {
        return materielTypeCode;
    }

    /**
     * @param materielTypeCode
     *            The materielTypeCode to set.
     */
    public void setMaterielTypeCode(String materielTypeCode) {
        this.materielTypeCode = materielTypeCode;
    }

    /**
     * @return Returns the reportTitle.
     */
    public String getReportTitle() {
        return reportTitle;
    }

    /**
     * @param reportTitle
     *            The reportTitle to set.
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
