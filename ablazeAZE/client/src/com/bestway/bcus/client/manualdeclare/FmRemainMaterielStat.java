/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclarereport.action.ManualDeclareReportAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmRemainMaterielStat extends JInternalFrameBase {
    private JTable                    jTable                    = null;
    private JToolBar                  jToolBar                  = null;
    private JButton                   btnSearch                 = null;
    private JButton                   btnExit                   = null;
    private JButton                   btnPrint                  = null;
    private JPanel                    jPanel                    = null;
    private JCalendarComboBox         cbbBeginDate              = null;
    private JCalendarComboBox         cbbEndDate                = null;
    private JPanel                    jContentPane              = null;
    private JLabel                    jLabel1                   = null;
    private JLabel                    jLabel2                   = null;
    private JLabel                    jLabel3                   = null;
    private JScrollPane               jScrollPane               = null;

    private ManualDeclareReportAction manualDeclareReportAction = null;
    private JTableListModel           tableModel                = null;
    private List                      dataSource                = new ArrayList();

    /**
     * This is the default constructor
     */
    public FmRemainMaterielStat() {
        super();
        initialize();
        ManualDeclareReportAction manualDeclareReportAction = (ManualDeclareReportAction) CommonVars
        .getApplicationContext().getBean("manualDeclareReportAction");
	manualDeclareReportAction.report1(new Request(
           CommonVars.getCurrUser()));
        initTable(dataSource);

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("边角料统计报表");
        this.setContentPane(getJContentPane());
        this.setSize(733, 428);

    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.add(getBtnSearch());
            jToolBar.add(getBtnPrint());
            jToolBar.add(getBtnExit());
            jToolBar.add(getJPanel());
        }
        return jToolBar;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSearch() {
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
    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setText("关闭");
            btnExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    FmRemainMaterielStat.this.doDefaultCloseAction();
                }
            });
        }
        return btnExit;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnPrint() {
        if (btnPrint == null) {
            btnPrint = new JButton();
            btnPrint.setText("打印");
            btnPrint.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    print();
                }
            });
        }
        return btnPrint;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jLabel3 = new JLabel();
            jLabel2 = new JLabel();
            jLabel1 = new JLabel();
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jLabel1.setText("从");
            jLabel1.setForeground(new java.awt.Color(227, 145, 0));
            jLabel1.setBounds(20, 3, 14, 24);
            jLabel2.setText("到");
            jLabel2.setForeground(new java.awt.Color(227, 145, 0));
            jLabel2.setBounds(154, 3, 15, 24);
            jLabel3.setText("报关单边角料的统计");
            jLabel3.setForeground(new java.awt.Color(227, 145, 0));
            jLabel3.setBounds(293, 3, 131, 24);
            jPanel.add(getCbbBeginDate(), null);
            jPanel.add(jLabel1, null);
            jPanel.add(jLabel2, null);
            jPanel.add(jLabel3, null);
            jPanel.add(getCbbEndDate(), null);
        }
        return jPanel;
    }

    /**
     * This method initializes jCalendarComboBox
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getCbbBeginDate() {
        if (cbbBeginDate == null) {
            cbbBeginDate = new JCalendarComboBox();
            cbbBeginDate.setBounds(41, 3, 110, 24);
        }
        return cbbBeginDate;
    }

    /**
     * This method initializes cbbEndDate
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getCbbEndDate() {
        if (cbbEndDate == null) {
            cbbEndDate = new JCalendarComboBox();
            cbbEndDate.setBounds(176, 3, 110, 24);
        }
        return cbbEndDate;
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
            jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
            jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
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
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();

        }
        return jTable;
    }

    /**
     * 初始化表格
     * 
     * @param list
     */
    private void initTable(List list) {
        tableModel = new JTableListModel(jTable, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List<JTableListColumn> list = new Vector<JTableListColumn>();
                        list.add(addColumn("料件号码", "materialCode", 100));
                        list.add(addColumn("料件名称", "materialName", 100));
                        list.add(addColumn("料件规格", "materialSpec", 100));
                        list.add(addColumn("料件单位", "unit", 80));
                        list.add(addColumn("料件耗用总量", "totalWasteAmount", 100));
                        list.add(addColumn("料件边角料耗用总量",
                                "remainMaterialTotalAmount", 120));
                        return list;
                    }
                });
    }

    /**
     * 查询数据
     * 
     */
    private void search() {
        Date beginDate = this.cbbBeginDate.getDate();
        Date endDate = this.cbbEndDate.getDate();
        if (beginDate.after(endDate) == true) {
            dataSource.clear();
        } else {
            dataSource = this.manualDeclareReportAction.statRemainMaterial(
                    new Request(CommonVars.getCurrUser()), beginDate, endDate);
        }
        initTable(dataSource);
    }

    /**
     * 打印报表
     * 
     */
    private void print() {
        if (dataSource == null || dataSource.size() <= 0) {
            return;
        }
        CustomReportDataSource ds = new CustomReportDataSource(dataSource);
        try {
            InputStream masterReportStream = FmRemainMaterielStat.class
                    .getResourceAsStream("report/RemainMateriel.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    masterReportStream, null, ds);
            DgReportViewer viewer = new DgReportViewer(jasperPrint);
            viewer.setVisible(true);
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

} // @jve:decl-index=0:visual-constraint="10,10"
