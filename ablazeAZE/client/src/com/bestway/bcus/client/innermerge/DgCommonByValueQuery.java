/*
 * Created on 2004-6-28
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCommonByValueQuery extends JDialogBase
{

	private JPanel			jContentPane	= null;

	private JPanel			jPanel			= null;

	private JComboBox		cbbQueryField	= null;

	private JTextField		tfQueryValue	= null;

	private JPanel			jPanel1			= null;

	private JButton			btnOK			= null;

	private JButton			btnCancel		= null;

	private  JTable			jTable			= null;

	private JScrollPane		jScrollPane		= null;

	private boolean			ok				= false;

	private Object			returnValue;

	private List			returnList;

	private JTableListModel	tableModel		= null;

	private List			dataSource		= null;

	private static boolean	singleResult	= true;

	private Object			initObject		= null;

	private JToolBar		jToolBar		= null;

	private static List		tableColumns	= null;
	private static Hashtable hsSelect = new Hashtable();



	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private ButtonGroup group = new ButtonGroup();
	/**
	 * @return Returns the tableColumns.
	 */
	public static List getTableColumns()
	{
		return tableColumns;
	}



	/**
	 * @param tableColumns
	 *            The tableColumns to set.
	 */
	public static void setTableColumns(List columns)
	{
		tableColumns = columns;
	}



	/**
	 * @return Returns the singleResult.
	 */
	public boolean isSingleResult()
	{
		return singleResult;
	}



	/**
	 * @param singleResult
	 *            The singleResult to set.
	 */
	public static void setSingleResult(boolean b)
	{
		singleResult = b;
	}



	/**
	 * @param singleResult
	 *            The singleResult to set.
	 */
	public boolean getSingleResult()
	{
		return singleResult;
	}



	/**
	 * @return Returns the dataSource.
	 */
	public List getDataSource()
	{
		return dataSource;
	}



	/**
	 * @param dataSource
	 *            The dataSource to set.
	 */
	public void setDataSource(List dataSource)
	{
		this.dataSource = dataSource;
	}



	public void setSelectedRow(Object obj)
	{
		this.initObject = obj;
	}



	/**
	 * @return Returns the returnList.
	 */
	public List getReturnList()
	{
		return returnList;
	}



	/**
	 * @param returnList
	 *            The returnList to set.
	 */
	private void setReturnList(List returnList)
	{
		this.returnList = returnList;
	}



	/**
	 * @return Returns the returnValue.
	 */
	public Object getReturnValue()
	{
		return returnValue;
	}



	/**
	 * @param returnValue
	 *            The returnValue to set.
	 */
	private void setReturnValue(Object returnValue)
	{
		this.returnValue = returnValue;
	}



	/**
	 * This method initializes
	 *  
	 */
	public DgCommonByValueQuery()
	{
		super();
		initialize();
		setAlwaysOnTop(true);
		setSingleResult(true);
	}



	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setTitle("查询");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(465, 375);
		group.add(jRadioButton);
		group.add(jRadioButton1);

	}



	/**
	 * 
	 * This method initializes jContentPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}



	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getCbbQueryField(), null);
			jPanel.add(getTfQueryValue(), null);
		}
		return jPanel;
	}



	class ComboBoxItem
	{
		private String	code;

		private String	name;



		public ComboBoxItem(String code, String name)
		{
			this.code = code;
			this.name = name;
		}



		public String getCode()
		{
			return code;
		}



		public void setCode(String code)
		{
			this.code = code;
		}



		public String getName()
		{
			return name;
		}



		public void setName(String name)
		{
			this.name = name;
		}



		public String toString()
		{
			return name;
		}
	}



	/**
	 * 
	 * This method initializes cbbQueryField
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 *  
	 */
	private JComboBox getCbbQueryField()
	{
		if (cbbQueryField == null)
		{
			cbbQueryField = new JComboBox();
			cbbQueryField.setBounds(4, 3, 127, 23);
		}
		return cbbQueryField;
	}



	/**
	 * 
	 * This method initializes tfQueryValue
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 *  
	 */
	private JTextField getTfQueryValue()
	{
		if (tfQueryValue == null)
		{
			tfQueryValue = new JTextField();
			tfQueryValue.setBounds(135, 4, 205, 23);
			tfQueryValue.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					String sKey = e.getKeyText(e.getKeyCode());
			        if (sKey.equalsIgnoreCase("Enter")) {			          
					   getResult();
			        }
				}
			});			
			tfQueryValue.addCaretListener(new javax.swing.event.CaretListener() { 
				public void caretUpdate(javax.swing.event.CaretEvent e) {    
					int col = getCbbQueryField().getSelectedIndex() + 1;
					boolean isEqual = true;
					if (jRadioButton.isSelected()){
						isEqual = true;
					} else {
						isEqual = false;
					}
					String value = getTfQueryValue().getText();
					if (value == null || value.equals("")){
						return;
					}	
					/*if (col == 1){
						value = delRe(value);
					}*/
					/*((JTableListModel) getJTable().getModel()).filter(col,
							value,isEqual,0);*/
					
					
				}
			});
		}
		return tfQueryValue;
	}

	//出掉数组重复值
	private String delRe(String value){
		String[] x = value.split(",");
		List list = new Vector();
		for (int i=0;i<x.length;i++){
		   if (!isexist(list,x[i])){
			  list.add(x[i]);
		   }
		}
		
	    String xx = "";
	    for (int j = 0; j < list.size(); j++) {
			String name = (String) list.get(j);
	    	if (j==0){
	    		xx = name;
	    		continue;
	    	}
	    	xx = xx+","+name;
	    }
	    return xx;
	}
    private boolean isexist(List list, String str){
    	for (int i=0;i<list.size();i++){
    		if (list.get(i)==null){
    			continue;
    		}
    		String x=list.get(i).toString();
    		if (x.equals(str)){
    			return true;
    		}
    	}
    	return false;
    }

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPanel1()
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel();
			jPanel1.add(getBtnOK(), null);
			jPanel1.add(getBtnCancel(), null);
		}
		return jPanel1;
	}



	/**
	 * 
	 * This method initializes btnOK
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnOK()
	{
		if (btnOK == null)
		{
			btnOK = new JButton();
			btnOK.setText("确定");
			btnOK.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{

					getResult();
				}

			});

		}
		return btnOK;
	}



	/**
	 *  
	 */
	private void getResult()
	{
		if (this.getSingleResult())
		{
			setReturnValue(((JTableListModel) getJTable().getModel())
					.getCurrentRow());
		}
		else
		{
			if (cbbQueryField.getSelectedItem()!= null && cbbQueryField.getSelectedIndex()==0 && !getTfQueryValue().getText().equals("")){
				setReturnList(((JTableListModel) getJTable().getModel())
						   .getCurrentRowsOrder());
			}/* else if (hsSelect.size()>0){
				setReturnList(((JTableListModel) getJTable().getModel())
						   .getCurrentRowsByRowSelect(hsSelect));
			}*/ else {
				setReturnList(((JTableListModel) getJTable().getModel())
						   .getCurrentRowsSele());
			}
		}
		this.setOk(true);
		this.dispose();
	}


 
	/**
	 * 
	 * This method initializes btnCancel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnCancel()
	{
		if (btnCancel == null)
		{
			btnCancel = new JButton();
			btnCancel.setText("取消");
			btnCancel.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					DgCommonByValueQuery.this.setOk(false);
					DgCommonByValueQuery.this.dispose();
				}
			});

		}
		return btnCancel;
	}



	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 *  
	 */
	public JTable getJTable()
	{
		if (jTable == null)
		{
			jTable = new JTable();			
			jTable.addMouseListener(new java.awt.event.MouseAdapter(){

				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					if (e.getClickCount() == 2)
					{
						if (getSingleResult())
						{
							setReturnValue(((JTableListModel) getJTable().getModel())
									.getCurrentRow());
						} else {
						    setReturnList(((JTableListModel) getJTable().getModel())
							    	.getCurrentRowsSele());
						}
						setOk(true);
						dispose();
					}
				}
			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
			                if (tableModel == null)
			                	return;
			                if (tableModel.getCurrentRow() == null)
			                	return;
			               /* int x = jTable.getSelectedRow();
							if (isExist(x) && hsSelect.get(x) ==  null ){
								hsSelect.put(x,x);
								System.out.println("--put:"+x);
							} else if (!isExist(x) && hsSelect.get(x) !=  null){
								hsSelect.remove(x);
								System.out.println("--remove:"+x);
							}*/
						}
					});

		}
		return jTable;
	}
	public boolean isExist(int name){
    	int[] s = jTable.getSelectedColumns();
    	for (int i=0;i<s.length;i++){
    		if (s[i]== name){
    			return true;
    		}
    	}
    	return false;
    }


	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 *  
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());				
		}
		return jScrollPane;
	}



	public boolean isOk()
	{
		return ok;
	}



	public void setOk(boolean ok)
	{
		this.ok = ok;
	}



	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 *  
	 */
	private JToolBar getJToolBar()
	{
		if (jToolBar == null)
		{
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getJRadioButton1());
		}
		return jToolBar;
	}



	public void setVisible(boolean b)
	{
		if (b)
		{
			tableModel = new JTableListModel(jTable, dataSource,
					new JTableListModelAdapter()
					{
						public List InitColumns()
						{
							if (DgCommonByValueQuery.getTableColumns() == null)
							{
								List list = new Vector();
								list.add(addColumn("编号", "code", 100));
								list.add(addColumn("名称", "name", 150));								
								return list;
							}
							else
							{
								List list = DgCommonByValueQuery.getTableColumns()
										.subList(
												0,
												DgCommonByValueQuery.getTableColumns()
														.size());
								DgCommonByValueQuery.tableColumns = null;
								return list;
							}
							
						}
					});
			if (initObject != null)
			{
				tableModel.setTableSelectedRow(initObject);
			}
			if (DgCommonByValueQuery.this.getSingleResult())
			{
				this.jTable
						.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			}
			else
			{
				this.jTable
						.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			}
			int columnCount = tableModel.getColumnCount();
			getCbbQueryField().removeAllItems();
			for (int i = 1; i < columnCount; i++)
			{
				getCbbQueryField().addItem(tableModel.getColumnName(i));
			}
			getCbbQueryField().setSelectedIndex(0);
		}
//		setTableColumnWidth();
		super.setVisible(b);		
	}



	/**
	 * 如果这个列module的总体长度 > 了表的长度那么不做任何动作 反之设置这个module的长度等于这个表的宽度
	 */
	private void setTableColumnWidth()
	{
		if (this.jScrollPane.getWidth() > jTable.getColumnModel()
				.getTotalColumnWidth())
		{
			int width = this.jScrollPane.getWidth()
					- jTable.getColumnModel().getColumn(0).getWidth() - 2;
			int count = this.jTable.getColumnCount() - 1;
			int eachColumnWidth = width / count;
			for (int i = 1; i <= count; i++)
			{
				jTable.getColumnModel().getColumn(i).setPreferredWidth(eachColumnWidth);
			}
			jScrollPane.setViewportView(getJTable());
		}
	}
	/**
	 * This method initializes jRadioButton	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setSelected(false);
			jRadioButton.setText("精确");
		}
		return jRadioButton;
	}
	/**
	 * This method initializes jRadioButton1	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */    
	private JRadioButton getJRadioButton1() {
		if (jRadioButton1 == null) {
			jRadioButton1 = new JRadioButton();
			jRadioButton1.setSelected(true);
			jRadioButton1.setText("模糊");
		}
		return jRadioButton1;
	}

  } //  @jve:decl-index=0:visual-constraint="10,10"

