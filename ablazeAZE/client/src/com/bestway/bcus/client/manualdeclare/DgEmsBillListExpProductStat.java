package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgEmsBillListExpProductStat extends JInternalFrameBase  {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnQuery = null;

	private JTableListModel tableModel = null;

	private ManualDeclareAction manualDeclareAction = null;

	private List lsResult = null;

	private JLabel jLabel2 = null;

	private JComboBox jComboBox = null;

	private String billListState = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgEmsBillListExpProductStat() {
		super();
		initialize();
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
	}

	// public void setVisible(boolean b) {
	// if (b) {
	// initTable();
	// }
	// super.setVisible(b);
	// }

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(750, 510));
		this.setTitle("出口成品清单情况统计表");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.cbbBeginDate.setDate(CommonVars.getBeginDate());
		this.cbbEndDate.setDate(new Date());
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty(null, "全部"));
		this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.DRAFT).toString(), "草稿"));
		this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.ALREADY_SEND).toString(), "已经申报"));
		this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.PASSED).toString(), "审批通过"));
		this.jComboBox.addItem(new ItemProperty(Integer.valueOf(
				ApplyToCustomsBillList.Effect).toString(), "生效"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
		this.jComboBox.setSelectedIndex(-1);

//		this.addWindowListener(new java.awt.event.WindowAdapter() {
//
//			public void windowOpened(java.awt.event.WindowEvent e) {
//				List list = new Vector();
//				initTable(list);
//				
//			}
//		});

	}
	@Override
	public void setVisible(boolean b) {
		if (b) {

			List list = new Vector();
			initTable(list);
		}
		super.setVisible(b);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(603, 4, 65, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgEmsBillListExpProductStat.this, "没有数据可以打印",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgEmsBillListExpProductStat.class
							.getResourceAsStream("report/EmsBillListExpProductStat.jasper");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("beginDate",
							cbbBeginDate.getDate() == null ? ""
									: (new SimpleDateFormat("yyyy-MM-dd"))
											.format(cbbBeginDate.getDate()));
					parameters.put("endDate", cbbEndDate.getDate() == null ? ""
							: (new SimpleDateFormat("yyyy-MM-dd"))
									.format(cbbEndDate.getDate()));
					// parameters.put("reportTitle",
					// isMaterial?"进口料件清单明细表":"出口成品清单明细表");
					JasperPrint jasperPrint;
					try {
						jasperPrint = JasperFillManager.fillReport(
								reportStream, parameters, ds);
						DgReportViewer viewer = new DgReportViewer(jasperPrint);
						viewer.setVisible(true);
					} catch (JRException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton1
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
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(337, 3, 59, 24));
			jLabel2.setText(" 清单状态");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(212, 5, 17, 24));
			jLabel1.setText(" 到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(5, 5, 105, 20));
			jLabel.setText(" 清单录入日期从 ");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJComboBox(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(115, 4, 94, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(233, 4, 91, 24));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(532, 4, 65, 24));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jComboBox.getSelectedItem() != null) {
						billListState = ((ItemProperty) jComboBox
								.getSelectedItem()).getCode();
					}

					new find().start();
				}
			});
		}
		return btnQuery;
	}

	class find extends Thread {

		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正获取数据，请稍后...");
				Date beginDate = CommonVars.dateToStandDate(cbbBeginDate
						.getDate());
				Date endDate = CommonVars.dateToStandDate(cbbEndDate.getDate());
				lsResult = manualDeclareAction.findBillListExpProductStat(
						new Request(CommonVars.getCurrUser()), beginDate,
						endDate, billListState);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsBillListExpProductStat.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				initTable(lsResult);
			}
		}
	}

	private String formatDouble(Double value) {
		DecimalFormat df = new DecimalFormat("#,##0.##");
		try {
			if (value != null) {
				return df.format(value.doubleValue());
			} else {
				return "0";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	private void castValue(JTable jTable, int colNum) {
		jTable.getColumnModel().getColumn(colNum).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "0"
								: formatDouble(Double
										.parseDouble((String) value)));
						return this;
					}
				});
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "commCode", 100));
						list.add(addColumn("品名", "commName", 100));
						list.add(addColumn("规格", "commSpec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("对应备案序号", "emsSerialNo", 100));
						// list.add(addColumn("单价", "unitPrice", 100));
						list.add(addColumn("总出口数量", "expTotalAmont", 100));
						list.add(addColumn("大单出口量", "bigExpAmount", 100));
						list.add(addColumn("直接出口数", "directExport", 100));
						list.add(addColumn("转厂出口数", "transferFactoryExport",
								100));
						list.add(addColumn("退厂返工数", "backFactoryRework", 100));
						list.add(addColumn("返工复出数", "reworkExport", 100));
						list.add(addColumn("客户", "scmcocName", 100));
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
		tableModel.setAllColumnsFractionCount(decimalLength);
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(403, 3, 120, 24));
		}
		return jComboBox;
	}

} // @jve:decl-index=0:visual-constraint="10,33"
