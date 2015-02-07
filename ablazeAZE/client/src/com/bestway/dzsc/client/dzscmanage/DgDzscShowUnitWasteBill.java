/*
 * Created on 2005-5-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.dzscmanage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDzscShowUnitWasteBill extends JDialogBase {

	private javax.swing.JPanel			jContentPane	= null;
	private JTable						jTable			= null;
	private JScrollPane					jScrollPane		= null;
	private JToolBar					jToolBar		= null;
	private JButton						btnUpBatch		= null;
	private JButton						btnNextBatch	= null;
	private JButton						btnOverprint	= null;
	private JButton						btnNotOverprint	= null;
	private JButton						btnExit			= null;
	private DzscEmsPorHead				head			= null; // 头
	private AttributiveCellTableModel	tableModel		= null;
	private int							length			= 6;
	private int							index			= -6;
	private int							count			= 0;
	private DzscAction					dzscAction		= null;

	/**
	 * This is the default constructor
	 */
	public DgDzscShowUnitWasteBill() {
		super();
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
		jTable = new MultiSpanCellTable();
		jScrollPane.setViewportView(jTable);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("成品对应料件单耗表");
		this.setSize(720, 500);
		this.setContentPane(getJContentPane());
	}

	public void setVisible(boolean isShow) {
		if (isShow) {
			count = this.dzscAction.findDzscEmsExgBillCount(new Request(
					CommonVars.getCurrUser()), head.getId());
			changeBatch(true);
		}
		super.setVisible(isShow);
	}

	public DzscEmsPorHead getHead() {
		return head;
	}

	public void setHead(DzscEmsPorHead head) {
		this.head = head;
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
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
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnUpBatch());
			jToolBar.add(getBtnNextBatch());
			jToolBar.add(getBtnOverprint());
			jToolBar.add(getBtnNotOverprint());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpBatch() {
		if (btnUpBatch == null) {
			btnUpBatch = new JButton();
			btnUpBatch.setText("上一批成品");
			btnUpBatch.setEnabled(false);
			btnUpBatch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					changeBatch(false);
				}
			});
		}
		return btnUpBatch;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNextBatch() {
		if (btnNextBatch == null) {
			btnNextBatch = new JButton();
			btnNextBatch.setText("下一批成品");
			btnNextBatch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					changeBatch(true);
				}
			});
		}
		return btnNextBatch;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOverprint() {
		if (btnOverprint == null) {
			btnOverprint = new JButton();
			btnOverprint.setText("套打单耗表");
			btnOverprint.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					print(true);
				}
			});
		}
		return btnOverprint;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNotOverprint() {
		if (btnNotOverprint == null) {
			btnNotOverprint = new JButton();
			btnNotOverprint.setText("非套打单耗表");
			btnNotOverprint
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							print(false);
						}
					});
		}
		return btnNotOverprint;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscShowUnitWasteBill.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化成品物料单耗表
	 * 
	 * @param list
	 */
	private void initTable(List dataSource, int index) {
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				dataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "ptNo", 100));
						list.add(addColumn("进口料件品名", "ptName", 100));
						list.add(addColumn("单耗", "unitWaste1", 80));
						list.add(addColumn("损耗", "wasteRate1", 80));
						list.add(addColumn("单耗", "unitWaste2", 80));
						list.add(addColumn("损耗", "wasteRate2", 80));
						list.add(addColumn("单耗", "unitWaste3", 80));
						list.add(addColumn("损耗", "wasteRate3", 80));
						list.add(addColumn("单耗", "unitWaste4", 80));
						list.add(addColumn("损耗", "wasteRate4", 80));
						list.add(addColumn("单耗", "unitWaste5", 80));
						list.add(addColumn("损耗", "wasteRate5", 80));
						list.add(addColumn("单耗", "unitWaste6", 80));
						list.add(addColumn("损耗", "wasteRate6", 80));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		ContractUnitWaste w = null;
		if(dataSource!=null && dataSource.size()>=1){
			w = (ContractUnitWaste)dataSource.get(0);
		}else{
			w = new ContractUnitWaste();
		}
		
		ColumnGroup gFinishedProduct =null;
		ColumnGroup titel1 = null;
		ColumnGroup titel2 = null;
		gFinishedProduct = new ColumnGroup("成品 "+ getShowIndex(index + 1));
		titel1 = new ColumnGroup("出品总数量：");
		titel1.add(cm.getColumn(3));
	    titel2 = new ColumnGroup(w.getAmount1().toString());
		titel2.add(cm.getColumn(4));
		gFinishedProduct.add(titel1);
		gFinishedProduct.add(titel2);
		header.addColumnGroup(gFinishedProduct);
		
		gFinishedProduct = new ColumnGroup("成品 " + getShowIndex(index + 2));
		titel1 = new ColumnGroup("出品总数量：");
		titel1.add(cm.getColumn(5));
	    titel2 = new ColumnGroup(w.getAmount2().toString());
		titel2.add(cm.getColumn(6));
		gFinishedProduct.add(titel1);
		gFinishedProduct.add(titel2);
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup("成品 " + getShowIndex(index + 3));
		titel1 = new ColumnGroup("出品总数量：");
		titel1.add(cm.getColumn(7));
	    titel2 = new ColumnGroup(w.getAmount3().toString());
		titel2.add(cm.getColumn(8));
		gFinishedProduct.add(titel1);
		gFinishedProduct.add(titel2);
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup("成品 " + getShowIndex(index + 4));
		titel1 = new ColumnGroup("出品总数量：");
		titel1.add(cm.getColumn(9));
	    titel2 = new ColumnGroup(w.getAmount4().toString());
		titel2.add(cm.getColumn(10));
		gFinishedProduct.add(titel1);
		gFinishedProduct.add(titel2);
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup("成品 " + getShowIndex(index + 5));
		titel1 = new ColumnGroup("出品总数量：");
		titel1.add(cm.getColumn(11));
	    titel2 = new ColumnGroup(w.getAmount5().toString());
		titel2.add(cm.getColumn(12));
		gFinishedProduct.add(titel1);
		gFinishedProduct.add(titel2);
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup("成品 " + getShowIndex(index + 6));
		titel1 = new ColumnGroup("出品总数量：");
		titel1.add(cm.getColumn(13));
	    titel2 = new ColumnGroup(w.getAmount6().toString());
		titel2.add(cm.getColumn(14));
		gFinishedProduct.add(titel1);
		gFinishedProduct.add(titel2);
		header.addColumnGroup(gFinishedProduct);
		
	}

	/**
	 * 更新批
	 * 
	 * @param isNextBatch
	 */
	private void changeBatch(Boolean isNextBatch) {
		if (isNextBatch == false) {
			index -= length;
		} else if (isNextBatch == true) {
			index += length;
		}
		List list = this.dzscAction.findContractUnitWasteByCustom(new Request(
				CommonVars.getCurrUser()), head.getId(), index, length);
		initTable(list, index);

		if (index >= count - 1 || count <= length) {
			this.btnNextBatch.setEnabled(false);
		} else {
			this.btnNextBatch.setEnabled(true);
		}
		if (index == 0) {
			this.btnUpBatch.setEnabled(false);
		} else {
			this.btnUpBatch.setEnabled(true);
		}

	}

	/**
	 * 获得实际要显示的成品个数值
	 * 
	 * @param index
	 * @return
	 */
	private String getShowIndex(int index) {
		if (index > count) {
			return "";
		}
		return String.valueOf(index);
	}

	/**
	 * 打印报表
	 * 
	 * @param isOverprint
	 */
	private void print(boolean isOverprint) {
		try {
			List list = new ArrayList();
			String emsNo = "";
			if (head != null) {
				String parentId = head.getId();
				list = dzscAction.findContractUnitWasteByCustom(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				emsNo = head.getImContractNo() == null ? "" : head
						.getImContractNo();
			}
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsNo);
			parameters.put("isOverprint", new Boolean(isOverprint));
			parameters.put("companyName", head.getMachName());
			parameters.put("count", count);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			InputStream masterReportStream = DgDzscEmsPor.class
			.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {

		}
	}

	

} // @jve:decl-index=0:visual-constraint="10,10"
