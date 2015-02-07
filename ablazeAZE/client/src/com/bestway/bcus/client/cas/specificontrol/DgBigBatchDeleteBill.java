/*
 * Created on 2005-6-8
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.cas.specificontrol;

/**
 * 大批量删除、回卷、生效单据 checked 2008-10-11
 * 
 * @author ?
 * 
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.BaseEntity;
import com.bestway.common.Request;
import com.bestway.common.client.erpbillcenter.DgBillMaster;
import com.bestway.common.client.erpbillcenter.FmBill;
import com.bestway.common.constant.SBillType;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author ls // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgBigBatchDeleteBill extends JDialogBase {

	private JPanel jContentPane = null;

	private CasAction casAction = null;

	private JToolBar jToolBar = null;

	private JButton btnSelectAll = null;

	private JButton btnRollback = null;

	private JButton btnDelete = null;

	private JButton btnExit = null;

	private JComboBox cbQuery = null;

	private JTable tb1 = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel1 = null;

	private JTabbedPane jTabbedPane = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tb2 = null;

	private JComboBox cbbBillType = null;

	private JLabel jLabel = null;

	private JButton btnEffect = null;
	/**
	 * 单据类型对象
	 */
	private BillType billType = null; // @jve:decl-index=0:

	/**
	 * This method initializes
	 * 
	 */
	public DgBigBatchDeleteBill() {
		super(false);
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
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
		this.setTitle("大批量删除、回卷、生效单据");
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
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), java.awt.BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * 初始化数据
	 * 
	 */
	private void initUIComponents() {
		this.initTable(new ArrayList(), tb2);
		this.initTable(new ArrayList(), tb1);
		cbQuery.setSelectedIndex(0);
		this.casBillOption = casAction.findCasBillOption(new Request(CommonVars
				.getCurrUser()));
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJPanel());
			jToolBar.add(getBtnSelectAll());
			jToolBar.add(getBtnEffect());
			jToolBar.add(getBtnEffectAll());
			jToolBar.add(getBtnRollback());
			jToolBar.add(getBtnDelete());

			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 初始化表
	 * 
	 * @param list
	 * @param tb
	 * @return
	 */
	private JTableListModel initTable(List list, JTable tb) {
		JTableListModel tableModel = new JTableListModel(tb, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据编号", "billNo", 100));
						list.add(addColumn("单据类型", "billType.name", 100));
						list.add(addColumn("生效日期", "validDate", 120));
						list.add(addColumn("客户名称", "scmCoc.name", 150));
						list.add(addColumn("有效", "isValid", 100));
						list.add(addColumn("是否记帐", "keepAccounts", 100));
						return list;
					}
				});
		tb.getColumnModel().getColumn(5).setCellRenderer(
				new TableCheckBoxRender());
		tb.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		tb.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return tableModel;
	}

	/**
	 * This method initializes btnSelectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSelectAll() {
		if (btnSelectAll == null) {
			btnSelectAll = new JButton();
			btnSelectAll.setText("全选");
		}
		btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (jTabbedPane.getSelectedIndex() == 1) {
					tb2.selectAll();
				} else {
					tb1.selectAll();
				}
			}
		});
		return btnSelectAll;
	}

	/**
	 * This method initializes btnRollback
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRollback() {
		if (btnRollback == null) {
			btnRollback = new JButton();
			btnRollback.setText("回卷");
			btnRollback.setEnabled(false);
			btnRollback.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel jtm = (JTableListModel) tb2.getModel();
					if (jtm.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "您没有选中任何记录！",
								"提示信息", JOptionPane.WARNING_MESSAGE);
						return;
					}
					List<BillMaster> billMasterlist = jtm.getCurrentRows();
					if (checkKeepAcount(billMasterlist)) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "回卷的单据中包含有已记帐的单据。",
								"提示信息", JOptionPane.ERROR_MESSAGE);
					} else {
						if (JOptionPane.showConfirmDialog(
								DgBigBatchDeleteBill.this, "您确定要回卷吗？", "提示信息",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							//
							// 已做对应不可已回卷
							//
							for (BillMaster item : billMasterlist)
								if (!isUnreel1(item)) {
									return;
								}
							try {
								List list = casAction.saveRollbackBillMaster(
										new Request(CommonVars.getCurrUser()),
										billMasterlist);
								jtm.deleteRows(list);
							} catch (Exception r) {
								r.printStackTrace();
							}
						}
					}
				}
			});
		}
		return btnRollback;
	}

	/**
	 * 判断单据中是否有记帐的
	 * 
	 * @param billMasterlist
	 *            要判断的单据
	 * @return boolean 如果包含已记帐的返回true，否则返回false
	 */
	private boolean checkKeepAcount(List billMasterlist) {
		if (billMasterlist != null) {
			for (int i = 0; i < billMasterlist.size(); i++) {
				Boolean keepAccounts = ((BillMaster) billMasterlist.get(i))
						.getKeepAccounts();
				if (keepAccounts != null && keepAccounts.booleanValue() == true) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel jtm = (JTableListModel) tb1.getModel();
					if (jtm.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "您没有选中任何记录！",
								"提示信息", JOptionPane.WARNING_MESSAGE);
						return;
					}
					List<BillMaster> billMasterlist = jtm.getCurrentRows();
					List<BillMaster> deleteBillMasterlist = new ArrayList<BillMaster>();
					if (JOptionPane.showConfirmDialog(
							DgBigBatchDeleteBill.this, "您确定要删除吗？", "提示信息",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						String err = "";
						Boolean isPass = true;
						for (BillMaster item : billMasterlist) {
							//
							// 已做对应不可已回卷
							//
							String s = "";
							if (!(s = isUnreel(item)).equals("")) {
								isPass = false;
								err = err + s;
							}
							if (isPass) {
								deleteBillMasterlist.add(item);
							}
							isPass = true;
						}
						if (err != null && !err.equals("")) {
							JOptionPane.showMessageDialog(
									DgBigBatchDeleteBill.this, err, "提示",
									JOptionPane.INFORMATION_MESSAGE);
							// return;
						}
						try {
							casAction.deleteBatchBillMaster(new Request(
									CommonVars.getCurrUser()),
									deleteBillMasterlist);
							jtm.deleteRows(deleteBillMasterlist);
						} catch (Exception r) {
							r.printStackTrace();
							JOptionPane.showMessageDialog(
									DgBigBatchDeleteBill.this, "可能存在外键引用！",
									"提示信息", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 是否回卷
	 * 
	 * @param billMaster
	 * @return
	 */
	private String isUnreel(BillMaster billMaster) {
		String err = "";
		List detailList = casAction.findBillDetail(new Request(CommonVars
				.getCurrUser()), billMaster.getId(), this.billType
				.getBillType());
		for (int i = 0; i < detailList.size(); i++) {
			BillDetail bill = (BillDetail) detailList.get(i);
			if (bill.getCustomNo() != null && !bill.getCustomNo().equals("")
					&& bill.getCustomNum() != null && bill.getCustomNum() > 0.0) {
				if (CasCommonVars.getBillCorrespondingControl()
						.getIsHandContrl() != null
						&& CasCommonVars.getBillCorrespondingControl()
								.getIsHandContrl() == true) {
				} else {
					err = "\n警告:单据号为:" + billMaster.getBillNo()
							+ "的单据其单据体数据已与报关单进行了对应，不能删除！";
					break;
				}
			}
		}
		return err;
	}

	/**
	 * 是否回卷
	 * 
	 * @return boolean
	 */
	private boolean isUnreel1(BillMaster billMaster) {
		List detailList = casAction.findBillDetail(new Request(CommonVars
				.getCurrUser()), billMaster.getId(), this.billType
				.getBillType());
		for (int i = 0; i < detailList.size(); i++) {
			BillDetail bill = (BillDetail) detailList.get(i);
			if (bill.getCustomNo() != null && !bill.getCustomNo().equals("")
					&& bill.getCustomNum() != null && bill.getCustomNum() > 0.0) {
				if (CasCommonVars.getBillCorrespondingControl()
						.getIsHandContrl() != null
						&& CasCommonVars.getBillCorrespondingControl()
								.getIsHandContrl() == true) {
				} else {
					JOptionPane.showMessageDialog(DgBigBatchDeleteBill.this,
							"其单据体数据已与报关单进行了对应，不能回卷！", "提示！",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method initializes btnExit
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
	 * This method initializes cbQuery
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbQuery() {
		if (cbQuery == null) {
			cbQuery = new JComboBox();
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.MATERIEL_IN), "料件入仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.MATERIEL_OUT), "料件出仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.PRODUCE_IN), "成品入仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.PRODUCE_OUT), "成品出仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.FIXTURE_IN), "设备入仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.FIXTURE_OUT), "设备出仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.HALF_PRODUCE_INOUT), "半成品出入仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.REMAIN_PRODUCE_INOUT), "残次品出入仓"));
			cbQuery.addItem(new ItemProperty(String
					.valueOf(SBillType.LEFTOVER_MATERIEL_INOUT), "边角料出入仓"));
			cbQuery.setBounds(60, 3, 145, 24);
			cbQuery.setSelectedIndex(-1);
			cbQuery.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						ItemProperty item = (ItemProperty) e.getItem();
						initCbbTypeCode(Integer.parseInt(item.getCode()));
					}
				}
			});
		}
		return cbQuery;
	}

	/**
	 * 初始化单据类型
	 * 
	 * @param billCategory
	 */

	private void initCbbTypeCode(int billCategory) {
		List billTypes = this.casAction.findBillType(new Request(CommonVars
				.getCurrUser()), billCategory);
		cbbBillType.removeAllItems();
		for (int i = 0; i < billTypes.size(); i++) {
			BillType billType = (BillType) billTypes.get(i);
			cbbBillType.addItem(billType);
		}
	}

	/**
	 * This method initializes tb1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTb1() {
		if (tb1 == null) {
			tb1 = new JTable();
		}
		return tb1;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTb1());
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
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(208, 3, 57, 24));
			jLabel.setText("单据类型");
			jLabel1 = new JLabel();
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jLabel1.setBounds(8, 3, 50, 24);
			jLabel1.setText("进出仓别");
			jPanel.add(getCbQuery(), null);
			jPanel.add(jLabel1, null);
			jPanel.add(getCbbBillType(), null);
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();

			jTabbedPane.addTab("未生效", null, getJScrollPane(), null);
			jTabbedPane.addTab("已生效", null, getJScrollPane1(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							setState();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTb2());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tb2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTb2() {
		if (tb2 == null) {
			tb2 = new JTable();
		}
		return tb2;
	}

	/**
	 * This method initializes cbbBillType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbBillType() {
		if (cbbBillType == null) {
			cbbBillType = new JComboBox();
			cbbBillType.setBounds(new java.awt.Rectangle(266, 3, 173, 24));
			cbbBillType.setRenderer(new UserListCellRenderer());
			cbbBillType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						pnCommonQueryPage.setInitState();
					}
				}
			});
		}
		return cbbBillType;
	}

	/**
	 * 获取单据类别列表框的内容
	 * 
	 * @author ower
	 * 
	 */

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			BillType billType = (BillType) value;
			if (billType != null) {
				s = billType.getName();
			}
			setText(s);
			return this;
		}
	}

	/**
	 * This method initializes btnEffect
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffect() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel jtm = (JTableListModel) tb1.getModel();
					if (jtm.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "您没有选中任何记录！",
								"提示信息", JOptionPane.WARNING_MESSAGE);
						return;
					}
					List billMasterlist = jtm.getCurrentRows();
					if (!checkIsCanValid(billMasterlist)) {
						if (JOptionPane.showConfirmDialog(
								DgBigBatchDeleteBill.this, "您确定要生效选中记录吗？",
								"提示信息", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							try {
								List list = casAction.saveEffectBillMasterAll(
										new Request(CommonVars.getCurrUser()),
										billMasterlist);
								jtm.deleteRows(list);
							} catch (Exception r) {
								r.printStackTrace();
							}
						}
					}
				}
			});
		}
		return btnEffect;
	}

	/**
	 * 海关帐基本单据项目设定
	 */
	private CasBillOption casBillOption; // @jve:decl-index=0:

	/**
	 * 通过单据中有没有生效时间、客户供应商、单据体判断选中的单据是否可以生效
	 * 
	 * @param billMasterlist
	 *            要判断的单据
	 * @return boolean 单生效日期、客户(供应商)、单据体为空、单据体填写不完整时返回true，否则false
	 */
	private boolean checkIsCanValid(List billMasterlist) {
		ItemProperty itmes = (ItemProperty) cbQuery.getSelectedItem();
		String name = itmes.getName();
		System.out.println("name=" + name);
		if (billMasterlist != null) {
			for (int i = 0; i < billMasterlist.size(); i++) {
				BillMaster billMaster = (BillMaster) billMasterlist.get(i);
				if (billMaster.getValidDate() == null) {
					JOptionPane.showMessageDialog(DgBigBatchDeleteBill.this,
							"其中单据号为 " + billMaster.getBillNo()
									+ " 的单据没有生效日期，不能生效！", "提示！", 2);
					return true;
				}
				//
				// 单据号
				//
				int billCode = Integer.parseInt(this.billType.getCode());
				if (billCode == 1101
						&& (this.casBillOption.getIsOutNeedCustomer() == null || this.casBillOption
								.getIsOutNeedCustomer().booleanValue() == false)) {
					// 
				} else if (billCode == 2002
						&& (this.casBillOption.getIsInNeedCustomer() == null // 2002
						// 车间入库
						|| this.casBillOption.getIsInNeedCustomer()
								.booleanValue() == false)) {
					//
				}

				else if (name.equals("料件入仓")
						&& (billCode == 1006 && this.casBillOption
								.getIsWorkShopBack())
						&& billMaster.getScmCoc() == null) {
					JOptionPane.showMessageDialog(DgBigBatchDeleteBill.this,
							"其中单据号为 " + billMaster.getBillNo()
									+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
					return true;
				}

				else if (name.equals("料件入仓")) {
					if (billMaster.getScmCoc() == null
							&& (billCode != 1001 && billCode != 1002
									&& billCode != 1010 && billCode != 1007 && billCode != 1006)) {
						System.out.println("ssss");
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "其中单据号为 "
										+ billMaster.getBillNo()
										+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);

						return true;
					}
				} else if (name.equals("料件出仓")) {
					if (billMaster.getScmCoc() == null
							&& (billCode != 1104 && billCode != 1105)) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "其中单据号为 "
										+ billMaster.getBillNo()
										+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
						return true;
					}
				} else if (name.equals("成品入仓")) {
					if (billMaster.getScmCoc() == null
							&& (billCode != 2001 && billCode != 2004 && billCode != 2005)) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "其中单据号为 "
										+ billMaster.getBillNo()
										+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
						return true;
					}
				} else if (name.equals("成品出仓")
						&& (billCode == 2103 && this.casBillOption
								.getIsBackWorkShop())
						&& billMaster.getScmCoc() == null) {
					JOptionPane.showMessageDialog(DgBigBatchDeleteBill.this,
							"其中单据号为 " + billMaster.getBillNo()
									+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
					return true;
				} else if (name.equals("成品出仓")) {
					if (billMaster.getScmCoc() == null
							&& (billCode != 2105 && billCode != 2109 && billCode != 2103)) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "其中单据号为 "
										+ billMaster.getBillNo()
										+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
						return true;
					}
				} else if (name.equals("成品出仓")) {
					if (billMaster.getScmCoc() == null)
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "其中单据号为 "
										+ billMaster.getBillNo()
										+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
					return true;
				} else if (!name.equals("成品入仓") && !name.equals("料件入仓")
						&& !name.equals("料件出仓") && !name.equals("料件出仓")
						&& !name.equals("残次品出入仓")) {
					if (billMaster.getScmCoc() == null
							&& (billCode != 2001 && billCode != 2004 && billCode != 2005)) {
						JOptionPane.showMessageDialog(
								DgBigBatchDeleteBill.this, "其中单据号为 "
										+ billMaster.getBillNo()
										+ " 的单据没有客户(供应商)，不能生效！", "提示！", 2);
						return true;
					}
				}
				ItemProperty item = (ItemProperty) cbQuery.getSelectedItem();
				List billDetaillist = casAction.findBillDetail(new Request(
						CommonVars.getCurrUser()), billMaster.getId(), Integer
						.valueOf(item.getCode()));
				if (billDetaillist == null || billDetaillist.size() == 0) {
					JOptionPane.showMessageDialog(DgBigBatchDeleteBill.this,
							"其中单据号为 " + billMaster.getBillNo()
									+ " 的单据没有单据体资料，不能生效！", "提示！", 2);
					return true;
				} else {
					for (int j = 0; j < billDetaillist.size(); j++) {
						BillDetail bill = (BillDetail) billDetaillist.get(j);
						if (bill.getPtAmount() == null
								|| bill.getPtAmount().doubleValue() == 0
								|| bill.getHsName() == null
								|| bill.getHsName().equals("")
								|| bill.getPtName() == null
								|| bill.getPtName().equals("")
								|| bill.getWareSet() == null) {
							JOptionPane.showMessageDialog(
									DgBigBatchDeleteBill.this, "其中单据号为 "
											+ billMaster.getBillNo()
											+ " 的单据中的单据体数据填写不完整，不能生效！", "提示！",
									2);
							return true;
						}
					}

				}
			}
		}
		return false;
	}

	/**
	 * 获得数据源
	 * 
	 * @param index
	 * @param length
	 * @param property
	 * @param value
	 * @param isLike
	 * @return
	 */
	public List getDataSource(int index, int length, String property,
			Object value, boolean isLike) {
		billType = (BillType) cbbBillType.getSelectedItem();
		if (billType == null) {
			return new ArrayList();
		}
		boolean isEffect = this.jTabbedPane.getSelectedIndex() == 1; // 生效
		return this.casAction.findBillMaster(new Request(CommonVars
				.getCurrUser()), billType, isEffect, index, length, property,
				value, isLike);
	}

	/**
	 * 查询
	 */

	PnCommonQueryPage pnCommonQueryPage = null;

	private JToolBar jToolBar1 = null;

	private JButton btnEffectAll = null;

	private JPanel jPanel1 = null;

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {

				@Override
				public JTableListModel initTable(List dataSource) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						return DgBigBatchDeleteBill.this.initTable(dataSource,
								tb2);
					} else {
						return DgBigBatchDeleteBill.this.initTable(dataSource,
								tb1);
					}

				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return DgBigBatchDeleteBill.this.getDataSource(index,
							length, property, value, isLike);
				}
			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.add(getPnCommonQueryPage());
		}
		return jToolBar1;
	}

	/**
	 * 设置状态
	 * 
	 * @param isImport
	 */
	private void setState() {
		this.btnEffect.setEnabled(jTabbedPane.getSelectedIndex() == 0);
		this.btnEffectAll.setEnabled(jTabbedPane.getSelectedIndex() == 0);
		this.btnDelete.setEnabled(jTabbedPane.getSelectedIndex() == 0);
		this.btnRollback.setEnabled(jTabbedPane.getSelectedIndex() == 1);
		this.cbbBillType.setSelectedItem(null);
	}

	/**
	 * This method initializes btnEffectAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffectAll() {
		if (btnEffectAll == null) {
			btnEffectAll = new JButton();
			btnEffectAll.setText("全部生效");
			btnEffectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JTableListModel jtm = (JTableListModel) tb1.getModel();
					List billMasterlist = jtm.getList();
					if (!checkIsCanValid(billMasterlist)) {
						if (JOptionPane.showConfirmDialog(
								DgBigBatchDeleteBill.this, "您确定要生效当前单据的所有记录吗？",
								"提示信息", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							try {
								billType = (BillType) cbbBillType
										.getSelectedItem();
								casAction.saveBillMasterEffect(new Request(
										CommonVars.getCurrUser()), billType,
										true);
								initTable(new ArrayList(), tb1);
							} catch (Exception r) {
								r.printStackTrace();
							}
						}
					}
				}
			});
		}
		return btnEffectAll;
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
			jPanel1.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

}
