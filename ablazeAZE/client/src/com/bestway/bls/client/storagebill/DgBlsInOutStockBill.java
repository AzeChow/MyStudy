package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BillImpExpFlag;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;
import javax.swing.JSplitPane;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

/**
 * 进出仓单据编辑窗体
 * 
 * @author hw
 * 
 */
public class DgBlsInOutStockBill extends JDialogBase {

	private JPanel jPanel = null;

	/**
	 * 进出仓标志
	 */
	private String inOutFlag;  //  @jve:decl-index=0:

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JToolBar jJToolBarBar = null;

	private JScrollPane jScrollPane = null;

	private JTable tbBlsInOutStockBillDetail = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JToolBar jJToolBarBar1 = null;

	private JPanel jPanel3 = null;

	private JButton btnModifiy = null;

	private JButton btnSave = null;

	private JLabel jLabel = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JCalendarComboBox cbbEffectDate = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JComboBox cbbIOFlag = null;

	private JComboBox cbbCorrOwner = null;

	private JComboBox cbbWareHouseCode = null;

	private MaterialManageAction materialManageAction = (MaterialManageAction) CommonVars
			.getApplicationContext().getBean("materialManageAction"); // @jve:decl-index=0:

	public BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:

	private JTextField tfBillNo = null;; // @jve:decl-index=0:

	public JTableListModel tableModel = null;
	/**
	 * 主窗体进仓
	 */
	public JTableListModel fathertableModelIn = null;

	/**
	 * 主窗体出仓
	 */
	public JTableListModel fathertableModelOut = null;

	public JTableListModel getFathertableModelOut() {
		return fathertableModelOut;
	}

	public void setFathertableModelOut(JTableListModel fathertableModelOut) {
		this.fathertableModelOut = fathertableModelOut;
	}

	public JTableListModel getFathertableModel() {
		return fathertableModelIn;
	}

	public void setFathertableModel(JTableListModel fathertableModel) {
		this.fathertableModelIn = fathertableModel;
	}

	/**
	 * 组建状态标示位
	 */
	private int dataState = DataState.ADD;

	private BlsInOutStockBill blsInOutStockBill = null; // @jve:decl-index=0:

	private JButton btnExit = null;

	private JButton btnShutDown = null;

	private JButton btnPrevious = null;

	private JButton btnNext = null;
	/**
	 * 物料实体类
	 */
	Materiel m = null;

	private JButton btnEffective = null;

	private JButton btnBackOff = null;

	private JLabel jLabel5 = null;

	private JTextField tfCreatePeople = null;

	private JLabel jLabel6 = null;

	private JCalendarComboBox cbbCreateDate = null;

	private JSplitPane jSplitPane = null;

	private JLabel jLabel7 = null;

	private JCheckBox cbIsFirstBill = null;

	/**
	 * 获取组建状态标识位
	 * 
	 * @return
	 */
	public int getDataState() {
		return dataState;
	}

	/**
	 * 设置组建状态标识位
	 * 
	 * @param dataState
	 */
	public void setDataState(int dataState) {
		this.dataState = dataState;
	}

	public BlsInOutStockBill getBlsInOutStockBill() {
		return blsInOutStockBill;
	}

	public void setBlsInOutStockBill(BlsInOutStockBill blsInOutStockBill) {
		this.blsInOutStockBill = blsInOutStockBill;
		if (blsInOutStockBill == null) {
			blsInOutStockBill = new BlsInOutStockBill();
		}
		List list = blsInOutStockBillAction.findBlsInOutStockBillDetail(
				new Request(CommonVars.getCurrUser()), blsInOutStockBill
						.getId());
		// System.out.println("blsInOutStockBill.getId()="
		// + blsInOutStockBill.getId());
		initTable(list);
	}

	/**
	 * 构造方法，初始化组件
	 */
	public DgBlsInOutStockBill(BlsInOutStockBill blsInOutStockBill) {
		super();
		initialize();

		initUIComponents();
		if (blsInOutStockBill == null) {
			blsInOutStockBill = new BlsInOutStockBill();
		}
		List list = blsInOutStockBillAction.findBlsInOutStockBillDetail(
				new Request(CommonVars.getCurrUser()), blsInOutStockBill
						.getId());
		// System.out.println("blsInOutStockBill.getId()="
		// + blsInOutStockBill.getId());
		initTable(list);
		// emptyData();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(758, 507));
		this.setTitle("进出仓单据编辑");
		this.setContentPane(getJPanel());

	}

	/**
	 * 初始化窗体中控件的初始值
	 * 
	 */
	protected void initUIComponents() {
		// btnAdd.setEnabled(false);
		this.cbbIOFlag.removeAllItems();
		this.cbbIOFlag.addItem(new ItemProperty(BillImpExpFlag.IMPORT, "进仓"));
		this.cbbIOFlag.addItem(new ItemProperty(BillImpExpFlag.EXPORT, "出仓"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbIOFlag);
		this.cbbIOFlag.setRenderer(CustomBaseRender.getInstance().getRender());
		this.cbbIOFlag.setSelectedIndex(-1);
		System.out.println(new Date());
		this.cbbEffectDate.setDate(new Date());
		CompanyOther other = CommonVars.getOther();
		if (other != null && other.getIsAutoGetDjNo() != null
				&& other.getIsAutoGetDjNo().equals(Boolean.valueOf(true))) {
			tfBillNo
					.setText(String.valueOf(blsInOutStockBillAction
							.getMaxBillNoByType(new Request(CommonVars
									.getCurrUser()))));
			System.out.println("tfBillNo="
					+ String.valueOf(blsInOutStockBillAction
							.getMaxBillNoByType(new Request(CommonVars
									.getCurrUser()))));
		}

		// 初始化供货方企业下拉框
		List list = materialManageAction.findScmCocs(new Request(CommonVars
				.getCurrUser()));
		this.cbbCorrOwner.removeAllItems();
		this.cbbCorrOwner.setModel(new DefaultComboBoxModel(list.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbCorrOwner,
				"code", "name", 250);
		this.cbbCorrOwner.setRenderer(CustomBaseRender.getInstance().getRender(
				"code", "name", 80, 170));
		// 初始化仓库编码下拉框
		List wareList = materialManageAction.findWareSet(new Request(CommonVars
				.getCurrUser()));
		this.cbbWareHouseCode.removeAllItems();
		this.cbbWareHouseCode.setModel(new DefaultComboBoxModel(wareList
				.toArray()));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				cbbWareHouseCode, "code", "name", 250);
		this.cbbWareHouseCode.setRenderer(CustomBaseRender.getInstance()
				.getRender("code", "name", 80, 170));
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
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
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
			jPanel1.add(getJJToolBarBar1(), BorderLayout.NORTH);
			jPanel1.add(getJPanel3(), BorderLayout.CENTER);
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
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJJToolBarBar(), BorderLayout.NORTH);
			jPanel2.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getBtnAdd());
			jJToolBarBar.add(getBtnEdit());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnShutDown());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbBlsInOutStockBillDetail());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbBlsInOutStockBillDetail
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbBlsInOutStockBillDetail() {
		if (tbBlsInOutStockBillDetail == null) {
			tbBlsInOutStockBillDetail = new JTable();
			tbBlsInOutStockBillDetail
					.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent e) {
							if (e.getClickCount() != 2) {
								return;
							}
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										DgBlsInOutStockBill.this, "请选择要修改的数据！",
										"提示！", JOptionPane.WARNING_MESSAGE);
								return;
							}
							DgBlsInOutStockBillDetail dg = new DgBlsInOutStockBillDetail(
									(BlsInOutStockBillDetail) tableModel
											.getCurrentRow());
							dg
									.setBlsInOutStockBillDetail((BlsInOutStockBillDetail) tableModel
											.getCurrentRow());
							dg.setDataState(DataState.BROWSE);
							dg.setTableModel(tableModel);
							dg.setDgg(DgBlsInOutStockBill.this);
							dg
									.setBlsInOutStockBillDetail((BlsInOutStockBillDetail) tableModel
											.getCurrentRow());
							dg.getBtnEdit().setEnabled(true);
							dg.setBlsInOutStockBill(blsInOutStockBill);
							dg.setVisibles(true);
						}
					});
		}
		return tbBlsInOutStockBillDetail;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
//		if()
//		{}
		tableModel = new JTableListModel(tbBlsInOutStockBillDetail, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("商品流水号", "seqNo", 100));
						list.add(addColumn("企业内部货号", "partNo.ptNo", 100));
						list.add(addColumn("商品名称", "warehouseName", 120));
						list.add(addColumn("规格型号", "spec", 140));
						list.add(addColumn("单位", "unit.name", 80));
						list.add(addColumn("数量", "detailQty", 80));
						list.add(addColumn("原产国", "originCountry.name", 100));
						list.add(addColumn("申报单价", "unitPrice", 80));
						list.add(addColumn("总价", "totalPrice", 80));
						list.add(addColumn("币值", "curr.name", 80));
						list.add(addColumn("毛重", "grossWeight", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("件数", "packCount", 80));
						list.add(addColumn("归并序号", "seqNum", 80));
						list.add(addColumn("报关单编号", "entryID", 80));
						list.add(addColumn("报关单商品序号", "entryGNo", 80));
						list.add(addColumn("账册序号", "emsNo", 100));
						list.add(addColumn("备注一", "remarks1", 80));
						list.add(addColumn("备注二", "remarks2", 80));
						return list;
					}
				});
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
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("inOutFlag="+inOutFlag);
					if (inOutFlag.equals(BlsIOStockBillIOF.IMPORT)) {
						List list = (List) BlsCommonQuery.getInstance()
								.getMaterielNotInBlsInnerMerge();
						if (list == null || list.size() <= 0) {
							return;
						}
						List list2 = blsInOutStockBillAction
								.importInnerMergeDataFromMateriel(new Request(
										CommonVars.getCurrUser()),
										blsInOutStockBill, list);
						List tableList = tableModel.getList();
						tableModel.addRows(list2);
						DgBlsInOutStockBillDetail dg = new DgBlsInOutStockBillDetail(
								(BlsInOutStockBillDetail) tableModel
										.getCurrentRow());
						dg.setBlsInOutStockBill(blsInOutStockBill);
						dg.setDataState(DataState.ADD);
						dg.setTableModel(tableModel);
						dg.setDgg(DgBlsInOutStockBill.this);
						dg.setDataSource(tableList);
						dg.setMateriels(m);
						dg.setInOutFlag(BlsIOStockBillIOF.IMPORT);
						dg.setVisible(true);
					} else {
						List list = (List) BlsCommonQuery.getInstance()
						.getMaterielNotInBlsInnerMerge();
				if (list == null || list.size() <= 0) {
					return;
				}
				List list2 = blsInOutStockBillAction
						.importInnerMergeDataFromMateriel(new Request(
								CommonVars.getCurrUser()),
								blsInOutStockBill, list);
				List tableList = tableModel.getList();
				tableModel.addRows(list2);
				DgBlsInOutStockBillDetail dg = new DgBlsInOutStockBillDetail(
						(BlsInOutStockBillDetail) tableModel
								.getCurrentRow());
				dg.setBlsInOutStockBill(blsInOutStockBill);
				dg.setDataState(DataState.ADD);
				dg.setTableModel(tableModel);
				dg.setDgg(DgBlsInOutStockBill.this);
				dg.setDataSource(tableList);
				dg.setMateriels(m);
				dg.setInOutFlag(BlsIOStockBillIOF.EXPORT);
				dg.setVisible(true);
					}

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
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
								"请选择要修改的数据！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					DgBlsInOutStockBillDetail dg = new DgBlsInOutStockBillDetail(
							(BlsInOutStockBillDetail) tableModel
									.getCurrentRow());
					dg.setDataState(DataState.EDIT);
					dg.setDgg(DgBlsInOutStockBill.this);
					dg.setTableModel(tableModel);
					dg.setBlsInOutStockBill(blsInOutStockBill);
					dg
							.setBlsInOutStockBillDetail((BlsInOutStockBillDetail) tableModel
									.getCurrentRow());
					dg.setVisibles(true);
				}
			});
		}
		return btnEdit;
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
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
								"请选择要删除的数据行!", "提示", 0);
						return;
					}
					deleteRow();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 删除一行信息
	 */
	public void deleteRow() {
		if (tableModel.getCurrentRows() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
		}
		BlsInOutStockBillDetail bsb = null;
		bsb = (BlsInOutStockBillDetail) tableModel.getCurrentRow();
		// System.out.println("bsb=" + bsb);
		blsInOutStockBillAction.deleteBlsInOutStockBillDetail(new Request(
				CommonVars.getCurrUser()), bsb);
		tableModel.deleteRow(bsb);
	}

	/**
	 * This method initializes jJToolBarBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar1() {
		if (jJToolBarBar1 == null) {
			jJToolBarBar1 = new JToolBar();
			jJToolBarBar1.add(getBtnModifiy());
			jJToolBarBar1.add(getBtnEffective());
			jJToolBarBar1.add(getBtnBackOff());
			jJToolBarBar1.add(getBtnSave());
			jJToolBarBar1.add(getBtnExit());
			jJToolBarBar1.add(getBtnPrevious());
			jJToolBarBar1.add(getBtnNext());
		}
		return jJToolBarBar1;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {

			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(386, 147, 54, 18));
			jLabel7.setText("期初单");

			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(125, 148, 53, 18));
			jLabel6.setText("录入日期");

			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(385, 105, 44, 18));
			jLabel5.setText(" 录入人 ");

			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(125, 105, 73, 18));
			jLabel4.setForeground(Color.blue);
			jLabel4.setText("仓库编码");

			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(385, 65, 63, 18));
			jLabel3.setText(" 供货方企业");

			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(125, 64, 77, 18));
			jLabel2.setText("单据生效日期");

			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(385, 27, 50, 18));
			jLabel1.setForeground(Color.blue);
			jLabel1.setText("单据编号");

			jLabel = new JLabel();
			jLabel.setForeground(Color.BLUE);
			jLabel.setBounds(new Rectangle(125, 24, 74, 18));
			jLabel.setText("进出仓标志");

			jPanel3 = new JPanel();
			jPanel3.setLayout(null);
			jPanel3.add(jLabel, null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			jPanel3.add(getCbbEffectDate(), null);
			jPanel3.add(jLabel3, null);
			jPanel3.add(jLabel4, null);
			jPanel3.add(getCbbIOFlag(), null);
			jPanel3.add(getCbbCorrOwnerCode(), null);
			jPanel3.add(getCbbWareHouseCode(), null);
			jPanel3.add(getTfBillNo(), null);
			jPanel3.add(jLabel5, null);
			jPanel3.add(getTfCreatePeople(), null);
			jPanel3.add(jLabel6, null);
			jPanel3.add(getCbbCreateDate(), null);
			jPanel3.add(jLabel7, null);
			jPanel3.add(getCbIsFirstBill(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes btnModifiy
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnModifiy() {
		if (btnModifiy == null) {
			btnModifiy = new JButton();
			btnModifiy.setText("修改");
			btnModifiy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnModifiy;
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
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("inOutFlag="+inOutFlag);
					if (!checkData()) {
						return;
					}
					fillData();
					String id = blsInOutStockBill.getId();
					blsInOutStockBill = blsInOutStockBillAction
							.saveBlsInOutStockBill(new Request(CommonVars
									.getCurrUser()), blsInOutStockBill);

					System.out.println(id);
					if (id == null) {
						// System.out.println("ssss");
						if (blsInOutStockBill.getIoFlag().equals(
								BlsIOStockBillIOF.IMPORT)) {
							fathertableModelIn.addRow(blsInOutStockBill);
						} else {
							fathertableModelOut.addRow(blsInOutStockBill);
						}

					} else {
						fathertableModelIn.updateRow(blsInOutStockBill);
					}
					dataState = DataState.BROWSE;
					setState();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 设置组建状态
	 */
	public void setState() {
		if (blsInOutStockBill == null) {
			blsInOutStockBill = new BlsInOutStockBill();
		}
		// this.btnAdd.setEnabled(dataState != DataState.BROWSE);
		this.tfCreatePeople.setEnabled(dataState != DataState.BROWSE);
		this.tfBillNo.setEnabled(dataState != DataState.BROWSE);
		// 车次号
		this.cbbIOFlag.setEnabled(dataState != DataState.BROWSE);
		// 车牌号
		this.cbbEffectDate.setEnabled(dataState != DataState.BROWSE);
		this.cbbCorrOwner.setEnabled(dataState != DataState.BROWSE);
		this.cbbWareHouseCode.setEnabled(dataState != DataState.BROWSE);
		this.cbIsFirstBill.setEnabled(dataState != DataState.BROWSE);
		// this.btnAdd.setEnabled(dataState == DataState.BROWSE? true
		// : false);
		this.btnSave.setEnabled(dataState != DataState.BROWSE);// 保存
		this.btnBackOff
				.setEnabled((!(blsInOutStockBill.getIsEffective() == null || !blsInOutStockBill
						.getIsEffective())));// 回卷

		btnModifiy
				.setEnabled((blsInOutStockBill.getIsEffective() == null || !blsInOutStockBill
						.getIsEffective())
						&& dataState == DataState.BROWSE);
		btnEffective
				.setEnabled((blsInOutStockBill.getIsEffective() == null || !blsInOutStockBill
						.getIsEffective())
						&& dataState == DataState.BROWSE);
		this.btnPrevious.setEnabled(this.dataState == DataState.BROWSE ? true
				: false);
		this.btnNext.setEnabled(this.dataState == DataState.BROWSE ? true
				: false);
		this.cbbCreateDate.setEnabled(dataState != DataState.BROWSE);
		this.btnAdd.setEnabled((dataState == DataState.BROWSE
				&& !blsInOutStockBill.getIsEffective())||dataState == DataState.EDIT);
		this.btnEdit.setEnabled(!blsInOutStockBill.getIsEffective());
		this.btnDelete.setEnabled(!blsInOutStockBill.getIsEffective());
		this.jLabel7.setVisible(BlsIOStockBillIOF.IMPORT
				.equals(blsInOutStockBill.getIoFlag()));

	}

	/**
	 * 填充并保存数据
	 * 
	 * @param data
	 */
	private void fillData() {
		if (this.dataState == DataState.ADD) {
			blsInOutStockBill = new BlsInOutStockBill();
		}
		ItemProperty item = (ItemProperty) cbbIOFlag.getSelectedItem();
		// System.out.println(item.getCode());
		blsInOutStockBill.setBillNo(tfBillNo.getText());
		blsInOutStockBill.setCorrOwner((ScmCoc) cbbCorrOwner.getSelectedItem());
		blsInOutStockBill.setCreateDate(cbbCreateDate.getDate());
		blsInOutStockBill.setValidDate(cbbEffectDate.getDate());
		blsInOutStockBill.setIoFlag(item.getCode());
		blsInOutStockBill.setWareHouseCode((WareSet) cbbWareHouseCode
				.getSelectedItem());
		blsInOutStockBill.setCreatePeople(this.tfCreatePeople.getText());

		if (BlsIOStockBillIOF.IMPORT.equals(blsInOutStockBill.getIoFlag())) {
			blsInOutStockBill.setIsFirstBill(cbIsFirstBill.isSelected());
		}

	}

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (cbbIOFlag.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
					"进出仓标志必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		String billNo = this.tfBillNo.getText();
		if (billNo == null || billNo.equals("")) {
			JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
					"单据编号必需填写！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}

		if (blsInOutStockBill.getId() == null) {
			List list = blsInOutStockBillAction.findBillNo(new Request(
					CommonVars.getCurrUser()), billNo);
			if (!list.isEmpty()) {
				JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
						"单据编号重复，请重新填写！", "提示！", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		if (cbbWareHouseCode.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
					"仓库编码必需选择！", "提示！", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * 显示数据
	 */
	private void showData() {

		if (blsInOutStockBill == null) {
			blsInOutStockBill = new BlsInOutStockBill();
		}
		if (blsInOutStockBill.getIoFlag() != null) {
			cbbIOFlag.setSelectedIndex(ItemProperty.getIndexByCode(
					this.blsInOutStockBill.getIoFlag(), cbbIOFlag));
		} else {
			cbbIOFlag.setSelectedIndex(-1);
		}
		if (dataState == DataState.ADD) {
			initBillNo();
		} else {
			this.tfBillNo.setText(blsInOutStockBill.getBillNo());
		}
		if (blsInOutStockBill.getValidDate() != null) {
			this.cbbEffectDate.setDate(blsInOutStockBill.getValidDate());
		} else {
			this.cbbEffectDate.setDate(new Date());
		}
		this.cbbCorrOwner.setSelectedItem(blsInOutStockBill.getCorrOwner());
		this.cbbWareHouseCode.setSelectedItem(blsInOutStockBill
				.getWareHouseCode());
		this.tfCreatePeople.setText(blsInOutStockBill.getCreatePeople());
		if (blsInOutStockBill.getCreateDate() != null) {
			this.cbbCreateDate.setDate(blsInOutStockBill.getCreateDate());
		} else {
			this.cbbCreateDate.setDate(new Date());
		}
		if (blsInOutStockBill.getCreatePeople() != null) {
			this.tfCreatePeople.setText(blsInOutStockBill.getCreatePeople());
		} else {
			this.tfCreatePeople.setText("");
		}

		if (blsInOutStockBill.getIsFirstBill() != null) {
			this.cbIsFirstBill.setSelected(blsInOutStockBill.getIsFirstBill());
		}
	}

	/**
	 * 清空数据
	 * 
	 */
	private void emptyData() {
		this.tfBillNo.setText("");
		this.cbbIOFlag.setSelectedIndex(-1);
		this.cbbEffectDate.setDate(null);
		this.cbbWareHouseCode.setSelectedItem(null);
		this.cbbCorrOwner.setSelectedItem(null);
		this.cbbWareHouseCode.setSelectedItem(null);
	}

	/**
	 * 初始化单据号
	 */
	public void initBillNo() {
		CompanyOther other = CommonVars.getOther();
		if (other != null && other.getIsAutoGetDjNo() != null
				&& other.getIsAutoGetDjNo().equals(Boolean.valueOf(true))) {
			tfBillNo
					.setText(String.valueOf(blsInOutStockBillAction
							.getMaxBillNoByType(new Request(CommonVars
									.getCurrUser()))));
			// System.out.println("tfBillNo="
			// + String.valueOf(blsInOutStockBillAction
			// .getMaxBillNoByType(new Request(CommonVars
			// .getCurrUser()))));
		}
	}

	@Override
	public void setVisible(boolean f) {
		if (f) {
			showData();
			setState();
			super.setVisible(f);
		}
	}

	/**
	 * This method initializes cbbEffectDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEffectDate() {
		if (cbbEffectDate == null) {
			cbbEffectDate = new JCalendarComboBox();
			cbbEffectDate.setDate(new Date());

			cbbEffectDate.setBounds(new Rectangle(204, 63, 161, 22));

		}
		return cbbEffectDate;
	}

	/**
	 * This method initializes cbbIOFlag
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbIOFlag() {
		if (cbbIOFlag == null) {
			cbbIOFlag = new JComboBox();

			cbbIOFlag.setBounds(new Rectangle(204, 24, 161, 20));

			cbbIOFlag.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					ItemProperty item = (ItemProperty)cbbIOFlag.getSelectedItem();
					if(item==null){
						jLabel7.setVisible(false);
						cbIsFirstBill.setSelected(false);
						cbIsFirstBill.setVisible(false);
					}else if(BlsIOStockBillIOF.IMPORT.equals(item.getCode())){
						jLabel7.setVisible(true);
						cbIsFirstBill.setVisible(true);
					}else{
						jLabel7.setVisible(false);
						cbIsFirstBill.setSelected(false);
						cbIsFirstBill.setVisible(false);
					}
					
				}
			});
		}
		return cbbIOFlag;
	}

	/**
	 * This method initializes cbbCorrOwnerCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbCorrOwnerCode() {
		if (cbbCorrOwner == null) {
			cbbCorrOwner = new JComboBox();

			cbbCorrOwner.setBounds(new Rectangle(448, 65, 161, 20));

		}
		return cbbCorrOwner;
	}

	/**
	 * This method initializes cbbWareHouseCode
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWareHouseCode() {
		if (cbbWareHouseCode == null) {
			cbbWareHouseCode = new JComboBox();

			cbbWareHouseCode.setBounds(new Rectangle(204, 105, 161, 20));

		}
		return cbbWareHouseCode;
	}

	/**
	 * This method initializes tfBillNo
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBillNo() {
		if (tfBillNo == null) {
			tfBillNo = new JTextField();

			tfBillNo.setBounds(new Rectangle(448, 26, 161, 22));

		}
		return tfBillNo;
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
	 * This method initializes btnShutDown
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShutDown() {
		if (btnShutDown == null) {
			btnShutDown = new JButton();
			btnShutDown.setText("关闭");
			btnShutDown.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnShutDown;
	}

	/**
	 * This method initializes btnPrevious
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton();
			btnPrevious.setText("上一笔");
			btnPrevious.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!fathertableModelIn.previousRow()) {
						btnPrevious.setEnabled(false);
						btnNext.setEnabled(true);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					blsInOutStockBill = (BlsInOutStockBill) fathertableModelIn
							.getCurrentRow();
					showData();
				}
			});
		}
		return btnPrevious;
	}

	/**
	 * This method initializes btnNext
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton();
			btnNext.setText("下一笔");
			btnNext.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!fathertableModelIn.nextRow()) {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(false);
					} else {
						btnPrevious.setEnabled(true);
						btnNext.setEnabled(true);
					}
					blsInOutStockBill = (BlsInOutStockBill) fathertableModelIn
							.getCurrentRow();
					showData();
				}
			});
		}
		return btnNext;
	}

	/**
	 * This method initializes btnEffective
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffective() {
		if (btnEffective == null) {
			btnEffective = new JButton();
			btnEffective.setText("生效");
			btnEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState != DataState.BROWSE) {
						JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
								"请先保存数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					// List list = tableModel.getList();
					if (tableModel.getList() == null
							|| tableModel.getList().isEmpty()) {
						JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
								"表体信息为空，不能第生效！", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					blsInOutStockBill.setIsEffective(true);
					blsInOutStockBill = blsInOutStockBillAction
							.saveBlsInOutStockBill(new Request(CommonVars
									.getCurrUser()), blsInOutStockBill);
					fathertableModelIn.updateRow(blsInOutStockBill);
					dataState = DataState.BROWSE;
					setState();
					btnModifiy.setEnabled(false);
				}
			});
		}
		return btnEffective;
	}

	/**
	 * This method initializes btnBackOff
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBackOff() {
		if (btnBackOff == null) {
			btnBackOff = new JButton();
			btnBackOff.setText("回卷");
			btnBackOff.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (dataState != DataState.BROWSE) {
						JOptionPane.showMessageDialog(DgBlsInOutStockBill.this,
								"请先保存数据！", "提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					blsInOutStockBill.setIsEffective(false);
					blsInOutStockBill = blsInOutStockBillAction
							.saveBlsInOutStockBill(new Request(CommonVars
									.getCurrUser()), blsInOutStockBill);
					fathertableModelIn.updateRow(blsInOutStockBill);
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnBackOff;
	}

	/**
	 * This method initializes tfCreatePeople
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCreatePeople() {
		if (tfCreatePeople == null) {
			tfCreatePeople = new JTextField();

			tfCreatePeople.setBounds(new Rectangle(448, 105, 161, 20));

		}
		return tfCreatePeople;
	}

	/**
	 * This method initializes cbbCreateDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbCreateDate() {
		if (cbbCreateDate == null) {
			cbbCreateDate = new JCalendarComboBox();
			cbbCreateDate.setBounds(new Rectangle(204, 144, 161, 20));
			cbbCreateDate.setDate(new Date());
		}
		return cbbCreateDate;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel1());
			jSplitPane.setBottomComponent(getJPanel2());
			jSplitPane.setDividerLocation(220);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes cbIsFirstBill
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsFirstBill() {
		if (cbIsFirstBill == null) {
			cbIsFirstBill = new JCheckBox();
			cbIsFirstBill.setBounds(new Rectangle(460, 146, 21, 21));
		}
		return cbIsFirstBill;
	}

} // @jve:decl-index=0:visual-constraint="63,9"
