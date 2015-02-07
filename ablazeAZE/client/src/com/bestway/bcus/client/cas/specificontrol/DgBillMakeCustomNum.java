package com.bestway.bcus.client.cas.specificontrol;

import java.awt.Font;
import java.util.Date;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.specificontrol.entity.TempMaterielTypeSetup;
import com.bestway.bcus.client.cas.DgExgExportInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class DgBillMakeCustomNum extends JDialogBase {

    private JPanel    jContentPane         = null;
    private JPanel    jPanel               = null;
    private JCheckBox cbProduct            = null;
    private JCheckBox cbMateriel           = null;
    private JCheckBox cbSimeProduct        = null;
    private JCheckBox cbMachine            = null;
    private JCheckBox cbRemainMateriel     = null;
    private JPanel    jPanel1              = null;
    private JButton   btnBillMakeCustomNum = null;
    private JButton   btnExit              = null;
    private JCheckBox cbBadProduct         = null;
    private JLabel    jLabel               = null;
    private JButton   btnSetup             = null;
    private JCheckBox cbIsMakeCustomsInfo  = null;
    private JCheckBox cbIsUpdateHsAmount   = null;
    private JButton   btnBatch             = null;

    /**
     * This method initializes
     * 
     */
    public DgBillMakeCustomNum() {
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
        this.setTitle("生成单据的折算报关数量");
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
            jContentPane.add(getCbProduct(), null);
            jContentPane.add(getCbMateriel(), null);
            jContentPane.add(getCbSimeProduct(), null);
            jContentPane.add(getCbMachine(), null);
            jContentPane.add(getCbRemainMateriel(), null);
            jContentPane.add(getBtnBillMakeCustomNum(), null);
            jContentPane.add(getBtnExit(), null);
            jContentPane.add(getCbBadProduct(), null);
            jContentPane.add(getBtnSetup(), null);
            jContentPane.add(getCbIsMakeCustomsInfo(), null);
            jContentPane.add(getCbIsUpdateHsAmount(), null);
            jContentPane.add(getBtnBatch(), null);
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
            jLabel.setBounds(new java.awt.Rectangle(6, 16, 507, 38));
            jLabel
                    .setText("\u751f\u6210\u5355\u636e\u7684\u6298\u7b97\u62a5\u5173\u6570\u91cf(\u672a\u6298\u7b97\u62a5\u5173\u6570\u91cf\u7684\u5355\u636e)");
            jLabel.setIcon(new ImageIcon(getClass().getResource(
                    "/com/bestway/bcus/client/resources/images/hint.gif")));
            jLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setBounds(0, 1, 634, 57);
            jPanel.setBackground(java.awt.Color.white);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jPanel.add(jLabel, null);
        }
        return jPanel;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbProduct() {
        if (cbProduct == null) {
            cbProduct = new JCheckBox();
            cbProduct.setText("成品类型单据");
            cbProduct.setBounds(53, 82, 104, 21);
            cbProduct.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
        }
        return cbProduct;
    }

    /**
     * This method initializes jCheckBox1
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbMateriel() {
        if (cbMateriel == null) {
            cbMateriel = new JCheckBox();
            cbMateriel.setText("料件类型单据");
            cbMateriel.setBounds(53, 110, 108, 18);
            cbMateriel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
        }
        return cbMateriel;
    }

    /**
     * This method initializes jCheckBox2
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbSimeProduct() {
        if (cbSimeProduct == null) {
            cbSimeProduct = new JCheckBox();
            cbSimeProduct.setText("半成品类型单据");
            cbSimeProduct.setBounds(53, 136, 112, 20);
            cbSimeProduct.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return cbSimeProduct;
    }

    /**
     * This method initializes jCheckBox3
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbMachine() {
        if (cbMachine == null) {
            cbMachine = new JCheckBox();
            cbMachine.setText("设备类型单据");
            cbMachine.setBounds(53, 164, 101, 19);
            cbMachine.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                    12));
        }
        return cbMachine;
    }

    /**
     * This method initializes jCheckBox4
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbRemainMateriel() {
        if (cbRemainMateriel == null) {
            cbRemainMateriel = new JCheckBox();
            cbRemainMateriel.setText("边角料类型单据");
            cbRemainMateriel.setBounds(53, 190, 112, 21);
            cbRemainMateriel.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return cbRemainMateriel;
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
    private JButton getBtnBillMakeCustomNum() {
        if (btnBillMakeCustomNum == null) {
            btnBillMakeCustomNum = new JButton();
            btnBillMakeCustomNum.setVisible(false);
            btnBillMakeCustomNum.setBounds(45, 409, 130, 26);
            btnBillMakeCustomNum.setText("生成折算报关数量");
            btnBillMakeCustomNum.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
            btnBillMakeCustomNum
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            if (JOptionPane.showConfirmDialog(
                                    DgBillMakeCustomNum.this,
                                    "生成折算报关数量\n确定要继续吗？？", "警告!!!",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                                new MakeThread(false).start();
                            }
                        }
                    });
        }
        return btnBillMakeCustomNum;
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
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbBadProduct() {
        if (cbBadProduct == null) {
            cbBadProduct = new JCheckBox();
            cbBadProduct.setBounds(new java.awt.Rectangle(53, 217, 109, 18));
            cbBadProduct.setText("残次品类型单据");
            cbBadProduct.setFont(new Font("Dialog", Font.PLAIN, 12));
        }
        return cbBadProduct;
    }

    /**
     * This method initializes btnSetup
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSetup() {
        if (btnSetup == null) {
            btnSetup = new JButton();
            btnSetup.setBounds(new java.awt.Rectangle(305, 406, 88, 26));
            btnSetup.setText("任务设置");
            btnSetup.setVisible(false);
        }
        return btnSetup;
    }

    /**
     * 获得选择类型
     * 
     * @return
     */
    private TempMaterielTypeSetup getTempMaterielTypeSetup() {
        TempMaterielTypeSetup temp = new TempMaterielTypeSetup();
        if (this.cbProduct.isSelected()) {
            temp.setIsProuduct(true);
        }
        if (this.cbMateriel.isSelected()) {
            temp.setIsMateriel(true);
        }
        if (this.cbMachine.isSelected()) {
            temp.setIsMachine(true);
        }
        if (this.cbSimeProduct.isSelected()) {
            temp.setIsSemiProduct(true);
        }
        if (this.cbBadProduct.isSelected()) {
            temp.setIsBadProduct(true);
        }
        if (this.cbRemainMateriel.isSelected()) {
            temp.setIsRemainMateriel(true);
        }
        if (this.cbIsMakeCustomsInfo.isSelected()) {
            temp.setIsMakeCustomsInfo(true);
        }
        if (this.cbIsUpdateHsAmount.isSelected()) {
            temp.setIsUpdateHsAmount(true);
        }
        return temp;
    }

    /**
     * 生成单据的折算报关数量
     * 
     * @author Administrator
     * 
     */
    class MakeThread extends Thread {
        private boolean isBatch = false;

        public MakeThread(boolean isBatch) {
            this.isBatch = isBatch;
        }

        public void run() {
            //
            // 用于标识这个对话框的ID
            //
            UUID uuid = UUID.randomUUID();
            final String flag = uuid.toString();
            String info = "";
            long beginTime = System.currentTimeMillis() ;
            try {
                final CasAction casAction = (CasAction) CommonVars
                        .getApplicationContext().getBean("casAction");
                ProgressTask progressTask = new ProgressTask() {
                    protected void setClientTipMessage() {
                        String tipMessage = casAction
                                .getMakeBillHsAmountTipMessage();
                        CommonProgress.setMessage(flag, tipMessage);
                    }
                };
                CommonProgress.showProgressDialog(flag,
                        DgBillMakeCustomNum.this, false, progressTask, 5000);
                CommonProgress.setMessage(flag, "系统正在生成单据的折算报关数量，请稍后...");
                btnBillMakeCustomNum.setEnabled(false);
                btnBatch.setEnabled(false);
                if (isBatch == false) {
                    casAction.makeBillHsAmount(new Request(CommonVars
                            .getCurrUser()), getTempMaterielTypeSetup());
                } else {
                	System.out.println("aaaa=="+getTempMaterielTypeSetup());
                    info = casAction.makeBillHsAmountByBatch(new Request(CommonVars
                            .getCurrUser()), getTempMaterielTypeSetup());
                }
                CommonProgress.closeProgressDialog(flag);
                info += (info.equals("")?"":"\n") + "任务完成,共用时:"+ (System.currentTimeMillis()-beginTime) +" 毫秒 ";
                JOptionPane.showMessageDialog(DgBillMakeCustomNum.this,
                        info, "提示", 2);
            } catch (Exception e) {
                CommonProgress.closeProgressDialog(flag);
                JOptionPane.showMessageDialog(DgBillMakeCustomNum.this,
                        "生成单据的折算报关数量失败！！！" + e.getMessage(), "提示", 2);
            }            
            btnBillMakeCustomNum.setEnabled(true);
            btnBatch.setEnabled(true);
        }
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsMakeCustomsInfo() {
        if (cbIsMakeCustomsInfo == null) {
            cbIsMakeCustomsInfo = new JCheckBox();
            cbIsMakeCustomsInfo.setSelected(true);
            cbIsMakeCustomsInfo.setVisible(false);
            cbIsMakeCustomsInfo.setBounds(new java.awt.Rectangle(293, 347, 193,
                    23));
            cbIsMakeCustomsInfo.setText("是否生成单据对应的报关资料");
            cbIsMakeCustomsInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
        }
        return cbIsMakeCustomsInfo;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsUpdateHsAmount() {
        if (cbIsUpdateHsAmount == null) {
            cbIsUpdateHsAmount = new JCheckBox();
            cbIsUpdateHsAmount.setBounds(new java.awt.Rectangle(293, 314, 336,
                    24));
            cbIsUpdateHsAmount.setSelected(true);
            cbIsUpdateHsAmount.setText("是否强制(已存在折算数量的单据)生成折算报关数量");
            cbIsUpdateHsAmount.setFont(new Font("Dialog", Font.PLAIN, 12));
        }
        return cbIsUpdateHsAmount;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnBatch() {
        if (btnBatch == null) {
            btnBatch = new JButton();
            btnBatch.setBounds(new Rectangle(343, 406, 190, 26));
            btnBatch.setText("批量生成折算报关数量");
            btnBatch.setFont(new Font("Dialog", Font.PLAIN, 12));
            btnBatch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(DgBillMakeCustomNum.this,
                            "生成折算报关数量\n确定要继续吗？？", "警告!!!",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                        new MakeThread(true).start();
                    }
                }
            });
        }
        return btnBatch;
    }

}
