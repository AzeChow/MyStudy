/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter.parameter;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.LotControl;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.Request;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnLotControl extends PnErpBillParameterCommon {

    private JPanel              jPanel                           = null;
    private JCheckBox           cbIsWorkshopImportNeedLot        = null;
    private JCheckBox           cbIsWorkshopBackNeedLot          = null;
    private JCheckBox           cbIsDirectExportNeedLot          = null;
    private JCheckBox           cbIsTransferFactoryExportNeedLot = null;
    private JPanel              jPanel1                          = null;
    private JButton             btnEdit                          = null;
    private JButton             btnAvailability                  = null;
    private JButton             btnExit                          = null;
    private JLabel              jLabel                           = null;
    private static PnLotControl pnLotControl                     = null;
    private LotControl          lotControl                       = null;

    /**
     * This method initializes
     * 
     */
    private PnLotControl() {
        super();
        lotControl = ErpBillParameterCommonVars.getLotControl();
        initialize();
        showData();
    }

    public static PnLotControl getInstance() {
        if (pnLotControl == null) {
            pnLotControl = new PnLotControl();
        }
        return pnLotControl;
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        jLabel1 = new JLabel();
        jLabel1.setBounds(22, 406, 140, 26);
        jLabel1.setText("");
        //(*)需重启才能生效!!!
        jLabel = new JLabel();
        this.setLayout(null);
        this.setSize(631, 446);
        jLabel.setBounds(3, 31, 586, 21);
        jLabel.setText("除必须输入的制单号的单据（领料单，成品入库单）外,你还可以勾选指定以下单据类型必须输入制单号");
        jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));      
       
        this.add(getJPanel(), null);
        this.add(getJPanel1(), null);
        this.add(getCbIsWorkshopImportNeedLot(), null);
        this.add(getCbIsWorkshopBackNeedLot(), null);
        this.add(getCbIsDirectExportNeedLot(), null);
        this.add(getCbIsTransferFactoryExportNeedLot(), null);
        this.add(getBtnEdit(), null);
        this.add(getBtnAvailability(), null);
        this.add(getBtnExit(), null);
        this.add(jLabel, null);

        this.add(jLabel1, null);
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
            jPanel.setBounds(3, 55, 625, 3);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return jPanel;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsWorkshopImportNeedLot() {
        if (cbIsWorkshopImportNeedLot == null) {
            cbIsWorkshopImportNeedLot = new JCheckBox();
            cbIsWorkshopImportNeedLot.setText("车间入库");
            cbIsWorkshopImportNeedLot.setBounds(53, 82, 112, 21);
            cbIsWorkshopImportNeedLot.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return cbIsWorkshopImportNeedLot;
    }

    /**
     * This method initializes jCheckBox1
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsWorkshopBackNeedLot() {
        if (cbIsWorkshopBackNeedLot == null) {
            cbIsWorkshopBackNeedLot = new JCheckBox();
            cbIsWorkshopBackNeedLot.setText("车间返回");
            cbIsWorkshopBackNeedLot.setBounds(53, 110, 161, 18);
            cbIsWorkshopBackNeedLot.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return cbIsWorkshopBackNeedLot;
    }

    /**
     * This method initializes jCheckBox2
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsDirectExportNeedLot() {
        if (cbIsDirectExportNeedLot == null) {
            cbIsDirectExportNeedLot = new JCheckBox();
            cbIsDirectExportNeedLot.setText("直接出口");
            cbIsDirectExportNeedLot.setBounds(53, 136, 230, 20);
            cbIsDirectExportNeedLot.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 12));
        }
        return cbIsDirectExportNeedLot;
    }

    /**
     * This method initializes jCheckBox3
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getCbIsTransferFactoryExportNeedLot() {
        if (cbIsTransferFactoryExportNeedLot == null) {
            cbIsTransferFactoryExportNeedLot = new JCheckBox();
            cbIsTransferFactoryExportNeedLot.setText("结转出口");
            cbIsTransferFactoryExportNeedLot.setBounds(53, 164, 198, 19);
            cbIsTransferFactoryExportNeedLot.setFont(new java.awt.Font(
                    "Dialog", java.awt.Font.PLAIN, 12));
        }
        return cbIsTransferFactoryExportNeedLot;
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
            btnAvailability.setText("生效");
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
        jPanel1.setBounds(3, 55, this.getWidth() - 6, 3);

        btnExit.setBounds(this.getWidth() - 92, this.getHeight() - 40, 68, 26);
        btnAvailability.setBounds(
                this.getWidth() - 92 - btnExit.getWidth() - 5,
                this.getHeight() - 40, 68, 26);
        btnEdit.setBounds(this.getWidth() - 92 - btnExit.getWidth()
                - btnAvailability.getWidth() - 10, this.getHeight() - 40, 68,
                26);
        jLabel1.setBounds(jLabel1.getX(),btnEdit.getY(),jLabel1.getWidth(),jLabel1.getHeight());
    }

    protected void paintComponent(Graphics g) {
        initUIComponents();
        super.paintComponent(g);
    }

    /**
     * 修改
     */
    private void edit() {
    	//
		// check authority
		//
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkErpBillCenterParameterByUpdate(new Request(
				CommonVars.getCurrUser()));
		
        super.setContainerEnabled(this, true);
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
        lotControl = casAction.saveLotControl(new Request(CommonVars
                .getCurrUser()), this.lotControl);
        ErpBillParameterCommonVars.setLotControl(lotControl);
        super.setContainerEnabled(this, false);
        this.dataSate = DataState.BROWSE;
        setState();
    }

    /**
     * 填充数据
     * 
     */
    private void fillData() {
        this.lotControl
                .setIsWorkshopImportNeedLot(this.cbIsWorkshopImportNeedLot
                        .isSelected());
        this.lotControl.setIsWorkshopBackNeedLot(this.cbIsWorkshopBackNeedLot
                .isSelected());
        this.lotControl.setIsDirectExportNeedLot(this.cbIsDirectExportNeedLot
                .isSelected());
        this.lotControl
                .setIsTransferFactoryExportNeedLot(this.cbIsTransferFactoryExportNeedLot
                        .isSelected());
    }

    /**
     * 显示数据
     * 
     */
    private void showData() {
        this.cbIsWorkshopImportNeedLot.setSelected(this.lotControl
                .getIsWorkshopImportNeedLot());
        this.cbIsWorkshopBackNeedLot.setSelected(this.lotControl
                .getIsWorkshopBackNeedLot());
        this.cbIsDirectExportNeedLot.setSelected(this.lotControl
                .getIsDirectExportNeedLot());
        this.cbIsTransferFactoryExportNeedLot.setSelected(this.lotControl
                .getIsTransferFactoryExportNeedLot());
        setState();
        super.setContainerEnabled(this, false);
    }

    private int dataSate = DataState.BROWSE; 
	private JLabel jLabel1 = null;
    /**
     * 设置状态
     * 
     */
    private void setState() {
        btnEdit.setEnabled(dataSate==DataState.BROWSE);
        btnAvailability.setEnabled(dataSate!=DataState.BROWSE);
    }

}
