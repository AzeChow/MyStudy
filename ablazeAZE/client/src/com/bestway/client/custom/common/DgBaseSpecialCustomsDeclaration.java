/*
 * Created on 2004-8-7
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
import java.awt.event.ItemListener;
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

import com.bestway.bcs.contract.entity.Contract;
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
import com.bestway.bcus.custombase.entity.parametercode.Uses;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bcus.system.entity.CustomsDeclarationSet;
import com.bestway.client.custom.common.report.CustomsPrinter;
import com.bestway.client.fixasset.FixAssetQuery;
import com.bestway.client.fixtureonorder.FixtureQuery;
import com.bestway.client.invoice.InvoiceQuery;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.FixType;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.constant.SupplmentType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CustomsUser;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.customs.common.action.BCSImpCustomsAuthorityAction;
import com.bestway.customs.common.action.BCSSpecialCustomsAuthorityAction;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.action.DZSCImpCustomsAuthorityAction;
import com.bestway.customs.common.action.DZSCSpecialCustomsAuthorityAction;
import com.bestway.customs.common.action.ImpCustomsAuthorityAction;
import com.bestway.customs.common.action.SpecialCustomsAuthorityAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.customs.common.entity.BaseCustomsDeclarationCommInfo;
import com.bestway.customs.common.entity.BaseCustomsDeclarationContainer;
import com.bestway.customs.common.entity.BaseEmsHead;
import com.bestway.customs.common.entity.TCSCommonCode;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.fecav.action.FecavAction;
import com.bestway.fecav.entity.FecavBill;
import com.bestway.fixasset.entity.DutyCertHead;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.invoice.entity.Invoice;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.KeyAdapterControl;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.bestway.util.RegexUtil;

/**
 * 特殊报关单编辑
 */
@SuppressWarnings("unchecked")
public abstract class DgBaseSpecialCustomsDeclaration extends JDialogBase {

	protected CustomsDeclarationSet other1 = null;

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

	protected JTextField tfBillOfLading = null;

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

	protected int dataState = DataState.BROWSE;

	protected JFormattedTextField tfPerTax = null;

	protected JFormattedTextField tfFreightage = null;

	protected JFormattedTextField tfInsurance = null;

	protected JFormattedTextField tfIncidentalExpenses = null;

	protected JFormattedTextField tfCommodityNum = null;

	protected JButton btnSave = null;

	protected JButton btnCancel = null;

	protected DefaultFormatterFactory defaultFormatterFactory = null; // @jve:decl-index=0:parse

	protected NumberFormatter numberFormatter = null; // @jve:decl-index=0:parse

	protected JButton btnTransferCustoms = null;

	protected JButton btnPreCustomsDeclarationCode = null;

	protected JTextField tfEmsNo = null;

	protected JTextField tfCompanyEmsNo = null;

	protected javax.swing.JLabel lbImpExpDate = null;

	protected javax.swing.JLabel lbImpExpPort = null;

	protected javax.swing.JLabel lbBalaceModeOrperTax = null;

	protected javax.swing.JLabel lbUsesOrMakeFactory = null;

	protected JTextField tfCustomsDeclarationSerialNumber = null;

	protected JComboBox cbbBalanceMode = null;

	protected JTextField tfManufacturer = null;

	protected Brief brief = null;

	protected JButton btnEffect = null;

	protected JButton btnCheckData = null;

	protected JButton btnUnreel = null;

	protected List containers = null; // @jve:decl-index=0:

	protected javax.swing.JLabel lbEmsNo = new JLabel();

	protected JLabel jLabel7 = null;

	private JLabel lbCustomsBroker;

	protected JTextField tfDeclaraCustomsBroker = null;

	protected JComboBox cbbDeclarationCompany = null;

	protected JLabel jLabel16 = null;

	protected JLabel jLabel18 = null;

	protected JLabel jLabel19 = null;

	protected JLabel jLabel20 = null;

	protected JTextField tfBondedWarehouse = null;

	protected SystemAction systemAction = null;

	protected int projectType = ProjectType.BCUS;

	protected JLabel lbCompanyEmsNo = null;

	protected JToolBar jToolBar1 = null;

	protected JButton jButton = null;

	protected JButton jButtonSj = null;

	protected JFooterTable jTable = null;

	protected JFooterScrollPane jScrollPane = null;

	private SpecialCustomsAuthorityAction specialCustomsAuthorityAction = null;
	private DZSCSpecialCustomsAuthorityAction dZSCSpecialCustomsAuthorityAction = null;
	private BCSSpecialCustomsAuthorityAction bCSSpecialCustomsAuthorityAction = null;
	protected ManualDeclareAction manualDecleareAction = null;
	private ImpCustomsAuthorityAction impCustomsAuthorityAction = null;

	private BCSImpCustomsAuthorityAction bCSImpCustomsAuthorityAction = null;

	private DZSCImpCustomsAuthorityAction dZSCImpCustomsAuthorityAction = null;
	private BaseEmsHead emsHead = null; // 电子帐册: // @jve:decl-index=0:

	private boolean isTaoda = false;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="773,90"

	private JMenuItem jMenuItem = null;

	private JMenuItem jMenuItem1 = null;

	private JMenuItem jMenuItem2 = null;

	private JMenuItem jMenuItem3 = null;

	private JMenuItem jMenuItem4 = null;

	private JMenuItem jMenuItem5 = null;

	private JMenuItem jMenuItem6 = null;

	private JMenuItem jMenuItem7 = null;

	private JLabel lbSupplmentType = null;

	private JComboBox cbbSupplmentType = null;

	private JLabel lbOperType = null;

	private JComboBox cbbOperType = null;

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

	// /**
	// * @return Returns the emsHeadH2k.
	// */
	// public BaseEmsHead getEmsHead() {
	// return emsHead;
	// }
	//
	// /**
	// * @param emsHeadH2k
	// * The emsHeadH2k to set.
	// */
	public void setEmsHead(BaseEmsHead emsHeadH2k) {
		this.emsHead = emsHeadH2k;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgBaseSpecialCustomsDeclaration() {
		super();
		initialize();
		// baseEncAction = (EncAction)
		// CommonVars.getApplicationContext().getBean(
		// "encAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		specialCustomsAuthorityAction = (SpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"specialCustomsAuthorityAction");
		bCSSpecialCustomsAuthorityAction = (BCSSpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"bCSSpecialCustomsAuthorityAction");
		dZSCSpecialCustomsAuthorityAction = (DZSCSpecialCustomsAuthorityAction) CommonVars
				.getApplicationContext().getBean(
						"dZSCSpecialCustomsAuthorityAction");
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
		components.add(this.tfCommodityNum);
		components.add(null);
		components.add(this.cbbCurrency);
		components.add(this.cbbCustomer);
		components.add(null);
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
		this.setTitle("特殊报关单编辑");
		this.setContentPane(getJContentPane());
		this.setSize(707, 543);

	}

	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			this.showPrimalData();
			this.containers = this.baseEncAction
					.findContainerByCustomsDeclaration(
							new Request(CommonVars.getCurrUser()),
							this.customsDeclaration);
			setState();
			if (this.dataState == DataState.ADD) {
				this.btnCancel.setEnabled(false);
			}
			if (commInfoModel == null) {
				initTable();
			}
		}
		super.setVisible(b);
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
			jToolBar.add(getBtnEffect());
			jToolBar.add(getBtnUnreel());
			jToolBar.add(getBtnCheckData());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnNonCoverPrint());
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
							DgBaseSpecialCustomsDeclaration.this
									.setCursor(new Cursor(Cursor.WAIT_CURSOR));
							fillData();
							if (jTabbedPane.getSelectedIndex() == 0) {
								// showData();
							} else if (jTabbedPane.getSelectedIndex() == 1) {
								initTable();
								btnAdd.requestFocus();
							}
							setState();
							DgBaseSpecialCustomsDeclaration.this
									.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						}
					});
		}
		return jTabbedPane;
	}

	protected JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(45, 30));
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
			int type = Integer.parseInt(((ItemProperty) cbbImpExpType
					.getSelectedItem()).getCode());
			if ((type == ImpExpType.EQUIPMENT_IMPORT
					|| type == ImpExpType.BACK_PORT_REPAIR || type == ImpExpType.EQUIPMENT_BACK_PORT)
					&& isFixEmsNoAndFixType(tfEmsNo.getText(),
							customsDeclaration.getFixType())) {
				List list;
				if (customsDeclaration.getFixType() == FixType.SHANZHI) {
					list = FixAssetQuery
							.getInstance()
							.findDutyCertDetailByCertNo(this.customsDeclaration);
					if (list == null) {
						return;
					}
					list = this.baseEncAction
							.addFixAssetCustomsDeclarationCommInfo(new Request(
									CommonVars.getCurrUser()),
									customsDeclaration, list);
				} else {
					list = FixtureQuery.getInstance()
							.findFixtureContractItemsByCustom(
									this.customsDeclaration);
					if (list == null) {
						return;
					}
					list = this.baseEncAction
							.addFixtureCustomsDeclarationCommInfo(new Request(
									CommonVars.getCurrUser()),
									customsDeclaration, list);
				}

				commInfoModel.addRows(list);
			} else {
				showCommInfoDialog(DataState.ADD);
			}
			this.countCommTotalPrice(commInfoModel.getList());
		}
	}

	protected JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(45, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editCustomsDeclarationData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 新增报关单，或者报关单商品信息
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
			if (commInfoModel == null) {
				return;
			}
			if (commInfoModel.getList().size() < 1) {
				return;
			}
			if (commInfoModel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(
						DgBaseSpecialCustomsDeclaration.this, "请选择你要修改的资料",
						"提示", 0);
				return;
			}
			// DgBaseSpecialCustomsDeclarationCommInfo dg = new
			// DgBaseSpecialCustomsDeclarationCommInfo();
			// dg.setDataState(DataState.EDIT);
			// dg.setCustomsDeclaration(customsDeclaration);
			// dg.setTableModel(customsDeclarationCommInfoModel);
			// dg.setBaseEncAction(this.baseEncAction);
			// dg.setVisible(true);
			showCommInfoDialog(DataState.EDIT);
		}
		countCommTotalPrice(commInfoModel.getList());
	}

	protected JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(45, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction
								.deleteCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.deleteCommodity(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
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
	 * 新增报关单，或者报关单商品信息
	 * 
	 */
	protected void deleteCustomsDeclarationData() {
		if (commInfoModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"请选择你要删除的资料", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(DgBaseSpecialCustomsDeclaration.this,
				"是否确定删除数据!!!", "提示", 0) != 0) {
			return;
		}
		baseEncAction.deleteCustomsDeclarationCommInfo(
				new Request(CommonVars.getCurrUser()),
				commInfoModel.getCurrentRows());
		initTable();
		countCommTotalPrice(commInfoModel.getList());
	}

	protected JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new Dimension(230, 30));
			jLabel.setBounds(5, 8, 40, 13);
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
			tfTelephone.setBounds(119, 3, 108, 25);
		}
		return tfTelephone;
	}

	protected JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("套打");
			btnPrint.setPreferredSize(new Dimension(45, 30));
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
	 * 得到随附单据名称2
	 */
	public String getattachedBillName2(String attachedbillCode) {
		String allName = "";
		if (customBaseAction == null || attachedbillCode == null
				|| attachedbillCode.equals("")) {
			return "";
		}
		char[] code = attachedbillCode.toCharArray();
		for (int i = 0; i < code.length; i++) {
			String name = customBaseAction.getLicenseDouName(String
					.valueOf(code[i]));
			allName = allName + " " + name.substring(0, code.length);
		}
		return allName;
	}

	protected JPanel getPnBaseInfo() {
		if (pnBaseInfo == null) {
			jLabel431 = new JLabel();
			jLabel431.setBounds(new Rectangle(439, 421, 58, 18));
			jLabel431.setText("进出口号");
			jLabel43 = new JLabel();
			jLabel43.setBounds(new Rectangle(438, 395, 60, 18));
			jLabel43.setText("车次号码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(341, 126, 48, 18));
			jLabel1.setText("工具编码");
			jLabel472 = new JLabel();
			jLabel472.setBounds(new Rectangle(239, 423, 72, 18));
			jLabel472.setText("\u5173\u8054\u62a5\u5173\u5355\u53f7");
			jLabel471 = new JLabel();
			jLabel471.setBounds(new Rectangle(22, 423, 60, 18));
			jLabel471.setText("\u5173\u8054\u624b\u518c\u53f7");
			jLabel502 = new JLabel();
			jLabel502.setBounds(new Rectangle(484, 227, 54, 20));
			jLabel502.setText("保费币制");
			jLabel501 = new JLabel();
			jLabel501.setBounds(new Rectangle(271, 251, 55, 19));
			jLabel501.setText("杂费币制");
			jLabel50 = new JLabel();
			jLabel50.setBounds(new Rectangle(21, 227, 50, 20));
			jLabel50.setText("运费币制");
			lbInvoiceNo = new JLabel();
			lbInvoiceNo.setBounds(new Rectangle(242, 396, 48, 21));
			lbInvoiceNo.setText("发票号码");
			lbInvoiceNo.setForeground(Color.blue);
			jLabel491 = new JLabel();
			jLabel491.setBounds(new Rectangle(493, 323, 48, 19));
			jLabel491.setText("\u5f55\u5165\u65f6\u95f4");
			jLabel16 = new JLabel();
			lbCustomsBroker = new JLabel();
			javax.swing.JLabel jLabel5 = new JLabel();
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
			lbImpExpDate = new JLabel();
			lbImpExpPort = new JLabel();
			javax.swing.JLabel jLabel22 = new JLabel();
			javax.swing.JLabel jLabel21 = new JLabel();
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			javax.swing.JLabel jLabel17 = new JLabel();
			javax.swing.JLabel jLabel15 = new JLabel();
			javax.swing.JLabel jLabel14 = new JLabel();
			javax.swing.JLabel jLabel13 = new JLabel();
			javax.swing.JLabel jLabel12 = new JLabel();
			javax.swing.JLabel jLabel11 = new JLabel();
			javax.swing.JLabel jLabel10 = new JLabel();
			javax.swing.JLabel jLabel9 = new JLabel();
			javax.swing.JLabel jLabel8 = new JLabel();
			javax.swing.JLabel jLabel6 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			lbBalaceModeOrperTax = new JLabel();
			lbUsesOrMakeFactory = new JLabel();
			pnBaseInfo = new JPanel();
			pnBaseInfo.setLayout(null);
			jLabel2.setBounds(6, 46, 65, 19);
			jLabel2.setText("进出口类型");
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel3.setBounds(183, 47, 49, 19);
			jLabel3.setText("预录入号");
			jLabel4.setBounds(432, 47, 48, 19);
			jLabel4.setText("统一编号");
			lbImpExpPort.setBounds(22, 72, 49, 19);
			lbImpExpPort.setText("进出口岸");
			jLabel6.setBounds(183, 73, 49, 19);
			jLabel6.setText("报关单号");
			jLabel6.setForeground(java.awt.Color.blue);
			lbImpExpDate.setBounds(341, 71, 50, 19);
			lbImpExpDate.setText("进口日期");
			lbImpExpDate.setForeground(java.awt.Color.blue);
			jLabel8.setBounds(498, 73, 49, 19);
			jLabel8.setText("申报日期");
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel9.setBounds(22, 98, 49, 19);
			jLabel9.setText("经营单位");
			jLabel10.setBounds(340, 97, 50, 19);
			jLabel10.setText("收货单位");
			jLabel11.setBounds(22, 124, 49, 19);
			jLabel11.setText("运输方式");
			jLabel12.setBounds(183, 125, 49, 19);
			jLabel12.setText("运输工具");
			jLabel13.setBounds(490, 125, 49, 19);
			jLabel13.setText("提运单号");
			jLabel14.setBounds(22, 150, 49, 19);
			jLabel14.setText("贸易方式");
			jLabel14.setForeground(java.awt.Color.blue);
			jLabel15.setBounds(183, 151, 49, 19);
			jLabel15.setText("征免性质");
			lbBalaceModeOrperTax.setBounds(414, 149, 59, 19);
			lbBalaceModeOrperTax.setText("征税比例%");
			jLabel17.setBounds(22, 176, 49, 19);
			jLabel17.setText("许可证号");
			jLabel18.setBounds(244, 177, 71, 19);
			jLabel18.setText("起运国(地区)");
			jLabel19.setBounds(458, 177, 62, 19);
			jLabel19.setText("境内目的地");
			jLabel20.setBounds(22, 204, 51, 19);
			jLabel20.setText("装货港");
			jLabel21.setBounds(183, 203, 49, 19);
			jLabel21.setText("批准文号");
			jLabel22.setBounds(343, 203, 61, 19);
			jLabel22.setText("合同协议号");
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel23.setBounds(498, 203, 50, 19);
			jLabel23.setText("成交方式");
			jLabel24.setBounds(183, 227, 49, 20);
			jLabel24.setText("运费");
			jLabel25.setBounds(21, 251, 50, 19);
			jLabel25.setText("保费");
			jLabel26.setBounds(433, 251, 35, 19);
			jLabel26.setText("杂费");
			jLabel27.setBounds(21, 276, 50, 19);
			jLabel27.setText("件数");
			jLabel27.setForeground(java.awt.Color.blue);
			jLabel28.setBounds(183, 276, 49, 19);
			jLabel28.setText("包装种类");
			jLabel29.setBounds(382, 276, 28, 19);
			jLabel29.setText("毛重");
			jLabel29.setForeground(java.awt.Color.blue);
			jLabel30.setBounds(516, 276, 28, 19);
			jLabel30.setText("净重");
			jLabel30.setForeground(java.awt.Color.blue);
			jLabel31.setBounds(21, 299, 50, 19);
			jLabel31.setText("集装箱号");
			jLabel31.setForeground(java.awt.Color.blue);
			jLabel32.setBounds(207, 299, 49, 19);
			jLabel32.setText("随附单据");
			lbUsesOrMakeFactory.setBounds(451, 299, 61, 19);
			lbUsesOrMakeFactory.setText("用途");
			jLabel34.setBounds(21, 323, 50, 19);
			jLabel34.setText("申报单位");
			jLabel35.setBounds(244, 323, 25, 19);
			jLabel35.setText("币制");
			jLabel35.setForeground(java.awt.Color.blue);
			jLabel36.setBounds(361, 323, 38, 19);
			jLabel36.setText("录入员");
			jLabel37.setBounds(21, 348, 50, 19);
			jLabel37.setText("客户名称");
			jLabel37.setForeground(java.awt.Color.blue);
			jLabel38.setBounds(244, 348, 25, 19);
			jLabel38.setText("汇率");
			jLabel39.setBounds(442, 348, 51, 19);
			jLabel39.setText("报送海关");
			jLabel40.setBounds(21, 372, 50, 19);
			jLabel40.setText("备注");
			jLabel41.setBounds(244, 372, 25, 19);
			jLabel41.setText("码头");
			jLabel42.setBounds(442, 372, 72, 19);
			jLabel42.setText("所有集装箱号");
			lbEmsNo.setBounds(184, 20, 49, 21);
			lbEmsNo.setText("帐册编号");
			lbCompanyEmsNo.setBounds(348, 20, 63, 21);
			lbCompanyEmsNo.setText("帐册流水号");
			jLabel5.setBounds(493, 20, 73, 21);
			jLabel5.setText("报关单流水号");
			lbCustomsBroker.setBounds(22, 21, 49, 17);
			lbCustomsBroker.setText("报关行");
			jLabel16.setBounds(21, 396, 50, 21);
			jLabel16.setText("保税仓库");
			pnBaseInfo.add(jLabel2, null);
			pnBaseInfo.add(getCbbImpExpType(), null);
			pnBaseInfo.add(jLabel3, null);
			pnBaseInfo.add(getTfPreCustomsDeclarationCode(), null);
			pnBaseInfo.add(jLabel4, null);
			pnBaseInfo.add(getTfUnificationCode(), null);
			pnBaseInfo.add(lbImpExpPort, null);
			pnBaseInfo.add(getCbbCustoms(), null);
			pnBaseInfo.add(jLabel6, null);
			pnBaseInfo.add(getTfCustomsDeclarationCode(), null);
			pnBaseInfo.add(lbImpExpDate, null);
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
			pnBaseInfo.add(getTfBillOfLading(), null);
			pnBaseInfo.add(jLabel14, null);
			pnBaseInfo.add(getCbbTradeMode(), null);
			pnBaseInfo.add(jLabel15, null);
			pnBaseInfo.add(getCbbLevyKind(), null);
			pnBaseInfo.add(lbBalaceModeOrperTax, null);
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
			pnBaseInfo.add(lbUsesOrMakeFactory, null);
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
			pnBaseInfo.add(jLabel5, null);
			pnBaseInfo.add(getTfCustomsDeclarationSerialNumber(), null);
			pnBaseInfo.add(getCbbBalanceMode(), null);
			pnBaseInfo.add(getTfMakeFactory(), null);
			pnBaseInfo.add(lbCustomsBroker, null);
			pnBaseInfo.add(getTfDeclaraCustomsBroker(), null);
			pnBaseInfo.add(getCbbDeclarationCompany(), null);
			pnBaseInfo.add(jLabel16, null);
			pnBaseInfo.add(getTfBondedWarehouse(), null);
			pnBaseInfo.add(getBtnEmsNo(), null);
			pnBaseInfo.add(getJButton9(), null);
			pnBaseInfo.add(jLabel491, null);
			pnBaseInfo.add(getTfCreateDate(), null);
			pnBaseInfo.add(getJButton3(), null);
			pnBaseInfo.add(lbInvoiceNo, null);
			pnBaseInfo.add(getTfInvoiceCode(), null);
			pnBaseInfo.add(getBtnInvoice(), null);
			pnBaseInfo.add(jLabel50, null);
			pnBaseInfo.add(getCbbFeeCurr(), null);
			pnBaseInfo.add(getCbbInsurCurr(), null);
			pnBaseInfo.add(getCbbOtherCurr(), null);
			pnBaseInfo.add(jLabel501, null);
			pnBaseInfo.add(jLabel502, null);
			pnBaseInfo.add(jLabel471, null);
			pnBaseInfo.add(getTfConnectNo(), null);
			pnBaseInfo.add(jLabel472, null);
			pnBaseInfo.add(getTfConnectDeclarationNo(), null);
			pnBaseInfo.add(jLabel1, null);
			pnBaseInfo.add(getTfdomesticConveyanceCode(), null);
			pnBaseInfo.add(getJButton1(), null);
			pnBaseInfo.add(jLabel43, null);
			pnBaseInfo.add(getTfCarNumber(), null);
			pnBaseInfo.add(jLabel431, null);
			pnBaseInfo.add(getTfexpAndImpNumber(), null);
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
			cbbImpExpType.setBounds(73, 45, 108, 20);
			cbbImpExpType.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setImpExpState();
					}
				}
			});
		}
		return cbbImpExpType;
	}

	/**
	 * 设置进出或出口时的表单状态
	 */
	protected void setImpExpState() {
		int type = Integer.parseInt(((ItemProperty) cbbImpExpType
				.getSelectedItem()).getCode());
		if (EncCommon.isImport(type) == true) {
			jLabel18.setText("起运国(地区)");
			jLabel19.setText("境内目的地");
			jLabel20.setText("装货港");
			lbImpExpDate.setText("进口日期");
			lbImpExpPort.setText("进口口岸");
			lbUsesOrMakeFactory.setText("用途");
			lbBalaceModeOrperTax.setText("征税比例");
			this.cbbUses.setVisible(true);
			this.tfPerTax.setVisible(true);
			this.cbbBalanceMode.setSelectedItem(null);
			this.tfManufacturer.setVisible(false);
			this.jButton1.setVisible(false);
			this.cbbBalanceMode.setVisible(false);
		} else {
			jLabel18.setText("运抵国(地区)");
			jLabel19.setText("境内货源地");
			jLabel20.setText("指运港");
			lbImpExpDate.setText("出口日期");
			lbImpExpPort.setText("出口口岸");
			lbUsesOrMakeFactory.setText("生产厂家");
			lbBalaceModeOrperTax.setText("结汇方式");
			cbbUses.setSelectedItem(null);
			this.tfPerTax.setText("");
			this.cbbUses.setSelectedItem(null);
			this.cbbUses.setVisible(false);
			this.tfPerTax.setVisible(false);
			this.tfManufacturer.setVisible(true);
			this.jButton1.setVisible(true);
			this.cbbBalanceMode.setVisible(true);
		}

		cbbImpExpType.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				if (e.getStateChange() != ItemEvent.SELECTED) {
					return;
				}
				setState();
				if (cbbImpExpType.getSelectedItem() != null) {
					Integer type = Integer
							.valueOf(((ItemProperty) cbbImpExpType
									.getSelectedItem()).getCode());
					if (type.equals(ImpExpType.TRANSFER_FACTORY_IMPORT)) {
					} else {
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

					}
				} else {
					// tfCustomsEnvelopBillNo.setText("");
				}
			}
		});
	}

	protected JTextField getTfPreCustomsDeclarationCode() {
		if (tfPreCustomsDeclarationCode == null) {
			tfPreCustomsDeclarationCode = new JTextField();
			tfPreCustomsDeclarationCode.setBounds(234, 47, 85, 21);
			tfPreCustomsDeclarationCode.setEditable(false);
		}
		return tfPreCustomsDeclarationCode;
	}

	protected JTextField getTfUnificationCode() {
		if (tfUnificationCode == null) {
			tfUnificationCode = new JTextField();
			tfUnificationCode.setBounds(482, 46, 87, 21);
			tfUnificationCode.setEditable(false);
		}
		return tfUnificationCode;
	}

	protected JComboBox getCbbCustoms() {
		if (cbbCustoms == null) {
			cbbCustoms = new JComboBox();
			cbbCustoms.setBounds(73, 71, 108, 20);
		}
		return cbbCustoms;
	}

	protected JTextField getTfCustomsDeclarationCode() {
		if (tfCustomsDeclarationCode == null) {
			tfCustomsDeclarationCode = new JTextField();
			tfCustomsDeclarationCode.setBounds(234, 73, 101, 21);
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
			cbbImpExpDate.setBounds(391, 71, 97, 20);
		}
		return cbbImpExpDate;
	}

	protected JCalendarComboBox getCbbDeclarationDate() {
		if (cbbDeclarationDate == null) {
			cbbDeclarationDate = new JCalendarComboBox();
			cbbDeclarationDate.setBounds(550, 71, 107, 21);
		}
		return cbbDeclarationDate;
	}

	protected JTextField getTfManageCompany() {
		if (tfManageCompany == null) {
			tfManageCompany = new JTextField();
			tfManageCompany.setBounds(73, 97, 262, 21);
			tfManageCompany.setEditable(false);
		}
		return tfManageCompany;
	}

	protected JTextField getTfAcceptGoodsCompany() {
		if (tfAcceptGoodsCompany == null) {
			tfAcceptGoodsCompany = new JTextField();
			tfAcceptGoodsCompany.setBounds(393, 97, 264, 21);
			tfAcceptGoodsCompany.setEditable(false);
		}
		return tfAcceptGoodsCompany;
	}

	protected JComboBox getCbbTransferMode() {
		if (cbbTransferMode == null) {
			cbbTransferMode = new JComboBox();
			cbbTransferMode.setBounds(73, 124, 108, 20);
		}
		return cbbTransferMode;
	}

	protected JTextField getTfConveyance() {
		if (tfConveyance == null) {
			tfConveyance = new JTextField();
			tfConveyance.setBounds(234, 125, 103, 21);
		}
		return tfConveyance;
	}

	protected JTextField getTfBillOfLading() {
		if (tfBillOfLading == null) {
			tfBillOfLading = new JTextField();
			tfBillOfLading.setBounds(541, 125, 95, 21);
		}
		return tfBillOfLading;
	}

	protected JComboBox getCbbTradeMode() {
		if (cbbTradeMode == null) {
			cbbTradeMode = new JComboBox();
			cbbTradeMode.setBounds(73, 150, 108, 20);
		}
		return cbbTradeMode;
	}

	protected JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(234, 150, 169, 20);
		}
		return cbbLevyKind;
	}

	protected JTextField getTfLicense() {
		if (tfLicense == null) {
			tfLicense = new JTextField();
			tfLicense.setBounds(73, 176, 169, 21);
		}
		return tfLicense;
	}

	protected JComboBox getCbbCountryOfLoadingOrUnloading() {
		if (cbbCountryOfLoadingOrUnloading == null) {
			cbbCountryOfLoadingOrUnloading = new JComboBox();
			cbbCountryOfLoadingOrUnloading.setBounds(316, 177, 139, 20);
		}
		return cbbCountryOfLoadingOrUnloading;
	}

	protected JComboBox getCbbDomesticDestinationOrSource() {
		if (cbbDomesticDestinationOrSource == null) {
			cbbDomesticDestinationOrSource = new JComboBox();
			cbbDomesticDestinationOrSource.setBounds(523, 176, 134, 20);
		}
		return cbbDomesticDestinationOrSource;
	}

	protected JComboBox getCbbPortOfLoadingOrUnloading() {
		if (cbbPortOfLoadingOrUnloading == null) {
			cbbPortOfLoadingOrUnloading = new JComboBox();
			cbbPortOfLoadingOrUnloading.setBounds(73, 203, 108, 20);
		}
		return cbbPortOfLoadingOrUnloading;
	}

	protected JTextField getTfAuthorizeFile() {
		if (tfAuthorizeFile == null) {
			tfAuthorizeFile = new JTextField();
			// tfAuthorizeFile.addKeyListener(new FocusActionListner(
			// getTfContract()));
			tfAuthorizeFile.setBounds(234, 203, 85, 21);
			tfAuthorizeFile.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (tfAuthorizeFile.getText() != null
							&& !tfAuthorizeFile.getText().trim().equals("")
							&& tfAuthorizeFile.getText().trim().length() != 9) {
						JOptionPane.showMessageDialog(
								DgBaseSpecialCustomsDeclaration.this,
								"批准文号应该为9位,请重新填写！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						tfAuthorizeFile.setText("");
					}
				}
			});
		}
		return tfAuthorizeFile;
	}

	// class FocusActionListner extends KeyAdapter {
	// Component component;
	//
	// FocusActionListner(Component component) {
	// this.component = component;
	// }
	//
	// public void keyPressed(KeyEvent e) {
	// KeyAdapterControl.setAddListener(true);
	// if (e.getKeyCode() == 10) {
	// component.requestFocus();
	// }
	// }
	// }

	protected JTextField getTfContract() {
		if (tfContract == null) {
			tfContract = new JTextField();
			tfContract.setBounds(406, 202, 91, 21);
		}
		return tfContract;
	}

	protected JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(550, 203, 107, 20);
		}
		return cbbTransac;
	}

	protected JComboBox getCbbFreightageType() {
		if (cbbFreightageType == null) {
			cbbFreightageType = new JComboBox();
			cbbFreightageType.setBounds(234, 227, 99, 20);
		}
		return cbbFreightageType;
	}

	protected JComboBox getCbbInsuranceType() {
		if (cbbInsuranceType == null) {
			cbbInsuranceType = new JComboBox();
			cbbInsuranceType.setBounds(73, 251, 108, 19);
		}
		return cbbInsuranceType;
	}

	protected JComboBox getCbbIncidentalExpensesType() {
		if (cbbIncidentalExpensesType == null) {
			cbbIncidentalExpensesType = new JComboBox();
			cbbIncidentalExpensesType.setBounds(470, 251, 99, 19);
		}
		return cbbIncidentalExpensesType;
	}

	protected JComboBox getCbbWrapType() {
		if (cbbWrapType == null) {
			cbbWrapType = new JComboBox();
			cbbWrapType.setBounds(234, 276, 146, 19);
		}
		return cbbWrapType;
	}

	protected JFormattedTextField getTfGrossWeight() {
		if (tfGrossWeight == null) {
			tfGrossWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfGrossWeight.setBounds(412, 276, 101, 19);
			// tfGrossWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfGrossWeight;
	}

	protected JFormattedTextField getTfNetWeight() {
		if (tfNetWeight == null) {
			tfNetWeight = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfNetWeight.setBounds(548, 276, 109, 19);
			// tfNetWeight.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfNetWeight;
	}

	class CustomDocument extends PlainDocument {
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
			// tfContainerNum.setDocument(new CustomDocument());
			tfContainerNum.setBounds(73, 299, 132, 19);
			// tfContainerNum.setEditable(false);
		}
		return tfContainerNum;
	}

	protected JTextField getTfAttachedBill() {
		if (tfAttachedBill == null) {
			tfAttachedBill = new JTextField();
			tfAttachedBill.setBounds(259, 299, 168, 19);
			tfAttachedBill.setEditable(false);
		}
		return tfAttachedBill;
	}

	protected JButton getBtnAttachedBill() {
		if (btnAttachedBill == null) {
			btnAttachedBill = new JButton();
			btnAttachedBill.setBounds(429, 299, 20, 19);
			btnAttachedBill.setText("...");
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
			cbbUses.setBounds(485, 299, 172, 20);
		}
		return cbbUses;
	}

	protected JComboBox getCbbCurrency() {
		if (cbbCurrency == null) {
			cbbCurrency = new JComboBox();
			cbbCurrency.setBounds(271, 323, 86, 19);
			cbbCurrency.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

					setRate();
				}
			});
		}
		return cbbCurrency;
	}

	protected JTextField getTfCreater() {
		if (tfCreater == null) {
			tfCreater = new JTextField();
			tfCreater.setBounds(403, 323, 84, 19);
			tfCreater.setEditable(false);
		}
		return tfCreater;
	}

	protected JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(73, 348, 167, 19);
		}
		return cbbCustomer;
	}

	protected JFormattedTextField getTfExchangeRate() {
		if (tfExchangeRate == null) {
			tfExchangeRate = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfExchangeRate.setBounds(271, 348, 169, 19);
			tfExchangeRate.setFormatterFactory(getDefaultFormatterFactory());
		}
		return tfExchangeRate;
	}

	protected JComboBox getCbbDeclarationCustoms() {
		if (cbbDeclarationCustoms == null) {
			cbbDeclarationCustoms = new JComboBox();
			cbbDeclarationCustoms.setBounds(501, 348, 156, 19);
		}
		return cbbDeclarationCustoms;
	}

	protected JTextField getTfMemos() {
		if (tfMemos == null) {
			tfMemos = new JTextField();
			tfMemos.setBounds(73, 372, 147, 19);
			tfMemos.setEditable(false);
		}
		return tfMemos;
	}

	protected JButton getBtnMemo() {
		if (btnMemo == null) {
			btnMemo = new JButton();
			btnMemo.setBounds(220, 372, 21, 19);
			btnMemo.setText("...");
			btnMemo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationMemo dg = new DgCustomsDeclarationMemo();
					if (!tfMemos.getText().trim().equals("")) {
						dg.setMemoStr(tfMemos.getText());
					}
					dg.setCertificateCode(customsDeclaration
							.getCertificateCode());
					dg.setContainerNum(customsDeclaration.getContainerNum());
					dg.setVisible(true);
					if (dg.isOk()) {
						tfMemos.setText(dg.returnMemoValue());
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
			cbbPredock.setBounds(271, 372, 169, 19);
			cbbPredock.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

					if (e.getItem() != null
							&& e.getStateChange() == ItemEvent.SELECTED
							&& tfMemos.getText().indexOf("装卸口岸") < 0) {
						System.out.println("itemStateChanged()");
						Customs customs = (Customs) cbbDeclarationCustoms
								.getSelectedItem();
						if (customs != null) {
							if (!"52".equals(customs.getCode().substring(0, 2))) {
								return;
							}
						}
						String shortName = ((PreDock) e.getItem())
								.getShortName();
						System.out.println("shortName=" + shortName);
						tfMemos.setText(tfMemos.getText().concat("[装卸口岸]:")
								+ shortName);
					}
				}
			});
		}
		return cbbPredock;
	}

	protected JTextField getTfAllContainerNum() {
		if (tfAllContainerNum == null) {
			tfAllContainerNum = new JTextField();
			tfAllContainerNum.setBounds(515, 372, 125, 19);
			tfAllContainerNum.setEditable(false);
		}
		return tfAllContainerNum;
	}

	protected JButton getBtnContainer() {
		if (btnContainer == null) {
			btnContainer = new JButton();
			btnContainer.setBounds(640, 372, 17, 19);
			btnContainer.setText("...");
			btnContainer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationContainer dg = new DgCustomsDeclarationContainer();
					dg.setBaseCustomsDeclaration(DgBaseSpecialCustomsDeclaration.this.customsDeclaration);
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
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected void initUIComponents() {
		// 初始化进出口类型
		this.cbbImpExpType.removeAllItems();
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_IMPORT).toString(), "料件进口"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "料件转厂"));
		this.cbbImpExpType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "一般贸易进口"));

		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES), "边角料内销"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));

		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.EQUIPMENT_IMPORT), "设备进口"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.BACK_PORT_REPAIR), "退港维修"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.EQUIPMENT_BACK_PORT), "设备退港"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.REMAIN_FORWARD_EXPORT), "余料结转(出口报关单)"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.EXPORT_STORAGE), "出口仓储"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.IMPORT_STORAGE), "进口仓储"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.MATERIAL_DOMESTIC_SALES), "料件内销"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.MATERIAL_EXCHANGE), "料件退换"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.MATERIAL_REOUT), "料件复出"));
		this.cbbImpExpType.addItem(new ItemProperty(String
				.valueOf(ImpExpType.REMAIN_FORWARD_IMPORT), "余料结转(进口报关单)"));

		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbImpExpType);
		this.cbbImpExpType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		// 初始化报关员与联系电话
		this.jComboBox.removeAllItems();
		List customsUserList = materialManageAction
				.findCustomsUser(new Request(CommonVars.getCurrUser()));
		for (int i = 0; i < customsUserList.size(); i++) {
			CustomsUser user = (CustomsUser) customsUserList.get(i);
			this.jComboBox.addItem(user.getName());
		}
		this.jComboBox.setUI(new CustomBaseComboBoxUI(100));
		// 初始化进口口岸
		this.cbbCustoms
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustoms);
		this.cbbCustoms.setRenderer(CustomBaseRender.getInstance().getRender());
		// 初始化运输方式
		this.cbbTransferMode.setModel(CustomBaseModel.getInstance()
				.getTransfModel());
		this.cbbTransferMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbTransferMode);
		// 初始化贸易方式
		this.cbbTradeMode.setModel(CustomBaseModel.getInstance()
				.getTradeModel());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTradeMode);
		this.cbbTradeMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbTradeMode.setSelectedItem(null);
		// 初始化征免性质
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.cbbLevyKind
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLevyKind);
		// 初始化起运国
		this.cbbCountryOfLoadingOrUnloading.setModel(CustomBaseModel
				.getInstance().getCountryModel());
		this.cbbCountryOfLoadingOrUnloading.setRenderer(CustomBaseRender
				.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbCountryOfLoadingOrUnloading);
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
				cbbPortOfLoadingOrUnloading);
		// 初始化成交方式
		this.cbbTransac.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		this.cbbTransac.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbTransac);
		// 初始化包装种类
		List listwarp = customBaseAction.findWrap();
		this.cbbWrapType.setModel(new DefaultComboBoxModel(listwarp.toArray()));
		this.cbbWrapType.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbWrapType, "code", "name");
		cbbWrapType.setSelectedItem(null);
		// 初始化结汇方式
		this.cbbBalanceMode.setModel(CustomBaseModel.getInstance()
				.getBalanceModeModel());
		this.cbbBalanceMode.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		this.cbbBalanceMode.setMaximumRowCount(CustomBaseModel.getInstance()
				.getBalanceModeModel().getSize());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBalanceMode);

		// 初始化用途
		this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbUses);

		/*
		 * // 初始化包装种类
		 * this.cbbUses.setModel(CustomBaseModel.getInstance().getUseModel());
		 * this.cbbUses.setRenderer(CustomBaseRender.getInstance().getRender());
		 */

		// 初始化币制
		this.cbbCurrency.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.cbbCurrency
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCurrency);
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
				cbbDeclarationCustoms);
		// 初始化码头
		this.cbbPredock.setModel(CustomBaseModel.getInstance()
				.getPreDockModel());
		this.cbbPredock.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbPredock);
		cbbPredock.setSelectedItem(null);
		// 初始化运费
		this.cbbFreightageType.removeAllItems();
		this.cbbFreightageType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.FREIGHT_RATE), "运费率"));
		this.cbbFreightageType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.FREIGHT_UNITPRICE), "运费单价/吨"));
		this.cbbFreightageType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.FREIGHT_TOTALPRICE), "运费总价"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbFreightageType);
		this.cbbFreightageType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		// 初始化运保费
		this.cbbInsuranceType.removeAllItems();
		this.cbbInsuranceType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.INSURANCE_RATE), "保费率"));
		this.cbbInsuranceType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.INSURANCE_TOTALPRICE), "保费总价"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbInsuranceType);
		this.cbbInsuranceType.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		// 初始化杂费
		this.cbbIncidentalExpensesType.removeAllItems();
		this.cbbIncidentalExpensesType.addItem(new ItemProperty(String
				.valueOf(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_RATE),
				"杂费率"));
		this.cbbIncidentalExpensesType
				.addItem(new ItemProperty(
						String.valueOf(BaseCustomsDeclaration.INCEDENTAL_EXPENSES_TOTALPRICE),
						"杂费总价"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbIncidentalExpensesType);
		this.cbbIncidentalExpensesType.setRenderer(CustomBaseRender
				.getInstance().getRender());
		// 初始化客户commonBaseCodeAction
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCustomer,
				"code", "name", 270);
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 100, 170));
		// 初始申报单位systemAction
		List companyList = systemAction.findCompanies();
		this.cbbDeclarationCompany.setModel(new DefaultComboBoxModel(
				companyList.toArray()));
		this.cbbDeclarationCompany.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 100, 170));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbDeclarationCompany, "code", "name", 270);

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
			// this.customsDeclaration.setEmsHeadH2k(this.emsHead.getEmsNo());
			// this.tfCompanyEmsNo.setText(this.emsHead.getCopEmsNo());
			Company company = (Company) CommonVars.getCurrUser().getCompany();
			// customsDeclaration.setTradeCode(emsHead.getTradeCode());
			// customsDeclaration.setTradeName(emsHead.getTradeName());
			// customsDeclaration.setMachCode(emsHead.getTradeCode());
			// customsDeclaration.setMachName(emsHead.getMachName());
			customsDeclaration.setTradeCode(company.getBuCode());
			customsDeclaration.setTradeName(company.getBuName());
			customsDeclaration.setMachCode(company.getCode());
			customsDeclaration.setMachName(company.getName());

			this.customsDeclaration.setImpExpFlag(Integer
					.valueOf(ImpExpFlag.SPECIAL));
			// this.customsDeclaration.setSerialNumber(this.baseEncAction
			// .getCustomsDeclarationSerialNumber(new Request(CommonVars
			// .getCurrUser()), ImpExpFlag.SPECIAL, ""));
			this.customsDeclaration.setCreater(CommonVars.getCurrUser());
			this.customsDeclaration.setCompany(company);
			// this.customsDeclaration
			// .setManageCompany(getBreif(((Company) CommonVars
			// .getCurrUser().getCompany()).getBuCode()));
			this.customsDeclaration
					.setManufacturer(getBreif(company.getCode()));

			this.customsDeclaration.setDeclarationDate(new Date());
			this.customsDeclaration.setImpExpDate(new Date());
			this.customsDeclaration.setCreateDate(new Date());
			// 报关员和报关员电话
			this.customsDeclaration.setCustomser(company.getAppCusMan());
			this.customsDeclaration.setTelephone(company.getAppCusManTel());
		} else if (dataState == DataState.EDIT || dataState == DataState.BROWSE) {
			customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
					.getCurrentRow();
		}
		showData();
		if (dataState != DataState.EDIT && dataState != DataState.BROWSE) {
			setRate();
		}

	}

	/**
	 * 显示报关数据
	 * 
	 */
	@SuppressWarnings("deprecation")
	protected void showData() {
		if (this.customsDeclaration == null) {
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
			this.tfBillOfLading.setText(this.customsDeclaration
					.getBillOfLading());
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
		if (this.customsDeclaration.getConveyance() != null) {
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
		if (this.customsDeclaration.getCreateDate() != null) {
			this.tfCreateDate.setDate(this.customsDeclaration.getCreateDate());
		} else {
			this.tfCreateDate.setDate(null);
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
		this.cbbDeclarationCustoms.setSelectedItem(this.customsDeclaration
				.getDeclarationCustoms());
		if (this.customsDeclaration.getManufacturer() != null) {
			this.tfManufacturer.setText(this.customsDeclaration
					.getManufacturer().getName());
		} else {
			this.tfManufacturer.setText("");
		}
		if (this.customsDeclaration.getDeclarationDate() != null) {
			this.cbbDeclarationDate.setDate(this.customsDeclaration
					.getDeclarationDate());
		} else {
			this.cbbDeclarationDate.setDate(null);
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
		if (this.customsDeclaration.getImpExpDate() != null) {
			this.cbbImpExpDate.setDate(this.customsDeclaration.getImpExpDate());
		} else {
			this.cbbImpExpDate.setDate(null);
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
		if (this.customsDeclaration.getEmsHeadH2k() != null) {
			this.tfEmsNo.setText(this.customsDeclaration.getEmsHeadH2k());
		} else {
			this.tfEmsNo.setText("");
		}
		if (this.customsDeclaration.getPreCustomsDeclarationCode() != null) {
			this.tfPreCustomsDeclarationCode.setText(this.customsDeclaration
					.getPreCustomsDeclarationCode());
		} else {
			this.tfPreCustomsDeclarationCode.setText("");
		}
		cbbPredock.setSelectedItem(this.customsDeclaration.getPredock());
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
		if (this.customsDeclaration.getAllContainerNum() != null) {
			this.tfAllContainerNum.setText(this.customsDeclaration
					.getAllContainerNum());
		} else {
			this.tfAllContainerNum.setText("");
		}
		if (this.customsDeclaration.getInvoiceCode() != null) {
			this.tfInvoiceCode
					.setText(this.customsDeclaration.getInvoiceCode());
		} else {
			this.tfInvoiceCode.setText("");
		}
		// 报关员输入方式修改 by sls
		if (this.customsDeclaration.getCustomser() != null) {
			this.jComboBox.setSelectedItem(this.customsDeclaration
					.getCustomser().toString());
		} else {
			this.jComboBox.setSelectedIndex(-1);
		}
		// if (this.customsDeclaration.getCustomser() != null) {
		// this.tfCustomer.setText(this.customsDeclaration.getCustomser());
		// } else {
		// this.tfCustomer.setText("");
		// }
		if (this.customsDeclaration.getTelephone() != null) {
			this.tfTelephone.setText(this.customsDeclaration.getTelephone());
		} else {
			this.tfTelephone.setText("");
		}
		this.cbbBalanceMode.setSelectedItem(this.customsDeclaration
				.getBalanceMode());
		if (this.customsDeclaration.getBondedWarehouse() != null) {
			this.tfBondedWarehouse.setText(this.customsDeclaration
					.getBondedWarehouse());
		} else {
			this.tfBondedWarehouse.setText("");
		}
		this.cbbDeclarationCompany.setSelectedItem(this.customsDeclaration
				.getDeclarationCompany());
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

		if (this.customsDeclaration.getExtendField1() != null) {
			this.tfCarNumber.setText(this.customsDeclaration.getExtendField1());
		} else {
			this.tfCarNumber.setText("");
		}

		if (this.customsDeclaration.getExtendField2() != null) {
			this.tfexpAndImpNumber.setText(this.customsDeclaration
					.getExtendField2());
		} else {
			this.tfexpAndImpNumber.setText("");
		}

		// lm edit by 2009-12-10
		// if (projectType == ProjectType.BCUS) {
		// this.tfEmsNo.setText(emsHead.getEmsNo());
		// }

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

	/**
	 * 填充数据
	 */
	@SuppressWarnings("deprecation")
	protected void fillData() {
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
		this.customsDeclaration.setEmsHeadH2k(this.tfEmsNo.getText().trim());
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
		// .getCurrUser()), ImpExpFlag.SPECIAL,
		// customsDeclaration.getEmsHeadH2k());
		// this.tfCustomsDeclarationSerialNumber.setText(serialNumber
		// .toString());
		// this.customsDeclaration.setSerialNumber(serialNumber);
		//
		// }
		// if ((!this.tfPreCustomsDeclarationCode.getText().trim().equals(
		// this.customsDeclaration.getPreCustomsDeclarationCode()))
		// && (this.customsDeclaration.getPreCustomsDeclarationCode() != null))
		// {
		// tfPreCustomsDeclarationCode.setText(baseEncAction
		// .getMaxPreCustomsDeclarationCode(new Request(CommonVars
		// .getCurrUser()), projectType));
		// } else {
		// tfPreCustomsDeclarationCode.setText("");
		// }
		this.customsDeclaration.setExtendField1(this.tfCarNumber.getText()
				.trim());
		this.customsDeclaration.setExtendField2(this.tfexpAndImpNumber
				.getText().trim());

		this.customsDeclaration
				.setPreCustomsDeclarationCode(this.tfPreCustomsDeclarationCode
						.getText().trim());
		this.customsDeclaration.setUnificationCode(this.tfUnificationCode
				.getText().trim());
		this.customsDeclaration.setCustoms((Customs) this.cbbCustoms
				.getSelectedItem());
		this.customsDeclaration
				.setCustomsDeclarationCode(this.tfCustomsDeclarationCode
						.getText().trim());

		/*
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); if
		 * (this.cbbImpExpDate.getDate() != null) { } else {
		 * this.customsDeclaration.setImpExpDate(null); } if
		 * (this.cbbDeclarationDate.getDate() != null) {
		 * this.customsDeclaration.setDeclarationDate(java.sql.Date
		 * .valueOf(dateFormat.format(this.cbbDeclarationDate .getDate()))); }
		 * else { this.customsDeclaration.setDeclarationDate(null); }
		 */

		this.customsDeclaration.setTransferMode((Transf) this.cbbTransferMode
				.getSelectedItem());
		this.customsDeclaration.setConveyance(this.tfConveyance.getText()
				.trim());
		this.customsDeclaration.setBillOfLading(this.tfBillOfLading.getText()
				.trim());
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
			this.customsDeclaration.setCommodityNum(Integer
					.valueOf(this.tfCommodityNum.getValue().toString()));
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
		this.customsDeclaration.setUses((Uses) this.cbbUses.getSelectedItem());
		this.customsDeclaration.setAttachedBill(this.tfAttachedBill.getText());
		this.customsDeclaration.setContainerNum(this.tfContainerNum.getText());
		this.customsDeclaration.setInvoiceCode(this.tfInvoiceCode.getText());
		this.customsDeclaration
				.setBalanceMode((BalanceMode) this.cbbBalanceMode
						.getSelectedItem());
		this.customsDeclaration.setBondedWarehouse(this.tfBondedWarehouse
				.getText());
		this.customsDeclaration
				.setDeclarationCompany((Company) this.cbbDeclarationCompany
						.getSelectedItem());
		if (this.jComboBox.getSelectedItem() != null) {
			this.customsDeclaration.setCustomser(this.jComboBox
					.getSelectedItem().toString());
		} else {
			this.customsDeclaration.setCustomser(null);
		}
		// this.customsDeclaration.setCustomser(this.tfCustomer.getText());
		this.customsDeclaration.setTelephone(this.tfTelephone.getText());
		if (this.tfCreateDate.getDate() != null) {
			this.customsDeclaration.setCreateDate(this.tfCreateDate.getDate());
		} else {
			this.customsDeclaration.setCreateDate(null);
		}
		this.customsDeclaration.setRelativeManualNo(this.tfConnectNo.getText()
				.trim());
		this.customsDeclaration.setRelativeId(this.tfConnectDeclarationNo
				.getText().trim());

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
		boolean isEffective = customsDeclaration.getEffective() == null ? false
				: customsDeclaration.getEffective().booleanValue();
		boolean isSend = customsDeclaration.getIsSend() == null ? false
				: customsDeclaration.getIsSend().booleanValue();
		btnAdd.setEnabled(index == 0 && dataState == DataState.BROWSE
				|| dataState == DataState.BROWSE && index == 1 && !isEffective);
		btnEdit.setEnabled(dataState == DataState.BROWSE && !isEffective
				&& !isSend);
		jButton.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnDelete.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnSave.setEnabled(dataState != DataState.BROWSE && !isEffective);
		btnCancel.setEnabled(dataState != DataState.BROWSE && !isEffective);
		btnDelete.setVisible(index == 1);
		btnEffect.setEnabled(dataState == DataState.BROWSE && !isEffective);
		btnUnreel.setEnabled(dataState == DataState.BROWSE && isEffective);
		btnEffect.setVisible(index == 0);
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
		// this.tfPreCustomsDeclarationCode
		// .setEditable(dataState != DataState.BROWSE);
		this.cbbCustoms.setEnabled(dataState != DataState.BROWSE);
		this.tfCustomsDeclarationCode
				.setEditable(dataState != DataState.BROWSE);
		this.cbbImpExpDate.setEnabled(dataState != DataState.BROWSE);
		this.cbbDeclarationDate.setEnabled(dataState != DataState.BROWSE);
		this.cbbTransferMode.setEnabled(dataState != DataState.BROWSE);
		this.tfConveyance.setEditable(dataState != DataState.BROWSE);
		this.tfBillOfLading.setEditable(dataState != DataState.BROWSE);
		this.tfdomesticConveyanceCode
				.setEditable(dataState != DataState.BROWSE);
		this.cbbTradeMode.setEnabled(dataState != DataState.BROWSE);
		this.cbbLevyKind.setEnabled(dataState != DataState.BROWSE);
		this.tfPerTax.setEditable(dataState != DataState.BROWSE);
		this.tfLicense.setEditable(dataState != DataState.BROWSE);
		this.tfContainerNum.setEditable(dataState != DataState.BROWSE);
		this.cbbCountryOfLoadingOrUnloading
				.setEnabled(dataState != DataState.BROWSE);
		this.cbbDomesticDestinationOrSource
				.setEnabled(dataState != DataState.BROWSE);
		this.cbbPortOfLoadingOrUnloading
				.setEnabled(dataState != DataState.BROWSE);
		this.tfAuthorizeFile.setEditable(dataState != DataState.BROWSE);
		this.tfInvoiceCode.setEditable(dataState != DataState.BROWSE);
		this.tfContract.setEditable(dataState != DataState.BROWSE);
		this.cbbTransac.setEnabled(dataState != DataState.BROWSE);
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
		this.cbbBalanceMode.setEnabled(dataState != DataState.BROWSE);
		this.tfBondedWarehouse.setEditable(dataState != DataState.BROWSE);
		this.cbbDeclarationCompany.setEnabled(dataState != DataState.BROWSE);
		this.tfEmsNo.setEditable(dataState != DataState.BROWSE);
		this.btnEmsNo.setEnabled(dataState != DataState.BROWSE);
		this.btnInvoice.setEnabled(dataState != DataState.BROWSE);
		this.jButton3.setEnabled(dataState != DataState.BROWSE);
		this.tfConnectNo.setEditable(dataState != DataState.BROWSE);
		this.tfConnectDeclarationNo.setEditable(dataState != DataState.BROWSE);
		this.tfManufacturer.setEnabled(dataState != DataState.BROWSE);
		this.jButton1.setEnabled(dataState != DataState.BROWSE);
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
	 * 统计商品总价,同时更新界面
	 */
	private void countCommTotalPrice(List list) {
		BaseCustomsDeclarationCommInfo tmp = null;
		Double commTotalPrice = 0.0;
		for (int i = 0; i < list.size(); i++) {
			tmp = (BaseCustomsDeclarationCommInfo) list.get(i);
			if (tmp != null && tmp.getCommTotalPrice() != null) {
				commTotalPrice = commTotalPrice + tmp.getCommTotalPrice();
			}
		}
		jLabel33.setText("商品总金额："
				+ CommonUtils.formatDoubleByDigit(commTotalPrice, 4));
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

						list.add(addColumn("商品编号", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 100));
						list.add(addColumn("型号规格", "commSpec", 100));
						list.add(addColumn("数量", "commAmount", 60));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("法定数量", "firstAmount", 80));
						list.add(addColumn("法定单位", "legalUnit.name", 80));
						list.add(addColumn("单位重量", "unitWeight", 80));

						ItemProperty selectedItem = (ItemProperty) cbbImpExpType
								.getSelectedItem();

						int type = 0;

						if (selectedItem != null) {

							type = Integer.parseInt(selectedItem.getCode());

						}

						// 从进出口类型判断 进/出口
						if (EncCommon.isImport(type) == true) {

							list.add(addColumn("原产国", "country.name", 80));

						} else {

							list.add(addColumn("目的国", "country.name", 80));

						}

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

						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// 页脚
		commInfoModel.clearFooterTypeInfo();

		commInfoModel.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));

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

	}

	/**
	 * 检查数据
	 */
	protected boolean checkData() {
		if (projectType == ProjectType.BCS) {
			ItemProperty item = (ItemProperty) cbbImpExpType.getSelectedItem();
			if (String.valueOf(ImpExpType.GENERAL_TRADE_IMPORT).equals(
					item.getCode())
					|| String.valueOf(ImpExpType.GENERAL_TRADE_EXPORT).equals(
							item.getCode())) {
				if (tfContract.getText() != null
						&& tfContract.getText().trim().length() > 0) {
					if (JOptionPane.NO_OPTION == JOptionPane.showOptionDialog(
							getParent(), "进出口类型为一般贸易时,合同协议号须为空,是否继续保存？", "提示",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "是", "否" }, "否")) {
						return false;
					}
				}
			}
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
		return EncCommon.checkSpecialCustomsDeclarationData(customsDeclaration,
				this);
	}

	/**
	 * This method initializes tfPerTax
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	protected JFormattedTextField getTfPerTax() {
		if (tfPerTax == null) {
			tfPerTax = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfPerTax.setBounds(473, 149, 185, 20);
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
			tfFreightage.setBounds(336, 227, 87, 20);
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
			tfInsurance.setBounds(183, 251, 87, 19);
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
			tfIncidentalExpenses.setBounds(570, 251, 87, 19);
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
			tfCommodityNum.setBounds(73, 276, 108, 19);
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
			btnSave.setPreferredSize(new Dimension(45, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String customsSavaCheck = manualDecleareAction.getBpara(
							new Request(CommonVars.getCurrUser(), true),
							BcusParameter.CUSTOMS_SAVA_CHECK);
					if (customsSavaCheck != null
							&& "1".equals(customsSavaCheck)) {
						if (checkData()) {
							saveCustom();
							showData();
						}
					} else {
						saveCustom();
						showData();
						if (commInfoModel == null) {
							initTable();
						}
						countCommTotalPrice(commInfoModel.getList());
					}

					// specialCustomsAuthorityAction.saveCustoms(new Request(
					// CommonVars.getCurrUser()));
					// fillData();
					// if (DgBaseSpecialCustomsDeclaration.this.cbbImpExpType
					// .getSelectedItem() == null) {
					// JOptionPane.showMessageDialog(null, "进出口类型不可为空！！",
					// "信息!!", JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }
					// customsDeclaration.setIsEmsCav(false);
					// customsDeclaration.setIsCheck(false);
					// saveData();
					// showData();
					// countCommTotalPrice(commInfoModel.getList());
				}
			});
		}
		return btnSave;
	}

	protected boolean saveCustom() {
		if (projectType == ProjectType.BCUS) {
			specialCustomsAuthorityAction.saveCustoms(new Request(CommonVars
					.getCurrUser()));
		} else if (projectType == ProjectType.DZSC) {
			dZSCSpecialCustomsAuthorityAction.saveCustoms(new Request(
					CommonVars.getCurrUser()));
		} else if (projectType == ProjectType.BCS) {
			bCSSpecialCustomsAuthorityAction.saveCustoms(new Request(CommonVars
					.getCurrUser()));
		}

		fillData();
		if (customsDeclaration.getImpExpType() == null) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
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
			btnCancel.setPreferredSize(new Dimension(45, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (customsDeclarationModel.getCurrentRow() != null) {
						customsDeclaration = (BaseCustomsDeclaration) customsDeclarationModel
								.getCurrentRow();
					} else {
						customsDeclaration = null;
					}
					showData();
					dataState = DataState.BROWSE;
					setState();
					countCommTotalPrice(commInfoModel.getList());
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
			btnTransferCustoms.setBounds(570, 44, 87, 23);
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

	/**
	 * 转关附加
	 * 
	 */
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
		// DgTransferCustomsConveyance dg = new DgTransferCustomsConveyance();
		// dg.setCustomsDeclaration(customsDeclaration);
		// dg.setBaseEncAction(baseEncAction);
		// // 如果是汽车运输
		// if (cbbTransferMode.getSelectedItem() != null
		// && ((Transf) cbbTransferMode.getSelectedItem()).getCode()
		// .trim().equals("4")) {
		// dg.setTransf((Transf) cbbTransferMode.getSelectedItem());
		// Calendar calendar = cbbImpExpDate.getCalendar();
		// dg.setImportDate(String.valueOf(calendar.get(Calendar.YEAR)) + "-"
		// + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-"
		// + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		// dg.setNumberPlate(tfBillOfLading.getText());
		// dg.setTruckTransfer(true);
		// }
		// dg.setVisible(true);
		DgTransferCustomsConveyance dg = new DgTransferCustomsConveyance();
		dg.setBaseEncAction(baseEncAction);
		dg.setCustomsDeclaration(customsDeclaration);
		// 如果是汽车运输或江海运输
		if (cbbTransferMode.getSelectedItem() != null) {

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
			dg.setNumberPlate(tfBillOfLading.getText());
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
			btnPreCustomsDeclarationCode.setBounds(320, 45, 108, 23);
			btnPreCustomsDeclarationCode.setText("产生预录入号");
			btnPreCustomsDeclarationCode
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(null,
									"要产生预录入号吗?", "确认", 0) == 0) {
								tfPreCustomsDeclarationCode.setText(baseEncAction
										.getMaxPreCustomsDeclarationCode(new Request(
												CommonVars.getCurrUser())));
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
			tfEmsNo.setBounds(234, 20, 85, 21);
			// tfEmsNo.setEditable(false);
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
			tfCompanyEmsNo.setBounds(413, 20, 67, 21);
			// tfCompanyEmsNo.setEditable(false);
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
			tfCustomsDeclarationSerialNumber.setEditable(false);
			tfCustomsDeclarationSerialNumber.setBounds(568, 20, 89, 21);
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
			cbbBalanceMode.setBounds(473, 149, 185, 20);
			cbbBalanceMode.setVisible(false);
		}
		return cbbBalanceMode;
	}

	/**
	 * This method initializes tfManufacturer
	 * 
	 * @return javax.swing.JTextField
	 */
	protected JTextField getTfMakeFactory() {
		if (tfManufacturer == null) {
			tfManufacturer = new JTextField();
			tfManufacturer.setBounds(508, 299, 130, 19);
			tfManufacturer.setVisible(false);
		}
		return tfManufacturer;
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
			btnEffect.setPreferredSize(new Dimension(45, 30));
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction
								.effectCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.effectCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
								.effectCustoms(new Request(CommonVars
										.getCurrUser()));
					}
					effectCustomsDeclaration();
					countCommTotalPrice(commInfoModel.getList());
				}
			});
		}
		return btnEffect;
	}

	/**
	 * 生效报关单
	 * 
	 */
	protected void effectCustomsDeclaration() {
		if (DgBaseSpecialCustomsDeclaration.this.tfCustomsDeclarationCode
				.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"报关单号不能为空！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (DgBaseSpecialCustomsDeclaration.this.tfCustomsDeclarationCode
				.getText().trim().length() != 18) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"报关单号必须为18位！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (!checkData()) {
			return;
		}
		if (commInfoModel == null) {
			initTable();
		}
		if (commInfoModel.getList().size() < 1) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"无商品信息资料所以不能生效！", "提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (JOptionPane.showConfirmDialog(DgBaseSpecialCustomsDeclaration.this,
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
	 * This method initializes btnCheckData
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getBtnCheckData() {
		if (btnCheckData == null) {
			btnCheckData = new JButton();
			btnCheckData.setText("检查");
			btnCheckData.setPreferredSize(new Dimension(45, 30));
			btnCheckData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					checkOther();
					if (checkData()) {
						JOptionPane.showMessageDialog(
								DgBaseSpecialCustomsDeclaration.this,
								"数据检查成功，没有错误！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						customsDeclaration.setIsCheck(true);
						saveData();
					}
				}
			});

		}
		return btnCheckData;
	}

	/**
	 * 1、进出口报关单、特殊报关单：增加报关单逻辑检查功能； 总价小数位不能超过2位，包装种类的代码只能是1位；
	 * 申报数量、法定数量的小数位不能大于4位， 关联报关单号不能小于大于18位、关联手册号不能小于大于12位； 商品编码不能小于10位。
	 * 
	 * @return
	 */
	private boolean checkOther() {
		// 关联报关单号=18位
		if (!RegexUtil.checkEqualSomeLength(customsDeclaration.getRelativeId(),
				18)) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"关联报关单号 不等于18位", "提示", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		// 关联手册号=12位；
		if (!RegexUtil.checkEqualSomeLength(
				customsDeclaration.getRelativeManualNo(), 12)) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"关联手册号 不等于12位", "提示", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		// // 备注最前面必须是转自或转至，并判断备注内容中，字符B或C或E或H后面必须11位数字。
		// if (customsDeclaration.getMemos() != null
		// && !customsDeclaration.getMemos().equals("")) {
		// if (!RegexUtil.checkMatch(customsDeclaration.getMemos(),
		// "^转(自|至)(B|E|C|H)\\d{11}$")) {
		// JOptionPane.showMessageDialog(
		// DgBaseSpecialCustomsDeclaration.this,
		// "备注最前面必须是转自或转至，并判断备注内容中，字符B或C或E或H后面必须11位数字。",
		// "提示", JOptionPane.INFORMATION_MESSAGE);
		// return true;
		// }
		// }

		List<BaseCustomsDeclarationCommInfo> list = commInfoModel.getList();
		for (BaseCustomsDeclarationCommInfo info : list) {
			// 总价小数位不能超过2位
			if (!RegexUtil.checkMaxAccuracy(
					info.getCommTotalPrice() == null ? "" : CommonUtils
							.formatDoubleByDigit(info.getCommTotalPrice(), 6),
					2)) {
				JOptionPane.showMessageDialog(
						DgBaseSpecialCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "总价小数位不能超过2位", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			// 申报数量的小数位不能大于4位
			if (!RegexUtil.checkMaxAccuracy(info.getCommAmount() == null ? ""
					: CommonUtils.formatDoubleByDigit(info.getCommAmount(), 6),
					4)) {
				JOptionPane.showMessageDialog(
						DgBaseSpecialCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "申报数量的小数位不能大于4位",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			// 法定数量的小数位不能大于4位
			if (!RegexUtil.checkMaxAccuracy(
					info.getFirstAmount() == null ? "" : CommonUtils
							.formatDoubleByDigit(info.getFirstAmount(), 6), 4)) {
				JOptionPane.showMessageDialog(
						DgBaseSpecialCustomsDeclaration.this,
						info.getSerialNumber() + " 号商品 " + "法定数量的小数位不能大于4位",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}

		return false;
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
			btnUnreel.setPreferredSize(new Dimension(45, 30));
			btnUnreel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (projectType == ProjectType.BCUS) {
						specialCustomsAuthorityAction
								.unreelCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.DZSC) {
						dZSCSpecialCustomsAuthorityAction
								.unreelCustoms(new Request(CommonVars
										.getCurrUser()));
					} else if (projectType == ProjectType.BCS) {
						bCSSpecialCustomsAuthorityAction
								.unreelCustoms(new Request(CommonVars
										.getCurrUser()));
					}
					unreelCustomsDeclaration();
					if (commInfoModel == null) {
						initTable();
					}
					countCommTotalPrice(commInfoModel.getList());
				}
			});
		}
		return btnUnreel;
	}

	FecavAction fecavAction = (FecavAction) CommonVars.getApplicationContext()
			.getBean("fecavAction");

	protected JButton btnEmsNo = null;

	private JComboBox jComboBox = null;

	private JButton jButton9 = null;

	private JButton btnNonCoverPrint = null;

	private JLabel jLabel491 = null;

	private JCalendarComboBox tfCreateDate = null;

	private JMenuItem jMenuItem8 = null;

	private JMenuItem miCoverPrintPrccBonded = null;

	private JMenuItem miBarcodePrint = null;

	private JButton jButton3 = null;

	private JLabel lbInvoiceNo = null;

	private JTextField tfInvoiceCode = null;

	private JButton btnInvoice = null;

	private JLabel jLabel50 = null;

	private JComboBox cbbFeeCurr = null;

	private JComboBox cbbInsurCurr = null;

	private JComboBox cbbOtherCurr = null;

	private JLabel jLabel501 = null;

	private JLabel jLabel502 = null;

	private JLabel jLabel471 = null;

	private JTextField tfConnectNo = null;

	private JLabel jLabel472 = null;

	private JTextField tfConnectDeclarationNo = null;

	private JLabel jLabel33 = null;

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

	private JButton jButton1 = null;

	private JLabel jLabel43 = null;

	private JTextField tfCarNumber = null;

	private JLabel jLabel431 = null;

	private JTextField tfexpAndImpNumber = null;

	protected void unreelCustomsDeclaration() {
		if (this.fecavAction.findFecavBillByCodeCount(
				new Request(CommonVars.getCurrUser()),
				customsDeclaration.getAuthorizeFile()) > 0) {
			JOptionPane.showMessageDialog(this, "外汇核销单中已经填入了出口日期！！ 不能回卷！",
					"提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (this.fecavAction.findStrikeImpCustomsDeclarationCount(new Request(
				CommonVars.getCurrUser()), customsDeclaration.getId()) > 0) {
			JOptionPane.showMessageDialog(this, "外汇核销单中已经填入了白单号码！！ 不能回卷！",
					"提示", JOptionPane.YES_NO_OPTION);
			return;
		}
		if (customsDeclaration.getIsEmsCav() != null
				&& customsDeclaration.getIsEmsCav()) {
			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
					"该单已经数据报核通过了，不能回卷!!!", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(DgBaseSpecialCustomsDeclaration.this,
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
			tfDeclaraCustomsBroker.setBounds(73, 21, 108, 20);
			tfDeclaraCustomsBroker.setEditable(false);
		}
		return tfDeclaraCustomsBroker;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	protected JComboBox getCbbDeclarationCompany() {
		if (cbbDeclarationCompany == null) {
			cbbDeclarationCompany = new JComboBox();
			cbbDeclarationCompany.setBounds(72, 323, 168, 19);
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
			tfBondedWarehouse.setBounds(72, 396, 169, 21);
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
				DgBaseSpecialCustomsDeclaration.this, other)) {
			return false;
		}
		this.customsDeclaration.setImpExpDate(this.cbbImpExpDate.getDate());
		this.customsDeclaration.setDeclarationDate(this.cbbDeclarationDate
				.getDate());

		customsDeclaration = (BaseCustomsDeclaration) baseEncAction
				.saveCustomsDeclaration(new Request(CommonVars.getCurrUser()),
						customsDeclaration);
		if (dataState == DataState.ADD) {
			//
			// 当是新增时保存所有集装箱
			//
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
	 * 新建立一个报关单
	 * 
	 * @return
	 */
	protected abstract BaseCustomsDeclaration newCustomsDeclaration();

	/**
	 * 弹出报关单商品信息编辑窗口
	 * 
	 * @param dataState
	 */
	protected abstract void showCommInfoDialog(int dataState);

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	protected JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel33 = new JLabel();
			jLabel33.setForeground(Color.red);
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton());
			// jToolBar1.add(getJButtonSj());
			jToolBar1.add(jLabel33);
		}
		return jToolBar1;
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	protected JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("排序");
			jButton.setToolTipText("点击后系统自动进行商检排序");
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
					DgSortChecked dg = new DgSortChecked();
					dg.setBaseEncAction(baseEncAction);
					dg.setList(vec);
					String info = dg.setDgVisible(true);
					if (!"".equals(info)) {
						JOptionPane.showMessageDialog(
								DgBaseSpecialCustomsDeclaration.this, info,
								"按商检排序", JOptionPane.ERROR_MESSAGE);
					}
					initTable();

				}
			});
			// hwy 在点击排序时，执行根据商检排序事件和方法2013-8-7
			// jButton.addActionListener(new java.awt.event.ActionListener() {
			// public void actionPerformed(java.awt.event.ActionEvent e) {
			// List list = getDataSource();
			// Vector vec = new Vector();
			// for (int i = 0; i < list.size(); i++) {
			// vec.addElement((BaseCustomsDeclarationCommInfo) list
			// .get(i));
			// }
			// DgSort dg = new DgSort();
			// dg.setBaseEncAction(baseEncAction);
			// dg.setList(vec);
			// dg.setVisible(true);
			// initTable();
			// }
			// });
		}
		return jButton;
	}

	// protected JButton getJButtonSj() {
	// if (jButtonSj == null) {
	// jButtonSj = new JButton();
	// jButtonSj.setText("商检排序");
	// jButtonSj.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent e) {
	// if (projectType == ProjectType.BCUS) {
	// impCustomsAuthorityAction
	// .definedOrder(new Request(CommonVars
	// .getCurrUser()));
	// } else if (projectType == ProjectType.DZSC) {
	// dZSCImpCustomsAuthorityAction
	// .definedOrder(new Request(CommonVars
	// .getCurrUser()));
	// } else if (projectType == ProjectType.BCS) {
	// bCSImpCustomsAuthorityAction
	// .definedOrder(new Request(CommonVars
	// .getCurrUser()));
	// }
	// List list = getDataSource();
	// Vector vec = new Vector();
	// for (int i = 0; i < list.size(); i++) {
	// vec
	// .addElement((BaseCustomsDeclarationCommInfo) list
	// .get(i));
	// }
	// DgSortChecked dg = new DgSortChecked();
	// dg.setBaseEncAction(baseEncAction);
	// dg.setList(vec);
	// String info = dg.setDgVisible(true);
	// if (!"".equals(info)) {
	// JOptionPane.showMessageDialog(
	// DgBaseSpecialCustomsDeclaration.this,
	// info, "按商检排序",
	// JOptionPane.ERROR_MESSAGE);
	// }
	// initTable();
	//
	// }
	// });
	// }
	// return jButtonSj;
	// }
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

						showCommInfoDialog(DataState.EDIT);
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
	protected JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEmsNo() {
		if (btnEmsNo == null) {
			btnEmsNo = new JButton();
			btnEmsNo.setBounds(new Rectangle(319, 20, 22, 21));
			btnEmsNo.setText("...");
			btnEmsNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbImpExpType.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(
								DgBaseSpecialCustomsDeclaration.this,
								"请输入进出口类型", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int type = Integer.parseInt(((ItemProperty) cbbImpExpType
							.getSelectedItem()).getCode());
					if (type == ImpExpType.EQUIPMENT_IMPORT
							|| type == ImpExpType.BACK_PORT_REPAIR
							|| type == ImpExpType.EQUIPMENT_BACK_PORT) {
						// DutyCertHead
						// dutyCertHead=FixAssetQuery.getInstance().findDutyCertHeadExecuting();
						Object obj = FixAssetQuery.getInstance()
								.findDutyCertHeadExecuting();
						if (obj == null) {
							// JOptionPane.showMessageDialog(
							// DgBaseSpecialCustomsDeclaration.this,
							// "你没有选择到数据,请重新选择一条数据或改变进出口类型", "提示",
							// JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (obj.getClass().equals(DutyCertHead.class)) {
							DutyCertHead dutyCertHead = (DutyCertHead) obj;
							if (dutyCertHead != null) {
								tfEmsNo.setText(dutyCertHead.getCertNo());
								customsDeclaration.setFixType(FixType.SHANZHI);
							}
						} else if (obj.getClass().equals(FixtureContract.class)) {
							FixtureContract fixtureContract = (FixtureContract) obj;
							if (fixtureContract != null) {
								getFixtureContractDate(fixtureContract);
								customsDeclaration.setFixType(FixType.LAILIAO);
							}
						}
					} else {
						List list = getEmsHeadList();
						if (list != null && list.size() > 0) {
							BaseEmsHead baseEmsHead = CommonQuery.getInstance()
									.findBaseEmsHeadExecuting(list);
							if (baseEmsHead != null) {
								tfEmsNo.setText(baseEmsHead.getEmsNo());
								if (baseEmsHead instanceof Contract) {
									tfContract.setText(((Contract) baseEmsHead)
											.getImpContractNo());
								} else if (baseEmsHead instanceof DzscEmsPorHead) {
									tfContract
											.setText(((DzscEmsPorHead) baseEmsHead)
													.getImContractNo());
								}
							}
						}
					}
				}
			});
		}
		return btnEmsNo;
	}

	private Boolean isFixEmsNoAndFixType(String emsNo, Integer fixType) {
		if (fixType == null || emsNo == null
				|| (fixType != FixType.SHANZHI && fixType != FixType.LAILIAO))
			return false;
		else {
			boolean boo = materialManageAction.findAllFixByNo(new Request(
					CommonVars.getCurrUser(), true), emsNo, fixType);
			if (!boo) {
				customsDeclaration.setFixType(null);
				customsDeclaration = (BaseCustomsDeclaration) baseEncAction
						.saveCustomsDeclaration(
								new Request(CommonVars.getCurrUser()),
								customsDeclaration);
			}

			return boo;
		}
	}

	/**
	 * 获取来料合同头的资料
	 * 
	 * @param fixtureContract
	 */
	public void getFixtureContractDate(FixtureContract fixtureContract) {

		tfEmsNo.setText(fixtureContract.getEmsNo());
		// 进出口岸
		if (fixtureContract.getDeclareCustoms() != null)
			this.cbbCustoms
					.setSelectedItem(fixtureContract.getDeclareCustoms());
		if (fixtureContract.getBeginDate() != null) {
			cbbImpExpDate.setDate(fixtureContract.getBeginDate());
			cbbDeclarationDate.setDate(fixtureContract.getBeginDate());
		}
		// 初始化贸易方式
		if (fixtureContract.getTrade() != null)
			cbbTradeMode.setSelectedItem(fixtureContract.getTrade());
		// 初始化征免性质
		if (fixtureContract.getLevyKind() != null)
			cbbLevyKind.setSelectedItem(fixtureContract.getLevyKind());
		// 初始化成交方式
		if (fixtureContract.getTransac() != null)
			cbbTransac.setSelectedItem(fixtureContract.getTransac());
		// 初始化币制
		if (fixtureContract.getCurr() != null)
			cbbCurrency.setSelectedItem(fixtureContract.getCurr());
	}

	/**
	 * 初始化窗口上控件的初始值
	 * 
	 */
	protected abstract List getEmsHeadList();

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(46, 3, 69, 25));
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
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setBounds(new Rectangle(633, 126, 23, 18));
			jButton9.setText("...");
			jButton9.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					addBillOfLading();
				}

			});
		}
		return jButton9;
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

	/**
	 * This method initializes btnNonCoverPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNonCoverPrint() {
		if (btnNonCoverPrint == null) {
			btnNonCoverPrint = new JButton();
			btnNonCoverPrint.setText("非套打");
			btnNonCoverPrint.setPreferredSize(new Dimension(48, 30));
			btnNonCoverPrint
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							isTaoda = false;
							Component comp = (Component) e.getSource();
							getJPopupMenu().show(comp, 0, comp.getHeight());
						}
					});
		}
		return btnNonCoverPrint;
	}

	/**
	 * This method initializes tfCreaterDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getTfCreateDate() {
		if (tfCreateDate == null) {
			tfCreateDate = new JCalendarComboBox();
			tfCreateDate.setBounds(new Rectangle(546, 323, 110, 19));
			tfCreateDate.setDate(new Date());
			tfCreateDate.setEnabled(false);
		}
		return tfCreateDate;
	}

	private Map getParaList() {

		List list = new ArrayList();

		Map prop = new HashMap();

		if (customsDeclaration != null) {

			list.add(customsDeclaration);

		} else {

			JOptionPane.showMessageDialog(DgBaseSpecialCustomsDeclaration.this,
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
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.add(getJMenuItem1());
			jPopupMenu.add(getJMenuItem4());
			jPopupMenu.add(getJMenuItem5());
			jPopupMenu.add(getJMenuItem6());
			jPopupMenu.add(getJMenuItem2());
			jPopupMenu.add(getJMenuItem3());
			jPopupMenu.add(getJMenuItem7());
			jPopupMenu.add(getJMenuItem8());

			jPopupMenu.add(getMiCoverPrintPrccBonded());
			jPopupMenu.add(getMiBarcodePrint());
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
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (isImport) {
						CustomsPrinter.getInstance(getParaList()).doInPrint0();
					} else {
						try {
							CustomsPrinter.getInstance(getParaList())
									.doOutPrint0();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(
									DgBaseSpecialCustomsDeclaration.this,
									"请确认是否有浏览系统参数设置的权限！");
						}

					}
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
			jMenuItem1.setText("进口发票");
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (isImport) {
						CustomsPrinter.getInstance(getParaList()).doInPrint2();
					}
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
			jMenuItem2.setText("进口装箱单");
			jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (isImport) {
						CustomsPrinter.getInstance(getParaList()).doInPrint4();
					}
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
			jMenuItem3.setText("出口装箱单1");
			jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (!isImport) {
						CustomsPrinter.getInstance(getParaList()).doOutPrint2();
					}
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
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (!isImport) {
						CustomsPrinter.getInstance(getParaList()).doOutPrint3();
					}
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
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (!isImport) {
						CustomsPrinter.getInstance(getParaList()).doOutPrint5();
					}
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
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (!isImport) {
						CustomsPrinter.getInstance(getParaList()).doOutPrint5();
					}
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
			jMenuItem7.setText("内地海关及香港海关陆路出/进境载货清单+(附表)");
			jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// CustomsPrinter.getInstance(getParaList()).doInPrint6(false);
					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());

					if (isImport) {
						CustomsPrinter.getInstance(getParaList()).doInPrint6(
								false, ImpExpFlag.SPECIAL);
					} else {
						CustomsPrinter.getInstance(getParaList()).doOutPrint7(
								false, ImpExpFlag.SPECIAL);
					}
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
			jMenuItem8.setText("内地海关及香港海关陆路出/进境载货清单(香港)+(附表)");
			jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					boolean isImport = EncCommon.isImport(customsDeclaration
							.getImpExpType().intValue());
					if (isImport) {
						CustomsPrinter.getInstance(getParaList()).doInPrint8(
								true, ImpExpFlag.SPECIAL);
					} else {
						CustomsPrinter.getInstance(getParaList()).doInPrint8(
								true, ImpExpFlag.SPECIAL);
					}
				}
			});
		}
		return jMenuItem8;
	}

	/**
	 * This method initializes miCoverPrintPrccBonded
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintPrccBonded() {
		if (miCoverPrintPrccBonded == null) {
			miCoverPrintPrccBonded = new JMenuItem();
			miCoverPrintPrccBonded.setText("打印中华人民共和国保税区货物备案清单");
			miCoverPrintPrccBonded
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport();
						}
					});
		}
		return miCoverPrintPrccBonded;
	}

	/**
	 * 打印套打海关保税区特殊货物备案清单
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

			String attachedBillName = "";
			attachedBillName = getattachedBillName(customsDeclaration
					.getAttachedBill());
			List list = customsDeclarationModel.getCurrentRows();
			// List list2=baseEncAction.findSpecialCustomsDeclaration(new
			// Request(CommonVars.getCurrUser()));
			BaseCustomsDeclaration bcd = (BaseCustomsDeclaration) list.get(0);
			System.out.println("bcd=" + bcd);
			Integer s = bcd.getImpExpFlag();
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
			Company company = (Company) customsDeclaration.getCompany();
			parameters.put("linkMan", company.getLinkman());
			parameters.put("attachedBillName", attachedBillName);
			InputStream masterReportStream = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQBondedAreaEntrantgoodsFilingBills.jasper");
			InputStream masterReportStream2 = FmBaseImportCustomsDeclaration.class
					.getResourceAsStream("report/PRCCIQExportBondedAreaEntrantgoodsFilingBills.jasper");
			// List<String> tempList = new ArrayList<String>();
			// tempList.add("tempData");
			int impExpType = customsDeclaration.getImpExpType();
			if (ImpExpType.isImportType(impExpType)) {
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters,
						new CustomReportDataSource(list));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} else {
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream2, parameters,
						new CustomReportDataSource(list));
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 条形码打印
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(319, 203, 23, 20));
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addFecavBill();
				}
			});
		}
		return jButton3;
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
	 * This method initializes tfInvoiceCode
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfInvoiceCode() {
		if (tfInvoiceCode == null) {
			tfInvoiceCode = new JTextField();
			tfInvoiceCode.setBounds(new Rectangle(292, 396, 118, 21));
			tfInvoiceCode.setEditable(true);
			tfInvoiceCode.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					if (tfInvoiceCode.getText() != null
							&& !tfInvoiceCode.getText().trim().equals("")
							&& tfInvoiceCode.getText().trim().length() != 8) {
						JOptionPane.showMessageDialog(
								DgBaseSpecialCustomsDeclaration.this,
								"发票号应该为8位,请重新填写！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						tfInvoiceCode.setText("");
					}
				}
			});
		}
		return tfInvoiceCode;
	}

	/**
	 * This method initializes btnInvoice
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInvoice() {
		if (btnInvoice == null) {
			btnInvoice = new JButton();
			btnInvoice.setBounds(new Rectangle(409, 396, 23, 21));
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
	 * This method initializes cbbFeeCurr
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFeeCurr() {
		if (cbbFeeCurr == null) {
			cbbFeeCurr = new JComboBox();
			cbbFeeCurr.setBounds(new Rectangle(73, 227, 108, 20));
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
			cbbInsurCurr.setBounds(new Rectangle(540, 227, 117, 20));
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
			cbbOtherCurr.setBounds(new Rectangle(328, 251, 99, 19));
		}
		return cbbOtherCurr;
	}

	/**
	 * This method initializes tfConnectNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfConnectNo() {
		if (tfConnectNo == null) {
			tfConnectNo = new JTextField();
			tfConnectNo.setBounds(new Rectangle(85, 420, 150, 22));
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
			tfConnectDeclarationNo.setBounds(new Rectangle(316, 419, 114, 22));
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
	 * @return javax.swing.JComboBox
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
	 * @return javax.swing.JComboBox
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
	 * @return javax.swing.JComboBox
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
			tfdomesticConveyanceCode.setBounds(new Rectangle(394, 124, 93, 21));
			tfdomesticConveyanceCode
					.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == 10) {
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

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(638, 298, 20, 19));
			jButton1.setText("...");
			jButton1.addKeyListener(new FocusActionListner(
					getCbbDeclarationCompany()));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
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
		return jButton1;
	}

	/**
	 * This method initializes tfCarNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCarNumber() {
		if (tfCarNumber == null) {
			tfCarNumber = new JTextField();
			tfCarNumber.setBounds(new Rectangle(505, 394, 151, 22));
		}
		return tfCarNumber;
	}

	/**
	 * This method initializes tfexpAndImpNumber
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfexpAndImpNumber() {
		if (tfexpAndImpNumber == null) {
			tfexpAndImpNumber = new JTextField();
			tfexpAndImpNumber.setBounds(new Rectangle(505, 418, 150, 22));
		}
		return tfexpAndImpNumber;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

