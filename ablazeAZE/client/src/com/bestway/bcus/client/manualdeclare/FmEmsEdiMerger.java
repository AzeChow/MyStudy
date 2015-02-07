/*
 * Created on 2004-7-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcs.client.contract.BcsClientHelper;
import com.bestway.bcs.client.contract.DgContract;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.message.DgRecvProcessMessage;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.message.entity.MessageQuery;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DelcareType;
import com.bestway.common.constant.EdiType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmEmsEdiMerger extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnBrowse = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnChange = null;

	private JButton btnDeclare = null;

	private JButton btnProcess = null;

	private JButton btnExit = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JButton jButton7 = null;

	private ManualDeclareAction manualDeclearAction = null;

	private SystemAction systemAction = null;

	private MessageAction messageAction = null;

	private JTableListModel tableModel = null;

	private EmsEdiMergerHead emsEdiMergerHead = null;

	private boolean isChange = false;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JPanel jPanel = null;

	private JComboBox cbbPrintParam = null;

	private JButton btnPrint = null;

	private JPopupMenu jPopupMenuPrint = null;
	private JMenuItem miChangegCount = null;

	private JButton btnUpdate = null;

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
			jPopupMenuPrint.add(getMiChangegCount());
		}
		return jPopupMenuPrint;
	}

	/**
	 * This is the default constructor
	 */
	public FmEmsEdiMerger() {
		super();

		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		initialize();
		// initUIComponents();
	}

	// private void initUIComponents() {
	// // 初始化打印选项
	// this.cbbPrintParam.removeAllItems();
	// // this.cbbPrintParam.addItem(" 打印执行归并关系");
	// this.cbbPrintParam.addItem("  [按变更次数]归并关系清单");
	// this.cbbPrintParam.setMaximumRowCount(1);
	// this.cbbPrintParam.setUI(new CustomBaseComboBoxUI(180));
	// cbbPrintParam.setSelectedIndex(-1);
	// }

	/**
	 * [按变更次数]归并关系清单
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiChangegCount() {
		if (miChangegCount == null) {
			miChangegCount = new JMenuItem();
			miChangegCount.setText("[按变更次数]归并关系清单");
			miChangegCount.setSize(new Dimension(100, 30));
			miChangegCount
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							printReport();
						}
					});
		}
		return miChangegCount;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(855, 572);
		this.setTitle("归并关系-表头");
		this.setContentPane(getJContentPane());
		List dataSource = null;
		dataSource = manualDeclearAction.findEmsEdiMergerHead(new Request(
				CommonVars.getCurrUser()));
		initTable(dataSource);
		setState();
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60,
								Integer.class));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("电话", "tel", 80));
						list.add(addColumn("地址", "address", 80));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "machAbility", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(4)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							FmEmsEdiMerger.this.setChange(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							FmEmsEdiMerger.this.setChange(true);
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(5)
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
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							if (FmEmsEdiMerger.this.isChange)
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(6)
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
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
						else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
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
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getJButton7());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
			jToolBar.add(getBtnUpdate());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setPreferredSize(new Dimension(60, 30));
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					list = manualDeclearAction.findEmsEdiTrHead(new Request(
							CommonVars.getCurrUser()));
					if ((list.size() == 1 && ((EmsEdiTrHead) list.get(0))
							.getDeclareState().equals(DeclareState.PROCESS_EXE))
							|| list.size() == 2) {
						EmsEdiMergerHead emsEdiMergerHead = manualDeclearAction
								.emsMergerAdd(new Request(CommonVars
										.getCurrUser()));
						tableModel.addRow(emsEdiMergerHead);
						setState();
					} else
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"经营范围不在《正在执行》状态，不能备案归并关系", "提示！", 0);
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					edit();
				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes btnView（浏览按钮）
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */

	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setPreferredSize(new Dimension(60, 30));
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"请选择你将要浏览的记录", "提示！", 0);
						return;
					}
					DgEmsEdiMerger dgEmsEdiMerger = new DgEmsEdiMerger();
					dgEmsEdiMerger.setTableModel(tableModel);
					dgEmsEdiMerger.setVisible(true);
				}
			});

		}
		return btnBrowse;
	}

	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmEmsEdiMerger.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgEmsEdiMerger dgEmsEdiMerger = new DgEmsEdiMerger();
		dgEmsEdiMerger
				.setChange(((EmsEdiMergerHead) tableModel.getCurrentRow())
						.getDeclareType().equals(DelcareType.MODIFY));
		dgEmsEdiMerger.setTableModel(tableModel);
		dgEmsEdiMerger.setVisible(true);
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEmsEdiMerger.this,
							"是否要删除该帐册记录？\n请注意：其下的成品料件以及BOM将一并被删除！", "确认", 0) == 0) {
						new deleteMerger().start();
					}
				}
			});

		}
		return btnDelete;
	}

	class deleteMerger extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在删除资料，请稍后...");
				EmsEdiMergerHead emsEdi = (EmsEdiMergerHead) tableModel
						.getCurrentRow();
				manualDeclearAction.deleteEmsEdiMergerImgExg(new Request(
						CommonVars.getCurrUser()), emsEdi);
				manualDeclearAction.deleteEmsEdiMergerHead(new Request(
						CommonVars.getCurrUser()), emsEdi);
				tableModel.deleteRow(emsEdi);
				setState();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"删除失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setPreferredSize(new Dimension(60, 30));
			btnChange.setText("变更");
			btnChange.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					new EmsChange().start();
				}
			});

		}
		return btnChange;
	}

	class EmsChange extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在变更资料，请稍后...");
				EmsEdiMergerHead emsHeadChange = null;
				emsHeadChange = manualDeclearAction
						.emsEdiMergerChange(new Request(CommonVars
								.getCurrUser()));
				tableModel.addRow(emsHeadChange);
				setState();
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"变更失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setPreferredSize(new Dimension(60, 30));
			btnDeclare.setText("海关申报");
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					EmsEdiMergerHead isEmsEdiMergerHead = (EmsEdiMergerHead) tableModel
							.getCurrentRow();
					EmsEdiMergerHead newEmsEdiMergerHead = manualDeclearAction.findNewEmsEdiMergerHeadById(new Request(
									CommonVars.getCurrUser()), isEmsEdiMergerHead.getId());
					
					if (newEmsEdiMergerHead.getDeclareState()
							.equals(DeclareState.WAIT_EAA)) {
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"审批状态为“等待审批”，不允许再次申报", "提示！", 0);
						return;
					}
				
					if (JOptionPane.showConfirmDialog(FmEmsEdiMerger.this,
							"是否确定要将【归并关系】向海关申报吗？", "确认", 0) == 0) {
						if (dataCheck()) { // 数据检查
							EmsEdiMergerHead emsEdiMergerHead = (EmsEdiMergerHead) tableModel
									.getCurrentRow();
							emsEdiMergerHead = manualDeclearAction
									.findEmsEdiMergerHeadById(new Request(
											CommonVars.getCurrUser()),
											emsEdiMergerHead.getId());
							if (emsEdiMergerHead.getDeclareState().equals(
									DeclareState.PROCESS_EXE)) {
								JOptionPane.showMessageDialog(
										FmEmsEdiMerger.this,
										"归并关系状态为“正在执行”不允许发送,请先关闭窗口再打开!");
								return;
							} else if (emsEdiMergerHead.getDeclareState()
									.equals(DeclareState.WAIT_EAA)) {
								JOptionPane.showMessageDialog(
										FmEmsEdiMerger.this,
										"归并关系状态为“等待审批”不允许发送,请先关闭窗口再打开!");
								return;
							}
							new chelonian().start();
						}
					}

				}
			});

		}
		return btnDeclare;
	}

	class tempChange extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在更新资料，请稍后...");
				manualDeclearAction.changeMergerflag(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"更新完毕，您可以正常新增归并前资料！", "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"更新失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	class chelonian extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在申报资料，请稍后...");
				String[] messageNames = null;
				Date now = new Date();
				SimpleDateFormat bartDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				String defaultDate = bartDateFormat.format(now);
				emsEdiMergerHead = (EmsEdiMergerHead) tableModel
						.getCurrentRow();
				emsEdiMergerHead.setDeclareDate(java.sql.Date
						.valueOf(defaultDate));// 申报日期
				emsEdiMergerHead.setDeclareTime(CommonVars.dateToTime(now));// 申报时间
				emsEdiMergerHead = manualDeclearAction
						.saveEmsEdiMergerHead(
								new Request(CommonVars.getCurrUser()),
								emsEdiMergerHead);
				tableModel.updateRow(emsEdiMergerHead);

				List list = CustomBaseList.getInstance().getGbtobigs();

				emsEdiMergerHead = (EmsEdiMergerHead) tableModel
						.getCurrentRow();
				if (emsEdiMergerHead.getDeclareType().equals(
						DelcareType.APPLICATION)) {
					messageNames = messageAction.exportMessage(new Request(
							CommonVars.getCurrUser()), emsEdiMergerHead, 1,
							list);
					messageAction.saveMessageQuery(
							new Request(CommonVars.getCurrUser()),
							MessageQuery.SENDTYPE,
							EdiType.EMS_EDI_MERGER_BEFORE,
							DelcareType.APPLICATION, messageNames[0],
							emsEdiMergerHead.getCopEmsNo(), "EMS212", 0);
					messageAction.saveMessageQuery(
							new Request(CommonVars.getCurrUser()),
							MessageQuery.SENDTYPE,
							EdiType.EMS_EDI_MERGER_AFTER,
							DelcareType.APPLICATION, messageNames[1],
							emsEdiMergerHead.getCopEmsNo(), "EMS215", 0);
				} else if (emsEdiMergerHead.getDeclareType().equals(
						DelcareType.MODIFY)) {
					messageNames = messageAction.exportMessage(new Request(
							CommonVars.getCurrUser()), emsEdiMergerHead, 2,
							list);
					messageAction.saveMessageQuery(
							new Request(CommonVars.getCurrUser()),
							MessageQuery.SENDTYPE,
							EdiType.EMS_EDI_MERGER_BEFORE, DelcareType.MODIFY,
							messageNames[0], emsEdiMergerHead.getCopEmsNo(),
							"EMS222", 0);
					messageAction.saveMessageQuery(
							new Request(CommonVars.getCurrUser()),
							MessageQuery.SENDTYPE,
							EdiType.EMS_EDI_MERGER_AFTER, DelcareType.MODIFY,
							messageNames[1], emsEdiMergerHead.getCopEmsNo(),
							"EMS225", 0);
				}
				// 1，申请备案
				// 2，等待审批
				// 3，正在执行
				emsEdiMergerHead.setDeclareState(DeclareState.WAIT_EAA);
				emsEdiMergerHead.setCheckMark("1"); // 审批标志
				emsEdiMergerHead = manualDeclearAction
						.saveEmsEdiMergerHead(
								new Request(CommonVars.getCurrUser()),
								emsEdiMergerHead);
				tableModel.updateRow(emsEdiMergerHead);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"报文已经生成，位置在中间服务器的：\n" + messageNames[0] + "\n"
								+ messageNames[1] + "\n", "报文已生成", 1);
				setState();

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"申报失败！" + e.getMessage(), "提示", 2);
			}
		}

	}

	/**
	 * 
	 * This method initializes jButton5
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setPreferredSize(new Dimension(60, 30));
			btnProcess.setText("回执处理");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgRecvProcessMessage dgProcessMessage = new DgRecvProcessMessage();
					dgProcessMessage.setType("EmsEdiMergerHead");
					dgProcessMessage.setVisible(true);

					List dataSource = manualDeclearAction
							.findEmsEdiMergerHead(new Request(CommonVars
									.getCurrUser()));
					initTable(dataSource);
					setState();
				}
			});

		}
		return btnProcess;
	}

	private void turning(EmsEdiMergerHead emsEdiMergerHead) {
		if (emsEdiMergerHead.getDeclareType().equals(DelcareType.MODIFY)) {
			List list = manualDeclearAction.findEmsEdiMergerHead(new Request(
					CommonVars.getCurrUser()));
			for (int i = 0; i < list.size(); i++) {
				if (((EmsEdiMergerHead) list.get(i)).getDeclareType().equals(
						DelcareType.APPLICATION)) {
					EmsEdiMergerHead emsHead = (EmsEdiMergerHead) list.get(i);
					emsHead.setHistoryState(new Boolean(true));
					emsHead = manualDeclearAction.saveEmsEdiMergerHead(
							new Request(CommonVars.getCurrUser()), emsHead);
					tableModel.deleteRow(emsHead);
				}
			}
			emsEdiMergerHead.setDeclareType(DelcareType.APPLICATION);
		}
		emsEdiMergerHead.setDeclareState(DeclareState.PROCESS_EXE); // 审批状态
	}

	private void setState() {
		btnAdd.setEnabled(tableModel.getSize() == 0);// 新增
		btnEdit.setEnabled(isDeclareState());// 修改
		btnDelete.setEnabled(isDeclareState());// 删除
		btnChange.setEnabled(isChangeState() && isPROCESS_EXE());// 变更
		jButton.setEnabled(!isPROCESS_EXE());
		btnDeclare.setEnabled(isDeclareState());
		btnProcess.setEnabled((tableModel.getCurrentRow() != null));// 回执处理
		boolean isSend = EmsEdiMergerClientLogic.getIsSend();
		btnChange.setVisible(!isSend);
		if (isSend) { // 分批发送
			btnDelete.setEnabled((tableModel.getCurrentRow() != null)
					&& ((EmsEdiMergerHead) tableModel.getCurrentRow())
							.getDeclareDate() == null);
		}
		EmsEdiMergerHead head = (EmsEdiMergerHead) tableModel.getCurrentRow();
		if (head != null) {
			if (head.getDeclareState().equals("1")
					|| head.getDeclareState().equals("4")) {
				btnUpdate.setEnabled(true);
			} else {
				btnUpdate.setEnabled(false);
			}
		}
	}

	private boolean isChangeState() { // 只有一条记录
		List list = null;
		list = manualDeclearAction.findEmsEdiMergerHead(new Request(CommonVars
				.getCurrUser()));
		if (list.size() == 1) {
			return true;
		}
		return false;
	}

	private boolean isPROCESS_EXE() { // 审批状态为：3
		if (tableModel.getCurrentRow() != null
				&& ((EmsEdiMergerHead) tableModel.getCurrentRow())
						.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			return true;
		}
		return false;
	}

	private boolean isDeclareState() { // 审批状态为：1
		if (tableModel.getCurrentRow() != null
				&& !((EmsEdiMergerHead) tableModel.getCurrentRow())
						.getDeclareState().equals(DeclareState.PROCESS_EXE)
				&& !((EmsEdiMergerHead) tableModel.getCurrentRow())
						.getDeclareState().equals(DeclareState.WAIT_EAA)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * This method initializes jButton6
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmEmsEdiMerger.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModel == null)
								return;
							if (tableModel.getCurrentRow() == null)
								return;
							setState();
						}
					});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setPreferredSize(new Dimension(60, 30));
			jButton7.setVisible(false);
			jButton7.setText("打印");
		}
		return jButton7;
	}

	/**
	 * @return Returns the isChange.
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            The isChange to set.
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setPreferredSize(new Dimension(60, 30));
			jButton.setText("数据检查");
			jButton.setVisible(false);
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataCheck();
				}
			});

		}
		return jButton;
	}

	private boolean dataCheck() {
		if (tableModel.getRowCount() < 1) {
			JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
					"归并关系为空，请先申请备案！", "提示", 2);
			return false;
		}
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmEmsEdiMerger.this, "请选择你将要数据检查的记录",
					"提示！", 2);
			return false;
		}
		EmsEdiMergerHead yy = (EmsEdiMergerHead) tableModel.getCurrentRow();
		/*
		 * if (yy.getCopEmsNo() == null || yy.getCopEmsNo().equals("")){
		 * JOptionPane
		 * .showMessageDialog(FmEmsEdiMerger.this,"检查资料完整性出错:企业内部编号不能为空！"
		 * ,"提示",2); return false; } if (yy.getSancEmsNo() == null ||
		 * yy.getSancEmsNo().equals("")){
		 * JOptionPane.showMessageDialog(FmEmsEdiMerger
		 * .this,"检查资料完整性出错:批文帐册号不能为空！","提示",2); return false; }
		 */
		if (CommonVars.formatDouble(yy.getMaxTurnMoney()).doubleValue() > CommonVars
				.formatDouble(yy.getMachAbility()).doubleValue() * 0.5) {
			JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
					"检查资料完整性出错:最大周转金额不能大于年加工生产能力的1/2！", "提示", 2);
			return false;
		}
		/*
		 * List dataSourceImgAfter = manualDeclearAction //归并后料件数据
		 * .findEmsEdiMergerImgAfter(new Request(CommonVars.getCurrUser()), yy);
		 * int x = 0; for (int i=0;i<dataSourceImgAfter.size();i++){
		 * EmsEdiMergerImgAfter imgAfter = (EmsEdiMergerImgAfter)
		 * dataSourceImgAfter.get(i); if (imgAfter.getLegalUnitGene() == null ||
		 * imgAfter.getLegalUnitGene().equals(Double.valueOf(0))){
		 * JOptionPane.showMessageDialog
		 * (FmEmsEdiMerger.this,"归并后料件第一法定单位比例因子不可为空！","提示",2); break; } x = 1;
		 * } if (x == 0){ return false; } List dataSourceExgAfter =
		 * manualDeclearAction //归并后成品数据 .findEmsEdiMergerExgAfter(new
		 * Request(CommonVars.getCurrUser()), yy); for (int
		 * i=0;i<dataSourceExgAfter.size();i++){ EmsEdiMergerExgAfter exgAfter =
		 * (EmsEdiMergerExgAfter) dataSourceExgAfter.get(i); if
		 * (exgAfter.getLegalUnitGene() == null ||
		 * exgAfter.getLegalUnitGene().equals(Double.valueOf(0))){
		 * JOptionPane.showMessageDialog
		 * (FmEmsEdiMerger.this,"归并后成品第一法定单位比例因子不可为空！","提示",2); break; } x = 2;
		 * } if (x == 0 || x == 1){ return false; }
		 * JOptionPane.showMessageDialog
		 * (FmEmsEdiMerger.this,"归并关系数据检查无误！","提示信息",2);
		 */
		return true;
	}

	/**
	 * This method initializes jButton1 注：将不在帐册归并关系中的资料在内部归并中打上标记
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setPreferredSize(new Dimension(60, 30));
			jButton1.setText("执行更新");
			jButton1.setVisible(false);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new tempChange().start();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("查看未备案");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInnerMergeNoMerger obj = new DgInnerMergeNoMerger();
					obj.setVisible(true);
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("初始化申报状态");
			jButton3.setVisible(false);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
							"初始化申报状态：\n" + "将该归并表体资料根据修改标记为【未修改】来设定申报状态为【已申报】",
							"提示！", 0);
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"请选择你将要初始化申报的记录", "提示！", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmEmsEdiMerger.this,
							"是否确定要将【归并关系】初始化申报吗？", "确认", 0) == 0) {
						new initSendState().start();
					}
				}
			});
		}
		return jButton3;
	}

	class initSendState extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在初始化要申报的资料，请稍后...");
				EmsEdiMergerHead yy = (EmsEdiMergerHead) tableModel
						.getCurrentRow();
				manualDeclearAction.initSendState(
						new Request(CommonVars.getCurrUser()), yy);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"系统初始化申报状态完成！", "提示", 1);
				setState();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"初始化失败！" + e.getMessage(), "提示", 2);
			}
		}

	}

	class changetemp extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmEmsEdiMerger.this);
				CommonProgress.setMessage("系统正在变更资料，请稍后...");
				EmsEdiMergerHead head = (EmsEdiMergerHead) tableModel
						.getCurrentRow();
				manualDeclearAction.changetemp(
						new Request(CommonVars.getCurrUser()), head);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this, "完成", "提示",
						2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
						"变更失败：！" + e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setPreferredSize(new Dimension(60, 30));
			jButton4.setText("临时更新");
			jButton4.setVisible(false);
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new changetemp().start();
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	// private JPanel getJPanel() {
	// if (jPanel == null) {
	// jPanel = new JPanel();
	// jPanel.setLayout(null);
	// jPanel.add(getCbbPrintParam(), null);
	// jPanel.add(getBtnPrint(), null);
	// }
	// return jPanel;
	// }
	/**
	 * This method initializes cbbPrintParam
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbPrintParam() {
		if (cbbPrintParam == null) {
			cbbPrintParam = new JComboBox();
			cbbPrintParam.setBounds(new Rectangle(5, 1, 118, 28));
		}
		return cbbPrintParam;
	}

	/**
	 * This method initializes btnPrint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setPreferredSize(new Dimension(60, 30));
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(125, 1, 66, 28));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// printReport();
					getJPopupMenuPrint()
							.show(btnPrint, 0, btnPrint.getHeight());
				}
			});
		}
		return btnPrint;
	}

	/**
	 * 打印报表
	 * 
	 */
	private void printReport() {
		// if (this.cbbPrintParam.getSelectedItem() == null) {
		// JOptionPane.showMessageDialog(null, "请选择要打印的项目!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return;
		// }
		List list = new ArrayList();
		EmsEdiMergerHead emsEdiMergerHead = (EmsEdiMergerHead) tableModel
				.getCurrentRow();
		if (emsEdiMergerHead != null) {
			list.add(emsEdiMergerHead);
		} else {
			JOptionPane.showMessageDialog(this, "请选择归并关系表头记录！", "提示",
					JOptionPane.YES_NO_OPTION);
			return;
		}
		// case 0:// 打印完整归并关系
		// // printAllEms(emsEdiMergerHead);
		// // break;
		// case 1:// [按变更次数] 归并关系变更清单
		try {
			DgEmsPrintTime dg = new DgEmsPrintTime();
			dg.setVisible(true);
			if (!dg.isOk()) {
				return;
			}
			int time = dg.getTime();
			if (time <= -1) {
				return;
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsEdiMergerHead.getEmsNo() == null ? ""
					: emsEdiMergerHead.getEmsNo());
			parameters.put("tradeName",
					emsEdiMergerHead.getTradeName() == null ? ""
							: emsEdiMergerHead.getTradeName());
			parameters.put("tradeCode",
					emsEdiMergerHead.getTradeCode() == null ? ""
							: emsEdiMergerHead.getTradeCode());
			parameters.put("machCode",
					emsEdiMergerHead.getMachCode() == null ? ""
							: emsEdiMergerHead.getMachCode());
			parameters.put("machName",
					emsEdiMergerHead.getMachName() == null ? ""
							: emsEdiMergerHead.getMachName());
			parameters.put("copEmsNo",
					emsEdiMergerHead.getCopEmsNo() == null ? ""
							: emsEdiMergerHead.getCopEmsNo());
			parameters.put("sancEmsNo",
					emsEdiMergerHead.getSancEmsNo() == null ? ""
							: emsEdiMergerHead.getSancEmsNo());
			parameters.put("emsApprNo",
					emsEdiMergerHead.getSancEmsNo() == null ? ""
							: emsEdiMergerHead.getEmsApprNo());
			parameters.put("machAbility",
					emsEdiMergerHead.getSancEmsNo() == null ? ""
							: emsEdiMergerHead.getMachAbility());
			parameters.put("maxTurnMoney",
					emsEdiMergerHead.getSancEmsNo() == null ? ""
							: emsEdiMergerHead.getMaxTurnMoney());
			parameters.put("tel", emsEdiMergerHead.getSancEmsNo() == null ? ""
					: emsEdiMergerHead.getTel());
			parameters.put("address",
					emsEdiMergerHead.getSancEmsNo() == null ? ""
							: emsEdiMergerHead.getAddress());
			//
			// 料件明细子报表
			//
			List ListfindemsEdiMergerHeadImgChange = manualDeclearAction
					.findemsEdiMergerHeadImgChange(
							new Request(CommonVars.getCurrUser()),
							emsEdiMergerHead, time);
			CustomReportDataSource dimg = new CustomReportDataSource(
					ListfindemsEdiMergerHeadImgChange);
			InputStream ImgFinishedProductSubReportStream = FmEmsEdiMerger.class
					.getResourceAsStream("report/EmsMergerChangeImgSubList.jasper");
			JasperReport imgFinishedProductSubReport = (JasperReport) JRLoader
					.loadObject(ImgFinishedProductSubReportStream);
			// 主表
			parameters.put("imgFinishedProductSubReport",
					imgFinishedProductSubReport);
			parameters.put("subReportDataSource", dimg);

			List<EmsEdiMergerHead> EmsList = new ArrayList<EmsEdiMergerHead>();
			EmsList.add(emsEdiMergerHead);
			CustomReportDataSource ds = new CustomReportDataSource(EmsList);
			InputStream masterReportStream = FmEmsEdiMerger.class
					.getResourceAsStream("report/EmsMergerChangeList.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			// 显示成品
			List finishedProductDataSource = manualDeclearAction
					.findemsEdiMergerHeadExgChange(
							new Request(CommonVars.getCurrUser()),
							emsEdiMergerHead, time);
			CustomReportDataSource finishedProductDataSourceds = new CustomReportDataSource(
					finishedProductDataSource);
			Map<String, Object> exgMap = new HashMap<String, Object>();
			exgMap.put("beforePageCount", jasperPrint.getPages().size());
			InputStream exportFinishedProductSubReportStream = FmEmsEdiMerger.class
					.getResourceAsStream("report/EmsMergerChangeExgList.jasper");
			JasperPrint jasperPrintContractExg = JasperFillManager.fillReport(
					exportFinishedProductSubReportStream, exgMap,
					finishedProductDataSourceds);
			int count = jasperPrintContractExg.getPages().size();
			for (int i = 0; i < count; i++) {
				jasperPrint.addPage((JRPrintPage) jasperPrintContractExg
						.getPages().get(i));
			}
			// 显示Bom
			List finishedUnitWasteDataSource = manualDeclearAction
					.findemsEdiMergerHeadBomChange(
							new Request(CommonVars.getCurrUser()),
							emsEdiMergerHead, time);
			CustomReportDataSource EmsUnitWasteReportStreamds = new CustomReportDataSource(
					finishedUnitWasteDataSource);
			Map<String, Object> BomMap = new HashMap<String, Object>();
			BomMap.put("beforePageCount", jasperPrint.getPages().size());
			InputStream contractUnitWasteReportStream = FmEmsEdiMerger.class
					.getResourceAsStream("report/EmsMergerChangeBomList.jasper");
			JasperPrint jasperPrintContractUnitWaste = JasperFillManager
					.fillReport(contractUnitWasteReportStream, BomMap,
							EmsUnitWasteReportStreamds);
			int size = jasperPrintContractUnitWaste.getPages().size();
			for (int i = 0; i < size; i++) {
				jasperPrint.addPage((JRPrintPage) jasperPrintContractUnitWaste
						.getPages().get(i));
			}
			//
			// 显示所有报表
			//
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void printAllEms(EmsEdiMergerHead emsEdiMergerHead) {
		try {
			//
			// 表头和料件清单
			//
			// InputStream emsMaterielStream = FmEmsHeadH2k.class
			// .getResourceAsStream("report/EmsPutOnRecordDetail.jasper");
			// // List emsMaterielList = manualDeclearAction.findEmsHeadH2kImg(
			// // new Request(CommonVars.getCurrUser()), emsEdiMergerHead);
			// // CustomReportDataSource emsMaterielDs = new
			// CustomReportDataSource(
			// // emsMaterielList);
			// // Map<String, Object> parameters1 = new HashMap<String,
			// Object>();
			// // parameters1.put("address", emsEdiMergerHead.getAddress() ==
			// null ? ""
			// // : emsEdiMergerHead.getAddress());
			// // parameters1.put("tel", emsEdiMergerHead.getTel() == null ? ""
			// // : emsEdiMergerHead.getTel());
			// // parameters1.put("emsNo", emsEdiMergerHead.getEmsNo() == null ?
			// ""
			// // : emsEdiMergerHead.getEmsNo());
			// // parameters1.put("outTradeCo",
			// // emsEdiMergerHead.getOutTradeCo() == null ? "" :
			// emsEdiMergerHead
			// // .getOutTradeCo());
			// // parameters1.put("tradeName", emsEdiMergerHead.getTradeName()
			// == null ? ""
			// // : emsHeadH2k.getTradeName());
			// // parameters1.put("tradeCode", emsHeadH2k.getTradeCode() == null
			// ? ""
			// // : emsHeadH2k.getTradeCode());
			// // parameters1.put("machCode", emsHeadH2k.getMachCode() == null ?
			// ""
			// // : emsHeadH2k.getMachCode());
			// // parameters1.put("machName", emsHeadH2k.getMachName() == null ?
			// ""
			// // : emsHeadH2k.getMachName());
			// // parameters1.put("copEmsNo", emsHeadH2k.getCopEmsNo() == null ?
			// ""
			// // : emsHeadH2k.getCopEmsNo());
			// // parameters1.put("sancEmsNo", emsHeadH2k.getSancEmsNo() == null
			// ? ""
			// // : emsHeadH2k.getSancEmsNo());
			// // parameters1.put("machAbility",
			// // emsHeadH2k.getMachAbility() == null ? "0" : String
			// // .valueOf(emsHeadH2k.getMachAbility()));
			// // parameters1.put("maxTurnMoney",
			// // emsHeadH2k.getMaxTurnMoney() == null ? "0" : String
			// // .valueOf(emsHeadH2k.getMaxTurnMoney()));
			// // parameters1.put("emsApprNo", emsHeadH2k.getEmsApprNo() == null
			// ? ""
			// // : emsHeadH2k.getEmsApprNo());
			// //
			// // JasperPrint masterJasperPrint = JasperFillManager.fillReport(
			// // emsMaterielStream, parameters1, emsMaterielDs);
			// //
			// // 成品清单
			// //
			// InputStream emsProductStream = FmEmsHeadH2k.class
			// .getResourceAsStream("report/EmsPutOnRecordDetailProduct.jasper");
			// List emsProductList = manualDeclearAction.findEmsHeadH2kExg(
			// new Request(CommonVars.getCurrUser()), emsHeadH2k);
			// CustomReportDataSource emsProductDs = new CustomReportDataSource(
			// emsProductList);
			// Map<String, Object> parameters2 = new HashMap<String, Object>();
			// parameters2.put("beforePageCount", masterJasperPrint.getPages()
			// .size());
			// JasperPrint emsProductJasperPrint = JasperFillManager.fillReport(
			// emsProductStream, parameters2, emsProductDs);
			// int size = emsProductJasperPrint.getPages().size();
			// for (int i = 0; i < size; i++) {
			// masterJasperPrint.addPage((JRPrintPage) emsProductJasperPrint
			// .getPages().get(i));
			// }
			// //
			// // 成品清单
			// //
			// InputStream emsBomStream = FmEmsHeadH2k.class
			// .getResourceAsStream("report/EmsPutOnRecordDetailBom.jasper");
			// List emsBomList = manualDeclearAction.findEmsHeadH2kBomOrder(
			// new Request(CommonVars.getCurrUser()), emsHeadH2k);
			// CustomReportDataSource emsBomDs = new CustomReportDataSource(
			// emsBomList);
			// Map<String, Object> parameters3 = new HashMap<String, Object>();
			// parameters3.put("beforePageCount", masterJasperPrint.getPages()
			// .size());
			// JasperPrint emsBomJasperPrint = JasperFillManager.fillReport(
			// emsBomStream, parameters3, emsBomDs);
			//
			// size = emsBomJasperPrint.getPages().size();
			// for (int i = 0; i < size; i++) {
			// masterJasperPrint.addPage((JRPrintPage) emsBomJasperPrint
			// .getPages().get(i));
			// }
			// //
			// // 显示所有报表
			// //
			// DgReportViewer viewer = new DgReportViewer(masterJasperPrint);
			// viewer.setVisible(true);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method initializes btnUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {
		if (btnUpdate == null) {
			btnUpdate = new JButton();
			btnUpdate.setText("同步内部归并");
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmEmsEdiMerger.this,
								"请选择你将要同步的记录", "提示！", 0);
						return;
					}
					DgUpdateInnerMerge dg = new DgUpdateInnerMerge();
					dg.setHead((EmsEdiMergerHead) tableModel.getCurrentRow());
					dg.setEms(false);
					dg.setVisible(true);
				}
			});
		}
		return btnUpdate;
	}

}
