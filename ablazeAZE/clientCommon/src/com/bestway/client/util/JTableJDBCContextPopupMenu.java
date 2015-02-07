package com.bestway.client.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileFilter;

import com.bestway.client.common.CommonVariables;

public class JTableJDBCContextPopupMenu extends JPopupMenu {
	private JTableListModelBase		model					= null;
	private JTable					table					= null;
	private DgSaveTableListToExcel	dgSave					= null;
	private JMenuItem				miSaveTableListToExcel	= null;
	private String					excelFileName			= "";

	public JTableJDBCContextPopupMenu(JTableListModelBase model, JTable table) {
		this.model = model;
		this.table = table;
		this.initData();
	}

	private void initData() {
		this.add(getMiSaveTableListToExcel());
		//
		// event
		//
		addTableContextPopupMouseListener(table);
		addKeyListener(table);
//		addTableHeaderMouseListener(table);
	}

	public void addTableHeaderMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getTableHeader()
				.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			// if (mouseListeners[i] instanceof RightMouseListener) {
			table.getTableHeader().removeMouseListener(mouseListeners[i]);
			// }
		}
	}

	/**
	 * This method initializes miAddFourInnerMerge
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getMiSaveTableListToExcel() {
		if (miSaveTableListToExcel == null) {
			miSaveTableListToExcel = new JMenuItem();
			miSaveTableListToExcel.setIcon(CommonVariables.getExcelIcon());
			miSaveTableListToExcel.setText("  导出Excel(S)");
			miSaveTableListToExcel.setMnemonic('S');
			miSaveTableListToExcel.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			miSaveTableListToExcel
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							saveToExcel();
						}
					});
		}
		return miSaveTableListToExcel;
	}

	class TableContextPopupEvent extends CommonTableContextPopupEvent {
		public void mousePressed(java.awt.event.MouseEvent e) {
			if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
				return;
			}
			getMiSaveTableListToExcel().setEnabled(true);
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
		Component container = CommonVariables.getFormWhereComponentIsIn(table);
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
			if (e.isControlDown() == true && e.isShiftDown() == false
					&& e.getKeyCode() == KeyEvent.VK_S) {
				saveToExcel();
			}
		}
	}

	private void saveToExcel() {
		Component container = CommonVariables.getFormWhereComponentIsIn(table);
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
			if (container instanceof JInternalFrame) {
				dgSave = new DgSaveTableListToExcel();
				dgSave.setTableModel(model);
				dgSave.setFileName(fileName);
				dgSave.setTitle("保存(" + ((JInternalFrame) container).getTitle()
						+ ")到Excel");
				dgSave.setVisible(true);

			} else if (container instanceof JDialog) {
				JDialog jDialog = (JDialog) container;
				dgSave = new DgSaveTableListToExcel(jDialog);
				dgSave.setTableModel(model);
				dgSave.setFileName(fileName);
				dgSave.setTitle("保存(" + ((JDialog) container).getTitle()
						+ ")到Excel");
				dgSave.setVisible(true);
			} else if (container instanceof JFrame) {
				dgSave = new DgSaveTableListToExcel();
				dgSave.setTableModel(model);
				dgSave.setFileName(fileName);
				dgSave.setTitle("保存(" + ((JFrame) container).getTitle()
						+ ")到Excel");
				dgSave.setVisible(true);
			}
		}
	}

	class ExampleFileFilter extends FileFilter {
		private List	list	= new ArrayList();

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
			dgSaveClose();
		}
	}

	class CustomWindowAdapter extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			dgSaveClose();
		}
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

}
