/*
 * Created on 2004-8-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Template
 * s
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
import javax.swing.JMenu;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.client.contractexe.DgMakeBcsCustomsDeclaration;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractexe.entity.BcsCustomsDeclaration;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
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
import com.bestway.bcus.custombase.entity.parametercode.BalanceMode;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.CustomsDeclaration;
import com.bestway.bcus.enc.entity.MakeCusomsDeclarationParam;
import com.bestway.bcus.enc.entity.TempCustomsDeclarationCommInfo;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.custom.common.report.CustomsDeclarationSubReportDataSource;
import com.bestway.client.custom.common.report.CustomsPrinter;
import com.bestway.client.custom.common.report.DgCaleReturnExport;
import com.bestway.client.custom.common.report.DgImportInvoiceMergerReportSet;
import com.bestway.client.custom.common.report.DgWExportCommodityEncasementMergerReportSet;
import com.bestway.client.custom.common.report.DgWExportCommodityEncasementReporLwjgtSet;
import com.bestway.client.invoice.InvoiceQuery;
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
import com.bestway.customs.common.action.BCSExpCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCExpCustomsAuthorityAction;
import com.bestway.customs.common.action.ExpCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.ExportInvoice;
import com.bestway.customs.common.entity.TCSCommonCode;
import com.bestway.customs.common.entity.TempTransFactInfo;
import com.bestway.dzsc.contractexe.entity.DzscCustomsDeclaration;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.invoice.entity.Invoice;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.bestway.util.RegexUtil;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public abstract class DgBaseExportCustomsDeclaration extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1189526668289702541L;

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

	protected JTableListModel commInfoModel = null;

	protected BaseEncAction baseEncAction = null;

	protected CustomBaseAction customBaseAction = null;

	protected MaterialManageAction materialManageAction = null;

	protected BaseCustomsDeclaration customsDeclaration = null; // @jve:decl-index=0:

	protected int dataState = DataState.BROWSE;

	protected JFormattedTextField tfFreightage = null;

	protected JFormattedTextField tfInsurance = null;

	protected JFormattedTextField tfIncidentalExpenses = null;

	protected JFormattedTextField tfCommodityNum = null;

	protected BaseEmsHead emsHead = null; // 电子帐册

	protected JButton btnSave = null;

	protected JButton btnCancel = null;

	protected DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	protected NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	protected JButton btnTransferCustoms = null;

	protected JButton btnPreCustomsDeclarationCode = null;

	protected JTextField tfEmsNo = null;

	protected JTextField tfCompanyEmsNo = null;

	protected JTextField tfManufacturer = null;

	protected JTextField tfCustomsDeclarationSerialNumber = null;

	protected JComboBox cbbBalanceMode = null;

	protected JButton btnCheckData = null;

	protected JButton btnEffect = null;

	protected JButton btnUnreel = null;

	protected List containers = null;

	protected JLabel jLabel46 = null;
	private JLabel lbCustomsBroker;

	protected JLabel lbCompanyEmsNo = null;

	protected JTextField tfDeclaraCustomsBroker = null;

	protected JComboBox cbbDeclarationCompany = null;

	protected SystemAction systemAction = null;

	protected JTextField tfBondedWarehouse = null;

	protected JLabel jLabel47 = null;

	protected javax.swing.JLabel lbEmsNo = new JLabel();

	protected String sedi = null;

	protected ManualDeclareAction manualDecleareAction = null;

	protected int projectType = ProjectType.BCUS;

	protected JLabel jLabel48 = null;

	protected JTextField tfInvoiceCode = null;

	protected JButton btnInvoice = null;

	protected JToolBar jToolBar1 = null;

	protected JButton jButton = null;

	protected JFooterTable jTable = null;

	protected JFooterScrollPane jScrollPane = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton btnAuthorizeFile = null;

	private boolean isTaoda = false;

	private String oldInvoiceCode = null;

	private FecavAction fecavAction = null;

	private JLabel jLabel49 = null;

	private JTextField tfCustomsEnvelopBillNo = null;

	private JButton btnFptAppNo = null;

	private JButton jButton4 = null;

	private JComboBox jComboBox = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JButton jButton7 = null;

	private ExpCustomsAuthorityAction expCustomsAuthorityAction = null;

	private BCSExpCustomsAuthorityAction bCSExpCustomsAuthorityAction = null;

	private DZSCExpCustomsAuthorityAction dZSCExpCustomsAuthorityAction = null;

	private int firstOpen = 1;// 每次第一次打开时进行判断

	private Boolean isManualPreCode = null; // @jve:decl-index=0:

	private DgTransferCustomsConveyance dgTransferCustomsConveyance = null;

	private JButton jButton8 = null;

	private JLabel jLabel43 = null;

	private JLabel lbCommInfoMoney = null;

	private JLabel jLabel = null;

	private JTextField tfBillOfLading = null; // 提运单号

	private JButton jButton9 = null;

	private JButton btnManufacturer = null;

	public JLabel jInspect = null;

	private JTextField tfInspect = null;

	private JLabel jLabel491 = null;

	private JCalendarComboBox tfCreateDate = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="773,90"

	private JMenuItem jMenuItem = null;

	private JMenuItem jMenuItem1 = null;

	private JMenuItem jMenuItem2 = null;

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

	private JMenuItem jMenuItem14 = null;

	private JMenuItem jMenuItem17 = null;// 2010-05-21新增 hcl

	private JMenuItem miZhangXiangDanMerger = null;

	private JMenuItem miInvoiceMerger = null;

	private JMenuItem jmItemBoxNoComplex = null;

	private JMenuItem jmItemBoxNo = null;

	private JMenuItem miPrintPrccBonded = null;

	private JMenuItem miBarcodePrint = null;

	private JMenuItem jMenuItem15 = null;

	private JMenu jMenu = null;

	private JMenuItem jMenuItem16 = null;

	private JMenuItem invoice = null;

	private JButton btnSortChecked = null;

	private JLabel jLabel50 = null;

	private JComboBox cbbFeeCurr = null;

	private JLabel jLabel502 = null;

	private JComboBox cbbInsurCurr = null;

	private JLabel jLabel501 = null;

	private JComboBox cbbOtherCurr = null;

	private JButton btnSplit = null;

	private JLabel jLabel471 = null;

	private JTextField tfConnectNo = null;

	private JLabel jLabel472 = null;

	private JTextField tfConnectDeclarationNo = null;

	private ContractAction contractAction = null; // 合同

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

	private JLabel jLabel1 = null;

	private JTextField tfdomesticConveyanceCode = null;

	private JButton btnCaleReturnExport;

	private CustomsDeclarationSet other1;

	private JLabel lbSupplmentType = null;

	private JComboBox cbbSupplmentType = null;

	private JLabel lbOperType = null;

	private JComboBox cbbOperType = null;

	public String oldMemos;// 临时存放进出口码头口岸

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
	public DgBaseExportCustomsDeclaration() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		expCustomsAuthorityAction = (ExpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean("expCustomsAuthorityAction");
		bCSExpCustomsAuthorityAction = (BCSExpCustomsAuthorityAction) CommonVars
				.getApplicationContext()
				.getBean("bCSExpCustomsAuthorityAction");
		dZSCExpCustomsAuthorityAction = (DZSCExpCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCExpCustomsAuthorityAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		fecavAction = (FecavAction) CommonVars.getApplicationContext().getBean(
				"fecavAction");
		initialize();
		// 这里是控制焦点的顺序，以方便键盘的输！
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
		components.add(this.tfManufacturer);
		components.add(this.cbbDeclarationCompany);
		// components.add(null);
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
		this.setTitle("出口报关单编辑");
		this.setContentPane(getJContentPane());
		this.setSize(734, 540);
		cbbImpExpType.setFocusable(true);
		cbbImpExpType.requestFocus();
		// tfPreCustomsDeclarationCode.setFocusable(false);
		btnPreCustomsDeclarationCode.setFocusable(false);
		// tfUnificationCode.setFocusable(false);
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
			if (customsDeclaration != null) {
				this.oldInvoiceCode = customsDeclaration.getInvoiceCode();
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

		if (customsDeclaration != null) {
			this.oldInvoiceCode = customsDeclaration.getInvoiceCode();
		}
		initUIComponents();
		this.showPrimalData();
		this.containers = this.baseEncAction.findContainerByCustomsDeclaration(
				new Request(CommonVars.getCurrUser()), this.customsDeclaration);
		setState();
		addListeners();
		if (commInfoModel == null) {
			initTable();
		}

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
					if (cbbImpExpType.getSelectedItem() != null
							&& ((ItemProperty) cbbImpExpType.getSelectedItem())
									.getCode().equals("5")) {
						if (tfMemos.getText() == null) {
							tfMemos.setText("转至");
						} else if (!tfMemos.getText().contains("转至")) {
							tfMemos.setText("转至" + tfMemos.getText());
						}
					} else {
						if (tfMemos.getText().contains("转至")) {
							int log = tfMemos.getText().trim().indexOf("转至");
							if (log >= 0) {
								String memos = tfMemos.getText().substring(
										log + 2,
										tfMemos.getText().trim().length());
								tfMemos.setText(memos);
							}
						}
					}
					Integer type = Integer
							.valueOf(((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode());
					if (type.equals(ImpExpType.TRANSFER_FACTORY_EXPORT)) {
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
					other1 = systemAction.findCustomsSet(
							new Request(CommonVars.getCurrUser(), true),
							customsDeclaration == null ? null : type);
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
						customsDeclaration.setBalanceMode(other1
								.getBalanceMode());
						cbbBalanceMode.setSelectedItem(customsDeclaration
								.getBalanceMode());

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
			jToolBar.add(getJButton5());
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
							DgBaseExportCustomsDeclaration.this
									.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							if (jTabbedPane.getSelectedIndex() == 0) {
								if (jPanel != null)
									getJPanel().setLayout(null);
								if (jLabel != null)
									jLabel.setVisible(true);
								if (jComboBox != null)
									jComboBox.setVisible(true);
								if (tfTelephone != null)
									tfTelephone.setVisible(true);
								if (btnPrint != null)
									btnPrint.setBounds(260, 4, 62, 21);
								if (jButton5 != null)
									jButton5.setBounds(new java.awt.Rectangle(
											322, 4, 72, 21));
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
									System.out.println(" -- 保存表头");
									if (!saveCustom()) { // 保存有错误提示
										jTabbedPane.setSelectedIndex(0);
										DgBaseExportCustomsDeclaration.this
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
							DgBaseExportCustomsDeclaration.this
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
	 * 新增报关单,或者报关单商品信息
	 * 
	 */
	protected void addCustomsDeclarationData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			dataState = DataState.ADD;
			showPrimalData();
			setState();
		} else if (jTabbedPane.getSelectedIndex() == 1) {// 新增表体
			if (customsDeclaration == null) {
				return;
			}
			List<TempCustomsDeclarationCommInfo> list = CommonQuery
					.getInstance().getTempCustomsDeclarationCommInfo(
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
								DgBaseExportCustomsDeclaration.this,
								"当前商品信息还可新增" + String.valueOf(20 - currN)
										+ "项!", "提示", 2);
						return;
					}
				}
			}
			baseEncAction.saveCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), true, list,
					customsDeclaration);
			initTable();
			showCommInfoMoney();

			// commInfoModel.setTableEndSelectRow();
			int index = commInfoModel.getList().size() - list.size();
			if (index >= 0) {
				commInfoModel.setSelectRow(index);
			}
			if (customsDeclaration != null) {
				oldInvoiceCode = customsDeclaration.getInvoiceCode();
			}
			// qq;
			editCustomsDeclarationData1();
			getAttachedBill();
		}
	}

	protected JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(43, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (customsDeclaration != null) {
						oldInvoiceCode = customsDeclaration.getInvoiceCode();
					}
					editCustomsDeclarationData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 修改报关单,或者报关单商品信息
	 * 
	 */
	protected void editCustomsDeclarationData() {
		if (jTabbedPane.getSelectedIndex() == 0
				|| jTabbedPane.getSelectedIndex() == 2) {
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
			// if (customsDeclaration.getBillListId() != null
			// && !"".equals(customsDeclaration.getBillListId())) {
			// JOptionPane.showMessageDialog(
			// DgBaseExportCustomsDeclaration.this,
			// "由于此报关单是根据报关清单自动生成的，所以不能修改商品明细", "提示",
			// JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			if (commInfoModel == null) {
				return;
			}
			if (commInfoModel.getList().size() < 1) {
				return;
			}
			if (commInfoModel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(
						DgBaseExportCustomsDeclaration.this, "请选择你要修改的资料",
						"提示", 0);
				return;
			}
			DgBaseExportCustomsDeclarationCommInfo dg = this
					.getExportCustomsDeclarationCommInfo();// new
			// DgBaseExportCustomsDeclarationCommInfo();
			dg.setTableModel(commInfoModel);
			dg.setEffective(customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue());
			dg.setDgBase(DgBaseExportCustomsDeclaration.this);
			dg.setBaseEncAction(this.baseEncAction);
			dg.setProjectType(projectType);
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
			getPiceAndWeight();
			initTable();
			showCommInfoMoney();
		}
	}

	/**
	 * 新增报关单,或者报关单商品信息
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
			DgBaseExportCustomsDeclarationCommInfo dg = this
					.getExportCustomsDeclarationCommInfo();
			dg.setTableModel(commInfoModel);
			dg.setEffective(customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue());
			dg.setDgBase(DgBaseExportCustomsDeclaration.this);
			dg.setBaseEncAction(this.baseEncAction);
			dg.setProjectType(projectType);
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
			getPiceAndWeight();
			initTable();
			showCommInfoMoney();
		}
	}

	protected DgBaseExportCustomsDeclarationCommInfo getExportCustomsDeclarationCommInfo() {
		return new DgBaseExportCustomsDeclarationCommInfo();
	}

	protected JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(43, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.deleteCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.deleteCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction
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
	 * 删除报关单商品信息
	 * 
	 */
	protected void deleteCustomsDeclarationData() {
		if (JOptionPane.showConfirmDialog(DgBaseExportCustomsDeclaration.this,
				"确定要删除吗？", "确认", 0) != 0) {
			return;
		}
		if (customsDeclaration == null) {
			return;
		}
		/*
		 * if (customsDeclaration.getBillListId() != null &&
		 * !"".equals(customsDeclaration.getBillListId())) {
		 * JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
		 * "由于此报关单是根据报关清单自动生成的，所以不能删除商品明细", "提示",
		 * JOptionPane.INFORMATION_MESSAGE); return; }
		 */
		if (commInfoModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		// if
		// (JOptionPane.showConfirmDialog(DgBaseExportCustomsDeclaration.this,
		// "是否确定删除数据!!!", "提示", 0) != 0) {
		// return;
		// }
		baseEncAction.deleteCustomsDeclarationCommInfo(
				new Request(CommonVars.getCurrUser()),
				commInfoModel.getCurrentRows());
		getPiceAndWeight();
		initTable();
		showCommInfoMoney();
		getAttachedBill();
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
		tfAttachedBill.setText(customsDeclaration.getAttachedBill());
		customsDeclarationModel.updateRow(customsDeclaration);
	}

	protected JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setPreferredSize(new Dimension(200, 29));
			jPanel.setLayout(null);
			jLabel.setBounds(1, 5, 36, 18);
			jLabel.setText("报关员");
			jPanel.add(jLabel, null);
			jPanel.add(getTfTelephone(), null);
			jPanel.add(getJComboBox(), null);
		}
		return jPanel;
	}

	protected JTextField getTfTelephone() {
		if (tfTelephone == null) {
			tfTelephone = new JTextField();
			tfTelephone.setBounds(112, 3, 105, 25);
		}
		return tfTelephone;
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
					jMenu.setVisible(true);
				}
			});
		}
		return btnPrint;
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

	protected Map getParaList() {
		List list = new ArrayList();
		Map prop = new HashMap();
		if (customsDeclaration != null) {
			list.add(customsDeclaration);
		} else {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
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
	 * 出口发票中间类传值填充
	 * 
	 * @param isInvoice
	 *            重写函数而已..无用参数..标示为出口发票
	 * @return
	 * @author sxk
	 */
	private Map getParaList(boolean isInvoice) {
		List list = new ArrayList();
		Map prop = new HashMap();
		if (customsDeclaration != null) {
			list.add(customsDeclaration);
		} else {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
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

		// sxk 添加于 10.10.13
		// 出口报关单列表信息
		List tableList = new ArrayList();
		// 合同料件
		List<ContractImg> imgList = new ArrayList<ContractImg>();
		// 合同成品
		List<ContractExg> exgList = new ArrayList<ContractExg>();
		// 最终传给报表的LIST
		List invoiceResult = new ArrayList();
		// 存放合同料件
		Map<String, ContractImg> imgMap = new HashMap<String, ContractImg>();
		// 存放合同成品
		Map<String, ContractExg> exgMap = new HashMap<String, ContractExg>();
		String key = "";
		if (null != commInfoModel) {
			tableList = commInfoModel.getList();
			// 出口合同号
			String expContractNo = customsDeclaration.getContract();
			// 当出口类型为 14:余料结转 时才为料件、其他情况为成品
			// 通过合同号查找合同料件
			if (customsDeclaration.getImpExpType().equals("14")) {
				// imgList =
				// contractAction.findContractImgByContractExpContractNo(new
				// Request(
				// CommonVars.getCurrUser()), expContractNo);
				// for(ContractImg c : imgList){
				// key = c.getSeqNum()+"/"
				// +c.getComplex().getCode()+"/"
				// +c.getName()+"/"
				// +c.getSpec();
				// imgMap.put(key, c);
				// }
			} else {
				// 通过合同号查找合同成品
				System.out.println("小开通过合同号查找合同成品expContractNo="
						+ expContractNo);
				exgList = contractAction.findContractExgByExpContractNo(
						new Request(CommonVars.getCurrUser()), expContractNo);
				System.out.println("小开通过合同号查找合同成品=" + exgList.size());
				for (ContractExg c : exgList) {
					key = c.getSeqNum() + "/" + c.getComplex().getCode() + "/"
							+ c.getName() + "/" + c.getSpec();
					exgMap.put(key, c);
				}
			}
			// 遍历报关单商品，将合同的信息填充进中间类
			String tableKey = "";
			for (int i = 0; i < tableList.size(); i++) {
				BaseCustomsDeclarationCommInfo b = (BaseCustomsDeclarationCommInfo) tableList
						.get(i);
				tableKey = b.getCommSerialNo() + "/" + b.getComplex().getCode()
						+ "/" + b.getCommName() + "/" + b.getCommSpec();
				ExportInvoice exportInvoice = new ExportInvoice();
				exportInvoice.setCommSerialNo(b.getSerialNumber());
				exportInvoice.setCommName(b.getCommName());
				exportInvoice.setCommSpec(b.getCommSpec());
				exportInvoice.setComplex(b.getComplex());
				exportInvoice.setCurrency(b.getBaseCustomsDeclaration()
						.getCurrency());
				exportInvoice.setUnit(b.getUnit());
				System.out.println("22222222222222222222");
				System.out.println("b.getCommUnitPrice()="
						+ b.getCommUnitPrice());
				System.out.println("b.getCommTotalPrice()="
						+ b.getCommTotalPrice());
				exportInvoice.setCommUnitPrice(CommonUtils.getDoubleByDigit(
						b.getCommUnitPrice(), 4));
				exportInvoice.setCommTotalPrice(CommonUtils.getDoubleByDigit(
						b.getCommTotalPrice(), 4));
				exportInvoice.setCommAmount(b.getCommAmount());
				exportInvoice.setProcessUnitPrice(0.0);
				exportInvoice.setProcessTotalPrice(0.0);
				System.out.println("customsDeclaration.getImpExpType()="
						+ customsDeclaration.getImpExpType());
				if (customsDeclaration.getImpExpType().equals("14")) {
					// ContractImg contractImg = imgMap.get(tableKey);
					// exportInvoice.setProcessUnitPrice(contractImg)
				} else {
					System.out.println("exgMap=" + exgMap.size());
					ContractExg contractExg = exgMap.get(tableKey);
					if (null != contractExg) {
						if (null != contractExg.getProcessUnitPrice()
								&& !"".equals(contractExg.getProcessUnitPrice())) {
							exportInvoice.setProcessUnitPrice(CommonUtils
									.getDoubleByDigit(
											contractExg.getProcessUnitPrice(),
											4));
						}
						System.out.println("contractExg.getProcessUnitPrice()="
								+ contractExg.getProcessUnitPrice());
						System.out.println("b.getCommAmount()="
								+ b.getCommAmount());
						if (null != b.getCommUnitPrice()
								&& null != contractExg.getProcessUnitPrice()) {
							if (b.getCommAmount() != null) {
								exportInvoice
										.setProcessTotalPrice(CommonUtils.getDoubleByDigit(
												b.getCommAmount()
														* contractExg
																.getProcessUnitPrice(),
												4));
							}

						}
					}
				}
				invoiceResult.add(exportInvoice);
			}
		}

		prop.put("invoiceResult", invoiceResult);
		return prop;
	}

	// 出口商品发票打印（东聚使用）
	private void printExportReport(List commInfoList, boolean isOverPrint) {
		List list = new ArrayList();
		if (customsDeclaration != null) {
			list.add(customsDeclaration);
		} else {
			JOptionPane.showMessageDialog(this, "当前无数据可列印！", "提示",
					JOptionPane.YES_NO_OPTION);
			return;
		}
		CustomReportDataSource ds = new CustomReportDataSource(list);
		try {
			CustomsDeclarationSubReportDataSource
					.setSubReportData(commInfoList);
			InputStream subReportStream = DgExportInvoiceItem.class
					.getResourceAsStream("report/ExportInvoiceItemCommodityReport.jasper");
			JasperReport subReport = (JasperReport) JRLoader
					.loadObject(subReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverPrint", Boolean.valueOf(isOverPrint));
			parameters.put("subReport", subReport);
			// CompanyOther other = CommonVars.getOther();
			Company comp = (Company) customsDeclaration.getCompany();
			if (comp != null) {
				parameters.put("openAnAccountBank", comp.getBank());// 开户银行
				parameters.put("bankAccounts", comp.getAccount());// 帐户
				// parameters.put("memo", baseExportInvoiceItem.getMemo());//备注
				// 转厂出口
				if (customsDeclaration.getImpExpType() != null
						&& customsDeclaration
								.getImpExpType()
								.equals(Integer
										.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT))) {
					parameters.put("outTradeCo", customsDeclaration
							.getCustomer().getName());// 客户
					parameters.put("outCoTel", customsDeclaration.getCustomer()
							.getLinkTel());// 外商公司电话
					parameters.put("outAddress", customsDeclaration
							.getCustomer().getAddre());// 外商公司地址
				} else {
					parameters.put("outTradeCo", comp.getOutTradeCo());// 外商公司
					parameters.put("outCoTel", comp.getOutCoTel());// 外商公司电话
					parameters.put("outAddress", comp.getOutAddress());// 外商公司地址
				}
			}
			parameters.put("portOfLoadingOrUnloadingName", customsDeclaration
					.getPortOfLoadingOrUnloading() == null ? ""
					: customsDeclaration.getPortOfLoadingOrUnloading()
							.getName());// 目的港
			// if (other != null) {
			// 转运港
			// parameters.put("transferModeName",other.getEconveyance());//运输工具(非江海运输则为运输方式)
			/*
			 * parameters.put("transferModeName", customsDeclaration //运输方式
			 * .getTransferMode
			 * ()==null?"":customsDeclaration.getTransferMode().getName());
			 */
			// parameters.put("portDest", baseExportInvoiceItem.getPortDest());
			// }
			InputStream reportStream = DgExportInvoiceItem.class
					.getResourceAsStream("report/ExportInvoiceItemReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);
			DgReportViewer dgReportViewer = new DgReportViewer(jasperPrint);
			dgReportViewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	protected JPanel getPnBaseInfo() {
		if (pnBaseInfo == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(513, 141, 52, 18));
			jLabel1.setText("工具编码");
			jLabel472 = new JLabel();
			jLabel472.setBounds(new Rectangle(238, 411, 72, 18));
			jLabel472.setText("\u5173\u8054\u62a5\u5173\u5355\u53f7");
			jLabel471 = new JLabel();
			jLabel471.setBounds(new Rectangle(26, 411, 60, 18));
			jLabel471.setText("\u5173\u8054\u624b\u518c\u53f7");
			jLabel501 = new JLabel();
			jLabel501.setBounds(new Rectangle(276, 241, 55, 19));
			jLabel501.setText("杂费币制");
			jLabel502 = new JLabel();
			jLabel502.setBounds(new Rectangle(489, 217, 54, 20));
			jLabel502.setText("保费币制");
			jLabel50 = new JLabel();
			jLabel50.setBounds(new Rectangle(26, 217, 50, 20));
			jLabel50.setText("运费币制");
			jLabel491 = new JLabel();
			jLabel491.setBounds(new Rectangle(531, 313, 48, 19));
			jLabel491.setText("录入时间");
			jInspect = new JLabel();// 插件用到
			jInspect.setVisible(false);
			jInspect.setForeground(java.awt.Color.blue);
			jInspect.setBounds(new Rectangle(463, 411, 65, 19));
			jInspect.setText("报检预录号");
			jLabel49 = new JLabel();
			jLabel49.setBounds(new Rectangle(386, 386, 74, 19));
			jLabel49.setText("结转申请单号");
			jLabel48 = new JLabel();
			jLabel47 = new JLabel();
			lbCustomsBroker = new JLabel();
			javax.swing.JLabel jLabel45 = new JLabel();
			lbCompanyEmsNo = new JLabel();

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
			jLabel2.setBounds(27, 36, 49, 19);
			jLabel2.setText("出口类型");
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel3.setBounds(188, 37, 49, 19);
			jLabel3.setText("预录入号");
			jLabel4.setBounds(437, 37, 48, 19);
			jLabel4.setText("统一编号");
			jLabel5.setBounds(27, 62, 49, 19);
			jLabel5.setText("出口口岸");
			jLabel6.setBounds(188, 62, 49, 19);
			jLabel6.setText("报关单号");
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setBounds(384, 62, 50, 19);
			jLabel7.setText("出口日期");
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel8.setBounds(523, 62, 49, 19);
			jLabel8.setText("申报日期");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(27, 88, 49, 19);
			jLabel9.setText("经营单位");
			jLabel10.setBounds(345, 88, 50, 19);
			jLabel10.setText("发货单位");
			jLabel11.setBounds(27, 114, 49, 19);
			jLabel11.setText("运输方式");
			jLabel12.setBounds(188, 114, 49, 19);
			jLabel12.setText("运输工具");
			jLabel13.setBounds(487, 114, 49, 19);
			jLabel13.setText("提运单号");
			jLabel14.setBounds(27, 140, 49, 19);
			jLabel14.setText("贸易方式");
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel15.setBounds(188, 140, 49, 19);
			jLabel15.setText("征免性质");
			jLabel16.setBounds(341, 140, 54, 19);
			jLabel16.setText("结汇方式");
			jLabel17.setBounds(27, 166, 49, 19);
			jLabel17.setText("许可证号");
			jLabel18.setBounds(249, 166, 71, 19);
			jLabel18.setText("运抵国(地区)");
			jLabel19.setBounds(463, 166, 62, 19);
			jLabel19.setText("境内货源地");
			jLabel20.setBounds(27, 194, 49, 19);
			jLabel20.setText("指运港");
			jLabel21.setBounds(188, 194, 49, 19);
			jLabel21.setText("批准文号");
			jLabel22.setBounds(340, 194, 61, 19);
			jLabel22.setText("合同协议号");
			jLabel23.setBounds(503, 194, 50, 19);
			jLabel23.setText("成交方式");
			jLabel24.setBounds(188, 217, 49, 20);
			jLabel24.setText("运费");
			jLabel25.setBounds(26, 241, 50, 19);
			jLabel25.setText("保费");
			jLabel26.setBounds(438, 241, 35, 19);
			jLabel26.setText("杂费");
			jLabel27.setBounds(27, 266, 49, 19);
			jLabel27.setText("件数");
			jLabel27.setForeground(java.awt.Color.blue);
			jLabel28.setBounds(188, 266, 49, 19);
			jLabel28.setText("包装种类");
			jLabel29.setBounds(387, 266, 28, 19);
			jLabel29.setText("毛重");
			jLabel29.setForeground(java.awt.Color.blue);
			jLabel30.setBounds(521, 266, 28, 19);
			jLabel30.setText("净重");
			jLabel30.setForeground(java.awt.Color.blue);
			jLabel31.setBounds(27, 289, 49, 19);
			jLabel31.setText("集装箱号");
			jLabel31.setForeground(java.awt.Color.blue);
			jLabel32.setBounds(249, 289, 49, 19);
			jLabel32.setText("随附单据");
			jLabel33.setBounds(433, 289, 49, 19);
			jLabel33.setText("生产厂家");
			jLabel34.setBounds(27, 313, 49, 19);
			jLabel34.setText("申报单位");
			jLabel35.setBounds(293, 313, 25, 19);
			jLabel35.setText("币制");
			jLabel35.setForeground(java.awt.Color.blue);
			jLabel36.setBounds(420, 313, 38, 19);
			jLabel36.setText("录入员");
			jLabel37.setBounds(27, 338, 49, 19);
			jLabel37.setText("客户名称");
			jLabel37.setForeground(java.awt.Color.blue);
			jLabel38.setBounds(293, 338, 25, 19);
			jLabel38.setText("汇率");
			jLabel39.setBounds(447, 338, 51, 19);
			jLabel39.setText("报送海关");
			jLabel40.setBounds(27, 362, 49, 19);
			jLabel40.setText("备注");
			jLabel41.setBounds(293, 362, 25, 19);
			jLabel41.setText("码头");
			jLabel42.setBounds(447, 362, 72, 19);
			jLabel42.setText("所有集装箱号");
			lbEmsNo.setBounds(189, 10, 49, 21);
			lbEmsNo.setText("帐册编号");
			lbCompanyEmsNo.setBounds(326, 10, 67, 21);
			lbCompanyEmsNo.setText("帐册流水号");
			jLabel45.setBounds(491, 11, 81, 19);
			jLabel45.setText("报关单流水号");
			lbCustomsBroker.setBounds(28, 10, 49, 21);
			lbCustomsBroker.setText("报关行");
			jLabel47.setBounds(27, 386, 49, 19);
			jLabel47.setText("保税仓库");
			jLabel48.setBounds(186, 386, 51, 19);
			jLabel48.setText("发票号码");
			jLabel48.setForeground(java.awt.Color.blue);
			jLabel49.setForeground(java.awt.Color.blue);
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
			pnBaseInfo.add(getBtnAuthorizeFile(), null);
			pnBaseInfo.add(jLabel22, null);
			pnBaseInfo.add(getTfContract(), null);
			pnBaseInfo.add(jLabel23, null);
			pnBaseInfo.add(getCbbTransac(), null);
			pnBaseInfo.add(jLabel24, null);
			pnBaseInfo.add(getCbbFreightageType(), null);
			pnBaseInfo.add(getTfFreightage(), null);
			pnBaseInfo.add(jLabel25, null);
			pnBaseInfo.add(getCbbInsuranceType(), null);
			pnBaseInfo.add(getTfInsurance(), null);
			pnBaseInfo.add(jLabel26, null);
			pnBaseInfo.add(getCbbIncidentalExpensesType(), null);
			pnBaseInfo.add(getTfIncidentalExpenses(), null);
			pnBaseInfo.add(jLabel27, null);
			pnBaseInfo.add(getTfCommodityNum(), null);
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
			pnBaseInfo.add(getTfManufacturer(), null);
			pnBaseInfo.add(getBtnManufacturer(), null);
			pnBaseInfo.add(jLabel34, null);
			pnBaseInfo.add(getCbbDeclarationCompany(), null);
			pnBaseInfo.add(jLabel35, null);
			pnBaseInfo.add(getCbbCurrency(), null);
			pnBaseInfo.add(jLabel36, null);
			pnBaseInfo.add(getTfCreater(), null);
			pnBaseInfo.add(jLabel491, null);
			pnBaseInfo.add(getTfCreateDate(), null);
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
			pnBaseInfo.add(getBtnTransferCustoms(), null);
			pnBaseInfo.add(getBtnPreCustomsDeclarationCode(), null);
			pnBaseInfo.add(lbEmsNo, null);
			pnBaseInfo.add(lbCompanyEmsNo, null);
			pnBaseInfo.add(getTfEmsNo(), null);
			pnBaseInfo.add(getTfCompanyEmsNo(), null);
			pnBaseInfo.add(jLabel45, null);
			pnBaseInfo.add(getTfCustomsDeclarationSerialNumber(), null);
			pnBaseInfo.add(getCbbBalanceMode(), null);
			pnBaseInfo.add(lbCustomsBroker, null);
			pnBaseInfo.add(getTfDeclaraCustomsBroker(), null);
			pnBaseInfo.add(getTfBondedWarehouse(), null);
			pnBaseInfo.add(jLabel47, null);
			pnBaseInfo.add(jLabel48, null);
			pnBaseInfo.add(getTfInvoiceCode(), null);
			pnBaseInfo.add(getBtnInvoice(), null);
			pnBaseInfo.add(jLabel49, null);
			pnBaseInfo.add(getTfCustomsEnvelopBillNo(), null);
			pnBaseInfo.add(getBtnFptAppNo(), null);
			pnBaseInfo.add(getTfBillOfLading(), null);
			pnBaseInfo.add(getJButton9(), null);
			pnBaseInfo.add(jInspect, null);
			pnBaseInfo.add(getTfInspect(), null);
			pnBaseInfo.add(jLabel50, null);
			pnBaseInfo.add(getCbbFeeCurr(), null);
			pnBaseInfo.add(jLabel502, null);
			pnBaseInfo.add(getCbbInsurCurr(), null);
			pnBaseInfo.add(jLabel501, null);
			pnBaseInfo.add(getCbbOtherCurr(), null);
			pnBaseInfo.add(jLabel471, null);
			pnBaseInfo.add(getTfConnectNo(), null);
			pnBaseInfo.add(jLabel472, null);
			pnBaseInfo.add(getTfConnectDeclarationNo(), null);
			pnBaseInfo.add(jLabel1, null);
			pnBaseInfo.add(getTfdomesticConveyanceCode(), null);
			pnBaseInfo.add(getLabel());
			pnBaseInfo.add(getTfVoyageNo());
		}
		return pnBaseInfo;
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

	protected JComboBox getCbbImpExpType() {
		if (cbbImpExpType == null) {
			cbbImpExpType = new JComboBox();
			cbbImpExpType.setBounds(78, 35, 108, 20);
		}
		return cbbImpExpType;
	}

	/**
	 * 当选定的报关类型在报关单参数设置中没有预设值时，返回初值;firstOpen是用来控制cbbImpExpType的初始化
	 * 
	 */
	private void showPrimaryData(Integer type) {
		if (firstOpen == 1 || firstOpen == 2) {
			firstOpen++;
		} else if (firstOpen != 1 && firstOpen != 2) {
			cbbDeclarationCustoms.setSelectedItem(null);
			cbbLevyKind.setSelectedItem(null);
			// cbbBillOfLading.setSelectedItem(null);
			cbbBalanceMode.setSelectedItem(null);

			cbbTradeMode.setSelectedItem(null);
			tfConveyance.setText("");

			cbbTransac.setSelectedItem(null);
			cbbCountryOfLoadingOrUnloading.setSelectedItem(null);
			cbbPredock.setSelectedItem(null);
			cbbPortOfLoadingOrUnloading.setSelectedItem(null);
			cbbWrapType.setSelectedItem(null);
			cbbCustoms.setSelectedItem(null);
			cbbTransferMode.setSelectedItem(null);
			cbbDomesticDestinationOrSource.setSelectedItem(null);
		}
	}

	private void setTradeMode() {
		if (cbbImpExpType.getSelectedItem() != null
				&& Integer.valueOf(
						((ItemProperty) cbbImpExpType.getSelectedItem())
								.getCode()).equals(
						ImpExpType.TRANSFER_FACTORY_EXPORT)) {
			List list = customBaseAction.findTrade("name", "来料深加工");
			if (list != null && list.size() > 0) {
				Trade trade = (Trade) list.get(0);
				cbbTradeMode.setSelectedItem(trade);
			}
		}
	}

	protected JTextField getTfPreCustomsDeclarationCode() {
		if (tfPreCustomsDeclarationCode == null) {
			tfPreCustomsDeclarationCode = new JTextField();
			tfPreCustomsDeclarationCode.setBounds(239, 37, 85, 21);
			tfPreCustomsDeclarationCode.setEditable(false);
		}
		return tfPreCustomsDeclarationCode;
	}

	protected JTextField getTfUnificationCode() {
		if (tfUnificationCode == null) {
			tfUnificationCode = new JTextField();
			tfUnificationCode.setBounds(487, 36, 87, 21);
			tfUnificationCode.setEditable(false);
			tfUnificationCode.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
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
			// KeyAdapterControl.setAddListener(true);
			// cbbCustoms.requestFocus(true);
			cbbCustoms.setBounds(78, 61, 108, 20);
		}
		return cbbCustoms;
	}

	protected JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(239, 62, 143, 21);
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
			cbbImpExpDate.setBounds(436, 62, 84, 20);
		}
		return cbbImpExpDate;
	}

	protected JCalendarComboBox getCbbDeclarationDate() {
		if (cbbDeclarationDate == null) {
			cbbDeclarationDate = new JCalendarComboBox();
			// cbbDeclarationDate.addKeyListener(new FocusActionListner(
			// getCbbTransferMode()));
			cbbDeclarationDate.addPropertyChangeListener("value",
					new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {

							setRate();

						}
					});
			cbbDeclarationDate.setBounds(576, 62, 106, 21);
		}
		return cbbDeclarationDate;
	}

	protected JTextField getTfManageCompany() {
		if (tfManageCompany == null) {
			tfManageCompany = new JTextField();
			tfManageCompany.setBounds(78, 87, 262, 21);
			tfManageCompany.setEditable(false);
		}
		return tfManageCompany;
	}

	protected JTextField getTfAcceptGoodsCompany() {
		if (tfAcceptGoodsCompany == null) {
			tfAcceptGoodsCompany = new JTextField();
			tfAcceptGoodsCompany.setBounds(398, 87, 284, 21);
			tfAcceptGoodsCompany.setEditable(false);
		}
		return tfAcceptGoodsCompany;
	}

	protected JComboBox getCbbTransferMode() {
		if (cbbTransferMode == null) {
			cbbTransferMode = new JComboBox();
			cbbTransferMode.setBounds(78, 114, 108, 20);
			cbbTransferMode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (customsDeclaration != null) {
								if (cbbTransferMode.getSelectedItem() != null) {
									customsDeclaration
											.setTransferMode((Transf) cbbTransferMode
													.getSelectedItem());
								} else {
									customsDeclaration.setTransferMode(null);
								}
							}
						}
					});
		}
		return cbbTransferMode;
	}

	protected JTextField getTfConveyance() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(239, 115, 103, 21);
			// 加入自定义位数故屏蔽掉
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
										DgBaseExportCustomsDeclaration.this,
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
								DgBaseExportCustomsDeclaration.this,
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
		private static final long serialVersionUID = -4176467664125606612L;

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

	//
	// protected JComboBox getCbbBillOfLading() {
	// if (cbbBillOfLading == null) {
	// cbbBillOfLading = new JComboBox();
	// cbbBillOfLading.setBounds(659, 125, 72, 21);
	// }
	// return cbbBillOfLading;
	// }

	protected JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(78, 140, 108, 20);
		}
		return cbbTradeMode;
	}

	protected JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(239, 140, 97, 20);
		}
		return cbbLevyKind;
	}

	protected JTextField getTfLicense() {
		if (tfLicense == null) {
			tfLicense = new JTextField();
			tfLicense.setBounds(78, 166, 169, 21);
		}
		return tfLicense;
	}

	protected JComboBox getCbbCountryOfLoadingOrUnloading() {
		if (cbbCountryOfLoadingOrUnloading == null) {
			cbbCountryOfLoadingOrUnloading = new JComboBox();
			cbbCountryOfLoadingOrUnloading.setBounds(321, 167, 139, 20);
		}
		return cbbCountryOfLoadingOrUnloading;
	}

	protected JComboBox getCbbDomesticDestinationOrSource() {
		if (cbbDomesticDestinationOrSource == null) {
			cbbDomesticDestinationOrSource = new JComboBox();
			cbbDomesticDestinationOrSource.setBounds(528, 166, 154, 20);
		}
		return cbbDomesticDestinationOrSource;
	}

	protected JComboBox getCbbPortOfLoadingOrUnloading() {
		if (cbbPortOfLoadingOrUnloading == null) {
			cbbPortOfLoadingOrUnloading = new JComboBox();
			cbbPortOfLoadingOrUnloading.setBounds(78, 193, 108, 20);
		}
		return cbbPortOfLoadingOrUnloading;
	}

	protected JTextField getTfAuthorizeFile() {
		if (tfAuthorizeFile == null) {
			tfAuthorizeFile = new JTextField();
			tfAuthorizeFile.setBounds(239, 193, 80, 21);
			/*
			 * tfAuthorizeFile.addFocusListener(new
			 * java.awt.event.FocusAdapter() { public void
			 * focusLost(java.awt.event.FocusEvent e) { if
			 * (tfAuthorizeFile.getText() !=
			 * null&&!"".equals(tfAuthorizeFile.getText().trim()) &&
			 * tfAuthorizeFile.getText().trim().length() != 9) {
			 * JOptionPane.showMessageDialog(
			 * DgBaseExportCustomsDeclaration.this, "批准文号应该为9位,请重新填写！", "提示！",
			 * JOptionPane.WARNING_MESSAGE); tfAuthorizeFile.setText(""); } }
			 * });
			 */
		}
		return tfAuthorizeFile;
	}

	protected JTextField getTfContract() {
		if (tfContract == null) {
			tfContract = new JTextField();
			tfContract.setBounds(405, 192, 97, 21);

		}
		return tfContract;
	}

	protected JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.addKeyListener(new FocusActionListner(
					getTfCommodityNum()));
			cbbTransac.setBounds(555, 193, 127, 20);
		}
		return cbbTransac;
	}

	protected JComboBox getCbbFreightageType() {
		if (cbbFreightageType == null) {
			cbbFreightageType = new JComboBox();
			cbbFreightageType.setBounds(239, 217, 99, 20);
		}
		return cbbFreightageType;
	}

	protected JComboBox getCbbInsuranceType() {
		if (cbbInsuranceType == null) {
			cbbInsuranceType = new JComboBox();
			cbbInsuranceType.setBounds(78, 241, 108, 19);
		}
		return cbbInsuranceType;
	}

	protected JComboBox getCbbIncidentalExpensesType() {
		if (cbbIncidentalExpensesType == null) {
			cbbIncidentalExpensesType = new JComboBox();
			cbbIncidentalExpensesType.setBounds(475, 241, 99, 19);
		}
		return cbbIncidentalExpensesType;
	}

	protected JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(239, 266, 146, 19);
		}
		return cbbWrapType;
	}

	protected JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(417, 266, 101, 19);
			// tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	protected JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(553, 266, 129, 19);
			// tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	class CustomDocument extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7389972893440228088L;

		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}

			if (super.getLength() >= 11 || str.length() > 11
					|| super.getLength() + str.length() > 11) {
				return;
			}
			// if(super.getLength() <= 11 || str.length() < 11
			// ||super.getLength() + str.length() < 11){
			// JOptionPane.showMessageDialog(
			// DgBaseExportCustomsDeclaration.this, "集装箱号不够11位，请查阅",
			// "提示", 0);
			// return;
			// }
			super.insertString(offs, str, a);
		}
	}

	protected JTextField getTfContainerNum() {
		if (tfContainerNum == null) {
			tfContainerNum = new JTextField();
			// tfContainerNum.setText("0"); //不默认为“0”主要为检查是本有号而检查通过
			tfContainerNum.setBounds(78, 289, 168, 19);
		}
		return tfContainerNum;
	}

	protected JTextField getTfAttachedBill() {
		if (tfAttachedBill == null) {
			tfAttachedBill = new JTextField();
			tfAttachedBill.setBounds(302, 289, 106, 19);
			tfAttachedBill.setEditable(false);
		}
		return tfAttachedBill;
	}

	protected JButton getBtnAttachedBill() {
		if (btnAttachedBill == null) {
			btnAttachedBill = new JButton();
			btnAttachedBill.setBounds(408, 289, 23, 19);
			btnAttachedBill.setText("...");
			btnAttachedBill.addKeyListener(new FocusActionListner(
					getTfManufacturer()));
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

	protected JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(320, 313, 92, 19);
			cbbCurrency
					.addKeyListener(new FocusActionListner(getCbbCustomer()));
			cbbCurrency.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					setRate();
				}
			});
		}
		return cbbCurrency;
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

	protected JTextField getTfCreater() {
		if (tfCreater == null) {
			tfCreater = new JTextField();
			tfCreater.setBounds(463, 313, 65, 19);
			tfCreater.setEditable(false);
		}
		return tfCreater;
	}

	protected JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(79, 338, 212, 19);
			cbbCustomer.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

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
							getCbbTradeMode().setSelectedItem(sc.getTrade());
						}
						if (sc.getPredock() != null) {
							getCbbPredock().setSelectedItem(sc.getPredock());
						}
						if (sc.getWarp() != null) {
							getCbbWrapType().setSelectedItem(sc.getWarp());
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
			tfExchangeRate.setBounds(320, 338, 125, 19);
			tfExchangeRate.setFormatterFactory(getDefaultFormatterFactory());
			tfExchangeRate.setEditable(false);
		}
		return tfExchangeRate;
	}

	protected JComboBox getCbbDeclarationCustoms() {
		if (cbbDeclarationCustoms == null) {
			cbbDeclarationCustoms = new JComboBox();
			// cbbDeclarationCustoms.addKeyListener(new FocusActionListner(
			// getCbbPredock()));
			cbbDeclarationCustoms.setBounds(506, 338, 176, 19);
		}
		return cbbDeclarationCustoms;
	}

	protected JTextField getTfMemos() {
		if (tfMemos == null) {
			tfMemos = new JTextField();
			tfMemos.setBounds(78, 362, 191, 19);
			tfMemos.setEditable(false);
		}
		return tfMemos;
	}

	protected JButton getBtnMemo() {
		if (btnMemo == null) {
			btnMemo = new JButton();
			btnMemo.setBounds(270, 362, 21, 19);
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
						// 回传的备注信息
						tfMemos.setText(dg.returnMemoValue());

						// 填写备注
						customsDeclaration.setMemos(tfMemos.getText());

						// 设值随附单证
						customsDeclaration.setCertificateCode(dg
								.returnCertificateCodeValue());

						// 随附单证信息
						String attachedBillCodes = dg.returnAttachedBillCode();

						tfAttachedBill.setText(attachedBillCodes);

						customsDeclaration.setAttachedBill(attachedBillCodes);
					}
				}
			});
		}
		return btnMemo;
	}

	protected JComboBox getCbbPredock() {
		if (cbbPredock == null) {
			cbbPredock = new JComboBox();
			// cbbPredock.addKeyListener(new FocusActionListner(getBtnSave()));
			cbbPredock.setBounds(320, 362, 125, 19);
			cbbPredock.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getItem() != null
							&& e.getStateChange() == ItemEvent.SELECTED) {
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
							if (tfMemos.getText().contains("转至")) {
								tfMemos.setText(tfMemos.getText() + "[装卸口岸]:"
										+ shortName);
							} else {
								tfMemos.setText("[装卸口岸]:" + shortName
										+ tfMemos.getText());
							}
						} else if (tfMemos.getText().indexOf("装卸口岸") > -1) {
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
			tfAllContainerNum.setBounds(520, 362, 139, 19);
			tfAllContainerNum.setEditable(false);
		}
		return tfAllContainerNum;
	}

	protected JButton getBtnContainer() {
		if (btnContainer == null) {
			btnContainer = new JButton();
			btnContainer.setBounds(661, 362, 21, 19);
			btnContainer.setText("...");
			btnContainer.addKeyListener(new FocusActionListner(getBtnSave()));
			btnContainer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCustomsDeclarationContainer dg = new DgCustomsDeclarationContainer();

					dg.setBaseCustomsDeclaration(DgBaseExportCustomsDeclaration.this.customsDeclaration);

					dg.setCustomsDeclarationDataState(dataState);

					dg.setBaseEncAction(baseEncAction);

					dg.setContainers(containers);

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
		switch (impExpType) {
		case ImpExpType.DIRECT_EXPORT:// 成品出口
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT:// 转厂出口
			break;
		case ImpExpType.BACK_MATERIEL_EXPORT:// 退料出口
			break;
		case ImpExpType.REWORK_EXPORT:// 返工复出
			break;
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT: // 边角料退港
			break;
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:// 边角料内销
			break;
		case ImpExpType.GENERAL_TRADE_EXPORT:// 一般贸易出口
			break;
		default:
			break;
		}

	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected void initUIComponents() {
		// 初始化报关员与联系电话
		this.jComboBox.removeAllItems();
		List customsUserList = materialManageAction
				.findCustomsUser(new Request(CommonVars.getCurrUser()));
		for (int i = 0; i < customsUserList.size(); i++) {
			CustomsUser user = (CustomsUser) customsUserList.get(i);
			this.jComboBox.addItem(user.getName());
		}
		this.jComboBox.setUI(new CustomBaseComboBoxUI(100));
		String name = jComboBox.getSelectedItem() == null ? null : jComboBox
				.getSelectedItem().toString();
		String tel = materialManageAction.findCustomsUserTel(new Request(
				CommonVars.getCurrUser()), name);
		tfTelephone.setText(tel);

		// 初始化司机纸海关编号
		/*
		 * this.jComboBox1.removeAllItems(); List complex = materialManageAction
		 * .findMotorCodeComplex(new Request(CommonVars.getCurrUser())); for
		 * (int i = 0; i < complex.size(); i++) { String complex1 =
		 * complex.get(i).toString(); this.jComboBox1.addItem(complex1); }
		 * this.jComboBox1.setUI(new CustomBaseComboBoxUI(100));
		 */
		// 初始化出口类型
		this.cbbImpExpType.removeAllItems();
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_EXPORT).toString(), "成品出口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "转厂出口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_MATERIEL_EXPORT).toString(), "退料出口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REMAIN_FORWARD_EXPORT).toString(), "余料结转"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbImpExpType.setSelectedIndex(-1);
		// 初始化出口口岸
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
		cbbWrapType.setSelectedItem(null);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");

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
		// 初始化结汇方式
		this.cbbBalanceMode.setModel(CustomBaseModel.getInstance()
				.getBalanceModeModel());
		this.cbbBalanceMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbBalanceMode.setMaximumRowCount(CustomBaseModel.getInstance()
				.getBalanceModeModel().getSize());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBalanceMode);
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
		// // 初始化提运单号
		// List tmplist = materialManageAction.findMotorCode(new Request(
		// CommonVars.getCurrUser()));
		// this.cbbBillOfLading.setModel(new DefaultComboBoxModel(tmplist
		// .toArray()));
		// this.cbbBillOfLading.setRenderer(CustomBaseRender.getInstance()
		// .getRender("code", "homeCard"));
		// CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
		// this.cbbBillOfLading, "code", "homeCard");
		// cbbBillOfLading.setSelectedItem(null);
		// 初始申报单位systemAction
		List companyList = systemAction.findEnableCompanies();
		this.cbbDeclarationCompany.setModel(new DefaultComboBoxModel(
				companyList.toArray()));
		this.cbbDeclarationCompany.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclarationCompany, "code", "name", 270);
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
			this.customsDeclaration = this.newCustomsDeclaration();
			this.containers = new ArrayList();
			// this.customsDeclaration.setSerialNumber(this.baseEncAction
			// .getCustomsDeclarationSerialNumber(new Request(CommonVars
			// .getCurrUser()), ImpExpFlag.EXPORT, emsHead
			// .getEmsNo()));
			this.customsDeclaration.setEmsHeadH2k(this.emsHead.getEmsNo());
			this.customsDeclaration.setImpExpFlag(Integer
					.valueOf(ImpExpFlag.EXPORT));
			this.customsDeclaration.setCreater(CommonVars.getCurrUser());
			this.customsDeclaration.setCompany(CommonVars.getCurrUser()
					.getCompany());
			customsDeclaration.setTradeCode(emsHead.getTradeCode());// 经营单位
			customsDeclaration.setTradeName(emsHead.getTradeName());
			customsDeclaration.setMachCode(emsHead.getMachCode());// 加工单位
			customsDeclaration.setMachName(emsHead.getMachName());
			customsDeclaration.setImpExpType(ImpExpType.DIRECT_EXPORT);
			this.customsDeclaration
					.setManufacturer(getBreif(((Company) CommonVars
							.getCurrUser().getCompany()).getCode()));
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

			other1 = systemAction.findCustomsSet(
					new Request(CommonVars.getCurrUser(), true),
					this.customsDeclaration.getImpExpType());

			if (other1 != null) {
				customsDeclaration.setDeclarationCustoms(other1
						.getDeclarationCustoms());
				customsDeclaration.setLevyKind(other1.getCustomlevyKind());
				customsDeclaration.setBillOfLading(other1.getBillOfLading());
				customsDeclaration.setBalanceMode(other1.getBalanceMode());
				if (customsDeclaration.getTradeMode() == null) {
					customsDeclaration.setTradeMode(other1.getTradeMode());
				}
				customsDeclaration.setConveyance(other1.getConveyance());

				customsDeclaration.setUses(other1.getUses());
				customsDeclaration.setTransac(other1.getTransac());
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

			// 后加来自系统参数设置
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
		// this.cbbBillOfLading.setSelectedItem(null);
		this.tfBillOfLading.setText("");
		this.tfCommodityNum.setText("0");
		this.tfManageCompany.setText("");
		this.tfContainerNum.setText("");
		this.tfContract.setText("");
		this.tfConveyance.setText("");
		this.cbbCountryOfLoadingOrUnloading.setSelectedItem(null);
		this.tfCreater.setText("");
		this.cbbCustomer.setSelectedItem(null);
		this.cbbCustoms.setSelectedItem(null);
		this.tfCustomsDeclarationCode.setText("");
		this.tfVoyageNo.setText("");
		this.cbbDeclarationCompany.setSelectedItem(null);
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
		// cbbImpExpType.setSelectedIndex(-1);
		this.tfIncidentalExpenses.setValue(Double.valueOf(0));
		cbbIncidentalExpensesType.setSelectedIndex(-1);
		this.tfInsurance.setValue(Double.valueOf(0));
		cbbInsuranceType.setSelectedIndex(-1);
		cbbLevyKind.setSelectedItem(null);
		this.tfLicense.setText("");
		this.tfMemos.setText("");
		this.tfBondedWarehouse.setText("");
		cbbPortOfLoadingOrUnloading.setSelectedItem(null);
		this.tfEmsNo.setText("");
		this.tfPreCustomsDeclarationCode.setText("");
		cbbPredock.setSelectedItem(null);
		cbbTradeMode.setSelectedItem(null);
		cbbTransac.setSelectedItem(null);
		cbbTransferMode.setSelectedItem(null);
		this.tfUnificationCode.setText("");
		// cbbUses.setSelectedItem(null);
		cbbWrapType.setSelectedItem(null);
		this.tfAllContainerNum.setText("");
		// this.tfContainerNum.setText("");
		this.jComboBox.setSelectedIndex(-1);
		this.tfTelephone.setText("");
		this.tfConnectNo.setText("");
		this.tfConnectDeclarationNo.setText("");
	}

	/**
	 * 显示报关数据
	 * 
	 */
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

		// 随附单证
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
			this.tfCommodityNum.setValue(new Integer("0"));
		}
		if (this.customsDeclaration.getTradeName() != null) {
			this.tfManageCompany
					.setText(this.customsDeclaration.getTradeName());
		} else {
			this.tfManageCompany.setText("");
		}

		if (containers == null) {

			this.containers = this.baseEncAction
					.findContainerByCustomsDeclaration(
							new Request(CommonVars.getCurrUser()),
							this.customsDeclaration);
		}

		// 集装箱号
		if (StringUtils.isNotBlank(customsDeclaration.getContainerNum())) {

			this.tfContainerNum.setText(containers!=null?Container
					.getAllConvertToContainerCode(containers):this.customsDeclaration
					.getContainerNum());
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

		this.cbbDeclarationCompany.setSelectedItem(this.customsDeclaration
				.getDeclarationCompany());

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
		// if (this.customsDeclaration.getBalanceMode() != null) {
		this.cbbBalanceMode.setSelectedItem(this.customsDeclaration
				.getBalanceMode());
		// } else {
		// this.tfPerTax.setValue(null);
		// }
		cbbPortOfLoadingOrUnloading.setSelectedItem(this.customsDeclaration
				.getPortOfLoadingOrUnloading());
		if (this.customsDeclaration.getEmsHeadH2k() != null) {
			this.tfEmsNo.setText(this.customsDeclaration.getEmsHeadH2k());
		} else {
			this.tfEmsNo.setText("");
		}

		// 预录入号修改 by xxm
		if (this.customsDeclaration.getTempPreCode() != null
				&& !this.customsDeclaration.getTempPreCode().equals("")) {
			this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
					.getTempPreCode());
		} else {
			this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
					.getPreCustomsDeclarationCode());
		}
		/*
		 * if (this.customsDeclaration.getPreCustomsDeclarationCode() != null) {
		 * this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
		 * .getPreCustomsDeclarationCode()); } else {
		 * this.tfPreCustomsDeclarationCode.setText(""); }
		 */
		cbbPredock.setSelectedItem(this.customsDeclaration.getPredock());
		if (null == this.customsDeclaration.getMemos()
				|| this.customsDeclaration.getMemos().equals("")) {
			// SXK 修改于7.14
			// this.tfMemos.setText("");
		} else {
			this.tfMemos.setText(this.customsDeclaration.getMemos());
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

		// 生产厂家
		if (this.customsDeclaration.getManufacturer() != null) {

			this.tfManufacturer.setText(this.customsDeclaration
					.getManufacturer().getName());

		} else {

			this.tfManufacturer.setText("");
		}

		cbbWrapType.setSelectedItem(this.customsDeclaration.getWrapType());

		// 所有集装箱号信息
		if (StringUtils.isNotBlank(customsDeclaration.getAllContainerNumLong())) {

			this.tfAllContainerNum.setText(this.customsDeclaration
					.getAllContainerNumLong());
		} else {
			tfAllContainerNum
					.setText(Container.getAllContainerCode(containers));

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
		if (this.customsDeclaration.getInvoiceCode() != null) {
			this.tfInvoiceCode
					.setText(this.customsDeclaration.getInvoiceCode());
		} else {
			this.tfInvoiceCode.setText("");
		}
		if (this.customsDeclaration.getCustomsEnvelopBillNo() != null) {
			this.tfCustomsEnvelopBillNo.setText(this.customsDeclaration
					.getCustomsEnvelopBillNo());
		} else {
			this.tfCustomsEnvelopBillNo.setText("");
		}
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
			// 报关单暂存 G
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
		this.customsDeclaration.setDeclaraCustomsBroker(tfDeclaraCustomsBroker
				.getText());
		if (this.cbbImpExpType.getSelectedIndex() > -1) {
			this.customsDeclaration.setImpExpType(Integer
					.valueOf(((ItemProperty) this.cbbImpExpType
							.getSelectedItem()).getCode()));
		} else {
			this.customsDeclaration.setImpExpType(null);
		}

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
		if (this.tfVoyageNo.getText() != null) {
			this.customsDeclaration.setVoyageNo(this.tfVoyageNo.getText()
					.trim());
		}
		if (this.tfCreateDate.getDate() != null) {
			this.customsDeclaration.setCreateDate(tfCreateDate.getDate());
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
		this.customsDeclaration.setBillOfLading(this.tfBillOfLading.getText());
		this.customsDeclaration.setTradeMode((Trade) this.cbbTradeMode
				.getSelectedItem());
		this.customsDeclaration.setLevyKind((LevyKind) this.cbbLevyKind
				.getSelectedItem());
		this.customsDeclaration
				.setBalanceMode((BalanceMode) this.cbbBalanceMode
						.getSelectedItem());
		this.customsDeclaration
				.setDeclarationCompany((Company) this.cbbDeclarationCompany
						.getSelectedItem());
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
			try {
				java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
						"######");
				Integer num = Integer.valueOf(decimalFormat
						.format(this.tfCommodityNum.getValue()));
				if (num > 999999999) {
					JOptionPane.showMessageDialog(null, "件数不能超过999999999");
					this.customsDeclaration.setCommodityNum(new Integer(0));
					tfCommodityNum.setText("0");
				} else {
					this.customsDeclaration.setCommodityNum(Integer
							.valueOf(decimalFormat.format(this.tfCommodityNum
									.getValue())));
				}
			} catch (NumberFormatException numberFormatEx) {
				JOptionPane.showMessageDialog(null, "件数不能超过999999999");
				this.customsDeclaration.setCommodityNum(new Integer(0));
				tfCommodityNum.setText("0");
			}
		} else {
			this.customsDeclaration.setCommodityNum(null);
		}
		this.customsDeclaration.setWrapType((Wrap) this.cbbWrapType
				.getSelectedItem());

		if (this.tfGrossWeight.getValue() != null
				&& !this.tfGrossWeight.getText().equals("")) {
			this.customsDeclaration.setGrossWeight(Double
					.valueOf(this.tfGrossWeight.getText().toString()));
		} else {
			this.customsDeclaration.setGrossWeight(null);
		}

		if (this.tfNetWeight.getValue() != null
				&& !this.tfNetWeight.getText().equals("")) {
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
		if (this.jComboBox.getSelectedItem() != null) {
			this.customsDeclaration.setCustomser(this.jComboBox
					.getSelectedItem().toString());
		} else {
			this.customsDeclaration.setCustomser(null);
		}
		this.customsDeclaration.setTelephone(this.tfTelephone.getText());
		this.customsDeclaration.setAllContainerNum(this.tfAllContainerNum
				.getText());
		this.customsDeclaration.setAttachedBill(this.tfAttachedBill.getText());
		this.customsDeclaration.setContainerNum(this.tfContainerNum.getText());
		this.customsDeclaration.setBondedWarehouse(this.tfBondedWarehouse
				.getText());
		this.customsDeclaration.setInvoiceCode(this.tfInvoiceCode.getText());
		this.customsDeclaration
				.setCustomsEnvelopBillNo(this.tfCustomsEnvelopBillNo.getText());

		this.customsDeclaration.setUnificationCode(tfUnificationCode.getText());// 统一编号
		tfUnificationCode.setEditable(false);

		Brief brief = null;
		if (tfManufacturer.getText() != null
				&& !tfManufacturer.getText().trim().equals("")) {
			brief = materialManageAction.findBrief(
					new Request(CommonVars.getCurrUser(), true), tfManufacturer
							.getText().trim());
		}
		customsDeclaration.setManufacturer(brief);
		if (brief == null)
			tfManufacturer.setText("");
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
		int index = jTabbedPane.getSelectedIndex();
		boolean isEffective = false;
		boolean isSend = false;
		if (customsDeclaration != null) {
			isEffective = customsDeclaration.getEffective() == null ? false
					: customsDeclaration.getEffective().booleanValue();
			isSend = customsDeclaration.getIsSend() == null ? false
					: customsDeclaration.getIsSend().booleanValue();
		}

		btnAdd.setEnabled(dataState == DataState.BROWSE && index == 1
				&& !isEffective);

		// 2015-3-23 : 暂时不判断 申报状态 来控制 修改按钮 是否可用 by zcj
		btnEdit.setEnabled(dataState == DataState.BROWSE && !isEffective);

		jButton.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton7.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton6.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnSortChecked
				.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton9.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton1.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton8.setEnabled(dataState == DataState.BROWSE && !isEffective);
		jButton2.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnDelete.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnSave.setEnabled(dataState != DataState.BROWSE && !isEffective);
		btnCancel.setEnabled(dataState != DataState.BROWSE && !isEffective);
		btnDelete.setVisible(index == 1);
		btnEffect.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnUnreel.setEnabled(dataState == DataState.BROWSE && isEffective);
		btnUnreel.setVisible(index == 0);
		btnSave.setVisible(index == 0 || index == 2);
		btnCancel.setVisible(index == 0);
		btnSplit.setEnabled(dataState == DataState.BROWSE && !isEffective);
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
		this.tfdomesticConveyanceCode
				.setEditable(dataState != DataState.BROWSE);
		this.tfBillOfLading.setEditable(dataState != DataState.BROWSE);
		this.cbbTradeMode.setEnabled(dataState != DataState.BROWSE);
		this.cbbLevyKind.setEnabled(dataState != DataState.BROWSE);
		this.cbbBalanceMode.setEnabled(dataState != DataState.BROWSE);
		this.tfLicense.setEditable(dataState != DataState.BROWSE);
		this.cbbCountryOfLoadingOrUnloading
				.setEnabled(dataState != DataState.BROWSE);
		this.cbbDomesticDestinationOrSource
				.setEnabled(dataState != DataState.BROWSE);
		this.cbbPortOfLoadingOrUnloading
				.setEnabled(dataState != DataState.BROWSE);
		this.tfAuthorizeFile.setEditable(dataState != DataState.BROWSE);
		this.cbbTransac.setEnabled(dataState != DataState.BROWSE);
		this.cbbFreightageType.setEnabled(dataState != DataState.BROWSE);
		this.tfFreightage.setEditable(dataState != DataState.BROWSE);
		this.cbbInsuranceType.setEnabled(dataState != DataState.BROWSE);
		tfContainerNum.setEditable(dataState != DataState.BROWSE);
		this.tfInsurance.setEditable(dataState != DataState.BROWSE);
		this.cbbIncidentalExpensesType
				.setEnabled(dataState != DataState.BROWSE);
		this.tfIncidentalExpenses.setEditable(dataState != DataState.BROWSE);
		this.cbbWrapType.setEnabled(dataState != DataState.BROWSE);
		this.tfCommodityNum.setEditable(dataState != DataState.BROWSE);
		this.tfGrossWeight.setEditable(dataState != DataState.BROWSE);
		this.tfNetWeight.setEditable(dataState != DataState.BROWSE);
		this.btnAttachedBill.setEnabled(dataState != DataState.BROWSE);
		this.tfBondedWarehouse.setEditable(dataState != DataState.BROWSE);
		this.cbbDeclarationCompany.setEnabled(dataState != DataState.BROWSE);
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
		this.tfInvoiceCode.setEditable(dataState != DataState.BROWSE);
		this.tfInspect.setEditable(dataState != DataState.BROWSE);
		this.btnInvoice.setEnabled(dataState != DataState.BROWSE);
		this.tfConnectNo.setEditable(dataState != DataState.BROWSE);
		this.tfConnectDeclarationNo.setEditable(dataState != DataState.BROWSE);
		boolean isEdit = cbbImpExpType.getSelectedItem() != null
				&& Integer.valueOf(
						((ItemProperty) cbbImpExpType.getSelectedItem())
								.getCode()).equals(
						ImpExpType.TRANSFER_FACTORY_EXPORT);
		this.btnFptAppNo.setEnabled(dataState != DataState.BROWSE && isEdit);
		this.tfCustomsEnvelopBillNo.setEditable(dataState != DataState.BROWSE
				&& isEdit);
		this.jButton9.setEnabled(dataState != DataState.BROWSE);
		btnAuthorizeFile.setEnabled(dataState != DataState.BROWSE);
		this.tfPreCustomsDeclarationCode
				.setEditable(dataState != DataState.BROWSE
						&& isManualPreCode != null && isManualPreCode);
		int type = CommonVars.getCustomsType(customsDeclaration);
		jButton2.setVisible(type == ProjectType.BCUS);
		jButton4.setVisible(type == ProjectType.BCUS);
		jButton6.setVisible(type == ProjectType.BCUS);
		jButton8.setVisible(type == ProjectType.DZSC);
		// tfContract.setEditable(dataState != DataState.BROWSE
		// && type == ProjectType.BCUS);
		tfContract.setEditable(dataState != DataState.BROWSE);
		tfManufacturer.setEnabled(dataState != DataState.BROWSE);
		btnManufacturer.setEnabled(dataState != DataState.BROWSE);
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
						list.add(addColumn("商品序号", "commSerialNo", 60,
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
						list.add(addColumn("法定数量", "firstAmount", 80));
						list.add(addColumn("法定单位", "legalUnit.name", 80));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("最终目的国", "country.name", 80));
						list.add(addColumn("单价", "commUnitPrice", 60));
						list.add(addColumn("总价", "commTotalPrice", 60));
						list.add(addColumn("第二法定数量", "secondAmount", 100));
						list.add(addColumn("第二法定单位", "secondLegalUnit.name",
								100));
						list.add(addColumn("用途", "uses.name", 60));
						list.add(addColumn("净重", "netWeight", 60));
						list.add(addColumn("毛重", "grossWeight", 60));
						list.add(addColumn("件(箱)数", "pieces", 60));
						list.add(addColumn("箱号", "boxNo", 100));
						list.add(addColumn("包装种类", "wrapType.name", 100));
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
				commInfoModel.addFooterTypeInfo(new TableFooterType(23,
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
				Double commtotalprice = cddi.getCommTotalPrice(); // 总价
				try {
					String countryname = cddi.getCountry().getName();
				} catch (Exception e) {
					str += "原产国, ";
					is = false;
				}
				if (cddi.getFirstAmount() == null) {
					str += "法定数量, ";
					is = false;
				}

				// 如果是成品需要检查报关商品是否有版本号
				if (!EncCommon.isMaterial(cddi.getBaseCustomsDeclaration()
						.getImpExpType())) {
					if (projectType == ProjectType.BCUS) {
						if (cddi.getVersion() == null
								|| cddi.getVersion().equals("")
								|| cddi.getVersion().length() <= 0
								|| cddi.getVersion().isEmpty()) {
							JOptionPane.showMessageDialog(this,
									"成品版本不允许为空，请修正！", "提示！", 0);
							return true;
						}
					}
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
	 * This method initializes tfFreightage
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfFreightage() {
		if (tfFreightage == null) {
			tfFreightage = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfFreightage.setBounds(341, 217, 108, 20);
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
			tfInsurance.setBounds(188, 241, 87, 19);
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
			tfIncidentalExpenses.setBounds(575, 241, 107, 19);
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
			tfCommodityNum.setBounds(78, 266, 108, 19);
			tfCommodityNum.setDocument(new CustomDocument());
			tfCommodityNum.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfCommodityNum;
	}

	// 判断字符限数
	@SuppressWarnings("serial")
	class CustomsDocument extends PlainDocument {
		public void insertString(int offs, String str, AttributeSet a)
				throws BadLocationException {
			if (str == null) {
				return;
			}
			if (super.getLength() >= 5 || str.length() > 5
					|| super.getLength() + str.length() > 5) {
				return;
			}

			super.insertString(offs, str, a);
		}
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
							expCustomsAuthorityAction.saveCustoms(new Request(
									CommonVars.getCurrUser()));
						} else if (projectType == ProjectType.DZSC) {
							dZSCExpCustomsAuthorityAction
									.saveCustoms(new Request(CommonVars
											.getCurrUser()));
						} else if (projectType == ProjectType.BCS) {
							bCSExpCustomsAuthorityAction
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

		fillData();
		if (customsDeclaration.getImpExpType() == null) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"出口类型不能为空", "提示", JOptionPane.YES_NO_OPTION);
			return false;
		}
		customsDeclaration.setIsEmsCav(false);
		customsDeclaration.setIsCheck(false);
		if (!saveData()) {
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
			btnTransferCustoms.setBounds(575, 34, 107, 23);
			btnTransferCustoms.setText("转关附加");
			btnTransferCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							transferCustoms();

						}
					});
		}
		return btnTransferCustoms;
	}

	protected void transferCustoms() {
		if (tfConveyance.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "运输工具栏目中不能为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!tfConveyance.getText().substring(0, 1).trim().equals("@")) {
			JOptionPane.showMessageDialog(null, "运输工具栏目中第一个字符不是 \"@\" !!",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (cbbTransferMode.getSelectedItem() == null) {// customsDeclaration.getTransferMode()
			// == null
			JOptionPane.showMessageDialog(null, "使用'转关附加'时'运输方式'不能为空", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgTransferCustomsConveyance dg = new DgTransferCustomsConveyance();
		dg.setCustomsDeclaration(customsDeclaration);
		dg.setBaseEncAction(baseEncAction);
		// 如果是汽车运输或江海运输

		if (cbbTransferMode.getSelectedItem() != null
		// && (((Transf) cbbTransferMode.getSelectedItem()).getName()
		// .trim().equals("公路运输")
		// || ((Transf)
		// cbbTransferMode.getSelectedItem()).getName().trim().equals("水路运输")
		// || ((Transf)
		// cbbTransferMode.getSelectedItem()).getName().trim().equals("物流中心")
		// || ((Transf)
		// cbbTransferMode.getSelectedItem()).getName().trim().equals("物流园区")
		// || ((Transf)
		// cbbTransferMode.getSelectedItem()).getName().trim().equals("出口加工")
		// )
		) {

			Transf transf = customBaseAction.findTransf();
			if (customsDeclaration.getDomesticTransferMode() != null) {
				dg.setTransf(customsDeclaration.getDomesticTransferMode());
			} else {
				dg.setTransf(transf);// 汽车运输
			}

			Calendar calendar = cbbImpExpDate.getCalendar();
			dg.setImportDate(String.valueOf(calendar.get(Calendar.YEAR)) + "-"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-"
					+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
			// dg.setNumberPlate(cbbBillOfLading
			// .getSelectedItem() == null ? null
			// : (((MotorCode) cbbBillOfLading
			// .getSelectedItem())
			// .getHomeCard() == null ? null
			// : ((MotorCode) cbbBillOfLading
			// .getSelectedItem())
			// .getHomeCard().trim())); // 提运单号
			dg.setNumberPlate(this.tfBillOfLading.getText());
			dg.setTruckTransfer(true);
		}
		dg.setVisible(true);
	}

	/**
	 * This method initializes btnPreCustomsDeclarationCode
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnPreCustomsDeclarationCode() {
		if (btnPreCustomsDeclarationCode == null) {
			btnPreCustomsDeclarationCode = new JButton();
			btnPreCustomsDeclarationCode.setBounds(325, 35, 108, 23);
			btnPreCustomsDeclarationCode.setText("产生预录入号");
			btnPreCustomsDeclarationCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getylno();
						}
					});
		}
		return btnPreCustomsDeclarationCode;
	}

	protected void getylno() {
		if (JOptionPane.showConfirmDialog(null, "要产生预录入号吗?", "确认", 0) == 0) {
			// 统一编号不为空时，修改预录入编号要提示并晴空
			if (tfUnificationCode.getText() != null
					&& !tfUnificationCode.getText().equals("")) {
				if (JOptionPane.showConfirmDialog(
						DgBaseExportCustomsDeclaration.this,
						"生成新的预录入号时是否同时清空统一编号?\n注：清空统一编号后发送，海关确认为这是一份从未申报过的报关单",
						"确认", 0) == 0) {
					customsDeclaration.setUnificationCode(null);
					tfUnificationCode.setText("");
				}
			}

			if (tfPreCustomsDeclarationCode.getText() != null
					&& tfPreCustomsDeclarationCode.getText().length() >= 6) {
				String s = tfPreCustomsDeclarationCode.getText(); // 000001
				sedi = EncCommon.substring(s, 0, s.length() - 6); //
				tfPreCustomsDeclarationCode.setText(sedi
						+ baseEncAction
								.getMaxPreCustomsDeclarationCode(new Request(
										CommonVars.getCurrUser())));

			} else {
				tfPreCustomsDeclarationCode.setText(baseEncAction
						.getMaxPreCustomsDeclarationCode(new Request(CommonVars
								.getCurrUser())));
			}

		}
	}

	/**
	 * This method initializes tfEmsCode
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(239, 10, 85, 21);
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
			tfCompanyEmsNo.setBounds(395, 10, 90, 21);
			tfCompanyEmsNo.setEditable(false);
		}
		return tfCompanyEmsNo;
	}

	/**
	 * This method initializes tfManufacturer
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfManufacturer() {
		if (tfManufacturer == null) {
			tfManufacturer = new JTextField();
			tfManufacturer.setBounds(483, 289, 176, 19);
		}
		return tfManufacturer;
	}

	/**
	 * This method initializes tfCustomsDeclarationSerialNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfCustomsDeclarationSerialNumber() {
		if (tfCustomsDeclarationSerialNumber == null) {
			tfCustomsDeclarationSerialNumber = new JTextField();
			tfCustomsDeclarationSerialNumber.setBounds(574, 10, 108, 21);
			tfCustomsDeclarationSerialNumber.setEditable(false);
		}
		return tfCustomsDeclarationSerialNumber;
	}

	/**
	 * This method initializes cbbBalanceMode
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbBalanceMode() {
		if (cbbBalanceMode == null) {
			cbbBalanceMode = new JComboBox();
			cbbBalanceMode.setBounds(398, 140, 114, 19);
		}
		return cbbBalanceMode;
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
		if (!this.baseEncAction.checkCustDeclCommInfoSerialNumber(new Request(
				CommonVars.getCurrUser(), true), customsDeclaration)) {
			if (JOptionPane.showConfirmDialog(this,
					"商品明细的序号不连续，如果忽略此问题则按“是”，否则按“否”继续", "提示！",
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
		this.fillData();
		if (CommonVars.commonCheckNull(DgBaseExportCustomsDeclaration.this,
				customsDeclaration)) {
			return false;
		}
		if (!(customsDeclaration instanceof BcsCustomsDeclaration)) {
			List list = commInfoModel.getList();
			for (int i = 0; i < list.size(); i++) {
				if (commonInofCheckNull(DgBaseExportCustomsDeclaration.this,
						(BaseCustomsDeclarationCommInfo) list.get(i))) {
					return false;
				}
			}
		}
		return EncCommon.checkExportCustomsDeclarationData(
				this.customsDeclaration, this);
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

	// private boolean checkWeight(){
	// List companyOtherList = customBaseAction.findCompanyOther(new Request(
	// CommonVars.getCurrUser()));
	// System.out.println("companyOtherList="+companyOtherList);
	// CompanyOther companyOther = null;
	// if (null != companyOtherList && companyOtherList.size() > 0) {
	// companyOther = (CompanyOther) companyOtherList.get(0);
	// }
	// Boolean isCheckWeight = false;
	// System.out.println("companyOther.getIsCheckWeight()="+companyOther.getIsCheckWeight());
	// if(null!=companyOther.getIsCheckWeight()){
	// isCheckWeight = companyOther.getIsCheckWeight();
	// }
	// if (isCheckWeight) {
	// // 表头总毛重、总净重
	// Double headGrossWeight = 0.0;
	// Double headNetWeight = 0.0;
	//
	// if (null != customsDeclaration.getGrossWeight())
	// headGrossWeight = customsDeclaration.getGrossWeight();
	// if (null != customsDeclaration.getNetWeight()) {
	// headNetWeight = customsDeclaration.getNetWeight();
	// }
	//
	// // 表体毛重、净重
	// Double GrossWeight = 0.0;
	// Double NetWeight = 0.0;
	// List list1 = commInfoModel.getList();
	// for (int i = 0; i < list1.size(); i++) {
	// BaseCustomsDeclarationCommInfo baseCustomsDeclarationCommInfo =
	// (BaseCustomsDeclarationCommInfo) list1
	// .get(i);
	// if (null != baseCustomsDeclarationCommInfo.getGrossWeight()) {
	// GrossWeight += baseCustomsDeclarationCommInfo
	// .getGrossWeight();
	// }
	// if (null != baseCustomsDeclarationCommInfo.getNetWeight()) {
	// NetWeight += baseCustomsDeclarationCommInfo
	// .getNetWeight();
	// }
	// }
	// // 判断是否一致
	// System.out.println("GrossWeight=" + GrossWeight);
	// System.out.println("headGrossWeight=" + headGrossWeight);
	// if(!GrossWeight.equals(headGrossWeight)){
	// JOptionPane.showMessageDialog(this, "报关单表头总毛重与表体毛重相加不一致!",
	// "提示!", 1);
	// return true;
	// }
	// // if (!GrossWeight.equals(headGrossWeight)) {
	// // if (JOptionPane.showConfirmDialog(this,
	// // "报关单表头总毛重与表体毛重相加不一致，如果忽略此问题则按“是”，否则按“否”继续", "提示！",
	// // JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
	// // return;
	// // }
	// // }
	// System.out.println("NetWeight=" + NetWeight);
	// System.out.println("headNetWeight=" + headNetWeight);
	// if (!NetWeight.equals(headNetWeight)) {
	// JOptionPane.showMessageDialog(this,
	// "报关单表头总净重与表体净重相加不一致!",
	// "提示!", 1);
	// return true;
	// // if (JOptionPane.showConfirmDialog(this,
	// // "报关单表头总净重与表体净重相加不一致，如果忽略此问题则按“是”，否则按“否”继续", "提示！",
	// // JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
	// // return;
	// // }
	// }
	// }
	// return false;
	// }
	//
	/**
	 * 检查表体净重、毛重与表头净重、毛重是否相等
	 * 
	 * @return
	 */
	private boolean checkSaveWeight() {
		List companyOtherList = customBaseAction.findCompanyOther(new Request(
				CommonVars.getCurrUser()));
		CompanyOther companyOther = null;
		if (null != companyOtherList && companyOtherList.size() > 0) {
			companyOther = (CompanyOther) companyOtherList.get(0);
		}

		/*
		 * 检查关封号
		 * 
		 * 如果参数设置需要检查关封，则检查关封；
		 */
		// 是否保存关封
		Boolean isSaveCustomsEnvelop = companyOther
				.getIsSaveCustomsEnvelopBillNo();

		if (isSaveCustomsEnvelop != null && isSaveCustomsEnvelop == true) {
			if (CommonUtils.isEmpty(tfCustomsEnvelopBillNo.getText())) {
				JOptionPane.showMessageDialog(this, "报关单表头结转申请单号不能为空!", "提示!",
						1);
				return true;
			}
		}

		Boolean isCheckWeight = false;
		if (null != companyOther.getIsCheckWeight()) {
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

	// 检查报关单
	private void checkCustoms() {

		if (checkSaveWeight()) {
			return;
		}

		if (checkOther()) {
			return;
		}

		if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
			if (!"".equals(checkCurrentRemainAmount())) {
				String str = checkCurrentRemainAmount();
				if (!"".equals(str)) {
					str += ",\n确定要继续吗?";
					if (JOptionPane.showConfirmDialog(
							DgBaseExportCustomsDeclaration.this, str, "严重警告",
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
							DgBaseExportCustomsDeclaration.this, str, "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		}

		if (checkData() && (!checkindex()) && checkAmount()
				&& checkCustomsTransmitPlus()) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
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
								DgBaseExportCustomsDeclaration.this,
								"系统提示：集装箱数为0\n是 否在说明字符串前加#1", "提示",
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
		// } else if (jTabbedPane.getSelectedIndex() == 1) {
		// if (!checkindex() && checkAmount()) {
		// JOptionPane.showMessageDialog(null, "数据检查成功，没有错误！", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// }
		// }

	}

	private boolean checkAmount() {
		boolean sgin = true;
		if (customsDeclaration instanceof CustomsDeclaration) {
			if (customsDeclaration.getImpExpType() != null
					&& customsDeclaration.getImpExpType() == ImpExpType.REWORK_EXPORT) {
				// 帐册中，对于返工复出的，要求检查数量
				try {
					sgin = this.manualDecleareAction
							.checkCustomssDeclarationCount(new Request(
									CommonVars.getCurrUser()),
									customsDeclaration);
					System.out.println(sgin);

				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
		}
		return sgin;
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
	 * 检查报关单转关附加
	 * 
	 * @see 如果 有 转关附加 则 检查提运单号 是否 与 境外、境内运输工具名称 一致
	 * @see 不一致 则提示是否通过保存和通过检查
	 * @return
	 */
	private boolean checkCustomsTransmitPlus() {

		/*
		 * 判断 是否 带有 转关附加 "@" 开头代表带有 转关附加
		 */
		if (StringUtils.isEmpty(tfConveyance.getText())
				|| !tfConveyance.getText().substring(0, 1).trim().equals("@")) {

			return true;
		}

		String overseasConveyanceBillOfLading = customsDeclaration
				.getOverseasConveyanceBillOfLading();

		String overseasConveyanceName = customsDeclaration
				.getOverseasConveyanceName();

		String domesticConveyanceName = customsDeclaration
				.getDomesticConveyanceName();

		// 提运单号
		String billOfLading = customsDeclaration.getBillOfLading();

		// 检查 是否 一致
		if (StringUtils.equalsIgnoreCase(billOfLading, overseasConveyanceName)
				&& StringUtils.equalsIgnoreCase(billOfLading,
						domesticConveyanceName)
				&& StringUtils.equalsIgnoreCase(billOfLading,
						overseasConveyanceBillOfLading)) {

			return true;

		} else {

			if (JOptionPane.showConfirmDialog(
					DgBaseExportCustomsDeclaration.this,
					"系统提示:提运单号与转关附加中境外或境内运输工具不一致,确定继续保存和通过检查吗?", "提示",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

				return true;

			}

			return false;

		}

	}

	/**
	 * 1、进出口报关单、特殊报关单：增加报关单逻辑检查功能； 总价小数位不能超过2位，包装种类的代码只能是1位；
	 * 申报数量、法定数量的小数位不能大于5位， 关联报关单号不能小于大于18位、关联手册号不能小于大于12位； 商品编码不能小于10位。
	 * 
	 * @return
	 */
	private boolean checkOther() {
		if (tfConveyance.getText() != null
				&& tfConveyance.getText().startsWith("K")) {
			if (tfTcsEdiId.getSelectedIndex() == 1) {
				return false;
			}
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"运输工具为‘K’开头表示报关单为：跨境快速通关报关单，报关单标志必须修改为‘普通报关’", "提示",
					JOptionPane.YES_NO_OPTION);
			return true;
		}

		// 备注最前面必须是转自或转至，并判断备注内容中，字符B或C或E后面必须11位数字。
		if (customsDeclaration.getImpExpType() == ImpExpType.TRANSFER_FACTORY_EXPORT
				&& customsDeclaration.getMemos() != null
				&& !customsDeclaration.getMemos().equals("")) {
			if (!RegexUtil.checkMatch(customsDeclaration.getMemos(),
					"^转(自|至)(B|E|C|H)\\d{11}(\\D+.*$|$)")) {
				JOptionPane.showMessageDialog(
						DgBaseExportCustomsDeclaration.this,
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
						DgBaseExportCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "总价小数位不能超过2位", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			// 申报数量的小数位不能大于5位
			if (!RegexUtil.checkMaxAccuracy(info.getCommAmount() == null ? ""
					: CommonUtils.formatDoubleByDigit(info.getCommAmount(), 6),
					5)) {
				JOptionPane.showMessageDialog(
						DgBaseExportCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "申报数量的小数位不能大于5位",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			// 法定数量的小数位不能大于5位
			if (!RegexUtil.checkMaxAccuracy(
					info.getFirstAmount() == null ? "" : CommonUtils
							.formatDoubleByDigit(info.getFirstAmount(), 6), 5)) {
				JOptionPane.showMessageDialog(
						DgBaseExportCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "法定数量的小数位不能大于5位",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}

		return false;
	}

	protected JButton getBtnEffect() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.setPreferredSize(new Dimension(43, 30));
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.effectCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.effectCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.effectCustoms(new Request(
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
	 * 生效检查
	 */
	protected void effectCustomsDeclaration() {
		if (checkSaveWeight()) {
			return;
		}

		if (DgBaseExportCustomsDeclaration.this.tfCustomsDeclarationCode
				.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"报关单号不能为空！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (DgBaseExportCustomsDeclaration.this.tfCustomsDeclarationCode
				.getText().trim().length() != 18) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"报关单号必须为18位！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		// 京瓷光学插件使用
		if (DgBaseExportCustomsDeclaration.this.tfInspect.getText().trim()
				.length() > 20) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"报检预录入号必须小于20位！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}

		if (!checkData()) {
			return;
		}
		if (CommonVars.getIsCustomAmountOut().booleanValue() == true) {
			if (!"".equals(checkCurrentRemainAmount())) {
				String str = checkCurrentRemainAmount();
				if (!"".equals(str)) {
					str += ",\n确定要继续吗?";
					if (JOptionPane.showConfirmDialog(
							DgBaseExportCustomsDeclaration.this, str, "严重警告",
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
							DgBaseExportCustomsDeclaration.this, str, "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		}
		if (commInfoModel == null) {
			initTable();
		}
		if (commInfoModel.getList().size() < 1) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"无商品信息资料所以不能生效！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}

		if (JOptionPane.showConfirmDialog(DgBaseExportCustomsDeclaration.this,
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
	 * This method initializes jButton1
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
						expCustomsAuthorityAction.unreelCustoms(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.unreelCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.unreelCustoms(new Request(
								CommonVars.getCurrUser()));
					}
					unreelCustomsDeclaration();
				}
			});
		}
		return btnUnreel;
	}

	/**
	 * 回卷报关单
	 * 
	 */
	protected void unreelCustomsDeclaration() {
		if (this.fecavAction.findFecavBillByCodeCount(
				new Request(CommonVars.getCurrUser()),
				customsDeclaration.getAuthorizeFile()) > 0) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
					"外汇核销单中已经填入了出口日期！！ 不能回卷！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (customsDeclaration.getIsEmsCav() != null
				&& customsDeclaration.getIsEmsCav()) {
			if (JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(
					DgBaseExportCustomsDeclaration.this, "该单已经数据报核通过了，是否回卷!!!",
					"提示", JOptionPane.OK_CANCEL_OPTION)) {
				return;
			}

		}
		if (JOptionPane.showConfirmDialog(DgBaseExportCustomsDeclaration.this,
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
	protected JTextField getTfDeclaraCustomsBroker() {
		if (tfDeclaraCustomsBroker == null) {
			tfDeclaraCustomsBroker = new JTextField();
			tfDeclaraCustomsBroker.setBounds(79, 11, 108, 21);
			tfDeclaraCustomsBroker.setEditable(false);
		}
		return tfDeclaraCustomsBroker;
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
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbDeclarationCompany() {
		if (cbbDeclarationCompany == null) {
			cbbDeclarationCompany = new JComboBox();
			cbbDeclarationCompany.addKeyListener(new FocusActionListner(
					getCbbCustomer()));
			cbbDeclarationCompany.setBounds(79, 313, 212, 19);
		}
		return cbbDeclarationCompany;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfBondedWarehouse() {
		if (tfBondedWarehouse == null) {
			tfBondedWarehouse = new JTextField();
			tfBondedWarehouse.setBounds(78, 386, 103, 19);
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
				DgBaseExportCustomsDeclaration.this, other)) {
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
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceCode() {
		if (tfInvoiceCode == null) {
			tfInvoiceCode = new JTextField();
			tfInvoiceCode.setBounds(239, 386, 124, 19);
			tfInvoiceCode.setEditable(true);
			tfInvoiceCode.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (tfInvoiceCode.getText() != null
							&& !tfInvoiceCode.getText().trim().equals("")
							&& tfInvoiceCode.getText().trim().length() != 8) {
						JOptionPane.showMessageDialog(
								DgBaseExportCustomsDeclaration.this,
								"发票号应该为8位,请重新填写！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						tfInvoiceCode.setText("");
					}
				}
			});
		}
		return tfInvoiceCode;
	}

	protected JTextField getTfInspect() {
		if (tfInspect == null) {
			tfInspect = new JTextField();
			tfInspect.setVisible(false);// 插件用到
			tfInspect.setBounds(new Rectangle(525, 411, 157, 19));
		}
		return tfInspect;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInvoice() {
		if (btnInvoice == null) {
			btnInvoice = new JButton();
			btnInvoice.setBounds(362, 386, 20, 19);
			btnInvoice.setText("...");
			btnInvoice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date declarationDate = cbbDeclarationDate.getDate();// customsDeclaration.getDeclarationDate();
					Invoice invoice = (Invoice) InvoiceQuery.getInstance()
							.findInvoiceCustomsDeclarationCodeIsNull(
									declarationDate);
					if (invoice != null) {
						tfInvoiceCode.setText(invoice.getInvoiceCode());
					}
				}
			});
		}
		return btnInvoice;
	}

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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			jToolBar1 = new JToolBar();
			jToolBar1.setLayout(f);
			jToolBar1.setFloatable(false);
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJButton8());
			jToolBar1.add(getJButton2());
			jToolBar1.add(getJButton4());
			jToolBar1.add(getJButton6());
			jToolBar1.add(getBtnSortChecked());
			jToolBar1.add(getJButton());

			jToolBar1.add(getJButton7());
			jToolBar1.add(getBtnCaleReturnExport());

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
						expCustomsAuthorityAction.definedOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction.definedOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.definedOrder(new Request(
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
	private JFooterTable getJTable() {
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
						expCustomsAuthorityAction.billToBgD(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction.billToBgD(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.billToBgD(new Request(
								CommonVars.getCurrUser()));
					}
					makeDzbaCustomsDeclaration();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 生成报关单
	 */
	private void makeDzbaCustomsDeclaration() {
		if (customsDeclaration == null || customsDeclaration.getId() == null) {
			JOptionPane.showMessageDialog(DgBaseExportCustomsDeclaration.this,
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
			para.setImportFlag(EncCommon.isImport(impExpType));
			para.setImpExpType(impExpType);
			para.setFromCustomsDeclaretion(true);
			para.setCustomsDeclaration(true);
			DgMakeApplyToCustoms dialog = new DgMakeApplyToCustoms();
			dialog.setPara(para);
			dialog.setCustoms((CustomsDeclaration) this.customsDeclaration);
			dialog.setVisible(true);
			customsDeclaration = dialog.getCustoms();
			initScmCocPara(customsDeclaration);
			infoforint();// 数据取整
			getPiceAndWeight();
			getAttachedBill();
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
			customsDeclaration = bcsDialog.getBcsCustomsDeclaration();
			initScmCocPara(customsDeclaration);
			infoforint();// 数据取整
			getPiceAndWeight();
			getAttachedBill();
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
			customsDeclaration = dzscDialog.getDzscCustomsDeclaration();
			initScmCocPara(customsDeclaration);
			infoforint();// 数据取整
			getPiceAndWeight();
			getAttachedBill();
			break;
		default:
			break;
		}
		initTable();
	}

	/**
	 * 申请转报关单带出客户供应商资料
	 */
	private void initScmCocPara(BaseCustomsDeclaration d) {
		CompanyOther other = CommonVars.getOther();
		if (other == null || (other != null && !other.getIsAutoAmount())) {
			return;
		}
		cbbCountryOfLoadingOrUnloading.setSelectedItem(d
				.getCountryOfLoadingOrUnloading());
		cbbPortOfLoadingOrUnloading.setSelectedItem(d
				.getPortOfLoadingOrUnloading());
		cbbCustoms.setSelectedItem(d.getCustoms());
		cbbTransferMode.setSelectedItem(d.getTransferMode());
		cbbCustomer.setSelectedItem(d.getCustomer());// 客户

		customsDeclarationModel.updateRow(d);
	}

	protected abstract void addFromMateriel();

	protected abstract void lookFromMateriel();

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("内部商品");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.innerCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.innerCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction
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
		return jButton2;
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
	 * This method initializes btnAuthorizeFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAuthorizeFile() {
		if (btnAuthorizeFile == null) {
			btnAuthorizeFile = new JButton();
			btnAuthorizeFile.setText("...");
			btnAuthorizeFile.addKeyListener(new FocusActionListner(
					getCbbTransac()));
			btnAuthorizeFile.setBounds(317, 193, 21, 21);
			btnAuthorizeFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							addFecavBill();
						}
					});
		}
		return btnAuthorizeFile;
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

	private void addFecavBill() {
		FecavBill obj = (FecavBill) CommonQueryPage.getInstance()
				.getFecavBillNotCustomsDeclaration();
		if (obj == null) {
			return;
		} else {
			this.tfAuthorizeFile.setText(obj.getCode());
		}

	}

	/**
	 * This method initializes tfCustomsEnvelopBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsEnvelopBillNo() {
		if (tfCustomsEnvelopBillNo == null) {
			tfCustomsEnvelopBillNo = new JTextField();
			tfCustomsEnvelopBillNo.setBounds(new Rectangle(463, 386, 196, 19));
		}
		return tfCustomsEnvelopBillNo;
	}

	/**
	 * This method initializes btnCustomsEnvelop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFptAppNo() {
		if (btnFptAppNo == null) {
			btnFptAppNo = new JButton();
			btnFptAppNo.setBounds(new Rectangle(658, 386, 24, 19));
			btnFptAppNo.setText("...");
			btnFptAppNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbImpExpType.getSelectedItem() == null) {
						return;
					}
					Date declarationDate = cbbDeclarationDate.getDate();
					ScmCoc scmCoc = (ScmCoc) cbbCustomer.getSelectedItem();
					int impExpType = Integer
							.parseInt(((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode());
					// FptAppItem fptAppItem = (FptAppItem)
					// FptQuery.getInstance()
					// .findFptAppItemByCustomsDeclaration(impExpType,
					// tfEmsNo.getText().trim(), scmCoc);
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
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("查看");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (commInfoModel == null) {
						return;
					}
					if (commInfoModel.getList().size() < 1) {
						return;
					}
					if (commInfoModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgBaseExportCustomsDeclaration.this,
								"请选择你要查看的资料", "提示", 0);
						return;
					}
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.lookCommodity(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction
								.lookCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.lookCommodity(new Request(
								CommonVars.getCurrUser()));
					}
					lookFromMateriel();
					infoforint();// 数据取整
					getPiceAndWeight();
					initTable();
					showCommInfoMoney();
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(43, 3, 68, 25);
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
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setText("非套打");
			jButton5.setPreferredSize(new Dimension(48, 30));
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isTaoda = false;
					Component comp = (Component) e.getSource();
					getJPopupMenu().show(comp, 0, comp.getHeight());
					jMenu.setVisible(false);
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setText("数据取整");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.dataFetchInt(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction.dataFetchInt(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.dataFetchInt(new Request(
								CommonVars.getCurrUser()));
					}
					customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
							.getCurrentRow();
					// JOptionPane.showMessageDialog(null,
					// customsDeclaration.getCustomer().getName());
					List list = getDataSource();
					baseEncAction.customsInfoForMatInt(
							new Request(CommonVars.getCurrUser()), list);
					getPiceAndWeight();
					initTable();
					showCommInfoMoney();
					// JOptionPane.showMessageDialog(null,
					// customsDeclaration.getCustomer().getName());
				}
			});
		}
		return jButton6;
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
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("自动排序");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						expCustomsAuthorityAction.autoOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCExpCustomsAuthorityAction.autoOrder(new Request(
								CommonVars.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSExpCustomsAuthorityAction.autoOrder(new Request(
								CommonVars.getCurrUser()));
					}
					if (JOptionPane.showConfirmDialog(
							DgBaseExportCustomsDeclaration.this,
							"是否确定要按商品序号排序？", "提示", 0) == 0) {

						new exectAutoOrder().start();
					}
				}
			});
		}
		return jButton7;
	}

	class exectAutoOrder extends Thread {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgBaseExportCustomsDeclaration.this);
				CommonProgress.setMessage("系统正在排序数据，请稍后...");
				baseEncAction.commInfoAutoOrder(
						new Request(CommonVars.getCurrUser()),
						customsDeclaration);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgBaseExportCustomsDeclaration.this,
						"排序数据失败：" + e.getMessage(), "提示", 2);
			} finally {
				initTable();
			}
		}
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("清单转报关单");
		}
		return jButton8;
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

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillOfLading() {
		if (tfBillOfLading == null) {
			tfBillOfLading = new JTextField();
			tfBillOfLading.setBounds(new Rectangle(546, 113, 110, 21));
		}
		return tfBillOfLading;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setBounds(new Rectangle(657, 113, 25, 20));
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

	/**
	 * This method initializes btnContainer1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnManufacturer() {
		if (btnManufacturer == null) {
			btnManufacturer = new JButton();
			btnManufacturer.setBounds(new Rectangle(661, 289, 21, 19));
			btnManufacturer.setText("...");
			btnManufacturer.addKeyListener(new FocusActionListner(
					getCbbDeclarationCompany()));
			btnManufacturer
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							Company obj = (Company) CommonQuery.getInstance()
									.getManufacturer();

							if (obj == null) {
								return;
							} else {
								tfManufacturer.setText(obj.getName());
							}
						}
					});
		}
		return btnManufacturer;
	}

	/**
	 * This method initializes tfCreaterDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfCreateDate() {
		if (tfCreateDate == null) {
			tfCreateDate = new JCalendarComboBox();
			tfCreateDate.setBounds(new Rectangle(581, 313, 101, 19));
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
	protected JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.add(getJMenuItem1());
			jPopupMenu.add(getJMenuItem2());
			jPopupMenu.add(getJMenuItem4());
			jPopupMenu.add(getJMenuItem5());
			jPopupMenu.add(getJMenuItem6());
			jPopupMenu.add(getInvoice());
			jPopupMenu.add(getJMenuItem3());
			jPopupMenu.add(getJMenuItem12());
			// jPopupMenu.add(getJMenuItem13());
			jPopupMenu.add(getJMenuItem14());
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
			jPopupMenu.add(getMiPrintPrccBonded());
			jPopupMenu.add(getJMenuItem16());
			jPopupMenu.add(getMiBarcodePrint());
			jPopupMenu.add(getJMenu());
			jPopupMenu.add(getMIBcs());

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
					try {
						CustomsPrinter.getInstance(getParaList()).doOutPrint0();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(
								DgBaseExportCustomsDeclaration.this,
								"请确认是否有浏览系统参数设置的权限！");
					}
				}
			});
		}
		return jMenuItem;
	}

	/**
	 * 
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
					CustomsPrinter.getInstance(getParaList()).doOutPrint1();
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
			jMenuItem3.setText("装箱单1");
			jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgWExportCommodityEncasementReporLwjgtSet dg = new DgWExportCommodityEncasementReporLwjgtSet(
							getParaList());
					dg.setMaterialManageAction(materialManageAction);
					dg.setVisible(true);
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
			jMenuItem4.setText("出口加工发票");
			jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint3();
				}
			});
		}
		return jMenuItem4;
	}

	/**
	 * This method initializes jMenuItem5
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem5() {
		if (jMenuItem5 == null) {
			jMenuItem5 = new JMenuItem();
			jMenuItem5.setText("来料加工发票");
			jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint5();
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
			jMenuItem6.setText("三资企业发票");
			jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint5();
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
			jMenuItem7.setText("内地海关及香港海关陆路出/出境载货清单");
			jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint7(
							false, ImpExpFlag.EXPORT);
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
			jMenuItem8.setText("内地海关及香港海关陆路出/出境载货清单+(附表)");
			jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint7(true,
							ImpExpFlag.EXPORT);
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
			jMenuItem9.setText("内地海关及香港海关陆路出/出境载货清单(香港)");
			jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList())
							.doOutMergeZaiHuoListPrint();
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
			jMenuItem10.setText("内地海关及香港海关陆路出/出境载货清单（香港）+(附表)");
			jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint9(true);
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
					CustomsPrinter.getInstance(getParaList()).doOutPrint10();
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
			jMenuItem12.setText("装箱单2");
			jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint11();
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
	private JMenuItem getJMenuItem13() {
		if (jMenuItem13 == null) {
			jMenuItem13 = new JMenuItem();
			jMenuItem13.setText("装箱单3");
			jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint12();
				}
			});
		}
		return jMenuItem13;
	}

	/**
	 * This method initializes jMenuItem14
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem14() {
		if (jMenuItem14 == null) {
			jMenuItem14 = new JMenuItem();
			jMenuItem14.setText("装箱单3");
			jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint13();
				}
			});
		}
		return jMenuItem14;
	}

	/**
	 * 报表：装箱单(包装种类在明细) 2010-05-21 hcl
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJmItemZhangXiangDan() {
		if (jMenuItem17 == null) {
			jMenuItem17 = new JMenuItem();
			jMenuItem17.setText("装箱单(包装种类在明细)");
			jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList())
							.doOutPrintZhangXiangDan();
				}
			});
		}
		return jMenuItem17;
	}

	/**
	 * 报表：装箱单(包装种类在明细及商品编码)
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
							CustomsPrinter
									.getInstance(getParaList())
									.doOutPrintZhangXiangDanComplexOrBoxNo(true);
						}
					});
		}
		return jmItemBoxNoComplex;
	}

	/**
	 * 报表：装箱单(包装种类在明细及箱号)
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
							.doOutPrintZhangXiangDanComplexOrBoxNo(false);
				}
			});
		}
		return jmItemBoxNo;
	}

	/**
	 * This method initializes miPrintPrccBonded
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintPrccBonded() {
		if (miPrintPrccBonded == null) {
			miPrintPrccBonded = new JMenuItem();
			miPrintPrccBonded.setText("打印中华人民共和国保税区出口货物备案清单");
			miPrintPrccBonded
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport();
						}
					});
		}
		return miPrintPrccBonded;
	}

	/**
	 * 打印套打海关保税区出口货物备案清单
	 */
	public void printReport() {
		try {
			// ContractAction contractAction = (ContractAction) CommonVars
			// .getApplicationContext().getBean("contractAction");
			// List list =null;
			// List list2=null;
			// if (contract != null) {
			// String parentId = contract.getId();
			// list = contractAction.findContractDomesticPurchaseBill(
			// new Request(CommonVars.getCurrUser()), parentId);
			//
			// list2=contractAction.setContractDomesticPurchaseBill(
			// new Request(CommonVars.getCurrUser()), parentId);
			// }
			List list = customsDeclarationModel.getCurrentRows();
			// List list2=baseEncAction.findSpecialCustomsDeclaration(new
			// Request(CommonVars.getCurrUser()));
			BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list.get(0);
			System.out.println("bcd=" + bcd);
			Integer s = bcd.getImpExpFlag();
			String attachedBillName = getattachedBillName(customsDeclaration
					.getAttachedBill());
			System.out.println("s=" + s);
			List list2 = baseEncAction.findCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), bcd);
			int size = list2.size();
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
					.getResourceAsStream("report/PRCCIQExportBondedAreaEntrantgoodsFilingBills.jasper");
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
			BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list.get(0);
			List list2 = baseEncAction.findCustomsDeclarationCommInfo(
					new Request(CommonVars.getCurrUser()), bcd);
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
	 * This method initializes jMenuItem15
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem15() {
		if (jMenuItem15 == null) {
			jMenuItem15 = new JMenuItem();
			jMenuItem15.setText("三资企业");
			jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint14(
							new Boolean(false));
				}
			});
		}
		return jMenuItem15;
	}

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("打印出口收汇核销单");
			jMenu.add(this.getJMenuItem16());
			jMenu.add(this.getJMenuItem15());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem16
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem16() {
		if (jMenuItem16 == null) {
			jMenuItem16 = new JMenuItem();
			jMenuItem16.setText("来料企业");
			jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doOutPrint14(
							new Boolean(true));
				}
			});
		}
		return jMenuItem16;
	}

	/**
	 * @return javax.swing.JMenuItem
	 */
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
							dg.setExp(true);
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
							dg.setExp(true);
							dg.setVisible(true);
						}
					});
		}
		return miInvoiceMerger;
	}

	/**
	 * This method initializes jMenuItem16
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getInvoice() {
		if (invoice == null) {
			invoice = new JMenuItem();
			invoice.setText("来料企业加工发票");
			invoice.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList(true))
							.InvoicePrint();
				}
			});
		}
		return invoice;
	}

	/**
	 * This method initializes btnSortChecked
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSortChecked() {
		if (btnSortChecked == null) {
			btnSortChecked = new JButton();
			btnSortChecked.setText("按商检排序");
			btnSortChecked
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (projectType == ProjectType.BCUS) {
								expCustomsAuthorityAction
										.definedOrder(new Request(CommonVars
												.getCurrUser()));
							} else if (projectType == ProjectType.DZSC) {
								dZSCExpCustomsAuthorityAction
										.definedOrder(new Request(CommonVars
												.getCurrUser()));
							} else if (projectType == ProjectType.BCS) {
								bCSExpCustomsAuthorityAction
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
										DgBaseExportCustomsDeclaration.this,
										info, "按商检排序",
										JOptionPane.ERROR_MESSAGE);
							}
							initTable();
						}
					});
		}
		return btnSortChecked;
	}

	/**
	 * This method initializes cbbFeeCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFeeCurr() {
		if (cbbFeeCurr == null) {
			cbbFeeCurr = new JComboBox();
			cbbFeeCurr.setBounds(new Rectangle(78, 217, 108, 19));
		}
		return cbbFeeCurr;
	}

	/**
	 * This method initializes cbbInsurCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbInsurCurr() {
		if (cbbInsurCurr == null) {
			cbbInsurCurr = new JComboBox();
			cbbInsurCurr.setBounds(new Rectangle(545, 217, 137, 20));
		}
		return cbbInsurCurr;
	}

	/**
	 * This method initializes cbbOtherCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbOtherCurr() {
		if (cbbOtherCurr == null) {
			cbbOtherCurr = new JComboBox();
			cbbOtherCurr.setBounds(new Rectangle(333, 241, 99, 19));
		}
		return cbbOtherCurr;
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
								DgBaseExportCustomsDeclaration.this,
								"此报关单商品项数没有超过20项，所以不能拆分。");
						return;
					}
					List list = baseEncAction.splitCustomsDeclaration(
							new Request(CommonVars.getCurrUser()),
							customsDeclaration);
					String s = "拆分生成" + list.size() + "笔新的出口报关单，报关单流水号如下:\r\n";
					for (int i = 0; i < list.size(); i++) {
						BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list
								.get(i);
						s += (bcd.getSerialNumber() + ";");
					}
					customsDeclarationModel.addRows(list);
					initTable();
					JOptionPane.showMessageDialog(
							DgBaseExportCustomsDeclaration.this, s);
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
			tfConnectNo.setBounds(new Rectangle(90, 408, 145, 22));
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
			tfConnectDeclarationNo.setBounds(new Rectangle(312, 409, 145, 22));
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
			pnTcs.add(getTfTcsEdiId(), null);
			pnTcs.add(lbTcsEntryTransitType, null);
			pnTcs.add(getTfTcsEntryType(), null);
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
			tfTcsNote.setBounds(new Rectangle(167, 299, 167, 50));
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
					.setBounds(new Rectangle(564, 139, 118, 21));
			tfdomesticConveyanceCode
					.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == 10) {
								Integer type = Integer
										.valueOf(((ItemProperty) cbbImpExpType
												.getSelectedItem()).getCode());
								if (type == ImpExpType.TRANSFER_FACTORY_EXPORT) {

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

	private JButton getBtnCaleReturnExport() {
		if (btnCaleReturnExport == null) {
			btnCaleReturnExport = new JButton();
			btnCaleReturnExport.setText("统计未复出数据");
			btnCaleReturnExport
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgCaleReturnExport dg = new DgCaleReturnExport();
							if (null != getEmsHead()) {
								dg.setEmsHeadH2k(getEmsHead().getEmsNo());
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
														DgBaseExportCustomsDeclaration.this,
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
		return btnCaleReturnExport;
	}

	/**
	 * 回写转关附加
	 * 
	 * @param motor
	 */
	private void writeDefaultValue(MotorCode motor) {
		String homeCard = tfBillOfLading.getText().trim();// 提运单号

		if (customsDeclaration.getTransferMode() == null
				|| customsDeclaration.getTransferMode().getName()
						.equals("物流园区")) {
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
	}

	/**
	 * This method initializes tfSupplmentType
	 * 
	 * @return javax.swing.JTextField
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

	private JComboBox getCbbOperType() {
		if (cbbOperType == null) {
			cbbOperType = new JComboBox();
			cbbOperType.setBounds(new Rectangle(480, 50, 167, 22));
		}
		return cbbOperType;
	}

	private JMenuItem miBcs = null;
	private JLabel label;
	private JTextField tfVoyageNo;

	private JMenuItem getMIBcs() {
		if (miBcs == null) {
			miBcs = new JMenuItem();
			miBcs.setText("BCS系统出口装箱单");
			miBcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					CustomsPrinter.getInstance(getParaList()).doInMIBcs();
				}
			});
		}
		return miBcs;
	}

	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel();
			label.setText("航次号");
			label.setBounds(new Rectangle(343, 126, 52, 18));
			label.setBounds(343, 114, 52, 18);
		}
		return label;
	}

	private JTextField getTfVoyageNo() {
		if (tfVoyageNo == null) {
			tfVoyageNo = new JTextField();
			tfVoyageNo.setBounds(387, 113, 98, 21);
		}
		return tfVoyageNo;
	}
}