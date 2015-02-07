package com.bestway.bcs.client.contractexe;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JFrame;

import com.bestway.bcs.client.contractexe.PnMakeBcsCustomsDeclaration2.CheckBoxEditor;
import com.bestway.bcs.client.contractexe.PnMakeBcsCustomsDeclaration2.MultiRenderer;
import com.bestway.bcs.contractexe.action.ContractExeAction;
import com.bestway.bcs.contractexe.entity.TempImpExpGoodsBill;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.manualdeclare.FmEmsHeadH2k;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.client.custom.common.DgMakeApplyToCustoms;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ImageIcon;

public class DgMakeCustomsFromImpExpGoods extends JDialogBase {

	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel       tableModel                = null;
	private ContractExeAction             encAction                 = null;
	private List   list  = null;
	private boolean isLj = true;
	private JButton jButton1 = null;
	private JLabel jLabel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JLabel jLabel2 = null;
	
	
	
	/**
	 * This is the default constructor
	 */
	public DgMakeCustomsFromImpExpGoods() {
		super();
		this.encAction = (ContractExeAction) CommonVars.getApplicationContext().getBean("contractExeAction");
		initialize();
	}

	public void setVisible(boolean isFlag) {
        if (isFlag == true) {
        	list = this.encAction.findTempImpExpGoodsBill(
                    new Request(CommonVars.getCurrUser()),new Boolean(isLj)); 
        	if (isLj){
        		initImgTable(list);
        	} else {
        		initExgTable(list);
        	}
        }
        super.setVisible(isFlag);
    }
	
	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(622, 408);
		this.setContentPane(getJContentPane());
		this.setTitle("进出货单转报关单");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.SOUTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jLabel1 = new JLabel();
			jLabel1.setText("");
			jLabel1.setIcon(new ImageIcon(getClass().getResource("/com/bestway/bcus/client/resources/images/hint.gif")));
			jLabel = new JLabel();
			jLabel.setText("进出货单转报关单");
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 24));
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.setBackground(java.awt.Color.white);
			jPanel.add(jLabel, java.awt.BorderLayout.CENTER);
			jPanel.add(jLabel1, java.awt.BorderLayout.WEST);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.EAST);
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
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
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
			jButton.setText("执行(Y)");
			jButton.setMnemonic(java.awt.event.KeyEvent.VK_Y);
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {  
					List ls = getParentList();
					if (ls == null  || ls.size() <= 0) {
			            JOptionPane.showMessageDialog(DgMakeCustomsFromImpExpGoods.this, "请选择即将要转的报关单号!",
			                    "提示", JOptionPane.INFORMATION_MESSAGE);
			            return;
			        }
					if (JOptionPane.showConfirmDialog(DgMakeCustomsFromImpExpGoods.this,
							"确定要执行吗?", "确认", 0) != 0) {
						return;
					}
					new execute(ls).start();
				}
			});
			
			
		}
		return jButton;
	}
	
	
       //执行
	   class execute extends Thread{
		   private List ls = null;
		   public execute(List ls){
				this.ls = ls;
			}
		   public void run(){
				 try {
		            CommonProgress.showProgressDialog();
		            CommonProgress.setMessage("系统正在执行数据，请稍后...");
		            String infostr = null;
					infostr = encAction.impExpGoodsTransCustoms(new Request(CommonVars.getCurrUser()),ls,isLj);
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgMakeCustomsFromImpExpGoods.this, infostr,
			                    "提示", JOptionPane.INFORMATION_MESSAGE);
				 } catch (Exception e) {
			          CommonProgress.closeProgressDialog();
			          JOptionPane.showMessageDialog(DgMakeCustomsFromImpExpGoods.this,
			                  "执行数据失败：！" + e.getMessage(), "提示", 2);    
				 } finally {							 
					 dispose();
				 }
		   }
		}
	
	
	/**
     * 获得选中关封申请单中的信息
     */
    public List getParentList() {
        if (this.tableModel == null) {
            return null;
        }
        List list = tableModel.getList();
        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempImpExpGoodsBill) {
            	TempImpExpGoodsBill t = (TempImpExpGoodsBill) list.get(i);
                if (t.getIsSelected().booleanValue() == true) {
                    newList.add(t);
                }
            }
        }
        return newList;
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
     * 初始化数据Table
     */
    private void initImgTable(List list) {        
        JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            public List InitColumns() {
                List list = new Vector();
                list.add(addColumn("选择", "isSelected", 100));
                list.add(addColumn("报关号", "customsNo", 100));
                list.add(addColumn("手册号", "contractNo", 100));
                list.add(addColumn("KR#", "kr", 100));
                list.add(addColumn("车牌号", "catNo", 100));
                
                return list;
            }
        };
        jTableListModelAdapter.setEditableColumn(1);
        tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
        jTable.getColumnModel().getColumn(1).setCellRenderer(
                new MultiRenderer());
        jTable.getColumnModel().getColumn(1).setCellEditor(
                new CheckBoxEditor(new JCheckBox()));
    }
    
    
    
    /**
     * 初始化数据Table
     */
    private void initExgTable(List list) {        
        JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            public List InitColumns() {
            	List list = new Vector();
                list.add(addColumn("选择", "isSelected", 100));
                list.add(addColumn("提运单号/大陆车牌", "catNo", 100));
				list.add(addColumn("合同号", "contractNo", 60));			
				list.add(addColumn("包装种类", "wrapType", 80));
				list.add(addColumn("运抵国/出口国", "countryOfLoadingOrUnloading", 100));
				list.add(addColumn("贸易方式", "tradeMode", 100));
				list.add(addColumn("备注1", "memo1", 100));
				list.add(addColumn("备注2", "memo2", 100));
				list.add(addColumn("出口口岸", "customs", 100));
				list.add(addColumn("指运港", "portLoad", 100));
				list.add(addColumn("批准文号", "authorizeFile", 100));
				list.add(addColumn("集装箱号", "containerNum", 100));
				list.add(addColumn("运输工具名称", "conveyance", 100));
				list.add(addColumn("运输方式", "transferMode", 100));
				list.add(addColumn("境内运输工具编号", "domeConveyance", 100));
				list.add(addColumn("报送海关", "declarationCustoms", 100));
				return list;
            }
        };
        jTableListModelAdapter.setEditableColumn(1);
        tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
        jTable.getColumnModel().getColumn(1).setCellRenderer(
                new MultiRenderer());
        jTable.getColumnModel().getColumn(1).setCellEditor(
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
                cb
                        .setSelected(Boolean.valueOf(value.toString())
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
            if (obj instanceof TempImpExpGoodsBill) {
            	TempImpExpGoodsBill temp = (TempImpExpGoodsBill) obj;
                temp.setIsSelected(new Boolean(cb.isSelected()));
                tableModel.updateRow(obj);
            }
            fireEditingStopped();
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
		}
		return jTable;
	}

	public boolean isLj() {
		return isLj;
	}

	public void setLj(boolean isLj) {
		this.isLj = isLj;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("退出(Q)");
			jButton1.setMnemonic(java.awt.event.KeyEvent.VK_Q);
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("                                     ");
			jPanel2 = new JPanel();
			jPanel2.setBackground(java.awt.Color.white);
			jPanel2.add(getJButton2(), null);
			jPanel2.add(getJButton3(), null);
			jPanel2.add(jLabel2, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("全选(A)");
			jButton2.setMnemonic(java.awt.event.KeyEvent.VK_A);
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMakeCustomsFromImpExpGoods.this.setCursor(new Cursor(
                            Cursor.WAIT_CURSOR));
                    selectAll(true);
                    DgMakeCustomsFromImpExpGoods.this.setCursor(new Cursor(
                            Cursor.DEFAULT_CURSOR));
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
			jButton3.setText("全不选(D)");
			jButton3.setMnemonic(java.awt.event.KeyEvent.VK_D);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMakeCustomsFromImpExpGoods.this.setCursor(new Cursor(
                            Cursor.WAIT_CURSOR));
                    selectAll(false);
                    DgMakeCustomsFromImpExpGoods.this.setCursor(new Cursor(
                            Cursor.DEFAULT_CURSOR));
				}
			});
		}
		return jButton3;
	}

	
	
	/**
     * 选中所有 or 清除所有选择
     */
    private void selectAll(boolean isSelected) {
        if (this.tableModel == null) {
            return;
        }
        List list = tableModel.getList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempImpExpGoodsBill) {
            	TempImpExpGoodsBill t = (TempImpExpGoodsBill) list.get(i);
                t.setIsSelected(new Boolean(isSelected));
            }
        }
        tableModel.updateRows(list);
    }
    
    
}  //  @jve:decl-index=0:visual-constraint="10,10"
