package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.TableCellRenderers.TableCheckBoxRender;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 进出仓单据主窗体 edit by cjb 2009.7.7
 * 
 * @author hw
 * 
 */
public class FmBlsInOutStockBill extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JToolBar jJToolBarBar = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 
	 * 进仓单据表格
	 */
	private JTable tbBlsInOutStockBillIn = null;

	/**
	 * 新境
	 */
	private JButton btnAdd = null;

	/**
	 * 修改
	 */
	private JButton btnEdit = null;

	/**
	 * 删除
	 */
	private JButton btnDelete = null;

	/**
	 * 转抄
	 */
	private JButton btnCopy = null;

	/**
	 * 查询
	 */
	private JButton btnQuery = null;

	/**
	 * 进仓单据表格模型
	 */
	public JTableListModel tableModelIn = null;
	/**
	 * 出仓单据表格模型
	 */
	public JTableListModel tableModelOut = null;
	/**
	 * 料件操作接口
	 */
	private MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
			.getApplicationContext().getBean("materialManageAction"); // @jve:decl-index=0:

	/**
	 * 进出仓单据服务器操作接口
	 */
	private BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:

	/**
	 * 转仓单
	 */
	private JButton btnSwitch = null;

	/**
	 * 刷新
	 */
	private JButton btnRefurbish = null;

	/**
	 * 退出关闭
	 */
	private JButton btnExit = null;

	/**
	 * 导入
	 */
	private JButton btnImport = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 出仓单据表格
	 */
	private JTable tbBlsInOutStockBillOut = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel = null;

	/**
	 * 开始日期
	 */
	private JCalendarComboBox cbbStartDate = null;

	private JLabel jLabel1 = null;

	/**
	 * 结束时间
	 */
	private JCalendarComboBox cbbEndDate = null;

	/**
	 * 查询刷新
	 */
	private JButton btnFind = null;

	private BlsInOutStockBill bsb = null;

	Boolean ed = null;

	/**
	 * 构造方法
	 * 
	 */
	public FmBlsInOutStockBill() {

		super();

		initialize();

		// Calendar tt = Calendar.getInstance();
		Calendar d = cbbStartDate.getCalendar();

		Date date = new Date();

		Date beginDate = CommonUtils.getBeginDate(date);

		Date endDate = CommonUtils.getEndDate(date);

		List list = blsInOutStockBillAction
				.findBlsInOutStockBillInByCreateDate(
						new Request(CommonVars.getCurrUser()), beginDate);

		initTableIn(list);

		List list2 = blsInOutStockBillAction
				.findBlsInOutStockBillOutByCreateDate(
						new Request(CommonVars.getCurrUser()), beginDate);

		initTableOut(list2);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(640, 389));
		this.setTitle("进出仓单据");
		this.setContentPane(getJPanel());

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

			jPanel.add(getJToolBar(), BorderLayout.SOUTH);

			jPanel.add(getJTabbedPane(), BorderLayout.CENTER);

		}

		return jPanel;
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
			jJToolBarBar.add(getBtnCopy());
			jJToolBarBar.add(getBtnQuery());
			jJToolBarBar.add(getBtnSwitch());
			jJToolBarBar.add(getBtnImport());
			jJToolBarBar.add(getBtnRefurbish());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbBlsInOutStockBillIn());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbBlsInOutStockBillIn
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBlsInOutStockBillIn() {
		if (tbBlsInOutStockBillIn == null) {
			tbBlsInOutStockBillIn = new JTable();
			tbBlsInOutStockBillIn
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								if (tableModelIn.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmBlsInOutStockBill.this,
											"请选择要修改的数据！", "提示！",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								DgBlsInOutStockBill dg = new DgBlsInOutStockBill(
										(BlsInOutStockBill) tableModelIn
												.getCurrentRow());
								dg.setInOutFlag(BlsIOStockBillIOF.IMPORT);
								dg.setDataState(DataState.BROWSE);
								dg.setFathertableModel(tableModelIn);
								dg.setBlsInOutStockBill((BlsInOutStockBill) tableModelIn
										.getCurrentRow());
								dg.setVisible(true);
								setState();
							} else if (e.getClickCount() == 1) {
								setState();
							}

						}

					});
			tbBlsInOutStockBillIn.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbBlsInOutStockBillIn
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
		}
		return tbBlsInOutStockBillIn;
	}

	/**
	 * 初始化表体数据
	 */
	private void initTableIn(List list) {

		tableModelIn = new JTableListModel(tbBlsInOutStockBillIn, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("进出仓标志", "ioFlag", 100));
						list.add(addColumn("单据编号", "billNo", 100));
						list.add(addColumn("生效", "isEffective", 100));
						list.add(addColumn("单据生效日期", "validDate", 100));
						list.add(addColumn("录入日期", "createDate", 100));
						list.add(addColumn("录入人", "createPeople", 100));
						list.add(addColumn("供货方企业", "corrOwner.name", 180));
						list.add(addColumn("仓库编码", "wareHouseCode.code", 100));
						return list;
					}
				});
		tbBlsInOutStockBillIn.getColumnModel().getColumn(3)
				.setCellRenderer(new TableCheckBoxRender());
		// tbContract.getColumnModel().getColumn(1).setCellRenderer(new
		// TableMultiRowRender());
		tbBlsInOutStockBillIn.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					// 转化表体数据
					private String castValue(Object value) {
						return BlsIOStockBillIOF.getImpExpFlagSpec(value
								.toString());
					}
				});
	}

	/**
	 * 初始化表体数据
	 */
	private void initTableOut(List list) {

		tableModelOut = new JTableListModel(tbBlsInOutStockBillOut, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("进出仓标志", "ioFlag", 100));
						list.add(addColumn("单据编号", "billNo", 100));
						list.add(addColumn("生效", "isEffective", 100));
						list.add(addColumn("单据生效日期", "validDate", 100));
						list.add(addColumn("录入日期", "createDate", 100));
						list.add(addColumn("录入人", "createPeople", 100));
						list.add(addColumn("供货方企业", "corrOwner.name", 180));
						list.add(addColumn("仓库编码", "wareHouseCode.code", 100));
						return list;
					}
				});

		tbBlsInOutStockBillOut.getColumnModel().getColumn(3)
				.setCellRenderer(new TableCheckBoxRender());

		tbBlsInOutStockBillOut.getColumnModel().getColumn(1)
				.setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(value));
						return this;
					}

					// 转化表体数据
					private String castValue(Object value) {
						return BlsIOStockBillIOF.getImpExpFlagSpec(value
								.toString());
					}
				});
	}

	/**
	 * 设置组建状态
	 */
	private void setState() {
		if (jTabbedPane.getSelectedIndex() == 0
				&& tableModelIn.getCurrentRow() != null) {

			bsb = (BlsInOutStockBill) tableModelIn.getCurrentRow();
			ed = bsb.getIsEffective() == null ? false : bsb.getIsEffective();
		} else if (jTabbedPane.getSelectedIndex() == 1
				&& tableModelOut.getCurrentRow() != null) {

			bsb = (BlsInOutStockBill) tableModelOut.getCurrentRow();
			ed = bsb.getIsEffective() == null ? false : bsb.getIsEffective();
		}
		this.btnEdit.setEnabled(!ed);
		this.btnDelete.setEnabled(!ed);
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
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgBlsInOutStockBill dg = new DgBlsInOutStockBill(null);
						dg.setInOutFlag(BlsIOStockBillIOF.IMPORT);
						dg.setDataState(DataState.ADD);
						dg.setFathertableModel(tableModelIn);
						dg.setFathertableModelOut(tableModelOut);
						dg.setState();
						dg.setVisible(true);
					} else {
						DgBlsInOutStockBill dg = new DgBlsInOutStockBill(null);
						dg.setInOutFlag(BlsIOStockBillIOF.EXPORT);
						dg.setDataState(DataState.ADD);
						dg.setFathertableModel(tableModelIn);
						dg.setFathertableModelOut(tableModelOut);
						dg.setState();
						dg.setVisible(true);
					}

					// setState();
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
					BlsInOutStockBill bill = null;
					DgBlsInOutStockBill dg = new DgBlsInOutStockBill(bill);
					dg.setDataState(DataState.EDIT);
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModelIn.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmBlsInOutStockBill.this, "请选择要修改的数据！",
									"提示！", JOptionPane.WARNING_MESSAGE);
							return;
						}
						System.out.println("fuck you");
						bill = (BlsInOutStockBill) tableModelIn.getCurrentRow();
						dg.setInOutFlag(BlsIOStockBillIOF.IMPORT);
						dg.setFathertableModel(tableModelIn);
						dg.setBlsInOutStockBill(bill);
					} else {
						if (tableModelOut.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmBlsInOutStockBill.this, "请选择要修改的数据！",
									"提示！", JOptionPane.WARNING_MESSAGE);
							return;
						}
						System.out.println("Fuck every body");
						bill = (BlsInOutStockBill) tableModelOut
								.getCurrentRow();
						dg.setInOutFlag(BlsIOStockBillIOF.EXPORT);
						dg.setFathertableModel(tableModelOut);
						dg.setBlsInOutStockBill(bill);
					}
					dg.setVisible(true);
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
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModelIn.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmBlsInOutStockBill.this, "请选择要删除的数据行!",
									"提示", 0);
							return;
						}
						deleteTableRow();
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelOut.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmBlsInOutStockBill.this, "请选择要删除的数据行!",
									"提示", 0);
							return;
						}
						deleteTableRow();
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除表体信息
	 */
	public void deleteTableRow() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			if (tableModelIn.getCurrentRows() != null) {
				if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
					return;
				}
			}
			BlsInOutStockBill bsb = (BlsInOutStockBill) tableModelIn
					.getCurrentRow();
			blsInOutStockBillAction.deleteBlsInOutStockBillHeadAndBody(
					new Request(CommonVars.getCurrUser()), bsb);
			tableModelIn.deleteRow(bsb);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			if (tableModelOut.getCurrentRows() != null) {
				if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
					return;
				}
			}
			BlsInOutStockBill bsb = (BlsInOutStockBill) tableModelOut
					.getCurrentRow();
			blsInOutStockBillAction.deleteBlsInOutStockBillHeadAndBody(
					new Request(CommonVars.getCurrUser()), bsb);
			tableModelOut.deleteRow(bsb);
		}

	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModelIn.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmBlsInOutStockBill.this, "请选择出入单据!",
									"提示！", JOptionPane.WARNING_MESSAGE);
							return;
						}
						BlsInOutStockBill blsInOutStockBill = (BlsInOutStockBill) tableModelIn
								.getCurrentRow();
						String billNo = null;
						CompanyOther other = CommonVars.getOther();
						if (other != null
								&& other.getIsAutoGetDjNo() != null
								&& other.getIsAutoGetDjNo().equals(
										Boolean.valueOf(true))) {
							billNo = String.valueOf(blsInOutStockBillAction
									.getMaxBillNoByType(new Request(CommonVars
											.getCurrUser())));
						}

						if (JOptionPane.showConfirmDialog(
								FmBlsInOutStockBill.this, "是否也转抄其表体资料?", "提示",
								0) == 0) {
							BlsInOutStockBill newBlsInOutStockBill = blsInOutStockBillAction
									.copyBlsInOutStockBillAndCommodityInfo(
											new Request(CommonVars
													.getCurrUser()),
											blsInOutStockBill, true, billNo);
							tableModelIn.addRow(newBlsInOutStockBill);
						} else {
							BlsInOutStockBill newBlsInOutStockBill = blsInOutStockBillAction
									.copyBlsInOutStockBillAndCommodityInfo(
											new Request(CommonVars
													.getCurrUser()),
											blsInOutStockBill, false, billNo);
							tableModelIn.addRow(newBlsInOutStockBill);
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						if (tableModelOut.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									FmBlsInOutStockBill.this, "请选择出入单据!",
									"提示！", JOptionPane.WARNING_MESSAGE);
							return;
						}
						BlsInOutStockBill blsInOutStockBill = (BlsInOutStockBill) tableModelOut
								.getCurrentRow();
						String billNo = null;
						CompanyOther other = CommonVars.getOther();
						if (other != null
								&& other.getIsAutoGetDjNo() != null
								&& other.getIsAutoGetDjNo().equals(
										Boolean.valueOf(true))) {
							billNo = String.valueOf(blsInOutStockBillAction
									.getMaxBillNoByType(new Request(CommonVars
											.getCurrUser())));
						}

						if (JOptionPane.showConfirmDialog(
								FmBlsInOutStockBill.this, "是否也转抄其表体资料?", "提示",
								0) == 0) {
							BlsInOutStockBill newBlsInOutStockBill = blsInOutStockBillAction
									.copyBlsInOutStockBillAndCommodityInfo(
											new Request(CommonVars
													.getCurrUser()),
											blsInOutStockBill, true, billNo);
							tableModelOut.addRow(newBlsInOutStockBill);
						} else {
							BlsInOutStockBill newBlsInOutStockBill = blsInOutStockBillAction
									.copyBlsInOutStockBillAndCommodityInfo(
											new Request(CommonVars
													.getCurrUser()),
											blsInOutStockBill, false, billNo);
							tableModelOut.addRow(newBlsInOutStockBill);
						}
					}
				}
			});
		}
		return btnCopy;
	}

	/**
	 * This method initializes btnQuery
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgQueryConditionForList dg = new DgQueryConditionForList();
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						initTableIn(list);
					}
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes btnSwitch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSwitch() {
		if (btnSwitch == null) {
			btnSwitch = new JButton();
			btnSwitch.setText("转仓单");
			btnSwitch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					BlsInOutStockBill blsInOutStockBill = (BlsInOutStockBill) tableModelIn
							.getCurrentRow();
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgMakeBillToWarehousewarrant dg = new DgMakeBillToWarehousewarrant(
								ImpExpFlag.IMPORT);
						dg.setBlsInOutStockBill(blsInOutStockBill);
						dg.setVisible(true);
					} else {
						DgMakeBillToWarehousewarrant dg = new DgMakeBillToWarehousewarrant(
								ImpExpFlag.EXPORT);
						dg.setBlsInOutStockBill(blsInOutStockBill);
						dg.setVisible(true);
					}
				}
			});
		}
		return btnSwitch;
	}

	/**
	 * This method initializes btnRefurbish
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefurbish() {
		if (btnRefurbish == null) {
			btnRefurbish = new JButton();
			btnRefurbish.setText("刷新");
			btnRefurbish.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = getDataSourceIn();
					List list2 = getDataSourceOut();
					if (jTabbedPane.getSelectedIndex() == 0) {
						initTableIn(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						initTableOut(list2);
					}
				}
			});
		}
		return btnRefurbish;
	}

	/**
	 * 查询进仓单据
	 * 
	 * @return
	 */
	private List getDataSourceIn() {
		List list = null;
		list = blsInOutStockBillAction.findBlsInOutStockBillIn(new Request(
				CommonVars.getCurrUser()));
		return list;
	}

	/**
	 * 查询出仓单据
	 * 
	 * @return
	 */
	private List getDataSourceOut() {
		List list = null;
		list = blsInOutStockBillAction.findBlsInOutStockBillOut(new Request(
				CommonVars.getCurrUser()));
		return list;
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
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 设置为显示状态
	 */
	@Override
	public void setVisible(boolean f) {
		if (f) {
			if (this.ed == null) {
				ed = new Boolean(false);
			}
			setState();
			super.setVisible(f);
		}

	}

	/**
	 * This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgBlsInOutDetailImport dg = new DgBlsInOutDetailImport(
								BlsIOStockBillIOF.IMPORT);
						dg.setTableModelHead(tableModelIn);
						dg.setVisible(true);
					} else {
						DgBlsInOutDetailImport dg = new DgBlsInOutDetailImport(
								BlsIOStockBillIOF.EXPORT);
						dg.setTableModelHead(tableModelOut);
						dg.setVisible(true);
					}

				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("进仓", null, getJPanel1(), null);
			jTabbedPane.addTab("出仓", null, getJPanel2(), null);
		}
		return jTabbedPane;
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
			jPanel1.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), BorderLayout.CENTER);
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
			jScrollPane1.setViewportView(getTbBlsInOutStockBillOut());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbBlsInOutStockBillOut
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBlsInOutStockBillOut() {
		if (tbBlsInOutStockBillOut == null) {
			tbBlsInOutStockBillOut = new JTable();
			tbBlsInOutStockBillOut
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() == 2) {
								if (tableModelOut.getCurrentRow() == null) {
									JOptionPane.showMessageDialog(
											FmBlsInOutStockBill.this,
											"请选择要修改的数据！", "提示！",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								DgBlsInOutStockBill dg = new DgBlsInOutStockBill(
										(BlsInOutStockBill) tableModelOut
												.getCurrentRow());
								dg.setInOutFlag(BlsIOStockBillIOF.EXPORT);
								dg.setDataState(DataState.BROWSE);
								dg.setFathertableModel(tableModelOut);
								dg.setBlsInOutStockBill((BlsInOutStockBill) tableModelOut
										.getCurrentRow());
								dg.setVisible(true);
								setState();
							} else if (e.getClickCount() == 1) {
								setState();
							}
						}
					});
			tbBlsInOutStockBillOut.getSelectionModel()
					.addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbBlsInOutStockBillOut
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
		}
		return tbBlsInOutStockBillOut;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel3());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("到:");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			jLabel = new JLabel();
			jLabel.setText("开始时间从：");
			jLabel.setPreferredSize(new Dimension(78, 25));
			jPanel3 = new JPanel();
			jPanel3.setLayout(flowLayout);
			jPanel3.add(jLabel, null);
			jPanel3.add(getCbbStartDate(), null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(getCbbEndDate(), null);
			jPanel3.add(getBtnFind(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes cbbStartDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbStartDate() {
		if (cbbStartDate == null) {
			cbbStartDate = new JCalendarComboBox();
			cbbStartDate.setDate(new Date());
			cbbStartDate.setPreferredSize(new Dimension(85, 25));
		}
		return cbbStartDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new Dimension(85, 25));
			cbbEndDate.setDate(new Date());
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnFind
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton();
			btnFind.setText("刷新");
			btnFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (tableModelIn.getCurrentRow() == null) {
					// JOptionPane.showMessageDialog(FmBlsInOutStockBill.this,
					// "请选择要修改的数据！", "提示！",
					// JOptionPane.WARNING_MESSAGE);
					// return;
					// }
					if (jTabbedPane.getSelectedIndex() == 0) {
						// String iOFlag = ((BlsInOutStockBill) tableModelIn
						// .getCurrentRow()).getIoFlag();
						Date startDate = cbbStartDate.getDate();
						Date endDate = cbbEndDate.getDate();
						List list = blsInOutStockBillAction
								.findBlsInOutStockBillInByDate(new Request(
										CommonVars.getCurrUser()), "I",
										startDate, endDate);
						initTableIn(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						// String iOFlag = ((BlsInOutStockBill) tableModelOut
						// .getCurrentRow()).getIoFlag();
						Date startDate = cbbStartDate.getDate();
						Date endDate = cbbEndDate.getDate();
						List list = blsInOutStockBillAction
								.findBlsInOutStockBillInByDate(new Request(
										CommonVars.getCurrUser()), "O",
										startDate, endDate);
						initTableOut(list);
					}
				}
			});
		}
		return btnFind;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
