package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FmCustomsDeclarationSet extends JDialogBase {

	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private SystemAction systemAction = null;
	private JTableListModel tableModel = null;
	/**
	 * This is the default constructor
	 */
	public FmCustomsDeclarationSet() {
		super();
		systemAction = (SystemAction) CommonVars.getApplicationContext().getBean("systemAction");
		initialize();
		List list = systemAction.findCustomsDeclarationSet(new Request(CommonVars.getCurrUser()));
		if (list != null && list.size()>0){
		    initTable(list);
		} else {
			initTable(new Vector());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(796, 505);
		this.setContentPane(getJContentPane());
		this.setTitle("报关单参数设置");
	}

	
	
	
	private void initTable(List list) {
			tableModel = new JTableListModel(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("进出口类型", "impType", 100));
							list.add(addColumn("报送海关", "declarationCustoms.name", 100));
							list.add(addColumn("征免性质", "customlevyKind.name", 100));							
							list.add(addColumn("提运单号", "billOfLading", 100));
							list.add(addColumn("结汇方式", "balanceMode.name", 100));
							list.add(addColumn("贸易方式", "tradeMode.name", 100));
							list.add(addColumn("运输工具", "conveyance", 100));
							list.add(addColumn("减免方式", "levyMode.name", 100));
							list.add(addColumn("用途", "uses.name", 100));
							list.add(addColumn("成交方式", "transac.name", 100));
							list.add(addColumn("原产国（体）", "country.name", 100));
							list.add(addColumn("起运国（头）", "coun.name", 100));
							list.add(addColumn("码头", "predock.name", 100));
							list.add(addColumn("装货港", "port.name", 100));
							list.add(addColumn("包装种类", "wrapType.name", 100));
							list.add(addColumn("进出口岸", "customs.name", 100));
							list.add(addColumn("运输方式", "transferMode.name", 100));
							list.add(addColumn("境内目的地", "district.name", 100));
							list.add(addColumn("币制", "curr.name", 100));
							return list;
						}
			});
			jTable.getColumnModel().getColumn(1).setCellRenderer(
					new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							String str = "";
							if (value != null) {
								str = ImpExpType.getImpExpTypeDesc(Integer
										.parseInt(value.toString()));
							}
							this.setText(str);
							return this;
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton2());			
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton3());
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
			jButton.setText("新增");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationSet dg = new DgCustomsDeclarationSet();
					dg.setTableModel(tableModel);
					dg.setIsAdd(true);
					dg.setVisible(true);
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
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCustomsDeclarationSet dg = new DgCustomsDeclarationSet();
					dg.setTableModel(tableModel);
					dg.setIsAdd(false);
					dg.setVisible(true);
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmCustomsDeclarationSet.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmCustomsDeclarationSet.this,
							"是否要删除该设置！", "确认", 0) == 0) {
						 List list = tableModel.getCurrentRows();
						 systemAction.deleteCustomsSet(new Request(CommonVars.getCurrUser()),list);
						 tableModel.deleteRows(list);
					}
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("退出");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton3;
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
			jTable = new JTable();
		}
		return jTable;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
