package com.bestway.client.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;

import com.bestway.client.common.CommonVariables;
import com.bestway.client.windows.form.FmMain;
import com.bestway.ui.winuicontrol.JTableBase;

public class JTableContextPopupMenu extends JPopupMenu {
	private JTableListModel model = null;

	private JTable table = null;

	private JTableListModelAdapter adapter = null;

	private Integer selectionMode = null;

	private DgSearch dgSearch = null;

	private DgRenderColumn dgRenderColumn = null;

	private DgJarDataStore dgSaveJarFile = null;

	private DgJarDataStore dgOpenJarFile = null;

	private DgSaveTableListToExcel dgSave = null;

	private JMenuItem miCopy = null;

	private JMenuItem miCopyValue = null;

	private JMenuItem miSearch = null;

	private JMenuItem miRenderColumn = null;

	private JMenuItem miSaveAllToExcel = null;

	private JMenuItem miSavePartToExcel = null;

	private JMenuItem miSaveJarFile = null;

	private JMenuItem miOpenJarFile = null;

	private ExcelAdapter excelAdapter = null;

	private String excelFileName = "";

	private Vector<RenderDataColumnItem> vector = null;

	private Map<String, Boolean> showColumnMap = null;

	private String className = null;

	private String key = null;

	private JarDataStoreInterface jarDataStoreInterface = CommonVariables
			.getJarDataStoreInterface();

	private RenderDataColumnInterface renderDataColumnInterface = CommonVariables
			.getRenderDataColumnInterface();

	private int xpoint = 0;

	private int ypoint = 0;

	private int selectedColumn = 0;

	public JTableContextPopupMenu(JTableListModel model, JTable table,
			JTableListModelAdapter adapter, Integer selectionMode) {
		this.model = model;
		this.table = table;
		this.adapter = adapter;
		this.selectionMode = selectionMode;
		this.initData();
	}

	private void initData() {
		this.excelAdapter = new ExcelAdapter(table, selectionMode);
		this.add(getMiCopyValue());
		this.add(getMiCopy());
		this.addSeparator();
		this.add(getMiSearch());
		this.add(getMiSaveAllToExcel());
		this.add(getMiSavePartToExcel());
		this.addSeparator();
		this.add(getMiRenderColumn());
		this.add(getMiSaveJarFile());
		this.add(getMiOpenJarFile());
		//
		// event
		//
		addTableContextPopupMouseListener(table);
		addKeyListener(table);
		addTableHeaderMouseListener(table);
	}

	/**
	 * This method initializes miNewTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMiCopy() {
		if (miCopy == null) {
			miCopy = new JMenuItem();
			miCopy.setIcon(CommonVariables.getCopyIcon());
			// ("导出Excel Ctrl+S")
			miCopy.setText("  复制(C)");
			miCopy.setMnemonic('C');
			miCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					ActionEvent.CTRL_MASK));
			miCopy.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					excelAdapter.copy();
				}
			});
		}
		return miCopy;
	}

	public JMenuItem getMiCopyValue() {
		if (miCopyValue == null) {
			miCopyValue = new JMenuItem();
			miCopyValue.setIcon(CommonVariables.getCopyIcon());
			// ("导出Excel Ctrl+S")
			miCopyValue.setText("  复制当前值(L)");
			miCopyValue.setMnemonic('L');
			miCopyValue.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
					ActionEvent.CTRL_MASK));
			miCopyValue.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					excelAdapter.copyCurrValues(xpoint, ypoint);
				}
			});
		}
		return miCopyValue;
	}
	
	public JMenuItem getMiRenderColumn() {
		if (miRenderColumn == null) {
			miRenderColumn = new JMenuItem();
			miRenderColumn.setText("        自定义显示列(R)");
			miRenderColumn.setMnemonic('R');
			miRenderColumn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
					ActionEvent.CTRL_MASK));
			miRenderColumn
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							showDgRenderColumn();
						}
					});
		}
		return miRenderColumn;
	}

	/**
	 * This method initializes miAddFourInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMiSaveAllToExcel() {
		if (miSaveAllToExcel == null) {
			miSaveAllToExcel = new JMenuItem();
			miSaveAllToExcel.setIcon(CommonVariables.getExcelIcon());
			miSaveAllToExcel.setText("  导出Excel(全部)(S)");
			miSaveAllToExcel.setMnemonic('S');
			miSaveAllToExcel.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			miSaveAllToExcel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							saveToExcel(true);
						}
					});
		}
		return miSaveAllToExcel;
	}

	/**
	 * This method initializes miAddFourInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMiSavePartToExcel() {
		if (miSavePartToExcel == null) {
			miSavePartToExcel = new JMenuItem();
			miSavePartToExcel.setIcon(CommonVariables.getExcelIcon());
			miSavePartToExcel.setText("  导出Excel(选择部分)(D)");
			miSavePartToExcel.setMnemonic('D');
			miSavePartToExcel.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_D, ActionEvent.CTRL_MASK));
			miSavePartToExcel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							saveToExcel(false);
						}
					});
		}
		return miSavePartToExcel;
	}

	/**
	 * This method initializes miUndoTenInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMiSearch() {
		if (miSearch == null) {
			miSearch = new JMenuItem();
			miSearch.setIcon(CommonVariables.getSearchIcon());
			miSearch.setText("  查找(F)");
			miSearch.setMnemonic('F');
			miSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
					ActionEvent.CTRL_MASK));
			miSearch.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					search();
				}
			});
		}
		return miSearch;
	}

	public JMenuItem getMiSaveJarFile() {
		if (miSaveJarFile == null) {
			miSaveJarFile = new JMenuItem();
			miSaveJarFile.setText("        保存数据(S)");
			miSaveJarFile.setMnemonic('S');
			miSaveJarFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
			miSaveJarFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							saveJarFile();
						}
					});
		}
		return miSaveJarFile;
	}

	public JMenuItem getMiOpenJarFile() {
		if (miOpenJarFile == null) {
			miOpenJarFile = new JMenuItem();
			miOpenJarFile.setText("        打开文件(O)");
			miOpenJarFile.setMnemonic('O');
			miOpenJarFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
			miOpenJarFile
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							openJarFile();
						}
					});
		}
		return miOpenJarFile;
	}

	class TableContextPopupEvent extends CommonTableContextPopupEvent {
		public void mousePressed(java.awt.event.MouseEvent e) {
			xpoint = e.getX();
			ypoint = e.getY();
			setSelectColumnByX(xpoint);
			if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
				return;
			}
			getMiCopyValue().setEnabled(true);
			getMiCopy().setEnabled(true);
			getMiSearch().setEnabled(true);
			getMiSaveAllToExcel().setEnabled(true);
			getMiRenderColumn().setEnabled(true);
			getMiSaveJarFile().setEnabled(true);
			getMiOpenJarFile().setEnabled(true);

			int[] columns = table.getSelectedColumns();
			int[] rows = table.getSelectedRows();

			int selectStartPointX = 0;
			int selectEndPointX = 0;
			//
			// copy Menu 菜单项是否激活的
			//
			boolean isCopyMenuEnabled = false;

			if (columns.length > 0 && rows.length > 0) {

				for (int i = 0; i < columns[0]; i++) {
					selectStartPointX += table.getColumnModel().getColumn(i)
							.getWidth()
							+ table.getColumnModel().getColumnMargin();
				}
				for (int i = 0; i <= columns[columns.length - 1]; i++) {
					selectEndPointX += table.getColumnModel().getColumn(i)
							.getWidth()
							+ table.getColumnModel().getColumnMargin();
				}
				//
				// 列不变，行可以不是连续的
				//
				for (int j = rows[0]; j <= rows[rows.length - 1]; j++) {
					boolean isSelectedRow = false;
					for (int i = 0; i < rows.length; i++) {
						if (j == rows[i]) {
							isSelectedRow = true;
							break;
						}
					}
					if (isSelectedRow == false) {
						continue;
					}
					int selectStartPointY = 0;
					int selectEndPointY = 0;
					for (int i = 0; i < j; i++) {
						selectStartPointY += table.getRowHeight(i);
						// + table.getRowMargin();
					}
					for (int i = 0; i <= j; i++) {
						selectEndPointY += table.getRowHeight(i);
						// + table.getRowMargin();
					}
					if (e.getPoint().x >= selectStartPointX
							&& e.getPoint().x <= selectEndPointX
							&& e.getPoint().y >= selectStartPointY
							&& e.getPoint().y <= selectEndPointY) {
						isCopyMenuEnabled = true;
						break;
					}
				}
			}
			miCopy.setEnabled(isCopyMenuEnabled);
			miCopyValue.setEnabled(isCopyMenuEnabled);
			show(table, e.getPoint().x, e.getPoint().y);
		}

	}

	public void addTableContextPopupMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof CommonTableContextPopupEvent) {
				table.removeMouseListener(mouseListeners[i]);
			}
		}
		table.addMouseListener(new TableContextPopupEvent());
	}

	/**
	 * DgSearch 查询对话框代码
	 */
	private void addKeyListener(final JTable table) {
		Object[] objs = table.getKeyListeners();
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] instanceof CommonTableKeyAdapter) {
				table.removeKeyListener((CommonTableKeyAdapter) objs[i]);
			}
		}
		table.addKeyListener(new CustomKeyAdapter());
		Component container = CommonVariables
				.getFormWhereComponentIsIn(table);
		addContainerListener((Container) container);
	}

	class CustomKeyAdapter extends CommonTableKeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			List list = model.getList();
			if (list == null || list.size() <= 0) {
				return;
			}
			table.setAutoscrolls(true);
		}

		public void keyPressed(KeyEvent e) {
			List list = model.getList();
			if (list == null || list.size() <= 0) {
				return;
			}
			if (e.isControlDown() == true && e.getKeyCode() == KeyEvent.VK_F) {
				search();
			} else if (e.isControlDown() == true && e.isShiftDown() == false
					&& e.getKeyCode() == KeyEvent.VK_S) {
				saveToExcel(true);
			} else if (e.isControlDown() == true && e.isShiftDown() == false
					&& e.getKeyCode() == KeyEvent.VK_D) {
				saveToExcel(false);
			} else if (e.isControlDown() == true
					&& e.getKeyCode() == KeyEvent.VK_R) {
				showDgRenderColumn();
			} else if (e.isShiftDown() == true && e.isControlDown() == true
					&& e.getKeyCode() == KeyEvent.VK_O) {
				openJarFile();
			} else if (e.isShiftDown() == true && e.isControlDown() == true
					&& e.getKeyCode() == KeyEvent.VK_S) {
				saveJarFile();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				table.setAutoscrolls(false);
				Rectangle rectangle = table.getCellRect(table.getSelectedRow(),
						0, true);
				Rectangle rect = new Rectangle(0, rectangle.y
						+ table.getRowHeight(), table.getWidth(), table
						.getRowHeight());
				table.scrollRectToVisible(rect);
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				table.setAutoscrolls(false);
				Rectangle rectangle = table.getCellRect(table.getSelectedRow(),
						0, true);
				Rectangle rect = new Rectangle(0, (rectangle.y - table
						.getRowHeight()) < 0 ? 0 : (rectangle.y - table
						.getRowHeight()), table.getWidth(), table
						.getRowHeight());
				table.scrollRectToVisible(rect);
			}
		}
	}

	public void addTableHeaderMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getTableHeader()
				.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			if (mouseListeners[i] instanceof RightMouseListener) {
				table.getTableHeader().removeMouseListener(mouseListeners[i]);
			}
		}
		table.getTableHeader().addMouseListener(new RightMouseListener());
	}

	public class RightMouseListener extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			//
			// 一定是右键才行
			//
			if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
				return;
			}
			getMiCopy().setEnabled(false);
			getMiCopyValue().setEnabled(false);
			getMiSearch().setEnabled(false);
			getMiSaveAllToExcel().setEnabled(false);
			getMiRenderColumn().setEnabled(false);
			getMiSaveJarFile().setEnabled(true);
			getMiOpenJarFile().setEnabled(true);
			show((Component) e.getSource(), e.getPoint().x, e.getPoint().y);
		}
	}

	private void search() {
		Component container = CommonVariables
				.getFormWhereComponentIsIn(table);
		if (container != null) {
			if (container instanceof JInternalFrame) {
				getDgSearch().setTitle(
						"查找---" + ((JInternalFrame) container).getTitle());
				dgSearch.setSelectedColumn(selectedColumn);
				getDgSearch().setVisible(true);
			} else if (container instanceof JDialog) {
				JDialog jDialog = (JDialog) container;
				getDgSearch(jDialog).setTitle(
						"查找---" + ((JDialog) container).getTitle());
				dgSearch.setSelectedColumn(selectedColumn);
				dgSearch.setVisible(true);
			} else if (container instanceof JFrame) {
				getDgSearch().setTitle(
						"查找---" + ((JFrame) container).getTitle());
				dgSearch.setSelectedColumn(selectedColumn);
				getDgSearch().setVisible(true);
			}
		}
	}

	private void saveToExcel(boolean isAll) {
		Component container = CommonVariables
				.getFormWhereComponentIsIn(table);
		if (container != null) {
			JFileChooser fileChooser = new JFileChooser("./");
			fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
			fileChooser.setFileFilter(new ExampleFileFilter("xls"));
			if (excelFileName != null && !"".equals(excelFileName)) {
				File initFile = new File("./" + excelFileName + ".xls");
				fileChooser.setSelectedFile(initFile);
			}
			String fileName = "";
			int state = fileChooser.showSaveDialog(container);
			if (state == JFileChooser.APPROVE_OPTION) {
				File f = fileChooser.getSelectedFile();
				String description = fileChooser.getFileFilter()
						.getDescription();
				String suffix = description.substring(description.indexOf("."));
				if (f.getPath().indexOf(".") > 0) {
					fileName = f.getPath();
				} else {
					fileName = f.getPath() + suffix;
				}
			} else {
				return;
			}
			File excelFile = new File(fileName);
			if (excelFile.exists()) {
				if (JOptionPane.showConfirmDialog(container, "文件已经存在,是否覆盖原文件?",
						"警告!", JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			if (container instanceof JInternalFrame) {
				dgSave = new DgSaveTableListToExcel();
				dgSave.setTableModel(model);
				dgSave.setFileName(fileName);
				dgSave.setSheetCaption(excelFileName);
				dgSave.setTitle("保存(" + ((excelFileName==null||"".equals(excelFileName.trim()))?((JInternalFrame) container).getTitle():excelFileName.trim())
						+ ")到Excel");
				dgSave.setAll(isAll);
				dgSave.setVisible(true);

			} else if (container instanceof JDialog) {
				JDialog jDialog = (JDialog) container;
				dgSave = new DgSaveTableListToExcel(jDialog);
				dgSave.setTableModel(model);
				dgSave.setFileName(fileName);
				dgSave.setSheetCaption(excelFileName);
				dgSave.setTitle("保存(" + ((excelFileName==null||"".equals(excelFileName.trim()))?((JDialog) container).getTitle():excelFileName.trim())
						+ ")到Excel");
				dgSave.setAll(isAll);
				dgSave.setVisible(true);
			} else if (container instanceof JFrame) {
				dgSave = new DgSaveTableListToExcel();
				dgSave.setTableModel(model);
				dgSave.setFileName(fileName);
				dgSave.setSheetCaption(excelFileName);
				dgSave.setTitle("保存(" + ((excelFileName==null||"".equals(excelFileName.trim()))?((JFrame) container).getTitle():excelFileName.trim())
						+ ")到Excel");
				dgSave.setAll(isAll);
				dgSave.setVisible(true);
			}
		}
	}

	class ExampleFileFilter extends FileFilter {
		private List list = new ArrayList();

		ExampleFileFilter(String suffix) {
			this.addExtension(suffix);
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (isAcceptSuffix(suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			String description = "*.";
			for (int i = 0; i < list.size(); i++) {
				description += list.get(i).toString() + " & *.";
			}
			return description.substring(0, description.length() - 5);
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}

		private boolean isAcceptSuffix(String suffix) {
			boolean isAccept = false;
			for (int i = 0; i < list.size(); i++) {
				if (suffix.equals(list.get(i).toString())) {
					isAccept = true;
					break;
				}
			}
			return isAccept;
		}

		public void addExtension(String extensionName) {
			if (extensionName.equals("")) {
				return;
			}
			list.add(extensionName.toLowerCase().trim());
		}

	}

	private void dgSaveClose() {
		if (dgSave != null) {
			dgSave.dispose();
			dgSave = null;
		}
	}

	private void addContainerListener(Container container) {
		if (container != null) {
			if (container instanceof JInternalFrame) {
				InternalFrameListener[] ls = ((JInternalFrame) container)
						.getInternalFrameListeners();
				for (int i = 0; i < ls.length; i++) {
					if (ls[i] instanceof CustomInternalFrameAdapter) {
						((JInternalFrame) container)
								.removeInternalFrameListener(ls[i]);
					}
				}
				((JInternalFrame) container)
						.addInternalFrameListener(new CustomInternalFrameAdapter());
			} else if (container instanceof JDialog) {
				WindowListener[] ls = ((JDialog) container)
						.getWindowListeners();
				for (int i = 0; i < ls.length; i++) {
					if (ls[i] instanceof CustomWindowAdapter) {
						((JDialog) container).removeWindowListener(ls[i]);
					}
				}
				((JDialog) container)
						.addWindowListener(new CustomWindowAdapter());
			} else if (container instanceof JFrame) {
				WindowListener[] ls = ((JFrame) container).getWindowListeners();
				for (int i = 0; i < ls.length; i++) {
					if (ls[i] instanceof CustomWindowAdapter) {
						((JFrame) container).removeWindowListener(ls[i]);
					}
				}
				((JFrame) container)
						.addWindowListener(new CustomWindowAdapter());
			}
		}
	}

	class CustomInternalFrameAdapter extends InternalFrameAdapter {
		public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
			dgSearchClose();
			dgSaveClose();
			dgRenderColumnClose();
			dgSaveJarFileClose();
			dgOpenJarFileClose();
		}
	}

	class CustomWindowAdapter extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			dgSearchClose();
			dgSaveClose();
			dgRenderColumnClose();
			dgSaveJarFileClose();
			dgOpenJarFileClose();
		}
	}

	private DgSearch getDgSearch() {
		if (dgSearch == null) {
			dgSearch = new DgSearch();
			dgSearch.setTb(table);
			dgSearch.setModal(true);
		}
		return dgSearch;
	}

	private DgSearch getDgSearch(JDialog jDialog) {
		if (dgSearch == null) {
			dgSearch = new DgSearch(jDialog);
			dgSearch.setModal(true);
			dgSearch.setTb(table);
		}
		return dgSearch;
	}

	private void setSelectColumnByX(int pointX) {
		int selectStartPointX = 0;
		for (int i = 0; i < table.getColumnCount(); i++) {
			selectStartPointX += table.getColumnModel().getColumn(i).getWidth();
			if (pointX < selectStartPointX) {
				selectedColumn = i;
				break;
			}
		}
	}

	private void dgSearchClose() {
		if (dgSearch != null) {
			dgSearch.dispose();
			dgSearch = null;
		}
	}

	private void showDgRenderColumn() {
		Component container = CommonVariables
				.getFormWhereComponentIsIn(table);
		if (container != null) {
			if (container instanceof JInternalFrame) {
				getDgRenderColumn().setTitle(
						"设置列的显示---" + ((JInternalFrame) container).getTitle());
				dgRenderColumn.setVector(vector);
				dgRenderColumn.setVisible(true);
			} else if (container instanceof JDialog) {
				JDialog jDialog = (JDialog) container;
				getDgRenderColumn(jDialog).setTitle(
						"设置列的显示---" + ((JDialog) container).getTitle());
				dgRenderColumn.setVector(vector);
				dgRenderColumn.setVisible(true);
			} else if (container instanceof JFrame) {
				getDgRenderColumn().setTitle(
						"设置列的显示---" + ((JFrame) container).getTitle());
				dgRenderColumn.setVector(vector);
				dgRenderColumn.setVisible(true);
			}
			//
			// 如果有改变并且确定
			//
			if (dgRenderColumn.isOk() == true) {
				//
				// 对key 进行重新排序并保存...
				//
				if (renderDataColumnInterface != null) {
					renderDataColumnInterface.saveRenderDataColumn(className,
							key, dgRenderColumn.returnValue());
					this.setColumnPreferredWidth();
					this.model.fireTableDataChanged();
				}
				// model.refreshTable();
			}
		}
	}

	private DgRenderColumn getDgRenderColumn() {
		if (dgRenderColumn == null) {
			dgRenderColumn = new DgRenderColumn();
		}
		return dgRenderColumn;
	}

	private DgRenderColumn getDgRenderColumn(JDialog jDialog) {
		if (dgRenderColumn == null) {
			dgRenderColumn = new DgRenderColumn(jDialog);
		}
		return dgRenderColumn;
	}

	private void dgRenderColumnClose() {
		if (dgRenderColumn != null) {
			dgRenderColumn.dispose();
			dgRenderColumn = null;
		}
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public void setMiRenderColumnEnabled(boolean isEnabled) {
		this.miRenderColumn.setEnabled(isEnabled);
	}

	/**
	 * 设置显示的列
	 * 
	 */
	public void setColumnPreferredWidth() {
		//
		// 初始化呈现的列的一些信息
		//
		this.setRenderDataColumnItem();
		//
		// init column width
		// 
		int columnCount = table.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			int width = adapter.getColumnWidth(i);
			String code = adapter.getColumnProperty(i);
			String captions = adapter.getColumnCaption(i) == null ? ""
					: adapter.getColumnCaption(i);
			Enumeration<TableColumn> en = table.getColumnModel().getColumns();
			while (en.hasMoreElements()) {
				TableColumn tableColumn = en.nextElement();
				String cap = tableColumn.getHeaderValue() == null ? ""
						: tableColumn.getHeaderValue().toString();
				if (cap.equals(captions)) {
					if (showColumnMap != null
							&& showColumnMap.get(code) != null
							&& !showColumnMap.get(code).booleanValue()) {
						tableColumn.setMinWidth(0);
						tableColumn.setMaxWidth(0);
						tableColumn.setPreferredWidth(0);
					} else {
						tableColumn.setMinWidth(25);
						tableColumn.setMaxWidth(Integer.MAX_VALUE);
						tableColumn.setPreferredWidth(width);
					}
				}
			}

			// if (showColumnMap.get(code) != null
			// && !showColumnMap.get(code).booleanValue()) {
			// Enumeration<TableColumn> en = table.getColumnModel()
			// .getColumns();
			// while (en.hasMoreElements()) {
			// TableColumn tableColumn = en.nextElement();
			// String cap = tableColumn.getHeaderValue() == null ? ""
			// : tableColumn.getHeaderValue().toString();
			// if (cap.equals(captions)) {
			// tableColumn.setMinWidth(0);
			// tableColumn.setMaxWidth(0);
			// tableColumn.setPreferredWidth(0);
			// }
			// }
			// } else {
			// TableColumn tableColumn = table.getColumnModel().getColumn(i);
			// tableColumn.setMinWidth(15);
			// tableColumn.setMaxWidth(Integer.MAX_VALUE);
			// tableColumn.setPreferredWidth(width);
			//
			// }
		}
	}

	/**
	 * 初始化显示设置的列
	 * 
	 */
	private void setRenderDataColumnItem() {
		if (renderDataColumnInterface == null || model == null
				|| model.getList() == null || model.getList().isEmpty()) {
			return;
		}
		vector = new Vector<RenderDataColumnItem>();
		showColumnMap = new HashMap<String, Boolean>();
		if (model.getList().size() <= 0) {
			return;
		}
		try {
			className = model.getList().get(0).getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			// className = model.getList().get(0).getClass().getSimpleName();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		key = "";
		List<JTableListColumn> columns = this.adapter.getColumns();
		for (JTableListColumn column : columns) {
			if (column instanceof SerialColumn) {
				continue;
			}
			key += column.getCustomProperty();
			RenderDataColumnItem checkableItem = new RenderDataColumnItem(
					column.getProperty(), column.getCaption());
			checkableItem.setSelected(true);
			vector.add(checkableItem);
		}
		//
		// server data
		//
		List<RenderDataColumnItem> list = this.renderDataColumnInterface
				.getRenderDataColumnItem(className, key);
		//
		// init 数据
		//		
		if (list != null) {
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			for (RenderDataColumnItem item : list) {
				map.put(item.getCode(), item.isSelected());
			}
			for (int i = 0; i < vector.size(); i++) {
				RenderDataColumnItem item = vector.get(i);
				item.setSelected(map.get(item.getCode()) == null ? true : map
						.get(item.getCode()));
			}
		}

		for (int i = 0; i < vector.size(); i++) {
			RenderDataColumnItem item = vector.get(i);
			showColumnMap.put(item.getCode(), item.isSelected());
		}
	}

	/**
	 * 保存数据to jar file
	 * 
	 */
	private void saveJarFile() {
		Component container = CommonVariables
				.getFormWhereComponentIsIn(table);
		String keyString = "";
		List<JTableBase> jTableBaseList = new ArrayList<JTableBase>();

		if (!(table instanceof JTableBase)) {
			List<JTableListColumn> columns = this.adapter.getColumns();
			for (JTableListColumn column : columns) {
				if (column instanceof SerialColumn) {
					continue;
				}
				keyString += column.getCustomProperty();
			}
		} else {
			jTableBaseList = new ArrayList<JTableBase>();
			CommonVariables.getJTableBaseByComponent((Container) container,
					jTableBaseList);

			Collections.sort(jTableBaseList);
			for (int i = 0; i < jTableBaseList.size(); i++) {
				JTableBase jTable = jTableBaseList.get(i);

				System.out.println("------------- "
						+ jTable.getJarDataSaveOrder());

				JTableListModel tempModel = (JTableListModel) jTable.getModel();
				List<JTableListColumn> columns = tempModel.getColumns();
				for (JTableListColumn column : columns) {
					if (column instanceof SerialColumn) {
						continue;
					}
					keyString += column.getCustomProperty();
				}
			}
		}

		if (container != null) {
			if (container instanceof JInternalFrame) {
				getDgSaveJarFile().setTitle(
						"保存数据---" + ((JInternalFrame) container).getTitle());
				dgSaveJarFile.setKeyString(keyString);
				dgSaveJarFile.setVisible(true);
			} else if (container instanceof JDialog) {
				JDialog jDialog = (JDialog) container;
				getDgSaveJarFile(jDialog).setTitle(
						"保存数据---" + ((JDialog) container).getTitle());
				dgSaveJarFile.setKeyString(keyString);
				dgSaveJarFile.setVisible(true);
			} else if (container instanceof JFrame) {
				getDgSaveJarFile().setTitle(
						"保存数据---" + ((JFrame) container).getTitle());
				dgSaveJarFile.setKeyString(keyString);
				dgSaveJarFile.setVisible(true);
			}
			//
			// 如果有改变并且确定
			//
			if (dgSaveJarFile.isOk() == true) {
				JarItem jarItem = dgSaveJarFile.returnValue();
				String fileNameNoPrefixSuffix = jarItem.getFileName();
				List<JarDataItem> jarDataItemList = new ArrayList<JarDataItem>();
				//
				// 单表保存
				//
				if (!(table instanceof JTableBase)) {
					List<String> columnNames = new ArrayList<String>();
					String[] columnNameArray = null;
					List<JTableListColumn> columns = this.adapter.getColumns();
					for (JTableListColumn column : columns) {
						if (column instanceof SerialColumn) {
							continue;
						}
						columnNames.add(column.getCaption());
					}
					columnNameArray = new String[columnNames.size()];
					for (int i = 0; i < columnNames.size(); i++) {
						columnNameArray[i] = columnNames.get(i);
					}
					JarDataItem jarDataItem = new JarDataItem();
					jarDataItem.setColumnNames(columnNameArray);
					jarDataItem.setStoreIndex(0);
					jarDataItem.setNote("");
					//
					// Get all rows.
					//
					List<List> rows = new ArrayList<List>();
					int rowCount = model.getList().size();
					int columnCount = model.getColumnCount();

					for (int i = 0; i < rowCount; i++) {
						Object objValue = model.getList().get(i);
						List<Object> row = new ArrayList<Object>();
						for (int j = 0; j < columnCount; j++) {
							if (j == 0) {
								continue;
							} else {
								Object value = model.getValueAt(objValue, j);
								row.add(value);
							}
						}
						rows.add(row);
					}
					jarDataItem.setRows(rows);
					jarDataItemList.add(jarDataItem);
					//
					// 多表保存
					//
				} else {
					for (int size = 0; size < jTableBaseList.size(); size++) {
						JTableBase jTable = jTableBaseList.get(size);
						JTableListModel tempModel = (JTableListModel) jTable
								.getModel();
						List<JTableListColumn> columns = tempModel.getColumns();
						List<String> columnNames = new ArrayList<String>();
						String[] columnNameArray = null;
						for (JTableListColumn column : columns) {
							if (column instanceof SerialColumn) {
								continue;
							}
							columnNames.add(column.getCaption());
						}
						columnNameArray = new String[columnNames.size()];
						for (int i = 0; i < columnNames.size(); i++) {
							columnNameArray[i] = columnNames.get(i);
						}
						JarDataItem jarDataItem = new JarDataItem();
						jarDataItem.setColumnNames(columnNameArray);
						jarDataItem.setStoreIndex(jTable.getJarDataSaveOrder());
						jarDataItem.setNote("");
						//
						// Get all rows.
						//
						List<List> rows = new ArrayList<List>();
						int rowCount = tempModel.getList().size();
						int columnCount = tempModel.getColumnCount();

						for (int i = 0; i < rowCount; i++) {
							Object objValue = tempModel.getList().get(i);
							List<Object> row = new ArrayList<Object>();
							for (int j = 0; j < columnCount; j++) {
								if (j == 0) {
									continue;
								} else {
									Object value = tempModel.getValueAt(
											objValue, j);
									row.add(value);
								}
							}
							rows.add(row);
						}
						jarDataItem.setRows(rows);
						jarDataItemList.add(jarDataItem);
					}
				}

				//
				// 保存记录
				//
				this.jarDataStoreInterface.saveJarFile(fileNameNoPrefixSuffix,
						keyString, true, jarDataItemList);
			}
		}
	}

	private DgJarDataStore getDgSaveJarFile() {
		if (dgSaveJarFile == null) {
			dgSaveJarFile = new DgJarDataStore();
			dgSaveJarFile.setDialogState(DgJarDataStore.SAVE_DIALOG);
		}
		return dgSaveJarFile;
	}

	private DgJarDataStore getDgSaveJarFile(JDialog jDialog) {
		if (dgSaveJarFile == null) {
			dgSaveJarFile = new DgJarDataStore(jDialog);
			dgSaveJarFile.setDialogState(DgJarDataStore.SAVE_DIALOG);
		}
		return dgSaveJarFile;
	}

	private void dgSaveJarFileClose() {
		if (dgSaveJarFile != null) {
			dgSaveJarFile.dispose();
			dgSaveJarFile = null;
		}
	}

	/**
	 * 打开数据 by jar file
	 * 
	 */
	private void openJarFile() {

		Component container = CommonVariables
				.getFormWhereComponentIsIn(table);
		String keyString = "";
		List<JTableBase> jTableBaseList = new ArrayList<JTableBase>();

		if (!(table instanceof JTableBase)) {
			List<JTableListColumn> columns = this.adapter.getColumns();
			for (JTableListColumn column : columns) {
				if (column instanceof SerialColumn) {
					continue;
				}
				keyString += column.getCustomProperty();
			}
		} else {
			jTableBaseList = new ArrayList<JTableBase>();
			CommonVariables.getJTableBaseByComponent((Container) container,
					jTableBaseList);

			Collections.sort(jTableBaseList);
			for (int i = 0; i < jTableBaseList.size(); i++) {
				JTableBase jTable = jTableBaseList.get(i);
				JTableListModel tempModel = (JTableListModel) jTable.getModel();
				List<JTableListColumn> columns = tempModel.getColumns();
				for (JTableListColumn column : columns) {
					if (column instanceof SerialColumn) {
						continue;
					}
					keyString += column.getCustomProperty();
				}
			}
		}

		if (container != null) {
			if (container instanceof JInternalFrame) {
				getDgOpenJarFile().setTitle(
						"打开文件---" + ((JInternalFrame) container).getTitle());
				dgOpenJarFile.setKeyString(keyString);
				dgOpenJarFile.setVisible(true);
			} else if (container instanceof JDialog) {
				JDialog jDialog = (JDialog) container;
				getDgOpenJarFile(jDialog).setTitle(
						"打开文件---" + ((JDialog) container).getTitle());
				dgOpenJarFile.setKeyString(keyString);
				dgOpenJarFile.setVisible(true);
			} else if (container instanceof JFrame) {
				getDgOpenJarFile().setTitle(
						"打开文件---" + ((JFrame) container).getTitle());
				dgOpenJarFile.setKeyString(keyString);
				dgOpenJarFile.setVisible(true);
			}
			//
			// 如果有改变并且确定
			//
			if (dgOpenJarFile.isOk() == true) {
				JarItem jarItem = dgOpenJarFile.returnValue();
				String fileNameNoPrefix = jarItem.getFileName();
				String hashCode = jarItem.getHashCode();

				List<JarDataItem> jarDataItemList = this.jarDataStoreInterface
						.openJarFile(fileNameNoPrefix, hashCode);

				if (jarDataItemList.size() == 1) {
					JarDataItem jarDataItem = jarDataItemList.get(0);
					new JTableJarDataModel(table, jarDataItem.getColumnNames(),
							jarDataItem.getRows(), keyString);
					JOptionPane
							.showMessageDialog(FmMain.getInstance(),
									"报表数据载入成功!!", "提示",
									JOptionPane.INFORMATION_MESSAGE);
					//
					// 打开的地方是多表
					//
				} else if (jarDataItemList.size() > 1
						&& jTableBaseList.size() > 0) {
					Map<Integer, JTableBase> map = new HashMap<Integer, JTableBase>();
					for (int i = 0; i < jTableBaseList.size(); i++) {
						JTableBase jTableBase = jTableBaseList.get(i);
						map.put(jTableBase.getJarDataSaveOrder(), jTableBase);
					}
					boolean isSuccess = false;
					for (int i = 0; i < jarDataItemList.size(); i++) {
						JarDataItem jarDataItem = jarDataItemList.get(i);
						Integer storeIndex = jarDataItem.getStoreIndex();
						JTableBase jTableBase = map.get(storeIndex);
						if (jTableBase == null) {
							continue;
						}
						new JTableJarDataModel(jTableBase, jarDataItem
								.getColumnNames(), jarDataItem.getRows(),
								keyString);
						isSuccess = true;
					}
					if (isSuccess == true) {
						JOptionPane.showMessageDialog(FmMain.getInstance(),
								"报表数据载入成功!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(FmMain.getInstance(),
								"报表数据载入失败,可能是您打开的报表文件与表格存入的报表文件不相对应!!", "提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(FmMain.getInstance(),
							"报表数据载入失败,可能是您打开的报表文件与表格存入的报表文件不相对应!!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private DgJarDataStore getDgOpenJarFile() {
		if (dgOpenJarFile == null) {
			dgOpenJarFile = new DgJarDataStore();
			dgOpenJarFile.setDialogState(DgJarDataStore.OPEN_DIALOG);
		}
		return dgOpenJarFile;
	}

	private DgJarDataStore getDgOpenJarFile(JDialog jDialog) {
		if (dgOpenJarFile == null) {
			dgOpenJarFile = new DgJarDataStore(jDialog);
			dgOpenJarFile.setDialogState(DgJarDataStore.OPEN_DIALOG);
		}
		return dgOpenJarFile;
	}

	private void dgOpenJarFileClose() {
		if (dgOpenJarFile != null) {
			dgOpenJarFile.dispose();
			dgOpenJarFile = null;
		}
	}

}
