package com.bestway.bcs.client.dictpor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.client.message.BcsMessageHelper;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.dictpor.entity.TempBcsDictPorCheckup;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmBcsDictPor extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnApply = null;

	private JButton btnProcess = null;

	private JButton btnCopy = null;

	private JButton btnClose = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private JButton btnChange = null;

	private JButton btnBrowse = null;

	private ContractAction contractAction = null;

	private BcsParameterSet parameterSet = null; // @jve:decl-index=0:

	private JButton btnBcsDictPorCheckup = null;

	private JButton btnChangeDictPorEmsNo = null;

	private JPopupMenu pmImport = null; // @jve:decl-index=0:visual-constraint="934,51"

	private JMenuItem miImportQPExcel = null;

	private JButton btnImport = null;

	private JButton btnRefresh = null;

	private JMenuItem miImportFromQP = null;

	private JButton btnRecord = null;

	private String manageType = "0"; // @jve:decl-index=0:
	private JButton btnUpdateSeqNum;

	/**
	 * This method initializes
	 * 
	 */
	public FmBcsDictPor() {
		super();
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser()));
		if (parameterSet != null && parameterSet.getManageType() != null) {
			this.manageType = parameterSet.getManageType();
		}
		initialize();
		initTable();
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(963, 486));
		this.setTitle("备案资料库备案");
		this.setContentPane(getJContentPane());
		parameterSet = contractAction.findBcsParameterSet(new Request(
				CommonVars.getCurrUser()));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setVgap(1);
			fl.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(352, 34));
			jToolBar.setFloatable(false);
			jToolBar.setLayout(fl);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnRecord());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnBcsDictPorCheckup());
			jToolBar.add(getBtnChangeDictPorEmsNo());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnUpdateSeqNum());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnClose());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {

		if (btnAdd == null) {

			btnAdd = new JButton();

			btnAdd.setText("新增");

			btnAdd.setPreferredSize(new Dimension(55, 30));

			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					Boolean flag = parameterSet.getPutOnRecords() == null ? false
							: parameterSet.getPutOnRecords();

					if (!flag) {

						if (tableModel.getList().size() > 0) {

							JOptionPane.showMessageDialog(FmBcsDictPor.this,
									"您在参数设置中只允许一本备案资料库备案!", "提示",
									JOptionPane.INFORMATION_MESSAGE);

							return;
						}

					}

					BcsDictPorHead head = bcsDictPorAction
							.addBcsDictPorHead(new Request(CommonVars
									.getCurrUser()));

					tableModel.addRow(head);
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(55, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要修改的备案资料库资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgBcsDictPor dg = new DgBcsDictPor();
					dg.setTableModelHead(tableModel);
					dg.setHeadDataState(DataState.EDIT);
					dg.setVisible(true);
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(55, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要删除的备案资料库表头", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					if (JOptionPane.showConfirmDialog(FmBcsDictPor.this,
							"请选择要删除的备案资料库表头", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					BcsDictPorHead head = (BcsDictPorHead) tableModel
							.getCurrentRow();
					bcsDictPorAction.deleteBcsDictPorHead(new Request(
							CommonVars.getCurrUser()), head);
					tableModel.deleteRow(head);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {

			btnApply = new JButton();

			btnApply.setText("报关申报");

			btnApply.setPreferredSize(new Dimension(60, 30));

			btnApply.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {

						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要申报的合同", "提示",
								JOptionPane.INFORMATION_MESSAGE);

						return;
					}

					BcsDictPorHead head = (BcsDictPorHead) tableModel
							.getCurrentRow();

					head = bcsDictPorAction.findBcsDictPorHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());

					tableModel.updateRow(head);
					// if (DzscClientLogic.isEmpty(head,
					// FmDzscEmsPorWjHead.this)) {
					// return;
					// }
					new ApplyThread().start();
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

				BcsDictPorHead head = (BcsDictPorHead) tableModel
						.getCurrentRow();

				try {

					DeclareFileInfo fileInfo = bcsDictPorAction
							.applyBcsDictPor(
									new Request(CommonVars.getCurrUser()), head);

					head = bcsDictPorAction.findBcsDictPorHeadById(new Request(
							CommonVars.getCurrUser()), head.getId());

					tableModel.updateRow(head);

					setState();

					CommonStepProgress.closeStepProgressDialog();

					JOptionPane.showMessageDialog(FmBcsDictPor.this,
							fileInfo.getFileInfoSpec(), "提示", 1);

				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmBcsDictPor.this, "系统申报失败 "
							+ ex.getMessage(), "确认", 1);
				}
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton4
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
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要处理回执的备案资料库", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					BcsDictPorHead head = (BcsDictPorHead) tableModel
							.getCurrentRow();
					List lsReturnFile = BcsMessageHelper.getInstance()
							.showBcsReceiptFile(DzscBusinessType.EMS_POR_WJ,
									head.getCopEmsNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					BcsDictPorHead headExing = null;
					if (head.getDictPorEmsNo() != null
							&& !"".equals(head.getDictPorEmsNo().trim())) {
						List list = bcsDictPorAction.findBcsDictPorHead(
								new Request(CommonVars.getCurrUser()),
								head.getDictPorEmsNo(),
								DeclareState.PROCESS_EXE);
						if (list != null && list.size() > 0) { // 已经存在正在执行
							headExing = (BcsDictPorHead) list.get(0);
						}
					}
					try {
						String result = bcsDictPorAction.processBcsDictPor(
								new Request(CommonVars.getCurrUser()), head,
								headExing, lsReturnFile);
						head = bcsDictPorAction.findBcsDictPorHeadById(
								new Request(CommonVars.getCurrUser()),
								head.getId());
						tableModel.updateRow(head);
						if (headExing != null) {
							BcsDictPorHead headExingTemp = bcsDictPorAction
									.findBcsDictPorHeadById(new Request(
											CommonVars.getCurrUser()),
											headExing.getId());
							if (headExingTemp == null) {
								tableModel.deleteRow(headExing);
							}
						}
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"回执处理成功！\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"回执处理失败" + ex.getMessage(), "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					setState();
				}
			});
		}
		return btnProcess;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.setPreferredSize(new Dimension(55, 30));
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要转抄的备案资料库", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					BcsDictPorHead head = bcsDictPorAction.copyBcsDictPor(
							new Request(CommonVars.getCurrUser()),
							(BcsDictPorHead) tableModel.getCurrentRow());
					tableModel.addRow(head);
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(55, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBcsDictPor.this.doDefaultCloseAction();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
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
							JTableListModel tableModel = (JTableListModel) jTable
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browse();
					}
				}
			});

		}
		return jTable;
	}

	/**
	 * 浏览
	 * 
	 */
	private void browse() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要显示的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgBcsDictPor dg = new DgBcsDictPor();
		dg.setTableModelHead(tableModel);
		// dg.setHeadDataState(DataState.EDIT);
		dg.setHeadDataState(DataState.BROWSE);
		dg.setVisible(true);
	}

	/**
	 * 初始化数据Table
	 */
	void initTable() {
		List list = bcsDictPorAction.findBcsDictPorHead(new Request(CommonVars
				.getCurrUser()));
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 100));
						list.add(addColumn("报送海关", "declareCustoms.name", 100));
						list.add(addColumn("备案资料库编号", "dictPorEmsNo", 100));
						list.add(addColumn("备案状态", "declareState", 60));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("经营单位编号", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));
						list.add(addColumn("加工单位编号", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 100));
						list.add(addColumn("年加工生产能力", "productRatio", 100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("修改标志", "modifyMark", 100));
						list.add(addColumn("备注", "memo", 100));
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
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		jTable.getColumnModel().getColumn(12)
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		btnAdd.setEnabled(true);
		BcsDictPorHead c = (BcsDictPorHead) tableModel.getCurrentRow();
		if (c == null) {
			return;
		}

		Boolean flag = parameterSet.getPutOnRecords() == null ? false
				: parameterSet.getPutOnRecords();
		String declareState = c.getDeclareState();
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		btnEdit.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		// btnCopy.setEnabled(isProcessExe);
		btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(declareState));
		btnApply.setEnabled(DeclareState.APPLY_POR.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
		btnCopy.setEnabled(flag);
		// if(!flag||count>0){
		// btnAdd.setEnabled(false);
		// }
		// 管理类别 0为既用于关务管理也向海关申报数据;1为仅用于关务管理不向海关申报数据
		if (parameterSet != null && parameterSet.getManageType() != null) {
			if (parameterSet.getManageType().equals("1")) {//
				btnRecord
						.setEnabled(DeclareState.APPLY_POR.equals(c
								.getDeclareState())
								|| DeclareState.CHANGING_EXE.equals(c
										.getDeclareState()));
			} else {
				btnRecord.setEnabled(false);
			}
		}
		btnChange.setEnabled(DeclareState.PROCESS_EXE.equals(declareState));
		this.btnChangeDictPorEmsNo.setEnabled(DeclareState.PROCESS_EXE
				.equals(declareState)
				|| DeclareState.CHANGING_EXE.equals(declareState));
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.setPreferredSize(new Dimension(55, 30));
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要变更的备案资料库", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
					BcsDictPorHead head = bcsDictPorAction.changeBcsDictPor(
							new Request(CommonVars.getCurrUser()),
							(BcsDictPorHead) tableModel.getCurrentRow());
					tableModel.addRow(head);
				}
			});
		}
		return btnChange;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.setPreferredSize(new Dimension(55, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要察看的备案资料库资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					DgBcsDictPor dg = new DgBcsDictPor();
					dg.setTableModelHead(tableModel);
					dg.setHeadDataState(DataState.BROWSE);
					dg.setVisible(true);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes jButton61
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBcsDictPorCheckup() {
		if (btnBcsDictPorCheckup == null) {
			btnBcsDictPorCheckup = new JButton();
			btnBcsDictPorCheckup.setBounds(new Rectangle(318, 4, 58, 21));
			btnBcsDictPorCheckup.setText("检查");
			btnBcsDictPorCheckup.setPreferredSize(new Dimension(55, 30));
			btnBcsDictPorCheckup.setForeground(Color.blue);
			btnBcsDictPorCheckup
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModel.getCurrentRows();
							if (list == null || list.isEmpty()) {
								JOptionPane
										.showMessageDialog(FmBcsDictPor.this,
												"请选择备案资料库！", "提示", 0);
								return;
							}
							List arrayList = bcsDictPorAction
									.bcsDictPorCheckup(
											new Request(CommonVars
													.getCurrUser()), list);
							DgShowBcsDictPorCheckup dgShowCheckup = new DgShowBcsDictPorCheckup();
							dgShowCheckup.setList(arrayList);
							dgShowCheckup.setVisible(true);
						}
					});
		}
		return btnBcsDictPorCheckup;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChangeDictPorEmsNo() {
		if (btnChangeDictPorEmsNo == null) {
			btnChangeDictPorEmsNo = new JButton();
			btnChangeDictPorEmsNo.setText("变更备案资料库号码");
			btnChangeDictPorEmsNo.setPreferredSize(new Dimension(120, 30));
			btnChangeDictPorEmsNo
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmBcsDictPor.this, "请先选择一本备案资料库!",
										"提示", JOptionPane.ERROR_MESSAGE);
								return;
							}
							BcsDictPorHead head = (BcsDictPorHead) tableModel
									.getCurrentRow();
							if (head.getDictPorEmsNo() == null
									|| "".equals(head.getDictPorEmsNo().trim())) {
								JOptionPane.showMessageDialog(
										FmBcsDictPor.this, "备案资料库号为空,无法修改！",
										"提示", JOptionPane.ERROR_MESSAGE);
								return;
							}
							String oldDictPorEmsNo = head.getDictPorEmsNo();
							String dictPorEmsNo = JOptionPane
									.showInputDialog(FmBcsDictPor.this,
											"请输入新备案资料库号码", "新备案资料库号码",
											JOptionPane.INFORMATION_MESSAGE);
							if (dictPorEmsNo == null
									|| "".equals(dictPorEmsNo.trim())) {
								return;
							}
							if (oldDictPorEmsNo.equals(dictPorEmsNo)) {
								JOptionPane.showMessageDialog(
										FmBcsDictPor.this,
										"新备案资料库号码和旧备案资料库号码一样！", "提示",
										JOptionPane.ERROR_MESSAGE);
								return;
							}
							// 检查新备案资料库是否存在
							if (CheckdictPorEmsNo(dictPorEmsNo)) {
								JOptionPane.showMessageDialog(
										FmBcsDictPor.this, "备案资料库号码已经存在！请重输入",
										"提示", JOptionPane.ERROR_MESSAGE);
								return;
							}
							List list = bcsDictPorAction.changeDictPorEmsNo(
									new Request(CommonVars.getCurrUser()),
									oldDictPorEmsNo, dictPorEmsNo);
							tableModel.updateRows(list);
						}
					});
		}
		return btnChangeDictPorEmsNo;
	}

	private boolean CheckdictPorEmsNo(String dictPorEmsNo) {
		List list = bcsDictPorAction.checkDictPorEmsNo(
				new Request(CommonVars.getCurrUser()), dictPorEmsNo);
		if (list.size() > 0 && list != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method initializes pmImport
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmImport() {
		if (pmImport == null) {
			pmImport = new JPopupMenu();
			pmImport.add(getMiImportQPExcel());
			pmImport.add(getMiImportFromQP());
		}
		return pmImport;
	}

	/**
	 * This method initializes miImportQPExcel
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportQPExcel() {
		if (miImportQPExcel == null) {
			miImportQPExcel = new JMenuItem();
			miImportQPExcel.setText("从QP导出文件中导入备案库资料");
			miImportQPExcel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImportDictPorFromQPExcel dg = new DgImportDictPorFromQPExcel(
									FmBcsDictPor.this);
							dg.setVisible(true);
							if (dg.isOk()) {
								BcsDictPorHead dictPorHead = dg
										.getBcsDictPorHead();
								if (dictPorHead != null) {
									tableModel.addRow(dictPorHead);
								}
							}
						}
					});
		}
		return miImportQPExcel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.setPreferredSize(new Dimension(55, 30));
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					bcsDictPorAction.importBcsDictPor(new Request(CommonVars
							.getCurrUser()));
					Component comp = (Component) e.getSource();
					getPmImport().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setPreferredSize(new Dimension(55, 30));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
					setState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportFromQP() {
		if (miImportFromQP == null) {
			miImportFromQP = new JMenuItem();
			miImportFromQP.setText("直接导入备案库资料");
			miImportFromQP
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List lsExistEmsNo = new ArrayList();
							List listDictPorHead = tableModel.getList();
							for (int i = 0; i < listDictPorHead.size(); i++) {
								BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) listDictPorHead
										.get(i);
								if (DeclareState.PROCESS_EXE
										.equals(bcsDictPorHead
												.getDeclareState())) {
									lsExistEmsNo.add(bcsDictPorHead
											.getDictPorEmsNo());
								}
							}
							DgBcsQPDictPorHead dg = new DgBcsQPDictPorHead();
							dg.setLsExistEmsNo(lsExistEmsNo);
							dg.setVisible(true);
							List list = dg.getLsResult();
							boolean isOverWrite = dg.isOverWrite();
							if (list != null && !list.isEmpty()) {
								contractAction.importBcsDictPorHeadFromQP(
										new Request(CommonVars.getCurrUser(),
												true), list, isOverWrite);
								initTable();
								setState();
							}
						}
					});
		}
		return miImportFromQP;
	}

	/**
	 * This method initializes btnRecord
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecord() {
		if (btnRecord == null) {
			btnRecord = new JButton();
			btnRecord.setText("备案");
			btnRecord.setPreferredSize(new Dimension(55, 30));
			btnRecord.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					BcsParameterSet parameterSet = contractAction
							.findBcsParameterSet(new Request(CommonVars
									.getCurrUser(), true));

					if (parameterSet != null
							&& parameterSet.getManageType() != null) {

						if (!parameterSet.getManageType().equals(manageType)) {
							JOptionPane.showMessageDialog(FmBcsDictPor.this,
									"您已经对参数设置进行了修改,关闭后重新打开才能生效！", "警告！",
									JOptionPane.WARNING_MESSAGE);

							return;
						}

					}

					BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) tableModel
							.getCurrentRow();
					if (bcsDictPorHead == null) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"请选择要修改的备案资料库资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						List arrayList = new ArrayList();
						arrayList = checkBcsDicPorHead(bcsDictPorHead);
						if (arrayList.size() > 0) {
							String errorInfo = ((TempBcsDictPorCheckup) arrayList
									.get(0)).getHeadErr();
							if (!errorInfo.equals("")) {
								JOptionPane.showMessageDialog(
										FmBcsDictPor.this, errorInfo, "提示！",
										JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
					}
					String dictPorEmsNo = bcsDictPorHead.getDictPorEmsNo();
					if (dictPorEmsNo.equals("")) {
						JOptionPane.showConfirmDialog(FmBcsDictPor.this,
								"备案资料库不允许为空，请输入后再备案", "提示",
								JOptionPane.YES_NO_OPTION);
						return;
					}
					Integer BcsDictPorImgCount = contractAction
							.findBcsDictPorImgCount(new Request(),
									bcsDictPorHead.getId());
					Integer BcsDictPorExgCount = contractAction
							.findBcsDictPorExgCount(new Request(),
									bcsDictPorHead.getId());
					if (0 == BcsDictPorImgCount || 0 == BcsDictPorExgCount) {
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								"料件表或成品表中没有数据，不能备案！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int option = JOptionPane.showConfirmDialog(
							FmBcsDictPor.this, "你确认备案库编号：" + dictPorEmsNo
									+ "，海关已经备案完毕吗？", "提示",
							JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						System.out.println(option);
						new recordData(bcsDictPorHead).execute();
					}
				}
			});
		}
		return btnRecord;
	}

	// 备案
	class recordData extends SwingWorker {
		BcsDictPorHead head;

		public recordData(BcsDictPorHead head) {
			this.head = head;
		}

		@Override
		protected Object doInBackground() throws Exception {
			// TODO Auto-generated method stub
			CommonProgress.showProgressDialog(FmBcsDictPor.this);
			CommonProgress.setMessage("系统正在执行查询，请稍后...");
			bcsDictPorAction.putOnRecordBcsDictPor(
					new Request(CommonVars.getCurrUser()), head);
			return bcsDictPorAction.findBcsDictPorHead(new Request(CommonVars
					.getCurrUser()));
		}

		protected void done() {// 更新UI
			List list = null;
			try {
				list = (List) this.get();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(FmBcsDictPor.this,
						"获得备案资料库备案数据失败", "提示", JOptionPane.CANCEL_OPTION);
			}
			if (list == null) {
				list = new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			tableModel.setList(list);
		}
	}

	/**
	 * 检查备案资料库基本资料
	 * 
	 * @param head
	 * @return
	 */
	public List checkBcsDicPorHead(BcsDictPorHead head) {
		List arrayList = new ArrayList();
		StringBuffer headErr = new StringBuffer("");
		// 表头检查
		if (head.getCopEmsNo() == null || head.getCopEmsNo().equals(""))// 企业内部编号
			headErr.append("企业内部编号不可为空;");
		if (head.getDeclareCustoms() == null)// 主管海关
			headErr.append("主管海关不可为空;");
		if (head.getMachCode() == null || head.getMachCode().equals(""))// 加工单位编号
			headErr.append("加工单位编号不可为空;");
		if (head.getMachName() == null || head.getMachName().equals(""))// 加工单位名称
			headErr.append("加工单位名称不可为空;");
		if (head.getProductRatio() == null || head.getProductRatio() == 0)// 生产能力
			headErr.append("生产能力不可为空;");
		if (head.getLevyKind() == null)// 征免性质
			headErr.append("征免性质不可为空;");
		if (head.getManageObject() == null || head.getManageObject().equals(""))// 管理对象
			headErr.append("管理对象不可为空;");
		if (head.getTrade() == null)// 贸易方式
			headErr.append("贸易方式不可为空;");
		if (head.getReceiveArea() == null)// 地区代码
			headErr.append("地区代码不可为空;");
		if (head.getCurr() == null)// 币制
			headErr.append("币制不可为空;");
		if (head.getMachiningType() == null)// 加工种类
			headErr.append("加工种类不可为空;");
		if (!headErr.toString().equals("")) {
			TempBcsDictPorCheckup tempBcsDictPorCheckup = new TempBcsDictPorCheckup();
			tempBcsDictPorCheckup.setCopEmsNo(head.getCopEmsNo());
			tempBcsDictPorCheckup.setHeadErr(headErr.toString());
			arrayList.add(tempBcsDictPorCheckup);
		}

		return arrayList;

	}

	private JButton getBtnUpdateSeqNum() {
		if (btnUpdateSeqNum == null) {
			btnUpdateSeqNum = new JButton("同步归并序号");
			btnUpdateSeqNum
					.setToolTipText("如（编码+名称+规格+单位）的商品与【报关商品资料】模块一致时，将归并序号同步到备案库表中");
			btnUpdateSeqNum.setPreferredSize(new Dimension(90, 30));
			btnUpdateSeqNum.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.YES_OPTION == JOptionPane.showOptionDialog(
							FmBcsDictPor.this, "确认将【备案表】归并序号与【报关商品资料】同步吗？",
							"提示", JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null,
							new Object[] { "是", "否" }, "否")) {

						List<BcsDictPorHead> heads = tableModel
								.getCurrentRows();

						if (heads == null || heads.isEmpty()
								|| heads.size() > 1) {
							throw new RuntimeException("有且只能选择一行");
						}

						Object[] returnValues = bcsDictPorAction.updateSeqNum(
								new Request(CommonVars.getCurrUser(), true),
								heads.get(0));
						JOptionPane.showMessageDialog(FmBcsDictPor.this,
								(String) returnValues[0]);
						List<BcsDictPorImg> imgs = (List<BcsDictPorImg>) returnValues[1];
						List<BcsDictPorExg> exgs = (List<BcsDictPorExg>) returnValues[2];
						if ((imgs != null && imgs.size() > 0)
								|| (exgs != null && exgs.size() > 0)) {
							DgBcsDictPorImgExgSeqNumEdit dg = new DgBcsDictPorImgExgSeqNumEdit();
							dg.setImgs(imgs);
							dg.setExgs(exgs);
							dg.setVisible(true);
						}
					}
				}
			});
		}
		return btnUpdateSeqNum;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
