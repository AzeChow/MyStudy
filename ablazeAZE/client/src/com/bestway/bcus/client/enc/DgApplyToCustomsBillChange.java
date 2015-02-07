package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.enc.DgApplyToCustomsBillList.MultiRenderer;
import com.bestway.bcus.client.manualdeclare.EmsEdiMergerClientLogic;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ApplyToCustomsBillList;
import com.bestway.bcus.enc.entity.AtcMergeAfterComInfo;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerImgAfter;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JCheckBox;

public class DgApplyToCustomsBillChange extends JDialogBase {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JSplitPane jSplitPane = null;
	private JSplitPane jSplitPane1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JComboBox jComboBox1 = null;
	private JSplitPane jSplitPane2 = null;
	private JSplitPane jSplitPane3 = null;
	private JPanel jPanel6 = null;
	private JPanel jPanel7 = null;
	private JPanel jPanel8 = null;
	private JPanel jPanel9 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane1 = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane2 = null;
	private JTable jTable2 = null;
	private JScrollPane jScrollPane3 = null;
	private JTable jTable3 = null;
	private int flat = -1;
	private EncAction encAction = null;
	private JTableListModel afterCommInfoTableModel = null;
	private JTableListModel afterCommInfoTableModel1 = null;
	
	private JTableListModel beforeCommInfoTableModel = null;
	private JTableListModel beforeCommInfoTableModel1 = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel2 = null;
	private AtcMergeAfterComInfo after = null;
	private AtcMergeAfterComInfo after1 = null;
	private JButton jButton2 = null;
	/**
	 * This is the default constructor
	 */
	public DgApplyToCustomsBillChange() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
		             "encAction");
		initialize();
	}

    //初始化Combox
    public void initCombox(List list){
    	this.jComboBox.removeAllItems();
    	this.jComboBox1.removeAllItems();
    	for (int i=0;i<list.size();i++){
    		ApplyToCustomsBillList temp = (ApplyToCustomsBillList) list.get(i);
    		if (temp.getListNo() == null){
    			continue;
    		}
    		this.jComboBox.addItem(temp.getListNo());
    		this.jComboBox1.addItem(temp.getListNo());
    	}
    	this.jComboBox.setSelectedIndex(-1);
    	this.jComboBox1.setSelectedIndex(-1);
    }
    
    
    
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(765, 576);
		this.setContentPane(getJContentPane());
		this.setTitle("清单调整");
		jCheckBox.setSelected(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				 List list = encAction.findApplyToCustomsBillListByFlatAndState(new Request(CommonVars.getCurrUser()),flat,ApplyToCustomsBillList.DRAFT);
				 initCombox(list);
				 initAfterTable(new Vector());
				 initAfterTable1(new Vector());
				 initBeforeTable(new Vector());
				 initBeforeTable1(new Vector());
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
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(361,325,51,24));
			jLabel2.setForeground(new java.awt.Color(204,51,0));
			jLabel2.setText("显示所有");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJButton2(), null);
		}
		return jContentPane;
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
			jPanel.setBounds(new java.awt.Rectangle(0,1,344,540));
			jPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jPanel1.setBounds(new java.awt.Rectangle(413,1,345,541));
			jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel1.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
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
			jButton.setBounds(new java.awt.Rectangle(351,173,56,48));
			jButton.setForeground(java.awt.Color.blue);
			jButton.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 36));
			jButton.setText(">");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if (afterCommInfoTableModel == null) //左边归并后
						return;
					after = (AtcMergeAfterComInfo) afterCommInfoTableModel.getCurrentRow();
					if (after == null){
						return;
					}
					//--------------------------------------------------
					if (jComboBox1.getSelectedItem() == null || jComboBox1.getSelectedItem().equals("")){
						JOptionPane.showMessageDialog(DgApplyToCustomsBillChange.this,"请选择右边清单号！","提示",2);
						return;
					}
					String billNo = (String) jComboBox1.getSelectedItem(); //右边清单号
					if (after.getBillList().getListNo().equals(billNo)){
						JOptionPane.showMessageDialog(DgApplyToCustomsBillChange.this,"两边清单相同！","提示",2);
						return;
					}
					if (afterCommInfoTableModel1.getList().size()>20){
						if (JOptionPane.showConfirmDialog(DgApplyToCustomsBillChange.this,
								"要添加的清单号已经超过20项，是否继续添加！", "确认", 0) != 0) {
							return;
						}
					}
					encAction.addBillToBill(new Request(CommonVars.getCurrUser()),after,billNo);
					//--------------------------------------------------
					afterCommInfoTableModel1.addRow(after);//右边新增
					initdata();
				    initdata1();
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
			jButton1.setBounds(new java.awt.Rectangle(351,239,56,48));
			jButton1.setForeground(java.awt.Color.blue);
			jButton1.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 36));
			jButton1.setText("<");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (afterCommInfoTableModel1 == null)//右边归并后
						return;
					after1 = (AtcMergeAfterComInfo) afterCommInfoTableModel1.getCurrentRow();
					if (after1 == null){
						return;
					}
					//-------------------------------------------------
					if (jComboBox.getSelectedItem() == null || jComboBox.getSelectedItem().equals("")){
						JOptionPane.showMessageDialog(DgApplyToCustomsBillChange.this,"请选择左边清单号！","提示",2);
						return;
					}
					String billNo = (String) jComboBox.getSelectedItem(); //左边清单号
					if (after1.getBillList().getListNo().equals(billNo)){
						JOptionPane.showMessageDialog(DgApplyToCustomsBillChange.this,"两边清单相同！","提示",2);
						return;
					}
					if (afterCommInfoTableModel.getList().size()>20){
						if (JOptionPane.showConfirmDialog(DgApplyToCustomsBillChange.this,
								"要添加的清单号已经超过20项，是否继续添加！", "确认", 0) != 0) {
							return;
						}
					}
					encAction.addBillToBill(new Request(CommonVars.getCurrUser()),after1,billNo);
					//--------------------------------------------------
					initdata();
				    initdata1();
				}
			});
		}
		return jButton1;
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
			jSplitPane.setDividerSize(0);
			jSplitPane.setTopComponent(getJPanel2());
			jSplitPane.setBottomComponent(getJPanel4());
			jSplitPane.setDividerLocation(40);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerSize(0);
			jSplitPane1.setTopComponent(getJPanel3());
			jSplitPane1.setBottomComponent(getJPanel5());
			jSplitPane1.setDividerLocation(40);
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(6,7,100,22));
			jLabel.setText("请选择清单号：");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel2.add(getJComboBox(), null);
			jPanel2.add(jLabel, null);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(7,8,106,22));
			jLabel1.setText("请选择清单号：");
			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel3.add(jLabel1, null);
			jPanel3.add(getJComboBox1(), null);
		}
		return jPanel3;
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
			jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel4.add(getJSplitPane2(), java.awt.BorderLayout.CENTER);
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
			jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, null));
			jPanel5.add(getJSplitPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new java.awt.Rectangle(105,5,207,27));
			jComboBox.setEditable(true);
			jComboBox.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(ActionEvent e) {   					
					initdata();
				}
			});
		}
		return jComboBox;
	}
	
	private void initdata(){
		if (jComboBox.getSelectedItem() == null || jComboBox.getSelectedItem().equals("")){//右边
			initAfterTable(new Vector());
			initBeforeTable(new Vector());
			return;
		}
		String billNo = (String) jComboBox.getSelectedItem();
		List listafter = encAction.findAtcMergeAfterComInfoByBillNo(new Request(CommonVars.getCurrUser()),billNo);
		initAfterTable(listafter);
		if (listafter != null && listafter.size()>0){
		    after = (AtcMergeAfterComInfo) listafter.get(0);
		    List listbefore = null;
		    if (jCheckBox.isSelected()){
		    	listbefore = encAction.findAllAtcMergerBeforeComInfo(new Request(CommonVars.getCurrUser()),billNo);
		    } else{
		        listbefore = encAction.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars.getCurrUser()),after);
		    }
		    initBeforeTable(listbefore);
		} else {
			initBeforeTable(new Vector());
		}
	}
	
	
    //初始化归并后
	private void initAfterTable(List list) {
		afterCommInfoTableModel = new JTableListModel(jTable,
				list, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSerialNo", 60));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "complexName", 100));
						list.add(addColumn("商品规格", "complexSpec", 100));						
						list.add(addColumn("申报数量", "declaredAmount", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						return list;
					}
				});
	}
	
	 //初始化归并后
	private void initAfterTable1(List list) {
		afterCommInfoTableModel1 = new JTableListModel(jTable1,
				list, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("帐册序号", "emsSerialNo", 60));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "complexName", 100));
						list.add(addColumn("商品规格", "complexSpec", 100));						
						list.add(addColumn("申报数量", "declaredAmount", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						return list;
					}
				});
	}
	

	
	//需要修改
	private void initBeforeTable(List list) {
		beforeCommInfoTableModel = new JTableListModel(jTable2,
				list, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("商品序号", "serialNo", 80));
						list.add(addColumn("帐册序号", "afterComInfo.emsSerialNo", 80));
						list.add(addColumn("商品货号", "materiel.ptNo", 100));
						list.add(addColumn("对应报关单商品号", "customsNo", 120));
						list.add(addColumn("商品编码", "afterComInfo.complex.code", 80));
						list.add(addColumn("商品名称", "materiel.factoryName", 120));
						list.add(addColumn("型号规格", "materiel.factorySpec", 120));
						list.add(addColumn("企业申报单价", "declaredPrice", 100));
						list.add(addColumn("申报数量", "declaredAmount", 100));
						list.add(addColumn("法定数量", "legalAmount", 100));
						list.add(addColumn("第二法定数量", "secondLegalAmount", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("成品版本号", "version", 80));						
						list.add(addColumn("备注", "memos", 120));
						list.add(addColumn("事业部", "projectDept.name", 120));
						return list;
					}
				});
		jTable2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
    //需要修改
	private void initBeforeTable1(List list) {
		beforeCommInfoTableModel1 = new JTableListModel(jTable3,
				list, new JTableListModelAdapter() {
					public List InitColumns() {
						List list = new Vector();
						list.add(addColumn("商品序号", "serialNo", 80));
						list.add(addColumn("帐册序号", "afterComInfo.emsSerialNo", 80));
						list.add(addColumn("商品货号", "materiel.ptNo", 100));
						list.add(addColumn("对应报关单商品号", "customsNo", 120));
						list.add(addColumn("商品编码", "afterComInfo.complex.code", 80));
						list.add(addColumn("商品名称", "materiel.factoryName", 120));
						list.add(addColumn("型号规格", "materiel.factorySpec", 120));
						list.add(addColumn("企业申报单价", "declaredPrice", 100));
						list.add(addColumn("申报数量", "declaredAmount", 100));
						list.add(addColumn("法定数量", "legalAmount", 100));
						list.add(addColumn("第二法定数量", "secondLegalAmount", 100));
						list.add(addColumn("毛重", "grossWeight", 100));
						list.add(addColumn("净重", "netWeight", 100));
						list.add(addColumn("成品版本号", "version", 80));						
						list.add(addColumn("备注", "memos", 120));
						list.add(addColumn("事业部", "projectDept.name", 120));
						return list;
					}
				});
		jTable3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1() {
		if (jComboBox1 == null) {
			jComboBox1 = new JComboBox();
			jComboBox1.setBounds(new java.awt.Rectangle(111,6,202,26));
			jComboBox1.setEditable(true);
			jComboBox1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(ActionEvent e) {   					
					
					initdata1();
				}
			});
		}
		return jComboBox1;
	}
	
	private void initdata1(){
		if (jComboBox1.getSelectedItem() == null || jComboBox1.getSelectedItem().equals("")){//左边
			initAfterTable1(new Vector());
			initBeforeTable1(new Vector());
			return;
		}
		String billNo = (String) jComboBox1.getSelectedItem();//左边清单号
		List listafter = encAction.findAtcMergeAfterComInfoByBillNo(new Request(CommonVars.getCurrUser()),billNo);
		initAfterTable1(listafter);
		if (listafter != null && listafter.size()>0){
			 after1 = (AtcMergeAfterComInfo) listafter.get(0);
		     List listbefore = null;
		     if (jCheckBox.isSelected()){
		    	 listbefore = encAction.findAllAtcMergerBeforeComInfo(new Request(CommonVars.getCurrUser()),billNo);
		     } else{
		         listbefore = encAction.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars.getCurrUser()),after1);
		     }
		     initBeforeTable1(listbefore);
		} else {
			 initBeforeTable1(new Vector());
		}
	}

	/**
	 * This method initializes jSplitPane2	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setDividerSize(0);
			jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane2.setTopComponent(getJPanel6());
			jSplitPane2.setBottomComponent(getJPanel8());
			jSplitPane2.setDividerLocation(250);
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jSplitPane3	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane3() {
		if (jSplitPane3 == null) {
			jSplitPane3 = new JSplitPane();
			jSplitPane3.setDividerLocation(250);
			jSplitPane3.setDividerSize(0);
			jSplitPane3.setTopComponent(getJPanel7());
			jSplitPane3.setBottomComponent(getJPanel9());
			jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane3;
	}

	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jPanel7	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(new BorderLayout());
			jPanel7.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jPanel8	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel8;
	}

	/**
	 * This method initializes jPanel9	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel9() {
		if (jPanel9 == null) {
			jPanel9 = new JPanel();
			jPanel9.setLayout(new BorderLayout());
			jPanel9.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel9;
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
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (afterCommInfoTableModel == null)
								return;
							after = (AtcMergeAfterComInfo) afterCommInfoTableModel.getCurrentRow();
							if (after == null){
								return;
							}
							if (!jCheckBox.isSelected()){
						       List listbefore = encAction.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars.getCurrUser()),after);
						       initBeforeTable(listbefore);
							}
						}
					});	
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
			jTable1.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (afterCommInfoTableModel1 == null)
								return;
							after1 = (AtcMergeAfterComInfo) afterCommInfoTableModel1.getCurrentRow();
							if (after1 == null){
								return;
							}
							if (!jCheckBox.isSelected()){
						       List listbefore = encAction.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars.getCurrUser()),after1);
						       initBeforeTable1(listbefore);
							}
						}
					});	
		}
		return jTable1;
	}

	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
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

	public void setFlat(int flat) {
		this.flat = flat;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(342,324,18,24));
			jCheckBox.setText("");
			jCheckBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 if (afterCommInfoTableModel != null && afterCommInfoTableModel.getCurrentRow() != null){//左边
							after = (AtcMergeAfterComInfo) afterCommInfoTableModel.getCurrentRow();
							String billNo = (String) jComboBox.getSelectedItem();
							List listbefore =  null;
							if (jCheckBox.isSelected()){								
								 listbefore = encAction.findAllAtcMergerBeforeComInfo(new Request(CommonVars.getCurrUser()),billNo);
							} else {
								 listbefore = encAction.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars.getCurrUser()),after);
							}
							initBeforeTable(listbefore);
					 }
					 if (afterCommInfoTableModel1 != null && afterCommInfoTableModel1.getCurrentRow() != null){//右边
							after1 = (AtcMergeAfterComInfo) afterCommInfoTableModel1.getCurrentRow();
							String billNo = (String) jComboBox1.getSelectedItem();
							List listbefore =  null;
							if (jCheckBox.isSelected()){								
								 listbefore = encAction.findAllAtcMergerBeforeComInfo(new Request(CommonVars.getCurrUser()),billNo);
							} else {
								 listbefore = encAction.findAtcMergeBeforeComInfoByAfterID(new Request(CommonVars.getCurrUser()),after1);
							}
							initBeforeTable1(listbefore);
					 }
					 
				}
			});
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(350,375,56,48));
			jButton2.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jButton2.setForeground(java.awt.Color.blue);
			jButton2.setText("※");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new billSort().start();
				}
			});
		}
		return jButton2;
	}
	
	class billSort extends Thread {
	   	  public void run(){
	   	  	 try{
	   	  	    CommonProgress.showProgressDialog(DgApplyToCustomsBillChange.this);
			    CommonProgress.setMessage("系统正在排序资料，请稍后...");
			    if (jComboBox.getSelectedItem() != null && !jComboBox.getSelectedItem().equals("")){//右边
			    	String billNo = (String) jComboBox.getSelectedItem();
			    	encAction.billSort(new Request(CommonVars.getCurrUser()),billNo);
				}				
				if (jComboBox1.getSelectedItem() != null && !jComboBox1.getSelectedItem().equals("")){//右边
					String billNo1 = (String) jComboBox1.getSelectedItem();
					encAction.billSort(new Request(CommonVars.getCurrUser()),billNo1);
				}				
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgApplyToCustomsBillChange.this,"排序完成!","提示",2);
				initdata();
				initdata1();
	   	  	 } catch (Exception e){
	   	  	    CommonProgress.closeProgressDialog();
			    JOptionPane.showMessageDialog(DgApplyToCustomsBillChange.this,"排序失败：！"+e.getMessage(),"提示",2);
	   	  	 }
	   	  }
	   }

}  //  @jve:decl-index=0:visual-constraint="10,10"
