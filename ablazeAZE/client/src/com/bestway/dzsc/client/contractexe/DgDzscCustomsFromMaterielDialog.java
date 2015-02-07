/*
 * Created on 2005-9-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.contractexe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.dzsc.contractexe.action.DzscContractExeAction;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.ui.winuicontrol.JDialogBase;
/**
 * @author 
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgDzscCustomsFromMaterielDialog extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JPanel jPanel = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel2 = null;
	private JTable jTable = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable1 = null;
	private JScrollPane jScrollPane1 = null;
	private DzscAction dzscAction = null;
	private List returnList = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModel1 = null;
	private Materiel materiel = null;
	private String materialType = null;
	private String emsNo = null;
	private boolean	 ok	= false;
	private DzscContractExeAction baseEncAction = null;
	private BaseCustomsDeclaration customsDeclaration = null;
	private DzscEmsImgBill imgbill = null;
	private DzscEmsExgBill exgbill = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPanel jPanel3 = null;
	private JToolBar jToolBar2 = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton4 = null;
	private boolean isQuery = false;
	//----分页----
	private int firstIndex = 0;
	/**
	 * This is the default constructor
	 */
	public DgDzscCustomsFromMaterielDialog() {
		super();
		dzscAction = (DzscAction) CommonVars
               .getApplicationContext().getBean("dzscAction");
		
		initialize();
		//setAlwaysOnTop(true);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(539, 428);
		this.setContentPane(getJContentPane());
		this.setTitle("新增报关单来自清单");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				firstIndex = 0;
				initTable(new Vector());
				initTable1(new Vector());
			}
		});
	}
	
	private void getpage(int firstIndex,boolean isQuery){
		List list = null;	
		list = dzscAction.findMaterielFromInner(new Request(CommonVars.getCurrUser()),materialType,firstIndex,(isQuery?this.jTextField.getText():null));			
		if (list != null && list.size()>0){
			initTable(list);
			materiel = (Materiel) list.get(0);
			String ptNo = ((Materiel) list.get(0)).getPtNo();
			List list1 = dzscAction.findBillByMaterielPtNo(new Request(CommonVars.getCurrUser()),emsNo,ptNo,materialType);
			if (list1 != null && list1.size()>0){
				if (materialType.equals(MaterielType.MATERIEL)){
					imgbill = (DzscEmsImgBill) list1.get(0);
				} else {
					exgbill = (DzscEmsExgBill) list1.get(0);
				}
			}
			initTable1(list1);
		} else {
			initTable(new Vector());
			initTable1(new Vector());
		}
	}
    //上
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "ptNo", 150));
						list.add(addColumn("商品名称", "factoryName", 150));
						list.add(addColumn("型号规格", "factorySpec", 150));
						list.add(addColumn("单位", "calUnit.name", 60));
						list.add(addColumn("类型", "scmCoi.coiProperty", 60));
						return list;
					}
				});
		jTable.getColumnModel().removeColumn(jTable.getColumnModel().getColumn(0));
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	 //下
	private void initTable1(final List list) {
		tableModel1 = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("凭证序号", "seqNum", 60));
						list.add(addColumn("序号", "no", 60));						
						list.add(addColumn("海关编码", "complex", 80));
						list.add(addColumn("商品名称", "name", 150));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("单位", "unit.name", 60));
						list.add(addColumn("金额", "money", 60));
						return list;
					}
				});
		jTable1.getColumnModel().removeColumn(jTable1.getColumnModel().getColumn(0));
		jTable1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.setFloatable(false);
			jToolBar.add(getJPanel());
		}
		return jToolBar;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.add(getJButton2(), null);
			jPanel.add(getJButton3(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("确定");
			jButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {  
					
					if (materiel == null){
						JOptionPane.showMessageDialog(DgDzscCustomsFromMaterielDialog.this,"请选择物料","提示",2);
						return;
					}
					if (imgbill == null && exgbill == null){
						JOptionPane.showMessageDialog(DgDzscCustomsFromMaterielDialog.this, "请选择物料对应得清单资料",
								"提示！", 2);
						return;
					}
					DgDzscFromMaterielEdit dg = new DgDzscFromMaterielEdit();				
					dg.setImgbill(imgbill);					
					dg.setExgbill(exgbill);
					dg.setMateriel(materiel);
					dg.setCustomsDeclaration(customsDeclaration);
					dg.setVisible(true);
					if (dg.isOk()){
						setOk(true);
						dispose();
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
			jButton3.setText("放弃");
			jButton3.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					setOk(false);
					dispose();
				}
			});
		}
		return jButton3;
	}
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */    
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(180);
			jSplitPane.setBottomComponent(getJPanel2());
			jSplitPane.setTopComponent(getJPanel3());
		}
		return jSplitPane;
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
			                if (tableModel == null){
			                	return;
			                }			                	
			                if (tableModel.getCurrentRow() == null){
			                	return;
			                }			                	
			                materiel = (Materiel) tableModel.getCurrentRow();
			                String ptNo = materiel.getPtNo();
							List list1 = dzscAction.findBillByMaterielPtNo(new Request(CommonVars.getCurrUser()),emsNo,ptNo,materialType);
							if (list1 != null && list1.size()>0){
								if (materialType.equals(MaterielType.MATERIEL)){
									imgbill = (DzscEmsImgBill) list1.get(0);
								} else {
									exgbill = (DzscEmsExgBill) list1.get(0);
								}
							}
							initTable1(list1);
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
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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
			                if (tableModel1 == null)
			                	return;
			                if (tableModel1.getCurrentRow() == null)
			                	return;
			                
							if (materialType.equals(MaterielType.MATERIEL)){
								imgbill = (DzscEmsImgBill) tableModel1.getCurrentRow();
							} else {
								exgbill = (DzscEmsExgBill) tableModel1.getCurrentRow();
							} 
						}
					});
		}
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
		}
		return jScrollPane1;
	}
	/**
	 * @return Returns the returnList.
	 */
	public List getReturnList() {
		return returnList;
	}
	/**
	 * @param returnList The returnList to set.
	 */
	public void setReturnList(List returnList) {
		this.returnList = returnList;
	}
	/**
	 * @return Returns the type.
	 */
	public String getMaterialType() {
		return materialType;
	}
	/**
	 * @param type The type to set.
	 */
	public void setMaterialType(String type) {
		this.materialType = type;
	}
	/**
	 * @return Returns the emsNo.
	 */
	public String getEmsNo() {
		return emsNo;
	}
	/**
	 * @param emsNo The emsNo to set.
	 */
	public void setEmsNo(String emsNo) {
		this.emsNo = emsNo;
	}
	/**
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}
	/**
	 * @param ok The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return Returns the customsDeclaration.
	 */
	public BaseCustomsDeclaration getCustomsDeclaration() {
		return customsDeclaration;
	}
	/**
	 * @param customsDeclaration The customsDeclaration to set.
	 */
	public void setCustomsDeclaration(BaseCustomsDeclaration customsDeclaration) {
		this.customsDeclaration = customsDeclaration;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("   上页   ");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (firstIndex >= 100){
					     firstIndex = firstIndex - 100;
					     getpage(firstIndex,isQuery);
					}
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
			jButton1.setText("   下页   ");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (tableModel.getRowCount() >= 100){
						   firstIndex = firstIndex + 100;
						   getpage(firstIndex,isQuery);
						}
				}
			});
		}
		return jButton1;
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
			jPanel3.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}
	/**
	 * This method initializes jToolBar2	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jToolBar2 = new JToolBar();
			jToolBar2.add(getJPanel1());
			jToolBar2.add(getJButton());
			jToolBar2.add(getJButton1());
		}
		return jToolBar2;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel.setBounds(4, 5, 43, 18);
			jLabel.setText("料号");
			jPanel1.add(jLabel, null);
			jPanel1.add(getJTextField(), null);
			jPanel1.add(getJButton4(), null);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(47, 5, 176, 22);
			jTextField.addKeyListener(new java.awt.event.KeyAdapter() { 
				public void keyPressed(java.awt.event.KeyEvent e) {    
					String sKey = e.getKeyText(e.getKeyCode());
			        if (sKey.equalsIgnoreCase("Enter")) {
			        	isQuery = true;
						firstIndex = 0;
						getpage(firstIndex,isQuery);
			        }
				}
			});

		}
		return jTextField;
	}
	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(221, 4, 73, 21);
			jButton4.setText("查询");
			jButton4.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
				       new find().start();
				}
			});
		}
		return jButton4;
	}
	
	class find extends Thread{	
		public void run(){
			 try {
	            CommonProgress.showProgressDialog(DgDzscCustomsFromMaterielDialog.this);
	            CommonProgress.setMessage("系统正获取数据，请稍后...");
	            isQuery = true;
				firstIndex = 0;
				getpage(firstIndex,isQuery);
	            CommonProgress.closeProgressDialog();
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgDzscCustomsFromMaterielDialog.this,
		                "获取数据失败：！" + e.getMessage(), "提示", 2);    
			 } 
		}
	  }
     }  //  @jve:decl-index=0:visual-constraint="10,10"
