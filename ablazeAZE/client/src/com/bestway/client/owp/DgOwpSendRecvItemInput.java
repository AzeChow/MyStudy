package com.bestway.client.owp;

import java.awt.BorderLayout;
import java.io.File;
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;

import com.bestway.bcus.client.common.CommonFileFilter;
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
import com.bestway.owp.action.OwpAppAction;
import com.bestway.owp.action.OwpMessageAction;
import com.bestway.owp.entity.OwpAppHead;
import com.bestway.owp.entity.OwpAppRecvItem;
import com.bestway.owp.entity.OwpAppSendItem;
import com.bestway.owp.entity.TempOwpAppRecvItem;
import com.bestway.owp.entity.TempOwpAppSendItem;
import com.bestway.ui.render.TableMultiRowRender;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 外发加工申请表 收/发货导入
 * @author Administrator
 *
 */
public class DgOwpSendRecvItemInput extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JTabbedPane jTabbedPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JScrollPane jScrollPane = null;

	private JTable tbSendItemInput = null; 

	private JScrollPane jScrollPane1 = null;

	private JTable tbRecvItemInput = null; 

	private File txtFile = null;
	

	/**
	 * 用来装  简繁 对照
	 */
	private Hashtable gbHash = null; // @jve:decl-index=0:

	/**
	 * 海关基础资料服务器端接口
	 */
	private CustomBaseAction customBaseAction = null;

	/**
	 * 外发加工远程服务接口
	 */
	private OwpAppAction owpAppAction = null;

	/**
	 * 外发加工申请表 表头
	 */
	private OwpAppHead head = null; // @jve:decl-index=0:

	/**
	 * 外发货物tableModel
	 */
	private JTableListModel tableModelSendItem = null;

	/**
	 * 收回货物tableModel
	 */
	private JTableListModel tableModelRecvItem = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbIsOverwrite = null;

	private JCheckBox cbCheckTitle = null;

	private JButton btnColumn = null;

	/**
	 * This is the default constructor
	 */
	public DgOwpSendRecvItemInput() {
		super();
		
		/**
		 * 初始化海关基础资料服务接口
		 */
		customBaseAction = (CustomBaseAction) CommonVars
				.getApplicationContext().getBean("customBaseAction");
		
		/**
		 * 初始化外发加工服务接口
		 */
		owpAppAction = (OwpAppAction) CommonVars.getApplicationContext().getBean(
				"owpAppAction");
		
		initialize();
		
		/**
		 * 外发货物 栏位
		 */
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.OWP_APP_INPUT_SEND, this
						.getDefaultSendItemTableColumnList());
		
		/**
		 * 收回货物 栏位
		 */
		InputTableColumnSetUtils.putColumn(
				InputTableColumnSet.OWP_APP_INPUT_RECV, this
						.getDefaultRecvItemTableColumnList());


		initTableSend(new ArrayList());
		initTableRecv(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(736, 469);
		this.setContentPane(getJContentPane());
		this.setTitle("外发加工申请表收发货资料导入接口");
	}

	/**
	 * 外发申请表 外发货物 栏位
	 * @return
	 */
	private List getDefaultSendItemTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("手册序号", "owpAppSendItem.trNo", 60,Integer.class));
		list.add(new JTableListColumn("商品编码", "owpAppSendItem.complex.code", 60,Integer.class));
		list.add(new JTableListColumn("商品名称", "owpAppSendItem.hsName", 100));
		list.add(new JTableListColumn("商品规格", "owpAppSendItem.hsSpec", 100));
		list.add(new JTableListColumn("计量单位", "owpAppSendItem.hsUnit.name", 100));
		list.add(new JTableListColumn("申报数量", "owpAppSendItem.qty",100));
		list.add(new JTableListColumn("备注", "owpAppSendItem.note", 100));
		return list;
	}

	/**
	 * 初始化导入的 外发申请表 外发货物 表
	 */
	private void initTableSend(List list) {
		tableModelSendItem = new JTableListModel(tbSendItemInput, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.OWP_APP_INPUT_SEND);
					}
				});
		tbSendItemInput.getColumnModel().getColumn(tbSendItemInput.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		tableModelSendItem.packRows();
	}

	/**
	 * 外发申请表 收回货物 栏位
	 * @return
	 */
	private List getDefaultRecvItemTableColumnList() {
		List<JTableListColumn> list = new Vector<JTableListColumn>();
		list.add(new JTableListColumn("手册序号", "owpAppRecvItem.trNo", 60,Integer.class));
		list.add(new JTableListColumn("商品编码", "owpAppRecvItem.complex.code", 60,Integer.class));
		list.add(new JTableListColumn("商品名称", "owpAppRecvItem.hsName", 100));
		list.add(new JTableListColumn("商品规格", "owpAppRecvItem.hsSpec", 100));
		list.add(new JTableListColumn("计量单位", "owpAppRecvItem.hsUnit.name", 100));
		list.add(new JTableListColumn("申报数量", "owpAppRecvItem.qty",100));
		list.add(new JTableListColumn("备注", "owpAppRecvItem.note", 100));
		return list;
	}

	/**
	 * 初始化导入的 外发申请表收回货物 表
	 */
	private void initTableRecv(List list) {
		tableModelRecvItem = new JTableListModel(tbRecvItemInput, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						return InputTableColumnSetUtils
								.getTableColumnList(InputTableColumnSet.OWP_APP_INPUT_RECV);
					}
				});
		tbRecvItemInput.getColumnModel().getColumn(tbRecvItemInput.getColumnCount() - 1)
				.setCellRenderer(new TableMultiRowRender());
		tableModelRecvItem.packRows();
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
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 工具条
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getBtnColumn());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
			jToolBar.add(getCbIsOverwrite());
			jToolBar.add(getCbCheckTitle());
		}
		return jToolBar;
	}

	/**
	 * 打开文件
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("1.打开文件");
			jButton.setToolTipText("打开你所将导入的资料，并检查基本语法错误");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgOwpSendRecvItemInput.this);
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
		return jButton;
	}

	/**
	 * 打开文件，对文件进行读取
	 * @author wss
	 */
	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgOwpSendRecvItemInput.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				if (jTabbedPane.getSelectedIndex() == 0) {
					
					list = parseTxtFileSendItem();
				} else if (jTabbedPane.getSelectedIndex() == 1) {
					
					list = parseTxtFileRecvItem();
				} 
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {	
			}
			return list;
		}

		@Override
		protected void done() {
			List list=null;
			try {
				list = (List)this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if(list==null){
				list=new ArrayList();
			}
			CommonProgress.closeProgressDialog();
			if (jTabbedPane.getSelectedIndex() == 0) {
				initTableSend(list);
			} else if (jTabbedPane.getSelectedIndex() == 1) {
				initTableRecv(list);
			}
		}
		
	}

	/**
	 * 初始化简繁对照
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
	 * 将字符串繁转简
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
	 * 将字符串数组 繁转简
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
	 * 获取字符串中的字符
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
	 * 解析要导入的外发货物文件 （只判断导入资料语法正不正确，并无业务逻辑）
	 * @return
	 */
	private List parseTxtFileSendItem() {

		//所有商品编码
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		
		//所有报关单位
		List unitList = CustomBaseList.getInstance().getUnits();
		

		Hashtable<String, Complex> hsComplex = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsHsUnit = new Hashtable<String, Unit>();

		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hsComplex.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsHsUnit.put(u.getName(), u);
		}

		
		
		//繁转简
		boolean ischange = true;
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		
		/**
		 * 用来装 导入手册序号的，防止导入重复序号
		 */
		List<Integer> lsTrNo = new ArrayList<Integer>();
		
		String suffix = getSuffix(txtFile);
		
		List<List> lines = new ArrayList<List>();
		
		if (suffix.equals("xls")) {
			// 导入xls
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} else {
			// 导入txt
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		List list = new ArrayList();
		
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.OWP_APP_INPUT_SEND);
		
		int zcount = lsIndex.size();// 字段数目
		
		//遍历每一行
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

			TempOwpAppSendItem temp = new TempOwpAppSendItem();
			OwpAppSendItem item = new OwpAppSendItem();
			
			String err = "";
			Complex complexCode = null;
			
			//遍历每一列
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				
				// 料件序号
				if ("owpAppSendItem.trNo".equals(columnField)) {
					
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   手册序号不合法\n";
						continue;
					}
					if (lsTrNo.contains(Double.valueOf(value).intValue())) {
						err = err + "[" + value + "]   手册序号重复\n";
					} else {
						lsTrNo.add(Double.valueOf(value).intValue());
					}
					item.setTrNo(Double.valueOf(value).intValue());
					
				} 
				
				// 商品编码
				else if ("owpAppSendItem.complex.code".equals(columnField)) {
					
					if (hsComplex.get(value) == null) {
						err = err + "[" + value + "]   不正确的商品编码\n";
						// }
					} else {
						Complex c = (Complex) hsComplex.get(value);
						item.setComplex(c);
					}
				}
				
				// 商品名称
				else if ("owpAppSendItem.hsName".equals(columnField)) {
					
					if (value != null && !value.equals("")) {
						item.setHsName(value);
					} else {
						err = err + "[" + value + "]   名称不可为空\n";
					}
				}
				
				//商品规格
				else if ("owpAppSendItem.hsSpec".equals(columnField)) {
					item.setHsSpec(value);
				} 
				
				//商品单位
				else if ("owpAppSendItem.hsUnit.name".equals(columnField)) {
					
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]   单位不为空\n";
					} else {
						if (hsHsUnit.get(value) != null) {
							Unit u = (Unit) hsHsUnit.get(value);
							item.setHsUnit(u);
						} else {
							err = err + "[" + value + "]   单位不存在\n";
						}
					}
				} 
				
				// 备案数量
				else if ("owpAppSendItem.qty".equals(columnField)) {
					
					Double num = 0.0;
					try {
						num = Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   备案数量不合法\n";
						continue;
					}
					item.setQty(num);
				}
				
				else if ("owpAppSendItem.note".equals(columnField)) {
					// 备注
					item.setNote(value);
				}
			}
		
			item.setHead(head);
			item.setCompany(CommonVars.getCurrUser().getCompany());
			temp.setOwpAppSendItem(item);
			temp.setErrinfo(err);
			temp.setRow(i + 1);
			list.add(temp);
		}
		return list;
	}

	/**
	 * 解析要导入的收回货物 文件 （只判断导入资料语法正不正确，并无业务逻辑）
	 * @return
	 */
	private List parseTxtFileRecvItem() {

		//所有商品编码
		List complexlist = customBaseAction.findComplexAll(new Request(
				CommonVars.getCurrUser()));
		
		//所有报关单位
		List unitList = CustomBaseList.getInstance().getUnits();
		

		Hashtable<String, Complex> hsComplex = new Hashtable<String, Complex>();
		Hashtable<String, Unit> hsHsUnit = new Hashtable<String, Unit>();

		for (int i = 0; i < complexlist.size(); i++) {
			Complex c = (Complex) complexlist.get(i);
			hsComplex.put(c.getCode(), c);
		}
		for (int i = 0; i < unitList.size(); i++) {
			Unit u = (Unit) unitList.get(i);
			hsHsUnit.put(u.getName(), u);
		}

		
		
		//繁转简
		boolean ischange = true;
		if (getCbJF().isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		
		/**
		 * 用来装 手册序号的，防止 手册序号 重复
		 */
		List<Integer> lsTrNo = new ArrayList<Integer>();
		
		String suffix = getSuffix(txtFile);
		
		List<List> lines = new ArrayList<List>();
		
		// 导入xls
		if (suffix.equals("xls")) {
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readExcel(txtFile, 2, null);
			} else {
				lines = FileReading.readExcel(txtFile, 1, null);
			}
		} 
		
		// 导入txt
		else {
			
			if (cbCheckTitle.isSelected()) {
				lines = FileReading.readTxt(txtFile, 2, null);
			} else {
				lines = FileReading.readTxt(txtFile, 1, null);
			}
		}
		List list = new ArrayList();
		
		List<String> lsIndex = InputTableColumnSetUtils
				.getColumnFieldIndex(InputTableColumnSet.OWP_APP_INPUT_RECV);
		
		int zcount = lsIndex.size();// 字段数目
		
		//遍历每一行
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

			TempOwpAppRecvItem temp = new TempOwpAppRecvItem();
			OwpAppRecvItem item = new OwpAppRecvItem();
			
			String err = "";
			Complex complexCode = null;
			
			//遍历每一列
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				String columnField = lsIndex.get(j);
				
				// 料件序号
				if ("owpAppRecvItem.trNo".equals(columnField)) {
					
					try {
						Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   手册序号不合法\n";
						continue;
					}
					if (lsTrNo.contains(Double.valueOf(value).intValue())) {
						err = err + "[" + value + "]   手册序号重复\n";
					} else {
						lsTrNo.add(Double.valueOf(value).intValue());
					}
					item.setTrNo(Double.valueOf(value).intValue());
					
				} 
				
				// 商品编码
				else if ("owpAppRecvItem.complex.code".equals(columnField)) {
					
					if (hsComplex.get(value) == null) {
						err = err + "[" + value + "]   不正确的商品编码\n";
						// }
					} else {
						Complex c = (Complex) hsComplex.get(value);
						item.setComplex(c);
					}
				}
				
				// 商品名称
				else if ("owpAppRecvItem.hsName".equals(columnField)) {
					
					if (value != null && !value.equals("")) {
						item.setHsName(value);
					} else {
						err = err + "[" + value + "]   名称不可为空\n";
					}
				}
				
				//商品规格
				else if ("owpAppRecvItem.hsSpec".equals(columnField)) {
					item.setHsSpec(value);
				} 
				
				//商品单位
				else if ("owpAppRecvItem.hsUnit.name".equals(columnField)) {
					
					if (value == null || "".equals(value)) {
						err = err + "[" + value + "]   单位不为空\n";
					} else {
						if (hsHsUnit.get(value) != null) {
							Unit u = (Unit) hsHsUnit.get(value);
							item.setHsUnit(u);
						} else {
							err = err + "[" + value + "]   单位不存在\n";
						}
					}
				} 
				
				// 备案数量
				else if ("owpAppRecvItem.qty".equals(columnField)) {
					
					Double num = 0.0;
					try {
						num = Double.valueOf(value);
					} catch (Exception e) {
						err = err + "[" + value + "]   备案数量不合法\n";
						continue;
					}
					item.setQty(num);
				}
				
				else if ("owpAppRecvItem.note".equals(columnField)) {
					// 备注
					item.setNote(value);
				}
			}
		
			item.setHead(head);
			item.setCompany(CommonVars.getCurrUser().getCompany());
			temp.setOwpAppRecvItem(item);
			temp.setErrinfo(err);
			temp.setRow(i + 1);
			list.add(temp);
		}
		return list;
	}


	/**
	 * 保存数据
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存数据");
			jButton1.setToolTipText("保存下方显示的数据,将检查业务逻辑错误");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileData().execute();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 保存数据
	 */
	class ImportFileData extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			try {
				long beginTime = System.currentTimeMillis();
				CommonProgress
						.showProgressDialog(DgOwpSendRecvItemInput.this);
				
				CommonProgress.setMessage("系统正在导入文件资料，请稍后...");
				
				boolean haveError = false;

				List ls = null;//要保存的数据
				List lsError = null;//保存数据时返回来的有错误的数据
				
				//外发货物
				if (jTabbedPane.getSelectedIndex() == 0) {
					ls = tableModelSendItem.getList();
					
					//判断有没有错误信息
					for (int i = 0; i < ls.size(); i++) {
						TempOwpAppSendItem temp = (TempOwpAppSendItem) ls.get(i);
		
						if (temp.getErrinfo() != null
									&& !temp.getErrinfo().equals("")) {
							CommonProgress.closeProgressDialog();
							JOptionPane.showMessageDialog(
									DgOwpSendRecvItemInput.this, "有错误信息，不可保存！",
									"提示!", 2);
							return null;
						}
					}
					
					//保存数据
					lsError = owpAppAction.importOwpAppSendItemFromTxtFile(new Request(CommonVars
								.getCurrUser()), ls, cbIsOverwrite.isSelected());
					
				} 
				
				//收回货物
				else if (jTabbedPane.getSelectedIndex() == 1) {
					ls = tableModelRecvItem.getList();
					
					//有错误信息不可保存
					for (int i = 0; i < ls.size(); i++) {
						TempOwpAppRecvItem temp = (TempOwpAppRecvItem) ls.get(i);
		
						if (temp.getErrinfo() != null && !temp.getErrinfo().equals("")) {
							CommonProgress.closeProgressDialog();
							JOptionPane.showMessageDialog(
									DgOwpSendRecvItemInput.this, "有错误信息，不可保存！",
									"提示!", 2);
							return null;
						}
					}
					
					//保存数据
					lsError = owpAppAction.importOwpAppRecvItemFromTxtFile(new Request(CommonVars
								.getCurrUser()), ls, cbIsOverwrite.isSelected());
				}
				CommonProgress.closeProgressDialog();
				
				if(lsError == null || lsError.size() == 0){
					long totalTime = System.currentTimeMillis() - beginTime;
						
					JOptionPane.showMessageDialog(DgOwpSendRecvItemInput.this,
								"导入数据成功！导入数据记录 " + ls.size() + " 条,共用时 " + totalTime
										+ " 毫秒", "提示", JOptionPane.INFORMATION_MESSAGE);
					DgOwpSendRecvItemInput.this.dispose();
					
				}else{
					DgErrorData dg = new DgErrorData();
					dg.setIsSend(jTabbedPane.getSelectedIndex() == 0 ? true:false);
					dg.setDataSource(lsError);
					dg.setVisible(true);
				}
				
				
				
			}catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgOwpSendRecvItemInput.this,
						"导入数据失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}
	}
	
	
	/**
	 * 退出按钮
	 * @return javax.swing.JButton
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("退    出");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgOwpSendRecvItemInput.this.dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.TOP);
			jTabbedPane.addTab("1.外发货物", null, getJPanel(), null);
			jTabbedPane.addTab("2.收回货物", null, getJPanel2(), null);
		}
		return jTabbedPane;
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
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
			jPanel2.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTbSendItemInput());
		}
		return jScrollPane;
	}

	/**
	 * 导入的 外发货物 表格
	 * @return javax.swing.JTable
	 */
	private JTable getTbSendItemInput() {
		if (tbSendItemInput == null) {
			tbSendItemInput = new JTable();
		}
		return tbSendItemInput;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTbRecvItemInput());
		}
		return jScrollPane1;
	}

	/**
	 * 导入的 收回货物 表格
	 * @return javax.swing.JTable
	 */
	private JTable getTbRecvItemInput() {
		if (tbRecvItemInput == null) {
			tbRecvItemInput = new JTable();
		}
		return tbRecvItemInput;
	}

	/**
	 * 繁转简 选项
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
	 * 资料存在覆盖导入 选项
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbIsOverwrite() {
		if (cbIsOverwrite == null) {
			cbIsOverwrite = new JCheckBox();
			cbIsOverwrite.setText("资料存在覆盖导入");
			cbIsOverwrite.setToolTipText("如果资料存在但没有勾选此项则会被跳过");
		}
		return cbIsOverwrite;
	}

	public OwpAppHead getHead() {
		return head;
	}

	public void setHead(OwpAppHead head) {
		this.head = head;
	}

	/**
	 * 第一行为标题 选项
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
	 * 栏位设定
	 * @return javax.swing.JButton
	 */
	private JButton getBtnColumn() {
		if (btnColumn == null) {
			btnColumn = new JButton();
			btnColumn.setText("栏位设定");
			btnColumn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (jTabbedPane.getSelectedIndex() == 0) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.OWP_APP_INPUT_SEND);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableSend(new ArrayList());
						}
					} else if (jTabbedPane.getSelectedIndex() == 1) {
						DgInputColumnSet dg = new DgInputColumnSet();
						dg.setTableFlag(InputTableColumnSet.OWP_APP_INPUT_RECV);
						dg.setVisible(true);
						if (dg.isOk()) {
							initTableRecv(new ArrayList());
						}
					}
				}
			});
		}
		return btnColumn;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
