package com.bestway.client.custom.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.bestway.bcs.client.contractexe.DgImpExpGoodsBill;
import com.bestway.bcs.client.contractexe.DgMakeBcsCustomsDeclaration;
import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DataState;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.enc.DgBcusImpexpRequestbillSplit;
import com.bestway.bcus.client.enc.DgQueryCondition;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.dzsc.client.impexprequest.DgDzscMakeCustomsListNew;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.jptds.client.common.CommonStepProgress;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.ui.winuicontrol.calendar.JCalendarComboBox;

/**
 * 电子帐册通关---进出口申请单
 * 
 * 刘民添加部分注释 修改时间: 2009-10-14
 * 
 * @author Administrator // change the template for this generated type comment
 * 
 *         go to Window - Preferences - Java - Code Style - Code Templates
 */
@SuppressWarnings({ "unchecked", "serial" })
public class FmBaseImpExpRequestBill extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JTabbedPane jTabbedPane = null;

	private JToolBar jToolBar = null;

	private JButton btnAdd = null;

	private JButton btnEdit = null;

	private JButton btnDelete = null;

	private JButton btnMakeCustomsBill = null;

	private JMenuItem miBackPort = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane = null;

	private JTree treImport = null;

	private JTree treExport = null;
	/** 申请单物料对象 */
	private ImpExpRequestBill impExpRequestBill = null; // @jve:decl-index=0:

	private EncAction encAction = null; // @jve:decl-index=0:
	/** 数据源List */
	private List list = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;
	/** 进出口类型 */
	private int impExpType = -1;
	/** 单据类型 */
	private int billType = -1;
	/** 物料类别 */
	private int materielProductType = -1;
	/** 是否第一次打开 */
	private boolean isFirstTime = true;
	/** 是否导入 */
	private boolean isImport = false;

	private JButton btnExit = null;

	private JMenuItem miUpdateMaxVersion = null;

	private JScrollPane jScrollPane1 = null;

	private JScrollPane jScrollPane2 = null;

	private JButton btnSearch = null;

	private JButton btnShowAll = null;

	private JPanel jPanel1 = null;

	private JLabel lbBeginDate = null;

	private JCalendarComboBox cbbBeginDate = null;

	private JLabel lbTo = null;

	private JCalendarComboBox cbbEndDate = null;

	private JButton btnRefresh = null;
	/** 分隔线 */
	private Separator separator3 = null;
	/** 其他功能的按钮 */
	private JPopupMenu pmOtherFunction = null;

	private JButton btnOtherFunction = null;
	/** 转抄 */
	private JMenuItem miCopy = null;
	/** 分隔线 */
	private Separator separatorOtherFunction = null;
	/** 分隔线 */
	private Separator separatorOtherFunction1 = null;
	/** 进出口类型 */
	private int type = -1;
	/** 系统类型 电子账册，电子手册，电子化手册。 默认：电子账册 */
	protected int projectType;
	/** 为进口/出口 */
	private Boolean isOut = false; // @jve:decl-index=0:

	private JPopupMenu pmMakeApplyOrCustoms = null;

	private ContractAction contractAction = null;

	private DzscAction dzscAction = null;

	/**
	 * This method initializes
	 * 
	 */
	public FmBaseImpExpRequestBill() {
		super();
		this.encAction = (EncAction) CommonVars.getApplicationContext()
				.getBean("encAction");
		this.contractAction = (ContractAction) CommonVars
				.getApplicationContext().getBean("contractAction");
		this.dzscAction = (DzscAction) CommonVars.getApplicationContext()
				.getBean("dzscAction");
		initialize();
	}

	/*
	 * public void setVisible(boolean b) { if (b) { if (projectType ==
	 * ProjectType.BCUS) { encAction.findImpExpRequestBill(new
	 * Request(CommonVars .getCurrUser())); } else if (projectType ==
	 * ProjectType.DZSC) { dzscAction.findImpExpRequestBill(new
	 * Request(CommonVars .getCurrUser())); } else if (projectType ==
	 * ProjectType.BCS) { contractAction.findImpExpRequestBill(new
	 * Request(CommonVars .getCurrUser())); } } super.setVisible(b); }
	 */

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setSize(931, 398);
		this.setTitle("进出口申请单");
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		cbbBeginDate.setDate(CommonVars.getBeginDate());
		jButton.setVisible(false);
	}

	/**
	 * 设置tree的可见性
	 */
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			TreePath treePath = treImport.getPathForRow(1);
			if (treePath != null) {
				treImport.setSelectionPath(treePath);
				treImport.scrollPathToVisible(treePath);
			}
			setState();
		}
		super.setVisible(isFlag);
		if (projectType == ProjectType.BCS) {
			btnMakeCustomsBill.setText("生成报关单");
		}
	}

	/**
	 * 
	 * @param projectType
	 * 
	 *            The projectType to set.
	 */
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public int getProjectType() {
		return projectType;
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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJPanel2(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * @return Returns the impExpRequestBill.
	 */
	public ImpExpRequestBill getImpExpRequestBill() {
		return impExpRequestBill;
	}

	/**
	 * @param impExpRequestBill
	 * 
	 *            The impExpRequestBill to set.
	 */
	public void setImpExpRequestBill(ImpExpRequestBill impExpRequestBill) {
		this.impExpRequestBill = impExpRequestBill;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(true);
			jSplitPane.setLeftComponent(getJTabbedPane());
			jSplitPane.setRightComponent(getJPanel());
			jSplitPane.setDividerLocation(130);
			jSplitPane.setDividerSize(0);
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("进口", null, getJScrollPane1(), null);
			jTabbedPane.addTab("出口", null, getJScrollPane2(), null);
			jTabbedPane
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {
							if (jTabbedPane.getSelectedIndex() == 0) {
								treeClick(1);
								TreePath treePath = treImport
										.getSelectionPath();
								if (treePath != null) {
									treImport.setSelectionPath(treePath);
									treImport.scrollPathToVisible(treePath);
									valueChange(treImport);
								}
								isOut = false;

							} else if (jTabbedPane.getSelectedIndex() == 1) {

								TreePath treePath = null;
								if (isFirstTime == true) {
									treePath = treExport.getPathForRow(1);
									isFirstTime = false;
								} else {
									treePath = treExport.getSelectionPath();
									valueChange(treExport);
								}
								if (treePath != null) {
									treExport.setSelectionPath(treePath);
									treExport.scrollPathToVisible(treePath);
								}
								isOut = true;
							}
						}
					});
		}
		return jTabbedPane;
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
			jToolBar.setFloatable(false);
			jToolBar.add(getBtnAdd());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnBrowse());
			jToolBar.add(getBtnDelete());
			// jToolBar.add(getBtnImportByMaterielBill());
			jToolBar.add(getBtnMakeCustomsBill());
			jToolBar.add(getBtnImport());
			jToolBar.add(getBtnAvailability());
			jToolBar.add(getBtnunAvailability());
			jToolBar.add(getBtnShowAll());
			jToolBar.add(getBtnOtherFunction());
			jToolBar.add(getBtnExit());
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
					addData();
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
					editData();
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
			btnDelete.setPreferredSize(new Dimension(60, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBaseImpExpRequestBill.this, "请选择要删除的数据行!",
								"提示", 0);
						return;
					}
					deleteData();
				}
			});
		}
		return btnDelete;
	}

	/**
	 * This method initializes btnMakeCustomsBill
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnMakeCustomsBill() {
		if (btnMakeCustomsBill == null) {
			btnMakeCustomsBill = new JButton();
			btnMakeCustomsBill.setText("生成报关单据");
			btnMakeCustomsBill.setPreferredSize(new Dimension(85, 30));
			btnMakeCustomsBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {

							if (projectType == ProjectType.BCS) {

								makeCustomsDeclaration();

							} else {

								getPmMakeApplyOrCustoms().show(
										btnMakeCustomsBill, 0,
										btnMakeCustomsBill.getHeight());

							}
						}
					});
		}
		return btnMakeCustomsBill;
	}

	/**
	 * This method initializes miBackPort
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getMiBackPort() {
		if (miBackPort == null) {
			miBackPort = new JMenuItem();
			miBackPort.setText("退港申请书");
			miBackPort.setSize(new Dimension(100, 30));
			miBackPort.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ImpExpRequestBill data = (ImpExpRequestBill) tableModel
							.getCurrentRow();
					if (data == null) {
						JOptionPane.showMessageDialog(
								FmBaseImpExpRequestBill.this, "请选择要退港的数据",
								"提示", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (isImport == true) {
						DgImpBackPortRequestBook dg = new DgImpBackPortRequestBook();
						dg.setImpExpRequestBill(data);
						dg.setTitle("进口---退港申请书");
						dg.setVisible(true);
					} else {
						DgExpBackPortRequestBook dg = new DgExpBackPortRequestBook();
						dg.setImpExpRequestBill(data);
						dg.setTitle("出口---退港申请书");
						dg.setVisible(true);
					}
				}
			});
		}
		return miBackPort;
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
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						editData();
					}
				}
			});
			jTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) jTable
									.getModel();
							if (tableModel == null) {
								return;
							}
							try {
								if (tableModel.getCurrentRow() != null) {
									setImpExpRequestBill((ImpExpRequestBill) tableModel
											.getCurrentRow());
									setState();
								}
							} catch (Exception cx) {

							}
						}
					});
			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					JTableListModel tableModel = (JTableListModel) jTable
							.getModel();
					if (tableModel == null) {
						return;
					}
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {
						ImpExpRequestBill obj = (ImpExpRequestBill) tableModel
								.getCurrentRow();
						if (obj.getIsCustomsBill() != null
								&& obj.getIsCustomsBill()) {
							obj.setIsCustomsBill(new Boolean(false));
						} else {
							obj.setIsCustomsBill(new Boolean(true));
						}
						obj = encAction.saveImpExpRequestBill(new Request(
								CommonVars.getCurrUser()), obj);
						tableModel.updateRow(obj);
					}

					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_P) {
						ImpExpRequestBill obj = (ImpExpRequestBill) tableModel
								.getCurrentRow();
						if (obj.getIsAvailability()) {
							obj.setIsAvailability(new Boolean(false));
						} else {
							obj.setIsAvailability(new Boolean(true));
						}
						obj = encAction.saveImpExpRequestBill(new Request(
								CommonVars.getCurrUser()), obj);
						tableModel.updateRow(obj);
					}
				}
			});
		}
		return jTable;
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
	 * @return Returns the impExpType.
	 */
	public int getType() {
		return impExpType;
	}

	/**
	 * @param impExpType
	 * 
	 *            The impExpType to set.
	 */
	public void setType(int type) {
		this.impExpType = type;
	}

	/**
	 * @return Returns the materielProductType.
	 */
	public int getMaterielProductType() {
		return materielProductType;
	}

	/**
	 * @param materielProductType
	 * 
	 *            The materielProductType to set.
	 */
	public void setMaterielProductType(int materielProductType) {
		this.materielProductType = materielProductType;
	}

	/**
	 * @return Returns the ediAction.
	 */
	public EncAction getEncAction() {
		return encAction;
	}

	/**
	 * @param ediAction
	 * 
	 *            The ediAction to set.
	 */
	public void setEdiAction(EncAction ediAction) {
		this.encAction = ediAction;
	}

	/**
	 * @return Returns the list.
	 */
	public List getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List list) {
		this.list = list;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * @param tableModel
	 * 
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * This method initializes treImport
	 * 
	 * @return javax.swing.JTree
	 */
	public JTree getTreImport() {
		if (treImport == null) {
			treImport = new JTree();
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("单据类型");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode(
					new ItemProperty(String.valueOf(ImpExpType.DIRECT_IMPORT),
							"料件进口"));
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.TRANSFER_FACTORY_IMPORT),
							"料件转厂"));
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.BACK_FACTORY_REWORK),
							"退厂返工"));
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.GENERAL_TRADE_IMPORT),
							"一般贸易进口"));

			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.IMPORT_EXG_BACK),
							"进料成品退换"));
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.IMPORT_REPAIR_MATERIAL),
							"修理物品"));

			root.add(root_1);
			root.add(root_2);
			root.add(root_3);
			root.add(root_4);
			root.add(root_5);
			root.add(root_6);

			treImport = new JTree(root);

			treImport
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							valueChange(treImport);
						}
					});
			ToolTipManager.sharedInstance().registerComponent(treImport);
			treImport.setCellRenderer(new DefaultTreeCellRenderer() {
				public Component getTreeCellRendererComponent(JTree tree,
						Object value, boolean sel, boolean expanded,
						boolean leaf, int row, boolean hasFocus) {

					super.getTreeCellRendererComponent(tree, value, sel,
							expanded, leaf, row, hasFocus);

					setToolTipText(value.toString());
					return this;
				}
			});
		}
		return treImport;
	}

	/**
	 * This method initializes treExport
	 * 
	 * @return javax.swing.JTree
	 */
	public JTree getTreExport() {
		if (treExport == null) {
			treExport = new JTree();
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("单据类型");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode(
					new ItemProperty(String.valueOf(ImpExpType.DIRECT_EXPORT),
							"成品出口"));
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.TRANSFER_FACTORY_EXPORT),
							"转厂出口"));
			DefaultMutableTreeNode root_3 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.BACK_MATERIEL_EXPORT),
							"退料出口"));
			DefaultMutableTreeNode root_4 = new DefaultMutableTreeNode(
					new ItemProperty(String.valueOf(ImpExpType.REWORK_EXPORT),
							"返工复出"));
			DefaultMutableTreeNode root_5 = new DefaultMutableTreeNode(
					new ItemProperty(String
							.valueOf(ImpExpType.REMIAN_MATERIAL_BACK_PORT),
							"边角料退港"));
			DefaultMutableTreeNode root_6 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES),
							"边角料内销"));
			DefaultMutableTreeNode root_7 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.GENERAL_TRADE_EXPORT),
							"一般贸易出口"));

			DefaultMutableTreeNode root_8 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.EXPORT_MATERIAL_REBACK),
							"修理物品复出"));

			DefaultMutableTreeNode root_9 = new DefaultMutableTreeNode(
					new ItemProperty(
							String.valueOf(ImpExpType.EXPORT_EXG_REBACK),
							"进料成品退换复出"));

			root.add(root_1);
			root.add(root_2);
			root.add(root_3);
			root.add(root_4);
			root.add(root_5);
			root.add(root_6);
			root.add(root_7);
			root.add(root_8);
			root.add(root_9);
			treExport = new JTree(root);
			treExport
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

						public void valueChanged(
								javax.swing.event.TreeSelectionEvent e) {
							valueChange(treExport);
						}
					});
			ToolTipManager.sharedInstance().registerComponent(treExport);
			treExport.setCellRenderer(new DefaultTreeCellRenderer() {
				public Component getTreeCellRendererComponent(JTree tree,
						Object value, boolean sel, boolean expanded,
						boolean leaf, int row, boolean hasFocus) {

					super.getTreeCellRendererComponent(tree, value, sel,
							expanded, leaf, row, hasFocus);

					setToolTipText(value.toString());
					return this;
				}
			});
		}
		return treExport;
	}

	/**
	 * 树改变 也就是 JTabPan 选择改变了
	 * 
	 * @param jTree
	 */
	private void valueChange(JTree jTree) {
		TreePath trePath = jTree.getSelectionPath();
		if (trePath == null) {
			return;
		}
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree
				.getSelectionPath().getLastPathComponent();
		if (selectedNode == null) {
			return;
		}
		if (!(selectedNode.getUserObject() instanceof ItemProperty)) {
			return;
		}
		ItemProperty item = (ItemProperty) selectedNode.getUserObject();
		treeClick(Integer.parseInt(item.getCode()));
		type = Integer.parseInt(item.getCode());

	}

	/**
	 * 树节点选择 显示数据,设置单据类型,设置料件成品标识
	 * 
	 * @param billType
	 */
	private void treeClick(int billType) {
		setType(billType);
		showDataByTreeSelectedCode(billType);
		setImport(EncCommon.isImport(billType));
		setMaterielProductType(EncCommon.getMaterielTypeByBillType(billType));
		setBillType(billType);
		setState();
	}

	/**
	 * 设置数据源 list ,初始化表格
	 * 
	 * @param selectedCode
	 */
	private void showDataByTreeSelectedCode(int selectedCode) {
		// this.setList(this.encAction.findImpExpRequestBillByType(new Request(
		// CommonVars.getCurrUser()), selectedCode, null, null, null,
		// null, null));
		setList(getDataSource());
		getMiBackPort().setEnabled(getBtnBackPortStateByCode(selectedCode));
		initTable(this.getList());
	}

	/**
	 * 获得退港申请单按钮的状态
	 * 
	 * @param selectedCode
	 * @return
	 */
	private boolean getBtnBackPortStateByCode(int selectedCode) {
		boolean isTrue = false;
		switch (selectedCode) {
		case ImpExpType.DIRECT_IMPORT:
			break;
		case ImpExpType.TRANSFER_FACTORY_IMPORT:
			break;
		case ImpExpType.BACK_FACTORY_REWORK:
			isTrue = true;
			break;
		case ImpExpType.GENERAL_TRADE_IMPORT:
			break;
		case ImpExpType.DIRECT_EXPORT:
			break;
		case ImpExpType.TRANSFER_FACTORY_EXPORT:
			break;
		case ImpExpType.BACK_MATERIEL_EXPORT:
			isTrue = true;
			break;
		case ImpExpType.REWORK_EXPORT:
			break;
		case ImpExpType.REMIAN_MATERIAL_BACK_PORT:
			isTrue = true;
			break;
		case ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES:
			break;
		case ImpExpType.GENERAL_TRADE_EXPORT:
			break;
		}
		return isTrue;
	}

	/**
	 * 初始化数据Table
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						String billType = "";
						String isCustomsBill = "";

						switch (projectType) {
						case ProjectType.BCS:
							billType = "已转报关单流水号";
							isCustomsBill = "完全转报关单";

							break;
						case ProjectType.DZSC:
							billType = "已转报关清单号码";
							isCustomsBill = "完全转报关清单";

							break;
						case ProjectType.BCUS:
							billType = "已转报关清单号码";
							isCustomsBill = "完全转报关清单";
							break;
						}
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						list.add(addColumn("单据号", "billNo", 110));
						list.add(addColumn("集装箱号码", "containerCode", 100));
						list.add(addColumn("生效日期", "beginAvailability", 70));
						list.add(addColumn("有效", "isAvailability", 40));
						list.add(addColumn(isCustomsBill, "isCustomsBill", 80));
						list.add(addColumn("项目总数", "itemCount", 60));
						list.add(addColumn("已转项目数", "toCustomCount", 70));
						list.add(addColumn(billType, "allBillNo", 120));
						if (projectType == ProjectType.DZSC
								|| projectType == ProjectType.BCS) {
							list.add(addColumn("合同号", "contractNo", 120));
						}
						list.add(addColumn("车次编号", "catNo", 100));
						// list.add(addColumn("仓库名称", "wareSet.name", 100));
						list.add(addColumn("客户/供应商名称", "scmCoc.name", 150));
						list.add(addColumn("运输工具", "conveyance", 60));
						list.add(addColumn("录入日期", "imputDate", 80));

						return list;
					}
				});
		jTable.getColumnModel().getColumn(5)
				.setCellRenderer(new MultiRenderer());
		jTable.getColumnModel().getColumn(4)
				.setCellRenderer(new MultiRenderer());
	}

	/**
	 * render table JchcckBox 列
	 */
	class MultiRenderer extends DefaultTableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null) {
				checkBox.setSelected(false);
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				checkBox.setSelected(Boolean.parseBoolean(value.toString()));
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(table.getBackground());
				if (isSelected) {
					checkBox.setForeground(table.getSelectionForeground());
					checkBox.setBackground(table.getSelectionBackground());
				} else {
					checkBox.setForeground(table.getForeground());
					checkBox.setBackground(table.getBackground());
				}
				return checkBox;
			}
			String str = (value == null) ? "" : value.toString();
			return super.getTableCellRendererComponent(table, str, isSelected,
					hasFocus, row, column);
		}
	}

	/**
	 * 新增
	 */
	private void addData() {

		DgImpExpRequestBill dg = new DgImpExpRequestBill();

		dg.setImpExpType(this.getType());// 单据类型

		dg.setProjectType(projectType);// 单据性质

		dg.setMaterielProductType(this.getMaterielProductType());// 是否是成品

		dg.setBillType(this.getBillType());

		dg.setImpExpRequestModel(this.tableModel);

		dg.setDataState(DataState.ADD);

		dg.setIsOut(isOut);

		dg.setVisible(true);

	}

	/**
	 * 修改
	 */
	private void editData() {
		if (tableModel.getCurrentRow() == null) {
			JOptionPane.showMessageDialog(this, "请选择你要修改的资料", "提示", 0);
			return;
		}
		DgImpExpRequestBill dg = new DgImpExpRequestBill();
		dg.setImpExpType(this.getType());
		dg.setProjectType(projectType);
		dg.setMaterielProductType(this.getMaterielProductType());
		dg.setImpExpRequestModel(this.tableModel);
		dg.setDataState(DataState.EDIT);
		dg.setIsOut(isOut);

		ImpExpRequestBill selectBill = (ImpExpRequestBill) this.tableModel
				.getCurrentRow();
		ImpExpRequestBill impExpRequestBill = encAction
				.findImpExpRequestBillById(
						new Request(CommonVars.getCurrUser()),
						selectBill.getId());

		tableModel.updateRow(impExpRequestBill);

		dg.setHeadImpExpRequestBill(impExpRequestBill);
		dg.setVisible(true);
	}

	/**
	 * 删除
	 */
	private void deleteData() {
		List lsit = new ArrayList();
		if (tableModel.getCurrentRows() != null) {
			if (JOptionPane.showConfirmDialog(this, "是否确定删除数据!", "提示", 0) != 0) {
				return;
			}
			List<ImpExpRequestBill> list = tableModel.getCurrentRows();
			for (ImpExpRequestBill data : list) {
				if (data.getIsAvailability()) {
					JOptionPane.showMessageDialog(this,
							"单据号:" + data.getBillNo() + "已经生效,不能删除!", "提示", 0);
					tableModel.deleteRows(lsit);
					return;
				}
				lsit.add(data);
				this.encAction.deleteImpExpRequestBill(
						new Request(CommonVars.getCurrUser()), data);

			}
			tableModel.deleteRows(lsit);
		} else {
			JOptionPane.showMessageDialog(this, "请选择要删除的数据行!", "提示", 0);
		}
	}

	/**
	 * 设置状态
	 */
	private void setState() {
		if (getImpExpRequestBill() != null) {
			btnEdit.setEnabled(impExpRequestBill.getIsAvailability() != null
					&& !impExpRequestBill.getIsAvailability().booleanValue());
			btnDelete.setEnabled(impExpRequestBill.getIsAvailability() != null
					&& !impExpRequestBill.getIsAvailability().booleanValue());
			if (impExpRequestBill.getIsAvailability() != null
					&& !impExpRequestBill.getIsAvailability().booleanValue()) {
				// 如果没生效
				btnAvailability.setEnabled(true);
				btnunAvailability.setEnabled(false);
				getMiSpit().setEnabled(
						impExpRequestBill.getIsCustomsBill() == null
								|| !impExpRequestBill.getIsCustomsBill());
			} else {
				getMiSpit().setEnabled(false);
				btnAvailability.setEnabled(false);
				btnunAvailability.setEnabled(true);
			}

			// btnEdit.setEnabled(impExpRequestBill.getIsAvailability()!=null &&
			// !impExpRequestBill.getIsAvailability().booleanValue());
		}
		if (projectType == ProjectType.BCS) {
			getMiMakeApplyToCustomsBill().setVisible(false);
			getSeparator3().setVisible(false);
		}
		if (projectType == ProjectType.DZSC) {
			// getMiMakeCustomsDeclaration().setVisible(false);
			// getSeparator3().setVisible(false);
		}
		if (projectType == ProjectType.BCUS) {
			btnMakeCustomsBill.setVisible(false);
		}
		getMiDeliveryCharge().setVisible(projectType == ProjectType.BCS);
		getSeparatorOtherFunction1().setVisible(projectType == ProjectType.BCS);// 分隔线
		getMiUpdateMaxVersion().setVisible(
				projectType == ProjectType.BCUS
						&& jTabbedPane.getSelectedIndex() == 1);
		getSeparatorOtherFunction().setVisible(
				projectType == ProjectType.BCUS
						&& jTabbedPane.getSelectedIndex() == 1);// 分隔线
		// miUpdateMaxVersion.setVisible(false);
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
					FmBaseImpExpRequestBill.this.doDefaultCloseAction();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes miUpdateMaxVersion
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getMiUpdateMaxVersion() {
		if (miUpdateMaxVersion == null) {
			miUpdateMaxVersion = new JMenuItem();
			miUpdateMaxVersion.setText("更新为最大版本号");
			miUpdateMaxVersion.setSize(new Dimension(100, 30));
			miUpdateMaxVersion
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (JOptionPane.showConfirmDialog(
									FmBaseImpExpRequestBill.this,
									"将更新所有申请单中版本号为空的物料,已转清单的除外！", "提示", 0) != 0) {
								return;
							}
							CommonProgress.showProgressDialog();
							CommonProgress.setMessage("系统正获更新数据，请稍后...");
							try {
								encAction.updateMaxVersion(new Request(
										CommonVars.getCurrUser()));
							} catch (Exception e1) {
								e1.printStackTrace();
								CommonProgress.closeProgressDialog();
								JOptionPane.showMessageDialog(
										FmBaseImpExpRequestBill.this,
										"更新数据失败！", "提示", 2);
								return;
							}
							CommonProgress.closeProgressDialog();
							JOptionPane.showMessageDialog(
									FmBaseImpExpRequestBill.this, "更新数据成功！",
									"提示", 2);
						}
					});
		}
		return miUpdateMaxVersion;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTreImport());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	public JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTreExport());
		}
		return jScrollPane2;
	}

	/**
	 * @param isImport
	 *            The isImport to set.
	 */
	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	public boolean getImport() {
		return this.isImport;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("辅助查询");
			btnSearch.setBounds(new Rectangle(363, 2, 91, 25));
			btnSearch.setPreferredSize(new Dimension(60, 30));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgQueryCondition dg = new DgQueryCondition();
					dg.setType(type);
					dg.setVisible(true);
					List list = dg.getLsResult();
					if (list != null) {
						initTable(list);
					}

				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnShowAll
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnShowAll() {
		if (btnShowAll == null) {
			btnShowAll = new JButton();
			btnShowAll.setText("全部显示");
			btnShowAll.setVisible(false);
			btnShowAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setList(encAction.findAllImpExpRequestBillByType(
							new Request(CommonVars.getCurrUser()), type));
					miBackPort.setEnabled(getBtnBackPortStateByCode(type));
					initTable(getList());
				}
			});
		}
		return btnShowAll;
	}

	/**
	 * This method initializes pmMakeApplyOrCustoms
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmMakeApplyOrCustoms() {
		if (pmMakeApplyOrCustoms == null) {
			pmMakeApplyOrCustoms = new JPopupMenu();
			pmMakeApplyOrCustoms
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			/*
			 * pmOtherFunction.add(getMiImportByMaterielBill()); Separator
			 * separator1 = new Separator();
			 * separator1.setForeground(Color.gray);
			 * pmOtherFunction.add(separator1);
			 */
			pmMakeApplyOrCustoms.add(getMiMakeApplyToCustomsBill());
			pmMakeApplyOrCustoms.add(getSeparator3());
			pmMakeApplyOrCustoms.add(getMiMakeCustomsDeclaration());
		}
		return pmMakeApplyOrCustoms;
	}

	/**
	 * This method initializes separator3
	 * 
	 * @return javax.swing.JSeparator
	 */
	private Separator getSeparator3() {
		if (separator3 == null) {
			separator3 = new Separator();
			separator3.setForeground(Color.gray);
		}
		return separator3;
	}

	/**
	 * This method initializes miImportByMaterielBill
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiImportByMaterielBill() {
		if (miImportByMaterielBill == null) {
			miImportByMaterielBill = new JMenuItem();
			miImportByMaterielBill.setText("导入");
			miImportByMaterielBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgMakeImpExpRequestBill dialog = new DgMakeImpExpRequestBill();
							dialog.setImpExpType(impExpType);
							dialog.setVisible(true);
						}
					});
		}
		return miImportByMaterielBill;
	}

	/**
	 * This method initializes miMakeApplyToCustomsBill
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiMakeApplyToCustomsBill() {
		if (miMakeApplyToCustomsBill == null) {
			miMakeApplyToCustomsBill = new JMenuItem();
			miMakeApplyToCustomsBill.setText("生成报关清单");
			miMakeApplyToCustomsBill
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							switch (projectType) {
							// case ProjectType.BCUS:
							// MakeCusomsDeclarationParam para = new
							// MakeCusomsDeclarationParam();
							// para.setImpExpType(impExpType);
							// para.setMaterielType(EncCommon
							// .getMaterielTypeByBillType(impExpType));
							// para.setImportFlag(EncCommon
							// .isImport(impExpType));
							// para.setImpExpType(impExpType);
							// DgMakeApplyToCustoms dialog = new
							// DgMakeApplyToCustoms();
							// dialog.setPara(para);
							// // dialog.setTableMode(getTableModel());
							// dialog.setVisible(true);
							//
							// setState();
							// break;
							case ProjectType.DZSC:
								DgDzscMakeCustomsListNew dzscdialog = new DgDzscMakeCustomsListNew();
								dzscdialog.setImpExpType(getType());
								dzscdialog
										.setMaterielProductType(materielProductType);
								dzscdialog.setImpExpTypeIxp(getType());
								dzscdialog.setBillType(getBillType());
								dzscdialog.setImportGoods(isImport);
								// dzscdialog.setNewBgd(false);
								dzscdialog.setTableMode(getTableModel());
								dzscdialog.setVisible(true);
								setState();

								break;
							}
						}
					});
		}
		return miMakeApplyToCustomsBill;
	}

	private JMenuItem miImportByMaterielBill = null;

	private JMenuItem miMakeApplyToCustomsBill = null;

	private JMenuItem miMakeCustomsDeclaration = null;

	private JButton btnImport = null;

	private JButton btnBrowse = null;

	private JPopupMenu pmInputData = null;

	private JMenuItem miBillCenter = null;

	private JMenuItem miTextFile = null;

	private JButton btnAvailability = null;

	private JButton btnunAvailability = null;

	private JMenuItem miDeliveryCharge = null;

	private JMenuItem miSpit = null;

	private JPanel jPanel2 = null;

	private JButton jButton = null;

	/**
	 * This method initializes miMakeCustomsDeclaration
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiMakeCustomsDeclaration() {
		if (miMakeCustomsDeclaration == null) {
			miMakeCustomsDeclaration = new JMenuItem();
			miMakeCustomsDeclaration.setText("生成报关单");
			miMakeCustomsDeclaration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							makeCustomsDeclaration();
						}
					});
		}
		return miMakeCustomsDeclaration;
	}

	/**
	 * 生成报关单
	 * 
	 */
	private void makeCustomsDeclaration() {

		switch (projectType) {

		case ProjectType.BCS:

			if (getTableModel().getList() == null) {

				JOptionPane.showMessageDialog(FmBaseImpExpRequestBill.this,
						"没有生效的申请单可以执行!", "提示", 2);
				return;

			}

			DgMakeBcsCustomsDeclaration bcsDialog = new DgMakeBcsCustomsDeclaration();

			bcsDialog.setImpExpType(getType());

			bcsDialog.setMaterielProductType(materielProductType);

			bcsDialog.setImportGoods(isImport);

			bcsDialog.setTableMode(getTableModel());

			bcsDialog
					.setHeadImpExpRequestBill((ImpExpRequestBill) getTableModel()
							.getCurrentRow());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date date = null;

			try {

				date = sdf.parse((String) getTableModel().getValueAt(
						getTableModel().getCurrRowCount(), 3));

			} catch (Exception e) {
				e.printStackTrace();
			}

			bcsDialog.setDate(date);

			bcsDialog.setVisible(true);

			break;

		case ProjectType.DZSC:
			DgMakeDzscCustomsDeclaration dzscDialog = new DgMakeDzscCustomsDeclaration();
			dzscDialog.setImpExpType(getType());
			dzscDialog.setMaterielProductType(materielProductType);
			dzscDialog.setImportGoods(isImport);
			// bcsDialog.setNewBgd(true);
			dzscDialog.setTableMode(getTableModel());
			dzscDialog.setVisible(true);
			break;
		default:
			break;
		}
		this.btnRefresh.doClick();
	}

	/**
	 * 状态
	 * 
	 */
	private void state() {
		switch (projectType) {
		case ProjectType.BCUS:
			miMakeCustomsDeclaration.setEnabled(false);
			miDeliveryCharge.setVisible(false);
			break;
		case ProjectType.BCS:
			miMakeCustomsDeclaration.setEnabled(true);
			miDeliveryCharge.setVisible(true);
			break;
		}
	}

	/**
	 * btnOtherFunction This method initializes btnImport
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImport() {
		if (btnImport == null) {
			btnImport = new JButton();
			btnImport.setText("导入");
			btnImport.setPreferredSize(new Dimension(60, 30));
			btnImport.setForeground(java.awt.Color.blue);
			btnImport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPmInputData().show(btnImport, 0, btnImport.getHeight());

					// DgMakeImpExpRequestBill dialog = new
					// DgMakeImpExpRequestBill();
					// dialog.setImpExpType(impExpType);
					// dialog.setVisible(true);
				}
			});
		}
		return btnImport;
	}

	/**
	 * This method initializes btnBrowse
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton();
			btnBrowse.setText("浏览");
			btnBrowse.setPreferredSize(new Dimension(60, 30));
			btnBrowse.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBaseImpExpRequestBill.this, "请选择你要显示的资料",
								"提示", 0);
						return;
					}
					editData();
				}
			});
		}
		return btnBrowse;
	}

	/**
	 * This method initializes pmInputData
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmInputData() {
		if (pmInputData == null) {
			pmInputData = new JPopupMenu();
			pmInputData.setSize(new Dimension(110, 36));
			pmInputData
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pmInputData.add(getMiBillCenter());
			Separator separatorinput = new Separator();
			separatorinput.setForeground(Color.gray);
			pmInputData.add(separatorinput);
			pmInputData.add(getMiTextFile());
		}
		return pmInputData;
	}

	/**
	 * This method initializes miBillCenter
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiBillCenter() {
		if (miBillCenter == null) {
			miBillCenter = new JMenuItem();
			miBillCenter.setSize(new Dimension(89, 29));
			miBillCenter.setText("单据中心导入");
			miBillCenter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgMakeImpExpRequestBill dialog = new DgMakeImpExpRequestBill();
					dialog.setImpExpType(impExpType);
					dialog.setVisible(true);
				}
			});
		}
		return miBillCenter;
	}

	/**
	 * This method initializes miTextFile
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiTextFile() {
		if (miTextFile == null) {
			miTextFile = new JMenuItem();
			miTextFile.setSize(new Dimension(81, 28));
			miTextFile.setText("文本文件导入");
			miTextFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputImpExpRequestBill dg = new DgInputImpExpRequestBill();
					dg.setInport(isImport);
					dg.setBillType(billType);
					dg.setProjectType(projectType);
					dg.setTableModelHead(getTableModel());
					dg.setVisible(true);
				}
			});
		}
		return miTextFile;
	}

	/**
	 * This method initializes btnAvailability
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAvailability() {
		if (btnAvailability == null) {
			btnAvailability = new JButton();
			btnAvailability.setText("生效");
			btnAvailability.setPreferredSize(new Dimension(60, 30));
			btnAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmBaseImpExpRequestBill.this,
										"请选择要生效的数据行!", "提示", 0);
								return;
							}

							List<ImpExpRequestBill> list = tableModel
									.getCurrentRows();
							AvailabilityThread thread = new AvailabilityThread();
							thread.setList(list);
							thread.start();
						}
					});
		}
		return btnAvailability;
	}

	/**
	 * 
	 * 生效和失效记录并设置状态
	 */
	private void beginAvailability(boolean isAvailability,
			ImpExpRequestBill data) {
		try {
			data.setIsAvailability(Boolean.valueOf(isAvailability));
			data = this.encAction.saveImpExpRequestBill(
					new Request(CommonVars.getCurrUser()), data);
			// this.tableModel.updateRow(data);
			// setState();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "数据可能有误,生效失败!", "提示", 0);
		}
	}

	/**
	 * 执行生效数据多线程
	 * 
	 * @author Administrator
	 * 
	 */
	class AvailabilityThread extends Thread {
		List<ImpExpRequestBill> list = new ArrayList();

		public void run() {
			CommonProgress.showProgressDialog(FmBaseImpExpRequestBill.this);
			CommonProgress.setMessage("系统正在生效数据，请稍后...");
			List tempList = new ArrayList();
			for (ImpExpRequestBill data : list) {
				List list = encAction.findImpExpCommodityInfoByCheck(
						new Request(CommonVars.getCurrUser()), data.getId());

				// 判断单据号为空不能生效; 2013.8.19,y-w-g
				if (data.getBillNo() == null || "".equals(data.getBillNo())) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "单据号不能为空!");
					return;
				}

				if (list.size() > 0) {
					String info = "";
					for (int i = 0; i < list.size(); i++) {
						info += list.get(i) + "\n";
					}
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null,
							"单据号为" + data.getBillNo()
									+ "商品信息中有空的数据,\n进出口申请记录不能生效!!" + "\n"
									+ info, "非法数据!!", 1);
					return;

				}
				List dataSource = encAction.findImpExpCommodityInfo(
						new Request(CommonVars.getCurrUser()), data.getId());

				if (dataSource.size() == 0) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null,
							"单据号为" + data.getBillNo()
									+ "商品信息中没有数据,\n进出口申请记录不能生效!!", "非法数据!!", 1);
					return;

				}
				beginAvailability(true, data);
				tempList.add(data);
			}
			CommonProgress.closeProgressDialog();
			tableModel.updateRows(tempList);
			JOptionPane.showMessageDialog(FmBaseImpExpRequestBill.this,
					"单据生效成功!", "提示!", JOptionPane.INFORMATION_MESSAGE);
		}

		/**
		 * @return the list.
		 */
		public List<ImpExpRequestBill> getList() {
			return list;
		}

		/**
		 * @param list
		 * 
		 *            The list to set.
		 */
		public void setList(List<ImpExpRequestBill> list) {
			this.list = list;
		}
	}

	/**
	 * This method initializes btnunAvailability
	 * 
	 * @return javax.swing.JButton
	 */

	private JButton getBtnunAvailability() {
		if (btnunAvailability == null) {
			btnunAvailability = new JButton();
			btnunAvailability.setText("撤消生效");
			btnunAvailability.setPreferredSize(new Dimension(60, 30));
			btnunAvailability
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (tableModel.getCurrentRow() == null) {
								JOptionPane.showMessageDialog(
										FmBaseImpExpRequestBill.this,
										"请选择要取消生效的数据行!", "提示", 0);
								return;
							}
							List<ImpExpRequestBill> list = tableModel
									.getCurrentRows();
							List tempList = new ArrayList();
							if (projectType == ProjectType.BCS) {
								Integer rt = encAction
										.findMakeBcsCustomsDeclarationCountByListId(
												new Request(CommonVars
														.getCurrUser()), list);
								if (rt != null && rt > 0) {
									JOptionPane.showMessageDialog(
											FmBaseImpExpRequestBill.this,
											"在进出口申请单中有数据已转报关单,不能撤消生效!", "警告!",
											JOptionPane.INFORMATION_MESSAGE);
									tableModel.updateRows(tempList);
									return;
								}
								for (ImpExpRequestBill data : list) {
									if (!data.getIsAvailability()) {
										continue;
									}
									beginAvailability(false, data);
									tempList.add(data);
								}
							} else {
								for (ImpExpRequestBill data : list) {
									ImpExpRequestBill impExpRequestBill = encAction
											.getImpExpRequestBill(new Request(
													CommonVars.getCurrUser()),
													data.getBillNo());

									if (impExpRequestBill.getToCustomCount() != null
											&& impExpRequestBill
													.getToCustomCount() > 0) {
										JOptionPane
												.showMessageDialog(
														FmBaseImpExpRequestBill.this,
														"在进出口申请单中有数据已转报关清单,不能撤消生效!",
														"警告!",
														JOptionPane.INFORMATION_MESSAGE);
										tableModel.updateRows(tempList);
										return;
									}
									if (!impExpRequestBill.getIsAvailability()) {
										continue;
									}
									beginAvailability(false, impExpRequestBill);
									tempList.add(impExpRequestBill);
								}
							}

							tableModel.updateRows(tempList);
							JOptionPane.showMessageDialog(
									FmBaseImpExpRequestBill.this, "单据取消生效成功!",
									"提示!", JOptionPane.INFORMATION_MESSAGE);

						}
					});
		}
		return btnunAvailability;
	}

	/**
	 * @return the billType.
	 */
	public int getBillType() {
		return this.billType;
	}

	/**
	 * @param billType
	 * 
	 *            The billType to set.
	 */
	public void setBillType(int billType) {
		this.billType = billType;
	}

	/**
	 * 为进口/出口
	 * 
	 * @return
	 */
	public Boolean getIsOut() {
		return isOut;
	}

	/**
	 * 为进口/出口
	 * 
	 * @param isOut
	 */
	public void setIsOut(Boolean isOut) {
		this.isOut = isOut;
	}

	/**
	 * This method initializes miDeliveryCharge
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getMiDeliveryCharge() {
		if (miDeliveryCharge == null) {
			miDeliveryCharge = new JMenuItem();
			miDeliveryCharge.setText("送货单");
			miDeliveryCharge.setSize(new Dimension(100, 30));
			miDeliveryCharge
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgImpExpGoodsBill dg = new DgImpExpGoodsBill();
							dg.setVisible(true);

						}
					});
		}
		return miDeliveryCharge;
	}

	/**
	 * This method initializes miSpit
	 * 
	 * @return javax.swing.JButton
	 */
	private JMenuItem getMiSpit() {
		if (miSpit == null) {
			miSpit = new JMenuItem();
			miSpit.setSize(new Dimension(100, 30));
			miSpit.setText("拆分");
			miSpit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// tableModel
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBaseImpExpRequestBill.this, "请选择申请单!", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					ImpExpRequestBill impExpRequestBill = (ImpExpRequestBill) tableModel
							.getCurrentRow();
					DgBcusImpexpRequestbillSplit dg = new DgBcusImpexpRequestbillSplit();
					dg.setImpExpRequestBill(impExpRequestBill);
					dg.setVisible(true);
					if (dg.isOk) {
						setList(encAction.findAllImpExpRequestBillByType(
								new Request(CommonVars.getCurrUser()), type));
						miBackPort.setEnabled(getBtnBackPortStateByCode(type));
						initTable(getList());
					}
				}
			});
		}
		return miSpit;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			lbTo = new JLabel();
			lbTo.setBounds(new Rectangle(172, 6, 17, 21));
			lbTo.setText("到");
			lbBeginDate = new JLabel();
			lbBeginDate.setBounds(new Rectangle(14, 3, 55, 26));
			lbBeginDate.setText("录入日期");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new Dimension(550, 30));
			jPanel1.add(lbBeginDate, null);
			jPanel1.add(getCbbBeginDate(), null);
			jPanel1.add(lbTo, null);
			jPanel1.add(getCbbEndDate(), null);
			jPanel1.add(getBtnRefresh(), null);
			jPanel1.add(getBtnSearch(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						jButton.setVisible(false);
					} else {
						jButton.setVisible(true);
					}
				}
			});
		}
		return jPanel1;
	}

	/**
	 * This method initializes cbbBeginDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbBeginDate() {
		if (cbbBeginDate == null) {
			cbbBeginDate = new JCalendarComboBox();
			cbbBeginDate.setBounds(new Rectangle(69, 4, 100, 24));
		}
		return cbbBeginDate;
	}

	/**
	 * This method initializes cbbEndDate
	 * 
	 * @return com.bestway.ui.winuicontrol.calendar.JCalendarComboBox
	 */
	private JCalendarComboBox getCbbEndDate() {
		if (cbbEndDate == null) {
			cbbEndDate = new JCalendarComboBox();
			cbbEndDate.setBounds(new Rectangle(193, 3, 100, 24));
		}
		return cbbEndDate;
	}

	/**
	 * This method initializes btnRefresh
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnRefresh() {
		if (btnRefresh == null) {
			btnRefresh = new JButton();
			btnRefresh.setBounds(new Rectangle(298, 2, 60, 25));
			btnRefresh.setText("刷新");
			btnRefresh.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = getDataSource();
					initTable(list);
					// miBackPort.setEnabled(getBtnBackPortStateByCode(type));
					setState();
				}
			});
		}
		return btnRefresh;
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	private List getDataSource() {
		if (projectType == ProjectType.BCUS) {
			encAction.findImpExpRequestBill(new Request(CommonVars
					.getCurrUser()));

		} else if (projectType == ProjectType.DZSC) {
			dzscAction.findImpExpRequestBill(new Request(CommonVars
					.getCurrUser()));

		} else if (projectType == ProjectType.BCS) {
			contractAction.findImpExpRequestBill(new Request(CommonVars
					.getCurrUser()));
		}
		List list = encAction.findImpExpRequestBillByTypeAndImputDate(
				new Request(CommonVars.getCurrUser()), getType(),
				cbbBeginDate.getDate(), cbbEndDate.getDate());
		return list;
	}

	/**
	 * This method initializes separatorOtherFunction
	 * 
	 * @return javax.swing.JSeparator
	 */
	private Separator getSeparatorOtherFunction() {
		if (separatorOtherFunction == null) {
			separatorOtherFunction = new Separator();
			separatorOtherFunction.setForeground(Color.gray);
		}
		return separatorOtherFunction;
	}

	/**
	 * This method initializes separatorOtherFunction1
	 * 
	 * @return javax.swing.JSeparator
	 */
	private Separator getSeparatorOtherFunction1() {
		if (separatorOtherFunction1 == null) {
			separatorOtherFunction1 = new Separator();
			separatorOtherFunction1.setForeground(Color.gray);
		}
		return separatorOtherFunction1;
	}

	/**
	 * This method initializes pmOtherFunction
	 * 
	 * @return javax.swing.JPopupMenu
	 */
	private JPopupMenu getPmOtherFunction() {
		if (pmOtherFunction == null) {
			pmOtherFunction = new JPopupMenu();
			pmOtherFunction.setSize(new Dimension(110, 36));
			pmOtherFunction
					.setBorder(javax.swing.BorderFactory
							.createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
			pmOtherFunction.add(getMiCopy());// 转抄
			Separator separator = new Separator();
			separator.setForeground(Color.gray);
			pmOtherFunction.add(separator);// 分隔线
			pmOtherFunction.add(getMiSpit());// 拆分
			Separator separator1 = new Separator();
			separator1.setForeground(Color.gray);
			pmOtherFunction.add(separator1);// 分隔线
			pmOtherFunction.add(getMiBackPort());// 退港申请书
			pmOtherFunction.add(getSeparatorOtherFunction());// 分隔线
			pmOtherFunction.add(getMiUpdateMaxVersion());// 更新为最大版本号
			pmOtherFunction.add(getSeparatorOtherFunction1());// 分隔线
			pmOtherFunction.add(getMiDeliveryCharge());// 送货单

		}
		return pmOtherFunction;
	}

	/**
	 * This method initializes btnOtherFunction
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOtherFunction() {
		if (btnOtherFunction == null) {
			btnOtherFunction = new JButton();
			btnOtherFunction.setText("其他功能");
			btnOtherFunction.setPreferredSize(new Dimension(60, 30));
			btnOtherFunction.setForeground(java.awt.Color.blue);
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
	 * This method initializes miCopy
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getMiCopy() {
		if (miCopy == null) {
			miCopy = new JMenuItem();
			miCopy.setSize(new Dimension(100, 30));
			miCopy.setText("转抄                        ");
			miCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModel.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FmBaseImpExpRequestBill.this, "请选择申请单!", "提示！",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					ImpExpRequestBill impExpRequestBill = (ImpExpRequestBill) tableModel
							.getCurrentRow();
					String billNo = null;
					CompanyOther other = CommonVars.getOther();
					if (other != null
							&& other.getIsAutoGetDjNo() != null
							&& other.getIsAutoGetDjNo().equals(
									Boolean.valueOf(true))) {
						billNo = String.valueOf(encAction.getMaxBillNoByType(
								new Request(CommonVars.getCurrUser()),
								getBillType()));
					}

					if (JOptionPane
							.showConfirmDialog(FmBaseImpExpRequestBill.this,
									"是否也转抄表体资料?", "提示", 0) == 0) {
						ImpExpRequestBill newImpExpRequestBill = encAction
								.copyImpExpRequestBillAndCommodityInfo(
										new Request(CommonVars.getCurrUser()),
										impExpRequestBill, true, billNo);
						tableModel.addRow(newImpExpRequestBill);
					} else {
						ImpExpRequestBill newImpExpRequestBill = encAction
								.copyImpExpRequestBillAndCommodityInfo(
										new Request(CommonVars.getCurrUser()),
										impExpRequestBill, false, billNo);
						tableModel.addRow(newImpExpRequestBill);
					}
				}
			});
		}
		return miCopy;
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
			jPanel2.add(getJPanel1(), BorderLayout.NORTH);
			jPanel2.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	class saveData extends Thread {

		private List selectList = null;

		public saveData(List selectList) {
			this.selectList = selectList;
		}

		@Override
		public void run() {
			try {
				CommonStepProgress.showStepProgressDialog();
				CommonStepProgress.setStepProgressMaximum(selectList.size());
				CommonStepProgress.setStepMessage("系统正在更新运输方式，请稍后...");

				for (int i = 0; i < selectList.size(); i++) {
					ImpExpRequestBill impExpBill = (ImpExpRequestBill) selectList
							.get(i);
					CommonStepProgress.setStepProgressValue(i);
					System.out.println("===" + impExpBill.getTransferMode());
					if (impExpBill.getTransferMode() != null) {
						impExpBill.setTransfMode(impExpBill.getTransferMode());
						impExpBill = encAction.saveImpExpRequestBill(
								new Request(CommonVars.getCurrUser()),
								impExpBill);
						tableModel.updateRow(impExpBill);
					}
				}

				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(null, "更新运输方式完成！", "提示!",
						JOptionPane.INFORMATION_MESSAGE);

			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				e.printStackTrace();
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(468, 4, 206, 23));
			jButton.setText("更新运输方式栏位到新栏位中");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (JOptionPane.showConfirmDialog(
							FmBaseImpExpRequestBill.this, "你确定要更新运输方式栏位吗?",
							"确认", 0) != 0) {
						return;
					}

					List list = encAction.findImpExpRequestBill1(new Request(
							CommonVars.getCurrUser()));
					new saveData(list).start();
				}
			});
		}
		return jButton;
	}
}
