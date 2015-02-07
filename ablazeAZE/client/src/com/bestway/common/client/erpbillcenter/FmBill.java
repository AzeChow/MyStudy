/*
 * Created on 2004-9-15
 *fillList
 * //LM于2008-9-9修改
 * Window - Preferences - Java - Code Style - Code Templates
 * checked by 陈井彬   date:2008.10.25
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnMutiConditionQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.Request;
import com.bestway.common.constant.SBillType;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author checked 2008-09-09 by yp checked by 陈井彬 date:2008.10.25
 */
public class FmBill extends JInternalFrameBase {

	private javax.swing.JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JScrollPane jScrollPane = null;

	/**
	 * 单据类别列表
	 */
	private JList listOrder = null;

	/**
	 * 新增按钮
	 */
	private JButton btnAdd = null;

	/**
	 * 修改按钮
	 */
	private JButton btnEdit = null;

	/**
	 * 删除按钮
	 */
	private JButton btnDelete = null;

	/**
	 * 刷新按钮
	 */
	private JButton btnRefresh = null;
	private JToolBar jToolBar = null;

	/**
	 * 单据信息表格
	 */
	private JTable tableOrderInfo = null;
	private JScrollPane jScrollPane1 = null;
	private CasAction casAction = null; // @jve:decl-index=0:

	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;
	private JTableListModel tableModel = null;
	private JPanel jPanel3 = null;

	/**
	 * 退出按钮
	 */
	private JButton btnExit = null;
	private JPanel jPanel4 = null;
	private JPanel jPanel5 = null;

	/**
	 * 料件入仓
	 */
	private JRadioButton rbImgImport = null;

	/**
	 * 料件出仓
	 */
	private JRadioButton rbImgOutput = null;

	/**
	 * 成品入仓
	 */
	private JRadioButton rbExgImport = null;

	/**
	 * 成品出仓
	 */
	private JRadioButton rbExgOutput = null;

	/**
	 * 设备入仓
	 */
	private JRadioButton rbDevImport = null;

	/**
	 * 设备出仓
	 */
	private JRadioButton rbDevOutput = null;

	/**
	 * 半成品出入仓
	 */
	private JRadioButton rbHalfExg = null;

	/**
	 * 残次品出入仓
	 */
	private JRadioButton rbInferior = null;

	/**
	 * 边角料出入仓
	 */
	private JRadioButton rbScrap = null;
	private ButtonGroup group = new ButtonGroup(); // @jve:decl-index=0:

	/**
	 * 单据类别
	 */
	private BillType billType = null;
	private JPanel pnCommon = null;
	private JToolBar jToolBar1 = null;

	/**
	 * 海关帐基本单据项目设定
	 */
	private CasBillOption casBillOption;

	/**
	 * 进出仓栏小数位数控制
	 */
	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:

	/**
	 * 单据出入仓类型
	 */
	private int billCategory = SBillType.MATERIEL_IN; // @jve:decl-index=0:

	/**
	 * This is the default constructor 构造函数
	 */
	public FmBill() {
		super();

		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkBillByBrower(new Request(CommonVars
				.getCurrUser()));

		/**
		 * 初始化海关帐远程接口
		 */
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casBillOption = casAction.findCasBillOption(new Request(CommonVars
				.getCurrUser()));
		initialize();
		this.addInternalFrameListener(new InternalFrameAdapter() {
			// 将JRadioButton进行分组
			public void internalFrameOpened(InternalFrameEvent e) {
				group.add(rbImgImport);
				group.add(rbImgOutput);
				group.add(rbExgImport);
				group.add(rbExgOutput);
				group.add(rbDevImport);
				group.add(rbDevOutput);
				group.add(rbHalfExg);
				group.add(rbInferior);
				group.add(rbScrap);

				rbImgImport.getModel().setSelected(true);

				if (rbImgImport.getModel().isSelected()) {
					billCategory = SBillType.MATERIEL_IN;
				} else if (rbImgOutput.getModel().isSelected()) {
					billCategory = SBillType.MATERIEL_OUT;
				} else if (rbExgImport.getModel().isSelected()) {
					billCategory = SBillType.PRODUCE_IN;
				} else if (rbExgOutput.getModel().isSelected()) {
					billCategory = SBillType.PRODUCE_OUT;
				} else if (rbDevImport.getModel().isSelected()) {
					billCategory = SBillType.FIXTURE_IN;
				} else if (rbDevOutput.getModel().isSelected()) {
					billCategory = SBillType.FIXTURE_OUT;
				} else if (rbHalfExg.getModel().isSelected()) {
					billCategory = SBillType.HALF_PRODUCE_INOUT;
				} else if (rbInferior.getModel().isSelected()) {
					billCategory = SBillType.REMAIN_PRODUCE_INOUT; // 残次品
				} else if (rbInferior.getModel().isSelected()) {
					billCategory = SBillType.LEFTOVER_MATERIEL_INOUT; // 边角料
				}
				fillList(billCategory);
				initUI();
				getDataSource();
			}

		});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("单据资料");
		this.setSize(956, 474);
		this.setContentPane(getJContentPane());
		btnTally.setEnabled(false);
		btnEfficient.setEnabled(false);
		btnRollBack.setEnabled(false);

	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		List list1 = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));
		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list1.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name");
		this.cbbScmCoc.setUI(new CustomBaseComboBoxUI(250));
		this.cbbScmCoc.setSelectedItem(null);

		cbbABeginDate.setDate(CommonVars.getBeginDate());
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
			jContentPane.add(getJPanel3(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
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
			jSplitPane.setDividerSize(2);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setDividerLocation(120);
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
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.NORTH);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jPanel2 = new JPanel();
			jLabel.setText("单据类别");
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getListOrder());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes listOrder
	 * 
	 * @return javax.swing.JList
	 */
	private JList getListOrder() {
		if (listOrder == null) {
			listOrder = new JList();
			listOrder
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true) {
								return;
							}
							BillType selectedBillType = (BillType) (((JList) e
									.getSource()).getSelectedValue());
							FmBill.this.setBillType((BillType) listOrder
									.getSelectedValue());

							// 期初单的都不做单据检查
							String billTypeCode = "";
							if (FmBill.this.billType != null) {
								billTypeCode = FmBill.this.billType.getCode();
							}
							if ("1001".equals(billTypeCode)
									|| "1002".equals(billTypeCode)
									|| "1014".equals(billTypeCode)
									|| "1015".equals(billTypeCode)
									|| "1016".equals(billTypeCode)
									|| "2001".equals(billTypeCode)
									|| "2011".equals(billTypeCode)
									|| "2012".equals(billTypeCode)
									|| "5002".equals(billTypeCode)
									|| "6003".equals(billTypeCode)) {
								getMiCheckPart().setEnabled(false);
							} else {

								getMiCheckPart().setEnabled(true);
							}
							if ("1020".equals(billTypeCode)) {
								lbCountryPay.setVisible(true);
							} else {
								lbCountryPay.setVisible(false);
							}
//							pnCommonQueryPage.setInitState();
							getDataSource();
						}
					});
		}
		return listOrder;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.setPreferredSize(new Dimension(60, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.checkBillByUpdate(new Request(
							CommonVars.getCurrUser()));

					if (listOrder.getSelectedIndex() == -1 || billType == null) {
						JOptionPane.showMessageDialog(FmBill.this, "请选择类型",
								"确认", 2);
						return;
					}
					int year = com.bestway.bcus.client.cas.parameter.CasCommonVars
							.getYearInt();
					Calendar day = Calendar.getInstance();
					day.set(Calendar.YEAR, year);
					DgBillMaster dgBillMaster = new DgBillMaster();
					dgBillMaster.setTableModel(tableModel);
					dgBillMaster.setBillType(billType);
					dgBillMaster.setBillCategoryBillMaster(billCategory);
					dgBillMaster.setDataState(DataState.ADD);
					dgBillMaster.setValidDate(day.getTime());
					dgBillMaster.setVisible(true);
				}
			});

		}
		return btnAdd;
	}

	/**
	 * This method initializes btnEdit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.checkBillByUpdate(new Request(
							CommonVars.getCurrUser()));

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBill.this,
								"请选择你要修改的资料", "确认", 2);
						return;
					}
					editData(billCategory);
				}

			});

		}
		return btnEdit;
	}

	/**
	 * 修改Data
	 */
	private void editData(int billCategory) {
		BillMaster m = (BillMaster) tableModel.getCurrentRow();
		DgBillMaster dgBillMaster = new DgBillMaster();
		dgBillMaster.setDataState(DataState.EDIT);
		dgBillMaster.setBillType(billType);
		dgBillMaster.setTableModel(tableModel);
		dgBillMaster.setBillCategoryBillMaster(billCategory);
		dgBillMaster.setValidDate(m.getValidDate());
		dgBillMaster.setVisible(true);

	}

	/**
	 * This method initializes btndelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					//
					// check authority
					//
					ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
							.getApplicationContext().getBean(
									"erpBillCenterAuthority");
					erpBillCenterAuthority.checkBillByDelete(new Request(
							CommonVars.getCurrUser()));

					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBill.this,
								"请选择你要删除的资料", "确认", 2);
						return;
					}
					if (((BillMaster) tableModel.getCurrentRow()).getIsValid()
							.equals(new Boolean(true))) {
						JOptionPane.showMessageDialog(FmBill.this, "已经生效,不能删除",
								"确认", 2);
						return;
					}
					BillMaster billMaster = (BillMaster) tableModel
							.getCurrentRow();
					if (vaildatorDataIsNull()) {
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBill.this,
							"确定要删除此记录吗?\n注意：其表体将一并被删除", "确认", 0) == 0) {
						casAction.deleteBillMaster(new Request(CommonVars
								.getCurrUser()), billMaster);
						tableModel.deleteRow(billMaster);
					}
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 判断其中单据是否被外发加工登记表引用
	 * 
	 * @return
	 * @author sxk
	 */
	private boolean vaildatorDataIsNull() {
		BillMaster billMaster = (BillMaster) tableModel.getCurrentRow();
		List list = new ArrayList();
		// 判断是否被引用
		boolean isUse = false;
		if (null != billMaster) {
			// 查询单据头
			List billdetailList = casAction.findBillDetailByMasterId(
					new Request(CommonVars.getCurrUser()), billMaster);
			System.out.println("list1.size=" + billdetailList.size());
			for (int i = 0; i < billdetailList.size(); i++) {
				// 根据单据头查找单据表体
				BillDetail billDetail = (BillDetail) billdetailList.get(i);
				// 循环判断是否存在于被引用标示类里
				list = casAction.findOwpBillAndBillDetail(new Request(
						CommonVars.getCurrUser()), billDetail.getId());
				System.out.println("list.size=" + list.size());
				if (list.size() > 0) {
					isUse = true;
				}
			}
			if (isUse) {
				JOptionPane.showMessageDialog(null, "单据已被外发加工登记表引用，不得删除！",
						"警告", JOptionPane.INFORMATION_MESSAGE);
				return true;
			}
		}
		return false;
	}

	/**
	 * This method initializes btnRefurbish
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setVisible(false);
			btnRefresh.setPreferredSize(new Dimension(60, 30));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();

				}
			});

		}
		return btnRefresh;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setVgap(1);
			fl.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setLayout(fl);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnBillTextImportar());
			jToolBar.add(getBtnEfficient());
			jToolBar.add(getBtnRollBack());
			jToolBar.add(getBtnTally());
			jToolBar.add(getBtnCopy());
			jToolBar.add(getBtnCheckBill());
			jToolBar.add(getBtnExit());
			jToolBar.add(getBtnConvertToInit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes tableOrderInfo
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTableOrderInfo() {
		if (tableOrderInfo == null) {
			tableOrderInfo = new JTable();
			tableOrderInfo.setRowHeight(25);
			tableOrderInfo.getName();
			tableOrderInfo
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tableOrderInfo.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						editData(billCategory);
					}
					if (e.getClickCount() == 1) {
						List<BillMaster> list = tableModel.getCurrentRows();
						int level = 1;
						for (BillMaster item : list) {
							if (item != null) {
								if (item.getKeepAccounts() != null
										&& item.getKeepAccounts() && level < 3) {// 已经记帐
									level = 3;
								} else {
									if (item.getIsValid() != null
											&& item.getIsValid() && level < 2) {// 已经生效
										level = 2;
									} else {
										level = 1;
									}
								}
							}
						}
						setState(level);
					}
				}
			});
		}
		return tableOrderInfo;
	}

	/**
	 * 设置JButton的状态
	 * 
	 * @param level
	 */

	private void setState(int level) {
		if (level == 3) {// 已经记帐
			btnTally.setEnabled(false);
			btnEfficient.setEnabled(false);
			btnRollBack.setEnabled(false);
		} else {
			if (level == 2) {// 已经生效
				btnTally.setEnabled(true);
				btnEfficient.setEnabled(false);
				btnRollBack.setEnabled(true);
			} else {
				btnTally.setEnabled(false);
				btnEfficient.setEnabled(true);
				btnRollBack.setEnabled(false);
			}
		}
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTableOrderInfo());
		}
		return jScrollPane1;
	}

	/**
	 * 放入List
	 * 
	 * @param billCategory
	 */

	private void fillList(int billCategory) {

//		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
//				"casAction");
		Vector vector = new Vector();
		List billTypes = this.casAction.findBillType(new Request(CommonVars
				.getCurrUser()), billCategory);
		List removeList = new ArrayList();
		for (int i = 0; i < billTypes.size(); i++) {
			BillType billType = (BillType) billTypes.get(i);
			if (billType.getIsShow() != null)
				if (billType.getIsShow().equals(false))
					removeList.add(billType);
			vector.add(billType);
		}
		vector.removeAll(removeList);
		this.listOrder.setListData(vector);
		this.listOrder.setCellRenderer(new UserListCellRenderer());
		if (this.listOrder.getModel().getSize() > 0) {
			FmBill.this.setBillType((BillType) listOrder.getSelectedValue());
			this.listOrder.setSelectedIndex(0);
		}
	}

	/**
	 * 初始化表
	 * 
	 * @return javax.swing.JTableListModel
	 */
	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(tableOrderInfo, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类别", "billType.name", 120));
						list.add(addColumn("单据号", "billNo", 100));
						list.add(addColumn("客户(供应商)", "scmCoc.name", 200));
						list.add(addColumn("有效", "isValid", 50));
						list.add(addColumn("生效日期", "validDate", 80));
						list.add(addColumn("是否记帐", "keepAccounts", 50));
						list.add(addColumn("备注", "notice", 100));
						return list;
					}
				});
		tableOrderInfo.getColumnModel().getColumn(4).setCellRenderer(
				new TableCheckBoxRender());
		tableOrderInfo.getColumnModel().getColumn(6).setCellRenderer(
				new TableCheckBoxRender());
		return tableModel;
	}

	/**
	 * List呈现类 单据类别是否能与报关单对应的单据
	 * 
	 * 若为True 则加"★"标记
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
				// s =
				// " ("+billType.getCode()+")  "+billType.getName();//wss这里只是为了方便，提交是要改回来

				if (billType.getIsTransferBill() != null
						&& billType.getIsTransferBill()) {
					setForeground(Color.BLUE);
				} else {
					setForeground(list.getForeground());
				}

				if (billType.getIsCustomsDeclarationCorresponding() != null
						&& billType.getIsCustomsDeclarationCorresponding()) {
					s += " ★";
				}
			}
			setText(s);
			return this;
		}
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
			jPanel3.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel3.add(getPnCommon(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
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
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmBill.this.dispose();
				}
			});
		}
		return btnExit;
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
			lbCountryPay = new JLabel();
			lbCountryPay.setText("此单据不参与海关帐年审报表统计");
			lbCountryPay.setForeground(java.awt.Color.blue);
			lbCountryPay.setFont(new Font("Dialog", Font.PLAIN, 11));
			lbCountryPay.setVisible(false);
			java.awt.GridLayout gridLayout2 = new GridLayout();
			jPanel5 = new JPanel();
			jPanel5.setLayout(gridLayout2);
			gridLayout2.setRows(2);
			gridLayout2.setColumns(0);
			jPanel5
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
			jPanel5.setBackground(new java.awt.Color(238, 238, 238));
			jPanel5.add(getRbImgImport(), null);
			jPanel5.add(getRbImgOutput(), null);
			jPanel5.add(getRbExgImport(), null);
			jPanel5.add(getRbExgOutput(), null);
			jPanel5.add(getRbHalfExg(), null);
			jPanel5.add(getRbScrap(), null);
			jPanel5.add(getRbInferior(), null);
			jPanel5.add(getRbDevImport(), null);
			jPanel5.add(getRbDevOutput(), null);
			jPanel5.add(lbCountryPay, null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes rbImgImport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImgImport() {
		if (rbImgImport == null) {
			rbImgImport = new JRadioButton();
			rbImgImport.setText("料件入仓");
			rbImgImport.setName("rbImgImport");
			rbImgImport.addActionListener(new RadioActionListner());
		}
		return rbImgImport;
	}

	/**
	 * This method initializes rbImgOutput
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImgOutput() {
		if (rbImgOutput == null) {
			rbImgOutput = new JRadioButton();
			rbImgOutput.setText("料件出仓");
			rbImgOutput.setName("rbImgOutput");
			rbImgOutput.addActionListener(new RadioActionListner());
		}
		return rbImgOutput;
	}

	/**
	 * This method initializes rbExgImport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExgImport() {
		if (rbExgImport == null) {
			rbExgImport = new JRadioButton();
			rbExgImport.setText("成品入仓");
			rbExgImport.setName("rbExgImport");
			rbExgImport.addActionListener(new RadioActionListner());
		}
		return rbExgImport;
	}

	/**
	 * This method initializes rbExgOutput
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbExgOutput() {
		if (rbExgOutput == null) {
			rbExgOutput = new JRadioButton();
			rbExgOutput.setText("成品出仓");
			rbExgOutput.setName("rbExgOutput");
			rbExgOutput.addActionListener(new RadioActionListner());
		}
		return rbExgOutput;
	}

	/**
	 * This method initializes rbDevImport
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDevImport() {
		if (rbDevImport == null) {
			rbDevImport = new JRadioButton();
			rbDevImport.setText("设备入仓");
			rbDevImport.setName("rbDevImport");
			rbDevImport.addActionListener(new RadioActionListner());
		}
		return rbDevImport;
	}

	/**
	 * This method initializes rbDevOutput
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbDevOutput() {
		if (rbDevOutput == null) {
			rbDevOutput = new JRadioButton();
			rbDevOutput.setText("设备出仓");
			rbDevOutput.setName("rbDevOutput");
			rbDevOutput.addActionListener(new RadioActionListner());
		}
		return rbDevOutput;
	}

	/**
	 * This method initializes rbHalfExg
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbHalfExg() {
		if (rbHalfExg == null) {
			rbHalfExg = new JRadioButton();
			rbHalfExg.setText("半成品出入仓");
			rbHalfExg.setName("rbHalfExg");
			rbHalfExg.addActionListener(new RadioActionListner());
		}
		return rbHalfExg;
	}

	/**
	 * This method initializes rbInferior
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbInferior() {
		if (rbInferior == null) {
			rbInferior = new JRadioButton();
			rbInferior.setText("残次品出入仓");
			rbInferior.setName("rbInferior");
			rbInferior.addActionListener(new RadioActionListner());
		}
		return rbInferior;
	}

	/**
	 * This method initializes rbScrap
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbScrap() {
		if (rbScrap == null) {
			rbScrap = new JRadioButton();
			rbScrap.setText("边角料出入仓");
			rbScrap.setName("rbScrap");
			rbScrap.addActionListener(new RadioActionListner());
		}
		return rbScrap;
	}

	/**
	 * 对所有的JRadioButton进行监听
	 * 
	 * @author ower
	 */
	private class RadioActionListner implements ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			int billCategory = -1;
			if (((JRadioButton) e.getSource()).getName().equals("rbImgImport")) {
				billCategory = SBillType.MATERIEL_IN;
			} else if (((JRadioButton) e.getSource()).getName().equals(
					"rbImgOutput")) {
				billCategory = SBillType.MATERIEL_OUT;
			}
			if (((JRadioButton) e.getSource()).getName().equals("rbExgImport")) {
				billCategory = SBillType.PRODUCE_IN;
			} else if (((JRadioButton) e.getSource()).getName().equals(
					"rbExgOutput")) {
				billCategory = SBillType.PRODUCE_OUT;
			}
			if (((JRadioButton) e.getSource()).getName().equals("rbDevImport")) {
				billCategory = SBillType.FIXTURE_IN;
			} else if (((JRadioButton) e.getSource()).getName().equals(
					"rbDevOutput")) {
				billCategory = SBillType.FIXTURE_OUT;
			}
			if (((JRadioButton) e.getSource()).getName().equals("rbHalfExg")) {
				billCategory = SBillType.HALF_PRODUCE_INOUT;
			} else if (((JRadioButton) e.getSource()).getName().equals(
					"rbInferior")) {
				billCategory = SBillType.REMAIN_PRODUCE_INOUT;
			} else if (((JRadioButton) e.getSource()).getName().equals(
					"rbScrap")) {
				billCategory = SBillType.LEFTOVER_MATERIEL_INOUT;
			}

			if (((JRadioButton) e.getSource()).getName().equals("rbHalfExg")) {
				// btnConvertToInit.setVisible(true);//wss:控制 折算在产品期初单 按钮的 可视化
				btnCheckBill.setVisible(false);// wss控制单据检查按钮 的可视化
			} else {
				// btnConvertToInit.setVisible(false);//wss:控制 折算在产品期初单 按钮的 可视化
				btnCheckBill.setVisible(true);// wss控制单据检查按钮 的可视化
			}

			setBillCategory(billCategory);
			fillList(billCategory);
		}
	}

	/**
	 * @param billCategory
	 *            The billCategory to set.
	 */

	public void setBillCategory(int billCategory) {

		this.billCategory = billCategory;
	}

	/**
	 * @return Returns the billType.
	 */
	public BillType getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 *            The billType to set.
	 */
	public void setBillType(BillType billType) {
		this.billType = billType;
	}

	/**
	 * This method initializes pnCommon
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnCommon() {
		if (pnCommon == null) {
			pnCommon = new JPanel();
			pnCommon.setLayout(new BorderLayout());
			pnCommon.add(getJPanel5(), java.awt.BorderLayout.NORTH);
			// pnCommon.add(getJToolBar1(), java.awt.BorderLayout.CENTER);
			pnCommon.add(getJPanel6(), BorderLayout.CENTER);
		}
		return pnCommon;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setVisible(false);
			// jToolBar1.add(getPnCommonQueryPage());
		}
		return jToolBar1;
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
		if (billType == null) {
			return new ArrayList();
		}
		return this.casAction.findBillMaster(new Request(CommonVars
				.getCurrUser()), billType, index, length, property, value,
				isLike);
	}

	public void getDataSource() {
		if (billType == null) {
			initTable(new ArrayList());
			return;
		}
		String billNo = tfBillNo.getText().trim();
		ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();
		String scmCocId =null;
		if (scmCoc != null) {
			 scmCocId = scmCoc.getCode();
		}
		Date beginDate = cbbABeginDate.getDate();
		Date endDate = cbbAEndDate.getDate();
		List list = this.casAction
				.findBillMaster(new Request(CommonVars.getCurrUser()),
						billType, billNo, scmCocId, beginDate, endDate);
		if (list != null && list.size() > 0) {
			initTable(list);
		} else {
			initTable(new ArrayList());
		}
	}

	/**
	 * 查询操作页面
	 */
	private PnMutiConditionQueryPage pnCommonQueryPage = null;

	/**
	 * 单据文本导入
	 */
	private JButton btnBillTextImportar = null;

	/**
	 * 回卷按钮
	 */
	private JButton btnRollBack = null;

	/**
	 * 生效按钮
	 */
	private JButton btnEfficient = null;

	/**
	 * 记帐按钮
	 */
	private JButton btnTally = null;

	/**
	 * 转抄按钮
	 */
	private JButton btnCopy = null;
	private JButton btnConvertToInit = null;
	private JButton btnCheckBill = null;
	private JPopupMenu pmCheckBill = null; // @jve:decl-index=0:visual-constraint="164,496"
	private JMenuItem miCheckAll = null; // @jve:decl-index=0:visual-constraint="695,217"
	private JMenuItem miCheckPart = null; // @jve:decl-index=0:visual-constraint="696,187"
	private JLabel lbCountryPay = null;
	private JPanel jPanel6 = null;
	private JTextField tfBillNo = null;
	private JLabel jLabel1 = null;
	private JCalendarComboBox cbbABeginDate = null;
	private JComboBox cbbScmCoc = null;
	private JButton btnSearch = null;
	private JCalendarComboBox cbbAEndDate = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JLabel jLabel4 = null;

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnMutiConditionQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnMutiConditionQueryPage() {

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmBill.this.initTable(dataSource);
				}

				//
				// @Override
				// public List getDataSource(int index, int length,
				// String property, Object value, boolean isLike) {
				// return FmBill.this.getDataSource(index, length, property,
				// value, isLike);
				// }

				@Override
				public List getDataSource(int index, int length) {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes btnBillTextImportar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBillTextImportar() {
		if (btnBillTextImportar == null) {
			btnBillTextImportar = new JButton();
			btnBillTextImportar.setText("单据文本导入");
			btnBillTextImportar.setPreferredSize(new Dimension(85, 30));
			btnBillTextImportar.setForeground(java.awt.Color.blue);
			btnBillTextImportar
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							DgBillTxtImportNew dg = new DgBillTxtImportNew();
//							if (rbHalfExg.isSelected()) {
//								dg.setIshalfProdect(rbHalfExg.isSelected());
//							}
							dg.setVisible(true);

						}
					});
		}
		return btnBillTextImportar;
	}

	/**
	 * This method initializes btnRollBack
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRollBack() {
		if (btnRollBack == null) {
			btnRollBack = new JButton();
			btnRollBack.setText("回卷");
			btnRollBack.setPreferredSize(new Dimension(60, 30));
			btnRollBack.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (tableModel == null
							|| tableModel.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(FmBill.this,
								"请选择您要回卷的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBill.this,
							"您确定要对记录回卷吗?", "确认", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
						return;
					}
					List<BillMaster> list = tableModel.getCurrentRows();
					String err = "";
					Boolean isPass = true;
					for (BillMaster item : list) {

						// 已做对应不可已回卷
						String s = "";
						if (!(s = isUnreel(item)).equals("")) {
							isPass = false;
							err = err + s;
						}

						// wss2010.10.08新加
						if ("4009".equals(billType.getCode())
								&& isAlreadyConvertToInit(item)) {
							JOptionPane.showMessageDialog(FmBill.this,
									"此单据已经折算成了单据号为" + item.getNotice()
											+ "的在产品期初单单据，不能回卷！\n"
											+ "确定要回卷，请先删除对应的在产品期初单单据！", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}

						// 有数据已进出口申请单在海关帐中 或者 有数据已转厂单据在海关帐中
						if (casAction
								.hasDataTransferImpExpRequestBillByBillMaster(
										new Request(CommonVars.getCurrUser()),
										item)
								|| casAction
										.hasDataTransferTransferFactoryBillByBillMaster(
												new Request(CommonVars
														.getCurrUser()), item)) {
							err = err + "\n警告:单据号为:" + item.getBillNo()
									+ "的单据在海关单据中有数据已进出口申请,或者有数据已转转厂单据,不能撤消生效!";
							isPass = false;
						}
						if (isPass) {
							item.setIsValid(new Boolean(false));
							item = casAction.saveBillMaster(new Request(
									CommonVars.getCurrUser()), item);
							tableModel.updateRow(item);
						}
						isPass = true;
					}
					if (err != null && !err.equals("")) {
						JOptionPane.showMessageDialog(FmBill.this, err, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnRollBack;
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
							+ "的单据其单据体数据已与报关单进行了对应，不能回卷！";
					break;
				}
			}
		}
		return err;
	}

	/**
	 * This method initializes btnEfficient
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEfficient() {
		if (btnEfficient == null) {
			btnEfficient = new JButton();
			btnEfficient.setText("生效");
			btnEfficient.setPreferredSize(new Dimension(60, 30));
			btnEfficient.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if (tableModel == null
							|| tableModel.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(FmBill.this,
								"请选择您要生效的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBill.this,
							"您确定要对记录生效吗?", "确认", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
						return;
					}
					List<BillMaster> list = tableModel.getCurrentRows();
					String err = "";
					Boolean isPass = true;
					for (BillMaster item : list) {
						String s = "";
						if (!(s = checkValid(item)).equals("")) {
							isPass = false;
							err = err + s;
						}
						if (isPass == true) {
							item.setIsValid(new Boolean(true));
							item = casAction.saveBillMaster(new Request(
									CommonVars.getCurrUser()), item);
							tableModel.updateRow(item);
						}
						isPass = true;
					}
					if (err != null && !err.equals("")) {
						JOptionPane.showMessageDialog(FmBill.this, err, "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						setState(2);
					}
				}
			});
		}
		return btnEfficient;
	}

	/**
	 * 以下类型单据可不用填写客户/供应商
	 * 
	 * @param billCode
	 * @return
	 */
	private boolean isCheckCustomerIsNull(Integer billCode) {
		Integer[] billCodes = new Integer[] { 1001, 1002, 1006, 1007, 1009,
				1010, 1020, 1104, 1105, 1108, 2001, 2004, 2005,
				2103,
				2105,
				2108,// wss:2010.07.23新增1020
				2109, 4001, 4002, 4003, 4009, 4101, 4102, 5001, 5002, 5003,
				5101, 5102, 6001, 6002, 6003, 6004, 6005, 6006, 6101, 6102,
				6103, 6104, 6105, 6106 };// wss2010.06.17增加边角料全部

		Vector<Integer> vector = new Vector<Integer>();
		for (int i = 0; i < billCodes.length; i++) {
			vector.add(billCodes[i]);
		}
		return vector.contains(billCode);
	}

	/**
	 * 数据验证
	 * 
	 * @param item
	 * @return
	 */

	private String checkValid(BillMaster item) {
		String msg = "";
		if (item.getValidDate() == null) {
			msg = msg + "\n警告:" + "生效日期不能为空,不能生效";
		}

		// 单据类型代码
		int billCode = Integer.parseInt(this.billType.getCode());
		CasBillOption casBillOption = casAction.findCasBillOption(new Request(
				CommonVars.getCurrUser()));

		// 车间领用 1101
		if (billCode == 1101
				&& (this.casBillOption.getIsOutNeedCustomer() == null || this.casBillOption
						.getIsOutNeedCustomer().booleanValue() == false)) {
			// 这里什么都不做
		}

		// 车间入库2002
		else if (billCode == 2002
				&& (this.casBillOption.getIsInNeedCustomer() == null // 2002
				|| this.casBillOption.getIsInNeedCustomer().booleanValue() == false)) {
			// 这里什么都不做
		}

		else if (!this.isCheckCustomerIsNull(billCode)
				&& (item.getScmCoc() == null)) {
			msg = msg + "\n警告:" + "客户不能为空,不能生效";
		}

		List detailList = casAction.findBillDetail(new Request(CommonVars
				.getCurrUser()), item.getId(), this.billType.getBillType());
		if (detailList == null || detailList.size() == 0) {
			msg = msg + "\n警告:" + "其单据体为空,不能生效";
		}
		for (int i = 0; i < detailList.size(); i++) {
			BillDetail bill = (BillDetail) detailList.get(i);
			if (("").equals(bill.getProductNo()) && 1101 == billCode) {
				msg = msg + "\n警告:" + "制单号未填写不能生效，不能生效！";
			}

			// 如果不是半成品，则需要检查 数量、工厂名称、报关名称
			if (billType.getBillType() != 8 && billType.getBillType() != 7
					&& !"半成品".equals(bill.getNote())) {
				if (bill.getPtAmount() == null
						|| bill.getPtAmount().doubleValue() == 0
						|| bill.getHsName() == null
						|| bill.getHsName().equals("")
						|| bill.getPtName() == null
						|| bill.getPtName().equals("")) {
					msg = msg + "\n警告:" + "单据体数据填写不完整,不能生效";
					break;
				}
			}

			// wss:全部都要检查仓库
			if (bill.getWareSet() == null) {
				msg = msg + "\n警告:" + " 单据体未填仓库 ";
				break;
			}

		}
		if (msg == null || msg.equals("")) {
			return msg;
		} else {
			msg = "\n单据号为:" + item.getBillNo() + msg;
			return msg;
		}
	}

	/**
	 * This method initializes btnTally
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnTally() {
		if (btnTally == null) {
			btnTally = new JButton();
			btnTally.setText("记帐");
			btnTally.setPreferredSize(new Dimension(60, 30));
			btnTally.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null
							|| tableModel.getCurrentRows() == null) {
						JOptionPane.showMessageDialog(FmBill.this,
								"请选择您要记帐的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (JOptionPane
							.showConfirmDialog(FmBill.this, "您确定要对记录进行记帐吗?",
									"确认", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION) {
						return;
					}
					List<BillMaster> list = tableModel.getCurrentRows();
					for (BillMaster item : list) {
						if (item.getIsValid()) {
							item.setKeepAccounts(new Boolean(true));
							item = casAction.saveBillMaster(new Request(
									CommonVars.getCurrUser()), item);
							tableModel.updateRow(item);
						} else {
							JOptionPane.showMessageDialog(FmBill.this,
									"单据" + item.getBillNo() + "还未生效，不能对其记账！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
					setState(3);
				}
			});
		}
		return btnTally;
	}

	/**
	 * This method initializes btnCopy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCopy() {
		if (btnCopy == null) {
			btnCopy = new JButton();
			btnCopy.setText("转抄");
			btnCopy.setPreferredSize(new Dimension(60, 30));
			btnCopy.setFont(new Font("Dialog", Font.BOLD, 12));
			btnCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBill.this,
								"请选择你要转抄的资料", "确认", 2);
						return;
					}
					List billMasters = tableModel.getCurrentRows();
					List newbillMasters = casAction.copyBillMaster(new Request(
							CommonVars.getCurrUser()), billMasters);
					tableModel.addRows(newbillMasters);
				}
			});
		}
		return btnCopy;
	}

	/**
	 * 折算在产品其初 按钮
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConvertToInit() {
		if (btnConvertToInit == null) {
			btnConvertToInit = new JButton();
			btnConvertToInit.setText("折算在产品期初单");
			btnConvertToInit.setVisible(false);
			btnConvertToInit
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// 1.提示内容
							String message = "\n此操作会将半成品出入仓单据生成在产品期初单。\n您确定要执行吗？";
							boolean isExist = casAction
									.isExistInitBillMasterMadeBySemiProduct(new Request(
											CommonVars.getCurrUser()));
							if (isExist) {
								message = "您之前有将半成品出入仓单据生成在产品期初单，此操作将会覆盖之前的。\n您确定要执行吗？";
							}
							// 2.提示对话框
							if (JOptionPane.showConfirmDialog(FmMain
									.getInstance(), message, "友情提示！",
									JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
								// 3.折算在产品期初单
								new ConvertToInit().start();

							}

						}
					});
		}
		return btnConvertToInit;
	}

	/**
	 * 折算在产品期初单线程
	 * 
	 * @author wss
	 */
	class ConvertToInit extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmBill.this);
				CommonProgress.setMessage("系统正在进行折算，请稍后...");
				casAction.convertToInitFromSemiProduct(new Request(CommonVars
						.getCurrUser()));
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmBill.this, "折算完成！", "提示", 2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();

				JOptionPane.showMessageDialog(FmBill.this, "数据折算失败：！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	/**
	 * 单据检查按钮
	 * 
	 * @return javax.swing.JButton
	 * @author wss2010.09.24
	 */
	private JButton getBtnCheckBill() {
		if (btnCheckBill == null) {
			btnCheckBill = new JButton();
			btnCheckBill.setText("单据检查");
			btnCheckBill.setPreferredSize(new Dimension(60, 30));
			btnCheckBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Component comp = (Component) e.getSource();
					getPmCheckBill().show(comp, 0, comp.getHeight());
				}
			});
		}
		return btnCheckBill;
	}

	/**
	 * 单据检查下弹框
	 * 
	 * @return javax.swing.JPopupMenu
	 * @author wss2010.09.24
	 */
	private JPopupMenu getPmCheckBill() {
		if (pmCheckBill == null) {
			pmCheckBill = new JPopupMenu();
			pmCheckBill.add(getMiCheckPart());
			pmCheckBill.add(getMiCheckAll());
		}
		return pmCheckBill;
	}

	/**
	 * 检查单据全部 菜单
	 * 
	 * @return javax.swing.JMenuItem
	 * @author wss2010.09.24
	 */
	private JMenuItem getMiCheckAll() {
		if (miCheckAll == null) {
			miCheckAll = new JMenuItem();
			miCheckAll.setText("检查当前列表所有单据");
			miCheckAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new CheckBillThread(billCategory, null).start();
				}
			});
		}
		return miCheckAll;
	}

	/**
	 * 检查单据部分 菜单
	 * 
	 * @return javax.swing.JMenuItem
	 * @author wss2010.09.24
	 */
	private JMenuItem getMiCheckPart() {
		if (miCheckPart == null) {
			miCheckPart = new JMenuItem();
			miCheckPart.setText("检查当前单据类别的单据");
			miCheckPart.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getList() == null
							|| tableModel.getList().size() <= 0) {
						JOptionPane.showMessageDialog(FmBill.this, "当前类型无单据",
								"确认！", 1);
						return;
					}

					new CheckBillThread(billCategory, billType).start();
				}
			});
		}
		return miCheckPart;
	}

	/**
	 * 单据检查线程
	 * 
	 * @author wss2010.09.24
	 */
	class CheckBillThread extends Thread {

		int billCategory;
		BillType billType;

		public CheckBillThread(int billCategory, BillType billType) {
			this.billCategory = billCategory;
			this.billType = billType;
		}

		public void run() {

			try {
				List infos = new ArrayList();
				String info = "";
				long beginTime = System.currentTimeMillis();

				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在进行检查，请稍后...");

				// 返回有问题的单据体
				List<BillDetail> listError = casAction.checkBillDetail(
						new Request(CommonVars.getCurrUser()), billCategory,
						billType);

				// 如果存在有问题的单据体
				if (listError != null && listError.size() != 0) {
					CommonProgress.closeProgressDialog();
					DgBillCheckResult dg = new DgBillCheckResult();
					dg.setDataSource(listError);
					dg.setVisible(true);
				}

				// 否则提示没错
				else {
					CommonProgress.closeProgressDialog();

					info += " 检查完毕,没有错误的单据体！,耗时:"
							+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
					JOptionPane.showMessageDialog(FmBill.this, info, "提示", 2);
				}

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmBill.this, "检查失败！！！"
						+ e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();

			}
		}
	}

	/**
	 * 判断是否被折算成在产品期初单
	 * 
	 * @return
	 * @author wss2010.10.08
	 */
	private boolean isAlreadyConvertToInit(BillMaster billMaster) {

		// 注释栏中的单据号码
		String billNo = billMaster.getNotice() == null ? "" : billMaster
				.getNotice();

		// 判断是否已经折算过
		if ("".equals(billNo)) {
			return false;
		}

		return casAction.isAlreadyConvertToInit(new Request(CommonVars
				.getCurrUser()), billNo);
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(542, 7, 69, 22));
			jLabel4.setText("客户/供应商");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(389, 7, 24, 19));
			jLabel3.setText("至");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(214, 7, 56, 20));
			jLabel2.setText("生效日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(54, 7, 41, 22));
			jLabel1.setText("单据号");
			jPanel6 = new JPanel();
			jPanel6.setLayout(null);
			jPanel6.setPreferredSize(new Dimension(1, 35));
			jPanel6.add(getTfBillNo(), null);
			jPanel6.add(jLabel1, null);
			jPanel6.add(getCbbABeginDate(), null);
			jPanel6.add(getCbbScmCoc(), null);
			jPanel6.add(getBtnSearch(), null);
			jPanel6.add(getCbbAEndDate(), null);
			jPanel6.add(jLabel2, null);
			jPanel6.add(jLabel3, null);
			jPanel6.add(jLabel4, null);
		}
		return jPanel6;
	}

	/**
	 * This method initializes tfBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			tfBillNo.setBounds(new Rectangle(94, 7, 112, 22));
		}
		return tfBillNo;
	}

	/**
	 * This method initializes cbbABeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbABeginDate() {
		if (cbbABeginDate == null) {
			cbbABeginDate = new JCalendarComboBox();
			cbbABeginDate.setBounds(new Rectangle(274, 7, 112, 22));
			cbbABeginDate.setPreferredSize(new Dimension(100, 22));
		}
		return cbbABeginDate;
	}

	/**
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(new Rectangle(617, 7, 157, 22));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setBounds(new Rectangle(783, 8, 71, 22));
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getDataSource();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes cbbAEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbAEndDate() {
		if (cbbAEndDate == null) {
			cbbAEndDate = new JCalendarComboBox();
			cbbAEndDate.setBounds(new Rectangle(414, 7, 112, 22));
			cbbAEndDate.setPreferredSize(new Dimension(100, 22));
		}
		return cbbAEndDate;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
