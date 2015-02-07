/*
 * Created on 2005-12-26
 * 商品禁用功能
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerHead;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author wp
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmBomUnitWearCompare extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JTableListModel tableModel = null;
	
	private JTableListModel tableModel1 = null;

	private ManualDeclareAction manualDecleareAction = null;

	private JButton jButton2 = null;

	private JTextField jTextField = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JButton jButton = null;
	private EmsHeadH2k emsHeadH2k = null;
	private EmsEdiMergerHead emsMergerHead = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField1 = null;

	/**
	 * This is the default constructor
	 */
	public FmBomUnitWearCompare() {
		super();
        manualDecleareAction = (ManualDeclareAction) CommonVars
           .getApplicationContext().getBean("manualdeclearAction");
        initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(828, 537);
		this.setContentPane(getJContentPane());
		this.setTitle("BOM单耗比较");
        List list = manualDecleareAction.findEmsHeadH2k(new Request(CommonVars.getCurrUser(),true));		
		if (list != null && list.size()>0){
		    emsHeadH2k = (EmsHeadH2k) list.get(0);
		}
		emsMergerHead = manualDecleareAction.findEmsEdiMergerHeadByDeclareState1(new Request(CommonVars.getCurrUser()),DeclareState.PROCESS_EXE);
		
		initTable(null);
	}


	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("成品序号", "emsHeadH2kVersion.emsHeadH2kExg.seqNum", 60));
						list.add(addColumn("归并后名称", "emsHeadH2kVersion.emsHeadH2kExg.name", 120));
						list.add(addColumn("归并后规格", "emsHeadH2kVersion.emsHeadH2kExg.spec", 240));						
						list.add(addColumn("成品版本", "emsHeadH2kVersion.version", 60));
						
						list.add(addColumn("料件序号", "seqNum", 60));
						list.add(addColumn("商品名称", "name", 120));
						list.add(addColumn("型号规格", "spec", 240));
						list.add(addColumn("单耗", "unitWear", 80));
						list.add(addColumn("损耗", "wear", 80));
						return list;
					}
				});
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable1(List list) {
		if (list == null) {
			list = new ArrayList();
		}
		tableModel1 = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料件序号", "bom.seqNum", 60));
						list.add(addColumn("商品名称", "bom.name", 120));
						list.add(addColumn("型号规格", "bom.spec", 240));
						list.add(addColumn("1.帐册单耗", "bom.unitWear", 80));
						list.add(addColumn("损耗", "bom.wear", 80));
						list.add(addColumn("2.工厂单耗", "unitWear", 80));
						list.add(addColumn("单耗差异=1-2", "unitWearCy", 100));						
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
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		} // @jve:decl-index=0:
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
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(40);
			jSplitPane.setDividerSize(5);
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
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(193,13,45,18));
			jLabel1.setText("版本号");
			jLabel = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel.setBounds(6, 9, 91, 24);
			jLabel.setText("成品备案序号");
			jPanel.add(jLabel, null);
			jPanel.add(getJButton2(), null);
			jPanel.add(getJTextField(), null);
			jPanel.add(getJButton(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getJTextField1(), null);
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
			jPanel1.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(341,9,68,22));
			jButton2.setText("查询");
			jButton2.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (jTextField.getText().equals("") || jTextField1.getText().equals("")){
						return;
					}
					if (emsHeadH2k == null || emsMergerHead == null){
						return;
					}
					new find().start();
				}
			
			});
		}
		return jButton2;
	}
	
	
class find extends Thread{
		
		public void run(){
			List lsResult = new Vector();
			List detailList = new Vector();
			 try {
	            CommonProgress.showProgressDialog(FmBomUnitWearCompare.this);
	            CommonProgress.setMessage("系统正获取数据，请稍后...");
	            
				Integer seqNum = Integer.valueOf(jTextField.getText()); //成品备案序号
				Integer version = Integer.valueOf(jTextField1.getText()); //成品版本号
				lsResult = manualDecleareAction.findEmsHeadH2kBomByCpSeqNum(new Request(CommonVars.getCurrUser()),seqNum,emsHeadH2k,version);
				detailList = manualDecleareAction.findBomUnitWearCompare(lsResult,emsMergerHead,seqNum,version);
				CommonProgress.closeProgressDialog();
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(FmBomUnitWearCompare.this,"获取数据失败：" + e.getMessage(), "提示", 2);    
			 } finally {			 	
			 	initTable(lsResult);
			 	initTable1(detailList);
			 }
		}
	}



	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new java.awt.Rectangle(97,10,91,22));
		}
		return jTextField;
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
			jSplitPane1.setTopComponent(getJPanel2());
			jSplitPane1.setBottomComponent(getJPanel3());
			jSplitPane1.setDividerLocation(200);
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
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
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
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(412,9,68,22));
			jButton.setText("退出");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setBounds(new java.awt.Rectangle(241,9,95,22));
		}
		return jTextField1;
	}
}  //  @jve:decl-index=0:visual-constraint="9,10"
