package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.CheckBalance;
import com.bestway.bcus.client.cas.otherreport.DgBalanceInfoInvalidationFileData.ColorTableCellRenderer;
import com.bestway.bcus.client.cas.otherreport.DgBalanceInfoInvalidationFileData.ExampleFileFilter;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCheckBalanceInvalidationFileData extends JDialogBase {

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel1 = null;

	private JButton btnClose = null;

	private JButton btnExport = null;

	private JPanel jPanel2 = null;

	private List dataSource = null;

	private JTableListModel tableModel = null;

	//	private CommonBaseCodeAction commonBaseCodeAction = null;

	/**
	 * This method initializes
	 *  
	 */
	public DgCheckBalanceInvalidationFileData() {
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
		this.setSize(762, 404);	
	}

	
	/**
	 * 
	 * */
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

	/**
	 * This method initializes btnExport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton();
			btnExport.setText("导出");
			btnExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser("./");
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new ExampleFileFilter("txt"));
					int state = fileChooser.showSaveDialog(DgCheckBalanceInvalidationFileData.this);
					if(state==JFileChooser.APPROVE_OPTION){
						File f = fileChooser.getSelectedFile();
						String description = fileChooser.getFileFilter().getDescription();
						String suffix = description.substring(description.indexOf("."));
						String filePath = f.getPath()+suffix;
						saveListToFile(dataSource,filePath);
					}
				}
			});		
		}
		return btnExport;
	}	
	

	
	
	private void saveListToFile(List dataSource,String filePath){
		String strTab = String.valueOf((char) 9);
		BufferedWriter bufferedWriter = null;
		if(dataSource == null){
			return;
		}		
		try
		{
			File file = new File(filePath);
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			for(int i=0;i<dataSource.size();i++){
				CheckBalance data =(CheckBalance)dataSource.get(i);
				StringBuilder strLine = new StringBuilder("");
				strLine.append(data.getCheckDate().toString()+strTab);
				strLine.append(data.getPtNo().toString()+strTab);
				strLine.append(data.getWareSet().getName().toString()+strTab);
				strLine.append(data.getCheckAmount().toString()+strTab);
				bufferedWriter.write(strLine.toString());
				bufferedWriter.newLine();				
			}
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		finally{
			try
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
				catch (IOException e2)
				{
					e2.printStackTrace();
				}								
		}
	}
	
	class ExampleFileFilter extends FileFilter
	{
		private List list = new ArrayList();
		ExampleFileFilter(String suffix){
			this.addExtension(suffix);
		}
		
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
			java.awt.GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			java.awt.GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 7;
			gridBagConstraints1.ipady = -7;
			gridBagConstraints1.insets = new java.awt.Insets(3, 4, 4, 65);
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 7;
			gridBagConstraints2.ipady = -7;
			gridBagConstraints2.insets = new java.awt.Insets(3, 341, 4, 4);
			jPanel2.add(getBtnClose(), gridBagConstraints1);
			jPanel2.add(getBtnExport(), gridBagConstraints2);
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
		tableModel = new JTableListModel((GroupableHeaderTable) jTable,
				dataSource, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("盘点时间", "checkDate", 150));
						list.add(addColumn("工厂料号", "ptNo", 100));
						list.add(addColumn("仓库名称", "wareSet.name", 120));
						list.add(addColumn("库存数量", "checkAmount", 120));
						list.add(addColumn("错误原因", "errorReason", 150));
						return list;
					}
				});;
	}

	class ColorTableCellRenderer extends DefaultTableCellRenderer {
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
		CheckBalance data = (CheckBalance) tableModel
				.getObjectByRow(row);
		int[] cols = data.getInvalidationColNo();
		for (int i = 0; i < cols.length; i++) {
			switch (column) {
			case 1:
				if (cols[i] == 2) {
					return true;
				}
				break;
		}
		}
		return false;
	}
	
}
