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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MotorCode;
import com.bestway.common.materialbase.entity.TempMotor;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JCheckBox;

public class DgDriverInfo extends JDialogBase {
	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JTableListModel tableModel = null;
	private JTableListModel tableModelDetail = null;
	private File txtFile = null;
	private JRadioButton jRadioButton = null;
	private Hashtable gbHash = null; // @jve:decl-index=0:
	private Hashtable headHash = null;
	private MaterialManageAction materialManageAction = null;
	private JButton jButton3 = null;
	private Hashtable hs = null;
	private MotorCode Driver = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JCheckBox jCheckBox = null;
	private JButton jsetColumn = null;
	private JTableListModel jmodel = null;
	private JCheckBox jcbIsOverwrite = null;
	private JCheckBox jcbCheckTitle = null;

	/**
	 * This is the default constructor
	 */
	public DgDriverInfo() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		InputTableColumnSetUtils.putColumn(InputTableColumnSet.DRIVER_INFO,
				this.getDriverInfoTableColumnList());
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
		this.setTitle("工厂司机导入");
	}

	/**
	 * 初始化表头信息
	 * 
	 * @return
	 */
	private List getDriverInfoTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		/*
		 * list.add(new JTableListColumn("编码", "bill1", 100)); list.add(new
		 * JTableListColumn("全称", "bill2", 100)); list.add(new
		 * JTableListColumn("简称", "bill3", 120)); list.add(new
		 * JTableListColumn("是否客户(是/否表示)", "bill4", 140)); list.add(new
		 * JTableListColumn("是否供应商(是/否表示)", "bill5", 140));
		 */
		list.add(new JTableListColumn("代码", "mc.code", 80));
		list.add(new JTableListColumn("司机名称(不可为空)", "mc.name", 80));
		list.add(new JTableListColumn("国内车牌", "mc.homeCard", 80));
		list.add(new JTableListColumn("香港车牌", "mc.hongkongCard", 80));
		list.add(new JTableListColumn("司机本海关编码", "mc.complex", 80));
		list.add(new JTableListColumn("IC卡", "mc.icCard", 80));
		list.add(new JTableListColumn("结关口岸", "mc.customsPort", 80));
		list.add(new JTableListColumn("运输单位", "mc.trafficUnit", 80));
		list.add(new JTableListColumn("运输单位地址", "mc.trafficUnitAdd", 80));
		list.add(new JTableListColumn("运输单位电话", "mc.trafficUnitTel", 80));
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
			jToolBar.add(getJcbIsOverwrite());
			jToolBar.add(getJCheckBox());
			jToolBar.add(getJcbCheckTitle());
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
		@Override
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgDriverInfo.this);
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

	/**
	 * 解析导入文件
	 * 
	 * @return
	 */
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
		ArrayList<TempMotor> list = new ArrayList<TempMotor>();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.DRIVER_INFO);
		int zcount = lsIndex.size();// 字段数目
		Map<String,MotorCode> mapMotorCode = getMotorCodeMap();
		Map<String,MotorCode> mapComplex = getComplexMap();
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
			String err = "";
			MotorCode obj = new MotorCode();
			TempMotor temp = new TempMotor();
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				System.out.println("import value is :" + value);
				String columnField = lsIndex.get(j);
				if ("mc.code".equals(columnField)) {
					if (value != null && !value.equals("")) {
						obj.setCode(value);// 编码
					} else {
						err = err + "[" + value + "]   编码不可为空/";
					}
				} else if ("mc.name".equals(columnField)) {
					if (value != null && !value.equals("")) {
						obj.setName(value);// 司机姓名
					} else {
						err = err + "[" + value + "]  司机姓名不可为空/";
					}
				} else if ("mc.homeCard".equals(columnField)) {
					if (value != null && !value.equals("")) {
						obj.setHomeCard(value);// 国内车牌
					} else {
						err = err + "[" + value + "] 国内车牌不可为空/";
					}
				} else if ("mc.hongkongCard".equals(columnField)) {
					if (value != null && !value.equals("")) {
						obj.setHongkongCard(value);// 香港车牌
					} else {
						err = err + "[" + value + "] 香港车牌不可为空/";
					}
				} else if ("mc.complex".equals(columnField)) {
					obj.setComplex(value);// 海关编码
				} else if ("mc.icCard".equals(columnField)) {
					obj.setIcCard(value);// IC卡
				} else if ("mc.customsPort".equals(columnField)) {
					obj.setCustomsPort(value);// 设置结关口岸
				} else if ("mc.trafficUnit".equals(columnField)) {
					obj.setTrafficUnit(value);// 设置运输单位
				} else if ("mc.trafficUnitAdd".equals(columnField)) {
					obj.setTrafficUnitAdd(value);// 设置运输单位地址
				} else if ("mc.trafficUnitTel".equals(columnField)) {
					obj.setTrafficUnitTel(value);// 设置运输单位电话
				}
			}
			if(!jcbIsOverwrite.isSelected()){
				MotorCode mcCard = mapMotorCode.get(obj.getHomeCard());
				if(mcCard==null){
					mapMotorCode.put(obj.getHomeCard(),obj);
				}else{
					err = err + "[" + obj.getHomeCard() + "] 国内车牌已经存在！/";
				}
				MotorCode mcComplex = mapComplex.get(obj.getComplex());
				if(mcComplex==null){
					mapComplex.put(obj.getComplex(),obj);
				}else{
					err = err + "[" + obj.getComplex() + "] 海关编码已经存在！/";
				}
			}
			temp.setMc(obj);
			temp.setErrinfo(err);
			list.add(temp);
		}
		// allList.add(list);
		return list;
	}
	
	public Map<String,MotorCode> getMotorCodeMap(){
		Map<String,MotorCode> mapMotorCode = new HashMap<String, MotorCode>();
		List<MotorCode> list = materialManageAction.findMotorCode(new Request(
				CommonVars.getCurrUser()));
		for (int i = 0; i < list.size(); i++) {
			MotorCode mc = list.get(i);
			mapMotorCode.put(mc.getHomeCard(), mc);
		}
		return mapMotorCode;
	}
	public Map<String,MotorCode> getComplexMap(){
		Map<String,MotorCode> mapComplex = new HashMap<String, MotorCode>();
		List<MotorCode> list = materialManageAction.findMotorCode(new Request(
				CommonVars.getCurrUser()));
		for (int i = 0; i < list.size(); i++) {
			MotorCode mc = list.get(i);
			mapComplex.put(mc.getComplex(), mc);
		}
		return mapComplex;
	}

	/*
	 * list.add(new JTableListColumn("代码", "code", 80)); list.add(new
	 * JTableListColumn("司机名称", "name", 80)); list.add(new
	 * JTableListColumn("国内车牌", "homeCard", 80)); list.add(new
	 * JTableListColumn("香港车牌", "hongkongCard", 80)); list.add(new
	 * JTableListColumn("司机本海关编码", "complex", 80)); list.add(new
	 * JTableListColumn("IC卡", "icCard", 80)); list.add(new
	 * JTableListColumn("结关口岸", "customsPort", 80)); list.add(new
	 * JTableListColumn("运输单位", "trafficUnit", 80)); list.add(new
	 * JTableListColumn("运输单位地址", "trafficUnitAdd", 80)); list.add(new
	 * JTableListColumn("运输单位电话", "trafficUnitTel", 80));
	 */
	private List getHead(String[] strs, int zcount) {
		List ls = new ArrayList();
		ArrayList list = new ArrayList();
		return ls;
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
						JOptionPane.showMessageDialog(DgDriverInfo.this,
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
		@Override
		public void run() {
			List list = null;
			try {
				list = tableModel.getList();
				for (int i = 0; i < list.size(); i++) {
					TempMotor obj = (TempMotor) list.get(i);
					if (obj.getErrinfo() != null
							&& !obj.getErrinfo().equals("")) {
						JOptionPane.showMessageDialog(DgDriverInfo.this,
								"有错误信息，不可保存！", "提示!", 2);
						return;
					}
				}
				materialManageAction.saveImportDriverInfo(new Request(
						CommonVars.getCurrUser()), jcbIsOverwrite.isSelected(),
						list);
				dispose();
				// jmodel.addRows(list);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
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
			jButton2.setText("退出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgDriverInfo.this.dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * 初始化表
	 */
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.DRIVER_INFO);
					}
					/*
					 * public List<JTableListColumn> InitColumns() {
					 * List<JTableListColumn> list = new
					 * Vector<JTableListColumn>(); list.add(addColumn("1.编码",
					 * "bill1", 100)); list.add(addColumn("2.全称", "bill2",
					 * 100)); list.add(addColumn("3.简称", "bill3", 120));
					 * list.add(addColumn("4.是否客户(是/否表示)", "bill4", 140));
					 * list.add(addColumn("5.是否供应商(是/否表示)", "bill5", 140));
					 * return list; }
					 */
				});
	}

	/**
	 * 调出文件选择框
	 */
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

		@Override
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

		@Override
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
					dg.setTableFlag(InputTableColumnSet.DRIVER_INFO);
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
	public JTableListModel getJmodel() {
		return jmodel;
	}

	public void setJmodel(JTableListModel jmodel) {
		this.jmodel = jmodel;
	}

	/**
	 * This method initializes jcbIsOverwrite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJcbIsOverwrite() {
		if (jcbIsOverwrite == null) {
			jcbIsOverwrite = new JCheckBox();
			jcbIsOverwrite.setText("资料存在覆盖导入");
		}
		return jcbIsOverwrite;
	}

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
