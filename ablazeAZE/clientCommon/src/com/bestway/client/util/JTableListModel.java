package com.bestway.client.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.util.footer.JFooterTable;
import com.bestway.client.util.footer.JTableFooter;
import com.bestway.client.util.footer.TableFooterType;

/**
 * JBCUS系统所扩展的JTableModel 通过List数据源来显示&绑定数据 (贺巍添加部分注释)
 * 
 * @author 余鹏
 * 
 */
public class JTableListModel extends JTableListModelBase implements ListModel {

	/**
	 * 绑定的数据源List
	 */
	protected List listData = null;

	/**
	 * 适配器
	 */
	protected JTableListModelAdapter adapter = null;

	/**
	 * 绑定的表格
	 */
	protected JTable table = null;

	/**
	 * 自身
	 */
	protected JTableListModel model = this;

	/**
	 * 是否允许排序
	 */
	protected boolean isSort = true;

	/**
	 * 排序列
	 */
	protected int sortColumn = -1;

	/**
	 * 上一次的排序列
	 */
	protected int oldSortTableColumn = -1;

	/**
	 * 正序&逆序标志
	 */
	protected int sort = 1;

	/**
	 * 索引数组
	 */
	protected int[] indexes = null;

	/**
	 * 当前列号
	 */
	protected int currentColumn = 0;

	/**
	 * 快速排序的中间数
	 */
	protected Object midleObj = null;

	/**
	 * 每个列的值
	 */
	protected Hashtable htColumnValues = new Hashtable();

	/**
	 * 存放获取到列的值
	 */
	protected Object[] strColumnValues = null;

	/**
	 * 排序标志
	 */
	protected Hashtable htSortFlag = new Hashtable();

	/**
	 * 是否允许设置值
	 */
	private boolean isAllowSetValue = false;

	/**
	 * 整个表格的菜单
	 */
	private JTableContextPopupMenu jTableContextPopupMenu = null;
	/**
	 * 创建新数据回掉接口
	 */
	private CreateTableDataListener createTableDataListener = null;
	/**
	 * 选择模型
	 */
	private Integer selectionMode = null;

	/**
	 * RuleBasedCollator 类是 Collator 的具体子类，它提供了一个简单的、数据驱动的表 collator。
	 * 可以通过此类来创建基于表的自定义 Collator。RuleBasedCollator 将字符映射到排序键。
	 */
	private RuleBasedCollator collator;

	/**
	 * 存放每行最大行高
	 */
	private Map<Integer, Map<Integer, Integer>> mapColumnHeight = new Hashtable<Integer, Map<Integer, Integer>>();
	/**
	 * 已编辑的数据
	 */
	private List editedData = new ArrayList();
	/**
	 * 存放表页脚标
	 */
	private Map<Integer, TableFooterType> footerMap = new HashMap<Integer, TableFooterType>();

	public CreateTableDataListener getCreateTableDataListener() {
		return createTableDataListener;
	}

	public void setCreateTableDataListener(
			CreateTableDataListener createTableDataListener) {
		this.createTableDataListener = createTableDataListener;
	}

	public Map<Integer, TableFooterType> getFooterMap() {
		return footerMap;
	}

	public boolean isAllowSetValue() {
		return isAllowSetValue;
	}

	public void setAllowSetValue(boolean isSetValue) {
		this.isAllowSetValue = isSetValue;
	}

	public Map<Integer, Map<Integer, Integer>> getMapColumnHeight() {
		return mapColumnHeight;
	}

	public void addEditedData(Object obj) {
		if (!this.editedData.contains(obj)) {
			this.editedData.add(obj);
		}
		this.fireTableDataChanged();
	}

	public JTableListModel(JTable table, List list,
			JTableListModelAdapter adapter) {
		this(table, list, adapter, null);
	}

	public JTableListModel(JTable table, List list,
			JTableListModelAdapter adapter, Integer selectionMode) {
		initData(table, list, adapter, selectionMode);
	}

	/**
	 * 初始化数据
	 * 
	 * @param table
	 * @param list
	 * @param adapter
	 * @param selectionMode
	 */
	private void initData(JTable table, List list,
			JTableListModelAdapter adapter, Integer selectionMode) {
		if (list != null) {
			this.listData = list;
		} else {
			this.listData = new ArrayList();
		}

		this.table = table;

		this.adapter = adapter;

		this.adapter.setTable(table);

		this.selectionMode = selectionMode;

		this.collator = (RuleBasedCollator) Collator
				.getInstance(java.util.Locale.CHINA);

		this.table.setRowHeight(0, 1000);

		// this.table.setRowHeight(8);
		this.allocateIndexes();

		//
		// 把删除后的加入
		//
		TableModel old = this.table.getModel();

		if (old != null && old != this) {

			if (old instanceof JTableJarDataModel) {

				JTableJarDataModel jarTableModel = (JTableJarDataModel) old;

				addTableListSelectionListeners(jarTableModel
						.getListSelectionListeners());
			}
		}
		this.table.setModel(this);

		this.table.setDoubleBuffered(true);

		this.table.setRowSelectionAllowed(true);

		this.table.setColumnSelectionAllowed(true);
		//
		// event
		//
		this.addMouseListener(table);
		//
		// 设置第一列 render
		//
		this.setSerialColumnRender(table);
		// ---------------------动态设置表中各列的数据类型，为排序方便
		aotuSetColumnsTpye();// by kcb 2008/3/14
		// ---------------------动态设置表中各列的数据类型，为排序方便
		//
		// init jTableContextPopupMenu
		//
		jTableContextPopupMenu = new JTableContextPopupMenu(this, this.table,
				adapter, selectionMode);
		//
		// 设置列的宽度
		//
		setColumnPreferredWidth();
		//
		// 设置选择 Model
		//
		if (selectionMode == null) {
			this.table
					.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		} else {
			this.table.setSelectionMode(selectionMode);
		}
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//

		if (table instanceof JFooterTable) {
			this.table.getTableHeader().setReorderingAllowed(false);
		}
		//
		// tableModel 刷新数据
		//
		this.fireTableDataChanged();
		this.setSingleSelectionBackground();
		this.statisticTableFooterInfo();
		// SwingUtilities.invokeLater(notifyTableRunnable);
		this.setSelectFirstRow();

		this.table.setRowHeight(23);
	}

	// /**
	// * 打开表的时候，表格的第一行被选中
	// */
	// private void setSelectFirstRow() {
	// if (selectionMode == null
	// || (selectionMode != ListSelectionModel.SINGLE_SELECTION && selectionMode
	// != ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)) {
	// if (list.size() > 0) {
	// table.setRowSelectionInterval(0, 0);
	// setTableSelectRowColor(0);
	// }
	// }
	// }

	private void setSingleSelectionBackground() {
		if (table.getSelectionModel().getSelectionMode() == ListSelectionModel.SINGLE_SELECTION) {
			int columnCount = table.getColumnCount();
			for (int i = 1; i < columnCount; i++) {
				table.getColumnModel().getColumn(i)
						.setCellRenderer(new SelectRowColorTableCellRenderer());
			}
		}
	}

	/**
	 * 打开表的时候，表格的第一行被选中
	 */
	private void setSelectFirstRow() {
		if (selectionMode == null
				|| (selectionMode != ListSelectionModel.SINGLE_SELECTION && selectionMode != ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)) {
			if (indexes.length > 0) {
				table.setRowSelectionInterval(0, 0);
				setTableSelectRowColor(0);
			}
		} else if (selectionMode == ListSelectionModel.SINGLE_SELECTION) {
			if (indexes.length > 0) {
				table.setRowSelectionInterval(0, 0);
			}
		}
	}

	/**
	 * 设置表格 序列col 的渲染器
	 * 
	 * @param jTable
	 */
	private void setSerialColumnRender(JTable jTable) {
		table.setDefaultRenderer(SerialColumn.class,
				new DefaultTableCellRenderer() {
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
						JTableHeader header = table.getTableHeader();
						if (header != null) {
							setForeground(header.getForeground());
							setBackground(header.getBackground());
							setFont(header.getFont());
						}
						setHorizontalAlignment(JLabel.CENTER);
						super.setText(String.valueOf(row + 1));
						return this;
					}
				});
	}

	public void setCellRenderer(TableCellRenderer tableCellRenderer, int column) {

		// table.getColumnModel().getColumn(column).setCellRenderer(
		// tableCellRenderer);
		// table.getColumnModel().getColumn(column).setCellRenderer(
		// new DefaultTableCellRenderer() {
		// public Component getTableCellRendererComponent(
		// JTable table, Object value, boolean isSelected,
		// boolean hasFocus, int row, int column) {
		// super.getTableCellRendererComponent(table, value,
		// isSelected, hasFocus, row, column);
		// if (isSelected) {
		// setForeground(new Color(0, 0, 204));
		// setBackground(table.getSelectionBackground());
		// } else {
		// if (row == table.getSelectedRow()) {
		// setForeground(table.getSelectionForeground());
		// setBackground(table.getSelectionBackground());
		// } else {
		// setForeground(table.getForeground());
		// setBackground(table.getBackground());
		// }
		// }
		// String str = "";
		// if (value != null) {
		// str = ImpExpType.getImpExpTypeDesc(Integer
		// .parseInt(value.toString()));
		// }
		// this.setText(str);
		// return this;
		// }
		// });
	}

	/**
	 * 重新加入TableListSelectionListeners监听器
	 * 
	 * @param listSelectionListeners
	 */
	private void addTableListSelectionListeners(
			ListSelectionListener[] listSelectionListeners) {
		DefaultListSelectionModel defaultListSelectionModel = (DefaultListSelectionModel) this.table
				.getSelectionModel();
		for (ListSelectionListener l : listSelectionListeners) {
			if (l instanceof JTable) {
				continue;
			}
			defaultListSelectionModel.addListSelectionListener(l);
		}
	}

	/**
	 * 设置表格中被选中行的颜色
	 * 
	 * @param selectRow
	 */
	private void setTableSelectRowColor(final int selectRow) {
		if (table.getSelectionModel().getSelectionMode() == ListSelectionModel.SINGLE_SELECTION
				|| table.getSelectionModel().getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
			return;
		}
		// 只针对没有SerialColumn序号的字段的条件
		if (table.getColumnCount() < this.getColumnCount()) {
			table.setColumnSelectionInterval(0, table.getColumnCount() - 1);
		} else if (table.getColumnCount() > 1) {
			table.setColumnSelectionInterval(1, table.getColumnCount() - 1);
		}

		// int columnCount = table.getColumnCount();
		// boolean allNot = true;
		// for (int i = 1; i < columnCount; i++) {
		// if (table.getColumnModel().getColumn(i).getCellRenderer() instanceof
		// SelectRowColorTableCellRenderer) {
		// allNot = false;
		// break;
		// }
		// }
		// for (int i = 1; i < columnCount; i++) {
		// if (allNot) {
		// table.getColumnModel().getColumn(i).setCellRenderer(
		// new SelectRowColorTableCellRenderer(selectRow));
		// } else {
		// if (table.getColumnModel().getColumn(i).getCellRenderer() instanceof
		// SelectRowColorTableCellRenderer) {
		// table.getColumnModel().getColumn(i).setCellRenderer(
		// new SelectRowColorTableCellRenderer(selectRow));
		// }
		// }
		// }
		// table.repaint();
		// table.validate();
	}

	// class SelectRowColorTableCellRenderer extends DefaultTableCellRenderer {
	// /**
	// *
	// */
	// private static final long serialVersionUID = 1L;
	//
	// private int selectRow;
	//
	// public SelectRowColorTableCellRenderer(int selectRow) {
	// this.selectRow = selectRow;
	// }
	//
	// public Component getTableCellRendererComponent(JTable table,
	// Object value, boolean isSelected, boolean hasFocus, int row,
	// int column) {
	// super.getTableCellRendererComponent(table, value, isSelected,
	// hasFocus, row, column);
	// if (isSelected) {
	// setForeground(table.getSelectionForeground());
	// setBackground(table.getSelectionBackground());
	// } else {
	// if (row == selectRow && row == table.getSelectedRow()) {
	// setForeground(table.getSelectionForeground());
	// setBackground(table.getSelectionBackground());
	// } else {
	// setForeground(table.getForeground());
	// setBackground(table.getBackground());
	// }
	// }
	// return this;
	// }
	// }
	/**
	 * 数据绑定
	 */
	public static void dataBind(JTable table, List list,
			JTableListModelAdapter adapter) {
		new JTableListModel(table, list, adapter);
	}

	public static void dataBind(JTable table, List list,
			JTableListModelAdapter adapter, int selectionMode) {
		new JTableListModel(table, list, adapter, selectionMode);
	}

	/**
	 * 获取元素所在的位置
	 */
	public Object getElementAt(int index) {
		return String.valueOf(index + 1);
	}

	/**
	 * 设置表的格子的可编辑
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (adapter != null) {
			List list = adapter.getEditableColumn();
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (columnIndex == ((Integer) list.get(i)).intValue()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public int getSize() {
		return (indexes == null ? 0 : indexes.length);
	}

	/**
	 * 返回当前行数
	 */
	public int getCurrRowCount() {
		return getEndCurrRow();
	}

	/**
	 * 添加数据监听器
	 */
	public void addListDataListener(ListDataListener a) {
	}

	/**
	 * 删除数据监听器
	 */
	public void removeListDataListener(ListDataListener a) {
	}

	/**
	 * 设置适配器
	 * 
	 * @param adapter
	 */
	public void setAdapter(JTableListModelAdapter adapter) {
		this.adapter = adapter;
	}

	/**
	 * 得到列数
	 */
	public int getColumnCount() {
		return adapter.getColumnCount();
	}

	public List<JTableListColumn> getColumns() {
		return adapter.getColumns();
	}

	public int getRowCount() {
		return getSize();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {

		if (isAllowSetValue) {

			adapter.setCellData(listData.get(indexes[row]), value, col);

		}
	}

	public Object getValueAt(int row, int col) {
		try {
			return adapter.getCellData(listData.get(indexes[row]), col);
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Object getValueAt(Object obj, int col) {
		return adapter.getValue(obj, col);
	}

	public Object getValueAt(int row) {
		return listData.get(indexes[row]);
	}

	/**
	 * 得到列的名称
	 */
	public String getColumnName(int i) {
		String caption = adapter.getColumnCaption(i);
		if (sortColumn == i) {
			caption = caption + getSortStr();
		}
		return caption;
	}

	/**
	 * 得到列的宽度
	 */
	public int getColumnWidth(int i) {
		return adapter.getColumnWidth(i);
	}

	/**
	 * 得到列的类型
	 */
	public Class getColumnClass(int c) {
		return adapter.getColumnClass(c);
	}

	/**
	 * 获取属性名称
	 * 
	 * @param c
	 * @return
	 */
	public String getPropertyName(int c) {
		return adapter.getPropertyName(c);
	}

	/**
	 * 根据列数得到列的类型
	 */
	public Class getTypeByColumn(int col) {
		Class cls = listData.get(0).getClass();
		return adapter.getTypeByColumn(cls, col);
	}

	/**
	 * 往表格里增加一行信息
	 * 
	 * @param tempList
	 */
	public synchronized void addRow(Object obj) {
		listData.add(obj);
		int[] indexs = new int[listData.size()];
		System.arraycopy(indexes, 0, indexs, 0, indexes.length);
		indexs[listData.size() - 1] = listData.size() - 1;
		indexes = indexs;
		fireTableDataChanged();
		// SwingUtilities.invokeLater(notifyTableRunnable);
		htColumnValues.clear();
		table.setRowSelectionInterval(listData.size() - 1, listData.size() - 1);
		setTableSelectRowColor(listData.size() - 1);
		table.invalidate();
		table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0,
				false));
		this.statisticTableFooterInfo();
	}

	public synchronized void moveUp() {
		Object obj = this.getCurrentRow();
		int index = findIndex(obj);
		if (index < 0) {
			return;
		}
		this.deleteRow(obj);
		this.addRow(--index, obj);
	}

	public synchronized void moveDown() {
		Object obj = this.getCurrentRow();
		int index = findIndex(obj);
		if (index < 0) {
			return;
		}
		this.deleteRow(obj);
		this.addRow(index++, obj);
	}

	/**
	 * 往表格里增加一行信息
	 * 
	 * @param tempList
	 */
	public synchronized void addRow(int index, Object obj) {
		List list = new ArrayList();
		list.add(obj);
		addRows(index, list);
	}

	/**
	 * luosheng 2008,12,30 日加入,也许没有什么作用, 经过测试可用. 如果要插入到那一条时才用.为了用户体验才加.
	 * 
	 * @param tempList
	 */
	public synchronized void addRows(int index, List tempList) {
		if (tempList.isEmpty()) {
			return;
		}
		if (index < 0) {
			index = 0;
		}
		boolean isAddLast = false;
		if (index > listData.size() - 1) {
			index = listData.size();
			isAddLast = true;
		}
		//
		// example : length = 10 indexAdd = 4 addCount = 2
		// length = 12
		//
		listData.addAll(index, tempList);
		int addCount = tempList.size();

		int indexAddBeginPoint = index;
		int indexAddEndPoint = index + addCount;

		int[] indexs = new int[listData.size()];
		System.arraycopy(indexes, 0, indexs, 0, indexAddBeginPoint);

		if (!isAddLast) {
			System.arraycopy(indexes, indexAddBeginPoint, indexs,
					indexAddEndPoint, indexes.length - indexAddBeginPoint);
		}

		for (int i = 0; i < addCount; i++) {
			indexs[indexAddBeginPoint + i] = indexAddBeginPoint + i;
		}
		for (int i = indexAddEndPoint; i < indexs.length; i++) {
			indexs[i] += addCount;
		}
		indexes = indexs;
		fireTableDataChanged();
		htColumnValues.clear();
		table.setRowSelectionInterval(indexAddBeginPoint, indexAddEndPoint - 1);
		setTableSelectRowColor(listData.size() - 1);
		table.invalidate();
		table.scrollRectToVisible(table.getCellRect(indexAddBeginPoint, 0,
				false));
		this.statisticTableFooterInfo();
	}

	public boolean isSort() {
		return isSort;
	}

	public void setSort(boolean isSort) {
		this.isSort = isSort;
	}

	/**
	 * 往表格里增加一行或多行信息
	 * 
	 * @param tempList
	 */
	public synchronized void addRows(List tempList) {
		if (tempList == null || tempList.size() <= 0) {
			return;
		}
		listData.addAll(tempList);
		int[] indexs = new int[listData.size()];
		System.arraycopy(indexes, 0, indexs, 0, indexes.length);
		for (int i = tempList.size(); i > 0; i--) {
			indexs[listData.size() - i] = listData.size() - i;
		}
		indexes = indexs;
		fireTableDataChanged();
		// SwingUtilities.invokeLater(notifyTableRunnable);
		htColumnValues.clear();
		if (listData.size() > 0 && table.getRowCount() > 0) {
			int selectIndex = listData.size() - tempList.size();
			if (selectIndex < 0) {
				selectIndex = 0;
			}

			if (selectIndex > table.getRowCount() - 1) {
				selectIndex = table.getRowCount() - 1;
			}

			table.setRowSelectionInterval(selectIndex, selectIndex);
			setTableSelectRowColor(selectIndex);
			// table.setRowSelectionInterval(list.size() - 1, list.size() - 1);
			// setTableSelectRowColor(list.size() - 1);
			table.invalidate();
			table.scrollRectToVisible(table.getCellRect(
					table.getRowCount() - 1, 0, false));
		}
		this.statisticTableFooterInfo();
	}

	/**
	 * 设置数据源
	 * 
	 * @param list
	 */
	public void setList(List list) {
		this.listData = list;
		htColumnValues.clear();
		if ((this.listData != null) && (this.adapter != null)) {
			initData(table, list, adapter, selectionMode);
			// allocateIndexes();
			// setColumnPreferredWidth();
			// fireTableDataChanged();
		}
	}

	public List getList() {
		return listData;
	}

	/**
	 * 更新行信息
	 * 
	 * @param obj
	 */
	public synchronized void addRowAfter(Object target, Object obj) {
		int index = findIndex(target);
		if (index >= 0) {
			this.addRow(index + 1, obj);
		} else {
			this.addRow(obj);
		}

	}

	/**
	 * 更新行信息
	 * 
	 * @param obj
	 */
	public synchronized void addRowAfter(Object target, List obj) {
		int index = findIndex(target);
		if (index >= 0) {
			this.addRow(index + 1, obj);
		} else {
			this.addRow(obj);
		}
	}

	/**
	 * 更新行信息
	 * 
	 * @param obj
	 */
	public synchronized void updateRow(Object obj) {
		int index = findIndex(obj);
		if (index >= 0) {
			listData.set(index, obj);
		} else {
			return;
		}
		fireTableDataChanged();
		// SwingUtilities.invokeLater(notifyTableRunnable);
		htColumnValues.clear();
		int tableIndex = getTableIndex(index);
		if (tableIndex >= 0 && tableIndex <= table.getRowCount() - 1) {
			table.setRowSelectionInterval(tableIndex, tableIndex);
			setTableSelectRowColor(tableIndex);
		}
		this.statisticTableFooterInfo();
	}

	/**
	 * 更新行信息
	 * 
	 * @param obj
	 */
	public synchronized void updateRows(List listSource) {
		for (int i = 0; i < listSource.size(); i++) {
			Object obj = listSource.get(i);
			int index = findIndex(obj);
			if (index >= 0) {
				listData.set(index, obj);
			}
		}
		fireTableDataChanged();
		// SwingUtilities.invokeLater(notifyTableRunnable);
		htColumnValues.clear();
		this.statisticTableFooterInfo();
	}

	/**
	 * 删除一行
	 * 
	 * @param obj
	 */
	public synchronized void deleteRow(Object obj) {
		int index = findIndex(obj);
		if (index < 0) {
			return;
		}
		int indexRowDelete = table.getSelectedRow();
		int indexDelete = getTableIndex(index);
		int[] indexsFront = new int[indexDelete];
		int[] indexsBack = new int[listData.size() - indexDelete - 1];
		System.arraycopy(indexes, 0, indexsFront, 0, indexDelete);
		System.arraycopy(indexes, indexDelete + 1, indexsBack, 0,
				listData.size() - indexDelete - 1);
		int[] indexs = new int[listData.size() - 1];
		System.arraycopy(indexsFront, 0, indexs, 0, indexDelete);
		System.arraycopy(indexsBack, 0, indexs, indexDelete, listData.size()
				- indexDelete - 1);
		for (int i = 0; i < indexs.length; i++) {
			if (indexs[i] > index) {
				indexs[i]--;
			}
		}
		indexes = indexs;
		listData.remove(index);
		htColumnValues.clear();
		fireTableDataChanged();

		int selectionRow = indexs.length - 1;
		if (indexRowDelete < indexs.length - 1) {
			selectionRow = indexRowDelete;
		}
		if (selectionRow >= 0) {
			table.setRowSelectionInterval(selectionRow, selectionRow);
			setTableSelectRowColor(listData.size() - 1);
			table.invalidate();
			table.scrollRectToVisible(table.getCellRect(selectionRow, 0, false));
		}
		// SwingUtilities.invokeLater(notifyTableRunnable);

		this.statisticTableFooterInfo();
	}

	/**
	 * 删除行信息
	 * 
	 * @param tempList
	 */
	public synchronized void deleteRows(List list) {
		// for (int i = 0; i < list.size(); i++) {
		// Object obj = list.get(i);
		// this.deleteRow(obj);
		// }
		for (int i = list.size() - 1; i >= 0; i--) {
			Object obj = list.get(i);
			this.deleteRow(obj);
		}
		this.statisticTableFooterInfo();
	}

	/**
	 * 获取当前行中的数据对象
	 * 
	 * @param tempList
	 */
	public Object getCurrentRow() {
		Object currObj = null;
		int currRow = getCurrRow();
		if (currRow < 0)
			return null;
		Object obj = null;
		if (currRow <= listData.size()) {
			obj = listData.get(currRow);
		}
		if (obj != null) {
			currObj = obj;
		}
		return currObj;
	}

	/**
	 * 设置表的选中行
	 *
	 * @param obj
	 */
	public void selectRowByData(Object obj) {
		int index = findIndex(obj);
		if (index >= 0) {
			int row = table.convertRowIndexToView(index);// getTableIndex(listIndex);
			if (row >= 0) {
				this.setSelectRow(row);
			}
		}
	}

	/**
	 * 被选中的行
	 * 
	 * @param tempList
	 */
	public synchronized void setSelectRow(int tableIndex) {
		if (tableIndex >= 0 && tableIndex <= table.getRowCount() - 1) {
			table.setRowSelectionInterval(tableIndex, tableIndex);
			setTableSelectRowColor(tableIndex);
		}
	}

	/**
	 * 根据给定的行号，返回list所对应行中的对象。 由于有新的<上笔><下笔>公用方法，所以此方法以后逐渐停止使用。
	 * 
	 * @param row
	 * @return
	 */
	public Object getObjectByRow(int row) {
		Object obj = null;
		if (row >= 0 && row < listData.size()) {
			obj = listData.get(indexes[row]);
		}
		table.setRowSelectionInterval(row, row);
		this.setTableSelectRowColor(row);
		return obj;
	}

	/**
	 * 根据给定的行号，返回list所对应行中的对象。 (此方法仅仅是根据table的行数来返回对象，请大家以后不要在此方法上加其他东西)
	 * 
	 * @param row
	 * @return
	 */
	public Object getDataByRow(int row) {
		Object obj = null;
		if (row >= 0 && row < listData.size()) {
			obj = listData.get(indexes[row]);
		}
		return obj;
	}

	/**
	 * 得到当前所有行数据
	 * 
	 * @return list
	 */
	public List getCurrentRows() {

		// 首先取得所有行数
		int[] rows = table.getSelectedRows();

		List ls = new ArrayList();

		for (int i = 0; i < rows.length; i++) {
			ls.add(listData.get(indexes[rows[i]]));
		}

		return ls;
	}

	/**
	 * 根据选择顺序添加到List
	 */
	public List getCurrentRowsSele() {
		List ls = new ArrayList();
		if (table.getSelectedRow() == -1) {
			if (table.getRowCount() > 0) {
				ls.add(listData.get(0));
			}
		} else {
			int[] rows = table.getSelectedRows();
			for (int i = 0; i < rows.length; i++) {
				ls.add(listData.get(indexes[rows[i]]));
			}
		}

		return ls;
	}

	/**
	 * 根据排列顺序添加到LIst
	 */
	public List getCurrentRowsOrder() {
		List ls = new ArrayList();
		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] == -1) {
				continue;
			}
			ls.add(listData.get(indexes[i]));
		}
		return ls;
	}

	/**
	 * 通过选中当前行返回Hashtable的对象
	 * 
	 * @param hs
	 * @return
	 */
	public List getCurrentRowsByRowSelect(Hashtable hs) {
		List ls = new ArrayList();
		for (int j = 0; j < hs.keySet().toArray().length; j++) {
			String name = (String) hs.keySet().toArray()[j];
			ls.add(listData.get(Integer.parseInt(name)));
		}
		return ls;
	}

	/**
	 * 是否结束
	 * 
	 * @param name
	 * @return
	 */
	public boolean isExist(String name) {
		int[] s = table.getSelectedColumns();
		for (int i = 0; i < s.length; i++) {
			if (String.valueOf(s[i]).equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置表的选中行
	 * 
	 * @param obj
	 */
	public void setTableSelectedRow(Object obj) {
		int listIndex = findIndex(obj);
		int tableIndex = getTableIndex(listIndex);
		table.scrollRectToVisible(table.getCellRect(tableIndex, 0, false));
		table.setRowSelectionInterval(tableIndex, tableIndex);
		setTableSelectRowColor(tableIndex);
	}

	/**
	 * 设置表的选中行
	 * 
	 * @param row
	 */
	public void setTableSelectRowByRow(int row) {
		if (table.getRowCount() <= 0) {
			return;
		}
		int tableIndex = getTableIndex(row - 1);
		table.scrollRectToVisible(table.getCellRect(tableIndex, 0, false));
		table.setRowSelectionInterval(tableIndex, tableIndex);
		setTableSelectRowColor(tableIndex);
	}

	/**
	 * 得到表格页脚的统计信息
	 */
	public void statisticTableFooterInfo() {
		if (table instanceof JFooterTable) {
			JTableFooter tableFooter = ((JFooterTable) table).getTableFooter();
			if (tableFooter != null) {
				tableFooter.statisticTableFooterInfo();
			}
		}
	}

	/**
	 * 第一条记录被选择
	 */
	public void setTableFirstSelectRow() {
		if (table.getRowCount() <= 0) {
			return;
		}
		int tableIndex = getTableIndex(0);
		table.scrollRectToVisible(table.getCellRect(tableIndex, 0, false));
		table.setRowSelectionInterval(tableIndex, tableIndex);
	}

	/**
	 * 最后一条记录被选择
	 */
	public void setTableEndSelectRow() {
		if (table.getRowCount() <= 0) {
			return;
		}
		int tableIndex = getTableIndex(table.getRowCount() - 1);
		table.scrollRectToVisible(table.getCellRect(tableIndex, 0, false));
		table.setRowSelectionInterval(tableIndex, tableIndex);
	}

	/**
	 * 选中当前行
	 * 
	 * @return
	 */
	protected int getCurrRow() {
		int currRow = -1;
		if (table.getSelectedRow() >= 0) {
			if (indexes.length > table.getSelectedRow()) {
				currRow = indexes[table.getSelectedRow()];
			}
		}
		return currRow;
	}

	/**
	 * 选中当表中当前的最后一行
	 * 
	 * @return
	 */
	protected int getEndCurrRow() {
		int currRow = -1;
		/*
		 * if (table.getSelectedRow() >= 0) { if (indexes.length >
		 * table.getSelectedRow()) { currRow = indexes[table.getSelectedRow()];
		 * } }
		 */
		currRow = table.getSelectedRow();
		return currRow;
	}

	/**
	 * 得到索引
	 * 
	 * @param listIndex
	 * @return
	 */
	protected int findIndex(Object obj) {
		if (obj == null) {
			return -1;
		}
		for (int i = 0; i < listData.size(); i++) {
			if (obj.equals(listData.get(i)))
				return i;
		}
		return -1;
	}

	/**
	 * 得到表的索引
	 * 
	 * @param listIndex
	 * @return
	 */
	protected int getTableIndex(int listIndex) {
		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] == listIndex) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 增加鼠标监听器
	 * 
	 * @param table
	 */
	public void addMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getTableHeader()
				.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof SortMouseListener) {
				table.getTableHeader().removeMouseListener(mouseListeners[i]);
			}
		}
		table.getTableHeader().setCursor(new Cursor(Cursor.HAND_CURSOR));
		table.getTableHeader().addMouseListener(new SortMouseListener(this));
	}

	/**
	 * 鼠标点击排序事件
	 * 
	 * @author ?
	 * 
	 */
	public class SortMouseListener extends MouseAdapter {
		private JTableListModel tableListModel;

		public SortMouseListener(JTableListModel tableListModel) {
			this.tableListModel = tableListModel;
		}

		/**
		 * 设置鼠标点击事件监听
		 */
		public void mouseClicked(MouseEvent event) {
			if (!isSort) {
				return;
			}
			if (tableListModel.table.getCellEditor() != null) {
				tableListModel.table.getCellEditor().stopCellEditing();
			}
			//
			// 一定是左键才行
			//
			if (event.getModifiers() != InputEvent.BUTTON1_MASK) {
				return;
			}
			int tableColumn = tableListModel.table.columnAtPoint(event
					.getPoint());
			if (tableColumn < 0) {
				return;
			}
			int modelColumn = tableListModel.table
					.convertColumnIndexToModel(tableColumn);
			if (tableListModel.adapter.getColumn(modelColumn) instanceof SerialColumn) {
				return;
			}
			table.getTableHeader().setCursor(new Cursor(Cursor.WAIT_CURSOR));

			// 改变table的排序顺序

			int oldModelColumn = sortColumn;
			int oldTableColumn = oldSortTableColumn;
			sortColumn = modelColumn;
			oldSortTableColumn = tableColumn;
			if (oldModelColumn == modelColumn) {
				sort = sort * (-1);
				htSortFlag.put(Integer.valueOf(sortColumn),
						Integer.valueOf(sort));
			} else {
				Object obj = htSortFlag.get(Integer.valueOf(sortColumn));
				if (obj == null) {
					sort = 1;
					htSortFlag.put(Integer.valueOf(sortColumn),
							Integer.valueOf(sort));
				} else {
					sort = (-1) * Integer.parseInt(obj.toString());
				}
			}
			if (oldTableColumn >= 0) {
				table.getColumnModel()
						.getColumn(oldTableColumn)
						.setHeaderValue(
								tableListModel.getColumnName(oldModelColumn));
			}

			// 改变table表头的标题。主要是加上"△"或者"▽"

			table.getColumnModel().getColumn(tableColumn)
					.setHeaderValue(tableListModel.getColumnName(modelColumn));
			table.getColumnModel()
					.getColumn(tableColumn)
					.setPreferredWidth(
							table.getColumnModel().getColumn(tableColumn)
									.getWidth()
									+ sort);

			tableListModel.sortByColumn(modelColumn);

			table.getTableHeader().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	 * 得到自动排序的图标（表头）
	 * 
	 * @return
	 */
	protected String getSortStr() {
		if (sort == 1) {
			return "△";
		} else {
			return "▽";
		}
	}

	/**
	 * 通知表的线程
	 */
	protected Runnable notifyTableRunnable = new Runnable() {
		public void run() {
			fireTableDataChanged();
		}
	};

	/**
	 * 构造索引indexes(仅初始化时调用)
	 */
	protected void allocateIndexes() {
		if (listData == null) {
			return;
		}
		int rowCount = listData.size();
		indexes = new int[rowCount];
		for (int row = 0; row < rowCount; row++) {
			indexes[row] = row;
		}
	}

	/**
	 * 根据当前行的值过滤 (模糊查询 查找定位)
	 * 
	 * @param column
	 * @param value
	 * @param isEquals
	 */
	public void filter(int column, String value, boolean isEquals) { // isEquals
		// 是否精确
		if (value == null || value.equals("")) {
			allocateIndexes();
			SwingUtilities.invokeLater(notifyTableRunnable);
			return;
		}
		Object[] mstr = getColumnValues(column);
		List rows = new ArrayList();
		if (!isEquals) {
			value = ".*?" + value + ".*?";
		}
		for (int i = 0; i < mstr.length; i++) {
			String sValue = (String) mstr[i];
			if (sValue != null) {
				if (sValue.matches(value)) {
					rows.add(Integer.valueOf(i));
				}
			}
		}
		indexes = new int[rows.size()];
		for (int row = 0; row < rows.size(); row++) {
			indexes[row] = ((Integer) rows.get(row)).intValue();
		}
		SwingUtilities.invokeLater(notifyTableRunnable);
	}

	/**
	 * 根据当前行的值过滤 (模糊查询 查找定位)
	 * 
	 * @param column
	 * @param value
	 * @param isEquals
	 * @param filedcolumn
	 */
	public void filter(int column, String value, boolean isEquals,
			int filedcolumn) { // isEquals 是否精确 filedcolumn : 0
		if (value == null || value.equals("")) {
			allocateIndexes();
			SwingUtilities.invokeLater(notifyTableRunnable);
			return;
		}
		Object[] mstr = getColumnValues(column);
		List<Integer> rows = new ArrayList<Integer>();
		if (column == filedcolumn + 1) {
			String[] yy = value.split(",");
			for (int j = 0; j < yy.length; j++) {// value
				value = yy[j];
				if (!isEquals) {
					value = ".*?" + specialString(value) + ".*?";
				}
				for (int i = 0; i < mstr.length; i++) {// shuju
					String sValue = (String) mstr[i];
					if (sValue != null && sValue.matches(value)) {
						rows.add(Integer.valueOf(i));
					}
				}
			}
		} else {
			if (!isEquals) {
				value = ".*?" + specialString(value) + ".*?";
			}
			for (int i = 0; i < mstr.length; i++) {
				String sValue = (String) mstr[i];
				if (sValue != null) {
					if (sValue.matches(value)) {
						rows.add(Integer.valueOf(i));
					}
				}
			}
		}
		indexes = new int[rows.size()];
		for (int row = 0; row < rows.size(); row++) {
			indexes[row] = ((Integer) rows.get(row)).intValue();
		}
		SwingUtilities.invokeLater(notifyTableRunnable);
	}

	/**
	 * 特殊字符串替换
	 * 
	 * @param value
	 * @return
	 */
	private String specialString(String value) {
		if (value != null) {
			value = value.replace("\\", "\\\\");
			value = value.replace("*", "\\*");
			value = value.replace("+", "\\+");
			value = value.replace("(", "\\(");
			value = value.replace(")", "\\)");
			value = value.replace("{", "\\{");
			value = value.replace("}", "\\}");
			value = value.replace("[", "\\[");
			value = value.replace("]", "\\]");
		}
		return value;
	}

	/**
	 * 获取合并行的行号
	 * 
	 * @param column
	 * @param value
	 * @param isAllMatching
	 * @param isMatchCase
	 * @param isPrefixMatching
	 * @return
	 */
	public int[] getMatchingRows(int column, String value,
			boolean isAllMatching, boolean isMatchCase, boolean isPrefixMatching) {
		Object[] mstr = getColumnValues(column);
		Map<Integer, Integer> rowMap = new HashMap<Integer, Integer>();
		value = specialString(value);
		if (isMatchCase == false) {
			value = value.toLowerCase();
		}
		if (isPrefixMatching == true) {
			value = value + ".*?";
		} else if (isAllMatching == false) {
			value = ".*?" + value + ".*?";
		}
		for (int i = 0; i < mstr.length; i++) {
			String sValue = (String) mstr[i];
			if (sValue != null) {
				if (isMatchCase == false) {
					sValue = sValue.toLowerCase();
				}
				if (sValue.matches(value)) {
					rowMap.put(new Integer(i), new Integer(i));
				}
			}
		}
		List<Integer> rowList = new ArrayList<Integer>();
		if (rowMap.size() > 0) {
			for (int i = 0; i < this.indexes.length; i++) {
				Object obj = rowMap.get(new Integer(this.indexes[i]));
				if (obj != null) {
					rowList.add(new Integer(i));
				}
			}
		}
		int[] rows = new int[rowList.size()];
		for (int i = 0; i < rowList.size(); i++) {
			rows[i] = rowList.get(i);
		}
		return rows;
	}

	/**
	 * 得到列的属性
	 * 
	 * @param col
	 * @return
	 */
	public String getColumnProperty(int col) {
		return this.adapter.getColumnProperty(col);
	}

	/**
	 * 得到列的值
	 * 
	 * @param column
	 * @return
	 */
	protected Object[] getColumnValues(int column) {
		if (htColumnValues.get(String.valueOf(column)) == null) {
			Object[] str = new String[listData.size()];
			for (int i = 0; i < listData.size(); i++) {
				str[i] = adapter.getCellData(listData.get(i), column);
			}
			htColumnValues.put(String.valueOf(column), str);
		}
		return (Object[]) htColumnValues.get(String.valueOf(column));
	}

	/**
	 * 得到列的值
	 * 
	 * @param columns
	 * @return
	 */
	protected Object[] getColumnValues(int[] columns) {
		String key = "";
		for (int i = 0; i < columns.length; i++) {
			key += columns[i];
		}
		if (htColumnValues.get(key) == null) {
			String[] str = new String[listData.size()];
			for (int i = 0; i < listData.size(); i++) {
				for (int j = 0; j < columns.length; j++) {
					Object obj = adapter.getCellData(listData.get(i),
							columns[j]);
					str[i] += (obj == null ? "" : obj.toString().trim());
				}
			}
			htColumnValues.put(key, str);
		}
		return (Object[]) htColumnValues.get(key);
	}

	/**
	 * 通过列来排序
	 * 
	 * @param column
	 */
	public void sortByColumn(int column) {
		if (column == 0) {
			return;
		}
		if (listData == null || listData.size() == 0) {
			return;
		}
		strColumnValues = getColumnValues(column);
		sortData(column);
		SwingUtilities.invokeLater(notifyTableRunnable);
	}

	/**
	 * 根据列找到列单元格分组
	 * 
	 * @param column
	 * @return
	 */
	protected Hashtable getGroupByColumn(int column) {
		int currentIndex = 0;
		Hashtable ht = new Hashtable();
		sort = 1;
		strColumnValues = getColumnValues(column);
		for (int i = 0; i < indexes.length; i++) {
			if (i == 0) {
				ht.put("start" + currentIndex, Integer.valueOf(i));
				if (indexes.length == 1) {
					ht.put("end" + currentIndex, Integer.valueOf(i));
				}
				continue;
			}
			if (!checkEquals(strColumnValues[indexes[i - 1]],
					strColumnValues[indexes[i]])) {
				currentIndex += 1;
				ht.put("end" + (currentIndex - 1), Integer.valueOf(i - 1));
				ht.put("start" + currentIndex, Integer.valueOf(i));
			}
			if (i == indexes.length - 1) {
				ht.put("end" + currentIndex, Integer.valueOf(i));
			}
		}
		return ht;
	}

	/**
	 * 根据列找到列单元格分组
	 * 
	 * @param columns
	 * @return
	 */
	protected Hashtable getGroupByColumn(int[] columns) {
		int currentIndex = 0;
		Hashtable ht = new Hashtable();
		sort = 1;
		strColumnValues = getColumnValues(columns);
		for (int i = 0; i < indexes.length; i++) {
			if (i == 0) {
				ht.put("start" + currentIndex, Integer.valueOf(i));
				if (indexes.length == 1) {
					ht.put("end" + currentIndex, Integer.valueOf(i));
				}
				continue;
			}
			if (!checkEquals(strColumnValues[indexes[i - 1]],
					strColumnValues[indexes[i]])) {
				currentIndex += 1;
				ht.put("end" + (currentIndex - 1), Integer.valueOf(i - 1));
				ht.put("start" + currentIndex, Integer.valueOf(i));
			}
			if (i == indexes.length - 1) {
				ht.put("end" + currentIndex, Integer.valueOf(i));
			}
		}
		return ht;
	}

	/**
	 * 多列排序
	 * 
	 * @param columns
	 */
	public void sortByColumns(int[] columns) {
		if (columns.length < 1) {
			return;
		}
		sort = 1;
		strColumnValues = getColumnValues(columns[0]);
		sortData(columns[0]);
		Hashtable ht = getGroupByColumn(columns[0]);
		strColumnValues = getColumnValues(columns[1]);
		Class cls = model.getColumnClass(columns[1]);
		for (int j = 0; j < (ht.size() / 2); j++) {
			shuttlesort(
					(int[]) indexes.clone(),
					indexes,
					Integer.valueOf(ht.get("start" + j).toString()).intValue(),
					Integer.valueOf(ht.get("end" + j).toString()).intValue() + 1,
					columns[1], cls);
			// System.out.println(Integer.valueOf(ht.get("start" +
			// j).toString())
			// .intValue());
		}
	}

	/**
	 * 加入多行排序
	 * 
	 * @param columns
	 */
	public void sortByColumns2(int[] columns) {
		if (columns.length < 1) {
			return;
		}
		sort = 1;
		strColumnValues = getColumnValues(columns);
		shuttlesort((int[]) indexes.clone(), indexes, 0, indexes.length, 0,
				String.class);
	}

	/**
	 * 根据列排序
	 * 
	 * @param column
	 */
	public void sortByColumns(int column) {
		sort = 1;
		strColumnValues = getColumnValues(column);
		sortData(column);
	}

	/**
	 * 排序数据
	 * 
	 * @param column
	 */
	protected void sortData(int column) {
		Class cls = model.getColumnClass(column);// kcb
		// System.out.println(cls == null ? "null" : cls.getName()
		// + " is column CLASSTYPE");
		shuttlesort((int[]) indexes.clone(), indexes, 0, indexes.length,
				column, cls);
	}

	/**
	 * 快速排序法
	 * 
	 * @param from
	 *            源排序顺序
	 * @param to
	 *            目标顺序
	 * @param low
	 *            低值
	 * @param high
	 *            高值
	 * @param column
	 *            列
	 * @param cls
	 *            类
	 */
	protected void shuttlesort(int from[], int to[], int low, int high,
			int column, Class cls) {
		if (high - low < 2) {
			return;
		}
		int middle = (low + high) / 2;
		shuttlesort(to, from, low, middle, column, cls);
		shuttlesort(to, from, middle, high, column, cls);

		int p = low;
		int q = middle;

		if (high - low >= 4
				&& compare(from[middle - 1], from[middle], column, cls) <= 0) {
			for (int i = low; i < high; i++) {
				to[i] = from[i];
			}
			return;
		}

		for (int i = low; i < high; i++) {
			if (q >= high
					|| (p < middle && compare(from[p], from[q], column, cls) <= 0)) {
				to[i] = from[p++];
			} else {
				to[i] = from[q++];
			}
		}
	}

	/**
	 * 数据比较
	 * 
	 * @param row1
	 * @param row2
	 * @param column
	 *            列
	 * @param cls
	 * @return
	 */
	public int compare(int row1, int row2, int column, Class cls) {
		Object o1 = strColumnValues[row1];
		Object o2 = strColumnValues[row2];
		if (o1 == null && o2 == null) {
			return 0;
		} else if (o1 == null) {
			return 1;
		} else if (o2 == null) {
			return -1;
		}
		return compareData(o1, o2, cls);
	}

	/**
	 * 数据比较
	 * 
	 * @param a
	 * @param b
	 * @param cls
	 * @return
	 */
	protected int compareData(Object a, Object b, Class cls) {
		int result = 0;
		Integer i;
		if (cls == Integer.class) {
			result = Integer.parseInt(a.toString())
					- Integer.parseInt(b.toString());
		} else if (cls == Float.class) {
			result = Float.compare(Float.parseFloat(a.toString()),
					Float.parseFloat(b.toString()));
		} else if (cls == Double.class) {
			// result = Double.compare(Double.parseDouble(a.toString()), Double
			// .parseDouble(b.toString()));
			try {
				result = Double.compare(new DecimalFormat().parse((String) a)
						.doubleValue(), new DecimalFormat().parse((String) b)
						.doubleValue());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (cls == Long.class) {
			result = (int) (Long.parseLong(a.toString()) - Long.parseLong(b
					.toString()));
		} else if (cls == Short.class) {
			result = (int) (Long.parseLong(a.toString()) - Long.parseLong(b
					.toString()));
		} else if (cls.equals(String.class)) {
			result = collator.compare(a, b);
		} else if (a instanceof Comparable) {
			result = ((Comparable) a).compareTo(b);
		}
		return result * sort;
	}

	/**
	 * 判断两个数据是否相等
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	protected boolean checkEquals(Object a, Object b) {
		if (a == null) {
			a = "";
		}
		if (b == null) {
			b = "";
		}
		return a.equals(b);
	}

	/**
	 * 光标滚动到上一行
	 * 
	 * @return boolean
	 */
	public boolean previousRow() {
		if (indexes.length <= 0) {
			return false;
		}
		int row = table.getSelectedRow();
		if (row <= 0) {
			return false;
		} else if (row >= indexes.length) {
			row = indexes.length;
		}
		row--;
		// Object obj=list.get(indexes[row]);
		table.setRowSelectionInterval(row, row);
		this.setTableSelectRowColor(row);
		if (row <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 光标滚动到下一行
	 * 
	 * @return boolean
	 */
	public boolean nextRow() {
		if (indexes.length <= 0) {
			return false;
		}
		int row = table.getSelectedRow();
		if (row < 0) {
			row = -1;
		} else if (row >= indexes.length - 1) {
			return false;
		}
		row++;
		// Object obj=list.get(indexes[row]);
		table.setRowSelectionInterval(row, row);
		this.setTableSelectRowColor(row);
		if (row >= indexes.length - 1) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否还有上一行
	 * 
	 * @return boolean
	 */
	public boolean hasPreviousRow() {
		if (indexes.length <= 0) {
			return false;
		}
		int row = table.getSelectedRow();
		if (row == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否还有下一行
	 * 
	 * @return boolean
	 */
	public boolean hasNextRow() {
		if (indexes.length <= 0) {
			return false;
		}
		int row = table.getSelectedRow();
		if (row == table.getRowCount() - 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 根据{Bool}取得选择的数据
	 * 
	 * @param propertyName
	 * @return
	 */
	public List getSelectedDataByBoolProperty(String propertyName) {
		if (table.isEditing()) {
			if (table.getCellEditor() != null) {
				table.getCellEditor().stopCellEditing();
			}
		}
		List listResult = new ArrayList();
		for (Object obj : listData) {
			try {
				String value = BeanUtils.getProperty(obj, propertyName);
				if (value != null && !"".equals(value.trim())
						&& Boolean.valueOf(value.toString())) {
					listResult.add(obj);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (table.getRowCount() > 0 && table.getColumnCount() > 0) {
			table.setRowSelectionInterval(0, 0);
			table.setColumnSelectionInterval(0, 0);
		}
		return listResult;
	}

	/**
	 * 得到Excel文件名称
	 * 
	 * @return
	 */
	public String getExcelFileName() {
		return jTableContextPopupMenu.getExcelFileName();
	}

	public void setExcelFileName(String excelFileName) {
		jTableContextPopupMenu.setExcelFileName(excelFileName);
	}

	public void setMiRenderColumnEnabled(boolean isEnabled) {
		jTableContextPopupMenu.setMiRenderColumnEnabled(isEnabled);
	}

	public JMenuItem getMiCopy() {
		return jTableContextPopupMenu.getMiCopy();
	}

	public JMenuItem getMiCopyValue() {
		return jTableContextPopupMenu.getMiCopyValue();
	}

	public JMenuItem getMiSaveAllToExcel() {
		return jTableContextPopupMenu.getMiSaveAllToExcel();
	}

	public JMenuItem getMiSavePartToExcel() {
		return jTableContextPopupMenu.getMiSavePartToExcel();
	}

	public JMenuItem getMiRenderColumn() {
		return jTableContextPopupMenu.getMiRenderColumn();
	}

	public JMenuItem getMiSaveTableListToExcel() {
		return jTableContextPopupMenu.getMiSaveAllToExcel();
	}

	public JMenuItem getMiSearch() {
		return jTableContextPopupMenu.getMiSearch();
	}

	private void setColumnPreferredWidth() {
		jTableContextPopupMenu.setColumnPreferredWidth();
	}

	public JTable getTable() {
		return table;
	}

	public JTableContextPopupMenu getJTableContextPopupMenu() {
		return jTableContextPopupMenu;
	}

	/**
	 * 增加页脚统计信息
	 * 
	 * @param tableFooterType
	 */
	public void addFooterTypeInfo(TableFooterType tableFooterType) {
		if (table instanceof JFooterTable) {
			JTableFooter tableFooter = ((JFooterTable) table).getTableFooter();
			if (tableFooter != null) {
				tableFooter.addFooterTypeInfo(tableFooterType);
			} else {
				if (tableFooterType != null
						&& tableFooterType.getColumnNo() != null) {
					footerMap.put(tableFooterType.getColumnNo(),
							tableFooterType);
				}
			}
		}
	}

	/**
	 * 清除页脚统计信息
	 */
	public void clearFooterTypeInfo() {
		if (table instanceof JFooterTable) {
			JTableFooter tableFooter = ((JFooterTable) table).getTableFooter();
			if (tableFooter != null) {
				tableFooter.clearFooterTypeInfo();
			} else {
				footerMap.clear();
				// if (tableFooterType != null
				// && tableFooterType.getColumnNo() != null) {
				// footerMap.put(tableFooterType.getColumnNo(),
				// tableFooterType);
				// }
			}
		}
	}

	/**
	 * 设置各报表中所有长度 this method created by kangbo at 2008-3-19
	 * 
	 * @param length
	 */
	public void setAllColumnsFractionCount(int length) {
		if (length < 0) {
			return;
		}
		List<JTableListColumn> coms = adapter.getColumns();
		if (coms == null || coms.isEmpty()) {
			return;
		}
		for (int i = 0; i < coms.size(); i++) {
			JTableListColumn cm = coms.get(i);
			if (cm == null) {
				continue;
			}
			cm.setFractionCount(length);
		}
	}

	/**
	 * 设备各报表中以千分号显示
	 * 
	 */
	public void setAllColumnsshowThousandthsign(Boolean showThousandthsign) {
		if (!showThousandthsign) {
			return;
		}
		List<JTableListColumn> coms = adapter.getColumns();
		if (coms == null || coms.isEmpty()) {
			return;
		}
		for (int i = 0; i < coms.size(); i++) {
			JTableListColumn cm = coms.get(i);
			if (cm == null) {
				continue;
			}
			cm.setShowThousandthsign(showThousandthsign);
		}
	}

	/**
	 * 设置表格列的小数位 this method created by kangbo at 2008-3-19
	 * 
	 * @param length
	 */
	public void setColumnsFractionCount(int index, int length) {
		if (index < 0 || length < 0) {
			return;
		}
		List<JTableListColumn> coms = adapter.getColumns();
		if (coms == null || coms.isEmpty()) {
			return;
		}
		for (int i = 0; i < coms.size(); i++) {
			if (i == index) {
				JTableListColumn cm = coms.get(i);
				if (cm == null) {
					continue;
				}
				cm.setFractionCount(length);
			}
		}
	}

	/**
	 * 动态设置表中各列的数据类型，为排序方便
	 * 
	 * @param
	 */
	private void aotuSetColumnsTpye() {// by kcb 2008/3/14

		Object obj = null;

		if (listData != null && listData.size() > 0) {
			obj = listData.get(0);
		}

		if (obj != null) {

			List<JTableListColumn> colms = adapter.getColumns();

			if (colms != null) {

				for (int i = 0; i < colms.size(); i++) {

					JTableListColumn cm = colms.get(i);

					if (cm != null) {

						if (cm instanceof SerialColumn) {

							continue;
						}

						String field = cm.getProperty();

						if (field != null && (!field.equals(""))) {

							Class cls = String.class;

							try {

								cls = PropertyUtils.getPropertyType(obj,
										field.trim());

							} catch (IllegalAccessException e) {

								cls = String.class;

							} catch (InvocationTargetException e) {

								cls = String.class;

							} catch (NoSuchMethodException e) {

								cls = String.class;

							} catch (IllegalArgumentException e) {

								cls = String.class;

							}
							if (cls == null) {

								cls = String.class;

							}

							String clsName = cls.getName() == null ? "null"
									: cls.getName();

							this.table.setDefaultRenderer(Double.class,
									new DefaultTableCellRenderer());

							this.table.setDefaultRenderer(Date.class,
									new DefaultTableCellRenderer());

							this.table.setDefaultRenderer(Long.class,
									new DefaultTableCellRenderer());

							this.table.setDefaultRenderer(Float.class,
									new DefaultTableCellRenderer());

							this.table.setDefaultRenderer(Short.class,
									new DefaultTableCellRenderer());

							this.table.setDefaultRenderer(Integer.class,
									new DefaultTableCellRenderer());

							this.table.setDefaultRenderer(Boolean.class,
									new DefaultTableCellRenderer());

							if (cm.getClassType() == null) {

								cm.setClassType(String.class);

							} else {

								Class cas = cm.getClassType();

								String casType = cas.getName() == null ? "null"
										: cas.getName();
								if (casType.equals("java.lang.Integer")) {
									cm.setClassType(Integer.class);
								} else if (casType.equals("java.long.Double")) {
									cm.setClassType(Double.class);
								} else if (casType.equals("java.util.Date")) {
									cm.setClassType(Date.class);
								} else {
									if (clsName.equals("java.lang.Double")) {
										cm.setClassType(Double.class);
									} else if (clsName
											.equals("java.lang.Integer")) {
										cm.setClassType(Integer.class);
									} else if (clsName.equals("java.util.Date")) {
										cm.setClassType(Date.class);
									} else if (clsName.equals("java.lang.Long")) {
										cm.setClassType(Long.class);
									} else if (clsName
											.equals("java.lang.Float")) {
										cm.setClassType(Float.class);
									} else if (clsName
											.equals("java.lang.Boolean")) {
										cm.setClassType(Boolean.class);
									} else if (clsName
											.equals("java.lang.Short")) {
										cm.setClassType(Short.class);
									} else {
										cm.setClassType(String.class);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	class SelectRowColorTableCellRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			if (isSelected) {
				setForeground(new Color(0, 0, 204));
				setBackground(table.getSelectionBackground());
			} else {
				if (row == table.getSelectedRow()) {
					setForeground(table.getSelectionForeground());
					setBackground(table.getSelectionBackground());
				} else {
					setForeground(table.getForeground());
					setBackground(table.getBackground());
				}
			}
			return this;
		}
	}

	// /**
	// * 刷新表中信息
	// */
	// public void fireTableDataChanged() {
	// super.fireTableDataChanged();
	// // CommonVariables.packRows(table,-2);
	// // CommonVariables.packRows(table,1);
	// }

	/**
	 * 显示多行
	 */
	public void packRows() {
		CommonVariables.packRows(table, -2);
	}

}
