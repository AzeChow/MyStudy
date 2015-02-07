/*
 * Created on 2004-7-5
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import com.bestway.bcs.client.message.BcsMessageHelper;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmContract extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRenovate = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnBrowse = null;

	private JButton btnPutOnRecord = null;

	private JButton btnCopy = null;

	private JButton btnExit = null;

	private JButton btnChanging = null;

	private JTable tbContract = null;

	private JScrollPane jScrollPane1 = null;

	private JTableListModel tableModel = null;

	private ContractAction contractAction = null;

	private JPanel jPanel = null;

	private JCheckBox cbIsCancel = null;

	private JButton btnProcessContract = null;

	private JPopupMenu pmCopyData = null;

	private JMenuItem miAllCopy = null;

	private JMenuItem miAllProductCopy = null;

	private JMenuItem miProductMateriel = null;

	private JButton btnApply = null;

	private JButton btnProcess = null;

	private String manageType = "0"; // @jve:decl-index=0:

	private JButton btnImport = null;

	private JPopupMenu pmImport = null; // @jve:decl-index=0:visual-constraint="937,148"

	private JMenuItem miImportQPExcel = null;

	private JMenuItem miImportFromQP = null;

	/**
	 * This is the default constructor
	 */
	public FmContract() {

		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		BcsParameterSet parameterSet = contractAction
				.findBcsParameterSet(new Request(CommonVars.getCurrUser()));
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
	 * @return void
	 */
	private void initialize() {
		this.setSize(816, 454);
		this.setTitle("通关手册备案管理");
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
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnApply());
			jToolBar.add(getBtnProcess());
			jToolBar.add(getBtnChanging());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnRenovate());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnProcessContract());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");

			btnAdd.setPreferredSize(new Dimension(64, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			btnEdit.setPreferredSize(new Dimension(64, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			btnDelete.setPreferredSize(new Dimension(64, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			btnBrowse.setPreferredSize(new Dimension(64, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					browse();
				}
			});

		}
		return btnBrowse;
	}

	/**
	 * 
	 * This method initializes btnPutOnRecord
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
			btnPutOnRecord.setPreferredSize(new Dimension(64, 30));
			btnPutOnRecord.addActionListener(

			new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BcsParameterSet parameterSet = contractAction
							.findBcsParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					if (parameterSet != null
							&& parameterSet.getManageType() != null) {
						if (!parameterSet.getManageType().equals(manageType)) {
							JOptionPane.showMessageDialog(FmContract.this,
									"您已经对参数设置进行了修改,关闭后重新打开才能生效！", "警告！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
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
			btnCopy.setPreferredSize(new Dimension(64, 30));
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// getPmCopyData().show(btnCopy, 0, btnCopy.getHeight());
					getPmCopyData().show(btnCopy, 0, btnCopy.getHeight());
				}
			});
		}
		return btnCopy;
	}

	// 刷新数据
	private JButton getBtnRenovate() {
		if (btnRenovate == null) {
			btnRenovate = new JButton();
			btnRenovate.setText("刷新");
			btnRenovate.setPreferredSize(new Dimension(64, 30));
			btnRenovate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
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
			btnExit.setPreferredSize(new Dimension(64, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmContract.this.dispose();
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
			btnChanging.setPreferredSize(new Dimension(64, 30));
			btnChanging.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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
			tbContract.setRowHeight(25);
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
					Contract c = (Contract) tableModel.getCurrentRow();
					if (c == null) {
						return;
					}

					String declareState = c.getDeclareState();
					if (declareState.equalsIgnoreCase(DeclareState.PROCESS_EXE)) {
						//
						// 核销
						//
						if (e.isShiftDown() == true
								&& e.isControlDown() == true
								&& e.getKeyCode() == KeyEvent.VK_RIGHT) {
							if (JOptionPane.showConfirmDialog(FmContract.this,
									"是否确定核销!!!", "提示", 0) != 0) {
								return;
							}
							contractCancel(c, true);
						}
					} else if (declareState
							.equalsIgnoreCase(DeclareState.CHANGING_CANCEL)) {
						//
						// 撤消核销
						//
						if (e.isShiftDown() == true
								&& e.isControlDown() == true
								&& e.getKeyCode() == KeyEvent.VK_LEFT) {
							if (JOptionPane.showConfirmDialog(FmContract.this,
									"是否确定撤消核销!!!", "提示", 0) != 0) {
								return;
							}
							contractCancel(c, false);
						}
						if (e.isShiftDown() == true
								&& e.isControlDown() == true
								&& e.getKeyCode() == KeyEvent.VK_RIGHT) {
							if (JOptionPane.showConfirmDialog(FmContract.this,
									"是否确定删除已核销的合同!!!", "警告",
									JOptionPane.WARNING_MESSAGE) != 0) {
								return;
							}
							List list = new ArrayList();
							list.add(c);
							contractAction.deleteCavContract(new Request(
									CommonVars.getCurrUser()), list);
							tableModel.deleteRow(c);
						}
					}

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

	private void showHeadData() {
		initTable();
	}

	/**
	 * 初始化数据Table
	 */
	public void initTable() {
		//
		// 查询没有审核的合同记录
		//
		List list = this.contractAction.findContract(
				new Request(CommonVars.getCurrUser()), cbIsCancel.isSelected());
		tableModel = new JTableListModel(tbContract, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("进口合同号", "impContractNo", 100));
						list.add(addColumn("出口合同号", "expContractNo", 100));
						list.add(addColumn("合同状态", "declareState", 60));
						list.add(addColumn("合同性质", "emsType", 100));
						list.add(addColumn("开始有效期", "beginDate", 100));
						list.add(addColumn("有效期限", "endDate", 100));
						// list.add(addColumn("延期期限", "deferDate", 100));
						list.add(addColumn("核销日期", "destroyDate", 100));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("报送关区", "declareCustoms.name", 100));

						list.add(addColumn("批文帐册号", "sancEmsNo", 100));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));

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
						list.add(addColumn("贸易国别", "tradeCountry.name", 100));
						list.add(addColumn("企业地址", "enterpriseAddress", 100));
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
		// tbContract.getColumnModel().getColumn(1).setCellRenderer(new
		// TableMultiRowRender());
		tbContract.getColumnModel().getColumn(4)
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

		tbContract.getColumnModel().getColumn(5)
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
	}

	/**
	 * 新增
	 */
	private void addData() {
		Contract contract = this.contractAction.newContract(new Request(
				CommonVars.getCurrUser()));

		tableModel.addRow(contract);
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
		Contract contract = (Contract) tableModel.getCurrentRow();
		contract = this.contractAction.findContractById(
				new Request(CommonVars.getCurrUser()), contract.getId());
		if (contract == null) {
			JOptionPane.showMessageDialog(this, "合同 " + contract.getEmsNo()
					+ " 已经删除不能修改", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (contract != null && !contract.getIsCancel()
				&& contract.getDeclareState() != null
				&& contract.getDeclareState().equals(DeclareState.PROCESS_EXE)) {
			JOptionPane.showMessageDialog(this, "合同 " + contract.getEmsNo()
					+ " 已经生效不能修改", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DgContract dg = new DgContract();
		dg.setTableModelContract(tableModel);
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
		DgContract dg = new DgContract();
		dg.setTableModelContract(tableModel);
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
				Contract contract = (Contract) list.get(i);
				String emsNo = contract.getEmsNo();
				contract = this.contractAction.findContractById(new Request(
						CommonVars.getCurrUser()), contract.getId());
				if (contract == null) {
					JOptionPane.showMessageDialog(this, "合同 " + emsNo
							+ " 已经删除不能再删除", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (contract != null
						&& !contract.getIsCancel()
						&& contract.getDeclareState().equals(
								DeclareState.PROCESS_EXE)) {
					JOptionPane.showMessageDialog(this, "合同 " + emsNo
							+ " 已经生效不能删除", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			try {
				Contract contract = (Contract) list.get(0);
				if (contract.getIsCancel()) {
					this.contractAction.deleteCavContract(new Request(
							CommonVars.getCurrUser()), tableModel
							.getCurrentRows());
					tableModel.deleteRows(tableModel.getCurrentRows());
				} else {
					this.contractAction.deleteContract(
							new Request(CommonVars.getCurrUser()),
							tableModel.getCurrentRows());
					tableModel.deleteRows(tableModel.getCurrentRows());
				}
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
	 * 合同核消
	 */
	private void contractCancel(Contract contract, boolean isCancel) {
		if (contract == null) {
			return;
		}
		contract.setIsCancel(isCancel);
		if (isCancel) {
			contract.setDeclareState(DeclareState.CHANGING_CANCEL);
		} else {
			contract.setDeclareState(DeclareState.PROCESS_EXE);
		}
		contract = this.contractAction.saveContract(
				new Request(CommonVars.getCurrUser()), contract);
		this.tableModel.deleteRow(contract);
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
			Contract contract = (Contract) tableModel.getCurrentRow();
			if (!DeclareState.CHANGING_EXE.equals(contract.getDeclareState())) {
				if (contractAction.isExistContractByEmsNo(new Request(
						CommonVars.getCurrUser()), contract)) {
					JOptionPane.showMessageDialog(FmContract.this,
							"合同手册编号重复,不能备案!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			String checkMsg = this.contractAction
					.checkContractIsAmountToInteger(
							new Request(CommonVars.getCurrUser(), true),
							contract);
			if (checkMsg != null && checkMsg.trim().length() > 0) {
				JOptionPane.showMessageDialog(this, checkMsg, "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String checkMessage = this.contractAction
					.checkContractForPutOnRecord(
							new Request(CommonVars.getCurrUser(), true),
							contract);
			if (checkMessage != null && checkMessage.trim().length() > 0) {
				if (JOptionPane.showConfirmDialog(FmContract.this, checkMessage
						+ "\n是否确定备案此合同!!!", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			Contract c = this.contractAction.putOnRecordContract(new Request(
					CommonVars.getCurrUser()), contract);
			tableModel.updateRow(c);
			//
			// 获得其备案合同
			//
			if (DeclareState.CHANGING_EXE.equals(contract.getDeclareState())) {
				Contract putOnRecordContract = null;
				for (int i = 0; i < tableModel.getList().size(); i++) {
					Contract tempContract = (Contract) this.tableModel
							.getList().get(i);
					if (DeclareState.PROCESS_EXE.equals(tempContract
							.getDeclareState())
							&& tempContract.getEmsNo().equals(
									contract.getEmsNo())) {
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
			// 合同变更1.对应带"/"标志的旧合同原有的合同号不改变
			// 2.对于新签的合同，可以在合同号末尾增加两位变更次数（非必填）
			Contract contract = (Contract) tableModel.getCurrentRow();

			String impContractNo = contract.getImpContractNo();

			String expContractNo = contract.getExpContractNo();

			boolean isOldContract = false;// 是否是旧的合同

			if (impContractNo.contains("/") && expContractNo.contains("/")) {

				isOldContract = true;

			}

			if (isOldContract) {// 旧合同变更

				Contract c = contractAction.changingContract(new Request(
						CommonVars.getCurrUser()), contract);

				if (c != null) {

					initTable();

				} else {

					JOptionPane.showMessageDialog(this, "已存在变更合同!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {// 新签合同变更

				DgContractNoUpdate dg = new DgContractNoUpdate();

				dg.setContract(contract);

				dg.setVisible(true);

				if (dg.isOk() && dg.isCheck()) {
					contract = dg.getContract();
					Contract c = this.contractAction.changingContract(
							new Request(CommonVars.getCurrUser()), contract);
					if (c != null) {
						initTable();
					} else {
						JOptionPane.showMessageDialog(this, "已存在变更合同!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
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
		Contract c = (Contract) tableModel.getCurrentRow();
		if (c == null) {
			return false;
		}
		if (c.getEmsNo() == null || c.getEmsNo().equals("")) {
			JOptionPane.showMessageDialog(this, "合同手册编号不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} else if (c.getEmsNo().toString().trim().length() != 12) {
			if (JOptionPane.showConfirmDialog(this, "当前手册号不是12位!", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
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
		// if (c.getIePort1() == null) {
		// JOptionPane.showMessageDialog(this, "进口口岸不可为空!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
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

		if (c.getEndDate() == null) {
			JOptionPane.showMessageDialog(this, "合同有效日期不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getDestroyDate() == null) {
			JOptionPane.showMessageDialog(this, "合同核销日期不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		// if (c.getTradeCountry() == null) {
		// JOptionPane.showMessageDialog(this, "合同贸易国别不可为空!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// if (c.getLinkMan() == null || c.getLinkMan().equals("")) {
		// JOptionPane.showMessageDialog(this, "联系人不可为空!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }
		// if (c.getContactTel() == null || c.getContactTel().equals("")) {
		// JOptionPane.showMessageDialog(this, "联系电话不可为空!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		// }

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
		if (c.getImgAmount() == null || c.getImgAmount().doubleValue() == 0) {
			JOptionPane.showMessageDialog(this, "进口总值不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (c.getCurr() == null) {
			JOptionPane.showMessageDialog(this, "币制不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		if (c.getImpContractNo() == null || c.getImpContractNo().equals("")) {
			if (JOptionPane.showConfirmDialog(this, "进口合同号为空,确认备案吗?", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return false;
			}
		}
		if (this.contractAction.getMaxContractImgSeqNum(
				new Request(CommonVars.getCurrUser()), c.getId()) <= 0) {
			JOptionPane.showMessageDialog(this, "料件记录个数不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		if (this.contractAction.getMaxContractExgSeqNum(
				new Request(CommonVars.getCurrUser()), c.getId()) <= 0) {
			JOptionPane.showMessageDialog(this, "成品记录个数不可为空!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return false;

		}
		// if (this.contractAction.getMaxContractBomSeqNum(new
		// Request(CommonVars
		// .getCurrUser()), c.getId()) <= 0) {
		// JOptionPane.showMessageDialog(this, "单耗记录个数不可为空!!!", "提示",
		// JOptionPane.INFORMATION_MESSAGE);
		// return false;
		//
		// }
		if (JOptionPane.showConfirmDialog(this, "你确认合同:" + c.getImpContractNo()
				+ "  海关已经备案完毕吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
			return false;
		}
		return true;
	}

	/**
	 * 是一般合同还是纸质手册电子化
	 * 
	 * @return
	 */
	private boolean isContractEms() {
		Contract contract = (Contract) tableModel.getCurrentRow();
		if (contract != null && contract.getIsContractEms() != null) {
			return contract.getIsContractEms();
		}
		return false;
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		boolean isContractEms = isContractEms();
		btnAdd.setEnabled(!cbIsCancel.isSelected());
		Contract c = (Contract) tableModel.getCurrentRow();
		if (c == null) {
			return;
		}
		boolean isProcessExe = (c.getDeclareState() != null && c
				.getDeclareState().equals(DeclareState.PROCESS_EXE));
		btnDelete.setEnabled(DeclareState.APPLY_POR.equals(c.getDeclareState())
				|| DeclareState.CHANGING_EXE.equals(c.getDeclareState()));
		btnCopy.setEnabled(!cbIsCancel.isSelected()
				&& (DeclareState.APPLY_POR.equals(c.getDeclareState()) || DeclareState.PROCESS_EXE
						.equals(c.getDeclareState())));
		btnEdit.setEnabled(!isProcessExe
				&& !cbIsCancel.isSelected()
				&& (DeclareState.APPLY_POR.equals(c.getDeclareState()) || DeclareState.CHANGING_EXE
						.equals(c.getDeclareState())));
		btnChanging.setEnabled(isProcessExe && !cbIsCancel.isSelected());
		if (manageType.equals("0")) {// true:纸质手册电子化false:普通纸质手册
			btnPutOnRecord
					.setEnabled((!isContractEms)
							&& (DeclareState.APPLY_POR.equals(c
									.getDeclareState()) || DeclareState.CHANGING_EXE
									.equals(c.getDeclareState())));
		} else if (this.manageType.equals("1")) {
			btnPutOnRecord.setEnabled(DeclareState.APPLY_POR.equals(c
					.getDeclareState())
					|| DeclareState.CHANGING_EXE.equals(c.getDeclareState()));
		}
		this.btnApply
				.setEnabled(isContractEms
						&& (DeclareState.APPLY_POR.equals(c.getDeclareState()) || DeclareState.CHANGING_EXE
								.equals(c.getDeclareState()))
						&& this.manageType.equals("0"));// 是电子化手册合，而且状态必需是申请备案，加上管理类型是申报和管理，才可用
		this.btnProcess.setEnabled(DeclareState.WAIT_EAA.equals(c
				.getDeclareState()));
		this.btnProcessContract.setEnabled(isContractEms);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			// jPanel.setLayout(null);
			jPanel.add(getCbIsCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCancel() {
		if (cbIsCancel == null) {
			cbIsCancel = new JCheckBox();
			cbIsCancel.setBounds(2, 8, 109, 14);
			cbIsCancel.setText("已核销的合同");
			cbIsCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					initTable();
					setState();
				}
			});
		}
		return cbIsCancel;
	}

	/**
	 * This method initializes btnResetContractBom
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcessContract() {
		if (btnProcessContract == null) {
			btnProcessContract = new JButton();
			btnProcessContract.setText("下载合同");
			btnProcessContract
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgProcessContractData dg = new DgProcessContractData();
							dg.setVisible(true);
							initTable();
						}
					});
		}
		return btnProcessContract;
	}

	private JPopupMenu getPmCopyData() {
		if (pmCopyData == null) {
			pmCopyData = new JPopupMenu();
			pmCopyData.add(getMiAllCopy());
			pmCopyData.add(getMiAllProductCopy());
			pmCopyData.add(getMiProductMateriel());
		}
		return pmCopyData;
	}

	public JMenuItem getMiAllCopy() {
		if (miAllCopy == null) {
			miAllCopy = new JMenuItem();
			miAllCopy.setText("完全转抄");
			miAllCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() != null) {
						//
						// 控制多条转抄
						//
						// boolean isCopy = true;
						// List ls = tableModel.getCurrentRows();
						// for (int i = 0; i < ls.size(); i++) {
						// Contract contract = (Contract) ls.get(i);
						// if (!DeclareState.PROCESS_EXE
						// .equals(contract.getDeclareState())) {
						// isCopy = false;
						// break;
						// }
						// }
						// if (isCopy == false) {
						// JOptionPane.showMessageDialog(this,
						// "转抄数据中存在不是正在执行的合同!!!",
						// "提示", JOptionPane.INFORMATION_MESSAGE);
						// return;
						// }
						List<Contract> list = contractAction.copyContract(
								new Request(CommonVars.getCurrUser()),
								tableModel.getCurrentRows());
						tableModel.addRows(list);
					} else {
						JOptionPane.showMessageDialog(FmContract.this,
								"请选择要转抄合同!!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return miAllCopy;
	}

	public JMenuItem getMiAllProductCopy() {
		if (miAllProductCopy == null) {
			miAllProductCopy = new JMenuItem();
			miAllProductCopy.setText("转抄成品并带出单耗和料件");
			miAllProductCopy
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() != null) {
								DgCopyProduct dg = new DgCopyProduct();
								dg.setTableModelContract(tableModel);
								dg.setVisible(true);
								if (dg.isOk()) {
									tableModel.addRow(dg.getContract());
								}
							} else {
								JOptionPane.showMessageDialog(FmContract.this,
										"请选择要转抄合同!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
		}
		return miAllProductCopy;
	}

	public JMenuItem getMiProductMateriel() {
		if (miProductMateriel == null) {
			miProductMateriel = new JMenuItem();
			miProductMateriel.setText("转抄成品(不带出单耗),料件");
			miProductMateriel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() != null) {
								DgCopyProudctMateriel dg = new DgCopyProudctMateriel();
								dg.setContract((Contract) tableModel
										.getCurrentRow());
								dg.setVisible(true);
								if (dg.isOk()) {
									tableModel.addRow(dg.getContract());
								}
							} else {
								JOptionPane.showMessageDialog(FmContract.this,
										"请选择要转抄合同!!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
		}
		return miProductMateriel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BcsParameterSet parameterSet = contractAction
							.findBcsParameterSet(new Request(CommonVars
									.getCurrUser(), true));
					if (parameterSet != null
							&& parameterSet.getManageType() != null) {
						if (!parameterSet.getManageType().equals(manageType)) {
							JOptionPane.showMessageDialog(FmContract.this,
									"您已经对参数设置进行了修改,关闭后重新打开才能生效！", "警告！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane
								.showMessageDialog(FmContract.this, "请选择合同!",
										"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (!getBtnApply().getText().equals("海关申报")) {
						JOptionPane.showMessageDialog(FmContract.this,
								"您已经对参数设置进行了修改,关闭后重新打开才能生效！", "警告！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					Contract head = (Contract) tableModel.getCurrentRow();
					if (head.getIsContractEms() == null
							|| !head.getIsContractEms()) {
						JOptionPane.showMessageDialog(FmContract.this,
								"该合同不是电子化手册合同，不能申报！", "警告！",
								JOptionPane.WARNING_MESSAGE);
						return;

					}
					head = contractAction.findContractById(new Request(
							CommonVars.getCurrUser()), head.getId());
					tableModel.updateRow(head);
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
				Contract head = (Contract) tableModel.getCurrentRow();
				try {
					String checkMsg = contractAction
							.checkContractIsAmountToInteger(new Request(
									CommonVars.getCurrUser(), true), head);
					if (checkMsg != null && checkMsg.trim().length() > 0) {
						JOptionPane
								.showMessageDialog(FmContract.this, checkMsg,
										"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					String checkModifyMarkMsg = contractAction
							.checkContractIsUnitModifyMarkExgBom(new Request(
									CommonVars.getCurrUser()), head);
					if (checkModifyMarkMsg != null
							&& checkModifyMarkMsg.trim().length() > 0) {
						if (JOptionPane.showConfirmDialog(FmContract.this,
								"成品序号" + checkModifyMarkMsg
										+ "修改标志与成品单耗【修改标志】不一致，确定继续申报吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
					}
					DeclareFileInfo fileInfo = contractAction.applyContract(
							new Request(CommonVars.getCurrUser()), head);
					head = contractAction.findContractById(new Request(
							CommonVars.getCurrUser()), head.getId());
					if (head != null) {
						// tableModel.updateRow(head);
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmContract.this,
							fileInfo.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmContract.this, "系统申报失败"
							+ ex.getMessage(), "确认", 1);
				}
				// setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnProcess() {
		if (btnProcess == null) {
			btnProcess = new JButton();
			btnProcess.setText("处理回执");
			btnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					contractAction.applyContractcheck(new Request(CommonVars
							.getCurrUser()));
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmContract.this,
								"请选择要回执处理的合同", "提示", 2);
						return;
					}
					Contract head = (Contract) tableModel.getCurrentRow();
					if (head.getIsContractEms() == null
							|| !head.getIsContractEms()) {
						JOptionPane.showMessageDialog(FmContract.this,
								"该合同不是电子化手册合同，不能处理回执！", "警告！",
								JOptionPane.WARNING_MESSAGE);
						return;

					}
					Contract contract = (Contract) tableModel.getCurrentRow();// 变更
					List lsReturnFile = BcsMessageHelper.getInstance()
							.showBcsReceiptFile(DzscBusinessType.EMS_POR_BILL,
									contract.getCopEmsNo());
					if (lsReturnFile.size() <= 0) {
						return;
					}
					Contract billExingHead = null;
					if (contract.getEmsNo() != null
							&& !"".equals(contract.getEmsNo().trim())) {
						billExingHead = contractAction
								.findExingContractByEmsNo(new Request(
										CommonVars.getCurrUser()), contract
										.getEmsNo());
					}
					try {
						String result = contractAction.processContract(
								new Request(CommonVars.getCurrUser()),
								contract, billExingHead, lsReturnFile);
						contract = contractAction.findContractById(new Request(
								CommonVars.getCurrUser()), contract.getId());
						tableModel.updateRow(contract);
						if (billExingHead != null) {
							Contract exingHeadTemp = contractAction
									.findContractById(
											new Request(CommonVars
													.getCurrUser()),
											billExingHead.getId());
							if (exingHeadTemp == null) {
								tableModel.deleteRow(billExingHead);
							}
						}
						JOptionPane.showMessageDialog(FmContract.this,
								"回执处理成功！\n" + result, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmContract.this, "回执处理失败"
								+ ex.getMessage(), "提示",
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.setPreferredSize(new Dimension(64, 30));
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					contractAction.importContract(new Request(CommonVars
							.getCurrUser()));
					Component comp = (Component) e.getSource();
					getPmImport().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes jPopupMenu
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
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportQPExcel() {
		if (miImportQPExcel == null) {
			miImportQPExcel = new JMenuItem();
			miImportQPExcel.setText("从QP导出文件中导入手册资料");
			miImportQPExcel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImportContractFromQPExcel dg = new DgImportContractFromQPExcel(
									FmContract.this);
							dg.setVisible(true);
							if (dg.isOk()) {
								Contract contract = dg.getContract();
								if (contract != null) {
									tableModel.addRow(contract);
								}
							}
						}
					});
		}
		return miImportQPExcel;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportFromQP() {
		if (miImportFromQP == null) {
			miImportFromQP = new JMenuItem();
			miImportFromQP.setText("直接导入通关手册资料");
			miImportFromQP
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List lsExistEmsNo = new ArrayList();
							List listDictPorHead = tableModel.getList();
							for (int i = 0; i < listDictPorHead.size(); i++) {
								Contract contract = (Contract) listDictPorHead
										.get(i);
								if (DeclareState.PROCESS_EXE.equals(contract
										.getDeclareState())) {
									lsExistEmsNo.add(contract.getEmsNo());
								}
							}
							DgBcsQPContractHead dg = new DgBcsQPContractHead();
							dg.setLsExistEmsNo(lsExistEmsNo);
							dg.setVisible(true);
							List list = dg.getLsResult();
							boolean isOverWrite = dg.isOverWrite();
							if (list != null && !list.isEmpty()) {
								contractAction.importContractFromQP(
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

} // @jve:decl-index=0:visual-constraint="10,10"
