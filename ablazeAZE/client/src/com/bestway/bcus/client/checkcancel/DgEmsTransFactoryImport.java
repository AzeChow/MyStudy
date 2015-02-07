package com.bestway.bcus.client.checkcancel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
import javax.swing.filechooser.FileFilter;

import com.bestway.bcus.checkcancel.action.CheckCancelAction;
import com.bestway.bcus.checkcancel.entity.EmsAnalyHead;
import com.bestway.bcus.checkcancel.entity.EmsTransFactory;
import com.bestway.bcus.client.common.CommonClientBig5GBConverter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.CommonUtils;
import com.bestway.common.GetKeyValueImpl;
import com.bestway.common.Request;
import com.bestway.common.materialbase.action.MaterialManageAction;
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.Materiel;
import com.bestway.ui.winuicontrol.JDialogBase;

/**
 * 工厂Bom导入
 * 
 * @author
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DgEmsTransFactoryImport extends JDialogBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JToolBar jToolBar = null;
	private JButton btnOpen = null;
	private JButton btnSave = null;
	private JButton btnExit = null;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	protected JTable jTable = null;
	public EmsAnalyHead head;
	protected JTableListModel tableModel = null;
	/**
	 * 打开文件类（Excel或txt）
	 */
	private File txtFile = null;
	private JCheckBox cb = null;
	/**
	 * 用于繁转简
	 */
	private Hashtable gbHash = null; // @jve:decl-index=0:
	protected MaterialManageAction materialManageAction = null;
	protected CheckCancelAction checkCancelAction = null;
	
	private JCheckBox cbCheckTitle = null;
	
	private List returnList = null;

	/**
	 * DgBomTxtImport的构造函数 This is the default constructor
	 */
	public DgEmsTransFactoryImport() {
		super();
		materialManageAction = (MaterialManageAction) CommonVars
				.getApplicationContext().getBean("materialManageAction");
		checkCancelAction = (CheckCancelAction) CommonVars
				.getApplicationContext().getBean("checkCancelAction");
		
		initialize();		
		initTable(new ArrayList());
	}

	/**
	 * 初始化控件 This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(744, 500);
		this.setContentPane(getJContentPane());
		this.setTitle("BOM文本导入");
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
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			jToolBar = new JToolBar();
			jToolBar.setLayout(f);
			jToolBar.add(getJButton());
			jToolBar.add(getJButton1());
			jToolBar.add(getJButton2());
			jToolBar.add(getJRadioButton());
			jToolBar.add(getCbCheckTitle());
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
			btnOpen.setText("1.打开文本");
			btnOpen.setPreferredSize(new Dimension(70, 30));
			btnOpen.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					txtFile = getFile();
					// System.out.println("hi");
					if (txtFile == null) {
						return;
					}
					new ImportFileDataRunnable().execute();
				}
			});
		}
		return btnOpen;
	}

	/**
	 * 导入文件流
	 * 
	 * @author hw
	 * 
	 */
	class ImportFileDataRunnable extends SwingWorker {
		@Override
		protected Object doInBackground() throws Exception {
			List list = new ArrayList();
			try {
				CommonProgress.showProgressDialog(DgEmsTransFactoryImport.this);
				CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
				list = parseTxtFile();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
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
			initTable(list);

		}

	}

	/**
	 * 初始化繁转简对照表
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
	 * 获取到文件的后缀名
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
	 * @param source
	 * @return
	 */
	private String[] changStrs(String[] source) {
		String newStrs[] = new String[source.length];
		for (int i = 0, n = source.length; i < n; i++) {
			// newStrs[i] = changeStr(source[i]);
			newStrs[i] = CommonClientBig5GBConverter.getInstance()
					.big5ConvertToGB(source[i]);
		}
		return newStrs;
	}

	/**
	 * 解析导入的文件
	 * 
	 * @return
	 */
	private List parseTxtFile() {
		boolean ischange = true;
		if (cb.isSelected()) {
			infTojHsTable();
		} else {
			ischange = false;
		}
		
		// 查询料件
		List<Materiel> materielList = materialManageAction.findMateriel(new Request(
				CommonVars.getCurrUser()));
		Map<String, Materiel> materielMap = CommonUtils.listToMap(materielList, new GetKeyValueImpl<Materiel>() {
			@Override
			public String getKey(Materiel e) {
				return e.getPtNo();
			}
			
		});
				
		// 查询单位
		List<CalUnit> unitList = materialManageAction.findCalUnit(new Request(
				CommonVars.getCurrUser()));
		Map<String, CalUnit> unitMap = CommonUtils.listToMap(unitList, new GetKeyValueImpl<CalUnit>(){
			@Override
			public String getKey(CalUnit e) {
				return e.getName();
			}});
		
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
		ArrayList<EmsTransFactory> list = new ArrayList<EmsTransFactory>();
		int zcount = 6; // 字段数目
		EmsTransFactory transFactory = null;
		Materiel materiel = null;
		for (int i = 0; i < lines.size(); i++) {
			List line = lines.get(i);
			String[] strs = new String[zcount];
			for (int j = 0; j < zcount; j++) {
				strs[j] = (line.get(j) == null ? "" : line.get(j).toString().trim());
			}
			if (ischange) {
				strs = changStrs(strs);
			}
			String err = "";
			transFactory = new EmsTransFactory();
			materiel = materielMap.get(strs[0]);
			for (int j = 0; j < zcount; j++) {
				String value = strs[j];
				
				switch (j) {
				case 0:
					if(CommonUtils.notEmpty(value)) {
						if(materiel == null) {
							err += "; 【料件】不存在！";
						}
						transFactory.setPtNo(value);
						transFactory.setPtName(materiel.getFactoryName());
						transFactory.setPtSpec(materiel.getFactorySpec());
					} else {
						err += "; 【料号】不能为空！";
					}
					break;
//				case 1:
//					if(CommonUtils.notEmpty(value)) {
//						transFactory.setPtName(value);
//					} else {
//						err += "; 【品名】不能为空！";
//					}
//					break;
//				case 2:
//					if(CommonUtils.notEmpty(value)) {
//						transFactory.setPtSpec(value);
//					} else {
//						err += "; 【规格】不能为空！";
//					}
//					break;
				case 3:
					if(CommonUtils.notEmpty(value)) {
						transFactory.setCalUnit(unitMap.get(value));
						if(transFactory.getCalUnit() == null) {
							err += "; 【单位】不存在！";
						}
					} else {
						err += "; 【单位】不能为空！";
					}
					break;
				case 4:
					if(CommonUtils.notEmpty(value)) {
						try {
							transFactory.setUnTransferNum(Double.parseDouble(value));
						} catch (NumberFormatException e) {
							err += "; 【已收货未转厂数量】必须为数字";
						}
					} else {
						err += "; 【已收货未转厂数量】不能为空！";
					}
					break;
				case 5:
					if(CommonUtils.notEmpty(value)) {
						try {
							transFactory.setUnReceiveNum(Double.parseDouble(value));
						} catch (NumberFormatException e) {
							err += "; 【已送货未转厂折料数量】必须为数字";
						}
					} else {
						err += "; 【已送货未转厂折料数量】不能为空！";
					}
					break;
				}
				
				transFactory.setErr(err);
			}

			list.add(transFactory);
		}
		return list;
	}

	private JButton getJButton1() {
		if (btnSave == null) {
			btnSave = new JButton();
			btnSave.setText("2.保存数据");
			btnSave.setPreferredSize(new Dimension(70, 30));
			btnSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					saveData();
				}
			});
		}
		return btnSave;
	}

	public void saveData() {
		List list = null;
		list = tableModel.getList();
		if (list.size() == 0) {
			JOptionPane.showMessageDialog(DgEmsTransFactoryImport.this,
					"数据为空，请先导入数据！", "提示!", 2);
			return;
		}
		for (int i = 0; i < tableModel.getList().size(); i++) {
			EmsTransFactory obj = (EmsTransFactory) tableModel.getList().get(i);
			if (obj.getErr() != null && !obj.getErr().equals("")) {
				JOptionPane.showMessageDialog(DgEmsTransFactoryImport.this,
						"有错误信息，不可保存！", "提示!", 2);
				return;
			}
		}
		
		new SaveDataRunnable(list).run();
	}

	/**
	 * 保存文件流
	 * 
	 * @author hw
	 * 
	 */
	public class SaveDataRunnable extends Thread {
		/**
		 * 传入导入的资料
		 */
		public List data = new ArrayList();

		public SaveDataRunnable(List list) {
			this.data = list;
		}

		public void run() {
			CommonProgress.showProgressDialog(DgEmsTransFactoryImport.this);
			CommonProgress.setMessage("系统正在读取文件资料，请稍后...");
			// list = tableModel.getList();
			try {
				
				returnList = checkCancelAction.importEmsTransFactory(
						new Request(CommonVars.getCurrUser()), head, data);
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgEmsTransFactoryImport.this,
						"导入失败!\n\nBOM数据: " + String.valueOf(data.size()), "提示",
						2);
				e.printStackTrace();
				return;
			} finally {
				CommonProgress.closeProgressDialog();
			}
			CommonProgress.closeProgressDialog();
			JOptionPane.showMessageDialog(DgEmsTransFactoryImport.this,
					"导入成功!\n\nBOM数据: " + String.valueOf(data.size()), "提示", 2);
			DgEmsTransFactoryImport.this.dispose();
		}
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
			btnExit.setPreferredSize(new Dimension(60, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					DgEmsTransFactoryImport.this.dispose();
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
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel;
	}

	// /**
	// * This method initializes jPanel1
	// *
	// * @return javax.swing.JPanel
	// */
	// private JPanel getJPanel1() {
	// if (jPanel1 == null) {
	// jPanel1 = new JPanel();
	// jPanel1.setLayout(new BorderLayout());
	// }
	// return jPanel1;
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
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}

	/**
	 * 初始化单据头表
	 * 
	 * @return javax.swing.JScrollPane
	 */
	protected void initTable(final List list) {
		tableModel = new JTableListModel(jTable, list,
				new JTableListModelAdapter() {
					public List InitColumns() {
						List columns = new ArrayList();
						columns.add(addColumn("料号", "ptNo", 80));
						columns.add(addColumn("品名", "ptName", 150));
						columns.add(addColumn("规格", "ptSpec", 200));
						columns.add(addColumn("计量单位", "calUnit.name", 60));
						columns.add(addColumn("已收货未转厂", "unTransferNum", 80));
						columns.add(addColumn("已转厂未收货", "unReceiveNum", 80));
						columns.add(addColumn("错误信息", "err", 300));

						return columns;
					}
				});
	}

	/**
	 * 调出文件选择框
	 * 
	 * @return
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
	 * 文件过滤类
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
	private JCheckBox getJRadioButton() {
		if (cb == null) {
			cb = new JCheckBox();
			cb.setText("繁转简");
		}
		return cb;
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
	 * @return the returnList
	 */
	public List getReturnList() {
		return returnList;
	}
	
	
	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
} // @jve:decl-index=0:visual-constraint="10,10"
