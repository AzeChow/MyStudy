package com.bestway.bls.client.entrance;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Panel;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bls.action.MaterielImportApplyAction;
import com.bestway.bls.client.message.BlsMessageHelper;
import com.bestway.bls.entity.BillSpecialApplyHead;
import com.bestway.bls.entity.BillSpecialApplyType;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
import com.bestway.bls.entity.MaterielImportApply;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.GridBagConstraints;

/**
 * 保税物流料件进口申请
 * @author chen
 *
 */
public class FmMaterielImportApply extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JToolBar jJToolBarBar = null;
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
	 * 保税物料进口申请action
	 */
	private MaterielImportApplyAction materielImportApplyAction = null;  //  @jve:decl-index=0:

	/**
	 * 保税物料进口申请表头表格
	 */
	private JTableListModel tableModel = null;
	private JPanel panel = null;
	private JScrollPane jScrollPane = null;
	/**
	 * 申请物料表格
	 */
	private JTable tableImportApply = null;
	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;
	/**
	 * 海关申报按钮
	 */
	private JButton btnSend = null;
	/**
	 * 回执处理按钮
	 */
	private JButton btnReturnProcess = null;
	/**
	 * This is the xxx default constructor
	 */
	public FmMaterielImportApply() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(576, 354);
		this.setContentPane(getJContentPane());
		this.setTitle("保税物料进口申请");
		//初始化materielImportApplyAction
		materielImportApplyAction = (MaterielImportApplyAction)CommonVars
			.getApplicationContext().getBean("materielImportApplyAction");
		
		//初始化表格
		List list = materielImportApplyAction.findMaterielImportApplyAll(new Request(CommonVars.getCurrUser()));
		this.initTable(list);
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
			jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
			jContentPane.add(getPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnBrowse());
			jJToolBarBar.add(getBtnSend());
			jJToolBarBar.add(getBtnReturnProcess());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
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
					DgImportApplyList dgImportApplyList = new DgImportApplyList();
					dgImportApplyList.setTableHeadModel(tableModel);
					dgImportApplyList.setDataState(DataState.ADD);
					dgImportApplyList.setVisible(true);
				}
			});
		}
		return btnAdd;
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
					DgImportApplyList dgImportApplyList = new DgImportApplyList();
					dgImportApplyList.setTableHeadModel(tableModel);
					dgImportApplyList.setDataState(DataState.EDIT);
					dgImportApplyList.showData();
					dgImportApplyList.setVisible(true);
				}
			});
		}
		return btnEdit;
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
					MaterielImportApply materielImportApply = (MaterielImportApply)tableModel.getCurrentRow();
					if(materielImportApply==null){
						JOptionPane.showMessageDialog(FmMaterielImportApply.this, "请选择保税物料进口申请单");
						return;
					}
					materielImportApplyAction.deleteMaterielImportApply(new Request(CommonVars.getCurrUser()), materielImportApply);
					tableModel.deleteRow(materielImportApply);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnBrowse	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportApplyList dgImportApplyList = new DgImportApplyList();
					dgImportApplyList.setTableHeadModel(tableModel);
					dgImportApplyList.setDataState(DataState.BROWSE);
					dgImportApplyList.showData();
					dgImportApplyList.setVisible(true);
				}
			});
		}
		return btnBrowse;
	}
	/**
	 * 显示表格数据 
	 * @param list
	 */
	private void initTable(List list){
		tableModel = new JTableListModel(this.tableImportApply, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("仓库编号", "warehouseCode.name", 100));
						list.add(addColumn("申报状态", "declareState", 150));
						list.add(addColumn("申请原因", "applyReason", 100));
						list.add(addColumn("备注", "note", 200));			
						return list;
					}
				});
		tableImportApply.getColumnModel().getColumn(2).setCellRenderer(
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
	}

	/**
	 * This method initializes panel	
	 * 	
	 * @return java.awt.Panel	
	 */
	private JPanel getPanel() {
		if (panel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			panel.add(getJScrollPane(), gridBagConstraints);
		}
		return panel;
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
		if (tableImportApply == null) {
			tableImportApply = new JTable();
			tableImportApply.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if(e.getClickCount()==2){
						btnEdit.doClick();
					}
				}
			});
		}
		return tableImportApply;
	}

	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmMaterielImportApply.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes btnSend	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSend() {
		if (btnSend == null) {
			btnSend = new JButton();
			btnSend.setText("\u6d77\u5173\u7533\u62a5");
			btnSend.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMaterielImportApply.this,
								"请选择要海关申报的资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmMaterielImportApply.this,
							"确定要进行海关申报吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					MaterielImportApply materielImportApply = (MaterielImportApply) tableModel
							.getCurrentRow();
					materielImportApply = materielImportApplyAction
							.applyMaterielImportApply(new Request(CommonVars
									.getCurrUser()), materielImportApply);
					String declareInfo = "";
					if (DeclareState.PROCESS_EXE.equals(materielImportApply
							.getDeclareState())) {
						declareInfo = ("保税物料进口申请"
								+ materielImportApply.getWarehouseCode().getName() + " 申报成功！");
					} else if (DeclareState.APPLY_POR
							.equals(materielImportApply.getDeclareState())) {
						declareInfo = ("保税物料进口申请"
								+ materielImportApply.getWarehouseCode().getName() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA
							.equals(materielImportApply.getDeclareState())) {
						declareInfo = ("保税物料进口申请"
								+ materielImportApply.getWarehouseCode().getName() + " 正在等待审批！");
					}
					tableModel.updateRow(materielImportApply);
					JOptionPane
							.showMessageDialog(FmMaterielImportApply.this,
									declareInfo, "提示！",
									JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnSend;
	}

	/**
	 * This method initializes btnReturnProcess	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReturnProcess() {
		if (btnReturnProcess == null) {
			btnReturnProcess = new JButton();
			btnReturnProcess.setText("\u56de\u6267\u5904\u7406");
			btnReturnProcess.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmMaterielImportApply.this,
								"请选择要回执处理的单证核销资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							FmMaterielImportApply.this, "确定要进行回执处理吗？",
							"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					MaterielImportApply materielImportApply = (MaterielImportApply) tableModel
							.getCurrentRow();
					String serviceType = "";
					String applyType = BlsServiceType.IMPORT_APPLY;
					BlsReceiptResult blsReceiptResult = BlsMessageHelper
							.getInstance().showBlsReceiptFile(
									serviceType,
									materielImportApply.getId());
					if (blsReceiptResult == null) {
						return;
					}
					materielImportApply = materielImportApplyAction
							.processMaterielImportApply(new Request(CommonVars
									.getCurrUser()),
									materielImportApply,
									blsReceiptResult);
					String declareInfo = "";
					if (DeclareState.PROCESS_EXE
							.equals(materielImportApply
									.getDeclareState())) {
						declareInfo = ("保税物料进口申请"
								+ materielImportApply.getWarehouseCode().getName() + " 申报成功！");
					} else if (DeclareState.APPLY_POR
							.equals(materielImportApply
									.getDeclareState())) {
						declareInfo = ("保税物料进口申请"
								+ materielImportApply.getWarehouseCode().getName()  + " 申报失败！");
					} else if (DeclareState.WAIT_EAA
							.equals(materielImportApply
									.getDeclareState())) {
						declareInfo = ("保税物料进口申请"
								+ materielImportApply.getWarehouseCode().getName()  + " 正在等待审批！");
					}
					tableModel.updateRow(materielImportApply);
//					setState();
					JOptionPane.showMessageDialog(
							FmMaterielImportApply.this, declareInfo,
							"提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnReturnProcess;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
