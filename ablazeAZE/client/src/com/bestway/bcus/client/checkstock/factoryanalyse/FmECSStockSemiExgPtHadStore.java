package com.bestway.bcus.client.checkstock.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.checkstock.action.ECSCheckStockAction;
import com.bestway.bcus.checkstock.action.ECSCheckStockAuthority;
import com.bestway.bcus.checkstock.entity.ECSSection;
import com.bestway.bcus.checkstock.entity.ECSSemiFinishedExgResolve;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStore;
import com.bestway.bcus.checkstock.entity.ECSStockSemiExgPtHadStoreResolve;
import com.bestway.bcus.checkstock.logic.ECSFactoryAnalyseLogic;
import com.bestway.bcus.client.checkstock.DgECSSectionSel;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.CommonUtils;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

public class FmECSStockSemiExgPtHadStore extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ECSFactoryAnalyseLogic vfVerificationAction;
	
	private AttributiveCellTableModel showTableModelConvert;
	
	private JTableListModelAdapter adapterRawdata;
	
	private JTableListModel showTableModelRawdata;
	
	private JTableListModelAdapter adapterConvert;
	
	public Request request;
	
	//菜单控件
	private	JToolBar tbMenu;
	
	private JScrollPane scrollPane2;
	
	/**
	 * 导入盘点数据
	 */
	private JButton btnImpInventoryDate;
	
	/**
	 * 折算报关数量
	 */
	private JButton btnReducedClearanceNumber;
	
	/**
	 * 查看历史
	 */
	private JButton btnViewHistory;
	
	/**
	 * 导出Excel
	 */
	private JButton btnExportExcel;
	
	/**
	 * 清空当前数据
	 */
	private JButton btnemptyCurrentData;
	private JTabbedPane tabbedPane;
	private JPanel pnRawdata;
	private JPanel pnconvert;
	private JScrollPane scrollPane;
	private JButton btnReducedCustomsNumber;
	
	private JTable rawDataTable; 
	private JTable convertTable;
	private JTextField tfseqNum;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JPanel panel2;
	private ECSSection ecsSection;
	private ECSCheckStockAction ecsCheckStockAction ;
	private JLabel label;
	private JButton btnChoose;
	private JLabel label_1;
	private JTextField tfNump;
	private JLabel label_2;
	private JTextField tfNumb;
	private JLabel label_3;
	private JTextField tfBegin;
	private JLabel label_4;
	private JTextField tfEnd;
	
	
	
	public FmECSStockSemiExgPtHadStore() {
		request = new Request(CommonVars.getCurrUser());
		ECSCheckStockAuthority authority = (ECSCheckStockAuthority)CommonVars.getApplicationContext().getBean("ecsCheckStockAuthority");
		ecsCheckStockAction = (ECSCheckStockAction) CommonVars.getApplicationContext().getBean("ecsCheckStockAction");
		authority.checkECSStockSemiExgPtHadStore(request);
		initialize();
	}
	
	/**
	 * 初始化窗体
	 */
	private void initialize(){
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPanel(),BorderLayout.NORTH);
		getContentPane().add(getPanel2(),BorderLayout.CENTER);
		initRawdataJtable(new ArrayList<Object>());
		initConvertJtable(new ArrayList<Object>());
	}
	@Override
	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		super.setVisible(b);
		this.setTitle("半成品库存（已入库)");
	}
	private JTable getConvertTable(){
		if(convertTable==null){
			convertTable = new JTable();
		}
		return convertTable;
	}
	
	private JScrollPane getScrollPane(){
		if(scrollPane==null){
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getConvertTable());
		}
		return scrollPane;
	}
	
	private JTabbedPane getTabbedPane(){
		if(tabbedPane==null){
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("导入原态", null, getPnRawdata(), null);
			tabbedPane.addTab("折算数据", null, getPnconvert(), null);
		}
		return tabbedPane;
	}
	
	private JPanel getPnconvert(){
		if(pnconvert==null){
			pnconvert =new JPanel();
			pnconvert = new JPanel();
			pnconvert.setLayout(new BorderLayout(0, 0));
			pnconvert.add(getscrollPane2(), BorderLayout.CENTER);
		}
		return pnconvert;
	}
	
	private JPanel getPnRawdata(){
		if(pnRawdata == null){
			pnRawdata = new JPanel();
			pnRawdata.setLayout(new BorderLayout(0, 0));
			pnRawdata.add(getScrollPane(), BorderLayout.CENTER);
		}
		return pnRawdata;
	}
	
	private JScrollPane getscrollPane2(){
		if(scrollPane2==null){
			scrollPane2 = new JScrollPane();
			scrollPane2.setViewportView(getRawDataTable());
		}
		return scrollPane2;
	}
	
	private JTable getRawDataTable(){
		if(rawDataTable == null){
			rawDataTable = new MultiSpanCellTable();
		}
		return rawDataTable;
	}
	
	
	private JToolBar getTbMenu(){
		if(tbMenu==null){
			tbMenu = new JToolBar();
			//设置tbMenu布局
			FlowLayout f = new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			tbMenu.setLayout(f);
			tbMenu.add(getLblNewLabel());
			tbMenu.add(getTfSection());
			tbMenu.add(getBtnViewHistory());
			tbMenu.add(getBtnImpInventoryDate());
			tbMenu.add(getBtnReducedClearanceNumber());
			tbMenu.add(getBtnReducedCustomsNumber());
			tbMenu.add(getBtnExportExcel());
			tbMenu.add(getBtnemptyCurrentData());
		}
		return tbMenu;
	}
	private JButton getBtnemptyCurrentData() {
		if(btnemptyCurrentData==null){
			btnemptyCurrentData = new JButton("清空当前数据");
			btnemptyCurrentData.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					try {
						if (getSection() == null) {
					
							JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this, "请先选择批次!");
							return;
						}
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmECSStockSemiExgPtHadStore.this, "确定要清空本次批次的数据吗?",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}else{
							CommonProgress.showProgressDialog(FmECSStockSemiExgPtHadStore.this);
							CommonProgress.setMessage("系统正在删除,请稍等...");
							disableControls();
							ecsCheckStockAction.deleteECSStockSemiExgPtHadStores(request, getSection());
						
							initConvertJtable(new ArrayList<Object>());
							initRawdataJtable(new ArrayList<Object>());
						}
					} catch (Exception e1) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this,e1.getMessage());
					}finally{
						CommonProgress.closeProgressDialog();
						enableControls();
					}
					
				}
			});
		}
		return btnemptyCurrentData;
	}
	
	private JButton getBtnExportExcel(){
		if(btnExportExcel==null){
			btnExportExcel = new JButton("导出Excel");
			btnExportExcel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getSection() == null) {
				
						JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this, "请先选择批次!");
						return;
					}
					disableControls();
					if(tabbedPane.getSelectedIndex()==0){
						showTableModelRawdata.getMiSaveTableListToExcel().doClick();
					}else{
						showTableModelConvert.getMiSaveTableListToExcel().doClick();
					}
					enableControls();
				}
			});
		}
		return btnExportExcel;
	}
	
	private JButton getBtnViewHistory(){
		if(btnViewHistory==null){
			btnViewHistory = new JButton("查询历史记录");
			btnViewHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FmECSStockSemiExgPtHadStore.this.doFindStars();
				}
			});
		}
		return btnViewHistory;
	}
	
	private JButton getBtnReducedCustomsNumber(){
		if(btnReducedCustomsNumber==null){
			btnReducedCustomsNumber = new JButton("3.折算报关数量");
			btnReducedCustomsNumber.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getSection() == null) {
					
						JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this, "请先选择批次!");
						return;
					}
					FmECSStockSemiExgPtHadStore.this.doConvertStars();
				}
			});
		}
		return btnReducedCustomsNumber;
	}
	
	private JButton getBtnReducedClearanceNumber(){
		if(btnReducedClearanceNumber==null){
			btnReducedClearanceNumber = new JButton("2.半成品折算料件（报关常用工厂BOM）");
			btnReducedClearanceNumber.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getSection() == null) {
					
						JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this, "请先选择批次!");
						return;
					}
					new resolveECSStockSemiExgPtHadStore().execute();
				}
			});
		}
		return btnReducedClearanceNumber;
	}
	
	private JButton getBtnImpInventoryDate(){
		if(btnImpInventoryDate==null){
			btnImpInventoryDate = new JButton("1.导入盘点数据");
			btnImpInventoryDate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getSection() == null) {
			
						JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this, "请先选择批次!");
						return;
					}
					final Request req = new Request(CommonVars.getCurrUser());
					if(ecsCheckStockAction.isExistsBySection(req, getSection(),ECSStockSemiExgPtHadStore.class)){	
					
						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getParent(), 
								"已存在本批次数据，确定删除本批次数据，重新导入?","提示", JOptionPane.YES_NO_OPTION, 
								JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}
						ecsCheckStockAction.deleteECSStockSemiExgPtHadStores(req, getSection());
				
						initConvertJtable(Collections.EMPTY_LIST);
						initRawdataJtable(Collections.EMPTY_LIST);
					}
					List dataSource = new ArrayList<ECSStockSemiExgPtHadStore>();
					DgImportDataExg dipdie = new DgImportDataExg();
					dipdie.setClazz("FmECSStockSemiExgPtHadStore");
					dipdie.setDataSource(dataSource);
					dipdie.setSection(getSection());
			
					dipdie.setVisible(true);
					FmECSStockSemiExgPtHadStore.this.doFindStars();
				}
			});
		}
		return btnImpInventoryDate;
	}
	
	
	/** 
	 * 初始化Jtable
	 * @param list
	 */
	private void initConvertJtable(List list){
		if(adapterRawdata==null){
			adapterRawdata = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
//					【成品】、【料件】、【报关料件】
//					成品料号；版本号；库存数量；
					list.add(addColumn("成品料号", "baseStockExg.ptNo", 150));
					list.add(addColumn("版本号", "baseStockExg.version", 60));
					list.add(addColumn("库存数量", "baseStockExg.storeAmount", 60));
//					料件料号；工厂名称；工厂规格；工厂单位；库存数量,单耗,损耗,单项用量
					list.add(addColumn("料件料号", "ptNo", 100));
					list.add(addColumn("工厂名称", "ptName", 100));
					list.add(addColumn("工厂规格", "ptSpec", 150));
					list.add(addColumn("工厂单位", "ptUnit", 50));
					list.add(addColumn("单耗", "unitWaste", 100));
					list.add(addColumn("损耗", "waste", 100));
					list.add(addColumn("单项用量", "unitUsed", 100));
					list.add(addColumn("成品耗用量", "usedAmount", 100));
//					报关商品名称；报关商品规格；报关单位；这算报关数量；折算系数；料件备案序号；
					list.add(addColumn("料件备案序号", "seqNum", 100));
					list.add(addColumn("报关商品名称", "hsName", 150));
					list.add(addColumn("报关商品规格", "hsSpec", 150));
					list.add(addColumn("计量单位", "hsUnit", 80));
					list.add(addColumn("折算报关数量", "hsAmount", 100));
					list.add(addColumn("折算系数", "unitConvert", 100));
					return list;
				}
			};
		}
		showTableModelConvert = new AttributiveCellTableModel((MultiSpanCellTable) rawDataTable, list, adapterRawdata);
		
		TableColumnModel cm = rawDataTable.getColumnModel();
		ColumnGroup exgGroup = new ColumnGroup("【工厂成品】");
		exgGroup.add(cm.getColumn(1));
		exgGroup.add(cm.getColumn(2));
		exgGroup.add(cm.getColumn(3));
		ColumnGroup ptImpGroup = new ColumnGroup("【工厂料件】");
		ptImpGroup.add(cm.getColumn(4));
		ptImpGroup.add(cm.getColumn(5));
		ptImpGroup.add(cm.getColumn(6));
		ptImpGroup.add(cm.getColumn(7));
		ptImpGroup.add(cm.getColumn(8));
		ptImpGroup.add(cm.getColumn(9));
		ptImpGroup.add(cm.getColumn(10));
		ptImpGroup.add(cm.getColumn(11));
		ColumnGroup hsImpGroup = new ColumnGroup("【报关料件】");
		hsImpGroup.add(cm.getColumn(12));
		hsImpGroup.add(cm.getColumn(13));
		hsImpGroup.add(cm.getColumn(14));
		hsImpGroup.add(cm.getColumn(15));
		hsImpGroup.add(cm.getColumn(16));
		hsImpGroup.add(cm.getColumn(17));
		GroupableTableHeader header = (GroupableTableHeader) rawDataTable
				.getTableHeader();
		header.addColumnGroup(exgGroup);
		header.addColumnGroup(ptImpGroup);
		header.addColumnGroup(hsImpGroup);
	}
	
	private void initRawdataJtable(List list){
		if(list==null){
			list = new Vector();
		}
		if(adapterConvert==null){
			adapterConvert = new JTableListModelAdapter() {
				public List InitColumns() {
					List<JTableListColumn> list = new Vector<JTableListColumn>();
					list.add(addColumn("成品料号", "ptNo", 160));
					list.add(addColumn("工厂名称", "ptName", 160));
					list.add(addColumn("工厂规格", "ptSpec", 160));
					list.add(addColumn("工厂单位", "ptUnit", 80));
					list.add(addColumn("库存数量", "storeAmount", 100));
					list.add(addColumn("版本号", "version", 80));
					list.add(addColumn("仓库","warehouse",100));
					list.add(addColumn("备注","memo",200));
					return list;
				}
			};
		}
		showTableModelRawdata = new JTableListModel(convertTable, list, adapterConvert);
	}
	
	//查询历史记录
	private void doFindStars() {

		
		if (getSection() == null) {
			JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this, "请先选择批次!");
			return;
		}
		final String seqNum = tfseqNum.getText().trim();		
		new AsynSwingWorker<List<?>>() {
			int tableIndex = tabbedPane.getSelectedIndex();
			protected List<?> submit() {
				CommonProgress.setMessage("系统正在获取文件数据，请稍后...");
				try{
					if(tableIndex == 0)
						return  ecsCheckStockAction.findECSStockSemiExgPtHadStores(request, getSection(), null);
					else 
				    	return  ecsCheckStockAction.findECSStockSemiExgPtHadStoreResolves(request, getSection(), 
					
							(seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
				}finally{
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(List<?> data) {			
				if(tableIndex == 0)
					initRawdataJtable(data);
				else
					initConvertJtable(data);			
			}

		}.doWork();
	}
	
  //半成品折算成BOM
	public class resolveECSStockSemiExgPtHadStore extends SwingWorker{
		
		public long startTime = 0;
		
		@Override
		protected Object doInBackground() throws Exception {
			startTime = System.currentTimeMillis();
			CommonProgress.showProgressDialog(FmECSStockSemiExgPtHadStore.this);
			CommonProgress.setMessage("系统正在折料件，请稍后...");
			disableControls();
			//半成品开始折料件BOM
			List<ECSStockSemiExgPtHadStoreResolve> semiExgPtHadStoreResolveLs=ecsCheckStockAction.
					resolveECSStockSemiExgPtHadStoreResolves(request,getSection());
			       
			return semiExgPtHadStoreResolveLs;
		}
		@Override
		protected void done() {
			List<ECSStockSemiExgPtHadStore>  semiExgPtHadStoreLs;
			try {
				semiExgPtHadStoreLs = (List<ECSStockSemiExgPtHadStore>) get();
				tabbedPane.setSelectedIndex(1);
				initConvertJtable(semiExgPtHadStoreLs);
			} catch (InterruptedException e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this,e.getMessage());
			} catch (ExecutionException e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmECSStockSemiExgPtHadStore.this,e.getMessage());
			}finally{
				enableControls();
				CommonProgress.closeProgressDialog();
				long finishTime = System.currentTimeMillis();
				System.out.println("2.半成品折算料件（报关常用工厂BOM）共用时："+(finishTime-startTime)/1000+"秒,合计为"
						+((finishTime-startTime)/1000)/1000+"分钟");
				
			}
		}
	}

	//折算报关单数据
	private void doConvertStars() {
		new SwingWorker<List, Void>() {
			@Override
			protected List doInBackground() throws Exception {
				request.setTaskId(CommonStepProgress.getExeTaskId());
				CommonStepProgress.showStepProgressDialog(request.getTaskId());				
				CommonStepProgress.setStepMessage("正在发送请求，请稍后...");
				try{
					return ecsCheckStockAction.convertECSStockSemiExgPtHadStoreResolves(
							request, getSection());   
				        
				}finally{
					CommonStepProgress.closeStepProgressDialog();
				}
			}

			@Override
			protected void done() {
				List<ECSStockSemiExgPtHadStoreResolve>  semiExgPtHadStoreResolve;
				List errorInfos = new ArrayList<ErrorMessage>();
				try {
					semiExgPtHadStoreResolve = (List<ECSStockSemiExgPtHadStoreResolve>) get();
					tabbedPane.setSelectedIndex(1);
					if(semiExgPtHadStoreResolve.size()>0){
						initConvertJtable(semiExgPtHadStoreResolve);
					}
					;
					for (int i = 0; i < semiExgPtHadStoreResolve.size(); i++) {
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + semiExgPtHadStoreResolve.get(0));
						//如果返回的数据存在备案序号为空的代表这部分 
						//料件料号不存在归并关系，或者归并关系内容不完整需提醒
						ECSStockSemiExgPtHadStoreResolve ecs = semiExgPtHadStoreResolve.get(i);
						if(ecs.getSeqNum()==null){
							ErrorMessage errorMessage =new ErrorMessage();
							errorMessage.setPtNo(ecs.getPtNo());
							errorMessage.setErrorMessage("料件料号不存在归并关系，或者归并关系内容不完整");
							errorInfos.add(errorMessage);
						}
					}
					if(errorInfos!=null&&!errorInfos.isEmpty()){
						DgErrorMessage dgErrorMessage = new DgErrorMessage();
						dgErrorMessage.initTable(errorInfos);
						dgErrorMessage.setVisible(true);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}finally{
					CommonStepProgress.closeStepProgressDialog();
				}
			};
		}.execute();
	}
	
	
	/**
	 * 获得批次号,当批次号为空时返回null,否则返回批次号
	 * @return
	 */
	public ECSSection getSection() {
		return this.ecsSection;
	}
	
	private void disableControls(){
		btnImpInventoryDate.setEnabled(false);
		btnReducedCustomsNumber.setEnabled(false);
		btnReducedClearanceNumber.setEnabled(false);
		btnViewHistory.setEnabled(false);
		btnExportExcel.setEnabled(false);
		btnemptyCurrentData.setEnabled(false);
	}
	
	private void enableControls(){
		btnImpInventoryDate.setEnabled(true);
		btnReducedCustomsNumber.setEnabled(true);
		btnReducedClearanceNumber.setEnabled(true);
		btnViewHistory.setEnabled(true);
		btnExportExcel.setEnabled(true);
		btnemptyCurrentData.setEnabled(true);
	}
	
	private JTextField getTfSection() {
		if (tfseqNum == null) {
			tfseqNum = new JTextField();
			tfseqNum.setPreferredSize(new Dimension(6, 27));
			tfseqNum.setColumns(10);
			tfseqNum.setDocument(new PlainDocument (){
				  public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {							             
					  char c = str.charAt(str.length()-1);
					  if(c >='0' && c <='9'){
						  super.insertString(offs, str, a);
					  }
			      }

			});
		}
		return tfseqNum;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("备案序号:");
		}
		return lblNewLabel;
	}
	/**
	 * 穿透查询
	 * @param section
	 * @param mergerNo
	 */
	public void showData(ECSSection ecsSection, Integer seqNum) {
		
		fillValues(ecsSection);
		if(seqNum != null)
			tfseqNum.setText(String.valueOf(seqNum));
		tabbedPane.setSelectedIndex(1);
		btnViewHistory.doClick();
	}
	
	/**
	 * 填充批次数据
	 * @param section
	 */
	public void fillValues(ECSSection ecsSection){
		this.ecsSection = ecsSection;
//		if(section!=null){			 				
			tfNump.setText(ecsSection.getCancelOwnerHead().getCancelTimes());
//			tfNump.setText(section.getVerificationNo()+"");
			tfNumb.setText(ecsSection.getVerificationNo().toString());
			tfBegin.setText(CommonUtils.getDate(ecsSection.getBeginDate(),"yyyy-MM-dd"));
			tfEnd.setText(CommonUtils.getDate(ecsSection.getEndDate(),"yyyy-MM-dd"));
//		}
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.setPreferredSize(new Dimension(1, 30));
			panel.add(getLabel_6());
			panel.add(getBtnChoose());
			panel.add(getLabel_1());
			panel.add(getTfNump());
			panel.add(getLabel_2());
			panel.add(getTfNumb());
			panel.add(getLabel_3());
			panel.add(getTfBegin());
			panel.add(getLabel_4());
			panel.add(getTfEnd());
		}
		return panel;
	}
	private JPanel getPanel2() {
		if (panel2 == null) {
			panel2 = new JPanel();
			panel2.setLayout(new BorderLayout(0, 0));
			panel2.add(getTbMenu(),BorderLayout.NORTH);
			panel2.add(getTabbedPane(),BorderLayout.CENTER);
		}
		return panel2;
	}
	private JLabel getLabel_6() {
		if (label == null) {
			label = new JLabel("盘点核查批次选择");
			label.setBounds(10, 10, 98, 15);
		}
		return label;
	}
	private JButton getBtnChoose() {
		if (btnChoose == null) {
			btnChoose = new JButton(".....");
			btnChoose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgECSSectionSel dgECSSection = new DgECSSectionSel();
					dgECSSection.setVisible(true);
					if(dgECSSection.isOk()){
						ecsSection = dgECSSection.getSection();
						fillValues(ecsSection);
						
					}
				}
			});
			btnChoose.setBounds(110, 6, 34, 23);
		}
		return btnChoose;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("核销报核次数");
			label_1.setBounds(152, 10, 80, 15);
		}
		return label_1;
	}
	private JTextField getTfNump() {
		if (tfNump == null) {
			tfNump = new JTextField();
			tfNump.setEditable(false);
			tfNump.setBounds(229, 7, 50, 21);
			tfNump.setColumns(10);
		}
		return tfNump;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("盘点核查次数");
			label_2.setBounds(298, 10, 80, 15);
		}
		return label_2;
	}
	private JTextField getTfNumb() {
		if (tfNumb == null) {
			tfNumb = new JTextField();
			tfNumb.setEditable(false);
			tfNumb.setBounds(378, 7, 50, 21);
			tfNumb.setColumns(10);
		}
		return tfNumb;
	}
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("起始日期");
			label_3.setBounds(454, 10, 54, 15);
		}
		return label_3;
	}
	private JTextField getTfBegin() {
		if (tfBegin == null) {
			tfBegin = new JTextField();
			tfBegin.setEditable(false);
			tfBegin.setColumns(10);
			tfBegin.setBounds(504, 7, 80, 21);
		}
		return tfBegin;
	}
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("截止日期");
			label_4.setBounds(585, 10, 54, 15);
		}
		return label_4;
	}
	private JTextField getTfEnd() {
		if (tfEnd == null) {
			tfEnd = new JTextField();
			tfEnd.setEditable(false);
			tfEnd.setColumns(10);
			tfEnd.setBounds(639, 7, 80, 21);
		}
		return tfEnd;
	}
} 
