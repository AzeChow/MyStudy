/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.authority.CasSpecifControlAction;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JFrameBase;
import com.bestway.ui.winuicontrol.JFrameBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmSpecificControl extends JInternalFrameBase {

    private javax.swing.JPanel     jContentPane                 = null;
    private JPanel                 jPanel2                      = null;
    private JPanel                 jPanel3                      = null;
    private JRadioButton           rbEditBillPrice              = null;
    private JRadioButton           rbBillCorresponding          = null;
    private JRadioButton           rbHandworkBatchCorresponding = null;
    private JRadioButton           rbBillCorrespondingSearch    = null;
    private ButtonGroup            buttonGroup                  = null;
    private JPanel                 jPanel                       = null;
    private JPanel                 jPanel1                      = null;
    private JPanel                 jPanel4                      = null;
    private JPanel                 jPanel5                      = null;
    private JButton                btnExit                      = null;
    private JRadioButton           rbFinanceBatchWriteAccount   = null;
    private JRadioButton           rbBigBatchDeleteBill         = null;
    private JRadioButton           rbPkCorr                     = null;
    private JRadioButton           rbBillMakeCustomNum          = null;
    private JRadioButton           rbHalfProductManager         = null;
    private CasSpecifControlAction casSpecifControlAction       = null;

    /**
     * This is the default constructor
     */
    public FmSpecificControl() {
        super();
        casSpecifControlAction = (CasSpecifControlAction) CommonVars.getApplicationContext()
            .getBean("casSpecifControlAction");
        initialize();
        setState();

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("海关帐特殊控制");
        this.setSize(730, 533);
        this.setContentPane(getJContentPane());
        this.getButtonGroup();
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
            jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
            jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            jPanel2 = new JPanel();
            jPanel2.setLayout(null);
            jPanel2.setOpaque(false);
            jPanel2
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "单据",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12),
                                    java.awt.Color.black));
            jPanel2.setBounds(28, 20, 654, 104);
            jPanel2.add(getRbEditBillPrice(), null);
            jPanel2.add(getRbFinanceBatchWriteAccount(), null);
            jPanel2.add(getRbBigBatchDeleteBill(), null);
            jPanel2.setOpaque(false);
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
            jPanel3.setOpaque(false);
            jPanel3.setLayout(null);
            jPanel3
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "单据对应",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12),
                                    java.awt.Color.black));
            jPanel3.setBounds(28, 145, 654, 108);
            jPanel3.add(getRbBillCorresponding(), null);
            jPanel3.add(getRbHandworkBatchCorresponding(), null);
            jPanel3.add(getRbBillCorrespondingSearch(), null);
            jPanel3.setOpaque(false);
            jPanel3.add(getRbPkCorr(), null);
        }
        return jPanel3;
    }

    /**
     * This method initializes jRadioButton
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbEditBillPrice() {
        if (rbEditBillPrice == null) {
            rbEditBillPrice = new JRadioButton();
            // rbEditBillPrice.setEnabled(false);
            rbEditBillPrice.setText("修改单据单价");
            rbEditBillPrice.setBounds(437, 58, 111, 21);
            rbEditBillPrice.setOpaque(false);
            rbEditBillPrice.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return rbEditBillPrice;
    }

    /**
     * This method initializes jRadioButton4
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbBillCorresponding() {
        if (rbBillCorresponding == null) {
            rbBillCorresponding = new JRadioButton();
            rbBillCorresponding.setText("单据对应");
            rbBillCorresponding.setBounds(109, 30, 102, 20);
            rbBillCorresponding.setOpaque(false);
            rbBillCorresponding.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return rbBillCorresponding;
    }

    /**
     * This method initializes jRadioButton5
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbHandworkBatchCorresponding() {
        if (rbHandworkBatchCorresponding == null) {
            rbHandworkBatchCorresponding = new JRadioButton();
            rbHandworkBatchCorresponding.setText("手工批量对应");
            rbHandworkBatchCorresponding.setBounds(438, 30, 113, 20);
            rbHandworkBatchCorresponding.setOpaque(false);
            rbHandworkBatchCorresponding.setCursor(new Cursor(
                    Cursor.HAND_CURSOR));
        }
        return rbHandworkBatchCorresponding;
    }

    /**
     * This method initializes jRadioButton6
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbBillCorrespondingSearch() {
        if (rbBillCorrespondingSearch == null) {
            rbBillCorrespondingSearch = new JRadioButton();
            rbBillCorrespondingSearch.setText("报关单和单据对应查询");
            rbBillCorrespondingSearch.setBounds(109, 58, 163, 20);
            rbBillCorrespondingSearch.setOpaque(false);
            rbBillCorrespondingSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return rbBillCorrespondingSearch;
    }

    /**
     * This method initializes buttonGroup
     * 
     * @return javax.swing.ButtonGroup
     */
    private ButtonGroup getButtonGroup() {
        if (buttonGroup == null) {
            buttonGroup = new ButtonGroup();
            setAllRadioButtonProperty(this.jPanel1);
        }
        return buttonGroup;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            javax.swing.JLabel jLabel2 = new JLabel();

            javax.swing.JLabel jLabel1 = new JLabel();

            javax.swing.JLabel jLabel = new JLabel();
            jPanel.setLayout(new BorderLayout());
            jLabel.setText("海关帐特殊控制");
            jLabel.setForeground(new java.awt.Color(255, 153, 0));
            jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jPanel.setBackground(java.awt.Color.white);
            jLabel1.setText("");
            jLabel1
                    .setIcon(new ImageIcon(
                            getClass()
                                    .getResource(
                                            "/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
            jLabel2.setText("");
            jLabel2.setIcon(new ImageIcon(getClass().getResource(
                    "/com/bestway/bcus/client/resources/images/titlepic.jpg")));
            jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
            jPanel.add(jLabel1, java.awt.BorderLayout.WEST);
            jPanel.add(jLabel2, java.awt.BorderLayout.EAST);
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
            jPanel1.setLayout(null);
            jPanel1.add(getJPanel2(), null);
            jPanel1.add(getJPanel3(), null);
            jPanel1.add(getJPanel4(), null);
            jPanel1.add(getJPanel5(), null);
            jPanel1.add(getBtnExit(), null);
        }
        return jPanel1;
    }

    /**
     * This method initializes jRadioButton
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbPkCorr() {
        if (rbPkCorr == null) {
            rbPkCorr = new JRadioButton();
            rbPkCorr.setBounds(438, 58, 194, 21);
            rbPkCorr.setText("单据对应（PK单对应）");
            rbPkCorr.setOpaque(false);
            rbPkCorr.setCursor(new Cursor(Cursor.HAND_CURSOR));

        }
        return rbPkCorr;
    }

    /**
     * This method initializes rbBillMakeCustomNum
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbBillMakeCustomNum() {
        if (rbBillMakeCustomNum == null) {
            rbBillMakeCustomNum = new JRadioButton();
            rbBillMakeCustomNum.setBounds(new java.awt.Rectangle(109, 29, 171,
                    23));
            rbBillMakeCustomNum.setText("生成单据的折算报关数量");
            rbBillMakeCustomNum.setOpaque(false);
            rbBillMakeCustomNum.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        return rbBillMakeCustomNum;
    }

    /**
     * This method initializes jPanel4
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel4() {
        if (jPanel4 == null) {
            jPanel4 = new JPanel();
            jPanel4.setLayout(null);
            jPanel4.setOpaque(false);
            jPanel4.setBounds(28, 274, 652, 105);
            jPanel4
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "其它",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12), null));
            jPanel4.add(getRbBillMakeCustomNum(), null);
            jPanel4.add(getRbHalfProductManager(), null);
        }
        return jPanel4;
    }

    /**
     * This method initializes jPanel5
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel5() {
        if (jPanel5 == null) {
            jPanel5 = new JPanel();
            jPanel5.setOpaque(false);
            jPanel5.setBounds(14, 395, 682, 3);
            jPanel5
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return jPanel5;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setBounds(600, 411, 68, 26);
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
     * This method initializes jRadioButton10
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbFinanceBatchWriteAccount() {
        if (rbFinanceBatchWriteAccount == null) {
            rbFinanceBatchWriteAccount = new JRadioButton();
            rbFinanceBatchWriteAccount.setOpaque(false);
            rbFinanceBatchWriteAccount.setBounds(new java.awt.Rectangle(437,
                    31, 109, 21));
            rbFinanceBatchWriteAccount.setText("财务成批记帐");
        }
        return rbFinanceBatchWriteAccount;
    }

    /**
     * This method initializes jRadioButton11
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbBigBatchDeleteBill() {
        if (rbBigBatchDeleteBill == null) {
            rbBigBatchDeleteBill = new JRadioButton();
            rbBigBatchDeleteBill.setOpaque(false);
            rbBigBatchDeleteBill.setBounds(new Rectangle(111, 31, 199, 20));
            rbBigBatchDeleteBill.setText("大批量删除、回卷、生效单据");
        }
        return rbBigBatchDeleteBill;
    }

    /**
     * 初始化组件
     * 
     */
    private void initUIComponents() {
        jPanel2.setBounds(28, 10, this.getWidth() - 28 - 28, 105);
        jPanel3.setBounds(28, this.jPanel2.getY() + this.jPanel2.getHeight()
                + 10, this.getWidth() - 28 - 28, 105);
        jPanel4.setBounds(28, this.jPanel3.getY() + this.jPanel3.getHeight()
                + 10, this.getWidth() - 28 - 28, 105);

        btnExit.setBounds(this.jPanel1.getWidth() - 92, this.jPanel1
                .getHeight()
                - this.btnExit.getHeight() - 10, 68, 26);
        jPanel5.setBounds(3, this.btnExit.getY() - 13,
                this.jPanel1.getWidth() - 6, 3);

    }

    /**
     * 重画组件
     */
    protected void paintComponent(Graphics g) {
        initUIComponents();
        setState();
        super.paintComponent(g);
    }

    /**
     * 设置所有的RadioButton的属性和事件
     * 
     * @param component
     */
    private void setAllRadioButtonProperty(Component component) {
        if (!(component instanceof Container)) {
            return;
        }
        Container container = (Container) component;
        for (int i = 0; i < container.getComponentCount(); i++) {
            Component temp = container.getComponent(i);
            if (temp instanceof JRadioButton) {
                UUID uuid = UUID.randomUUID();
                final String flag = uuid.toString();
                JRadioButton radioButton = (JRadioButton) temp;
                radioButton.setOpaque(false);
                radioButton.setActionCommand(flag);
                radioButton.addActionListener(new RadioActionListner());
                buttonGroup.add(radioButton);
            } else {
                setAllRadioButtonProperty(temp);
            }
        }
    }

    /**
     * 单击监听类
     * 
     * @author ls
     * 
     */
    private class RadioActionListner implements ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            JComponent component = (JComponent) e.getSource();
            component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
//            try {
                
                // 大批量删除或回卷或生效单据 1
                // 财务成批记帐 2
                // 修改单据单价 3
                // 单据对应 4
                // 手工批量对应 5
                // 报关单和单据对应查询 6
                // 单据对应--PK单对应 7 
                // 生成单据的折算报关数量 8
                // 半成品委外管理 9
                
                
                if (rbBigBatchDeleteBill.isSelected()) { // 大批量删除或回卷或生效单据
                    //
                    // check authority
                    //
                    casSpecifControlAction.check1ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbBigBatchDeleteBill.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgBigBatchDeleteBill();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                } else if (rbFinanceBatchWriteAccount.isSelected()) {// 财务成批记帐

                    //
                    // check authority
                    //
                    casSpecifControlAction.check2ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbFinanceBatchWriteAccount.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgFinanceBatchWriteAccount();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                }else if (rbEditBillPrice.isSelected()) { // 修改单据单价

                    //
                    // check authority
                    //
                    casSpecifControlAction.check3ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbEditBillPrice.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgEditBillPrice();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                } else  if (rbBillCorresponding.isSelected()) { // 单据对应
                    //
                    // check authority
                    //
                    casSpecifControlAction.check4ByBrower(new Request(CommonVars.getCurrUser()));

                    String flag = rbBillCorresponding.getActionCommand();
                    JFrameBase fm = JFrameBaseHelper.getJFrameBaseByFlag(flag);
                    if (fm == null) {
                        fm = new FmBillCorresponding();
                        JFrameBaseHelper.putJDialogBaseToFlag(flag, fm);
                        fm.setVisible(true);
                    } else {
                        fm.setVisibleNoChange(true);
                        JFrameBaseHelper.deiconify(fm);
                    }

                } else if (rbHandworkBatchCorresponding.isSelected()) {// 手工批量对应
                    //
                    // check authority
                    //
                    casSpecifControlAction.check5ByBrower(new Request(CommonVars.getCurrUser()));

                    String flag = rbHandworkBatchCorresponding
                            .getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgHandworkBatchCorresponding();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                } else if (rbBillCorrespondingSearch.isSelected()) {// 单据对应查询

                    //
                    // check authority
                    //
                    casSpecifControlAction.check6ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbBillCorrespondingSearch.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgBillCorrespondingSearch();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                } else if (rbPkCorr.isSelected()) {// 单据对应(PK单对应)

                    //
                    // check authority
                    //
                    casSpecifControlAction.check7ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbPkCorr.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgBillCorresByPk();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                } else if (rbBillMakeCustomNum.isSelected()) {// 生成单据的折算报关数量

                    //
                    // check authority
                    //
                    casSpecifControlAction.check8ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbBillMakeCustomNum.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgBillMakeCustomNum();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }

                } else if (rbHalfProductManager.isSelected()) {// 半成品转料件

                    //
                    // check authority
                    //
                    casSpecifControlAction.check9ByBrower(new Request(CommonVars.getCurrUser()));
                    
                    String flag = rbHalfProductManager.getActionCommand();
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgHalfProductManager();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag, dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }
                }
//            } catch (Exception ex) {
//            	System.out.println(ex);
//                throw new RuntimeException(ex.getMessage());
//            } finally {
//            	
//                component.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            }

        }
    }

    /**
     * 设置事件状态
     * 
     */
    private void setState() {
        if (CasCommonVars.getBillCorrespondingControl().getIsHandContrl() != null
                && CasCommonVars.getBillCorrespondingControl()
                        .getIsHandContrl() == true) {
            rbBillCorresponding.setEnabled(false);
            rbHandworkBatchCorresponding.setEnabled(true);
        } else {
            rbBillCorresponding.setEnabled(true);
            rbHandworkBatchCorresponding.setEnabled(false);
        }
    }

    /**
     * This method initializes rbHalfProductManager
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbHalfProductManager() {
        if (rbHalfProductManager == null) {
            rbHalfProductManager = new JRadioButton();
            rbHalfProductManager.setBounds(new java.awt.Rectangle(438, 29, 137,
                    23));
            rbHalfProductManager.setText("半成品委外管理");
        }
        return rbHalfProductManager;
    }

}