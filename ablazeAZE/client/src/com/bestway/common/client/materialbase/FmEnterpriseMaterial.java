/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author Administrator 工厂物流主档操作
 */
public class FmEnterpriseMaterial extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar = null;

	/**
	 * 新增物流主档
	 */
	private JButton btnAdd = null;

	/**
	 * 修改物流主档
	 */
	private JButton btnEdit = null;

	/**
	 * 删除物流主档
	 */
	private JButton btnDelete = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 料件管理操作---远程方法调用
	 */
	private MaterialManageAction materialManageAction = null;

	private ManualDeclareAction manualDecleareAction = null;// TODO to 解释

	private CommonBaseCodeAction commonBaseCodeAction = null;// TODO to 解释

	private JPanel jPanel4 = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 存放物流通用代码－－物料类别资料
	 */
	private ScmCoi scmCoiSelected = null;

	/**
	 * 物料类别列表
	 */
	private JList jList = null;

	private JTableListModel tableModel = null;

	/**
	 * 关闭退出按钮
	 */
	private JButton btnExit = null;

	/**
	 * 修改单重按钮
	 */
	private JButton btnEditUnitWeight = null;

	private JPanel jPanel6 = null;

	private JToolBar jToolBar1 = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 申报按钮
	 */
	private JButton btnDeclare = null;

	/**
	 * 变更按钮
	 */
	private JButton btnChange = null;

	/**
	 * 生效按钮
	 */
	private JButton btnEffective = null;

	/**
	 * 刷新按钮
	 */
	private JButton btnRefresh = null;

	private JButton btnInput = null;

	/**
	 * This is the default constructor 默认构造方法
	 */
	public FmEnterpriseMaterial() {
		super();
		initialize();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");

		materialManageAction.checkEnterpriseMaterielByBrowser(new Request(
				CommonVars.getCurrUser()));

		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		initUIComponents();
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
	@Override
	public void setVisible(boolean b) {
		if (b) {

		}
		super.setVisible(b);
	}

	/**
	 * 初始化控件
	 * 
	 */
	private void initUIComponents() {
		Vector<Object> vector = new Vector<Object>();
		List cois = this.materialManageAction.findScmCois(new Request(
				CommonVars.getCurrUser(), true));
		for (int i = 0; i < cois.size(); i++) {
			ScmCoi coi = (ScmCoi) cois.get(i);
			vector.add(coi);
		}
		this.jList.setListData(vector);
		this.jList.setCellRenderer(new UserListCellRenderer());
		if (this.jList.getModel().getSize() > 0) {
			if (scmCoiSelected != null) {
				this.jList.setSelectedValue(scmCoiSelected, true);
			} else {
				this.jList.setSelectedIndex(0);
				scmCoiSelected = ((ScmCoi) this.jList.getModel()
						.getElementAt(0));
			}
		}
	}

	/**
	 * 
	 * @author Administrator 料件类别单元渲染器
	 */
	class UserListCellRenderer extends DefaultListCellRenderer {
		@Override
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

	/**
	 * 把料件的类型常量转换成中文显示字符串
	 * 
	 * @param typecode
	 * @return
	 */
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
	 * This method initializes this 界面初始化
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setSize(768, 366);
		this.setTitle("工厂物料主档");
		this.setHelpId("enterpriseMaterial");

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					@Override
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
			jPanel.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jSplitPane.setDividerLocation(150);
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(true);
			jToolBar.add(getBtnInput());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnEditUnitWeight());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRefresh());
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
			btnAdd.setPreferredSize(new Dimension(55, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (scmCoiSelected == null) {
						JOptionPane.showMessageDialog(
								FmEnterpriseMaterial.this, "物料类别为空，不能新增", "确认",
								2);
						return;
					}

					materialManageAction
							.checkEnterpriseMaterielByAdd(new Request(
									CommonVars.getCurrUser()));

					DgEnterpriseMaterial dg = new DgEnterpriseMaterial();
					dg.setTableModel(tableModel);
					dg.setDataState(DataState.ADD);// .setAdd(true);
					dg.setScmCoi(scmCoiSelected);
					dg.setVisible(true);
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
			btnEdit.setPreferredSize(new Dimension(55, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null
							|| tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmEnterpriseMaterial.this, "请选择你要修改的资料", "确认",
								1);
						return;
					}
					materialManageAction
							.checkEnterpriseMaterielByUpdate(new Request(
									CommonVars.getCurrUser()));

					EnterpriseMaterial materiel = (EnterpriseMaterial) tableModel
							.getCurrentRow();
					boolean isEdit = true;
					if (scmCoiSelected.getCoiProperty().equals(
							MaterielType.MATERIEL)
							|| scmCoiSelected.getCoiProperty().equals(
									MaterielType.SEMI_FINISHED_PRODUCT)) {
						isEdit = !manualDecleareAction.findFactoryNameFromImg(
								new Request(CommonVars.getCurrUser(), true),
								materiel.getPtNo());
					}
					if (scmCoiSelected.getCoiProperty().equals(
							MaterielType.FINISHED_PRODUCT)
							|| scmCoiSelected.getCoiProperty().equals(
									MaterielType.SEMI_FINISHED_PRODUCT)) {
						isEdit = !manualDecleareAction.findFactoryNameFromExg(
								new Request(CommonVars.getCurrUser(), true),
								materiel.getPtNo());
					}
					DgEnterpriseMaterial dgMateriel = new DgEnterpriseMaterial();
					dgMateriel.setDataState(DataState.EDIT);
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
			btnDelete.setPreferredSize(new Dimension(55, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel == null
							|| tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmEnterpriseMaterial.this, "请选择你要删除的资料", "确认",
								1);
						return;
					}
					materialManageAction
							.checkEnterpriseMaterielByDelete(new Request(
									CommonVars.getCurrUser()));
					List list = tableModel.getCurrentRows();
					if (JOptionPane.showConfirmDialog(
							FmEnterpriseMaterial.this, "您确定要删除该条记录吗？", "提示信息",
							0) == JOptionPane.YES_OPTION) {
						List ptNoList = materialManageAction
								.findPtNo(new Request(CommonVars.getCurrUser(),
										true));
						String ptno = "";
						for (int i = 0; i < list.size(); i++) {
							EnterpriseMaterial materiel = (EnterpriseMaterial) list
									.get(i);
							String tmp = materiel.getPtNo();
							if (!ptNoList.contains(tmp)) {
								try {
									long beginTime = System.currentTimeMillis();

									materialManageAction
											.deleteEnterpriseMaterial(
													new Request(CommonVars
															.getCurrUser(),
															true), materiel);
									long endTime = System.currentTimeMillis();
									tableModel.deleteRow(materiel);
								} catch (Exception r) {
									continue;
								}
							} else {
								ptno += tmp + ";";
							}
						}
						if (!ptno.equals("")) {
							JOptionPane.showMessageDialog(
									FmEnterpriseMaterial.this, "料号为：" + ptno
											+ "在报关常用物料中有引用,不能删除！", "提示", 1);
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
			jTable.setRowHeight(25);
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
							FmEnterpriseMaterial.this.scmCoiSelected = (ScmCoi) (((JList) e
									.getSource()).getSelectedValue());
							pnCommonQueryPage.setInitState();
						}
					});

		}
		return jList;
	}

	/**
	 * 表格初始化
	 * 
	 * @param list
	 * @return
	 */
	private JTableListModel initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("类别", "scmCoi.name", 80));
						list.add(addColumn("料号", "ptNo", 100));
						list.add(addColumn("商品编码", "complex.code", 80));
						list.add(addColumn("工厂商品名称", "factoryName", 100));
						list.add(addColumn("工厂型号规格", "factorySpec", 100));
						list.add(addColumn("工厂单位", "calUnit.name", 50));
						list.add(addColumn("报关单位", "ptUnit.name", 50));
						list.add(addColumn("单价", "ptPrice", 50));
						list.add(addColumn("单位折算系数", "unitConvert", 80));
						list.add(addColumn("净重", "ptNetWeight", 50));
						list.add(addColumn("材料来源", "materialSource", 100));
						list.add(addColumn("报关助记码", "customsNo", 80));
						list.add(addColumn("英文品名","ptEnglishName",80));
						list.add(addColumn("英文规格","ptEnglishSpec",80));
						list.add(addColumn("创建日期", "createDate", 80));
						return list;
					}
				});
		setColor(jTable);
		return tableModel;

	}

	/**
	 * render table color row
	 */
	class ColorTableCellRenderer extends DefaultTableCellRenderer {

		public ColorTableCellRenderer() {
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			boolean isChange = false;
			if (checkValue(table, row, column)) {
//				c.setBackground(new Color(153, 204, 153));
//				c.setForeground(table.getForeground());
				c.setForeground(new Color(0, 0, 204));
				c.setBackground(table.getBackground());
				isChange = true;
			}
			if (isSelected) {
				c.setForeground(table.getSelectionForeground());
				c.setBackground(table.getSelectionBackground());
			}
			if (isChange == false && !isSelected) {
				c.setForeground(table.getForeground());
				c.setBackground(table.getBackground());
			}
			return c;
		}
	}

	private void setColor(JTable jTable) {
		JTableListModel tableModel = (JTableListModel) jTable.getModel();
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(
					new ColorTableCellRenderer());
		}
	}

	private boolean checkValue(JTable table, int row, int column) {
		JTableListModel tableModel = (JTableListModel) table.getModel();
		EnterpriseMaterial data = (EnterpriseMaterial) tableModel
				.getDataByRow(row);
		if (data == null) {
			return false;
		}
		if (data.getCreateDate() == null ) {
			return false;
		}
		Date currentDate = new Date();
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		if (formate.format(data.getCreateDate()).equals(
				formate.format(currentDate))) {
			return true;
		}
		return false;
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
			btnExit.setText("退出");
			btnExit.setPreferredSize(new Dimension(55, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					FmEnterpriseMaterial.this.doDefaultCloseAction();
				}
			});

		}
		return btnExit;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditUnitWeight() {
		if (btnEditUnitWeight == null) {
			btnEditUnitWeight = new JButton();
			btnEditUnitWeight.setText("修改单重");
			btnEditUnitWeight.setVisible(false);
			btnEditUnitWeight.setPreferredSize(new Dimension(70, 30));
			btnEditUnitWeight
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel == null
									|| tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmEnterpriseMaterial.this,
										"请选择你要修改的单重记录", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgEpEditUnitWeight dg = new DgEpEditUnitWeight();
							dg.setTableModel(tableModel);
							dg.setVisible(true);
						}
					});
		}
		return btnEditUnitWeight;
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
		long beginTime = System.currentTimeMillis();
		List list = materialManageAction.findEnterpriseMaterial(new Request(
				CommonVars.getCurrUser()), materielType, index, length,
				property, value, isLike);
		long endTime = System.currentTimeMillis();

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
					return FmEnterpriseMaterial.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmEnterpriseMaterial.this.getDataSource(index,
							length, property, value, isLike);
				}

				@Override
				public Long getDataSourceCount(int index, int length,
						String property, Object value, boolean isLike) {
					if (scmCoiSelected == null) {
						return 0L;
					}
					String materielType = scmCoiSelected.getCode();
					return materialManageAction.findEnterpriseMaterialCount(
							new Request(CommonVars.getCurrUser()),
							materielType, index, length, property, value,
							isLike);
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
			btnDeclare.setText("申报");
			btnDeclare.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (jList.getSelectedValue() == null) {
					// JOptionPane.showMessageDialog(FmCustomMaterial.this,
					// "请选择物料类别", "确认", 1);
					// return;
					// }
					// String scmCoiCode = ((ScmCoi) jList.getSelectedValue())
					// .getCode();
					// materialManageAction.applyCustomMaterial(new Request(
					// CommonVars.getCurrUser()), scmCoiCode);
					// pnCommonQueryPage.setInitState();
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
						JOptionPane.showMessageDialog(
								FmEnterpriseMaterial.this, "请选择你要变更的资料", "确认",
								1);
						return;
					}
					EnterpriseMaterial materiel = (EnterpriseMaterial) tableModel
							.getCurrentRow();
					boolean canEdit = true;
					if (scmCoiSelected.getCoiProperty().equals(
							MaterielType.MATERIEL)
							|| scmCoiSelected.getCoiProperty().equals(
									MaterielType.SEMI_FINISHED_PRODUCT)) {
						canEdit = !manualDecleareAction.findFactoryNameFromImg(
								new Request(CommonVars.getCurrUser()), materiel
										.getPtNo());
					}
					if (scmCoiSelected.getCoiProperty().equals(
							MaterielType.FINISHED_PRODUCT)
							|| scmCoiSelected.getCoiProperty().equals(
									MaterielType.SEMI_FINISHED_PRODUCT)) {
						canEdit = !manualDecleareAction.findFactoryNameFromExg(
								new Request(CommonVars.getCurrUser()), materiel
										.getPtNo());
					}
					DgEnterpriseMaterial dgMateriel = new DgEnterpriseMaterial();
					// dgMateriel.setAdd(false);
					// dgMateriel.setEdit(isEdit);
					dgMateriel.setDataState(DataState.CHANGE);
					dgMateriel.setTableModel(tableModel);
					dgMateriel.setVisible(true);
				}
			});
		}
		return btnChange;
	}

	private void setState() {
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			return;
		}
		EnterpriseMaterial material = (EnterpriseMaterial) tableModel
				.getCurrentRow();
		// String stateMark = material.getStateMark();
		// this.btnChange.setEnabled(stateMark.equals(DzscState.EXECUTE));
		// //
		// this.btnDeclare.setEnabled(stateMark.equals(DzscState.Application));
		// this.btnDelete.setEnabled(stateMark.equals(DzscState.ORIGINAL));
		// this.btnEdit.setEnabled(stateMark.equals(DzscState.ORIGINAL));
		// this.btnEditUnitWeight.setEnabled(stateMark.equals(DzscState.ORIGINAL));
		// this..setEnabled(stateMark.equals(DzscState.Execute));
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEffective() {
		if (btnEffective == null) {
			btnEffective = new JButton();
			btnEffective.setText("生效");
			btnEffective.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (jList.getSelectedValue() == null) {
					// JOptionPane.showMessageDialog(FmCustomMaterial.this,
					// "请选择物料类别", "确认", 1);
					// return;
					// }
					// String scmCoiCode = ((ScmCoi) jList.getSelectedValue())
					// .getCode();
					// materialManageAction.effectiveCustomMaterial(new Request(
					// CommonVars.getCurrUser()), scmCoiCode);
					// pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnEffective;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setText("刷新");
			btnRefresh.setPreferredSize(new Dimension(55, 30));
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// EnterpriseMaterial material = (EnterpriseMaterial)
					// tableModel
					// .getCurrentRow();
					// initUIComponents();
					pnCommonQueryPage.setInitState();
					// if (material != null) {
					// tableModel.updateRow(material);
					// }
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * This method initializes btnInput
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInput() {
		if (btnInput == null) {
			btnInput = new JButton();
			btnInput.setText("文本导入");
			btnInput.setPreferredSize(new Dimension(70, 30));
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEnterpriseTxtImport dg = new DgEnterpriseTxtImport();
					dg.setVisible(true);
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnInput;
	}

}
