package com.bestway.bcus.client.cas.otherreport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.CheckBalanceOfCustom;
import com.bestway.bcus.cas.entity.TempOfCheckBalanceOfCustom;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;

/**
 * 盘点编码级导入
 * 
 * @author
 * 
 */
public class DgCheckBalanceOfCustomImport extends JDialogBase {
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
	private CasAction casActon = null;
	private JButton btnColumnSet = null;
	private JCheckBox cbCheckTitle = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel6 = null;

	/**
	 * DgBomTxtImport的构造函数 This is the default constructor
	 */
	public DgCheckBalanceOfCustomImport() {
		super();
		casActon = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.CHECKBALANCE_OFCUSTOM, this
						.getDefaultBomTableColumnList());
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
		this.setTitle("盘点库存（编码级）文本导入");
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getJRadioButton());
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
			btnOpen.setPreferredSize(new Dimension(70, 30));
			btnOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					txtFile = getFile();
					// System.out.println("hi");
					if (txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().execute();
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
	class ImportFileDataRunnable extends SwingWorker {
		@Override
		protected Object doInBackground() {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgCheckBalanceOfCustomImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				initTable(list);
			}
			return list;
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
		// 获取三种报关模式的，料件MAP（名称+规格+单位）；
		HashMap<String, String> checkMap = casActon.getCheckMap();

		for (Map.Entry<String, String> entry : checkMap.entrySet()) {
			System.out.println("key=" + entry.getKey());
			System.out.println("value=" + entry.getValue());
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
		ArrayList<TempOfCheckBalanceOfCustom> list = new ArrayList<TempOfCheckBalanceOfCustom>();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.CHECKBALANCE_OFCUSTOM);
		String info = "";
		// 验证不能为空的字段是否存在
		boolean isDate = false;
		boolean isName = false;
		boolean isSpec = false;
		boolean isUnitName = false;
		boolean isCheckAmount = false;
		boolean isKcType = false;
		boolean isWareSet=false;
		for (int i = 0; i < lsIndex.size(); i++) {
			if (lsIndex.get(i).equals("balance.checkDate")) {
				isDate = true;
			}
			if (lsIndex.get(i).equals("balance.name")) {
				isName = true;
			}
			if (lsIndex.get(i).equals("balance.spec")) {
				isSpec = true;
			}
			if (lsIndex.get(i).equals("balance.unitName")) {
				isUnitName = true;
			}
			if (lsIndex.get(i).equals("balance.checkAmount")) {
				isCheckAmount = true;
			}
			if (lsIndex.get(i).equals("balance.kcType")) {
				isKcType = true;
			}
			if (lsIndex.get(i).equals("balance.wareSet.name")) {
				isWareSet = true;
			}
		}
		if (!isDate) {
			info += "栏位设定不能没盘点日期\n";
		}
		if (!isName) {
			info += "栏位设定不能没有商品名称\n";
		}
		if (!isSpec) {
			info += "栏位设定不能没有商品规格\n";
		}
		if (!isUnitName) {
			info += "栏位设定不能没有单位\n";
		}
		if (!isCheckAmount) {
			info += "栏位设定不能没有数量\n";
		}
		if (!isKcType) {
			info += "栏位设定不能没有库别类型\n";
		}
		if (!isWareSet) {
			info += "栏位设定不能没有仓库\n";
		}
		if (!info.equals("")) {
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgCheckBalanceOfCustomImport.this,
					info, "提示信息", 0);
			return null;
		}
		Map<String, WareSet> wareSetMap = new HashMap<String, WareSet>();
		List warSetList = this.casActon.findWareSet(new Request(CommonVars
				.getCurrUser()));
		for (int i = 0; i < warSetList.size(); i++) {
			WareSet wareSet = (WareSet) warSetList.get(i);
			wareSetMap.put(wareSet.getCode(), wareSet);
		}
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
			CheckBalanceOfCustom balance = new CheckBalanceOfCustom();
			TempOfCheckBalanceOfCustom temp = new TempOfCheckBalanceOfCustom();
			String err = "";
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				// 盘点时间
				if ("balance.checkDate".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							balance
									.setCheckDate(dateFormat
											.parse(value.trim()));
						} catch (Exception ex) {
							err = err + ("第" + (i + 1) + "行日期格式错误");
						}

					} else {
						err = err + "[" + value + "]  盘点时间不能为空/";
					}
					// 商品名称
				} else if ("balance.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						balance.setName(value);
					}

					// 报关规格
				} else if ("balance.spec".equals(columnField)) {
					if (value != null && !value.equals("")) {
						balance.setSpec(value);
					}
					// 报关单位
				} else if ("balance.unitName".equals(columnField)) {
					if (value != null && !value.equals("")) {
						balance.setUnitName(value);
					} else {
						err += "单位不能为空/";
					}
				} // 数量
				else if ("balance.checkAmount".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							balance.setCheckAmount(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					} else {
						err += "数量不能为空/";
					}
					// 库存类别
				} else if ("balance.kcType".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (value.trim().endsWith("0")
								|| value.trim().endsWith("1")
								|| value.trim().endsWith("2")
								|| value.trim().endsWith("3")
								|| value.trim().endsWith("4")
								|| value.trim().endsWith("5")
								|| value.trim().endsWith("6")) {
							balance.setKcType(value);
						} else {
							err += "库存类别只能为：0-6/";
						}

					} else {
						err += "库存类别不能为空/";
					}
					
				} else if ("balance.wareSet.name".equals(columnField)) {// 仓库
					if (value != null && !value.equals("")) {
						if (wareSetMap.get(value) == null) {
							err = err + "[" + value + "]  仓库在系统中不存在/";
						}
						balance.setWareSet(wareSetMap.get(value));
					} else {
						err = err + "[" + value + "]  仓库不能为空/";
					}
				}
			}

			String key = balance.getName() + "/" + balance.getSpec() + "/"
					+ balance.getUnitName();
			if (checkMap.get(key) == null) {
				err += "该料件名称/规格/单位 不存在合同、手册中";
			}
			temp.setBalance(balance);
			temp.setErrinfo(err);
			list.add(temp);
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

	private JButton getJButton1() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("2.保存数据");
			btnSave.setPreferredSize(new Dimension(70, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					list = tableModel.getList();
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(
								DgCheckBalanceOfCustomImport.this,
								"数据为空，请先导入数据！", "提示!", 2);
						return;
					}
					for (int i = 0; i < tableModel.getList().size(); i++) {
						TempOfCheckBalanceOfCustom obj = (TempOfCheckBalanceOfCustom) tableModel
								.getList().get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgCheckBalanceOfCustomImport.this,
									"有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}

					new ImportFileData().execute();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 保存数据
	 */
	class ImportFileData extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress
						.showProgressDialog(DgCheckBalanceOfCustomImport.this);

				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				List ls = tableModel.getList();

				casActon.saveBalanceList(new Request(CommonVars.getCurrUser()),
						ls);
				System.out.println("isok");

				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;

				JOptionPane.showMessageDialog(
						DgCheckBalanceOfCustomImport.this, "导入数据成功！导入数据记录 "
								+ ls.size() + " 条,共用时 " + totalTime + " 毫秒",
						"提示", JOptionPane.INFORMATION_MESSAGE);
				DgCheckBalanceOfCustomImport.this.dispose();

			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgCheckBalanceOfCustomImport.this, "导入数据失败 "
								+ ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}
			return null;
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
			btnExit.setPreferredSize(new Dimension(70, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgCheckBalanceOfCustomImport.this.dispose();
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
		list.add(new JTableListColumn("盘点日期(YYYY-MM-DD)", "balance.checkDate",  150));
		list.add(new JTableListColumn("商品名称", "balance.name",200));
		list.add(new JTableListColumn("商品规格", "balance.spec", 100));
		list.add(new JTableListColumn("单位", "balance.unitName", 100));
		list.add(new JTableListColumn("数量", "balance.checkAmount", 100));
		list.add(new JTableListColumn("仓库(代码)", "balance.wareSet.name", 100));
		list.add(new JTableListColumn("库存类别(代码)", "balance.kcType", 100));
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
			jPanel.add(getJPanel2(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);

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
								.getTableColumnList(InputTableColumnSet.CHECKBALANCE_OFCUSTOM);
					}
				});
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		setTableColumnRenderer(tableModel);

	}

	public void setTableColumnRenderer(JTableListModel tableModel) {
		int columnCount = tableModel.getColumnCount();
		for (int i = 1; i < columnCount; i++) {
			if ("balance.kcType".equals(tableModel.getPropertyName(i))) {
				tableModel.getTable().getColumnModel().getColumn(i)
						.setCellRenderer(new DefaultTableCellRenderer() {
							public Component getTableCellRendererComponent(
									JTable table, Object value,
									boolean isSelected, boolean hasFocus,
									int row, int column) {
								super.getTableCellRendererComponent(table,
										value, isSelected, hasFocus, row,
										column);
								super.setText((value == null) ? ""
										: castValue1(value));
								return this;
							}

							private String castValue1(Object value) {
								String returnValue = "";
								if (String.valueOf(value).trim().equals("")) {
									return "";
								}
								if (value.equals("0")) {
									returnValue = "料件库存";
								} else if (value.equals("1")) {
									returnValue = "成品库存";
								} else if (value.equals("2")) {
									returnValue = "在产品库存";
								} else if (value.equals("5")) {
									returnValue = "残次品库存";
								} else if (value.equals("4")) {
									returnValue = "外发未收回";
								} else if (value.equals("6")) {
									returnValue = "边角料库存";
								}
								return returnValue;
							}
						});
			}
		}
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
	 * This method initializes btnColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumnSet == null) {
			btnColumnSet = new JButton();
			btnColumnSet.setText("栏位设定");
			btnColumnSet.setPreferredSize(new Dimension(70, 30));
			btnColumnSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.CHECKBALANCE_OFCUSTOM);
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

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(11, 7, 631, 18));
			jLabel6
					.setText("\u5e93\u5b58\u7c7b\u522b\u5b9a\u4e49\uff1a  0\u8868\u793a\u6599\u4ef6\u5e93\u5b58\u30011\u8868\u793a\u6210\u54c1\u5e93\u5b58\u30012\u8868\u793a\u5728\u4ea7\u54c1\u5e93\u5b58\u30014\u8868\u793a\u5916\u53d1\u672a\u6536\u56de\u30015\u6b8b\u6b21\u54c1\u5e93\u5b58\u30016\u8fb9\u89d2\u6599");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setPreferredSize(new Dimension(1, 30));
			jPanel2.add(jLabel6, null);
		}
		return jPanel2;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
