package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.entity.ECSFinishedExg;
import com.bestway.bcus.checkstock.entity.ECSFinishingExg;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSSemiFinishedExg;
import com.bestway.bcus.checkstock.entity.ECSStagnateExg;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendExgPt;
import com.bestway.bcus.checkstock.entity.ECSStockOutSendSemiExgPt;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStore;
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
import com.bestway.common.materialbase.entity.CalUnit;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.ui.winuicontrol.JDialogBase;
import com.bestway.util.FileUtils;
import com.bestway.util.RegexUtil;


public class DgImportDataExg<T> extends JDialogBase {

	private ECSCheckStockAction ecsCheckStockAction;
	
	private ECSSection  section=null;
	
	public Request request;
	
	//保存导入的信息是否有错误
	private boolean boo = false;
	
	/*** 繁转简 */
	private Hashtable gbHash = null; 
	
	private JPanel jContentPane = null;
	
	private File txtFile = null;

	private JToolBar jToolBar = null;

	private JButton jButton = null;

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
	private String clazz;
	
	//保存用于放入数据库的list
	private List<T> dataSource ;
	
			
	//保存用于放入表格list
	List<String[]> listTable = null;
	private JButton btnDLTpl;
	


	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	

	public List<T> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<T> dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method initializes
	 * 
	 */
	public DgImportDataExg() {
		super();
		
		request = new Request(CommonVars.getCurrUser());
		
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars
				.getApplicationContext().getBean("ecsCheckStockAction");
		
		initialize();
		initJtable(new ArrayList());
		infTojHsTable();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("数据导入接口 ->成品/半成品");
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
				CommonProgress.showProgressDialog(DgImportDataExg.this);
				CommonProgress.setMessage("系统正在打开文件资料，请稍后...");
				afterList = parseTxtFile(txtFile);
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataExg.this,
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
		listTable = new ArrayList();
		//保存BCS内部归并对应表（BcsInnerMerge）中的料号，用于查找料号是否存在
		Map<String, MaterialBomVersion> versionMap = new HashMap<String, MaterialBomVersion>();
		long b = System.currentTimeMillis();
		List<MaterialBomVersion> bom = ecsCheckStockAction.findMaterialBomVersions(request);
		long s = System.currentTimeMillis();
		System.out.println("time:"+(s-b)/1000.0);
		
		for (int i = 0; i < bom.size(); i++) {
			MaterialBomVersion v = (MaterialBomVersion) bom.get(i);
			versionMap.put(v.getMaster().getMateriel().getPtNo()
					+ "," + v.getVersion(), v);
		}	

		String suffix = getSuffix(file);
		List<List> lines = null;
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
		
		
		for (int i = 0; i < lines.size(); i++) {
			if (cbCheckTitle.isSelected() && i == 0) {
				continue;
			}
			
			List line = lines.get(i);
			
			if (cbJF.isSelected()) {
				line = changStrs(line);
				CommonProgress.setMessage("文件总共有【" + (lines.size()-1) + "】行数据,现导入数据【"
						+ i + "】行,请稍等...");
			}else{
				CommonProgress.setMessage("文件总共有【" + lines.size() + "】行数据,现导入数据【"
						+ i + "】行,请稍等...");
			}
			

			if(line.size()<getProperty().length){
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataExg.this, "导入栏位数量少于系统栏位!");
				break;
			}
			
			int ptNoIndex = getIndex("成品料号(必填)");
			int versionIndex = getIndex("成品BOM版本");
			int storeAmountIndex = getIndex("成品库存数量(必填)");
			int ptNameIndex = getIndex("工厂名称");
			int ptSpecIndex = getIndex("工厂规格");
			int ptUnitIndex = getIndex("工厂单位");
			int errorInfoIndex = getIndex("错误信息");
			
			String[] strs = new String[line.size()+1];
			//用于保存错误信息
			String errorInformation = "";
			
			for (int j = 0; j < errorInfoIndex; j++) {
				Object obj = line.get(j);
				strs[j] = obj == null?""+j : obj.toString();
			}
			//判断成品料号和BOM版本
			if(versionMap.get(strs[ptNoIndex]+","+strs[versionIndex])==null){
				errorInformation+="成品料号"+strs[ptNoIndex]+"所对应的BOM号"+strs[versionIndex]+"在常用工厂BOM模块找不到";
			}else{
				strs[ptNameIndex]=versionMap.
						get(strs[ptNoIndex]+","+strs[versionIndex]).getMaster().getMateriel().getFactoryName();
				strs[ptSpecIndex]=versionMap.
						get(strs[ptNoIndex]+","+strs[versionIndex]).getMaster().getMateriel().getFactorySpec();
				
				CalUnit calUnit = versionMap.
						get(strs[ptNoIndex]+","+strs[versionIndex]).getMaster().getMateriel().getCalUnit();
				strs[ptUnitIndex]=(calUnit==null?null:calUnit.getName());
			}
			
			//判断成品库存数量
			if(RegexUtil.checkFloat(strs[storeAmountIndex])){
				double storeAmount = Double.parseDouble(strs[storeAmountIndex]);
				if(storeAmount<=0){
					errorInformation+=errorInformation.equals("")?
							"成品库存数量必须大于0":",库存数量必须大于0";
				}
			}else{
				errorInformation+=errorInformation.equals("")?
						"成品库存数量必须是数字":",成品库存数量必须是数字";
			}
			
			strs[errorInfoIndex]=errorInformation;
			
			if(strs[errorInfoIndex].equals("")){
				try {
					if("FmECSFinishedExg".equals(clazz)){//残次品成品
						ECSFinishedExg finishedExg = new ECSFinishedExg();
						finishedExg.setPtNo(strs[0]);
						finishedExg.setVersion(Integer.valueOf(strs[1]));
						finishedExg.setStoreAmount(Double.valueOf(strs[2]));
						finishedExg.setPtName(strs[3]);
						finishedExg.setPtSpec(strs[4]);
						finishedExg.setPtUnit(strs[5]);
						finishedExg.setWarehouse(strs[6]);
						finishedExg.setMemo(strs[7]);
						dataSource.add((T)finishedExg);
						
					}else if("FmECSSemiFinishedExg".equals(clazz)){//残次品半成品
						ECSSemiFinishedExg semiFinishedExg = new ECSSemiFinishedExg();
						semiFinishedExg.setPtNo(strs[0]);
						semiFinishedExg.setVersion(Integer.valueOf(strs[1]));
						semiFinishedExg.setStoreAmount(Double.valueOf(strs[2]));
						semiFinishedExg.setPtName(strs[3]);
						semiFinishedExg.setPtSpec(strs[4]);
						semiFinishedExg.setPtUnit(strs[5]);
						semiFinishedExg.setWarehouse(strs[6]);
						semiFinishedExg.setMemo(strs[7]);
						dataSource.add((T)semiFinishedExg);
					}else if("FmECSFinishingExg".equals(clazz)){//在制品成品
						ECSFinishingExg finishingExg = new ECSFinishingExg();
						finishingExg.setPtNo(strs[0]);
						finishingExg.setVersion(Integer.valueOf(strs[1]));
						finishingExg.setStoreAmount(Double.valueOf(strs[2]));
						finishingExg.setPtName(strs[3]);
						finishingExg.setPtSpec(strs[4]);
						finishingExg.setPtUnit(strs[5]);
						finishingExg.setWarehouse(strs[6]);
						finishingExg.setMemo(strs[7]);
						dataSource.add((T)finishingExg);
					}else if("FmECSStockOutSendExgPt".equals(clazz)){//外发成品
						ECSStockOutSendExgPt stockOutSendExgPt = new ECSStockOutSendExgPt();
						stockOutSendExgPt.setPtNo(strs[0]);
						stockOutSendExgPt.setVersion(Integer.valueOf(strs[1]));
						stockOutSendExgPt.setStoreAmount(Double.valueOf(strs[2]));
						stockOutSendExgPt.setPtName(strs[3]);
						stockOutSendExgPt.setPtSpec(strs[4]);
						stockOutSendExgPt.setPtUnit(strs[5]);
						stockOutSendExgPt.setWarehouse(strs[6]);
						stockOutSendExgPt.setMemo(strs[7]);
						dataSource.add((T)stockOutSendExgPt);
					}else if("FmECSStockOutSendSemiExgPt".equals(clazz)){//外发半成品
						ECSStockOutSendSemiExgPt stockOutSendSemiExg = new ECSStockOutSendSemiExgPt();
						stockOutSendSemiExg.setPtNo(strs[0]);
						stockOutSendSemiExg.setVersion(Integer.valueOf(strs[1]));
						stockOutSendSemiExg.setStoreAmount(Double.valueOf(strs[2]));
						stockOutSendSemiExg.setPtName(strs[3]);
						stockOutSendSemiExg.setPtSpec(strs[4]);
						stockOutSendSemiExg.setPtUnit(strs[5]);
						stockOutSendSemiExg.setWarehouse(strs[6]);
						stockOutSendSemiExg.setMemo(strs[7]);
						dataSource.add((T)stockOutSendSemiExg);
					}else if("FmECSStockSemiExgPtHadStore".equals(clazz)){
						ECSStockSemiExgPtHadStore stockSemiExgPtHadStore = new ECSStockSemiExgPtHadStore();
						stockSemiExgPtHadStore.setPtNo(strs[0]);
						stockSemiExgPtHadStore.setVersion(Integer.valueOf(strs[1]));
						stockSemiExgPtHadStore.setStoreAmount(Double.valueOf(strs[2]));
						stockSemiExgPtHadStore.setPtName(strs[3]);
						stockSemiExgPtHadStore.setPtSpec(strs[4]);
						stockSemiExgPtHadStore.setPtUnit(strs[5]);
						stockSemiExgPtHadStore.setWarehouse(strs[6]);
						stockSemiExgPtHadStore.setMemo(strs[7]);
						dataSource.add((T)stockSemiExgPtHadStore);
					}else if("FmECSStagnateExg".equals(clazz)){//呆滞品成品
						ECSStagnateExg stagnateExg = new ECSStagnateExg();
						stagnateExg.setPtNo(strs[0]);
						stagnateExg.setVersion(Integer.valueOf(strs[1]));
						stagnateExg.setStoreAmount(Double.valueOf(strs[2]));
						stagnateExg.setPtName(strs[3]);
						stagnateExg.setPtSpec(strs[4]);
						stagnateExg.setPtUnit(strs[5]);
						stagnateExg.setWarehouse(strs[6]);
						stagnateExg.setMemo(strs[7]);
						dataSource.add((T)stagnateExg);
					}
					
//					for (int j = 0; j < stu.length; j++) {
//						if(j==storeAmountIndex){
//							PropertyUtils.setSimpleProperty(badBaseStockExg, stu[j], Double.parseDouble(strs[j]));
//							continue;
//						}
//						if(j==versionIndex){
//							PropertyUtils.setSimpleProperty(badBaseStockExg, stu[j], Integer.parseInt(strs[j]));
//							continue;
//						}
//						PropertyUtils.setSimpleProperty(badBaseStockExg, stu[j], strs[j]);
//					}
					
				} catch (Exception e) {
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
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("1.打开文件");
			jButton.setPreferredSize(new Dimension(80, 30));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.removeChoosableFileFilter(fileChooser
							.getFileFilter());
					fileChooser.setFileFilter(new CommonFileFilter(
							new String[] { "txt", "xls" }, "选择文档"));
					int state = fileChooser
							.showOpenDialog(DgImportDataExg.this);
					if (state != JFileChooser.APPROVE_OPTION) {
						return;
					}
					txtFile = fileChooser.getSelectedFile();
					if (txtFile == null || !txtFile.exists()) {
						return;
					}
					//点击打开时清空listVFStockExg里面的数据
					if(dataSource!=null){
						dataSource.clear();
					}
					boo = false;
					new ImportFileDataRunnable().execute();

				}
			});
		}
		return jButton;
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
				CommonProgress.showProgressDialog(DgImportDataExg.this);
				CommonProgress.setMessage("系统正在保存，请稍后...");
				
				if(dataSource.size()<=0){
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgImportDataExg.this,
							"没有正确数据无法导入! ","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(boo){
					CommonProgress.closeProgressDialog();
					JOptionPane.showMessageDialog(DgImportDataExg.this,
							"有错误无法导入! ","提示",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				//保存导入的数据至数据库中
				if("FmECSFinishedExg".equals(clazz)){//残次品成品
					ecsCheckStockAction.saveECSFinishedExg(request, section, (List<ECSFinishedExg>)dataSource);
				}else if("FmECSSemiFinishedExg".equals(clazz)){//残次品半成品
					ecsCheckStockAction.saveECSSemiFinishedExg(request, section, (List<ECSSemiFinishedExg>)dataSource);
				}else if("FmECSFinishingExg".equals(clazz)){//在制品成品
					ecsCheckStockAction.saveECSFinishingExgs(request, section, (List<ECSFinishingExg>)dataSource);
				}else if("FmECSStockOutSendExgPt".equals(clazz)){//外发成品
					ecsCheckStockAction.saveECSStockOutSendExgPts(request, section, (List<ECSStockOutSendExgPt>)dataSource);
				}else if("FmECSStockOutSendSemiExgPt".equals(clazz)){//外发半成品
					ecsCheckStockAction.saveECSStockOutSendSemiExgPts(request, section, (List<ECSStockOutSendSemiExgPt>)dataSource);
				}else if("FmECSStockSemiExgPtHadStore".equals(clazz)){//半成品库存（已入库）
					ecsCheckStockAction.saveECSStockSemiExgPtHadStores(request, section, (List<ECSStockSemiExgPtHadStore>)dataSource);
				}else if("FmECSStagnateExg".equals(clazz)){//呆滞品成品
					ecsCheckStockAction.saveECSStagnateExgs(request, section, (List<ECSStagnateExg>)dataSource);
				}
				
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(DgImportDataExg.this,
						"保存数据成功！保存数据共 " + dataSource.size() + "条","提示",
						JOptionPane.INFORMATION_MESSAGE);
				DgImportDataExg.this.dispose();
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
			jButton2.setPreferredSize(new Dimension(45, 30));
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
					initJtable(dataSource);
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
					list.add(addColumn("成品料号(必填)", "ptNo", 100));
					list.add(addColumn("成品BOM版本", "version", 100));
					list.add(addColumn("成品库存数量(必填)", "storeAmount", 100));
					list.add(addColumn("工厂名称", "ptName", 100));
					list.add(addColumn("工厂规格", "ptSpec", 100));
					list.add(addColumn("工厂单位", "ptUnit", 50));
					list.add(addColumn("仓库","warehouse",100));
					list.add(addColumn("备注","memo",200));
					list.add(addColumn("错误信息", "errorInformation", 450));
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
	
	public ECSSection getSection() {
		return section;
	}

	public void setSection(ECSSection section) {
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
			if (String.valueOf(xxx[i]).getBytes().length > 1) {
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
}
