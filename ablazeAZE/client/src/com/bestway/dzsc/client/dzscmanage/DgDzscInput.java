package com.bestway.dzsc.client.dzscmanage;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DzscManageType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.client.message.DzscCommon;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsImgBill;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorHead;
import com.bestway.dzsc.dzscmanage.entity.DzscEmsPorWjHead;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsBomBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsExgBill;
import com.bestway.dzsc.dzscmanage.entity.TempDzscEmsImgBill;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgDzscInput extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null; // 料件

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null; // 成品

	private JScrollPane jScrollPane2 = null;

	private JTable jTable2 = null; // 单耗

	private File txtFile = null;

	private Hashtable gbHash = null;

	private CustomBaseAction customBaseAction = null;

	private DzscAction dzscAction = null;

	private DzscEmsPorHead head = null; // @jve:decl-index=0:

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelBom = null;

	private JCheckBox jCheckBox = null;

	private JCheckBox cbJF = null;

	private Integer dzscManageType = null; // @jve:decl-index=0:

	private JCheckBox cbIsOverwrite = null;
	private boolean isOk = false;

	/**
	 * This is the default constructor
	 */
	public DgDzscInput() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(687, 469);
		this.setContentPane(getJContentPane());
		this.setTitle("通关备案数据导入接口");
		initTableImg(new ArrayList());
		initTableExg(new ArrayList());
		initTableBom(new ArrayList());
	}

	/**
	 * 初始化料件
	 */
	private void initTableImg(List list) {
		tableModelImg = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("1.料件序号", "img.seqNum", 60,
								Integer.class));
						list.add(addColumn("2.商品编码", "img.complex.code", 100));
						list.add(addColumn("3.商品名称", "img.name", 100));
						list.add(addColumn("4.商品规格", "img.spec", 100));
						list.add(addColumn("5.单位", "img.unit.name", 60));
						list.add(addColumn("6.备案数量", "img.amount", 60));
						list.add(addColumn("7.单价", "img.price", 60));
						list.add(addColumn("8.原产国", "img.country.name", 60));
						list
								.add(addColumn("9.增减免税方式", "img.levyMode.name",
										60));
						list.add(addColumn("10.归并序号", "img.tenSeqNum", 60));
						list.add(addColumn("11.报关助记码", "img.customsNo", 60));
						list.add(addColumn("错误信息", "errinfo", 300));
						return list;
					}
				});
		List<JTableListColumn> cm = tableModelImg.getColumns();
		cm.get(6).setFractionCount(5);
		cm.get(7).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(6)).setFractionCount(5);
		// ((JTableListColumn)jTable.getColumnModel().getColumn(7)).setFractionCount(5);
	}

	/**
	 * 初始化成品
	 */
	private void initTableExg(List list) {
		tableModelExg = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("1.成品序号", "exg.seqNum", 60,
								Integer.class));
						list.add(addColumn("2.商品编码", "exg.complex.code", 100));
						list.add(addColumn("3.商品名称", "exg.name", 100));
						list.add(addColumn("4.商品规格", "exg.spec", 100));
						list.add(addColumn("5.单位", "exg.unit.name", 60));
						list.add(addColumn("6.备案数量", "exg.amount", 60));
						list.add(addColumn("7.单价", "exg.price", 60));
						list.add(addColumn("8.原料费", "exg.imgMoney", 60));
						list.add(addColumn("9.加工费", "exg.machinPrice", 60));
						list.add(addColumn("10.消费国", "exg.country.name", 60));
						list
								.add(addColumn("11.增减免税方式",
										"exg.levyMode.name", 60));
						list.add(addColumn("12.归并序号", "exg.tenSeqNum", 60));
						list.add(addColumn("13.报关助记码", "exg.customsNo", 60));
						list.add(addColumn("错误信息", "errinfo", 300));
						return list;
					}
				});
		List<JTableListColumn> cm = tableModelExg.getColumns();
		cm.get(6).setFractionCount(5);
		cm.get(7).setFractionCount(5);
		// ((JTableListColumn)jTable1.getColumnModel().getColumn(6)).setFractionCount(5);
		// ((JTableListColumn)jTable1.getColumnModel().getColumn(7)).setFractionCount(5);
	}

	/**
	 * 初始化单耗
	 */
	private void initTableBom(List list) {
		tableModelBom = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("1.成品序号",
										"bom.dzscEmsExgBill.seqNum", 60,
										Integer.class));
						list.add(addColumn("2.料件序号", "bom.imgSeqNum", 100));
						list.add(addColumn("3.单耗", "bom.unitWare", 100));
						list.add(addColumn("4.损耗", "bom.ware", 100));
						list.add(addColumn("5.非保税料件比例%", "bom.nonMnlRatio",
										100));
						list.add(addColumn("错误信息", "errinfo", 300));
						return list;
					}
				});
		List<JTableListColumn> cm = tableModelExg.getColumns();
		cm.get(3).setFractionCount(9);
		cm.get(4).setFractionCount(5);
		cm.get(5).setFractionCount(5);
		// ((JTableListColumn)jTable2.getColumnModel().getColumn(3)).setFractionCount(9);
		// ((JTableListColumn)jTable2.getColumnModel().getColumn(4)).setFractionCount(5);
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
			jToolBar.add(getJCheckBox());
			jToolBar.add(getCbIsOverwrite());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("打开文件");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser.showOpenDialog(DgDzscInput.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
		
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return jButton;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgDzscInput.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				if (jTabbedPane.getSelectedIndex() == 0) {
					list = parseTxtFileImg();
				} else if (jTabbedPane.getSelectedIndex() == 1) {	
					list = parseTxtFileExg();
				} else {
					list = parseTxtFileBom();
				}
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {

			} finally {
				CommonProgress.closeProgressDialog();
				if (jTabbedPane.getSelectedIndex() == 0) {
					initTableImg(list);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					initTableExg(list);
				} else {
					initTableBom(list);
				}
			}
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

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = changeStr(source[i]);
		}
		return newStrs;
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		// System.out.println("--"+dataIndex+"="+fileRow[dataIndex]);
		return fileRow[dataIndex];
	}

	private List parseTxtFileImg() {
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		List unitList = CustomBaseList.getInstance().getUnits();

		List countryList = CustomBaseList.getInstance().getCountrys();

		List levyModeList = CustomBaseList.getInstance().getLevymode();
		// List tenSeqNum = dzscAction.findTenInnerSeqNum(new Request(CommonVars
		// .getCurrUser()));
		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		Hashtable<String, Country> hsCountry = new Hashtable<String, Country>();
		Hashtable<String, LevyMode> hsLevyMode = new Hashtable<String, LevyMode>();
		// Hashtable<String, Integer> hstenSeqNum = new Hashtable<String,
		// Integer>();
		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hs.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsUnit.put(u.getName(), u);
		}
		for (int i = 0; i < countryList.size(); i++) {
			Country c = (Country) countryList.get(i);
			hsCountry.put(c.getName(), c);
		}
		for (int i = 0; i < levyModeList.size(); i++) {
			LevyMode c = (LevyMode) levyModeList.get(i);
			hsLevyMode.put(c.getName(), c);
		}
		// for (int i = 0; i < tenSeqNum.size(); i++) {
		// DzscTenInnerMerge obj = (DzscTenInnerMerge) tenSeqNum.get(i);
		// String complex = obj.getTenComplex() == null ? "" : obj
		// .getTenComplex().getCode();
		// String name = obj.getTenPtName() == null ? "" : obj.getTenPtName();
		// String spec = obj.getTenPtSpec() == null ? "" : obj.getTenPtSpec();
		// String unitCode = obj.getTenUnit() == null ? "" : obj.getTenUnit()
		// .getCode();
		// String key = complex + "/" + name + "/" + spec + "/" + unitCode;
		// if (hstenSeqNum.get(key) == null) {
		// hstenSeqNum.put(key, obj.getTenSeqNum());
		// }
		// }

		boolean ischange = true;
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		List<Integer> lsSeqNum = new ArrayList<Integer>();
		List<Integer> lsInnerMergeSeqNum = new ArrayList<Integer>();
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//			
			lines = FileReading.readExcel(txtFile, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(txtFile, 0, null);
		}

		List list = new ArrayList();
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

			TempDzscEmsImgBill obj = new TempDzscEmsImgBill();
			DzscEmsImgBill img = new DzscEmsImgBill();
			String err = "";
			if (jCheckBox.isSelected()) {
				int zcount = 10;// 字段数目
				for (int j = 0; j < zcount; j++) {
					String value = getFileColumnValue(strs, j);
					if (j == 0) {// 料件序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   序号不合法/";
							continue;
						}
						if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
							err = err + "[" + value + "]   序号重复/";
							// continue;
						} else {
							lsSeqNum.add(Double.valueOf(value).intValue());
						}
						img.setSeqNum(Double.valueOf(value).intValue());
					} else if (j == 1) {// 商品编码
						if (hs.get(value) == null) {
							Complex cobj = dzscAction.findCustomsComplexByCode(
									new Request(CommonVars.getCurrUser()),
									value);

							if (cobj != null) {
								hs.put(cobj.getCode(), cobj);
								img.setComplex(cobj);// 商品编码
							} else {
								err = err + "[" + value + "]   不正确的商品编码/";
							}
						} else {
							Complex c = (Complex) hs.get(value);
							img.setComplex(c);
						}
					} else if (j == 2) {// 名称/规格
						if (value != null && !value.equals("")) {
							int x = value.indexOf("/");
							if (x == -1) {
								img.setName(value);
							} else {
								img.setName(value.substring(0, x).trim());
								img.setSpec(value.substring(x + 1, value
										.length()));
							}
						} else {
							err = err + "[" + value + "]   名称规格不可为空/";
						}
					} else if (j == 3) {// 单位
						if (value == null || "".equals(value)) {
							err = err + "[" + value + "]   单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								img.setUnit(u);
							} else {
								err = err + "[" + value + "]   单位不存在/";
							}
						}
					} else if (j == 4) {// 备案数量
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   备案不合法/";
							continue;
						}
						img.setAmount(Double.valueOf(value));
					} else if (j == 5) {// 单价
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   单价不合法/";
							continue;
						}
						img.setPrice(Double.valueOf(value));
					} else if (j == 6) {// 原产国
						if (value != null && !"".equals(value.trim())) {
							if (hsCountry.get(value.trim()) != null) {
								Country u = (Country) hsCountry.get(value
										.trim());
								img.setCountry(u);
							} else {
								err = err + "[" + value + "]   原产国不存在/";
							}
						}
					} else if (j == 7) {// 增减免税方式
						if (value != null && !"".equals(value.trim())) {
							if (hsLevyMode.get(value.trim()) != null) {
								LevyMode u = (LevyMode) hsLevyMode.get(value
										.trim());
								img.setLevyMode(u);
							} else {
								err = err + "[" + value + "]   增减免税方式不存在/";
							}
						}
					} else if (j == 8) {// 料件归并序号
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]  归并序号不合法/";
								continue;
							}
							if (lsInnerMergeSeqNum.contains(Double.valueOf(
									value).intValue())) {
								err = err + "[" + value + "]  归并序号重复/";
								// continue;
							} else {
								lsInnerMergeSeqNum.add(Double.valueOf(value)
										.intValue());
							}
							img.setTenSeqNum(Double.valueOf(value).intValue());
						}
						// else {
						// if (isMaterielManageType()) {
						// err = err + " 归并序号为空/";
						// }
						// }
					} else if (j == 9) {// 报关助记码
						img.setCustomsNo(value);
					}
				}
			} else {
				int zcount = 11;// 字段数目
				for (int j = 0; j < zcount; j++) {
					String value = getFileColumnValue(strs, j);
					if (j == 0) {// 料件序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   序号不合法/";
							continue;
						}
						if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
							err = err + "[" + value + "]   序号重复/";
							// continue;
						} else {
							lsSeqNum.add(Double.valueOf(value).intValue());
						}
						img.setSeqNum(Double.valueOf(value).intValue());
					} else if (j == 1) {// 商品编码
						if (hs.get(value) == null) {
							Complex cobj = dzscAction.findCustomsComplexByCode(
									new Request(CommonVars.getCurrUser()),
									value);

							if (cobj != null) {
								hs.put(cobj.getCode(), cobj);
								img.setComplex(cobj);// 商品编码
							} else {
								err = err + "[" + value + "]   不正确的商品编码/";
							}
						} else {
							Complex c = (Complex) hs.get(value);
							img.setComplex(c);
						}
					} else if (j == 2) {// 名称
						if (value != null && !value.equals("")) {
							img.setName(value);
						} else {
							err = err + "[" + value + "]   名称不可为空/";
						}
					} else if (j == 3) {// 规格
						img.setSpec(value);

					} else if (j == 4) {// 单位
						if (value == null || "".equals(value)) {
							err = err + "[" + value + "]   单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								img.setUnit(u);
							} else {
								err = err + "[" + value + "]   单位不存在/";
							}
						}
					} else if (j == 5) {// 备案数量
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   备案不合法/";
							continue;
						}
						img.setAmount(Double.valueOf(value));
					} else if (j == 6) {// 单价
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   单价不合法/";
							continue;
						}
						img.setPrice(Double.valueOf(value));
					} else if (j == 7) {// 原产国
						if (value != null && !"".equals(value.trim())) {
							if (hsCountry.get(value.trim()) != null) {
								Country u = (Country) hsCountry.get(value
										.trim());
								img.setCountry(u);
							} else {
								err = err + "[" + value + "]   原产国不存在/";
							}
						}
					} else if (j == 8) {// 增减免税方式
						if (value != null && !"".equals(value.trim())) {
							if (hsLevyMode.get(value.trim()) != null) {
								LevyMode u = (LevyMode) hsLevyMode.get(value
										.trim());
								img.setLevyMode(u);
							} else {
								err = err + "[" + value + "]   增减免税方式不存在/";
							}
						}
					} else if (j == 9) {// 料件归并序号
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]  归并序号不合法/";
								continue;
							}
							if (lsInnerMergeSeqNum.contains(Double.valueOf(
									value).intValue())) {
								err = err + "[" + value + "]  归并序号重复/";
								// continue;
							} else {
								lsInnerMergeSeqNum.add(Double.valueOf(value)
										.intValue());
							}
							img.setTenSeqNum(Double.valueOf(value).intValue());
						}
						// else {
						// if (isMaterielManageType()) {
						// err = err + " 归并序号为空/";
						// }
						// }
					} else if (j == 10) {// 报关助记码
						img.setCustomsNo(value);
					}
				}
			}
			// String key = (img.getComplex() == null ? "" : img.getComplex()
			// .getCode())
			// + "/"
			// + (img.getName() == null ? "" : img.getName())
			// + "/"
			// + (img.getSpec() == null ? "" : img.getSpec())
			// + "/"
			// + (img.getUnit() == null ? "" : img.getUnit().getCode());

			// img.setTenSeqNum(hstenSeqNum.get(key) == null ? null
			// : (Integer) hstenSeqNum.get(key));
			// System.out.println("----:"+img.getSeqNum()+"---:"+img.getTenSeqNum());
			img.setMoney((img.getAmount() == null ? Double.valueOf(0) : img
					.getAmount())
					* (img.getPrice() == null ? Double.valueOf(0) : img
							.getPrice()));
			img.setDzscEmsPorHead(head);
			img.setCompany(CommonVars.getCurrUser().getCompany());
			// img.setModifyMark(ModifyMarkState.ADDED);
			obj.setImg(img);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	private List parseTxtFileExg() {
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		List unitList = CustomBaseList.getInstance().getUnits();
		List countryList = CustomBaseList.getInstance().getCountrys();

		List levyModeList = CustomBaseList.getInstance().getLevymode();
		// List tenSeqNum = dzscAction.findTenInnerSeqNum(new Request(CommonVars
		// .getCurrUser()));
		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		Hashtable<String, Country> hsCountry = new Hashtable<String, Country>();
		Hashtable<String, LevyMode> hsLevyMode = new Hashtable<String, LevyMode>();
		Hashtable<String, Integer> hstenSeqNum = new Hashtable<String, Integer>();
		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hs.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsUnit.put(u.getName(), u);
		}
		for (int i = 0; i < countryList.size(); i++) {
			Country c = (Country) countryList.get(i);
			hsCountry.put(c.getName(), c);
		}
		for (int i = 0; i < levyModeList.size(); i++) {
			LevyMode c = (LevyMode) levyModeList.get(i);
			hsLevyMode.put(c.getName(), c);
		}
		// for (int i = 0; i < tenSeqNum.size(); i++) {
		// DzscTenInnerMerge obj = (DzscTenInnerMerge) tenSeqNum.get(i);
		// String complex = obj.getTenComplex() == null ? "" : obj
		// .getTenComplex().getCode();
		// String name = obj.getTenPtName() == null ? "" : obj.getTenPtName();
		// String spec = obj.getTenPtSpec() == null ? "" : obj.getTenPtSpec();
		// String unitCode = obj.getTenUnit() == null ? "" : obj.getTenUnit()
		// .getCode();
		// String key = complex + "/" + name + "/" + spec + "/" + unitCode;
		// if (hstenSeqNum.get(key) == null) {
		// hstenSeqNum.put(key, obj.getTenSeqNum());
		// }
		// }
		boolean ischange = true;
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		List<Integer> lsSeqNum = new ArrayList<Integer>();
		List<Integer> lsInnerMergeSeqNum = new ArrayList<Integer>();
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//			
			lines = FileReading.readExcel(txtFile, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(txtFile, 0, null);
		}

		List list = new ArrayList();
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

			TempDzscEmsExgBill obj = new TempDzscEmsExgBill();
			DzscEmsExgBill exg = new DzscEmsExgBill();
			String err = "";
			if (this.jCheckBox.isSelected()) {
				int zcount = 12;// 字段数目
				for (int j = 0; j < zcount; j++) {
					String value = getFileColumnValue(strs, j);
					if (j == 0) {// 料件序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   序号不合法/";
							continue;
						}
						if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
							err = err + "[" + value + "]   序号重复/";
							continue;
						} else {
							lsSeqNum.add(Double.valueOf(value).intValue());
						}
						exg.setSeqNum(Double.valueOf(value).intValue());
					} else if (j == 1) {// 商品编码
						if (hs.get(value) == null) {
							Complex cobj = dzscAction.findCustomsComplexByCode(
									new Request(CommonVars.getCurrUser()),
									value);

							if (cobj != null) {
								hs.put(cobj.getCode(), cobj);
								exg.setComplex(cobj);// 商品编码
							} else {
								err = err + "[" + value + "]   不正确的商品编码/";
							}
						} else {
							Complex c = (Complex) hs.get(value);
							exg.setComplex(c);
						}
					} else if (j == 2) {// 名称/规格
						if (value != null && !value.equals("")) {
							int x = value.indexOf("/");
							if (x == -1) {
								exg.setName(value);
							} else {
								exg.setName(value.substring(0, x).trim());
								exg.setSpec(value.substring(x + 1, value
										.length()));
							}
						} else {
							err = err + "[" + value + "]   名称规格不可为空/";
						}
					} else if (j == 3) {// 单位
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								exg.setUnit(u);
							} else {
								err = err + "[" + value + "]   单位不存在/";
							}
						}
					} else if (j == 4) {// 备案数量
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   备案数量不合法/";
							continue;
						}
						exg.setAmount(Double.valueOf(value));
					} else if (j == 5) {// 单价
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   单价不合法/";
							continue;
						}
						exg.setPrice(Double.valueOf(value));
					} else if (j == 6) {// 原料费单价
						if (value != null) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   原料费单价不合法/";
								continue;
							}
							exg.setImgMoney(Double.valueOf(value));
						}
					} else if (j == 7) {// 加工费单价
						if (value != null) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   加工费单价不合法/";
								continue;
							}
							exg.setMachinPrice(Double.valueOf(value));
						}
					} else if (j == 8) {// 消费国
						if (value != null && !"".equals(value.trim())) {
							if (hsCountry.get(value.trim()) != null) {
								Country u = (Country) hsCountry.get(value
										.trim());
								exg.setCountry(u);
							} else {
								err = err + "[" + value + "]   消费国不存在/";
							}
						}
					} else if (j == 9) {// 增减免税方式
						if (value != null && !"".equals(value.trim())) {
							if (hsLevyMode.get(value.trim()) != null) {
								LevyMode u = (LevyMode) hsLevyMode.get(value
										.trim());
								exg.setLevyMode(u);
							} else {
								err = err + "[" + value + "]   增减免税方式不存在/";
							}
						}
					} else if (j == 10) {// 成品归并序号
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]  归并序号不合法/";
								continue;
							}
							if (lsInnerMergeSeqNum.contains(Double.valueOf(
									value).intValue())) {
								err = err + "[" + value + "]  归并序号重复/";
								// continue;
							} else {
								lsInnerMergeSeqNum.add(Double.valueOf(value)
										.intValue());
							}
							exg.setTenSeqNum(Double.valueOf(value).intValue());
						}
						// else {
						// if (isMaterielManageType()) {
						// err = err + " 归并序号为空/";
						// }
						// }
					} else if (j == 11) {// 报关助记码
						exg.setCustomsNo(value);
					}
				}
			} else {
				int zcount = 13;// 字段数目
				for (int j = 0; j < zcount; j++) {
					String value = getFileColumnValue(strs, j);
					if (j == 0) {// 料件序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   序号不合法/";
							continue;
						}
						if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
							err = err + "[" + value + "]   序号重复/";
							continue;
						} else {
							lsSeqNum.add(Double.valueOf(value).intValue());
						}
						exg.setSeqNum(Double.valueOf(value).intValue());
					} else if (j == 1) {// 商品编码
						if (hs.get(value) == null) {
							Complex cobj = dzscAction.findCustomsComplexByCode(
									new Request(CommonVars.getCurrUser()),
									value);

							if (cobj != null) {
								hs.put(cobj.getCode(), cobj);
								exg.setComplex(cobj);// 商品编码
							} else {
								err = err + "[" + value + "]   不正确的商品编码/";
							}
						} else {
							Complex c = (Complex) hs.get(value);
							exg.setComplex(c);
						}
					} else if (j == 2) {// 名称
						if (value != null && !value.equals("")) {
							exg.setName(value);
						} else {
							err = err + "[" + value + "]   名称不可为空/";
						}
					} else if (j == 3) {// 规格
						exg.setSpec(value);

					} else if (j == 4) {// 单位
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								exg.setUnit(u);
							} else {
								err = err + "[" + value + "]   单位不存在/";
							}
						}
					} else if (j == 5) {// 备案数量
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   备案数量不合法/";
							continue;
						}
						exg.setAmount(Double.valueOf(value));
					} else if (j == 6) {// 单价
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   单价不合法/";
							continue;
						}
						exg.setPrice(Double.valueOf(value));
					} else if (j == 7) {// 原料费单价
						if (value != null) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   原料费单价不合法/";
								continue;
							}
							exg.setImgMoney(Double.valueOf(value));
						}
					} else if (j == 8) {// 加工费单价
						if (value != null) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   加工费单价不合法/";
								continue;
							}
							exg.setMachinPrice(Double.valueOf(value));
						}
					} else if (j == 9) {// 消费国
						if (value != null && !"".equals(value.trim())) {
							if (hsCountry.get(value.trim()) != null) {
								Country u = (Country) hsCountry.get(value
										.trim());
								exg.setCountry(u);
							} else {
								err = err + "[" + value + "]   消费国不存在/";
							}
						}
					} else if (j == 10) {// 增减免税方式
						if (value != null && !"".equals(value.trim())) {
							if (hsLevyMode.get(value.trim()) != null) {
								LevyMode u = (LevyMode) hsLevyMode.get(value
										.trim());
								exg.setLevyMode(u);
							} else {
								err = err + "[" + value + "]   增减免税方式不存在/";
							}
						}
					} else if (j == 11) {// 成品归并序号
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]  归并序号不合法/";
								continue;
							}
							if (lsInnerMergeSeqNum.contains(Double.valueOf(
									value).intValue())) {
								err = err + "[" + value + "]  归并序号重复/";
								// continue;
							} else {
								lsInnerMergeSeqNum.add(Double.valueOf(value)
										.intValue());
							}
							exg.setTenSeqNum(Double.valueOf(value).intValue());
						}
						// else {
						// if (isMaterielManageType()) {
						// err = err + " 归并序号为空/";
						// }
						// }
					} else if (j == 12) {// 报关助记码
						exg.setCustomsNo(value);
					}
				}
			}
			// String key = (exg.getComplex() == null ? "" : exg.getComplex()
			// .getCode())
			// + "/"
			// + (exg.getName() == null ? "" : exg.getName())
			// + "/"
			// + (exg.getSpec() == null ? "" : exg.getSpec())
			// + "/"
			// + (exg.getUnit() == null ? "" : exg.getUnit().getCode());
			//
			// exg.setTenSeqNum(hstenSeqNum.get(key) == null ? null
			// : (Integer) hstenSeqNum.get(key));
			exg.setMoney((exg.getAmount() == null ? Double.valueOf(0) : exg
					.getAmount())
					* (exg.getPrice() == null ? Double.valueOf(0) : exg
							.getPrice()));
			exg.setMachinMoney((exg.getAmount() == null ? Double.valueOf(0)
					: exg.getAmount())
					* (exg.getMachinPrice() == null ? Double.valueOf(0) : exg
							.getMachinPrice()));
			exg.setDzscEmsPorHead(head);
			exg.setCompany(CommonVars.getCurrUser().getCompany());
			// exg.setModifyMark(ModifyMarkState.ADDED);
			obj.setExg(exg);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	private List parseTxtFileBom() {
		Hashtable<Integer, DzscEmsExgBill> hsexg = new Hashtable<Integer, DzscEmsExgBill>();
		Hashtable<Integer, DzscEmsImgBill> hsimg = new Hashtable<Integer, DzscEmsImgBill>();
		List listexg = dzscAction.findDzscEmsExgBill(new Request(CommonVars
				.getCurrUser()), head);
		List listimg = dzscAction.findEmsPorImgBill(new Request(CommonVars
				.getCurrUser()), head);
		for (int i = 0; i < listexg.size(); i++) {
			DzscEmsExgBill c = (DzscEmsExgBill) listexg.get(i);
			if (c.getSeqNum() != null) {
				hsexg.put(c.getSeqNum(), c);
			}
		}
		for (int i = 0; i < listimg.size(); i++) {
			DzscEmsImgBill u = (DzscEmsImgBill) listimg.get(i);
			if (u.getSeqNum() != null) {
				hsimg.put(u.getSeqNum(), u);
			}
		}
		int zcount = 4;// 字段数目
		boolean ischange = true;
		if (getCbJF().isSelected()) {
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
			lines = FileReading.readExcel(txtFile, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(txtFile, 0, null);
		}

		List list = new ArrayList();
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
			TempDzscEmsBomBill obj = new TempDzscEmsBomBill();
			DzscEmsBomBill bom = new DzscEmsBomBill();
			String err = "";
			for (int j = 0; j <= zcount; j++) {
				String value = getFileColumnValue(strs, j);
				if (j == 0) {// 成品序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   成品序号不合法/";
						continue;
					}
					if (hsexg.get(Double.valueOf(value).intValue()) != null) {
						DzscEmsExgBill emsexg = (DzscEmsExgBill) hsexg
								.get(Double.valueOf(value).intValue());
						bom.setDzscEmsExgBill(emsexg);
					} else {
						err = err + "[" + Double.valueOf(value).intValue()
								+ "]   成品序号不存在/";
					}
				} else if (j == 1) {// 料件序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   料件序号不合法/";
						continue;
					}
					if (hsimg.get(Double.valueOf(value).intValue()) != null) {
						DzscEmsImgBill emsimg = (DzscEmsImgBill) hsimg
								.get(Double.valueOf(value).intValue());
						bom.setComplex(emsimg.getComplex());
						bom.setName(emsimg.getName());
						bom.setSpec(emsimg.getSpec());
						bom.setUnit(emsimg.getUnit());
						bom.setCountry(emsimg.getCountry());
						bom.setImgSeqNum(emsimg.getSeqNum());
						bom.setPrice(emsimg.getPrice() == null ? 0.0 : emsimg
								.getPrice());
					} else {
						err = err + "[" + Double.valueOf(value).intValue()
								+ "]   料件序号不存在/";
					}
				} else if (j == 2) {// 单耗
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单耗不合法/";
						continue;
					}
					bom.setUnitWare(Double.valueOf(value));
				} else if (j == 3) {// 损耗
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   损耗不合法/";
						continue;
					}
					bom.setWare(Double.valueOf(value));
				} else if (j == 4) {// 非保税料件比例%
					// 非保税料件比例%
					bom.setNonMnlRatio(Double.valueOf(value));
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   非保税料件比例%不合法\n";
						continue;
					}

					if (Double.valueOf(value).doubleValue() > 99
							|| Double.valueOf(value).doubleValue() < 0) {
						err = err + "[" + value + "]   非保税料件比例范围大于等于0且小于等于99\n";
						continue;
					}
				}
			}
			bom.setUnitDosage((bom.getUnitWare() == null ? Double.valueOf(0)
					: bom.getUnitWare())
					/ (1 - ((bom.getWare() == null || Double.valueOf(100.0)
							.equals(bom.getWare())) ? Double.valueOf(0) : bom
							.getWare() / 100.0)));
			bom.setCompany(CommonVars.getCurrUser().getCompany());
			bom.setPrice(bom.getPrice() == null ? 0.0 : bom.getPrice());
			// bom.setModifyMark(ModifyMarkState.ADDED);
			obj.setBom(bom);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("保存文件");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List ls = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						ls = tableModelImg.getList();
						for (int i = 0; i < ls.size(); i++) {
							TempDzscEmsImgBill obj = (TempDzscEmsImgBill) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								JOptionPane.showMessageDialog(DgDzscInput.this,
										"有错误信息，不可保存！", "提示!", 2);
								return;
							}
						}
						dzscAction.saveDzscEmsImgFromImport(new Request(
								CommonVars.getCurrUser()), ls, cbIsOverwrite
								.isSelected());
						initTableImg(new Vector());
						isOk = true;
						JOptionPane.showMessageDialog(DgDzscInput.this,
								"保存完毕！", "提示!", 2);

					} else if (jTabbedPane.getSelectedIndex() == 1) {
						ls = tableModelExg.getList();
						for (int i = 0; i < ls.size(); i++) {
							TempDzscEmsExgBill obj = (TempDzscEmsExgBill) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								JOptionPane.showMessageDialog(DgDzscInput.this,
										"有错误信息，不可保存！", "提示!", 2);
								return;
							}
						}
						dzscAction.saveDzscEmsExgFromImport(new Request(
								CommonVars.getCurrUser()), ls, cbIsOverwrite
								.isSelected());
						initTableExg(new Vector());
						isOk = true;
						JOptionPane.showMessageDialog(DgDzscInput.this,
								"保存完毕！", "提示!", 2);
					} else {
						ls = tableModelBom.getList();
						for (int i = 0; i < ls.size(); i++) {
							TempDzscEmsBomBill obj = (TempDzscEmsBomBill) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								JOptionPane.showMessageDialog(DgDzscInput.this,
										"有错误信息，不可保存！", "提示!", 2);
								return;
							}
						}
						dzscAction.saveDzscEmsBomFromImport(new Request(
								CommonVars.getCurrUser()), ls, cbIsOverwrite
								.isSelected());
						initTableBom(new Vector());
						isOk = true;
						JOptionPane.showMessageDialog(DgDzscInput.this,
								"保存完毕！", "提示!", 2);
					}

				}
			});
		}
	
		return jButton1;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("退    出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDzscInput.this.dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("1.料件", null, getJPanel(), null);
			jTabbedPane.addTab("2.成品", null, getJPanel2(), null);
			jTabbedPane.addTab("3.单耗", null, getJPanel1(), null);
		}
		return jTabbedPane;
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
			jPanel1.add(getJScrollPane2(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getJTable2());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes jTable2
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable2() {
		if (jTable2 == null) {
			jTable2 = new JTable();
		}
		return jTable2;
	}

	public DzscEmsPorHead getHead() {
		return head;
	}

	public void setHead(DzscEmsPorHead head) {
		this.head = head;
	}

	// private boolean isMaterielManageType() {
	// if (dzscManageType == null) {
	// DzscEmsPorWjHead wjHead = dzscAction
	// .findExingDzscEmsPorWjHeadByEmsNo(new Request(CommonVars
	// .getCurrUser()), head.getCorrEmsNo());
	// if (wjHead != null) {
	// dzscManageType = wjHead.getDzscManageType();
	// }
	// if (dzscManageType == null) {
	// dzscManageType = DzscCommon.getInstance().getDzscManageType();
	// }
	// }
	// if (dzscManageType == null || dzscManageType == DzscManageType.MATERIAL)
	// {
	// return true;
	// } else {
	// return false;
	// }
	// }

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("名称规格是否连在一起，并且以\"/\"区分");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbJF() {
		if (cbJF == null) {
			cbJF = new JCheckBox();
			cbJF.setText("是否繁转简");
		}
		return cbJF;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOverwrite() {
		if (cbIsOverwrite == null) {
			cbIsOverwrite = new JCheckBox();
			cbIsOverwrite.setText("是否覆盖导入");
		}
		return cbIsOverwrite;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
