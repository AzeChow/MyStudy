package com.bestway.client.selfcheck;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JPanel;
import java.awt.Dimension;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.common.MaterielType;
import com.bestway.ui.winuicontrol.drawflow.FlowButton;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import com.bestway.ui.winuicontrol.drawflow.FlowLine;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;

public class PnSelfCheck extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image image = null;
	private FlowButton flowButton = null;
	private FlowButton flowButton1 = null;
	private FlowButton flowButton2 = null;
	private FlowButton flowButton3 = null;
	private FlowButton flowButton4 = null;
	private FlowButton flowButton5 = null;
	private FlowButton flowButton8 = null;
	private FlowButton flowButton51 = null;
	private FlowButton flowButton41 = null;
	private FlowButton flowButton42 = null;
	private FlowButton flowButton6 = null;
	private FlowButton flowButton61 = null;
	private FlowButton flowButton62 = null;
	private FlowButton flowButton63 = null;
	private FlowButton flowButton64 = null;
	private FlowButton flowButton65 = null;
	private FlowButton flowButton66 = null;
	private FlowButton flowButton67 = null;
	private FlowButton flowButton511 = null;
	private FlowButton flowButton5111 = null;
	private FlowButton flowButton81 = null;
	private FlowButton flowButton7 = null;
	private FlowButton flowButton9 = null;
	private FlowButton flowButton91 = null;
	private FlowButton flowButton621 = null;
	private FlowButton flowButton622 = null;
	private FlowButton flowButton623 = null;
	private FlowButton flowButton43 = null;
	private FlowButton flowButton44 = null;
	private FlowButton flowButton45 = null;
	private FlowButton flowButton811 = null;
	private FlowButton flowButton812 = null;
	private FlowLine flowLine = null;
	private FlowLine flowLine1 = null;
	private FlowLine flowLine2 = null;
	private FlowLine flowLine3 = null;
	private FlowLine flowLine4 = null;
	private FlowLine flowLine5 = null;
	private FlowLine flowLine6 = null;
	private FlowLine flowLine61 = null;
	private FlowLine flowLine62 = null;
	private FlowLine flowLine41 = null;
	private FlowLine flowLine42 = null;
	private FlowLine flowLine51 = null;
	private FlowLine flowLine52 = null;
	private FlowLine flowLine621 = null;
	private FlowLine flowLine622 = null;
	private FlowLine flowLine623 = null;
	private FlowLine flowLine624 = null;
	private FlowLine flowLine625 = null;
	private FlowLine flowLine7 = null;
	private FlowLine flowLine8 = null;
	private FlowLine flowLine9 = null;
	private FlowLine flowLine91 = null;
	private FlowLine flowLine92 = null;
	private FlowLine flowLine93 = null;
	private FlowLine flowLine94 = null;
	private FlowLine flowLine95 = null;
	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public PnSelfCheck() {
		super();
		initialize();
		this.setBorder(null);
		image = CommonVars.getImageSource().getImage("background.gif");
	}

	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
			int x = (this.getWidth() - jPanel.getWidth()) / 2;
			int y = (this.getHeight() - jPanel.getHeight()) / 2;
			if (x < 0) {
				x = 0;
			}
			if (y < 0) {
				y = 0;
			}
			jPanel.setBounds(x, y, jPanel.getWidth(), jPanel.getHeight());
		} catch (Exception e) {

		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		flowLine95 = new FlowLine();
		flowLine95.setArrowDirection(2);
		flowLine95.setDrawPairArrow(true);
		flowLine95.setBounds(new Rectangle(728, 147, 22, 30));
		flowLine95.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine94 = new FlowLine();
		flowLine94.setArrowDirection(2);
		flowLine94.setDrawPairArrow(true);
		flowLine94.setBounds(new Rectangle(725, 211, 33, 32));
		flowLine94.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine93 = new FlowLine();
		flowLine93.setArrowDirection(2);
		flowLine93.setDrawPairArrow(true);
		flowLine93.setBounds(new Rectangle(587, 400, 27, 20));
		flowLine93.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine92 = new FlowLine();
		flowLine92.setArrowDirection(2);
		flowLine92.setDrawPairArrow(true);
		flowLine92.setBounds(new Rectangle(587, 346, 22, 24));
		flowLine92.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine91 = new FlowLine();
		flowLine91.setArrowDirection(2);
		flowLine91.setDrawPairArrow(true);
		flowLine91.setBounds(new Rectangle(584, 302, 26, 21));
		flowLine91.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine9 = new FlowLine();
		flowLine9.setArrowDirection(2);
		flowLine9.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
		flowLine9.setBounds(new Rectangle(583, 229, 29, 22));
		flowLine9.setDrawPairArrow(true);
		flowLine8 = new FlowLine();
		flowLine8.setDrawPairArrow(true);
		flowLine8.setBounds(new Rectangle(511, 198, 25, 18));
		flowLine7 = new FlowLine();
		flowLine7.setDrawPairArrow(true);
		flowLine7.setBounds(new Rectangle(511, 128, 25, 14));
		flowLine625 = new FlowLine();
		flowLine625.setBounds(new Rectangle(224, 434, 44, 17));
		flowLine624 = new FlowLine();
		flowLine624.setBounds(new Rectangle(247, 368, 20, 29));
		flowLine623 = new FlowLine();
		flowLine623.setBounds(new Rectangle(248, 328, 20, 17));
		flowLine622 = new FlowLine();
		flowLine622.setBounds(new Rectangle(247, 288, 23, 20));
		flowLine621 = new FlowLine();
		flowLine621.setBounds(new Rectangle(246, 247, 24, 14));
		flowLine52 = new FlowLine();
		flowLine52.setOnlyDrawLine(true);
		flowLine52.setBounds(new Rectangle(242, 299, 11, 85));
		flowLine52.setLineDirection(1);
		flowLine51 = new FlowLine();
		flowLine51.setOnlyDrawLine(true);
		flowLine51.setBounds(new Rectangle(242, 212, 11, 43));
		flowLine51.setLineDirection(1);
		flowLine42 = new FlowLine();
		flowLine42.setOnlyDrawLine(true);
		flowLine42.setBounds(new Rectangle(224, 322, 24, 29));
		flowLine41 = new FlowLine();
		flowLine41.setOnlyDrawLine(true);
		flowLine41.setBounds(new Rectangle(224, 220, 24, 22));
		flowLine62 = new FlowLine();
		flowLine62.setBounds(new Rectangle(247, 197, 22, 28));
		flowLine61 = new FlowLine();
		flowLine61.setBounds(new Rectangle(248, 169, 20, 17));
		flowLine6 = new FlowLine();
		flowLine6.setBounds(new Rectangle(247, 108, 20, 24));
		flowLine5 = new FlowLine();
		flowLine5.setLineDirection(1);
		flowLine5.setBounds(new Rectangle(242, 121, 11, 57));
		flowLine5.setOnlyDrawLine(true);
		flowLine4 = new FlowLine();
		flowLine4.setOnlyDrawLine(true);
		flowLine4.setBounds(new Rectangle(224, 136, 24, 26));
		flowLine3 = new FlowLine();
		flowLine3.setDrawPairArrow(true);
		flowLine3.setBounds(new Rectangle(104, 424, 30, 24));
		flowLine2 = new FlowLine();
		flowLine2.setDrawPairArrow(true);
		flowLine2.setBounds(new Rectangle(104, 323, 30, 15));
		flowLine1 = new FlowLine();
		flowLine1.setDrawPairArrow(true);
		flowLine1.setBounds(new Rectangle(104, 233, 30, 19));
		flowLine = new FlowLine();
		flowLine.setDrawPairArrow(true);
		flowLine.setBounds(new Rectangle(104, 134, 30, 22));
		this.setSize(904, 534);
		this.setLayout(null);
		this.add(getJPanel(), null);
	}

	/**
	 * This method initializes flowButton
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton() {
		if (flowButton == null) {
			flowButton = new FlowButton();
			flowButton.setButtonType(2);
			flowButton
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialThatDayBalance?materielType="
							+ MaterielType.MATERIEL + ";title=原材料当日结存表");
			flowButton.setBounds(new Rectangle(13, 99, 92, 87));
			flowButton.setText("<html>原材料当<br>日结存表</html>");
		}
		return flowButton;
	}

	/**
	 * This method initializes flowButton1
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton1() {
		if (flowButton1 == null) {
			flowButton1 = new FlowButton();
			flowButton1.setButtonType(2);
			flowButton1
					.setActionCommand("com.bestway.client.selfcheck.DgCurrentThatDayBalanceToBom");
			flowButton1.setBounds(new Rectangle(13, 197, 92, 87));
			flowButton1.setText("<html>在产品结<br>存折料表<html>");
		}
		return flowButton1;
	}

	/**
	 * This method initializes flowButton2
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton2() {
		if (flowButton2 == null) {
			flowButton2 = new FlowButton();
			flowButton2.setButtonType(2);
			flowButton2
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialThatDayBalanceToBom");
			flowButton2.setBounds(new Rectangle(19, 292, 92, 87));
			flowButton2.setText("<html>产成品结<br>存折料表<html>");
		}
		return flowButton2;
	}

	/**
	 * This method initializes flowButton3
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton3() {
		if (flowButton3 == null) {
			flowButton3 = new FlowButton();
			flowButton3.setButtonType(2);
			// flowButton2.setActionCommand("com.bestway.client.selfcheck.");
			flowButton3.setBounds(new Rectangle(13, 391, 92, 87));
			flowButton3.setText("<html>外发加工结<br>存折料表<html>");
		}
		return flowButton3;
	}

	/**
	 * This method initializes flowButton4
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton4() {
		if (flowButton4 == null) {
			flowButton4 = new FlowButton();
			flowButton4.setBottomFilledColor(new Color(168, 244, 255));
			flowButton4.setButtonType(1);
			flowButton4.setForeground(new Color(6, 73, 201));
			flowButton4.setText("<html>原材料当<br>日结存表<html>");
			flowButton4.setBounds(new Rectangle(133, 120, 91, 52));
			flowButton4
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialThatDayBalance?materielType="
							+ MaterielType.MATERIEL + ";title=原材料当日结存表");
		}
		return flowButton4;
	}

	/**
	 * This method initializes flowButton5
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton5() {
		if (flowButton5 == null) {
			flowButton5 = new FlowButton();
			flowButton5.setBottomFilledColor(new Color(168, 244, 255));
			flowButton5.setButtonType(1);
			flowButton5
					.setActionCommand("com.bestway.client.selfcheck.DgCurrentThatDayBalance");
			flowButton5.setForeground(new Color(6, 73, 201));
			flowButton5.setBounds(new Rectangle(133, 209, 91, 52));
			flowButton5.setText("<html>在产品当<br>日结存表<html>");
		}
		return flowButton5;
	}

	/**
	 * This method initializes flowButton8
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton8() {
		if (flowButton8 == null) {
			flowButton8 = new FlowButton();
			flowButton8.setText("实际库存");
			flowButton8.setBottomFilledColor(new Color(51, 102, 204));
			flowButton8.setTopFilledColor(new Color(32, 209, 253));
			flowButton8.setBorderColor(new Color(204, 255, 255));
			flowButton8.setBounds(new Rectangle(134, 41, 109, 43));
			flowButton8.setForeground(Color.white);
		}
		return flowButton8;
	}

	/**
	 * This method initializes flowButton41
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton41() {
		if (flowButton41 == null) {
			flowButton41 = new FlowButton();
			flowButton41.setBottomFilledColor(new Color(168, 244, 255));
			flowButton41.setButtonType(1);
			flowButton41.setForeground(new Color(6, 73, 201));
			flowButton41.setText("<html>产成品当<br>日结存表<html>");
			flowButton41.setBounds(new Rectangle(133, 312, 91, 52));
			flowButton41
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialThatDayBalance?materielType="
							+ MaterielType.FINISHED_PRODUCT + ";title=产成品当日结存表");
		}
		return flowButton41;
	}

	/**
	 * This method initializes flowButton42
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton42() {
		if (flowButton42 == null) {
			flowButton42 = new FlowButton();
			flowButton42.setBottomFilledColor(new Color(168, 244, 255));
			flowButton42.setButtonType(1);
			flowButton42
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialThatDayBalance?materielType="
							+ "outSource" + ";title=外发加工当日结存表");
			flowButton42.setForeground(new Color(6, 73, 201));
			flowButton42.setBounds(new Rectangle(133, 413, 91, 52));
			flowButton42.setText("<html>外发加工当<br>日结存表<html>");
		}
		return flowButton42;
	}

	/**
	 * This method initializes flowButton6
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton6() {
		if (flowButton6 == null) {
			flowButton6 = new FlowButton();
			flowButton6.setText("原材料进出仓帐");
			flowButton6.setBounds(new Rectangle(268, 109, 129, 30));
			flowButton6
					.setActionCommand("com.bestway.client.selfcheck.DgCheckSelfImpExpQuery?materialType="
							+ MaterielType.MATERIEL + ";title=原材料物料帐");
		}
		return flowButton6;
	}

	/**
	 * This method initializes flowButton61
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton61() {
		if (flowButton61 == null) {
			flowButton61 = new FlowButton();
			flowButton61
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialDifferent?materielType="
							+ MaterielType.MATERIEL + ";title=结存与盘点差额表");
			flowButton61.setBounds(new Rectangle(268, 153, 129, 35));
			flowButton61.setText("<html>原材料仓帐<br>与盘点差额表<html>");
		}
		return flowButton61;
	}

	/**
	 * This method initializes flowButton62
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton62() {
		if (flowButton62 == null) {
			flowButton62 = new FlowButton();
			flowButton62
					.setActionCommand("com.bestway.client.selfcheck.DgCheckSelfImpExpQuery?materialType="
							+ MaterielType.SEMI_FINISHED_PRODUCT
							+ ";title=在产品物料帐");
			flowButton62.setBounds(new Rectangle(268, 192, 129, 35));
			flowButton62.setText("在产品明细帐");
		}
		return flowButton62;
	}

	/**
	 * This method initializes flowButton63
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton63() {
		if (flowButton63 == null) {
			flowButton63 = new FlowButton();
			flowButton63
					.setActionCommand("com.bestway.client.selfcheck.DgWorkOrderBOM");
			flowButton63.setBounds(new Rectangle(268, 237, 129, 32));
			flowButton63.setText("制单耗用情况表");
		}
		return flowButton63;
	}

	/**
	 * This method initializes flowButton64
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton64() {
		if (flowButton64 == null) {
			flowButton64 = new FlowButton();
			flowButton64
					.setActionCommand("com.bestway.client.selfcheck.DgCheckSelfImpExpQuery?materialType="
							+ MaterielType.FINISHED_PRODUCT + ";title=产成品物料帐");
			flowButton64.setBounds(new Rectangle(268, 284, 129, 30));
			flowButton64.setText("产成品明细帐");
		}
		return flowButton64;
	}

	/**
	 * This method initializes flowButton65
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton65() {
		if (flowButton65 == null) {
			flowButton65 = new FlowButton();
			flowButton65
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialDifferent?materielType="
							+ MaterielType.FINISHED_PRODUCT
							+ ";title=产成品结存与盘点差额表");
			flowButton65.setBounds(new Rectangle(268, 322, 129, 36));
			flowButton65.setText("<html>产成品仓帐<br>与盘点差额表<html>");
		}
		return flowButton65;
	}

	/**
	 * This method initializes flowButton66
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton66() {
		if (flowButton66 == null) {
			flowButton66 = new FlowButton();
			flowButton66
					.setActionCommand("com.bestway.client.selfcheck.DgProductConvert?materielType="
							+ MaterielType.FINISHED_PRODUCT + ";title=产成品折料表");
			flowButton66.setBounds(new Rectangle(268, 364, 129, 34));
			flowButton66.setText("产成品折料情况");
		}
		return flowButton66;
	}

	/**
	 * This method initializes flowButton67
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton67() {
		if (flowButton67 == null) {
			flowButton67 = new FlowButton();
			flowButton67
					.setActionCommand("com.bestway.client.selfcheck.DgMaterialDifferent?materielType="
							+ "outSource" + ";title=外发加工结存与盘点差额表");
			flowButton67.setBounds(new Rectangle(268, 422, 129, 39));
			flowButton67.setText("<html>外发加工仓帐<br>与盘点差额表<html>");
		}
		return flowButton67;
	}

	/**
	 * This method initializes flowButton511
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton511() {
		if (flowButton511 == null) {
			flowButton511 = new FlowButton();
			flowButton511.setBottomFilledColor(new Color(168, 244, 255));
			flowButton511.setButtonType(1);
			flowButton511
					.setActionCommand("com.bestway.client.selfcheck.DgSupplierBalanceDetailInfo?isM="
							+ true + ";title=供应商结转差额明细表");
			flowButton511.setBounds(new Rectangle(422, 108, 91, 56));
			flowButton511.setText("<html>供应商结转<br>差异明细表<html>");
		}
		return flowButton511;
	}

	/**
	 * This method initializes flowButton5111
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton5111() {
		if (flowButton5111 == null) {
			flowButton5111 = new FlowButton();
			flowButton5111.setBottomFilledColor(new Color(168, 244, 255));
			flowButton5111.setButtonType(1);
			flowButton5111
					.setActionCommand("com.bestway.client.selfcheck.DgClientConvertDetailInfo");
			flowButton5111.setBounds(new Rectangle(423, 187, 90, 56));
			flowButton5111.setText("<html>成品结转<br>折料情况表<html>");
		}
		return flowButton5111;
	}

	/**
	 * This method initializes flowButton81
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton81() {
		if (flowButton81 == null) {
			flowButton81 = new FlowButton();
			flowButton81.setText("转厂情况");
			flowButton81.setBottomFilledColor(new Color(51, 102, 204));
			flowButton81.setBorderColor(new Color(204, 255, 255));
			flowButton81.setTopFilledColor(new Color(32, 209, 253));
			flowButton81.setBounds(new Rectangle(459, 52, 134, 41));
			flowButton81.setForeground(Color.white);
		}
		return flowButton81;
	}

	/**
	 * This method initializes flowButton7
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton7() {
		if (flowButton7 == null) {
			flowButton7 = new FlowButton();
			flowButton7.setBottomFilledColor(new Color(168, 244, 255));
			flowButton7
					.setActionCommand("com.bestway.client.selfcheck.DgSupplierBalanceAllInfo?isM="
							+ true + ";title=供应商结转差额总表");
			flowButton7.setForeground(new Color(6, 73, 201));
			flowButton7.setBounds(new Rectangle(537, 114, 120, 37));
			flowButton7.setText("<html>供应商结转<br>差额总表<html>");
		}
		return flowButton7;
	}

	/**
	 * This method initializes flowButton9
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton9() {
		if (flowButton9 == null) {
			flowButton9 = new FlowButton();
			flowButton9.setBottomFilledColor(new Color(168, 244, 255));
			flowButton9
					.setActionCommand("com.bestway.client.selfcheck.DgSupplierBalanceAllInfo?isM="
							+ false + ";title=客户结转差额总表");
			flowButton9.setForeground(new Color(6, 73, 201));
			flowButton9.setBounds(new Rectangle(537, 188, 120, 42));
			flowButton9.setText("<html>客户结转<br>差额总表<html>");
		}
		return flowButton9;
	}

	/**
	 * This method initializes flowButton91
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton91() {
		if (flowButton91 == null) {
			flowButton91 = new FlowButton();
			flowButton91.setBottomFilledColor(new Color(168, 244, 255));
			flowButton91
					.setActionCommand("com.bestway.client.selfcheck.DgSupplierBalanceDetailInfo?isM="
							+ false + ";title=客户结转差额明细表");
			flowButton91.setForeground(new Color(6, 73, 201));
			flowButton91.setBounds(new Rectangle(537, 250, 120, 52));
			flowButton91.setText("<html>客户结转<br>差额明细表<html>");
		}
		return flowButton91;
	}

	/**
	 * This method initializes flowButton621
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton621() {
		if (flowButton621 == null) {
			flowButton621 = new FlowButton();
			flowButton621
					.setActionCommand("com.bestway.client.selfcheck.DgTransferDetailInfo?isImport="
							+ false + ";title=结转明细表");
			flowButton621.setBounds(new Rectangle(537, 321, 120, 26));
			flowButton621.setText("结转明细表");
		}
		return flowButton621;
	}

	/**
	 * This method initializes flowButton622
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton622() {
		if (flowButton622 == null) {
			flowButton622 = new FlowButton();
			flowButton622
					.setActionCommand("com.bestway.client.selfcheck.DgRecvSendDetailInfo?materielType="
							+ MaterielType.FINISHED_PRODUCT + ";title=收货明细表");
			flowButton622.setBounds(new Rectangle(537, 368, 120, 32));
			flowButton622.setText("收货明细表");
		}
		return flowButton622;
	}

	/**
	 * This method initializes flowButton623
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton623() {
		if (flowButton623 == null) {
			flowButton623 = new FlowButton();
			flowButton623.setBottomFilledColor(new Color(168, 244, 255));
			flowButton623
					.setActionCommand("com.bestway.client.selfcheck.DgRecvSendDetailInfo?materielType="
							+ MaterielType.FINISHED_PRODUCT + ";title=关封明细表");
			flowButton623.setForeground(new Color(6, 73, 201));
			flowButton623.setBounds(new Rectangle(537, 418, 120, 51));
			flowButton623.setText("关封明细表");
		}
		return flowButton623;
	}

	/**
	 * This method initializes flowButton43
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton43() {
		if (flowButton43 == null) {
			flowButton43 = new FlowButton();
			flowButton43.setBottomFilledColor(new Color(168, 244, 255));
			flowButton43.setButtonType(1);

			flowButton43
					.setActionCommand("com.bestway.client.selfcheck.FmContractExecStat");
			flowButton43.setBounds(new Rectangle(682, 113, 117, 36));

			flowButton43.setText("合同执行情况表");
		}
		return flowButton43;
	}

	/**
	 * This method initializes flowButton44
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton44() {
		if (flowButton44 == null) {
			flowButton44 = new FlowButton();
			flowButton44.setBottomFilledColor(new Color(168, 244, 255));
			flowButton44.setButtonType(1);

			flowButton44.setActionCommand("");
			flowButton44.setBounds(new Rectangle(682, 176, 116, 36));

			flowButton44.setText("进口报关清单");
		}
		return flowButton44;
	}

	/**
	 * This method initializes flowButton45
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton45() {
		if (flowButton45 == null) {
			flowButton45 = new FlowButton();
			flowButton45.setBottomFilledColor(new Color(168, 244, 255));
			flowButton45.setButtonType(1);

			flowButton45.setActionCommand("");
			flowButton45.setBounds(new Rectangle(682, 243, 116, 36));

			flowButton45.setText("出口报关清单");
		}
		return flowButton45;
	}

	/**
	 * This method initializes flowButton811
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton811() {
		if (flowButton811 == null) {
			flowButton811 = new FlowButton();
			flowButton811.setText("<html>现执行<br>合同情况<html>");
			flowButton811.setBottomFilledColor(new Color(51, 102, 204));
			flowButton811.setBorderColor(new Color(204, 255, 255));
			flowButton811.setTopFilledColor(new Color(32, 209, 253));
			flowButton811.setBounds(new Rectangle(689, 46, 96, 48));
			flowButton811.setForeground(Color.white);
		}
		return flowButton811;
	}

	/**
	 * This method initializes flowButton812
	 * 
	 * @return com.bestway.ui.winuicontrol.drawflow.FlowButton
	 */
	private FlowButton getFlowButton812() {
		if (flowButton812 == null) {
			flowButton812 = new FlowButton();
			flowButton812
					.setActionCommand("com.bestway.bcus.client.cas.otherreport.FmBalanceInfo");
			flowButton812.setText("<html>保税物流<br>短溢表<html>");
			flowButton812.setBottomFilledColor(new Color(51, 102, 204));
			flowButton812.setBorderColor(new Color(204, 255, 255));
			flowButton812.setTopFilledColor(new Color(32, 209, 253));
			flowButton812.setBounds(new Rectangle(458, 8, 133, 40));
			flowButton812.setForeground(Color.white);
		}
		return flowButton812;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setOpaque(false);
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(12, 5, 816, 492));
			jPanel.add(getFlowButton(), null);
			jPanel.add(getFlowButton1(), null);
			jPanel.add(getFlowButton2(), null);
			jPanel.add(getFlowButton3(), null);
			jPanel.add(getFlowButton4(), null);
			jPanel.add(getFlowButton5(), null);
			jPanel.add(getFlowButton8(), null);
			jPanel.add(getFlowButton41(), null);
			jPanel.add(getFlowButton42(), null);
			jPanel.add(getFlowButton6(), null);
			jPanel.add(getFlowButton61(), null);
			jPanel.add(getFlowButton62(), null);
			jPanel.add(getFlowButton63(), null);
			jPanel.add(getFlowButton64(), null);
			jPanel.add(getFlowButton65(), null);
			jPanel.add(getFlowButton66(), null);
			jPanel.add(getFlowButton67(), null);
			jPanel.add(getFlowButton511(), null);
			jPanel.add(getFlowButton5111(), null);
			jPanel.add(getFlowButton81(), null);
			jPanel.add(getFlowButton7(), null);
			jPanel.add(getFlowButton9(), null);
			jPanel.add(getFlowButton91(), null);
			jPanel.add(getFlowButton621(), null);
			jPanel.add(getFlowButton622(), null);
			jPanel.add(getFlowButton623(), null);
			jPanel.add(getFlowButton43(), null);
			jPanel.add(getFlowButton44(), null);
			jPanel.add(getFlowButton45(), null);
			jPanel.add(getFlowButton811(), null);
			jPanel.add(getFlowButton812(), null);
			jPanel.add(flowLine, null);
			jPanel.add(flowLine1, null);
			jPanel.add(flowLine2, null);
			jPanel.add(flowLine3, null);
			jPanel.add(flowLine4, null);
			jPanel.add(flowLine5, null);
			jPanel.add(flowLine6, null);
			jPanel.add(flowLine61, null);
			jPanel.add(flowLine62, null);
			jPanel.add(flowLine41, null);
			jPanel.add(flowLine42, null);
			jPanel.add(flowLine51, null);
			jPanel.add(flowLine52, null);
			jPanel.add(flowLine621, null);
			jPanel.add(flowLine622, null);
			jPanel.add(flowLine623, null);
			jPanel.add(flowLine624, null);
			jPanel.add(flowLine625, null);
			jPanel.add(flowLine7, null);
			jPanel.add(flowLine8, null);
			jPanel.add(flowLine9, null);
			jPanel.add(flowLine91, null);
			jPanel.add(flowLine92, null);
			jPanel.add(flowLine93, null);
			jPanel.add(flowLine94, null);
			jPanel.add(flowLine95, null);
		}
		return jPanel;
	}

} // @jve:decl-index=0:visual-constraint="-6,1"
