package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.report.CustomsEnvelopSubReportDataSource;
import com.bestway.common.constant.CanelSort;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptCancelBill;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 进出货转厂单据
 * 
 * by check lxr 2008.09.09
 * 
 * @author lxr
 * 
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FmFptExgBillHead extends FmCommon {

	private JPanel contentPn = null;

	private FptBillHead billHead = null;

	// private JTableListModel tableModelImEx = null;
	// private JTableListModel tableModelImExCancel = null;
	/**
	 * 发货tableModel
	 */
	private JTableListModel tableModelExport = null;
	/**
	 * 撤消tableModel
	 */
	private JTableListModel tableModelCancel = null;

	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnShow = null;
	private JButton btnApply = null;
	private JButton btnProcess = null;
	private JButton btnOther = null;
	private JButton btnCopy = null;
	private JButton btnClose = null;
	private JButton btnChange = null;
	private JButton btnRefresh = null;
	private JButton btSreach = null;

	private FptManageAction fptManageAction = null;
	private JTabbedPane tpnPane = null;
	private JPanel pn3 = null;
	private JPanel pn4 = null;
	private JLabel lbInputTime = null;
	private JLabel lbTo = null;

	private JPopupMenu pmOther = null;
	private JMenuItem miCasToFpt = null;
	private JMenuItem importData = null;
	private JMenuItem miFptToCustoms = null;

	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;

	private JScrollPane spn0 = null;
	private JScrollPane spn4 = null;
	private JScrollPane spn6 = null;

	private JTable tbExport = null;
	private JTable tbCancel = null;
	private JLabel lbCustomer = null;

	private JLabel lbSendNo = null;
	private JTextField tfSenNo = null;
	private JButton btnQuery = null;

	private JToolBar tbBarTop = null;
	private JToolBar tb1 = null;

	private JComboBox cbbCustomer = null;

	private JPanel pn = null;
	private JPanel pn1 = null;

	private FptMessageAction fptMessageAction = null;

	private JButton btnResend = null; // @jve:decl-index=0:visual-constraint="106,666"

	private JButton btnPrint = null;
	private JPopupMenu pmPrint = null;

	private JMenuItem getMiPrintTrue = null;
	private JMenuItem getMiPrintFalse = null;
	private List subReportData = null;

	private final static int PRINT_STYPE_A = 1;
	private final static int PRINT_STYPE_B = 2;
	private final static int PRINT_STYPE_C = 3;

	//
	// /**单据类型*/
	// private Integer billType = Integer.valueOf(0); // @jve:decl-index=0:

	private String fptInOutFlag = FptInOutFlag.OUT; // @jve:decl-index=0:
	private String fptBusinessType = FptBusinessType.FPT_BILL; // @jve:decl-index=0:
	private JButton btnReceipt;

	public FmFptExgBillHead() {

		super();

		fptMessageAction = (FptMessageAction) CommonVars
				.getApplicationContext().getBean("fptMessageAction");

		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");

		fptManageAction.permissionCheckOutBillGlance(new Request(CommonVars
				.getCurrUser()));

		initialize();

		cbbBeginDate.setDate(CommonVars.getBeginDate());

		cbbEndDate.setDate(new Date());

		initCustomer();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		setContentPane(getContentPn());

		setSize(771, 503);

		setTitle("成品退货单");

		addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				initTableExport(getDataSource());
				setState();
			}
		});
	}

	/**
	 * 初始化客户/供应商
	 */
	private void initCustomer() {
		// if (tpnPane.getSelectedIndex() == 0) {
		// list = materialManageAction.findScmManuFactoryIn(new Request(
		// CommonVars.getCurrUser(), true));
		//
		// } else if (tpnPane.getSelectedIndex() == 1) {
		// list = materialManageAction.findScmManuFactoryOut(new Request(
		// CommonVars.getCurrUser(), true));
		// }
		// this.cbbCustomer.removeAllItems();
		// this.cbbCustomer.setModel(new DefaultComboBoxModel(list.toArray()));

		DefaultComboBoxModel scmCocs = new DefaultComboBoxModel();
		scmCocs = new DefaultComboBoxModel(super.getCustomer().toArray());
		this.cbbCustomer.setModel(scmCocs);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbCustomer, "code", "name", 280);
		this.cbbCustomer.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 110, 170));
		this.cbbCustomer.setSelectedItem(null);
	}

	/**
	 * 设置状态
	 */
	private void setState() {

		if (tpnPane.getSelectedIndex() == 0) {// 转出

			setStateInorOut();

		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消

			setStateCancel();
		}
	}

	/**
	 * 设置转出转入的状态
	 */
	private void setStateInorOut() {
		// btnCopy.setVisible(true);
		// this.btnShow.setVisible(true);
		// btnOther.setVisible(true);
		// pn3.setVisible(true);
		// FptBillHead fptBillHead = (FptBillHead)
		// this.tableModelExport.getCurrentRow();
		// if(fptBillHead != null){
		// if(DeclareState.APPLY_POR.equals(fptBillHead.getAppState())){
		// btnEdit.setEnabled(true);
		// }
		// }
		// ItemEnabled("");
		btnCopy.setVisible(true);

		btnShow.setVisible(true);

		btnOther.setVisible(true);

		pn3.setVisible(true);

		FptBillHead c = (FptBillHead) tableModelExport.getCurrentRow();

		if (c == null) {
			return;
		}

		boolean isProcessExe = (c.getAppState() != null && c.getAppState()
				.equals(DeclareState.PROCESS_EXE));

		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(c.getAppState())
				|| DeclareState.CHANGING_EXE.equals(c.getAppState()));

		btnCopy.setEnabled((DeclareState.APPLY_POR.equals(c.getAppState()) || DeclareState.PROCESS_EXE
				.equals(c.getAppState())));

		btnEdit.setEnabled(!isProcessExe
				&& (DeclareState.APPLY_POR.equals(c.getAppState()) || DeclareState.CHANGING_EXE
						.equals(c.getAppState())));

		btnChange.setEnabled(isProcessExe);

		btnApply.setEnabled((DeclareState.APPLY_POR.equals(c.getAppState()) || DeclareState.CHANGING_EXE
				.equals(c.getAppState())));

		btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(c.getAppState()));

	}

	// private void ItemEnabled(String flag) {
	// if (tableModelCancel == null) {
	// return;
	// }
	// FptCancelBill c = (FptCancelBill) tableModelCancel.getCurrentRow();
	// boolean isProcessExe = (c.getDeclareState() != null && c
	// .getDeclareState().equals(DeclareState.PROCESS_EXE));
	// btnDelete.setEnabled(isProcessExe);
	// btnEdit.setEnabled(isProcessExe);
	// this.btnApply.setEnabled(isProcessExe);
	// btnChange.setEnabled(isProcessExe);
	// }
	/**
	 * 设置撤消的状态
	 * 
	 * @return
	 */
	private void setStateCancel() {
		// 屏闭一些按钮
		btnCopy.setVisible(false);
		btnOther.setVisible(false);
		this.btnShow.setVisible(false);
		pn3.setVisible(false);
		FptCancelBill c = (FptCancelBill) tableModelCancel.getCurrentRow();
		if (c == null) {
			return;
		}
		boolean isProcessExe = (c.getDeclareState() != null && c
				.getDeclareState().equals(DeclareState.PROCESS_EXE));
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(c.getDeclareState())
				|| DeclareState.CHANGING_EXE.equals(c.getDeclareState()));
		btnEdit.setEnabled(!isProcessExe
				&& (DeclareState.APPLY_POR.equals(c.getDeclareState()) || DeclareState.CHANGING_EXE
						.equals(c.getDeclareState())));
		this.btnApply.setEnabled((DeclareState.APPLY_POR.equals(c
				.getDeclareState()) || DeclareState.CHANGING_EXE.equals(c
				.getDeclareState())));
		this.btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(c
				.getDeclareState()));
		btnChange.setEnabled(isProcessExe);

	}

	/**
	 * 获取转出转入数据来源
	 * 
	 * @return
	 */
	private List getDataSource() {
		List list = null;

		if (tpnPane.getSelectedIndex() == 0) { // 转出 --发货
			list = fptManageAction.findInOutFptBillHeadByType(new Request(
					CommonVars.getCurrUser()), fptInOutFlag, fptBusinessType,
					cbbBeginDate.getDate(), cbbEndDate.getDate(), null, null,
					null, (ScmCoc) cbbCustomer.getSelectedItem());
		}

		return list;
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
			contentPn.add(getJToolBar(), BorderLayout.NORTH);
			contentPn.add(getPn(), BorderLayout.CENTER);
		}
		return contentPn;
	}

	/**
	 * 初始化数据转出Table
	 */
	private void initTableExport(List list) {

		tableModelExport = new JTableListModel(tbExport, list,
				new JTableListModelAdapter() {

					public List<JTableListColumn> InitColumns() {

						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("序号", "serialNumber", 50));
						list.add(addColumn("客户", "customer.name", 90));
						list.add(addColumn("发货企业内部编号", "issueCopBillNo", 180));
						list.add(addColumn("申报状态", "appState", 60));
						list.add(addColumn("发货单编号", "billNo", 100));
						list.add(addColumn("统一编号", "seqNo", 100));
						list.add(addColumn("申请表编号", "appNo", 80));
						list.add(addColumn("已转报关单流水号",
								"makeCustomsDeclarationCode", 100));
						list.add(addColumn("转出企业手册/账册号", "outEmsNo", 100));
						list.add(addColumn("单据类型", "sysType", 60));
						list.add(addColumn("转出标志", "billSort", 60));
						list.add(addColumn("是否撤销", "isCancel", 50));
						list.add(addColumn("申报时间", "issueIsDeclaDate", 80));
						list.add(addColumn("转入企业名称", "receiveTradeName", 100));
						list.add(addColumn("录入时间", "createDate", 60));
						list.add(addColumn("录入人员", "aclUser.userName", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});

		tbExport.getColumnModel().getColumn(4)
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
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});

		tbExport.getColumnModel().getColumn(9)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptBusinessType
								.getFptBusinessTypeDesc(value.toString()));
						return this;
					}
				});
		tbExport.getColumnModel().getColumn(10)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString()));
						return this;
					}
				});
		tbExport.getColumnModel().getColumn(11)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						boolean isCancel = (value == null ? false : Boolean
								.valueOf(value.toString()));
						super.setText(isCancel ? "已撤销" : "");
						return this;
					}
				});
	}

	/**
	 * 初始化撤消table
	 */
	public void ininTableCancel() {
		List list = fptManageAction.findFptCancelBill(
				new Request(CommonVars.getCurrUser()),
				FptBusinessType.FPT_BILL, FptInOutFlag.OUT);
		tableModelCancel = new JTableListModel(tbCancel, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("申请单编号", "appNo", 130));
						list.add(addColumn("转出企业内部编号", "copNo", 150));
						list.add(addColumn("电子口岸统一编号", "seqNo", 130));
						list.add(addColumn("企业编号", "tradeCode", 100));
						list.add(addColumn("发货单编号", "billNo", 160));
						list.add(addColumn("撤消类型", "canelSort", 80));
						list.add(addColumn("申报状态", "declareState", 70));
						list.add(addColumn("备注", "note", 160));
						return list;
					}
				});

		tbCancel.getColumnModel().getColumn(6)
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
						return CanelSort.getCanelSortState(value.toString());
					}
				});

		tbCancel.getColumnModel().getColumn(7)
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
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});

	}

	/**
	 * 删除
	 */
	private void deleteData() {
		if (tpnPane.getSelectedIndex() == 0) {// 转出
			deleteInorOutData();
		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消
			deleteCancelData();
		}
	}

	/**
	 * 删除转入转出资料
	 */
	private void deleteInorOutData() {
		if (tableModelExport.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
			super.fptManageAction.deleteFptBillHead(
					new Request(CommonVars.getCurrUser()),
					(FptBillHead) tableModelExport.getCurrentRow());
			tableModelExport.deleteRow(tableModelExport.getCurrentRow());
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示", 0);
		}
	}

	/**
	 * 删除撤消
	 */
	private void deleteCancelData() {
		if (tableModelCancel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要删除的资料!", "提示", 0);
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!!!", "提示", 0) != 0) {
			return;
		}
		FptCancelBill data = (FptCancelBill) tableModelCancel.getCurrentRow();
		fptManageAction.deleteFptCancelBill(
				new Request(CommonVars.getCurrUser()), data);
		tableModelCancel.deleteRow(data);
	}

	/**
	 * This method initializes tbBarTop
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (tbBarTop == null) {
			tbBarTop = new JToolBar();
			tbBarTop.add(getBtnAdd());
			tbBarTop.add(getBtnEdit());
			tbBarTop.add(getBtnDelete());
			tbBarTop.add(getBtnShow());
			tbBarTop.add(getBtnApply());
			tbBarTop.add(getBtnResend());
			tbBarTop.add(getBtnProcess());
			tbBarTop.add(getBtnReceipt());
			tbBarTop.add(getBtnCopy());
			tbBarTop.add(getBtnChange());
			tbBarTop.add(getBtSreach());

			tbBarTop.add(getBtnPrint());

			tbBarTop.add(getBtnOther());
			tbBarTop.add(getBtnClose());
		}
		return tbBarTop;
	}

	/**
	 * 报表打印
	 * 
	 * @return
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印 ");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmPrint().show(btnPrint, 0, btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	// 打印弹出菜单
	private JPopupMenu getPmPrint() {
		if (pmPrint == null) {
			pmPrint = new JPopupMenu();
			pmPrint.setBorder(javax.swing.BorderFactory
					.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			// pmPrint.add(getMiPrintTrue());
			Separator separator0 = new Separator();
			separator0.setForeground(Color.gray);
			pmPrint.add(getMiPrintFalse());
		}
		return pmPrint;
	}

	// 打印弹出子菜单套打
	private JMenuItem getMiPrintTrue() {
		if (getMiPrintTrue == null) {
			getMiPrintTrue = new JMenuItem();
			getMiPrintTrue.setText("套打成品发货单");
			getMiPrintTrue.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgBillHead.PRINT_STYPE_A, true);
				}
			});
		}
		return getMiPrintTrue;
	}

	// 打印弹出子菜单非套打
	private JMenuItem getMiPrintFalse() {
		if (getMiPrintFalse == null) {
			getMiPrintFalse = new JMenuItem();
			getMiPrintFalse.setText("非套打成品发货单");
			getMiPrintFalse.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptExgBillHead.PRINT_STYPE_A, true);
				}
			});
		}
		return getMiPrintFalse;
	}

	/**
	 * 报表打印
	 * 
	 * 发货 tableModelExport 撤消 tableModelCancel 非套打:printType = true 套打:false
	 */
	@SuppressWarnings("rawtypes")
	private void printReport(int flag, Boolean printType) {
		// if (this.tableModelExport.getCurrentRow() == null) {
		// JOptionPane.showMessageDialog(null, "请选择要打印的申请表!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		//
		// FptBillHead fptBillHead =
		// (FptBillHead)this.tableModelExport.getCurrentRow();
		// String sysType = "";
		// List outlist = fptManageAction
		// .findFptBillDictItemCommodityInfo(
		// new Request(CommonVars
		// .getCurrUser()),
		// fptBillHead.getId(), FptInOutFlag.OUT ,sysType);
		//
		// System.out.println(outlist.size()+"List size ...FptInOutFlag.OUT >> Head 成品（发）发货");
		// CustomReportDataSource ds = new CustomReportDataSource(outlist);
		// printReportByCustomsEnvelopForOld(ds, false, printType,fptBillHead);
		// this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		FptBillHead fptBillHead = new FptBillHead();

		if (tpnPane.getSelectedIndex() == 0) {// 转出

			if (tableModelExport.getCurrentRow() == null) {

				JOptionPane.showMessageDialog(null, "请选择要打印的收发货单!!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			fptBillHead = (FptBillHead) tableModelExport.getCurrentRow();

		} else if (tpnPane.getSelectedIndex() == 1) {
			JTableListModel tableModel = (JTableListModel) tbCancel.getModel();
			FptCancelBill fptCancelBill = (FptCancelBill) tableModel
					.getCurrentRow();
			String copNo = fptCancelBill.getCopNo();
			JTableListModel tableModelExp = (JTableListModel) tbExport
					.getModel();
			List list = tableModelExp.getList();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof FptBillHead) {
					FptBillHead head = (FptBillHead) list.get(i);
					if (head.getIssueCopBillNo().equals(copNo)) {
						fptBillHead = head;
						break;
					}
				}
			}
		}

		// List outTempList = fptManageAction.findFptBillItemCommodityInfo(new
		// Request(CommonVars.getCurrUser()), fptBillHead.getId(),
		// FptInOutFlag.OUT );
		// List inTempList = fptManageAction.findFptBillItemCommodityInfo(new
		// Request(CommonVars.getCurrUser()), fptBillHead.getId(),
		// FptInOutFlag.IN );
		// FptReportUtils.setInList(inTempList);
		// FptReportUtils.setOutList(outTempList);
		List list = new ArrayList();

		list.add(tableModelExport.getCurrentRow());

		CustomReportDataSource ds = new CustomReportDataSource(list);

		printFptInOutBillReport(ds, fptBillHead);

	}

	/**
	 * 成品打印收(退)货登记
	 */
	private void printReportByCustomsEnvelopForOld(CustomReportDataSource ds,
			boolean isCustomsEnvelopNoPrint, boolean isOverprint, Object object) {
		try {

			InputStream masterReportStream = FmFptExgBillHead.class
					.getResourceAsStream("report/FptExgInOutBillHeadRecordReport.jasper");
			FptBillHead fptBillHead = (FptBillHead) object;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverPrint", new Boolean(isOverprint));
			parameters.put("isHead", new Boolean(true));
			parameters.put("appNo", fptBillHead.getAppNo());
			parameters.put("billNo", fptBillHead.getBillNo());
			parameters.put("issueTradeCod", fptBillHead.getIssueTradeCod());
			parameters.put("issueTradeName", fptBillHead.getIssueTradeName());
			// parameters.put("issueDate",
			// fptBillHead.getIssueDate().toString());
			parameters.put(
					"issueDate",
					fptBillHead.getIssueDate() == null ? " "
							: new SimpleDateFormat("yyyy-MM-dd")
									.format(fptBillHead.getIssueDate()));
			parameters.put("outEmsNo", fptBillHead.getOutEmsNo());

			// System.out.println(" 成品* "+fptBillHead.getAppNo()+" * "+fptBillHead.getIssueDate().toString()
			// );
			// 非套打:isOverprint = true 套打:..
			parameters.put("isOverPrint", new Boolean(isOverprint));
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);

			viewer.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	/**
	 * 深加工结转收发货单
	 */
	private void printFptInOutBillReport(CustomReportDataSource ds,
			FptBillHead fptBillHead) {
		try {
			InputStream reportStream = null;
			InputStream fptInBillSubReportStream = null;
			InputStream fptOutBillSubReportStream = null;
			List items = null;
			if (fptBillHead != null) {

				items = fptManageAction.findTransferFactoryCommodityInfo(
						new Request(CommonVars.getCurrUser()),
						fptBillHead.getId());

				subReportData = items;
			}

			CustomsEnvelopSubReportDataSource.setSubReportData(subReportData);

			reportStream = FmFptExgBillHead.class
					.getResourceAsStream("report/FptInOutBillReport.jasper");

			fptInBillSubReportStream = FmFptExgBillHead.class
					.getResourceAsStream("report/FptInOutBillReportIn.jasper");

			fptOutBillSubReportStream = FmFptExgBillHead.class
					.getResourceAsStream("report/FptInOutBillReportOut.jasper");

			JasperReport fptInBillSubReport = (JasperReport) JRLoader
					.loadObject(fptInBillSubReportStream);

			JasperReport fptOutBillSubReport = (JasperReport) JRLoader
					.loadObject(fptOutBillSubReportStream);

			Map<String, Object> parameters = new HashMap<String, Object>();

			FptReportUtils.getParamMapFromBean(parameters, fptBillHead, "");

			FptAppHead fptAppHead = fptManageAction.findFptAppHeadAppNo(
					new Request(CommonVars.getCurrUser()), FptInOutFlag.OUT,
					fptBillHead.getAppNo());

			if (fptAppHead != null) {
				parameters.put("contrNo", fptAppHead.getContrNo() == null ? ""
						: fptAppHead.getContrNo());
			}
			parameters.put("dsOut", FptInOutFlag.OUT);

			parameters.put("dsIn", FptInOutFlag.IN);

			parameters.put("fptInBillSubReport", fptInBillSubReport);

			parameters.put("fptOutBillSubReport", fptOutBillSubReport);

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					reportStream, parameters, ds);

			DgReportViewer viewer = new DgReportViewer(jasperPrint);

			viewer.setVisible(true);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////

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
					fptManageAction.permissionCheckOutBillAdd(new Request(
							CommonVars.getCurrUser()));
					addData();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 新增公共
	 */
	private void addData() {
		if (tpnPane.getSelectedIndex() == 0) {// 转出
			addInOrOutData();
		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消
			addCancelData();
		}
	}

	/**
	 * 增加转入转出资料
	 */
	private void addInOrOutData() {
		FptBillHead head = null;
		head = fptManageAction.newFptBillHead(
				new Request(CommonVars.getCurrUser()), this.fptInOutFlag,
				this.fptBusinessType);
		tableModelExport.addRow(head);
		setState();

	}

	/**
	 * 增加撤消资料
	 */
	private void addCancelData() {

		List<FptCancelBill> rlist = new ArrayList<FptCancelBill>();
		List lists = FptQuery.getInstance().findFptBillHeadForCancel(
				fptBusinessType, fptInOutFlag);
		if (lists == null) {
			return;
		}
		for (int i = 0; i < lists.size(); i++) {
			FptBillHead head = (FptBillHead) lists.get(i);
			FptCancelBill fptCancelBill = new FptCancelBill();
			fptCancelBill.setAppNo(head.getAppNo());
			fptCancelBill.setSeqNo(head.getSeqNo());
			if (FptBusinessType.FPT_BILL.equals(head.getSysType())
					&& FptInOutFlag.OUT.equals(head.getBillSort())) {
				fptCancelBill.setTradeCode(head.getIssueTradeCod());
				fptCancelBill.setCanelSort("0");
				fptCancelBill.setCopNo(head.getIssueCopBillNo());
			} else if (FptBusinessType.FPT_BILL_BACK.equals(head.getSysType())
					&& FptInOutFlag.IN.equals(head.getBillSort())) {
				fptCancelBill.setTradeCode(head.getReceiveTradeCod());
				fptCancelBill.setCanelSort("1");
				fptCancelBill.setCopNo(head.getReceiveCopBillNo());
			}
			fptCancelBill.setSysType(head.getSysType());
			fptCancelBill.setBillSort(head.getBillSort());
			fptCancelBill.setDeclareState(DeclareState.APPLY_POR);
			fptCancelBill.setBillNo(head.getBillNo());
			fptCancelBill = fptManageAction.saveFptCancelBill(new Request(
					CommonVars.getCurrUser()), fptCancelBill);
			rlist.add(fptCancelBill);
		}
		tableModelCancel.addRows(rlist);
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
					fptManageAction.permissionCheckOutBillEdit(new Request(
							CommonVars.getCurrUser()));
					editData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 修改数据
	 */
	public void editData() {
		if (tpnPane.getSelectedIndex() == 0) {// 转出
			editinorOutData();
		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消
			editCancelData();
		}
	}

	/**
	 * 修改转入转出资料
	 */
	public void editinorOutData() {

		if (tableModelExport.getCurrentRow() == null) {

			JOptionPane.showMessageDialog(FmFptExgBillHead.this, "请选择要修改的数据",
					"提示", 0);
			return;

		}

		DgFptBillHead dgFptBillItem = new DgFptBillHead();

		billHead = (FptBillHead) tableModelExport.getCurrentRow();

		billHead = fptManageAction.findFptBillHeadById(
				new Request(CommonVars.getCurrUser()), billHead.getId());

		tableModelExport.updateRow(billHead);

		if (billHead.getAppState().equals(DeclareState.PROCESS_EXE)
				|| billHead.getAppState().equals(DeclareState.WAIT_EAA)) {

			dgFptBillItem.setDataState(DataState.BROWSE);

		} else {

			dgFptBillItem.setDataState(DataState.EDIT);

		}

		dgFptBillItem.setFptInOutFlag(fptInOutFlag);

		dgFptBillItem.setFptBusinessType(fptBusinessType);

		dgFptBillItem.setTableModelHead(tableModelExport);

		dgFptBillItem.setVisible(true);
	}

	/**
	 * 修改撤消数据
	 */
	public void editCancelData() {
		if (tableModelCancel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmFptExgBillHead.this, "请选择要修改的数据",
					"提示", 0);
			return;
		}
		DgFptCancelBill dg = new DgFptCancelBill();
		dg.setTableModel(tableModelCancel);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
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
					fptManageAction.permissionCheckOutBillDel(new Request(
							CommonVars.getCurrUser()));
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnShow
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShow() {
		if (btnShow == null) {
			btnShow = new JButton();
			btnShow.setText("浏览");
			btnShow.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExport.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"请选中要显示的单据！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					billHead = (FptBillHead) tableModelExport.getCurrentRow();
					billHead = fptManageAction.findFptBillHeadById(new Request(
							CommonVars.getCurrUser()), billHead.getId());
					tableModelExport.updateRow(billHead);
					DgFptBillHead dgFptBillItem = new DgFptBillHead();
					dgFptBillItem.setDataState(DataState.BROWSE);
					dgFptBillItem.setTableModelHead(tableModelExport);
					dgFptBillItem.setFptInOutFlag(fptInOutFlag);
					dgFptBillItem.setFptBusinessType(fptBusinessType);
					dgFptBillItem.setVisible(true);

				}
			});
		}
		return btnShow;
	}

	/**
	 * This method initializes btnApply
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setForeground(Color.blue);
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckApply(new Request(CommonVars
							.getCurrUser()));
					apply();
				}
			});
		}
		return btnApply;
	}

	/**
	 * 海关申报
	 */
	private void apply() {
		if (tpnPane.getSelectedIndex() == 0) {
			if (tableModelExport.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(FmFptExgBillHead.this, "请选择单据!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} else {
			if (tableModelCancel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(FmFptExgBillHead.this, "请选择单据!",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		if (JOptionPane.showConfirmDialog(FmFptExgBillHead.this, "是否确定海关申报!",
				"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		if (tpnPane.getSelectedIndex() == 0) {// 转出
			applyInOrOut();
		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消
			applyCancel();
		}

	}

	/**
	 * 海关申报转入转出
	 */
	private void applyInOrOut() {

		// // 判断归并后的资料是否是按申请表序号与交易单位来进行合并
		// if (isMergerCheck()) {
		// JOptionPane.showMessageDialog(FmFptExgBillHead.this,
		// "当申请表序号与交易单位都一致时，应该合并成归并后的一条!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		FptBillHead head = (FptBillHead) tableModelExport.getCurrentRow();
		head = fptManageAction.findFptBillHeadById(
				new Request(CommonVars.getCurrUser()), head.getId());
		tableModelExport.updateRow(head);
		new ApplyInOrOutThread().start();

	}

	/**
	 * 海关申报撤消
	 */
	private void applyCancel() {
		FptCancelBill head = (FptCancelBill) tableModelCancel.getCurrentRow();
		if (head.getNote() == null || "".equals(head.getNote().trim())) {
			JOptionPane.showMessageDialog(FmFptExgBillHead.this,
					"备注栏为空，请输入单据撤销原因!", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		head = fptManageAction.findFptCancelBillById(
				new Request(CommonVars.getCurrUser()), head.getId());
		tableModelCancel.updateRow(head);
		new ApplyCancelThread().start();
	}

	//
	// /**
	// * 判断归并后的资料是否是按申请表序号与交易单位来进行合并
	// *
	// * @author ower
	// *
	// */
	// private boolean isMergerCheck() {
	// String inOutFlag = FptInOutFlag.OUT;
	// String sysType = FptBusinessType.FPT_BILL;
	// FptBillHead c = (FptBillHead) tableModelExport.getCurrentRow();
	// return this.fptManageAction.isMergerCheckFptBillDictItem(new Request(
	// CommonVars.getCurrUser(), true), inOutFlag, c.getId(), sysType);
	// }

	/**
	 * 海关申报转出转入线程
	 * 
	 * @author ower
	 * 
	 */
	class ApplyInOrOutThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				FptBillHead head = (FptBillHead) tableModelExport
						.getCurrentRow();
				try {
					DeclareFileInfo fileInfo = fptManageAction.applyFptBill(
							new Request(CommonVars.getCurrUser()), head);
					head = fptManageAction.findFptBillHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModelExport.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					String result = "<html>系统生成报文成功<br>" + "报文名称为:"
							+ fileInfo.getFileName() + "<br>";
					JOptionPane.showMessageDialog(FmFptExgBillHead.this,
							result, "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					throw new RuntimeException("系统生成报文失败！", ex);
				}
				setState();
			} catch (Exception ex) {
				throw new RuntimeException("系统生成报文失败！", ex);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}

			int count = 0;

			try {
				// 上传文件
				if (FptQuery.getInstance().isHttpUpload()) {
					count = fptMessageAction.httpUpload(new Request(CommonVars
							.getCurrUser()));

					if (count == 0) {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"系统报文发送失败，请重新发送", "确认", 1);
					} else {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"系统报文发送成功!", "确认", 1);
					}
				}
			} catch (RuntimeException e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmFptExgBillHead.this,
						"系统报文发送失败，请重新发送 " + e.getMessage(), "确认", 1);
				return;
			}
		}
	}

	/**
	 * 海关申报资料下载线程
	 * 
	 * @author ower
	 * 
	 */
	// class ApplyDownThread extends Thread {
	// public void run() {
	// try {
	// String taskId = CommonStepProgress.getExeTaskId();
	// CommonStepProgress.showStepProgressDialog(taskId);
	// CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
	// Request request = new Request(CommonVars.getCurrUser());
	// request.setTaskId(taskId);
	// FptDownData head = (FptDownData) tableModelImEx.getCurrentRow();
	// try {
	// DeclareFileInfo fileInfo = fptManageAction
	// .applyFptDownData(
	// new Request(CommonVars.getCurrUser()), head);
	// head = fptManageAction.findFptDownDataById(new Request(
	// CommonVars.getCurrUser()), head.getId());
	// if (head != null) {
	// tableModelImEx.updateRow(head);
	// }
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(null,
	// fileInfo.getFileInfoSpec(), "提示", 1);
	// } catch (Exception ex) {
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(null,
	// "系统申报失败 " + ex.getMessage(), "确认", 1);
	// }
	// setState();
	// } catch (Exception ex) {
	// System.out.println(ex.getStackTrace() + "\n-->"
	// + ex.getMessage());
	// } finally {
	// CommonStepProgress.closeStepProgressDialog();
	// }
	// }
	// }

	/**
	 * 海关申报单据撤消线程
	 * 
	 * @author ower
	 * 
	 */
	class ApplyCancelThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				FptCancelBill head = (FptCancelBill) tableModelCancel
						.getCurrentRow();
				try {
					DeclareFileInfo fileInfo = fptManageAction
							.applyFptCancelBill(
									new Request(CommonVars.getCurrUser()), head);
					head = fptManageAction.findFptCancelBillById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModelCancel.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					String result = "<html>系统生成报文成功<br>" + "报文名称为:"
							+ fileInfo.getFileName() + "<br>";
					JOptionPane.showMessageDialog(null, result, "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					throw new RuntimeException("系统申报失败！", ex);
				}
				setState();
			} catch (Exception ex) {
				throw new RuntimeException("系统申报失败！", ex);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}

			int count = 0;

			try {
				// 上传文件
				if (FptQuery.getInstance().isHttpUpload()) {
					count = fptMessageAction.httpUpload(new Request(CommonVars
							.getCurrUser()));

					if (count == 0) {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"系统报文发送失败，请重新发送", "确认", 1);
					} else {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"系统报文发送成功!", "确认", 1);
					}
				}
			} catch (RuntimeException e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmFptExgBillHead.this,
						"系统报文发送失败，请重新发送 " + e.getMessage(), "确认", 1);
				return;
			}
		}
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("回执处理");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckProcess(new Request(
							CommonVars.getCurrUser()));
					process();
				}
			});
		}
		return btnProcess;
	}

	/**
	 * 回执处理
	 */
	private void process() {

		if (tpnPane.getSelectedIndex() == 0) {// 转出
			processFptBill();
		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消
			processCancel();
		}
	}

	/**
	 * 回执处理转入转出
	 */
	private void processFptBill() {
		int processCount = 0;
		int waitCount = 0;
		StringBuffer noReceipt = new StringBuffer();
		;
		List list = tableModelExport.getList();
		for (int i = 0; i < list.size(); i++) {
			FptBillHead fptBillHead = (FptBillHead) list.get(i);
			fptBillHead = fptManageAction.findFptBillHeadById(new Request(
					CommonVars.getCurrUser()), fptBillHead.getId());
			if (DeclareState.WAIT_EAA.equals(fptBillHead.getAppState())) {
				waitCount++;
				FptBillHead result = processSingleFptBill(fptBillHead);
				if (result != null) {
					processCount++;
				} else {
					noReceipt.append(fptBillHead.getIssueCopBillNo()).append(
							"\r\n");
				}
			}
		}
		setState();
		if (waitCount > 0) {
			if (processCount > 0) {
				JOptionPane.showMessageDialog(
						this,
						"有"
								+ processCount
								+ "笔发货单收到回执，请点击查看按钮查看回执明细内容。"
								+ ("".equals(noReceipt.toString().trim()) ? ""
										: "\r内部编号是" + noReceipt + "发货单没有回执。"),
						"提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "内部编号是：\r\n" + noReceipt
						+ "的发货单没有回执。", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "没有需要回执处理的发货单。", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 回执处理转入转出
	 */
	private FptBillHead processSingleFptBill(FptBillHead head) {
		List lsReturnFile = new ArrayList();
		String copBillNo = "";
		copBillNo = head.getIssueCopBillNo();
		try {
			// 下载回执,通过服务器下载
			if (FptQuery.getInstance().isHttpUpload()) {
				fptMessageAction.httpDownload(
						new Request(CommonVars.getCurrUser()), copBillNo);
			}
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(FmFptExgBillHead.this,
					"下载回执失败！可能原因：1、网络不通、连接不到远程ftp。2、ftp参数配置错误");
		}
		// lsReturnFile = FptMessageHelper.getInstance().showBcsReceiptFile(
		// FptBusinessType.FPT_BILL, copBillNo);
		lsReturnFile.addAll(fptMessageAction.findNotProcessReturnFile(
				new Request(CommonVars.getCurrUser()),
				FptBusinessType.FPT_BILL, copBillNo));
		if (lsReturnFile.size() <= 0) {
			return null;
		}

		try {
			String result = fptManageAction.processFptBillHead(new Request(
					CommonVars.getCurrUser()), head, lsReturnFile);
			head = fptManageAction.findFptBillHeadById(
					new Request(CommonVars.getCurrUser()), head.getId());
			tableModelExport.updateRow(head);
			return head;
			// JOptionPane.showMessageDialog(FmFptExgBillHead.this, "回执处理成功！\n"
			// + result, "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmFptExgBillHead.this,
					"回执处理失败" + ex.getMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		// setState();

	}

	/**
	 * 回执处理撤消
	 */
	private void processCancel() {
		int processCount = 0;
		int waitCount = 0;
		StringBuffer noReceipt = new StringBuffer();
		;
		List list = tableModelCancel.getList();
		for (int i = 0; i < list.size(); i++) {
			FptCancelBill head = (FptCancelBill) list.get(i);
			head = fptManageAction.findFptCancelBillById(
					new Request(CommonVars.getCurrUser()), head.getId());
			if (DeclareState.WAIT_EAA.equals(head.getDeclareState())) {
				waitCount++;
				FptCancelBill result = processSingleCancel(head);
				if (result != null) {
					processCount++;
				} else {
					noReceipt.append(head.getCopNo()).append("\r\n");
				}
			}
		}
		setState();
		if (waitCount > 0) {
			if (processCount > 0) {
				JOptionPane
						.showMessageDialog(
								this,
								"有"
										+ processCount
										+ "笔撤销发货单收到回执，请点击查看按钮查看回执明细内容。"
										+ ("".equals(noReceipt.toString()
												.trim()) ? "" : "\r内部编号是"
												+ noReceipt + "撤销发货单没有回执。"),
								"提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "内部编号是：\r\n" + noReceipt
						+ "的撤销发货单没有回执。", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "没有需要回执处理的撤销发货单。", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 回执处理撤消
	 */
	private FptCancelBill processSingleCancel(FptCancelBill head) {
		try {
			// 下载回执,通过服务器下载
			if (FptQuery.getInstance().isHttpUpload()) {
				fptMessageAction.httpDownload(
						new Request(CommonVars.getCurrUser()), head.getCopNo());
			}
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(FmFptExgBillHead.this,
					"下载回执失败！可能原因：1、网络不通、连接不到远程ftp。2、ftp参数配置错误");
		}
		List lsReturnFile = new ArrayList();
		// List lsReturnFile =
		// FptMessageHelper.getInstance().showBcsReceiptFile(
		// FptBusinessType.FPT_CANCEL_BILL, head.getCopNo());
		String copNo = head.getCopNo();
		lsReturnFile.addAll(fptMessageAction.findNotProcessReturnFile(
				new Request(CommonVars.getCurrUser()),
				FptBusinessType.FPT_CANCEL_BILL, copNo));
		lsReturnFile.addAll(fptMessageAction.findNotProcessReturnFile(
				new Request(CommonVars.getCurrUser()),
				FptBusinessType.FPT_BILL, copNo));
		if (lsReturnFile.size() <= 0) {
			return null;
		}
		try {
			String result = fptManageAction.processFptCancelBill(new Request(
					CommonVars.getCurrUser()), head, lsReturnFile);
			head = fptManageAction.findFptCancelBillById(
					new Request(CommonVars.getCurrUser()), head.getId());
			tableModelCancel.updateRow(head);
			return head;
			// JOptionPane.showMessageDialog(this, "回执处理成功！\n" + result, "提示",
			// JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "回执处理失败" + ex.getMessage(),
					"提示", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		// setState();

	}

	/**
	 * This method initializes btnOther
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOther() {
		if (btnOther == null) {
			btnOther = new JButton();
			btnOther.setText("其它功能");
			btnOther.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckOther(new Request(CommonVars
							.getCurrUser()));
					getPmOther().show(getBtnOther(), 0,
							getBtnOther().getHeight());
				}
			});
		}
		return btnOther;
	}

	/**
	 * This method initializes pmOther
	 * 
	 * @return javax.swing.JPopupMenu
	 */

	private JPopupMenu getPmOther() {
		if (pmOther == null) {
			pmOther = new JPopupMenu();
			pmOther.setSize(new Dimension(70, 28));
			pmOther.add(getImportData());
			pmOther.addSeparator();
			pmOther.add(getMiCasToFpt());
			pmOther.addSeparator();
			pmOther.add(getMiFptToCustoms());
		}
		return pmOther;
	}

	private JMenuItem getImportData() {
		if (importData == null) {
			importData = new JMenuItem();
			importData.setSize(new Dimension(47, 24));
			importData.setText("直接导入");

			importData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!LicenseClient.getInstance(
							CommonVars.getCurrUser().getCompany().getName())
							.checkFptManagePermisson()) {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"没有使用此功能的权限，如果需要请联系百思维！");
						return;
					}
					DgImportDataExg dialog = new DgImportDataExg();
					dialog.setVisible(true);
					if (tpnPane.getSelectedIndex() == 0) {// 转出
						initTableExport(getDataSource());
					}

					setState();
				}
			});
		}
		return importData;
	}

	/**
	 * This method initializes miCasToFpt
	 * 
	 * @return javax.swing.JMenuItem
	 */

	private JMenuItem getMiCasToFpt() {
		if (miCasToFpt == null) {
			miCasToFpt = new JMenuItem();
			miCasToFpt.setSize(new Dimension(47, 24));
			miCasToFpt.setText("单据中心导入");

			miCasToFpt.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!LicenseClient.getInstance(
							CommonVars.getCurrUser().getCompany().getName())
							.checkFptManagePermisson()) {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"没有使用此功能的权限，如果需要请联系百思维！");
						return;
					}
					DgMakeFptBill dialog = new DgMakeFptBill();
					dialog.setNewFptBillHead(true);
					dialog.setVisible(true);
					if (tpnPane.getSelectedIndex() == 0) {// 转出
						initTableExport(getDataSource());
					}

					setState();
				}
			});
		}
		return miCasToFpt;
	}

	/**
	 * This method initializes miFptToCustoms
	 * 
	 * @return javax.swing.JMenuItem
	 */

	private JMenuItem getMiFptToCustoms() {
		if (miFptToCustoms == null) {
			miFptToCustoms = new JMenuItem();
			miFptToCustoms.setSize(new Dimension(47, 24));
			miFptToCustoms.setText("转报关单");
			miFptToCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMakeCustomsDeclaration dialog = new DgMakeCustomsDeclaration();
							dialog.setImport(FptInOutFlag.OUT);
							// 只有收发货单转报关单
							dialog.setSysType(FptBusinessType.FPT_BILL);
							dialog.setBackSysType(FptBusinessType.FPT_BILL_BACK);
							dialog.setVisible(true);
							if (dialog.isOk()) {
								if (tpnPane.getSelectedIndex() == 0) {// 转出
									initTableExport(getDataSource());
								}

								setState();
							}
						}
					});
		}
		return miFptToCustoms;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckCopy(new Request(CommonVars
							.getCurrUser()));
					if (tableModelExport.getCurrentRow() != null) {
						List<FptBillHead> list = fptManageAction.copyFptBillHead(
								new Request(CommonVars.getCurrUser()),
								tableModelExport.getCurrentRows(), 0, 0);
						tableModelExport.addRows(list);
					} else {
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"请选择要转抄单据!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFptExgBillHead.this.dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes tpnPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (tpnPane == null) {
			tpnPane = new JTabbedPane();
			tpnPane.setTabPlacement(JTabbedPane.BOTTOM);
			tpnPane.addTab("发货单", null, getSpn0(), null);
			tpnPane.addTab("单据撤消", null, getSpn4(), null);
			tpnPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tpnPane.getSelectedIndex() == 0) {// 转出
						initTableExport(getDataSource());
						initCustomer();
					} else if (tpnPane.getSelectedIndex() == 1) {
						ininTableCancel();
					}
					setState();
				}
			});
		}
		return tpnPane;
	}

	/**
	 * This method initializes btnChange
	 * 
	 * @return javax.swing.JButton
	 */
	/**
	 * 变更暂时隐藏
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.setVisible(false);
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// changingData();
				}
			});
		}
		return btnChange;
	}

	/**
	 * 变更结转单据
	 * 
	 */
	// private void changingData() {
	// tableModelImEx = tableModelExport;
	// if (tableModelImEx.getCurrentRow() != null) {
	// if (JOptionPane.showConfirmDialog(this, "是否确定变更单据!", "提示",
	// JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
	// return;
	// }
	// FptBillHead c = this.fptManageAction.changingFptBill(new Request(
	// CommonVars.getCurrUser()), (FptBillHead) tableModelImEx
	// .getCurrentRow());
	// if (c != null) {
	// tableModelImEx.addRow(c);
	// } else {
	// JOptionPane.showMessageDialog(this, "已存在变更单据!", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// }
	// } else {
	// JOptionPane.showMessageDialog(this, "请选择要变更单据!", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// }
	// }

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setBounds(new Rectangle(567, 2, 65, 25));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tpnPane.getSelectedIndex() == 0) {// 转出
						initTableExport(getDataSource());
					}
					setState();
				}
			});
		}
		return btnRefresh;
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
			pn1.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return pn1;
	}

	/**
	 * This method initializes spn0
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn0() {
		if (spn0 == null) {
			spn0 = new JScrollPane();
			spn0.setViewportView(getTbExport());
		}
		return spn0;
	}

	/**
	 * This method initializes tbExport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExport() {

		if (tbExport == null) {
			tbExport = new JTable();
			tbExport.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
		}

		tbExport.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}

						JTableListModel tableModel = (JTableListModel) tbExport
								.getModel();

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

		return tbExport;
	}

	public String getBillType() {
		return fptBusinessType;
	}

	public void setBillType(String fptBusinessType) {
		this.fptBusinessType = fptBusinessType;
	}

	/**
	 * This method initializes pn3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn3() {
		if (pn3 == null) {
			lbCustomer = new JLabel();
			lbCustomer.setBounds(new Rectangle(291, 6, 75, 18));
			lbCustomer.setText("客户");
			lbTo = new JLabel();
			lbTo.setText("到");
			lbTo.setBounds(new Rectangle(174, 7, 18, 18));
			lbInputTime = new JLabel();
			lbInputTime.setText("录入日期");
			lbInputTime.setBounds(new Rectangle(11, 7, 61, 18));
			pn3 = new JPanel();
			pn3.setLayout(null);
			pn3.setPreferredSize(new Dimension(720, 28));
			pn3.add(lbInputTime, null);
			pn3.add(getCbbBeginDate(), null);
			pn3.add(lbTo, null);
			pn3.add(getCbbEndDate(), null);
			pn3.add(getBtnRefresh(), null);
			pn3.add(lbCustomer, null);
			pn3.add(getCbbCustomer(), null);
		}
		return pn3;
	}

	/**
	 * This method initializes pn3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn4() {
		if (pn4 == null) {
			pn4 = new JPanel();
			pn4.setLayout(null);
			pn4.setPreferredSize(new Dimension(720, 28));
			lbSendNo = new JLabel("发货单编号");
			lbSendNo.setBounds(11, 8, 61, 15);
			pn4.add(lbSendNo);

			tfSenNo = new JTextField();
			tfSenNo.setBounds(new Rectangle(73, 3, 97, 23));
			pn4.add(tfSenNo);
			tfSenNo.setColumns(10);

			pn4.add(getBtnQuery());
		}
		return pn4;
	}

	/**
	 * 
	 * @return
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new Rectangle(195, 3, 61, 23));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(73, 3, 97, 23));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(195, 3, 92, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btSreach
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtSreach() {
		if (btSreach == null) {
			btSreach = new JButton();
			btSreach.setText("查询");
			btSreach.setVisible(false);
			btSreach.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgQueryConditionForFptBillHead dg = new DgQueryConditionForFptBillHead();
					dg.setType(Integer.parseInt(fptBusinessType));
					dg.setImport(FptInOutFlag.IN.equals(fptInOutFlag));
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						if (tpnPane.getSelectedIndex() == 0) {// 转出
							initTableExport(list);
						}
					}

				}
			});
		}
		return btSreach;
	}

	/**
	 * This method initializes spn4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn4() {
		if (spn4 == null) {
			spn4 = new JScrollPane();
			spn4.setViewportView(getSpn6());
		}
		return spn4;
	}

	/**
	 * This method initializes spn6
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn6() {
		if (spn6 == null) {
			spn6 = new JScrollPane();
			spn6.setViewportView(getTbCancel());
		}
		return spn6;
	}

	/**
	 * This method initializes tbCancel
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbCancel() {
		if (tbCancel == null) {
			tbCancel = new JTable();
			tbCancel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
		}
		tbCancel.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) tbCancel
								.getModel();
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
		return tbCancel;
	}

	/**
	 * This method initializes tb1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getTb1() {
		if (tb1 == null) {
			tb1 = new JToolBar();
			tb1.setFloatable(false);
			tb1.add(getPn3());
			// tb1.add(getPn4());
		}
		return tb1;
	}

	/**
	 * This method initializes cbbCustomer
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCustomer() {
		if (cbbCustomer == null) {
			cbbCustomer = new JComboBox();
			cbbCustomer.setBounds(new Rectangle(333, 4, 231, 23));
		}
		return cbbCustomer;
	}

	/**
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn() {
		if (pn == null) {
			pn = new JPanel();
			pn.setLayout(new BorderLayout());
			pn.add(getTb1(), BorderLayout.NORTH);
			pn.add(getPn1(), BorderLayout.CENTER);
		}
		return pn;
	}

	/**
	 * 下载明细
	 */
	// private void DownloadDetail() {
	// tableModelImEx = tableModelExport;
	// if (tableModelImEx.getCurrentRow() != null) {
	// if (JOptionPane.showConfirmDialog(this, "是否确定下载对方明细数据?", "提示",
	// JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
	// return;
	// }
	// FptBillHead fptBillHead = (FptBillHead) tableModelImEx
	// .getCurrentRow();
	// if (fptBillHead != null) {
	// boolean isOut = true;
	// if (FptInOutFlag.IN.equals(fptBillHead.getBillSort())) {
	// isOut = false;
	// }
	// DownloadDetailTask task = new DownloadDetailTask(fptBillHead,
	// isOut);
	// task.execute();
	// }
	// } else {
	// JOptionPane.showMessageDialog(this, "请选择要下载对方明细的表头!", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// }
	// }

	// class DownloadDetailTask extends SwingWorker {
	// FptBillHead fptBillHead = null;
	// boolean isOut = true;
	//
	// public DownloadDetailTask(FptBillHead fptBillHead, boolean isOut) {
	// this.fptBillHead = fptBillHead;
	// this.isOut = isOut;
	// }
	//
	// @Override
	// protected Object doInBackground() throws Exception {
	// List items = new ArrayList();
	// try {
	// String taskId = CommonStepProgress.getExeTaskId();
	// CommonStepProgress.showStepProgressDialog(taskId);
	// CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
	// Request request = new Request(CommonVars.getCurrUser());
	// request.setTaskId(taskId);
	// if (isOut) {
	// items = fptManageAction.downloadFptBillItemsOutByQp(
	// new Request(CommonVars.getCurrUser()), taskId,
	// fptBillHead);
	// } else {
	// items = fptManageAction.downloadFptBillItemsInByQp(
	// new Request(CommonVars.getCurrUser()), taskId,
	// fptBillHead);
	// }
	// CommonStepProgress.closeStepProgressDialog();
	//
	// } catch (Exception ex) {
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(FmFptOutBillHead.this, "下载失败 "
	// + ex.getMessage(), "确认", 1);
	// } finally {
	// CommonStepProgress.closeStepProgressDialog();
	// }
	// return items;
	// }
	// }

	private JButton getBtnResend() {
		if (btnResend == null) {
			btnResend = new JButton();
			btnResend.setText("补发报文");
			btnResend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fptManageAction.permissionCheckResend(new Request(
							CommonVars.getCurrUser()));
					int count = 0;
					try {
						// 上传文件
						count = fptMessageAction.httpUpload(new Request(
								CommonVars.getCurrUser()));
						if (count == 0) {
							JOptionPane.showMessageDialog(
									FmFptExgBillHead.this, "上传目录为空，没有可上传的报文",
									"确认", 1);
						}
					} catch (Exception ex) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(FmFptExgBillHead.this,
								"系统补发报文失败，请重新发送 " + ex.getMessage(), "确认", 1);
						return;
					}
					JOptionPane.showMessageDialog(FmFptExgBillHead.this,
							"系统补发报文成功，一个上传" + count + "个报文", "确认", 1);
				}
			});
		}
		return btnResend;
	}

	private JButton getBtnReceipt() {
		if (btnReceipt == null) {
			btnReceipt = new JButton("查看回执");
			btnReceipt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showReceiptResult();
				}
			});
		}
		return btnReceipt;
	}

	private void showReceiptResult() {
		String sysType = "";
		String copNo = "";
		if (tpnPane.getSelectedIndex() == 0) {// 转出
			sysType = FptBusinessType.FPT_BILL;
			if (tableModelExport.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(this, "请选择要查看回执的发货单", "提示", 2);
				return;
			}
			FptBillHead fptBillHead = (FptBillHead) tableModelExport
					.getCurrentRow();
			copNo = fptBillHead.getIssueCopBillNo();
		} else if (tpnPane.getSelectedIndex() == 1) {// 撤消
			if (tableModelCancel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(this, "请选择要查看回执的撤销数据", "提示", 2);
				return;
			}
			// 2013-12-4
			// 因撤销单前面两个回执类型是撤销单，但后面两个回执类型为收发货单类型，所以当为撤销单时，根据撤销单内部编号查询回执
			// sysType = FptBusinessType.FPT_CANCEL_BILL;
			sysType = null;
			FptCancelBill head = (FptCancelBill) tableModelCancel
					.getCurrentRow();
			copNo = head.getCopNo();
		}
		FptMessageHelper.getInstance().showFptReceiptResult(sysType,
				this.fptInOutFlag, copNo);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
