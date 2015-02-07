/*
 * Created on 2004-9-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillUtil;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.cas.DgFactoryQuery;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Condition;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.WareType;
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
public class DgFinanceQuery extends JDialogBase {

    private javax.swing.JPanel        jContentPane         = null;

    private JPanel                    jPanel               = null;
    private JSplitPane                jSplitPane           = null;
    private JPanel                    jPanel1              = null;
    private JPanel                    jPanel2              = null;
    private JTable                    jTable               = null;
    private JScrollPane               jScrollPane          = null;

    private JLabel                    jLabel               = null;
    private int                       titleName            = 1;
    private AttributiveCellTableModel tableModel           = null;
    private JCalendarComboBox         jCalendarComboBox    = null;
    private JCalendarComboBox         jCalendarComboBox1   = null;
    private JButton                   jButton              = null;
    private JButton                   jButton1             = null;
    private JButton                   jButton2             = null;
    private MaterialManageAction materialManageAction = null;
    private CasAction                 casAction            = null;
    private BillDetail                billDetail           = null;
    private JLabel                    jLabel7              = null;
    private JComboBox                 jComboBox3           = null;
    private String                    intOutFlat           = null;
    private List                      list                 = null;
    private JComboBox                 jComboBox            = null;
    private String                    reportTitle          = null;
    private String                    ptUnitName           = null;

    /**
     * This is the default constructor
     */
    public DgFinanceQuery() {
        super();
        jTable = new MultiSpanCellTable();
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

        this.setTitle("财务明细帐");
        this.setSize(733, 541);
        this.setContentPane(getJContentPane());
    }

    public void setVisible(boolean isFlag) {
        if (isFlag) {
            initTopName();
            initUI(DgFinanceQuery.this.getTitleName());
            initTable(new ArrayList());
        }
        super.setVisible(isFlag);
    }

    private void initUI(int titleName) {
        // 初始化单据类型
        List list2 = null;
        List list3 = null;
        if (DgFinanceQuery.this.getTitleName() == 1)
            this.setIntOutFlat(MaterielType.MATERIEL);
        else if (DgFinanceQuery.this.getTitleName() == 2)
            this.setIntOutFlat(MaterielType.FINISHED_PRODUCT);
        else if (DgFinanceQuery.this.getTitleName() == 3)
            this.setIntOutFlat(MaterielType.REMAIN_MATERIEL);
        else if (DgFinanceQuery.this.getTitleName() == 4)
            this.setIntOutFlat(MaterielType.BAD_PRODUCT);
        // 初始化仓库
        List list = materialManageAction.findWareSet(new Request(CommonVars
                .getCurrUser(),true));
        this.jComboBox.setModel(new DefaultComboBoxModel(list.toArray()));
        this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
                "code", "name"));
        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
                this.jComboBox, "code", "name");

        // 初始化工厂商品
        list2 = casAction.findDistinctPtNameAndWrite(new Request(CommonVars
                .getCurrUser()), this.getIntOutFlat());
        List customLists = new Vector();
        if (list2 != null && !list2.isEmpty()) {
            for (int i = 0; i < list2.size(); i++) {
                Object[] obj = (Object[]) list2.get(i);
                BillTemp temp = new BillTemp();
                temp.setBill1(CommonVars.formatStr((String) (obj[0])));
                temp.setBill2(CommonVars.formatStr((String) (obj[1])));
                customLists.add(temp);
            }
        }
        this.jComboBox3
                .setModel(new DefaultComboBoxModel(customLists.toArray()));
        this.jComboBox3.setRenderer(CustomBaseRender.getInstance().getRender(
                "bill1", "bill2", 150, 150));
        CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
                this.jComboBox3, "bill2", "bill1");
        this.jComboBox3.setUI(new CustomBaseComboBoxUI(300));
        // this.jComboBox3.setSelectedIndex(-1);
        this.jCalendarComboBox.setDate(java.sql.Date
                .valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
                        .getYear()
                        + "-01-01"));
        this.jCalendarComboBox1.setDate(java.sql.Date
                .valueOf(com.bestway.bcus.client.cas.parameter.CasCommonVars
                        .getYear()
                        + "-12-31"));
    }

    private void initTable(final List list) {
        tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
                list, new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("生效日期", "bill1", 80));
                        list.add(addColumn("摘要", "bill2", 80));
                        list.add(addColumn("单据号", "bill3", 90));

                        list.add(addColumn("数量", "billSum1", 80)); // 借方
                        list.add(addColumn("单价", "billSum2", 80));
                        list.add(addColumn("金额", "billSum3", 80));

                        list.add(addColumn("数量", "billSum4", 80)); // 贷方
                        list.add(addColumn("单价", "billSum5", 80));
                        list.add(addColumn("金额", "billSum6", 80));

                        list.add(addColumn("数量", "billSum7", 80)); // 借方余额
                        list.add(addColumn("单价", "billSum8", 80));
                        list.add(addColumn("金额", "billSum9", 80));
                        return list;
                    }
                });
        TableColumnModel cm = jTable.getColumnModel();
        ColumnGroup voucher = new ColumnGroup("借方");
        voucher.add(cm.getColumn(4));
        voucher.add(cm.getColumn(5));
        voucher.add(cm.getColumn(6));

        ColumnGroup inside = new ColumnGroup("贷方");
        inside.add(cm.getColumn(7));
        inside.add(cm.getColumn(8));
        inside.add(cm.getColumn(9));

        ColumnGroup directnessIN = new ColumnGroup("借方余额");
        directnessIN.add(cm.getColumn(10));
        directnessIN.add(cm.getColumn(11));
        directnessIN.add(cm.getColumn(12));

        GroupableTableHeader header = (GroupableTableHeader) jTable
                .getTableHeader();
        header.addColumnGroup(voucher); // 借方
        header.addColumnGroup(inside); // 贷方
        header.addColumnGroup(directnessIN); // 借方余额
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
            jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
            jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
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
            javax.swing.JLabel jLabel5 = new JLabel();

            javax.swing.JLabel jLabel4 = new JLabel();

            jPanel = new JPanel();
            jLabel = new JLabel();
//            jPanel.setLayout(new BorderLayout());
//            jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
//            jPanel
//                    .setBorder(javax.swing.BorderFactory
//                            .createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
//            jPanel.setBackground(java.awt.Color.white);
//            jLabel4.setText("");
//            jLabel4.setIcon(new ImageIcon(getClass().getResource(
//                    "/com/bestway/bcus/client/resources/images/titlepic.jpg")));
//            jLabel5.setText("");
//            jLabel5
//                    .setIcon(new ImageIcon(
//                            getClass()
//                                    .getResource(
//                                            "/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
//            jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
//            jPanel.add(jLabel4, java.awt.BorderLayout.EAST);
//            jPanel.add(jLabel5, java.awt.BorderLayout.WEST);
        }
        return jPanel;
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
            jSplitPane.setDividerLocation(95);
            jSplitPane.setDividerSize(2);
            jSplitPane.setTopComponent(getJPanel2());
            jSplitPane.setBottomComponent(getJPanel1());
        }
        return jSplitPane;
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
            jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return jPanel1;
    }

    /**
     * 
     * This method initializes jPanel2
     * 
     * 
     * 
     * @return javax.swing.JPanel
     * 
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            javax.swing.JLabel jLabel3 = new JLabel();

            jLabel7 = new JLabel();

            javax.swing.JLabel jLabel2 = new JLabel();

            javax.swing.JLabel jLabel1 = new JLabel();

            jPanel2 = new JPanel();
            jPanel2.setLayout(null);
            jPanel2.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            jLabel1.setText("日期从:");
            jLabel1.setBounds(18, 11, 47, 18);
            jLabel2.setBounds(181, 11, 26, 21);
            jLabel2.setText("到：");
            jLabel7.setBounds(16, 42, 54, 20);
            jLabel7.setText("商品名称");
            jLabel3.setBounds(354, 11, 31, 22);
            jLabel3.setText("仓库");
            jPanel2.add(jLabel1, null);
            jPanel2.add(getJCalendarComboBox(), null);
            jPanel2.add(jLabel2, null);
            jPanel2.add(getJCalendarComboBox1(), null);
            jPanel2.add(jLabel7, null);
            jPanel2.add(getJComboBox3(), null);
            jPanel2.add(getJButton(), null);
            jPanel2.add(getJButton1(), null);
            jPanel2.add(getJButton2(), null);
            jPanel2.add(jLabel3, null);
            jPanel2.add(getJComboBox(), null);
        }
        return jPanel2;
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
            jScrollPane.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        }
        return jScrollPane;
    }

    /**
     * @return Returns the titleName.
     */
    public int getTitleName() {
        return titleName;
    }

    /**
     * @param titleName
     *            The titleName to set.
     */
    public void setTitleName(int titleName) {
        this.titleName = titleName;
    }

    private void initTopName() {
        if (DgFinanceQuery.this.getTitleName() == 1) {
            jLabel.setText("财务明细帐:原材料");
            this.setReportTitle("原材料明细帐");
        } else if (DgFinanceQuery.this.getTitleName() == 2) {
            jLabel.setText("财务明细帐:产成品");
            this.setReportTitle("产成品明细帐");
        } else if (DgFinanceQuery.this.getTitleName() == 3) {
            jLabel.setText("财务明细帐:边角料");
            this.setReportTitle("边角料明细帐");
        } else if (DgFinanceQuery.this.getTitleName() == 4) {
            jLabel.setText("财务明细帐:残次品");
            this.setReportTitle("残次品明细帐");
        }
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
            jCalendarComboBox.setBounds(71, 11, 90, 22);
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
            jCalendarComboBox1.setBounds(220, 11, 91, 22);
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
            jButton.setBounds(354, 37, 70, 24);
            jButton.setText("查询");
            jButton.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (jComboBox.getSelectedItem() == null
                            || jComboBox.getSelectedItem().equals("")) // 仓库
                    {
                        JOptionPane.showMessageDialog(DgFinanceQuery.this,
                                "请选择仓库！", "提示！", 2);
                        return;
                    }
                    if (jComboBox3.getSelectedItem() == null
                            || jComboBox3.getSelectedItem().equals("")) // 报关商品
                    {
                        JOptionPane.showMessageDialog(DgFinanceQuery.this,
                                "请选择商品！", "提示！", 2);
                        return;
                    }
                    String className = BillUtil
                            .getBillDetailTableNameByMaterielType(getIntOutFlat());
                    String orderBy = "order by a.billMaster.validDate,a.billMaster.billType.name";
                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(new Condition("and", null,
                            "billMaster.keepAccounts", "=", new Boolean(true),
                            null));
                    if (jCalendarComboBox.getDate() != null) // 开始日期
                        conditions.add(new Condition("and", null,
                                "billMaster.validDate", ">=", CommonVars
                                        .dateToStandDate(jCalendarComboBox
                                                .getDate()), null));
                    if (jCalendarComboBox1.getDate() != null) // 结束日期
                        conditions.add(new Condition("and", null,
                                "billMaster.validDate", "<=", CommonVars
                                        .dateToStandDate(jCalendarComboBox1
                                                .getDate()), null));
                    conditions.add(new Condition("and", null, "ptName", "=",
                            ((BillTemp) jComboBox3.getSelectedItem())
                                    .getBill1(), null));
                    conditions.add(new Condition("and", null, "ptSpec", "=",
                            ((BillTemp) jComboBox3.getSelectedItem())
                                    .getBill2(), null));
                    conditions.add(new Condition("and", null,
                            "wareSet.code", "=",
                            ((WareSet) jComboBox.getSelectedItem()).getCode(),
                            null));
                    // 物料类型
                    // conditions.add(new
                    // Condition("and",null,"billMaster.billType.produceType","=",DgFinanceQuery.this.getIntOutFlat(),null));

                    List result = casAction.commonCount(new Request(CommonVars
                            .getCurrUser()), null, className, conditions,
                            orderBy,null);

                    if (result != null && !result.isEmpty()) {
                        list = new Vector();
                        double yeAmount = 0;
                        double yeMoney = 0;
                        for (int i = 0; i < result.size(); i++) {
                            BillTemp temp = new BillTemp();
                            BillDetail bill = (BillDetail) result.get(i);
                            temp.setBill1(CommonVars.dateToString(bill
                                    .getBillMaster().getValidDate())); // 日期
                            temp.setBill2(bill.getBillMaster().getBillType()
                                    .getName()); // 名称
                            temp.setBill3(bill.getBillMaster().getBillNo()); // 号码
                            if (i == 0) {
                                DgFinanceQuery.this.setPtUnitName(bill
                                        .getPtUnit().getName()); // 单位
                            }
                            if (bill.getBillMaster().getBillType()
                                    .getWareType().equals(
                                            Integer.valueOf(WareType.WARE_IN))) {
                                temp.setBillSum1(CommonVars.formatDouble(bill
                                        .getPtAmount())); // 借方-数量
                                temp.setBillSum2(CommonVars.formatDouble(bill
                                        .getPtPrice())); // 借方-单价
                                temp.setBillSum3(CommonVars.formatDouble(bill
                                        .getMoney())); // 借方-金额

                                temp.setBillSum7(Double.valueOf(CommonVars
                                        .formatDouble(bill.getPtAmount())
                                        .doubleValue()
                                        + yeAmount)); // 借方余额-数量
                                temp.setBillSum8(CommonVars.formatDouble(bill
                                        .getPtPrice())); // 借方余额-单价
                                temp.setBillSum9(Double.valueOf(CommonVars
                                        .formatDouble(bill.getMoney())
                                        .doubleValue()
                                        + yeMoney)); // 借方余额-金额
                                yeAmount = CommonVars.formatDouble(
                                        bill.getPtAmount()).doubleValue()
                                        + yeAmount;
                                yeMoney = CommonVars.formatDouble(
                                        bill.getMoney()).doubleValue()
                                        + yeMoney;
                            } else if (bill.getBillMaster().getBillType()
                                    .getWareType().equals(
                                            Integer.valueOf(WareType.WARE_OUT))) {
                                temp.setBillSum4(CommonVars.formatDouble(bill
                                        .getPtAmount())); // 贷方-数量
                                temp.setBillSum5(CommonVars.formatDouble(bill
                                        .getPtPrice())); // 贷方-单价
                                temp.setBillSum6(CommonVars.formatDouble(bill
                                        .getMoney())); // 贷方-金额

                                temp.setBillSum7(Double.valueOf(yeAmount
                                        - CommonVars.formatDouble(
                                                bill.getPtAmount())
                                                .doubleValue())); // 借方余额-数量
                                temp.setBillSum8(CommonVars.formatDouble(bill
                                        .getPtPrice())); // 借方余额-单价
                                temp
                                        .setBillSum9(Double.valueOf(yeMoney
                                                - CommonVars.formatDouble(
                                                        bill.getMoney())
                                                        .doubleValue())); // 借方余额-金额
                                yeAmount = yeAmount
                                        - CommonVars.formatDouble(
                                                bill.getPtAmount())
                                                .doubleValue();
                                yeMoney = yeMoney
                                        - CommonVars.formatDouble(
                                                bill.getMoney()).doubleValue();

                            }
                            list.add(temp);
                        }
                        initTable(list);
                    } else {
                        initTable(new Vector());
                        JOptionPane.showMessageDialog(DgFinanceQuery.this,
                                "没有查到符合条件的记录！", "提示！", 2);
                    }
                }
            });

        }
        return jButton;
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
            jButton1.setBounds(436, 37, 69, 23);
            jButton1.setText("打印");
            jButton1.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (list != null && !list.isEmpty()) {
                        CommonDataSource imgExgDS = new CommonDataSource(list);
                        List company = new Vector();
                        company.add(CommonVars.getCurrUser().getCompany());
                        CommonDataSource companyDS = new CommonDataSource(
                                company);
                        String hsName = "";
                        String hsSpec = "";
                        if (jComboBox3.getSelectedItem() != null
                                && !jComboBox3.getSelectedItem().equals("")) // 工厂商品
                        {
                            hsName = ((BillTemp) jComboBox3.getSelectedItem())
                                    .getBill1();
                            hsSpec = ((BillTemp) jComboBox3.getSelectedItem())
                                    .getBill2();
                            hsName = hsName + "/" + hsSpec;
                        }
                        InputStream masterReportStream = DgFactoryQuery.class
                                .getResourceAsStream("report/FinanceQueryReport.jasper");
                        InputStream detailReportStream = DgFactoryQuery.class
                                .getResourceAsStream("report/FinanceQueryReportSubb.jasper");
                        try {
                            JasperReport detailReport = (JasperReport) JRLoader
                                    .loadObject(detailReportStream);

                            Map parameters = new HashMap();
                            parameters.put("imgExgDS", imgExgDS);
                            parameters.put("detailReport", detailReport);
                            parameters.put("hsName", hsName);
                            parameters.put("title", DgFinanceQuery.this
                                    .getReportTitle());
                            parameters.put("ptUnit", DgFinanceQuery.this
                                    .getPtUnitName());
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
            jButton2.setBounds(518, 37, 69, 23);
            jButton2.setText("关闭");
            jButton2.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent e) {

                    DgFinanceQuery.this.dispose();

                }
            });

        }
        return jButton2;
    }

    /**
     * 
     * This method initializes jComboBox3
     * 
     * 
     * 
     * @return javax.swing.JComboBox
     * 
     */
    private JComboBox getJComboBox3() {
        if (jComboBox3 == null) {
            jComboBox3 = new JComboBox();
            jComboBox3.setBounds(75, 39, 237, 20);
        }
        return jComboBox3;
    }

    /**
     * @return Returns the intOutFlat.
     */
    public String getIntOutFlat() {
        return intOutFlat;
    }

    /**
     * @param intOutFlat
     *            The intOutFlat to set.
     */
    public void setIntOutFlat(String intOutFlat) {
        this.intOutFlat = intOutFlat;
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
            jComboBox.setBounds(393, 11, 194, 22);
        }
        return jComboBox;
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

    /**
     * @return Returns the ptUnitName.
     */
    public String getPtUnitName() {
        return ptUnitName;
    }

    /**
     * @param ptUnitName
     *            The ptUnitName to set.
     */
    public void setPtUnitName(String ptUnitName) {
        this.ptUnitName = ptUnitName;
    }
} // @jve:decl-index=0:visual-constraint="16,40"
