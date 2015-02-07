package com.bestway.bcs.client.verification.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;

import com.bestway.bcs.bcsinnermerge.entity.BcsInnerMerge;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.entity.VFBaseStockImg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFStockImg;
import com.bestway.bcus.client.common.CommonFileFilter;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseList;
import com.bestway.bcus.client.common.FileReading;
import com.bestway.bcus.custombase.entity.parametercode.Gbtobig;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.FileUtils;
import com.bestway.util.RegexUtil;


public class DgImportDataImg extends JDialogBase {

	private VFVerificationAction vfVerificationAction;
	
	private VFSection section=null;
	
	private Class vFBaseStockImg = null;
	
	private Request request;
	
	//保存导入的信息是否有错误,true为有错误
	private boolean boo = false;
	
	private Hashtable gbHash = null; 
	
	private JPanel jContentPane = null;
	
	private File txtFile = null;

	private JToolBar jToolBar = null;

	private JButton btnOpenFile = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JCheckBox cbJF = null;

	private JCheckBox cbCheckTitle = null;

	private JTableListModel tableModel = null;
	private JTableListModelAdapter	jlma = null;
	private List afterList = null;
	
	private JPanel jPanel1 = null;
	private JButton btnDel = null;
	private JScrollPane scrollPane;
	private JTable table;
	
	//保存用于放入数据库的list
	List<VFBaseStockImg> listBaseStockImg = new ArrayList<VFBaseStockImg>();
			
	//保存用于放入表格list
	List<String[]> listTable = null;
	private JButton btnDLTpl;

	/**
	 * This method initializes
	 * 
	 */
	public DgImportDataImg() {
		super();
		
		request = new Request(CommonVars.getCurrUser());
		
		vfVerificationAction = (VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		
		initialize();
		initJtable(null);
		infTojHsTable();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("料件盘点数据导入窗口");
		this.setContentPane(getJContentPane());
		this.setSize(732, 513);

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
			jContentPane.add(getJPanel1(), BorderLayout.NORTH);
			jContentPane.add(getScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * 打开文件
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				CommonProgress.showProgressDialog(DgImportDataImg.this);
				CommonProgress.setMessage("系统正在打开文件资料，请稍后...");
				afterList = parseTxtFile(txtFile);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataImg.this,
						"打开文件失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return afterList;
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
			initJtable(list);
		}
	}


	private String getSuffix(File f) {
		String s = f.getPath(), suffix = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase().trim();
		return suffix;
	}


	private List parseTxtFile(File file) {
		CommonProgress.setMessage("系统正在读取xls文件,请稍等...");
		listTable = new ArrayList();
		//用于查找料号是否存在
		Hashtable<String, BcsInnerMerge> hsBcsInnerMerge = new Hashtable<String, BcsInnerMerge>();
		
		//内部归并对应表中的料号
		List ptNoList = vfVerificationAction.findBcsInnerMerge(request, MaterielType.MATERIEL);
		
		for (int i = 0; i < ptNoList.size(); i++) {
			BcsInnerMerge bim = (BcsInnerMerge) ptNoList.get(i);
			if(bim.getBcsTenInnerMerge()!= null){
				hsBcsInnerMerge.put(bim.getMateriel().getPtNo(), bim);
			}
		}	
		String suffix = getSuffix(file);
		List<List> lines = null;
		System.out.println("打开导入的Excel数据开始");
		if (suffix.equals("xls")) {
			//
			// 导入xls
			//			
			lines = FileReading.readExcel(file, 0, null);
		} else {
			//
			// 导入txt
			//
			lines = FileReading.readTxt(file, 0, "GBK");
		}
		System.out.println("循环保存和判断料件数据开始");
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			List line = lines.get(i);
			
			if (cbJF.isSelected()) {
				// 繁简转换
				line = changStrs(line);
			}
			CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现验证数据【"
					+ i + "】行,请稍等...");
			
			
			if(line.size()<getProperty().length){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataImg.this, "导入栏位数量少于系统栏位!");
				break;
			}
			
			int ptNoIndex = getIndex("工厂料号(必填)");
			int storeAmountIndex = getIndex("库存数量(必填)");
			int ptNameIndex = getIndex("工厂名称");
			int ptSpecIndex = getIndex("工厂规格");
			int ptUnitIndex = getIndex("工厂单位");
			int errorInfoIndex = getIndex("错误信息");
			
			//保存错误信息
			String errorInformation = "";
			
			String[] strs = new String[errorInfoIndex+1];
			
			for (int j = 0; j < errorInfoIndex; j++) {
				Object obj = line.get(j);
				strs[j] = obj == null ? "" : obj.toString();
			}
			
			//判断工厂料号
			if(hsBcsInnerMerge.get(strs[ptNoIndex])==null){
				errorInformation+="料号"+strs[ptNoIndex]+"在物料与报关对应表中找不到或没有对应商品";
			}else{
				if(hsBcsInnerMerge.get(strs[ptNoIndex]).getMateriel().getCalUnit()==null){
					errorInformation+="料号"+strs[ptNoIndex]+"在物料与报关对应表中的对应商品没有计量单位";
					System.out.println(errorInformation);
				}else{
					strs[ptNameIndex]=hsBcsInnerMerge.
							get(strs[ptNoIndex]).getMateriel().getFactoryName();
					strs[ptSpecIndex]=hsBcsInnerMerge.
							get(strs[ptNoIndex]).getMateriel().getFactorySpec();
					strs[ptUnitIndex]=hsBcsInnerMerge.
							get(strs[ptNoIndex]).getMateriel().getCalUnit().getName();
				}
			}
			//判断库存数量
			if(RegexUtil.checkFloat(strs[storeAmountIndex])){
				double storeAmount = Double.parseDouble(strs[storeAmountIndex]);
				if(storeAmount<=0){
					errorInformation+=errorInformation.equals("")?
							"库存数量必须大于0":",库存数量必须大于0";
				}
			}else{
				errorInformation+=errorInformation.equals("")?
						"库存数量不能为空且必须是数字":",库存数量不能为空且必须是数字";
			}
			
			strs[errorInfoIndex]=errorInformation;
			
			if("".equals(strs[errorInfoIndex])){
				try {
					
					VFBaseStockImg stockImg = null;
					
					if(getVFBaseStockImg()!=null){
						stockImg = (VFBaseStockImg)getVFBaseStockImg().newInstance();
					}else{
						stockImg = new VFStockImg();
					}
					String[] stu = getProperty();
					for (int j = 0; j < stu.length; j++) {
						if(j==storeAmountIndex){
							PropertyUtils.setSimpleProperty(stockImg, stu[j], Double.parseDouble(strs[j]));
							continue;
						}
						PropertyUtils.setSimpleProperty(stockImg, stu[j], strs[j]);
					}
					listBaseStockImg.add(stockImg);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}else{
				//保存是否有错误，用于保存true代表有
				boo = true;
			}
			listTable.add(strs);
		}
		return listTable;
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
			jToolBar.add(getBtnDel());
			jToolBar.add(getBtnDLTpl());
			jToolBar.add(getJButton2());
			jToolBar.add(getCbJF());
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
		if (btnOpenFile == null) {
			btnOpenFile = new JButton();
			btnOpenFile.setText("1.打开文件");
			btnOpenFile.setPreferredSize(new Dimension(80, 30));
			btnOpenFile.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportDataImg.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					//点击打开时清空listVFStockExg里面的数据
					listBaseStockImg.clear();
					boo = false;
					new ImportFileDataRunnable().execute();
				}
			});
		}
		return btnOpenFile;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("2.保存数据");
			jButton1.setPreferredSize(new Dimension(80, 30));
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new ImportFileData().start();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 保存数据
	 * 
	 * @author Administrator
	 * 
	 */
	class ImportFileData extends Thread {
		@Override
		public void run() {
				
				try {
					CommonProgress.showProgressDialog(DgImportDataImg.this);
					CommonProgress.setMessage("系统正在保存，请稍后...");
					
					if(listBaseStockImg.size()<=0){
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgImportDataImg.this,
								"没有正确数据无法保存! ","提示",JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if(boo){
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(DgImportDataImg.this,
								"有错误无法保存! ","提示",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					//保存导入的数据至数据库中
					vfVerificationAction.saveVFStockImgs(request,
							section, listBaseStockImg);
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgImportDataImg.this,
							"保存数据成功！保存数据共 " + listBaseStockImg.size() + "条","提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgImportDataImg.this, e2.getMessage());
				}finally{
					CommonProgress.closeProgressDialog();
				}
				DgImportDataImg.this.dispose();
				
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
			jButton2.setText("退 出");
			jButton2.setPreferredSize(new Dimension(60, 30));
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return jButton2;
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
	 * This method initializes cbCheckTitle
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getCbCheckTitle() {
		if (cbCheckTitle == null) {
			cbCheckTitle = new JCheckBox();
			cbCheckTitle.setText("\u7b2c\u4e00\u884c\u4e3a\u6807\u9898\u884c");
			cbCheckTitle.setSelected(true);
		}
		return cbCheckTitle;
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
			jPanel1.add(getJToolBar(), BorderLayout.NORTH);

		}
		return jPanel1;
	}

	/**对倒入的错误数据进行删除
	 * This method initializes btnDel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("删除错误");
			btnDel.setPreferredSize(new Dimension(60,30));
			btnDel.addActionListener(new java.awt.event.ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					//改变错误状态
					boo=false;
					initJtable(listBaseStockImg);
				}
			});
		}
		return btnDel;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}
	
	
	/**
	 * 初始化Jtable
	 * @param list
	 */
	public void initJtable(List list){
		jlma=new JTableListModelAdapter() {
				@Override
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("工厂料号(必填)", "ptNo", 130));
					list.add(addColumn("工厂名称", "ptName", 100));
					list.add(addColumn("工厂规格", "ptSpec", 150));
					list.add(addColumn("工厂单位", "ptUnit", 80));
					list.add(addColumn("库存数量(必填)", "storeAmount", 60));
					list.add(addColumn("仓库", "warehouse", 60));
					list.add(addColumn("备注", "memo", 60));
					list.add(addColumn("错误信息", "errorInformation", 400));
					return list;
				}
			};
		tableModel = new JTableListModel(table, list,jlma);
		TableCellRenderer boolTcr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				super.getTableCellRendererComponent(table, value,
						isSelected, hasFocus, row, column);
				if ("".equals(value)||value==null) {
					if (row == table.getSelectedRow()) {
						setForeground(table.getSelectionForeground());
						setBackground(table.getSelectionBackground());
					} else {
						setForeground(table.getForeground());
						setBackground(table.getBackground());
					}
				}else{
					this.setBackground(new java.awt.Color(255, 255, 150)); 
				}
				return this;
			}
		};
		table.getColumnModel().getColumn(table.getColumnCount() - 1)
				.setCellRenderer(boolTcr);
	}
	
	/**
	 * 当找不到对应的列名时返回-1否则返回列名所对应的下标
	 * @param stu
	 * @return
	 */
	public int getIndex(String stu){
		int index=-1;
		List<JTableListColumn> jTableListColumn = jlma.getColumns();
		for (int j = 0; j < jTableListColumn.size(); j++) {
			JTableListColumn jtlc = jTableListColumn.get(j);
			if(jtlc.getCaption().equals(stu)){
				index = j-1;
			}
		}
		return index;
	}
	
	public String[] getProperty(){
		List<JTableListColumn> list = jlma.getColumns();
		String[] property = new String[list.size()-2];
		for (int i = 1; i < list.size()-1; i++) {
			property[i-1] = list.get(i).getProperty();
		}
		return property;
	}
	
	public VFSection getSection() {
		return section;
	}

	public void setSection(VFSection section) {
		this.section = section;
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
	 * 繁转简
	 * 
	 * @param source
	 * @return
	 */
	private List changStrs(List source) {
		List newList = new ArrayList();
		for (int i = 0; i < source.size(); i++) {
			newList.add(changeStr(source.get(i).toString()));
		}
		return newList;
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
			if (String.valueOf(xxx[i]).getBytes().length >1) {
				if (gbHash.get(String.valueOf(xxx[i])) != null) {
					z = (String) gbHash.get(String.valueOf(xxx[i]));
				}
			}
			yy = yy + z;
		}
		return yy;
	}
	private JButton getBtnDLTpl() {
		if (btnDLTpl == null) {
			btnDLTpl = new JButton("下载导入模版");
			btnDLTpl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FileUtils.exportTableToExcel(tableModel, getParent());
				}
			});
		}
		return btnDLTpl;
	}
	
	public void setVFBaseStockImg(Class vFBaseStockImg){
		this.vFBaseStockImg = vFBaseStockImg;
	}
	
	public Class getVFBaseStockImg(){
		return this.vFBaseStockImg;
	}
}