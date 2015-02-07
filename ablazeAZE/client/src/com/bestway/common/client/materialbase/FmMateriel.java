/*
 * Created on 2004-6-29
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.materialbase;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

import org.springframework.dao.DataAccessException;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Dimension;

/**
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates --------------------check by
 * chensir 08.9.27 报关常用工厂物料
 */
public class FmMateriel extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel3 = null;

	private JToolBar jToolBar = null;

	/**
	 * 新增企业原始物料资料
	 */
	private JButton btnAdd = null;

	/**
	 * 修改报关常用工厂物料
	 */
	private JButton btnEdit = null;

	/**
	 * 删除表格选中的行
	 */
	private JButton btnDelete = null;

	/**
	 * 表格
	 */
	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 普通基础代码操作接口
	 */
	private CommonBaseCodeAction commonBaseCodeAction = null;

	private ManualDeclareAction manualDecleareAction = null;

	/**
	 * 料件管理操作接口
	 */
	private MaterialManageAction materialManageAction = null;

	private JPanel jPanel4 = null;

	private JScrollPane jScrollPane1 = null;

	/**
	 * 存放物流通用代码－－物料类别资料
	 */
	private ScmCoi scmCoiSelected = null;

	private JList jList = null;

	/**
	 * 表格模型
	 */
	private JTableListModel tableModel = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;

	/**
	 * 修改单重
	 */
	private JButton btnEditUnitWeight = null;

	private JPanel jPanel6 = null;

	private JToolBar jToolBar1 = null;

	/**
	 * 查询操作页面
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 删除重复物料
	 */
	private JButton btnDeleteMet = null;

	/**
	 * 刷新
	 */
	private JButton btnRefresh = null;

	/**
	 * This is the default constructor 默认构造方法
	 */
	public FmMateriel() {
		super();
		// 初始化
		initialize();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		manualDecleareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initUIComponents();

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
				CommonVars.getCurrUser()));
		for (int i = 0; i < cois.size(); i++) {
			ScmCoi coi = (ScmCoi) cois.get(i);
			vector.add(coi);
		}
		this.jList.setListData(vector);
		this.jList.setCellRenderer(new UserListCellRenderer());
		if (this.jList.getModel().getSize() > 0) {
			if (scmCoiSelected != null) {
				this.jList.setSelectedValue(scmCoiSelected, true);
				System.out.println("--------select" + scmCoiSelected.getName());
			} else {
				this.jList.setSelectedIndex(0);
				scmCoiSelected = ((ScmCoi) this.jList.getModel()
						.getElementAt(0));
			}
		}
		// System.out.println("--------"+scmCoiSelected.getName());
	}

	/**
	 * 特定表格单元格渲染器
	 * 
	 * @author Administrator
	 * 
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
	 * 返回类型的名字
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
	 * This method initializes this 初始化
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setSize(768, 366);
		this.setTitle("报关常用工厂物料");
		this.setHelpId("materiel");

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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.setFloatable(true);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnEditUnitWeight());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnDeleteMet());
			jToolBar.add(getBtnRefresh());
			jToolBar.add(getBtnExit());
		}
		return jToolBar;
	}

	/**
	 * 新增企业原始物料资料
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
						JOptionPane.showMessageDialog(FmMateriel.this,
								"物料类别为空，不能新增", "确认", 2);
						return;
					}
					materialManageAction.findMaterielforControlAdd(new Request(
							CommonVars.getCurrUser()));
					List list = MaterialQuery.getInstance()
							.findEnterpriseMaterialForMaterial(
									scmCoiSelected.getCode());
					if (list == null || list.size() == 0) {
						return;
					}

					long beginTime=System.currentTimeMillis();
					list = materialManageAction.addCustomMaterial(new Request(
							CommonVars.getCurrUser(), true), list);
					long endTime=System.currentTimeMillis();
					System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
					tableModel.addRows(list);
				}
			});

		}
		return btnAdd;
	}

	/**
	 * 修改报关常用工厂物料
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

					edit();

				}
			});

		}
		return btnEdit;
	}

	/**
	 * 报关常用物料的修改
	 */
	private void edit() {
		if (tableModel == null || tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(FmMateriel.this, "请选择你要修改的资料", "确认",
					1);
			return;
		}
		Materiel materiel = (Materiel) tableModel.getCurrentRow();
		boolean isEdit = true;
		if (scmCoiSelected.getCoiProperty().equals(MaterielType.MATERIEL)
				|| scmCoiSelected.getCoiProperty().equals(
						MaterielType.SEMI_FINISHED_PRODUCT)) {
			isEdit = !manualDecleareAction.findFactoryNameFromImg(new Request(
					CommonVars.getCurrUser(), true), materiel.getPtNo());
		}
		if (scmCoiSelected.getCoiProperty().equals(
				MaterielType.FINISHED_PRODUCT)
				|| scmCoiSelected.getCoiProperty().equals(
						MaterielType.SEMI_FINISHED_PRODUCT)) {
			isEdit = !manualDecleareAction.findFactoryNameFromExg(new Request(
					CommonVars.getCurrUser(), true), materiel.getPtNo());
		}
		materialManageAction.findMaterielforControlEdit(new Request(CommonVars
				.getCurrUser()));
		DgMateriel dgMateriel = new DgMateriel();
		dgMateriel.setAdd(false);
		dgMateriel.setEdit(isEdit);
		dgMateriel.setTableModel(tableModel);
		dgMateriel.setVisible(true);
	}

	/**
	 * 删除表格行
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
				StringBuffer bf = null;

				private void showMessage() {
					if (!bf.equals("料号为:" + '\n')) {
						if (!bf.substring(bf.length() - 1).equals("\n")) {
							bf.append("\n");
						}
						bf.append("的物料被其它模块所使用,不能够删除!");
						JOptionPane.showMessageDialog(FmMateriel.this, bf,
								"提示!", JOptionPane.WARNING_MESSAGE);
					}
				}

				public void actionPerformed(java.awt.event.ActionEvent e) {
					int k = 0;
					bf = new StringBuffer("料号为:");
					materialManageAction.findMaterielforControlDel(new Request(
							CommonVars.getCurrUser()));
					if (tableModel == null
							|| tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmMateriel.this,
								"请选择你要删除的资料", "确认", 1);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmMateriel.this,
							"您确定要删除该条记录吗？", "提示信息", 0) == JOptionPane.YES_OPTION) {
						List list = tableModel.getCurrentRows();
						for (int i = 0; i < list.size(); i++) {
							Materiel materiel = (Materiel) list.get(i);
							try {
								long beginTime=System.currentTimeMillis();
								String materielUsingCondition = materialManageAction.checkMaterielUsingCondition(
										new Request(CommonVars.getCurrUser(),
												true), materiel);
								if(null!=materielUsingCondition&&!materielUsingCondition.isEmpty()){
									JOptionPane.showMessageDialog(FmMateriel.this,
											materielUsingCondition+"使用到了料号："+materiel.getPtNo()+",请先删除引用", "确认", 1);
									return;
								}
								materialManageAction.deleteMateriel(
										new Request(CommonVars.getCurrUser(),
												true), materiel);
								long endTime=System.currentTimeMillis();
								System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
								
								tableModel.deleteRow(materiel);
							} catch (DataAccessException r) {
								bf.append(materiel.getPtNo() + ",");
								k++;
								if (k % 7 == 0) {
									bf.append('\n');
								}
								continue;
							} catch (Exception rx) {
								System.out.println(rx);
								System.out.println(rx.getMessage());
								continue;
							}
						}
						if (k > 0) {
							showMessage();
						}
					}
					System.gc();
				}
			});

		}
		return btnDelete;
	}

	/**
	 * 返回表格
	 * 
	 * @return javax.swing.JTable
	 * 
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.setRowHeight(25);
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						edit();
					}
				}
			});
			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				@Override
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (tableModel == null)
						return;
					if (tableModel.getCurrentRow() == null)
						return;
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						Materiel materiel = (Materiel) tableModel
								.getCurrentRow();
						if (materiel.getIsNewMateriel() != null
								&& materiel.getIsNewMateriel().equals(
										new Boolean(true))) {
							materiel.setIsNewMateriel(new Boolean(false));
						} else {
							materiel.setIsNewMateriel(new Boolean(true));
						}
						materialManageAction.saveMateriel(new Request(
								CommonVars.getCurrUser()), materiel);
						tableModel.updateRow(materiel);
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
							if (e.getValueIsAdjusting()
									|| !jList.isFocusOwner()) {
								return;
							}
							scmCoiSelected = (ScmCoi) (((JList) e.getSource())
									.getSelectedValue());
							pnCommonQueryPage.setInitState();
							System.out
									.println("---- pnCommonQueryPage.setInitState(); ------");
						}
					});

		}
		return jList;
	}

	/**
	 * 初始化表格
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
						list.add(addColumn("工厂单位", "calUnit.name", 70));
						list.add(addColumn("报关单位", "ptUnit.name", 70));
						list.add(addColumn("单价", "ptPrice", 80));
						list.add(addColumn("单位折算系数", "unitConvert", 80));
						list.add(addColumn("净重", "ptNetWeight", 80));
						list.add(addColumn("版本号", "ptVersion", 70));
						list.add(addColumn("英文品名", "ptEnglishName", 80));
						list.add(addColumn("英文规格", "ptEnglishSpec", 80));
						list.add(addColumn("创建日期", "createDate", 80));
						list.add(addColumn("修改日期", "modifyDate", 80));
						list.add(addColumn("材料来源", "materialSource", 100));
						list.add(addColumn("报关助记码", "customsNo", 100));
						return list;
					}
				});
		return tableModel;

	}

	/**
	 * 获得删除按钮
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("关闭");
			btnExit.setPreferredSize(new Dimension(55, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
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
							materialManageAction
									.findMaterielforControlEdit(new Request(
											CommonVars.getCurrUser()));
							if (tableModel == null
									|| tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(FmMateriel.this,
										"请选择你要修改的单重记录", "提示",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							DgEditUnitWeight dg = new DgEditUnitWeight();
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
		long beginTime=System.currentTimeMillis();
		List list =  materialManageAction.findMateriel(new Request(CommonVars
				.getCurrUser()), materielType, index, length, property, value,
				isLike);
		long endTime=System.currentTimeMillis();
		System.out.println("------Search Time :"+(endTime-beginTime)/1000.0);
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
					return FmMateriel.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmMateriel.this.getDataSource(index, length,
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
	private JButton getBtnDeleteMet() {
		if (btnDeleteMet == null) {
			btnDeleteMet = new JButton();
			btnDeleteMet.setText("删除重复物料");
			btnDeleteMet.setPreferredSize(new Dimension(90, 30));
			btnDeleteMet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					materialManageAction.findMaterielforControlDel(new Request(
							CommonVars.getCurrUser()));
					DgRepeatMateriel dg = new DgRepeatMateriel();
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list.size() > 0) {
						try {
							long beginTime=System.currentTimeMillis();
							materialManageAction
									.deleteRepeatMateriel(new Request(
											CommonVars.getCurrUser(), true),
											list);
							long endTime=System.currentTimeMillis();
							System.out.println("------delect the same Time :"+(endTime-beginTime)/1000.0);
							JOptionPane.showMessageDialog(FmMateriel.this,
									"删除重复物料成功！", "提示", 1);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(FmMateriel.this,
									"删除重复物料失败！", "提示", 1);
						}
					}
				}
			});
		}
		return btnDeleteMet;
	}

	/**
	 * This method initializes jButton1
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
//					Materiel materiel = (Materiel) tableModel.getCurrentRow();
//					initUIComponents();
					pnCommonQueryPage.setInitState();
//					if (materiel != null) {
//						tableModel.updateRow(materiel);
//					}
				}
			});
		}
		return btnRefresh;
	}

}
