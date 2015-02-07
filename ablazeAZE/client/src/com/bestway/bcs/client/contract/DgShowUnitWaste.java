/*
 * Created on 2005-5-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Component;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractUnitWaste;
import com.bestway.bcs.contract.entity.ImgExgObject;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomReportDataSource;
import com.bestway.bcus.client.common.DgReportViewer;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 成品对应料件单耗表
 * 
 * @author ls change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates
 */
public class DgShowUnitWaste extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JToolBar jToolBar = null;
	private JButton btnUpBatch = null;
	private JButton btnNextBatch = null;
	private JButton btnOverprint = null;
	private JButton btnNotOverprint = null;
	private JButton btnExportAll = null;
	private JButton btnExit = null;
	private Contract contract = null;
	private ContractAction contractAction = null;
	private AttributiveCellTableModel tableModel = null;
	private int length = 6;
	private int index = -6;
	private int count = 0;

	/**
	 * This is the default constructor
	 */
	public DgShowUnitWaste() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
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
			count = this.contractAction.findContractExgCount(new Request(
					CommonVars.getCurrUser()), contract.getId());
			changeBatch(true);
		}
		super.setVisible(isShow);
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
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
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
			jToolBar.add(getBtnExportAll());
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
	 * This method initializes btnExportAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExportAll() {
		if (btnExportAll == null) {
			btnExportAll = new JButton();
			btnExportAll.setText("显示所有成品");
			btnExportAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//initTable(new Vector(),-1);
					//count = contractAction.findContractExgCount(new Request(
					//		CommonVars.getCurrUser()), contract.getId());
					List list = contractAction.findContractUnitWasteAllNoGroup(new Request(
							CommonVars.getCurrUser()), contract.getId());
					initTableAll(list, count);
				}
			});
		}
		return btnExportAll;
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
					DgShowUnitWaste.this.dispose();
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
						list.add(addColumn("料件序号", "ptNo", 50));
						list.add(addColumn("进口料件品名", "ptName", 100));
						list.add(addColumn("单耗", "unitWaste1", 55));
						list.add(addColumn("损耗", "wasteRate1", 55));
						list.add(addColumn("单耗", "unitWaste2", 55));
						list.add(addColumn("损耗", "wasteRate2", 55));
						list.add(addColumn("单耗", "unitWaste3", 55));
						list.add(addColumn("损耗", "wasteRate3", 55));
						list.add(addColumn("单耗", "unitWaste4", 55));
						list.add(addColumn("损耗", "wasteRate4", 55));
						list.add(addColumn("单耗", "unitWaste5", 55));
						list.add(addColumn("损耗", "wasteRate5", 55));
						list.add(addColumn("单耗", "unitWaste6", 55));
						list.add(addColumn("损耗", "wasteRate6", 55));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		if (tableModel.getList().size() < 1) {
			return;
		}
		ContractUnitWaste unitWaste = (ContractUnitWaste) tableModel.getList()
				.get(0);
		List unitWastes = tableModel.getList();
		System.out.println(unitWastes.size()+"=============");
		ColumnGroup gFinishedProduct = new ColumnGroup(
				unitWaste.getExg1() != null ? unitWaste.getExg1() : "");
		gFinishedProduct.add(cm.getColumn(3));
		gFinishedProduct.add(cm.getColumn(4));
		header.addColumnGroup(gFinishedProduct);
		gFinishedProduct = new ColumnGroup(
				unitWaste.getExg2() != null ? unitWaste.getExg2() : "");
		gFinishedProduct.add(cm.getColumn(5));
		gFinishedProduct.add(cm.getColumn(6));
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup(
				unitWaste.getExg3() != null ? unitWaste.getExg3() : "");
		gFinishedProduct.add(cm.getColumn(7));
		gFinishedProduct.add(cm.getColumn(8));
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup(
				unitWaste.getExg4() != null ? unitWaste.getExg4() : "");
		gFinishedProduct.add(cm.getColumn(9));
		gFinishedProduct.add(cm.getColumn(10));
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup(
				unitWaste.getExg5() != null ? unitWaste.getExg5() : "");
		gFinishedProduct.add(cm.getColumn(11));
		gFinishedProduct.add(cm.getColumn(12));
		header.addColumnGroup(gFinishedProduct);

		gFinishedProduct = new ColumnGroup(
				unitWaste.getExg6() != null ? unitWaste.getExg6() : "");
		gFinishedProduct.add(cm.getColumn(13));
		gFinishedProduct.add(cm.getColumn(14));
		header.addColumnGroup(gFinishedProduct);
	}
	/**
	 * 初始化所有成品物料单耗表
	 * 
	 * @param list
	 */
	private void initTableAll(List dataSource, int index) {
		ImgExgObject imgExgObject = (ImgExgObject)dataSource.get(0);
		if (imgExgObject==null) {
			return;
		}
		Object[][] imgObj = imgExgObject.getImgObj();
		final Object[] exgObj = imgExgObject.getExgObj();
		List data = new ArrayList(imgObj.length);
		for (int i = 0; i < imgObj.length; i++) {
			data.add(imgObj[i]);
		}
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) jTable,
				data, new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", 50));
						list.add(addColumn("进口料件品名", 100));
						for (int i = 0; i < exgObj.length/2; i++) {
							list.add(addColumn("单耗", 100));
							list.add(addColumn("损耗", 100));
						}
						return list;
					}
				});
		TableColumnModel cm = jTable.getColumnModel();
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		int n = 0;
		for (int i = 0; i < exgObj.length; i++) {
			if((i%2)!=0){
				ColumnGroup gFinishedProduct = new ColumnGroup(
						exgObj[i] != null ? exgObj[i].toString() : "ss");
				cm.getColumn(2 * n + 3).setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = 	CommonUtils.formatDoubleByDigit(Double.valueOf(value.toString()), 6);
						}
						this.setText(str);
						return this;
					}
				} );
				cm.getColumn(2 * n + 4).setCellRenderer(new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String str = "";
						if (value != null) {
							str = 	CommonUtils.formatDoubleByDigit(Double.valueOf(value.toString()), 6);
						}
						this.setText(str);
						return this;
					}
				} );
				gFinishedProduct.add(cm.getColumn(2 * n + 3));
				gFinishedProduct.add(cm.getColumn(2 * n + 4));
				n++;
				header.addColumnGroup(gFinishedProduct);
			}
		}
		
		
//		if (tableModel.getList().size() < 1) {
//			return;
//		}
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
		List list = this.contractAction.findContractUnitWaste(new Request(
				CommonVars.getCurrUser()), contract.getId(), index, length);
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
			List squNoList = new ArrayList();
			String emsNo = "";
			String contractNo = "";
			if (contract != null) {
				String parentId = contract.getId();
				list = contractAction.findContractUnitWaste(new Request(
						CommonVars.getCurrUser()), parentId, -1, -1);
				emsNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
				contractNo = contract.getExpContractNo() == null ? "" : contract
						.getExpContractNo();
			}
			if (list == null || list.size() == 0) {
				JOptionPane.showMessageDialog(DgShowUnitWaste.this, "不存在单损耗表!");
				return;
			}
			int groupId = 0;
			for (int i = 0, size = list.size(); i < size; i++) {
				ContractUnitWaste c = (ContractUnitWaste) list.get(i);
				if (c.getGroupId() == groupId) {
					System.out.println(c.getGroupId());
					squNoList.add(c.getExgSeqNum1() == null ? "" : c
							.getExgSeqNum1().toString());
					squNoList.add(c.getExgSeqNum2() == null ? "" : c
							.getExgSeqNum2().toString());
					squNoList.add(c.getExgSeqNum3() == null ? "" : c
							.getExgSeqNum3().toString());
					squNoList.add(c.getExgSeqNum4() == null ? "" : c
							.getExgSeqNum4().toString());
					squNoList.add(c.getExgSeqNum5() == null ? "" : c
							.getExgSeqNum5().toString());
					squNoList.add(c.getExgSeqNum6() == null ? "" : c
							.getExgSeqNum6().toString());
					groupId++;
				}
			}
			for (int i = 0; i < squNoList.size(); i++) {
				System.out.println(squNoList.get(i).toString());
			}
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("emsNo", emsNo);
			parameters.put("contractNo", contractNo);
			parameters.put("isOverprint", new Boolean(isOverprint));
			parameters.put("companyName", contract.getMachName());
			parameters.put("count", count);
			parameters.put("squNoList", squNoList);
			CustomReportDataSource ds = new CustomReportDataSource(list);
			ds.setMaximumFractionDigits(9);
			InputStream masterReportStream = DgContract.class
					.getResourceAsStream("report/MaterielUnitWasteReport.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					masterReportStream, parameters, ds);
			DgReportViewer viewer = new DgReportViewer(jasperPrint);
			viewer.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
