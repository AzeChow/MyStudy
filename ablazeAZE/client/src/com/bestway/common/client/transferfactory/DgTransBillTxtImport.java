package com.bestway.common.client.transferfactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.common.transferfactory.entity.CustomsEnvelopBill;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

public class DgTransBillTxtImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnOpenFile = null;

	private JButton btnSave = null;

	private JButton btnClose = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	// private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;

	private File txtFile = null;

	private JRadioButton jRadioButton = null;

	private Hashtable gbHash = null; // @jve:decl-index=0:

	// private Hashtable headHash = null;

	private CasAction casAction = null;

	private JButton btnParamSet = null;

	private Hashtable hs = null;

	private BillTemp bill = null;

	private CustomBaseAction customBaseAction = null;

	// 客户供应商
	private HashMap scmcocName = new HashMap(); // @jve:decl-index=0:

	private HashMap scmcocCode = new HashMap(); // @jve:decl-index=0:

	// 仓库名称
	private HashMap countryName = new HashMap(); // @jve:decl-index=0:

	private HashMap countryCode = new HashMap(); // @jve:decl-index=0:

	// 产销国
	private HashMap wareSetName = new HashMap(); // @jve:decl-index=0:

	private HashMap wareSetCode = new HashMap(); // @jve:decl-index=0:

	// 币制
	private HashMap currName = new HashMap(); // @jve:decl-index=0:

	private HashMap currCode = new HashMap(); // @jve:decl-index=0:

	private JButton btnColumnSet = null;

	/**
	 * This is the default constructor
	 */
	public DgTransBillTxtImport() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.TRANSFERFACTORY_IEBILL, this
						.getDefaultBomTableColumnList());
		initTableDetail(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(761, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("转厂单据导入");
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
			jContentPane.add(getJPanel1(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnColumnSet());
			jToolBar.add(getBtnParamSet());
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnClose());
			jToolBar.add(getJRadioButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnOpenFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("3.打开");
			btnOpenFile.setPreferredSize(new Dimension(60, 30));
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (hs == null) {
						JOptionPane.showMessageDialog(
								DgTransBillTxtImport.this, "参数没有设置!", "提示", 2);
						return;
					}
					// headHash = new Hashtable();
					txtFile = getFile();
					if (txtFile == null) {
						return;
					} else if (!txtFile.exists()) {
						JOptionPane
								.showMessageDialog(DgTransBillTxtImport.this,
										"你选择的文件不存在", "提示", 0);
						return;
					}
					new ImportFileDataRunnable().execute();
				}
			});
		}
		return btnOpenFile;
	}

	class ImportFileDataRunnable extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			// List detailList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgTransBillTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				initConditionList();
				list = parseTxtFile();
				// detailList = (List) list.get(1);
				CommonProgress.closeProgressDialog();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				initTableDetail(list);
			}
			return list;

		}
	}

	//
	// private void infTojHsTable() {
	// if (gbHash == null) {
	// gbHash = new Hashtable();
	// List list = CustomBaseList.getInstance().getGbtobigs();
	// for (int i = 0; i < list.size(); i++) {
	// Gbtobig gb = (Gbtobig) list.get(i);
	// gbHash.put(gb.getBigname(), gb.getName());
	// }
	// }
	// }

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	// private String changeStr(String s) { // 繁转简
	// String yy = "";
	// char[] xxx = s.toCharArray();
	// for (int i = 0; i < xxx.length; i++) {
	// String z = String.valueOf(xxx[i]);
	// if (String.valueOf(xxx[i]).getBytes().length == 2) {
	// if (gbHash.get(String.valueOf(xxx[i])) != null) {
	// z = (String) gbHash.get(String.valueOf(xxx[i]));
	// }
	// }
	// yy = yy + z;
	// }
	// return yy;
	// }

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
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

	/**
	 * 单据导入栏位实体设定
	 * 
	 * @author sxk 2010/11/16
	 * @return
	 */
	private List parseTxtFile() {
		// 判断参数设置中进出货设置
		boolean isImpExpGoods = true;
		if ("出货".equals(hs.get(5))) {
			isImpExpGoods = false;
		}
		// 缓存所有关封管理中的关封
		List<CustomsEnvelopBill> ceblist = casAction.findCustomsEnvelopBill(
				new Request(CommonVars.getCurrUser()), isImpExpGoods);
		Map<String, CustomsEnvelopBill> cebmap = new HashMap<String, CustomsEnvelopBill>();
		for (CustomsEnvelopBill t : ceblist) {
			if (null == cebmap.get(t.getCustomsEnvelopBillNo())) {
				cebmap.put(t.getCustomsEnvelopBillNo(), t);
			}
		}
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.TRANSFERFACTORY_IEBILL);
		// List allList = new ArrayList();
		int zcount = lsIndex.size();// 字段数目

		// 获取是否繁转简
		boolean ischange = true;
		if (getJRadioButton().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		// 获取生效日期
		String dateFormatStr = (hs == null ? "yyyy-MM-dd" : (String) hs.get(6));

		String suffix = getSuffix(txtFile);
		// 第一行是否为标题
		boolean isTitle = Boolean.parseBoolean(hs.get("isTitle").toString());
		int beginRow = 1;
		if (isTitle) {
			beginRow = 2;
		}
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//			
			lines = FileReading.readExcel(txtFile, beginRow, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(txtFile, beginRow, null);
		}
		// ArrayList list = new ArrayList(); // 表头
		ArrayList listDetail = new ArrayList();// 表体,表头
		String err = ""; // 错误提示
		for (int i = 0; i < lines.size(); i++) {
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}

			if (ischange) {// 如果繁转简
				strs = changStrs(strs);
			}
			// BillTemp objHead = new BillTemp(); // 表头临时字段
			BillTemp objItem = new BillTemp(); // 表体临时字段
			// 遍历第行内容
			for (int j = 0; j < zcount; j++) {
				// value为每得每例值
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("bill1".equals(columnField)) {// 1:单据号码
					if (value != null || "".equals(value)) {
						List mulpList = casAction
								.checkMulpTransFactoryBillFactoryBillNo(
										new Request(CommonVars.getCurrUser()),
										value);
						if (mulpList != null && mulpList.size() > 0) {
							err = err + " 单据号码 [" + value + "]已存在 /";
						}
						// objHead.setBill1(value);
						objItem.setBill1(value);
					} else {
						err = err + " 单据号不能为空/";
					}
				} else if ("bill2".equals(columnField)) {// 2:客户供应商
					if (value != null) {
						ScmCoc scmCoc = null;
						if (hs.get(1) != null && hs.get(1).equals("代码")) {
							scmCoc = (ScmCoc) scmcocCode.get(value.trim());
						} else {
							// System.out.println("==value==" + value.trim()
							// + "==" + scmcocName.get(value.trim()));
							scmCoc = (ScmCoc) scmcocName.get(value.trim());
						}
						if (scmCoc == null) {
							err = err + "客户供应商[" + value + "]在系统中不存在/";
						}

						// objHead.setBill2(value);
						objItem.setBill2(value);
						objItem.setScmcoc(scmCoc);
					} else {
						err = err + " 客户供应商不能为空/";
					}
				}

				else if ("bill3".equals(columnField)) {// 3:生效日期
					if (value == null || "".equals(value)) {
						err = err + " 生效日期不能为空/";
						continue;
					}
					SimpleDateFormat bartDateFormat = new SimpleDateFormat(
							dateFormatStr);
					try {
						bartDateFormat.parse(value);
					} catch (ParseException e) {
						err = err + "[" + value + "]日期格式不合法/";
						continue;
					}
					// objHead.setBill3(value);
					objItem.setBill3(value);
				}

				else if ("bill4".equals(columnField)) {// 4:仓库名称
					if (value != null) {
						WareSet wareSet = null;
						if (hs.get(2) != null && hs.get(2).equals("代码")) {
							wareSet = (WareSet) wareSetCode.get(value.trim());
						} else {
							wareSet = (WareSet) wareSetName.get(value.trim());
						}
						if (wareSet == null) {
							err = err + "仓库[" + value + "]在系统中不存在/";
						}
						// objHead.setWareSet(wareSet);
						// objHead.setBill4(value);
						objItem.setBill4(value);
						objItem.setWareSet(wareSet);
					}
				}

				else if ("bill5".equals(columnField)) {// 5:关封号
					if (value != null && !"".equals(value)) {
						if (cebmap.get(value) == null) {
							err = err + "关封号 [" + value + "]在系统中不存在 /";
						}
						// objHead.setBill5(value);
						objItem.setBill5(value);

					} else {
						err = err + "关封号[" + value + "]不可为空/";
					}
				} else if ("bill6".equals(columnField)) {// 6:备注
					// objHead.setBill6(value);
					objItem.setBill6(value);
				}

				// --------------- 表体----------------
				else if ("bill7".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "手册号[" + value + "]不可为空/";
						continue;
					}
					if (objItem.getBill15() != null) {
						List listCheck = this.casAction
								.checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
										objItem.getBill15(), value, null);
						if (listCheck == null || listCheck.size() <= 0) {
							err = err + "手册号[" + value + "]在关封中不存在 /";
						}
					}
					objItem.setBill7(value);// 7：手册号
				}

				else if ("bill8".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "备案序号[" + value + "]不可为空/";
						continue;
					}
					try {
						objItem.setBill8(value);// 8：备案序号
						Integer seqNum = Integer.valueOf(value);
						if (objItem.getBill15() != null
								&& objItem.getBill17() != null) {
							List listCheck = this.casAction
									.checkCustomsEnvelopCommodityInfoIsExistsEmsNoOrSeqNum(
											objItem.getBill15(), objItem
													.getBill17(), seqNum);
							System.out.println(" 1=" + objItem.getBill15()
									+ " 2=" + objItem.getBill17() + " 3="
									+ seqNum + " 4=" + listCheck.size());
							if (listCheck == null || listCheck.size() <= 0) {
								err = err + "备案序号[" + value + "]在关封中不存在 /";
								continue;
							}
						}
					} catch (Exception e) {
						err = err + "[" + value + "]备案序号不合法/";
						continue;
					}

				}

				// else if ("bill9".equals(columnField)) { // 9：商品编码
				// objItem.setBill9(value);
				// }
				//
				// else if ("bill10".equals(columnField)) { // 10：品名
				// objItem.setBill10(value);
				// }
				//
				// else if ("bill11".equals(columnField)) { // 11：规格型号
				// objItem.setBill11(value);
				// }
				//
				// else if ("bill12".equals(columnField)) { // 12：单位
				// objItem.setBill12(value);
				// }

				// else if ("bill13".equals(columnField)) { // 13：关封数量
				// objItem.setBill13(value);
				// }

				else if ("bill14".equals(columnField)) { // 14：转厂数量
					if (value != null && !"".equals(value)) {
						objItem.setBill14(value);
					} else {
						err = err + "转厂数量[" + value + "]不可为空/";
					}
					objItem.setBill14(value);
				}

				else if ("bill15".equals(columnField)) { // 15：单价
					objItem.setBill15(value);
				}

				else if ("bill16".equals(columnField)) { // 16：总价
					if (value != null && !"".equals(value)) {
						objItem.setBill16(value);
					} else {
						err = err + "总价[" + value + "]不可为空/";
					}
				}

				else if ("bill17".equals(columnField)) {// 17：币值
					if (value != null && !"".equals(value)) {
						Curr curr = null;
						if (hs.get(4) != null && hs.get(4).equals("代码")) {
							curr = (Curr) currCode.get(value.trim());
						} else {
							curr = (Curr) currName.get(value.trim());
						}
						if (curr == null) {
							err = err + "币制[" + value + "]在系统中不存在/";
						}
						objItem.setCurr(curr);
						objItem.setBill17(value);
					} else {
						err = err + " 币制[" + value + "]不可为空/";
					}

				}

				else if ("bill18".equals(columnField)) { // 18：毛重
					if (value != null && !"".equals(value)) {
						objItem.setBill18(value);
					} else {
						err = err + "毛重[" + value + "]不可为空!";
					}
				}

				else if ("bill19".equals(columnField)) { // 19：净重
					if (value != null && !"".equals(value)) {
						objItem.setBill19(value);
					} else {
						err = err + "净重[" + value + "]不可为空/";
					}
				}

				else if ("bill20".equals(columnField)) { // 20：产销国
					if (value != null) {
						Country country = null;
						if (hs.get(3) != null && hs.get(3).equals("代码")) {
							country = (Country) countryCode.get(value.trim());
						} else {
							country = (Country) countryName.get(value.trim());
						}
						if (country == null) {
							err = err + "产销国[" + value + "] 在系统中不存在/";
						}
						objItem.setCountry(country);// 
						objItem.setBill20(value);
					}
				}

				else if ("bill21".equals(columnField)) { // 21：来源单据号
					objItem.setBill21(value);
				}

				else if ("bill22".equals(columnField)) { // 22：体积
					objItem.setBill22(value);
				}

				else if ("bill23".equals(columnField)) { // 23：版本号
					objItem.setBill23(value);
				}

				else if ("bill24".equals(columnField)) { // 24：备注
					objItem.setBill24(value);
				}
			}
			objItem.setErrinfo(err);
			listDetail.add(objItem);// 表体
			// String key = (objHead.getBill1() == null ? "" :
			// objHead.getBill1())
			// + "/"
			// + (objHead.getBill2() == null ? "" : objHead.getBill2())
			// + "/"
			// + (objHead.getBill3() == null ? "" : objHead.getBill3())
			// + "/"
			// + (objHead.getBill4() == null ? "" : objHead.getBill4())
			// + "/"
			// + (objHead.getBill5() == null ? "" : objHead.getBill5())
			// + "/"
			// + (objHead.getBill6() == null ? "" : objHead.getBill6());
			// if (headHash.get(key) == null) {
			// headHash.put(key, key);
			// // list.add(objHead);
			// }
			// allList.add(listDetail);
			// allList.add(err);
		}
		// allList.add(list);

		return listDetail;
	}

	/**
	 * 为了减轻对服务器数据库的压力,尽量减少对数据库的操作.用缓存!
	 */
	private void initConditionList() {
		// 客户供应商
		scmcocName.clear();
		scmcocCode.clear();

		// 仓库
		wareSetName.clear();
		wareSetCode.clear();

		// 产销国
		countryName.clear();
		countryCode.clear();

		// 币制
		currName.clear();
		currCode.clear();

		// 客户供应商
		List ScmcocList = customBaseAction.findScmCoc(new Request(CommonVars
				.getCurrUser()), null, null);
		for (int i = 0; i < ScmcocList.size(); i++) {
			ScmCoc scmcoc = (ScmCoc) ScmcocList.get(i);
			scmcocName.put(scmcoc.getName(), scmcoc);
			scmcocCode.put(scmcoc.getCode(), scmcoc);
		}

		// 仓库
		List WareSetList = customBaseAction.findWareSet(new Request(CommonVars
				.getCurrUser()));
		for (int i = 0; i < WareSetList.size(); i++) {
			WareSet wareSet = (WareSet) WareSetList.get(i);
			wareSetName.put(wareSet.getName(), wareSet);
			wareSetCode.put(wareSet.getCode(), wareSet);
		}

		// 产销国
		List CountryName = customBaseAction.findCountry("", "");
		for (int i = 0; i < CountryName.size(); i++) {
			Country country = (Country) CountryName.get(i);
			countryName.put(country.getName(), country);
			countryCode.put(country.getCode(), country);
		}

		// 仓库
		List CurrList = customBaseAction.findCurr("", "");
		for (int i = 0; i < CurrList.size(); i++) {
			Curr curr = (Curr) CurrList.get(i);
			currName.put(curr.getName(), curr);
			currCode.put(curr.getCode(), curr);
		}
	}

	private List getHead(String[] strs, int zcount) {
		List ls = new ArrayList();
		ArrayList list = new ArrayList();

		return ls;
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("4.保存");
			btnSave.setPreferredSize(new Dimension(60, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// if (tableModel.getList() == null
					// || tableModel.getList().size() < 1) {
					// JOptionPane.showMessageDialog(
					// DgTransBillTxtImport.this, "保存数据为空!", "提示", 2);
					// return;
					// }
					List detailList = tableModelDetail.getList();
					String err = "";
					for (int i = 0; i < detailList.size(); i++) {
						BillTemp billTemp = (BillTemp) detailList.get(i);
						if (billTemp.getErrinfo() != null
								&& !"".equals(billTemp.getErrinfo())) {
							err += billTemp.getErrinfo();
						}
					}
					if (!"".equals(err)) {
						CommonProgress.closeProgressDialog();
						JOptionPane
								.showMessageDialog(DgTransBillTxtImport.this,
										"数据有错，不能保存", "提示", 2);
						return;
					}
					new SaveDataRunnable().start();
				}
			});
		}
		return btnSave;
	}

	class SaveDataRunnable extends Thread {
		public void run() {
			List list = null;
			List dlist = null;
			try {
				CommonProgress.showProgressDialog(DgTransBillTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				// list = tableModel.getList();
				dlist = tableModelDetail.getList();
				casAction.executeTransBillImport(new Request(CommonVars
						.getCurrUser()), list, dlist, hs);
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统读文件资料完毕！请稍后...");
				JOptionPane.showMessageDialog(DgTransBillTxtImport.this,
						"保存数据成功！", "提示", 2);
				initTableDetail(new Vector());
			} catch (Exception e) {
				e.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgTransBillTxtImport.this,
						"保存数据失败! ", "提示", 2);
			} finally {
				//CommonProgress.closeProgressDialog();
				// initTable(new Vector());
				

			}
		}
	}

	/**
	 * This method initializes btnClose
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("退出");
			btnClose.setPreferredSize(new Dimension(60, 30));
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransBillTxtImport.this.dispose();
				}
			});
		}
		return btnClose;
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
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
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
	 * 初始化单据头表 This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private void initTableDetail(final List list) {
		tableModelDetail = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.TRANSFERFACTORY_IEBILL);
					}
				});
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

	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("单据号码 ★", "bill1", 100));
		list.add(new JTableListColumn("客户供应商★", "bill2", 120));
		list.add(new JTableListColumn("生效日期★", "bill3", 140));
		list.add(new JTableListColumn("仓库名称", "bill4", 100));
		list.add(new JTableListColumn("关封号★", "bill5", 100));
		list.add(new JTableListColumn("备注", "bill6", 100));
		// 单据体
		list.add(new JTableListColumn("手册号★", "bill7", 100));
		list.add(new JTableListColumn("备案序号★", "bill8", 100));
		// list.add(new JTableListColumn("商品编码", "bill9", 120));
		// list.add(new JTableListColumn("名称", "bill10", 140));
		// list.add(new JTableListColumn("规格型号", "bill11", 100));
		// list.add(new JTableListColumn("单位", "bill12", 100));
		// list.add(new JTableListColumn("关封数量", "bill13", 100));
		list.add(new JTableListColumn("转厂数量★", "bill14", 100));
		list.add(new JTableListColumn("单价", "bill15", 100));
		list.add(new JTableListColumn("总价★", "bill16", 100));
		list.add(new JTableListColumn("币制★", "bill17", 100));
		list.add(new JTableListColumn("毛重★", "bill18", 100));
		list.add(new JTableListColumn("净重★", "bill19", 100));
		list.add(new JTableListColumn("产销国", "bill20", 100));
		list.add(new JTableListColumn("来源单据号", "bill21", 100));
		list.add(new JTableListColumn("体积", "bill22", 100));
		list.add(new JTableListColumn("版本号", "bill23", 100));
		list.add(new JTableListColumn("备注", "bill24", 100));
		return list;
	}

	// private void initTableDetail(final List list) {
	// tableModelDetail = new JTableListModel(jTable1, list,
	// new JTableListModelAdapter() {
	// public List<JTableListColumn> InitColumns() {
	// List<JTableListColumn> list = new Vector<JTableListColumn>();
	// list.add(addColumn("1.手册号", "bill1", 100));
	// list.add(addColumn("2.备案序号", "bill2", 100));
	// list.add(addColumn("3.商品编码", "bill3", 120));
	// list.add(addColumn("4.品名", "bill4", 140));
	// list.add(addColumn("5.规格型号", "bill5", 100));
	// list.add(addColumn("6.单位", "bill6", 100));
	// list.add(addColumn("7.关封数量", "bill7", 100));
	// list.add(addColumn("8.转厂数量", "bill8", 100));
	// list.add(addColumn("9.单价", "bill9", 100));
	// list.add(addColumn("10.总价", "bill10", 100));
	// list.add(addColumn("11.币值", "bill11", 100));
	// list.add(addColumn("12.毛重", "bill12", 100));
	// list.add(addColumn("13.净重", "bill13", 100));
	// list.add(addColumn("14.产销国", "bill14", 100));
	// list.add(addColumn("15.来源单据号", "bill15", 100));
	// list.add(addColumn("16.体积", "bill16", 100));
	// list.add(addColumn("17.版本号", "bill17", 100));
	// list.add(addColumn("18.备注", "bill18", 100));
	//						
	// return list;
	// }
	// });
	// }

	// 调出文件选择框
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
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("【开始导入】是否繁转简   [文本格式见下方表格表头排列顺序]");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes btnParamSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnParamSet() {
		if (btnParamSet == null) {
			btnParamSet = new JButton();
			btnParamSet.setText("2.参数设置");
			btnParamSet.setPreferredSize(new Dimension(72, 30));
			btnParamSet.setForeground(java.awt.Color.blue);
			btnParamSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgTransBillTxtImportSet dg = new DgTransBillTxtImportSet();
					dg.setHs(hs);
					dg.setVisible(true);
					if (dg.isOk()) {
						hs = dg.hs;
					}
				}
			});
		}
		return btnParamSet;
	}

	/**
	 * This method initializes btnColumnSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumnSet() {
		if (btnColumnSet == null) {
			btnColumnSet = new JButton();
			btnColumnSet.setText("1.栏位设定");
			btnColumnSet.setPreferredSize(new Dimension(72, 30));
			btnColumnSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.TRANSFERFACTORY_IEBILL);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTableDetail(new ArrayList());
					}
				}
			});
		}
		return btnColumnSet;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
