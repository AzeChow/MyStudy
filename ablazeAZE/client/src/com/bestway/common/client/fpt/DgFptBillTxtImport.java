package com.bestway.common.client.fpt;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.cas.action.CasAction;
import com.bestway.bcus.cas.entity.BillTemp;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;

public class DgFptBillTxtImport extends JDialogBase {

	private JPanel jContentPane = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JScrollPane jScrollPane1 = null;

	private JTable jTable1 = null;

	private JTableListModel tableModel = null;

	private JTableListModel tableModelDetail = null;

	private File txtFile = null;

	private JRadioButton jRadioButton = null;

	private Hashtable gbHash = null;

	private Hashtable headHash = null;

	private CasAction casAction = null;

	private JButton jButton3 = null;

	//private Hashtable hs = null;
	
	private BillTemp bill = null;

	private JButton jButton4 = null;

	/**
	 * This is the default constructor
	 */
	public DgFptBillTxtImport() {
		super();
		casAction = (CasAction) CommonVars.getApplicationContext().getBean(
				"casAction");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(675, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("转厂单据导入");
		initTable(new Vector());
		initTableDetail(new Vector());
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
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
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
			jToolBar.add(getJButton());
			jToolBar.add(getJButton3());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton4());
			jToolBar.add(getJButton2());
			
			jToolBar.add(getJRadioButton());
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
			jButton.setText("1.打开");
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

	class ImportFileDataRunnable extends Thread {
		@Override
		public void run() {
			List list = new ArrayList();
			List headList = new ArrayList();
			List detailList = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgFptBillTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
				headList = (List) list.get(0);
				detailList = (List) list.get(1);
				String err = (String) list.get(2);
				if (!err.equals("")){
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgFptBillTxtImport.this,
							"注意以下错误:"+err, "提示", 2);
					headList = new Vector();
					detailList = new Vector();
				}
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统正在检验文件资料，请稍后...");
			} catch (Exception e) {

			} finally {
				initTable(headList);
				initTableDetail(detailList);
			}
		}
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
	

	private List parseTxtFile() {
		List allList = new ArrayList();
		int zcount = 10;// 字段数目
		boolean ischange = true;
		if (getJRadioButton().isSelected()) {
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
			lines = FileReading.readExcel(txtFile, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(txtFile, 0, null);
		}

		ArrayList list = new ArrayList(); // 表头
		ArrayList listDetail = new ArrayList();// 表体
        String err = "";
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
			
			BillTemp obj = new BillTemp();
			for (int j = 0; j < zcount; j++) {
				String value = getFileColumnValue(strs, j);
				if (j == 0) {
					/*if (value == null || value.equals("") || (value != null && (!value.equals("0") || value.equals("1")))){
						err = err + "\n警告:第" + (i+1) + "行   ["+value+"]   单据类型不合法，应该为进货：0 出货：1  !";
					} else {*/
				    obj.setBill1(value);// 单据类型代码
					//}
				} else if (j == 1) {
					obj.setBill2(value);// 单据号码
				} else if (j == 2) {
					obj.setBill3(value);// 客户供应商Locale.SIMPLIFIED_CHINESE
				} else if (j == 3) {
					if (value == null || "".equals(value)) {
						continue;
					}
					//err = err + "\n警告:第" + (i+1) + "行   ["+value+"]   日期不合法，应该为 YYYY-MM-DD !";
					obj.setBill4(value);// 生效日期			
				} else if (j == 4) {
					obj.setBill5(value);// 备注
				} else if (j == 5) {
					if (value == null || "".equals(value)) {
						continue;
					}
					try{
						Integer.valueOf(value);
					} catch (Exception e){
						err = err + "\n警告:第" + (i+1) + "行   ["+value+"]   序号不合法!";
						continue;
					}
					obj.setBill6(value);// 序号
				} else if (j == 6) {
					if (value == null || "".equals(value)) {
						continue;
					}
					try{
						Double.valueOf(value);
					} catch (Exception e){
						err = err + "\n警告:第" + (i+1) + "行   ["+value+"]   送货数不合法!";
						continue;
					} 
					obj.setBill7(value);// 送货数
				} else if (j == 7) {
					if (value == null || "".equals(value)) {
						continue;
					}
					try{
						Double.valueOf(value);
					} catch (Exception e){
						err = err + "\n警告:第" + (i+1) + "行   ["+value+"]   单价不合法!";
						continue;
					} 
					obj.setBill8(value);// 单价
				}  else if (j == 8) {
					obj.setBill9(value);// 事业部
				}  else if (j == 9) { //关封号
					obj.setBill10(value);
				}
			}
			listDetail.add(obj);// 表体
			String key = (obj.getBill1() == null ? "" : obj.getBill1()) + "/"
					+ (obj.getBill2() == null ? "" : obj.getBill2()) + "/"
					+ (obj.getBill3() == null ? "" : obj.getBill3()) + "/"
					+ (obj.getBill4() == null ? "" : obj.getBill4()) + "/"
					+ (obj.getBill5() == null ? "" : obj.getBill5()) + "/"
					+ (obj.getBill10() == null ? "" : obj.getBill10());
			if (headHash.get(key) == null) {
				headHash.put(key, key);
				list.add(obj);
			}
		}
		allList.add(list);
		allList.add(listDetail);
		allList.add(err);

		return allList;
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
			jButton1.setText("3.装载");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					List dlist = null;
					String temp ="";
					if (tableModel.getList().size() < 1) {
						JOptionPane.showMessageDialog(DgFptBillTxtImport.this,
								"装载数据为空!", "提示", 2);
						return;
					}
					/*if (hs == null) {
						JOptionPane.showMessageDialog(DgTransBillTxtImport.this,
								"参数没有设置!", "提示", 2);
						return;
					}*/
					/*dlist = tableModelDetail.getList();
					temp =casAction.findMateriel(dlist);
					if(!temp.equals("")){
						if (JOptionPane.showConfirmDialog(DgTransBillTxtImport.this,
								temp+"料号不存在，是否继续导入!!!", "提示",
								JOptionPane.OK_CANCEL_OPTION)== JOptionPane.OK_OPTION){
							new SaveDataRunnable().start();
						}
						return;
					}*/
					new SaveDataRunnable().start();
				}
			});
		}
		return jButton1;
	}

	class SaveDataRunnable extends Thread {
		@Override
		public void run() {
			List list = null;
			List dlist = null;
			try {
				CommonProgress.showProgressDialog(DgFptBillTxtImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = tableModel.getList();
				dlist = tableModelDetail.getList();
				try {
					casAction.executeTransBillImport(new Request(CommonVars
							.getCurrUser()), list, dlist);
				} catch (SQLException e1) {
					CommonProgress.closeProgressDialog();
					e1.printStackTrace();
				}
				CommonProgress.closeProgressDialog();
				CommonProgress.setMessage("系统读文件资料完毕！请稍后...");
			} catch (Exception e) {

			} finally {
				initTable(new Vector());
				initTableDetail(new Vector());
				JOptionPane.showMessageDialog(DgFptBillTxtImport.this,
						"保存完毕!\n\n单据头数据: " + String.valueOf(list.size())
								+ " 笔\n单据体数据: " + String.valueOf(dlist.size())
								+ " 笔", "提示", 2);
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
					DgFptBillTxtImport.this.dispose();
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setTopComponent(getJPanel());
			jSplitPane.setBottomComponent(getJPanel1());
			jSplitPane.setDividerLocation(200);
		}
		return jSplitPane;
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
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane1(), java.awt.BorderLayout.CENTER);
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
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTable1());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTable1
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable1() {
		if (jTable1 == null) {
			jTable1 = new JTable();
		}
		return jTable1;
	}

	// 单据头
	private void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("单据类型代码", "bill1", 100));
						list.add(addColumn("单据号码", "bill2", 100));
						list.add(addColumn("客户供应商(代码)", "bill3", 120));
						list.add(addColumn("生效日期(YYYY-MM-DD)", "bill4", 140));
						list.add(addColumn("备注", "bill5", 100));
						list.add(addColumn("关封号", "bill10", 100));
						return list;
					}
				});
	}

	// 单据体
	private void initTableDetail(final List list) {
		tableModelDetail = new JTableListModel(jTable1, list,
				new JTableListModelAdapter() {
					@Override
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list = new Vector<JTableListColumn>();
						list.add(addColumn("1.单据类型代码", "bill1", 100));
						list.add(addColumn("2.单据号码", "bill2", 100));
						list.add(addColumn("3.客户供应商(代码)", "bill3", 120));
						list.add(addColumn("4.生效日期(YYYY-MM-DD)", "bill4", 140));
						list.add(addColumn("5.备注", "bill5", 100));

						list.add(addColumn("6.序号", "bill6", 100));
						list.add(addColumn("7.送货数", "bill7", 100));
						list.add(addColumn("8.单价", "bill8", 100));
						list.add(addColumn("9.事业部(名称)", "bill9", 100));
						list.add(addColumn("10.关封号", "bill10", 100));
						return list;
					}
				});
	}

//	 调出文件选择框
	private File getFile() {
		File txtFile = null;
		JFileChooser fileChooser = new JFileChooser();
		//fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("xls"));
		fileChooser.addChoosableFileFilter(new ExampleFileFilter("txt"));
		
		
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

		@Override
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

		@Override
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
	private JRadioButton getJRadioButton() {
		if (jRadioButton == null) {
			jRadioButton = new JRadioButton();
			jRadioButton.setText("【开始导入】是否繁转简   [文本格式见下方表格表头排列顺序]");
		}
		return jRadioButton;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setText("2.参数设置");
			jButton3.setEnabled(false);
			jButton3.setForeground(java.awt.Color.blue);
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					/*DgBillTxtImportSet dg = new DgBillTxtImportSet();
					dg.setHs(hs);
					dg.setVisible(true);
					if (dg.isOk()) {
						hs = dg.hs;
					}*/
				}
			});
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setText("说明");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(DgFptBillTxtImport.this,"1.单据类型代码: 0 表示进货， 1 表示出货","提示",2);
				}
			});
		}
		return jButton4;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
