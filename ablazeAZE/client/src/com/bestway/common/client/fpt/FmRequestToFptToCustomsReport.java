package com.bestway.common.client.fpt;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.license.LicenseClient;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.TempCasBillTOFptTOCustomsReport;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Administrator // change the template for this generated type comment
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
public class FmRequestToFptToCustomsReport extends JInternalFrameBase {

	private JTabbedPane jTabbedPane = null;

	private JSplitPane pnRquestToCustomsList = null;

	private JSplitPane pnFptToCustomsList = null;

	private JTable tbList = null;

	private JScrollPane jScrollPane = null;

	private JTable tbStatus = null;

	private JScrollPane jScrollPane1 = null;

	private AttributiveCellTableModel rquestToCustomsTableModel = null;

	private AttributiveCellTableModel fptToCustomsTableModel = null;

	private JToolBar jToolBar = null;

	private JButton btrquestToCustoms = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JToolBar jToolBar1 = null;

	private JButton btFptToCustoms = null;

	private JButton jButton11 = null;

	private JButton jButton21 = null;

	private List list = null; // @jve:decl-index=0:

	private JButton btnNew = null;
	private String billNo = null; // @jve:decl-index=0:
	private String bomNo = null;
	private Date beginDate = null;
	private Date endDate = null; // @jve:decl-index=0:
	private ScmCoc scmCoc = null; // @jve:decl-index=0:
	private boolean isImport;
	private boolean isMark = false;

	private FptManageAction fptManageAction = null;

	private JButton btnRecivBgdNum = null;

	private JButton btnCancelBgdNum = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmRequestToFptToCustomsReport() {
		super();
		initialize();
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		tbList = new MultiSpanCellTable();
		tbStatus = new MultiSpanCellTable();
		fptManageAction.permissionCheckCorresponding(new Request(CommonVars.getCurrUser()));
		list = new Vector();
		showEmptyData(list);
		showData(list);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("发/退货对应报表");
		this.setContentPane(getJTabbedPane());
		this.setSize(725, 513);
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {

					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						if (!LicenseClient
								.getInstance(
										CommonVars.getCurrUser().getCompany()
												.getName())
								.checkFptManagePermisson()) {
							JOptionPane.showMessageDialog(
									FmRequestToFptToCustomsReport.this,
									"没有使用结转单据对应表的权限，如果需要请联系百思维！");
							doDefaultCloseAction();
						}
					}
				});
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("单据 / 收送货 / 报关单对应表", null,
					getPnRquestToCustomsList(), null);
			jTabbedPane.addTab("收送货 / 报关对应表", null, getPnFptToCustomsList(),
					null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							JTabbedPane tab = (JTabbedPane) e.getSource();
							if (tab.getSelectedIndex() == 0) {
								DgRequestToFptToCustomsQueryCondition dg = new DgRequestToFptToCustomsQueryCondition();
								List list = dg.getLsResult();
								if (list != null) {
									showEmptyData(list);
								}
							} else if (tab.getSelectedIndex() == 1) {
								DgFptToCustomsQueryCondition dg = new DgFptToCustomsQueryCondition();
								List list = dg.getLsResult();
								if (list != null) {
									showData(list);
								}

							}
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes pnRquestToCustomsList
	 * 
	 * @return javax.swing.JPanel
	 */
	private JSplitPane getPnRquestToCustomsList() {
		if (pnRquestToCustomsList == null) {
			pnRquestToCustomsList = new JSplitPane();
			pnRquestToCustomsList.setDividerLocation(35);
			pnRquestToCustomsList.setOrientation(JSplitPane.VERTICAL_SPLIT);
			pnRquestToCustomsList.setBottomComponent(getJScrollPane());
			pnRquestToCustomsList.setTopComponent(getJToolBar());
			pnRquestToCustomsList.setDividerSize(3);
		}
		return pnRquestToCustomsList;
	}

	/**
	 * This method initializes pnFptToCustomsList
	 * 
	 * @return javax.swing.JPanel
	 */
	private JSplitPane getPnFptToCustomsList() {
		if (pnFptToCustomsList == null) {
			pnFptToCustomsList = new JSplitPane();
			pnFptToCustomsList.setLayout(null);
			pnFptToCustomsList.setDividerLocation(35);
			pnFptToCustomsList.setOrientation(JSplitPane.VERTICAL_SPLIT);
			pnFptToCustomsList.setBottomComponent(getJScrollPane1());
			pnFptToCustomsList.setTopComponent(getJToolBar1());
			pnFptToCustomsList.setDividerSize(3);
		}
		return pnFptToCustomsList;
	}

	/**
	 * This method initializes tbList
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbList() {
		if (tbList == null) {
			tbList = new JTable();
		}
		return tbList;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbStatus
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbStatus() {
		if (tbStatus == null) {
			tbStatus = new JTable();
		}
		return tbStatus;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbStatus());
		}
		return jScrollPane1;
	}

	private JTableListModel showEmptyData(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		rquestToCustomsTableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbList, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类型", "inOutFlag", 60));
						list.add(addColumn("单据单号",
								"billDetail.billMaster.billNo", 100));
						list.add(addColumn("客户",
								"billDetail.billMaster.scmCoc.name", 150));
						list.add(addColumn("生效日期",
								"billDetail.billMaster.validDate", 80));
						list
								.add(addColumn("对应报关单号", "billDetail.customNo",
										150));
						list
								.add(addColumn("对应报关数量",
										"billDetail.customNum", 80));
						list.add(addColumn("工厂货号", "billDetail.ptPart", 120));
						list.add(addColumn("名称", "billDetail.ptName", 100));
						list.add(addColumn("规格", "billDetail.ptSpec", 100));
						list.add(addColumn("单位", "billDetail.ptUnit.name", 50));
						list.add(addColumn("数量", "billDetail.ptAmount", 50));
						list.add(addColumn("净重", "billDetail.netWeight", 50));
						// list.add(addColumn("送货单单号", "fptBillItem.",
						// 150));

						list.add(addColumn("申请单号",
								"fptBillItem.fptBillHead.appNo", 80));
						list.add(addColumn("料号", "fptBillItem.copGNo", 120));
						list.add(addColumn("归并前商品名称", "fptBillItem.copGName",
								100));
						list.add(addColumn("归并前型号规格", "fptBillItem.copGModel",
								100));
						list.add(addColumn("序号", "fptBillItem.trGno", 40));
						list.add(addColumn("商品编码", "fptBillItem.complex.code",
								80));
						list
								.add(addColumn("商品名称", "fptBillItem.commName",
										150));
						list
								.add(addColumn("规格型号", "fptBillItem.commSpec",
										150));
						list.add(addColumn("交易单位",
								"fptBillItem.tradeUnit.name", 60));
						list.add(addColumn("交易数量", "fptBillItem.tradeQty", 80));
						list
								.add(addColumn("申报单位", "fptBillItem.unit.name",
										80));
						list.add(addColumn("申报数量", "fptBillItem.qty", 80));
						list
								.add(addColumn(
										"手册/账册号",
										"customsCommInfo.baseCustomsDeclaration.emsHeadH2k",
										100));
						list
								.add(addColumn(
										"报关单流水号",
										"customsCommInfo.baseCustomsDeclaration.serialNumber",
										80));
						list
								.add(addColumn(
										"报关单号",
										"customsCommInfo.baseCustomsDeclaration.customsDeclarationCode",
										150));
						list
								.add(addColumn(
										"申请单号",
										"customsCommInfo.baseCustomsDeclaration.customsEnvelopBillNo",
										80));
						list.add(addColumn("商品序号",
								"customsCommInfo.commSerialNo", 60));
						list.add(addColumn("商品编码",
								"customsCommInfo.complex.code", 80));
						list.add(addColumn("商品名称", "customsCommInfo.commName",
								150));
						list.add(addColumn("型号规格", "customsCommInfo.commSpec",
								150));
						list.add(addColumn("单位", "customsCommInfo.unit.name",
								40));
						list.add(addColumn("数量", "customsCommInfo.commAmount",
								50));

						return list;
					}
				});
		tbList.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString()));
						return this;
					}
				});
		tbList.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							this.setForeground(Color.RED);
						} else {
							this.setForeground(Color.BLACK);
						}
						return this;
					}
				});
		((MultiSpanCellTable) tbList).splitRows2(new int[] { 25, 26, 29 });
		((MultiSpanCellTable) tbList).combineRowsByIndeies(new int[] { 25, 26,
				27, 28, 29, 30, 31, 32, 33, 34 }, new int[] { 25, 26, 27, 28,
				29, 30, 31, 32, 33, 34 });

		TableColumnModel cm = tbList.getColumnModel();
		ColumnGroup gRequest = new ColumnGroup("收送货与报关对应表");
		gRequest.add(cm.getColumn(1));
		gRequest.add(cm.getColumn(2));
		gRequest.add(cm.getColumn(3));
		gRequest.add(cm.getColumn(4));
		gRequest.add(cm.getColumn(5));
		gRequest.add(cm.getColumn(6));
		gRequest.add(cm.getColumn(7));
		gRequest.add(cm.getColumn(8));
		gRequest.add(cm.getColumn(9));
		gRequest.add(cm.getColumn(10));
		gRequest.add(cm.getColumn(11));
		gRequest.add(cm.getColumn(12));
		ColumnGroup gApply = new ColumnGroup("转厂收发货单据");
		gApply.add(cm.getColumn(13));
		gApply.add(cm.getColumn(14));
		gApply.add(cm.getColumn(15));
		gApply.add(cm.getColumn(16));
		gApply.add(cm.getColumn(17));
		gApply.add(cm.getColumn(18));
		gApply.add(cm.getColumn(19));
		gApply.add(cm.getColumn(20));
		gApply.add(cm.getColumn(21));
		gApply.add(cm.getColumn(22));
		gApply.add(cm.getColumn(23));
		gApply.add(cm.getColumn(24));
		ColumnGroup gCustoms = new ColumnGroup("报关单信息");
		gCustoms.add(cm.getColumn(25));
		gCustoms.add(cm.getColumn(26));
		gCustoms.add(cm.getColumn(27));
		gCustoms.add(cm.getColumn(28));
		gCustoms.add(cm.getColumn(29));
		gCustoms.add(cm.getColumn(30));
		gCustoms.add(cm.getColumn(31));
		gCustoms.add(cm.getColumn(32));
		gCustoms.add(cm.getColumn(33));
		gCustoms.add(cm.getColumn(34));
		GroupableTableHeader header = (GroupableTableHeader) tbList
				.getTableHeader();
		header.addColumnGroup(gRequest);
		header.addColumnGroup(gApply);
		header.addColumnGroup(gCustoms);
		jScrollPane.setViewportView(tbList);
		rquestToCustomsTableModel.setMiRenderColumnEnabled(false);
		return rquestToCustomsTableModel;
	}

	private JTableListModel showData(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		fptToCustomsTableModel = new AttributiveCellTableModel(
				(MultiSpanCellTable) tbStatus, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类型", "inOutFlag", 60));
						list.add(addColumn("申请单号",
								"fptBillItem.fptBillHead.appNo", 80));
						list.add(addColumn("客户",
								"fptBillItem.fptBillHead.customer.name", 150));
						list.add(addColumn("料号", "fptBillItem.copGNo", 120));
						list.add(addColumn("归并前商品名称", "fptBillItem.copGName",
								100));
						list.add(addColumn("归并前型号规格", "fptBillItem.copGModel",
								100));
						list.add(addColumn("序号", "fptBillItem.trGno", 40));
						list.add(addColumn("商品编码", "fptBillItem.complex.code",
								80));
						list
								.add(addColumn("商品名称", "fptBillItem.commName",
										150));
						list
								.add(addColumn("规格型号", "fptBillItem.commSpec",
										150));
						list.add(addColumn("交易单位",
								"fptBillItem.tradeUnit.name", 60));
						list.add(addColumn("交易数量", "fptBillItem.tradeQty", 80));
						list
								.add(addColumn("申报单位", "fptBillItem.unit.name",
										80));
						list.add(addColumn("申报数量", "fptBillItem.qty", 80));
						list
								.add(addColumn(
										"手册/账册号",
										"customsCommInfo.baseCustomsDeclaration.emsHeadH2k",
										100));
						list
								.add(addColumn(
										"报关单流水号",
										"customsCommInfo.baseCustomsDeclaration.serialNumber",
										80));
						list
								.add(addColumn(
										"报关单号",
										"customsCommInfo.baseCustomsDeclaration.customsDeclarationCode",
										150));
						list
								.add(addColumn(
										"申请单号",
										"customsCommInfo.baseCustomsDeclaration.customsEnvelopBillNo",
										80));
						list.add(addColumn("商品序号",
								"customsCommInfo.commSerialNo", 60));
						list.add(addColumn("商品编码",
								"customsCommInfo.complex.code", 80));
						list.add(addColumn("商品名称", "customsCommInfo.commName",
								150));
						list.add(addColumn("型号规格", "customsCommInfo.commSpec",
								150));
						list.add(addColumn("单位", "customsCommInfo.unit.name",
								40));
						list.add(addColumn("数量", "customsCommInfo.commAmount",
								50));

						return list;
					}
				});
		tbStatus.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : FptInOutFlag
								.getInOutFlagSpec(value.toString()));
						return this;
					}
				});
		((MultiSpanCellTable) tbStatus).splitRows2(new int[] { 15, 16, 19 });
		((MultiSpanCellTable) tbStatus).combineRowsByIndeies(new int[] { 15,
				16, 17, 18, 19, 20, 21, 22, 23, 24 }, new int[] { 15, 16, 17,
				18, 19, 20, 21, 22, 23, 24 });
		TableColumnModel cm = tbStatus.getColumnModel();
		ColumnGroup gApply = new ColumnGroup("转厂收发货单据");
		gApply.add(cm.getColumn(1));
		gApply.add(cm.getColumn(2));
		gApply.add(cm.getColumn(3));
		gApply.add(cm.getColumn(4));
		gApply.add(cm.getColumn(5));
		gApply.add(cm.getColumn(6));
		gApply.add(cm.getColumn(7));
		gApply.add(cm.getColumn(8));
		gApply.add(cm.getColumn(9));
		gApply.add(cm.getColumn(10));
		gApply.add(cm.getColumn(11));
		gApply.add(cm.getColumn(12));
		gApply.add(cm.getColumn(13));
		gApply.add(cm.getColumn(14));
		ColumnGroup gCustoms = new ColumnGroup("报关单信息");
		gCustoms.add(cm.getColumn(15));
		gCustoms.add(cm.getColumn(16));
		gCustoms.add(cm.getColumn(17));
		gCustoms.add(cm.getColumn(18));
		gCustoms.add(cm.getColumn(19));
		gCustoms.add(cm.getColumn(20));
		gCustoms.add(cm.getColumn(21));
		gCustoms.add(cm.getColumn(22));
		gCustoms.add(cm.getColumn(23));
		gCustoms.add(cm.getColumn(24));
		GroupableTableHeader header = (GroupableTableHeader) tbStatus
				.getTableHeader();
		header.addColumnGroup(gApply);
		header.addColumnGroup(gCustoms);
		jScrollPane1.setViewportView(tbStatus);
		fptToCustomsTableModel.setMiRenderColumnEnabled(false);
		return fptToCustomsTableModel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtrquestToCustoms());
			jToolBar.add(getBtnRecivBgdNum());
			jToolBar.add(getBtnCancelBgdNum());
			jToolBar.add(getBtnNew());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btrquestToCustoms
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtrquestToCustoms() {
		if (btrquestToCustoms == null) {
			btrquestToCustoms = new JButton();
			btrquestToCustoms.setText("查询");
			btrquestToCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgRequestToFptToCustomsQueryCondition dg = new DgRequestToFptToCustomsQueryCondition();
							dg.setImport(true);
							dg.setVisible(true);
							isImport = dg.isImport();
							billNo = dg.getBillNo();
							scmCoc = dg.getScmCoc();
							bomNo = dg.getBomNo();
							beginDate = dg.getBeginDate();
							endDate = dg.getEndDate();
							isMark = true;
							List list = dg.getLsResult();
							if (list != null) {
								showEmptyData(list);
							}
						}
					});
		}
		return btrquestToCustoms;
	}

	/**
	 * This method initializes btFptToCustoms
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtFptToCustoms() {
		if (btFptToCustoms == null) {
			btFptToCustoms = new JButton();
			btFptToCustoms.setText("查询");
			btFptToCustoms
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgFptToCustomsQueryCondition dg = new DgFptToCustomsQueryCondition();
							dg.setImport(true);
							dg.setVisible(true);
							List list = dg.getLsResult();
							if (list != null) {
								showData(list);
							}
						}
					});
		}
		return btFptToCustoms;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setVisible(false);
			jButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			jButton1.setText("\u6253\u5370");
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getBtFptToCustoms());
			jToolBar1.add(getJButton11());
			jToolBar1.add(getJButton21());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setVisible(false);
			jButton11.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			jButton11.setText("\u6253\u5370");
		}
		return jButton11;
	}

	/**
	 * This method initializes jButton21
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton21() {
		if (jButton21 == null) {
			jButton21 = new JButton();
			jButton21.setText("关闭");
			jButton21.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton21;
	}

	/**
	 * This method initializes btnNew
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNew() {
		if (btnNew == null) {
			btnNew = new JButton();
			btnNew.setText("刷新");
			btnNew.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new MyFindThread().start();
				}
			});
		}
		return btnNew;
	}

	class MyFindThread extends Thread {
		public void run() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正获取数据，请稍后...");
			find();
			CommonProgress.closeProgressDialog();
		}
	}

	/**
	 * 开始查询
	 */
	private void find() {
		if (!isMark) {
			return;
		}
		List lsResult = fptManageAction.getMakeFptBillFromCasBill(new Request(
				CommonVars.getCurrUser()), isImport, scmCoc, beginDate,
				endDate, billNo, bomNo);
		if (lsResult != null) {
			showEmptyData(lsResult);
		}
	}

	/**
	 * This method initializes btnRecivBgdNum
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRecivBgdNum() {
		if (btnRecivBgdNum == null) {
			btnRecivBgdNum = new JButton();
			btnRecivBgdNum.setText("回写结转单据报关单号");
			btnRecivBgdNum
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (rquestToCustomsTableModel.getCurrentRow() == null) {
								return;
							}
							// 回写结转单据报关单号
							List<TempCasBillTOFptTOCustomsReport> listC = rquestToCustomsTableModel
									.getCurrentRows();
							new BillCorrespondingThread(listC).start();
						}
					});
		}
		return btnRecivBgdNum;
	}

	// 回写结转单据报关单号
	class BillCorrespondingThread extends Thread {

		List<TempCasBillTOFptTOCustomsReport> listC = null;

		public BillCorrespondingThread(
				List<TempCasBillTOFptTOCustomsReport> listC) {
			this.listC = listC;
		}

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {

				String info = "";
				long beginTime = System.currentTimeMillis();
				CommonProgress.setMessage(flag, "系统正在进行结转单据报关单回写，请稍后...");

				fptManageAction.reciveCustomsDeclarationCode(new Request(
						CommonVars.getCurrUser()), listC);

				CommonProgress.closeProgressDialog(flag);
				info += " 结转单据报关单回写任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(null, info, "提示", 2);
				find();
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(null, "结转单据报关单回写失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

	/**
	 * This method initializes btnCancelBgdNum
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancelBgdNum() {
		if (btnCancelBgdNum == null) {
			btnCancelBgdNum = new JButton();
			btnCancelBgdNum.setText("取消结转单据报关单号");
			btnCancelBgdNum
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (rquestToCustomsTableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(null,
										"请选择要取消的记录项!!", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							// 取消结转单据报关单号
							new CancelCorrespondingThread().start();
						}
					});
		}
		return btnCancelBgdNum;
	}

	// 取消结转单据报关单号
	class CancelCorrespondingThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				String info = "";
				long beginTime = System.currentTimeMillis();
				CommonProgress.setMessage(flag, "系统正在取消结转单据报关单号，请稍后...");

				List<TempCasBillTOFptTOCustomsReport> list = rquestToCustomsTableModel
						.getCurrentRows();
				fptManageAction.cancelCustomsDeclarationCode(new Request(
						CommonVars.getCurrUser()), list);
				CommonProgress.closeProgressDialog(flag);
				info += " 取消结转单据对应任务完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(null, info, "提示", 2);
				find();
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(null, "取消结转单据对应失败！"
						+ e.getMessage(), "提示", 2);
			}
		}
	}

} // @jve:decl-index=0:visual-constraint="22,12"
