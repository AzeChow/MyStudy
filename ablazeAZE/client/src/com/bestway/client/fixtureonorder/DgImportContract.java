package com.bestway.client.fixtureonorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

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
import com.bestway.bcus.client.enc.DgImportApplyToCustomsBillListDefault;
import com.bestway.bcus.custombase.entity.hscode.Complex;
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
import com.bestway.common.constant.DeclareState;
import com.bestway.fixasset.entity.TempImportEquipmentContract;
import com.bestway.fixtureonorder.action.FixtureContractAction;
import com.bestway.fixtureonorder.entity.FixtureContract;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgImportContract extends JDialogBase{
	


	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;
	private ImportApplyCustomsProperty importApplyProperty = null;// 参数设置实体类
	private JButton btnOpenFile = null;

	private JButton btnSaveData = null;

	private JButton btnExit = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JScrollPane jScrollPane = null;

	private File txtFile = null;

	private Hashtable gbHash = null; // @jve:decl-index=0:

	private EncAction encAction = null;

	private Contract head = null; // @jve:decl-index=0:

	private JTableListModel tableModel = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbIsOverwrite = null;

	private ContractAction contractAction = null;

	private BcsDictPorAction bcsDictPorAction = null;
	
	private FixtureContractAction fixtureContractAction = null;

	private boolean isEms = true;

	private int materielItemCount = 0; // 设置表头料件项数

	private int productItemCount = 0; // 表头成品项目个数
	
	private FixtureContract fixtureContract = null; // 合同对象  //  @jve:decl-index=0:

	public FixtureContract getFixtureContract() {
		return fixtureContract;
	}

	public void setFixtureContract(FixtureContract fixtureContract) {
		this.fixtureContract = fixtureContract;
	}

	/**
	 * 第一行为标题
	 */
	private JCheckBox cbCheckTitle = null;

	private JButton btnColumn = null;

	private JTabbedPane tpnImpExpType = null;

	private JTable jTable = null;
	protected ManualDeclareAction manualDeclareAction = null;

	/**
	 * This is the default constructor
	 */
	public DgImportContract() {
		super();
		encAction = (EncAction) CommonVars.getApplicationContext().getBean(
				"encAction");
		manualDeclareAction = (ManualDeclareAction) CommonVars
				.getApplicationContext().getBean("manualdeclearAction");
		fixtureContractAction = (FixtureContractAction)CommonVars
		.getApplicationContext().getBean("fixtureContractAction");
		initialize();
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.EQUIPMENT_CONTRACT, this
						.getDefaultTableColumnList());
		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(697, 459);
		this.setContentPane(getJContentPane());
		this.setTitle("设备协议导入接口");
	}

	private List getDefaultTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();		
//		list.add(new JTableListColumn("手册编号", "emsNo", 100));
//		list.add(new JTableListColumn("进口合同号", "impContractNo", 100));
//		list.add(new JTableListColumn("出口合同号", "expContractNo", 100));
//		list.add(new JTableListColumn("批文号", "sancEmsNo", 100));
//		list.add(new JTableListColumn("协议书号", "agreementNo", 100));
////		list.add(new JTableListColumn("合同状态", "declareState", 60));
//		list.add(new JTableListColumn("开始有效期", "beginDate", 100));
//		list.add(new JTableListColumn("有效期限", "availabilityDate", 100));
//		list.add(new JTableListColumn("延期期限", "deferDate", 100));
//		list.add(new JTableListColumn("报送关区", "declareCustoms", 100));
////		list.add(new JTableListColumn("经营单位代码", "tradeCode", 100));
////		list.add(new JTableListColumn("经营单位名称", "tradeName", 100));
//
//		list.add(new JTableListColumn("进口口岸", "iePort1", 100));
////		list.add(new JTableListColumn("外商公司", "outTradeCo", 100));
//		list.add(new JTableListColumn("征免性质", "levyKindName", 50));
//		list.add(new JTableListColumn("保税方式", "payModeName", 50));
//		list.add(new JTableListColumn("贸易方式", "tradeType", 100));
//		list.add(new JTableListColumn("贸易国别", "tradeCountryName", 100));
////		list.add(new JTableListColumn("收货单位代码", "machCode", 100));
////		list.add(new JTableListColumn("收货单位名称", "machName", 100));
////		list.add(new JTableListColumn("企业地址", "enterpriseAddress", 100));
////		list.add(new JTableListColumn("联系人", "linkMan", 100));
////		list.add(new JTableListColumn("联系电话", "contactTel", 100));
//		//list.add(new JTableListColumn("协义书号", "agreementNo", 100));
//
////		list.add(new JTableListColumn("设备总金额", "fixtureAmount", 100));
//		list.add(new JTableListColumn("重点标志", "emphasisFlag", 100));
//		list.add(new JTableListColumn("币制", "curr", 100));
//		list.add(new JTableListColumn("监管费用", "wardshipFee", 100));
//		list.add(new JTableListColumn("监管费率", "wardshipRate", 100));
//		list.add(new JTableListColumn("成交方式", "transac", 100));
//		list.add(new JTableListColumn("进口口岸2", "iePort2", 100));
//		list.add(new JTableListColumn("进口口岸3", "iePort3", 100));
//		list.add(new JTableListColumn("进口口岸4", "iePort4", 100));
//		list.add(new JTableListColumn("进口口岸5", "iePort5", 100));
//		list.add(new JTableListColumn("审批人", "retrialer", 100));
//		list.add(new JTableListColumn("审批日期", "approveDate", 100));
////		list.add(new JTableListColumn("设备项数", "machineCount", 100));
//		list.add(new JTableListColumn("表头备注", "memo", 100));
		
		//表体
		list.add(new JTableListColumn("分协议号", "secondProtocol",100));
		list.add(new JTableListColumn("进口日期", "importDate", 100));
		list.add(new JTableListColumn("商品序号", "seqNum", 100));
		list.add(new JTableListColumn("商品编码", "complexCode", 100));
		list.add(new JTableListColumn("商品名称", "name", 100));
		list.add(new JTableListColumn("规格型号", "spec", 100));
		list.add(new JTableListColumn("计量单位", "unitName", 100));
		list.add(new JTableListColumn("单价", "declarePrice", 100));
		list.add(new JTableListColumn("数量 ", "amount", 100));
//		list.add(new JTableListColumn("总金额", "totalPrice", 100));		
		list.add(new JTableListColumn("法定单位", "firstUnitName", 100));
		list.add(new JTableListColumn("原产地", "countryName", 100));
		list.add(new JTableListColumn("征免方式", "levyMode", 100));	
		list.add(new JTableListColumn("设备类型", "equipmentType", 100));	
		list.add(new JTableListColumn("详细型号规格", "detailedSpec", 100));	
		list.add(new JTableListColumn("表体备注", "itemMemo", 100));
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
								.getTableColumnList(InputTableColumnSet.EQUIPMENT_CONTRACT);
					}
				});
//		jTable.getColumnModel().getColumn(6).setCellRenderer(
//				new DefaultTableCellRenderer() {
//					public Component getTableCellRendererComponent(
//							JTable table, Object value, boolean isSelected,
//							boolean hasFocus, int row, int column) {
//						super.getTableCellRendererComponent(table, value,
//								isSelected, hasFocus, row, column);
//						super.setText((value == null) ? "" : castValue1(value));
//						return this;
//					}
//
//					private String castValue1(Object value) {
//						String returnValue = "";
//
//						if (value.equals(DeclareState.APPLY_POR)) {
//							returnValue = "正在申请";
//						} else if (value.equals(DeclareState.PROCESS_EXE)) {
//							returnValue = "正在执行";
//						} else if (value.equals(DeclareState.CHANGING_EXE)) {
//							returnValue = "正在变更";
//						}
//						return returnValue;
//					}
//				});
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
			jToolBar.add(getBtnOpenFile());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getBtnExit());
			jToolBar.add(getCbJF());
//			jToolBar.add(getCbIsOverwrite());
			jToolBar.add(getCbCheckTitle());
		}
		return jToolBar;
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

	/**
	 * This method initializes btnOpenFile
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1.打开文件");
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					txtFile = getFile();
					if (txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().execute();					
				}
			});
		}
		return btnOpenFile;
	}

	class ImportFileDataRunnable extends SwingWorker {		
		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgImportContract.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			} finally {				
				initTable(list);
				return null;
			}
		}
	}

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

		ArrayList list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.EQUIPMENT_CONTRACT);		
		int zcount = lsIndex.size();//字段数目		
		for (int i = 0; i < lines.size(); i++) {	
			String err = "";
			List line = lines.get(i);
			String[] strs = new String[line.size()];
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = (obj == null ? "" : obj.toString());
			}           
			if (ischange) {
				strs = changStrs(strs);
			}

			TempImportEquipmentContract obj = new TempImportEquipmentContract();
			for (int j = 0; j < zcount; j++) {

				String value = getFileColumnValue(strs, j);
			    if(value!=null && "".equals(value)){
			    	value=value.trim();
			    }
				String columnField = lsIndex.get(j);
//				if ("emsNo".equals(columnField)) {
//					if(value==null || "".equals(value)){
//						err = err + "[" + value + "] 帐册编号不能为空/";
//					}
//					try {
//						obj.setEmsNo(value);// 帐册编号
//					} catch (Exception e) {
//						err = err + "[" + value + "] 帐册编号不合法/";
//						continue;
//					}
//				} else if ("sancEmsNo".equals(columnField)) {
//					try {
//						obj.setSancEmsNo(value);// 批文号
//					} catch (Exception e) {
//						err = err + "[" + value + "]  批文号不合法/";
//						continue;
//					}
//				}
////				else if ("declareState".equals(columnField)) {
////					if(value==null || "".equals("")){
////						value = DeclareState.APPLY_POR;
////					}
////					if(DeclareState.APPLY_POR.equals(value.trim()) || "正在申请".equals(value.trim())){
////						value = DeclareState.APPLY_POR;
////					}else if(DeclareState.PROCESS_EXE.equals(value.trim()) || "正在执行".equals(value.trim())){
////						value = DeclareState.PROCESS_EXE;
////					}else if(DeclareState.CHANGING_EXE.equals(value.trim()) || "正在变更".equals(value.trim())){
////						value = DeclareState.CHANGING_EXE;
////					}else{
////						err = err +"[" + value + "] 帐册状态不合法/";
////					}
////					try {
////						obj.setDeclareState(value);// 帐册状态
////					} catch (Exception e) {
////						err = err + "[" + value + "]   帐册状态不合法/";
////						continue;
////					}
////				}
//				else if ("beginDate".equals(columnField)) {					
//					try {
//						if(value!=null && !"".equals(value) && !isDate(value)){
//							err = err + "[" + value + "] 开始有效期限不合法";
//						}						
//						obj.setBeginDate(value);// 开始有效期
//					} catch (Exception e) {
//						err = err + "[" + value + "]   开始有效期不合法/";
//						continue;
//					}
//				}else if ("declareCustoms".equals(columnField)) {
//					try {
//						obj.setDeclareCustoms(value);// 报关关区
//					} catch (Exception e) {
//						err = err + "[" + value + "]   报关关区不合法/";
//						continue;
//					}
//				}
////				else if ("tradeCode".equals(columnField)) {
////					try {
////						obj.setTradeCode(value);// 经营单位代码
////					} catch (Exception e) {
////						err = err + "[" + value + "]   经营单位代码不合法/";
////						continue;
////					}
////				} else if ("tradeName".equals(columnField)) {
////					try {
////						obj.setTradeName(value);//  经营单位名称
////					} catch (Exception e) {
////						err = err + "[" + value + "]    经营单位名称不合法/";
////						continue;
////					}
////				}  
//				else if ("tradeType".equals(columnField)) {					
//					try {
//						obj.setTradeType(value);// 贸易方式
//					} catch (Exception e) {
//						err = err + "[" + value + "]  贸易方式不合法/";
//						continue;
//					}
//
//				} else if ("availabilityDate".equals(columnField)) {
//					try {
//						if(value!=null && !"".equals(value) && !isDate(value)){
//							err = err + "[" + value + "] 有效期限不合法";
//						}
//						obj.setAvailabilityDate(value);//有效期限
//					} catch (Exception e) {
//						err = err + "[" + value + "] 有效期限不合法/";
//						continue;
//					}
//				} else if ("tradeCountry".equals(columnField)) {
//					try {
//						obj.setTradeCountry(value);// 贸易国别
//					} catch (Exception e) {
//						err = err + "[" + value + "]   贸易国别不合法/";
//						continue;
//					}
//				} 
////				else if ("machCode".equals(columnField)) {
////					try {
////						obj.setMachCode(value);// 收货单位代码
////					} catch (Exception e) {
////						err = err + "[" + value + "]   收货单位代码不合法/";
////						continue;
////					}
////				}else if ("machName".equals(columnField)) {
////					try {
////						obj.setMachName(value);// 收货单位名称
////					} catch (Exception e) {
////						err = err + "[" + value + "]   收货单位名称不合法/";
////						continue;
////					}
////				}
//				else if ("deferDate".equals(columnField)) {
//					try {
//						if(value!=null && !"".equals(value) && !isDate(value)){
//							err = err + "[" + value + "] 延期期限不合法";
//						}
//						obj.setDeferDate(value);//  延期期限
//					} catch (Exception e) {
//						err = err + "[" + value + "] 延期期限不合法/";
//						continue;
//					}
//				}
////				else if ("enterpriseAddress".equals(columnField)) {
////					try {
////						obj.setEnterpriseAddress(value);// 企业地址
////					} catch (Exception e) {
////						err = err + "[" + value + "]   企业地址不合法/";
////						continue;
////					}
////				} else if ("linkMan".equals(columnField)) {
////					try {
////						obj.setLinkMan(value);// 联系人
////					} catch (Exception e) {
////						err = err + "[" + value + "]  联系人不合法/";
////						continue;
////					}
////				} else if ("contactTel".equals(columnField)) {
////					try {
////						obj.setContactTel(value);// 联系电话
////					} catch (Exception e) {
////						err = err + "[" + value + "]   联系电话不合法/";
////						continue;
////					}
////				} 
//				else if ("agreementNo".equals(columnField)) {				
//					try {
//						obj.setAgreementNo(value);// 协义书号
//					} catch (Exception e) {
//						err = err + "[" + value + "] 协义书号不合法/";
//						continue;
//					}
//				} else if ("impContractNo".equals(columnField)) {					
//					try {
//						obj.setImpContractNo(value);// 进口合同号
//					} catch (Exception e) {
//						err = err + "[" + value + "]   进口合同号不合法/";
//						continue;
//					}
//				} else if ("expContractNo".equals(columnField)) {
//					try {
//						obj.setExpContractNo(value);// 出口合同号
//					} catch (Exception e) {
//						err = err + "[" + value + "]   出口合同号不合法/";
//						continue;
//					}
//				}
////				else if ("fixtureAmount".equals(columnField)) {
////					if (value !=null && !"".equals(value) && !isDigit(value)) {
////						err = err + "[" + value + "]设备总金额应为数字型/";
////					}
////					try {
////						obj.setFixtureAmount(value);// 设备总金额
////					} catch (Exception e) {
////						err = err + "[" + value + "]  设备总金额不合法/";
////						continue;
////					}
////				} 
//				else if ("emphasisFlag".equals(columnField)) {
//					try {
//						obj.setEmphasisFlag(value);// 重点标志
//					} catch (Exception e) {
//						err = err + "[" + value + "]   重点标志不合法/";
//						continue;
//					}
//				}else if ("curr".equals(columnField)) {
//					try {
//						obj.setCurr(value);// 币制
//					} catch (Exception e) {
//						err = err + "[" + value + "]   币制不合法/";
//						continue;
//					}
//				} else if ("wardshipFee".equals(columnField)) {
//					if (value !=null && !"".equals(value) && !isDigit(value)) {
//						err = err + "[" + value + "]监管费用应为数字型/";
//					}
//					try {
//						obj.setWardshipFee(value);// 监管费用
//					} catch (Exception e) {
//						err = err + "[" + value + "]   监管费用不合法/";
//						continue;
//					}
//				} else if ("wardshipRate".equals(columnField)) {
//					if (value !=null && !"".equals(value)&&!isDigit(value)) {
//						err = err + "[" + value + "] 监管费率应为数字型/";
//					}
//					try {
//						obj.setWardshipRate(value);// 监管费率
//					} catch (Exception e) {
//						err = err + "[" + value + "]   监管费率不合法/";
//						continue;
//					}
//				} else if ("transac".equals(columnField)) {
//					try {
//						obj.setTransac(value);//成交方式
//					} catch (Exception e) {
//						err = err + "[" + value + "]   成交方式不合法/";
//						continue;
//					}
//				} else if ("iePort1".equals(columnField)) {
//					try {
//						obj.setIePort1(value);// 进口口岸
//					} catch (Exception e) {
//						err = err + "[" + value + "]   进口口岸不合法/";
//						continue;
//					}
//				}else if ("outTradeCo".equals(columnField)) {
//					try {
//						obj.setOutTradeCo(value);// 外商公司
//					} catch (Exception e) {
//						err = err + "[" + value + "]   外商公司不合法/";
//						continue;
//					}
//				}else if ("levyKindName".equals(columnField)) {
//					try {
//						obj.setLevyKindName(value);// 征免性质
//					} catch (Exception e) {
//						err = err + "[" + value + "]   征免性质不合法/";
//						continue;
//					}
//				}else if ("payModeName".equals(columnField)) {
//					try {
//						obj.setPayModeName(value);// 保税方式
//					} catch (Exception e) {
//						err = err + "[" + value + "]   保税方式不合法/";
//						continue;
//					}
//				}else if ("outTradeCo".equals(columnField)) {
//					try {
//						obj.setOutTradeCo(value);// 贸易方式
//					} catch (Exception e) {
//						err = err + "[" + value + "]   贸易方式不合法/";
//						continue;
//					}
//				}else if ("tradeCountryName".equals(columnField)) {
//					try {
//						obj.setTradeCountryName(value);// 贸易国别
//					} catch (Exception e) {
//						err = err + "[" + value + "]   贸易国别不合法/";
//						continue;
//					}
//				} else if ("iePort2".equals(columnField)) {
//					try {
//						obj.setIePort2(value);// 进口口岸2
//					} catch (Exception e) {
//						err = err + "[" + value + "]   进口口岸2不合法/";
//						continue;
//					}
//				}  else if ("iePort3".equals(columnField)) {
//					try {
//						obj.setIePort3(value);// 进口口岸2
//					} catch (Exception e) {
//						err = err + "[" + value + "]   进口口岸3不合法/";
//						continue;
//					}
//				}  else if ("iePort4".equals(columnField)) {
//					try {
//						obj.setIePort4(value);// 进口口岸2
//					} catch (Exception e) {
//						err = err + "[" + value + "]   进口口岸4不合法/";
//						continue;
//					}
//				}  else if ("iePort5".equals(columnField)) {
//					try {
//						obj.setIePort5(value);// 进口口岸2
//					} catch (Exception e) {
//						err = err + "[" + value + "]   进口口岸5不合法/";
//						continue;
//					}
//				} 			
//				else if ("firstTrialer".equals(columnField)) {					
//					try {
//						obj.setFirstTrialer(value);//  初审人
//					} catch (Exception e) {
//						err = err + "[" + value + "]   初审人不合法/";
//						continue;
//					}
//				} else if ("retrialer".equals(columnField)) {					
//					try {
//						obj.setRetrialer(value);// 复审人
//					} catch (Exception e) {
//						err = err + "[" + value + "]   复审人不合法/";
//						continue;
//					}
//				} else if ("approveDate".equals(columnField)) {
//					if(value!=null && !"".equals(value) && !isDate(value)){
//						err = err + "[" + value + "] 审批日期不合法";
//					}
//					try {
//						obj.setApproveDate(value);// 审批日期
//					} catch (Exception e) {
//						err = err + "[" + value + "]   审批日期不合法/";
//						continue;
//					}//TODO
//				}
////				else if ("machineCount".equals(columnField)) {
////					if(value!=null && !"".equals(value) && !isDigit(value)){
////						err = err + "[" + value + "] 设备项数不合法";
////					}
////					try {
////						obj.setMachineCount(value);// 审批日期
////					} catch (Exception e) {
////						err = err + "[" + value + "] 设备项数不合法/";
////						continue;
////					}//TODO
////				}
//				else if ("memo".equals(columnField)) {
//					try {
//						obj.setMemo(value);// 表头备注
//					} catch (Exception e) {
//						err = err + "[" + value + "]   表头备注不合法/";
//						continue;
//					}
//				} else if ("isSelected".equals(columnField)) {
//					try {
//						obj.setIsSelected(value);// 是否被选中
//					} catch (Exception e) {
//						err = err + "[" + value + "] 是否被选中不合法/";
//						continue;
//					}
//				}else if ("type".equals(columnField)) {
//					try {
//						obj.setType(value);// 类型
//					} catch (Exception e) {
//						err = err + "[" + value + "] 类型不合法/";
//						continue;
//					}
//				}
//				else 
				if ("seqNum".equals(columnField)) {
					try {						
						if(value!=null && !"".equals(value)){
							if(isInteger(value)){
							Integer num = Integer.valueOf(value);
							if(fixtureContractAction.isExistFixtureContractItems(new Request(CommonVars
									.getCurrUser()), num,fixtureContract.getId())){
								err = err + "[" + value + "] 商品序号已经存在/";	
							}
							}else{
								err = err + "[" + value + "] 商品序号应为整数/";	
							}
						}else{
							err = err + "[" + value + "] 商品序号不能为空/";	
						}
						obj.setSeqNum(value);// 商品序号
						
					} catch (Exception e) {
						err = err + "[" + value + "] 商品序号不合法/";
						continue;
					}
				}else if ("name".equals(columnField)) {
					try {
						obj.setName(value);// 商品名称
					} catch (Exception e) {
						err = err + "[" + value + "] 商品名称不合法/";
						continue;
					}
				}
				else if ("secondProtocol".equals(columnField)) {
					try {
						obj.setSecondProtocol(value);// 分协议
					} catch (Exception e) {
						err = err + "[" + value + "] 分协议不合法/";
						continue;
					}
				}
				else if ("importDate".equals(columnField)) {	
					if(value!=null && !"".equals(value) && !isDate(value)){
						err = err + "[" + value + "] 进口日期不合法";
					}
					try {
						obj.setImportDate(value);// 进口日期
					} catch (Exception e) {
						err = err + "[" + value + "] 进口日期不合法/";
						continue;
					}
				}
				else if ("spec".equals(columnField)) {
					try {
						obj.setSpec(value);// 规格型号
					} catch (Exception e) {
						err = err + "[" + value + "] 规格型号不合法/";
						continue;
					}
				}else if ("unitName".equals(columnField)) {
					try {
						obj.setUnitName(value);// 计量单位
					} catch (Exception e) {
						err = err + "[" + value + "] 计量单位不合法/";
						continue;
					}
				}else if ("declarePrice".equals(columnField)) {
					if (value !=null && !"".equals(value)&&!isDigit(value)) {
						err = err + "[" + value + "] 单价应为数字型/";
					}
					try {
						obj.setDeclarePrice(value);// 单价
					} catch (Exception e) {
						err = err + "[" + value + "] 单价不合法/";
						continue;
					}
				}else if ("amount".equals(columnField)) {
					if (value !=null && !"".equals(value)&&!isDigit(value)) {
						err = err + "[" + value + "] 数量应为数字型/";
					}
					try {
						obj.setAmount(value);// 数量 
					} catch (Exception e) {
						err = err + "[" + value + "] 数量 不合法/";
						continue;
					}
				}
//				else if ("totalPrice".equals(columnField)) {
//					if (value !=null && !"".equals(value)&&!isDigit(value)) {
//						err = err + "[" + value + "] 总金额应为数字型/";
//					}
//					try {
//						obj.setTotalPrice(value);// 总金额
//					} catch (Exception e) {
//						err = err + "[" + value + "] 总金额不合法/";
//						continue;
//					}
//				}
				else if ("complexCode".equals(columnField)) {
					if(value==null || "".equals(value)){
						err = err + "[" + value + "] 商品编码不能为空/";
					}else{
						Complex complex = fixtureContractAction
						.findComplexByCode(new Request(CommonVars
								.getCurrUser()),value.trim());
						if(complex==null){
							err = err + "[" + value + "] 商品编码在自用商品库中不存在/";
						}
					}					
					try {
						obj.setComplexCode(value);// 商品编码
					} catch (Exception e) {
						err = err + "[" + value + "] 商品编码不合法/";
						continue;
					}
				}else if ("firstUnitName".equals(columnField)) {
					try {
						obj.setFirstUnitName(value);//  法定单位
					} catch (Exception e) {
						err = err + "[" + value + "]  法定单位不合法/";
						continue;
					}
				}else if ("countryName".equals(columnField)) {
					try {
						obj.setCountryName(value);//  原产地
					} catch (Exception e) {
						err = err + "[" + value + "]  原产地不合法/";
						continue;
					}
				}else if ("levyMode".equals(columnField)) {
					try {
						obj.setLevyMode(value);//  征免方式
					} catch (Exception e) {
						err = err + "[" + value + "]  征免方式不合法/";
						continue;
					}
				}else if ("equipmentType".equals(columnField)) {
					if(value==null || "".equals(value)){
						err = err + "[" + value + "]  设备类型不能为空/";
					}
					if(value!=null && !"".equals(value)){
						if((!"".equals(value) && "0".equals(value)) || (!"".equals(value) && "不作价设备".equals(value))){
							value="0";
						}else
						if((!"".equals(value) && "1".equals(value)) || (!"".equals(value) && "借用设备".equals(value))){
							value="1";
						}else{
							err = err + "[" + value + "]  设备类型不合法/";
						}
					}
					try {
						obj.setEquipmentType(value);//  设备类型
					} catch (Exception e) {
						err = err + "[" + value + "]  设备类型不合法/";
						continue;
					}
				}else if ("detailedSpec".equals(columnField)) {					
					try {
						obj.setDetailedSpec(value);// 详细型号规格
					} catch (Exception e) {
						err = err + "[" + value + "]  详细型号规格不合法/";
						continue;
					}
				}else if ("itemMemo".equals(columnField)) {					
					try {
						obj.setItemMemo(value);// 表体备注
					} catch (Exception e) {
						err = err + "[" + value + "]  表体备注不合法/";
						continue;
					}
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
	 * 判断字符串是否是日期字符串
	 * @param str
	 * @return
	 */
	private boolean isDate(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		if(str.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
			return true;
		}
		return false;
	}
	
	private boolean isInteger(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		if(str.matches("\\d*")){
			return true;
		}
		return false;
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

	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}

	private String changeStr(String s) { // 繁转简
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

	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			newStrs[i] = CommonClientBig5GBConverter. getInstance().big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

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
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List ls = new ArrayList();
					ls = tableModel.getList();
					for (int i = 0; i < ls.size(); i++) {
						TempImportEquipmentContract obj = (TempImportEquipmentContract) ls
								.get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(
									DgImportContract.this,
									"请修正错误信息，再保存！", "提示!", 2);
							return;
						}
					}
					new SaveFileDataRunnable(ls,fixtureContract).execute();
					
				}
			});
		}
		return btnSaveData;
	}

	/**
	 * 保存文本导入
	 * 
	 * @author ower
	 * 
	 */
	class SaveFileDataRunnable extends SwingWorker{
		private List ls = null;
		private FixtureContract fixtureContract = null; // 合同对象
		

		public SaveFileDataRunnable(List list,FixtureContract fixtureContract) {
			this.ls = list;
			this.fixtureContract=fixtureContract;
		}
		
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgImportContract.this);
				CommonProgress.setMessage("系统正在保存文件资料，请稍后...");				
				fixtureContractAction.importDataFromFile(new Request(CommonVars
						.getCurrUser()), ls, false,fixtureContract);
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(
						DgImportContract.this,
						"保存完毕！", "提示!", 2);
				DgImportContract.this.dispose();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			} finally {
				String parentId = fixtureContract.getId();
				list = fixtureContractAction
						.findContractItemByParentId(
								new Request(CommonVars
										.getCurrUser()),
								parentId);
				initTable(list);
				return null;
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
			btnExit.setText("退    出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgImportContract.this.dispose();
				}
			});
		}
		return btnExit;
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
			jPanel.add(getTpnImpExpType(), BorderLayout.CENTER);
		}
		return jPanel;
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
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbJF() {
		if (cbJF == null) {
			cbJF = new JCheckBox();
			cbJF.setText("繁转简");
		}
		return cbJF;
	}

	/**
	 * This method initializes jCheckBox1
	 * 
	 * @return javax.swing.JCheckBox
	 */
//	private JCheckBox getCbIsOverwrite() {
//		if (cbIsOverwrite == null) {
//			cbIsOverwrite = new JCheckBox();
//			cbIsOverwrite.setText("资料存在覆盖导入");
//		}
//		return cbIsOverwrite;
//	}

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
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgInputColumnSet dg = new DgInputColumnSet();
					dg
							.setTableFlag(InputTableColumnSet.EQUIPMENT_CONTRACT);
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
	 * This method initializes tpnImpExpType
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTpnImpExpType() {
		if (tpnImpExpType == null) {
			tpnImpExpType = new JTabbedPane();
			tpnImpExpType.addTab("设备协议", null, getJScrollPane(), null);
			tpnImpExpType
					.addChangeListener(new javax.swing.event.ChangeListener() {
						public void stateChanged(javax.swing.event.ChangeEvent e) {

						}
					});
		}
		return tpnImpExpType;
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


}
