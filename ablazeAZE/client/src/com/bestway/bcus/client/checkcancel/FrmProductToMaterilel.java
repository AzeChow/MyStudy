package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.action.CheckCancelAuthorityAction;
import com.bestway.bcus.checkcancel.entity.FactoryStoryProduct;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kVersion;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FrmProductToMaterilel extends JInternalFrameBase {

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTableExg = null;

	private JPanel jPanel11 = null;

	private JSplitPane jSplitPane1 = null;

	private JPanel jPanel21 = null;

	private JScrollPane jScrollPane3 = null;

	private JTable jTableVersion = null;

	private JPanel jPanel3 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTableBom = null;

	private JToolBar jToolBar = null; // @jve:decl-index=0:visual-constraint="633,148"

	private JButton jbAdd = null;

	private JButton jbDelete = null;

	private JButton jbExit = null;

	private CheckCancelAction checkCancelAction = null;

	private JTableListModel tableModelVersion = null; // @jve:decl-index=0:visual-constraint="18,1004"

	private JTableListModel tableModelExg = null; // @jve:decl-index=0:visual-constraint="26,1076"

	private JTableListModel tableModelBom = null; // @jve:decl-index=0:visual-constraint="27,1041"

	private JTableListModel tableModelBomAnyle = null;

	private CheckCancelAuthorityAction checkCancelAuthorityAction = null;

	private FactoryStoryProduct fs = null;

	private ButtonGroup buttonGroup = null; // @jve:decl-index=0:visual-constraint="29,1115"

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel4 = null;

	private JToolBar jJToolBarBar = null;

	private JLabel jLabel = null;

	private JComboBox jComboBox = null;

	private JButton jButton = null;

	private JSplitPane jSplitPane2 = null;

	private JList jList = null;

	private JButton btnCount = null;

	private JPanel jPanel5 = null;

	private JScrollPane jScrollPane5 = null;

	private JScrollPane jScrollPane4 = null;

	private JPanel jPanel6 = null;

	private JTable jTable = null;

	private JPanel jPanel7 = null;

	private JPanel jPanel8 = null;

	public FrmProductToMaterilel() {

		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		checkCancelAuthorityAction = (CheckCancelAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkCancelAuthorityAction");

		initialize();
	}

	// /**
	// * This method initializes this
	// *
	// */
	private void initialize() {

		setTitle("成品折算报关料件");
		this.setContentPane(getJTabbedPane());
		checkCancelAuthorityAction.findAllFactoryStoryProduct(new Request(
				CommonVars.getCurrUser()));
		List list = checkCancelAction.findFactoryStoryProduct(new Request(
				CommonVars.getCurrUser()));
		initTableExg(list);
		getButtonGroup();
		this.setSize(650, 496);
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
			jPanel.add(getJPanel1(), BorderLayout.CENTER);
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
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
			jPanel1.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(180);
			jSplitPane.setDividerSize(5);
			jSplitPane.setTopComponent(getJPanel2());
			jSplitPane.setBottomComponent(getJPanel11());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
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
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTableExg());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTableExg
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableExg() {
		if (jTableExg == null) {
			jTableExg = new JTable();
			jTableExg.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							if (tableModelExg != null
									&& tableModelExg.getCurrentRow() != null) {
								initTableVersion((FactoryStoryProduct) tableModelExg
										.getCurrentRow());
							} else {
								initTableVersion(null);
							}
						}
					});
		}
		return jTableExg;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jPanel11 = new JPanel();
			jPanel11.setLayout(new BorderLayout());
			jPanel11.setBorder(BorderFactory.createTitledBorder(null,
					"\u6210\u54c1\u5355\u8017",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, null, null));
			jPanel11.add(getJSplitPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel11;
	}

	/**
	 * This method initializes jSplitPane1
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerLocation(105);
			jSplitPane1.setDividerSize(5);
			jSplitPane1.setLeftComponent(getJPanel21());
			jSplitPane1.setRightComponent(getJPanel3());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes jPanel21
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel21() {
		if (jPanel21 == null) {
			jPanel21 = new JPanel();
			jPanel21.setLayout(new BorderLayout());
			jPanel21.add(getJScrollPane3(), java.awt.BorderLayout.CENTER);
		}
		return jPanel21;
	}

	/**
	 * This method initializes jScrollPane3
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setViewportView(getJTableVersion());
		}
		return jScrollPane3;
	}

	/**
	 * This method initializes jTableVersion
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableVersion() {
		if (jTableVersion == null) {
			jTableVersion = new JTable();
			jTableVersion.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							initTableBom(fs,
									(EmsHeadH2kVersion) tableModelVersion
											.getCurrentRow());
						}
					});
		}
		return jTableVersion;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTableBom());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTableBom
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTableBom() {
		if (jTableBom == null) {
			jTableBom = new JTable();
		}
		return jTableBom;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setSize(new Dimension(191, 23));
			jToolBar.add(getJbAdd());
			jToolBar.add(getJbDelete());
			jToolBar.add(getJbExit());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jbAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbAdd() {
		if (jbAdd == null) {
			jbAdd = new JButton();
			jbAdd.setText("从文本导入");
			jbAdd.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputFactoryStoryMateriel dg = new DgInputFactoryStoryMateriel();
					dg.setVisible(true);
					checkCancelAuthorityAction
							.findAllFactoryStoryProduct(new Request(CommonVars
									.getCurrUser()));
					List list = checkCancelAction
							.findFactoryStoryProduct(new Request(CommonVars
									.getCurrUser()));
					initTableExg(list);
				}
			});
		}
		return jbAdd;
	}

	/**
	 * This method initializes jbDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbDelete() {
		if (jbDelete == null) {
			jbDelete = new JButton();
			jbDelete.setText("\u5220\u9664");
			jbDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelExg.getCurrentRow() == null) {
						JOptionPane.showMessageDialog(
								FrmProductToMaterilel.this, "请选择要删除的数据！",
								"提示！", JOptionPane.WARNING_MESSAGE);
						return;
					}
					List list = tableModelExg.getCurrentRows();
					checkCancelAction.delFactoryStoryProduct(new Request(
							CommonVars.getCurrUser()), list);
					tableModelExg.deleteRows(list);
				}
			});
		}
		return jbDelete;
	}

	/**
	 * This method initializes jbExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJbExit() {
		if (jbExit == null) {
			jbExit = new JButton();
			jbExit.setText("\u5173\u95ed");
			jbExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jbExit;
	}

	private void initTableExg(List list) {
		tableModelExg = new JTableListModel(getJTableExg(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("料号", "ptNo", 100));
						list.add(addColumn("名称", "ptName", 150));
						list.add(addColumn("规格型号", "ptSpec", 150));
						list.add(addColumn("数量", "quantity", 80));
						list.add(addColumn("计量单位", "units", 80));
						list.add(addColumn("单价", "unitPrice", 80));
						list.add(addColumn("总价", "amountPrice", 80));
						list.add(addColumn("净重", "netWeight", 80));
						list.add(addColumn("毛重", "grossWeight", 80));
						return list;
					}
				});
		if (tableModelExg.getCurrentRow() != null) {
			initTableVersion((FactoryStoryProduct) tableModelExg
					.getCurrentRow());
		} else
			initTableVersion(null);
	}

	private void initTableVersion(FactoryStoryProduct data) {
		List list = new ArrayList();
		if (data != null) {
			fs = data;
			list = checkCancelAction
					.findEmsHeadH2kVersionByFactoryStoryProductPtNo(
							new Request(CommonVars.getCurrUser()), data
									.getPtNo());
		}
		tableModelVersion = new JTableListModel(getJTableVersion(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// list.add(addColumn("统计", "aa", 30));
						list.add(addColumn("版本号", "name", 85));

						return list;
					}
				});
		if (data != null && tableModelVersion.getCurrentRow() != null) {
			initTableBom(data, (EmsHeadH2kVersion) tableModelVersion
					.getCurrentRow());
		} else {
			initTableBom(null, null);
		}
	}

	private void initTableBom(FactoryStoryProduct data,
			EmsHeadH2kVersion emsHeadH2kVersion) {
		List dataSourceBom = new ArrayList();
		if (data != null && emsHeadH2kVersion != null) {
			Double amountPrice = data.getQuantity();
			dataSourceBom = checkCancelAction.findEmsHeadH2kBom(new Request(
					CommonVars.getCurrUser()), // 得到BOM
					emsHeadH2kVersion, amountPrice);
		}
		tableModelBom = new JTableListModel(getJTableBom(), dataSourceBom,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("料件序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("申报单位", "unit.name", 80));
						list.add(addColumn("单耗", "unitWear", 80));
						list.add(addColumn("损耗", "wear", 80));
						list.add(addColumn("总用量", "totalUse", 80));
						list.add(addColumn("单价", "price", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
	}

	private void initTableBomAnyle(List list) {
		tableModelBomAnyle = new JTableListModel(getJTable(), list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list
								.add(addColumn("料件序号", "seqNum", 60,
										Integer.class));
						list.add(addColumn("商品编码", "complex.code", 100));
						list.add(addColumn("商品名称", "name", 100));
						list.add(addColumn("型号规格", "spec", 100));
						list.add(addColumn("申报单位", "unit.name", 80));
						list.add(addColumn("总用量", "totalUse", 80));
						list.add(addColumn("单价", "price", 80));
						list.add(addColumn("备注", "note", 100));
						return list;
					}
				});
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
	 */
	private ButtonGroup getButtonGroup() {
		if (buttonGroup == null) {
			buttonGroup = new ButtonGroup();
		}
		return buttonGroup;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
			jTabbedPane.addTab("成品折算明细", null, getJPanel(), null);
			jTabbedPane.addTab("折算料件统计", null, getJPanel4(), null);
			jTabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if (jTabbedPane.getSelectedIndex() == 1) {
						initTableBomAnyle(new ArrayList());
						initJListAndCbb();
					}
				}
			});
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BorderLayout());
			jPanel4.add(getJPanel8(), BorderLayout.CENTER);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJJToolBarBar() {
		if (jJToolBarBar == null) {
			jLabel = new JLabel();
			jLabel.setText("统一版本");
			jLabel.setBounds(new Rectangle(318, 6, 52, 18));
			jJToolBarBar = new JToolBar();
			jJToolBarBar.add(getJPanel7());
			jJToolBarBar.add(getBtnCount());
			jJToolBarBar.add(getJButton());
		}
		return jJToolBarBar;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(373, 3, 120, 24));
			jComboBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					String str = (String) jComboBox.getSelectedItem();
					for (int i = 0; i < jList.getModel().getSize(); i++) {
						Object[] dataobj = (Object[]) jList.getModel()
								.getElementAt(i);
						Object[] strobj = (Object[]) dataobj[0];
						String[] strs = new String[2];
						strs[0] = (String) strobj[0];
						strs[1] = (String) strobj[2];
						if (strs[1].equals(str)) {
							dataobj[1] = new Boolean(true);
						} else {
							dataobj[1] = new Boolean(false);
						}
					}
					jList.repaint();
				}
			});
		}
		return jComboBox;
	}

	private List getSelectedList() {
		List properList = new ArrayList();
		for (int i = 0; i < jList.getModel().getSize(); i++) {
			Object[] dataobj = (Object[]) jList.getModel().getElementAt(i);
			if ((Boolean) dataobj[1]) {
				properList.add(dataobj[0]);
			}
		}
		return properList;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("关闭");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jSplitPane2
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane2() {
		if (jSplitPane2 == null) {
			jSplitPane2 = new JSplitPane();
			jSplitPane2.setDividerSize(4);
			jSplitPane2.setDividerLocation(510);
			jSplitPane2.setResizeWeight(1);
			jSplitPane2.setRightComponent(getJScrollPane5());
			jSplitPane2.setLeftComponent(getJPanel6());
		}
		return jSplitPane2;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setCellRenderer(new CheckListRender());
			jList.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int index = jList.locationToIndex(e.getPoint());
					if (index == -1) {
						return;
					}
					Object[] objs = (Object[]) jList.getModel().getElementAt(
							index);
					objs[1] = (!(Boolean) objs[1]);
					Rectangle rect = jList.getCellBounds(index, index);
					jList.repaint();
				}
			});

		}
		return jList;
	}

	private void initJListAndCbb() {
		List list = checkCancelAction.findAllEmsHeadH2kExgVersion(new Request(
				CommonVars.getCurrUser()));
		List addList = new ArrayList();
		for (int k = 0; k < list.size(); k++) {
			Object[] objs = new Object[2];
			objs[0] = list.get(k);
			objs[1] = new Boolean(false);
			addList.add(objs);
		}
		jList.setListData(addList.toArray());
		Map map = new HashMap();
		jComboBox.removeAllItems();
		for (int p = 0; p < list.size(); p++) {
			Object[] strs = (Object[]) list.get(p);
			if (map.get(strs[2]) == null) {
				jComboBox.addItem(strs[2]);
			}
			map.put(strs[2], strs[2]);
		}

	}

	/**
	 * This method initializes btnCount
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCount() {
		if (btnCount == null) {
			btnCount = new JButton();
			btnCount.setText("统计折算");
			btnCount.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new StartThread().start();
				}
			});
		}
		return btnCount;
	}

	/**
	 * This method initializes jPanel5
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jScrollPane5
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane5() {
		if (jScrollPane5 == null) {
			jScrollPane5 = new JScrollPane();
			jScrollPane5.setViewportView(getJList());
		}
		return jScrollPane5;
	}

	class CheckListRender extends DefaultListCellRenderer {

		public CheckListRender() {
			super();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			JCheckBox cbx = new JCheckBox();
			setComponentOrientation(list.getComponentOrientation());
			if (isSelected) {
				cbx.setBackground(list.getSelectionBackground());
				cbx.setForeground(list.getSelectionForeground());
			} else {
				cbx.setBackground(list.getBackground());
				cbx.setForeground(list.getForeground());
			}
			cbx.setIcon(null);
			Object[] objs = (Object[]) value;
			Object[] strobj = (Object[]) objs[0];
			String[] strs = new String[2];
			strs[0] = (String) strobj[0];
			strs[1] = (String) strobj[2];
			String show = strs[0] + "/" + strs[1];
			cbx.setText(show);
			cbx.setSelected((Boolean) objs[1]);
			cbx.setFont(list.getFont());
			Border border = null;
			if (cellHasFocus) {
				if (isSelected) {
					border = UIManager
							.getBorder("List.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager
							.getBorder("List.focusCellHighlightBorder");
				}
			} else {
				border = noFocusBorder;
			}
			cbx.setBorder(border);

			return cbx;
		}

	}

	/**
	 * This method initializes jScrollPane4
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane4() {
		if (jScrollPane4 == null) {
			jScrollPane4 = new JScrollPane();
			jScrollPane4.setViewportView(getJTable());
		}
		return jScrollPane4;
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
			jPanel6.add(getJScrollPane4(), BorderLayout.CENTER);
		}
		return jPanel6;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * This method initializes jPanel7
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel7() {
		if (jPanel7 == null) {
			jPanel7 = new JPanel();
			jPanel7.setLayout(null);
			jPanel7.add(getJComboBox(), null);
			jPanel7.add(jLabel, null);
		}
		return jPanel7;
	}

	/**
	 * This method initializes jPanel8
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel8() {
		if (jPanel8 == null) {
			jPanel8 = new JPanel();
			jPanel8.setLayout(new BorderLayout());
			jPanel8.add(getJJToolBarBar(), BorderLayout.SOUTH);
			jPanel8.add(getJSplitPane2(), BorderLayout.CENTER);
		}
		return jPanel8;
	}

	class StartThread extends Thread {
		public void run() {
			try {
				CommonProgress.showProgressDialog(FrmProductToMaterilel.this);
				CommonProgress.setMessage("请稍后...");
				List list = checkCancelAction.findTempEmsHeadH2kBomAnyle(
						new Request(CommonVars.getCurrUser()),
						getSelectedList());
				initTableBomAnyle(list);
				CommonProgress.closeProgressDialog();
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FrmProductToMaterilel.this,
						"统计失败：！" + ex.getMessage(), "提示!",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
	}
} // @jve:decl-index=0:visual-constraint="10,10"
