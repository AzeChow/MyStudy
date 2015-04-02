package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsEdiHeadH2kBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsEdiMergerBomFrom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.DgSaveTableListToExcel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 导入内购数 -- 弹出框
 * 
 * @author zcj
 *
 */
@SuppressWarnings("unchecked")
public class DgCancelImgByInnerBuyImport extends JDialogBase {

	private static final long serialVersionUID = 1L;
	private boolean isRepeat = false;
	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton jButton2 = null;
	private List list = null;
	private File txtFile = null;
	private Hashtable gbHash = null;
	private EmsHeadH2k emsHeadH2k = null;
	private ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction");
	private List afterList = null;
	private List tlist = new Vector();
	private JButton btnColumn = null;
	private JCheckBox cbIsOverwrite = null;
	private JCheckBox cbCheckTitle = null;
	private JCheckBox cbJF = null;
	private JToolBar jToolBar = null;
	private List<EmsHeadH2kExg> exgs = null;
	private List<EmsHeadH2kImg> imgs = null;
	private List<Object[]> listNum = new ArrayList<Object[]>();
	private JCheckBox cbZeroCalulate = null;

	public DgCancelImgByInnerBuyImport() {
		super();
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.EMSH2K_BOM_INPUT,
				this.getDefaultBomTableColumnList());
		list = new Vector();
		initTable(list);
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.EMSH2K_BOM_INPUT);
					}
				});
	}

	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品序号(不为空)", "seqNum", 120, Integer.class));
		list.add(new JTableListColumn("版本号(不为空)", "version", 120, Integer.class));
		list.add(new JTableListColumn("料件序号(不为空)", "bom.seqNum", 120));
		list.add(new JTableListColumn("单耗(不为空)", "bom.unitWear", 100));
		list.add(new JTableListColumn("损耗率(%)", "bom.wear", 100));
		return list;
	}

	
	private void initialize() {
		this.setSize(745, 520);
		this.setContentPane(getJContentPane());
		this.setTitle("批量导入内购数据");
	}

	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(0);
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setTopComponent(getJToolBar());
			jSplitPane.setDividerLocation(40);
		}
		return jSplitPane;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("1.打开文件");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgCancelImgByInnerBuyImport.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					tlist.clear();
					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton;
	}

	// 调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());

		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				String suffix = getSuffix(f);
				if (f.isDirectory() == true) {
					return true;
				}
				if (suffix != null) {
					if (suffix.toLowerCase().equals("txt")) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			public String getDescription() {
				return "*.txt";
			}

			private String getSuffix(File f) {
				String s = f.getPath(), suffix = null;
				int i = s.lastIndexOf('.');
				if (i > 0 && i < s.length() - 1)
					suffix = s.substring(i + 1).toLowerCase();
				return suffix;
			}
		});
		int state = fileChooser
				.showOpenDialog(DgCancelImgByInnerBuyImport.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	private String changeStr(String s) { // 繁转简
		String yy = "";
		char[] xxx = s.toCharArray();
		for (int i = 0; i < xxx.length; i++) {
			String z = String.valueOf(xxx[i]);
			if (String.valueOf(xxx[i]).getBytes().length == 2) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {

			try {
				CommonProgress
						.showProgressDialog(DgCancelImgByInnerBuyImport.this);

				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");

				List list = parseTxtFile();

				afterList = list;

				CommonProgress.closeProgressDialog();

			} catch (Exception e) {

				e.printStackTrace();

			} finally {

				initTable(afterList);

				String tishi = "以下为损耗超过50%\n\n";
				for (int i = 0; i < tlist.size(); i++) {
					tishi = tishi + ((String) tlist.get(i)) + "\n";
				}
				if (!tishi.equals("以下为损耗超过50%\n\n")) {
					JOptionPane.showMessageDialog(
							DgCancelImgByInnerBuyImport.this, tishi, "提示", 2);
				}

			}
		}
	}

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = changeStr(source[i]);
		}
		return newStrs;
	}

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

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	private List parseTxtFile() {

		boolean ischange = true;

		boolean isMerge = cbZeroCalulate.isSelected();

		// 获取文件名字的后缀名
		String suffix = getSuffix(txtFile);

		List<List> lines = new ArrayList<List>();

		/*
		 * 读取导入文件
		 */
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

		List<EmsEdiHeadH2kBomFrom> list = new ArrayList<EmsEdiHeadH2kBomFrom>();

		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.EMSH2K_BOM_INPUT);

		return list;
	}

	/**
	 * 判断料件序号是否存在
	 * 
	 * @param no
	 * @return
	 */
	private Boolean imgNoExists(Integer no) {
		if (imgs == null) {
			return false;
		}
		for (EmsHeadH2kImg img : imgs) {
			if (no.equals(img.getSeqNum())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断成品序号是否存在
	 * 
	 * @param no
	 * @return
	 */
	private Boolean exgNoExists(Integer no) {
		if (exgs == null) {
			return false;
		}
		for (EmsHeadH2kExg exg : exgs) {
			if (no.equals(exg.getSeqNum())) {
				return true;
			}
		}
		return false;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存数据");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (afterList == null) {
						return;
					}

					boolean isYesNo = false;

					for (int i = 0; i < afterList.size(); i++) {

						EmsEdiHeadH2kBomFrom o = (EmsEdiHeadH2kBomFrom) afterList
								.get(i);

						if (o.getErrinfo() != null
								&& !"".equals(o.getErrinfo().trim())) {

							isYesNo = true;
						}
					}

					if (isYesNo) {

						CommonProgress.closeProgressDialog();

						if (JOptionPane.YES_OPTION != JOptionPane
								.showOptionDialog(
										DgCancelImgByInnerBuyImport.this,
										"数据中有错误信息，是否继续保存数据？", "提示",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE, null,
										new Object[] { "是", "否" }, "否")) {
							return;
						}
					}

					if (afterList.size() > 0) {
						new SaveFileDataRunnable(afterList).start();
					}
				}
			});
		}
		return jButton1;
	}

	class SaveFileDataRunnable extends Thread {
		List afterList = null;

		private SaveFileDataRunnable(List afterList) {
			this.afterList = afterList;
		}

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgCancelImgByInnerBuyImport.this);
				CommonProgress.setMessage("系统正在保存数据资料，请稍后...");

				int[] x = manualdeclearAction.saveToEmsHeadH2k(new Request(
						CommonVars.getCurrUser()), emsHeadH2k, afterList,
						cbIsOverwrite.isSelected());

				CommonProgress.closeProgressDialog();

				JOptionPane.showMessageDialog(DgCancelImgByInnerBuyImport.this,
						"导入结束!\n\n" + "总记录数：" + x[4] + "\n\n" + "存在但无任何变化："
								+ x[5] + "\n" + "由于未备案或有错误信息未导入数：" + x[0]
								+ "\n" + "单耗存在修改记录数：" + x[1] + "\n"
								+ "版本存在新增记录数：" + x[2] + "\n" + "版本不存在新增记录数："
								+ x[3] + "", "提示", 2);

				// 导入完成后 清空table
				initTable(new Vector());

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统保存资料失败！");
			} finally {
			}
		}
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.isControlDown() == true
							&& e.getKeyCode() == KeyEvent.VK_L) {

						List list = new ArrayList();
						for (int i = 0; i < afterList.size(); i++) {
							EmsEdiMergerBomFrom obj = (EmsEdiMergerBomFrom) afterList
									.get(i);
							if (obj.getIsMerger().equals("未备案")
									|| (obj.getErrinfo() != null && !obj
											.getErrinfo().equals(""))) {
								continue;
							}
							list.add(obj);
						}
						initTable(list);
						saveExcel(tableModel);
						initTable(afterList);
					}
				}
			});
		}
		return jTable;
	}

	private void saveExcel(JTableListModel tableModel) {

		String excelFileName = "";
		JFileChooser fileChooser = new JFileChooser("./");
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.setFileFilter(new ExampleFileFilter("xls"));
		if (excelFileName != null && !"".equals(excelFileName)) {
			File initFile = new File("./" + excelFileName + ".xls");
			fileChooser.setSelectedFile(initFile);
		}
		String fileName = "";
		int state = fileChooser
				.showSaveDialog(DgCancelImgByInnerBuyImport.this);
		if (state == JFileChooser.APPROVE_OPTION) {
			File f = fileChooser.getSelectedFile();
			String description = fileChooser.getFileFilter().getDescription();
			String suffix = description.substring(description.indexOf("."));
			if (f.getPath().indexOf(".") > 0) {
				fileName = f.getPath();
			} else {
				fileName = f.getPath() + suffix;
			}
		} else {
			return;
		}

		DgSaveTableListToExcel dgSave = new DgSaveTableListToExcel(
				DgCancelImgByInnerBuyImport.this);
		dgSave.setTableModel(tableModel);
		dgSave.setFileName(fileName);
		dgSave.setTitle("保存("
				+ ((JDialog) DgCancelImgByInnerBuyImport.this).getTitle()
				+ ")到Excel");
		dgSave.setVisible(true);
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

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					DgCancelImgByInnerBuyImport.this.dispose();
				}
			});
		}
		return jButton2;
	}

	public EmsHeadH2k getEmsHeadH2k() {
		return emsHeadH2k;
	}

	public void setEmsHeadH2k(EmsHeadH2k emsHeadH2k) {
		this.emsHeadH2k = emsHeadH2k;
	}

	/**
	 * This method initializes btnColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("\u680f\u4f4d\u8bbe\u5b9a");
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.EMSH2K_BOM_INPUT);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}

				}
			});
		}
		return btnColumn;
	}

	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("\u7b2c\u4e00\u884c\u4e3a\u6807\u9898\u884c");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());

			jToolBar.add(getCbCheckTitle());
			jToolBar.add(getCbZeroCalulate());

		}
		return jToolBar;
	}

	public List getExgs() {
		return exgs;
	}

	public void setExgs(List<EmsHeadH2kExg> exgs) {

		this.exgs = exgs;
	}

	public List getImgs() {
		return imgs;
	}

	public void setImgs(List<EmsHeadH2kImg> imgs) {

		this.imgs = imgs;
	}

	private JCheckBox getCbZeroCalulate() {
		if (cbZeroCalulate == null) {
			cbZeroCalulate = new JCheckBox();
			cbZeroCalulate.setText("内购金额为0时,根据平均单价反算");
		}
		return cbZeroCalulate;
	}

}
