/*
 * Created on 2004-11-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.DBDataRoot;
import com.bestway.bcus.dataimport.entity.TxtTaskEx;
import com.bestway.bcus.dataimport.entity.TxtTaskSel;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.dataimport.DgDataTools.TxtDataRunnable;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import com.bestway.ui.editor.SQLTextPane;
/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgViewEdit extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
   
	private JLabel jLabel = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel pnContext = null;

	private JButton btnPrevious = null;
	private JButton btnNext = null;
	private JButton btnExit = null;


	private JPanel jPage1 = null; //页一
	private JPanel jPage2 = null; //页二
	private JPanel jPage3 = null; //页三
	private JPanel jPage4 = null; //页四
	private JPanel jPage5 = null; //页五
	private int step = 0;

	private JLabel jLabel4 = null;
	private JLabel jLabel5 = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModel1 = null;
	private JTableListModel tableModel2 = null;
	private DataImportAction dataImportAction = null;
	private DBDataRoot db = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JList jList = null;
	private JList jList1 = null;
	
	private Vector tableSource = new Vector(); //未选择的表
	private Vector tableDest = new Vector();   //已选择的表
	private Vector vector = new Vector();       //所有字段
	private Vector vf = new Vector();           //当前表字段
	private JScrollPane jScrollPane3 = null;
	private JList jList2 = null;
	private JScrollPane jScrollPane4 = null;
	private JList jList3 = null;
	private Vector groupSource = new Vector(); //未选分组的字段
	private Vector groupDest = new Vector();   //分组的字段
	
	private Vector orderSource = new Vector(); //未选排序的字段
	private Vector orderDest = new Vector();   //排序的字段	
	
	private JComboBox jComboBox = null;
	private JComboBox jComboBox1 = null;
	private JComboBox jComboBox2 = null;
	private JComboBox jComboBox3 = null;
	private JComboBox jComboBox4 = null;
	private JComboBox jComboBox5 = null;
	private JComboBox jComboBox6 = null;
	private JComboBox jComboBox7 = null;
	private JScrollPane jScrollPane5 = null;
	private JTextArea jTextArea = null;
	private JButton jButton = null;
	
	private JRadioButton jRadioButton = null;
	private JRadioButton jRadioButton1 = null;

	private ButtonGroup group = new ButtonGroup();
	private String orderBy = "asc";
	private String where = "";
	private JButton jButton5 = null;
	private JButton jButton6 = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane6 = null;
	private JScrollPane jScrollPane7 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton7 = null;
	private JButton jButton8 = null;

	private SQLTextPane sqlTextPane = null;
	private DBDataRoot  dbDataRoot = null;  //  @jve:decl-index=0:
	
	/**
	 * This is the default constructor
	 */
	public DgViewEdit() {
		super();
		dataImportAction = (DataImportAction) CommonVars
            .getApplicationContext().getBean("dataImportAction");
		initialize();
	}
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();		
						list.add(addColumn("名称", "name", 150));
						return list;
					}
				});
		
		Object obj = null;
		for(int i=0;i<dataSource.size();i++){
			if(dbDataRoot!=null && dbDataRoot.getId().equals(
					((DBDataRoot)dataSource.get(i)).getId())){
				obj = dataSource.get(i);
			}
		}
		if(obj != null){
			tableModel.setTableSelectedRow(obj);	
		}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("视图设置向导");
		this.setSize(535, 365);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getPnContext(), null);
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
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBounds(1, 1, 527, 42);
			jPanel.setBackground(java.awt.Color.white);
			jPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel.setText("标题");
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 18));
			jLabel2.setText("");
			jLabel2.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepic.jpg")));
			jLabel3.setText("");
			jLabel3.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/titlepoint.jpg")));
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel2, java.awt.BorderLayout.EAST);
			jPanel.add(jLabel3, java.awt.BorderLayout.WEST);
		}
		return jPanel;
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
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(1, 295, 526, 36);
			jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanel1.add(getBtnPrevious(), null);
			jPanel1.add(getBtnNext(), null);
			jPanel1.add(getBtnExit(), null);
			jPanel1.add(getJButton8(), null);
		}
		return jPanel1;
	}

	/**
	 * 
	 * This method initializes PnContext
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getPnContext() {
		if (pnContext == null) {
			pnContext = new JPanel();
			pnContext.setLayout(null);
			pnContext.setBounds(1, 41, 525, 255);
		}
		return pnContext;
	}

	/**
	 * 
	 * This method initializes btnPrevious
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setBounds(139, 9, 76, 22);
			btnPrevious.setText("上一步");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					step--;
                    gotoStep(step);
                    if (step == 0){
                    	clearAll();
                    }
				}
			});

		}
		return btnPrevious;
	}
	 private void gotoStep(int step) {
        switch (step) {
        case 0:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage1(), null);
            getDataRoot(); //得到数据源
            this.getJPage1().setVisible(true);
            this.jLabel.setText("第一步：选择数据源");
            break;
        case 1:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage2(), null);
            try {
			   getTable();   //得到表
			} catch (SQLException e) {
				e.printStackTrace();
			}
            this.getJPage2().setVisible(true);
            this.jLabel.setText("第二步：选择表");
            break;
        case 2:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage3(), null);
            this.jList2.setCellRenderer(new UserListCellRenderer());
            initTableVector();
            getAllFields();
            getFieldsList(0);
            this.getJPage3().setVisible(true);
            this.jLabel.setText("第三步：选择字段");
            break;
        case 3:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage4(), null);
            DbCommon.initComboBox(this.jComboBox,this.jComboBox3,
            		this.jComboBox6,this.jComboBox7,this.jComboBox1,jComboBox4,tableDest,jComboBox2,jComboBox5);
            this.getJPage3().setVisible(true);
            this.jLabel.setText("第四步：条件过滤");
            break;
        case 4:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage2(), null);
            initGroupFields();
            this.getJPage2().setVisible(true);
            this.jLabel.setText("第五步：分组");
            break;
        case 5:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage2(), null);
            initOrderFields();
            this.getJPage2().setVisible(true);
            this.jLabel.setText("第六步：排序");
            break;
        case 6:
            this.pnContext.removeAll();
            this.pnContext.add(this.getJPage5(), null);
            setsql();
            this.getJPage5().setVisible(true);
            this.jLabel.setText("第七步：验证保存");
            break;
        }
        this.pnContext.repaint();
        this.pnContext.validate();
        setState();
    }
     private void setsql(){
     	DbCommon.getSql(sqlTextPane,vector,where,tableDest,groupDest,orderDest);
     }
	 private void initOrderFields(){
	 	this.jLabel5.setText("可选排序的字段");
	 	this.jLabel4.setText("排序的字段");
	 	group.add(jRadioButton);
		group.add(jRadioButton1);
		if (orderBy.equals("asc")){
		  this.jRadioButton.setSelected(true);
		} else {
		  this.jRadioButton1.setSelected(true);
		}
	 	jRadioButton.setVisible(true);
	 	jRadioButton1.setVisible(true);
	 	this.jList.setCellRenderer(new UserListCellRenderer());
		this.jList1.setCellRenderer(new UserListCellRenderer());
		if (orderSource.isEmpty() && orderDest.isEmpty()){
			orderSource = DbCommon.allSelectedFields(vector);
		}
		this.jList.setListData(orderSource);
		this.jList1.setListData(orderDest);
	 }
	 private void initGroupFields(){
	 	this.jLabel5.setText("可选分组的字段");
	 	this.jLabel4.setText("分组的字段");
	 	jRadioButton.setVisible(false);
	 	jRadioButton1.setVisible(false);
	 	this.jList.setCellRenderer(new UserListCellRenderer());
		this.jList1.setCellRenderer(new UserListCellRenderer());
		if (groupSource.isEmpty() && groupDest.isEmpty()){
			groupSource = DbCommon.allSelectedFields(vector);
		}
		this.jList.setListData(groupSource);
		this.jList1.setListData(groupDest);
	 }
	 private void initTableVector(){
	 	this.jList2.setListData(tableDest);
	 }
	 private void getTable()throws SQLException{;
	 	this.jLabel5.setText("可选择的表");
	 	this.jLabel4.setText("选择的表");	 
	 	jRadioButton.setVisible(false);
	 	jRadioButton1.setVisible(false);
	 	this.jList.setCellRenderer(new UserListCellRenderer());
		this.jList1.setCellRenderer(new UserListCellRenderer());
	 	if (tableSource.isEmpty() && tableDest.isEmpty()){
	 		tableSource = dataImportAction.isNulltable(new Request(CommonVars.getCurrUser()),db);
	 	}
	    this.jList.setListData(tableSource);
	    this.jList1.setListData(tableDest);
	 }
	 
	 
	 private void getDataRoot(){
	 	List list = dataImportAction.findDBDataRoot(new Request(CommonVars.getCurrUser()));
        if (list != null && !list.isEmpty()){
        	initTable(list);
        }
	 }
	    private void setState() {
	        this.btnPrevious.setEnabled(this.step > 0);
	        this.btnNext.setEnabled(this.step < 6);
	        this.jButton8.setEnabled(this.step < 6);
	    }
	  private boolean checkDbIsConnect(){
	  	 if (tableModel.getCurrentRow()==null){
        	JOptionPane.showMessageDialog(DgViewEdit.this, "请选择数据源！", "提示！", 2);
			return false;
         }
        db = (DBDataRoot) tableModel.getCurrentRow();
        //检查速度慢
        /*if (!dataImportAction.checkDB(new Request(CommonVars.getCurrUser()),db).equals("成功")){
        	JOptionPane.showMessageDialog(DgViewEdit.this, dataImportAction.checkDB(new Request(CommonVars.getCurrUser()),db), "提示！", 2);
        	return false;
        }
        if (db.getUrl().indexOf("jdbc:odbc:drive") > -1){//odbc	
        	
        } else {
	        try {
				if (dataImportAction.isNulltable(new Request(CommonVars.getCurrUser()),db).isEmpty()){
					JOptionPane.showMessageDialog(DgViewEdit.this, "该数据库为空！", "提示！", 2);
					return false;
				}
			} catch (HeadlessException e) {
				return false;
			} catch (SQLException e) {
				return false;
			}
        }*/
		return true;
	  }
	/**
	 * 
	 * This method initializes btnNext
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setBounds(225, 9, 76, 22);
			btnNext.setText("下一步");
			btnNext.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (step == 0) {
						if (!checkDbIsConnect()){
							return;
						}
					} else if (step == 1){
						if (tableDest.size()<1){
							JOptionPane.showMessageDialog(DgViewEdit.this, "至少要选择一个表！", "提示！", 2);
							return;
						}
					} else if (step == 2){
						if (!DbCommon.isSelectedFields(vector)){
							JOptionPane.showMessageDialog(DgViewEdit.this, "至少要选择一个字段！", "提示！", 2);
							return;
						}
					} else if (step == 3){
						DgViewEdit.this.setWhere(DgViewEdit.this.jTextArea.getText());
					}
					step++;
                    gotoStep(step);
				}
			});

		}
		return btnNext;
	}

	/**
	 * 
	 * This method initializes btnExit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 *  
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setBounds(312, 9, 76, 22);
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jPage1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPage1() {
		if (jPage1 == null) {
			jPage1 = new JPanel();
			javax.swing.JLabel jLabel1 = new JLabel();

			jPage1.setLayout(null);
			jPage1.setSize(525, 254);
			jPage1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel1.setBounds(25, 24, 158, 20);
			jLabel1.setText("请从下列选择数据源名称：");
			jPage1.add(jLabel1, null);
			jPage1.add(getJScrollPane(), null);
		}
		return jPage1;
	}

	/**
	 * 
	 * This method initializes jPage2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPage2() {
		if (jPage2 == null) {
			jPage2 = new JPanel();
			 jLabel4 = new JLabel();

			 jLabel5 = new JLabel();

			jPage2.setLayout(null);
			jPage2.setSize(525, 254);
			jPage2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel4.setBounds(346, 11, 80, 21);
			jLabel4.setText("已选择的表");
			jLabel5.setBounds(75, 11, 116, 21);
			jLabel5.setText("可选择的表");
			jPage2.add(getJScrollPane1(), null);
			jPage2.add(getJScrollPane2(), null);
			jPage2.add(getJButton1(), null);
			jPage2.add(getJButton2(), null);
			jPage2.add(getJButton3(), null);
			jPage2.add(getJButton4(), null);
			jPage2.add(jLabel4, null);
			jPage2.add(jLabel5, null);
			jPage2.add(getJRadioButton(), null);
			jPage2.add(getJRadioButton1(), null);
		}
		return jPage2;
	}

	/**
	 * 
	 * This method initializes jPage3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 *  
	 */
	private JPanel getJPage3() {
		if (jPage3 == null) {
			jPage3 = new JPanel();
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel7 = new JLabel();

			jPage3.setLayout(null);
			jPage3.setSize(525, 254);
			jPage3.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jLabel6.setBounds(88, 11, 66, 18);
			jLabel6.setText("已选择的表");
			jLabel7.setBounds(298, 11, 113, 18);
			jLabel7.setText("选择的表对应的字段");
			jPage3.add(getJScrollPane3(), null);
			jPage3.add(getJScrollPane4(), null);
			jPage3.add(jLabel6, null);
			jPage3.add(jLabel7, null);
		}
		return jPage3;
	}

	public void setVisible(boolean b) {
        if (b) {
            this.gotoStep(this.step);
        }
        super.setVisible(b);
    }
	/**

	 * This method initializes jScrollPane1	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(25, 37, 196, 194);
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**

	 * This method initializes jScrollPane2	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBounds(298, 36, 196, 194);
			jScrollPane2.setViewportView(getJList1());
		}
		return jScrollPane2;
	}

	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(237, 104, 48, 27);
			jButton1.setText(">");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					addTableToGroup();
					
				}
			});

		}
		return jButton1;
	}
	void addTableToGroup() {
		if (step == 1){
			add(1,"表名",tableSource,tableDest);
		} else if (step == 4){
			add(4,"排序字段",groupSource,groupDest);
		} else if (step == 5){
			add(5,"排序字段",orderSource,orderDest);
		}
	}
	private void add(int step,String tishi,Vector vectorSource,Vector vectorDest){
		if (this.jList.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "请选择要添加的"+tishi+"!", "提示", 2);
			return;
		}
		Object[] selectedTables = this.jList.getSelectedValues();
		if (step==5){
			for (int i = 0; i < selectedTables.length; i++) {
				BillTemp temp = (BillTemp) selectedTables[i];
				BillTemp temp1 = new BillTemp();
				temp1.setBill1(temp.getBill1()+" "+orderBy);
				vectorDest.addElement(temp1);
				vectorSource.removeElement(temp);
			}
		} else {
			for (int i = 0; i < selectedTables.length; i++) {			
				BillTemp temp = (BillTemp) selectedTables[i];
				vectorDest.addElement(temp);
				vectorSource.removeElement(temp);
			}
			
		}		
		this.jList.setListData(vectorSource);
		this.jList1.setListData(vectorDest);
		
	}
	/**

	 * This method initializes jButton2	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(237, 64, 48, 27);
			jButton2.setText(">>");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					int count = DgViewEdit.this.jList.getModel()
					   .getSize();
			        int[] indices = new int[count];
			        for (int i = 0; i < count; i++) {
				         indices[i] = i;
			        }
			        DgViewEdit.this.jList.setSelectedIndices(indices);
			        addTableToGroup();
					
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
			jButton3.setBounds(237, 143, 48, 27);
			jButton3.setText("<");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					removeTableFromGroup();
				}
			});

		}
		return jButton3;
	}
	void removeTableFromGroup() {
		if (step == 1){
			remove(1,"表名",tableSource,tableDest);
		} else if (step == 4){
			remove(4,"分组字段",groupSource,groupDest);
		} else if (step == 5){
			remove(5,"排序字段",orderSource,orderDest);
		}

	}
	private void remove(int step,String tishi,Vector vectorSource,Vector vectorDest){
		if (this.jList1.getSelectedValue() == null) {
			JOptionPane.showMessageDialog(this, "请选择要删除选择"+tishi+"!", "提示", 2);
			return;
		}
		if (step == 5){
			Object[] removedUsers = this.jList1.getSelectedValues();
			for (int i = 0; i < removedUsers.length; i++) {
				BillTemp temp = (BillTemp) removedUsers[i];
				BillTemp temp1 = new BillTemp();
				temp1.setBill1(removeOrder(temp.getBill1()));
				vectorSource.addElement(temp1);
				vectorDest.removeElement(temp);
			}
		} else {
		Object[] removedUsers = this.jList1.getSelectedValues();
		for (int i = 0; i < removedUsers.length; i++) {
			BillTemp temp = (BillTemp) removedUsers[i];
			vectorSource.addElement(temp);
			vectorDest.removeElement(temp);
			if (step == 1){
				DbCommon.deleteFields(vector,temp.getBill1());	
			}
		}
		}
		this.jList.setListData(vectorSource);
		this.jList1.setListData(vectorDest);
	}
	private String removeOrder(String exitOrder){
		String[] strs = null;
		String s = new String();
		s = exitOrder;
		strs = s.split(" "); 
		return strs[0];
	}
	/**

	 * This method initializes jButton4	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(237, 186, 48, 27);
			jButton4.setText("<<");
			jButton4.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					int count = DgViewEdit.this.jList1.getModel()
					    .getSize();
			        int[] indices = new int[count];
			        for (int i = 0; i < count; i++) {
			        	indices[i] = i;
			        }
			        DgViewEdit.this.jList1.setSelectedIndices(indices);
			        removeTableFromGroup();
				}
			});

		}
		return jButton4;
	}

	/**

	 * This method initializes jList	

	 * 	

	 * @return javax.swing.JList	

	 */    
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.addMouseListener(new java.awt.event.MouseAdapter() { 

				public void mouseClicked(java.awt.event.MouseEvent e) {    

					if (e.getClickCount() == 2) {
						addTableToGroup();
					}
				}
			});

		}
		return jList;
	}

	/**

	 * This method initializes jList1	

	 * 	

	 * @return javax.swing.JList	

	 */    
	private JList getJList1() {
		if (jList1 == null) {
			jList1 = new JList();
			jList1.addMouseListener(new java.awt.event.MouseAdapter() { 

				public void mouseClicked(java.awt.event.MouseEvent e) {    

					if (e.getClickCount() == 2) {
						removeTableFromGroup();
					}
				}
			});

		}
		return jList1;
	}
	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((BillTemp) value).getBill1();
				//setIcon(CommonVars.getRcpIconSource().getIcon(
					//	"images.table.icon"));
			}
			setText(s);
			return this;
		}
	}

	/**

	 * This method initializes jScrollPane3	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setBounds(27, 33, 195, 198);
			jScrollPane3.setViewportView(getJList2());
		}
		return jScrollPane3;
	}

	/**

	 * This method initializes jList2	

	 * 	

	 * @return javax.swing.JList	

	 */    
	private JList getJList2() {
		if (jList2 == null) {
			jList2 = new JList();
			jList2.addMouseListener(new java.awt.event.MouseAdapter() { 

				public void mouseClicked(java.awt.event.MouseEvent e) {   //获取字段 

					if (e.getClickCount()==1){
						getFieldsList(1);
					}

				}
			});

		}
		return jList2;
	}
	//得到所有字段to vector
	private void getAllFields(){
	   if (tableDest!=null && !tableDest.isEmpty()){
	   	   for (int i=0;i<tableDest.size();i++){
	   	   	  String tableName = ((BillTemp)tableDest.get(i)).getBill1();
	   	      if (DbCommon.isExitFields(vector,tableName).isEmpty()){
	   	          getFields(tableName); 
	   	      }
	   	   }
	   }
	}
	
    private void getFieldsList(int first){
    	DgViewEdit.this.jList3.removeAll();
		vf.clear();
		if (first==0){
			DgViewEdit.this.jList2.setSelectedIndex(0);
		}
		Object selectedTables = DgViewEdit.this.jList2.getSelectedValue();
		String tableName = ((BillTemp) selectedTables).getBill1();	
		vf = DbCommon.isExitFields(vector,tableName);
		DgViewEdit.this.jList3.setListData(vf);
    }
    private void getFields(String tableName){
    	Vector columnsName = new Vector();
    	try {
    		columnsName = dataImportAction.getFName(new Request(CommonVars.getCurrUser()),db,tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	CheckableItem item = new CheckableItem("所有字段",tableName);
		vector.add(item);
		for (int j=0;j<columnsName.size();j++){
			CheckableItem item1 = new CheckableItem(((BillTemp)columnsName.get(j)).getBill1(),tableName);
			vector.add(item1);
		}
    }
	/**

	 * This method initializes jScrollPane4	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setBounds(224, 33, 271, 197);
			jScrollPane4.setViewportView(getJList3());
		}
		return jScrollPane4;
	}

	/**

	 * This method initializes jList3	

	 * 	

	 * @return javax.swing.JList	

	 */    
	private JList getJList3() {
		if (jList3 == null) {
			jList3 = new JList();
			jList3.setCellRenderer(new CheckListRenderer());
			jList3.addMouseListener(new MouseAdapter() {
			      public void mouseClicked(MouseEvent e) {
			        int index = jList3.locationToIndex(e.getPoint());
			        CheckableItem item = (CheckableItem) DgViewEdit.this.jList3.getModel().getElementAt(index);
			        item.setSelected(! item.isSelected());
			        if (index==0){
			        	for(int j=1;j<DgViewEdit.this.jList3.getModel().getSize();j++){
							CheckableItem item1 = (CheckableItem) DgViewEdit.this.jList3.getModel().getElementAt(j);
							if (item.isSelected() && (!item1.isSelected())){
								item1.setSelected(true);
							} else if (!item.isSelected() && item1.isSelected()){
								item1.setSelected(false);
							}
							Rectangle rect = jList3.getCellBounds(j, j);
					        jList3.repaint(rect);
			        	}
			        }
			        Rectangle rect = jList3.getCellBounds(index, index);
			        jList3.repaint(rect);
			      }
			    }); 
		}
		return jList3;
	}
	class CheckListRenderer extends JCheckBox implements ListCellRenderer
	{

		public CheckListRenderer()
		{
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus)
		{
			setEnabled(list.isEnabled());
			CheckableItem item = (CheckableItem)value;
			setSelected(item.isSelected());
			this.setSize(350,17);
			setText("  "+item.getCode());
			return this;
		}
	}
	/**

	 * This method initializes jPage4	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPage4() {
		if (jPage4 == null) {
			jPage4 = new JPanel();
			jPage4.setLayout(null);
			jPage4.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPage4.setSize(525, 254);
			jPage4.add(getJComboBox(), null);
			jPage4.add(getJComboBox1(), null);
			jPage4.add(getJComboBox2(), null);
			jPage4.add(getJComboBox3(), null);
			jPage4.add(getJComboBox4(), null);
			jPage4.add(getJComboBox5(), null);
			jPage4.add(getJComboBox6(), null);
			jPage4.add(getJComboBox7(), null);
			jPage4.add(getJScrollPane5(), null);
			jPage4.add(getJButton(), null);
		}
		return jPage4;
	}

	/**

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(87, 8, 96, 22);
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
			jComboBox1.setBounds(189, 8, 106, 22);
			jComboBox1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    if (DgViewEdit.this.jComboBox1.getSelectedItem()!=null && !DgViewEdit.this.jComboBox1.getSelectedItem().equals("")){
					DbCommon.getComFields(vector,DgViewEdit.this.jComboBox1.getSelectedItem().toString(),
							DgViewEdit.this.jComboBox2);
                    }
				}
			});

		}
		return jComboBox1;
	}

	/**

	 * This method initializes jComboBox2	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox2() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(300, 8, 107, 22);
		}
		return jComboBox2;
	}

	/**

	 * This method initializes jComboBox3	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox3() {
		if (jComboBox3 == null) {
			jComboBox3 = new JComboBox();
			jComboBox3.setBounds(15, 35, 67, 22);
		}
		return jComboBox3;
	}

	/**

	 * This method initializes jComboBox4	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox4() {
		if (jComboBox4 == null) {
			jComboBox4 = new JComboBox();
			jComboBox4.setBounds(87, 35, 96, 22);
			jComboBox4.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					 if (DgViewEdit.this.jComboBox4.getSelectedItem()!=null && !DgViewEdit.this.jComboBox4.getSelectedItem().equals("")){
						DbCommon.getComFields(vector,DgViewEdit.this.jComboBox4.getSelectedItem().toString(),
								DgViewEdit.this.jComboBox5);
	                    }
				}
			});

		}
		return jComboBox4;
	}

	/**

	 * This method initializes jComboBox5	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox5() {
		if (jComboBox5 == null) {
			jComboBox5 = new JComboBox();
			jComboBox5.setBounds(189, 35, 106, 22);
		}
		return jComboBox5;
	}

	/**

	 * This method initializes jComboBox6	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox6() {
		if (jComboBox6 == null) {
			jComboBox6 = new JComboBox();
			jComboBox6.setBounds(300, 35, 107, 22);
		}
		return jComboBox6;
	}

	/**

	 * This method initializes jComboBox7	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox7() {
		if (jComboBox7 == null) {
			jComboBox7 = new JComboBox();
			jComboBox7.setBounds(15, 8, 67, 22);
		}
		return jComboBox7;
	}

	/**

	 * This method initializes jScrollPane5	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setBounds(10, 63, 503, 165);
			jScrollPane5.setViewportView(getJTextArea());
		}
		return jScrollPane5;
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

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(414, 35, 47, 22);
			jButton.setText("+");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) { 
					String whsql = jComboBox7.getSelectedItem().toString()+" "+
					jComboBox.getSelectedItem().toString()+
					jComboBox1.getSelectedItem().toString()+"."+jComboBox2.getSelectedItem().toString()+
					jComboBox3.getSelectedItem().toString()+
					jComboBox4.getSelectedItem().toString()+"."+jComboBox5.getSelectedItem().toString()+
					jComboBox6.getSelectedItem().toString();
					if (!DgViewEdit.this.jTextArea.getText().equals("")){	
					  DgViewEdit.this.jTextArea.setText(DgViewEdit.this.jTextArea.getText()+"\n"+" "+whsql);
					} else {
						DgViewEdit.this.jTextArea.setText(whsql);
					}
				   jComboBox7.setSelectedIndex(-1);jComboBox.setSelectedIndex(-1);jComboBox1.setSelectedIndex(-1);
				   jComboBox2.setSelectedIndex(-1);jComboBox3.setSelectedIndex(-1);jComboBox4.setSelectedIndex(-1);
				   jComboBox5.setSelectedIndex(-1);jComboBox6.setSelectedIndex(-1);
				}
			});

		}
		return jButton;
	}

	/**

	 * This method initializes jRadioButton	

	 * 	

	 * @return javax.swing.JRadioButton	

	 */    
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setBounds(234, 17, 61, 21);
			jRadioButton.setText("升序");
			jRadioButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (jRadioButton.isSelected()){
					  setOrderBy("asc");
					} else {
						setOrderBy("desc");
					}
				}
			});

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
			jRadioButton1.setBounds(234, 36, 60, 21);
			jRadioButton1.setText("降序");
			jRadioButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (jRadioButton1.isSelected()){
						  setOrderBy("desc");
						} else {
							setOrderBy("asc");
						}

				}
			});

		}
		return jRadioButton1;
	}

	/**
	 * @return Returns the orderBy.
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy The orderBy to set.
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**

	 * This method initializes jPage5	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPage5() {
		if (jPage5 == null) {
			jPage5 = new JPanel();
			jPage5.setLayout(null);
			jPage5.setSize(525, 254);
			jPage5.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPage5.add(getJButton5(), null);
			jPage5.add(getJButton6(), null);
			jPage5.add(getJScrollPane6(), null);
			jPage5.add(getJScrollPane7(), null);
			jPage5.add(getJButton7(), null);
		}
		return jPage5;
	}

	/**

	 * This method initializes jButton5	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(13, 5, 66, 26);
			jButton5.setText("验证");
			jButton5.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {   
					String sql = DgViewEdit.this.sqlTextPane.getText();
					sql = dataImportAction.formatSql(new Request(CommonVars.getCurrUser()),sql);
					System.out.println("-- Sql:"+sql);
					if (textSQL(sql)){
						JOptionPane.showMessageDialog(DgViewEdit.this,"SQL语句验证成功！", "提示！", 2);
						//new showFiles().start();
					}
				}
			});

		}
		return jButton5;
	}
	class TxtDataRunnable extends Thread {
		public void run() {
			  try {
				  CommonProgress.showProgressDialog(DgViewEdit.this);
				  CommonProgress.setMessage("系统正在读取资料，请稍后...");							  				
				  String sql = DgViewEdit.this.sqlTextPane.getText();
				  sql = dataImportAction.formatSql(new Request(CommonVars.getCurrUser()),sql);
					System.out.println("-- Sql:"+sql);
					int columnCount;
					try {
						columnCount = dataImportAction.getFieldsPart(new Request(CommonVars.getCurrUser()),db,sql);	
					    Vector vector = new Vector();						
					    Vector columns = new Vector();
					    jTable.setColumnModel(new DefaultTableColumnModel());
					    
					    columns  = dataImportAction.getFieldName2(new Request(CommonVars.getCurrUser()),db,DgViewEdit.this.sqlTextPane.getText(),columnCount);	
					    
					    vector = dataImportAction.getTableColumn(new Request(CommonVars.getCurrUser()),db,sql,columnCount);
					    TableModel  dm =  new DefaultTableModel(vector,columns);
						jTable1.setModel(dm);
					} catch (SQLException e) {
						CommonProgress.closeProgressDialog();
						System.out.println("读取数据出错："+e.getMessage());
					}
					
			  } finally {
				  CommonProgress.closeProgressDialog();
			  }
		}
	}
	

	/**

	 * This method initializes jButton6	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setBounds(83, 5, 66, 26);
			jButton6.setText("保存");
			jButton6.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					//String sql  = DgViewEdit.this.sqlTextPane.getText();
                    //sql = dataImportAction.formatSql(new Request(CommonVars.getCurrUser()),sql);
                    //测试速度慢给添加语句慢
                    //if (textSQL(sql)){
						DgViewSave dg = new DgViewSave();
						dg.setDbDataRoot(db);
						dg.setAdd(true);
						dg.setSqlStr(DgViewEdit.this.sqlTextPane.getText());
						dg.setVisible(true);
						if (dg.isOk()){
							tableModel1.addRow(dg.getDb());
							JOptionPane.showMessageDialog(DgViewEdit.this,"视图添加成功!", "提示", 2);
							dispose();				
						}
                   // }
				}
			});

		}
		return jButton6;
	}

	/**

	 * This method initializes jTable1	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
			jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		}
		return jTable1;
	}

	/**

	 * This method initializes jScrollPane6	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane6() {
		if (jScrollPane6 == null) {
			jScrollPane6 = new JScrollPane();
			jScrollPane6.setBounds(13, 37, 499, 139);
			jScrollPane6.setViewportView(getJTable1());
		}
		return jScrollPane6;
	}

	/**

	 * This method initializes jScrollPane7	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane7() {
		if (jScrollPane7 == null) {
			jScrollPane7 = new JScrollPane();
			jScrollPane7.setBounds(13, 177, 500, 69);
			jScrollPane7.setViewportView(getSqlTextPane());
		}
		return jScrollPane7;
	}

	/**
	 * @param where The where to set.
	 */
	public void setWhere(String where) {
		this.where = where;
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
	private void clearAll(){
		tableSource.removeAllElements();
		tableDest.removeAllElements();
		vector.removeAllElements();
		vf.removeAllElements();
		groupSource.removeAllElements();
		groupDest.removeAllElements();
		orderSource.removeAllElements();
		orderDest.removeAllElements();
		where.equals("");
	}
	private boolean textSQL(String sql){
		String textSql = dataImportAction.textSql(new Request(CommonVars.getCurrUser()),db,sql);
		if (textSql.equals("成功")){
			return true;
		} else {
			JOptionPane.showMessageDialog(DgViewEdit.this,textSql, "提示！", 2);
			return false;
		}
	}
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseClicked(java.awt.event.MouseEvent e) {

					if (e.getClickCount() == 2) {
						if (!checkDbIsConnect()){
							return;
						}
						step++;
	                    gotoStep(step);
					}
				}
			});
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
			jScrollPane.setBounds(26, 52, 191, 186);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jButton7	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton7() {
		if (jButton7 == null) {
			jButton7 = new JButton();
			jButton7.setBounds(155, 5, 90, 26);
			jButton7.setText("显示数据");
			jButton7.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
                    String sql  = DgViewEdit.this.sqlTextPane.getText();
                    sql = dataImportAction.formatSql(new Request(CommonVars.getCurrUser()),sql);
                    System.out.println("-- Sql:"+sql);
					if (textSQL(sql)){
						new TxtDataRunnable().start();
					}					
				}
			});
		}
		return jButton7;
	}
	class showFiles extends Thread {
		public void run() {
			  try {
				  CommonProgress.showProgressDialog(DgViewEdit.this);
				  CommonProgress.setMessage("系统正在读取资料，请稍后...");	
		          String sql = DgViewEdit.this.sqlTextPane.getText();
		          int columnCount;
			      columnCount = dataImportAction.getFieldsPart(new Request(CommonVars.getCurrUser()),db,DgViewEdit.this.sqlTextPane.getText());	
		          Vector vector = new Vector();						
		          Vector columns = new Vector();
		          jTable.setColumnModel(new DefaultTableColumnModel());
		          
		          columns  = dataImportAction.getFieldName2(new Request(CommonVars.getCurrUser()),db,DgViewEdit.this.sqlTextPane.getText(),columnCount);	
		          
		         vector = new Vector();
		         TableModel  dm =  new DefaultTableModel(vector,columns);
			     jTable1.setModel(dm);
		    } catch (Exception e){
		    	CommonProgress.closeProgressDialog();
			    System.out.println("读取数据出错："+e.getMessage());
		    } finally {
				  CommonProgress.closeProgressDialog();
		    }
		}
	}
	
	/**
	 * This method initializes jButton8	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setBounds(395, 9, 76, 22);
			jButton8.setText("完成");
			jButton8.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (!checkDbIsConnect()){
						return;
					}
					step = 6;
					pnContext.removeAll();
			        pnContext.add(getJPage5(), null);
			        getJPage5().setVisible(true);
			        jLabel.setText("第七步：验证保存");
			        pnContext.repaint();
			        pnContext.validate();
			        setState();
				}
			});
		}
		return jButton8;
	}
	/**
	 * This method initializes sqlTextPane	
	 * 	
	 * @return com.bestway.ui.editor.SQLTextPane	
	 */
	private SQLTextPane getSqlTextPane() {
		if (sqlTextPane == null) {
			sqlTextPane = new SQLTextPane();
		}
		return sqlTextPane;
	}
	public DBDataRoot getDbDataRoot() {
		return dbDataRoot;
	}
	public void setDbDataRoot(DBDataRoot dbDataRoot) {
		this.dbDataRoot = dbDataRoot;
	}
    } 
 