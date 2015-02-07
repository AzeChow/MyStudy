/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.otherreport.DgTransFactCompareInfoOnMonth.tableCellRender;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.client.common.TableCheckBoxRender;
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
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import javax.swing.JCheckBox;

/**
 * @author yp
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgAllTransFactDiffInfo extends JDialogBase {

	private JPanel jPanel = null;

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
	private Integer otherReportMaximumFractionDigits = CasCommonVars
			.getOtherOption().getOtherReportMaximumFractionDigits() == null ? 2
			: CasCommonVars.getOtherOption()
					.getOtherReportMaximumFractionDigits(); // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgAllTransFactDiffInfo() {
		super(false);
		initialize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initUIComponents();
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
	} // @jve:decl-index=0:visual-constraint="10,10"

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(750, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("转厂差额总表");
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

	// private void initUIComponents() {
	// // List lsScmCoc = new ArrayList();
	// // if (this.jRadioButton.isSelected()) {
	// // lsScmCoc = this.getManufacturer();
	// // this.jLabel2.setText("供应商名称");
	// // } else {
	// // lsScmCoc = this.getCustomer();
	// // this.jLabel2.setText("客户名称");
	// //
	// // }
	// // this.jComboBox.setModel(new DefaultComboBoxModel(lsScmCoc.toArray()));
	// // CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox,
	// // "code", "name", 250);
	// // this.jComboBox.setRenderer(CustomBaseRender.getInstance().getRender(
	// // "code", "name", 80, 170));
	// // int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
	// // .getYearInt();
	// // Calendar beginClarendar = Calendar.getInstance();
	// // beginClarendar.set(Calendar.YEAR, year);
	// // beginClarendar.set(Calendar.MONTH, 0);
	// // beginClarendar.set(Calendar.DAY_OF_MONTH, 1);
	// // this.cbbBeginDate.setDate(beginClarendar.getTime());
	// // this.cbbBeginDate.setCalendar(beginClarendar);
	// //
	// // Calendar endClarendar = Calendar.getInstance();
	// // endClarendar.set(Calendar.YEAR, year);
	// // endClarendar.set(Calendar.MONTH, 11);
	// // endClarendar.set(Calendar.DAY_OF_MONTH, 31);
	// // this.cbbEndDate.setDate(endClarendar.getTime());
	// // this.cbbEndDate.setCalendar(endClarendar);
	// //
	// // List dataSource = this.casAction
	// // .findLeftoverMaterielBalanceInfo(new Request(CommonVars
	// // .getCurrUser(), true));
	// initTable(new ArrayList());
	// }

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
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(216, 6, 48, 18));
			jLabel11.setText("开始日期");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(9, 36, 48, 18));
			jLabel2.setText("\u5546\u54c1\u540d\u79f0");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(8, 7, 61, 18));
			jLabel.setText("\u4f9b\u5e94\u5546");
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel1.setBounds(new Rectangle(216, 38, 48, 18));
			jPanel = new JPanel();
			jPanel.setPreferredSize(new Dimension(450, 34));
			jPanel.setLayout(null);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel.add(jLabel1, null);
			jPanel.add(getBtnPrint(), null);
			jPanel.add(getBtnClose(), null);
			jPanel.add(getBtnSearch(), null);
			jPanel.add(jLabel, null);
			jPanel.add(getCbbScmCoc(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getTfCommName(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel11, null);
			jPanel.add(getCbbBeginDate(), null);
		}
		return jPanel;
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
			btnReloadSearch.setBounds(new Rectangle(485, 32, 60, 25));
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
			btnClose.setBounds(new Rectangle(552, 31, 60, 26));
			btnClose.setPreferredSize(new java.awt.Dimension(60, 26));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgAllTransFactDiffInfo.this.dispose();
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
			cbbEndDate.setPreferredSize(new java.awt.Dimension(185, 22));
			cbbEndDate.setBounds(new Rectangle(268, 35, 141, 22));
		}
		return cbbEndDate;
	}

	private void initTable(final List list) {
		if (tableModel != null) {
			tableModel.setList(list);
			return;
		}
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("商品编码", "complexCode", 100));
				list.add(addColumn("报关名称", "commName", 180));
				list.add(addColumn("报关单位", "commUnit", 100));
				list.add(addColumn("供应商", "scmCocName", 120));
				list.add(addColumn("待先转发生始日", "beginDate", 100));
				list.add(addColumn("数量", "waitTransAmount", 180));
				list.add(addColumn("重量", "waitTransWeight", 100));
				list.add(addColumn("数量", "exceedTransAmount", 100));
				list.add(addColumn("重量", "exceedTransWeight", 100));
				list.add(addColumn("数量", "envelopRemainAmount", 100));
				list.add(addColumn("重量", "envelopRemainWeight", 100));
				list.add(addColumn("数量", "diffAmount", 100));
				list.add(addColumn("重量", "diffWeight", 100));
				list.add(addColumn("备注", "", 100));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		TableColumnModel cm = jTable.getColumnModel();
		cm.getColumn(13).setCellRenderer(new TableCheckBoxRender());
		ColumnGroup gWait = new ColumnGroup("待转");
		gWait.add(cm.getColumn(6));
		gWait.add(cm.getColumn(7));
		ColumnGroup gExceed = new ColumnGroup("先转");
		gExceed.add(cm.getColumn(8));
		gExceed.add(cm.getColumn(9));
		ColumnGroup gEnvelopRemain = new ColumnGroup("关封余量");
		gEnvelopRemain.add(cm.getColumn(10));
		gEnvelopRemain.add(cm.getColumn(11));
		ColumnGroup gDiff = new ColumnGroup("差额");
		gDiff.add(cm.getColumn(12));
		gDiff.add(cm.getColumn(13));
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gWait);
		header.addColumnGroup(gExceed);
		header.addColumnGroup(gEnvelopRemain);
		header.addColumnGroup(gDiff);

		List<JTableListColumn> xs = tableModel.getColumns();

		xs.get(6).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(7).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(8).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(9).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(10).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(11).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(12).setFractionCount(otherReportMaximumFractionDigits);
		xs.get(13).setFractionCount(otherReportMaximumFractionDigits);

		jTable.getColumnModel().getColumn(6).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new tableCellRender());
		jTable.getColumnModel().getColumn(8).setCellRenderer(
				new tableCellRender());
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

	}

	class tableCellRender extends DefaultTableCellRenderer {
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

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="792,76"

	private JButton btnPrint = null;

	private JLabel jLabel = null;

	private JComboBox cbbScmCoc = null;

	private JLabel jLabel2 = null;

	private JTextField tfCommName = null;

	private JButton jButton = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbTransImport = null;

	private JRadioButton rbTransExport = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel11 = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCheckBox cbIsFindByScm = null;

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class ReloadSearchThread extends Thread {
		public void run() {
			if (cbIsFindByScm.isSelected()
					&& cbbScmCoc.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(DgAllTransFactDiffInfo.this,
						"请选择客户或供应商", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			// if (tfCommName.getText().trim().equals("")) {
			// JOptionPane.showMessageDialog(DgTransFactCompareInfoOnDay.this,
			// "请输入商品名称", "提示", JOptionPane.INFORMATION_MESSAGE);
			// return;
			// }
			if (cbbBeginDate.getDate() == null) {
				JOptionPane.showMessageDialog(DgAllTransFactDiffInfo.this,
						"请选择开始日期", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (cbbEndDate.getDate() == null) {
				JOptionPane.showMessageDialog(DgAllTransFactDiffInfo.this,
						"请选择结束日期", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
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
						DgAllTransFactDiffInfo.this, false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				// Date beginDate = cbbBeginDate.getDate();
				ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
				Date endDate = cbbEndDate.getDate();
				boolean isImg = rbTransImport.isSelected();
				boolean isFindByScm = cbIsFindByScm.isSelected();
				infos = casAction.findAllTransFactDiffInfo(new Request(
						CommonVars.getCurrUser()), isImg, isFindByScm, scmCoc,
						endDate);
				System.out.println("infos size = " + infos.size());
				tableModel.setList(infos);
				CommonProgress.closeProgressDialog(flag);
				// info += " 任务完成,共用时:" + (System.currentTimeMillis() -
				// beginTime)
				// + " 毫秒 ";
				// JOptionPane.showMessageDialog(
				// DgAllTransFactDiffInfo.this, info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgAllTransFactDiffInfo.this,
						"重新获取失败！！！" + e.getMessage(), "提示", 2);
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
	 * This method initializes btnCustomSort
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setBounds(new Rectangle(420, 32, 58, 25));
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList().isEmpty()) {
						JOptionPane.showMessageDialog(
								DgAllTransFactDiffInfo.this, "没有数据可打印", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					CustomReportDataSource ds = new CustomReportDataSource(
							tableModel.getList());
					Map<String, Object> parameters = new HashMap<String, Object>();
					String companyName = ((Company) CommonVars.getCurrUser()
							.getCompany()).getName();
					parameters.put("companyName", companyName);
					InputStream reportStream = DgFixtureNotInTaxation.class
							.getResourceAsStream("report/AllTransFactDiffInfoReport.jasper");
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
		return btnPrint;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(68, 3, 141, 23));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes tfCommName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCommName() {
		if (tfCommName == null) {
			tfCommName = new JTextField();
			tfCommName.setBounds(new Rectangle(68, 35, 122, 22));
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
			jButton.setBounds(new Rectangle(189, 35, 20, 21));
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(418, 2, 252, 28));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.RAISED));
			jPanel1.add(getRbTransImport(), null);
			jPanel1.add(getRbTransExport(), null);
			jPanel1.add(getCbIsFindByScm(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes rbTransImport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransImport() {
		if (rbTransImport == null) {
			rbTransImport = new JRadioButton();
			rbTransImport.setBounds(new Rectangle(154, 3, 49, 20));
			rbTransImport.setText("\u8f6c\u8fdb");
			rbTransImport.setSelected(true);
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
	 * This method initializes rbTransExport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbTransExport() {
		if (rbTransExport == null) {
			rbTransExport = new JRadioButton();
			rbTransExport.setBounds(new Rectangle(201, 3, 49, 20));
			rbTransExport.setText("\u8f6c\u51fa");
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

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(2);
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setDividerLocation(60);
		}
		return jSplitPane;
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
			jPanel2.add(getJScrollPane(), BorderLayout.NORTH);
		}
		return jPanel2;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(267, 3, 141, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbIsFindByScm
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsFindByScm() {
		if (cbIsFindByScm == null) {
			cbIsFindByScm = new JCheckBox();
			cbIsFindByScm.setBounds(new Rectangle(6, 3, 149, 21));
			cbIsFindByScm.setText("是否按供应商/客户查询");
		}
		return cbIsFindByScm;
	}
}
