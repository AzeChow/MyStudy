/*
 * Created on 2004-10-9
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.finance;


import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.CheckMaster;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgFinanceCheck extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JButton jButton = null;
	private JComboBox jComboBox = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private CommonBaseCodeAction commonBaseCodeAction = null;
	private CasAction casAction = null;	
	private JTableListModel tableModel = null;  
	private List list = null;
	private String materielType = null;
	/**
	 * This is the default constructor
	 */
	public DgFinanceCheck() {
		super();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
	       .getApplicationContext().getBean("commonBaseCodeAction");
        casAction = (CasAction) CommonVars.getApplicationContext()
	       .getBean( "casAction");
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(731, 541);
		this.setTitle("盘点表");
		this.setContentPane(getJContentPane());
		
	}
    
    
    
    public void setVisible(boolean isFlag){
        if(isFlag){
            initUI();
            list = casAction.findCheckMaster(new Request(
                 CommonVars.getCurrUser()),String.valueOf(MaterielType.MATERIEL));
            if (list!=null && !list.isEmpty())
            {
              initTable(list);
            } else {
              initTable(new Vector());
            }
        }
        super.setVisible(isFlag);
    }
    
    
   
    
    
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("流水号", "num", 80));
						list.add(addColumn("类型", "masterielType", 100));
						list.add(addColumn("盘点时间", "checkDate", 80));
						list.add(addColumn("仓库名称", "wareSet.name", 80));
						list.add(addColumn("备注", "note", 120));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(2).setCellRenderer(
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
						if (value.equals(String.valueOf(MaterielType.MATERIEL))) {
							returnValue = "原材料";
						} else if (value.equals(String.valueOf(MaterielType.FINISHED_PRODUCT))) {
							returnValue = "产成品";
						} else if (value.equals(String.valueOf(MaterielType.REMAIN_MATERIEL))) {
							returnValue = "边角料";
						} else if (value.equals(String.valueOf(MaterielType.BAD_PRODUCT))) {
							returnValue = "残次品";
						} else if (value.equals(String.valueOf(MaterielType.MACHINE))) {
							returnValue = "设备";
						}
						return returnValue;
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
			jSplitPane.setDividerSize(2);
			jSplitPane.setDividerLocation(50);
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
			jPanel.setLayout(null);
			jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel.setBackground(java.awt.Color.white);
			jPanel.add(getJButton(), null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(getJButton1(), null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jButton	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(11, 13, 89, 22);
			jButton.setText("增加单据");
			jButton.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    CheckMaster checkMaster = new CheckMaster();
                    checkMaster.setNum(Integer.valueOf(casAction.getNum(new Request(
								CommonVars.getCurrUser()),"CheckMaster")));
                    checkMaster.setMasterielType(Integer.valueOf(DgFinanceCheck.this.materielType));
                    Date date = new Date();
                    checkMaster.setCheckDate(CommonVars.dateToStandDate(date));
                    checkMaster.setIsDelete(new Boolean(false));
                    checkMaster.setCompany(CommonVars.getCurrUser().getCompany());
                    checkMaster = casAction.saveCheckMasters(new Request(
								CommonVars.getCurrUser()),checkMaster);
                    tableModel.addRow(checkMaster);
                    edit();

				}
			});

		}
		return jButton;
	}

	/**

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(107, 13, 107, 22);
			jComboBox.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (DgFinanceCheck.this.jComboBox.getSelectedItem() != null) {
						DgFinanceCheck.this.setMaterielType(((ItemProperty) DgFinanceCheck.this.jComboBox
								.getSelectedItem()).getCode());
						list = casAction.findCheckMaster(new Request(
								CommonVars.getCurrUser()),String.valueOf(((ItemProperty) DgFinanceCheck.this.jComboBox
										.getSelectedItem()).getCode()));
						   if (list!=null && !list.isEmpty())
						   {
						     initTable(list);
						   } else {
						     initTable(new Vector());
						   }	
					}
					
				}
			});

		}
		return jComboBox;
	}

	/**

	 * This method initializes jButton1	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(250, 13, 75, 21);
			jButton1.setText("修改");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFinanceCheck.this, "请选择你将要修改的记录",
								"提示！", 0);
						return;
					}
					edit();
				}
			});

		}
		return jButton1;
	}
    private void edit(){
    	DgFinanceCheckEdit dg = new DgFinanceCheckEdit();
		dg.setMaterielType(((ItemProperty) DgFinanceCheck.this.jComboBox
				.getSelectedItem()).getName());
		dg.setMaterielTypeCode(((ItemProperty) DgFinanceCheck.this.jComboBox
				.getSelectedItem()).getCode());
		dg.setTableModelMaster(tableModel);
		dg.setVisible(true);
    }
	/**

	 * This method initializes jButton2	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(330, 14, 75, 21);
			jButton2.setText("作废");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgFinanceCheck.this, "请选择你将要修改的记录",
								"提示！", 2);
						return;
					}
					CheckMaster checkMaster = (CheckMaster) tableModel.getCurrentRow();
					checkMaster.setIsDelete(new Boolean(true));
					checkMaster = casAction.saveCheckMasters(new Request(CommonVars.getCurrUser()),checkMaster);
					tableModel.deleteRow(checkMaster);
					
				}
			});

		}
		
		return jButton2;
	}
    private void initUI(){
    	this.jComboBox.removeAllItems();
		this.jComboBox.addItem(new ItemProperty(String.valueOf(MaterielType.MATERIEL), "原材料"));
		this.jComboBox.addItem(new ItemProperty(String.valueOf(MaterielType.FINISHED_PRODUCT), "产成品"));
		this.jComboBox.addItem(new ItemProperty(String.valueOf(MaterielType.REMAIN_MATERIEL), "边角料"));
		this.jComboBox.addItem(new ItemProperty(String.valueOf(MaterielType.BAD_PRODUCT), "残次品"));
		this.jComboBox.addItem(new ItemProperty(String.valueOf(MaterielType.MACHINE), "设备"));
		this.jComboBox
				.setRenderer(CustomBaseRender.getInstance().getRender());
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox);
    }
	/**

	 * This method initializes jButton3	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(409, 14, 75, 21);
			jButton3.setText("关闭");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgFinanceCheck.this.dispose();

				}
			});

		}
		return jButton3;
	}

	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}
	/**
	 * @param materielType The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
	}
       }  //  @jve:decl-index=0:visual-constraint="10,10"
