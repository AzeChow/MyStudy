/*
 * Created on 2004-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bls.client.blsinnermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bls.action.BlsInnerMergeAction;
import com.bestway.bls.entity.BlsTenInnerMerge;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.Item;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.DataType;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.SerialColumn;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import java.awt.Rectangle;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates checked by 陈井彬
 *         2008.11.7
 *         报头商品资料信息
 */
public class FmBlsTenInnerMerge extends JInternalFrameBase {

	private JToolBar jToolBar = null;

	/**
	 * 报头商品表格
	 */
	private JTable tableWare = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 报头商品表格模型
	 */
	private JTableListModel tableModel = null;

	private BlsInnerMergeAction blsInnerMergeAction = null;

	/**
	 * 增加按钮
	 */
	private JButton btnAdd = null;

	/**
	 * 刷新按钮
	 */
	private JButton btnRenovate = null;

	/**
	 * 删除按钮
	 */
	private JButton btnDelete = null;

	/**
	 * 修改按钮
	 */
	private JButton btnEdit = null;

	private JPanel jContentPane = null;

	private JPanel jPanel = null;

	private JToolBar jToolBar1 = null;

	private JPanel jPanel1 = null;

	/**
	 * 关闭按钮
	 */
	private JButton btnExit = null;

	/**
	 * 查询公共组件
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 商品信息数据源
	 */
	private List dataSource = null; // @jve:decl-index=0:

	/**
	 * This is the default constructor 构造函数
	 */
	public FmBlsTenInnerMerge() {
		super();
		blsInnerMergeAction = (BlsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("blsInnerMergeAction");
		initialize();
		// this.initTable(pnCommonQueryPage.dataSource);
		// initCbbQueryFields();

	}

	/**
	 * This method initializes this 初始化
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(665, 379);
		this.setContentPane(getJContentPane());
		this.setTitle("报关商品资料");

		this
				.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
					public void internalFrameOpened(
							javax.swing.event.InternalFrameEvent e) {
						// initTable(pnCommonQueryPage.dataSource);
						pnCommonQueryPage.setInitState();
						// initCbbQueryFields();
					}
				});
	}

	// public void initCbbQueryFields() {
	// if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {
	// pnCommonQueryPage.cbbQueryField.setSelectedItem(null);
	// return;
	// }
	// pnCommonQueryPage.getCbbQueryField().removeAllItems();
	// if (tableModel != null) {
	// for (int i = 0; i < tableModel.getColumnCount(); i++) {
	// JTableListColumn c = tableModel.getColumns().get(i);
	// if (c instanceof SerialColumn) {
	// continue;
	// }
	// if (c.isShowSearch() == false) {
	// continue;
	// }
	// pnCommonQueryPage.getCbbQueryField().addItem(
	// new Item(c.getCaption(),
	// c.getCustomProperty() == null ? c.getProperty()
	// : c.getCustomProperty(),
	// getDataTypeByColumn(c.getProperty())));
	// }
	// pnCommonQueryPage.cbbQueryField.setSelectedItem(null);
	// }
	// }
	//
	// public int getDataTypeByColumn(String sProp) {
	// List list = tableModel.getList();
	// if (list == null || list.size() <= 0) {
	// return DataType.NULL;
	// }
	// int dataType = DataType.NULL;
	// try {
	// if (list.get(0) == null) {
	// return dataType;
	// }
	// Class cls = CommonVariables.getTypeByField(list.get(0).getClass(),
	// sProp);
	// if (cls.equals(Integer.class) || cls.equals(Long.class)
	// || cls.equals(Short.class)) {
	// dataType = DataType.INTEGER;
	// } else if (cls.equals(Double.class) || cls.equals(Float.class)) {
	// dataType = DataType.DOUBLE;
	// } else if (cls.equals(String.class)) {
	// dataType = DataType.STRING;
	// } else if (cls.equals(Boolean.class)) {
	// dataType = DataType.BOOLEAN;
	// } else if (cls.equals(Date.class) || cls.equals(Calendar.class)) {
	// dataType = DataType.DATE;
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return dataType;
	// }

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
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRenovate());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJPanel1());

		}
		return jToolBar;
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
		if (tableWare == null) {
			tableWare = new JTable();
			tableWare.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() >= 2) {
						editData();
					}
				}
			});
		}
		return tableWare;
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
			jScrollPane.setBackground(java.awt.Color.WHITE);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * 初始化表格
	 */
	private JTableListModel initTable(List list) {

		tableModel = new JTableListModel(tableWare, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("归并序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("编码", "complex.code", 80));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("商品规格", "spec", 100));
						list.add(addColumn("常用单位", "comUnit.name", 80));
						list.add(addColumn("第一法定单位", "complex.firstUnit.name",
								120));
						list.add(addColumn("第二法定单位", "complex.secondUnit.name",
								120));
						list.add(addColumn("单位重量", "unitWeight", 80));
						list.add(addColumn("单价", "price", 60));
						list.add(addColumn("币制", "curr.name", 60));
						list.add(addColumn("产销国", "country.name", 70));
						list.add(addColumn("加工费单价", "machPrice", 60));
						list.add(addColumn("类型", "scmCoi", 70));
						list.add(addColumn("主辅料", "isMainImg", 70));
						return list;
					}
				});

		tableWare.getColumnModel().getColumn(13).setCellRenderer(
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
						if (String.valueOf(value).trim().equals("")) {
							return "";
						}
						if (value.equals(MaterielType.FINISHED_PRODUCT)) {
							returnValue = "成品";
						} else if (value.equals(MaterielType.MATERIEL)) {
							returnValue = "料件";
						}
						return returnValue;
					}
				});

		tableWare.getColumnModel().getColumn(14).setCellRenderer(
				new TableCheckBoxRender());
		return tableModel;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
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
//					ItemProperty item = (ItemProperty) cbbMaterielType
//							.getSelectedItem();
//					if (item != null) {
						List list = BlsInnerMergeQuery.getInstance()
								.getComplexOfBlsTenInnerMerge();
						if (list == null) {
							BlsTenInnerMerge dd;
							return;
						}
						List listComplex = blsInnerMergeAction
								.saveBlsTenInnerMerge(new Request(CommonVars
										.getCurrUser()), list);
						tableModel.addRows(listComplex);
//					} else {
//						JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this,
//								"请先选择物料类别！", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
//					}

				}
			});
		}
		return btnAdd;
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
//					ItemProperty item = (ItemProperty) cbbMaterielType
//							.getSelectedItem();
//					String items = item.getCode();
					List list = tableModel.getCurrentRows();
					if (list.size() < 0) {
						JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this,
								"请选择要删除的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					// blsInnerMergeAction.deleteBlsTenInnerMerge(new Request(
					// CommonVars.getCurrUser()), list);
					List<BlsTenInnerMerge> ListBlsTenInnerMerge = blsInnerMergeAction
							.deleteBlsTenInnerMergeForContract(new Request(
									CommonVars.getCurrUser()), list);
					if (ListBlsTenInnerMerge.size() > 0
							&& list.size() == ListBlsTenInnerMerge.size()) {
						JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this,
								"你确定要删除所选择的报关商品归并吗?", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						for (int i = 0; i < ListBlsTenInnerMerge.size(); i++) {
							BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) ListBlsTenInnerMerge
									.get(i);
							tableModel.deleteRow(blsTenInnerMerge);
						}
					} else if (ListBlsTenInnerMerge.size() > 0
							&& ListBlsTenInnerMerge.size() < list.size()) {
						JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this,
								"所选的部分数据被内部归并引用，暂时无法删除，请先删除引用！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						for (int i = 0; i < ListBlsTenInnerMerge.size(); i++) {
							BlsTenInnerMerge blsTenInnerMerge = (BlsTenInnerMerge) ListBlsTenInnerMerge
									.get(i);
							tableModel.deleteRow(blsTenInnerMerge);
						}
					} else if (ListBlsTenInnerMerge.size() == 0) {
						JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this,
								"所选的数据被内部归并引用，暂时无法删除，请先删除引用！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton();
			btnEdit.setText("修改");
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
				}
			});
		}
		return btnEdit;
	}

	// 刷新数据
	private JButton getBtnRenovate() {
		if (btnRenovate == null) {
			btnRenovate = new JButton();
			btnRenovate.setText("刷新");
			btnRenovate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					pnCommonQueryPage.setInitState();
				}
			});
		}
		return btnRenovate;
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
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
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
			jPanel.add(getJToolBar1(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
		}
		return jPanel1;
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
//		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
//				.getCode();
//		if (materielType == null) {
//			return new ArrayList();
//		}
		Request request = new Request(CommonVars.getCurrUser());
		dataSource = blsInnerMergeAction.findBlsTenInnerMerge(request,
				index, length, property, value, isLike);
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

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmBlsTenInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmBlsTenInnerMerge.this.getDataSource(index, length,
							property, value, isLike);
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * 修改数据
	 * 
	 */
	private void editData() {
//		ItemProperty item = (ItemProperty) cbbMaterielType.getSelectedItem();
//		if (item != null) {
			if (tableModel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this,
						"请选择你要修改的数据", "提示", JOptionPane.OK_OPTION);
				return;
			}
			DgBlsTenInnerMerge dg = new DgBlsTenInnerMerge();
			dg.setTable(tableWare);
			dg.setAddFromBlsTenInnerMerge(true);
			dg.setDataState(DataState.EDIT);
//			dg.setMaterielType(item.getCode());
			dg.setTitle("报关商品资料编辑");
			dg.setVisible(true);
//		} else {
//			JOptionPane.showMessageDialog(FmBlsTenInnerMerge.this, "请先选择物料类别",
//					"提示", JOptionPane.OK_OPTION);
//		}
	}
}
