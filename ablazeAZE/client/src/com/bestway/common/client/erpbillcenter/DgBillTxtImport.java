package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.entity.TempBillInputErrMassageShow;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

/**
 * 
 * 刘民添加部分注释 修改时间: 2008-10-25
 * 
 * @author ? // change the template for this generated type comment go to Window
 *         - Preferences - Java - Code Style - Code Templates 单据导入
 */
public class DgBillTxtImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnOpen = null;

	private JButton btnImbark = null;

	private JButton btnExit = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;
	/** 文件导入 */
	private File txtFile = null;

	private JRadioButton rbIsBig5ConvertToGB = null;

	private Hashtable gbHash = null; // @jve:decl-index=0:

	private Hashtable headHash = null;

	private CasAction casAction = null;

	private JButton btnParameterSet = null;

	private boolean ishalfProdect = false;

	public boolean isIshalfProdect() {
		return ishalfProdect;
	}

	public void setIshalfProdect(boolean ishalfProdect) {
		this.ishalfProdect = ishalfProdect;
	}

	// 保存参数设置
	private Hashtable paramerMap = null;
	/** 单据临时表 */
	private BillTemp bill = null;
	/** 单据类别 */
	// private BillType ty = null;
	private JLabel jLabel = null;

	// private String materielType = null;
	/** 一对多 */
	private boolean isOneToMany = false;

	// private HashMap<String, Boolean> parameter = new HashMap();

	private static final long serialVersionUID = CommonUtils
			.getSerialVersionUID();

	/**
	 * This is the default constructor
	 */
	public DgBillTxtImport() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(742, 511);
		this.setContentPane(getJContentPane());
		this.setTitle("单据导入");
		initTable(new Vector());
		initTableDetail(new Vector());
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setVgap(1);
			fl.setHgap(1);
			jLabel = new JLabel();
			jLabel.setText("注意在备注栏后多加一行空列");
			jLabel.setForeground(Color.BLUE);
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setPreferredSize(new Dimension(352, 34));
			jToolBar.setLayout(fl);
			jToolBar.add(getBtnParameterSet());
			jToolBar.add(getBtnOpen());
			jToolBar.add(getBtnImbark());
			jToolBar.add(getBtnExit());
			jToolBar.add(getRbIsBig5ConvertToGB());
			jToolBar.add(jLabel);
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnOpen
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpen() {
		if (btnOpen == null) {
			btnOpen = new JButton();
			btnOpen.setText("2.打开");
			btnOpen.setPreferredSize(new Dimension(85, 30));
			btnOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (paramerMap == null) {
						JOptionPane.showMessageDialog(DgBillTxtImport.this,
								"参数没有设置!", "提示", 2);
						return;
					} else if (paramerMap.get(5) == null) {
						JOptionPane.showMessageDialog(DgBillTxtImport.this,
								"物料类别没有设置!", "提示", 2);
						return;
					}
					headHash = new Hashtable();
					txtFile = getFile();
					if (txtFile == null) {
						return;
					} else if (!txtFile.exists()) {
						JOptionPane.showMessageDialog(DgBillTxtImport.this,
								"你选择的文件不存在", "提示", 0);
						return;
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return btnOpen;
	}

	/**
	 * 文件导入多线程
	 * 
	 * @author ower
	 * 
	 */

	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			List headList = new ArrayList();
			List detailList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgBillTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();// 应该在这里吧
				if (list == null || list.size() == 0) {
					JOptionPane.showMessageDialog(DgBillTxtImport.this,
							"您导入的文档没有数据,请检查!", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				headList = (List) list.get(0);
				detailList = (List) list.get(1);
				List<TempBillInputErrMassageShow> errorList = (List<TempBillInputErrMassageShow>) list
						.get(2);
				if (errorList != null && errorList.size() != 0) {
					CommonProgress.closeProgressDialog();
					DgInvalidationBillTxtDate dgInvalidationFileData = new DgInvalidationBillTxtDate();
					dgInvalidationFileData.setDataSource(errorList);
					dgInvalidationFileData.setVisible(true);
					headList = new Vector();
					detailList = new Vector();
				}
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				throw new RuntimeException(e);
			} finally {
				CommonProgress.closeProgressDialog();
				initTable(headList);
				initTableDetail(detailList);
			}
		}
	}

	/**
	 * 获取文件列值
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

	/*
	 * private String changeStr(String s) { // 繁转简 String yy = ""; char[] xxx =
	 * s.toCharArray(); for (int i = 0; i < xxx.length; i++) { String z =
	 * String.valueOf(xxx[i]); if (String.valueOf(xxx[i]).getBytes().length ==
	 * 2) { if (gbHash.get(String.valueOf(xxx[i])) != null) { z = (String)
	 * gbHash.get(String.valueOf(xxx[i])); } } yy = yy + z; } return yy; }
	 */
	/**
	 * 获取文件后缀
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
	 * 繁体转换成简体
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
	 * 解析文件
	 * 
	 * @return
	 */

	private List parseTxtFile() {

		/**
		 * 单据类型名称 与 单据类型代码 关联
		 */
		Hashtable mapBillType = this.casAction
				.findCodeAndFactryNameFromBillType(new Request(CommonVars
						.getCurrUser()));

		// 物料类型名称
		String wlTypeName = (String) paramerMap.get(5);
		// System.out.println("(String) hs.get(5)=" + (String)
		// paramerMap.get(5));
		Boolean isHalf = false;
		if (("半成品".equals(wlTypeName)) || "残次品(半成品)".equals(wlTypeName)) {
			isHalf = true;
		}

		/**
		 * 料号 与 报关资料(料号、报关商品编码、报关名称、报关规格、报关单位、折算比例、工厂单价、净重)关联
		 * 
		 * （半成品特殊：料号 与 Mateiel(料号、报关商品编码、报关规格、报关单位、折算比例、工厂单价、报关名称、净重) 关联）
		 */
		// Map<String, Object[]> mapPtNoHs = new HashMap<String, Object[]>();
		//		
		// if(isHalf){
		// //来源于报关常用工厂物料取得料号、报关商品编码、报关规格、报关单位、折算比例、工厂单价、报关名称、净重
		// mapPtNoHs = this.casAction.fintPtNoAndCodeInMateriel(
		// new Request(CommonVars.getCurrUser()),
		// wlTypeName);
		// }else{
		// //来源于工厂与实际对应表取得料号、报关商品编码、报关规格、报关单位、折算比例、工厂单价、报关名称、净重
		// mapPtNoHs= this.casAction.findptNoandCodeInStatCusNameRelation(
		// new Request(CommonVars.getCurrUser()),
		// wlTypeName);
		// }
		/**
		 * 对应关系料号+报关名称+报关规格+报关单位 + (手册号？)与本身 关联 （半成品特殊：资料来自报关常用工厂物料）
		 */

		Map<String, String> mapHsHs = new HashMap<String, String>();
		Map<String, String> MapHsHsEmsNo = new HashMap<String, String>();
		if (isHalf) {
			// 来源于工厂物料取得料号、名称、规格
			mapHsHs = this.casAction.findDistinctMaterielByPtNoNameSpecUnit(
					new Request(CommonVars.getCurrUser()), wlTypeName);
		} else {
			// 来源于工厂与实际对应表取得料号、名称、规格、单位
			mapHsHs = this.casAction.findDistinctFFCByPtNoNameSpecUnit(
					new Request(CommonVars.getCurrUser()), wlTypeName, false);
			// 来源于工厂与实际对应表取得料号、名称、规格、单位、手册号
			MapHsHsEmsNo = this.casAction.findDistinctFFCByPtNoNameSpecUnit(
					new Request(CommonVars.getCurrUser()), wlTypeName, true);

		}

		// Iterator iterator_2 = MapHsHsEmsNo.keySet().iterator();
		// while (iterator_2.hasNext()) {
		// String key =(String) iterator_2.next();
		//         
		// System.out.println("tmp is :"+key);
		// }
		/**
		 * 料号 与 报关常用工厂物料 关联
		 */
		Map<String, Materiel> mapPtNoM = this.casAction.getMaterialByType(
				new Request(CommonVars.getCurrUser()), wlTypeName);

		/*
		 * Failed to connect to remote VM. Connection refused. Connection
		 * refused: connect
		 * 
		 * 客户/供应商 与 其代码 关联
		 */
		Map<String, String> mapScm = this.casAction.getScmCocForImport(
				new Request(CommonVars.getCurrUser()), (String) paramerMap
						.get(1));// 客户MAP

		/**
		 * 单据头 表名
		 */
		String tablename = getMasterTableName(wlTypeName);

		/**
		 * 单据头，用于过滤
		 */
		List headlist = this.casAction.getCasHeadForImport(new Request(
				CommonVars.getCurrUser()), tablename);

		/**
		 * 参数设置
		 */
		CasBillOption casBillOption = this.casAction
				.findCasBillOption(new Request(CommonVars.getCurrUser()));

		/**
		 * 仓库list,用于检查仓库
		 */
		List<WareSet> warSets = this.casAction.findWareSet(new Request(
				CommonVars.getCurrUser()));

		Map<String, String> ckHsByCode = new HashMap<String, String>();
		Map<String, String> ckHsByName = new HashMap<String, String>();

		for (int i = 0; i < warSets.size(); i++) {
			WareSet obj = (WareSet) warSets.get(i);
			if (obj != null && obj.getCode() != null
					&& !obj.getCode().equals("")) {
				ckHsByCode.put(obj.getCode(), obj.getId());
			}
			if (obj != null && obj.getName() != null
					&& !obj.getName().equals("")) {
				ckHsByName.put(obj.getName(), obj.getId());
			}
		}

		List allList = new ArrayList();
		// 是否导入对应报关单号
		boolean isincustoms = Boolean.parseBoolean(paramerMap.get(
				"isInputCustoms").toString());

		// 第一行是否为标题
		boolean isTitle = Boolean.parseBoolean(paramerMap.get("isTitle")
				.toString());
		int beginRow = 1;
		if (isTitle) {
			beginRow = 2;
		}

		// 如果生效日期为空，是否以当天为生效日期，如果否的话，提示出错。
		boolean isCurrentDate = Boolean.parseBoolean(paramerMap.get(
				"isCurrentDate").toString());

		// 获取生效日期
		String dateFormatStr = (paramerMap == null ? "yyyy-MM-dd"
				: (String) paramerMap.get(3));
		String curDate = "";
		if (isCurrentDate) {
			Calendar cal = Calendar.getInstance();
			curDate = "" + cal.get(Calendar.YEAR) + "-";
			if ((cal.get(Calendar.MONTH) + 1) < 10) {
				curDate += "0" + (cal.get(Calendar.MONTH) + 1) + "-";
			} else {
				curDate += "" + (cal.get(Calendar.MONTH) + 1) + "-";
			}
			if ((cal.get(Calendar.DATE)) < 10) {
				curDate += "0" + cal.get(Calendar.DATE);
			} else {
				curDate += "" + cal.get(Calendar.DATE);
			}
		}
		int zcount = 26;// 字段数目
		if (isincustoms) {// 如果导入对应报关单
			zcount = 27;
		}

		// 是否繁转简
		boolean ischange = true;
		if (getRbIsBig5ConvertToGB().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();// 导入的内容

		if (suffix.equals("xls")) {
			lines = FileReading.readExcel(txtFile, beginRow, null);// 导入xls
		} else {
			lines = FileReading.readTxt(txtFile, beginRow, null);// 导入txt
		}

		Random random = new Random();// 单据唯一号
		ArrayList list = new ArrayList(); // 表头
		ArrayList listDetail = new ArrayList();// 表体
		ArrayList repeatHeadList = new ArrayList(); // 错误表头list
		List<TempBillInputErrMassageShow> errorList = new ArrayList<TempBillInputErrMassageShow>();
		String err = "";
		for (int i = 0; i < lines.size(); i++) {
			// Materiel mt = null;
			// Materiel mts = null;
			List line = lines.get(i);// 每行内容
			String[] strs = new String[line.size()];// 每行内容组成的数组

			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}

			if (ischange) {// 如果繁转简
				strs = changStrs(strs);
			}

			// Object[] objs = null;

			BillTemp billTemp = new BillTemp();
			String billCode = "";

			// 遍历第行内容
			for (int j = 0; j < zcount; j++) {
				// value为每得每例值
				String value = getFileColumnValue(strs, j);

				if (j == 0) {// 单据类型代码
					if (mapBillType.get(value) == null) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行,第" + (j + 1)
								+ "列 单据类型代码没有设置或没有与之对应的工厂单据类型!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						error.setPtNo(value);
						errorList.add(error);
						continue;
					} else {
						billCode = (String) mapBillType.get(value);
					}
					billTemp.setBill1(value);
				}

				else if (j == 1) {// 单据号码
					billTemp.setBill2(value);
				}

				else if (j == 2) {// 客户供应商
					billTemp.setBill3(value);
				}

				else if (j == 3) {// 生效日期格式不合法
					if (value == null || value.equals("")) {
						if (isCurrentDate) {
							value = curDate;
						} else {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行,第" + (j + 1)
									+ "列 生效日期为空!" + "";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							errorList.add(error);
							continue;
						}
					} else {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								dateFormatStr);
						try {
							bartDateFormat.parse(value);
						} catch (ParseException e) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行,第" + (j + 1)
									+ "列 生效日期= [" + dateFormatStr + "] 格式不合法!"
									+ "";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							errorList.add(error);
							continue;
						}
					}
					billTemp.setBill4(value);
				}

				else if (j == 4) {// 关封号wss2010.08.31添加
					billTemp.setEnvelopNo(value);
				}

				else if (j == 5) {// 备注
					billTemp.setBill5(value);
				}

				else if (j == 6) {// 备注栏后面的空列
					billTemp.setBill100(String.valueOf(random));// 存放单据唯一号
				}

				else if (j == 7) {// 料号哦,要检查料号在报关常用工厂物料中是否存在
					// ------------------------------------------------------------
					if (!(billCode.equals("2011") || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
							|| billCode.equals("1015") || billCode
							.equals("1016"))) {// 已收货未结转期初单,已结转未收货期初单

						// 如果不为以上期初单，则检查料号是否存在 点解？
						if (value == null || value.equals("")) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行,第" + (j + 1)
									+ "列 料号为空!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							error.setPtNo(value);
							errorList.add(error);
							continue;
						}
						// mt = mapPtNoM.get(value);//获取对应的报关常用工厂物料
						if (mapPtNoM.get(value) == null) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行,第" + (j + 1)
									+ "列 料号在报关常用物料中不存在!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							error.setPtNo(value);
							errorList.add(error);
							continue;
						}

					}
					// 料号 与 对应关系资料 关联 （半成品特殊：料号 与 报关常用工厂物料资料 关联）
					// objs = mapPtNoHs.get(value);
					billTemp.setBill6(value);// 料号

				}

				else if (j == 8) {// 净重
					if (value == null || "".equals(value)) {
						continue;
					}
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行,第" + (j + 1) + "列 净重不合法!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						error.setPtNo(value);
						errorList.add(error);
						continue;
					}
					billTemp.setBill7(value);

				}

				else if (j == 9) {// 毛重

					if (value == null || "".equals(value)) {
						continue;
					}
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行,第" + (j + 1) + "列 毛重不合法!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						error.setPtNo(value);
						errorList.add(error);
						continue;
					}
					billTemp.setBill8(value);

				}

				else if (j == 10) {// 数量
					if (value == null || "".equals(value)) {
						continue;
					}
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行,第" + (j + 1) + "列 数量不合法!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						error.setPtNo(value);
						errorList.add(error);
						continue;
					}
					billTemp.setBill9(value);

				}

				else if (j == 11) {// 工厂单价
					if (value == null || "".equals(value)) {
						// 如果没有导工厂单价量暂不计算，放在保存时进行计算
						continue;
						// if (objs == null) {
						// if (mt == null) {
						// billTemp.setBill10("0.0");
						// } else {
						// billTemp.setBill10(mt.getPtPrice() == null ? "0.0"
						// : mt.getPtPrice().toString());
						// }
						// } else {
						// Double dou = Double.valueOf(objs[6] == null ? "0.0"
						// : objs[6].toString());
						// billTemp.setBill10(dou.toString());// 工厂单价
						// }
					} else {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (j + 1) + "行 单价不合法!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							error.setPtNo(value);
							errorList.add(error);
							continue;
						}
						billTemp.setBill10(value);// 单价
					}
				}

				else if (j == 12) {// 版本号
					if (value == null || "".equals(value)) {
						continue;
					}
					try {
						if (Double.valueOf(value) == 0)
							value = "0";
						Integer.valueOf(value);
					} catch (Exception e) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行,第" + (j + 1) + "列 版本号不合法!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						error.setPtNo(value);
						errorList.add(error);
						continue;
					}
					billTemp.setBill11(value.trim());
				}

				else if (j == 13) {// 仓库
					String ck = (paramerMap == null ? "" : (String) paramerMap
							.get(2));
					String sck = value;
					if (sck != null && !sck.equals("")) {
						if (ck != null && ck.equals("名称")) {
							if (!ckHsByName.containsKey(sck)) {
								TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
								err = "警告:第" + (i + 1) + "行,第" + (j + 1)
										+ "列  仓库名称 = " + sck + " 在仓库基本表中不存在!";
								error.setErrorReason(err);
								error.setInvalidationRow(i + 1);
								error.setPtNo(value);
								errorList.add(error);
								continue;
							}
						} else {
							if (!ckHsByCode.containsKey(sck)) {
								TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
								err = "警告:第" + (i + 1) + "行,第" + (j + 1)
										+ "列  仓库代码  = " + sck + " 在仓库基本表中不存在!";
								error.setErrorReason(err);
								error.setInvalidationRow(i + 1);
								error.setPtNo(value);
								errorList.add(error);
								continue;
							}
						}
					}
					billTemp.setBill12(value);
				}

				else if (j == 14) {// 制单号
					if (value != null && (value.length() > 255)) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行" + (j + 1)
								+ "列 制单号不可以超过255位!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						error.setPtNo(value);
						errorList.add(error);
						continue;
					}
					billTemp.setBill13(value);
				}

				else if (j == 15) {// 折算报关数量
					if (value == null || "".equals(value)) {
						// 如果没有导折算报关数量暂不计算，放在保存时进行计算
						continue;
						// Double du = Double
						// .parseDouble(billTemp.getBill9() == null ? "0.0"
						// : billTemp.getBill9());
						// if (objs == null) {
						// billTemp.setBill14("0.0");
						// } else {
						// //objs[4]为折算比例
						// Double dou = Double
						// .parseDouble(objs[5] == null ? "0.0"
						// : objs[5].toString());
						// Double d = du * dou;
						// billTemp.setBill14(d.toString());// 折算报关数量
						// }
					} else {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行" + (j + 1)
									+ "列 折算报关数量不合法!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							error.setPtNo(value);
							errorList.add(error);
							continue;
						}
						billTemp.setBill14(value);
					}
				}

				else if (j == 16) {// 对应数量
					if (value == null || "".equals(value)) {
						billTemp.setBill15(null);// 对应数量
					} else {
						if (isincustoms) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
								err = "警告:第" + (i + 1) + "行" + (j + 1)
										+ "列 对应数量不合法!";
								error.setErrorReason(err);
								error.setInvalidationRow(i + 1);
								error.setPtNo(value);
								errorList.add(error);
								continue;
							}
							billTemp.setBill15(value);
						}
					}
				}

				else if (j == 17) {// 海关单价
					if (value == null || "".equals(value)) {
						// 如果没有导海关单价暂不计算，放在保存时进行计算
						continue;
						// if (objs == null) {
						// billTemp.setBill16("");
						// } else {
						// //objs[5]为折算比例
						// Double val = Double
						// .parseDouble(billTemp.getBill10() == null ? "0.0"
						// : billTemp.getBill10());
						// Double dp = Double
						// .parseDouble(objs[5] == null ? "0.0"
						// : objs[5].toString());
						// if (dp == 0.0) {
						// billTemp.setBill16(val == null ? null : val
						// .toString());// 海关单价
						// } else {
						// billTemp.setBill16(new Double(val /
						// dp).toString());// 海关单价
						// }
						// }
					} else {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行" + (j + 1)
									+ "列 海关单价不合法!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							error.setPtNo(value);
							errorList.add(error);
							continue;
						}
						billTemp.setBill16(value);// 海关单价
					}
				}

				// =======================以下为导入期初单用

				else if (j == 18) {// 报关名称
					// if (billCode.equals("2011")
					// || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
					// || billCode.equals("1015")
					// || billCode.equals("1016")) {// //已收货未结转期初单,已结转未收货期初单
					// System.out.println("wss hsName: " + value);
					billTemp.setBill18(value);
					// }
				}

				else if (j == 19) {// 报关规格
					// if (billCode.equals("2011")
					// || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
					// || billCode.equals("1015")
					// || billCode.equals("1016")) {// //已收货未结转期初单,已结转未收货期初单
					// System.out.println("wss hsSpec:" + value);
					billTemp.setBill19(value);
					// }
				}

				else if (j == 20) {// 报关单位
					// if (billCode.equals("2011")
					// || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
					// || billCode.equals("1015")
					// || billCode.equals("1016")) {// //已收货未结转期初单,已结转未收货期初单
					// System.out.println("wss hsUnit:" + value);
					billTemp.setBill20(value);
				}

				else if (j == 21) {// 手册号
					// System.out.println("wss emsNo:" + value);
					billTemp.setEmsNo(value);
				}

				else if (j == 22) {// 对应报关单号
					if (paramerMap.get("isInputCustoms") != null
							&& (Boolean.parseBoolean(paramerMap.get(
									"isInputCustoms").toString()))) {
						if (value == null || "".equals(value)) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第" + (i + 1) + "行" + (j + 1)
									+ "列 对应报关单号选项选择后没有导入数据!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							error.setPtNo(value);
							errorList.add(error);
							continue;
						} else {
							billTemp.setBill17(value);
						}
					}
				}

				else if (j == 23) { // 单据备注
					if (wlTypeName != null && wlTypeName.equals("残次品(料件)")) {
						value = "料件";
					} else if (wlTypeName != null
							&& wlTypeName.equals("残次品(成品)")) {
						value = "成品";
					} else if (wlTypeName != null
							&& wlTypeName.equals("残次品(半成品)")) {
						value = "半成品";
					}
					billTemp.setNote(value);
					if (billCode.equals("1005")
							&& (casBillOption.getIsNoticeBillNo() == null || casBillOption
									.getIsNoticeBillNo().booleanValue() == true)) {
						String hsKey = billTemp.getNote() == null ? ""
								: billTemp.getNote().trim();
						if (hsKey.equals("") || hsKey.length() != 8) {
							TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
							err = "警告:第"
									+ (i + 1)
									+ "行"
									+ (j + 1)
									+ "列 参数设置勾选了[国内购买检查备注发票号],而[单体备注]栏位未备注长度为8位的发票号码!";
							error.setErrorReason(err);
							error.setInvalidationRow(i + 1);
							errorList.add(error);
						}
					}

				}

				else if (j == 24) {// 24.送货单号
					// System.out.println("j = 24 value = " + value);
					billTemp.setTakeBillNo(value);
				}

				else if (j == 25) {// 24 订单号
					// System.out.println("j = 24 value = " + value);
					billTemp.setOrderNo(value);
				}
			}

			// -----------重新根据单位净重计算净重
			// for (int j = 8; j <= 8; j++) {
			// if (j == 8) {
			// 如果计算净重暂不计算，放在保存时进行计算
			// continue;
			// if (billTemp.getBill7() == null ||
			// "".equals(billTemp.getBill7())) {
			// Double du = Double
			// .parseDouble(billTemp.getBill9() == null ? "0.0"
			// : billTemp.getBill9());// 数量
			// if (objs == null) {
			// if (mt != null) {
			// billTemp
			// .setBill7(mt.getPtNetWeight() == null ? "0.0"
			// : mt.getPtNetWeight()
			// .toString());
			// } else {
			// billTemp.setBill7("0.0");
			// }
			// } else {
			// //objs[7]为净重
			// Double dou = Double
			// .parseDouble(objs[7] == null ? "0.0"
			// : objs[7].toString());// 单位净重
			// BigDecimal d = new BigDecimal(du * dou);
			// billTemp.setBill7(d
			// .setScale(3, BigDecimal.ROUND_HALF_UP)
			// .toString());
			// }
			// }
			// }
			// }

			// try {
			// Double vls = Double
			// .valueOf(billTemp.getBill16() == null ? "0.0"
			// : billTemp.getBill16().toString())
			// * Double.valueOf(billTemp.getBill14() == null ? "0.0"
			// : billTemp.getBill14().toString());
			// billTemp.setBillSum19(vls);// 总价
			// } catch (Exception e) {
			// billTemp.setBillSum19(0.0);// 总价
			// }

			// 只用报关名称来存在
			boolean isExistHsName = true;

			// 未填报关资料
			if (billTemp.getBill18() == null
					|| "".equals(billTemp.getBill18().trim())) {
				isExistHsName = false;
			}

			// 如果 有填写报关名称的
			if (isExistHsName) {// wss:2010.07.12修改
				String ptNo = billTemp.getBill6() == null ? "" : billTemp
						.getBill6().toString().trim();// 料号
				String hsName = billTemp.getBill18() == null ? "" : billTemp
						.getBill18().toString().trim();// 报关名称
				String hsSpec = billTemp.getBill19() == null ? "" : billTemp
						.getBill19().toString().trim();// 报关规格
				String hsUnitName = billTemp.getBill20() == null ? ""
						: billTemp.getBill20().toString().trim();// 报关单位
				String emsNo = billTemp.getEmsNo() == null ? "" : billTemp
						.getEmsNo().toString().trim();// 手册号
				// key=料号+ "/" + 报关名称+"/"+报关规格+"/"+报关单位
				String key1 = "";
				// 当手册号为空时
				if (emsNo == null || "".equals(emsNo)) {
					String str = "";
					if (isHalf) {
						key1 = ptNo + "/" + hsName +"/"+hsSpec;
						str = "报关常用工厂物料";
					} else {
						key1 = ptNo + "/" + hsName +"/"+hsSpec+ "/" + hsUnitName;
						str = "对应关系";
					}

					if (mapHsHs.get(key1) == null) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行【 料号=" + ptNo + ",报关名称="
								+ hsName + ",报关规格=" + hsSpec + ",报关单位="
								+ hsUnitName + "】在" + str + "中不存在!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						errorList.add(error);

					}
				} else {
					// 当不为空时
					key1 = ptNo + "/" + hsName +"/"+hsSpec+ "/" + hsUnitName+"/" + emsNo;
//					System.out.print("=======key1=" + key1 + "\n");
//					System.out.print("=======MapHsHsEmsNo.size=="
//							+ MapHsHsEmsNo.size() + "\n");
//					Iterator it = MapHsHsEmsNo.keySet().iterator();
//					while (it.hasNext()) {
//						String key = (String)it.next();
//						if(key.equals(key1)){
//							System.out.print("=======YES======"+"\n");
//						}
//						if(key.contains("H-01-0006-01")){
//							System.out.print("=======key======"+key+"\n");
//						}
//					}
					if (MapHsHsEmsNo.get(key1) == null) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行【 料号=" + ptNo + ",报关名称="
								+ hsName + ",报关规格=" + hsSpec + ",报关单位="
								+ hsUnitName + ",手册号=" + emsNo + "】在对应关系中不存在!";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						errorList.add(error);
					}
				}
			}

			if (!casBillOption.getIsBillRepeat()) {// 不允许单据头重复
				// 单据号+单据类型代码+生效日期格+客户供应商(代码)
				String head = (billTemp.getBill2() == null ? "" : billTemp
						.getBill2())
						+ (String) mapBillType
								.get(billTemp.getBill1() == null ? ""
										: billTemp.getBill1())
						+ (billTemp.getBill4() == null ? "" : billTemp
								.getBill4()) + mapScm.get(billTemp.getBill3());
				if (headlist.contains(head)) {
					if (!repeatHeadList.contains(head)) {
						TempBillInputErrMassageShow error = new TempBillInputErrMassageShow();
						err = "警告:第" + (i + 1) + "行 单据头有重复，不能导入";
						error.setErrorReason(err);
						error.setInvalidationRow(i + 1);
						errorList.add(error);
						repeatHeadList.add(head);
					}
				}
			}

			listDetail.add(billTemp);// 表体
			String key2 = (billTemp.getBill1() == null ? "" : billTemp
					.getBill1())
					+ "/"
					+ (billTemp.getBill2() == null ? "" : billTemp.getBill2())
					+ "/"
					+ (billTemp.getBill3() == null ? "" : billTemp.getBill3())
					+ "/"
					+ (billTemp.getBill4() == null ? "" : billTemp.getBill4())
					+ "/"
					+ (billTemp.getBill5() == null ? "" : billTemp.getBill5())
					+ "/" + billTemp.getBill100();
			if (headHash.get(key2) == null) {
				headHash.put(key2, key2);
				list.add(billTemp);
			}
		}
		allList.add(list);
		allList.add(listDetail);
		allList.add(errorList);
		// 统计检查

		return allList;
	}

	/**
	 * 获取表头
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
	 * This method initializes btnImbark
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnImbark() {
		if (btnImbark == null) {
			btnImbark = new JButton();
			btnImbark.setText("3.保存");
			btnImbark.setPreferredSize(new Dimension(85, 30));
			btnImbark.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List dlist = null;
					String temp = "";
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(DgBillTxtImport.this,
								"装载数据为空!", "提示", 2);
						return;
					}
					if (paramerMap == null) {
						JOptionPane.showMessageDialog(DgBillTxtImport.this,
								"参数没有设置!", "提示", 2);
						return;
					} else if (paramerMap.get(5) == null) {
						JOptionPane.showMessageDialog(DgBillTxtImport.this,
								"物料类别没有设置!", "提示", 2);
						return;
					}
					/*
					 * dlist = tableModelDetail.getList(); temp
					 * =casAction.findMateriel(dlist); if(!temp.equals("")){ if
					 * (JOptionPane.showConfirmDialog(DgBillTxtImport.this,
					 * temp+"料号不存在，是否继续导入!!!", "提示",
					 * JOptionPane.OK_CANCEL_OPTION)== JOptionPane.OK_OPTION){
					 * new SaveDataRunnable().start(); } return; }
					 */
					new SaveDataRunnable().start();
				}
			});
		}
		return btnImbark;
	}

	/**
	 * 保存数据多线程
	 * 
	 * @author ower
	 * 
	 */

	class SaveDataRunnable extends Thread {
		boolean isok = true;

		public void run() {
			List list = null;
			List dlist = null;
			try {
				CommonProgress.showProgressDialog(DgBillTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = tableModel.getList();
				dlist = tableModelDetail.getList();

				try {
					casAction.executeBillImport(new Request(CommonVars
							.getCurrUser()), list, dlist, paramerMap);
				} catch (SQLException e1) {
					isok = false;
					e1.printStackTrace();
				}

				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {
				isok = false;
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
				if (isok) {
					JOptionPane.showMessageDialog(DgBillTxtImport.this,
							"导入完毕!\n\n单据头数据: " + String.valueOf(list.size())
									+ " 笔\n单据体数据: "
									+ String.valueOf(dlist.size()) + " 笔",
							"提示", 2);
				} else {
					JOptionPane.showMessageDialog(DgBillTxtImport.this,
							"装载失败！", "提示！", 2);
				}
			}
		}
	}

	/**
	 * 获取主表名称
	 * 
	 * @param type
	 * @return
	 */

	private String getMasterTableName(String type) {
		String tableName = "";
		if (type != null && type.equals("料件")) {
			tableName = "BillMasterMateriel";
		} else if (type != null && type.equals("成品")) {
			tableName = "BillMasterProduct";
		} else if (type != null && type.equals("设备")) {
			tableName = "BillMasterFixture";
		} else if (type != null && type.equals("半成品")) {
			tableName = "BillMasterHalfProduct";
		} else if (type != null && type.indexOf("残次品") > -1) {
			tableName = "BillMasterRemainProduct";
		} else if (type != null && type.equals("边角料")) {
			tableName = "BillMasterLeftoverMateriel";
		}
		return tableName;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.setPreferredSize(new Dimension(85, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgBillTxtImport.this.dispose();
				}
			});
		}
		return btnExit;
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
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(200);
		}
		return jSplitPane;
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
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
	 * 初始化单据表表头信息
	 * 
	 * @param list
	 */
	private void initTable(final List list) {// 单据头
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// list.add(addColumn("单据类型代码", "bill1", 100));
						list.add(addColumn("单据名称", "bill1", 100));// wss2010.06.10改
						list.add(addColumn("单据号码", "bill2", 100));
						list.add(addColumn("客户供应商(代码)", "bill3", 120));
						list.add(addColumn("生效日期(YYYY-MM-DD)", "bill4", 140));
						list.add(addColumn("关封号", "envelopNo", 120));// wss2010.08.31新增关封号
						list.add(addColumn("备注", "bill5", 100));
						return list;
					}
				});
	}

	/**
	 * 初始化单据表表体信息
	 * 
	 * @param list
	 */

	private void initTableDetail(final List list) {// 单据体
		tableModelDetail = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						// list.add(addColumn("1.单据类型代码", "bill1", 100));
						list.add(addColumn("1.单据名称", "bill1", 100));// wss:2010.06.10修改
						list.add(addColumn("2.单据号码", "bill2", 100));
						list.add(addColumn("3.客户供应商(代码)", "bill3", 120));
						list.add(addColumn("4.生效日期(YYYY-MM-DD)", "bill4", 140));

						list.add(addColumn("5.关封号", "envelopNo", 120));// wss2010.08.31新增关封号

						list.add(addColumn("6.备注", "bill5", 100));
						list.add(addColumn("7.料号", "bill6", 100));
						list.add(addColumn("8.净重", "bill7", 100));
						list.add(addColumn("9.毛重", "bill8", 100));
						list.add(addColumn("10.数量", "bill9", 100));
						list.add(addColumn("11.单价", "bill10", 100));
						list.add(addColumn("12.版本号", "bill11", 100));
						list.add(addColumn("13.仓库(代码)", "bill12", 120));
						list.add(addColumn("14.制单号", "bill13", 100));
						list.add(addColumn("15.折算报关数量", "bill14", 135));
						list.add(addColumn("16.对应数量", "bill15", 100));
						list.add(addColumn("17.海关单价", "bill16", 100));
						list.add(addColumn("18.报关名称", "bill18", 100));
						list.add(addColumn("19.报关规格", "bill19", 100));
						list.add(addColumn("20.报关单位", "bill20", 100));

						list.add(addColumn("21.手册号", "emsNo", 100));// wss2010.09.07

						list.add(addColumn("22.对应报关单", "bill17", 100));
						list.add(addColumn("23.单体备注", "note", 100));
						list.add(addColumn("24.送货单号", "takeBillNo", 100));
						list.add(addColumn("25.订单号", "orderNo", 100));
						return list;
					}
				});
	}

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

	/**
	 * 文件过滤
	 * 
	 * @author ower
	 * 
	 */

	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		/**
		 * 接收文件
		 */

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

		/**
		 * 获取文件类型
		 */

		public String getDescription() {
			return "*." + this.suffix;
		}

		/**
		 * 获取文件后缀
		 * 
		 * @param f
		 * @return
		 */

		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * This method initializes rbIsBig5ConvertToGB
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIsBig5ConvertToGB() {
		if (rbIsBig5ConvertToGB == null) {
			rbIsBig5ConvertToGB = new JRadioButton();
			rbIsBig5ConvertToGB.setText("【开始导入】繁转简   [文本格式见下方表格表头排列顺序]");
		}
		return rbIsBig5ConvertToGB;
	}

	/**
	 * This method initializes btnParameterSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnParameterSet() {
		if (btnParameterSet == null) {
			btnParameterSet = new JButton();
			btnParameterSet.setText("1.参数设置");
			btnParameterSet.setPreferredSize(new Dimension(85, 30));
			btnParameterSet.setForeground(java.awt.Color.blue);
			btnParameterSet
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgBillTxtImportSet dg = new DgBillTxtImportSet();
							dg.setHs(paramerMap);
							dg.setVisible(true);
							if (dg.isOk()) {
								paramerMap = dg.hs;
							}
						}
					});
		}
		return btnParameterSet;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
