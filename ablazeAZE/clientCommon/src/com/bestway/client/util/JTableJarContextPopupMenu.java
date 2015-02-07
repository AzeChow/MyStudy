package com.bestway.client.util;

import java.awt.Component;
import java.awt.Container;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bestway.client.common.CommonVariables;
import com.bestway.client.windows.form.FmMain;
import com.bestway.ui.winuicontrol.JTableBase;

public class JTableJarContextPopupMenu extends JPopupMenu {
	private JTableJarDataModel		model					= null;
	private JTable					table					= null;

	private DgJarDataStore			dgSaveJarFile			= null;
	private DgJarDataStore			dgOpenJarFile			= null;
	private DgSaveTableListToExcel	dgSave					= null;
	private JMenuItem				miSaveTableListToExcel	= null;
	private JMenuItem				miSaveJarFile			= null;
	private JMenuItem				miOpenJarFile			= null;
	private String					excelFileName			= "";
	private JarDataStoreInterface	jarDataStoreInterface	= CommonVariables
																	.getJarDataStoreInterface();

	public JTableJarContextPopupMenu(JTableJarDataModel model, JTable table) {
		this.model = model;
		this.table = table;
		this.initData();
	}

	private void initData() {
		this.add(getMiSaveTableListToExcel());
		this.addSeparator();
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
			if (e.getModifiers() != InputEvent.BUTTON3_MASK) {
				return;
			}
			getMiSaveTableListToExcel().setEnabled(true);
			getMiSaveJarFile().setEnabled(true);
			getMiOpenJarFile().setEnabled(true);
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

		public void keyPressed(KeyEvent e) {
			// List list = model.getList();
			// if (list == null || list.size() <= 0) {
			// return;
			// }
			if (e.isControlDown() == true && e.isShiftDown() == false
					&& e.getKeyCode() == KeyEvent.VK_S) {
				saveToExcel();
			} else if (e.isShiftDown() == true && e.isControlDown() == true
					&& e.getKeyCode() == KeyEvent.VK_O) {
				openJarFile();
			} else if (e.isShiftDown() == true && e.isControlDown() == true
					&& e.getKeyCode() == KeyEvent.VK_S) {
				saveJarFile();
			}
		}
	}

	public void addTableHeaderMouseListener(JTable table) {
		MouseListener[] mouseListeners = table.getTableHeader()
				.getMouseListeners();
		for (int i = 0; i < mouseListeners.length; i++) {
			// if (mouseListeners[i] instanceof RightMouseListener) {
			table.getTableHeader().removeMouseListener(mouseListeners[i]);
			// }
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
			getMiSaveTableListToExcel().setEnabled(false);
			getMiSaveJarFile().setEnabled(true);
			getMiOpenJarFile().setEnabled(true);
			show((Component) e.getSource(), e.getPoint().x, e.getPoint().y);
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
	
	private class CustomInternalFrameAdapter extends InternalFrameAdapter {
		public void internalFrameClosing(javax.swing.event.InternalFrameEvent e) {
			dgSaveClose();
			dgSaveJarFileClose();
			dgOpenJarFileClose();
		}
	}

	private class CustomWindowAdapter extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			dgSaveClose();
			dgSaveJarFileClose();
			dgOpenJarFileClose();
		}
	}
	
	
	
	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	/**
	 * 保存数据to jar file
	 * 
	 */
	private void saveJarFile() {
		Component container = CommonVariables.getFormWhereComponentIsIn(table);
		List<JTableBase> jTableBaseList = new ArrayList<JTableBase>();

		String keyString = this.model.getKeyString();

		if ((table instanceof JTableBase)) {
			jTableBaseList = new ArrayList<JTableBase>();
			CommonVariables.getJTableBaseByComponent((Container) container,
					jTableBaseList);
			Collections.sort(jTableBaseList);
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
				String hashCode = jarItem.getHashCode();
				List<JarDataItem> jarDataItemList = new ArrayList<JarDataItem>();
				//
				// 单表保存
				//
				if (!(table instanceof JTableBase)) {
					String[] columnNameArray = this.model.getColumnNames();
					JarDataItem jarDataItem = new JarDataItem();
					jarDataItem.setColumnNames(columnNameArray);
					jarDataItem.setStoreIndex(0);
					jarDataItem.setNote("");
					jarDataItem.setRows(this.model.getRows());
					jarDataItemList.add(jarDataItem);
					//
					// 多表保存
					//
				} else {
					for (int size = 0; size < jTableBaseList.size(); size++) {
						JTableBase jTable = jTableBaseList.get(size);
						JTableJarDataModel tempModel = (JTableJarDataModel) jTable
								.getModel();
						String[] columnNameArray = tempModel.getColumnNames();
						JarDataItem jarDataItem = new JarDataItem();
						jarDataItem.setColumnNames(columnNameArray);
						jarDataItem.setStoreIndex(jTable.getJarDataSaveOrder());
						jarDataItem.setNote("");
						jarDataItem.setRows(tempModel.getRows());
						jarDataItemList.add(jarDataItem);
					}
				}
				//
				// 保存记录
				//
				this.jarDataStoreInterface.saveJarFile(fileNameNoPrefixSuffix,
						hashCode,false, jarDataItemList);
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
		Component container = CommonVariables.getFormWhereComponentIsIn(table);
		String keyString = this.model.getKeyString();

		List<JTableBase> jTableBaseList = new ArrayList<JTableBase>();
		if ((table instanceof JTableBase)) {
			jTableBaseList = new ArrayList<JTableBase>();
			CommonVariables.getJTableBaseByComponent((Container) container,
					jTableBaseList);
			Collections.sort(jTableBaseList);
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
				}else{
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
