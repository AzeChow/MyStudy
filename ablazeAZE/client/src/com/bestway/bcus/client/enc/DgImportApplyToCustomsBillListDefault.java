/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.bcus.client.enc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcs.contract.action.ContractAction;
import com.bestway.bcs.contract.entity.Contract;
import com.bestway.bcs.dictpor.action.BcsDictPorAction;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.ImportApplyCustomsProperty;
import com.bestway.bcus.enc.entity.ImportApplyToCustomsBillListTempData;
import com.bestway.bcus.manualdeclare.action.ManualDeclareAction;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2k;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 清单数据导入接口
 * 
 * @author fhz // change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class DgImportApplyToCustomsBillListDefault extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;
	/** 参数设置实体类 */
	private ImportApplyCustomsProperty importApplyProperty = null;

	private JButton btnOpenFile = null;

	private JButton btnSaveData = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JScrollPane jScrollPane = null;
	/** 导入文件 */
	private File txtFile = null;
	/** 繁转简 */
	private Hashtable gbHash = null; // @jve:decl-index=0:

	private EncAction encAction = null;
	/** 存放合同备案表头资料 */
	private Contract head = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbIsOverwrite = null;

	private ContractAction contractAction = null;

	private BcsDictPorAction bcsDictPorAction = null;
	/** 是否为帐册 */
	private boolean isEms = true;
	/** 设置表头料件项数 */
	private int materielItemCount = 0;
	/** 表头成品项目个数 */
	private int productItemCount = 0;

	private JCheckBox cbCheckTitle = null;

	private JButton btnColumn = null;

	private JTable jTable = null;

	protected ManualDeclareAction manualDeclareAction = null;

	private JPanel jPanel1 = null;

	private JPanel jPanel11 = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JLabel jLabel3 = null;

	private JLabel jLabel4 = null;

	private JLabel jLabel5 = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public DgImportApplyToCustomsBillListDefault() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.IMPORT_APPLY_CUSTOMS_BILL, this
						.getDefaultTableColumnList());
		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(811, 459);
		this.setContentPane(getJContentPane());
		this.setTitle("清单数据导入接口");
	}

	/**
	 * 初始化进出口清单表
	 * 
	 * @return
	 */

	private List getDefaultTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("进出口类型(名称)", "headImpExpType", 120));
		list.add(new JTableListColumn("清单编号", "headBillListNo", 120,
				Integer.class));
		list.add(new JTableListColumn("申报地海关(名称)", "headDeclareCustom", 100));
		list.add(new JTableListColumn("运输方式(名称)", "headTransportMode", 100));
		list.add(new JTableListColumn("进出口岸(名称)", "headEntrancePort", 100));
		list.add(new JTableListColumn("监管方式(名称)", "headTradeMode", 100));
		list.add(new JTableListColumn("海关版本号", "beforeVersion", 80));
		list.add(new JTableListColumn("料号(不可为空)", "beforeMaterielPtNo", 100));
		list.add(new JTableListColumn("表头备注", "headMemo", 60));
		list.add(new JTableListColumn("企业申报单价", "beforeDeclaredPrice", 120));
		list.add(new JTableListColumn("申报数量", "beforeDeclaredAmount", 100));
		list.add(new JTableListColumn("总价", "beforeTotalPrice", 80));
		list.add(new JTableListColumn("加工费总价", "beforeWorkUsd", 80));
		list.add(new JTableListColumn("产销国(名称)", "beforeSalesCountry", 120));
		list.add(new JTableListColumn("件数", "beforePeice", 80));
		list.add(new JTableListColumn("箱号", "beforeBoxNo", 80));
		list.add(new JTableListColumn("币制(名称)", "beforeCurrency", 60));
		list.add(new JTableListColumn("净重", "beforeNetWeight", 80));
		list.add(new JTableListColumn("毛重", "beforeGrossWeight", 80));
		list.add(new JTableListColumn("征免方式(名称)", "beforeLevyModel", 100));
		list.add(new JTableListColumn("用途(名称)", "beforeUsess", 100));
		list.add(new JTableListColumn("事业部", "beforeProjectDept", 100));
		list.add(new JTableListColumn("法定数量", "beforeLegalAmount", 100));
		list
				.add(new JTableListColumn("第二法定数量", "beforeSecondLegalAmount",
						100));
		list.add(new JTableListColumn("表体备注", "beforeBodyMemo", 100));
		list.add(new JTableListColumn("扩展备注", "beforeExtendMemo", 100));
		list.add(new JTableListColumn("企业版本", "beforeCmpVersion", 80));
		list.add(new JTableListColumn("客户/供应商(代码)", "headCustoms", 100));

		return list;
	}

	/**
	 * 初始化料件
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.IMPORT_APPLY_CUSTOMS_BILL);
					}
				});
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getBtnExit());
			jToolBar.add(getCbJF());
			jToolBar.add(getCbIsOverwrite());
			jToolBar.add(getCbCheckTitle());
		}
		return jToolBar;
	}

	/**
	 * 导入文本过滤
	 * 
	 * @author Administrator
	 * 
	 */

	class ExampleFileFilter extends FileFilter {
		String suffix = "";

		ExampleFileFilter(String suffix) {
			this.suffix = suffix;
		}

		/**
		 * 验证是否导入
		 */

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
		 * 获取文本类型
		 */

		public String getDescription() {
			return "*." + this.suffix;
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
				suffix = s.substring(i + 1).toLowerCase();
			return suffix;
		}
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
	 * This method initializes btnOpenFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1.打开文件");
			btnOpenFile.setPreferredSize(new Dimension(70, 30));
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
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

	/**
	 * 数据文本导入多线程类
	 * 
	 * @author Administrator
	 * 
	 */

	class ImportFileDataRunnable extends Thread {
		public void run() {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgImportApplyToCustomsBillListDefault.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				list = encAction.tempDataCheck(new Request(CommonVars
						.getCurrUser()), importApplyProperty, list);
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
				initTable(list);
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgImportApplyToCustomsBillListDefault.this, ex
								.getMessage(), "提示!",
						JOptionPane.INFORMATION_MESSAGE);
				ex.printStackTrace();
			} finally {
				// final List temp = list;
				CommonProgress.closeProgressDialog();
			}
		}

	}

	private String isImpOrExp(String value) {
		String ImpExpType = null;
		if ("料件进口".equals(value) || "料件转厂".equals(value)
				|| "一般贸易进口".equals(value) || "修理物品".equals(value)
				|| "退料出口".equals(value) || "边角料退港".equals(value)
				|| "料件内销".equals(value) || "边角料内销".equals(value)|| "料件内销，海关批准内销".equals(value)) {
			ImpExpType = "料件";
		} else if ("成品出口".equals(value) || "转厂出口".equals(value)
				|| "返工复出".equals(value) || "一般贸易出口".equals(value)
				|| "修理物品复出".equals(value) || "退厂返工".equals(value)
				|| "进料成品退换".equals(value)||"进料成品退换复出".equals(value)) {
			ImpExpType = "成品";
		} else {
			ImpExpType = "";
		}
		return ImpExpType;
	}
	
	

	/**
	 * 文件解析
	 * 
	 * @return
	 */

	private List parseTxtFile() {
		boolean ischange = false;
		if (getCbJF().isSelected()) {
			ischange = true;
		} else {
			ischange = false;
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
		// 记录循环每行的料件成品类型
		String impExpType = "";
		// 记录循环版本
		Integer version = 0;
		ArrayList list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.IMPORT_APPLY_CUSTOMS_BILL);
		String err = "";
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
			ImportApplyToCustomsBillListTempData obj = new ImportApplyToCustomsBillListTempData();
			for (int j = 0; j < zcount; j++) {

				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("headImpExpType".equals(columnField)) {
					try {
						obj.setHeadImpExpType(value);// 进出口类型
						if (value != null && !"".equals(value)) {
							impExpType = isImpOrExp(value);
							if ("".equals(impExpType)) {
								err = err + "[" + value 
								                      + "]   进出口类型不存在！\n";
							}
							
						}
					} catch (Exception e) {
						err = err + "[" + value + "]   进出口类型不合法/";
						continue;
					}
				} else if ("headBillListNo".equals(columnField)) {
					if (!cbIsOverwrite.isSelected()) {
						if (checkBillListNoOverlap(value.toString().trim())) {
							err = err + "[" + value + "] 清单编号已经存在是否重复\n";
						}
					}
					try {
						obj.setHeadBillListNo(value.trim());// 清单编号
					} catch (Exception e) {
						err = err + "[" + value + "]  清单编号不合法/";
						continue;
					}

				} else if ("headDeclareCustom".equals(columnField)) {
					try {
						obj.setHeadDeclareCustom(value);// 申报地海关
					} catch (Exception e) {
						err = err + "[" + value + "]   申报地海关不合法/";
						continue;
					}
				} else if ("headTransportMode".equals(columnField)) {
					try {
						obj.setHeadTransportMode(value);// 运输方式
					} catch (Exception e) {
						err = err + "[" + value + "]   运输方式不合法/";
						continue;
					}
				} else if ("headEntrancePort".equals(columnField)) {
					try {
						obj.setHeadEntrancePort(value);// 进出口岸
					} catch (Exception e) {
						err = err + "[" + value + "]   进出口岸不合法/";
						continue;
					}
				} else if ("headTradeMode".equals(columnField)) {
					try {
						obj.setHeadTradeMode(value);// 监管方式
					} catch (Exception e) {
						err = err + "[" + value + "]   监管方式不合法/";
						continue;
					}
				} else if ("headCustoms".equals(columnField)) {
					obj.setHeadCustoms(value);// 客户(供商应)
				} else if ("headMemo".equals(columnField)) {
					try {
						// 截取255长度
						value = getLimitLengthString(value, 255);
						obj.setHeadMemo(value);// 表头备注
					} catch (Exception e) {
						err = err + "[" + value + "]   表头备注不合法/";
						continue;
					}
				} else if ("beforeMaterielPtNo".equals(columnField)) {
					try {
						if (value != null && !"".equals(value)) {
							List Merger = null;
							List bom = null;
							value = value.trim();
							if (impExpType.equals("料件")) {
								Merger = encAction
										.findEmsHeadH2kByPtNoFromMerger(
												new Request(CommonVars
														.getCurrUser()), value);
							} else {
								Merger = encAction
										.findEmsHeadH2kExgByPtNoFromMerger(
												new Request(CommonVars
														.getCurrUser()), value);
								bom = encAction
										.findEmsHeadH2kBomByPtNoFromMerger(
												new Request(CommonVars
														.getCurrUser()), value,
												version);
							}
							obj.setBeforeMaterielPtNo(value);// 料号
							if (Merger.size() <= 0) {
								err = err + "[" + value
										+ "]   该料号对应的备案号未在账册中备案！/";
								continue;
							}
							if (bom != null && bom.size() > 0) {
								err = err + "[" + value
										+ "]   该成品在电子账册中有BOM料件未申报！/";
								continue;
							}
						} else {
							err = err + "[" + value + "]   料号不可为空/";
							continue;
						}
					} catch (Exception e) {
						err = err + "[" + value + "]   料号不合法/";
						continue;
					}
				} else if ("beforeDeclaredPrice".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]单价应为数字型/";
					}
					try {
						obj.setBeforeDeclaredPrice(value);// 企业申报单价
					} catch (Exception e) {
						err = err + "[" + value + "]   企业申报单价不合法/";
						continue;
					}
				} else if ("beforeDeclaredAmount".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]申报数量应为数字型/";
					}
					try {
						obj.setBeforeDeclaredAmount(value);// 申报数量
					} catch (Exception e) {
						err = err + "[" + value + "]   申报数量不合法/";
						continue;
					}
				} else if ("beforeTotalPrice".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]总价应为数字型/";
					}
					try {
						obj.setBeforeTotalPrice(value);// 总价
					} catch (Exception e) {
						err = err + "[" + value + "]   总价不合法/";
						continue;
					}
				} else if ("beforeWorkUsd".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]加工费总价应为数字型/";
					}
					try {
						obj.setBeforeWorkUsd(value);// 加工费总价
					} catch (Exception e) {
						err = err + "[" + value + "]   加工费总价不合法/";
						continue;
					}
				} else if ("beforeSalesCountry".equals(columnField)) {
					try {
						obj.setBeforeSalesCountry(value);// 产销国
					} catch (Exception e) {
						err = err + "[" + value + "]   产销国不合法/";
						continue;
					}
				} else if ("beforePeice".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]件数应为数字型/";
					}
					try {
						obj.setBeforePeice(value);// 件数
					} catch (Exception e) {
						err = err + "[" + value + "]  件数不合法/";
						continue;
					}
				} else if ("beforeBoxNo".equals(columnField)) {// 箱号
					obj.setBeforeBoxNo(value);
				} else if ("beforeCurrency".equals(columnField)) {// 币制
					try {
						obj.setBeforeCurrency(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   币制不合法/";
						continue;
					}
				} else if ("beforeNetWeight".equals(columnField)) {// 净重
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]净重应为数字型/";
					}
					try {
						obj.setBeforeNetWeight(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   净重不合法/";
						continue;
					}
				} else if ("beforeGrossWeight".equals(columnField)) {// 毛重
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]毛重应为数字型/";
					}
					try {
						obj.setBeforeGrossWeight(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   毛重不合法/";
						continue;
					}
				} else if ("beforeLevyModel".equals(columnField)) {// 征免方式
					try {
						obj.setBeforeLevyModel(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   征免方式不合法/";
						continue;
					}
				} else if ("beforeUsess".equals(columnField)) {// 用途
					try {
						obj.setBeforeUsess(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   用途不合法/";
						continue;
					}
				} else if ("beforeProjectDept".equals(columnField)) {// 事业部
					try {
						obj.setBeforeProjectDept(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   事业部不合法/";
						continue;
					}
				} else if ("beforeLegalAmount".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]法定数量应为数字型/";
					}
					try {
						obj.setBeforeLegalAmount(value);// 法定数量
					} catch (Exception e) {
						err = err + "[" + value + "]   法定数量不合法/";
						continue;
					}
				} else if ("beforeSecondLegalAmount".equals(columnField)) {
					if (value != null && !"".equals(value) && !isDigit(value)) {
						err = err + "[" + value + "]二法定数量应为数字型/";
					}
					try {
						obj.setBeforeSecondLegalAmount(value);// 第二法定数量
					} catch (Exception e) {
						err = err + "[" + value + "]   第二法定数量不合法/";
						continue;
					}
				} else if ("beforeVersion".equals(columnField)) {// 海关版本
					obj.setBeforeVersion(value);
					/**
					 * 对海关版本的导入字段进行检查,2013.8.26
					 */
					if((ImpExpType.getImpExpTypeDesc(ImpExpType.DIRECT_EXPORT).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.TRANSFER_FACTORY_EXPORT).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.BACK_MATERIEL_EXPORT).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.REWORK_EXPORT).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.REMIAN_MATERIAL_BACK_PORT).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.REMIAN_MATERIAL_DOMESTIC_SALES).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.GENERAL_TRADE_EXPORT).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.EXPORT_MATERIAL_REBACK).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.EXPORT_EXG_REBACK).equals(obj.getHeadImpExpType()))){
						
						if (value == null || "".equals(value)) {
							err = err + "进出口类型为[" + obj.getHeadImpExpType()
							+ "] 海关版本号不能为空\n";
						}else{
							try {
								version = Integer.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   版本号不合法/";
								continue;
							}
						}
						
					}else if(ImpExpType.getImpExpTypeDesc(ImpExpType.DIRECT_IMPORT).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.TRANSFER_FACTORY_IMPORT).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.BACK_FACTORY_REWORK).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.GENERAL_TRADE_IMPORT).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.MATERIAL_DOMESTIC_SALES).equals(obj.getHeadImpExpType()) 
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.IMPORT_EXG_BACK).equals(obj.getHeadImpExpType())
							|| ImpExpType.getImpExpTypeDesc(ImpExpType.IMPORT_REPAIR_MATERIAL).equals(obj.getHeadImpExpType())){
						
					}
					
				} else if ("beforeCmpVersion".equals(columnField)) {// 企业版本
					obj.setBeforeCmpVersion(value == null ? "" : value.trim());
					
					
					if (("退厂返工".equals(obj.getHeadImpExpType())
							|| "成品出口".equals(obj.getHeadImpExpType())
							|| "转厂出口".equals(obj.getHeadImpExpType())
							|| "返工复出".equals(obj.getHeadImpExpType()) || "一般贸易出口"
							.equals(obj.getHeadImpExpType()))) {
						if (value != null
								&& !"".equals(value)
								&& (obj.getBeforeVersion() == null || ""
										.equals(obj.getBeforeVersion()))) {
							if (obj.getBeforeMaterielPtNo() != null
									&& !"".equals(obj.getBeforeMaterielPtNo())) {
								Integer cmpVer = manualDeclareAction
										.getVersion(new Request(CommonVars
												.getCurrUser()), obj
												.getBeforeMaterielPtNo(), obj
												.getBeforeCmpVersion().trim());
								if(cmpVer==-1){
									err = err + "料号所对应的企业版本在归并关系中未找到海关版本/";
								}
								obj.setBeforeVersion(String.valueOf(cmpVer));
							} else {
								err = err + "料号不存在/";
							}
						} 
					} else {
						if (value != null && !"".equals(value)) {
							err = err + "进出口类型为[" + obj.getHeadImpExpType()
									+ "] 企业版本号应该为空\n";
						}
					}

				} else if ("beforeMemos".equals(columnField)) {
					try {
						obj.setBeforeMemos(value);// 表体备注
					} catch (Exception e) {
						err = err + "[" + value + "]   表体备注不合法/";
						continue;
					}
				} else if ("beforeExtendMemo".equals(columnField)) {
					try {
						obj.setBeforeExtendMemo(value);// 扩展备注
					} catch (Exception e) {
						err = err + "[" + value + "]   扩展备注不合法/";
						continue;
					}
				}
				// if (tpnImpExpType.getSelectedIndex() == 1) {
				// obj.setMaterielType("Export");
				// } else if (tpnImpExpType.getSelectedIndex() == 0) {
				// obj.setMaterielType("Import");
				// }

				if (obj.getBeforeCmpVersion() != null&& !"".equals(obj.getBeforeCmpVersion())
						&& (obj.getBeforeVersion() == null || "".equals(obj.getBeforeVersion()))
						&& obj.getBeforeMaterielPtNo() != null&& !"".equals(obj.getBeforeMaterielPtNo())) {
					Integer cmpVer = manualDeclareAction.getVersion(
							new Request(CommonVars.getCurrUser()), obj
									.getBeforeMaterielPtNo(), obj
									.getBeforeCmpVersion().trim());
					obj.setBeforeVersion(String.valueOf(cmpVer));
				}
				List contracts = manualDeclareAction
						.findEmsHeadH2kInExecuting(new Request(CommonVars
								.getCurrUser(), true));
				for (int l = 0; l < contracts.size(); l++) {
					obj
							.setHeadEmsNo(((EmsHeadH2k) contracts.get(l))
									.getEmsNo());
				}
				obj.setErrinfo(err);      
			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * 断判是否为数字型字符
	 * 
	 * @param list
	 * @param strs
	 */
	private boolean isDigit(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		int d = 0;
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (!Character.isDigit(c[i])) {
				if ((c[i] + "").equals(".")) {
					d++;
				} else
					return false;
			}
		}
		if (d < 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 断判清单编号是否重复
	 * 
	 * @param value
	 * @return
	 */
	private boolean checkBillListNoOverlap(String value) {
		return encAction.checkBillListNoOverlap(new Request(CommonVars
				.getCurrUser()), value);

	}

	/**
	 * 繁转简
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
	 * 繁体转换成简体
	 * 
	 * @param source
	 * @return
	 */

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		// for (int i = 0, n = source.length; i < n; i++) {
		// newStrs[i] = changeStr(source[i]);
		// }
		return newStrs;
	}

	/**
	 * 获得导入文件的所有列值
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
	 * This method initializes btnSaveData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveData() {
		if (btnSaveData == null) {
			btnSaveData = new JButton();
			btnSaveData.setText("2.保存数据");
			btnSaveData.setPreferredSize(new Dimension(70, 30));
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List ls = new ArrayList();
					ls = tableModel.getList();
					for (int i = 0; i < ls.size(); i++) {
						ImportApplyToCustomsBillListTempData obj = (ImportApplyToCustomsBillListTempData) ls
								.get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgImportApplyToCustomsBillListDefault.this,
									"有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}
					SaveFileDataRunnable thread = new SaveFileDataRunnable(ls);
					thread.start();
				}
			});
		}
		return btnSaveData;
	}

	/**
	 * 保存文本数据多线程类
	 * 
	 * @author ower
	 * 
	 */
	class SaveFileDataRunnable extends Thread {
		List ls = null;

		public SaveFileDataRunnable(List list) {
			this.ls = list;
		}

		/**
		 * 保存文本
		 */

		public void run() {
			final List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgImportApplyToCustomsBillListDefault.this);
				CommonProgress.setMessage("系统正在保存文件资料，请稍后...");
				encAction.importDataFromFile(new Request(CommonVars
						.getCurrUser()), ls, importApplyProperty, cbIsOverwrite
						.isSelected());
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgImportApplyToCustomsBillListDefault.this,
						"保存完毕，请关闭所有通关备案界面然后重新打开！", "提示!", 2);
				DgImportApplyToCustomsBillListDefault.this.dispose();
			} catch (Exception ex) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgImportApplyToCustomsBillListDefault.this, ex
								.getMessage(), "提示!",
						JOptionPane.INFORMATION_MESSAGE);
				ex.printStackTrace();
			} finally {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						initTable(list);
					}
				});
			}
		}
	}

	/**
	 * This method initializes btnExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.setPreferredSize(new Dimension(70, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportApplyToCustomsBillListDefault.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBorder(null);
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
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
			cbJF.setPreferredSize(new Dimension(70, 30));
		}
		return cbJF;
	}

	/**
	 * This method initializes cbIsOverwrite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOverwrite() {
		if (cbIsOverwrite == null) {
			cbIsOverwrite = new JCheckBox();
			cbIsOverwrite.setText("资料存在覆盖导入");
		}
		return cbIsOverwrite;
	}

	public Contract getHead() {
		return head;
	}

	public void setHead(Contract head) {
		this.head = head;
	}

	public boolean isEms() {
		return isEms;
	}

	public void setEms(boolean isEms) {
		this.isEms = isEms;
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
	 * This method initializes btnColumn
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("栏位设定");
			btnColumn.setPreferredSize(new Dimension(70, 30));
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg
							.setTableFlag(InputTableColumnSet.IMPORT_APPLY_CUSTOMS_BILL);
					dg.setVisible(true);
					if (dg.isOk()) {
						initTable(new ArrayList());
					}
				}
			});
		}
		return btnColumn;
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
	 * 截取字符串 len为字节长度
	 * 
	 * @param str
	 * @param len
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	public static String getLimitLengthString(String str, int len) {

		try {

			int counterOfDoubleByte = 0;

			byte[] b = str.getBytes("gb2312");

			if (b.length <= len)

				return str;

			for (int i = 0; i < len; i++) {

				if (b[i] < 0)

					counterOfDoubleByte++;

			}

			if (counterOfDoubleByte % 2 == 0)

				return new String(b, 0, len, "gb2312");

			else

				return new String(b, 0, len - 1, "gb2312");

		} catch (Exception ex) {

			ex.printStackTrace();

			return "";

		}

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
			jPanel1.setPreferredSize(new Dimension(1, 120));
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);
			jPanel1.add(getJPanel11(), BorderLayout.CENTER);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel11
	 * 
	 * @return javax.swing.JPanel
	 * hwy 2012-09-02
	 */
	private JPanel getJPanel11() {
		if (jPanel11 == null) {
			jLabel = new JLabel();
			jLabel.setForeground(java.awt.Color.blue);
			jLabel.setBounds(new Rectangle(8, 57, 222, 18));
			jLabel.setText("说明：同一份清单导入相同的客户/供应商");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(236, 59, 292, 18));
			jLabel7.setText("4、海关版本与企业版本栏位的数据不允许同时为空");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new Rectangle(434, 42, 286, 21));
			jLabel6.setText("3、同一个成品海关版本号与企业版本号不可同时存在");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(236, 42, 200, 18));
			jLabel5.setText("2、企业版本为空，海关版本不为空");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new Rectangle(236, 24, 564, 18));
			jLabel4.setText("1、企业版本不为空，海关版本为空，系统自动转海关版本。方法是根据企业版本从归并关系BOM获取版本");
			jLabel3 = new JLabel();
			jLabel3.setForeground(java.awt.Color.blue);
			jLabel3.setBounds(new Rectangle(10, 24, 217, 18));
			jLabel3.setText("关于导入海关、企业版本栏位注意事项：");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(9, 3, 196, 17));
			jLabel2.setText("说明：海关版本与企业版本的区别：");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(197, 2, 608, 19));
			jLabel1
					.setText("海关版本指需要在海关备案的，海关所规定的版本号如：0，1，2....企业版本：指企业内部使用的，无需海关备案");
			jPanel11 = new JPanel();
			jPanel11.setLayout(null);
			jPanel11.add(jLabel1, null);
			jPanel11.add(jLabel2, null);
			jPanel11.add(jLabel3, null);
			jPanel11.add(jLabel4, null);
			jPanel11.add(jLabel5, null);
			jPanel11.add(jLabel6, null);
			jPanel11.add(jLabel7, null);
			jPanel11.add(jLabel, null);
		}
		return jPanel11;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

