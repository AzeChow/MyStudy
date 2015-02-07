/**
 * 实际报关资料
 * 
 * 刘民添加部分注释
 * 
 * @author 余鹏// change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */
package com.bestway.common.client.erpbillcenter;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.erpbillcenter.authority.ErpBillCenterAuthority;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Color;
import java.awt.SystemColor;

public class FmFactualCustoms extends JInternalFrameBase {

	private JToolBar jJToolBarBar = null;

	private JButton btnInputBcs = null;

	private JButton btnInputBcus = null;

	private JButton btnInputDzsc = null;

	private JButton btnExit = null;

	private JLabel jLabel = null;

	private JComboBox cbbMaterialType = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JTableListModel tableModel = null;

	private CasAction casAction = null;

	private JButton btnEditEmsNo = null;

	private JButton btnRefresh = null;

	private JButton btnImportFromFile = null;

	private JSplitPane jSplitPane = null;

	private JSplitPane jSplitPane1 = null;

	private PnCommonQueryPage pnCommonQueryPage = null;

	private List dataSource = null;

	private JPanel jPanel = null;

	private JButton btnDelete = null;

	private JToolBar jToolBar = null;

	private JPanel jPanel1 = null;

	private JRadioButton rbEmsSeqNum = null;

	private JPanel jPanel2 = null;

	private JRadioButton rbEmsNoNameSpecUnit = null;
	private ButtonGroup bg = new ButtonGroup();

	private JToolBar jToolBar1 = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmFactualCustoms() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		ErpBillCenterAuthority erpBillCenterAuthority = (ErpBillCenterAuthority) CommonVars
				.getApplicationContext().getBean("erpBillCenterAuthority");
		erpBillCenterAuthority.checkFactualCustoms(new Request(
				CommonVars.getCurrUser()));
		initialize();
		getPnCommonQueryPage().setInitState();
		bg.add(this.rbEmsNoNameSpecUnit);
		bg.add(this.rbEmsSeqNum);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(863, 558));
		this.setContentPane(getJSplitPane());
		this.setTitle("实际报关资料");
		cbbMaterialType.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				getPnCommonQueryPage().setInitState();
				if (((ItemProperty) cbbMaterialType.getSelectedItem())
						.getCode().equals(MaterielType.MACHINE)) {
					btnEditEmsNo.setVisible(true);
				} else
					btnEditEmsNo.setVisible(false);
			}
		});

	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("物料类型");
			jLabel.setBounds(new Rectangle(16, 2, 55, 22));
			jLabel.setPreferredSize(new Dimension(55, 25));
			jJToolBarBar = new JToolBar();
			jJToolBarBar.setFloatable(false);
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setHgap(1);
			fl.setVgap(1);
			jJToolBarBar.setLayout(fl);
			jJToolBarBar.add(getBtnEditEmsNo());
			jJToolBarBar.add(getJPanel1());
			jJToolBarBar.add(getBtnImportFromFile());
			jJToolBarBar.add(getBtnDelete());
			jJToolBarBar.add(getBtnExit());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes btnInputBcs
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInputBcs() {
		if (btnInputBcs == null) {
			btnInputBcs = new JButton();
			btnInputBcs.setPreferredSize(new Dimension(100, 25));
			btnInputBcs.setText("电子化手册导入");
			btnInputBcs.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbMaterialType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"没有选择物料类型！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					MyFindThread fth = new MyFindThread();
					fth.type = ProjectType.BCS;
					fth.execute();
				}
			});
		}
		return btnInputBcs;
	}

	/**
	 * This method initializes btnInputBcus
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInputBcus() {
		if (btnInputBcus == null) {
			btnInputBcus = new JButton();
			btnInputBcus.setPreferredSize(new Dimension(90, 25));
			btnInputBcus.setText("电子帐册导入");
			btnInputBcus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbMaterialType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"没有选择物料类型！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					MyFindThread fth = new MyFindThread();
					fth.type = ProjectType.BCUS;
					fth.execute();
				}
			});
		}
		return btnInputBcus;
	}

	/**
	 * This method initializes btnInputDzsc
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInputDzsc() {
		if (btnInputDzsc == null) {
			btnInputDzsc = new JButton();
			btnInputDzsc.setPreferredSize(new Dimension(90, 30));
			btnInputDzsc.setText("电子手册导入");
			btnInputDzsc.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (cbbMaterialType.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"没有选择物料类型！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					MyFindThread fth = new MyFindThread();
					fth.type = ProjectType.DZSC;
					fth.execute();
				}
			});
		}
		return btnInputDzsc;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setPreferredSize(new Dimension(65, 30));
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
	 * This method initializes cbbMaterialType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterialType() {
		if (cbbMaterialType == null) {
			cbbMaterialType = new JComboBox();
			cbbMaterialType.setPreferredSize(new Dimension(110, 24));
			cbbMaterialType.setBounds(new Rectangle(82, 0, 157, 24));
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			// cbbMaterialType.addItem(new ItemProperty(
			// MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterialType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			cbbMaterialType
					.addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			cbbMaterialType.addItem(new ItemProperty(
					MaterielType.REMAIN_MATERIEL, "边角料"));
			cbbMaterialType.addItem(new ItemProperty(MaterielType.BAD_PRODUCT,
					"残次品"));
			cbbMaterialType.setSelectedIndex(0);
		}
		return cbbMaterialType;
	}

	/**
	 * 初始化tableModel
	 * 
	 * @param list
	 * @return
	 */

	private JTableListModel initTable(final List list) {
		// if (cbbMaterialType.getSelectedItem() != null) {
		// String mtype = ((ItemProperty) cbbMaterialType.getSelectedItem())
		// .getCode();
		// dataSource = casAction.findFactualCustoms(new Request(CommonVars
		// .getCurrUser()), mtype);
		// } else {
		// dataSource = new ArrayList();
		// }

		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("编码", "complex.code", 80));
						list.add(addColumn("名称", "cusName", 140));
						list.add(addColumn("规格", "cusSpec", 140));
						list.add(addColumn("单位", "cusUnit.name", 40));
						if (cbbMaterialType != null
								&& cbbMaterialType.getSelectedItem() != null
								&& ((ItemProperty) cbbMaterialType
										.getSelectedItem()).getCode().equals(
										MaterielType.MACHINE))
							list.add(addColumn("合同协议号", "emsNo", 120));
						else
							list.add(addColumn("手册号", "emsNo", 120));
						list.add(addColumn("版本", "version", 40));
						list
								.add(addColumn("归并序号", "seqNum", 50,
										Integer.class));
						list.add(addColumn("管理类型", "projectName", 60));
						list.add(addColumn("起始日期", "emsBeginDate", 100));
						list.add(addColumn("结束日期", "emsEndDate", 100));

						return list;
					}
				});
		jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableModel.setMiRenderColumnEnabled(false);
		return tableModel;
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
			jTable.setRowHeight(25);
		}
		return jTable;
	}

	/**
	 * This method initializes btnEditEmsNo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEditEmsNo() {
		if (btnEditEmsNo == null) {
			btnEditEmsNo = new JButton();
			// btnEditEmsNo.setPreferredSize(new Dimension(40, 25));
			btnEditEmsNo.setText("修改协议号");
			btnEditEmsNo.setVisible(false);
			btnEditEmsNo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane
								.showMessageDialog(FmFactualCustoms.this,
										"请选择一条要修改的数据!", "提示",
										JOptionPane.ERROR_MESSAGE);
						return;
					}
					DgEditEmsNo dg = new DgEditEmsNo();
					dg.setTableModel(tableModel);
					dg.setVisible(true);
				}
			});
		}
		return btnEditEmsNo;
	}

	/**
	 * SwingWorker线程类
	 * 
	 * @author ower
	 * 
	 */

	class MyFindThread extends SwingWorker {
		public int type = ProjectType.BCS;

		@Override
		protected Object doInBackground() {
			CommonProgress.showProgressDialog();
			CommonProgress.setMessage("系统正在获取数据，请稍候...");

			if (type == ProjectType.BCS) {
				String mtype = ((ItemProperty) cbbMaterialType
						.getSelectedItem()).getCode();
				if (mtype.equals(MaterielType.MACHINE)) {// 设备
					boolean isok = casAction
							.makeAllInnerFixtureToFactualCustoms(new Request(
									CommonVars.getCurrUser()), ProjectType.BCS);
					if (!isok) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"导入数据失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
				} else {
					boolean isok = casAction
							.makeAllContractImgExgToFactualCustoms(new Request(
									CommonVars.getCurrUser()), mtype,
									ProjectType.BCS, false);
					if (!isok) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"导入数据失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
				}
			} else if (type == ProjectType.BCUS) {
				String mtype = ((ItemProperty) cbbMaterialType
						.getSelectedItem()).getCode();
				if (mtype.equals(MaterielType.MACHINE)) {// 设备
					boolean isok = casAction
							.makeAllInnerFixtureToFactualCustoms(new Request(
									CommonVars.getCurrUser()), ProjectType.BCUS);
					if (!isok) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"导入数据失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
				} else {
					boolean isok = casAction
							.makeAllContractImgExgToFactualCustoms(new Request(
									CommonVars.getCurrUser()), mtype,
									ProjectType.BCUS, false);
					if (!isok) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"导入数据失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
				}
			} else if (type == ProjectType.DZSC) {
				String mtype = ((ItemProperty) cbbMaterialType
						.getSelectedItem()).getCode();
				if (mtype.equals(MaterielType.MACHINE)) {// 设备
					boolean isok = casAction
							.makeAllInnerFixtureToFactualCustoms(new Request(
									CommonVars.getCurrUser()), ProjectType.DZSC);
					if (!isok) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"导入数据失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
				} else {
					boolean isok = casAction
							.makeAllContractImgExgToFactualCustoms(new Request(
									CommonVars.getCurrUser()), mtype,
									ProjectType.DZSC, rbEmsSeqNum.isSelected());
					if (!isok) {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"导入数据失败！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return null;
					}
				}
			} else if (type == 10000) {
				// initTable();
			}
			CommonProgress.closeProgressDialog();

			return null;
		}

		/**
		 * 执行查询完毕后
		 */

		@Override
		protected void done() {
			getPnCommonQueryPage().setInitState();
		}
	}

	/**
	 * This method initializes btnImportFromFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImportFromFile() {
		if (btnImportFromFile == null) {
			btnImportFromFile = new JButton();
			btnImportFromFile.setPreferredSize(new Dimension(90, 30));
			btnImportFromFile.setText("从文件导入");
			btnImportFromFile
					.addActionListener(new java.awt.event.ActionListener() {

						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							DgHsnImportFromFile dg = new DgHsnImportFromFile();
							ItemProperty type = (ItemProperty) cbbMaterialType
									.getSelectedItem();
							dg.setMaterielType(type.getCode());
							dg.setTableModel(tableModel);
							dg.setVisible(true);
						}

					});
		}
		return btnImportFromFile;
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
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJSplitPane1());
			jSplitPane.setDividerLocation(110);
			jSplitPane.setDividerSize(2);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(35);
			jSplitPane1.setTopComponent(getJJToolBarBar());
			jSplitPane1.setBottomComponent(getJPanel());
			jSplitPane1.setDividerSize(2);
		}
		return jSplitPane1;
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
		Request request = new Request(CommonVars.getCurrUser());
		if (cbbMaterialType.getSelectedItem() != null) {
			String mtype = ((ItemProperty) cbbMaterialType.getSelectedItem())
					.getCode();
			dataSource = casAction.findStatCusNameRelationHsn(request, index,
					length, property, value, isLike, mtype);
		} else {
			dataSource = new ArrayList();
		}
		return dataSource;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */

	private PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {
				/**
				 * 初始化表格
				 */

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmFactualCustoms.this.initTable(dataSource);

				}

				/**
				 * 获取数据源
				 */

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					List list = FmFactualCustoms.this.getDataSource(index,
							length, property, value, isLike);
					return list;
				}

			};
			pnCommonQueryPage.setPreferredSize(new Dimension(60, 23));
		}
		return pnCommonQueryPage;
	}

	/**
	 * 得到所有数据
	 */
	public void getData() {
		pnCommonQueryPage = getPnCommonQueryPage();
		if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {
			pnCommonQueryPage.getCbbQueryField().removeAllItems();
			pnCommonQueryPage.setFirstHasDataInit(true);
		}
		getPnCommonQueryPage().setInitState();
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setPreferredSize(new Dimension(2, 100));
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJToolBar1(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setPreferredSize(new Dimension(65, 30));
			btnDelete.setText("删除");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (tableModel.getCurrentRow() != null) {
						if (JOptionPane.showConfirmDialog(
								FmFactualCustoms.this, "您确定要删除选择的资料吗?", "提示",
								JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION) {
							return;
						}
						if (tableModel.getCurrentRows().size() == 0) {
							JOptionPane.showMessageDialog(
									FmFactualCustoms.this, "请选择你要删除的列!", "提示!",
									JOptionPane.WARNING_MESSAGE);
							return;
						}
						List delelist = tableModel.getCurrentRows();
						casAction.deleteStatCusNameRelationHsns(new Request(
								CommonVars.getCurrUser()), delelist);

						getPnCommonQueryPage().setInitState();
					} else {
						JOptionPane.showMessageDialog(FmFactualCustoms.this,
								"请选择你要删除的列!", "提示!",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

				}

			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnInputBcs());
			jToolBar.add(getBtnInputBcus());
			jToolBar.add(getBtnInputDzsc());
			jToolBar.add(getJPanel2());
		}
		return jToolBar;
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
			jPanel1.setPreferredSize(new Dimension(250, 30));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel1.add(jLabel, null);
			jPanel1.add(getCbbMaterialType(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes rbEmsSeqNum
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEmsSeqNum() {
		if (rbEmsSeqNum == null) {
			rbEmsSeqNum = new JRadioButton();
			rbEmsSeqNum.setText("电子手册导入以(手册号＋归并后序号)为唯一标识");
			rbEmsSeqNum.setForeground(Color.darkGray);
			rbEmsSeqNum.setBounds(new Rectangle(6, 1, 369, 16));
		}
		return rbEmsSeqNum;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getRbEmsSeqNum(), null);
			jPanel2.add(getRbEmsNoNameSpecUnit(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbEmsNoNameSpecUnit
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEmsNoNameSpecUnit() {
		if (rbEmsNoNameSpecUnit == null) {
			rbEmsNoNameSpecUnit = new JRadioButton();
			rbEmsNoNameSpecUnit.setBounds(new Rectangle(6, 16, 369, 14));
			rbEmsNoNameSpecUnit.setSelected(true);
			rbEmsNoNameSpecUnit.setForeground(SystemColor.activeCaption);
			rbEmsNoNameSpecUnit.setText("电子手册导入以(手册号＋名称+规格＋单位)为唯一标识");
		}
		return rbEmsNoNameSpecUnit;
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
			jToolBar1.setPreferredSize(new Dimension(2, 40));
			jToolBar1.add(getPnCommonQueryPage());
		}
		return jToolBar1;
	}

	// class FindThred extends Thread {
	// public int type = ProjectType.BCS;
	//
	// public void run() {
	// CommonProgress.showProgressDialog();
	// CommonProgress.setMessage("系统正在获取数据，请稍候...");
	// if (type == ProjectType.BCS) {
	// String mtype = ((ItemProperty) cbbMaterialType
	// .getSelectedItem()).getCode();
	// if (mtype.equals(MaterielType.MACHINE)) {// 设备
	// boolean isok = casAction
	// .makeAllInnerFixtureToFactualCustoms(new Request(
	// CommonVars.getCurrUser()), ProjectType.BCS);
	// if (!isok) {
	// JOptionPane.showMessageDialog(FmFactualCustoms.this,
	// "导入数据失败！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// } else {
	// boolean isok = casAction
	// .makeAllContractImgExgToFactualCustoms(new Request(
	// CommonVars.getCurrUser()), mtype,
	// ProjectType.BCS);
	// if (!isok) {
	// JOptionPane.showMessageDialog(FmFactualCustoms.this,
	// "导入数据失败！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// }
	// initTable();
	// } else if (type == ProjectType.BCUS) {
	// String mtype = ((ItemProperty) cbbMaterialType
	// .getSelectedItem()).getCode();
	// if (mtype.equals(MaterielType.MACHINE)) {// 设备
	// boolean isok = casAction
	// .makeAllInnerFixtureToFactualCustoms(new Request(
	// CommonVars.getCurrUser()), ProjectType.BCUS);
	// if (!isok) {
	// JOptionPane.showMessageDialog(FmFactualCustoms.this,
	// "导入数据失败！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// } else {
	// boolean isok = casAction
	// .makeAllContractImgExgToFactualCustoms(new Request(
	// CommonVars.getCurrUser()), mtype,
	// ProjectType.BCUS);
	// if (!isok) {
	// JOptionPane.showMessageDialog(FmFactualCustoms.this,
	// "导入数据失败！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// }
	// initTable();
	// } else if (type == ProjectType.DZSC) {
	// String mtype = ((ItemProperty) cbbMaterialType
	// .getSelectedItem()).getCode();
	// if (mtype.equals(MaterielType.MACHINE)) {// 设备
	// boolean isok = casAction
	// .makeAllInnerFixtureToFactualCustoms(new Request(
	// CommonVars.getCurrUser()), ProjectType.DZSC);
	// if (!isok) {
	// JOptionPane.showMessageDialog(FmFactualCustoms.this,
	// "导入数据失败！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// } else {
	// boolean isok = casAction
	// .makeAllContractImgExgToFactualCustoms(new Request(
	// CommonVars.getCurrUser()), mtype,
	// ProjectType.DZSC);
	// if (!isok) {
	// JOptionPane.showMessageDialog(FmFactualCustoms.this,
	// "导入数据失败！", "提示",
	// JOptionPane.INFORMATION_MESSAGE);
	// return;
	// }
	// }
	// initTable();
	// } else if (type == 10000) {
	// initTable();// 刷新
	// }
	// CommonProgress.closeProgressDialog();
	// }
	//
	// }
}
