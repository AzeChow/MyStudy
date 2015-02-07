
/*
 * Created on 2004-9-21
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.dataimport;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.dataimport.entity.FieldList;
import com.bestway.bcus.dataimport.entity.TxtFormat;
import com.bestway.bcus.dataimport.entity.TxtTask;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.Gbflag;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author lin
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgTxtFormatFieldSetup extends JDialogBase {
	
	private DataImportAction dataImportAction = null;

	private JPanel jContentPane = null;

	private JPanel jPanelSouth = null;

	private JPanel jPanelWest = null;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel1 = null;

	private JTable jTable1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTextField jfSort = null;

	private JComboBox jfFunc = null;

	private JTextField jfFielddesc = null;

	private JTextField jfFieldname = null;

	private JTextField jfFieldlen = null;

	private JPanel jPanel4 = null;

	private JPanel jPanel5 = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;

	private JButton jButton6 = null;

	private JButton jButton7 = null;

	private JButton jButton8 = null;

	private JButton jButton9 = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModel1 = null;

	private TxtFormat txtFormat = null; //字段设置

	private TxtTask txtTask = null; //格式

	private JComboBox jComboBox = null;
	private javax.swing.JLabel jLabel6 = new JLabel();
	private int dataState = DataState.BROWSE;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JComboBox jComboBox1 = null;
	private JCheckBox jCheckBox = null;
	private JComboBox jComboBox2 = null;
	/**
	 * @param owner
	 */
	public DgTxtFormatFieldSetup() {
		super();
		initialize();
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		initData();
		initUI();
		setState();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		this.setTitle("文本导入数据中心");
		this.setContentPane(getJPanel());
		this.setSize(612, 436);

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
		//字段类型
		this.jComboBox2.removeAllItems();
        this.jComboBox2.addItem("String");
        this.jComboBox2.addItem("Double");
        this.jComboBox2.addItem("Integer");
        this.jComboBox2.addItem("Boolean");
        this.jComboBox2.addItem("Date");
        this.jComboBox2.addItem("double");
        this.jComboBox2.addItem("对象");
        this.jComboBox2.setUI(new CustomBaseComboBoxUI(112));
    }
	private void initData() {
		List list = dataImportAction.findTxtTask(new Request(CommonVars
				.getCurrUser()));
		if (list!=null && !list.isEmpty()){
			initTable(list);
			txtTask = (TxtTask) list.get(0);
			this.jLabel6.setText("导入文本格式设置--"+txtTask.getTaskname());
			List listDetail = dataImportAction.findTxtFormat(new Request(
					CommonVars.getCurrUser()), txtTask); //显示字段设置
			if (listDetail!=null && !listDetail.isEmpty()){
				initDetailTable(listDetail);	
				txtFormat = (TxtFormat) listDetail.get(0);
				setStateDetail(txtFormat);
			} else {
				initDetailTable(new Vector());
				clearData();
			}
			
		} else {
			initTable(new Vector());
			initDetailTable(new Vector());
			clearData();
		}
	}

	
	private void initTable(List dataSource) {
		tableModel = new JTableListModel(jTable, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();						
						list.add(addColumn("目标表", "classList.name", 110));
						list.add(addColumn("格式名称", "taskname", 100));
						list.add(addColumn("其它说明", "memo", 100));
						list.add(addColumn("转换标致", "gbflag", 100));
						list.add(addColumn("任务创建者", "creator", 100));
						list.add(addColumn("任务修改者", "editor", 100));
						list.add(addColumn("建立日期", "createdate", 100));
						return list;
					}
				});

	}

	private void initDetailTable(List dataSource) {
		tableModel1 = new JTableListModel(jTable1, dataSource,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("字段序号", "sortno", 80));
						list.add(addColumn("字段名称", "name", 100));
						list.add(addColumn("字段类型", "fieldtype", 100));
						list.add(addColumn("字段长度", "fieldlen", 100));
						list.add(addColumn("字段说明", "fielddesc", 100));
						list.add(addColumn("是否主键", "iskey", 80));
						list.add(addColumn("格式创建者", "creator", 80));
						list.add(addColumn("格式修改者", "editor", 80));
						list.add(addColumn("建立日期", "createdate", 80));
						return list;
					}
				});
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout(0, 1));
			jContentPane.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.WEST);
			jContentPane.add(getJPanel12(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanelSouth == null) {
			jPanelSouth = new JPanel();
			jPanelSouth.setLayout(new BorderLayout());
			jPanelSouth.setPreferredSize(new java.awt.Dimension(10, 80));
			jPanelSouth.setBackground(java.awt.Color.white);
			jPanelSouth.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			jPanelSouth.add(getJPanel5(), java.awt.BorderLayout.CENTER);
			jPanelSouth.add(getJPanel4(), java.awt.BorderLayout.WEST);
		}
		return jPanelSouth;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanelWest == null) {
			jPanelWest = new JPanel();
			jPanelWest.setLayout(new BorderLayout());
			jPanelWest.setPreferredSize(new java.awt.Dimension(150, 10));
			jPanelWest.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "文本导入格式设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanelWest.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanelWest;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private void setStateDetail(TxtFormat currentTxtForm) {
		jfSort.setText(String.valueOf(currentTxtForm.getSortno()));
		if (currentTxtForm.getIsNull().equals(new Boolean(true))){
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}
		jfFieldname.setText(currentTxtForm.getFieldname());
		jComboBox2.setSelectedItem(currentTxtForm.getFieldtype());
		jfFieldlen.setText(String.valueOf(currentTxtForm.getFieldlen()));
		jfFielddesc.setText(currentTxtForm.getFielddesc());
		if (currentTxtForm.getGbflag()!=null){
		  this.jComboBox.setSelectedIndex(
		  		ItemProperty.getIndexByCode(currentTxtForm.getGbflag(), jComboBox));
		} else {
			this.jComboBox.setSelectedIndex(-1);
		}
		if (currentTxtForm.getGbflag().equals(Gbflag.OBJ)){
			this.jComboBox1.setEnabled(true);		
		} else {
			this.jComboBox1.setEnabled(false);
		}
		if (currentTxtForm.getGbflag().equals(Gbflag.OBJ)){
	    	initGbstr(currentTxtForm);
		} else {
			this.jComboBox1.setModel(new DefaultComboBoxModel(new Vector().toArray()));
		}
		/*if (currentTxtForm.getIsNull().equals(new Boolean(true))){
			this.jCheckBox.setSelected(true);
		} else {
			this.jCheckBox.setSelected(false);
		}*/

	}

	private void initGbstr(TxtFormat currentTxtForm){
		List list = dataImportAction.findFieldList(new Request(CommonVars
				.getCurrUser()),currentTxtForm.getObjName());
		this.jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"sortno", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "sortno", "name");
		this.jComboBox1.setSelectedIndex(-1);
		if (currentTxtForm.getGbStr()!=null){
			jComboBox1.setSelectedItem(currentTxtForm.getGbStr());
		} else {
			jComboBox1.setSelectedItem(null);
		}
	}
    private void initGbstrToBox(TxtFormat currentTxtForm){
    	List list = dataImportAction.findFieldList(new Request(CommonVars
				.getCurrUser()),currentTxtForm.getObjName());
		this.jComboBox1.setModel(new DefaultComboBoxModel(list.toArray()));
		this.jComboBox1.setRenderer(CustomBaseRender.getInstance().getRender(
				"sortno", "name"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.jComboBox1, "sortno", "name");
		this.jComboBox1.setSelectedIndex(-1);
    }
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel == null) {

			javax.swing.JLabel jLabel7 = new JLabel();

			javax.swing.JLabel jLabel5 = new JLabel();
			javax.swing.JLabel jLabel4 = new JLabel();
			javax.swing.JLabel jLabel2 = new JLabel();
			javax.swing.JLabel jLabel1 = new JLabel();
			javax.swing.JLabel jLabel = new JLabel();
			javax.swing.JLabel jLabel3 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setPreferredSize(new java.awt.Dimension(10, 50));
			jPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "字段设置编辑", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12), new java.awt.Color(51,51,51)));
			jLabel4.setBounds(213, 48, 59, 15);
			jLabel4.setText("字段名称");
			jLabel5.setBounds(214, 77, 63, 16);
			jLabel5.setText("字段长度");
			jLabel3.setBounds(16, 48, 56, 17);
			jLabel3.setText("序号");
			jLabel.setBounds(16, 77, 55, 18);
			jLabel.setText("字段类型");
			jLabel1.setBounds(16, 105, 58, 16);
			jLabel1.setText("转换方式");
			jLabel2.setBounds(16, 132, 58, 15);
			jLabel2.setText("字段说明");
			jLabel7.setBounds(214, 107, 66, 20);
			jLabel7.setText("导入对象项");
			jLabel6.setBounds(16, 25, 266, 16);
			jLabel6.setText("文本导入格式设置--");
			jLabel6.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			jLabel6.setForeground(new java.awt.Color(255,153,51));
			jPanel.add(jLabel3, null);
			jPanel.add(getJTextField(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(getJTextField2(), null);
			jPanel.add(jLabel4, null);
			jPanel.add(getJTextField3(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getJTextField4(), null);
			jPanel.add(getJComboBox2(), null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(getJComboBox1(), null);
			jPanel.add(getJCheckBox(), null);
			jPanel.add(getJComboBox22(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel23() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());

			jPanel2.setPreferredSize(new java.awt.Dimension(10, 50));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "导入字段设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel12() {
		if (jPanel1 == null) {
			java.awt.GridLayout gridLayout1 = new GridLayout(0, 1);
			jPanel1 = new JPanel();
			jPanel1.setLayout(gridLayout1);
			jPanel1.add(getJPanel3(), null);
			jPanel1.add(getJPanel23(), null);
		}
		return jPanel1;
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
		jTable1.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {

						try {
							if (tableModel1.getCurrentRow() != null)
								txtFormat = (TxtFormat) tableModel1
								    .getCurrentRow();
								setStateDetail(txtFormat);
							    setState();
						} catch (NullPointerException e) {
						}
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
			jScrollPane1.setViewportView(getJTable1());
			jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField() {
		if (jfSort == null) {
			jfSort = new JTextField();
			jfSort.setBounds(82, 48, 118, 22);
		}
		return jfSort;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2() {
		if (jfFielddesc == null) {
			jfFielddesc = new JTextField();
			jfFielddesc.setBounds(81, 132, 220, 22);
		}
		return jfFielddesc;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField3() {
		if (jfFieldname == null) {
			jfFieldname = new JTextField();
			jfFieldname.setBounds(286, 48, 113, 22);
		}
		return jfFieldname;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField4() {
		if (jfFieldlen == null) {
			jfFieldlen = new JTextField();
			jfFieldlen.setBounds(287, 77, 113, 22);
		}
		return jfFieldlen;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setPreferredSize(new java.awt.Dimension(180, 10));
			jPanel4.setBackground(java.awt.Color.white);
			jPanel4.add(getJButton(), null);
			jPanel4.add(getJButton1(), null);
			jPanel4.add(getJButton2(), null);
			jPanel4.add(getJButton3(), null);
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
			jPanel5.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
			jPanel5.setBackground(java.awt.Color.white);
			jPanel5.add(getJButton4(), null);
			jPanel5.add(getJButton5(), null);
			jPanel5.add(getJButton8(), null);
			jPanel5.add(getJButton6(), null);
			jPanel5.add(getJButton7(), null);
			jPanel5.add(getJButton9(), null);
		}
		return jPanel5;
	}

	private void clearData() {
		jfSort.setText("");
		jfFieldname.setText("");
		jComboBox2.setSelectedIndex(-1);
		jfFieldlen.setText("");
		jfFielddesc.setText("");
		jfFielddesc.setText("");
		this.jComboBox.setSelectedItem("无");
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("增加格式");
			jButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					DgTxtFormatAdd dgTxtFormatAdd = new DgTxtFormatAdd();
					dgTxtFormatAdd.setAdd(true);
					dgTxtFormatAdd.setTableModel(tableModel);
					dgTxtFormatAdd.setVisible(true);
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
			jButton1.setText("修改格式");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtFormatFieldSetup.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					DgTxtFormatAdd dgTxtFormatAdd = new DgTxtFormatAdd();
					dgTxtFormatAdd.setAdd(false);
					dgTxtFormatAdd.setTableModel(tableModel);
					dgTxtFormatAdd.setVisible(true);
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
			jButton2.setText("删除格式");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtFormatFieldSetup.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

				    txtTask = (TxtTask) tableModel.getCurrentRow();
					if (JOptionPane.showConfirmDialog(DgTxtFormatFieldSetup.this,
							"将该格式字段设置一并删除，确定删除吗？", "确认", 0) == 0) {
						dataImportAction.deleteAllFormat(new Request(CommonVars
								.getCurrUser()), txtTask);
						dataImportAction.deleteTxtTask(new Request(CommonVars
								.getCurrUser()), txtTask);
						tableModel.deleteRow(txtTask);
					    clearData();
					    initDetailTable(new Vector());
					    DgTxtFormatFieldSetup.this.jLabel6.setText("导入文本格式设置--");
						setState();
					}

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
			jButton3.setText("刷        新");
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("新增字段");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtFormatFieldSetup.this,
								"请选择导入格式的资料", "确认", 2);
						return;
					}
					TxtFormat txtFormat = null;
					List list = (List) CommonQuery.getInstance().getFieldList(
							false,txtTask.getClassList(),txtTask);
					if (list == null || list.isEmpty())
						return;
					for (int i = 0; i < list.size(); i++) {
						txtFormat = (TxtFormat) list.get(i);
						txtFormat.setTxttask(txtTask);
						txtFormat.setSortno(Integer.valueOf(dataImportAction
			                    .getFieldFormatNum(new Request(CommonVars.getCurrUser()),
			                            "TxtFormat", txtTask)));
						txtFormat.setGbflag(Gbflag.NO);
						txtFormat.setCreatedate(CommonVars.dateToStandDate(new Date()));
						txtFormat.setCreator(CommonVars.getCurrUser().getUserName());
						txtFormat.setCompany(CommonVars.getCurrUser()
								.getCompany());
						txtFormat = dataImportAction.saveTxtFormat(
								new Request(CommonVars.getCurrUser()),
								txtFormat);
						tableModel1.addRow(txtFormat);
					}
					setState();
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
			jButton5.setText("修改字段");
			jButton5.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/*if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtFormatFieldSetup.this,
								"请选择导入格式的资料", "确认", 2);
						return;
					}*/
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtFormatFieldSetup.this,
								"请选择导入字段的资料", "确认", 2);
						return;
					}
					dataState = DataState.EDIT;
					setState();
					//jTable1.setEnabled(false);
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
	private void fillFormatData(TxtFormat txtFormat) {
		txtFormat.setTxttask(txtTask);
		txtFormat.setFieldname(jfFieldname.getText().trim());
		txtFormat.setSortno(Integer.valueOf(jfSort.getText()));
		if (this.jComboBox2.getSelectedItem()!=null){
		    txtFormat.setFieldtype(this.jComboBox2.getSelectedItem().toString());
		} else {
			txtFormat.setFieldtype("String");
		}
		txtFormat.setFieldlen(Integer.valueOf(jfFieldlen.getText()));
		txtFormat.setFielddesc(jfFielddesc.getText().trim());
		txtFormat.setEditor(CommonVars.getCurrUser().getUserName());
		if (this.jComboBox.getSelectedItem()!=null){
		   txtFormat.setGbflag(((ItemProperty) this.jComboBox.getSelectedItem())
				.getCode());
		} else {
			txtFormat.setGbflag("0");
		}
		if (this.jCheckBox.isSelected()){
		    txtFormat.setIsNull(new Boolean(true));   
		} else {
			txtFormat.setIsNull(new Boolean(false));
		}
		txtFormat.setGbStr((FieldList)jComboBox1.getSelectedItem());
	}

	private void modifyFormatData() {
		fillFormatData(txtFormat);
		txtFormat = dataImportAction.saveTxtFormat(new Request(
				CommonVars.getCurrUser()), txtFormat);
		tableModel1.updateRow(txtFormat);
	}

	private JButton getJButton6() {
		jButton6 = new JButton();
		jButton6.setText("保        存");
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyFormatData();
				dataState = DataState.BROWSE;
				setState();
				jTable1.setEnabled(true);
			}
		});

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
			jButton7.setText("取        消");
			jButton7.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {

					jTable1.setEnabled(true);
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return jButton7;
	}

	/**
	 * This method initializes jButton8
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton8() {
		if (jButton8 == null) {
			jButton8 = new JButton();
			jButton8.setText("删除字段");
			jButton8.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tableModel1.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgTxtFormatFieldSetup.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}

					TxtFormat txtFormat = (TxtFormat) tableModel1
							.getCurrentRow();
					if (JOptionPane.showConfirmDialog(DgTxtFormatFieldSetup.this,
							"你确定要删除此记录吗?", "确认", 0) == 0) {
						dataImportAction.deleteTxtFormat(new Request(CommonVars
								.getCurrUser()), txtFormat);
						tableModel1.deleteRow(txtFormat);
						if (tableModel1.getRowCount()==0){
							clearData();
						}
					}
					dataState = DataState.BROWSE;
					setState();
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
			jButton9.setText("存为规范");
			jButton9.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					JFileChooser jfc = new JFileChooser();
	                if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {    
					try {
						File write = new File(jfc.getSelectedFile().getPath()+".txt");
						BufferedWriter bw = null;
						try {
							bw = new BufferedWriter(new FileWriter(write));
						    List list = dataImportAction.findTxtFormat(new Request(
								    CommonVars.getCurrUser()), txtTask); //显示字段设置
						    bw.write("格式名称:  "+txtTask.getTaskname()+"    目标表:  "+txtTask.getClassList().getName()+"\r\n");
						    bw.write("┏━━━┯━━━━━━━┯━━━━━━━┯━━━━┯━━━━┯━━━━┯━━━━━━━━━━━━━━━━━━━━┓\r\n");
						    bw.write("┃序号  │字段名称      │字段类型      │字段长度│是否主键│是否为空│其他说明                                ┃\r\n");
						    for (int i=0;i<list.size();i++){ //行
					           TxtFormat txt = (TxtFormat)list.get(i);
					           bw.write("┠───┼───────┼───────┼────┼────┼────┼────────────────────┨\r\n");
					    	   bw.write("┃"+writeNull(String.valueOf(txt.getSortno()),6)+
					    	   		"│"+writeNull(txt.getName(),14)+
					    	   		"│"+writeNull(txt.getFieldtype(),14)+
					    	        "│"+writeNull(String.valueOf(txt.getFieldlen()),8)+
					    	   		"│"+writeNull(change(txt.getIskey()),8)+
					    	   		"│"+writeNull(change(txt.getIsNull()),8)+
					    	   		"│"+writeNull(txt.getFielddesc(),40)+"┃\r\n");	  	  
					        }	
						    bw.write("┗━━━┷━━━━━━━┷━━━━━━━┷━━━━┷━━━━┷━━━━┷━━━━━━━━━━━━━━━━━━━━┛\r\n");
						    bw.write("注意：文本资料的第一行必须是您设置的文本导入任务的名称；文本资料中必须删除掉栏位名称行！");
					        bw.close();
					       
						} catch (FileNotFoundException e2) {
							e2.printStackTrace();
						}	
					 } catch (IOException e1) {
						e1.printStackTrace();
					 }
	                }
				}
		});
		}
		return jButton9;
	
	}
	/**

	 * This method initializes jComboBox	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox2() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(82, 105, 119, 22);
			jComboBox.addMouseListener(new java.awt.event.MouseAdapter() { 

				public void mouseClicked(java.awt.event.MouseEvent e) {    
					if (DgTxtFormatFieldSetup.this.jComboBox.getSelectedItem()==null){
						return;
					}
					if (!((ItemProperty) DgTxtFormatFieldSetup.this.jComboBox.getSelectedItem())
							.getCode().equals(Gbflag.OBJ)){
						DgTxtFormatFieldSetup.this.jComboBox1.setSelectedIndex(-1);
					}			   

				}
			    
			});
			jComboBox.addItemListener(new java.awt.event.ItemListener() { 
				public void itemStateChanged(java.awt.event.ItemEvent e) {
			    	 if (tableModel1.getCurrentRow() != null && jComboBox.getSelectedItem()!= null){
						 txtFormat = (TxtFormat) tableModel1
						    .getCurrentRow();
						 if (((ItemProperty) DgTxtFormatFieldSetup.this.jComboBox.getSelectedItem()).getCode().equals(Gbflag.OBJ)){
						 	initGbstrToBox(txtFormat);
						 } else {
							jComboBox1.setModel(new DefaultComboBoxModel(new Vector().toArray()));
						 }
				   }
			    	 setState();	
	               }
				
				});
		}
		return jComboBox;
	}
   private void setState(){
   	jButton1.setEnabled(gettableModel()); //修改格式
   	jButton2.setEnabled(gettableModel()); //删除格式
   	jButton4.setEnabled(gettableModel()); //新增字段
   	jButton5.setEnabled(gettableModel1() && dataState == DataState.BROWSE); //修改字段
   	jButton8.setEnabled(gettableModel1() && dataState == DataState.BROWSE); //删除字段
   	jButton6.setEnabled(gettableModel1() && !(dataState == DataState.BROWSE));//保存
   	jButton7.setEnabled(gettableModel1() && !(dataState == DataState.BROWSE));//取消
   	jButton9.setEnabled(tableModel1.getRowCount()>0 && dataState == DataState.BROWSE);//存为规范
   	jfSort.setEditable(gettableModel1() && !(dataState == DataState.BROWSE));
   	jfFieldname.setEditable(gettableModel1() && !(dataState == DataState.BROWSE));
   	jComboBox2.setEnabled(gettableModel1() && !(dataState == DataState.BROWSE));
	jfFieldlen.setEditable(gettableModel1() && !(dataState == DataState.BROWSE));
	jComboBox.setEnabled(gettableModel1() && !(dataState == DataState.BROWSE));
	jCheckBox.setEnabled(gettableModel1() && !(dataState == DataState.BROWSE));
	jComboBox1.setEnabled(gettableModel1() && !(dataState == DataState.BROWSE) );//&& ((TxtFormat) tableModel1.getCurrentRow()).getGbflag().equals(Gbflag.OBJ)
	jfFielddesc.setEditable(gettableModel1() && !(dataState == DataState.BROWSE));
   }
   private boolean gettableModel(){
   	if (tableModel.getCurrentRow()!=null && tableModel.getRowCount()>0) {
   		return true;
   	}
   	return false;
   }
   private boolean gettableModel1(){
   	if (tableModel1.getCurrentRow()!=null && tableModel1.getRowCount()>0){
   		return true;
   	}
   	return false;
   }
	/**

	 * This method initializes jTable	

	 * 	

	 * @return javax.swing.JTable	

	 */    
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setIntercellSpacing(new java.awt.Dimension(0,0));
			jTable.setShowHorizontalLines(false);
			jTable.setShowVerticalLines(false);
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
                        txtTask = (TxtTask) tableModel.getCurrentRow();
                        List listDetail = dataImportAction.findTxtFormat(new Request(
            					CommonVars.getCurrUser()), txtTask); //显示字段设置
            			if (listDetail!=null && !listDetail.isEmpty()){
            				initDetailTable(listDetail);
            				txtFormat = (TxtFormat) listDetail.get(0);
            				setStateDetail(txtFormat);
            			} else {
            				initDetailTable(new Vector());
            				clearData();
            			}
                        setState();
                        DgTxtFormatFieldSetup.this.jLabel6.setText("导入文本格式设置--"+((TxtTask)tableModel.getCurrentRow()).getTaskname());
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
		}
		return jScrollPane;
	}

	/**

	 * This method initializes jComboBox1	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(287, 106, 112, 21);
			jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() { 

				public void propertyChange(java.beans.PropertyChangeEvent e) { 
					if ((e.getPropertyName().equals("model"))) { 
						System.out.println("propertyChange(model)"); //  
					} 
				}
			});

		}
		return jComboBox1;
	}

	/**

	 * This method initializes jCheckBox	

	 * 	

	 * @return javax.swing.JCheckBox	

	 */    
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(311, 132, 88, 21);
			jCheckBox.setText("是否为空");
		}
		return jCheckBox;
	}

	/**

	 * This method initializes jComboBox2	

	 * 	

	 * @return javax.swing.JComboBox	

	 */    
	private JComboBox getJComboBox22() {
		if (jComboBox2 == null) {
			jComboBox2 = new JComboBox();
			jComboBox2.setBounds(82, 77, 119, 21);
		}
		return jComboBox2;
	}
	private String writeNull(String ss,int i){
		String yy = ss;
		if (ss.getBytes().length<i){
			return yy = ss+writeN(i-ss.getBytes().length);//
		}
		return yy;		
	}
  
   private String writeN(int i){
   	  int k = 0;
   	  String ss="";
   	  while ( k<i) {
   	  	ss=ss+" ";
   	  	k++;
   	  }
   	  return ss;
   }
   private String change(Boolean yy){
	if (yy.equals(new Boolean(true))){
		return "是";
	} else {
		return "否";
	}
}
} //  @jve:decl-index=0:visual-constraint="40,7"
