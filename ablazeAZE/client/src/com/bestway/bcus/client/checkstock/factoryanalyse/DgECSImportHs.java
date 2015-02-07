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
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import com.bestway.bcus.checkstock.entity.ECSStockExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutFactoryImg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendImg;
import com.bestway.bcus.checkstock.entity.ECSStockProcessImg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExg;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockTravelingImg;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kExg;
import com.bestway.bcus.manualdeclare.entity.EmsHeadH2kImg;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.FileUtils;
import com.bestway.util.RegexUtil;

@SuppressWarnings("unchecked")
public class DgECSImportHs<T> extends JDialogBase {

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
	private Map<Integer, EmsHeadH2kImg> imgMap;  //  @jve:decl-index=0:
	private List<String[]> lsResult;  //  @jve:decl-index=0:
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

	private List<EmsHeadH2kImg> bcsInnerMergeListImg;
	
	private Map<String,Integer> bomMaxMap;  //  @jve:decl-index=0:

	private Map<String,Integer> bomExistsMap;  //  @jve:decl-index=0:
	
	private ECSCheckStockAction ecsCheckStockAction;
	
	private boolean isPass = true;

	private String clazz;  //  @jve:decl-index=0:
	
	private List<T> listData;
	
	private ECSSection section;

	private JTableListModel tableMode;
	private JCheckBox cbMax;
	
	public ECSSection geteSection() {
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

	private List<EmsHeadH2kExg> bcsInnerMergeListExg;

	private HashMap<Integer, EmsHeadH2kExg> exgMap;
	private JButton btnDLTpl;
	private JPanel panel;
	private JPanel pnHsImport;
	private JLabel lblNewLabel;
	private JRadioButton rbImportExgNo;
	private JRadioButton rbImportImgNo;
	private ButtonGroup bGroup;
	private Boolean hsImportExgNo = Boolean.TRUE; //编码级>>>导入类型

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public Boolean getHsImportExgNo() {
		return hsImportExgNo;
	}

	public void setHsImportExgNo(Boolean hsImportExgNo) {
		this.hsImportExgNo = hsImportExgNo;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgECSImportHs(boolean isImg) {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		this.isImg = isImg;
		initialize();
	}
	public void setVisible(boolean isFlag) {
		if (isFlag) {
			setPnHsImport();
			if (isImg) {
				initTableImg(Collections.EMPTY_LIST);
			} else {
				initTableExg(Collections.EMPTY_LIST);
			}
		}
		super.setVisible(isFlag);
	}
	/**
	 *是否显示导入类型面板
	 */
	private void setPnHsImport(){
		if(clazz.equals("PnECSStockTravelingExgHs")||clazz.equals("PnECSStockExgHs")){
			this.pnHsImport.setVisible(true);
		}else{
			this.pnHsImport.setVisible(false);
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
		this.setTitle("数据导入接口 -> 编码级 -> "+((isImg)?"料件":"成品"));
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
			jPanel.add(getPanel(), BorderLayout.NORTH);
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jPanel;
	}

	/**
	 * 
	 * @return
	 */
	private void initConditionList() {
		if (isImg||!hsImportExgNo) {
			imgMap = new HashMap<Integer, EmsHeadH2kImg>();
			bcsInnerMergeListImg = ecsCheckStockAction.findEmsHeadH2kImg(request,section);
			for (EmsHeadH2kImg mTemp : bcsInnerMergeListImg) {
				imgMap.put(mTemp.getSeqNum(), mTemp);
			}
		} else {
			exgMap = new HashMap<Integer, EmsHeadH2kExg>();
			bcsInnerMergeListExg = ecsCheckStockAction.findEmsHeadH2kExg(request,section);
			for (EmsHeadH2kExg mTemp : bcsInnerMergeListExg) {
				exgMap.put(mTemp.getSeqNum(), mTemp);
			}
		}
	}

	
	private Map<String, Integer> getBomMaxMap() {
		List<Object[]> bomList = ecsCheckStockAction.findMaxEmsVersion(request);
		bomMaxMap = new HashMap<String, Integer>();
		for(Object[] o:bomList){
			bomMaxMap.put(o[0].toString(), (Integer)o[1]);
		}
		return bomMaxMap;
	}
	
	
	private Map<String, Integer> getExistsBomMap() {
		List<Object[]> bomList = ecsCheckStockAction.findAllEmsVersion(request);
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
			EmsHeadH2kImg imgs = null;
			EmsHeadH2kExg exgs = null;
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
						sb.append(" 备案序号不允许为空 ");
					} else {
						if (isImg||!hsImportExgNo) {
							imgs = imgMap.get(Integer.parseInt(strs[0].toString()));
							if (imgs == null) {
								sb.append("  料件备案序号不存在 ");
							}
						}else {
							exgs = exgMap.get(Integer.parseInt(strs[0].toString()));
							if (exgs == null) {
								sb.append("  成品备案序号不存在 ");
							}
						}
					}
				} else if (j == 1) {
					if ("".equals(strs[1].toString()) || strs[1] == null) {
						sb.append("  数量不能为空 ");
					} else if (!RegexUtil.checkFloat(strs[1].toString())) {
						sb.append("  数量类型不对 ");
					}
				} else if (j == 2 && !isImg) {
					if(hsImportExgNo){
						if (strs[2] == null || "".equals(strs[2].toString())) {
							if(cbMax.isSelected()){
								String ver = String.valueOf(bomMaxMap.get(strs[0]));
								if(ver == null || "null".equals(ver)){
									sb.append("  在账册单耗中没有找到最大版本号 ");
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
									sb.append("  版本号在账册单耗中不存在");
								}
							}
						}
					}
				}
			}

			if (imgs != null || exgs != null) {
				if (isImg||!hsImportExgNo) {
					strs[2] = imgs.getName();
					strs[3] = imgs.getSpec();
					strs[4] = imgs.getUnit().getName();
				} else {
					strs[3] = exgs.getName();
					strs[4] = exgs.getSpec();
					strs[5] = exgs.getUnit().getName();
					//strs[2] = bcsStrs[2];//version
				}
			}
			String errorInfo = sb.toString() == null ? "" : sb.toString();
			if (errorInfo != null && errorInfo.length() > 2) {
				isPass = false;
				if (isImg||!hsImportExgNo) {
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
			List<Object> list = new ArrayList<Object>();
			try {
				CommonProgress.showProgressDialog(DgECSImportHs.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				// System.out.println("Exgs >>> >>> >>>"+bcsInnerMergeList.size());
				initConditionList();// 初始化查询条件
				getBomMaxMap();
				getExistsBomMap();
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				list = parseTxtFile();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgECSImportHs.this, "导入数据失败：！" + e.getMessage(), "提示",
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
			List<Object> list = new ArrayList<Object>();
			try {
				list =  (List<Object>) this.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if (isImg) {
				initTableImg(list);
			} else if(!isImg&&hsImportExgNo){
				initTableExg(list);
			}else{
				initTableHsImg(list);
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
			jToolBar.setPreferredSize(new Dimension(20, 45));
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
					int state = fileChooser.showOpenDialog(DgECSImportHs.this);
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
						if (!isImg&&(!isImg&&hsImportExgNo)) {
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
						} else if(!isImg&&hsImportExgNo){
							initTableExg(lsResult2);
						}else{
							initTableHsImg(lsResult2);
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
						JOptionPane.showMessageDialog(DgECSImportHs.this, "有错误信息，不可保存！", "提示!", 2);
						File uploaderrdir = new File(uploaderr);
						File uploaderrdirfile = new File(uploaderr + File.separator + file.getName());
						if (uploaderrdir.exists()) {
							moveFile(file, uploaderrdirfile);
						}
						return;
					}

					if (tableMode.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgECSImportHs.this, "无数据可保存", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// **导入后保存的操作
					DgECSImportHs.this.generalSveEntity(lsResult2);
					JOptionPane.showMessageDialog(DgECSImportHs.this, "保存数据成功！", "提示!", JOptionPane.INFORMATION_MESSAGE);
					DgECSImportHs.this.dispose();

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
				list.add(new JTableListColumn("备案序号（必填）", "No", 100));
				list.add(new JTableListColumn("库存数据（必填）", "Qty", 100));
				list.add(new JTableListColumn("版本号（必填）", "version", 100));
				list.add(new JTableListColumn("报关名称", "Name", 140));
				list.add(new JTableListColumn("报关规格", "Spec", 100));
				list.add(new JTableListColumn("计量单位", "Unit", 100));
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
	 * 初始化表格
	 * 
	 * @param list
	 */
	private void initTableImg(List list) {
		tableMode = new JTableListModel(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("备案序号（必填）", "ptNo", 100));
				list.add(new JTableListColumn("库存数据（必填）", "ptQty", 100));
				list.add(new JTableListColumn("报关名称", "ptName", 140));
				list.add(new JTableListColumn("报关规格", "ptSpec", 100));
				list.add(new JTableListColumn("计量单位", "ptUnit", 100));
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
	 * 初始化表格编码级>>>料件项号导入
	 * 
	 * @param list
	 */
	private void initTableHsImg(List list) {
		tableMode = new JTableListModel(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("备案序号（必填）", "seqNum", 100));
				list.add(new JTableListColumn("库存数量（必填）", "hsAmount", 100));
				list.add(new JTableListColumn("报关名称", "hsName", 100));
				list.add(new JTableListColumn("报关规格", "hsSpec", 140));
				list.add(new JTableListColumn("计量单位", "hsUnit", 100));
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
		if ("PnECSStockImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockImg ecs = new ECSStockImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0d);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockImgs(request, section, (List<ECSStockImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockOutFactoryImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockOutFactoryImg ecs = new ECSStockOutFactoryImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0d);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockOutFactoryImgs(request, section, (List<ECSStockOutFactoryImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockBuyImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockBuyImg ecs = new ECSStockBuyImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0d);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockBuyImgs(request, section, (List<ECSStockBuyImg>) listData);
		}

		/**
		 * 
		 */
		if ("PnECSStockTravelingImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockTravelingImg ecs = new ECSStockTravelingImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0d);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockTravelingImgs(request, section, (List<ECSStockTravelingImg>) listData);
		}

		/**
		 * 【成品在途】库存
		 */
		if ("PnECSStockTravelingExgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			if(hsImportExgNo){//编码级成品>>>成品项号导入保存
				for (int i = 0; i < lsResult2.size(); i++) {
					ECSStockTravelingExg ecs = new ECSStockTravelingExg();
					ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
					ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
					ecs.setVersion(Integer.parseInt(lsResult2.get(i)[2] == null ? "0" : lsResult2.get(i)[2].toString()));
					ecs.setHsName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
					ecs.setHsSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
					ecs.setHsUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
					ecs.setWarehouse(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
					ecs.setMemo(lsResult2.get(i)[7] == null ? "" :lsResult2.get(i)[7].toString());
					ecs.setPtAmount(0d);
					listData.add((T) ecs);
				}
				ecsCheckStockAction.saveECSStockTravelingExgs(request, section, (List<ECSStockTravelingExg>) listData);
			}else{
				 //编码级成品>>>料件项号导入保存
				for (int i = 0; i < lsResult2.size(); i++) {
					ECSStockTravelingExgResolve ecsResolve = new ECSStockTravelingExgResolve();
					ecsResolve.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
					ecsResolve.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
					ecsResolve.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
					ecsResolve.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
					ecsResolve.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
					ecsResolve.setUnitUsed(0.0);
					ecsResolve.setUnitWaste(0.0);
					ecsResolve.setWaste(0.0);
					listData.add((T) ecsResolve);
				}
				ecsCheckStockAction.saveECSStockTravelingExgResolves(request, section, (List<ECSStockTravelingExgResolve>) listData);
			}
		}
		
		/**
		 * 
		 */
		if ("PnECSStockProcessImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockProcessImg ecs = new ECSStockProcessImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0d);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockProcessImgs(request, section, (List<ECSStockProcessImg>) listData);
		}

		/**
		 * 编码级成品保存
		 */
		if ("PnECSStockExgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			if(hsImportExgNo){
				for (int i = 0; i < lsResult2.size(); i++) {//编码级成品>>>成品项号导入保存
					ECSStockExg ecs = new ECSStockExg();
					ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
					ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
					ecs.setVersion(Integer.parseInt(lsResult2.get(i)[2] == null ? "0" : lsResult2.get(i)[2].toString()));
					ecs.setHsName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
					ecs.setHsSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
					ecs.setHsUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
					ecs.setWarehouse(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
					ecs.setMemo(lsResult2.get(i)[7] == null ? "" :lsResult2.get(i)[7].toString());
					ecs.setPtAmount(0.0);
					listData.add((T) ecs);
				}
				ecsCheckStockAction.saveECSStockExgs(request, section, (List<ECSStockExg>) listData);
			}else{ //编码级成品>>>料件项号导入保存
				for (int i = 0; i < lsResult2.size(); i++) {
					ECSStockExgResolve ecsResolve = new ECSStockExgResolve();
					ecsResolve.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
					ecsResolve.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
					ecsResolve.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
					ecsResolve.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
					ecsResolve.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
					ecsResolve.setUnitUsed(0.0);
					ecsResolve.setUnitWaste(0.0);
					ecsResolve.setWaste(0.0);
					listData.add((T) ecsResolve);
				}
				ecsCheckStockAction.saveECSStockExgResolves(request, section, (List<ECSStockExgResolve>) listData);
			}
			
		}

		/**
		 * 编码级外发库存
		 */
		if ("PnECSStockOutSendExgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockOutSendExg ecs = new ECSStockOutSendExg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setVersion(Integer.parseInt(lsResult2.get(i)[2] == null ? "0" : lsResult2.get(i)[2].toString()));
				ecs.setHsName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setHsUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setWarehouse(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setMemo(lsResult2.get(i)[7] == null ? "" :lsResult2.get(i)[7].toString());
				ecs.setPtAmount(0.0);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockOutSendExgs(request, section, (List<ECSStockOutSendExg>) listData);
		}
		
		/**
		 * 外发库存-原材料
		 */
		if ("PnECSStockOutSendImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStockOutSendImg ecs = new ECSStockOutSendImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0.0);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStockOutSendImgs(request, section, (List<ECSStockOutSendImg>) listData);
		}
		/**
		 * 残次品库存-原材料
		 */
		if ("PnECSBadImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSBadImg ecs = new ECSBadImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0.0);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSBadImgs(request, section, (List<ECSBadImg>) listData);
		}
		/**
		 * 在制品库存-原材料
		 */
		if ("PnECSFinishingImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSFinishingImg ecs = new ECSFinishingImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0.0);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSFinishingImgs(request, section, (List<ECSFinishingImg>) listData);
		}
		/**
		 * 呆滞品库存-原材料
		 */
		if ("PnECSStagnateImgHs".equals(this.clazz)) {
			if (lsResult2 == null)
				lsResult2 = lsResult;
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSStagnateImg ecs = new ECSStagnateImg();
				ecs.setSeqNum(Integer.parseInt(lsResult2.get(i)[0].toString()));
				ecs.setHsAmount(Double.parseDouble(lsResult2.get(i)[1] == null ? "0" : lsResult2.get(i)[1].toString()));
				ecs.setHsName(lsResult2.get(i)[2] == null ? "" :lsResult2.get(i)[2].toString());
				ecs.setHsSpec(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setHsUnit(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setWarehouse(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				ecs.setMemo(lsResult2.get(i)[6] == null ? "" :lsResult2.get(i)[6].toString());
				ecs.setPtAmount(0.0);
				listData.add((T) ecs);
			}
			ecsCheckStockAction.saveECSStagnateImgs(request, section, (List<ECSStagnateImg>) listData);
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
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(getJToolBar(), BorderLayout.CENTER);
			panel.add(getPnHsImport(), BorderLayout.SOUTH);
		}
		return panel;
	}
	private JPanel getPnHsImport() {
		if (pnHsImport == null) {
			pnHsImport = new JPanel();
			pnHsImport.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			pnHsImport.add(getLblNewLabel());
			pnHsImport.add(getRbImportExgNo());
			pnHsImport.add(getRbImportImgNo());
			bGroup = new ButtonGroup();
			bGroup.add(rbImportExgNo);
			bGroup.add(rbImportImgNo);
		}
		return pnHsImport;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("导入类型选择：");
		}
		return lblNewLabel;
	}
	private JRadioButton getRbImportExgNo() {
		if (rbImportExgNo == null) {
			rbImportExgNo = new JRadioButton("成品项号");
			rbImportExgNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hsImportExgNo =Boolean.TRUE;
					cbMax.setEnabled(true);
					initTableExg(Collections.EMPTY_LIST);
				}
			});
			rbImportExgNo.setSelected(true);
		}
		return rbImportExgNo;
	}
	private JRadioButton getRbImportImgNo() {
		if (rbImportImgNo == null) {
			rbImportImgNo = new JRadioButton("料件项号");
			rbImportImgNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					hsImportExgNo =Boolean.FALSE;
					cbMax.setEnabled(false);
					initTableHsImg(Collections.EMPTY_LIST);
				}
			});
		}
		return rbImportImgNo;
	}
}