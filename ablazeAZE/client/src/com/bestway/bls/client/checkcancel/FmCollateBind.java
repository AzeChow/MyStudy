/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.SwingWorkerTask;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.bls.action.BlsCheckCancelAction;
import com.bestway.bls.client.message.BlsMessageHelper;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.CollateBind;
import com.bestway.bls.entity.FormType;
import com.bestway.bls.entity.StorageBill;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author luosheng 核销管理
 */
public class FmCollateBind extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private Integer imp = null;
	/**
	 * 新增
	 */
	private JButton btnAdd = null;

	/**
	 * 修改
	 */
	private JButton btnEdit = null;

	/**
	 * 删除
	 */
	private JButton btnDelete = null;

	/**
	 * 海关申报
	 */
	private JButton btnApply = null;

	/**
	 * 关闭
	 */
	private JButton btnClose = null;

	/**
	 * 核销表格
	 */
	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 电子手册中期核销表头
	 */
	private DzscCheckHead dzscCheckHead = null; // @jve:decl-index=0:

	/**
	 * 核销接口
	 */
	private DzscContractCavAction dzscContractCavAction = null; // @jve:decl-index=0:

	private DzscAction dzscAction = null;

	private MaterialApplyAction materialApplyAction = null;

	/**
	 * 入仓单-出口报关单表格模型
	 */
	private JTableListModel tableModel = null;

	/**
	 * 出仓单-出口报关单表格模型
	 */
	private JTableListModel tableModel1 = null;

	/**
	 * 出仓单-入仓单表格模型
	 */
	private JTableListModel tableModel2 = null;

	// private EmsEdiTrHead emsEdiTrHead = null;

	private boolean isChange = false;

	/**
	 * 处理回执
	 */
	private JButton btnProcess = null;

	/**
	 * 消息处理接口
	 */
	private MessageAction messageAction = null;

	/**
	 * 从仓单导入
	 */
	private JButton btnImportDataByBill = null;

	/**
	 * 保税物流核销表头
	 */
	private BlsCheckCancelAction blsCheckCancelAction = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar1 = null;

	/**
	 * This is the default constructor
	 */
	public FmCollateBind() {
		super();
		blsCheckCancelAction = (BlsCheckCancelAction) CommonVars
				.getApplicationContext().getBean("blsCheckCancelAction");
		dzscContractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		messageAction = (MessageAction) CommonVars.getApplicationContext()
				.getBean("messageAction");
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(650, 395);
		this.setTitle("保税物流核查-表头");
		this.setContentPane(getJContentPane());

		// List dataSource = null;
		// dataSource = dzscContractCavAction.findDzscCheckHead(new Request(
		// CommonVars.getCurrUser()));
		//
		// initTable(dataSource);
		serch();

	}

	/**
	 * 显示窗口
	 */
	public void setVisible(boolean b) {
		if (b) {
			this.pnCommonQueryPage.setInitState();
			setState();
		}
		super.setVisible(b);
	}

	/**
	 * 表格初始化
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTable(final List list) {
		JTableListModel tableModel = getModel();
		JTable jTable = getTable();
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单证编号", "formID", 100));
						list.add(addColumn("企业编码", "brief.code", 100));
						list.add(addColumn("单证类型", "formType", 120));
						list.add(addColumn("核销单证类型", "collateFormType", 100));
						list.add(addColumn("申报状态", "declareState", 120));
						list.add(addColumn("创建时间", "createDate", 120));
						list.add(addColumn("备注", "note", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FormType
								.getNote(value.toString()));
						return this;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FormType
								.getNote(value.toString()));
						return this;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						String returnValue = "初始状态";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DeclareState.APPLY_POR)) {
							returnValue = "初始状态";

						} else if (value.equals(DeclareState.WAIT_EAA))
							returnValue = "等待审批";
						else if (value.equals(DeclareState.PROCESS_EXE))
							returnValue = "正在执行";
						else if (value.equals(DeclareState.BACK_BILL))
							returnValue = "退单";
						return returnValue;
					}
				});
		if (jTabbedPane.getSelectedIndex() == 0) {
			this.tableModel = tableModel;
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			this.tableModel1 = tableModel;
		} else {
			this.tableModel2 = tableModel;
		}
		return tableModel;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */

	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.NORTH);
			jContentPane.add(getJToolBar2(), BorderLayout.SOUTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
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
			jToolBar = new JToolBar();
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnImportDataByBill());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnCheck());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * 用于保存核销表头,把界面上的信息保存到实体
	 * 
	 * @param checkHead
	 * @param emsHeadH2k
	 */
	private void fillCheckHead(DzscCheckHead checkHead,
			DzscEmsPorHead emsHeadH2k) {
		checkHead.setEmsNo(emsHeadH2k.getEmsNo());
		checkHead.setTradeCode(emsHeadH2k.getTradeCode());
		checkHead.setTradeName(emsHeadH2k.getTradeName());
		checkHead.setMachCode(emsHeadH2k.getMachCode());
		checkHead.setMachName(emsHeadH2k.getMachName());
		// DzscMaterielHead dzscMaterielHead = materialApplyAction
		// .findDzscMaterielHead(new Request(CommonVars.getCurrUser()));
		// checkHead.setCopTrNo(dzscMaterielHead.getCopEntNo());
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String defaultDate = bartDateFormat.format(now);
		checkHead.setBeginDate(java.sql.Date.valueOf(defaultDate));
		checkHead.setCompany(CommonVars.getCurrUser().getCompany()); // 公司id
		checkHead.setDeclareState(DzscState.ORIGINAL);
		checkHead.setManageObject(emsHeadH2k.getManageObject());
		checkHead.setMasterCustoms(((Company) emsHeadH2k.getCompany())
				.getMasterCustoms());
	}

	/**
	 * 新增核销表头
	 * 
	 * @return
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");

			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 新增
	 * 
	 * */
	private void addData() {
		Company com = this.blsCheckCancelAction.findCompany(new Request(
				CommonVars.getCurrUser()));
		if (com.getCode() == null || "".equals(com.getCode())) {
			JOptionPane.showMessageDialog(FmCollateBind.this, "公司的加工单位编码不能为空",
					"核查", JOptionPane.OK_OPTION);
			return;
		}
		Brief brief = this.blsCheckCancelAction.findBrief(new Request(
				CommonVars.getCurrUser()), com.getCode());
		if (com.getCode() == null || "".equals(com.getCode())) {
			JOptionPane.showMessageDialog(FmCollateBind.this, "编码 == ["
					+ com.getCode() + "] 在海关注册公司不存在,请检查!", "核查",
					JOptionPane.OK_OPTION);
			return;
		}
		// 新建
		CollateBind newObj = new CollateBind();
		newObj.setBrief(brief);
		newObj.setCompany(CommonUtils.getCompany());
		newObj.setDeclareState(DeclareState.APPLY_POR);
		if (jTabbedPane.getSelectedIndex() == 0) {
			imp = 0;
			newObj.setFormType(FormType.IM_MFT);
			newObj.setCollateFormType(FormType.IM_ENT);

			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.ADD);
			dgCheck.setCollateBindNew(newObj);
			dgCheck.setImExp(imp);
			dgCheck.setVisible(true);

			// newObj = this.blsCheckCancelAction.newCollateBind(new Request(
			// CommonVars.getCurrUser()), newObj);
			// getModel().addRow(newObj);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			imp = 1;
			newObj.setFormType(FormType.EX_MFT);
			newObj.setCollateFormType(FormType.EX_ENT);

			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.ADD);
			dgCheck.setCollateBindNew(newObj);
			dgCheck.setImExp(imp);
			dgCheck.setVisible(true);
			// newObj = this.blsCheckCancelAction.newCollateBind(new Request(
			// CommonVars.getCurrUser()), newObj);
			// getModel().addRow(newObj);
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			imp = 2;
			newObj.setFormType(FormType.EX_MFT);
			newObj.setCollateFormType(FormType.IM_MFT);

			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.ADD);
			dgCheck.setCollateBindNew(newObj);
			dgCheck.setImExp(imp);
			dgCheck.setVisible(true);
			// newObj = this.blsCheckCancelAction.newCollateBind(new Request(
			// CommonVars.getCurrUser()), newObj);
			// getModel().addRow(newObj);
		}
		setState();
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
	 * 修改表头
	 */
	private void edit() {

		if (getModel().getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmCollateBind.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		if (jTabbedPane.getSelectedIndex() == 0) {
			imp = 0;
			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.EDIT);
			dgCheck.setVisible(true);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			imp = 1;
			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.EDIT);
			dgCheck.setVisible(true);
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			imp = 2;
			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.EDIT);
			dgCheck.setVisible(true);
		}
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
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getModel().getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCollateBind.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					List checkHeads = getModel().getCurrentRows();

					if (JOptionPane.showConfirmDialog(FmCollateBind.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
						// dzscContractCavAction.deleteAllCheckImgExg(new
						// Request(
						// CommonVars.getCurrUser()),checkHead);
						blsCheckCancelAction.deleteCollateBind(new Request(
								CommonVars.getCurrUser()), checkHeads);
						getModel().deleteRows(checkHeads);
						setState();
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes btnDeclare
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getModel().getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCollateBind.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmCollateBind.this,
							"确定要进行海关申报吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					CollateBind collateBind = (CollateBind) getModel()
							.getCurrentRow();
					collateBind = blsCheckCancelAction.applyCollatebind(
							new Request(CommonVars.getCurrUser()), collateBind);
					String declareInfo = "";
					if (DeclareState.PROCESS_EXE.equals(collateBind
							.getDeclareState())) {
						declareInfo = ("单证核销申报" + collateBind.getFormID() + " 申报成功！");
					} else if (DeclareState.APPLY_POR.equals(collateBind
							.getDeclareState())) {
						declareInfo = ("单证核销申报" + collateBind.getFormID() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA.equals(collateBind
							.getDeclareState())) {
						declareInfo = ("单证核销申报" + collateBind.getFormID() + " 正在等待审批！");
					}
					getModel().updateRow(collateBind);
					setState();
					JOptionPane
							.showMessageDialog(FmCollateBind.this, declareInfo,
									"提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});

		}
		return btnApply;
	}

	/**
	 * 海关申报
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
				dzscCheckHead = (DzscCheckHead) getModel().getCurrentRow();
				try {
					DeclareFileInfo fileInfo = dzscContractCavAction
							.applyDzscCheck(new Request(CommonVars
									.getCurrUser()), dzscCheckHead);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmCollateBind.this, fileInfo
							.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmCollateBind.this, "系统申报失败 "
							+ ex.getMessage(), "确认", 1);
				}
				dzscCheckHead.setDeclareState(DzscState.APPLY);
				getModel().updateRow(dzscCheckHead);
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * 设置界面控件状态
	 */
	private void setState() {
		if (getModel() == null || getModel().getCurrentRow() == null) {
			return;
		}
		CollateBind data = (CollateBind) getModel().getCurrentRow();
		String state = data.getDeclareState();
		if (state == null || state.equals("")) {
			return;
		}
		this.btnEdit.setEnabled(state.equals(DeclareState.APPLY_POR)
				|| state.equals(DeclareState.BACK_BILL));
		this.btnDelete.setEnabled(state.equals(DeclareState.APPLY_POR));
		this.btnApply.setEnabled(state.equals(DeclareState.APPLY_POR));
		this.btnProcess.setEnabled(state.equals(DeclareState.WAIT_EAA));
	}

	/**
	 * 
	 * This method initializes btnClose
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton s
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmCollateBind.this.dispose();

				}
			});

		}
		return btnClose;
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() > 1) {
						browseData();
					}
				}

			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting() || tableModel == null) {
								return;
							}
							setState();
						}
					});
		}
		return jTable;
	}

	/** browse Data */
	private void browseData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			imp = 0;
			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.BROWSE);
			dgCheck.setVisible(true);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			imp = 1;
			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.BROWSE);
			dgCheck.setVisible(true);
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			imp = 2;
			DgCollateBind dgCheck = new DgCollateBind(imp);
			dgCheck.setTableModel(getModel());
			dgCheck.setDataState(DataState.BROWSE);
			dgCheck.setVisible(true);
		}
	}

	/**
	 * 取得当前表格模型
	 * 
	 * @return
	 */
	private JTableListModel getModel() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			return tableModel;
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			return tableModel1;
		}
		return tableModel2;

	}

	/**
	 * 取得当前表格
	 * 
	 * @return
	 */
	private JTable getTable() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			return jTable;
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			return jTable1;
		}
		return jTable2;

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
	 * @return Returns the checkCancelAction.
	 */
	public DzscContractCavAction getDzscContractCavAction() {
		return dzscContractCavAction;
	}

	/**
	 * @param checkCancelAction
	 *            The checkCancelAction to set.
	 */
	public void setCheckCancelAction(DzscContractCavAction checkCancelAction) {
		this.dzscContractCavAction = checkCancelAction;
	}

	/**
	 * @return Returns the checkHead.
	 */
	public DzscCheckHead getDzscCheckHead() {
		return dzscCheckHead;
	}

	/**
	 * @param checkHead
	 *            The checkHead to set.
	 */
	public void setDzscCheckHead(DzscCheckHead checkHead) {
		this.dzscCheckHead = checkHead;
	}

	/**
	 * This method initializes btnProcess
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("处理回执");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getModel().getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCollateBind.this,
								"请选择要回执处理的单证核销资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmCollateBind.this,
							"确定要进行回执处理吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					CollateBind collateBind = (CollateBind) getModel()
							.getCurrentRow();
					BlsReceiptResult blsReceiptResult = BlsMessageHelper
							.getInstance().showBlsReceiptFile(
									BlsServiceType.COLLATEBIND_DECLARE,
									collateBind.getId());
					if (blsReceiptResult == null) {
						return;
					}
					collateBind = blsCheckCancelAction.processCollatebind(
							new Request(CommonVars.getCurrUser()), collateBind,
							blsReceiptResult);
					String resultInfo = "";
					if (DeclareState.PROCESS_EXE.equals(collateBind
							.getDeclareState())) {
						resultInfo = ("单证核销申报" + collateBind.getFormID() + " 申报成功！");
					} else if (DeclareState.APPLY_POR.equals(collateBind
							.getDeclareState())) {
						resultInfo = ("单证核销申报" + collateBind.getFormID() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA.equals(collateBind
							.getDeclareState())) {
						resultInfo = ("单证核销申报" + collateBind.getFormID() + " 正在等待审批！");
					}
					getModel().updateRow(collateBind);
					setState();
					JOptionPane.showMessageDialog(FmCollateBind.this,
							resultInfo, "提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnProcess;
	}

	/**
	 * This method initializes btnImportDataByBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportDataByBill() {
		if (btnImportDataByBill == null) {
			btnImportDataByBill = new JButton();
			btnImportDataByBill.setText("从仓单导入");
			btnImportDataByBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							saveImportDataByBill();
						}
					});
		}
		return btnImportDataByBill;
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJToolBar1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getPnCommonQueryPage());
		}
		return jToolBar1;
	}

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTable2 = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JToolBar jToolBar2 = null;

	private JPanel jPanel31 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbStartDate = null;

	private JLabel jLabel11 = null;

	private JCalendarComboBox cbbEndDate = null;

	/**
	 * 查询按钮
	 */
	private JButton btnFind = null;

	/**
	 * 检查按钮
	 */
	private JButton btnCheck = null;

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmCollateBind.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmCollateBind.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * 获得数据源
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		Integer type = 0;
		if (jTabbedPane.getSelectedIndex() == 1) {
			type = 1;
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			type = 2;
		}
		return this.blsCheckCancelAction.findCollateBind(new Request(CommonVars
				.getCurrUser()), index, length, property, value, isLike, type);
	}

	/** 导入数据来自创单 */
	private void saveImportDataByBill() {
		List<StorageBill> heads = new ArrayList();
		Integer flag = null;
		if (jTabbedPane.getSelectedIndex() == 0) {
			flag = 0;
			heads = CheckCancelQuery.getInstance()
					.findStorageBillByProcessExes(flag);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			flag = 1;
			heads = CheckCancelQuery.getInstance()
					.findStorageBillByProcessExes(flag);
		} else if (jTabbedPane.getSelectedIndex() == 2) {
			flag = 2;
			heads = CheckCancelQuery.getInstance()
					.findStorageBillByProcessExes(flag);
		}

		if (heads != null && !heads.isEmpty()) {
			DownloadBatchTask task = new DownloadBatchTask(heads);
			task.start();
			//
			// refresh data
			//
			// pnCommonQueryPage.setInitState();

		}

	}

	/***
	 * 下载批量任务
	 * 
	 * @author ower
	 * 
	 */
	class DownloadBatchTask extends SwingWorkerTask {
		List<StorageBill> heads = new ArrayList<StorageBill>();

		public DownloadBatchTask(List<StorageBill> heads) {
			this.heads = heads;
		}

		/**
		 * 唯一值,及调用方法
		 * 
		 * @param uuid
		 */
		protected void doRun(String taskId) {
			//
			// 导入
			//
			final List<CollateBind> list = blsCheckCancelAction
					.saveImportDataByBill(
							new Request(CommonVars.getCurrUser()), taskId,
							heads);
			//
			// 同步
			//
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					getModel().addRows(list);
				}
			});
		}
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("入仓单--进口报关单", null, getJPanel2(), null);
			jTabbedPane.addTab("出仓单--出口报关单", null, getJPanel3(), null);
			jTabbedPane.addTab("出仓单--入仓单", null, getJPanel4(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (getModel() == null) {
								serch();
							}
							setState();
						}
					});
		}
		return jTabbedPane;
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
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane1(), BorderLayout.CENTER);
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
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browseData();
					}
				}
			});
			jTable1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting() || tableModel1 == null) {
								return;
							}
							setState();
						}
					});
		}
		return jTable1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browseData();
					}
				}
			});
			jTable2.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting() || tableModel2 == null) {
								return;
							}
							setState();
						}
					});
		}
		return jTable2;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getJPanel31());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jPanel31
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel31() {
		if (jPanel31 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("\u5230:");
			jLabel2 = new JLabel();
			jLabel2.setPreferredSize(new Dimension(78, 25));
			jLabel2.setText("\u5f00\u59cb\u65f6\u95f4\u4ece\uff1a");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jPanel31 = new JPanel();
			jPanel31.setLayout(flowLayout);
			jPanel31.add(jLabel2, null);
			jPanel31.add(getCbbStartDate(), null);
			jPanel31.add(jLabel11, null);
			jPanel31.add(getCbbEndDate(), null);
			jPanel31.add(getBtnFind(), null);
		}
		return jPanel31;
	}

	/**
	 * This method initializes cbbStartDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbStartDate() {
		if (cbbStartDate == null) {
			cbbStartDate = new JCalendarComboBox();
			cbbStartDate.setPreferredSize(new Dimension(85, 25));
			cbbStartDate.setDate(new Date());
		}
		return cbbStartDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(85, 25));
			cbbEndDate.setDate(new Date());
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnFind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton();
			btnFind.setText("刷新");
			btnFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					serch();
				}
			});
		}
		return btnFind;
	}

	/**
	 * 核销信息查询
	 */
	private void serch() {
		Integer type = 0;
		if (jTabbedPane.getSelectedIndex() == 0) {
			type = 0;
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			type = 1;
		} else {
			type = 2;
		}
		initTable(blsCheckCancelAction.findCollateBind(new Request(CommonVars
				.getCurrUser()), cbbStartDate.getDate(), CommonUtils
				.getEndDate(cbbEndDate.getDate()), type));
	}

	/**
	 * This method initializes btnCheck
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("核销导入");
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String ioFlag = "";
					String formType = "";
					String collateFormType = "";
					if (jTabbedPane.getSelectedIndex() == 0) {
						ioFlag = "I";
						formType = FormType.IM_MFT;
						collateFormType = FormType.IM_ENT;
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						ioFlag = "O";
						formType = FormType.EX_MFT;
						collateFormType = FormType.EX_ENT;
					} else {
						ioFlag = "O";
						formType = FormType.EX_MFT;
						collateFormType = FormType.IM_MFT;
					}
					new DgCheckQuery(ioFlag, formType, collateFormType)
							.setVisible(true);
					cbbStartDate.setDate(null);
					serch();
				}
			});
		}
		return btnCheck;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
