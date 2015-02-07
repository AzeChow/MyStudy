package com.bestway.common.client.erpbill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.TempCustomOrderChangContract;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.TempEntBomVersion;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 2008-09-22
 * 
 * @author lxr
 * 
 */
@SuppressWarnings("unchecked")
public class DgCustomOrderToContract extends JDialogBase {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private OrderCommonAction orderCommonAction = null;
	
	private MaterialManageAction materialManageAction = null;
	
	/**
	 * 报关类型 1:电子手册 2：纸制手册 3：纸制手册电子化
	 */
	private Integer customType = null; // @jve:decl-index=0:
	/**
	 * 存放所选需要转的订单表
	 */
	private List listCustomOrderHead = null; // @jve:decl-index=0:
	/**
	 * 存放订单体
	 */
	private List listCustomOrderDetail = new ArrayList(); // @jve:decl-index=0:
	/**
	 * 存放订单表表
	 */
	private List orderDtailsTempList = null; // @jve:decl-index=0:

	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel21 = null;
	private JPanel jPanel11 = null;
	private JLabel jLabel17 = null;

	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane11 = null;
	private JScrollPane jScrollPane = null;

	private JTable tbOrderHead = null;
	private JTable tbOrderDetails = null;
	private JTable tbContract = null;
	private JLabel jLabel = null;

	private JToolBar jJToolBarBar = null;

	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;

	private JLabel lbHeadText = null;
	private JLabel jLabel19 = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;

	private JComboBox cbbContract = null;

	private JTableListModel tableMode = null;
	private JTableListModel tableModelOrderHead = null;
	private JTableListModel tableModelOrderDetail = null;
	private JTableListModel tableModelContract = null;

	private JRadioButton rbSellOrder = null;
	private JRadioButton rbStockOrder = null;

	private ButtonGroup bgOrderType = null; // @jve:decl-index=0:
	private ButtonGroup bgSelect = null; // @jve:decl-index=0:
	/**
	 * 订单类型(0:销售订单 1:采购订单)
	 */
	private Integer orderType = 0; // @jve:decl-index=0:
	/**
	 * 执行步骤
	 */
	private int step = 1;

	private JButton btnSelectAll = null;

	private JButton btnNotSelectAll = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JButton btnSelectAll1 = null;

	private JButton btnNotSelectAll1 = null;

	/**
	 * @param
	 */
	public DgCustomOrderToContract() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		materialManageAction =  (MaterialManageAction) CommonVars
		.getApplicationContext().getBean("materialManageAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(736, 468);
		this.setTitle("转合同");
		this.setContentPane(getJContentPane());
		this.cbbContract.removeAllItems();

	}

	/**
	 * 初始化组件
	 */
	private void initUIComponents() {
		getButtonGroup();
		getButtonGroups();
	}

	private ButtonGroup getButtonGroup() {
		if (bgOrderType == null) {
			bgOrderType = new ButtonGroup();
			bgOrderType.add(getRbSellOrder());
			bgOrderType.add(getRbStockOrder());
		}
		return bgOrderType;
	}

	private ButtonGroup getButtonGroups() {
		if (bgSelect == null) {
			bgSelect = new ButtonGroup();
			bgSelect.add(getBtnSelectAll());
			bgSelect.add(getBtnNotSelectAll());
		}
		return bgSelect;
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			List contracts = orderCommonAction.findContractByProcessExe(
					new Request(CommonVars.getCurrUser()), customType);

			cbbContract.addItem(null);
			if (customType > 1) {
				for (int i = 0; i < contracts.size(); i++) {
					Contract contract = (Contract) contracts.get(i);
					String copEmsNo = contract.getCopEmsNo();
					cbbContract.addItem(copEmsNo);
				}
			} else if (customType == 1) {
				for (int i = 0; i < contracts.size(); i++) {
					DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
					String copTrNo = contract.getCopTrNo();
					cbbContract.addItem(copTrNo);
				}
			}
			getCustomOrderHead();
		}
		super.setVisible(isFlag);
	}

	/**
	 * 得到订单单头
	 */
	private void getCustomOrderHead() {
		orderType = getOrderType();
		listCustomOrderHead = orderCommonAction
				.findCustomOrderHeadByNotChangeToContract(new Request(
						CommonVars.getCurrUser()), orderType);
		if (listCustomOrderHead != null && listCustomOrderHead.size() > 0) {
			initTableOrderHead(listCustomOrderHead);
		} else {
			initTableOrderHead(listCustomOrderHead);
		}
	}

	/**
	 * 获得订单类型
	 * 
	 * @return
	 */
	private Integer getOrderType() {
		if (this.rbSellOrder.isSelected()) {
			return 0;
		} else if (this.rbStockOrder.isSelected()) {
			return 1;
		}
		return 0;
	}

	/**
	 * 初始化订单头Table
	 */
	private void initTableOrderHead(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("订单号码", "billCode", 150));
				list.add(addColumn("订单日期", "orderDate", 80));
				list.add(addColumn("订单类型", "ordertype", 80));
				list.add(addColumn("订单客户", "customer.name", 250));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelOrderHead = new JTableListModel(tbOrderHead, list,
				jTableListModelAdapter);
		tbOrderHead.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbOrderHead.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));

		tbOrderHead.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					private String castValue(Object value) {
						if ("0".equals(value.toString())) {
							return "销售订单";
						} else {
							return "采购订单";
						}
					}
				});
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JCheckBox cb = new JCheckBox();
		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				value = new Boolean(false);
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
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
			return cb.isSelected();
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof CustomOrder) {
				CustomOrder temp = (CustomOrder) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof CustomOrderDetail) {
				CustomOrderDetail temp = (CustomOrderDetail) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			} else if (obj instanceof TempCustomOrderChangContract) {
				TempCustomOrderChangContract temp = (TempCustomOrderChangContract) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 初始化订单体Table
	 */
	private void initTableOrderDetails(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("订单货品", "materiel.ptNo", 120));
				list.add(addColumn("<html><body><font color='blue'>成品版本号</font></body></html>", "version", 70));
				list.add(addColumn("单位", "calUnit.name", 60));
				list.add(addColumn("币别", "curr.name", 60));
				list.add(addColumn("单价", "unitPrice", 80));
				list.add(addColumn("数量", "amount", 100));
				list.add(addColumn("总价", "totalPrice", 100));
				list.add(addColumn("未转合同数量", "notContractNum", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelOrderDetail = new JTableListModel(tbOrderDetails, list,
				jTableListModelAdapter);
		tbOrderDetails.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbOrderDetails.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
		jTableListModelAdapter.setEditableColumn(3);
		JComboBox cbb = new JComboBox();
		tbOrderDetails.getColumnModel().getColumn(3).setCellEditor(
				new DefaultCellEditor(cbb) 
		{
			private static final long serialVersionUID = 1L;
			public Component getTableCellEditorComponent(JTable table,
					Object value, boolean isSelected, int row,
					int column) {
				JComboBox cbb = (JComboBox) super.getComponent();
				System.out.println(value);
				cbb.removeAllItems();
				Object tmp = table.getModel().getValueAt(row, 2);
				// 料号
				String ptNo = tmp == null ? "" : tmp.toString();
				// bom管理类型
				int bomStructureType = 0;
				// 查询出该料号的所有版本
				List<TempEntBomVersion> list = materialManageAction
						.findMaterialBomVersion(new Request(
								CommonVars.getCurrUser(), true), ptNo,
								bomStructureType);
				if (list != null) {
					List<String> tmpList = new ArrayList<String>(list.size());
					for (int i = 0; i < list.size(); i++) {
						tmpList.add(list.get(i).getVersion() + "");
					}
					cbb.setModel(new DefaultComboBoxModel(tmpList
							.toArray()));
				}
				
				return super.getTableCellEditorComponent(table, value,
						isSelected, row, column);
			}
		});
		
		tbOrderDetails.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Object version = tbOrderDetails.getColumnModel().getColumn(3).getCellEditor().getCellEditorValue();
				if(version != null && !version.equals("")) {
					CustomOrderDetail o = (CustomOrderDetail) tableModelOrderDetail.getCurrentRow();
					CustomOrderDetail tmp = (CustomOrderDetail) o.clone();
					tmp.setVersion(Integer.parseInt(version.toString()));
					tableModelOrderDetail.updateRow(tmp);
				}
				return super.getTableCellRendererComponent(table,
						value, isSelected, hasFocus, row, column);
			}
		});
	}
	
	/**
	 * 初始化生成的合同内容Table
	 */
	private void initTableOrderToContract(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("选择", "isSelected", 40));
				list.add(addColumn("料号", "ptNo", 100));
				list.add(addColumn("备案号", "seqNum", 40));
				list.add(addColumn("商品编码", "code.code", 80));
				list.add(addColumn("名称", "name", 100));
				list.add(addColumn("规格", "spec", 100));
				list.add(addColumn("版本号", "version", 40));
				list.add(addColumn("单位", "unit.name", 50));
				list.add(addColumn("单价", "unitPrice", 70));
				list.add(addColumn("数量", "notContractNum", 70));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelContract = new JTableListModel(tbContract, list,
				jTableListModelAdapter);
		tbContract.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbContract.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));
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
			jContentPane.setPreferredSize(new Dimension(300, 200));
			jContentPane.add(getJJToolBarBar(), BorderLayout.SOUTH);
			jContentPane.add(getJPanel11(), BorderLayout.NORTH);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 执行订单转合同
	 * 
	 * @author ower
	 * 
	 */
	class ChangeToContractThread extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				try {
					// 转合同
					String emsNo = cbbContract.getSelectedItem().toString()
							.trim();
					// wss:在这里
					List headList = orderCommonAction.ChangeToContract(
							new Request(CommonVars.getCurrUser()),
							listCustomOrderDetail, customType, emsNo);
					if (headList != null && headList.size() > 0) {
						tableMode.updateRows(headList);
					}
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "订单转合同成功! ", "确认", 1);
					DgCustomOrderToContract.this.dispose();
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(null, "转合同失败! "+ ex.getMessage(), "确认", 1);
				}				
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	public void setCustomType(Integer customType) {
		this.customType = customType;
	}

	public Integer getCustomType() {
		return customType;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.setSize(new Dimension(201, 165));
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
			jPanel2.add(getJPanel21(), java.awt.BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbOrderDetails());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbOrderDetails
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbOrderDetails() {
		if (tbOrderDetails == null) {
			tbOrderDetails = new JTable();
		}
		return tbOrderDetails;
	}

	/**
	 * This method initializes jPanel51
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			jPanel21 = new JPanel();
			jPanel21.setLayout(fl);
			jPanel21.setSize(new Dimension(310, 50));
			jPanel21.add(getBtnSelectAll(), null);
			jPanel21.add(getBtnNotSelectAll(), null);
		}
		return jPanel21;
	}

	/**
	 * 选中所有 or 清除所有选择
	 * 
	 * @param isAll
	 */
	private void setSelectedAll(boolean isAll) {

		if (step == 1) {
			List dlist = tableModelOrderHead.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof CustomOrder) {
					CustomOrder temp = (CustomOrder) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModelOrderHead.getTable().repaint();
		} else if (step == 2) {
			List dlist = tableModelOrderDetail.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof CustomOrderDetail) {
					CustomOrderDetail temp = (CustomOrderDetail) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModelOrderDetail.getTable().repaint();
		} else if (step == 3) {
			List dlist = tableModelContract.getList();
			for (int i = 0; i < dlist.size(); i++) {
				Object obj = dlist.get(i);
				if (obj instanceof TempCustomOrderChangContract) {
					TempCustomOrderChangContract temp = (TempCustomOrderChangContract) obj;
					temp.setIsSelected(isAll);
				}
			}
			tableModelContract.getTable().repaint();
		}

	}

	/**
	 * This method initializes jPanel41
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setSize(new Dimension(210, 143));
			jPanel3.add(getJScrollPane11(), java.awt.BorderLayout.CENTER);
			jPanel3.add(getJPanel21(), java.awt.BorderLayout.NORTH);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane11
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane11() {
		if (jScrollPane11 == null) {
			jScrollPane11 = new JScrollPane();
			jScrollPane11.setViewportView(getTbContract());
		}
		return jScrollPane11;
	}

	/**
	 * This method initializes tbContract
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbContract() {
		if (tbContract == null) {
			tbContract = new JTable();
		}
		return tbContract;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			FlowLayout fl2 = new FlowLayout();
			fl2.setHgap(3);
			fl2.setVgap(1);
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setLayout(fl2);
			jJToolBarBar.setBounds(new Rectangle(56, 217, 231, 36));
			jJToolBarBar.add(getBtnPrevious());
			jJToolBarBar.add(getBtnNext());
			jJToolBarBar.add(getBtnOk());
			jJToolBarBar.add(getBtnCancel());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setEnabled(false);
			btnPrevious.setText("上一步");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					step--;
					mupStep(step);

				}
			});
			btnPrevious.setEnabled(false);
		}
		return btnPrevious;
	}

	/**
	 * 执行上一步操作
	 * 
	 * @param step
	 */

	private void mupStep(int step) {
		if (step == 1) {
			getJContentPane().remove(getJPanel2());
			getJContentPane().add(getJPanel(), BorderLayout.CENTER);
			initTableOrderHead(listCustomOrderHead);
			getBtnNext().setEnabled(true);
			getBtnPrevious().setEnabled(false);
			lbHeadText.setText("第一步：选择要生成合同的订单 ");
			this.repaint();

		} else if (step == 2) {
			getJContentPane().remove(getJPanel3());
			getJContentPane().add(getJPanel2(), BorderLayout.CENTER);
			jPanel2.add(getJPanel21(), BorderLayout.NORTH);
			initTableOrderDetails(orderDtailsTempList);
			getBtnNext().setEnabled(true);
			getBtnOk().setEnabled(false);
			lbHeadText.setText("第二步：选择所要转的订单明细");
			this.repaint();

		}
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setPreferredSize(new Dimension(50, 28));
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addStep(step);
				}
			});
		}
		return btnNext;
	}

	/**
	 * 执行下一步操作
	 */
	private void addStep(int step) {
		if (step == 1) {
			if (cbbContract.getSelectedItem() == null
					|| "".equals(cbbContract.getSelectedItem())) {
				JOptionPane.showMessageDialog(DgCustomOrderToContract.this,
						"请选择合同的企业编号！", "提示！", JOptionPane.WARNING_MESSAGE);
				return;
			}
			List orderHeadTempList = new ArrayList();
			List head = this.tableModelOrderHead.getList();
			for (int i = 0; i < head.size(); i++) {
				CustomOrder ap = (CustomOrder) head.get(i);
				if (ap.getIsSelected() != null && ap.getIsSelected()) {
					orderHeadTempList.add(ap);
				}
			}
			if (orderHeadTempList.size() == 0) {
				JOptionPane.showMessageDialog(DgCustomOrderToContract.this,
						"没有选择需要转的订单！", "提示！", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 根据表选择的表头资料获取对应的表体明细
			orderDtailsTempList = orderCommonAction
					.findCustomOrderDetailByNotChangeToContract(new Request(
							CommonVars.getCurrUser()), orderHeadTempList);
			getJContentPane().remove(getJPanel());
			getJContentPane().add(getJPanel2(), BorderLayout.CENTER);

			jPanel2.add(getJPanel21(), BorderLayout.NORTH);
			initTableOrderDetails(orderDtailsTempList);
			getBtnPrevious().setEnabled(true);
			getBtnNext().setEnabled(true);
			lbHeadText.setText("第二步：选择所要转的订单明细");
			this.setVisible(true);
			this.repaint();
			this.step++;
		} else if (step == 2) {

			List orderDetailTempList = new ArrayList();
			List detail = this.tableModelOrderDetail.getList();
			for (int i = 0; i < detail.size(); i++) {
				CustomOrderDetail ap = (CustomOrderDetail) detail.get(i);
				if (ap.getIsSelected() != null && ap.getIsSelected()) {
					orderDetailTempList.add(ap);
				}
			}
			if (orderDetailTempList.size() == 0) {
				JOptionPane.showMessageDialog(DgCustomOrderToContract.this,
						"没有选择需要转的订单明细！", "提示！", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 根据内部归应关系找到相应的报关资料
			// 这里1
			List contractList = orderCommonAction
					.CustomOrderToContractByBgCommInfo(new Request(CommonVars
							.getCurrUser()), orderDetailTempList, customType);
			getJContentPane().remove(getJPanel2());
			getJContentPane().add(getJPanel3(), BorderLayout.CENTER);

			jPanel3.add(getJPanel21(), BorderLayout.NORTH);
			// 这里2
			initTableOrderToContract(contractList);
			getBtnPrevious().setEnabled(true);
			getBtnNext().setEnabled(false);
			getBtnOk().setEnabled(true);
			lbHeadText.setText("第三步：选择所要转的合同明细");
			this.setVisible(true);
			this.repaint();
			this.step++;
		}
	}

	/**
	 * This method initializes btnOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setEnabled(false);
			btnOk.setText("执行");
			btnOk.setPreferredSize(new Dimension(50, 28));
			btnOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					fillData();
					new ChangeToContractThread().start();
				}
			});
		}
		return btnOk;
	}

	/**
	 * 获得已选择的订单明细
	 */
	private void fillData() {

		if (this.tableModelOrderDetail == null) {
			return;
		} else {
			List list = this.tableModelOrderDetail.getList();
			for (int i = 0; i < list.size(); i++) {
				CustomOrderDetail bill = (CustomOrderDetail) list.get(i);
				if (bill.getIsSelected() != null && bill.getIsSelected()) {
					listCustomOrderDetail.add(bill);
				}
			}
		}

	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setPreferredSize(new Dimension(50, 28));
			btnCancel.setText("退出");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomOrderToContract.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel19 = new JLabel();
			jLabel19.setIcon(new ImageIcon(getClass().getResource(
					"/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel19.setText("");
			lbHeadText = new JLabel();
			lbHeadText.setFont(new Font("Dialog", Font.BOLD, 20));
			lbHeadText.setText("第一步：请选择要生成合同的订单 ");
			lbHeadText.setForeground(new Color(255, 153, 0));
			jLabel17 = new JLabel();
			jLabel17
					.setIcon(new ImageIcon(
							getClass()
									.getResource(
											"/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jLabel17.setText("");
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.setBounds(new Rectangle(133, 17, 463, 41));
			jPanel11.setBackground(Color.white);
			jPanel11.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel11.add(jLabel17, java.awt.BorderLayout.WEST);
			jPanel11.add(lbHeadText, java.awt.BorderLayout.CENTER);
			jPanel11.add(jLabel19, java.awt.BorderLayout.EAST);
		}
		return jPanel11;
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
			jPanel.add(getJPanel1(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jLabel.setText("合同的企业编号");
			jLabel.setBounds(new Rectangle(39, 22, 93, 19));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(40, 80));
			jPanel1.add(getJPanel4(), null);
			jPanel1.add(getJPanel5(), null);
			jPanel1.add(getBtnSelectAll1(), null);
			jPanel1.add(getBtnNotSelectAll1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbOrderHead());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbOrderHead
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbOrderHead() {
		if (tbOrderHead == null) {
			tbOrderHead = new JTable();
		}
		return tbOrderHead;
	}

	/**
	 * This method initializes cbbContract
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbContract() {
		if (cbbContract == null) {
			cbbContract = new JComboBox();
			cbbContract.setBounds(new Rectangle(148, 17, 195, 23));
		}
		return cbbContract;
	}

	/**
	 * This method initializes rbSellOrder
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbSellOrder() {
		if (rbSellOrder == null) {
			rbSellOrder = new JRadioButton();
			rbSellOrder.setText("销售订单");
			rbSellOrder.setBounds(new Rectangle(60, 22, 73, 20));
			rbSellOrder.setSelected(true);
			rbSellOrder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbSellOrder.isSelected()) {
						getCustomOrderHead();
					}
				}
			});
		}
		return rbSellOrder;
	}

	/**
	 * This method initializes rbStockOrder
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbStockOrder() {
		if (rbStockOrder == null) {
			rbStockOrder = new JRadioButton();
			rbStockOrder.setText("采购订单");
			rbStockOrder.setBounds(new Rectangle(194, 22, 73, 20));
			rbStockOrder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbStockOrder.isSelected()) {
						getCustomOrderHead();
					}
				}
			});
		}
		return rbStockOrder;
	}

	public JTableListModel getTableMode() {
		return tableMode;
	}

	public void setTableMode(JTableListModel tableMode) {
		this.tableMode = tableMode;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(364, 11, 61, 21);
			btnSelectAll.setText("全选");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setSelectedAll(true);
				}
			});
		}
		return btnSelectAll;
	}

	/**
	 * This method initializes btnNotSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll() {
		if (btnNotSelectAll == null) {
			btnNotSelectAll = new JButton();
			btnNotSelectAll.setBounds(431, 11, 61, 21);
			btnNotSelectAll.setText("全否");
			btnNotSelectAll
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setSelectedAll(false);
						}
					});
		}
		return btnNotSelectAll;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new Rectangle(33, 4, 304, 48));
			jPanel4.setBorder(BorderFactory.createTitledBorder(null,
					"\u8bf7\u9009\u62e9\u8ba2\u5355\u7c7b\u578b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel4.add(getRbSellOrder(), null);
			jPanel4.add(getRbStockOrder(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(new Rectangle(344, 6, 369, 46));
			jPanel5
					.setBorder(BorderFactory
							.createTitledBorder(
									null,
									"\u8bf7\u9009\u62e9\u5408\u540c\u7684\u4f01\u4e1a\u7f16\u53f7",
									TitledBorder.DEFAULT_JUSTIFICATION,
									TitledBorder.DEFAULT_POSITION, new Font(
											"Dialog", Font.PLAIN, 12),
									new Color(51, 51, 51)));
			jPanel5.add(getCbbContract(), null);
			jPanel5.add(jLabel, null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes btnSelectAll1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll1() {
		if (btnSelectAll1 == null) {
			btnSelectAll1 = new JButton();
			btnSelectAll1.setBounds(new Rectangle(11, 55, 58, 22));
			btnSelectAll1.setText("全选");
			btnSelectAll1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setSelectedAll(true);
						}
					});
		}
		return btnSelectAll1;
	}

	/**
	 * This method initializes btnNotSelectAll1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelectAll1() {
		if (btnNotSelectAll1 == null) {
			btnNotSelectAll1 = new JButton();
			btnNotSelectAll1.setBounds(new Rectangle(77, 55, 58, 22));
			btnNotSelectAll1.setText("全否");
			btnNotSelectAll1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							setSelectedAll(false);
						}
					});
		}
		return btnNotSelectAll1;
	}
} // @jve:decl-index=0:visual-constraint="136,23"

class MyTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int myRow = -1, myCol = -1;
	TableCellEditor myEditor;

	public void setComboCell(int r, int c, TableCellEditor ce) {
		this.myRow = r;
		this.myCol = c;
		this.myEditor = ce;
	}

	@Override
	public TableCellEditor getCellEditor(int row, int column) {
		System.out.println(row + "," + column + ";" + myRow + "," + myCol + ","
				+ myEditor);
		if (row == myRow && column == myCol && myEditor != null)
			return myEditor;
		return super.getCellEditor(row, column);
	}

}

class MyComboBoxEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyComboBoxEditor(String[] items) {
		super(new JComboBox(items));
	}
}
