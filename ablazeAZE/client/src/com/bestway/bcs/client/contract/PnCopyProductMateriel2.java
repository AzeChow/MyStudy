/*
 * Created on 2004-8-16
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.TempContractExg;
import com.bestway.bcs.contract.entity.TempContractImg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JPanelBase;
import java.awt.Rectangle;
import javax.swing.JTextField;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PnCopyProductMateriel2 extends JPanelBase {

    private PnCopyProductMateriel2 pnMakeCustomsEnvelopBill2 = null;
    private JTableListModel            tableModel                = null;
    private JTable                     jTable                    = null;
    private JScrollPane                jScrollPane               = null;
    private JButton                    btnSelectAll              = null;
    private JButton                    btnNotSelectAll           = null;

	private ContractAction contractAction = null; // 合同
    private Contract contract = null;
	private JTextField jTextField = null;
    
    /**
     * This is the default constructor
     */
    public PnCopyProductMateriel2() {
        super();
        initialize();
        contractAction = (ContractAction) CommonVars.getApplicationContext().getBean(
                "contractAction");
    }

    public void setVisible(boolean isFlag) {
        if (isFlag == true) {
        	if (contract != null) {
				String parentId = contract.getId();
				List list = contractAction
						.findTempContractImgByParentId(
								new Request(CommonVars
										.getCurrUser()),
								parentId);
				initTable(list);
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
        javax.swing.JLabel jLabel = new JLabel();
        this.setLayout(null);
        this.setSize(500, 248);
        jLabel.setBounds(13, 11, 151, 21);
        jLabel.setText("选择转抄的料件(\",\"号分开)");
        this.add(jLabel, null);
        this.add(getJScrollPane(), null);
        this.add(getBtnSelectAll(), null);
        this.add(getBtnNotSelectAll(), null);
        this.add(getJTextField(), null);
    }

    /**
     * @return Returns the pnMakeCustomsEnvelopBill2.
     */
    public PnCopyProductMateriel2 getPnMakeCustomsEnvelopBill2() {
        return pnMakeCustomsEnvelopBill2;
    }

    /**
     * @param pnMakeCustomsEnvelopBill2
     *            The pnMakeCustomsEnvelopBill2 to set.
     */
    public void setPnMakeCustomsEnvelopBill2(
            PnCopyProductMateriel2 pnMakeCustomsEnvelopBill2) {
        this.pnMakeCustomsEnvelopBill2 = pnMakeCustomsEnvelopBill2;
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
            jScrollPane.setBounds(2, 39, 497, 209);
            jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

    /**
     * This method initializes btnSelectAll
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnSelectAll() {
        if (btnSelectAll == null) {
            btnSelectAll = new JButton();
            btnSelectAll.setBounds(364, 11, 61, 21);
            btnSelectAll.setText("全选");
            btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    selectAll(true);
                }
            });
        }
        return btnSelectAll;
    }

    /**
     * This method initializes btnNotSelectAll
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnNotSelectAll() {
        if (btnNotSelectAll == null) {
            btnNotSelectAll = new JButton();
            btnNotSelectAll.setBounds(431, 11, 61, 21);
            btnNotSelectAll.setText("不选");
            btnNotSelectAll
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            selectAll(false);
                        }
                    });
        }
        return btnNotSelectAll;
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
            if (list.get(i) instanceof TempContractImg) {
                TempContractImg temp = (TempContractImg) list.get(i);
                temp.setIsSelected(new Boolean(isSelected));
            }
        }
        tableModel.updateRows(list);
    }

    /**
     * 初始化数据Table
     */
    private void initTable(List list) {   
        JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            public List InitColumns() {
                List list = new Vector();
                list.add(addColumn("选择", "isSelected", 100));
                list.add(addColumn("序号", "contractImg.seqNum", 60, Integer.class));
				list.add(addColumn("商品编码", "contractImg.complex.code", 80));
				list.add(addColumn("商品名称", "contractImg.name", 150));
				list.add(addColumn("规格型号", "contractImg.spec", 100));
				list.add(addColumn("计量单位", "contractImg.unit.name", 80));
				list.add(addColumn("单价", "contractImg.declarePrice", 100));
				list.add(addColumn("数量", "contractImg.amount", 100));
				list.add(addColumn("损耗数量", "contractImg.wasteAmount", 100));
				list.add(addColumn("总金额", "contractImg.totalPrice", 100));
				list.add(addColumn("法定单位总量", "contractImg.legalTotalAmount", 100));
				list.add(addColumn("法定单位数量", "contractImg.legalAmount", 100));
				list.add(addColumn("法定单位", "contractImg.complex.firstUnit.name",
						100));
				list.add(addColumn("单位重量", "contractImg.unitWeight", 100));
				list.add(addColumn("总重量", "contractImg.totalWeight", 100));
				list.add(addColumn("原产地", "contractImg.country.name", 100));
				list.add(addColumn("归并序号", "contractImg.credenceNo", 60,
						Integer.class));
				list.add(addColumn("备注", "contractImg.note", 100));
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
                //                if (checkValue(table, row, column)) {
                //                    checkBox.setBackground(Color.BLUE);
                //                    checkBox.setForeground(Color.WHITE);
                //                }
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
            if (obj instanceof TempContractImg) {
                TempContractImg temp = (TempContractImg) obj;
                temp.setIsSelected(new Boolean(cb.isSelected()));
                tableModel.updateRow(obj);
            }
            fireEditingStopped();
        }

    }

    /**
     * 获得选中关封申请单中商品信息的信息
     */
    public List<ContractImg> getSelectedContractImgList() {
    	List<ContractImg> newList = new ArrayList<ContractImg>();
        if (this.tableModel == null) {
            return newList;
        }
        List list = tableModel.getList();
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof TempContractImg) {
                TempContractImg temp = (TempContractImg) list.get(i);
                if (temp.getIsSelected().booleanValue() == true) {
                    newList.add(temp.getContractImg());
                }
            }
        }
        return newList;
    }

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(168, 10, 169, 24));
			jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					String[] keys = (jTextField.getText().trim() + e
							.getKeyChar()).trim().split(",");
					if (keys.length > 0) {
						List list = tableModel.getList();
						for (int i = 0; i < list.size(); i++) {
							TempContractImg temp = (TempContractImg) list
									.get(i);
							if (temp.getContractImg().getSeqNum() != null) {
								temp.setIsSelected(checkKey(keys, temp
										.getContractImg().getSeqNum()
										.toString()));
							}
						}
						tableModel.setList(list);
					}
				}
			});
		}
		return jTextField;
	}
	private boolean checkKey(String[] keys, String key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] != null && key != null
					&& keys[i].trim().equals(key.trim())) {
				return true;
			}
		}
		return false;
	}
}

