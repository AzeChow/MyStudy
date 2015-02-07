/*
 * Created on 2004-7-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.checkcancel;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JButton;

import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.CheckExg;
import com.bestway.bcus.checkcancel.entity.CheckHead;
import com.bestway.bcus.checkcancel.entity.CheckImg;
import com.bestway.bcus.checkcancel.entity.ImportDataForCheckImg;
import com.bestway.bcus.checkcancel.entity.ImportDataForFactoryPd;
import com.bestway.bcus.client.checkcancel.FmFactoryCheck.TxtDataRunnable;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ContractKind;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JComboBox;
import java.awt.Rectangle;
/**
 * @author Administrator
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgCheck extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JToolBar jToolBar = null;
	private JButton jbAdd = null;
	private JButton jbEdit = null;
	private JButton jbDelete = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JTextField jTextField = null;
	private JTextField jTextField1 = null;
	private JTextField jTextField2 = null;
	private JTextField jTextField3 = null;
	private JTextField jTextField4 = null;
	private JTextField jTextField6 = null;
	private JButton jButton3 = null;
	private JTable jTableImg = null;
	private JScrollPane jScrollPane = null;
	private JTable jTableExg = null;
	private JScrollPane jScrollPane1 = null;
	private JButton jButton4 = null;
	private JButton jbSave = null;
	private Customs custom = null; //海关关区
	private CheckCancelAction checkCancelAction = null;
	private ManualDeclareAction manualDeclearAction = null;
	private CheckHead checkHead = null; //中期核查表头
	private int dataState = DataState.BROWSE;
	private JTableListModel tableModel = null;

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;
	private JCalendarComboBox jCalendarComboBox = null;

	private JButton jButton = null;
	private File file1 = null;
	private Hashtable gbHash = null;

	private JLabel jLabel7 = null;

	private JComboBox jComboBox = null;

	private JLabel jLabel8 = null;
	/**
	 * This is the default constructor
	 */
	public DgCheck() {
		super();
		checkCancelAction = (CheckCancelAction) CommonVars
		.getApplicationContext().getBean("checkCancelAction");
		manualDeclearAction = (ManualDeclareAction) CommonVars
		.getApplicationContext().getBean("manualdeclearAction");
		
		initialize();
		setState();
		
	}
	
   private void initCombom(){
	   jComboBox.removeAllItems();
	   jComboBox.addItem(new ItemProperty("1", "中期核查"));
	   jComboBox.addItem(new ItemProperty("2", "库存调整"));
	   jComboBox.setRenderer(CustomBaseRender.getInstance().getRender());
	   CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(jComboBox);
   }
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("中期核查维护");
		this.setSize(666, 421);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowOpened(java.awt.event.WindowEvent e) {
				initCombom();
				if (tableModel.getCurrentRow() != null) {
					checkHead = (CheckHead) tableModel.getCurrentRow();
					custom = checkHead.getMasterCustoms();
				}
				fillWindow();
				List dataSourceImg = null;
				dataSourceImg = checkCancelAction.findCheckImg(
						new Request(CommonVars.getCurrUser()), checkHead);
				if (dataSourceImg!=null && !dataSourceImg.isEmpty())
			    	initTableImg(dataSourceImg);
				else
					initTableImg(new Vector());
				//删除了tab中期核查成品
//			    List dataSourceExg = null;
//				dataSourceExg = checkCancelAction.findCheckExg(
//						new Request(CommonVars.getCurrUser()), checkHead);
//				if (dataSourceExg!=null && !dataSourceExg.isEmpty() && dataSourceExg.size()!=0)
//				    initTableExg(dataSourceExg);
//				else
//					initTableExg(new Vector());
				dataState = DataState.EDIT;
				
			}
		});
	}
	private void initTableImg(final List list) {
		tableModelImg = new JTableListModel(jTableImg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("帐册序号", "seqNum", 50,Integer.class));
						list.add(addColumn("1.料号", "ptNo", 100));
						list.add(addColumn("料件名称", "name", 120));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("规格型号", "spec", 120));
						list.add(addColumn("备案计量单位", "unit.name", 100));
						list.add(addColumn("2.原料库存数量", "materStock", 100));
						list.add(addColumn("3.原料在途数量", "materByWay", 100));
						list.add(addColumn("4.成品折料数量", "passExgUse", 100));
						list.add(addColumn("5.在线数量", "onlines", 100));
						list.add(addColumn("6.废品,残次品折料数量", "otherConvert", 120));
						list.add(addColumn("7.废料数量", "depose", 100));
						list.add(addColumn("8.转进未报数量", "turnInNoReport", 100));				
						return list;
					}
				});
	}
	private void initTableExg(final List list) {
		tableModelExg = new JTableListModel(jTableExg, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("帐册序号", "seqNum", 50, Integer.class));
						list.add(addColumn("版本号", "version", 80));
						list.add(addColumn("成品号/货号", "materiel.ptNo", 100));
						list.add(addColumn("成品名称", "materiel.factoryName", 120));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("规格型号", "materiel.factorySpec", 120));
						list.add(addColumn("备案计量单位", "unit.name", 80));
						return list;
					}
				});
	}	
	private void fillWindow() {
		jTextField.setText(checkHead.getEmsNo());
		jTextField1.setText(checkHead.getTradeCode());
		jTextField3.setText(checkHead.getMachCode());
		jTextField2.setText(checkHead.getTradeName());
		jTextField4.setText(checkHead.getMachName());
		if (checkHead.getMasterCustoms()!=null)
	    	jTextField6.setText(checkHead.getMasterCustoms().getName()+
	    			"("+checkHead.getMasterCustoms().getCode()+")");
		else
			jTextField6.setText("");
		
		jCalendarComboBox.setDate(checkHead.getHead()==null?null:checkHead.getHead().getBeginDate());
		
		if (checkHead.getFlagHg() != null) {
			jComboBox.setSelectedIndex(ItemProperty.getIndexByCode(checkHead.getFlagHg(), jComboBox));
		} else {
			jComboBox.setSelectedIndex(-1);
		}
		
		
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**

	 * This method initializes jToolBar	

	 * 	

	 * @return javax.swing.JToolBar	

	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJbAdd());
			jToolBar.add(getJbEdit());
			jToolBar.add(getJbDelete());
			jToolBar.add(getJbSave());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton4());
		}
		return jToolBar;
	}

	/**

	 * This method initializes jbAdd	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJbAdd() {
		if (jbAdd == null) {
			jbAdd = new JButton();
			jbAdd.setText("新增");
			jbAdd.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					if (jTabbedPane.getSelectedIndex() == 1)//料件
					{
						CheckImg checkImg = null;
						List list = (List) CommonQuery.getInstance()
								.getImgFromInnerMerge(   // 来自内部归并关系已备案
										false, checkHead);
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							checkImg = (CheckImg) list.get(i);
							checkImg.setCheckHead(checkHead);
							checkImg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							checkImg = checkCancelAction
									.saveCheckImg(new Request(CommonVars
											.getCurrUser()), checkImg);
							tableModelImg.addRow(checkImg);
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) //成品
					{
						CheckExg checkExg = null;
						List list = (List) CommonQuery.getInstance()
								.getExgFromInnerMerge(   // 来自内部归并关系已备案
										false, checkHead);
						if (list == null || list.isEmpty())
							return;
						for (int i = 0; i < list.size(); i++) {
							checkExg = (CheckExg) list.get(i);
							checkExg.setCheckHead(checkHead);
							checkExg.setCompany(CommonVars.getCurrUser()
									.getCompany());
							checkExg = checkCancelAction
									.saveCheckExg(new Request(CommonVars
											.getCurrUser()), checkExg);
							tableModelExg.addRow(checkExg);
						}
					}
					setState();
				}
			});

		}
		return jbAdd;
	}

	/**

	 * This method initializes jbEdit	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJbEdit() {
		if (jbEdit == null) {
			jbEdit = new JButton();
			jbEdit.setText("修改");
			jbEdit.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (jTabbedPane.getSelectedIndex() == 0) {
						dataState = DataState.EDIT;
						setState();
					} else					
						if (jTabbedPane.getSelectedIndex() == 1)//料件
						{
							DgCheckImg dgCheckImg = new DgCheckImg();
							if (tableModelImg.getCurrentRow() == null){
								JOptionPane.showMessageDialog(DgCheck.this, "请选择你将要修改的记录",
										"提示！", 0);
								return;
							}
							dgCheckImg.setTableModel(tableModelImg);
							dgCheckImg.setVisible(true);
				     	} else if (jTabbedPane.getSelectedIndex() == 2) {//成品
				     	 	DgCheckExg dgCheckExg = new DgCheckExg();
							if (tableModelExg.getCurrentRow() == null){
								JOptionPane.showMessageDialog(DgCheck.this, "请选择你将要修改的记录",
										"提示！", 0);
								return;
							}
							dgCheckExg.setTableModel(tableModelExg);
							dgCheckExg.setVisible(true);
				     	}
				}
			});

		}
		return jbEdit;
	}

	/**

	 * This method initializes jbDelete	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJbDelete() {
		if (jbDelete == null) {
			jbDelete = new JButton();
			jbDelete.setText("删除");
			jbDelete.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					new deleteAll().start();
				}
			});

		}
		return jbDelete;
	}

	
	
	class deleteAll extends Thread{
		public void run(){
			try{				
				if (jTabbedPane.getSelectedIndex() == 1)//料件
				{
					if (tableModelImg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgCheck.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					List list = tableModelImg.getCurrentRows();
					if (JOptionPane.showConfirmDialog(DgCheck.this,
							"你确定要删除此记录吗?", "确认", 0) == 0) {
						CommonProgress.showProgressDialog(DgCheck.this);
						CommonProgress.setMessage("系统正在删除数据，请稍候.....");
						checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
						tableModelImg.deleteRows(list);
						CommonProgress.closeProgressDialog();
					}
				} else if (jTabbedPane.getSelectedIndex() == 2) {//成品
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgCheck.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					List list = tableModelExg.getCurrentRows();
					if (JOptionPane.showConfirmDialog(DgCheck.this,
							"你确定要删除此记录吗?", "确认", 0) == 0) {
						CommonProgress.showProgressDialog(DgCheck.this);
						CommonProgress.setMessage("系统正在删除数据，请稍候.....");
						checkCancelAction.deleteList(new Request(CommonVars.getCurrUser()),list);
						tableModelExg.deleteRows(list);
						CommonProgress.closeProgressDialog();
					}
				}
				setState();				
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCheck.this,"删除失败：！"+e.getMessage(),"提示",2);
			}
		}
	}
	/**

	 * This method initializes jTabbedPane	

	 * 	

	 * @return javax.swing.JTabbedPane	

	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
			jTabbedPane.addTab("中期核查表头", null, getJPanel(), null);
			jTabbedPane.addTab("中期核查料件", null, getJPanel1(), null);
			//jTabbedPane.addTab("中期核查成品", null, getJPanel2(), null);
			jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() { 

				public void stateChanged(javax.swing.event.ChangeEvent e) {    

					setState();

				}
			});

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
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(85,247,291,18));
			jLabel8.setText("注：蓝色字体为必填项");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(357,95,70,18));
			jLabel7.setForeground(java.awt.Color.blue);
			jLabel7.setText("申报类型");
			javax.swing.JLabel jLabel6 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();

			javax.swing.JLabel jLabel4 = new JLabel();

			javax.swing.JLabel jLabel3 = new JLabel();

			javax.swing.JLabel jLabel2 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(87, 93, 62, 18);
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setText("电子帐册号");
			jLabel1.setBounds(87, 126, 78, 19);
			jLabel1.setForeground(java.awt.Color.blue);
			jLabel1.setText("经营单位代码");
			jLabel2.setBounds(87, 160, 75, 20);
			jLabel2.setForeground(java.awt.Color.blue);
			jLabel2.setText("经营单位名称");
			jLabel3.setBounds(357, 126, 78, 22);
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setText("加工单位代码");
			jLabel4.setBounds(358, 161, 74, 20);
			jLabel4.setForeground(java.awt.Color.blue);
			jLabel4.setText("加工单位名称");
			jLabel5.setBounds(87, 192, 77, 21);
			jLabel5.setForeground(java.awt.Color.blue);
			jLabel5.setText("本期起始日期");
			jLabel6.setBounds(357, 194, 77, 20);
			jLabel6.setForeground(java.awt.Color.blue);
			jLabel6.setText("主管海关代码");
			jPanel.add(jLabel, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField1(), null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(getJTextField6(), null);
			jPanel.add(getJButton3(), null);
			jPanel.add(getJCalendarComboBox(), null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel8, null);
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

	 * This method initializes jPanel2	

	 * 	

	 * @return javax.swing.JPanel	

	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
			jTextField.setBounds(169, 93, 116, 22);
			jTextField.setEditable(false);
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
			jTextField1.setBounds(169, 126, 116, 22);
			jTextField1.setEditable(false);
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
			jTextField2.setBounds(169, 160, 117, 22);
			jTextField2.setEditable(false);
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
			jTextField3.setBounds(439, 125, 116, 22);
			jTextField3.setEditable(false);
		}
		return jTextField3;
	}

	/**

	 * This method initializes jTextField4	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField4() {
		if (jTextField4 == null) {
			jTextField4 = new JTextField();
			jTextField4.setBounds(440, 160, 115, 22);
			jTextField4.setEditable(false);
		}
		return jTextField4;
	}

	/**

	 * This method initializes jTextField6	

	 * 	

	 * @return javax.swing.JTextField	

	 */    
	private JTextField getJTextField6() {
		if (jTextField6 == null) {
			jTextField6 = new JTextField();
			jTextField6.setBounds(439, 193, 115, 22);
			jTextField6.setEditable(false);
		}
		return jTextField6;
	}

	/**

	 * This method initializes jButton3	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(554, 193, 21, 21);
			jButton3.setText("...");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					Customs c = (Customs) CommonQuery.getInstance()
					  .getCustoms();
		         	if (c != null) {
				     custom = c;
				     getJTextField6().setText(c.getName()+"("+c.getCode()+")");
			      }
				}
			});

		}
		return jButton3;
	}

	/**

	 * This method initializes jTableImg	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTableImg() {
		if (jTableImg == null) {
			jTableImg = new JTable();
		}
		return jTableImg;
	}

	/**

	 * This method initializes jScrollPane	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTableImg());
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jTableExg	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTableExg() {
		if (jTableExg == null) {
			jTableExg = new JTable();
		}
		return jTableExg;
	}

	/**

	 * This method initializes jScrollPane1	

	 * 	

	 * @return javax.swing.JScrollPane	

	 */    
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTableExg());
		}
		return jScrollPane1;
	}

	/**

	 * This method initializes jButton4	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("关闭");
			jButton4.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    

					DgCheck.this.dispose();

				}
			});

		}
		return jButton4;
	}

	/**

	 * This method initializes jbSave	

	 * 	

	 * @return javax.swing.JButton	

	 */    
	private JButton getJbSave() {
		if (jbSave == null) {
			jbSave = new JButton();
			jbSave.setText("保存");
			jbSave.addActionListener(new java.awt.event.ActionListener() { 

				public void actionPerformed(java.awt.event.ActionEvent e) {    
					checkHead.setMasterCustoms(custom);
					if (jComboBox.getSelectedItem() != null) {
						checkHead.setFlagHg(((ItemProperty) jComboBox.getSelectedItem())
								.getCode());
					} else {
						checkHead.setFlagHg(null);
					}
					checkHead = checkCancelAction.saveCheckHead(new Request(CommonVars.getCurrUser()),checkHead);
					tableModel.updateRow(checkHead);
					dataState = DataState.BROWSE;
					setState();

				}
			});

		}
		return jbSave;
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
	 * @return Returns the tableModelExg.
	 */
	public JTableListModel getTableModelExg() {
		return tableModelExg;
	}
	/**
	 * @param tableModelExg The tableModelExg to set.
	 */
	public void setTableModelExg(JTableListModel tableModelExg) {
		this.tableModelExg = tableModelExg;
	}
	/**
	 * @return Returns the tableModelImg.
	 */
	public JTableListModel getTableModelImg() {
		return tableModelImg;
	}
	/**
	 * @param tableModelImg The tableModelImg to set.
	 */
	public void setTableModelImg(JTableListModel tableModelImg) {
		this.tableModelImg = tableModelImg;
	}
	/**

	 * This method initializes jCalendarComboBox	

	 * 	

	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox	

	 */    
	private JCalendarComboBox getJCalendarComboBox() {
		if (jCalendarComboBox == null) {
			jCalendarComboBox = new JCalendarComboBox();
			jCalendarComboBox.setEnabled(false);
			jCalendarComboBox.setBounds(169, 191, 118, 22);
		}
		return jCalendarComboBox;
	}
	private void setState() {
		jbAdd.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)); //新增
		jbEdit.setEnabled((dataState == DataState.BROWSE && (jTabbedPane
				.getSelectedIndex() == 0))
				|| (isImgExgPageAndExistData()));
		jbDelete.setEnabled(isImgExgPageAndExistData()); //删除
		jbSave.setEnabled((jTabbedPane.getSelectedIndex() == 0)
				&& dataState != DataState.BROWSE);       //保存
		//jCalendarComboBox.setEnabled(dataState != DataState.BROWSE);
		jButton3.setEnabled(dataState != DataState.BROWSE);
		jButton.setEnabled(!(jTabbedPane.getSelectedIndex() == 0)); //导入
		jComboBox.setEnabled(dataState != DataState.BROWSE);
	}
	private boolean isImgExgPageAndExistData() {
		if ((jTabbedPane.getSelectedIndex() == 1)
				&& (tableModelImg.getRowCount() > 0)) {
			return true;
		}
		if ((jTabbedPane.getSelectedIndex() == 2)
				&& (tableModelExg.getRowCount() > 0)) {
			return true;
		}
		return false;
	}
	
	
    //调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				String suffix = getSuffix(f);
				if (f.isDirectory() == true) {
					return true;
				}
				if (suffix != null) {
					if (suffix.toLowerCase().equals("txt")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			public String getDescription() {
				return "*.txt";
			}

			private String getSuffix(File f) {
				String s = f.getPath(), suffix = null;
				int i = s.lastIndexOf('.');
				if (i > 0 && i < s.length() - 1)
					suffix = s.substring(i + 1).toLowerCase();
				return suffix;
			}
		});
		int state = fileChooser.showOpenDialog(DgCheck.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}
//	private void infTojHsTable() {
//		if (gbHash == null) {
//			gbHash = new Hashtable();
//		}
//		List list = CustomBaseList.getInstance().getGbtobigs();
//		for (int i = 0; i < list.size(); i++) {
//			Gbtobig gb = (Gbtobig) list.get(i);
//			gbHash.put(gb.getBigname(), gb.getName());
//		}
//	}
//	private String changeStr(String s) { // 简转繁
//		String yy = "";
//		char[] xxx = s.toCharArray();
//		for (int i = 0; i < xxx.length; i++) {
//			String z = String.valueOf(xxx[i]);
//			if (String.valueOf(xxx[i]).getBytes().length == 2) {
//				if (gbHash.get(String.valueOf(xxx[i])) != null) {
//					z = (String) gbHash.get(String.valueOf(xxx[i]));
//				}
//			}
//			yy = yy + z;
//		}
//		return yy;
//	}
	
    //读取并检查数据
	private List parseTxtFile(File file) {
		//boolean ischange = true;
		//infTojHsTable();
		BufferedReader in;
		String[] strs = null;
		List <ImportDataForCheckImg>list = new ArrayList<ImportDataForCheckImg>();  // 字段所有的值
		try {
			in = new BufferedReader(new FileReader(file));
			String s = new String();
			try {
				while ((s = in.readLine()) != null) {
					ImportDataForCheckImg data = new ImportDataForCheckImg(); // 每个字段对应的值
					if (s.trim().equals("")) {
						continue;
					}
					/*if (ischange) {
						s = changeStr(s);
					}*/
					strs = s.split(String.valueOf((char) 9));
					data.setPtNo(getStr(strs,0));
			        data.setMaterStock(forD(getStr(strs,1)));
			        data.setMaterByWay(forD(getStr(strs,2)));
			        data.setPassExgUse(forD(getStr(strs,3)));
			        data.setOnlines(forD(getStr(strs,4)));
			        data.setOtherConvert(forD(getStr(strs,5)));
			        data.setDepose(forD(getStr(strs,6)));
			        data.setTurnInNoReport(forD(getStr(strs,7)));
					list.add(data);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private Double forD(Object obj){
		try{
			return Double.valueOf(obj.toString());
		} catch (Exception e){
			return Double.valueOf(0);
		}
	}
	
	private String getStr(String[] strs,int i){
		int len = strs.length;
		if (i<=len-1){
			return strs[i];
		}
		return "";
	}
	
	
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("文本导入");
			jButton.setForeground(java.awt.Color.blue);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					file1 = getFile();
					List list = null;
					list = parseTxtFile(file1);
					System.out.println("list.size:"+list.size());
					new TxtDataRunnable(list).start();
					
					
				}
			});
		}
		return jButton;
	}	
	
	
	
	class TxtDataRunnable extends Thread {
		private List list = null;
		public TxtDataRunnable(List list){
			this.list = list;
		}		
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgCheck.this);
				List sList = checkCancelAction.importDataToCheck(new Request(CommonVars.getCurrUser()),list,checkHead);
				List errorlist = (List)sList.get(1);
				List rightlist = (List) sList.get(0);
				BillTemp b = (BillTemp) sList.get(2);				
				String ErrorStr = "";
				if (errorlist != null && errorlist.size()>0){
					ErrorStr = "温馨提示：以上物料有："+b.getBill2()+"    笔记录因不存在归并关系归并前即不可导入，请先备案归并关系归并前料件！\n\n";
				}
				ErrorStr += "读入文本总数："+b.getBill1()+"        可更新到核查表数："+b.getBill3()+"        可新增到核查表数："+b.getBill4();
				CommonProgress.closeProgressDialog();
				DgCheckError dg = new DgCheckError();
				dg.setErrorType("1");
				dg.setList(errorlist);
				dg.setErrorStr(ErrorStr);
				dg.setVisible(true);
				if (dg.isOk()){
					CommonProgress.showProgressDialog(DgCheck.this);
					if (rightlist != null || rightlist.size()>0){
					   checkCancelAction.saveDataToCheck(new Request(CommonVars.getCurrUser()),rightlist,checkHead);
					   CommonProgress.closeProgressDialog();
					   JOptionPane.showMessageDialog(DgCheck.this,"导入完毕！\n\n" +
					   		"文本总数：    "+b.getBill1()+"        \n" +
					   		"未导入数：    "+b.getBill2()+"        \n"+
					   		"已更新到核查表数："+b.getBill3()+"        \n" +
					   		"已新增到核查表数："+b.getBill4(),"提示",2);
					   List dataSourceImg = null;
					   dataSourceImg = checkCancelAction.findCheckImg(
					   new Request(CommonVars.getCurrUser()), checkHead);
					   if (dataSourceImg!=null && !dataSourceImg.isEmpty()){
					    	initTableImg(dataSourceImg);
					   }  else {
							initTableImg(new Vector());
					   }
					   setState();
					}
				}
			} catch (Exception e){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgCheck.this,"导入出错，请与百思维客服人员联系！","提示",2);
			}
		}
	}



	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(439,92,115,22));
		}
		return jComboBox;
	}
	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
