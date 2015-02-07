package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.StringUtils;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsCustomsBomDetail;
import com.bestway.bcs.client.dictpor.DgAllChangState;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.TempBankMode;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseModel;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.InitialFocusSetter.MyOwnFocusTraversalPolicy;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.client.common.tableeditor.JNumberForcedTableCellRenderer;
import com.bestway.bcus.client.common.tableeditor.JNumberTableCellEditor;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorEnableListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorListener;
import com.bestway.bcus.client.common.tableeditor.TableCellEditorParameter;
import com.bestway.bcus.custombase.entity.basecode.InvestMode;
import com.bestway.bcus.custombase.entity.basecode.MachiningType;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.custombase.entity.depcode.RedDep;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.FetchInMode;
import com.bestway.bcus.custombase.entity.parametercode.LevyKind;
import com.bestway.bcus.custombase.entity.parametercode.PayMode;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transac;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DeleteResultMark;
import com.bestway.common.constant.EquipMode;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.LimitFlag;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CurrRate;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JCustomFormattedTextField;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.DateValueListener;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 通关手册备案窗体
 * 
 */
@SuppressWarnings("unchecked")
public class DgContract extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrint = null;

	// private JComboBox cbbPrint = null;

	private JButton btnSave = null;

	private JButton btnShowUnitWaste = null;

	private JButton btnUndo = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel pn1 = null;

	private JPanel pn3 = null;

	private JPanel pn2 = null;

	private JLabel jLabel2 = null;

	private JTextField tfDeclareState = null;

	private JLabel jLabel3 = null;

	private JTextField tfCopEmsNo = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JComboBox cbbDeclareCustoms = null;

	private JComboBox cbbEmsType = null;

	private JLabel jLabel6 = null;

	private JTextField tfEmsNo = null;

	private JLabel jLabel7 = null; // @jve:decl-index=0:

	private JComboBox cbbIePort = null;

	private JLabel jLabel8 = null; // @jve:decl-index=0:

	private JLabel jLabel9 = null; // @jve:decl-index=0:

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JComboBox cbbTrade = null;

	private JLabel jLabel12 = null;

	private JTextField tfMachName = null;

	private JLabel jLabel13 = null;

	private JComboBox cbbTradeCountry = null;

	private JLabel jLabel15 = null;

	private JTextField tfMachCode = null;

	private JLabel jLabel16 = null;

	private JTextField tfEnterpriseAddress = null;

	private JLabel jLabel17 = null;

	private JCalendarComboBox cbbDestroyDate = null;

	private JLabel jLabel18 = null;

	private JTextField tfLinkMan = null;

	private JLabel jLabel19 = null;

	private JTextField tfContactTel = null;

	private JLabel jLabel20 = null;

	private JComboBox cbbLevyKind = null;

	private JLabel jLabel21 = null;

	private JTextField tfOutTradeCo = null;

	private JLabel jLabel22 = null;

	private JTextField tfPermitNo = null;

	private JLabel jLabel23 = null;

	private JTextField tfAgreementNo = null;

	private JLabel jLabel24 = null;

	private JTextField tfImpContractNo = null;

	private JLabel jLabel25 = null;

	private JTextField tfExpContractNo = null;

	private JLabel jLabel26 = null;

	private JLabel jLabel27 = null;

	private JLabel jLabel28 = null;

	private JComboBox cbbCurr = null;

	private JLabel jLabel29 = null;

	private JComboBox cbbWardshipRate = null;

	private JLabel jLabel30 = null;

	private JLabel jLabel31 = null;

	private JComboBox cbbTransac = null;

	private JLabel jLabel32 = null;

	private JComboBox cbbIePort2 = null;

	private JLabel jLabel33 = null;

	private JComboBox cbbIePort3 = null;

	private JLabel jLabel34 = null;

	private JComboBox cbbIePort4 = null;

	private JLabel jLabel35 = null;

	private JComboBox cbbIePort5 = null;

	private JLabel jLabel36 = null;

	private JTextField tfApprover = null;

	private JLabel jLabel37 = null;

	private JCalendarComboBox cbbApproveDate = null;

	private JLabel jLabel38 = null;

	private JComboBox cbbPayMode = null;

	private JLabel jLabel39 = null; // @jve:decl-index=0:

	private JComboBox cbbMachiningType = null;

	private JLabel jLabel40 = null;

	private JLabel jLabel41 = null;

	private JTextField tfMemo = null;

	private JFormattedTextField tfImgAmount = null;

	private JFormattedTextField tfExgAmount = null;

	private JFormattedTextField tfWardshipFee = null;

	private JTextField tfEmsApprNo = null;

	private JSplitPane jSplitPane = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAddFromCredenceFinishProduct = null;

	private JPanel jPanel = null;

	private JTable tbExg = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel4 = null;

	private JToolBar jToolBar2 = null;

	private JTable tbBom = null;

	private JLabel jLabel1 = null;

	private JButton btnEditFinishProduct = null;

	private JButton btnDeleteFinishProduct = null;

	private JButton btnChangeFinishProductNo = null;

	private JButton btnFinishProductSort = null;

	private JButton btnEditUnitWaste = null;

	private JButton btnDeleteUnitWaste = null;

	private JButton btnUnitWasteSort = null;

	private JToolBar jToolBar3 = null;

	private JTable tbImg = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnChangingMaterielNo = null;

	private JButton btnMaterielSort = null;

	private JButton btnAmountToInteger = null;

	private JButton btnEditMateriel = null;

	private JLabel jLabel43 = null;

	private ContractAction contractAction = null; // 合同

	private JTableListModel tableModelContract = null; // 表头

	private JTableListModel tableModelImg = null; // 合同料件

	private JTableListModel tableModelExg = null; // 合同成品

	private JTableListModel tableModelBom = null; // 合同单耗

	private Contract contract = null; // 合同对象 // @jve:decl-index=0:

	private int dataState = DataState.BROWSE;

	private JScrollPane jScrollPane3 = null;

	private MaterialManageAction materialManageAction = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private JButton btnAddMateriel = null;

	private JButton btnDeleteMateriel = null;

	private JButton btnEdit = null;

	private SystemAction systemAction = (SystemAction) CommonVars
			.getApplicationContext().getBean("systemAction"); // @jve:decl-index=0:

	private BcsInnerMergeAction bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
			.getApplicationContext().getBean("bcsInnerMergeAction"); // @jve:decl-index=0:

	private JLabel jLabel44 = null;

	private JLabel jLabel45 = null;

	private JLabel jLabel47 = null;

	private JLabel jLabel48 = null;

	private JLabel jLabel49 = null;

	private JLabel jLabel51 = null;

	private JLabel jLabel54 = null;

	private JLabel jLabel55 = null;

	private JLabel jLabel57 = null;

	private JLabel jLabel70 = null;

	private JComboBox cbbReceiveArea = null;

	private JTextField tfHandler = null;

	private JCustomFormattedTextField tfInSaleRate = null;

	private JCalendarComboBox cbbSaveDate = null;

	private JCustomFormattedTextField tfMaterielItemCount = null;

	private JCustomFormattedTextField tfProductItemCount = null;

	private JTextField tfOutLinkManager = null;

	private JTextField tfDictPorEmsNo = null;

	private JComboBox cbbInvestMode = null;

	private JComboBox cbbFetchInMode = null;

	private JButton jButton = null;

	private JButton btnCopyUnitWaste = null;

	private JButton btnCal = null;

	private JLabel lbContractExg = null;

	private JButton btnChangeMaterielNameSpec = null;

	private JButton btnChangeProductNameSpec = null;

	private JButton btnDeleteUnitWasteIsNull = null;

	private JTextField tfTradeCode = null;

	private JLabel jLabel101 = null;

	private JTextField tfTradeName = null;

	private JLabel jLabel58 = null;

	private JComboBox cbbRedDep = null;

	private JLabel jLabel61 = null;

	private JComboBox cbbManageObject = null;

	private BcsParameterSet bcsParameterSet = null; // @jve:decl-index=0:

	private JPanel jPanel2 = null;

	private JRadioButton rbCommonContract = null;

	private JRadioButton rbContractEms = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="35,6"

	private JButton btnSaveBom = null;

	private JButton btnRefreshBom = null;

	private JButton jButton1 = null;

	private JLabel jLabel42 = null;

	private JComboBox cbbLimitFlag = null;

	private JButton btnTradeCode = null;

	private JButton btnBomExport = null;

	private JButton btnChangeDeclareState = null;

	private JButton btnChangeImgModifyMark = null;

	private JButton btnChangeExgModifyMark = null;

	private JButton btnChangeBomModifyMark = null;

	private JPopupMenu pmChangeDeclareState = null; // @jve:decl-index=0:visual-constraint="807,243"

	private JMenuItem miUndoDeclare = null;

	private JPopupMenu pmChangeModifyMark = null; // @jve:decl-index=0:visual-constraint="806,324"

	private JMenuItem miNotModified = null;

	private JMenuItem miModified = null;

	private JMenuItem miDelete = null;

	private JMenuItem miAdd = null;

	private JPopupMenu pmAddUnitWaste = null; // @jve:decl-index=0:visual-constraint="809,404"

	private JMenuItem miAddUnitWasteFromContractImg = null;

	private JMenuItem miAddUnitWasteFromOther = null;

	private JButton btnAddUnitWaste = null;

	private JPopupMenu jPopupMenuPrint = null;

	private JPopupMenu pmenuPrintChang = null;

	private JMenuItem miCoverPrintExportExgNew = null;

	private JMenuItem miNonCoverPrintExportExgNew = null;

	private JMenuItem miCoverPrintImportImgNew = null;

	private JMenuItem miNonCoverPrintImportImgNew = null;

	private JMenuItem miCoverPrintExg = null;

	private JMenuItem miNonCoverPrintExg = null;

	private JMenuItem miCoverPrintImg = null;

	private JMenuItem miNonCoverPrintImg = null;

	private JMenuItem miCoverPrintUnitConsumption = null;

	private JMenuItem miNonCoverPrintUnitConsumption = null;

	private JMenuItem miPrintPutOnRecordContract = null;

	private JMenuItem miPrintPutOnRecordContarct2 = null;

	private JMenuItem miPrintExportExgCharge = null;

	private JMenuItem miPrintImEmImgBalance = null;

	private JMenuItem miProcessingTradeUnitConsumption = null;

	private JMenuItem miPrintCustomClearanceContarct = null;

	private JComboBox cbbEquipMode = null;

	private JLabel jLabel = null;

	private JMenuItem miPrintContractDomesticPurchaseBill = null;

	private JButton btnPrintChang = null;

	private JMenuItem menuItemMiProductChang = null;

	private JMenuItem jMenuItem = null;

	private JMenuItem menuItemMiNonProductChang = null;

	private JMenuItem menuItemMiMaterialChang = null;

	private JMenuItem menuItemMiNonMaterialChang = null;

	private JMenuItem menuItemMiBomChang = null;

	private JMenuItem menuItemMiNonBomChang = null;

	private JMenuItem mecuItemMiHead = null;

	private JMenuItem mecuItemMiNonHead = null;

	private JLabel jLabel421 = null;

	private JComboBox cbbBank = null;

	private JMenuItem miAddUnitWasteFromCustomsBom = null;

	private JPanel pn4 = null;

	private JPanel jPanel1 = null;

	private JLabel lbImpTotal = null;

	private JTextField tfImptotal = null;

	private JLabel lbExpTotal = null;

	private JTextField tfExpTotal = null;

	private JLabel lbProcessTotalPrice = null;

	private JTextField tfProcessTotalPrice = null;

	private JLabel lbCountScale = null;

	private JTextField tfCountScale = null;

	private JLabel lbPortMoney = null;

	private JTextField tfPortMoney = null;

	private JButton tfRecount = null;

	private Double countScale = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel46 = null;

	private JLabel jLabel50 = null;

	private JPanel jPanel3 = null;

	private JRadioButton rbUnitWare = null;

	private JRadioButton rbWare = null;

	private JButton btnRound = null;

	private JTextField tfRound = null;

	// 单耗弹出菜单
	private JPopupMenu copyBOM;

	// 本地合同转抄单耗
	private JMenuItem currContractCopyBOM;

	// 其他合同转抄单耗
	private JMenuItem otherCopyBOM;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public DgContract() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(858, 612);
		this.setContentPane(getJContentPane());
		this.setTitle("通关手册备案");
		this.getButtonGroup();
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			contract = (Contract) this.tableModelContract.getCurrentRow();
			initEmsType();
			showHeadData();
			setState();
		}
		super.setVisible(isFlag);
	}

	/**
	 * @param dataState
	 *            The dataState to set.
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnUndo());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnPrintChang());
			jToolBar.add(getBtnShowUnitWaste());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnBomExport());
			jToolBar.add(getBtnCal());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnChangeDeclareState());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnAddFinishProduct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setPreferredSize(new Dimension(80, 30));
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// printReport();
					// BcsClientHelper.getInstance().printReport(DgContract.this,contract,
					// cbbPrint);
					getJPopupMenuPrint()
							.show(btnPrint, 0, btnPrint.getHeight());
				}

			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	/*
	 * private JComboBox getCbbPrint() { if (cbbPrint == null) { cbbPrint = new
	 * JComboBox(); cbbPrint.setBounds(new Rectangle(4, 1, 148, 27)); } return
	 * cbbPrint; }
	 */

	/**
	 * This method initializes btnAddUnitWaste
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(80, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveHeadData();
				}

			});
		}
		return btnSave;
	}

	private void saveHeadData() {
		if (DeclareState.APPLY_POR.equals(contract.getDeclareState())) {
			if (null != tfCopEmsNo.getText()
					&& tfCopEmsNo.getText().length() > 18) {
				JOptionPane.showMessageDialog(this, "企业内部编号不能大于18位", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		if (contract != null) {
			String emsNo = contract.getEmsNo();
			Contract c = this.contractAction.findContractById(new Request(
					CommonVars.getCurrUser()), contract.getId());
			if (c == null) {
				JOptionPane.showMessageDialog(this,
						"合同 " + emsNo + " 已经删除不能修改", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (c != null && !c.getIsCancel()
					&& c.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
				JOptionPane.showMessageDialog(this,
						"合同 " + emsNo + " 已经生效不能修改", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		fillData();
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		beginDate = CommonUtils.getBeginDate(beginDate);
		endDate = CommonUtils.getEndDate(endDate);
		if (beginDate != null && endDate != null) {
			if (beginDate.after(endDate)) {
				JOptionPane.showMessageDialog(this, "起始日期大于有效日期!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		if (!DeclareState.CHANGING_EXE.equals(contract.getDeclareState())) {
			if (contractAction.isExistContractByEmsNo(
					new Request(CommonVars.getCurrUser()), contract)) {
				JOptionPane.showMessageDialog(DgContract.this, "合同手册编号重复!!!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				// return;
			}
		}
		contract = contractAction.saveContract(
				new Request(CommonVars.getCurrUser()), contract);
		if (dataState == DataState.ADD) {
			tableModelContract.addRow(contract);
			// dataState = DataState.EDIT;
		} else {
			tableModelContract.updateRow(contract);
		}
		dataState = DataState.BROWSE;
		setState();
		// setContainerEnabled(pn1, false);
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowUnitWaste() {
		if (btnShowUnitWaste == null) {
			btnShowUnitWaste = new JButton();
			btnShowUnitWaste.setText("显示单耗表");
			btnShowUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgShowUnitWaste dg = new DgShowUnitWaste();
							dg.setContract(contract);
							dg.setVisible(true);
						}
					});
		}
		return btnShowUnitWaste;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUndo() {
		if (btnUndo == null) {
			btnUndo = new JButton();
			btnUndo.setText("取消");
			btnUndo.setPreferredSize(new Dimension(80, 30));
			btnUndo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showHeadData();
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnUndo;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(80, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// BcsClientHelper.getInstance().printChangedContractImgReport(
					// contract);
					DgContract.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("基本情况", null, getPn1(), null);
			jTabbedPane.addTab("料件总表", null, getPn3(), null);
			jTabbedPane.addTab("成品及单耗表", null, getPn2(), null);
			jTabbedPane.addTab("其他", null, getPn4(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane tab = (JTabbedPane) e.getSource();
							showData(tab);
						}
					});
		}
		return jTabbedPane;
	}

	private void showData(JTabbedPane tab) {
		List list = new ArrayList();
		if (tab.getSelectedIndex() == 0) {
			if (contract != null) {
				showHeadData();
				setState();
			}
		} else if (tab.getSelectedIndex() == 1) {
			if (dataState != DataState.BROWSE) {
				saveHeadData();
			}
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractImgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
			}
			initImgTable(list);
		} else if (tab.getSelectedIndex() == 2) {
			if (dataState != DataState.BROWSE) {
				saveHeadData();
			}
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
			}
			initExgTable(list);
			if (list.size() <= 0) {
				initBomTable(list);
			}
			setState();
		}
		// 新增BY 石小凯 7月19日
		else if (tab.getSelectedIndex() == 3) {
			contract = this.contractAction.findContractById(new Request(
					CommonVars.getCurrUser()), contract.getId());
			System.out.println("contract:" + contract.getDeclareState());
			// if (DeclareState.APPLY_POR.equals(contract.getDeclareState())||
			// DeclareState.CHANGING_EXE.equals(contract.getDeclareState())) {
			// btnSave.setEnabled(true);
			// }
			tfImptotal.setEditable(false);
			tfExpTotal.setEditable(false);
			tfProcessTotalPrice.setEditable(false);
			tfPortMoney.setEditable(false);
			// 进口总值
			tfImptotal
					.setText(format(contract.getImgAmount() == null ? new Double(
							0) : contract.getImgAmount()));
			// 出口总值
			tfExpTotal
					.setText(format(contract.getExgAmount() == null ? new Double(
							0) : contract.getExgAmount()));
			// 加工费总价
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractExgByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
			}
			List<ContractExg> contractExg = list;
			double ProcessTotalPrice = 0.0;
			if (null != contractExg && contractExg.size() > 0) {
				for (ContractExg c : contractExg) {
					if (null != c.getProcessTotalPrice())
						ProcessTotalPrice += c.getProcessTotalPrice();
				}
			}
			tfProcessTotalPrice.setText(String.valueOf(ProcessTotalPrice));
			// 计算比例
			// 判断实体中计算比例是否为空,
			// 1.不为空的话初始值使用数据库里的,
			// 2.为空的话初始值由合同性质做判断初始化
			System.out
					.println("contract.getEmsType()=" + contract.getEmsType());
			System.out.println("contract.getCountScale()="
					+ contract.getCountScale());
			if (null == contract.getCountScale()
					|| contract.getCountScale().equals("")) {
				if (null == cbbEmsType.getSelectedItem()
						|| cbbEmsType.getSelectedItem().equals("")) {
					tfCountScale.setText("0.0");
				} else {
					String s = cbbEmsType.getSelectedItem() + "";
					if (s.trim().equals("来料加工")
							|| s.trim().equals("纸质手册电子化来料手册")) {
						tfCountScale.setText("0.002");
					} else if (s.trim().equals("进料加工")
							|| s.trim().equals("纸质手册电子化进料手册")) {
						tfCountScale.setText("0.0005");
					} else {
						tfCountScale.setText("0.0");
					}
				}
			} else {
				tfCountScale.setText(format((double) contract.getCountScale()));
			}

			// 获取合同性质
			String getEmsType = null;
			if (null != contract.getEmsType()
					&& !contract.getEmsType().equals("")) {
				getEmsType = ContractKind.getContractKindSpec(contract
						.getEmsType());
			}
			String emsType = cbbEmsType.getSelectedItem() + "";

			// 当切换分页容器时查看合同性质是否更改，如更改过后则计算比例为，
			// 如更改过后则计算比例为初始值。
			if (!emsType.trim().equals(getEmsType)) {
				if (null == cbbEmsType.getSelectedItem()
						|| cbbEmsType.getSelectedItem().equals("")) {
					tfCountScale.setText("0.0");
				} else {
					String s = cbbEmsType.getSelectedItem() + "";
					if (s.trim().equals("来料加工")
							|| s.trim().equals("纸质手册电子化来料手册")) {
						tfCountScale.setText("0.002");
					} else if (s.trim().equals("进料加工")
							|| s.trim().equals("纸质手册电子化进料手册")) {
						tfCountScale.setText("0.0005");
					} else {
						tfCountScale.setText("0.0");
					}
				}
			}
		}
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			jLabel421 = new JLabel();
			jLabel421.setBounds(new Rectangle(244, 444, 74, 20));
			jLabel421.setText("台帐银行");
			jLabel421.setHorizontalTextPosition(SwingConstants.RIGHT);
			jLabel421.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel421.setForeground(Color.blue);
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(244, 419, 74, 21));
			jLabel.setForeground(Color.blue);
			jLabel.setText("单耗申报环节");
			jLabel42 = new JLabel();
			jLabel42.setBounds(new Rectangle(42, 442, 62, 20));
			jLabel42.setForeground(Color.blue);
			jLabel42.setText("限制类标志");
			jLabel61 = new JLabel();
			jLabel61.setBounds(new Rectangle(458, 18, 77, 20));
			jLabel61.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel61.setText("管理对象");
			jLabel61.setForeground(Color.blue);
			jLabel58 = new JLabel();
			jLabel58.setBounds(new Rectangle(461, 41, 74, 20));
			jLabel58.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel58.setText("主管外经部门");
			jLabel58.setForeground(Color.blue);
			jLabel101 = new JLabel();
			jLabel101.setBounds(new Rectangle(244, 86, 74, 22));
			jLabel101.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel101.setText("经营单位名称");
			jLabel101.setForeground(new Color(51, 51, 51));
			jLabel57 = new JLabel();
			jLabel57.setBounds(new Rectangle(473, 374, 61, 20));
			jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel57.setText("成品项数");
			jLabel70 = new JLabel();
			jLabel70.setBounds(new Rectangle(227, 199, 91, 20));
			jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel70.setText("外商公司经理人");
			jLabel55 = new JLabel();
			jLabel55.setBounds(new Rectangle(257, 374, 61, 20));
			jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel55.setText("原料项数");
			jLabel54 = new JLabel();
			jLabel54.setBounds(new Rectangle(474, 351, 61, 20));
			jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel54.setText("输入时间");
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(232, 398, 86, 20));
			jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel51.setText("备案资料库编号");
			jLabel49 = new JLabel();
			jLabel49.setBounds(new Rectangle(257, 351, 61, 20));
			jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel49.setText("内销比%");
			jLabel48 = new JLabel();
			jLabel48.setBounds(new Rectangle(42, 397, 61, 20));
			jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel48.setText("引进方式");
			jLabel47 = new JLabel();
			jLabel47.setBounds(new Rectangle(42, 419, 61, 20));
			jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel47.setText("投资方式");
			jLabel45 = new JLabel();
			jLabel45.setBounds(new Rectangle(42, 374, 61, 20));
			jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel45.setText("经办人");
			jLabel44 = new JLabel();
			jLabel44.setBounds(new Rectangle(42, 351, 61, 20));
			jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel44.setForeground(Color.blue);
			jLabel44.setText("收货地区");
			jLabel41 = new JLabel();
			jLabel40 = new JLabel();
			jLabel39 = new JLabel();
			jLabel38 = new JLabel();
			jLabel37 = new JLabel();
			jLabel36 = new JLabel();
			jLabel35 = new JLabel();
			jLabel34 = new JLabel();
			jLabel33 = new JLabel();
			jLabel32 = new JLabel();
			jLabel31 = new JLabel();
			jLabel30 = new JLabel();
			jLabel29 = new JLabel();
			jLabel28 = new JLabel();
			jLabel27 = new JLabel();
			jLabel26 = new JLabel();
			jLabel25 = new JLabel();
			jLabel24 = new JLabel();
			jLabel23 = new JLabel();
			jLabel22 = new JLabel();
			jLabel21 = new JLabel();
			jLabel20 = new JLabel();
			jLabel19 = new JLabel();
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			jLabel7 = new JLabel();
			jLabel6 = new JLabel();
			jLabel5 = new JLabel();
			jLabel4 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			pn1 = new JPanel();
			pn1.setLayout(null);
			jLabel2.setBounds(51, 64, 52, 20);
			jLabel2.setText("合同状态");
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel3.setBounds(30, 19, 73, 20);
			jLabel3.setText("企业内部编号");
			jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel4.setBounds(43, 41, 60, 20);
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("主管海关");
			jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel5.setBounds(266, 41, 52, 20);
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setText("合同性质");
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setBounds(266, 19, 52, 20);
			jLabel6.setText("手册编号");
			jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel7.setBounds(266, 64, 52, 20);
			jLabel7.setText("进出口岸");
			jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel8.setBounds(483, 132, 52, 20);
			jLabel8.setForeground(java.awt.Color.blue);
			jLabel8.setText("起始日期");
			jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel9.setBounds(483, 177, 52, 20);
			jLabel9.setForeground(java.awt.Color.blue);
			jLabel9.setText("有效日期");
			jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setBounds(22, 86, 81, 20);
			jLabel10.setText("经营单位编码");
			jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel10.setForeground(new Color(51, 51, 51));
			jLabel11.setBounds(51, 132, 52, 20);
			jLabel11.setText("贸易方式");
			jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel11.setForeground(java.awt.Color.blue);
			jLabel12.setBounds(241, 110, 77, 20);
			jLabel12.setText("收货单位名称");
			jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel12.setForeground(new Color(51, 51, 51));
			jLabel13.setBounds(255, 132, 63, 20);
			jLabel13.setForeground(new Color(51, 51, 51));
			jLabel13.setText("起抵地");
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
			jLabel15.setBounds(23, 110, 80, 20);
			jLabel15.setForeground(new Color(51, 51, 51));
			jLabel15.setText("收货单位编码");
			jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel16.setBounds(51, 154, 52, 20);
			jLabel16.setText("企业地址");
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel17.setBounds(483, 154, 52, 20);
			jLabel17.setText("核销到期");
			jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel18.setBounds(63, 177, 40, 20);
			jLabel18.setForeground(new Color(51, 51, 51));
			jLabel18.setText("联系人");
			jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel19.setBounds(266, 177, 52, 20);
			jLabel19.setForeground(new Color(51, 51, 51));
			jLabel19.setText("联系电话");
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel20.setBounds(483, 64, 52, 20);
			jLabel20.setForeground(java.awt.Color.blue);
			jLabel20.setText("征免性质");
			jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel21.setBounds(42, 199, 61, 20);
			jLabel21.setForeground(java.awt.Color.blue);
			jLabel21.setText("外商公司");
			jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel22.setBounds(483, 199, 52, 20);
			jLabel22.setText("许可证号");
			jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel23.setBounds(43, 221, 60, 20);
			jLabel23.setText("协议书号");
			jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel24.setBounds(250, 221, 68, 20);
			jLabel24.setForeground(java.awt.Color.blue);
			jLabel24.setText("进口合同号");
			jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel25.setBounds(461, 221, 74, 20);
			jLabel25.setText("出口合同号");
			jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel26.setBounds(48, 242, 55, 20);
			jLabel26.setForeground(java.awt.Color.blue);
			jLabel26.setText("进口总值");
			jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel27.setBounds(266, 242, 52, 20);
			jLabel27.setText("出口总值");
			jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel28.setBounds(483, 242, 52, 20);
			jLabel28.setForeground(java.awt.Color.blue);
			jLabel28.setText("币制");
			jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel29.setBounds(40, 263, 63, 20);
			jLabel29.setText("监管费率");
			jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel30.setBounds(266, 263, 52, 20);
			jLabel30.setText("监管费");
			jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel31.setBounds(483, 263, 52, 20);
			jLabel31.setText("成交方式");
			jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel32.setBounds(42, 286, 61, 20);
			jLabel32.setText("进出口岸2");
			jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel33.setBounds(255, 286, 63, 20);
			jLabel33.setText("进出口岸3");
			jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel34.setBounds(473, 286, 62, 20);
			jLabel34.setText("进出口岸4");
			jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel35.setBounds(42, 307, 61, 20);
			jLabel35.setText("进出口岸5");
			jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel36.setBounds(266, 307, 52, 20);
			jLabel36.setText("审批人");
			jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel37.setBounds(483, 307, 52, 20);
			jLabel37.setText("审批日期");
			jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel38.setBounds(42, 329, 61, 20);
			jLabel38.setText("保税方式");
			jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel39.setBounds(266, 329, 52, 20);
			jLabel39.setForeground(Color.blue);
			jLabel39.setText("加工种类");
			jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel40.setBounds(474, 329, 61, 20);
			jLabel40.setForeground(Color.blue);
			jLabel40.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel40.setText("批准文号");
			jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel41.setBounds(474, 397, 61, 20);
			jLabel41.setText("备注");
			jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			pn1.add(jLabel2, null);
			pn1.add(getTfDeclareState(), null);
			pn1.add(jLabel3, null);
			pn1.add(getTfCopEmsNo(), null);
			pn1.add(jLabel4, null);
			pn1.add(jLabel5, null);
			pn1.add(getCbbDeclareCustoms(), null);
			pn1.add(getCbbEmsType(), null);
			pn1.add(jLabel6, null);
			pn1.add(getTfEmsNo(), null);
			pn1.add(jLabel7, null);
			pn1.add(getCbbIePort(), null);
			pn1.add(jLabel8, null);
			pn1.add(jLabel9, null);
			pn1.add(getCbbBeginDate(), null);
			pn1.add(getCbbEndDate(), null);
			pn1.add(jLabel10, null);
			pn1.add(jLabel11, null);
			pn1.add(getCbbTrade(), null);
			pn1.add(jLabel12, null);
			pn1.add(getTfMachName(), null);
			pn1.add(jLabel13, null);
			pn1.add(getCbbTradeCountry(), null);
			pn1.add(jLabel15, null);
			pn1.add(getTfMachCode(), null);
			pn1.add(jLabel16, null);
			pn1.add(getTfEnterpriseAddress(), null);
			pn1.add(jLabel17, null);
			pn1.add(getJCalendarComboBox3(), null);
			pn1.add(jLabel18, null);
			pn1.add(getTfLinkMan(), null);
			pn1.add(jLabel19, null);
			pn1.add(getJTextField7(), null);
			pn1.add(jLabel20, null);
			pn1.add(getCbbLevyKind(), null);
			pn1.add(jLabel21, null);
			pn1.add(getTfOutTradeCo(), null);
			pn1.add(jLabel22, null);
			pn1.add(getTfPermitNo(), null);
			pn1.add(jLabel23, null);
			pn1.add(getTfAgreementNo(), null);
			pn1.add(jLabel24, null);
			pn1.add(getTfImpContractNo(), null);
			pn1.add(jLabel25, null);
			pn1.add(getTfExpContractNo(), null);
			pn1.add(jLabel26, null);
			pn1.add(jLabel27, null);
			pn1.add(jLabel28, null);
			pn1.add(getCbbCurr(), null);
			pn1.add(jLabel29, null);
			pn1.add(getCbbWardshipRate(), null);
			pn1.add(jLabel30, null);
			pn1.add(jLabel31, null);
			pn1.add(getCbbTransac(), null);
			pn1.add(jLabel32, null);
			pn1.add(getCbbIePort2(), null);
			pn1.add(jLabel33, null);
			pn1.add(getCbbIePort3(), null);
			pn1.add(jLabel34, null);
			pn1.add(getCbbIePort4(), null);
			pn1.add(jLabel35, null);
			pn1.add(getCbbIePort5(), null);
			pn1.add(jLabel36, null);
			pn1.add(getTfApprover(), null);
			pn1.add(jLabel37, null);
			pn1.add(getCbbApproveDate(), null);
			pn1.add(jLabel38, null);
			pn1.add(getCbbPayMode(), null);
			pn1.add(jLabel39, null);
			pn1.add(getCbbMachiningType(), null);
			pn1.add(jLabel40, null);
			pn1.add(jLabel41, null);
			pn1.add(getTfMemo(), null);
			pn1.add(getTfImgAmount(), null);
			pn1.add(getTfExgAmount(), null);
			pn1.add(getTfWardshipFee(), null);
			pn1.add(getTfEmsApprNo(), null);
			pn1.add(jLabel44, null);
			pn1.add(jLabel45, null);
			pn1.add(jLabel47, null);
			pn1.add(jLabel48, null);
			pn1.add(jLabel49, null);
			pn1.add(jLabel51, null);
			pn1.add(jLabel54, null);
			pn1.add(jLabel55, null);
			pn1.add(jLabel57, null);
			pn1.add(jLabel70, null);
			pn1.add(getCbbReceiveArea(), null);
			pn1.add(getTfHandler(), null);
			pn1.add(getTfInSaleRate(), null);
			pn1.add(getCbbSaveDate(), null);
			pn1.add(getTfMaterielItemCount(), null);
			pn1.add(getTfProductItemCount(), null);
			pn1.add(getTfOutLinkManager(), null);
			pn1.add(getTfDictPorEmsNo(), null);
			pn1.add(getCbbInvestMode(), null);
			pn1.add(getCbbFetchInMode(), null);
			pn1.add(getTfTradeCode(), null);
			pn1.add(jLabel101, null);
			pn1.add(getTfTradeName(), null);
			pn1.add(jLabel58, null);
			pn1.add(getCbbRedDep(), null);
			pn1.add(jLabel61, null);
			pn1.add(getCbbManageObject(), null);
			pn1.add(getJPanel2(), null);
			pn1.add(jLabel42, null);
			pn1.add(getCbbLimitFlag(), null);
			pn1.add(getBtnTradeCode(), null);
			pn1.add(getCbbEquipMode(), null);
			pn1.add(jLabel, null);
			pn1.add(jLabel421, null);
			pn1.add(getCbbBank(), null);
		}
		return pn1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn3() {
		if (pn3 == null) {
			pn3 = new JPanel();
			pn3.setLayout(new BorderLayout());
			pn3.add(getJToolBar3(), java.awt.BorderLayout.NORTH);
			pn3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return pn3;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(new BorderLayout());
			pn2.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return pn2;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDeclareState() {
		if (tfDeclareState == null) {
			tfDeclareState = new JTextField();
			tfDeclareState.setEditable(false);
			tfDeclareState.setBounds(106, 64, 127, 20);
		}
		return tfDeclareState;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopEmsNo() {
		if (tfCopEmsNo == null) {
			tfCopEmsNo = new JTextField();
			tfCopEmsNo.setBounds(106, 18, 127, 20);
			// tfCopEmsNo.setDocument(new ConveyanceDocument());
		}
		return tfCopEmsNo;
	}

	// class ConveyanceDocument extends PlainDocument {
	// public void insertString(int offs, String str, AttributeSet a)
	// throws BadLocationException {
	// if (str == null) {
	// return;
	// }
	// if (super.getLength() >= 18 || str.getBytes().length > 18
	// || (super.getLength() + str.getBytes().length) > 18) {
	// return;
	// }
	// super.insertString(offs, str, a);
	// }
	// }

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbDeclareCustoms() {
		if (cbbDeclareCustoms == null) {
			cbbDeclareCustoms = new JComboBox();
			cbbDeclareCustoms.setBounds(106, 41, 127, 20);
		}
		return cbbDeclareCustoms;
	}

	/**
	 * This method initializes jComboBox3
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEmsType() {
		if (cbbEmsType == null) {
			cbbEmsType = new JComboBox();
			cbbEmsType.setBounds(320, 41, 127, 20);

		}
		return cbbEmsType;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsNo() {
		if (tfEmsNo == null) {
			tfEmsNo = new JTextField();
			tfEmsNo.setBounds(320, 18, 127, 20);
			tfEmsNo.setDocument(new PlainDocument() {
				public void insertString(int offs, String str, AttributeSet a)
						throws BadLocationException {
					if (str == null) {
						return;
					}
					if (super.getLength() >= 12 || str.getBytes().length > 12
							|| (super.getLength() + str.getBytes().length) > 12) {
						return;
					}
					super.insertString(offs, str, a);
				}
			});
		}
		return tfEmsNo;
	}

	/**
	 * This method initializes jComboBox4
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort() {
		if (cbbIePort == null) {
			cbbIePort = new JComboBox();
			cbbIePort.setBounds(320, 64, 127, 20);
		}
		return cbbIePort;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(540, 132, 127, 20);
			cbbBeginDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					if (dataState == DataState.BROWSE) {
						return;
					}
					if (contract != null
							&& contract.getIsImportFromQP() != null
							&& !contract.getIsImportFromQP()) {
						return;
					}
					Calendar beginDate = cbbBeginDate.getCalendar();
					Calendar endDate = null;
					Calendar destroyDate = null;
					if (beginDate != null) {
						endDate = (Calendar) beginDate.clone();
						endDate.add(Calendar.MONTH, 12);
						destroyDate = (Calendar) endDate.clone();
						destroyDate.add(Calendar.DAY_OF_MONTH, 30);
					}
					cbbEndDate.setCalendar(endDate);
					cbbDestroyDate.setCalendar(destroyDate);

				}

			});
			// cbbBeginDate.addChangeListener(new ChangeListener() {
			//
			// public void stateChanged(ChangeEvent arg0) {
			// Calendar beginDate = cbbBeginDate.getCalendar();
			// Calendar endDate = null;
			// Calendar destroyDate = null;
			// if (beginDate != null) {
			// endDate = (Calendar) beginDate.clone();
			// endDate.add(Calendar.MONTH, 12);
			// destroyDate = (Calendar) endDate.clone();
			// destroyDate.add(Calendar.DAY_OF_MONTH, 30);
			// }
			// cbbEndDate.setCalendar(endDate);
			// cbbDestroyDate.setCalendar(destroyDate);
			// }
			//
			// });
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
			cbbEndDate.setBounds(540, 177, 127, 20);
			cbbEndDate.addDateValueListener(new DateValueListener() {

				public void valueChanged(Date newDate) {
					Calendar endDate = cbbEndDate.getCalendar();
					;
					Calendar destroyDate = null;
					if (endDate != null) {
						destroyDate = (Calendar) endDate.clone();
						destroyDate.add(Calendar.DAY_OF_MONTH, 30);
					}
					cbbDestroyDate.setCalendar(destroyDate);

				}

			});
			// cbbEndDate.addChangeListener(new
			// javax.swing.event.ChangeListener() {
			// public void stateChanged(javax.swing.event.ChangeEvent e) {
			// Calendar endDate = cbbEndDate.getCalendar();;
			// Calendar destroyDate = null;
			// if (endDate != null) {
			// destroyDate = (Calendar) endDate.clone();
			// destroyDate.add(Calendar.DAY_OF_MONTH, 30);
			// }
			// cbbDestroyDate.setCalendar(destroyDate);
			// }
			// });
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jComboBox5
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTrade() {
		if (cbbTrade == null) {
			cbbTrade = new JComboBox();
			cbbTrade.setBounds(106, 132, 127, 20);
		}
		return cbbTrade;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMachName() {
		if (tfMachName == null) {
			tfMachName = new JTextField();
			tfMachName.setEditable(false);
			tfMachName.setBounds(320, 110, 347, 20);
		}
		return tfMachName;
	}

	/**
	 * This method initializes jComboBox7
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTradeCountry() {
		if (cbbTradeCountry == null) {
			cbbTradeCountry = new JComboBox();
			cbbTradeCountry.setBounds(320, 132, 127, 20);
		}
		return cbbTradeCountry;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMachCode() {
		if (tfMachCode == null) {
			tfMachCode = new JTextField();
			tfMachCode.setEditable(false);
			tfMachCode.setBounds(106, 110, 127, 20);
		}
		return tfMachCode;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEnterpriseAddress() {
		if (tfEnterpriseAddress == null) {
			tfEnterpriseAddress = new JTextField();
			tfEnterpriseAddress.setBounds(106, 154, 341, 20);
		}
		return tfEnterpriseAddress;
	}

	/**
	 * This method initializes jCalendarComboBox3
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox3() {
		if (cbbDestroyDate == null) {
			cbbDestroyDate = new JCalendarComboBox();
			cbbDestroyDate.setBounds(540, 154, 127, 20);
		}
		return cbbDestroyDate;
	}

	/**
	 * This method initializes jTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfLinkMan() {
		if (tfLinkMan == null) {
			tfLinkMan = new JTextField();
			tfLinkMan.setBounds(106, 177, 127, 20);
		}
		return tfLinkMan;
	}

	/**
	 * This method initializes jTextField7
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField7() {
		if (tfContactTel == null) {
			tfContactTel = new JTextField();
			tfContactTel.setBounds(320, 177, 127, 20);
		}
		return tfContactTel;
	}

	/**
	 * This method initializes jComboBox8
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLevyKind() {
		if (cbbLevyKind == null) {
			cbbLevyKind = new JComboBox();
			cbbLevyKind.setBounds(540, 64, 127, 20);
		}
		return cbbLevyKind;
	}

	/**
	 * This method initializes jTextField8
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfOutTradeCo() {
		if (tfOutTradeCo == null) {
			tfOutTradeCo = new JTextField();
			tfOutTradeCo.setBounds(106, 199, 127, 20);
		}
		return tfOutTradeCo;
	}

	/**
	 * This method initializes jTextField9
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPermitNo() {
		if (tfPermitNo == null) {
			tfPermitNo = new JTextField();
			tfPermitNo.setBounds(540, 199, 127, 20);
		}
		return tfPermitNo;
	}

	/**
	 * This method initializes jTextField10
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfAgreementNo() {
		if (tfAgreementNo == null) {
			tfAgreementNo = new JTextField();
			tfAgreementNo.setBounds(106, 221, 127, 20);
		}
		return tfAgreementNo;
	}

	/**
	 * This method initializes jTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImpContractNo() {
		if (tfImpContractNo == null) {
			tfImpContractNo = new JTextField();
			tfImpContractNo.setBounds(320, 221, 127, 20);
			tfImpContractNo.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

						public void removeUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

						public void changedUpdate(DocumentEvent e) {
							tfExpContractNo.setText(tfImpContractNo.getText());
						}

					});
		}
		return tfImpContractNo;
	}

	/**
	 * This method initializes jTextField12
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpContractNo() {
		if (tfExpContractNo == null) {
			tfExpContractNo = new JTextField();
			tfExpContractNo.setBounds(540, 221, 127, 20);
		}
		return tfExpContractNo;
	}

	/**
	 * This method initializes jComboBox9
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCurr() {
		if (cbbCurr == null) {
			cbbCurr = new JComboBox();
			cbbCurr.setBounds(540, 242, 127, 20);
		}
		return cbbCurr;
	}

	/**
	 * This method initializes jComboBox10
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWardshipRate() {
		if (cbbWardshipRate == null) {
			cbbWardshipRate = new JComboBox();
			cbbWardshipRate.setBounds(106, 263, 127, 20);
			cbbWardshipRate.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						setWardshipFee();
					}
				}
			});
		}
		return cbbWardshipRate;
	}

	/**
	 * This method initializes jComboBox11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbTransac() {
		if (cbbTransac == null) {
			cbbTransac = new JComboBox();
			cbbTransac.setBounds(540, 263, 127, 20);
		}
		return cbbTransac;
	}

	/**
	 * This method initializes jComboBox12
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort2() {
		if (cbbIePort2 == null) {
			cbbIePort2 = new JComboBox();
			cbbIePort2.setBounds(106, 286, 127, 20);
		}
		return cbbIePort2;
	}

	/**
	 * This method initializes jComboBox13
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort3() {
		if (cbbIePort3 == null) {
			cbbIePort3 = new JComboBox();
			cbbIePort3.setBounds(320, 286, 127, 20);
		}
		return cbbIePort3;
	}

	/**
	 * This method initializes jComboBox14
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort4() {
		if (cbbIePort4 == null) {
			cbbIePort4 = new JComboBox();
			cbbIePort4.setBounds(540, 286, 127, 20);
		}
		return cbbIePort4;
	}

	/**
	 * This method initializes jComboBox15
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIePort5() {
		if (cbbIePort5 == null) {
			cbbIePort5 = new JComboBox();
			cbbIePort5.setBounds(106, 307, 127, 20);
		}
		return cbbIePort5;
	}

	/**
	 * This method initializes jTextField16
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfApprover() {
		if (tfApprover == null) {
			tfApprover = new JTextField();
			tfApprover.setBounds(320, 307, 127, 20);
		}
		return tfApprover;
	}

	/**
	 * This method initializes jCalendarComboBox4
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbApproveDate() {
		if (cbbApproveDate == null) {
			cbbApproveDate = new JCalendarComboBox();
			cbbApproveDate.setBounds(540, 307, 127, 20);
		}
		return cbbApproveDate;
	}

	/**
	 * This method initializes jComboBox16
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPayMode() {
		if (cbbPayMode == null) {
			cbbPayMode = new JComboBox();
			cbbPayMode.setBounds(106, 329, 127, 20);
		}
		return cbbPayMode;
	}

	/**
	 * This method initializes jComboBox17
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMachiningType() {
		if (cbbMachiningType == null) {
			cbbMachiningType = new JComboBox();
			cbbMachiningType.setBounds(320, 329, 127, 20);
		}
		return cbbMachiningType;
	}

	/**
	 * This method initializes jTextField17
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMemo() {
		if (tfMemo == null) {
			tfMemo = new JTextField();
			tfMemo.setBounds(540, 397, 127, 20);
			tfMemo.setEditable(false);
		}
		return tfMemo;
	}

	/**
	 * This method initializes jFormattedTextField
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfImgAmount() {
		if (tfImgAmount == null) {
			tfImgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfImgAmount.setBounds(106, 242, 127, 20);
			tfImgAmount.setEditable(false);
			tfImgAmount.setValue(new Double(0));
			tfImgAmount.setToolTipText("国内购买不计算在内");
			tfImgAmount.getDocument().addDocumentListener(
					new DocumentListener() {

						public void insertUpdate(DocumentEvent e) {
							setWardshipFee();
						}

						public void removeUpdate(DocumentEvent e) {
							setWardshipFee();

						}

						public void changedUpdate(DocumentEvent e) {
							setWardshipFee();

						}

					});
		}
		return tfImgAmount;
	}

	/**
	 * This method initializes jFormattedTextField1
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfExgAmount() {
		if (tfExgAmount == null) {
			tfExgAmount = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfExgAmount.setBounds(320, 242, 127, 20);
			tfExgAmount.setEditable(false);
			tfExgAmount.setValue(new Double(0));
		}
		return tfExgAmount;
	}

	/**
	 * This method initializes jFormattedTextField2
	 * 
	 * @return javax.swing.JFormattedTextField
	 */
	private JFormattedTextField getTfWardshipFee() {
		if (tfWardshipFee == null) {
			tfWardshipFee = new com.bestway.ui.winuicontrol.JCustomFormattedTextField();
			tfWardshipFee.setBounds(320, 263, 127, 20);
			tfWardshipFee.setValue(new Double(0));
		}
		return tfWardshipFee;
	}

	/**
	 * This method initializes jTextField13
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfEmsApprNo() {
		if (tfEmsApprNo == null) {
			tfEmsApprNo = new JTextField();
			tfEmsApprNo.setBounds(540, 329, 127, 20);
		}
		return tfEmsApprNo;
	}

	/**
	 * @return Returns the tableModelContract.
	 */
	public JTableListModel getTableModelContract() {
		return tableModelContract;
	}

	/**
	 * @param tableModelContract
	 *            The tableModelContract to set.
	 */
	public void setTableModelContract(JTableListModel tableModelHead) {
		this.tableModelContract = tableModelHead;
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
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel4());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel1 = new JLabel();
			jToolBar1 = new JToolBar();
			jLabel1.setText("      成品及单耗表                                                 ");
			jLabel1.setForeground(java.awt.Color.blue);
			jToolBar1.add(getBtnAddFromCredenceFinishProduct());
			jToolBar1.add(getBtnChangeProductNameSpec());
			jToolBar1.add(getBtnEditFinishProduct());
			jToolBar1.add(getBtnDeleteFinishProduct());
			jToolBar1.add(getBtnChangeFinishProductNo());
			jToolBar1.add(getBtnFinishProductSort());
			jToolBar1.add(getBtnChangeExgModifyMark());
			jToolBar1.add(jLabel1);
		}
		return jToolBar1;
	}

	/**
	 * This method initializes btnAddFinishProduct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddFromCredenceFinishProduct() {
		if (btnAddFromCredenceFinishProduct == null) {
			btnAddFromCredenceFinishProduct = new JButton();
			btnAddFromCredenceFinishProduct.setText("新增成品");
			btnAddFromCredenceFinishProduct.setPreferredSize(new Dimension(65,
					30));
			btnAddFromCredenceFinishProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (contract == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请先保存合同记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = new ArrayList();
							List lsResult = new ArrayList();
							// boolean isContractEms = isContractEms();
							boolean isContractEms = true;
							if (isContractEms) {
								contract = contractAction.findContractById(
										new Request(CommonVars.getCurrUser()),
										contract.getId());
								String dictPorEmsNo = contract.getCorrEmsNo();
								if (dictPorEmsNo == null
										|| "".equals(dictPorEmsNo.trim())) {
									List lsExingBcsDictPor = bcsDictPorAction
											.findExingBcsDictPorHead(new Request(
													CommonVars.getCurrUser(),
													true));
									if (lsExingBcsDictPor.size() <= 0) {
										JOptionPane
												.showMessageDialog(
														DgContract.this,
														"备案资料库料件没有成品",
														"提示",
														JOptionPane.INFORMATION_MESSAGE);
										return;
									} else if (lsExingBcsDictPor.size() == 1) {
										dictPorEmsNo = ((BcsDictPorHead) lsExingBcsDictPor
												.get(0)).getDictPorEmsNo();
									} else {
										BcsDictPorHead bcsDictPorHead = BcsClientHelper
												.getInstance()
												.findExingBcsDictPorHead(
														lsExingBcsDictPor);
										if (bcsDictPorHead == null) {
											return;
										}
										dictPorEmsNo = bcsDictPorHead
												.getDictPorEmsNo();
									}
								}
								boolean isFilt = true;
								if (JOptionPane
										.showConfirmDialog(
												DgContract.this,
												"是否根据手册成品过滤查询出来的备案资料库成品\r\n如果选\"是\"是过滤，选\"否\"不过滤",
												"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									isFilt = false;
								}
								list = BcsClientHelper.getInstance()
										.findBcsDictPorImgExgNotContract(
												MaterielType.FINISHED_PRODUCT,
												contract.getId(), dictPorEmsNo,
												isFilt);
								if (list == null || list.size() <= 0) {
									return;
								}
								if (contract.getCorrEmsNo() == null
										|| "".equals(contract.getCorrEmsNo()
												.trim())) {
									contract.setCorrEmsNo(dictPorEmsNo);
									contract = contractAction.saveContract(
											new Request(CommonVars
													.getCurrUser()), contract);
								}
								lsResult = contractAction
										.saveContractExgFromDictPorExg(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
							} else {
								if (CommonVars.getContractFrom() != null) {
									if (CommonVars.getContractFrom()
											.equals("2")) {
										list = BcsClientHelper.getInstance()
												.getComplex();
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveComtractExgByComplex(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
									} else if (CommonVars.getContractFrom()
											.equals("1")) {
										list = BcsClientHelper
												.getInstance()
												.findBcsInnerMergeNotContract(
														contract,
														MaterielType.FINISHED_PRODUCT,
														true);
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveContractExg(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
									} else if (CommonVars.getContractFrom()
											.equals("0")) {
										list = BcsClientHelper
												.getInstance()
												.findBcsInnerMergeNotContract(
														contract,
														MaterielType.FINISHED_PRODUCT,
														false);
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveContractExg(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
									}
								}
							}
							tableModelExg.addRows(lsResult);
							DgContractFinishProduct dg = new DgContractFinishProduct();
							dg.setTableModel(tableModelExg);
							dg.setContract(contract);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnAddFromCredenceFinishProduct;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
			tbExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbExg
									.getModel();
							if (tableModel == null) {
								lbContractExg.setText("");
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								List list = new ArrayList();
								if (tableModel.getCurrentRow() != null) {
									ContractExg exg = ((ContractExg) tableModel
											.getCurrentRow());
									// lbContractExg.setText("当前单耗的成品序号："
									// + exg.getSeqNum() + " " + "名称："
									// + exg.getName());
									String parentId = exg.getId();
									list = contractAction
											.findContractBomByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
								}
								initBomTable(list);
							}
						}
					});
		}
		return tbExg;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbExg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel4.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			// lbContractExg = new JLabel();
			// lbContractExg.setText("");
			// lbContractExg.setForeground(java.awt.Color.blue);
			// FlowLayout fl = new FlowLayout();
			// fl.setAlignment(FlowLayout.LEFT);
			// fl.setVgap(1);
			// fl.setHgap(1);
			jToolBar2 = new JToolBar();
			jToolBar2.setPreferredSize(new Dimension(352, 34));
			// jToolBar2.setFloatable(true);
			jToolBar2.setFloatable(false);
			// jToolBar2.setLayout(fl);
			jToolBar2.add(getBtnAddUnitWaste());
			jToolBar2.add(getBtnDeleteUnitWasteIsNull());
			jToolBar2.add(getBtnEditUnitWaste());
			jToolBar2.add(getBtnDeleteUnitWaste());
			jToolBar2.add(getBtnUnitWasteSort());
			jToolBar2.add(getBtnCopyUnitWaste());
			// jToolBar2.add(jLabel42);
			// jToolBar2.add(lbContractExg);
			jToolBar2.add(getBtnSaveBom());
			jToolBar2.add(getJButton22());
			jToolBar2.add(getBtnChangeBomModifyMark());
			// 焦点顺序控制
			jToolBar2.add(getJPanel3());
			Component components[] = { btnAddUnitWaste,
					miAddUnitWasteFromContractImg, miAddUnitWasteFromOther,
					miAddUnitWasteFromCustomsBom, btnDeleteUnitWasteIsNull,
					btnEditUnitWaste, btnDeleteUnitWaste, btnCopyUnitWaste,
					btnSaveBom, btnRefreshBom, btnChangeBomModifyMark };
			this.setFocusTraversalPolicy(new MyOwnFocusTraversalPolicy(
					components));
		}

		return jToolBar2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBom() {
		if (tbBom == null) {
			tbBom = new JTable();
			InputMap inputMap = tbBom
					.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
			KeyStroke entry = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

			KeyStroke sTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 1);
			KeyStroke sEntry = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 1);

			Object tabOperObject = inputMap.get(tab);
			inputMap.remove(entry);
			inputMap.put(entry, tabOperObject);

			Object sTabOperObject = inputMap.get(sTab);
			inputMap.remove(sEntry);
			inputMap.put(sEntry, sTabOperObject);
		}
		return tbBom;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditFinishProduct() {
		if (btnEditFinishProduct == null) {
			btnEditFinishProduct = new JButton();
			btnEditFinishProduct.setText("修改成品");
			btnEditFinishProduct.setPreferredSize(new Dimension(65, 30));
			btnEditFinishProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExg == null
									|| tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要修改的合同成品记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgContractFinishProduct dg = new DgContractFinishProduct();
							dg.setTableModel(tableModelExg);
							dg.setContract(contract);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnEditFinishProduct;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteFinishProduct() {
		if (btnDeleteFinishProduct == null) {
			btnDeleteFinishProduct = new JButton();
			btnDeleteFinishProduct.setText("删除成品");
			btnDeleteFinishProduct.setPreferredSize(new Dimension(65, 30));
			btnDeleteFinishProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExg == null
									|| tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要删除的合同成品记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(DgContract.this,
									"是否确定删除合同成品记录!!!", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List deleteList = tableModelExg.getCurrentRows();
							Map<Integer, List<ContractExg>> map = contractAction.deleteContractExg(
									new Request(CommonVars.getCurrUser()),
									deleteList);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelExg.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelExg.updateRows(lsUpdate);
							}
							showBomData();
						}
					});
		}
		return btnDeleteFinishProduct;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeFinishProductNo() {
		if (btnChangeFinishProductNo == null) {
			btnChangeFinishProductNo = new JButton();
			btnChangeFinishProductNo.setText("变更序号");
			btnChangeFinishProductNo.setPreferredSize(new Dimension(65, 30));
			btnChangeFinishProductNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExg == null
									|| tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要变更的成品记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ContractExg info = (ContractExg) tableModelExg
									.getCurrentRow();
							Contract base = (Contract) info.getContract();
							if (base.getIsContractEms() != null
									&& base.getIsContractEms()) {
								if (!ModifyMarkState.ADDED
										.equals(((ContractExg) tableModelExg
												.getCurrentRow())
												.getModifyMark())) {
									JOptionPane.showMessageDialog(
											DgContract.this, "该成品已经备案，不能变更序号！",
											"提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							}
							int beginSeqNum = contractAction
									.findMaxContractExgSeqNumExceptAdd(
											new Request(CommonVars
													.getCurrUser()), contract) + 1;
							DgChangeExgSeqNum dg = new DgChangeExgSeqNum();
							dg.setTableModel(tableModelExg);
							dg.setModifyMark(((ContractExg) tableModelExg
									.getCurrentRow()).getModifyMark());
							dg.setContract(contract);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								if (contract != null) {
									String parentId = contract.getId();
									List list = contractAction
											.findContractExgByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initExgTable(list);
								}
							}
						}
					});
		}
		return btnChangeFinishProductNo;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFinishProductSort() {
		if (btnFinishProductSort == null) {
			btnFinishProductSort = new JButton();
			btnFinishProductSort.setText("成品排序");
			btnFinishProductSort.setPreferredSize(new Dimension(65, 30));
			btnFinishProductSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExg == null
									|| tableModelExg.getList().size() <= 0) {
								return;
							}
							List list = tableModelExg.getList();
							if (list.size() <= 0) {
								return;
							}
							// if
							// (JOptionPane.showConfirmDialog(DgContract.this,
							// "是否将成品排序!!!", "提示",
							// JOptionPane.YES_NO_OPTION) ==
							// JOptionPane.YES_OPTION) {
							// list = contractAction
							// .saveContractImgAmountInteger(
							// new Request(CommonVars
							// .getCurrUser()), list);
							Vector vet = new Vector();
							list = contractAction.findContractExgByModifyMark(
									new Request(CommonVars.getCurrUser()),
									contract, ModifyMarkState.ADDED);
							int beginSeqNum = contractAction
									.findMaxContractExgSeqNumExceptAdd(
											new Request(CommonVars
													.getCurrUser()), contract) + 1;
							int size = list.size();
							for (int i = 0; i < size; i++) {
								vet.add(list.get(i));
							}
							DgContractExgSort dg = new DgContractExgSort();
							dg.setList(vet);
							dg.setContractAction(contractAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								list = dg.getList();
								tableModelExg.updateRows(list);
							}
							if (contract != null) {
								String parentId = contract.getId();
								list = contractAction
										.findContractExgByParentId(new Request(
												CommonVars.getCurrUser()),
												parentId);
								initExgTable(list);
							}
							// }

						}
					});
		}
		return btnFinishProductSort;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditUnitWaste() {
		if (btnEditUnitWaste == null) {
			btnEditUnitWaste = new JButton();
			btnEditUnitWaste.setText("修改单耗");
			btnEditUnitWaste.setPreferredSize(new Dimension(65, 30));
			btnEditUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelBom == null
									|| tableModelBom.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要修改的单耗表记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgUnitWaste dg = new DgUnitWaste();
							dg.setTableModel(tableModelBom);
							dg.setTableModelExg(tableModelExg);
							dg.setContract(contract);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnEditUnitWaste;
	}

	/**
	 * This method initializes jButton9
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteUnitWaste() {
		if (btnDeleteUnitWaste == null) {
			btnDeleteUnitWaste = new JButton();
			btnDeleteUnitWaste.setText("删除");
			btnDeleteUnitWaste.setPreferredSize(new Dimension(65, 30));
			btnDeleteUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelBom == null
									|| tableModelBom.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要删除的单耗记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(DgContract.this,
									"是否确定删除这些单耗!!!", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List deleteList = tableModelBom.getCurrentRows();
							Map<Integer, List<ContractBom>> map = contractAction.deleteContractBom(
									new Request(CommonVars.getCurrUser()),
									deleteList);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelBom.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelBom.updateRows(lsUpdate);
							}
							if (deleteList.size() > 0) {
								ContractExg contractExg = ((ContractBom) deleteList
										.get(0)).getContractExg();
								if (contractExg != null) {
									contractExg = (ContractExg) contractAction
											.load(ContractExg.class,
													contractExg.getId());
									if (contractExg != null) {
										tableModelExg.updateRow(contractExg);
									}
								}
							}
						}
					});
		}
		return btnDeleteUnitWaste;
	}

	protected void deleteAllBom() {
		List deleteList = tableModelBom.getList();
		Map<Integer, List<ContractBom>> map = contractAction.deleteContractBom(
				new Request(CommonVars.getCurrUser()), deleteList);
		List lsDelete = map.get(DeleteResultMark.DELETE);
		if (lsDelete != null && lsDelete.size() > 0) {
			tableModelBom.deleteRows(lsDelete);
		}
		List lsUpdate = map.get(DeleteResultMark.UPDATE);
		if (lsUpdate != null && lsUpdate.size() > 0) {
			tableModelBom.updateRows(lsUpdate);
		}
		if (deleteList.size() > 0) {
			ContractExg contractExg = ((ContractBom) deleteList.get(0))
					.getContractExg();
			if (contractExg != null) {
				contractExg = (ContractExg) contractAction.load(
						ContractExg.class, contractExg.getId());
				if (contractExg != null) {
					tableModelExg.updateRow(contractExg);
				}
			}
		}

	}

	/**
	 * This method initializes jButton10
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUnitWasteSort() {
		if (btnUnitWasteSort == null) {
			btnUnitWasteSort = new JButton();
			btnUnitWasteSort.setText("单耗排序");
			btnUnitWasteSort.setVisible(false);
			btnUnitWasteSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelBom == null
									|| tableModelBom.getList().size() <= 0) {
								return;
							}
							List list = tableModelBom.getList();
							if (list.size() <= 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(DgContract.this,
									"是否将单耗排序!!!", "提示",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								// list = contractAction
								// .saveContractImgAmountInteger(
								// new Request(CommonVars
								// .getCurrUser()), list);
								Vector vet = new Vector();
								int size = tableModelBom.getSize();
								for (int i = 0; i < size; i++) {
									vet.add(tableModelBom.getDataByRow(i));
								}
								DgContractBomSort dg = new DgContractBomSort();
								dg.setList(vet);
								dg.setContractAction(contractAction);
								dg.setVisible(true);
								if (dg.isOk()) {
									list = dg.getList();
									tableModelBom.updateRows(list);
								}
								if (tableModelExg.getCurrentRow() != null) {
									ContractExg exg = ((ContractExg) tableModelExg
											.getCurrentRow());
									// lbContractExg.setText("当前单耗的成品序号："
									// + exg.getSeqNum() + " " + "名称："
									// + exg.getName());
									String parentId = exg.getId();
									list = contractAction
											.findContractBomByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initBomTable(list);
								}
							}
						}
					});
		}
		return btnUnitWasteSort;
	}

	/**
	 * This method initializes jToolBar3
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar3() {
		if (jToolBar3 == null) {
			jLabel43 = new JLabel();
			jToolBar3 = new JToolBar();
			jLabel43.setText("                 料件总表          ");
			jLabel43.setForeground(new java.awt.Color(227, 145, 0));
			jToolBar3.add(getBtnAddMateriel());
			jToolBar3.add(getBtnChangeMaterielNameSpec());
			jToolBar3.add(getBtnEditMateriel());
			jToolBar3.add(getBtnRefreshBom());
			jToolBar3.add(getBtnChangingMaterielNo());
			jToolBar3.add(getBtnMaterielSort());
			jToolBar3.add(getBtnAmountToInteger());
			jToolBar3.add(getBtnChangeImgModifyMark());
			jToolBar3.add(jLabel43);
		}
		return jToolBar3;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
			tbImg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbImg
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {

							}
						}
					});
		}
		return tbImg;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbImg());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jButton12
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangingMaterielNo() {
		if (btnChangingMaterielNo == null) {
			btnChangingMaterielNo = new JButton();
			btnChangingMaterielNo.setText("变更序号");
			btnChangingMaterielNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImg == null
									|| tableModelImg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要变更的料件记录!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ContractImg info = (ContractImg) tableModelImg
									.getCurrentRow();
							Contract base = (Contract) info.getContract();
							if (base.getIsContractEms() != null
									&& base.getIsContractEms()) {
								if (!ModifyMarkState.ADDED
										.equals(((ContractImg) tableModelImg
												.getCurrentRow())
												.getModifyMark())) {
									JOptionPane.showMessageDialog(
											DgContract.this, "该料件已经备案，不能变更序号！",
											"提示",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							}
							int beginSeqNum = contractAction
									.findMaxContractImgSeqNumExceptAdd(
											new Request(CommonVars
													.getCurrUser()), contract) + 1;
							DgChangeImgSeqNum dg = new DgChangeImgSeqNum();
							dg.setTableModel(tableModelImg);
							dg.setModifyMark(((ContractImg) tableModelImg
									.getCurrentRow()).getModifyMark());
							dg.setContract(contract);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								if (contract != null) {
									String parentId = contract.getId();
									List list = contractAction
											.findContractImgByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initImgTable(list);
								}
							}
						}
					});
		}
		return btnChangingMaterielNo;
	}

	/**
	 * This method initializes jButton13
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMaterielSort() {
		if (btnMaterielSort == null) {
			btnMaterielSort = new JButton();
			btnMaterielSort.setText("料件排序");
			btnMaterielSort
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImg == null
									|| tableModelImg.getList().size() <= 0) {
								return;
							}
							List list = tableModelImg.getList();
							if (list.size() <= 0) {
								return;
							}
							// if
							// (JOptionPane.showConfirmDialog(DgContract.this,
							// "是否将料件排序!!!", "提示",
							// JOptionPane.YES_NO_OPTION) ==
							// JOptionPane.YES_OPTION) {
							// list = contractAction
							// .saveContractImgAmountInteger(
							// new Request(CommonVars
							// .getCurrUser()), list);
							Vector vet = new Vector();
							list = contractAction.findContractImgByModifyMark(
									new Request(CommonVars.getCurrUser()),
									contract, ModifyMarkState.ADDED);
							int beginSeqNum = contractAction
									.findMaxContractImgSeqNumExceptAdd(
											new Request(CommonVars
													.getCurrUser()), contract) + 1;
							int size = list.size();
							for (int i = 0; i < size; i++) {
								vet.add(list.get(i));
							}
							DgContractImgSort dg = new DgContractImgSort();
							dg.setList(vet);
							dg.setContractAction(contractAction);
							dg.setBeginSeqNum(beginSeqNum);
							dg.setVisible(true);
							if (dg.isOk()) {
								// list = dg.getList();
								// tableModelImg.updateRows(list);
								if (contract != null) {
									String parentId = contract.getId();
									list = contractAction
											.findContractImgByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
									initImgTable(list);
								}
							}
						}
						// }
					});
		}
		return btnMaterielSort;
	}

	/**
	 * This method initializes jButton14
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAmountToInteger() {
		if (btnAmountToInteger == null) {
			btnAmountToInteger = new JButton();
			btnAmountToInteger.setText("数量取整");
			btnAmountToInteger
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImg == null
									|| tableModelImg.getList().size() <= 0) {
								return;
							}
							List list = tableModelImg.getList();
							if (list.size() <= 0) {
								return;
							}
							if (JOptionPane.showConfirmDialog(DgContract.this,
									"是否将所有记录的数量取整!!!", "提示",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								list = contractAction
										.saveContractImgAmountInteger(
												new Request(CommonVars
														.getCurrUser()), list);
								tableModelImg.updateRows(list);
							}
						}
					});
		}

		return btnAmountToInteger;
	}

	/**
	 * This method initializes jButton15
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditMateriel() {
		if (btnEditMateriel == null) {
			btnEditMateriel = new JButton();
			btnEditMateriel.setText("修改料件");
			btnEditMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImg == null
									|| tableModelImg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要修改的料件表记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgContractMateriel dg = new DgContractMateriel();
							dg.setTableModel(tableModelImg);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					});
		}
		return btnEditMateriel;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbBom());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(80, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
					// setContainerEnabled(pn1, true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 初始化单耗表
	 */
	private void initBomTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 60));
				list.add(addColumn("料件总表序号", "contractImgSeqNum", 80,
						Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 100));
				list.add(addColumn("规格型号", "spec", 100));
				list.add(addColumn("净耗", "unitWaste", 80));
				list.add(addColumn("损耗率%", "waste", 100));
				list.add(addColumn("单项用量", "unitDosage", 100));
				list.add(addColumn("非保税料件比例%", "nonMnlRatio", 100));
				list.add(addColumn("单价", "declarePrice", 100));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("单位", "unit.name", 100));
				list.add(addColumn("金额", "totalPrice", 100));
				list.add(addColumn("主料/辅料", "isMainImg", 100));

				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("备案序号", "imgCredenceNo", 100));
				list.add(addColumn("备注", "note", 100));
				return list;
			}
		};
		JTableListModel.dataBind(tbBom, list, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		tableModelBom = (JTableListModel) tbBom.getModel();
		tbBom.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		tbBom.getColumnModel().getColumn(14)
				.setCellRenderer(new TableCheckBoxRender());
		List<JTableListColumn> coms = tableModelBom.getColumns();
		coms.get(8).setFractionCount(9);
		jTableListModelAdapter.setEditableColumn(6);
		jTableListModelAdapter.setEditableColumn(7);
		jTableListModelAdapter.setEditableColumn(9);
		tableModelBom.setAllowSetValue(true);
		JNumberTableCellEditor unitWasteTableCellEditor = new JNumberTableCellEditor(
				9);
		unitWasteTableCellEditor
				.addAutoCalcListener(new TableCellEditorListener() {
					public void run(TableCellEditorParameter param) {
						commitValueChange((ContractBom) param.getEditingData());
					}
				});
		unitWasteTableCellEditor
				.setEnableListener(new TableCellEditorEnableListener() {
					public boolean isCanEdit(TableCellEditorParameter param) {
						ContractBom detail = (ContractBom) param
								.getEditingData();
						if (detail == null) {
							return false;
						}
						String declareState = detail.getContractExg()
								.getContract().getDeclareState();
						if (DeclareState.APPLY_POR.equals(declareState)
								|| DeclareState.CHANGING_EXE
										.equals(declareState)) {
							return true;
						} else {
							return false;
						}
					}
				});
		JNumberTableCellEditor wasteTableCellEditor = new JNumberTableCellEditor(
				5);
		wasteTableCellEditor.addAutoCalcListener(new TableCellEditorListener() {
			public void run(TableCellEditorParameter param) {
				commitValueChange((ContractBom) param.getEditingData());
			}
		});
		wasteTableCellEditor
				.setEnableListener(new TableCellEditorEnableListener() {
					public boolean isCanEdit(TableCellEditorParameter param) {
						ContractBom detail = (ContractBom) param
								.getEditingData();
						if (detail == null) {
							return false;
						}
						String declareState = detail.getContractExg()
								.getContract().getDeclareState();
						if (DeclareState.APPLY_POR.equals(declareState)
								|| DeclareState.CHANGING_EXE
										.equals(declareState)) {
							return true;
						} else {
							return false;
						}
					}
				});

		JNumberTableCellEditor nonRateTableCellEditor = new JNumberTableCellEditor(
				5);
		nonRateTableCellEditor
				.setEnableListener(new TableCellEditorEnableListener() {
					public boolean isCanEdit(TableCellEditorParameter param) {
						ContractBom detail = (ContractBom) param
								.getEditingData();
						if (detail == null) {
							return false;
						}
						String declareState = detail.getContractExg()
								.getContract().getDeclareState();
						if (DeclareState.APPLY_POR.equals(declareState)
								|| DeclareState.CHANGING_EXE
										.equals(declareState)) {
							return true;
						} else {
							return false;
						}
					}
				});

		tbBom.getColumnModel().getColumn(6)
				.setCellEditor(unitWasteTableCellEditor);
		tbBom.getColumnModel().getColumn(6)
				.setCellRenderer(new JNumberForcedTableCellRenderer());
		tbBom.getColumnModel().getColumn(7).setCellEditor(wasteTableCellEditor);
		tbBom.getColumnModel().getColumn(7)
				.setCellRenderer(new JNumberForcedTableCellRenderer());

		tbBom.getColumnModel().getColumn(9)
				.setCellEditor(nonRateTableCellEditor);
		tbBom.getColumnModel().getColumn(9)
				.setCellRenderer(new JNumberForcedTableCellRenderer());
	}

	private static void commitValueChange(ContractBom detail) {
		if (detail == null) {
			return;
		}
		double unitWaste = (detail.getUnitWaste() == null ? 0.0 : detail
				.getUnitWaste());
		double waste = (detail.getWaste() == null ? 0.0 : detail.getWaste());
		if (waste >= 100 || waste < 0) {
			detail.setWaste(0.0);
			waste = 0.0;
		}
		double unitUsed = CommonVars.getDoubleByDigit(unitWaste
				/ (1 - waste / 100.0), 9);
		detail.setUnitDosage(unitUsed);
		double exgAmount = CommonVars.formatDouble(detail.getContractExg()
				.getExportAmount());
		double imgAmount = CommonVars.getDoubleByDigit(unitUsed * exgAmount, 5);
		detail.setAmount(imgAmount);
		detail.setTotalPrice(CommonVars.getDoubleByDigit(
				CommonVars.formatDouble(detail.getDeclarePrice()) * imgAmount,
				5));
	}

	/**
	 * 初始化料件总表
	 * 
	 */
	private void initImgTable(List list) {
		JTableListModel.dataBind(tbImg, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 100));
				list.add(addColumn("料件序号", "seqNum", 60, Integer.class));
				list.add(addColumn("记录号", "credenceNo", 60, Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 150));
				list.add(addColumn("规格型号", "spec", 100));
				list.add(addColumn("计量单位", "unit.name", 80));
				list.add(addColumn("单价", "declarePrice", 100));
				list.add(addColumn("数量", "amount", 100));
				// list.add(addColumn("损耗数量", "wasteAmount", 100));
				list.add(addColumn("总金额", "totalPrice", 100));
				list.add(addColumn("主料/辅料", "isMainImg", 100));
				// list.add(addColumn("法定单位总量", "legalTotalAmount", 100));
				// list.add(addColumn("法定单位数量", "legalAmount", 100));
				list.add(addColumn("法定单位", "complex.firstUnit.name", 100));
				// list.add(addColumn("征税比例", "dutyRate", 100));
				list.add(addColumn("单位重量", "unitWeight", 100));
				list.add(addColumn("总重量", "totalWeight", 100));
				list.add(addColumn("原产地", "country.name", 100));
				list.add(addColumn("征免方式", "levyMode.name", 100));
				// list.add(addColumn("废料是否报关", "isApplyToCustoms", 100));

				list.add(addColumn("备注", "note", 100));
				return list;
			}
		});
		tableModelImg = (JTableListModel) tbImg.getModel();
		tbImg.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		tbImg.getColumnModel().getColumn(11)
				.setCellRenderer(new TableCheckBoxRender());
	}

	/**
	 * 初始化成品表
	 * 
	 */
	private void initExgTable(List list) {
		JTableListModel.dataBind(tbExg, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("修改标志", "modifyMark", 100));
				list.add(addColumn("成品序号", "seqNum", 60, Integer.class));
				list.add(addColumn("记录号", "credenceNo", 60, Integer.class));
				list.add(addColumn("商品编码", "complex.code", 80));
				list.add(addColumn("商品名称", "name", 150));
				list.add(addColumn("规格型号", "spec", 100));
				list.add(addColumn("出口数量", "exportAmount", 100));
				list.add(addColumn("单位", "unit.name", 80));
				list.add(addColumn("单价", "unitPrice", 100));
				list.add(addColumn("总额", "totalPrice", 100));
				// list.add(addColumn("法定数量", "legalAmount", 100));
				list.add(addColumn("法定单位", "complex.firstUnit.name", 100));
				list.add(addColumn("原料费", "materialFee", 100));
				list.add(addColumn("消费国", "country.name", 100));
				list.add(addColumn("加工费单价", "processUnitPrice", 100));
				list.add(addColumn("加工费总价", "processTotalPrice", 100));
				list.add(addColumn("单位毛重", "unitGrossWeight", 100));
				list.add(addColumn("单位净重", "unitNetWeight", 100));
				list.add(addColumn("征减免税方式", "levyMode.name", 100));
				list.add(addColumn("申报状态", "dutyRateString", 70));
				list.add(addColumn("备注", "note", 100));
				return list;
			}
		});
		tableModelExg = (JTableListModel) tbExg.getModel();

		tbExg.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});

	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
		// 申报关区号
		this.cbbDeclareCustoms.setModel(CustomBaseModel.getInstance()
				.getCustomModel());
		this.initComboBoxRenderer(cbbDeclareCustoms);
		// 进出口岸
		this.cbbIePort.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort);
		// 初始经营单位systemAction
		// List companyList = systemAction.findCompanies();
		this.cbbRedDep.setModel(CustomBaseModel.getInstance().getRedDepModel());
		this.initComboBoxRenderer(cbbRedDep);
		// 进出口岸2
		this.cbbIePort2
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort2);
		// 进出口岸3
		this.cbbIePort3
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort3);
		// 进出口岸4
		this.cbbIePort4
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort4);
		// 进出口岸5
		this.cbbIePort5
				.setModel(CustomBaseModel.getInstance().getCustomModel());
		this.initComboBoxRenderer(cbbIePort5);
		// 贸易方式
		this.cbbTrade.setModel(CustomBaseModel.getInstance().getTradeModel());
		this.initComboBoxRenderer(cbbTrade);
		// 贸易国别
		this.cbbTradeCountry.setModel(CustomBaseModel.getInstance()
				.getCountryModel());
		this.initComboBoxRenderer(cbbTradeCountry);
		// 征面性质
		this.cbbLevyKind.setModel(CustomBaseModel.getInstance()
				.getLevyKindModel());
		this.initComboBoxRenderer(cbbLevyKind);
		// 保税方式
		this.cbbPayMode.setModel(CustomBaseModel.getInstance()
				.getPayModeModel());
		this.initComboBoxRenderer(cbbPayMode);
		// 加工种类
		this.cbbMachiningType.setModel(CustomBaseModel.getInstance()
				.getMachiningTypeModel());
		this.initComboBoxRenderer(cbbMachiningType);
		// 初始化币制
		this.cbbCurr.setModel(CustomBaseModel.getInstance().getCurrModel());
		this.initComboBoxRenderer(cbbCurr);
		// 成交方式
		this.cbbTransac.setModel(CustomBaseModel.getInstance()
				.getTransacModel());
		this.initComboBoxRenderer(cbbTransac);

		// 接收地区
		this.cbbReceiveArea.setModel(CustomBaseModel.getInstance()
				.getDistrictModelModel());
		this.initComboBoxRenderer(cbbReceiveArea);
		// 投资方式
		this.cbbInvestMode.setModel(CustomBaseModel.getInstance()
				.getInvestModeModel());
		this.initComboBoxRenderer(cbbInvestMode);
		// 引进方式
		this.cbbFetchInMode.setModel(CustomBaseModel.getInstance()
				.getFetchInModel());
		this.initComboBoxRenderer(cbbFetchInMode);

		// 合同性质
		cbbEmsType.setRenderer(CustomBaseRender.getInstance()
				.getRender(50, 150));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEmsType);
		this.cbbEmsType.setSelectedItem(null);
		this.cbbEmsType.setUI(new CustomBaseComboBoxUI(200));
		// 监管费用率
		cbbWardshipRate.removeAllItems();
		cbbWardshipRate.addItem("0.001");
		cbbWardshipRate.addItem("0.0015");
		cbbWardshipRate.addItem("0.003");
		this.initComboBoxRenderer(cbbWardshipRate);
		// 打印
		// cbbPrint.removeAllItems();
		// cbbPrint.addItem(" 套打出口成品表(新)");
		// cbbPrint.addItem(" 非套打出口成品表(新)");
		// cbbPrint.addItem(" 套打进口料件表(新)");
		// cbbPrint.addItem(" 非套打进口料件表(新)");
		// cbbPrint.addItem(" 套打成品表");
		// cbbPrint.addItem(" 非套打成品表");
		// cbbPrint.addItem(" 套打料件表");
		// cbbPrint.addItem(" 非套打料件表");
		// cbbPrint.addItem(" 套打单耗表");
		// cbbPrint.addItem(" 非套打单耗表");
		// cbbPrint.addItem(" 打印加工合同备案情况表");
		// cbbPrint.addItem(" 打印预申报合同组成表");
		// cbbPrint.addItem(" 出口成品加工费表");
		// cbbPrint.addItem(" 料件进出口平衡检查表");
		// cbbPrint.addItem(" 加工贸易单耗申报表");
		// cbbPrint.addItem(" 打印通关手册表");

		// 初始化管理对象
		this.cbbManageObject.removeAllItems();
		this.cbbManageObject.addItem(new ItemProperty(ManageObject.MANAGE_COP,
				"经营单位"));
		this.cbbManageObject.addItem(new ItemProperty(
				ManageObject.MANUFACTURE_COP, "加工单位"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbManageObject);
		this.cbbManageObject.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbManageObject.setSelectedIndex(-1);
		// 限制类标志
		cbbLimitFlag.removeAllItems();
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_BEFORE_EMS,
				"调整前旧手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ADJUST_AFTER_EMS,
				"调整后新手册"));
		cbbLimitFlag.addItem(new ItemProperty(LimitFlag.ACOUNT_BOOK_USE,
				"台账专用手册"));
		cbbLimitFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbLimitFlag);

		// 单耗申报环节
		cbbEquipMode.removeAllItems();
		cbbEquipMode.addItem(new ItemProperty(EquipMode.PUT_ON_RECORD,
				EquipMode.getEquipModeDesc(EquipMode.PUT_ON_RECORD)));
		cbbEquipMode.addItem(new ItemProperty(EquipMode.BEFORE_EXPORT,
				EquipMode.getEquipModeDesc(EquipMode.BEFORE_EXPORT)));
		cbbEquipMode.addItem(new ItemProperty(EquipMode.BEFORE_CANCEL,
				EquipMode.getEquipModeDesc(EquipMode.BEFORE_CANCEL)));
		cbbEquipMode.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbEquipMode);

		cbbBank.removeAllItems();
		cbbBank.addItem(new ItemProperty(TempBankMode.PAPER, TempBankMode
				.getBankModeDesc(TempBankMode.PAPER)));
		cbbBank.addItem(new ItemProperty(TempBankMode.ICBC, TempBankMode
				.getBankModeDesc(TempBankMode.ICBC)));
		cbbBank.addItem(new ItemProperty(TempBankMode.BANKOFCHINA, TempBankMode
				.getBankModeDesc(TempBankMode.BANKOFCHINA)));

		cbbBank.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbBank);

	}

	private void initEmsType() {
		boolean isContractEms = this.isContractEms();
		cbbEmsType.removeAllItems();
		if (isContractEms) {
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.IMPORT_MATERIEL_EMS,
							ContractKind
									.getContractKindSpec(ContractKind.IMPORT_MATERIEL_EMS)));
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.COME_MATERIEL_EMS,
							ContractKind
									.getContractKindSpec(ContractKind.COME_MATERIEL_EMS)));

		} else {
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.FOREIGN_CAPITAL_SELL_PRODUCT,
							ContractKind
									.getContractKindSpec(ContractKind.FOREIGN_CAPITAL_SELL_PRODUCT)));
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.COME_MATERIEL_PROCESS,
							ContractKind
									.getContractKindSpec(ContractKind.COME_MATERIEL_PROCESS)));
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.IMPORT_MATERIEL_PROCESS,
							ContractKind
									.getContractKindSpec(ContractKind.IMPORT_MATERIEL_PROCESS)));
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.FOREIGN_CAPITAL_MACHINE,
							ContractKind
									.getContractKindSpec(ContractKind.FOREIGN_CAPITAL_MACHINE)));
			cbbEmsType
					.addItem(new ItemProperty(
							ContractKind.BONDEN_WAREHOUSE,
							ContractKind
									.getContractKindSpec(ContractKind.BONDEN_WAREHOUSE)));
		}
		cbbEmsType.setSelectedIndex(-1);

		if (isContractEms) {
			this.getMiAddUnitWasteFromOther().setText("从备案资料库中增加单耗");
		} else {
			this.getMiAddUnitWasteFromOther().setText("从商品资料中增加单耗");
		}

		if (DeclareState.CHANGING_EXE.equals(contract.getDeclareState())
				|| (DeclareState.WAIT_EAA.equals(contract.getDeclareState()) && !ModifyMarkState.ADDED
						.equals(contract.getModifyMark()))) {
			// cbbPrint.addItem(" " + ContractPrintItem.YES_WASTE_CHANGE);
			// cbbPrint.addItem(" " + ContractPrintItem.NO_WASTE_CHANGE);
			// cbbPrint.addItem(" "
			// + ContractPrintItem.OVERPRINT_IMPORT_CHANGED_REPORT);
			// cbbPrint.addItem(" " + ContractPrintItem.IMPORT_CHANGED_REPORT);
			// cbbPrint.addItem(" "
			// + ContractPrintItem.OVERPRINT_EXPORT_CHANGED_REPORT);
			// cbbPrint.addItem(" " + ContractPrintItem.EXPORT_CHANGED_REPORT);
			//
		}
		// cbbPrint.addItem(ContractPrintItem.BUYATHOME_MATERIAL_APPLAY);
	}

	/**
	 * 设置监管费
	 * 
	 */
	private void setWardshipFee() {
		String amountStr = "0";
		try {
			double wardship = cbbWardshipRate.getSelectedItem() != null ? Double
					.valueOf(cbbWardshipRate.getSelectedItem().toString())
					: 0.0;
			double imgAmount = Double.valueOf((tfImgAmount.getValue()
					.toString()));
			BigDecimal bd = new BigDecimal(wardship * imgAmount);
			amountStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			tfWardshipFee.setValue(Double.valueOf(amountStr));
		} catch (Exception ex) {
		}

	}

	/**
	 * 初始化Cbb呈现
	 * 
	 * @param cbb
	 */
	private void initComboBoxRenderer(JComboBox cbb) {
		cbb.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbb);
		cbb.setSelectedItem(null);
	}

	/**
	 * 清空数据
	 * 
	 */
	private void showEmptyData() {
		//
		// 显示日期
		//
		tfApprover.setText(null);
		tfCopEmsNo.setText(null);
		tfEmsNo.setText(null);
		tfTradeCode.setText(null);
		tfTradeName.setText(null);
		tfMachName.setText(null);
		tfMachCode.setText(null);
		tfEnterpriseAddress.setText(null);
		tfContactTel.setText(null);
		tfLinkMan.setText(null);
		tfOutTradeCo.setText(null);
		tfPermitNo.setText(null);
		tfExpContractNo.setText(null);
		tfImpContractNo.setText(null);
		tfAgreementNo.setText(null);
		tfImgAmount.setValue(new Double(0));
		tfExgAmount.setValue(new Double(0));
		tfWardshipFee.setValue(new Double(0));
		tfMemo.setText(null);
		tfEmsApprNo.setText(null);
		cbbBeginDate.setDate(null);
		cbbEndDate.setDate(null);
		cbbApproveDate.setDate(null);
		// cbbDeferDate.setDate(null);
		cbbDestroyDate.setDate(null);
		cbbWardshipRate.setSelectedItem(null);
		cbbDeclareCustoms.setSelectedItem(null);
		cbbTrade.setSelectedItem(null);
		cbbTradeCountry.setSelectedItem(null);
		cbbLevyKind.setSelectedItem(null);
		cbbPayMode.setSelectedItem(null);
		cbbMachiningType.setSelectedItem(null);
		cbbCurr.setSelectedItem(null);
		cbbTransac.setSelectedItem(null);
		cbbIePort.setSelectedItem(null);
		cbbIePort2.setSelectedItem(null);
		cbbIePort3.setSelectedItem(null);
		cbbIePort4.setSelectedItem(null);
		cbbIePort5.setSelectedItem(null);
		cbbEmsType.setSelectedItem(null);

		tfHandler.setText(null);
		tfOutLinkManager.setText(null);
		tfDictPorEmsNo.setText(null);
		tfInSaleRate.setValue(new Double(0));
		tfMaterielItemCount.setValue(new Double(0));
		tfProductItemCount.setValue(new Double(0));
		cbbReceiveArea.setSelectedItem(null);
		cbbInvestMode.setSelectedItem(null);
		cbbFetchInMode.setSelectedItem(null);
		cbbSaveDate.setDate(null);
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showHeadData() {
		contract = this.contractAction.findContractById(
				new Request(CommonVars.getCurrUser()), contract.getId());
		// contract = (Contract) this.tableModelContract.getCurrentRow();
		tableModelContract.updateRow(contract);
		if (null != contract.getCountScale()) {
			countScale = contract.getCountScale();
		}
		System.out.println("contract:" + contract.getDeclareState());
		if (DeclareState.APPLY_POR.equals(contract.getDeclareState())) {
			tfDeclareState.setText("草稿状态");
		} else if (DeclareState.WAIT_EAA.equals(contract.getDeclareState())) {
			tfDeclareState.setText("等待审批");
		} else if (DeclareState.CHANGING_EXE.equals(contract.getDeclareState())) {
			tfDeclareState.setText("正在变更");
		} else if (DeclareState.PROCESS_EXE.equals(contract.getDeclareState())) {
			tfDeclareState.setText("正在执行");
		}
		tfApprover.setText(contract.getApprover());
		tfCopEmsNo.setText(contract.getCopEmsNo());
		tfEmsNo.setText(contract.getEmsNo());
		tfTradeCode.setText(contract.getTradeCode());
		tfTradeName.setText(contract.getTradeName());
		tfMachName.setText(contract.getMachName());
		tfMachCode.setText(contract.getMachCode());
		tfEnterpriseAddress.setText(contract.getEnterpriseAddress());
		tfContactTel.setText(contract.getContactTel());
		tfLinkMan.setText(contract.getLinkMan());
		tfOutTradeCo.setText(contract.getOutTradeCo());
		tfPermitNo.setText(contract.getPermitNo());
		tfExpContractNo.setText(contract.getExpContractNo());
		tfImpContractNo.setText(contract.getImpContractNo());
		tfAgreementNo.setText(contract.getAgreementNo());
		tfImgAmount.setValue(contract.getImgAmount() == null ? new Double(0)
				: contract.getImgAmount());
		tfExgAmount.setValue(contract.getExgAmount() == null ? new Double(0)
				: contract.getExgAmount());
		tfWardshipFee
				.setValue(contract.getWardshipFee() == null ? new Double(0)
						: contract.getWardshipFee());
		tfMemo.setText(contract.getMemo());
		tfEmsApprNo.setText(contract.getEmsApprNo());

		tfHandler.setText(contract.getHandler());
		tfOutLinkManager.setText(contract.getOutLinkManager());
		tfDictPorEmsNo.setText(contract.getCorrEmsNo());

		tfInSaleRate.setValue(contract.getInSaleRate() == null ? new Double(0)
				: contract.getInSaleRate());
		tfMaterielItemCount
				.setValue(contract.getMaterielItemCount() == null ? new Double(
						0) : contract.getMaterielItemCount());
		tfProductItemCount
				.setValue(contract.getProductItemCount() == null ? new Double(0)
						: contract.getProductItemCount());
		if (contract.getIsContractEms() != null && contract.getIsContractEms()) {
			this.rbContractEms.setSelected(true);
		} else {
			// this.rbCommonContract.setSelected(true);
		}

		//
		// 显示下拉列表
		//
		if (contract.getEmsType() != null) {
			cbbEmsType.setSelectedIndex(ItemProperty.getIndexByCode(
					contract.getEmsType(), cbbEmsType));
		}
		cbbWardshipRate.setSelectedItem(contract.getWardshipRate());
		cbbDeclareCustoms.setSelectedItem(contract.getDeclareCustoms());
		cbbTrade.setSelectedItem(contract.getTrade());
		cbbTradeCountry.setSelectedItem(contract.getTradeCountry());
		cbbLevyKind.setSelectedItem(contract.getLevyKind());
		cbbPayMode.setSelectedItem(contract.getPayMode());
		cbbMachiningType.setSelectedItem(contract.getMachiningType());
		cbbCurr.setSelectedItem(contract.getCurr());
		cbbTransac.setSelectedItem(contract.getTransac());
		cbbIePort.setSelectedItem(contract.getIePort1());
		cbbIePort2.setSelectedItem(contract.getIePort2());
		cbbIePort3.setSelectedItem(contract.getIePort3());
		cbbIePort4.setSelectedItem(contract.getIePort4());
		cbbIePort5.setSelectedItem(contract.getIePort5());
		cbbReceiveArea.setSelectedItem(contract.getReceiveArea());
		cbbInvestMode.setSelectedItem(contract.getInvestMode());
		cbbFetchInMode.setSelectedItem(contract.getFetchInMode());
		cbbRedDep.setSelectedItem(contract.getRedDep());
		cbbManageObject.setSelectedIndex(ItemProperty.getIndexByCode(
				String.valueOf(contract.getManageObject()), cbbManageObject));
		//
		// 显示日期
		//
		cbbBeginDate.setDate(contract.getBeginDate());
		cbbEndDate.setDate(contract.getEndDate());
		cbbApproveDate.setDate(contract.getApproveDate());
		// cbbDeferDate.setDate(contract.getDeferDate());
		cbbDestroyDate.setDate(contract.getDestroyDate());
		cbbSaveDate.setDate(contract.getSaveDate());
		this.cbbLimitFlag.setSelectedIndex(ItemProperty.getIndexByCode(
				contract.getLimitFlag(), cbbLimitFlag));
		this.cbbEquipMode.setSelectedIndex(ItemProperty.getIndexByCode(
				contract.getEquipMode(), cbbEquipMode));
		this.cbbBank.setSelectedIndex(ItemProperty.getIndexByCode(
				contract.getBankModel(), cbbBank));
	}

	private void showImgData() {
		List list = new ArrayList();
		if (contract != null) {
			String parentId = contract.getId();
			list = contractAction.findContractImgByParentId(new Request(
					CommonVars.getCurrUser()), parentId);
		}
		initImgTable(list);
	}

	private void showExgData() {
		List list = new ArrayList();
		if (contract != null) {
			String parentId = contract.getId();
			list = contractAction.findContractExgByParentId(new Request(
					CommonVars.getCurrUser()), parentId);
		}
		initExgTable(list);
		if (list.size() <= 0) {
			initBomTable(list);
		}
	}

	private void showBomData() {
		List list = new ArrayList();
		if (tableModelExg == null || tableModelExg.getCurrentRow() == null) {
			initBomTable(list);
			return;
		}
		ContractExg exg = (ContractExg) tableModelExg.getCurrentRow();
		String parentId = exg.getId();
		list = contractAction.findContractBomByParentId(
				new Request(CommonVars.getCurrUser()), parentId);
		initBomTable(list);
	}

	/**
	 * 填充数据
	 * 
	 * @param contract
	 */
	private void fillData() {
		contract.setCopEmsNo(tfCopEmsNo.getText().trim());
		contract.setEmsNo(tfEmsNo.getText());
		contract.setEnterpriseAddress(tfEnterpriseAddress.getText());
		contract.setLinkMan(tfLinkMan.getText());
		contract.setContactTel(tfContactTel.getText());
		// contract.setSancEmsNo(tfPermitNo.getText());
		contract.setOutTradeCo(tfOutTradeCo.getText());
		contract.setAgreementNo(tfAgreementNo.getText());
		contract.setImpContractNo(tfImpContractNo.getText());
		contract.setExpContractNo(tfExpContractNo.getText());
		contract.setExgAmount(Double.valueOf((tfExgAmount.getValue().toString())));
		contract.setImgAmount(Double.valueOf((tfImgAmount.getValue().toString())));
		contract.setMemo(tfMemo.getText());

		contract.setEmsApprNo(tfEmsApprNo.getText());
		contract.setPermitNo(tfPermitNo.getText());
		contract.setApprover(tfApprover.getText());
		if (cbbWardshipRate.getSelectedItem() != null
				&& !cbbWardshipRate.getSelectedItem().equals("")) {
			contract.setWardshipRate(Double.valueOf(cbbWardshipRate
					.getSelectedItem().toString()));
		}
		contract.setWardshipFee(Double.valueOf((tfWardshipFee.getValue()
				.toString())));

		contract.setHandler(tfHandler.getText());
		contract.setOutLinkManager(tfOutLinkManager.getText());
		contract.setInSaleRate(Double.valueOf((tfInSaleRate.getValue()
				.toString())));
		contract.setMaterielItemCount(Double.valueOf(
				(tfMaterielItemCount.getValue().toString())).intValue());
		contract.setProductItemCount(Double.valueOf(
				(tfProductItemCount.getValue().toString())).intValue());

		//
		// 填充引用对象
		//
		contract.setLevyKind((LevyKind) cbbLevyKind.getSelectedItem());
		contract.setCurr((Curr) cbbCurr.getSelectedItem());
		contract.setTradeCountry((Country) cbbTradeCountry.getSelectedItem());
		contract.setTrade((Trade) cbbTrade.getSelectedItem());
		contract.setIePort1((Customs) cbbIePort.getSelectedItem());
		contract.setDeclareCustoms((Customs) cbbDeclareCustoms
				.getSelectedItem());
		contract.setRedDep((RedDep) cbbRedDep.getSelectedItem());
		contract.setTransac((Transac) cbbTransac.getSelectedItem());
		contract.setIePort4((Customs) cbbIePort4.getSelectedItem());
		contract.setIePort3((Customs) cbbIePort3.getSelectedItem());
		contract.setIePort2((Customs) cbbIePort2.getSelectedItem());
		contract.setIePort5((Customs) cbbIePort5.getSelectedItem());
		contract.setMachiningType((MachiningType) cbbMachiningType
				.getSelectedItem());
		contract.setPayMode((PayMode) cbbPayMode.getSelectedItem());
		contract.setCompany(CommonVars.getCurrUser().getCompany());
		if (this.cbbEmsType.getSelectedItem() != null) {
			ItemProperty itemProperty = (ItemProperty) cbbEmsType
					.getSelectedItem();
			contract.setEmsType(itemProperty.getCode());
		}
		contract.setReceiveArea((District) cbbReceiveArea.getSelectedItem());
		contract.setInvestMode((InvestMode) cbbInvestMode.getSelectedItem());
		contract.setFetchInMode((FetchInMode) cbbFetchInMode.getSelectedItem());
		contract.setAclUser(CommonVars.getCurrUser());
		//
		// 填充日期
		//
		contract.setBeginDate(cbbBeginDate.getDate());
		contract.setEndDate(cbbEndDate.getDate());
		// contract.setEndDate(cbbEndDate.getDate());
		contract.setApproveDate(cbbApproveDate.getDate());
		// contract.setDeferDate(cbbDeferDate.getDate());
		contract.setDestroyDate(cbbDestroyDate.getDate());
		contract.setSaveDate(cbbSaveDate.getDate());
		contract.setIsContractEms(rbContractEms.isSelected());
		if (this.cbbManageObject.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbManageObject
					.getSelectedItem();
			contract.setManageObject(item.getCode());
		}
		if (this.cbbLimitFlag.getSelectedItem() != null) {
			contract.setLimitFlag(((ItemProperty) cbbLimitFlag
					.getSelectedItem()).getCode());
		} else {
			contract.setLimitFlag(null);
		}
		if (this.cbbEquipMode.getSelectedItem() != null) {
			contract.setEquipMode(((ItemProperty) cbbEquipMode
					.getSelectedItem()).getCode());
		} else {
			contract.setEquipMode(null);
		}
		if (this.cbbBank.getSelectedItem() != null) {
			contract.setBankModel(((ItemProperty) cbbBank.getSelectedItem())
					.getCode());
		} else {
			contract.setBankModel(null);
		}
		if (ModifyMarkState.UNCHANGE.equals(contract.getModifyMark())) {
			contract.setModifyMark(ModifyMarkState.MODIFIED);
		}
		contract.setTradeCode(tfTradeCode.getText().trim());// 经营单位编码
		contract.setTradeName(tfTradeName.getText().trim());// 经营单位名称
		contract.setCorrEmsNo(this.tfDictPorEmsNo.getText().trim());
		contract.setCountScale(countScale);

	}

	/**
	 * 设置Components状态
	 */
	private void setState() {
		boolean isChanging = false;
		if (this.contract != null) {
			if (DeclareState.CHANGING_EXE.equals(this.contract
					.getDeclareState())) {
				isChanging = true;
			}
		} else {
			return;
		}

		boolean isExeing = false;

		if (this.contract != null) {
			if (DeclareState.PROCESS_EXE
					.equals(this.contract.getDeclareState())) {
				isExeing = true;
			}
		}

		boolean isWaiting = false;

		if (this.contract != null) {
			if (DeclareState.WAIT_EAA.equals(this.contract.getDeclareState())) {
				isWaiting = true;
			}
		}
		String declareState = this.contract.getDeclareState();
		boolean isContractEms = isContractEms();
		//
		// 合同基本信息面板
		//
		// setTabbedPaneChangeState();
		btnSave.setEnabled(!DeclareState.CHANGING_CANCEL.equals(declareState)
				&& (jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE); // 保存
		btnEdit.setEnabled(!DeclareState.CHANGING_CANCEL.equals(declareState)
				&& (jTabbedPane.getSelectedIndex() == 0)
				&& dataState == DataState.BROWSE && !isExeing && !isWaiting); // 修改
		btnUndo.setEnabled(!DeclareState.CHANGING_CANCEL.equals(declareState)
				&& (jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE); // 撤消
		cbbDeclareCustoms.setEnabled(dataState != DataState.BROWSE);
		this.cbbRedDep.setEnabled(dataState != DataState.BROWSE);
		this.cbbManageObject.setEnabled(dataState != DataState.BROWSE
				&& !isChanging);
		cbbIePort.setEnabled(dataState != DataState.BROWSE);
		// tfPreEmsNo.setEditable(dataState != DataState.BROWSE);
		cbbEmsType.setEnabled(dataState != DataState.BROWSE);
		cbbBeginDate.setEnabled(dataState != DataState.BROWSE);
		cbbEndDate.setEnabled(dataState != DataState.BROWSE);
		cbbTrade.setEnabled(dataState != DataState.BROWSE);
		cbbTradeCountry.setEnabled(dataState != DataState.BROWSE);
		tfEnterpriseAddress.setEditable(dataState != DataState.BROWSE);
		// cbbDeferDate.setEnabled(dataState != DataState.BROWSE);
		cbbDestroyDate.setEnabled(dataState != DataState.BROWSE);
		cbbLevyKind.setEnabled(dataState != DataState.BROWSE);
		tfContactTel.setEditable(dataState != DataState.BROWSE);
		tfLinkMan.setEditable(dataState != DataState.BROWSE);
		tfOutTradeCo.setEditable(dataState != DataState.BROWSE);
		tfPermitNo.setEditable(dataState != DataState.BROWSE);
		tfExpContractNo.setEditable(!(dataState != DataState.BROWSE));
		tfImpContractNo.setEditable(dataState != DataState.BROWSE);
		tfAgreementNo.setEditable(dataState != DataState.BROWSE);
		// tfImgAmount.setEnabled(dataState != DataState.BROWSE);
		// tfExgAmount.setEnabled(dataState != DataState.BROWSE);
		cbbCurr.setEnabled(dataState != DataState.BROWSE);
		cbbTransac.setEnabled(dataState != DataState.BROWSE);
		tfWardshipFee.setEnabled(dataState != DataState.BROWSE);
		cbbWardshipRate.setEnabled(dataState != DataState.BROWSE);
		cbbIePort2.setEnabled(dataState != DataState.BROWSE);
		cbbIePort3.setEnabled(dataState != DataState.BROWSE);
		cbbIePort4.setEnabled(dataState != DataState.BROWSE);
		cbbIePort5.setEnabled(dataState != DataState.BROWSE);
		tfApprover.setEditable(dataState != DataState.BROWSE);
		cbbApproveDate.setEnabled(dataState != DataState.BROWSE);
		cbbPayMode.setEnabled(dataState != DataState.BROWSE);
		cbbMachiningType.setEnabled(dataState != DataState.BROWSE);
		tfMemo.setEditable(dataState != DataState.BROWSE);
		tfEmsApprNo.setEditable(dataState != DataState.BROWSE);
		cbbLimitFlag.setEnabled(dataState != DataState.BROWSE);
		this.cbbEquipMode.setEnabled(dataState != DataState.BROWSE);
		this.cbbBank.setEnabled(dataState != DataState.BROWSE);
		tfEmsNo.setEditable(dataState != DataState.BROWSE && !isChanging
				&& !isContractEms);
		this.tfDictPorEmsNo.setEditable(dataState != DataState.BROWSE
				&& !isChanging);
		int imgCount = contractAction.findContractImgCount(new Request(
				CommonVars.getCurrUser()), this.contract.getId());
		int exgCount = contractAction.findContractExgCount(new Request(
				CommonVars.getCurrUser()), this.contract.getId());
		// rbCommonContract.setEnabled(dataState != DataState.BROWSE
		// && imgCount == 0 && exgCount == 0);
		rbContractEms.setEnabled(dataState != DataState.BROWSE && imgCount == 0
				&& exgCount == 0);
		this.tfCopEmsNo.setEditable(dataState != DataState.BROWSE
				&& DeclareState.APPLY_POR.equals(declareState));
		//
		// 成品及单耗面板
		//
		this.btnAddFromCredenceFinishProduct
				.setEnabled(dataState == DataState.BROWSE && !isExeing
						&& !isWaiting);
		this.btnEditFinishProduct.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnChangeFinishProductNo.setEnabled(dataState == DataState.BROWSE
				&& !isExeing);// &&
		// !isChanging
		// &&
		// !isExeing
		this.btnDeleteFinishProduct.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnFinishProductSort.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnEditUnitWaste.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnAddUnitWaste.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnDeleteUnitWaste.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnSaveBom.setEnabled(dataState == DataState.BROWSE && !isExeing
				&& !isWaiting);
		this.btnRefreshBom.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnUnitWasteSort.setEnabled(dataState == DataState.BROWSE
				&& !isChanging && !isExeing && !isWaiting);
		this.getMiAddUnitWasteFromContractImg().setEnabled(
				dataState == DataState.BROWSE && !isExeing && !isWaiting);
		this.getMiAddUnitWasteFromOther().setEnabled(
				dataState == DataState.BROWSE && !isExeing && !isWaiting);
		this.btnChangeProductNameSpec.setEnabled(!isExeing && !isWaiting);
		this.btnCopyUnitWaste.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnDeleteUnitWasteIsNull.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		// this.btnChangeExgComplex.setEnabled(!isChanging);

		// x
		// 料件总表面板 btnChangingMaterielNo btnChangingFinishProductNo
		//
		jButton1.setEnabled(DeclareState.APPLY_POR.equals(this.contract
				.getDeclareState())
				|| DeclareState.CHANGING_EXE.equals(this.contract
						.getDeclareState()));

		this.btnDeleteMateriel.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnAddMateriel.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnAmountToInteger.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnEditMateriel.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnChangingMaterielNo.setEnabled(dataState == DataState.BROWSE
				&& !isExeing);
		// && !isExeing
		this.btnMaterielSort.setEnabled(dataState == DataState.BROWSE
				&& !isExeing && !isWaiting);
		this.btnChangeMaterielNameSpec.setEnabled(!isExeing && !isWaiting);
		// this.btnChangeImgComplex.setEnabled(!isChanging);
		this.btnCal.setVisible(!isContractEms
				|| (isContractEms && DeclareState.APPLY_POR
						.equals(this.contract.getDeclareState())));
		this.btnCal.setEnabled(!isExeing && !isWaiting);
		// this.btnChangeExgComplex.setVisible(!isContractEms);
		// this.btnChangeFinishProductNo.setVisible(!isContractEms);
		// this.btnChangeImgComplex.setVisible(!isContractEms);
		// this.btnChangeMaterielNameSpec.setVisible(!isContractEms);
		// this.btnChangeProductNameSpec.setVisible(!isContractEms);
		// this.btnChangingMaterielNo.setVisible(!isContractEms);
		// this.btnAmountToInteger.setVisible(!isContractEms);
		btnTradeCode.setEnabled(dataState != DataState.BROWSE && !isExeing
				&& !isChanging && !isWaiting);

		this.getMiUndoDeclare().setEnabled(
				DeclareState.WAIT_EAA.equals(declareState));
		this.btnChangeExgModifyMark.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnChangeImgModifyMark.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.btnChangeBomModifyMark.setEnabled(DeclareState.APPLY_POR
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		this.tfRound.setEditable(dataState == DataState.BROWSE && !isExeing
				&& !isWaiting);
		this.btnRound.setEnabled(dataState == DataState.BROWSE && !isExeing
				&& !isWaiting);
		this.rbUnitWare.setEnabled(dataState == DataState.BROWSE && !isExeing
				&& !isWaiting);
		this.rbWare.setEnabled(dataState == DataState.BROWSE && !isExeing
				&& !isWaiting);
	}

	/**
	 * 是一般合同还是纸质手册电子化 2013-3-15 hwy 纸质手册电子化，根据管理类型进行判断手册号栏位是否可以编辑
	 * 
	 * @return
	 */
	private boolean isContractEms() {
		if (contract.getIsContractEms() != null
				&& contract.getIsContractEms() == false) {
			return false;
		}
		if (bcsParameterSet == null) {
			bcsParameterSet = contractAction.findBcsParameterSet(new Request(
					CommonVars.getCurrUser()));
		}
		boolean type;
		if (bcsParameterSet.getManageType() != null
				&& bcsParameterSet.getManageType().equals("0")) {
			type = true;
		} else {
			type = false;
		}
		return type;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddMateriel() {
		if (btnAddMateriel == null) {
			btnAddMateriel = new JButton();
			btnAddMateriel.setText("新增料件");
			btnAddMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (contract == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请先保存合同记录!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							List list = new ArrayList();
							List lsResult = new ArrayList();
							// boolean isContractEms1 = isContractEms();
							boolean isContractEms = true;
							if (isContractEms) {
								contract = contractAction.findContractById(
										new Request(CommonVars.getCurrUser()),
										contract.getId());
								String dictPorEmsNo = contract.getCorrEmsNo();
								if (dictPorEmsNo == null
										|| "".equals(dictPorEmsNo.trim())) {
									List lsExingBcsDictPor = bcsDictPorAction
											.findExingBcsDictPorHead(new Request(
													CommonVars.getCurrUser(),
													true));
									if (lsExingBcsDictPor.size() <= 0) {
										JOptionPane
												.showMessageDialog(
														DgContract.this,
														"备案资料库料件没有资料",
														"提示",
														JOptionPane.INFORMATION_MESSAGE);
										return;
									} else if (lsExingBcsDictPor.size() == 1) {
										dictPorEmsNo = ((BcsDictPorHead) lsExingBcsDictPor
												.get(0)).getDictPorEmsNo();
									} else {
										BcsDictPorHead bcsDictPorHead = BcsClientHelper
												.getInstance()
												.findExingBcsDictPorHead(
														lsExingBcsDictPor);
										if (bcsDictPorHead == null) {
											return;
										}
										dictPorEmsNo = bcsDictPorHead
												.getDictPorEmsNo();
									}
								}
								boolean isFilt = true;
								if (JOptionPane
										.showConfirmDialog(
												DgContract.this,
												"是否根据手册料件过滤查询出来的备案资料库料件\r\n如果选\"是\"是过滤，选\"否\"不过滤",
												"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									isFilt = false;
								}
								list = BcsClientHelper.getInstance()
										.findBcsDictPorImgExgNotContract(
												MaterielType.MATERIEL,
												contract.getId(), dictPorEmsNo,
												isFilt);
								if (list == null || list.size() <= 0) {
									return;
								}
								if (contract.getCorrEmsNo() == null
										|| "".equals(contract.getCorrEmsNo()
												.trim())) {
									contract.setCorrEmsNo(dictPorEmsNo);
									contract = contractAction.saveContract(
											new Request(CommonVars
													.getCurrUser()), contract);
								}
								lsResult = contractAction
										.saveContractImgFromDictPorImg(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
							} else {
								if (CommonVars.getContractFrom() != null) {
									if (CommonVars.getContractFrom()
											.equals("2")) {
										list = BcsClientHelper.getInstance()
												.getComplex();
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveComtractImgByComplex(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
									} else if (CommonVars.getContractFrom()
											.equals("1")) {
										list = BcsClientHelper.getInstance()
												.findBcsInnerMergeNotContract(
														contract,
														MaterielType.MATERIEL,
														true);
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveContractImg(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
									} else if (CommonVars.getContractFrom()
											.equals("0")) {
										list = BcsClientHelper.getInstance()
												.findBcsInnerMergeNotContract(
														contract,
														MaterielType.MATERIEL,
														false);
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveContractImg(
												new Request(CommonVars
														.getCurrUser()),
												contract, list);
									}
								}
							}

							tableModelImg.addRows(lsResult);
							DgContractMateriel dg = new DgContractMateriel();
							dg.setTableModel(tableModelImg);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);

						}
					});
		}
		return btnAddMateriel;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefreshBom() {
		if (btnDeleteMateriel == null) {
			btnDeleteMateriel = new JButton();
			btnDeleteMateriel.setText("删除料件");
			btnDeleteMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImg == null
									|| tableModelImg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择要删除的料件记录!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (JOptionPane.showConfirmDialog(DgContract.this,
									"是否确定删除合同料件记录?", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							List deleteList = tableModelImg.getCurrentRows();

							/**
							 * 查找合同BOM 来自 合同料件序号
							 */
							List<ContractImg> contractBomList = contractAction.findContractBomImg(
									new Request(CommonVars.getCurrUser()),
									deleteList);
							if (contractBomList.size() > 0) {
								ContractImg contractImg = contractBomList
										.get(0);
								if (JOptionPane.showConfirmDialog(
										DgContract.this, "料件'"
												+ contractImg.getComplex()
														.getCode()
												+ "'在成品BOM中存在，确定要删除吗?", "提示",
										JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
									return;
								}
							}
							Map<Integer, List<ContractImg>> map = contractAction.deleteContractImg(
									new Request(CommonVars.getCurrUser()),
									deleteList);
							List lsDelete = map.get(DeleteResultMark.DELETE);
							if (lsDelete != null && lsDelete.size() > 0) {
								tableModelImg.deleteRows(lsDelete);
							}
							List lsUpdate = map.get(DeleteResultMark.UPDATE);
							if (lsUpdate != null && lsUpdate.size() > 0) {
								tableModelImg.updateRows(lsUpdate);
							}
						}
					});
		}
		return btnDeleteMateriel;
	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if (!(component instanceof JLabel)) {
				component.setEnabled(isFlag);
				if (component instanceof Container) {
					setContainerEnabled((Container) component, isFlag);
				}
			}
		}
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbReceiveArea() {
		if (cbbReceiveArea == null) {
			cbbReceiveArea = new JComboBox();
			cbbReceiveArea.setBounds(new Rectangle(106, 351, 127, 20));
		}
		return cbbReceiveArea;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfHandler() {
		if (tfHandler == null) {
			tfHandler = new JTextField();
			tfHandler.setBounds(new Rectangle(106, 374, 127, 20));
		}
		return tfHandler;
	}

	/**
	 * This method initializes jCustomFormattedTextField1
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfInSaleRate() {
		if (tfInSaleRate == null) {
			tfInSaleRate = new JCustomFormattedTextField();
			tfInSaleRate.setBounds(new Rectangle(320, 351, 127, 20));
			tfInSaleRate.setValue(new Double(0));
		}
		return tfInSaleRate;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbSaveDate() {
		if (cbbSaveDate == null) {
			cbbSaveDate = new JCalendarComboBox();
			cbbSaveDate.setBounds(new Rectangle(540, 351, 127, 20));
		}
		return cbbSaveDate;
	}

	/**
	 * This method initializes jCustomFormattedTextField2
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfMaterielItemCount() {
		if (tfMaterielItemCount == null) {
			tfMaterielItemCount = new JCustomFormattedTextField();
			tfMaterielItemCount.setBounds(new Rectangle(320, 374, 127, 20));
			tfMaterielItemCount.setEditable(false);
			tfMaterielItemCount.setValue(new Double(0));
		}
		return tfMaterielItemCount;
	}

	/**
	 * This method initializes jCustomFormattedTextField4
	 * 
	 * @return com.bestway.ui.winuicontrol.JCustomFormattedTextField
	 */
	private JCustomFormattedTextField getTfProductItemCount() {
		if (tfProductItemCount == null) {
			tfProductItemCount = new JCustomFormattedTextField();
			tfProductItemCount.setBounds(new Rectangle(540, 374, 127, 20));
			tfProductItemCount.setEditable(false);
			tfProductItemCount.setValue(new Double(0));
		}
		return tfProductItemCount;
	}

	private JTextField getTfOutLinkManager() {
		if (tfOutLinkManager == null) {
			tfOutLinkManager = new JTextField();
			tfOutLinkManager.setBounds(new Rectangle(320, 199, 127, 20));
			// tfOutLinkManager.setEditable(false);
			// tfOutLinkManager.setValue(new Double(0));
		}
		return tfOutLinkManager;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfDictPorEmsNo() {
		if (tfDictPorEmsNo == null) {
			tfDictPorEmsNo = new JTextField();
			tfDictPorEmsNo.setBounds(new Rectangle(320, 398, 127, 20));
			tfDictPorEmsNo.setEditable(true);
		}
		return tfDictPorEmsNo;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbInvestMode() {
		if (cbbInvestMode == null) {
			cbbInvestMode = new JComboBox();
			cbbInvestMode.setBounds(new Rectangle(106, 419, 127, 20));
		}
		return cbbInvestMode;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbFetchInMode() {
		if (cbbFetchInMode == null) {
			cbbFetchInMode = new JComboBox();
			cbbFetchInMode.setBounds(new Rectangle(106, 397, 127, 20));
		}
		return cbbFetchInMode;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("导出Excel");
			jButton.setVisible(false);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgBomToExcel dg = new DgBomToExcel();
					dg.setContract(contract);
					dg.setVisible(true);

				}
			});
		}
		return jButton;
	}

	private JButton getBtnChangeMaterielNameSpec() {
		if (btnChangeMaterielNameSpec == null) {
			btnChangeMaterielNameSpec = new JButton();
			btnChangeMaterielNameSpec.setText("变更名称规格");
			btnChangeMaterielNameSpec.setVisible(true);
			btnChangeMaterielNameSpec
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelImg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										btnChangeMaterielNameSpec,
										"       请选择要修改的料件!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ContractImg info = (ContractImg) tableModelImg
									.getCurrentRow();

							String complexCode = info.getComplex().getCode();

							Contract base = (Contract) info.getContract();
							if (base.getIsContractEms() != null
									&& base.getIsContractEms()) {
								List rlist = contractAction
										.findBcsCustomsDeclarationCommInfo(
												new Request(CommonVars
														.getCurrUser()), base
														.getEmsNo(), info
														.getSeqNum(),
												complexCode, ImpExpFlag.IMPORT,
												ImpExpType.BACK_FACTORY_REWORK);
								if (rlist.size() > 0) {
									String emsNuber = "该项商品以被以下报关单所使用，流水号如下:\n";
									for (int i = 0; i < rlist.size(); i++) {
										emsNuber += (rlist.get(i) == null ? ""
												: rlist.get(i).toString() + ",");
									}
									emsNuber += "\n所以不能变更名称规格！";
									JOptionPane.showMessageDialog(
											DgContract.this, emsNuber, "警告！",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
							}
							DgChangeContractNameSpec dg = new DgChangeContractNameSpec();
							dg.setContract(contract);
							dg.setTableModel(tableModelImg);
							dg.setIsMaterial(true);
							dg.setVisible(true);
						}
					});
		}
		return btnChangeMaterielNameSpec;

	}

	private JButton getBtnChangeProductNameSpec() {

		if (btnChangeProductNameSpec == null) {

			btnChangeProductNameSpec = new JButton();

			btnChangeProductNameSpec.setText("变更名称规格");

			btnChangeProductNameSpec.setVisible(true);

			btnChangeProductNameSpec
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (tableModelExg.getCurrentRow() == null) {

								JOptionPane.showMessageDialog(
										btnChangeProductNameSpec,
										"       请选择要修改的商品!", "提示!",
										JOptionPane.INFORMATION_MESSAGE);

								return;

							}

							// 合同成品 （当前所选择的成品信息）
							ContractExg info = (ContractExg) tableModelExg
									.getCurrentRow();

							// 商品编码
							String complexCode = info.getComplex().getCode();

							Contract base = (Contract) info.getContract();

							if (base.getIsContractEms() != null
									&& base.getIsContractEms()) {

								/*
								 * 商品序号可能 出现相同 ,但是商品编码 不可能相同
								 * 
								 * 2者为并列条件 可排除料件或成品的序号出现被使用报关单使用
								 */
								List rlist = contractAction.findBcsCustomsDeclarationCommInfo(
										new Request(CommonVars.getCurrUser()),
										base.getEmsNo(), info.getSeqNum(),
										complexCode, ImpExpFlag.EXPORT,
										ImpExpType.BACK_MATERIEL_EXPORT);

								if (rlist.size() > 0) {

									String emsNuber = "该项商品以被以下报关单所使用，流水号如下:\n";

									for (int i = 0; i < rlist.size(); i++) {

										emsNuber += (rlist.get(i) == null ? ""
												: rlist.get(i).toString() + ",");

									}

									emsNuber += "\n所以不能变更名称规格！";

									JOptionPane.showMessageDialog(
											DgContract.this, emsNuber, "警告！",
											JOptionPane.WARNING_MESSAGE);

									return;

								}

							}

							DgChangeContractNameSpec dg = new DgChangeContractNameSpec();

							dg.setIsMaterial(false);

							dg.setContract(contract);

							dg.setTableModel(tableModelExg);

							dg.setVisible(true);

						}
					});
		}
		return btnChangeProductNameSpec;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopyUnitWaste() {
		if (btnCopyUnitWaste == null) {
			btnCopyUnitWaste = new JButton();
			btnCopyUnitWaste.setText("转抄单耗");
			btnCopyUnitWaste.setPreferredSize(new Dimension(65, 30));
			btnCopyUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// if(!bcsParameterSet.getPutOnRecords()){
							// JOptionPane.showMessageDialog(DgContract.this,
							// "电子化手册参数已设置【允许多本备案资料库备案】，无法转抄单耗，请重新设置后再尝试！",
							// "提示", 2);
							// return;
							// }

							// 如果获取当前行没有 成品的数据 就提示
							if (tableModelExg.getCurrentRow() == null) {

								JOptionPane.showMessageDialog(DgContract.this,
										"请选中要转抄单耗的成品资料", "提示", 2);

								return;

							}

							/**
							 * 这里将 加入 2 个新的 按钮 作为 本合同转抄 / 其他合同转抄
							 */
							Component comp = (Component) e.getSource();

							getCopyBOM().show(comp, 0, comp.getHeight());

						}
					});
		}
		return btnCopyUnitWaste;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCal() {
		if (btnCal == null) {
			btnCal = new JButton();
			btnCal.setText("重新计算");
			btnCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(DgContract.this,
							"是否确定根据成品数量和单耗重新反算成品总金额和料件数量!!!", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					try {
						contractAction.reCalContractUnitWaste(new Request(
								CommonVars.getCurrUser()), contract);
						if (jTabbedPane.getSelectedIndex() == 1) {
							showImgData();
						} else if (jTabbedPane.getSelectedIndex() == 2) {
							showExgData();
						}
						JOptionPane.showMessageDialog(DgContract.this,
								"重新计算成功!!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(DgContract.this,
								"重新计算失败!!!\n" + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						System.out.println(ex.getMessage());
						return;
					}
				}
			});
		}
		return btnCal;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteUnitWasteIsNull() {
		if (btnDeleteUnitWasteIsNull == null) {
			btnDeleteUnitWasteIsNull = new JButton();
			btnDeleteUnitWasteIsNull.setText("删除单耗为空的记录");
			btnDeleteUnitWasteIsNull
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(DgContract.this,
									"是否确定删除单耗为空的 记录!!!", "提示",
									JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
							contractAction.deleteContractBomUnitWasteIsNull(
									new Request(CommonVars.getCurrUser(), true),
									contract); // 这个删除里加了只有增加才可以
							if (tableModelExg.getCurrentRow() != null) {
								ContractExg exg = ((ContractExg) tableModelExg
										.getCurrentRow());
								String parentId = exg.getId();
								List list = contractAction
										.findContractBomByParentId(new Request(
												CommonVars.getCurrUser()),
												parentId);
								initBomTable(list);
							}
						}
					});
		}
		return btnDeleteUnitWasteIsNull;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeCode() {
		if (tfTradeCode == null) {
			tfTradeCode = new JTextField();
			tfTradeCode.setBounds(new Rectangle(106, 86, 106, 22));
			tfTradeCode.setEditable(false);
		}
		return tfTradeCode;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfTradeName() {
		if (tfTradeName == null) {
			tfTradeName = new JTextField();
			tfTradeName.setBounds(new Rectangle(320, 86, 347, 22));
			tfTradeName.setEditable(false);
		}
		return tfTradeName;
	}

	/**
	 * This method initializes cbbDeclareType1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbRedDep() {
		if (cbbRedDep == null) {
			cbbRedDep = new JComboBox();
			cbbRedDep.setBounds(new Rectangle(540, 41, 127, 21));
		}
		return cbbRedDep;
	}

	/**
	 * This method initializes cbbDeclareType11
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageObject() {
		if (cbbManageObject == null) {
			cbbManageObject = new JComboBox();
			cbbManageObject.setBounds(new Rectangle(540, 18, 127, 20));
		}
		return cbbManageObject;
	}

	/**
	 * This method initializes jPanel2
	 * 2013-3-14因去掉纸质手册模式，所以默认手册模式为：纸质手册电子化，值为true，此jpanle也隐藏
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			// jPanel2.setBounds(new Rectangle(41, 469, 626, 29));
			jPanel2.setBorder(BorderFactory
					.createLineBorder(Color.lightGray, 1));
			// lyh 2013-03-04 根据需求现在海关模式已早就不存在【纸质手册】，古去掉此选项
			// jPanel2.add(getRbCommonContract(), null);
			jPanel2.add(getRbContractEms(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbCommonContract() {
		if (rbCommonContract == null) {
			rbCommonContract = new JRadioButton();
			rbCommonContract.setText("普通纸质手册");
			rbCommonContract.setBounds(new Rectangle(73, 3, 97, 23));
			rbCommonContract
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							contract.setIsContractEms(false);
							initEmsType();
						}
					});
		}
		return rbCommonContract;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbContractEms() {
		if (rbContractEms == null) {
			rbContractEms = new JRadioButton();
			rbContractEms.setText("纸质手册电子化");
			rbContractEms.setSelected(true);
			// rbContractEms.setBounds(new Rectangle(73, 3, 150, 23));
			rbContractEms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							contract.setIsContractEms(true);
							initEmsType();
						}
					});
		}
		return rbContractEms;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			// buttonGroup.add(this.getRbCommonContract());
			buttonGroup.add(this.getRbContractEms());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveBom() {
		if (btnSaveBom == null) {
			btnSaveBom = new JButton();
			btnSaveBom.setText("保存");
			btnSaveBom.setPreferredSize(new Dimension(65, 30));
			btnSaveBom.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom != null) {
						if (tbBom.getCellEditor() != null) {
							tbBom.getCellEditor().stopCellEditing();
						}
						List list = tableModelBom.getList();
						for (int i = 0; i < list.size(); i++) {
							ContractBom bom = (ContractBom) list.get(i);
							bom = contractAction.saveContractBom(new Request(
									CommonVars.getCurrUser()), bom);
							tableModelBom.updateRow(bom);
						}
						if (list.size() > 0) {
							ContractExg contractExg = ((ContractBom) list
									.get(0)).getContractExg();
							if (contractExg != null) {
								contractExg = (ContractExg) contractAction
										.load(ContractExg.class,
												contractExg.getId());
								if (contractExg != null) {
									tableModelExg.updateRow(contractExg);
								}
							}
						}
					}
				}
			});
		}
		return btnSaveBom;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton22() {
		if (btnRefreshBom == null) {
			btnRefreshBom = new JButton();
			btnRefreshBom.setText("取消");
			btnRefreshBom.setPreferredSize(new Dimension(65, 30));
			btnRefreshBom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = new ArrayList();
							if (tableModelExg.getCurrentRow() != null) {
								ContractExg exg = ((ContractExg) tableModelExg
										.getCurrentRow());
								// lbContractExg.setText("当前单耗的成品序号："
								// + exg.getSeqNum() + " " + "名称："
								// + exg.getName());
								String parentId = exg.getId();
								list = contractAction
										.findContractBomByParentId(new Request(
												CommonVars.getCurrUser()),
												parentId);
							}
							initBomTable(list);
						}
					});
		}
		return btnRefreshBom;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("导入");
			jButton1.setPreferredSize(new Dimension(80, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgContractInput dg = new DgContractInput();
					dg.setHead(contract);
					if (rbContractEms.isSelected()) {
						dg.setEms(true);
					} else {
						dg.setEms(false);
					}
					dg.setVisible(true);
					showData(jTabbedPane);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbLimitFlag() {
		if (cbbLimitFlag == null) {
			cbbLimitFlag = new JComboBox();
			cbbLimitFlag.setBounds(new Rectangle(106, 441, 127, 20));
		}
		return cbbLimitFlag;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTradeCode() {
		if (btnTradeCode == null) {
			btnTradeCode = new JButton();
			btnTradeCode.setText("...");
			btnTradeCode.setBounds(new Rectangle(214, 86, 22, 22));
			btnTradeCode.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc obj = (ScmCoc) CommonQuery.getInstance()
							.getTradeCode();
					if (obj != null) {
						tfTradeCode.setText(obj.getCode());
						tfTradeName.setText(obj.getName());
					}
				}
			});
		}
		return btnTradeCode;
	}

	/**
	 * This method initializes btnBomExport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBomExport() {
		if (btnBomExport == null) {
			btnBomExport = new JButton();
			btnBomExport.setText("BOM导出");
			btnBomExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBcsBOMExport dg = new DgBcsBOMExport();
					dg.setContract(contract);
					dg.setVisible(true);
				}
			});
		}
		return btnBomExport;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDeclareState() {
		if (btnChangeDeclareState == null) {
			btnChangeDeclareState = new JButton();
			btnChangeDeclareState.setText("改变申报状态");
			btnChangeDeclareState
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								getPmChangeDeclareState().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeDeclareState;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeImgModifyMark() {
		if (btnChangeImgModifyMark == null) {
			btnChangeImgModifyMark = new JButton();
			btnChangeImgModifyMark.setText("改变修改标志");
			btnChangeImgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeImgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeImgModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeImgModifyMark;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeExgModifyMark() {
		if (btnChangeExgModifyMark == null) {
			btnChangeExgModifyMark = new JButton();
			btnChangeExgModifyMark.setText("改变修改标志");
			btnChangeExgModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeExgModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeExgModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeExgModifyMark;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeBomModifyMark() {
		if (btnChangeBomModifyMark == null) {
			btnChangeBomModifyMark = new JButton();
			btnChangeBomModifyMark.setText("改变修改标志");
			btnChangeBomModifyMark.setToolTipText(ModifyMarkState
					.getToolTipText());
			btnChangeBomModifyMark
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgAllChangState dg = new DgAllChangState();
							dg.setVisible(true);
							if (!dg.isOk()) {
								return;
							}
							if (dg.isCheckBoxState()) {
								Component comp = (Component) e.getSource();
								addChangeModifyMarkListener(getMiNotModified(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.UNCHANGE));
								addChangeModifyMarkListener(getMiModified(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.MODIFIED));
								addChangeModifyMarkListener(getMiDelete(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.DELETED));
								addChangeModifyMarkListener(getMiAdd(),
										new ChangeBomModifyMarkListener(
												ModifyMarkState.ADDED));
								getPmChangeModifyMark().show(comp, 0,
										comp.getHeight());
							}
						}
					});
		}
		return btnChangeBomModifyMark;
	}

	/**
	 * This method initializes pmChangeDeclareState
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeDeclareState() {
		if (pmChangeDeclareState == null) {
			pmChangeDeclareState = new JPopupMenu();
			pmChangeDeclareState.add(getMiUndoDeclare());
		}
		return pmChangeDeclareState;
	}

	/**
	 * This method initializes miUndoDeclare
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiUndoDeclare() {
		if (miUndoDeclare == null) {
			miUndoDeclare = new JMenuItem();
			miUndoDeclare.setText("\u53d6\u6d88\u7533\u62a5");
			miUndoDeclare
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String declareState = "";
							if (contract.getEmsNo() != null
									&& !"".equals(contract.getEmsNo().trim())) {
								declareState = DeclareState.CHANGING_EXE;
							} else {
								declareState = DeclareState.APPLY_POR;
							}
							contract = contractAction
									.changeContractDeclareState(new Request(
											CommonVars.getCurrUser()),
											contract, declareState);
							setState();
							if (tableModelContract != null) {
								tableModelContract.updateRow(contract);
							}
						}
					});
		}
		return miUndoDeclare;
	}

	/**
	 * This method initializes pmChangeModifyMark
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmChangeModifyMark() {
		if (pmChangeModifyMark == null) {
			pmChangeModifyMark = new JPopupMenu();
			pmChangeModifyMark.add(getMiNotModified());
			pmChangeModifyMark.add(getMiModified());
			pmChangeModifyMark.add(getMiDelete());
			pmChangeModifyMark.add(getMiAdd());
		}
		return pmChangeModifyMark;
	}

	/**
	 * This method initializes miNotModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotModified() {
		if (miNotModified == null) {
			miNotModified = new JMenuItem();
			miNotModified.setText("\u672a\u4fee\u6539");
		}
		return miNotModified;
	}

	/**
	 * This method initializes miModified
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiModified() {
		if (miModified == null) {
			miModified = new JMenuItem();
			miModified.setText("\u5df2\u4fee\u6539");
		}
		return miModified;
	}

	/**
	 * This method initializes miDelete
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiDelete() {
		if (miDelete == null) {
			miDelete = new JMenuItem();
			miDelete.setText("\u5df2\u5220\u9664");
		}
		return miDelete;
	}

	private JMenuItem getMiAdd() {
		if (miAdd == null) {
			miAdd = new JMenuItem();
			miAdd.setText("新  增");
		}
		return miAdd;
	}

	class ChangeImgModifyMarkListener implements ActionListener {

		private String modifyMark = "";

		public ChangeImgModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelImg.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgContract.this, "请选择料件", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = tableModelImg.getCurrentRows();
			contractAction.changeContractImgModifyMark(
					new Request(CommonVars.getCurrUser()), list, modifyMark);
			showImgData();
		}

	}

	class ChangeExgModifyMarkListener implements ActionListener {

		private String modifyMark = "";

		public ChangeExgModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelExg.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgContract.this, "请选择成品", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = tableModelExg.getCurrentRows();
			contractAction.changeContractExgModifyMark(
					new Request(CommonVars.getCurrUser()), list, modifyMark);
			showExgData();
		}

	}

	class ChangeBomModifyMarkListener implements ActionListener {

		private String modifyMark = "";

		public ChangeBomModifyMarkListener(String modifyMark) {
			this.modifyMark = modifyMark;
		}

		public void actionPerformed(ActionEvent e) {
			if (tableModelBom.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(DgContract.this, "请选择单耗", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			List list = tableModelBom.getCurrentRows();
			contractAction.changeContractBomModifyMark(
					new Request(CommonVars.getCurrUser()), list, modifyMark);
			// showBomData();
			// showExgData();

			JTableListModel tableModel = (JTableListModel) tbExg.getModel();
			if (tableModel == null) {
				return;
			}
			if (tableModel.getCurrentRow() != null) {
				ContractExg exg = ((ContractExg) tableModel.getCurrentRow());
				String parentId = exg.getId();
				list = contractAction.findContractBomByParentId(new Request(
						CommonVars.getCurrUser()), parentId);
			}
			initBomTable(list);
		}
	}

	private void addChangeModifyMarkListener(JMenuItem menuItem,
			ActionListener actionListener) {
		ActionListener[] actionListeners = menuItem.getActionListeners();
		for (int i = 0; i < actionListeners.length; i++) {
			menuItem.removeActionListener(actionListeners[i]);
		}
		menuItem.addActionListener(actionListener);
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmAddUnitWaste() {
		if (pmAddUnitWaste == null) {
			pmAddUnitWaste = new JPopupMenu();
			pmAddUnitWaste.add(getMiAddUnitWasteFromContractImg());
			pmAddUnitWaste.add(getMiAddUnitWasteFromOther());
			pmAddUnitWaste.add(getMiAddUnitWasteFromCustomsBom());
		}

		return pmAddUnitWaste;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddUnitWasteFromContractImg() {
		if (miAddUnitWasteFromContractImg == null) {
			miAddUnitWasteFromContractImg = new JMenuItem();
			miAddUnitWasteFromContractImg.setText("从料件总表中新增成品单耗");
			miAddUnitWasteFromContractImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExg == null
									|| tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ContractExg c = (ContractExg) tableModelExg
									.getCurrentRow();
							List list = CommonQuery.getInstance()
									.getContractImgNoInContractBom(c);
							if (list != null && list.size() > 0) {
								list = contractAction
										.saveContractBomFromContractImg(
												new Request(CommonVars
														.getCurrUser()), c,
												list);
								tableModelBom.addRows(list);
								DgUnitWaste dg = new DgUnitWaste();
								dg.setTableModel(tableModelBom);
								dg.setTableModelExg(tableModelExg);
								dg.setContract(contract);
								dg.setDataState(DataState.EDIT);
								dg.setIsEditable(true);// 控制 商品名称和型号规格是否可修改
								dg.setVisible(true);
							}
						}
					});
		}
		return miAddUnitWasteFromContractImg;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddUnitWasteFromOther() {
		if (miAddUnitWasteFromOther == null) {
			miAddUnitWasteFromOther = new JMenuItem();
			miAddUnitWasteFromOther.setText("从商品资料中增加单耗");
			miAddUnitWasteFromOther
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModelExg == null
									|| tableModelExg.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请选择合同成品记录!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							ContractExg c = (ContractExg) tableModelExg
									.getCurrentRow();
							List list = new ArrayList();
							List lsResult = new ArrayList();
							boolean isContractEms = isContractEms();
							if (isContractEms) {
								contract = contractAction.findContractById(
										new Request(CommonVars.getCurrUser()),
										contract.getId());
								String dictPorEmsNo = contract.getCorrEmsNo();
								if (dictPorEmsNo == null
										|| "".equals(dictPorEmsNo.trim())) {
									List lsExingBcsDictPor = bcsDictPorAction
											.findExingBcsDictPorHead(new Request(
													CommonVars.getCurrUser(),
													true));
									if (lsExingBcsDictPor.size() <= 0) {
										JOptionPane
												.showMessageDialog(
														DgContract.this,
														"备案资料库料件没有资料",
														"提示",
														JOptionPane.INFORMATION_MESSAGE);
										return;
									} else if (lsExingBcsDictPor.size() == 1) {
										dictPorEmsNo = ((BcsDictPorHead) lsExingBcsDictPor
												.get(0)).getDictPorEmsNo();
									} else {
										BcsDictPorHead bcsDictPorHead = BcsClientHelper
												.getInstance()
												.findExingBcsDictPorHead(
														lsExingBcsDictPor);
										if (bcsDictPorHead == null) {
											return;
										}
										dictPorEmsNo = bcsDictPorHead
												.getDictPorEmsNo();
									}
								}
								list = BcsClientHelper.getInstance()
										.findBcsDictPorImgNotContractBom(
												c.getId(),
												contract.getCorrEmsNo());
								if (list == null || list.size() <= 0) {
									return;
								}
								if (contract.getCorrEmsNo() == null
										|| "".equals(contract.getCorrEmsNo()
												.trim())) {
									contract.setCorrEmsNo(dictPorEmsNo);
									contract = contractAction.saveContract(
											new Request(CommonVars
													.getCurrUser()), contract);
								}
								lsResult = contractAction
										.saveContractBomFromDictPorImg(
												new Request(CommonVars
														.getCurrUser()), c,
												list);
							} else {
								if (CommonVars.getContractFrom() != null) {
									if (CommonVars.getContractFrom()
											.equals("2")) {
										list = BcsClientHelper.getInstance()
												.getComplex();
										if (list == null || list.size() <= 0) {
											return;
										}
										lsResult = contractAction.saveComtractBomByComplex(
												new Request(CommonVars
														.getCurrUser()), c,
												list);
									} else if (CommonVars.getContractFrom()
											.equals("1")) {
										DgAddContractBomFromInnerMerge dg = new DgAddContractBomFromInnerMerge();
										dg.setContractExg(c);
										dg.setFromInnerMerge(true);
										dg.setVisible(true);
										lsResult = dg.getLsResult();
									} else if (CommonVars.getContractFrom()
											.equals("0")) {
										DgAddContractBomFromInnerMerge dg = new DgAddContractBomFromInnerMerge();
										dg.setContractExg(c);
										dg.setFromInnerMerge(false);
										dg.setVisible(true);
										lsResult = dg.getLsResult();
									}
								}
							}
							if (lsResult != null && lsResult.size() > 0) {
								tableModelBom.addRows(lsResult);
								DgUnitWaste dg = new DgUnitWaste();
								dg.setTableModel(tableModelBom);
								dg.setTableModelExg(tableModelExg);
								dg.setContract(contract);
								dg.setDataState(DataState.EDIT);
								dg.setIsEditable(true);// 控制 商品名称和型号规格是否可修改
								dg.setVisible(true);
							}
						}
					});
		}
		return miAddUnitWasteFromOther;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddUnitWaste() {
		if (btnAddUnitWaste == null) {
			btnAddUnitWaste = new JButton();
			btnAddUnitWaste.setText("新增单耗");
			btnAddUnitWaste.setPreferredSize(new Dimension(65, 30));
			btnAddUnitWaste
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							ContractExg exg = ((ContractExg) (((JTableListModel) tbExg
									.getModel()).getCurrentRow()));
							if (exg == null) {
								JOptionPane.showMessageDialog(DgContract.this,
										"请先添加成品 后再添加料件单耗！");
								return;
							}
							Component comp = (Component) e.getSource();
							getPmAddUnitWaste().show(comp, 0, comp.getHeight());
							// 当合同状态为正在变更，新增成品为新增状态时，按报关单耗可按,其他状态不可按
							// System.out.println("contract.equal():"+contract.getDeclareState().equals(DeclareState.CHANGING_EXE));
							// System.out.println("exg.equal():"+exg.getModifyMark().equals(ModifyMarkState.ADDED));
							// System.out.println("contract:"+contract.getDeclareState());
							// System.out.println("exg:"+exg.getModifyMark());
							if (contract.getDeclareState().equals(
									DeclareState.CHANGING_EXE)
									&& (exg.getModifyMark().equals(
											ModifyMarkState.UNCHANGE)
											|| exg.getModifyMark().equals(
													ModifyMarkState.MODIFIED) || exg
											.getModifyMark().equals(
													ModifyMarkState.DELETED)))
								miAddUnitWasteFromCustomsBom.setEnabled(false);
							else
								miAddUnitWasteFromCustomsBom.setEnabled(true);
						}
					});
		}
		return btnAddUnitWaste;
	}

	/**
	 * This method initializes jPopupMenuPrint
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenuPrint() {
		if (jPopupMenuPrint == null) {
			jPopupMenuPrint = new JPopupMenu();
			jPopupMenuPrint.setSize(new Dimension(110, 36));
			jPopupMenuPrint
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPopupMenuPrint.add(getMiCoverPrintExportExgNew());
			jPopupMenuPrint.add(getMiNonCoverPrintExportExgNew());
			jPopupMenuPrint.add(getMiCoverPrintImportImgNew());
			jPopupMenuPrint.add(getMiNonCoverPrintImportImgNew());
			jPopupMenuPrint.add(new JSeparator());
			jPopupMenuPrint.add(getMiCoverPrintExg());
			jPopupMenuPrint.add(getMiNonCoverPrintExg());
			jPopupMenuPrint.add(getMiCoverPrintImg());
			jPopupMenuPrint.add(getMiNonCoverPrintImg());
			jPopupMenuPrint.add(getMiCoverPrintUnitConsumption());
			jPopupMenuPrint.add(getMiNonCoverPrintUnitConsumption());
			jPopupMenuPrint.add(new JSeparator());
			jPopupMenuPrint.add(getMiPrintPutOnRecordContract());
			jPopupMenuPrint.add(getMiPrintPutOnRecordContarct2());
			jPopupMenuPrint.add(getMiPrintExportExgCharge());
			jPopupMenuPrint.add(getMiPrintImEmImgBalance());
			jPopupMenuPrint.add(getMiProcessingTradeUnitConsumption());
			jPopupMenuPrint.add(getMiPrintCustomClearanceContarct());
			jPopupMenuPrint.add(getMiPrintContractDomesticPurchaseBill());
		}
		return jPopupMenuPrint;
	}

	private JPopupMenu getPmenuPrintChang() {
		if (pmenuPrintChang == null) {
			pmenuPrintChang = new JPopupMenu();
			pmenuPrintChang.setSize(new Dimension(110, 36));
			pmenuPrintChang
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pmenuPrintChang.add(getMecuItemMiHead());
			pmenuPrintChang.add(getMecuItemMiNonHead());
			// pmenuPrintChang.add(getMenuItemMiProductChang());
			pmenuPrintChang.add(getMenuItemMiNonProductChang());
			// pmenuPrintChang.add(getMenuItemMiMaterialChang());
			pmenuPrintChang.add(getMenuItemMiNonMaterialChang());
			pmenuPrintChang.add(getMenuItemMiBomChang());
			pmenuPrintChang.add(getMenuItemMiNonBomChang());
		}
		return pmenuPrintChang;
	}

	/**
	 * 套打出口成品表（新）
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintExportExgNew() {
		if (miCoverPrintExportExgNew == null) {
			miCoverPrintExportExgNew = new JMenuItem();
			miCoverPrintExportExgNew.setText("套打出口成品表(新)");
			miCoverPrintExportExgNew.setSize(new Dimension(100, 30));
			miCoverPrintExportExgNew
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintExportExgNew(DgContract.this,
											contract, false);
						}
					});
		}
		return miCoverPrintExportExgNew;
	}

	/**
	 * This method initializes miNonCoverPrintExportExgNew
	 * 
	 * @return javax.swing.JMenuItem
	 */

	private JMenuItem getMiNonCoverPrintExportExgNew() {
		if (miNonCoverPrintExportExgNew == null) {
			miNonCoverPrintExportExgNew = new JMenuItem();
			miNonCoverPrintExportExgNew.setText("非套打出口成品表(新)");
			miNonCoverPrintExportExgNew.setSize(new Dimension(100, 30));
			miNonCoverPrintExportExgNew
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.nonCoverPrintExportExgNew(DgContract.this,
											contract, false);
						}
					});
		}
		return miNonCoverPrintExportExgNew;
	}

	/**
	 * This method initializes miCoverPrintImportImgNew
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintImportImgNew() {
		if (miCoverPrintImportImgNew == null) {
			miCoverPrintImportImgNew = new JMenuItem();
			miCoverPrintImportImgNew.setText("套打进口料件表(新)");
			miCoverPrintImportImgNew.setSize(new Dimension(100, 30));
			miCoverPrintImportImgNew
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintImportImgNew(DgContract.this,
											contract, false);
						}
					});
		}
		return miCoverPrintImportImgNew;
	}

	/**
	 * This method initializes miNonCoverPrintImportImgNew
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintImportImgNew() {
		if (miNonCoverPrintImportImgNew == null) {
			miNonCoverPrintImportImgNew = new JMenuItem();
			miNonCoverPrintImportImgNew.setText("非套打进口料件表(新)");
			miNonCoverPrintImportImgNew.setSize(new Dimension(100, 30));
			miNonCoverPrintImportImgNew
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.nonCoverPrintImportImgNew(DgContract.this,
											contract, false);
						}
					});
		}
		return miNonCoverPrintImportImgNew;
	}

	/*
	 * setText("打印加工贸易备案资料申请表"); setSize(new Dimension(100, 30));
	 */
	/**
	 * This method initializes miCoverPrintExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintExg() {
		if (miCoverPrintExg == null) {
			miCoverPrintExg = new JMenuItem();
			miCoverPrintExg.setText("套打成品表");
			miCoverPrintExg.setSize(new Dimension(100, 30));
			miCoverPrintExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().coverPrintExg(
									DgContract.this, contract);
						}
					});
		}
		return miCoverPrintExg;
	}

	/**
	 * This method initializes miNonCoverPrintExg
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintExg() {
		if (miNonCoverPrintExg == null) {
			miNonCoverPrintExg = new JMenuItem();
			miNonCoverPrintExg.setText("非套打成品表");
			miNonCoverPrintExg.setSize(new Dimension(100, 30));
			miNonCoverPrintExg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().nonCoverPrintExg(
									DgContract.this, contract);
						}
					});
		}
		return miNonCoverPrintExg;
	}

	/**
	 * 套打料件
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintImg() {
		if (miCoverPrintImg == null) {
			miCoverPrintImg = new JMenuItem();
			miCoverPrintImg.setText("套打料件表");
			miCoverPrintImg.setSize(new Dimension(100, 30));
			miCoverPrintImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().coverPrintImg(
									DgContract.this, contract);
						}
					});
		}
		return miCoverPrintImg;
	}

	/**
	 * 非套打料件表
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintImg() {
		if (miNonCoverPrintImg == null) {
			miNonCoverPrintImg = new JMenuItem();
			miNonCoverPrintImg.setText("非套打料件表");
			miNonCoverPrintImg.setSize(new Dimension(100, 30));
			miNonCoverPrintImg
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().nonCoverPrintImg(
									DgContract.this, contract);
						}
					});
		}
		return miNonCoverPrintImg;
	}

	/**
	 * This method initializes miCoverPrintUnitConsumption
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCoverPrintUnitConsumption() {
		if (miCoverPrintUnitConsumption == null) {
			miCoverPrintUnitConsumption = new JMenuItem();
			miCoverPrintUnitConsumption.setText("套打单耗表");
			miCoverPrintUnitConsumption.setSize(new Dimension(100, 30));
			miCoverPrintUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintUnitConsumption(DgContract.this,
											contract, false);
						}
					});
		}
		return miCoverPrintUnitConsumption;
	}

	/**
	 * This method initializes miNonCoverPrintUnitConsumption
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNonCoverPrintUnitConsumption() {
		if (miNonCoverPrintUnitConsumption == null) {
			miNonCoverPrintUnitConsumption = new JMenuItem();
			miNonCoverPrintUnitConsumption.setText("非套打单耗表");
			miNonCoverPrintUnitConsumption.setSize(new Dimension(100, 30));
			miNonCoverPrintUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.nonCoverPrintUnitConsumption(
											DgContract.this, contract, false);
						}
					});
		}
		return miNonCoverPrintUnitConsumption;
	}

	/**
	 * This method initializes miPrintPutOnRecordContract
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintPutOnRecordContract() {
		if (miPrintPutOnRecordContract == null) {
			miPrintPutOnRecordContract = new JMenuItem();
			miPrintPutOnRecordContract.setText("打印加工合同备案情况表");
			miPrintPutOnRecordContract.setSize(new Dimension(100, 30));
			miPrintPutOnRecordContract
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.printPutOnRecordContract(DgContract.this,
											contract);
						}
					});
		}
		return miPrintPutOnRecordContract;
	}

	/**
	 * This method initializes miPrintPutOnRecordContarct2
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintPutOnRecordContarct2() {
		if (miPrintPutOnRecordContarct2 == null) {
			miPrintPutOnRecordContarct2 = new JMenuItem();
			miPrintPutOnRecordContarct2.setText("打印预申报合同组成表");
			miPrintPutOnRecordContarct2.setSize(new Dimension(100, 30));
			miPrintPutOnRecordContarct2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.printPutOnRecordContract2(DgContract.this,
											contract);
						}
					});
		}
		return miPrintPutOnRecordContarct2;
	}

	/**
	 * This method initializes miPrintExportExgCharge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintExportExgCharge() {
		if (miPrintExportExgCharge == null) {
			miPrintExportExgCharge = new JMenuItem();
			miPrintExportExgCharge.setText("打印出口成品费用表");
			miPrintExportExgCharge.setSize(new Dimension(100, 30));
			miPrintExportExgCharge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().printExportExgCharge(
									DgContract.this, contract);
						}
					});
		}
		return miPrintExportExgCharge;
	}

	/**
	 * This method initializes miPrintImEmImgBalance
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintImEmImgBalance() {
		if (miPrintImEmImgBalance == null) {
			miPrintImEmImgBalance = new JMenuItem();
			miPrintImEmImgBalance.setText("打印料件进出平衡检查表");
			miPrintImEmImgBalance.setSize(new Dimension(100, 30));
			miPrintImEmImgBalance
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().printImEmImgBalance(
									DgContract.this, contract);
						}
					});
		}
		return miPrintImEmImgBalance;
	}

	/**
	 * This method initializes miProcessingTradeUnitConsumption
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiProcessingTradeUnitConsumption() {
		if (miProcessingTradeUnitConsumption == null) {
			miProcessingTradeUnitConsumption = new JMenuItem();
			miProcessingTradeUnitConsumption.setText("打印加工贸易单耗申报表");
			miProcessingTradeUnitConsumption.setSize(new Dimension(100, 30));
			miProcessingTradeUnitConsumption
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.processingTradeUnitConsumption(
											DgContract.this, contract);
						}
					});
		}
		return miProcessingTradeUnitConsumption;
	}

	/**
	 * This method initializes miPrintCustomClearanceContarct
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintCustomClearanceContarct() {
		if (miPrintCustomClearanceContarct == null) {
			miPrintCustomClearanceContarct = new JMenuItem();
			miPrintCustomClearanceContarct.setText("打印通关手册表");
			miPrintCustomClearanceContarct.setSize(new Dimension(100, 30));
			miPrintCustomClearanceContarct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.printCustomClearanceContarct(
											DgContract.this, contract);
						}
					});
		}
		return miPrintCustomClearanceContarct;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbEquipMode() {
		if (cbbEquipMode == null) {
			cbbEquipMode = new JComboBox();
			cbbEquipMode.setBounds(new Rectangle(320, 419, 127, 20));
		}
		return cbbEquipMode;
	}

	/**
	 * This method initializes miPrintContractDomesticPurchaseBill
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiPrintContractDomesticPurchaseBill() {
		if (miPrintContractDomesticPurchaseBill == null) {
			miPrintContractDomesticPurchaseBill = new JMenuItem();
			miPrintContractDomesticPurchaseBill.setText("打印合同国内购料清单表");
			miPrintContractDomesticPurchaseBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// printPyShopList();
							BcsClientHelper.getInstance()
									.printContractDomesticPurchaseBill(
											DgContract.this, contract);
						}
					});
		}
		return miPrintContractDomesticPurchaseBill;
	}

	/**
	 * This method initializes btnPrintChang
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrintChang() {
		if (btnPrintChang == null) {
			btnPrintChang = new JButton();
			btnPrintChang.setText("打印变更表");
			btnPrintChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmenuPrintChang().show(btnPrintChang, 0,
									btnPrintChang.getHeight());
						}
					});
		}
		return btnPrintChang;
	}

	/**
	 * This method initializes MenuItemProductChang
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemMiProductChang() {
		if (menuItemMiProductChang == null) {
			menuItemMiProductChang = new JMenuItem();
			menuItemMiProductChang.setText("套打成品变更表");
			menuItemMiProductChang.setSize(new Dimension(100, 30));
			menuItemMiProductChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintExportChange(DgContract.this,
											contract);
						}
					});
		}
		return menuItemMiProductChang;
	}

	/**
	 * This method initializes menuItemMiNonProductChang
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemMiNonProductChang() {
		if (menuItemMiNonProductChang == null) {
			menuItemMiNonProductChang = new JMenuItem();
			menuItemMiNonProductChang.setText("非套打成品变更表");
			menuItemMiNonProductChang.setSize(new Dimension(100, 30));
			menuItemMiNonProductChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintExportChange(DgContract.this,
											contract);
						}
					});
		}
		return menuItemMiNonProductChang;
	}

	/**
	 * This method initializes menuItemMiMaterialChang
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemMiMaterialChang() {
		if (menuItemMiMaterialChang == null) {
			menuItemMiMaterialChang = new JMenuItem();
			menuItemMiMaterialChang.setText("套打料件变更表");
			menuItemMiMaterialChang.setSize(new Dimension(100, 30));
			menuItemMiMaterialChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintImportChange(DgContract.this,
											contract);
						}
					});
		}
		return menuItemMiMaterialChang;
	}

	/**
	 * This method initializes menuItemMiNonMaterialChang
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemMiNonMaterialChang() {
		if (menuItemMiNonMaterialChang == null) {
			menuItemMiNonMaterialChang = new JMenuItem();
			menuItemMiNonMaterialChang.setText("非套打料件变更表");
			menuItemMiNonMaterialChang.setSize(new Dimension(100, 30));
			menuItemMiNonMaterialChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintImportChange(DgContract.this,
											contract);
						}
					});
		}
		return menuItemMiNonMaterialChang;
	}

	/**
	 * This method initializes menuItemMiBomChang
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemMiBomChang() {
		if (menuItemMiBomChang == null) {
			menuItemMiBomChang = new JMenuItem();
			menuItemMiBomChang.setText("套打单耗变更表");
			menuItemMiBomChang.setSize(new Dimension(100, 30));
			menuItemMiBomChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.coverPrintUnitConsumption(DgContract.this,
											contract, true);
						}
					});
		}
		return menuItemMiBomChang;
	}

	/**
	 * This method initializes menuItemMiNonBomChang
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMenuItemMiNonBomChang() {
		if (menuItemMiNonBomChang == null) {
			menuItemMiNonBomChang = new JMenuItem();
			menuItemMiNonBomChang.setText("非套打单耗变更表");
			menuItemMiNonBomChang.setSize(new Dimension(100, 30));
			menuItemMiNonBomChang
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance()
									.nonCoverPrintUnitConsumption(
											DgContract.this, contract, true);
						}
					});
		}
		return menuItemMiNonBomChang;
	}

	/**
	 * This method initializes mecuItemMiHead
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMecuItemMiHead() {
		if (mecuItemMiHead == null) {
			mecuItemMiHead = new JMenuItem();
			mecuItemMiHead.setText("套打通关手册表头");
			mecuItemMiHead
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().printContractHead(
									DgContract.this, contract, true);
						}
					});
		}
		return mecuItemMiHead;
	}

	/**
	 * This method initializes mecuItemMiNonHead
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMecuItemMiNonHead() {
		if (mecuItemMiNonHead == null) {
			mecuItemMiNonHead = new JMenuItem();
			mecuItemMiNonHead.setText("非套打通关手册表头");
			mecuItemMiNonHead
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							BcsClientHelper.getInstance().printContractHead(
									DgContract.this, contract, false);
						}
					});
		}
		return mecuItemMiNonHead;
	}

	/**
	 * 打印套打核销料件表
	 */
	private void printPyShopList() {
		try {
			ContractAction contractAction = (ContractAction) CommonVars
					.getApplicationContext().getBean("contractAction");
			List list = null;
			List list2 = null;

			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractDomesticPurchaseBill(
						new Request(CommonVars.getCurrUser()), parentId);

				list2 = contractAction.setContractDomesticPurchaseBill(
						new Request(CommonVars.getCurrUser()), parentId);
			}

			CustomReportDataSource ds = new CustomReportDataSource(list2);
			Map<String, Object> parameters = new HashMap<String, Object>();

			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/PYBuyInner.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes cbbBank
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBank() {
		if (cbbBank == null) {
			cbbBank = new JComboBox();
			cbbBank.setBounds(new Rectangle(320, 444, 127, 20));
		}
		return cbbBank;
	}

	/**
	 * hw 获取报关单耗
	 */
	public void getCustomsBom() {
		JTableListModel tableModel = (JTableListModel) tbExg.getModel();
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(DgContract.this, "请选择合同成品记录!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List list = new ArrayList();
		ContractExg exg = ((ContractExg) tableModel.getCurrentRow());
		list = this.bcsInnerMergeAction.findCustomsBomDetailByExg(new Request(
				CommonVars.getCurrUser()), exg);
		if (list.size() == 0) {
			// List list2=
			JOptionPane.showMessageDialog(DgContract.this, "报关单耗不存在", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// 删除原来成品bom
		deleteAllBom();
		// 备案资料库编号
		String corrEmsNo = exg.getContract().getCorrEmsNo();
		// 合同id
		String id = exg.getContract().getId();
		String str = "";
		List lists = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			BcsCustomsBomDetail bsb = (BcsCustomsBomDetail) list.get(i);
			int tenSeqNum = bsb.getTenSeqNum();
			lists = bcsDictPorAction
					.findBcsDictPorImgBycorrEmsNoAndtenSeqNum(new Request(
							CommonVars.getCurrUser()), corrEmsNo, tenSeqNum);
			if (lists.size() == 0) {
				str += "您所选择的归并序号为" + tenSeqNum + "的料件不在备案资料库内！不能添加" + "\n";
			}
		}

		if (lists.size() == 0) {
			System.out.println("str=" + str);
			new DgMessage("导入信息", str);
			return;
		}
		List bomlists = this.contractAction.writeToImgAndBomTable(new Request(
				CommonVars.getCurrUser()), id, exg, list);

		initBomTable(bomlists);
	}

	/**
	 * This method initializes miAddUnitWasteFromCustomsBom
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiAddUnitWasteFromCustomsBom() {

		if (miAddUnitWasteFromCustomsBom == null) {
			miAddUnitWasteFromCustomsBom = new JMenuItem();
			miAddUnitWasteFromCustomsBom.setText("从报关单耗中增加");
			miAddUnitWasteFromCustomsBom
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getCustomsBom();
						}
					});
		}

		return miAddUnitWasteFromCustomsBom;
	}

	/**
	 * 删除bom
	 */
	public void deleteBomImg(List deleteList) {
		// List deleteList = tableModelBom.getCurrentRows();
		Map<Integer, List<ContractBom>> map = contractAction.deleteContractBom(
				new Request(CommonVars.getCurrUser()), deleteList);
		List lsDelete = map.get(DeleteResultMark.DELETE);
		if (lsDelete != null && lsDelete.size() > 0) {
			tableModelBom.deleteRows(lsDelete);
		}
		List lsUpdate = map.get(DeleteResultMark.UPDATE);
		if (lsUpdate != null && lsUpdate.size() > 0) {
			tableModelBom.updateRows(lsUpdate);
		}
		if (deleteList.size() > 0) {
			ContractExg contractExg = ((ContractBom) deleteList.get(0))
					.getContractExg();
			if (contractExg != null) {
				contractExg = (ContractExg) contractAction.load(
						ContractExg.class, contractExg.getId());
				if (contractExg != null) {
					tableModelExg.updateRow(contractExg);
				}
			}
		}
	}

	/**
	 * This method initializes pn4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn4() {
		if (pn4 == null) {
			pn4 = new JPanel();
			pn4.setLayout(null);
			pn4.setName("");
			pn4.add(getJPanel1(), null);
		}
		return pn4;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel50 = new JLabel();
			jLabel50.setBounds(new Rectangle(188, 223, 279, 18));
			jLabel50.setForeground(Color.blue);
			jLabel50.setText("进料合同：口岸费=进出口总价*计算比例*汇率");
			jLabel46 = new JLabel();
			jLabel46.setBounds(new Rectangle(188, 198, 276, 18));
			jLabel46.setFont(new Font("Dialog", Font.PLAIN, 12));
			jLabel46.setForeground(Color.blue);
			jLabel46.setText("来料合同：口岸费=加工费总价*计算比例*汇率");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new Rectangle(335, 117, 13, 18));
			jLabel14.setText("￥");
			lbPortMoney = new JLabel();
			lbPortMoney.setBounds(new Rectangle(283, 117, 42, 18));
			lbPortMoney.setText("口岸费");
			lbCountScale = new JLabel();
			lbCountScale.setBounds(new Rectangle(282, 72, 63, 18));
			lbCountScale.setText("计算比例");
			lbProcessTotalPrice = new JLabel();
			lbProcessTotalPrice.setBounds(new Rectangle(51, 73, 68, 18));
			lbProcessTotalPrice.setText("加工费总价");
			lbExpTotal = new JLabel();
			lbExpTotal.setBounds(new Rectangle(282, 30, 60, 18));
			lbExpTotal.setText("出口总值");
			lbImpTotal = new JLabel();
			lbImpTotal.setText("进口总值");
			lbImpTotal.setBounds(new Rectangle(51, 30, 65, 20));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(30, 19, 547, 265));
			jPanel1.setBorder(BorderFactory
					.createTitledBorder(
							null,
							"\u53e3\u5cb8\u57fa\u7840\u8bbe\u65bd\u5efa\u8bbe\u57fa\u91d1",
							TitledBorder.DEFAULT_JUSTIFICATION,
							TitledBorder.DEFAULT_POSITION, new Font("Dialog",
									Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel1.add(lbImpTotal, null);
			jPanel1.add(getTfImptotal(), null);
			jPanel1.add(lbExpTotal, null);
			jPanel1.add(getTfExpTotal(), null);
			jPanel1.add(lbProcessTotalPrice, null);
			jPanel1.add(getTfProcessTotalPrice(), null);
			jPanel1.add(lbCountScale, null);
			jPanel1.add(getTfCountScale(), null);
			jPanel1.add(lbPortMoney, null);
			jPanel1.add(getTfPortMoney(), null);
			jPanel1.add(getTfRecount(), null);
			jPanel1.add(jLabel14, null);
			jPanel1.add(jLabel46, null);
			jPanel1.add(jLabel50, null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes tfImptotal
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfImptotal() {
		if (tfImptotal == null) {
			tfImptotal = new JTextField();
			tfImptotal.setBounds(new Rectangle(120, 29, 123, 22));
		}
		return tfImptotal;
	}

	/**
	 * This method initializes tfExpTotal
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfExpTotal() {
		if (tfExpTotal == null) {
			tfExpTotal = new JTextField();
			tfExpTotal.setBounds(new Rectangle(348, 29, 102, 22));
		}
		return tfExpTotal;
	}

	/**
	 * This method initializes tfProcessTotalPrice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfProcessTotalPrice() {
		if (tfProcessTotalPrice == null) {
			tfProcessTotalPrice = new JTextField();
			tfProcessTotalPrice.setBounds(new Rectangle(120, 70, 123, 22));
		}
		return tfProcessTotalPrice;
	}

	/**
	 * This method initializes tfCountScale
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCountScale() {
		if (tfCountScale == null) {
			tfCountScale = new JTextField();
			tfCountScale.setBounds(new Rectangle(348, 71, 102, 22));
		}
		return tfCountScale;
	}

	/**
	 * This method initializes tfPortMoney
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfPortMoney() {
		if (tfPortMoney == null) {
			tfPortMoney = new JTextField();
			tfPortMoney.setBounds(new Rectangle(348, 115, 102, 22));
		}
		return tfPortMoney;
	}

	/**
	 * This method initializes tfRecount
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getTfRecount() {
		if (tfRecount == null) {
			tfRecount = new JButton();
			tfRecount.setBounds(new Rectangle(349, 160, 101, 22));
			tfRecount.setText("计算");
			tfRecount.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					contract = contractAction.findContractById(new Request(
							CommonVars.getCurrUser()), contract.getId());
					// 获得币制栏内容
					String curr = cbbCurr.getSelectedItem() + "";
					// 本币汇率
					Double currrate = 1.0;
					// 口岸费
					Double portMoney = 0.0;
					if (null == curr || curr.trim().equals("")) {
						JOptionPane
								.showMessageDialog(DgContract.this, "请输入币制!",
										"提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						if (!curr.trim().equals("CNY")) {
							List list = contractAction.findCurrRateByCurr(
									new Request(CommonVars.getCurrUser()),
									curr.trim());
							if (list.size() == 0) {
								JOptionPane.showMessageDialog(DgContract.this,
										"没有找到本币对人民币汇率!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							} else {
								CurrRate cr = (CurrRate) list.get(list.size() - 1);
								currrate = cr.getRate();
							}
						}

						// 口岸费计算
						if (null == cbbEmsType.getSelectedItem()
								|| cbbEmsType.getSelectedItem().equals("")) {
							JOptionPane.showMessageDialog(DgContract.this,
									"请输入合同性质!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							String s = cbbEmsType.getSelectedItem() + "";
							if (s.trim().equals("来料加工")
									|| s.trim().equals("纸质手册电子化来料手册")) {
								portMoney = Double.valueOf(tfProcessTotalPrice
										.getText())
										* Double.valueOf(tfCountScale.getText())
										* currrate;
								tfPortMoney.setText(format(portMoney));
								countScale = Double.valueOf(tfCountScale
										.getText());
								contract.setCountScale(countScale);
								System.out.println("000000000countScale"
										+ Double.valueOf(tfCountScale.getText()));
								contract = contractAction.saveContract(
										new Request(CommonVars.getCurrUser()),
										contract);
							} else if (s.trim().equals("进料加工")
									|| s.trim().equals("纸质手册电子化进料手册")) {
								portMoney = (Double.valueOf(tfImptotal
										.getText()) + Double.valueOf(tfExpTotal
										.getText()))
										* Double.valueOf(tfCountScale.getText())
										* currrate;
								System.out.println("portMoney=" + portMoney);
								portMoney = CommonUtils.getDoubleByDigit(
										portMoney, 2);
								System.out.println("portMoney=" + portMoney);
								tfPortMoney.setText(format(portMoney));
								countScale = Double.valueOf(tfCountScale
										.getText());
								System.out.println("0000000000countScale"
										+ Double.valueOf(tfCountScale.getText()));
								contract.setCountScale(countScale);
								contract = contractAction.saveContract(
										new Request(CommonVars.getCurrUser()),
										contract);
							}
						}
					}
				}
			});

		}
		return tfRecount;
	}

	public String format(Double dou) {

		// if (nf == null) {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(30);
		// nf.setMinimumFractionDigits(pointLen);
		nf.setMaximumIntegerDigits(99);
		nf.setGroupingUsed(false);
		nf.setRoundingMode(RoundingMode.HALF_UP);
		// }
		// System.out.println("-------------->>>>>" + nf.format(dou == null ?
		// 0.00 : dou.doubleValue()));
		return nf.format(dou == null ? 0.00 : dou.doubleValue());
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(getRbUnitWare(), null);
			jPanel3.add(getRbWare(), null);
			jPanel3.add(getBtnRound(), null);
			jPanel3.add(getTfRound(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes rbUnitWare
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbUnitWare() {
		if (rbUnitWare == null) {
			rbUnitWare = new JRadioButton();
			rbUnitWare.setBounds(new Rectangle(6, 1, 69, 14));
			rbUnitWare.setText("净耗");
			rbUnitWare.setSelected(true);
		}
		return rbUnitWare;
	}

	/**
	 * This method initializes rbWare
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbWare() {
		if (rbWare == null) {
			rbWare = new JRadioButton();
			rbWare.setBounds(new Rectangle(6, 16, 67, 14));
			rbWare.setText("损耗率");
		}
		return rbWare;
	}

	/**
	 * This method initializes btnRound
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRound() {
		if (btnRound == null) {
			btnRound = new JButton();
			btnRound.setBounds(new Rectangle(151, 2, 95, 28));
			btnRound.setText("∑取小数");
			btnRound.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelBom == null) {
						return;
					}
					if (tableModelBom.getList().size() < 1) {
						return;
					}
					String message = "";
					int para = 0;
					if (rbUnitWare.isSelected() && rbWare.isSelected()) {
						message = "【净耗】【损耗率】";
						para = 1;
					} else if (rbUnitWare.isSelected()) {
						message = "【净耗】";
						para = 2;
					} else if (rbWare.isSelected()) {
						message = "【损耗率】";
						para = 3;
					}
					if (message.length() > 0) {
						if (JOptionPane.showConfirmDialog(DgContract.this,
								"确定把" + message + "取小数吗?", "提示",
								JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						roundUnitWareAndWare(para);
					}
				}
			});
		}
		return btnRound;
	}

	/**
	 * 转抄单耗 弹出菜单
	 * 
	 * @return
	 */
	private JPopupMenu getCopyBOM() {

		if (copyBOM == null) {

			copyBOM = new JPopupMenu();

			copyBOM.add(getCurrContractCopyBOM());

			copyBOM.add(getOtherCopyBOM());

		}

		return copyBOM;

	}

	/**
	 * 初始化 本合同转抄单耗
	 * 
	 * @return
	 */
	private JMenuItem getCurrContractCopyBOM() {

		if (currContractCopyBOM == null) {

			currContractCopyBOM = new JMenuItem("本合同转抄");

			currContractCopyBOM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					// 转抄
					copyBOMFromContractToCurrContractExg(true);

				}
			});

		}

		return currContractCopyBOM;
	}

	/**
	 * 初始化其他合同转抄
	 * 
	 * @return
	 */
	private JMenuItem getOtherCopyBOM() {

		if (otherCopyBOM == null) {

			otherCopyBOM = new JMenuItem("其他合同转抄");

			otherCopyBOM.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					// 转抄
					copyBOMFromContractToCurrContractExg(false);

				}
			});

		}

		return otherCopyBOM;
	}

	/**
	 * 当前合同成品 转抄单耗
	 */
	private void copyBOMFromContractToCurrContractExg(
			boolean isCurrContractCopyBom) {

		// 这是 合同成品 需要转抄到的 对象 当前行 所有数据
		ContractExg to = (ContractExg) tableModelExg.getCurrentRow();

		// 获取这个 合同成品的 -备案资料库--- 编号
		String corrEmsNo = to.getContract().getCorrEmsNo();

		// 获取当前合同的ID
		String contractId = to.getContract().getId();

		// 查询这个 备案序号 所对应 的成品 单耗
		ContractExg from = null;

		// 用于提示 本合同没有所对应的 成品备案序号
		String tips = null;

		// 判断 是否 本合同转抄
		if (isCurrContractCopyBom) {

			DgCurrContractCopyBOM dgCurrContractCopyBOM = new DgCurrContractCopyBOM();

			dgCurrContractCopyBOM.setVisible(true);

			// 例如这里是传过来的 备案序号
			String seqNum = dgCurrContractCopyBOM.getSeqNum();

			tips = seqNum;

			if (seqNum != null) {

				// 根据 当前合同 备案序号(成品序号) 查询 合同成品
				from = contractAction.findCurrContractExgByCredenceNo(
						new Request(CommonVars.getCurrUser()), contractId,
						corrEmsNo, seqNum);

				// 如果出现 转抄相同 的合同成品
				if (from.getId().equals(to.getId())) {

					JOptionPane.showMessageDialog(DgContract.this,
							"您所转抄的当前合同成品的序号与所输入的序号相同，请另行选择合适的合同成品");

					return;

				}

			} else {

				return;
			}

			// 从其他合同转抄
		} else {

			// 数据源 用于给 DgCommonQueryContract 这个的来源数据调用
			List dataSource = new ArrayList();

			// 当前所有 的 成品 加入到这个数据源里面
			dataSource.addAll(tableModelExg.getList());

			// 删除 这个需要转抄单耗的对象 在这个数据源里的
			dataSource.remove(to);

			// 获取 从其他合同 转抄过来的 合同成品 这是 "from"
			from = BcsClientHelper.getInstance().getContractExgForCopy(
					dataSource, corrEmsNo);

		}

		// 如果为空直接就返回
		if (from == null) {

			// if (tips != null) {
			//
			// JOptionPane.showMessageDialog(DgContract.this,
			// "本合同没有此成品备案序号 : " + tips + ",请检查");
			//
			// } else {
			//
			// return;
			// }

			return;
		}

		// 这个 被转抄的 合同成品 它的 ID 作为 父ID
		String parentId = from.getId();

		// 单耗 列表
		List listBom = new ArrayList();

		if (parentId != null) {

			// 获取 单耗列表 -=-=-=-= 根据 这个 被转抄的合同成品 ID 来查询
			listBom = contractAction.findContractBomByParentId(new Request(
					CommonVars.getCurrUser()), parentId);

			if (listBom.size() == 0 || listBom == null) {

				JOptionPane.showMessageDialog(DgContract.this,
						"此成品没有对应的单耗,请检查所选成品是否包含单耗信息!");

				return;

			}

		}

		// 料件 列表
		List listImg = new ArrayList();

		if (parentId != null) {

			// 被转抄合同的ID
			String Id = from.getContract().getId();

			// 获取 料件列表 -=-=-=-= 根据 这个 被转抄的合同成品 ID 来查询
			listImg = contractAction.findContractImgByParentId(new Request(
					CommonVars.getCurrUser()), Id);
		}

		// 用于 存放 料件 数据
		Map map = new HashMap<Integer, Integer>();

		for (int i = 0; i < listImg.size(); i++) {

			// 合同料件 ------ 迭代获取
			ContractImg img = (ContractImg) listImg.get(i);

			// 以 料件的 序号 作为 key : 料件的 记录号为 value 存贮在 这个map
			map.put(img.getSeqNum(), img.getCredenceNo());

		}

		for (int i = 0; i < listBom.size(); i++) {

			// 合同 单耗
			ContractBom img = (ContractBom) listBom.get(i);

			// 根据料件合同的总表序号
			if (map.get(img.getContractImgSeqNum()) != null) {

				// 设置 料件的记录号
				img.setImgCredenceNo(Integer.parseInt(map.get(
						img.getContractImgSeqNum()).toString()));

			}
		}

		// 保存 合同单耗 操作 这个 单耗列表 已保存 有记录号
		contractAction.saveContractBom(new Request(CommonVars.getCurrUser()),
				listBom);

		if (JOptionPane.showConfirmDialog(DgContract.this,
				"确定要转抄单耗吗？如转抄失败请【补充】单耗表中的料件【备案序号】！", "提示",
				JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
			return;
		}

		/*
		 * 这里是开始转抄
		 */
		contractAction.copyContractUnitWaste(
				new Request(CommonVars.getCurrUser()), from, to);

		showBomData();

		// 获取 已完成 转抄单耗的合同成品
		to = (ContractExg) contractAction.load(ContractExg.class, to.getId());

		if (to != null) {
			tableModelExg.updateRow(to);
		}

	}

	/**
	 * This method initializes tfRound
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfRound() {
		if (tfRound == null) {
			tfRound = new JTextField();
			tfRound.setBounds(new Rectangle(75, 3, 75, 25));
			tfRound.setText("0.######");
		}
		return tfRound;
	}

	/**
	 * 单耗与损耗率小数位处理 int para 处理字段
	 */
	private void roundUnitWareAndWare(int para) {
		if (tableModelBom == null) {
			return;
		}
		if (tableModelBom.getList().size() < 1) {
			return;
		}
		List list = tableModelBom.getCurrentRows();
		for (int i = 0; i < 1; i++) {
			ContractBom bom = (ContractBom) list.get(i);
			tableModelBom.updateRow(bom);
			String lengthStr = tfRound.getText().trim();
			int length = lengthStr.length() - lengthStr.lastIndexOf(".") - 1;
			switch (para) {
			case 1:
				bom.setUnitWaste(CommonUtils.getDoubleByDigit(
						Double.valueOf(bom.getUnitWaste()), length));
				bom.setWaste(CommonUtils.getDoubleByDigit(
						Double.valueOf(bom.getWaste()), length));
				break;
			case 2:
				bom.setUnitWaste(CommonUtils.getDoubleByDigit(
						Double.valueOf(bom.getUnitWaste()), length));

				break;
			case 3:
				bom.setWaste(CommonUtils.getDoubleByDigit(
						Double.valueOf(bom.getWaste()), length));
				break;
			}
			bom = contractAction.saveContractBom(
					new Request(CommonVars.getCurrUser()), bom);
			tableModelBom.updateRow(bom);
		}
	}
} // @jve:decl-index=0:visual-constraint="16,46"

class DgCurrContractCopyBOM extends JDialogBase {

	private JButton sure;

	private JButton cancel;

	private JPanel jContentPane;

	private JTextField number;

	private JLabel numberLabel;

	// 备案序号 (记录号)
	private String seqNum;

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public DgCurrContractCopyBOM() {

		initialize();

	}

	private void initialize() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setTitle("本合同转抄");

		setSize(400, 260);

		setContentPane(getJContentPane());

	}

	private JPanel getJContentPane() {

		if (jContentPane == null) {

			jContentPane = new JPanel();

			jContentPane.setLayout(null);

			jContentPane.add(getNumberLabel());

			jContentPane.add(getNumber());

			jContentPane.add(getSure());

			jContentPane.add(getCancel());

		}

		return jContentPane;

	}

	private JTextField getNumber() {

		if (number == null) {

			number = new JTextField();

			number.setColumns(10);

			number.setBounds(205, 64, 75, 21);

		}

		return number;

	}

	private JButton getSure() {

		if (sure == null) {

			sure = new JButton("确定");

			sure.setBounds(77, 138, 75, 25);

			sure.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					String no = checkNumber(number.getText());

					if (no.equals("")) {

						return;

					}

					DgCurrContractCopyBOM.this.setSeqNum(no);

					DgCurrContractCopyBOM.this.dispose();

				}
			});

		}

		return sure;

	}

	private JButton getCancel() {

		if (cancel == null) {

			cancel = new JButton("取消");

			cancel.setBounds(205, 138, 75, 25);

			cancel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					DgCurrContractCopyBOM.this.dispose();

				}
			});

		}

		return cancel;

	}

	private JLabel getNumberLabel() {

		if (numberLabel == null) {

			numberLabel = new JLabel("从成品序号转入：");

			numberLabel.setBounds(77, 58, 118, 32);
		}

		return numberLabel;

	}

	private String checkNumber(String text) {

		if (StringUtils.isNotBlank(text) && StringUtils.isNumeric(text)) {

			return text;

		}

		return "";

	}

}
