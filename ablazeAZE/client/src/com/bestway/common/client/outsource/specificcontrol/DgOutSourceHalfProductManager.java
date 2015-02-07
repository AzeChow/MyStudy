package com.bestway.common.client.outsource.specificcontrol;

import java.awt.Font;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgOutSourceHalfProductManager extends JDialogBase {

    private JPanel  jContentPane             = null;
    private JPanel  jPanel                   = null;
    private JPanel  jPanel1                  = null;
    private JButton btnHalfProductToMateriel = null;
    private JButton btnExit                  = null;
    private JLabel  jLabel                   = null;
    private JButton btnHalfProudctToMateriel = null;
    private JButton btn4104To1112            = null;
    private JButton btn4105To1101 = null;

    /**
     * This method initializes
     * 
     */
    public DgOutSourceHalfProductManager() {
        super(false);
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(636, 475);
        this
                .setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("半成品管理");
        this.setContentPane(getJContentPane());

    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.setSize(631, 446);
            jContentPane.add(getJPanel(), null);
            jContentPane.add(getJPanel1(), null);
            jContentPane.add(getBtnHalfProductToMateriel(), null);
            jContentPane.add(getBtnExit(), null);
            jContentPane.add(getBtnHalfProudctToMateriel(), null);
            jContentPane.add(getBtn4104To1112(), null);
            jContentPane.add(getBtn4105To1101(), null);
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
            jLabel = new JLabel();
            jLabel.setBounds(new java.awt.Rectangle(3, 31, 573, 35));
            jLabel.setIcon(CommonVars.getIconSource().getIcon("hint.gif"));
            jLabel.setText("   半成品折算成料件单据");
            jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setBounds(0, 1, 634, 72);
            jPanel.setBackground(java.awt.Color.white);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jPanel.add(jLabel, null);
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jPanel1 = new JPanel();
            jPanel1.setBounds(3, 394, 625, 3);
            jPanel1
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return jPanel1;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnHalfProductToMateriel() {
        if (btnHalfProductToMateriel == null) {
            btnHalfProductToMateriel = new JButton();
            btnHalfProductToMateriel.setBounds(42, 96, 308, 26);
            btnHalfProductToMateriel.setText("半成品委外入库 ---> 委外料件返回");
            btnHalfProductToMateriel
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(
                                    DgOutSourceHalfProductManager.this,
                                    "生成委外料件返回单据(料件)\n确定要继续吗？？", "警告!!!",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                                new makeThread().start();
                            }
                        }
                    });
        }
        return btnHalfProductToMateriel;
    }

    /**
     * This method initializes jButton3
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setBounds(539, 406, 68, 26);
            btnExit.setText("关闭");
            btnExit
                    .setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                            12));
            btnExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return btnExit;
    }

    /**
     * 生成单据的折算报关数量
     * 
     * @author Administrator
     * 
     */
    class makeThread extends Thread {
        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID();
            final String flag = uuid.toString();
            long beginTime = System.currentTimeMillis();
            try {
                CasAction casAction = (CasAction) CommonVars
                        .getApplicationContext().getBean("casAction");
                CommonProgress.showProgressDialog(flag,
                        DgOutSourceHalfProductManager.this);
                CommonProgress.setMessage(flag, "正在进行数据转换，请稍后...");
                btnHalfProductToMateriel.setEnabled(false);
                casAction.save1013By4003(new Request(CommonVars.getCurrUser()));
                CommonProgress.closeProgressDialog(flag);

                String info = "半成品委外入库单据(半成品) 转成 委外料件返回单据(料件)任务完成!!\n共用时:"
                        + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this, info,
                        "提示", 2);

            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane
                        .showMessageDialog(DgOutSourceHalfProductManager.this,
                                "半成品委外入库单据(半成品) 转成 委外料件返回单据(料件) 失败！！！"
                                        + e.getMessage(), "提示", 2);
            }
            btnHalfProductToMateriel.setEnabled(true);
        }
    }

    /**
     * 生成单据的折算报关数量
     * 
     * @author Administrator
     * 
     */
    class makeThread1 extends Thread {
        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID();
            final String flag = uuid.toString();
            long beginTime = System.currentTimeMillis();
            try {
                CasAction casAction = (CasAction) CommonVars
                        .getApplicationContext().getBean("casAction");
                CommonProgress.showProgressDialog(flag,
                        DgOutSourceHalfProductManager.this);
                CommonProgress.setMessage(flag, "正在进行数据转换，请稍后...");
                btnHalfProductToMateriel.setEnabled(false);
                casAction.save1110By4103(new Request(CommonVars.getCurrUser()));
                CommonProgress.closeProgressDialog(flag);

                String info = "半成品委外出库单据(半成品)转成 车间领用单据(料件)任务完成!!\n共用时:"
                        + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this, info,
                        "提示", 2);

            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this,
                        "数据转换 失败！！！" + e.getMessage(), "提示", 2);
            }
            btnHalfProductToMateriel.setEnabled(true);
        }
    }

    /**
     * This method initializes btn
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnHalfProudctToMateriel() {
        if (btnHalfProudctToMateriel == null) {
            btnHalfProudctToMateriel = new JButton();
            btnHalfProudctToMateriel.setBounds(new java.awt.Rectangle(42,137,308,26));
            btnHalfProudctToMateriel.setText("半成品委外出库  ---> 外发加工出库           ");
            btnHalfProudctToMateriel
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(
                                    DgOutSourceHalfProductManager.this,
                                    "生成车间领用单据(料件)\n确定要继续吗？？", "警告!!!",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                                new makeThread1().start();
                            }
                        }
                    });
        }
        return btnHalfProudctToMateriel;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtn4104To1112() {// 4104 外发加工返修半成品 --> 外发加工返修出库 1112
        if (btn4104To1112 == null) {
            btn4104To1112 = new JButton();
            btn4104To1112.setBounds(new java.awt.Rectangle(42,176,308,26));
            btn4104To1112.setText("外发加工返修半成品 ---> 外发加工返修出库");
            btn4104To1112
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(
                                    DgOutSourceHalfProductManager.this,
                                    "生成外发加工返修出库(料件) \n确定要继续吗？？", "警告!!!",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                                new makeThread2().start();
                            }
                        }
                    });
        }
        return btn4104To1112;
    }

    class makeThread2 extends Thread {
        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID();
            final String flag = uuid.toString();
            long beginTime = System.currentTimeMillis();
            try {
                CasAction casAction = (CasAction) CommonVars
                        .getApplicationContext().getBean("casAction");
                CommonProgress.showProgressDialog(flag,
                        DgOutSourceHalfProductManager.this);
                CommonProgress.setMessage(flag, "正在进行数据转换，请稍后...");
                btn4104To1112.setEnabled(false);
                casAction.save1112By4104(new Request(CommonVars.getCurrUser()));
                CommonProgress.closeProgressDialog(flag);

                String info = "外发加工返修半成品 转成 外发加工返修出库(料件) 任务完成!!\n共用时:"
                        + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this, info,
                        "提示", 2);

            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this,
                        "外发加工返修半成品 转成 外发加工返修出库(料件) 任务失败！！！" + e.getMessage(),
                        "提示", 2);
            }
            btn4104To1112.setEnabled(true);
        }
    }

    /**
     * This method initializes jButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getBtn4105To1101() {
        if (btn4105To1101 == null) {
            btn4105To1101 = new JButton();
            btn4105To1101.setBounds(new java.awt.Rectangle(42,214,308,27));
            btn4105To1101.setText("外发加工领料 ---> 车间领用单据 ");
            btn4105To1101
            .addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(
                            DgOutSourceHalfProductManager.this,
                            "外发加工领料 ---> 车间领用单据 \n确定要继续吗？？", "警告!!!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        new makeThread3().start();
                    }
                }
            });
        }
        return btn4105To1101;
    }
    
    
    
    class makeThread3 extends Thread {
        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID();
            final String flag = uuid.toString();
            long beginTime = System.currentTimeMillis();
            try {
                CasAction casAction = (CasAction) CommonVars
                        .getApplicationContext().getBean("casAction");
                CommonProgress.showProgressDialog(flag,
                        DgOutSourceHalfProductManager.this);
                CommonProgress.setMessage(flag, "正在进行数据转换，请稍后...");
                btn4105To1101.setEnabled(false);
                casAction.save1101By4105(new Request(CommonVars.getCurrUser()));
                CommonProgress.closeProgressDialog(flag);

                String info = "外发加工领料 ---> 车间领用单据 任务完成!!\n共用时:"
                        + (System.currentTimeMillis() - beginTime) + " 毫秒 ";
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this, info,
                        "提示", 2);

            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane.showMessageDialog(DgOutSourceHalfProductManager.this,
                        "外发加工领料 ---> 车间领用单据 任务失败！！！" + e.getMessage(),
                        "提示", 2);
            }
            btn4105To1101.setEnabled(true);
        }
    }
    

}
