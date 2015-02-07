/*
 * Created on 2004-10-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceSellSum extends JDialogBase {

    private javax.swing.JPanel   jContentPane         = null;

    private JSplitPane           jSplitPane           = null;
    private JPanel               jPanel               = null;
    private JPanel               jPanel1              = null;
    private JTable               jTable               = null;
    private JScrollPane          jScrollPane          = null;
    private JCalendarComboBox    jCalendarComboBox    = null;
    private JCalendarComboBox    jCalendarComboBox1   = null;
    private JButton              jButton              = null;
    private JButton              jButton1             = null;
    private JButton              jButton2             = null;
    private CommonBaseCodeAction commonBaseCodeAction = null;
    private CasAction            casAction            = null;
    private JTableListModel      tableModel           = null;
    private List                 customLists          = null;

    /**
     * This is the default constructor
     */
    public DgFinanceSellSum() {
        super();
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
        this.setTitle("销售收入统计表");
        this.setSize(733, 541);
        this.setContentPane(getJContentPane());

    }

    public void setVisible(boolean isFlag) {
        if (isFlag) {
            DgFinanceSellSum.this.jCalendarComboBox
                    .setDate(java.sql.Date
                            .valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
                                    .getYear()
                                    + "-01-01"));
            DgFinanceSellSum.this.jCalendarComboBox1
                    .setDate(java.sql.Date
                            .valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
                                    .getYear()
                                    + "-12-31"));
            initTable(new ArrayList());
        }
        super.setVisible(isFlag);
    }

    private void initTable(final List list) {
        tableModel = new JTableListModel(jTable, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("项目", "bill1", 100));
                        list.add(addColumn("借方发生额(元)", "billSum1", 100));
                        list.add(addColumn("贷方发生额(元)", "billSum2", 100));
                        list.add(addColumn("余额(元)", "billSum3", 100));
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
            jContentPane.setLayout(new BorderLayout());
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
            jSplitPane.setDividerSize(0);
            jSplitPane.setDividerLocation(65);
            jSplitPane.setTopComponent(getJPanel1());
            jSplitPane.setBottomComponent(getJPanel());
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
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
            javax.swing.JLabel jLabel1 = new JLabel();

            javax.swing.JLabel jLabel = new JLabel();

            jPanel1 = new JPanel();
            jPanel1.setLayout(null);
            jPanel1.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jPanel1.setBackground(java.awt.Color.white);
            jLabel.setBounds(12, 22, 52, 16);
            jLabel.setText("日期从：");
            jLabel1.setBounds(174, 22, 25, 20);
            jLabel1.setText("到：");
            jPanel1.add(jLabel, null);
            jPanel1.add(getJCalendarComboBox(), null);
            jPanel1.add(jLabel1, null);
            jPanel1.add(getJCalendarComboBox1(), null);
            jPanel1.add(getJButton(), null);
            jPanel1.add(getJButton1(), null);
            jPanel1.add(getJButton2(), null);
        }
        return jPanel1;
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
            jScrollPane
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return jScrollPane;
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
            jCalendarComboBox.setBounds(72, 19, 85, 22);
        }
        return jCalendarComboBox;
    }

    /**
     * 
     * This method initializes jCalendarComboBox1
     * 
     * 
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     * 
     */
    private JCalendarComboBox getJCalendarComboBox1() {
        if (jCalendarComboBox1 == null) {
            jCalendarComboBox1 = new JCalendarComboBox();
            jCalendarComboBox1.setBounds(201, 21, 86, 22);
        }
        return jCalendarComboBox1;
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
            jButton.setBounds(304, 19, 63, 24);
            jButton.setText("查询");
            jButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // String className = "BillDetail";
                    String className = "BillDetailFixture";
                    List<Condition> conditions = new ArrayList<Condition>();
                    String selects = "select a.billMaster.billType.name,sum(a.money)";
                    String groupBy = "group by a.billMaster.billType.name";
                    customLists = new Vector();
                    List list = null;

                    list = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), selects, className, getConditions(
                            conditions, "直接出口"), groupBy,null);

                    if (list != null && !list.isEmpty()) {
                        Object[] obj = (Object[]) list.get(0);
                        BillTemp temp = new BillTemp();
                        temp.setBill1("直接出口"); // 项目
                        temp
                                .setBillSum1(CommonVars
                                        .formatDouble(new Double(0))); // 借方
                        temp.setBillSum2(CommonVars
                                .formatDouble((Double) obj[1])); // 贷方
                        temp.setBillSum3(CommonVars
                                .formatDouble((Double) obj[1])); // 余额
                        customLists.add(temp);
                    } else {
                        write("直接出口");
                    }
                    conditions.clear();
                    list = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), selects, className, getConditions(
                            conditions, "结转出口"), groupBy,null);
                    if (list != null && !list.isEmpty()) {
                        Object[] obj = (Object[]) list.get(0);
                        BillTemp temp = new BillTemp();
                        temp.setBill1("结转出口"); // 项目
                        temp
                                .setBillSum1(CommonVars
                                        .formatDouble(new Double(0))); // 借方
                        temp.setBillSum2(CommonVars
                                .formatDouble((Double) obj[1])); // 贷方
                        temp.setBillSum3(CommonVars
                                .formatDouble((Double) obj[1])); // 余额
                        customLists.add(temp);
                    } else {
                        write("结转出口");
                    }
                    conditions.clear();
                    list = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), selects, className, getConditions(
                            conditions, "海关批准内销"), groupBy,null);
                    if (list != null && !list.isEmpty()) {
                        Object[] obj = (Object[]) list.get(0);
                        BillTemp temp = new BillTemp();
                        temp.setBill1("海关批准内销"); // 项目
                        temp
                                .setBillSum1(CommonVars
                                        .formatDouble(new Double(0))); // 借方
                        temp.setBillSum2(CommonVars
                                .formatDouble((Double) obj[1])); // 贷方
                        temp.setBillSum3(CommonVars
                                .formatDouble((Double) obj[1])); // 余额
                        customLists.add(temp);
                    } else {
                        write("海关批准内销");
                    }
                    conditions.clear();
                    list = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), selects, className, getConditions(
                            conditions, "返工复出"), groupBy,null);
                    if (list != null && !list.isEmpty()) {
                        Object[] obj = (Object[]) list.get(0);
                        BillTemp temp = new BillTemp();
                        temp.setBill1("返工复出"); // 项目
                        temp
                                .setBillSum1(CommonVars
                                        .formatDouble(new Double(0))); // 借方
                        temp.setBillSum2(CommonVars
                                .formatDouble((Double) obj[1])); // 贷方
                        temp.setBillSum3(CommonVars
                                .formatDouble((Double) obj[1])); // 余额
                        customLists.add(temp);
                    } else {
                        write("返工复出");
                    }
                    conditions.clear();
                    list = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), selects, className, getConditions(
                            conditions, "退厂返工"), groupBy,null);
                    if (list != null && !list.isEmpty()) {
                        Object[] obj = (Object[]) list.get(0);
                        BillTemp temp = new BillTemp();
                        temp.setBill1("退厂返工"); // 项目
                        temp.setBillSum1(CommonVars
                                .formatDouble((Double) obj[1])); // 借方
                        temp
                                .setBillSum2(CommonVars
                                        .formatDouble(new Double(0))); // 贷方
                        temp.setBillSum3(Double.valueOf(0 - CommonVars
                                .formatDouble((Double) obj[1]).doubleValue())); // 余额
                        customLists.add(temp);
                    } else {
                        write("退厂返工");
                    }
                    initTable(customLists);
                }
            });

        }
        return jButton;
    }

    private void write(String typeName) {
        BillTemp temp = new BillTemp();
        temp.setBill1(typeName); // 项目
        temp.setBillSum1(CommonVars.formatDouble(new Double(0))); // 借方
        temp.setBillSum2(CommonVars.formatDouble(new Double(0))); // 贷方
        temp.setBillSum3(CommonVars.formatDouble(new Double(0))); // 余额
        customLists.add(temp);
    }

    private List getConditions(List conditions, String billType) {
        conditions.add(new Condition("and", null, "billMaster.keepAccounts",
                "=", new Boolean(true), null));
        if (jCalendarComboBox.getDate() != null) // 开始日期
            conditions.add(new Condition("and", null, "billMaster.validDate",
                    ">=", CommonVars.dateToStandDate(jCalendarComboBox
                            .getDate()), null));
        if (jCalendarComboBox1.getDate() != null) // 结束日期
            conditions.add(new Condition("and", null, "billMaster.validDate",
                    "<=", CommonVars.dateToStandDate(jCalendarComboBox1
                            .getDate()), null));
        conditions.add(new Condition("and", null, "billMaster.billType.name",
                "=", billType, null));
        return conditions;
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
            jButton1.setBounds(376, 19, 59, 24);
            jButton1.setText("打印");
            jButton1.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    if (customLists != null && !customLists.isEmpty()) {
                        CommonDataSource imgExgDS = new CommonDataSource(
                                customLists);
                        List company = new Vector();
                        company.add(CommonVars.getCurrUser().getCompany());
                        CommonDataSource companyDS = new CommonDataSource(
                                company);
                        String riqi = "从 "
                                + CommonVars.dateToString(jCalendarComboBox
                                        .getDate())
                                + " 至 "
                                + CommonVars.dateToString(jCalendarComboBox1
                                        .getDate());
                        InputStream masterReportStream = DgFinanceSellSum.class
                                .getResourceAsStream("report/FinanceSellSumReport.jasper");
                        InputStream detailReportStream = DgFinanceSellSum.class
                                .getResourceAsStream("report/FinanceSellSumReportSubb.jasper");
                        try {
                            JasperReport detailReport = (JasperReport) JRLoader
                                    .loadObject(detailReportStream);

                            Map parameters = new HashMap();
                            parameters.put("imgExgDS", imgExgDS);
                            parameters.put("detailReport", detailReport);
                            parameters.put("riqi", riqi);
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
        return jButton1;
    }

    /**
     * 
     * This method initializes jButton2
     * 
     * 
     * 
     * @return javax.swing.JButton
     * 
     */
    private JButton getJButton2() {
        if (jButton2 == null) {
            jButton2 = new JButton();
            jButton2.setBounds(443, 19, 59, 24);
            jButton2.setText("关闭");
            jButton2.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    DgFinanceSellSum.this.dispose();

                }
            });

        }
        return jButton2;
    }

} // @jve:decl-index=0:visual-constraint="10,10"
