package com.bestway.common.client.tools;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.Item;
import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.tools.action.CheckToolsAuthorityAction;
import com.bestway.common.tools.action.ToolsAction;
import com.bestway.common.tools.entity.TempNodeItem;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

/**
 * @author luosheng 2006/9/1
 * 
 */

public class FmDataDict extends JInternalFrameBase {

	private JPanel jContentPane = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTable tbTable = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbField = null;

	private ToolsAction toolsAction = null;

	private Map<TempNodeItem, List<TempNodeItem>> dataDictMap = null;

	private CheckToolsAuthorityAction checkToolsAuthorityAction = null;

	private JPanel pnTop = null;

	private JToolBar jToolBar = null;

	private JButton btnSearch = null;

	private JButton btnClose = null;

	private JComboBox cbbSearchValue = null;

	private JLabel jLabel = null;

	private JPanel pnToolBar = null;
	private JTableListModel tableModel = null;

	private JButton btnEmpty = null;

	private Map<TempNodeItem, List<TempNodeItem>> dataEmptyMap = null;

	private boolean isDisplayAll = true;

	/**
	 * This is the default constructor
	 */
	public FmDataDict() {
		super();
		initialize();
		toolsAction = (ToolsAction) CommonVars.getApplicationContext().getBean(
				"toolsAction");
		checkToolsAuthorityAction = (CheckToolsAuthorityAction) CommonVars
				.getApplicationContext().getBean("checkToolsAuthorityAction");
		checkToolsAuthorityAction.checkToolsAuthorityDiary(new Request(
				CommonVars.getCurrUser()));
		dataDictMap = this.toolsAction.getTableColumnMap();
		dataEmptyMap = this.getDataEmptyMap(dataDictMap);
		this.initTbTable(dataDictMap);
		this.initCbbSearchValue();
	}

	/**
	 * 获取为空的表
	 * 
	 * @param dataDictMap
	 * @return
	 */
	private Map getDataEmptyMap(Map dataDictMap) {
		if (dataDictMap != null) {
			List<TempNodeItem> list = new ArrayList<TempNodeItem>();
			dataEmptyMap = new HashMap<TempNodeItem, List<TempNodeItem>>();
			list.addAll(dataDictMap.keySet());
			for (TempNodeItem tempNodeItem : list) {
				List<TempNodeItem> emptyFieldList = new ArrayList<TempNodeItem>();
				// 获取主表备注
				String remark = tempNodeItem.getRemark().trim();
				List<TempNodeItem> fieldList = (List<TempNodeItem>) dataDictMap
						.get(tempNodeItem);
				for (TempNodeItem fieldTempNodeItem : fieldList) {
					// 获取从表备注
					String fieldReMark = fieldTempNodeItem.getRemark().trim();
					// 判断备注是否为空
					if (fieldReMark.equals("")) {
						emptyFieldList.add(fieldTempNodeItem);
					}
				}
				// 如果有空的就加入
				if (emptyFieldList.size() > 0) {
					dataEmptyMap.put(tempNodeItem, emptyFieldList);
				} else if (remark.equals("")) {
					dataEmptyMap.put(tempNodeItem, emptyFieldList);
				}
			}
		}
		return dataEmptyMap;
	}

	private void initCbbSearchValue() {
		this.cbbSearchValue.setEditable(true);
		Properties propsVersion = new Properties();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(FmDataDict.class
					.getResourceAsStream("DataDictItem.properties"), "UTF-8"));
			propsVersion.load(br);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

				}
			}
		}
		List<Item> items = new ArrayList<Item>();
		Set<Map.Entry<Object, Object>> set =propsVersion.entrySet();
		for (Map.Entry<Object, Object> entry : set) {
			String key = (String) entry.getKey();
			String name = (String) entry.getValue();
			Integer sort = Integer.valueOf( name.substring(name.indexOf("@")+1, name.length()));
			name=name.substring(0,name.indexOf("@"));
			Item item = new Item(name, key, sort);
			items.add(item);
		}
		Collections.sort(items,new Comparator<Item>(){   
	           public int compare(Item arg0, Item arg1) {   
	               return arg0.getSort().compareTo(arg1.getSort());   
	            }   
	        });  
		cbbSearchValue.setModel(new DefaultComboBoxModel(items.toArray()));
		cbbSearchValue.setSelectedIndex(-1);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(610, 365);
		this.setContentPane(getJContentPane());
		this.setTitle("数据字典查询");
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerLocation(185);
			jSplitPane.setDividerSize(10);
			jSplitPane.setTopComponent(getPnTop());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setOneTouchExpandable(true);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbTable() {
		if (tbTable == null) {
			tbTable = new JTable();
			tbTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if (e.getValueIsAdjusting()) {
								return;
							}
							JTableListModel tableModel = (JTableListModel) tbTable
									.getModel();
							if (tableModel == null) {
								return;
							}

							try {
								TempNodeItem key = (TempNodeItem) tableModel
										.getCurrentRow();
								initTbField(key);
							} catch (Exception cx) {
								cx.printStackTrace();
							}
						}
					});
		}
		return tbTable;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbField());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes tbField
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbField() {
		if (tbField == null) {
			tbField = new JTable() ;

		}
		return tbField;
	}

	/**
	 * 初始化主表
	 * 
	 */
	private void initTbTable(Map<TempNodeItem, List<TempNodeItem>> dataMap) {
		List<TempNodeItem> list = new ArrayList<TempNodeItem>();
		list.addAll(dataMap.keySet());
		Collections.sort(list);

		tableModel = new JTableListModel(tbTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("表名", "name", 150));
						list.add(addColumn("类名", "className", 180));
						list.add(addColumn("备注", "remark", 600));
						list.add(addColumn("详细类名", "className", 250));
						return list;
					}
				});
		tbTable.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							String entityName = value.toString();
							int lastIndex = entityName.lastIndexOf(".");
							if (lastIndex > -1) {
								super.setText(entityName
										.substring(lastIndex + 1));
							}
						}
						return this;
					}
				});
		tbTable.getColumnModel().getColumn(3).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							String html = CommonVariables.convertMessageToHtml(
									value.toString(), 100);
							setText(html);
						}
						return this;
					}
				});
		tableModel.packRows();
	}

	/**
	 * 初始化字段表
	 * 
	 */
	private void initTbField(TempNodeItem key) {
		List<TempNodeItem> list = new ArrayList<TempNodeItem>();
		if (isDisplayAll) {
			list = dataDictMap.get(key);
		} else {
			list = dataEmptyMap.get(key);
		}
		if (list == null) {
			list = new ArrayList<TempNodeItem>();
		}
		JTableListModel tableModel = new JTableListModel(tbField, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new ArrayList<JTableListColumn>();
						list.add(addColumn("字段名", "name", 150));
						list.add(addColumn("类字段", "cnName", 150));
						list.add(addColumn("字段类型", "className", 150));
						list.add(addColumn("备注", "remark", 200));
						return list;
					}
				});
		tbField.getColumnModel().getColumn(2).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							super.setText(getString(value.toString()));
						}
						return this;
					}

					protected String getString(String value) {
						String[] strs = value.split("\n");
						if (strs.length > 0) {
							return strs[0].toString().substring(4);
						} else {
							return "";
						}
					}
				});
		tbField.getColumnModel().getColumn(4).setCellRenderer(
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						if (value != null) {
							String html = CommonVariables.convertMessageToHtml(
									value.toString(), 100);
							setText(html);
						}
						return this;
					}
				});
		tableModel.packRows();
	}

	/**
	 * This method initializes pnTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnTop() {
		if (pnTop == null) {
			pnTop = new JPanel();
			pnTop.setLayout(new BorderLayout());
			pnTop.add(getJScrollPane(), BorderLayout.CENTER);
			pnTop.add(getJToolBar(), BorderLayout.NORTH);
		}
		return pnTop;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel = new JLabel();
			jLabel.setText("过滤模块");
			jToolBar = new JToolBar();
			jToolBar.add(getPnToolBar());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSearch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSearch() {
		if (btnSearch == null) {
			btnSearch = new JButton();
			btnSearch.setText("查询");
			btnSearch.setPreferredSize(new Dimension(60, 25));
			btnSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return btnSearch;
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.setPreferredSize(new Dimension(60, 25));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}

	/**
	 * This method initializes cbbSearchValue
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbSearchValue() {
		if (cbbSearchValue == null) {
			cbbSearchValue = new JComboBox();
			cbbSearchValue.setPreferredSize(new Dimension(250, 25));
		}
		return cbbSearchValue;
	}

	/**
	 * This method initializes pnToolBar
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnToolBar() {
		if (pnToolBar == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);
			flowLayout.setVgap(1);
			flowLayout.setHgap(1);
			pnToolBar = new JPanel();
			pnToolBar.setLayout(flowLayout);
			pnToolBar.add(jLabel, null);
			pnToolBar.add(getCbbSearchValue(), null);
			pnToolBar.add(getBtnSearch(), null);
			pnToolBar.add(getBtnEmpty(), null);
			pnToolBar.add(getBtnClose(), null);
		}
		return pnToolBar;
	}

	private void search() {
		if (cbbSearchValue.getSelectedIndex() == -1) {
			tableModel.filter(2, null, false, -1);
		} else {
			Item item = (Item) cbbSearchValue.getSelectedItem();
			tableModel.filter(2, item.getProperty(), false, -1);
		}
	}

	/**
	 * This method initializes btnEmpty
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnEmpty() {
		if (btnEmpty == null) {
			btnEmpty = new JButton();
			btnEmpty.setText("显示为空的字段");
			btnEmpty.setPreferredSize(new Dimension(130, 25));
			btnEmpty.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (isDisplayAll) {
						isDisplayAll = false;
						initTbTable(dataEmptyMap);
						btnEmpty.setText("显示全部");
					} else {
						initTbTable(dataDictMap);
						isDisplayAll = true;
						btnEmpty.setText("显示为空的字段");
					}
				}
			});
		}
		return btnEmpty;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
