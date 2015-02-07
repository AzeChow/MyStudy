/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.parameter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.authority.CasParameterAction;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnBillCorrespondingControl extends PnCasParameterCommon {

    private JPanel                            jPanel                     = null;
    private JCheckBox                         cbIsTransferBack           = null;
    private JPanel                            jPanel1                    = null;
    private JButton                           btnEdit                    = null;
    private JButton                           btnAvailability            = null;
    private JButton                           btnExit                    = null;
    private JLabel                            jLabel                     = null;
    private JLabel                            jLabel1                    = null;
    private JButton                           btnInit                    = null;
    private JRadioButton                      rbIsSystemControl          = null;
    private JRadioButton                      rbIsSpecialControl         = null;
    private JRadioButton                      rbIsHandContrl             = null;
    private JLabel                            jLabel2                    = null;
    private JLabel                            jLabel3                    = null;
    private JLabel                            jLabel4                    = null;
    private JLabel                            jLabel5                    = null;
    private JLabel                            jLabel6                    = null;
    private JLabel                            jLabel7                    = null;
    private ButtonGroup                       buttonGroup                = null;
    private static PnBillCorrespondingControl pnBillCorrespondingControl = null;
    private BillCorrespondingControl          billCorrespondingControl   = null;
    private CasParameterAction                casParameterAction         = null;

    /**
     * This method initializes
     * 
     */
    private PnBillCorrespondingControl() {
        super();
        casParameterAction = (CasParameterAction) CommonVars.getApplicationContext()
        .getBean("casParameterAction");
        billCorrespondingControl = CasCommonVars.getBillCorrespondingControl();
        initialize();
        this.getButtonGroup();
        showData();
    }

    public static PnBillCorrespondingControl getInstance() {
        if (pnBillCorrespondingControl == null) {
            pnBillCorrespondingControl = new PnBillCorrespondingControl();
        }
        return pnBillCorrespondingControl;
    }

    /**
     * This method initializes buttonGroup
     * 
     * @return javax.swing.ButtonGroup
     */
    private ButtonGroup getButtonGroup() {
        if (buttonGroup == null) {
            buttonGroup = new ButtonGroup();
            setAllRadioButtonProperty(this);
        }
        return buttonGroup;
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
                JRadioButton radioButton = (JRadioButton) temp;
                // radioButton.setOpaque(false);
                // radioButton.addActionListener(new RadioActionListner());
                buttonGroup.add(radioButton);
            } else {
                setAllRadioButtonProperty(temp);
            }
        }
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        jLabel8 = new JLabel();
        jLabel7 = new JLabel();
        jLabel6 = new JLabel();
        jLabel5 = new JLabel();
        jLabel4 = new JLabel();
        jLabel3 = new JLabel();
        jLabel2 = new JLabel();
        jLabel7.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel6.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel5.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel4.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel3.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel2.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel1 = new JLabel();
        jLabel1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel2.setBounds(77, 110, 366, 21);
        jLabel2.setText("不能手工输入或导入对应关系,");
        jLabel3.setBounds(77, 131, 368, 21);
        jLabel3.setText("单据与报关单的对应关系能否成立以及对数量由程序控制.");
        jLabel4.setBounds(77, 193, 397, 21);
        jLabel4.setText("不能手工输入或导入对应关系,");
        jLabel5.setBounds(77, 213, 407, 21);
        jLabel5.setText("单据与报关单的对应关系能否成立用户控制,但对数量由程序控制.");
        jLabel6.setBounds(77, 272, 396, 21);
        jLabel6.setText("可以在单据手工输入对应关系或从数据接口导入对应关系，");
        jLabel7.setBounds(77, 293, 398, 21);
        jLabel7.setText("系统不对对应做任何控制.");
        this.add(getRbIsSystemControl(), null);
        this.add(getRbIsSpecialControl(), null);
        this.add(getRbIsHandContrl(), null);
        this.add(jLabel2, null);
        this.add(jLabel3, null);
        this.add(jLabel4, null);
        this.add(jLabel5, null);
        this.add(jLabel6, null);
        this.add(jLabel7, null);
        jLabel = new JLabel();
        this.setLayout(null);
        this.setSize(631, 446);
        jLabel.setBounds(3, 23, 425, 21);
        jLabel.setText("以下三种控制可以由低到高(从选项1到选项3)进行转换,但反之不行，除非进行");
        jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        jLabel1.setBounds(3, 4, 436, 21);
        jLabel1.setText("对应关系的控制方法关系到对应能否使用手工输入，接口导入和界面选取的方式。");
        jLabel8.setBounds(22, 406, 140, 26);
        jLabel8.setText("");
        // (*)需重启才能生效!!!
        this.add(getJPanel(), null);
        this.add(getJPanel1(), null);
        this.add(getCbIsTransferBack(), null);
        this.add(getBtnEdit(), null);
        this.add(getBtnAvailability(), null);
        this.add(getBtnExit(), null);
        this.add(jLabel, null);

        this.add(jLabel1, null);
        this.add(getBtnInit(), null);
        this.add(jLabel8, null);
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setBounds(2, 65, 625, 3);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return jPanel;
    }

    /**
     * This method initializes jCheckBox3
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsTransferBack() {
        if (cbIsTransferBack == null) {
            cbIsTransferBack = new JCheckBox();
            cbIsTransferBack.setText("结转退货单参与对应");
            cbIsTransferBack.setBounds(54, 341, 136, 19);
            cbIsTransferBack.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return cbIsTransferBack;
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
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnEdit() {
        if (btnEdit == null) {
            btnEdit = new JButton();
            btnEdit.setBounds(387, 406, 68, 26);
            btnEdit.setText("修改");
            btnEdit
                    .setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                            12));
            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    edit();
                }
            });
        }
        return btnEdit;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnAvailability() {
        if (btnAvailability == null) {
            btnAvailability = new JButton();
            btnAvailability.setBounds(463, 406, 68, 26);
            btnAvailability.setText("保存");
            btnAvailability.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
            btnAvailability
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            availability();
                        }
                    });
        }
        return btnAvailability;
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
                    close();
                }
            });
        }
        return btnExit;
    }

    /**
     * 初始化组件
     * 
     */
    private void initUIComponents() {
        jPanel.setBounds(3, this.getHeight() - 50, this.getWidth() - 6, 3);
        jPanel1.setBounds(3, 66, this.getWidth() - 6, 3);

        btnExit.setBounds(this.getWidth() - 92, this.getHeight() - 40, 68, 26);
        btnAvailability.setBounds(
                this.getWidth() - 92 - btnExit.getWidth() - 5,
                this.getHeight() - 40, 68, 26);
        btnEdit.setBounds(this.getWidth() - 92 - btnExit.getWidth()
                - btnAvailability.getWidth() - 10, this.getHeight() - 40, 68,
                26);
        jLabel8.setBounds(jLabel8.getX(), btnEdit.getY(), jLabel8.getWidth(),
                jLabel8.getHeight());
    }

    protected void paintComponent(Graphics g) {
        initUIComponents();
        super.paintComponent(g);
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnInit() {
        if (btnInit == null) {
            btnInit = new JButton();
            btnInit.setText("对应关系初始化");
            btnInit
                    .setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                            12));
            btnInit.setBounds(3, 44, 120, 21);
            btnInit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    new InitDataThread().start();
                }
            });

        }
        return btnInit;
    }

    /**
     * This method initializes jRadioButton
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbIsSystemControl() {
        if (rbIsSystemControl == null) {
            rbIsSystemControl = new JRadioButton();
            rbIsSystemControl.setBounds(54, 91, 124, 19);
            rbIsSystemControl.setText("1. 系统自动控制");
            rbIsSystemControl.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return rbIsSystemControl;
    }

    /**
     * This method initializes jRadioButton1
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbIsSpecialControl() {
        if (rbIsSpecialControl == null) {
            rbIsSpecialControl = new JRadioButton();
            rbIsSpecialControl.setBounds(54, 175, 153, 19);
            rbIsSpecialControl.setText("2. 特殊控制");
            rbIsSpecialControl.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return rbIsSpecialControl;
    }

    /**
     * This method initializes jRadioButton2
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getRbIsHandContrl() {
        if (rbIsHandContrl == null) {
            rbIsHandContrl = new JRadioButton();
            rbIsHandContrl.setBounds(54, 255, 110, 19);
            rbIsHandContrl.setText("3. 手工控制");
            rbIsHandContrl.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return rbIsHandContrl;
    }

    /**
     * 修改
     */
    private void edit() {
        casParameterAction.checkCasParameterByUpdate(new Request(CommonVars.getCurrUser()));
        this.dataSate = DataState.EDIT;
        setState();

    }

    /**
     * 生效
     */
    private void availability() {
        CasAction casAction = (CasAction) CommonVars.getApplicationContext()
                .getBean("casAction");
        fillData();
        billCorrespondingControl = casAction.saveBillCorrespondingControl(
                new Request(CommonVars.getCurrUser()),
                this.billCorrespondingControl);
        CasCommonVars.setBillCorrespondingControl(billCorrespondingControl);
        this.dataSate = DataState.BROWSE;
        setState();
    }

    /**
     * 填充数据
     * 
     */
    private void fillData() {
        this.billCorrespondingControl.setIsHandContrl(this.rbIsHandContrl
                .isSelected());
        this.billCorrespondingControl.setIsSystemControl(this.rbIsSystemControl
                .isSelected());
        this.billCorrespondingControl
                .setIsSpecialControl(this.rbIsSpecialControl.isSelected());
        this.billCorrespondingControl.setIsTransferBack(this.cbIsTransferBack
                .isSelected());

    }

    /**
     * 显示数据
     * 
     */
    private void showData() {
        this.rbIsHandContrl.setSelected(this.billCorrespondingControl
                .getIsHandContrl());
        this.rbIsSystemControl.setSelected(this.billCorrespondingControl
                .getIsSystemControl());
        this.rbIsSpecialControl.setSelected(this.billCorrespondingControl
                .getIsSpecialControl());
        this.cbIsTransferBack.setSelected(this.billCorrespondingControl
                .getIsTransferBack());
        setState();
    }

    private int    dataSate = DataState.BROWSE;

    private JLabel jLabel8  = null;

    /**
     * 设置状态
     * 
     */
    private void setState() {
        btnEdit.setEnabled(dataSate == DataState.BROWSE);
        btnInit.setEnabled(dataSate != DataState.BROWSE);
        btnAvailability.setEnabled(dataSate != DataState.BROWSE);
        this.rbIsHandContrl.setEnabled(dataSate != DataState.BROWSE);
        this.rbIsSystemControl.setEnabled(!(this.billCorrespondingControl
                .getIsSpecialControl() == true || this.billCorrespondingControl
                .getIsHandContrl() == true)
                && dataSate != DataState.BROWSE);
        this.rbIsSpecialControl.setEnabled(!(this.billCorrespondingControl
                .getIsHandContrl() == true)
                && dataSate != DataState.BROWSE);
        this.cbIsTransferBack.setEnabled(dataSate != DataState.BROWSE);
    }

    class InitDataThread extends Thread {
        public void run() {
            initData();
        }
    }

    /**
     * 对应关系初始化
     * 
     */
    private void initData() {
        String year = CasCommonVars.getYear();
        String info = "重要!!!!!!!! \n\n对应关系初始化是将本年度【" + year
                + "】所有单据对应关系清空,\n然后允许您重新设定单据对应控制选项.\n\n确定要继续吗？？";
        if (JOptionPane.showConfirmDialog(FmMain.getInstance(), info, "警告!!!",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                CommonProgress.showProgressDialog();
                CommonProgress.setMessage("系统正在进行对应关系初始化，请稍后...");
                billCorrespondingControl.setIsHandContrl(false);//手动控制
                billCorrespondingControl.setIsSystemControl(true);//系统自动控制
                billCorrespondingControl.setIsSpecialControl(false);//特殊控制
                CasAction casAction = (CasAction) CommonVars
                        .getApplicationContext().getBean("casAction");
                //
                // 所有单据对应关系清空代码
                //
                casAction.deleteBillCorresponding(new Request(CommonVars
                        .getCurrUser()));
                billCorrespondingControl = casAction
                        .saveBillCorrespondingControl(new Request(CommonVars
                                .getCurrUser()), billCorrespondingControl);
                CasCommonVars
                        .setBillCorrespondingControl(billCorrespondingControl);
                setState();
                CommonProgress.closeProgressDialog();
                JOptionPane
                        .showMessageDialog(FmMain.getInstance(), "对应关系初始化完成!!",
                                "提示!!!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                CommonProgress.closeProgressDialog();
                throw new RuntimeException(e);
            }
        }
    }

} // @jve:decl-index=0:visual-constraint="9,7"
