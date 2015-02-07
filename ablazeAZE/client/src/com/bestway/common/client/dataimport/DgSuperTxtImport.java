package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.SuperClassList;
import com.bestway.bcus.dataimport.entity.SuperFieldList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import org.apache.commons.beanutils.PropertyUtils;

public class DgSuperTxtImport extends JDialogBase {

	private JPanel jContentPane = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JButton jButton1 = null;
	private JScrollPane jScrollPane2 = null;
	private JTable jTable2 = null;
	private JScrollPane jScrollPane3 = null;
	private JTable jTable3 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JButton jButton5 = null;
	private JButton jButton6 = null;
	private ToolsAction toolsAction = null;
	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;
	private JButton jButton7 = null;
	private List fieldList = null;
	private SuperClassList superClass = null;
	private JTableListModel tableModelItem = null;
	private JTableListModel tableModelTask = null;
	private JTableListModel tableModelData = null;
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;
	private JTextPane jTextPane = null;
	private JLabel jLabel5 = null;
	private ButtonGroup					group				= new ButtonGroup();
	private DataImportAction dataImportAction = null;
	private JButton jButton8 = null;
	private JButton jButton9 = null;
	private boolean isAdd = true;
	private Hashtable hs = null;
	private String className;
	private JButton jButton10 = null;
	private JButton jButton11 = null;
	private File txtFile = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JToolBar jToolBar1 = null;
	private JButton jButton12 = null;
	private JButton jButton13 = null;
	private JScrollPane jScrollPane4 = null;
	private JTextArea jTextArea = null;
	private JToolBar jToolBar2 = null;
	private JButton jButton14 = null;
	private JScrollPane jScrollPane5 = null;
	private JTable jTable4 = null;
	private String error = "";
	List objList =  null;
	/**
	 * This is the default constructor
	 */
	public DgSuperTxtImport() {
		super();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
	       "toolsAction");
		dataImportAction = (DataImportAction) CommonVars.getApplicationContext().getBean("dataImportAction");
		dataDictMap = this.toolsAction.getTableColumnMap();
		initialize();
		setPaneState(0);
		List ls = dataImportAction.findSuperClassList(new Request(CommonVars.getCurrUser()));
		initTaskTable(ls);
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(671, 427);
		this.setContentPane(getJContentPane());
		this.setTitle("文本导入接口");
		initTbTable();
		group.add(jRadioButton);
		group.add(jRadioButton1);
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
	
	
	/**
	 * 初始化主表
	 * 
	 */
	private void initTbTable() {
		Vector <String>d = new Vector<String>();
  	    d.add("EnterpriseMaterial");
  	    d.add("Materiel");
  	    d.add("CalUnit");
  	    
  	    
  	    hs = new Hashtable();
		List<TempNodeItem> list = new ArrayList<TempNodeItem>();
		Iterator itKey = dataDictMap.keySet().iterator(); 
		list.addAll(dataDictMap.keySet());
		Collections.sort(list);
		List<TempNodeItem> listafter = new ArrayList<TempNodeItem>();
        for (int i=0;i<list.size();i++){
        	TempNodeItem item = (TempNodeItem) list.get(i);
        	if (d.contains(getClassName(item.getClassName()))) {
        		listafter.add(item);
        		hs.put(item.getClassName(),item);
        	}        	
        }
		
		JTableListModel tableModel = new JTableListModel(jTable1, listafter,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("类名", "className", 80));
						list.add(addColumn("备注", "cnName", 200));
						return list;
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
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setSize(new java.awt.Dimension(738,462));
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
			jTabbedPane.addTab("1", null, getJPanel(), null);
			jTabbedPane.addTab("2", null, getJPanel1(), null);
			jTabbedPane.addTab("3", null, getJPanel2(), null);
			jTabbedPane.addTab("4", null, getJPanel3(), null);
			jTabbedPane.addTab("5", null, getJPanel4(), null);
			jTabbedPane.addTab("6", null, getJPanel5(), null);
			
		}
		return jTabbedPane;
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
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
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(13,14,260,26));
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("请选择将要设置的类(必选项)");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(307,121,108,22));
			jLabel1.setText("格式备注(可不填)");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(305,47,103,23));
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("格式名称(必填项)");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(getJScrollPane1(), null);
			jPanel1.add(getJButton1(), null);
			jPanel1.add(getJButton6(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getJTextField1(), null);
			jPanel1.add(jLabel2, null);
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
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(316,12,202,27));
			jLabel4.setText("已选栏位");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(21,11,200,27));
			jLabel3.setText("被选栏位");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJScrollPane2(), null);
			jPanel2.add(getJScrollPane3(), null);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton5(), null);
			jPanel2.add(getJButton7(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJTextPane(), null);
			jPanel2.add(getJButton10(), null);
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
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(30,24,118,29));
			jLabel5.setText("导入选项设置");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(getJButton3(), null);
			jPanel3.add(getJButton4(), null);
			jPanel3.add(getJRadioButton(), null);
			jPanel3.add(getJRadioButton1(), null);
			jPanel3.add(getJCheckBox(), null);
			jPanel3.add(jLabel5, null);
		}
		return jPanel3;
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
			jToolBar.add(getJButton8());			
			jToolBar.add(getJButton9());
			jToolBar.add(getJButton11());
			
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
			jButton.setText("新增格式");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					isAdd = true;
					setPaneState(1);
					superClass = null;
				}
			});
		}
		return jButton;
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

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(15,44,261,332));
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
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(511,345,102,30));
			jButton1.setText("下一步");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					JTableListModel tableModel = null;
					tableModel = (JTableListModel) jTable1.getModel();
					if (tableModel == null || tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择要导入的类!","提示",2);
						return;
					}
                    if (jTextField.getText().trim().equals("")){
                    	JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请填写格式名称!","提示",2);
						return;
                    }
					setPaneState(2);
		
					
					try {
						TempNodeItem key = (TempNodeItem) tableModel.getCurrentRow();
						className = key.getClassName();
						initTbField(key);
						//已选择
						if (isAdd){
							fieldList = null;
							initTable(new Vector());
						} else {
							List ls = dataImportAction.findSuperFieldListByClass(new Request(CommonVars.getCurrUser()),superClass);
							initTable(ls);
							fieldList = new ArrayList();
							for (int i=0;i<ls.size();i++){
								fieldList.add(ls.get(i));
							}
						}
						
						
					} catch (Exception cx) {
						cx.printStackTrace();
					}
					
				}
			});
		}
		return jButton1;
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
		JTableListModel tableModel = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("类字段", "cnName", 80));
						list.add(addColumn("备注", "cnName", 200));
						return list;
					}
				});
		jTable2.getColumnModel().getColumn(1).setCellRenderer(
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

		jTable2.getColumnModel().getColumn(2).setCellRenderer(
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
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(new java.awt.Rectangle(20,44,215,322));
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
	 * This method initializes jScrollPane3	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setBounds(new java.awt.Rectangle(316,45,217,320));
			jScrollPane3.setViewportView(getJTable3());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTable3	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable3() {
		if (jTable3 == null) {
			jTable3 = new JTable();
		}
		return jTable3;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(545,333,79,29));
			jButton2.setText("下一步");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelItem == null || tableModelItem.getRowCount()<=0){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择字段对应!","提示",2);
						return;
					}
					setPaneState(3);
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
			jButton3.setBounds(new java.awt.Rectangle(508,327,98,31));
			jButton3.setText("完成");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					
					//生成任务
					if (superClass == null){
						superClass = new SuperClassList();
					}
					superClass.setTaskName(jTextField.getText().trim());//任务名称
					superClass.setMemo(jTextField1.getText().trim());//备注
					superClass.setClassName(className);
					if (jRadioButton.isSelected()){
						//检查是否有增加唯一值
						List ls = tableModelItem.getList();
						boolean isOnly = false;
						for (int i=0;i<ls.size();i++){
							SuperFieldList obj = (SuperFieldList) ls.get(i);
							if (obj.getIsOnlyValue()){
								isOnly = true;
								break;
							}							
						}
						if (!isOnly){
							JOptionPane.showMessageDialog(DgSuperTxtImport.this,"选择更新导入必须有选择标识唯一值！","提示",2);
							return;
						}
						superClass.setImportSelect(0);
					} else {
						superClass.setImportSelect(1);
					}
					if (jCheckBox.isSelected()){
						superClass.setIsFtoJ(new Boolean(true));
					} else {
						superClass.setIsFtoJ(new Boolean(false));
					}
					
					//保存表头/表体
					superClass = dataImportAction.saveSuperFieldList(new Request(CommonVars.getCurrUser()),fieldList,superClass,isAdd);
					setPaneState(0);
					if (isAdd){
						tableModelTask.addRow(superClass);		
					} else {
						tableModelTask.updateRow(superClass);	
					}
							
					
					
				}
			});
		}
		return jButton3;
	}
	
	private void setPaneState(int pages){
		jTabbedPane.setEnabledAt(0,false);
		jTabbedPane.setEnabledAt(1,false);
		jTabbedPane.setEnabledAt(2,false);
		jTabbedPane.setEnabledAt(3,false);
		jTabbedPane.setEnabledAt(4,false);
		jTabbedPane.setEnabledAt(5,false);
		jTabbedPane.setEnabledAt(pages,true);	
		jTabbedPane.setSelectedIndex(pages);
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new java.awt.Rectangle(386,327,98,30));
			jButton4.setText("上一步");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setPaneState(2);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(new java.awt.Rectangle(546,290,77,31));
			jButton5.setText("上一步");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setPaneState(1);
				}
			});
		}
		return jButton5;
	}

	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setBounds(new java.awt.Rectangle(407,345,92,30));
			jButton6.setText("上一步");
			jButton6.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setPaneState(0);
				}
			});
		}
		return jButton6;
	}

	/**
	 * This method initializes jButton7	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setBounds(new java.awt.Rectangle(243,146,65,29));
			jButton7.setText("→");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel tableModel1 = (JTableListModel) jTable2.getModel();
					if (tableModel1 == null || tableModel1.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择要添加的类字段！","提示",2);
						return;
					}
					
				   
					
					
				   System.out.println("1111111111111111111111");
				   TempNodeItem fieldItem = (TempNodeItem) tableModel1.getCurrentRow();
				   String fieldName = getFieldName(fieldItem.getCnName());
				   String name = getFieldString(fieldItem.getCnName());
				   String typeObj = "";
				   String fieldType = "";
				   try {
					    PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(Class.forName(className));
					    for (int j = 0; j < props.length; j++) {
							String tempField = props[j].getName();
							if (tempField.equals(fieldName)){
								Class clazz = props[j].getPropertyType();
								if(clazz.getName().indexOf("String")>-1){
									fieldType = "String";
							    }else if(clazz.getName().indexOf("Double")>-1){
							    	fieldType = "Double";
							    }else if(clazz.getName().indexOf("double")>-1){
							    	fieldType = "double";
							    }else if(clazz.getName().indexOf("Integer")>-1){
							    	fieldType = "Integer";
							    }else if(clazz.getName().indexOf("Boolean")>-1){
							    	fieldType = "Boolean";
							    }else if(clazz.getName().indexOf("Date")>-1){
							    	fieldType = "Date";
							    }else {
							    	typeObj = clazz.getName();
							    	fieldType = "对象";
							    }
							}						
					    }
					
					} catch (ClassNotFoundException e2) {
						e2.printStackTrace();
					}
					System.out.println("222222222222222222222222222");
						
					
										
					DgSuperFieldEdit edit = new DgSuperFieldEdit();
                    edit.setFieldName(name+"("+fieldName+")");
                    edit.setFieldType(fieldType);
                    edit.setTypeObj(typeObj);
					edit.setVisible(true);
					if (edit.isOk()){
						if (fieldList  == null){
							fieldList = new ArrayList();
						}
						SuperFieldList obj = new SuperFieldList();	
						obj.setSeqNum(edit.getSeqNum());
						obj.setFieldname(fieldName);
						obj.setName(name);
						obj.setIsNullValue(Boolean.valueOf(edit.isNull()));
						obj.setFieldtype(fieldType);
						obj.setChildClassName(typeObj);
						obj.setChildFieldName(edit.getChildFieldName());
						obj.setIsOnlyValue(edit.isOnlyValue());
						fieldList.add(obj);
						tableModelItem.addRow(obj);
					}					
					
					
					
					
				}
			});
		}
		return jButton7;
	}
	
	
	private void initTable(List list) {
		tableModelItem = new JTableListModel(jTable3, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("列序号", "seqNum", 50));
						list.add(addColumn("类字段", "fieldname", 80));						
						list.add(addColumn("类字段备注", "name", 100));
						list.add(addColumn("类字段类型", "fieldtype", 80));
						return list;
					}
		});
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
	//得到字段名
	private String getFieldName(String value) {
		String[] strs = value.split("\n");
		if (strs.length > 0) {
			return strs[0].toString().substring(4);
		} else {
			return "";
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
			jRadioButton.setBounds(new java.awt.Rectangle(65,70,111,26));
			jRadioButton.setText("更新导入");
			jRadioButton.setSelected(true);
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
			jRadioButton1.setBounds(new java.awt.Rectangle(177,73,78,21));
			jRadioButton1.setText("递增导入");
		}
		return jRadioButton1;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(65,112,136,21));
			jCheckBox.setText("是否繁转简");
		}
		return jCheckBox;
	}
	
	
	
	
	private void initTaskTable(List list) {
		if (list == null || list.size()<0){
			list = new Vector();
		}
		tableModelTask = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "seqNum", 50));
						list.add(addColumn("任务名称", "taskName", 80));		
						list.add(addColumn("导入方式", "importSelect", 80));	
						list.add(addColumn("是否繁转简", "isFtoJ", 80));	
						list.add(addColumn("任务备注", "memo", 100));
						
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
							returnValue = "更新导入";
						} else if (value.equals("1")) {
							returnValue = "递增导入";
						} 
						return returnValue;
					}
				});
		jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						super.setText((value == null) ? ""
								: castValue1(value));
						return this;
					}

					private String castValue1(Object value) {
						String returnValue = "";
						if (value.equals(Boolean.valueOf(true))) {
							returnValue = "繁转简";
						} else {
							returnValue = "不转换";
						}
						return returnValue;
					}
				});
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(305,75,256,30));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(305,148,251,31));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();			
			jTextPane.setBounds(new java.awt.Rectangle(543,33,78,244));
			jTextPane.setEditable(false);
			jTextPane.setText("说明：" +
					"列序号表示导入文件列排列顺序，对于id(ID),company(公司)一般不用选，系统自动会保存。");
		}
		return jTextPane;
	}

	/**
	 * This method initializes jButton8	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("编辑格式");
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {					
					if (tableModelTask == null || tableModelTask.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择要删除的格式！","提示",2);
						return;
					}
					isAdd = false;
					
					superClass = (SuperClassList) tableModelTask.getCurrentRow();
					
					TempNodeItem item = (TempNodeItem) hs.get(superClass.getClassName());
					JTableListModel tableModel = (JTableListModel) jTable1.getModel();
					tableModel.setTableSelectedRow(item);
					
					jTextField.setText(superClass.getTaskName());
					jTextField1.setText(superClass.getMemo());
					
					setPaneState(1);
				}
			});
		}
		return jButton8;
	}

	/**
	 * This method initializes jButton9	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton9() {
		if (jButton9 == null) {
			jButton9 = new JButton();
			jButton9.setText("删除格式");
			jButton9.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelTask == null || tableModelTask.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择要删除的格式！","提示",2);
						return;
					}
					if (JOptionPane.showConfirmDialog(DgSuperTxtImport.this,
							"您确定要删除格式吗?","提示", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					List ls = tableModelTask.getCurrentRows();
					dataImportAction.deleteSuperClassList(new Request(CommonVars.getCurrUser()),ls);
					tableModelTask.deleteRows(ls);
					
				}
			});
		}
		return jButton9;
	}

	/**
	 * This method initializes jButton10	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton10() {
		if (jButton10 == null) {
			jButton10 = new JButton();
			jButton10.setBounds(new java.awt.Rectangle(242,194,66,26));
			jButton10.setText("←");
			jButton10.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelItem == null || tableModelItem.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择要移除的字段！","提示",2);
						return;
					}
					SuperFieldList obj  = (SuperFieldList) tableModelItem.getCurrentRow();
					fieldList.remove(obj);
					obj.setIsSelected(Boolean.valueOf(true));
					fieldList.add(obj);
					tableModelItem.deleteRow(obj);
				}
			});
		}
		return jButton10;
	}

	/**
	 * This method initializes jButton11	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton11() {
		if (jButton11 == null) {
			jButton11 = new JButton();
			jButton11.setText("执行导入");
			jButton11.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelTask == null || tableModelTask.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"请选择要执行导入的任务格式！","提示",2);
						return;
					}
					txtFile = getFile();
					if (txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton11;
	}
	
	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			error = "";
			SuperClassList obj = null;
			objList =  null;
			try {
				CommonProgress.showProgressDialog(DgSuperTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				obj = (SuperClassList) tableModelTask.getCurrentRow();
				List allList = dataImportAction.importData(new Request(CommonVars.getCurrUser()),list,obj);
				objList = (List)allList.get(0);
				error = (String)allList.get(1);
				
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {

			} finally {
				setPaneState(4);
				initDataTable(objList,obj);
				
				
				if (error.equals("")){
					jTextArea.setText("数据无误，请保存！");
				} else {
					jTextArea.setText(error);
				}
			}
		}
	}
	
	
	private void initDataTable(List list,final SuperClassList obj) {
		if (list == null || list.size()<0){
			list = new Vector();
		}
		tableModelData = new JTableListModel(jTable4, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						List fieldList = dataImportAction.findSuperFieldListByClass(new Request(CommonVars.getCurrUser()),obj);
						for (int i=0;i<fieldList.size();i++){
							SuperFieldList fieldobj = (SuperFieldList) fieldList.get(i);
							list.add(addColumn(fieldobj.getName(), fieldobj.getFieldname(), 100));
						}					
						
						return list;
					}
		});
	}
		
		
	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}
	
	private List parseTxtFile() {
		System.out.println("--------------------------txtFile:"+txtFile.getName());
		String suffix = getSuffix(txtFile);
		System.out.println("---------------------------suffix:"+suffix);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {		
			lines = FileReading.readExcel(txtFile, 0, null);
		} else {
			lines = FileReading.readTxt(txtFile, 0, null);
		}
		return lines;
	}
	
	
	
	
    //调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("xls"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int state = fileChooser.showDialog(this, "选择导入文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJToolBar2(), java.awt.BorderLayout.SOUTH);
			jPanel4.add(getJScrollPane5(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(new BorderLayout());
			jPanel5.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel5.add(getJScrollPane4(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jToolBar1	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton12());
			jToolBar1.add(getJButton13());
		}
		return jToolBar1;
	}

	/**
	 * This method initializes jButton12	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton12() {
		if (jButton12 == null) {
			jButton12 = new JButton();
			jButton12.setText("保存导入");
			jButton12.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!error.equals("")){
						JOptionPane.showMessageDialog(DgSuperTxtImport.this,"读取数据有错误，不可保存！","提示",2);
						return;
					}
					new saveDataRunnable().start();
					
				}
			});
		}
		return jButton12;
	}
	
	class saveDataRunnable extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgSuperTxtImport.this);
				CommonProgress.setMessage("系统正在保存文件资料，请稍后...");
				
				dataImportAction.saveImportData(new Request(CommonVars.getCurrUser()),objList);
								
				CommonProgress.closeProgressDialog();
			} catch (Exception e) {

			} finally {
				JOptionPane.showMessageDialog(DgSuperTxtImport.this,"保存完毕！","提示",2);
				setPaneState(0);
				
			}
		}
	}
	
	/**
	 * This method initializes jButton13	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton13() {
		if (jButton13 == null) {
			jButton13 = new JButton();
			jButton13.setText("浏览数据");
			jButton13.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setPaneState(4);
				}
			});
		}
		return jButton13;
	}

	/**
	 * This method initializes jScrollPane4	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTextArea());
		}
		return jScrollPane4;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}

	/**
	 * This method initializes jToolBar2	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getJButton14());
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jButton14	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton14() {
		if (jButton14 == null) {
			jButton14 = new JButton();
			jButton14.setText("查看日志");
			jButton14.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setPaneState(5);
				}
			});
		}
		return jButton14;
	}

	/**
	 * This method initializes jScrollPane5	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getJTable4());
		}
		return jScrollPane5;
	}

	/**
	 * This method initializes jTable4	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable4() {
		if (jTable4 == null) {
			jTable4 = new JTable();
		}
		return jTable4;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
