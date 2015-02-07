package com.bestway.bcus.client.custombase;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.SQLException;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcs.client.contract.DgContract;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.basecode.Brief;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.countrycode.PortLin;
import com.bestway.bcus.custombase.entity.countrycode.PreDock;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.hscode.CustomsComplex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.entity.InputInExRequestBillOrder;
import com.bestway.bcus.enc.entity.TempImpExpRequestBillForInput;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.custom.common.DgInputImpExpRequestBill;
import com.bestway.client.custom.common.DgInputInExRequestBillSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCocControl;
import com.bestway.common.materialbase.entity.ScmCocForInput;
import com.bestway.ui.winuicontrol.JDialogBase;
import javax.swing.JCheckBox;

public class DgBriefImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;
	
	private JTableListModel tableModelParent = null;
	
	private JTableListModel tableModelDetail = null;

	private File txtFile = null;

	private JRadioButton jRadioButton = null;

	private Hashtable gbHash = null; // @jve:decl-index=0:

	private Hashtable headHash = null;

	private MaterialManageAction materialManageAction = null;

	private JButton jButton3 = null;

	private Hashtable hs = null;

	private BillTemp bill = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JCheckBox jCheckBox = null;

	private JButton jsetColumn = null;

	private JCheckBox jcbCheckTitle = null;

	private HashMap mriefName = new HashMap(); // @jve:decl-index=0:

	private HashMap mriefCode = new HashMap(); // @jve:decl-index=0:

	private HashMap countryName = new HashMap(); // @jve:decl-index=0:

	private HashMap countryCode = new HashMap(); // @jve:decl-index=0:

	private HashMap portLinName = new HashMap(); // @jve:decl-index=0:

	private HashMap portLinCode = new HashMap(); // @jve:decl-index=0:

	private HashMap country2Name = new HashMap(); // @jve:decl-index=0:

	private HashMap country2Code = new HashMap(); // @jve:decl-index=0:

	private HashMap customsName = new HashMap(); // @jve:decl-index=0:

	private HashMap customsCode = new HashMap(); // @jve:decl-index=0:

	private HashMap belongToCustomsName = new HashMap(); // @jve:decl-index=0:

	private HashMap belongToCustomsCode = new HashMap(); // @jve:decl-index=0:

	private HashMap transfName = new HashMap(); // @jve:decl-index=0:

	private HashMap transfCode = new HashMap(); // @jve:decl-index=0:

	private HashMap predockName = new HashMap(); // @jve:decl-index=0:

	private HashMap predockCode = new HashMap(); // @jve:decl-index=0:

	private HashMap wrapTypeName = new HashMap(); // @jve:decl-index=0:

	private HashMap wrapTypeCode = new HashMap(); // @jve:decl-index=0:

	private HashMap tradeModeName = new HashMap(); // @jve:decl-index=0:

	private HashMap tradeModeCode = new HashMap(); // @jve:decl-index=0:

	private CustomBaseAction customBaseAction = null;

	private SystemAction systemAction = null;// SystemAction接口

	public JTableListModel getTableModelParent() {
		return tableModelParent;
	}

	public void setTableModelParent(JTableListModel tableModelParent) {
		this.tableModelParent = tableModelParent;
	}

	/**
	 * This is the default constructor
	 */
	public DgBriefImport() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
				.getBean("systemAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.BCUS_CUSTOMBASE_BRIEF, this
						.getTableColumnListAll());
		initTable(new Vector());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(675, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("海关注册公司导入");

	}

	private List getTableColumnListAll() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "code", 100));
		list.add(new JTableListColumn("名称", "name", 100));
		return list;
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
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJsetColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getJcbCheckTitle());
			jToolBar.add(getJCheckBox());
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
					headHash = new Hashtable();
					txtFile = getFile();
					if (txtFile == null) {
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
			List headList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgBriefImport.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				initConditionList();// 初始化查询条件
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				// headList = (List) list.get(0);
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
				initTable(list);
			}
		}
	}

	/**
	 * 为了减轻对服务器数据库的压力,尽量减少对数据库的操作.用缓存!
	 */
	private void initConditionList() {
//		mriefName.clear();
//		mriefCode.clear();
//		countryName.clear();
//		countryCode.clear();
//		portLinName.clear();
//		portLinCode.clear();
//		country2Name.clear();
//		country2Code.clear();
//		customsName.clear();
//		customsCode.clear();
//		belongToCustomsName.clear();
//		belongToCustomsCode.clear();
//		transfName.clear();
//		transfCode.clear();
//		predockName.clear();
//		predockCode.clear();
//		wrapTypeName.clear();
//		wrapTypeCode.clear();
//		tradeModeName.clear();
//		tradeModeCode.clear();
//
//		// 关务海关注册公司
//		List BriefName = customBaseAction.findBrief("", "");
//		for (int i = 0; i < BriefName.size(); i++) {
//			Brief brief = (Brief) BriefName.get(i);
//			mriefName.put(brief.getName(), brief);
//			mriefCode.put(brief.getCode(), brief);
//		}
//		System.out.println("关务海关注册公司" + BriefName.size());
//		// 运抵国、 产销国
//		List CountryName = customBaseAction.findCountry("", "");
//		for (int i = 0; i < CountryName.size(); i++) {
//			Country country = (Country) CountryName.get(i);
//			countryName.put(country.getName(), country);
//			countryCode.put(country.getCode(), country);
//		}
//		System.out.println("运抵国、 产销国、 出口口岸、所属海关" + CountryName.size());
//		// 所属海关、 出口口岸
//		List CustomsName = customBaseAction.findCustoms("", "");
//		for (int i = 0; i < CustomsName.size(); i++) {
//			Customs customs = (Customs) CustomsName.get(i);
//			customsName.put(customs.getName(), customs);
//			customsCode.put(customs.getCode(), customs);
//		}
//		// 指运港
//		List PortLinName = customBaseAction.findPortLin("", "");
//		for (int i = 0; i < PortLinName.size(); i++) {
//			PortLin portLin = (PortLin) PortLinName.get(i);
//			portLinName.put(portLin.getName(), portLin);
//			portLinCode.put(portLin.getCode(), portLin);
//		}
//		System.out.println("指运港" + PortLinName.size());
//		// 运输方式
//		List ModeOfTransport = customBaseAction.findTransf("", "");
//		for (int i = 0; i < ModeOfTransport.size(); i++) {
//			Transf transf = (Transf) ModeOfTransport.get(i);
//			transfName.put(transf.getName(), transf);
//			transfCode.put(transf.getCode(), transf);
//		}
//		System.out.println("运输方式" + ModeOfTransport.size());
//		// 码头
//		List Predock = customBaseAction.findPreDock("", "");
//		for (int i = 0; i < Predock.size(); i++) {
//			PreDock preDock = (PreDock) Predock.get(i);
//			predockName.put(preDock.getName(), preDock);
//			predockCode.put(preDock.getCode(), preDock);
//		}
//		System.out.println("码头" + Predock.size());
//		// 包装种类
//		List WrapType = customBaseAction.findWrap();
//		for (int i = 0; i < WrapType.size(); i++) {
//			Wrap wrap = (Wrap) WrapType.get(i);
//			wrapTypeName.put(wrap.getName(), wrap);
//			wrapTypeCode.put(wrap.getCode(), wrap);
//		}
//		System.out.println("包装种类" + WrapType.size());
//		// 贸易方式
//		List TradeMode = customBaseAction.findTrade("", "");
//		for (int i = 0; i < TradeMode.size(); i++) {
//			Trade trade = (Trade) TradeMode.get(i);
//			tradeModeName.put(trade.getName(), trade);
//			tradeModeCode.put(trade.getCode(), trade);
//		}
//		System.out.println("贸易方式" + TradeMode.size());
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

	//
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

	private List parseTxtFile() {
		List allList = new ArrayList();
		boolean ischange = true;
		if (getJCheckBox().isSelected()) {
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
			if (jcbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			//
			// 导入txt
			//
			if (jcbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}

		ArrayList list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.BCUS_CUSTOMBASE_BRIEF);
		int zcount = lsIndex.size();// 字段数目
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
			BriefInput briefInput = new BriefInput();
			StringBuffer sb = new StringBuffer("");
			String err = "";
			BillTemp obj = new BillTemp();
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				String msg ="";
				if ("code".equals(columnField)) {
					if(value == null || value.equals("")){
						msg += "代码不能为空\n"; 
					}
					if (customBaseAction.isReDgBriefCode(value)) {
						msg += "该代码已存在"; 
					}
					briefInput.setCode(value);
				} else if ("name".equals(columnField)) {
					if (customBaseAction.isReDgBriefName(value)) {
						msg+="该名称已存在！";
					}
					briefInput.setName(value);
				}
				if(!msg.equals("")){
				briefInput.setErrinfo(msg);
				}
			}
			list.add(briefInput);
		}
		return list;
	}

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
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存数据");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(DgBriefImport.this,
								"保存导入数据为空!", "提示", 2);
						return;
					}
					list = tableModel.getList();
					new SaveDataRunnable().start();
				}
			});
		}
		return jButton1;
	}

	class SaveDataRunnable extends Thread {
		public void run() {
			List list = null;
			try {
				list = tableModel.getList();
				for (int i = 0; i < list.size(); i++) {
					BriefInput obj = (BriefInput) list.get(i);
					if (obj.getErrinfo() != null
							&& !obj.getErrinfo().equals("")) {
						JOptionPane.showMessageDialog(DgBriefImport.this,
								"有错误信息，不可保存！", "提示!", 2);
						// -----------------------------------------
						// File uploaderrdir = new File(uploaderr);
						// File uploaderrdirfile = new File(uploaderr +
						// File.separator
						// + txtFile.getName());
						// if (uploaderrdir.exists()) {
						// moveFile(txtFile, uploaderrdirfile);
						// }
						// -----------------------------------------
						return;
					}
				}
				customBaseAction.SaveBriefs(getEntityFromImportEntity(list));
				tableModelParent.addRows(getEntityFromImportEntity(list));
				JOptionPane.showMessageDialog(DgBriefImport.this,
						"保存完毕!\n\n海关注册公司数据: " + String.valueOf(list.size()),
						"提示", 2);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgBriefImport.this,
						"保存失败!\n\n海关注册公司数据: " + String.valueOf(list.size()),
						"提示", 2);
				e.printStackTrace();
			}
		}
	}
	/**
	 * 把导入类型转换为对应的实体
	 */
	public List getEntityFromImportEntity(List list){
		List results = new ArrayList();
		for(int i = 0;i<list.size();i++){
			Brief brief  =  new Brief();
			brief.setCode(((BriefInput)list.get(i)).getCode());
			brief.setName(((BriefInput)list.get(i)).getName());
			results.add(brief);
		}
		return results;
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
					DgBriefImport.this.dispose();
				}
			});
		}
		return jButton2;
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

	// 单据头
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.BCUS_CUSTOMBASE_BRIEF);
					}
					/*
					 * public List<JTableListColumn> InitColumns() { List<JTableListColumn>
					 * list = new Vector<JTableListColumn>();
					 * list.add(addColumn("1.编码", "bill1", 100));
					 * list.add(addColumn("2.全称", "bill2", 100));
					 * list.add(addColumn("3.简称", "bill3", 120));
					 * list.add(addColumn("4.是否客户(是/否表示)", "bill4", 140));
					 * list.add(addColumn("5.是否供应商(是/否表示)", "bill5", 140));
					 * return list; }
					 */
				});
	}

	// 调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();

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
	/*
	 * private JRadioButton getJRadioButton() { if (jRadioButton == null) {
	 * jRadioButton = new JRadioButton(); jRadioButton.setText("繁转简"); } return
	 * jRadioButton; }
	 */

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
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setText("繁转简");
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jsetColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJsetColumn() {
		if (jsetColumn == null) {
			jsetColumn = new JButton();
			jsetColumn.setText("栏位设定");
			jsetColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.BCUS_CUSTOMBASE_BRIEF);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}
				}
			});
		}
		return jsetColumn;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */

	/**
	 * This method initializes jcbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJcbCheckTitle() {
		if (jcbCheckTitle == null) {
			jcbCheckTitle = new JCheckBox();
			jcbCheckTitle.setText("第一行为标题行");
		}
		return jcbCheckTitle;
	}
}// @jve:decl-index=0:visual-constraint="10,10"
