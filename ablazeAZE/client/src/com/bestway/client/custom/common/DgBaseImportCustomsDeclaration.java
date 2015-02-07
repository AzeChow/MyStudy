/*
 * Created on 2004-8-7
 *
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.contractexe.DgMakeBcsCustomsDeclaration;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomFormattedTextFieldUtils;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.custom.common.report.CustomsPrinter;
import com.bestway.client.custom.common.report.DgCaleReturnImport;
import com.bestway.client.custom.common.report.DgImportInvoiceMergerReportSet;
import com.bestway.client.custom.common.report.DgWExportCommodityEncasementMergerReportSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.action.BCSImpCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCImpCustomsAuthorityAction;
import com.bestway.customs.common.action.ImpCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.TCSCommonCode;
import com.bestway.customs.common.entity.TempTransFactInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.bestway.util.RegexUtil;

/**
 * 进口报关单编辑窗口
 * 
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public abstract class DgBaseImportCustomsDeclaration extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JPanel jContentPane = null;

	protected JToolBar jToolBar = null;

	protected JTabbedPane jTabbedPane = null;

	protected JButton btnAdd = null;

	protected JButton btnEdit = null;

	protected JButton btnDelete = null;

	protected JPanel jPanel = null;

	protected JTextField tfTelephone = null;

	protected JButton btnPrint = null;

	protected JPanel pnBaseInfo = null;

	protected JPanel pnCommInfo = null;

	protected JComboBox cbbImpExpType = null;

	protected JTextField tfPreCustomsDeclarationCode = null;

	protected JTextField tfUnificationCode = null;

	protected JComboBox cbbCustoms = null;

	protected JTextField tfCustomsDeclarationCode = null;

	protected JCalendarComboBox cbbImpExpDate = null;

	protected JCalendarComboBox cbbDeclarationDate = null;

	protected JTextField tfManageCompany = null;

	protected JTextField tfAcceptGoodsCompany = null;

	protected JComboBox cbbTransferMode = null;

	protected JTextField tfConveyance = null;

	private boolean isTaoda = false;

	// protected JComboBox cbbBillOfLading = null;

	protected JComboBox cbbTradeMode = null;

	protected JComboBox cbbLevyKind = null;

	protected JTextField tfLicense = null;

	protected JComboBox cbbCountryOfLoadingOrUnloading = null;

	protected JComboBox cbbDomesticDestinationOrSource = null;

	protected JComboBox cbbPortOfLoadingOrUnloading = null;

	protected JTextField tfAuthorizeFile = null;

	protected JTextField tfContract = null;

	protected JComboBox cbbTransac = null;

	protected JComboBox cbbFreightageType = null;

	protected JComboBox cbbInsuranceType = null;

	protected JComboBox cbbIncidentalExpensesType = null;

	protected JComboBox cbbWrapType = null;

	protected JFormattedTextField tfGrossWeight = null;

	protected JFormattedTextField tfNetWeight = null;

	protected JTextField tfContainerNum = null;

	protected JTextField tfAttachedBill = null;

	protected JButton btnAttachedBill = null;

	protected JComboBox cbbUses = null;

	protected JComboBox cbbCurrency = null;

	protected JTextField tfCreater = null;

	protected JComboBox cbbCustomer = null;

	protected JFormattedTextField tfExchangeRate = null;

	protected JComboBox cbbDeclarationCustoms = null;

	protected JTextField tfMemos = null;

	protected JButton btnMemo = null;

	protected JComboBox cbbPredock = null;

	protected JTextField tfAllContainerNum = null;

	protected JButton btnContainer = null;

	protected JTableListModel customsDeclarationModel = null;

	protected BaseCustomsDeclaration customsDeclaration = null; // @jve:decl-index=0:

	protected JTableListModel commInfoModel = null;

	protected BaseEncAction baseEncAction = null;

	protected CustomBaseAction customBaseAction = null;

	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	protected int dataState = DataState.BROWSE;

	protected JFormattedTextField tfPerTax = null;

	protected JFormattedTextField tfFreightage = null;

	protected JFormattedTextField tfInsurance = null;

	protected JFormattedTextField tfIncidentalExpenses = null;

	protected JFormattedTextField tfCommodityNum = null;

	protected BaseEmsHead emsHead = null; // 电子帐册 // @jve:decl-index=0:

	protected JButton btnSave = null;

	protected JButton btnCancel = null;

	protected DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	protected NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	protected JButton btnTransferCustoms = null;

	protected JButton btnPreCustomsDeclarationCode = null;

	protected JTextField tfEmsNo = null;

	protected JTextField tfCompanyEmsNo = null;

	protected JTextField tfCustomsDeclarationSerialNumber = null;

	protected JButton btnCheckData = null;

	protected JButton btnEffect = null;

	protected JButton btnUnreel = null;

	protected List containers = null; // @jve:decl-index=0:

	protected JLabel jLabel46 = null;
	private JLabel lbCustomsBroker;

	protected JLabel lbCompanyEmsNo = null;

	protected JTextField tfDeclaraCustomsBroker = null;

	protected SystemAction systemAction = null;

	protected JComboBox cbbDeclarationCompany = null;

	protected JLabel jLabel47 = null;

	protected JTextField tfBondedWarehouse = null;

	protected javax.swing.JLabel lbEmsNo = new JLabel();

	protected String sedi = null;

	protected JToolBar jToolBar1 = null;

	protected JButton jButton = null;

	protected JFooterTable jTable = null;

	protected JFooterScrollPane jScrollPane = null;

	private JButton jButton1 = null;

	protected int projectType = ProjectType.BCUS;

	private JButton jButton3 = null;

	private JLabel jLabel43 = null;

	private JLabel lbCommInfoMoney = null;

	private JLabel jLabel = null;

	private ImpCustomsAuthorityAction impCustomsAuthorityAction = null;

	private BCSImpCustomsAuthorityAction bCSImpCustomsAuthorityAction = null;

	private DZSCImpCustomsAuthorityAction dZSCImpCustomsAuthorityAction = null;

	@SuppressWarnings("unused")
	private int firstOpen = 1;// 每次第一次打开时进行判断

	private Boolean isManualPreCode = null; // @jve:decl-index=0:

	private JTextField tfBillOfLading = null;

	private JButton jButton9 = null;

	private JButton btnAuthorizeFile = null;

	private CustomsDeclarationSet other1;

	private String oldMemos;

	public String getOldMemos() {
		return oldMemos;
	}

	public void setOldMemos(String oldMemos) {
		this.oldMemos = oldMemos;
	}

	/**
	 * @return Returns the customsDeclarationModel.
	 */
	public JTableListModel getCustomsDeclarationModel() {
		return customsDeclarationModel;
	}

	/**
	 * @param customsDeclarationModel
	 *            The customsDeclarationModel to set.
	 */
	public void setCustomsDeclarationModel(
			JTableListModel customsDeclarationModel) {
		this.customsDeclarationModel = customsDeclarationModel;
	}

	/**
	 * @return Returns the dataState.
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * @return Returns the customsDeclaration.
	 */
	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}

	/**
	 * @param customsDeclaration
	 *            The customsDeclaration to set.
	 */
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}

	/**
	 * @return Returns the emsHeadH2k.
	 */
	public BaseEmsHead getEmsHead() {
		return emsHead;
	}

	/**
	 * @param emsHeadH2k
	 *            The emsHeadH2k to set.
	 */
	public void setEmsHead(BaseEmsHead emsHead) {
		this.emsHead = emsHead;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBaseImportCustomsDeclaration() {
		super();
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		impCustomsAuthorityAction = (ImpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean("impCustomsAuthorityAction");
		bCSImpCustomsAuthorityAction = (BCSImpCustomsAuthorityAction) CommonVars
				.getApplicationContext()
				.getBean("bCSImpCustomsAuthorityAction");
		dZSCImpCustomsAuthorityAction = (DZSCImpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCImpCustomsAuthorityAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		// 这里是控制焦点的顺序，以方便键盘的输入！
		List<Object> components = new ArrayList<Object>();
		components.add(this.cbbImpExpType);
		components.add(this.cbbCustoms);
		components.add(null);
		components.add(this.tfManageCompany);
		components.add(this.cbbTransferMode);
		components.add(null);
		components.add(this.tfBillOfLading);
		components.add(this.cbbTradeMode);
		components.add(null);
		components.add(this.tfAuthorizeFile);
		components.add(this.cbbTransac);
		components.add(null);
		components.add(this.tfAttachedBill);
		components.add(this.cbbUses);
		components.add(this.cbbDeclarationCompany);
		components.add(this.cbbCurrency);
		components.add(this.cbbCustomer);
		components.add(null);
		components.add(this.tfMemos);
		components.add(this.cbbPredock);
		components.add(this.tfAllContainerNum);
		components.add(this.btnSave);
		this.setComponentFocusList(components);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	protected void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("进口报关单编辑");
		this.setContentPane(getJContentPane());
		this.setSize(736, 540);
		cbbImpExpType.setFocusable(true);
		cbbImpExpType.requestFocus();
		// tfPreCustomsDeclarationCode.setFocusable(false);
		btnPreCustomsDeclarationCode.setFocusable(false);
		// tfUnificationCode.setFocusable(false);//eidt by cjb 2009.11.6
		// 明门ctrl+L问题
		btnTransferCustoms.setFocusable(false);
		tfCreater.setFocusable(false);
		tfCreateDate.setFocusable(false);
		tfCreateDate.getJTextField().setFocusable(false);
		tfCreateDate.getCalendarPanel().setFocusable(false);
	}

	public void setVisible(boolean b) {
		if (b) {
			// 初始化表头资料
			if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
				if (customsDeclaration == null
						&& customsDeclarationModel.getCurrentRow() != null) {
					customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
							.getCurrentRow();
				}
			}
			initUIComponents();
			this.showPrimalData();
			this.containers = this.baseEncAction
					.findContainerByCustomsDeclaration(
							new Request(CommonVars.getCurrUser()),
							this.customsDeclaration);
			setState();
			addListeners();
			if (commInfoModel == null) {
				initTable();
			}
			// setStateforEnvelop();
		}
		super.setVisible(b);
	}

	public void show(BaseCustomsDeclaration customsDeclaration) {
		// this.customsDeclaration =
		// baseEncAction.findCustomsDeclarationByCustomsDeclarationCode(new
		// Request(CommonVars
		// .getCurrUser()), customsDeclarationCode, true);
		// this.emsHead = new EmsHeadH2k();
		// emsHead.setEmsNo(emsNo);
		this.customsDeclaration = customsDeclaration;
		this.emsHead = new EmsHeadH2k();
		this.emsHead.setEmsNo(customsDeclaration.getEmsHeadH2k());
		initUIComponents();
		this.showPrimalData();
		this.containers = this.baseEncAction.findContainerByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), this.customsDeclaration);
		setState();
		addListeners();
		if (commInfoModel == null) {
			initTable();
		}
		this.getBtnUnreel().setEnabled(false);
		this.getBtnCheckData().setEnabled(false);
		this.getBtnPrint().setEnabled(true);
		this.getJButton4().setEnabled(true);
		super.setVisible(true);
	}

	private void addListeners() {
		getCbbImpExpType().addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				setState();
				if (cbbImpExpType.getSelectedItem() != null) {
					Integer type = Integer
							.valueOf(((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode());
					if (cbbImpExpType.getSelectedItem() != null
							&& ((ItemProperty) cbbImpExpType.getSelectedItem())
									.getCode().equals("1")) {
						if (tfMemos.getText() == null) {
							tfMemos.setText("转自");
						} else if (!tfMemos.getText().contains("转自")) {
							tfMemos.setText("转自" + tfMemos.getText());
						}
					} else {
						if (tfMemos.getText().contains("转自")) {
							int log = tfMemos.getText().trim().indexOf("转自");
							if (log >= 0) {
								String memos = tfMemos.getText().substring(
										log + 2,
										tfMemos.getText().trim().length());
								tfMemos.setText(memos);
							}
						}
					}
					if (type.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) {
						tfCustomsEnvelopBillNo
								.setText(customsDeclaration == null ? ""
										: customsDeclaration
												.getCustomsEnvelopBillNo());
					} else {
						tfCustomsEnvelopBillNo.setText("");
					}
					if (type.equals((customsDeclaration == null || customsDeclaration
							.getImpExpType() == null) ? Integer.valueOf(-1)
							: customsDeclaration.getImpExpType())) {
						return;
					}
					CustomsDeclarationSet other1 = systemAction.findCustomsSet(
							new Request(CommonVars.getCurrUser(), true),
							customsDeclaration == null ? null : type);
					System.out.println("other1=" + other1);
					// 后加来自系统参数设置
					if (other1 != null) {
						customsDeclaration.setDeclarationCustoms(other1
								.getDeclarationCustoms());
						cbbDeclarationCustoms
								.setSelectedItem(customsDeclaration
										.getDeclarationCustoms());
						customsDeclaration.setLevyKind(other1
								.getCustomlevyKind());
						cbbLevyKind.setSelectedItem(customsDeclaration
								.getLevyKind());
						customsDeclaration.setBillOfLading(other1
								.getBillOfLading());
						tfBillOfLading.setText(customsDeclaration
								.getBillOfLading());
						customsDeclaration.setTradeMode(other1.getTradeMode());
						cbbTradeMode.setSelectedItem(customsDeclaration
								.getTradeMode());
						customsDeclaration.setConveyance(other1.getConveyance());
						tfConveyance.setText(customsDeclaration.getConveyance());

						customsDeclaration.setTransac(other1.getTransac());
						cbbTransac.setSelectedItem(customsDeclaration
								.getTransac());
						customsDeclaration
								.setCountryOfLoadingOrUnloading(other1
										.getCoun());
						cbbCountryOfLoadingOrUnloading
								.setSelectedItem(customsDeclaration
										.getCountryOfLoadingOrUnloading());
						customsDeclaration.setPredock(other1.getPredock());
						cbbPredock.setSelectedItem(customsDeclaration
								.getPredock());
						customsDeclaration.setPortOfLoadingOrUnloading(other1
								.getPort());
						cbbPortOfLoadingOrUnloading
								.setSelectedItem(customsDeclaration
										.getPortOfLoadingOrUnloading());
						customsDeclaration.setWrapType(other1.getWrapType());
						cbbWrapType.setSelectedItem(customsDeclaration
								.getWrapType());
						customsDeclaration.setCustoms(other1.getCustoms());
						cbbCustoms.setSelectedItem(customsDeclaration
								.getCustoms());
						customsDeclaration.setTransferMode(other1
								.getTransferMode());
						cbbTransferMode.setSelectedItem(customsDeclaration
								.getTransferMode());
						customsDeclaration
								.setDomesticDestinationOrSource(other1
										.getDistrict());
						customsDeclaration.setUses(other1.getUses());
						cbbUses.setSelectedItem(customsDeclaration.getUses());
						cbbDomesticDestinationOrSource
								.setSelectedItem(customsDeclaration
										.getDomesticDestinationOrSource());

						customsDeclaration.setCurrency(other1.getCurr());
						// 后加来自系统参数设置
						CompanyOther other = CommonVars.getOther();
						if (other != null) {
							if (customsDeclaration.getCurrency() == null) {
								customsDeclaration.setCurrency(other
										.getCommonCurr());
							}
						}
						cbbCurrency.setSelectedItem(customsDeclaration
								.getCurrency());

						firstOpen = 3;
					}
				} else {
					tfCustomsEnvelopBillNo.setText("");
				}
			}
		});
	}

	protected JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	protected JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getBtnSplit());
			jToolBar.add(getBtnEffect());
			jToolBar.add(getBtnUnreel());
			jToolBar.add(getBtnCheckData());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getJButton4());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	protected JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("基本信息", null, getPnBaseInfo(), null);
			jTabbedPane.addTab("商品信息", null, getPnCommInfo(), null);
			jTabbedPane.addTab("收发信息", null, getPnTcs(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							DgBaseImportCustomsDeclaration.this
									.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							if (jTabbedPane.getSelectedIndex() == 0) {
								if (jPanel != null)
									jPanel.setLayout(null);
								if (jLabel != null)
									jLabel.setVisible(true);
								if (jComboBox != null)
									jComboBox.setVisible(true);
								if (tfTelephone != null)
									tfTelephone.setVisible(true);
								if (btnPrint != null)
									btnPrint.setBounds(260, 4, 62, 21);
								if (jButton4 != null)
									jButton4.setBounds(new java.awt.Rectangle(
											322, 4, 72, 21));
								// String conveyance = "";
								// if (tfConveyance.getText().length() > 0) {
								// conveyance =
								// tfConveyance.getText().substring(0,
								// 1);
								// }
								if (customsDeclarationModel != null
										&& customsDeclarationModel
												.getCurrentRow() != null
										&& dataState != DataState.ADD) {
									customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
											.getCurrentRow();

								}
								// showData();
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								boolean isEffective = false;
								if (customsDeclaration != null) {
									isEffective = customsDeclaration
											.getEffective() == null ? false
											: customsDeclaration.getEffective()
													.booleanValue();

								}
								if (!isEffective
										&& dataState != DataState.BROWSE) {// 非生效状态
									if (!saveCustom()) { // 保存有错误提示
										jTabbedPane.setSelectedIndex(0);
										DgBaseImportCustomsDeclaration.this
												.setCursor(new Cursor(
														Cursor.DEFAULT_CURSOR));

										return;
									}
								}
								if (jPanel != null)
									jPanel.setLayout(new java.awt.FlowLayout());

								if (jLabel != null)
									jLabel.setVisible(false);
								if (jComboBox != null)
									jComboBox.setVisible(false);
								if (tfTelephone != null)
									tfTelephone.setVisible(false);
								initTable();
								showCommInfoMoney();
								btnAdd.requestFocus();
							}
							setState();
							DgBaseImportCustomsDeclaration.this
									.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return jTabbedPane;
	}

	public JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(43, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addCustomsDeclarationData();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 新增报关单，或者报关单商品信息
	 * 
	 */
	protected void addCustomsDeclarationData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			dataState = DataState.ADD;
			showPrimalData();
			setState();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			if (customsDeclaration == null) {
				return;
			}
			List list = CommonQuery.getInstance()
					.getTempCustomsDeclarationCommInfo(
							EncCommon.isMaterial(customsDeclaration
									.getImpExpType()), customsDeclaration);
			if (list == null) {
				return;
			}
			List listParameter = systemAction.findCompanyOther(new Request(
					CommonVars.getCurrUser(), true));
			if (listParameter.size() > 0 && listParameter.get(0) != null) {
				CompanyOther companyOther = (CompanyOther) listParameter.get(0);
				Boolean isAllowBGDDetailExceed20 = companyOther
						.getIsAllowBGDDetailExceed20();
				if (isAllowBGDDetailExceed20 == null
						|| !isAllowBGDDetailExceed20) {
					int getN = list.size();
					int currN = commInfoModel.getRowCount();
					if ((getN + currN) > 20) {
						JOptionPane.showMessageDialog(
								DgBaseImportCustomsDeclaration.this,
								"当前商品信息还可新增" + String.valueOf(20 - currN)
										+ "项!", "提示", 2);
						return;
					}
				}
			}
			baseEncAction.saveCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), true, list,
					customsDeclaration);
			getPiceAndWeight();// 更新主表
			initTable();
			// commInfoModel.setTableEndSelectRow();
			int index = commInfoModel.getList().size() - list.size();
			if (index >= 0) {
				commInfoModel.setSelectRow(index);
			}
			editCustomsDeclarationData1();
			showCommInfoMoney();
			getAttachedBill();// 更新表头的随附单据
		}
	}

	/**
	 * 更新表头的随附单据
	 */
	private void getAttachedBill() {
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		if (!(other.getIsCustomAutoAttachedBill() == null ? false : other
				.getIsCustomAutoAttachedBill())) {
			return;
		}
		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.getAttachedBill(new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		if (this.customsDeclaration.getAttachedBill() != null) {
			this.tfAttachedBill.setText(this.customsDeclaration
					.getAttachedBill());
		} else {
			this.tfAttachedBill.setText("");
		}
		// tfAttachedBill.setText(customsDeclaration.getAttachedBill());
		customsDeclarationModel.updateRow(customsDeclaration);
	}

	protected JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(43, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editCustomsDeclarationData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 修改新增报关单，或者报关单商品信息
	 * 
	 */
	protected void editCustomsDeclarationData() {
		if (jTabbedPane.getSelectedIndex() == 0
				|| jTabbedPane.getSelectedIndex() == 2) {
			if (customsDeclaration == null) {
				return;
			}
			dataState = DataState.EDIT;
			setState();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			if (customsDeclaration == null) {
				return;
			}
			if (commInfoModel == null) {
				return;
			}
			if (commInfoModel.getList().size() < 1) {
				return;
			}
			if (commInfoModel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(
						DgBaseImportCustomsDeclaration.this, "请选择你要修改的资料",
						"提示", 0);
				return;
			}
			DgBaseImportCustomsDeclarationCommInfo dg = this
					.getImportCustomsDeclarationCommInfo();
			dg.setTableModel(commInfoModel);
			dg.setEffective(customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue());
			dg.setBaseEncAction(this.baseEncAction);
			dg.setProjectType(projectType);
			dg.setDgBase(this);
			if (other1 == null) {
				Integer type = Integer.valueOf(((ItemProperty) cbbImpExpType
						.getSelectedItem()).getCode());
				other1 = systemAction.findCustomsSet(
						new Request(CommonVars.getCurrUser(), true),
						customsDeclaration == null ? null : type);
			}
			dg.other1 = other1;
			dg.setVisible(true);
			infoforint();// 数据取整
			getPiceAndWeight();// 是否自动带出净毛重
			initTable();
			showCommInfoMoney();
		}
	}

	/**
	 * 新增报关单，或者报关单商品信息
	 * 
	 */
	protected void editCustomsDeclarationData1() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			if (customsDeclaration == null) {
				return;
			}
			dataState = DataState.EDIT;
			showPrimalData();
			setState();
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			if (customsDeclaration == null) {
				return;
			}
			if (commInfoModel == null) {
				return;
			}
			if (commInfoModel.getList().size() < 1) {
				return;
			}
			if (commInfoModel.getCurrentRow() == null) {
				return;
			}
			DgBaseImportCustomsDeclarationCommInfo dg = this
					.getImportCustomsDeclarationCommInfo();
			dg.setTableModel(commInfoModel);
			dg.setEffective(customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue());
			dg.setBaseEncAction(this.baseEncAction);
			dg.setProjectType(projectType);
			dg.setDgBase(this);
			if (other1 == null) {
				Integer type = Integer.valueOf(((ItemProperty) cbbImpExpType
						.getSelectedItem()).getCode());
				other1 = systemAction.findCustomsSet(
						new Request(CommonVars.getCurrUser(), true),
						customsDeclaration == null ? null : type);
			}
			dg.other1 = other1;
			dg.setVisible(true);
			infoforint();// 数据取整
			getPiceAndWeight();// 更新主表
			initTable();
			showCommInfoMoney();
		}
	}

	protected DgBaseImportCustomsDeclarationCommInfo getImportCustomsDeclarationCommInfo() {
		return new DgBaseImportCustomsDeclarationCommInfo();
	}

	protected JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(43, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.deleteCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction
								.deleteCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction
								.deleteCommodity(new Request(CommonVars
										.getCurrUser()));
					}
					deleteCustomsDeclarationData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除新增报关单，或者报关单商品信息
	 * 
	 */
	protected void deleteCustomsDeclarationData() {
		if (JOptionPane.showConfirmDialog(DgBaseImportCustomsDeclaration.this,
				"确定要删除吗？", "确认", 0) != 0) {
			return;
		}
		if (customsDeclaration == null) {
			return;
		}
		/*
		 * if (customsDeclaration.getBillListId() != null &&
		 * !"".equals(customsDeclaration.getBillListId())) {
		 * JOptionPane.showMessageDialog( DgBaseImportCustomsDeclaration.this,
		 * "由于此报关单是根据报关清单自动生成的，所以不能删除商品明细", "提示",
		 * JOptionPane.INFORMATION_MESSAGE); return; }
		 */
		if (commInfoModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		// if
		// (JOptionPane.showConfirmDialog(DgBaseImportCustomsDeclaration.this,
		// "是否确定删除数据!!!", "提示", 0) != 0) {
		// return;
		// }
		baseEncAction.deleteCustomsDeclarationCommInfo(
				new Request(CommonVars.getCurrUser()),
				commInfoModel.getCurrentRows());
		getPiceAndWeight();// 更新主表
		initTable();
		showCommInfoMoney();
		getAttachedBill();
	}

	protected JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(200, 30));
			jLabel.setBounds(1, 9, 40, 13);
			jLabel.setText("报关员");
			jPanel.add(jLabel, null);
			jPanel.add(getTfTelephone(), null);
			jPanel.add(getJComboBox3(), null);
		}
		return jPanel;
	}

	protected JTextField getTfTelephone() {
		if (tfTelephone == null) {
			tfTelephone = new JTextField();
			tfTelephone.setBounds(107, 3, 91, 25);
		}
		return tfTelephone;
	}

	/**
	 * 获取集装箱号（打印使用）
	 * 
	 * @return
	 */
	protected String getContia() {
		String contain = "";
		List list = baseEncAction.findContainerByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), customsDeclaration);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String xs = ((BaseCustomsDeclarationContainer) list.get(i))
						.getContainerCode();
				if (!xs.equals(customsDeclaration.getContainerNum().substring(
						0, 11))) {
					contain = contain + xs + " ";
				}
			}
		}
		if (!contain.equals("")) {
			contain = "集装箱:" + contain;
		}
		return contain;
	}

	protected JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("套打");
			btnPrint.setPreferredSize(new Dimension(43, 30));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isTaoda = true;
					Component comp = (Component) e.getSource();
					getJPopupMenu().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * 打印报表
	 * 
	 */

	protected JPanel getPnBaseInfo() {
		if (pnBaseInfo == null) {
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(515, 139, 48, 18));
			jLabel51.setText("工具编码");
			jLabel472 = new JLabel();
			jLabel472.setBounds(new Rectangle(235, 411, 75, 20));
			jLabel472.setText("关联报关单号");
			jLabel471 = new JLabel();
			jLabel471.setBounds(new Rectangle(23, 411, 62, 20));
			jLabel471.setText("关联手册号");
			jLabel502 = new JLabel();
			jLabel502.setBounds(new Rectangle(485, 217, 54, 20));
			jLabel502.setText("保费币制");
			jLabel501 = new JLabel();
			jLabel501.setBounds(new Rectangle(272, 241, 55, 19));
			jLabel501.setText("杂费币制");
			jLabel50 = new JLabel();
			jLabel50.setBounds(new Rectangle(22, 217, 50, 20));
			jLabel50.setText("运费币制");
			jLabel49 = new JLabel();
			jLabel49.setBounds(new Rectangle(518, 313, 53, 19));
			jLabel49.setText("录入时间");
			jLabel44 = new JLabel();
			jLabel44.setVisible(false);// 插件用到
			jLabel44.setBounds(new Rectangle(510, 391, 63, 18));
			jLabel44.setForeground(java.awt.Color.blue);
			jLabel44.setText("报检预录号");
			jLabel1 = new JLabel();
			jLabel1.setVisible(true);// 插件用到
			jLabel1.setBounds(new Rectangle(382, 386, 37, 22));
			jLabel1.setText("发票号");
			jLabel48 = new JLabel();
			jLabel48.setBounds(new Rectangle(167, 386, 72, 22));
			jLabel48.setText("结转申请单号");
			jLabel47 = new JLabel();
			lbCustomsBroker = new JLabel();
			lbCompanyEmsNo = new JLabel();
			javax.swing.JLabel jLabel45 = new JLabel();

			javax.swing.JLabel jLabel42 = new JLabel();
			javax.swing.JLabel jLabel41 = new JLabel();
			javax.swing.JLabel jLabel40 = new JLabel();
			javax.swing.JLabel jLabel39 = new JLabel();
			javax.swing.JLabel jLabel38 = new JLabel();
			javax.swing.JLabel jLabel37 = new JLabel();
			javax.swing.JLabel jLabel36 = new JLabel();
			javax.swing.JLabel jLabel35 = new JLabel();
			javax.swing.JLabel jLabel34 = new JLabel();
			javax.swing.JLabel jLabel33 = new JLabel();
			javax.swing.JLabel jLabel32 = new JLabel();
			javax.swing.JLabel jLabel31 = new JLabel();
			javax.swing.JLabel jLabel30 = new JLabel();
			javax.swing.JLabel jLabel29 = new JLabel();
			javax.swing.JLabel jLabel28 = new JLabel();
			javax.swing.JLabel jLabel27 = new JLabel();
			javax.swing.JLabel jLabel26 = new JLabel();
			javax.swing.JLabel jLabel25 = new JLabel();
			javax.swing.JLabel jLabel24 = new JLabel();
			javax.swing.JLabel jLabel23 = new JLabel();
			javax.swing.JLabel jLabel22 = new JLabel();
			javax.swing.JLabel jLabel21 = new JLabel();
			javax.swing.JLabel jLabel20 = new JLabel();
			javax.swing.JLabel jLabel19 = new JLabel();
			javax.swing.JLabel jLabel18 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel16 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel7 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			pnBaseInfo = new JPanel();
			pnBaseInfo.setLayout(null);
			jLabel2.setBounds(23, 36, 49, 19);
			jLabel2.setText("进口类型");
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel3.setBounds(184, 37, 49, 19);
			jLabel3.setText("预录入号");
			jLabel4.setBounds(433, 37, 48, 19);
			jLabel4.setText("统一编号");
			jLabel5.setBounds(23, 62, 49, 19);
			jLabel5.setText("进口口岸");
			jLabel6.setBounds(184, 62, 49, 19);
			jLabel6.setText("报关单号");
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setBounds(380, 62, 50, 19);
			jLabel7.setText("进口日期");
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel8.setBounds(519, 62, 49, 19);
			jLabel8.setText("申报日期");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(23, 88, 49, 19);
			jLabel9.setText("经营单位");
			jLabel10.setBounds(341, 87, 50, 19);
			jLabel10.setText("收货单位");
			jLabel11.setBounds(23, 114, 49, 19);
			jLabel11.setText("运输方式");
			jLabel12.setBounds(184, 115, 49, 19);
			jLabel12.setText("运输工具");
			jLabel13.setBounds(494, 115, 50, 19);
			jLabel13.setText("提运单号");
			jLabel14.setBounds(23, 140, 49, 19);
			jLabel14.setText("贸易方式");
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel15.setBounds(184, 141, 49, 19);
			jLabel15.setText("征免性质");
			jLabel16.setBounds(346, 140, 59, 19);
			jLabel16.setText("征税比例%");
			jLabel17.setBounds(23, 166, 49, 19);
			jLabel17.setText("许可证号");
			jLabel18.setBounds(245, 167, 71, 19);
			jLabel18.setText("起运国(地区)");
			jLabel19.setBounds(459, 167, 62, 19);
			jLabel19.setText("境内目的地");
			jLabel20.setBounds(23, 192, 49, 19);
			jLabel20.setText("装货港");
			jLabel21.setBounds(184, 193, 49, 19);
			jLabel21.setText("批准文号");
			jLabel21.setForeground(java.awt.Color.black);
			jLabel22.setBounds(336, 193, 61, 19);
			jLabel22.setText("合同协议号");
			jLabel23.setBounds(499, 193, 50, 19);
			jLabel23.setText("成交方式");
			jLabel24.setBounds(184, 217, 49, 20);
			jLabel24.setText("运费");
			jLabel25.setBounds(22, 241, 50, 19);
			jLabel25.setText("保费");
			jLabel26.setBounds(434, 241, 35, 19);
			jLabel26.setText("杂费");
			jLabel27.setBounds(22, 266, 50, 19);
			jLabel27.setText("件数");
			jLabel27.setForeground(java.awt.Color.blue);
			jLabel28.setBounds(184, 266, 49, 19);
			jLabel28.setText("包装种类");
			jLabel29.setBounds(385, 266, 28, 19);
			jLabel29.setText("毛重");
			jLabel29.setForeground(java.awt.Color.blue);
			jLabel30.setBounds(519, 266, 28, 19);
			jLabel30.setText("净重");
			jLabel30.setForeground(java.awt.Color.blue);
			jLabel31.setBounds(22, 289, 50, 19);
			jLabel31.setText("集装箱号");
			jLabel31.setForeground(java.awt.Color.blue);
			jLabel32.setBounds(248, 289, 49, 19);
			jLabel32.setText("随附单据");
			jLabel33.setBounds(469, 289, 29, 19);
			jLabel33.setText("用途");
			jLabel34.setBounds(22, 313, 50, 19);
			jLabel34.setText("申报单位");
			jLabel35.setBounds(280, 313, 25, 19);
			jLabel35.setText("币制");
			jLabel35.setForeground(java.awt.Color.blue);
			jLabel36.setBounds(407, 313, 38, 19);
			jLabel36.setText("录入员");
			jLabel37.setBounds(22, 338, 50, 19);
			jLabel37.setText("客户名称");
			jLabel37.setForeground(java.awt.Color.blue);
			jLabel38.setBounds(281, 340, 25, 19);
			jLabel38.setText("汇率");
			jLabel39.setBounds(444, 340, 51, 19);
			jLabel39.setText("报送海关");
			jLabel40.setBounds(22, 362, 50, 19);
			jLabel40.setText("备注");
			jLabel41.setBounds(282, 362, 25, 19);
			jLabel41.setText("码头");
			jLabel42.setBounds(445, 362, 72, 19);
			jLabel42.setText("所有集装箱号");
			lbEmsNo.setBounds(185, 10, 49, 21);
			lbEmsNo.setText("帐册编号");
			lbCompanyEmsNo.setBounds(323, 10, 63, 21);
			lbCompanyEmsNo.setText("帐册流水号");
			jLabel45.setBounds(484, 11, 75, 19);
			jLabel45.setText("报关单流水号");
			lbCustomsBroker.setBounds(23, 10, 49, 18);
			lbCustomsBroker.setText("报关行");
			jLabel47.setBounds(22, 386, 50, 21);
			jLabel47.setText("保税仓库");
			jLabel48.setForeground(java.awt.Color.blue);
			pnBaseInfo.add(jLabel2, null);
			pnBaseInfo.add(getCbbImpExpType(), null);
			pnBaseInfo.add(jLabel3, null);
			pnBaseInfo.add(getTfPreCustomsDeclarationCode(), null);
			pnBaseInfo.add(jLabel4, null);
			pnBaseInfo.add(getTfUnificationCode(), null);
			pnBaseInfo.add(jLabel5, null);
			pnBaseInfo.add(getCbbCustoms(), null);
			pnBaseInfo.add(jLabel6, null);
			pnBaseInfo.add(getTfCustomsDeclarationCode(), null);
			pnBaseInfo.add(jLabel7, null);
			pnBaseInfo.add(getCbbImpExpDate(), null);
			pnBaseInfo.add(jLabel8, null);
			pnBaseInfo.add(getCbbDeclarationDate(), null);
			pnBaseInfo.add(jLabel9, null);
			pnBaseInfo.add(getTfManageCompany(), null);
			pnBaseInfo.add(jLabel10, null);
			pnBaseInfo.add(getTfAcceptGoodsCompany(), null);
			pnBaseInfo.add(jLabel11, null);
			pnBaseInfo.add(getCbbTransferMode(), null);
			pnBaseInfo.add(jLabel12, null);
			pnBaseInfo.add(getTfConveyance(), null);
			pnBaseInfo.add(jLabel13, null);
			// pnBaseInfo.add(getCbbBillOfLading(), null);
			pnBaseInfo.add(jLabel14, null);
			pnBaseInfo.add(getCbbTradeMode(), null);
			pnBaseInfo.add(jLabel15, null);
			pnBaseInfo.add(getCbbLevyKind(), null);
			pnBaseInfo.add(jLabel16, null);
			pnBaseInfo.add(jLabel17, null);
			pnBaseInfo.add(getTfLicense(), null);
			pnBaseInfo.add(jLabel18, null);
			pnBaseInfo.add(getCbbCountryOfLoadingOrUnloading(), null);
			pnBaseInfo.add(jLabel19, null);
			pnBaseInfo.add(getCbbDomesticDestinationOrSource(), null);
			pnBaseInfo.add(jLabel20, null);
			pnBaseInfo.add(getCbbPortOfLoadingOrUnloading(), null);
			pnBaseInfo.add(jLabel21, null);
			pnBaseInfo.add(getTfAuthorizeFile(), null);
			pnBaseInfo.add(jLabel22, null);
			pnBaseInfo.add(getTfContract(), null);
			pnBaseInfo.add(jLabel23, null);
			pnBaseInfo.add(getCbbTransac(), null);
			pnBaseInfo.add(jLabel24, null);
			pnBaseInfo.add(getCbbFreightageType(), null);
			pnBaseInfo.add(jLabel25, null);
			pnBaseInfo.add(getCbbInsuranceType(), null);
			pnBaseInfo.add(jLabel26, null);
			pnBaseInfo.add(getCbbIncidentalExpensesType(), null);
			pnBaseInfo.add(jLabel27, null);
			pnBaseInfo.add(jLabel28, null);
			pnBaseInfo.add(getCbbWrapType(), null);
			pnBaseInfo.add(jLabel29, null);
			pnBaseInfo.add(getTfGrossWeight(), null);
			pnBaseInfo.add(jLabel30, null);
			pnBaseInfo.add(getTfNetWeight(), null);
			pnBaseInfo.add(jLabel31, null);
			pnBaseInfo.add(getTfContainerNum(), null);
			pnBaseInfo.add(jLabel32, null);
			pnBaseInfo.add(getTfAttachedBill(), null);
			pnBaseInfo.add(getBtnAttachedBill(), null);
			pnBaseInfo.add(jLabel33, null);
			pnBaseInfo.add(getCbbUses(), null);
			pnBaseInfo.add(jLabel34, null);
			pnBaseInfo.add(jLabel35, null);
			pnBaseInfo.add(getCbbCurrency(), null);
			pnBaseInfo.add(jLabel36, null);
			pnBaseInfo.add(getTfCreater(), null);
			pnBaseInfo.add(jLabel37, null);
			pnBaseInfo.add(getCbbCustomer(), null);
			pnBaseInfo.add(jLabel38, null);
			pnBaseInfo.add(getTfExchangeRate(), null);
			pnBaseInfo.add(jLabel39, null);
			pnBaseInfo.add(getCbbDeclarationCustoms(), null);
			pnBaseInfo.add(jLabel40, null);
			pnBaseInfo.add(getTfMemos(), null);
			pnBaseInfo.add(getBtnMemo(), null);
			pnBaseInfo.add(jLabel41, null);
			pnBaseInfo.add(getCbbPredock(), null);
			pnBaseInfo.add(jLabel42, null);
			pnBaseInfo.add(getTfAllContainerNum(), null);
			pnBaseInfo.add(getBtnContainer(), null);
			pnBaseInfo.add(getTfPerTax(), null);
			pnBaseInfo.add(getTfFreightage(), null);
			pnBaseInfo.add(getTfInsurance(), null);
			pnBaseInfo.add(getTfIncidentalExpenses(), null);
			pnBaseInfo.add(getTfCommodityNum(), null);
			pnBaseInfo.add(getBtnTransferCustoms(), null);
			pnBaseInfo.add(getBtnPreCustomsDeclarationCode(), null);
			pnBaseInfo.add(lbEmsNo, null);
			pnBaseInfo.add(lbCompanyEmsNo, null);
			pnBaseInfo.add(getTfEmsNo(), null);
			pnBaseInfo.add(getTfCompanyEmsNo(), null);
			pnBaseInfo.add(jLabel45, null);
			pnBaseInfo.add(getTfCustomsDeclarationSerialNumber(), null);
			pnBaseInfo.add(lbCustomsBroker, null);
			pnBaseInfo.add(getJComboBox(), null);
			pnBaseInfo.add(getJComboBox2(), null);
			pnBaseInfo.add(jLabel47, null);
			pnBaseInfo.add(getTfBondedWarehouse(), null);
			pnBaseInfo.add(jLabel48, null);
			pnBaseInfo.add(getTfCustomsEnvelopBillNo(), null);
			pnBaseInfo.add(getBtnFptAppNo(), null);
			pnBaseInfo.add(getTfBillOfLading(), null);
			pnBaseInfo.add(getJButton9(), null);
			pnBaseInfo.add(getBtnAuthorizeFile(), null);
			pnBaseInfo.add(jLabel1, null);
			pnBaseInfo.add(getTfInvoiceCode(), null);
			pnBaseInfo.add(jLabel44, null);
			pnBaseInfo.add(getTfInspect(), null);
			pnBaseInfo.add(jLabel49, null);
			pnBaseInfo.add(getTfCreateDate(), null);
			pnBaseInfo.add(jLabel50, null);
			pnBaseInfo.add(getCbbFeeCurr(), null);
			pnBaseInfo.add(jLabel501, null);
			pnBaseInfo.add(getCbbOtherCurr(), null);
			pnBaseInfo.add(jLabel502, null);
			pnBaseInfo.add(getCbbInsurCurr(), null);
			pnBaseInfo.add(jLabel471, null);
			pnBaseInfo.add(jLabel472, null);
			pnBaseInfo.add(getTfConnectNo(), null);
			pnBaseInfo.add(getTfConnectDeclarationNo(), null);
			pnBaseInfo.add(jLabel51, null);
			pnBaseInfo.add(getTfdomesticConveyanceCode(), null);
			pnBaseInfo.add(getLabel());
			pnBaseInfo.add(getTfVoyageNo());
		}
		return pnBaseInfo;
	}

	private JTextField getTfBillOfLading() {
		if (tfBillOfLading == null) {
			tfBillOfLading = new JTextField();
			tfBillOfLading.setBounds(new Rectangle(544, 116, 115, 20));
		}
		return tfBillOfLading;
	}

	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setBounds(new Rectangle(660, 115, 25, 18));
			jButton9.setText("...");
			jButton9.addKeyListener(new FocusActionListner(getCbbTradeMode()));
			jButton9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addBillOfLading();
				}

			});
		}
		return jButton9;
	}

	private JButton getBtnAuthorizeFile() {
		if (btnAuthorizeFile == null) {
			btnAuthorizeFile = new JButton();
			btnAuthorizeFile.setText("...");
			btnAuthorizeFile.addKeyListener(new FocusActionListner(
					getCbbTransac()));
			btnAuthorizeFile.setBounds(313, 193, 21, 21);
			btnAuthorizeFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							addFecavBill();
						}
					});
		}
		return btnAuthorizeFile;
	}

	private void addFecavBill() {
		FecavBill obj = (FecavBill) CommonQueryPage.getInstance()
				.getFecavBillNotCustomsDeclaration();
		if (obj == null) {
			return;
		} else {
			this.tfAuthorizeFile.setText(obj.getCode());
		}

	}

	private void addBillOfLading() {
		MotorCode obj = (MotorCode) CommonQueryPage.getInstance()
				.getMotorCode();
		if (obj == null) {
			return;
		} else {
			this.tfBillOfLading.setText(obj.getHomeCard());
		}
	}

	protected JPanel getPnCommInfo() {
		if (pnCommInfo == null) {
			pnCommInfo = new JPanel();
			pnCommInfo.setLayout(new BorderLayout());
			pnCommInfo.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			pnCommInfo.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return pnCommInfo;
	}

	class ItemListenerAdapter implements java.awt.event.ItemListener {

		public void itemStateChanged(ItemEvent e) {
			// if (e.getStateChange() == ItemEvent.SELECTED) {
			// return;
			// }
			final JComboBox comboBox = ((JComboBox) e.getSource());
			if (comboBox.isEditable()) {
				if (comboBox.getSelectedItem() == null) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							comboBox.requestFocus();
						}
					});
				}
			}
		}
	}

	protected JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(74, 35, 108, 20);
			// cbbImpExpType.addFocusListener(new java.awt.event.FocusAdapter()
			// {
			// public void focusLost(java.awt.event.FocusEvent e) {
			// final JComboBox comboBox = ((JComboBox) e.getSource());
			// SwingUtilities.invokeLater(new Runnable() {
			// public void run() {
			// if (comboBox.isEditable()) {
			// System.out.println("comboBox.getSelectedItem()"+comboBox.getSelectedItem());
			// if (comboBox.getSelectedItem() == null) {
			// comboBox.requestFocus();
			// }
			//
			// }
			// }
			// });
			// }
			// });
		}
		return cbbImpExpType;
	}

	// protected JComboBox getCbbImpExpType() {
	// if (cbbImpExpType == null) {
	// cbbImpExpType = new JComboBox();
	// cbbImpExpType.setBounds(73, 45, 108, 20);
	// }
	// return cbbImpExpType;
	// }

	/**
	 * 当选定的报关类型在报关单参数设置中没有预设值时，返回初值;firstOpen是用来控制cbbImpExpType的初始化
	 */
	// private void showPrimaryData(Integer type) {
	// if (firstOpen == 1 || firstOpen == 2) {
	// firstOpen++;
	//
	// } else if (firstOpen != 1 && firstOpen != 2) {
	// cbbDeclarationCustoms.setSelectedItem(null);
	// cbbLevyKind.setSelectedItem(null);
	// // cbbBillOfLading.setSelectedItem(null);
	// cbbTradeMode.setSelectedItem(null);
	// tfConveyance.setText("");
	// cbbTransac.setSelectedItem(null);
	// cbbCountryOfLoadingOrUnloading.setSelectedItem(null);
	// cbbPredock.setSelectedItem(null);
	// cbbPortOfLoadingOrUnloading.setSelectedItem(null);
	// cbbWrapType.setSelectedItem(null);
	// cbbCustoms.setSelectedItem(null);
	// cbbTransferMode.setSelectedItem(null);
	// cbbUses.setSelectedItem(null);
	// cbbDomesticDestinationOrSource.setSelectedItem(null);
	// }
	// }
	// private void setTradeMode() {
	// if (cbbImpExpType.getSelectedItem() != null
	// && Integer.valueOf(
	// ((ItemProperty) cbbImpExpType.getSelectedItem())
	// .getCode()).equals(
	// ImpExpType.TRANSFER_FACTORY_IMPORT)) {
	// List list = customBaseAction.findTrade("name", "来料深加工");
	// if (list != null && list.size() > 0) {
	// Trade trade = (Trade) list.get(0);
	// cbbTradeMode.setSelectedItem(trade);
	// }
	// }
	// }
	// private void setStateforEnvelop() {
	// boolean isEdit = cbbImpExpType.getSelectedItem() != null
	// && Integer.valueOf(
	// ((ItemProperty) cbbImpExpType.getSelectedItem())
	// .getCode()).equals(
	// ImpExpType.TRANSFER_FACTORY_IMPORT);
	// tfCustomsEnvelopBillNo.setEditable(isEdit);
	// btnCustomsEnvelopBillNo.setEnabled(isEdit);
	// }
	// **** protected JComboBox getCbbImpExpType() {
	// if (cbbImpExpType == null) {
	// cbbImpExpType = new JComboBox();
	// cbbImpExpType.setBounds(73, 45, 108, 20);
	// cbbImpExpType.addItemListener(new ItemListener() {
	// public void itemStateChanged(ItemEvent e) {
	// if (e.getStateChange() == ItemEvent.SELECTED) {
	// JComboBox cbb = (JComboBox) e.getSource();
	// if (cbb.getSelectedItem() == null) {
	// return;
	// }
	// initUIComponentsByImpExpType(Integer
	// .parseInt(((ItemProperty) cbb.getSelectedItem())
	// .getCode()));
	// }
	// }
	// });
	// }
	// return cbbImpExpType;
	// **** }
	protected JTextField getTfPreCustomsDeclarationCode() {
		if (tfPreCustomsDeclarationCode == null) {
			tfPreCustomsDeclarationCode = new JTextField();
			tfPreCustomsDeclarationCode.setBounds(235, 37, 85, 21);
			tfPreCustomsDeclarationCode.setEditable(false);
		}
		return tfPreCustomsDeclarationCode;
	}

	protected JTextField getTfUnificationCode() {
		if (tfUnificationCode == null) {
			tfUnificationCode = new JTextField();
			tfUnificationCode.setBounds(483, 36, 90, 21);
			tfUnificationCode.setEditable(false);
			tfUnificationCode.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					System.out.println("执行ctrl+l");
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						if (dataState == DataState.EDIT) {
							tfUnificationCode.setEditable(true);
						}
					}
				}
			});
		}
		return tfUnificationCode;
	}

	protected JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(74, 62, 108, 20);
			// cbbCustoms.addFocusListener(new java.awt.event.FocusAdapter() {
			// public void focusLost(java.awt.event.FocusEvent e) {
			// final JComboBox comboBox = ((JComboBox) e.getSource());
			// SwingUtilities.invokeLater(new Runnable() {
			// public void run() {
			// if (comboBox.isEditable()) {
			// if (comboBox.getSelectedItem() == null) {
			// comboBox.requestFocus();
			// }
			//
			// }
			// }
			// });
			// }
			// });
		}
		return cbbCustoms;
	}

	protected JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(235, 62, 143, 21);
			// tfCustomsDeclarationCode
			// .addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// if (tfCustomsDeclarationCode.getText().trim().length()>18) {
			// // tfCustomsDeclarationCode.requestFocus();
			// // tfCustomsDeclarationCode.requestFocusInWindow();
			// }
			// }
			// });
			tfCustomsDeclarationCode.setDocument(new PlainDocument() {
				@Override
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (tfCustomsDeclarationCode.getText() != null
							&& tfCustomsDeclarationCode.getText().trim()
									.length() < 18) {
						super.insertString(offs, str, a);
						System.out.println(str);
					}
					super.insertString(offs, "", a);
				}
			});
		}
		return tfCustomsDeclarationCode;
	}

	protected JCalendarComboBox getCbbImpExpDate() {
		if (cbbImpExpDate == null) {
			cbbImpExpDate = new JCalendarComboBox();
			cbbImpExpDate.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					cbbDeclarationDate.setDate(cbbImpExpDate.getDate());
				}
			});
			cbbImpExpDate.setDate(new Date());
			cbbImpExpDate.setBounds(432, 62, 84, 20);
		}
		return cbbImpExpDate;
	}

	protected JCalendarComboBox getCbbDeclarationDate() {
		if (cbbDeclarationDate == null) {
			cbbDeclarationDate = new JCalendarComboBox();
			cbbDeclarationDate.setBounds(573, 62, 112, 21);
			cbbDeclarationDate.addPropertyChangeListener("value",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							setRate();
						}
					});
		}
		return cbbDeclarationDate;
	}

	protected JTextField getTfManageCompany() {
		if (tfManageCompany == null) {
			tfManageCompany = new JTextField();
			tfManageCompany.setBounds(74, 87, 262, 21);
			tfManageCompany.setEditable(false);
		}
		return tfManageCompany;
	}

	protected JTextField getTfAcceptGoodsCompany() {
		if (tfAcceptGoodsCompany == null) {
			tfAcceptGoodsCompany = new JTextField();
			tfAcceptGoodsCompany.setBounds(395, 87, 290, 21);
			tfAcceptGoodsCompany.setEditable(false);
		}
		return tfAcceptGoodsCompany;
	}

	protected JComboBox getCbbTransferMode() {
		if (cbbTransferMode == null) {
			cbbTransferMode = new JComboBox();
			cbbTransferMode.setBounds(74, 114, 108, 20);
		}
		return cbbTransferMode;
	}

	protected JTextField getTfConveyance() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(235, 115, 113, 21);
			tfConveyance.setDocument(new ConveyanceDocument());
			tfConveyance.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					CompanyOther other = CommonVars.getOther();
					// 检查运输工具位数时加上一额外自定义位数，在系统设置-其他设置-其他设置-运输工具额外位数
					// 若为空默认为14
					int addDigit = other.getTransportTool() == null ? 14
							: (other.getTransportTool() + 1);
					System.out.println("运输工具额外位数：" + addDigit);
					if (tfConveyance.getText().length() > 0) {
						String conveyance = tfConveyance.getText().substring(0,
								1);
						if (conveyance.equals("@")) {
							if (tfConveyance.getText().length() != 14
									&& tfConveyance.getText().length() != 17
									&& tfConveyance.getText().length() != addDigit) {
								JOptionPane.showMessageDialog(
										DgBaseImportCustomsDeclaration.this,
										"司机纸号码应该为13位或者16位或用户自定义位", "提示",
										JOptionPane.YES_NO_OPTION);
							}
						}
					}
				}
			});

			tfConveyance.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if (tfConveyance.getText() != null
							&& tfConveyance.getText().startsWith("K")
							&& tfTcsEdiId.getSelectedIndex() != 1) {
						JOptionPane.showMessageDialog(
								DgBaseImportCustomsDeclaration.this,
								"运输工具为‘K’开头表示报关单为：跨境快速通关报关单，报关单标志将自动修改为‘普通报关’",
								"提示", JOptionPane.CANCEL_OPTION);
						tfTcsEdiId.setSelectedItem(new ItemProperty(
								TCSCommonCode.TcsEdiId_normal,
								TCSCommonCode
										.getTcsEdiId(TCSCommonCode.TcsEdiId_normal)));
						btnTransferCustoms.setEnabled(false);
					} else {
						btnTransferCustoms.setEnabled(true);
					}
				}
			});

		}
		return tfConveyance;
	}

	class ConveyanceDocument extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			String text = super.getText(0, super.getLength());
			if (text.length() > 1) {
				String conveyance = text.substring(0, 1);
				if (conveyance.equals("@")) {
					if (super.getLength() > 26 || str.length() > 26
							|| super.getLength() + str.length() > 26) {
						return;
					}
				}
			}
			super.insertString(offs, str, a);
		}
	}

	// public JComboBox getCbbBillOfLading() {
	// if (cbbBillOfLading == null) {
	// cbbBillOfLading = new JComboBox();
	// cbbBillOfLading.setBounds(473, 123, 184, 21);
	// }
	// return cbbBillOfLading;
	// }

	protected JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(74, 140, 108, 20);
		}
		return cbbTradeMode;
	}

	protected JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(235, 140, 108, 20);
			cbbLevyKind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (cbbLevyKind.getSelectedItem() == null //edit by cjb
					// 10.14
					// && cbbTradeMode.getSelectedItem() != null) {
					// cbbLevyKind.requestFocus();
					// }
				}
			});
		}
		return cbbLevyKind;
	}

	protected JTextField getTfLicense() {
		if (tfLicense == null) {
			tfLicense = new JTextField();
			tfLicense.setBounds(74, 166, 169, 21);
		}
		return tfLicense;
	}

	protected JComboBox getCbbCountryOfLoadingOrUnloading() {
		if (cbbCountryOfLoadingOrUnloading == null) {
			cbbCountryOfLoadingOrUnloading = new JComboBox();
			cbbCountryOfLoadingOrUnloading.setBounds(317, 167, 139, 20);
		}
		return cbbCountryOfLoadingOrUnloading;
	}

	protected JComboBox getCbbDomesticDestinationOrSource() {
		if (cbbDomesticDestinationOrSource == null) {
			cbbDomesticDestinationOrSource = new JComboBox();
			cbbDomesticDestinationOrSource.setBounds(525, 166, 160, 20);
		}
		return cbbDomesticDestinationOrSource;
	}

	protected JComboBox getCbbPortOfLoadingOrUnloading() {
		if (cbbPortOfLoadingOrUnloading == null) {
			cbbPortOfLoadingOrUnloading = new JComboBox();
			cbbPortOfLoadingOrUnloading.setBounds(74, 193, 108, 20);
		}
		return cbbPortOfLoadingOrUnloading;
	}

	protected JTextField getTfAuthorizeFile() {
		if (tfAuthorizeFile == null) {
			tfAuthorizeFile = new JTextField();
			tfAuthorizeFile.setBounds(235, 193, 79, 21);
			tfAuthorizeFile.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (tfAuthorizeFile.getText() != null
							&& !tfAuthorizeFile.getText().trim().equals("")
							&& tfAuthorizeFile.getText().trim().length() != 9) {
						JOptionPane.showMessageDialog(
								DgBaseImportCustomsDeclaration.this,
								"批准文号应该为9位,请重新填写！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						tfAuthorizeFile.setText("");
					}
				}
			});
		}
		return tfAuthorizeFile;
	}

	protected JTextField getTfContract() {
		if (tfContract == null) {
			tfContract = new JTextField();
			tfContract.setBounds(401, 192, 97, 21);

		}
		return tfContract;
	}

	protected JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(552, 193, 133, 20);
		}
		return cbbTransac;
	}

	protected JComboBox getCbbFreightageType() {
		if (cbbFreightageType == null) {
			cbbFreightageType = new JComboBox();
			cbbFreightageType.setBounds(235, 217, 99, 20);
		}
		return cbbFreightageType;
	}

	protected JComboBox getCbbInsuranceType() {
		if (cbbInsuranceType == null) {
			cbbInsuranceType = new JComboBox();
			cbbInsuranceType.setBounds(74, 241, 108, 19);
		}
		return cbbInsuranceType;
	}

	protected JComboBox getCbbIncidentalExpensesType() {
		if (cbbIncidentalExpensesType == null) {
			cbbIncidentalExpensesType = new JComboBox();
			cbbIncidentalExpensesType.setBounds(471, 241, 99, 19);
		}
		return cbbIncidentalExpensesType;
	}

	protected JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(237, 266, 146, 19);
		}
		return cbbWrapType;
	}

	protected JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(415, 266, 101, 19);
			// tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	protected JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(550, 266, 135, 19);
			// tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	class CustomDocument extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}

			if (super.getLength() >= 11 || str.length() > 11
					|| super.getLength() + str.length() > 11) {
				return;
			}
			super.insertString(offs, str, a);
		}
	}

	protected JTextField getTfContainerNum() {
		if (tfContainerNum == null) {
			tfContainerNum = new JTextField();
			tfContainerNum.setBounds(74, 289, 168, 19);
			// tfContainerNum
			// .addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// if (tfContainerNum.getText().trim().equals("")) {
			// // tfContainerNum.requestFocus();
			// tfContainerNum.requestFocusInWindow();
			// }
			// }
			// });
		}
		return tfContainerNum;
	}

	protected JTextField getTfAttachedBill() {
		if (tfAttachedBill == null) {
			tfAttachedBill = new JTextField();
			tfAttachedBill.setBounds(299, 289, 139, 19);
			tfAttachedBill.setEditable(false);
		}
		return tfAttachedBill;
	}

	protected JButton getBtnAttachedBill() {
		if (btnAttachedBill == null) {
			btnAttachedBill = new JButton();
			btnAttachedBill.setBounds(436, 289, 20, 19);
			btnAttachedBill.setText("...");
			btnAttachedBill
					.addKeyListener(new FocusActionListner(getCbbUses()));
			btnAttachedBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAttachedBill dgAttachedBill = new DgAttachedBill();
							dgAttachedBill.setAttachedBill(tfAttachedBill
									.getText().trim());
							dgAttachedBill.setVisible(true);
							if (dgAttachedBill.isOk()) {
								tfAttachedBill.setText(dgAttachedBill
										.returnValue());
							}
						}
					});
		}
		return btnAttachedBill;
	}

	protected JComboBox getCbbUses() {
		if (cbbUses == null) {
			cbbUses = new JComboBox();
			cbbUses.setBounds(495, 289, 190, 19);
		}
		return cbbUses;
	}

	protected JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(306, 313, 95, 19);
			cbbCurrency.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setRate();
				}
			});
			cbbCurrency
					.addKeyListener(new FocusActionListner(getCbbCustomer()));
		}
		return cbbCurrency;
	}

	protected JTextField getTfCreater() {
		if (tfCreater == null) {
			tfCreater = new JTextField();
			tfCreater.setBounds(450, 313, 64, 19);
			tfCreater.setEditable(false);
		}
		return tfCreater;
	}

	protected JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(74, 338, 204, 20);
			cbbCustomer.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// if (e.getStateChange() == ItemEvent.SELECTED) {
					CompanyOther other = CommonVars.getOther();
					if (other == null) {
						return;
					}
					if (!(other.getIsAutoAmount() == null ? false : other
							.getIsAutoAmount())) {
						return;
					}
					if (cbbCustomer.getSelectedItem() != null) {
						ScmCoc sc = (ScmCoc) cbbCustomer.getSelectedItem();
						if ((sc == null ? "" : sc.getId())
								.equals(customsDeclaration.getCustomer() == null ? ""
										: customsDeclaration.getCustomer()
												.getId())) {
							return;
						}
						if (sc.getTrade() != null) {
							DgBaseImportCustomsDeclaration.this
									.getCbbTradeMode().setSelectedItem(
											sc.getTrade());
						}
						if (sc.getPredock() != null) {
							DgBaseImportCustomsDeclaration.this.getCbbPredock()
									.setSelectedItem(sc.getPredock());
						}
						if (sc.getWarp() != null) {
							DgBaseImportCustomsDeclaration.this
									.getCbbWrapType().setSelectedItem(
											sc.getWarp());
						}
						if (sc.getCountry() != null) {
							cbbCountryOfLoadingOrUnloading.setSelectedItem(sc
									.getCountry());
						}
						if (sc.getPortLin() != null) {
							cbbPortOfLoadingOrUnloading.setSelectedItem(sc
									.getPortLin());
						}
						if (sc.getCustoms() != null) {
							cbbCustoms.setSelectedItem(sc.getCustoms());
						}
						if (sc.getTransf() != null) {
							cbbTransferMode.setSelectedItem(sc.getTransf());
						}
					}
				}
			});
		}
		return cbbCustomer;
	}

	protected JFormattedTextField getTfExchangeRate() {
		if (tfExchangeRate == null) {
			tfExchangeRate = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfExchangeRate.setBounds(307, 338, 135, 21);
			tfExchangeRate.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExchangeRate;
	}

	protected JComboBox getCbbDeclarationCustoms() {
		if (cbbDeclarationCustoms == null) {
			cbbDeclarationCustoms = new JComboBox();
			cbbDeclarationCustoms.setBounds(503, 340, 182, 20);
		}
		return cbbDeclarationCustoms;
	}

	protected JTextField getTfMemos() {
		if (tfMemos == null) {
			tfMemos = new JTextField();
			tfMemos.setBounds(74, 362, 185, 19);
			tfMemos.setEditable(false);
		}
		return tfMemos;
	}

	protected JButton getBtnMemo() {
		if (btnMemo == null) {
			btnMemo = new JButton();
			btnMemo.setBounds(261, 362, 19, 19);
			btnMemo.setText("...");
			btnMemo.addKeyListener(new FocusActionListner(getCbbPredock()));
			btnMemo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int impExpType = Integer
							.parseInt(((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode());
					DgCustomsDeclarationMemo dg = new DgCustomsDeclarationMemo();
					if (!tfMemos.getText().trim().equals("")) {
						dg.setMemoStr(tfMemos.getText());
					}
					dg.setCertificateCode(customsDeclaration
							.getCertificateCode());
					dg.setImpExpType(impExpType);
					dg.setContainerNum(tfContainerNum.getText().trim());
					dg.setVisible(true);
					if (dg.isOk()) {
						tfMemos.setText(dg.returnMemoValue());
						customsDeclaration.setMemos(tfMemos.getText());
						customsDeclaration.setCertificateCode(dg
								.returnCertificateCodeValue());
					}
				}
			});
		}
		return btnMemo;
	}

	protected JComboBox getCbbPredock() {
		if (cbbPredock == null) {
			cbbPredock = new JComboBox();
			cbbPredock.setBounds(308, 362, 135, 19);
			cbbPredock.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getItem() != null
							&& e.getStateChange() == ItemEvent.SELECTED) {
						System.out.println("itemStateChanged()");
						Customs customs = (Customs) cbbDeclarationCustoms
								.getSelectedItem();
						if (customs != null) {
							if (!"52".equals(customs.getCode().substring(0, 2))) {
								return;
							}
						}
						String shortName = ((PreDock) e.getItem()) == null ? ""
								: ((PreDock) e.getItem()).getShortName();
						if (tfMemos.getText().indexOf("装卸口岸") < 0) {// 不存在装卸口岸
							if (tfMemos.getText().contains("转自")) {
								tfMemos.setText(tfMemos.getText() + "[装卸口岸]:"
										+ shortName);
							} else {
								tfMemos.setText("[装卸口岸]:" + shortName
										+ tfMemos.getText());
							}
						} else if (tfMemos.getText().indexOf("装卸口岸") > -1) {// 存在装卸口岸
							String oldInfo = tfMemos.getText().trim();
							int len = oldInfo.indexOf("装卸口岸");
							String newInfo = "";
							if (oldMemos == null || "".equals(oldMemos)) {
								if (!shortName.equals(oldInfo.substring(
										len + 6, oldInfo.length()))) {
									newInfo = oldInfo.substring(0, len + 6)
											+ shortName
											+ oldInfo.substring(len + 6
													+ shortName.length(),
													oldInfo.length());
								} else {
									newInfo = oldInfo;
								}

							} else {
								newInfo = oldInfo.replace(oldMemos, shortName);
							}
							tfMemos.setText(newInfo);
						}
						oldMemos = shortName;
					}
				}
			});

		}
		return cbbPredock;
	}

	protected JTextField getTfAllContainerNum() {
		if (tfAllContainerNum == null) {
			tfAllContainerNum = new JTextField();
			tfAllContainerNum.setBounds(518, 362, 150, 19);
			tfAllContainerNum.setEditable(false);
		}
		return tfAllContainerNum;
	}

	protected JButton getBtnContainer() {
		if (btnContainer == null) {
			btnContainer = new JButton();
			btnContainer.setBounds(668, 362, 17, 19);
			btnContainer.setText("...");
			btnContainer.addKeyListener(new FocusActionListner(getBtnSave()));
			btnContainer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationContainer dg = new DgCustomsDeclarationContainer();
					dg.setBaseCustomsDeclaration(DgBaseImportCustomsDeclaration.this.customsDeclaration);
					dg.setCustomsDeclarationDataState(dataState);
					dg.setContainers(containers);
					dg.setBaseEncAction(baseEncAction);
					dg.setVisible(true);
					containers = dg.getResultContainer();
					tfAllContainerNum.setText(Container
							.getAllContainerCode(containers));
					tfContainerNum.setText(Container
							.getAllConvertToContainerCode(containers));
					//
					// 如果是修改那么就进行对报关单保存
					//
					if (dataState == DataState.EDIT) {
						customsDeclaration.setAllContainerNum(tfAllContainerNum
								.getText());
						customsDeclaration.setContainerNum(tfContainerNum
								.getText());
						customsDeclaration = (BaseCustomsDeclaration) baseEncAction
								.saveCustomsDeclaration(
										new Request(CommonVars.getCurrUser()),
										customsDeclaration);
						customsDeclarationModel.updateRow(customsDeclaration);
					}
				}
			});
		}
		return btnContainer;
	}

	/**
	 * 初始化窗口上的数据来自进出口类型
	 */
	protected void initUIComponentsByImpExpType(int impExpType) {
		// if (dataState != DataState.ADD) {
		// return;
		// }
		// DefaultComboBoxModel transferMode = null;
		// switch (impExpType) {
		// case ImpExpType.DIRECT_IMPORT://料件进口
		// transferMode = CustomBaseModel.getInstance().getTransfModel();
		// this.tfConveyance.setEditable(true);
		// this.tfAttachedBill.setEditable(true);
		// this.tfBillOfLading.setEditable(true);
		// break;
		// case ImpExpType.TRANSFER_FACTORY_IMPORT://转厂进口
		// transferMode = new DefaultComboBoxModel(this.getTransfList(false)
		// .toArray());
		// this.tfConveyance.setEditable(true);
		// this.tfConveyance.setText("");
		// this.tfAttachedBill.setEditable(false);
		// this.tfBillOfLading.setEditable(false);
		// this.tfBillOfLading.setText("");
		// break;
		// case ImpExpType.BACK_FACTORY_REWORK://退厂返工
		// transferMode = new DefaultComboBoxModel(this.getTransfList(true)
		// .toArray());
		// this.tfConveyance.setEditable(false);
		// this.tfAttachedBill.setEditable(false);
		// this.tfBillOfLading.setEditable(true);
		// break;
		// case ImpExpType.GENERAL_TRADE_IMPORT://一般贸易进口
		// transferMode = CustomBaseModel.getInstance().getTransfModel();
		// this.tfConveyance.setEditable(true);
		// this.tfAttachedBill.setEditable(true);
		// this.tfBillOfLading.setEditable(true);
		// break;
		// default:
		// break;
		// }

	}

	/**
	 * 获得是否是汽车运输的运输方式list
	 */
	protected List getTransfList(boolean isAutocarMode) {
		List transferList = CustomBaseList.getInstance().getTransfs();
		List newList = new ArrayList();
		for (int i = 0; i < transferList.size(); i++) {
			Transf transf = (Transf) transferList.get(i);
			if (isAutocarMode == true) {
				if (transf.getCode().trim().equals("4")) {// 是汽车运输
					newList.add(transf);
					break;
				}
			} else {
				if (!transf.getCode().trim().equals("4")) {// 不是汽车运输
					newList.add(transf);
				}
			}
		}
		return newList;
	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected void initUIComponents() {

		// 初始化司机纸海关编号
		/*
		 * this.jComboBox1.removeAllItems(); List complex = materialManageAction
		 * .findMotorCodeComplex(new Request(CommonVars.getCurrUser())); for
		 * (int i = 0; i < complex.size(); i++) { String complex1 =
		 * complex.get(i).toString(); this.jComboBox1.addItem(complex1); }
		 * this.jComboBox1.setUI(new CustomBaseComboBoxUI(100));
		 */
		// 初始化报关员与联系电话
		this.jComboBox.removeAllItems();
		List customsUserList = materialManageAction
				.findCustomsUser(new Request(CommonVars.getCurrUser()));
		for (int i = 0; i < customsUserList.size(); i++) {
			CustomsUser user = (CustomsUser) customsUserList.get(i);
			this.jComboBox.addItem(user.getName());
		}
		this.jComboBox.setUI(new CustomBaseComboBoxUI(100));
		// 初始化进口类型
		this.cbbImpExpType.removeAllItems();
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REMAIN_FORWARD_IMPORT).toString(), "余料结转进口"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.MATERIAL_DOMESTIC_SALES), "海关批准内销"));

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbImpExpType.setSelectedIndex(0);
		// 初始化提运单号
		// List tmplist = materialManageAction.findMotorCode(new Request(
		// CommonVars.getCurrUser()));
		// this.cbbBillOfLading.setModel(new DefaultComboBoxModel(tmplist
		// .toArray()));
		// this.cbbBillOfLading.setRenderer(CustomBaseRender.getInstance()
		// .getRender("code", "homeCard"));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbBillOfLading, "code", "homeCard");
		// cbbBillOfLading.setSelectedItem(null);

		// 初始化进口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustoms);
		// 初始化运输方式
		this.cbbTransferMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransferMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransferMode);

		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTradeMode);
		cbbTradeMode.setSelectedItem(null);

		// 初始化征免性质
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.cbbLevyKind
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbLevyKind);

		// 初始化起运国
		this.cbbCountryOfLoadingOrUnloading.setModel(CustomBaseModel
				.getInstance().getCountryModel());
		this.cbbCountryOfLoadingOrUnloading.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCountryOfLoadingOrUnloading);

		// 初始化境内目的地
		this.cbbDomesticDestinationOrSource.setModel(CustomBaseModel
				.getInstance().getDistrictModelModel());
		this.cbbDomesticDestinationOrSource.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDomesticDestinationOrSource);
		// 初始化装货港
		this.cbbPortOfLoadingOrUnloading.setModel(CustomBaseModel.getInstance()
				.getPortLinModel());
		this.cbbPortOfLoadingOrUnloading.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPortOfLoadingOrUnloading);
		// 初始化成交方式
		this.cbbTransac.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		this.cbbTransac.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbTransac);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		cbbWrapType.setSelectedItem(null);
		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbUses);
		// 初始化币制
		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCurrency);
		// 初始化保费币制
		this.cbbFeeCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbFeeCurr.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFeeCurr);
		// 初始化运费币制
		this.cbbInsurCurr
				.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbInsurCurr.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbInsurCurr);
		// 初始化杂费币制
		this.cbbOtherCurr
				.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbOtherCurr.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbOtherCurr);

		// 初始化报送海关
		this.cbbDeclarationCustoms.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.cbbDeclarationCustoms.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclarationCustoms);
		// 初始化码头
		this.cbbPredock.setModel(CustomBaseModel.getInstance()
				.getPreDockModel());
		this.cbbPredock.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbPredock);
		cbbPredock.setSelectedItem(null);
		// 初始化运费
		this.cbbFreightageType.removeAllItems();
		this.cbbFreightageType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.FREIGHT_RATE), "运费率"));
		this.cbbFreightageType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.FREIGHT_UNITPRICE), "运费单价/吨"));
		this.cbbFreightageType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.FREIGHT_TOTALPRICE), "运费总价"));
		this.cbbFreightageType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbFreightageType);
		// 初始化运保费
		this.cbbInsuranceType.removeAllItems();
		this.cbbInsuranceType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.INSURANCE_RATE), "保费率"));
		this.cbbInsuranceType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.INSURANCE_TOTALPRICE), "保费总价"));
		this.cbbInsuranceType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbInsuranceType);
		// 初始化杂费
		this.cbbIncidentalExpensesType.removeAllItems();
		this.cbbIncidentalExpensesType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_RATE),
				"杂费率"));
		this.cbbIncidentalExpensesType
				.addItem(new ItemProperty(
						String.valueOf(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_TOTALPRICE),
						"杂费总价"));
		this.cbbIncidentalExpensesType.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbIncidentalExpensesType);
		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 270);
		// 初始申报单位systemAction
		List companyList = systemAction.findEnableCompanies();
		this.cbbDeclarationCompany.setModel(new DefaultComboBoxModel(
				companyList.toArray()));
		this.cbbDeclarationCompany.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclarationCompany, "code", "name", 270);
		// // 初始化打印选项
		// this.cbbPrintParam.removeAllItems();
		//
		// this.cbbPrintParam.setUI(new CustomBaseComboBoxUI(310));
		// this.cbbPrintParam.setMaximumRowCount(8);

		// 手动输入预录入号
		List listParameter = systemAction.findCompanyOther(new Request(
				CommonVars.getCurrUser(), true));
		if (listParameter.size() > 0 && listParameter.get(0) != null) {
			CompanyOther companyOther = (CompanyOther) listParameter.get(0);
			isManualPreCode = companyOther.getIsManualPreCode();
			if (isManualPreCode != null && isManualPreCode) {
				this.tfPreCustomsDeclarationCode.setEditable(true);
				this.btnPreCustomsDeclarationCode.setVisible(false);
				tfPreCustomsDeclarationCode.setBounds(234, 47, 193, 21);
			}
		}

		tfTcsEntryType.removeAllItems();
		tfTcsEntryType
				.addItem(new ItemProperty(
						TCSCommonCode.TcsEntryType_declarationAndNoPaper,
						TCSCommonCode
								.getTcsEntryType(TCSCommonCode.TcsEntryType_declarationAndNoPaper)));
		tfTcsEntryType.addItem(new ItemProperty(
				TCSCommonCode.TcsEntryType_normal, TCSCommonCode
						.getTcsEntryType(TCSCommonCode.TcsEntryType_normal)));
		tfTcsEntryType.addItem(new ItemProperty(
				TCSCommonCode.TcsEntryType_list, TCSCommonCode
						.getTcsEntryType(TCSCommonCode.TcsEntryType_list)));
		tfTcsEntryType.addItem(new ItemProperty(
				TCSCommonCode.TcsEntryType_noPaper, TCSCommonCode
						.getTcsEntryType(TCSCommonCode.TcsEntryType_noPaper)));
		tfTcsEntryType
				.addItem(new ItemProperty(
						TCSCommonCode.TcsEntryType_listAndNoPaper,
						TCSCommonCode
								.getTcsEntryType(TCSCommonCode.TcsEntryType_listAndNoPaper)));
		tfTcsEntryType.setRenderer(CustomBaseRender.getInstance().getRender());

		tfTcsType.removeAllItems();
		tfTcsType.addItem(new ItemProperty(TCSCommonCode.TcsType_normal,
				TCSCommonCode.getTcsType(TCSCommonCode.TcsType_normal)));
		tfTcsType.addItem(new ItemProperty(TCSCommonCode.TcsType_ML,
				TCSCommonCode.getTcsType(TCSCommonCode.TcsType_ML)));
		tfTcsType.addItem(new ItemProperty(TCSCommonCode.TcsType_SS,
				TCSCommonCode.getTcsType(TCSCommonCode.TcsType_SS)));
		tfTcsType.addItem(new ItemProperty(TCSCommonCode.TcsType_SD,
				TCSCommonCode.getTcsType(TCSCommonCode.TcsType_SD)));
		tfTcsEdiId.removeAllItems();
		tfTcsEdiId.addItem(new ItemProperty(TCSCommonCode.TcsEdiId_southBefore,
				TCSCommonCode.getTcsEdiId(TCSCommonCode.TcsEdiId_southBefore)));
		tfTcsEdiId.addItem(new ItemProperty(TCSCommonCode.TcsEdiId_normal,
				TCSCommonCode.getTcsEdiId(TCSCommonCode.TcsEdiId_normal)));
		tfTcsEdiId.addItem(new ItemProperty(TCSCommonCode.TcsEdiId_northBefore,
				TCSCommonCode.getTcsEdiId(TCSCommonCode.TcsEdiId_northBefore)));
		tfTcsEdiId.addItem(new ItemProperty(
				TCSCommonCode.TcsEdiId_normal_south, TCSCommonCode
						.getTcsEdiId(TCSCommonCode.TcsEdiId_normal_south)));

		cbbOperType.removeAllItems();
		cbbOperType
				.addItem(new ItemProperty(
						TCSCommonCode.OperType_customs_upload,
						TCSCommonCode
								.getOperTypeDesc(TCSCommonCode.OperType_customs_upload)));
		cbbOperType
				.addItem(new ItemProperty(
						TCSCommonCode.OperType_customs_transfer_upload,
						TCSCommonCode
								.getOperTypeDesc(TCSCommonCode.OperType_customs_transfer_upload)));
		cbbOperType
				.addItem(new ItemProperty(
						TCSCommonCode.OperType_customs_declaration,
						TCSCommonCode
								.getOperTypeDesc(TCSCommonCode.OperType_customs_declaration)));
		cbbOperType
				.addItem(new ItemProperty(
						TCSCommonCode.OperType_customs_transfer_declaration,
						TCSCommonCode
								.getOperTypeDesc(TCSCommonCode.OperType_customs_transfer_declaration)));
		cbbOperType
				.addItem(new ItemProperty(
						TCSCommonCode.OperType_customs_dzsc_upload,
						TCSCommonCode
								.getOperTypeDesc(TCSCommonCode.OperType_customs_dzsc_upload)));
		cbbOperType.addItem(new ItemProperty(
				TCSCommonCode.OperType_customs_save, TCSCommonCode
						.getOperTypeDesc(TCSCommonCode.OperType_customs_save)));

		CustomFormattedTextFieldUtils
				.setFormatterFactory(this.tfGrossWeight, 4);
		CustomFormattedTextFieldUtils.setFormatterFactory(this.tfNetWeight, 4);
	}

	/**
	 * 当新增或删除时，显示最初数据
	 */
	protected void showPrimalData() {
		if (dataState == DataState.ADD) {
			this.customsDeclaration = this.newCustomsDeclaration();// new
			// BaseCustomsDeclaration();
			this.containers = new ArrayList();
			// this.customsDeclaration.setSerialNumber(this.baseEncAction
			// .getCustomsDeclarationSerialNumber(new Request(CommonVars
			// .getCurrUser()), ImpExpFlag.IMPORT, emsHead
			// .getEmsNo()));
			this.customsDeclaration.setEmsHeadH2k(this.emsHead.getEmsNo());
			customsDeclaration.setTradeCode(emsHead.getTradeCode());
			customsDeclaration.setTradeName(emsHead.getTradeName());
			customsDeclaration.setMachCode(emsHead.getMachCode());
			customsDeclaration.setMachName(emsHead.getMachName());
			this.customsDeclaration.setImpExpFlag(Integer
					.valueOf(ImpExpFlag.IMPORT));
			this.customsDeclaration.setImpExpType(ImpExpType.DIRECT_IMPORT);
			this.customsDeclaration.setCreater(CommonVars.getCurrUser());
			this.customsDeclaration.setCompany(CommonVars.getCurrUser()
					.getCompany());

			// 报关员和报关员电话
			this.customsDeclaration.setCustomser(((Company) CommonVars
					.getCurrUser().getCompany()).getAppCusMan());
			this.customsDeclaration.setTelephone(((Company) CommonVars
					.getCurrUser().getCompany()).getAppCusManTel());
			if (((Company) CommonVars.getCurrUser().getCompany())
					.getAppCusManTel() == null
					|| ((Company) CommonVars.getCurrUser().getCompany())
							.getAppCusManTel().equals("")) {
				String name = jComboBox.getSelectedItem() == null ? null
						: jComboBox.getSelectedItem().toString();
				String tel = materialManageAction.findCustomsUserTel(
						new Request(CommonVars.getCurrUser()), name);
				this.customsDeclaration.setTelephone(tel);
			}
			// edit by xxm
			// 后加来自系统参数设置,如果系统参数设置-进口类型为空或所有类型或与新增类型相一致则带出系统参数设置，否则不带出
			CustomsDeclarationSet other1 = systemAction.findCustomsSet(
					new Request(CommonVars.getCurrUser(), true),
					this.customsDeclaration.getImpExpType());

			if (other1 != null) {
				customsDeclaration.setDeclarationCustoms(other1
						.getDeclarationCustoms());
				customsDeclaration.setLevyKind(other1.getCustomlevyKind());
				customsDeclaration.setBillOfLading(other1.getBillOfLading());
				customsDeclaration.setBalanceMode(other1.getBalanceMode());
				customsDeclaration.setTradeMode(other1.getTradeMode());
				customsDeclaration.setConveyance(other1.getConveyance());
				customsDeclaration.setTransac(other1.getTransac());
				customsDeclaration.setUses(other1.getUses());
				customsDeclaration.setCountryOfLoadingOrUnloading(other1
						.getCoun());
				customsDeclaration.setPredock(other1.getPredock());
				customsDeclaration
						.setPortOfLoadingOrUnloading(other1.getPort());
				customsDeclaration.setWrapType(other1.getWrapType());
				customsDeclaration.setCustoms(other1.getCustoms());
				customsDeclaration.setTransferMode(other1.getTransferMode());
				customsDeclaration.setDomesticDestinationOrSource(other1
						.getDistrict());
				customsDeclaration.setCurrency(other1.getCurr());
			}

			CompanyOther other = CommonVars.getOther();
			if (other != null) {
				if (customsDeclaration.getCurrency() == null) {
					customsDeclaration.setCurrency(other.getCommonCurr());
				}
				customsDeclaration.setDeclarationCompany(other
						.getDeclarationCompany());

			}

		} else if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
			if (customsDeclarationModel != null
					&& customsDeclarationModel.getCurrentRow() != null) {
				customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
						.getCurrentRow();
			}
		}
		showData();
		if (dataState != DataState.EDIT && dataState != DataState.BROWSE) {
			setRate();
		}
	}

	/**
	 * 显示空数据
	 * 
	 */
	protected void showEmptyData() {
		this.tfCustomsDeclarationSerialNumber.setText("");
		this.tfAcceptGoodsCompany.setText("");
		this.tfAttachedBill.setText("");
		this.tfAuthorizeFile.setText("");
		this.tfCommodityNum.setText("0");
		this.tfManageCompany.setText("");
		// this.tfContainerNum.setText("");
		this.tfContract.setText("");
		this.tfConveyance.setText("");
		this.cbbCountryOfLoadingOrUnloading.setSelectedItem(null);
		this.tfCreater.setText("");
		this.cbbCustomer.setSelectedItem(null);
		this.cbbCustoms.setSelectedItem(null);
		this.tfCustomsDeclarationCode.setText("");
		this.tfVoyageNo.setText("");
		this.cbbDeclarationCustoms.setSelectedItem(null);
		this.cbbDeclarationDate.setDate(null);
		this.cbbDomesticDestinationOrSource.setSelectedItem(null);
		this.tfExchangeRate.setValue(null);
		this.tfFreightage.setValue(null);
		this.cbbFreightageType.setSelectedIndex(-1);
		this.tfGrossWeight.setText("");
		this.tfNetWeight.setText("");
		this.cbbImpExpDate.setDate(null);
		this.tfCreateDate.setDate(null);
		cbbImpExpType.setSelectedIndex(-1);
		this.tfIncidentalExpenses.setValue(Double.valueOf(0));
		cbbIncidentalExpensesType.setSelectedIndex(-1);
		this.tfInsurance.setValue(Double.valueOf(0));
		cbbInsuranceType.setSelectedIndex(-1);
		cbbLevyKind.setSelectedItem(null);
		this.tfLicense.setText("");
		this.tfMemos.setText("");
		this.tfPerTax.setValue(null);
		cbbPortOfLoadingOrUnloading.setSelectedItem(null);
		this.tfEmsNo.setText("");
		this.tfPreCustomsDeclarationCode.setText("");
		cbbPredock.setSelectedItem(null);
		cbbTradeMode.setSelectedItem(null);
		cbbTransac.setSelectedItem(null);
		cbbTransferMode.setSelectedItem(null);
		this.tfUnificationCode.setText("");
		cbbUses.setSelectedItem(null);
		cbbWrapType.setSelectedItem(null);
		this.tfAllContainerNum.setText("");
		this.tfContainerNum.setText("");
		this.jComboBox.setSelectedIndex(-1);
		this.cbbDeclarationCompany.setSelectedItem(null);
		this.tfBondedWarehouse.setText("");
		this.tfCustomsEnvelopBillNo.setText("");
		this.tfTelephone.setText("");
		this.tfConnectNo.setText("");
		this.tfConnectDeclarationNo.setText("");
	}

	/**
	 * 显示报关数据
	 * 
	 */
	@SuppressWarnings("deprecation")
	protected void showData() {
		if (this.customsDeclaration == null) {
			showEmptyData();
			return;
		}
		if (customsDeclaration != null && customsDeclaration.getId() != null) {
			customsDeclaration = baseEncAction.findCustomsDeclarationById(
					new Request(CommonVars.getCurrUser()),
					customsDeclaration.getId());
		}
		if (customsDeclaration.getBrokerCorp() != null) {
			tfDeclaraCustomsBroker.setText(customsDeclaration.getBrokerCorp()
					.getOrgaName());
		}
		if (this.customsDeclaration.getSerialNumber() != null) {
			this.tfCustomsDeclarationSerialNumber
					.setText(this.customsDeclaration.getSerialNumber()
							.toString());
		} else {
			this.tfCustomsDeclarationSerialNumber.setText("");
		}
		if (this.customsDeclaration.getMachName() != null) {
			this.tfAcceptGoodsCompany.setText(this.customsDeclaration
					.getMachName());
		} else {
			this.tfAcceptGoodsCompany.setText("");
		}
		if (this.customsDeclaration.getAttachedBill() != null) {
			this.tfAttachedBill.setText(this.customsDeclaration
					.getAttachedBill());
		} else {
			this.tfAttachedBill.setText("");
		}
		if (this.customsDeclaration.getAuthorizeFile() != null) {
			this.tfAuthorizeFile.setText(this.customsDeclaration
					.getAuthorizeFile());
		} else {
			this.tfAuthorizeFile.setText("");
		}

		if (this.customsDeclaration.getBillOfLading() != null) {
			this.tfBillOfLading.setText(customsDeclaration.getBillOfLading());
		} else {
			this.tfBillOfLading.setText("");
		}

		if (this.customsDeclaration.getDomesticConveyanceCode() != null) {
			this.tfdomesticConveyanceCode.setText(customsDeclaration
					.getDomesticConveyanceCode());
		} else {
			this.tfdomesticConveyanceCode.setText("");
		}

		if (this.customsDeclaration.getCommodityNum() != null) {
			this.tfCommodityNum.setValue(this.customsDeclaration
					.getCommodityNum());
		} else {
			this.tfCommodityNum.setText("0");
		}
		if (this.customsDeclaration.getTradeName() != null) {
			this.tfManageCompany
					.setText(this.customsDeclaration.getTradeName());
		} else {
			this.tfManageCompany.setText("");
		}
		if (this.customsDeclaration.getContainerNum() != null) {
			this.tfContainerNum.setText(this.customsDeclaration
					.getContainerNum());
		} else {
			this.tfContainerNum.setText("");
		}
		if (this.customsDeclaration.getContract() != null) {
			this.tfContract.setText(this.customsDeclaration.getContract());
		} else {
			this.tfContract.setText("");
		}
		if (this.customsDeclaration.getConveyance() != null) {// 运输工具
			this.tfConveyance.setText(this.customsDeclaration.getConveyance());
		} else {
			this.tfConveyance.setText("");
		}
		this.cbbCountryOfLoadingOrUnloading
				.setSelectedItem(this.customsDeclaration
						.getCountryOfLoadingOrUnloading());
		if (this.customsDeclaration.getCreater() != null) {
			this.tfCreater.setText(this.customsDeclaration.getCreater()
					.getUserName());
		} else {
			this.tfCreater.setText("");
		}
		this.cbbCurrency.setSelectedItem(this.customsDeclaration.getCurrency());
		this.cbbFeeCurr.setSelectedItem(this.customsDeclaration.getFeeCurr());
		this.cbbInsurCurr.setSelectedItem(this.customsDeclaration
				.getInsurCurr());
		this.cbbOtherCurr.setSelectedItem(this.customsDeclaration
				.getOtherCurr());
		if (this.customsDeclaration.getCustomer() != null) {
			this.cbbCustomer.setSelectedItem(this.customsDeclaration
					.getCustomer());
		} else {
			this.cbbCustomer.setSelectedItem(null);
		}
		// if (this.customsDeclaration.getCustomer() != null) {
		// this.cbbCustomer.setSelectedItem(this.customsDeclaration
		// .getCustomer());
		// ScmCoc sc = customsDeclaration.getCustomer();
		// if (customsDeclaration.getTradeMode() == null) {
		// if (sc.getTrade() != null) {
		// this.cbbTradeMode.setSelectedItem(sc.getTrade());
		// } else
		// this.cbbTradeMode.setSelectedItem(null);
		// } else {
		// this.cbbTradeMode.setSelectedItem(customsDeclaration
		// .getTradeMode());
		// }
		// if (customsDeclaration.getPredock() == null) {
		// if (sc.getPredock() != null) {
		// this.cbbPredock.setSelectedItem(sc.getPredock());
		// } else {
		// this.cbbPredock.setSelectedItem(null);
		// }
		// } else {
		// this.cbbPredock
		// .setSelectedItem(customsDeclaration.getPredock());
		// }
		// if (customsDeclaration.getWrapType() == null) {
		// if (sc.getWarp() != null) {
		// this.cbbWrapType.setSelectedItem(sc.getWarp());
		// } else {
		// this.cbbWrapType.setSelectedItem(null);
		// }
		// } else {
		// this.cbbWrapType.setSelectedItem(customsDeclaration
		// .getWrapType());
		// }
		// } else {
		// this.cbbCustomer.setSelectedItem(null);
		// }

		this.cbbCustoms.setSelectedItem(this.customsDeclaration.getCustoms());
		if (this.customsDeclaration.getCustomsDeclarationCode() != null) {
			this.tfCustomsDeclarationCode.setText(this.customsDeclaration
					.getCustomsDeclarationCode());
		} else {
			this.tfCustomsDeclarationCode.setText("");
		}

		if (this.customsDeclaration.getVoyageNo() != null) {
			this.tfVoyageNo.setText(this.customsDeclaration.getVoyageNo());
		} else {
			this.tfVoyageNo.setText("");
		}

		this.cbbDeclarationCustoms.setSelectedItem(this.customsDeclaration
				.getDeclarationCustoms());

		if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
			if (this.customsDeclaration.getDeclarationDate() != null) {
				this.cbbDeclarationDate.setDate(this.customsDeclaration
						.getDeclarationDate());
			} else {
				this.cbbDeclarationDate.setDate(null);
			}

			if (this.customsDeclaration.getImpExpDate() != null) {
				this.cbbImpExpDate.setDate(this.customsDeclaration
						.getImpExpDate());
			} else {
				this.cbbImpExpDate.setDate(null);
			}

			if (this.customsDeclaration.getCreateDate() != null) {
				this.tfCreateDate.setDate(this.customsDeclaration
						.getCreateDate());
			} else {
				this.tfCreateDate.setDate(null);
			}

		} else if (dataState == DataState.ADD) {
			this.cbbImpExpDate.setDate(new Date());
			this.cbbDeclarationDate.setDate(new Date());
			this.tfCreateDate.setDate(new Date());
		}

		/*
		 * if (this.customsDeclaration.getDeclarationDate() != null) {
		 * this.cbbDeclarationDate.setDate(this.customsDeclaration
		 * .getDeclarationDate()); } else {
		 * this.cbbDeclarationDate.setDate(null); }
		 */
		this.cbbDomesticDestinationOrSource
				.setSelectedItem(this.customsDeclaration
						.getDomesticDestinationOrSource());
		if (this.customsDeclaration.getExchangeRate() != null) {
			this.tfExchangeRate.setValue(this.customsDeclaration
					.getExchangeRate());
		} else {
			this.tfExchangeRate.setValue(null);
		}
		if (this.customsDeclaration.getFreightage() != null) {
			this.tfFreightage.setValue(this.customsDeclaration.getFreightage());
		} else {
			this.tfFreightage.setValue(null);
		}
		if (customsDeclaration.getFreightageType() != null) {
			this.cbbFreightageType.setSelectedIndex(ItemProperty
					.getIndexByCode(customsDeclaration.getFreightageType()
							.toString(), cbbFreightageType));
		} else {
			this.cbbFreightageType.setSelectedIndex(-1);
		}
		if (this.customsDeclaration.getGrossWeight() != null) {
			this.tfGrossWeight.setValue(this.customsDeclaration
					.getGrossWeight());
		} else {
			this.tfGrossWeight.setText("");
		}
		if (this.customsDeclaration.getNetWeight() != null) {
			this.tfNetWeight.setValue(this.customsDeclaration.getNetWeight());
		} else {
			this.tfNetWeight.setText("");
		}
		/*
		 * if (this.customsDeclaration.getImpExpDate() != null) {
		 * this.cbbImpExpDate.setDate(this.customsDeclaration.getImpExpDate());
		 * } else { this.cbbImpExpDate.setDate(null); }
		 */
		if (customsDeclaration.getImpExpType() != null) {
			cbbImpExpType.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(this.customsDeclaration.getImpExpType()),
					cbbImpExpType));
		} else {
			cbbImpExpType.setSelectedIndex(-1);
		}
		if (this.customsDeclaration.getIncidentalExpenses() != null) {
			this.tfIncidentalExpenses.setValue(this.customsDeclaration
					.getIncidentalExpenses());
		} else {
			this.tfIncidentalExpenses.setValue(Double.valueOf(0));
		}
		if (this.customsDeclaration.getIncidentalExpensesType() != null) {
			cbbIncidentalExpensesType.setSelectedIndex(ItemProperty
					.getIndexByCode(String.valueOf(this.customsDeclaration
							.getIncidentalExpensesType()),
							cbbIncidentalExpensesType));
		} else {
			cbbIncidentalExpensesType.setSelectedIndex(-1);
		}
		if (this.customsDeclaration.getInsurance() != null) {
			this.tfInsurance.setValue(this.customsDeclaration.getInsurance());
		} else {
			this.tfInsurance.setValue(Double.valueOf(0));
		}
		if (this.customsDeclaration.getInsuranceType() != null) {
			cbbInsuranceType.setSelectedIndex(ItemProperty.getIndexByCode(
					String.valueOf(this.customsDeclaration.getInsuranceType()),
					cbbInsuranceType));
		} else {
			cbbInsuranceType.setSelectedIndex(-1);
		}
		cbbLevyKind.setSelectedItem(this.customsDeclaration.getLevyKind());
		if (this.customsDeclaration.getLicense() != null) {
			this.tfLicense.setText(this.customsDeclaration.getLicense());
		} else {
			this.tfLicense.setText("");
		}
		if (this.customsDeclaration.getMemos() != null) {
			this.tfMemos.setText(this.customsDeclaration.getMemos());
		} else {
			this.tfMemos.setText("");
		}
		if (this.customsDeclaration.getPerTax() != null) {
			this.tfPerTax.setValue(this.customsDeclaration.getPerTax());
		} else {
			this.tfPerTax.setValue(null);
		}
		cbbPortOfLoadingOrUnloading.setSelectedItem(this.customsDeclaration
				.getPortOfLoadingOrUnloading());

		// 预录入号修改 by xxm
		if (this.customsDeclaration.getTempPreCode() != null
				&& !this.customsDeclaration.getTempPreCode().equals("")) {
			this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
					.getTempPreCode());
		} else {
			this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
					.getPreCustomsDeclarationCode());
		}
		if (this.customsDeclaration.getCustomsEnvelopBillNo() != null) {
			this.tfCustomsEnvelopBillNo.setText(this.customsDeclaration
					.getCustomsEnvelopBillNo());
		} else {
			this.tfCustomsEnvelopBillNo.setText("");
		}

		/*
		 * if (this.customsDeclaration.getPreCustomsDeclarationCode() != null) {
		 * this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
		 * .getPreCustomsDeclarationCode()); } else {
		 * this.tfPreCustomsDeclarationCode.setText(""); }
		 */
		cbbPredock.setSelectedItem(this.customsDeclaration.getPredock());
		if (this.customsDeclaration.getEmsHeadH2k() != null) {
			this.tfEmsNo.setText(this.customsDeclaration.getEmsHeadH2k());
		} else {
			this.tfEmsNo.setText("");
		}
		cbbTradeMode.setSelectedItem(this.customsDeclaration.getTradeMode());
		cbbTransac.setSelectedItem(this.customsDeclaration.getTransac());
		cbbTransferMode.setSelectedItem(this.customsDeclaration
				.getTransferMode());
		if (this.customsDeclaration.getUnificationCode() != null) {
			this.tfUnificationCode.setText(this.customsDeclaration
					.getUnificationCode());
		} else {
			this.tfUnificationCode.setText("");
		}
		cbbUses.setSelectedItem(this.customsDeclaration.getUses());
		cbbWrapType.setSelectedItem(this.customsDeclaration.getWrapType());
		if (this.customsDeclaration.getAllContainerNumLong() != null) {
			this.tfAllContainerNum.setText(this.customsDeclaration
					.getAllContainerNumLong());
		} else {
			this.tfAllContainerNum.setText("");
		}
		// this.tfContainerNum.setText(getAllConvertToContainerCode());
		if (this.customsDeclaration.getCustomser() != null) {
			this.jComboBox.setSelectedItem(this.customsDeclaration
					.getCustomser().toString());
		} else {
			this.jComboBox.setSelectedIndex(-1);
		}
		if (this.customsDeclaration.getTelephone() != null) {
			this.tfTelephone.setText(this.customsDeclaration.getTelephone());
		} else {
			this.tfTelephone.setText("");
		}
		if (this.customsDeclaration.getBondedWarehouse() != null) {
			this.tfBondedWarehouse.setText(this.customsDeclaration
					.getBondedWarehouse());
		} else {
			this.tfBondedWarehouse.setText("");
		}
		this.cbbDeclarationCompany.setSelectedItem(this.customsDeclaration
				.getDeclarationCompany());
		this.getTfInvoiceCode().setText(
				this.customsDeclaration.getInvoiceCode() == null ? ""
						: this.customsDeclaration.getInvoiceCode());
		this.getTfInspect().setText(
				this.customsDeclaration.getExtendField3() == null ? ""
						: this.customsDeclaration.getExtendField3());
		if (this.customsDeclaration.getRelativeManualNo() != null) {
			this.tfConnectNo.setText(this.customsDeclaration
					.getRelativeManualNo());
		} else {
			this.tfConnectNo.setText("");
		}
		if (this.customsDeclaration.getRelativeId() != null) {
			this.tfConnectDeclarationNo.setText(this.customsDeclaration
					.getRelativeId());
		} else {
			this.tfConnectDeclarationNo.setText("");
		}

		// 集成通信息
		tfTcsTaskId.setText(customsDeclaration.getTcsTaskId() == null ? ""
				: customsDeclaration.getTcsTaskId());

		if (CommonUtils.notEmpty(customsDeclaration.getTcsType())) {
			tfTcsType.setSelectedItem(new ItemProperty(customsDeclaration
					.getTcsType(), TCSCommonCode.getTcsType(customsDeclaration
					.getTcsType())));
		} else {
			tfTcsType.setSelectedIndex(0);
		}

		if (CommonUtils.notEmpty(customsDeclaration.getTcsEdiId())) {
			tfTcsEdiId.setSelectedItem(new ItemProperty(customsDeclaration
					.getTcsEdiId(), TCSCommonCode
					.getTcsEdiId(customsDeclaration.getTcsEdiId())));
		} else {
			tfTcsEdiId.setSelectedIndex(0);
		}

		if (CommonUtils.notEmpty(customsDeclaration.getTcsEntryType())) {
			tfTcsEntryType.setSelectedItem(new ItemProperty(customsDeclaration
					.getTcsEntryType(), TCSCommonCode
					.getTcsEntryType(customsDeclaration.getTcsEntryType())));
		} else {
			tfTcsEntryType.setSelectedIndex(0);
		}

		if (CommonUtils.notEmpty(customsDeclaration.getOperType())) {
			cbbOperType.setSelectedItem(new ItemProperty(customsDeclaration
					.getOperType(), TCSCommonCode
					.getOperTypeDesc(customsDeclaration.getOperType())));
		} else {
			// 报关单暂存 G 不能修改
			cbbOperType.setSelectedIndex(5);

		}
		// cbbOperType 不能修改
		cbbOperType.setEnabled(false);

		tfTcsNote.setText(customsDeclaration.getTcsNote() == null ? ""
				: customsDeclaration.getTcsNote());
		tfTcsResultMessage
				.setText(customsDeclaration.getTcsResultMessage() == null ? ""
						: customsDeclaration.getTcsResultMessage());
		tfTcsResultTime
				.setText(customsDeclaration.getTcsResultTime() == null ? ""
						: CommonUtils.getDate(
								customsDeclaration.getTcsResultTime(),
								"yyyy-MM-dd HH:mm:ss"));
		tfTcsSendTime.setText(customsDeclaration.getTcsSendTime() == null ? ""
				: CommonUtils.getDate(customsDeclaration.getTcsSendTime(),
						"yyyy-MM-dd HH:mm:ss"));

		if (customsDeclaration.getSupplmentType() == null) {
			cbbSupplmentType.setSelectedIndex(0);
		} else {
			cbbSupplmentType.setSelectedItem(new ItemProperty(
					customsDeclaration.getSupplmentType().toString(),
					SupplmentType.getSupplmentTypeDesc(customsDeclaration
							.getSupplmentType())));
		}
	}

	@SuppressWarnings("deprecation")
	protected void fillData() {
		if (this.customsDeclaration == null) {
			return;
		}
		this.customsDeclaration.setDeclaraCustomsBroker(tfDeclaraCustomsBroker
				.getText());
		if (this.cbbImpExpType.getSelectedIndex() > -1) {
			this.customsDeclaration.setImpExpType(Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode()));
		} else {
			this.customsDeclaration.setImpExpType(null);
		}
		// if (dataState == DataState.ADD) {
		// Integer serialNumber = this.baseEncAction
		// .getCustomsDeclarationSerialNumber(new Request(CommonVars
		// .getCurrUser()), ImpExpFlag.IMPORT,
		// customsDeclaration.getEmsHeadH2k());
		// this.tfCustomsDeclarationSerialNumber.setText(serialNumber
		// .toString());
		// this.customsDeclaration.setSerialNumber(serialNumber);
		// }
		/*
		 * if ((!this.tfPreCustomsDeclarationCode.getText().trim().equals(
		 * this.customsDeclaration.getPreCustomsDeclarationCode())) &&
		 * (this.customsDeclaration.getPreCustomsDeclarationCode() != null)) {
		 * tfPreCustomsDeclarationCode.setText(baseEncAction
		 * .getMaxPreCustomsDeclarationCode(new Request(CommonVars
		 * .getCurrUser()))); }
		 */

		String s = this.tfPreCustomsDeclarationCode.getText(); // 99978888024000005
		if (s != null && s.length() > 6) {
			this.customsDeclaration.setPreCustomsDeclarationCode(EncCommon
					.substring(s, s.length() - 6, s.length()));
			this.customsDeclaration.setTempPreCode(s);
		} else {
			this.customsDeclaration.setPreCustomsDeclarationCode(s);
		}

		this.customsDeclaration.setUnificationCode(this.tfUnificationCode
				.getText().trim());
		this.customsDeclaration.setCustoms((Customs) this.cbbCustoms
				.getSelectedItem());
		this.customsDeclaration
				.setCustomsDeclarationCode(this.tfCustomsDeclarationCode
						.getText().trim());
		// 原代码 2014/11/29
		// if (this.tfVoyageNo.getText() != null) {
		// this.customsDeclaration.setVoyageNo(this.tfVoyageNo.getText()
		// .trim());
		// }

		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// if (this.cbbImpExpDate.getDate() != null) {
		// this.customsDeclaration.setImpExpDate(java.sql.Date
		// .valueOf(dateFormat.format(this.cbbImpExpDate.getDate())));
		// } else {
		// this.customsDeclaration.setImpExpDate(null);
		// }
		// if (this.cbbDeclarationDate.getDate() != null) {
		// this.customsDeclaration.setDeclarationDate(java.sql.Date
		// .valueOf(dateFormat.format(this.cbbDeclarationDate
		// .getDate())));
		// } else {
		// this.customsDeclaration.setDeclarationDate(null);
		// }
		if (this.tfCreateDate.getDate() != null) {
			this.customsDeclaration.setCreateDate(this.tfCreateDate.getDate());
		} else {
			this.customsDeclaration.setCreateDate(null);
		}
		if (this.cbbImpExpDate.getDate() != null) {
			this.customsDeclaration.setImpExpDate(this.cbbImpExpDate.getDate());
		} else {
			this.customsDeclaration.setImpExpDate(null);
		}
		if (this.cbbDeclarationDate.getDate() != null) {
			this.customsDeclaration.setDeclarationDate(this.cbbDeclarationDate
					.getDate());
		} else {
			this.customsDeclaration.setDeclarationDate(null);
		}

		this.customsDeclaration.setTransferMode((Transf) this.cbbTransferMode
				.getSelectedItem());
		this.customsDeclaration.setConveyance(this.tfConveyance.getText()
				.trim());

		this.customsDeclaration.setBillOfLading(tfBillOfLading.getText());

		// this.customsDeclaration
		// .setDomesticConveyanceCode(tfdomesticConveyanceCode.getText());

		this.customsDeclaration.setTradeMode((Trade) this.cbbTradeMode
				.getSelectedItem());
		this.customsDeclaration.setLevyKind((LevyKind) this.cbbLevyKind
				.getSelectedItem());
		if (this.tfPerTax.getValue() != null) {
			this.customsDeclaration.setPerTax(Double.valueOf(this.tfPerTax
					.getValue().toString()));
		} else {
			this.customsDeclaration.setPerTax(null);
		}
		this.customsDeclaration.setLicense(this.tfLicense.getText().trim());
		this.customsDeclaration
				.setCountryOfLoadingOrUnloading((Country) this.cbbCountryOfLoadingOrUnloading
						.getSelectedItem());
		this.customsDeclaration
				.setDomesticDestinationOrSource((District) this.cbbDomesticDestinationOrSource
						.getSelectedItem());
		this.customsDeclaration
				.setPortOfLoadingOrUnloading((PortLin) this.cbbPortOfLoadingOrUnloading
						.getSelectedItem());
		this.customsDeclaration.setAuthorizeFile(this.tfAuthorizeFile.getText()
				.trim());
		this.customsDeclaration.setContract(this.tfContract.getText().trim());
		this.customsDeclaration.setTransac((Transac) this.cbbTransac
				.getSelectedItem());
		if (this.cbbFreightageType.getSelectedItem() != null) {
			this.customsDeclaration.setFreightageType(Integer
					.valueOf(((ItemProperty) this.cbbFreightageType
							.getSelectedItem()).getCode()));
		} else {
			this.customsDeclaration.setFreightageType(null);
		}
		if (this.tfFreightage.getValue() != null) {
			this.customsDeclaration.setFreightage(Double
					.valueOf(this.tfFreightage.getValue().toString()));
		} else {
			this.customsDeclaration.setFreightage(null);
		}
		if (this.cbbInsuranceType.getSelectedItem() != null) {
			this.customsDeclaration.setInsuranceType(Integer
					.valueOf(((ItemProperty) this.cbbInsuranceType
							.getSelectedItem()).getCode()));
		} else {
			this.customsDeclaration.setInsuranceType(null);
		}
		if (this.tfInsurance.getValue() != null) {
			this.customsDeclaration.setInsurance(Double
					.valueOf(this.tfInsurance.getValue().toString()));
		} else {
			this.customsDeclaration.setInsurance(null);
		}

		if (this.cbbIncidentalExpensesType.getSelectedItem() != null) {
			this.customsDeclaration.setIncidentalExpensesType(Integer
					.valueOf(((ItemProperty) this.cbbIncidentalExpensesType
							.getSelectedItem()).getCode()));
		} else {
			this.customsDeclaration.setIncidentalExpensesType(null);
		}
		if (this.tfIncidentalExpenses.getValue() != null) {
			this.customsDeclaration.setIncidentalExpenses(Double
					.valueOf(this.tfIncidentalExpenses.getValue().toString()));
		} else {
			this.customsDeclaration.setIncidentalExpenses(null);
		}

		if (this.tfCommodityNum.getValue() != null) {
			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
					"######");
			try {
				this.customsDeclaration.setCommodityNum(Integer
						.valueOf(decimalFormat.format(this.tfCommodityNum
								.getValue())));

			} catch (NumberFormatException numberFormatEx) {
			}
		} else {
			this.customsDeclaration.setCommodityNum(null);
		}
		this.customsDeclaration.setWrapType((Wrap) this.cbbWrapType
				.getSelectedItem());

		if (this.tfGrossWeight.getValue() != null) {
			this.customsDeclaration.setGrossWeight(Double
					.valueOf(this.tfGrossWeight.getText().toString()));
		} else {
			this.customsDeclaration.setGrossWeight(null);
		}
		if (this.tfNetWeight.getValue() != null) {
			this.customsDeclaration.setNetWeight(Double
					.valueOf(this.tfNetWeight.getText().toString()));
		} else {
			this.customsDeclaration.setNetWeight(null);
		}
		this.customsDeclaration.setCurrency((Curr) this.cbbCurrency
				.getSelectedItem());
		this.customsDeclaration.setFeeCurr((Curr) this.cbbFeeCurr
				.getSelectedItem());
		this.customsDeclaration.setInsurCurr((Curr) this.cbbInsurCurr
				.getSelectedItem());
		this.customsDeclaration.setOtherCurr((Curr) this.cbbOtherCurr
				.getSelectedItem());
		if (this.tfExchangeRate.getValue() != null) {
			this.customsDeclaration.setExchangeRate(Double
					.valueOf(this.tfExchangeRate.getValue().toString()));
		} else {
			this.customsDeclaration.setExchangeRate(null);
		}
		this.customsDeclaration
				.setDeclarationCustoms((Customs) this.cbbDeclarationCustoms
						.getSelectedItem());
		this.customsDeclaration.setMemos(this.tfMemos.getText());
		this.customsDeclaration.setPredock((PreDock) this.getCbbPredock()
				.getSelectedItem());
		this.customsDeclaration.setCustomer((ScmCoc) this.cbbCustomer
				.getSelectedItem());
		this.customsDeclaration.setAllContainerNum(this.tfAllContainerNum
				.getText());
		this.customsDeclaration.setContainerNum(this.tfContainerNum.getText());
		if (this.jComboBox.getSelectedItem() != null) {
			this.customsDeclaration.setCustomser(this.jComboBox
					.getSelectedItem().toString());
		} else {
			this.customsDeclaration.setCustomser(null);
		}
		this.customsDeclaration.setTelephone(this.tfTelephone.getText());
		this.customsDeclaration.setUses((Uses) this.cbbUses.getSelectedItem());
		this.customsDeclaration.setAttachedBill(this.tfAttachedBill.getText());
		this.customsDeclaration.setBondedWarehouse(this.tfBondedWarehouse
				.getText());
		this.customsDeclaration
				.setDeclarationCompany((Company) this.cbbDeclarationCompany
						.getSelectedItem());
		this.customsDeclaration
				.setCustomsEnvelopBillNo(this.tfCustomsEnvelopBillNo.getText());

		this.customsDeclaration.setUnificationCode(tfUnificationCode.getText());// 统一编号
		tfUnificationCode.setEditable(false);
		this.customsDeclaration.setInvoiceCode(getTfInvoiceCode().getText());
		this.customsDeclaration.setExtendField3(this.getTfInspect().getText());
		this.customsDeclaration.setRelativeManualNo(this.tfConnectNo.getText()
				.trim());
		this.customsDeclaration.setRelativeId(this.tfConnectDeclarationNo
				.getText().trim());

		// 集成通信息
		customsDeclaration.setTcsType(((ItemProperty) tfTcsType
				.getSelectedItem()).getCode());
		customsDeclaration.setTcsEdiId(((ItemProperty) tfTcsEdiId
				.getSelectedItem()).getCode());
		customsDeclaration.setTcsEntryType(((ItemProperty) (tfTcsEntryType
				.getSelectedItem())).getCode());
		customsDeclaration.setOperType(((ItemProperty) (cbbOperType
				.getSelectedItem())).getCode());
		customsDeclaration.setTcsNote(tfTcsNote.getText());
		customsDeclaration.setTcsResultMessage(tfTcsResultMessage.getText());
		if (tfTcsResultTime.getText() == null
				|| tfTcsResultTime.getText().equals("")) {
			customsDeclaration.setTcsResultTime(null);
		} else {
			customsDeclaration.setTcsResultTime(new Date(tfTcsResultTime
					.getText().replace('-', '/')));
		}

		if (tfTcsSendTime.getText() == null
				|| "".equals(tfTcsSendTime.getText())) {
			customsDeclaration.setTcsSendTime(null);
		} else {
			customsDeclaration.setTcsSendTime(new Date(tfTcsSendTime.getText()
					.replace('-', '/')));
		}
		customsDeclaration.setTcsTaskId(tfTcsTaskId.getText());

		customsDeclaration.setSupplmentType(Integer
				.valueOf(((ItemProperty) cbbSupplmentType.getSelectedItem())
						.getCode()));
	}

	protected Brief getBreif(String breifCode) {
		List list = customBaseAction.findBrief("code", breifCode);
		if (list.size() < 1) {
			return null;
		} else {
			return (Brief) list.get(0);
		}
	}

	protected void setState() {
		boolean isEffective = false;
		int index = jTabbedPane.getSelectedIndex();
		boolean isSend = false;
		if (customsDeclaration != null) {
			isEffective = customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue();
			isSend = customsDeclaration.getIsSend() == null ? false
					: customsDeclaration.getIsSend().booleanValue();
		}
		btnAdd.setEnabled(dataState == DataState.BROWSE // jTabbedPane.getSelectedIndex()
				// == 0 && dataState ==
				// DataState.BROWSE ||
				&& index == 1 && !isEffective);
		btnEdit.setEnabled(dataState == DataState.BROWSE && !isEffective
				&& !isSend);
		jButton.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton1.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton7.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton3.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton5.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton6.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnSortChcked.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnDelete.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnSave.setEnabled(dataState != DataState.BROWSE && !isEffective);
		btnCancel.setEnabled(dataState != DataState.BROWSE && !isEffective);
		btnEffect.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnUnreel.setEnabled(dataState == DataState.BROWSE && isEffective);
		btnSplit.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnDelete.setVisible(index == 1);
		// btnEffect.setVisible(index == 0);
		btnUnreel.setVisible(index == 0);
		btnSave.setVisible(index == 0 || index == 2);
		btnCancel.setVisible(index == 0);
		if (index == 1) {
			return;
		}
		// 如下写主要为了提高效率
		if (dataState != DataState.BROWSE) {
			if (commInfoModel == null) {
				initTable();
			}
			this.cbbImpExpType.setEnabled(dataState != DataState.BROWSE
					&& commInfoModel.getList().size() < 1);
		} else {
			this.cbbImpExpType.setEnabled(dataState != DataState.BROWSE);
		}
		/*
		 * this.tfPreCustomsDeclarationCode .setEditable(dataState !=
		 * DataState.BROWSE);
		 */
		this.cbbCustoms.setEnabled(dataState != DataState.BROWSE);
		this.tfCustomsDeclarationCode
				.setEditable(dataState != DataState.BROWSE);
		this.tfVoyageNo.setEditable(dataState != DataState.BROWSE);
		this.cbbImpExpDate.setEnabled(dataState != DataState.BROWSE);
		this.cbbDeclarationDate.setEnabled(dataState != DataState.BROWSE);
		this.cbbTransferMode.setEnabled(dataState != DataState.BROWSE);
		this.tfConveyance.setEditable(dataState != DataState.BROWSE);
		// this.cbbBillOfLading.setEnabled(dataState != DataState.BROWSE);
		this.tfBillOfLading.setEditable(dataState != DataState.BROWSE);

		this.tfdomesticConveyanceCode
				.setEditable(dataState != DataState.BROWSE);

		this.cbbTradeMode.setEnabled(dataState != DataState.BROWSE);
		this.cbbLevyKind.setEnabled(dataState != DataState.BROWSE);
		this.tfPerTax.setEditable(dataState != DataState.BROWSE);
		this.tfLicense.setEditable(dataState != DataState.BROWSE);
		this.cbbCountryOfLoadingOrUnloading
				.setEnabled(dataState != DataState.BROWSE);
		this.cbbDomesticDestinationOrSource
				.setEnabled(dataState != DataState.BROWSE);
		this.cbbPortOfLoadingOrUnloading
				.setEnabled(dataState != DataState.BROWSE);
		this.tfAuthorizeFile.setEditable(dataState != DataState.BROWSE);
		this.cbbTransac.setEnabled(dataState != DataState.BROWSE);
		tfContainerNum.setEditable(dataState != DataState.BROWSE);
		this.cbbFreightageType.setEnabled(dataState != DataState.BROWSE);
		this.tfFreightage.setEditable(dataState != DataState.BROWSE);
		this.cbbInsuranceType.setEnabled(dataState != DataState.BROWSE);
		this.tfInsurance.setEditable(dataState != DataState.BROWSE);
		this.cbbIncidentalExpensesType
				.setEnabled(dataState != DataState.BROWSE);
		this.tfIncidentalExpenses.setEditable(dataState != DataState.BROWSE);
		this.cbbWrapType.setEnabled(dataState != DataState.BROWSE);
		this.tfCommodityNum.setEditable(dataState != DataState.BROWSE);
		this.tfGrossWeight.setEditable(dataState != DataState.BROWSE);
		this.tfInvoiceCode.setEditable(dataState != DataState.BROWSE);
		this.tfInspect.setEditable(dataState != DataState.BROWSE);
		this.tfNetWeight.setEditable(dataState != DataState.BROWSE);
		this.btnAttachedBill.setEnabled(dataState != DataState.BROWSE);
		this.cbbUses.setEnabled(dataState != DataState.BROWSE);
		this.cbbCurrency.setEnabled(dataState != DataState.BROWSE);
		this.cbbFeeCurr.setEnabled(dataState != DataState.BROWSE);
		this.cbbInsurCurr.setEnabled(dataState != DataState.BROWSE);
		this.cbbOtherCurr.setEnabled(dataState != DataState.BROWSE);
		this.cbbCustomer.setEnabled(dataState != DataState.BROWSE);
		this.tfExchangeRate.setEditable(dataState != DataState.BROWSE);
		this.cbbDeclarationCustoms.setEnabled(dataState != DataState.BROWSE);
		this.btnMemo.setEnabled(dataState != DataState.BROWSE);
		this.cbbPredock.setEnabled(dataState != DataState.BROWSE);
		this.btnContainer.setEnabled(dataState != DataState.BROWSE);
		// this.jTabbedPane.setEnabled(dataState == DataState.BROWSE);
		this.jComboBox.setEnabled(dataState != DataState.BROWSE);
		this.tfTelephone.setEditable(dataState != DataState.BROWSE);
		this.btnPreCustomsDeclarationCode
				.setEnabled(dataState != DataState.BROWSE);
		this.btnTransferCustoms.setEnabled(dataState != DataState.BROWSE);
		this.btnPrint.setEnabled(dataState == DataState.BROWSE);
		this.jButton4.setEnabled(dataState == DataState.BROWSE);
		this.tfBondedWarehouse.setEditable(dataState != DataState.BROWSE);
		this.cbbDeclarationCompany.setEnabled(dataState != DataState.BROWSE);
		boolean isEdit = cbbImpExpType.getSelectedItem() != null
				&& Integer.valueOf(
						((ItemProperty) cbbImpExpType.getSelectedItem())
								.getCode()).equals(
						ImpExpType.TRANSFER_FACTORY_IMPORT);
		this.tfCustomsEnvelopBillNo.setEditable(dataState != DataState.BROWSE
				&& isEdit);
		this.btnFptAppNo.setEnabled(dataState != DataState.BROWSE && isEdit);
		this.btnAuthorizeFile.setEnabled(dataState != DataState.BROWSE);
		this.jButton9.setEnabled(dataState != DataState.BROWSE);
		this.tfConnectNo.setEditable(dataState != DataState.BROWSE);
		this.tfConnectDeclarationNo.setEditable(dataState != DataState.BROWSE);
		this.tfPreCustomsDeclarationCode
				.setEditable(dataState != DataState.BROWSE
						&& isManualPreCode != null && isManualPreCode);
		int type = CommonVars.getCustomsType(customsDeclaration);
		jButton3.setVisible(type == ProjectType.BCUS);
		jButton2.setVisible(type == ProjectType.BCUS);
		jButton5.setVisible(type == ProjectType.BCUS);
		jButton7.setVisible(type == ProjectType.DZSC);
		// tfContract.setEditable(dataState != DataState.BROWSE
		// && type == ProjectType.BCUS);
		tfContract.setEditable(dataState != DataState.BROWSE);
	}

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	protected boolean checkData() {

		if (this.customsDeclaration == null) {

			return false;

		}

		if (!this.baseEncAction
				.checkCustDeclCommInfoSeqNumIsCorrespondsNameAndSpec(
						new Request(CommonVars.getCurrUser(), true),
						EncCommon.isMaterial(customsDeclaration.getImpExpType()),
						customsDeclaration)) {

			if (JOptionPane.showConfirmDialog(this,
					"商品明细的项号与手册通关备案的品名规格不一致，如果忽略此问题则按“是”，否则按“否”继续", "提示！",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {

				return false;

			}
		}

		String[] msg = this.baseEncAction
				.checkCustDeclCommInfoLegalAmountBySeqNumIsZero(new Request(
						CommonVars.getCurrUser(), false), customsDeclaration);

		if (msg[0] != null && !"".equals(msg[0])) {
			if (JOptionPane.showConfirmDialog(this, msg[0]
					+ "\n如果忽略此问题则按“是”，否则按“否”继续", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		if (msg[1] != null && !"".equals(msg[1])) {
			JOptionPane.showMessageDialog(this, msg[1], "提示", 0);
			return false;
		}
		if (!this.baseEncAction.checkCustDeclCommInfoSerialNumber(new Request(
				CommonVars.getCurrUser(), true), customsDeclaration)) {
			if (JOptionPane.showConfirmDialog(this,
					"商品明细的序号不连续，如果忽略此问题则按“是”，否则按“否”继续", "提示！",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		fillData();
		if (CommonVars.commonCheckNull(DgBaseImportCustomsDeclaration.this,
				customsDeclaration)) {
			return false;
		}
		if (!(customsDeclaration instanceof BcsCustomsDeclaration)) {
			List list = commInfoModel.getList();
			for (int i = 0; i < list.size(); i++) {
				if (commonInofCheckNull(DgBaseImportCustomsDeclaration.this,
						(BaseCustomsDeclarationCommInfo) list.get(i))) {
					return false;
				}
			}
		}

		return EncCommon.checkImportCustomsDeclarationData(customsDeclaration,
				this);
	}

	/**
	 * 检查报关单商品信息
	 * 
	 * @param parentComponent
	 * @param obj
	 * @return
	 */
	public boolean commonInofCheckNull(Component parentComponent,
			BaseCustomsDeclarationCommInfo obj) {
		SystemAction systemAction = (SystemAction) CommonVars
				.getApplicationContext().getBean("systemAction");
		String info = systemAction.commonCheckNull(
				new Request(CommonVars.getCurrUser()), obj);
		if (info != null) {
			JOptionPane.showMessageDialog(parentComponent,
					"报关单商品项号为 " + obj.getSerialNumber() + " 的商品" + info, "提示",
					2);
			return true;
		}
		return false;
	}

	protected List getDataSource() {
		List list = new ArrayList();
		if (customsDeclaration != null) {
			if (customsDeclaration.getId() != null) {
				list = baseEncAction.findCustomsDeclarationCommInfo(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
			}
		}
		return list;
	}

	/**
	 * 初始化报关单商品信息
	 */
	protected void initTable() {
		List list = getDataSource();
		commInfoModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("项号", "serialNumber", 60,
								Integer.class));
						list.add(addColumn("商品序号", "commSerialNo", 80,
								Integer.class));
						if (customsDeclaration != null
								&& (!EncCommon.isMaterial(customsDeclaration
										.getImpExpType()))
								&& (projectType == ProjectType.BCUS)) {
							list.add(addColumn("版本", "version", 40));
						}
						list.add(addColumn("商品编号", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 100));
						list.add(addColumn("数量", "commAmount", 60));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("法定数量", "firstAmount", 80));// legalAmount
						// 修改前用的
						list.add(addColumn("法定单位", "legalUnit.name", 80));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("原产国", "country.name", 80));
						list.add(addColumn("单价", "commUnitPrice", 60));
						list.add(addColumn("总价", "commTotalPrice", 60));
						list.add(addColumn("第二法定数量", "secondAmount", 100));// secondLegalAmount
						// 修改前用的
						list.add(addColumn("第二法定单位", "secondLegalUnit.name",
								100));
						list.add(addColumn("用途", "uses.name", 60));
						list.add(addColumn("净重", "netWeight", 60));
						list.add(addColumn("毛重", "grossWeight", 60));
						list.add(addColumn("件(箱)数", "pieces", 60));
						list.add(addColumn("箱号", "boxNo", 100));
						list.add(addColumn("事业部", "projectDept.name", 100));
						if (customsDeclaration != null
								&& (!EncCommon.isMaterial(customsDeclaration
										.getImpExpType()))
								&& (projectType == ProjectType.BCS)) {
							list.add(addColumn("加工费总价", "workUsd", 80));
						}
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// 页脚
		commInfoModel.clearFooterTypeInfo();
		commInfoModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		if (customsDeclaration != null
				&& (!EncCommon.isMaterial(customsDeclaration.getImpExpType()))
				&& (projectType == ProjectType.BCUS)) {
			commInfoModel.addFooterTypeInfo(new TableFooterType(7,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(9,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(14,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(15,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(18,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(19,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(20,
					TableFooterType.SUM, ""));
		} else {
			commInfoModel.addFooterTypeInfo(new TableFooterType(6,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(8,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(13,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(14,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(17,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(18,
					TableFooterType.SUM, ""));
			commInfoModel.addFooterTypeInfo(new TableFooterType(19,
					TableFooterType.SUM, ""));
			if (customsDeclaration != null
					&& (!EncCommon.isMaterial(customsDeclaration
							.getImpExpType()))
					&& (projectType == ProjectType.BCS)) {
				commInfoModel.addFooterTypeInfo(new TableFooterType(22,
						TableFooterType.SUM, ""));
			}
		}

	}

	private boolean checkindex() {
		String str = "";
		boolean is = true;
		if (commInfoModel == null) {
			initTable();
		}
		int q = commInfoModel.getRowCount();
		if (q == 0) {
			if (jTabbedPane.getSelectedIndex() == 1) {
				JOptionPane.showMessageDialog(this, "该表中没有数据！", "提示！", 0);
				return true;
			}
		}
		for (int i = 0; i < q; i++) {
			BaseCustomsDeclarationCommInfo cddi = (BaseCustomsDeclarationCommInfo) commInfoModel
					.getValueAt(i);
			if (cddi != null) {
				if (cddi.getCommAmount() == null) {
					str += "数量, ";
					is = false;
				}
				// Double commamount = cddi.getCommAmount();// 数量
				if (cddi.getCommUnitPrice() == null) {
					str += "单价, ";
					is = false;
				}
				// Double communitprice = cddi.getCommUnitPrice();// 单价
				if (cddi.getCommTotalPrice() == null) {
					str += "金额, ";
					is = false;
				}
				// Double commtotalprice = cddi.getCommTotalPrice(); // 总价
				try {
					// String countryname = cddi.getCountry().getName();
				} catch (Exception e) {
					str += "原产国, ";
					is = false;
				}
				// String countryname = cddi.getCountry().getName();// 原产国
				try {
					// String usename = cddi.getUses().getName();
				} catch (Exception e) {
					str += "用途, ";
					is = false;
				}
				// String usename = cddi.getUses().getName();// 用途
				if (cddi.getFirstAmount() == null) {
					str += "法定数量, ";
					is = false;
				}

			} else if (jTabbedPane.getSelectedIndex() == 1) {
				JOptionPane.showMessageDialog(this, "该表中并没数据！", "提示！", 0);
				return true;
			}
			if (!is) {
				if (jTabbedPane.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(this, "商品信息页面中： 第" + (i + 1)
							+ "笔数据中" + str + " 没有填写！", "提示！", 0);
					return true;
				}
				if (jTabbedPane.getSelectedIndex() == 1) {
					JOptionPane.showMessageDialog(this, "该页面： 第" + (i + 1)
							+ "笔数据中" + str + " 没有填写！", "提示！", 0);
					return true;
				}

			} else
				continue;
		}

		return !is;
	}

	/**
	 * This method initializes tfPerTax
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfPerTax() {
		if (tfPerTax == null) {
			tfPerTax = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPerTax.setBounds(406, 139, 108, 20);
			tfPerTax.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfPerTax;
	}

	/**
	 * This method initializes tfFreightage
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfFreightage() {
		if (tfFreightage == null) {
			tfFreightage = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfFreightage.setBounds(337, 217, 87, 20);
			tfFreightage.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfFreightage;
	}

	/**
	 * This method initializes tfInsurance
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfInsurance() {
		if (tfInsurance == null) {
			tfInsurance = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfInsurance.setBounds(184, 241, 87, 19);
			tfInsurance.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfInsurance;
	}

	/**
	 * This method initializes tfIncidentalExpenses
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfIncidentalExpenses() {
		if (tfIncidentalExpenses == null) {
			tfIncidentalExpenses = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfIncidentalExpenses.setBounds(572, 241, 113, 19);
			tfIncidentalExpenses
					.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfIncidentalExpenses;
	}

	/**
	 * This method initializes tfCommodityNum
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfCommodityNum() {
		if (tfCommodityNum == null) {
			tfCommodityNum = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfCommodityNum.setBounds(74, 266, 108, 19);
			tfCommodityNum.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCommodityNum;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(43, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String customsSavaCheck = manualDecleareAction.getBpara(
							new Request(CommonVars.getCurrUser(), true),
							BcusParameter.CUSTOMS_SAVA_CHECK);
					if (customsSavaCheck != null
							&& "1".equals(customsSavaCheck)) {
						checkCustoms();
					} else {
						if (projectType == ProjectType.BCUS) {
							impCustomsAuthorityAction.saveCustoms(new Request(
									CommonVars.getCurrUser()));
						} else if (projectType == ProjectType.DZSC) {
							dZSCImpCustomsAuthorityAction
									.saveCustoms(new Request(CommonVars
											.getCurrUser()));
						} else if (projectType == ProjectType.BCS) {
							bCSImpCustomsAuthorityAction
									.saveCustoms(new Request(CommonVars
											.getCurrUser()));
						}
						saveCustom();
						fillData();
						showData();
					}

				}
			});
		}
		return btnSave;
	}

	protected boolean saveCustom() {
		BaseCustomsDeclaration oldcustomsDeclaration = null;
		if (customsDeclaration != null) {
			oldcustomsDeclaration = (BaseCustomsDeclaration) this.customsDeclaration
					.clone();
		}
		fillData();
		if (customsDeclaration.getImpExpType() == null) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"进口类型不能为空", "提示", JOptionPane.YES_NO_OPTION);
			return false;
		}
		customsDeclaration.setIsEmsCav(false);
		customsDeclaration.setIsCheck(false);
		if (!saveData()) {
			if (oldcustomsDeclaration != null) {
				customsDeclaration = (BaseCustomsDeclaration) oldcustomsDeclaration
						.clone();

				customsDeclarationModel.updateRow(customsDeclaration);
			}
			return false;
		}
		return true;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.setPreferredSize(new Dimension(43, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (customsDeclarationModel.getCurrentRow() != null) {
						customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
								.getCurrentRow();
					}
					// else {
					// customsDeclaration = null;
					// }
					showData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes defaultFormatterFactory
	 * 
	 * @return javax.swing.text.DefaultFormatterFactory
	 */
	protected DefaultFormatterFactory getDefaultFormatterFactory() {
		if (defaultFormatterFactory == null) {
			defaultFormatterFactory = new DefaultFormatterFactory();
			defaultFormatterFactory.setDisplayFormatter(getNumberFormatter());
			defaultFormatterFactory.setEditFormatter(getNumberFormatter());
		}
		return defaultFormatterFactory;
	}

	/**
	 * This method initializes numberFormatter
	 * 
	 * @return javax.swing.text.NumberFormatter
	 */
	protected NumberFormatter getNumberFormatter() {
		if (numberFormatter == null) {
			numberFormatter = new NumberFormatter();
		}
		return numberFormatter;
	}

	/**
	 * This method initializes btnTransferCustoms
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnTransferCustoms() {
		if (btnTransferCustoms == null) {
			btnTransferCustoms = new JButton();
			btnTransferCustoms.setBounds(574, 34, 111, 23);
			btnTransferCustoms.setText("转关附加");
			btnTransferCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tfConveyance.getText().equals("")) {
								JOptionPane.showMessageDialog(null,
										"运输工具栏目中不能为空!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (!tfConveyance.getText().substring(0, 1).trim()
									.equals("@")) {
								JOptionPane.showMessageDialog(null,
										"运输工具栏目中第一个字符不是 \"@\" !!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (cbbTransferMode.getSelectedItem() == null) {// customsDeclaration.getTransferMode()
								JOptionPane.showMessageDialog(null,
										"使用'转关附加'时'运输方式'不能为空", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgTransferCustomsConveyance dg = new DgTransferCustomsConveyance();
							dg.setBaseEncAction(baseEncAction);
							dg.setCustomsDeclaration(customsDeclaration);
							// 如果是汽车运输或江海运输
							if (cbbTransferMode.getSelectedItem() != null) {

								Transf transf = customBaseAction.findTransf();
								if (customsDeclaration
										.getDomesticTransferMode() != null) {
									dg.setTransf(customsDeclaration
											.getDomesticTransferMode());
								} else {
									dg.setTransf(transf);// 汽车运输
								}

								Calendar calendar = cbbImpExpDate.getCalendar();
								dg.setImportDate(String.valueOf(calendar
										.get(Calendar.YEAR))
										+ "-"
										+ String.valueOf(calendar
												.get(Calendar.MONTH) + 1)
										+ "-"
										+ String.valueOf(calendar
												.get(Calendar.DAY_OF_MONTH)));
								dg.setNumberPlate(tfBillOfLading.getText());
								dg.setTruckTransfer(true);
							}
							dg.setVisible(true);
						}
					});
		}
		return btnTransferCustoms;
	}

	/**
	 * This method initializes btnPreCustomsDeclarationCode
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnPreCustomsDeclarationCode() {
		if (btnPreCustomsDeclarationCode == null) {
			btnPreCustomsDeclarationCode = new JButton();
			btnPreCustomsDeclarationCode.setBounds(321, 35, 111, 23);
			btnPreCustomsDeclarationCode.setText("产生预录入号");
			btnPreCustomsDeclarationCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(null,
									"要产生预录入号吗?", "确认", 0) == 0) {
								// 统一编号不为空时，修改预录入编号要提示并清空
								if (tfUnificationCode.getText() != null
										&& !tfUnificationCode.getText().equals(
												"")) {
									if (JOptionPane
											.showConfirmDialog(
													DgBaseImportCustomsDeclaration.this,
													"生成新的预录入号时是否同时清空统一编号?\n注：清空统一编号后发送，海关确认为这是一份从未申报过的报关单",
													"确认", 0) == 0) {
										customsDeclaration
												.setUnificationCode(null);
										tfUnificationCode.setText("");
									}
								}

								if (tfPreCustomsDeclarationCode.getText() != null
										&& tfPreCustomsDeclarationCode
												.getText().length() >= 6) {
									String s = tfPreCustomsDeclarationCode
											.getText(); // 000001
									sedi = EncCommon.substring(s, 0,
											s.length() - 6); //
									tfPreCustomsDeclarationCode.setText(sedi
											+ baseEncAction
													.getMaxPreCustomsDeclarationCode(new Request(
															CommonVars
																	.getCurrUser())));

								} else {
									tfPreCustomsDeclarationCode.setText(baseEncAction
											.getMaxPreCustomsDeclarationCode(new Request(
													CommonVars.getCurrUser())));
								}
							}
						}
					});
		}
		return btnPreCustomsDeclarationCode;
	}

	/**
	 * This method initializes tfEmsCode
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(235, 10, 85, 21);
			tfEmsNo.setEditable(false);
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes tfCompanyEmsNo
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCompanyEmsNo() {
		if (tfCompanyEmsNo == null) {
			tfCompanyEmsNo = new JTextField();
			tfCompanyEmsNo.setBounds(389, 10, 91, 21);
			tfCompanyEmsNo.setEditable(false);
		}
		return tfCompanyEmsNo;
	}

	/**
	 * This method initializes tfCustomsDeclarationSerialNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCustomsDeclarationSerialNumber() {
		if (tfCustomsDeclarationSerialNumber == null) {
			tfCustomsDeclarationSerialNumber = new JTextField();
			tfCustomsDeclarationSerialNumber.setBounds(563, 11, 122, 21);
			tfCustomsDeclarationSerialNumber.setEditable(false);
		}
		return tfCustomsDeclarationSerialNumber;
	}

	/**
	 * This method initializes btnCheckData
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCheckData() {
		if (btnCheckData == null) {
			btnCheckData = new JButton();
			btnCheckData.setText("检查");
			btnCheckData.setPreferredSize(new Dimension(43, 30));
			btnCheckData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkCustoms();
				}
			});
		}
		return btnCheckData;
	}

	/**
	 * 检查表体净重、毛重与表头净重、毛重是否相等
	 * 
	 * @return
	 */
	private boolean checkSaveWeight() {

		List companyOtherList = customBaseAction.findCompanyOther(new Request(
				CommonVars.getCurrUser()));

		CompanyOther companyOther = null;

		if (companyOtherList != null && companyOtherList.size() > 0) {

			companyOther = (CompanyOther) companyOtherList.get(0);

		}

		/*
		 * 检查关封号
		 * 
		 * 如果参数设置需要检查关封，则检查关封；检查范围：料件转厂（1）、转厂出口（5）
		 */
		// 是否保存关封
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
				|| customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT) {
			Boolean isSaveCustomsEnvelop = companyOther
					.getIsSaveCustomsEnvelopBillNo();
			if (isSaveCustomsEnvelop != null && isSaveCustomsEnvelop == true) {
				if (CommonUtils.isEmpty(tfCustomsEnvelopBillNo.getText())) {
					JOptionPane.showMessageDialog(this, "报关单表头结转申请单号不能为空!",
							"提示!", 1);
					return true;
				}
			}
		}
		Boolean isCheckWeight = false;
		if (companyOther.getIsCheckWeight() != null) {
			isCheckWeight = companyOther.getIsCheckWeight();
		}
		if (isCheckWeight) {
			// 表头总毛重、总净重
			Double headGrossWeight = 0.0;
			Double headNetWeight = 0.0;

			if (null != tfGrossWeight.getValue())
				headGrossWeight = Double.valueOf(tfGrossWeight.getValue()
						.toString());

			if (null != tfNetWeight.getValue()) {
				headNetWeight = Double.valueOf(tfNetWeight.getValue()
						.toString());
			}

			// 表体毛重、净重
			Double GrossWeight = 0.0;
			Double NetWeight = 0.0;
			List list1 = commInfoModel.getList();
			for (int i = 0; i < list1.size(); i++) {
				BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo = (BaseCustomsDeclarationCommInfo) list1
						.get(i);
				if (null != baseCustomsDeclarationCommInfo.getGrossWeight()) {
					GrossWeight += baseCustomsDeclarationCommInfo
							.getGrossWeight();
				}
				if (null != baseCustomsDeclarationCommInfo.getNetWeight()) {
					NetWeight += baseCustomsDeclarationCommInfo.getNetWeight();
				}
			}
			// 判断是否一致
			if (!GrossWeight.equals(headGrossWeight)) {
				JOptionPane.showMessageDialog(this, "报关单表头总毛重与表体毛重相加不一致!",
						"提示!", 1);
				return true;
			}
			if (!NetWeight.equals(headNetWeight)) {
				JOptionPane.showMessageDialog(this, "报关单表头总净重与表体净重相加不一致!",
						"提示!", 1);
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查 报关单 是否填写了必须的栏位
	 */
	private void checkCustoms() {

		// 检查 保存重量
		if (checkSaveWeight()) {
			return;
		}

		// 检查其他
		if (checkOther()) {
			return;
		}

		if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {

			if (!"".equals(checkCurrentRemainAmount())) {

				String str = checkCurrentRemainAmount();

				if (!"".equals(str)) {

					str += ",\n确定要继续吗?";

					if (JOptionPane.showConfirmDialog(
							DgBaseImportCustomsDeclaration.this, str, "严重警告",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {

						return;

					}
				}
			}

		} else {

			if (!"".equals(checkCurrentRemainAmount())) {

				String str = checkCurrentRemainAmount();

				if (!"".equals(str)) {

					JOptionPane.showMessageDialog(
							DgBaseImportCustomsDeclaration.this, str, "提示",
							JOptionPane.INFORMATION_MESSAGE);

					return;

				}
			}
		}

		if (checkData() && (!checkindex())) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"数据检查成功，没有错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
			customsDeclaration.setIsCheck(true);
			String containerNum = tfContainerNum.getText().trim();
			String note = tfMemos.getText().trim();
			Integer impExpType = cbbImpExpType.getSelectedItem() != null ? Integer
					.parseInt(((ItemProperty) cbbImpExpType.getSelectedItem())
							.getCode()) : null;
			if (impExpType != null
					&& (impExpType == ImpExpType.TRANSFER_FACTORY_IMPORT || impExpType == ImpExpType.TRANSFER_FACTORY_EXPORT)) {
			} else {
				if (containerNum == null || "".equals(containerNum)
						|| "0".equals(containerNum)) {
					if (note != null && !"".equals(note)
							&& !note.startsWith("#1")) {
						if (JOptionPane.showConfirmDialog(
								DgBaseImportCustomsDeclaration.this,
								"系统提示：集装箱数为0\n是否在说明字符串前加'#1'", "提示",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
							tfMemos.setText("#1 " + note);
							customsDeclaration.setMemos("#1 " + note);
						}
					}
				}
			}
			saveData();
			showData();
		}
		// if (jTabbedPane.getSelectedIndex() == 0) {
		//
		//
		// } else if (jTabbedPane.getSelectedIndex() == 1) {
		// if (!checkindex()) {
		// JOptionPane.showMessageDialog(null, "数据检查成功，没有错误！", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// }
		// }
	}

	/**
	 * 检查商品明细与当前余量差额
	 * 
	 * @return
	 */
	protected String checkCurrentRemainAmount() {
		String str = "";
		return str;
	}

	/**
	 * 1、进出口报关单、特殊报关单：增加报关单逻辑检查功能； 总价 小数位 不能超过 2 位，包装种类的代码 只能 是 1 位；
	 * 申报数量、法定数量的小数位不能大于5位， 关联报关单号不能小于大于 18 位、关联手册号 不能 小于大于 12位； 商品编码不能小于 10 位。
	 * 
	 * @return
	 */
	private boolean checkOther() {

		if (tfConveyance.getText() != null
				&& tfConveyance.getText().startsWith("K")) {

			if (tfTcsEdiId.getSelectedIndex() == 1) {

				return false;

			}

			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"运输工具为‘K’开头表示报关单为：跨境快速通关报关单，报关单标志必须修改为‘普通报关’", "提示",
					JOptionPane.YES_NO_OPTION);

			return true;

		}

		// 备注最前面必须是转自或转至，并判断备注内容中，字符B或C或E后面必须11位数字。
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_IMPORT
				&& customsDeclaration.getMemos() != null
				&& !customsDeclaration.getMemos().equals("")) {

			if (!RegexUtil.checkMatch(customsDeclaration.getMemos(),
					"^转(自|至)(B|E|C|H)\\d{11}(\\D+.*$|$)")) {

				JOptionPane.showMessageDialog(
						DgBaseImportCustomsDeclaration.this,
						"备注最前面必须是转自或转至，字符B或C或E或H后面必须11位数字", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return true;
			}
		}

		List<BaseCustomsDeclarationCommInfo> list = commInfoModel.getList();

		for (BaseCustomsDeclarationCommInfo info : list) {

			// 总价小数位不能超过2位
			if (!RegexUtil.checkMaxAccuracy(
					info.getCommTotalPrice() == null ? "" : CommonUtils
							.formatDoubleByDigit(info.getCommTotalPrice(), 6),
					2)) {

				JOptionPane.showMessageDialog(
						DgBaseImportCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "总价小数位不能超过2位", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return true;
			}

			// 申报数量的小数位不能大于5位
			if (!RegexUtil.checkMaxAccuracy(info.getCommAmount() == null ? ""
					: CommonUtils.formatDoubleByDigit(info.getCommAmount(), 6),
					5)) {

				JOptionPane.showMessageDialog(
						DgBaseImportCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "申报数量的小数位不能大于5位",
						"提示", JOptionPane.INFORMATION_MESSAGE);

				return true;

			}

			// 法定数量的小数位不能大于5位
			if (!RegexUtil.checkMaxAccuracy(
					info.getFirstAmount() == null ? "" : CommonUtils
							.formatDoubleByDigit(info.getFirstAmount(), 6), 5)) {

				JOptionPane.showMessageDialog(
						DgBaseImportCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "法定数量的小数位不能大于5位",
						"提示", JOptionPane.INFORMATION_MESSAGE);

				return true;

			}

		}

		return false;
	}

	/**
	 * This method initializes btnEffect
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnEffect() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.setPreferredSize(new Dimension(43, 30));
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.effectCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction
								.effectCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.effectCustoms(new Request(
								CommonVars.getCurrUser()));
					}
					saveCustom();
					effectCustomsDeclaration();
				}
			});
		}
		return btnEffect;
	}

	/**
	 * 生效时检查项目
	 */
	protected void effectCustomsDeclaration() {
		if (checkSaveWeight()) {
			return;
		}
		if (DgBaseImportCustomsDeclaration.this.tfCustomsDeclarationCode
				.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"报关单号不能为空！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (DgBaseImportCustomsDeclaration.this.tfCustomsDeclarationCode
				.getText().trim().length() != 18) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"报关单号必须为18位！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		// 京瓷光学插件使用
		if (DgBaseImportCustomsDeclaration.this.tfInspect.getText().trim()
				.length() > 20) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"报检预录入号必须小于20位！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (!checkData()) {
			return;
		}
		// 检查报关单商品数量与当前余量
		if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
			if (!"".equals(checkCurrentRemainAmount())) {
				String str = checkCurrentRemainAmount();
				if (!"".equals(str)) {
					str += ",\n确定要继续吗?";
					if (JOptionPane.showConfirmDialog(
							DgBaseImportCustomsDeclaration.this, str, "严重警告",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
						return;
					}
				}
			}
		} else {
			if (!"".equals(checkCurrentRemainAmount())) {
				String str = checkCurrentRemainAmount();
				if (!"".equals(str)) {
					JOptionPane.showMessageDialog(
							DgBaseImportCustomsDeclaration.this, str, "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		}
		if (commInfoModel == null) {
			initTable();
		}
		if (commInfoModel.getList().size() < 1) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"无商品信息资料所以不能生效！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (tfInvoiceCode.getText() == null
				|| tfInvoiceCode.getText().equals("")) {
			if (JOptionPane.showConfirmDialog(
					DgBaseImportCustomsDeclaration.this, "发票号为空，你确定要生效吗？",
					"提示！", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
				return;
		}
		if (JOptionPane.showConfirmDialog(DgBaseImportCustomsDeclaration.this,
				"是否确定生效数据!!!", "提示", 0) != 0) {
			return;
		}
		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.effectCustomsDeclaration(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		customsDeclarationModel.updateRow(customsDeclaration);
		setState();
	}

	/**
	 * This method initializes btnUnreel
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnUnreel() {
		if (btnUnreel == null) {
			btnUnreel = new JButton();
			btnUnreel.setText("回卷");
			btnUnreel.setPreferredSize(new Dimension(43, 30));
			btnUnreel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.unreelCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction
								.unreelCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.unreelCustoms(new Request(
								CommonVars.getCurrUser()));
					}
					unreelCustomsDeclaration();
				}
			});
		}
		return btnUnreel;
	}

	FecavAction fecavAction = (FecavAction) CommonVars.getApplicationContext()
			.getBean("fecavAction"); // @jve:decl-index=0:

	private JLabel jLabel48 = null;

	private JTextField tfCustomsEnvelopBillNo = null;

	private JButton btnFptAppNo = null;

	private JButton jButton2 = null;

	private JComboBox jComboBox = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JButton btnCaleReturnImport = null;

	private JButton jButton7 = null;

	public JLabel jLabel1 = null;

	private JTextField tfInvoiceCode = null;

	public JLabel jLabel44 = null;

	private JTextField tfInspect = null;

	private JLabel jLabel49 = null;

	private JCalendarComboBox tfCreateDate = null;

	private JPopupMenu jPopupMenu = null; // 套打
											// @jve:decl-index=0:visual-constraint="773,90"

	private JMenuItem jMenuItem = null;

	private JMenuItem jMenuItem1 = null;

	private JMenuItem jMenuItem2 = null;

	private JMenuItem jMenuItemHEms = null;

	private JMenuItem jMenuItem3 = null;

	private JMenuItem jMenuItem4 = null;

	private JMenuItem jMenuItem5 = null;

	private JMenuItem jMenuItem6 = null;

	private JMenuItem jMenuItem7 = null;

	private JMenuItem jMenuItem8 = null;

	private JMenuItem jMenuItem9 = null;

	private JMenuItem jMenuItem10 = null;

	private JMenuItem jMenuItem11 = null;

	private JMenuItem jMenuItem12 = null;

	private JMenuItem jMenuItem13 = null;

	private JMenuItem jmItemBoxNoComplex = null;

	private JMenuItem jmItemBoxNo = null;

	private JMenuItem miPrintReport = null;

	private JMenuItem miBarcodePrint = null;

	private JMenuItem jMenuItemInvoice = null;

	private JMenuItem miZhangXiangDanMerger = null;

	private JMenuItem miInvoiceMerger = null;

	private JButton btnSortChcked = null;

	private JLabel jLabel50 = null;

	// 运费币制
	private JComboBox cbbFeeCurr = null;

	private JLabel jLabel501 = null;

	/**
	 * 杂费币制
	 */
	private JComboBox cbbOtherCurr = null;

	private JLabel jLabel502 = null;

	// 保费币制
	private JComboBox cbbInsurCurr = null;

	private JButton btnSplit = null;

	private JLabel jLabel471 = null;

	private JLabel jLabel472 = null;

	private JTextField tfConnectNo = null;

	private JTextField tfConnectDeclarationNo = null;

	private JPanel pnTcs = null;

	private JLabel lbTcsTaskId = null;

	private JTextField tfTcsTaskId = null;

	private JLabel lbTcsSendTime = null;

	private JTextField tfTcsSendTime = null;

	private JLabel lbTcsEntryType = null;

	private JComboBox tfTcsEntryType = null;

	private JLabel lbTcsEntryTransitType = null;

	private JComboBox tfTcsEdiId = null;

	private JLabel lbTcsDeclareProperty = null;

	private JComboBox tfTcsType = null;

	private JLabel lbTcsNote = null;

	private JTextArea tfTcsNote = null;

	private JLabel lbtcsResultTime = null;

	private JTextField tfTcsResultTime = null;

	private JLabel lbTcsResultMessage = null;

	private JTextArea tfTcsResultMessage = null;

	private JScrollPane spnTcsResultMessage = null;

	private JLabel jLabel51 = null;

	private JTextField tfdomesticConveyanceCode = null;

	private JLabel lbSupplmentType = null;

	private JComboBox cbbSupplmentType = null;

	private JLabel lbOperType = null;

	private JComboBox cbbOperType = null;
	private JLabel label;
	private JTextField tfVoyageNo;

	protected void unreelCustomsDeclaration() {
		if (this.fecavAction.findStrikeImpCustomsDeclarationCount(new Request(
				CommonVars.getCurrUser()), customsDeclaration.getId()) > 0) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"外汇核销单中已经填入了白单号码！！ 不能回卷！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (customsDeclaration.getIsEmsCav() != null
				&& customsDeclaration.getIsEmsCav()) {
			if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(
					DgBaseImportCustomsDeclaration.this, "该单已经数据报核通过了，是否回卷!!!",
					"提示", JOptionPane.OK_CANCEL_OPTION)) {
				return;
			}

		}
		if (JOptionPane.showConfirmDialog(DgBaseImportCustomsDeclaration.this,
				"是否确定回卷数据!!!", "提示", 0) != 0) {
			return;
		}
		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.unreelCustomsDeclaration(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		customsDeclarationModel.updateRow(customsDeclaration);
		setState();
	}

	/**
	 * This method initializes cbbPaper
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JTextField getJComboBox() {
		if (tfDeclaraCustomsBroker == null) {
			tfDeclaraCustomsBroker = new JTextField();
			tfDeclaraCustomsBroker.setBounds(74, 10, 108, 21);
			tfDeclaraCustomsBroker.setEditable(false);
		}
		return tfDeclaraCustomsBroker;
	}

	/**
	 * This method initializes cbbDeclarationCompany
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getJComboBox2() {
		if (cbbDeclarationCompany == null) {
			cbbDeclarationCompany = new JComboBox();
			// cbbDeclarationCompany.addKeyListener(new FocusActionListner(
			// getCbbCustomer()));
			cbbDeclarationCompany.setBounds(74, 313, 204, 19);
		}
		return cbbDeclarationCompany;
	}

	/**
	 * This method initializes tfBondedWarehouse
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfBondedWarehouse() {
		if (tfBondedWarehouse == null) {
			tfBondedWarehouse = new JTextField();
			tfBondedWarehouse.setBounds(74, 385, 93, 23);
		}
		return tfBondedWarehouse;
	}

	/**
	 * 保存数据
	 */
	protected boolean saveData() {
		if (customsDeclaration == null) {
			return false;
		}
		CompanyOther other = CommonVars.getOther();
		// 检查重复值
		if (EncCommon.savecheck(projectType, customsDeclaration,
				DgBaseImportCustomsDeclaration.this, other)) {
			return false;
		}
		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.saveCustomsDeclaration(new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		if (dataState == DataState.ADD) {
			baseEncAction.saveCustomsDeclarationContainer(new Request(
					CommonVars.getCurrUser()), customsDeclaration, containers);
			customsDeclarationModel.addRow(customsDeclaration);
		} else {
			customsDeclarationModel.updateRow(customsDeclaration);
		}
		dataState = DataState.BROWSE;
		setState();
		return true;
	}

	/**
	 * 得到随附单据名称
	 */
	public String getattachedBillName(String attachedbillCode) {
		String allName = "";
		if (customBaseAction == null || attachedbillCode == null
				|| attachedbillCode.equals("")) {
			return "";
		}
		char[] code = attachedbillCode.toCharArray();
		CompanyOther other = CommonVars.getOther();
		for (int i = 0; i < code.length; i++) {
			String name = customBaseAction.getLicenseDouName(String
					.valueOf(code[i]));
			if (other != null && other.getPrintAll()) {
				allName = allName + " " + name;
			} else {
				allName = allName + " "
						+ (name == null ? "" : name.substring(0, 4));
			}
		}
		return allName;
	}

	/**
	 * 新建立一个报关单
	 * 
	 * @return
	 */
	protected abstract BaseCustomsDeclaration newCustomsDeclaration();

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			lbCommInfoMoney = new JLabel();
			lbCommInfoMoney.setText("12345678.12");
			lbCommInfoMoney.setForeground(Color.red);
			jLabel43 = new JLabel();
			jLabel43.setText("商品金额:");
			jLabel43.setForeground(Color.red);
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJButton7());
			jToolBar1.add(getJButton3());
			jToolBar1.add(getJButton2());
			jToolBar1.add(getJButton5());
			jToolBar1.add(getBtnSortChcked());
			jToolBar1.add(getJButton());
			jToolBar1.add(getJButton6());
			jToolBar1.add(getBtnCaleReturnImport());

			jToolBar1.add(jLabel43);
			jToolBar1.add(lbCommInfoMoney);
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("自定义排序");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.definedOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction.definedOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.definedOrder(new Request(
								CommonVars.getCurrUser()));
					}
					List list = getDataSource();
					Vector vec = new Vector();
					for (int i = 0; i < list.size(); i++) {
						vec.addElement((BaseCustomsDeclarationCommInfo) list
								.get(i));
					}
					DgSort dg = new DgSort();
					dg.setBaseEncAction(baseEncAction);
					dg.setList(vec);
					dg.setVisible(true);
					initTable();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	protected JFooterTable getJTable() {
		if (jTable == null) {
			jTable = new JFooterTable();
			jTable.setRowHeight(25);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (commInfoModel == null) {
							return;
						}
						if (commInfoModel.getList().size() < 1) {
							return;
						}
						if (commInfoModel.getCurrentRow() == null) {
							return;
						}
						// DgBaseImportCustomsDeclarationCommInfo dg = new
						// DgBaseImportCustomsDeclarationCommInfo();
						// dg
						// .setEffective(customsDeclaration.getEffective() ==
						// null ? false
						// : customsDeclaration.getEffective()
						// .booleanValue());
						// dg.setTableModel(customsDeclarationCommInfoModel);
						// dg.setVisible(true);
						editCustomsDeclarationData();
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("申请单转报关单");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.billToBgD(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction.billToBgD(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.billToBgD(new Request(
								CommonVars.getCurrUser()));
					}
					makeDzbaCustomsDeclaration();
					// 进口报关单不需要初始化客户
					infoforint();// 数据取整
					getPiceAndWeight();
					getAttachedBill();
				}
			});
		}
		return jButton1;
	}

	/**
	 * @return Returns the projectType.
	 */
	public int getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("内部商品");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.innerCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction
								.innerCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction
								.innerCommodity(new Request(CommonVars
										.getCurrUser()));
					}
					addFromMateriel();
					infoforint();// 数据取整
					getPiceAndWeight();
					initTable();
					showCommInfoMoney();
					getAttachedBill();
				}
			});
		}
		return jButton3;
	}

	/**
	 * 修改件，净重，毛重
	 * 
	 */
	protected void getPiceAndWeight() {
		CompanyOther other = CommonVars.getOther();

		if (other == null || (other != null && other.getIsAutoWeight() == null)
				|| (other != null && !other.getIsAutoWeight())) {
			return;
		}
		if (customsDeclaration.getEffective()) {
			return;
		}
		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.getPiceAndWeight(new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		tfGrossWeight.setValue(customsDeclaration.getGrossWeight());
		tfNetWeight.setValue(customsDeclaration.getNetWeight());
		tfCommodityNum.setValue(customsDeclaration.getCommodityNum());
		customsDeclarationModel.updateRow(customsDeclaration);
	}

	/**
	 * 生成报关单
	 */
	private void makeDzbaCustomsDeclaration() {
		if (customsDeclaration == null || customsDeclaration.getId() == null) {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"请先保存报关单", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		Integer impExpType = customsDeclaration.getImpExpType();
		switch (projectType) {
		case ProjectType.BCUS:
			MakeCusomsDeclarationParam para = new MakeCusomsDeclarationParam();
			para.setImpExpType(impExpType);
			para.setMaterielType(EncCommon
					.getMaterielTypeByBillType(impExpType));
			para.setImpExpType(impExpType);
			para.setCustomsDeclaration(true);
			para.setFromCustomsDeclaretion(true);
			DgMakeApplyToCustoms dialog = new DgMakeApplyToCustoms();
			dialog.setPara(para);
			dialog.setCustoms((CustomsDeclaration) this.customsDeclaration);
			dialog.setVisible(true);

			break;
		case ProjectType.BCS:
			DgMakeBcsCustomsDeclaration bcsDialog = new DgMakeBcsCustomsDeclaration();
			bcsDialog.setImpExpType(impExpType);
			bcsDialog.setMaterielProductType(EncCommon
					.getMaterielTypeByBillType(impExpType));
			bcsDialog.setImportGoods(EncCommon.isImport(impExpType));
			bcsDialog.setInRequestBill(false);
			bcsDialog
					.setBcsCustomsDeclaration((BcsCustomsDeclaration) this.customsDeclaration);
			bcsDialog.setVisible(true);
			break;
		case ProjectType.DZSC:
			DgMakeDzscCustomsDeclaration dzscDialog = new DgMakeDzscCustomsDeclaration();
			dzscDialog.setImpExpType(impExpType);
			dzscDialog.setMaterielProductType(EncCommon
					.getMaterielTypeByBillType(impExpType));
			dzscDialog.setImportGoods(EncCommon.isImport(impExpType));
			dzscDialog.setInRequestBill(false);
			dzscDialog
					.setDzscCustomsDeclaration((DzscCustomsDeclaration) this.customsDeclaration);
			dzscDialog.setVisible(true);
			break;
		default:
			break;
		}
		initTable();
	}

	protected abstract void addFromMateriel();

	protected abstract void lookFromMateriel();

	/**
	 * This method initializes tfCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillNo() {
		if (tfCustomsEnvelopBillNo == null) {
			tfCustomsEnvelopBillNo = new JTextField();
			tfCustomsEnvelopBillNo.setBounds(new Rectangle(239, 386, 117, 24));
		}
		return tfCustomsEnvelopBillNo;
	}

	/**
	 * This method initializes btnFptAppNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFptAppNo() {
		if (btnFptAppNo == null) {
			btnFptAppNo = new JButton();
			btnFptAppNo.setBounds(new Rectangle(356, 386, 24, 23));
			btnFptAppNo.setText("...");
			btnFptAppNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbImpExpType.getSelectedItem() == null) {
						return;
					}
					// Date declarationDate = cbbDeclarationDate.getDate();
					ScmCoc scmCoc = (ScmCoc) cbbCustomer.getSelectedItem();
					int impExpType = Integer
							.parseInt(((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode());
					// FptAppItem fptAppItem = (FptAppItem)
					// FptQuery.getInstance()
					// .findFptAppItemByCustomsDeclaration(impExpType,
					// tfEmsNo.getText().trim(), scmCoc);
					// if (fptAppItem != null) {
					// tfCustomsEnvelopBillNo.setText(fptAppItem
					// .getFptAppHead().getAppNo());
					//
					// }
					TempTransFactInfo tempTransFactInfo = (TempTransFactInfo) EncCommon
							.findTempTransFactInfo(impExpType, tfEmsNo
									.getText().trim(), scmCoc);
					if (tempTransFactInfo != null) {
						tfCustomsEnvelopBillNo.setText(tempTransFactInfo
								.getBillNo());
					}
				}
			});
		}
		return btnFptAppNo;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查看");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commInfoModel == null) {
						return;
					}
					if (commInfoModel.getList().size() < 1) {
						return;
					}
					if (commInfoModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgBaseImportCustomsDeclaration.this,
								"请选择你要查看的资料", "提示", 0);
						return;
					}
					impCustomsAuthorityAction.lookCommodity(new Request(
							CommonVars.getCurrUser()));
					lookFromMateriel();
					infoforint();// 数据取整
					getPiceAndWeight();
					initTable();
					showCommInfoMoney();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox3() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(42, 3, 64, 25);
			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String name = jComboBox.getSelectedItem() == null ? null
							: jComboBox.getSelectedItem().toString();
					String tel = materialManageAction.findCustomsUserTel(
							new Request(CommonVars.getCurrUser()), name);
					tfTelephone.setText(tel);
				}
			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("非套打");
			jButton4.setPreferredSize(new Dimension(48, 30));
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isTaoda = false;
					Component comp = (Component) e.getSource();
					getJPopupMenu().show(comp, 0, comp.getHeight());
				}
			});
		}
		return jButton4;
	}

	private Map getParaList() {
		List list = new ArrayList();
		Map prop = new HashMap();
		if (customsDeclaration != null) {
			list.add(customsDeclaration);
		} else {
			JOptionPane.showMessageDialog(DgBaseImportCustomsDeclaration.this,
					"当前无数据可列印！", "提示", JOptionPane.YES_NO_OPTION);
			return prop;
		}
		CustomReportDataSource ds = new CustomReportDataSource(list);
		prop.put("CustomReportDataSource", ds);
		if (commInfoModel == null) {
			initTable();
		}
		String attachedBillName = "";
		attachedBillName = getattachedBillName(customsDeclaration
				.getAttachedBill());
		prop.put("attachedBillName", attachedBillName);

		prop.put("CustomsDeclaration", customsDeclaration);
		prop.put("commInfoModel", commInfoModel);
		prop.put("projectType", projectType);
		prop.put("isTaoda", isTaoda);
		prop.put("baseEncAction", baseEncAction);
		return prop;

	}

	/**
	 * 打印报表
	 * 
	 */

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("数据取整");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.dataFetchInt(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction.dataFetchInt(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.dataFetchInt(new Request(
								CommonVars.getCurrUser()));
					}
					List list = getDataSource();
					baseEncAction.customsInfoForMatInt(
							new Request(CommonVars.getCurrUser()), list);
					initTable();
					showCommInfoMoney();
				}
			});
		}
		return jButton5;
	}

	private void infoforint() {
		if (projectType == ProjectType.BCUS) {
			String isforint = manualDecleareAction.getBpara(new Request(
					CommonVars.getCurrUser(), true),
					BcusParameter.CUSTOMS_FORINT);
			if (isforint != null && "1".equals(isforint)) {
				List list = getDataSource();
				baseEncAction.customsInfoForMatInt(
						new Request(CommonVars.getCurrUser()), list);
			}
		}
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("自动排序");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						impCustomsAuthorityAction.autoOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCImpCustomsAuthorityAction.autoOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSImpCustomsAuthorityAction.autoOrder(new Request(
								CommonVars.getCurrUser()));
					}
					if (JOptionPane.showConfirmDialog(
							DgBaseImportCustomsDeclaration.this,
							"是否确定要按商品序号排序？", "提示", 0) == 0) {

						new exectAutoOrder().start();
					}
				}
			});
		}
		return jButton6;
	}

	private JButton getBtnCaleReturnImport() {
		if (btnCaleReturnImport == null) {
			btnCaleReturnImport = new JButton();
			btnCaleReturnImport.setText("统计未复进数据");
			btnCaleReturnImport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgCaleReturnImport dg = new DgCaleReturnImport();
							if (getEmsHead() != null) {
								dg.setEmsNo(getEmsHead().getEmsNo());
								dg.setProjectType(projectType);
							}
							dg.setVisible(true);
							List list = dg.getReturnList();
							if (list == null) {
								return;
							}
							List listParameter = systemAction
									.findCompanyOther(new Request(CommonVars
											.getCurrUser(), true));
							if (listParameter.size() > 0
									&& listParameter.get(0) != null) {
								CompanyOther companyOther = (CompanyOther) listParameter
										.get(0);
								Boolean isAllowBGDDetailExceed20 = companyOther
										.getIsAllowBGDDetailExceed20();
								if (isAllowBGDDetailExceed20 == null
										|| !isAllowBGDDetailExceed20) {
									int getN = list.size();
									int currN = commInfoModel.getRowCount();
									if ((getN + currN) > 20) {
										JOptionPane
												.showMessageDialog(
														DgBaseImportCustomsDeclaration.this,
														"当前商品信息还可新增"
																+ String.valueOf(20 - currN)
																+ "项!", "提示", 2);
										return;
									}
								}
							}
							baseEncAction.saveCustomsDeclarationCommInfo(
									new Request(CommonVars.getCurrUser()),
									true, list, customsDeclaration);
							getPiceAndWeight();// 更新主表
							initTable();
							// commInfoModel.setTableEndSelectRow();
							int index = commInfoModel.getList().size()
									- list.size();
							if (index >= 0) {
								commInfoModel.setSelectRow(index);
							}
							editCustomsDeclarationData1();
							showCommInfoMoney();
							getAttachedBill();// 更新表头的随附单据
						}
					});
		}
		return btnCaleReturnImport;
	}

	class exectAutoOrder extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgBaseImportCustomsDeclaration.this);
				CommonProgress.setMessage("系统正在排序数据，请稍后...");
				baseEncAction.commInfoAutoOrder(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgBaseImportCustomsDeclaration.this,
						"排序数据失败：" + e.getMessage(), "提示", 2);
			} finally {
				initTable();
			}
		}
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("清单转报关单");
		}
		return jButton7;
	}

	private void showCommInfoMoney() {
		CompanyOther other = CommonVars.getOther();
		List list = commInfoModel.getList();
		double totalPrice = 0.0;
		for (int i = 0; i < list.size(); i++) {
			BaseCustomsDeclarationCommInfo commInfo = (BaseCustomsDeclarationCommInfo) list
					.get(i);
			totalPrice += (commInfo.getCommTotalPrice() == null ? 0.0
					: commInfo.getCommTotalPrice());
		}
		if (other != null) {
			lbCommInfoMoney
					.setText(String.valueOf(CommonUtils.formatDoubleByDigit(
							totalPrice, other.getCustomTotalNum())));
		} else {
			lbCommInfoMoney.setText(String.valueOf(CommonUtils
					.formatDoubleByDigit(totalPrice, 4)));
		}
	}

	private void setRate() {
		if (getCbbCurrency().getSelectedItem() != null) {
			Curr obj = (Curr) getCbbCurrency().getSelectedItem();
			Double rate = baseEncAction.getCurrRateByCurr(new Request(
					CommonVars.getCurrUser()), obj, getCbbDeclarationDate()
					.getDate(), getTfEmsNo().getText());
			tfExchangeRate.setValue(rate);
		}
	}

	/**
	 * This method initializes tfInvoiceCode
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getTfInvoiceCode() {
		if (tfInvoiceCode == null) {
			tfInvoiceCode = new JTextField();
			tfInvoiceCode.setVisible(true);// 插件用到
			tfInvoiceCode.setBounds(new Rectangle(416, 386, 89, 24));
			// tfInvoiceCode.addFocusListener(new java.awt.event.FocusAdapter()
			// {
			// public void focusLost(java.awt.event.FocusEvent e) {
			// if (tfInvoiceCode.getText() != null
			// && !tfInvoiceCode.getText().trim().equals("")
			// && tfInvoiceCode.getText().trim().length() != 8) {
			// JOptionPane.showMessageDialog(
			// DgBaseImportCustomsDeclaration.this,
			// "发票号应该为8位,请重新填写！", "提示！",
			// JOptionPane.WARNING_MESSAGE);
			// tfInvoiceCode.setText("");
			// }
			// }
			// });
		}
		return tfInvoiceCode;
	}

	/**
	 * This method initializes tfInspect
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getTfInspect() {
		if (tfInspect == null) {
			tfInspect = new JTextField();
			tfInspect.setVisible(false);// 插件用到
			tfInspect.setBounds(new Rectangle(573, 388, 112, 22));
		}
		return tfInspect;
	}

	/**
	 * This method initializes tfCreaterDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfCreateDate() {
		if (tfCreateDate == null) {
			tfCreateDate = new JCalendarComboBox();
			tfCreateDate.setBounds(new Rectangle(567, 313, 118, 19));
			tfCreateDate.setDate(new Date());
			tfCreateDate.setEnabled(false);
		}
		return tfCreateDate;
	}

	/**
	 * This method initializes jPopupMenu 套打
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.add(getJMenuItem1());
			jPopupMenu.add(getJMenuItem2());
			jPopupMenu.add(getJMenuItem3());
			jPopupMenu.add(getJMenuItemInvoice());
			jPopupMenu.add(getJMenuItem4());
			jPopupMenu.add(getJMenuItem5());
			jPopupMenu.add(getJMenuItem12());
			jPopupMenu.add(getJmItemZhangXiangDan());
			jPopupMenu.add(getJmItemZhangXiangDanComplex());
			jPopupMenu.add(getJmItemZhangXiangDanBoxNo());
			jPopupMenu.add(getMiZhangXiangDanMerger());
			jPopupMenu.add(getMiInvoiceMerger());
			jPopupMenu.add(getJMenuItem7());
			jPopupMenu.add(getJMenuItem8());
			jPopupMenu.add(getJMenuItem9());
			jPopupMenu.add(getJMenuItem10());
			jPopupMenu.add(getJMenuItem11());
			jPopupMenu.add(getMiPrintReport());
			jPopupMenu.add(getMiBarcodePrint());
			jPopupMenu.add(getJMenuItem6());

		}
		return jPopupMenu;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("报关单");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint0();
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("报关单附页");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint1();
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes jMenuItem2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem2() {
		if (jMenuItem2 == null) {
			jMenuItem2 = new JMenuItem();
			jMenuItem2.setText("报关单集装箱附页");
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInOutPrint20();
				}
			});
		}
		return jMenuItem2;
	}

	/**
	 * This method initializes jMenuItem3
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem3() {
		if (jMenuItem3 == null) {
			jMenuItem3 = new JMenuItem();
			jMenuItem3.setText("进口发票");
			jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint2();
				}

			});
		}
		return jMenuItem3;
	}

	/**
	 * This method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem4() {
		if (jMenuItem4 == null) {
			jMenuItem4 = new JMenuItem();
			jMenuItem4.setText("进口发票(COMMERCIAL INVOICE)");
			jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList())
							.doInPrintInvoice2();
				}
			});
		}
		return jMenuItem4;
	}

	/**
	 * This method initializes jMenuItem4
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemInvoice() {
		if (jMenuItemInvoice == null) {
			jMenuItemInvoice = new JMenuItem();
			jMenuItemInvoice.setText("进口发票(有法定单位)");
			jMenuItemInvoice
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							CustomsPrinter.getInstance(getParaList())
									.doInPrintInvoice3();
						}
					});
		}
		return jMenuItemInvoice;
	}

	/**
	 * This method initializes jMenuItem5
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem();
			jMenuItem5.setText("发票(太平)");
			jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint3();
				}
			});
		}
		return jMenuItem5;
	}

	/**
	 * This method initializes jMenuItem6
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem6() {
		if (jMenuItem6 == null) {
			jMenuItem6 = new JMenuItem();
			jMenuItem6.setText("装箱单");
			jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint4();
				}
			});
		}
		return jMenuItem6;
	}

	/**
	 * This method initializes jMenuItem7
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem7() {
		if (jMenuItem7 == null) {
			jMenuItem7 = new JMenuItem();
			jMenuItem7.setText("内地海关及香港海关陆路出/进境载货清单");
			jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint6(false,
							ImpExpFlag.IMPORT);
				}
			});
		}
		return jMenuItem7;
	}

	/**
	 * This method initializes jMenuItem8
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem8() {
		if (jMenuItem8 == null) {
			jMenuItem8 = new JMenuItem();
			jMenuItem8.setText("内地海关及香港海关陆路出/进境载货清单+(附表)");
			jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint6(true,
							ImpExpFlag.IMPORT);
				}
			});
		}
		return jMenuItem8;
	}

	/**
	 * This method initializes jMenuItem9
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem9() {
		if (jMenuItem9 == null) {
			jMenuItem9 = new JMenuItem();
			jMenuItem9.setText("内地海关及香港海关陆路出/进境载货清单(香港)");
			jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint8(false,
							ImpExpFlag.IMPORT);
				}
			});
		}
		return jMenuItem9;
	}

	/**
	 * This method initializes jMenuItem10
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem10() {
		if (jMenuItem10 == null) {
			jMenuItem10 = new JMenuItem();
			jMenuItem10.setText("内地海关及香港海关陆路出/进境载货清单(香港)+(附表)");
			jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint8(true,
							ImpExpFlag.IMPORT);
				}
			});
		}
		return jMenuItem10;
	}

	/**
	 * This method initializes jMenuItem11
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem11() {
		if (jMenuItem11 == null) {
			jMenuItem11 = new JMenuItem();
			jMenuItem11.setText("内地海关及香港海关陆路出/进境载货清单(附表)");
			jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint9();
				}
			});
		}
		return jMenuItem11;
	}

	/**
	 * This method initializes jMenuItem12
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem12() {
		if (jMenuItem12 == null) {
			jMenuItem12 = new JMenuItem();
			jMenuItem12.setText("装箱单(明细无包装种类)");
			jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInPrint10();
				}
			});
		}
		return jMenuItem12;
	}

	/**
	 * This method initializes jMenuItem13
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJmItemZhangXiangDan() {
		if (jMenuItem13 == null) {
			jMenuItem13 = new JMenuItem();
			jMenuItem13.setText("装箱单(包装种类在明细)");
			jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList())
							.doInPrintZhangXiangDan();
				}
			});
		}
		return jMenuItem13;
	}

	/**
	 * This method initializes jMenuItem13 装箱单包装种类在明细商品编码
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJmItemZhangXiangDanComplex() {
		if (jmItemBoxNoComplex == null) {
			jmItemBoxNoComplex = new JMenuItem();
			jmItemBoxNoComplex.setText("装箱单(包装种类在明细及商品编码)");
			jmItemBoxNoComplex
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							CustomsPrinter.getInstance(getParaList())
									.doInPrintZhangXiangDanComplexOrBoxNo(true);
						}
					});
		}
		return jmItemBoxNoComplex;
	}

	/**
	 * This method initializes jMenuItem13 装箱单包装种类在明细箱号
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJmItemZhangXiangDanBoxNo() {
		if (jmItemBoxNo == null) {
			jmItemBoxNo = new JMenuItem();
			jmItemBoxNo.setText("装箱单(包装种类在明细及箱号)");
			jmItemBoxNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList())
							.doInPrintZhangXiangDanComplexOrBoxNo(false);
				}
			});
		}
		return jmItemBoxNo;
	}

	@SuppressWarnings("unused")
	private String fillJList() {
		List list = CustomBaseList.getInstance().getLicensedocus();
		for (int i = 0; i < list.size(); i++) {
			LicenseDocu obj = (LicenseDocu) list.get(i);
		}
		return null;
	}

	/**
	 * 打印中华人民共和国保税区进境货物备案清单报表
	 */
	public void printReport() {
		try {
			List list = customsDeclarationModel.getCurrentRows();
			BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list.get(0);
			// System.out.println("bcd=" + bcd);
			// Integer s = bcd.getImpExpFlag();
			// System.out.println(customsDeclaration.getAttachedBill());
			String attachedBillName = getattachedBillName(customsDeclaration
					.getAttachedBill());

			// System.out.println("attachedBillName"+attachedBillName);
			// String attachedBillName = (String) prop.get("attachedBillName");
			List list2 = baseEncAction.findCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), bcd);
			int size = list2.size();
			System.out.println("size=" + size);
			InputStream subReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingSubReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			CustomReportDataSource ds = new CustomReportDataSource(list2);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("size", size);

			parameters.put("isOverprint", isTaoda);
			parameters.put("subReport", subReport);
			parameters.put("subReportDataSource", ds);
			parameters.put("attachedBillName", attachedBillName);
			InputStream masterReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingBills.jasper");
			// List<String> tempList = new ArrayList<String>();
			// tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							list));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes miPrintReport
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintReport() {
		if (miPrintReport == null) {
			miPrintReport = new JMenuItem();
			miPrintReport.setText("打印中华人民共和国保税区进境货物备案清单");
			miPrintReport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport();
						}
					});
		}
		return miPrintReport;
	}

	/**
	 * This method initializes miBarcodePrint
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBarcodePrint() {
		if (miBarcodePrint == null) {
			miBarcodePrint = new JMenuItem();
			miBarcodePrint.setText("打印保税区报关单条形码");
			miBarcodePrint
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printBarcodePrint();
						}
					});
		}
		return miBarcodePrint;
	}

	/**
	 * 打印保税区报关单条形码
	 */
	public void printBarcodePrint() {
		try {
			List list = customsDeclarationModel.getCurrentRows();
			Map<String, Object> parameters = new HashMap<String, Object>();
			// BaseCustomsDeclaration bcd = (BaseCustomsDeclaration)
			// list.get(0);
			// List list2 = baseEncAction.findCustomsDeclarationCommInfo(
			// new Request(CommonVars.getCurrUser()), bcd);
			InputStream masterReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/tiaoxingma.jasper");
			// List<String> tempList = new ArrayList<String>();
			// tempList.add("tempData");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, new CustomReportDataSource(
							list));
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes btnSortChcked
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSortChcked() {
		if (btnSortChcked == null) {
			btnSortChcked = new JButton();
			btnSortChcked.setText("按商检排序");
			btnSortChcked
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (projectType == ProjectType.BCUS) {
								impCustomsAuthorityAction
										.definedOrder(new Request(CommonVars
												.getCurrUser()));
							} else if (projectType == ProjectType.DZSC) {
								dZSCImpCustomsAuthorityAction
										.definedOrder(new Request(CommonVars
												.getCurrUser()));
							} else if (projectType == ProjectType.BCS) {
								bCSImpCustomsAuthorityAction
										.definedOrder(new Request(CommonVars
												.getCurrUser()));
							}
							List list = getDataSource();
							Vector vec = new Vector();
							for (int i = 0; i < list.size(); i++) {
								vec.addElement((BaseCustomsDeclarationCommInfo) list
										.get(i));
							}
							DgSortChecked dg = new DgSortChecked();
							dg.setBaseEncAction(baseEncAction);
							dg.setList(vec);
							String info = dg.setDgVisible(true);
							if (!"".equals(info)) {
								JOptionPane.showMessageDialog(
										DgBaseImportCustomsDeclaration.this,
										info, "按商检排序",
										JOptionPane.ERROR_MESSAGE);
							}
							initTable();

						}
					});
		}
		return btnSortChcked;
	}

	class FocusActionListner extends KeyAdapter {
		Component component;

		FocusActionListner(Component component) {
			this.component = component;
		}

		public void keyPressed(KeyEvent e) {
			KeyAdapterControl.setAddListener(true);
			if (e.getKeyCode() == 10) {
				component.requestFocus();
			}
		}
	}

	/**
	 * This method initializes cbbFeeCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFeeCurr() {
		if (cbbFeeCurr == null) {
			cbbFeeCurr = new JComboBox();
			cbbFeeCurr.setBounds(new Rectangle(74, 217, 108, 20));
		}
		return cbbFeeCurr;
	}

	/**
	 * This method initializes cbbFeeCurr1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOtherCurr() {
		if (cbbOtherCurr == null) {
			cbbOtherCurr = new JComboBox();
			cbbOtherCurr.setBounds(new Rectangle(329, 241, 99, 19));
		}
		return cbbOtherCurr;
	}

	/**
	 * This method initializes cbbFeeCurr2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbInsurCurr() {
		if (cbbInsurCurr == null) {
			cbbInsurCurr = new JComboBox();
			cbbInsurCurr.setBounds(new Rectangle(542, 217, 143, 20));
		}
		return cbbInsurCurr;
	}

	/**
	 * This method initializes btnSplit
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnSplit() {
		if (btnSplit == null) {
			btnSplit = new JButton();
			btnSplit.setText("拆分");
			btnSplit.setPreferredSize(new Dimension(43, 30));
			btnSplit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commInfoModel == null) {
						initTable();
					}
					if (commInfoModel.getList().size() <= 20) {
						JOptionPane.showMessageDialog(
								DgBaseImportCustomsDeclaration.this,
								"此报关单商品项数没有超过20项，所以不能拆分。");
						return;
					}
					List list = baseEncAction.splitCustomsDeclaration(
							new Request(CommonVars.getCurrUser()),
							customsDeclaration);
					String s = "拆分生成" + list.size() + "笔新的进口报关单，报关单流水号如下:\r\n";
					for (int i = 0; i < list.size(); i++) {
						BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list
								.get(i);
						s += (bcd.getSerialNumber() + ";");
					}
					customsDeclarationModel.addRows(list);
					initTable();
					JOptionPane.showMessageDialog(
							DgBaseImportCustomsDeclaration.this, s);
				}
			});
		}
		return btnSplit;
	}

	/**
	 * This method initializes tfConnectNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConnectNo() {
		if (tfConnectNo == null) {
			tfConnectNo = new JTextField();
			tfConnectNo.setBounds(new Rectangle(88, 411, 145, 21));
		}
		return tfConnectNo;
	}

	/**
	 * This method initializes tfConnectDeclarationNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConnectDeclarationNo() {
		if (tfConnectDeclarationNo == null) {
			tfConnectDeclarationNo = new JTextField();
			tfConnectDeclarationNo.setBounds(new Rectangle(311, 411, 145, 21));
		}
		return tfConnectDeclarationNo;
	}

	/**
	 * This method initializes pnTcs
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTcs() {
		if (pnTcs == null) {
			lbOperType = new JLabel();
			lbOperType.setBounds(new Rectangle(374, 50, 100, 22));
			lbOperType.setText("操作类型");
			lbSupplmentType = new JLabel();
			lbSupplmentType.setBounds(new Rectangle(50, 370, 100, 22));
			lbSupplmentType.setText("补充报关类型");
			lbTcsResultMessage = new JLabel();
			lbTcsResultMessage.setBounds(new Rectangle(374, 150, 100, 22));
			lbTcsResultMessage.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsResultMessage.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsResultMessage.setText("回执信息：");
			lbtcsResultTime = new JLabel();
			lbtcsResultTime.setBounds(new Rectangle(374, 100, 100, 22));
			lbtcsResultTime.setHorizontalAlignment(SwingConstants.LEFT);
			lbtcsResultTime.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbtcsResultTime.setText("回执时间：");
			lbTcsNote = new JLabel();
			lbTcsNote.setText("协同任务备注：");
			lbTcsNote.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsNote.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsNote.setBounds(new Rectangle(50, 300, 100, 22));
			lbTcsDeclareProperty = new JLabel();
			lbTcsDeclareProperty.setText("EDI申报备注：");
			lbTcsDeclareProperty.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsDeclareProperty.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsDeclareProperty.setBounds(new Rectangle(50, 250, 100, 22));
			lbTcsEntryTransitType = new JLabel();
			lbTcsEntryTransitType.setText("报关标志：");
			lbTcsEntryTransitType.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsEntryTransitType.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsEntryTransitType.setBounds(new Rectangle(50, 200, 100, 22));
			lbTcsEntryType = new JLabel();
			lbTcsEntryType.setText("报关单类型：");
			lbTcsEntryType.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsEntryType.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsEntryType.setBounds(new Rectangle(50, 150, 100, 22));
			lbTcsSendTime = new JLabel();
			lbTcsSendTime.setText("报文传输时间：");
			lbTcsSendTime.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsSendTime.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsSendTime.setBounds(new Rectangle(50, 100, 100, 22));
			lbTcsTaskId = new JLabel();
			lbTcsTaskId.setText("TcsTaskId：");
			lbTcsTaskId.setHorizontalAlignment(SwingConstants.LEFT);
			lbTcsTaskId.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lbTcsTaskId.setBounds(new Rectangle(50, 50, 100, 22));
			pnTcs = new JPanel();
			pnTcs.setLayout(null);
			pnTcs.add(lbTcsTaskId, null);
			pnTcs.add(getTfTcsTaskId(), null);
			pnTcs.add(lbTcsSendTime, null);
			pnTcs.add(getTfTcsSendTime(), null);
			pnTcs.add(lbTcsEntryType, null);
			pnTcs.add(getTfTcsEntryType(), null);
			pnTcs.add(lbTcsEntryTransitType, null);
			pnTcs.add(getTfTcsEdiId(), null);
			pnTcs.add(lbTcsDeclareProperty, null);
			pnTcs.add(getTfTcsType(), null);
			pnTcs.add(lbTcsNote, null);
			pnTcs.add(getTfTcsNote(), null);
			pnTcs.add(lbtcsResultTime, null);
			pnTcs.add(getTfTcsResultTime(), null);
			pnTcs.add(lbTcsResultMessage, null);
			pnTcs.add(getSpnTcsResultMessage(), null);
			pnTcs.add(lbSupplmentType, null);
			pnTcs.add(getCbbSupplmentType(), null);
			pnTcs.add(lbOperType, null);
			pnTcs.add(getCbbOperType(), null);
		}
		return pnTcs;
	}

	/**
	 * This method initializes tfTcsTaskId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTcsTaskId() {
		if (tfTcsTaskId == null) {
			tfTcsTaskId = new JTextField();
			tfTcsTaskId.setBounds(new Rectangle(167, 50, 167, 22));
			tfTcsTaskId.setEditable(false);
			tfTcsTaskId.setEnabled(true);
		}
		return tfTcsTaskId;
	}

	/**
	 * This method initializes tfTcsSendTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTcsSendTime() {
		if (tfTcsSendTime == null) {
			tfTcsSendTime = new JTextField();
			tfTcsSendTime.setBounds(new Rectangle(167, 100, 167, 22));
			tfTcsSendTime.setEditable(false);
			tfTcsSendTime.setEnabled(true);
		}
		return tfTcsSendTime;
	}

	/**
	 * This method initializes tfTcsEntryType
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getTfTcsEntryType() {
		if (tfTcsEntryType == null) {
			tfTcsEntryType = new JComboBox();
			tfTcsEntryType.setBounds(new Rectangle(167, 150, 167, 22));
		}
		return tfTcsEntryType;
	}

	/**
	 * This method initializes tfTcsEdiId
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getTfTcsEdiId() {
		if (tfTcsEdiId == null) {
			tfTcsEdiId = new JComboBox();
			tfTcsEdiId.setBounds(new Rectangle(167, 200, 167, 22));
		}
		return tfTcsEdiId;
	}

	/**
	 * This method initializes tfTcsType
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getTfTcsType() {
		if (tfTcsType == null) {
			tfTcsType = new JComboBox();
			tfTcsType.setBounds(new Rectangle(167, 250, 167, 22));
		}
		return tfTcsType;
	}

	/**
	 * This method initializes tfTcsNote
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getTfTcsNote() {
		if (tfTcsNote == null) {
			tfTcsNote = new JTextArea();
			tfTcsNote.setBounds(new Rectangle(167, 300, 167, 50));
			tfTcsNote.setLineWrap(true);
		}
		return tfTcsNote;
	}

	/**
	 * This method initializes tfTcsResultTime
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTcsResultTime() {
		if (tfTcsResultTime == null) {
			tfTcsResultTime = new JTextField();
			tfTcsResultTime.setBounds(new Rectangle(480, 100, 167, 22));
			tfTcsResultTime.setEditable(false);
		}
		return tfTcsResultTime;
	}

	/**
	 * This method initializes tfTcsResultMessage
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextArea getTfTcsResultMessage() {
		if (tfTcsResultMessage == null) {
			tfTcsResultMessage = new JTextArea(20, 15);
			tfTcsResultMessage.setLineWrap(true);
			tfTcsResultMessage.setEditable(false);
		}
		return tfTcsResultMessage;
	}

	private JScrollPane getSpnTcsResultMessage() {
		if (spnTcsResultMessage == null) {
			spnTcsResultMessage = new JScrollPane(getTfTcsResultMessage());
			spnTcsResultMessage.setBounds(new Rectangle(480, 150, 167, 250));
		}
		return spnTcsResultMessage;
	}

	/**
	 * This method initializes tfdomesticConveyanceCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfdomesticConveyanceCode() {
		if (tfdomesticConveyanceCode == null) {
			tfdomesticConveyanceCode = new JTextField();
			tfdomesticConveyanceCode
					.setBounds(new Rectangle(565, 139, 120, 20));
			tfdomesticConveyanceCode
					.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == 10) {

								Integer type = Integer
										.valueOf(((ItemProperty) cbbImpExpType
												.getSelectedItem()).getCode());
								if (type == ImpExpType.TRANSFER_FACTORY_IMPORT) {

									return;
								}

								if (tfdomesticConveyanceCode.getText() != null
										&& !"".equals(tfdomesticConveyanceCode
												.getText())) {
									MotorCode motor = materialManageAction
											.findMotorCodeComplex(new Request(
													CommonVars.getCurrUser()),
													tfdomesticConveyanceCode
															.getText().trim());
									if (motor != null) {
										tfBillOfLading.setText(motor
												.getHomeCard());
										writeDefaultValue(motor);
									}
								}
							}
						}
					});
		}
		return tfdomesticConveyanceCode;
	}

	/**
	 * 回写转关附加
	 * 
	 * @param motor
	 */
	private void writeDefaultValue(MotorCode motor) {
		String homeCard = tfBillOfLading.getText().trim();// 提运单号
		if (customsDeclaration.getTransferMode() == null) {
			return;
		}
		if (customsDeclaration.getTransferMode().getName().trim()
				.equals("水路运输")) {
			customsDeclaration.setDomesticConveyanceCode(motor.getComplex());
			customsDeclaration.setDomesticConveyanceName(homeCard);
		} else {
			customsDeclaration.setOverseasConveyanceCode(motor.getComplex());
			customsDeclaration.setOverseasConveyanceName(motor
					.getHongkongCard());
			customsDeclaration.setOverseasConveyanceBillOfLading(motor
					.getHomeCard());
			customsDeclaration.setDomesticConveyanceCode(motor.getComplex());
			customsDeclaration.setDomesticConveyanceName(homeCard);

		}

		tfBillOfLading.setText(motor.getHomeCard());
	}

	private JMenuItem getMiZhangXiangDanMerger() {
		if (miZhangXiangDanMerger == null) {
			miZhangXiangDanMerger = new JMenuItem();
			miZhangXiangDanMerger.setText("装箱单(按运输工具合并)");
			miZhangXiangDanMerger
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgWExportCommodityEncasementMergerReportSet dg = new DgWExportCommodityEncasementMergerReportSet();
							dg.setBaseEncAction(baseEncAction);
							dg.setMaterialManageAction(materialManageAction);
							dg.setProjectType(projectType);
							dg.setIsOverPrint(isTaoda);
							dg.setBaseCustomsDeclaration(customsDeclaration);
							dg.setExp(false);
							dg.setVisible(true);
						}
					});
		}
		return miZhangXiangDanMerger;
	}

	/**
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiInvoiceMerger() {
		if (miInvoiceMerger == null) {
			miInvoiceMerger = new JMenuItem();
			miInvoiceMerger.setText("发票(按运输工具合并)");
			miInvoiceMerger
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImportInvoiceMergerReportSet dg = new DgImportInvoiceMergerReportSet();
							dg.setBaseEncAction(baseEncAction);
							dg.setMaterialManageAction(materialManageAction);
							dg.setProjectType(projectType);
							dg.setIsOverPrint(isTaoda);
							dg.setBaseCustomsDeclaration(customsDeclaration);
							dg.setisImport(true);
							dg.setExp(false);
							dg.setVisible(true);
						}
					});
		}
		return miInvoiceMerger;
	}

	/**
	 * This method initializes cbbSupplmentType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSupplmentType() {
		if (cbbSupplmentType == null) {
			cbbSupplmentType = new JComboBox();
			cbbSupplmentType.setBounds(new Rectangle(167, 370, 167, 22));
			cbbSupplmentType.addItem(new ItemProperty(
					SupplmentType.NO_SYPPLMENT.toString(), SupplmentType
							.getSupplmentTypeDesc(SupplmentType.NO_SYPPLMENT)));
			cbbSupplmentType
					.addItem(new ItemProperty(
							SupplmentType.INITIATIVE_SYPPLMENT.toString(),
							SupplmentType
									.getSupplmentTypeDesc(SupplmentType.INITIATIVE_SYPPLMENT)));
			cbbSupplmentType.setSelectedIndex(0);
		}
		return cbbSupplmentType;
	}

	/**
	 * This method initializes cbbOperType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOperType() {
		if (cbbOperType == null) {
			cbbOperType = new JComboBox();
			cbbOperType.setBounds(new Rectangle(480, 50, 167, 22));
		}
		return cbbOperType;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("航次号");
			label.setBounds(new Rectangle(514, 149, 48, 18));
			label.setBounds(351, 116, 48, 18);
		}
		return label;
	}

	private JTextField getTfVoyageNo() {
		if (tfVoyageNo == null) {
			tfVoyageNo = new JTextField();
			tfVoyageNo.setBounds(new Rectangle(564, 149, 94, 20));
			tfVoyageNo.setBounds(396, 116, 94, 20);
		}
		return tfVoyageNo;
	}
}
