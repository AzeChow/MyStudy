package com.bestway.wjsz.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrExg;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrHead;
import com.bestway.bcus.manualdeclare.entity.EmsEdiTrImg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DzscEmsType;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgWj;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.waijing.action.WjszAction;

public class FmSBaseDataHead extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JScrollPane jScrollPane = null;
	private JTable tbImg = null;
	private JScrollPane jScrollPane1 = null;
	private JTable tbExg = null;
	private JPanel jPanel4 = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JRadioButton jRadioButton2 = null;
	private JButton btnUpload = null;
	private JButton btnExit = null;
	private ButtonGroup group = new ButtonGroup();
	private JTableListModel tableModel = null;// 表头
	private JTableListModel tableModelImg = null;// 料件
	private JTableListModel tableModelExg = null;// 成品
	private WjszAction wjszAction = null;
	private int selectInt = 0;
	private JButton jButton3 = null;
	private JButton btnSelectAll = null;
	private JButton btnNotSelect = null;
	private JLabel jLabel = null;
	private JPanel jPanel5 = null;
	private JScrollPane jScrollPane2 = null;
	private JTable tbHead = null;
	private JButton btnDownload = null;
	private JButton btnRefresh = null;

	/**
	 * This is the default constructor
	 */
	public FmSBaseDataHead() {
		super();
		wjszAction = (WjszAction) CommonVars.getApplicationContext().getBean(
				"wjszAction");
		initialize();
		WjszClientUtil.login();
		com.bestway.jptds.client.common.CommonVars.getApplicationContext();
		selectInt = (Integer) wjszAction.getModule(new Request(CommonVars
				.getCurrUser()));
		if (selectInt == 0) {
			selectInt = 1;
		}
		moduSelect(selectInt);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(846, 427);
		this.setContentPane(getJContentPane());
		this.setTitle("凭证数据交换");
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameClosed(
							javax.swing.event.InternalFrameEvent e) {
						wjszAction.saveModuleSelect(new Request(CommonVars
								.getCurrUser()), selectInt);
					}
				});
		group.add(jRadioButton);
		group.add(jRadioButton1);
		group.add(jRadioButton2);
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(120);
		}
		return jSplitPane;
	}

	/**
	 * 电子手册合同备案表头
	 */
	public JTableListModel initTableBcusHead(JTable jTable) {
		List list = (List) wjszAction.findBcusWjHead(new Request(CommonVars
				.getCurrUser()));
		if (list == null) {
			return initTableBcusWj(new Vector());
		} else {
			return initTableBcusWj(list);
		}
	}

	/**
	 * 电子手册合同备案表头
	 */
	public JTableListModel initTableDzscHead() {
		List list = (List) wjszAction.findDzscWjHead(new Request(CommonVars
				.getCurrUser()));
		if (list != null && list.size() > 0) {
			return initTableWj(list);
		} else {
			return initTableWj(new Vector());
		}
	}

	/**
	 * 电子帐册经营范围表头
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTableBcusWj(final List list) {
		tableModel = new JTableListModel(tbHead, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("帐册编号", "emsNo", 100));
						list.add(addColumn("申报类型", "declareType", 80));
						list.add(addColumn("审批状态", "declareState", 80));
						list.add(addColumn("修改标志", "modifyMark", 60));
						list.add(addColumn("变更次数", "modifyTimes", 60,
								Integer.class));
						list.add(addColumn("经营单位代码", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 120));
						list.add(addColumn("加工单位代码", "ownerCode", 100));
						list.add(addColumn("加工单位名称", "ownerName", 120));
						list.add(addColumn("申报日期", "declareDate", 80));
						list.add(addColumn("批准日期", "newApprDate", 80));
						list.add(addColumn("年加工能力", "productRatio", 80));
						list.add(addColumn("外经贸部门", "declareDep.name", 100));
						list.add(addColumn("主管海关", "masterCustoms.code", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("帐册类型", "emsType", 80));
						return list;
					}
				});
		tbHead.getColumnModel().getColumn(3).setCellRenderer(
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {
							returnValue = "申请备案";
							// FmEmsEdiTr.this.setChange(false);
						} else if (value.equals("2")) {
							returnValue = "申请变更";
							// FmEmsEdiTr.this.setChange(true);
						}
						return returnValue;
					}
				});
		tbHead.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue(table,
								row, value));
						return this;
					}

					private String castValue(JTable table, int row, Object value) {
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("1")) {

							String declareType = ((EmsEdiTrHead) ((JTableListModel) table
									.getModel()).getDataByRow(row))
									.getDeclareType();
							if (declareType.equals("2"))
								returnValue = "申请变更";
							else
								returnValue = "申请备案";
						} else if (value.equals("2"))
							returnValue = "等待审批";
						else if (value.equals("3"))
							returnValue = "正在执行";
						return returnValue;
					}
				});
		tbHead.getColumnModel().getColumn(5).setCellRenderer(
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
						String returnValue = "";
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(ModifyMarkState.ADDED))
							returnValue = "新增";
						else if (value.equals(ModifyMarkState.DELETED))
							returnValue = "已删除";
						else if (value.equals(ModifyMarkState.MODIFIED))
							returnValue = "已修改";
						else if (value.equals(ModifyMarkState.UNCHANGE))
							returnValue = "未修改";
						return returnValue;
					}
				});
		return tableModel;
	}

	/**
	 * 电子手册合同备案表头
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTableWj(final List list) {
		tableModel = new JTableListModel(tbHead, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 50));
						list.add(addColumn("合同号", "ieContractNo", 100));
						list.add(addColumn("手册编号", "emsNo", 100));
						list.add(addColumn("合同状态", "declareState", 100));
						list.add(addColumn("手册类型", "emsType", 100));
						list.add(addColumn("起始日期", "beginDate", 100));
						list.add(addColumn("结束日期", "endDate", 100));
						list.add(addColumn("核销期限", "destroyDate", 100));
						list.add(addColumn("备注", "note", 100));
						list.add(addColumn("延期期限", "deferDate", 100));
						list.add(addColumn("进出口岸", "iePort1.name", 100));
						list.add(addColumn("经营单位", "tradeName", 100));
						list.add(addColumn("贸易方式", "trade.name", 100));
						list.add(addColumn("收货单位", "machName", 100));
						list.add(addColumn("贸易国别", "tradeCountry.name", 100));
						list.add(addColumn("批文号", "sancEmsNo", 100));
						list.add(addColumn("协议书号", "agreementNo", 100));
						list.add(addColumn("出口合同号", "imContractNo", 100));
						list.add(addColumn("进口总值", "imgAmount", 100));
						list.add(addColumn("出口总值", "exgAmount", 100));
						list.add(addColumn("币制", "curr.name", 100));
						list.add(addColumn("监管费率", "wardshipRate", 100));
						list.add(addColumn("监管费", "wardshipFee", 100));
						list.add(addColumn("进出口岸二", "iePort2.name", 100));
						list.add(addColumn("进出口岸三", "iePort3.name", 100));
						list.add(addColumn("进出口岸四", "iePort4.name", 100));
						list.add(addColumn("进出口岸五", "iePort5.name", 100));
						list.add(addColumn("企业地址", "enterpriseAddress", 100));
						list.add(addColumn("联系人", "linkMan", 100));
						list.add(addColumn("联系电话", "contactTel", 100));

						return list;
					}
				});
		tbHead.getColumnModel().getColumn(4).setCellRenderer(
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscState.ORIGINAL)) {
							returnValue = "原始状态";
						} else if (value.equals(DzscState.APPLY)) {
							returnValue = "等待审批";
						} else if (value.equals(DzscState.EXECUTE)) {
							returnValue = "正在执行";
						} else if (value.equals(DzscState.CHANGE)) {
							returnValue = "正在变更";
						} else if (value.equals(DzscState.BACK_BILL)) {
							returnValue = "退单状态";
						}
						return returnValue;
					}
				});
		tbHead.getColumnModel().getColumn(5).setCellRenderer(
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(DzscEmsType.COME_PROCESS_CONTRACT)) {
							returnValue = "来料加工合同手册";
						} else if (value
								.equals(DzscEmsType.IMPORT_PROCESS_CONTRACT)) {
							returnValue = "进料加工合同手册";
						}
						return returnValue;
					}
				});
		return tableModel;
	}

	/**
	 * 电子化手册合同备案表头
	 */
	public JTableListModel initTableBcsHead() {
		List list = (List) wjszAction.findBcsDictPorHead(new Request(CommonVars
				.getCurrUser()));
		if (list != null && list.size() > 0) {
			return initBcstable(list);
		} else {
			return initBcstable(new Vector());
		}
	}

	/**
	 * 电子化手册备案资料库表头
	 * 
	 */
	private JTableListModel initBcstable(final List list) {
		tableModel = new JTableListModel(tbHead, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("流水号", "seqNum", 100));
						list.add(addColumn("报送海关", "declareCustoms.name", 100));
						list.add(addColumn("备案资料库编号", "dictPorEmsNo", 100));
						list.add(addColumn("备案状态", "declareState", 60));
						list.add(addColumn("企业内部编号", "copEmsNo", 100));
						list.add(addColumn("经营单位编号", "tradeCode", 100));
						list.add(addColumn("经营单位名称", "tradeName", 100));
						list.add(addColumn("加工单位编号", "machCode", 100));
						list.add(addColumn("加工单位名称", "machName", 100));
						list.add(addColumn("年加工生产能力", "productRatio", 100));
						list.add(addColumn("申报日期", "declareDate", 100));
						list.add(addColumn("修改标志", "modifyMark", 100));
						list.add(addColumn("备注", "memo", 100));
						return list;
					}
				});

		tbHead.getColumnModel().getColumn(4).setCellRenderer(
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
						return DeclareState.getDeclareStateSpec(value
								.toString());
					}
				});
		tbHead.getColumnModel().getColumn(12).setCellRenderer(
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
						return ModifyMarkState.getModifyMarkSpec(value
								.toString());
					}
				});
		tbHead.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return tableModel;
	}

	private void moduSelect(int i) {
		wjszAction.saveModuleSelect(new Request(CommonVars.getCurrUser()),
				selectInt);
		jTabbedPane.setSelectedIndex(0);
		if (i == 0) {// 电子帐册/经营范围
			// this.jLabel.setText("提示：只来自经营范围【申请备案】或【申请变更】状态下！");
			jRadioButton.setSelected(true);
			tableModel = initTableBcusHead(tbHead);
			if (tableModel != null) {
				EmsEdiTrHead head = (EmsEdiTrHead) tableModel.getCurrentRow();
				tableModelImg = SClientLogic.initTableImg(tbImg, head);
				tableModelExg = SClientLogic.initTableExg(tbExg, head);
			}
		} else if (i == 1) {// 电子手册(确定使用)
			// this.jLabel.setText("提示：只来自合同备案【原始状态】或【正在变更】状态下！");
			jRadioButton1.setSelected(true);
			tableModel = initTableDzscHead();
			if (tableModel != null) {
				List ls = tableModel.getList();
				if (ls != null && ls.size() > 0) {
					DzscEmsPorWjHead porHead = (DzscEmsPorWjHead) ls.get(0);
					tableModelImg = SClientLogic.initTableImgWj(tbImg, porHead);
					tableModelExg = SClientLogic.initTableExgWj(tbExg, porHead);
				} else {
					tableModelImg = SClientLogic.initTableImgWj(tbImg,
							new Vector());
					tableModelExg = SClientLogic.initTableImgWj(tbExg,
							new Vector());
				}

			}

		} else if (i == 2) {// 电子化手册(确定使用)
			// this.jLabel.setText("提示：只来自备案资料库【原始状态】或【正在变更】状态下！");
			jRadioButton2.setSelected(true);
			tableModel = initTableBcsHead();
			if (tableModel != null) {
				List ls = tableModel.getList();
				if (ls != null && ls.size() > 0) {
					BcsDictPorHead porHead = (BcsDictPorHead) ls.get(0);
					tableModelImg = SClientLogic.initTableImgDictPor(tbImg,
							porHead);
					tableModelExg = SClientLogic.initTableExgDictPor(tbExg,
							porHead);
				} else {
					tableModelImg = SClientLogic.initTableImgDictPor(tbImg,
							new Vector());
					tableModelExg = SClientLogic.initTableExgDictPor(tbExg,
							new Vector());
				}

			}
		}
	}

	/**
	 * 刷新料件和成品的数据
	 */
	private void refreshImgExgData() {
		if (selectInt == 0) {// 电子帐册/经营范围
		} else if (selectInt == 1) {// 电子手册(确定使用)
			if (tableModel != null) {
				DzscEmsPorWjHead porHead = (DzscEmsPorWjHead) tableModel
						.getCurrentRow();
				if (porHead != null) {
					if (this.jTabbedPane.getSelectedIndex() == 1) {
						tableModelImg = SClientLogic.initTableImgWj(tbImg,
								porHead);
					} else if (this.jTabbedPane.getSelectedIndex() == 2) {
						tableModelExg = SClientLogic.initTableExgWj(tbExg,
								porHead);
					}
				} else {
					if (this.jTabbedPane.getSelectedIndex() == 1) {
						tableModelImg = SClientLogic.initTableImgWj(tbImg,
								new Vector());
					} else if (this.jTabbedPane.getSelectedIndex() == 2) {
						tableModelExg = SClientLogic.initTableImgWj(tbExg,
								new Vector());
					}
				}

			}

		} else if (selectInt == 2) {// 电子化手册(确定使用)
			if (tableModel != null) {
				BcsDictPorHead porHead = (BcsDictPorHead) tableModel
						.getCurrentRow();
				if (porHead != null) {
					if (this.jTabbedPane.getSelectedIndex() == 1) {
						tableModelImg = SClientLogic.initTableImgDictPor(tbImg,
								porHead);
					} else if (this.jTabbedPane.getSelectedIndex() == 2) {
						tableModelExg = SClientLogic.initTableExgDictPor(tbExg,
								porHead);
					}
				} else {
					if (this.jTabbedPane.getSelectedIndex() == 1) {
						tableModelImg = SClientLogic.initTableImgDictPor(tbImg,
								new Vector());
					} else if (this.jTabbedPane.getSelectedIndex() == 2) {
						tableModelExg = SClientLogic.initTableExgDictPor(tbExg,
								new Vector());
					}
				}

			}
		}
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(11, 49, 670, 29));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getBtnUpload(), null);
			jPanel.add(getBtnExit(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getBtnSelectAll(), null);
			jPanel.add(getBtnNotSelect(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getBtnDownload(), null);
			jPanel.add(getBtnRefresh(), null);
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
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("表头", null, getJPanel5(), null);
			jTabbedPane.addTab("料件", null, getJPanel2(), null);
			jTabbedPane.addTab("成品", null, getJPanel3(), null);

			// jTabbedPane.addChangeListener(new
			// javax.swing.event.ChangeListener() {
			// public void stateChanged(javax.swing.event.ChangeEvent e) {
			// refreshImgExgData();
			// }
			// });
		}
		return jTabbedPane;
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
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbImg());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbImg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbImg() {
		if (tbImg == null) {
			tbImg = new JTable();
			tbImg.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						jButton3.doClick();
					}
				}
			});
		}
		return tbImg;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbExg() {
		if (tbExg == null) {
			tbExg = new JTable();
			tbExg.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						jButton3.doClick();
					}
				}
			});
		}
		return tbExg;
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
			jPanel4.setBounds(new java.awt.Rectangle(10, 0, 673, 45));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "模式选择",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					java.awt.Color.blue));
			jPanel4.add(getJRadioButton(), null);
			jPanel4.add(getJRadioButton1(), null);
			jPanel4.add(getJRadioButton2(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setSelected(true);
			jRadioButton.setBounds(new Rectangle(503, 15, 152, 21));
			jRadioButton.setText("电子帐册(经营范围)");
			jRadioButton.setVisible(false);
			jRadioButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectInt = 0;
					moduSelect(selectInt);
				}
			});
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setBounds(new Rectangle(19, 16, 148, 21));
			jRadioButton1.setText("电子手册(合同备案)");
			jRadioButton1
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectInt = 1;
							moduSelect(selectInt);
						}
					});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jRadioButton2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton2() {
		if (jRadioButton2 == null) {
			jRadioButton2 = new JRadioButton();
			jRadioButton2.setBounds(new Rectangle(173, 16, 164, 21));
			jRadioButton2.setText("电子化手册(备案资料库)");
			jRadioButton2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectInt = 2;
							moduSelect(selectInt);
						}
					});
		}
		return jRadioButton2;
	}

	/**
	 * This method initializes btnUpload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpload() {
		if (btnUpload == null) {
			btnUpload = new JButton();
			btnUpload.setBounds(new java.awt.Rectangle(303, 85, 73, 25));
			btnUpload.setText("上传");
			btnUpload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					List selectLsImg = getCommodityListImg();
					List selectLsExg = getCommodityListExg();
					System.out.println("------------selectLsImg size:"
							+ selectLsImg.size());
					System.out.println("------------selectLsExg size:"
							+ selectLsExg.size());
					if ((selectLsImg != null && selectLsImg.size() > 0)
							|| (selectLsExg != null && selectLsExg.size() > 0)) {
						if (JOptionPane.showConfirmDialog(FmSBaseDataHead.this,
								"您确定要将选择的数据上传到外经系统吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						if (checkSelectDataWjCodeIsNull(selectInt, selectLsImg,
								selectLsExg)) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"对不起，您选择的资料外经编码有为空！", "提示", 2);
							return;
						}

						boolean isConver = false;
						if (JOptionPane.showConfirmDialog(FmSBaseDataHead.this,
								"是否覆盖已存在且未审核的资料?", "提示",
								JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
							isConver = true;
						}

						String message = SUploadLogic.uploadCredenceData(
								isConver, selectInt, selectLsImg, selectLsExg);
						JOptionPane.showMessageDialog(FmSBaseDataHead.this,
								message, "提示", 2);
					} else {
						JOptionPane.showMessageDialog(FmSBaseDataHead.this,
								"请选择要上传的料件和成品资料", "提示", 2);
						return;
					}
				}
			});
		}
		return btnUpload;
	}

	private boolean checkSelectDataWjCodeIsNull(int SelectInt,
			List selectLsImg, List selectLsExg) {
		if (SelectInt == 1) {// 电子手册
			for (int i = 0; i < selectLsImg.size(); i++) {
				DzscEmsImgWj img = (DzscEmsImgWj) selectLsImg.get(i);
				if (img.getWjCode() == null
						|| img.getWjCode().trim().equals("")) {
					return true;
				}
			}
			for (int i = 0; i < selectLsExg.size(); i++) {
				DzscEmsExgWj exg = (DzscEmsExgWj) selectLsExg.get(i);
				if (exg.getWjCode() == null
						|| exg.getWjCode().trim().equals("")) {
					return true;
				}
			}
		} else if (SelectInt == 2) {// 电子化手册
			for (int i = 0; i < selectLsImg.size(); i++) {
				BcsDictPorImg img = (BcsDictPorImg) selectLsImg.get(i);
				if (img.getWjCode() == null
						|| img.getWjCode().trim().equals("")) {
					return true;
				}
			}
			for (int i = 0; i < selectLsExg.size(); i++) {
				BcsDictPorExg exg = (BcsDictPorExg) selectLsExg.get(i);
				if (exg.getWjCode() == null
						|| exg.getWjCode().trim().equals("")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(new Rectangle(608, 85, 73, 25));
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(379, 85, 141, 25));
			jButton3.setText("修改外经凭证序号");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (selectInt == 1) {// 电子手册
						if (jTabbedPane.getSelectedIndex() == 1) {// 料件
							DgSBaseDataWjCode dg = new DgSBaseDataWjCode();
							dg.setLj(true);
							dg.setTableModel(tableModelImg);
							dg.setSelectInt(selectInt);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						} else if (jTabbedPane.getSelectedIndex() == 2) {
							DgSBaseDataWjCode dg = new DgSBaseDataWjCode();
							dg.setLj(false);
							dg.setTableModel(tableModelExg);
							dg.setSelectInt(selectInt);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					} else if (selectInt == 2) { // 电子化手册
						if (jTabbedPane.getSelectedIndex() == 1) {// 料件
							DgSBaseDataWjCode dg = new DgSBaseDataWjCode();
							dg.setLj(true);
							dg.setTableModel(tableModelImg);
							dg.setSelectInt(selectInt);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						} else if (jTabbedPane.getSelectedIndex() == 2) {
							DgSBaseDataWjCode dg = new DgSBaseDataWjCode();
							dg.setLj(false);
							dg.setTableModel(tableModelExg);
							dg.setSelectInt(selectInt);
							dg.setDataState(DataState.EDIT);
							dg.setVisible(true);
						}
					}
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setBounds(new java.awt.Rectangle(9, 85, 90, 25));
			btnSelectAll.setText("全部选中");
			btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectData(true);
				}
			});
		}
		return btnSelectAll;
	}

	private void selectData(boolean isSelect) {
		if (selectInt == 0) {// 电子帐册
			if (jTabbedPane.getSelectedIndex() == 1) {// 料件
				List ls = tableModelImg.getList();
				for (int i = 0; i < ls.size(); i++) {
					EmsEdiTrImg obj = (EmsEdiTrImg) ls.get(i);
					obj.setIsSelected(isSelect);
					tableModelImg.updateRow(obj);
				}
			} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
				List ls = tableModelExg.getList();
				for (int i = 0; i < ls.size(); i++) {
					EmsEdiTrExg obj = (EmsEdiTrExg) ls.get(i);
					obj.setIsSelected(isSelect);
					tableModelExg.updateRow(obj);
				}
			}
		} else if (selectInt == 1) {// 电子手册
			if (jTabbedPane.getSelectedIndex() == 1) {// 料件
				List ls = tableModelImg.getList();
				for (int i = 0; i < ls.size(); i++) {
					DzscEmsImgWj obj = (DzscEmsImgWj) ls.get(i);
					obj.setIsSelected(isSelect);
					tableModelImg.updateRow(obj);
				}
			} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
				List ls = tableModelExg.getList();
				for (int i = 0; i < ls.size(); i++) {
					DzscEmsExgWj obj = (DzscEmsExgWj) ls.get(i);
					obj.setIsSelected(isSelect);
					tableModelExg.updateRow(obj);
				}
			}
		} else if (selectInt == 2) {// 电子化手册
			if (jTabbedPane.getSelectedIndex() == 1) {// 料件
				List ls = tableModelImg.getList();
				for (int i = 0; i < ls.size(); i++) {
					BcsDictPorImg obj = (BcsDictPorImg) ls.get(i);
					obj.setIsSelected(isSelect);
					tableModelImg.updateRow(obj);
				}
			} else if (jTabbedPane.getSelectedIndex() == 2) {// 成品
				List ls = tableModelExg.getList();
				for (int i = 0; i < ls.size(); i++) {
					BcsDictPorExg obj = (BcsDictPorExg) ls.get(i);
					obj.setIsSelected(isSelect);
					tableModelExg.updateRow(obj);
				}
			}
		}
	}

	/**
	 * This method initializes btnNotSelect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotSelect() {
		if (btnNotSelect == null) {
			btnNotSelect = new JButton();
			btnNotSelect.setBounds(new java.awt.Rectangle(113, 85, 90, 25));
			btnNotSelect.setText("全部不选");
			btnNotSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectData(false);
				}
			});
		}
		return btnNotSelect;
	}

	public List getCommodityListImg() {
		if (selectInt == 0) {
			List list = tableModelImg.getList();
			List<EmsEdiTrImg> newList = new ArrayList<EmsEdiTrImg>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof EmsEdiTrImg) {
					EmsEdiTrImg temp = (EmsEdiTrImg) list.get(i);
					if (temp.getIsSelected() != null
							&& temp.getIsSelected().booleanValue() == true) {
						newList.add(temp);
					}
				}
			}
			return newList;
		} else if (selectInt == 1) {
			List list = tableModelImg.getList();
			List<DzscEmsImgWj> newList = new ArrayList<DzscEmsImgWj>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof DzscEmsImgWj) {
					DzscEmsImgWj temp = (DzscEmsImgWj) list.get(i);
					if (temp.getIsSelected() != null
							&& temp.getIsSelected().booleanValue() == true) {
						newList.add(temp);
					}
				}
			}
			return newList;
		} else if (selectInt == 2) {
			List list = tableModelImg.getList();
			List<BcsDictPorImg> newList = new ArrayList<BcsDictPorImg>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof BcsDictPorImg) {
					BcsDictPorImg temp = (BcsDictPorImg) list.get(i);
					if (temp.getIsSelected() != null
							&& temp.getIsSelected().booleanValue() == true) {
						newList.add(temp);
					}
				}
			}
			return newList;
		}
		return new Vector();
	}

	public List getCommodityListExg() {
		if (selectInt == 0) {
			List list = tableModelExg.getList();
			List<EmsEdiTrExg> newList = new ArrayList<EmsEdiTrExg>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof EmsEdiTrExg) {
					EmsEdiTrExg temp = (EmsEdiTrExg) list.get(i);
					if (temp.getIsSelected() != null
							&& temp.getIsSelected().booleanValue() == true) {
						newList.add(temp);
					}
				}
			}
			return newList;
		} else if (selectInt == 1) {
			List list = tableModelExg.getList();
			List<DzscEmsExgWj> newList = new ArrayList<DzscEmsExgWj>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof DzscEmsExgWj) {
					DzscEmsExgWj temp = (DzscEmsExgWj) list.get(i);
					if (temp.getIsSelected() != null
							&& temp.getIsSelected().booleanValue() == true) {
						newList.add(temp);
					}
				}
			}
			return newList;
		} else if (selectInt == 2) {
			List list = tableModelExg.getList();
			List<BcsDictPorExg> newList = new ArrayList<BcsDictPorExg>();
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof BcsDictPorExg) {
					BcsDictPorExg temp = (BcsDictPorExg) list.get(i);
					if (temp.getIsSelected() != null
							&& temp.getIsSelected().booleanValue() == true) {
						newList.add(temp);
					}
				}
			}
			return newList;
		}
		return new Vector();
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTbHead());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes tbHead
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbHead() {
		if (tbHead == null) {
			tbHead = new JTable();
			tbHead.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 1) {
						if (selectInt == 0) {// 电子帐册/经营范围
							// this.jLabel.setText("提示：只来自经营范围【申请备案】或【申请变更】状态下！");
							if (tableModel != null
									&& tableModel.getCurrentRow() != null) {
								EmsEdiTrHead head = (EmsEdiTrHead) tableModel
										.getCurrentRow();
								tableModelImg = SClientLogic.initTableImg(
										tbImg, head);
								tableModelExg = SClientLogic.initTableExg(
										tbExg, head);
							}
						} else if (selectInt == 1) {// 电子手册(确定使用)
							if (tableModel != null
									&& tableModel.getCurrentRow() != null) {
								DzscEmsPorWjHead porHead = (DzscEmsPorWjHead) tableModel
										.getCurrentRow();
								tableModelImg = SClientLogic.initTableImgWj(
										tbImg, porHead);
								tableModelExg = SClientLogic.initTableExgWj(
										tbExg, porHead);
							}
						} else if (selectInt == 2) {// 电子化手册(确定使用)
							if (tableModel != null
									&& tableModel.getCurrentRow() != null) {
								BcsDictPorHead porHead = (BcsDictPorHead) tableModel
										.getCurrentRow();
								tableModelImg = SClientLogic
										.initTableImgDictPor(tbImg, porHead);
								tableModelExg = SClientLogic
										.initTableExgDictPor(tbExg, porHead);
							}
						}
					}
				}
			});
		}
		return tbHead;
	}

	/**
	 * This method initializes btnDownload
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDownload() {
		if (btnDownload == null) {
			btnDownload = new JButton();
			btnDownload.setBounds(new Rectangle(228, 85, 73, 24));
			btnDownload.setText("下载");
			btnDownload.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (selectInt == 1) {// 电子手册
						DzscEmsPorWjHead dzscEmsPorWjHead = (DzscEmsPorWjHead) tableModel
								.getCurrentRow();
						if (dzscEmsPorWjHead == null) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"请选择要下载的资料", "提示", 2);
							return;
						}
						if (DzscState.EXECUTE.equals(dzscEmsPorWjHead
								.getDeclareState())) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"合同" + dzscEmsPorWjHead.getCopTrNo()
											+ "正在执行，所以不能导入。");
							return;
						}
						if (DzscState.APPLY.equals(dzscEmsPorWjHead
								.getDeclareState())) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"合同" + dzscEmsPorWjHead.getCopTrNo()
											+ "正在申报，所以不能导入。");
							return;
						}
						if (JOptionPane.showConfirmDialog(FmSBaseDataHead.this,
								"您确定要将从外经系统下载资料到本合同备案吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						DgSCredenceSelect dg = new DgSCredenceSelect();
						dg.setVisible(true);
						if (dg.isOK()) {
							List listImgCredence = dg.getSelectImgCredence();
							List listExgCredence = dg.getSelectExgCredence();
							boolean isConver = false;
							if (JOptionPane.showConfirmDialog(
									FmSBaseDataHead.this, "是否覆盖已存在且未审核的资料?",
									"提示", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
								isConver = true;
							}
							String message = SDownloadLogic
									.downloadDzscEmsPorWjData(isConver,
											dzscEmsPorWjHead, listImgCredence,
											listExgCredence);
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									message);
							refreshImgExgData();
						}
					} else if (selectInt == 2) {// 电子化手册
						BcsDictPorHead bcsDictPorHead = (BcsDictPorHead) tableModel
								.getCurrentRow();
						if (bcsDictPorHead == null) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"请选择要下载的资料", "提示", 2);
							return;
						}
						if (DeclareState.PROCESS_EXE.equals(bcsDictPorHead
								.getDeclareState())) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"备案资料库" + bcsDictPorHead.getCopEmsNo()
											+ "正在执行，所以不能导入。");
							return;
						}
						if (DeclareState.WAIT_EAA.equals(bcsDictPorHead
								.getDeclareState())) {
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									"备案资料库" + bcsDictPorHead.getCopEmsNo()
											+ "正在申报，所以不能导入。");
							return;
						}
						if (JOptionPane.showConfirmDialog(FmSBaseDataHead.this,
								"您确定要将从外经系统下载资料到本备案资料库吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						DgSCredenceSelect dg = new DgSCredenceSelect();
						dg.setVisible(true);
						if (dg.isOK()) {
							List listImgCredence = dg.getSelectImgCredence();
							List listExgCredence = dg.getSelectExgCredence();
							boolean isConver = false;
							if (JOptionPane.showConfirmDialog(
									FmSBaseDataHead.this, "是否覆盖已存在且未审核的资料?",
									"提示", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
								isConver = true;
							}
							String message = SDownloadLogic
									.downloadBcsDictData(isConver,
											bcsDictPorHead, listImgCredence,
											listExgCredence);
							JOptionPane.showMessageDialog(FmSBaseDataHead.this,
									message);
							refreshImgExgData();
						}

					}
				}
			});
		}
		return btnDownload;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setBounds(new Rectangle(523, 85, 83, 25));
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// refreshImgExgData();
					if (jRadioButton1.isSelected()) {
						selectInt = 1;
					} else if (jRadioButton2.isSelected()) {
						selectInt = 2;
					}
					moduSelect(selectInt);
				}
			});
		}
		return btnRefresh;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
