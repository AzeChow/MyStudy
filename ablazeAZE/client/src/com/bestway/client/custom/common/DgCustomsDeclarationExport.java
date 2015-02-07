package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JFrame;

import com.bestway.bcus.cas.entity.ImportExportInfo;
import com.bestway.bcus.client.cas.DgImpExpQuery;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpFlag;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableCellRenderer;

public class DgCustomsDeclarationExport extends JDialogBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JLabel jLabel = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox jCalendarComboBox = null;
	private JLabel jLabel2 = null;
	private JCalendarComboBox jCalendarComboBox1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private BaseEncAction baseEncAction = null;
	private int impExpFlag;
	private JCheckBox jCheckBox = null;
	private JTableListModel tableModel = null;

	/**
	 * This is the default constructor
	 */
	public DgCustomsDeclarationExport() {
		super();
		initialize();
		initTable(new Vector());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(780, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("入出境信息");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {

				if (impExpFlag == ImpExpFlag.IMPORT) {
					jComboBox.setSelectedItem("进口报关单");
				} else if (impExpFlag == ImpExpFlag.EXPORT) {
					jComboBox.setSelectedItem("出口报关单");
				} else {
					jComboBox.setSelectedItem("");
				}
			}
		});
		
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
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(50);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(360, 11, 23, 23));
			jLabel2.setText("至");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(208, 11, 61, 20));
			jLabel1.setText("申报日期");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(17, 11, 73, 20));
			jLabel.setText("报关单类型");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJCalendarComboBox1(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJCheckBox(), null);
		}
		return jPanel;
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
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(90, 11, 112, 24));
			jComboBox.addItem("");
			jComboBox.addItem("进口报关单");
			jComboBox.addItem("出口报关单");
			jComboBox.addItem("特殊报关单");
			jComboBox.setSelectedIndex(0);
		}
		return jComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox
					.setBounds(new java.awt.Rectangle(269, 11, 89, 24));
		}
		return jCalendarComboBox;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getJCalendarComboBox1() {
		if (jCalendarComboBox1 == null) {
			jCalendarComboBox1 = new JCalendarComboBox();
			jCalendarComboBox1
					.setBounds(new java.awt.Rectangle(382, 11, 89, 24));
		}
		return jCalendarComboBox1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(565, 14, 79, 25));
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new searchThread().start();
				}
			});
		}
		return jButton;
	}

	class searchThread extends Thread {

		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			jButton.setEnabled(false);
			List ls = null;
			try {
				CommonProgress.showProgressDialog(flag,
						DgCustomsDeclarationExport.this);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				int impExpFlag = -1;
				if (jComboBox.getSelectedItem().equals("进口报关单")) {
					impExpFlag = ImpExpFlag.IMPORT;
				} else if (jComboBox.getSelectedItem().equals("出口报关单")) {
					impExpFlag = ImpExpFlag.EXPORT;
				} else if (jComboBox.getSelectedItem().equals("特殊报关单")) {
					impExpFlag = ImpExpFlag.SPECIAL;
				}
				boolean isFtoJ = false;
				if (jCheckBox.isSelected()) {
					isFtoJ = true;
				}

				Date begindate = CommonVars.dateToStandDate(jCalendarComboBox
						.getDate());
				Date enddate = CommonVars.dateToStandDate(jCalendarComboBox1
						.getDate());
				ls = baseEncAction.customsDeclarationExport(new Request(
						CommonVars.getCurrUser()), impExpFlag, begindate,
						enddate, isFtoJ);

				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgCustomsDeclarationExport.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				if (ls != null && !ls.isEmpty()) {
					initTable(ls);
				} else {
					initTable(new Vector());
					JOptionPane.showMessageDialog(
							DgCustomsDeclarationExport.this, "没有查到符合条件的记录！",
							"提示！", 2);
				}
			}
			jButton.setEnabled(true);
		}
	}

	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("报关单类型",
								"baseCustomsDeclaration.impExpFlag", 100));
						list.add(addColumn("电子帐册序号", "", 100));
						list.add(addColumn("大单流水号",
								"baseCustomsDeclaration.serialNumber", 100,
								Integer.class));
						list.add(addColumn("进出口类型",
								"baseCustomsDeclaration.impExpType", 100));
						list.add(addColumn("是否生效",
								"baseCustomsDeclaration.effective", 80));
						list.add(addColumn("进出口岸",
								"baseCustomsDeclaration.customs.name", 80));
						list.add(addColumn("海关手册号",
								"baseCustomsDeclaration.emsHeadH2k", 90));
						list.add(addColumn("进出口日期",
								"baseCustomsDeclaration.impExpDate", 100));
						list.add(addColumn("申报日期",
								"baseCustomsDeclaration.declarationDate", 100));
						list.add(addColumn("经营单位",
								"baseCustomsDeclaration.tradeName", 120));
						list
								.add(addColumn(
										"运输方式",
										"baseCustomsDeclaration.transferMode.name",
										80));
						list.add(addColumn("运输工具",
								"baseCustomsDeclaration.conveyance", 80));
						list.add(addColumn("提运单号",
								"baseCustomsDeclaration.billOfLading", 80));
						list.add(addColumn("收货单位",
								"baseCustomsDeclaration.machName", 120));
						list.add(addColumn("贸易方式",
								"baseCustomsDeclaration.tradeMode.name", 80));
						list.add(addColumn("征免性质",
								"baseCustomsDeclaration.levyKind.name", 80));
						list.add(addColumn("征税比例/结汇方式",
								"baseCustomsDeclaration.perTax", 120));
						list.add(addColumn("许可证号",
								"baseCustomsDeclaration.license", 100));
						list
								.add(addColumn(
										"起运国",
										"baseCustomsDeclaration.countryOfLoadingOrUnloading.name",
										80));
						list
								.add(addColumn(
										"装货港/指运港",
										"baseCustomsDeclaration.portOfLoadingOrUnloading.name",
										100));
						list
								.add(addColumn(
										"境内目的地/境内货源地",
										"baseCustomsDeclaration.domesticDestinationOrSource.name",
										120));
						list.add(addColumn("批准文号",
								"baseCustomsDeclaration.authorizeFile", 100));
						list.add(addColumn("成交方式",
								"baseCustomsDeclaration.transac.name", 80));
						list.add(addColumn("运费率",
								"baseCustomsDeclaration.freightage", 80));
						list.add(addColumn("保费率",
								"baseCustomsDeclaration.insurance", 80));
						list
								.add(addColumn(
										"杂费率",
										"baseCustomsDeclaration.incidentalExpenses",
										80));
						list.add(addColumn("合同号", "", 80));
						list.add(addColumn("件数",
								"baseCustomsDeclaration.commodityNum", 80));
						list.add(addColumn("包装种类",
								"baseCustomsDeclaration.wrapType.name", 80));
						list.add(addColumn("毛重",
								"baseCustomsDeclaration.grossWeight", 80));
						list.add(addColumn("净重",
								"baseCustomsDeclaration.netWeight", 80));
						list.add(addColumn("集装箱号",
								"baseCustomsDeclaration.containerNum", 100));
						list.add(addColumn("监管代码", "", 80));
						list.add(addColumn("用途/生产厂家",
								"baseCustomsDeclaration.uses.name", 100));
						list.add(addColumn("备注",
								"baseCustomsDeclaration.memos", 120));
						list
								.add(addColumn(
										"预录入编号",
										"baseCustomsDeclaration.preCustomsDeclarationCode",
										100));
						list
								.add(addColumn(
										"报关单号",
										"baseCustomsDeclaration.customsDeclarationCode",
										120));
						list
								.add(addColumn(
										"币制",
										"baseCustomsDeclaration.currency.currSymb",
										80));
						list
								.add(addColumn(
										"申报单位名称",
										"baseCustomsDeclaration.declarationCompany.name",
										120));
						list
								.add(addColumn(
										"录入员",
										"baseCustomsDeclaration.creater.userName",
										100));
						list.add(addColumn("大单来源", "", 80));
						list.add(addColumn("所附单据内容", "", 100));
						list
								.add(addColumn(
										"发货单位",
										"baseCustomsDeclaration.acceptGoodsCompany.name",
										80));
						list.add(addColumn("内销比例", "", 120));
						list
								.add(addColumn(
										"报送海关",
										"baseCustomsDeclaration.declarationCustoms.name",
										80));
						list.add(addColumn("运费标记",
								"baseCustomsDeclaration.freightageType", 80));
						list.add(addColumn("保费标记",
								"baseCustomsDeclaration.insuranceType", 80));
						list
								.add(addColumn(
										"杂费标记",
										"baseCustomsDeclaration.incidentalExpensesType",
										80));
						list.add(addColumn("所有集装箱号码",
								"baseCustomsDeclaration.allContainerNum", 120));
						list.add(addColumn("装卸码头",
								"baseCustomsDeclaration.predock.name", 80));
						list.add(addColumn("条形码", "", 80));
						list.add(addColumn("报关员",
								"baseCustomsDeclaration.customser", 80));
						list.add(addColumn("报关员电话",
								"baseCustomsDeclaration.telephone", 100));
						list.add(addColumn("联系人", "", 80));
						list.add(addColumn("联系电话", "", 80));
						list.add(addColumn("打印人", "", 80));
						list.add(addColumn("是否通过逻辑监控", "", 120));
						list
								.add(addColumn(
										"境外运输工具编码",
										"baseCustomsDeclaration.overseasConveyanceCode",
										120));
						list
								.add(addColumn(
										"境外运输工具名称",
										"baseCustomsDeclaration.overseasConveyanceName",
										120));
						list
								.add(addColumn(
										"境外运输工具航次",
										"baseCustomsDeclaration.overseasConveyanceFlights",
										120));
						list
								.add(addColumn(
										"境外运输工具提单号",
										"baseCustomsDeclaration.overseasConveyanceBillOfLading",
										120));
						list
								.add(addColumn(
										"境内运输工具编号",
										"baseCustomsDeclaration.domesticConveyanceCode",
										120));
						list
								.add(addColumn(
										"境内运输工具名称",
										"baseCustomsDeclaration.domesticConveyanceName",
										120));
						list
								.add(addColumn(
										"境内运输工具航次",
										"baseCustomsDeclaration.domesticConveyanceFlights",
										120));
						list
								.add(addColumn(
										"境内运输方式",
										"baseCustomsDeclaration.domesticTransferMode.name",
										120));
						list.add(addColumn("IC卡号", "", 80));
						list.add(addColumn("转关是否确认", "", 120));
						list.add(addColumn("单据资料/海关帐", "", 120));
						list.add(addColumn("备用", "", 80));
						list.add(addColumn("承运单位编号", "", 120));
						list.add(addColumn("承运单位名称", "", 120));
						list.add(addColumn("空箱数", "", 80));
						list.add(addColumn("预计运抵指运地日期", "", 120));
						list.add(addColumn("报关单币制与合同币制汇率",
								"baseCustomsDeclaration.exchangeRate", 120));
						list.add(addColumn("统一编号",
								"baseCustomsDeclaration.unificationCode", 120));
						list
								.add(addColumn(
										"报关单号",
										"baseCustomsDeclaration.customsDeclarationCode",
										120));
						list.add(addColumn("海关手册号",
								"baseCustomsDeclaration.emsHeadH2k", 120));
						list.add(addColumn("进口大单流水号",
								"baseCustomsDeclaration.serialNumber", 100,
								Integer.class));
						list.add(addColumn("项号", "serialNumber", 80,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "commName", 120));
						list.add(addColumn("型号规格", "commSpec", 120));
						list.add(addColumn("数量", "commAmount", 80));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("法定数量", "firstAmount", 100));
						list.add(addColumn("法定单位", "legalUnit.name", 100));
						list.add(addColumn("千克量", "", 80));
						list.add(addColumn("原产国", "country.name", 80));
						list.add(addColumn("单价", "commUnitPrice", 80));
						list.add(addColumn("总价", "commTotalPrice", 80));
						list.add(addColumn("是否为料件", "", 80));
						list.add(addColumn("合同对应序号", "commSerialNo", 100,
								Integer.class));
						list.add(addColumn("第二法定单位数量", "secondAmount", 120));
						list.add(addColumn("第二法定单位", "secondLegalUnit.name",
								120));
						list.add(addColumn("征减免税方式", "levyMode.name", 120));
						list.add(addColumn("用途", "uses.name", 80));
						list.add(addColumn("货号", "", 80));
						list.add(addColumn("版本", "version", 80));
						list.add(addColumn("编号", "", 80));
						list.add(addColumn("待用", "", 80));
						list.add(addColumn("单价(录入)", "", 80));
						list.add(addColumn("总价(录入)", "", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("毛重", "grossWeight", 80));
						list.add(addColumn("集装箱数", "pieces", 100));
						list.add(addColumn("备注", "", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							if(String.valueOf(ImpExpFlag.IMPORT).equals(value)){
								str="进口报关单";
							}else if(String.valueOf(ImpExpFlag.EXPORT).equals(value)){
								str="出口报关单";
							}if(String.valueOf(ImpExpFlag.SPECIAL).equals(value)){
								str="特殊报关单";
							}
						}
						this.setText(str);
						return this;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = ImpExpType.getImpExpTypeDesc(Integer
									.parseInt(value.toString()));
						}
						this.setText(str);
						return this;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : ((Boolean
								.valueOf(value.toString())) ? "Y" : "N"));
						return this;
					}
				});

		jTable
				.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(657, 14, 79, 25));
			jButton1.setText("退出");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationExport.this.dispose();
				}
			});
		}
		return jButton1;
	}

	public BaseEncAction getBaseEncAction() {
		return baseEncAction;
	}

	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}

	public void setImpExpFlag(int impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(482, 14, 64, 21));
			jCheckBox.setText("繁转简");
		}
		return jCheckBox;
	}

}
