package com.bestway.bcs.client.verification.factoryanalyse;

/**
 * 
 * 刘民添加部分注释 修改时间: 2008-11-24
 * 
 * @author ? // change the template for this generated type comment go to Window
 *         - Preferences - Java - Code Style - Code Templates 进出口申请单数据导入接口
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFStockBuyImg;
import com.bestway.bcs.verification.entity.VFStockTravelingImg;
import com.bestway.bcs.verification.entity.temp.VFBaseStockExgTemp;
import com.bestway.bcs.verification.entity.temp.VFBaseStockImgTemp;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.bcus.enc.action.EncAction;
import com.bestway.bcus.enc.entity.TempImpExpRequestBillForInput;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ProjectType;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.FileUtils;
import com.bestway.util.RegexUtil;
import java.awt.event.ActionListener;

@SuppressWarnings("unchecked")
public class DgVFStockTravelingImg extends JDialogBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jPanel = null;

	private JButton btnInput = null;

	private JButton btnExit = null;

	/** 退出 */
	private boolean isInport = false;
	/** 单据类型 */
	private int billType = -1;

	/*** 繁转简 */
	private Hashtable gbHash = null; //

	/**
	 * 对应关系
	 */
	private Map<String, String[]> bcsMap;
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

	private List<Object> bcsInnerMergeList;

	private VFVerificationAction vfAction;

	private boolean isPass = true;

	private VFSection vfSection;

	private Request request;
	
	private FmVFStockTravelingImg fmVFStockTravelingImg;

	public FmVFStockTravelingImg getFmVFStockTravelingImg() {
		return fmVFStockTravelingImg;
	}

	public void setFmVFStockTravelingImg(FmVFStockTravelingImg fmVFStockTravelingImg) {
		this.fmVFStockTravelingImg = fmVFStockTravelingImg;
	}

	public VFSection getVfSection() {
		return vfSection;
	}

	public void setVfSection(VFSection vfSection) {
		this.vfSection = vfSection;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	private JTableListModel tableMode;
	private JButton btnDLTpl;

	/**
	 * This method initializes
	 * 
	 */
	public DgVFStockTravelingImg() {
		super();
		request = new Request(CommonVars.getCurrUser());
		vfAction = (VFVerificationAction) CommonVars.getApplicationContext().getBean(
				"vfVerificationAction");
		initialize();
		initTable(new ArrayList());
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(960, 498));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJPanel());
		this.setTitle("在途盘点数据导入窗口");
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
	 * 为了减轻对服务器数据库的压力,尽量减少对数据库的操作.用缓存! 缓存无非就是全部 查询出来然后 操作list
	 */
	private Map<String, String[]> initConditionList() {
		if (bcsInnerMergeList == null || bcsInnerMergeList.get(0) == null) {
			bcsInnerMergeList = vfAction.findVFFactoryBcsInnerMergeList(request);
			bcsMap = new HashMap<String, String[]>();
			for (int i = 0; i < bcsInnerMergeList.size(); i++) {
				Object[] bcs = (Object[])bcsInnerMergeList.get(i);
				String ptNo = bcs[0].toString();
				String[] strs = new String[bcs.length -1];
				strs[0] = bcs[1] == null ? "":bcs[1].toString();
				strs[1] = bcs[2] == null ? "":bcs[2].toString();
				strs[2] = bcs[3] == null ? "":bcs[3].toString();
				strs[3] = bcs[4] == null ? "":bcs[4].toString();
				if(!"".equals(strs[3])){
					bcsMap.put(ptNo, strs);
				}
			}
		}
		return bcsMap;
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
			String[] strs = null;//new String[line.size() +1];
			if(line.size() < 7){
				strs = new String[7 +1];
			}else{
				strs = new String[line.size() +1];
			}

			// 列操作
			String errorInfo = null;
			String[] bcsStrs = null;
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < line.size(); j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
				// 繁简转换
				 if (cbBig5ConvertToGB.isSelected()) {
				 strs = changStrs(strs);
				 }
				 CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
							+ i + "】行,请稍等...");

				if (j == 0) {
					bcsStrs = bcsMap.get(strs[0]);
					if (strs[0] == null || "".equals(strs[0].toString())) {
						sb.append("1.料号不能为空");
					} else {
						
						if (bcsStrs ==  null) {
							sb.append(" 2.料号不存在对应关系");
						}else{
							String[] stu = bcsMap.get(strs[0]);
							if(stu[2]==null){
								sb.append(" 2.料号的在对应关系的对应商品没有计量单位");
							}
						}
					}
				}  else if (j == 4) {
					if (strs[4] == null || "".equals(strs[4].toString())) {
						sb.append(" 3.数量不能为空");
					}else if(!RegexUtil.checkFloat(strs[4].toString())){
						sb.append(" 3.数量类型不对");
					}
				}
			}
			
				if(line.size() < 5 ){
					sb.append(" 3.数量不能为空");
				}
				
				if(bcsStrs != null){
					strs[1] = bcsStrs[0];
					strs[2] = bcsStrs[1];
					strs[3] = bcsStrs[2];
					System.out.println(bcsStrs[2]+" 1111111111111111111111111111111111                   ");
				}
				
				errorInfo = sb.toString() == null ? "" : sb.toString();
				if (!"".equals(errorInfo) || errorInfo != null && errorInfo.length() > 2) {
					isPass = false;
					strs[7] = errorInfo;
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
			btnExit.setPreferredSize(new Dimension(75, 30));
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

	public boolean isInport() {
		return isInport;
	}

	public void setInport(boolean isInport) {
		this.isInport = isInport;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
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
				CommonProgress.showProgressDialog(DgVFStockTravelingImg.this);
				CommonProgress.setMessage("系统正在初始化数据，请稍后...");
				initConditionList();// 初始化查询条件
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				list = parseTxtFile();
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgVFStockTravelingImg.this,
						"导入数据失败：！" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
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
			initTable(list);
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
			jToolBar.setPreferredSize(new Dimension(20, 35));
			jToolBar.setFloatable(false);
			jToolBar.setLayout(f);

			jToolBar.add(getBtnInput());
			jToolBar.add(getBtnSaveData());
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnDLTpl());

			jToolBar.add(getBtnExit());
			jToolBar.add(getCbCheckTitle());
			jToolBar.add(getCbBig5ConvertToGB());

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
					fileChooser.setFileFilter(new CommonFileFilter(new String[] { "txt", "xls" },
							"选择文档"));
					int state = fileChooser.showOpenDialog(DgVFStockTravelingImg.this);
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
					int len = 8;
					lsResult2 = new ArrayList<String[]>();
					for(int i = 0; i < lsResult.size(); i++){
						String error = lsResult.get(i)[len -1] == null ? null : lsResult.get(i)[len -1].toString();
						if(error == null || "".equals(error)){
							lsResult2.add(lsResult.get(i));
						}
					}
					initTable(lsResult2);
					isPass = true;
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
					String uploaderr = "D:/bestwaydata/uploaderr";// 导入失败路径
					String uploaddatabak = "D:/bestwaydata/uploaddatabak";// 导入成功路径

					if (!isPass) {
						JOptionPane.showMessageDialog(DgVFStockTravelingImg.this, "有错误信息，不可保存！",
								"提示!", 2);

						File uploaderrdir = new File(uploaderr);
						File uploaderrdirfile = new File(uploaderr + File.separator
								+ file.getName());
						if (uploaderrdir.exists()) {
							moveFile(file, uploaderrdirfile);
						}
						return;
					}

					if (tableMode.getRowCount() == 0) {
						JOptionPane.showMessageDialog(DgVFStockTravelingImg.this, "无数据可保存", "提示!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					List<VFStockTravelingImg> listVF = new ArrayList<VFStockTravelingImg>();
					if(lsResult2 == null) lsResult2 = lsResult;
					for(int i = 0; i < lsResult2.size(); i++){
							VFStockTravelingImg vf = new VFStockTravelingImg();
								vf.setPtNo(lsResult2.get(i)[0].toString());
								vf.setPtName(lsResult2.get(i)[1].toString());
								vf.setPtSpec(lsResult2.get(i)[2].toString());
								vf.setPtUnit(lsResult2.get(i)[3]==null||"".equals(lsResult2.get(i)[3])?"":lsResult2.get(i)[3].toString());
								vf.setStoreAmount(Double.parseDouble(lsResult2.get(i)[4].toString() == null ? "0":lsResult2.get(i)[4].toString()));
								vf.setSection(vfSection);
								vf.setWarehouse(lsResult2.get(i)[5].toString());
								vf.setMemo(lsResult2.get(i)[6].toString());
								listVF.add(vf);
					}
					// ****保存数据逻辑
					vfAction.saveVFStockTravelingImgs(request, vfSection, listVF);

					JOptionPane.showMessageDialog(DgVFStockTravelingImg.this, "保存数据成功！", "提示!",
							JOptionPane.INFORMATION_MESSAGE);
					fmVFStockTravelingImg.initTable(listVF);
					DgVFStockTravelingImg.this.dispose();

					// delete temp File
					File uploaddatabakdir = new File(uploaddatabak);
					File uploaddatabakdirfile = new File(uploaddatabak + File.separator
							+ file.getName());
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
		tableMode = new JTableListModel(jTable, list, new JTableListModelAdapter() {
			public List InitColumns() {
				List<JTableListColumn> list = new Vector<JTableListColumn>();
				list.add(new JTableListColumn("工厂料件料号(必填)", "ptNo", 100));
				list.add(new JTableListColumn("工厂物料名称", "ptName", 140));
				list.add(new JTableListColumn("工厂物料规格", "ptSpec", 100));
				list.add(new JTableListColumn("工厂物料单位", "ptUnit", 140));
				list.add(new JTableListColumn("库存数量(必填)", "storeAmount", 100));
				list.add(new JTableListColumn("仓库", "warehouse", 100));
				list.add(new JTableListColumn("备注", "memo", 100));
				list.add(new JTableListColumn("错误信息", "errorInfo", 320));
				return list;
			}
		});
		
		
		jTable.getColumnModel().getColumn(jTable.getColumnCount() - 1).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected,hasFocus, row, column);
						    if (value != null && !"".equals(value)) {//第一个属性列的值
						    	super.setBackground(new java.awt.Color(255, 255, 150));
						    }else{
						    	if (row == table.getSelectedRow()) { //选中时设置前景和背景颜色
									setForeground(table.getSelectionForeground());
									setBackground(table.getSelectionBackground());
								} else { //未选中时设置前景和背景颜色
									setForeground(table.getForeground());
									setBackground(table.getBackground());
								}
						    }
				return this;
			}
		});
		
	}
	


	private JButton getBtnDLTpl() {
		if (btnDLTpl == null) {
			btnDLTpl = new JButton("下载导入模版");
			btnDLTpl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FileUtils.exportTableToExcel(tableMode, getParent());
				}
			});
		}
		return btnDLTpl;
	}
}