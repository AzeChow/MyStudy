package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.DeclareState;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptAppHead;
import com.bestway.common.fpt.entity.FptAppItem;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.TempFptBillHeadImportFromExcel;
import com.bestway.dzsc.dzscmanage.action.DzscAction;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 导入Excel资料入转厂申请单
 * 
 * @author hw
 * 
 */
@SuppressWarnings({"unchecked"})
public class DgFptBillHeadImportFromExcel extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JToolBar jToolBar = null;
	private JScrollPane jScrollPane = null;
	private JTable tbSendDetails = null;
	private JPanel jPanel = null;
	private JButton btnOpenFile = null;
	private JButton btnSave = null;
	private JButton btnFieldsSet = null;
	private JButton btnExit = null;
	private JTableListModel tableModelSend = null;
	private JCheckBox cbChangeStr = null;
	private JCheckBox cbCheckTitle = null;
	/**
	 * 用来繁转简
	 */
	private Hashtable gbHash = null;
	/**
	 * 打开文件类（Excel或txt）
	 */
	private File txtFile = null;
	/**
	 * 存放表头的，用来判断是否存在
	 */
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel1 = null;
	private CustomBaseAction customBaseAction = null;
	private DzscAction dzscAction = null;
	// 转厂标志
	private String fptInOutFlag = FptInOutFlag.OUT; // @jve:decl-index=0:
	private FptManageAction fptManageAction = null;
	/**
	 * 转厂进出货表头
	 */
	private FptBillHead fptBillHead = null; // @jve:decl-index=0:
	private JCheckBox cbbMerge = null;

	// private Integer index = 0; // @jve:decl-index=0:
	/**
	 * DgFptBillHeadImportFromExcel 构造方法
	 * 
	 */
	public DgFptBillHeadImportFromExcel() {
		super();
		// this.index=index;
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		dzscAction = (DzscAction) CommonVars.getApplicationContext().getBean(
				"dzscAction");
		
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.FptBill_INPUT_SEND_DETAILS, this
						.getSendDetailsTableColumnLists());
		
		initTableSend(new ArrayList());
	}

	/**
	 * 设置组件可见性 （顺带控制标题显示）
	 */
	public void setVisible(boolean isFlag) {
		if(isFlag) {
			if(FptBusinessType.FPT_BILL.equals(fptBillHead.getSysType())) {
				jTabbedPane.setTitleAt(0, "成品发货明细");
			}
			else if(FptBusinessType.FPT_BILL_BACK.equals(fptBillHead
					.getSysType())) {
				jTabbedPane.setTitleAt(0, "料件退货明细");
			}
		}
		super.setVisible(isFlag);
	}

	/**
	 * 初始化控件 This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(636, 436));
		this.setTitle("Excel导入");
		this.setContentPane(getJPanel());
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if(jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSave());
			jToolBar.add(getBtnFieldsSet());
			jToolBar.add(getBtnExit());
			jToolBar.add(getJCheckBox());
			jToolBar.add(getJcbCheckTitle());
			jToolBar.add(getCbbMerge());
		}
		return jToolBar;
	}

	public String getFptInOutFlag() {
		return fptInOutFlag;
	}

	public void setFptInOutFlag(String fptInOutFlag) {
		this.fptInOutFlag = fptInOutFlag;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbSendDetails());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbSendDetails
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbSendDetails() {
		if(tbSendDetails == null) {
			tbSendDetails = new JTable();
		}
		return tbSendDetails;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if(jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJTabbedPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnOpenFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if(btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1.打开文件");
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					txtFile = getFile();
					if(txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return btnOpenFile;
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
		if(state == JFileChooser.APPROVE_OPTION) {
			txtFile = fileChooser.getSelectedFile();
		}
		return txtFile;
	}

	/**
	 * 内部类（用来打开文件）
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
			if(f.isDirectory() == true) {
				return true;
			}
			if(suffix != null) {
				if(suffix.toLowerCase().equals(this.suffix)) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}

		public String getDescription() {
			return "*." + this.suffix;
		}
        /**
         * 得到导入文件的后缀名
         * @param f
         * @return
         */
		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if(i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * This method initializes btnSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSave() {
		if(btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("2.保存数据");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List ls = null;
					ls = tableModelSend.getList();
					System.out.println(ls.size());
					for(int i = 0;i < ls.size();i++) {
						TempFptBillHeadImportFromExcel obj = (TempFptBillHeadImportFromExcel) ls
								.get(i);
						if(obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgFptBillHeadImportFromExcel.this,
									"有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}
					fptManageAction.saveFptBillHeadFromImport(new Request(
							CommonVars.getCurrUser()), ls, fptInOutFlag,
							false);
					JOptionPane.showMessageDialog(
							DgFptBillHeadImportFromExcel.this,
							"导入成功！", "提示!", 2);
					initTableSend(null);
				}
			});
		}
		return btnSave;
	}

	/**
	 * This method initializes btnFieldsSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFieldsSet() {
		if(btnFieldsSet == null) {
			btnFieldsSet = new JButton();
			btnFieldsSet.setText("3.栏位设定");
			btnFieldsSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(jTabbedPane.getSelectedIndex() == 0) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg
								.setTableFlag(InputTableColumnSet.FptBill_INPUT_SEND_DETAILS);
						dg.setVisible(true);
						if(dg.isOk()) {
							initTableSend(new ArrayList());
						}
					}
				}
			});
		}
		return btnFieldsSet;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if(btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 初始化发货表
	 */
	private void initTableSend(final List list) {
		tableModelSend = new JTableListModel(tbSendDetails, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List list = InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.FptBill_INPUT_SEND_DETAILS);
						//list.add(0, list.get(list.size() - 1));
						//list.remove(list.size() - 1);
						return list;
					}
				});
	}
	


	/**
	 * 初始化成品明细表头
	 * 
	 * @return
	 */
	private List getSendDetailsTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("序号", "fptBillItem.listNo", 60,
				Integer.class));//1
		list.add(new JTableListColumn("修改标记", "fptBillItem.modifyMake", 100));//2
		list.add(new JTableListColumn("料号", "fptBillItem.copGNo", 100));//3
		list.add(new JTableListColumn("归并前商品名称", "fptBillItem.copGName", 100));//4
		list.add(new JTableListColumn("归并前商品规格", "fptBillItem.copGModel", 100));//5
		list.add(new JTableListColumn("申报表序号", "fptBillItem.appGNo", 100));//6
		list.add(new JTableListColumn("项号", "fptBillItem.trGno", 100));//7
		list.add(new JTableListColumn("商品编码", "fptBillItem.complex.code", 60));//8
		list.add(new JTableListColumn("商品名称", "fptBillItem.commName", 100));//9
		list.add(new JTableListColumn("规格型号", "fptBillItem.commSpec", 200));//10
		list
				.add(new JTableListColumn("交易单位", "fptBillItem.tradeUnit.name",
						80));//11
		list.add(new JTableListColumn("交易数量", "fptBillItem.tradeQty", 100));//12
		list.add(new JTableListColumn("申报单位", "fptBillItem.unit.name", 80));//13
		list.add(new JTableListColumn("申报数量", "fptBillItem.qty", 80));//14
		list.add(new JTableListColumn("备注", "fptBillItem.note", 80));//15
		return list;
	}
	/**
	 * 初始化成品明细表头
	 * 
	 * @return
	 */
	private List getSendDetailsTableColumnLists() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("序号(必填)", "fptBillItem.listNo", 60,
				Integer.class));//1
		list.add(new JTableListColumn("修改标记", "fptBillItem.modifyMake", 100));//2
		list.add(new JTableListColumn("料号", "fptBillItem.copGNo", 100));//3
		list.add(new JTableListColumn("归并前商品名称", "fptBillItem.copGName", 100));//4
		list.add(new JTableListColumn("归并前商品规格", "fptBillItem.copGModel", 100));//5
		list.add(new JTableListColumn("申报表序号(必填)", "fptBillItem.appGNo", 100));//6
		list.add(new JTableListColumn("项号", "fptBillItem.trGno", 100));//7
		list.add(new JTableListColumn("商品编码", "fptBillItem.complex.code", 60));//8
		list.add(new JTableListColumn("商品名称", "fptBillItem.commName", 100));//9
		list.add(new JTableListColumn("规格型号", "fptBillItem.commSpec", 200));//10
		list
		.add(new JTableListColumn("交易单位(必填)", "fptBillItem.tradeUnit.name",
				80));//11
		list.add(new JTableListColumn("交易数量(必填)", "fptBillItem.tradeQty", 100));//12
		list.add(new JTableListColumn("申报单位", "fptBillItem.unit.name", 80));//13
		list.add(new JTableListColumn("申报数量(必填)", "fptBillItem.qty", 80));//14
		list.add(new JTableListColumn("备注", "fptBillItem.note", 80));//15
		return list;
	}

	/**
	 * 导入文件线程
	 * 
	 * @author hw
	 * 
	 */
	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgFptBillHeadImportFromExcel.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFileSendDetails();
				// headList = (List) list.get(0);
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				CommonProgress.closeProgressDialog();
				if(jTabbedPane.getSelectedIndex() == 0) {
					initTableSend(list);
				}
			}
		}
	}

	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox() {
		if(cbChangeStr == null) {
			cbChangeStr = new JCheckBox();
			cbChangeStr.setText("繁转简");
		}
		return cbChangeStr;
	}

	/**
	 * This method initializes jcbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJcbCheckTitle() {
		if(cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("第一行为标题行");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	/**
	 * 导入Excel中资料到发获方明细表
	 * 
	 * @return list
	 */
	private List parseTxtFileSendDetails() {
		Hashtable<Integer, FptAppItem> appItemHs = new Hashtable<Integer, FptAppItem>();
		FptAppHead appHead = fptManageAction.findFptAppHeadByAppNo(new Request(
				CommonVars.getCurrUser()), fptBillHead.getAppNo(),
				DeclareState.PROCESS_EXE);
		List appItemList = fptManageAction.findFptAppItems(new Request(
				CommonVars.getCurrUser()), appHead.getId());
		if(appItemList != null && appItemList.size() > 0) {
			for(int i = 0;i < appItemList.size();i++) {
				FptAppItem appItem = (FptAppItem) appItemList.get(i);
				appItemHs.put(appItem.getListNo(), appItem);
			}
		}
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		List unitList = CustomBaseList.getInstance().getUnits();
		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();
		for(int i = 0;i < complexlist.size();i++) {
			Complex c = (Complex) complexlist.get(i);
			hs.put(c.getCode(), c);
		}
		for(int i = 0;i < unitList.size();i++) {
			Unit u = (Unit) unitList.get(i);
			hsUnit.put(u.getName(), u);
		}
		boolean ischange = true;
		if(getJCheckBox().isSelected()) {
			infTojHsTable();
		}
		else {
			ischange = false;
		}
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if(suffix.equals("xls")) {
			//
			// 导入xls
			//			
			if(cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			}
			else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		}
		else {
			//
			// 导入txt
			//
			if(cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			}
			else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		ArrayList<TempFptBillHeadImportFromExcel> list = new ArrayList<TempFptBillHeadImportFromExcel>();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.FptBill_INPUT_SEND_DETAILS);
		int zcount = lsIndex.size();// 字段数目
		for(int i = 0;i < lines.size();i++) {
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for(int j = 0;j < line.size();j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if(ischange) {
				strs = changStrs(strs);
			}
			TempFptBillHeadImportFromExcel temp = new TempFptBillHeadImportFromExcel();
			FptBillItem fpt = new FptBillItem();
			String err = "";
			for(int j = 0;j < zcount;j++) {
				String value = getFileColumnValue(strs, j);
				System.out.println("import value is :" + value);
				String columnField = lsIndex.get(j);
				if("fptBillItem.listNo".equals(columnField)) {
					if(value != null && !value.equals("")) {
						fpt.setListNo(Double.valueOf(value).intValue());// 序号
					}
					else {
						err = err + "[" + value + "]   序号不可为空/";
					}
				}
//				else if("fptBillItem.modifyMake".equals(columnField)) {
//					if(value != null && !value.equals("")) {
//						fpt.setModifyMake(value);// 修改标志
//					}
//					else {
//						err = err + "[" + value + "]  修改标志不可为空/";
//					}
//				}
				else if("fptBillItem.copGNo".equals(columnField)) {
					if(value != null && !value.equals("")) {
						fpt.setCopGNo(value);// 料号
					}
					else {
						err = err + "[" + value + "] 料号不可为空/";
					}
				}
				else if("fptBillItem.copGName".equals(columnField)) {
					if(value != null && !value.equals("")) {
						fpt.setCopGName(value);// 归并前商品名称
					}
					else {
						err = err + "[" + value + "] 归并前商品名称不可为空/";
					}
				}
				else if("fptBillItem.copGModel".equals(columnField)) {
					fpt.setCopGModel(value);// 归并前商品规格
				}
				else if("fptBillItem.appGNo".equals(columnField)) {
					Integer appNo = Double.valueOf(value).intValue();
					if(value != null && !value.equals("")) {
						if(appItemHs.get(appNo) == null) {
							err = err + "[" + value + "]" + "申请表序号" + appNo
									+ "在转厂申请表中不存在\n";
						}
						fpt.setAppGNo(Double.valueOf(value).intValue());// 序号
					}
					else {
						err = err + "[" + value + "]  申报表序号不可为空/";
					}
				}
				else if("fptBillItem.trGno".equals(columnField)) {
					if(value != null && !value.equals("")) {
						fpt.setTrGno(Double.valueOf(value).intValue());// 项号
					}
					else {
						err = err + "[" + value + "]  项号不可为空/";
					}
				}
				else if("fptBillItem.complex.code".equals(columnField)) {
					if(hs.get(value) == null) {
						Complex cobj = dzscAction.findCustomsComplexByCode(
								new Request(CommonVars.getCurrUser()), value);
						if(cobj != null) {
							hs.put(cobj.getCode(), cobj);
							fpt.setComplex(cobj);// 商品编码
						}
						else {
							err = err + "[" + value + "]   不正确商品编码/";
						}
					}
					else {
						Complex c = (Complex) hs.get(value);
						fpt.setComplex(c);
					}
				}
				else if("fptBillItem.commName".equals(columnField)) {
					fpt.setCommName(value);// 商品名称
				}
				else if("fptBillItem.commSpec".equals(columnField)) {
					fpt.setCommSpec(value);// 规格型号
				}
				else if("fptBillItem.tradeUnit.name".equals(columnField)) {
					if(value == null || "".equals(value)) {
						err = err + "[" + value + "]   交易单位不为空/";
					}
					else {// 判断是否在单据中存在
						if(hsUnit.get(value) != null) {
							Unit u = (Unit) hsUnit.get(value);
							fpt.setTradeUnit(u);
						}
						else {
							err = err + "[" + value + "]   交易单位不存在/";
						}
					}
				}
				else if("fptBillItem.tradeQty".equals(columnField)) {
					if(value != null && !value.equals("")) {
						fpt.setTradeQty(Double.valueOf(value));// 交易数量
					}
					else {
						err = err + "[" + value + "]  交易数量不可为空/";
					}
				}
				else if("fptBillItem.unit.name".equals(columnField)) {
					if(value == null || "".equals(value)) {
						err = err + "[" + value + "]   申报单位不为空/";
					}
					else {
						if(hsUnit.get(value) != null) {
							Unit u = (Unit) hsUnit.get(value);
							fpt.setUnit(u);
						}
						else {
							err = err + "[" + value + "]   申报单位不存在/";
						}
					}
				}
				else if("fptBillItem.qty".equals(columnField)) {
					if(value != null && !value.equals("")) {
						fpt.setQty(Double.valueOf(value));// 申报数量
					}
					else {
						err = err + "[" + value + "]  申报数量不可为空/";
					}
				}
				else if("fptBillItem.note".equals(columnField)) {
					fpt.setNote(value);// 设置备注
				}
			}
			fpt.setBillSort(fptInOutFlag);
			
			fpt.setFptBillHead(fptBillHead);
			temp.setFptBillItem(fpt);
			fillData(fpt,fptBillHead,appItemHs);
			temp.setErrinfo(err);
			list.add(temp);
			System.out.println(list.size());
		}
		
		Map<Integer, TempFptBillHeadImportFromExcel> map = new HashMap<Integer, TempFptBillHeadImportFromExcel>();
		TempFptBillHeadImportFromExcel tmp = null;
		TempFptBillHeadImportFromExcel tmp1 = null;
		for (int i = 0; i < list.size(); i++) {
			tmp = list.get(i);
			tmp1 = map.get(tmp.getFptBillItem().getAppGNo());
			if(tmp1 == null) {
				map.put(tmp.getFptBillItem().getAppGNo(), tmp);
			} else {
				tmp1.getFptBillItem().setQty(add(tmp.getFptBillItem().getQty(), tmp1.getFptBillItem().getQty()));
				tmp1.getFptBillItem().setTradeQty(add(tmp.getFptBillItem().getTradeQty(), tmp1.getFptBillItem().getTradeQty()));
				tmp1.getFptBillItem().setPtAmount(add(tmp.getFptBillItem().getPtAmount(), tmp1.getFptBillItem().getPtAmount()));
			}
		}
		
		
		
		return new ArrayList(map.values());
	}
	
	private void fillData(FptBillItem fpt,FptBillHead fptBillHead,Hashtable<Integer, FptAppItem> appItemHs){
		FptAppItem fptAppItem = appItemHs.get(fpt.getAppGNo());
		if(fpt.getEmsNo()==null){
			fpt.setEmsNo(fptBillHead.getOutEmsNo());
		}
		if(fpt.getUnit()==null&&fptAppItem!=null){
			fpt.setUnit(fptAppItem.getUnit());
		}
		if(fpt.getBillSort()==null){
			fpt.setBillSort(fptBillHead.getBillSort());
		}
		if(fpt.getComplex()==null&&fptAppItem!=null){
			fpt.setComplex(fptAppItem.getCodeTs());
		}
		if(fpt.getCommName()==null&&fptAppItem!=null){
			fpt.setCommName(fptAppItem.getName());
		}
		if(fpt.getAppGNo()==null&&fptAppItem!=null){
			fpt.setAppGNo(fptAppItem.getListNo());// 申请表序号
		}
		if(fpt.getTrGno()==null&&fptAppItem!=null){
			fpt.setTrGno(fptAppItem.getTrNo());// 项号
		}
		if(fpt.getCommSpec()==null&&fptAppItem!=null){
			fpt.setCommSpec(fptAppItem.getSpec());
		}
	}
	
	private Double add(Double d1, Double d2) {
		if(d1 == null) {
			d1 = 0.0;
		}
		
		if(d2 == null) {
			d2 = 0.0;
		}
		
		return d1 + d2;
	}

	/**
	 * 获取导入文件中列的值
	 * 
	 * @param fileRow
	 * @param dataIndex
	 * @return
	 */
	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if(dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	/**
	 * 繁转简
	 * 
	 * @param source
	 * @return
	 */
	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for(int i = 0,n = source.length;i < n;i++) {
			// newStrs[i] = changeStr(source[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance().big5ConvertToGB(
					source[i]);
		}
		return newStrs;
	}

	/**
	 * 初始化繁转简对照表
	 */
	private void infTojHsTable() {
		if(gbHash == null) {
			gbHash = new Hashtable();
			List list = CustomBaseList.getInstance().getGbtobigs();
			for(int i = 0;i < list.size();i++) {
				Gbtobig gb = (Gbtobig) list.get(i);
				gbHash.put(gb.getBigname(), gb.getName());
			}
		}
	}

	/**
	 * 得到导入文件的后缀名
	 * 
	 * @param f
	 * @return
	 */
	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if(i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if(jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("发货明细", null, getJPanel1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if(jPanel1 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(getJScrollPane(), gridBagConstraints);
		}
		return jPanel1;
	}

	public FptBillHead getHead() {
		return fptBillHead;
	}

	public void setHead(FptBillHead head) {
		this.fptBillHead = head;
	}

	/**
	 * This method initializes cbbMerge	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbbMerge() {
		if (cbbMerge == null) {
			cbbMerge = new JCheckBox();
			cbbMerge.setText("项号合并");
			cbbMerge.setSelected(true);
		}
		return cbbMerge;
	}
}
