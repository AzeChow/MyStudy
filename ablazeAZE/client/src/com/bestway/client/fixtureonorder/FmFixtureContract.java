/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.fixtureonorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import com.bestway.fixtureonorder.action.CheckFixAuthorityAction;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;

/**
 * @author fhz
 * 设备协议备案管理
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 * edit by 陈井彬
 */
public class FmFixtureContract extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	/**
	 * 刷新按钮
	 */
	private JButton btnRenovate = null;

	/**
	 * 新增按钮
	 */
	private JButton btnAdd = null;

	/**
	 * 修改按钮
	 */
	private JButton btnEdit = null;

	/**
	 * 删除按钮
	 */
	private JButton btnDelete = null;

	/**
	 * 浏览按钮
	 */
	private JButton btnBrowse = null;

	/**
	 * 备案按钮
	 */
	private JButton btnPutOnRecord = null;

	/**
	 * 转抄按钮
	 */
	private JButton btnCopy = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;

	/**
	 * 变更按钮
	 */
	private JButton btnChanging = null;

	/**
	 * 合同表格
	 */
	private JTable tbContract = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 合同表格模型
	 */
	private JTableListModel tableModel = null;

	/**
	 * 合同操作接口
	 */
	private FixtureContractAction fixtureContractAction = null;

	/**
	 * 对应关系操作接口
	 */
	private CheckFixAuthorityAction checkFixAuthorityAction = null;

	/**
	 * This is the default constructor
	 */
	public FmFixtureContract() {
		super();
		fixtureContractAction = (FixtureContractAction) CommonVars
				.getApplicationContext().getBean("fixtureContractAction");
		checkFixAuthorityAction = (CheckFixAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkFixAuthorityAction");
		initialize();
		initTable(false);
		setState();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(702, 454);
		this.setTitle("设备协议备案管理");
		this.setContentPane(getJContentPane());
		this.setHelpId("");
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
			jContentPane.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnChanging());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnRenovate());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 新增按钮
	 * @return
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");

			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkNewFixtureContract(new Request(CommonVars.getCurrUser()));
					addData();
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
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkEditFixtureContract(new Request(CommonVars.getCurrUser()));
					editData();
				}
			});

		}
		return btnEdit;
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
					checkFixAuthorityAction.checkDeleteFixtureContract(new Request(CommonVars.getCurrUser()));
					deleteData();
				}
			});
		}
		return btnDelete;
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
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkBrowseFixtureContract(new Request(CommonVars.getCurrUser()));
					browse();
				}
			});

		}
		return btnBrowse;
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
		if (btnPutOnRecord == null) {
			btnPutOnRecord = new JButton();
			btnPutOnRecord.setText("备案");
			btnPutOnRecord.addActionListener(

			new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkPutOnRecordFixtureContract(new Request(CommonVars.getCurrUser()));
					putOnRecordData();
				}
			});
		}
		return btnPutOnRecord;
	}

	/**
	 * 
	 * This method initializes btnPrint
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkCopyFixtureContract(new Request(CommonVars.getCurrUser()));
					if (tableModel.getCurrentRow() != null) {
						List<FixtureContract> list = fixtureContractAction
								.copyContract(new Request(CommonVars
										.getCurrUser()), tableModel
										.getCurrentRows());
						tableModel.addRows(list);
					} else {
						JOptionPane.showMessageDialog(FmFixtureContract.this,
								"请选择要转抄合同!!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}

				}
			});
		}
		return btnCopy;
	}

	/**
	 * 刷新数据
	 * @return
	 */
	private JButton getBtnRenovate() {
		if (btnRenovate == null) {
			btnRenovate = new JButton();
			btnRenovate.setText("刷新");
			btnRenovate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkRenovateFixtureContract(new Request(CommonVars.getCurrUser()));
					initTable(false);
				}
			});
		}
		return btnRenovate;
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
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmFixtureContract.this.dispose();
				}
			});

		}
		return btnExit;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChanging() {
		if (btnChanging == null) {
			btnChanging = new JButton();
			btnChanging.setText("变更");
			btnChanging.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					checkFixAuthorityAction.checkChangFixtureContract(new Request(CommonVars.getCurrUser()));
					changingData();
				}
			});
		}
		return btnChanging;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbContract() {
		if (tbContract == null) {
			tbContract = new JTable();
			tbContract.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbContract
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
			tbContract.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						browse();
					}
				}
			});
			tbContract.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					FixtureContract c = (FixtureContract) tableModel
							.getCurrentRow();
					if (c == null) {
						return;
					}

					String declareState = c.getDeclareState();

				}
			});
		}
		return tbContract;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jScrollPane1.setViewportView(getTbContract());
		}
		return jScrollPane1;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(boolean isCancel) {
		//
		// 查询没有审核的合同记录
		//
		List list = fixtureContractAction.findContract(new Request(CommonVars
				.getCurrUser()), isCancel);
		System.out.println("list-------------------------------" + list);
		tableModel = new JTableListModel(tbContract, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("进口合同号", "impContractNo", 100));
						list.add(addColumn("出口合同号", "expContractNo", 100));
						list.add(addColumn("批文号", "sancEmsNo", 100));
						list.add(addColumn("协议书号", "agreementNo", 100));
						list.add(addColumn("合同状态", "declareState", 60));
						list.add(addColumn("开始有效期", "beginDate", 100));
						list.add(addColumn("有效期限", "availabilityDate", 100));
						list.add(addColumn("延期期限", "deferDate", 100));
						list.add(addColumn("报送关区", "declareCustoms.name", 100));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));

						list.add(addColumn("进口口岸", "iePort1.name", 100));
						list.add(addColumn("外商公司", "outTradeCo", 100));
						list.add(addColumn("征免性质", "levyKind.name", 50));
						list.add(addColumn("保税方式", "payMode.name", 50));
						list.add(addColumn("贸易方式", "trade.name", 100));
						list.add(addColumn("贸易国别", "tradeCountry.name", 100));
						list.add(addColumn("企业地址", "enterpriseAddress", 100));
						list.add(addColumn("联系人", "linkMan", 100));
						list.add(addColumn("联系电话", "contactTel", 100));
						list.add(addColumn("协议书号", "agreementNo", 100));

						list.add(addColumn("设备总金额", "fixtureAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("监管费用", "wardshipFee", 100));
						list.add(addColumn("监管费率", "wardshipRate", 100));
						list.add(addColumn("成交方式", "transac.name", 100));
						list.add(addColumn("进口口岸2", "iePort2.name", 100));
						list.add(addColumn("进口口岸3", "iePort3.name", 100));
						list.add(addColumn("进口口岸4", "iePort4.name", 100));
						list.add(addColumn("进口口岸5", "iePort5.name", 100));
						list.add(addColumn("审批人", "retrialer", 100));
						list.add(addColumn("审批日期", "approveDate", 100));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});

		tbContract.getColumnModel().getColumn(6).setCellRenderer(
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

						if (value.equals(DeclareState.APPLY_POR)) {
							returnValue = "正在申请";
						} else if (value.equals(DeclareState.PROCESS_EXE)) {
							returnValue = "正在执行";
						} else if (value.equals(DeclareState.CHANGING_EXE)) {
							returnValue = "正在变更";
						}
						return returnValue;
					}
				});
	}

	/**
	 * 新增
	 */
	private void addData() {
		DgFixtureContract dg = new DgFixtureContract();
		dg.setTableModelContract(tableModel);
		dg.setDataState(DataState.ADD);
		dg.setTitleString("设备协议新增");
		dg.setVisible(true);
	}

	/**
	 * 修改
	 */
	private void editData() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		FixtureContract contract = (FixtureContract) tableModel.getCurrentRow();
		contract = this.fixtureContractAction.findContractById(new Request(
				CommonVars.getCurrUser()), contract.getId());
		if (contract == null) {
			JOptionPane.showMessageDialog(this, "合同 " + contract.getEmsNo()
					+ " 已经删除不能修改", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (contract != null && contract.getDeclareState() != null
				&& contract.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			JOptionPane.showMessageDialog(this, "合同 " + contract.getEmsNo()
					+ " 已经生效不能修改", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgFixtureContract dg = new DgFixtureContract();
		dg.setTableModelContract(tableModel);
		dg.setTitleString("设备协议修改");
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
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
		DgFixtureContract dg = new DgFixtureContract();
		dg.setTableModelContract(tableModel);
		dg.setTitleString("设备协议浏览");
		dg.setDataState(DataState.BROWSE);
		dg.setVisible(true);
	}

	/**
	 * 删除
	 */
	private void deleteData() {
		if (tableModel.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
			//
			// 控制多条删除
			//
			List list = tableModel.getCurrentRows();
			for (int i = 0; i < list.size(); i++) {
				FixtureContract contract = (FixtureContract) list.get(i);
				String emsNo = contract.getEmsNo();
				contract = this.fixtureContractAction
						.findContractById(
								new Request(CommonVars.getCurrUser()), contract
										.getId());
				if (contract == null) {
					JOptionPane.showMessageDialog(this, "合同 " + emsNo
							+ " 已经删除不能再删除", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (contract != null
						&& contract.getDeclareState().equals(
								DeclareState.PROCESS_EXE)) {
					JOptionPane.showMessageDialog(this, "合同 " + emsNo
							+ " 已经生效不能删除", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			try {
				FixtureContract contract = (FixtureContract) list.get(0);
				this.fixtureContractAction.deleteContract(new Request(
						CommonVars.getCurrUser()), tableModel.getCurrentRows());
				tableModel.deleteRows(tableModel.getCurrentRows());

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 备案
	 * 
	 */
	private void putOnRecordData() {
		if (tableModel.getCurrentRow() != null) {
			if (validateData() == false) {
				return;
			}
			FixtureContract fixtureContract = (FixtureContract) tableModel
					.getCurrentRow();
			if (!DeclareState.CHANGING_EXE.equals(fixtureContract
					.getDeclareState())) {
				if (fixtureContractAction.isExistContractByEmsNo(new Request(
						CommonVars.getCurrUser()), fixtureContract)) {
					JOptionPane.showMessageDialog(FmFixtureContract.this,
							"合同手册编号重复,不能备案!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			String checkMessage = this.fixtureContractAction
					.checkContractForPutOnRecord(new Request(CommonVars
							.getCurrUser(), true), fixtureContract);
			if (checkMessage != null && checkMessage.trim().length() > 0) {
				if (JOptionPane.showConfirmDialog(FmFixtureContract.this,
						checkMessage + "\n是否确定备案此合同!!!", "提示",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			FixtureContract c = this.fixtureContractAction.putOnRecordContract(
					new Request(CommonVars.getCurrUser()), fixtureContract);
			tableModel.updateRow(c);
			//
			// 获得其备案合同
			//           
			if (DeclareState.CHANGING_EXE.equals(fixtureContract
					.getDeclareState())) {
				FixtureContract putOnRecordContract = null;
				for (int i = 0; i < tableModel.getList().size(); i++) {
					FixtureContract tempContract = (FixtureContract) this.tableModel
							.getList().get(i);
					if (DeclareState.PROCESS_EXE.equals(tempContract
							.getDeclareState())
							&& tempContract.getEmsNo().equals(
									fixtureContract.getEmsNo())) {
						putOnRecordContract = tempContract;
						break;
					}
				}
				if (putOnRecordContract != null) {
					tableModel.deleteRow(putOnRecordContract);
				}
			}

		} else {
			JOptionPane.showMessageDialog(this, "请选择要备案合同!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 变更合同
	 * 
	 */
	private void changingData() {
		if (tableModel.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定变更合同!", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
			FixtureContract c = this.fixtureContractAction.changingContract(
					new Request(CommonVars.getCurrUser()),
					(FixtureContract) tableModel.getCurrentRow());
			if (c != null) {
				tableModel.addRow(c);
			} else {
				JOptionPane.showMessageDialog(this, "已存在变更合同!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "请选择要变更合同!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 检验备案数据
	 * 
	 */
	private boolean validateData() {
		FixtureContract c = (FixtureContract) tableModel.getCurrentRow();
		if (c == null) {
			return false;
		}
		if (c.getEmsNo() == null || c.getEmsNo().equals("")) {
			JOptionPane.showMessageDialog(this, "合同手册编号不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		// else if (c.getEmsNo().toString().trim().length() != 12) {
		// if (JOptionPane.showConfirmDialog(this, "当前手册号不是12位!", "提示",
		// JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
		// return false;
		// }
		// }
		if (c.getTradeName() == null || c.getTradeName().equals("")) {
			JOptionPane.showMessageDialog(this, "经营单位不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getMachName() == null || c.getMachName().equals("")) {
			JOptionPane.showMessageDialog(this, "收货单位不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getIePort1() == null) {
			JOptionPane.showMessageDialog(this, "进口口岸不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getTrade() == null) {
			JOptionPane.showMessageDialog(this, "贸易方式不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getBeginDate() == null) {
			JOptionPane.showMessageDialog(this, "合同起始日期不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (c.getAvailabilityDate() == null) {
			JOptionPane.showMessageDialog(this, "合同有效日期不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getTradeCountry() == null) {
			JOptionPane.showMessageDialog(this, "合同贸易国别不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getLinkMan() == null || c.getLinkMan().equals("")) {
			JOptionPane.showMessageDialog(this, "联系人不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getContactTel() == null || c.getContactTel().equals("")) {
			JOptionPane.showMessageDialog(this, "联系电话不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (c.getLevyKind() == null) {
			JOptionPane.showMessageDialog(this, "征免性质不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getOutTradeCo() == null || c.getOutTradeCo().equals("")) {
			JOptionPane.showMessageDialog(this, "外商公司不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getFixtureAmount() == null
				|| c.getFixtureAmount().doubleValue() == 0) {
			JOptionPane.showMessageDialog(this, "进口总值不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (c.getCurr() == null) {
			JOptionPane.showMessageDialog(this, "币制不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getTransac() == null) {
			JOptionPane.showMessageDialog(this, "成交不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getImpContractNo() == null || c.getImpContractNo().equals("")) {
			if (JOptionPane.showConfirmDialog(this, "进口合同号为空,确认备案吗?", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		if (c.getSancEmsNo() == null || c.getSancEmsNo().equals("")) {
			if (JOptionPane.showConfirmDialog(this, "批文号为空,确认备案吗?", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		if (this.fixtureContractAction.getMaxContractItemSeqNum(new Request(
				CommonVars.getCurrUser()), c.getId()) <= 0) {
			JOptionPane.showMessageDialog(this, "料件记录个数不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		if (JOptionPane.showConfirmDialog(this, "你确认合同:" + c.getImpContractNo()
				+ "  海关已经备案完毕吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return false;
		}
		return true;
	}

	/**
	 * 设置状态
	 */
	private void setState() {

		btnAdd.setEnabled(true);
		FixtureContract c = (FixtureContract) tableModel.getCurrentRow();
		if (c == null) {
			return;
		}
		boolean isProcessExe = (c.getDeclareState() != null && c
				.getDeclareState().equals(DeclareState.PROCESS_EXE));
		btnDelete.setEnabled(!isProcessExe);
		btnEdit.setEnabled(!isProcessExe);
		btnChanging.setEnabled(isProcessExe);
		btnPutOnRecord.setEnabled(!isProcessExe);

	}

}
