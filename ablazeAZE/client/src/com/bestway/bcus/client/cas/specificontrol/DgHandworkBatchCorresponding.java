/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.DgBillMaster;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgHandworkBatchCorresponding extends JDialogBase {

	private JPanel			jContentPane			= null;
	private JToolBar		jToolBar				= null;
	private JButton			btnSearch				= null;
	private JButton			btnAllSelect			= null;
	private JButton			btnWrite				= null;
	private JButton			btnExit					= null;
	private JComboBox		cbbType					= null;
	private JTable			jTable					= null;
	private JScrollPane		jScrollPane				= null;
	private JPanel			jPanel					= null;
	private JLabel			jLabel					= null;
	private JLabel			jLabel1					= null;
	private JPanel			jPanel1					= null;
	private JLabel			jLabel2					= null;
	private JTextField		tfText					= null;
	private JTable			jTable1					= null;
	private JScrollPane		jScrollPane1			= null;
	private JLabel			jLabel3					= null;
	private JTableListModel	tableModelBillDetail	= null;
	private JTableListModel	tableModelBill			= null;
	private CasAction		casAction				= null;
	private Integer 		maximumFractionDigits 	= CasCommonVars.getOtherOption()
	.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
	.getOtherOption().getInOutMaximumFractionDigits();

	/**
	 * This method initializes
	 * 
	 */
	public DgHandworkBatchCorresponding() {
		super(false);
		initialize();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initUIComponents();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("手工批量对应");
		this.setContentPane(getJContentPane());
		this.setSize(755, 539);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel3 = new JLabel();
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jLabel3.setBounds(18, 313, 102, 21);
			jLabel3.setText("单据明细");
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(jLabel3, null);
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
			jLabel = new JLabel();
			jToolBar = new JToolBar();
			jLabel.setText("                                                ");
			jToolBar.setBounds(0, 0, 747, 34);
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnSearch());
			jToolBar.add(getBtnAllSelect());
			jToolBar.add(getJPanel1());
			jToolBar.add(getBtnWrite());
			jToolBar.add(getBtnExit());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAllSelect() {
		if (btnAllSelect == null) {
			btnAllSelect = new JButton();
			btnAllSelect.setText("全选");
			btnAllSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					allSelect();
				}
			});
		}
		return btnAllSelect;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnWrite() {
		if (btnWrite == null) {
			btnWrite = new JButton();
			btnWrite.setText("写入");
			btnWrite.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					writeIn();
				}
			});
		}
		return btnWrite;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbType() {
		if (cbbType == null) {
			cbbType = new JComboBox();
			cbbType.setBounds(60, 3, 145, 24);

		}
		return cbbType;
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
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						DgBillMaster dgBillMaster = new DgBillMaster(null);
						JTableListModel model = (JTableListModel) jTable
								.getModel();
						dgBillMaster.setTableModel(model);
						BillType billType = ((BillMaster) model.getCurrentRow())
								.getBillType();
						dgBillMaster.setBillType(billType);
						dgBillMaster.setDataState(DataState.BROWSE);
						dgBillMaster.setVisible(true);
					}
				}
			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) jTable
									.getModel();
							if (tableModel == null) {
								return;
							}
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (!lsm.isSelectionEmpty()) {
								List list = new ArrayList();
								if (tableModel.getCurrentRow() != null) {
                                    BillMaster bm = ((BillMaster) tableModel
                                            .getCurrentRow());
									String parentId = bm.getId();
                                    int billType = bm.getBillType().getBillType();
									list = casAction.findBillDetail(
											new Request(CommonVars
													.getCurrUser()), parentId,billType);
								}
								initJTable1(list);
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
			jScrollPane.setBounds(0, 34, 747, 278);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel1 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel1.setBounds(8, 3, 50, 24);
			jLabel1.setText("单据类型");
			jPanel.add(getCbbType(), null);
			jPanel.add(jLabel1, null);
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
			jLabel2 = new JLabel();
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jLabel2.setText("对应关系");
			jLabel2.setBounds(6, 5, 55, 18);
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
		if (tfText == null) {
			tfText = new JTextField();
			tfText.setBounds(64, 4, 151, 24);
		}
		return tfText;
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(0, 336, 745, 175);
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		this.cbbType.removeAllItems();
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_IMPORT).toString(), "直接进口"));
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_IMPORT).toString(), "结转进口"));
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.MATERIAL_EXCHANGE).toString(), "料件退换"));
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.MATERIAL_REOUT).toString(), "料件复出"));
		// 初始化出口类型
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.DIRECT_EXPORT).toString(), "直接出口"));
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.TRANSFER_FACTORY_EXPORT).toString(), "结转出口"));
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.BACK_FACTORY_REWORK).toString(), "退厂返工"));
		this.cbbType.addItem(new ItemProperty(Integer.valueOf(
				ImpExpType.REWORK_EXPORT).toString(), "返工复出"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(this.cbbType);
		this.cbbType.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbType.setSelectedItem(null);
		//
		// 初始化表
		//
		initJTable(new ArrayList());
		initJTable1(new ArrayList());
	}

	/**
	 * 查询
	 * 
	 */
	private void search() {
		ItemProperty item = (ItemProperty) this.cbbType.getSelectedItem();
		if (item == null) {
			return;
		}
		Integer impExpType = Integer.valueOf(item.getCode());
		List dataSource = this.casAction.findBillMaster(new Request(CommonVars
				.getCurrUser()), impExpType);
		initJTable(dataSource);
	}

	/**
	 * 写入
	 * 
	 */
	private void writeIn() {
		if ("".equals(this.tfText.getText())) {
			JOptionPane.showMessageDialog(this, "对应关系不可为空!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (this.tableModelBill.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择要对应的单据!!", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		List list = this.tableModelBill.getCurrentRows();
		this.casAction.writeIn(new Request(CommonVars.getCurrUser()), list,
				this.tfText.getText());
		tableModelBill.deleteRows(list);

	}

	/**
	 * 全选
	 * 
	 */
	private void allSelect() {
		this.jTable.selectAll();
	}

	/**
	 * 初始化BillMaster
	 * 
	 */
	private void initJTable(List list) {
		tableModelBill = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类别", "billType.name", 100));
						list.add(addColumn("单据号", "billNo", 100));
						list.add(addColumn("仓库", "wareSet.name", 80));
						list.add(addColumn("客户名称", "scmCoc.name", 100));
						list.add(addColumn("有效", "isValid", 50));
						list.add(addColumn("生效日期", "validDate", 100));
						list.add(addColumn("是否记帐", "keepAccounts", 50));
						return list;
					}
				});
		jTable.getColumnModel().getColumn(5).setCellRenderer(
				new TableCheckBoxRender());
		jTable.getColumnModel().getColumn(7).setCellRenderer(
				new TableCheckBoxRender());
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	/**
	 * 初始化表明细
	 * 
	 */
	private void initJTable1(List list) {
		tableModelBillDetail = new JTableListModel(jTable1, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品料号", "ptPart", 100));
						list.add(addColumn("商品名称", "ptName", 150));
						list.add(addColumn("商品规格", "ptSpec", 100));
						list.add(addColumn("商品版本", "version", 100));
						list.add(addColumn("数量", "ptAmount", 60));
						list.add(addColumn("单价", "ptPrice", 60));
						list.add(addColumn("金额", "money", 60));
						list.add(addColumn("制单号", "productNo", 80));
						list.add(addColumn("报关单号", "customNo", 80));
						list.add(addColumn("对应报关数量", "customNum", 120));
						list.add(addColumn("商品海关编码", "complex.code", 120));
						list.add(addColumn("报关商品名称", "hsName", 150));
						list.add(addColumn("报关商品规格 ", "htSpec", 120));
						list.add(addColumn("折算报关数量", "hsAmount", 120));
						list.add(addColumn("报关单价", "hsPrice", 80));
						return list;
					}
				});
	}

}
