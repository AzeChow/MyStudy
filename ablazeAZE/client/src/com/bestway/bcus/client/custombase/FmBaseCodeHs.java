/*
 * Created on 2004-6-10
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bestway.bcus.client.CustomsBaseHttpClientUtil;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonQuery;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.CustomBaseClassName;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.LicenseDocu;
import com.bestway.bcus.dataimport.action.DataImportAction;
import com.bestway.bcus.system.action.TcsParametersAction;
import com.bestway.client.custom.common.JTraceFileChooser;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CspZIPUtil;
import com.bestway.common.Request;
import com.bestway.jptds.common.AppException;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author eidt by cjb 商品编码管理 // change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
@SuppressWarnings("unchecked")
public class FmBaseCodeHs extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	/**
	 * 商品编码类型树
	 */
	private JTree treeType = null;

	private JToolBar jToolBar = null;

	/**
	 * 显示商品编码表格
	 */
	private JTable tbCustomer = null;

	private JScrollPane jScrollPane = null;

	/**
	 * 显示商品编码表格模型
	 */
	private JTableListModel tmCustomer = null;

	/**
	 * 商品编码类型树模型
	 */
	private DefaultTreeModel trmType = null; // @jve:decl-index=0:

	/**
	 * 树结点序号
	 */
	private int treeIndex = 0;

	/**
	 * 商品编码操作接口
	 */
	private CustomBaseAction customBaseAction = null; // @jve:decl-index=0:

	/**
	 * 新增
	 */
	private JButton btnAdd = null;

	/**
	 * 删除
	 */
	private JButton btnDelete = null;

	/**
	 * 编辑
	 */
	private JButton btnEdit = null;

	/**
	 * 关闭
	 */
	private JButton btnExit = null;

	private JToolBar jToolBar1 = null;

	/**
	 * 商品编码数据集合
	 */
	private List dataSource = null;

	private JPanel jpanel2 = null;

	private JPanel jpanel1 = null;

	/**
	 * 存放海关商品编码资料
	 */
	private CustomsComplex customsComplex = null;

	/**
	 * 报关参数
	 */
	private LicenseDocu licenseDocu = null;

	/**
	 * 商品编码
	 */
	private Complex complex = null;

	/**
	 * 数据导入接口
	 */
	private DataImportAction dataImportAction = null;

	private JPanel pnCommonQuery = null;

	private JComboBox jComboBox = null;

	/**
	 * 商品编码查询字段
	 */
	private JTextField tfQueryValue = null;

	/**
	 * 查询
	 */
	private JButton btnSearch = null;

	private TcsParametersAction tcsParametersAction = null;

	// 解压后 文件存放的位置
	private String localFolder;

	/**
	 * This is the default constructor
	 */
	public FmBaseCodeHs() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		customBaseAction.checkBaseCodeHsAuthority(new Request(CommonVars
				.getCurrUser()));
		dataImportAction = (DataImportAction) CommonVars
				.getApplicationContext().getBean("dataImportAction");
		tcsParametersAction = (TcsParametersAction) CommonVars
				.getApplicationContext().getBean("tcsParametersAction");
		initialize();
		setState();
		// NodeRenderer renderer = new NodeRenderer();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJSplitPane());
		this.setSize(1276, 498);
		this.setTitle("商品编码");
		this.setHelpId("baseCodeHs");
		this.validate();
		this.pack();
		this.setTreeIndex(0);

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
			jContentPane.add(getJTree(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJpanel2() {
		if (jpanel2 == null) {
			jpanel2 = new JPanel(new BorderLayout());
		}
		jpanel2.add(getJToolBar(), java.awt.BorderLayout.NORTH);
		return jpanel2;

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJpanel1() {
		if (jpanel1 == null) {
			jpanel1 = new JPanel(new BorderLayout());
			jpanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jpanel1.add(getJToolBar1(), java.awt.BorderLayout.NORTH);

		}
		return jpanel1;
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
			this.getJSplitPane().setResizeWeight(0.9);
			// this.getJSplitPane().setDividerSize(7);
			jSplitPane.setLeftComponent(getJContentPane());
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setDividerLocation(100);
		}
		return jSplitPane;
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
	private JPanel getJPanel2() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			// jPanel.add(getJpanel2(),java.awt.BorderLayout.CENTER);
			jPanel.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jPanel.add(getJpanel1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * This method initializes jTree
	 * 
	 * 
	 * 
	 * @return javax.swing.JTree
	 * 
	 */
	private JTree getJTree() {
		if (treeType == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("商品编码");
			DefaultMutableTreeNode root_0 = new DefaultMutableTreeNode("海关商品编码");
			DefaultMutableTreeNode root_1 = new DefaultMutableTreeNode("自用商品编码");
			DefaultMutableTreeNode root_2 = new DefaultMutableTreeNode(
					"领证商品备注表");
			// DefaultMutableTreeNode root_3 = new
			// DefaultMutableTreeNode("许可证代码");
			root.add(root_0);
			root.add(root_1);
			root.add(root_2);
			// root.add(root_3);
			treeType = new JTree(root);
			// 设置颜色 如果被选中是绿色，不被选中则是红色
			// DefaultTreeCellRenderer
			// cellRenderer=(DefaultTreeCellRenderer)jTree.getCellRenderer();
			// cellRenderer.setTextNonSelectionColor(Color.red);
			// cellRenderer.setTextSelectionColor(Color.blue);
			treeType.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {

				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeType
							.getLastSelectedPathComponent();

					if (selectedNode == null) {

						return;

					}

					DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode
							.getParent();

					if (parent != null) {

						int selectedIndexValue = parent.getIndex(selectedNode);

						setTreeIndex(selectedIndexValue);

						getData();

					}

					setState();

				}
			});

			treeType.setCellRenderer(new NodeRenderer());

		}
		return treeType;
	}

	/**
	 * 商品编码树结点渲染器
	 * 
	 * @author Administrator
	 * 
	 */
	class NodeRenderer extends DefaultTreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getTreeCellRendererComponent(JTree jTtree,
				Object value, boolean selected, boolean expanded, boolean leaf,
				int row, boolean hasFocus)

		{
			super.getTreeCellRendererComponent(treeType, value, selected,
					expanded, leaf, row, hasFocus);

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			if (node.getUserObject().toString().trim().equals("自用商品编码")) // 条件满足
			{

				setForeground(Color.BLUE);
			}
			return this;
		}
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
			// jToolBar.setPreferredSize(new Dimension(650, 34));
			jToolBar.add(getBtnAdd());
			// jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnEdit());
			jToolBar.add(getBtnDelete());
			jToolBar.add(getbtnCheck());
			jToolBar.add(getJButton());
			jToolBar.add(getBnSerch());
			jToolBar.add(getBtnEnable());
			jToolBar.add(getBtnDisable());
			jToolBar.add(getBtnFTP());
			jToolBar.add(getLocaleUpdate());
			jToolBar.add(getBtnUpdate());
			jToolBar.add(getBtnModifyLowrate());
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
		if (tbCustomer == null) {
			tbCustomer = new JTable();
		}
		return tbCustomer;
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
	 * @return Returns the iBaseCode.
	 */
	public CustomBaseAction getIBaseCode() {
		return customBaseAction;
	}

	/**
	 * @param baseCode
	 *            The iBaseCode to set.
	 */
	public void setIBaseCode(CustomBaseAction baseCode) {
		customBaseAction = baseCode;
	}

	/**
	 * @return Returns the model.
	 */
	public DefaultTreeModel getModel() {
		return trmType;
	}

	/**
	 * @param model
	 *            The model to set.
	 */
	public void setModel(DefaultTreeModel model) {
		this.trmType = model;
	}

	/**
	 * @return Returns the sindex.
	 */
	public int getTreeIndex() {
		return this.treeIndex;
	}

	/**
	 * @param sindex
	 *            The sindex to set.
	 */
	public void setTreeIndex(int treeIndex) {
		this.treeIndex = treeIndex;
	}

	/**
	 * 得到所有数据
	 */
	public void getData() {

		// 被选择 第一个 树节点
		if (treeIndex == 0) {

			pnCommonQueryPage = getPnCommonQueryPage();

			if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {

				pnCommonQueryPage.getCbbQueryField().removeAllItems();

				pnCommonQueryPage.setFirstHasDataInit(true);

			}

			getPnCommonQueryPage().setInitState();
			// List list= FmBaseCodeHs.this.getDataSources(index, length,
			// property, value, isLike);}
			// initTable(dataSource, treeIndex);
		}

		else if (treeIndex == 1) {

			pnCommonQueryPage = getPnCommonQueryPage();

			if (pnCommonQueryPage.getCbbQueryField().getItemCount() > 0) {

				pnCommonQueryPage.getCbbQueryField().removeAllItems();

				pnCommonQueryPage.setFirstHasDataInit(true);

			}

			getPnCommonQueryPage().setInitState();

			// initTable(dataSource, treeIndex);
			// getPnCommonQueryPage().setInitState();
			// initTable(dataSource, treeIndex);
		} else if (treeIndex == 2) {
			List dataSource = CustomBaseList.getInstance().getLicensenotes(); // 领证商品备注表
			initTable(dataSource, treeIndex);
		}
		// else if (treeIndex == 3) {
		// List list = CustomBaseList.getInstance().getLicensedocus();
		// initTable(list, treeIndex);
		// }
	}

	/**
	 * 初始化表格
	 */
	private JTableListModel initTable(List list, final int treeIndex1) {

		if (list == null) {

			list = new Vector();

		}

		// 商品类别
		if (treeIndex1 == 0) {

			tmCustomer = new JTableListModel(tbCustomer, list,
					new JTableListModelAdapter() {

						public List<JTableListColumn> InitColumns() {

							List<JTableListColumn> list = new Vector<JTableListColumn>();

							list.add(addColumn("编码", "code", 90));

							list.add(addColumn("商品名称", "name", 200));

							list.add(addColumn("第一法定单位", "firstUnit.name", 100));

							list.add(addColumn("第二法定单位", "secondUnit.name", 100));

							list.add(addColumn("优惠税率", "lowrate", 50, false));

							list.add(addColumn("普通税率", "highrate", 50, false));

							list.add(addColumn("许可证代码", "ccontrol", 85));

							list.add(addColumn("消费税率", "regRate", 70, false));

							list.add(addColumn("出口税率", "outRate", 70, false));

							list.add(addColumn("增值税率", "taxRate", 70, false));

							list.add(addColumn("备注", "note", 70));

							// list.add(addColumn("许可证代码注解", "licExp", 200));

							return list;

						}
					});
			// jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tbCustomer.getColumnModel().getColumn(7)
					.setCellRenderer(new ToolTipRenderer());

			// 商品类别
		} else if (treeIndex1 == 1) {
			JTableListModelAdapter jTableListModelAdapter = new JTableListModelAdapter() {
				public List<JTableListColumn> InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();

					// list.add(addColumn("是否有更新", "isChange", 90));
					list.add(addColumn("编码", "code", 90));
					// list.add(addColumn("是否选择", "isSelected", 50, false));
					list.add(addColumn("商品名称", "name", 200));
					list.add(addColumn("第一法定单位", "firstUnit.name", 100));
					list.add(addColumn("第二法定单位", "secondUnit.name", 100));
					list.add(addColumn("许可证代码", "ccontrol", 85));
					list.add(addColumn("备注", "note", 250));
					list.add(addColumn("状态", "isOut", 50));
					list.add(addColumn("优惠税率", "lowrate", 70));
					return list;
				}
			};
			tmCustomer = new JTableListModel(tbCustomer, list,
					jTableListModelAdapter);
			tbCustomer
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			tbCustomer.getColumnModel().getColumn(7)
					.setCellRenderer(new DefaultTableCellRenderer() {
						/**
				 * 
				 */
						private static final long serialVersionUID = 1L;

						public Component getTableCellRendererComponent(
								JTable table, Object value, boolean isSelected,
								boolean hasFocus, int row, int column) {
							super.getTableCellRendererComponent(table, value,
									isSelected, hasFocus, row, column);
							String str = "启用";
							if ("1".equals(value)) {
								str = "停用";
							}
							this.setText(str);
							return this;
						}
					});

		} else if (treeIndex1 == 2) { // 领证商品备注表
			tmCustomer = new JTableListModel(tbCustomer, list,
					new JTableListModelAdapter() {
						public List<JTableListColumn> InitColumns() {
							List<JTableListColumn> list = new Vector<JTableListColumn>();
							list.add(addColumn("商品编码", "code", 100));
							list.add(addColumn("辅助编码", "assistanceCode", 100));
							list.add(addColumn("流水号", "pkSeq", 50));
							list.add(addColumn("备注", "name", 50));
							list.add(addColumn("分类说明", "classElucidation", 200));
							return list;
						}
					});
			CommonQuery.getInstance().addCommonFilter(jComboBox, tfQueryValue,
					btnSearch, tmCustomer);
		}
		return tmCustomer;
	}

	/**
	 * 编辑列
	 */
	class CheckBoxEditor extends DefaultCellEditor implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private JCheckBox cb;

		private JTable table = null;

		public CheckBoxEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null) {
				return null;
			}
			if (Boolean.valueOf(value.toString()) instanceof Boolean) {
				cb = new JCheckBox();
				cb.setSelected(Boolean.valueOf(value.toString()).booleanValue());
			}
			cb.setHorizontalAlignment(JLabel.CENTER);
			cb.addActionListener(this);
			this.table = table;
			return cb;
		}

		public Object getCellEditorValue() {
			cb.removeActionListener(this);
			return cb;
		}

		public void actionPerformed(ActionEvent e) {
			JTableListModel tableModel = (JTableListModel) this.table
					.getModel();
			Object obj = tableModel.getCurrentRow();
			if (obj instanceof Complex) {
				Complex temp = (Complex) obj;
				temp.setIsSelected(new Boolean(cb.isSelected()));
				tableModel.updateRow(obj);
			}
			fireEditingStopped();
		}
	}

	/**
	 * 全选
	 * 
	 * @param isSelected
	 */
	private void selectAll(boolean isSelected) {

		List list = tmCustomer.getList();
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			complex.setIsSelected(isSelected);
		}
		tmCustomer.setList(list);

	}

	/**
	 * 获取选中数据
	 * 
	 * @return
	 */
	private List getSelectData() {
		List lsResult = new ArrayList();
		if (tmCustomer == null) {
			return lsResult;
		}
		List list = tmCustomer.getList();
		for (int i = 0; i < list.size(); i++) {
			Complex complex = (Complex) list.get(i);
			if (complex.getIsSelected() != null && complex.getIsSelected()) {
				lsResult.add(complex);
			}
		}
		return lsResult;
	}

	/**
	 * @return Returns the tableModel.
	 */
	public JTableListModel getTableModel() {
		return tmCustomer;
	}

	/**
	 * @param tableModel
	 *            The tableModel to set.
	 */
	public void setTableModel(JTableListModel tableModel) {
		this.tmCustomer = tableModel;
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
			btnAdd.setPreferredSize(new Dimension(80, 30));
			btnAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = CustombaseQuery.getInstance()
							.findCustomsComplexNotInComplex();
					if (list == null) {
						return;
					}
					tmCustomer.addRows(customBaseAction.saveComplex(list));
					// CustomBaseList.getInstance().setComplexes(
					// tableModel.getList());
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
			btnDelete.setPreferredSize(new Dimension(80, 30));
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tmCustomer.getCurrentRows();
					List lsDelete = new ArrayList();
					if (list.size() < 0) {
						JOptionPane.showMessageDialog(FmBaseCodeHs.this,
								"请选择要删除的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					for (int i = 0; i < list.size(); i++) {
						Complex complex = (Complex) list.get(i);
						try {
							customBaseAction.deleteComplex(complex);
							lsDelete.add(complex);
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(FmBaseCodeHs.this,
									"所选数据被其它地方引用，请先删除其引用！", "提示",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
					// try {
					// customBaseAction.deleteComplex(list);
					// } catch (Exception ex) {
					// JOptionPane.showMessageDialog(FmBaseCodeHs.this,
					// "所选数据被其它地方引用，请先删除其引用！", "提示",
					// JOptionPane.INFORMATION_MESSAGE);
					// return;
					// }

					tmCustomer.deleteRows(lsDelete);
				}
			});
		}
		return btnDelete;
	}

	/**
	 * 界面状态设置
	 */
	private void setState() {
		this.btnAdd.setVisible(treeIndex == 1);
		this.btnEdit.setVisible(treeIndex == 1);
		this.btnDelete.setVisible(treeIndex == 1);
		btnModifyLowrate.setVisible(treeIndex == 1);
		this.pnCommonQueryPage.setVisible(treeIndex == 1 || treeIndex == 0);
		this.pnCommonQuery.setVisible(treeIndex == 2);
		// this.btnCheck.setVisible(treeIndex == 1);
		// bnFind.setVisible(treeIndex == 1);
		bnTongBu.setVisible(treeIndex == 1);
		bnSelectAll.setVisible(treeIndex == 1);
		bnNoSelect.setVisible(treeIndex == 1);
		bnSerch.setVisible(treeIndex == 1);

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
			btnEdit.setPreferredSize(new Dimension(80, 30));
			btnEdit.setVisible(true);
			btnEdit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tmCustomer.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(FmBaseCodeHs.this,
								"请选择你要修改的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					if (JOptionPane.showConfirmDialog(FmBaseCodeHs.this,
							"修改此内容将会更改所有用到商品编码的模块如：备案资料、报关单等请谨慎使用此功能，确定要修改吗？",
							"提示！", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
						return;
					}
					DgComplex dg = new DgComplex();
					dg.setComplex((Complex) tmCustomer.getCurrentRow());
					dg.setVisible(true);
					if (dg.isOk()) {
						Complex complex = dg.getComplex();
						tmCustomer.updateRow(complex);
					}
				}
			});
		}
		return btnEdit;
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
			btnExit.setPreferredSize(new Dimension(80, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
			jToolBar1.add(getPnCommonQuery());
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
		Request request = new Request(CommonVars.getCurrUser());

		dataSource = customBaseAction.findComplexAll(request, index, length,
				property, value, isLike);
		return dataSource;
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
	public List getDataSources(int index, int length, String property,
			Object value, boolean isLike) {
		dataSource = customBaseAction.findCustomsComplex(index, length,
				property, value, isLike);
		return dataSource;
	}

	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage pnCommonQueryPage = null;

	/**
	 * 查找错误编码
	 */
	private JButton btnCheck = null;

	/**
	 * 查找差异商品
	 */
	private JButton bnFind = null;

	/**
	 * 同步
	 */
	private JButton bnTongBu = null;

	/**
	 * 全选
	 */
	private JButton bnSelectAll = null;

	/**
	 * 全不选
	 */
	private JButton bnNoSelect = null;

	/**
	 * 检查更新
	 */
	private JButton bnSerch = null;

	private JPanel jPanel1 = null;

	private JButton btnDisable = null;

	private JButton btnEnable = null;

	private JButton btnUpdate = null;

	private JButton btnFTP = null;
	private JButton btnModifyLowrate;
	private JButton localeUpdateData;

	/**
	 * 查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage() {

		if (pnCommonQueryPage == null) {

			pnCommonQueryPage = new PnCommonQueryPage() {

				private static final long serialVersionUID = 1L;

				@Override
				public JTableListModel initTable(List dataSource) {

					return FmBaseCodeHs.this.initTable(dataSource, treeIndex);

				}

				@Override
				public List getDataSource(int index, int length,
						String property, Object value, boolean isLike) {
					List list = null;
					if (treeIndex == 0) {
						list = FmBaseCodeHs.this.getDataSources(index, length,
								property, value, isLike);
					}

					else if (treeIndex == 1) {
						list = FmBaseCodeHs.this.getDataSource(index, length,
								property, value, isLike);
					}
					return list;
				}

			};
		}
		return pnCommonQueryPage;
	}

	/**
	 * 编辑
	 */
	public void Edit() {

		List list = customBaseAction.findCustomsComplex();
		for (int i = 0; i < list.size(); i++) {
			String ss = ((CustomsComplex) list.get(i)).getCcontrol();
			System.out.println("------------------------" + ss);
			for (int j = 0; j < ss.length(); j++) {
				String a;
				a = ss.substring(j, j + 1);
				System.out.println("-------------------" + a + "---------");
			}
			// List list1 =
			// customBaseAction.findCustomsDocuName(licenseDocu.getCode());
			// for(int m= 0;m < list1.size();m++){
			// String cc = ((LicenseDocu)list1.get(m)).getCode();
			// System.out.println("--------------"+cc+"~~~~~~~~~~~~~~~~~~~~`");

			customsComplex.setLicExp("string");

			tmCustomer.updateRow(customsComplex);

			// }
		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getbtnCheck() {
		if (btnCheck == null) {
			btnCheck = new JButton();
			btnCheck.setText("查找错误编码");
			btnCheck.setVisible(false);
			btnCheck.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new Find().start();
				}
			});
		}
		return btnCheck;
	}

	/**
	 * 检查是否有错误编码
	 * 
	 * @author Administrator
	 * 
	 */
	class Find extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FmBaseCodeHs.this);
				CommonProgress.setMessage("系统正在检查数据，请稍后...");
				List list = customBaseAction.findComplexNotInCustomsComplex();
				if (list == null || list.size() == 0) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(null, "               "
							+ "没有错误编码！", "提示！", JOptionPane.CLOSED_OPTION);
					return;

				} else {
					CommonProgress.closeProgressDialog();
					initTable(list, 1);
					// Thread.currentThread().stop();
					return;
				}
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(null,
						"检查数据失败：！" + e.getMessage(), "提示", 2);
			}

		}
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (bnFind == null) {
			bnFind = new JButton();
			bnFind.setText("查找差异商品");
			bnFind.setVisible(false);
			bnFind.setForeground(java.awt.Color.blue);
			bnFind.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FindChanged().start();
				}
			});
		}
		return bnFind;
	}

	/**
	 * 清空编码
	 * 
	 * @author Administrator
	 * 
	 */
	class ClearComplec extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正在清空商品编码，请稍后...");
				String hsql = "";
				int x = 0;
				long beginTime = System.currentTimeMillis();
				for (int i = 0; i < 2; i++) {
					if (i == 0) {
						hsql = "delete from customscomplex";
					} else if (i == 1) {
						hsql = "delete from custombaseversion where tablename = 'customscomplex'";
					}
					try {
						dataImportAction.execuFileSql(
								new Request(CommonVars.getCurrUser()), hsql);
					} catch (Exception ex) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(
								FmBaseCodeHs.this,
								"执行有错误：\n" + ex + "\n" + "第 "
										+ String.valueOf(i + 1) + " 条语句" + "\n"
										+ "请将执行文件E-Mail至负责您公司百思维顾问邮件中,谢谢!",
								"提示", 2);
						x = 1;
						break;
					}
				}
				CommonProgress.closeProgressDialog();
				long endTime = System.currentTimeMillis();
				if (x == 0) {
					JOptionPane.showMessageDialog(FmBaseCodeHs.this, "执行成功:\n"
							+ "执行语句：\n" + hsql + "共消耗时间: "
							+ (endTime - beginTime) + " 毫秒 ", "提示", 2);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询变化商品
	 * 
	 * @author Administrator
	 * 
	 */
	class FindChanged extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正获取数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				List list = customBaseAction.findChangedComplex(request);
				tmCustomer.setList(list);
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmBaseCodeHs.this, "查找数据完毕！",
						"提示", 2);
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmBaseCodeHs.this,
						"查找数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}

		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (bnTongBu == null) {
			bnTongBu = new JButton();
			bnTongBu.setText("同步");
			bnTongBu.setBounds(new Rectangle(140, 1, 60, 25));
			bnTongBu.setForeground(java.awt.Color.blue);
			bnTongBu.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new UpdateComplex().start();
				}
			});
		}
		return bnTongBu;
	}

	/**
	 * 商品同步
	 * 
	 * @author Administrator
	 * 
	 */
	class UpdateComplex extends Thread {
		public void run() {
			try {
				String taskId = CommonStepProgress.getExeTaskId();
				CommonStepProgress.showStepProgressDialog(taskId);
				CommonStepProgress.setStepMessage("系统正同步数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				List list = getSelectData();
				if (list == null || list.size() <= 0) {
					CommonStepProgress.closeStepProgressDialog();
					JOptionPane.showMessageDialog(FmBaseCodeHs.this,
							"请选择要同步的数据行!", "提示", 2);
					return;
				}
				list = customBaseAction.updateComplex(request, list);
				// tableModel.updateRows(list);
				tmCustomer.setList(list);
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmBaseCodeHs.this, "同步完毕!", "提示",
						2);
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				JOptionPane.showMessageDialog(FmBaseCodeHs.this,
						"同步数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonStepProgress.closeStepProgressDialog();
			}

		}
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnSelectAll() {
		if (bnSelectAll == null) {
			bnSelectAll = new JButton();
			bnSelectAll.setText("全选");
			bnSelectAll.setBounds(new Rectangle(6, 2, 60, 25));
			bnSelectAll.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(true);
				}
			});
		}
		return bnSelectAll;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnNoSelect() {
		if (bnNoSelect == null) {
			bnNoSelect = new JButton();
			bnNoSelect.setText("全否");
			bnNoSelect.setBounds(new Rectangle(70, 2, 60, 25));
			bnNoSelect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					selectAll(false);
				}
			});
		}
		return bnNoSelect;
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
	private JPanel getPnCommonQuery() {
		if (pnCommonQuery == null) {
			java.awt.GridBagConstraints gridBagConstraints15 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints14 = new GridBagConstraints();

			java.awt.GridBagConstraints gridBagConstraints13 = new GridBagConstraints();

			pnCommonQuery = new JPanel();
			pnCommonQuery.setLayout(new GridBagLayout());
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.gridy = 0;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints13.ipadx = 49;
			gridBagConstraints13.ipady = -2;
			gridBagConstraints13.insets = new java.awt.Insets(3, 6, 2, 3);
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints14.ipadx = 35;
			gridBagConstraints14.ipady = 3;
			gridBagConstraints14.insets = new java.awt.Insets(3, 4, 2, 3);
			gridBagConstraints15.gridx = 2;
			gridBagConstraints15.gridy = 0;
			gridBagConstraints15.ipadx = 11;
			gridBagConstraints15.ipady = -3;
			gridBagConstraints15.insets = new java.awt.Insets(3, 3, 2, 7);
			pnCommonQuery.add(getJComboBox(), gridBagConstraints13);
			pnCommonQuery.add(getJTextField(), gridBagConstraints14);
			pnCommonQuery.add(getBtnSearch(), gridBagConstraints15);
		}
		return pnCommonQuery;
	}

	/**
	 * 
	 * This method initializes jComboBox
	 * 
	 * 
	 * 
	 * @return javax.swing.JComboBox
	 * 
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setPreferredSize(new java.awt.Dimension(111, 27));
			jComboBox.addItem("代码");
			jComboBox.addItem("名称");
		}
		return jComboBox;
	}

	/**
	 * 
	 * This method initializes jTextField
	 * 
	 * 
	 * 
	 * @return javax.swing.JTextField
	 * 
	 */
	private JTextField getJTextField() {
		if (tfQueryValue == null) {
			tfQueryValue = new JTextField();
			tfQueryValue.setPreferredSize(new java.awt.Dimension(120, 22));
		}
		return tfQueryValue;
	}

	/**
	 * 
	 * This method initializes btnSearch
	 * 
	 * 
	 * 
	 * @return javax.swing.JButton
	 * 
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					// 查询
					// searchFlat = 1;
					// getFields();
					// getSearchData(FmBaseCodeParameter.this.searchFiled,
					// tfQueryValue.getText());

				}
			});

		}
		return btnSearch;
	}

	/**
	 * This method initializes jButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBnSerch() {
		if (bnSerch == null) {
			bnSerch = new JButton();
			bnSerch.setText("海关与自用商编差异比较");
			bnSerch.setPreferredSize(new Dimension(155, 30));
			bnSerch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgComplexUpdateInfo dg = new DgComplexUpdateInfo();
					// dg.setErrLs(errorLs);
					dg.setVisible(true);
					// new FindUpdateComplex().start();
				}
			});
		}
		return bnSerch;
	}

	/**
	 * 检查更新
	 * 
	 * @author Administrator
	 * 
	 */
	class FindUpdateComplex extends Thread {
		public void run() {
			try {
				bnSerch.setEnabled(false);
				CommonProgress.showProgressDialog();
				CommonProgress.setMessage("系统正检查更新数据，请稍后...");
				Request request = new Request(CommonVars.getCurrUser());
				List errorLs = customBaseAction.findUpdateComplex(request);
				CommonProgress.closeProgressDialog();
				if (errorLs != null && errorLs.size() > 0) {
					JOptionPane.showMessageDialog(FmBaseCodeHs.this,
							"检查完毕,有数据需要变更,请继续查看变更信息!", "提示", 2);

					// DgComplexUpdateInfo dg = new DgComplexUpdateInfo();
					// dg.setErrLs(errorLs);
					// dg.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(FmBaseCodeHs.this,
							"检查完毕,无任何需要变更!", "提示", 2);
				}

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmBaseCodeHs.this,
						"检查数据失败：！" + e.getMessage(), "提示", 2);
			} finally {
				CommonProgress.closeProgressDialog();
				bnSerch.setEnabled(true);
			}

		}
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setVisible(false);
			jPanel1.setLayout(null);
			jPanel1.add(getBnSelectAll(), null);
			jPanel1.add(getBnNoSelect(), null);
			jPanel1.add(getJButton2(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes btnDisable
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDisable() {
		if (btnDisable == null) {
			btnDisable = new JButton();
			btnDisable.setText("停用");
			btnDisable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = tmCustomer.getCurrentRows();

					if (list.size() < 0) {
						JOptionPane.showMessageDialog(FmBaseCodeHs.this,
								"请选择要停用的数据", "提示", JOptionPane.OK_OPTION);
						return;
					}
					list = customBaseAction.disableComplex(list);
					tmCustomer.updateRows(list);
				}
			});
		}
		return btnDisable;
	}

	/**
	 * This method initializes btnEnable
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEnable() {

		if (btnEnable == null) {

			btnEnable = new JButton();

			btnEnable.setText("启用");

			btnEnable.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					List list = tmCustomer.getCurrentRows();

					if (list.size() < 0) {

						JOptionPane.showMessageDialog(FmBaseCodeHs.this,
								"请选择要启用的数据", "提示", JOptionPane.OK_OPTION);

						return;

					}

					list = customBaseAction.enableComplex(list);

					tmCustomer.updateRows(list);

				}

			});
		}
		return btnEnable;
	}

	/**
	 * This method initializes btnUpdate
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnUpdate() {

		if (btnUpdate == null) {

			btnUpdate = new JButton();

			btnUpdate.setText("升级基础资料");

			btnUpdate.setToolTipText("点击更新最新海关商品编码");

			btnUpdate.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					new UpgradeCustomsBaseThread().start();

				}
			});
		}
		return btnUpdate;
	}

	/**
	 * 海关商品资料升级
	 * 
	 * @author Administrator
	 * 
	 */
	class UpgradeCustomsBaseThread extends Thread {

		public void run() {

			btnUpdate.setEnabled(false);

			long b = System.currentTimeMillis();

			List fileNames = getUpgradeFileList();

			CommonProgress.showProgressDialog();

			try {

				if (fileNames != null && fileNames.size() > 0) {

					for (int i = 0; i < fileNames.size(); i++) {

						String fileName = (String) fileNames.get(i);

						downloadFtpFileAndUploadToServer(fileName);

						customBaseAction.upgradeCustomsBase();// 开始导入

						fileName = CustomBaseClassName
								.getCustomBaseClassName(fileName.substring(0,
										fileName.indexOf(".")));

						CommonProgress.setMessage("系统正升级 " + fileName
								+ "......");

					}

					long e = System.currentTimeMillis();

					System.out.println("update time:" + (e - b) / 1000.0);

				} else {

					CommonProgress.closeProgressDialog();

					JOptionPane.showMessageDialog(FmBaseCodeHs.this,
							"基础资料已经是最新版本", "提示！",
							JOptionPane.INFORMATION_MESSAGE);

				}

			} catch (Exception ex) {

				CommonProgress.closeProgressDialog();
				/**
				 * 2014-09-23 测试发现org.hibernate.StaleStateException: Batch
				 * update returned unexpected row count from update [0]; actual
				 * row count: 0; expected: 1 异常信息 或主键重复异常
				 * 原因：这个问题是又于改了LicenseDocu此表中的主键引起的 建议解决方法：drop table
				 * licensedocu 然后重新启动程序，重新建表
				 */
				ex.printStackTrace();

				JOptionPane.showMessageDialog(FmBaseCodeHs.this, "升级基础资料出错,"
						+ ex.getMessage(), "提示！",
						JOptionPane.INFORMATION_MESSAGE);

			} finally {

				CommonProgress.closeProgressDialog();

				btnUpdate.setEnabled(true);

			}
		}
	}

	/**
	 * 直接更新资料数据
	 * 
	 * @return
	 */
	private String getCustomsBaseDataFolder() {
		String folder = System.getProperty("user.dir") + File.separator
				+ "CustomsBaseData";
		System.out.println(folder);
		File file = new File(folder);
		if (!file.exists()) {
			file.mkdirs();
		}
		return folder;
	}

	/**
	 * 从FTP服务器上下载文件并同时上传到JBCUS服务器端的目录下。
	 */
	private void downloadFtpFileAndUploadToServer(String zipfileName) {

		File file = downloadHttpFile(zipfileName);

		try {

			if (file != null) {

				byte[] bytes = getBytesFromFile(file);

				this.customBaseAction.uploadCustomsBaseFileToServer(bytes,
						zipfileName);

			} else {

				JOptionPane.showMessageDialog(FmBaseCodeHs.this, "下载文件失败",
						"提示！", JOptionPane.INFORMATION_MESSAGE);

				return;

			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		try {

			// 文件工具 删除目录
			FileUtils.forceDelete(file);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private byte[] getBytesFromFile(File file) throws IOException {

		return FileUtils.readFileToByteArray(file);
	}

	/**
	 * 从FTP服务器上下载文件
	 * 
	 * @param fileName
	 */
	private File downloadHttpFile(String fileName) {

		try {

			CustomsBaseHttpClientUtil.download(fileName,
					getCustomsBaseDataFolder());

		} catch (Exception e) {

			e.printStackTrace();

			throw new RuntimeException(e);

		}

		return new File(getCustomsBaseDataFolder() + File.separator + fileName);
	}

	/**
	 * 从FTP服务器上下载文件
	 * 
	 * @param fileName
	 */
	private File downloadHttpFile2(String fileName) {

		try {

			CustomsBaseHttpClientUtil.download(fileName,
					getCustomsBaseDataFolderForLocalUpdate());

		} catch (Exception e) {

			e.printStackTrace();

			throw new RuntimeException(e);

		}

		return new File(getCustomsBaseDataFolderForLocalUpdate()
				+ File.separator + fileName);
	}

	/**
	 * 获取到导入的文件列表。
	 * 
	 * @return
	 */
	private List getUpgradeFileList() {

		Map map = new HashMap();

		// 这是需要下载的文件名字
		String ftpFileName = "CustomsBaseVersion.xml";

		File file = downloadHttpFile(ftpFileName);// 从FTP服务器下载当前的文件列表及版本。

		SAXBuilder sb = new SAXBuilder();

		Document doc = null;

		try {

			doc = sb.build(new FileInputStream(file));

			Element root = doc.getRootElement();

			List listRow = root.getChildren("row");

			for (int i = 0; i < listRow.size(); i++) {

				Element element = (Element) listRow.get(i);

				String tableName = "";

				String fileName = "";

				String createDate = "";

				if (element.getAttribute("tableName") != null) {

					tableName = element.getAttribute("tableName").getValue()
							.toLowerCase();

				}

				if (element.getAttribute("fileName") != null) {

					fileName = element.getAttribute("fileName").getValue();

				}

				if (element.getAttribute("createDate") != null) {

					createDate = element.getAttribute("createDate").getValue();

				}

				map.put(tableName, new String[] { fileName, createDate });

			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

		file.delete();

		return customBaseAction.getMustUpgradeFileName(map);// 将文件列表传到服务器端进行过滤和排序。

	}

	/**
	 * This method initializes btnFTP
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFTP() {
		if (btnFTP == null) {
			btnFTP = new JButton();
			btnFTP.setText("网络测试");
			btnFTP.setToolTipText("<html><body>测试HTTP与平台(http://webftp.bsw.com.cn)是否畅通"
					+ "<br/>"
					+ "			测试FTP与平台IP地址：218.16.119.187 ：21</body></html>");
			btnFTP.setPreferredSize(new Dimension(80, 30));
			btnFTP.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						boolean isConn = CustomsBaseHttpClientUtil
								.testConnect();
						if (isConn) {
							JOptionPane.showMessageDialog(FmBaseCodeHs.this,
									"HTTP网络畅通", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						} else {
							JOptionPane.showMessageDialog(FmBaseCodeHs.this,
									"网络不通,请检查互联网是否畅通.", "提示！",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(FmBaseCodeHs.this,
								"网络不通,详细信息：" + ex.getMessage(), "提示！",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			});
		}
		return btnFTP;
	}

	private JButton getBtnModifyLowrate() {
		if (btnModifyLowrate == null) {
			btnModifyLowrate = new JButton("修改优惠税率");
			btnModifyLowrate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Complex c = (Complex) getTableModel().getCurrentRow();
					String str = JOptionPane.showInputDialog(getParent(),
							"优惠税率", c.getLowrate());
					if (str != null && !str.trim().equals("")) {
						if (org.apache.commons.lang.math.NumberUtils
								.isNumber(str.trim())) {
							c.setLowrate(str.trim());
							c = customBaseAction.saveComplex(c);
							getTableModel().updateRow(c);
							return;
						}
						JOptionPane.showMessageDialog(getParent(),
								"优惠税率必须是数字!", "提醒",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			});
		}
		return btnModifyLowrate;
	}

	private JButton getLocaleUpdate() {

		if (localeUpdateData == null) {

			localeUpdateData = new JButton("本地更新基础资料");

			localeUpdateData.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					// 本地更新
					localUpdate();

				}
			});
		}
		return localeUpdateData;
	}

	/*
	 * 本地更新方法
	 */
	private void localUpdate() {

		// 文件选择导航器
		JTraceFileChooser fchooser = new JTraceFileChooser("请选择要更新的zip、rar文件");

		// 文件类型过滤 这里需求是 找出 *.zip ; *.rar
		fchooser.setFileFilter(new FileNameExtensionFilter("压缩文件", "zip", "rar"));

		// 不接受过滤选项
		fchooser.setAcceptAllFileFilterUsed(false);

		File file = null;

		if (JFileChooser.APPROVE_OPTION == fchooser.showOpenDialog(getParent())) {

			file = fchooser.getSelectedFile();

			System.out.println("file = " + file.getPath());

		}

		if (file == null) {

			return;

		}

		// 解压文件
		decompressFile(file);

		update(true, file.getName());
	}

	/**
	 * 解压文件
	 * 
	 * @param file
	 */
	private void decompressFile(File file) {

		try {

			// 解压缩到 本地临时文件目录
			CspZIPUtil.zipDecompress(file.getPath(),
					getCustomsBaseDataFolderForLocalUpdate() + File.separator);

			System.out.println("解压缩之后的路径 : >>>>> "
					+ getCustomsBaseDataFolderForLocalUpdate());

			System.out.println("ZIP 源文件路径  : >>>>>>>  " + file.getPath()
					+ " <<<<<<<< ");

		} catch (Exception ex) {

			throw new AppException("解压文件失败!" + ex.getMessage());

		}
	}

	/**
	 * 创建 临时 本地 更新文件目录
	 * 
	 * @return 临时文件目录
	 */
	private String getCustomsBaseDataFolderForLocalUpdate() {

		// 获取目录 |*****/LocaleCustomsBaseData/|
		String folder = System.getProperty("java.io.tmpdir")
				+ "LocaleCustomsBaseData";

		File file = new File(folder);

		// 文件是否存在
		if (!file.exists()) {

			// 创建文件目录
			file.mkdirs();

		}

		return folder;
	}

	/**
	 * 获取到导入的 <本地> 文件列表。
	 * 
	 * @param 是否本地更新
	 *
	 * @return
	 */
	public List getUpgradeLocaleFileList(boolean localUpdate, String fileName1) {

		Map map = new HashMap();

		String ftpFileName = "CustomsBaseVersion.xml";

		File file = null;

		// 是否本地更新
		if (!localUpdate) {

			// 非本地更新就从FTP服务上下载文件列表及版本
			file = downloadHttpFile2(ftpFileName);// 从FTP服务器下载当前的文件列表及版本。

		} else {

			// 从本地上查找 版本列表文件
			file = findDeZipFolder(ftpFileName, fileName1,
					getCustomsBaseDataFolderForLocalUpdate());

			// 将本地的解压目录给下个方法使用
			localFolder = file.getParent();

		}

		if (file == null) {

			throw new AppException("没有找到CustomsBaseVersion.xml文件");

		}

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

			org.w3c.dom.Document doc = builder.parse(file);

			NodeList nl = doc.getElementsByTagName("row");

			for (int i = 0; i < nl.getLength(); i++) {

				Node node = nl.item(i);

				NamedNodeMap nameMap = node.getAttributes();

				String tableName = "";

				String fileName = "";

				String createDate = "";

				if (nameMap.getNamedItem("tableName") != null) {

					tableName = nameMap.getNamedItem("tableName")
							.getNodeValue().toLowerCase();

				}

				if (nameMap.getNamedItem("fileName") != null) {

					fileName = nameMap.getNamedItem("fileName").getNodeValue();

				}

				if (nameMap.getNamedItem("createDate") != null) {

					createDate = nameMap.getNamedItem("createDate")
							.getNodeValue();

				}

				map.put(tableName.toLowerCase(), new String[] { fileName,
						createDate });
			}

		} catch (Exception ex) {

			ex.printStackTrace();

			throw new AppException("获取CustomsBaseVersion.xml文件失败");

		}

		if (file != null && file.exists()) {

			System.out.println("fileName = " + file.getPath());

			// file.delete();
		}

		// 将文件列表传到服务器端进行过滤和排序。
		return customBaseAction.getMustUpgradeFileName(map);

	}

	/**
	 * 递归查找文件
	 * 
	 * @param findFile
	 *            你需要找的文件
	 * @param fileName
	 *            如果当前目录下没你要找的文件 那么就根据压缩文件名去继续找
	 * @param path
	 *            当前你查找的文件目录
	 * @return
	 */
	private File findDeZipFolder(String findFile, String fileName, String path) {

		// 从本地上查找 版本列表文件
		File file = getFile(path, findFile);

		// 如果存在就直接返回
		if (file.exists()) {

			return file;

		} else {

			file = findDeZipFolder(findFile, fileName, path + File.separator
					+ fileName.replace(".zip", ""));

		}

		return file;

	}

	/**
	 * 本地文件上传到服务器端的目录下。
	 *
	 * @param zipfileName
	 *            需要上传的压缩文件
	 */
	private void localFileAndUploadToServer(String zipfileName) {

		File file = getFile(getCustomsBaseDataFolder() + File.separator,
				zipfileName);

		try {

			byte[] bytes = getBytesFromFile(file);

			customBaseAction.uploadCustomsBaseFileToServer(bytes, zipfileName);

			FileUtils.forceDelete(file);

		} catch (IOException e) {

			e.printStackTrace();

			throw new AppException("文件不能正常上传...");

		}

	}

	/**
	 * 更新 方法
	 * 
	 * @param isLocalUpdate
	 *            是否本地更新
	 */
	private void update(final boolean isLocalUpdate, final String userZipfName) {

		/*
		 * 设置 当前 所有关于更新的 按钮 不可用
		 */
		// 更新资料按钮
		btnUpdate.setEnabled(false);

		// 本地更新按钮
		localeUpdateData.setEnabled(false);

		DgProgressCustomBaseData dgProgressCustomBaseData = new DgProgressCustomBaseData(
				getUpgradeLocaleFileList(isLocalUpdate, userZipfName),
				localFolder);

		dgProgressCustomBaseData.setVisible(true);

		// 更新资料按钮
		btnUpdate.setEnabled(true);

		// 本地更新按钮
		localeUpdateData.setEnabled(true);

		List list = customBaseAction.findComplexNotInCustomsComplex();

		initTable(list, treeIndex);

	}

	public static File getFile(String... names) {
		if (names == null) {
			throw new NullPointerException("名字必须是有效的!");
		}
		File file = null;
		for (String name : names) {
			if (file == null) {
				file = new File(name);
			} else {
				file = new File(file, name);
			}
		}
		return file;
	}

} // @jve:decl-index=0:visual-constraint="10,46"

/**
 * 表格渲染器
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
class ToolTipRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List list = CustomBaseList.getInstance().getLicensedocus();

	HashMap hmap = new HashMap();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		String show = "";
		JLabel lb = (JLabel) super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		for (int i = 0; i < list.size(); i++) {
			LicenseDocu license = (LicenseDocu) list.get(i);
			if (license != null) {
				hmap.put(license.getCode(), license.getName());
			}
			// System.out.println(license.getCode()+" :"+ license.getName());
		}
		if (value == null) {
			lb.setToolTipText("");
		} else {
			char[] values = value.toString().toCharArray();
			for (int i = 0; i < values.length; i++) {
				// String str=String.valueOf( values[i]);

				show += "代码" + values[i] + ":"
						+ hmap.get(String.valueOf(values[i])) + " ";
				// System.out.println(values[i]);
			}
		}

		lb.setToolTipText(show);
		return lb;
	}

}
