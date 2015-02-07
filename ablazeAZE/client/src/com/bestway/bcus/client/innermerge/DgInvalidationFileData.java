/*
 * Created on 2004-7-20
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeFileData;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableHeaderTable;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author bsway
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgInvalidationFileData extends JDialogBase {

	private List dataSource = null;

	private JTableListModel tableModel = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;
	private CommonBaseCodeAction commonBaseCodeAction = null;
	
	private List list = null;

	//	private CommonBaseCodeAction commonBaseCodeAction = null;

	/**
	 * This method initializes
	 *  
	 */
	public DgInvalidationFileData() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
		         .getApplicationContext().getBean("commonBaseCodeAction");
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
		this.setTitle("请确认数据记录");
		this.setContentPane(getJPanel());
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
				InnerMergeFileData data =(InnerMergeFileData)dataSource.get(i);
				StringBuilder strLine = new StringBuilder("");		
				strLine.append(data.getSerialNo().trim()+strTab);
				strLine.append((data.getBeforeMaterielCode()==null?"":data.getBeforeMaterielCode())+strTab);
				strLine.append((data.getBeforeTenComplexCode()==null?"":data.getBeforeTenComplexCode())+strTab);
				strLine.append((data.getBeforeMaterielName()==null?"":data.getBeforeMaterielName()));
				strLine.append((data.getBeforeMaterielSpec()==null?"":"+"+data.getBeforeMaterielSpec())+strTab);				
				strLine.append((data.getBeforeLegalUnit()==null?"":data.getBeforeLegalUnit())+strTab);
				strLine.append((data.getBeforeEnterpriseUnit()==null?"":data.getBeforeEnterpriseUnit())+strTab);
				strLine.append((data.getAfterTenMemoNo()==null?"":data.getAfterTenMemoNo())+strTab);
				strLine.append((data.getAfterTenComplexCode()==null?"":data.getAfterTenComplexCode())+strTab);
				strLine.append((data.getAfterComplexlName()==null?"":data.getAfterComplexlName()));
				strLine.append((data.getAfterComplexSpec()==null?"":"/"+data.getAfterComplexSpec())+strTab);
				strLine.append((data.getAfterLegalUnit()==null?"":data.getAfterLegalUnit())+strTab);
				strLine.append((data.getAfterMemoUnit()==null?"":data.getAfterMemoUnit())+strTab);
				strLine.append((data.getFourNo()==null?"":data.getFourNo())+strTab);
				strLine.append((data.getFourComplexName()==null?"":data.getFourComplexName())+strTab);
				strLine.append((data.getFourComplexCode()==null?"":data.getFourComplexCode()));
				strLine.append((data.getUnitWeight()==null?"":data.getUnitWeight())+strTab);
				strLine.append((data.getUnitConvert()==null?"":data.getUnitConvert()));
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
						list.add(addColumn("序号", "serialNo", 50,Integer.class));
						list.add(addColumn("料号", "beforeMaterielCode", 50));
						list.add(addColumn("10位商品编码", "beforeTenComplexCode", 100));
						list.add(addColumn("商品名称.规格,型号", "hsBeforeMaterielNameSpec",
								120));//+beforeMaterielSpec
						list.add(addColumn("法定", "beforeLegalUnit", 25));
						list.add(addColumn("企业", "beforeEnterpriseUnit", 25));
						list.add(addColumn("备案序号", "afterTenMemoNo", 50,
								Integer.class));
						list.add(addColumn("10位商品编码", "afterTenComplexCode",
								100));
						list.add(addColumn("商品名称.规格,型号", "hsAfterComplexNameSpec",
								120));//+afterMaterielSpec
						list.add(addColumn("法定", "afterLegalUnit", 25));
						list.add(addColumn("备案", "afterMemoUnit", 25));
						list.add(addColumn("4位编码序号", "fourNo", 70,
								Integer.class));
						list.add(addColumn("4位商品名称", "fourComplexName", 70));
						list.add(addColumn("4位商品编码", "fourComplexCode", 70));
						
						list.add(addColumn("单重", "unitWeight", 80));
						list.add(addColumn("单位折算系数", "unitConvert", 80));
						
						list.add(addColumn("错误原因", "errorReason", 300));
						return list;
					}
				});

		TableColumnModel cm = jTable.getColumnModel();

		cm.removeColumn(cm.getColumn(0));

		ColumnGroup gBeforeUnit = new ColumnGroup("计量单位");
		gBeforeUnit.add(cm.getColumn(4));
		gBeforeUnit.add(cm.getColumn(5));

		ColumnGroup gAfterUnit = new ColumnGroup("计量单位");
		gAfterUnit.add(cm.getColumn(9));
		gAfterUnit.add(cm.getColumn(10));

		ColumnGroup gBefore = new ColumnGroup("归并前");
		gBefore.add(cm.getColumn(1));
		gBefore.add(cm.getColumn(2));
		gBefore.add(cm.getColumn(3));
		gBefore.add(gBeforeUnit);

		ColumnGroup gAfter = new ColumnGroup("归并后");
		gAfter.add(cm.getColumn(6));
		gAfter.add(cm.getColumn(7));
		gAfter.add(cm.getColumn(8));
		gAfter.add(gAfterUnit);
		
		ColumnGroup hz = new ColumnGroup("扩展导入");
		hz.add(cm.getColumn(14));
		hz.add(cm.getColumn(15));		
		
		GroupableTableHeader header = (GroupableTableHeader) jTable
				.getTableHeader();
		header.addColumnGroup(gBefore);
		header.addColumnGroup(gAfter);
		header.addColumnGroup(hz);
		jTable.getColumnModel().getColumn(16).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						
						return this;
					}
				
				});
		/*jTable.getColumnModel().getColumn(2).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(9).setCellRenderer(
				new ColorTableCellRenderer());
		jTable.getColumnModel().getColumn(10).setCellRenderer(
				new ColorTableCellRenderer());*/
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
			jPanel.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("导出");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser("./");
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new ExampleFileFilter("txt"));
					int state = fileChooser.showSaveDialog(DgInvalidationFileData.this);
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
			jButton1.setText("导入数据");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton1;
	}
	
	class ImportFileDataRunnable extends Thread {
		public void run() {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(DgInvalidationFileData.this);
				CommonProgress.setMessage("系统正在保存导入文件资料，请稍后...");
				for (int i=0;i<dataSource.size();i++){
					InnerMergeFileData fileData = (InnerMergeFileData) dataSource.get(i);
					if (fileData.getErrinfo() != null && !fileData.getErrinfo().equals("")){
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgInvalidationFileData.this,"导入文件有错误，不可导入! 请看最右边的错误原因","提示",2);						
						return;
					}
				}
				
				commonBaseCodeAction.importDataFromTxtFile(new Request(
						CommonVars.getCurrUser()), list);
				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;
				JOptionPane.showMessageDialog(DgInvalidationFileData.this,
						"导入数据成功！导入数据记录 " + list.size() + " 条,共用时 " + totalTime
								+ " 毫秒", "提示", JOptionPane.INFORMATION_MESSAGE);

				DgInvalidationFileData.this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgInvalidationFileData.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("关闭");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
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

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	/*class ColorTableCellRenderer extends DefaultTableCellRenderer {
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
		InnerMergeFileData data = (InnerMergeFileData) tableModel
				.getObjectByRow(row);
		int[] cols = data.getInvalidationColNo();
		for (int i = 0; i < cols.length; i++) {
			switch (column) {
			case 2:
				if (cols[i] == InnerMergeFileData.BEFORE_TEN_COMPLEX_CODE) {
					return true;
				}
				break;
			case 4:
				if (cols[i] == InnerMergeFileData.BEFORE_LEGAL_UNIT) {
					return true;
				}
				break;
			case 5:
				if (cols[i] == InnerMergeFileData.BEFORE_ENTERPRISE_UNIT) {
					return true;
				}
				break;
			case 7:
				if (cols[i] == InnerMergeFileData.AFTER_TEN_COMPLEX_CODE) {
					return true;
				}
				break;
			case 9:
				if (cols[i] == InnerMergeFileData.AFTER_LEGAL_UNIT) {
					return true;
				}
				break;
			case 10:
				if (cols[i] == InnerMergeFileData.AFTER_MEMO_UNIT) {
					return true;
				}
				break;
			}
		}
		return false;
	}*/

} //  @jve:decl-index=0:visual-constraint="10,10"
