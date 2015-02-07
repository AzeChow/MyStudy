package com.bestway.bcs.client.verification.factoryanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.bestway.bcs.client.verification.DgVFBaseStockExgOrImgEdit;
import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFBaseStockExg;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcs.verification.entity.VFSemiBadExg;
import com.bestway.bcs.verification.entity.VFSemiBadExgConvert;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.MaterielType;
import com.bestway.common.Request;
import com.bestway.common.materialbase.entity.MaterialBomVersion;
import com.bestway.jptds.client.common.CustomBaseComboBoxEditor;
import com.bestway.jptds.client.common.CustomBaseRender;
import com.bestway.ui.winuicontrol.JInternalFrameBase;

public class FmVFSemiBadExg extends JInternalFrameBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VFVerificationAction vfVerificationAction;
	
	private AttributiveCellTableModel showTableModelConvert;
	
	private JTableListModelAdapter adapterRawdata;
	
	private JTableListModel showTableModelRawdata;
	
	private JTableListModelAdapter adapterConvert;
	
	public Request request;
	
	private JLabel lbBatchSelect;
	
	//菜单控件
	private	JToolBar tbMenu;
	
	private JScrollPane scrollPane2;
	/**
	 * 批次时间选择
	 */
	private JComboBox cbbSelectVFSection;
	
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
	private JButton btnEdit;
	private JButton btnDelete;
	
	
	public FmVFSemiBadExg() {
		
		this.setTitle("残次品半成品");
		request = new Request(CommonVars.getCurrUser());
		vfVerificationAction = (VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
//		authority.checkSemiBadExg(new Request(CommonVars.getCurrUser()));
		initialize();
	}
	
	/**
	 * 初始化窗体
	 */
	private void initialize(){
		//存放工厂盘点数据表的JTable
		getContentPane().add(getTbMenu(), BorderLayout.NORTH);
		getContentPane().add(getTabbedPane(), BorderLayout.CENTER);
		
		initRawdataJtable(new ArrayList<Object>());
		initConvertJtable(new ArrayList<Object>());
		initCbbSelectTime();
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
			tabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
						int index = tabbedPane.getSelectedIndex();
						if (index == 1) {
							
							lblNewLabel.setVisible(true);
							tfseqNum.setVisible(true);
							btnEdit.setVisible(false);
							btnDelete.setVisible(false);
						} else if (index == 0) {
							btnEdit.setVisible(true);
							btnDelete.setVisible(true);
							lblNewLabel.setVisible(false);
							tfseqNum.setVisible(false);
						}
				}
			});
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
			tbMenu.add(getLbBatchSelect());
			tbMenu.add(getCbbSelectVFSection());
			tbMenu.add(getLblNewLabel());
			tbMenu.add(getTfSection());
			tbMenu.add(getBtnViewHistory());
			tbMenu.add(getBtnImpInventoryDate());
			tbMenu.add(getBtnReducedClearanceNumber());
			tbMenu.add(getBtnReducedCustomsNumber());
			tbMenu.add(getBtnExportExcel());
			tbMenu.add(getBtnEdit());
			tbMenu.add(getBtnDelete());
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
						if (getVFSection() == null) {
							JOptionPane.showMessageDialog(FmVFSemiBadExg.this, "请先选择批次!");
							return;
						}
						if(JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmVFSemiBadExg.this, "确定要清空本次批次的数据吗?",
								"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}else{
							CommonProgress.showProgressDialog(FmVFSemiBadExg.this);
							CommonProgress.setMessage("系统正在删除,请稍等...");
							disableControls();
							vfVerificationAction.deleteVFSemiBadExgConverts(request, getVFSection());
							vfVerificationAction.deleteVFSemiBadExgs(request, getVFSection());
							initConvertJtable(new ArrayList<Object>());
							initRawdataJtable(new ArrayList<Object>());
						}
					} catch (Exception e1) {
						CommonProgress.closeProgressDialog();
						JOptionPane.showMessageDialog(FmVFSemiBadExg.this,e1.getMessage());
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
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFSemiBadExg.this, "请先选择批次!");
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
			btnViewHistory = new JButton("查询批次数据");
			btnViewHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					queryVFSectionData();
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
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFSemiBadExg.this, "请先选择批次!");
						return;
					}
					new convertVFSemiBadExgConvert().execute();
					
				}
			});
		}
		return btnReducedCustomsNumber;
	}
	
	private JButton getBtnReducedClearanceNumber(){
		if(btnReducedClearanceNumber==null){
			btnReducedClearanceNumber = new JButton("2.成品折算料件（报关常用工厂BOM）");
			btnReducedClearanceNumber.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFSemiBadExg.this, "请先选择批次!");
						return;
					}
					new resolveVFSemiBadExg().execute();
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
					if (getVFSection() == null) {
						JOptionPane.showMessageDialog(FmVFSemiBadExg.this, "请先选择批次!");
						return;
					}
					final Request req = new Request(CommonVars.getCurrUser());
					if(vfVerificationAction.isExistsBySection(req, getVFSection(), VFSemiBadExg.class)){						
						if(JOptionPane.YES_OPTION != JOptionPane.showOptionDialog(getParent(), "已存在本批次数据，确定删除本批次数据，重新导入?","提示", JOptionPane.YES_NO_OPTION, 
								JOptionPane.INFORMATION_MESSAGE,null, new Object[]{"是","否"},"否")){
							return;
						}
						vfVerificationAction.deleteVFSemiBadExgConverts(request, getVFSection());
						vfVerificationAction.deleteVFSemiBadExgs(request, getVFSection());
						initConvertJtable(Collections.EMPTY_LIST);
						initRawdataJtable(Collections.EMPTY_LIST);
					}					
					DgImportDataIExg dipdie = new DgImportDataIExg();
					dipdie.setSection(getVFSection());
					dipdie.setvFBaseStockExg(VFSemiBadExg.class);
					dipdie.setVisible(true);
					queryVFSectionData();
				}
			});
		}
		return btnImpInventoryDate;
	}
	
	private  JComboBox getCbbSelectVFSection(){
		if(cbbSelectVFSection==null){
			cbbSelectVFSection = new JComboBox();
			cbbSelectVFSection.setEditable(true);
			cbbSelectVFSection.setPreferredSize(new Dimension(120,27));
		}
		return cbbSelectVFSection;
	}
	
	
	private JLabel getLbBatchSelect(){
		if(lbBatchSelect==null){
			lbBatchSelect = new JLabel("批次选择:");
		}
		return lbBatchSelect;
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
					list.add(addColumn("成品料号", "stockExg.ptNo", 100));
					list.add(addColumn("版本号", "stockExg.version", 80));
					list.add(addColumn("库存数量", "stockExg.storeAmount", 60));
//					料件料号；工厂名称；工厂规格；工厂单位；库存数量,单耗,损耗,单项用量
					list.add(addColumn("料件料号", "ptNo", 100));
					list.add(addColumn("工厂名称", "ptName", 100));
					list.add(addColumn("工厂规格", "ptSpec", 100));
					list.add(addColumn("工厂单位", "ptUnit", 50));
					list.add(addColumn("单耗", "unitWaste", 100));
					list.add(addColumn("损耗", "waste", 100));
					list.add(addColumn("单项用量", "unitUsed", 100));
					list.add(addColumn("成品耗用量", "storeAmount", 100));
//					报关商品名称；报关商品规格；报关单位；这算报关数量；折算系数；归并序号；
					list.add(addColumn("归并序号", "mergerNo", 60)); 
					list.add(addColumn("报关商品名称", "hsName", 100));
					list.add(addColumn("报关商品规格", "hsSpec", 150));
					list.add(addColumn("计量单位", "hsUnit", 80));
					list.add(addColumn("折算报关数量", "hsAmount", 150));
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
					list.add(addColumn("仓库", "warehouse", 100));
					list.add(addColumn("备注", "memo", 100));
					return list;
				}
			};
		}
		showTableModelRawdata = new JTableListModel(convertTable, list, adapterConvert);
	}
	
	/**
	 * 初始化批次选择的下拉列表
	 */
	private void initCbbSelectTime(){
		//格式化时间
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<VFSection> dataSource=vfVerificationAction.findVFSectionList(request);
		List<VFSection> manulist=new ArrayList<VFSection>();
		for (int i = 0; i < dataSource.size(); i++) {
			VFSection vfSection=(VFSection) dataSource.get(i);
			vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection.getEndDate())));
			manulist.add(vfSection);
		}
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel(manulist.toArray());
		this.cbbSelectVFSection.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbSelectVFSection,"verificationNo","endDate",150);
		this.cbbSelectVFSection.setRenderer(CustomBaseRender.getInstance().getRender(
				"verificationNo", "endDate", 40, 100));
		this.cbbSelectVFSection.setSelectedItem(null);
	}
	
	
	
	
	private void queryVFSectionData(){
		if (getVFSection() == null) {
			JOptionPane.showMessageDialog(FmVFSemiBadExg.this, "请先选择批次!");
			return;
		}
		disableControls();
		String seqNum = tfseqNum.getText().trim();		
		List<VFSemiBadExg> listVFSemiBadExg = vfVerificationAction.findVFSemiBadExgs(request, getVFSection());
		initRawdataJtable(listVFSemiBadExg);
		List<VFSemiBadExgConvert> listVFSemiBadExgConverts = vfVerificationAction.findVFSemiBadExgConverts(request, getVFSection(), (seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
		initConvertJtable(listVFSemiBadExgConverts);
		//tabbedPane.setSelectedIndex(0);
		enableControls();
	}

	public class resolveVFSemiBadExg extends SwingWorker{
		
		public long startTime = 0;
		
		@Override
		protected Object doInBackground() throws Exception {
			
			startTime = System.currentTimeMillis();
			
			CommonProgress.showProgressDialog(FmVFSemiBadExg.this);
			CommonProgress.setMessage("系统正在折料件，请稍后...");
			disableControls();
			//保存BOM版本，用于查找该成品BOM是否存在
			Map<String, MaterialBomVersion> versionMap = new HashMap<String, MaterialBomVersion>();
			List<MaterialBomVersion> bom = vfVerificationAction.findMaterialBomVersions(request);
			for (int i = 0; i < bom.size(); i++) {
				MaterialBomVersion v = (MaterialBomVersion) bom.get(i);
				versionMap.put(v.getMaster().getMateriel().getPtNo()
						+ "," + v.getVersion(), v);
			}
			List<VFSemiBadExg> exgs =vfVerificationAction.findVFSemiBadExgs(request,getVFSection());
			VFSemiBadExg exgss = null;
			List<ErrorMessage> errs = new ArrayList<ErrorMessage>();
			for(int i=0;i<exgs.size();i++){
				exgss=exgs.get(i);
				if(versionMap.get(exgss.getPtNo() + "," + exgss.getVersion()) == null){
					errs.add(new ErrorMessage(exgss.getPtNo() ,"版本号" +  exgss.getVersion() +"在报关常用工厂BOM不存在"));
				}
			}
			if(errs.size() > 0){
				DgErrorMessage.show(errs);
			}
			List<VFSemiBadExgConvert> ListExgConvert= vfVerificationAction.resolveVFSemiBadExgs(request,getVFSection());
			return ListExgConvert;
		}
		
		@Override
		protected void done() {
			List<VFSemiBadExgConvert>  ListExgConvert;
			try {
				ListExgConvert = (List<VFSemiBadExgConvert>) get();
				tabbedPane.setSelectedIndex(1);
				initConvertJtable(ListExgConvert);
			} catch (InterruptedException e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmVFSemiBadExg.this,e.getMessage());
			} catch (ExecutionException e) {
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmVFSemiBadExg.this,e.getMessage());
			}finally{
				enableControls();
				CommonProgress.closeProgressDialog();
				
				long finishTime = System.currentTimeMillis();
				System.out.println("2.成品折算料件（报关常用工厂BOM）共用时："+(finishTime-startTime)/1000+"秒,合计为"
						+((finishTime-startTime)/1000)/1000+"分钟");
				
			}
		}
	}
	
	public class convertVFSemiBadExgConvert extends SwingWorker{
		
		public long startTime = 0;
		
		@Override
		protected Object doInBackground() throws Exception {
			startTime = System.currentTimeMillis();
			
			CommonProgress.showProgressDialog(FmVFSemiBadExg.this);
			CommonProgress.setMessage("系统正在折算报关数量，请稍后...");
			disableControls();
			List<VFSemiBadExgConvert> ListExgConvert = new ArrayList<VFSemiBadExgConvert>();
			String logg =null;
			try {
				logg = vfVerificationAction.convertVFSemiBadExgConvertsEms(
						request, getVFSection());
				if (logg != null && !logg.isEmpty()) {
					String[] ptNos = logg.toString().split(",");
					List<ErrorMessage> errs = new ArrayList<ErrorMessage>();
					for (int i = 0; i < ptNos.length; i++) {
						errs.add(new ErrorMessage(ptNos[i],
								"在【物料与报关对应表】中不存在,或找不到对应关系"));
					}
					DgErrorMessage.show(errs);
				}
				String seqNum = tfseqNum.getText().trim();		
				ListExgConvert = vfVerificationAction.findVFSemiBadExgConverts(request, getVFSection(), (seqNum == null || "".equals(seqNum))?null:Integer.parseInt(seqNum));
			} catch (Exception e) {
				CommonProgress.closeProgressDialog();
				
			}finally{
				CommonProgress.closeProgressDialog();
			}
			return ListExgConvert;
		}
		
		@Override
		protected void done() {
			List<VFSemiBadExgConvert>  ListExgConvert;
			try {
				ListExgConvert = (List<VFSemiBadExgConvert>) get();
				tabbedPane.setSelectedIndex(1);
				if(ListExgConvert != null && ListExgConvert.size()>0){
					initConvertJtable(ListExgConvert);
				}
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(FmVFSemiBadExg.this,e.getMessage());
			} catch (ExecutionException e) {
				JOptionPane.showMessageDialog(FmVFSemiBadExg.this,e.getMessage());
			}finally{
				enableControls();
				
				long finishTime = System.currentTimeMillis();
				System.out.println("3.折算报关数量共用时："+(finishTime-startTime)/1000+"秒,合计为"
						+((finishTime-startTime)/1000)/1000+"分钟");
			}
		}
	}
	
	/**
	 * 获得批次号,当批次号为空时返回null,否则返回批次号
	 * @return
	 */
	private VFSection getVFSection(){
		VFSection section = (VFSection)cbbSelectVFSection.getSelectedItem();
		return section;
	}
	
	private void disableControls(){
		cbbSelectVFSection.setEnabled(false);
		btnImpInventoryDate.setEnabled(false);
		btnReducedCustomsNumber.setEnabled(false);
		btnReducedClearanceNumber.setEnabled(false);
		btnViewHistory.setEnabled(false);
		btnExportExcel.setEnabled(false);
		btnemptyCurrentData.setEnabled(false);
	}
	
	private void enableControls(){
		cbbSelectVFSection.setEnabled(true);
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
			tfseqNum.setVisible(false);
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
			lblNewLabel = new JLabel("归并序号:");
			lblNewLabel.setVisible(false);
		}
		return lblNewLabel;
	}
	/**
	 * 穿透查询
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
//		cbbSelectVFSection.setSelectedItem(section);
		int count = cbbSelectVFSection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbbSelectVFSection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbbSelectVFSection.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null)
			tfseqNum.setText(String.valueOf(mergerNo));
		tabbedPane.setSelectedIndex(1);
		btnViewHistory.doClick();
	}
	
	private JButton getBtnEdit() {
		if (btnEdit == null) {
			btnEdit = new JButton("修改");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DgVFBaseStockExgOrImgEdit edit = new DgVFBaseStockExgOrImgEdit();
					VFBaseStockExg baseStockExg = (VFBaseStockExg)showTableModelRawdata.getCurrentRow();
					if(baseStockExg==null){
						return;
					}
					edit.setBaseStockExg(baseStockExg);
					edit.setMaterielType(MaterielType.FINISHED_PRODUCT);
					edit.setVisible(true);
					if(edit.isOk){
						showTableModelRawdata.updateRow(baseStockExg);
					}
				}
			});
		}
		return btnEdit;
	}
	private JButton getBtnDelete() {
		if (btnDelete == null) {
			btnDelete = new JButton("删除");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new DeleteDataRunnable().execute();
				}
			});
		}
		return btnDelete;
	}
	
	/**
	 * 删除
	 * @author Administrator
	 */
	class DeleteDataRunnable extends SwingWorker {

		@Override
		protected Object doInBackground() throws Exception {
			try {
				List<VFBaseStockExg> baseStockExgs = (List<VFBaseStockExg>)showTableModelRawdata.getCurrentRows();
				if(baseStockExgs==null){
					return null;
				}
				if (JOptionPane.OK_OPTION != JOptionPane.showOptionDialog(FmVFSemiBadExg.this, "确定要删除所选择的数据吗?",
						"提示", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "是",
								"否" }, "否")) {
					return null;
				} else {
					CommonProgress.showProgressDialog(FmVFSemiBadExg.this);
					CommonProgress.setMessage("系统正在删除数据，请稍后...");
					vfVerificationAction.deleteVFBaseStockExgs(request, baseStockExgs);
					showTableModelRawdata.deleteRows(baseStockExgs);
					CommonProgress.closeProgressDialog();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				CommonProgress.closeProgressDialog();
				JOptionPane.showMessageDialog(FmVFSemiBadExg.this,
						"删除失败 " + ex.getMessage(), "提示",
						JOptionPane.ERROR_MESSAGE);
			} finally {
				CommonProgress.closeProgressDialog();
			}
			return null;
		}

		@Override
		protected void done() {
			CommonProgress.closeProgressDialog();
		}
	}
} 
