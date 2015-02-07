package com.bestway.bcs.client.contract;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.BcsParameterSet;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.contract.entity.ContractBom;
import com.bestway.bcs.contract.entity.ContractExg;
import com.bestway.bcs.contract.entity.ContractImg;
import com.bestway.bcs.contract.entity.TempContractBom;
import com.bestway.bcs.contract.entity.TempContractExg;
import com.bestway.bcs.contract.entity.TempContractImg;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcs.dictpor.entity.BcsDictPorExg;
import com.bestway.bcs.dictpor.entity.BcsDictPorHead;
import com.bestway.bcs.dictpor.entity.BcsDictPorImg;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.LevyMode;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.DutyRateType;
import com.bestway.common.constant.ModifyMarkState;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgContractInput extends JDialogBase {

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

	private Hashtable gbHash = null; // @jve:decl-index=0:

	private CustomBaseAction customBaseAction = null;

	private DzscAction dzscAction = null;

	private Contract head = null; // @jve:decl-index=0:

	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelBom = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbIsOverwrite = null;

	private ContractAction contractAction = null;

	private BcsDictPorAction bcsDictPorAction = null;

	private boolean isEms = true;

	private int materielItemCount = 0; // 设置表头料件项数

	private int productItemCount = 0; // 表头成品项目个数

	private JCheckBox cbCheckTitle = null;

	private JButton btnColumn = null;
	
	boolean isWrite = false;//判断是否根据记录号来反写商品名称，规格，单位，计量单位

	/**
	 * This is the default constructor
	 */
	public DgContractInput() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		contractAction = (ContractAction) CommonVars.getApplicationContext()
				.getBean("contractAction");
		bcsDictPorAction = (BcsDictPorAction) CommonVars
				.getApplicationContext().getBean("bcsDictPorAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.CONTRACT_IMG_INPUT, this
						.getDefaultImgTableColumnList());
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.CONTRACT_EXG_INPUT, this
						.getDefaultExgTableColumnList());
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.CONTRACT_BOM_INPUT, this
						.getDefaultBomTableColumnList());

		initTableImg(new ArrayList());
		initTableExg(new ArrayList());
		initTableBom(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(736, 469);
		this.setContentPane(getJContentPane());
		this.setTitle("通关备案数据导入接口");
	}

	private List getDefaultImgTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料件序号", "contractImg.seqNum", 60,
				Integer.class));
		list.add(new JTableListColumn("记录号", "contractImg.credenceNo", 60,
				Integer.class));
		list.add(new JTableListColumn("商品编码", "contractImg.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "contractImg.name", 100));
		list.add(new JTableListColumn("商品规格", "contractImg.spec", 100));
		list
				.add(new JTableListColumn("计量单位(名称)", "contractImg.unit.name",
						100));
		list.add(new JTableListColumn("单价", "contractImg.declarePrice", 60));
		list.add(new JTableListColumn("数量", "contractImg.amount", 60));
		list.add(new JTableListColumn("主料/辅料(true/false)",
				"contractImg.isMainImg", 120));
		list.add(new JTableListColumn("单位重量", "contractImg.unitWeight", 100));
		list.add(new JTableListColumn("原产地(名称)", "contractImg.country.name",
				120));
		list.add(new JTableListColumn("征免方式(名称)", "contractImg.levyMode.name",
				120));
		// list.add(new JTableListColumn("限制类标志", "contractImg.restriction",
		// 120));
		list.add(new JTableListColumn("备注", "contractImg.note", 100));
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
								.getTableColumnList(InputTableColumnSet.CONTRACT_IMG_INPUT);
					}
				});
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		tableModelImg.packRows();
	}

	private List getDefaultExgTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品序号", "contractExg.seqNum", 60,
				Integer.class));
		list.add(new JTableListColumn("记录号", "contractExg.credenceNo", 60,
				Integer.class));
		list.add(new JTableListColumn("商品编码", "contractExg.complex.code", 100));
		list.add(new JTableListColumn("商品名称", "contractExg.name", 100));
		list.add(new JTableListColumn("商品规格", "contractExg.spec", 100));
		list
				.add(new JTableListColumn("计量单位(名称)", "contractExg.unit.name",
						100));
		list.add(new JTableListColumn("单价", "contractExg.unitPrice", 60));
		list.add(new JTableListColumn("数量", "contractExg.exportAmount", 60));
		list.add(new JTableListColumn("加工费单价", "contractExg.processUnitPrice",
				100));
		list.add(new JTableListColumn("原料费", "contractExg.materialFee", 100));
		list.add(new JTableListColumn("单位毛重", "contractExg.unitGrossWeight",
				100));
		list
				.add(new JTableListColumn("单位净重", "contractExg.unitNetWeight",
						100));
		list.add(new JTableListColumn("消费国(名称)", "contractExg.country.name",
				120));
		list.add(new JTableListColumn("征免方式(名称)", "contractExg.levyMode.name",
				120));
		list.add(new JTableListColumn("申报状态(名称)", "contractExg.dutyRateString",
				120));
		// list.add(new JTableListColumn("限制类标志", "contractExg.restriction",
		// 120));
		list.add(new JTableListColumn("备注", "contractExg.note", 100));
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
								.getTableColumnList(InputTableColumnSet.CONTRACT_EXG_INPUT);
					}
				});
		jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		tableModelExg.packRows();
	}

	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("成品序号", "contractBom.contractExg.seqNum",
				60, Integer.class));
		list.add(new JTableListColumn("料件序号", "contractBom.contractImgSeqNum",
				100));
		list.add(new JTableListColumn("单耗", "contractBom.unitWaste", 100));
		list.add(new JTableListColumn("损耗", "contractBom.waste", 100));
		list.add(new JTableListColumn("非保税料件比例%", "contractBom.nonMnlRatio",
				100));
		return list;
	}

	/**
	 * 初始化单耗
	 */
	private void initTableBom(List list) {
		tableModelBom = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						// return getBomTableColumnList();
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.CONTRACT_BOM_INPUT);
					}
				});
		jTable2.getColumnModel().getColumn(jTable2.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		tableModelBom.packRows();
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
			jToolBar.add(getBtnColumn());
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
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("1.打开文件");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if(JOptionPane.OK_OPTION == JOptionPane.showOptionDialog(DgContractInput.this, "是否需要根据记录号从备案库更新到通关手册？",
							"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"是")){
						isWrite = true;
					}else{
						isWrite = false;
					}
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgContractInput.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					String dictPorEmsNo = head.getCorrEmsNo();// 备案资料库编号
					if (isEms) {// 电子化手册
						if (dictPorEmsNo == null
								|| "".equals(dictPorEmsNo.trim())) {
							List lsExingBcsDictPor = bcsDictPorAction
									.findExingBcsDictPorHead(new Request(
											CommonVars.getCurrUser(), true));
							if (lsExingBcsDictPor.size() <= 0) {
								JOptionPane.showMessageDialog(
										DgContractInput.this, "备案资料库料件没有资料",
										"提示", JOptionPane.INFORMATION_MESSAGE);
								return;
							} else if (lsExingBcsDictPor.size() == 1) {
								dictPorEmsNo = ((BcsDictPorHead) lsExingBcsDictPor
										.get(0)).getDictPorEmsNo();
							} else {
								BcsDictPorHead bcsDictPorHead = BcsClientHelper
										.getInstance().findExingBcsDictPorHead(
												lsExingBcsDictPor);
								if (bcsDictPorHead == null) {
									return;
								}
								dictPorEmsNo = bcsDictPorHead.getDictPorEmsNo();
							}
						}
					}

					new ImportFileDataRunnable(dictPorEmsNo).execute();

				}
			});
		}
		return jButton;
	}

	class ImportFileDataRunnable extends SwingWorker {
		private String dictPorEmsNo;

		public ImportFileDataRunnable(String dictPorEmsNo) {
			this.dictPorEmsNo = dictPorEmsNo;
		}

		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgContractInput.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				BcsParameterSet bcsParameterSet = customBaseAction
						.findBcsParameterSet(new Request(CommonVars
								.getCurrUser()));// 参数设置
				if (jTabbedPane.getSelectedIndex() == 0) {
					list = parseTxtFileImg(dictPorEmsNo, bcsParameterSet);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					list = parseTxtFileExg(dictPorEmsNo, bcsParameterSet);
				} else {
					list = parseTxtFileBom();
				}
				head.setCorrEmsNo(dictPorEmsNo);
				contractAction.saveContract(new Request(CommonVars
						.getCurrUser()), head);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
			}
			return list;
		}

		@Override
		protected void done() {
			List list = null;
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (list == null) {
				list = new ArrayList();
			}
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

	@SuppressWarnings("unused")
	private List parseTxtFileImg(String dictPorEmsNo,
			BcsParameterSet bcsParameterSet) {
		// add by xxm 2007-11-27
		Hashtable<Integer, BcsDictPorImg> imgHs = new Hashtable<Integer, BcsDictPorImg>();
		if (isEms) {// 电子化手册
			List headLs = bcsDictPorAction.findBcsDictPorHead(new Request(
					CommonVars.getCurrUser()), dictPorEmsNo,
					DeclareState.PROCESS_EXE);
			if (headLs != null && headLs.size() > 0) {
				BcsDictPorHead head = (BcsDictPorHead) headLs.get(0);
				List imgLs = bcsDictPorAction.findBcsDictPorImgByHead(
						new Request(CommonVars.getCurrUser()), head);
				for (int k = 0; k < imgLs.size(); k++) {
					BcsDictPorImg img = (BcsDictPorImg) imgLs.get(k);
					if ((ModifyMarkState.UNCHANGE).equals(img.getModifyMark())) {// 未修改
						imgHs.put(img.getSeqNum(), img);
					}
				}
			} else {
				JOptionPane.showMessageDialog(DgContractInput.this, "没有正在执行的"
						+ dictPorEmsNo + "备案资料库", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return new ArrayList();
			}
		}

		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		List unitList = CustomBaseList.getInstance().getUnits();
		List countryList = CustomBaseList.getInstance().getCountrys();
		List levyModeList = CustomBaseList.getInstance().getLevymode();
		List lsBcsDictPorExgSeqNum = new ArrayList();
		//名称、规格限定长度
		List bytesLength = customBaseAction.findBytesLength(new Request(
				CommonVars.getCurrUser()));
		String strBytesLength = String.valueOf(bytesLength.get(0));
		//是否名称、规格限定长度
		List isControlLength = customBaseAction.findIsControlLength(new Request(
				CommonVars.getCurrUser()));
		String strIsControlLength = String.valueOf(isControlLength.get(0));
		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		Hashtable<String, Country> hsCountry = new Hashtable<String, Country>();
		Hashtable<String, LevyMode> hsLevyMode = new Hashtable<String, LevyMode>();

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
		// if (bcsDictPorHead != null) {
		// List list = this.bcsDictPorAction.findBcsDictPorHead(new
		// Request(CommonVars.getCurrUser()), bcsDictPorHead);
		// for (int i = 0; i < list.size(); i++) {
		// lsBcsDictPorExgSeqNum.add(((BcsDictPorExg) list.get(i)).getSeqNum());
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
		List list = new ArrayList();
		int len = 50;
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.CONTRACT_IMG_INPUT);
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

			TempContractImg obj = new TempContractImg();
			ContractImg img = new ContractImg();
			String err = "";
			Complex complexCode = null;

			// int zcount = 13;// 字段数目

			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("contractImg.seqNum".equals(columnField)) {
					// 料件序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   序号不合法\n";
						continue;
					}
					if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
						err = err + "[" + value + "]   序号重复\n";
					} else {
						lsSeqNum.add(Double.valueOf(value).intValue());
					}
					img.setSeqNum(Double.valueOf(value).intValue());
				} else if ("contractImg.credenceNo".equals(columnField)) {
					// 记录号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   记录号不合法\n";
						continue;
					}
					Integer credenceNo = Double.valueOf(value).intValue();
					if (dictPorEmsNo != null && !"".equals(dictPorEmsNo.trim())) {
						if (imgHs.get(credenceNo) == null) {
							err = err + "[" + value + "]   记录号" + credenceNo
									+ "在备案资料库中不存在\n";
						}
					}
					img.setCredenceNo(credenceNo);
				} else if ("contractImg.complex.code".equals(columnField)) {
					// 商品编码
					if(isWrite&&(img.getCredenceNo()!=null&&(imgHs.get(img.getCredenceNo())!=null))){
						img.setComplex(imgHs.get(img.getCredenceNo()).getComplex());
					}else{
						if (hs.get(value) == null) {
							err = err + "[" + value + "]   不正确的商品编码\n";
						} else {
							Complex c = (Complex) hs.get(value);
							img.setComplex(c);
						}
					}
				} else if ("contractImg.name".equals(columnField)) {
					// 名称
					if(isWrite&&(img.getCredenceNo()!=null)&&(imgHs.get(img.getCredenceNo())!=null)){
						img.setName(imgHs.get(img.getCredenceNo()).getCommName());
					}else{
						if (value != null && !value.equals("")) {
							if(strIsControlLength.equals("false")){
							img.setName(value);
							}else{
								if(value.toString().length() > Integer.parseInt(strBytesLength)){
									err = err + "商品名称长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
								}
								img.setName(value);
							}
//							if (bcsParameterSet != null
//									&& bcsParameterSet.getIsControlLength() != null
//									&& bcsParameterSet.getIsControlLength()) {// 参数设置,是否要控制名称、规格的长度
//								len = bcsParameterSet.getBytesLength() == null ? 50
//										: bcsParameterSet.getBytesLength();
//							}
//							if (value.getBytes().length > len) {
//								err = err + "[" + value + "]   名称长度超过了设置的长度" + len
//										+ "/";
//							}
						} else {
							err = err + "[" + value + "]   名称不可为空\n";
						}
					}
				} else if ("contractImg.spec".equals(columnField)) {
					//规格
					if(isWrite&&(img.getCredenceNo()!=null)&&(imgHs.get(img.getCredenceNo())!=null)){
						img.setSpec(imgHs.get(img.getCredenceNo()).getCommSpec());
					}else{
						if (value != null && !value.equals("")) {
							if(strIsControlLength.equals("false")){
							img.setSpec(value);
							}else{
								if(value.toString().length() > Integer.parseInt(strBytesLength)){
									err = err + "商品规格长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
								}
								img.setSpec(value);
							}
//							if (bcsParameterSet != null
//									&& bcsParameterSet.getIsControlLength() != null
//									&& bcsParameterSet.getIsControlLength()) {// 参数设置,是否要控制名称、规格的长度
//								len = bcsParameterSet.getBytesLength() == null ? 50
//										: bcsParameterSet.getBytesLength();
//							}
//							if (value.getBytes().length > len) {
//								err = err + "[" + value + "]   规格长度超过了设置的长度" + len
//										+ "/";
//							}
						}
					}
				} else if ("contractImg.unit.name".equals(columnField)) {
					// 单位
					if(isWrite&&(img.getCredenceNo()!=null)&&(imgHs.get(img.getCredenceNo())!=null)){
						img.setUnit(imgHs.get(img.getCredenceNo()).getComUnit());
					}else{
						if (value == null || "".equals(value)) {
							err = err + "[" + value + "]   单位不为空\n";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								img.setUnit(u);
							} else {
								err = err + "[" + value + "]   单位不存在\n";
							}
						}
					}
				} else if ("contractImg.declarePrice".equals(columnField)) {
					// 单价
					Double price = 0.0;
					try {
						price = Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单价不合法\n";
						continue;
					}
					img.setDeclarePrice(price);
				} else if ("contractImg.amount".equals(columnField)) {
					// 备案数量
					Double num = 0.0;
					try {
						num = Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   备案数量不合法\n";
						continue;
					}
					img.setAmount(num);
				} else if ("contractImg.isMainImg".equals(columnField)) {
					// 主辅料  当填写的值是“true”或“主料”或“是”的时候，主辅料为true
					Boolean isMainImg = false;
					try {
						isMainImg = Boolean.valueOf(value);
					} catch (Exception e) {
//						err = err + "[" + value + "]   主辅料不合法\n";
//						continue;
					}
					if(value!=null&&("是".equals(value.trim())
							||"主料".equals(value.trim())||isMainImg)){
						img.setIsMainImg(true);
					}else{
						img.setIsMainImg(false);
					}
				} else if ("contractImg.unitWeight".equals(columnField)) {
					// 单位重量
					Double wight = 0.0;
					try {
						wight = Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单位重量不合法\n";
						continue;
					}
					img.setUnitWeight(wight);
				} else if ("contractImg.country.name".equals(columnField)) {
					// 原产国
					if (value != null && !"".equals(value.trim())) {
						if (hsCountry.get(value.trim()) != null) {
							Country u = (Country) hsCountry.get(value.trim());
							img.setCountry(u);
						} else {
							err = err + "[" + value + "]   原产地不存在\n";
						}
					}
				} else if ("contractImg.levyMode.name".equals(columnField)) {
					// 增减免税方式
					if (value != null && !"".equals(value.trim())) {
						if (hsLevyMode.get(value.trim()) != null) {
							LevyMode u = (LevyMode) hsLevyMode
									.get(value.trim());
							img.setLevyMode(u);
						} else {
							err = err + "[" + value + "]   增减免税方式不存在\n";
						}
					}
				}
				// if ("contractImg.restriction".equals(columnField)) {
				// // 限制类标志
				// try {
				// Double.valueOf(value);
				// } catch (Exception e) {
				// err = err + "[" + value + "] 限制类标志不合法/";
				// continue;
				// }
				// img.setRestriction(Double.valueOf(value).intValue());
				// }
				else if ("contractImg.note".equals(columnField)) {
					// 备注
					img.setNote(value);
				}
			}
			img.setTotalPrice((img.getAmount() == null ? Double.valueOf(0)
					: img.getAmount())
					* (img.getDeclarePrice() == null ? Double.valueOf(0) : img
							.getDeclarePrice()));
			img.setContract(head);
			img.setCompany(CommonVars.getCurrUser().getCompany());
			obj.setContractImg(img);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	private List parseTxtFileExg(String dictPorEmsNo,
			BcsParameterSet bcsParameterSet) {
		// add by xxm 2007-11-27
		Hashtable<Integer, BcsDictPorExg> exgHs = new Hashtable<Integer, BcsDictPorExg>();
		if (isEms) {// 电子化手册
			List headLs = bcsDictPorAction.findBcsDictPorHead(new Request(
					CommonVars.getCurrUser()), dictPorEmsNo,
					DeclareState.PROCESS_EXE);
			if (headLs != null && headLs.size() > 0) {
				BcsDictPorHead head = (BcsDictPorHead) headLs.get(0);
				List exgLs = bcsDictPorAction.findBcsDictPorExgByHead(
						new Request(CommonVars.getCurrUser()), head);
				for (int k = 0; k < exgLs.size(); k++) {
					BcsDictPorExg exg = (BcsDictPorExg) exgLs.get(k);
					if ((ModifyMarkState.UNCHANGE).equals(exg.getModifyMark())) {// 未修改
						exgHs.put(exg.getSeqNum(), exg);
					}
				}
			} else {
				JOptionPane.showMessageDialog(DgContractInput.this, "没有正在执行的"
						+ dictPorEmsNo + "备案资料库", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return new ArrayList();
			}
		}

		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		List unitList = CustomBaseList.getInstance().getUnits();
		List countryList = CustomBaseList.getInstance().getCountrys();
		List levyModeList = CustomBaseList.getInstance().getLevymode();
		//名称、规格限定长度
		List bytesLength = customBaseAction.findBytesLength(new Request(
				CommonVars.getCurrUser()));
		String strBytesLength = String.valueOf(bytesLength.get(0));
		//是否名称、规格限定长度
		List isControlLength = customBaseAction.findIsControlLength(new Request(
				CommonVars.getCurrUser()));
		String strIsControlLength = String.valueOf(isControlLength.get(0));
		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		Hashtable<String, Country> hsCountry = new Hashtable<String, Country>();
		Hashtable<String, LevyMode> hsLevyMode = new Hashtable<String, LevyMode>();

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
				.getColumnFieldIndex(InputTableColumnSet.CONTRACT_EXG_INPUT);
		int zcount = lsIndex.size();// 15; // 字段数目
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

			TempContractExg obj = new TempContractExg();
			ContractExg exg = new ContractExg();
			String err = "";

			// int zcount = 15;// 字段数目

			Complex complexCode = null;
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("contractExg.seqNum".equals(columnField)) {
					// 料件序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   序号不合法\n";
						continue;
					}
					if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
						err = err + "[" + value + "]   序号重复\n";
					} else {
						lsSeqNum.add(Double.valueOf(value).intValue());
					}
					exg.setSeqNum(Double.valueOf(value).intValue());
				} else if ("contractExg.credenceNo".equals(columnField)) {
					// 记录号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   记录号不合法\n";
						continue;
					}
					Integer credenceNo = Double.valueOf(value).intValue();
					if (dictPorEmsNo != null && !"".equals(dictPorEmsNo.trim())) {
						if (exgHs.get(credenceNo) == null) {
							err = err + "[" + value + "]   记录号" + credenceNo
									+ "在备案资料库中不存在/";
						}
					}
					exg.setCredenceNo(credenceNo);
				} else if ("contractExg.complex.code".equals(columnField)) {
					// 商品编码
					if(isWrite&&(exg.getCredenceNo()!=null)&&(exgHs.get(exg.getCredenceNo())!=null)){
						exg.setComplex(exgHs.get(exg.getCredenceNo()).getComplex());
					}else{
						if (hs.get(value) == null) {
							err = err + "[" + value + "]   不正确的商品编码\n";
						} else {
							Complex c = (Complex) hs.get(value);
							exg.setComplex(c);
						}
					}
				} else if ("contractExg.name".equals(columnField)) {//商品名称
					if(isWrite&&(exg.getCredenceNo()!=null)&&(exgHs.get(exg.getCredenceNo())!=null)){
						exg.setName(exgHs.get(exg.getCredenceNo()).getCommName());
					}else{
						if (value != null && !value.equals("")) {
							if(strIsControlLength.equals("false")){
							exg.setName(value);
							}else{
								if(value.toString().length() > Integer.parseInt(strBytesLength)){
									err = err + "商品名称长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
								}
								exg.setName(value);
							}
//							if (bcsParameterSet != null
//									&& bcsParameterSet.getIsControlLength() != null
//									&& bcsParameterSet.getIsControlLength()) {
//								// 参数设置,是否要控制名称、规格的长度
//								len = bcsParameterSet.getBytesLength() == null ? 50
//										: bcsParameterSet.getBytesLength();
//							}
//							if (value.getBytes().length > len) {
//								err = err + "[" + value + "]   名称长度超过了设置的长度" + len
//										+ "/";
//							}
						} else {
							err = err + "[" + value + "]   名称不可为空\n";
						}
					}
				} else if ("contractExg.spec".equals(columnField)) {//商品规格
					if(isWrite&&(exg.getCredenceNo()!=null)&&(exgHs.get(exg.getCredenceNo())!=null)){
						exg.setSpec(exgHs.get(exg.getCredenceNo()).getCommSpec());
					}else{
						if (value != null && !value.equals("")) {
							if(strIsControlLength.equals("false")){
							exg.setSpec(value);
							}else{
								if(value.toString().length() > Integer.parseInt(strBytesLength)){
									err = err + "商品规格长度超过了设置的长度" + Integer.parseInt(strBytesLength) + "/";
								}
								exg.setSpec(value);
							}
//							if (bcsParameterSet != null
//									&& bcsParameterSet.getIsControlLength() != null
//									&& bcsParameterSet.getIsControlLength()) {
//								// 参数设置,是否要控制名称、规格的长度
//								len = bcsParameterSet.getBytesLength() == null ? 50
//										: bcsParameterSet.getBytesLength();
//							}
//							if (value.getBytes().length > len) {
//								err = err + "[" + value + "]   规格长度超过了设置的长度" + len
//										+ "/";
//							}
						}
					}
				} else if ("contractExg.unit.name".equals(columnField)) {
					// 单位
					if(isWrite&&(exg.getCredenceNo()!=null)&&(exgHs.get(exg.getCredenceNo())!=null)){
						exg.setUnit(exgHs.get(exg.getCredenceNo()).getComUnit());
					}else{
						if (value == null || "".equals(value)) {
							err = err + "[" + value + "]   单位不为空\n";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								exg.setUnit(u);
							} else {
								err = err + "[" + value + "]   单位不存在\n";
							}
						}
					}
				} else if ("contractExg.unitPrice".equals(columnField)) {
					// 单价
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单价不合法\n";
						continue;
					}
					exg.setUnitPrice(Double.valueOf(value));
				} else if ("contractExg.exportAmount".equals(columnField)) {
					// 备案数量
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   备案数量不合法\n";
						continue;
					}
					exg.setExportAmount(Double.valueOf(value));
				} else if ("contractExg.processUnitPrice".equals(columnField)) {
					// 加工费单价
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   加工费单价不合法\n";
						continue;
					}
					exg.setProcessUnitPrice(Double.valueOf(value));
				} else if ("contractExg.materialFee".equals(columnField)) {
					// 原料费
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   原料费不合法\n";
						continue;
					}
					exg.setMaterialFee(Double.valueOf(value));
				} else if ("contractExg.unitGrossWeight".equals(columnField)) {
					// 单位毛重
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单位毛重不合法\n";
						continue;
					}
					exg.setUnitGrossWeight(Double.valueOf(value));
				} else if ("contractExg.unitNetWeight".equals(columnField)) {
					// 单位净重
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单位净重不合法\n";
						continue;
					}
					exg.setUnitNetWeight(Double.valueOf(value));
				} else if ("contractExg.country.name".equals(columnField)) {
					// 消费国
					if (value != null && !"".equals(value.trim())) {
						if (hsCountry.get(value.trim()) != null) {
							Country u = (Country) hsCountry.get(value.trim());
							exg.setCountry(u);
						} else {
							err = err + "[" + value + "]   消费国不存在\n";
						}
					}
				} else if ("contractExg.levyMode.name".equals(columnField)) {
					// 增减免税方式
					if (value != null && !"".equals(value.trim())) {
						if (hsLevyMode.get(value.trim()) != null) {
							LevyMode u = (LevyMode) hsLevyMode
									.get(value.trim());
							exg.setLevyMode(u);
						} else {
							err = err + "[" + value + "]   增减免税方式不存在\n";
						}
					}
				}
				// if ("contractExg.restriction".equals(columnField)) {
				// // 限制类标志
				// try {
				// Double.valueOf(value);
				// } catch (Exception e) {
				// err = err + "[" + value + "] 限制类标志不合法/";
				// continue;
				// }
				// exg.setRestriction(Double.valueOf(value).intValue());
				// }
				else if ("contractExg.note".equals(columnField)) {
					// 备注
					exg.setNote(value);
				} else if ("contractExg.dutyRateString".equals(columnField)) {
					Double dutyRate = DutyRateType.getDutyRateStrint(value);
					if (dutyRate == 0) {
						err = err + "[" + value + "]   申报方式不存在\n";
					} else {
						exg.setDutyRate(dutyRate);
					}
				}
			}
			exg.setTotalPrice((exg.getExportAmount() == null ? Double
					.valueOf(0) : exg.getExportAmount())
					* (exg.getDeclarePrice() == null ? Double.valueOf(0) : exg
							.getDeclarePrice()));
			exg.setContract(head);
			exg.setCompany(CommonVars.getCurrUser().getCompany());
			obj.setContractExg(exg);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	private List parseTxtFileBom() {
		Hashtable<Integer, ContractExg> hsexg = new Hashtable<Integer, ContractExg>();
		Hashtable<Integer, ContractImg> hsimg = new Hashtable<Integer, ContractImg>();
		List listimg = contractAction.findContractImgByParentId(new Request(
				CommonVars.getCurrUser()), head.getId());
		List listexg = contractAction.findContractExgByParentId(new Request(
				CommonVars.getCurrUser()), head.getId());
		for (int i = 0; i < listexg.size(); i++) {
			ContractExg c = (ContractExg) listexg.get(i);
			if (c.getSeqNum() != null) {
				hsexg.put(c.getSeqNum(), c);
			}
		}
		for (int i = 0; i < listimg.size(); i++) {
			ContractImg u = (ContractImg) listimg.get(i);
			if (u.getSeqNum() != null) {
				hsimg.put(u.getSeqNum(), u);
			}
		}
		// int zcount = 4;// 字段数目
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

		List resultlist = new ArrayList();
		List<String>mulList=new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.CONTRACT_BOM_INPUT);
		int zcount = lsIndex.size();// 4; // 字段数目
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
			TempContractBom obj = new TempContractBom();
			ContractBom bom = new ContractBom();
			String err = "";
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("contractBom.contractExg.seqNum".equals(columnField)) {
					// 成品序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   成品序号不合法\n";
						continue;
					}
					if (hsexg.get(Double.valueOf(value).intValue()) != null) {
						ContractExg emsexg = (ContractExg) hsexg.get(Double
								.valueOf(value).intValue());
						bom.setContractExg(emsexg);
					} else {
						err = err + "[" + Double.valueOf(value).intValue()
								+ "]   成品序号不存在\n";
					}
				} else if ("contractBom.contractImgSeqNum".equals(columnField)) {
					// 料件序号
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   料件序号不合法\n";
						continue;
					}
					if (hsimg.get(Double.valueOf(value).intValue()) != null) {
						ContractImg emsimg = (ContractImg) hsimg.get(Double
								.valueOf(value).intValue());
						bom.setComplex(emsimg.getComplex());
						bom.setName(emsimg.getName());
						bom.setSpec(emsimg.getSpec());
						bom.setUnit(emsimg.getUnit());
						bom.setCountry(emsimg.getCountry());
						bom.setContractImgSeqNum(emsimg.getSeqNum());
						bom.setImgCredenceNo(emsimg.getCredenceNo());
						bom.setIsMainImg(emsimg.getIsMainImg());
					} else {
						err = err + "[" + Double.valueOf(value).intValue()
								+ "]   料件序号不存在\n";
					}
				} else if ("contractBom.unitWaste".equals(columnField)) {
					// 单耗
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   单耗不合法\n";
						continue;
					}
					bom.setUnitWaste(Double.valueOf(value));
				} else if ("contractBom.waste".equals(columnField)) {
					// 损耗
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   损耗不合法\n";
						continue;
					}
					bom.setWaste(Double.valueOf(value));
				} else if ("contractBom.nonMnlRatio".equals(columnField)) {
					try {
						// 非保税料件比例%
						bom.setNonMnlRatio(Double.valueOf(value));
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   非保税料件比例%不合法\n";
						continue;
					}
					if (CommonUtils.getDoubleByDigit(Double.valueOf(value),5).doubleValue() >=100
							|| CommonUtils.getDoubleByDigit(Double.valueOf(value),5).doubleValue() < 0) {
						err = err + "[" + value + "]   非保税料件比例范围大于等于0且小于100\n";
						continue;
					}

				}
			}
			bom.setUnitDosage((bom.getUnitWaste() == null ? Double.valueOf(0)
					: bom.getUnitWaste())
					/ (1 - ((bom.getWaste() == null || Double.valueOf(100.0)
							.equals(bom.getWaste())) ? Double.valueOf(0) : bom
							.getWaste() / 100.0)));
			bom.setCompany(CommonVars.getCurrUser().getCompany());
			obj.setContractBom(bom);
			String allSeqNum=obj.getContractBom().getContractExg()==null?"":obj.getContractBom().getContractExg().getSeqNum()
					+"/"+obj.getContractBom().getContractImgSeqNum();
			if(mulList.contains(allSeqNum)){
				err = err + "成品序号[" +obj.getContractBom()==null?"":(obj.getContractBom().getContractExg()==null?"":obj.getContractBom().getContractExg().getSeqNum())+ "]与料件序号["+obj.getContractBom().getContractImgSeqNum()+"]存在重复记录\n";
			}else{
				mulList.add(obj.getContractBom().getContractExg()==null?"":obj.getContractBom().getContractExg().getSeqNum()+"/"+obj.getContractBom().getContractImgSeqNum());	
			}
			obj.setErrinfo(err);
			resultlist.add(obj);
		}
		return resultlist;
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
					new SavaData().start();
//					List ls = null;
//					if (jTabbedPane.getSelectedIndex() == 0) {
//						ls = tableModelImg.getList();
//						for (int i = 0; i < ls.size(); i++) {
//							TempContractImg obj = (TempContractImg) ls.get(i);
//
//							if (obj.getErrinfo() != null
//									&& !obj.getErrinfo().equals("")) {
//								JOptionPane.showMessageDialog(
//										DgContractInput.this, "有错误信息，不可保存！",
//										"提示!", 2);
//								return;
//							}
//						}
//						contractAction.saveBcsImgFromImport(new Request(
//								CommonVars.getCurrUser()), ls, cbIsOverwrite
//								.isSelected());
//						initTableImg(new Vector());
//						JOptionPane.showMessageDialog(DgContractInput.this,
//								"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);
//
//					} else if (jTabbedPane.getSelectedIndex() == 1) {
//						ls = tableModelExg.getList();
//						for (int i = 0; i < ls.size(); i++) {
//							TempContractExg obj = (TempContractExg) ls.get(i);
//
//							if (obj.getErrinfo() != null
//									&& !obj.getErrinfo().equals("")) {
//								JOptionPane.showMessageDialog(
//										DgContractInput.this, "有错误信息，不可保存！",
//										"提示!", 2);
//								return;
//							}
//						}
//						contractAction.saveBcsExgFromImport(new Request(
//								CommonVars.getCurrUser()), ls, cbIsOverwrite
//								.isSelected());
//						initTableExg(new Vector());
//						JOptionPane.showMessageDialog(DgContractInput.this,
//								"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);
//					} else {
//						ls = tableModelBom.getList();
//						for (int i = 0; i < ls.size(); i++) {
//							TempContractBom obj = (TempContractBom) ls.get(i);
//							if (obj.getErrinfo() != null
//									&& !obj.getErrinfo().equals("")) {
//								JOptionPane.showMessageDialog(
//										DgContractInput.this, "有错误信息，不可保存！",
//										"提示!", 2);
//								return;
//							}
//						}
//						contractAction.saveContractEmsBomFromImport(
//								new Request(CommonVars.getCurrUser()), ls,
//								cbIsOverwrite.isSelected());
//						initTableBom(new Vector());
//
//						JOptionPane.showMessageDialog(DgContractInput.this,
//								"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);
//					}

				}
			});
		}
		return jButton1;
	}
	
	/**
	 * 保存数据
	 */
	private class SavaData extends Thread {
		public void run() {
			String taskId = CommonStepProgress.getExeTaskId();
			CommonStepProgress.showStepProgressDialog(taskId);
			CommonStepProgress.setStepMessage("系统正在保存数据,请稍等...");
			try {
				Request request = new Request(CommonVars.getCurrUser());
				request.setTaskId(taskId);
				List ls = null;
				if (jTabbedPane.getSelectedIndex() == 0) {
					ls = tableModelImg.getList();
					for (int i = 0; i < ls.size(); i++) {
						TempContractImg obj = (TempContractImg) ls.get(i);

						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgContractInput.this, "有错误信息，不可保存！",
									"提示!", 2);
							return;
						}
					}
					contractAction.saveBcsImgFromImport(request, ls, cbIsOverwrite
							.isSelected());
					initTableImg(new Vector());
					JOptionPane.showMessageDialog(DgContractInput.this,
							"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);

				} else if (jTabbedPane.getSelectedIndex() == 1) {
					ls = tableModelExg.getList();
					for (int i = 0; i < ls.size(); i++) {
						TempContractExg obj = (TempContractExg) ls.get(i);

						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgContractInput.this, "有错误信息，不可保存！",
									"提示!", 2);
							return;
						}
					}
					contractAction.saveBcsExgFromImport(request, ls, cbIsOverwrite
							.isSelected());
					initTableExg(new Vector());
					JOptionPane.showMessageDialog(DgContractInput.this,
							"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);
				} else {
					ls = tableModelBom.getList();
					for (int i = 0; i < ls.size(); i++) {
						TempContractBom obj = (TempContractBom) ls.get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgContractInput.this, "有错误信息，不可保存！",
									"提示!", 2);
							return;
						}
					}
					contractAction.saveContractEmsBomFromImport(request, ls,
							cbIsOverwrite.isSelected());
					initTableBom(new Vector());

					JOptionPane.showMessageDialog(DgContractInput.this,
							"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);
				}
			} catch (Exception e) {
				CommonStepProgress.closeStepProgressDialog();
				e.printStackTrace();
			}finally{
				CommonStepProgress.closeStepProgressDialog();
			}
			
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
			jButton2.setText("退    出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgContractInput.this.dispose();
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

	public Contract getHead() {
		return head;
	}

	public void setHead(Contract head) {
		this.head = head;
	}

	public boolean isEms() {
		return isEms;
	}

	public void setEms(boolean isEms) {
		this.isEms = isEms;
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
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	/**
	 * This method initializes btnColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("栏位设定");
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.CONTRACT_IMG_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableImg(new ArrayList());
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.CONTRACT_EXG_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableExg(new ArrayList());
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.CONTRACT_BOM_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableBom(new ArrayList());
						}
					}
				}
			});
		}
		return btnColumn;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
