package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu.Separator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.transferfactory.entity.TransferFactoryBill;
/**
 * by 2009-1-10 lm checked
 * @author Administrator
 *进出货转厂单据
 */
@SuppressWarnings("unchecked")
public class FmTransferFactoryBill extends FmCommon {
	private static final long serialVersionUID = 1L;

	private JPanel contentPn = null;
	private JSplitPane spn = null;
	private JPanel pn = null;
	private JTabbedPane tabPn = null;
	private JToolBar tb = null;
	private JButton btnAdd = null;							//新增
	private JButton btnEdit = null;							//修改
	private JButton btnDelete = null;						//删除
	private JButton btnOtherFunction = null;				//其它功能
	private JButton btnExit = null;							//退出关闭
	private JTable jTable = null;
	private JScrollPane spn1 = null;
	private JList trImportGoods = null;						//显示进口单据树
	private JList trExportGoods = null;						//显示出口单据树
	private JCheckBox cbShowAll = null;						//是否显示进货全部数据
	private JPopupMenu pmOtherFunction = null;
	private JMenuItem miImportByMaterielBill = null;
	private JMenuItem miMakeCustomsBillList = null;
	private JMenuItem miMakeCustomsDeclaration = null;
	private JMenuItem billEffect = null;
	private JMenuItem billNoEffect = null;
	private TransferFactoryBill transferFactoryBill = null;	//转厂进出货单
	private ScmCoc scmCoc = null;							//存放物流通用代码－－客户供应商资料
	private List list = null;  //  @jve:decl-index=0:
	private JTableListModel tableModel = null;				//表格数据模型
	private boolean isImportGoods = true;					//是否导入商品
	//private boolean isFirstTime = true;						//是否是第一时间
	//private boolean isControl = false;						//是否控制
	private JScrollPane spn2 = null;
	private JScrollPane spn3 = null;
	private JMenuItem miSplitBill = null;
	private JSeparator jSeparator = null;
	private JButton btnImport = null;						//单据导入
	private JButton btnRubish = null;
	private JPanel pnLeft = null;
	private JTextField tfCondition = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmTransferFactoryBill() {
		super();
		initialize();
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getContentPn());
		this.setSize(646, 335);
		this.setTitle("进出货转厂单据");
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
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
		listValueChange(trImportGoods);
	}

	private JPanel getContentPn() {
		if (contentPn == null) {
			contentPn = new JPanel();
			contentPn.setLayout(new BorderLayout());
			contentPn.add(getSpn(), java.awt.BorderLayout.CENTER);
		}
		return contentPn;
	}

	public boolean isImportGoods() {
		return isImportGoods;
	}

	public void setImportGoods(boolean isImportGoods) {
		this.isImportGoods = isImportGoods;
	}

	public TransferFactoryBill getTransferFactoryBill() {
		return transferFactoryBill;
	}

	public void setCustomsEnvelopRequestBill(
			TransferFactoryBill customsEnvelopRequestBill) {
		this.transferFactoryBill = customsEnvelopRequestBill;
	}

	private JSplitPane getSpn() {
		if (spn == null) {
			spn = new JSplitPane();
			spn.setDividerSize(10);
			spn.setOneTouchExpandable(true);
			spn.setLeftComponent(getPnLeft());
			spn.setRightComponent(getPn());
			spn.setDividerLocation(120);
		}
		return spn;
	}

	private JPanel getPn() {
		if (pn == null) {
			pn = new JPanel();
			pn.setLayout(new BorderLayout());
			pn.add(getTb(), java.awt.BorderLayout.NORTH);
			pn.add(getSpn1(), java.awt.BorderLayout.CENTER);
		}
		return pn;
	}

	private JTabbedPane getTabPn() {
		if (tabPn == null) {
			tabPn = new JTabbedPane();
			tabPn.setTabPlacement(SwingConstants.BOTTOM);
			tabPn.addTab("进货", null, getSpn2(), null);
			tabPn.addTab("出货", null, getSpn3(), null);
			tabPn
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							showData();
						}
					});
		}
		return tabPn;
	}

	private JToolBar getTb() {
		if (tb == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			tb = new JToolBar();
			tb.setLayout(f);
			tb.setFloatable(false);
			tb.add(getBtnAdd());
			tb.add(getBtnEdit());
			tb.add(getBtnDelete());
			tb.add(getBtnOtherFunction());
			tb.add(getBtnRubish());
			tb.add(getBtnImport());
			tb.add(getBtnExit());
			tb.add(getCbShowAll());
		}
		return tb;
	}

	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(60, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addData();
				}
			});
		}
		return btnAdd;
	}

	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
				}
			});
		}
		return btnEdit;
	}

	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	private JButton getBtnOtherFunction() {
		if (btnOtherFunction == null) {
			btnOtherFunction = new JButton();
			btnOtherFunction.setText("其它功能");
			btnOtherFunction.setPreferredSize(new Dimension(60, 30));
			btnOtherFunction
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmOtherFunction().show(btnOtherFunction, 0,
									btnOtherFunction.getHeight());
						}
					});
		}
		return btnOtherFunction;
	}

	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmTransferFactoryBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {

				}

				public void keyPressed(KeyEvent e) {
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_F) {
						// JOptionPane.showMessageDialog(null, "kdkkddkd");
					}
				}

				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
						//isControl = false;
					}
				}
			});
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
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
									setCustomsEnvelopRequestBill((TransferFactoryBill) tableModel
											.getCurrentRow());
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
		}
		return jTable;
	}

	private JScrollPane getSpn1() {
		if (spn1 == null) {
			spn1 = new JScrollPane();
			spn1.setViewportView(getJTable());
		}
		return spn1;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	private JList getTrImportGoods() {
		if (trImportGoods == null) {
			if(manufacturers == null) {
				manufacturers = super.getManufacturer();
			}
			trImportGoods = new JList(manufacturers.toArray());
			trImportGoods.setCellRenderer(CustomBaseRender.getInstance().getRender("code", "name"));
			trImportGoods.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			trImportGoods.setSelectedIndex(0);
			trImportGoods
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(
						javax.swing.event.ListSelectionEvent e) {
					listValueChange(trImportGoods);
				}
			});

		}
		return trImportGoods;
	}

	private JList getTrExportGoods() {
		if (trExportGoods == null) {
			if(customers == null) {
				customers = super.getCustomer();
			}
			trExportGoods = new JList(customers.toArray());
			trExportGoods.setCellRenderer(CustomBaseRender.getInstance().getRender("code", "name"));
			trExportGoods.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			trExportGoods.setSelectedIndex(0);
			trExportGoods
			.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(
						javax.swing.event.ListSelectionEvent e) {
					listValueChange(trExportGoods);
				}
			});
		}
		return trExportGoods;
	}

	/**
	 * 树节点选择 显示数据,设置关封申请单类型
	 */
	private void listClick(String id) {
		showDataByTreeSelectedCode(id);
		this.cbShowAll.setSelected(false);
	}

	/**
	 * 设置数据源 list ,初始化表格
	 */
	private void showDataByTreeSelectedCode(String scmCocId) {
		this.setList(super.transferFactoryManageAction
				.findTransferFactoryBillByScmCocId(new Request(CommonVars
						.getCurrUser()), scmCocId, isImportGoods));

		initTable(this.getList());
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("单据号", "transferFactoryBillNo",
										120));
						list.add(addColumn("生效", "isAvailability", 50));
						list.add(addColumn("是否转报关单", "isCustomsDeclaration",
								100));
						list.add(addColumn("生效日期", "beginAvailability", 80));
						list.add(addColumn("客户/供应商名称", "scmCoc.name", 150));
						list.add(addColumn("仓库名称", "wareSet.name", 150));
						list.add(addColumn("商品项数", "itemCount", 80));
						list.add(addColumn("已转清单项数", "makeBillListItemCount",
								80));
						list.add(addColumn("生成的报关单号码",
								"makeCustomsDeclarationCode", 100));
						list.add(addColumn("录入人员", "aclUser.userName", 80));
						list.add(addColumn("备注", "memo", 100));
						list.add(addColumn("关封号", "envelopNo", 80));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(3).setCellRenderer(
				new TableCheckBoxRender());
		// jTable.getColumnModel().getColumn(4).setCellRenderer(
		// new TableCheckBoxRender());
	}

	/**
	 * 新增
	 */
	private void addData() {
		DgTransferFactoryBill dg = new DgTransferFactoryBill();
		dg.setImportGoods(this.isImportGoods());
		dg.setTransferFactoryBillModel(this.tableModel);
		dg.setScmCoc(scmCoc);
		dg.setDataState(DataState.ADD);
		dg.setVisible(true);
	}

	/**
	 * 修改
	 */
	private void editData() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示", 0);
			return;
		}
		DgTransferFactoryBill dg = new DgTransferFactoryBill();
		dg.setImportGoods(this.isImportGoods());
		dg.setTransferFactoryBillModel(this.tableModel);
		dg.setDataState(DataState.EDIT);
		dg.setVisible(true);
		showData();
	}

	/**
	 * 删除
	 */
	private void deleteData() {
		if (tableModel.getCurrentRow() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!!!", "提示", 0) != 0) {
				return;
			}
			super.transferFactoryManageAction.deleteTransferFactoryBill(
					new Request(CommonVars.getCurrUser()),
					(TransferFactoryBill) tableModel.getCurrentRow());
			tableModel.deleteRow(tableModel.getCurrentRow());
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!!!", "提示", 0);
		}
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		TransferFactoryBill factoryBill = null;
		if (tabPn.getSelectedIndex() == 0 && tableModel != null) {
			factoryBill = (TransferFactoryBill) tableModel.getCurrentRow();
		} else {
			factoryBill = (TransferFactoryBill) tableModel.getCurrentRow();
		}
		if (factoryBill != null) {
			btnEdit
					.setEnabled(factoryBill.getIsApplyToCustomsBill() == null ? true
							: !factoryBill.getIsApplyToCustomsBill()
									.booleanValue()
									|| factoryBill
											.getIsCustomsEnvelopRequestBill() == null ? true
									: !factoryBill
											.getIsCustomsEnvelopRequestBill()
											.booleanValue());
			btnDelete.setEnabled(factoryBill.getIsAvailability() == null ? true
					: !factoryBill.getIsAvailability().booleanValue());
		}
	}

	/**
	 * This method initializes cbShowAll
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbShowAll() {
		if (cbShowAll == null) {
			cbShowAll = new JCheckBox();
			cbShowAll.setText("显示进货全部数据");
			cbShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					showAllData();
				}
			});
		}
		return cbShowAll;
	}

	/**
	 * 显示所有进货或出货的数据
	 */
	private void showAllData() {
		if (this.cbShowAll.isSelected() == true) {
			List list = super.transferFactoryManageAction
					.findTransferFactoryBillByImpExpGoodsFlag(new Request(
							CommonVars.getCurrUser()), isImportGoods());
			this.initTable(list);
		} else {
			if (isImportGoods() == true) {
				this.listValueChange(this.trImportGoods);
			} else {
				this.listValueChange(this.trExportGoods);
			}
		}
	}

	private JPopupMenu getPmOtherFunction() {
		if (pmOtherFunction == null) {
			pmOtherFunction = new JPopupMenu();
			pmOtherFunction
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pmOtherFunction.add(getMiImportByMaterielBill());
			pmOtherFunction.add(getJSeparator());
			pmOtherFunction.add(getMiSplitBill());
			Separator separator3 = new Separator();
			separator3.setForeground(Color.gray);
			pmOtherFunction.add(separator3);
			pmOtherFunction.add(getMiMakeCustomsDeclaration());
			pmOtherFunction.add(getJSeparator());
			pmOtherFunction.add(getBillEffect());
			pmOtherFunction.add(getJSeparator());
			pmOtherFunction.add(getBillNoEffect());
		}
		return pmOtherFunction;
	}

	private JMenuItem getMiImportByMaterielBill() {
		if (miImportByMaterielBill == null) {
			miImportByMaterielBill = new JMenuItem();
			miImportByMaterielBill.setText("从海关帐单据导入");
			miImportByMaterielBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMakeTransferFactoryBill dialog = new DgMakeTransferFactoryBill();
							dialog.setImportGoods(isImportGoods);
							dialog.setScmCoc(scmCoc);
							dialog.setVisible(true);
							if (isImportGoods() == true) {
								listValueChange(trImportGoods);
							} else {
								listValueChange(trExportGoods);
							}
						}
					});
		}
		return miImportByMaterielBill;
	}

	@SuppressWarnings("unused")
	private JMenuItem getMiMakeCustomsBillList() {
		if (miMakeCustomsBillList == null) {
			miMakeCustomsBillList = new JMenuItem();
			miMakeCustomsBillList.setText("生成报关清单");
			miMakeCustomsBillList
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMakeCustomsBillList dialog = new DgMakeCustomsBillList();
							dialog.setImportGoods(isImportGoods);
							dialog.setTableMode(tableModel);
							dialog.setVisible(true);
							showData();
							setState();
						}
					});
		}
		return miMakeCustomsBillList;
	}

	private JMenuItem getMiMakeCustomsDeclaration() {
		if (miMakeCustomsDeclaration == null) {
			miMakeCustomsDeclaration = new JMenuItem();
			miMakeCustomsDeclaration.setText("生成报关单");
			miMakeCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DefaultMutableTreeNode selectedNode = null;
							DgMakeCustomsDeclaration dialog = new DgMakeCustomsDeclaration();
							dialog.setImport(isImportGoods);
							if (selectedNode != null) {
								dialog.setInitScmCoc((ScmCoc) selectedNode
										.getUserObject());
							}
							dialog.setVisible(true);
							if (dialog.isOk()) {
								showAllData();
								setState();
							}
						}
					});
		}
		return miMakeCustomsDeclaration;
	}
	private JMenuItem getBillNoEffect() {
		if (billNoEffect == null) {
			billNoEffect = new JMenuItem();
			billNoEffect.setText("大批量回卷");
			billNoEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmTransferFactoryBill.this,
								"请选择要取消生效的数据行!", "提示", 0);
						return;
					}
					List<TransferFactoryBill> list = tableModel
							.getCurrentRows();
					List tempList = new ArrayList();
					for (TransferFactoryBill data : list) {
						if (data.getMakeCustomsDeclarationCode() != null
								&& !data.getMakeCustomsDeclarationCode().equals("")) {
							JOptionPane.showMessageDialog(
									FmTransferFactoryBill.this,
									"在进出口结转单据中有数据已转报关单,不能撤消生效!!", "警告",
									JOptionPane.INFORMATION_MESSAGE);
							tableModel.updateRows(tempList);
							return;
						}
						if (!data.getIsAvailability()) {
							continue;
						}
						beginAvailability(false, data);
						tempList.add(data);
					}
					tableModel.updateRows(tempList);
					JOptionPane.showMessageDialog(
							FmTransferFactoryBill.this, "单据取消生效成功!",
							"提示!", JOptionPane.INFORMATION_MESSAGE);

				}
			});
		}
		return billNoEffect;
	}
	private JMenuItem getBillEffect() {
		if (billEffect == null) {
			billEffect = new JMenuItem();
			billEffect.setText("大批量生效");
			billEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmTransferFactoryBill.this, "请选择要生效的数据行!",
								"提示", 0);
						return;
					}
					List<TransferFactoryBill> list = tableModel
							.getCurrentRows();
					AvailabilityThread thread = new AvailabilityThread();
					thread.setList(list);
					thread.start();
				}
			});
		}
		return billEffect;
	}

	/**
	 * 
	 * 生效和失效记录并设置状态
	 */

	private void beginAvailability(boolean isAvailability,
			TransferFactoryBill data) {
		try {
			data.setIsAvailability(Boolean.valueOf(isAvailability));
			data = this.transferFactoryManageAction.saveTransferFactoryBill(
					new Request(CommonVars.getCurrUser()), data);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "数据可能有误,生效失败!", "提示", 0);
		}
	}
	/***
	 * 执行
	 * @author Administrator
	 *
	 */

	class AvailabilityThread extends Thread {
		List<TransferFactoryBill> list = new ArrayList();

		@Override
		public void run() {
			CommonProgress.showProgressDialog(FmTransferFactoryBill.this);
			CommonProgress.setMessage("系统正在生效数据，请稍后...");
			List tempList = new ArrayList();
			for (TransferFactoryBill data : list) {
				List list = transferFactoryManageAction
						.findTransferFactoryCommodityInfoByCheck(new Request(
								CommonVars.getCurrUser()), data
								.getId());
				if (list.size() > 0 ) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "单据号为"
							+ data.getTransferFactoryBillNo()
							+ "商品信息中有空的数据,\n结转单据记录不能生效!!", "非法数据!!",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				List dataSource = transferFactoryManageAction.findImpExpCommodityInfo(
						new Request(CommonVars.getCurrUser()), data.getId());

				if (dataSource.size() == 0) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "单据号为"
							+ data.getTransferFactoryBillNo() + "商品信息中没有数据,\n进出口申请记录不能生效!!",
							"非法数据!!", 1);
					return;

				}
				beginAvailability(true, data);
				tempList.add(data);
			}
			CommonProgress.closeProgressDialog();
			tableModel.updateRows(tempList);
			JOptionPane.showMessageDialog(FmTransferFactoryBill.this,
					"单据生效成功!", "提示!", JOptionPane.INFORMATION_MESSAGE);
		}

		public List<TransferFactoryBill> getList() {
			return list;
		}

		public void setList(List<TransferFactoryBill> list) {
			this.list = list;
		}
	}

	private JScrollPane getSpn2() {
		if (spn2 == null) {
			spn2 = new JScrollPane();
			spn2.setViewportView(getTrImportGoods());
		}
		return spn2;
	}

	private JScrollPane getSpn3() {
		if (spn3 == null) {
			spn3 = new JScrollPane();
			spn3.setViewportView(getTrExportGoods());
		}
		return spn3;
	}
	/***
	 * 数据显示
	 */

	private void showData() {
		if (tabPn.getSelectedIndex() == 0) {
			setImportGoods(true);
			tfCondition.setToolTipText("查找供应商...");
			listValueChange(trImportGoods);
		} else if (tabPn.getSelectedIndex() == 1) {
			setImportGoods(false);
			tfCondition.setToolTipText("查找客户...");
			listValueChange(trExportGoods);
		}
		FmTransferFactoryBill.this.cbShowAll
				.setText(isImportGoods() == true ? "显示进货全部数据" : "显示出货全部数据");
	}
	private JMenuItem getMiSplitBill() {
		if (miSplitBill == null) {
			miSplitBill = new JMenuItem();
			miSplitBill.setText("自动分单");
			miSplitBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmTransferFactoryBill.this, "请选择你要修改的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(
							FmTransferFactoryBill.this, "确定要拆分此单据？", "提示",
							JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					TransferFactoryBill transferFactoryBill = (TransferFactoryBill) tableModel
							.getCurrentRow();
					TransferFactoryBill newTransferFactoryBill = transferFactoryManageAction
							.splitTransferFactoryBill(new Request(CommonVars
									.getCurrUser(), true), transferFactoryBill);
					tableModel.addRow(newTransferFactoryBill);
					JOptionPane.showMessageDialog(FmTransferFactoryBill.this,
							"生成新的单据是:"
									+ newTransferFactoryBill
											.getTransferFactoryBillNo(), "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return miSplitBill;
	}

	private JSeparator getJSeparator() {
		if (jSeparator == null) {
			jSeparator = new JSeparator();
		}
		return jSeparator;
	}

	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("单据导入");
			btnImport.setPreferredSize(new Dimension(60, 30));
			btnImport.setForeground(java.awt.Color.blue);
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransBillTxtImport dg = new DgTransBillTxtImport();
					dg.setVisible(true);
				}
			});
		}
		return btnImport;
	}

	private JButton getBtnRubish() {
		if (btnRubish == null) {
			btnRubish = new JButton();
			btnRubish.setText("刷新");
			btnRubish.setPreferredSize(new Dimension(60, 30));
			btnRubish.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isImportGoods() == true) {
						listValueChange(trImportGoods);
					} else {
						listValueChange(trExportGoods);
					}
				}
			});
		}
		return btnRubish;
	}

	private JPanel getPnLeft() {
		if (pnLeft == null) {
			pnLeft = new JPanel();
			pnLeft.setLayout(new BorderLayout());
			pnLeft.add(getTabPn(), BorderLayout.CENTER);
			pnLeft.add(getTfCondition(), BorderLayout.NORTH);
		}
		return pnLeft;
	}

	private JTextField getTfCondition() {
		if (tfCondition == null) {
			tfCondition = new JTextField();
			tfCondition.setToolTipText("查找供应商...");
			tfCondition.addCaretListener(new javax.swing.event.CaretListener() {
				public void caretUpdate(javax.swing.event.CaretEvent e) {
					if(tabPn.getSelectedIndex() == 0) {
						trImportGoods.setListData(searchData(tfCondition.getText(), manufacturers).toArray());
					} else {
						trExportGoods.setListData(searchData(tfCondition.getText(), customers).toArray());
					}
				}
			});
		}
		return tfCondition;
	}
	
	private List customers;
	private List manufacturers;
	
	private List searchData(String condition, List list) {
		if(condition == null || "".equals(condition)) {
			return list;
		}
		List res = new ArrayList();
		ScmCoc scmCoc = null;
		for (int i = 0; i < list.size(); i++) {
			scmCoc = (ScmCoc) list.get(i);
			if(scmCoc.getCode().toLowerCase().contains(condition.toLowerCase()) 
					|| scmCoc.getName().toLowerCase().contains(condition.toLowerCase())) {
				res.add(scmCoc);
			}
		}
		
		return res;
	}
	
	private void listValueChange(JList list) {
		String scmCocId = null;
		if(list.getSelectedIndex() >= 0) {
			scmCocId = ((ScmCoc) list
					.getSelectedValue()).getId();
		}
		listClick(scmCocId);
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
