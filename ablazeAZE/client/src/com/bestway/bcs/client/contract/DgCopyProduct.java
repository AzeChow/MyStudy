/*
 * Created on 2005-3-23
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.TempContractExg;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JTextField;

/**
 * @author
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgCopyProduct extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JPanel pn2 = null;

	private JSplitPane jSplitPane = null;

	private JToolBar jToolBar1 = null;

	private JPanel jPanel = null;

	private JTable tbFinishProduct = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel4 = null;

	private JToolBar jToolBar2 = null;

	private JTable tbUnitWaste = null;

	private ContractAction contractAction = null; // 合同

	private JTableListModel tableModelContract = null; // 表头

	private JTableListModel tableModelExg = null; // 合同成品

	private JTableListModel tableModelUnitWaste = null; // 合同单耗

	private Contract contract = null; // 合同对象

	private JScrollPane jScrollPane3 = null;

	private JLabel lbContractExg = null;

	private JLabel jLabel = null;

	private boolean isOk = false;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JLabel jLabel2 = null;

	private JPanel jPanel1 = null;

	private JTextField jTextField = null;

	/**
	 * @return Returns the isOk.
	 * 
	 * 
	 * 
	 * /**
	 * @throws java.awt.HeadlessException
	 */
	public DgCopyProduct() {
		super();
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		initialize();
		initUIComponents();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(732, 540);
		// this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("转抄成品并带出单耗和料件");
		this.setHelpId("dzsc");
	}

	public void setVisible(boolean isFlag) {
		if (isFlag == true) {
			showData();
		}
		super.setVisible(isFlag);
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
			jContentPane.add(getPn2(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPn2() {
		if (pn2 == null) {
			pn2 = new JPanel();
			pn2.setLayout(new BorderLayout());
			pn2.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return pn2;
	}

	/**
	 * @return Returns the tableModelContract.
	 */
	public JTableListModel getTableModelContract() {
		return tableModelContract;
	}

	/**
	 * @param tableModelContract
	 *            The tableModelContract to set.
	 */
	public void setTableModelContract(JTableListModel tableModelHead) {
		this.tableModelContract = tableModelHead;
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
			jSplitPane.setDividerLocation(200);
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel4());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText("\u9009\u62e9\u8f6c\u6284\u7684\u6210\u54c1(\",\"\u53f7\u5206\u5f00)");
			jLabel2.setBounds(new Rectangle(7, 6, 140, 15));
			jToolBar1 = new JToolBar();
			jToolBar1.add(getJButton());
			jToolBar1.add(getJButton1());
			jToolBar1.add(getJPanel1());
		}
		return jToolBar1;
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFinishProduct() {
		if (tbFinishProduct == null) {
			tbFinishProduct = new JTable();
			tbFinishProduct.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbFinishProduct
									.getModel();
							if (tableModel == null) {
								lbContractExg.setText("");
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								List list = new ArrayList();
								if (tableModel.getCurrentRow() != null) {
									ContractExg exg = ((TempContractExg) tableModel
											.getCurrentRow()).getContractExg();
									// lbContractExg.setText("当前单耗的成品序号："
									// + exg.getSeqNum() + " " + "名称："
									// + exg.getName());
									String parentId = exg.getId();
									list = contractAction
											.findContractBomByParentId(
													new Request(CommonVars
															.getCurrUser()),
													parentId);
								}
								initTbUnitWaste(list);
							}
						}
					});
		}
		return tbFinishProduct;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbFinishProduct());
		}
		return jScrollPane;
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
			jPanel4.add(getJToolBar2(), java.awt.BorderLayout.NORTH);
			jPanel4.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jToolBar2
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar2() {
		if (jToolBar2 == null) {
			jLabel = new JLabel();
			jLabel.setText("      单耗表");
			jLabel.setForeground(java.awt.Color.blue);
			lbContractExg = new JLabel();
			lbContractExg.setText("");
			lbContractExg.setForeground(java.awt.Color.blue);
			jToolBar2 = new JToolBar();
			jToolBar2.add(lbContractExg);
			jToolBar2.add(jLabel);
		}
		return jToolBar2;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbUnitWaste() {
		if (tbUnitWaste == null) {
			tbUnitWaste = new JTable();

		}
		return tbUnitWaste;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getTbUnitWaste());
		}
		return jScrollPane3;
	}

	/**
	 * 初始化单耗表
	 */
	private void initTbUnitWaste(List list) {
		JTableListModel.dataBind(tbUnitWaste, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("序号", "seqNum", 60, Integer.class));
						list.add(addColumn("料件总表序号", "contractImgSeqNum", 100,
								Integer.class));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("规格型号", "spec", 100));
						list.add(addColumn("单耗", "unitWaste", 100));
						list.add(addColumn("损耗", "wasteAmount", 100));
						list.add(addColumn("单项用量", "unitDosage", 100));
						list.add(addColumn("单价", "declarePrice", 100));
						list.add(addColumn("数量", "amount", 100));
						list.add(addColumn("单位", "unit.name", 100));
						list.add(addColumn("金额", "totalPrice", 100));
						list.add(addColumn("法定数量", "legalAmount", 100));
						list.add(addColumn("法定单位", "complex.firstUnit.name",
								100));
						list.add(addColumn("单位重量", "unitWeight", 100));
						list.add(addColumn("主料/辅料", "materialType", 100));
						list.add(addColumn("原产地", "country.name", 100));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
		tbUnitWaste.getColumnModel().getColumn(15).setCellRenderer(
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

						if (value.equals(MaterielType.FINISHED_PRODUCT)) {
							returnValue = "成品";
						} else if (value.equals(MaterielType.MACHINE)) {
							returnValue = "设备";
						} else if (value.equals(MaterielType.MATERIEL)) {
							returnValue = "主料";
						} else if (value
								.equals(MaterielType.ASSISTANT_MATERIAL)) {
							returnValue = "辅料";
						} else if (value.equals(MaterielType.WASTE_MATERIAL)) {
							returnValue = "消耗料";
						}
						return returnValue;
					}
				});
		tableModelUnitWaste = (JTableListModel) tbUnitWaste.getModel();
		// tbUnitWaste
		// .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始化成品表
	 * 
	 */
	private void initTbFinishProduct(List list) {
		JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
			public List InitColumns() {
				List list = new Vector();
				list.add(addColumn("选择", "isSelected", 100));
				list.add(addColumn("成品序号", "contractExg.seqNum", 60,
						Integer.class));
				list.add(addColumn("商品编码", "contractExg.complex.code", 80));
				list.add(addColumn("商品名称", "contractExg.name", 150));
				list.add(addColumn("规格型号", "contractExg.spec", 100));
				list.add(addColumn("出口数量", "contractExg.exportAmount", 100));
				list.add(addColumn("单位", "contractExg.unit.name", 80));
				list.add(addColumn("单价", "contractExg.unitPrice", 100));
				list.add(addColumn("总额", "contractExg.totalPrice", 100));
				list.add(addColumn("法定数量", "contractExg.legalAmount", 100));
				list.add(addColumn("法定单位",
						"contractExg.complex.firstUnit.name", 100));
				list.add(addColumn("原料费", "contractExg.materialFee", 100));
				list.add(addColumn("消费国", "contractExg.country.name", 100));
				list
						.add(addColumn("加工费单价", "contractExg.processUnitPrice",
								100));
				list.add(addColumn("加工费总价", "contractExg.processTotalPrice",
						100));
				list.add(addColumn("单位毛重", "contractExg.unitGrossWeight", 100));
				list.add(addColumn("单位净重", "contractExg.unitNetWeight", 100));
				list.add(addColumn("征减免税方式", "contractExg.levyMode.name", 100));
				list.add(addColumn("归并序号", "contractExg.credenceNo", 60));
				list.add(addColumn("备注", "contractExg.note", 100));
				return list;
			}
		};
		jTableListModelAdapter.setEditableColumn(1);
		tableModelExg = new JTableListModel(tbFinishProduct, list,
				jTableListModelAdapter);
		tbFinishProduct.getColumnModel().getColumn(1).setCellRenderer(
				new TableCheckBoxRender());
		tbFinishProduct.getColumnModel().getColumn(1).setCellEditor(
				new CheckBoxEditor(new JCheckBox()));

	}

	/**
	 * 编辑列
	 */

	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		private JCheckBox cb;

		private JTable table = null;

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
			if (obj instanceof TempContractExg) {
				TempContractExg temp = (TempContractExg) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}

	}

	/**
	 * 初始化组件
	 * 
	 */
	private void initUIComponents() {
	}

	/**
	 * 显示数据
	 * 
	 */
	private void showData() {
		this.contract = (Contract) this.tableModelContract.getCurrentRow();
		List list = new ArrayList();
		if (contract != null) {
			String parentId = contract.getId();
			list = contractAction.findTempContractExgByParentId(new Request(
					CommonVars.getCurrUser()), parentId);
		}
		initTbFinishProduct(list);
		if (list.size() <= 0) {
			initTbUnitWaste(new ArrayList());
		}
		if (tableModelExg.getCurrentRow() != null) {
			ContractExg exg = ((TempContractExg) tableModelExg.getCurrentRow())
					.getContractExg();
			String parentId = exg.getId();
			list = contractAction.findContractBomByParentId(new Request(
					CommonVars.getCurrUser()), parentId);
			initTbUnitWaste(list);
		}
	}

	public boolean isOk() {
		return isOk;
	}

	public Contract getContract() {
		return contract;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("执行转抄");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					done();
				}
			});
		}
		return jButton;
	}

	/**
	 * 执行
	 */
	private void done() {

		List list = tableModelExg.getList();
		List<ContractExg> contractExgList = new ArrayList<ContractExg>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof TempContractExg) {
				TempContractExg temp = (TempContractExg) list.get(i);
				if (temp.getIsSelected().booleanValue() == true) {
					contractExgList.add(temp.getContractExg());
				}
			}
		}

		if (contractExgList.size() <= 0) {
			if (JOptionPane.showConfirmDialog(null, "确定只转抄表头吗!!!", "提示",
					JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
		}
		contract = this.contractAction.copyContractProduct(new Request(
				CommonVars.getCurrUser(),true), contract, contractExgList);		
		isOk = true;
		this.dispose();
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("关闭");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.add(jLabel2, null);
			jPanel1.add(getJTextField(), null);
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
			jTextField.setBounds(new Rectangle(152, 3, 225, 22));
			jTextField.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					String[] keys = (jTextField.getText().trim() + e
							.getKeyChar()).trim().split(",");
					if (keys.length > 0) {
						List list = tableModelExg.getList();
						for (int i = 0; i < list.size(); i++) {
							TempContractExg temp = (TempContractExg) list
									.get(i);
							if (temp.getContractExg().getSeqNum() != null) {
								temp.setIsSelected(checkKey(keys, temp
										.getContractExg().getSeqNum()
										.toString()));
							}
						}
						tableModelExg.setList(list);
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
