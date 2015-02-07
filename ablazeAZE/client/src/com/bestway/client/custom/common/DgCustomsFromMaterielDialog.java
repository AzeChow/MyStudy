/*
 * Created on 2005-9-1
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.custom.common;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.manualdeclare.entity.TempEmsEdiHeadH2k;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.customs.common.action.BaseEncAction;
import com.bestway.customs.common.entity.BaseCustomsDeclaration;
import com.bestway.ui.winuicontrol.JDialogBase;
//import com.informix.util.stringUtil;
/**
 * @author xxm(公共模块)
 *
 * // change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DgCustomsFromMaterielDialog extends JDialogBase {

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
	private BaseEncAction baseEncAction = null;  //  @jve:decl-index=0:
	
	private List returnList = null;
	private JTableListModel materielTableModel = null;
	private JTableListModel emsTableModel = null;
	private Materiel materiel = null;
	private String materialType = null; //成品料件类型标记  //  @jve:decl-index=0:
	private String emsNo = null; //电子帐册号  //  @jve:decl-index=0:
	private boolean	 ok	= false;
	private BaseCustomsDeclaration customsDeclaration = null;
	//Bcs
	private ContractImg bcsimgbill = null;
	private ContractExg bcsexgbill = null;
	//Bcus
	private EmsHeadH2kImg bcusimgbill = null;
	private EmsHeadH2kExg bcusexgbill = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JPanel jPanel3 = null;
	private JToolBar jToolBar2 = null;
	private JPanel jPanel1 = null;
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JButton jButton4 = null;
	private boolean isQuery = false;
	private int projectType = ProjectType.BCUS; //模块类型
	
	//----分页----
	private int firstIndex = 0;
	/**
	 * This is the default constructor
	 */
	public DgCustomsFromMaterielDialog() {
		super();
		
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(648, 428);
		this.setContentPane(getJContentPane());
		this.setTitle("新增报关单来自清单");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent e) {
				firstIndex = 0;
				initTable(new Vector());
				
				switch (projectType){
				case ProjectType.BCUS:
					initTableBcus(new Vector());
					break;
				case ProjectType.BCS:				
				}
				
			}
		});
	}
	
	private void getpage(int firstIndex,boolean isQuery){
		List materielList = null;	
		//物料主挡
		materielList = baseEncAction.findMaterielFromInner(new Request(CommonVars.getCurrUser()),materialType,firstIndex,(isQuery?this.jTextField.getText():null),emsNo);	
		String keys = "";
		for(int i=0;i<materielList.size();i++){
			materiel = (Materiel) materielList.get(i);
			String ptNo = ((Materiel) materielList.get(i)).getPtNo();//料号
			String ptVersion = ((Materiel) materielList.get(i)).getPtVersion();			
			List emsList2 = baseEncAction.findBillByMaterielPtNo2(new Request(CommonVars.getCurrUser()),emsNo,ptNo,materialType,ptVersion);
			for(int j=0;j<emsList2.size();j++){
				keys += "帐册序号为：" + emsList2.get(j).toString() + " 的商品"
				+ "已被禁用！\n";				
			}
		}
		if(!"".equals(keys)){
			JOptionPane.showMessageDialog(DgCustomsFromMaterielDialog.this,keys,
					"提示！", 2);
		}
		if (materielList != null && materielList.size()>0){
			initTable(materielList);
			materiel = (Materiel) materielList.get(0);
			String ptNo = ((Materiel) materielList.get(0)).getPtNo();//料号
			String ptVersion = ((Materiel) materielList.get(0)).getPtVersion();
			//成品有多版本
			List emsList = baseEncAction.findBillByMaterielPtNo(new Request(CommonVars.getCurrUser()),emsNo,ptNo,materialType,ptVersion);			
			switch (projectType){
			case ProjectType.BCUS:
				if (emsList != null && emsList.size()>0){
					initTableBcus(emsList);
				} else {
					initTableBcus(new Vector());
				}
				break;
			case ProjectType.BCS:
				if (emsList != null && emsList.size()>0){
					if (materialType.equals(MaterielType.MATERIEL)){
						bcsimgbill = (ContractImg) emsList.get(0);
					} else {
						bcsexgbill = (ContractExg) emsList.get(0);
					}
					initTableBcsDzba(emsList);
				} else {
					initTableBcsDzba(new Vector());
				}
				break;			
			}
		} else {
			initTable(new Vector());
		}
	}
    //上
	private void initTable(final List list) {
		materielTableModel = new JTableListModel(jTable, list,
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
	 //BCS,DZBA
	private void initTableBcsDzba(final List list) {
		emsTableModel = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						list.add(addColumn("凭证序号", "tenSeqNum", 60));
						list.add(addColumn("序号", "no", 60));						
						list.add(addColumn("海关编码", "complex.code", 80));
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
	//下Bcus
	private void initTableBcus(final List list) {
		 JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
					public List InitColumns() {
						List <JTableListColumn>list = new Vector<JTableListColumn>();
						if (materialType.equals(MaterielType.MATERIEL)){
							list.add(addColumn("选择", "isSelected", 100));
							list.add(addColumn("序号", "emsHeadH2kImg.seqNum", 60));						
							list.add(addColumn("海关编码", "emsHeadH2kImg.complex.code", 80));
							list.add(addColumn("商品名称", "emsHeadH2kImg.name", 150));
							list.add(addColumn("型号规格", "emsHeadH2kImg.spec", 100));
							list.add(addColumn("单位", "emsHeadH2kImg.unit.name", 60));
						} else if (materialType.equals(MaterielType.FINISHED_PRODUCT)){
							list.add(addColumn("选择", "isSelected", 100));
							list.add(addColumn("序号", "emsHeadH2kExg.seqNum", 60));						
							list.add(addColumn("海关编码", "emsHeadH2kExg.complex.code", 80));
							list.add(addColumn("商品名称", "emsHeadH2kExg.name", 150));
							list.add(addColumn("型号规格", "emsHeadH2kExg.spec", 100));
							list.add(addColumn("单位", "emsHeadH2kExg.unit.name", 60));
						    list.add(addColumn("版本", "emsHeadH2kExg.version", 60));
						}
						return list;
					}
				};
				jTableListModelAdapter.setEditableColumn(1);
				emsTableModel = new JTableListModel(jTable1, list, jTableListModelAdapter);
		        jTable1.getColumnModel().getColumn(1).setCellRenderer(
		                new MultiRenderer());
		        jTable1.getColumnModel().getColumn(1).setCellEditor(
		                new CheckBoxEditor(new JCheckBox()));
	}
	
	 /**
     * render table JchcckBox 列
     */
    class MultiRenderer extends DefaultTableCellRenderer {
        JCheckBox checkBox = new JCheckBox();

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            if (value == null) {
                return this;
            }
            if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                checkBox.setSelected(Boolean.parseBoolean(value.toString()));
                checkBox.setHorizontalAlignment(JLabel.CENTER);
                checkBox.setBackground(table.getBackground());
                if (isSelected) {
                    checkBox.setForeground(table.getSelectionForeground());
                    checkBox.setBackground(table.getSelectionBackground());
                } else {
                    checkBox.setForeground(table.getForeground());
                    checkBox.setBackground(table.getBackground());
                }
                return checkBox;
            }
            String str = (value == null) ? "" : value.toString();
            return super.getTableCellRendererComponent(table, str, isSelected,
                    hasFocus, row, column);
        }
    }

    /**
     * 编辑列
     */
    class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
        private JCheckBox cb;
        private JTable    table = null;

        public CheckBoxEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            if (value == null) {
                return null;
            }
            if (Boolean.valueOf(value.toString()) instanceof Boolean) {
                cb = new JCheckBox();
                cb.setSelected(Boolean.valueOf(value.toString())
                                .booleanValue());
            }
            cb.setHorizontalAlignment(JLabel.CENTER);
            cb.addActionListener(this);
            this.table = table;
            return cb;
        }

        public Object getCellEditorValue() {
            cb.removeActionListener(this);
            return cb;
        }

        public void actionPerformed(ActionEvent e) {
            JTableListModel tableModel = (JTableListModel) this.table
                    .getModel();
            Object obj = tableModel.getCurrentRow();
            if (obj instanceof TempEmsEdiHeadH2k) {
            	TempEmsEdiHeadH2k temp = (TempEmsEdiHeadH2k) obj;
                temp.setIsSelected(new Boolean(cb.isSelected()));
                tableModel.updateRow(obj);
            }
            fireEditingStopped();
        }

    }

    /**
     * 获得选中关封申请单中商品信息的信息
     */
    public List getCommodityList() {
        if (this.emsTableModel == null) {
            return null;
        }
        List list = emsTableModel.getList();
        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempEmsEdiHeadH2k) {
            	TempEmsEdiHeadH2k temp = (TempEmsEdiHeadH2k) list.get(i);
            	if (temp.getIsSelected().booleanValue() == true) {
                    newList.add(temp);
                }
            }
        }
        return newList;
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
						JOptionPane.showMessageDialog(DgCustomsFromMaterielDialog.this,"请选择物料","提示",2);
						return;
					}
					DgFromMaterielEdit dg = new DgFromMaterielEdit();
					dg.setMateriel(materiel);
					dg.setProjectType(projectType);
					switch (projectType){
					case ProjectType.BCUS:
						List detailList = getCommodityList();
						if (detailList == null || detailList.size()<=0){
							JOptionPane.showMessageDialog(DgCustomsFromMaterielDialog.this, "请选择物料对应得帐册资料",
									"提示！", 2);
							return;
						}
						TempEmsEdiHeadH2k tempH2k = (TempEmsEdiHeadH2k) detailList.get(0);
						if (materialType.equals(MaterielType.MATERIEL)){						
							bcusimgbill = tempH2k.getEmsHeadH2kImg();
						} else {
							bcusexgbill = tempH2k.getEmsHeadH2kExg();
						}
						dg.setBcusimgbill(bcusimgbill);			
						dg.setBcusexgbill(bcusexgbill);	
						dg.setCustomsDeclaration(customsDeclaration);
						dg.setVisible(true);
						if (dg.isOk()){
							setOk(true);
							dispose();
						}
						break;
					case ProjectType.BCS:
						if (bcsimgbill == null && bcsexgbill == null){
							JOptionPane.showMessageDialog(DgCustomsFromMaterielDialog.this, "请选择物料对应得清单资料",
									"提示！", 2);
							return;
						}
						dg.setBcsimgbill(bcsimgbill);			
						dg.setBcsexgbill(bcsexgbill);						
						dg.setCustomsDeclaration(customsDeclaration);
						dg.setVisible(true);
						if (dg.isOk()){
							setOk(true);
							dispose();
						}
						break;					
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
			                if (materielTableModel == null){
			                	return;
			                }			                	
			                if (materielTableModel.getCurrentRow() == null){
			                	return;
			                }			                	
			                materiel = (Materiel) materielTableModel.getCurrentRow();
			                String ptNo = materiel.getPtNo();//料号
			                String ptVersion = materiel.getPtVersion();
                            //成品有多版本
			    			List emsList = baseEncAction.findBillByMaterielPtNo(new Request(CommonVars.getCurrUser()),emsNo,ptNo,materialType,ptVersion);								
			    			switch (projectType){
							case ProjectType.BCUS:
								if (emsList != null && emsList.size()>0){
									/*TempEmsEdiHeadH2k tempH2k = (TempEmsEdiHeadH2k) emsList.get(0);
									if (type.equals(MaterielType.MATERIEL)){ 
										bcusimgbill = tempH2k.getEmsHeadH2kImg();
									} else {
										bcusexgbill = tempH2k.getEmsHeadH2kExg();
									}*/
									initTableBcus(emsList);
								} else {
									initTableBcus(new Vector());
								}
								break;
							case ProjectType.BCS:
								if (emsList != null && emsList.size()>0){
									if (materialType.equals(MaterielType.MATERIEL)){
										bcsimgbill = (ContractImg) emsList.get(0);
									} else {
										bcsexgbill = (ContractExg) emsList.get(0);
									}
									initTableBcsDzba(emsList);
								} else {
									initTableBcsDzba(new Vector());
								}
								break;							
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
							/*if (e.getValueIsAdjusting()) {
								return;
							}*/
			                /*if (emsTableModel == null)
			                	return;
			                if (emsTableModel.getCurrentRow() == null)
			                	return;
			                switch (projectType){
			                case ProjectType.BCUS:
			                	if (type.equals(MaterielType.MATERIEL)){
									bcusimgbill = (EmsHeadH2kImg) emsTableModel.getCurrentRow();
								} else {
									bcusexgbill = (EmsHeadH2kExg) emsTableModel.getCurrentRow();
								}
			                	break;
			                case ProjectType.BCS:
			                	if (type.equals(MaterielType.MATERIEL)){
									bcsimgbill = (ContractImg) emsTableModel.getCurrentRow();
								} else {
									bcsexgbill = (ContractExg) emsTableModel.getCurrentRow();
								}
			                	break;			                
			                }*/
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
					if (materielTableModel.getRowCount() >= 100){
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
	            CommonProgress.showProgressDialog(DgCustomsFromMaterielDialog.this);
	            CommonProgress.setMessage("系统正获取数据，请稍后...");
	            isQuery = true;
				firstIndex = 0;
				getpage(firstIndex,isQuery);
	            CommonProgress.closeProgressDialog();
			 } catch (Exception e) {
		        CommonProgress.closeProgressDialog();
		        JOptionPane.showMessageDialog(DgCustomsFromMaterielDialog.this,
		                "获取数据失败：！" + e.getMessage(), "提示", 2);    
			 } 
		}
	  }

	/**
	 * @param baseEncAction The baseEncAction to set.
	 */
	public void setBaseEncAction(BaseEncAction baseEncAction) {
		this.baseEncAction = baseEncAction;
	}
	/**
	 * @param projectType The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	
	
	
     }  //  @jve:decl-index=0:visual-constraint="10,10"
