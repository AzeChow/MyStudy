/*
 * Created on 2005-5-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.fpt;

import java.awt.Rectangle;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class DgQueryConditionForFptBillHead extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel3 = null;
	private JComboBox cbbDeclareState = null;
	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JLabel jLabel4 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private List lsResult = null;
	private boolean isImport;
	private JLabel jLabel = null;
	private Integer type = null;
	private FptManageAction fptManageAction = null;
	private JButton btnExpCopBillNO = null;
	private JTextField cbbExpTradeCod = null;
	private Brief brief = null; // 海关编码
	private JLabel jLabel2 = null;
	private JComboBox cbbFptApp = null;

	/**
	 * @return Returns the isImport.
	 */
	public boolean isImport() {
		return isImport;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	/**
	 * @return Returns the lsResult.
	 */
	public List getLsResult() {
		return lsResult;
	}

	/**
	 * This is the default constructor
	 */
	public DgQueryConditionForFptBillHead() {
		super();
		initialize();
		this.fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
		}
		super.setVisible(b);
	}

	/**
	 * 初始化窗口上 控件的值
	 * 
	 */
	private void initUIComponents() {
		String inOutFlag;
		if (isImport) {
			inOutFlag = "0";
		} else {
			inOutFlag = "1";
		}
		List list = fptManageAction.findFptAppHeadByScmCoc(new Request(
				CommonVars.getCurrUser(), true), inOutFlag,
				DeclareState.PROCESS_EXE, null,null);
		cbbFptApp.removeAllItems();
		this.cbbFptApp.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbFptApp,
				"appNo", "appNo");
		cbbFptApp.setRenderer(CustomBaseRender.getInstance().getRender("appNo",
				"appNo", 200, 0));
		cbbFptApp.setSelectedIndex(-1);

		this.cbbDeclareState.removeAllItems();
		this.cbbDeclareState.addItem(new ItemProperty(Integer.valueOf(
				DeclareState.APPLY_POR).toString(), "初始状态"));
		this.cbbDeclareState.addItem(new ItemProperty(Integer.valueOf(
				DeclareState.WAIT_EAA).toString(), "等待审批"));
		this.cbbDeclareState.addItem(new ItemProperty(Integer.valueOf(
				DeclareState.PROCESS_EXE).toString(), "正在执行"));
		this.cbbDeclareState.addItem(new ItemProperty(Integer.valueOf(
				DeclareState.CHANGING_EXE).toString(), "正在变更"));
		this.cbbDeclareState.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclareState);
		this.cbbDeclareState.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("请输入检索条件");
		this.setSize(379, 230);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(16, 88, 102, 18));
			jLabel2.setText("申请单编号");
			jLabel = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel1 = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel1.setBounds(16, 51, 102, 24);
			jLabel1.setText("申报状态");
			jLabel3.setBounds(16, 115, 102, 24);
			jLabel3.setText("录入日期");
			jLabel4.setBounds(237, 122, 13, 18);
			jLabel4.setText("至");
			jLabel.setBounds(16, 17, 102, 20);
			jLabel.setText("转入/转出企业名称");
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(getCbbDeclareState(), null);
			jContentPane.add(getCbbBeginDate(), null);
			jContentPane.add(getCbbEndDate(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel4, null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getBtnExpCopBillNO(), null);
			jContentPane.add(getCbbExpTradeCod(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getCbbFptApp(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareState() {
		if (cbbDeclareState == null) {
			cbbDeclareState = new JComboBox();
			cbbDeclareState.setBounds(128, 54, 221, 25);
		}
		return cbbDeclareState;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(128, 120, 104, 20);
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(255, 122, 94, 19);
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(112, 160, 61, 25);
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String state = null;
					String tradeCode = null;
					if (cbbExpTradeCod.getText() != null
							&& !"".equals(cbbExpTradeCod.getText())) {
						tradeCode = cbbExpTradeCod.getText();
					}
					if (cbbDeclareState.getSelectedItem() != null) {
						state = ((ItemProperty) cbbDeclareState
								.getSelectedItem()).getCode();
					}
					String appNo = null;
					if (cbbFptApp.getSelectedItem() != null) {
						appNo = ((FptAppHead) cbbFptApp.getSelectedItem())
								.getAppNo();
					}
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					lsResult = fptManageAction.findInOutFptBillHeadByType(
							new Request(CommonVars.getCurrUser()), isImport?FptInOutFlag.IN:FptInOutFlag.OUT,
							type+"", beginDate, endDate, tradeCode, state, appNo,
							null);
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(200, 160, 58, 25);
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * This method initializes btnExpCopBillNO
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExpCopBillNO() {
		if (btnExpCopBillNO == null) {
			btnExpCopBillNO = new JButton();
			btnExpCopBillNO.setBounds(new Rectangle(321, 20, 26, 25));
			btnExpCopBillNO.setText("...");
			btnExpCopBillNO
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							Brief brief = (Brief) CommonQuery
									.getInstance()
									.getCustomBrief(
											DgQueryConditionForFptBillHead.this.brief);
							if (brief != null) {
								DgQueryConditionForFptBillHead.this.brief = brief;
								getCbbExpTradeCod().setText(brief.getName());
							}

						}
					});
		}
		return btnExpCopBillNO;
	}

	/**
	 * This method initializes cbbExpTradeCod
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCbbExpTradeCod() {
		if (cbbExpTradeCod == null) {
			cbbExpTradeCod = new JTextField();
			cbbExpTradeCod.setBounds(new Rectangle(128, 21, 193, 22));
			cbbExpTradeCod.setEditable(false);
		}
		return cbbExpTradeCod;
	}

	/**
	 * This method initializes cbbFptApp
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFptApp() {
		if (cbbFptApp == null) {
			cbbFptApp = new JComboBox();
			cbbFptApp.setBounds(new Rectangle(128, 87, 221, 26));
		}
		return cbbFptApp;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
