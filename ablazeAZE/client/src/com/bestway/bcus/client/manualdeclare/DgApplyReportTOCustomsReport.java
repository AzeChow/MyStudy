package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgApplyReportTOCustomsReport extends JInternalFrameBase {

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JLabel jLabel2 = null;
	private JCalendarComboBox ccbbBeginDate = null;
	private JLabel jLabel3 = null;
	private JCalendarComboBox ccbbEndDate = null;
	private JComboBox cbbImpExpFlag = null;
	private JLabel jLabel511 = null;
	private JCheckBox CbbListUnitNo = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable = null;
	private List list = null;  //  @jve:decl-index=0:
	private JTableListModel tableModel = null;
	private ManualDeclareAction manualDeclareAction = null;
	private EncAction encAction = null;
	private JLabel jLabel51 = null;
	private JComboBox cbbBillType = null;
	private JLabel jLabel = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgApplyReportTOCustomsReport() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(780, 501));
		this.setTitle("清单与报关单对应表");
		this.setContentPane(getJContentPane());
		initUIComponents();
		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		// public void windowOpened(java.awt.event.WindowEvent e) {
		// list = new Vector();
		// initTable(list);
		// }
		// });
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			list = new Vector();
			initTable(list);
		}
		super.setVisible(b);
	}

	private void initUIComponents() {

		// 初始进出类型
		// this.cbbImpExpFlag.removeAllItems();
		this.cbbImpExpFlag
				.addItem(new ItemProperty(MaterielType.MATERIEL, "进口"));
		this.cbbImpExpFlag.addItem(new ItemProperty(
				MaterielType.FINISHED_PRODUCT, "出口"));
		this.cbbImpExpFlag.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbImpExpFlag);
		this.cbbImpExpFlag.setSelectedIndex(-1);
		// 初始单据类型
		initCbbBillType(null);
	}

	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// 清单
						list
								.add(addColumn(
										"内部清单号",
										"atcMergeBeforeComInfo.afterComInfo.billList.listNo",
										100));
						list
								.add(addColumn(
										"统一编号",
										"atcMergeBeforeComInfo.afterComInfo.billList.listUniteNo",
										100));
						list
								.add(addColumn(
										"清单类型",
										"atcMergeBeforeComInfo.afterComInfo.billList.impExpType",
										80));
						list
								.add(addColumn(
										"帐册序号",
										"atcMergeBeforeComInfo.afterComInfo.emsSerialNo",
										50));
						
						list
								.add(addColumn(
										"商品编码",
										"atcMergeBeforeComInfo.afterComInfo.complex.code",
										80));
						list.add(addColumn("品名规格",
								"atcMergeBeforeComInfo.extendMemo", 150));
						
						list.add(addColumn("数量",
								"atcMergeBeforeComInfo.declaredAmount", 80));
						list.add(addColumn("申报单位",
								"atcMergeBeforeComInfo.afterComInfo.unit.name",
								50));
						list.add(addColumn("净重",
								"atcMergeBeforeComInfo.netWeight", 80));
						list.add(addColumn("毛重",
								"atcMergeBeforeComInfo.grossWeight", 80));
						list.add(addColumn("单价",
								"atcMergeBeforeComInfo.declaredPrice", 80));
						list.add(addColumn("金额",
								"atcMergeBeforeComInfo.totalPrice", 120));
						// 报关单
						list.add(addColumn("帐册号",
								"customsCommInfo.commSerialNo", 50));
						list
								.add(addColumn(
										"报关单号",
										"customsCommInfo.baseCustomsDeclaration.customsDeclarationCode",
										120));
						list
								.add(addColumn(
										"统一编号",
										"customsCommInfo.baseCustomsDeclaration.unificationCode",
										120));
						list
								.add(addColumn(
										"进出口类型",
										"customsCommInfo.baseCustomsDeclaration.impExpType",
										80));
						list.add(addColumn("数量", "customsCommInfo.commAmount",
								80));
						list.add(addColumn("单位", "customsCommInfo.unit.name",
								50));
						list.add(addColumn("单价",
								"customsCommInfo.commUnitPrice", 80));
						list.add(addColumn("金额",
								"customsCommInfo.commTotalPrice", 100));
						list.add(addColumn("原产国/抵运国",
								"customsCommInfo.country.name", 100));
						list
								.add(addColumn(
										"申报日期",
										"customsCommInfo.baseCustomsDeclaration.declarationDate",
										100));
						list
								.add(addColumn(
										"进出口日期",
										"customsCommInfo.baseCustomsDeclaration.impExpDate",
										100));
						list
								.add(addColumn(
										"是否已生效",
										"customsCommInfo.baseCustomsDeclaration.effective",
										80));
						list.add(addColumn("客户",
								"atcMergeBeforeComInfo.afterComInfo.billList.scmCoc.name", 150));
						return list;
					}
				});

		// 截取小数位
		String reportDecimalLength = manualDeclareAction.getBpara(new Request(
				CommonVars.getCurrUser(), true),
				BcusParameter.ReportDecimalLength);
		Integer decimalLength = 2;
		if (reportDecimalLength != null)
			decimalLength = Integer.valueOf(reportDecimalLength);

		List<JTableListColumn> cms = tableModel.getColumns();
		cms.get(7).setFractionCount(decimalLength);
		cms.get(9).setFractionCount(decimalLength);
		cms.get(10).setFractionCount(decimalLength);
		cms.get(11).setFractionCount(decimalLength);
		cms.get(12).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 12, 7, 11, decimalLength);

		cms.get(17).setFractionCount(decimalLength);
		cms.get(19).setFractionCount(decimalLength);
		cms.get(20).setFractionCount(decimalLength);
		CommonVars.castMultiplicationValue(jTable, 20, 17, 19, decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		jTable.getColumnModel().getColumn(3).setCellRenderer(
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
						switch (Integer.valueOf(String.valueOf(value))) {
						// 进口
						case ImpExpType.DIRECT_IMPORT:
							returnValue = "料件进口";
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							returnValue = "料件转厂";
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							returnValue = "退厂返工";
							break;
						case ImpExpType.GENERAL_TRADE_IMPORT:
							returnValue = "一般贸易进口";
							break;
						case ImpExpType.IMPORT_EXG_BACK:
							returnValue = "进料成品退换";
							break;
						case ImpExpType.IMPORT_REPAIR_MATERIAL:
							returnValue = "修理物品";
							break;
						// 出口
						case ImpExpType.DIRECT_EXPORT:
							returnValue = "成品出口";
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							returnValue = "转厂出口";
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							returnValue = "退料出口";
							break;
						case ImpExpType.REWORK_EXPORT:
							returnValue = "返工复出";
							break;
						case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
							returnValue = "边角料退港";
							break;
						case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
							returnValue = "边角料内销";
							break;
						case ImpExpType.GENERAL_TRADE_EXPORT:
							returnValue = "一般贸易出口";
							break;
						case ImpExpType.EXPORT_MATERIAL_REBACK:
							returnValue = "修理物品复出";
							break;
						case ImpExpType.EXPORT_EXG_REBACK:
							returnValue = "进料成品退换复出";
							break;
						default:
							returnValue = "没有对应的类型";
							break;
						}
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(16).setCellRenderer(
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
						switch (Integer.valueOf(String.valueOf(value))) {
						// 进口
						case ImpExpType.DIRECT_IMPORT:
							returnValue = "料件进口";
							break;
						case ImpExpType.TRANSFER_FACTORY_IMPORT:
							returnValue = "料件转厂";
							break;
						case ImpExpType.BACK_FACTORY_REWORK:
							returnValue = "退厂返工";
							break;
						case ImpExpType.REMAIN_FORWARD_IMPORT:
							returnValue = "余料结转进口";
							break;
						case ImpExpType.MATERIAL_DOMESTIC_SALES:
							returnValue = "海关批准内销";
							break;
						// 出口
						case ImpExpType.DIRECT_EXPORT:
							returnValue = "成品出口";
							break;
						case ImpExpType.TRANSFER_FACTORY_EXPORT:
							returnValue = "转厂出口";
							break;
						case ImpExpType.BACK_MATERIEL_EXPORT:
							returnValue = "退料出口";
							break;
						case ImpExpType.REWORK_EXPORT:
							returnValue = "返工复出";
							break;
						case ImpExpType.REMAIN_FORWARD_EXPORT:
							returnValue = "余料结转";
							break;
						default:
							returnValue = "没有对应的类型";
							break;
						}
						return returnValue;
					}
				});

		jTable.getColumnModel().getColumn(24).setCellRenderer(
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
						String returnValue = "否";
						if (value.toString().equals("true")) {
							returnValue = "是";
						} else
							returnValue = "否";
						return returnValue;
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
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(100);
			jSplitPane.setDividerSize(0);
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setTopComponent(getJPanel());
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
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(6, 14, 199, 43));
			jLabel.setText("清单与报关单对应表");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 20));
			jLabel.setForeground(Color.blue);
			jLabel51 = new JLabel();
			jLabel51.setBounds(new Rectangle(216, 37, 67, 18));
			jLabel51.setText("清单类型");
			jLabel511 = new JLabel();
			jLabel511.setBounds(new Rectangle(216, 4, 67, 23));
			jLabel511.setText("进出口类型");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(440, 69, 25, 23));
			jLabel3.setText("至");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(215, 65, 67, 23));
			jLabel2.setText("开始日期");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getCcbbBeginDate(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getCcbbEndDate(), null);
			jPanel.add(getCbbImpExpFlag(), null);
			jPanel.add(jLabel511, null);
			jPanel.add(getCbbListUnitNo(), null);
			jPanel.add(jLabel51, null);
			jPanel.add(getCbbBillType(), null);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(647, 18, 94, 23));
			jButton.setText("查询");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new find().start(); // TODO Auto-generated Event stub
					// actionPerformed()
				}
			});
		}
		return jButton;
	}

	class find extends Thread {

		public void run() {
			List lsResult = new Vector();
			try {

				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date beginDate = ccbbBeginDate.getDate();// 开始日期
				Date endDate = ccbbEndDate.getDate(); // 结束日期
				String impExpFlagCode = null;// 进出类型Code
				if (cbbImpExpFlag.getSelectedItem() != null) {
					impExpFlagCode = ((ItemProperty) cbbImpExpFlag
							.getSelectedItem()).getCode();
				}
				String billTypeCode = null;// 单据类型Code
				if (cbbBillType.getSelectedItem() != null) {
					billTypeCode = ((ItemProperty) cbbBillType
							.getSelectedItem()).getCode();
				}
				if (cbbImpExpFlag.getSelectedItem() != null) {
					impExpFlagCode = ((ItemProperty) cbbImpExpFlag
							.getSelectedItem()).getCode();
				}
				if (CbbListUnitNo.isSelected()) {
					lsResult = encAction.getApplyReportTOCustomsReport(
							new Request(CommonVars.getCurrUser()),
							impExpFlagCode, billTypeCode, beginDate, endDate,
							true);
				} else {
					lsResult = encAction.getApplyReportTOCustomsReport(
							new Request(CommonVars.getCurrUser()),
							impExpFlagCode, billTypeCode, beginDate, endDate,
							false);
				}
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgApplyReportTOCustomsReport.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(647, 57, 94, 23));
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();// TODO Auto-generated Event stub
					// actionPerformed()
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes ccbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbbBeginDate() {
		if (ccbbBeginDate == null) {
			ccbbBeginDate = new JCalendarComboBox();
			ccbbBeginDate.setBounds(new Rectangle(294, 70, 137, 23));
		}
		return ccbbBeginDate;
	}

	/**
	 * This method initializes ccbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCcbbEndDate() {
		if (ccbbEndDate == null) {
			ccbbEndDate = new JCalendarComboBox();
			ccbbEndDate.setBounds(new Rectangle(472, 68, 137, 23));
		}
		return ccbbEndDate;
	}

	/**
	 * This method initializes cbbImpExpFlag
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbImpExpFlag() {
		if (cbbImpExpFlag == null) {
			cbbImpExpFlag = new JComboBox();
			cbbImpExpFlag.setBounds(new Rectangle(295, 4, 137, 23));
			cbbImpExpFlag.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

					String impExpFlagCode = null;// 进出类型Code
					String billTypeCode = null;// 单据类型Code
					if (cbbImpExpFlag.getSelectedItem() != null)
						impExpFlagCode = ((ItemProperty) cbbImpExpFlag
								.getSelectedItem()).getCode();
					if (cbbBillType.getSelectedItem() != null)
						billTypeCode = ((ItemProperty) cbbBillType
								.getSelectedItem()).getCode();
					initCbbBillType(impExpFlagCode);

				}
			});
		}
		return cbbImpExpFlag;
	}

	private void initCbbBillType(String impExpFlagCode) {
		cbbBillType.removeAllItems();
		if (impExpFlagCode == null) {
			// 进口
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "进料成品退换"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_EXG_BACK), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_REPAIR_MATERIAL), "修理物品"));

			// 出口
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_MATERIAL_REBACK), "修理物品复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_EXG_REBACK), "进料成品退换复出"));
		} else if (impExpFlagCode.equals(MaterielType.MATERIEL)) {
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_IMPORT), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT), "料件转厂"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_FACTORY_REWORK), "退厂返工"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_IMPORT), "进料成品退换"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_EXG_BACK), "料件进口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.IMPORT_REPAIR_MATERIAL), "修理物品"));
		} else if (impExpFlagCode.equals(MaterielType.FINISHED_PRODUCT)) {
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.DIRECT_EXPORT), "成品出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT), "转厂出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.BACK_MATERIEL_EXPORT), "退料出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REWORK_EXPORT), "返工复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT), "边角料退港"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
					"边角料内销"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.GENERAL_TRADE_EXPORT), "一般贸易出口"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_MATERIAL_REBACK), "修理物品复出"));
			cbbBillType.addItem(new ItemProperty(String
					.valueOf(ImpExpType.EXPORT_EXG_REBACK), "进料成品退换复出"));
		}
		this.cbbBillType
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbBillType);
		this.cbbBillType.setSelectedIndex(-1);
	}

	/**
	 * This method initializes CbbListUnitNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbbListUnitNo() {
		if (CbbListUnitNo == null) {
			CbbListUnitNo = new JCheckBox();
			CbbListUnitNo.setBounds(new Rectangle(469, 9, 137, 21));
			CbbListUnitNo.setText("按统一编号");
			CbbListUnitNo.setForeground(Color.red);
		}
		return CbbListUnitNo;
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
	 * @return com.bestway.client.util.mutispan.MultiSpanCellTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();// MultiSpanCellTable();
		}
		return jTable;
	}

	/**
	 * This method initializes cbbBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillType() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(new Rectangle(295, 33, 137, 27));
		}
		return cbbBillType;
	}

} // @jve:decl-index=0:visual-constraint="10,18"
