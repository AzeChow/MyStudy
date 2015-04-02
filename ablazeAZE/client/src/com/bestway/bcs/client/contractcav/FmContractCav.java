/*
 * Created on 2005-4-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contractcav;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.client.contract.DgContract;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcs.contractcav.entity.ContractCav;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmContractCav extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JTable tblContract = null;

	private JScrollPane jScrollPane = null;

	private JButton btnShowContract = null;

	private JButton btnCavCal = null;

	private JButton btnCavConfirm = null;

	private JButton btnExit = null;

	private JTableListModel tableModel = null;

	private ContractAction contractAction = null;

	private ContractCavAction contractCavAction = null;

	private JButton btnSimulation = null;

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnShowContract());
			jToolBar.add(getBtnCavCal());
			jToolBar.add(getBtnCavConfirm());
			jToolBar.add(getBtnSimulation());
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
			tblContract.setRowHeight(25);
			tblContract.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tblContract
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								if (tableModel.getCurrentRow() != null) {
									Contract contract = (Contract) tableModel
											.getCurrentRow();
									btnCavConfirm.setVisible(contract
											.getIsContractEms() == null
											|| !contract.getIsContractEms());
									btnSimulation.setVisible(contract
											.getIsContractEms() == null
											|| contract.getIsContractEms());
								}
							} catch (Exception cx) {

							}
						}
					});
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
										FmContractCav.this, "请选择你要显示的资料", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgContract dg = new DgContract();
							dg.setTableModelContract(tableModel);
							dg.setDataState(DataState.BROWSE);
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
			btnCavCal.setPreferredSize(new Dimension(65, 30));
			btnCavCal.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmContractCav.this,
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

			Contract contract = (Contract) tableModel.getCurrentRow();

			Request request = new Request(CommonVars.getCurrUser());

			request.setTaskId(taskId);

			// 核算 <自用与海关用>
			contractCavAction.cavContract(request, contract.getEmsNo());

			// 查询 自用 核销表
			ContractCav contractCavSelf = contractCavAction.findContractCav(
					request, contract.getEmsNo(), false);

			if (contractCavSelf == null) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmContractCav.this, "核销计算失败",
						"提示", 0);
				return null;
			}

			return contractCavSelf;
		}

		@Override
		protected void done() {
			ContractCav contractCavSelf;
			try {
				contractCavSelf = (ContractCav) this.get();
			} catch (InterruptedException e) {

				e.printStackTrace();
				return;
			} catch (ExecutionException e) {

				e.printStackTrace();
				return;
			}
			if (contractCavSelf == null) {
				return;
			}

			Contract contract = (Contract) tableModel.getCurrentRow();

			String emsNo = (String) tableModel.getValueAt(
					tableModel.getCurrentRow(), 7);

			Request request = new Request(CommonVars.getCurrUser());

			// 进口金额
			Double sumExgTotalPrice = contractAction
					.findSumContractImgOrExgTotalPrices(request,
							contract.getId(), ContractExg.class.getName());

			// 出口金额
			Double sumImgTotalPrice = contractAction
					.findSumContractImgOrExgTotalPrices(request,
							contract.getId(), ContractImg.class.getName());

			DgContractCav dgContractCav = new DgContractCav();

			dgContractCav.setSumContractExgTotalPrice(sumExgTotalPrice);

			dgContractCav.setSumContractImgTotalPrice(sumImgTotalPrice);

			dgContractCav.setContractCavSelf(contractCavSelf);

			dgContractCav.setEmsNo(emsNo);

			// 查询 海关用 核销表
			ContractCav contractCavCustoms = contractCavAction.findContractCav(
					request, contract.getEmsNo(), true);

			if (contractCavCustoms == null) {

				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmContractCav.this, "核销计算失败",
						"提示", 0);
				return;
			}

			dgContractCav.setContractCavCustoms(contractCavCustoms);
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
			btnCavConfirm.setPreferredSize(new Dimension(65, 30));
			btnCavConfirm.setPreferredSize(new Dimension(65, 30));
			btnCavConfirm
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									FmContractCav.this, "你确定确认核销吗？", "提示",
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
								if (tableModel.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmContractCav.this, "请选择要核销确认的合同",
											"提示", JOptionPane.OK_OPTION);
									return;
								}
								Contract contract = (Contract) tableModel
										.getCurrentRow();
								contract.setDeclareState("5");
								contract.setIsCancel(new Boolean(true));
								contractAction.saveContract(new Request(
										CommonVars.getCurrUser()), contract);
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
			btnExit.setPreferredSize(new Dimension(65, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmContractCav.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This is the default constructor
	 */
	public FmContractCav() {
		super();
		initialize();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initTable();
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
		contractCavAction.controlContractCav(new Request(CommonVars
				.getCurrUser()));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("合同核销管理");
		this.setSize(752, 252);
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
		List list = this.contractAction.findContractByProcessExe(new Request(
				CommonVars.getCurrUser()));
		tableModel = new JTableListModel(tblContract, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("报送关区", "declareCustoms.name", 100));
						list.add(addColumn("合同状态", "declareState", 60));
						list.add(addColumn("进口合同号", "impContractNo", 100));
						list.add(addColumn("出口合同号", "expContractNo", 100));
						list.add(addColumn("合同性质", "emsType", 50));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("批文帐册号", "sancEmsNo", 50));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 50));
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

		tblContract.getColumnModel().getColumn(3)
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
								.toString().trim());
					}
				});

		tblContract.getColumnModel().getColumn(6)
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
						return ContractKind.getContractKindSpec(value
								.toString().trim());
					}
				});
		tblContract
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

	}

	/**
	 * This method initializes btnSimulation
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSimulation() {
		if (btnSimulation == null) {
			btnSimulation = new JButton();
			btnSimulation.setText("核销确认");
			btnSimulation.setPreferredSize(new Dimension(65, 30));
			btnSimulation
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									FmContractCav.this, "你确定核销吗？", "提示",
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
								if (tableModel.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmContractCav.this, "请选择要核销确认的合同",
											"提示", JOptionPane.OK_OPTION);
									return;
								}
								Contract contract = (Contract) tableModel
										.getCurrentRow();
								contract.setDeclareState("5");
								contract.setIsCancel(new Boolean(true));
								contractAction.saveContract(new Request(
										CommonVars.getCurrUser()), contract);
								tableModel.deleteRow(contract);
							}
						}
					});
		}
		return btnSimulation;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
