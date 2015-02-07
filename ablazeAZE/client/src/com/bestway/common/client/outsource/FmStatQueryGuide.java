/*
 * Created on 2004-9-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.outsource;

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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.Request;
import com.bestway.common.outsource.authority.OutsourceAuthority;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JDialogBaseHelper;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Font;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmStatQueryGuide extends JInternalFrameBase {

    private javax.swing.JPanel jContentPane   = null;
    private JPanel             jPanel         = null;
    private JPanel             jPanel1        = null;
    private JRadioButton       jRadioButton6  = null;
    private JPanel             jPanel2        = null;
    private ButtonGroup        group          = new ButtonGroup();
    private JPanel             PnBottom       = null;
    private JButton            btnExit        = null;

    /**
     * This is the default constructor
     */
    public FmStatQueryGuide() {
        super();
        //
		// check authority
		//
		OutsourceAuthority outsourceAuthority = (OutsourceAuthority) CommonVars
				.getApplicationContext().getBean("outsourceAuthority");
		outsourceAuthority.checkOutsourceStatReportByBrower(new Request(
				CommonVars.getCurrUser()));
        initialize();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("委外统计报表");
        this.setSize(710, 517);
        this.setContentPane(getJContentPane());    
        this.setAllRadioButtonProperty(jContentPane);
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
                final String flag = uuid.toString() ;
                JRadioButton radioButton = (JRadioButton) temp;
                radioButton.setOpaque(false);
                radioButton.setActionCommand(flag);
                radioButton.addActionListener(new RadioActionListner());
                group.add(radioButton);
            } else {
                setAllRadioButtonProperty(temp);
            }
        }
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
            jContentPane.setFont(new java.awt.Font("Dialog",
                    java.awt.Font.PLAIN, 18));
            jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
            jContentPane.add(getJPanel1(), java.awt.BorderLayout.CENTER);
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
            javax.swing.JLabel jLabel2 = new JLabel();

            javax.swing.JLabel jLabel1 = new JLabel();

            javax.swing.JLabel jLabel = new JLabel();

            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jLabel.setText("委外统计报表");
            jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
            jLabel.setForeground(new java.awt.Color(255, 153, 0));
            jPanel.setBackground(java.awt.Color.white);
            jPanel
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            jPanel
                    .setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
                            18));
            jLabel1.setText("");
            jLabel1.setIcon(new ImageIcon(getClass().getResource(
                    "/com/bestway/bcus/client/resources/images/titlepic.jpg")));
            jLabel2.setText("");
            jLabel2
                    .setIcon(new ImageIcon(
                            getClass()
                                    .getResource(
                                            "/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
            jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
            jPanel.add(jLabel1, java.awt.BorderLayout.EAST);
            jPanel.add(jLabel2, java.awt.BorderLayout.WEST);
        }
        return jPanel;
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
            jPanel1.setLayout(null);
            jPanel1.add(getJPanel2(), null);
            jPanel1.add(getPnBottom(), null);
            jPanel1.add(getBtnExit(), null);
        }
        return jPanel1;
    }

    /**
     * 
     * This method initializes jRadioButton6
     * 
     * 
     * 
     * @return javax.swing.JRadioButton
     * 
     */
    private JRadioButton getJRadioButton6() {
        if (jRadioButton6 == null) {
            jRadioButton6 = new JRadioButton();
            jRadioButton6.setText("外发加工帐");
            jRadioButton6.setBounds(72, 37, 137, 21);
        }
        return jRadioButton6;
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
            jPanel2 = new JPanel();
            jPanel2.setLayout(null);
            jPanel2.setBounds(28, 22, 638, 322);
            jPanel2
                    .setBorder(javax.swing.BorderFactory
                            .createTitledBorder(
                                    javax.swing.BorderFactory
                                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED),
                                    "委外统计报表",
                                    javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                    javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                    new java.awt.Font("Dialog",
                                            java.awt.Font.PLAIN, 12),
                                    java.awt.Color.black));
            jPanel2.add(getJRadioButton6(), null);
        }
        return jPanel2;
    }

    /**
     * 初始化组件
     * 
     */
    private void initUIComponents() {
        PnBottom.setBounds(3, this.jPanel1.getHeight() - 50, this.jPanel1
                .getWidth() - 6, 3);      
        btnExit.setBounds(this.jPanel1.getWidth() - 92, this.jPanel1
                .getHeight() - 40, 68, 26);
        //
        jPanel2.setBounds(28, 28,
        		this.getWidth() - 28 - 28, this.jPanel1.getHeight() - 98);
    }

    protected void paintComponent(Graphics g) {
        initUIComponents();
        super.paintComponent(g);
    }

    /**
     * This method initializes jPanel4
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnBottom() {
        if (PnBottom == null) {
            PnBottom = new JPanel();
            PnBottom.setBounds(24, 384, 667, 3);
            PnBottom
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        }
        return PnBottom;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnExit() {
        if (btnExit == null) {
            btnExit = new JButton();
            btnExit.setBounds(560, 394, 68, 26);
            btnExit.setText("关闭");
            btnExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return btnExit;
    }
    
    
    
    private class RadioActionListner implements ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            JComponent component = (JComponent) e.getSource();
            component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
                if (jRadioButton6.isSelected()) {// 委托发外加工账
                    
                    String flag = jRadioButton6.getActionCommand() ;
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgConsignQuery();
                        JDialogBaseHelper.putJDialogBaseToFlag(flag,dialog);
                        dialog.setVisible(true);
                    } else {
                        dialog.setVisibleNoChange(true);
                    }
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                component.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }
    
} 
