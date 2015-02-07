/*
 * Created on 2005-3-2
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import javax.swing.JLabel;

import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import com.bestway.bcus.client.common.CommonVars;
import java.awt.Rectangle;
import java.awt.Font;
/**
 * @author xxm
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PnErpBillCenter extends JPanel {

    private JLabel  jLabel1  = null;
    private Image   image    = null;
    private JLabel  jLabel   = null;
    private FlowButton flowButton = null;
    private FlowButton flowButton1 = null;
    private FlowButton flowButton2 = null;
    private FlowButton flowButton3 = null;
    private FlowButton flowButton4 = null;
    private FlowButton flowButton5 = null;
    private FlowButton flowButton6 = null;
    /**
     * @param arg0
     * @param arg1
     */
    public PnErpBillCenter(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnErpBillCenter(LayoutManager arg0) {
        super(arg0);
        initialize();
    }

    /**
     * @param arg0
     */
    public PnErpBillCenter(boolean arg0) {
        super(arg0);
        initialize();
    }

    /**
     * 
     */
    public PnErpBillCenter() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        jLabel = new JLabel();
        jLabel1 = new JLabel();
        this.setLayout(null);
        this.setSize(755, 462);
        Dimension panelSize = this.getSize();
        jLabel1.setBounds(137, 12, 216, 32);
        jLabel1.setText("单据中心");
        jLabel1.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel.setBounds(363, 19, 376, 24);
        jLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        jLabel.setText(CommonVars.getMainDay());
        jLabel.setForeground(new java.awt.Color(0, 102, 102));
        image = CommonVars.getImageSource().getImage("background.gif");
        this.add(jLabel1, null);

        this.add(getFlowButton());
        this.add(getFlowButton1());
        this.add(getFlowButton2());
        this.add(getFlowButton5());
        this.add(getFlowButton3());
        this.add(getFlowButton6());
        this.add(getFlowButton4());
        this.add(jLabel, null);
    }

    @Override
	public void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {

        }
    }
    
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.setBounds(new java.awt.Rectangle(185,171,138,30));
			flowButton.setActionCommand("com.bestway.common.client.erpbillcenter.parameter.FmErpBillCenterParameterSet");
			flowButton.setText("参数设置");
		}
		return flowButton;
	}

	private FlowButton getFlowButton5() {
		if (flowButton5 == null) {
			flowButton5 = new FlowButton();
			flowButton5.setBounds(new java.awt.Rectangle(215,207,138,30));
			flowButton5.setActionCommand("com.bestway.common.client.erpbillcenter.FmFactualCustoms");
			flowButton5.setText("实际报关资料");
		}
		return flowButton5;
	}
	
	
	private FlowButton getFlowButton1() {
		if (flowButton1 == null) {
			flowButton1 = new FlowButton();
			flowButton1.setBounds(new java.awt.Rectangle(242,243,138,30));
			flowButton1.setActionCommand("com.bestway.common.client.erpbillcenter.FmFactoryAndFactualCustomsRalation");
			flowButton1.setText("对应关系");
		}
		return flowButton1;
	}
	
	private FlowButton getFlowButton2() {
		if (flowButton2 == null) {
			flowButton2 = new FlowButton();
			flowButton2.setBounds(new java.awt.Rectangle(275,279,138,30));
			flowButton2.setActionCommand("com.bestway.common.client.erpbillcenter.FmBillType");
			flowButton2.setText("工厂单据类型");
		}
		return flowButton2;
	}


	private FlowButton getFlowButton3() {
		if (flowButton3 == null) {
			flowButton3 = new FlowButton();
			flowButton3.setBounds(new java.awt.Rectangle(317,315,138,30));
			flowButton3.setActionCommand("com.bestway.common.client.erpbillcenter.FmBill");
			flowButton3.setText("工厂单据管理");
		}
		return flowButton3;
	}
	
	private FlowButton getFlowButton6() {
		if (flowButton6 == null) {
			flowButton6 = new FlowButton();
			flowButton6.setBounds(new java.awt.Rectangle(343,351,138,30));
			flowButton6.setActionCommand("com.bestway.common.client.erpbill.FmCustomOrder");
			flowButton6.setText("订单管理");
		}
		return flowButton6;
	}
	
	private FlowButton getFlowButton4() {
		if (flowButton4 == null) {
			flowButton4 = new FlowButton();
			flowButton4.setBounds(new Rectangle(372, 385, 138, 30));
			flowButton4.setActionCommand("com.bestway.common.client.erpbillcenter.specificontrol.FmSpecificControl");
			flowButton4.setText("特殊控制");
		}
		return flowButton4;
	}


      }  //  @jve:decl-index=0:visual-constraint="28,-88"
