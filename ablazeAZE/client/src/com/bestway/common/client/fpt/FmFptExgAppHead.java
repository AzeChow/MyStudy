package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingWorker;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcs.client.verification.factoryanalyse.DgImportDataIExg;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFStockExg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.SwingWorkerTask;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.District;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.report.CustomsEnvelopSubReportDataSource;
import com.bestway.common.constant.AppClass;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.PrintFptAppHead;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.ui.message.JMessage;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import com.bestway.util.RegexUtil;

/***
 * by 2009-1-10 lm checked
 * 
 * @author Administrator
 * 
 */
public class FmFptExgAppHead extends FmCommon {

	private JPanel contentPn = null;

	private JToolBar tb = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JTable tb1 = null;

	private JScrollPane spn1 = null;
	private JScrollPane spn2 = null;

	private JPopupMenu pmPrint = null;

	private JMenuItem miOverprintNotPrintCustomsEnvelopNo = null;

	private JMenuItem miOverprintPrintCustomsEnvelopNo = null;

	private JMenuItem miOverprintPrintCustomsEnvelopNoForOld = null;

	private JMenuItem miNotOverprintCustomsEnvelopReport = null;

	private JMenuItem miTransferDescriptionRegister = null;

	private FptMessageAction fptMessageAction = null;

	/**
	 * 套打不打印关封号
	 */
	private final static int OVERPRINT_NOT_PRINT_CUSTOMS_ENVELOP_NO = 1;

	/**
	 * 套打打印关封号
	 */
	private final static int OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO = 2;

	/**
	 * 非套打打印关封
	 */
	private final static int NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT = 3;

	/**
	 * 套打打印关封号ForOld
	 */
	private final static int OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO_ForOld = 5;

	/**
	 * 关封编辑注册
	 */
	private final static int TRANSFER_DESCRIPTION_REGISTER = 4;

	// private FptAppHead fptAppHead = null;

	private List list = null; // @jve:decl-index=0:

	private JTableListModel tableMode1 = null;
	private JTableListModel tableMode2 = null;
	/** 是否导入商品 */
	private boolean isImportGoods = true;
	/** 是否是第一时间 */
	private boolean isFirstTime = true;
	/** 关封报表 */
	private List subReportData = null;

	private JButton btnRefresh = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JButton btnApply = null;

	private JButton btnChanging = null;

	private JButton btnBrowse = null;

	private JButton btnPutOnRecord = null;

	private JButton btnProcess = null;

	private JButton btnCopy = null;

	private JMenuItem miAllCopy = null;

	private JMenuItem miHead = null;

	private JMenuItem miHeadAndItems = null;

	private JPopupMenu pmCopyData = null;

	private JTabbedPane tabPn = null;

	private JPopupMenu jPopupMenu = null; // @jve:decl-index=0:visual-constraint="780,100"

	private JMenuItem jMenuItem = null; // @jve:decl-index=0:visual-constraint="823,56"

	private JMenuItem jMenuItem1 = null; // @jve:decl-index=0:visual-constraint="843,89"

	private JPanel pn1 = null;

	private JToolBar tbBar = null;

	private JPanel pn2 = null;

	private JLabel lb = null;

	private JComboBox cbbScmCoc = null;

	private JButton btnSearch = null;

	private JButton btnResend = null;

	private JButton btnCollated = null;

	private JCheckBox cbCollated = null;

	private JButton btnCanCollated = null;

	private JTree jTree = null; // @jve:decl-index=0:visual-constraint="891,55"

	private JButton btnImport = null;

	private JButton btnDownloadExcel = null;
	private JButton btnReceipt;
	private JCalendarComboBox cbbBeginDate;
	private JCalendarComboBox cbbEndDate;
	private JTable tb2;
	private JButton btnCancel;
	private Request request;
	private JButton btnExcelImport;

	/**
	 * This method initializes
	 * 
	 */
	public FmFptExgAppHead() {
		super();
		request = new Request(CommonVars.getCurrUser());
		manualDecleareAction = (ManualDeclareAction) CommonVars.getApplicationContext().getBean(
				"manualdeclearAction");
		fptMessageAction = (FptMessageAction) CommonVars.getApplicationContext().getBean(
				"fptMessageAction");
		fptManageAction.permissionCheckAppHeadGlance(new Request(CommonVars.getCurrUser()));
		initialize();
		initUIComponents();
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(new Date());
	}

	/***
	 * 设置版本号
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			// String inOutFlag = getInOutFlag();
			// List list = fptManageAction.findFptAppHeadByInOutFlag(new
			// Request(
			// CommonVars.getCurrUser()), FptInOutFlag.OUT);
			ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
			List list = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(
					new Request(CommonVars.getCurrUser()), scmCoc, null, null, FptInOutFlag.OUT,
					false, false);
			initTable(list, tb1);
		}
		super.setVisible(isFlag);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getContentPn());
		this.setSize(868, 476);
		this.setTitle("转出申请表");
	}

	/**
	 * This method initializes contentPn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getContentPn() {
		if (contentPn == null) {
			contentPn = new JPanel();
			contentPn.setLayout(new BorderLayout());
			contentPn.add(getTb(), java.awt.BorderLayout.NORTH);
			contentPn.add(getPn1(), BorderLayout.CENTER);
		}
		return contentPn;
	}

	/**
	 * This method initializes tb
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTb() {
		if (tb == null) {
			tb = new JToolBar();
			tb.setFloatable(true);
			tb.add(getBtnAdd());
			tb.add(getBtnEdit());
			tb.add(getBtnDelete());
			tb.add(getBtnBrowse());
			tb.add(getBtnPutOnRecord());
			tb.add(getBtnApply());
			tb.add(getBtnResend());
			tb.add(getBtnProcess());
			tb.add(getBtnReceipt());
			tb.add(getBtnChanging());
			tb.add(getBtnCopy());
			tb.add(getBtnCollated());
			tb.add(getBtnCanCollated());
			tb.add(getBtnCancel());
			tb.add(getBtnRefresh());
			tb.add(getBtnImport());
			tb.add(getBtnExcelImport());
			tb.add(getBtnDownloadExcel());
			tb.add(getBtnPrint());
			tb.add(getBtnExit());
		}
		return tb;
	}

	/**
	 * This method initializes pmPrint
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmPrint() {
		if (pmPrint == null) {
			pmPrint = new JPopupMenu();
			pmPrint.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			/**
			 * pmPrint.add(getMiOverprintPrintCustomsEnvelopNoForOld());
			 * 
			 * Separator separator0 = new Separator();
			 * separator0.setForeground(Color.gray);
			 * pmPrint.add(getMiOverprintPrintCustomsEnvelopNo());
			 * 
			 * Separator separator1 = new Separator();
			 * separator1.setForeground(Color.gray); pmPrint.add(separator1);
			 * pmPrint.add(getMiOverprintNotPrintCustomsEnvelopNo());
			 **/
			Separator separator2 = new Separator();
			separator2.setForeground(Color.GRAY);
			pmPrint.add(separator2);
			pmPrint.add(getMiNotOverprintCustomsEnvelopReport());

			/**
			 * Separator separator3 = new Separator();
			 * separator3.setForeground(Color.GRAY); pmPrint.add(separator3);
			 * pmPrint.add(getMiTransferDescriptionRegister());
			 **/
		}
		return pmPrint;
	}

	/**
	 * This method initializes miOverprintNotPrintCustomsEnvelopNo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiOverprintNotPrintCustomsEnvelopNo() {
		if (miOverprintNotPrintCustomsEnvelopNo == null) {
			miOverprintNotPrintCustomsEnvelopNo = new JMenuItem();
			miOverprintNotPrintCustomsEnvelopNo.setText("套打申请表(不打印申请表号)");
			miOverprintNotPrintCustomsEnvelopNo.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgAppHead.OVERPRINT_NOT_PRINT_CUSTOMS_ENVELOP_NO);
				}
			});

		}
		return miOverprintNotPrintCustomsEnvelopNo;
	}

	/**
	 * This method initializes miOverprintPrintCustomsEnvelopNo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiOverprintPrintCustomsEnvelopNo() {
		if (miOverprintPrintCustomsEnvelopNo == null) {
			miOverprintPrintCustomsEnvelopNo = new JMenuItem();
			miOverprintPrintCustomsEnvelopNo.setText("套打申请表(打印申请表号)");
			miOverprintPrintCustomsEnvelopNo.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgAppHead.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO);
				}
			});
		}
		return miOverprintPrintCustomsEnvelopNo;
	}

	/**
	 * This method initializes miOverprintPrintCustomsEnvelopNoForOld
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiOverprintPrintCustomsEnvelopNoForOld() {
		if (miOverprintPrintCustomsEnvelopNoForOld == null) {
			miOverprintPrintCustomsEnvelopNoForOld = new JMenuItem();
			miOverprintPrintCustomsEnvelopNoForOld.setText("套打申请表.旧(打印申请表号)");
			miOverprintPrintCustomsEnvelopNoForOld.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgAppHead.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO_ForOld);
				}
			});
		}
		return miOverprintPrintCustomsEnvelopNoForOld;
	}

	/**
	 * This method initializes miOverprintCustomsEnvelopReport
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiNotOverprintCustomsEnvelopReport() {
		if (miNotOverprintCustomsEnvelopReport == null) {
			miNotOverprintCustomsEnvelopReport = new JMenuItem();
			miNotOverprintCustomsEnvelopReport.setText("非套打申请表(新表)");
			miNotOverprintCustomsEnvelopReport.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgAppHead.NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT);
				}
			});
		}
		return miNotOverprintCustomsEnvelopReport;
	}

	/**
	 * This method initializes miTransferDescriptionRegister
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiTransferDescriptionRegister() {
		if (miTransferDescriptionRegister == null) {
			miTransferDescriptionRegister = new JMenuItem();
			miTransferDescriptionRegister.setText("实际结转情况登记表");
			miTransferDescriptionRegister.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgAppHead.TRANSFER_DESCRIPTION_REGISTER);
				}
			});
		}
		return miTransferDescriptionRegister;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckAppHeadAdd(new Request(CommonVars.getCurrUser()));
					getJPopupMenu().show(getBtnAdd(), 0,
							getBtnAdd().getY() + getBtnAdd().getPreferredSize().height);
				}
			});
		}
		return btnAdd;
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
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckAppHeadEdit(new Request(CommonVars.getCurrUser()));
					editData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckAppHeadDel(new Request(CommonVars.getCurrUser()));
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
//			btnPrint.setText("打印 ");
			btnPrint.setText("非套打申请表(新表)");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					柯鹏程getPmPrint().show(btnPrint, 0, btnPrint.getHeight());
					printReport(FmFptExgAppHead.NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT);
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFptExgAppHead.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes tb1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTb1() {
		if (tb1 == null) {
			tb1 = new JTable();
			tb1.addMouseListener(new MouseListenerClass());
			tb1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting()) {
						return;
					}
					JTableListModel tableModel = (JTableListModel) tb1.getModel();
					if (tableModel == null) {
						return;
					}

					try {
						if (tableModel.getCurrentRow() != null) {
							setState();
						}
					} catch (Exception cx) {

					}
				}
			});
		}
		return tb1;
	}

	/**
	 * 表格鼠标事件
	 */
	class MouseListenerClass extends MouseAdapter {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if (e.getClickCount() == 2) {
				// editData();
				JTableListModel tableMode = (0 == tabPn.getSelectedIndex()) ? tableMode1 : tableMode2;
				browse(tableMode);
			}
		}
	}

	/**
	 * This method initializes spn
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn() {
		if (spn1 == null) {
			spn1 = new JScrollPane();
			spn1.setViewportView(getTb1());
		}
		return spn1;
	}

	/**
	 * This method initializes spn
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn2() {
		if (spn2 == null) {
			spn2 = new JScrollPane();
			spn2.setViewportView(getTb2());
		}
		return spn2;
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableMode1;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableMode1 = tableModel;
	}

	// /////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////////////////////////

	/**
	 * 初始化数据
	 */
	private void initUIComponents() {
		// group.add(rbOut);
		// group.add(rbIn);

		DefaultComboBoxModel scmCocs = new DefaultComboBoxModel();
		// if (this.getInOutFlag().equals(FptInOutFlag.IN)) {
		// scmCocs = new DefaultComboBoxModel(super.getManufacturer()
		// .toArray());
		// } else {
		scmCocs = new DefaultComboBoxModel(super.getCustomer().toArray());
		// }
		this.cbbScmCoc.setModel(scmCocs);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc, "code", "name", 280);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender("code", "name", 110,
				170));
		this.cbbScmCoc.setSelectedItem(null);

	}

	/**
	 * 初始化数据参数 tb
	 */
	@SuppressWarnings("rawtypes")
	private void initTable(List list, JTable tb) {
		tableMode1 = new JTableListModel(tb, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("客户", "scmCoc.name", 100));
				list.add(addColumn("申报状态", "declareState", 100));// 2
				list.add(addColumn("申报日期", "outDate", 100));
				list.add(addColumn("申请表编号", "appNo", 100));
				list.add(addColumn("电子口岸统一编号", "seqNo", 80));
				list.add(addColumn("转入转出标记", "inOutFlag", 75)); // 1
				list.add(addColumn("修改标记", "modifyMarkState", 100));// 3

				list.add(addColumn("转出企业内部编号", "outCopAppNo", 100));
				list.add(addColumn("企业合同号", "contrNo", 80));
				list.add(addColumn("转出手册/帐册编号", "emsNo", 100));
				list.add(addColumn("转入企业名称", "inTradeName", 80));
				list.add(addColumn("转入手册/帐册编号", "inEmsNo", 100));

				list.add(addColumn("是否结案", "isCollated", 50));// 6
				list.add(addColumn("申请表类型", "appClass", 80));

				list.add(addColumn("转出企业代码", "outTradeCode", 150));
				list.add(addColumn("转出企业名称", "outTradeName", 60));
				list.add(addColumn("转出地", "outDistrict.name", 50));
				list.add(addColumn("转入企业代码", "inTradeCode", 80));

				list.add(addColumn("转入地", "inDistrict.name", 100));
				list.add(addColumn("转出地海关", "outCustoms.name", 100));

				list.add(addColumn("转出企业批准证编号", "outLiceNo", 100));
				list.add(addColumn("转出申报企业代码", "outTradeCode2", 100));
				list.add(addColumn("转出申报企业名称", "outTradeName2", 100));

				list.add(addColumn("申报企业9位组织机构代码", "outAgentCode", 100));
				list.add(addColumn("申报企业组织机构名称", "outAgentName", 100));
				list.add(addColumn("预计运输耗时（天）", "conveyDay", 100));
				list.add(addColumn("送货距离（公里）", "conveySpa", 100));
				list.add(addColumn("转出企业法人/联系电话", "outCorp", 100));
				list.add(addColumn("转出申报人/联系电话", "outDecl", 100));
				list.add(addColumn("转出备注", "outNote", 100));

				list.add(addColumn("转入企业内部编号", "inCopAppNo", 100));
				list.add(addColumn("转入地海关", "inCustoms.name", 100));
				list.add(addColumn("转入申报企业9位组织机构代码", "inAgentCode", 100));
				list.add(addColumn("转入申报企业组织机构名称", "inAgentName", 100));
				list.add(addColumn("转入企业法人/联系电话", "inCorp", 100));
				list.add(addColumn("转入申报人/联系电话", "inDecl", 100));
				list.add(addColumn("转入备注", "inNote", 100));
				list.add(addColumn("转入企业批准证编号", "inLiceNo", 100));
				list.add(addColumn("转入申报日期", "inDate", 100));
				list.add(addColumn("转入申报企业代码", "inTradeCode2", 100));
				list.add(addColumn("转入申报企业名称", "inTradeName2", 100));
				list.add(addColumn("录入员", "aclUser.userName", 100));
				list.add(addColumn("手册类型", "projectType", 100));
				return list;
			}
		});
		tb.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return DeclareState.getDeclareStateSpec(value.toString());
			}
		});
		tb.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				// super.getTableCellRendererComponent(table, value,
				// isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return FptInOutFlag.getNote(value.toString());
			}
		});

		tb.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return ModifyMarkState.getModifyMarkSpec(value.toString());
			}
		});
		tb.getColumnModel().getColumn(13).setCellRenderer(new TableCheckBoxRender());

		tb.getColumnModel().getColumn(14).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return AppClass.getNote(value.toString());
			}
		});

		tb.getColumnModel().getColumn(43).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return ProjectType.getNote(Integer.valueOf(value.toString()));
			}
		});
	}

	/**
	 * 获得转入转出
	 * 
	 * @return
	 */
	// private String getInOutFlag() {
	// if (this.tabPn.getSelectedIndex() == 1) {
	// return FptInOutFlag.IN;
	// } else {
	// return FptInOutFlag.OUT;
	// }
	// }

	/**
	 * 新增
	 */
	private void addData() {
		// FptParameterSet parameterSet = fptManageAction
		// .findTransParameterSet(new Request(CommonVars
		// .getCurrUser(), true));
		// if( parameterSet.getProjectType()== null){
		// JOptionPane.showMessageDialog(this, "请到转厂管理参数设置中,先设置项目类型!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		// String inOutFlag = getInOutFlag();
		FptAppHead fptAppHead = this.fptManageAction.newFptAppHead(
				new Request(CommonVars.getCurrUser()), FptInOutFlag.OUT);
		tableMode1.addRow(fptAppHead);
		// DgFptAppHead dg = new DgFptAppHead();
		// dg.setImportGoods(this.isImportGoods());
		// dg.setCustomsEnvelopModel(this.tableModel);
		// dg.setDataState(DataState.ADD);
		// dg.setVisible(true);
	}

	/**
	 * 修改
	 */
	private void editData() {
		if (tableMode1.getCurrentRow() == null) {
			JOptionPane
					.showMessageDialog(this, "请选择你要修改的资料", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FptAppHead head = (FptAppHead) tableMode1.getCurrentRow();
		head = this.fptManageAction.findFptAppHeadById(new Request(CommonVars.getCurrUser()),
				head.getId());
		if (head == null) {
			JOptionPane.showMessageDialog(this, "申请表企业内部编号 " + head.getOutCopAppNo() + " 已经删除不能修改",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (head != null && head.getDeclareState() != null
				&& head.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			JOptionPane.showMessageDialog(this, "申请表企业内部编号 " + head.getOutCopAppNo() + " 已经生效不能修改",
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgFptAppHead dg = new DgFptAppHead();
		dg.setFptInOutFlag(FptInOutFlag.OUT);
		dg.setFptAppHeadModel(this.tableMode1);
		// dg.setFptAppHead(fptAppHead);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
	}

	/**
	 * 浏览
	 * 
	 */
	private void browse(JTableListModel tableMode) {
		FptAppHead head = (FptAppHead) tableMode.getCurrentRow();
		if (tableMode.getCurrentRow() == null) {
			JOptionPane
					.showMessageDialog(this, "请选择你要显示的资料", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgFptAppHead dg = new DgFptAppHead();
		dg.setFptInOutFlag(head.getInOutFlag());
		dg.setFptAppHeadModel(tableMode);
		dg.setDataState(DataState.BROWSE);
		dg.setVisible(true);
	}

	/**
	 * 删除
	 */
	private void deleteData() {
		if (tableMode1.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			super.fptManageAction.deleteFptAppHead(new Request(CommonVars.getCurrUser()),
					(FptAppHead) tableMode1.getCurrentRow());
			tableMode1.deleteRow(tableMode1.getCurrentRow());
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/***
	 * 数据转换
	 */

	private void changingData() {
		if (tableMode1.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定变更申请表!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			FptAppHead c = this.fptManageAction.changingFptAppHead(
					new Request(CommonVars.getCurrUser()), (FptAppHead) tableMode1.getCurrentRow());
			if (c != null) {
				tableMode1.addRow(c);
			} else {
				JOptionPane.showMessageDialog(this, "已存在变更申请表!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane
					.showMessageDialog(this, "请选择要变更申请表!", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 检验备案数据
	 * 
	 */
	private boolean validateDataIsNull() {
		FptAppHead fptAppHead = (FptAppHead) tableMode1.getCurrentRow();
		if (fptAppHead == null) {
			return true;
		}
		String fptInOutFlag = fptAppHead.getInOutFlag();
		if (fptInOutFlag.equals(FptInOutFlag.OUT)) {
			if (fptAppHead.getAppClass() == null || "".equals(fptAppHead.getAppClass())) {
				JOptionPane.showMessageDialog(null, "申请表类型不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getOutLiceNo() == null || "".equals(fptAppHead.getOutLiceNo())) {
				JOptionPane.showMessageDialog(null, "转出企业批准证编号不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getOutAgentCode() == null || "".equals(fptAppHead.getOutAgentCode())) {
				JOptionPane.showMessageDialog(null, "转出申报企业9位组织机构代码不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else if (fptAppHead.getOutAgentCode().trim().length() != 9) {
				JOptionPane.showMessageDialog(null, "转出申报企业9位组织机构代码必须为9位！", "提示",
						JOptionPane.YES_NO_OPTION);
				return true;
			}

			if (fptAppHead.getOutAgentName() == null || "".equals(fptAppHead.getOutAgentName())) {
				JOptionPane.showMessageDialog(null, "转出申报企业组织机构名称不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (fptAppHead.getOutTradeCode() == null || "".equals(fptAppHead.getOutTradeCode())) {
				JOptionPane.showMessageDialog(null, "转出企业代码不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getOutTradeName() == null || "".equals(fptAppHead.getOutTradeName())) {
				JOptionPane.showMessageDialog(null, "转出企业名称不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getEmsNo() == null || "".equals(fptAppHead.getEmsNo())) {
				JOptionPane.showMessageDialog(null, "转出手册/帐册编号不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getOutCopAppNo() == null || "".equals(fptAppHead.getOutCopAppNo())) {
				JOptionPane.showMessageDialog(null, "转出企业内部编号不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (fptAppHead.getOutDistrict() == null) {
				JOptionPane.showMessageDialog(null, "转出地不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getOutCustoms() == null) {
				JOptionPane.showMessageDialog(null, "转出地海关不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getInTradeCode() == null || fptAppHead.getInTradeCode().equals("")) {
				JOptionPane.showMessageDialog(null, "转入企业代码不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (fptAppHead.getInTradeName() == null || "".equals(fptAppHead.getInTradeName())) {
				JOptionPane.showMessageDialog(null, "转入企业名称不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}

			if (fptAppHead.getInDistrict() == null) {
				JOptionPane.showMessageDialog(null, "目的地不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		} else { // 转入
			if (fptAppHead.getSeqNo() == null || "".equals(fptAppHead.getSeqNo())) {
				JOptionPane.showMessageDialog(null, "电子口岸统一编号不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else if (fptAppHead.getSeqNo().trim().length() != 18) {
				JOptionPane.showMessageDialog(null, "电子口岸统一编号必须为18位！", "提示",
						JOptionPane.YES_NO_OPTION);
				return true;
			}
			if (fptAppHead.getInAgentCode() == null || "".equals(fptAppHead.getInAgentCode())) {
				JOptionPane.showMessageDialog(null, "转入申报企业9位组织机构代码不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else if (fptAppHead.getInAgentCode().trim().length() != 9) {
				JOptionPane.showMessageDialog(null, "转入申报企业9位组织机构代码必须为9位！", "提示",
						JOptionPane.YES_NO_OPTION);
				return true;
			}
			if (fptAppHead.getInAgentName() == null || "".equals(fptAppHead.getInAgentName())) {
				JOptionPane.showMessageDialog(null, "转入申报企业组织机构名称不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
			if (fptAppHead.getInCustoms() == null) {
				JOptionPane.showMessageDialog(null, "转入地海关不可为空!!", "警告",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}

		//
		// 检查明细
		//
		List<FptAppItem> fptAppItems = this.fptManageAction.findFptAppItems(
				new Request(CommonVars.getCurrUser()), fptAppHead.getId(), fptInOutFlag);
		if (fptAppItems.size() <= 0) {
			JOptionPane.showMessageDialog(this, "申请表明细记录个数不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		String errorMsg = "";
		boolean isErrorMsg = false;
		for (FptAppItem fptAppItem : fptAppItems) {
			String msg = vaildatorDataIsNull(fptAppItem);
			if (msg != null && !"".equals(msg.trim())) {
				isErrorMsg = true;
				String listNo = "序号是 [ " + fptAppItem.getListNo().toString() + " ] 的明细资料有以下空值 : \n";
				errorMsg += listNo + msg;
			}
		}
		if (isErrorMsg) {
			// JMessage.showMessageDialog(FmMain.getInstance(), errorMsg,
			// JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION,
			// JMessageButtonEnum.OK);
			JMessage.showMessageDialog(FmMain.getInstance(), "申请明细资料填写不完整,请查看详细信息!!",
					JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, new RuntimeException(
							"\n" + errorMsg));
			return true;
		}
		return false;
	}

	/**
	 * 获得明细
	 * 
	 * @param f
	 * @return
	 */
	private String vaildatorDataIsNull(FptAppItem f) {
		StringBuffer sb = new StringBuffer();
		//
		// 商品项号
		//
		if (f.getTrNo() == null) {
			String str = "   商品项号不可为空\n";
			sb.append(str);
			// JOptionPane.showMessageDialog(null, "商品项号不可为空", "警告",
			// JOptionPane.INFORMATION_MESSAGE);
			// return true;
		}
		//
		// 商品编码
		//
		if (f.getCodeTs() == null) {
			String str = "   商品编码不可为空\n";
			sb.append(str);
			// JOptionPane.showMessageDialog(null, "商品编码不可为空", "警告",
			// JOptionPane.INFORMATION_MESSAGE);
			// return true;
		}
		//
		// 商品名称
		//
		if (f.getName() == null || f.getName().trim().equals("")) {
			String str = "   商品名称不可为空\n";
			sb.append(str);
			// JOptionPane.showMessageDialog(null, "商品名称不可为空", "警告",
			// JOptionPane.INFORMATION_MESSAGE);
			// return true;
		}
		//
		// 验证数量
		//
		if (f.getQty() == null || (f.getQty() == 0.0)) {
			String str = "   申报数量不可为空\n";
			sb.append(str);
			// JOptionPane.showMessageDialog(null, "申报数量不可为空", "警告",
			// JOptionPane.INFORMATION_MESSAGE);
			// return true;
		}
		//
		// 申报单位
		//
		if (f.getUnit() == null) {
			String str = "   申报单位不可为空\n";
			sb.append(str);
			// JOptionPane.showMessageDialog(null, "申报单位不可为空", "警告",
			// JOptionPane.INFORMATION_MESSAGE);
			// return true;
		}
		//
		// 法定单位
		//
		if (f.getUnit1() == null) {
			String str = "   法定单位不可为空\n";
			sb.append(str);
			// JOptionPane.showMessageDialog(null, "法定单位不可为空", "警告",
			// JOptionPane.INFORMATION_MESSAGE);
			// return true;
		}
		//
		// 转入验证
		//
		if (f.getInOutFlag().equals(FptInOutFlag.IN)) {
			if (f.getInEmsNo() == null || f.getInEmsNo().trim().equals("")) {
				String str = "   转入手册/帐册号不可为空\n";
				sb.append(str);
				// JOptionPane.showMessageDialog(null, "转入手册/帐册号不可为空!!", "警告",
				// JOptionPane.INFORMATION_MESSAGE);
				// return true;
			}

		}

		//
		// 转出验证 2013.10.11 HH
		//
		// if (f.getInOutFlag().equals(FptInOutFlag.OUT)) {
		// if (f.getInOutNo() == null) {
		// String str = "   转出序号不可为空\n";
		// sb.append(str);
		// }
		// }
		return sb.toString();
	}

	/**
	 * 备案
	 * 
	 */
	private void putOnRecordData() {
		if (tableMode1.getCurrentRow() != null) {
			if (validateDataIsNull() == true) {
				return;
			}
			FptAppHead fptAppHead = (FptAppHead) tableMode1.getCurrentRow();
			if (!DeclareState.CHANGING_EXE.equals(fptAppHead.getDeclareState())) {
				if (fptManageAction.isExistFptAppHeadByOutCopAppNo(
						new Request(CommonVars.getCurrUser()), fptAppHead)) {
					JOptionPane.showMessageDialog(FmFptExgAppHead.this, "申请表手册编号重复,不能备案!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			String checkMessage = this.fptManageAction.checkFptAppHeadForPutOnRecord(new Request(
					CommonVars.getCurrUser(), true), fptAppHead);
			if (checkMessage != null && checkMessage.trim().length() > 0) {
				if (JOptionPane.showConfirmDialog(FmFptExgAppHead.this, checkMessage
						+ "\n是否确定备案此申请表!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			FptAppHead c = this.fptManageAction.putOnRecordFptAppHead(
					new Request(CommonVars.getCurrUser()), fptAppHead);
			tableMode1.updateRow(c);
			//
			// 获得其备案申请表
			//
			if (DeclareState.CHANGING_EXE.equals(fptAppHead.getDeclareState())) {
				FptAppHead putOnRecordFptAppHead = null;
				for (int i = 0; i < tableMode1.getList().size(); i++) {
					FptAppHead tempFptAppHead = (FptAppHead) this.tableMode1.getList().get(i);
					if (DeclareState.PROCESS_EXE.equals(tempFptAppHead.getDeclareState())
							&& tempFptAppHead.getEmsNo().equals(fptAppHead.getEmsNo())) {
						putOnRecordFptAppHead = tempFptAppHead;
						break;
					}
				}
				if (putOnRecordFptAppHead != null) {
					tableMode1.deleteRow(putOnRecordFptAppHead);
				}
			}

		} else {
			JOptionPane
					.showMessageDialog(this, "请选择要备案申请表!", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		FptAppHead c = null;
		// String inOutFlag = getInOutFlag();
		// if (FptInOutFlag.OUT.equals(inOutFlag)) {
		c = (FptAppHead) ((JTableListModel) tb1.getModel()).getCurrentRow();
		// }
		// else {
		// c = (FptAppHead) ((JTableListModel) jTable1.getModel())
		// .getCurrentRow();
		// }
		// c = (FptAppHead) tableModel.getCurrentRow();
		if (c == null) {
			return;
		}

		String declareState = c.getDeclareState();
		boolean isChangingExe = DeclareState.CHANGING_EXE.equals(declareState);
		boolean isProcessExe = DeclareState.PROCESS_EXE.equals(declareState);
		boolean isApplyPor = DeclareState.APPLY_POR.equals(declareState);
		boolean isWaitEaa = DeclareState.WAIT_EAA.equals(declareState);
		boolean isCancel = DeclareState.WAIT_EAA.equals(declareState) || DeclareState.PROCESS_EXE.equals(declareState);
		boolean isCollated;
		if (c.getIsCollated() == null) {
			isCollated = false;
		} else {
			isCollated = c.getIsCollated();
		}
		//
		// 能编辑
		//
		boolean isCanEdit = isApplyPor || isChangingExe;
		//
		// 不能编辑
		//
		boolean isNotCanEdit = isProcessExe || isWaitEaa;

		btnEdit.setEnabled(isCanEdit);
		btnDelete.setEnabled(isCanEdit);
		this.btnApply.setEnabled(isCanEdit);

		// this.btnProcess.setEnabled(isWaitEaa);
		btnCopy.setEnabled(isApplyPor || isProcessExe);
		btnChanging.setEnabled(isProcessExe);
		btnPutOnRecord.setEnabled(isCanEdit);
		// this.btnCollated.setEnabled(!isCollated);
		this.btnCollated.setEnabled(DeclareState.PROCESS_EXE.equals(declareState));
		this.btnCanCollated.setEnabled(isCollated);
		btnCancel.setEnabled(isCancel);

	}

	/**
	 * 报表打印
	 */
	private void printReport(int flag) {
		if (this.tableMode1.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要打印的申请表!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List list = new ArrayList();
		list.add(this.tableMode1.getCurrentRow());
		FptAppHead fptAppHead = (FptAppHead) this.tableMode1.getCurrentRow();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		switch (flag) {
		case FmFptExgAppHead.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO_ForOld:
			printReportByCustomsEnvelopForOld(ds, false, true);
			break;
		case FmFptExgAppHead.OVERPRINT_NOT_PRINT_CUSTOMS_ENVELOP_NO:
			printReportByCustomsEnvelop(ds, fptAppHead, false, true);
			break;
		case FmFptExgAppHead.OVERPRINT_PRINT_CUSTOMS_ENVELOP_NO:
			printReportByCustomsEnvelop(ds, fptAppHead, true, true);
			break;
		case FmFptExgAppHead.NOT_OVERPRINT_CUSTOMS_ENVELOP_REPORT:
			printReportByCustomsEnvelop(ds, fptAppHead, true, false);
			break;
		case FmFptExgAppHead.TRANSFER_DESCRIPTION_REGISTER:
			printTransferDescriptionRegister(ds);
			break;
		default:
			break;
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 结转情况登记表打印
	 */
	private void printTransferDescriptionRegister(CustomReportDataSource ds) {
		try {
			InputStream masterReportStream = null;
			InputStream exportSubReportStream = null;
			InputStream importSubReportStream = null;

			masterReportStream = FmFptExgAppHead.class
					.getResourceAsStream("report/TransferGoodsRecords.jasper");

			// JasperReport exportSubReport = (JasperReport) JRLoader
			// .loadObject(exportSubReportStream);
			// JasperReport importSubReport = (JasperReport) JRLoader
			// .loadObject(importSubReportStream);
			Map parameters = new HashMap();

			JasperPrint jasperPrint = JasperFillManager.fillReport(masterReportStream, parameters,
					ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 申请表报表打印
	 */
	private void printReportByCustomsEnvelop(CustomReportDataSource ds, FptAppHead fptAppHead,
			boolean isCustomsEnvelopNoPrint, boolean isOverprint) {
		InputStream masterReportStream = null;
		InputStream exportSubReportStream = null;
		InputStream importSubReportStream = null;
		String transferCode = manualDecleareAction.getBpara(new Request(CommonVars.getCurrUser()),
				BcusParameter.TransferCode);
		try {
			List items = null;
			if (fptAppHead != null) {
				items = this.fptManageAction.findFptAppItems(new Request(CommonVars.getCurrUser()),
						fptAppHead.getId());
				subReportData = items;
			}
			CustomsEnvelopSubReportDataSource.setSubReportData(this.subReportData);
			masterReportStream = FmFptExgAppHead.class.getResourceAsStream("report/FptApp.jasper");

			// ****************出口子报表******************//

			exportSubReportStream = FmFptExgAppHead.class
					.getResourceAsStream("report/FptAppExpSubReport.jasper");
			// ****************进口子报表*****************//
			importSubReportStream = FmFptExgAppHead.class
					.getResourceAsStream("report/FptAppImpSubReport.jasper");

			JasperReport exportSubReport = (JasperReport) JRLoader
					.loadObject(exportSubReportStream);
			JasperReport importSubReport = (JasperReport) JRLoader
					.loadObject(importSubReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			String exportCompanyTel = "", exportCorporation = "";// 转出
			String exportYear = "", exportMonth = "", exportDay = "";// 转出
			String importCompanyTel = "", importCorporation = "";// 转入
			String importYear = "", importMonth = "", importDay = "";// 转入
			FptAppHead customsEnvelopBill = (FptAppHead) this.tableMode1.getCurrentRow();
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String outDate = "";
			String inDate = "";
			Calendar c = Calendar.getInstance();
			if (fptAppHead.getOutDate() != null) {
				c.setTime(fptAppHead.getOutDate());
				System.out.println(c.get(Calendar.YEAR));
				System.out.println(fptAppHead.getOutDate().getYear());
				exportYear = "" + c.get(Calendar.YEAR);
				exportMonth = "" + c.get(Calendar.MONTH);
				exportDay = "" + c.get(Calendar.DAY_OF_MONTH);
				outDate = fptAppHead.getOutDate() == null ? "" : bartDateFormat.format(fptAppHead
						.getOutDate());
				inDate = fptAppHead.getInDate() == null ? "" : bartDateFormat.format(fptAppHead
						.getInDate());
			}

			parameters.put("appNo", fptAppHead.getAppNo()); // 申请表编号
			parameters.put("seqNo", fptAppHead.getSeqNo()); // 电子口岸统一编号
			parameters.put("appClass",
					fptAppHead.getAppClass() == null ? "" : fptAppHead.getAppClass()); // 申报表类型
			parameters.put("contrNo",
					fptAppHead.getContrNo() == null ? "" : fptAppHead.getContrNo()); // 企业合同号
			parameters.put("endDate", fptAppHead.getEndDate() == null ? "" : fptAppHead
					.getEndDate().toString().substring(0, 10)); // 手册有效期 //手册有效期
			parameters.put("conveySpa", fptAppHead.getConveySpa() == null ? "" : fptAppHead
					.getConveySpa().toString());// 送货距离
			parameters.put("conveyDay", fptAppHead.getConveyDay() == null ? "" : fptAppHead
					.getConveyDay().toString());// 运输耗时
			parameters.put("inLiceNo",
					fptAppHead.getInLiceNo() == null ? "" : fptAppHead.getInLiceNo());// 转入批准证编号
			parameters.put("subReportExp", exportSubReport);
			parameters.put("subReportImp", importSubReport);
			parameters.put("dsExp", FptInOutFlag.OUT);
			parameters.put("dsImp", FptInOutFlag.IN);
			parameters.put("isCustomsEnvelopNoPrint", new Boolean(isCustomsEnvelopNoPrint));
			parameters.put("isOverprint", new Boolean(isOverprint));
			parameters.put("isImportGoods", new Boolean("1".equals(fptAppHead.getInOutFlag())));
			parameters.put("traCode", transferCode);
			/**
			 * 转出企业填写
			 */
			parameters.put("outCustoms.name", fptAppHead.getOutCustoms() == null ? "" : fptAppHead
					.getOutCustoms().getName()); // 转出主管海关
			if (fptAppHead.getOutDistrict() != null) {
				parameters.put("outDistrict.name", fptAppHead.getOutDistrict().getName());// 转出地
			}
			parameters.put("outTradeName",
					fptAppHead.getOutTradeName() == null ? "" : fptAppHead.getOutTradeName()); // 转出企业
			parameters.put("outTradeCode",
					fptAppHead.getOutTradeCode() == null ? "" : fptAppHead.getOutTradeCode()); // 转出企业代码
			parameters.put("outAgentName",
					fptAppHead.getOutAgentName() == null ? "" : fptAppHead.getOutAgentName());// //转出申报企业名称
			parameters.put("outAgentCode",
					fptAppHead.getOutAgentCode() == null ? "" : fptAppHead.getOutAgentCode());// 转出申报企业编码
			parameters.put("outCopAppNo", fptAppHead.getOutCopAppNo()); // 转出企业内部编号
			parameters.put("outLiceNo",
					fptAppHead.getOutLiceNo() == null ? "" : fptAppHead.getOutLiceNo()); // 转出企业内部编号
			parameters.put("outCorp",
					fptAppHead.getOutCorp() == null ? "" : fptAppHead.getOutCorp()); // 转出企业法人联系电话
			parameters.put("outDate", outDate); // 申报日期
			parameters.put("exportCorporation", exportCorporation);
			parameters.put("exportCompanyTel", exportCompanyTel);
			parameters.put("exportYear", exportYear);
			parameters.put("exportMonth", exportMonth);
			parameters.put("exportDay", exportDay);
			/**
			 * 转入企业填写
			 */
			parameters.put("inCustoms.name", fptAppHead.getInCustoms() == null ? "" : fptAppHead
					.getInCustoms().getName()); // 转入主管海关
			if (fptAppHead.getInDistrict() != null) {
				parameters.put("inDistrict.name", fptAppHead.getInDistrict().getName()); // 转入地
			}
			parameters.put("inTradeName",
					fptAppHead.getInTradeName() == null ? "" : fptAppHead.getInTradeName()); // 转入企业
			parameters.put("inTradeCode", fptAppHead.getInTradeCode() == null ? "" : fptAppHead
					.getInTradeCode().toString()); // 转入企业编码
			parameters.put("inAgentName",
					fptAppHead.getInAgentName() == null ? "" : fptAppHead.getInAgentName());// //转入申报企业名称
			parameters.put("inAgentCode",
					fptAppHead.getInAgentCode() == null ? "" : fptAppHead.getInAgentCode());// 转入申报企业编码
			parameters.put("inCopAppNo",
					fptAppHead.getInCopAppNo() == null ? "" : fptAppHead.getInCopAppNo()); // 转入企业内部编号
			parameters.put("inCorp", fptAppHead.getInCorp() == null ? "" : fptAppHead.getInCorp()); // 转入企业法人联系电话
			parameters.put("inDate", inDate); // 转入申报日期
			parameters.put("importCorporation", importCorporation);
			parameters.put("importCompanyTel", importCompanyTel);
			parameters.put("importYear", importYear);
			parameters.put("importMonth", importMonth);
			parameters.put("importDay", importDay);

			JasperPrint jasperPrint = JasperFillManager.fillReport(masterReportStream, parameters,
					ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 以进出口各5笔明细记录 分割成一个报表数据
	 * 
	 * @param fptAppHead
	 *            申请单表头
	 * @param outList
	 *            出口商品明细
	 * @param inList
	 *            进口商品明细
	 * @return 父报表数据源
	 */
	private List<PrintFptAppHead> splitReportData(FptAppHead fptAppHead, List<FptAppItem> outList,
			List<FptAppItem> inList) {
		List<PrintFptAppHead> masterLs = new ArrayList<PrintFptAppHead>();

		// 保持进出口明细商品笔数相同，以笔数多的为准，笔数不足的不全，以便适应打印报表行数格式
		if (outList == null) {
			outList = new ArrayList<FptAppItem>();
		}
		if (inList == null) {
			inList = new ArrayList<FptAppItem>();
		}
		int size = Math.abs(outList.size() - inList.size());
		if (outList.size() > inList.size()) {
			for (int i = 0; i < size; i++) {
				FptAppItem item = new FptAppItem();
				item.setFptAppHead(outList.get(0).getFptAppHead());
				item.setInOutFlag(FptInOutFlag.IN);
				item.setListNo(i + 1);
				inList.add(item);
			}
		} else if (inList.size() > outList.size()) {
			for (int i = 0; i < size; i++) {
				FptAppItem item = new FptAppItem();
				item.setFptAppHead(inList.get(0).getFptAppHead());
				item.setInOutFlag(FptInOutFlag.OUT);
				item.setListNo(i + 1);
				outList.add(item);
			}
		}

		// 复制第一笔表头数据
		PrintFptAppHead head = new PrintFptAppHead();
		try {
			PropertyUtils.copyProperties(head, fptAppHead);
		} catch (Exception e) {
		}
		head.setSerialNumber(0);
		// 以5笔记录 拆分出口数据 为一张报表
		for (int i = 0; i < outList.size(); i++) {
			if (i % 5 == 0) {
				if (i != 0) {
					head = (PrintFptAppHead) head.clone();
					head.setSerialNumber(head.getSerialNumber() + 1);
				}
				masterLs.add(head);
			}
			outList.get(i).setFptAppHead(head);
		}
		// 以5笔记录 拆分进口数据 为一张报表
		for (int i = 0, j = 0; i < inList.size(); i++) {
			if (i % 5 == 0) {
				if (j < masterLs.size()) {
					head = masterLs.get(j);
				} else {
					head = (PrintFptAppHead) head.clone();
					head.setSerialNumber(head.getSerialNumber() + 1);
					masterLs.add(head);
				}
				j++;
			}
			inList.get(i).setFptAppHead(head);
		}
		outList.addAll(inList);
		return masterLs;
	}

	/**
	 * 申请表报表打印
	 */
	private void printReportByCustomsEnvelopForOld(CustomReportDataSource ds,
			boolean isCustomsEnvelopNoPrint, boolean isOverprint) {
		String transferCode = manualDecleareAction.getBpara(new Request(CommonVars.getCurrUser()),
				BcusParameter.TransferCode);
		try {
			// if (subReportData == null) {
			// this.subReportData = this.transferFactoryManageAction
			// .findCustomsEnvelopCommodityInfo(new Request(CommonVars
			// .getCurrUser()));
			// }
			CustomsEnvelopSubReportDataSource.setSubReportData(this.subReportData);
			InputStream masterReportStream = null;
			InputStream exportSubReportStream = null;
			InputStream importSubReportStream = null;

			masterReportStream = FmFptExgAppHead.class
					.getResourceAsStream("report/CustomsEnvelopExportReportForOld.jasper");
			//
			// 出口公司
			//
			exportSubReportStream = FmFptExgAppHead.class
					.getResourceAsStream("report/CustomsEnvelopExportSubReportForOld.jasper");
			//
			// 进口公司
			//
			importSubReportStream = FmFptExgAppHead.class
					.getResourceAsStream("report/CustomsEnvelopImportSubReportForOld.jasper");

			JasperReport exportSubReport = (JasperReport) JRLoader
					.loadObject(exportSubReportStream);
			JasperReport importSubReport = (JasperReport) JRLoader
					.loadObject(importSubReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (isImportGoods) {
				parameters.put("isImportGoods", new Boolean(true));
			} else {
				parameters.put("isImportGoods", new Boolean(false));
			}
			parameters.put("isCustomsEnvelopNoPrint", new Boolean(isCustomsEnvelopNoPrint));
			parameters.put("isOverprint", new Boolean(isOverprint));
			parameters.put("exportSubReport", exportSubReport);
			parameters.put("importSubReport", importSubReport);
			parameters.put("traCode", transferCode);
			boolean istins = CommonVars.isCompany("田氏化工");
			if (istins) {
				parameters.put("istins", "1");
			} else {
				parameters.put("istins", "0");
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(masterReportStream, parameters,
					ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cbCollated.setSelected(false);
					ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					List list = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(new Request(
							CommonVars.getCurrUser()), scmCoc, cbbBeginDate.getDate(), cbbEndDate
							.getDate(), FptInOutFlag.OUT, false, false);
					initTable(list, tb1);
				}
			});
		}
		return btnRefresh;
	}

	public void refresh() {
		cbCollated.setSelected(false);
		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
		List list = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(
				new Request(CommonVars.getCurrUser()), scmCoc, cbbBeginDate.getDate(),
				cbbEndDate.getDate(), FptInOutFlag.OUT, false, false);
		initTable(list, tb1);
	}

	/**
	 * This method initializes btnApply
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckApply(new Request(CommonVars.getCurrUser()));
					FptAppHead fptAppHead = (FptAppHead) tableMode1.getCurrentRow();
					if (fptAppHead == null) {
						JOptionPane.showMessageDialog(FmFptExgAppHead.this, "请选择要申报的申请表!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmFptExgAppHead.this, "是否确定申报申请表!", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					if (validateDataIsNull() == true) {
						return;
					}

					new ApplyThread().start();
				}
			});
		}
		return btnApply;
	}

	/***
	 * 执行
	 * 
	 * @author Administrator
	 * 
	 */
	class ApplyThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				FptAppHead head = (FptAppHead) tableMode1.getCurrentRow();
				try {
					DeclareFileInfo fileInfo = fptManageAction.applyFptAppHead(new Request(
							CommonVars.getCurrUser()), head, taskId);
					head = fptManageAction.findFptAppHeadById(
							new Request(CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableMode1.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					String result = "<html>系统生成报文成功<br>" + "报文名称为:" + fileInfo.getFileName()
							+ "<br>";
					JOptionPane.showMessageDialog(FmFptExgAppHead.this, result, "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmFptExgAppHead.this,
							"系统生成报文失败 " + ex.getMessage(), "确认", 1);
				}
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->" + ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}

			int count = 0;

			try {
				// 上传文件
				if (FptQuery.getInstance().isHttpUpload()) {
					count = fptMessageAction.httpUpload(new Request(CommonVars.getCurrUser()));
					if (count == 0) {
						JOptionPane.showMessageDialog(FmFptExgAppHead.this, "系统报文发送失败，请重新发送", "确认",
								1);
					}
				}
			} catch (RuntimeException e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmFptExgAppHead.this,
						"系统报文发送失败，请重新发送 " + e.getMessage(), "确认", 1);
				return;
			}
		}

	}

	/**
	 * This method initializes btnChanging
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChanging() {
		if (btnChanging == null) {
			btnChanging = new JButton();
			btnChanging.setText("变更");
			btnChanging.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckAppHeadChange(new Request(CommonVars
							.getCurrUser()));
					changingData();
				}
			});
		}
		return btnChanging;
	}

	/**
	 * This method initializes btnBrowse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("\u6d4f\u89c8");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//JTableListModel tableMode = (0 == FmFptExgAppHead.this.tabPn.getSelectedIndex()) ? tableMode1 : tableMode2;
					browse(tableMode1);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes btnPutOnRecord
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPutOnRecord() {
		if (btnPutOnRecord == null) {
			btnPutOnRecord = new JButton();
			btnPutOnRecord.setVisible(false);
			btnPutOnRecord.setText("\u5907\u6848");
			btnPutOnRecord.addActionListener(

			new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					putOnRecordData();
				}
			});
		}
		return btnPutOnRecord;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("\u5904\u7406\u56de\u6267");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckProcess(new Request(CommonVars.getCurrUser()));
					processFptApp();
				}
			});
		}
		return btnProcess;
	}

	/**
	 * 回执处理
	 */
	private void processFptApp() {
		int processCount = 0;
		int waitCount = 0;
		StringBuffer noReceipt = new StringBuffer();
		;
		List list = tableMode1.getList();
		for (int i = 0; i < list.size(); i++) {
			FptAppHead fptAppHead = (FptAppHead) list.get(i);
			fptAppHead = fptManageAction.findFptAppHeadById(new Request(CommonVars.getCurrUser()),
					fptAppHead.getId());
			if (DeclareState.WAIT_EAA.equals(fptAppHead.getDeclareState())) {
				waitCount++;
				FptAppHead result = processSingleFptApp(fptAppHead);
				if (result != null) {
					processCount++;
				} else {
					noReceipt.append(fptAppHead.getOutCopAppNo()).append("\r\n");
				}
			}
		}
		setState();
		if (waitCount > 0) {
			if (processCount > 0) {
				JOptionPane.showMessageDialog(this, "有"
						+ processCount
						+ "笔成品申请表收到回执，请点击查看按钮查看回执明细内容。"
						+ ("".equals(noReceipt.toString().trim()) ? "" : "\r内部编号是" + noReceipt
								+ "成品申请表没有回执。"), "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "内部编号是：\r\n" + noReceipt + "的成品申请表没有回执。", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "没有需要回执处理的成品申请表。", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 处理回执
	 */
	private FptAppHead processSingleFptApp(FptAppHead fptAppHead) {
		//
		// 获得变更前的存在的数据
		//
		FptAppHead existFtpAppHead = null;
		List lsReturnFile = new ArrayList();
		//
		// 企业内部编码
		//
		String outCopAppNo = fptAppHead.getOutCopAppNo();
		// 下载回执,通过服务器下载
		if (FptQuery.getInstance().isHttpUpload()) {
			try {
				fptMessageAction.httpDownload(new Request(CommonVars.getCurrUser()), outCopAppNo);
			} catch (RuntimeException e) {
				JOptionPane.showMessageDialog(FmFptExgAppHead.this,
						"下载回执失败！可能原因：1、网络不通、连接不到远程ftp。2、ftp参数配置错误");
			}
		}

		// lsReturnFile = FptMessageHelper.getInstance().showBcsReceiptFile(
		// FptBusinessType.FPT_APP, outCopAppNo);
		lsReturnFile.addAll(fptMessageAction.findNotProcessReturnFile(
				new Request(CommonVars.getCurrUser()), FptBusinessType.FPT_APP, outCopAppNo));
		if (lsReturnFile.size() <= 0) {
			return null;
		}
		existFtpAppHead = this.fptManageAction.findFptAppHeadByOutCopAppNo(
				new Request(CommonVars.getCurrUser()), outCopAppNo);

		// } else {// 转入
		// //
		// // 企业内部编码
		// //
		// String inCopAppNo = fptAppHead.getInCopAppNo();
		// lsReturnFile = FptMessageHelper.getInstance().showBcsReceiptFile(
		// FptBusinessType.FPT_APP, inCopAppNo);
		// if (lsReturnFile.size() <= 0) {
		// return;
		// }
		// existFtpAppHead = this.fptManageAction.findFptAppHeadByInCopAppNo(
		// new Request(CommonVars.getCurrUser()), inCopAppNo);
		// }
		//
		// 处理报文
		//
		try {
			String result = fptManageAction.processFptAppHead(
					new Request(CommonVars.getCurrUser()), fptAppHead, existFtpAppHead,
					lsReturnFile);
			fptAppHead = fptManageAction.findFptAppHeadById(new Request(CommonVars.getCurrUser()),
					fptAppHead.getId());
			tableMode1.updateRow(fptAppHead);
			if (existFtpAppHead != null) {
				FptAppHead exingHeadTemp = fptManageAction.findFptAppHeadById(new Request(
						CommonVars.getCurrUser()), existFtpAppHead.getId());
				if (exingHeadTemp == null) {
					tableMode1.deleteRow(existFtpAppHead);
				}
			}
			return fptAppHead;
			// JOptionPane.showMessageDialog(FmFptExgAppHead.this, "回执处理成功！\n"
			// + result, "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			// JOptionPane.showMessageDialog(FmFptExgAppHead.this,
			// "回执处理失败" + ex.getMessage(), "提示",
			// JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("\u8f6c\u6284");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckCopy(new Request(CommonVars.getCurrUser()));
					getPmCopyData().show(btnCopy, 0, btnCopy.getHeight());
					getPmCopyData().show(btnCopy, 0, btnCopy.getHeight());
				}
			});
		}
		return btnCopy;
	}

	/***
	 * this method is initializes pmCopyData
	 * 
	 * @return
	 */
	private JPopupMenu getPmCopyData() {
		if (pmCopyData == null) {
			pmCopyData = new JPopupMenu();
			pmCopyData.add(getMiAllCopy());
			pmCopyData.add(getMiHead());
			pmCopyData.add(getMiHeadAndItems());
		}
		return pmCopyData;
	}

	/***
	 * this method is initializes miAllCopy
	 * 
	 * @return
	 */
	public JMenuItem getMiAllCopy() {
		if (miAllCopy == null) {
			miAllCopy = new JMenuItem();
			miAllCopy.setText("完全转抄");
			miAllCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableMode1.getCurrentRow() != null) {
						List<FptAppHead> list = fptManageAction.copyFptAppHeadAll(new Request(
								CommonVars.getCurrUser()), tableMode1.getCurrentRows());
						tableMode1.addRows(list);
					} else {
						JOptionPane.showMessageDialog(FmFptExgAppHead.this, "请选择要转抄申请表!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return miAllCopy;
	}

	/***
	 * this method is initializes miHead
	 * 
	 * @return
	 */
	public JMenuItem getMiHead() {
		if (miHead == null) {
			miHead = new JMenuItem();
			miHead.setText("转抄表头");
			miHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableMode1.getCurrentRow() != null) {
						List<FptAppHead> list = fptManageAction.copyFptAppHead(

						new Request(CommonVars.getCurrUser()), tableMode1.getCurrentRows());
						tableMode1.addRows(list);
					} else {
						JOptionPane.showMessageDialog(FmFptExgAppHead.this, "请选择要转抄申请表!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return miHead;
	}

	/***
	 * this method is initializes miHeadAndItems
	 * 
	 * @return
	 */
	public JMenuItem getMiHeadAndItems() {
		if (miHeadAndItems == null) {
			miHeadAndItems = new JMenuItem();
			miHeadAndItems.setText("转抄表头表体");
			miHeadAndItems.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableMode1.getCurrentRow() != null) {
						DgCopyHeadAndItems dg = new DgCopyHeadAndItems();
						dg.setFptAppHead((FptAppHead) tableMode1.getCurrentRow());
						dg.setVisible(true);
						if (dg.isOk()) {
							tableMode1.addRow(dg.getFptAppHead());
						}
					} else {
						JOptionPane.showMessageDialog(FmFptExgAppHead.this, "请选择要转抄申请表!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return miHeadAndItems;
	}

	/**
	 * This method initializes tabPn
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTabPn() {
		if (tabPn == null) {
			tabPn = new JTabbedPane();
			tabPn.setTabPlacement(JTabbedPane.BOTTOM);
			tabPn.addTab("转出申请表", null, getSpn(), null);
			tabPn.addTab("已作废申请表", null, getSpn2(), null);
			tabPn.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					tb.setVisible(tabPn.getSelectedIndex() != 1);
					cbCollated.setVisible(tabPn.getSelectedIndex() != 1);
					tbBar.setVisible(true);

					initUIComponents();
					showAllData(tabPn.getSelectedIndex());
				}

			});
		}
		return tabPn;
	}

	/***
	 * 显示所有的数据
	 */
	private void showAllData(int x) {

		switch (x) {
		case 0:// 申请表
			List list1 = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(
					new Request(CommonVars.getCurrUser()), null, this.cbbBeginDate.getDate(),
					this.cbbEndDate.getDate(), FptInOutFlag.OUT, cbCollated.isSelected(), false);
			initTable((null == list1) ? Collections.EMPTY_LIST : list1, tb1);
			break;
		case 1:// 报废
			if (null == tableMode2) {
				List list2 = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(new Request(
						CommonVars.getCurrUser()), null, this.cbbBeginDate.getDate(),
						this.cbbEndDate.getDate(), FptInOutFlag.OUT, false, true);
				initTable2((null == list2) ? Collections.EMPTY_LIST : list2, tb2);
			}
			break;
		}

	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	// private JTable getJTable1() {
	// if (jTable1 == null) {
	// jTable1.getSelectionModel().addListSelectionListener(
	// new ListSelectionListener() {
	// public void valueChanged(ListSelectionEvent e) {
	// if (e.getValueIsAdjusting()) {
	// return;
	// }
	// JTableListModel tableModel = (JTableListModel) jTable1
	// .getModel();
	// if (tableModel == null) {
	// return;
	// }
	//
	// try {
	// if (tableModel.getCurrentRow() != null) {
	// setState();
	// }
	// } catch (Exception cx) {
	//
	// }
	// }
	// });
	// }
	// return jTable1;
	// }

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getJPopupMenu() {
		if (jPopupMenu == null) {
			jPopupMenu = new JPopupMenu();
			jPopupMenu.setSize(new Dimension(45, 28));
			jPopupMenu.add(getJMenuItem());
			jPopupMenu.addSeparator();
			jPopupMenu.add(getJMenuItem1());
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
			jMenuItem = new JMenuItem("新增");
			jMenuItem.setSize(new Dimension(23, 23));
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
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
			jMenuItem1 = new JMenuItem("订单生成转厂申请表");
			jMenuItem1.setSize(new Dimension(18, 18));
			jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!LicenseClient.getInstance(CommonVars.getCurrUser().getCompany().getName())
							.checkFptManagePermisson()) {
						JOptionPane.showMessageDialog(FmFptExgAppHead.this,
								"没有使用此功能的权限，如果需要请联系百思维！");
						return;
					}
					DgMakeCustomsOrderToFptAppHead dg = new DgMakeCustomsOrderToFptAppHead();
					dg.setVisible(true);
				}
			});
		}
		return jMenuItem1;
	}

	/**
	 * This method initializes pn1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn1() {
		if (pn1 == null) {
			pn1 = new JPanel();
			pn1.setLayout(new BorderLayout());
			pn1.add(getTabPn(), BorderLayout.CENTER);
			pn1.add(getTbBar(), BorderLayout.NORTH);
		}
		return pn1;
	}

	/**
	 * This method initializes tbBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTbBar() {
		if (tbBar == null) {
			tbBar = new JToolBar();
			tbBar.add(getPn2());
		}
		return tbBar;
	}

	/**
	 * This method initializes pn2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			lb = new JLabel();
			lb.setText("客户");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(3);
			flowLayout.setHgap(2);
			pn2 = new JPanel();
			pn2.setLayout(flowLayout);
			pn2.add(lb, null);
			pn2.add(getCbbScmCoc(), null);
			pn2.add(getCalendarComboBox());
			pn2.add(getCalendarComboBox_1());
			pn2.add(getBtnSearch(), null);
			pn2.add(getCbCollated(), null);
		}
		return pn2;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setPreferredSize(new Dimension(250, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setPreferredSize(new Dimension(60, 22));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});

		}
		return btnSearch;
	}

	/***
	 * 查询
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private void search() {
		ScmCoc scmCoc = (ScmCoc) this.cbbScmCoc.getSelectedItem();
		if (scmCoc == null) {
			showAllData(tabPn.getSelectedIndex());
		} else {
			if (1 == tabPn.getSelectedIndex()) {
				// 针对 作废操作
				List list = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(new Request(
						CommonVars.getCurrUser()), scmCoc, this.cbbBeginDate.getDate(),
						this.cbbEndDate.getDate(), FptInOutFlag.OUT, false, true);
				initTable2(list, tb2);
			} else {
				List list = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(new Request(
						CommonVars.getCurrUser()), scmCoc, this.cbbBeginDate.getDate(),
						this.cbbEndDate.getDate(), FptInOutFlag.OUT, cbCollated.isSelected(), false);
				initTable(list, tb1);
			}
		}
	}

	/**
	 * 批量下载
	 */
	private void downloadBatch() {
		ScmCoc scmCoc = (ScmCoc) this.cbbScmCoc.getSelectedItem();
		if (scmCoc == null) {
			JOptionPane.showMessageDialog(FmFptExgAppHead.this, "请选择转出供应商!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			if (scmCoc.getBrief() == null) {
				JOptionPane.showMessageDialog(FmFptExgAppHead.this, "该供应商的海关编码为空!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String outTradeCode = scmCoc.getBrief().getCode();
			List<FptAppHead> heads = FptQuery.getInstance().findFptAppHeadByOutQp(outTradeCode);
			if (heads != null && !heads.isEmpty()) {
				DownloadBatchTask task = new DownloadBatchTask(heads, outTradeCode);
				task.start();
				//
				// refresh data
				//
				search();
				// showAllData();
			}

		}
	}

	/***
	 * 下载批量任务
	 * 
	 * @author ower
	 * 
	 */
	class DownloadBatchTask extends SwingWorkerTask {
		List<FptAppHead> heads = new ArrayList<FptAppHead>();
		String outTradeCode = null;

		public DownloadBatchTask(List<FptAppHead> heads, String outTradeCode) {
			this.heads = heads;
			this.outTradeCode = outTradeCode;
		}

		/**
		 * 唯一值,及调用方法
		 * 
		 * @param uuid
		 */
		protected void doRun(String taskId) {
			heads = fptManageAction.downloadFptAppHeadsOutByQp(
					new Request(CommonVars.getCurrUser()), taskId, outTradeCode, heads);
		}
	}

	/**
	 * 下载明细
	 */
	private void downloadDetail() {

		if (tableMode1.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定下载对方明细数据?", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			FptAppHead fptAppHead = (FptAppHead) tableMode1.getCurrentRow();
			if (fptAppHead != null) {
				boolean isOut = true;
				if (FptInOutFlag.IN.equals(fptAppHead.getInOutFlag())) {
					isOut = false;
				}
				DownloadDetailTask task = new DownloadDetailTask(fptAppHead, isOut);
				task.start();
			}
		} else {
			JOptionPane.showMessageDialog(this, "请选择要下载对方明细的表头!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/***
	 * 下载申请表明细
	 * 
	 * @author ower
	 * 
	 */
	class DownloadDetailTask extends SwingWorkerTask {
		FptAppHead fptAppHead = null;
		boolean isOut = true;

		public DownloadDetailTask(FptAppHead fptAppHead, boolean isOut) {
			this.fptAppHead = fptAppHead;
			this.isOut = isOut;
		}

		/**
		 * 唯一值,及调用方法
		 * 
		 * @param uuid
		 */
		protected void doRun(String taskId) {
			if (isOut) {
				List<FptAppItem> items = fptManageAction.downloadFptAppItemsOutByQp(new Request(
						CommonVars.getCurrUser()), taskId, fptAppHead);
			} else {
				List<FptAppItem> items = fptManageAction.downloadFptAppItemsInByQp(new Request(
						CommonVars.getCurrUser()), taskId, fptAppHead);
			}
		}
	}

	/**
	 * This method initializes btnResend
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnResend() {
		if (btnResend == null) {
			btnResend = new JButton();
			btnResend.setText("补发报文");
			btnResend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckResend(new Request(CommonVars.getCurrUser()));
					int count = 0;
					try {
						// 上传文件
						count = fptMessageAction.httpUpload(new Request(CommonVars.getCurrUser()));
						if (count == 0) {
							JOptionPane.showMessageDialog(FmFptExgAppHead.this, "上传目录为空，没有可上传的报文",
									"确认", 1);
						}
					} catch (Exception ex) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(FmFptExgAppHead.this,
								"系统补发报文失败，请重新发送 " + ex.getMessage(), "确认", 1);
						return;
					}
					JOptionPane.showMessageDialog(FmFptExgAppHead.this, "系统补发报文成功，一个上传" + count
							+ "个报文", "确认", 1);
				}
			});
		}
		return btnResend;
	}

	/**
	 * This method initializes btnCollated
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCollated() {
		if (btnCollated == null) {
			btnCollated = new JButton();
			btnCollated.setText("结案");
			btnCollated.setEnabled(false);
			btnCollated.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fptManageAction.permissionCheckAppHeadEndCase(new Request(CommonVars
							.getCurrUser()));
					collatedFptAppHead();
				}
			});
		}
		return btnCollated;
	}

	/**
	 * 核销关封
	 */
	private void collatedFptAppHead() {
		if (tableMode1.getCurrentRows() == null) {
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否确定结案", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		List<FptAppHead> fptAppHeadList = tableMode1.getCurrentRows();
		for (int i = 0; i < fptAppHeadList.size(); i++) {
			FptAppHead fah = fptAppHeadList.get(i);
			fah.setIsCollated(true);
			fptManageAction.saveFptAppHead(new Request(CommonVars.getCurrUser()), fah);
		}
	}

	/**
	 * This method initializes cbCollated
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCollated() {
		if (cbCollated == null) {
			cbCollated = new JCheckBox();
			cbCollated.setText("已结案");
			cbCollated.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					if (cbCollated.isSelected()) {// 结案

						List fptAppHeadList = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(
								new Request(CommonVars.getCurrUser()), scmCoc,
								cbbBeginDate.getDate(), cbbEndDate.getDate(), FptInOutFlag.OUT,
								true, false);
						if (fptAppHeadList != null) {
							initTable(fptAppHeadList, tb1);
						}
					} else {// 未结案
						List fptAppHeadList = fptManageAction.findFptAppHeadByIsCollatedAndScmCoc(
								new Request(CommonVars.getCurrUser()), scmCoc,
								cbbBeginDate.getDate(), cbbEndDate.getDate(), FptInOutFlag.OUT,
								false, false);
						if (fptAppHeadList != null) {
							initTable(fptAppHeadList, tb1);
						}
					}
				}
			});
		}
		return cbCollated;
	}

	/**
	 * This method initializes btnCanCollated
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCanCollated() {
		if (btnCanCollated == null) {
			btnCanCollated = new JButton();
			btnCanCollated.setText("撤销结案");
			btnCanCollated.addActionListener(new java.awt.event.ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					fptManageAction.permissionCheckAppHeadCancelEndCase(new Request(CommonVars
							.getCurrUser()));
					canCollatedFptAppHead();
				}

			});
		}
		return btnCanCollated;
	}

	/**
	 * 撤销核销关封
	 */
	private void canCollatedFptAppHead() {

		if (tableMode1.getCurrentRows() == null) {
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否撤销结案", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		List<FptAppHead> fptAppHeadList = tableMode1.getCurrentRows();
		for (int i = 0; i < fptAppHeadList.size(); i++) {
			FptAppHead fah = fptAppHeadList.get(i);
			fah.setIsCollated(false);
			fptManageAction.saveFptAppHead(new Request(CommonVars.getCurrUser()), fah);
		}
	}

	/**
	 * This method initializes jTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getJTree() {
		if (jTree == null) {
			jTree = new JTree();
		}
		return jTree;
	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("QP导入");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportFptApp dg = new DgImportFptApp(FmFptExgAppHead.this);
					dg.setQp(true);
					dg.setFptInOutFlag(FptInOutFlag.OUT);
					dg.setVisible(true);
				}
			});
		}
		return btnImport;
	}


	/**
	 * 下载文档
	 * 
	 * @param parent
	 * @param url
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void dlFile(Component parent, URL url) throws FileNotFoundException, IOException {
		JFileChooser f = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("xls", "xls");
		f.setFileFilter(filter);
		String name = FmMain.getInstance().getDeskPanel().getSelectedFrame().getTitle()
				+ "导入样板.xls";
		f.setSelectedFile(new File(name));
		if (f.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File file = f.getSelectedFile();
//			String path = file.getAbsolutePath();
//			String ext = FilenameUtils.getExtension(path);
//			System.out.print(" >>>>>>>>>> >>>>>>>>>>>> patch " + path);
            String path = file.getAbsolutePath();
            if(path == null || !path.toLowerCase().endsWith(".xls")){
                path = path.concat(".xls");
            }
//			if (ext == null || !ext.equals("xls")) {
//				path = path.concat(".xls");
//			}
			FileOutputStream out = null;
			InputStream in = null;
			try {
				out = new FileOutputStream(new File(path));
				in = url.openStream();
				int i = 0;
				byte[] b = new byte[1024];
				while ((i = in.read(b)) != -1) {
					out.write(b);
				}
				JOptionPane.showMessageDialog(parent, "下载完成！", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} finally {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			}
		}
	}

	/**
	 * This method initializes btnDownloadExcel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownloadExcel() {
		if (btnDownloadExcel == null) {
			btnDownloadExcel = new JButton();
			btnDownloadExcel.setText("下载导入模板");
			btnDownloadExcel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						dlFile(FmFptExgAppHead.this,
								FmFptExgAppHead.this.getClass().getResource(
										"excel/FptOutAppHeadItemImportFormat2.xls"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnDownloadExcel;
	}

	private JButton getBtnReceipt() {
		if (btnReceipt == null) {
			btnReceipt = new JButton("查看回执");
			btnReceipt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					showReceiptResult();
				}
			});
		}
		return btnReceipt;
	}

	private void showReceiptResult() {
		String sysType = FptBusinessType.FPT_APP;
		if (tableMode1.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择要查看回执的申请表", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FptAppHead fptAppHead = (FptAppHead) tableMode1.getCurrentRow();
		String copNo = fptAppHead.getOutCopAppNo();
		FptMessageHelper.getInstance().showFptReceiptResult(sysType, FptInOutFlag.OUT, copNo);
	}

	private JCalendarComboBox getCalendarComboBox() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new Dimension(100, 20));
			cbbBeginDate.setBounds(new Rectangle(73, 3, 110, 28));
		}
		return cbbBeginDate;
	}

	private JCalendarComboBox getCalendarComboBox_1() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(100, 20));
			cbbEndDate.setBounds(new Rectangle(195, 3, 110, 28));
		}
		return cbbEndDate;
	}

	private JTable getTb2() {
		if (tb2 == null) {
			tb2 = new JTable();
			tb2.addMouseListener(new MouseListenerClass());
		}
		return tb2;
	}

	/**
	 * 初始化数据参数 tb
	 */
	@SuppressWarnings("rawtypes")
	private void initTable2(List list, JTable tb) {
		tableMode2 = new JTableListModel(tb, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("客户", "scmCoc.name", 100));
				list.add(addColumn("申报状态", "declareState", 100));// 2
				list.add(addColumn("申报日期", "outDate", 100));
				list.add(addColumn("申请表编号", "appNo", 100));
				list.add(addColumn("电子口岸统一编号", "seqNo", 80));
				list.add(addColumn("转入转出标记", "inOutFlag", 75)); // 1
				list.add(addColumn("修改标记", "modifyMarkState", 100));// 3

				list.add(addColumn("转出企业内部编号", "outCopAppNo", 100));
				list.add(addColumn("企业合同号", "contrNo", 80));
				list.add(addColumn("转出手册/帐册编号", "emsNo", 100));
				
				list.add(addColumn("转入企业名称", "inTradeName", 80));
				list.add(addColumn("转入手册/帐册编号", "inEmsNo", 100));
				
				list.add(addColumn("是否结案", "isCollated", 50));// 6
				list.add(addColumn("申请表类型", "appClass", 80));

				list.add(addColumn("转出企业代码", "outTradeCode", 150));
				list.add(addColumn("转出企业名称", "outTradeName", 60));
				list.add(addColumn("转出地", "outDistrict.name", 50));
				list.add(addColumn("转入企业代码", "inTradeCode", 80));

				list.add(addColumn("转入地", "inDistrict.name", 100));
				list.add(addColumn("转出地海关", "outCustoms.name", 100));

				list.add(addColumn("转出企业批准证编号", "outLiceNo", 100));
				list.add(addColumn("转出申报企业代码", "outTradeCode2", 100));
				list.add(addColumn("转出申报企业名称", "outTradeName2", 100));

				list.add(addColumn("申报企业9位组织机构代码", "outAgentCode", 100));
				list.add(addColumn("申报企业组织机构名称", "outAgentName", 100));
				list.add(addColumn("预计运输耗时（天）", "conveyDay", 100));
				list.add(addColumn("送货距离（公里）", "conveySpa", 100));
				list.add(addColumn("转出企业法人/联系电话", "outCorp", 100));
				list.add(addColumn("转出申报人/联系电话", "outDecl", 100));
				list.add(addColumn("转出备注", "outNote", 100));

				list.add(addColumn("转入企业内部编号", "inCopAppNo", 100));
				list.add(addColumn("转入地海关", "inCustoms.name", 100));
				list.add(addColumn("转入申报企业9位组织机构代码", "inAgentCode", 100));
				list.add(addColumn("转入申报企业组织机构名称", "inAgentName", 100));
				list.add(addColumn("转入企业法人/联系电话", "inCorp", 100));
				list.add(addColumn("转入申报人/联系电话", "inDecl", 100));
				list.add(addColumn("转入备注", "inNote", 100));
				list.add(addColumn("转入企业批准证编号", "inLiceNo", 100));
				list.add(addColumn("转入申报日期", "inDate", 100));
				list.add(addColumn("转入申报企业代码", "inTradeCode2", 100));
				list.add(addColumn("转入申报企业名称", "inTradeName2", 100));
				list.add(addColumn("录入员", "aclUser.userName", 100));
				list.add(addColumn("手册类型", "projectType", 100));
				list.add(addColumn("修改人", "modifyPeople", 100));
				list.add(addColumn("修改日期", "modifyDate", 100));
				list.add(addColumn("作废说明", "marke", 160));
				return list;
			}
		});
		tb.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return DeclareState.getDeclareStateSpec(value.toString());
			}
		});
		tb.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				// super.getTableCellRendererComponent(table, value,
				// isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return FptInOutFlag.getNote(value.toString());
			}
		});

		tb.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {

				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return ModifyMarkState.getModifyMarkSpec(value.toString());
			}
		});
		tb.getColumnModel().getColumn(13).setCellRenderer(new TableCheckBoxRender());

		tb.getColumnModel().getColumn(14).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return AppClass.getNote(value.toString());
			}
		});

		tb.getColumnModel().getColumn(43).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				if (isSelected) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				super.setText((value == null) ? "" : castValue(value));
				return this;
			}

			private String castValue(Object value) {
				return ProjectType.getNote(Integer.valueOf(value.toString()));
			}
		});
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("作废");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(null == tableMode1)
						return ;
					
					FptAppHead fptAppHead = (FptAppHead) tableMode1.getCurrentRow();
					if(null == fptAppHead){
							JOptionPane.showMessageDialog(FmFptExgAppHead.this, "请选择数据", "提示",JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					boolean isGo = fptManageAction.isExistFptFptBillHeadByAppNo(request, fptAppHead.getAppNo(), FptInOutFlag.OUT);
					if(isGo){
						JOptionPane.showMessageDialog(FmFptExgAppHead.this, "已经存在发货单数据 不可作废", "提示",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmFptExgAppHead.this, "特此声明：此功能操作后的影响由企业自己负责。请谨慎使用！ 确认作废吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					
//					if(JOptionPane.YES_NO_OPTION != JOptionPane.showOptionDialog(FmFptExgAppHead.this, "假如导入数据，将删除本批次下的 历史数据！","提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否"));
					DgFptAppCancel dg = new DgFptAppCancel();
					dg.setVisible(true);
					String marke = dg.getNote();
					if (null != marke && !"".equals(marke)) {
						fptAppHead.setMarke(marke);
						fptAppHead.setDeclareState(DeclareState.IS_CAN);
						fptManageAction.saveFptAppHead(request, fptAppHead);
						tableMode1.deleteRow(fptAppHead);
						if(tableMode2!=null){
							tableMode2.addRow(fptAppHead);
						}
					}
				}
			});
		}
		return btnCancel;
	}
	private JButton getBtnExcelImport() {
		if (btnExcelImport == null) {
			btnExcelImport = new JButton("EXCEL导入");
			btnExcelImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgImportFptApp dg = new DgImportFptApp(FmFptExgAppHead.this);
					dg.setFptInOutFlag(FptInOutFlag.OUT);
					dg.setVisible(true);
					File file = dg.getExcelFile();
					Date endDate = dg.getCbbEndDate().getDate();
					if(dg.isOk()){
						new ImportFileDataRunnable(file,endDate).execute();
					}
				}
			});
		}
		return btnExcelImport;
	}
	
	/**
	 * 打开文件
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileDataRunnable extends SwingWorker {

		File file = null;
		Date endDate = null;
		
		public ImportFileDataRunnable(File file,Date endDate){
			this.file = file;
			this.endDate = endDate;
		}
		
		@Override
		protected Object doInBackground() throws Exception {
			List returnList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(FmFptExgAppHead.this);
				CommonProgress.setMessage("系统正在打开文件资料，请稍后...");
				returnList = parseTxtFile(this.file,this.endDate);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmFptExgAppHead.this,
						"打开文件失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return returnList;
		}

		@Override
		protected void done() {
			List list = null;
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			JTableListModel tableModel = (JTableListModel) tb1.getModel();
			tableModel.addRows(list);
		}
	}
	
	private List parseTxtFile(File file,Date endDate) {

		String suffix = getSuffix(file);
		List<List> lines = null;
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//			
			lines = FileReading.readExcel(file, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(file, 0, "GBK");
		}
		List<ScmCoc> scmCocs = new DgCommon().getCustomer();
		Map<String,ScmCoc> mapScmCoc = new HashMap<String,ScmCoc>();
		for (int i = 0; i < scmCocs.size(); i++) {
			mapScmCoc.put(scmCocs.get(i).getCode(), scmCocs.get(i));
		}
		
		List<District> districts = CustomBaseList.getInstance().getDistrict();
		Map<String,District> mapDistrict = new HashMap<String,District>();
		for (int i = 0; i < districts.size(); i++) {
			mapDistrict.put(districts.get(i).getCode(), districts.get(i));
		}
		
		List<FptAppHead> heads = new ArrayList<FptAppHead>();
		List<FptAppItem> items = new ArrayList<FptAppItem>();
		Map<String,FptAppHead> mapHead = new HashMap<String,FptAppHead>();
		for (int i = 0; i < lines.size(); i++) {
			if (i == 0) {
				continue;
			}
			
			List line = lines.get(i);
			
			String[] strs = new String[line.size()];
			for (int j = 0; j < strs.length; j++) {
				Object obj = line.get(j);
				strs[j] = obj == null?""+j : obj.toString();
			}
			
			FptAppHead fptAppHead = mapHead.get(strs[3]);
			if(fptAppHead==null){
				fptAppHead = this.fptManageAction.newFptAppHead(
						new Request(CommonVars.getCurrUser()), FptInOutFlag.OUT);
				ScmCoc scmCoc = mapScmCoc.get(strs[1]);
				if(scmCoc==null){
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(FmFptExgAppHead.this, "客户找不到");
					return new ArrayList();
				}
				Brief brief = scmCoc.getBrief();
				if(brief!=null){
					fptAppHead.setInTradeCode(brief.getCode());
					fptAppHead.setInTradeName(brief.getName());
				}
				fptAppHead.setInDistrict(scmCoc.getSrcDistrict());
				Integer converDay = NumberUtils.toInt(
						scmCoc.getTransportationTime()==null?"0":scmCoc.getTransportationTime().toString());
				fptAppHead.setConveyDay(converDay);
				Integer conveySpa = NumberUtils.toInt(
						scmCoc.getDeliveryDistance()==null?"0":scmCoc.getDeliveryDistance().toString());
				fptAppHead.setConveySpa(conveySpa);
				fptAppHead.setScmCoc(scmCoc);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					if(!"".equals(strs[2])){
						fptAppHead.setOutDate(dateFormat.parse(strs[2]));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fptAppHead.setOutCopAppNo(strs[3]);
				fptAppHead.setSeqNo(strs[4]);
				fptAppHead.setAppNo(strs[5]);
				fptAppHead.setEmsNo(strs[6]);
				fptAppHead.setOutTradeCode2(strs[7]);
				fptAppHead.setOutDistrict(mapDistrict.get(strs[8]));
				fptAppHead.setEndDate(endDate);
				mapHead.put(strs[3], fptAppHead);
				heads.add(fptAppHead);
			}
			FptAppItem fptAppItem = new FptAppItem();
			if(strs[9]!=null&&!"".equals(strs[9].trim())){
				fptAppItem.setListNo(Integer.parseInt(strs[9]));
			}
			if(strs[10]!=null&&!"".equals(strs[10].trim())){
				fptAppItem.setTrNo(Integer.parseInt(strs[10]));
			}
			if(strs[11]!=null&&!"".equals(strs[11].trim())){
				fptAppItem.setQty(Double.valueOf(strs[11]));
			}
			if(strs[12]!=null&&!"".equals(strs[12].trim())){
				fptAppItem.setQty1(Double.valueOf(strs[12]));
			}
			fptAppItem.setNote(strs[13]);
			fptAppItem.setFptAppHead(fptAppHead);
			
			if(!fillFptAppItem(fptAppHead,fptAppItem)){
				CommonProgress.closeProgressDialog();
				//填充数据失败
				JOptionPane.showMessageDialog(this, "未找到成品商品项号为："+fptAppItem.getTrNo()+"的成品！");
				fptManageAction.deleteFptAppHead(request, fptAppHead);
				heads.remove(fptAppHead);
				for (int j = 0; j < heads.size(); j++) {
					fptManageAction.deleteFptAppHead(request,heads.get(j));
				}
				return new ArrayList<Object>();
			}
			items.add(fptAppItem);
		}
		fptManageAction.saveFptAppItem(new Request(CommonVars.getCurrUser(), true), items);
		fptManageAction.saveFptAppHeads(request, heads);
		return heads;
	}
	
	public boolean fillFptAppItem(FptAppHead fptAppHead,FptAppItem f){
		int projectType = fptAppHead.getProjectType();
		String emsNoOut = fptAppHead.getEmsNo();
		
		switch (projectType) {
		case ProjectType.BCS:
			List<ContractExg> contractExgs = fptManageAction.findFptAppItemExgBySeqNum(new Request(CommonVars.getCurrUser(), true),emsNoOut, f.getTrNo(),ProjectType.BCS);
			if(contractExgs.size()>0){
				ContractExg contractExg = contractExgs.get(0);
				f.setCompany(CommonUtils.getCompany());
				f.setCodeTs(contractExg.getComplex());
				f.setName(contractExg.getName());
				f.setSpec(contractExg.getSpec());
				f.setUnit(contractExg.getUnit());
				f.setUnit1(contractExg.getComplex().getFirstUnit());
				f.setUnitPrice(contractExg.getDeclarePrice());
				f.setInOutFlag(fptAppHead.getInOutFlag());
				f.setModifyMarkState(ModifyMarkState.ADDED);
			}else{
				return false;
			}
			break;
		case ProjectType.BCUS:
			List<EmsHeadH2kExg> emsHeadH2kExgs = fptManageAction.findFptAppItemExgBySeqNum(new Request(CommonVars.getCurrUser(), true),emsNoOut, f.getTrNo(),ProjectType.BCUS);
			if(emsHeadH2kExgs.size()>0){
				EmsHeadH2kExg emsHeadH2kExg = emsHeadH2kExgs.get(0);
				f.setCompany(CommonUtils.getCompany());
				f.setCodeTs(emsHeadH2kExg.getComplex());
				f.setName(emsHeadH2kExg.getName());
				f.setSpec(emsHeadH2kExg.getSpec());
				f.setUnit(emsHeadH2kExg.getUnit());
				f.setUnit1(emsHeadH2kExg.getComplex().getFirstUnit());
				f.setUnitPrice(emsHeadH2kExg.getDeclarePrice());
				f.setInOutFlag(fptAppHead.getInOutFlag());
				f.setModifyMarkState(ModifyMarkState.ADDED);
			}else{
				return false;
			}
			break;
		case ProjectType.DZSC:
			List<DzscEmsExgBill> dzscEmsExgBills = fptManageAction.findFptAppItemExgBySeqNum(new Request(CommonVars.getCurrUser(), true),emsNoOut, f.getTrNo(),ProjectType.DZSC);
			if(dzscEmsExgBills.size()>0){
				DzscEmsExgBill dzscEmsExgBill = dzscEmsExgBills.get(0);
				f.setCompany(CommonUtils.getCompany());
				f.setCodeTs(dzscEmsExgBill.getComplex());
				f.setName(dzscEmsExgBill.getName());
				f.setSpec(dzscEmsExgBill.getSpec());
				f.setUnit(dzscEmsExgBill.getUnit());
				f.setUnit1(dzscEmsExgBill.getComplex().getFirstUnit());
				f.setInOutFlag(fptAppHead.getInOutFlag());
				f.setModifyMarkState(ModifyMarkState.ADDED);
			}else{
				return false;
			}
			break;
		}
		return true;
	}
	
	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
