package com.bestway.bls.client.storagebill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

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
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.bls.action.BlsInOutStockBillAction;
import com.bestway.bls.entity.BlsIOStockBillIOF;
import com.bestway.bls.entity.BlsInOutStockBill;
import com.bestway.bls.entity.BlsInOutStockBillDetail;
import com.bestway.bls.entity.TempInOutStockBills;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 贺巍于2009年12月添加注释
 * 
 * 进出仓单据倒入主窗体
 * 
 * @author hw
 * 
 */
public class DgBlsInOutDetailImport extends JDialogBase {

	private JPanel jPanel = null;

	private JButton btnInput = null;

	private JButton btnExit = null;
	/** 所有列 */
	private List columnList = null; // @jve:decl-index=0:
	/** 退出 */
	private boolean isInport = false;
	/** 单据类型 */
	private int billType = -1;

	private Hashtable headHash = null;

	private CustomBaseAction customBaseAction = null;

	private EncAction encAction = null;
	/** 料件管理操作接口 */
	private MaterialManageAction materialManageAction = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelHead = null;

	/*** 繁转简 */
	private Hashtable gbHash = null; // @jve:decl-index=0:

	private List billPtNoList = new ArrayList(); // @jve:decl-index=0:
	/** 导入文件 */
	private File txtFile = null;
	/**
	 * 存放供应商名字map
	 */
	private HashMap manufacturerListName = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放供应商代码map
	 */
	private HashMap manufacturerListCode = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放仓库编码名的map
	 */
	private HashMap waresetListName = new HashMap();
	/**
	 * 存放仓库名称的map
	 */
	private HashMap waresetListCode = new HashMap();
	/**
	 * 存放客户名字map
	 */
	private HashMap customerListName = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放客户代码map
	 */
	private HashMap customerListCode = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放国家代码map
	 */
	private HashMap countryrListCode = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放国家名称map
	 */
	private HashMap countryrListName = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放币制代码map
	 */
	private HashMap currListCode = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放币制名称map
	 */
	private HashMap currListName = new HashMap(); // @jve:decl-index=0:

	// private HashMap materielListCode = new HashMap();
	/**
	 * 存放物料名称map
	 */
	private HashMap materielListName = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放进出口岸代码map
	 */
	private HashMap customsListCode = new HashMap(); // @jve:decl-index=0:
	/**
	 * 存放进出口岸名称map
	 */
	private HashMap customsListName = new HashMap(); // @jve:decl-index=0:
	private JToolBar jToolBar = null;

	private JButton btnSaveData = null;

	private JScrollPane jScrollPane = null;

	private JTable tbBlsInOutDetailImport = null;

	private JCheckBox cbCheckTitle = null;

	private JCheckBox cbBig5ConvertToGB = null;

	private JButton btnFieldSet = null;

	private JCheckBox cbAmounTaccumulate = null;

	private String inOutFlag = null;
	public BlsInOutStockBillAction blsInOutStockBillAction = (BlsInOutStockBillAction) CommonVars
			.getApplicationContext().getBean("blsInOutStockBillAction"); // @jve:decl-index=0:

	/**
	 * 构造函数 This method initializes
	 * 
	 */
	public DgBlsInOutDetailImport(String inOutFlag) {
		super();
		this.inOutFlag = inOutFlag;
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		initialize();
		if (inOutFlag.equals(BlsIOStockBillIOF.IMPORT)) {
			InputTableColumnSetUtils.putColumn(
					InputTableColumnSet.BLS_INOUTSTOCKBILL_INPUT, this
							.getDefaultTableColumnList());
		} else {
			InputTableColumnSetUtils.putColumn(
					InputTableColumnSet.BLS_INOUTSTOCKBILL_INPUT, this
							.getDefaultTableColumnListOut());
		}

		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(680, 498));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("进出仓单据导入接口");
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
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
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headHash = new Hashtable();
					txtFile = getFile();
					if (txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().start();

				}
			});
		}
		return btnInput;
	}

	/**
	 * 倒入文件线程
	 * 
	 * @author hw
	 * 
	 */
	class ImportFileDataRunnable extends Thread {

		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgBlsInOutDetailImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				initConditionList();
				if (inOutFlag.equals(BlsIOStockBillIOF.IMPORT)) {
					list = parseTxtFileIn();
				} else {
					list = parseTxtFileOut();
				}
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

	/**
	 * 调出文件选择框的内部类
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

		/**
		 * 得到描述
		 */
		public String getDescription() {
			return "*." + this.suffix;
		}

		/**
		 * 大小写转换
		 * 
		 * @param f
		 * @return
		 */
		private String getSuffix(File f) {
			String s = f.getPath(), suffix = null;
			int i = s.lastIndexOf('.');
			if (i > 0 && i < s.length() - 1)
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
	}

	/**
	 * 初始化表
	 * 
	 * @return
	 */

	private List getDefaultTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进出仓标志", "bsb.ioFlag", 100));
		list.add(new JTableListColumn("单据编号", "bsb.billNo", 100));
		list.add(new JTableListColumn("单据生效日期", "bsb.validDate", 100));
		list.add(new JTableListColumn("供货方企业", "bsb.corrOwner.name", 180));
		list.add(new JTableListColumn("仓库编码", "bsb.wareHouseCode.code", 100));

		list.add(new JTableListColumn("商品流水号", "bsd.seqNo", 100));
		list.add(new JTableListColumn("原产国", "bsd.originCountry.name", 100));
		list.add(new JTableListColumn("企业内部货号", "bsd.partNo.ptNo", 100));
		list.add(new JTableListColumn("数量", "bsd.detailQty", 80));
		list.add(new JTableListColumn("申报单价", "bsd.unitPrice", 80));
		list.add(new JTableListColumn("币值", "bsd.curr.name", 80));
		list.add(new JTableListColumn("毛重", "bsd.grossWeight", 80));
		list.add(new JTableListColumn("净重", "bsd.netWeight", 80));
		list.add(new JTableListColumn("件数", "bsd.packCount", 80));
		list.add(new JTableListColumn("归并序号", "bsd.seqNum", 80));
		list.add(new JTableListColumn("报关单18位编号", "bsd.entryID", 120));
		list.add(new JTableListColumn("报关单商品序号", "bsd.entryGNo", 120));
		// list.add(new JTableListColumn("工厂单位(名称)",
		// "commInfo.materiel.calUnit.name", 100));

		return list;
	}

	/**
	 * 初始化倒入出仓单据表
	 * 
	 * @return
	 */

	private List getDefaultTableColumnListOut() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进出仓标志", "bsb.ioFlag", 100));
		list.add(new JTableListColumn("单据编号", "bsb.billNo", 100));
		list.add(new JTableListColumn("单据生效日期", "bsb.validDate", 100));
		list.add(new JTableListColumn("供货方企业", "bsb.corrOwner.name", 180));
		list.add(new JTableListColumn("仓库编码", "bsb.wareHouseCode.code", 100));

		list.add(new JTableListColumn("商品流水号", "bsd.seqNo", 100));
		list.add(new JTableListColumn("原产国", "bsd.originCountry.name", 100));
		list.add(new JTableListColumn("企业内部货号", "bsd.partNo.ptNo", 100));
		list.add(new JTableListColumn("数量", "bsd.detailQty", 80));
		list.add(new JTableListColumn("申报单价", "bsd.unitPrice", 80));
		list.add(new JTableListColumn("币值", "bsd.curr.name", 80));
		list.add(new JTableListColumn("毛重", "bsd.grossWeight", 80));
		list.add(new JTableListColumn("净重", "bsd.netWeight", 80));
		list.add(new JTableListColumn("件数", "bsd.packCount", 80));
		list.add(new JTableListColumn("归并序号", "bsd.seqNum", 80));
		list.add(new JTableListColumn("入仓单据单据号", "bsd.inBillNo", 120));
		list.add(new JTableListColumn("入仓单据商品序号", "bsd.inBillGoodNo", 120));
		list.add(new JTableListColumn("报关单18位编号", "bsd.entryID", 120));
		list.add(new JTableListColumn("报关单商品序号", "bsd.entryGNo", 120));
		// list.add(new JTableListColumn("工厂单位(名称)",
		// "commInfo.materiel.calUnit.name", 100));

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
		billPtNoList = blsInOutStockBillAction
				.findBlsInOutBillBillNo(new Request(CommonVars.getCurrUser()));
		// 把所有数据库中已存在的单据号放在billPtNoList中
		// for (int k = 0; k < tbillPtNoList.size(); k++) {
		// ImpExpRequestBill bill = (ImpExpRequestBill) tbillPtNoList.get(k);
		// billPtNoList.add(bill.getBillNo());
		// }
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
		System.out.println("tmanufacturerList.size()="
				+ tmanufacturerList.size());
		for (int k = 0; k < tmanufacturerList.size(); k++) {
			ScmCoc sc = (ScmCoc) tmanufacturerList.get(k);
			manufacturerListName.put(sc.getName(), sc);
			manufacturerListCode.put(sc.getCode(), sc);
		}
		List twaresetList = customBaseAction.findWareSet(new Request(CommonVars
				.getCurrUser()));
		for (int k = 0; k < twaresetList.size(); k++) {
			WareSet ws = (WareSet) twaresetList.get(k);
			waresetListName.put(ws.getName(), ws);
			waresetListCode.put(ws.getCode(), ws);
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
	}

	/**
	 * 解析导入文件
	 * 
	 * @return
	 */

	private List parseTxtFileIn() {
		List lsResult = new ArrayList();
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
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.BLS_INOUTSTOCKBILL_INPUT);
		int zcount = lsIndex.size();
		if (cbBig5ConvertToGB.isSelected()) {
			infTojHsTable();
		}
		List lsBillOrder = blsInOutStockBillAction
				.findBlsInOutBillBillNo(new Request(CommonVars.getCurrUser()));
		// if (lsBillOrder.size() == 0 || lsBillOrder.get(0) == null) {
		// JOptionPane.showMessageDialog(DgBlsInOutDetailImport.this,
		// "请进行导入参数设置！");
		// return new ArrayList();
		// }
		// InputInOutStockBillOrder data = (InputInOutStockBillOrder)
		// lsBillOrder
		// .get(0);
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
			TempInOutStockBills tempInput = new TempInOutStockBills();
			BlsInOutStockBill bsb = new BlsInOutStockBill();
			BlsInOutStockBillDetail bsd = new BlsInOutStockBillDetail();

			/*
			 * list.add(new JTableListColumn("进出仓标志", "ioFlag", 100));
			 * list.add(new JTableListColumn("单据编号", "billNo", 100));
			 * list.add(new JTableListColumn("单据生效日期", "validDate", 100));
			 * list.add(new JTableListColumn("供货方企业", "corrOwner.name", 180));
			 * list.add(new JTableListColumn("仓库编码", "wareHouseCode.code",
			 * 100));
			 * 
			 * 
			 * list.add(new JTableListColumn("原产国", "originCountry.name", 100));
			 * list.add(new JTableListColumn("企业内部货号", "partNo.ptNo", 100));
			 * list.add(new JTableListColumn("数量", "detailQty", 80));
			 * list.add(new JTableListColumn("申报单价", "unitPrice", 80));
			 * list.add(new JTableListColumn("币值", "curr.name", 80));
			 * list.add(new JTableListColumn("毛重", "grossWeight", 80));
			 * list.add(new JTableListColumn("净重", "netWeight", 80));
			 * list.add(new JTableListColumn("件数", "packCount", 80));
			 * list.add(new JTableListColumn("归并序号", "seqNum", 80));
			 */

			StringBuffer sb = new StringBuffer("");
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				String err = "";
				if ("bsb.ioFlag".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "出入仓标志为空!;");
					} else {
						if (value != null && !value.equals("")) {
							bsb.setIoFlag(value);// 出入仓标志
						} else {
							err = err + "[" + value + "]   出入仓标志不可为空/";
						}
					}
				}

				if ("bsb.billNo".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "行单据号为空!;");
					} else {
						if (billPtNoList.contains(value)) {// 判断单据号是否已经存在
							sb.append("警告:第" + (i + 1) + "行单据号已在表中存在");
						}
						if (value != null && !value.equals("")) {
							bsb.setBillNo(value);// 编码
						} else {
							err = err + "[" + value + "]   编码不可为空/";
						}
					}
				}

				// 生效日期
				if ("bsb.validDate".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							bsb.setValidDate(dateFormat.parse(value.trim()));
						} catch (Exception ex) {
							sb.append("第" + (i + 1) + "行日期格式错误");
						}
					} else {
						err = err + "[" + value + "]   生效日期不可为空/";
					}
				}
				// 客户供应商（代码|名称）
				if ("bsb.corrOwner.name".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						ScmCoc scmCoc = null;
						// scmCoc = (ScmCoc) manufacturerListName
						// .get(value.trim());
						scmCoc = (ScmCoc) manufacturerListName
								.get(value.trim());
						if (scmCoc != null) {
							bsb.setCorrOwner(scmCoc);
						} else {
							sb.append("第" + (i + 1) + "行客户供应商:" + value
									+ "不存在;");
						}
					} else {
						sb.append("第" + (i + 1) + "行客户供应商为空!;");
					}
				}
				// billHead.setBillType(this.getBillType());

				// 仓库编码（代码|名称）
				if ("bsb.wareHouseCode.code".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						ScmCoc scmCoc = null;
						// scmCoc = (ScmCoc) manufacturerListName
						// .get(value.trim());
						WareSet wareset = (WareSet) waresetListCode.get(value
								.trim());
						if (wareset != null) {
							bsb.setWareHouseCode(wareset);
						} else {
							sb.append("第" + (i + 1) + "仓库编码:" + value + "不存在;");
						}
					} else {
						sb.append("第" + (i + 1) + "仓库编码为空!;");
					}
				}

				bsb.setIsEffective(new Boolean(false));

				if ("bsd.seqNo".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "商品流水号为空!;");
					} else {
						if (value != null && !value.equals("")) {
							bsd.setSeqNo(Integer.valueOf(value));// 商品流水号
						} else {
							err = err + "[" + value + "]   商品流水号不可为空/";
						}
					}
				}

				// 原产国（代码|名称）
				if ("bsd.originCountry.name".equals(columnField)) {
					if (value != null) {
						Country country = null;
						country = (Country) countryrListName.get(value.trim());
						if (country == null) {
							sb.append("警告:第" + (i + 1) + "行所所输入产销国,在系统中未能找到!");
						}
						bsd.setOriginCountry(country);
					}
				}

				// 物料编号
				if ("bsd.partNo.ptNo".equals(columnField)) {
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
							bsd.setPartNo(materiel);
						}
					}
				}

				// 数量
				if ("bsd.detailQty".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   数量不合法/");
							continue;
						}
						bsd.setDetailQty(Double.valueOf(value));
					}
					continue;
				}

				// 单价
				if ("bsd.unitPrice".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   单价不合法/");
							continue;
						}
						bsd.setUnitPrice(Double.valueOf(value));
					}
					continue;
				}

				// 币制（代码|名称）
				if ("bsd.curr.name".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						Curr currency = null;
						currency = (Curr) currListName.get(value.trim());
						if (currency == null) {
							sb.append("警告:第" + i + "行所所输入币制,在系统中未能找到!");
						}
						bsd.setCurr(currency);
					}
				}

				// 毛重
				if ("bsd.grossWeight".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   毛重不合法/");
							continue;
						}
						bsd.setGrossWeight(Double.valueOf(value));
					}
					continue;
				}
				// 净重
				if ("bsd.netWeight".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   净重不合法/");
							continue;
						}
						bsd.setNetWeight(Double.valueOf(value));
					}
					continue;
				}

				// 件数
				if ("bsd.packCount".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Integer.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    件数不合法/");
							continue;
						}
						bsd.setPackCount(Double.valueOf(value).intValue());
					}
					continue;
				}

				// 归并序号
				if ("bsd.seqNum".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Integer.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    归并序号不合法/");
							continue;
						}
						bsd.setSeqNum(Integer.valueOf(value));
					}
					continue;
				}

				// 报关单商品序号
				if ("bsd.entryGNo".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						// try {
						// Integer.valueOf(value);
						// } catch (Exception e) {
						// sb.append("[" + value + "]    归并序号不合法/");
						// continue;
						// }
						bsd.setEntryGNo(value);
					} else {
						bsd.setEntryGNo("");
					}
					continue;
				}

				// 报关单18位编号
				if ("bsd.entryID".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						// try {
						// Integer.valueOf(value);
						// } catch (Exception e) {
						// sb.append("[" + value + "]    归并序号不合法/");
						// continue;
						// }
						bsd.setEntryID(value);
					} else {
						bsd.setEntryID("");
					}
					continue;
				}
				tempInput.setBsb(bsb);
				tempInput.setBsd(bsd);
				tempInput.setErrinfo(sb.toString());
			}
			lsResult.add(tempInput);
		}
		return lsResult;
	}

	/**
	 * 解析导入文件
	 * 
	 * @return
	 */

	private List parseTxtFileOut() {
		List lsResult = new ArrayList();
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
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.BLS_INOUTSTOCKBILL_INPUT);
		int zcount = lsIndex.size();
		if (cbBig5ConvertToGB.isSelected()) {
			infTojHsTable();
		}
		List lsBillOrder = blsInOutStockBillAction
				.findBlsInOutBillBillNo(new Request(CommonVars.getCurrUser()));
		// if (lsBillOrder.size() == 0 || lsBillOrder.get(0) == null) {
		// JOptionPane.showMessageDialog(DgBlsInOutDetailImport.this,
		// "请进行导入参数设置！");
		// return new ArrayList();
		// }
		// InputInOutStockBillOrder data = (InputInOutStockBillOrder)
		// lsBillOrder
		// .get(0);
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
			TempInOutStockBills tempInput = new TempInOutStockBills();
			BlsInOutStockBill bsb = new BlsInOutStockBill();
			BlsInOutStockBillDetail bsd = new BlsInOutStockBillDetail();

			/*
			 * list.add(new JTableListColumn("进出仓标志", "ioFlag", 100));
			 * list.add(new JTableListColumn("单据编号", "billNo", 100));
			 * list.add(new JTableListColumn("单据生效日期", "validDate", 100));
			 * list.add(new JTableListColumn("供货方企业", "corrOwner.name", 180));
			 * list.add(new JTableListColumn("仓库编码", "wareHouseCode.code",
			 * 100));
			 * 
			 * 
			 * list.add(new JTableListColumn("原产国", "originCountry.name", 100));
			 * list.add(new JTableListColumn("企业内部货号", "partNo.ptNo", 100));
			 * list.add(new JTableListColumn("数量", "detailQty", 80));
			 * list.add(new JTableListColumn("申报单价", "unitPrice", 80));
			 * list.add(new JTableListColumn("币值", "curr.name", 80));
			 * list.add(new JTableListColumn("毛重", "grossWeight", 80));
			 * list.add(new JTableListColumn("净重", "netWeight", 80));
			 * list.add(new JTableListColumn("件数", "packCount", 80));
			 * list.add(new JTableListColumn("归并序号", "seqNum", 80));
			 */

			StringBuffer sb = new StringBuffer("");
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				String err = "";
				if ("bsb.ioFlag".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "出入仓标志为空!;");
					} else {
						if (value != null && !value.equals("")) {
							bsb.setIoFlag(value);// 出入仓标志
						} else {
							err = err + "[" + value + "]   出入仓标志不可为空/";
						}
					}
				}

				if ("bsb.billNo".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "行单据号为空!;");
					} else {
						if (billPtNoList.contains(value)) {// 判断单据号是否已经存在
							sb.append("警告:第" + (i + 1) + "行单据号已在表中存在");
						}
						if (value != null && !value.equals("")) {
							bsb.setBillNo(value);// 编码
						} else {
							err = err + "[" + value + "]   编码不可为空/";
						}
					}
				}

				// 生效日期
				if ("bsb.validDate".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							bsb.setValidDate(dateFormat.parse(value.trim()));
						} catch (Exception ex) {
							sb.append("第" + (i + 1) + "行日期格式错误");
						}
					} else {
						err = err + "[" + value + "]   生效日期不可为空/";
					}
				}
				// 客户供应商（代码|名称）
				if ("bsb.corrOwner.name".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						ScmCoc scmCoc = null;
						// scmCoc = (ScmCoc) manufacturerListName
						// .get(value.trim());
						scmCoc = (ScmCoc) manufacturerListName
								.get(value.trim());
						if (scmCoc != null) {
							bsb.setCorrOwner(scmCoc);
						} else {
							sb.append("第" + (i + 1) + "行客户供应商:" + value
									+ "不存在;");
						}
					} else {
						sb.append("第" + (i + 1) + "行客户供应商为空!;");
					}
				}
				// billHead.setBillType(this.getBillType());

				// 仓库编码（代码|名称）
				if ("bsb.wareHouseCode.code".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						ScmCoc scmCoc = null;
						// scmCoc = (ScmCoc) manufacturerListName
						// .get(value.trim());
						WareSet wareset = (WareSet) waresetListCode.get(value
								.trim());
						if (wareset != null) {
							bsb.setWareHouseCode(wareset);
						} else {
							sb.append("第" + (i + 1) + "仓库编码:" + value + "不存在;");
						}
					} else {
						sb.append("第" + (i + 1) + "仓库编码为空!;");
					}
				}

				bsb.setIsEffective(new Boolean(false));

				if ("bsd.seqNo".equals(columnField)) {
					if (value.equals("")) {
						sb.append("第" + (i + 1) + "商品流水号为空!;");
					} else {
						if (value != null && !value.equals("")) {
							bsd.setSeqNo(Integer.valueOf(value));// 商品流水号
						} else {
							err = err + "[" + value + "]   商品流水号不可为空/";
						}
					}
				}

				// 原产国（代码|名称）
				if ("bsd.originCountry.name".equals(columnField)) {
					if (value != null) {
						Country country = null;
						country = (Country) countryrListName.get(value.trim());
						if (country == null) {
							sb.append("警告:第" + (i + 1) + "行所所输入产销国,在系统中未能找到!");
						}
						bsd.setOriginCountry(country);
					}
				}

				// 物料编号
				if ("bsd.partNo.ptNo".equals(columnField)) {
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
							bsd.setPartNo(materiel);
						}
					}
				}

				// 数量
				if ("bsd.detailQty".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   数量不合法/");
							continue;
						}
						bsd.setDetailQty(Double.valueOf(value));
					}
					continue;
				}

				// 单价
				if ("bsd.unitPrice".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   单价不合法/");
							continue;
						}
						bsd.setUnitPrice(Double.valueOf(value));
					}
					continue;
				}

				// 币制（代码|名称）
				if ("bsd.curr.name".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						Curr currency = null;
						currency = (Curr) currListName.get(value.trim());
						if (currency == null) {
							sb.append("警告:第" + i + "行所所输入币制,在系统中未能找到!");
						}
						bsd.setCurr(currency);
					}
				}

				// 毛重
				if ("bsd.grossWeight".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   毛重不合法/");
							continue;
						}
						bsd.setGrossWeight(Double.valueOf(value));
					}
					continue;
				}
				// 净重
				if ("bsd.netWeight".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]   净重不合法/");
							continue;
						}
						bsd.setNetWeight(Double.valueOf(value));
					}
					continue;
				}

				// 件数
				if ("bsd.packCount".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Integer.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    件数不合法/");
							continue;
						}
						bsd.setPackCount(Double.valueOf(value).intValue());
					}
					continue;
				}

				// 归并序号
				if ("bsd.seqNum".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						try {
							Integer.valueOf(value);
						} catch (Exception e) {
							sb.append("[" + value + "]    归并序号不合法/");
							continue;
						}
						bsd.setSeqNum(Integer.valueOf(value));
					}
					continue;
				}

				// 入仓单据商品序号
				if ("bsd.inBillGoodNo".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {

						// try {
						// Integer.valueOf(value);
						// } catch (Exception e) {
						// sb.append("[" + value + "] 入仓单据商品序号不合法/");
						// continue;
						// }
						bsd.setInBillGoodNo(Integer.parseInt(value));
					} else {
						bsd.setInBillGoodNo(null);
					}
					// try {
					// Integer.valueOf(value);
					// } catch (Exception e) {
					// sb.append("[" + value + "] 入仓单据商品序号不合法/");
					// continue;
					// }
					// bsd.setInBillGoodNo(Integer.valueOf(value));
					// }
					//
					// continue;
					// }
				}
				// 入仓单据单据号
				if ("bsd.inBillNo".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						// try {
						// Integer.valueOf(value);
						// } catch (Exception e) {
						// sb.append("[" + value + "]    归并序号不合法/");
						// continue;
						// }
						bsd.setInBillNo(value);
					} else {
						bsd.setInBillNo("");
					}
					continue;
				}

				// 报关单商品序号
				if ("bsd.entryGNo".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						// try {
						// Integer.valueOf(value);
						// } catch (Exception e) {
						// sb.append("[" + value + "]    归并序号不合法/");
						// continue;
						// }
						bsd.setEntryGNo(value);
					} else {
						bsd.setEntryGNo("");
					}
					continue;
				}

				// 报关单18位编号
				if ("bsd.entryID".equals(columnField)) {
					if (value != null && !"".equals(value.trim())) {
						// try {
						// Integer.valueOf(value);
						// } catch (Exception e) {
						// sb.append("[" + value + "]    归并序号不合法/");
						// continue;
						// }
						bsd.setEntryID(value);
					} else {
						bsd.setEntryID("");
					}
					continue;
				}

				tempInput.setBsb(bsb);
				tempInput.setBsd(bsd);
				tempInput.setErrinfo(sb.toString());
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

	// /**
	// * 文本导入多线程
	// *
	// * @author Administrator
	// *
	// */
	//
	// class ImportFileDataRunnable extends SwingWorker {
	//
	// @Override
	// protected Object doInBackground() throws Exception {
	// List list = new ArrayList();
	// try {
	// CommonProgress
	// .showProgressDialog(DgBlsInOutDetailImport.this);
	// CommonProgress.setMessage("系统正在初始化数据，请稍后...");
	// initConditionList();// 初始化查询条件
	// CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
	// list = parseTxtFile();
	// } catch (Exception e) {
	// CommonProgress.closeProgressDialog();
	// JOptionPane.showMessageDialog(DgBlsInOutDetailImport.this,
	// "导入数据失败：！" + e.getMessage(), "提示",
	// JOptionPane.ERROR_MESSAGE);
	// e.printStackTrace();
	// } finally {
	// CommonProgress.closeProgressDialog();
	// }
	// return list;
	// }
	//
	// @Override
	// protected void done() {
	// // TODO Auto-generated method stub
	// super.done();
	// List list = new ArrayList();
	// try {
	// list = (List) this.get();
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (ExecutionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// initTable(list);
	// }
	// }

	public JTableListModel getTableModelHead() {
		return tableModelHead;
	}

	public void setTableModelHead(JTableListModel tableModelHead) {
		this.tableModelHead = tableModelHead;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getBtnInput());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnFieldSet());
			jToolBar.add(getBtnExit());
			jToolBar.add(getCbCheckTitle());
			jToolBar.add(getCbBig5ConvertToGB());
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
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List list = null;
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(
								DgBlsInOutDetailImport.this, "保存导入数据为空!", "提示",
								2);
						return;
					}
					list = tableModel.getList();
					new SaveDataRunnable().start();
				}
			});
		}
		return btnSaveData;
	}

	/**
	 * 保存数据线程
	 * 
	 * @author hw
	 * 
	 */
	class SaveDataRunnable extends Thread {
		public void run() {
			List list = null;
			try {
				list = tableModel.getList();
				for (int i = 0; i < list.size(); i++) {
					TempInOutStockBills obj = (TempInOutStockBills) list.get(i);
					if (obj.getErrinfo() != null
							&& !obj.getErrinfo().equals("")) {
						JOptionPane.showMessageDialog(
								DgBlsInOutDetailImport.this, "有错误信息，不可保存！",
								"提示!", 2);
					}
				}
				// materialManageAction.saveImportDriverInfo(new Request(
				// CommonVars.getCurrUser()), jcbIsOverwrite.isSelected(),
				// list);
				// jmodel.addRows(list);
				blsInOutStockBillAction
						.saveInOutStockBillAndImpExpCommodityInfo(new Request(
								CommonVars.getCurrUser()), list,
								cbAmounTaccumulate.isSelected());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	// /**
	// * 移动文件
	// * @param fSrc
	// * @param fDest
	// */
	// private void moveFile(File fSrc, File fDest) {
	// try {
	// if (fSrc.exists()) {
	// // FileUtils.copyFile(fSrc, fDest);
	// copyFile(fSrc, fDest);
	// fSrc.delete();
	// }
	// } catch (Exception e) {
	// throw new RuntimeException(e);
	// }
	// }
	// /**
	// * 复制文件
	// * @param oldPath
	// * @param newPath
	// */
	// private void copyFile(File oldPath, File newPath) {
	// try {
	// int bytesum = 0;
	// int byteread = 0;
	// File oldfile = oldPath;
	// if (oldfile.exists()) { // 文件存在时
	// InputStream inStream = new FileInputStream(oldPath); // 读入原文件
	// FileOutputStream fs = new FileOutputStream(newPath);
	// byte[] buffer = new byte[1444];
	// int length;
	// while ((byteread = inStream.read(buffer)) != -1) {
	// bytesum += byteread; // 字节数 文件大小
	// System.out.println(bytesum);
	// fs.write(buffer, 0, byteread);
	// }
	// inStream.close();
	// }
	// } catch (Exception e) {
	// System.out.println("复制单个文件操作出错");
	// e.printStackTrace();
	//
	// }
	// }
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
		if (tbBlsInOutDetailImport == null) {
			tbBlsInOutDetailImport = new JTable();
		}
		return tbBlsInOutDetailImport;
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
			btnFieldSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg
							.setTableFlag(InputTableColumnSet.BLS_INOUTSTOCKBILL_INPUT);
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
		tableModel = new JTableListModel(tbBlsInOutDetailImport, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.BLS_INOUTSTOCKBILL_INPUT);
					}
				});
		tbBlsInOutDetailImport.getColumnModel().getColumn(
				tbBlsInOutDetailImport.getColumnCount() - 1).setCellRenderer(
				new TableMultiRowRender());
	}

	/**
	 * This method initializes cbAmounTaccumulate
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbAmounTaccumulate() {
		if (cbAmounTaccumulate == null) {
			cbAmounTaccumulate = new JCheckBox();
			cbAmounTaccumulate.setForeground(Color.blue);
			cbAmounTaccumulate
					.setText("\u6599\u53f7,\u4ea7\u9500\u56fd\u76f8\u540c\u5546\u54c1\u7684\u6570\u91cf\u7d2f\u52a0\uff01");
		}
		return cbAmounTaccumulate;
	}
	/*
	 * String uploaderr = "D:/bestwaydata/uploaderr";// 导入失败路径 String
	 * uploaddatabak = "D:/bestwaydata/uploaddatabak";// 导入成功路径 List list =
	 * parseTxtFile(); for (int i = 0; i < list.size(); i++) {
	 * TempImpExpRequestBillForInput obj = (TempImpExpRequestBillForInput) list
	 * .get(i); if (obj.getErrinfo() != null && !obj.getErrinfo().equals("")) {
	 * JOptionPane.showMessageDialog( DgBlsInOutDetailImport.this,
	 * "有错误信息，不可保存！", "提示!", 2); // ----------------------------------------- //
	 * File uploaderrdir = new File(uploaderr); // File uploaderrdirfile = new
	 * File(uploaderr + File.separator // + txtFile.getName()); // if
	 * (uploaderrdir.exists()) { // moveFile(txtFile, uploaderrdirfile); // } //
	 * ----------------------------------------- return; } } List lsResult =
	 * blsInOutStockBillAction.saveInOutStockBillAndImpExpCommodityInfo( new
	 * Request(CommonVars.getCurrUser()), list,
	 * cbAmounTaccumulate.isSelected()); tableModelHead.addRows(lsResult);
	 * JOptionPane.showMessageDialog( DgBlsInOutDetailImport.this,
	 * "保存数据成功！保存成功的申请单个数是:" + lsResult.size(), "提示!",
	 * JOptionPane.INFORMATION_MESSAGE); //
	 * ------------------------------------------------- // File
	 * uploaddatabakdir = new File(uploaddatabak); // File uploaddatabakdirfile
	 * = new File(uploaddatabak // + File.separator + txtFile.getName()); // if
	 * (uploaddatabakdir.exists()) { // moveFile(txtF/home/hw/桌面/贺巍2009年12月KPI考核 .xlsile, uploaddatabakdirfile);
	 * // } // -------------------------------------------------
	 */
}
