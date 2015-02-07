package com.bestway.common.client.message;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.BcsBusinessType;
import com.bestway.common.constant.CspProcessResult;
import com.bestway.common.message.action.CspMessageAction;
import com.bestway.common.message.entity.CspReceiptResult;
import com.bestway.ui.winuicontrol.JDialogBase;

@SuppressWarnings({"unchecked","serial"})
public class DgCspReceiptFile extends JDialogBase {

	private JPanel jContentPane = null;

	protected JTableListModel tableModelNotProcess;

	private JToolBar jToolBar = null;

	private JButton btnOk = null;

	private JScrollPane jScrollPane = null;

	protected JTable tbNotProcess = null;

	private JPanel jPanel = null;

	private JButton btnCancel = null;

	protected CspMessageAction cspMessageAction = null; // @jve:decl-index=0:

	protected String sysType; // @jve:decl-index=0:

	protected String copEmsNo; // @jve:decl-index=0:

	private boolean isOK = false;

	private JButton btnSelectAll = null;

	private JButton btnNotSelect = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbNotProcessDetail = null;

	public boolean isOK() {
		return isOK;
	}

	public void setCopEmsNo(String copEmsNo) {
		this.copEmsNo = copEmsNo;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public CspMessageAction getCspMessageAction() {
		return cspMessageAction;
	}

	public void setCspMessageAction(CspMessageAction cspMessageAction) {
		this.cspMessageAction = cspMessageAction;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgCspReceiptFile() {
		super();
		initialize();
	}

	public void setVisible(boolean b) {
		if (b) {
			List list = this.getDataSource();
			this.initNotProcessTable(list);
		}
		super.setVisible(b);
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	protected List getDataSource() {
		return cspMessageAction.findNotProcessReturnFile(new Request(CommonVars
				.getCurrUser()), sysType, copEmsNo);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(611, 416));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("回执信息");
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJToolBar(), BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	protected void initNotProcessTable(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 100));
				// list.add(addColumn("经营单位代码", "tradeCode", 100));
				list.add(addColumn("企业内部编号", "copEmsNo", 160));
				// list.add(addColumn("核查/报核次数", "colDcrTime", 100));
				list.add(addColumn("数据中心统一编号", "seqNo", 100));
				list.add(addColumn("海关分册编号", "emsNo", 80));
				list.add(addColumn("处理结果", "chkMark", 100));
				list.add(addColumn("通知时间", "formatedNoticeDate", 120));
				list.add(addColumn("文件名称", "fileName", 300));
				// list.add(addColumn("数据类型", "sysType", 80));
				list.add(addColumn("申报类型", "declareType", 80));

				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelNotProcess = new JTableListModel(tbNotProcess, list,
				jTableListModelAdapter);

		tbNotProcess.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbNotProcess.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		tbNotProcess.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : CspProcessResult
								.getResultDesc(value.toString().trim()));
						return this;
					}

				});
	}

	/**
	 * 编辑列
	 */
	protected class CheckBoxEditor extends DefaultCellEditor implements
			ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb
						.setSelected(Boolean.valueOf(value.toString())
								.booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof CspReceiptResult) {
				CspReceiptResult temp = (CspReceiptResult) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("确定");
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String strBcsBusinessTypeDesc = BcsBusinessType
							.getBcsBusinessTypeDesc(sysType);
					if(BcsBusinessType.EMS_POR_WJ.equals(sysType)) {
						strBcsBusinessTypeDesc = "结转申请单";
					}
					if (tableModelNotProcess.getList().size() <= 0) {
						JOptionPane
								.showMessageDialog(
										DgCspReceiptFile.this,
										"企业内部编号为:"
												+ copEmsNo
												+ "的"
												+ strBcsBusinessTypeDesc
												+ "没有回执报文", "提示",
										JOptionPane.INFORMATION_MESSAGE);

					} else {
						if (!checkIsComplete()) {
							if (JOptionPane
									.showConfirmDialog(
											DgCspReceiptFile.this,
											"企业内部编号为:"
													+ copEmsNo
													+ "的"
													+ strBcsBusinessTypeDesc
													+ "的报文没有接收完成,你是否确定要处理回执",
											"提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
								return;
							}
						}
						isOK = true;
					}
					dispose();
				}
			});
		}
		return btnOk;
	}

	private boolean checkIsComplete() {
		List list = tableModelNotProcess.getList();
		if (cspMessageAction.checkIsSuccess(new Request(CommonVars
				.getCurrUser()), sysType, list)) {
			return true;
		}
		if (cspMessageAction.checkIsFailure(new Request(CommonVars
				.getCurrUser()), sysType, list)) {
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbNotProcess());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotProcess() {
		if (tbNotProcess == null) {
			tbNotProcess = new JTable();
			tbNotProcess.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							showNotProcessDetailData();
						}
					});
		}
		return tbNotProcess;
	}

	private void showNotProcessDetailData() {
		List list = new ArrayList();
		if (tableModelNotProcess != null) {
			CspReceiptResult cspReceiptResult = (CspReceiptResult) tableModelNotProcess
					.getCurrentRow();
			if (cspReceiptResult != null
					&& cspReceiptResult.getReceiptResultDetailList() != null) {
				list = cspReceiptResult.getReceiptResultDetailList();
			}
		}
		this.initNotProcessDetailTable(list);
	}

	private void initNotProcessDetailTable(List list) {
		new JTableListModel(tbNotProcessDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("信息明细", "resultInfo", 500));
						return list;
					}
				});
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(0);
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelect(), null);
			jPanel.add(getBtnOk(), null);
			jPanel.add(getBtnCancel(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * 抓取所有选中的回执文件
	 * 
	 * @return
	 */
	public List getReturnFile() {
		List lsResult = new ArrayList();
		// for(int i=0;i<tableModelNotProcess.getRowCount();i++){
		// CspReceiptResult
		// result=(CspReceiptResult)tableModelNotProcess.getDataByRow(i);
		// if(result.getIsSelected()!=null&&result.getIsSelected()){
		// lsResult.add(result);
		// }
		// }
		for (int i = 0; i < tableModelNotProcess.getList().size(); i++) {
			CspReceiptResult result = (CspReceiptResult) tableModelNotProcess
					.getList().get(i);
			if (result.getIsSelected() != null && result.getIsSelected()) {
				lsResult.add(result);
			}
		}
		return lsResult;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelect() {
		if (btnNotSelect == null) {
			btnNotSelect = new JButton();
			btnNotSelect.setText("不选");
			btnNotSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(false);
				}
			});
		}
		return btnNotSelect;
	}

	/**
	 * 选中所有 or 清除所有选择
	 */
	private void selectAll(boolean isSelected) {
		if (this.tableModelNotProcess == null) {
			return;
		}
		List list = tableModelNotProcess.getList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof CspReceiptResult) {
				CspReceiptResult temp = (CspReceiptResult) list.get(i);
				temp.setIsSelected(new Boolean(isSelected));
			}
		}
		tableModelNotProcess.updateRows(list);
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(200);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerSize(5);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbNotProcessDetail());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbNotProcessDetail() {
		if (tbNotProcessDetail == null) {
			tbNotProcessDetail = new JTable();
		}
		return tbNotProcessDetail;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
