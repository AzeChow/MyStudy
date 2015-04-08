package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

import org.apache.commons.lang.StringUtils;

import com.bestway.bcus.checkcancel.entity.CancelCusImgResult;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
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

	// 料件序号 对照表
	private Map<Integer, CancelCusImgResult> imgMap = new HashMap<Integer, CancelCusImgResult>();

	private ManualDeclareAction manualDeclearAction = null;

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JTableListModel tableModel = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton jButton2 = null;

	private File txtFile = null;
	private Hashtable gbHash = null;

	private List afterList = null;

	private JButton btnColumn = null;

	private JCheckBox cbCheckTitle = null;

	private JToolBar jToolBar = null;

	private JCheckBox cbZeroCalulate = null;

	private Set<String> captions;

	// 是否包含错误信息
	private boolean isHasError = false;

	// 线程处理器 处理前后线程问题
	private Executor executor = Executors.newSingleThreadExecutor();

	// 料件核销结果集
	private List<CancelCusImgResult> imgCancelResults = null;

	// 记录所有导入为 空 或 0 的 内购金额 || 反算用
	private Map<Integer, TempCancelImgResult> markZeroBlank = null;

	// 用于计算并设值
	private Map<Integer, TempCancelImgResult> calBalMap = new HashMap<Integer, TempCancelImgResult>();

	public DgCancelImgByInnerBuyImport() {
		super();
		manualDeclearAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.CANCELCHECK_CANCELCUS_INNERBUYIMPORT,
				this.getDefaultBomTableColumnList());

		// 设置线程异常助手
		//Thread.setDefaultUncaughtExceptionHandler(new CustomThreadCatchExceptionHelper());

		initTable(new Vector());
	}

	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.CANCELCHECK_CANCELCUS_INNERBUYIMPORT);
					}
				});
	}

	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("帐册序号(必填)", "seqNum", 120, Integer.class));
		list.add(new JTableListColumn("名称", "name", 120));
		list.add(new JTableListColumn("规格", "commSpec", 120));
		list.add(new JTableListColumn("单位", "unit", 50));
		list.add(new JTableListColumn("内购数量(必填)", "innerUseSumNum", 110));
		list.add(new JTableListColumn("内购金额(必填)", "inChinaBuyNum", 110));
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

					executor.execute(new ImportFileDataRunnable());

					if (!isHasError) {
						executor.execute(new InitCalulateBalance());
					}
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

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	class ImportFileDataRunnable implements Runnable {
		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgCancelImgByInnerBuyImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");

				if (captions != null) {

					// 清除 所有缓存 的 标题
					captions.clear();

				} else {
					captions = new HashSet<String>();
				}
				// 获取文件名字的后缀名
				Boolean isXls = getSuffix(txtFile).equals("xls");

				// 写入标题 --- 用于验证
				if (isXls) {

					for (String caption : FileReading.readExcelCaption(txtFile,
							1, "ISO-8859-1")) {
						captions.add(caption);
					}
				} else {
					for (String caption : FileReading.readTxtCaption(txtFile,
							1, "UTF-8")) {
						captions.add(caption);
					}
				}
				// 没有检查通过就结束
				if (!checkCaption()) {

					CommonProgress.closeProgressDialog();

					JOptionPane.showMessageDialog(
							DgCancelImgByInnerBuyImport.this,
							"导入的Excel文件没有包含必填的<帐册序号>,<内够数量>,<内购金额>", "提示",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				// 解析文件后导入数据到 tableModel后展示
				importData(parseTxtFile(isXls), cbZeroCalulate.isSelected());

				CommonProgress.closeProgressDialog();

			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
			} finally {
				initTable(afterList);
			}
		}
	}

	/**
	 * 导入数据
	 * 
	 * @param isCalulateZreo
	 */
	private void importData(List<List> lines, boolean isCalulateZreo) {

		isHasError = false;

		List<TempCancelImgResult> cancelImgResults = new ArrayList<TempCancelImgResult>();

		// 取出 对象属性名 列表
		List<String> lsIndexName = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.CANCELCHECK_CANCELCUS_INNERBUYIMPORT);

		// 判断是否需要 创建 装载 为 0 或 空的 料件序号
		markZeroBlank = isCalulateZreo ? new HashMap<Integer, TempCancelImgResult>()
				: null;

		for (List<Object> values : lines) {

			StringBuffer errorinfo = new StringBuffer("");

			TempCancelImgResult cancelImgResult = new TempCancelImgResult();

			for (int i = 0; i < values.size(); i++) {

				String value = ((String) values.get(i)).trim();

				String name = lsIndexName.get(i);

				if (name.equals("name")) {

					cancelImgResult.setName(value);
				}
				if (name.equals("seqNum")) {

					if (StringUtils.isNotBlank(value)
							&& StringUtils.isNumeric(value)) {

						cancelImgResult.setSeqNum(Integer.valueOf(value));

						// 判断 不存在这个料件序号
						if (isNotExits(Integer.valueOf(value))) {

							errorinfo.append(",导入的料件号不存在");

							cancelImgResult.setErrinfo(errorinfo.toString());
						}
					} else {

						errorinfo.append(",错误的料件序号");

						cancelImgResult.setErrinfo(errorinfo.toString());
					}
				}
				if (name.equals("commSpec")) {

					cancelImgResult.setCommSpec(value);
				}
				if (name.equals("inChinaBuyNum")) {

					if (StringUtils.isNotBlank(value)) {

						try {
							cancelImgResult.setInChinaBuyNum(Double
									.valueOf(value));

						} catch (NumberFormatException e) {

							errorinfo.append(",错误的内购金额");

							cancelImgResult.setErrinfo(errorinfo.toString());
						}
					} else {

						// 需要记录金额为空的序号 并且不能有错误信息
						if (StringUtils.isBlank(value) && isCalulateZreo
								&& !isHasError) {

							cancelImgResult.setInChinaBuyNum(0.0);

							markZeroBlank.put(cancelImgResult.getSeqNum(),
									cancelImgResult);
						} else {
							errorinfo.append(",错误的内购金额");

							cancelImgResult.setErrinfo(errorinfo.toString());
						}

					}
				}
				if (name.equals("innerUseSumNum")) {

					if (StringUtils.isNotBlank(value)) {

						try {
							cancelImgResult.setInnerUseSumNum(Double
									.valueOf(value));

						} catch (NumberFormatException e) {

							errorinfo.append(",错误的内购数量");

							cancelImgResult.setErrinfo(errorinfo.toString());
						}
					} else {

						if (StringUtils.isBlank(value)) {

							errorinfo.append(",内购数量为空");

							cancelImgResult.setErrinfo(errorinfo.toString());
						} else {
							errorinfo.append(",错误的内购数量");

							cancelImgResult.setErrinfo(errorinfo.toString());
						}
					}
				}
				if (name.equals("unit")) {

					cancelImgResult.setUnit(value);
				}
			}
			cancelImgResult.setErrinfo(subErrorInfo(cancelImgResult
					.getErrinfo()));

			cancelImgResults.add(cancelImgResult);
		}

		afterList = cancelImgResults;

		if (isCalulateZreo && !isHasError) {

			calulateZeroCancelImgResult();
		}
	}

	/**
	 * 去除 前面 的 "," 如果","在第一个位置 0
	 * 
	 * @param errorinfo
	 * @return
	 */
	private String subErrorInfo(String errorinfo) {

		if (StringUtils.isNotBlank(errorinfo) && errorinfo.indexOf(",") == 0) {

			// 这里出现错误信息
			if (!isHasError) {
				isHasError = true;
			}
			return errorinfo.substring(1, errorinfo.length());
		}
		return null;
	}

	/**
	 * 判断是否包含导入的必填标题
	 * 
	 * @return
	 */
	private boolean checkCaption() {

		return captions.contains("帐册序号") && captions.contains("内购数量")
				&& captions.contains("内购金额");
	}

	/**
	 * 是否存在次料件
	 * 
	 * @param seqNum
	 * @return
	 */
	private boolean isNotExits(Integer seqNum) {

		CancelCusImgResult cancelCusImgResult = imgMap.get(seqNum);
		return cancelCusImgResult == null;
	}

	/**
	 * 计算 如果导入的 内购金额为 空或者为0时 反算内购数量
	 * 
	 * @param isCalulateZero
	 */
	private void calulateZeroCancelImgResult() {

		// 先删去金额为空的部分 列表
		afterList.removeAll(markZeroBlank.values());

		for (Iterator<?> it = markZeroBlank.keySet().iterator(); it.hasNext();) {

			// 需要进行反算的序号
			Integer seqNum = (Integer) it.next();

			CancelCusImgResult cancelCusImgResult = imgMap.get(seqNum);

			// 原料件序号 平均金额数
			BigDecimal averagePrice = new BigDecimal(
					cancelCusImgResult.getAveragePrice());

			TempCancelImgResult tempCancelImgResult = markZeroBlank.get(seqNum);

			// 内购数量
			BigDecimal amounts = new BigDecimal(
					tempCancelImgResult.getInnerUseSumNum());

			// 反算后的 内购金额
			BigDecimal inChinaBuyPrice = averagePrice.multiply(amounts);

			tempCancelImgResult.setInChinaBuyNum(inChinaBuyPrice.doubleValue());
		}

		afterList.addAll(markZeroBlank.values());

		// 排序一下
		Collections.sort(afterList, new Comparator<TempCancelImgResult>() {
			@Override
			public int compare(TempCancelImgResult t1, TempCancelImgResult t2) {
				return t1.getSeqNum() == t2.getSeqNum() ? 0
						: t1.getSeqNum() > t2.getSeqNum() ? 1 : -1;
			}
		});
	}

	/**
	 * 保存时，计算结余数 = 应剩余数量 + 国内购买 + 其他来源
	 * 
	 */
	private void calulateBalance() {

		for (Iterator<?> it = calBalMap.keySet().iterator(); it.hasNext();) {

			Integer key = (Integer) it.next();

			// 取得当前 的 料件核销结果
			CancelCusImgResult cancelCusImgResult = imgMap.get(key);

			// 取得对应的 料件临时需导入的数据
			TempCancelImgResult tempCancelImgResult = calBalMap.get(key);

			// 内购金额
			cancelCusImgResult.setInChinaBuyMoney(tempCancelImgResult
					.getInChinaBuyNum());

			// 内购数量
			cancelCusImgResult.setInChinaBuyNum(tempCancelImgResult
					.getInnerUseSumNum());

			// 反算结余金额
			cancelCusImgResult.setResultSumPrice(BigDecimal
					.valueOf(cancelCusImgResult.getInChinaBuyMoney())
					.add(BigDecimal.valueOf(cancelCusImgResult
							.getLeaveSumPrice()))
					.add(BigDecimal.valueOf(cancelCusImgResult
							.getOtherSourcePrice())).doubleValue());

			// 反算 结余数量
			cancelCusImgResult.setResultNum(BigDecimal
					.valueOf(cancelCusImgResult.getInChinaBuyNum())
					.add(BigDecimal.valueOf(cancelCusImgResult.getLeaveNum()))
					.add(BigDecimal.valueOf(cancelCusImgResult
							.getOtherSourceNum())).doubleValue());

		}

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

	/**
	 * 解析文档
	 * 
	 * @param isXls
	 *            是否excel
	 * @return
	 */
	private List parseTxtFile(boolean isXls) {

		List<List> lines = null;
		/*
		 * 读取导入文件
		 */
		if (isXls) {
			// 导入xls
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			// 导入txt
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		return lines;
	}

	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存数据");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (isHasError) {
						JOptionPane.showMessageDialog(
								DgCancelImgByInnerBuyImport.this,
								"导入的Excel文件包含有错误的信息,请核对正确信息后导入。", "提示",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					if (afterList.size() > 0) {
						executor.execute(new SaveFileDataRunnable());
					}
				}
			});
		}
		return jButton1;
	}

	/**
	 * 保存
	 * 
	 * @author zcj 2015-4-3
	 */
	class SaveFileDataRunnable implements Runnable {

		public void run() {
			try {
				CommonProgress
						.showProgressDialog(DgCancelImgByInnerBuyImport.this);
				CommonProgress.setMessage("系统正在保存数据资料，请稍后...");

				calulateBalance();

				// 开始保存
				DgCancelImgByInnerBuyImport.this.manualDeclearAction
						.batchSaveOrUpdate(
								new Request(CommonVars.getCurrUser()),
								imgCancelResults);

				CommonProgress.setMessage("系统保存数据已完成.");

			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统保存资料失败！");
			} finally {

				CommonProgress.closeProgressDialog();
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
	 * 初始化 核销 Map 缓存用途
	 * 
	 * @author zcj 2015-4-3
	 */
	class InitCancelCusResultsMap implements Runnable {
		@Override
		public void run() {
			for (CancelCusImgResult imgResult : imgCancelResults) {

				imgMap.put(imgResult.getEmsSeqNum(), imgResult);
			}
		}
	}

	/**
	 * 当打开文件完成后 ，准备的Map数据
	 * 
	 * @author zcj
	 *
	 */
	class InitCalulateBalance implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < afterList.size(); i++) {

				TempCancelImgResult tempCancelImgResult = (TempCancelImgResult) afterList
						.get(i);

				calBalMap.put(tempCancelImgResult.getSeqNum(),
						tempCancelImgResult);
			}
		}
	}

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

	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("\u680f\u4f4d\u8bbe\u5b9a");
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.CANCELCHECK_CANCELCUS_INNERBUYIMPORT);
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

	private JCheckBox getCbZeroCalulate() {
		if (cbZeroCalulate == null) {
			cbZeroCalulate = new JCheckBox();
			cbZeroCalulate.setText("内购金额为0或未填写时,根据平均单价反算");
		}
		return cbZeroCalulate;
	}

	public void setImgCancelResults(List imgCancelResults) {

		this.imgCancelResults = imgCancelResults;

		// 设值完毕后 ， 开始执行 初始化 核销Map
		executor.execute(new InitCancelCusResultsMap());
	}

	/**
	 * 线程异常助手 -- 捕获线程异常,处理异常
	 * 
	 * @author zcj 2015-4-8
	 */
	class CustomThreadCatchExceptionHelper implements
			Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable tw) {

			throw new RuntimeException("");
		}
	}

}
