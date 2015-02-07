package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.bcus.system.entity.Company;
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
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.FileUtils;
import com.bestway.util.RegexUtil;


public class DgImportDataImg extends JDialogBase {

	private FptManageAction fptManageAction;
	
	private Request request;
	
	//保存导入的信息是否有错误,true为有错误
	private boolean boo = false;
	
	private Hashtable gbHash = null; 
	
	private JPanel jContentPane = null;
	
	private File txtFile = null;

	private JToolBar jToolBar = null;

	private JButton btnOpenFile = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbCheckTitle = null;

	private JTableListModel tableModel = null;
	private JTableListModelAdapter	jlma = null;
	private List afterList = null;
	
	private JPanel jPanel1 = null;
	private JButton btnDel = null;
	private JScrollPane scrollPane;
	private JTable table;
	
	//保存用于放入数据库的list
	List<List> listImg = new ArrayList<List>();
	List list = new ArrayList();
	//保存用于放入表格list
	List<Object[]> listTable = null;
	private JButton btnDLTpl;

	/**
	 * This method initializes
	 * 
	 */
	public DgImportDataImg() {
		super();
		
		request = new Request(CommonVars.getCurrUser());
		
		fptManageAction = (FptManageAction) CommonVars.getApplicationContext()
				.getBean("fptManageAction");
		
		initialize();
		initJtable(null);
		infTojHsTable();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("料件退货单导入");
		this.setContentPane(getJContentPane());
		this.setSize(732, 513);

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
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 打开文件
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(DgImportDataImg.this);
				CommonProgress.setMessage("系统正在打开文件资料，请稍后...");
				afterList = parseTxtFile(txtFile);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataImg.this,
						"打开文件失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
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
			initJtable(list);
		}
	}


	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}


	private List parseTxtFile(File file) {
		CommonProgress.setMessage("系统正在读取xls文件,请稍等...");
		listTable = new ArrayList();
		list.clear();
		
		String suffix = getSuffix(file);
		List<List> lines = null;
		System.out.println("打开导入的Excel数据开始");
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//			
			lines = FileReading.readExcel(file, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(file, 0, "GBK");
		}
		
		List<String> listAppHead = new ArrayList<String>();
		List<String> listAppItem = new ArrayList<String>();
		Hashtable<String, String> mapAppNo = new Hashtable<String, String>();//去掉重复
		Hashtable<String, String> mapListNo = new Hashtable<String, String>();//去掉重复
		System.out.println("循环保存和判断料件数据开始");
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			List line = lines.get(i);
			
			if (cbJF.isSelected()) {
				// 繁简转换
				line = changStrs(line);
			}
			if(line.size()<getProperty().length){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataImg.this, "导入栏位数量少于系统栏位!");
				break;
			}
			
			Hashtable<String, Integer> mapIndex = getIndex();
			int errorInfoIndex = mapIndex.get("错误信息");
			int appNo = mapIndex.get("申请表编号(必填)");
			int listNo = mapIndex.get("申请表序号(必填)");
			String[] strs = new String[errorInfoIndex+1];
			
			for (int j = 0; j < errorInfoIndex; j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if(mapAppNo.get(strs[appNo])==null){
				listAppHead.add(strs[appNo]);
				mapAppNo.put(strs[appNo], strs[appNo]);
			}
			if(checkInt(strs[listNo])){
				if(mapListNo.get(strs[listNo])==null){
					listAppItem.add(strs[listNo]);
					mapListNo.put(strs[listNo], strs[listNo]);
				}
			}
		}
		//检查数据
		checkDate(lines,listAppHead,listAppItem);
		return listTable;
	}
	
	/**
	 * 检查数据
	 * @param lines
	 * @param listAppHead
	 * @param listAppItem
	 */
	private void checkDate(List lines,List<String> listAppHead,List<String> listAppItem){
		List<FptBillHead> fptBillHeads = new ArrayList<FptBillHead>();
		List<FptBillItem> fptBillItems = new ArrayList<FptBillItem>();
		Hashtable<String, FptBillHead> mapHead = new Hashtable<String, FptBillHead>();
		Hashtable<String, String> mapNumber = new Hashtable<String, String>();
		//用于查找流水号是否存在
		Hashtable<Integer, Boolean> numberMap = getSerialNumber();
		//用于查找单位是否存在
		Hashtable<String,Unit> mapUnit = getUnit();
		
		//用于查找申请表编号是否存在
		Hashtable<String,FptAppHead> mapAppHead = getFptAppHead(listAppHead);
		
		//用于查找申请表序号是否存在
		Hashtable<String,Hashtable<Integer,FptAppItem>> mapAppItem = getFptAppItem(listAppItem);
		
		Hashtable<String, Integer> mapIndex = getIndex();
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现验证数据【"
					+ i + "】行,请稍等...");
			
			//保存错误信息
			String errorInformation = "";
			
			List<String> line = (List<String>)lines.get(i);
			
			//流水号
			if(checkInt(line.get(mapIndex.get("流水号(必填)")))){
				if(numberMap.get(Integer.parseInt(line.get(mapIndex.get("流水号(必填)"))))!=null){
					errorInformation+="流水号"+line.get(mapIndex.get("流水号(必填)"))+"已经存在/";
				}
			}else{
				errorInformation+="流水号不能为空且必须是整数/";
			}
			
			//申请表编号
			FptAppHead fptAppHead = mapAppHead.get(line.get(mapIndex.get("申请表编号(必填)")));
			if(fptAppHead==null){
				errorInformation+="申请表编号找不到/";
			}
			
			//申请表序号
			FptAppItem fptAppItem = null;
			if(checkInt(line.get(mapIndex.get("申请表序号(必填)")))){
				Hashtable<Integer,FptAppItem> mapItem = mapAppItem.get(line.get(mapIndex.get("申请表编号(必填)")));
				if(mapItem!=null){
					fptAppItem = mapItem.get(Integer.parseInt(line.get(mapIndex.get("申请表序号(必填)"))));
					if(fptAppItem==null){
						errorInformation+="申请表序号找不到/";
					}
				}else{
					errorInformation+="申请表序号找不到/";
				}
			}else{
				errorInformation+="申请表序号不能为空且必须是整数/";
			}
			
			//申报数量
			if(RegexUtil.checkFloat(line.get(mapIndex.get("申报数量(必填)")))){
				double amount = Double.parseDouble(line.get(mapIndex.get("申报数量(必填)")));
				if(amount<=0){
					errorInformation+="申报数量必须大于0/";
				}
			}else{
				errorInformation+="申报数量不能为空且必须是数字/";
			}
			
			//交易数量
			if(RegexUtil.checkFloat(line.get(mapIndex.get("交易数量(必填)")))){
				double amount = Double.parseDouble(line.get(mapIndex.get("交易数量(必填)")));
				if(amount<=0){
					errorInformation+="交易数量必须大于/";
				}
			}else{
				errorInformation+="交易数量不能为空且必须是数字/";
			}
			
			//交易单位
			Unit nuit = mapUnit.get(line.get(mapIndex.get("交易单位(必填)")));
			if(nuit==null){
				errorInformation+="交易单位找不到/";
			}
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(line.get(mapIndex.get("日期(必填)")));
			} catch (ParseException e) {
				errorInformation+="日期格式错误/";
			}
			
			String key = ""+line.get(mapIndex.get("流水号(必填)"))+"/"+line.get(mapIndex.get("申请表编号(必填)"))+"/"
					+line.get(mapIndex.get("申报人(必填)"))+""+line.get(mapIndex.get("日期(必填)"));
			if(mapNumber.get(line.get(mapIndex.get("流水号(必填)")))!=null){//判断流水号
				if(!mapNumber.get(line.get(mapIndex.get("流水号(必填)"))).equals(key)){
					errorInformation+=errorInformation.equals("")?
							"流水号相同，申请表编号、申报人、日期必须相同":",流水号相同，申请表编号、申报人、日期必须相同";
				}
			}else{
				mapNumber.put(line.get(mapIndex.get("流水号(必填)")), key);
			}
			
			if("".equals(errorInformation)){
				saveData(line,mapHead,date,nuit,fptBillHeads,fptBillItems,fptAppHead,fptAppItem);
				list.add(line.toArray());
			}else{
				//保存是否有错误，用于保存true代表有
				boo = true;
			}
			line.add(errorInformation);
			listTable.add(line.toArray());
		}
	}
	
	public void saveData(List<String> line,Hashtable<String, FptBillHead> mapHead,
			Date date,Unit unit,List<FptBillHead> fptBillHeads,
			List<FptBillItem> fptBillItems,FptAppHead fptAppHead,FptAppItem fptAppItem){
		
		Hashtable<String, Integer> mapIndex = getIndex();
		
		String key = ""+line.get(mapIndex.get("流水号(必填)"))+"/"+line.get(mapIndex.get("申请表编号(必填)"))+"/"
				+line.get(mapIndex.get("申报人(必填)"))+""+line.get(mapIndex.get("日期(必填)"));
		FptBillHead head = mapHead.get(key);
		if(head==null){
			head = new FptBillHead();
			head.setAppNo(line.get(mapIndex.get("申请表编号(必填)")));
			head.setSerialNumber(Integer.parseInt(line.get(mapIndex.get("流水号(必填)"))));
			head.setReceiveDate(date);
			head.setCreateDate(new Date());
			Company com =(Company)CommonVars.getCurrUser().getCompany();
			head.setBillSort(FptInOutFlag.IN);
			head.setSysType(FptBusinessType.FPT_BILL_BACK);
			head.setAppState(String.valueOf(DeclareState.APPLY_POR));
			head.setCompany(com);
			head.setAclUser(CommonVars.getCurrUser());
			head.setCustomer(fptAppHead.getScmCoc());
			head.setOutEmsNo(fptAppHead.getEmsNo());
			head.setReceiveTradeCod(fptAppHead.getInTradeCode());
			head.setReceiveTradeName(fptAppHead.getInTradeName());
			head.setReceiveAgentCode(fptAppHead.getInAgentCode());
			head.setReceiveAgentName(fptAppHead.getInAgentName());
			head.setReceiveIsDeclaEm(line.get(mapIndex.get("申报人(必填)")));
			head.setIssueTradeCod(fptAppHead.getOutTradeCode());
			head.setIssueTradeName(fptAppHead.getOutTradeName());
			head.setReceiveNote(line.get(mapIndex.get("表头备注")));
			fptBillHeads.add(head);
			mapHead.put(key, head);
			listImg.add(fptBillHeads);
		}
		
		FptBillItem item = new FptBillItem();
		item.setAppGNo(Integer.parseInt(line.get(mapIndex.get("申请表序号(必填)"))));
		item.setTrGno(fptAppItem.getTrNo());
		item.setComplex(fptAppItem.getCodeTs());
		item.setCommName(fptAppItem.getName());
		item.setCommSpec(fptAppItem.getSpec());
		item.setTradeUnit(unit);
		item.setUnit(fptAppItem.getUnit());
		item.setTradeQty(Double.parseDouble(line.get(mapIndex.get("申报数量(必填)"))));
		item.setNote(line.get(mapIndex.get("表体备注")));
		item.setFptBillHead(head);
		item.setBillSort(FptInOutFlag.IN);
		item.setQty(Double.parseDouble(line.get(mapIndex.get("交易数量(必填)"))));
		if (FptInOutFlag.IN.equals(item.getFptBillHead().getBillSort())) {
			item.setInEmsNo(fptAppItem.getInEmsNo());
		}
		fptBillItems.add(item);
		listImg.add(fptBillItems);
	}
	
	/**
	 * 获取流水号
	 * @return
	 */
	public Hashtable<Integer, Boolean> getSerialNumber(){
		Hashtable<Integer, Boolean> numberMap = new Hashtable<Integer, Boolean>();
		//查找流水号
		List lsMumber = fptManageAction.findFptBillHeadSerialNumber(request,FptInOutFlag.IN);;
		for (int i = 0; i < lsMumber.size(); i++) {
			if(lsMumber.get(i)!=null){
				numberMap.put(Integer.parseInt(lsMumber.get(i).toString()), true);
			}
		}
		return numberMap;
	}
	
	/**
	 * 获取申请单表体
	 * @return
	 */
	public Hashtable<String,Hashtable<Integer,FptAppItem>> getFptAppItem(List list){
		
		Hashtable<String,Hashtable<Integer,FptAppItem>> mapApp = new Hashtable<String,Hashtable<Integer,FptAppItem>>();
		List<FptAppItem> dataSource = fptManageAction.findFptAppItemToFptBillItem(new Request(CommonVars.getCurrUser()), FptInOutFlag.IN, list);
		for (int i = 0; i < dataSource.size(); i++) {
			FptAppItem app = dataSource.get(i);
			if(app.getFptAppHead()==null){
				continue;
			}
			String appNo = app.getFptAppHead().getAppNo();
			Hashtable<Integer,FptAppItem> MapItem = new Hashtable<Integer,FptAppItem>();
			MapItem.put(app.getListNo(), app);
			mapApp.put(appNo, MapItem);
		}
		return mapApp;
	}
	
	/**
	 * 获取申请单表头
	 * @return
	 */
	public Hashtable<String,FptAppHead> getFptAppHead(List list){
		Hashtable<String,FptAppHead> mapApp = new Hashtable<String,FptAppHead>();
		List<FptAppHead> dataSource = fptManageAction.findFptAppHead(new Request(CommonVars.getCurrUser()), FptInOutFlag.IN,list);
		for (int i = 0; i < dataSource.size(); i++) {
			FptAppHead app = dataSource.get(i);
			mapApp.put(app.getAppNo(), app);
		}
		return mapApp;
	}
	
	public Hashtable<String,Unit> getUnit(){
		Hashtable<String,Unit> mapUnit = new Hashtable<String,Unit>();
		List<Unit> lsUnit=fptManageAction.findUnit(request);
		for (int i = 0; i < lsUnit.size(); i++) {
			Unit unit = lsUnit.get(i);
			mapUnit.put(unit.getName(),unit);
		}
		return mapUnit;
	}
	
	public Boolean checkInt(String stu){
		Boolean boo = false;
		try {
			Integer.parseInt(stu);
			boo = true;
		} catch (Exception e) {
			boo =false;
		}
		return boo;
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
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnDLTpl());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
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
			btnOpenFile.setPreferredSize(new Dimension(80, 30));
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setSelectedFile(new File("C:\\Users\\Administrator\\Desktop\\料件退货单导入样板.xls"));
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportDataImg.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					//点击打开时清空listVFStockExg里面的数据
					listImg.clear();
					boo = false;
					new ImportFileDataRunnable().execute();
				}
			});
		}
		return btnOpenFile;
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
			jButton1.setPreferredSize(new Dimension(80, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileData().start();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 保存数据
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileData extends Thread {
		@Override
		public void run() {
				
				try {
					CommonProgress.showProgressDialog(DgImportDataImg.this);
					CommonProgress.setMessage("系统正在保存，请稍后...");
					
					if(listImg.size()<=0){
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgImportDataImg.this,
								"没有正确数据无法导入! ","提示",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(boo){
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgImportDataImg.this,
								"有错误无法导入! ","提示",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					//保存导入的数据至数据库中
					fptManageAction.saveFptBillHeadsAndFptBillItems(request,listImg,FptBusinessType.FPT_BILL_BACK);
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgImportDataImg.this,
							"保存数据成功！保存数据共 " + ((List)(listImg.get(0))).size() + "条","提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgImportDataImg.this, e2.getMessage());
				}finally{
					CommonProgress.closeProgressDialog();
				}
				DgImportDataImg.this.dispose();
				
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
			cbJF.setText("\u7e41\u8f6c\u7b80");
		}
		return cbJF;
	}

	/**
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("\u7b2c\u4e00\u884c\u4e3a\u6807\u9898\u884c");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
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
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);

		}
		return jPanel1;
	}

	/**对倒入的错误数据进行删除
	 * This method initializes btnDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除错误");
			btnDel.setPreferredSize(new Dimension(60,30));
			btnDel.addActionListener(new java.awt.event.ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					//改变错误状态
					boo=false;
					initJtable(list);
				}
			});
		}
		return btnDel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}
	
	
	/**
	 * 初始化Jtable
	 * @param list
	 */
	public void initJtable(List list){
		jlma=new JTableListModelAdapter() {
				@Override
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("流水号(必填)", "", 70));
					list.add(addColumn("申请表编号(必填)", "", 100));
					list.add(addColumn("申报人(必填)", "", 80));
					list.add(addColumn("日期(必填)", "", 80));
					list.add(addColumn("表头备注", "", 60));
					list.add(addColumn("申请表序号(必填)", "", 90));
					list.add(addColumn("申报数量(必填)", "", 60));
					list.add(addColumn("交易单位(必填)", "", 60));
					list.add(addColumn("交易数量(必填)", "", 60));
					list.add(addColumn("表体备注", "", 60));
					list.add(addColumn("错误信息", "", 400));
					return list;
				}
			};
		tableModel = new JTableListModel(table, list,jlma);
		TableCellRenderer boolTcr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				if ("".equals(value)||value==null) {
					if (row == table.getSelectedRow()) {
						setForeground(table.getSelectionForeground());
						setBackground(table.getSelectionBackground());
					} else {
						setForeground(table.getForeground());
						setBackground(table.getBackground());
					}
				}else{
					this.setBackground(new java.awt.Color(255, 255, 150)); 
				}
				return this;
			}
		};
		table.getColumnModel().getColumn(table.getColumnCount() - 1)
				.setCellRenderer(boolTcr);
	}
	
	/**
	 * 当找不到对应的列名时返回-1否则返回列名所对应的下标
	 * @param stu
	 * @return
	 */
	public Hashtable<String, Integer> getIndex(){
		Hashtable<String, Integer> map = new Hashtable<String, Integer>();
		List<JTableListColumn> jTableListColumn = jlma.getColumns();
		for (int j = 0; j < jTableListColumn.size(); j++) {
			JTableListColumn jtlc = jTableListColumn.get(j);
			map.put(jtlc.getCaption(), j-1);
		}
		return map;
	}
	
	public String[] getProperty(){
		List<JTableListColumn> list = jlma.getColumns();
		String[] property = new String[list.size()-2];
		for (int i = 1; i < list.size()-1; i++) {
			property[i-1] = list.get(i).getProperty();
		}
		return property;
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
	 * 繁转简
	 * 
	 * @param source
	 * @return
	 */
	private List changStrs(List source) {
		List newList = new ArrayList();
		for (int i = 0; i < source.size(); i++) {
			newList.add(changeStr(source.get(i).toString()));
		}
		return newList;
	}
	
	/**
	 * 繁转简
	 * 
	 * @param s
	 * @return
	 */
	private String changeStr(String s) {
		String yy = "";
		char[] xxx = s.toCharArray();
		for (int i = 0; i < xxx.length; i++) {
			String z = String.valueOf(xxx[i]);
			if (String.valueOf(xxx[i]).getBytes().length >1) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}
	private JButton getBtnDLTpl() {
		if (btnDLTpl == null) {
			btnDLTpl = new JButton("下载导入模版");
			btnDLTpl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FileUtils.exportTableToExcel(tableModel, getParent());
				}
			});
		}
		return btnDLTpl;
	}
}