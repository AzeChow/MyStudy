/*
 * Created on 2005-5-13
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.outsource.specificcontrol;

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
public class FmSpecificControl extends JInternalFrameBase {

    private javax.swing.JPanel jContentPane                   = null;

    private ButtonGroup        buttonGroup                    = null;

    private JPanel             jPanel                         = null;

    private JPanel             jPanel1                        = null;

    private JPanel             jPanel4                        = null;

    private JPanel             jPanel5                        = null;

    private JButton            btnExit                        = null;

    private JRadioButton rbHalfProductManager = null;

	/**
     * This is the default constructor
     */
    public FmSpecificControl() {
        super();
        //
		// check authority
		//
		OutsourceAuthority outsourceAuthority = (OutsourceAuthority) CommonVars
				.getApplicationContext().getBean("outsourceAuthority");
		outsourceAuthority.checkSpecialByBrower(new Request(
				CommonVars.getCurrUser()));
		
        initialize(); 
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setTitle("委外特殊控制");
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
            jLabel.setText("委外特殊控制");
            jLabel.setForeground(new java.awt.Color(255, 153, 0));
            jLabel.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 18));
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
            jPanel1.add(getJPanel4(), null);
            jPanel1.add(getJPanel5(), null);
            jPanel1.add(getBtnExit(), null);
        }
        return jPanel1;
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
            jPanel4.setBounds(28, 23, 652, 356);
            jPanel4
                    .setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED), "委外控制", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12), new java.awt.Color(51,51,51)));
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
     * 初始化组件
     * 
     */
    private void initUIComponents() {    
        jPanel4.setBounds(28, 28,
        		this.getWidth() - 28 - 28, this.jPanel1.getHeight() - 98);
        
        btnExit.setBounds(this.jPanel1.getWidth() - 92,
    			 this.jPanel1.getHeight()-this.btnExit.getHeight()-10, 
    			 68, 26);
        jPanel5.setBounds(3, this.btnExit.getY()-13,
        		this.jPanel1.getWidth() - 6, 3);
        
    }

    /**
     * 重画组件
     */
    protected void paintComponent(Graphics g) {
        initUIComponents();
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
                final String flag = uuid.toString() ;
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
     * // change the template for this generated type comment go to Window -
     * Preferences - Java - Code Style - Code Templates
     */
    private class RadioActionListner implements ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            JComponent component = (JComponent) e.getSource();
            component.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            try {
            	if (rbHalfProductManager.isSelected()) {// 半成品转料件 
                    
                    String flag = rbHalfProductManager.getActionCommand() ;
                    JDialogBase dialog = JDialogBaseHelper
                            .getJDialogBaseByFlag(flag);
                    if (dialog == null) {
                        dialog = new DgOutSourceHalfProductManager();
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

    

    /**
     * This method initializes rbHalfProductManager	
     * 	
     * @return javax.swing.JRadioButton	
     */
    private JRadioButton getRbHalfProductManager() {
        if (rbHalfProductManager == null) {
            rbHalfProductManager = new JRadioButton();
            rbHalfProductManager.setBounds(new java.awt.Rectangle(63,55,137,23));
            rbHalfProductManager.setText("半成品委外管理");
        }
        return rbHalfProductManager;
    }

    
}  