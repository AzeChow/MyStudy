/*
 * Created on 2004-11-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.ClassList;
import com.bestway.bcus.dataimport.entity.DBFormat;
import com.bestway.bcus.dataimport.entity.DBFormatSetup;
import com.bestway.bcus.dataimport.entity.DBView;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFormatEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JToolBar jToolBar1 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JPanel jPanel2 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JTextField jTextField3 = null;
	private JComboBox jComboBox = null;
	private JTextField jTextField4 = null;
	private DataImportAction dataImportAction = null;
	private DBFormat dbFormat=null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModel1 = null;
	private int dataState = DataState.BROWSE;
	
	private JButton jButton4 = null;
	private JButton jButton5 = null;
	private ClassList classList = null;
	private DBView dbview = null;
	private JButton jButton6 = null;

	private JCheckBox jCheckBox = null;
	/**
	 * This is the default constructor
	 */
	public DgFormatEdit() {
		super();
		dataImportAction = (DataImportAction) CommonVars
		     .getApplicationContext().getBean("dataImportAction"); 
		initialize();
		setState();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("域编辑");
		this.setSize(706, 489);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowOpened(java.awt.event.WindowEvent e){
				dbFormat = (DBFormat) tableModel.getCurrentRow();  //格式	
				initUI();
				fillWindow();
				initData();
			}	
		});
	}
	private void initUI(){
        //转换
		this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty("0", "不转换"));
		this.jComboBox.addItem(new ItemProperty("1", "繁转简"));
		this.jComboBox.addItem(new ItemProperty("2", "简转繁"));
		this.jComboBox
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox);
	}
	private void fillWindow(){
		jTextField.setText(dbFormat.getRegionName());
		jTextField1.setText(dbFormat.getClassList().getName());
		jTextField2.setText(dbFormat.getDbView().getDb().getName());
		jTextField3.setText(dbFormat.getDbView().getName());
		jTextField4.setText(dbFormat.getMemo());
        //转换方式
		if (dbFormat.getGbflag()!= null) {
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(String
					.valueOf(this.dbFormat.getGbflag()), jComboBox));
		} else {
			jComboBox.setSelectedIndex(-1);
		}
		if (dbFormat.getIsBigToBgk()!=null && dbFormat.getIsBigToBgk()){
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}
		classList = dbFormat.getClassList();
		dbview = dbFormat.getDbView();
		
	}
	private void initData(){
		List list = dataImportAction.findDBFormatSetup(new Request(CommonVars.getCurrUser()),dbFormat);
		if (list !=null && !list.isEmpty()){
			initTable(list);
		} else {
			initTable(new Vector());
		}
	}
	private void initTable(List dataSource) {
		tableModel1 = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();		
						list.add(addColumn("目的表字段名", "aimName", 80));
						list.add(addColumn("目的表字段类型", "aimFieldType", 100));
						list.add(addColumn("目的表字段长度", "aimFieldLen", 100));
						list.add(addColumn("是否主键","iskey",80));
						list.add(addColumn("源表字段名", "sourceFieldName", 80));
						list.add(addColumn("源表字段类型", "sourceFieldType", 80));
						list.add(addColumn("源表字段长度", "sourceFieldLen", 80));
						list.add(addColumn("是否匹配", "isMatch", 80));
						return list;
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jSplitPane	

	 * 	

	 * @return javax.swing.JSplitPane	

	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
		}
		return jSplitPane;
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
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
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
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jToolBar.add(getJButton4());
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
			jButton.setText("编辑对应关系");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFormatSetup dg = new DgFormatSetup();
					dg.setTableModel1(tableModel1);
					dg.setDbFormat(dbFormat);
					dg.setVisible(true);
				}
			});

		}
		return jButton;
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
			jScrollPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jToolBar1	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJButton2());
			jToolBar1.add(getJButton3());
		}
		return jToolBar1;
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

					dataState = DataState.EDIT;
					setState();
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
			jButton2.setText("保存");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    if (jTextField.getText().equals("")){
                    	JOptionPane.showMessageDialog(DgFormatEdit.this,
    							"请填写域名称！", "确认", 2);
    					  return;
                    }
					dbFormat.setMemo(jTextField4.getText());
					dbFormat.setRegionName(jTextField.getText());
					dbFormat.setClassList(classList);
					dbFormat.setDbView(dbview);
					if (jComboBox.getSelectedItem() != null) {
						dbFormat.setGbflag(((ItemProperty) jComboBox.getSelectedItem())
										.getCode());
					}
					if (jCheckBox.isSelected()){
						dbFormat.setIsBigToBgk(new Boolean(true));
					} else {
						dbFormat.setIsBigToBgk(new Boolean(false));
					}
					dbFormat = dataImportAction.saveDBFormat(new Request(CommonVars.getCurrUser()),dbFormat);
					tableModel.updateRow(dbFormat);
					dataState = DataState.BROWSE;
					setState();
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
			jButton3.setText("关闭");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFormatEdit.this.dispose();
				}
			});

		}
		return jButton3;
	}

	/**

	 * This method initializes jPanel2	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jLabel.setBounds(71, 19, 45, 19);
			jLabel.setText("域名称");
			jLabel1.setBounds(336, 19, 56, 21);
			jLabel1.setText("目标表名");
			jLabel2.setBounds(71, 48, 72, 20);
			jLabel2.setText("数据源名称");
			jLabel3.setBounds(336, 48, 56, 21);
			jLabel3.setText("视图名称");
			jLabel4.setBounds(71, 77, 72, 22);
			jLabel4.setText("字符集转换");
			jLabel5.setBounds(336, 77, 47, 21);
			jLabel5.setText("域描述");
			jPanel2.add(jLabel, null);
			jPanel2.add(getJTextField(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getJTextField1(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTextField2(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTextField3(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getJTextField4(), null);
			jPanel2.add(getJButton5(), null);
			jPanel2.add(getJButton6(), null);
			jPanel2.add(getJCheckBox(), null);
		}
		return jPanel2;
	}

	/**

	 * This method initializes jTextField	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(151, 19, 132, 22);
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
			jTextField1.setEditable(false);
			jTextField1.setBounds(395, 19, 146, 22);
		}
		return jTextField1;
	}

	/**

	 * This method initializes jTextField2	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setEditable(false);
			jTextField2.setBounds(151, 48, 133, 22);
		}
		return jTextField2;
	}

	/**

	 * This method initializes jTextField3	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField3() {
		if (jTextField3 == null) {
			jTextField3 = new JTextField();
			jTextField3.setEditable(false);
			jTextField3.setBounds(395, 48, 145, 22);
		}
		return jTextField3;
	}

	/**

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(151, 77, 132, 21);
		}
		return jComboBox;
	}

	/**

	 * This method initializes jTextField4	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(395, 77, 145, 22);
		}
		return jTextField4;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}
	/**
	 * @param tableModel The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}
	/**
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return tableModel1;
	}
	/**
	 * @param tableModel1 The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		this.tableModel1 = tableModel1;
	}
	/**

	 * This method initializes jButton4	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("删除对应关系");
			jButton4.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    if (tableModel1.getCurrentRow()==null){
                    	JOptionPane.showMessageDialog(DgFormatEdit.this,"请选中字段对应！","提示",2);
                    	return;
                    }
                    DBFormatSetup obj = (DBFormatSetup) tableModel1.getCurrentRow();
					dataImportAction.deleteDBFormatSetup(new Request(CommonVars.getCurrUser()),obj);
					tableModel1.deleteRow(obj);
					
				}
			});

		}
		return jButton4;
	}
	private void setState(){
		jButton1.setEnabled(dataState == DataState.BROWSE);
		jButton2.setEnabled(!(dataState == DataState.BROWSE));
		jTextField.setEditable(!(dataState == DataState.BROWSE));
		jComboBox.setEnabled(!(dataState == DataState.BROWSE));
		jTextField4.setEditable(!(dataState == DataState.BROWSE));
		jButton5.setEnabled(!(dataState == DataState.BROWSE));
		jButton6.setEnabled(!(dataState == DataState.BROWSE));
		jCheckBox.setEnabled(!(dataState == DataState.BROWSE));
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(541, 17, 25, 24);
			jButton5.setText("...");
			jButton5.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					ClassList m = (ClassList)CommonQuery.getInstance().getClassList();
					if (m!=null){
						classList = m;
						jTextField1.setText(m.getName());
					}
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
			jButton6.setBounds(283, 46, 28, 25);
			jButton6.setText("...");
			jButton6.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					DBView d = (DBView)CommonQuery.getInstance().getDBView(dbFormat.getDbView().getDb());
					if (d!=null){
						dbview = d;
						jTextField2.setText(d.getName());
					}
				}
			});
		}
		return jButton6;
	}
	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(550,76,149,24));
			jCheckBox.setText("是否Big5转Gbk码转换");
		}
		return jCheckBox;
	}
                     }  //  @jve:decl-index=0:visual-constraint="10,10"
