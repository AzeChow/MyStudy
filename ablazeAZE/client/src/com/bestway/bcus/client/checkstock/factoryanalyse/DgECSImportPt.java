package com.bestway.bcus.client.checkstock.factoryanalyse;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSBadImg;
import com.bestway.bcus.checkstock.entity.ECSFinishingImg;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSStagnateImg;
import com.bestway.bcus.checkstock.entity.ECSStockBuyImg;
import com.bestway.bcus.checkstock.entity.ECSStockExg;
import com.bestway.bcus.checkstock.entity.ECSStockImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutFactoryImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendImg;
import com.bestway.bcus.checkstock.entity.ECSStockProcessImg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingImg;
import com.bestway.bcus.checkstock.entity.temp.MergeTemp;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.FileUtils;
import com.bestway.util.RegexUtil;

@SuppressWarnings("unchecked")
public class DgECSImportPt<T> extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JButton btnInput = null;

	private JButton btnExit = null;

	/** 退出 */
	private boolean isImg = true;

	/*** 繁转简 */
	private Hashtable gbHash = null; //

	/**
	 * 对应关系
	 */
	private Map<String, String[]> bcsMap;  //  @jve:decl-index=0:
	private List<String[]> lsResult;
	private List<String[]> lsResult2;

	/** 导入文件 */
	private File file = null;

	private JToolBar jToolBar = null;

	private JButton btnSaveData = null;

	private JScrollPane jScrollPane = null;

	private JTable jTable = null;

	private JCheckBox cbCheckTitle = null;

	private JCheckBox cbBig5ConvertToGB = null;

	private int projectType = ProjectType.BCUS;

	private JButton btnDel = null;

	private List<MergeTemp> bcsInnerMergeList;
	
	private Map<String,Integer> bomMaxMap;

	private Map<String,Integer> bomExistsMap;
	
	private ECSCheckStockAction ecsCheckStockAction;
	
	private boolean isPass = true;

	private String clazz;  //  @jve:decl-index=0:
	
	private List<T> listData;
	
	private ECSSection section;

	public ECSSection getSection() {
		return section;
	}

	public void setSection(ECSSection section) {
		this.section = section;
	}

	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	private Request request;

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	private JTableListModel tableMode;
	private JCheckBox cbMax;
	private JButton btnDLTpl;

	/**
	 * This method initializes
	 * 
	 */
	public DgECSImportPt(boolean isImg) {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		this.isImg = isImg;
		initialize();
		if (isImg) {
			initTableImg(new ArrayList());
		} else {
			initTableExg(new ArrayList());
		}
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(950, 498));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("数据导入接口 -> 料号级 -> "+((isImg)?"料件":"成品"));
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
			jPanel.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jPanel;
	}

	/**
	 * 
	 * @return
	 */
	private Map<String, String[]> initConditionList() {
		bcsMap = new HashMap<String, String[]>();
		if (isImg) {
			bcsInnerMergeList = ecsCheckStockAction.findAllEdiMergePtPartImg(request);
		} else {
			bcsInnerMergeList = ecsCheckStockAction.findAllEdiMergePtPartExg(request);
		}

		for (MergeTemp mTemp : bcsInnerMergeList) {
			String[] strs = new String[] { mTemp.getPtName(), mTemp.getPtSpec(), mTemp.getPtUnit() };
			bcsMap.put(mTemp.getPtNo(), strs);
		}

		return bcsMap;
	}

	
	private Map<String, Integer> getBomMaxMap() {
		List<Object[]> bomList = ecsCheckStockAction.findMaxVersion(request);
		bomMaxMap = new HashMap<String, Integer>();
		for(Object[] o:bomList){
			bomMaxMap.put(o[0].toString(), (Integer)o[1]);
		}
		return bomMaxMap;
	}
	
	
	private Map<String, Integer> getExistsBomMap() {
		List<Object[]> bomList = ecsCheckStockAction.findAllVersion(request);
		bomExistsMap = new HashMap<String, Integer>();
		for(Object[] o:bomList){
			bomExistsMap.put(o[0].toString()+"@"+(Integer)o[1], (Integer)o[1]);
		}
		return bomExistsMap;
	}
	/**
	 * 解析导入文件
	 * 
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	private List parseTxtFile() {
		lsResult = new ArrayList();
		String suffix = getSuffix(file);
		List<List> lines = new ArrayList<List>();

		if (suffix.equals("xls")) {
			// 导入xls
			lines = FileReading.readExcel(file, 0, null);
		} else {
			// 导入txt
			lines = FileReading.readTxt(file, 0, "GBK");
		}

		// 业务逻辑处理
		// 行操作 lines.size()
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}

			List line = lines.get(i);
			String[] strs = null;
			if (line.size() <= 6) {
//				strs = new String[7 + 1];
				strs = new String[8 + 1];
			} else {
				strs = new String[line.size() + 1];
			}

			// 列操作
			StringBuffer sb = new StringBuffer();
			String[] bcsStrs = null;
			for (int j = 0; j < strs.length; j++) {
				if(j < line.size()){
					Object obj = line.get(j);
					strs[j] = obj == null ? "" : obj.toString();
				}

				// 繁简转换
				if (cbBig5ConvertToGB.isSelected()) {
					strs = changStrs(strs);
				}
				CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【" + i + "】行,请稍等...");

				if (j == 0) {

					if (strs[0] == null || "".equals(strs[0])) {
						sb.append(" 料号不能为空 ");
					} else {
						bcsStrs = bcsMap.get(strs[0]);
						if (bcsStrs == null) {
							sb.append("  料号在归并关系中不存在 ");
						}
					}
				} else if (j == 1) {
					if ("".equals(strs[1].toString()) || strs[1] == null) {
						sb.append("  数量不能为空 ");
					} else if (!RegexUtil.checkFloat(strs[1].toString())) {
						sb.append("  数量类型不对 ");
					}
				} else if (j == 2 && !isImg) {
					if (strs[2] == null || "".equals(strs[2].toString())) {
						if(cbMax.isSelected()){
							String ver = String.valueOf(bomMaxMap.get(strs[0]));
							if(ver == null || "null".equals(ver)){
								sb.append("  没有找到最大版本号 ");
							}else{
								strs[2] = ver;
							}
						}else{
							sb.append("  版本号不能为空 ");
						}
						
					} else{
						if(!RegexUtil.checkFloat(strs[2].toString())){
							sb.append("  版本号类型不对 ");
						}else{
							if(bomExistsMap.get(strs[0]+"@"+strs[2]) == null){
								sb.append(" 版本在归并前BOM或者账册单耗中不存在 ");
							}
						}
					}
				}
			}

			if (bcsStrs != null) {
				if (isImg) {
					strs[2] = bcsStrs[0];
					strs[3] = bcsStrs[1];
					strs[4] = bcsStrs[2];
				} else {
					strs[3] = bcsStrs[0];
					strs[4] = bcsStrs[1];
					strs[5] = bcsStrs[2];
					//strs[2] = bcsStrs[2];//version
				}
			}
			String errorInfo = sb.toString() == null ? "" : sb.toString();
			if (errorInfo != null && errorInfo.length() > 2) {
				isPass = false;
				if (isImg) {
//					strs[5] = errorInfo;
					strs[7] = errorInfo;
				} else {
//					strs[6] = errorInfo;
					strs[8] = errorInfo;
				}
			}

			lsResult.add(strs);
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
			btnExit.setPreferredSize(new Dimension(70, 30));
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return btnExit;
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
				CommonProgress.showProgressDialog(DgECSImportPt.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				// System.out.println("Exgs >>> >>> >>>"+bcsInnerMergeList.size());
				initConditionList();// 初始化查询条件
				getBomMaxMap();
				getExistsBomMap();
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				list = parseTxtFile();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgECSImportPt.this, "导入数据失败：！" + e.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return list;
		}

		@Override
		protected void done() {
			super.done();
			List list = new ArrayList();
			try {
				list = (List) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (isImg) {
				initTableImg(list);
			} else {
				initTableExg(list);
			}
		}
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
			jToolBar.setPreferredSize(new Dimension(20, 50));
			jToolBar.setFloatable(false);
			jToolBar.setLayout(f);

			jToolBar.add(getBtnInput());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnDLTpl());

			jToolBar.add(getBtnExit());
			jToolBar.add(getCbCheckTitle());
			jToolBar.add(getCbBig5ConvertToGB());
			jToolBar.add(getCbMax());

		}
		return jToolBar;
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
			btnInput.setPreferredSize(new Dimension(70, 30));
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser.showOpenDialog(DgECSImportPt.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					file = fileChooser.getSelectedFile();
					if (file == null || !file.exists()) {
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
	 * This method initializes btnDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setPreferredSize(new Dimension(70, 30));
			btnDel.setText("删除错误");
			btnDel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if(lsResult != null && lsResult.size() >0 ){
//						int len = 6;
						int len = 8;
						if (!isImg) {
//							len = 7;
							len = 9;
						}										
						lsResult2 = new ArrayList<String[]>();
						for (int i = 0; i < lsResult.size(); i++) {
							String error = lsResult.get(i)[len - 1] == null ? null : lsResult.get(i)[len - 1].toString();
							if (error == null || "".equals(error)) {
								lsResult2.add(lsResult.get(i));
							}
						}
						if (isImg) {
							initTableImg(lsResult2);
						} else {
							initTableExg(lsResult2);
						}
						isPass = true;
					}
				}
			});
		}
		return btnDel;
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
					String uploaderr = "D:/bestwaydata/uploaderr";// 导入失败路径
					String uploaddatabak = "D:/bestwaydata/uploaddatabak";// 导入成功路径

					if (!isPass) {
						JOptionPane.showMessageDialog(DgECSImportPt.this, "有错误信息，不可保存！", "提示!", 2);
						File uploaderrdir = new File(uploaderr);
						File uploaderrdirfile = new File(uploaderr + File.separator + file.getName());
						if (uploaderrdir.exists()) {
							moveFile(file, uploaderrdirfile);
						}
						return;
					}

					if (tableMode.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgECSImportPt.this, "无数据可保存", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// **导入后保存的操作
					DgECSImportPt.this.generalSveEntity(lsResult2);
					JOptionPane.showMessageDialog(DgECSImportPt.this, "保存数据成功！", "提示!", JOptionPane.INFORMATION_MESSAGE);
					DgECSImportPt.this.dispose();

					// delete temp File
					File uploaddatabakdir = new File(uploaddatabak);
					File uploaddatabakdirfile = new File(uploaddatabak + File.separator + file.getName());
					if (uploaddatabakdir.exists()) {
						moveFile(file, uploaddatabakdirfile);
					}
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
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
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
		if(s != null && !s.trim().isEmpty()){
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
	 * 初始化表格
	 * 
	 * @param list
	 */
	private void initTableExg(List list) {
		tableMode = new JTableListModel(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("料号（必填）", "ptNo", 100));
				list.add(new JTableListColumn("库存数量（必填）", "ptQty", 100));
				list.add(new JTableListColumn("版本号（必填）", "version", 100));
				list.add(new JTableListColumn("工厂名称", "ptName", 140));
				list.add(new JTableListColumn("工厂规格", "Speec", 100));
				list.add(new JTableListColumn("工厂单位", "Unit", 100));
				list.add(new JTableListColumn("仓库","warehouse",100));
				list.add(new JTableListColumn("备注","memo",200));
				//list.add(new JTableListColumn("工单号", "userQty", 100));
				list.add(new JTableListColumn("错误信息", "errorInfo", 320));
				
				return list;
			}
		});

		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (value != null && !"".equals(value)) {// 第一个属性列的值
					super.setBackground(new java.awt.Color(255, 255, 150));
				} else {
					if (row == table.getSelectedRow()) {
						setForeground(table.getSelectionForeground());
						setBackground(table.getSelectionBackground());
					} else {
						setForeground(table.getForeground());
						setBackground(table.getBackground());
					}
				}
				return this;
			}
		});
	}

	/**
	 * 初始化表格
	 * 
	 * @param list
	 */
	private void initTableImg(List list) {
		tableMode = new JTableListModel(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("料号（必填）", "ptNo", 100));
				list.add(new JTableListColumn("库存数量（必填）", "ptQty", 100));
				list.add(new JTableListColumn("工厂名称", "ptName", 140));
				list.add(new JTableListColumn("工厂规格", "ptSpec", 100));
				list.add(new JTableListColumn("工厂单位", "ptUnit", 100));
				list.add(new JTableListColumn("仓库","warehouse",100));
				list.add(new JTableListColumn("备注","memo",200));
				list.add(new JTableListColumn("错误信息", "errorInfo", 320));
				
				return list;
			}
		});

		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (value != null && !"".equals(value)) {// 第一个属性列的值
					super.setBackground(new java.awt.Color(255, 255, 150));
				} else {
					if (row == table.getSelectedRow()) {
						setForeground(table.getSelectionForeground());
						setBackground(table.getSelectionBackground());
					} else {
						setForeground(table.getForeground());
						setBackground(table.getBackground());
					}
				}
				return this;
			}
		});
	}

	/**
	 * 
	 */
	private void generalSveEntity(/** ECSSection ecsSection **/
	List list) {
		if ("PnECSStockImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockImg ecs = new ECSStockImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockImgs(request, section, (List<ECSStockImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockOutFactoryImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockOutFactoryImg ecs = new ECSStockOutFactoryImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockOutFactoryImgs(request, section, (List<ECSStockOutFactoryImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockBuyImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockBuyImg ecs = new ECSStockBuyImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockBuyImgs(request, section, (List<ECSStockBuyImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockTravelingImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockTravelingImg ecs = new ECSStockTravelingImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockTravelingImgs(request, section, (List<ECSStockTravelingImg>) listData);
		}

		/**
		 * 【成品在途】库存
		 */
		if ("PnECSStockTravelingExgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockTravelingExg ecs = new ECSStockTravelingExg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setVersion(Integer.parseInt(lsResult2.get(i)[2] == null ? "0" : lsResult2.get(i)[2].toString()));
				ecs.setPtName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setPtUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setWarehouse(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setMemo(lsResult2.get(i)[7] == null ? "" :lsResult2.get(i)[7].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockTravelingExgs(request, section, (List<ECSStockTravelingExg>) listData);
		}
		
		/**
		 * 
		 */
		if ("PnECSStockProcessImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockProcessImg ecs = new ECSStockProcessImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockProcessImgs(request, section, (List<ECSStockProcessImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockExgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockExg ecs = new ECSStockExg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setVersion(Integer.parseInt(lsResult2.get(i)[2] == null ? "0" : lsResult2.get(i)[2].toString()));
				ecs.setPtName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setPtUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setWarehouse(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setMemo(lsResult2.get(i)[7] == null ? "" :lsResult2.get(i)[7].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockExgs(request, section, (List<ECSStockExg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockOutSendExgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockOutSendExg ecs = new ECSStockOutSendExg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setVersion(Integer.parseInt(lsResult2.get(i)[2] == null ? "0" : lsResult2.get(i)[2].toString()));
				ecs.setPtName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setPtUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setWarehouse(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setMemo(lsResult2.get(i)[7] == null ? "" :lsResult2.get(i)[7].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockOutSendExgs(request, section, (List<ECSStockOutSendExg>) listData);
		}

		/**
		 * 残次品库存-原材料
		 */
		if ("PnECSBadImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSBadImg ecs = new ECSBadImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSBadImgs(request, section, (List<ECSBadImg>) listData);
		}
		/**
		 * 在制品库存-原材料
		 */
		if ("PnECSFinishingImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSFinishingImg ecs = new ECSFinishingImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSFinishingImgs(request, section, (List<ECSFinishingImg>) listData);
		}
		/**
		 * 呆滞品库存-原材料
		 */
		if ("PnECSStagnateImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStagnateImg ecs = new ECSStagnateImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStagnateImgs(request, section, (List<ECSStagnateImg>) listData);
		}
		/**
		 * 外发库存-原材料
		 */
		if ("PnECSStockOutSendImgPt".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockOutSendImg ecs = new ECSStockOutSendImg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setPtName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setPtSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockOutSendImgs(request, section, (List<ECSStockOutSendImg>) listData);
		}
		
	}

	private JCheckBox getCbMax() {
		if (cbMax == null) {
			cbMax = new JCheckBox("<html>以电子账册成品单耗最大版本进行转换(勾选后当版本号<br/>为空，系统自动补充账册最大版本号导入)</html>");
			if(isImg){
				cbMax.setEnabled(false);
				cbMax.setVisible(false);
			}
		}
		return cbMax;
	}
	private JButton getBtnDLTpl() {
		if (btnDLTpl == null) {
			btnDLTpl = new JButton("下载导入模版");
			btnDLTpl.setPreferredSize(new Dimension(90, 30));
			btnDLTpl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FileUtils.exportTableToExcel(tableMode, getParent());
				}
			});
		}
		return btnDLTpl;
	}
}