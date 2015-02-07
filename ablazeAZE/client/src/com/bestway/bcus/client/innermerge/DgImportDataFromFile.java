/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.innermerge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableColumn;

import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.innermerge.action.CommonBaseCodeAction;
import com.bestway.bcus.innermerge.entity.InnerMergeData;
import com.bestway.bcus.innermerge.entity.InnerMergeFileData;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * @author lxr 2008-10-25
 * 
 */
@SuppressWarnings("unchecked")
public class DgImportDataFromFile extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private CommonBaseCodeAction commonBaseCodeAction = null;
	private MaterialManageAction materialManageAction = null;

	private File txtFile = null;

	private boolean ok = false;

	private String materielType = null; // @jve:decl-index=0:

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton btnColumn = null;

	private JButton jButton2 = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbUpperCase = null;

	private JCheckBox cbCheckTitle = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane2 = null;

	private JTable jTable2 = null;
	private List afterList = null;
	private JTableListModel tableModelImg = null;

	private JTableListModel tableModelExg = null;

	private JTableListModel tableModelFinishedproduct = null;
	List listData10 = new ArrayList(); // @jve:decl-index=0:
	List listData4 = new ArrayList(); // @jve:decl-index=0:

	private JButton btnDelete = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgImportDataFromFile() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		commonBaseCodeAction = (CommonBaseCodeAction) CommonVars
				.getApplicationContext().getBean("commonBaseCodeAction");
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.EMSEDIMERGER_EXG_INPUT,
				this.getDefaultTableColumnList());
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.EMSEDIMERGER_IMG_INPUT,
				this.getDefaultTableColumnList());
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.EMSEDIMERGER_FINISHEDPRODUCT_INPUT,
				this.getDefaultTableColumnList());
		initTableImg(new ArrayList());
		initTableExg(new ArrayList());
		initTableFinishedproduct(new ArrayList());
	}

	private void initTableExg(List list) {
		tableModelExg = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.EMSEDIMERGER_EXG_INPUT);
					}
				});
		/*
		 * jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
		 * .setCellRenderer(new TableMultiRowRender());
		 */
		TableColumn column = jTable.getColumnModel().getColumn(
				jTable.getColumnCount() - 1);
		column.setCellRenderer(new DefaultTableCellRenderer());
		int index = getIsMainImgIndex(tableModelExg);
		if(index!=-1){
			jTable.getColumnModel().getColumn(index).setCellRenderer(new TableCellRenderer());
		}
	}

	private void initTableImg(List list) {
		tableModelImg = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.EMSEDIMERGER_IMG_INPUT);
					}
				});
		/*
		 * jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1)
		 * .setCellRenderer(new TableMultiRowRender());
		 */

		TableColumn column = jTable1.getColumnModel().getColumn(
				jTable1.getColumnCount() - 1);
		column.setCellRenderer(new DefaultTableCellRenderer());
		int index = getIsMainImgIndex(tableModelImg);
		if(index!=-1){
			jTable1.getColumnModel().getColumn(index).setCellRenderer(new TableCellRenderer());
		}
	}

	private int getIsMainImgIndex(JTableListModel tableModel){
		for (int i = 1; i < tableModel.getColumnCount(); i++) {
			String propertyName = tableModel.getColumnProperty(i);
			if("isMainImg".equals(propertyName)){
				return i;
			}
		}
		return -1;
	}
	
	private void initTableFinishedproduct(List list) {
		tableModelFinishedproduct = new JTableListModel(jTable2, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.EMSEDIMERGER_FINISHEDPRODUCT_INPUT);
					}
				});
		/*
		 * jTable2.getColumnModel().getColumn(jTable2.getColumnCount() - 1)
		 * .setCellRenderer(new TableMultiRowRender());
		 */
		TableColumn column = jTable2.getColumnModel().getColumn(
				jTable2.getColumnCount() - 1);
		column.setCellRenderer(new DefaultTableCellRenderer());
		int index = getIsMainImgIndex(tableModelImg);
		if(index!=-1){
			jTable2.getColumnModel().getColumn(index).setCellRenderer(new TableCellRenderer());
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("内部归并数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(753, 513);

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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				long beginTime = System.currentTimeMillis();
				String info = "";
				CommonProgress.showProgressDialog(DgImportDataFromFile.this);
				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				afterList = parseTxtFile(txtFile);
				CommonProgress.closeProgressDialog();
				info += " 数据导入完成,共用时:"
						+ (System.currentTimeMillis() - beginTime) + " 毫秒 ";
				JOptionPane.showMessageDialog(DgImportDataFromFile.this, info,
						"提示", 2);
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				throw new RuntimeException("导入数据失败 ", ex);
			} finally {
			}
			return afterList;
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
			if (jTabbedPane.getSelectedIndex() == 1) {
				initTableImg(list);
			} else if (jTabbedPane.getSelectedIndex() == 0) {
				initTableExg(list);
			} else {
				initTableFinishedproduct(list);
			}
		}
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		// System.out.println("--"+dataIndex+"="+fileRow[dataIndex]);
		return fileRow[dataIndex];
	}

	
	
	
	
	private String[] changStrs(String[] source) {
		
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {

			char[] c1 = source[i].toCharArray();
			char[] c2 = new char[c1.length];
			//去除（空格）字符 二进制 为10100000 的字符 ,此字符 不是真正意义上的 空格 
			for(int x = 0; x < c1.length; x ++){
				if (!"10100000".equals(Integer.toBinaryString(c1[x]))){
					c2[x] = c1[x];
				}
			}
			source[i] = new String(c2).trim();
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}



	/**
	 * 检查是否存在繁体表
	 * 
	 * @return
	 */
	private String[] checkStrs(String[] source) {
		
		String newStrs1[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			
			char[] c1 = source[i].toCharArray();
			char[] c2 = new char[c1.length];
			//去除（空格）字符 二进制 为10100000 的字符 ,此字符 不是真正意义上的 空格 
			for(int x = 0; x < c1.length; x ++){
				if (!"10100000".equals(Integer.toBinaryString(c1[x]))){
					c2[x] = c1[x];
				}
			}
			source[i] = new String(c2).trim();
			//System.out.println(c2.toString() +"  checkStrs  >>>>>>>>>>>>>>>>>>      "+source[i]+" >>>>>>>>. "+Integer.toBinaryString(' '));
			newStrs1[i] = CommonClientBig5GBConverter.getInstance().getNoBig5(
					source[i]);
		}
		return newStrs1;
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	private List parseTxtFile(File file) {

		// List complexlist = commonBaseCodeAction
		// .findComplexLegalUnit(new Request(CommonVars.getCurrUser()));
		// 工厂单位
		List unitList = materialManageAction.findCalUnit(new Request(CommonVars
				.getCurrUser(), true));
		List hsUnitList = CustomBaseList.getInstance().getUnits();
		List<String> lsSeqNum = new ArrayList<String>();
		ArrayList<InnerMergeFileData> list = new ArrayList<InnerMergeFileData>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		for (int i = 0; i < hsUnitList.size(); i++) {
			Unit u = (Unit) hsUnitList.get(i);
			hsUnit.put(u.getName().trim().toLowerCase(), u);
			System.out.println(u.getCode() + "=" + u.getName());
		}

		Hashtable<String, CalUnit> calUnit = new Hashtable<String, CalUnit>();
		for (int i = 0; i < unitList.size(); i++) {
			CalUnit u = (CalUnit) unitList.get(i);
			if (u.getName() == null) {
				continue;
			}
			calUnit.put(u.getName().trim().toLowerCase(), u);
			// System.out.println(u.getCode() + "=" + u.getName());
		}

		String suffix = getSuffix(file);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//
			System.out.println("XSL 导入 >>>>>>>>>>>>>>>>>>>  ");
			lines = FileReading.readExcel(file, 0, null);
		} else {
			//
			// 导入txt
			//
			System.out.println("TXT 导入 >>>>>>>>>>>>>>>>>>>  ");
			lines = FileReading.readTxt(file, 0, "GBK");
		}
		List<String> lsIndex = null;
		if (jTabbedPane.getSelectedIndex() == 0) {
			lsIndex = InputTableColumnSetUtils
					.getColumnFieldIndex(InputTableColumnSet.EMSEDIMERGER_EXG_INPUT);
		} else if (jTabbedPane.getSelectedIndex() == 1) {
			lsIndex = InputTableColumnSetUtils
					.getColumnFieldIndex(InputTableColumnSet.EMSEDIMERGER_IMG_INPUT);
		} else {
			lsIndex = InputTableColumnSetUtils
					.getColumnFieldIndex(InputTableColumnSet.EMSEDIMERGER_FINISHEDPRODUCT_INPUT);
		}

		Map<String,String> map = new HashMap<String,String>();
		
		int zcount = lsIndex.size();
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
					+ i + "】行,请稍等...");
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			String[] info = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}

			String err = "";
			if (getCbJF().isSelected()) {
				strs = changStrs(strs);
				info = checkStrs(strs);
			}

			InnerMergeFileData data = new InnerMergeFileData();
			for (int j = 0; j < zcount; j++) {

				String value = getFileColumnValue(strs, j);
				String infoErr = getFileColumnValue(info, j);
				String columnField = lsIndex.get(j);
				if (jTabbedPane.getSelectedIndex() == 0) {
					data.setMaterielType(MaterielType.FINISHED_PRODUCT);
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					data.setMaterielType(MaterielType.MATERIEL);
				} else {
					data.setMaterielType(MaterielType.SEMI_FINISHED_PRODUCT);
				}
				if (infoErr != null && !"".equals(infoErr)) {
					err = err + "[" + infoErr + "]在繁简体对照表中不存在，请在海关基础资料-基础代码模块中补充/";
				}
				if ("beforeMaterielCode".equals(columnField)) {// 料号
					if (value != null && !value.equals("")) {
						if (cbUpperCase.isSelected()) { // 料号去尾空格并转为大写
							data.setBeforeMaterielCode(value.trim()
									.toUpperCase());
						} else {
							data.setBeforeMaterielCode(value);
						}
						if (lsSeqNum.contains(data.getBeforeMaterielCode())) {
							err = err + "[" + value + "]文件中料号重复/";
						} else {
							lsSeqNum.add(data.getBeforeMaterielCode());
							List innerMergeDatas = commonBaseCodeAction
									.findInnerMergeDataByPtNo(new Request(
											CommonVars.getCurrUser()), data
											.getBeforeMaterielCode());
							if (innerMergeDatas.size() > 0) {
								Iterator it = innerMergeDatas.iterator();
								String innerMergeDataByPtNo = "";
								while (it.hasNext()) {
									InnerMergeData innerMergeData = (InnerMergeData) it
											.next();
									innerMergeDataByPtNo += innerMergeData
											.getHsAfterTenMemoNo();
								}
								err = err + "料号已经在内部归并里存在，与备案序号"
										+ innerMergeDataByPtNo + "重复/";
							}
						}
					} else {
						err = err + "[" + value + "]料号不可为空/";
					}
				} else if ("beforeMaterielName".equals(columnField)) { // 工厂位商品名称
					if (value != null && !value.equals("")) {
						data.setBeforeMaterielName(value);
					} else {
						err = err + "[" + value + "]工厂位商品名称不可为空/";
					}
				} else if ("beforeMaterielSpec".equals(columnField)) {// 工厂位商品规格
					if (value != null && !value.equals("")) {
						data.setBeforeMaterielSpec(value);
					}
				} else if ("beforeEnterpriseUnit".equals(columnField)) {// 工厂单位
					if (value != null && !value.equals("")) {
						CalUnit u = (CalUnit) calUnit.get(value.trim()
								.toLowerCase());
						if (u == null) {
							err = err + "[" + value + "]工厂单位在单位表中不存在/";
						} else {
							data.setBeforeEnterpriseUnit(u.getName());
						}
					}
				} else if ("afterTenMemoNo".equals(columnField)) {// 备案序号
					if (value != null && !value.equals("")) {
						if (!value.matches("\\d*")) {
							err = err + "[" + value + "]备案序号只能为整数/";
						} else {
							data.setAfterTenMemoNo(value);
							listData10 = commonBaseCodeAction
									.findInnerMergeDataByTenNoFromFile(
											new Request(CommonVars
													.getCurrUser()), data
													.getMaterielType(), value);
						}
					} else {
						err = err + "[" + value + "]备案序号不可为空/";
					}
				} else if ("afterTenComplexCode".equals(columnField)) {// 商品编码
					if (value != null && !"".equals(value)) {
						List listComlex = commonBaseCodeAction
								.findComplexLegalUnit(
										new Request(CommonVars.getCurrUser()),
										value);
						// if(list)
						// Complex c = (Complex) hs.get(value);
						if (listComlex == null || listComlex.size() <= 0) {
							err = err + "[" + value + "]归并后海关商品编码库不存在/";
						} else {
							Complex c = (Complex) listComlex.get(0);
							data.setAfterTenComplexCode(c.getCode());
							if (data.getAfterTenMemoNo() != null) {
								err = err
										+ check10FileData(
												data.getAfterTenMemoNo(),
												c.getCode(),
												"afterTenComplexCode");
							}
						}

					} else {
						err = err + "[" + value + "]   商品编码不可为空/";
					}
				} else if ("afterComplexlName".equals(columnField)) {// 10商品名称
					if (value != null && !"".equals(value)) {
						data.setAfterComplexlName(value);
						if (data.getAfterTenMemoNo() != null) {
							err = err
									+ check10FileData(data.getAfterTenMemoNo(),
											value, "afterComplexlName");
						}
					} else {
						err = err + "[" + value + "]10商品名称不可为空/";
					}
				} else if ("afterComplexSpec".equals(columnField)) { // 10商品规格
					if (value != null && !"".equals(value)) {
						data.setAfterComplexSpec(value);
						if (data.getAfterTenMemoNo() != null) {
							err = err
									+ check10FileData(data.getAfterTenMemoNo(),
											value, "afterComplexSpec");
						}
					} else {
						err = err + "[" + value + "]报关商品规格不可为空或为'/'/";
						continue;
					}
				} else if ("afterMemoUnit".equals(columnField)) {// 备案单位
					if (value != null && !value.equals("")) {
						Unit u = (Unit) hsUnit.get(value);
						if (u == null) {
							err = err + "[" + value + "]备案单位在单位表中不存在/";
						} else {
							data.setAfterMemoUnit(u.getName());
							if (data.getAfterTenMemoNo() != null) {
								err = err
										+ check10FileData(
												data.getAfterTenMemoNo(),
												u.getName(), "afterMemoUnit");
							}
						}
					} else {
						err = err + "[" + value + "]备案单位不可为空/";
					}
				} else if ("fourNo".equals(columnField)) {// 4位序号
					if (value != null && !value.equals("")) {
						if (!value.matches("\\d*")) {
							err = err + "[" + value + "]4位序号只能为整数/";
						} else {
							data.setFourNo(value);
							listData4 = commonBaseCodeAction
									.findInnerMergeDataByFourNoFromFile(
											new Request(CommonVars
													.getCurrUser()), data
													.getMaterielType(), value);
						}

					} else {
						err = err + "[" + value + "]4位序号不可为空/";
					}
				} else if ("fourComplexName".equals(columnField)) {// 4位商品名称
					if (value != null && !value.equals("")) {
						data.setFourComplexName(value);
						if (data.getFourNo() != null) {
							err = err
									+ check4FileData(data.getFourNo(), value,
											"fourComplexName");
						}
					} else {
						err = err + "[" + value + "]4位商品名称不可为空/";
					}
				} else if ("fourComplexCode".equals(columnField)) {// 4位商编
					if (value != null && !value.equals("")) {
						data.setFourComplexCode(value);
						if (data.getFourNo() != null) {
							err = err
									+ check4FileData(data.getFourNo(), value,
											"fourComplexCode");
						}
					} else {
						err = err + "[" + value + "]4位商编不可为空/";
					}
				} else if ("unitWeight".equals(columnField)) {
					Double unitWeight = 0.0;
					try {
						if (value != null && !value.equals("")) {
							unitWeight = forD(value);
						}
					} catch (Exception e) {
						err = err + "[" + value + "]单重不合法/";
						continue;
					}
					data.setUnitWeight(unitWeight);
				} else if ("unitConvert".equals(columnField)) {
					Double unitConvert = 0.0;
					try {
						if (value != null && !value.equals("")
								&& (Double.parseDouble(value) != 0)) {
							unitConvert = forD(value);
						} else {
							err = err + "[" + value + "]单位折算不能为空或零/";
							continue;
						}
					} catch (Exception e) {
						err = err + "[" + value + "]单位折算不合法/";
						continue;
					}
					data.setUnitConvert(unitConvert);
				} else if ("ptPrice".equals(columnField)) {
					Double ptPrice = 0.0;
					try {
						if (value != null && !value.equals("")) {
							ptPrice = forD(value);
						}
					} catch (Exception e) {
						err = err + "[" + value + "]单价不合法/";
						continue;
					}
					data.setPtPrice(ptPrice);
				}else if("fristUnitRatio".equals(columnField)){
					Double ratio = null;
					try {
						if (value != null && !value.equals("")) {
							ratio = forD(value);
						}
					} catch (Exception e) {
						err = err + "[" + value + "]第一法定单位比例因子不合法/";
						continue;
					}
					data.setFristUnitRatio(ratio);
				}else if("secondUnitRatio".equals(columnField)){
					Double ratio = null;
					try {
						if (value != null && !value.equals("")) {
							ratio = forD(value);
						}
					} catch (Exception e) {
						err = err + "[" + value + "]第二法定单位比例因子不合法/";
						continue;
					}
					data.setSecondUnitRatio(ratio);
				}else if("weigthUnitGene".equals(columnField)){
					Double weigthUnitGene = null;
					try {
						if (value != null && !value.equals("")) {
							weigthUnitGene = forD(value);
						}
					} catch (Exception e) {
						err = err + "[" + value + "]重量单位比例因子不合法/";
						continue;
					}
					data.setWeigthUnitGene(weigthUnitGene);
				}else if("isMainImg".equals(columnField)){
					if(value != null && !value.equals("") && "是".equals(value.toString())){
						data.setIsMainImg(true);
					}else{
						data.setIsMainImg(false);
					}
				}
				
			}
			//key = 备案序号
			String key = data.getAfterTenMemoNo()==null?"":data.getAfterTenMemoNo();
			String afterComplexlName = data.getAfterComplexlName()==null?"":data.getAfterComplexlName();
			String afterComplexSpec = data.getAfterComplexSpec()==null?"":data.getAfterComplexSpec();
			String afterMemoUnit = data.getAfterMemoUnit()==null?"":data.getAfterMemoUnit();
			String firstUnitRatio = data.getFristUnitRatio() == null ? "" : String.valueOf(data.getFristUnitRatio());
			String secondUnitRatio = data.getSecondUnitRatio() == null ? "" : String.valueOf(data.getSecondUnitRatio());
			//value = 名称/规格/单位/
			String value = afterComplexlName +"/" + afterComplexSpec +"/" + afterMemoUnit+"/"+firstUnitRatio+"/"+secondUnitRatio;
			
			if(map.get(key)==null){
				map.put(key, value);
			}else{
				if(!map.get(key).equals(value)){
					err = err + "备案序号相同,[名称][规格][单位][第一法定比例因子][第二法定比例因子]必须相同！";
				}
			}
			
			data.setErrinfo(err);
			list.add(data);
		}
		return list;
	}

	/**
	 * 数据检查十码和类别查找内部归并数据
	 * 
	 * @param list
	 * @param materielType
	 */
	private String check10FileData(String numNo, String value, String columfield) {
		String err = "";
		// 根据十码和类别查找内部归并数据
		Object[] mergeData = null;
		if (!listData10.isEmpty())
			mergeData = (Object[]) listData10.get(0);

		// if (mergeData != null && columfield.equals("afterTenComplexCode")
		// && mergeData[0] == null) {
		// err = "数据库里备案序号" + numNo + "商品编码为空/";
		// return err;
		// } else
		if (mergeData != null && columfield.equals("afterTenComplexCode")) {
			if (mergeData[0] == null) {
				err = "数据库里备案序号" + numNo + "商品编码为空/";
			} else if (!((Complex) mergeData[0]).getCode().equals(value)) {
				err = "归并后海关商品编码”与数据库里备案序号为" + numNo + "对应的不同/";
			}
			return err;
		}
		if (mergeData != null && columfield.equals("afterComplexlName")
				&& !mergeData[1].equals(value)) {
			err = "归并后商品名称”与数据库里备案序号为" + numNo + "对应的不同/";
			return err;
		}
		if (mergeData != null && columfield.equals("afterComplexSpec")
				&& !mergeData[2].equals(value)) {
			err = "归并后型号规格”与数据库里备案序号为" + numNo + "对应的不同/";
			return err;
		}
		if (mergeData != null && columfield.equals("afterMemoUnit")) {
			if (mergeData[3] == null) {
				err = "数据库里备案序号" + numNo + "计量单位为空/";
			} else if (!((Unit) mergeData[3]).getName().equals(value)) {

				err = "计量单位(备案)”与数据库里备案序号为" + numNo + "对应的不同/";
			}
			return err;
		} else {
			return err;
		}
	}

	/**
	 * 检查4码和类别查找内部归并数据
	 * 
	 * @return
	 */
	private String check4FileData(String numNo, String value, String columfield) {
		String err = "";
		// 根据4码和类别查找内部归并数据
		Object[] mergeData = null;
		if (!listData4.isEmpty()) {
			mergeData = (Object[]) listData4.get(0);
		}

		if (mergeData != null && columfield.equals("fourComplexCode")
				&& !mergeData[0].equals(value)) {
			err = "“4位海关商品编码”与数据库里备案序号为" + numNo + "对应的不同/";
			return err;
		}
		if (mergeData != null && columfield.equals("fourComplexName")
				&& !(value).equals(mergeData[1] == null ? "" : mergeData[1])) {
			err = "“4位商品名称”与数据库里备案序号为" + numNo + "对应的不同/";
			return err;
		} else {
			return err;
		}
		// if (mergeData != null && columfield.equals("fourComplexCode")
		// && !mergeData.getHsFourCode().equals(value)) {
		// err = "“4位海关商品编码”与数据库里备案序号为" + numNo + "对应的不同/";
		// return err;
		// }
		// if (mergeData != null && columfield.equals("fourComplexName")
		// && !mergeData.getHsFourMaterielName().equals(value)) {
		// err = "“4位商品名称”与数据库里备案序号为" + numNo + "对应的不同/";
		// return err;
		// } else {
		// return err;
		// }
	}

	private List getDefaultTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("料号(不为空)", "beforeMaterielCode", 100));
		list.add(new JTableListColumn("工厂商品名称(不为空)", "beforeMaterielName", 140));
		list.add(new JTableListColumn("工厂商品规格(不为空)", "beforeMaterielSpec", 140));
		list.add(new JTableListColumn("工厂单位(名称)(不为空)", "beforeEnterpriseUnit",
				140));
		list.add(new JTableListColumn("备案序号(不为空)", "afterTenMemoNo", 100));
		list.add(new JTableListColumn("10位商品编码(不为空)", "afterTenComplexCode",
				150));
		list.add(new JTableListColumn("报关商品名称(不为空)", "afterComplexlName", 150));
		list.add(new JTableListColumn("报关商品规格(不为空或'/')", "afterComplexSpec",
				150));
		list.add(new JTableListColumn("备案单位(名称)(不为空)", "afterMemoUnit", 150));
		list.add(new JTableListColumn("4位编码序号(不为空)", "fourNo", 140));
		list.add(new JTableListColumn("4位商品编码(不为空)", "fourComplexCode", 140));
		list.add(new JTableListColumn("4位商品名称(不为空)", "fourComplexName", 140));
		list.add(new JTableListColumn("单重", "unitWeight", 60));
		list.add(new JTableListColumn("单位折算系数(不为空或零)", "unitConvert", 80));
		list.add(new JTableListColumn("单价", "ptPrice", 60));
		list.add(new JTableListColumn("第一法定单位比例因子", "fristUnitRatio", 80));
		list.add(new JTableListColumn("第二法定单位比例因子", "secondUnitRatio", 80));
		list.add(new JTableListColumn("重量单位比例因子", "weigthUnitGene", 80));
		list.add(new JTableListColumn("是否主料(填是或否)", "isMainImg", 80));
		
		return list;
	}

	private Double forD(String d) throws Exception {
		if (d == null || "".equals(d.trim())) {
			return Double.valueOf(0);
		}
		try {
			return Double.valueOf(d);
		} catch (Exception ex) {
			throw new Exception();
			// return Double.valueOf(0);
		}
	}

	/**
	 * This method initializes buttonGroup
	 * 
	 * @return javax.swing.ButtonGroup
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
	 * @return Returns the ok.
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            The ok to set.
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return Returns the materielType.
	 */
	public String getMaterielType() {
		return materielType;
	}

	/**
	 * @param materielType
	 *            The materielType to set.
	 */
	public void setMaterielType(String materielType) {
		this.materielType = materielType;
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
			jToolBar.add(getBtnDelete());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
			jToolBar.add(getCbUpperCase());
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
			jButton.setPreferredSize(new Dimension(80, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportDataFromFile.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImportFileDataRunnable().execute();

				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.\u4fdd\u5b58\u6570\u636e");
			jButton1.setPreferredSize(new Dimension(80, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileData().start();
				}
			});
		}
		return jButton1;
	}

	class ImportFileData extends Thread {
		public void run() {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress.showProgressDialog(DgImportDataFromFile.this);
				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				List ls = null;
				if (jTabbedPane.getSelectedIndex() == 1) {
					ls = tableModelImg.getList();
				} else if (jTabbedPane.getSelectedIndex() == 0) {
					ls = tableModelExg.getList();
				} else if (jTabbedPane.getSelectedIndex() == 2) {
					ls = tableModelFinishedproduct.getList();
				}
				for (int i = 0; i < ls.size(); i++) {
					InnerMergeFileData obj = (InnerMergeFileData) ls.get(i);
					if (obj.getErrinfo() != null
							&& !obj.getErrinfo().equals("")) {
						JOptionPane.showMessageDialog(
								DgImportDataFromFile.this, obj.getErrinfo() + "有错误信息，不可保存！",
								"提示!", 2);
						CommonProgress.closeProgressDialog();
						return;
					}
				}
				commonBaseCodeAction.importDataFromTxtFile(new Request(
						CommonVars.getCurrUser()), afterList);
				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;
				JOptionPane.showMessageDialog(DgImportDataFromFile.this,
						"导入数据成功！导入数据记录 " + afterList.size() + " 条,共用时 "
								+ totalTime + " 毫秒", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				DgImportDataFromFile.this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataFromFile.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}
		}
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
			btnColumn.setPreferredSize(new Dimension(60, 30));
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.EMSEDIMERGER_EXG_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableExg(new ArrayList());
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.EMSEDIMERGER_IMG_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableImg(new ArrayList());
						}
					} else if (jTabbedPane.getSelectedIndex() == 2) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.EMSEDIMERGER_FINISHEDPRODUCT_INPUT);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableFinishedproduct(new ArrayList());
						}
					}
				}
			});
		}
		return btnColumn;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("退 出");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes cbJF
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
	 * This method initializes cbIsOverwrite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbUpperCase() {
		if (cbUpperCase == null) {
			cbUpperCase = new JCheckBox();
			cbUpperCase.setText("物料去尾空格并转为大写");
		}
		return cbUpperCase;
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
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(JTabbedPane.TOP);
			jTabbedPane.addTab("1.成品", null, getJPanel(), null);
			jTabbedPane.addTab("2.料件", null, getJPanel2(), null);
			jTabbedPane.addTab("3.半成品", null, getJPanel1(), null);

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
	 * This method initializes btnDelete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("删除错误数据");
			btnDelete.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List lsit = new ArrayList();
					List<InnerMergeFileData> list = null;
					if (jTabbedPane.getSelectedIndex() == 0) {
						list = tableModelExg.getList();
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						list = tableModelImg.getList();
					} else {
						list = tableModelFinishedproduct.getList();
					}

					for (InnerMergeFileData data : list) {
						if (data.getErrinfo() != null
								&& !"".equals(data.getErrinfo())) {
							lsit.add(data);
						}
					}
					if (jTabbedPane.getSelectedIndex() == 0) {
						tableModelExg.deleteRows(lsit);
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						tableModelImg.deleteRows(lsit);
					} else {
						tableModelFinishedproduct.deleteRows(lsit);
					}

				}
			});
		}
		return btnDelete;
	}

	private class DefaultTableCellRenderer extends TableMultiRowRender {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			super.getTableCellRendererComponent(table, value, isSelected,
					hasFocus, row, column);
			{
				super.setForeground(Color.red);
			}
			return this;
		}
	}
	
	private class TableCellRenderer extends TableMultiRowRender {
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			String boo = "否";
			if(value!=null&&Boolean.TRUE.equals(Boolean.parseBoolean(value.toString()))){
				boo = "是";
			}
			
			super.getTableCellRendererComponent(table, boo, isSelected,
					hasFocus, row, column);
			
			return this;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Boolean.TRUE.equals(true));
	}
} // @jve:decl-index=0:visual-constraint="38,13"

