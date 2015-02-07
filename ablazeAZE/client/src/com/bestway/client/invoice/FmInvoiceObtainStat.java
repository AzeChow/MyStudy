package com.bestway.client.invoice;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.invoice.action.InvoiceAction;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

public class FmInvoiceObtainStat extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane jScrollPane = null;

	private JTable tbInvoice = null;

	private JButton jButton = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel1 = null;

	private JButton btnQuery = null;

	private JButton btnReport = null;

	private JButton btnExit = null;

	private InvoiceAction invoiceAction = null;

	private JTableListModel tableModel = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmInvoiceObtainStat() {
		super();
		initialize();
		invoiceAction = (InvoiceAction) CommonVars.getApplicationContext()
				.getBean("invoiceAction");
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(687, 406));
		this.setTitle("出口商品发票领用存统计表");
		this.setContentPane(getJContentPane());
		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						GregorianCalendar thisday = new GregorianCalendar();
						int year = thisday.get(GregorianCalendar.YEAR);
						int month = thisday.get(GregorianCalendar.MONTH);
						GregorianCalendar firstDay = new GregorianCalendar(
								year, month, 1);
						GregorianCalendar lastDay = new GregorianCalendar(year,
								month, 1);
						lastDay.add(GregorianCalendar.MONTH, 1);
						lastDay.add(GregorianCalendar.DATE, -1);
						cbbBeginDate.setDate(firstDay.getTime());
						cbbEndDate.setDate(lastDay.getTime());
						List list = new ArrayList();// invoiceAction.findInvoiceForObtainStat(
						// new
						// Request(CommonVars.getCurrUser()),beginDate,endDate);
						initTable(list);
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
			jToolBar.add(getJButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbInvoice());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbInvoice() {
		if (tbInvoice == null) {
			tbInvoice = new GroupableHeaderTable();
		}
		return tbInvoice;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText(" ");
			jButton.setVisible(true);
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new java.awt.Rectangle(174,5,59,21));
			jLabel = new JLabel();
			jLabel.setText("起始日期");
			jLabel.setBounds(new java.awt.Rectangle(10,5,65,21));
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnQuery(), null);
			jPanel.add(getBtnReport(), null);
			jPanel.add(getBtnExit(), null);
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
			cbbBeginDate.setBounds(new java.awt.Rectangle(78,5,85,21));
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
			cbbEndDate.setBounds(new java.awt.Rectangle(236,5,84,21));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnQuery() {
		if (btnQuery == null) {
			btnQuery = new JButton();
			btnQuery.setText("查询");
			btnQuery.setBounds(new java.awt.Rectangle(376,3,60,23));
			btnQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Date beginDate = cbbBeginDate.getDate();
					if (beginDate == null) {
						JOptionPane.showMessageDialog(FmInvoiceObtainStat.this,
								"请选择起始日期", "提示", JOptionPane.OK_OPTION);
						return;
					}
					Date endDate = cbbEndDate.getDate();
					if (endDate == null) {
						JOptionPane.showMessageDialog(FmInvoiceObtainStat.this,
								"请选择结束日期", "提示", JOptionPane.OK_OPTION);
						return;
					}
					List list = invoiceAction.findInvoiceForObtainStat(
							new Request(CommonVars.getCurrUser()), beginDate,
							endDate);
					initTable(list);
				}
			});
		}
		return btnQuery;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReport() {
		if (btnReport == null) {
			btnReport = new JButton();
			btnReport.setText("打印");
			btnReport.setBounds(new java.awt.Rectangle(445,3,61,23));
			btnReport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null) {
						return;
					}
					List list = new ArrayList();
					list.addAll(tableModel.getList());
					if (list.size() > 0) {
						list.remove(list.size() - 1);
					}
					CustomReportDataSource ds = new CustomReportDataSource(list);
					InputStream reportStream = FmInvoiceObtainStat.class
							.getResourceAsStream("report/InvoiceObtainStatReport.jasper");
					Map parameters = new HashMap();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					parameters.put("beginDate", dateFormat.format(cbbBeginDate
							.getDate()));
					parameters.put("endDate", dateFormat.format(cbbEndDate
							.getDate()));
					parameters
							.put("currentDate", dateFormat.format(new Date()));
					JasperPrint jasperPrint = null;
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
		return btnReport;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setBounds(new java.awt.Rectangle(556,3,60,23));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmInvoiceObtainStat.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	private void initTable(List list) {
		tableModel = new JTableListModel(this.getTbInvoice(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<Object> list = (List<Object>) (new Vector());
						list.add(addColumn("品名", "commName", 80));
						list.add(addColumn("份数", "originalPieces", 40));
						list.add(addColumn("起始发票号", "originalBeginCode", 160));
						list.add(addColumn("终至发票号", "originalEndCode", 160));
						list.add(addColumn("份数", "currentDraftPieces", 40));
						list.add(addColumn("起始发票号", "currentDraftBeginCode",
								160));
						list
								.add(addColumn("终至发票号", "currentDraftEndCode",
										160));
						list.add(addColumn("份数", "currentUsedPieces", 40));
						list
								.add(addColumn("起始发票号", "currentUsedBeginCode",
										160));
						list.add(addColumn("终至发票号", "currentUsedEndCode", 160));
						list.add(addColumn("份数", "currentCanceledPieces", 40));
						list.add(addColumn("起始发票号", "currentCanceledBeginCode",
								160));
						list.add(addColumn("终至发票号", "currentCanceledEndCode",
								160));
						list.add(addColumn("交回未开票数量", "", 60));
						list.add(addColumn("份数", "finalPieces", 40));
						list.add(addColumn("起始发票号", "finalBeginCode", 160));
						list.add(addColumn("终至发票号", "finalEndCode", 160));
						return list;
					}
				});
		TableColumnModel cm = tbInvoice.getColumnModel();
		ColumnGroup gOriginal = new ColumnGroup("期初库存");
		gOriginal.add(cm.getColumn(2));
		gOriginal.add(cm.getColumn(3));
		gOriginal.add(cm.getColumn(4));
		ColumnGroup gDraft = new ColumnGroup("本期领购");
		gDraft.add(cm.getColumn(5));
		gDraft.add(cm.getColumn(6));
		gDraft.add(cm.getColumn(7));
		ColumnGroup gUsed = new ColumnGroup("本期开具");
		gUsed.add(cm.getColumn(8));
		gUsed.add(cm.getColumn(9));
		gUsed.add(cm.getColumn(10));
		ColumnGroup gCanceled = new ColumnGroup("本期作废或遗失");
		gCanceled.add(cm.getColumn(11));
		gCanceled.add(cm.getColumn(12));
		gCanceled.add(cm.getColumn(13));
		ColumnGroup gFinal = new ColumnGroup("期末库存");
		gFinal.add(cm.getColumn(15));
		gFinal.add(cm.getColumn(16));
		gFinal.add(cm.getColumn(17));
		GroupableTableHeader header = (GroupableTableHeader) tbInvoice
				.getTableHeader();
		header.addColumnGroup(gOriginal);
		header.addColumnGroup(gDraft);
		header.addColumnGroup(gUsed);
		header.addColumnGroup(gCanceled);
		header.addColumnGroup(gFinal);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
