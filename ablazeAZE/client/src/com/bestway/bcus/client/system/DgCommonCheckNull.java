package com.bestway.bcus.client.system;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.CommonCheckNull;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgCommonCheckNull extends JDialogBase {

	private JPanel jContentPane = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private ToolsAction toolsAction = null;
	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;
	private JScrollPane jScrollPane2 = null;
	private JTable jTable2 = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private SystemAction systemAction = null;
	private JTableListModel tableModelItem = null;
	private JButton jButton2 = null;
	
	/**
	 * This is the default constructor
	 */
	public DgCommonCheckNull() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
		       "toolsAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		    .getBean("systemAction");
		initialize();
		dataDictMap = this.toolsAction.getTableColumnMap();
		this.initTbTable();
		List ls = systemAction.findCommonCheckNull(new Request(CommonVars.getCurrUser()));
		initTable(ls);
	}

	
	/**
	 * 初始化主表
	 * 
	 */
	private void initTbTable() {
		List<TempNodeItem> list = new ArrayList<TempNodeItem>();
		Iterator itKey = dataDictMap.keySet().iterator(); 
		list.addAll(dataDictMap.keySet());
		Collections.sort(list);
		List<TempNodeItem> listafter = new ArrayList<TempNodeItem>();
        for (int i=0;i<list.size();i++){
        	TempNodeItem item = (TempNodeItem) list.get(i);
        	if (!"CustomsDeclaration".equals(getClassName(item.getClassName())) 
        			&& !"CustomsDeclarationCommInfo".equals(getClassName(item.getClassName()))){
        		continue;
        	}
        	listafter.add(item);
        }
		
		JTableListModel tableModel = new JTableListModel(jTable, listafter,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						//list.add(addColumn("表名", "name", 150));
						list.add(addColumn("类名", "className", 80));
						list.add(addColumn("备注", "cnName", 200));
						//list.add(addColumn("详细类名", "className", 250));
						return list;
					}
				});
		/*jTable.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							String entityName = value.toString();
							int lastIndex = entityName.lastIndexOf(".");
							if (lastIndex > -1) {
								super.setText(entityName
										.substring(lastIndex + 1));
							}
						}
						return this;
					}
				});*/
		jTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							super.setText(getString(value.toString()));
						}
						return this;
					}

					protected String getString(String value) {
						String[] strs = value.split("\n");
						StringBuffer strBuffer = new StringBuffer();
						int length = strs.length;
						for (int i = 0; i < length; i++) {
							if (i <= 1) {
								continue;
							}
							if (i == strs.length - 1) {
								strBuffer.append(strs[i]);
							} else {
								strBuffer.append(strs[i] + "\n");
							}
						}
						return strBuffer.toString().substring(5);
					}
				});
	}

	/**
	 * 初始化字段表
	 * 
	 */
	private void initTbField(TempNodeItem key) {
		List<TempNodeItem> list = dataDictMap.get(key);
		if (list == null) {
			list = new ArrayList<TempNodeItem>();
		}
		JTableListModel tableModel = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						//list.add(addColumn("字段名", "name", 150));
						list.add(addColumn("类字段", "cnName", 80));
						//
						list.add(addColumn("备注", "cnName", 200));
						//list.add(addColumn("字段类型", "className", 150));
						return list;
					}
				});
		jTable1.getColumnModel().getColumn(1).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							super.setText(getString(value.toString()));
						}
						return this;
					}

					protected String getString(String value) {
						String[] strs = value.split("\n");
						if (strs.length > 0) {
							return strs[0].toString().substring(4);
						} else {
							return "";
						}
					}
				});

		jTable1.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							super.setText(getString(value.toString()));
						}
						return this;
					}

					protected String getString(String value) {
						String[] strs = value.split("\n");
						StringBuffer strBuffer = new StringBuffer();
						int length = strs.length;
						for (int i = 0; i < length; i++) {
							if (i <= 2) {
								continue;
							}
							if (i == strs.length - 1) {
								strBuffer.append(strs[i]);
							} else {
								strBuffer.append(strs[i] + "\n");
							}
						}
						return strBuffer.toString().substring(5);
					}
				});
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(768, 449);
		this.setContentPane(getJContentPane());
		this.setTitle("自定义检查保存选项");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJScrollPane2(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(356,188,61,65));
			jButton.setText("添加");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel = (JTableListModel) jTable.getModel();
					if (tableModel == null || tableModel.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgCommonCheckNull.this,"请选择要添加的类名称！","提示",2);
						return;
					}
					JTableListModel tableModel1 = (JTableListModel) jTable1.getModel();
					if (tableModel1 == null || tableModel1.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgCommonCheckNull.this,"请选择要添加的类字段！","提示",2);
						return;
					}
					
					TempNodeItem classItem = (TempNodeItem) tableModel.getCurrentRow();
					System.out.println(getClassName(classItem.getClassName()) +"        "+getString(classItem.getCnName()));
					
					//TempNodeItem fieldItem = (TempNodeItem) tableModel1.getCurrentRow();
					
					
					List fieldLs = tableModel1.getCurrentRows();
					for (int i=0;i<fieldLs.size();i++){
						TempNodeItem fieldItem = (TempNodeItem) fieldLs.get(i);
						
						System.out.println(getFieldName(fieldItem.getCnName())+"       "+getFieldString(fieldItem.getCnName()));
						
						CommonCheckNull obj = new CommonCheckNull();
						obj.setClassName(classItem.getClassName());
						obj.setClassMemo(getString(classItem.getCnName()));
						obj.setFieldName(getFieldName(fieldItem.getCnName()));
						obj.setFieldMemo(getFieldString(fieldItem.getCnName()));
						obj.setFieldType(getType(fieldItem.getClassName()));
						obj = systemAction.saveCommonCheckNull(new Request(CommonVars.getCurrUser()), obj);
						tableModelItem.addRow(obj);
					}
					
					
				}
			});
		}
		return jButton;
	}

	private String getType(String className){
		if (className == null || className.equals("")){
			return "String";
		}
		if (className.indexOf("varchar")>-1){
			return "String";
		}
		if (className.indexOf("double")>-1){
			return "Double";
		}
		if (className.indexOf("int")>-1){
			return "Integer";
		}
		if (className.indexOf("datetime")>-1){
			return "Date";
		}
		if (className.indexOf("tinyint")>-1){
			return "Boolean";
		}
		return "String";
	}
	//得到类名
	private String getClassName(String value){
		if (value != null) {
			String entityName = value.toString();
			int lastIndex = entityName.lastIndexOf(".");
			if (lastIndex > -1) {
				return (entityName.substring(lastIndex + 1));
			}
		}
		return value;
	}
	//得到类备注
	private String getString(String value) {
		String[] strs = value.split("\n");
		StringBuffer strBuffer = new StringBuffer();
		int length = strs.length;
		for (int i = 0; i < length; i++) {
			if (i <= 1) {
				continue;
			}
			if (i == strs.length - 1) {
				strBuffer.append(strs[i]);
			} else {
				strBuffer.append(strs[i] + "\n");
			}
		}
		return strBuffer.toString().substring(5);
	}
	//得到字段名
	private String getFieldName(String value) {
		String[] strs = value.split("\n");
		if (strs.length > 0) {
			return strs[0].toString().substring(4);
		} else {
			return "";
		}
	}
	//得到字段备注
	protected String getFieldString(String value) {
		String[] strs = value.split("\n");
		StringBuffer strBuffer = new StringBuffer();
		int length = strs.length;
		for (int i = 0; i < length; i++) {
			if (i <= 2) {
				continue;
			}
			if (i == strs.length - 1) {
				strBuffer.append(strs[i]);
			} else {
				strBuffer.append(strs[i] + "\n");
			}
		}
		return strBuffer.toString().substring(5);
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(356,263,60,65));
			jButton1.setText("移除");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelItem.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgCommonCheckNull.this,"请选择要移除的选项！","提示",2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgCommonCheckNull.this,
							"您确定要移除吗?","提示", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					
					List ls = tableModelItem.getCurrentRows();
					systemAction.deleteLs(new Request(CommonVars.getCurrUser()),ls);
					tableModelItem.deleteRows(ls);
				}
			});
		}
		return jButton1;
	}


	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new java.awt.Rectangle(423,1,336,412));
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}


	/**
	 * This method initializes jTable2	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
		}
		return jTable2;
	}


	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(3,166,344,247));
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}


	/**
	 * This method initializes jTable1	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}


	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(1,0,344,163));
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
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) jTable
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								TempNodeItem key = (TempNodeItem) tableModel
										.getCurrentRow();
								initTbField(key);
							} catch (Exception cx) {
								cx.printStackTrace();
							}
						}
					});
		}
		return jTable;
	}

	
	
	private void initTable(List list) {
		tableModelItem = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类名", "className", 100));
						list.add(addColumn("类备注", "classMemo", 100));
						list.add(addColumn("类字段名", "fieldName", 100));							
						list.add(addColumn("类字段备注", "fieldMemo", 100));
						list.add(addColumn("类字段类型", "fieldType", 100));
						return list;
					}
		});
	}


	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(356,341,60,64));
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCommonCheckNull.this.dispose();
				}
			});
		}
		return jButton2;
	}
		
}  //  @jve:decl-index=0:visual-constraint="10,10"
