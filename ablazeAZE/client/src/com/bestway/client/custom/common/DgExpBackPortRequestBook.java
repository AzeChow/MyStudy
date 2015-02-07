/*
 * Created on 2004-12-30
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ExpBackPortRequestBook;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExpBackPortRequestBook extends JDialogBase {

    private javax.swing.JPanel     jContentPane           = null;

    private JToolBar               jToolBar               = null;
    private JButton                btnSave                = null;
    private JButton                btnPrint               = null;
    private JButton                btnExit                = null;
    private JTable                 jTable                 = null;
    private JScrollPane            jScrollPane            = null;
    private JTextField             tfCompany              = null;
    private JTextField             tfHongKongCommodityNo  = null;
    private JTextField             tfConveyance           = null;
    private JTextField             tfContractNo           = null;
    private JTextField             tfComplexNo            = null;
    private JLabel                 jLabel                 = null;
    private JLabel                 jLabel1                = null;
    private JLabel                 jLabel2                = null;
    private JLabel                 jLabel3                = null;
    private JLabel                 jLabel5                = null;
    private JLabel                 jLabel7                = null;
    private JLabel                 jLabel12               = null;
    private JLabel                 jLabel13               = null;
    private JScrollPane            jScrollPane1           = null;

    private ExpBackPortRequestBook expBackPortRequestBook = null;
    private ImpExpRequestBill      impExpRequestBill      = null;
    private EncAction              encAction              = null;
    private JTableListModel        tableModel             = null;

    private JCalendarComboBox      cbbRequestDate         = null;
    private JEditorPane            epRequestExcuse        = null;

    /**
     * This is the default constructor
     */
    public DgExpBackPortRequestBook() {
        super();
        this.encAction = (EncAction) CommonVars.getApplicationContext()
                .getBean("encAction");
        initialize();
    }

    public void setVisible(boolean isFlag) {
        if (isFlag == true) {
            this.initComponents();
            this.showData();
        }
        super.setVisible(isFlag);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("退港申请书-退厂返工");
        this.setSize(649, 482);
        this.setContentPane(getJContentPane());
    }

    /**
     * @return Returns the impExpRequestBill.
     */
    public ImpExpRequestBill getImpExpRequestBill() {
        return impExpRequestBill;
    }

    /**
     * @param impExpRequestBill
     *            The impExpRequestBill to set.
     */
    public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
        this.impExpRequestBill = impExpRequestBill;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jLabel13 = new JLabel();
            jLabel12 = new JLabel();
            jLabel7 = new JLabel();
            jLabel5 = new JLabel();
            jLabel3 = new JLabel();
            jLabel2 = new JLabel();
            jLabel1 = new JLabel();
            jLabel = new JLabel();
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(null);
            jLabel.setBounds(388, 47, 49, 19);
            jLabel.setText("海关编号");
            jLabel1.setBounds(14, 47, 50, 19);
            jLabel1.setText("申请单位");
            jLabel2.setBounds(14, 359, 50, 19);
            jLabel2.setText("申请理由");
            jLabel3.setBounds(14, 93, 50, 19);
            jLabel3.setText("动输工具");
            jLabel5.setBounds(386, 93, 51, 19);
            jLabel5.setText("申请日期");
            jLabel7.setBounds(400, 70, 37, 19);
            jLabel7.setText("合同号");
            jLabel12.setBounds(14, 70, 49, 19);
            jLabel12.setText("香港商号");
            jLabel13.setBounds(13, 116, 113, 19);
            jLabel13.setText("现申请出口下列物件");
            jContentPane.add(getJToolBar(), null);
            jContentPane.add(getJScrollPane(), null);
            jContentPane.add(getTfCompany(), null);
            jContentPane.add(getTfHongKongCommodityNo(), null);
            jContentPane.add(getTfConveyance(), null);
            jContentPane.add(getCbbRequestDate(), null);
            jContentPane.add(getTfContractNo(), null);
            jContentPane.add(getTfComplexNo(), null);
            jContentPane.add(jLabel, null);
            jContentPane.add(jLabel1, null);
            jContentPane.add(jLabel2, null);
            jContentPane.add(jLabel3, null);
            jContentPane.add(jLabel5, null);
            jContentPane.add(jLabel7, null);
            jContentPane.add(jLabel12, null);
            jContentPane.add(jLabel13, null);
            jContentPane.add(getJScrollPane1(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes jToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJToolBar() {
        if (jToolBar == null) {
            jToolBar = new JToolBar();
            jToolBar.setBounds(0, 0, 647, 34);
            jToolBar.add(getBtnSave());
            jToolBar.add(getBtnPrint());
            jToolBar.add(getBtnExit());
        }
        return jToolBar;
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
                    saveData();
                }
            });
        }
        return btnSave;
    }

    /**
     * This method initializes btnPrint
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
     * This method initializes btnExit
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setText("关闭");
            btnExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DgExpBackPortRequestBook.this.dispose();
                }
            });
        }
        return btnExit;
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
            jScrollPane.setBounds(14, 137, 614, 219);
        }
        return jScrollPane;
    }

    /**
     * This method initializes tfCompany
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfCompany() {
        if (tfCompany == null) {
            tfCompany = new JTextField();
            tfCompany.setBounds(65, 47, 301, 19);
        }
        return tfCompany;
    }

    /**
     * This method initializes tfHongKongCommodityNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfHongKongCommodityNo() {
        if (tfHongKongCommodityNo == null) {
            tfHongKongCommodityNo = new JTextField();
            tfHongKongCommodityNo.setBounds(65, 70, 301, 19);
        }
        return tfHongKongCommodityNo;
    }

    /**
     * This method initializes tfConveyance
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfConveyance() {
        if (tfConveyance == null) {
            tfConveyance = new JTextField();
            tfConveyance.setBounds(65, 93, 301, 19);
        }
        return tfConveyance;
    }

    /**
     * This method initializes tfContractNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfContractNo() {
        if (tfContractNo == null) {
            tfContractNo = new JTextField();
            tfContractNo.setBounds(439, 70, 191, 19);
        }
        return tfContractNo;
    }

    /**
     * This method initializes tfComplexNo
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTfComplexNo() {
        if (tfComplexNo == null) {
            tfComplexNo = new JTextField();
            tfComplexNo.setBounds(439, 47, 191, 19);
        }
        return tfComplexNo;
    }

    /**
     * This method initializes cbbRequestDate
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getCbbRequestDate() {
        if (cbbRequestDate == null) {
            cbbRequestDate = new JCalendarComboBox();
            cbbRequestDate.setBounds(439, 93, 191, 19);
        }
        return cbbRequestDate;
    }

    /**
     * This method initializes epRequestExcuse
     * 
     * @return javax.swing.JEditorPane
     */
    private JEditorPane getEpRequestExcuse() {
        if (epRequestExcuse == null) {
            epRequestExcuse = new JEditorPane();
        }
        return epRequestExcuse;
    }

    /**
     * This method initializes jScrollPane1
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setBounds(14, 381, 614, 64);
            jScrollPane1
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jScrollPane1.setViewportView(getEpRequestExcuse());
        }
        return jScrollPane1;
    }

    /**
     * 打印
     */
    private void print() {
        //
        // 并不保存数据
        //
        fillData();
        List list = new ArrayList();
        if (this.expBackPortRequestBook != null) {
            list.add(this.expBackPortRequestBook);
        }
        CustomReportDataSource ds = new CustomReportDataSource(list);
        CustomsDeclarationSubReportDataSource
        .setSubReportData(encAction
                .findImpExpCommodityInfo(new Request(CommonVars
                        .getCurrUser())));
        try {
            InputStream subReportStream = DgExpBackPortRequestBook.this
                    .getClass().getResourceAsStream(
                            "report/ExpBackPortRequestBookSubReport.jasper");
            JasperReport subReport = (JasperReport) JRLoader
                    .loadObject(subReportStream);
            Map parameters = new HashMap();
            parameters.put("subReport", subReport);
            InputStream reportStream = DgExpBackPortRequestBook.this
                    .getClass()
                    .getResourceAsStream("report/ExpBackPortRequestBook.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    reportStream, parameters, ds);
            DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
            dgReportViewer.setVisible(true);
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 初始化对象
     */
    private void initComponents() {
        expBackPortRequestBook = this.encAction.findExpBackPortRequestBookById(
                new Request(CommonVars.getCurrUser()), this.impExpRequestBill
                        .getId());
        if (expBackPortRequestBook == null) {
            expBackPortRequestBook = new ExpBackPortRequestBook();
            expBackPortRequestBook.setImpExpRequestBill(this.impExpRequestBill);
        }
    }

    /**
     * 显示数据
     */
    private void showData() {
        if (this.expBackPortRequestBook.getContractNo() != null) {
            this.tfContractNo.setText(this.expBackPortRequestBook
                    .getContractNo());
        } else {
            this.tfContractNo.setText("");
        }
        if (this.expBackPortRequestBook.getConveyance() != null) {
            this.tfConveyance.setText(this.expBackPortRequestBook
                    .getConveyance());
        } else {
            this.tfConveyance.setText("");
        }

        if (this.expBackPortRequestBook.getHongKongCommodityNo() != null) {
            this.tfHongKongCommodityNo.setText(this.expBackPortRequestBook
                    .getHongKongCommodityNo());
        } else {
            this.tfHongKongCommodityNo.setText("");
        }

        if (this.expBackPortRequestBook.getRequestDate() != null) {
            this.cbbRequestDate.setDate(this.expBackPortRequestBook
                    .getRequestDate());
        } else {
            this.cbbRequestDate.setDate(null);
        }

        if (this.expBackPortRequestBook.getRequestExcuse() != null) {
            this.epRequestExcuse.setText(this.expBackPortRequestBook
                    .getRequestExcuse());
        } else {
            this.epRequestExcuse.setText("");
        }

        if (this.expBackPortRequestBook.getImpExpRequestBill() != null) {
            if (this.expBackPortRequestBook.getImpExpRequestBill().getCompany() != null) {
                this.tfCompany.setText(this.expBackPortRequestBook
                        .getImpExpRequestBill().getCompany().getName());
                String buCode = ((Company) this.expBackPortRequestBook
                        .getImpExpRequestBill().getCompany()).getBuCode();
                if (buCode != null) {
                    this.tfComplexNo.setText(buCode);
                } else {
                    this.tfComplexNo.setText("");
                }
            } else {
                this.tfCompany.setText("");
                this.tfComplexNo.setText("");
            }
        } else {
            this.tfCompany.setText("");
            this.tfComplexNo.setText("");
        }
        this.initTable();
    }

    /**
     * 填充数据
     *  
     */
    private void fillData() {
        this.expBackPortRequestBook.setCompany(CommonVars.getCurrUser()
                .getCompany());
        this.expBackPortRequestBook.setContractNo(this.tfContractNo.getText());
        this.expBackPortRequestBook.setConveyance(this.tfConveyance.getText());
        this.expBackPortRequestBook
                .setHongKongCommodityNo(this.tfHongKongCommodityNo.getText());
        this.expBackPortRequestBook.setRequestExcuse(this.epRequestExcuse
                .getText());
        //        this.expBackPortRequestBook.setRequestExcuse(null);
        this.expBackPortRequestBook.setRequestDate(this.cbbRequestDate
                .getDate());
    }

    /**
     * 保存数据
     */
    private void saveData() {
        this.fillData();
        this.expBackPortRequestBook = this.encAction
                .saveExpBackPortRequestBook(new Request(CommonVars
                        .getCurrUser()), this.expBackPortRequestBook);

    }

    /**
     * 初始化表
     */
    private void initTable() {
        List list = this.encAction.findImpExpCommodityInfoByPutOnRecord(
                new Request(CommonVars.getCurrUser()),
                this.expBackPortRequestBook.getImpExpRequestBill().getId());
        tableModel = new JTableListModel(jTable, list,
                new JTableListModelAdapter() {
                    public List InitColumns() {
                        List list = new Vector();
                        list.add(addColumn("料号", "materiel.ptNo", 90));
                        list.add(addColumn("名称", "materiel.factoryName", 120));
                        list.add(addColumn("规格型号", "materiel.factorySpec", 120));
                        list.add(addColumn("单位", "materiel.calUnit.name", 60));
                        list.add(addColumn("数量", "quantity", 60));
                        list.add(addColumn("单价", "unitPrice", 60));
                        list.add(addColumn("总金额", "amountPrice", 60));
                        list.add(addColumn("毛重", "grossWeight", 60));
                        list.add(addColumn("净重", "netWeight", 60));
                        list.add(addColumn("体积", "bulks", 60));
                        list.add(addColumn("制单号", "makeBillNo", 60));
                        list.add(addColumn("版本号", "version", 60));
                        return list;
                    }
                });
    }

}