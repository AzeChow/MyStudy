package com.bestway.client.fixasset;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.AgreementGroupState;
import com.bestway.common.constant.AgreementState;
import com.bestway.fixasset.action.FixAssetAction;
import com.bestway.fixasset.entity.Agreement;
import com.bestway.fixasset.entity.AgreementCommInfo;
import com.bestway.fixasset.entity.AgreementGroupHead;
import com.bestway.fixasset.entity.ImpAgreementCommInfo;
import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Color;

public class DgAgreement extends JDialogBase {

	private JPanel jContentPane = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pnHead = null;

	private JPanel pnDetail = null;

	private JPanel pnGroup = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel1 = null;

	private JTextField tfSancEmsNo = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JCalendarComboBox cbbApproveDate = null;

	private JLabel jLabel5 = null;

	private JCustomFormattedTextField tfTotalMoney = null;

	private JLabel jLabel6 = null;

	private JTextField tfStateMark = null;

	private JLabel jLabel7 = null;

	private JCustomFormattedTextField tfChangeRemainMoney = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JTextField tfTradeCode = null;

	private JTextField tfTradeName = null;

	private JTextField tfBriefName = null;

	private JTextField tfAddress = null;

	private JTextField tfLinkMan = null;

	private JTextField tfLinkTel = null;

	private JTextField tfWorkingRange = null;

	private JCustomFormattedTextField tfRegisteredMoney = null;

	private JCustomFormattedTextField tfInvestMoney = null;

	private JComboBox cbbCurr = null;

	private JComboBox cbbLevyKind = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private JToolBar jToolBar = null;

	private JButton btnAddCommInfo = null;

	private JButton btnChangingCommInfoNo = null;

	private JButton btnCommInfoNoSort = null;

	private JButton btnSort = null;

	private JScrollPane jScrollPane = null;

	private JTable tbCommInfo = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel20 = null;

	private JToolBar jToolBar1 = null;

	private JToolBar jToolBar2 = null;

	private JButton btnAddDel = null;

	private JButton btnAddAdd = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbDelete = null;

	private JScrollPane jScrollPane2 = null;

	private JTable tbImp = null;

	private JSplitPane jSplitPane2 = null;

	private JScrollPane jScrollPane3 = null;

	private JTable tbGroup = null;

	private JSplitPane jSplitPane3 = null;

	private JScrollPane jScrollPane4 = null;

	private JTable tbGroupDetail = null;

	private JPanel jPanel5 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JCalendarComboBox cbbGroupDate = null;

	private JComboBox cbbGroupState = null;

	private JButton btnSetState = null;

	private JButton btnSetDate = null;

	private JButton btnUndoConfirm = null;

	private JButton btnDeleteDel = null;

	private JButton btnEditDel = null;

	private JButton btnDeleteAdd = null;

	private JButton btnEditAdd = null;

	private JButton btnImportAdd = null;

	private JButton btnGroup = null;

	private JButton btnDeleteCommInfo = null;

	private JButton btnEditCommInfo = null;

	private JButton btnSaveAgreement = null;

	private JButton btnCancel = null;

	private Agreement agreement; // @jve:decl-index=0:

	private FixAssetAction fixAssetAction; // @jve:decl-index=0:

	private DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:visual-constraint=""

	private NumberFormatter numberFormatter = null; // @jve:decl-index=0:visual-constraint=""

	private JTableListModel tableModelCommInfo;

	private JTableListModel tableModelImp;

	private JTableListModel tableModelDelete;

	private JTableListModel tableModelGroupHead;

	private JTableListModel tableModelGroupDetail;

	private JButton btnAddNewAdd = null;

	private int btnState = 0;

	private CheckFixAuthorityAction checkFixAuthorityAction = null; // @jve:decl-index=0:

	public DgAgreement() {
		super();
		initialize();
	}

	public DgAgreement(boolean isModal) {
		super(isModal);
		initialize();
	}

	public DgAgreement(JFrame owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public DgAgreement(Dialog owner, boolean isModal) {
		super(owner, isModal);
		initialize();
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(745, 514));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("设备批文");
		this.setContentPane(getJContentPane());
		fixAssetAction = (FixAssetAction) CommonVars.getApplicationContext()
				.getBean("fixAssetAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			this.showHeadData(agreement);
			setState();
		}
		super.setVisible(b);
	}

	private void initUIComponents() {
		// 状态类型
		this.cbbGroupState.removeAllItems();
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.RECEIVE).toString(), AgreementGroupState
				.getStateDesc(AgreementGroupState.RECEIVE)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.CERT_HANDIN).toString(),
				AgreementGroupState
						.getStateDesc(AgreementGroupState.CERT_HANDIN)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.CERT_OUTBILL).toString(),
				AgreementGroupState
						.getStateDesc(AgreementGroupState.CERT_OUTBILL)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.ON_OUT_NOTICE).toString(),
				AgreementGroupState
						.getStateDesc(AgreementGroupState.ON_OUT_NOTICE)));

		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.ARRIVE_HK).toString(), AgreementGroupState
				.getStateDesc(AgreementGroupState.ARRIVE_HK)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.ARRIVE_DG).toString(), AgreementGroupState
				.getStateDesc(AgreementGroupState.ARRIVE_DG)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.WAR).toString(), AgreementGroupState
				.getStateDesc(AgreementGroupState.WAR)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.APPLY_TO_CUSTOMS).toString(),
				AgreementGroupState
						.getStateDesc(AgreementGroupState.APPLY_TO_CUSTOMS)));
		this.cbbGroupState.addItem(new ItemProperty(Integer.valueOf(
				AgreementGroupState.IN_FACTORY).toString(), AgreementGroupState
				.getStateDesc(AgreementGroupState.IN_FACTORY)));

		this.cbbGroupState.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbGroupState);
		this.cbbGroupState.setSelectedIndex(-1);

		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		this.cbbApproveDate.setDate(null);

		// 初始化征免性质
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.cbbLevyKind
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyKind);

		// 初始化币制
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbCurr);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("批文表头", null, getPnHead(), null);
			jTabbedPane.addTab("批文表体", null, getPnDetail(), null);
			jTabbedPane.addTab("分组状态", null, getPnGroup(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								agreement = fixAssetAction.findAgreement(
										new Request(CommonVars.getCurrUser()),
										agreement);
								showHeadData(agreement);
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								List lsCommInfo = fixAssetAction
										.findAgreementCommInfo(new Request(
												CommonVars.getCurrUser()),
												agreement);
								initCommInfoTable(lsCommInfo);
								List lsAdd = fixAssetAction
										.findImpAgreementCommInfo(new Request(
												CommonVars.getCurrUser()),
												agreement);
								initImpTable(lsAdd);
								List lsDelete = fixAssetAction
										.findDeleteAgreementCommInfo(
												new Request(CommonVars
														.getCurrUser()),
												agreement);
								initDeleteTable(lsDelete);
							} else if (jTabbedPane.getSelectedIndex() == 2) {
								List list = fixAssetAction
										.findAgreementGroupHead(new Request(
												CommonVars.getCurrUser()),
												agreement);
								initGroupHeadTable(list);
							}
						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnHead() {
		if (pnHead == null) {
			pnHead = new JPanel();
			pnHead.setLayout(null);
			pnHead.add(getJPanel(), null);
			pnHead.add(getJPanel1(), null);
			pnHead.add(getBtnSaveAgreement(), null);
			pnHead.add(getBtnCancel(), null);
		}
		return pnHead;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnDetail() {
		if (pnDetail == null) {
			pnDetail = new JPanel();
			pnDetail.setLayout(new BorderLayout());
			pnDetail.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return pnDetail;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnGroup() {
		if (pnGroup == null) {
			pnGroup = new JPanel();
			pnGroup.setLayout(new BorderLayout());
			pnGroup.add(getJSplitPane2(), java.awt.BorderLayout.CENTER);
		}
		return pnGroup;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(32, 288, 79, 26));
			jLabel7.setText("变更累计余额");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(32, 254, 79, 24));
			jLabel6.setText("协议状态");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(32, 213, 79, 27));
			jLabel5.setForeground(Color.blue);
			jLabel5.setText("设备金额");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(32, 176, 79, 23));
			jLabel4.setText("审批日期");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(32, 138, 79, 24));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("有效日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(32, 102, 79, 21));
			jLabel2.setForeground(Color.blue);
			jLabel2.setText("起始日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(32, 64, 79, 24));
			jLabel1.setForeground(Color.blue);
			jLabel1.setText("协议批文号");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(32, 34, 79, 22));
			jLabel.setForeground(Color.blue);
			jLabel.setText("设备手册号");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new java.awt.Rectangle(41, 32, 277, 368));
			jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.SystemColor.controlDkShadow, 1));
			jPanel.add(jLabel, null);
			jPanel.add(getTfEmsNo(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getTfSancEmsNo(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbbApproveDate(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTfTotalMoney(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(getTfStateMark(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getTfChangeRemainMoney(), null);
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
			jLabel18 = new JLabel();
			jLabel18.setBounds(new java.awt.Rectangle(17, 318, 73, 19));
			jLabel18.setText("经营范围");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(17, 291, 73, 21));
			jLabel17.setText("征免性质");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new java.awt.Rectangle(17, 258, 73, 22));
			jLabel16.setForeground(Color.blue);
			jLabel16.setText("投资总额");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new java.awt.Rectangle(17, 228, 73, 24));
			jLabel15.setForeground(Color.blue);
			jLabel15.setText("注册资金");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new java.awt.Rectangle(17, 195, 73, 29));
			jLabel14.setForeground(Color.blue);
			jLabel14.setText("币制");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new java.awt.Rectangle(17, 165, 73, 24));
			jLabel13.setText("联系电话");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(17, 134, 73, 26));
			jLabel12.setText("联系人");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(17, 105, 73, 22));
			jLabel11.setText("企业地址");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(17, 75, 73, 24));
			jLabel10.setText("海关编码");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(17, 43, 73, 28));
			jLabel9.setText("企业名称");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(17, 13, 73, 27));
			jLabel8.setText("经营单位");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new java.awt.Rectangle(371, 32, 277, 368));
			jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(
					java.awt.SystemColor.controlDkShadow, 1));
			jPanel1.add(jLabel8, null);
			jPanel1.add(jLabel9, null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(jLabel11, null);
			jPanel1.add(jLabel12, null);
			jPanel1.add(jLabel13, null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(jLabel15, null);
			jPanel1.add(jLabel16, null);
			jPanel1.add(jLabel17, null);
			jPanel1.add(jLabel18, null);
			jPanel1.add(getTfTradeCode(), null);
			jPanel1.add(getTfTradeName(), null);
			jPanel1.add(getTfBriefName(), null);
			jPanel1.add(getTfAddress(), null);
			jPanel1.add(getTfLinkMan(), null);
			jPanel1.add(getTfLinkTel(), null);
			jPanel1.add(getTfWorkingRange(), null);
			jPanel1.add(getTfRegisteredMoney(), null);
			jPanel1.add(getTfInvestMoney(), null);
			jPanel1.add(getCbbCurr(), null);
			jPanel1.add(getCbbLevyKind(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(new java.awt.Rectangle(113, 35, 136, 22));
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfSancEmsNo() {
		if (tfSancEmsNo == null) {
			tfSancEmsNo = new JTextField();
			tfSancEmsNo.setBounds(new java.awt.Rectangle(113, 67, 136, 22));
		}
		return tfSancEmsNo;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new java.awt.Rectangle(113, 106, 136, 22));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(113, 140, 136, 22));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jCalendarComboBox2
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbApproveDate() {
		if (cbbApproveDate == null) {
			cbbApproveDate = new JCalendarComboBox();
			cbbApproveDate.setBounds(new java.awt.Rectangle(113, 179, 136, 22));
		}
		return cbbApproveDate;
	}

	/**
	 * This method initializes jCustomFormattedTextField
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfTotalMoney() {
		if (tfTotalMoney == null) {
			tfTotalMoney = new JCustomFormattedTextField();
			tfTotalMoney.setBounds(new java.awt.Rectangle(113, 217, 136, 22));
			tfTotalMoney.setEditable(false);
			tfTotalMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfTotalMoney;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfStateMark() {
		if (tfStateMark == null) {
			tfStateMark = new JTextField();
			tfStateMark.setBounds(new java.awt.Rectangle(113, 256, 136, 22));
			tfStateMark.setEditable(false);
		}
		return tfStateMark;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfChangeRemainMoney() {
		if (tfChangeRemainMoney == null) {
			tfChangeRemainMoney = new JCustomFormattedTextField();
			tfChangeRemainMoney.setBounds(new java.awt.Rectangle(113, 291, 136,
					22));
			tfChangeRemainMoney.setEditable(false);
			tfChangeRemainMoney
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfChangeRemainMoney;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(new java.awt.Rectangle(98, 15, 167, 23));
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new java.awt.Rectangle(98, 43, 167, 23));
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBriefName() {
		if (tfBriefName == null) {
			tfBriefName = new JTextField();
			tfBriefName.setBounds(new java.awt.Rectangle(98, 74, 167, 23));
			tfBriefName.setEditable(false);
		}
		return tfBriefName;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAddress() {
		if (tfAddress == null) {
			tfAddress = new JTextField();
			tfAddress.setBounds(new java.awt.Rectangle(98, 104, 167, 23));
		}
		return tfAddress;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLinkMan() {
		if (tfLinkMan == null) {
			tfLinkMan = new JTextField();
			tfLinkMan.setBounds(new java.awt.Rectangle(98, 132, 167, 23));
		}
		return tfLinkMan;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLinkTel() {
		if (tfLinkTel == null) {
			tfLinkTel = new JTextField();
			tfLinkTel.setBounds(new java.awt.Rectangle(98, 164, 167, 23));
		}
		return tfLinkTel;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfWorkingRange() {
		if (tfWorkingRange == null) {
			tfWorkingRange = new JTextField();
			tfWorkingRange.setBounds(new java.awt.Rectangle(98, 316, 167, 23));
		}
		return tfWorkingRange;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfRegisteredMoney() {
		if (tfRegisteredMoney == null) {
			tfRegisteredMoney = new JCustomFormattedTextField();
			tfRegisteredMoney
					.setBounds(new java.awt.Rectangle(98, 229, 167, 23));
			tfRegisteredMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfRegisteredMoney;
	}

	/**
	 * This method initializes jCustomFormattedTextField3
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfInvestMoney() {
		if (tfInvestMoney == null) {
			tfInvestMoney = new JCustomFormattedTextField();
			tfInvestMoney.setBounds(new java.awt.Rectangle(98, 261, 167, 23));
			tfInvestMoney.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfInvestMoney;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(new java.awt.Rectangle(98, 196, 167, 23));
		}
		return cbbCurr;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(new java.awt.Rectangle(98, 290, 167, 23));
		}
		return cbbLevyKind;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(200);
			jSplitPane.setTopComponent(getJPanel2());
			jSplitPane.setBottomComponent(getJSplitPane1());
			jSplitPane.setDividerSize(4);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAddCommInfo());
			jToolBar.add(getBtnEditCommInfo());
			jToolBar.add(getBtnDeleteCommInfo());
			jToolBar.add(getBtnChangingCommInfoNo());
//			jToolBar.add(getBtnCommInfoNoSort());
			jToolBar.add(getBtnSort());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddCommInfo() {
		if (btnAddCommInfo == null) {
			btnAddCommInfo = new JButton();
			btnAddCommInfo.setText("新增");
			btnAddCommInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAgreementAddCommInfo(new Request(
											CommonVars.getCurrUser()));
							List list = FixAssetQuery.getInstance()
									.findComplexNotInAgreement(agreement);
							if(list==null)
								return ;
							list = fixAssetAction.addAgreementCommInfo(
									new Request(CommonVars.getCurrUser()),
									agreement, list);
							tableModelCommInfo.addRows(list);
						}
					});
		}
		return btnAddCommInfo;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbCommInfo());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCommInfo() {
		if (tbCommInfo == null) {
			tbCommInfo = new JTable();
		}
		return tbCommInfo;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerSize(4);
			jSplitPane1.setLeftComponent(getJPanel3());
			jSplitPane1.setRightComponent(getJPanel4());
			jSplitPane1.setDividerLocation(350);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel19 = new JLabel();
			jLabel19.setText("取消进口的物品");
			jLabel19.setForeground(java.awt.Color.blue);
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(jLabel19, java.awt.BorderLayout.NORTH);
			jPanel3.add(getJToolBar1(), java.awt.BorderLayout.SOUTH);
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel20 = new JLabel();
			jLabel20.setText("增加进口的物品");
			jLabel20.setForeground(java.awt.Color.blue);
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(jLabel20, java.awt.BorderLayout.NORTH);
			jPanel4.add(getJToolBar2(), java.awt.BorderLayout.SOUTH);
			jPanel4.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtnAddDel());
			jToolBar1.add(getBtnEditDel());
			jToolBar1.add(getBtnDeleteDel());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getBtnAddAdd());
			jToolBar2.add(getBtnEditAdd());
			jToolBar2.add(getBtnImportAdd());
			jToolBar2.add(getBtnDeleteAdd());
			jToolBar2.add(getBtnAddNewAdd());
			jToolBar2.add(getBtnGroup());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddDel() {
		if (btnAddDel == null) {
			btnAddDel = new JButton();
			btnAddDel.setText("新增");
			btnAddDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementAddDel(new Request(
							CommonVars.getCurrUser()));
					if (tableModelCommInfo.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择要取消的设备", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgAgreement.this,
							"你真的要取消这些设备吗", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					List list = tableModelCommInfo.getCurrentRows();
					list = fixAssetAction.addDeleteAgreementCommInfo(
							new Request(CommonVars.getCurrUser()), agreement,
							list);
					tableModelDelete.addRows(list);
				}
			});
		}
		return btnAddDel;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddAdd() {
		if (btnAddAdd == null) {
			btnAddAdd = new JButton();
			btnAddAdd.setText("进口备案设备");
			btnAddAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementAddAdd(new Request(
							CommonVars.getCurrUser()));
					if (tableModelCommInfo.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择要进口的备案设备", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = tableModelCommInfo.getCurrentRows();
					list = fixAssetAction.addPORAgreementCommInfo(new Request(
							CommonVars.getCurrUser()), agreement, list);
					tableModelImp.addRows(list);
				}
			});
		}
		return btnAddAdd;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbDelete());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbDelete() {
		if (tbDelete == null) {
			tbDelete = new JTable();
		}
		return tbDelete;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbImp());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImp() {
		if (tbImp == null) {
			tbImp = new JTable();
		}
		return tbImp;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setDividerLocation(180);
			jSplitPane2.setDividerSize(4);
			jSplitPane2.setBottomComponent(getJSplitPane3());
			jSplitPane2.setTopComponent(getJScrollPane3());
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbGroup());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable3
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbGroup() {
		if (tbGroup == null) {
			tbGroup = new JTable();
			tbGroup.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbGroup
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							List list = new ArrayList();
							if (!lsm.isSelectionEmpty()) {
								if (tableModel.getCurrentRow() != null) {
									AgreementGroupHead groupHead = ((AgreementGroupHead) tableModel
											.getCurrentRow());
									// lbContractExg.setText("当前单耗的成品序号："
									// + exg.getSeqNum() + " " + "名称："
									// + exg.getName());
									list = fixAssetAction
											.findAgreementGroupDetail(
													new Request(CommonVars
															.getCurrUser()),
													groupHead);
								}
							}
							initGroupDetailTable(list);
						}
					});
		}
		return tbGroup;
	}

	/**
	 * This method initializes jSplitPane3
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane3.setDividerSize(3);
			jSplitPane3.setDividerLocation(200);
			jSplitPane3.setBottomComponent(getJPanel5());
			jSplitPane3.setTopComponent(getJScrollPane4());
		}
		return jSplitPane3;
	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getTbGroupDetail());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jTable4
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbGroupDetail() {
		if (tbGroupDetail == null) {
			tbGroupDetail = new JTable();
		}
		return tbGroupDetail;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel22 = new JLabel();
			jLabel22.setBounds(new java.awt.Rectangle(50, 33, 29, 16));
			jLabel22.setText("日期");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new java.awt.Rectangle(50, 6, 29, 16));
			jLabel21.setText("状态");
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.add(jLabel21, null);
			jPanel5.add(jLabel22, null);
			jPanel5.add(getCbbGroupDate(), null);
			jPanel5.add(getCbbGroupState(), null);
			jPanel5.add(getBtnSetState(), null);
			jPanel5.add(getBtnSetDate(), null);
			jPanel5.add(getBtnUndoConfirm(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbGroupDate() {
		if (cbbGroupDate == null) {
			cbbGroupDate = new JCalendarComboBox();
			cbbGroupDate.setBounds(new java.awt.Rectangle(80, 31, 133, 21));
		}
		return cbbGroupDate;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbGroupState() {
		if (cbbGroupState == null) {
			cbbGroupState = new JComboBox();
			cbbGroupState.setBounds(new java.awt.Rectangle(80, 6, 133, 21));
			cbbGroupState.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						if (tableModelGroupHead == null
								|| tableModelGroupHead.getCurrentRow() == null) {
							return;
						}
						AgreementGroupHead groupHead = (AgreementGroupHead) tableModelGroupHead
								.getCurrentRow();
						int state = Integer
								.valueOf(((ItemProperty) cbbGroupState
										.getSelectedItem()).getCode());
						switch (state) {
						case AgreementGroupState.RECEIVE:
							cbbGroupDate.setDate(groupHead.getCreateDate());
							break;
						case AgreementGroupState.CERT_HANDIN:
							cbbGroupDate.setDate(groupHead
									.getDutyCertApplyDate());
							break;
						case AgreementGroupState.CERT_OUTBILL:
							cbbGroupDate.setDate(groupHead
									.getDutyCertApproveDate());
							break;
						case AgreementGroupState.ON_OUT_NOTICE:
							cbbGroupDate.setDate(groupHead
									.getPrepExportNoteDate());
							break;
						case AgreementGroupState.ARRIVE_HK:
							cbbGroupDate.setDate(groupHead.getArriveHKDate());
							break;
						case AgreementGroupState.ARRIVE_DG:
							cbbGroupDate.setDate(groupHead.getArriveDGDate());
							break;
						case AgreementGroupState.WAR:
							cbbGroupDate.setDate(groupHead.getInspectDate());
							break;
						case AgreementGroupState.APPLY_TO_CUSTOMS:
							cbbGroupDate.setDate(groupHead.getDeclareDate());
							break;
						case AgreementGroupState.IN_FACTORY:
							cbbGroupDate.setDate(groupHead.getInFactDate());
							break;
						}
					}
				}
			});
		}
		return cbbGroupState;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSetState() {
		if (btnSetState == null) {
			btnSetState = new JButton();
			btnSetState.setBounds(new java.awt.Rectangle(249, 5, 124, 21));
			btnSetState.setText("设定为当前状态");
			btnSetState.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementSetState(new Request(
							CommonVars.getCurrUser()));
					if (cbbGroupState.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择状态", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (tableModelGroupHead.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择你要修改状态的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					AgreementGroupHead groupHead = (AgreementGroupHead) tableModelGroupHead
							.getCurrentRow();
					groupHead.setStateMark(Integer
							.valueOf(((ItemProperty) cbbGroupState
									.getSelectedItem()).getCode()));
					groupHead = fixAssetAction.saveAgreementGroupHead(
							new Request(CommonVars.getCurrUser()), groupHead);
					tableModelGroupHead.updateRow(groupHead);

				}
			});
		}
		return btnSetState;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSetDate() {
		if (btnSetDate == null) {
			btnSetDate = new JButton();
			btnSetDate.setBounds(new java.awt.Rectangle(249, 33, 124, 21));
			btnSetDate.setText("更改状态日期");
			btnSetDate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementSetDate(new Request(
							CommonVars.getCurrUser()));
					if (tableModelGroupHead.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择你要修改状态的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (cbbGroupState.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择状态", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (cbbGroupDate.getDate() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择日期", "提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					AgreementGroupHead groupHead = (AgreementGroupHead) tableModelGroupHead
							.getCurrentRow();
					int state = Integer.valueOf(((ItemProperty) cbbGroupState
							.getSelectedItem()).getCode());
					switch (state) {
					case AgreementGroupState.RECEIVE:
						groupHead.setCreateDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.CERT_HANDIN:
						groupHead.setDutyCertApplyDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.CERT_OUTBILL:
						groupHead
								.setDutyCertApproveDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.ON_OUT_NOTICE:
						groupHead.setPrepExportNoteDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.ARRIVE_HK:
						groupHead.setArriveHKDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.ARRIVE_DG:
						groupHead.setArriveDGDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.WAR:
						groupHead.setInspectDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.APPLY_TO_CUSTOMS:
						groupHead.setDeclareDate(cbbGroupDate.getDate());
						break;
					case AgreementGroupState.IN_FACTORY:
						groupHead.setInFactDate(cbbGroupDate.getDate());
						break;
					}
					groupHead = fixAssetAction.saveAgreementGroupHead(
							new Request(CommonVars.getCurrUser()), groupHead);
					tableModelGroupHead.updateRow(groupHead);
				}
			});
		}
		return btnSetDate;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndoConfirm() {
		if (btnUndoConfirm == null) {
			btnUndoConfirm = new JButton();
			btnUndoConfirm.setBounds(new java.awt.Rectangle(395, 21, 120, 28));
			btnUndoConfirm.setText("取消递单");
			btnUndoConfirm
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAgreementUndoConfirm(new Request(
											CommonVars.getCurrUser()));
							if (tableModelGroupHead.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgAgreement.this,
										"请选择你要取消递单的资料", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							AgreementGroupHead groupHead = (AgreementGroupHead) tableModelGroupHead
									.getCurrentRow();
							if (groupHead.getStateMark() == AgreementGroupState.RECEIVE) {
								JOptionPane.showMessageDialog(DgAgreement.this,
										"此批商品已经取消递单，不能取消递单", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (groupHead.getStateMark() == AgreementGroupState.APPLY_TO_CUSTOMS) {
								JOptionPane.showMessageDialog(DgAgreement.this,
										"此批商品已经报关，不能取消递单", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							fixAssetAction.cancelHandInBill(new Request(
									CommonVars.getCurrUser()), groupHead
									.getAgreement(), groupHead
									.getChangedTimes());
							List list = fixAssetAction.findAgreementGroupHead(
									new Request(CommonVars.getCurrUser()),
									agreement);
							initGroupHeadTable(list);
							JOptionPane.showMessageDialog(DgAgreement.this,
									"取消递单完成!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
						}
					});
		}
		return btnUndoConfirm;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteDel() {
		if (btnDeleteDel == null) {
			btnDeleteDel = new JButton();
			btnDeleteDel.setText("删除");
			btnDeleteDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkAgreementDeleteDel(new Request(CommonVars
									.getCurrUser()));
					if (tableModelDelete.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择要删除的设备", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgAgreement.this,
							"你真的要删除这些设备吗", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					// DeleteAgreementCommInfo commInfo =
					// (DeleteAgreementCommInfo) tableModelDelete
					// .getCurrentRow();
					List list = tableModelDelete.getCurrentRows();
					fixAssetAction.deleteDeleteAgreementCommInfo(new Request(
							CommonVars.getCurrUser()), list);
					tableModelDelete.deleteRows(list);
				}
			});
		}
		return btnDeleteDel;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditDel() {
		if (btnEditDel == null) {
			btnEditDel = new JButton();
			btnEditDel.setText("修改");
			btnEditDel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementEditDel(new Request(
							CommonVars.getCurrUser()));
					if (tableModelDelete.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择要修改的设备", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgImpDelAgreementCommInfo dg = new DgImpDelAgreementCommInfo();
					dg.setTableModel(tableModelDelete);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEditDel;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteAdd() {
		if (btnDeleteAdd == null) {
			btnDeleteAdd = new JButton();
			btnDeleteAdd.setText("删除");
			btnDeleteAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkAgreementDeleteAdd(new Request(CommonVars
									.getCurrUser()));
					if (tableModelImp.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择要删除的进口设备", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = tableModelImp.getCurrentRows();
					for (int i = 0; i < list.size(); i++) {
						ImpAgreementCommInfo impCommInfo = (ImpAgreementCommInfo) list
								.get(i);
						int count = fixAssetAction
								.findAgreementGroupCountNoHandIn(new Request(
										CommonVars.getCurrUser()), impCommInfo
										.getAgreement(), impCommInfo
										.getMainNo());
						if (count > 0) {
							JOptionPane.showMessageDialog(DgAgreement.this,
									"商品" + impCommInfo.getComplex().getCode()
											+ "在分组集结中还有未向海关递单的资料，所以不能删除", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					fixAssetAction.deleteImpAgreementCommInfo(new Request(
							CommonVars.getCurrUser()), list);
					tableModelImp.deleteRows(list);
				}
			});
		}
		return btnDeleteAdd;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditAdd() {
		if (btnEditAdd == null) {
			btnEditAdd = new JButton();
			btnEditAdd.setText("修改");
			btnEditAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementEditAdd(new Request(
							CommonVars.getCurrUser()));
					if (tableModelImp.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgAgreement.this,
								"请选择要修改的设备", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgImpDelAgreementCommInfo dg = new DgImpDelAgreementCommInfo();
					dg.setTableModel(tableModelImp);
					dg.setDataState(DataState.EDIT);
					dg.setVisible(true);
					showImpCommInfo();
				}
			});
		}
		return btnEditAdd;
	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportAdd() {
		if (btnImportAdd == null) {
			btnImportAdd = new JButton();
			btnImportAdd.setText("导入新设备");
			btnImportAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkAgreementImportAdd(new Request(CommonVars
									.getCurrUser()));
					// if (tableModel.getCurrentRow() == null) {
					// JOptionPane.showMessageDialog(FmAgreement.this,
					// "请选择你要导入设备明细的批文", "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// }
					// Agreement agreement = (Agreement) tableModel
					// .getCurrentRow();
					DgImportAgreementCommInfo dg = new DgImportAgreementCommInfo();
					dg.setChange(true);
					dg.setAgreement(agreement);
					dg.setVisible(true);
					showImpCommInfo();
				}
			});
		}
		return btnImportAdd;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnGroup() {
		if (btnGroup == null) {
			btnGroup = new JButton();
			btnGroup.setText("分组集结");
			btnGroup.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkAgreementGroup(new Request(
							CommonVars.getCurrUser()));
					// if(tableModelImp.getList().size()<=0){
					// JOptionPane.showMessageDialog(DgAgreement.this,
					// "请新增要进口的设备", "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }
					DgAgreementGroup dgGroup = new DgAgreementGroup();
					dgGroup.setAgreement(agreement);
					dgGroup.setVisible(true);
					showImpCommInfo();
					List lsDelete = fixAssetAction.findDeleteAgreementCommInfo(
							new Request(CommonVars.getCurrUser()), agreement);
					initDeleteTable(lsDelete);
				}
			});
		}
		return btnGroup;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteCommInfo() {
		if (btnDeleteCommInfo == null) {
			btnDeleteCommInfo = new JButton();
			btnDeleteCommInfo.setText("删除");
			btnDeleteCommInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAgreementDeleteCommInfo(new Request(
											CommonVars.getCurrUser()));
							List list = tableModelCommInfo.getCurrentRows();
							if (list.size() <= 0) {
								JOptionPane.showMessageDialog(DgAgreement.this,
										"请选择要删除的设备", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(DgAgreement.this,
									"你真的要删除这些设备吗", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							list = fixAssetAction
									.deleteAgreementCommInfo(new Request(
											CommonVars.getCurrUser()), list);
							tableModelCommInfo.deleteRows(list);
						}
					});
		}
		return btnDeleteCommInfo;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditCommInfo() {
		if (btnEditCommInfo == null) {
			btnEditCommInfo = new JButton();
			btnEditCommInfo.setText("修改");
			btnEditCommInfo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAgreementEditCommInfo(new Request(
											CommonVars.getCurrUser()));
							if (tableModelCommInfo.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgAgreement.this,
										"请选择要修改的设备", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgAgreementCommInfo dg = new DgAgreementCommInfo();
							dg.setTableModel(tableModelCommInfo);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnEditCommInfo;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangingCommInfoNo() {
		if (btnChangingCommInfoNo == null) {
			btnChangingCommInfoNo = new JButton();
			btnChangingCommInfoNo.setText("变更序号");
			btnChangingCommInfoNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkChangingAgreementCommInfoNo(new Request(
											CommonVars.getCurrUser()));
							if (tableModelCommInfo == null
									|| tableModelCommInfo.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgAgreement.this,
										"请选择要变更的设备记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgChangeCommInfoSeqNum dg = new DgChangeCommInfoSeqNum();
							dg.setTableModel(tableModelCommInfo);
							dg.setVisible(true);
							if (dg.isOk()) {
								if (agreement != null) {
									String parentId = agreement.getId();
									List list = fixAssetAction
											.findAgreementCommInfoByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initCommInfoTable(list);
								}
							}
						}
					});
		}
		return btnChangingCommInfoNo;
	}

	/**
	 * btnSort This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCommInfoNoSort() {
		if (btnCommInfoNoSort == null) {
			btnCommInfoNoSort = new JButton();
			btnCommInfoNoSort.setText("设备自由排序");
			btnCommInfoNoSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							checkFixAuthorityAction
									.checkAgreementCommInfoSort(new Request(
											CommonVars.getCurrUser()));
							if (tableModelCommInfo == null
									|| tableModelCommInfo.getList().size() <= 0) {
								return;
							}
							List list = tableModelCommInfo.getList();
							if (list.size() <= 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(DgAgreement.this,
									"是否将设备排序!!!", "提示",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								Vector vet = new Vector();
								int size = tableModelCommInfo.getSize();
								for (int i = 0; i < size; i++) {
									vet.add(tableModelCommInfo.getDataByRow(i));
								}
								DgCommInfoNoSort dg = new DgCommInfoNoSort();
								dg.setList(vet);
								dg.setFixAssetAction(fixAssetAction);
								dg.setVisible(true);
								if (dg.isOk()) {
									list = dg.getList();
									tableModelCommInfo.updateRows(list);
								}
								if (agreement != null) {
									String parentId = agreement.getId();
									list = fixAssetAction
											.findAgreementCommInfoByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initCommInfoTable(list);
								}
							}
						}
					});
		}
		return btnCommInfoNoSort;
	}

	/**
	 * btnSort This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSort() {
		if (btnSort == null) {
			btnSort = new JButton();
			btnSort.setText("排序");
			btnSort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkAgreementCommInfoSort(new Request(CommonVars
									.getCurrUser()));
					List list=tableModelCommInfo.getList();
					List arrayList=new ArrayList();
					for(int i=0;i<list.size();i++){
						AgreementCommInfo agreementCommInfo=(AgreementCommInfo)list.get(i);
						agreementCommInfo.setMainNo(i+1);
						arrayList.add(agreementCommInfo);
					}
					arrayList=fixAssetAction.saveAgreementCommInfo(new Request(CommonVars
							.getCurrUser()), arrayList);
					tableModelCommInfo.updateRows(arrayList);
//					initCommInfoTable(arrayList);
				}
			});
		}
		return btnSort;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveAgreement() {
		if (btnSaveAgreement == null) {
			btnSaveAgreement = new JButton();
			btnSaveAgreement
					.setBounds(new java.awt.Rectangle(422, 416, 78, 24));
			btnSaveAgreement.setText("保存");
			btnSaveAgreement
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							fillHeadData(agreement);
							btnState = 1;
							setState();
							agreement = fixAssetAction.saveAgreement(
									new Request(CommonVars.getCurrUser()),
									agreement);
						}
					});
		}
		return btnSaveAgreement;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setBounds(new java.awt.Rectangle(533, 416, 75, 24));
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showHeadData(agreement);
					btnState = 0;
					setState();
				}
			});
		}
		return btnCancel;
	}

	private void showHeadData(Agreement agreement) {
		if (agreement == null) {
			return;
		}
		this.tfEmsNo.setText(agreement.getEmsNo());
		this.tfSancEmsNo.setText(agreement.getSancEmsNo());
		this.cbbBeginDate.setDate(agreement.getBeginDate());
		this.cbbEndDate.setDate(agreement.getEndDate());
		this.cbbApproveDate.setDate(agreement.getApproveDate());
		this.tfTotalMoney.setValue(agreement.getTotalMoney());
		this.tfChangeRemainMoney.setValue(agreement.getChangeRemainMoney());
		this.tfTradeCode.setText(agreement.getTradeCode());
		this.tfTradeName.setText(agreement.getTradeName());
		this.tfBriefName.setText(agreement.getMachCode());
		this.tfAddress.setText(agreement.getAddress());
		this.tfLinkMan.setText(agreement.getLinkMan());
		this.tfLinkTel.setText(agreement.getLinkTel());
		this.tfWorkingRange.setText(agreement.getWorkingRange());
		this.cbbCurr.setSelectedItem(agreement.getCurr());
		this.cbbLevyKind.setSelectedItem(agreement.getLevyKind());
		this.tfRegisteredMoney.setValue(agreement.getRegisteredMoney());
		this.tfInvestMoney.setValue(agreement.getInvestMoney());
		this.tfStateMark.setText(AgreementState.getStateDesc(agreement
				.getDeclareState() == null ? -1 : agreement.getDeclareState()));
	}

	private void fillHeadData(Agreement agreement) {
		if (agreement == null) {
			return;
		}
		agreement.setEmsNo(tfEmsNo.getText());
		agreement.setSancEmsNo(this.tfSancEmsNo.getText());
		agreement.setBeginDate(this.cbbBeginDate.getDate());
		agreement.setEndDate(this.cbbEndDate.getDate());
		agreement.setApproveDate(this.cbbApproveDate.getDate());
		agreement.setTotalMoney(this.tfTotalMoney.getValue() == null ? 0
				: Double.parseDouble(this.tfTotalMoney.getValue().toString()));
		agreement
				.setChangeRemainMoney(this.tfChangeRemainMoney.getValue() == null ? 0
						: Double.parseDouble(this.tfChangeRemainMoney
								.getValue().toString()));

		agreement.setAddress(this.tfAddress.getText());
		agreement.setLinkMan(this.tfLinkMan.getText());
		agreement.setLinkTel(this.tfLinkTel.getText());
		agreement.setWorkingRange(this.tfWorkingRange.getText());
		agreement.setCurr((Curr) this.cbbCurr.getSelectedItem());
		agreement.setLevyKind((LevyKind) this.cbbLevyKind.getSelectedItem());
		agreement
				.setRegisteredMoney(this.tfRegisteredMoney.getValue() == null ? 0
						: Double.parseDouble(this.tfRegisteredMoney.getValue()
								.toString()));
		agreement.setInvestMoney(this.tfInvestMoney.getValue() == null ? 0
				: Double.parseDouble(this.tfInvestMoney.getValue().toString()));
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	private DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDefaultFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
			defaultFormatterFactory.setNullFormatter(getNumberFormatter());
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	private NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	private void initCommInfoTable(List list) {
		JTableListModel.dataBind(tbCommInfo, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list
								.add(addColumn("主序号", "mainNo", 100,
										Integer.class));
						list.add(addColumn("海关编号", "complex.code", 100));
						list.add(addColumn("名称", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("金额", "totalPrice", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("原产地", "country.name", 100));
						list.add(addColumn("状态", "stateMark", 100));
						return list;
					}
				});
		tableModelCommInfo = (JTableListModel) tbCommInfo.getModel();
		tbCommInfo.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = AgreementState.getStateDesc(Integer
									.parseInt(value.toString()));
						}
						super.setText(str);
						return this;
					}
				});
	}

	private void initImpTable(List list) {
		JTableListModel.dataBind(tbImp, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("主序号", "mainNo", 100, Integer.class));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("金额", "totalPrice", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("状态", "stateMark", 100));
				return list;
			}
		});
		tableModelImp = (JTableListModel) tbImp.getModel();
		tbImp.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = AgreementState.getStateDesc(Integer
									.parseInt(value.toString()));
						}
						super.setText(str);
						return this;
					}
				});

	}

	private void initDeleteTable(List list) {
		JTableListModel.dataBind(tbDelete, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("主序号", "mainNo", 100, Integer.class));
				list.add(addColumn("海关编号", "complex.code", 100));
				list.add(addColumn("名称", "commName", 100));
				list.add(addColumn("规格", "commSpec", 100));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("金额", "totalPrice", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("状态", "stateMark", 100));
				return list;
			}
		});
		tableModelDelete = (JTableListModel) tbDelete.getModel();
		tbDelete.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = AgreementState.getStateDesc(Integer
									.parseInt(value.toString()));
						}
						super.setText(str);
						return this;
					}
				});
	}

	private void initGroupHeadTable(List list) {
		JTableListModel.dataBind(tbGroup, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<Object> list = (List<Object>) (new Vector());
				list.add(addColumn("变更次数", "changedTimes", 100));
				list.add(addColumn("组号", "groupNo", 100));
				list.add(addColumn("发票号", "invoiceNo", 100));
				list.add(addColumn("征免税号", "certNo", 100));
				list.add(addColumn("保证书号", "warNo", 100));
				list.add(addColumn("状态", "stateMark", 100));
				list.add(addColumn("收件日期", "createDate", 100));
				list.add(addColumn("征免税证递单日期", "dutyCertApplyDate", 100));
				list.add(addColumn("征免税证出单日期", "dutyCertApproveDate", 100));
				list.add(addColumn("待出货通知日期", "prepExportNoteDate", 100));
				list.add(addColumn("设备到港日期", "arriveHKDate", 100));
				list.add(addColumn("设备到莞日期", "arriveDGDate", 100));
				list.add(addColumn("商检日期", "inspectDate", 100));
				list.add(addColumn("报关日期", "declareDate", 100));
				list.add(addColumn("进厂日期", "inFactDate", 100));
				return list;
			}
		});
		tableModelGroupHead = (JTableListModel) tbGroup.getModel();
		tbGroup.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = AgreementGroupState.getStateDesc(Integer
									.parseInt(value.toString()));
						}
						super.setText(str);
						return this;
					}
				});
	}

	private void initGroupDetailTable(List list) {
		JTableListModel.dataBind(tbGroupDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("组号", "groupHead.groupNo", 100));
						list.add(addColumn("主序号", "mainNo", 100));
						list.add(addColumn("海关编号", "complex.code", 100));
						list.add(addColumn("名称", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("金额", "totalPrice", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("原产地", "country.name", 100));
						// list.add(addColumn("状态", "stateMark", 100));
						return list;
					}
				});
		tableModelGroupDetail = (JTableListModel) tbGroupDetail.getModel();

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddNewAdd() {
		if (btnAddNewAdd == null) {
			btnAddNewAdd = new JButton();
			btnAddNewAdd.setText("增加新设备");
			btnAddNewAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction
							.checkAgreementAddNewAdd(new Request(CommonVars
									.getCurrUser()));
					List list = FixAssetQuery.getInstance()
							.findComplexNotInAgreement(agreement);
					list = fixAssetAction.addNewAgreementCommInfo(new Request(
							CommonVars.getCurrUser()), agreement, list);
					tableModelImp.addRows(list);
				}
			});
		}
		return btnAddNewAdd;
	}

	private void setState() {
		this.btnSaveAgreement.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnCancel.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState != 0);

		this.tfEmsNo.setEditable(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.tfSancEmsNo.setEditable(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.cbbBeginDate.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.cbbEndDate.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.cbbApproveDate.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		// this.tfTotalMoney.setEnabled(AgreementState.ADDED == agreement
		// .getDeclareState());
		this.tfAddress.setEditable(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.tfLinkMan.setEditable(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.tfLinkTel.setEditable(AgreementState.ADDED == agreement
				.getDeclareState());
		this.cbbCurr.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.cbbLevyKind.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.tfRegisteredMoney.setEnabled(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.tfInvestMoney.setEditable(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.tfWorkingRange.setEditable(AgreementState.ADDED == agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnAddCommInfo
				.setEnabled(AgreementState.PUT_ON_RECORD != agreement
						.getDeclareState());
		this.btnEditCommInfo
				.setEnabled(AgreementState.PUT_ON_RECORD != agreement
						.getDeclareState());
		this.btnDeleteCommInfo
				.setEnabled(AgreementState.PUT_ON_RECORD != agreement
						.getDeclareState());
		this.btnChangingCommInfoNo
				.setEnabled(AgreementState.PUT_ON_RECORD != agreement
						.getDeclareState());
//		this.btnCommInfoNoSort
//				.setEnabled(AgreementState.PUT_ON_RECORD != agreement
//						.getDeclareState());
		this.btnSort
				.setEnabled(AgreementState.PUT_ON_RECORD != agreement
						.getDeclareState());

		this.btnAddDel.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnEditDel.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnDeleteDel.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);

		this.btnAddAdd.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnEditAdd.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnImportAdd.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnDeleteAdd.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		this.btnAddNewAdd.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
		// this.jButton1.setEnabled(AgreementState.ADDED != agreement
		// .getDeclareState());
		this.btnGroup.setEnabled(AgreementState.ADDED != agreement
				.getDeclareState()
				&& btnState == 0);
	}

	private void showImpCommInfo() {
		List lsAdd = fixAssetAction.findImpAgreementCommInfo(new Request(
				CommonVars.getCurrUser()), agreement);
		initImpTable(lsAdd);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
