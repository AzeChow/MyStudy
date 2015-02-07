/*
 * Created on 2004-9-15
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.bill.entity.BillDetail;
import com.bestway.bcus.cas.bill.entity.BillMaster;
import com.bestway.bcus.cas.bill.entity.BillMasterFixture;
import com.bestway.bcus.cas.bill.entity.BillMasterHalfProduct;
import com.bestway.bcus.cas.bill.entity.BillMasterLeftoverMateriel;
import com.bestway.bcus.cas.bill.entity.BillMasterMateriel;
import com.bestway.bcus.cas.bill.entity.BillMasterProduct;
import com.bestway.bcus.cas.bill.entity.BillMasterRemainProduct;
import com.bestway.bcus.cas.entity.BillType;
import com.bestway.bcus.cas.entity.FactoryAndFactualCustomsRalation;
import com.bestway.bcus.cas.entity.TempObject;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.client.cas.CasQuery;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseComboBoxUI;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.footer.JFooterScrollPane;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.TableFooterType;
import com.bestway.client.windows.form.FmMain;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.SBillType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import java.awt.Dimension;

/**
 * 工厂单据表维护 checked 2008-09-09
 * 
 * @author ?
 * 
 */
public class DgBillMaster extends JDialogBase {

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel3 = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnSave = null;

	private JButton btnEffect = null;

	private JButton btnExit = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel1 = null;

	private JToolBar jToolBar1 = null;

	private JButton btnAdds = null;

	private JButton btnEdits = null;

	private JButton btnDelete = null;

	private JPanel jPanel3 = null;

	private JFooterTable jTable = null;

	private JFooterScrollPane jScrollPane = null;

	private JTextField tfBillTypeName = null;

	private JTextField tfBillNo = null;

	private JComboBox cbbScmCoc = null;

	private JCalendarComboBox cbbValidDate = null;

	private JButton btnCanel = null;

	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	/**
	 * 海关帐远程接口
	 */
	private CasAction casAction = null;

	/**
	 * 表头
	 */
	private BillMaster billMaster = null;

	/**
	 * 单据类型
	 */
	private BillType billType = null; // @jve:decl-index=0:

	/**
	 * 单据表头 表Model
	 */
	private JTableListModel tableModel = null;

	/**
	 * 单据表体 表Model
	 */
	private JTableListModel tableModelDetail = null;

	/**
	 * 单据明细状态
	 */
	private int dataStateBillDetail = DataState.BROWSE;
	/**
	 * 数据状态
	 */
	private int dataState = DataState.BROWSE;

	private JButton btnRollBack = null;

	private JButton btnBooking = null;
	/**
	 * 是否是主表
	 */
	private boolean isMasterTable = true;

	private CasBillOption casBillOption; // @jve:decl-index=0:

	/**
	 * 单据进出仓类别
	 */
	private int billCategory = -1; // @jve:decl-index=0:

	private JLabel jLabel2 = null;

	/**
	 * 关封号
	 */
	private JTextField tfCustomsCover = null;

	private Date validDate = null; // @jve:decl-index=0:

	/**
	 * 主要用来判断残次品资料是料件、还是成品
	 */
	private boolean isMateriel = true;
//	/**
//	 * 最大小数位
//	 */
//	private Integer maximumFractionDigits = CasCommonVars.getOtherOption()
//			.getInOutMaximumFractionDigits() == null ? 6 : CasCommonVars
//			.getOtherOption().getInOutMaximumFractionDigits(); // @jve:decl-index=0:
	private Integer maximumFractionDigits = 9; // @jve:decl-index=0:
	
	
	/**
	 * 是否编辑查询信息
	 */
	private boolean isEditFromQuery = false;

	private JLabel jLabel4 = null;

	private JTextField tfNotice = null;

	private JButton btnAdds1 = null;

	private JButton btnAddSemiProduct = null;

	private JButton btnEnvelopNo = null;

	private JButton btnConvertToInit = null;

	/**
	 * 带参数的构造器
	 * 
	 * @param owner
	 */
	public DgBillMaster(JFrame owner) {
		super(owner, true);
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();

	}

	/**
	 * 默认的constructor
	 */
	public DgBillMaster() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
	}

	/**
	 * 初始化标题&大小
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("工厂单据表维护");
		this.setSize(755, 539);
		this.setContentPane(getJContentPane());
	}

	/**
	 * 设置组件的状态
	 */

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			if (dataState == DataState.EDIT) { // 编辑
				if (!isEditFromQuery) {
					Object temp = tableModel.getCurrentRow();
					if (temp != null) {
						if (temp instanceof BillMaster) {
							billMaster = (BillMaster) temp;
							isMasterTable = true;
						} else if (temp instanceof BillDetail) {
							billMaster = ((BillDetail) temp).getBillMaster();
							isMasterTable = false;
						}
					}
				} else {
					isMasterTable = true;
				}

				initUI();
				initUIData();

				if ((new Boolean(true)).equals(billMaster.getIsValid())) {
					dataStateBillDetail = DataState.BROWSE;// 浏览
				} else {
					dataStateBillDetail = DataState.EDIT;// 编辑
				}
				setState();
			} else if (dataState == DataState.ADD) { // 新增
				dataStateBillDetail = DataState.ADD;
				initUI();
				tfBillNo.setText(DgBillMaster.this.getInitBillNo());
				setState();
			} else if (dataState == DataState.BROWSE) {// 浏览
				Object temp = tableModel.getCurrentRow();
				if (temp != null) {
					if (temp instanceof BillMaster) {
						billMaster = (BillMaster) temp;
						isMasterTable = true;
					} else if (temp instanceof BillDetail) {
						billMaster = ((BillDetail) temp).getBillMaster();
						isMasterTable = false;
					}
				}
				initUI();
				initUIData();
				setContainerEnabled(this, false);
			}
			initUITable();
			setUIVisibleState();
		}
		super.setVisible(isFlag);
	}

	/**
	 * 设置几个控件显示状态
	 */
	private void setUIVisibleState() {
		System.out.print(this.casBillOption.getIsInChangeHouseMateriel()+"2=====================");
		int billCode = Integer.parseInt(this.billType.getCode());// 单据号
		// 车间领用 1101
		if (billCode == 1101
				&& (this.casBillOption.getIsOutNeedCustomer() == null || this.casBillOption
						.getIsOutNeedCustomer().booleanValue() == false)) {
			this.jLabel3.setVisible(false);
			this.cbbScmCoc.setVisible(false);
		} else if (billCode == 2002
				&& (this.casBillOption.getIsInNeedCustomer() == null // 2002
				// 车间入库
				|| this.casBillOption.getIsInNeedCustomer().booleanValue() == false)) {
			this.jLabel3.setVisible(false);
			this.cbbScmCoc.setVisible(false);
		}
			{
			this.jLabel3.setVisible(true);
			this.cbbScmCoc.setVisible(true);
		}

		int sBillType = billType.getBillType();
		if (sBillType == SBillType.REMAIN_PRODUCE_IN
				|| sBillType == SBillType.REMAIN_PRODUCE_OUT
				|| sBillType == SBillType.REMAIN_PRODUCE_INOUT) {
			this.btnAdds.setText("新增来自料件");
			this.btnAdds1.setVisible(true);
			this.btnAddSemiProduct.setVisible(true);
		} else {
			this.btnAdds1.setVisible(false);
			this.btnAddSemiProduct.setVisible(false);
		}
	}

	/**
	 * 设置容器除JLabel外所有控件的enabled属性
	 */
	protected void setContainerEnabled(Container container, boolean isFlag) {
		for (int i = 0; i < container.getComponentCount(); i++) {
			Component component = container.getComponent(i);
			if ((component instanceof JButton)) {
				component.setEnabled(isFlag);
			} else if (component instanceof Container) {
				setContainerEnabled((Container) component, isFlag);
			}
		}
	}

	/**
	 * 初始化UI表格
	 */
	private void initUITable() {
		if (billMaster != null) {
			List detailList = casAction.findBillDetail(new Request(CommonVars
					.getCurrUser()), billMaster.getId(), this.billType
					.getBillType());
			if (detailList != null && !detailList.isEmpty()) {
				initTable(detailList);
			} else {
				initTable(new Vector());
			}
		} else {
			initTable(new Vector());
		}
	}

	/**
	 * 初始化表体
	 * 
	 * @param list
	 */
	private void initTable(final List list) {
		tableModelDetail = new JTableListModel(jTable, list,
				new JTableListModelAdapter(maximumFractionDigits) {
					@SuppressWarnings("unchecked")
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品料号", "ptPart", 100));
						list.add(addColumn("商品名称", "ptName", 100));
						list.add(addColumn("商品规格", "ptSpec", 100));
						list.add(addColumn("商品版本", "version", 100));
						list.add(addColumn("仓库名称", "wareSet.name", 100));
						// list.add(addColumn("仓库代码", "wareSet.code", 80));
						list.add(addColumn("数量", "ptAmount", 60));
						list.add(addColumn("单价", "ptPrice", 60));
						list.add(addColumn("金额", "money", 60));
						list.add(addColumn("单净重", "inNetWeight", 60));
						list.add(addColumn("净重", "netWeight", 60));
						list.add(addColumn("制单号", "productNo", 80));
						list.add(addColumn("送货单号", "takeBillNo", 80));
						list.add(addColumn("订单号", "oderBillNo", 60));
						list.add(addColumn("对应报关单号", "customNo", 80));
						list.add(addColumn("对应报关数量", "customNum", 120));
						// list.add(addColumn("商品海关编码", "complex.code", 120));
						list.add(addColumn("报关商品名称", "hsName", 120));
						list.add(addColumn("报关商品规格 ", "hsSpec", 120));
						list.add(addColumn("折算报关数量", "hsAmount", 120));
						list.add(addColumn("报关单价", "hsPrice", 80));
						list.add(addColumn("备注", "note", 80));
						// 页脚
						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableModelDetail.clearFooterTypeInfo();
		tableModelDetail.addFooterTypeInfo(new TableFooterType(0,
				TableFooterType.CONSTANT, "合计"));
		tableModelDetail.addFooterTypeInfo(new TableFooterType(6,
				TableFooterType.SUM, ""));
		tableModelDetail.addFooterTypeInfo(new TableFooterType(8,
				TableFooterType.SUM, ""));
		tableModelDetail.addFooterTypeInfo(new TableFooterType(9,
				TableFooterType.SUM, ""));
	}

	/**
	 * 初始化UI控件
	 */
	private void initUI() {
		List list1 = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser(), true));

		this.casBillOption = casAction.findCasBillOption(new Request(CommonVars
				.getCurrUser()));

		this.cbbScmCoc.setModel(new DefaultComboBoxModel(list1.toArray()));
		this.cbbScmCoc.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 50, 200));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbScmCoc, "code", "name");
		this.cbbScmCoc.setUI(new CustomBaseComboBoxUI(250));
		this.cbbScmCoc.setSelectedItem(null);
		tfBillTypeName.setText(billType.getName()); // 单据类型
		cbbValidDate.setDate(this.validDate);
	}

	/**
	 * 初始化UI数据
	 */
	private void initUIData() {
		if (billMaster.getScmCoc() != null) {
			cbbScmCoc.setSelectedItem(billMaster.getScmCoc());
		} else {
			cbbScmCoc.setSelectedItem(null);
		}
		tfBillTypeName.setText(billMaster.getBillType().getName());

		if (billType.getCode().equals("1006")
				&& this.casBillOption.getIsWorkShopBack() != null
				&& this.casBillOption.getIsWorkShopBack().booleanValue() == true) {
			System.out.println("casBillOption.getIsWorkShopBack()="
					+ casBillOption.getIsWorkShopBack());
			cbbScmCoc.setEnabled(true);
		} else if (billType.getCode().equals("2103")
				&& this.casBillOption.getIsBackWorkShop() != null
				&& this.casBillOption.getIsBackWorkShop().booleanValue() == true) {
			cbbScmCoc.setEnabled(true);
			System.out.println("hahahaha");
		}
		tfBillNo.setText(billMaster.getBillNo());
		tfCustomsCover.setText(billMaster.getEnvelopNo());
		tfNotice.setText(billMaster.getNotice());
		// cbbValidDate.setDate(billMaster.getValidDate());
	}

	/**
	 * 设置组件的状态
	 */
	private void setState() {
		int billCode = Integer.parseInt(this.billType.getCode());

		this.tfCustomsCover.setEnabled((billCode == 1004 || billCode == 2102)
				&& !(dataStateBillDetail == DataState.BROWSE) && !valid());
		this.btnEnvelopNo.setEnabled((billCode == 1004 || billCode == 2102)
				&& !(dataStateBillDetail == DataState.BROWSE) && !valid());

		this.btnAdd.setEnabled(dataStateBillDetail == DataState.BROWSE); // 新增
		this.btnEdit.setEnabled(dataStateBillDetail == DataState.BROWSE
				&& !valid() && billMaster != null);// 修改
		this.btnSave.setEnabled(!(dataStateBillDetail == DataState.BROWSE)
				&& !valid());// 保存
		this.btnCanel.setEnabled(!(dataStateBillDetail == DataState.BROWSE)
				&& !valid()); // 取消
		this.btnRollBack.setEnabled(dataStateBillDetail == DataState.BROWSE
				&& valid() && !keepAccounts() && billMaster != null); // 回卷
		this.btnEffect.setEnabled(dataStateBillDetail == DataState.BROWSE
				&& !valid() && !keepAccounts() && billMaster != null); // 生效
		if (billCode == 1006) {
			this.cbbScmCoc
					.setEnabled(!(dataStateBillDetail == DataState.BROWSE)
							&& !valid() && billCode != 1006
							| this.casBillOption.getIsWorkShopBack() == null ? false
							: this.casBillOption.getIsWorkShopBack() == null ? false
									: this.casBillOption.getIsWorkShopBack());

		} else if (billCode == 2103) {
			this.cbbScmCoc
					.setEnabled(!(dataStateBillDetail == DataState.BROWSE)
							&& !valid() && billCode != 2103
							| this.casBillOption.getIsBackWorkShop() == null ? false
							: this.casBillOption.getIsBackWorkShop() == null ? false
									: this.casBillOption.getIsBackWorkShop());
		} else {
			this.cbbScmCoc
					.setEnabled(!(dataStateBillDetail == DataState.BROWSE)
							&& !valid() && billCode != 1006);
		}
		this.tfBillNo.setEditable(!(dataStateBillDetail == DataState.BROWSE));
		this.tfNotice.setEditable(!(dataStateBillDetail == DataState.BROWSE));
		this.cbbValidDate.setEnabled(!(dataStateBillDetail == DataState.BROWSE)
				&& !valid());
		this.btnAdds.setEnabled(dataStateBillDetail != DataState.ADD
				&& billMaster != null && !valid());
		// wss新增
		this.btnAdds1.setEnabled(dataStateBillDetail != DataState.ADD
				&& billMaster != null && !valid());
		this.btnAddSemiProduct.setEnabled(dataStateBillDetail != DataState.ADD
				&& billMaster != null && !valid());

		this.btnEdits.setEnabled(dataStateBillDetail != DataState.ADD
				&& billMaster != null && !valid());
		this.btnDelete.setEnabled(dataStateBillDetail != DataState.ADD
				&& billMaster != null && !valid());
		this.btnBooking.setEnabled(dataStateBillDetail == DataState.BROWSE
				&& billMaster != null && valid() && !keepAccounts()); // 记帐

		// //折算在产品期初单按钮
		// this.btnConvertToInit.setVisible(DataState.BROWSE ==
		// dataStateBillDetail
		// && 4009 == billCode
		// && valid());

	}

	/**
	 * 生效
	 * 
	 * @return boolean
	 */
	private boolean valid() {
		if (billMaster != null
				&& (new Boolean(true)).equals(billMaster.getIsValid())) {
			return true;
		}
		return false;
	}

	/**
	 * 记帐
	 * 
	 * @return boolean
	 */
	private boolean keepAccounts() {
		if (billMaster != null
				&& (new Boolean(true)).equals(billMaster.getKeepAccounts())) {
			return true;
		}
		return false;
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
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerSize(0);
			jSplitPane.setDividerLocation(150);
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
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
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnCanel());
			jToolBar.add(getBtnEffect());
			jToolBar.add(getBtnRollBack());
			jToolBar.add(getBtnBooking());
			jToolBar.add(getBtnExit());
			jToolBar.add(getBtnConvertToInit());
		}
		return jToolBar;
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
					DgBillMaster.this.setDataState(DataState.ADD);
					DgBillMaster.this.setBillMaster(null);
					clearData();
					tfBillNo.setText(DgBillMaster.this.getInitBillNo());
					dataStateBillDetail = DataState.ADD;
					initTable(new Vector());
					setState();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes btnEdit
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.setPreferredSize(new Dimension(60, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillMaster.this.setDataState(DataState.EDIT);
					dataStateBillDetail = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("保存");
			btnSave.setPreferredSize(new Dimension(60, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (validateData() == true) {
						return;
					}
					if (dataState == DataState.ADD) {
						BillMaster billMaster = getInstanceBillMaster();
						fillData(billMaster);
						billMaster = casAction.saveBillMaster(new Request(
								CommonVars.getCurrUser()), billMaster);
						DgBillMaster.this.setBillMaster(billMaster);
						if (isMasterTable == true) {
							if (!isEditFromQuery)
								tableModel.addRow(billMaster);
						}
					} else if (dataState == DataState.EDIT) {
						fillData(billMaster);
						billMaster = casAction.saveBillMaster(new Request(
								CommonVars.getCurrUser()), billMaster);
						DgBillMaster.this.setBillMaster(billMaster);
						if (isMasterTable == true) {
							if (!isEditFromQuery)
								tableModel.updateRow(billMaster);
						}
					}
					initUITable();
					dataStateBillDetail = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSave;
	}

	/**
	 * 单据号是否可为重复
	 * 
	 * @return boolean
	 */
	private boolean validateData() {
		//
		// 为了查询条件用的
		//
		BillMaster tempBillMaster = getInstanceBillMaster();
		if (dataState == DataState.ADD) {
			tempBillMaster.setBillNo(tfBillNo.getText());
		} else if (dataState == DataState.EDIT) {
			tempBillMaster.setId(billMaster.getId());
			tempBillMaster.setBillNo(tfBillNo.getText());
		}
		if (tempBillMaster.getBillNo() == null
				|| tempBillMaster.getBillNo().trim().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(DgBillMaster.this, "单据号不可以为空!!",
					"提示信息", JOptionPane.WARNING_MESSAGE);
			return true;
		}
		if (casBillOption.getIsBillRepeat() == false) {
			boolean isExist = casAction.isExistBillByBillNo(new Request(
					CommonVars.getCurrUser(), true), tempBillMaster);
			if (isExist == true) {
				JOptionPane.showMessageDialog(DgBillMaster.this,
						"单据号重复不可以保存，要单据号可以重复请在\n\r"
								+ "”单据中心--参数设置--单据选项--单据号可以重复“前打勾", "提示信息",
						JOptionPane.WARNING_MESSAGE);
				return true;
			}
		}
		return false;
	}

	/**
	 * 实例化其子类
	 * 
	 * @return BillMaster
	 */
	private BillMaster getInstanceBillMaster() {
		BillMaster billMaster = null;
		int sBillType = billType.getBillType();

		if (sBillType == SBillType.PRODUCE_IN
				|| sBillType == SBillType.PRODUCE_OUT) {
			billMaster = new BillMasterProduct();

		} else if (sBillType == SBillType.MATERIEL_IN
				|| sBillType == SBillType.MATERIEL_OUT) {
			billMaster = new BillMasterMateriel();

		} else if (sBillType == SBillType.FIXTURE_IN
				|| sBillType == SBillType.FIXTURE_OUT) {
			billMaster = new BillMasterFixture();

		} else if (sBillType == SBillType.HALF_PRODUCE_IN
				|| sBillType == SBillType.HALF_PRODUCE_OUT) {
			billMaster = new BillMasterHalfProduct();

		} else if (sBillType == SBillType.LEFTOVER_MATERIEL_IN
				|| sBillType == SBillType.LEFTOVER_MATERIEL_OUT) {
			billMaster = new BillMasterLeftoverMateriel();

		} else if (sBillType == SBillType.REMAIN_PRODUCE_IN
				|| sBillType == SBillType.REMAIN_PRODUCE_OUT) {
			billMaster = new BillMasterRemainProduct();
		}
		return billMaster;
	}

	/**
	 * 往实体类中填充数据
	 * 
	 * @param billMaster
	 */
	private void fillData(BillMaster billMaster) {
		billMaster.setBillType(billType);
		billMaster.setBillNo(tfBillNo.getText());
		billMaster.setNotice(tfNotice.getText());
		billMaster.setIsValid(new Boolean(false));
		billMaster.setScmCoc((ScmCoc) this.cbbScmCoc.getSelectedItem());
		billMaster.setCompany(CommonVars.getCurrUser().getCompany());
		billMaster.setEnvelopNo(tfCustomsCover.getText());
		Date date = this.cbbValidDate.getDate();
		if (date != null) {
			billMaster.setValidDate(CommonVars.dateToStandDate(date));
		} else {
			billMaster.setValidDate(date);
		}
		billMaster.setKeepAccounts(new Boolean(false));
	}

	/**
	 * 
	 * This method initializes btnEffect
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEffect() {
		if (btnEffect == null) {
			btnEffect = new JButton();
			btnEffect.setText("生效");
			btnEffect.setPreferredSize(new Dimension(60, 30));
			btnEffect.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkValid()) {
						return;
					}
					billMaster.setIsValid(new Boolean(true));
					Date date = DgBillMaster.this.cbbValidDate.getDate();
					billMaster.setValidDate(CommonVars.dateToStandDate(date));
					billMaster = casAction.saveBillMaster(new Request(
							CommonVars.getCurrUser()), billMaster);
					if (isMasterTable == true) {
						if (!isEditFromQuery)
							tableModel.updateRow(billMaster);
					}

					btnConvertToInit.setEnabled(false);
					setState();
				}
			});

		}
		return btnEffect;
	}

	/**
	 * 以下单据类型不检查 客户/供应商
	 * 
	 * @param billCode
	 * @return
	 */
	private boolean isCheckCustomerIsNull(Integer billCode) {
		Integer[] billCodes = new Integer[] { 1001, 1002, 1006, 1007, 1009,
				1010, 1020, 1104, 1105, 1108, 2001, 2004, 2005,
				2103,
				2105,
				2108,// wss2010.07.23新增1020
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
	 * 检查是否有效
	 * 
	 * @return boolean
	 */
	private boolean checkValid() {
		if (cbbValidDate.getDate() == null) {
			JOptionPane.showMessageDialog(DgBillMaster.this, "生效日期不能为空！",
					"提示！", 2);
			return true;
		}

		// 单据号
		int billCode = Integer.parseInt(this.billType.getCode());

		// 1101车间领用
		if (billCode == 1101
				&& (this.casBillOption.getIsOutNeedCustomer() == null || this.casBillOption
						.getIsOutNeedCustomer().booleanValue() == false)) {
			// 什么都不做
		}

		// 2002车间入库
		else if (billCode == 2002
				&& (this.casBillOption.getIsInNeedCustomer() == null || this.casBillOption
						.getIsInNeedCustomer().booleanValue() == false)) {
			// 什么都不做
		}
		
		// 检查客户
		else if ((!this.isCheckCustomerIsNull(billCode)) // 车间返回,返回车间
				&& (this.cbbScmCoc.getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(DgBillMaster.this, "客户不能为空！", "提示！",
					2);
			return true;
		}

		// 检查单据体数量
		if (tableModelDetail.getRowCount() == 0) {
			JOptionPane.showMessageDialog(DgBillMaster.this, "其单据体为空，不能生效！",
					"提示！", 2);
			return true;
		}

		List detailList = casAction.findBillDetail(new Request(CommonVars
				.getCurrUser()), billMaster.getId(), this.billType
				.getBillType());
		boolean isCheckPass = true;
		StringBuffer infos = new StringBuffer();
		for (int i = 0; i < detailList.size(); i++) {
			BillDetail bill = (BillDetail) detailList.get(i);
			BillMaster billMaster = bill.getBillMaster();
			int billType = billMaster.getBillType().getBillType().intValue();
			String info = "料号 = " + bill.getPtPart();

			// 料件 车间领用 要填 制单号
			if ((bill.getProductNo() == null || "".equals(bill.getProductNo()))
					&& billCode == 1101) {
				info = info + " 其制单号未填写 ";
				infos.append(info + "\n");
				isCheckPass = false;
				continue;
			}
			Double ptAmount = bill.getPtAmount() == null ? 0 : bill
					.getPtAmount();

			// 除半成品外 要检查 报关名称 、工厂名称
			if (billType != 8 && billType != 7 && !"半成品".equals(bill.getNote())) {
				if (ptAmount == 0.0 || bill.getHsName() == null
						|| bill.getHsName().equals("")
						|| bill.getPtName() == null
						|| bill.getPtName().equals("")) {
					info = info + " 其单据体数据填写不完整 ";
					infos.append(info + "\n");
					isCheckPass = false;
					continue;
				}
			}

			// wss:全部都要检查仓库

			/*
			 * if(bill.getWareSet() == null){ info = info + " 其单据体未填仓库 ";
			 * infos.append(info + "\n"); isCheckPass = false; continue; }
			 */

		}
		if (isCheckPass == false) {
			infos.append("不能生效!!");
			JOptionPane.showMessageDialog(DgBillMaster.this, infos.toString(),
					"提示！", 2);
			return true;
		}
		return false;
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
					DgBillMaster.this.dispose();
				}
			});

		}
		return btnExit;
	}

	/**
	 * 
	 * This method initializes jPanel2
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(324, 86, 64, 18));
			jLabel4.setText("备注");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(36, 86, 71, 18));
			jLabel2.setText("关封号");
			javax.swing.JLabel jLabel5 = new JLabel();

			jLabel3 = new JLabel();

			javax.swing.JLabel jLabel1 = new JLabel();

			javax.swing.JLabel jLabel = new JLabel();

			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "基本资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jLabel.setText("单据类型");
			jLabel.setBounds(36, 25, 78, 20);
			jLabel1.setBounds(323, 24, 56, 22);
			jLabel1.setText("单据号码");
			jLabel3.setBounds(323, 57, 84, 22);
			jLabel3.setText("客户(供应商)");
			jLabel5.setBounds(36, 58, 78, 20);
			jLabel5.setText("生效日期");
			jPanel2.add(jLabel, null);
			jPanel2.add(getTfBillTypeName(), null);
			jPanel2.add(jLabel1, null);
			jPanel2.add(getTfBillNo(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getCbbScmCoc(), null);
			jPanel2.add(jLabel5, null);
			jPanel2.add(getCbbValidDate(), null);
			jPanel2.add(jLabel2, null);
			jPanel2.add(getTfCustomsCover(), null);
			jPanel2.add(jLabel4, null);
			jPanel2.add(getTfNotice(), null);
			jPanel2.add(getBtnEnvelopNo(), null);
		}
		return jPanel2;
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
			jPanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJPanel3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar1 = new JToolBar();
			jToolBar1.setLayout(f);
			jToolBar1.add(getBtnAdds());
			jToolBar1.add(getBtnAdds1());
			jToolBar1.add(getBtnAddSemiProduct());
			jToolBar1.add(getBtnEdits());
			jToolBar1.add(getBtnDelete());
		}
		return jToolBar1;
	}

	/**
	 * 
	 * This method initializes btnAdds
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdds() {
		if (btnAdds == null) {
			btnAdds = new JButton();
			btnAdds.setText("新增");// 表体新增
			btnAdds.setPreferredSize(new Dimension(60, 30));
			btnAdds.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbValidDate.getDate() == null) {
						JOptionPane.showMessageDialog(DgBillMaster.this,
								"生效日期不能为空！", "提示！", 2);
						return;
					}
					isMateriel = true;
					DgBillDetail dgBillDetail = new DgBillDetail(billMaster,
							tableModelDetail);
					dgBillDetail.setMateriel(isMateriel);
					dgBillDetail.setDataState(DataState.ADD);
					int billType = billMaster.getBillType().getBillType()
							.intValue();
					dgBillDetail.setBillType(billType);

					// 如果是半成品 ，则会从 报关常用工厂物料资料 抓取相应资料
					if (billType == 8 || billType == 7) {
						List lists = ErpBillCenterQuery.getInstance()
								.getBcpFromWlzd(
										false,
										dgBillDetail.getMaterielType(billType,
												isMateriel),
										tableModelDetail.getList());
						if (lists != null && lists.size() > 0) {
							List<BillDetail> updateList = new ArrayList<BillDetail>();
							for (int i = 0; i < lists.size(); i++) {
								BillDetail billDetail = null;
								billDetail = dgBillDetail
										.fillBillDetails((Materiel) lists
												.get(i));
								if (btnAdds.getText().equals("新增来自料件")) {
									billDetail.setNote("料件");
								}
								updateList.add(billDetail);
							}
							updateList = casAction.saveBillDetail(new Request(
									CommonVars.getCurrUser()), updateList);
							tableModelDetail.addRows(updateList);
							dgBillDetail.fillInvNetWeightM(updateList.get(0)
									.getUnitWeight().toString());
							dgBillDetail.setCurrBillDetail(updateList.get(0));
							dgBillDetail
									.setBillCategoryBillDetail(billCategory);
							dgBillDetail.setVisible(true);
						}
					}
					// 从对照表中选择物料
					else {
						List list = ErpBillCenterQuery.getInstance()
								.getFactoryAndFactualCustomsRalationforBill(
										false,
										dgBillDetail.getMaterielType(billType,
												isMateriel),
										tableModelDetail.getList());
						if (list != null && list.size() > 0) {
							List<BillDetail> updateList = new ArrayList<BillDetail>();
							for (int i = 0; i < list.size(); i++) {
								BillDetail billDetail = null;

								// //此处填写了海关资料
								billDetail = dgBillDetail
										.fillBillDetail((FactoryAndFactualCustomsRalation) list
												.get(i));
								if (btnAdds.getText().equals("新增来自料件")) {
									billDetail.setNote("料件");
								}
								// 暂时屏蔽自动对应报关资料功能
								// billDetail =
								// dgBillDetail.autoSetFactualCustoms(billDetail.getPtPart()
								// , cbbValidDate.getDate(), billDetail);
								updateList.add(billDetail);
							}
							updateList = casAction.saveBillDetail(new Request(
									CommonVars.getCurrUser()), updateList);
							tableModelDetail.addRows(updateList);
							dgBillDetail.fillInvNetWeight(dgBillDetail
									.getMaterielType(billType, isMateriel),
									updateList.get(0));
							dgBillDetail.setCurrBillDetail(updateList.get(0));
							dgBillDetail
									.setBillCategoryBillDetail(billCategory);
							dgBillDetail.setVisible(true);
						}
					}
				}
			});
		}
		return btnAdds;
	}

	/**
	 * 
	 * This method initializes btnEdits
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnEdits() {
		if (btnEdits == null) {
			btnEdits = new JButton();
			btnEdits.setText("修改");
			btnEdits.setPreferredSize(new Dimension(60, 30));
			btnEdits.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBillMaster.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}
					editBillDetail();
				}

			});
		}
		return btnEdits;
	}

	/**
	 * 编辑单据明细
	 */
	private void editBillDetail() {
		int billType = billMaster.getBillType().getBillType().intValue();
		DgBillDetail dgBillDetail = new DgBillDetail(billMaster,
				tableModelDetail);
		dgBillDetail.setDataState(DataState.EDIT);

		// dgBillDetail.setFatherTableModel(tableModelDetail);wss2010.09.25发现好像没用故屏蔽

		dgBillDetail.fillInvNetWeight(dgBillDetail.getMaterielType(billType,
				isMateriel), (BillDetail) tableModelDetail.getCurrentRow());
		dgBillDetail.setCurrBillDetail((BillDetail) tableModelDetail
				.getCurrentRow());
		dgBillDetail
				.setPtAmount(((BillDetail) tableModelDetail.getCurrentRow())
						.getPtAmount());
		dgBillDetail.setBillCategoryBillDetail(billCategory);

		// dgBillDetail.doWarehouses();wss2010.09.16屏蔽因为太耗时了，而且作用不大

		dgBillDetail.setVisible(true);
	}

	/**
	 * 
	 * This method initializes btnDelete
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除");
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelDetail.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBillMaster.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					List<BillDetail> listBillDetail = tableModelDetail
							.getCurrentRows();
					if (JOptionPane.showConfirmDialog(DgBillMaster.this,
							"你确定要删除此记录吗?", "确认", 0) == 0) {
						casAction.deleteBillDetail(new Request(CommonVars
								.getCurrUser()), listBillDetail);
						tableModelDetail.deleteRows(listBillDetail);
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 
	 * This method initializes jPanel3
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(
					null, "商品资料",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null,
					null));
			jPanel3.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jTable
	 * 
	 * 
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JFooterTable getJTable() {
		if (jTable == null) {
			jTable = new JFooterTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						if (dataStateBillDetail != DataState.ADD
								&& billMaster != null && !valid()) {
							editBillDetail();
						}
					}
				}
			});
		}
		return jTable;
	}

	/**
	 * 
	 * This method initializes jScrollPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JFooterScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JFooterScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes tfBillTypeName
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfBillTypeName() {
		if (tfBillTypeName == null) {
			tfBillTypeName = new JTextField();
			tfBillTypeName.setEditable(false);
			tfBillTypeName.setBounds(118, 24, 174, 22);
		}
		return tfBillTypeName;
	}

	/**
	 * 
	 * This method initializes tfBillNo
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();
			// jTextField1.setEditable(false);
			tfBillNo.setBounds(410, 26, 172, 22);

		}
		return tfBillNo;
	}

	/**
	 * 
	 * This method initializes cbbScmCoc
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.setBounds(410, 57, 172, 22);
		}
		return cbbScmCoc;
	}

	/**
	 * 
	 * This method initializes jCalendarComboBox
	 * 
	 * 
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 * 
	 */
	private JCalendarComboBox getCbbValidDate() {
		if (cbbValidDate == null) {
			cbbValidDate = new JCalendarComboBox();
			cbbValidDate.setBounds(118, 55, 174, 22);
		}
		return cbbValidDate;
	}

	/**
	 * 获得单据号
	 * 
	 * @return
	 */
	private String getInitBillNo() {
		if (this.casBillOption.getIsInitBillNo() != null
				&& this.casBillOption.getIsInitBillNo().booleanValue() == true) {
			return casAction.getBillNo(billType.getBillType());
		}
		return "";
	}

	/**
	 * 
	 * This method initializes btnCanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnCanel() {
		if (btnCanel == null) {
			btnCanel = new JButton();
			btnCanel.setText("取消");
			btnCanel.setPreferredSize(new Dimension(60, 30));
			btnCanel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (billMaster != null) {
						initUIData();
					} else {
						clearData();
					}
					dataStateBillDetail = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnCanel;
	}

	/**
	 * 清除数据
	 */
	private void clearData() {
		tfBillNo.setText("");
		cbbScmCoc.setSelectedItem(null);
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
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
	 * @return Returns the billMaster.
	 */
	public BillMaster getBillMaster() {
		return billMaster;
	}

	/**
	 * @param billMaster
	 *            The billMaster to set.
	 */
	public void setBillMaster(BillMaster billMaster) {
		this.billMaster = billMaster;
	}

	/**
	 * @return Returns the tableModelDetail.
	 */
	public JTableListModel getTableModelDetail() {
		return tableModelDetail;
	}

	/**
	 * @param tableModelDetail
	 *            The tableModelDetail to set.
	 */
	public void setTableModelDetail(JTableListModel tableModelDetail) {
		this.tableModelDetail = tableModelDetail;
	}

	/**
	 * 是否回卷
	 * 
	 * @return boolean
	 */
	private boolean isUnreel() {
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
					JOptionPane.showMessageDialog(DgBillMaster.this,
							"其单据体数据已与报关单进行了对应，不能回卷！", "提示！",
							JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * This method initializes btnRollBack
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnRollBack() {
		if (btnRollBack == null) {
			btnRollBack = new JButton();
			btnRollBack.setText("回卷");
			btnRollBack.setPreferredSize(new Dimension(60, 30));
			btnRollBack.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 已做对应不可已回卷
					if (!isUnreel()) {
						return;
					}

					// wss2010.10.08新加
					if ("4009".equals(billType.getCode())
							&& isAlreadyConvertToInit()) {
						JOptionPane.showMessageDialog(DgBillMaster.this,
								"此单据已经折算成了单据号为" + billMaster.getNotice()
										+ "的在产品期初单单据，不能回卷！\n"
										+ "确定要回卷，请先删除对应的在产品期初单单据！", "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					// 有数据已进出口申请单在海关帐中 或者 有数据已转厂单据在海关帐中
					if (casAction.hasDataTransferImpExpRequestBillByBillMaster(
							new Request(CommonVars.getCurrUser()), billMaster)
							|| casAction
									.hasDataTransferTransferFactoryBillByBillMaster(
											new Request(CommonVars
													.getCurrUser()), billMaster)) {
						JOptionPane.showMessageDialog(null,
								"在海关单据中有数据已进出口申请,或者有数据已转转厂单据,不能撤消生效!!", "警告",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					billMaster.setIsValid(new Boolean(false));
					billMaster = casAction.saveBillMaster(new Request(
							CommonVars.getCurrUser()), billMaster);
					if (isMasterTable == true) {
						if (!isEditFromQuery)
							tableModel.updateRow(billMaster);
					}

					btnConvertToInit.setEnabled(true);
					setState();
				}
			});
		}
		return btnRollBack;
	}

	/**
	 * 
	 * This method initializes btnBooking
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnBooking() {
		if (btnBooking == null) {
			btnBooking = new JButton();
			btnBooking.setText("记帐");
			btnBooking.setPreferredSize(new Dimension(60, 30));
			btnBooking.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					billMaster.setKeepAccounts(new Boolean(true));
					billMaster = casAction.saveBillMaster(new Request(
							CommonVars.getCurrUser()), billMaster);
					if (isMasterTable == true) {
						tableModel.updateRow(billMaster);
					}
					setState();

				}
			});

		}
		return btnBooking;
	}

	public int getDataState() {
		return dataState;
	}

	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	/**
	 * 设置只读
	 */
	public void onlyView() {
		btnAdd.setEnabled(false);
		btnEdit.setEnabled(false);
		btnSave.setEnabled(false);
		btnRollBack.setEnabled(false);
		btnEffect.setEnabled(false);
		btnExit.setEnabled(false);
		btnAdds.setEnabled(false);
		btnAdds1.setEnabled(false);

		btnAddSemiProduct.setEnabled(false);

		btnEdits.setEnabled(false);
		btnDelete.setEnabled(false);
		btnCanel.setEnabled(false);
		btnBooking.setEnabled(false);
		tfBillTypeName.setEnabled(false);
		tfBillNo.setEnabled(false);
		cbbScmCoc.setEnabled(false);
		cbbValidDate.setEnabled(false);
		tfNotice.setEnabled(false);
	}

	/**
	 * 设置 单据进出仓类别
	 * 
	 * @param billCategory
	 */
	public void setBillCategoryBillMaster(int billCategory) {
		this.billCategory = billCategory;

	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCustomsCover() {
		if (tfCustomsCover == null) {
			tfCustomsCover = new JTextField();
			tfCustomsCover.setBounds(new Rectangle(118, 85, 154, 22));
		}
		return tfCustomsCover;
	}

	/**
	 * @return the validDate
	 */
	public Date getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate
	 *            the validDate to set
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the isEditFromQuery
	 */
	public boolean isEditFromQuery() {
		return isEditFromQuery;
	}

	/**
	 * @param isEditFromQuery
	 *            the isEditFromQuery to set
	 */
	public void setEditFromQuery(boolean isEditFromQuery) {
		this.isEditFromQuery = isEditFromQuery;
	}

	/**
	 * This method initializes tfNotice
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfNotice() {
		if (tfNotice == null) {
			tfNotice = new JTextField();
			tfNotice.setBounds(new Rectangle(410, 85, 174, 22));
		}
		return tfNotice;
	}

	/**
	 * This method initializes btnAdds1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdds1() {
		if (btnAdds1 == null) {
			btnAdds1 = new JButton();
			btnAdds1.setText("新增来自成品");
			btnAdds1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbValidDate.getDate() == null) {
						JOptionPane.showMessageDialog(DgBillMaster.this,
								"生效日期不能为空！", "提示！", 2);
						return;
					}
					isMateriel = false;
					DgBillDetail dgBillDetail = new DgBillDetail(billMaster,
							tableModelDetail);
					dgBillDetail.setMateriel(isMateriel);
					// dgBillDetail.setAdd(true);
					dgBillDetail.setDataState(DataState.ADD);
					int billType = billMaster.getBillType().getBillType()
							.intValue();
					dgBillDetail.setBillType(billType);
					// 从对照表中选择物料
					List list = ErpBillCenterQuery.getInstance()
							.getFactoryAndFactualCustomsRalationforBill(false,
									MaterielType.FINISHED_PRODUCT,
									tableModelDetail.getList());
					if (list != null && list.size() > 0) {
						List<BillDetail> updateList = new ArrayList<BillDetail>();
						for (int i = 0; i < list.size(); i++) {
							BillDetail billDetail = null;
							billDetail = dgBillDetail
									.fillBillDetail((FactoryAndFactualCustomsRalation) list
											.get(i));
							billDetail.setNote("成品");
							// 暂时屏蔽自动对应报关资料功能
							// billDetail =
							// dgBillDetail.autoSetFactualCustoms(billDetail.getPtPart()
							// , cbbValidDate.getDate(), billDetail);
							updateList.add(billDetail);
						}
						updateList = casAction.saveBillDetail(new Request(
								CommonVars.getCurrUser()), updateList);
						tableModelDetail.addRows(updateList);
						dgBillDetail.fillInvNetWeight(dgBillDetail
								.getMaterielType(billType, isMateriel),
								updateList.get(0));
						dgBillDetail.setCurrBillDetail(updateList.get(0));
						dgBillDetail.setBillCategoryBillDetail(billCategory);

						dgBillDetail.setVisible(true);

					}
				}
			});
		}
		return btnAdds1;
	}

	/**
	 * This method initializes btnAddSemiProduct
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAddSemiProduct() {
		if (btnAddSemiProduct == null) {
			btnAddSemiProduct = new JButton();
			btnAddSemiProduct.setText("新增自半成品");
			btnAddSemiProduct
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (cbbValidDate.getDate() == null) {
								JOptionPane.showMessageDialog(
										DgBillMaster.this, "生效日期不能为空！", "提示！",
										2);
								return;
							}
							isMateriel = true;
							DgBillDetail dgBillDetail = new DgBillDetail(
									billMaster, tableModelDetail);
							dgBillDetail.setMateriel(isMateriel);
							// dgBillDetail.setAdd(true);
							dgBillDetail.setDataState(DataState.ADD);

							int billType = billMaster.getBillType()
									.getBillType().intValue();

							System.out.println("billType=" + billType);

							dgBillDetail.setBillType(billType);

							// 从 报关常用工厂物料 (半成品、残次品) 中抓资料
							List lists = ErpBillCenterQuery.getInstance()
									.getMForBadBillDetial(false,
											tableModelDetail.getList());
							if (lists != null && lists.size() > 0) {
								List<BillDetail> updateList = new ArrayList<BillDetail>();
								for (int i = 0; i < lists.size(); i++) {
									BillDetail billDetail = null;
									billDetail = dgBillDetail
											.fillBillDetails((Materiel) lists
													.get(i));
									billDetail.setNote("半成品");
									updateList.add(billDetail);
								}
								updateList = casAction.saveBillDetail(
										new Request(CommonVars.getCurrUser()),
										updateList);
								tableModelDetail.addRows(updateList);

								dgBillDetail.fillInvNetWeightM(updateList
										.get(0).getUnitWeight().toString());
								dgBillDetail.setCurrBillDetail(updateList
										.get(0));
								dgBillDetail
										.setBillCategoryBillDetail(billCategory);
								dgBillDetail.setVisible(true);
							}

						}
					});
		}
		return btnAddSemiProduct;
	}

	/**
	 * This method initializes btnEnvelopNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEnvelopNo() {
		if (btnEnvelopNo == null) {
			btnEnvelopNo = new JButton();
			btnEnvelopNo.setBounds(new Rectangle(272, 85, 20, 22));
			btnEnvelopNo.setText("...");
			btnEnvelopNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					ScmCoc scmCoc = (ScmCoc) cbbScmCoc.getSelectedItem();

					Object obj = CasQuery.getInstance().findEnvelopNoByScmCoc(
							billType.getCode(), scmCoc);
					if (obj != null) {
						TempObject temp = (TempObject) obj;
						tfCustomsCover.setText((String) temp.getObject());
					}
				}
			});
		}
		return btnEnvelopNo;
	}

	/**
	 * 折算在产品期初单按钮，将半成品期初单折算成在产品期初单 不需要了。因为在计算年审报表中已加半成品折料的计算且放入在产品期初单中
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

							// 提示内容
							String message = "";

							// 判断是否已经折算过
							boolean isExist = isAlreadyConvertToInit();

							if (isExist) {
								message = "该半成品期初已经折算成在产品期初单，是否确定重新折算？";
							} else {
								message = "请确定在产品期初单是否包含半成品期初单，若是，建议不折算.\n"
										+ "如果需要折算，请点击【确定】，否则点击【取消】。";
							}
							// 2.提示对话框
							if (JOptionPane.showConfirmDialog(FmMain
									.getInstance(), message, "友情提示！",
									JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
								// 3.将关成品期间单折算在产品期初单
								new ConvertToInit().start();

							}

						}
					});
		}
		return btnConvertToInit;
	}

	/**
	 * 半成品期初单折算在产品期初单线程
	 * 
	 * @author wss2010.10.08
	 */
	class ConvertToInit extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(DgBillMaster.this);
				CommonProgress.setMessage("系统正在进行折算，请稍后...");
				billMaster = casAction.convertToInitFromSemiProductInit(
						new Request(CommonVars.getCurrUser()), billMaster);
				getTfNotice().setText(billMaster.getNotice());// 备注写入
				tableModel.updateRow(billMaster);

				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgBillMaster.this, "折算完成！", "提示",
						2);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();

				JOptionPane.showMessageDialog(DgBillMaster.this, "数据折算失败：！"
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
	 * @author wss
	 */
	private boolean isAlreadyConvertToInit() {

		// 注释栏中的单据号码
		String billNo = billMaster.getNotice() == null ? "" : billMaster
				.getNotice();

		if ("".equals(billNo)) {
			return false;
		}

		// 判断是否已经折算过
		return casAction.isAlreadyConvertToInit(new Request(CommonVars
				.getCurrUser()), billNo);
	}

}
