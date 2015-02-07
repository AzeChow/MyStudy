/*
 * Created on 2004-10-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JLabel;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;

/**
 * @author 陈海鹏
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgExgBomCost extends JDialogBase {

    private JPanel            jPanel1        = null;
    private JList             jList          = null;
    private JLabel            jLabel         = null;
    private JCalendarComboBox startDate      = null;
    private JLabel            jLabel1        = null;
    private JCalendarComboBox endDate        = null;
    private JButton           btnSearch      = null;
    private JButton           btnPrint       = null;
    private JButton           btnClose       = null;
    private JCheckBox         cbOverZeroOnly = null;
    private JCheckBox         jCheckBox1     = null;
    private JTable            jTable         = null;
    private JScrollPane       jScrollPane    = null; // @jve:decl-index=0:visual-constraint="216,266"
    private JPanel            jContentPane   = null;
    private JSplitPane        jSplitPane     = null;
    private JScrollPane       jScrollPane1   = null;

    /**
     * This method initializes
     * 
     */
    public DgExgBomCost() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setContentPane(getJContentPane());
        this.setResizable(false);
        this.setTitle("批量完工成品耗用原材料明细");
        this.setSize(733, 541);

    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jLabel = new JLabel();
            jLabel1 = new JLabel();
            jPanel1.setLayout(null);
            jLabel.setBounds(151, 7, 51, 23);
            jLabel.setText("起始日期");
            jLabel1.setBounds(300, 7, 13, 23);
            jLabel1.setText("到");
            jPanel1.add(jLabel, null);
            jPanel1.add(getStartDate(), null);
            jPanel1.add(jLabel1, null);
            jPanel1.add(getEndDate(), null);
            jPanel1.add(getBtnSearch(), null);
            jPanel1.add(getBtnPrint(), null);
            jPanel1.add(getBtnClose(), null);
            jPanel1.add(getJScrollPane1(), null);
            jPanel1.add(getCbOverZeroOnly(), null);
            jPanel1.add(getJCheckBox1(), null);
        }
        return jPanel1;
    }

    /**
     * This method initializes jList
     * 
     * @return javax.swing.JList
     */
    private JList getJList() {
        if (jList == null) {
            jList = new JList();
        }
        return jList;
    }

    /**
     * This method initializes startDate
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getStartDate() {
        if (startDate == null) {
            startDate = new JCalendarComboBox();
            startDate.setBounds(206, 7, 88, 23);
        }
        return startDate;
    }

    /**
     * This method initializes endDate
     * 
     * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
     */
    private JCalendarComboBox getEndDate() {
        if (endDate == null) {
            endDate = new JCalendarComboBox();
            endDate.setBounds(318, 7, 85, 23);
        }
        return endDate;
    }

    /**
     * This method initializes btnSearch
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSearch() {
        if (btnSearch == null) {
            btnSearch = new JButton();
            btnSearch.setSize(66, 23);
            btnSearch.setLocation(414, 6);
            btnSearch.setText("查询");
            btnSearch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                                                                // Auto-generated
                                                                // Event stub
                                                                // actionPerformed()
                }
            });
        }
        return btnSearch;
    }

    /**
     * This method initializes btnPrint
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnPrint() {
        if (btnPrint == null) {
            btnPrint = new JButton();
            btnPrint.setLocation(414, 35);
            btnPrint.setSize(66, 23);
            btnPrint.setText("打印");
            btnPrint.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                }
            });
        }
        return btnPrint;
    }

    /**
     * This method initializes btnClose
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnClose() {
        if (btnClose == null) {
            btnClose = new JButton();
            btnClose.setLocation(414, 63);
            btnClose.setSize(66, 23);
            btnClose.setText("关闭");
            btnClose.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DgExgBomCost.this.dispose();
                }
            });
        }
        return btnClose;
    }

    /**
     * This method initializes cbOverZeroOnly
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbOverZeroOnly() {
        if (cbOverZeroOnly == null) {
            cbOverZeroOnly = new JCheckBox();
            cbOverZeroOnly.setText("只显示数量大于零的产品");
            cbOverZeroOnly.setBounds(147, 36, 223, 23);
        }
        return cbOverZeroOnly;
    }

    /**
     * This method initializes jCheckBox1
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getJCheckBox1() {
        if (jCheckBox1 == null) {
            jCheckBox1 = new JCheckBox();
            jCheckBox1.setText("料件耗用量汇总显示");
            jCheckBox1.setBounds(147, 64, 223, 23);
        }
        return jCheckBox1;
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
        }
        return jScrollPane;
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
            jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
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
            jSplitPane.setDividerSize(2);
            jSplitPane.setDividerLocation(125);
            jSplitPane.setTopComponent(getJPanel1());
            jSplitPane.setBottomComponent(getJScrollPane());
        }
        return jSplitPane;
    }

    /**
     * This method initializes jScrollPane1
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setBounds(10, 5, 131, 113);
            jScrollPane1.setViewportView(getJList());
        }
        return jScrollPane1;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
