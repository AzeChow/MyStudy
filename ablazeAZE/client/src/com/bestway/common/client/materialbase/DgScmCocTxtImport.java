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
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Trade;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.system.action.SystemAction;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.ScmCocControl;
import com.bestway.common.materialbase.entity.ScmCocForInput;
import com.bestway.ui.winuicontrol.JDialogBase;

import javax.swing.JCheckBox;

public class DgScmCocTxtImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTableListModel tableModel = null;
	
	private JTableListModel tableModelDetail = null;

	private File txtFile = null;

	private JRadioButton jRadioButton = null;

	private Hashtable gbHash = null;  //  @jve:decl-index=0:

	private Hashtable headHash = null;

	private MaterialManageAction materialManageAction = null;

	private JButton jButton3 = null;

	private Hashtable hs = null;
	
	private BillTemp bill = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JCheckBox jCheckBox = null;

	private JButton jsetColumn = null;

	private JCheckBox jcbIsOverwrite = null;

	private JCheckBox jcbCheckTitle = null;

	private JButton btnParameterSet = null;
	
//	private HashMap mriefName = new HashMap();  //  @jve:decl-index=0:
//	
//	private HashMap mriefCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap countryName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap countryCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap portLinName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap portLinCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap country2Name = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap country2Code = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap customsName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap customsCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap belongToCustomsName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap belongToCustomsCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap transfName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap transfCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap predockName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap predockCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap wrapTypeName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap wrapTypeCode = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap tradeModeName = new HashMap();  //  @jve:decl-index=0:
	
	private HashMap tradeModeCode = new HashMap();  //  @jve:decl-index=0:
	
	private CustomBaseAction customBaseAction =null;
	
	private SystemAction systemAction = null;//SystemAction接口

	/**
	 * This is the default constructor
	 */
	public DgScmCocTxtImport() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars.getApplicationContext().getBean(
				"materialManageAction");
		customBaseAction = (CustomBaseAction) CommonVars.getApplicationContext()
		.getBean("customBaseAction");
		systemAction = (SystemAction) CommonVars.getApplicationContext()
		.getBean("systemAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.SCM_COCTXT, this
						.getScmCocTxtTableColumnListAll());
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
		this.setTitle("客户供应商导入");
		
	}
	private List getScmCocTxtTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "bill1", 100));
		list.add(new JTableListColumn("全称", "bill2", 100));
		list.add(new JTableListColumn("简称", "bill3", 120));
		list.add(new JTableListColumn("是否客户(是/否表示)", "bill4", 140));
		list.add(new JTableListColumn("是否供应商(是/" +
				"否表示)", "bill5", 140));
		return list;
	}
	private List getScmCocTxtTableColumnListAll() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("编码", "scmCoc.code", 100));
		list.add(new JTableListColumn("全称", "scmCoc.name", 100));
		list.add(new JTableListColumn("简称", "scmCoc.fname", 120));
		list.add(new JTableListColumn("关务海关注册公司", "scmCoc.brief.name", 150));
		list.add(new JTableListColumn("是否合作者", "scmCoc.isCollaborater", 80));
		list.add(new JTableListColumn("是否经营单位", "scmCoc.isWork", 80));
		list.add(new JTableListColumn("是否结转出口",
				"scmCoc.isTransferFactoryOut", 80));
		list.add(new JTableListColumn("是否直接出口", "scmCoc.isStraightOut", 80));
		list.add(new JTableListColumn("是否结转进口", "scmCoc.isTransferFactoryIn",
				80));
		list.add(new JTableListColumn("是否直接进口", "scmCoc.isStraightIn", 80));
		list.add(new JTableListColumn("是否国内购买", "scmCoc.isHomeBuy", 80));
		list.add(new JTableListColumn("是否国内销售客户", "scmCoc.isLeiXiao", 80));
		list.add(new JTableListColumn("是否客户", "scmCoc.isCustomer", 140));
		list.add(new JTableListColumn("是否供应商", "scmCoc.isManufacturer", 140));
		list.add(new JTableListColumn("运抵国", "scmCoc.country.name", 80));
		list.add(new JTableListColumn("指运港", "scmCoc.portLin.name", 80));
		list.add(new JTableListColumn("产销国", "scmCoc.country2.name", 80));
		list.add(new JTableListColumn("出口口岸", "scmCoc.customs.name", 80));
		list.add(new JTableListColumn("所属海关", "scmCoc.belongToCustoms.name",
				80));
		list.add(new JTableListColumn("运输方式", "scmCoc.transf.name", 80));
		list
				.add(new JTableListColumn("运输耗时",
						"scmCoc.transportationTime", 80));
		list.add(new JTableListColumn("码头", "scmCoc.predock.name", 80));
		list.add(new JTableListColumn("包装种类", "scmCoc.warp.name", 80));
		list.add(new JTableListColumn("贸易方式", "scmCoc.trade.tradeFname", 80));
		list.add(new JTableListColumn("送货距离", "scmCoc.deliveryDistance", 80));
		list.add(new JTableListColumn("国际代码", "scmCoc.flatCode", 80));
		list.add(new JTableListColumn("英文名称", "scmCoc.engName", 80));
		list.add(new JTableListColumn("联系人", "scmCoc.linkMan", 80));
		list.add(new JTableListColumn("联系电话", "scmCoc.linkTel", 80));
		list.add(new JTableListColumn("传真号", "scmCoc.fax", 80));
		list.add(new JTableListColumn("地址", "scmCoc.addre", 80));
		list.add(new JTableListColumn("银行及账号", "scmCoc.bank", 80));
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
			jToolBar.add(getBtnParameterSet());
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJsetColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getJcbIsOverwrite());
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

	class ImportFileDataRunnable extends Thread 
	{
		public void run() 
		{
			List list = new ArrayList();
			List headList = new ArrayList();
			try 
			{
				CommonProgress.showProgressDialog(DgScmCocTxtImport.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				initConditionList();// 初始化查询条件
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
//				headList = (List) list.get(0);
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} 
			catch(Exception e) 
			{
					e.printStackTrace();
			}
			finally
			{
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
		countryName.clear();
		countryCode.clear();
		portLinName.clear();
		portLinCode.clear();
		country2Name.clear();
		country2Code.clear();
		customsName.clear();
		customsCode.clear();
		belongToCustomsName.clear();
		belongToCustomsCode.clear();
		transfName.clear();
		transfCode.clear();
		predockName.clear();
		predockCode.clear();
		wrapTypeName.clear();
		wrapTypeCode.clear();
		tradeModeName.clear();
		tradeModeCode.clear();
		
		//关务海关注册公司
//		List BriefName = customBaseAction.findBrief("", "");
//		for (int i = 0; i < BriefName.size(); i++) {
//			Brief brief = (Brief) BriefName.get(i);
//			mriefName.put(brief.getName(), brief);
//			mriefCode.put(brief.getCode(), brief);
//		}
//		System.out.println("关务海关注册公司"+BriefName.size());
		// 运抵国、 产销国
		List CountryName = customBaseAction.findCountry("", "");
		for (int i = 0; i < CountryName.size(); i++) {
			Country country = (Country) CountryName.get(i);
			countryName.put(country.getName(), country);
			countryCode.put(country.getCode(), country);
		}
		System.out.println("运抵国、 产销国、 出口口岸、所属海关"+CountryName.size());
		// 所属海关、 出口口岸
		List CustomsName = customBaseAction.findCustoms("", "");
		for (int i = 0; i < CustomsName.size(); i++) {
			Customs customs = (Customs) CustomsName.get(i);
			customsName.put(customs.getName(), customs);
			customsCode.put(customs.getCode(), customs);
		}
		// 指运港
		List PortLinName = customBaseAction.findPortLin("", "");
		for (int i = 0; i < PortLinName.size(); i++) {
			PortLin portLin = (PortLin) PortLinName.get(i);
			portLinName.put(portLin.getName(), portLin);
			portLinCode.put(portLin.getCode(), portLin);
		}
		System.out.println("指运港"+PortLinName.size());
		//运输方式
		List ModeOfTransport = customBaseAction.findTransf("", "");
		for (int i = 0; i < ModeOfTransport.size(); i++) {
			Transf transf = (Transf) ModeOfTransport.get(i);
			transfName.put(transf.getName(), transf);
			transfCode.put(transf.getCode(), transf);
		}
		System.out.println("运输方式"+ModeOfTransport.size());
		//码头
		List Predock = customBaseAction.findPreDock("", "");
		for (int i = 0; i < Predock.size(); i++) {
			PreDock preDock = (PreDock) Predock.get(i);
			predockName.put(preDock.getName(), preDock);
			predockCode.put(preDock.getCode(), preDock);
		}
		System.out.println("码头"+Predock.size());
		//包装种类
		List WrapType = customBaseAction.findWrap();
		for (int i = 0; i < WrapType.size(); i++) {
			Wrap wrap = (Wrap) WrapType.get(i);
			wrapTypeName.put(wrap.getName(), wrap);
			wrapTypeCode.put(wrap.getCode(), wrap);
		}
		System.out.println("包装种类"+WrapType.size());
		//贸易方式
		List TradeMode = customBaseAction.findTrade("", "");
		for (int i = 0; i < TradeMode.size(); i++) {
			Trade trade = (Trade) TradeMode.get(i);
			tradeModeName.put(trade.getName(), trade);
			tradeModeCode.put(trade.getCode(), trade);
		}
		System.out.println("贸易方式"+TradeMode.size());
	}
//
//	private void infTojHsTable() {
//		if (gbHash == null) {
//			gbHash = new Hashtable();
//			List list = CustomBaseList.getInstance().getGbtobigs();
//			for (int i = 0; i < list.size(); i++) {
//				Gbtobig gb = (Gbtobig) list.get(i);
//				gbHash.put(gb.getBigname(), gb.getName());
//			}
//		}
//	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}
//
//	private String changeStr(String s) { // 繁转简
//		String yy = "";
//		char[] xxx = s.toCharArray();
//		for (int i = 0; i < xxx.length; i++) {
//			String z = String.valueOf(xxx[i]);
//			if (String.valueOf(xxx[i]).getBytes().length == 2) {
//				if (gbHash.get(String.valueOf(xxx[i])) != null) {
//					z = (String) gbHash.get(String.valueOf(xxx[i]));
//				}
//			}
//			yy = yy + z;
//		}
//		return yy;
//	}

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
//			newStrs[i] = changeStr(source[i]);
			newStrs[i]=CommonClientBig5GBConverter.getInstance().big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	private List parseTxtFile() 
	{
		Map<String,ScmCoc> mapSecCocs = new HashMap<String,ScmCoc>();
		List<ScmCoc> secCocs = materialManageAction.findScmCocs(new Request(CommonVars.getCurrUser()));
		for (int i = 0; i < secCocs.size(); i++) {
			ScmCoc semCoc = secCocs.get(i);

			String code = semCoc.getCode()==null?"":semCoc.getCode();
			String name = semCoc.getName()==null?"":semCoc.getName();
			String briefCode = semCoc.getBrief()==null?"":semCoc.getBrief().getCode();
			String isCustomer = semCoc.getIsCustomer()==null?"":semCoc.getIsCustomer().toString();
			String isManufacturer = semCoc.getIsManufacturer()==null?"":semCoc.getIsManufacturer().toString();
			
			//key = 【编码】+【名称】【海关注册公司】【是否客户】【是否供应商】
			String key = code+"/"+name+"/"+briefCode+"/"+isCustomer+"/"+isManufacturer;
			mapSecCocs.put(key, semCoc);
		}
		
		
		List allList = new ArrayList();	
		boolean ischange = true;
		if(getJCheckBox().isSelected()) 
		{
			infTojHsTable();
		} 
		else 
		{
			ischange = false;
		}
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if(suffix.equals("xls")) 
		{
			//
			// 导入xls
			//			
			if(jcbCheckTitle.isSelected())
			{
				lines = FileReading.readExcel(txtFile, 2, null);
			} 
			else 
			{
				lines = FileReading.readExcel(txtFile, 1, null);
			}			
		}
		else 
		{
			//
			// 导入txt
			//
			if(jcbCheckTitle.isSelected())
			{
				lines = FileReading.readTxt(txtFile, 2, null);
			} 
			else 
			{
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}

		ArrayList list = new ArrayList(); 
		List<String> lsIndex = InputTableColumnSetUtils
		.getColumnFieldIndex(InputTableColumnSet.SCM_COCTXT);
		int zcount = lsIndex.size();// 字段数目
		
		List ScmCocControllist = materialManageAction.findScmCocControl(new Request(CommonVars
				.getCurrUser()));
		if (ScmCocControllist.size() == 0 || ScmCocControllist.get(0) == null) {
			JOptionPane.showMessageDialog(DgScmCocTxtImport.this,
					"请进行导入参数设置！");
			return new ArrayList();
		}
		List lsDestColumnSet = systemAction.findInputTableColumnSet(
				new Request(CommonVars.getCurrUser()), InputTableColumnSet.SCM_COCTXT);
		/**
		 * 栏位设置里是否选上了
		 */
		boolean briefName = false;//海关注册公司
		boolean isCustomer = false;//是否客户
		boolean isManufacturer = false;// 是否供应商
		
		/**
		 * 导入栏位里所填的是/否
		 */
		boolean isTransferFactoryOut = false;//是否结转出口
		boolean isTransferFactoryIn = false;//是否结转进口
		boolean isStraightIn = false;//是否直接进口
		boolean isHomeBuy = false; //是否国内购买
		boolean isStraightOut = false; //是否直接出口
		boolean isLeiXiao = false;  //是否国内销售客户
		
		boolean isbriefNameNull = false; //海关注册公司是否为空 
		
		if(lsDestColumnSet.size() == 0){
			briefName = true;
			isCustomer = true;
			isManufacturer = true;
		}
		for (int i = 0; i < lsDestColumnSet.size(); i++) {
			
			InputTableColumnSet inputTableColumnSet = (InputTableColumnSet) lsDestColumnSet
					.get(i);
//			if(inputTableColumnSet.getColumnCaption().equals("是否结转出口")){
//				isTransferFactoryOut = true;
//			}
//			else if(inputTableColumnSet.getColumnCaption().equals("是否结转进口")){
//				isTransferFactoryIn = true;
//			}
		    if(inputTableColumnSet.getColumnCaption().equals("关务海关注册公司")){
				System.out.println("SET 公司 TRUE");
				briefName = true;
			}
//			else if(inputTableColumnSet.getColumnCaption().equals("是否直接出口")){
//				isStraightOut = true;
//			}
//			else if(inputTableColumnSet.getColumnCaption().equals("是否国内销售客户")){
//				isLeiXiao = true;
//			}
			else if(inputTableColumnSet.getColumnCaption().equals("是否客户")){
				isCustomer = true;
			}
			else if(inputTableColumnSet.getColumnCaption().equals("是否供应商")){
				isManufacturer = true;
			}
//			else if(inputTableColumnSet.getColumnCaption().equals("是否直接进口")){
//				isStraightIn = true;
//			}
//			else if(inputTableColumnSet.getColumnCaption().equals("是否国内购买")){
//				isHomeBuy = true;
//			}
		}
		ScmCocControl data = (ScmCocControl) ScmCocControllist
				.get(0);
		for(int i = 0; i < lines.size(); i++)
		{
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for(int j = 0; j < line.size(); j++) 
			{
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}

			if(ischange)
			{
				strs = changStrs(strs);
			}
			ScmCocForInput scmCocForInput = new ScmCocForInput();
			ScmCoc scmCoc = new ScmCoc();
			scmCocForInput.setScmCoc(scmCoc);
			StringBuffer sb = new StringBuffer("");
			String err = "";
			BillTemp obj = new BillTemp();
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				System.out.println("import value is :"+value);
				String columnField = lsIndex.get(j);
				if("scmCoc.code".equals(columnField)) 
				{
					if(value != null && !value.equals("")) 
					{
						scmCoc.setCode(value);
					} 
					else 
					{
						sb.append("警告：必须输入编码!");
					}					
				} 
				else if ("scmCoc.name".equals(columnField)) 
				{
					scmCoc.setName(value);
				} 
				else if ("scmCoc.fname".equals(columnField)) {
					scmCoc.setFname(value);
				} 
				//是否客户
				else if ("scmCoc.isCustomer".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsCustomer(new Boolean(true));
					}else{
						if(isTransferFactoryOut || isStraightOut || isLeiXiao){
							scmCoc.setIsCustomer(new Boolean(true));
						}else{
							scmCoc.setIsCustomer(new Boolean(false));
						}
					}
				} 
				//是否经营单位
				else if ("scmCoc.isWork".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsWork(new Boolean(true));
					}else{
						scmCoc.setIsWork(new Boolean(false));
					}
				} 
				//是否国内销售客户
				else if ("scmCoc.isLeiXiao".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsLeiXiao(new Boolean(true));
						isLeiXiao = true;
					}else{
						scmCoc.setIsLeiXiao(new Boolean(false));
						isLeiXiao = false;
					}
				} 
				// 是否供应商
				else if ("scmCoc.isManufacturer".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsManufacturer(new Boolean(true));
					}else{
						if(isTransferFactoryIn || isStraightIn || isHomeBuy){
							scmCoc.setIsManufacturer(new Boolean(true));
						}else{
							scmCoc.setIsManufacturer(new Boolean(false));
						}
							
					}
				}
				//是否合作者
				else if ("scmCoc.isCollaborater".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsCollaborater(new Boolean(true));
					}else{
						scmCoc.setIsCollaborater(new Boolean(false));
					}
				}
				//是否结转出口
				else if ("scmCoc.isTransferFactoryOut".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsTransferFactoryOut(new Boolean(true));
						isTransferFactoryOut = true;
					}else{
						scmCoc.setIsTransferFactoryOut(new Boolean(false));
						isTransferFactoryOut = false;
					}
				}
				//是否直接出口
				else if ("scmCoc.isStraightOut".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsStraightOut(new Boolean(true));
						isStraightOut = true;
					}else{
						scmCoc.setIsStraightOut(new Boolean(false));
						isStraightOut = false;
					}
				}
				//是否结转进口
				else if ("scmCoc.isTransferFactoryIn".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsTransferFactoryIn(new Boolean(true));
						isTransferFactoryIn = true;
					}else{
						scmCoc.setIsTransferFactoryIn(new Boolean(false));
						isTransferFactoryIn = false;
					}
				}
				//是否直接进口
				else if ("scmCoc.isStraightIn".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsStraightIn(new Boolean(true));
						isStraightIn = true;
					}else{
						scmCoc.setIsStraightIn(new Boolean(false));
						isStraightIn = false;
					}
				}
				//是否国内购买
				else if ("scmCoc.isHomeBuy".equals(columnField)) {
					if("是".equals(value)||"1".equals(value)){
						scmCoc.setIsHomeBuy(new Boolean(true));
						isHomeBuy = true; 
					}else{
						scmCoc.setIsHomeBuy(new Boolean(false));
						isHomeBuy = false; 
					}
				}
				//海关注册公司
				else if ("scmCoc.brief.name".equals(columnField)) {
					if (value != null && !"".equals(value)) {
						Brief brief = null;
						if (data.getBriefName() != null
								&& data.getBriefName().equals("代码")) {
							List listBrief=customBaseAction.findBrief("code", value.trim());
							if(!listBrief.isEmpty()){
								brief=(Brief)listBrief.get(0);
							}
//							brief = (Brief) mriefCode.get(value
//									.trim());
						} else {
//							brief = (Brief) mriefName.get(value
//									.trim());
							List listBrief=customBaseAction.findBrief("name", value.trim());
							if(!listBrief.isEmpty()){
								brief=(Brief)listBrief.get(0);
							}
						}
						if(null==brief && !"".equals(value)){
							sb.append("警告:第" + (i + 1) + "行所输入海关注册公司,在系统中未能找到!");
						}else{
							scmCoc.setBrief(brief);
						}
					}else{
						isbriefNameNull = true;
					}
				}
				//运抵国
				else if ("scmCoc.country.name".equals(columnField)) {
					if (value != null) {
						Country country = null;
						if (data.getCountryName() != null
								&& data.getCountryName().equals("代码")) {
							country = (Country) countryCode.get(value
									.trim());
						} else {
							country = (Country) countryName.get(value
									.trim());
						}
						if (country == null) {
							sb.append("警告:第" + (i + 1) + "行所输入运抵国,在系统中未能找到!");
						}
						scmCoc.setCountry(country);
					}
				}
				//指运港
				else if ("scmCoc.portLin.name".equals(columnField)) {
					if (value != null) {
						PortLin portLin = null;
						if (data.getPortLinName() != null
								&& data.getPortLinName().equals("代码")) {
							portLin = (PortLin) portLinCode.get(value
									.trim());
						} else {
							portLin = (PortLin) portLinName.get(value
									.trim());
						}
						if (portLin == null) {
							sb.append("警告:第" + (i + 1) + "行所输入指运港,在系统中未能找到!");
						}
						scmCoc.setPortLin(portLin);
					}
				}
				//产销国
				else if ("scmCoc.country2.name".equals(columnField)) {
					if (value != null) {
						Country country = null;
						if (data.getCountry2Name() != null
								&& data.getCountry2Name().equals("代码")) {
							country = (Country) countryCode.get(value
									.trim());
						} else {
							country = (Country) countryName.get(value
									.trim());
						}
						if (country == null) {
							sb.append("警告:第" + (i + 1) + "行所输入产销国,在系统中未能找到!");
						}
						scmCoc.setCountry2(country);
					}
				}
				//出口口岸
				else if ("scmCoc.customs.name".equals(columnField)) {
					if (value != null) {
						Customs customs = null;
						if (data.getCustomsName() != null
								&& data.getCustomsName().equals("代码")) {
							customs = (Customs) customsCode.get(value
									.trim());
						} else {
							customs = (Customs) customsName.get(value
									.trim());
						}
						if (customs == null) {
							sb.append("警告:第" + (i + 1) + "行所输入出口口岸,在系统中未能找到!");
						}
						scmCoc.setCustoms(customs);
					}
				}
				//所属海关
				else if ("scmCoc.belongToCustoms.name".equals(columnField)) {
					if (value != null) {
						Customs belongToCustoms = null;
						if (data.getBelongToCustomsName() != null
								&& data.getBelongToCustomsName().equals("代码")) {
							belongToCustoms = (Customs) customsCode.get(value
									.trim());
						} else {
							belongToCustoms = (Customs) customsName.get(value
									.trim());
						}
						if (belongToCustoms == null) {
							sb.append("警告:第" + (i + 1) + "行所输入所属海关,在系统中未能找到!");
						}
						scmCoc.setBelongToCustoms(belongToCustoms);
					}
				}
				//运输方式
				else if ("scmCoc.transf.name".equals(columnField)) {
					if (value != null) {
						Transf transf = null;
						if (data.getTransfName() != null
								&& data.getTransfName().equals("代码")) {
							transf = (Transf) transfCode.get(value
									.trim());
						} else {
							transf = (Transf) transfName.get(value
									.trim());
						}
						if (transf == null) {
							sb.append("警告:第" + (i + 1) + "行所输入运输方式,在系统中未能找到!");
						}
						scmCoc.setTransf(transf);
					}
				}
				//码头
				else if ("scmCoc.predock.name".equals(columnField)) {
					if (value != null) {
						PreDock preDock = null;
						for(Object o : predockCode.keySet()){    
							PreDock s =(PreDock)predockCode.get(o);
						}   
						for(Object o : predockName.keySet()){    
							PreDock s =(PreDock)predockName.get(o);
						}   
						if (data.getPredock()!= null
								&& data.getPredock().equals("代码")) {
							preDock = (PreDock) predockCode.get(value
									.trim());
						} else {
							preDock = (PreDock) predockName.get(value
									.trim());
						}
						if (preDock == null) {
							sb.append("警告:第" + (i + 1) + "行所输入码头,在系统中未能找到!");
						}
						scmCoc.setPredock(preDock);
					}
				}
				//包装种类
				else if ("scmCoc.warp.name".equals(columnField)) {
					if (value != null) {
						Wrap wrap = null;
						if (data.getWrapType() != null
								&& data.getWrapType().equals("代码")) {
							wrap = (Wrap) wrapTypeCode.get(value
									.trim());
						} else {
							wrap = (Wrap) wrapTypeName.get(value
									.trim());
						}
						if (wrap == null) {
							sb.append("警告:第" + (i + 1) + "行所输入包装种类,在系统中未能找到!");
						}
						scmCoc.setWarp(wrap);
					}
				}
				//贸易方式
				else if ("scmCoc.trade.tradeFname".equals(columnField)) {
					if (value != null) {
						Trade trade = null;
						if (data.getTradeMode() != null
								&& data.getTradeMode().equals("代码")) {
							trade = (Trade) tradeModeCode.get(value
									.trim());
						} else {
							trade = (Trade) tradeModeName.get(value
									.trim());
						}
						if (trade == null) {
							sb.append("警告:第" + (i + 1) + "行所输入贸易方式,在系统中未能找到!");
						}
						scmCoc.setTrade(trade);
					}
				}
				//国际代码
				else if ("scmCoc.flatCode".equals(columnField)) 
				{
					scmCoc.setFlatCode(value);
				} 
				//送货距离
				else if ("scmCoc.deliveryDistance".equals(columnField)) 
				{
					try{
						scmCoc.setDeliveryDistance(Double.valueOf(value));
					}catch(Exception e){
						sb.append("警告:第" + (i + 1) + "行所输入送货距离必须为数字!");
						e.printStackTrace();
					}
				} 
				//英文名称
				else if ("scmCoc.engName".equals(columnField)) 
				{
					scmCoc.setEngName(value);
				} 
				//联系人
				else if ("scmCoc.linkMan".equals(columnField)) 
				{
					scmCoc.setLinkMan(value);
				} 
				//联系电话
				else if ("scmCoc.linkTel".equals(columnField)) 
				{
					scmCoc.setLinkTel(value);
				} 
				//传真号
				else if ("scmCoc.fax".equals(columnField)) 
				{
					scmCoc.setFax(value);
				} 
				//地址
				else if ("scmCoc.addre".equals(columnField)) 
				{
					scmCoc.setAddre(value);
				} 
				//银行+帐号
				else if ("scmCoc.bank".equals(columnField)) 
				{
					scmCoc.setBank(value);
				} 
				//运输耗时
				else if ("scmCoc.transportationTime".equals(columnField)) 
				{
					try{
					if(null!=value){
						scmCoc.setTransportationTime(Double.valueOf(value));
					}
					}catch(Exception e){
						sb.append("警告:第" + (i + 1) + "行所输入运输耗时必须为数字!");
						e.printStackTrace();
					}
				}
				if(isTransferFactoryOut || isStraightOut || isLeiXiao){
					if(!isCustomer){
						scmCoc.setIsCustomer(new Boolean(true));
					}
				}
				if(isTransferFactoryIn || isStraightIn || isHomeBuy){
					if(!isManufacturer){
						scmCoc.setIsManufacturer(new Boolean(true));
					}
				}
				scmCocForInput.setErrinfo(sb.toString());
			}
			
			if(isTransferFactoryOut || isTransferFactoryIn ){
				if(!briefName){
					JOptionPane.showMessageDialog(DgScmCocTxtImport.this,
					"类别为结转进口或结转出口，关务海关注册公司不能为空！");
					return new ArrayList();
				}else{
					if(isbriefNameNull){
						JOptionPane.showMessageDialog(DgScmCocTxtImport.this,
						"类别为结转进口或结转出口，关务海关注册公司不能为空！");
						return new ArrayList();
					}
				}
			}
			ScmCoc semCoc =scmCocForInput.getScmCoc();
			String code = semCoc.getCode()==null?"":semCoc.getCode();
			String name = semCoc.getName()==null?"":semCoc.getName();
			String briefCode = semCoc.getBrief()==null?"":semCoc.getBrief().getCode();
			String customer = semCoc.getIsCustomer()==null?"":semCoc.getIsCustomer().toString();
			String manufacturer = semCoc.getIsManufacturer()==null?"":semCoc.getIsManufacturer().toString();
			
			//key = 【编码】+【名称】【海关注册公司】【是否客户】【是否供应商】
			String key = code+"/"+name+"/"+briefCode+"/"+customer+"/"+manufacturer;
			if(mapSecCocs.get(key)==null){
				mapSecCocs.put(key, scmCocForInput.getScmCoc());
			}else{
				scmCocForInput.setErrinfo(scmCocForInput.getErrinfo()==null?null
						:scmCocForInput.getErrinfo()+"编码+名称+海关注册公司+是否客户+是否供应商不能重复！");
			}
			list.add(scmCocForInput);
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
						JOptionPane.showMessageDialog(DgScmCocTxtImport.this,
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
					ScmCocForInput obj = (ScmCocForInput) list
							.get(i);
					if (obj.getErrinfo() != null
							&& !obj.getErrinfo().equals("")) {
						JOptionPane.showMessageDialog(
								DgScmCocTxtImport.this,
								"有错误信息，不可保存！", "提示!", 2);
						// -----------------------------------------
//						File uploaderrdir = new File(uploaderr);
//						File uploaderrdirfile = new File(uploaderr + File.separator
//								+ txtFile.getName());
//						if (uploaderrdir.exists()) {
//							moveFile(txtFile, uploaderrdirfile);
//						}
						// -----------------------------------------
						return;
					}
				}
				materialManageAction.saveInputScmcoc(new Request(CommonVars
							.getCurrUser()),list,jcbIsOverwrite.isSelected());
				JOptionPane.showMessageDialog(DgScmCocTxtImport.this,
						"保存完毕!\n\n客户供应商数据: " + String.valueOf(list.size())
								,"提示", 2);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(DgScmCocTxtImport.this,
						"保存失败!\n\n客户供应商数据: " + String.valueOf(list.size())
								,"提示", 2);
				e.printStackTrace();
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
					DgScmCocTxtImport.this.dispose();
				}
			});
		}
		return jButton2;
	}
	
	private void infTojHsTable()
	{
		if(gbHash == null) 
		{
			gbHash = new Hashtable();
			List list = CustomBaseList.getInstance().getGbtobigs();
			for(int i = 0; i < list.size(); i++) 
			{
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
						.getTableColumnList(InputTableColumnSet.SCM_COCTXT);
			}
					/*public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("1.编码", "bill1", 100));
						list.add(addColumn("2.全称", "bill2", 100));
						list.add(addColumn("3.简称", "bill3", 120));
						list.add(addColumn("4.是否客户(是/否表示)", "bill4", 140));
						list.add(addColumn("5.是否供应商(是/否表示)", "bill5", 140));
						return list;
					}*/
				});
	}

//	 调出文件选择框
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
	/*private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("繁转简");
		}
		return jRadioButton;
	}*/

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
	private JButton getJsetColumn() 
	{
		if(jsetColumn == null) 
		{
			jsetColumn = new JButton();
			jsetColumn.setText("栏位设定");
			jsetColumn.addActionListener(new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.SCM_COCTXT);
						dg.setVisible(true);
						if(dg.isOk()) 
						{
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

	/**
	 * This method initializes btnParameterSet	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnParameterSet() {
		if (btnParameterSet == null) {
			btnParameterSet = new JButton();
			btnParameterSet.setText("参数设定");
			btnParameterSet
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgScmCocTxtImportSet dg = new DgScmCocTxtImportSet();
					dg.setVisible(true);
				}
			});
		}
		return btnParameterSet;
	}
}// @jve:decl-index=0:visual-constraint="10,10"
