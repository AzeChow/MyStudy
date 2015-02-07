/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contractanalyse.entity.TempStorageStatByProduct;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.dzsc.contractanalyse.action.DzscAnalyseAction;
import com.bestway.dzsc.contractanalyse.entity.TempDzscStorageStatByMateriel;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.message.action.DzscMessageAction;
import com.bestway.dzsc.message.entity.DzscParameterSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscStorageAnalyse extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnRefresh = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JDzscContractList jList = null;

	private JCheckBox cbContractExe = null;

	private GroupableHeaderTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JCheckBox cbContractCancel = null;

	private JScrollPane jScrollPane1 = null;

	private JPanel jPanel = null;

	private JButton btnPrint = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JCheckBox cbMateriel = null;

	private JCheckBox cbFinishedProduct = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JCalendarComboBox cbbEndDate = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JTableListModel tableModel = null;

	private DzscAnalyseAction contractAnalyseAction = null;

	private DzscParameterSet dzscParameterSet = null;

	private DzscMessageAction dzscMessageAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgDzscStorageAnalyse() {
		super();
		initialize();
		contractAnalyseAction = (DzscAnalyseAction) CommonVars
				.getApplicationContext().getBean("dzscAnalyseAction");
		dzscMessageAction = (DzscMessageAction) CommonVars
				.getApplicationContext().getBean("dzscMessageAction");
		dzscParameterSet = dzscMessageAction.findDzscMessageDirSet(new Request(
				CommonVars.getCurrUser()));
		initMaterielTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("料件库存分析");
		this.setContentPane(getJContentPane());
		this.setSize(715, 518);

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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel
					.setText("                                                    ");
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnPrint());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					refresh();
				}
			});
		}
		return btnRefresh;
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
			jSplitPane.setDividerLocation(555);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setLeftComponent(getJScrollPane());
		}
		return jSplitPane;
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
			jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0,
					0, 0));
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 13;
			gridBagConstraints1.ipady = -11;
			gridBagConstraints1.insets = new java.awt.Insets(5, 7, 1, 5);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipadx = 27;
			gridBagConstraints2.ipady = -8;
			gridBagConstraints2.insets = new java.awt.Insets(1, 7, 7, 15);
			jPanel2.add(getCbContractExe(), gridBagConstraints1);
			jPanel2.add(getCbContractCancel(), gridBagConstraints2);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JContractList
	 */
	private JDzscContractList getJList() {
		if (jList == null) {
			jList = new JDzscContractList();
			jList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					Date beginDate = null;
					// Date endDate=null;
					int size = jList.getModel().getSize();
					for (int i = 0; i < size; i++) {
						DzscEmsPorHead contract = (DzscEmsPorHead) jList
								.getModel().getElementAt(i);
						if (contract.isSelected()) {
							if (beginDate == null) {
								beginDate = contract.getBeginDate();
							} else {
								if (beginDate
										.compareTo(contract.getBeginDate()) > 0) {
									beginDate = contract.getBeginDate();
								}
							}
						}
					}
					if (beginDate != null) {
						cbbBeginDate.setDate(beginDate);
					}
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractExe() {
		if (cbContractExe == null) {
			cbContractExe = new JCheckBox();
			cbContractExe.setText("正在执行的合同");
			cbContractExe.setSelected(true);
			cbContractExe
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectContractItem();
						}
					});
		}
		return cbContractExe;
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
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbContractCancel() {
		if (cbContractCancel == null) {
			cbContractCancel = new JCheckBox();
			cbContractCancel.setText("核销的合同");
			cbContractCancel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							selectContractItem();
						}
					});
		}
		return cbContractCancel;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel1.setBounds(154, 3, 18, 24);
			jLabel1.setText("从");
			jLabel2.setBounds(285, 2, 20, 24);
			jLabel2.setText("到");
			jPanel.add(getCbMateriel(), null);
			jPanel.add(getCbFinishedProduct(), null);
			jPanel.add(getCbbBeginDate(), null);
			jPanel.add(getCbbEndDate(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrint() {
		if (btnPrint == null) {
			btnPrint = new JButton();
			btnPrint.setText("打印");
			btnPrint.setVisible(false);
			btnPrint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					print();
				}
			});
		}
		return btnPrint;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscStorageAnalyse.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jCheckBox2
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMateriel() {
		if (cbMateriel == null) {
			cbMateriel = new JCheckBox();
			cbMateriel.setBounds(12, 5, 49, 21);
			cbMateriel.setText("料件");
			cbMateriel.setSelected(true);
			cbMateriel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					cbFinishedProduct.setSelected(false);
					cbMateriel.setSelected(true);
				}

			});
		}
		return cbMateriel;
	}

	/**
	 * This method initializes jCheckBox3
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbFinishedProduct() {
		if (cbFinishedProduct == null) {
			cbFinishedProduct = new JCheckBox();
			cbFinishedProduct.setBounds(63, 5, 54, 21);
			cbFinishedProduct.setText("成品");
			cbFinishedProduct.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					cbFinishedProduct.setSelected(true);
					cbMateriel.setSelected(false);
				}

			});
		}
		return cbFinishedProduct;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(176, 3, 103, 24);
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
			cbbEndDate.setBounds(306, 3, 103, 24);
		}
		return cbbEndDate;
	}

	/**
	 * 刷新
	 * 
	 */
	public void refresh() {
		Date beginDate = this.cbbBeginDate.getDate();
		Date endDate = this.cbbEndDate.getDate();
		List contracts = this.jList.getSelectedContracts();
		if (beginDate == null || endDate == null || beginDate.after(endDate)
				|| contracts == null || contracts.size() <= 0) {
			if (this.cbMateriel.isSelected()) {
				initMaterielTable(new ArrayList());
			} else if (this.cbFinishedProduct.isSelected()) {
				initFinishProductTable(new ArrayList());
			}
		} else {
			if (this.cbMateriel.isSelected()) {
				List list = this.contractAnalyseAction.findStorageStat(
						new Request(CommonVars.getCurrUser()), contracts,
						beginDate, endDate, MaterielType.MATERIEL);
				initMaterielTable(list);
			} else if (this.cbFinishedProduct.isSelected()) {
				List list = this.contractAnalyseAction.findStorageStat(
						new Request(CommonVars.getCurrUser()), contracts,
						beginDate, endDate, MaterielType.FINISHED_PRODUCT);
				initFinishProductTable(list);
			}
		}

	}

	/**
	 * 初始化料件数据表
	 * 
	 */
	public void initMaterielTable(List dataSource) {
		List contracts = this.jList.getSelectedContracts();
		final int contractSize = contracts.size();
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter(true) {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("商品名称,规格", "nameSpec", 150));
						list.add(addColumn("单位", "unit.name", 50));
						for (int i = 0; i < contractSize; i++) {
							list.add(addColumn("总进口量", "totalImpAmount", 100,
									i, "tempContractStatList"));
							list.add(addColumn("大单进口量", "orderImpAmount", 100,
									i, "tempContractStatList"));
							list.add(addColumn("退料出口量",
									"backMaterielExpAmount", 100, i,
									"tempContractStatList"));
							list.add(addColumn("成品使用量",
									"finishProductDosageAmount", 100, i,
									"tempContractStatList"));
							list.add(addColumn("余料(库存)", "remainStorageAmount",
									100, i, "tempContractStatList"));
							list.add(addColumn("现进口余量", "nowImpAmount", 100, i,
									"tempContractStatList"));
						}
						list.add(addColumn("总合计", "totalize", 100));
						return list;
					}
				});

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();

		List<JTableListColumn> column = tableModel.getColumns();
		for (int i = 0; i < contractSize; i++) {
			column.get(4 + 6 * i).setFractionCount(decimalLength);
			column.get(5 + 6 * i).setFractionCount(decimalLength);
			column.get(6 + 6 * i).setFractionCount(decimalLength);
			column.get(7 + 6 * i).setFractionCount(decimalLength);
			column.get(8 + 6 * i).setFractionCount(decimalLength);
			column.get(9 + 6 * i).setFractionCount(decimalLength);

		}
		CompanyOther other = CommonVars.getOther();
		if (other == null) {
			return;
		}
		tableModel.setAllColumnsshowThousandthsign(other
				.getIsAutoshowThousandthsign() == null ? false : other
				.getIsAutoshowThousandthsign());
		column.get(4 + 6 * contractSize).setFractionCount(decimalLength);

		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();

		for (int i = 0; i < contractSize; i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			ColumnGroup tempColumnGroup = new ColumnGroup(contract
					.getIeContractNo());
			tempColumnGroup.add(cm.getColumn(4 + 6 * i));
			tempColumnGroup.add(cm.getColumn(5 + 6 * i));
			tempColumnGroup.add(cm.getColumn(6 + 6 * i));
			tempColumnGroup.add(cm.getColumn(7 + 6 * i));
			tempColumnGroup.add(cm.getColumn(8 + 6 * i));
			tempColumnGroup.add(cm.getColumn(9 + 6 * i));
			header.addColumnGroup(tempColumnGroup);
		}

	}

	/**
	 * 初始化成品数据表
	 * 
	 */
	public void initFinishProductTable(List dataSource) {
		List contracts = this.jList.getSelectedContracts();
		final int contractSize = contracts.size();
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter(true) {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品编码", "complexCode", 100));
						list.add(addColumn("商品名称,规格", "nameSpec", 150));
						list.add(addColumn("单位", "unit.name", 50));
						for (int i = 0; i < contractSize; i++) {
							list.add(addColumn("合同定量", "contractRation", 100,
									i, "tempContractStatList"));
							list.add(addColumn("总出口量", "totalExpAmount", 100,
									i, "tempContractStatList"));
							list.add(addColumn("返工数量", "returnAmount", 100, i,
									"tempContractStatList"));
							list.add(addColumn("大单出口量", "orderExpAmount", 100,
									i, "tempContractStatList"));
							list.add(addColumn("可以出口数量", "canExpRemain", 100,
									i, "tempContractStatList"));
							list.add(addColumn("现在出口数量", "nowExpAmount", 100,
									i, "tempContractStatList"));
						}
						list.add(addColumn("手册通关备案补充说明", "note", 120));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();

		for (int i = 0; i < contractSize; i++) {
			DzscEmsPorHead contract = (DzscEmsPorHead) contracts.get(i);
			ColumnGroup tempColumnGroup = new ColumnGroup(contract
					.getIeContractNo());
			tempColumnGroup.add(cm.getColumn(4 + 6 * i));
			tempColumnGroup.add(cm.getColumn(5 + 6 * i));
			tempColumnGroup.add(cm.getColumn(6 + 6 * i));
			tempColumnGroup.add(cm.getColumn(7 + 6 * i));
			tempColumnGroup.add(cm.getColumn(8 + 6 * i));
			tempColumnGroup.add(cm.getColumn(9 + 6 * i));
			header.addColumnGroup(tempColumnGroup);
		}

		// 显示小数位,默认2位
		Integer decimalLength = 2;
		if (dzscParameterSet != null
				&& dzscParameterSet.getReportDecimalLength() != null)
			decimalLength = dzscParameterSet.getReportDecimalLength();

		List<JTableListColumn> column = tableModel.getColumns();

		for (int i = 0; i < contractSize; i++) {
			column.get(4 + 6 * i).setFractionCount(decimalLength);
			column.get(5 + 6 * i).setFractionCount(decimalLength);
			column.get(6 + 6 * i).setFractionCount(decimalLength);
			column.get(7 + 6 * i).setFractionCount(decimalLength);
			column.get(8 + 6 * i).setFractionCount(decimalLength);
			column.get(9 + 6 * i).setFractionCount(decimalLength);

		}

	}

	/**
	 * 选择合同项目
	 */
	private void selectContractItem() {
		jList.showContractData(this.cbContractExe.isSelected(),
				this.cbContractCancel.isSelected());
	}

	/**
	 * 打印
	 * 
	 */
	private void print() {
		String company = CommonVars.getCurrUser().getCompany().getName();
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		if (cbMateriel.isSelected() == true) {
			try {
				//
				// 主报表
				//
				Map<String, Object> parameters = new HashMap<String, Object>();
				// parameters.put("company", company);
				List<TempDzscStorageStatByMateriel> list = new ArrayList<TempDzscStorageStatByMateriel>();
				//
				// 生成数据源没有加入
				//                
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/StorageAnalyseMateriel.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (cbFinishedProduct.isSelected() == true) {

			try {
				//
				// 主报表
				//
				Map<String, Object> parameters = new HashMap<String, Object>();
				// parameters.put("company", company);
				List<TempStorageStatByProduct> list = new ArrayList<TempStorageStatByProduct>();
				//
				// 生成数据源没有加入 TempStorageStatByProduct
				//   
				CustomReportDataSource ds = new CustomReportDataSource(list);
				InputStream masterReportStream = DgDzscMaterielExecuteAnalyse.class
						.getResourceAsStream("report/StorageAnalyseProduct.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						masterReportStream, parameters, ds);
				DgReportViewer viewer = new DgReportViewer(jasperPrint);
				viewer.setVisible(true);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}
