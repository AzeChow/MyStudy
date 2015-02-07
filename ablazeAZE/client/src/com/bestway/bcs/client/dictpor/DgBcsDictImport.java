package com.bestway.bcs.client.dictpor;

import java.awt.BorderLayout;
import java.io.File;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcs.dictpor.entity.TempBcsDictPorExg;
import com.bestway.bcs.dictpor.entity.TempBcsDictPorImg;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.winuicontrol.JDialogBase;

import java.awt.Dimension;

public class DgBcsDictImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnOpenFile = null;

	private JButton btnSaveData = null;

	private JButton jButton2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null; // 料件

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null; // 成品

	private File txtFile = null;

	private Hashtable gbHash = null;

	private CustomBaseAction customBaseAction = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private BcsDictPorHead head = null; // @jve:decl-index=0:

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelBom = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbIsOverwrite = null;

	private DzscAction dzscAction = null;

	private JCheckBox cbCheckTitle = null;

	private JButton btnColumn = null;

	/**
	 * This is the default constructor
	 */
	public DgBcsDictImport() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		initialize();
		InputTableColumnSetUtils.putColumn(InputTableColumnSet.DICT_IMG_INPUT,
				this.getDefaultImgTableColumnList());
		InputTableColumnSetUtils.putColumn(InputTableColumnSet.DICT_EXG_INPUT,
				this.getDefaultExgTableColumnList());
		initTableImg(new ArrayList());
		initTableExg(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(746, 469);
		this.setContentPane(getJContentPane());
		this.setTitle("备案资料库备案导入接口");
	}

	private List getDefaultImgTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "img.seqNum", 60, Integer.class));
		list.add(new JTableListColumn("归并序号", "img.innerMergeSeqNum", 100));
		list.add(new JTableListColumn("商品编码", "img.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "img.commName", 100));
		list.add(new JTableListColumn("商品规格", "img.commSpec", 100));
		list.add(new JTableListColumn("常用单位(名称)", "img.comUnit.name", 100));
		list.add(new JTableListColumn("单价", "img.unitPrice", 60));
		list.add(new JTableListColumn("币制(名称)", "img.curr.name", 100));
		list.add(new JTableListColumn("是否主料(true/false)", "img.isMainImg", 200));
		list.add(new JTableListColumn("外经编码", "img.wjCode", 100));
		// list.add(new JTableListColumn("错误信息", "errinfo", 300));
		return list;
	}

	/**
	 * 初始化料件
	 */
	private void initTableImg(List list) {
		tableModelImg = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.DICT_IMG_INPUT);
					}
				});
	}

	private List getDefaultExgTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("备案序号", "exg.seqNum", 60, Integer.class));
		list.add(new JTableListColumn("归并序号", "exg.innerMergeSeqNum", 100));
		list.add(new JTableListColumn("商品编码", "exg.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "exg.commName", 100));
		list.add(new JTableListColumn("商品规格", "exg.commSpec", 100));
		list.add(new JTableListColumn("常用单位(名称)", "exg.comUnit.name", 100));
		list.add(new JTableListColumn("单价", "exg.unitPrice", 60));
		list.add(new JTableListColumn("币制(名称)", "exg.curr.name", 100));
		list.add(new JTableListColumn("外经编码", "exg.wjCode", 100));
		// list.add(new JTableListColumn("错误信息", "errinfo", 300));
		return list;
	}

	/**
	 * 初始化成品
	 */
	private void initTableExg(List list) {
		tableModelExg = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.DICT_EXG_INPUT);
					}
				});
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
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
			jToolBar.add(getCbIsOverwrite());
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
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1.打开文件");
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgBcsDictImport.this);
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
		return btnOpenFile;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgBcsDictImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				BcsParameterSet bcsParameterSet = customBaseAction
						.findBcsParameterSet(new Request(CommonVars
								.getCurrUser()));// 参数设置
				if (jTabbedPane.getSelectedIndex() == 0) {
					list = parseTxtFileImg(bcsParameterSet);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					list = parseTxtFileExg(bcsParameterSet);
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
		return fileRow[dataIndex];
	}

	private List parseTxtFileImg(BcsParameterSet bcsParameterSet) {
		
		Map<String,BcsDictPorImg> imgMap = new HashMap<String, BcsDictPorImg>();
		Map<Integer,Integer> mapExgSeqNum = new HashMap<Integer, Integer>();
		initImgMap(imgMap,mapExgSeqNum);
		
		// 商品编码
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		//名称、规格限定长度
		List bytesLength = customBaseAction.findBytesLength(new Request(
				CommonVars.getCurrUser()));
		String strBytesLength = String.valueOf(bytesLength.get(0));
		//是否名称、规格限定长度
		List isControlLength = customBaseAction.findIsControlLength(new Request(
				CommonVars.getCurrUser()));
		String strIsControlLength = String.valueOf(isControlLength.get(0));
		// 单位
		List unitList = CustomBaseList.getInstance().getUnits();
		// 币制
		List currList = CustomBaseList.getInstance().getCurrs();

		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		Hashtable<String, Curr> hsCurr = new Hashtable<String, Curr>();

		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hs.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsUnit.put(u.getName(), u);
		}
		for (int i = 0; i < currList.size(); i++) {
			Curr u = (Curr) currList.get(i);
			hsCurr.put(u.getName(), u);
		}

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
		int len = 50;
		List list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.DICT_IMG_INPUT);
		int zcount = lsIndex.size();// 13; // 字段数目
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

			TempBcsDictPorImg obj = new TempBcsDictPorImg();
			BcsDictPorImg img = new BcsDictPorImg();
			String err = "";

			// int zcount = 9;// 字段数目
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("img.seqNum".equals(columnField)) {// 料件序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   序号不合法/";
						continue;
					}
					if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
						err = err + "[" + value + "]   序号重复/";
					} else {
						lsSeqNum.add(Double.valueOf(value).intValue());
					}
					img.setSeqNum(Double.valueOf(value).intValue());
				} else if ("img.innerMergeSeqNum".equals(columnField)) {// 归并序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   归并序号不合法/";
						continue;
					}
					img.setInnerMergeSeqNum(Double.valueOf(value).intValue());
				} else if ("img.complex.code".equals(columnField)) {// 商品编码
					if (hs.get(value) == null) {
						Complex cobj = dzscAction.findCustomsComplexByCode(
								new Request(CommonVars.getCurrUser()), value);

						if (cobj != null) {
							hs.put(cobj.getCode(), cobj);
							img.setComplex(cobj);// 商品编码
						} else {
							err = err + "[" + value + "]   不正确商品编码/";
						}
					} else {
						Complex c = (Complex) hs.get(value);
						img.setComplex(c);
					}
				} else if ("img.commName".equals(columnField)) {// 名称/规格
					if(strIsControlLength.equals("false")){
					img.setCommName(value);
					}else{
					if(value.toString().length() > Integer.parseInt(strBytesLength)){
						err = err + "商品名称长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
					}
					img.setCommName(value);
					}
						if (value != null && !value.equals("")) {
//						if (bcsParameterSet != null
//								&& bcsParameterSet.getIsControlLength() != null
//								&& bcsParameterSet.getIsControlLength()) {
//							// 参数设置,是否要控制名称、规格的长度
//							len = bcsParameterSet.getBytesLength() == null ? 50
//									: bcsParameterSet.getBytesLength();
//						}
//						if (value.getBytes().length > len) {
//							err = err + "[" + value + "]   名称长度超过了设置的长度" + len
//									+ "/";
//						}
					} else {
						err = err + "[" + value + "]   名称不可为空/";
					}
					}else if ("img.commSpec".equals(columnField)) {// 规格
						if(strIsControlLength.equals("false")){
							img.setCommSpec(value);
						}else{
						if(value.toString().length() > Integer.parseInt(strBytesLength)){
							err = err + "商品规格长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
						}
					    img.setCommSpec(value);
						}
//					if (value != null && !value.equals("")) {
//						if (bcsParameterSet != null
//								&& bcsParameterSet.getIsControlLength() != null
//								&& bcsParameterSet.getIsControlLength()) {// 参数设置,是否要控制名称、规格的长度
//							len = bcsParameterSet.getBytesLength() == null ? 50
//									: bcsParameterSet.getBytesLength();
//						}
//						if (value.getBytes().length > len) {
//							err = err + "[" + value + "]   规格长度超过了设置的长度" + len
//									+ "/";
//						}
//					}
				} else if ("img.comUnit.name".equals(columnField)) {// 单位
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]   单位不为空/";
					} else {
						if (hsUnit.get(value) != null) {
							Unit u = (Unit) hsUnit.get(value);
							img.setComUnit(u);
						} else {
							err = err + "[" + value + "]   单位不存在/";
						}
					}
				} else if ("img.unitPrice".equals(columnField)) {// 备案单价
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单价不合法/";
						continue;
					}
					img.setUnitPrice(Double.valueOf(value));
				} else if ("img.curr.name".equals(columnField)) {// 币制
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]   币制不为空/";
					} else {
						if (hsCurr.get(value) != null) {
							Curr u = (Curr) hsCurr.get(value);
							img.setCurr(u);
						} else {
							err = err + "[" + value + "]   币制不存在/";
						}
					}
				} else if ("img.isMainImg".equals(columnField)) {// 是否主料
					try {
						Boolean.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   是否主料不合法/";
						continue;
					}
					img.setIsMainImg(Boolean.valueOf(value));
				} else if ("img.wjCode".equals(columnField)) {// 外经编码
					img.setWjCode(value);
				}
			}
			if(!cbIsOverwrite.isSelected()){
				Integer seqNum = img.getSeqNum();
				String complexCode = img.getComplex()==null?"":img.getComplex().getCode();
				String commName =  img.getCommName();
				String commSpec = img.getCommSpec();
				String ComUnitCode = img.getComUnit()==null?"":img.getComUnit().getCode();
				
				//编码/名称/规格/单位
				String key = complexCode+"/"+commName+"/"+commSpec+"/"+ComUnitCode;
				
				if(imgMap.get(key)!=null){
					err=err+"[编码/名称/规格/单位]不能同时重复";
				}else{
					imgMap.put(key, img);
				}
				
				if(mapExgSeqNum.get(seqNum)!=null){
					err=err+"[备案序号]不能重复";
				}else{
					mapExgSeqNum.put(seqNum, seqNum);
				}
			}
			
			
			img.setDictPorHead(head);
			img.setCompany(CommonVars.getCurrUser().getCompany());
			obj.setImg(img);
			obj.setErrinfo(err);
			list.add(obj);
			
		}
		return list;
	}

	private List parseTxtFileExg(BcsParameterSet bcsParameterSet) {
		
		Map<String,BcsDictPorExg> exgMap = new HashMap<String, BcsDictPorExg>();
		Map<Integer,Integer> mapExgSeqNum = new HashMap<Integer,Integer>();
		initExgMap(exgMap,mapExgSeqNum);
		
		// 商品编码
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		//名称、规格限定长度
		List bytesLength = customBaseAction.findBytesLength(new Request(
				CommonVars.getCurrUser()));
		String strBytesLength = String.valueOf(bytesLength.get(0));
		//是否名称、规格限定长度
		List isControlLength = customBaseAction.findIsControlLength(new Request(
				CommonVars.getCurrUser()));
		String strIsControlLength = String.valueOf(isControlLength.get(0));
		// 单位
		List unitList = CustomBaseList.getInstance().getUnits();
		// 币制
		List currList = CustomBaseList.getInstance().getCurrs();

		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		Hashtable<String, Curr> hsCurr = new Hashtable<String, Curr>();

		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hs.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsUnit.put(u.getName(), u);
		}
		for (int i = 0; i < currList.size(); i++) {
			Curr u = (Curr) currList.get(i);
			hsCurr.put(u.getName(), u);
		}

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
		int len = 50;
		List list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.DICT_EXG_INPUT);
		int zcount = lsIndex.size();// 13; // 字段数目
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

			TempBcsDictPorExg obj = new TempBcsDictPorExg();
			BcsDictPorExg exg = new BcsDictPorExg();
			String err = "";
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("exg.seqNum".equals(columnField)) {// 成品序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   序号不合法/";
						continue;
					}
					if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
						err = err + "[" + value + "]   序号重复/";
					} else {
						lsSeqNum.add(Double.valueOf(value).intValue());
					}
					exg.setSeqNum(Double.valueOf(value).intValue());
				} else if ("exg.innerMergeSeqNum".equals(columnField)) {// 归并序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   归并序号不合法/";
						continue;
					}
					exg.setInnerMergeSeqNum(Double.valueOf(value).intValue());
				} else if ("exg.complex.code".equals(columnField)) {// 商品编码
					if (hs.get(value) == null) {
						Complex cobj = dzscAction.findCustomsComplexByCode(
								new Request(CommonVars.getCurrUser()), value);

						if (cobj != null) {
							hs.put(cobj.getCode(), cobj);
							exg.setComplex(cobj);// 商品编码
						} else {
							err = err + "[" + value + "]   不正确商品编码/";
						}
					} else {
						Complex c = (Complex) hs.get(value);
						exg.setComplex(c);
					}
				} else if ("exg.commName".equals(columnField)) {// 名称/规格
					if (value != null && !value.equals("")) {
						if(strIsControlLength.equals("false")){
							exg.setCommName(value);
						}else{
						if(value.toString().length()> Integer.parseInt(strBytesLength)){
							err = err + "商品名称长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
						}
						exg.setCommName(value);
						}
//						if (bcsParameterSet != null
//								&& bcsParameterSet.getIsControlLength() != null
//								&& bcsParameterSet.getIsControlLength()) {
//							// 参数设置,是否要控制名称、规格的长度
//							len = bcsParameterSet.getBytesLength() == null ? 50
//									: bcsParameterSet.getBytesLength();
//						}
//						if (value.getBytes().length > len) {
//							err = err + "[" + value + "]   名称长度超过了设置的长度" + len
//									+ "/";
//						}
					} else {
						err = err + "[" + value + "]   名称不可为空/";
					}

				} else if ("exg.commSpec".equals(columnField)) {// 规格
					if(strIsControlLength.equals("false")){
						exg.setCommSpec(value);
					}else{
					if(value.toString().length() > Integer.parseInt(strBytesLength)){
						err = err + "商品规格长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
					}
					exg.setCommSpec(value);
					}
					if (value != null && !value.equals("")) {
//						if (bcsParameterSet != null
//								&& bcsParameterSet.getIsControlLength() != null
//								&& bcsParameterSet.getIsControlLength()) {
//							// 参数设置,是否要控制名称、规格的长度
//							len = bcsParameterSet.getBytesLength() == null ? 50
//									: bcsParameterSet.getBytesLength();
//						}
//						if (value.getBytes().length > len) {
//							err = err + "[" + value + "]   规格长度超过了设置的长度" + len
//									+ "/";
//						}
					}
				} else if ("exg.comUnit.name".equals(columnField)) {// 单位
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]   单位不为空/";
					} else {
						if (hsUnit.get(value) != null) {
							Unit u = (Unit) hsUnit.get(value);
							exg.setComUnit(u);
						} else {
							err = err + "[" + value + "]   单位不存在/";
						}
					}
				} else if ("exg.unitPrice".equals(columnField)) {// 备案单价
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单价不合法/";
						continue;
					}
					exg.setUnitPrice(Double.valueOf(value));
				} else if ("exg.curr.name".equals(columnField)) {// 币制
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]   币制不为空/";
					} else {
						if (hsCurr.get(value) != null) {
							Curr u = (Curr) hsCurr.get(value);
							exg.setCurr(u);
						} else {
							err = err + "[" + value + "]   币制不存在/";
						}
					}
				} else if ("exg.wjCode".equals(columnField)) {// 外经编码
					exg.setWjCode(value);
				}
			}
			
			if(!cbIsOverwrite.isSelected()){
				Integer seqNum = exg.getSeqNum();
				String complexCode = exg.getComplex()==null?"":exg.getComplex().getCode();
				String commName =  exg.getCommName();
				String commSpec = exg.getCommSpec();
				String ComUnitCode = exg.getComUnit()==null?"":exg.getComUnit().getCode();
				
				//编码/名称/规格/单位
				String key = complexCode+"/"+commName+"/"+commSpec+"/"+ComUnitCode;
				
				if(exgMap.get(key)!=null){
					err=err+"[编码/名称/规格/单位]不能同时重复";
				}else{
					exgMap.put(key, exg);
				}
				
				if(mapExgSeqNum.get(seqNum)!=null){
					err=err+"[备案序号]不能重复";
				}else{
					mapExgSeqNum.put(seqNum, seqNum);
				}
				
			}
			
			exg.setDictPorHead(head);
			exg.setCompany(CommonVars.getCurrUser().getCompany());
			obj.setExg(exg);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	/**
	 * 获取成品map
	 * @return
	 */
	private void initExgMap(Map<String,BcsDictPorExg> mapExg,
			Map<Integer,Integer> mapExgSeqNum){
		List<BcsDictPorExg> list = bcsDictPorAction.findBcsDictPorExgByHead(new Request(
				CommonVars.getCurrUser()), head);
		for (int i = 0; i < list.size(); i++) {
			BcsDictPorExg exg = list.get(i);
			
			Integer seqNum = exg.getSeqNum();
			String complexCode = exg.getComplex()==null?"":exg.getComplex().getCode();
			String commName =  exg.getCommName();
			String commSpec = exg.getCommSpec();
			String ComUnitCode = exg.getComUnit()==null?"":exg.getComUnit().getCode();
			
			//编码/名称/规格/单位
			String key = complexCode+"/"+commName+"/"+commSpec+"/"+ComUnitCode;
			
			//序号
			mapExgSeqNum.put(seqNum, seqNum);
			mapExg.put(key, exg);
		}
	}
	
	/**
	 * 获取料件map
	 * @return
	 */
	private void initImgMap(Map<String,BcsDictPorImg> mapExg,
			Map<Integer,Integer> mapExgSeqNum){
		List<BcsDictPorImg> list = bcsDictPorAction.findBcsDictPorImgByHead(new Request(
				CommonVars.getCurrUser()), head);
		for (int i = 0; i < list.size(); i++) {
			BcsDictPorImg img = list.get(i);
			
			Integer seqNum = img.getSeqNum();
			String complexCode = img.getComplex()==null?"":img.getComplex().getCode();
			String commName =  img.getCommName();
			String commSpec = img.getCommSpec();
			String ComUnitCode = img.getComUnit()==null?"":img.getComUnit().getCode();
			
			//编码/名称/规格/单位
			String key = complexCode+"/"+commName+"/"+commSpec+"/"+ComUnitCode;
			
			mapExgSeqNum.put(seqNum, seqNum);
			mapExg.put(key, img);
		}
	}
	
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnSaveData == null) {
			btnSaveData = new JButton();
			btnSaveData.setText("2.保存数据");
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List ls = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						ls = tableModelImg.getList();
						for (int i = 0; i < ls.size(); i++) {
							TempBcsDictPorImg obj = (TempBcsDictPorImg) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								JOptionPane.showMessageDialog(
										DgBcsDictImport.this, "有错误信息，不可保存！",
										"提示!", 2);
								return;
							}
						}
						bcsDictPorAction.saveBcsDictImgFromImport(new Request(
								CommonVars.getCurrUser()), ls, cbIsOverwrite
								.isSelected());
						initTableImg(new Vector());
						JOptionPane.showMessageDialog(DgBcsDictImport.this,
								"保存完毕，请关闭然后重新打开备案资料库备案界面！", "提示!", 2);

					} else if (jTabbedPane.getSelectedIndex() == 1) {
						ls = tableModelExg.getList();
						for (int i = 0; i < ls.size(); i++) {
							TempBcsDictPorExg obj = (TempBcsDictPorExg) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								JOptionPane.showMessageDialog(
										DgBcsDictImport.this, "有错误信息，不可保存！",
										"提示!", 2);
								return;
							}
						}
						bcsDictPorAction.saveBcsDictExgFromImport(new Request(
								CommonVars.getCurrUser()), ls, cbIsOverwrite
								.isSelected());
						initTableExg(new Vector());
						JOptionPane.showMessageDialog(DgBcsDictImport.this,
								"保存完毕，请关闭然后重新打开备案资料库备案界面！", "提示!", 2);
					}

				}
			});
		}
		return btnSaveData;
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
					DgBcsDictImport.this.dispose();
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

	public BcsDictPorHead getHead() {
		return head;
	}

	public void setHead(BcsDictPorHead head) {
		this.head = head;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbJF() {
		if (cbJF == null) {
			cbJF = new JCheckBox();
			cbJF.setText("繁转简");
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
			cbIsOverwrite.setText("资料存在覆盖导入");
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
			cbCheckTitle.setText("第一行为标题行");
		}
		return cbCheckTitle;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("栏位设定");
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.DICT_IMG_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableImg(new ArrayList());
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.DICT_EXG_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableExg(new ArrayList());
						}
					}
				}
			});
		}
		return btnColumn;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
