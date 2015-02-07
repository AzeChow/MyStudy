/*
 * 设备参数设置
 * Created on 2004-8-24
 * 
 *	checked 2009-1-21 by lm
 * //@author fhz
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.ParameterSet;
import com.bestway.common.Request;
import com.bestway.common.authority.entity.AclUser;
import com.bestway.common.constant.ParameterType;
import com.bestway.common.warning.action.WarningAction;
import com.bestway.common.warning.entity.WarningItem;
import com.bestway.common.warning.entity.WarningItemFlag;
import com.bestway.common.warning.entity.WarningThread;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;
import java.awt.Font;

public class FmFixtureParameterSet extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel pnMain = null;

	private JButton btnSave = null;

	private JButton btnClose = null;

	private SystemAction systemAction = null;

	private JButton btnAmend = null;

	private int dataState = DataState.BROWSE;

	private String emsFrom = "1";

	private String contraceFrom = "1";

	private JPanel pnTop = null;

	private FixtureContractAction fixtureContractAction = null;

	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	private WarningAction warningAction = null;

	private JLabel lbAheadRemind = null;

	private JSeparator jSeparator = null;

	private JRadioButton rbIsStart = null;

	private JFormattedTextField tfDays = null;

	private JLabel lbAhead = null;

	private JLabel lbDay = null;

	/**
	 * This is the default constructor
	 */
	public FmFixtureParameterSet() {
		super();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		warningAction = (WarningAction) CommonVars.getApplicationContext()
				.getBean("warningAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("设备参数设置");
		this.setSize(895, 529);
		this.setContentPane(getJContentPane());
		fillWindow();
		dataState = DataState.BROWSE;
		setState();

	}

	private void fillWindow() {
		WarningItem warningItem = warningAction.findWarningItem(new Request(
				CommonVars.getCurrUser(),true),
				WarningItemFlag.FIXTURE_CUSTOM_OVERSEE);
		WarningThread warningThread = warningAction.findWarningThread(
				new Request(CommonVars.getCurrUser(),true),
				WarningThread.WARNING_ITEM);
		if (warningItem == null) {
			this.tfDays.setText(null);
		} else {
			this.tfDays.setText(warningItem.getAmount().toString());
		}

		if (warningThread == null)
			this.rbIsStart.setSelected(false);
		else {
			if (warningThread.getIsStart() == true)
				this.rbIsStart.setSelected(true);
			else
				this.rbIsStart.setSelected(false);
		}
	}

	/**
	 * This method initializes pnContent
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(javax.swing.BorderFactory
					.createCompoundBorder(null, null));
			jContentPane.add(getPnMain(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getPnTop(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes pn
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnMain() {
		if (pnMain == null) {
			lbDay = new JLabel();
			lbDay.setBounds(new Rectangle(223, 74, 32, 20));
			lbDay.setText("天");
			lbDay.setForeground(new Color(51, 0, 255));
			lbAhead = new JLabel();
			lbAhead.setBounds(new Rectangle(127, 75, 29, 19));
			lbAhead.setText("提前");
			lbAhead.setForeground(new Color(51, 0, 255));
			lbAheadRemind = new JLabel();
			pnMain = new JPanel();
			pnMain.setLayout(null);
			pnMain.setForeground(new java.awt.Color(51, 0, 255));
			lbAheadRemind.setBounds(37, 46, 225, 21);
			lbAheadRemind.setText("提前提示设备监管到期(单位天)：");
			lbAheadRemind.setForeground(new java.awt.Color(51, 0, 255));
			pnMain.add(getBtnSave(), null);
			pnMain.add(getBtnClose(), null);
			pnMain.add(getBtnAmend(), null);
			pnMain.add(lbAheadRemind, null);
			pnMain.add(getJSeparator(), null);
			pnMain.add(getRbIsStart(), null);
			pnMain.add(getTfDays(), null);
			pnMain.add(lbAhead, null);
			pnMain.add(lbDay, null);
		}
		return pnMain;
	}

	/**
	 * 
	 * This method initializes btnSave
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setBounds(445, 407, 80, 26);
			btnSave.setText("保存");
			btnSave.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkEditParameterSet(new Request(CommonVars.getCurrUser()));
					// 保存设备预警WarningItem warningItem = new WarningItem();
					fillData();
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		WarningItem warningItem = warningAction.findWarningItem(new Request(
				CommonVars.getCurrUser(),true),
				WarningItemFlag.FIXTURE_CUSTOM_OVERSEE);
		if (warningItem == null) {
			warningItem = new WarningItem();
			warningItem.setExcuteKind(0);
			warningItem.setExcutetime("12:00:00");
			if (tfDays.getText() != null && !tfDays.getText().equals("")) {
				if (Double.valueOf(tfDays.getText()) == 0.0)
					warningItem.setAmount(1.0);
				else
					warningItem.setAmount(Double.valueOf(tfDays.getText()));
			}
			warningItem.setItemName("设备监管到期前天数小于或等于 X 天的时候提醒");
			warningItem.setCompany(CommonVars.getCurrUser().getCompany());
			warningItem.setNote("设备监管到期预警");
			warningItem
					.setWarningItemFlag(WarningItemFlag.FIXTURE_CUSTOM_OVERSEE);
		} else {
			if (tfDays.getText() != null && !tfDays.getText().equals("")) {
				if (Double.valueOf(tfDays.getText()) == 0.0)
					warningItem.setAmount(1.0);
				else
					warningItem.setAmount(Double.valueOf(tfDays.getText()));
			}
		}

		warningItem = warningAction.saveWarningItem(new Request(CommonVars
				.getCurrUser(),true), warningItem);

		WarningThread warningThread = warningAction.findWarningThread(
				new Request(CommonVars.getCurrUser(),true),
				WarningThread.WARNING_ITEM);
		if (rbIsStart.isSelected()) {
			if (warningThread == null) {
				warningThread = new WarningThread();
			}
			warningThread.setCompany(CommonVars.getCurrUser().getCompany());
			warningThread.setType(WarningThread.WARNING_ITEM);
			warningThread.setIsStart(true);
			warningAction.saveWarningThread(new Request(CommonVars
					.getCurrUser(),true), warningThread);
			warningAction.startWarningThread(new Request(CommonVars
					.getCurrUser(),true));
		} else {
			if (warningThread != null) {
				warningThread.setIsStart(false);
				warningAction.saveWarningThread(new Request(CommonVars
						.getCurrUser(),true), warningThread);
				warningAction.shutDownWarningThread(new Request(CommonVars
						.getCurrUser(),true));
			}
		}
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfDays() {
		if (tfDays == null) {
			tfDays = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfDays.setBounds(155, 73, 69, 23);
		}
		return tfDays;
	}

	/**
	 * @param style
	 */
	private void saveStyle(int style) {
		AclUser obj = CommonVars.getCurrUser();
		obj.setStyle(Integer.valueOf(style));
		systemAction.saveAclUser(new Request(CommonVars.getCurrUser(),true), obj);
	}

	/**
	 * @param parameterType
	 * @param values
	 * @param flat
	 */
	private void saveValues(int parameterType, String values, String flat) {
		List list = systemAction.findnameValues(new Request(CommonVars
				.getCurrUser(),true), parameterType);
		ParameterSet para = (ParameterSet) list.get(0);
		para.setNameValues(values);
		if (flat.equals("0")) {

		} else if (flat.equals("1")) {
			CommonVars.setEmsFrom(values);
		} else if (flat.equals("2")) {
			CommonVars.setContractFrom(values);
		}
		systemAction.saveValues(new Request(CommonVars.getCurrUser(),true), para);

	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(532, 407, 79, 26);
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmFixtureParameterSet.this.dispose();

				}
			});

		}
		return btnClose;
	}

	/**
	 * This method initializes btnAmend
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAmend() {
		if (btnAmend == null) {
			btnAmend = new JButton();
			btnAmend.setBounds(356, 407, 81, 26);
			btnAmend.setText("修改");
			btnAmend.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkEditParameterSet(new Request(CommonVars.getCurrUser()));
					dataState = DataState.EDIT;
					setState();
				}
			});

		}
		return btnAmend;
	}

	/**
	 * 
	 */
	private void setState() {
		this.rbIsStart.setEnabled(!(dataState == DataState.BROWSE));
		this.btnSave.setEnabled(!(dataState == DataState.BROWSE));
		this.btnAmend.setEnabled(dataState == DataState.BROWSE);
		this.tfDays.setEnabled(!(dataState == DataState.BROWSE));
		// && rbIsStart.isSelected());

	}

	/**
	 * @return Returns the emsFrom.
	 */
	public String getEmsFrom() {
		return emsFrom;
	}

	/**
	 * @param emsFrom
	 *            The emsFrom to set.
	 */
	public void setEmsFrom(String emsFrom) {
		this.emsFrom = emsFrom;
	}

	/**
	 * 
	 * This method initializes pnTop
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			javax.swing.JLabel jLabel17 = new JLabel();

			javax.swing.JLabel jLabel18 = new JLabel();

			javax.swing.JLabel jLabel19 = new JLabel();

			pnTop.setLayout(new BorderLayout());
			jLabel17.setText("");
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel18.setText("来料设备系统参数设置");
			jLabel18
					.setFont(new Font("\u65b0\u5b8b\u4f53", Font.BOLD, 20));
			jLabel18.setForeground(new java.awt.Color(255, 153, 0));
			jLabel19.setText("");
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			pnTop
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pnTop.setBackground(java.awt.Color.white);
			pnTop.add(jLabel17, java.awt.BorderLayout.WEST);
			pnTop.add(jLabel18, java.awt.BorderLayout.CENTER);
			pnTop.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return pnTop;
	}

	/**
	 * @return Returns the contraceFrom.
	 */
	public String getContraceFrom() {
		return contraceFrom;
	}

	/**
	 * @param contraceFrom
	 *            The contraceFrom to set.
	 */
	public void setContraceFrom(String contraceFrom) {
		this.contraceFrom = contraceFrom;
	}

	/**
	 * This method initializes jSeparator
	 * 
	 * @return javax.swing.JSeparator
	 */
	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
			jSeparator.setBounds(new java.awt.Rectangle(7, 384, 876, 8));
		}
		return jSeparator;
	}

	protected void paintComponent(Graphics g) {
		jSeparator.setBounds(3, this.pnMain.getHeight() - 50, this.pnMain
				.getWidth() - 6, 3);
		btnAmend.setBounds(this.pnMain.getWidth() - 392,
				this.pnMain.getHeight() - 40, 68, 26);
		btnSave.setBounds(this.pnMain.getWidth() - 292,
				this.pnMain.getHeight() - 40, 68, 26);
		btnClose.setBounds(this.pnMain.getWidth() - 192, this.pnMain
				.getHeight() - 40, 68, 26);
		super.paintComponent(g);
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIsStart() {
		if (rbIsStart == null) {
			rbIsStart = new JRadioButton();
			rbIsStart.setBounds(new Rectangle(37, 73, 79, 21));
			rbIsStart.setText("启动提示");
		}
		return rbIsStart;
	}
}
