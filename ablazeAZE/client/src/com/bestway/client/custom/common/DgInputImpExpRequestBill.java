package com.bestway.client.custom.common;

/**
 * 
 * 刘民添加部分注释 修改时间: 2008-11-24
 * 
 * @author ? // change the template for this generated type comment go to Window
 *         - Preferences - Java - Code Style - Code Templates 进出口申请单数据导入接口
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.countrycode.Country;
import com.bestway.bcus.custombase.entity.countrycode.Customs;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Transf;
import com.bestway.bcus.custombase.entity.parametercode.Wrap;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImpExpCommodityInfo;
import com.bestway.bcus.enc.entity.ImpExpRequestBill;
import com.bestway.bcus.enc.entity.InputInExRequestBillOrder;
import com.bestway.bcus.enc.entity.TempImpExpRequestBillForInput;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CaleUtil;
import com.bestway.common.CommonUtils;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.common.constant.ProjectType;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.bcus.system.entity.CompanyOther;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
@SuppressWarnings("unchecked")
public class DgInputImpExpRequestBill extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JButton btnInput = null;

	private JButton btnExit = null;
	/** 所有列 */
	private List columnList = null;
	/** 退出 */
	private boolean isInport = false;
	/** 单据类型 */
	private int billType = -1;

	private CustomBaseAction customBaseAction = null;

	private EncAction encAction = null;
	/** 料件管理操作接口 */
	private MaterialManageAction materialManageAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelHead = null;

	private JCheckBox cbAmounTaccumulate = null;
	/*** 繁转简 */
	private Hashtable gbHash = null; // @jve:decl-index=0:

	private List billPtNoList = new ArrayList(); // @jve:decl-index=0:
	/** 导入文件 */
	private File txtFile = null;

	private HashMap manufacturerListName = new HashMap(); // @jve:decl-index=0:

	private HashMap manufacturerListCode = new HashMap(); // @jve:decl-index=0:

	private HashMap customerListName = new HashMap(); // @jve:decl-index=0:

	private HashMap customerListCode = new HashMap(); // @jve:decl-index=0:

	private HashMap countryrListCode = new HashMap(); // @jve:decl-index=0:

	private HashMap countryrListName = new HashMap(); // @jve:decl-index=0:

	private HashMap currListCode = new HashMap(); // @jve:decl-index=0:

	private HashMap currListName = new HashMap(); // @jve:decl-index=0:

	// private HashMap materielListCode = new HashMap();

	private HashMap materielListName = new HashMap(); // @jve:decl-index=0:

	private HashMap customsListCode = new HashMap(); // @jve:decl-index=0:

	private HashMap customsListName = new HashMap(); // @jve:decl-index=0:

	private HashMap transferModeListCode = new HashMap(); // @jve:decl-index=0:

	private HashMap transferModeListName = new HashMap(); // @jve:decl-index=0:
	private JToolBar jToolBar = null;

	private JButton btnSaveData = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JCheckBox cbCheckTitle = null;

	private JCheckBox cbBig5ConvertToGB = null;

	private JButton btnFieldSet = null;

	private JButton btnParameterSet = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel = null;

	private int projectType = ProjectType.BCUS;

	private JButton btnDel = null;

	private JCheckBox cbbCaleAmountPrice = null;

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	CompanyOther other = CommonVars.getOther();  //  @jve:decl-index=0:
	/**
	 * This method initializes
	 * 
	 */
	public DgInputImpExpRequestBill() {
		super();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();

	}

	public void setVisible(boolean b) {
		if (b == true) {
			InputTableColumnSetUtils.putColumn(
					InputTableColumnSet.IMP_EXP_REQ_BILL_INPUT, this
							.getDefaultTableColumnList());
			initTable(new ArrayList());
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(960, 498));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("进出口申请单数据导入接口");
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
			jPanel.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes btnInput
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnInput() {
		if (btnInput == null) {
			btnInput = new JButton();
			btnInput.setText("1.打开文件");
			btnInput.setPreferredSize(new Dimension(80, 30));
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgInputImpExpRequestBill.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					ImportFileDataRunnable inputDataThread = new ImportFileDataRunnable();
					inputDataThread.execute();

				}
			});
		}
		return btnInput;
	}

	/**
	 * 初始化表
	 * 
	 * @return
	 */

	private List getDefaultTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("单据号", "billHead.billNo", 100));
		list.add(new JTableListColumn("生效日期(yyyy-MM-dd)",
				"billHead.beginAvailability", 140));
		list.add(new JTableListColumn("运输方式", "billHead.transfMode.name", 100));
		list.add(new JTableListColumn("运输工具", "billHead.conveyance", 100));
		list.add(new JTableListColumn("集装箱号", "billHead.containerCode", 100));
		list.add(new JTableListColumn("进出口岸", "billHead.iePort.name", 100));
		list.add(new JTableListColumn("客户供应商", "billHead.scmCoc.name", 100));
		list.add(new JTableListColumn("备注", "billHead.memos", 100));
		list.add(new JTableListColumn("料号", "commInfo.materiel.ptNo", 100));
		list.add(new JTableListColumn("数量", "commInfo.quantity", 80));
		list.add(new JTableListColumn("单价", "commInfo.unitPrice", 80));
		list.add(new JTableListColumn("总金额", "commInfo.amountPrice", 80));
		list.add(new JTableListColumn("加工费总价", "commInfo.workUsd", 80));
		list.add(new JTableListColumn("单毛重", "commInfo.invgrossWeight", 80));
		list.add(new JTableListColumn("毛重", "commInfo.grossWeight", 80));
		list.add( new JTableListColumn("单净重", "commInfo.invnetWeight", 80));
		list.add(new JTableListColumn("净重", "commInfo.netWeight", 80));
		list.add(new JTableListColumn("件数", "commInfo.piece", 80));
		list.add(new JTableListColumn("箱号", "commInfo.boxNo", 100));
		list.add(new JTableListColumn("制单号", "commInfo.makeBillNo", 100));
		int width = 0;
		if (getProjectType() == ProjectType.BCUS) {
			width = 100;
		}
		list.add(new JTableListColumn("企业版本", "commInfo.cmpVersion", width));
		list.add(new JTableListColumn("海关版本", "commInfo.version", 100));
		list.add(new JTableListColumn("产销国", "commInfo.country.name", 100));
		list.add(new JTableListColumn("币制", "commInfo.currency.name", 100));
		list.add(new JTableListColumn("表体扩展备注栏", "commInfo.extendMemo", 100));
		list.add(new JTableListColumn("包装种类(名字)", "commInfo.wrapType.name", 100));
		return list;
	}

	/**
	 * 为了减轻对服务器数据库的压力,尽量减少对数据库的操作.用缓存!
	 */
	private void initConditionList() {
		billPtNoList.clear();
		manufacturerListName.clear();
		manufacturerListCode.clear();
		customerListName.clear();
		customerListCode.clear();
		countryrListName.clear();
		countryrListCode.clear();
		currListName.clear();
		currListCode.clear();
		materielListName.clear();
		List tbillPtNoList = encAction.findImpExpRequestBill1(new Request(
				CommonVars.getCurrUser()));
		// 把所有数据库中已存在的单据号放在billPtNoList中
		for (int k = 0; k < tbillPtNoList.size(); k++) {
			ImpExpRequestBill bill = (ImpExpRequestBill) tbillPtNoList.get(k);
			billPtNoList.add(bill.getBillNo());
		}
		// 进出口岸
		List custmosList = customBaseAction.findCustoms(null, null);
		for (int i = 0; i < custmosList.size(); i++) {
			Customs cust = (Customs) custmosList.get(i);
			customsListName.put(cust.getName(), cust);
			customsListCode.put(cust.getCode(), cust);
		}
		// 供应商
		List tmanufacturerList = customBaseAction.findScmCoc(new Request(
				CommonVars.getCurrUser()), false, true);
		for (int k = 0; k < tmanufacturerList.size(); k++) {
			ScmCoc sc = (ScmCoc) tmanufacturerList.get(k);
			manufacturerListName.put(sc.getName(), sc);
			manufacturerListCode.put(sc.getCode(), sc);
		}
		// 客户
		List tcustomerListName = customBaseAction.findScmCoc(new Request(
				CommonVars.getCurrUser()), true, false);
		for (int k = 0; k < tcustomerListName.size(); k++) {
			ScmCoc sc = (ScmCoc) tcustomerListName.get(k);
			customerListName.put(sc.getName(), sc);
			customerListCode.put(sc.getCode(), sc);
		}
		// 国家
		List tcountryrList = customBaseAction.findCountry(new Request(
				CommonVars.getCurrUser()));
		for (int k = 0; k < tcountryrList.size(); k++) {
			Country sc = (Country) tcountryrList.get(k);
			countryrListName.put(sc.getName(), sc);
			countryrListCode.put(sc.getCode(), sc);
		}
		// 币别
		List tcurrList = customBaseAction.findCurr(new Request(CommonVars
				.getCurrUser()));
		for (int k = 0; k < tcurrList.size(); k++) {
			Curr sc = (Curr) tcurrList.get(k);
			currListName.put(sc.getName(), sc);
			currListCode.put(sc.getCode(), sc);
		}
		// 物料
		List tmaterielList = this.materialManageAction
				.findMateriel(new Request(CommonVars.getCurrUser()));
		for (int k = 0; k < tmaterielList.size(); k++) {
			Materiel sc = (Materiel) tmaterielList.get(k);
			materielListName.put(sc.getPtNo(), sc);
		}
		List transferModeList = this.customBaseAction.findTransferMode();
		for (int k = 0; k < transferModeList.size(); k++) {
			Transf sc = (Transf) transferModeList.get(k);
			transferModeListName.put(sc.getName(), sc);
			transferModeListCode.put(sc.getCode(), sc);
		}

	}

	/**
	 * 解析导入文件
	 * 
	 * @return
	 */

	private List parseTxtFile() {
		List lsResult = new ArrayList();
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		Integer materielType = this.isInport == true ? Integer
				.parseInt(MaterielType.MATERIEL) : Integer
				.parseInt(MaterielType.FINISHED_PRODUCT);
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
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.IMP_EXP_REQ_BILL_INPUT);
		int zcount = lsIndex.size();
		if (cbBig5ConvertToGB.isSelected()) {
			infTojHsTable();
		}
		List lsBillOrder = encAction.findInputInExRequestBillOrder(new Request(
				CommonVars.getCurrUser()));
		if (lsBillOrder.size() == 0 || lsBillOrder.get(0) == null) {
			JOptionPane.showMessageDialog(DgInputImpExpRequestBill.this,
					"请进行导入参数设置！");
			return new ArrayList();
		}
		
		Map<String, Wrap> wrapMap = new HashMap<String, Wrap>();
		List<Wrap> wrapList = customBaseAction.findWrap("", "");
		Wrap wraps = null;
		for (int i = 0; i < wrapList.size(); i++) {
			wraps = wrapList.get(i);
			wrapMap.put(wraps.getName(), wraps);
		}
		
		InputInExRequestBillOrder data = (InputInExRequestBillOrder) lsBillOrder
				.get(0);
		for (int i = 0; i < lines.size(); i++) {
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if (cbBig5ConvertToGB.isSelected()) {
				strs = changStrs(strs);
			}
			TempImpExpRequestBillForInput tempInput = new TempImpExpRequestBillForInput();
			ImpExpRequestBill billHead = new ImpExpRequestBill();
			ImpExpCommodityInfo commInfo = new ImpExpCommodityInfo();
			tempInput.setBillHead(billHead);
			tempInput.setCommInfo(commInfo);
			StringBuffer sb = new StringBuffer("");
			for (int j = 0; j < zcount; j++) {
				billHead.setBillType(this.getBillType());
				billHead.setMaterielProductFlag(materielType);
				if (data.getIsvalid() != null && data.getIsvalid().equals("1"))
					billHead.setIsAvailability(new Boolean(true));
				else
					billHead.setIsAvailability(new Boolean(false));
				billHead.setImputDate(CommonVars.nowToStandDate());// 录入日期
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				System.out.println(j + ":" + columnField + ":" + value);
				if ("billHead.billNo".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "行单据号为空!;");
					} else {
						if (billPtNoList.contains(value)
								&& (data.getIsdjnore() == null
										|| data.getIsdjnore().trim().equals("") || data
										.getIsdjnore().equals("0"))) {// 判断单据号是否已经存在
							sb.append("警告:第" + (i + 1) + "行单据号已在表中存在");
						}
						billHead.setBillNo(value);
					}
				} else if ("billHead.beginAvailability".equals(columnField)) {// 生效日期
					if (value != null && !"".equals(value.trim())) {
						try {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							billHead.setBeginAvailability(dateFormat
									.parse(value.trim()));
						} catch (Exception ex) {
							sb.append("第" + (i + 1) + "行日期格式错误");
						}
					}
				} else if ("billHead.conveyance".equals(columnField)) {// 运输工具
					billHead.setConveyance(value);
				} else if ("billHead.transfMode.name".equals(columnField)) {// 运输方式
					if (value != null) {
						Transf transf = null;
						if (data.getTransferModeCodeName() != null
								&& data.getTransferModeCodeName().equals("代码")) {
							transf = (Transf) transferModeListCode.get(value
									.trim());
						} else {
							transf = (Transf) transferModeListName.get(value
									.trim());
						}
						if (transf == null) {
							sb.append("警告:第" + (i + 1) + "行所输入运输方式,在系统中未能找到!");
						}
						billHead.setTransfMode(transf);
					}
				} else if ("billHead.containerCode".equals(columnField)) {// 集装箱号
					billHead.setContainerCode(value);
				} else if ("billHead.iePort.name".equals(columnField)) {// 进出口岸(代码|名称)
					if (value != null) {
						Customs customs = null;
						if (data.getIeportCodeName() != null
								&& data.getIeportCodeName().equals("代码")) {
							customs = (Customs) customsListCode.get(value
									.trim());
						} else {
							customs = (Customs) customsListName.get(value
									.trim());
						}
						if (customs == null) {
							sb.append("警告:第" + (i + 1) + "行所输入进出口岸,在系统中未能找到!");
						}
						billHead.setIePort(customs);
					}
				} else if ("billHead.scmCoc.name".equals(columnField)) {// 客户供应商（代码|名称）
					if (value != null) {
						ScmCoc scmCoc = null;
						if (this.isInport) {
							if (data.getScmCocCodeName() != null
									&& data.getScmCocCodeName().equals("代码")) {
								scmCoc = (ScmCoc) manufacturerListCode
										.get(value.trim());
							} else {
								scmCoc = (ScmCoc) manufacturerListName
										.get(value.trim());
							}
						} else {
							if (data.getScmCocCodeName() != null
									&& data.getScmCocCodeName().equals("代码")) {
								scmCoc = (ScmCoc) customerListCode.get(value
										.trim());
							} else {
								scmCoc = (ScmCoc) customerListName.get(value
										.trim());
							}
						}
						if (scmCoc != null) {
							billHead.setScmCoc(scmCoc);
						} else {
							sb.append("第" + (i + 1) + "行客户供应商:" + value
									+ "不存在;");
						}
					} else {
						sb.append("第" + (i + 1) + "行客户供应商为空!;");
					}
				} else if ("billHead.memos".equals(columnField)) {// 备注
					billHead.setMemos(value);
				} else if ("commInfo.materiel.ptNo".equals(columnField)) {// 物料编号
					if (value == null || "".equals(value.trim())) {
						sb.append("警告:第" + (i + 1) + "行料号为空!");
					} else {
						Materiel materiel = (Materiel) materielListName
								.get(value.trim());
						if (materiel == null) {
							sb
									.append("警告:第" + (i + 1) + "行料号" + value
											+ "不存在!");
						} else {
							commInfo.setMateriel(materiel);
							if (projectType == ProjectType.BCUS) {
								// 检查内部归并是否禁用
								List list = materialManageAction
										.findInnerMergeDataByptNoIsForbid(
												new Request(CommonVars
														.getCurrUser()), value
														.trim());
								if (list != null && list.size() > 0) {
									sb.append("警告:第" + (i + 1) + "行料号已经被禁用");
								}
							}
						}
					}
				} else if ("commInfo.unitPrice".equals(columnField)) {// 单价
					if (value != null && !"".equals(value.trim())) {

						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   单价不合法/");
							continue;
						}
						commInfo.setUnitPrice(Double.valueOf(value));
					}
				} else if ("commInfo.grossWeight".equals(columnField)) {// 毛重
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   毛重不合法/");
							continue;
						}
						commInfo.setGrossWeight(Double.valueOf(value));
					} else {
						sb.append("[" + value + "]   毛重不为0/");
						continue;
					}

				} else if ("commInfo.netWeight".equals(columnField)) {// 净重
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   净重不合法/");
							continue;
						}
						commInfo.setNetWeight(Double.valueOf(value));
					} else {
						commInfo.setNetWeight(0.0);
					}
				} else if ("commInfo.makeBillNo".equals(columnField)) {// 制单号
					commInfo.setMakeBillNo(value);

				} else if ("commInfo.cmpVersion".equals(columnField)) {// 企业版本号
					commInfo.setCmpVersion(value == null ? "" : value.trim());
					// 2:退厂返工 4:成品出口 5:转厂出口 10：一般贸易出口 7：返工复出
					if (billHead.getBillType() == 4
							|| billHead.getBillType() == 2
							|| billHead.getBillType() == 5
							|| billHead.getBillType() == 10
							|| billHead.getBillType() == 7) {
						if (value != null
								&& !"".equals(value)
								&& (commInfo.getVersion() == null || ""
										.equals(commInfo.getVersion()))) {
							if (commInfo.getMateriel() != null) {
								Integer cmpVer = materialManageAction
										.getVersion(new Request(CommonVars
												.getCurrUser()), commInfo
												.getMateriel().getPtNo(),
												commInfo.getCmpVersion());
								if (cmpVer == -1) {
									sb.append("料件所对应的企业版本在归并关系中未找到对应的海关版本/");
									continue;
								}
								commInfo.setVersion(String.valueOf(cmpVer));
							} else {
								sb.append("料件不存在/");
							}
						} else {
							if (commInfo.getVersion() == null
									|| "".equals(commInfo.getVersion())) {
								sb
										.append("进出口类型为["
												+ ImpExpType
														.getImpExpTypeDesc(billHead
																.getBillType())
												+ "] 海关版本号不能为空");
							}
						}
					} else {
						if (value != null && !"".equals(value)) {
							sb.append("进出口类型为["
									+ ImpExpType.getImpExpTypeDesc(billHead
											.getBillType()) + "] 企业版本号应该为空");
						}
					}
				} else if ("commInfo.version".equals(columnField)) {// 海关版本号

					commInfo.setVersion(value);
					// 2:退厂返工 4:成品出口 5:转厂出口 10：一般贸易出口 7：返工复出
					if (billHead.getBillType() == 4
							|| billHead.getBillType() == 2
							|| billHead.getBillType() == 5
							|| billHead.getBillType() == 10
							|| billHead.getBillType() == 7) {
						try {
							Integer.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   版本号不合法/");
							continue;
						}
					} else {
						if (value != null && !"".equals(value)) {
							sb.append("进出口类型为["
									+ ImpExpType.getImpExpTypeDesc(billHead
											.getBillType()) + "] 海关版本号应该为空");
						}
						continue;
					}

				} else if ("commInfo.amountPrice".equals(columnField)) {// 总金额
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    总金额不合法/");
							continue;
						}
						commInfo.setAmountPrice(Double.valueOf(value));
					}
				} else if ("commInfo.workUsd".equals(columnField)) {// 加工费总价
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    加工费总价不合法/");
							continue;
						}
						commInfo.setWorkUsd(Double.valueOf(value));
					}
				} else if ("commInfo.invnetWeight".equals(columnField)) {// 单净重
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    单净重不合法/");
							continue;
						}
						commInfo.setInvnetWeight(Double.valueOf(value));
					}
				} else if ("commInfo.invgrossWeight".equals(columnField)) {// 单毛重
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    单毛重不合法/");
							continue;
						}
						commInfo.setInvgrossWeight(Double.valueOf(value));
					}
				} else if ("commInfo.quantity".equals(columnField)) {// 数量
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    数量不合法/");
							continue;
						}
						commInfo.setQuantity(Double.valueOf(value));
					}
				} else if ("commInfo.country.name".equals(columnField)) {// 产销国（代码|名称）
					if (value != null) {
						Country country = null;
						if (data.getCountryCodeName() != null
								&& data.getCountryCodeName().equals("代码")) {
							country = (Country) countryrListCode.get(value
									.trim());
						} else {
							country = (Country) countryrListName.get(value
									.trim());
						}
						if (country == null) {
							sb.append("警告:第" + (i + 1) + "行所所输入产销国,在系统中未能找到!");
						}
						commInfo.setCountry(country);
					}
				} else if ("commInfo.currency.name".equals(columnField)) {// 币制（代码|名称）
					if (value != null) {
						Curr currency = null;
						if (data.getCurencyCodeName() != null
								&& data.getCurencyCodeName().equals("代码")) {
							currency = (Curr) currListCode.get(value.trim());
						} else {
							currency = (Curr) currListName.get(value.trim());
						}
						if (currency == null) {
							sb.append("警告:第" + i + "行所所输入币制,在系统中未能找到!");
						}
						commInfo.setCurrency(currency);
					}
				} else if ("commInfo.piece".equals(columnField)) {// 件数
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    件数不合法/");
							continue;
						}
						commInfo.setPiece(Double.valueOf(value).intValue());
					}
				} else if ("commInfo.boxNo".equals(columnField)) {// 箱号
					if (value != null && !"".equals(value.trim())) {
						commInfo.setBoxNo(value.trim());
					}
				} else if ("commInfo.extendMemo".equals(columnField)) {// 表体扩展备注栏
					commInfo.setExtendMemo(value);
				} else if ("commInfo.indEventweight".equals(columnField)) {// 单箱量
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    单箱量不合法/");
							continue;
						}
						commInfo.setIndEventweight(Double.valueOf(value));
					}
				} else if ("commInfo.customerOtherName".equals(columnField)) {// 客户别
					commInfo.setCustomerOtherName(value);
				} else if ("commInfo.wrapType.name".equals(columnField)) {// 包装方式
					if(CommonUtils.notEmpty(value)) {
						Wrap wrap = wrapMap.get(value);
						if(wrap == null) {
							sb.append("[" + value + "] 包装种类不存在！");
						} else {
							commInfo.setWrapType(wrap);
						}
					}
				}
				tempInput.setErrinfo(sb.toString());
			}

			// 单价，单净重，单毛重自动从物料中带出并自动计算总价
			System.out.println("commInfo1" +commInfo.getAmountPrice() );
			System.out.println("other.getIsSumMoney()" + other.getIsSumMoney());
			if(other.getIsSumMoney() == null || other.getIsSumMoney() == true) {
				if (commInfo.getMateriel() != null
						&& materielListName.get(commInfo.getMateriel().getPtNo()) != null) {
					Materiel materiel = (Materiel) materielListName.get(commInfo
							.getMateriel().getPtNo());
					if (commInfo.getUnitPrice() == null
							|| "".equals(commInfo.getUnitPrice())) {
						commInfo.setUnitPrice(materiel.getPtPrice());
					}
					if (commInfo.getInvnetWeight() == null
							|| "".equals(commInfo.getInvnetWeight())) {
						commInfo.setInvnetWeight(materiel.getPtNetWeight());
					}
					if (commInfo.getInvgrossWeight() == null
							|| "".equals(commInfo.getInvgrossWeight())) {
						commInfo.setInvgrossWeight(materiel.getPtOutWeight());
					}
					if (commInfo.getQuantity() != null
							&& commInfo.getUnitPrice() != null) {
						commInfo.setAmountPrice(CaleUtil.multiply(commInfo.getQuantity(), commInfo.getUnitPrice(),3));
						System.out.println(CaleUtil.multiply(commInfo.getQuantity(), commInfo.getUnitPrice(),3));
					} else {
						commInfo.setAmountPrice(0.0);
						}
				}
			}
			System.out.println("commInfo2" +commInfo.getAmountPrice() );
			System.out.println(CaleUtil.multiply(commInfo.getQuantity(), commInfo.getUnitPrice(),3));
			// 如果毛重、净重为空自动带出
			if (commInfo.getGrossWeight() == null
					|| "".equals(commInfo.getGrossWeight())) {
				if (commInfo.getQuantity() != null
						&& commInfo.getInvgrossWeight() != null)
					commInfo.setGrossWeight(commInfo.getQuantity()
							* commInfo.getInvgrossWeight());
				else
					commInfo.setGrossWeight(0.0);
			}
			if (commInfo.getNetWeight() == null
					|| "".equals(commInfo.getNetWeight())) {
				if (commInfo.getQuantity() != null
						&& commInfo.getInvnetWeight() != null)
					commInfo.setNetWeight(commInfo.getQuantity()
							* commInfo.getInvnetWeight());
				else
					commInfo.setNetWeight(0.0);
			}
			
			if ((commInfo.getNetWeight() == null ? 0 : commInfo.getNetWeight()) > (commInfo
					.getGrossWeight() == null ? 0 : commInfo.getGrossWeight())) {
				tempInput.setErrinfo((tempInput.getErrinfo() == null ? ""
						: tempInput.getErrinfo())
						+ "[" + commInfo.getGrossWeight() + "]   毛重不能小于净重/");
			}
			lsResult.add(tempInput);
		}
		return lsResult;
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退  出");
			btnExit.setPreferredSize(new Dimension(75, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * 获取所有列
	 * 
	 * @return
	 */

	private List getColumnList() {
		if (columnList == null) {
			columnList = new ArrayList();
			columnList.add(null);
			for (int i = 0; i < 24; i++) {
				columnList.add(new Integer(i));
			}
		}
		return columnList;
	}

	/**
	 * 导入文件列号
	 * 
	 * @author Administrator
	 * 
	 */

	class InputFileColumnNumCbb extends JComboBox {
		public InputFileColumnNumCbb() {
			super();
			this.setRenderer(new CbbColumnListRender());
			this.setListData(getColumnList());
		}

		public InputFileColumnNumCbb(int index) {
			super();
			this.setRenderer(new CbbColumnListRender());
			this.setListData(getColumnList());
			this.setSelectedIndex(index);
		}

		public void setListData(List list) {
			for (int i = 0; i < list.size(); i++) {
				this.addItem(list.get(i));
			}
		}
	}

	/**
	 * List呈现类
	 * 
	 * @author Administrator
	 * 
	 */

	class CbbColumnListRender extends DefaultListCellRenderer {
		public CbbColumnListRender() {
			super();
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setComponentOrientation(list.getComponentOrientation());
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			if (value instanceof Icon) {
				setIcon((Icon) value);
				setText("");
			} else {
				setIcon(null);
				Integer ing = null;
				if (value != null) {
					ing = (Integer) value;
					setText("第" + (new Integer(ing.intValue() + 1)).toString()
							+ "列");
				} else {
					setText("无对应");
				}
			}

			setEnabled(list.isEnabled());
			setFont(list.getFont());

			Border border = null;
			if (cellHasFocus) {
				if (isSelected) {
					border = UIManager
							.getBorder("List.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager
							.getBorder("List.focusCellHighlightBorder");
				}
			} else {
				border = noFocusBorder;
			}
			setBorder(border);

			return this;
		}
	}

	/**
	 * 文本文件过滤
	 * 
	 * @author Administrator
	 * 
	 */

	class TextFileFilter extends FileFilter {
		public boolean accept(File f) {
			if (f.getName().endsWith(".txt")) {
				return true;
			} else
				return false;
		}

		public String getDescription() {
			return "*.txt";
		}
	}

	public boolean isInport() {
		return isInport;
	}

	public void setInport(boolean isInport) {
		this.isInport = isInport;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	/**
	 * 文本导入多线程
	 * 
	 * @author Administrator
	 * 
	 */

	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgInputImpExpRequestBill.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				initConditionList();// 初始化查询条件
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				list = parseTxtFile();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgInputImpExpRequestBill.this,
						"导入数据失败：！" + e.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return list;
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			List list = new ArrayList();
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initTable(list);
		}
	}

	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	/**
	 * This method initializes cbAmounTaccumulate
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAmounTaccumulate() {
		if (cbAmounTaccumulate == null) {
			cbAmounTaccumulate = new JCheckBox();
			cbAmounTaccumulate.setText("料号,产销国相同商品的数量累加");
		}
		return cbAmounTaccumulate;
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
			jToolBar.setPreferredSize(new Dimension(20, 35));
			jToolBar.setFloatable(false);
			jToolBar.setLayout(f);
			jToolBar.add(getBtnParameterSet());
			jToolBar.add(getBtnInput());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnFieldSet());
			jToolBar.add(getBtnExit());
			jToolBar.add(getCbCheckTitle());
			jToolBar.add(getCbBig5ConvertToGB());
			jToolBar.add(getCbbCaleAmountPrice());
			jToolBar.add(getCbAmounTaccumulate());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnSaveData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveData() {
		if (btnSaveData == null) {
			btnSaveData = new JButton();
			btnSaveData.setText("2.保存数据");
			btnSaveData.setPreferredSize(new Dimension(80, 30));
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String uploaderr = "D:/bestwaydata/uploaderr";// 导入失败路径
					String uploaddatabak = "D:/bestwaydata/uploaddatabak";// 导入成功路径
					List list = tableModel.getList();
					for (int i = 0; i < list.size(); i++) {
						TempImpExpRequestBillForInput obj = (TempImpExpRequestBillForInput) list
								.get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgInputImpExpRequestBill.this,
									"有错误信息，不可保存！", "提示!", 2);
							// -----------------------------------------
							File uploaderrdir = new File(uploaderr);
							File uploaderrdirfile = new File(uploaderr
									+ File.separator + txtFile.getName());
							if (uploaderrdir.exists()) {
								moveFile(txtFile, uploaderrdirfile);
							}
							// -----------------------------------------
							return;
						}
					}
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(
								DgInputImpExpRequestBill.this, "无数据可保存", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List lsResult = encAction
							.saveImpExpRequestBillAndImpExpCommodityInfo(
									new Request(CommonVars.getCurrUser()),
									list, cbAmounTaccumulate.isSelected(),other.getIsSumMoney());
					tableModelHead.addRows(lsResult);

					JOptionPane.showMessageDialog(
							DgInputImpExpRequestBill.this,
							"保存数据成功！保存成功的申请单个数是:" + lsResult.size(), "提示!",
							JOptionPane.INFORMATION_MESSAGE);
					// -------------------------------------------------
					File uploaddatabakdir = new File(uploaddatabak);
					File uploaddatabakdirfile = new File(uploaddatabak
							+ File.separator + txtFile.getName());
					if (uploaddatabakdir.exists()) {
						moveFile(txtFile, uploaddatabakdirfile);
					}
					// -------------------------------------------------
				}
			});
		}
		return btnSaveData;
	}

	/**
	 * 移动文件
	 * 
	 * @param fSrc
	 * @param fDest
	 */
	private void moveFile(File fSrc, File fDest) {
		try {
			if (fSrc.exists()) {
				// FileUtils.copyFile(fSrc, fDest);
				copyFile(fSrc, fDest);
				fSrc.delete();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	private void copyFile(File oldPath, File newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = oldPath;
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}
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
	 * @param s
	 * @return
	 */

	private String changeStr(String s) {
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

	/**
	 * 繁转简
	 * 
	 * @param source
	 * @return
	 */

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = changeStr(source[i]);
		}
		return newStrs;
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
		// System.out.println("--"+dataIndex+"="+fileRow[dataIndex]);
		return fileRow[dataIndex];
	}

	/**
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("第一行是标题");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
	}

	/**
	 * This method initializes cbBig5ConvertToGB
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbBig5ConvertToGB() {
		if (cbBig5ConvertToGB == null) {
			cbBig5ConvertToGB = new JCheckBox();
			cbBig5ConvertToGB.setText("繁转简");
		}
		return cbBig5ConvertToGB;
	}

	/**
	 * This method initializes btnFieldSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnFieldSet() {
		if (btnFieldSet == null) {
			btnFieldSet = new JButton();
			btnFieldSet.setText("栏位设定");
			btnFieldSet.setPreferredSize(new Dimension(80, 30));
			btnFieldSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.IMP_EXP_REQ_BILL_INPUT);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}
				}
			});
		}
		return btnFieldSet;
	}

	/**
	 * 初始化表格
	 * 
	 * @param list
	 */

	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.IMP_EXP_REQ_BILL_INPUT);
					}
				});
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
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
			btnParameterSet.setPreferredSize(new Dimension(80, 30));
			btnParameterSet
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							DgInputInExRequestBillSet dg = new DgInputInExRequestBillSet();
							dg.setVisible(true);
						}
					});
		}
		return btnParameterSet;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setDividerSize(0);
			jSplitPane.setDividerLocation(70);
			jSplitPane.setBottomComponent(getJScrollPane());
			jSplitPane.setTopComponent(getJPanel1());
		}
		return jSplitPane;
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
			jPanel1.add(getJPanel2(), BorderLayout.CENTER);
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
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(60, 7, 651, 18));
			jLabel
					.setText("说明：如果单价,单净重,单毛重格式中没设置或已设置格式但导入值为空时，都会自动从物料带出并反算总净重,总毛重,总价");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new Rectangle(5, 5, 10, 10));
			jPanel2.add(jLabel, null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes btnDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setPreferredSize(new Dimension(80, 30));
			btnDel.setText("删除错误");
			btnDel.addActionListener(new java.awt.event.ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					List ls = null;
					ls = tableModel.getList();
					List delList = new ArrayList();
					if (ls != null && ls.size() > 0) {
						for (int i = 0; i < ls.size(); i++) {
							TempImpExpRequestBillForInput obj = (TempImpExpRequestBillForInput) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								delList.add(obj);
							}
						}
						//删除错误数据
						tableModel.deleteRows(delList);
					}
				}
			});
		}
		return btnDel;
	}

	/**
	 * This method initializes cbbCaleAmountPrice	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCbbCaleAmountPrice() {
		if (cbbCaleAmountPrice == null) {
			cbbCaleAmountPrice = new JCheckBox();
			cbbCaleAmountPrice.setText("计算总价");
			cbbCaleAmountPrice.setVisible(false);
		}
		return cbbCaleAmountPrice;
	}

} // @jve:decl-index=0:visual-constraint="10,10"