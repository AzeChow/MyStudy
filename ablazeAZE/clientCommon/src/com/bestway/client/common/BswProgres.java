/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJDialog.java
 *
 * Created on 2010-11-29, 9:12:32
 */
package com.bestway.client.common;

import com.bestway.ui.winuicontrol.JDialogBase;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author kcb
 */
public class BswProgres extends JDialogBase {

    private static Thread thread = null;
    private static BswProgres bswProgres = null;
    private static boolean isRun = false;

    private BswProgres() {
        super();
        initialize();
    }

    public static void showDialog() {
        if (thread != null) {
            throw new RuntimeException("线程还没结束!");
        }
        final Thread timeThread = new Thread() {
            public void run() {
                long begain = System.currentTimeMillis();
                while (isRun) {
                    try {
                        Thread.currentThread().sleep(1 * 1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BswProgres.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    long curr = System.currentTimeMillis();
                    long useTime = (curr - begain) / 1000l;
                    lbMsg.setText("任务已使用时间:" + useTime + "秒!");
                    if (useTime > 60 * 2) {
                         btnClose.setText("关闭");
                        btnClose.setEnabled(true);
                    } else {
                         btnClose.setText("关闭(" + (120 - useTime) + "秒)");
                    }
                }
            }
        };
        thread = new Thread() {
            public void run() {
                UIManager.put("ProgressBar.repaintInterval", new Integer(300));
                UIManager.put("ProgressBar.cycleTime", new Integer(6000));
                isRun = true;
                timeThread.start();
                bswProgres = new BswProgres();
                bswProgres.setVisible(true);
            }
        };
        thread.start();

    }

    public static void closeDialog() {
        try {
            while (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
            if (bswProgres != null) {
                bswProgres.dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            isRun = false;
            thread = null;
        }
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setBounds(0, 0, 480, 63);
        this.setTitle("正在运行");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setContentPane(getJPanel());
        this.setUndecorated(true);
        this.setResizable(false);
    }

    private JPanel getJPanel() {
        pnTask = new javax.swing.JPanel();
        lbMsg = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        psbTask = new javax.swing.JProgressBar();
        psbTask.setStringPainted(true);
        psbTask.setString("正在执行任务,请等待......");
        pnTask.setLayout(null);

        lbMsg.setText("任务已使用时间:0秒!");
        pnTask.add(lbMsg);
        lbMsg.setBounds(0, 0, 480, 28);
        pnTask.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(160, 200, 240)));

        btnClose.setText("关闭");
        pnTask.add(btnClose);
        btnClose.setBounds(369, 0, 110, 30);
        pnTask.add(psbTask);
        psbTask.setBounds(0, 30, 480, 30);
        psbTask.setIndeterminate(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 441, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 124, Short.MAX_VALUE));
        btnClose.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeDialog();
            }
        });
        btnClose.setEnabled(false);
        return pnTask;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        BswProgres.showDialog();
        try {
            Thread.currentThread().sleep(1000 * 60 * 3);
        } catch (InterruptedException ex) {
            Logger.getLogger(BswProgres.class.getName()).log(Level.SEVERE, null, ex);
        }
        BswProgres.closeDialog();
    }
    // Variables declaration - do not modify
    private static javax.swing.JButton btnClose;
    private static javax.swing.JLabel lbMsg;
    private javax.swing.JPanel pnTask;
    private javax.swing.JProgressBar psbTask;
    // End of variables declaration
}
