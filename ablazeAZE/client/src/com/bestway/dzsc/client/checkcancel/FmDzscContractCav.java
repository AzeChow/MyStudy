/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.checkcancel;

import java.awt.Component;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.DzscState;
import com.bestway.dzsc.checkcancel.action.DzscContractCavAction;
import com.bestway.dzsc.checkcancel.entity.DzscContractCav;
import com.bestway.dzsc.client.dzscmanage.DgDzscEmsPor;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class FmDzscContractCav extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable tblContract = null;

	private JScrollPane jScrollPane = null;

	private JButton btnShowContract = null;

	private JButton btnCavCal = null;

	private JButton btnCavConfirm = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private DzscAction contractAction = null;

	private DzscContractCavAction contractCavAction = null;

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnShowContract());
			jToolBar.add(getBtnCavCal());
			jToolBar.add(getBtnCavConfirm());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTblContract() {
		if (tblContract == null) {
			tblContract = new JTable();
		}
		return tblContract;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTblContract());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowContract() {
		if (btnShowContract == null) {
			btnShowContract = new JButton();
			btnShowContract.setText("显示合同内容");
			btnShowContract
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmDzscContractCav.this, "请选中要显示的合同！",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgDzscEmsPor dg = new DgDzscEmsPor();
							dg.setDataState(DataState.BROWSE);
							dg.setTableModelHead(tableModel);
							dg.setVisible(true);
						}
					});
		}
		return btnShowContract;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCavCal() {
		if (btnCavCal == null) {
			btnCavCal = new JButton();
			btnCavCal.setText("核销计算");
			btnCavCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmDzscContractCav.this,
								"请选择你要核销的合同", "提示", 0);
						return;
					}

					new ContractCavCalculate().execute();
				}
			});
		}
		return btnCavCal;
	}

	class ContractCavCalculate extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			String taskId = CommonStepProgress.getExeTaskId();
			CommonStepProgress.showStepProgressDialog(taskId);
			CommonStepProgress.setStepMessage("系统正核销计算，请稍后...");
			DzscEmsPorHead contract = (DzscEmsPorHead) tableModel
					.getCurrentRow();
			List list = contractCavAction.findContractCav(new Request(
					CommonVars.getCurrUser()), contract.getEmsNo(), false);
			if (list.size() < 1) {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				contractCavAction.cavContract(request, contract.getEmsNo());
				list = contractCavAction.findContractCav(new Request(CommonVars
						.getCurrUser()), contract.getEmsNo(), false);
				if (list.size() < 1) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmDzscContractCav.this,
							"核销计算失败", "提示", 0);
					return list;
				}
			}
			return list;
		}

		@Override
		protected void done() {
			List list = null;
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			if (list == null || list.isEmpty()) {
//				JOptionPane.showMessageDialog(FmDzscContractCav.this, "核销计算失败",
//						"提示", 0);
				return;
			}
			DzscEmsPorHead contract = (DzscEmsPorHead) tableModel
					.getCurrentRow();
			DgDzscContractCav dgContractCav = new DgDzscContractCav();
			dgContractCav.setDzscContractCavSelf((DzscContractCav) list.get(0));
			list = contractCavAction.findContractCav(new Request(CommonVars
					.getCurrUser()), contract.getEmsNo(), true);
			if (list.size() < 1) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmDzscContractCav.this, "核销计算失败",
						"提示", 0);
				return;
			}
			dgContractCav.setDzscContractCavCustoms((DzscContractCav) list
					.get(0));
			dgContractCav.setContract(contract);
			CommonStepProgress.closeStepProgressDialog();
			dgContractCav.setVisible(true);
			initTable();
			tableModel.updateRow(contract);
		}

	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCavConfirm() {
		if (btnCavConfirm == null) {
			btnCavConfirm = new JButton();
			btnCavConfirm.setText("核销确认");
			btnCavConfirm.setVisible(false);
			btnCavConfirm
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									FmDzscContractCav.this, "你确定确认核销吗？", "提示",
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
								if (tableModel.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmDzscContractCav.this,
											"请选择要核销确认的合同", "提示",
											JOptionPane.OK_OPTION);
									return;
								}
								DzscEmsPorHead contract = (DzscEmsPorHead) tableModel
										.getCurrentRow();
								contract
										.setDeclareState(DzscState.CHECK_CANCEL);
								contractAction.saveDzscEmsPorHead(new Request(
										CommonVars.getCurrUser(), true),
										contract);
								tableModel.deleteRow(contract);
							}
						}
					});
		}
		return btnCavConfirm;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmDzscContractCav.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This is the default constructor
	 */
	public FmDzscContractCav() {
		super();
		initialize();
		contractAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		initTable();
		contractCavAction = (DzscContractCavAction) CommonVars
				.getApplicationContext().getBean("dzscContractCavAction");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("合同核销管理");
		this.setSize(658, 398);
		this.setContentPane(getJContentPane());
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
	 * 初始化数据Table
	 */
	private void initTable() {
		//
		// 查询没有审核的合同记录
		//
		List list = this.contractAction.findDzscEmsPorHead(new Request(
				CommonVars.getCurrUser()), DzscState.EXECUTE);
		tableModel = new JTableListModel(tblContract, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号", "copTrNo", 100));
						// list.add(addColumn("报送关区",
						// "company.declareCustoms.name", 100));
						list.add(addColumn("合同状态", "declareState", 60));
						list.add(addColumn("进口合同号", "ieContractNo", 100));
						list.add(addColumn("出口合同号", "imContractNo", 100));
						list.add(addColumn("合同性质", "emsType", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 50));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));
						list.add(addColumn("开始有效期", "beginDate", 100));
						list.add(addColumn("结束有效期", "endDate", 100));
						list.add(addColumn("进口口岸", "iePort1.name", 100));
						list.add(addColumn("外商公司", "outTradeCo", 100));
						list.add(addColumn("征免性质", "levyKind.name", 50));
						list.add(addColumn("保税方式", "payMode.name", 50));
						list.add(addColumn("加工种类", "machiningType.name", 50));
						list.add(addColumn("备案批准日期", "newApprDate", 100));
						list.add(addColumn("变更批准日期", "changeApprDate", 100));
						list.add(addColumn("批准证号", "emsApprNo", 100));
						list.add(addColumn("审批标志", "checkMark", 100));
						list.add(addColumn("执行标志", "exeMark", 100));
						list.add(addColumn("贸易方式", "trade.name", 100));
						list.add(addColumn("有效期限", "availabilityDate", 100));
						list.add(addColumn("贸易国别", "tradeCountry.name", 100));
						list.add(addColumn("延期期限", "deferDate", 100));
						list.add(addColumn("企业地址", "enterpriseAddress", 100));
						list.add(addColumn("核销日期", "destroyDate", 100));
						list.add(addColumn("联系人", "linkMan", 100));
						list.add(addColumn("联系电话", "contactTel", 100));
						list.add(addColumn("协议书号", "agreementNo", 100));					
						list.add(addColumn("进口总金额", "imgAmount", 100));
						list.add(addColumn("出口总金额", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("监管费用", "wardshipFee", 100));
						list.add(addColumn("监管费率", "wardshipRate", 100));
						list.add(addColumn("成交方式", "transac.name", 100));
						list.add(addColumn("进口口岸2", "iePort2.name", 100));
						list.add(addColumn("进口口岸3", "iePort3.name", 100));
						list.add(addColumn("进口口岸4", "iePort4.name", 100));
						list.add(addColumn("进口口岸5", "iePort5.name", 100));
						list.add(addColumn("审批人", "approver", 100));
						list.add(addColumn("审批日期", "approveDate", 100));
						list.add(addColumn("许可证号", "permitNo", 100));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});

		tblContract.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
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

						if (value.equals(DzscState.APPLY)) {
							returnValue = "正在申请";
						} else if (value.equals(DzscState.EXECUTE)) {
							returnValue = "正在执行";
						} else if (value.equals(DzscState.CHANGE)) {
							returnValue = "正在变更";
						}
						return returnValue;
					}
				});

		tblContract.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
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
						if (value.equals(DzscEmsType.COME_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易来料手册";
						} else if (value
								.equals(DzscEmsType.IMPORT_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易进料手册";
						}
						if (value.equals(DzscEmsType.MACHINE_PROCESS_EMS_BILL)) {
							returnValue = "加工贸易设备手册";
						}
						return returnValue;
					}
				});
		tblContract
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

	}

} // @jve:decl-index=0:visual-constraint="10,10"
