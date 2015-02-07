package com.bestway.common.client.erpbill;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.CustomOrder;
import com.bestway.common.erpbill.entity.CustomOrderDetail;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

/**
 * 2008-09-26
 * 
 * @author lxr checked by 陈井彬 date:2008.10.25
 */
public class FmCustomOrder extends JInternalFrameBase {
	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JScrollPane jScrollPane = null;
	private JTable tbOrder = null;

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
	 * 退出按钮
	 */
	private JButton btnExit = null;

	/**
	 * 订单表格模型
	 */
	private JTableListModel tableModelOrder = null;

	/**
	 * 订单操作接口
	 */
	private OrderCommonAction orderCommonAction = null;

	/**
	 * 生效按钮
	 */
	private JButton btnEffect = null;

	/**
	 * 回卷按钮
	 */
	private JButton btnCancel = null;

	/**
	 * 导入按钮
	 */
	private JButton btnInput = null;

	/**
	 * 刷新按钮
	 */
	private JButton btnRefurbish = null;

	/**
	 * 料件成品及单耗表
	 */
	private JButton btnImgOrExg = null;
	private JToolBar jJToolBarBar = null;

	/**
	 * 生成合同
	 */
	private JButton btnChangToContract = null;
	private JPanel jPanel1 = null;

	/**
	 * 浏览按钮
	 */
	private JButton btnBrowse = null;
	private JPanel jPanel = null;

	/**
	 * 客户订单参数设定Entity
	 */
	private Customparames parames = null;
	/**
	 * 存放定单表头List
	 */
	private List<CustomOrder> listCustomOrder = new ArrayList<CustomOrder>(); // @jve:decl-index=0:
	/**
	 * 存放错误资料list
	 */
	private List<CustomOrder> errList = new ArrayList<CustomOrder>(); // @jve:decl-index=0:
	/**
	 * 存放正确资料list
	 */
	private List<CustomOrder> okList = new ArrayList<CustomOrder>(); // @jve:decl-index=0:

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 存放所有的订单明细
	 */
	private Map<String, String> mapAllDetail = new HashMap<String, String>();

	/**
	 * This method initializes 构造函数
	 */
	public FmCustomOrder() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		parames = orderCommonAction.findCustomparames(new Request(CommonVars
				.getCurrUser()));
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkCustomOrder(new Request(CommonVars
				.getCurrUser()));
		initialize();
		this.getTbOrder().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) getTbOrder()
								.getModel();

						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception ee) {
							ee.printStackTrace();
						}
					}
				});
	}

	/**
	 * This method initializes this 初始化
	 */
	private void initialize() {
		this.setSize(797, 473);
		this.setContentPane(getJContentPane());
		this.setTitle("客户订单管理");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						pnCommonQueryPage.setInitState();
					}
				});

	}

	/**
	 * 设置按钮状态
	 */
	private void setState() {
		if (tableModelOrder == null) {
			return;
		}
		CustomOrder order = (CustomOrder) tableModelOrder.getCurrentRow();
		boolean flag = order.getIfok();

		btnEdit.setEnabled(!flag);
		btnDelete.setEnabled(!flag);
		btnEffect.setEnabled(!flag);
		btnCancel.setEnabled(flag);

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
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrower());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getSetcs());
			jToolBar.add(getBtnok());
			jToolBar.add(getBtncancel());
			jToolBar.add(getImportpo());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbOrder());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbOrder() {
		if (tbOrder == null) {
			tbOrder = new JTable();
			tbOrder.setRowHeight(25);
			tbOrder.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						DgCustomOrder dg = new DgCustomOrder();
						dg.settableModelOrder(tableModelOrder);
						dg.setDataState(DataState.BROWSE);
						dg.setVisible(true);
					}
				}
			});
		}
		return tbOrder;
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
			btnAdd.setPreferredSize(new Dimension(60, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					DgCustomOrder dg = new DgCustomOrder();
					dg.settableModelOrder(tableModelOrder);
					dg.setDataState(DataState.ADD);
					dg.setVisible(true);
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
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					CustomOrder customOrder = (CustomOrder) tableModelOrder
							.getCurrentRow();
					if (tableModelOrder.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"请选择你要修改的资料!", "确认!", 2);
						return;
					}
					DgCustomOrder dg = new DgCustomOrder();
					dg.settableModelOrder(tableModelOrder);
					dg.setDataState(DataState.EDIT);
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
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					listCustomOrder = tableModelOrder.getCurrentRows();
					if (listCustomOrder.size() == 0) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"请选择你要删除的资料!", "确认!", 2);
						return;
					}

					CustomOrder customOrder = null;
					List newDelList = new ArrayList();
					List list = new ArrayList();
					for (int i = 0; i < listCustomOrder.size(); i++) {
						customOrder = (CustomOrder) listCustomOrder.get(i);

						if (customOrder.getIfok().equals(new Boolean(true))) {
							list.add(customOrder);
							continue;
						}
						newDelList.add(customOrder);
						customOrder = null;
					}
					String meg = getMesageOderNo(list);
					if (list.size() > 0) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"以下订单已生效不能被删除:\n" + meg, "确认", 2);

					}
					if (newDelList.size() > 0) {
						if (JOptionPane.showConfirmDialog(FmCustomOrder.this,
								"有" + newDelList.size()
										+ "条订单可以删除，确定要删除这些订单吗?\n注意：其表体将一并被删除",
								"确认", 0) == 0) {
							List tempList = tableModelOrder.getList();
							tempList.removeAll(newDelList);
							orderCommonAction.dropordermaster(new Request(
									CommonVars.getCurrUser()), newDelList);
							orderCommonAction.delectCustomOrder(new Request(
									CommonVars.getCurrUser()), newDelList);
							initTable(tempList);
						}
					}

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
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmCustomOrder.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化Table表格
	 */
	private JTableListModel initTable(final List list) {
		JTableListModelAdapter tableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(addColumn("订单号码", "billCode", 100));
				list.add(addColumn("订单日期", "orderDate", 80));
				list.add(addColumn("客户名称", "customer.name", 150));
				list.add(addColumn("是否转厂", "ifzc", 55));
				list.add(addColumn("是否生效", "ifok", 55));
				list.add(addColumn("导入次数", "importcount", 80));
				list.add(addColumn("报关类型", "customType", 80));
				list.add(addColumn("订单总项数", "contractCount", 100));
				list.add(addColumn("已转合同项数", "isChangeToContract", 100));
				list.add(addColumn("已转企业内部编号", "copEmsNo", 150));
				list.add(addColumn("", "id", 0));
				return list;
			}
		};

		tableModelOrder = new JTableListModel(tbOrder, list,
				tableListModelAdapter);
		tableModelOrder.setAllowSetValue(true);
		tbOrder.getColumnModel().getColumn(7).setCellRenderer(
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
						if (value.equals("1")) {
							returnValue = "电子手册";
						} else if (value.equals("2")) {
							returnValue = "纸质手册";
						} else if (value.equals("3")) {
							returnValue = "电子化手册";
						}
						return returnValue;
					}
				});

		tbOrder.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		tbOrder.getColumnModel().getColumn(5).setCellRenderer(
				new TableCheckBoxRender());
		tbOrder.getColumnModel().getColumn(10).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String id = table.getModel().getValueAt(row, 11) == null ? ""
								: ((String) table.getModel()
										.getValueAt(row, 11)).toString();
						super.setText(mapAllDetail.get(id) == null ? ""
								: mapAllDetail.get(id).toString());
						return this;
					}

				});

		TableColumn column = tbOrder.getColumnModel().getColumn(11);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setPreferredWidth(0);
		column.setWidth(0);
		tbOrder
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return tableModelOrder;
	}

	/**
	 * 复选按钮渲染器
	 * 
	 * @author ower
	 * 
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
			this.cb = checkBox;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value != null) {
				if (Boolean.valueOf(value.toString()) instanceof Boolean) {
					cb.setSelected(Boolean.valueOf(value.toString())
							.booleanValue());
				}
			} else {
				cb.setSelected(false);
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
			if (obj instanceof BaseCustomsDeclaration) {
				BaseCustomsDeclaration temp = (BaseCustomsDeclaration) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * This method initializes btnok
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnok() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.setPreferredSize(new Dimension(60, 30));
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					errList.clear();
					okList.clear();
					listCustomOrder = tableModelOrder.getCurrentRows();
					if (listCustomOrder.size() == 0) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"请选择你要生效的资料!", "确认!", 2);
						return;
					}
					initDate();
					if (errList.size() > 0) {
						String mesage = getMesageOderNo(errList);
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"以下订单资料不全或有误：" + mesage, "确认", 2);
					} else {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"订单数据检查通过!", "确认", 2);
					}
					if (okList.size() > 0) {
						new InureDataThread().execute();
					}
				}
			});
		}
		return btnEffect;
	}

	/**
	 * 取得已生效的订单号码
	 * 
	 * @param list
	 * @return
	 */
	private String getMesageOderNo(List list) {
		String mesage = "";
		for (int i = 0; i < list.size(); i++) {
			CustomOrder objs = (CustomOrder) list.get(i);
			String orderNo = objs.getBillCode();
			String sign = ",";
			if (i == list.size() - 1 || list.size() == 1) {
				sign = "";
			}
			mesage = mesage + orderNo + sign;

		}
		return mesage;
	}

	/**
	 * 初始化错误信息
	 */
	private void initDate() {
		for (int i = 0; i < listCustomOrder.size(); i++) {
			CustomOrder customOrder = (CustomOrder) listCustomOrder.get(i);
			int rateSource = customOrder.getRateSource();
			if (validateData1(customOrder, rateSource) == true) {
				errList.add(customOrder);
				continue;
			} else {
				okList.add(customOrder);
			}

		}
	}

	/**
	 * 检查数据逻辑
	 * 
	 * @param customOrder
	 * @param rateSource
	 * @return
	 */
	private boolean validateData1(CustomOrder customOrder, int rateSource) {

		List result = orderCommonAction.findCustomOrderDetail(new Request(
				CommonVars.getCurrUser()), customOrder);
		List listExgBom = orderCommonAction.isExitInCustomBom(new Request(
				CommonVars.getCurrUser()), customOrder, "0");
		List listImgBom = orderCommonAction.isExitInCustomBom(new Request(
				CommonVars.getCurrUser()), customOrder, "2");
		List listExgInner = orderCommonAction.isExitInInner(new Request(
				CommonVars.getCurrUser()), customOrder, "0");
		List listImgInner = orderCommonAction.isExitInInner(new Request(
				CommonVars.getCurrUser()), customOrder, "2");
		List listExgRecords = orderCommonAction.isExitInRecords(new Request(
				CommonVars.getCurrUser()), customOrder, "0");
		List listImgRecords = orderCommonAction.isExitInRecords(new Request(
				CommonVars.getCurrUser()), customOrder, "2");

		if (customOrder.getIfok() == true) {
			return true;
		}

		if (result.size() == 0) {
			return true;
		} else {
			List<CustomOrderDetail> list = new ArrayList<CustomOrderDetail>();
			for (int i = 0; i < result.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) result
						.get(i);
				if (customOrderDetail.getBgamount() == null
						|| customOrderDetail.getBgamount() == 0.0
						|| customOrderDetail.getSalesDate() == null
						|| customOrderDetail.getCalUnit() == null
						|| customOrderDetail.getCurr() == null) {
					list.add(customOrderDetail);
				}
			}
			if (list.size() > 0) {
				return true;
			}
		}
		// if (listExgBom.size() != 0) {
		// return true;
		// }
		// if (listImgBom.size() != 0) {
		// return true;
		// }
		// if (listExgInner.size() != 0) {
		// return true;
		// }
		// if (listImgInner.size() != 0) {
		// return true;
		// }
		// if (listExgRecords.size() != 0) {
		// return true;
		// }
		// if (listImgRecords.size() != 0) {
		// return true;
		// }
		return false;
	}

	/**
	 * 开始生效
	 * 
	 * @author ower
	 * 
	 */
	private class InureDataThread extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在生效订单,请等待...");
			try {
				okList = orderCommonAction.okorder(new Request(CommonVars
						.getCurrUser()), okList);
				tableModelOrder.updateRows(okList);

				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();

			}
			return null;
		}
	}

	/**
	 * 检查数据是否存在BOM中
	 * 
	 * @param customOrder
	 * @param rateSource
	 * @return
	 */
	private boolean validateData(CustomOrder customOrder, int rateSource) {
		String mesage1 = null;
		if (rateSource == 1) {
			mesage1 = "报关常用工厂ＢＯＭ";
		} else if (rateSource == 2) {
			mesage1 = "ＢＯＭ备案";
		} else if (rateSource == 3) {
			mesage1 = "报关单耗";
		}
		List result = orderCommonAction.findCustomOrderDetail(new Request(
				CommonVars.getCurrUser()), customOrder);
		List listExgBom = orderCommonAction.isExitInCustomBom(new Request(
				CommonVars.getCurrUser()), customOrder, "0");
		List listImgBom = orderCommonAction.isExitInCustomBom(new Request(
				CommonVars.getCurrUser()), customOrder, "2");
		List listExgInner = orderCommonAction.isExitInInner(new Request(
				CommonVars.getCurrUser()), customOrder, "0");
		List listImgInner = orderCommonAction.isExitInInner(new Request(
				CommonVars.getCurrUser()), customOrder, "2");
		List listExgRecords = orderCommonAction.isExitInRecords(new Request(
				CommonVars.getCurrUser()), customOrder, "0");
		List listImgRecords = orderCommonAction.isExitInRecords(new Request(
				CommonVars.getCurrUser()), customOrder, "2");
		System.out.println("1::" + listExgBom + "    2:::" + listImgBom
				+ "   3:::" + listExgInner + "   4:::" + listImgInner + "5:::"
				+ listExgRecords + "   6:::" + listImgRecords);
		if (customOrder.getIfok() == true) {
			JOptionPane.showMessageDialog(this, "该订单已生效!!", "提示！", 0);
			return true;
		}

		if (result.size() == 0) {

			JOptionPane.showMessageDialog(this, "该订单中没有成品资料!!", "提示！", 0);
			return true;
		} else {
			List<CustomOrderDetail> list = new ArrayList<CustomOrderDetail>();
			for (int i = 0; i < result.size(); i++) {
				CustomOrderDetail customOrderDetail = (CustomOrderDetail) result
						.get(i);
				if (customOrderDetail.getBgamount() == null
						|| customOrderDetail.getBgamount() == 0.0
						|| customOrderDetail.getSalesDate() == null
						|| customOrderDetail.getCalUnit() == null
						|| customOrderDetail.getCurr() == null) {
					list.add(customOrderDetail);

				}
			}
			if (list.size() > 0) {
				JOptionPane
						.showMessageDialog(this, "该订单中成品资料中的值有为空!", "提示！", 0);
				return true;
			}
		}

		if (listExgBom.size() != 0) {
			String mesage = getMesageOderNo(listExgBom);

			JOptionPane.showMessageDialog(this, "成品料号为" + mesage + "的成品在"
					+ mesage1 + "中不存在!!", "提示！", 0);
			return true;
		}
		if (listImgBom.size() != 0) {
			String mesage = getMesageOderNo(listImgBom);

			JOptionPane.showMessageDialog(this, "料件料号为" + mesage + "的料件在"
					+ mesage1 + "中不存在!!", "提示！", 0);
			return true;
		}
		if (listExgInner.size() != 0) {
			String mesage = getMesageOderNo(listExgInner);

			JOptionPane.showMessageDialog(this, "成品料号为" + mesage
					+ "的成品在归并关系中不存在!!", "提示！", 0);
			return true;
		}
		if (listImgInner.size() != 0) {
			String mesage = getMesageOderNo(listImgInner);

			JOptionPane.showMessageDialog(this, "料件料号为" + mesage
					+ "的料件在归并关系中不存在!!", "提示！", 0);
			return true;
		}
		if (listExgRecords.size() != 0) {
			String mesage = getMesageOderNo(listExgRecords);

			JOptionPane.showMessageDialog(this, "成品料号为" + mesage + "的成品没有备案!!",
					"提示！", 0);
			return true;
		}
		if (listImgRecords.size() != 0) {
			String mesage = getMesageOderNo(listImgRecords);
			JOptionPane.showMessageDialog(this, "料件料号为" + mesage + "的料件没有备案!!",
					"提示！", 0);
			return true;
		}
		return false;
	}

	/**
	 * This method initializes btncancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtncancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("回卷");
			btnCancel.setPreferredSize(new Dimension(60, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					listCustomOrder = tableModelOrder.getCurrentRows();
					if (listCustomOrder.size() == 0) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"请选择你要回卷的资料!", "确认!", 2);
						return;
					}

					CustomOrder customOrder = null;
					List newDelList = new ArrayList();
					for (int i = 0; i < listCustomOrder.size(); i++) {
						customOrder = (CustomOrder) listCustomOrder.get(i);
						if (customOrder.getIfok().equals(new Boolean(false))) {
							JOptionPane.showMessageDialog(FmCustomOrder.this,
									"所选订单中包含未生效,不能回卷", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						newDelList.add(customOrder);

					}
					String billNo = orderCommonAction
							.countExsitsNotChangeContract(new Request(
									CommonVars.getCurrUser()), listCustomOrder);
					if (!"".equals(billNo)) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"所选订单号" + billNo + " 已转合同,不能回卷", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					for (int j = 0; j < newDelList.size(); j++) {
						customOrder = (CustomOrder) newDelList.get(j);
						orderCommonAction.delCustomOrderAll(new Request(
								CommonVars.getCurrUser()), customOrder);
						customOrder = orderCommonAction.cancelorder(
								new Request(CommonVars.getCurrUser()),
								customOrder);
						tableModelOrder.updateRow(customOrder);
						customOrder = null;
					}
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes importpo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getImportpo() {
		if (btnInput == null) {
			btnInput = new JButton();
			btnInput.setText("导入");
			btnInput.setPreferredSize(new Dimension(60, 30));
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						return;
					}

					DgOrderImportDataFromFile data = new DgOrderImportDataFromFile();
					data.setVisible(true);
					List list = orderCommonAction.findCustomOrder(new Request(
							CommonVars.getCurrUser()));

					tableModelOrder.deleteRows(tableModelOrder.getList());
					tableModelOrder.addRows(list);
				}
			});
		}
		return btnInput;
	}

	/**
	 * This method initializes setcs
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSetcs() {
		if (btnRefurbish == null) {
			btnRefurbish = new JButton();
			btnRefurbish.setText("刷新");
			btnRefurbish.setPreferredSize(new Dimension(60, 30));
			btnRefurbish.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();

				}
			});
		}
		return btnRefurbish;
	}

	/**
	 * 检查参数设置中是否有设置类型与单耗
	 * 
	 * @return
	 */
	private boolean validateData() {
		if (parames == null) {
			JOptionPane.showMessageDialog(FmCustomOrder.this,
					"没有进行参数设置,请设置参数后进行操作!", "确认!", 2);
			return true;
		}
		if (parames.getRateSource() == null || parames.getRateSource() == 0) {
			JOptionPane.showMessageDialog(FmCustomOrder.this, "单耗来源没有设置!",
					"确认!", 2);
			return true;
		}
		if (parames.getSetbgtype() == null || parames.getSetbgtype() == 0) {
			JOptionPane.showMessageDialog(FmCustomOrder.this, "报关类型没有设置!",
					"确认!", 2);
			return true;
		}
		return false;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnImgOrExg == null) {
			btnImgOrExg = new JButton();
			btnImgOrExg.setText("料件成品及单耗表");
			btnImgOrExg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					Integer customType = parames.getSetbgtype();
					DgProductAndMaterielAndRate dgProductAndMaterielAndRate = new DgProductAndMaterielAndRate();
					dgProductAndMaterielAndRate.setCustomType(customType);
					dgProductAndMaterielAndRate.setVisible(true);
				}
			});
		}
		return btnImgOrExg;
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

		mapAllDetail = this.orderCommonAction
				.findAllCustomOrderDetail(new Request(CommonVars.getCurrUser()));

		return this.orderCommonAction.findOrderMaster(new Request(CommonVars
				.getCurrUser()), index, length, property, value, isLike);
	}

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
					return FmCustomOrder.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmCustomOrder.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setFloatable(false);
			jJToolBarBar.add(getPnCommonQueryPage());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnChangToContract == null) {
			btnChangToContract = new JButton();
			btnChangToContract.setText("生成合同");
			btnChangToContract
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (validateData()) {
								return;
							}
							Integer customType = parames.getSetbgtype();
							DgCustomOrderToContract dg = new DgCustomOrderToContract();
							dg.setTableMode(tableModelOrder);
							dg.setCustomType(customType);
							dg.setVisible(true);
							pnCommonQueryPage.setInitState();

						}
					});
		}

		return btnChangToContract;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel(), BorderLayout.CENTER);

		}
		return jPanel1;
	}

	/**
	 * This method initializes btnBrower
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrower() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.setPreferredSize(new Dimension(60, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData()) {
						return;
					}
					CustomOrder customOrder = (CustomOrder) tableModelOrder
							.getCurrentRow();
					if (tableModelOrder.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCustomOrder.this,
								"请选择你要查看的资料!", "确认!", 2);
						return;
					}
					DgCustomOrder dg = new DgCustomOrder();
					dg.settableModelOrder(tableModelOrder);
					dg.setDataState(DataState.BROWSE);
					dg.setVisible(true);
				}
			});
		}
		return btnBrowse;
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
			jPanel.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
