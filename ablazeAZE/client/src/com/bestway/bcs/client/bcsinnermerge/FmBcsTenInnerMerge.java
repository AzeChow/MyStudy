/*
 * Created on 2004-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcs.client.bcsinnermerge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.bcsinnermerge.action.BcsInnerMergeAction;
import com.bestway.bcs.bcsinnermerge.entity.BcsTenInnerMerge;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.client.common.TableCheckBoxRender;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

/**
 * @author Administrator
 * 
 *         // change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Style - Code Templates checked by 陈井彬
 *         2008.11.7 报头商品资料信息
 */
public class FmBcsTenInnerMerge extends JInternalFrameBase {

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

	/**
	 * 内部归并接口
	 */
	private BcsInnerMergeAction bcsInnerMergeAction = null;

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
	 * 物料类别
	 */
	private JComboBox cbbMaterielType = null;

	private JLabel lb = null;

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
	private JButton btnImport;
	private BcsDictPorHead bcsDictPorHead = null;

	/**
	 * This is the default constructor 构造函数
	 */
	public FmBcsTenInnerMerge() {
		super();
		bcsInnerMergeAction = (BcsInnerMergeAction) CommonVars
				.getApplicationContext().getBean("bcsInnerMergeAction");
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
						pnCommonQueryPage.setInitState();
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
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJPanel1());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getBtnRenovate());
			jToolBar.add(getBtnExit());
			

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
			tableWare.setRowHeight(25);
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
			btnAdd.setPreferredSize(new Dimension(65, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty item = (ItemProperty) cbbMaterielType
							.getSelectedItem();
					if (item != null) {
						List list = BcsCommonQuery.getInstance()
								.getComplexOfBcsTenInnerMerge();
						if (list == null) {
							return;
						}
						long beginTime=System.currentTimeMillis();
//						//已存在的报关商品资料
//						List<BcsTenInnerMerge> listBcsTenInnerMerge = bcsInnerMergeAction.findBcsTenInnerMerge(new Request(CommonVars.getCurrUser()), item.getCode());
//						//如果新增的商品在报关商品资料中已存在提示不允许保存！Key=编码+名称+规格+单位
//						Map<String, BcsTenInnerMerge> tenInnerMergeMap = new HashMap<String, BcsTenInnerMerge>();
//						String errorMessage="";
//						List<Complex> tempList = new ArrayList<Complex>();
//						for (int i = 0; i < listBcsTenInnerMerge.size(); i++) {
//							 BcsTenInnerMerge obj = listBcsTenInnerMerge.get(i);
//							 String code = obj.getComplex().getCode();
//							 String name = obj.getName()==null?"":obj.getName();
//							 String spec= obj.getSpec()==null?"":obj.getSpec();
//							 String unit = obj.getComUnit()==null?"":obj.getComUnit().getName();
//							 String key = code+name+spec+unit;
//							 if(!tenInnerMergeMap.containsKey(key)){
//								 tenInnerMergeMap.put(key, obj);
//							 }
//						}
//						for (int i = 0; i < list.size(); i++) {
//							 Complex complex = (Complex)list.get(i);
//							 String code = complex.getCode();
//							 String name = complex.getName()==null?"":complex.getName();
//							 String spec= "";
//							 String unit = "";
//							 String key = code+name+spec+unit;
//							 if(!tenInnerMergeMap.containsKey(key)){
//								 tempList.add(complex);
//							 }else{
//								 errorMessage += complex.getCode()+"/" ;
//							 }
//						}
//						if(errorMessage.length()>0){
//							JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
//									"所选择的料号"+errorMessage+"在报关商品资料中已存在相同的编码+名称+规格+单位", "提示",
//									JOptionPane.INFORMATION_MESSAGE);
//							return;
//						}
//						
						List listComplex = bcsInnerMergeAction.importBcsTenInnerMergeFromComplex(
								new Request(CommonVars.getCurrUser()), list, item.getCode());
						
						long endTime=System.currentTimeMillis();
						System.out.println("------Save Time :"+(endTime-beginTime)/1000.0);
						tableModel.addRows(listComplex);
						for(Object c : listComplex){
							tableModel.selectRowByData(c);
							if(editData())
								break;
						}
						pnCommonQueryPage.refreshData();
					} else {
						JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
								"请先选择物料类别！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}

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
			btnDelete.setPreferredSize(new Dimension(65, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ItemProperty item = (ItemProperty) cbbMaterielType
							.getSelectedItem();
					String items = item.getCode();
					List list = tableModel.getCurrentRows();
					if (list.size() < 0) {
						JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
								"请选择要删除的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					// bcsInnerMergeAction.deleteBcsTenInnerMerge(new Request(
					// CommonVars.getCurrUser()), list);
					List<BcsTenInnerMerge> ListBcsTenInnerMerge = bcsInnerMergeAction
							.deleteBcsTenInnerMergeForContract(new Request(
									CommonVars.getCurrUser()), list, items);
					if (ListBcsTenInnerMerge.size() > 0
							&& list.size() == ListBcsTenInnerMerge.size()) {
//						JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
//								"你确定要删除所选择的报关商品归并吗?", "提示",
//								JOptionPane.INFORMATION_MESSAGE);
						if (JOptionPane.showConfirmDialog(FmBcsTenInnerMerge.this,
								"你确定要删除所选择的报关商品归并吗?", "提示", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
							return;
						}
						
						long  beginTime = System.currentTimeMillis();

						for (int i = 0; i < ListBcsTenInnerMerge.size(); i++) {
							BcsTenInnerMerge bcsTenInnerMerge = (BcsTenInnerMerge) ListBcsTenInnerMerge
									.get(i);
							tableModel.deleteRow(bcsTenInnerMerge);
						}
						long endTime = System.currentTimeMillis();
						System.out.println("----delete time ---" + (endTime - beginTime)/1000.0);
						
						
					} else if (ListBcsTenInnerMerge.size() > 0
							&& ListBcsTenInnerMerge.size() < list.size()) {
						JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
								"所选的部分数据被商品归并或备案资料库或合同引用，暂时无法删除，请先删除引用！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						for (int i = 0; i < ListBcsTenInnerMerge.size(); i++) {
							BcsTenInnerMerge bcsTenInnerMerge = (BcsTenInnerMerge) ListBcsTenInnerMerge
									.get(i);
							tableModel.deleteRow(bcsTenInnerMerge);
						}
					} else if (ListBcsTenInnerMerge.size() == 0) {
						JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
								"所选的数据被商品归并或备案资料库或合同引用，暂时无法删除，请先删除引用！", "提示",
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
			btnEdit.setVisible(false);
			btnEdit.setPreferredSize(new Dimension(65, 30));
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					editData();
				}
			});
		}
		return btnEdit;
	}

	/**
	 * 刷新数据
	 * 
	 * @return
	 */
	private JButton getBtnRenovate() {
		if (btnRenovate == null) {
			btnRenovate = new JButton();
			btnRenovate.setText("刷新");
			btnRenovate.setPreferredSize(new Dimension(65, 30));
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
			lb = new JLabel();
			lb.setBounds(new Rectangle(4, 1, 58, 27));
			lb.setText("物料类别");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(250, 30));
			jPanel1.setBorder(BorderFactory
					.createEtchedBorder(EtchedBorder.LOWERED));
			jPanel1.add(lb, null);
			jPanel1.add(getCbbMaterielType(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbMaterielType
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbMaterielType() {
		if (cbbMaterielType == null) {
			cbbMaterielType = new JComboBox();
			cbbMaterielType.setBounds(new Rectangle(65, 1, 174, 27));
			cbbMaterielType.addItem(new ItemProperty(
					MaterielType.FINISHED_PRODUCT, "成品"));
			// cbbMaterielType.addItem(new ItemProperty(
			// MaterielType.SEMI_FINISHED_PRODUCT, "半成品"));
			cbbMaterielType.addItem(new ItemProperty(MaterielType.MATERIEL,
					"料件"));
			 cbbMaterielType
			 .addItem(new ItemProperty(MaterielType.MACHINE, "设备"));
			// cbbMaterielType.addItem(new ItemProperty(
			// MaterielType.REMAIN_MATERIEL, "边角料"));
			// cbbMaterielType.addItem(new
			// ItemProperty(MaterielType.BAD_PRODUCT,
			// "残次品"));
			cbbMaterielType.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED
							&& cbbMaterielType.isFocusOwner()) {
						pnCommonQueryPage.setInitState();
						if(((ItemProperty)cbbMaterielType.getSelectedItem()).getCode().equals(MaterielType.MACHINE))
							btnImport.setEnabled(false);
						else
							btnImport.setEnabled(true);
					}					
				}
			});
		}
		return cbbMaterielType;
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
			btnExit.setPreferredSize(new Dimension(65, 30));
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
		String materielType = ((ItemProperty) cbbMaterielType.getSelectedItem())
				.getCode();
		if (materielType == null) {
			return new ArrayList();
		}
		Request request = new Request(CommonVars.getCurrUser());
		dataSource = bcsInnerMergeAction.findBcsTenInnerMerge(request,
				materielType, index, length, property, value, isLike);
		return dataSource;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	public PnCommonQueryPage getPnCommonQueryPage() {
		if (pnCommonQueryPage == null) {
			pnCommonQueryPage = new PnCommonQueryPage() {

				@Override
				public JTableListModel initTable(List dataSource) {
					return FmBcsTenInnerMerge.this.initTable(dataSource);
				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					return FmBcsTenInnerMerge.this.getDataSource(index, length,
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
	private boolean editData() {
		ItemProperty item = (ItemProperty) cbbMaterielType.getSelectedItem();
		if (item != null) {
			if (tableModel.getCurrentRow() == null) {
				JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,
						"请选择你要修改的数据", "提示", JOptionPane.OK_OPTION);
				return false;
			}
			DgBcsTenInnerMerge dg = new DgBcsTenInnerMerge();
			dg.setTable(tableWare);
			dg.setFm(FmBcsTenInnerMerge.this);
			dg.setAddFromBcsTenInnerMerge(true);
			dg.setDataState(DataState.EDIT);
			dg.setMaterielType(item.getCode());
			dg.setTitle("报关商品资料编辑");
			dg.setVisible(true);
			return dg.getIsCancel();
		} else {
			JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this, "请先选择物料类别","提示", JOptionPane.OK_OPTION);
			return false;
		}
	}
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton("备案资料库导入");
			btnImport.setPreferredSize(new Dimension(120, 30));
			btnImport.setToolTipText("商品编码 + 商品名称 + 商品规格 + 计量单位为唯一标识");			
			btnImport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//检查备案资料库
					if(checkBcsDictPorHead()){
						return;
					}
					
					if(bcsDictPorHead==null){
						JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this, "请选择备案资料库！");
						return;
					}
					
					final String matertielType = ((ItemProperty)cbbMaterielType.getSelectedItem()).getCode();
					new AsynSwingWorker() {
						protected Object submit() {
							try{
								CommonProgress.showProgressDialog();
								CommonProgress.setMessage("正在导入,请稍等...");
								Integer size = bcsInnerMergeAction.importInnerMergeDataFromContract(new Request(CommonVars.getCurrUser()),matertielType,bcsDictPorHead);
								if(size!=null){
									CommonProgress.closeProgressDialog();
									JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this,"成功导入"+size+"笔数据！");
								}
								return null;
								
							}finally{
								CommonProgress.closeProgressDialog();
							}
						}
						protected void success(Object result) {
							pnCommonQueryPage.setInitState();
						}
					}.doWork();
					
				}
			});
		}
		return btnImport;
	}
	
	/**
	 * 检查备案资料库
	 */
	public boolean checkBcsDictPorHead(){
		List<BcsDictPorHead> bcsDictPorHeads = bcsInnerMergeAction.findCountBcsDictPorHead(new Request(CommonVars.getCurrUser()));
		//当正在执行的备案资料库数量大于一,有多个时,弹出选择框让用户选择备案资料库
		if(bcsDictPorHeads.size()>1){
			DgBcsTenInnerImport dg = new DgBcsTenInnerImport();
			dg.setVisible(true);
			
			//当点击确定是获取选择的备案资料库,否则退出操作
			if(dg.getIsOk()){
				bcsDictPorHead = dg.getBcsDictPorHead();
			}else{
				return true;
			}
		}else{
			//当正在执行的备案资料库数量等于时,取当前正在执行的备案资料库
			if(bcsDictPorHeads.size()==1){
				if(JOptionPane.NO_OPTION == JOptionPane.showOptionDialog(getContentPane(), "确定从备案资料库导入吗？","提示", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,new String[]{"是","否"} , "否"))
					return true;
				bcsDictPorHead = bcsDictPorHeads.get(0);
			}else if(bcsDictPorHeads.size()==0){//等于0时,提示没有正在执行的备案资料库
				JOptionPane.showMessageDialog(FmBcsTenInnerMerge.this, "没有正在执行的备案资料库！");
				return true;
			}
		}
		
		return false;
	}
}
