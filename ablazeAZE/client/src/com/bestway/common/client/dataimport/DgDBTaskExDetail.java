/*
 * Created on 2004-10-7
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.Component;
import java.awt.FlowLayout;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBTaskEx;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgDBTaskExDetail extends JDialogBase {

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JLabel jLabel = new JLabel();
	private JButton jButton = null;

	private DBTaskEx dbTaskEx = null;

	private DataImportAction dataImportAction = null;

	private JTableListModel tableModel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;
	
	private String titleName = null;

	public DgDBTaskExDetail() {
		super();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initialize();
		
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJPanel());
		this.setSize(436, 288);
		this.setTitle("设置任务包含导入的表");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				
				if (tableModel.getCurrentRow() != null) {
					dbTaskEx = (DBTaskEx) tableModel.getCurrentRow();
					List list = dataImportAction.findDBTaskSel(new Request(
							CommonVars.getCurrUser()), dbTaskEx);
					
					if (list !=null && !list.isEmpty()){
						initTableDetail(list);
					} else {
						initTableDetail(new Vector());
					}
					DgDBTaskExDetail.this.jLabel.setText(DgDBTaskExDetail.this.getTitleName());
			    }
			}
		});
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJPanel3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.setBackground(java.awt.Color.white);
			jPanel1.add(getJLabel(), null);

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
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
			jPanel2.add(getJButton(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
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
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确  定");		
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DgDBTaskExDetail.this.dispose();
				}
			});
		}
		return jButton;
	}

	private JLabel getJLabel() {
		if (jLabel !=null){
			jLabel.setText("任务名称->");
			jLabel.setForeground(new java.awt.Color(251,150,16));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel.setForeground(new java.awt.Color(249,134,1));
		}
		return jLabel;
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
	
	private void initTableDetail(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("导入域名称", "dbFormat.regionName", 150));
						list.add(addColumn("视图名称", "dbFormat.dbView.name", 100));
						list.add(addColumn("字符集转换", "dbFormat.gbflag", 120));
						list.add(addColumn("格式创建者", "dbFormat.creator", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(3).setCellRenderer(
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals("0")) {
							returnValue = "不转换";
						} else if (value.equals("1")) {
							returnValue = "繁转简";
						} else if (value.equals("2")) {
							returnValue = "简转繁";
						}
						return returnValue;
					}
				});
	}
	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setAutoCreateColumnsFromModel(true);
			jTable.setShowHorizontalLines(false);
			jTable.setShowVerticalLines(false);
			jTable.setShowGrid(false);
			jTable.setRowSelectionAllowed(true);
			jTable.setIntercellSpacing(new java.awt.Dimension(0,0));
			jTable.setGridColor(new java.awt.Color(122,138,153));
			jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		}
		return jTable;
    }

    
	/**
	 * @return Returns the titleName.
	 */
	public String getTitleName() {
		return titleName;
	}
	/**
	 * @param titleName The titleName to set.
	 */
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
