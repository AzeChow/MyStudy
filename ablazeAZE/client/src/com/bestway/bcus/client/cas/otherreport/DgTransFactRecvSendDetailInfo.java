/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.parameterset.entity.OtherOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.system.entity.Company;
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

/**
 * @author yp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings("unchecked")
public class DgTransFactRecvSendDetailInfo extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JButton btnReloadSearch = null;

	private JButton btnClose = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTableListModel tableModel = null;

	private CasAction casAction = null;

	private JPanel jContentPane = null;

	private JLabel jLabel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private MaterialManageAction materialManageAction = null;
	
	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="792,76"
	
	private OtherOption otherOption = null; // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgTransFactRecvSendDetailInfo() {
		super(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		otherOption = CasCommonVars.getOtherOption();
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(712, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("收/送货明细表");
		this.getButtonGroup();
	}

	private void initUIComponents() {
		List lsScmCoc = new ArrayList();
		this.jLabel2.setText("供应商/客户名称");
		if (this.jRadioButton.isSelected()) {
			lsScmCoc = this.getManufacturer();
//			this.jLabel2.setText("供应商名称");
		} else {
			lsScmCoc = this.getCustomer();
//			this.jLabel2.setText("客户名称");
		}
		this.jComboBox.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
				"code", "name", 250);
		this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
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

//		List dataSource = this.casAction
//				.findLeftoverMaterielBalanceInfo(new Request(CommonVars
//						.getCurrUser(), true));
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
			if (scmCoc.getIsCustomer() != null
					&& scmCoc.getIsCustomer().booleanValue()) {
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
			if (scmCoc.getIsManufacturer() != null
					&& scmCoc.getIsManufacturer().booleanValue()) {
				manufacturerList.add(scmCoc);
			}
		}
		return manufacturerList;
	}

	/**
	 * This method initializes pn
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("供应商/客户名称");
			jLabel2.setBounds(new Rectangle(2, 8, 90, 15));
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new Rectangle(286, 34, 52, 15));
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("开始日期");
			jLabel.setBounds(new Rectangle(286, 7, 52, 15));
			jPanel.add(jLabel2, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getBtnClose(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes endDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setPreferredSize(new java.awt.Dimension(85, 22));
			cbbBeginDate.setBounds(new Rectangle(343, 4, 124, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnReloadSearch == null) {
			btnReloadSearch = new JButton();
			btnReloadSearch.setText("查询");
			btnReloadSearch.setBounds(new Rectangle(479, 3, 60, 26));
			btnReloadSearch.setPreferredSize(new java.awt.Dimension(60, 26));
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// if (JOptionPane.showConfirmDialog(
							// FmTransFactRecvSendDetailInfo.this,
							// btnReloadSearch.getText() + "\n确定要继续吗？？",
							// "警告!!!", JOptionPane.YES_NO_OPTION,
							// JOptionPane.WARNING_MESSAGE) ==
							// JOptionPane.YES_OPTION) {
							new ReloadSearchThread().start();
							// }

						}
					});
		}
		return btnReloadSearch;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setBounds(new Rectangle(609, 3, 60, 26));
			btnClose.setPreferredSize(new java.awt.Dimension(60, 26));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransFactRecvSendDetailInfo.this.dispose();
				}
			});
		}
		return btnClose;
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
	 * This method initializes pnContent
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
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setPreferredSize(new java.awt.Dimension(85, 22));
			cbbEndDate.setBounds(new Rectangle(343, 31, 124, 22));
		}
		return cbbEndDate;
	}
//hwy2012-9-25
	private void initTable(final List list) {
	
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("日期", "validDate", 100));
				list.add(addColumn("供应商/客户代码", "scmCoc.code", 120));
				list.add(addColumn("供应商/客户名称", "scmCoc.name", 180));
				list.add(addColumn("单据号", "billNo", 100));
				list.add(addColumn("工厂料号", "ptPart", 100));
				list.add(addColumn("工厂品名", "ptName", 100));
				list.add(addColumn("工厂规格", "ptSpec", 120));
				list.add(addColumn("工厂单位", "ptUnit.name", 100));
				list.add(addColumn("工厂数量", "ptRecvAmount", 180));
				list.add(addColumn("报关数量", "hsRecvAmount", 100));
				list.add(addColumn("重量", "recvNetWeight", 100));
				//去除退货单数量，把退货单数量转化负数，显示在进货单中
//				list.add(addColumn("工厂数量", "ptBackAmount", 100));
//				list.add(addColumn("报关数量", "hsBackAmount", 100));
//				list.add(addColumn("重量", "backNetWeight", 100));
				list.add(addColumn("对应报关单号", "customNo", 80));
				list.add(addColumn("商品编码", "complex.code", 100));
				list.add(addColumn("报关名称", "hsName", 100));
				list.add(addColumn("报关规格", "hsSpec", 80));
				list.add(addColumn("报关单位", "hsUnit.name", 100));
				list.add(addColumn("送货单号","takeBillNo",100));
				list.add(addColumn("采购业务订单号", "poSoBillNo", 180));
				list.add(addColumn("单据头备注", "note", 80));
				list.add(addColumn("单据体备注", "notice", 80));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		ColumnGroup gOriginal = new ColumnGroup("摘要");
		gOriginal.add(cm.getColumn(1));
		gOriginal.add(cm.getColumn(2));
		gOriginal.add(cm.getColumn(3));
		gOriginal.add(cm.getColumn(4));
		gOriginal.add(cm.getColumn(5));
		gOriginal.add(cm.getColumn(6));
		gOriginal.add(cm.getColumn(7));
		gOriginal.add(cm.getColumn(8));
		ColumnGroup gSend = new ColumnGroup(jRadioButton.isSelected() ? "<html><body>收货<font color='blue'>(正数为收货,负数为退货)</font></body></html>"
				: "<html><body>送货<font color='blue'>(正数为送货,负数为退货)</font></body></html>");
		gSend.add(cm.getColumn(9));
		gSend.add(cm.getColumn(10));
		gSend.add(cm.getColumn(11));
//		ColumnGroup gBack = new ColumnGroup("退货");
//		gBack.add(cm.getColumn(11));
//		gBack.add(cm.getColumn(12));
//		gBack.add(cm.getColumn(13));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gOriginal);
		header.addColumnGroup(gSend);
//		header.addColumnGroup(gBack);
		List<JTableListColumn> xs = tableModel.getColumns();
		// 显示小数位,默认2位
		int decimalLength = otherOption.getOtherReportMaximumFractionDigits() == null ? 2
				: otherOption.getOtherReportMaximumFractionDigits();
		xs.get(9).setFractionCount(decimalLength);
		xs.get(10).setFractionCount(decimalLength);
		xs.get(11).setFractionCount(decimalLength);
		xs.get(12).setFractionCount(decimalLength);
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(11).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(12).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(13).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(14).setCellRenderer(
				new tableCellRender());
	}
	
	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value == null) {
				this.setText("0.00");
			}else{
				if(value.equals(""))
					this.setText("0.00");
			}
			return this;
		}
	}

	private JLabel jLabel2 = null;

	private JComboBox jComboBox = null;

	private JPanel jPanel3 = null;

	private JRadioButton jRadioButton = null;

	private JRadioButton jRadioButton1 = null;



	private JButton jButton = null;

	private JSplitPane jSplitPane = null;

	private JCheckBox jCbIsCustomNo = null;

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class ReloadSearchThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				btnReloadSearch.setEnabled(false);
				List infos = new ArrayList();
				String info = "";
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
						DgTransFactRecvSendDetailInfo.this, false,
						progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				if (beginDate != null && endDate != null) {
					if (beginDate.before(endDate)) {

						infos = casAction.findTransFactRecvSendDetailInfo(
								new Request(CommonVars.getCurrUser()),
								jRadioButton.isSelected(),jCbIsCustomNo.isSelected(),(ScmCoc) jComboBox
										.getSelectedItem(), beginDate, endDate);
					}
				}
				System.out.println("infos size = " + infos.size());
				tableModel.setList(infos);
				CommonProgress.closeProgressDialog(flag);
//				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
//						+ " 毫秒 ";
//				JOptionPane.showMessageDialog(
//						DgTransFactRecvSendDetailInfo.this, info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(
						DgTransFactRecvSendDetailInfo.this, "重新获取失败！！！"
								+ e.getMessage(), "提示", 2);
			} finally {
				ToolsAction toolsAction = (ToolsAction) CommonVars
						.getApplicationContext().getBean("toolsAction");
				toolsAction.removeProgressInfo(flag);
			}
			btnReloadSearch.setEnabled(true);
			// String tipMessage = casAction.getClientInfosByLeftover(new
			// Request(
			// CommonVars.getCurrUser(), true));
			// JOptionPane.showMessageDialog(FmLeftoverMaterielStat.this,
			// tipMessage, "边角料查询计算过程", 2);
		}
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(115, 22));
			jComboBox.setBounds(new Rectangle(94, 5, 185, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new java.awt.Dimension(115, 26));
			jPanel3.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel3.setBounds(new Rectangle(8, 30, 271, 26));
			jPanel3.add(getJRadioButton(), null);
			jPanel3.add(getJRadioButton1(), null);
			jPanel3.add(getJCbIsCustomNo(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("转进");
			jRadioButton.setBounds(new Rectangle(7, 4
					, 49, 20));
			jRadioButton.setSelected(true);
			jRadioButton.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(e.getStateChange()==java.awt.event.ItemEvent.SELECTED){
						initUIComponents();
					}
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
			jRadioButton1.setText("转出");
			jRadioButton1.setBounds(new Rectangle(60, 4
					, 49, 20));
			jRadioButton1.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if(e.getStateChange()==java.awt.event.ItemEvent.SELECTED){
						initUIComponents();
					}
				}
			});
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.getJRadioButton());
			buttonGroup.add(this.getJRadioButton1());
		}
		return buttonGroup;
	}

	/**
	 * This method initializes btnCustomSort	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打印");
			jButton.setBounds(new Rectangle(544, 3, 60, 26));
			jButton.setPreferredSize(new java.awt.Dimension(60, 26));
			jButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					printOfReport();
				}
			});
		}
		return jButton;
	}

	private void printOfReport(){
		if(tableModel == null){
			JOptionPane.showMessageDialog(DgTransFactRecvSendDetailInfo.this, "当前无数据可列印！", "提示",
					JOptionPane.YES_NO_OPTION);
			return;
		}
		try{
		List list = tableModel.getList();
		CustomReportDataSource ds = new CustomReportDataSource(list);
		InputStream subReportStream = DgTransFactRecvSendDetailInfo.class
				.getResourceAsStream("report/TransFactRecvSendDetailInfoReport.jasper");
		JasperReport reportStream = (JasperReport) JRLoader
				.loadObject(subReportStream);
		Map<String, Object> parameters = new HashMap<String, Object>();
		String companyName=((Company)CommonVars.getCurrUser().getCompany()).getName();
		parameters.put("companyName", companyName);
		parameters.put("gys",jComboBox.getSelectedItem()==null?"":((ScmCoc)jComboBox.getSelectedItem()).getName());
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				reportStream, parameters, ds);
		DgReportViewer viewer = new DgReportViewer(jasperPrint);
		viewer.setVisible(true);

	} catch (Exception e2) {
		e2.printStackTrace();
	}
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
	 * This method initializes jCbIsCustomNo	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCbIsCustomNo() {
		if (jCbIsCustomNo == null) {
			jCbIsCustomNo = new JCheckBox();
			jCbIsCustomNo.setBounds(new Rectangle(111, 4, 155, 21));
			jCbIsCustomNo.setSelected(true);
			jCbIsCustomNo.setText("报关单号允许为空");
		}
		return jCbIsCustomNo;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
