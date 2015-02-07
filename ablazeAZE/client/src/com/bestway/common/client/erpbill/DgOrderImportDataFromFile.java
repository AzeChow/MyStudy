/*
 * Created on 2004-7-17
 *
 * //
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bestway.common.client.erpbill;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.DgInputColumnSet;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.client.common.InputTableColumnSetUtils;
import com.bestway.bcus.custombase.entity.parametercode.Curr;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.system.entity.InputTableColumnSet;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.common.erpbill.action.OrderCommonAction;
import com.bestway.common.erpbill.entity.Customparames;
import com.bestway.common.erpbill.entity.OrderDate;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.common.materialbase.entity.ScmCoc;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;
import java.awt.Dimension;

/**
 * 2008-08-26
 * 
 * @author lxr
 * 
 */
public class DgOrderImportDataFromFile extends JDialogBase {

	private JPanel jContentPane = null;
	private OrderCommonAction orderCommonAction = null;
	private JTableListModel tableModel = null;
	private JToolBar jToolBar = null;

	private JButton btnOpen = null;
	private JButton btnSave = null;
	private JButton btnColumn = null;
	private JButton btnExit = null;

	private JCheckBox cbIsOverwrite = null;
	private JCheckBox cbIsTitle = null;

	private JPanel jPanel = null;

	private JCheckBox cbJF = null;
	/**
	 * 存放繁转简Hashtable
	 */
	private Hashtable gbHash = null;
	private File txtFile = null;
	private JScrollPane jScrollPane = null;
	private JTable tbOrder = null;
	
	private int decimalSize = 5;

	/**
	 * This method initializes
	 * 
	 */
	public DgOrderImportDataFromFile() {
		super();
		orderCommonAction = (OrderCommonAction) CommonVars
				.getApplicationContext().getBean("orderCommonAction");
		initialize();
		//wss:2010.05.17为了  限定导入的数据    小数位
		Customparames customparames = orderCommonAction.findCustomparames(
						new Request(CommonVars.getCurrUser()));
		if(customparames != null){
			decimalSize = customparames.getDecimalSize() == null ? 5:customparames.getDecimalSize();
		}
		
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.CUSTOMORDER_INPUT, this
						.getDefaultBomTableColumnList());
		initTable(new ArrayList());
	}

	/**
	 * 定义Table栏位
	 * 
	 * @return
	 */
	private List getDefaultBomTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("订单类型", "orderType", 80));
		list.add(new JTableListColumn("订单号码", "billCode", 100));
		list.add(new JTableListColumn("订单日期", "orderDate", 80));
		list.add(new JTableListColumn("客户/供应商(名称)", "scmCoc", 100));
		list.add(new JTableListColumn("交货日期", "salesDate", 80));//wss:2010.05.09新增
		list.add(new JTableListColumn("料号", "ptNo", 100));
		list.add(new JTableListColumn("数量", "bgAmount", 80));
		list.add(new JTableListColumn("单位(名称)", "bgUnit", 60));
		list.add(new JTableListColumn("单价", "unitPrice", 80));
		list.add(new JTableListColumn("币别(名称)", "curr", 60));
		list.add(new JTableListColumn("总价", "totalPrice", 80));
		return list;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("数据导入");
		this.setContentPane(getJContentPane());
		this.setSize(789, 446);
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
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 打开文件
	 * 
	 * @author ower
	 * 
	 */
	class ImportFileDataRunnable extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress
						.showProgressDialog(DgOrderImportDataFromFile.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {

			}
			return list;
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
			initTable(list);

		}
	}

	/**
	 * 取得文件数据
	 * 
	 * @param fileRow
	 * @param dataIndex
	 * @return
	 */
	public String getFileColumnValue(String[] fileRow, int dataIndex) {
		if (dataIndex > fileRow.length - 1) {
			return null;
		}
		return fileRow[dataIndex];
	}

	/**
	 * 设置繁转简
	 * 
	 * @param source
	 * @return
	 */
	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];

		for (int i = 0, n = source.length; i < n; i++) {

			newStrs[i] = CommonClientBig5GBConverter.getInstance().big5ConvertToGB(
					source[i]);

		}
		return newStrs;
	}

	/**
	 * 开始读取文件
	 * 
	 * @param file
	 * @return
	 */
	private List parseTxtFile() {
		boolean ischange = false;
		if (getCbJF().isSelected()) {
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
			if (cbIsTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			//
			// 导入txt
			//
			if (cbIsTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}

		ArrayList list = new ArrayList();
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.CUSTOMORDER_INPUT);
		int zcount = lsIndex.size(); // 字段数目
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

			OrderDate obj = new OrderDate();
			for (int j = 0; j < zcount; j++) {

				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				if ("orderType".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]订单类型不为空/";
					} else {
						if (!"销售订单".equals(value) && !"采购订单".equals(value)) {
							err = err + "[" + value + "]订单类型[销售订单或采购订单]/";
						}
					}
					try {
						obj.setOrderType(value);// 订单类型
					} catch (Exception e) {
						err = err + "[" + value + "]订单类型不合法/";
						continue;
					}
				} else if ("billCode".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "] 订单编号不能为空/";
					}
					obj.setBillCode(value.trim());// 订单编号

				} else if ("orderDate".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]订单日期不能为空/";
					} else {
						try {
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							java.util.Date outDate = dateFormat.parse(value);
							java.sql.Date simpleDate = new java.sql.Date(
									outDate.getTime());
							value = simpleDate.toString();
						} catch (Exception f) {
							err = err + "[" + value + "]订单日期格式请用:yyyy-mm-dd/";
						}
					}
					try {
						obj.setOrderDate(value);// 订单日期
					} catch (Exception e) {
						err = err + "[" + value + "]订单日期不合法/";
						continue;
					}
				} else if ("scmCoc".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]客户名称不能为空/";
					} else {
						ScmCoc sc = orderCommonAction.findscmcoc(new Request(
								CommonVars.getCurrUser()), value);
						if (sc == null) {
							err = err + "[" + value + "]客户在系统中不存在/";
						}
					}
					try {
						obj.setScmCoc(value);// 客户
					} catch (Exception e) {
						err = err + "[" + value + "]客户不合法/";
						continue;
					}
				//wss:2010.05.09新加  交货日期
				} else if ("salesDate".equals(columnField)) {
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]交货日期不能为空/";
					} else {
						try {
							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"yyyy-MM-dd");
							java.util.Date outDate = dateFormat.parse(value);
							java.sql.Date simpleDate = new java.sql.Date(
									outDate.getTime());
							value = simpleDate.toString();
						} catch (Exception f) {
							err = err + "[" + value + "]交货日期格式请用:yyyy-mm-dd/";
						}
					}
					try {
						obj.setSalesDate(value);// 订单日期
					} catch (Exception e) {
						err = err + "[" + value + "]交货日期不合法/";
						continue;
					}
				} else if ("ptNo".equals(columnField)) {
					if ((value == null) || ("".equals(value))) {
						err = err + "[" + value + "]货号不能为空/";
					} else {
						Materiel ml = this.orderCommonAction.findMateriel(
								new Request(CommonVars.getCurrUser()), value);
						if (ml == null) {
							err = err + "[" + value + "]货号在物料资料中不存在/";
						}
					}
					try {
						obj.setPtNo(value);// 料号
					} catch (Exception e) {
						err = err + "[" + value + "]货号不合法/";
						continue;
					}
				} else if ("bgAmount".equals(columnField)) {// 数量
					if (!isDigit(value)) {
						err = err + "[" + value + "]数量应为数字型/";
					}else{
						//wss2010.05.10新加四舍五入
						value = CommonUtils.getDoubleByDigit(Double.valueOf(value), decimalSize) + "";
					}
					
					try {
						obj.setBgAmount(value);// 数量
					} catch (Exception e) {
						err = err + "[" + value + "]数量不合法/";
						continue;
					}
				} else if ("bgUnit".equals(columnField)) {
					if ((value == null) || ("".equals(value))) {
						err = err + "[" + value + "]单位不能为空/";
					} else {
						CalUnit ml = this.orderCommonAction.findCalUnit(
								new Request(CommonVars.getCurrUser()), value);
						if (ml == null) {
							err = err + "[" + value + "]单位在系统中不存在/";
						}
					}
					try {
						obj.setBgUnit(value);// 单位
					} catch (Exception e) {
						err = err + "[" + value + "]单位不合法/";
						continue;
					}
				} else if ("unitPrice".equals(columnField)) {
					if (!isDigit(value)) {
						err = err + "[" + value + "]单价应为数字型/";
					}else{
						//wss2010.05.10新加四舍五入
						value = CommonUtils.getDoubleByDigit(Double.valueOf(value), decimalSize) + "";
					}
					try {
						obj.setUnitPrice(value);// 单价
					} catch (Exception e) {
						err = err + "[" + value + "]单价不合法/";
						continue;
					}

				} else if ("curr".equals(columnField)) {
					if (value != null && !"".equals(value)) {
						Curr curr = orderCommonAction.findCurr(new Request(
								CommonVars.getCurrUser()), value);
						if (curr == null) {
							err = err + "[" + value + "]币别在系统中不存在/";
						}
					}
					try {
						obj.setCurr(value);// 币别
					} catch (Exception e) {
						err = err + "[" + value + "]币别不合法/";
						continue;
					}
				} else if ("totalPrice".equals(columnField)) {
					if (value == null || "0".equals(String.valueOf(value))) {
						if (!isDigit(obj.getBgAmount())
								|| !isDigit(obj.getUnitPrice())) {
							obj.setTotalPrice("0");
						} else {
							
							//wss2010.05.10新加四舍五入
							obj.setTotalPrice(String.valueOf(CommonUtils.getDoubleByDigit(Double.valueOf(obj
									.getBgAmount())
									* Double.valueOf(obj.getUnitPrice()),decimalSize)));//wss添加四舍五入
						}// 总价
					} else {
						if (!isDigit(value)) {
							err = err + "[" + value + "]总价应为数字型/";
						}else{
							//wss2010.05.10新加四舍五入
							value = CommonUtils.getDoubleByDigit(Double.valueOf(value), decimalSize) + "";
						}
						try {
							obj.setTotalPrice(value);// 总价
						} catch (Exception e) {
							err = err + "[" + value + "]总价不合法/";
							continue;
						}
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
	
//	/**
//	 * 截取设定的小数位
//	 * @author wss
//	 * 这个方法实在是太烂了!
//	 */
//	private String formatValue(String value){
//		int i = value.indexOf(".");
//		if(i != -1){
//			String str = value.substring(i);
//			int j = str.length();
//			if(j >=  decimalSize + 2){
//				char c;
//				if(str.charAt(decimalSize + 1) < 53){//四舍五入
//					c = str.charAt(decimalSize);
//				}else{
//					c = (char)(str.charAt(decimalSize) + 1);
//				}
//				return value.substring(0,i) + str.substring(0,decimalSize) + c;
//			}
//		}
//		return value;
//	}

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
	 * 初始化Table
	 * 
	 * @param list
	 */
	private void initTable(List list) {
		tableModel = new JTableListModel(tbOrder, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.CUSTOMORDER_INPUT);
					}
				});
		tbOrder.getColumnModel().getColumn(tbOrder.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		tableModel.packRows();
	}

	/**
	 * 获得文件扩展名
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

	public void setTableModel(JTableListModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTableListModel getTableModel() {
		return tableModel;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setBounds(new Rectangle(5, 214, 569, 34));
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
			jToolBar.add(getCbIsOverwrite());
			jToolBar.add(getCbIsTitle());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (btnOpen == null) {
			btnOpen = new JButton();
			btnOpen.setText("1.打开文件");
			btnOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgOrderImportDataFromFile.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					new ImportFileDataRunnable().execute();
				}
			});
		}
		return btnOpen;
	}

	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("2.保存数据");
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileData().execute();
				}
			});
		}
		return btnSave;
	}

	/**
	 * 开始导入文件
	 * 
	 * @author ower
	 * 
	 */
	class ImportFileData extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress
						.showProgressDialog(DgOrderImportDataFromFile.this);
				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				List ls = null;
				ls = tableModel.getList();
				for (int i = 0; i < ls.size(); i++) {
					OrderDate obj = (OrderDate) ls.get(i);
					if (obj.getErrinfo() != null
							&& !obj.getErrinfo().equals("")) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(
								DgOrderImportDataFromFile.this, "有错误信息，不可保存！",
								"提示!", 2);
						return null;
					}
				}
				orderCommonAction.importDataFromTxtFile(new Request(CommonVars
						.getCurrUser()), ls, cbIsOverwrite.isSelected());
				CommonProgress.closeProgressDialog();
				long totalTime = System.currentTimeMillis() - beginTime;
				JOptionPane.showMessageDialog(DgOrderImportDataFromFile.this,
						"导入数据成功！导入数据记录 " + ls.size() + " 条,共用时 " + totalTime
								+ " 毫秒", "提示", JOptionPane.INFORMATION_MESSAGE);
				DgOrderImportDataFromFile.this.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgOrderImportDataFromFile.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}

			return null;
		}
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
					dg.setTableFlag(InputTableColumnSet.CUSTOMORDER_INPUT);
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
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("退出");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOrderImportDataFromFile.this.dispose();
				}
			});
		}
		return btnExit;
	}

	/**
	 * This method initializes cbIsOverwrite
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOverwrite() {
		if (cbIsOverwrite == null) {
			cbIsOverwrite = new JCheckBox();
			cbIsOverwrite.setVisible(false);
			cbIsOverwrite.setText("资料存在覆盖导入");
		}
		return cbIsOverwrite;
	}

	/**
	 * This method initializes cbIsTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsTitle() {
		if (cbIsTitle == null) {
			cbIsTitle = new JCheckBox();
			cbIsTitle.setText("第一行为标题行");
		}
		return cbIsTitle;
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
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
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
		}
		return cbJF;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbOrder());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tbOrder	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTbOrder() {
		if (tbOrder == null) {
			tbOrder = new JTable();
		}
		return tbOrder;
	}

} // @jve:decl-index=0:visual-constraint="10,10"

