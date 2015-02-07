/*
 * Created on 2004-11-18
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.client.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.table.TableColumn;

/**
 * @author Administrator
 * 
 * // change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DgSaveTableListToExcel extends JDialog {

	private JPanel			jContentPane	= null;

	private JTextField		tfFileName		= null;

	private JProgressBar	jProgressBar	= null;

	private String			fileName		= "";
	
	private String			sheetCaption		= "";

	private JTableListModelBase	tableModel		= null;
	
	private boolean exportCatpion = true; 

//	private Thread			fileThread		= null;
	
	private boolean isAll =true;

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetCaption() {
		return sheetCaption;
	}

	public void setSheetCaption(String sheetCaption) {
		this.sheetCaption = sheetCaption;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgSaveTableListToExcel() {
		super();
		initialize();
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public DgSaveTableListToExcel(JDialog jDialog) {
		super(jDialog);
		initialize();
		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public void setVisible(boolean isFlag) {
		if (isFlag) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = this.getSize();
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			this.setLocation((screenSize.width - frameSize.width) / 2,
					(screenSize.height - frameSize.height) / 2);
			tfFileName.setText(fileName);
			SaveTableListToExcel saveTableListToExcel = new SaveTableListToExcel();
			saveTableListToExcel.setFileName(fileName);
			saveTableListToExcel.setSheetCaption(sheetCaption);
			saveTableListToExcel.setTableModel(tableModel);
			saveTableListToExcel.setAll(isAll);
			saveTableListToExcel.setRemoveExportColumns(this.getRemoveExportColumns(tableModel.getTable()));
			saveTableListToExcel.setDialog(DgSaveTableListToExcel.this);
			saveTableListToExcel.setExportCaption(exportCatpion);
			saveTableListToExcel.execute();
//			fileThread = new Thread(saveTableListToExcel);
//			fileThread.start();
		}
		super.setVisible(isFlag);
	}

	/**
	 * 获得 not export excel 的列
	 * @param table
	 * @return
	 */
	public List<Integer> getRemoveExportColumns(JTable table) {
		List<Integer> removeExportColumns = new ArrayList<Integer>();
		int columnCount = table.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			TableColumn tableColumn = table.getColumnModel().getColumn(i);
			if (tableColumn.getPreferredWidth() <= 0) {
				removeExportColumns.add(i);
			}
		}
		return removeExportColumns;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setSize(391, 94);
		// this.setUndecorated(true);
		this.setResizable(false);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane
					.setBorder(javax.swing.BorderFactory
							.createTitledBorder(
									null,
									"文件名及其路径",
									javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
									javax.swing.border.TitledBorder.DEFAULT_POSITION,
									null, null));
			// jContentPane.setBackground(java.awt.SystemColor.control);
			jContentPane.add(getTfFileName(), null);
			jContentPane.add(getJProgressBar(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tfFileName
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfFileName() {
		if (tfFileName == null) {
			tfFileName = new JTextField();
			tfFileName.setEditable(false);
			tfFileName.setBounds(new java.awt.Rectangle(10, 18, 361, 22));
			tfFileName.setBackground(java.awt.Color.white);
		}
		return tfFileName;
	}

	/**
	 * This method initializes jProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setUI(new BasicProgressBarUI() {
				protected int getCellLength() {
					return 10;
				}

				protected int getCellSpacing() {
					return 2;
				}
			});
			jProgressBar.setBorder(javax.swing.BorderFactory
					.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
			jProgressBar.setForeground(new Color(10, 36, 106));
			jProgressBar.setBackground(Color.white);
			jProgressBar.setVisible(false);
			jProgressBar.setBounds(new java.awt.Rectangle(10, 42, 361, 14));
		}
		return jProgressBar;
	}

	

	public void setProgressBarMaximum(int maxNum) {
		this.jProgressBar.setMaximum(maxNum);
	}

	public void setProgressBarValue(int value) {
		this.jProgressBar.setValue(value);
	}

	/**
	 * 
	 * 
	 */
	public void doBeforThreadRun() {
		// this.btnFileName.setEnabled(false);
		// this.btnOk.setEnabled(false);
		// this.btnClose.setEnabled(false);
		this.jProgressBar.setVisible(true);
		// this.setUndecorated(true);
	}

	public void doAfterThreadRun() {
		// this.btnFileName.setEnabled(true);
		// this.btnOk.setEnabled(true);
		// this.btnClose.setEnabled(true);
		this.setProgressBarValue(0);
		this.jProgressBar.setVisible(false);
		this.dispose();
		// this.setUndecorated(false);
	}

	public void fileIsOpenedException() {
		JOptionPane.showMessageDialog(DgSaveTableListToExcel.this, "文件"
				+ fileName + "已经在另外的程序中被打开，\n 请先关闭打开此文件的外部程序后再继续执行操作！", "提示",
				JOptionPane.OK_OPTION);
	}

	/**
	 * 显示导出成功信息
	 * 
	 */
	public void showSuccessMessage() {
		JOptionPane.showMessageDialog(DgSaveTableListToExcel.this,
				"转存Excel完毕！", "提示", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * 显示导出成功信息
	 * 
	 */
	public void showFailMessage() {
		JOptionPane.showMessageDialog(DgSaveTableListToExcel.this,
				"转存Excel失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * @return Returns the moduleName.
	 */
	public String getModuleName() {
		String title = this.getTitle();
		int leftParenthesesPos = title.indexOf("(");
		int rightParenthesesPos = title.indexOf(")");
		return title.substring(leftParenthesesPos + 1, rightParenthesesPos);
	}

	public JTableListModelBase getTableModel() {
		return tableModel;
	}

	public void setTableModel(JTableListModelBase tableModel) {
		this.tableModel = tableModel;
	}

	public void setExportCatpion(boolean exportCatpion) {
		this.exportCatpion = exportCatpion;
	}
	
}
