/*
 * Created on 2005-7-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.parameter;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PnOther extends PnCasParameterCommon {

	private JPanel jPanel = null;
	
	private JPanel jPanel1 = null;
	
	private JButton btnEdit = null;
	
	private JButton btnAvailability = null;
	
	private JButton btnExit = null;
	
	private JLabel jLabel = null;
	
	private static PnOther pnOhter = null;
	
	private OtherOption otherOption = null; // @jve:decl-index=0:
	
	private int dataSate = DataState.BROWSE;

	private JLabel jLabel1 = null;
	
	private JLabel jLabel2 = null;
	
	private JCustomFormattedTextField tfInOutMaximumFractionDigits = null;
	
	private JCheckBox jbIsShow = null;
	
	private JLabel jLabel3 = null;
	
	private JTextField cbbPerson = null;
	
	private JLabel jLabel4 = null;
	
	private JLabel jLabel5 = null;
	
	private JLabel jLabel6 = null;
	
	private JLabel jLabel7 = null;
	
	private JLabel jLabel8 = null;
	
	private JCustomFormattedTextField tfAllReportMaximumFractionDigits = null;
	
	private JCustomFormattedTextField tfOtherReportMaximum = null;

	private JLabel jLabel9 = null;
	
	private JLabel jLabe20 = null;

	private JLabel jLabel10 = null;

	private JTextField tfBalanceReport = null;

	/**
	 * This method initializes
	 * 
	 */
	private PnOther() {
		super();
		otherOption = CasCommonVars.getOtherOption();
		initialize();
		showData();
	}

	public static PnOther getInstance() {
		if (pnOhter == null) {
			pnOhter = new PnOther();
		}
		return pnOhter;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel10 = new JLabel();
		jLabel10.setBounds(new Rectangle(33, 227, 207, 18));
		jLabel10.setText("4:       平衡表小数位设置");
		jLabe20 = new JLabel();
		jLabe20.setText("其它报表显示小数位");
		jLabe20.setBounds(new Rectangle(65, 180, 172, 21));
		jLabel9 = new JLabel();
		jLabel9.setText("3:");
		jLabel9.setBounds(new Rectangle(33, 180, 29, 18));
		jLabel8 = new JLabel();
		jLabel8.setBounds(new Rectangle(65, 131, 172, 21));
		jLabel8.setText("工厂资料统计报表小数位设置");
		jLabel7 = new JLabel();
		jLabel7.setBounds(new Rectangle(32, 133, 29, 18));
		jLabel7.setText("2：");
		this.setLayout(null);
		this.setSize(631, 446);
		jLabel6 = new JLabel();
		jLabel6.setBounds(new Rectangle(33,321, 29, 18));
		jLabel6.setText("6：");
		jLabel5 = new JLabel();
		jLabel5.setBounds(new Rectangle(33, 274, 29, 18));
		jLabel5.setText("5：");
		jLabel4 = new JLabel();
		jLabel4.setBounds(new Rectangle(33, 86, 29, 18));
		jLabel4.setText("1：");
		jLabel3 = new JLabel();
		jLabel3.setBounds(new Rectangle(64, 321, 138, 18));
		jLabel3.setText("年审报表中的填表人：");
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(66, 83, 172, 21));
		jLabel2.setText("进出仓帐报表小数位设置:");
		jLabel1 = new JLabel();
		jLabel1.setBounds(22, 406, 140, 26);
		jLabel1.setText("");
		// (*)需重启才能生效!!!
		jLabel = new JLabel();

		jLabel.setBounds(3, 31, 586, 21);
		jLabel.setText("其它选项");
		jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));

		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);
		this.add(getBtnEdit(), null);
		this.add(getBtnAvailability(), null);
		this.add(getBtnExit(), null);
		this.add(jLabel, null);

		this.add(jLabel1, null);
		this.add(jLabel2, null);
		this.add(getTfInOutMaximumFractionDigits(), null);
		this.add(getJbIsShow(), null);
		this.add(jLabel3, null);
		this.add(getCbbPerson(), null);
		this.add(jLabel4, null);
		this.add(jLabel5, null);
		this.add(jLabel6, null);
		this.add(jLabel7, null);
		this.add(jLabel8, null);
		this.add(getTfAllReportMaximumFractionDigits(), null);
		this.add(jLabel9, null);
		this.add(jLabe20, null);
		this.add(getTfOtherReportMaximum(), null);
		this.add(jLabel10, null);
		this.add(getTfBalanceReport(), null);
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
		jPanel1.setBounds(3, 55, this.getWidth() - 6, 3);

		btnExit.setBounds(this.getWidth() - 92, this.getHeight() - 40, 68, 26);
		btnAvailability.setBounds(
				this.getWidth() - 92 - btnExit.getWidth() - 5,
				this.getHeight() - 40, 68, 26);
		btnEdit.setBounds(this.getWidth() - 92 - btnExit.getWidth()
				- btnAvailability.getWidth() - 10, this.getHeight() - 40, 68,
				26);
		jLabel1.setBounds(jLabel1.getX(), btnEdit.getY(), jLabel1.getWidth(),
				jLabel1.getHeight());
	}

	protected void paintComponent(Graphics g) {
		initUIComponents();
		super.paintComponent(g);
	}

	/**
	 * 修改
	 */
	private void edit() {
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
		otherOption = casAction.saveOtherOption(new Request(CommonVars
				.getCurrUser()), this.otherOption);
		CasCommonVars.setOtherOption(otherOption);
		super.setContainerEnabled(this, false);
		this.dataSate = DataState.BROWSE;
		setState();
	}

	/**
	 * 填充数据
	 * 
	 */
	private void fillData() {
		Integer maximumFractionDigits = Integer
				.parseInt(this.tfInOutMaximumFractionDigits.getValue()
						.toString());
		Integer allMaximumFractionDigits = Integer
				.parseInt(this.tfAllReportMaximumFractionDigits.getValue()
						.toString());
		Integer otherReportMaximumFractionDigits = Integer.parseInt(this.tfOtherReportMaximum.getValue()
				.toString());
		this.otherOption.setOtherReportMaximumFractionDigits(otherReportMaximumFractionDigits);
		this.otherOption
				.setAllReportInOutMaximumFractionDigits(allMaximumFractionDigits);
		this.otherOption.setInOutMaximumFractionDigits(maximumFractionDigits);
		this.otherOption
				.setIsShowTransFactIncipient(this.jbIsShow.isSelected());
		this.otherOption.setFillPerson(this.cbbPerson.getText() == null ? ""
				: this.cbbPerson.getText());
		this.otherOption.setBalanceDegree(CommonUtils
				.notEmpty(this.tfBalanceReport.getText()) ? new Integer(
				this.tfBalanceReport.getText()) : 0);

	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		this.tfInOutMaximumFractionDigits.setValue(this.otherOption
				.getInOutMaximumFractionDigits() == null ? 6 : this.otherOption
				.getInOutMaximumFractionDigits());
		this.jbIsShow.setSelected(this.otherOption
				.getIsShowTransFactIncipient() == null ? false
				: this.otherOption.getIsShowTransFactIncipient());
		this.cbbPerson.setText(this.otherOption.getFillPerson() == null ? ""
				: this.otherOption.getFillPerson());
		this.tfAllReportMaximumFractionDigits.setValue(this.otherOption
				.getAllReportInOutMaximumFractionDigits() == null ? 6
				: this.otherOption.getAllReportInOutMaximumFractionDigits());
		this.tfOtherReportMaximum.setValue(this.otherOption
				.getOtherReportMaximumFractionDigits() == null ? 2 : this.otherOption
				.getOtherReportMaximumFractionDigits());
		this.tfBalanceReport
				.setText(this.otherOption.getBalanceDegree() == null ? ""
						: this.otherOption.getBalanceDegree().toString());
		setState();
		super.setContainerEnabled(this, false);
	}

	/**
	 * 设置状态
	 * 
	 */
	private void setState() {
		btnEdit.setEnabled(dataSate == DataState.BROWSE);
		btnAvailability.setEnabled(dataSate != DataState.BROWSE);
		// jbIsShow.setEnabled(dataSate == DataState.BROWSE);
	}

	/**
	 * This method initializes tfInOutMaximumFractionDigits
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfInOutMaximumFractionDigits() {
		if (tfInOutMaximumFractionDigits == null) {
			tfInOutMaximumFractionDigits = new JCustomFormattedTextField();
			tfInOutMaximumFractionDigits.setBounds(new Rectangle(242, 83, 140,
					22));
			tfInOutMaximumFractionDigits.setValue(new Integer(6));
		}
		return tfInOutMaximumFractionDigits;
	}

	/**
	 * This method initializes jbIsShow
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJbIsShow() {
		if (jbIsShow == null) {
			jbIsShow = new JCheckBox();
			jbIsShow.setBounds(new Rectangle(65, 261, 400, 44));
			jbIsShow.setText("在年审报表的备注栏位统计转厂期初单");
		}
		return jbIsShow;
	}

	/**
	 * This method initializes cbbPerson
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCbbPerson() {
		if (cbbPerson == null) {
			cbbPerson = new JTextField();
			cbbPerson.setBounds(new Rectangle(242, 320, 93, 22));
		}
		return cbbPerson;
	}

	/**
	 * This method initializes tfAllReportMaximumFractionDigits
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfAllReportMaximumFractionDigits() {
		if (tfAllReportMaximumFractionDigits == null) {
			tfAllReportMaximumFractionDigits = new JCustomFormattedTextField();
			tfAllReportMaximumFractionDigits.setBounds(new Rectangle(242, 132, 140, 22));
			tfAllReportMaximumFractionDigits.setValue(new Integer(6));
		}
		return tfAllReportMaximumFractionDigits;
	}
	/**
	 * This method initializes tfAllReportMaximumFractionDigits
	 * 
	 * @return javax.swing.JTextField
	 */
	private JCustomFormattedTextField getTfOtherReportMaximum() {
		if (tfOtherReportMaximum == null) {
			tfOtherReportMaximum = new JCustomFormattedTextField();
			tfOtherReportMaximum.setBounds(new Rectangle(242, 180, 140, 22));
			tfOtherReportMaximum.setValue(new Integer(2));
		}
		return tfOtherReportMaximum;
	}

	/**
	 * This method initializes tfBalanceReport	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTfBalanceReport() {
		if (tfBalanceReport == null) {
			tfBalanceReport = new JTextField();
			tfBalanceReport.setBounds(new Rectangle(242, 225, 140, 22));
		}
		return tfBalanceReport;
	}

} // @jve:decl-index=0:visual-constraint="8,10"
