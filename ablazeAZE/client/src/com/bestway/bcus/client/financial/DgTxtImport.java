package com.bestway.bcus.client.financial;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import org.apache.struts.taglib.tiles.InitDefinitionsTag;

import com.bestway.bcs.client.contract.DgContract;
import com.bestway.common.client.fpt.DgFptBillHeadImportFromExcel;
import com.bestway.common.fpt.entity.TempFptBillHeadImportFromExcel;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.TempBomsManager;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.EnterpriseBomManage;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 账务成本分析修改数据导入
 * 
 * @author
 * 
 */
public class DgTxtImport extends JDialogBase {
	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnOpen = null;
	private JButton btnSave = null;
	private JButton btnExit = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JTableListModel tableModel = null;
	/**
	 * 打开文件类（Excel或txt）
	 */
	private File txtFile = null;
	private JCheckBox cb = null;
	/**
	 * 用于繁转简
	 */
	private Hashtable gbHash = null; // @jve:decl-index=0:
	private MaterialManageAction materialManageAction = null;
	private JCheckBox cbReplace = null;
	private JButton btnColumnSet = null;
	private JCheckBox cbCheckTitle = null;

	/**
	 * DgTxtImport的构造函数 This is the default constructor
	 */
	public DgTxtImport() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.ENTERPRISE_BOM_INPUT, this.getDefaultBomTableColumnList());
		initTable(new ArrayList());
	}

	/**
	 * 初始化控件 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(675, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("账务成本分析修改数据导入");
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
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getCbReplace());
			jToolBar.add(getCbCheckTitle());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnOpen == null) {
			btnOpen = new JButton();
			btnOpen.setText("1.打开文本");
			btnOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					txtFile = getFile();
					// System.out.println("hi");
					if (txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return btnOpen;
	}

	/**
	 * 导入文件流
	 * 
	 * @author hw
	 * 
	 */
	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			List headList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				// List listNotFound =
				// materialManageAction.checkBomImportFileData(
				// new Request(CommonVars.getCurrUser(), true), list,
				// getCbReplace().isSelected());
				// if(!listNotFound.isEmpty())
				// {
				// JOptionPane.showMessageDialog(DgBomTxtImport.this,
				// "在文件中存在异常的资料", "提示",
				// JOptionPane.INFORMATION_MESSAGE);
				// initTable(listNotFound);
				// return;
				// }
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				initTable(list);
			}
		}
	}

	/**
	 * 初始化繁转简对照表
	 */
	private void infTojHsTable() {
		if (gbHash == null) {
			gbHash = new Hashtable();
			List list = CustomBaseList.getInstance().getGbtobigs();
			for (int i = 0; i < list.size(); i++) {
				Gbtobig gb = (Gbtobig) list.get(i);
				gbHash.put(gb.getBigname(), gb.getName());
			}
		}
	}

	/**
	 * 获取到导入文件列的值
	 * 
	 * @param fileRow
	 * @param dataIndex
	 * @return
	 */
	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	/**
	 * 获取到文件的后缀名
	 * 
	 * @param f
	 * @return
	 */
	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	/**
	 * 繁转简
	 * 
	 * @param source
	 * @return
	 */
	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	/**
	 * 解析导入的文件
	 * 
	 * @return
	 */
	private List parseTxtFile() {
		boolean ischange = true;
		if (cb.isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//	
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			//
			// 导入txt
			//
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		ArrayList<TempBomsManager> list = new ArrayList<TempBomsManager>();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.ENTERPRISE_BOM_INPUT);
		int zcount = lsIndex.size();// 13; // 字段数目
		for (int i = 0; i < lines.size(); i++) {
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = (obj == null ? "" : obj.toString());
			}
			if (ischange) {
				strs = changStrs(strs);
			}
			EnterpriseBomManage ebm = new EnterpriseBomManage();
			TempBomsManager obj = new TempBomsManager();
			String err = "";
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				System.out.println("value:" + value);
				String columnField = lsIndex.get(j);
				if ("ebm.parentNo".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setParentNo(value);
						// obj.setBill1(value);// 父级料号
					} else {
						err = err + "[" + value + "]   父级料号为空/";
					}
				} else if ("ebm.versionNo".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setVersionNo(value);
					}
					// Double.valueOf(value);
					// obj.setBill3(value);// 版本
					else {
						err = err + "[" + value + "]   版本号为空/";
					}
				} else if ("ebm.compentNo".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setCompentNo(value);
					} else {
						err = err + "[" + value + "]   子级料号为空/";
					}
					// obj.setBill2(value);// 子级料号
				} else if ("ebm.memo".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setMemo(value);
					} else {
						ebm.setMemo("");
						// err = err + "[" + value + "]   备注为空/";
					}
				} else if ("ebm.unitWare".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setUnitWare(Double.valueOf(value));
					}
					// obj.setBill4(value);// 单耗
					else {
						err = err + "[" + value + "]   单耗不合法/";
					}
				} else if ("ebm.ware".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setWare(Double.valueOf(value));
					} else {
						err = err + "[" + value + "]   损耗不合法/";
					}
					// Double.valueOf(value);
					// obj.setBill5(value);// 损耗
					//						
				} else if ("ebm.unitDosage".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setUnitDosage(Double.valueOf(value));
						// obj.setBill6(value);// 单项用量
					} else {
						err = err + "[" + value + "]   单项用量号不可为空/";
					}
				}
			}
			obj.setEbm(ebm);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	/**
	 * 获取文件头
	 * 
	 * @param strs
	 * @param zcount
	 * @return
	 */
	private List getHead(String[] strs, int zcount) {
		List ls = new ArrayList();
		ArrayList list = new ArrayList();
		return ls;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	// if (!listNotExisted.isEmpty() && listNotExisted.size() >
	// 0) {
	// if (JOptionPane
	// .showConfirmDialog(
	// DgBomTxtImport.this,
	// "同一个父件的同一个版本号里已经存在有子件,是否继续导入？按确定继续导入,按取消显示存在的料号!!!",
	// "提示", JOptionPane.OK_CANCEL_OPTION) !=
	// JOptionPane.OK_OPTION) {
	// initTableDetail(listNotExisted);
	// return;
	// }
	//
	// }
	private JButton getJButton1() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("2.保存数据");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					list = tableModel.getList();
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(DgTxtImport.this,
								"数据为空，请先导入数据！", "提示!", 2);
						return;
					}
					List listNotFound = null;
					boolean isReplace = getCbReplace().isSelected();
					for (int i = 0; i < tableModel.getList().size(); i++) {
						TempBomsManager obj = (TempBomsManager) tableModel
								.getList().get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(DgTxtImport.this,
									"有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}
					listNotFound = materialManageAction.checkBomImportFileData(
							new Request(CommonVars.getCurrUser(), true), list,
							getCbReplace().isSelected());
					if (!listNotFound.isEmpty()) {
						JOptionPane.showMessageDialog(DgTxtImport.this,
								"在文件中存在异常的资料", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						initTable(listNotFound);
						return;
					}
					//wss:2010.06.09修改此方法覆盖功能
					List listNotExisted = materialManageAction
							.findNotExistedBomManageFileData(new Request(
									CommonVars.getCurrUser()), list,
									getCbReplace().isSelected());
					new SaveDataRunnable(listNotExisted).start();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 保存文件流
	 * 
	 * @author hw
	 * 
	 */
	class SaveDataRunnable extends Thread {
		/**
		 * 传入导入的资料
		 */
		private List data = new ArrayList();

		public SaveDataRunnable(List list) {
			this.data = list;
		}

		public void run() {
			CommonProgress.showProgressDialog(DgTxtImport.this);
			CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
			// list = tableModel.getList();
			try {
				materialManageAction.executeBomImport(new Request(CommonVars
						.getCurrUser()), data);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgTxtImport.this,
						"导入失败!\n\nBOM数据: " + String.valueOf(data.size()), "提示",
						2);
				e.printStackTrace();
				return;
			} finally {
				CommonProgress.closeProgressDialog();
			}
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgTxtImport.this,
					"导入成功!\n\nBOM数据: " + String.valueOf(data.size()), "提示", 2);
			DgTxtImport.this.dispose();
		}
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTxtImport.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化表 This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list
				.add(new JTableListColumn("父级料号", "ebm.parentNo", 60,
						Integer.class));
		list
				.add(new JTableListColumn("版本号", "ebm.versionNo", 60,
						Integer.class));
		list.add(new JTableListColumn("子级料号", "ebm.compentNo", 100));
		list.add(new JTableListColumn("单耗", "ebm.unitWare", 100));
		list.add(new JTableListColumn("损耗", "ebm.ware", 100));
		list.add(new JTableListColumn("单项用量", "ebm.unitDosage", 100));
		list.add(new JTableListColumn("备注", "ebm.memo", 100));
		return list;
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
		}
		return jPanel1;
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
	 * 初始化单据头表 This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.ENTERPRISE_BOM_INPUT);
					}
				});
	}

	/**
	 * 调出文件选择框
	 * 
	 * @return
	 */
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();
		// fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("xls"));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		int state = fileChooser.showDialog(this, "选择导入文件");
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	/**
	 * 文件过滤类
	 * 
	 * @author hw
	 * 
	 */
	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		public boolean accept(File f) {
			String suffix = getSuffix(f);
			if (f.isDirectory() == true) {
				return true;
			}
			if (suffix != null) {
				if (suffix.toLowerCase().equals(this.suffix)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * This method initializes jRadioButton
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JCheckBox getJRadioButton() {
		if (cb == null) {
			cb = new JCheckBox();
			cb.setText("繁转简");
		}
		return cb;
	}

	/**
	 * This method initializes cbReplace
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JCheckBox getCbReplace() {
		if (cbReplace == null) {
			cbReplace = new JCheckBox();
			cbReplace.setText("资料存在覆盖导入");
		}
		return cbReplace;
	}

	/**
	 * This method initializes btnColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumnSet == null) {
			btnColumnSet = new JButton();
			btnColumnSet.setText("栏位设定");
			btnColumnSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.ENTERPRISE_BOM_INPUT);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}
				}
			});
		}
		return btnColumnSet;
	}

	/**
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("第一行为标题行");
		}
		return cbCheckTitle;
	}
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
} // @jve:decl-index=0:visual-constraint="10,10"
