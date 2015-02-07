/*
 * Created on 2004-10-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillFixing;
import com.bestway.bcus.cas.entity.BillFixingBase;
import com.bestway.bcus.cas.entity.BillFixingThing;
import com.bestway.bcus.cas.entity.TempExgExportInfo;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.cas.report.CasReportVars;
import com.bestway.bcus.client.common.CommonDataSource;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.client.common.TableTextFieldEditor;
import com.bestway.bcus.client.common.TableTextFieldEditorEvent;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Condition;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCustomFixingThing extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel jPanel1 = null;

	private JCalendarComboBox cbbEndDate = null;

	private JTextField tfUser = null;

	private JCheckBox cbOverMillion = null;

	private JButton btnSearch = null;

	private JButton btnReloadSearch = null;

	private JButton btnDelete = null;

	private JButton btnPrint = null;

	private CasAction casAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModel2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel4 = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private JButton btnExit = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits();

	private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * This is the default constructor
	 */
	public DgCustomFixingThing() {
		super(false);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		initUIComponents();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {

		}
		super.setVisible(isFlag);
	}

	private void initUIComponents() {

		int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
				.getYearInt();
		Calendar beginClarendar = Calendar.getInstance();
//		beginClarendar.set(Calendar.YEAR, year-5);
		beginClarendar.set(Calendar.YEAR, year-4);//wss修改
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
		if (CasCommonVars.getOtherOption().getFillPerson() != null
				&& !CasCommonVars.getOtherOption().getFillPerson().equals("")) {
			tfUser.setText(CasCommonVars.getOtherOption().getFillPerson()
					.toString());
		}
		initTable(new ArrayList());
		initTable2(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("加工贸易生产设备使用情况表");
		this.setSize(835, 541);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	private void initTable(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter(
				maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("设备名称/单位", "bill1", 200));
				list.add(addColumn("1.直接报关进口", "billSum1", 100, Double.class));
				list.add(addColumn("2.结转报关进口", "billSum2", 100, Double.class));

				list.add(addColumn("3.其中征税进口", "billSum3", 100, Double.class));
				list.add(addColumn("4.国内采购", "billSum4", 100, Double.class));
				list.add(addColumn("5.其他来源", "billSum5", 100, Double.class));

				list.add(addColumn("6.来源合计", "billSum6", 100, Double.class));
				list.add(addColumn("7.退运出口", "billSum7", 100, Double.class));
				list.add(addColumn("8.结转报关出口", "billSum8", 100, Double.class));

				list.add(addColumn("9.出借/出租", "billSum9", 100, Double.class));
				list
						.add(addColumn("10.海关批准出售", "billSum10", 100,
								Double.class));
				list.add(addColumn("11.其他出售", "billSum11", 100, Double.class));

				list.add(addColumn("12.报废", "billSum12", 100, Double.class));

				list.add(addColumn("13.闲置", "billSum13", 100, Double.class));
				list.add(addColumn("14.其他处置", "billSum14", 100, Double.class));
				list.add(addColumn("15.在生产数量", "billSum15", 100, Double.class));

				list.add(addColumn("16.备注", "bill2", 100));
				return list;
			}
		};

		tableModel = new JTableListModel(jTable, list, jTableListModelAdapter,
				ListSelectionModel.SINGLE_SELECTION);
		//
		// 设置列的修改和修改 enter key event
		//
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 1; i < 18; i++) {
			if (i == 16) {
				continue;
			}
			editColumns.add(i);
			jTable.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		jTableListModelAdapter.setEditableColumn(editColumns);
		for (int i = 2; i < 18; i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(
					new tableCellRender());
		}
	}

	private void initTable2(final List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter(
				maximumFractionDigits) {
			public List<JTableListColumn> InitColumns() {
				List<JTableListColumn> list = new ArrayList<JTableListColumn>();
				list.add(addColumn("设备名称/单位", "bill1", 100));
				list.add(addColumn("1.直接报关进口", "billSum1", 100, Double.class));
				list.add(addColumn("2.结转报关进口", "billSum2", 100, Double.class));

				list.add(addColumn("3.其中征税进口", "billSum3", 100, Double.class));
				list.add(addColumn("4.国内采购", "billSum4", 100, Double.class));
				list.add(addColumn("5.其他来源", "billSum5", 100, Double.class));

				list.add(addColumn("6.来源合计", "billSum6", 100, Double.class));
				list.add(addColumn("7.退运出口", "billSum7", 100, Double.class));
				list.add(addColumn("8.结转报关出口", "billSum8", 100, Double.class));

				list.add(addColumn("9.出借/出租", "billSum9", 100, Double.class));
				list
						.add(addColumn("10.海关批准出售", "billSum10", 100,
								Double.class));
				list.add(addColumn("11.其他出售", "billSum11", 100, Double.class));

				list.add(addColumn("12.报废", "billSum12", 100, Double.class));

				list.add(addColumn("13.闲置", "billSum13", 100, Double.class));
				list.add(addColumn("14.其他处置", "billSum14", 100, Double.class));
				list.add(addColumn("15.在生产数量", "billSum15", 100, Double.class));

				list.add(addColumn("16.备注", "bill2", 100));
				return list;
			}
		};
		tableModel2 = new JTableListModel(jTable1, list,
				jTableListModelAdapter, ListSelectionModel.SINGLE_SELECTION);
		//
		// 设置列的修改和修改 enter key event
		//
		List<Integer> editColumns = new ArrayList<Integer>();
		for (int i = 1; i < 18; i++) {
			if (i == 16) {
				continue;
			}
			editColumns.add(i);
			jTable1.getColumnModel().getColumn(i).setCellEditor(
					new TableTextFieldEditor(new JTextField(), event));
		}
		jTableListModelAdapter.setEditableColumn(editColumns);
		for (int i = 2; i < 18; i++) {
			jTable1.getColumnModel().getColumn(i).setCellRenderer(
					new tableCellRender());
		}
	}

	class tableCellRender extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (value != null) {
				if (value instanceof Double) {
					Double tmp = Double.parseDouble((String) value);
					DecimalFormat df = new DecimalFormat("###");
					this.setText("" + df.format(tmp));
				}
			}
			return this;
		}
	}

	/**
	 * cellEditor 回车事件
	 */
	private TableTextFieldEditorEvent event = new TableTextFieldEditorEvent() {
		public Object saveObject(Object obj) {
			return casAction.saveBillFixingBase(new Request(CommonVars
					.getCurrUser(), true), (BillFixingBase) obj);

		}
	};

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel jLabel2 = null;

	private JButton btnDelete1 = null;

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(-2, 5, 62, 18));
			jLabel2.setText("开始日期：");
			jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setBounds(152, 4, 63, 22);
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel.setText("结束日期：");
			jLabel1.setBounds(301, 4, 43, 22);
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setText("填表人");
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getTfUser(), null);
			jPanel1.add(getCbOverMillion(), null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(jLabel2, null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(211, 4, 89, 22);
		}
		return cbbEndDate;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfUser() {
		if (tfUser == null) {
			tfUser = new JTextField();
			tfUser.setBounds(346, 4, 75, 22);
		}
		return tfUser;
	}

	/**
	 * 
	 * This method initializes jCheckBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JCheckBox
	 * 
	 */
	private JCheckBox getCbOverMillion() {
		if (cbOverMillion == null) {
			cbOverMillion = new JCheckBox();
			cbOverMillion.setBounds(419, 5, 76, 22);
			cbOverMillion.setText("超过百万");
		}
		return cbOverMillion;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new searchThread().start();
				}
			});

		}
		return btnSearch;
	}

	/**
	 * 
	 * This method initializes jButton1
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnReloadSearch() {
		if (btnReloadSearch == null) {
			btnReloadSearch = new JButton();
			btnReloadSearch.setText("重新计算");
			btnReloadSearch
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									DgCustomFixingThing.this, btnReloadSearch
											.getText()
											+ "\n确定要继续吗？？", "警告!!!",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
								new ReloadSearchThread().start();
							}
						}
					});

		}
		return btnReloadSearch;
	}

	/**
	 * 
	 * This method initializes jButton2
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						if (tableModel == null
								|| tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgCustomFixingThing.this, "请选择要删除的设备记录!!!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								DgCustomFixingThing.this, "确认要删除吗??", "警告!!!",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
							List list = tableModel.getCurrentRows();
							casAction.deleteBillFixingBase(new Request(
									CommonVars.getCurrUser(), true), list);
							tableModel.deleteRows(list);
						}
					} else {
						if (tableModel2 == null
								|| tableModel2.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(
									DgCustomFixingThing.this, "请选择要删除的设备记录!!!",
									"提示", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (JOptionPane.showConfirmDialog(
								DgCustomFixingThing.this, "确认要删除吗??", "警告!!!",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
							List list = tableModel2.getCurrentRows();
							casAction.deleteBillFixingBase(new Request(
									CommonVars.getCurrUser(), true), list);
							tableModel2.deleteRows(list);
						}
					}

				}
			});

		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						//list = tableModel.getList();
						list = tableModel.getCurrentRowsOrder();
					} else {
						//list = tableModel2.getList();
						list = tableModel2.getCurrentRowsOrder();
					}
					print(list);
					// if (list != null && !list.isEmpty()) {
					// List billList = new Vector();
					// for (int i = 0; i <= list.size() / 8; i++) { // for 1
					// for (int j = 0; j <= 16; j++) { // for2
					// int size = list.size();
					// BillTemp temp = new BillTemp();
					// if (size >= i * 8 + 1) {
					// temp.setBill1(getValue(i * 8, j, list));
					// }
					// if (size >= i * 8 + 2) {
					// temp.setBill2(getValue(i * 8 + 1, j, list));
					// }
					// if (size >= i * 8 + 3) {
					// temp.setBill3(getValue(i * 8 + 2, j, list));
					// }
					// if (size >= i * 8 + 4) {
					// temp.setBill4(getValue(i * 8 + 3, j, list));
					// }
					// if (size >= i * 8 + 5) {
					// temp.setBill5(getValue(i * 8 + 4, j, list));
					// }
					// if (size >= i * 8 + 6) {
					// temp.setBill6(getValue(i * 8 + 5, j, list));
					// }
					// if (size >= i * 8 + 7) {
					// temp.setBill7(getValue(i * 8 + 6, j, list));
					// }
					// if (size >= i * 8 + 8) {
					// temp.setBill8(getValue(i * 8 + 7, j, list));
					// }
					// billList.add(temp);
					// } // for2
					// } // for1
					// print(billList);
					// } // if
				}
			});

		}
		return btnPrint;
	}

	private String getValue(int j, int i, List<BillFixingBase> list) {
		if (jTabbedPane.getSelectedIndex() == 0) {
			return getValueFixing(j, i, list);
		} else {
			return getValueFixingThing(j, i, list);
		}
	}

	private String getValueFixingThing(int j, int i, List<BillFixingBase> list) {
		String yy = "";
		if (i == 0) { // 名称/单位
			yy = (list.get(j)).getBill1();
		} else if (i == 1) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum1())
					.toString();
		} else if (i == 2) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum2())
					.toString();
		} else if (i == 3) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum3())
					.toString();
		} else if (i == 4) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum4())
					.toString();
		} else if (i == 5) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum5())
					.toString();
		} else if (i == 6) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum6())
					.toString();
		} else if (i == 7) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum7())
					.toString();
		} else if (i == 8) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum8())
					.toString();
		} else if (i == 9) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum9())
					.toString();
		} else if (i == 10) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum10())
					.toString();
		} else if (i == 11) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum11())
					.toString();
		} else if (i == 12) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum12())
					.toString();
		} else if (i == 13) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum13())
					.toString();
		} else if (i == 14) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum14())
					.toString();
		} else if (i == 15) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum15())
					.toString();
		} else if (i == 16) { // 备注
			yy = (list.get(j)).getBill2();
		}
		return yy;
	}

	private String getValueFixing(int j, int i, List<BillFixingBase> list) {
		String yy = "";
		if (i == 0) { // 名称/单位
			yy = (list.get(j)).getBill1();
		} else if (i == 1) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum1())
					.toString();
		} else if (i == 2) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum2())
					.toString();
		} else if (i == 3) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum3())
					.toString();
		} else if (i == 4) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum4())
					.toString();
		} else if (i == 5) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum5())
					.toString();
		} else if (i == 6) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum6())
					.toString();
		} else if (i == 7) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum7())
					.toString();
		} else if (i == 8) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum8())
					.toString();
		} else if (i == 9) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum9())
					.toString();
		} else if (i == 10) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum10())
					.toString();
		} else if (i == 11) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum11())
					.toString();
		} else if (i == 12) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum12())
					.toString();
		} else if (i == 13) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum13())
					.toString();
		} else if (i == 14) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum14())
					.toString();
		} else if (i == 15) {
			yy = CommonVars.formatDouble((list.get(j)).getBillSum15())
					.toString();
		} else if (i == 16) { // 备注
			yy = (list.get(j)).getBill2();
		}
		return yy;
	}

	private void print(List list) {
		List<TempExgExportInfo> newList = new ArrayList<TempExgExportInfo>();
		for (int i = 0; i < list.size(); i++) {
			BillFixingBase oldObject = (BillFixingBase) list.get(i);
			for (int j = 0; j < 15; j++) {
				String fieldName = "";
				if (j == 15) {
					fieldName = "bill2";
				} else {
					fieldName = "billSum" + Integer.valueOf(j + 1).toString();
				}
				Object value = null;
				try {
					value = PropertyUtils.getProperty(oldObject, fieldName);
					if (value instanceof Double) {
						DecimalFormat df = new DecimalFormat("#,##0.00");
						value = df.format(value);
					}
				} catch (Exception e) {
					logger.error("获取属性时出错，属性名：" + fieldName);
				}
				if (i % 8 == 0) {
					newList.add(new TempExgExportInfo());
				}
				setValueToNewList(newList, i, j, value);
			}
		}

		CasReportVars.setBillFixingBaseList(list);
		CommonDataSource imgExgDS = new CommonDataSource(newList);
		// List company = new Vector();
		Company company = (Company) CommonVars.getCurrUser().getCompany();
		// company.add(CommonVars.getCurrUser().getCompany());
		// CommonDataSource companyDS = new CommonDataSource(company);
		InputStream masterReportStream = DgFactoryQuery.class
				.getResourceAsStream("report/CustomFixing.jasper");
		// InputStream detailReportStream = DgFactoryQuery.class
		// .getResourceAsStream("report/CustomFixingSubb.jasper");
		String writeer="";
		if (CasCommonVars.getOtherOption().getFillPerson() != null
				&& !CasCommonVars.getOtherOption().getFillPerson().equals("")) {
			writeer = CasCommonVars.getOtherOption().getFillPerson()
					.toString();
		} else {
			writeer = this.tfUser.getText();
		}
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String defaultDate = bartDateFormat.format(this.cbbEndDate.getDate());
		try {
			// JasperReport detailReport = (JasperReport) JRLoader
			// .loadObject(detailReportStream);

			Map<String, Object> parameters = new HashMap<String, Object>();
			// parameters.put("imgExgDS", imgExgDS);
			// parameters.put("detailReport", detailReport);
			parameters.put("writeer", writeer);
			parameters.put("riqi", defaultDate);
			parameters.put("name", company.getName());
			parameters.put("owner", company.getOwner());
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(masterReportStream,
					parameters, imgExgDS);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (JRException e1) {
			e1.printStackTrace();
		}
	}

	private void setValueToNewList(List newList, int i, int j, Object value) {
		String fieldName = "vf" + Integer.valueOf(i % 8 + 1).toString();
		Object newObject = newList.get((i / 8) * 15 + j);
		try {
			if (value == null) {
				PropertyUtils.setProperty(newObject, fieldName, "");
			} else {
				PropertyUtils.setProperty(newObject, fieldName, value
						.toString());
			}
		} catch (Exception e) {
			logger.error("设置属性时出错，属性名：" + fieldName);
		}
	}

	/**
	 * 
	 * This method initializes jTabbedPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JTabbedPane
	 * 
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("自用资料", null, getJPanel3(), null);
			jTabbedPane.addTab("海关资料", null, getJPanel4(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {

						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								btnReloadSearch.setText("重新计算");
								// btnDelete.setEnabled(false);
							} else {
								btnReloadSearch.setText("重新获取");
								// btnDelete.setEnabled(true);
							}
						}
					});

		}
		return jTabbedPane;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jTable1
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jButton4
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCustomFixingThing.this.dispose();

				}
			});

		}
		return btnExit;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel1());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnReloadSearch());
			jToolBar.add(getBtnDelete1());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 生成单据的折算报关数量
	 * 
	 * @author Administrator
	 * 
	 */
	class searchThread extends Thread {
		public void run() {
			//
			// 用于标识这个对话框的ID
			//
			UUID uuid = UUID.randomUUID();
			final String flag = uuid.toString();
			try {
				CommonProgress.showProgressDialog(flag,
						DgCustomFixingThing.this);
				CommonProgress.setMessage(flag, "系统正在进行查询，请稍后...");
				btnSearch.setEnabled(false);
				List<Condition> conditions = new ArrayList<Condition>();
				conditions.add(new Condition("and", null,
						"billMaster.validDate", "<=", cbbEndDate.getDate(),
						null));
				List infos = null;
				if (jTabbedPane.getSelectedIndex() == 0) {
					infos = casAction.findBillFixing(new Request(CommonVars
							.getCurrUser()), cbOverMillion.isSelected());
					tableModel.setList(infos);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					infos = casAction.findBillFixingThing(new Request(
							CommonVars.getCurrUser()), cbOverMillion
							.isSelected());
					tableModel2.setList(infos);
				}
				CommonProgress.closeProgressDialog(flag);
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgCustomFixingThing.this,
						"查询失败！！！" + e.getMessage(), "提示", 2);
			}
			btnSearch.setEnabled(true);
		}
	}

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
				CommonProgress.showProgressDialog(flag,
						DgCustomFixingThing.this);
				CommonProgress.setMessage(flag, "系统正在重新获取资料，请稍后...");
				btnReloadSearch.setEnabled(false);

//				List<Condition> conditions = new ArrayList<Condition>();
//				conditions.add(new Condition("and", null,
//						"billMaster.validDate", ">=", cbbBeginDate.getDate(), null));
//				conditions.add(new Condition("and", null,
//						"billMaster.validDate", "<=", cbbEndDate.getDate(), null));
				
				Date beginDate = cbbBeginDate.getDate();
				Date endDate = cbbEndDate.getDate();
								
				List infos = null;

				if (jTabbedPane.getSelectedIndex() == 0) {
//					infos = casAction.calBillFixing(new Request(CommonVars
//							.getCurrUser()), conditions);
					//wss:因要查五年的，所以改用原生SQL查询
					infos = casAction.calBillFixingBySql(new Request(CommonVars
															.getCurrUser()),
															beginDate,
															endDate);
					for (int i = 0; i < infos.size(); i++) {
						BillFixingBase exgExportInfo = (BillFixingBase) infos.get(i);
						if (exgExportInfo.getMoney() < 1000000) {
							exgExportInfo.setBill2("金额小于一百万");
						} else {
							exgExportInfo.setBill2("金额大于一百万");
						}
					}
					tableModel.setList(infos);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					casAction.copyBillFixingThing(new Request(CommonVars
							.getCurrUser()));
					infos = casAction.findBillFixingThing(new Request(
							CommonVars.getCurrUser()), cbOverMillion
							.isSelected());
					for (int i = 0; i < infos.size(); i++) {
						BillFixingBase exgExportInfo = (BillFixingBase) infos.get(i);
						if (exgExportInfo.getMoney() < 1000000) {
							exgExportInfo.setBill2("金额小于一百万");
						} else {
							exgExportInfo.setBill2("金额大于一百万");
						}
					}
					tableModel2.setList(infos);
				}

				CommonProgress.closeProgressDialog(flag);

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog(flag);
				JOptionPane.showMessageDialog(DgCustomFixingThing.this,
						"重新获取失败！！！" + e.getMessage(), "提示", 2);
			}
			btnReloadSearch.setEnabled(true);
		}
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						BillFixingBase b = new BillFixing();
						b = casAction.saveBillFixingBase(new Request(CommonVars
								.getCurrUser(), true), b);
						tableModel.addRow(b);
					} else {
						BillFixingBase b = new BillFixingThing();
						b = casAction.saveBillFixingBase(new Request(CommonVars
								.getCurrUser(), true), b);
						tableModel2.addRow(b);
					}
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(60, 4, 91, 22));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes btnDelete1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDelete1() {
		if (btnDelete1 == null) {
			btnDelete1 = new JButton();
			btnDelete1.setText("隐藏");
			btnDelete1.setToolTipText("打印年审报表时，不显示隐藏的商品，重新获取或关闭后后恢复");
			btnDelete1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						 list = tableModel.getCurrentRows();
						 tableModel.deleteRows(list);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						 list = tableModel2.getCurrentRows();
						 tableModel2.deleteRows(list);
					}
				}
			});
			
			
		}
		return btnDelete1;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
