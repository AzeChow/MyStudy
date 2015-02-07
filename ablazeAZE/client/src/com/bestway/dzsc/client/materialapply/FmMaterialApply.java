/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.dzsc.client.materialapply;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.system.entity.Company;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscBusinessType;
import com.bestway.common.constant.DeclareFileInfo;
import com.bestway.common.constant.DzscState;
import com.bestway.common.constant.ManageObject;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.materialapply.action.MaterialApplyAction;
import com.bestway.dzsc.materialapply.entity.DzscMaterielHead;
import com.bestway.dzsc.materialapply.entity.MaterialApply;
import com.bestway.dzsc.materialapply.entity.MaterialChange;
import com.bestway.dzsc.materialapply.entity.TempMaterialApplySelectParam;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * @author Administrator
 *  // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FmMaterialApply extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private MaterialApplyAction materialApplyAction = null;

	private MaterialManageAction materialManageAction = null;

	private DzscMaterielHead dzscMaterielHead = null; // @jve:decl-index=0:

	private JPanel jPanel4 = null;

	private JScrollPane jScrollPane1 = null;

	private ScmCoi scmCoiSelected = null;

	private JList jList = null;

	private JTableListModel tableModel = null;

	private JButton btnExit = null;

	private JPanel jPanel6 = null;

	private JToolBar jToolBar1 = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private JButton btnDeclare = null;

	private JButton btnChange = null;

	private JButton btnEffective = null;

	private JRadioButton rbNotChanged = null;

	private JRadioButton rbChanged = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="125,397"

	private JButton btnBrowse = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel5 = null;

	private JLabel jLabel1 = null;

	private JTextField tfCopEntNo = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JTextField tfMasterCustoms = null;

	private JTextField tfCode = null;

	private JTextField tfName = null;

	private JTextField tfBuCode = null;

	private JTextField tfBuName = null;

	private JCalendarComboBox cbbInputDate = null;

	private JCalendarComboBox cbbDeclareDate = null;

	private JButton btnEditHead = null;

	private JButton btnSava = null;

	private int dataState = DataState.BROWSE;

	private JButton btnDeleteApply = null;

	private JLabel jLabel9 = null;

	private JComboBox cbbManageObject = null;

	private JPopupMenu pmOtherFunction = null; // @jve:decl-index=0:visual-constraint="983,28"

	private JMenuItem miShowForbid = null;

	private JMenuItem miCancelForbid = null;

	private JButton btnOtherFunction = null;

	private JMenuItem miShowAll = null;

	private JMenuItem jMenuItem = null;

	/**
	 * This is the default constructor
	 */
	public FmMaterialApply() {
		super();
		initialize();
		materialApplyAction = (MaterialApplyAction) CommonVars
				.getApplicationContext().getBean("materialApplyAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		this.getJTable().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.getJTable().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {
							return;
						}
						JTableListModel tableModel = (JTableListModel) getJTable()
								.getModel();
						if (tableModel == null) {
							return;
						}
						try {
							if (tableModel.getCurrentRow() != null) {
								setState();
							}
						} catch (Exception ee) {
						}
					}
				});
	}

	/**
	 * 设置显示
	 */
	public void setVisible(boolean b) {
		if (b) {
			initUIComponents();
			showData();
			setState();
		}
		super.setVisible(b);
	}

	private void showData() {
		if (jTabbedPane.getSelectedIndex() == 0) {
			dzscMaterielHead = materialApplyAction
					.findDzscMaterielHead(new Request(CommonVars.getCurrUser()));
			showHeadData(dzscMaterielHead);
		} else {
			dzscMaterielHead = materialApplyAction
					.findDzscMaterielHead(new Request(CommonVars.getCurrUser()));
			showHeadData(dzscMaterielHead);
			Vector<Object> vector = new Vector<Object>();
			List cois = this.materialManageAction.findScmCois(new Request(
					CommonVars.getCurrUser(), true));
			for (int i = 0; i < cois.size(); i++) {
				ScmCoi coi = (ScmCoi) cois.get(i);
				if (MaterielType.MATERIEL.equals(coi.getCoiProperty())
						|| MaterielType.FINISHED_PRODUCT.equals(coi
								.getCoiProperty())
						|| MaterielType.MACHINE.equals(coi.getCoiProperty())) {
					vector.add(coi);
				}
			}
			this.jList.setListData(vector);
			this.jList.setCellRenderer(new UserListCellRenderer());
			if (this.jList.getModel().getSize() > 0) {
				scmCoiSelected = ((ScmCoi) this.jList.getModel()
						.getElementAt(0));
				this.jList.setSelectedIndex(0);
			}
		}
	}

	private void showHeadData(DzscMaterielHead dzscMaterielHead) {
		this.tfCopEntNo.setText(dzscMaterielHead.getCopEntNo());
		this.tfMasterCustoms
				.setText(dzscMaterielHead.getMasterCustoms() == null ? ""
						: dzscMaterielHead.getMasterCustoms().getName());
		this.tfCode.setText(dzscMaterielHead.getMachCode());
		this.tfName.setText(dzscMaterielHead.getMachName());
		this.tfBuCode.setText(dzscMaterielHead.getTradeCode());
		this.tfBuName.setText(dzscMaterielHead.getTradeName());
		this.cbbInputDate.setDate(dzscMaterielHead.getInputDate());
		this.cbbDeclareDate.setDate(dzscMaterielHead.getDeclareDate());
		this.cbbManageObject
				.setSelectedIndex(ItemProperty
						.getIndexByCode(String.valueOf(dzscMaterielHead
								.getManageObject() == null ? "0"
								: dzscMaterielHead.getManageObject()),
								cbbManageObject));
	}

	private void fillData() {
		dzscMaterielHead.setCopEntNo(this.tfCopEntNo.getText());
		if (this.cbbManageObject.getSelectedItem() != null) {
			ItemProperty item = (ItemProperty) cbbManageObject
					.getSelectedItem();
			dzscMaterielHead.setManageObject(item.getCode());
		}
	}

	/**
	 * 初始化控件
	 * 
	 */
	private void initUIComponents() {
		// 初始化管理对象
		this.cbbManageObject.removeAllItems();
		this.cbbManageObject.addItem(new ItemProperty(ManageObject.MANAGE_COP,
				"经营单位"));
		this.cbbManageObject.addItem(new ItemProperty(
				ManageObject.MANUFACTURE_COP, "加工单位"));
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(
				this.cbbManageObject);
		this.cbbManageObject.setRenderer(CustomBaseRender.getInstance()
				.getRender());
		cbbManageObject.setSelectedIndex(-1);

		this.jTabbedPane.setSelectedIndex(1);
	}

	class UserListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);
			String s = "";
			if (value != null) {
				s = ((ScmCoi) value).getName();
				setIcon(CommonVars.getRcpIconSource().getIcon(
						"images.table.icon"));
			}
			String stype = "【" + getTypeName(((ScmCoi) value).getCoiProperty())
					+ "】";
			setText(stype + s);
			return this;
		}
	}

	private String getTypeName(String typecode) {
		if (typecode.equals(MaterielType.ASSISTANT_MATERIAL)) {
			return "辅料";
		} else if (typecode.equals(MaterielType.BAD_PRODUCT)) {
			return "残次品";
		} else if (typecode.equals(MaterielType.FINISHED_PRODUCT)) {
			return "成品";
		} else if (typecode.equals(MaterielType.MACHINE)) {
			return "设备";
		} else if (typecode.equals(MaterielType.MATERIEL)) {
			return "料件";
		} else if (typecode.equals(MaterielType.REMAIN_MATERIEL)) {
			return "边角料";
		} else if (typecode.equals(MaterielType.SEMI_FINISHED_PRODUCT)) {
			return "半成品";
		} else if (typecode.equals(MaterielType.WASTE_MATERIAL)) {
			return "消耗品";
		}
		return "";
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.getButtonGroup();
		this.setSize(768, 446);
		this.setTitle("物料备案");

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						pnCommonQueryPage.setInitState();
					}
				});
	}

	/**
	 * 
	 * This method initializes jPanel
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jSplitPane
	 * 
	 * 
	 * 
	 * @return javax.swing.JSplitPane
	 * 
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setLeftComponent(getJPanel1());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setResizeWeight(0.0);
			jSplitPane.setDividerLocation(120);
		}
		return jSplitPane;
	}

	/**
	 * 
	 * This method initializes jPanel1
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJPanel4(), java.awt.BorderLayout.NORTH);
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJPanel3(), java.awt.BorderLayout.NORTH);
			jPanel2.add(getJPanel6(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
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
			jPanel3.add(getJToolBar(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * 
	 * This method initializes jToolBar
	 * 
	 * 
	 * 
	 * @return javax.swing.JToolBar
	 * 
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(true);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			// jToolBar.add(getBtnEditUnitWeight());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnDeleteApply());
			jToolBar.add(getBtnDeclare());
			jToolBar.add(getBtnChange());
			jToolBar.add(getBtnEffective());
			jToolBar.add(getRbNotChanged());
			jToolBar.add(getRbChanged());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnOtherFunction());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 
	 * This method initializes jButton
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("新增");
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (rbNotChanged.isSelected()) {
						if (scmCoiSelected == null) {
							JOptionPane.showMessageDialog(FmMaterialApply.this,
									"物料类别为空，不能新增", "确认", 2);
							return;
						}
						// DgMaterialApply dg = new DgMaterialApply();
						// dg.setTableModel(tableModel);
						// dg.setDataState(DataState.ADD);// .setAdd(true);
						// dg.setScmCoi(scmCoiSelected);
						// dg.setVisible(true);
						List list = MaterialApplyQuery.getInstance()
								.findMaterialNotInApply(
										scmCoiSelected.getCode());
						if (list == null || list.size() == 0) {
							return;
						}
						list = materialApplyAction.addMaterialApply(
								new Request(CommonVars.getCurrUser()), list);
						tableModel.addRows(list);
					} else {
						if (scmCoiSelected == null) {
							JOptionPane.showMessageDialog(FmMaterialApply.this,
									"物料类别为空，不能新增", "确认", 2);
							return;
						}
						// DgMaterialApply dg = new DgMaterialApply();
						// dg.setTableModel(tableModel);
						// dg.setDataState(DataState.ADD);// .setAdd(true);
						// dg.setScmCoi(scmCoiSelected);
						// dg.setVisible(true);
						List list = MaterialApplyQuery.getInstance()
								.findMaterialNotInApply(
										scmCoiSelected.getCode());
						if (list == null || list.size() == 0) {
							return;
						}
						list = materialApplyAction.addMaterialChange(
								new Request(CommonVars.getCurrUser()), list);
						tableModel.addRows(list);
					}
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 
	 * This method initializes jButton1
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
			btnEdit.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (tableModel == null
							|| tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择你要修改的资料", "确认", 1);
						return;
					}
					// MaterialApply materialApply = (MaterialApply) tableModel
					// .getCurrentRow();
					// boolean isEdit = true;
					// if (scmCoiSelected.getCoiProperty().equals(
					// MaterielType.MATERIEL)
					// || scmCoiSelected.getCoiProperty().equals(
					// MaterielType.SEMI_FINISHED_PRODUCT)) {
					// isEdit = !manualDecleareAction.findFactoryNameFromImg(
					// new Request(CommonVars.getCurrUser()),
					// materialApply.getMateriel()
					// .getPtNo());
					// }
					// if (scmCoiSelected.getCoiProperty().equals(
					// MaterielType.FINISHED_PRODUCT)
					// || scmCoiSelected.getCoiProperty().equals(
					// MaterielType.SEMI_FINISHED_PRODUCT)) {
					// isEdit = !manualDecleareAction.findFactoryNameFromExg(
					// new Request(CommonVars.getCurrUser()),
					// materialApply.getMateriel()
					// .getPtNo());
					// }
					DgMaterialApply dgMateriel = new DgMaterialApply();
					// dgMateriel.setAdd(false);
					// dgMateriel.setEdit(isEdit);
					if (rbNotChanged.isSelected()) {
						dgMateriel.setDataState(DataState.EDIT);
					} else if (rbChanged.isSelected()) {
						dgMateriel.setDataState(DataState.CHANGE);
					}
					dgMateriel.setTableModel(tableModel);
					dgMateriel.setVisible(true);

				}
			});

		}
		return btnEdit;
	}

	/**
	 * 
	 * This method initializes jButton2
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
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (rbNotChanged.isSelected()) {
						if (tableModel == null
								|| tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(FmMaterialApply.this,
									"请选择你要删除的资料", "确认", 1);
							return;
						}
						// MaterialApply materialApply = (MaterialApply)
						// tableModel
						// .getCurrentRow();
						List list = tableModel.getCurrentRows();
						if (JOptionPane.showConfirmDialog(FmMaterialApply.this,
								"您确定要删除该条记录吗？", "提示信息", 0) == JOptionPane.YES_OPTION) {
							try {
								// materialApplyAction.deleteMaterialApply(
								// new Request(CommonVars.getCurrUser()),
								// materialApply);
								// tableModel.deleteRow(materialApply);
								materialApplyAction.deleteMaterialApply(
										new Request(CommonVars.getCurrUser()),
										list);
								tableModel.deleteRows(list);
							} catch (Exception r) {
								JOptionPane.showMessageDialog(
										FmMaterialApply.this,
										"该物料已被引用，您不可以删除该物料！", "提示信息", 2);
							}

						}
					} else if (rbChanged.isSelected()) {
						if (tableModel == null
								|| tableModel.getCurrentRow() == null) {
							JOptionPane.showMessageDialog(FmMaterialApply.this,
									"请选择你要取消变更的资料", "确认", 1);
							return;
						}
						List list = tableModel.getCurrentRows();
						if (JOptionPane.showConfirmDialog(FmMaterialApply.this,
								"您确定要取消变更该条记录吗？", "提示信息", 0) == JOptionPane.YES_OPTION) {
							// try {
							// materialApplyAction.deleteMaterialChange(
							// new Request(CommonVars.getCurrUser()),
							// materialChange);
							// tableModel.deleteRow(materialChange);
							materialApplyAction
									.deleteMaterialChange(new Request(
											CommonVars.getCurrUser()), list);
							tableModel.deleteRows(list);
							// } catch (Exception r) {
							// JOptionPane.showMessageDialog(
							// FmMaterialApply.this,
							// "该物料已被引用，您不可以删除该物料！", "提示信息", 2);
							// }

						}
					}

				}
			});

		}
		return btnDelete;
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
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
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
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 
	 * This method initializes jPanel4
	 * 
	 * 
	 * 
	 * @return javax.swing.JPanel
	 * 
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			javax.swing.JLabel jLabel = new JLabel();
			jPanel4 = new JPanel();
			jLabel.setText("物料类别");
			jPanel4.add(jLabel, null);
		}
		return jPanel4;
	}

	/**
	 * 
	 * This method initializes jScrollPane1
	 * 
	 * 
	 * 
	 * @return javax.swing.JScrollPane
	 * 
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJList());
		}
		return jScrollPane1;
	}

	/**
	 * 
	 * This method initializes jtRightList
	 * 
	 * 
	 * 
	 * @return javax.swing.JList
	 * 
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList
					.addListSelectionListener(new javax.swing.event.ListSelectionListener() {

						public void valueChanged(
								javax.swing.event.ListSelectionEvent e) {
							if (e.getValueIsAdjusting() == true
									|| !jList.isFocusOwner()) {
								return;
							}
							FmMaterialApply.this.scmCoiSelected = (ScmCoi) (((JList) e
									.getSource()).getSelectedValue());
							pnCommonQueryPage.setInitState();
							setState();

						}
					});

		}
		return jList;
	}

	private JTableListModel initTable(List list) {
		if (list == null) {
			list = new Vector();
		}
		if (rbNotChanged.isSelected()) {
			JTableListModel.dataBind(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("序号", "no", 50, Integer.class));
							list
									.add(addColumn("类别",
											"materiel.scmCoi.name", 80));
							list.add(addColumn("状态标志", "stateMark", 60));
							list.add(addColumn("修改标志", "modifyMark", 60));
							list.add(addColumn("料号", "materiel.ptNo", 100));
							list.add(addColumn("商品编码", "materiel.complex.code",
									80));
							list.add(addColumn("工厂商品名称",
									"materiel.factoryName", 100));
							list.add(addColumn("工厂型号规格",
									"materiel.factorySpec", 100));
							list.add(addColumn("工厂单位", "materiel.calUnit.name",
									50));
							list.add(addColumn("报关单位", "materiel.ptUnit.name",
									50));
							list.add(addColumn("单价", "materiel.ptPrice", 50));
							list
									.add(addColumn("净重",
											"materiel.ptNetWeight", 50));

							list.add(addColumn("是否归并禁用", "isForbidMerge", 100));
							return list;
						}
					});// , ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		} else if (rbChanged.isSelected()) {
			JTableListModel.dataBind(jTable, list,
					new JTableListModelAdapter() {
						public List InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("序号", "no", 50, Integer.class));
							list.add(addColumn("类别", "scmCoi.name", 80));
							list.add(addColumn("状态标志", "stateMark", 60));
							list.add(addColumn("修改标志", "modifyMark", 60));
							list.add(addColumn("料号", "ptNo", 100));
							list.add(addColumn("商品编码", "complex.code", 80));
							list.add(addColumn("工厂商品名称", "factoryName", 100));
							list.add(addColumn("工厂型号规格", "factorySpec", 100));
							list.add(addColumn("工厂单位", "calUnit.name", 50));
							list.add(addColumn("报关单位", "ptUnit.name", 50));
							list.add(addColumn("单价", "ptPrice", 50));
							list.add(addColumn("净重", "ptNetWeight", 50));

							list.add(addColumn("是否归并禁用", "isForbidMerge", 100));
							return list;
						}
					});// , ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		}
		tableModel = (JTableListModel) jTable.getModel();
		TableColumn column = this.jTable.getColumnModel().getColumn(3);
		column.setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				String state = "";
				if (value != null) {
					state = value.toString();
				}
				if (state.equals(DzscState.ORIGINAL)) {
					this.setText("初始状态");
				} else if (state.equals(DzscState.APPLY)) {
					this.setText("申报状态");
				}
				if (state.equals(DzscState.EXECUTE)) {
					this.setText("生效状态");
				}
				if (state.equals(DzscState.CHANGE)) {
					this.setText("变更状态");
				}
				return this;
			}
		});
		this.jTable.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						String state = "";
						if (value != null) {
							state = value.toString();
						}
						if (state.equals(ModifyMarkState.UNCHANGE)) {
							this.setText("未修改");
						} else if (state.equals(ModifyMarkState.ADDED)) {
							this.setText("新增");
						}
						if (state.equals(ModifyMarkState.MODIFIED)) {
							this.setText("已修改");
						}
						if (state.equals(ModifyMarkState.DELETED)) {
							this.setText("已删除");
						}
						return this;
					}
				});
		this.jTable.getColumnModel().getColumn(13).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						boolean state = false;
						if (value != null) {
							state = Boolean.parseBoolean(value.toString()
									.trim());
						}
						if (state) {
							this.setText("是");
						} else {
							this.setText("否");
						}

						return this;
					}
				});
		return tableModel;
	}

	/**
	 * 
	 * This method initializes jButton3
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmMaterialApply.this.doDefaultCloseAction();
				}
			});

		}
		return btnExit;
	}

	/**
	 * This method initializes jPanel6
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			jPanel6 = new JPanel();
			jPanel6.setLayout(new BorderLayout());
			jPanel6.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel6.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jToolBar1
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar1() {
		if (jToolBar1 == null) {
			jToolBar1 = new JToolBar();
			jToolBar1.setFloatable(false);
			jToolBar1.add(getPnCommonQueryPage());
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
		if (scmCoiSelected == null) {
			return new ArrayList();
		}
		String materielType = scmCoiSelected.getCode();
		List list = new ArrayList();
		if (rbNotChanged.isSelected()) {
			list = materialApplyAction.findMaterialApply(new Request(CommonVars
					.getCurrUser()), materielType, index, length, property,
					value, isLike);
		} else if (rbChanged.isSelected()) {
			list = materialApplyAction.findMaterialChange(new Request(
					CommonVars.getCurrUser()), materielType, index, length,
					property, value, isLike);
		}
		return list;
	}

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
					return FmMaterialApply.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmMaterialApply.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeclare() {
		if (btnDeclare == null) {
			btnDeclare = new JButton();
			btnDeclare.setText("海关申报");
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jList.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择物料类别", "确认", 1);
						return;
					}
					new ApplyThread().start();
				}
			});
		}
		return btnDeclare;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnChange() {
		if (btnChange == null) {
			btnChange = new JButton();
			btnChange.setText("变更");
			btnChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null
							|| tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择你要变更的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmMaterialApply.this,
							"您确定要变更该条记录吗？", "提示信息",
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
					MaterialApply materialApply = (MaterialApply) tableModel
							.getCurrentRow();
					materialApplyAction.changeMaterial(new Request(CommonVars
							.getCurrUser()), materialApply);
					// tableModel.deleteRow(materialApply);

					// EnterpriseMaterial materiel = (EnterpriseMaterial)
					// tableModel
					// .getCurrentRow();
					// boolean canEdit = true;
					// if (scmCoiSelected.getCoiProperty().equals(
					// MaterielType.MATERIEL)
					// || scmCoiSelected.getCoiProperty().equals(
					// MaterielType.SEMI_FINISHED_PRODUCT)) {
					// canEdit = !manualDecleareAction.findFactoryNameFromImg(
					// new Request(CommonVars.getCurrUser()), materiel
					// .getPtNo());
					// }
					// if (scmCoiSelected.getCoiProperty().equals(
					// MaterielType.FINISHED_PRODUCT)
					// || scmCoiSelected.getCoiProperty().equals(
					// MaterielType.SEMI_FINISHED_PRODUCT)) {
					// canEdit = !manualDecleareAction.findFactoryNameFromExg(
					// new Request(CommonVars.getCurrUser()), materiel
					// .getPtNo());
					// }
					rbChanged.setSelected(true);
					pnCommonQueryPage.initCbbQueryField();
				}
			});
		}
		return btnChange;
	}

	private void setState() {
		dzscMaterielHead = materialApplyAction
				.findDzscMaterielHead(new Request(CommonVars.getCurrUser()));

		// this.tfCopEntNo.setEditable(DataState.EDIT == dataState);
		this.tfMasterCustoms.setEditable(false);
		this.tfBuCode.setEditable(false);
		this.tfBuName.setEditable(false);
		this.tfBuCode.setEditable(false);
		this.tfName.setEditable(false);
		this.tfCode.setEditable(false);
		this.cbbDeclareDate.setEnabled(false);
		this.cbbInputDate.setEnabled(false);
		this.btnEditHead.setEnabled(dataState != DataState.EDIT);
		this.btnSava.setEnabled(dataState != DataState.BROWSE);
		this.cbbManageObject.setEnabled(dataState != DataState.BROWSE);

		this.btnAdd.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getMaterialState()));
		this.btnDeclare.setEnabled(!DzscState.APPLY.equals(dzscMaterielHead
				.getMaterialState()));

		if (rbNotChanged.isSelected()) {
			// boolean canAdd = true;
			// if (tableModel != null && tableModel.getList().size() > 0) {
			// if (((MaterialApply) tableModel.getList().get(0))
			// .getStateMark().equals(DzscState.APPLY)) {
			// canAdd = false;
			// }
			// }

			if (tableModel == null || tableModel.getCurrentRow() == null) {
				machineStateControl();// 设备时对一些按钮进行控制
				return;
			}
			MaterialApply material = (MaterialApply) tableModel.getCurrentRow();
			String stateMark = material.getStateMark();
			this.btnChange.setEnabled(stateMark.equals(DzscState.EXECUTE));
			// this.btnDeclare.setEnabled(stateMark.equals(DzscState.Application));
			this.btnDelete.setEnabled(stateMark.equals(DzscState.ORIGINAL));
			this.btnDelete.setText("删除");
			this.btnDeleteApply.setVisible(false);
			this.btnEdit.setEnabled(stateMark.equals(DzscState.ORIGINAL));
			// this.btnEditUnitWeight.setEnabled(stateMark
			// .equals(DzscState.Original));
			// this..setEnabled(stateMark.equals(DzscState.Execute));
		} else if (rbChanged.isSelected()) {
			this.btnAdd.setEnabled(true);
			this.btnChange.setEnabled(false);
			if (tableModel == null || tableModel.getCurrentRow() == null) {
				machineStateControl();// 设备时对一些按钮进行控制
				return;
			}
			MaterialChange material = (MaterialChange) tableModel
					.getCurrentRow();
			String stateMark = material.getStateMark();
			this.btnDelete.setEnabled(stateMark.equals(DzscState.CHANGE));
			this.btnDelete.setText("取消变更");
			this.btnDeleteApply.setVisible(true);
			this.btnEdit.setEnabled(stateMark.equals(DzscState.CHANGE));
		}
		machineStateControl();// 设备时对一些按钮进行控制
	}

	/**
	 * 设备时对一些按钮进行控制
	 */
	private void machineStateControl() {
		if (scmCoiSelected != null
				&& scmCoiSelected.getCoiProperty().equals(MaterielType.MACHINE)) {// 设备
			btnDeclare.setEnabled(false);// 海关申报
			btnEffective.setEnabled(false);// 处理回执
			btnChange.setEnabled(false);// 变更
			rbNotChanged.setEnabled(false);// 未变更
			rbChanged.setEnabled(false);// 变更中
			// btnDelete.setEnabled(true);//删除
			// btnEdit.setEnabled(true);//修改
		} else {
			rbNotChanged.setEnabled(true);// 未变更
			rbChanged.setEnabled(true);// 变更中
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffective() {
		if (btnEffective == null) {
			btnEffective = new JButton();
			btnEffective.setText("处理回执");
			btnEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List lsReturnFile=DzscCommon.getInstance().showDzscReceiptFile(
							DzscBusinessType.MATERIAL,
							dzscMaterielHead.getCopEntNo());
					if (lsReturnFile.size()<=0) {
						return;
					}
					rbNotChanged.setSelected(true);
					try {
						String result = materialApplyAction
								.processMaterialApplyResult(new Request(
										CommonVars.getCurrUser()),lsReturnFile);
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"处理回执成功\n" + result, "确认", 1);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"处理回执失败" + ex.getMessage(), "确认", 1);
					}
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnEffective;
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbNotChanged() {
		if (rbNotChanged == null) {
			rbNotChanged = new JRadioButton();
			rbNotChanged.setText("未变更");
			rbNotChanged.setSelected(true);
			rbNotChanged.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// FmMaterialApply.this.scmCoiSelected = (ScmCoi) (jList
					// .getSelectedValue());
					if (scmCoiSelected == null) {// jList.getSelectedValue()
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择物料类别", "确认", 1);
						return;
					}
					pnCommonQueryPage.setInitState();
					pnCommonQueryPage.initCbbQueryField();
					setState();
					// Auto-generated
					// Event stub
					// itemStateChanged()
				}
			});
		}
		return rbNotChanged;
	}

	/**
	 * This method initializes jRadioButton1
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbChanged() {
		if (rbChanged == null) {
			rbChanged = new JRadioButton();
			rbChanged.setText("变更中");
			rbChanged.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					// FmMaterialApply.this.scmCoiSelected = (ScmCoi) (jList
					// .getSelectedValue());
					if (scmCoiSelected == null) {// jList.getSelectedValue()
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择物料类别", "确认", 1);
						return;
					}
					pnCommonQueryPage.setInitState();
					pnCommonQueryPage.initCbbQueryField();
					setState();
					// Auto-generated
					// Event stub
					// itemStateChanged()
				}
			});
		}
		return rbChanged;
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
			buttonGroup.add(this.rbChanged);
			buttonGroup.add(this.rbNotChanged);
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("查看");
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null
							|| tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择你要查看的资料", "确认", 1);
						return;
					}
					DgMaterialApply dgMateriel = new DgMaterialApply();
					dgMateriel.setDataState(DataState.BROWSE);
					dgMateriel.setTableModel(tableModel);
					dgMateriel.setVisible(true);
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("企业信息", null, getJPanel5(), null);
			jTabbedPane.addTab("成品/料件信息", null, getJSplitPane(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							showData();
						}
					});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(54, 231, 98, 18));
			jLabel9.setText("管理对象");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(352, 195, 82, 22));

			jLabel8.setText("申请日期");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(352, 91, 82, 22));
			jLabel7.setText("主管海关");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(352, 160, 82, 22));
			jLabel6.setText("加工单位名称");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(53, 195, 98, 22));
			jLabel5.setText("录入日期");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(352, 127, 82, 22));
			jLabel4.setText("经营单位名称");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(53, 160, 98, 22));
			jLabel3.setText("加工单位编码");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(53, 127, 98, 22));
			jLabel2.setText("经营单位编码");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(53, 91, 98, 22));
			jLabel1.setText("企业内部编号");
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.add(jLabel1, null);
			jPanel5.add(getTfCopEntNo(), null);
			jPanel5.add(jLabel2, null);
			jPanel5.add(jLabel3, null);
			jPanel5.add(jLabel4, null);
			jPanel5.add(jLabel5, null);
			jPanel5.add(jLabel6, null);
			jPanel5.add(jLabel7, null);
			jPanel5.add(jLabel8, null);
			jPanel5.add(getTfMasterCustoms(), null);
			jPanel5.add(getTfCode(), null);
			jPanel5.add(getTfName(), null);
			jPanel5.add(getTfBuCode(), null);
			jPanel5.add(getTfBuName(), null);
			jPanel5.add(getCbbInputDate(), null);
			jPanel5.add(getCbbDeclareDate(), null);
			jPanel5.add(getBtnEditHead(), null);
			jPanel5.add(getBtnSava(), null);
			jPanel5.add(jLabel9, null);
			jPanel5.add(getCbbManageObject(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCopEntNo() {
		if (tfCopEntNo == null) {
			tfCopEntNo = new JTextField();
			tfCopEntNo.setBounds(new java.awt.Rectangle(155, 91, 153, 22));
			tfCopEntNo.setEditable(false);
		}
		return tfCopEntNo;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMasterCustoms() {
		if (tfMasterCustoms == null) {
			tfMasterCustoms = new JTextField();
			tfMasterCustoms.setBounds(new java.awt.Rectangle(437, 91, 162, 22));
			tfMasterCustoms.setEditable(false);
		}
		return tfMasterCustoms;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfCode() {
		if (tfCode == null) {
			tfCode = new JTextField();
			tfCode.setBounds(new java.awt.Rectangle(155, 160, 153, 22));

		}
		return tfCode;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfName() {
		if (tfName == null) {
			tfName = new JTextField();

			tfName.setBounds(new java.awt.Rectangle(437, 160, 162, 22));

		}
		return tfName;
	}

	/**
	 * This method initializes jTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBuCode() {
		if (tfBuCode == null) {
			tfBuCode = new JTextField();
			tfBuCode.setBounds(new java.awt.Rectangle(155, 127, 153, 22));
		}
		return tfBuCode;
	}

	/**
	 * This method initializes jTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBuName() {
		if (tfBuName == null) {
			tfBuName = new JTextField();
			tfBuName.setBounds(new java.awt.Rectangle(437, 127, 162, 22));
		}
		return tfBuName;
	}

	/**
	 * This method initializes jCalendarComboBox
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbInputDate() {
		if (cbbInputDate == null) {
			cbbInputDate = new JCalendarComboBox();
			cbbInputDate.setBounds(new java.awt.Rectangle(155, 195, 153, 22));
		}
		return cbbInputDate;
	}

	/**
	 * This method initializes jCalendarComboBox1
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbDeclareDate() {
		if (cbbDeclareDate == null) {
			cbbDeclareDate = new JCalendarComboBox();
			cbbDeclareDate.setBounds(new java.awt.Rectangle(437, 195, 162, 22));
		}
		return cbbDeclareDate;
	}

	/**
	 * This method initializes btnEdit1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditHead() {
		if (btnEditHead == null) {
			btnEditHead = new JButton();
			btnEditHead.setBounds(new java.awt.Rectangle(178, 313, 71, 25));
			btnEditHead.setText("修改");
			btnEditHead.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dataState = DataState.EDIT;
					setState();
				}
			});
		}
		return btnEditHead;
	}

	/**
	 * This method initializes btnSava
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSava() {
		if (btnSava == null) {
			btnSava = new JButton();
			btnSava.setBounds(new java.awt.Rectangle(375, 313, 71, 25));
			btnSava.setText("保存");
			btnSava.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbManageObject.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"请选择管理对象", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					fillData();
					materialApplyAction.saveDzscMaterielHead(new Request(
							CommonVars.getCurrUser()), dzscMaterielHead);
					dataState = DataState.BROWSE;
					setState();
				}
			});

		}
		return btnSava;
	}

	// @jve:decl-index=0:visual-constraint="10,10"

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDeleteApply() {
		if (btnDeleteApply == null) {
			btnDeleteApply = new JButton();
			btnDeleteApply.setText("删除备案");
			btnDeleteApply
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRows().size() <= 0) {
								JOptionPane.showMessageDialog(
										FmMaterialApply.this, "请选择你要删除的资料",
										"确认", 1);
								return;
							}
							List list = tableModel.getCurrentRows();
							if (JOptionPane.showConfirmDialog(
									FmMaterialApply.this, "您确定要删除此备案记录吗？",
									"提示信息", 0) == JOptionPane.YES_OPTION) {
								try {
									list = materialApplyAction
											.markDeleteMaterialChange(
													new Request(CommonVars
															.getCurrUser()),
													list);
									tableModel.updateRows(list);
								} catch (Exception r) {
									JOptionPane.showMessageDialog(
											FmMaterialApply.this,
											"该物料已被引用，您不可以删除该物料！", "提示信息", 2);
								}

							}
						}
					});
		}
		return btnDeleteApply;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbManageObject() {
		if (cbbManageObject == null) {
			cbbManageObject = new JComboBox();
			cbbManageObject
					.setBounds(new java.awt.Rectangle(155, 228, 153, 22));
		}
		return cbbManageObject;
	}

	class ApplyThread extends Thread {
		public void run() {
			try {
				DgMaterialApplySelect dg = new DgMaterialApplySelect();
				dg.setVisible(true);
				if (!dg.isOk()) {
					return;
				}
				TempMaterialApplySelectParam param = dg.getParam();
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				// String scmCoiCode = ((ScmCoi) jList.getSelectedValue())
				// .getCode();
				try {
					DeclareFileInfo fileInfo = materialApplyAction
							.applyMaterial(request, param);
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmMaterialApply.this,
							fileInfo.getFileInfoSpec(), "提示", 1);
				} catch (Exception ex) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmMaterialApply.this,
							"系统申报失败 " + ex.getMessage(), "确认", 1);
				}
				pnCommonQueryPage.setInitState();
				setState();
			} catch (Exception ex) {
				System.out.println(ex.getStackTrace() + "\n-->"
						+ ex.getMessage());
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jPopupMenu
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmOtherFunction() {
		if (pmOtherFunction == null) {
			pmOtherFunction = new JPopupMenu();
			pmOtherFunction.add(getMiShowForbid());
			pmOtherFunction.add(getMiCancelForbid());
			pmOtherFunction.add(getMiShowAll());
			pmOtherFunction.add(getJMenuItem());
		}
		return pmOtherFunction;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowForbid() {
		if (miShowForbid == null) {
			miShowForbid = new JMenuItem();
			miShowForbid.setText("显示被内部归并禁用的物料");
			miShowForbid.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String materielType = scmCoiSelected.getCode();
					List list = materialApplyAction
							.findForbidMergeMaterialApply(new Request(
									CommonVars.getCurrUser()), materielType);
					initTable(list);
				}
			});
		}
		return miShowForbid;
	}

	/**
	 * This method initializes jMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCancelForbid() {
		if (miCancelForbid == null) {
			miCancelForbid = new JMenuItem();
			miCancelForbid.setText("取消物料的内部归并禁用");
			miCancelForbid
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							List list = tableModel.getCurrentRows();
							if (list.size() <= 0) {
								JOptionPane.showMessageDialog(
										FmMaterialApply.this, "请选择要取消禁用的物料",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							list = materialApplyAction
									.cancelForbidMergeMaterialApply(
											new Request(CommonVars
													.getCurrUser()), list);
							tableModel.updateRows(list);
						}
					});
		}
		return miCancelForbid;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOtherFunction() {
		if (btnOtherFunction == null) {
			btnOtherFunction = new JButton();
			btnOtherFunction.setText("其他");
			btnOtherFunction
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							getPmOtherFunction().show(btnOtherFunction, 0,
									btnOtherFunction.getHeight());
						}
					});
		}
		return btnOtherFunction;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiShowAll() {
		if (miShowAll == null) {
			miShowAll = new JMenuItem();
			miShowAll.setText("显示所有");
			miShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return miShowAll;
	}

	/**
	 * This method initializes jMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("显示异常资料");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = materialApplyAction
							.findExceptionMaterialApply(new Request(CommonVars
									.getCurrUser(), true));
					if (list.size() > 0) {
						DgExceptionMaterialApply dg = new DgExceptionMaterialApply();
						dg.setDataSource(list);
						dg.setVisible(true);
						if (dg.getDataSource().size() <= 0) {
							showData();
						}
					} else {
						JOptionPane.showMessageDialog(FmMaterialApply.this,
								"没有异常资料");
					}
				}
			});
		}
		return jMenuItem;
	}
}
