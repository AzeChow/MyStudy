package com.bestway.common.client.erpbillcenter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.cas.parameterset.entity.BillCorrespondingControl;
import com.bestway.bcus.cas.parameterset.entity.CasBillOption;
import com.bestway.bcus.client.cas.parameter.CasCommonVars;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.WareSet;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgBillTxtImportNew extends JDialogBase {

	private javax.swing.JPanel jPanel = null;
	private JToolBar jToolBar = null;
	private JButton btnOpenFile = null;
	private JButton btnSaveData = null;
	private JButton btnSet = null;
	private JButton btnCancel = null;
	private JCheckBox cbIsTitle = null;
	private JCheckBox cbIsCurrentDate = null;
	private JPanel jPanel2 = null;
	private JRadioButton rbMaterials = null;
	private JRadioButton rbProduct = null;
	private JRadioButton rbHalfProduct = null;
	private JRadioButton rbEquipment = null;
	private JRadioButton rbScrap = null;
	private JRadioButton rbImperfections = null;
	private JPanel jPanel3 = null;
	private JTableListModel tableModel = null;
	private Hashtable mapParamers = new Hashtable(); // @jve:decl-index=0:
	private Hashtable headHash = null;
	private File txtFile = null;
	private CasAction casAction = null;
	// private Hashtable gbHash = null; // @jve:decl-index=0:
	private JPanel jPanel4 = null;
	private ButtonGroup bg = null; // @jve:decl-index=0:
	private JRadioButton rbIsBig5ConvertToGB = null;
	private List headList = new ArrayList(); // @jve:decl-index=0:
	// private boolean isNoErr = true;
	private JRadioButton rbImperfections2 = null;
	private JRadioButton rbImperfections3 = null;
	private JCheckBox cbIsImportCustomNo = null;
	private JCheckBox cbIsValid = null;
	private JPanel jPanel1 = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JComboBox cbbScmCoc = null;
	private JComboBox cbbWarehouse = null;
	private JComboBox cbbValidDate = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	/** 单据对应控制对象 */
	private BillCorrespondingControl billCorrespondingControl = null; // @jve:decl-index=0:
	private JButton btnDel = null;

	/**
	 * This method initializes
	 * 
	 */
	public DgBillTxtImportNew() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
		InputTableColumnSetUtils.putColumn(InputTableColumnSet.BILL_INPUT, this
				.getDefaultTableColumnList());
		initTableBill(new ArrayList());

	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(838, 506));
		this.setContentPane(getJPanel());
		this.setTitle("单据导入");
		getBg();
		billCorrespondingControl = CasCommonVars.getBillCorrespondingControl();
		cbIsImportCustomNo
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						if (cbIsImportCustomNo.isSelected()) {
							if (billCorrespondingControl == null
									|| billCorrespondingControl
											.getIsHandContrl() == null
									|| (!billCorrespondingControl
											.getIsHandContrl())) {
								JOptionPane
										.showMessageDialog(
												DgBillTxtImportNew.this,
												"管理报表-参数设置-单据对应控制,\n对应关系不是手工控制，因此该选项不能生效！",
												"提示！",
												JOptionPane.WARNING_MESSAGE);
								cbIsImportCustomNo.setSelected(false);
							}
						}
					}
				});

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
			jPanel.add(getJPanel3(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			FlowLayout fl = new FlowLayout();
			fl.setAlignment(FlowLayout.LEFT);
			fl.setVgap(1);
			fl.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setFloatable(false);
			jToolBar.setLayout(fl);
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnSet());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnCancel());
			jToolBar.add(getCbIsTitle());
			jToolBar.add(getRbIsBig5ConvertToGB());
		}
		return jToolBar;
	}

	/**
	 * This method initializes btnOpenFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1打开文件");
			btnOpenFile.setPreferredSize(new Dimension(85, 30));
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
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
		return btnOpenFile;
	}

	// 调出文件选择框
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

	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();

			List detailList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgBillTxtImportNew.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				detailList = (List) list.get(0);
				headList = (List) list.get(1);
				initTableBill(detailList);
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				initTableBill(new ArrayList());
				throw new RuntimeException(e);
			} finally {
				CommonProgress.closeProgressDialog();
			}
		}
	}

	private List parseTxtFile() {
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.BILL_INPUT);

		initMap();

		// 物料类型名称
		String wlTypeName = (String) mapParamers.get(5);
		/**
		 * 单据类型名称 与 单据类型代码 关联
		 */
		Hashtable mapBillType = this.casAction.findCodeAndFactryNameByName(
				new Request(CommonVars.getCurrUser()), wlTypeName);// <名称，代码>
		/**
		 * 料号 与 报关常用工厂物料 关联
		 */
		Map<String, Materiel> mapPtNoM = this.casAction.getMaterialByType(
				new Request(CommonVars.getCurrUser()), wlTypeName);

		Boolean isHalf = false;
		if (("半成品".equals(wlTypeName)) || "残次品(半成品)".equals(wlTypeName)) {
			isHalf = true;
		}
		/**
		 * 对应关系料号+报关名称+报关规格+报关单位 + (手册号？)与本身 关联 （半成品特殊：资料来自报关常用工厂物料）
		 */

		Map<String, String> mapHsHs = new HashMap<String, String>();
		Map<String, String> MapHsHsEmsNo = new HashMap<String, String>();
		if (isHalf) {
			// 来源于工厂物料取得料号、名称、规格
			mapHsHs = this.casAction.findDistinctMaterielByPtNoNameSpecUnit(
					new Request(CommonVars.getCurrUser()), wlTypeName);
		} else {
			// 来源于工厂与实际对应表取得料号、名称、规格、单位
			mapHsHs = this.casAction.findDistinctFFCByPtNoNameSpecUnit(
					new Request(CommonVars.getCurrUser()), wlTypeName, false);
			// 来源于工厂与实际对应表取得料号、名称、规格、单位、手册号
			MapHsHsEmsNo = this.casAction.findDistinctFFCByPtNoNameSpecUnit(
					new Request(CommonVars.getCurrUser()), wlTypeName, true);

		}
		/*
		 * 
		 * 客户/供应商 与 其代码 关联
		 */
		Map<String, String> mapScm = this.casAction.getScmCocForImport(
				new Request(CommonVars.getCurrUser()), (String) mapParamers
						.get(1));// 客户MAP

		/**
		 * 单据头 表名
		 */
		String tablename = getMasterTableName(wlTypeName);
		/**
		 * 单据头，用于过滤
		 */
		List headlist = this.casAction.getCasHeadForImport(new Request(
				CommonVars.getCurrUser()), tablename);// 单据头，用于过滤
		/**
		 * 参数设置
		 */
		CasBillOption casBillOption = this.casAction
				.findCasBillOption(new Request(CommonVars.getCurrUser()));
		/**
		 * 仓库list,用于检查仓库
		 */
		List<WareSet> warSets = this.casAction.findWareSet(new Request(
				CommonVars.getCurrUser()));
		Map<String, String> ckHsByCode = new HashMap<String, String>();
		Map<String, String> ckHsByName = new HashMap<String, String>();

		for (int i = 0; i < warSets.size(); i++) {
			WareSet obj = (WareSet) warSets.get(i);
			if (obj != null && obj.getCode() != null
					&& !obj.getCode().equals("")) {
				ckHsByCode.put(obj.getCode(), obj.getId());
			}
			if (obj != null && obj.getName() != null
					&& !obj.getName().equals("")) {
				ckHsByName.put(obj.getName(), obj.getId());
			}
		}
		// ------------------------------------------------------------------------------
		List allList = new ArrayList();
		// 是否导入对应报关单号
		boolean isincustoms = Boolean.parseBoolean(mapParamers.get(
				"isInputCustoms").toString());
		// 第一行是否为标题
		boolean isTitle = Boolean.parseBoolean(mapParamers.get("isTitle")
				.toString());
		int beginRow = 1;
		if (isTitle) {
			beginRow = 2;
		}
		// 如果生效日期为空，是否以当天为生效日期，如果否的话，提示出错
		boolean isCurrentDate = Boolean.parseBoolean(mapParamers.get(
				"isCurrentDate").toString());
		// 获取生效日期
		String dateFormatStr = (mapParamers == null ? "yyyy-MM-dd"
				: (String) mapParamers.get(3));

		String curDate = "";
		if (isCurrentDate) {
			Calendar cal = Calendar.getInstance();
			curDate = "" + cal.get(Calendar.YEAR) + "-";
			if ((cal.get(Calendar.MONTH) + 1) < 10) {
				curDate += "0" + (cal.get(Calendar.MONTH) + 1) + "-";
			} else {
				curDate += "" + (cal.get(Calendar.MONTH) + 1) + "-";
			}
			if ((cal.get(Calendar.DATE)) < 10) {
				curDate += "0" + cal.get(Calendar.DATE);
			} else {
				curDate += "" + cal.get(Calendar.DATE);
			}
		}
		// boolean ischange = true;
		// if (getRbIsBig5ConvertToGB().isSelected()) {
		// infTojHsTable();
		// } else {
		// ischange = false;
		// }
		// ------------------------------------------------------------------------------
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			lines = FileReading.readExcel(txtFile, beginRow, null);
		} else {
			lines = FileReading.readTxt(txtFile, beginRow, null);
		}
		// ------------------------------------------------------------------------------
		Random random = new Random();// 单据唯一号
		ArrayList list = new ArrayList(); // 表头
		ArrayList listDetail = new ArrayList();// 表体
		ArrayList repeatHeadList = new ArrayList(); // 错误表头list
		int zcount = lsIndex.size();// 13; // 字段数目
		for (int i = 0; i < lines.size(); i++) {
			String err = "";
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			if (getRbIsBig5ConvertToGB().isSelected()) {
				strs = changStrs(strs);
			}
			// Object[] objs = null;
			BillTemp obj = new BillTemp();
			String billCode = "";
			boolean isCheck = true;
			for (int j = 0; j < zcount; j++) {
				String columnField = lsIndex.get(j);
				String value = getFileColumnValue(strs, j);
				if ("bill1".equals(columnField)) {
					obj.setBill1(value);// 单据类型代码
					if (mapBillType.get(value) == null) {
						err = err + "单据类型代码没有设置或没有与之对应  / ";
						continue;
					} else {
						billCode = (String) mapBillType.get(value);
						// 车间返回=1006,车间领用=1101,车间入库=2002,返回车间=2103
						// 料件转仓入库=1010，料件转仓出库=1105，成品转仓入库=2005，成品转仓出库=2109
						// 导入时可以不需要录入客户供应商名称
//						if (!(billCode.equals("1006") || billCode.equals("1101")
//								|| billCode.equals("2002")
//								|| billCode.equals("2103")
//								|| billCode.equals("1010")
//								|| billCode.equals("1105")
//								|| billCode.equals("2005")
//								|| billCode.equals("2109"))) {
//							isCheck = true;
//						}
						//hwy2012-11-15
						 if(billCode.equals("1006")){
						 isCheck = casBillOption.getIsWorkShopBack();
						 }else if(billCode.equals("1101")){
						 isCheck = casBillOption.getIsOutNeedCustomer();
						 }else if(billCode.equals("2002")){
						 isCheck = casBillOption.getIsInNeedCustomer();
						 }else if(billCode.equals("2103")){
						 isCheck = casBillOption.getIsBackWorkShop();
						 }
						 else if(billCode.equals("1010") || billCode.equals("1105")
								 || billCode.equals("2109") || billCode.equals("2005")){
						 isCheck = false;
						
						 }
					}

				} else if ("bill2".equals(columnField)) {
					obj.setBill2(value);// 单据号码
				} else if ("bill3".equals(columnField)) {
					String ck1 = (mapParamers == null ? ""
							: (String) mapParamers.get(1));
					String scmName = ck1.equals("名称全称") ? "名称全称" : (ck1
							.equals("名称简称") ? "名称简称" : "代码");
					obj.setBill3(value);// 客户供应商
					if (isCheck) {
						if (mapScm.get(value.trim()) == null) {
							err = err + "客户供应商" + scmName + "在客户供应商表中不存在  / ";
							continue;
						}
					}
				} else if ("bill4".equals(columnField)) {
					if (value == null || value.equals("")) {
						if (isCurrentDate) {
							value = curDate;
						} else {
							err = err + "生效日期为空  / ";
							continue;
						}
					} else {
						SimpleDateFormat bartDateFormat = new SimpleDateFormat(
								dateFormatStr);
						try {
							bartDateFormat.parse(value);
						} catch (ParseException e) {
							err = err + "生效日期= [" + dateFormatStr
									+ "] 格式不合法  / ";
							continue;
						}
					}
					obj.setBill4(value);// 生效日期
				} else if ("envelopNo".equals(columnField)) {// 关封号wss2010.08.31添加
					obj.setEnvelopNo(value);
				}

				else if ("bill5".equals(columnField)) {// 备注
					obj.setBill5(value);
				}

				else if ("bill100".equals(columnField)) {// 备注栏后面的空列
					obj.setBill100(String.valueOf(random));// 存放单据唯一号
				} else if ("bill6".equals(columnField)) {
					obj.setBill6(value);// 料号
					// ------------------------------------------------------------
					if (!(billCode.equals("2011") || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
							|| billCode.equals("1015") || billCode
							.equals("1016"))) {// 已收货未结转期初单,已结转未收货期初单
						// 如果不为以上期初单，则检查料号是否存在
						if (value == null || value.equals("")) {
							err = err + "料号为空!";
							continue;
						}
						if (mapPtNoM.get(value) == null) {
							err = err + "料号在报关常用物料中不存在  / ";
							continue;
						}
					}
					// ------------------------------------------------------------
				} else if ("bill7".equals(columnField)) {
					if (value == null || "".equals(value)) {
						value = "0.0";
					}
					obj.setBill7(value);// 净重
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "净重不合法  / ";
						continue;
					}
					// obj.setBill100(String.valueOf(random));// 存放单据唯一号
				} else if ("bill8".equals(columnField)) {// 毛重

					if (value == null || "".equals(value)) {
						continue;
					}
					obj.setBill8(value);
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "毛重不合法  / ";
						continue;
					}
				} else if ("bill9".equals(columnField)) {
					if (value == null || "".equals(value)) {
						value = "0.0";
					}
					obj.setBill9(value);// 数量
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "数量不合法  / ";
						continue;
					}

				} else if ("bill10".equals(columnField)) {
					if (value == null || "".equals(value)) {
						continue;
					} else {
						obj.setBill10(value);// 单价
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "单价不合法  / ";
							continue;
						}
					}
				} else if ("bill11".equals(columnField)) {
					if (value == null || "".equals(value)) {
						continue;
					}
					obj.setBill11(value.trim());// 版本号
					try {
						if (Double.valueOf(value) == 0)
							value = "0";
						Integer.valueOf(value);
					} catch (Exception e) {
						err = err + "版本号不合法  / ";
						continue;
					}

				} else if ("bill12".equals(columnField)) {
					String ck1 = (mapParamers == null ? ""
							: (String) mapParamers.get(2));
					String sck = value;
					obj.setBill12(value);// 仓库
					if (sck != null && !sck.equals("")) {
						if (ck1 != null && ck1.equals("名称")) {
							if (!ckHsByName.containsKey(sck)) {
								err = err + "仓库名称 = " + sck + " 在仓库基本表中不存在  / ";
								continue;
							}
						} else {
							if (!ckHsByCode.containsKey(sck)) {
								err = err + "仓库名称 = " + sck + " 在仓库基本表中不存在  / ";
								continue;
							}
						}
					}
				} else if ("bill13".equals(columnField)) {
					obj.setBill13(value);// 制单号
					if (value != null && (value.length() > 255)) {
						err = err + "制单号不可以超过255位  / ";
						continue;
					}
				} else if ("bill14".equals(columnField)) {
					if (value == null || "".equals(value)) {
						continue;
					} else {
						obj.setBill14(value);// 折算报关数量
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "折算报关数量不合法  / ";
							continue;
						}
					}
				} else if ("bill15".equals(columnField)) {
					if (value == null || "".equals(value)) {
						obj.setBill15(null);// 对应数量
					} else {
						if (isincustoms) {
							obj.setBill15(value);// 对应数量
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + " 对应数量不合法  / ";
								continue;
							}
						}
					}
				} else if ("bill16".equals(columnField)) {
					if (value == null || "".equals(value)) {
						continue;
					} else {
						obj.setBill16(value);// 海关单价
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + " 海关单价不合法  / ";
							continue;
						}
					}
				}
				// =======================以下为导入期初单用
				else if ("bill18".equals(columnField)) {
					// if (billCode.equals("2011")
					// || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
					// || billCode.equals("1015")
					// || billCode.equals("1016")) {// //已收货未结转期初单,已结转未收货期初单
					obj.setBill18(value);// 报关名称
					// }
				} else if ("bill19".equals(columnField)) {
					// if (billCode.equals("2011")
					// || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
					// || billCode.equals("1015")
					// || billCode.equals("1016")) {// //已收货未结转期初单,已结转未收货期初单
					obj.setBill19(value);// 报关规格
					// }
				} else if ("bill20".equals(columnField)) {
					// if (billCode.equals("2011")
					// || billCode.equals("2012")// 已交货未结转期初单,已结转未交货期初单
					// || billCode.equals("1015")
					// || billCode.equals("1016")) {// //已收货未结转期初单,已结转未收货期初单
					obj.setBill20(value);// 报关单位
					// }
				} else if ("emsNo".equals(columnField)) {// 手册号
					// System.out.println("wss emsNo:" + value);
					obj.setEmsNo(value);
				} else if ("bill17".equals(columnField)) {
					if (mapParamers.get("isInputCustoms") != null
							&& (Boolean.parseBoolean(mapParamers.get(
									"isInputCustoms").toString()))) {
						if (value == null || "".equals(value)) {
							err = err + " 对应报关单号选项选择后没有导入数据  / ";
							continue;
						} else {
							obj.setBill17(value);// 对应报关单号
						}

					}
				} else if ("note".equals(columnField)) { // 单据备注
					if (wlTypeName != null && wlTypeName.equals("残次品(料件)")) {
						value = "料件";
					} else if (wlTypeName != null
							&& wlTypeName.equals("残次品(成品)")) {
						value = "成品";
					} else if (wlTypeName != null
							&& wlTypeName.equals("残次品(半成品)")) {
						value = "半成品";
					}
					obj.setNote(value);
					if (billCode.equals("1005")
							&& (casBillOption.getIsNoticeBillNo() == null || casBillOption
									.getIsNoticeBillNo().booleanValue() == true)) {
						String hsKey = obj.getNote() == null ? "" : obj
								.getNote().trim();
						if (hsKey.equals("") || hsKey.length() != 8) {
							err = err
									+ " 参数设置勾选了[国内购买检查备注发票号],而[单体备注]栏位未备注长度为8位的发票号码  / ";
						}
					}

				} else if ("takeBillNo".equals(columnField)) {// 24.送货单号
					// System.out.println("j = 24 value = " + value);
					obj.setTakeBillNo(value);
				} else if ("orderNo".equals(columnField)) {// 24 订单号
					// System.out.println("j = 24 value = " + value);
					obj.setOrderNo(value);
				}
			}
			// 只用报关名称来存在
			boolean isExistHsName = true;
			// 未填报关资料
			if (obj.getBill18() == null || "".equals(obj.getBill18().trim())) {
				isExistHsName = false;
			}

			// 如果 有填写报关名称的
			if (isExistHsName) {// wss:2010.07.12修改
				String ptNo = obj.getBill6() == null ? "" : obj.getBill6()
						.toString().trim();// 料号
				String hsName = obj.getBill18() == null ? "" : obj.getBill18()
						.toString().trim();// 报关名称
				String hsSpec = obj.getBill19() == null ? "" : obj.getBill19()
						.toString().trim();// 报关规格
				String hsUnitName = obj.getBill20() == null ? "" : obj
						.getBill20().toString().trim();// 报关单位
				String emsNo = obj.getEmsNo() == null ? "" : obj.getEmsNo()
						.toString().trim();// 手册号
				// key=料号+ "/" + 报关名称+"/"+报关规格+"/"+报关单位
				String key1 = "";
				// 当手册号为空时
				if (emsNo == null || "".equals(emsNo)) {
					String str = "";
					if (isHalf) {
						key1 = ptNo + "/" + hsName + "/" + hsSpec;
						str = "报关常用工厂物料";
					} else {
						key1 = ptNo + "/" + hsName + "/" + hsSpec + "/"
								+ hsUnitName;
						str = "对应关系";
					}
					if (mapHsHs.get(key1) == null) {
						err = err + "【 料号=" + ptNo + ",报关名称=" + hsName
								+ ",报关规格=" + hsSpec + ",报关单位=" + hsUnitName
								+ "】在" + str + "中不存在  / ";

					}
				} else {
					// 当不为空时
					key1 = ptNo + "/" + hsName + "/" + hsSpec + "/"
							+ hsUnitName + "/" + emsNo;
					if (MapHsHsEmsNo.get(key1) == null) {
						err = err + "【 料号=" + ptNo + ",报关名称=" + hsName
								+ ",报关规格=" + hsSpec + ",报关单位=" + hsUnitName
								+ ",手册号=" + emsNo + "】在对应关系中不存在  / ";
					}
				}

			}

			if (!casBillOption.getIsBillRepeat()) {// 不允许单据头重复
				// 单据号+单据类型代码+生效日期格+客户供应商(代码)
				String head = (obj.getBill2() == null ? "" : obj.getBill2())
						+ (String) mapBillType.get(obj.getBill1() == null ? ""
								: obj.getBill1())
						+ (obj.getBill4() == null ? "" : obj.getBill4())
						+ mapScm.get(obj.getBill3());
				if (headlist.contains(head)) {
						err = err + "单据头有重复，不能导入  / ";
						repeatHeadList.add(head);
				}
			}
			obj.setErrinfo(err);
			listDetail.add(obj);// 表体
			// String key = (obj.getBill2() == null ? "" : obj.getBill2());
			String key = (obj.getBill1() == null ? "" : obj.getBill1()) + "/"
					+ (obj.getBill2() == null ? "" : obj.getBill2()) + "/"
					+ (obj.getBill3() == null ? "" : obj.getBill3()) + "/"
					+ (obj.getBill4() == null ? "" : obj.getBill4()) + "/"
					+ (obj.getBill5() == null ? "" : obj.getBill5()) + "/"
					+ obj.getBill100();
			if (headHash.get(key) == null) {
				headHash.put(key, key);
				list.add(obj);
			}
		}
		allList.add(listDetail);
		allList.add(list);
		return allList;
	}

	private String getMasterTableName(String type) {
		String tableName = "";
		if (type != null && type.equals("料件")) {
			tableName = "BillMasterMateriel";
		} else if (type != null && type.equals("成品")) {
			tableName = "BillMasterProduct";
		} else if (type != null && type.equals("设备")) {
			tableName = "BillMasterFixture";
		} else if (type != null && type.equals("半成品")) {
			tableName = "BillMasterHalfProduct";
		} else if (type != null && type.indexOf("残次品") > -1) {
			tableName = "BillMasterRemainProduct";
		} else if (type != null && type.equals("边角料")) {
			tableName = "BillMasterLeftoverMateriel";
		}
		return tableName;
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	/**
	 * This method initializes btnSaveData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveData() {
		if (btnSaveData == null) {
			btnSaveData = new JButton();
			btnSaveData.setText("2保存数据");
			btnSaveData.setPreferredSize(new Dimension(85, 30));
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// List dlist = null;
					// String temp = "";
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(DgBillTxtImportNew.this,
								"无保存的数据!", "提示", 2);
						return;
					}
					if (mapParamers == null) {
						JOptionPane.showMessageDialog(DgBillTxtImportNew.this,
								"参数没有设置!", "提示", 2);
						return;
					} else if (mapParamers.get(5) == null) {
						JOptionPane.showMessageDialog(DgBillTxtImportNew.this,
								"物料类别没有设置!", "提示", 2);
						return;
					}
					boolean isNoErr = true;
					for (int i = 0; i < tableModel.getList().size(); i++) {
						BillTemp billTemp = (BillTemp) tableModel.getList()
								.get(i);
						if (billTemp.getErrinfo() != null
								&& !"".equals(billTemp.getErrinfo())) {
							isNoErr = false;
						}
					}
					if (!isNoErr) {
						System.out.println(isNoErr);
						JOptionPane.showMessageDialog(DgBillTxtImportNew.this,
								"导入文件其中数据有错,不能保存。", "提示!", 2);
						return;
					}
					new SaveDataRunnable().start();
				}
			});
		}
		return btnSaveData;
	}

	class SaveDataRunnable extends Thread {
		boolean isok = true;

		public void run() {
			List dlist = null;
			try {
				CommonProgress.showProgressDialog(DgBillTxtImportNew.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				dlist = tableModel.getList();
				try {
					casAction.executeBillImport(new Request(CommonVars
							.getCurrUser()), headList, dlist, mapParamers);
				} catch (SQLException e1) {
					isok = false;
					e1.printStackTrace();
				}
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {
				isok = false;
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
				if (isok) {
					initTableBill(new ArrayList());
					JOptionPane.showMessageDialog(DgBillTxtImportNew.this,
							"导入完毕!\n\n单据头数据: "
									+ String.valueOf(headList.size())
									+ " 笔\n单据体数据: "
									+ String.valueOf(dlist.size()) + " 笔",
							"提示", 2);
				} else {
					JOptionPane.showMessageDialog(DgBillTxtImportNew.this,
							"装载失败！", "提示！", 2);
				}
			}
		}
	}

	/**
	 * This method initializes btnSet
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSet() {
		if (btnSet == null) {
			btnSet = new JButton();
			btnSet.setText("栏位设定");
			btnSet.setPreferredSize(new Dimension(85, 30));
			btnSet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg.setTableFlag(InputTableColumnSet.BILL_INPUT);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTableBill(new ArrayList());
					}
				}
			});
		}
		return btnSet;
	}

	/**
	 * This method initializes btnCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("退出");
			btnCancel.setPreferredSize(new Dimension(85, 30));
			btnCancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnCancel;
	}

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setText("第一行为标题");
			cbIsTitle.setSelected(true);
			cbIsTitle.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					mapParamers.put("isTitle", getCbIsTitle().isSelected());
				}
			});
		}
		return cbIsTitle;
	}

	/**
	 * This method initializes cbIsCurrentDate
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsCurrentDate() {
		if (cbIsCurrentDate == null) {
			cbIsCurrentDate = new JCheckBox();
			cbIsCurrentDate.setText("如果生效日期为空,默认当天为生效日期");
			cbIsCurrentDate.setBounds(new Rectangle(11, 21, 232, 20));
		}
		return cbIsCurrentDate;
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
			jPanel2.setPreferredSize(new Dimension(690, 100));
			jPanel2.add(getJPanel4(), java.awt.BorderLayout.WEST);
			jPanel2.add(getJPanel1(), BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes rbMaterials
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbMaterials() {
		if (rbMaterials == null) {
			rbMaterials = new JRadioButton();
			rbMaterials.setText("料件");
			rbMaterials.setBounds(new Rectangle(10, 47, 68, 20));
			rbMaterials.setSelected(true);
		}
		return rbMaterials;
	}

	/**
	 * This method initializes rbProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbProduct() {
		if (rbProduct == null) {
			rbProduct = new JRadioButton();
			rbProduct.setText("成品");
			rbProduct.setBounds(new Rectangle(82, 49, 68, 18));
		}
		return rbProduct;
	}

	/**
	 * This method initializes rbHalfProduct
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbHalfProduct() {
		if (rbHalfProduct == null) {
			rbHalfProduct = new JRadioButton();
			rbHalfProduct.setText("半成品");
			rbHalfProduct.setBounds(new Rectangle(153, 48, 68, 19));
		}
		return rbHalfProduct;
	}

	/**
	 * This method initializes rbEquipment
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbEquipment() {
		if (rbEquipment == null) {
			rbEquipment = new JRadioButton();
			rbEquipment.setText("设备");
			rbEquipment.setBounds(new Rectangle(247, 49, 57, 17));
		}
		return rbEquipment;
	}

	/**
	 * This method initializes rbScrap
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbScrap() {
		if (rbScrap == null) {
			rbScrap = new JRadioButton();
			rbScrap.setText("边角料");
			rbScrap.setBounds(new Rectangle(316, 48, 62, 20));
		}
		return rbScrap;
	}

	/**
	 * This method initializes rbImperfections
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImperfections() {
		if (rbImperfections == null) {
			rbImperfections = new JRadioButton();
			rbImperfections.setText("残次品(料件方式)");
			rbImperfections.setBounds(new Rectangle(11, 74, 119, 18));
		}
		return rbImperfections;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.add(getJPanel2(), BorderLayout.NORTH);
			jPanel3.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel3;
	}

	private List getDefaultTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("单据名称", "bill1", 100));// wss:2010.06.10修改
		list.add(new JTableListColumn("单据号码", "bill2", 100));
		list.add(new JTableListColumn("客户供应商(代码)", "bill3", 120));
		list.add(new JTableListColumn("生效日期(YYYY-MM-DD)", "bill4", 140));
		list.add(new JTableListColumn("关封号", "envelopNo", 120));// wss2010.08.31新增关封号
		list.add(new JTableListColumn("备注", "bill5", 100));

		list.add(new JTableListColumn("料号", "bill6", 100));
		list.add(new JTableListColumn("净重", "bill7", 100));
		list.add(new JTableListColumn("毛重", "bill8", 100));
		list.add(new JTableListColumn("数量", "bill9", 100));
		list.add(new JTableListColumn("单价", "bill10", 100));
		list.add(new JTableListColumn("版本号", "bill11", 100));
		list.add(new JTableListColumn("仓库(代码)", "bill12", 120));
		list.add(new JTableListColumn("制单号", "bill13", 100));
		list.add(new JTableListColumn("折算报关数量", "bill14", 135));
		list.add(new JTableListColumn("对应数量", "bill15", 100));
		list.add(new JTableListColumn("海关单价", "bill16", 100));
		list.add(new JTableListColumn("报关名称", "bill18", 100));
		list.add(new JTableListColumn("报关规格", "bill19", 100));
		list.add(new JTableListColumn("报关单位", "bill20", 100));
		list.add(new JTableListColumn("手册号", "emsNo", 100));// wss2010.09.07
		list.add(new JTableListColumn("对应报关单", "bill17", 100));
		list.add(new JTableListColumn("单体备注", "note", 100));
		list.add(new JTableListColumn("送货单号", "takeBillNo", 100));
		list.add(new JTableListColumn("订单号", "orderNo", 100));
		return list;
	}

	private void initTableBill(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.BILL_INPUT);
					}
				});
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		// ((MultiSpanCellTable) jTable).combineRows(2, new int[] { 1, 2, 3, 4
		// });
	}

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

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(6, 20, 403, 18));
			jLabel3.setForeground(Color.blue);
			jLabel3.setText("注意：“单据类别”在单据管理里面设置，系统根据工厂单据名称自动识别");
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBorder(BorderFactory.createTitledBorder(null,
					"\u8bf7\u9009\u62e9\u7269\u6599\u7c7b\u522b",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel4.setPreferredSize(new Dimension(410, 80));
			jPanel4.add(getRbMaterials(), null);
			jPanel4.add(getRbProduct(), null);
			jPanel4.add(getRbScrap(), null);
			jPanel4.add(getRbImperfections(), null);
			jPanel4.add(getRbEquipment(), null);
			jPanel4.add(getRbHalfProduct(), null);
			jPanel4.add(getRbImperfections2(), null);
			jPanel4.add(getRbImperfections3(), null);
			jPanel4.add(jLabel3, null);
		}
		return jPanel4;
	}

	public ButtonGroup getBg() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getRbMaterials());
			bg.add(getRbProduct());
			bg.add(getRbHalfProduct());
			bg.add(getRbEquipment());
			bg.add(getRbScrap());
			bg.add(getRbImperfections());
			bg.add(getRbImperfections2());
			bg.add(getRbImperfections3());
		}
		return bg;
	}

	private void initMap() {
		if (mapParamers == null) {
			mapParamers = new Hashtable();
		} else {
			mapParamers.clear();
		}
		// 1.客户供应商
		String scmcoc = cbbScmCoc.getSelectedItem() == null ? "" : cbbScmCoc
				.getSelectedItem().toString();
		mapParamers.put(1, scmcoc);
		// 2.仓库
		String ck = cbbWarehouse.getSelectedItem() == null ? "" : cbbWarehouse
				.getSelectedItem().toString();
		mapParamers.put(2, ck);
		// 3.生效日期
		String isvdate = cbbValidDate.getSelectedItem() == null ? ""
				: cbbValidDate.getSelectedItem().toString();
		mapParamers.put(3, isvdate);

		// 5.物料类型
		if (rbMaterials.isSelected()) {
			mapParamers.put(5, "料件");
		} else if (rbProduct.isSelected()) {
			mapParamers.put(5, "成品");
		} else if (rbEquipment.isSelected()) {
			mapParamers.put(5, "设备");
		} else if (rbHalfProduct.isSelected()) {
			mapParamers.put(5, "半成品");
		} else if (rbImperfections.isSelected()) {
			mapParamers.put(5, "残次品(料件)");
		} else if (rbImperfections2.isSelected()) {
			mapParamers.put(5, "残次品(成品)");
		} else if (rbImperfections3.isSelected()) {
			mapParamers.put(5, "残次品(半成品)");
		} else if (rbScrap.isSelected()) {
			mapParamers.put(5, "边角料");
		}
		mapParamers.put("isInputCustoms", getCbIsImportCustomNo().isSelected());
		mapParamers.put("isTitle", getCbIsTitle().isSelected());
		mapParamers.put("isCurrentDate", getCbIsCurrentDate().isSelected());
		mapParamers.put("isValid", getCbIsValid().isSelected());
	}

	/**
	 * This method initializes rbIsBig5ConvertToGB
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbIsBig5ConvertToGB() {
		if (rbIsBig5ConvertToGB == null) {
			rbIsBig5ConvertToGB = new JRadioButton();
			rbIsBig5ConvertToGB.setText("【开始导入】繁转简   [文本格式见下方表格表头排列顺序]");
		}
		return rbIsBig5ConvertToGB;
	}

	/**
	 * This method initializes rbImperfections2
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImperfections2() {
		if (rbImperfections2 == null) {
			rbImperfections2 = new JRadioButton();
			rbImperfections2.setBounds(new Rectangle(135, 73, 117, 19));
			rbImperfections2
					.setText("\u6b8b\u6b21\u54c1(\u6210\u54c1\u65b9\u5f0f)");
		}
		return rbImperfections2;
	}

	/**
	 * This method initializes rbImperfections3
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbImperfections3() {
		if (rbImperfections3 == null) {
			rbImperfections3 = new JRadioButton();
			rbImperfections3.setBounds(new Rectangle(253, 73, 129, 19));
			rbImperfections3
					.setText("\u6b8b\u6b21\u54c1(\u534a\u6210\u54c1\u65b9\u5f0f)");
		}
		return rbImperfections3;
	}

	/**
	 * This method initializes cbIsImportCustomNo
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsImportCustomNo() {
		if (cbIsImportCustomNo == null) {
			cbIsImportCustomNo = new JCheckBox();
			cbIsImportCustomNo.setText("导入对应报关单号");
			cbIsImportCustomNo.setBounds(new Rectangle(11, 78, 232, 16));
		}
		return cbIsImportCustomNo;
	}

	/**
	 * This method initializes cbIsValid
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsValid() {
		if (cbIsValid == null) {
			cbIsValid = new JCheckBox();
			cbIsValid.setText("导入后单据默认生效");
			cbIsValid.setBounds(new Rectangle(11, 52, 232, 15));
		}
		return cbIsValid;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(246, 78, 60, 18));
			jLabel2.setText("生效日期");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(246, 51, 60, 18));
			jLabel1.setText("仓库");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(246, 20, 60, 18));
			jLabel.setText("客户供应商");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBorder(BorderFactory.createTitledBorder(null,
					"\u53c2\u6570\u8bbe\u7f6e",
					TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog",
							Font.PLAIN, 12), new Color(51, 51, 51)));
			jPanel1.setPreferredSize(new Dimension(300, 50));
			jPanel1.add(getCbIsCurrentDate(), null);
			jPanel1.add(getCbIsImportCustomNo(), null);
			jPanel1.add(getCbIsValid(), null);
			jPanel1.add(getCbbScmCoc(), null);
			jPanel1.add(getCbbWarehouse(), null);
			jPanel1.add(getCbbValidDate(), null);
			jPanel1.add(jLabel, null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(jLabel2, null);
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
	 * This method initializes cbbScmCoc
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbScmCoc() {
		if (cbbScmCoc == null) {
			cbbScmCoc = new JComboBox();
			cbbScmCoc.addItem("");
			cbbScmCoc.addItem("代码");
			cbbScmCoc.addItem("名称全称");
			cbbScmCoc.addItem("名称简称");
			cbbScmCoc.setSelectedIndex(2);
			cbbScmCoc.setBounds(new Rectangle(307, 21, 106, 21));
		}
		return cbbScmCoc;
	}

	/**
	 * This method initializes cbbWarehouse
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbWarehouse() {
		if (cbbWarehouse == null) {
			cbbWarehouse = new JComboBox();
			cbbWarehouse.addItem("");
			cbbWarehouse.addItem("代码");
			cbbWarehouse.addItem("名称");
			cbbWarehouse.setSelectedIndex(2);
			cbbWarehouse.setBounds(new Rectangle(307, 49, 106, 21));
		}
		return cbbWarehouse;
	}

	/**
	 * This method initializes cbbValidDate
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbbValidDate() {
		if (cbbValidDate == null) {
			cbbValidDate = new JComboBox();
			cbbValidDate.addItem("");
			cbbValidDate.addItem("yyyy-MM-dd");
			cbbValidDate.addItem("yyyy/MM/dd");
			cbbValidDate.addItem("yyyyMMdd");
			cbbValidDate.setSelectedIndex(1);
			cbbValidDate.setBounds(new Rectangle(307, 75, 106, 21));
		}
		return cbbValidDate;
	}

	/**
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
					List ls = new ArrayList();
					//删除表头
					for (int i = 0; i < headList.size(); i++) {
						BillTemp headTemp = (BillTemp)headList.get(i);
						if(headTemp.getErrinfo() != null
								&& !"".equals(headTemp.getErrinfo())){
							ls.add(headTemp);
						}
					}
					headList.removeAll(ls);
					
					//删除表体
					ls.clear();
					ls = tableModel.getList();
					List delList = new ArrayList();
					if (ls != null && ls.size() > 0) {
						for (int i = 0; i < ls.size(); i++) {
							BillTemp obj = (BillTemp) ls
									.get(i);
							if (obj.getErrinfo() != null
									&& !obj.getErrinfo().equals("")) {
								delList.add(obj);
							}
						}
						//删除错误数据
						tableModel.deleteRows(delList);
						System.out.println(tableModel);
					}
				}
			});
		}
		return btnDel;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
