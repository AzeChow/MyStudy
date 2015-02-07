package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.entity.BillToWareHouseRelationExp;
import com.bestway.bls.entity.BillToWareHouseRelations;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.Delivery;
import com.bestway.bls.entity.StorageBill;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.TableCellRenderers.TableCheckBoxRender;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.ui.message.DgMessage;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.util.Date;

/**
 * 仓单管理checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
public class FmBaseStorageBill extends JInternalFrameBase {

	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnCancel = null;
	private JScrollPane jScrollPane = null;
	private JTable tfStorageBill = null;
	private JTableListModel tableModel;
	private JButton btnBrowse = null;
	private StorageBillAction storageBillAction;
	private BlsInOutStockBillAction blsInOutStockBillAction;

	/**
	 * This method initializes
	 * 
	 */
	public FmBaseStorageBill() {
		super();
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
				.getApplicationContext().getBean("blsInOutStockBillAction");
		initialize();
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			int inout = 0;
			if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
				inout = 1;
			}
//			this.pnCommonQueryPage.setInitState();
			// List list = storageBillAction.findStorageBillByInOut(new Request(
			// CommonVars.getCurrUser()), inout);
			// initTable(list);
			serch();
			setState();
			super.setVisible(f);
		}

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(742, 409));
		this.setTitle("仓单管理");
		this.setContentPane(getJPanel());
		initTable(new ArrayList());

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
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel.add(getJPanel1(), BorderLayout.NORTH);
			jPanel.add(getJPanel2(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton5());
			jToolBar.add(getJButton2());
			jToolBar.add(getBtnEffect());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton4());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (FmBaseStorageBill.this instanceof FmImportStorageBill) {
						DgImportStorage dg = new DgImportStorage();
						dg.setDataState(DataState.ADD);
						dg.setFathertableModel(tableModel);
						dg.setVisible(true);
						setState();
					} else if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
						DgExportStorage dg = new DgExportStorage();
						dg.setDataState(DataState.ADD);
						dg.setFathertableModel(tableModel);
						dg.setVisible(true);
						setState();
					}

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
	private JButton getJButton1() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseStorageBill.this,
								"请选择要修改的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (FmBaseStorageBill.this instanceof FmImportStorageBill) {
						DgImportStorage dg = new DgImportStorage();
						dg.setDataState(DataState.EDIT);
						dg.setFathertableModel(tableModel);
						dg.setStorageBill((StorageBill) tableModel
								.getCurrentRow());
						dg.setVisible(true);
						setState();
					} else if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
						DgExportStorage dg = new DgExportStorage();
						dg.setDataState(DataState.EDIT);
						dg.setFathertableModel(tableModel);
						dg.setStorageBill((StorageBill) tableModel
								.getCurrentRow());
						dg.setVisible(true);
						setState();
					}
				}
			});
		}
		return btnEdit;
	}

	private void setState() {
		if (tableModel.getCurrentRow() != null) {
			StorageBill dery = (StorageBill) tableModel.getCurrentRow();
			boolean ed = dery.getEffective() == null ? false : dery
					.getEffective();
			btnEdit.setEnabled(!ed);
			btnDelete.setEnabled(!ed);
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseStorageBill.this,
								"请选择要删除的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBaseStorageBill.this,
							"你确定要删除数据吗?", "提示！", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					StorageBill dellist = (StorageBill) tableModel
							.getCurrentRow();
					String iOFlag = dellist.getIoFlag();
					
					if (iOFlag.equals(BlsIOStockBillIOF.IMPORT)
							&& iOFlag != null) {
						List listAll = (List) tableModel.getCurrentRows();
						for (int i = 0; i < listAll.size(); i++) {
							System.out.println("listAll.size()="+listAll.size());
							StorageBill sb = (StorageBill) listAll.get(i);
							List list = blsInOutStockBillAction
									.findBillToWareHouseRelationsInByStockBill(
											new Request(CommonVars
													.getCurrUser()), sb);

							// 删除入仓单的话，入仓单据的商品明晰的已转仓单标志要重新设回false
							List list2 = blsInOutStockBillAction
									.findBlsInOutStockBillDetailByStockBill(
											new Request(CommonVars
													.getCurrUser()), sb);
							if (list2.size() != 0) {
								for (int j = 0; j < list2.size(); j++) {
									BlsInOutStockBillDetail bsd = (BlsInOutStockBillDetail) list2
											.get(j);
									bsd.setIsStockBill(false);
									blsInOutStockBillAction
											.saveBlsInOutStockBillDetail(
													new Request(CommonVars
															.getCurrUser()),
													bsd);
								}
							}
							System.out.println("list.size()=" + list.size());
							if (list.size() != 0) {
								for (int a = 0; a < list.size(); a++) {
									BillToWareHouseRelations bwhr = (BillToWareHouseRelations) list
											.get(a);
									blsInOutStockBillAction
											.deleteBillToWareHouseRelations(
													new Request(CommonVars
															.getCurrUser()),
													bwhr);
								}
							}
							storageBillAction.deleteStorageBill(new Request(
									CommonVars.getCurrUser()), sb);
							tableModel.deleteRow(sb);
						}
					} else if (iOFlag.equals(BlsIOStockBillIOF.EXPORT)
							&& iOFlag != null) {
						List listAll = (List) tableModel.getCurrentRows();
						for (int i = 0; i < listAll.size(); i++) {
							StorageBill sbb = (StorageBill) listAll.get(i);
							List list = blsInOutStockBillAction
									.findBillToWareHouseRelationExpInByStockBill(
											new Request(CommonVars
													.getCurrUser()), sbb);
							List list2 = blsInOutStockBillAction
									.findBlsInOutStockBillDetailByStockBills(
											new Request(CommonVars
													.getCurrUser()), sbb);
							if (list2.size() != 0) {
								for (int j = 0; j < list2.size(); j++) {
									BlsInOutStockBillDetail bsd = (BlsInOutStockBillDetail) list2
											.get(j);
									bsd.setIsStockBill(false);
									blsInOutStockBillAction
											.saveBlsInOutStockBillDetail(
													new Request(CommonVars
															.getCurrUser()),
													bsd);
								}
							}
							if (list.size() != 0) {
								for (int a = 0; a < list.size(); a++) {
									BillToWareHouseRelationExp sb = (BillToWareHouseRelationExp) list
											.get(a);
									blsInOutStockBillAction
											.deleteBillToWareHouseRelationExp(
													new Request(CommonVars
															.getCurrUser()), sb);
								}
							}
							storageBillAction.deleteStorageBill(new Request(
									CommonVars.getCurrUser()), sbb);
							tableModel.deleteRow(sbb);
						}
					}
					setState();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("关闭");
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
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
		if (tfStorageBill == null) {
			tfStorageBill = new JTable();
		}
		tfStorageBill.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseStorageBill.this,
								"请选择数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (FmBaseStorageBill.this instanceof FmImportStorageBill) {
						DgImportStorage dg = new DgImportStorage();
						dg.setDataState(DataState.BROWSE);
						dg.setFathertableModel(tableModel);
						dg.setStorageBill((StorageBill) tableModel
								.getCurrentRow());
						dg.setVisible(true);
						setState();
					} else if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
						DgExportStorage dg = new DgExportStorage();
						dg.setDataState(DataState.BROWSE);
						dg.setFathertableModel(tableModel);
						dg.setStorageBill((StorageBill) tableModel
								.getCurrentRow());
						dg.setVisible(true);
						setState();
					}
				} else if (e.getClickCount() == 1) {
					setState();
				}
			}
		});
		return tfStorageBill;
	}

	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(tfStorageBill, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("仓单号", "billNo", 100)); //  
						list.add(addColumn("仓库编码", "wareHouseCode.code", 100));
						list.add(addColumn("进出仓标志", "ioFlag", 80));
						list.add(addColumn("生效", "effective", 100));
						list.add(addColumn("车次号", "delivery.deliveryNo", 100));
						list
								.add(addColumn("申报状态", "delivery.declareState",
										100));
						list.add(addColumn("仓单类型", "billType", 100));
						list.add(addColumn("申报海关", "customsCode.name", 100));
						list.add(addColumn("仓单有效期", "validDate", 90));
						list.add(addColumn("订单号", "orderNo", 100));
						list.add(addColumn("计划编号", "planNo", 100));
						list.add(addColumn("总件数", "packCount", 100));
						list.add(addColumn("包装种类", "wrap.name", 100));
						list.add(addColumn("供货商", "supplierCd.name", 150));
						list.add(addColumn("供货方企业", "corrOwnerCode.name", 150));
						list.add(addColumn("毛重", "grossWeight", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("手册号", "manualNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("商品项数", "itemsCount", 90));
						list.add(addColumn("进出口岸", "iePort.name", 100));
						list.add(addColumn("转出方名称", "outName", 150));
						list.add(addColumn("转入方名称", "inName", 150));
						return list;
					}
				});
		tfStorageBill.getColumnModel().getColumn(7).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null || value.toString().trim().equals("")) {
							super.setText("");
						} else if (value.toString().trim().equals("00")) {
							super.setText("申报初始库存");
						} else if (value.toString().trim().equals("01")) {
							super.setText("后报关方式");
						} else if (value.toString().trim().equals("02")) {
							super.setText("先报关分批送货方式");
						} else if (value.toString().trim().equals("03")) {
							super.setText("特殊审核");
						} else {
							super.setText("");
						}
						return this;
					}

				});
		tfStorageBill.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value == null || value.toString().trim().equals("")) {
							super.setText("");
						} else if (value.toString().equals("I")) {
							super.setText("进仓");
						} else if (value.toString().equals("O")) {
							super.setText("出仓");
						} else {
							super.setText("");
						}
						return this;
					}

				});
		tfStorageBill.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		tfStorageBill.getColumnModel().getColumn(6).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super
								.setText((value == null) ? castValue(DeclareState.APPLY_POR)
										: castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		return tableModel;
	}

	/**
	 * This method initializes jButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton5() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseStorageBill.this,
								"请选择要修改的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (FmBaseStorageBill.this instanceof FmImportStorageBill) {
						DgImportStorage dg = new DgImportStorage();
						dg.setDataState(DataState.BROWSE);
						dg.setFathertableModel(tableModel);
						dg.setStorageBill((StorageBill) tableModel
								.getCurrentRow());
						dg.setVisible(true);
						setState();
					} else if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
						DgExportStorage dg = new DgExportStorage();
						dg.setDataState(DataState.BROWSE);
						dg.setFathertableModel(tableModel);
						dg.setStorageBill((StorageBill) tableModel
								.getCurrentRow());
						dg.setVisible(true);
						setState();
					}
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;
	private JToolBar jJToolBarBar = null;
	private JPanel jPanel1 = null;
	private JButton btnRefresh = null;
	private JButton btnEffect = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel = null;
	private JCalendarComboBox cbbStartDate = null;
	private JCalendarComboBox cbbEndDate = null;
	private JButton btnSerch1 = null;
	private JLabel jLabel1 = null;

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
					return FmBaseStorageBill.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {

					return FmBaseStorageBill.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	private List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		String inout = "I";
		if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
			inout = "O";
		}
		return this.storageBillAction.findStorageBill(new Request(CommonVars
				.getCurrUser()), index, length, property, value, isLike, inout);
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getPnCommonQueryPage());
		}
		return jJToolBarBar;
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
			jPanel1.add(getJJToolBarBar(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes btnEffect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnEffect() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List<StorageBill> bills = tableModel.getCurrentRows();
					if (bills == null || bills.size()<1) {
						JOptionPane.showMessageDialog(FmBaseStorageBill.this,
								"请选择要生效的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					StringBuffer infos = new StringBuffer();
					for(StorageBill bill : bills){
						if(bill.getEffective()!=null && bill.getEffective()){
							infos.append("仓单号："+bill.getBillNo()+"已经生效！\n");
							continue;
						}
						List lsit = storageBillAction
						.findStorageBillAfterForDelivery(new Request(
								CommonVars.getCurrUser()), bill);
						if (lsit == null || lsit.size() == 0 || lsit.get(0) == null) {
							infos.append("仓单号："+bill.getBillNo()+"仓单表体为空，不能生效！\n");
							continue;
						}
						String ret = storageBillAction.checkStorageBill(
								new Request(CommonVars.getCurrUser()), bill);
						if(ret!=null && !"".equals(ret)){
						    infos.append("仓单号："+bill.getBillNo()+ret+"\n");
						    continue;
						}
						if (bill.getId() != null) {
							bill = storageBillAction.findStorageBillByID(
									new Request(CommonVars.getCurrUser()),
									bill.getId());
						}
						bill.setEffective(true);
						bill = (StorageBill) storageBillAction
								.saveOrUpdateObject(new Request(CommonVars
										.getCurrUser()), bill);
						tableModel.updateRow(bill);
						infos.append("仓单号："+bill.getBillNo()+"生效成功！\n");
					}
					String errorInfo = infos.toString();
					if(errorInfo==null || "".equals(errorInfo)){
						JOptionPane.showMessageDialog(FmBaseStorageBill.this,
								"全部生效成功！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
					}else{
						DgMessage dgMessage = new DgMessage("生效成败记录",errorInfo);
					}
				}
			});
		}
		return btnEffect;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(207, 5, 17, 26));
			jLabel1.setText("到");
			jLabel = new JLabel();
			jLabel.setText("开始日期从：");
			jLabel.setBounds(new Rectangle(25, 5, 90, 26));
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(0, 40));
			jPanel2.add(jLabel, null);
			jPanel2.add(getCbbStartDate(), null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getBtnSerch1(), null);
			jPanel2.add(jLabel1, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbStartDate	
	 * 	
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	
	 */
	private JCalendarComboBox getCbbStartDate() {
		if (cbbStartDate == null) {
			cbbStartDate = new JCalendarComboBox();
			cbbStartDate.setBounds(new Rectangle(112, 5, 86, 26));
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
			cbbEndDate.setBounds(new Rectangle(226, 5, 95, 26));
			cbbEndDate.setDate(new Date());
			cbbEndDate.setPreferredSize(new Dimension(85, 25));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnSerch1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnSerch1() {
		if (btnSerch1 == null) {
			btnSerch1 = new JButton();
			btnSerch1.setBounds(new Rectangle(324, 5, 60, 26));
			btnSerch1.setText("刷新");
			btnSerch1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					serch();
				}
			});
		}
		return btnSerch1;
	}
	
	private void serch(){
		List list = null;
		if (FmBaseStorageBill.this instanceof FmExportStorageBill) {
			String ioFlag = "O";
			list = storageBillAction.findStorageBill(new Request(
					CommonVars.getCurrUser()),cbbStartDate.getDate(),cbbEndDate.getDate(),ioFlag);
		}else{
			String ioFlag = "I";
			list = storageBillAction.findStorageBill(new Request(
					CommonVars.getCurrUser()),cbbStartDate.getDate(),cbbEndDate.getDate(),ioFlag);
		}
		initTable(list);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
