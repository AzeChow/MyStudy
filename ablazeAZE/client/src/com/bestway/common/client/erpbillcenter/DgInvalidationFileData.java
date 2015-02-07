package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.ui.winuicontrol.JDialogBase;
@SuppressWarnings("unchecked")
public class DgInvalidationFileData extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel1 = null;

	private JButton btnClose = null;

	private JPanel jPanel2 = null;

	private List dataSource = null;  //  @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JButton btnRemoveErrorImport = null;

	
	private Integer result = Integer.valueOf(0);  //  @jve:decl-index=0:
	//	private CommonBaseCodeAction commonBaseCodeAction = null;

	/**
	 * This method initializes
	 *  
	 */
	public DgInvalidationFileData() {
		super();
		initialize();
		jTable = new GroupableHeaderTable();
		jScrollPane.setViewportView(jTable);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("无效的数据记录");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(1000, 600);	
	}

	
	/**
	 * 
	 * */
	@Override
	public void setVisible(boolean isFlag){
		if(isFlag){
			if (dataSource != null) {
				initTable(dataSource);
			}
		}
		super.setVisible(isFlag);
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	
	class ExampleFileFilter extends FileFilter
	{
		private List list = new ArrayList();
		ExampleFileFilter(String suffix){
			this.addExtension(suffix);
		}
		
		@Override
		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if(f.isDirectory()== true){
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		@Override
		public String getDescription() {
			String description ="*.";
			for(int i=0;i<list.size() ;i++){
				description += list.get(i).toString()+" & *.";
			}
			return description.substring(0,description.length()-5);
		}
		
		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1)
						.toLowerCase();
			return suffix;
		}
		
		private boolean isAcceptSuffix(String suffix){
			boolean isAccept = false;
			for(int i=0;i<list.size();i++){
				if(suffix.equals(list.get(i).toString())){
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}
		
		public void addExtension(String extensionName){
			if(extensionName.equals("")){
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}
		
		
	}
	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			jPanel2 = new JPanel();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 7;
			gridBagConstraints1.ipady = -7;
			gridBagConstraints1.insets = new java.awt.Insets(3, 4, 4, 65);
			jPanel2.add(getBtnRemoveErrorImport(), null);
			jPanel2.add(getBtnClose(), gridBagConstraints1);
		}
		return jPanel2;
	}

	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(List dataSource) {
		this.dataSource = dataSource;
	}

	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable,
				dataSource, new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("错误行号", "invalidationRow", 50));
						list.add(addColumn("错误原因", "errorReason", 200));
						list.add(addColumn("料号", "materiel.ptNo",
								"a.materiel.ptNo", 60));
						list.add(addColumn("商品名称", "materiel.factoryName",
								"a.materiel.factoryName", 80));
						list.add(addColumn("型号规格", "materiel.factorySpec",
								"a.materiel.factorySpec", 80));
						list.add(addColumn("净重", "materiel.ptNetWeight",
								"a.materiel.ptNetWeight", 40));
						list.add(addColumn("参考单价", "materiel.ptPrice",
								"a.materiel.ptPrice", 60));
						list.add(addColumn("单位", "materiel.calUnit.name",
								"a.materiel.calUnit.name", 40));
						list.add(addColumn("单位折算", "unitConvert", 60));
						list.add(addColumn("编码",
								"statCusNameRelationHsn.complex.code",
								"a.statCusNameRelationHsn.complex.code", 60));
						list.add(addColumn("名称",
								"statCusNameRelationHsn.cusName",
								"a.statCusNameRelationHsn.cusName", 80));
						list.add(addColumn("规格",
								"statCusNameRelationHsn.cusSpec",
								"a.statCusNameRelationHsn.cusSpec", 80));
						list.add(addColumn("单位",
								"statCusNameRelationHsn.cusUnit.name",
								"a.statCusNameRelationHsn.cusUnit.name", 40));
						list.add(addColumn("管理类型",
								"statCusNameRelationHsn.projectName",
								"a.statCusNameRelationHsn.projectName", 60));
						return list;
					}
				});;	
		
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						this.setText("第"+(String)value+"行");
						return this;
					}
				});		
	}

	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			if (checkValue(table, row, column)) {
				c.setBackground(Color.BLUE);
				c.setForeground(Color.WHITE);
			} else {
				if (isSelected) {
					c.setForeground(table.getSelectionForeground());
					c.setBackground(table.getSelectionBackground());
				} else {
					c.setForeground(table.getForeground());
					c.setBackground(table.getBackground());
				}
			}
			return c;
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		BalanceInfo data = (BalanceInfo) tableModel
				.getObjectByRow(row);
		int[] cols = data.getInvalidationColNo();
		for (int i = 0; i < cols.length; i++) {
			switch (column) {
			case 1:
				if (cols[i] == 1) {
					return true;
				}
				break;
		}
		}
		return false;
	}

	/**
	 * This method initializes btnRemoveErrorImport	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnRemoveErrorImport() {
		if (btnRemoveErrorImport == null) {
			btnRemoveErrorImport = new JButton();
			btnRemoveErrorImport.setText("删除错误数据导入");
			btnRemoveErrorImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					result = Integer.valueOf(1);
					dispose();
				}
			});
		}
		return btnRemoveErrorImport;
	}

	public Integer getResult() {
		return result;
	}
}


