/*
 * Created on 2004-10-19
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.LeftoverMaterielBalanceInfo;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ProgressTask;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.common.ProgressInfo;
import com.bestway.common.ProjectTypeParam;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmLeftoverMaterielStat extends JDialogBase {

	private JPanel				jPanel			= null;
	private JLabel				jLabel			= null;
	private JCalendarComboBox	cbbBeginDate	= null;
	private JButton				btnReloadSearch	= null;
	private JButton				btnClose		= null;
	private JPanel				jPanel2			= null;
	private JTable				jTable			= null;
	private JScrollPane			jScrollPane		= null;
	private JTableListModel		tableModel		= null;
	private CasAction			casAction		= null;
	private JPanel				jContentPane	= null;
	private JToolBar			jToolBar		= null;
	private JLabel				jLabel1			= null;
	private JCalendarComboBox	cbbEndDate		= null;
	private JCheckBox			cbBcus			= null;
	private JCheckBox			cbBcs			= null;
	private JCheckBox			cbDzsc			= null;

	/**
	 * This method initializes
	 * 
	 */
	public FmLeftoverMaterielStat() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		initUIComponents();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(733, 541);
		this.setContentPane(getJContentPane());
		this.setTitle("边角料查询报表");

	}

	private void initUIComponents() {

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

		List dataSource = this.casAction
				.findLeftoverMaterielBalanceInfo(new Request(CommonVars
						.getCurrUser(), true));
		initTable(dataSource);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			FlowLayout flowLayout1 = new FlowLayout();
			flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
			flowLayout1.setVgap(1);
			jLabel1 = new JLabel();
			jLabel1.setText("结束日期");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(flowLayout1);
			jPanel
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("开始日期");
			jPanel.add(jLabel, null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(getCbBcus(), null);
			jPanel.add(getCbBcs(), null);
			jPanel.add(getCbDzsc(), null);
			jPanel.add(getBtnSearch(), null);
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
			btnReloadSearch.setText("报表计算");
			btnReloadSearch.setPreferredSize(new java.awt.Dimension(86, 26));
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									FmLeftoverMaterielStat.this,
									btnReloadSearch.getText() + "\n确定要继续吗？？",
									"警告!!!", JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
								new ReloadSearchThread().start();
							}

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
			btnClose.setPreferredSize(new java.awt.Dimension(60, 26));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmLeftoverMaterielStat.this.dispose();
				}
			});
		}
		return btnClose;
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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
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
		}
		return jToolBar;
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
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes cbBcus
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBcus() {
		if (cbBcus == null) {
			cbBcus = new JCheckBox();
			cbBcus.setSelected(true);
			cbBcus.setText("联网监管");
		}
		return cbBcus;
	}

	/**
	 * This method initializes cbBcs
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBcs() {
		if (cbBcs == null) {
			cbBcs = new JCheckBox();
			cbBcs.setSelected(true);
			cbBcs.setText("纸质手册");
		}
		return cbBcs;
	}

	/**
	 * This method initializes cbDzsc
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbDzsc() {
		if (cbDzsc == null) {
			cbDzsc = new JCheckBox();
			cbDzsc.setSelected(true);
			cbDzsc.setText("电子手册");
		}
		return cbDzsc;
	}

	private void initTable(final List list) {
		JTableListModelAdapter adapter = new JTableListModelAdapter() {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("商品编码", "complex", 100));
				list.add(addColumn("料件名称/规格/单位", "key", 180));
				list.add(addColumn("1.边角料入库量", "f1", 100));
				list.add(addColumn("2.边角料出库量", "f2", 120));
				list.add(addColumn("3.实际库存量=1-2", "f3", 100));
				list.add(addColumn("4.成品入库单折损耗", "f4", 180));
				list.add(addColumn("5.各合同的损耗量之和", "f5", 180));
				list.add(addColumn("6.差额 = 1-5 ", "f6", 100));
				list.add(addColumn("7.单价", "unitPrice", 100));
				list.add(addColumn("8.出口税率", "outRate", 100));
				list.add(addColumn("9.增值税率", "taxRate", 100));
				list.add(addColumn("10.应补税额=6*7*8+(6*7*8+6*7)*9", "f7", 200));
				return list;
			}
		};
		tableModel = new JTableListModel(jTable, list, adapter,
				ListSelectionModel.SINGLE_SELECTION);
		//
		// 设置列的修改和修改 enter key event
		//
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 9; i < 12; i++) {
			editColumns.add(i);
			jTable.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		adapter.setEditableColumn(editColumns);

	}

	/**
	 * cellEditor 回车事件
	 */
	private TableTextFieldEditorEvent	event					= new TableTextFieldEditorEvent() {
																	public Object saveObject(
																			Object obj) {
																		return casAction
																				.saveLeftoverMaterielBalanceInfo(
																						new Request(
																								CommonVars
																										.getCurrUser(),
																								true),
																						(LeftoverMaterielBalanceInfo) obj);

																	}
																};
	private JPanel						jPanel1					= null;
	private JToolBar					jJToolBarBar			= null;
	private JCheckBox					cbCalcProductToWaste	= null;

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
						if (progressInfo != null && progressInfo.getHintMessage() != null) {
							CommonProgress.setMessage(flag, progressInfo
									.getHintMessage());
						}
					}
				};
				CommonProgress.showProgressDialog(flag,
						FmLeftoverMaterielStat.this, false, progressTask, 5000);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
				if (beginDate != null && endDate != null) {
					if (beginDate.before(endDate)) {
						boolean isCalcProductToWaste = cbCalcProductToWaste
								.isSelected();
						infos = casAction.findCalLeftoverMateriel(new Request(
								CommonVars.getCurrUser()), beginDate, endDate,
								getProjectTypeParam(), isCalcProductToWaste,
								flag);
					}
				}
				System.out.println("infos size = " + infos.size());
				tableModel.setList(infos);
				CommonProgress.closeProgressDialog(flag);
				info += " 任务完成,共用时:" + (System.currentTimeMillis() - beginTime)
						+ " 毫秒 ";
				JOptionPane.showMessageDialog(FmLeftoverMaterielStat.this,
						info, "提示", 2);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(FmLeftoverMaterielStat.this,
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

	private ProjectTypeParam getProjectTypeParam() {
		ProjectTypeParam projectTypeParam = new ProjectTypeParam();
		projectTypeParam.setIsBcs(this.cbBcs.isSelected());
		projectTypeParam.setIsBcus(this.cbBcus.isSelected());
		projectTypeParam.setIsDzsc(this.cbDzsc.isSelected());
		return projectTypeParam;
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
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJJToolBarBar(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getCbCalcProductToWaste());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCalcProductToWaste() {
		if (cbCalcProductToWaste == null) {
			cbCalcProductToWaste = new JCheckBox();
			cbCalcProductToWaste.setText("是否计算成品入库单折损耗");
		}
		return cbCalcProductToWaste;
	}
}
