package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.action.CustomBaseAction;
import com.bestway.bcus.custombase.entity.hscode.Complex;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.custombase.entity.parametercode.Unit;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.FptBusinessType;
import com.bestway.common.constant.FptInOutFlag;
import com.bestway.common.fpt.action.FptManageAction;
import com.bestway.common.fpt.entity.FptBillHead;
import com.bestway.common.fpt.entity.FptBillItem;
import com.bestway.common.fpt.entity.TempFptBillHead;
import com.bestway.common.fpt.entity.TempFptBillItem;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptBillImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton btnOpenFile = null;

	private JButton btnSaveData = null;

	private JButton btnClose = null;

	private JSplitPane jSplitPane = null;

	private JScrollPane jScrollPane = null;

	private JTable tbFptBillHead = null;

	private JScrollPane jScrollPane1 = null;

	private JTable tbFptBillItem = null;

	private JCheckBox cbIsOverwrite = null;

	private JCheckBox cbJF = null;

	private File txtFile = null;

	private Hashtable gbHash = null;

	private JTableListModel tableModelHead;

	private JTableListModel tableModelItem;

	private FptBillHead fptBillHead = null; // @jve:decl-index=0:

	private CustomBaseAction customBaseAction = null;

	private FptManageAction fptManageAction = null;

	public FptBillHead getFptBillHead() {
		return fptBillHead;
	}

	public void setFptBillHead(FptBillHead fptBillHead) {
		this.fptBillHead = fptBillHead;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgFptBillImport() {
		super();
		initialize();
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		fptManageAction = (FptManageAction) CommonVars
				.getApplicationContext().getBean("fptManageAction");
	}
	
	public void setVisible(boolean b){
		if(b){
			this.initTableHead(new ArrayList());
			this.initTableItem(new ArrayList());
		}
		super.setVisible(b);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(643, 482));
		this.setContentPane(getJContentPane());
		this.setTitle("导入对方资料");

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
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
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
			jToolBar.add(getBtnClose());
			jToolBar.add(getCbIsOverwrite());
			jToolBar.add(getCbJF());
		}
		return jToolBar;
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
			newStrs[i] = changeStr(source[i]);
		}
		return newStrs;
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

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1.打开文件");
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgFptBillImport.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImportFileDataRunnable().start();
				}
			});
		}
		return btnOpenFile;
	}

	class ImportFileDataRunnable extends Thread {
		public void run() {
			List lsHead = new ArrayList();
			List lsItem = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgFptBillImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				lsHead = parseFileHead();
				lsItem = parseFileItem();
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {

			} finally {
				CommonProgress.closeProgressDialog();
				initTableHead(lsHead);
				initTableItem(lsItem);
			}

		}
	}

	/**
	 * 读取Excel文件中的表头内容
	 * 
	 * @return
	 */
	private List parseFileHead() {
		this.fptBillHead = fptManageAction
				.findFptBillHeadById(new Request(CommonVars.getCurrUser()),
						this.fptBillHead.getId());
		boolean ischange = true;
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			lines = FileReading.readExcel(txtFile, 2, null);
		} else {
			lines = FileReading.readTxt(txtFile, 2, null);
		}
		List list = new ArrayList();
		int zcount = 9;
		String inOutFlag = fptBillHead.getBillSort();
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
			TempFptBillHead obj = new TempFptBillHead();
			String err = "";
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				if (j == 0) {// 转入转出企业编码
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转入企业编码不能为空/";
						} else {
							fptBillHead.setReceiveTradeCod(value.trim());
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转出企业编码不能为空/";
						} else {
							fptBillHead.setIssueTradeCod(value.trim());
						}
					}
				} else if (j == 1) {// 转入转出企业名称
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转入企业名称不能为空/";
						} else {
							fptBillHead.setReceiveTradeName(value.trim());
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转出企业名称不能为空/";
						} else {
							fptBillHead.setIssueTradeName(value.trim());
						}
					}
				} else if (j == 2) {// 转入转出企业组织机构编码
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转入企业组织机构编码不能为空/";
						} else {
							fptBillHead.setReceiveAgentCode(value.trim());
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转出企业组织机构编码不能为空/";
						} else {
							fptBillHead.setIssueAgentCode(value.trim());
						}
					}
				} else if (j == 3) {// 转入转出企业组织机构名称
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转入企业组织机构名称不能为空/";
						} else {
							fptBillHead.setReceiveAgentName(value.trim());
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转出企业组织机构名称不能为空/";
						} else {
							fptBillHead.setIssueAgentName(value.trim());
						}
					}
				} else if (j == 4) {// 转入转出企业内部编号
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转入企业内部编号不能为空/";
						} else {
							fptBillHead.setReceiveCopBillNo(value.trim());
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							err = err + "[" + value + "]   转出企业内部编号不能为空/";
						} else {
							fptBillHead.setIssueCopBillNo(value.trim());
						}
					}
				} else if (j == 5) {// 转入转出企业申报日期
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							// err = err + "[" + value + "] 转入企业申报日期不能为空/";
						} else {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								fptBillHead.setReceiveIsDeclaDate(dateFormat
										.parse(value.trim()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							// err = err + "[" + value + "] 转出企业申报日期不能为空/";
						} else {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								fptBillHead.setIssueIsDeclaDate(dateFormat
										.parse(value.trim()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} else if (j == 6) {// 转入转出企业申报人
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							// err = err + "[" + value + "] 转入企业申报人不能为空/";
						} else {
							fptBillHead.setReceiveIsDeclaEm(value.trim());
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							// err = err + "[" + value + "] 转出企业申报人不能为空/";
						} else {
							fptBillHead.setIssueIsDeclaEm(value.trim());
						}
					}
				} else if (j == 7) {// 转入转出企业收货送货日期
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						if (value == null || "".equals(value.trim())) {
							// err = err + "[" + value + "] 转入企业收货日期不能为空/";
						} else {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								fptBillHead.setReceiveDate(dateFormat
										.parse(value.trim()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						if (value == null || "".equals(value.trim())) {
							// err = err + "[" + value + "] 转出企业送货日期不能为空/";
						} else {
							DateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							try {
								fptBillHead.setIssueDate(dateFormat.parse(value
										.trim()));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} else if (j == 8) {// 备注
					if (FptInOutFlag.OUT.equals(inOutFlag)) {
						fptBillHead.setReceiveNote(value);
					} else {
						fptBillHead.setIssueNote(value);
					}
				}
			}
			// item.setFptBillHead(head);
			fptBillHead.setCompany(CommonVars.getCurrUser().getCompany());
			obj.setT(fptBillHead);
			obj.setErrinfo(err);
			list.add(obj);
		}
		return list;
	}

	/**
	 * 读取Excel文件中的表体内容
	 * 
	 * @return
	 */
	private List parseFileItem() {
		// 商品编码
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		// 单位
		List unitList = CustomBaseList.getInstance().getUnits();

		Hashtable<String, Complex> hs = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsUnit = new Hashtable<String, Unit>();

		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hs.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsUnit.put(u.getName(), u);
		}
		boolean ischange = true;
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		List<Integer> lsSeqNum = new ArrayList<Integer>();
		String suffix = getSuffix(txtFile);
		List<List> lines = new ArrayList<List>();
		if (suffix.equals("xls")) {
			lines = FileReading.readExcel(txtFile, 4, null);
		} else {
			lines = FileReading.readTxt(txtFile, 4, null);
		}
		List list = new ArrayList();
		int zcount = -1;
		if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
			zcount = 16;
		} else if (FptInOutFlag.IN.equals(fptBillHead.getBillSort())) {
			zcount = 14;
		}
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
			TempFptBillItem obj = new TempFptBillItem();
			FptBillItem item = new FptBillItem();
			if (FptInOutFlag.IN.equals(fptBillHead.getBillSort())) {
				item.setBillSort(FptInOutFlag.OUT);
			} else {
				item.setBillSort(FptInOutFlag.IN);
			}
			item.setFptBillHead(fptBillHead);
//			item.setModifyMake(ModifyMarkState.UNCHANGE);
			String err = "";
			if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
				for (int j = 0; j < zcount; j++) {
					String value = getFileColumnValue(strs, j);
					if (j == 0) {// 序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   序号不合法/";
							continue;
						}
						if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
							err = err + "[" + value + "]   序号重复/";
						} else {
							lsSeqNum.add(Double.valueOf(value).intValue());
						}
						item.setListNo(Double.valueOf(value).intValue());
					} else if (j == 1) {// 发货序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   发货序号不合法/";
							continue;
						}
						item.setOutNo(Double.valueOf(value).intValue());
					} else if (j == 2) {// 料号
						item.setCopGNo(value);
					} else if (j == 3) {// 手册/账册号
						item.setInEmsNo(value);
					} else if (j == 4) {// 归并前物料名称
						item.setCopGName(value);
					} else if (j == 5) {// 归并前型号规格
						item.setCopGModel(value);
					} else if (j == 6) {// 申请表序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   申请表序号不合法/";
							continue;
						}
						item.setAppGNo(Double.valueOf(value).intValue());
					} else if (j == 7) {// 项号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   项号不合法/";
							continue;
						}
						item.setTrGno(Double.valueOf(value).intValue());
					} else if (j == 8) {// 商品编码
						if (hs.get(value) == null) {
							Complex cobj = customBaseAction
									.makeComplexFromCustomsComplex(new Request(
											CommonVars.getCurrUser()), value);
							if (cobj != null) {
								hs.put(cobj.getCode(), cobj);
								item.setComplex(cobj);// 商品编码
							} else {
								err = err + "[" + value + "]   不正确商品编码/";
							}
						} else {
							Complex c = (Complex) hs.get(value);
							item.setComplex(c);
						}
					} else if (j == 9) {// 商品名称
						item.setCommName(value);
					} else if (j == 10) {// 商品规格
						item.setCommSpec(value);
					} else if (j == 11) {// 交易单位
						if (value == null || "".equals(value)) {
							err = err + "[" + value + "]   交易单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								item.setTradeUnit(u);
							} else {
								err = err + "[" + value + "]   交易单位不存在/";
							}
						}
					} else if (j == 12) {// 交易数量
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   交易数量不合法/";
								continue;
							}
							item.setTradeQty(Double.valueOf(value));
						}
					} else if (j == 13) {// 计量单位
						if (value == null || "".equals(value)) {
							// err = err + "[" + value + "] 计量单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								item.setUnit(u);
							} else {
								err = err + "[" + value + "]   计量单位不存在/";
							}
						}
					} else if (j == 14) {// 计量数量
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   计量交易数量不合法/";
								continue;
							}
							item.setQty(Double.valueOf(value));
						}
					} else if (j == 15) {// 备注
						item.setNote(value);
					}
				}
				item.setCompany(CommonVars.getCurrUser().getCompany());
				obj.setT(item);
				obj.setErrinfo(err);
				list.add(obj);
			} else if (FptInOutFlag.IN.equals(fptBillHead.getBillSort())) {
				for (int j = 0; j < zcount; j++) {
					String value = getFileColumnValue(strs, j);
					if (j == 0) {// 序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   序号不合法/";
							continue;
						}
						if (lsSeqNum.contains(Double.valueOf(value).intValue())) {
							err = err + "[" + value + "]   序号重复/";
						} else {
							lsSeqNum.add(Double.valueOf(value).intValue());
						}
						item.setListNo(Double.valueOf(value).intValue());
					} else if (j == 1) {// 料号
						item.setCopGNo(value);
					} else if (j == 2) {// 归并前物料名称
						item.setCopGName(value);
					} else if (j == 3) {// 归并前型号规格
						item.setCopGModel(value);
					} else if (j == 4) {// 申请表序号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   申请表序号不合法/";
							continue;
						}
						item.setAppGNo(Double.valueOf(value).intValue());
					} else if (j == 5) {// 项号
						try {
							Double.valueOf(value);
						} catch (Exception e) {
							err = err + "[" + value + "]   项号不合法/";
							continue;
						}
						item.setTrGno(Double.valueOf(value).intValue());
					} else if (j == 6) {// 商品编码
						if (hs.get(value) == null) {
							Complex cobj = customBaseAction
									.makeComplexFromCustomsComplex(new Request(
											CommonVars.getCurrUser()), value);
							if (cobj != null) {
								hs.put(cobj.getCode(), cobj);
								item.setComplex(cobj);// 商品编码
							} else {
								err = err + "[" + value + "]   不正确商品编码/";
							}
						} else {
							Complex c = (Complex) hs.get(value);
							item.setComplex(c);
						}
					} else if (j == 7) {// 商品名称
						item.setCommName(value);
					} else if (j == 8) {// 商品规格
						item.setCommSpec(value);
					} else if (j == 9) {// 交易单位
						if (value == null || "".equals(value)) {
							err = err + "[" + value + "]   交易单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								item.setTradeUnit(u);
							} else {
								err = err + "[" + value + "]   交易单位不存在/";
							}
						}
					} else if (j == 10) {// 交易数量
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   交易数量不合法/";
								continue;
							}
							item.setTradeQty(Double.valueOf(value));
						}
					} else if (j == 11) {// 计量单位
						if (value == null || "".equals(value)) {
							// err = err + "[" + value + "] 计量单位不为空/";
						} else {
							if (hsUnit.get(value) != null) {
								Unit u = (Unit) hsUnit.get(value);
								item.setUnit(u);
							} else {
								err = err + "[" + value + "]   计量单位不存在/";
							}
						}
					} else if (j == 12) {// 计量数量
						if (value != null && !"".equals(value.trim())) {
							try {
								Double.valueOf(value);
							} catch (Exception e) {
								err = err + "[" + value + "]   计量交易数量不合法/";
								continue;
							}
							item.setQty(Double.valueOf(value));
						}
					} else if (j == 13) {// 备注
						item.setNote(value);
					}
				}
				item.setCompany(CommonVars.getCurrUser().getCompany());
				obj.setT(item);
				obj.setErrinfo(err);
				list.add(obj);
			}
		}
		return list;
	}

	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		// System.out.println("--"+dataIndex+"="+fileRow[dataIndex]);
		return fileRow[dataIndex];
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSaveData() {
		if (btnSaveData == null) {
			btnSaveData = new JButton();
			btnSaveData.setText("2.保存数据");
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tableModelHead.getList().size() == 0) {
						JOptionPane.showMessageDialog(DgFptBillImport.this,
								"没有表头资料！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if (tableModelItem.getList().size() == 0) {
						JOptionPane.showMessageDialog(DgFptBillImport.this,
								"没有表体明细资料！", "提示",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List ls = tableModelHead.getList();
					for (int i = 0; i < ls.size(); i++) {
						TempFptBillHead obj = (TempFptBillHead) ls.get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(DgFptBillImport.this,
									"表头资料有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}
					ls = tableModelItem.getList();
					for (int i = 0; i < ls.size(); i++) {
						TempFptBillItem obj = (TempFptBillItem) ls.get(i);
						if (obj.getErrinfo() != null
								&& !obj.getErrinfo().equals("")) {
							JOptionPane.showMessageDialog(DgFptBillImport.this,
									"表体明细资料有错误信息，不可保存！", "提示!", 2);
							return;
						}
					}
					fptManageAction.saveFptBillItemFormOtherSide(
							new Request(CommonVars.getCurrUser()),
							tableModelHead.getList(), tableModelItem.getList(),
							cbJF.isSelected());
					initTableHead(new ArrayList());
					initTableItem(new ArrayList());
				}
			});
		}
		return btnSaveData;
	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton();
			btnClose.setText("关闭");
			btnClose.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
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
			jSplitPane.setDividerLocation(100);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJScrollPane1());
			jSplitPane.setDividerSize(3);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbFptBillHead());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFptBillHead() {
		if (tbFptBillHead == null) {
			tbFptBillHead = new JTable();
		}
		return tbFptBillHead;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbFptBillItem());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTbFptBillItem() {
		if (tbFptBillItem == null) {
			tbFptBillItem = new JTable();
		}
		return tbFptBillItem;
	}

	/**
	 * This method initializes cbIsOverwrite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOverwrite() {
		if (cbIsOverwrite == null) {
			cbIsOverwrite = new JCheckBox();
			cbIsOverwrite
					.setText("\u8d44\u6599\u5b58\u5728\u8986\u76d6\u5bfc\u5165");
		}
		return cbIsOverwrite;
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
	 * 初始收货明细
	 * 
	 */
	private void initTableHead(List list) {
		tableModelHead = new JTableListModel(tbFptBillHead, list,
				new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();

						if (FptBusinessType.FPT_BILL.equals(fptBillHead
								.getSysType())) {
							if (FptInOutFlag.OUT.equals(fptBillHead
									.getBillSort())) {
								list.add(addColumn("收货企业内部编号",
										"t.receiveCopBillNo", 120));
								list.add(addColumn("收货单编号", "t.billNo", 100));
								list.add(addColumn("收货企业编码",
										"t.receiveTradeCod", 100));
								list.add(addColumn("收货企业名称",
										"t.receiveTradeName", 100));
								list.add(addColumn("收货企业组织机构代码",
										"t.receiveAgentCode", 100));
								list.add(addColumn("收货企业组织机构名称",
										"t.receiveAgentName", 100));
								list.add(addColumn("收货企业申报日期",
										"t.receiveIsDeclaDate", 100));
								list.add(addColumn("收货企业申报人",
										"t.receiveIsDeclaEm", 100));
								list
										.add(addColumn("收货日期", "t.receiveDate",
												100));
							} else {
								list.add(addColumn("发货企业内部编号",
										"t.issueCopBillNo", 120));
								list.add(addColumn("发货单编号", "t.billNo", 100));
								list.add(addColumn("发货企业编码", "t.issueTradeCod",
										100));
								list.add(addColumn("发货企业名称",
										"t.issueTradeName", 100));
								list.add(addColumn("发货企业组织机构代码",
										"t.issueAgentCode", 100));
								list.add(addColumn("发货企业组织机构名称",
										"t.issueAgentName", 100));
								list.add(addColumn("发货企业申报日期",
										"t.issueIsDeclaDate", 100));
								list.add(addColumn("发货企业申报人",
										"t.issueIsDeclaEm", 100));
								list.add(addColumn("发货日期", "t.issueDate", 100));
							}
						} else {
							if (FptInOutFlag.OUT.equals(fptBillHead
									.getBillSort())) {
								list.add(addColumn("发退货企业内部编号",
										"t.receiveCopBillNo", 120));
								list.add(addColumn("发退货单编号", "t.billNo", 100));
								list.add(addColumn("发退货企业编码",
										"t.receiveTradeCod", 100));
								list.add(addColumn("发退货企业名称",
										"t.receiveTradeName", 100));
								list.add(addColumn("发退货企业组织机构代码",
										"t.receiveAgentCode", 100));
								list.add(addColumn("发退货企业组织机构名称",
										"t.receiveAgentName", 100));
								list.add(addColumn("发退货企业申报日期",
										"t.receiveIsDeclaDate", 100));
								list.add(addColumn("发退货企业申报人",
										"t.receiveIsDeclaEm", 100));
								list.add(addColumn("发退货日期", "t.receiveDate",
										100));
							} else {
								list.add(addColumn("收退货企业内部编号",
										"t.issueCopBillNo", 120));
								list.add(addColumn("收退货单编号", "t.billNo", 100));
								list.add(addColumn("收退货企业编码",
										"t.issueTradeCod", 100));
								list.add(addColumn("收退货企业名称",
										"t.issueTradeName", 100));
								list.add(addColumn("收退货企业组织机构代码",
										"t.issueAgentCode", 100));
								list.add(addColumn("收退货企业组织机构名称",
										"t.issueAgentName", 100));
								list.add(addColumn("收退货企业申报日期",
										"t.issueIsDeclaDate", 100));
								list.add(addColumn("收退货企业申报人",
										"t.issueIsDeclaEm", 100));
								list
										.add(addColumn("收退货日期", "t.issueDate",
												100));
							}
						}
						list.add(addColumn("错误信息", "errinfo", 100));
						return list;
					}
				});
	}

	/**
	 * 初始收货明细
	 * 
	 */
	private void initTableItem(List list) {
		JTableListModel.dataBind(tbFptBillItem, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						if (FptInOutFlag.OUT.equals(fptBillHead.getBillSort())) {
							list.add(addColumn("1.序号", "t.listNo", 40));
							list.add(addColumn("2.发货序号", "t.outNo", 80));
							list.add(addColumn("3.料号", "t.copGNo", 80));
							list.add(addColumn("4.手册/账册号", "t.inEmsNo", 100));
							list.add(addColumn("5.归并前商品名称", "t.copGName", 100));
							list
									.add(addColumn("6.归并前商品规格", "t.copGModel",
											100));
							list.add(addColumn("7.申报表序号", "t.appGNo", 60));
							list.add(addColumn("8.项号", "t.trGno", 40));
							list.add(addColumn("9.商品编码", "t.complex.code", 80));
							list.add(addColumn("10.商品名称", "t.commName", 100));
							list.add(addColumn("11.规格型号", "t.commSpec", 100));
							list.add(addColumn("12.交易单位", "t.tradeUnit.name",
									60));
							list.add(addColumn("13.交易数量", "t.tradeQty", 80));
							list.add(addColumn("14.申报单位", "t.unit.name", 60));
							list.add(addColumn("15.申报数量", "t.qty", 80));
							list.add(addColumn("16.备注", "t.note", 100));
							list.add(addColumn("错误信息", "errinfo", 100));
						} else {
							list.add(addColumn("1.序号", "t.listNo", 40));
							list.add(addColumn("2.料号", "t.copGNo", 80));
							list.add(addColumn("3.归并前商品名称", "t.copGName", 100));
							list
									.add(addColumn("4.归并前商品规格", "t.copGModel",
											100));
							list.add(addColumn("5.申报表序号", "t.appGNo", 60));
							list.add(addColumn("6.项号", "t.trGno", 40));
							list.add(addColumn("7.商品编码", "t.complex.code", 80));
							list.add(addColumn("8.商品名称", "t.commName", 100));
							list.add(addColumn("9.规格型号", "t.commSpec", 100));
							list.add(addColumn("10.交易单位", "t.tradeUnit.name",
									60));
							list.add(addColumn("11.交易数量", "t.tradeQty", 80));
							list.add(addColumn("12.申报单位", "t.unit.name", 60));
							list.add(addColumn("13.申报数量", "t.qty", 80));
							list.add(addColumn("14.备注", "t.note", 100));
							list.add(addColumn("错误信息", "errinfo", 100));
						}
						return list;
					}
				});
		this.tableModelItem = (JTableListModel) tbFptBillItem.getModel();
		tbFptBillItem
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
} // @jve:decl-index=0:visual-constraint="10,10"
