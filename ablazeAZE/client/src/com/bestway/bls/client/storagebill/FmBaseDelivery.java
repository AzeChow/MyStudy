package com.bestway.bls.client.storagebill;

/**
 * 车次管理checked by kcb 2009-1-10
 * 
 * @author kangbo
 * 
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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
import com.bestway.bls.action.BlsMessageAction;
import com.bestway.bls.action.StorageBillAction;
import com.bestway.bls.client.message.BlsMessageHelper;
import com.bestway.bls.entity.BlsReceiptResult;
import com.bestway.bls.entity.BlsServiceType;
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
import javax.swing.JLabel;
import java.awt.Rectangle;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.util.Date;

public class FmBaseDelivery extends JInternalFrameBase {

	private JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JButton btnAdd = null;
	private JButton btnEdit = null;
	private JButton btnDelete = null;
	private JButton btnCancel = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbDelivery = null;
	private JTableListModel tableModel;
	private StorageBillAction storageBillAction;
	private BlsMessageAction blsMessageAction = null;
	private JButton btnApply = null;
	private JButton btnBorwse = null;
	private JButton btnProcces = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBaseDelivery() {
		super();
		storageBillAction = (StorageBillAction) CommonVars
				.getApplicationContext().getBean("storageBillAction");
		blsMessageAction = (BlsMessageAction) CommonVars
		.getApplicationContext().getBean("blsMessageAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(743, 526));
		this.setTitle("车次管理");
		this.setContentPane(getJPanel());

	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			int inout = 0;
			if (FmBaseDelivery.this instanceof FmExportDelivery) {
				inout = 1;
			}
//			this.pnCommonQueryPage.setInitState();
			// List list = storageBillAction.findDeliveryByInOut(new Request(
			// CommonVars.getCurrUser()), inout);
			// initTable(list);
			serch();
			setState();
			super.setVisible(f);
		}

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
			jToolBar.add(getJButton6());
			jToolBar.add(getJButton5());
			jToolBar.add(getJButton7());
			jToolBar.add(getBtnEffect());
			jToolBar.add(getJButton2());
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
					if (FmBaseDelivery.this instanceof FmImportDelivery) {
						DgImportDelivery dg = new DgImportDelivery();
						dg.setDataState(DataState.ADD);
						dg.setFathertableModel(tableModel);
						dg.setVisible(true);
						setState();
					} else if (FmBaseDelivery.this instanceof FmExportDelivery) {
						DgExportDelivery dg = new DgExportDelivery();
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
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"请选择要修改的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (FmBaseDelivery.this instanceof FmImportDelivery) {
						DgImportDelivery dg = new DgImportDelivery();
						dg.setDataState(DataState.EDIT);
						dg.setFathertableModel(tableModel);
						dg.setDelivery((Delivery) tableModel.getCurrentRow());
						dg.setVisible(true);
						setState();
					} else if (FmBaseDelivery.this instanceof FmExportDelivery) {
						DgExportDelivery dg = new DgExportDelivery();
						dg.setDataState(DataState.EDIT);
						dg.setFathertableModel(tableModel);
						dg.setDelivery((Delivery) tableModel.getCurrentRow());
						dg.setVisible(true);
						setState();
					}
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
	private JButton getJButton2() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"请选择要删除的数据!", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					Delivery dellist = (Delivery) tableModel.getCurrentRow();
					List list = storageBillAction.findStorageBillForDelivery(
							new Request(CommonVars.getCurrUser()), dellist);
					String warning = "";
					for (int i = 0; i < list.size(); i++) {
						StorageBill sbill = (StorageBill) list.get(i);
						if (sbill.getEffective() != null
								&& sbill.getEffective()) {
							warning += ((sbill.getBillNo() == null ? "" : sbill
									.getBillNo()) + ";");
						}
					}
					if (!warning.equals("")) {
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"表体以下仓单已经生效，所以该车次不能删除！\n仓单号如下：" + warning,
								"提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBaseDelivery.this,
							"你确定要删除数据吗?", "提示！", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}

					storageBillAction.deleteDelivery(new Request(CommonVars
							.getCurrUser()), dellist);
					tableModel.deleteRow(dellist);
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
			jScrollPane.setViewportView(getJScrollPane1());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (tbDelivery == null) {
			tbDelivery = new JTable();
			tbDelivery.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(FmBaseDelivery.this,
									"请选择要修改的数据！", "提示！",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (FmBaseDelivery.this instanceof FmImportDelivery) {
							DgImportDelivery dg = new DgImportDelivery();
							dg.setDataState(DataState.BROWSE);
							dg.setFathertableModel(tableModel);
							dg.setDelivery((Delivery) tableModel
									.getCurrentRow());
							dg.setVisible(true);
							setState();
						} else if (FmBaseDelivery.this instanceof FmExportDelivery) {
							DgExportDelivery dg = new DgExportDelivery();
							dg.setDataState(DataState.BROWSE);
							dg.setFathertableModel(tableModel);
							dg.setDelivery((Delivery) tableModel
									.getCurrentRow());
							dg.setVisible(true);
							setState();
						}
					} else if (e.getClickCount() == 1) {
						setState();
					}
				}
			});
		}
		return tbDelivery;
	}

	private void setState() {
		if (tableModel.getCurrentRow() != null) {
			Delivery dery = (Delivery) tableModel.getCurrentRow();
			boolean ed = dery.getEffective() == null ? false : dery
					.getEffective();
			btnEdit.setEnabled(!ed);
			btnDelete.setEnabled(!ed);
			btnApply
					.setEnabled((dery.getDeclareState() == null || DeclareState.APPLY_POR
							.equals(dery.getDeclareState()))
							&& (dery.getEffective() != null && dery
									.getEffective()));
			btnProcces.setEnabled((dery.getDeclareState() != null && dery
					.getDeclareState().equals(DeclareState.WAIT_EAA))
					&& (dery.getEffective() != null && dery.getEffective()));
			this.btnEffect.setEnabled(!ed);
		}
	}

	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(tbDelivery, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("车次号", "deliveryNo", 100));
						list.add(addColumn("企业名称", "tradeCo.name", 150)); // 
						list.add(addColumn("企业编码", "tradeCo.code", 100)); //  
						list.add(addColumn("业务类型", "operType", 70));
						list.add(addColumn("生效", "effective", 60));
						list.add(addColumn("申报状态", "declareState", 80));
						list.add(addColumn("车牌号", "vehicleLicense", 100));
						list.add(addColumn("承运单位名称", "carrierName", 150));
						list.add(addColumn("承运单位编码", "carrierCode", 100));
						list.add(addColumn("仓单数", "billCount", 100));
						list
								.add(addColumn("预计到达日期",
										"schedularArrivalDate", 90));
						list.add(addColumn("载货物件数", "packNo", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("第一集装箱号", "containerNo1", 150));
						list.add(addColumn("第二个集装箱号", "containerNo2", 150));
						list.add(addColumn("第一关锁号", "sealNo1", 150));
						list.add(addColumn("第二关锁号", "sealNo2", 150));
						list.add(addColumn("修改标志", "modifyMark", 80));
						list.add(addColumn("报文发送时间", "messTimeStamp", 90));

						return list;
					}
				});
		// jTable.getColumnModel().getColumn(4).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// if (value == null || value.toString().trim().equals("")) {
		// super.setText("");
		// } else if (value.toString().trim().equals("00")) {
		// super.setText("申报初始库存");
		// } else if (value.toString().trim().equals("01")) {
		// super.setText("后报关方式");
		// } else if (value.toString().trim().equals("02")) {
		// super.setText("先报关分批送货方式");
		// } else if (value.toString().trim().equals("03")) {
		// super.setText("特殊审核");
		// } else {
		// super.setText("");
		// }
		// return this;
		// }
		//
		// });
		tbDelivery.getColumnModel().getColumn(5).setCellRenderer(
				new TableCheckBoxRender());
		tbDelivery.getColumnModel().getColumn(6).setCellRenderer(
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
		tbDelivery.getColumnModel().getColumn(19).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super
								.setText((value == null) ? castValue(ModifyMarkState.UNCHANGE)
										: castValue(value));
						return this;
					}

					private String castValue(Object value) {
						return ModifyMarkState.getModifyMarkSpec(value
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
		if (btnApply == null) {
			btnApply = new JButton();
			btnApply.setText("海关申报");
			btnApply.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (true) {
					// JOptionPane.showMessageDialog(FmBaseDelivery.this,
					// "此功能稍候推出！", "提示！", JOptionPane.WARNING_MESSAGE);
					// return;
					// }
					if(blsMessageAction.checkIsAutoDeclare(new Request(
							CommonVars.getCurrUser()))){
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"系统设定自动进行海关申报，所以不能进行手动申报！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"请选择要申报的车次资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBaseDelivery.this,
							"确定要进行海关申报吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					Delivery delivery = (Delivery) tableModel.getCurrentRow();
					delivery = storageBillAction.applyDelivery(new Request(
							CommonVars.getCurrUser()), delivery);
					String declareInfo = "";
					if (DeclareState.PROCESS_EXE.equals(delivery
							.getDeclareState())) {
						declareInfo = ("车次申报" + delivery.getDeliveryNo() + " 申报成功！");
					} else if (DeclareState.APPLY_POR.equals(delivery
							.getDeclareState())) {
						declareInfo = ("车次申报" + delivery.getDeliveryNo() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA.equals(delivery
							.getDeclareState())) {
						declareInfo = ("车次申报" + delivery.getDeliveryNo() + " 正在等待审批！");
					}
					tableModel.updateRow(delivery);
					setState();
					JOptionPane
							.showMessageDialog(FmBaseDelivery.this,
									declareInfo, "提示！",
									JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnApply;
	}

	/**
	 * This method initializes jButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton6() {
		if (btnBorwse == null) {
			btnBorwse = new JButton();
			btnBorwse.setText("浏览");
			btnBorwse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"请选择要浏览的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (FmBaseDelivery.this instanceof FmImportDelivery) {
						DgImportDelivery dg = new DgImportDelivery();
						dg.setDataState(DataState.BROWSE);
						dg.setFathertableModel(tableModel);
						dg.setDelivery((Delivery) tableModel.getCurrentRow());
						dg.setVisible(true);
						setState();
					} else if (FmBaseDelivery.this instanceof FmExportDelivery) {
						DgExportDelivery dg = new DgExportDelivery();
						dg.setDataState(DataState.BROWSE);
						dg.setFathertableModel(tableModel);
						dg.setDelivery((Delivery) tableModel.getCurrentRow());
						dg.setVisible(true);
						setState();
					}
				}
			});
		}
		return btnBorwse;
	}

	/**
	 * This method initializes jButton7
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton7() {
		if (btnProcces == null) {
			btnProcces = new JButton();
			btnProcces.setText("回执处理");
			btnProcces.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(blsMessageAction.checkIsAutoDeclare(new Request(
							CommonVars.getCurrUser()))){
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"系统设定自动进行回执处理，所以不能进行手动回执处理！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseDelivery.this,
								"请选择要回执处理的车次资料！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBaseDelivery.this,
							"确定要进行回执处理吗？", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
						return;
					}
					Delivery delivery = (Delivery) tableModel.getCurrentRow();
					BlsReceiptResult blsReceiptResult = BlsMessageHelper
							.getInstance().showBlsReceiptFile(
									BlsServiceType.MANIFEST_DECLARE,
									delivery.getId());
					if (blsReceiptResult == null) {
						return;
					}
					delivery = storageBillAction.processDelivery(new Request(
							CommonVars.getCurrUser()), delivery,
							blsReceiptResult);
					String resultInfo = "";
					if (DeclareState.PROCESS_EXE.equals(delivery
							.getDeclareState())) {
						resultInfo = ("车次申报" + delivery.getDeliveryNo() + " 申报成功！");
					} else if (DeclareState.APPLY_POR.equals(delivery
							.getDeclareState())) {
						resultInfo = ("车次申报" + delivery.getDeliveryNo() + " 申报失败！");
					} else if (DeclareState.WAIT_EAA.equals(delivery
							.getDeclareState())) {
						resultInfo = ("车次申报" + delivery.getDeliveryNo() + " 正在等待审批！");
					}
					tableModel.updateRow(delivery);
					setState();
					JOptionPane.showMessageDialog(FmBaseDelivery.this,
							resultInfo, "提示！", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return btnProcces;
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
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbEndDate = null;
	private JButton btnSerch1 = null;

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
					return FmBaseDelivery.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {

					return FmBaseDelivery.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	private List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		String inout = "I";
		if (FmBaseDelivery.this instanceof FmExportDelivery) {
			inout = "O";
		}
		return this.storageBillAction.findDelivery(new Request(CommonVars
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
					List<Delivery> selectDelivery = tableModel.getCurrentRows();
					if(selectDelivery==null || selectDelivery.size()==0){
					   JOptionPane.showMessageDialog(FmBaseDelivery.this,
							"仓单信息为空，不能第生效！", "提示！",
							JOptionPane.WARNING_MESSAGE);
					   return;
					}
					StringBuffer bf = new StringBuffer();
					for (Delivery delivery : selectDelivery) {
						if(delivery.getEffective()!=null && delivery.getEffective()){
							bf.append("车次号："+delivery.getDeliveryNo()+" ：已经生效！\n");
							continue;
						}
						List list = storageBillAction
								.findStorageBillForDelivery(new Request(
										CommonVars.getCurrUser()), delivery);
						if (list == null || list.isEmpty()) {
							bf.append("车次号："+delivery.getDeliveryNo()+" ：仓单信息为空,生效失败！\n");
							continue;
						}
						String warning = "";
						for (int i = 0; i < list.size(); i++) {
							StorageBill sbill = (StorageBill) list.get(i);
							if (sbill.getEffective() == null
									|| !sbill.getEffective()) {
								warning += ((sbill.getBillNo() == null ? ""
										: sbill.getBillNo()) + ";");
							}
						}
						if (!warning.equals("")) {
							bf.append("车次号："+delivery.getDeliveryNo()+" ：以下仓单没有生效,生效失败！"+warning+"\n");
							continue;
						}
						delivery.setEffective(true);
						delivery = (Delivery) storageBillAction
								.saveOrUpdateObject(new Request(CommonVars
										.getCurrUser()), delivery);
						tableModel.updateRow(delivery);
						bf.append("车次号："+delivery.getDeliveryNo()+" ：生效成功！\n");
					}
					DgMessage message = new DgMessage("车次生效",bf.toString());
					btnEffect.setEnabled(false);
					btnApply.setEnabled(true);
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
			jLabel1.setBounds(new Rectangle(197, 5, 21, 26));
			jLabel1.setText("\u5230");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(25, 5, 83, 26));
			jLabel.setText("\u5f00\u59cb\u65e5\u671f\u4ece\uff1a");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(0, 40));
			jPanel2.add(jLabel, null);
			jPanel2.add(getCbbStartDate(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getCbbEndDate(), null);
			jPanel2.add(getBtnSerch1(), null);
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
			cbbStartDate.setBounds(new Rectangle(110, 5, 85, 26));
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
			cbbEndDate.setBounds(new Rectangle(220, 5, 92, 26));
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
			btnSerch1.setBounds(new Rectangle(315, 5, 69, 26));
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

		String inout = "I";
		if (FmBaseDelivery.this instanceof FmExportDelivery) {
			inout = "O";
		}
		List list = storageBillAction.findDelivery(new Request(CommonVars
				.getCurrUser()), cbbStartDate.getDate(), cbbEndDate.getDate(),inout);
	   initTable(list);	
	
	}
} // @jve:decl-index=0:visual-constraint="10,10"
