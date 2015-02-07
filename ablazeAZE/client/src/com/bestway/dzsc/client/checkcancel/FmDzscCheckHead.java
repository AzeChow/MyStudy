/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.message.action.MessageAction;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.CheckRange;
import com.bestway.dzsc.checkcancel.entity.DzscCheckHead;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmDzscCheckHead extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnApply = null;

	private JButton jButton7 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private DzscCheckHead dzscCheckHead = null; // @jve:decl-index=0:

	private DzscContractCavAction dzscContractCavAction = null; // @jve:decl-index=0:

	private DzscAction dzscAction = null;

	private MaterialApplyAction materialApplyAction = null;

	private JTableListModel tableModel = null;

	// private EmsEdiTrHead emsEdiTrHead = null;

	private boolean isChange = false;

	private JButton btnProcess = null;

	private MessageAction messageAction = null;

	/**
	 * This is the default constructor
	 */
	public FmDzscCheckHead() {
		super();
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
		this.setSize(472, 315);
		this.setTitle("中期核查-表头");
		this.setContentPane(getJContentPane());

		List dataSource = null;
		dataSource = dzscContractCavAction.findDzscCheckHead(new Request(
				CommonVars.getCurrUser()));

		initTable(dataSource);
		setState();

	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 120));
						list.add(addColumn("申报状态", "declareState", 120));
						list.add(addColumn("本期起始日期", "beginDate", 80));
						list.add(addColumn("主管海关", "masterCustoms.name", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(6).setCellRenderer(
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
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscState.ORIGINAL)) {
							returnValue = "初始状态";

						} else if (value.equals(DzscState.APPLY))
							returnValue = "正在申请";
						else if (value.equals(DzscState.EXECUTE))
							returnValue = "正在执行";
						else if (value.equals(DzscState.CHANGE))
							returnValue = "正在变更";
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
			jContentPane.setLayout(new BorderLayout());
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
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getJButton7());
		}
		return jToolBar;
	}

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

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("新增");

			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					DzscEmsPorHead dzscEmsPorHead = DzscCheckCancelQuery
							.getInstance().findDzscEmsPorHeadExcu();
					DzscCheckHead dzscCheckHead = new DzscCheckHead();
					fillCheckHead(dzscCheckHead, dzscEmsPorHead);
					dzscCheckHead.setColRange(CheckRange.EMS);
					dzscCheckHead.setCopEmsNo(dzscEmsPorHead.getCopTrNo());
					dzscCheckHead = dzscContractCavAction.saveDzscCheckHead(
							new Request(CommonVars.getCurrUser()),
							dzscCheckHead);
					tableModel.addRow(dzscCheckHead);
					setState();
					// }
				}
			});

		}
		return jButton;
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

	private void edit() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmDzscCheckHead.this, "请选择你将要修改的记录",
					"提示！", 0);
			return;
		}
		DgDzscCheck dgCheck = new DgDzscCheck();
		dgCheck.setTableModel(tableModel);
		dgCheck.setDataState(DataState.EDIT);
		dgCheck.setVisible(true);
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

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCheckHead.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					DzscCheckHead checkHead = (DzscCheckHead) tableModel
							.getCurrentRow();

					if (JOptionPane.showConfirmDialog(FmDzscCheckHead.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
						// dzscContractCavAction.deleteAllCheckImgExg(new
						// Request(
						// CommonVars.getCurrUser()),checkHead);
						dzscContractCavAction.deleteDzscCheckHead(new Request(
								CommonVars.getCurrUser()), checkHead);
						tableModel.deleteRow(checkHead);
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
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCheckHead.this,
								"请选择你将要申报的记录", "提示！", 0);
						return;
					}
					new ApplyThread().start();
					// dzscCheckHead = (DzscCheckHead)
					// tableModel.getCurrentRow();
					// try {
					// String fileName =
					// FmDzscCheckHead.this.dzscContractCavAction
					// .applyDzscCheck(new Request(CommonVars
					// .getCurrUser()), dzscCheckHead);
					//
					// JOptionPane.showMessageDialog(FmDzscCheckHead.this,
					// "申报成功，文件为" + fileName, "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					//
					// } catch (Exception ex) {
					// JOptionPane.showMessageDialog(FmDzscCheckHead.this,
					// "申报失败 " + ex.getMessage(), "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }
					// dzscCheckHead.setDeclareState(DzscState.APPLY);
					// tableModel.updateRow(dzscCheckHead);
					// setState();
				}
			});

		}
		return btnApply;
	}

	class ApplyThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				dzscCheckHead = (DzscCheckHead) tableModel.getCurrentRow();
				try {
					DeclareFileInfo fileInfo = dzscContractCavAction
							.applyDzscCheck(new Request(CommonVars
									.getCurrUser()), dzscCheckHead);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscCheckHead.this,
							fileInfo.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscCheckHead.this,
							"系统申报失败 " + ex.getMessage(), "确认", 1);
				}
				dzscCheckHead.setDeclareState(DzscState.APPLY);
				tableModel.updateRow(dzscCheckHead);
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	private void setState() {
		if (tableModel.getCurrentRow() == null) {
			return;
		}
		DzscCheckHead data = (DzscCheckHead) tableModel.getCurrentRow();
		String state = data.getDeclareState();
		if (state == null || state.equals("")) {
			return;
		}
		this.btnEdit.setEnabled(state.equals(DzscState.ORIGINAL)
				|| state.equals(DzscState.CHANGE));
		this.btnDelete.setEnabled(state.equals(DzscState.ORIGINAL)
				|| state.equals(DzscState.CHANGE));
		this.btnApply.setEnabled(state.equals(DzscState.ORIGINAL)
				|| state.equals(DzscState.CHANGE));
		this.btnProcess.setEnabled(state.equals(DzscState.APPLY));
	}

	/**
	 * 
	 * This method initializes jButton7
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton s
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setText("关闭");
			jButton7.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					FmDzscCheckHead.this.dispose();

				}
			});

		}
		return jButton7;
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
					if (e.getClickCount() == 2) {
						DgDzscCheck dgCheck = new DgDzscCheck();
						dgCheck.setTableModel(tableModel);
						dgCheck.setVisible(true);
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
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscCheckHead.this,
								"请选择数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					DzscCheckHead data = (DzscCheckHead) tableModel
							.getCurrentRow();
					List lsReturnFile = DzscCommon.getInstance()
							.showDzscReceiptFile(DzscBusinessType.CHECK,
									data.getCopEmsNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					try {
						String result = dzscContractCavAction
								.processDzscCheckHead(new Request(CommonVars
										.getCurrUser()), data, lsReturnFile);
						JOptionPane.showMessageDialog(FmDzscCheckHead.this,
								"处理回执成功！\n" + result, "提示！",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmDzscCheckHead.this,
								"处理回执失败！" + ex.getMessage(), "提示！",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					data.setDeclareState(DzscState.EXECUTE);
					tableModel.updateRow(data);
					setState();
					JOptionPane.showMessageDialog(FmDzscCheckHead.this,
							"处理回执成功！", "提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnProcess;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
