package com.bestway.bcus.client.checkstock.transferanalyse;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffExg;
import com.bestway.bcus.checkstock.entity.ECSTransferDiffImg;
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
public class DgECSImport extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JButton btnInput = null;

	private JButton btnExit = null;
	/**
	 * 料件/成品
	 */
	private boolean isImg = true;
	/**编码级别导入**/
	private boolean isHsImport = false;

	/*** 繁转简 */
	@SuppressWarnings("rawtypes")
	private Hashtable gbHash = null; //

	/**
	 * 对应关系
	 */
	private Map<String, String[]> bcsMap;
	private List<String[]> lsResult;
	private List<String[]> lsResult2;
	private Map<String, Integer> bomMaxMap;
	private Map<String, Integer> bomExistsMap;

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

	private ECSCheckStockAction ecsCheckStockAction;

	private boolean isPass = true;

	private Object fmObj;

	public Object getFmObj() {
		return fmObj;
	}

	public void setFmObj(Object fmObj) {
		this.fmObj = fmObj;
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

	/**
	 * This method initializes
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public DgECSImport(boolean isImg,boolean isHsImport) {
		super();
		request = new Request(CommonVars.getCurrUser());
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		this.setImg(isImg);
		this.isHsImport = isHsImport;
		initialize();		
		initTable(Collections.EMPTY_LIST);		
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(923, 504));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("数据导入接口-->"+(isHsImport ? "编码级" : "料号级") +"-->"+(isImg ? "结转料件":"结转成品"));
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
	 * 初始化 归并关系数据
	 * @return
	 */
	private Map<String, String[]> initConditionList() {
		bcsMap = new HashMap<String, String[]>();
		if(isHsImport){	
			//初始化归并后的编码级数据
			if(isImg){
				bcsInnerMergeList = ecsCheckStockAction.findAllEdiMergeHsPartImg(request);
			}else{
				bcsInnerMergeList = ecsCheckStockAction.findAllEdiMergeHsPartExg(request);
			}
			for (MergeTemp mTemp : bcsInnerMergeList) {
				String[] strs = new String[] { mTemp.getHsName(), mTemp.getHsSpec(), mTemp.getHsUnit() };
				bcsMap.put(String.valueOf(mTemp.getSeqNum()), strs);
			}
		}else{
			//初始化归并前的料件级数据
			if (isImg) {
				bcsInnerMergeList = ecsCheckStockAction.findAllEdiMergePtPartImg(request);
			} else {
				bcsInnerMergeList = ecsCheckStockAction.findAllEdiMergePtPartExg(request);
			}		
			
			for (MergeTemp mTemp : bcsInnerMergeList) {
				String[] strs = new String[] { mTemp.getPtName(), mTemp.getPtSpec(), mTemp.getPtUnit() };
				bcsMap.put(mTemp.getPtNo(), strs);
			}
		}		
		return bcsMap;
	}
	/**
	 * 初始化Bom最大版本数据
	 * @return
	 */
	private Map<String, Integer> getBomMap() {
		List<Object[]> bomLs = null;
		if(isHsImport){
			//编码级别  查询结果[[备案序号,版本号]]
			bomLs = ecsCheckStockAction.findMaxEmsVersion(request); 			
		}else{		
			//料件级 查询结果[[料号,版本号]]
			bomLs = ecsCheckStockAction.findMaxVersion(request);
		}
		bomMaxMap = new HashMap<String, Integer>();
		for (Object[] o : bomLs) {						
			//料件级序号做主键
			bomMaxMap.put(o[0].toString(), (Integer) o[1]);			
		}
		return bomMaxMap;
	}
	/**
	 * 初始化Bom所有版本数据
	 * @return
	 */
	private Map<String, Integer> getExistsBomMap() {
		List<Object[]> bomList = null;
		if(isHsImport){
			//编码级 [[备案序号,版本号]]
			bomList = ecsCheckStockAction.findAllEmsVersion(request);
		}else{
			//料件级 [[料号,版本号]]
			bomList = ecsCheckStockAction.findAllVersion(request);
		}				
		bomExistsMap = new HashMap<String, Integer>();
		for (Object[] o : bomList) {			
			bomExistsMap.put(o[0].toString() +"#"+ (Integer) o[1], (Integer) o[1]);			
		}
		return bomExistsMap;
	}

	private List hsParseTxtFile(){		
		lsResult = new ArrayList();
		String suffix = getSuffix(file);		
		List<List> lines = null;
		if (suffix.equals("xls")) {	// 导入xls
			lines = FileReading.readExcel(file, 0, null);
		} else {	// 导入txt			
			lines = FileReading.readTxt(file, 0, "GBK");
		}
		String[] strs = null;
		// 行操作
		isPass = true;
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) 
				continue;			
			strs = new String[getColumns().size()]; 
			List line = lines.get(i);
			for (int j = 0; j < strs.length; j++) {
				CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【" + i + "】行,请稍等...");
				if(j < line.size()){
					Object obj = line.get(j);
					strs[j] = (obj == null ? "" : obj.toString().trim());
				}
			}
			// 繁简转换
			if (cbBig5ConvertToGB.isSelected()) {
				strs = changStrs(strs);
			}
			/**验证信息**/
			String errorinfo = hsVaild(strs);
			if(StringUtils.isNotEmpty(errorinfo)){		//设置错误信息
				isPass = false;
				strs[getColumns().size()-1] = errorinfo;
			}
			/****设置默认值**/
			hsSetValues(strs);
			lsResult.add(strs);
		}
		return lsResult;			
	}
	/**
	 * 编码级验证
	 * @param rowData
	 * @return 错误信息
	 */
	private String hsVaild(String[] rowData){
		String tmp = rowData[0];	//备案序号
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isEmpty(tmp) || !NumberUtils.isNumber(tmp)){
			sb.append("【备案序号】不能为空或不为整数!");
		}else{
			if(bcsMap.get(tmp) == null){
				sb.append("【备案序号】对应关系中不存在!");	
			}
		}
		if(isImg){
			tmp = rowData[4];	//已收货未转厂
			if(StringUtils.isNotEmpty(tmp) && !NumberUtils.isNumber(tmp)){
				sb.append("【已收货未转厂】不为数字!");	
			}
			String tmp2 = rowData[5];	//已转厂未收货
			if(StringUtils.isNotEmpty(tmp2) && !NumberUtils.isNumber(tmp2)){
				sb.append("【已转厂未收货】不为数字!");	
			}
			if(StringUtils.isEmpty(tmp) && StringUtils.isEmpty(tmp2)){
				sb.append("【已收货未转厂】、【已转厂未收货】不能同时为空!");
			}
			tmp = rowData[6];	//收货数量
			if(StringUtils.isNotEmpty(tmp) && !NumberUtils.isNumber(tmp)){
				sb.append("【收货数量】不为数字!");	
			}
			tmp = rowData[7];	//报关数量
			if(StringUtils.isNotEmpty(tmp) && !NumberUtils.isNumber(tmp)){
				sb.append("【报关数量】不为数字!");	
			}
		}else{
			String seqNum = rowData[0];	//备案序号
			tmp = rowData[4];	//版本号
			if(StringUtils.isEmpty(tmp)){
				if(cbMax.isSelected()){
					if(StringUtils.isNotEmpty(seqNum)){
						if(bomMaxMap.get(seqNum) == null){
							sb.append("【版本号】找不到最大版本!");
						}
					}
				}else{
					sb.append("【版本号】不能为空!");
				}
			}else{				
				if(StringUtils.isNotEmpty(seqNum)){
					if(bomExistsMap.get(seqNum+"#"+tmp) == null){
						sb.append("【版本号】不存在!");
					}
				}
			}
			tmp = rowData[5];	//已送货未转厂
			if(StringUtils.isNotEmpty(tmp) && !NumberUtils.isNumber(tmp)){
				sb.append("【已送货未转厂】不为数字!");	
			}
			String tmp2 = rowData[6];	//已转厂未送货
			if(StringUtils.isNotEmpty(tmp2) && !NumberUtils.isNumber(tmp2)){
				sb.append("【已转厂未送货】不为数字!");	
			}
			if(StringUtils.isEmpty(tmp) && StringUtils.isEmpty(tmp2)){
				sb.append("【已送货未转厂】、【已转厂未送货】不能同时为空!");
			}
			tmp = rowData[7];	//收货数量
			if(StringUtils.isNotEmpty(tmp) && !NumberUtils.isNumber(tmp)){
				sb.append("【送货数量】不为数字!");	
			}
			tmp = rowData[8];	//报关数量
			if(StringUtils.isNotEmpty(tmp) && !NumberUtils.isNumber(tmp)){
				sb.append("【报关数量】不为数字!");
			}
		}
		return sb.toString();
	}
	/**
	 * 编码级设置数据默认值
	 * @param rowData
	 * @return 错误信息
	 */
	private void hsSetValues(String[] rowData){
		String seqNum = rowData[0];	//备案序号
		if(StringUtils.isNotEmpty(seqNum)){
			String[] infos = bcsMap.get(seqNum);		//信息
			if(infos != null){
				rowData[1] = infos[0];		//报关商品名称
				rowData[2] = infos[1];		//报关商品规格
				rowData[3] = infos[2];		//计量单位
			}
			if(!isImg && cbMax.isSelected()){
				String ver =  rowData[4];	//版本号
				//设置默认最大版本号
				if(StringUtils.isEmpty(ver)){	
					Integer maxVer = bomMaxMap.get(seqNum);
					if(maxVer != null){
						rowData[4] = String.valueOf(maxVer);
					}
				}				
			}
			int idx = isImg ? 4 : 5;		//开始下标
			//设置默认值 数字
			for(int i = idx ; i < rowData.length-1;i++){
				if(StringUtils.isEmpty(rowData[i])){
					rowData[i] =String.valueOf(0);
				}
			}
		}
	}
	
	/**
	 * 解析导入文件(料件级)
	 * 
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	private List ptParseTxtFile() {
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

		// 行操作
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}

			List line = lines.get(i);
			String[] strs = null;
			if (line.size() <= 6) {
				strs = new String[7 + 1];
			} else {
				strs = new String[line.size() + 1];
			}

			// 列操作
			String[] bcsStrs = null;
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < strs.length; j++) {
				if(j < line.size()){
					Object obj = line.get(j);
					strs[j] = (obj == null ? "" : obj.toString().trim());
				}
				// 繁简转换
				if (cbBig5ConvertToGB.isSelected()) {
					strs = changStrs(strs);
				}
				CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【" + i + "】行,请稍等...");

				if (j == 0) {
					if (strs[0] == null || "".equals(strs[0])) {
						sb.append("料号不能为空 ");
					} else {
						bcsStrs = bcsMap.get(strs[0]);
						if (bcsStrs == null) {
							sb.append(" 料号在归并关系中不存在 ");
						}
					}
				} else if (j == 2) {
					if (("".equals(strs[2]) || strs[2] == null)
							&& ("".equals(strs[1]) || strs[1] == null)) {
						sb.append(" 数量不能为空 ");
					} else {
						if (("".equals(strs[2]) || strs[2] == null)) {
							strs[2] = "0";
						}

						if (("".equals(strs[1]) || strs[1] == null)) {
							strs[1] = "0";
						}
					}
				} else if (j == 3 && !isImg) {
					if (strs[3] == null || "".equals(strs[3].toString())) {
						if (cbMax.isSelected()) {
							Integer ver = bomMaxMap.get(strs[0]);
							if(ver == null){
								sb.append("  没有找到最大版本号  ");
							} else {
								strs[3] = String.valueOf(ver);
							}
						} else {
							sb.append("  版本号不能为空  ");
						}
					} else {
						if (!RegexUtil.checkFloat(strs[3].toString())) {
							sb.append("  版本号类型不对  ");
						} else {
							if (bomExistsMap.get(strs[0] +"#"+ strs[3]) == null) {
								sb.append("  版本在归并前BOM或者账册单耗中不存在  ");
							}
						}
					}
				}
			}
			String errorInfo = sb.toString() == null ? "" : sb.toString();
			if (errorInfo != null && errorInfo.length() > 2) {
				isPass = false;
				if (isImg) {
					strs[6] = errorInfo;
				} else {
					strs[7] = errorInfo;
				}
			}

			if (bcsStrs != null) {
				if (isImg) {
					strs[3] = bcsStrs[0];
					strs[4] = bcsStrs[1];
					strs[5] = bcsStrs[2];
				} else {
					strs[4] = bcsStrs[0];
					strs[5] = bcsStrs[1];
					strs[6] = bcsStrs[2];
					// strs[3] = bcsStrs[2];//version
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
			btnExit.setText("退出");
			btnExit.setPreferredSize(new Dimension(40, 30));
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
		
		protected Object doInBackground() throws Exception {
			List list = null;
			try {
				CommonProgress.showProgressDialog(DgECSImport.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");	
				long t = System.currentTimeMillis();
				initConditionList();// 初始化查询条件
				System.out.println("初始化归并关系用时:" + (System.currentTimeMillis()-t)+"ms");
				t = System.currentTimeMillis();
				if(!isImg){	
					//成品时，加载最大版本 以及存在的所有版本
					if(cbMax.isSelected()){
						getBomMap();
						System.out.println("初始化最大版本用时:" + (System.currentTimeMillis()-t)+"ms");
						t = System.currentTimeMillis();
					}
					getExistsBomMap();
					System.out.println("初始化所有版本用时:" + (System.currentTimeMillis()-t)+"ms");
					t = System.currentTimeMillis();
				}				
				if(isHsImport){					
					list = hsParseTxtFile();
				}else{					
					list = ptParseTxtFile();
				}
				System.out.println("解析以及验证用时:" + (System.currentTimeMillis()-t)+"ms");
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgECSImport.this, "导入数据失败：！" + e.getMessage(), "提示",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return list;
		}

		@Override
		protected void done() {
			super.done();
			try {
				initTable((List) this.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
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

			jToolBar.add(getBtnExit());
			jToolBar.add(getBtnDLTpl());
			jToolBar.add(getCbBig5ConvertToGB());
			jToolBar.add(getCbCheckTitle());
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
			btnInput.setPreferredSize(new Dimension(80, 30));
			btnInput.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser.showOpenDialog(DgECSImport.this);
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
			btnDel.setPreferredSize(new Dimension(80, 30));
			btnDel.setText("删除错误");
			btnDel.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent e) {
					int len = getColumns().size();
					if(lsResult != null){
						lsResult2 = new ArrayList<String[]>();
						for (int i = 0; i < lsResult.size(); i++) {
							String error = lsResult.get(i)[len - 1] == null ? null : lsResult.get(i)[len - 1].toString();
							if (error == null || "".equals(error)) {
								lsResult2.add(lsResult.get(i));
							}
						}
							initTable(lsResult2);
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
			btnSaveData.setPreferredSize(new Dimension(80, 30));
			btnSaveData.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!isPass) {
						JOptionPane.showMessageDialog(DgECSImport.this, "有错误信息，不可保存！", "提示!", 2);						
						return;
					}

					if (tableMode.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgECSImport.this, "无数据可保存", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					// **导入后保存的操作
					if(isHsImport){
						hsSaveEntity(lsResult2);
					}else{
						ptSaveEntity(lsResult2);
					}
					JOptionPane.showMessageDialog(DgECSImport.this, "保存数据成功！", "提示!", JOptionPane.INFORMATION_MESSAGE);
					DgECSImport.this.dispose();
				}
			});
		}
		return btnSaveData;
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
	private void initTable(List list) {
		initTable(list, getColumns());
	}
	private List<JTableListColumn> columns = null;
	private JButton btnDLTpl;
	private List<JTableListColumn> getColumns(){
		if(columns == null){
			columns = new ArrayList<JTableListColumn>();
			if(isHsImport){			/***************编码级别表格列*************/
				if(isImg){	//料件
					columns.add(new JTableListColumn("备案序号（必填）", "seqNum", 110));
					columns.add(new JTableListColumn("报关商品名称", "hsName", 100));
					columns.add(new JTableListColumn("报关商品规格", "hsSpec", 120));
					columns.add(new JTableListColumn("计量单位", "hsUnit", 80));
					columns.add(new JTableListColumn("已收货未转厂（必填）", "hsUnTransferNum", 120));
					columns.add(new JTableListColumn("已转厂未收货（必填）", "hsUnSendferNum", 120));
					columns.add(new JTableListColumn("收货数量", "hsBillNum", 100));
					columns.add(new JTableListColumn("报关数量", "hsCustomsNum", 100));
				}else{	//成品
					columns.add(new JTableListColumn("备案序号（必填）", "seqNum", 110));
					columns.add(new JTableListColumn("报关商品名称", "hsName", 120));
					columns.add(new JTableListColumn("报关商品规格", "hsSpec", 120));
					columns.add(new JTableListColumn("计量单位", "hsUnit", 50));
					columns.add(new JTableListColumn("版本号", "version", 110));
					columns.add(new JTableListColumn("已送货未转厂（必填）", "hsUnTransferNum", 120));
					columns.add(new JTableListColumn("已转厂未送货（必填）", "hsUnSendferNum", 120));
					columns.add(new JTableListColumn("送货数量", "hsBillNum", 100));
					columns.add(new JTableListColumn("报关数量", "hsCustomsNum", 100));
				}
			}else{		/***************料件级别表格列*************/
				if(isImg){	//料件
					columns.add(new JTableListColumn("料号（必填）", "ptNo", 100));
					columns.add(new JTableListColumn("已收货未转厂（必填）", "ptQty", 120));
					columns.add(new JTableListColumn("已转厂未收货（必填）", "version", 120));
					columns.add(new JTableListColumn("工厂名称", "ptName", 140));
					columns.add(new JTableListColumn("工厂规格", "Speec", 100));
					columns.add(new JTableListColumn("工厂单位", "Unit", 100));
				}else{		//成品
					columns.add(new JTableListColumn("料号（必填）", "ptNo", 100));
					columns.add(new JTableListColumn("已送货未转厂（必填）", "ptQty", 120));
					columns.add(new JTableListColumn("已转厂未送货（必填）", "", 120));
					columns.add(new JTableListColumn("版本号（必填）", "version", 90));
					columns.add(new JTableListColumn("工厂名称", "ptName", 140));
					columns.add(new JTableListColumn("工厂规格", "Speec", 100));
					columns.add(new JTableListColumn("工厂单位", "Unit", 100));
				}
			}
			columns.add(new JTableListColumn("错误信息", "errorInfo", 320));
		}
		return columns;
	}
	
	private void initTable(List<?> data,final List<JTableListColumn> columns){
		tableMode = new JTableListModel(jTable, data, new JTableListModelAdapter() {
			public List InitColumns() {				
				return columns;
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
	 * 编码级保存数据
	 * @param list
	 */
	private void hsSaveEntity(List<String[]> list) {
		if (lsResult2 == null)
			lsResult2 = lsResult;
		/**料件*/
		if (this.fmObj instanceof FmECSTransferImgBalance) {
			List<ECSTransferDiffImg> listECS = new ArrayList<ECSTransferDiffImg>();
			String[] row = null;
			ECSTransferDiffImg ecs = null;
			for (int i = 0; i < lsResult2.size(); i++) {
				row  = lsResult2.get(i);
				ecs = new ECSTransferDiffImg();			
				ecs.init();
				ecs.setSeqNum(NumberUtils.toInt(row[0]));
				ecs.setHsName(row[1]);
				ecs.setHsSpec(row[2]);
				ecs.setHsUnit(row[3]);
				ecs.setHsUnTransferNum(NumberUtils.toDouble(row[4]));
				ecs.setHsUnSendferNum(NumberUtils.toDouble(row[5]));
				ecs.setHsBillNum(NumberUtils.toDouble(row[6]));
				ecs.setHsCustomsNum(NumberUtils.toDouble(row[7]));
				listECS.add(ecs);
			}
			//先删除之前数据
			ecsCheckStockAction.deleteECSTransferDiffImgs(request, ((FmECSTransferImgBalance) this.fmObj).getEcsSection());
			//后保存数据
			ecsCheckStockAction.saveECSTransferDiffImgs(request,((FmECSTransferImgBalance) this.fmObj).getEcsSection(), listECS);
			((FmECSTransferImgBalance) this.fmObj).initTable(listECS);
		}
		/**成品*/
		if (this.fmObj instanceof FmECSTransferExgBalance) {
			List<ECSTransferDiffExg> listECS = new ArrayList<ECSTransferDiffExg>();						
			String[] row = null;
			ECSTransferDiffExg ecs = null;
			for (int i = 0; i < lsResult2.size(); i++) {
				row  = lsResult2.get(i);
				ecs = new ECSTransferDiffExg();				
				ecs.init();
				ecs.setSeqNum(NumberUtils.toInt(row[0]));
				ecs.setHsName(row[1]);
				ecs.setHsSpec(row[2]);
				ecs.setHsUnit(row[3]);
				ecs.setVersion(NumberUtils.toInt(row[4]));
				ecs.setHsUnTransferNum(NumberUtils.toDouble(row[5]));
				ecs.setHsUnSendferNum(NumberUtils.toDouble(row[6]));
				ecs.setHsBillNum(NumberUtils.toDouble(row[7]));
				ecs.setHsCustomsNum(NumberUtils.toDouble(row[8]));
				listECS.add(ecs);
			}
			//先保存
			ecsCheckStockAction.deleteECSTransferDiffExgs(request, ((FmECSTransferExgBalance) this.fmObj).getEcsSection());
			//后保存
			ecsCheckStockAction.saveECSTransferDiffExgs(request,((FmECSTransferExgBalance) this.fmObj).getEcsSection(), listECS);
			((FmECSTransferExgBalance) this.fmObj).initJtableP(listECS);
		}
	}
	
	/**
	 * 料件级保存数据
	 * */
	private void ptSaveEntity(List list) {
		if (lsResult2 == null)
			lsResult2 = lsResult;
		/**料件*/
		if (this.fmObj instanceof FmECSTransferImgBalance) {
			List<ECSTransferDiffImg> listECS = new ArrayList<ECSTransferDiffImg>();
			
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSTransferDiffImg ecs = new ECSTransferDiffImg();				
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtUnTransferNum(Double.parseDouble(lsResult2.get(i)[1] == null || "".equals(lsResult2.get(i)[1]) ? "0"
						: lsResult2.get(i)[1].toString()));
				ecs.setPtUnSendferNum(Double.parseDouble(lsResult2.get(i)[2] == null || "".equals(lsResult2.get(i)[2]) ? "0"
						: lsResult2.get(i)[2].toString())); // 测试
				ecs.setPtName(lsResult2.get(i)[3] == null ? "" :lsResult2.get(i)[3].toString());
				ecs.setPtSpec(lsResult2.get(i)[4] == null ? "" :lsResult2.get(i)[4].toString());
				ecs.setPtUnit(lsResult2.get(i)[5] == null ? "" :lsResult2.get(i)[5].toString());
				listECS.add(ecs);
			}
			//先删除之前数据
			ecsCheckStockAction.deleteECSTransferDiffImgs(request, ((FmECSTransferImgBalance) this.fmObj).getEcsSection());
			//后保存数据
			ecsCheckStockAction.saveECSTransferDiffImgs(request,((FmECSTransferImgBalance) this.fmObj).getEcsSection(), listECS);
			((FmECSTransferImgBalance) this.fmObj).initTable(listECS);
		}
		/**成品*/
		if (this.fmObj instanceof FmECSTransferExgBalance) {
			List<ECSTransferDiffExg> listECS = new ArrayList<ECSTransferDiffExg>();						
			for (int i = 0; i < lsResult2.size(); i++) {
				ECSTransferDiffExg ecs = new ECSTransferDiffExg();
				ecs.setPtNo(lsResult2.get(i)[0].toString());
				ecs.setPtUnTransferNum(Double.parseDouble(lsResult2.get(i)[1] == null || "".equals(lsResult2.get(i)[1]) ? "0"
						: lsResult2.get(i)[1].toString()));
				ecs.setPtUnSendferNum(Double.parseDouble(lsResult2.get(i)[2] == null || "".equals(lsResult2.get(i)[2]) ? "0"
						: lsResult2.get(i)[2].toString()));
				ecs.setVersion(Integer.parseInt(lsResult2.get(i)[3].toString()));
				ecs.setPtName(lsResult2.get(i)[4] == null ? "":lsResult2.get(i)[4].toString());
				ecs.setPtSpec(lsResult2.get(i)[5] == null ? "":lsResult2.get(i)[5].toString());
				ecs.setPtUnit(lsResult2.get(i)[6] == null ? "":lsResult2.get(i)[6].toString());
				listECS.add(ecs);
			}
			//先保存
			ecsCheckStockAction.deleteECSTransferDiffExgs(request, ((FmECSTransferExgBalance) this.fmObj).getEcsSection());
			//后保存
			ecsCheckStockAction.saveECSTransferDiffExgs(request,((FmECSTransferExgBalance) this.fmObj).getEcsSection(), listECS);
			((FmECSTransferExgBalance) this.fmObj).initJtableP(listECS);
		}
	}

	public boolean isImg() {
		return isImg;
	}

	public void setImg(boolean isImg) {
		this.isImg = isImg;
	}

	private JCheckBox getCbMax() {
		if (cbMax == null) {
			cbMax = new JCheckBox("<html>以电子账册成品单耗最大版本进行转换(勾选后当版本号<br/>为空，系统自动补充账册最大版本号导入)</html>");
			if (isImg) {
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
					FileUtils.exportTableToExcel(tableMode, DgECSImport.this);
				}
			});
		}
		return btnDLTpl;
	}
}