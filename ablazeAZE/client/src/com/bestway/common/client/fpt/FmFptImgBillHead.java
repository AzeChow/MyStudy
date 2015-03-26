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
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.fpt.report.CustomsEnvelopSubReportDataSource;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DownLoadState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.action.FptMessageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptCancelBill;
import com.bestway.common.fpt.entity.FptParameterSet;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 进出货转厂单据
 * 
 * by change lyh 2013.05.24
 * 
 * by check lxr 2008.09.09
 * 
 * @author lxr
 * 
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FmFptImgBillHead extends FmCommon {

	private JPanel contentPn = null;

	private FptBillHead billHead = null;

	// private JTableListModel tableModelImEx = null;
	/**
	 * 收货tableModel
	 */
	private JTableListModel tableModelImport = null;
	/**
	 * 资料下载tableModel
	 */
	// private JTableListModel tableModelDown = null;
	/**
	 * 撤消tableModel
	 */
	// private JTableListModel tableModelCancel = null;

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

	private JLabel lbInputTime = null;
	private JLabel lbTo = null;

	private JPopupMenu pmOther = null;
	private JMenuItem miCasToFpt = null;
	private JMenuItem miFptToCustoms = null;

	private JCalendarComboBox cbbBeginDate = null;
	private JCalendarComboBox cbbEndDate = null;

	private JScrollPane spn1 = null;
	private JScrollPane spn5 = null;
	private JTable tbImport = null;
	private JLabel lbCustomer = null;

	private JToolBar tbBarTop = null;
	private JToolBar tb1 = null;

	private JComboBox cbbCustomer = null;

	private JPanel pn = null;
	/** 单据类型 */
	private String billType = null; // @jve:decl-index=0:

	private FptMessageAction fptMessageAction = null;

	private PnFptDownData pnFptDownData = null;

	private JButton btnResend = null;

	private FptParameterSet parameterSet = null;

	private Integer projectType = null;

	private JButton btnPrint = null;
	private JPopupMenu pmPrint = null;

	private JMenuItem getMiPrintTrue = null;
	private JMenuItem getMiPrintFalse = null;

	private final static int PRINT_STYPE_A = 1;
	private final static int PRINT_STYPE_B = 2;
	private final static int PRINT_STYPE_C = 3;
	private JButton btnReceipt;
	private List subReportData = null; // @jve:decl-index=0:

	public FmFptImgBillHead() {
		super();
		fptMessageAction = (FptMessageAction) CommonVars
				.getApplicationContext().getBean("fptMessageAction");
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		parameterSet = fptManageAction.findTransParameterSet(new Request(
				CommonVars.getCurrUser(), true));
		projectType = parameterSet.getProjectType();
		fptManageAction.permissionCheckInBillGlance(new Request(CommonVars
				.getCurrUser()));
		initialize();
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(new Date());
		initCustomer();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getContentPn());
		this.setSize(771, 503);
		this.setTitle("料件收货单据");
		this.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
			public void internalFrameOpened(
					javax.swing.event.InternalFrameEvent e) {
				initTableImport(getDataSource());
				setState();
			}
		});
	}

	/**
	 * 初始化客户/供应商
	 */
	private void initCustomer() {
		// List list = null;
		// if (tpnPane.getSelectedIndex() == 0) {//供应商
		// list = materialManageAction.findScmManucustomer(new Request(
		// CommonVars.getCurrUser(),true));
		//
		// }
		// else if (tpnPane.getSelectedIndex() == 1) {
		// list = materialManageAction.findScmManuFactoryOut(new Request(
		// CommonVars.getCurrUser(), true));
		// }

		DefaultComboBoxModel scmCocs = new DefaultComboBoxModel();
		// if (this.getInOutFlag().equals(FptInOutFlag.IN)) {
		scmCocs = new DefaultComboBoxModel(super.getManufacturer().toArray());
		this.cbbCustomer.removeAllItems();
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
		// if (tpnPane.getSelectedIndex() == 0) {
		setStateInorOut();
		// } else if (tpnPane.getSelectedIndex() == 1) {// 资料下载
		// setStateDown();
		// }
	}

	/**
	 * 设置转出转入的状态
	 */
	private void setStateInorOut() {
		btnCopy.setVisible(true);
		this.btnShow.setVisible(true);
		btnOther.setVisible(true);
		pn3.setVisible(true);
		FptBillHead c = (FptBillHead) tableModelImport.getCurrentRow();
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
		this.btnApply
				.setEnabled((DeclareState.APPLY_POR.equals(c.getAppState()) || DeclareState.CHANGING_EXE
						.equals(c.getAppState())));
		this.btnProcess
				.setEnabled(DeclareState.WAIT_EAA.equals(c.getAppState()));

		btnApply.setText("海关申报");

	}

	/**
	 * 设置资料下载的状态
	 * 
	 * @return
	 */
	// private void setStateDown() {
	// // 屏闭一些按钮
	// btnCopy.setVisible(false);
	// this.btnShow.setVisible(false);
	// btnOther.setVisible(false);
	// pn3.setVisible(false);
	// cbbIsCancel.setVisible(false);
	//
	// tableModelImEx = tableModelDown;
	// FptDownData c = (FptDownData) tableModelImEx.getCurrentRow();
	// if (c == null) {
	// return;
	// }
	// boolean isProcessExe = (c.getDeclareState() != null && c
	// .getDeclareState().equals(DeclareState.PROCESS_EXE));
	// btnDelete.setEnabled(DeclareState.APPLY_POR.equals(c.getDeclareState())
	// || DeclareState.CHANGING_EXE.equals(c.getDeclareState()));
	// btnEdit.setEnabled(!isProcessExe
	// && (DeclareState.APPLY_POR.equals(c.getDeclareState()) ||
	// DeclareState.CHANGING_EXE
	// .equals(c.getDeclareState())));
	// this.btnApply.setEnabled((DeclareState.APPLY_POR.equals(c
	// .getDeclareState()) || DeclareState.CHANGING_EXE.equals(c
	// .getDeclareState())));
	// this.btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(c
	// .getDeclareState()));
	// btnChange.setEnabled(isProcessExe);
	//
	// btnApply.setText("申请下载");
	//
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
		FptCancelBill c = (FptCancelBill) tableModelImport.getCurrentRow();
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
		// this.btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(c
		// .getDeclareState()));
		btnChange.setEnabled(isProcessExe);

	}

	/**
	 * 获取转出转入数据来源
	 * 
	 * @return
	 */
	private List getDataSource() {
		List list = null;

		// if (tpnPane.getSelectedIndex() == 0) { // 转出 --发货
		// list = fptManageAction.findInOutFptBillHeadByType(new Request(
		// CommonVars.getCurrUser()), true, jList.getSelectedIndex(),
		// this.cbbBeginDate.getDate(), this.cbbEndDate.getDate(),
		// null, null, null, cbbIsCancel.isSelected(),
		// (ScmCoc) this.cbbCustomer.getSelectedItem());
		// } else {

		// list = fptManageAction.findInOutFptBillHeadByType(new Request(
		// CommonVars.getCurrUser()), false, FptInOutFlag.rollIN,
		// this.cbbBeginDate.getDate(), this.cbbEndDate.getDate(),
		// null, null, null, cbbIsCancel.isSelected(),
		// (ScmCoc) this.cbbCustomer.getSelectedItem()); // 转入--收货

		list = fptManageAction.findInOutFptBillHeadByType(new Request(
				CommonVars.getCurrUser()), FptInOutFlag.IN,
				FptBusinessType.FPT_BILL, this.cbbBeginDate.getDate(),
				this.cbbEndDate.getDate(), null, null, null,
				(ScmCoc) this.cbbCustomer.getSelectedItem()); // 转入--收货

		// }
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
	// private void initTableExport(List list) {
	// tableModelExport = new JTableListModel(tbExport, list,
	// new JTableListModelAdapter() {
	// public List<JTableListColumn> InitColumns() {
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(addColumn("客户/供应商", "customer.name", 90));
	// list.add(addColumn("发货企业内部", "issueCopBillNo", 180));
	// list.add(addColumn("申报状态", "appState", 60));
	// if (jList.getSelectedIndex() == 0) {
	// list.add(addColumn("发货单编号", "billNo", 100));
	// } else {
	// list.add(addColumn("收退货单编号", "billNo", 100));
	// }
	// list.add(addColumn("统一编号", "seqNo", 100));
	// list.add(addColumn("申请表编号", "appNo", 80));
	// list.add(addColumn("已转报关单流水号",
	// "makeCustomsDeclarationCode", 100));
	// list.add(addColumn("转出企业手册/账册号", "outEmsNo", 100));
	// list.add(addColumn("单据类型", "sysType", 60));
	// list.add(addColumn("转出标志", "billSort", 60));
	// list.add(addColumn("申报时间", "issueIsDeclaDate", 80));
	// list.add(addColumn("转入企业名称", "receiveTradeName", 100));
	// list.add(addColumn("录入时间", "createDate", 60));
	// list.add(addColumn("录入人员", "aclUser.userName", 80));
	// list.add(addColumn("备注", "note", 100));
	// return list;
	// }
	// });
	// tbExport.getColumnModel().getColumn(3).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value, boolean isSelected,
	// boolean hasFocus, int row, int column) {
	// super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// super.setText((value == null) ? "" : castValue(value));
	// return this;
	// }
	//
	// private String castValue(Object value) {
	// return DeclareState.getDeclareStateSpec(value
	// .toString());
	// }
	// });
	// tbExport.getColumnModel().getColumn(9).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value, boolean isSelected,
	// boolean hasFocus, int row, int column) {
	// super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// super.setText((value == null) ? "" : FptBusinessType
	// .getFptBusinessTypeDesc(value.toString()));
	// return this;
	// }
	// });
	// tbExport.getColumnModel().getColumn(10).setCellRenderer(
	// new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value, boolean isSelected,
	// boolean hasFocus, int row, int column) {
	// super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// super.setText((value == null) ? "" : FptInOutFlag
	// .getInOutFlagSpec(value.toString()));
	// return this;
	// }
	// });
	// }

	/**
	 * 初始化转入table
	 * 
	 * @param list
	 */
	private void initTableImport(List list) {
		tableModelImport = new JTableListModel(tbImport, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("供应商", "customer.name", 90));
						list.add(addColumn("收货企业内部", "receiveCopBillNo", 180));
						list.add(addColumn("申报状态", "appState", 60));
						list.add(addColumn("收货单编号", "billNo", 100));
						list.add(addColumn("统一编号", "seqNo", 100));
						list.add(addColumn("申请表编号", "appNo", 80));
						list.add(addColumn("已转报关单流水号",
								"makeCustomsDeclarationCode", 100));
						list.add(addColumn("单据类型", "sysType", 60));
						list.add(addColumn("转入标志", "billSort", 60));
						list.add(addColumn("申报时间", "receiveIsDeclaDate", 80));
						list.add(addColumn("转出企业名称", "issueTradeName", 100));
						list.add(addColumn("录入时间", "createDate", 60));
						list.add(addColumn("录入人员", "aclUser.userName", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		tbImport.getColumnModel().getColumn(3)
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
		tbImport.getColumnModel().getColumn(8)
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
		tbImport.getColumnModel().getColumn(9)
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
	}

	// /**
	// * 初始化下载资料table
	// */
	// public void ininTableDown() {
	// String downLoadState = "C";
	// List list = this.fptManageAction.findRecordationDataDownLoad(
	// new Request(CommonVars.getCurrUser()), downLoadState,
	// FptInOutFlag.IN);
	// tableModelDown = new JTableListModel(tbDown, list,
	// new JTableListModelAdapter() {
	// public List InitColumns() {
	// List list = new Vector();
	// list.add(addColumn("电子口岸统一编号", "seqNo", 150));
	// list.add(addColumn("申报状态", "declareState", 60));
	// list.add(addColumn("企业编号", "tradeCode", 100));
	// list.add(addColumn("申请单编号", "appNo", 100));
	// list.add(addColumn("转出企业内部编号", "outCopNo", 150));
	// list.add(addColumn("下载类型", "downLoadState", 60));
	// list.add(addColumn("备注", "note", 150));
	// return list;
	// }
	// });
	// tbDown.getColumnModel().getColumn(2)
	// .setCellRenderer(new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value, boolean isSelected,
	// boolean hasFocus, int row, int column) {
	// super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// super.setText((value == null) ? "" : castValue(value));
	// return this;
	// }
	//
	// private String castValue(Object value) {
	// return DeclareState.getDeclareStateSpec(value
	// .toString());
	// }
	// });
	// tbDown.getColumnModel().getColumn(6)
	// .setCellRenderer(new DefaultTableCellRenderer() {
	// public Component getTableCellRendererComponent(
	// JTable table, Object value, boolean isSelected,
	// boolean hasFocus, int row, int column) {
	// super.getTableCellRendererComponent(table, value,
	// isSelected, hasFocus, row, column);
	// super.setText((value == null) ? "" : castValue(value));
	// return this;
	// }
	//
	// private String castValue(Object value) {
	// return DownLoadState.getDownLoadStateSpec(value
	// .toString());
	// }
	// });
	// }

	/**
	 * 删除
	 */
	private void deleteData() {
		// if (tpnPane.getSelectedIndex() == 0) {// 收货
		// tableModelImEx = tableModelImport;
		deleteInorOutData();
		// } else if (tpnPane.getSelectedIndex() == 1) {// 资料下载
		// deleteDownData();
		// }
	}

	/**
	 * 删除转入转出资料
	 */
	private void deleteInorOutData() {
		if (tableModelImport.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
			super.fptManageAction.deleteFptBillHead(
					new Request(CommonVars.getCurrUser()),
					(FptBillHead) tableModelImport.getCurrentRow());
			tableModelImport.deleteRow(tableModelImport.getCurrentRow());
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示", 0);
		}
	}

	/**
	 * 删除资料下载
	 */
	// private void deleteDownData() {
	// if (tableModelImEx.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(null, "请选择要删除的资料!", "提示", 0);
	// return;
	// }
	// if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!!!", "提示", 0) != 0) {
	// return;
	// }
	// FptDownData data = (FptDownData) tableModelImEx.getCurrentRow();
	// fptManageAction.deleteRecordationDataDownLoad(
	// new Request(CommonVars.getCurrUser()), data);
	// tableModelImEx.deleteRow(data);
	// }

	/**
	 * 删除撤消
	 */
	// private void deleteCancelData() {
	// if (tableModelImEx.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(null, "请选择要删除的资料!", "提示", 0);
	// return;
	// }
	// if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!!!", "提示", 0) != 0) {
	// return;
	// }
	// FptCancelBill data = (FptCancelBill) tableModelImEx.getCurrentRow();
	// fptManageAction.deleteFptCancelBill(
	// new Request(CommonVars.getCurrUser()), data);
	// tableModelImEx.deleteRow(data);
	// }

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
			getMiPrintTrue.setText("套打料件收货单");
			getMiPrintTrue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					printReport(FmFptImgBillHead.PRINT_STYPE_A, false);
				}
			});
		}
		return getMiPrintTrue;
	}

	// 打印弹出子菜单非套打
	private JMenuItem getMiPrintFalse() {
		if (getMiPrintFalse == null) {
			getMiPrintFalse = new JMenuItem();
			getMiPrintFalse.setText("非套打料件收货单");
			getMiPrintFalse.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					printReport(FmFptImgBillHead.PRINT_STYPE_A, true);
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
		if (this.tableModelImport.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择要打印的收发货单!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FptBillHead fptBillHead = (FptBillHead) this.tableModelImport
				.getCurrentRow();
		List list = new ArrayList();
		list.add(this.tableModelImport.getCurrentRow());
		CustomReportDataSource ds = new CustomReportDataSource(list);
		printFptInOutBillReport(ds, fptBillHead);
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
				items = this.fptManageAction.findTransferFactoryCommodityInfo(
						new Request(CommonVars.getCurrUser()),
						fptBillHead.getId());
				subReportData = items;
			}
			CustomsEnvelopSubReportDataSource
					.setSubReportData(this.subReportData);
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
					new Request(CommonVars.getCurrUser()), FptInOutFlag.IN,
					fptBillHead.getAppNo());
			if (fptAppHead != null) {
				parameters.put("contrNo", fptAppHead.getContrNo() == null ? ""
						: fptAppHead.getContrNo());
			}
			parameters.put("dsOut", FptInOutFlag.OUT);

			parameters.put("dsIn", FptInOutFlag.IN);

			Transf transportToolTypenew = fptBillHead.getTransportToolTypenew();

			parameters.put(
					"transportToolTypenew",
					transportToolTypenew == null ? "" : transportToolTypenew
							.getName());

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

	/**
	 * 料件打印收(退)货登记 明天要修改的
	 */
	private void printReportByCustomsEnvelopForOld(CustomReportDataSource ds,
			boolean isCustomsEnvelopNoPrint, boolean isOverprint, Object object) {

		try {

			InputStream masterReportStream = FmFptExgBillHead.class
					.getResourceAsStream("report/FptImgInOutBillHeadRecordReport.jasper");

			FptBillHead fptBillHead = (FptBillHead) object;
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("isOverPrint", new Boolean(isOverprint));
			parameters.put("isHead", new Boolean(true));
			parameters.put("appNo", fptBillHead.getAppNo());
			parameters.put("billNo", fptBillHead.getBillNo());
			parameters.put("issueTradeCod", fptBillHead.getIssueTradeCod());
			parameters.put("issueTradeName", fptBillHead.getIssueTradeName());
			// parameters.put("issueDate", fptBillHead.getIssueDate() == null ?
			// " ":fptBillHead.getReceiveDate().toString());
			parameters.put(
					"issueDate",
					fptBillHead.getReceiveDate() == null ? " "
							: new SimpleDateFormat("yyyy-MM-dd")
									.format(fptBillHead.getReceiveDate()));
			parameters.put("outEmsNo", fptBillHead.getOutEmsNo());

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
					fptManageAction.permissionCheckInBillAdd(new Request(
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
		// if (tpnPane.getSelectedIndex() == 0) {// 转出
		// addInOrOutData();
		// } else
		// if (tpnPane.getSelectedIndex() == 0) {
		addInOrOutData();
		// } else if (tpnPane.getSelectedIndex() == 1) {// 资料下载
		// addDownData();
		// }
		// else if (tpnPane.getSelectedIndex() == 2) {// 撤消
		// addCancelData();
		// }
	}

	/**
	 * 增加转入转出资料
	 */
	private void addInOrOutData() {
		FptBillHead head = null;
		// if (tpnPane.getSelectedIndex() == 0) { // 转出
		// head = fptManageAction.newFptBillHead(new Request(CommonVars
		// .getCurrUser()), tpnPane.getSelectedIndex(), jList
		// .getSelectedIndex());
		// tableModelExport.addRow(head);
		// } else {

		// head = fptManageAction.newFptBillHead(new Request(CommonVars
		// .getCurrUser()), tpnPane.getSelectedIndex(),
		// Integer.valueOf(FptInOutFlag.IN));
		// tableModelImport.addRow(head);

		head = fptManageAction.newFptBillHead(
				new Request(CommonVars.getCurrUser()), FptInOutFlag.IN,
				FptBusinessType.FPT_BILL);
		tableModelImport.addRow(head);

		// }
		setState();

	}

	/**
	 * 增加下载资料
	 */
	// private void addDownData() {
	// DgFptDownData dg = new DgFptDownData(FptBusinessType.FPT_BILL,
	// FptInOutFlag.IN);
	// dg.setTableModel(this.tableModelDown);
	// dg.setDataState(DataState.ADD);
	// dg.setVisible(true);
	// }

	// /**
	// * 增加撤消资料
	// */
	// private void addCancelData() {
	//
	// List<FptCancelBill> rlist = new ArrayList<FptCancelBill>();
	// List lists = FptQuery.getInstance().findFptBillHeadForCancel(
	// FptInOutFlag.IN, FptBusinessType.FPT_BILL);
	// if (lists == null) {
	// return;
	// }
	// for (int i = 0; i < lists.size(); i++) {
	// FptBillHead head = (FptBillHead) lists.get(i);
	// FptCancelBill fptCancelBill = new FptCancelBill();
	// fptCancelBill.setAppNo(head.getAppNo());
	// fptCancelBill.setSeqNo(head.getSeqNo());
	// if (FptBusinessType.FPT_BILL.equals(head.getSysType())
	// && FptInOutFlag.OUT.equals(head.getBillSort())) {
	// fptCancelBill.setTradeCode(head.getIssueTradeCod());
	// fptCancelBill.setCanelSort("0");
	// fptCancelBill.setCopNo(head.getIssueCopBillNo());
	// } else if (FptBusinessType.FPT_BILL_BACK.equals(head.getSysType())
	// && FptInOutFlag.IN.equals(head.getBillSort())) {
	// fptCancelBill.setTradeCode(head.getReceiveTradeCod());
	// fptCancelBill.setCanelSort("1");
	// fptCancelBill.setCopNo(head.getReceiveCopBillNo());
	// }
	// fptCancelBill.setDeclareState(DeclareState.APPLY_POR);
	// fptCancelBill = fptManageAction.saveFptCancelBill(new Request(
	// CommonVars.getCurrUser()), fptCancelBill);
	// rlist.add(fptCancelBill);
	// }
	// tableModelCancel.addRows(rlist);
	// }

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
					fptManageAction.permissionCheckInBillEdit(new Request(
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
		// if (tpnPane.getSelectedIndex() == 0) {// 转入
		// tableModelImEx = tableModelImport;
		editinorOutData();
		// } else if (tpnPane.getSelectedIndex() == 1) {// 资料下载
		// tableModelImEx = tableModelDown;
		// editDownData();
		// }
		// else if (tpnPane.getSelectedIndex() == 2) {// 撤消
		// tableModelImEx = tableModelCancel;
		// editCancelData();
		// }
	}

	/**
	 * 修改转入转出资料
	 */
	public void editinorOutData() {
		if (tableModelImport.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmFptImgBillHead.this, "请选择要修改的数据",
					"提示", 0);
			return;
		}
		DgFptBillHead dgFptBillItem = new DgFptBillHead();
		billHead = (FptBillHead) tableModelImport.getCurrentRow();
		billHead = fptManageAction.findFptBillHeadById(
				new Request(CommonVars.getCurrUser()), billHead.getId());
		tableModelImport.updateRow(billHead);
		if (billHead.getAppState().equals(DeclareState.PROCESS_EXE)
				|| billHead.getAppState().equals(DeclareState.WAIT_EAA)) {
			dgFptBillItem.setDataState(DataState.BROWSE);
		} else {
			dgFptBillItem.setDataState(DataState.EDIT);
		}
		dgFptBillItem.setFptInOutFlag(FptInOutFlag.IN);
		dgFptBillItem.setFptBusinessType(FptBusinessType.FPT_BILL);
		dgFptBillItem.setTableModelHead(tableModelImport);
		dgFptBillItem.setVisible(true);
	}

	/**
	 * 修改资料下载数据
	 */
	// public void editDownData() {
	// if (tableModelImEx.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this, "请选择要修改的数据",
	// "提示", 0);
	// return;
	// }
	// DgFptDownData dg = new DgFptDownData(FptBusinessType.FPT_BILL,
	// FptInOutFlag.IN);
	// FptDownData DownData = (FptDownData) tableModelImEx.getCurrentRow();
	// if (DownData.getDeclareState().equals(DeclareState.PROCESS_EXE)
	// || DownData.getDeclareState().equals(DeclareState.WAIT_EAA)) {
	// dg.setDataState(DataState.BROWSE);
	// } else {
	// dg.setDataState(DataState.EDIT);
	// }
	// dg.setTableModel(tableModelImEx);
	// dg.setVisible(true);
	// dg.setVisible(true);
	// }

	// /**
	// * 修改撤消数据
	// */
	// public void editCancelData() {
	// if (tableModelImEx.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this, "请选择要修改的数据",
	// "提示", 0);
	// return;
	// }
	// DgFptCancelBill dg = new DgFptCancelBill();
	// dg.setTableModel(tableModelImEx);
	// dg.setDataState(DataState.EDIT);
	// dg.setVisible(true);
	// }

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
					fptManageAction.permissionCheckInBillDel(new Request(
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
					// if (tpnPane.getSelectedIndex() == 0) {// 转出
					// tableModelImEx = tableModelExport;
					// } else {
					// tableModelImEx = tableModelImport;
					// }
					if (tableModelImport.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmFptImgBillHead.this,
								"请选中要显示的单据！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					billHead = (FptBillHead) tableModelImport.getCurrentRow();
					billHead = fptManageAction.findFptBillHeadById(new Request(
							CommonVars.getCurrUser()), billHead.getId());
					tableModelImport.updateRow(billHead);
					DgFptBillHead dgFptBillItem = new DgFptBillHead();
					dgFptBillItem.setDataState(DataState.BROWSE);
					dgFptBillItem.setTableModelHead(tableModelImport);
					dgFptBillItem.setFptInOutFlag(FptInOutFlag.IN);
					dgFptBillItem.setFptBusinessType(FptBusinessType.FPT_BILL);
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
		if (tableModelImport.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(null, "请选择数据!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (JOptionPane.showConfirmDialog(FmFptImgBillHead.this, "是否确定海关申报!",
				"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return;
		}
		applyFptBill();

	}

	/**
	 * 海关申报转入转出
	 */
	private void applyFptBill() {
		// 判断归并后的资料是否是按申请表序号与交易单位来进行合并
		// tableModelImEx = tableModelImport;
		// if (isMergerCheck()) {
		// JOptionPane.showMessageDialog(FmFptImgBillHead.this,
		// "当申请表序号与交易单位都一致时，应该合并成归并后的一条!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		FptBillHead head = (FptBillHead) tableModelImport.getCurrentRow();
		head = fptManageAction.findFptBillHeadById(
				new Request(CommonVars.getCurrUser()), head.getId());
		tableModelImport.updateRow(head);
		new ApplyInOrOutThread().start();

	}

	/**
	 * 海关申报资料下载
	 */
	// private void applyDown() {
	// tableModelImEx = tableModelDown;
	// FptDownData head = (FptDownData) tableModelImEx.getCurrentRow();
	// head = fptManageAction.findFptDownDataById(
	// new Request(CommonVars.getCurrUser()), head.getId());
	// tableModelImEx.updateRow(head);
	// new ApplyDownThread().start();
	// }

	// /**
	// * 海关申报撤消
	// */
	// private void applyCancel() {
	// tableModelImEx = tableModelCancel;
	// FptCancelBill head = (FptCancelBill) tableModelImEx.getCurrentRow();
	// if (head.getNote() == null || "".equals(head.getNote().trim())) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "备注栏为空，请输入单据撤销原因!", "提示", JOptionPane.INFORMATION_MESSAGE);
	// }
	//
	// head = fptManageAction.findFptCancelBillById(
	// new Request(CommonVars.getCurrUser()), head.getId());
	// tableModelImEx.updateRow(head);
	// new ApplyCancelThread().start();
	// }

	/**
	 * 判断归并后的资料是否是按申请表序号与交易单位来进行合并
	 * 
	 * @author ower
	 * 
	 */
	// private boolean isMergerCheck() {
	// String inOutFlag = FptInOutFlag.IN;
	// String sysType = FptBusinessType.FPT_BILL;
	// // if (tpnPane.getSelectedIndex() == 0) {// 转出
	// // inOutFlag = "0";
	// // tableModelImEx = tableModelExport;
	// // } else {
	// // inOutFlag = "1";
	// // tableModelImEx = tableModelImport;
	// // }
	// FptBillHead c = (FptBillHead) tableModelImport.getCurrentRow();
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
				FptBillHead head = (FptBillHead) tableModelImport
						.getCurrentRow();
				try {
					DeclareFileInfo fileInfo = fptManageAction.applyFptBill(
							new Request(CommonVars.getCurrUser()), head);
					head = fptManageAction.findFptBillHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						tableModelImport.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					String result = "<html>系统生成报文成功<br>" + "报文名称为:"
							+ fileInfo.getFileName() + "<br>";
					JOptionPane.showMessageDialog(FmFptImgBillHead.this,
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
						JOptionPane.showMessageDialog(FmFptImgBillHead.this,
								"系统报文发送失败，请重新发送", "确认", 1);
					} else {
						JOptionPane.showMessageDialog(FmFptImgBillHead.this,
								"系统报文发送成功!", "确认", 1);
					}
				}
			} catch (RuntimeException e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmFptImgBillHead.this,
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
	// throw new RuntimeException("系统申报失败！", ex);
	// }
	// setState();
	// } catch (Exception ex) {
	// throw new RuntimeException("系统申报失败！", ex);
	// } finally {
	// CommonStepProgress.closeStepProgressDialog();
	// }
	// int count = 0;
	//
	// try {
	// // 上传文件
	// if (FptQuery.getInstance().isFtpUpload()) {
	// count = fptMessageAction.ftpUpload(new Request(CommonVars
	// .getCurrUser()));
	//
	// if (count == 0) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "系统报文发送失败，请重新发送", "确认", 1);
	// } else {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "系统报文发送成功!", "确认", 1);
	// }
	// }
	// } catch (RuntimeException e) {
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "系统报文发送失败，请重新发送 " + e.getMessage(), "确认", 1);
	// }
	// JOptionPane.showMessageDialog(FmFptInBillHead.this, "系统报文已生成成功! ",
	// "确认", 1);
	// }
	// }

	/**
	 * 海关申报单据撤消线程
	 * 
	 * @author ower
	 * 
	 */

	// class ApplyCancelThread extends Thread {
	// public void run() {
	// try {
	// String taskId = CommonStepProgress.getExeTaskId();
	// CommonStepProgress.showStepProgressDialog(taskId);
	// CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
	// Request request = new Request(CommonVars.getCurrUser());
	// request.setTaskId(taskId);
	// FptCancelBill head = (FptCancelBill) tableModelImEx
	// .getCurrentRow();
	// try {
	// DeclareFileInfo fileInfo = fptManageAction
	// .applyFptCancelBill(
	// new Request(CommonVars.getCurrUser()), head);
	// head = fptManageAction.findFptCancelBillById(new Request(
	// CommonVars.getCurrUser()), head.getId());
	// if (head != null) {
	// tableModelImEx.updateRow(head);
	// }
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(null,
	// fileInfo.getFileInfoSpec(), "提示", 1);
	// } catch (Exception ex) {
	// CommonStepProgress.closeStepProgressDialog();
	// throw new RuntimeException("系统申报失败！", ex);
	// }
	// setState();
	// } catch (Exception ex) {
	// throw new RuntimeException("系统申报失败！", ex);
	// } finally {
	// CommonStepProgress.closeStepProgressDialog();
	// }
	// int count = 0;
	//
	// try {
	// // 上传文件
	// if (FptQuery.getInstance().isFtpUpload()) {
	// count = fptMessageAction.ftpUpload(new Request(CommonVars
	// .getCurrUser()));
	//
	// if (count == 0) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "系统报文发送失败，请重新发送", "确认", 1);
	// } else {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "系统报文发送成功!", "确认", 1);
	// }
	// }
	// } catch (RuntimeException e) {
	// CommonStepProgress.closeStepProgressDialog();
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "系统报文发送失败，请重新发送 " + e.getMessage(), "确认", 1);
	// }
	// JOptionPane.showMessageDialog(FmFptInBillHead.this, "系统报文已生成成功! ",
	// "确认", 1);
	// }
	// }

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
					processFptBill();
				}
			});
		}
		return btnProcess;
	}

	/**
	 * 回执处理
	 */
	private void processFptBill() {
		int processCount = 0;
		int waitCount = 0;
		StringBuffer noReceipt = new StringBuffer();
		;
		List list = tableModelImport.getList();
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
					noReceipt.append(fptBillHead.getReceiveCopBillNo()).append(
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
										: "\r\n内部编号是：\r\n" + noReceipt
												+ "的收货单没有回执。"), "提示",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "内部编号是：\r\n" + noReceipt
						+ "的收货单没有回执。", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "没有需要回执处理的收货单。", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 回执处理转入转出
	 */
	private FptBillHead processSingleFptBill(FptBillHead head) {
		List lsReturnFile = new ArrayList();
		String copBillNo = "";
		copBillNo = head.getReceiveCopBillNo();
		try {
			if (FptQuery.getInstance().isHttpUpload()) {
				// 下载回执,通过服务器下载
				fptMessageAction.httpDownload(
						new Request(CommonVars.getCurrUser()), copBillNo);
			}
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(FmFptImgBillHead.this,
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
			tableModelImport.updateRow(head);
			return head;
			// JOptionPane.showMessageDialog(FmFptImgBillHead.this, "回执处理成功！\n"
			// + result, "提示", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(FmFptImgBillHead.this,
					"回执处理失败" + ex.getMessage(), "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		// setState();

	}

	// /**
	// * 回执处理资料下载
	// */
	// private void processDown() {
	//
	// if (tableModelImEx.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(this, "请选择要回执处理的下载数据", "提示", 2);
	// return;
	// }
	// FptDownData head = (FptDownData) tableModelImEx.getCurrentRow();
	// try {
	// if (FptQuery.getInstance().isFtpUpload()) {
	// // 下载回执,通过服务器下载
	// fptMessageAction.ftpDownload(
	// new Request(CommonVars.getCurrUser()),
	// head.getOutCopNo());
	// }
	// } catch (RuntimeException e) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "下载报文失败！可能原因：1、网络不通、连接不到远程ftp。2、ftp参数配置错误");
	// }
	// List lsReturnFile = FptMessageHelper.getInstance().showBcsReceiptFile(
	// FptBusinessType.FPT_DOWN_DATA, head.getOutCopNo());
	// if (lsReturnFile.size() <= 0) {
	// return;
	// }
	// try {
	// String result = fptManageAction.processFptDownData(new Request(
	// CommonVars.getCurrUser()), head, lsReturnFile);
	// head = fptManageAction.findFptDownDataById(
	// new Request(CommonVars.getCurrUser()), head.getId());
	// tableModelImEx.updateRow(head);
	// JOptionPane.showMessageDialog(this, "回执处理成功！\n" + result, "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// } catch (Exception ex) {
	// JOptionPane.showMessageDialog(this, "回执处理失败" + ex.getMessage(),
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// setState();
	//
	// }
	//
	// /**
	// * 回执处理撤消
	// */
	// private void processCancel() {
	//
	// if (tableModelImEx.getCurrentRow() == null) {
	// JOptionPane.showMessageDialog(this, "请选择要单据撤消的数据", "提示", 2);
	// return;
	// }
	// FptCancelBill head = (FptCancelBill) tableModelImEx.getCurrentRow();
	// try {
	// if (FptQuery.getInstance().isFtpUpload()) {
	// // 下载回执,通过服务器下载
	// fptMessageAction.ftpDownload(
	// new Request(CommonVars.getCurrUser()), head.getCopNo());
	// }
	// } catch (RuntimeException e) {
	// JOptionPane.showMessageDialog(FmFptInBillHead.this,
	// "下载报文失败！可能原因：1、网络不通、连接不到远程ftp。2、ftp参数配置错误");
	// }
	// List lsReturnFile = FptMessageHelper.getInstance().showBcsReceiptFile(
	// FptBusinessType.FPT_CANCEL_BILL, head.getCopNo());
	// if (lsReturnFile.size() <= 0) {
	// return;
	// }
	// try {
	// String result = fptManageAction.processFptCancelBill(new Request(
	// CommonVars.getCurrUser()), head, lsReturnFile);
	// head = fptManageAction.findFptCancelBillById(
	// new Request(CommonVars.getCurrUser()), head.getId());
	// tableModelImEx.updateRow(head);
	// JOptionPane.showMessageDialog(this, "回执处理成功！\n" + result, "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// } catch (Exception ex) {
	// JOptionPane.showMessageDialog(this, "回执处理失败" + ex.getMessage(),
	// "提示", JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// setState();
	//
	// }

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
			pmOther.add(getMiCasToFpt());
			pmOther.addSeparator();
			pmOther.add(getMiFptToCustoms());
		}
		return pmOther;
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
						JOptionPane.showMessageDialog(FmFptImgBillHead.this,
								"没有使用此功能的权限，如果需要请联系百思维！");
						return;
					}
					DgMakeFptBill dialog = new DgMakeFptBill();
					dialog.setNewFptBillHead(true);
					dialog.setVisible(true);
					// if (tpnPane.getSelectedIndex() == 0) {// 转出
					// initTableExport(getDataSource());
					// } else {
					initTableImport(getDataSource());
					// }
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
							dialog.setImport(FptInOutFlag.IN);
							// 只有收发货单转报关单
							dialog.setSysType(FptBusinessType.FPT_BILL);
							dialog.setVisible(true);
							if (dialog.isOk()) {
								// if (tpnPane.getSelectedIndex() == 0) {// 转出
								// initTableExport(getDataSource());
								// } else {
								initTableImport(getDataSource());
								// }
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
					// if (tpnPane.getSelectedIndex() == 0) {// 转出
					// tableModelImEx = tableModelExport;
					// } else {
					fptManageAction.permissionCheckCopy(new Request(CommonVars
							.getCurrUser()));
					// tableModelImEx = tableModelImport;
					// }
					if (tableModelImport.getCurrentRow() != null) {
						List<FptBillHead> list = fptManageAction.copyFptBillHead(
								new Request(CommonVars.getCurrUser()),
								tableModelImport.getCurrentRows(), 1, 0);
						tableModelImport.addRows(list);
					} else {
						JOptionPane.showMessageDialog(FmFptImgBillHead.this,
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
					FmFptImgBillHead.this.dispose();
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
			tpnPane.addTab("收货单", null, getSpn1(), null);
			tpnPane.addTab("资料下载", null, getPnFptDownData(), null);
			tpnPane.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					tbBarTop.setVisible(tpnPane.getSelectedIndex() != 1);
					tb1.setVisible(tpnPane.getSelectedIndex() != 1);
					if (tpnPane.getSelectedIndex() == 0) {
						initTableImport(getDataSource());
						initCustomer();
					} else if (tpnPane.getSelectedIndex() == 1) {// 资料下载
						// ininTableDown();
						pnFptDownData.queryData();
					}
					setState();
				}
			});
		}
		return tpnPane;
	}

	/**
	 * @return the pnFptDownData
	 */
	public PnFptDownData getPnFptDownData() {
		if (pnFptDownData == null) {
			pnFptDownData = new PnFptDownData(DownLoadState.FPT_BILL,
					FptInOutFlag.IN);
		}
		return pnFptDownData;
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
					changingData();
				}
			});
		}
		return btnChange;
	}

	/**
	 * 变更结转单据
	 * 
	 */
	private void changingData() {
		// if (tpnPane.getSelectedIndex() == 0) {// 转出
		// tableModelImEx = tableModelExport;
		// } else {
		// tableModelImEx = tableModelImport;
		// }
		if (tableModelImport.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定变更单据!", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			FptBillHead c = this.fptManageAction.changingFptBill(new Request(
					CommonVars.getCurrUser()), (FptBillHead) tableModelImport
					.getCurrentRow());
			if (c != null) {
				tableModelImport.addRow(c);
			} else {
				JOptionPane.showMessageDialog(this, "已存在变更单据!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "请选择要变更单据!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
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
			btnRefresh.setBounds(new Rectangle(581, 4, 65, 25));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (tpnPane.getSelectedIndex() == 0) {// 转出
					// initTableExport(getDataSource());
					// } else {
					initTableImport(getDataSource());
					// }
					setState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes spn1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn1() {
		if (spn1 == null) {
			spn1 = new JScrollPane();
			spn1.setViewportView(getTbImport());
		}
		return spn1;
	}

	/**
	 * This method initializes tbImport
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImport() {
		if (tbImport == null) {
			tbImport = new JTable();
			tbImport.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
		}
		tbImport.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) tbImport
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
		return tbImport;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * This method initializes pn3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn3() {
		if (pn3 == null) {
			lbCustomer = new JLabel();
			lbCustomer.setBounds(new Rectangle(291, 5, 52, 18));
			lbCustomer.setText("供应商");
			lbTo = new JLabel();
			lbTo.setText("到");
			lbTo.setBounds(new Rectangle(174, 7, 18, 18));
			lbInputTime = new JLabel();
			lbInputTime.setText("录入日期");
			lbInputTime.setBounds(new Rectangle(11, 6, 61, 18));
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
					boolean flat;
					if (tpnPane.getSelectedIndex() == Integer.valueOf(Integer
							.valueOf(0))) {
						flat = false;
					} else {
						flat = true;
					}
					DgQueryConditionForFptBillHead dg = new DgQueryConditionForFptBillHead();
					dg.setType(Integer.valueOf(FptInOutFlag.IN));
					dg.setImport(flat);
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						// if (tpnPane.getSelectedIndex() == 0) {// 转出
						// initTableExport(list);
						// } else {
						initTableImport(list);
						// }
					}

				}
			});
		}
		return btSreach;
	}

	/**
	 * This method initializes spn5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSpn5() {
		if (spn5 == null) {
			spn5 = new JScrollPane();
		}
		return spn5;
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
			cbbCustomer.setBounds(new Rectangle(347, 4, 231, 23));
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
			pn.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return pn;
	}

	/**
	 * 下载明细
	 */
	// private void DownloadDetail() {
	// // if (tpnPane.getSelectedIndex() == 0) {// 转出
	// // tableModelImEx = tableModelExport;
	// // } else
	// if (tpnPane.getSelectedIndex() == 0) {
	// tableModelImEx = tableModelImport;
	// }
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
	// JOptionPane.showMessageDialog(FmFptInBillHead.this, "下载失败 "
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
									FmFptImgBillHead.this, "上传目录为空，没有可上传的报文",
									"确认", 1);
						}
					} catch (Exception ex) {
						CommonStepProgress.closeStepProgressDialog();
						JOptionPane.showMessageDialog(FmFptImgBillHead.this,
								"系统补发报文失败，请重新发送 " + ex.getMessage(), "确认", 1);
						return;
					}
					JOptionPane.showMessageDialog(FmFptImgBillHead.this,
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
		String sysType = FptBusinessType.FPT_BILL;
		if (tableModelImport.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择要查看回执的收货单", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FptBillHead fptBillHead = (FptBillHead) tableModelImport
				.getCurrentRow();
		String copNo = fptBillHead.getReceiveCopBillNo();
		FptMessageHelper.getInstance().showFptReceiptResult(sysType,
				FptInOutFlag.IN, copNo);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
