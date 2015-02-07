/*
 * Created on 2004-11-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBFormat;
import com.bestway.bcus.dataimport.entity.DBFormatSetup;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.Gbflag;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFormatSetup extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton1 = null;
	private JComboBox jComboBox = null;
	private JComboBox jComboBox1 = null;
	private DataImportAction dataImportAction = null;
	private JTableListModel tableModel = null;
	private JTableListModel destTableModel = null;
	private JTableListModel sourceTableModel = null;
	private JTableListModel TableModel1 = null;
	private DBFormat dbFormat = null;
	private DBFormatSetup db = null;
	private JTable jTable2 = null;
	private JScrollPane jScrollPane2 = null;
	private FieldList destfieldList = null;
	private BillTemp billTemp = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JButton jButton2 = null;
	private JLabel jLabel4 = null;
	private JComboBox jComboBox2 = null;
	/**
	 * This is the default constructor
	 */
	public DgFormatSetup() {
		super();
		dataImportAction = (DataImportAction) CommonVars
	         .getApplicationContext().getBean("dataImportAction"); 
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("字段对照");
		this.setSize(706, 475);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter(){
			public void windowOpened(java.awt.event.WindowEvent e){
				initUI();
				initData();	
				initSourceFields();
				initDestFields();
			}	
		});
		
		
	}
	private void initData(){
		List list = dataImportAction.findDBFormatSetup(new Request(CommonVars.getCurrUser()),dbFormat);
		if (list !=null && !list.isEmpty()){
			initTable(list);
			db = (DBFormatSetup) list.get(0);
			setValue(db);
		} else {
			initTable(new Vector());
		}
	}
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();		
						list.add(addColumn("目的表字段名", "aimName", 80));
						list.add(addColumn("目的表字段类型", "aimFieldType", 100));
						list.add(addColumn("目的表字段长度", "aimFieldLen", 100));
						list.add(addColumn("是否主键", "iskey",80));
						list.add(addColumn("源表字段名", "sourceFieldName", 80));
						list.add(addColumn("源表字段类型", "sourceFieldType", 80));
						list.add(addColumn("源表字段长度", "sourceFieldLen", 80));
						list.add(addColumn("是否匹配", "isMatch", 80));
						return list;
					}
				});
	}
	private void initTableDest(List dataSource){
		destTableModel = new JTableListModel(jTable1, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();		
						list.add(addColumn("目的表字段名", "name", 140));
						return list;
					}
				});
	}
	private void initTableSource(List dataSource){
		sourceTableModel = new JTableListModel(jTable2, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();		
						list.add(addColumn("源表字段名", "bill1", 140));
						return list;
					}
				});
	}
	private void initUI(){
    	//转换方式
    	this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty(Gbflag.NO, "无"));
		this.jComboBox.addItem(new ItemProperty(Gbflag.OBJ, "对象转换"));
		this.jComboBox.addItem(new ItemProperty(Gbflag.NEW_COID, "当前公司ID"));
		this.jComboBox.addItem(new ItemProperty(Gbflag.SEQNUM, "序号/流水号"));
		this.jComboBox
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox);
		jComboBox.setSelectedIndex(0);
		this.jLabel.setText("目标表："+dbFormat.getClassList().getName());//目标表
		this.jLabel1.setText("源视图："+dbFormat.getDbView().getName());//源视图
		
		jComboBox2.setEnabled(false);
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
			jSplitPane.setDividerLocation(200);
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
			 jLabel4 = new JLabel();
			 jLabel1 = new JLabel();

			 jLabel = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel2.setBounds(241, 72, 60, 20);
			jLabel2.setText("转换方式");
			jLabel3.setBounds(241, 103, 67, 21);
			jLabel3.setText("导入对象项");
			jLabel.setBounds(241, 13, 192, 20);
			jLabel.setText("目标表：");
			jLabel.setForeground(new java.awt.Color(251,133,15));
			jLabel1.setBounds(241, 38, 192, 20);
			jLabel1.setText("源视图：");
			jLabel1.setForeground(new java.awt.Color(251,131,15));
			jLabel4.setBounds(241, 131, 63, 19);
			jLabel4.setText("对象子项");
			jPanel.add(getJButton1(), null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(getJScrollPane2(), null);
			jPanel.add(getJScrollPane1(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJButton2(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJComboBox2(), null);
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
			jPanel1.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel2.setBackground(java.awt.Color.white);
			jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel2.add(getJButton(), null);
		}
		return jPanel2;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("完   成");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					dispose();
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
		jTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {
                        if (tableModel==null){
                        	return;
                        }
                        if (tableModel.getCurrentRow()==null){
                        	return;
                        }
                        db = (DBFormatSetup) tableModel.getCurrentRow();
                        setValue(db);
					}
				});
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

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(245, 159, 73, 26);
			jButton1.setText(" 新增");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    if (destTableModel.getCurrentRow()==null){
                    	JOptionPane.showMessageDialog(DgFormatSetup.this,"没有选中目的字段!","提示",2);
                    	return;
                    }
                    if (sourceTableModel.getCurrentRow()!=null){
                        billTemp = (BillTemp) sourceTableModel.getCurrentRow();
                    }
                    destfieldList = (FieldList) destTableModel.getCurrentRow();

                    if (destfieldList.getObjName()!=null && ((jComboBox.getSelectedItem()==null) || ((ItemProperty) jComboBox.getSelectedItem())
							.getCode().equals("0"))){
                    	JOptionPane.showMessageDialog(DgFormatSetup.this,"转换方式不能为无或空","提示",2);
                    	return;
                    }
					DBFormatSetup dbSetup = new DBFormatSetup();
					dbSetup.setAimFieldName(destfieldList.getFieldname());
					dbSetup.setAimFieldType(destfieldList.getFieldtype());
					dbSetup.setAimFieldLen(destfieldList.getFieldlen());
					dbSetup.setAimName(destfieldList.getName());
					if (sourceTableModel.getCurrentRow()!=null){
	                    billTemp = (BillTemp) sourceTableModel.getCurrentRow();
	                    dbSetup.setSourceFieldName(billTemp.getBill1());
					    dbSetup.setSourceFieldType(billTemp.getBill2());
					    dbSetup.setSourceFieldLen(Integer.valueOf(billTemp.getBill3()));					    
					    dbSetup.setOrderNo(Integer.valueOf(billTemp.getBill4()));
					    //dbSetup.setIsMatch(isMatch(destfieldList.getFieldlen(),Integer.valueOf(billTemp.getBill3()),destfieldList.getFieldtype(),billTemp.getBill2()));
					}
					dbSetup.setCompany(CommonVars.getCurrUser().getCompany());
					if (DgFormatSetup.this.jComboBox.getSelectedItem()!=null){
						dbSetup.setGbflag(((ItemProperty) DgFormatSetup.this.jComboBox.getSelectedItem())
								.getCode());
					} else {
						dbSetup.setGbflag("0");
					}
					dbSetup.setObjName(destfieldList.getObjName());
					dbSetup.setGbStr((FieldList)jComboBox1.getSelectedItem());
					dbSetup.setDbFormat(dbFormat);
					dbSetup.setIskey(destfieldList.getIskey());
					if (jComboBox2.getSelectedItem()!= null){
						dbSetup.setGbStrChild((FieldList) jComboBox2.getSelectedItem());
					}
					dbSetup = dataImportAction.saveDBFormatSetup(new Request(CommonVars.getCurrUser()),dbSetup);
					tableModel.addRow(dbSetup);
					TableModel1.addRow(dbSetup);
					
					FieldList destfieldList = (FieldList) destTableModel.getCurrentRow();
                    initGbstr(destfieldList);
				}
			});

		}
		return jButton1;
	}

	
	private Boolean isMatch(Integer aimLen,Integer sourceLen,String aimType,String sourceType){
		if ((sourceLen.intValue() <= aimLen.intValue()) && aimType.equals(sourceType)){ 
		  return new Boolean(true);
		}
		return new Boolean(false);
	}
	/**

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(314, 72, 119, 21);
		}
		return jComboBox;
	}

	/**

	 * This method initializes jComboBox1	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(314, 103, 119, 21);
			jComboBox1.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {    
					if (jComboBox1.getSelectedItem()!= null){
					    FieldList fieldList = (FieldList) jComboBox1.getSelectedItem();
					    if (fieldList.getFieldtype()!= null && fieldList.getFieldtype().equals("对象")){
					    	initChild(fieldList);
					    } else {
					    	jComboBox2.setSelectedIndex(-1);
							jComboBox2.setEnabled(false);
					    }
					} else {
						jComboBox2.setSelectedIndex(-1);
						jComboBox2.setEnabled(false);
					}
				}
			});				
		}
		return jComboBox1;
	}

	private void initChild(FieldList fieldList){
		if (fieldList.getObjName()!=null){
			this.jComboBox2.setEnabled(true);	
		    List list = dataImportAction.findFieldList(new Request(CommonVars
				  .getCurrUser()),fieldList.getObjName());
		    this.jComboBox2.setModel(new DefaultComboBoxModel(list.toArray()));
		    this.jComboBox2.setRenderer(CustomBaseRender.getInstance().getRender(
			    	"sortno", "name"));
		    CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
			    	this.jComboBox2, "sortno", "name");
		    this.jComboBox2.setSelectedIndex(-1);		    
		} else {
			this.jComboBox2.setEnabled(false);
			this.jComboBox2.setSelectedIndex(-1);
		}
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
	 * @return Returns the dbFormat.
	 */
	public DBFormat getDbFormat() {
		return dbFormat;
	}
	/**
	 * @param dbFormat The dbFormat to set.
	 */
	public void setDbFormat(DBFormat dbFormat) {
		this.dbFormat = dbFormat;
	}
   private void initSourceFields(){ //源表字段名
   	  List list = new Vector();
   	  list = dataImportAction.getSourceFields(new Request(CommonVars.getCurrUser()),dbFormat);
	  if (list!=null && list.size()>0){
	  	initTableSource(list);
	  } else {
	  	initTableSource(new Vector());
	  }
   }
   private void initDestFields(){ //目的表字段名
   	  List list = dataImportAction.findFieldList(new Request(CommonVars.getCurrUser()),dbFormat.getClassList());
      if (list != null && !list.isEmpty()){
      	initTableDest(list);
      	initGbstr((FieldList) list.get(0));
      } else {
      	initTableDest(new Vector());
      	this.jComboBox1.setEnabled(false);
      }
      
   }
	private void initGbstr(FieldList fieldList){
		if (fieldList.getObjName()!=null){
			this.jComboBox1.setEnabled(true);	
		    List list = dataImportAction.findFieldList(new Request(CommonVars
				  .getCurrUser()),fieldList.getObjName());
		    this.jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		    this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
			    	"sortno", "name"));
		    CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
			    	this.jComboBox1, "sortno", "name");
		    this.jComboBox1.setSelectedIndex(-1);
		    this.jComboBox.setSelectedIndex(Integer.parseInt(Gbflag.OBJ));		    
		} else {
			this.jComboBox1.setEnabled(false);
			this.jComboBox.setSelectedIndex(0);
		}
	}
	/**

	 * This method initializes jTable2	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
			jTable2.setIntercellSpacing(new java.awt.Dimension(0,0));
			jTable2.setShowHorizontalLines(false);
			jTable2.setShowVerticalLines(false);
		}
		return jTable2;
	}

	/**

	 * This method initializes jScrollPane2	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(442, 7, 248, 186);
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**

	 * This method initializes jTable1	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setIntercellSpacing(new java.awt.Dimension(0,0));
			jTable1.setShowHorizontalLines(false);
			jTable1.setShowVerticalLines(false);
		}
		jTable1.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {
                        if (destTableModel==null){
                        	return;
                        }
                        if (destTableModel.getCurrentRow()==null){
                        	return;
                        }
                        FieldList destfieldList = (FieldList) destTableModel.getCurrentRow();
                        initGbstr(destfieldList);
					}
				});
		return jTable1;
	}

	/**

	 * This method initializes jScrollPane1	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(4, 7, 226, 186);
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * @return Returns the tableModel1.
	 */
	public JTableListModel getTableModel1() {
		return TableModel1;
	}
	/**
	 * @param tableModel1 The tableModel1 to set.
	 */
	public void setTableModel1(JTableListModel tableModel1) {
		TableModel1 = tableModel1;
	}
	private void setValue(DBFormatSetup db){
		if (db.getGbflag()!=null){
			this.jComboBox.setSelectedIndex(
			  		ItemProperty.getIndexByCode(db.getGbflag(), jComboBox));
		} else {
			this.jComboBox.setSelectedIndex(-1);
		}
		if (db.getGbStr()!=null){
			initGbstr(db.getGbStr());
			
			if (db.getGbStr()!=null){
				jComboBox1.setSelectedItem(db.getGbStr());
			} else {
				jComboBox1.setSelectedIndex(-1);
			}
			jComboBox1.setEnabled(true);
		} else {
			jComboBox1.setSelectedIndex(-1);
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
			jButton2.setBounds(346, 160, 77, 25);
			jButton2.setText("删除");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null){
						JOptionPane.showMessageDialog(DgFormatSetup.this,"请选中要删除的对应?","提示",2);
						return;
					}
					DBFormatSetup setup = (DBFormatSetup) tableModel.getCurrentRow();
					dataImportAction.deleteDBFormatSetup(new Request(CommonVars.getCurrUser()),setup);
					tableModel.deleteRow(setup);
					TableModel1.deleteRow(setup);
				}
			});
		}
		return jButton2;
	}
	/**
	 * This method initializes jComboBox2	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(314, 130, 119, 20);
		}
		return jComboBox2;
	}
  }  //  @jve:decl-index=0:visual-constraint="10,10"
