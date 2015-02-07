/*
 * Created on 2005-12-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * @author wp
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgBomToExcel extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private Contract contract = null;
	private ContractAction     contractAction = null;
	private List contractList = null;
	private JTableListModel    tableModelUnitWaste     = null;
	/**
	 * This is the default constructor
	 */
	public DgBomToExcel() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
          .getBean("contractAction");
		initialize();
	}
	
	/**
	 * 初始化单耗表
	 */
	private void initTbUnitWaste(List list) {
		tableModelUnitWaste = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("成品序号", "contractExg.seqNum", 60));
						list.add(addColumn("序号", "seqNum", 60));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("单价", "declarePrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("单耗", "unitWaste", 100));
						list.add(addColumn("损耗", "wasteAmount", 100));
						list.add(addColumn("单项用量", "unitDosage", 100));
						list.add(addColumn("金额", "totalPrice", 100));
						list.add(addColumn("法定数量", "legalAmount", 100));
						list.add(addColumn("法定单位", "legalUnit.name", 100));
						list.add(addColumn("单位重量", "unitWeight", 100));
						list.add(addColumn("主料/辅料", "materialType", 100));
						list.add(addColumn("原产地", "country.name", 100));
						list.add(addColumn("料件总表序号", "contractImgSeqNum", 100));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(16).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? "" : castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";

						if (value.equals(MaterielType.FINISHED_PRODUCT)) {
							returnValue = "成品";
						} else if (value.equals(MaterielType.MACHINE)) {
							returnValue = "设备";
						} else if (value.equals(MaterielType.MATERIEL)) {
							returnValue = "主料";
						} else if (value
								.equals(MaterielType.ASSISTANT_MATERIAL)) {
							returnValue = "辅料";
						} else if (value.equals(MaterielType.WASTE_MATERIAL)) {
							returnValue = "消耗料";
						}
						return returnValue;
					}
				});
		jTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(690, 425);
		this.setContentPane(getJContentPane());
		this.setTitle("输出Bom");
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initTbUnitWaste(new Vector());
				contractList = contractAction.findContractExgByParentId(new Request(CommonVars.getCurrUser()),contract.getId());
				
			}
		});
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
		}
		return jToolBar;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("选择成品");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					List list = BcsClientHelper.getInstance().findContractExg(contractList);
					if (list == null || list.size()<0){
						return;
					}
					List unitList  = contractAction.findUnitWaste(new Request(CommonVars.getCurrUser()),list);
					tableModelUnitWaste.addRows(unitList);
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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					DgBomToExcel.this.dispose();
				}
			});
		}
		return jButton1;
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
	}  //  @jve:decl-index=0:
	/**
	 * @return Returns the contract.
	 */
	public Contract getContract() {
		return contract;
	}
	/**
	 * @param contract The contract to set.
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}
     }  //  @jve:decl-index=0:
