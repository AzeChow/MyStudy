package com.bestway.bcs.client.contractcav;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcs.contractcav.action.ContractCavAction;
import com.bestway.bcs.contractcav.entity.ContractBomCav;
import com.bestway.bcs.contractcav.entity.ContractImgCav;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.bcus.enc.entity.TempImpExpRequestBill;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgModifyProuctUsedWriteBack extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JTableListModel tableModel = null;

	private ContractImgCav imgCav = null;

	private boolean isOk = false;

	private List lsResult = new ArrayList();

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private ContractCavAction contractCavAction = null;

	public ContractImgCav getImgCav() {
		return imgCav;
	}

	public void setImgCav(ContractImgCav imgCav) {
		this.imgCav = imgCav;
	}

	public List getLsResult() {
		return lsResult;
	}

	public void setLsResult(List lsExgResult) {
		this.lsResult = lsExgResult;
	}

	public boolean isOk() {
		return isOk;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgModifyProuctUsedWriteBack() {
		super();
		initialize();
		contractCavAction = (ContractCavAction) CommonVars
				.getApplicationContext().getBean("contractCavAction");
	}

	public void setVisible(boolean b) {
		if (b) {
			this.initTable();
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(470, 555));
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("选择要反写的成品资料");
		this.setContentPane(getJContentPane());

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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton2());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton1());
		}
		return jToolBar;
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
				}
			});
		}
		return jTable;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("确定");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					lsResult = getSelectedData();
					isOk = true;
					dispose();
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
			jButton1.setText("取消");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	private void initTable() {
		List list = new ArrayList();
		if (imgCav != null) {
			list = contractCavAction.findContractBomCavByImgSeqNum(new Request(
					CommonVars.getCurrUser()), imgCav.getContractCav().getId(),
					imgCav.getSeqNum());
		}
        JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
            public List InitColumns() {
                List list = new Vector();
                list.add(addColumn("是否选择", "isSelected", 50));
				list.add(addColumn("成品序号", "seqProductNum", 50,
						Integer.class));
				list.add(addColumn("成品名称", "productName", 200));
				list.add(addColumn("成品规格", "productSpec", 200));
                return list;
            }
        };
        jTableListModelAdapter.setEditableColumn(1);
        tableModel = new JTableListModel(jTable, list, jTableListModelAdapter);
		TableColumn column = this.getJTable().getColumnModel().getColumn(1);
		column.setCellRenderer(new TableCheckBoxRender());
		column.setCellEditor(
                new CheckBoxEditor(new JCheckBox()));
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
            if (obj instanceof ContractBomCav) {
            	ContractBomCav temp = (ContractBomCav) obj;
                temp.setIsSelected(new Boolean(cb.isSelected()));
                tableModel.updateRow(obj);
            }
            fireEditingStopped();
        }
    }
	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("全部选中");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
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
			jButton3.setText("全部取消");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(false);
				}
			});
		}
		return jButton3;
	}

	private void selectAll(boolean isSelected) {
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			ContractBomCav bomCav = (ContractBomCav) list.get(i);
			bomCav.setIsSelected(isSelected);
		}
		tableModel.updateRows(list);
	}

	private List getSelectedData() {
		List lsResult = new ArrayList();
		List list = tableModel.getList();
		for (int i = 0; i < list.size(); i++) {
			ContractBomCav bomCav = (ContractBomCav) list.get(i);
			if (bomCav.getIsSelected()) {
				lsResult.add(bomCav);
			}
		}
		return lsResult;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
