package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import com.bestway.bcus.cas.entity.BalanceInfo;
import com.bestway.bcus.cas.entity.TempBillInputErrMassageShow;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Rectangle;
import java.awt.Dimension;

public class DgInvalidationBillTxtDate extends JDialogBase {

	private JPanel jContentPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel1 = null;

	private JButton btnClose = null;

	private JButton btnExport = null;

	private List dataSource = null;

	private JTableListModel tableModel = null;

	//	private CommonBaseCodeAction commonBaseCodeAction = null;

	/**
	 * This method initializes
	 *  
	 */
	public DgInvalidationBillTxtDate() {
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
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(85, 35));
			jPanel1.add(getBtnClose(), null);
			jPanel1.add(getBtnExport(), null);
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
			btnClose.setBounds(new Rectangle(409, 5, 92, 23));
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
			btnExport.setBounds(new Rectangle(299, 4, 93, 24));
			btnExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser("./");
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new ExampleFileFilter("txt"));
					int state = fileChooser.showSaveDialog(DgInvalidationBillTxtDate.this);
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
				TempBillInputErrMassageShow data =(TempBillInputErrMassageShow)dataSource.get(i);
				StringBuilder strLine = new StringBuilder("");
				strLine.append("第"+data.getInvalidationRow()+"行"+strTab);
				strLine.append(data.getErrorReason()+strTab);
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
//						list.add(addColumn("料号","ptNo",180));
						list.add(addColumn("错误原因", "errorReason", 750));
						return list;
					}
				});;	
		
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
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
		jTable.getColumnModel().getColumn(2).setCellRenderer(new TableMultiRowRender());
		jTable.setRowHeight(25);
	}

	class ColorTableCellRenderer extends DefaultTableCellRenderer {
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
	}}
