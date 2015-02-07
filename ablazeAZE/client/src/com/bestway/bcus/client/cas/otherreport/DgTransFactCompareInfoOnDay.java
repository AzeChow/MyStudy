package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.ProgressInfo;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

@SuppressWarnings("unchecked")
public class DgTransFactCompareInfoOnDay extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JLabel jLabel = null;

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel11 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel2 = null;

	private JTextField tfCommName = null;

	private JButton jButton = null;

	private JButton btnQuery = null;

	private JButton btnPrint = null;

	private JButton btnClose = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbTransImport = null;

	private JRadioButton rbTransExport = null;

	private JTableListModel tableModel = null;

	private MaterialManageAction materialManageAction = null;

	private CasAction casAction = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="816,82"
	private Integer otherReportMaximumFractionDigits = CasCommonVars
			.getOtherOption().getOtherReportMaximumFractionDigits() == null ? 2
			: CasCommonVars.getOtherOption()
					.getOtherReportMaximumFractionDigits(); // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgTransFactCompareInfoOnDay() {
		super(false);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(782, 489));
		this.setTitle("转厂差额明细表");
		this.setContentPane(getJContentPane());
		this.getButtonGroup();

	}

	private void initUIComponents() {
		List lsScmCoc = new ArrayList();
		if (this.rbTransImport.isSelected()) {
			lsScmCoc = this.getManufacturer();
			this.jLabel.setText("供应商名称");
		} else {
			lsScmCoc = this.getCustomer();
			this.jLabel.setText("客户名称");

		}
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbScmCoc,
				"code", "name", 250);
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
		beginClarendar.set(Calendar.YEAR, year);
		beginClarendar.set(Calendar.MONTH, 0);
		beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
		this.cbbBeginDate.setDate(beginClarendar.getTime());
		this.cbbBeginDate.setCalendar(beginClarendar);

		Calendar endClarendar = Calendar.getInstance();
		endClarendar.set(Calendar.YEAR, year);
		endClarendar.set(Calendar.MONTH, 11);
		endClarendar.set(Calendar.DAY_OF_MONTH, 31);
		this.cbbEndDate.setDate(endClarendar.getTime());
		this.cbbEndDate.setCalendar(endClarendar);
		//
		// List dataSource = this.casAction
		// .findLeftoverMaterielBalanceInfo(new Request(CommonVars
		// .getCurrUser(), true));
		initTable(new ArrayList());
	}

	/**
	 * 获得是客户的对象列表
	 */
	private List getCustomer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List customerList = new ArrayList();

		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if ((scmCoc.getIsTransferFactoryOut() != null && scmCoc
					.getIsTransferFactoryOut().booleanValue())
					|| (scmCoc.getIsCustomer() != null && scmCoc
							.getIsCustomer().booleanValue())) {
				customerList.add(scmCoc);
			}
		}
		return customerList;
	}

	/**
	 * 获得是供应商的对象列表
	 */
	private List getManufacturer() {
		List list = this.materialManageAction.findScmCocs(new Request(
				CommonVars.getCurrUser(), true));
		List manufacturerList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ScmCoc scmCoc = (ScmCoc) list.get(i);
			if ((scmCoc.getIsTransferFactoryIn() != null && scmCoc
					.getIsTransferFactoryIn().booleanValue())
					|| (scmCoc.getIsManufacturer() != null && scmCoc
							.getIsManufacturer().booleanValue())) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
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
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(60);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
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
			jLabel2.setBounds(new Rectangle(18, 31, 63, 25));
			jLabel2.setText("商品名称");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(302, 32, 55, 23));
			jLabel11.setText("结束日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(302, 4, 55, 23));
			jLabel1.setText("开始日期");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(18, 4, 64, 26));
			jLabel.setText("供应商");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCommName(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getJPanel1(), null);
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
			jTable = new GroupableHeaderTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(81, 4, 200, 26));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(358, 4, 108, 23));
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
			cbbEndDate.setBounds(new Rectangle(358, 32, 108, 23));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(new Rectangle(82, 31, 180, 26));
		}
		return tfCommName;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(261, 32, 20, 23));
			jButton.setText("...");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
					Date beginDate = cbbBeginDate.getDate();
					Date endDate = cbbEndDate.getDate();
					boolean isImg = rbTransImport.isSelected();
					Object object = CasQuery.getInstance()
							.findTransFactRecvSendCommName(isImg, scmCoc,
									beginDate, endDate);
					if (object != null) {
						tfCommName.setText(((TempObject) (object)).getObject1()
								.toString());
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setBounds(new Rectangle(522, 32, 64, 22));
			btnQuery.setText("查询");
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SearchThread().start();
				}
			});
		}
		return btnQuery;
	}

	class SearchThread extends Thread {
		public void run() {
			// if (cbbScmCoc.getSelectedItem() == null) {
			// JOptionPane.showMessageDialog(DgTransFactCompareInfoOnDay.this,
			// "请选择客户或供应商", "提示", JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			// if (tfCommName.getText().trim().equals("")) {
			// JOptionPane.showMessageDialog(DgTransFactCompareInfoOnDay.this,
			// "请输入商品名称", "提示", JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			if (cbbBeginDate.getDate() == null) {
				JOptionPane.showMessageDialog(DgTransFactCompareInfoOnDay.this,
						"请选择开始日期", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (cbbEndDate.getDate() == null) {
				JOptionPane.showMessageDialog(DgTransFactCompareInfoOnDay.this,
						"请选择结束日期", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			btnQuery.setEnabled(false);
			try {
				long beginTime = System.currentTimeMillis();
				ProgressTask progressTask = new ProgressTask() {
					protected void setClientTipMessage() {
						ToolsAction toolsAction = (ToolsAction) CommonVars
								.getApplicationContext().getBean("toolsAction");
						ProgressInfo progressInfo = toolsAction
								.getProgressInfo(flag);
						if (progressInfo != null
								&& progressInfo.getHintMessage() != null) {
							CommonProgress.setMessage(flag, progressInfo
									.getHintMessage());
						}
					}
				};
				CommonProgress.showProgressDialog(flag,
						DgTransFactCompareInfoOnDay.this, false, progressTask,
						5000);
				CommonProgress.setMessage(flag, "系统正获取数据，请稍后...");
				ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				String commName = null;
				if (!"".equals(tfCommName.getText().trim())) {
					commName = tfCommName.getText().trim();
				}
				boolean isImg = rbTransImport.isSelected();
				List list = casAction.findTransFactCompareInfoOnDay(
						new Request(CommonVars.getCurrUser()), isImg, commName,
						scmCoc, beginDate, endDate);
				CommonProgress.closeProgressDialog(flag);
				initTable(list);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgTransFactCompareInfoOnDay.this,
						"获取数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog(flag);
			}
			btnQuery.setEnabled(true);
			if (cbbScmCoc.getSelectedItem() == null) {
				btnPrint.setEnabled(false);
			} else {
				btnPrint.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setBounds(new Rectangle(590, 32, 63, 22));
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null || tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(
								DgTransFactCompareInfoOnDay.this, "没有数据可打印",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List list = tableModel.getList();
					CustomReportDataSource ds = new CustomReportDataSource(list);
					InputStream reportStream = DgTransFactCompareInfoOnDay.class
							.getResourceAsStream("report/TransFactCompareInfoOnDayReport.jasper");
					// Unit unit = casAction.findUnitByHsName(new Request(
					// CommonVars.getCurrUser()), tfCommName.getText()
					// .trim());
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("companyName", CommonVars.getCurrUser()
							.getCompany().getName());
					parameters.put("scmCocName",
							cbbScmCoc.getSelectedItem() == null ? ""
									: ((ScmCoc) cbbScmCoc.getSelectedItem())
											.getName());
					parameters.put("scmCoc",
							(rbTransImport.isSelected() ? "供应商" : "客户"));
					// parameters.put("commName", tfCommName.getText().trim());
					// parameters.put("commUnit", unit == null ? "" : unit
					// .getName());
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
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setBounds(new Rectangle(658, 32, 60, 22));
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(521, 2, 196, 26));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel1.add(getRbTransImport(), null);
			jPanel1.add(getRbTransExport(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransImport() {
		if (rbTransImport == null) {
			rbTransImport = new JRadioButton();
			rbTransImport.setText("转进");
			rbTransImport.setSelected(true);
			rbTransImport.setBounds(new Rectangle(51, 3, 49, 20));
			rbTransImport.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initUIComponents();
					}
				}
			});
		}
		return rbTransImport;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransExport() {
		if (rbTransExport == null) {
			rbTransExport = new JRadioButton();
			rbTransExport.setText("转出");
			rbTransExport.setBounds(new Rectangle(105, 3, 49, 20));
			rbTransExport.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
						initUIComponents();
					}
				}
			});
		}
		return rbTransExport;
	}

	private void initTable(final List list) {
		if (tableModel != null) {
			tableModel.setList(list);
			return;
		}
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("商品名称", "commName", 100));
				list.add(addColumn("规格", "ptSpec", 150));
				list.add(new JTableListColumn("日期", "date", 100));
				list.add(addColumn("客户/供应商名称", "scmCocName", 100));

				list.add(new JTableListColumn("数量", "sendAmount", 180,
						Double.class));
				list.add(new JTableListColumn("重量", "sendWeight", 100,
						Double.class));
				list.add(new JTableListColumn("数量", "transAmount", 120,
						Double.class));
				list.add(new JTableListColumn("重量", "transWeight", 100,
						Double.class));
				list.add(new JTableListColumn("数量", "remainAmount", 180,
						Double.class));
				list.add(new JTableListColumn("重量", "remainWeight", 180,
						Double.class));
				list.add(addColumn("关封号", "envelopNo", 100));
				list.add(new JTableListColumn("数量", "envelopAmount", 100,
						Double.class));
				list.add(new JTableListColumn("重量", "envelopWeight", 100,
						Double.class));
				list.add(addColumn("有效期", "term", 100));
				list.add(addColumn("是否结案", "isEndCase", 100));
				return list;
			}
		};

		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		cm.getColumn(13).setCellRenderer(new TableCheckBoxRender());
		ColumnGroup gSend = new ColumnGroup(rbTransImport.isSelected() ? "收货"
				: "送货");
		gSend.add(cm.getColumn(5));
		gSend.add(cm.getColumn(6));
		ColumnGroup gTrans = new ColumnGroup("转厂");
		gTrans.add(cm.getColumn(7));
		gTrans.add(cm.getColumn(8));
		ColumnGroup gRemain = new ColumnGroup("结余");
		gRemain.add(cm.getColumn(9));
		gRemain.add(cm.getColumn(10));
		ColumnGroup gEnvelopRemain = new ColumnGroup("关封余量");
		gEnvelopRemain.add(cm.getColumn(12));
		gEnvelopRemain.add(cm.getColumn(13));
		ColumnGroup gEnvelop = new ColumnGroup("关封");
		gEnvelop.add(cm.getColumn(11));
		gEnvelop.add(gEnvelopRemain);
		gEnvelop.add(cm.getColumn(14));
		gEnvelop.add(cm.getColumn(15));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gSend);
		header.addColumnGroup(gTrans);
		header.addColumnGroup(gRemain);
		header.addColumnGroup(gEnvelop);
		// jTable.getColumnModel().getColumn(2).setCellRenderer(
		// new tableCellRender());
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new tableCellRender1());
		jTable.getColumnModel().getColumn(13).setCellRenderer(
				new tableCellRender1());
		List<JTableListColumn> xs = tableModel.getColumns();
		xs.get(5).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(6).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(7).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(8).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(9).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(10).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(12).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(13).setFractionCount(otherReportMaximumFractionDigits);
	}

	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (value != null) {
				Date tmp = new Date();
				try {
					tmp = sdf.parse((String) value);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setText(sdf.format(tmp));
			}
			return this;
		}
	}

	class tableCellRender1 extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value != null) {
				double tmp = Double.parseDouble((String) value);
				this.setText(Double.toString(tmp));
			}
			return this;
		}
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getRbTransImport());
			buttonGroup.add(this.getRbTransExport());
		}
		return buttonGroup;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
