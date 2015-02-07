package com.bestway.common.client.materialbase;

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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;

import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.bcus.cas.entity.TempEnterpriseMaterial;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.EnterpriseMaterial;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCoi;
import com.bestway.common.materialbase.entity.ShareCode;
import com.bestway.common.materialbase.entity.SysArea;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 物料主档导入
 * 
 * @author
 * 
 */
public class DgEnterpriseTxtImport extends JDialogBase {
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
	private MaterialManageAction materialManageAction = null;
	private JCheckBox cbReplace = null;
	private JButton btnColumnSet = null;
	private JCheckBox cbCheckTitle = null;
	private JButton btnDeleteError;
	
	private List dataSource = null;

	/**
	 * DgBomTxtImport的构造函数 This is the default constructor
	 */
	public DgEnterpriseTxtImport() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.ENTERPRICE_MATERIAL, this
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
		this.setTitle("物料主档文本导入");
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
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnDeleteError());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getCbReplace());
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
				CommonProgress.showProgressDialog(DgEnterpriseTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				if(list!=null){
					DgEnterpriseTxtImport.this.dataSource = list;
				}else{
					DgEnterpriseTxtImport.this.dataSource = new ArrayList<Object>();
				}
				// List listNotFound =
				// materialManageAction.checkBomImportFileData(
				// new Request(CommonVars.getCurrUser(), true), list,
				// getCbReplace().isSelected());
				// if(!listNotFound.isEmpty())
				// {
				// JOptionPane.showMessageDialog(DgBomTxtImport.this,
				// "在文件中存在异常的资料", "提示",
				// JOptionPane.INFORMATION_MESSAGE);
				// initTable(listNotFound);
				// return;
				// }
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
		// 检查类型是否存在
		// 类别
		List checkList = materialManageAction.findList("ScmCoi");
		Map<String, ScmCoi> scmCoiMap = new HashMap<String, ScmCoi>();
		for (int i = 0; i < checkList.size(); i++) {
			ScmCoi c = (ScmCoi) checkList.get(i);
			if (scmCoiMap.get(c.getName()) == null)
				scmCoiMap.put(c.getName(), c);
		}
		// 编码
		checkList = materialManageAction.findList("Complex");
		Map<String, Complex> complexMap = new HashMap<String, Complex>();
		for (int i = 0; i < checkList.size(); i++) {
			Complex c = (Complex) checkList.get(i);
			if (complexMap.get(c.getCode()) == null)
				complexMap.put(c.getCode(), c);
		}
		// 工厂单位
		checkList = materialManageAction.findList("CalUnit");
		Map<String, CalUnit> calUnitMap = new HashMap<String, CalUnit>();
		for (int i = 0; i < checkList.size(); i++) {
			CalUnit c = (CalUnit) checkList.get(i);
			if (calUnitMap.get(c.getName()) == null)
				calUnitMap.put(c.getName(), c);
		}
		// 报关单位
		checkList = materialManageAction.findList("Unit");
		Map<String, Unit> ptUnitMap = new HashMap<String, Unit>();
		for (int i = 0; i < checkList.size(); i++) {
			Unit c = (Unit) checkList.get(i);
			if (ptUnitMap.get(c.getName()) == null)
				ptUnitMap.put(c.getName(), c);
		}
		// 重量单位与工厂单位一样

		// 供应商
		checkList = materialManageAction.findList("ScmCoc");
		Map<String, ScmCoc> scmCocMap = new HashMap<String, ScmCoc>();
		for (int i = 0; i < checkList.size(); i++) {
			ScmCoc c = (ScmCoc) checkList.get(i);
			if (scmCocMap.get(c.getName()) == null)
				scmCocMap.put(c.getName(), c);
		}
		// 原产国
		checkList = materialManageAction.findList("SysArea");
		Map<String, SysArea> sysAreaMap = new HashMap<String, SysArea>();
		for (int i = 0; i < checkList.size(); i++) {
			SysArea c = (SysArea) checkList.get(i);
			if (sysAreaMap.get(c.getName()) == null)
				sysAreaMap.put(c.getName(), c);
		}
		// 税制代码
		checkList = materialManageAction.findList("ShareCode");
		Map<String, ShareCode> taxationMap = new HashMap<String, ShareCode>();
		for (int i = 0; i < checkList.size(); i++) {
			ShareCode c = (ShareCode) checkList.get(i);
			if (taxationMap.get(c.getName()) == null)
				taxationMap.put(c.getName(), c);
		}
		// 包装种类
		checkList = materialManageAction.findList("Wrap");
		Map<String, Wrap> wrapMap = new HashMap<String, Wrap>();
		for (int i = 0; i < checkList.size(); i++) {
			Wrap c = (Wrap) checkList.get(i);
			if (wrapMap.get(c.getName()) == null)
				wrapMap.put(c.getName(), c);
		}
		// 料号,判断不都能重复用
		checkList = materialManageAction.findAllPtNo();
		Map<String, String> ptNoMap = new HashMap<String, String>();
		for (int i = 0; i < checkList.size(); i++) {
			String c = (String) checkList.get(i);
			if (ptNoMap.get(c) == null)
				ptNoMap.put(c, c);
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
		ArrayList<TempEnterpriseMaterial> list = new ArrayList<TempEnterpriseMaterial>();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.ENTERPRICE_MATERIAL);
		String info = "";
		// 验证不能为空的字段是否存在
		boolean isPtNo = false;
		boolean isTpye = false;
		boolean isUnit = false;
		boolean isName = false;
		for (int i = 0; i < lsIndex.size(); i++) {
			if (lsIndex.get(i).equals("ebm.ptNo")) {
				isPtNo = true;
				continue;
			}
			if (lsIndex.get(i).equals("ebm.scmCoi.name")) {
				isTpye = true;
				continue;
			}
			if (lsIndex.get(i).equals("ebm.calUnit.name")) {
				isUnit = true;
				continue;
			}
			if (lsIndex.get(i).equals("ebm.factoryName")) {
				isName = true;
			}
		}
		if (!isPtNo) {
			info+="栏位设定不能没有料号\n";
		}
		if (!isTpye) {
			info+="栏位设定不能没有物料类型\n";
		}
		if (!isUnit) {
			info+="栏位设定不能没有工厂单位\n";
		}
		if (!isName) {
			info+="栏位设定不能没有工厂品名\n";
		}
		if(!info.equals("")){
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgEnterpriseTxtImport.this, info, "提示信息",0);
			return null;
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
			EnterpriseMaterial ebm = new EnterpriseMaterial();
			TempEnterpriseMaterial obj = new TempEnterpriseMaterial();
			String err = "";
			// 料号
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("ebm.ptNo".equals(columnField)) {
					if (!cbReplace.isSelected()) {// 覆盖
						if (value != null && !value.equals("")) {

							if (ptNoMap.get(value) != null) {
								err = err + "[" + value + "]  料号不能重复/";
							} else {
								ebm.setPtNo(value);
							}
						} else {
							err = err + "[" + value + "]  料号不能为空/";
						}
					} else {
						if (value != null && !value.equals("")) {

							if (ptNoMap.get(value) != null) {
								ebm = materialManageAction
										.findEnterpriseMaterial(CommonUtils
												.getRequest(), value);
							} else {
								ebm.setPtNo(value);

							}
						} else {
							err = err + "[" + value + "]  料号不能为空/";
						}

					}
					// 类别
				} else if ("ebm.scmCoi.name".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						if (scmCoiMap.get(value) != null)
							ebm.setScmCoi(scmCoiMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此类别/";
					} else {
						err = err + "[" + value + "] 类别不能为空/";
					}
					// 商品编码
				} else if ("ebm.complex.code".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						if (complexMap.get(value) != null)
							ebm.setComplex(complexMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此商品编码/";
					}
					// 工厂品名
				} else if ("ebm.factoryName".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						ebm.setFactoryName(value);
					} else {
						err = err + "[" + value + "]  工厂品名不能为空/";
					}
					// 工厂规格
				} else if ("ebm.factorySpec".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						ebm.setFactorySpec(value);
					}
					// 报关名称
				} else if ("ebm.ptName".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						ebm.setPtName(value);
					}

					// 报关规格
				} else if ("ebm.ptSpec".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						ebm.setPtSpec(value);
					}
					// 工厂单位
				} else if ("ebm.calUnit.name".equals(columnField)) {
					System.out.println("columnField=" + columnField);
					System.out.println("value=" + value);
					if (value != null && !value.equals("")) {
						if (calUnitMap.get(value) != null)
							ebm.setCalUnit(calUnitMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此工厂单位/";
					} else {
						err = err + "[" + value + "]  工厂单位不能为空/";
					}
					// 报关单位
				} else if ("ebm.ptUnit.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (ptUnitMap.get(value) != null)
							ebm.setPtUnit(ptUnitMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此报关单位/";
					}
					// 工厂单位与报关单位折算系数
				} else if ("ebm.unitConvert".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setUnitConvert(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 详细型号规格
				} else if ("ebm.ptDeSpec".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setPtDeSpec(value);
					}
					// 单价
				} else if ("ebm.ptPrice".equals(columnField)) {
					if (value != null && !value.equals("")) {

						try {
							ebm.setPtPrice(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 净重
				} else if ("ebm.ptNetWeight".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setPtNetWeight(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}

					}
					// 重量单位
				} else if ("ebm.calWeightUnit.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (calUnitMap.get(value) != null)
							ebm.setCalWeightUnit(calUnitMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此重量单位/";
					}
					// 毛重
				} else if ("ebm.ptOutWeight".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setPtOutWeight(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 版本号
				} else if ("ebm.ptVersion".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setPtVersion(value);
					}
					// 英文品名
				} else if ("ebm.ptEnglishName".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setPtEnglishName(value);
					}
					// 英文详细规格
				} else if ("ebm.ptEnglishSpec".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setPtEnglishSpec(value);
					}
					// 供应商/客户
				} else if ("ebm.scmCoc.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (scmCocMap.get(value) != null)
							ebm.setScmCoc(scmCocMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此供应商或客户/";
					}
					// 原产国
				} else if ("ebm.sysArea.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (sysAreaMap.get(value) != null)
							ebm.setSysArea(sysAreaMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此原产国/";
					}
					// 税制代码
				} else if ("ebm.taxation.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (taxationMap.get(value) != null)
							ebm.setTaxation(taxationMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此税制代码/";
					}
					// 材料来源
				} else if ("ebm.materialSource".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setMaterialSource(value);
					}
					// 包装种类
				} else if ("ebm.wrap.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (wrapMap.get(value) != null)
							ebm.setWrap(wrapMap.get(value));
						else
							err = err + "[" + value + "]   数据库找不到此包装种类/";
					}
					// 仓库净重
				} else if ("ebm.cknetWeight".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setCknetWeight(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 是否保纳税
				} else if ("ebm.proBonded".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setProBonded(value);
					}
					// 对应料号
				} else if ("ebm.equalPtno".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setEqualPtno(value);
					}

				}// 废料代码
				else if ("ebm.scrapCode".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (value.equals("1") || value.equals("2")
								|| value.equals("3"))
							ebm.setScrapCode(value);
						else
							err = err + "[" + value + "]  废料代码只能是：（1或者2或者3）/";
					}
					// 废料重量
				} else if ("ebm.scrapWeight".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setScrapWeight(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 仓库毛重
				} else if ("ebm.ckoutWeight".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setCkoutWeight(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 企业数量
				} else if ("ebm.amount".equals(columnField)) {
					if (value != null && !value.equals("")) {
						try {
							ebm.setAmount(Double.valueOf(value));
						} catch (Exception e) {
							err = err + "[" + value + "]  该值不是数值，不能转换成数字/";
						}
					}
					// 关键零件
				} else if ("ebm.keyHardware".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (value.equals("是") || value.equals("否")) {
							if (value.equals("是")) {
								ebm.setKeyHardware(true);
							} else {
								ebm.setKeyHardware(false);
							}
						} else {
							err = err + "[" + value + "]  关键零件判断值请用：是或否/";
						}

					}
					// 是否委外
				} else if ("ebm.isOutsource".equals(columnField)) {
					if (value != null && !value.equals("")) {
						if (value.equals("是") || value.equals("否")) {
							if (value.equals("是")) {
								ebm.setIsOutsource(true);
							} else {
								ebm.setIsOutsource(false);
							}
						} else {
							err = err + "[" + value + "]  是否委外判断值请用：是或否/";
						}
					}
					// 报关助记码
				} else if ("ebm.customsNo".equals(columnField)) {
					if (value != null && !value.equals("")) {
						ebm.setCustomsNo(value);
					}
					// 关键零件
				}

			}
			obj.setEbm(ebm);
			obj.setErrinfo(err);
			list.add(obj);
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

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	// if (!listNotExisted.isEmpty() && listNotExisted.size() >
	// 0) {
	// if (JOptionPane
	// .showConfirmDialog(
	// DgBomTxtImport.this,
	// "同一个父件的同一个版本号里已经存在有子件,是否继续导入？按确定继续导入,按取消显示存在的料号!!!",
	// "提示", JOptionPane.OK_CANCEL_OPTION) !=
	// JOptionPane.OK_OPTION) {
	// initTableDetail(listNotExisted);
	// return;
	// }
	//
	// }
	private JButton getJButton1() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("2.保存数据");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					list = tableModel.getList();
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(
								DgEnterpriseTxtImport.this, "数据为空，请先导入数据！",
								"提示!", 2);
						return;
					}
					List listNotFound = null;
					boolean isReplace = getCbReplace().isSelected();
					for (int i = 0; i < tableModel.getList().size(); i++) {
						TempEnterpriseMaterial obj = (TempEnterpriseMaterial) tableModel
								.getList().get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgEnterpriseTxtImport.this, "有错误信息，不可保存！",
									"提示!", 2);
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
				CommonProgress.showProgressDialog(DgEnterpriseTxtImport.this);

				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				List ls = tableModel.getList();

				materialManageAction.saveList(ls);
				System.out.println("isok");

				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;

				JOptionPane.showMessageDialog(DgEnterpriseTxtImport.this,
						"导入数据成功！导入数据记录 " + ls.size() + " 条,共用时 " + totalTime
								+ " 毫秒", "提示", JOptionPane.INFORMATION_MESSAGE);
				DgEnterpriseTxtImport.this.dispose();

			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEnterpriseTxtImport.this,
						"导入数据失败 " + ex.getMessage(), "提示",
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
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEnterpriseTxtImport.this.dispose();
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
		list.add(new JTableListColumn("类别(不为空)", "ebm.scmCoi.name", 60, 200));
		list.add(new JTableListColumn("料号(不为空)", "ebm.ptNo", 60, 200));
		list.add(new JTableListColumn("十位商品编码", "ebm.complex.code", 100));
		list.add(new JTableListColumn("工厂品名(不为空)", "ebm.factoryName", 100));
		list.add(new JTableListColumn("工厂规格", "ebm.factorySpec", 100));
		list.add(new JTableListColumn("工厂单位(不为空)", "ebm.calUnit.name", 100));
		list.add(new JTableListColumn("报关名称", "ebm.ptName", 100));
		list.add(new JTableListColumn("报关规格", "ebm.ptSpec", 100));
		list.add(new JTableListColumn("报关单位", "ebm.ptUnit.name", 100));
		list.add(new JTableListColumn("工厂单位与报关单位折算系数", "ebm.unitConvert", 100));
		list.add(new JTableListColumn("详细型号规格", "ebm.ptDeSpec", 100));

		list.add(new JTableListColumn("单价", "ebm.ptPrice", 100));

		list.add(new JTableListColumn("净重", "ebm.ptNetWeight", 100));
		list.add(new JTableListColumn("重量单位", "ebm.calWeightUnit.name", 100));
		list.add(new JTableListColumn("毛重", "ebm.ptOutWeight", 100));
		list.add(new JTableListColumn("版本号", "ebm.ptVersion", 100));
		list.add(new JTableListColumn("英文品名", "ebm.ptEnglishName", 100));
		list.add(new JTableListColumn("英文详细规格", "ebm.ptEnglishSpec", 100));
		list.add(new JTableListColumn("供应商", "ebm.scmCoc.name", 100));
		list.add(new JTableListColumn("原产国", "ebm.sysArea.name", 100));
		list.add(new JTableListColumn("税制代码", "ebm.taxation.name", 100));
		list.add(new JTableListColumn("是否保纳税", "ebm.proBonded", 100));
		list.add(new JTableListColumn("对应料号", "ebm.equalPtno", 100));
		list.add(new JTableListColumn("废料代码", "ebm.scrapCode", 100));
		list.add(new JTableListColumn("废料重量", "ebm.scrapWeight", 100));
		list.add(new JTableListColumn("材料来源", "ebm.materialSource", 100));
		list.add(new JTableListColumn("是否关键零件", "ebm.keyHardware", 100));
		list.add(new JTableListColumn("包装种类", "ebm.wrap.name", 100));
		list.add(new JTableListColumn("仓库净重", "ebm.cknetWeight", 100));
		list.add(new JTableListColumn("仓库毛重", "ebm.ckoutWeight", 100));
		list.add(new JTableListColumn("企业数量", "ebm.amount", 100));
		list.add(new JTableListColumn("是否委外", "ebm.isOutsource", 100));
		list.add(new JTableListColumn("报关助记码", "ebm.customsNo", 100));
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
								.getTableColumnList(InputTableColumnSet.ENTERPRICE_MATERIAL);
					}
				});
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
	 * This method initializes cbReplace
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JCheckBox getCbReplace() {
		if (cbReplace == null) {
			cbReplace = new JCheckBox();
			cbReplace.setText("资料存在覆盖导入");
		}
		return cbReplace;
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
			btnColumnSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.ENTERPRICE_MATERIAL);
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
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}
	private JButton getBtnDeleteError() {
		if (btnDeleteError == null) {
			btnDeleteError = new JButton("删除错误");
			btnDeleteError.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List list = new ArrayList();
					if(dataSource==null||dataSource.size()==0){
						return;
					}
					for (int i = 0; i < dataSource.size(); i++) {
						
						Object obj = dataSource.get(i);
						if(obj instanceof TempEnterpriseMaterial){
							TempEnterpriseMaterial temp = (TempEnterpriseMaterial)obj;
							if(temp.getErrinfo()==null||"".equals(temp.getErrinfo().trim())){
								list.add(temp);
							}
						}
					}
					
					initTable(list);
				}
			});
		}
		return btnDeleteError;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
