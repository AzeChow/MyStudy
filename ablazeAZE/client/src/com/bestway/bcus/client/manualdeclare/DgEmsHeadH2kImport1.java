package com.bestway.bcus.client.manualdeclare;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kBom;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.DgSaveTableListToExcel;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

@SuppressWarnings("unchecked")
public class DgEmsHeadH2kImport1 extends JDialogBase {

	/**
	 * 
	 */
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
	private Hashtable gbHash = null; // @jve:decl-index=0:
	private EmsHeadH2k emsHeadH2k = null; // @jve:decl-index=0:
	private ManualDeclareAction manualdeclearAction = (ManualDeclareAction) CommonVars
			.getApplicationContext().getBean("manualdeclearAction"); // @jve:decl-index=0:
	private List afterList = null;
	private List tlist = new Vector(); // @jve:decl-index=0:
	private JButton btnColumn = null;
	private JCheckBox cbIsOverwrite = null;
	private JCheckBox cbCheckTitle = null;
	private JCheckBox cbJF = null;
	private JToolBar jToolBar = null;
	private List<EmsHeadH2kExg> exgs = null; // @jve:decl-index=0:
	private List<EmsHeadH2kImg> imgs = null; // @jve:decl-index=0:
	private List<Object[]> listNum = new ArrayList<Object[]>();// 当导入单耗版本重复，提示成品序号，版本号用
																// //
																// @jve:decl-index=0:
	private JCheckBox cbMergeUnitWaste = null;

	// //
	// @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public DgEmsHeadH2kImport1() {
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

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(745, 520);
		this.setContentPane(getJContentPane());
		this.setTitle("电子帐册单耗导入");
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
			jSplitPane.setDividerSize(0);
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setTopComponent(getJToolBar());
			jSplitPane.setDividerLocation(40);
		}
		return jSplitPane;
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
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
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
							.showOpenDialog(DgEmsHeadH2kImport1.this);
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
		int state = fileChooser.showOpenDialog(DgEmsHeadH2kImport1.this);
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
				CommonProgress.showProgressDialog(DgEmsHeadH2kImport1.this);

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
					JOptionPane.showMessageDialog(DgEmsHeadH2kImport1.this,
							tishi, "提示", 2);
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

		// 繁简转换
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}

		boolean isMerge = cbMergeUnitWaste.isSelected();

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

		// 检查 是否出现重复的 成品序号+版本号+料件序号Map
		Set<String> checkRepeat = new HashSet<String>();

		int zcount = lsIndex.size();// 5; // 字段数目

		for (int i = 0; i < lines.size(); i++) {

			List line = lines.get(i);

			String[] strs = new String[line.size()];

			for (int j = 0; j < line.size(); j++) {

				Object obj = line.get(j);

				strs[j] = obj == null ? "" : obj.toString();

			}

			if (ischange) {
				strs = changStrs(strs);
			}

			EmsEdiHeadH2kBomFrom obj = new EmsEdiHeadH2kBomFrom();

			EmsHeadH2kBom bom = new EmsHeadH2kBom();

			String err = "";

			isRepeat = false;

			for (int j = 0; j < zcount; j++) {

				String value = getFileColumnValue(strs, j);

				String columnField = lsIndex.get(j);

				if ("seqNum".equals(columnField)) {

					try {
						obj.setSeqNum(Integer.valueOf(value.trim()));
						if (!exgNoExists(Integer.valueOf(value.trim()))) {
							err = err + "[" + value + "] 成品序号不存在！";
						}
					} catch (Exception e) {
						err = err + "[" + value + "]   成品序号不合法/";
					}

				} else if ("version".equals(columnField)) {
					try {
						obj.setVersion(Integer.valueOf(value.trim()));
					} catch (Exception e) {
						err = err + "[" + value + "]   版本不正确/";
					}
				} else if ("bom.seqNum".equals(columnField)) {
					try {
						bom.setSeqNum(Integer.valueOf(value.trim()));
						if (!imgNoExists(Integer.valueOf(value.trim()))) {
							err = err + "[" + value + "] 料件序号不存在！";
						}
					} catch (Exception e) {
						err = err + "[" + value + "]   料件序号不正确/";
					}
				} else if ("bom.unitWear".equals(columnField)) {
					if (value == null || "".equals(value)) {
						value = "0.0";
					}
					try {
						bom.setUnitWear(Double.valueOf(value));
					} catch (Exception e) {
						err = err + "[" + value + "]   单耗不正确/";
					}
				} else if ("bom.wear".equals(columnField)) {
					if (value == null || "".equals(value)) {
						value = "0.0";
					}
					try {
						bom.setWear(Double.valueOf(value));
					} catch (Exception e) {
						err = err + "[" + value + "]   损耗率不正确/";
					}
					if (Double.parseDouble(value) >= 50) {
						tlist.add("成品序号:" + obj.getSeqNum() + "  版本号:"
								+ obj.getVersion() + " 料件序号:" + bom.getSeqNum());
					}
				}
			}

			// 非合并的情况 需要进行判断重复 的成品序号+版本号+料件序号
			if (!isMerge) {

				String exgSeqVerImgSeq = obj.getSeqNum() + "/"
						+ obj.getVersion() + "/" + bom.getSeqNum();

				if (!checkRepeat.contains(exgSeqVerImgSeq)) {

					checkRepeat.add(exgSeqVerImgSeq);
				} else {
					err = "出现重复的成品序号+版本号+料件序号";

					if (!isRepeat) {
						isRepeat = true;
					}
				}
			}

			obj.setErrinfo(err);

			obj.setBom(bom);

			list.add(obj);
		}

		/*
		 * 覆盖导入
		 */
		if (!cbIsOverwrite.isSelected()) {
			int size = list.size();

			CommonProgress.setMessage("系统正在检验文件资料，一共有：" + size
					+ "笔bom资料，请稍后...");

			// 1.1 验证emsbom数据 -------- Map<成品序号,Map<版本号,Map<料件序号,单耗>>>
			Map<Integer, Map<Integer, Map<Integer, EmsHeadH2kBom>>> emsBomMap = new HashMap<Integer, Map<Integer, Map<Integer, EmsHeadH2kBom>>>();

			EmsEdiHeadH2kBomFrom obj = null;

			Integer exgSeqNum = null;// 成品序号

			Integer version = null;// 版本号

			Integer imgSeqNum = null;// 料件序号

			// key = 料件序号 + 版本号 + 成品序号
			for (int i = 0; i < size; i++) {

				obj = list.get(i);

				// 获取料件序号
				exgSeqNum = obj.getSeqNum();

				// 获取 版本号
				version = obj.getVersion();

				// 获取成品序号
				imgSeqNum = obj.getBom().getSeqNum();

				// 取得 对应的 成品单耗
				Map<Integer, Map<Integer, EmsHeadH2kBom>> exgMap = emsBomMap
						.get(exgSeqNum);

				// 如果没有 或者没有包含的成品序号那么就 查出对应的数据 放进 emsBomMap
				if (exgMap == null && !emsBomMap.containsKey(exgSeqNum)) {

					exgMap = this.manualdeclearAction
							.getEmsHeadH2kBomByExgSeqNum(
									new Request(CommonVars.getCurrUser()),
									emsHeadH2k, exgSeqNum);

					emsBomMap.put(exgSeqNum, exgMap);
				}

				// 如果 有对应的成品序号
				if (exgMap != null) {

					// 那么就获取对应的版本号
					Map<Integer, EmsHeadH2kBom> versionMap = exgMap
							.get(version);

					if (versionMap != null) {

						// 获取对应的单耗
						EmsHeadH2kBom h2kBom = versionMap.get(imgSeqNum);

						// 出现重复的单耗 询问是否覆盖导入
						if (h2kBom != null) {

							obj.setErrinfo(obj.getErrinfo()
									+ "该版本料件已经存在！如果要导入请选择覆盖导入！否则将忽略该单耗。");

						}
					}
				}

				if ((i + 1) % 1000 == 0) {

					CommonProgress.setMessage("系统检验" + (i + 1) + "笔单耗，一共有："
							+ size);
				}
			}
		}

		/*
		 * 是否合并单耗
		 */
		if (isMerge) {

			final int size = list.size();

			/*
			 * 把list转化为map
			 */
			Map<String, EmsEdiHeadH2kBomFrom> map = new HashMap<String, EmsEdiHeadH2kBomFrom>();

			CommonUtils.listToMap(list, map,
					new GetKeyValueImpl<EmsEdiHeadH2kBomFrom>() {

						// 用于循环结束后判断是否可以计算合并损耗率的 标记
						private int count;

						// 企业 料件净耗
						private BigDecimal eUnitWear = null;

						// 企业 料件损耗
						private BigDecimal eWear = null;

						// 企业 成品单项用量
						private BigDecimal eUnitDosage = null;

						// key : 成品序号 + 版本号 + 料件序号 / value : 单项用量和 <缓存对应的单项用量和>
						private Map<String, BigDecimal> eUnitDosages = new HashMap<String, BigDecimal>();

						public String getKey(EmsEdiHeadH2kBomFrom e) {

							String key = null;

							EmsHeadH2kBom bom = e.getBom();

							key = e.getSeqNum() + "/" + e.getVersion() + "/"
									+ bom.getSeqNum();

							return key;
						}

						public void put(EmsEdiHeadH2kBomFrom e, Map map) {

							// 获取list里面EmsEdiHeadH2kBomFrom的损耗
							Double wear = e.getBom().getWear() == null ? Double
									.valueOf("0.0") : e.getBom().getWear();

							// 获取list 里面 EmsEdiHeadH2kBomFrom的单耗
							Double unitWear = e.getBom().getUnitWear() == null ? Double
									.valueOf("0.0") : e.getBom().getUnitWear();

							// 获取 key
							String key = getKey(e);

							// 获取旧值EmsEdiHeadH2kBomFrom ； 需要先判断是否已 放进去一个
							// EmsEdiHeadH2kBomFrom
							// 如果检验没有待单耗和损耗计算完毕后放进去
							EmsEdiHeadH2kBomFrom o = (EmsEdiHeadH2kBomFrom) map
									.get(key);

							/*
							 * 每次循环都更新map
							 * <e:每次迭代list的EmsEdiHeadH2kBomFrom对象><o:map已保存的对象>
							 */
							if (o == null) {

								super.put(e, map);

								key = getKey(e);

							} else {

								// 获取 map 里面 EmsEdiHeadH2kBomFrom的单耗
								Double unitWearInMap = o.getBom().getWear() == null ? Double
										.valueOf("0.0") : o.getBom()
										.getUnitWear();

								// 净耗相加
								e.getBom()
										.setUnitWear(unitWear + unitWearInMap);

								super.put(e, map);

							}

							calculateMergeUnitWearBefore(unitWear, wear, key);

							count++;

							// 如果已经全部循环完毕就开始进行计算
							calculateMergeUnitWearAfter(count, map);
						}

						/**
						 * <缓存>合并前的单项用量
						 * 
						 * @param unitWear
						 *            每次循环拿到的净耗
						 * @param wear
						 *            每次循环拿到的损耗率%
						 * @param key
						 *            map保存对应的键值
						 */
						private void calculateMergeUnitWearBefore(
								Double unitWear, Double wear, String key) {

							// 获取 净耗
							eUnitWear = new BigDecimal(unitWear);

							// 获取损耗 (因表示方式是 % 百分比 因此需要除100)
							eWear = new BigDecimal(wear).divide(new BigDecimal(
									"100.0"));

							// 计算单项用量 = 净耗/(1-损耗) 保留 9 位小数
							eUnitDosage = eUnitWear.divide(
									new BigDecimal("1.0").subtract(eWear), 9,
									BigDecimal.ROUND_HALF_UP);

							// 当获取的key值包含单项用量就相加
							if (eUnitDosages.get(key) != null) {

								eUnitDosage = eUnitDosages.get(key).add(
										eUnitDosage);

							}

							// 计算后 缓存 单项用量和
							eUnitDosages.put(key, eUnitDosage);

						}

						/**
						 * 外部循环结束后开始计算 合并单耗
						 * 
						 * @param count
						 *            用于触发计算合并单耗的标记数
						 * @param map
						 */
						private void calculateMergeUnitWearAfter(int count,
								Map map) {

							if (count == (size - 1)) {

								Iterator<String> it = map.keySet().iterator();

								// 开始求合并损耗率
								for (; it.hasNext();) {

									String key = it.next();

									// 这里获取的是单项用量和
									eUnitDosage = eUnitDosages.get(key);

									EmsEdiHeadH2kBomFrom eehbf = (EmsEdiHeadH2kBomFrom) map
											.get(key);

									// 这里获取的是单耗和
									eUnitWear = new BigDecimal(eehbf.getBom()
											.getUnitWear());

									// 合并损耗率公式
									// 合并损耗率 =1-(净耗1+...+净耗n)/(单项用量1+...+单项用量n)
									BigDecimal one = new BigDecimal("1");

									// 这里获取的是 <合并损耗率>
									eWear = one.subtract(eUnitWear.divide(
											eUnitDosage, 9,
											BigDecimal.ROUND_HALF_UP));

									eWear = eWear.multiply(new BigDecimal(
											"100.0"));

									eehbf.getBom().setWear(eWear.doubleValue());

								}

							}

						}

					});

			list = new ArrayList(map.values());
		}

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

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
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

					// 这里判断 是否 出现重复 成品序号+版本号+料件序号
					if (isRepeat) {

						JOptionPane.showMessageDialog(DgEmsHeadH2kImport1.this,
								"成品序号存在多个对应相同的料件序号和版本号,请检查单耗导入信息!", "提示",
								JOptionPane.WARNING_MESSAGE);

						return;

					}

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
								.showOptionDialog(DgEmsHeadH2kImport1.this,
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
				CommonProgress.showProgressDialog(DgEmsHeadH2kImport1.this);
				CommonProgress.setMessage("系统正在保存数据资料，请稍后...");
				// boolean isSave = chechSave();
				// if (!isSave) {
				// CommonProgress.closeProgressDialog();
				// String warnInfo = "重复位置：\n";
				// for (int i = 0; i < listNum.size(); i++) {
				// warnInfo += "成品序号为：" + listNum.get(i)[0] + " 版本号为："
				// + listNum.get(i)[1] + " 料件号为："
				// + listNum.get(i)[2] + "\n";
				// }
				// if (!cbIsOverwrite.isSelected()) {
				// if (JOptionPane.showConfirmDialog(
				// DgEmsHeadH2kImport1.this, "版本号不可重复，请确认版本号无重复！"
				// + "\n" + warnInfo, "确认",
				// JOptionPane.CLOSED_OPTION,
				// JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
				// return;
				// }
				// return;
				// } else {
				// if (JOptionPane.showConfirmDialog(null,
				// "提醒：导入的BOM已经存在，是否覆盖?\n覆盖只对状态为“新增”BOM有效，其他状态的不能覆盖。"
				// + "\n" + warnInfo, "提示",
				// JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
				// return;
				// }
				// }
				// }
				int[] x = manualdeclearAction.saveToEmsHeadH2k(new Request(
						CommonVars.getCurrUser()), emsHeadH2k, afterList,
						cbIsOverwrite.isSelected());

				CommonProgress.closeProgressDialog();

				JOptionPane.showMessageDialog(DgEmsHeadH2kImport1.this,
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

	// public boolean chechSave() {
	// boolean isSave = true;
	// Object[] obj = null;
	// listNum = new ArrayList<Object[]>();
	// List tableList = tableModel.getList();
	// List dateList = manualdeclearAction
	// .findAllEmsHeadH2kSqeNumAndVersion(new Request(CommonVars
	// .getCurrUser()));
	// if (dateList == null)
	// return false;
	// for (int i = 0; i < dateList.size(); i++) {
	// for (int j = 0; j < tableList.size(); j++) {
	// if (dateList.get(i).equals(
	// ((EmsEdiHeadH2kBomFrom) tableList.get(j)).getSeqNum()
	// .toString()
	// + "/"
	// + ((EmsEdiHeadH2kBomFrom) tableList.get(j))
	// .getVersion().toString()
	// + "/"
	// + ((EmsEdiHeadH2kBomFrom) tableList.get(j))
	// .getBom().getSeqNum().toString())) {
	// isSave = false;
	// obj = new Object[3];
	// // 成品序号位置
	// obj[0] = ((EmsEdiHeadH2kBomFrom) tableList.get(j))
	// .getSeqNum();
	// // 版本号
	// obj[1] = ((EmsEdiHeadH2kBomFrom) tableList.get(j))
	// .getVersion();
	// // 料件号
	// obj[2] = ((EmsEdiHeadH2kBomFrom) tableList.get(j))
	// .getBom().getSeqNum();
	// listNum.add(obj);
	// }
	// }
	// }
	// return isSave;
	// }

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
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
		int state = fileChooser.showSaveDialog(DgEmsHeadH2kImport1.this);
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
				DgEmsHeadH2kImport1.this);
		dgSave.setTableModel(tableModel);
		dgSave.setFileName(fileName);
		dgSave.setTitle("保存(" + ((JDialog) DgEmsHeadH2kImport1.this).getTitle()
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

					DgEmsHeadH2kImport1.this.dispose();
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

	/**
	 * This method initializes cbIsOverwrite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOverwrite() {
		if (cbIsOverwrite == null) {
			cbIsOverwrite = new JCheckBox();
			cbIsOverwrite
					.setText("\u8d44\u6599\u5b58\u5728\u8986\u76d6\u5bfc\u5165");
		}
		return cbIsOverwrite;
	}

	/**
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("\u7b2c\u4e00\u884c\u4e3a\u6807\u9898\u884c");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	/**
	 * This method initializes cbJF
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbJF() {
		if (cbJF == null) {
			cbJF = new JCheckBox();
			cbJF.setText("\u7e41\u8f6c\u7b80");
			cbJF.setVisible(false);
		}
		return cbJF;
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
			jToolBar.add(getCbIsOverwrite());
			jToolBar.add(getCbCheckTitle());
			jToolBar.add(getCbMergeUnitWaste());
			jToolBar.add(getCbJF());
		}
		return jToolBar;
	}

	public List getExgs() {
		return exgs;
	}

	public void setExgs(List<EmsHeadH2kExg> exgs) {
		// System.out.println("----成品-------");
		// for(EmsHeadH2kExg exg : exgs){
		// System.out.println(exg.getSeqNum());
		// }
		this.exgs = exgs;
	}

	public List getImgs() {
		return imgs;
	}

	public void setImgs(List<EmsHeadH2kImg> imgs) {
		// System.out.println("----料件-------");
		// for(EmsHeadH2kImg img : imgs){
		// System.out.println(img.getSeqNum());
		// }
		this.imgs = imgs;
	}

	/**
	 * This method initializes cbMergeUnitWaste
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbMergeUnitWaste() {
		if (cbMergeUnitWaste == null) {
			cbMergeUnitWaste = new JCheckBox();
			cbMergeUnitWaste.setText("合并单耗");
		}
		return cbMergeUnitWaste;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
