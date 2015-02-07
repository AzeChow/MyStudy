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
import com.bestway.bcus.client.common.CommonQueryPage;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.BcusParameter;
import com.bestway.bcus.manualdeclare.entity.EmsBillListImpMaterialStat;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class DgEmsBillListImpMaterialStat extends JInternalFrameBase {

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

	private JButton jButton2 = null;
	private List billlist = new Vector(); // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgEmsBillListImpMaterialStat() {
		super();
		initialize();
		this.cbbBeginDate.setDate(null);
		this.cbbEndDate.setDate(null);
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");

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
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(750, 510));
		this.setTitle("进口料件清单情况统计表");
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

		// this.addWindowListener(new java.awt.event.WindowAdapter() {
		//
		// public void windowOpened(java.awt.event.WindowEvent e) {
		//
		// List list = new Vector();
		// initTable(list);
		// }
		// });

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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
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
			btnPrint.setBounds(new Rectangle(614, 3, 60, 24));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgEmsBillListImpMaterialStat.this, "没有数据可以打印",
								"提示", JOptionPane.OK_OPTION);
						return;
					}
					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					InputStream reportStream = DgEmsBillListImpMaterialStat.class
							.getResourceAsStream("report/EmsBillListImpMaterialStat.jasper");
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
			jLabel2.setBounds(new Rectangle(313, 3, 57, 24));
			jLabel2.setText(" 清单状态");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(204, 3, 17, 24));
			jLabel1.setText(" 到");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(5, 5, 101, 20));
			jLabel.setText(" 清单录入日期从 ");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getJButton2(), null);
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
			cbbBeginDate.setBounds(new Rectangle(108, 4, 94, 24));
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
			cbbEndDate.setBounds(new Rectangle(221, 4, 91, 24));
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
			btnQuery.setBounds(new Rectangle(546, 3, 65, 24));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jComboBox.getSelectedItem() != null) {
						billListState = ((ItemProperty) jComboBox
								.getSelectedItem()).getCode();
					}
					if (billlist.size() == 0) {
						JOptionPane.showMessageDialog(
								DgEmsBillListImpMaterialStat.this, "请先选择料件",
								"提示", JOptionPane.OK_OPTION);
						return;
					} else {
						new find().start();
					}
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
				lsResult = manualDeclareAction.findBillListImpMaterialStat(
						new Request(CommonVars.getCurrUser()), beginDate,
						endDate, billListState, billlist);
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgEmsBillListImpMaterialStat.this, "获取数据失败：！"
								+ e.getMessage(), "提示", 2);
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
						list.add(addColumn("总进口数量", "impTotalAmont", 100));
						list.add(addColumn("大单进口量", "bigImpAmount", 100));
						list.add(addColumn("直接进口数", "directImport", 100));
						list.add(addColumn("转厂进口数", "transferFactoryImport",
								100));
						list.add(addColumn("退料出口数", "backMaterialExport", 100));
						list.add(addColumn("成品使用量", "productUse", 100));
						list.add(addColumn("余料情况", "remainAmount", 100));
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
			jComboBox.setBounds(new Rectangle(371, 3, 82, 24));
		}
		return jComboBox;
	}

	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(455, 3, 89, 24));
			jButton2.setText("选择料件");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = (List) CommonQueryPage.getInstance()
							.getEmsEdiImgBombeforeAll();
					if (list == null) {
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						Object[] objs = (Object[]) list.get(i);
						EmsBillListImpMaterialStat impMaterialStat = new EmsBillListImpMaterialStat();
						impMaterialStat.setCommCode(objs[0].toString());
						impMaterialStat.setCommName(objs[1].toString());
						impMaterialStat.setCommSpec(objs[2]==null ? "":objs[2].toString());
						billlist.add(impMaterialStat);
					}
					initTable(billlist);
				}
			});
		}
		return jButton2;
	}

}
