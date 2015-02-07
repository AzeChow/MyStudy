package com.bestway.bcs.client.verification.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumnModel;


import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.client.util.groupableheader.ColumnGroup;
import com.bestway.client.util.groupableheader.GroupableTableHeader;
import com.bestway.client.util.mutispan.AttributiveCellTableModel;
import com.bestway.client.util.mutispan.MultiSpanCellTable;
import com.bestway.common.Request;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import javax.swing.JTextField;

public class FmVFCustomsCountExgConvert extends JInternalFrameBase {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane=null;
	private JToolBar toolbar=null;
	private JLabel lbBatch=null;
	/**
	 * 批次设定下拉框
	 */
	private JComboBox cbbVFSection=null;
	/**
	 * 查看历史记录按钮
	 */
	private JButton btnHistory=null;
	/**
	 * 获取数据按钮
	 */
	private JButton btnFind=null;
	/**
	 * 导出数据按钮
	 */
	private JButton btnExport=null;
	/**
	 * 清除当前数据按钮
	 */
	private JButton btnClear=null;
	/**
	 * 关闭窗口按钮
	 */
	private JButton btnClose=null;
	/**
	 * 表格
	 */
	private JPanel panel=null;
	private JToolBar tbPage=null;
	private JScrollPane scrollPane=null;
	private JTable tbMaterials=null;
	private AttributiveCellTableModel tableModel = null;
	/**
	 * 分页菜单公共组件
	 */
	private PnCommonQueryPage pnCommonQueryPage=null;
	
	private VFVerificationAction vfVerificationAction=null;

	
	/**
	 * Create the frame.
	 */
	public FmVFCustomsCountExgConvert() {
		super();
		vfVerificationAction=(VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkCustomsCountExgConvert(new Request(CommonVars.getCurrUser()));
		initialize();

		initUI();
		initTable(pnCommonQueryPage.dataSource);
		pnCommonQueryPage.setInitState();
	}
	
	/**
	 * 初始化控件
	 */
	private void initialize(){
		
		this.setContentPane(getJContentPane());
		this.setTitle("成品折料统计表");
		
	}
	
	private JPanel getJContentPane() {
		if(jContentPane==null){
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);
			jContentPane.add(getPanel(), BorderLayout.CENTER);
			
		}
		
		return jContentPane;
	}
	
	private JToolBar getJToolBar() {
		if(toolbar==null){
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(0);
			f.setHgap(0);
			toolbar=new JToolBar();
			toolbar.setLayout(f);
			toolbar.add(getlbBatch());
			toolbar.add(getCbbDate());
			toolbar.add(getBtnHistory());
			toolbar.add(getBtnFind());
			toolbar.add(getBtnExport());
			toolbar.add(getBtnClear());
			toolbar.add(getBtnClose());
		}
		
		return toolbar;
	}
	
	private JLabel getlbBatch(){
		if(lbBatch==null){
			lbBatch=new JLabel();
			lbBatch.setText("批次设定：");
		}
		return lbBatch;
	}
	
	private JComboBox getCbbDate() {
		if (cbbVFSection == null) {
			cbbVFSection = new JComboBox();
			cbbVFSection.setPreferredSize(new Dimension(120, 27));
		}
		return cbbVFSection;
	}
	
	private JButton getBtnHistory(){
		if(btnHistory==null){
			btnHistory=new JButton("查询批次数据");
			btnHistory.setPreferredSize(new Dimension(90, 30));
			btnHistory.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFCustomsCountExgConvert.this, "请选择需要查询的批次!");
						return;
					}

					pnCommonQueryPage.setInitState();
				}
			});
			
		}
		return btnHistory;
	}
	
	
	private JButton getBtnFind() {
		if (btnFind == null) {
			btnFind = new JButton("获取折算统计数据");
			btnFind.setPreferredSize(new Dimension(120, 30));
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFCustomsCountExgConvert.this, "请选择需要查询的批次！");
						return;
					}
					int type=JOptionPane.showOptionDialog(null, "确定要先删除本次批次的数据再重新获取吗？","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
					if(type==JOptionPane.OK_OPTION){
						new GetQueryData().start();
					}else{
						return;
					}
				}
			});
			
		}
		return btnFind;
	}
	
	private JButton getBtnExport() {
		if (btnExport == null) {
			btnExport = new JButton("导出excel");
			btnExport.setPreferredSize(new Dimension(90, 30));
			btnExport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(tableModel.getRowCount()<=0){
						return;
					}else{
						tableModel.getMiSaveAllToExcel().doClick();
					}
				}
			});
			
		}
		return btnExport;
	}
	private JButton getBtnClear() {
		if (btnClear == null) {
			btnClear = new JButton("清空当前数据");
			btnClear.setPreferredSize(new Dimension(90, 30));
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFCustomsCountExgConvert.this, "请选择需要查询的批次!");
						return;
					}
					if(tableModel.getRowCount()<=0){
						return;
					}
					try {
						int type=JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
						if(type==JOptionPane.OK_OPTION){
							VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
							vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT);
						}else{
							return;
						}
						pnCommonQueryPage.refreshData();
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(FmVFCustomsCountExgConvert.this, "未能成功删除！","警告",JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			
		}
		return btnClear;
	}
	
	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("关闭");
			btnClose.setPreferredSize(new Dimension(65, 30));
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});

		}
		return btnClose;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTbMaterials());
			
		}
		return scrollPane;
	}
	private JTable getTbMaterials() {
		if (tbMaterials == null) {
			tbMaterials = new MultiSpanCellTable();
			tbMaterials.getColumnModel().addColumnModelListener(
					new TableColumnModelListener() {
						public void columnAdded(TableColumnModelEvent e) {}
						public void columnMarginChanged(ChangeEvent e) {}
						public void columnMoved(TableColumnModelEvent e) {}
						public void columnRemoved(TableColumnModelEvent e) {}
						public void columnSelectionChanged(ListSelectionEvent e) {
							int[] columns = tbMaterials.getSelectedColumns();
							int[] rows = tbMaterials.getSelectedRows();
							if (columns.length < 1 || rows.length < 1) {
								return;
							}
							if (columns[0] >= 0 && columns[0] <= 5) {
								tbMaterials.setColumnSelectionInterval(1, 5);
								return;
							} else if (columns[0] >= 6 && columns[0] <= 14) {
								tbMaterials.setColumnSelectionInterval(6, 14);
								return;
							}
						}
						
					});
		}
		return tbMaterials;
	}
	
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getTbPage(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JToolBar getTbPage() {
		if (tbPage == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			tbPage=new JToolBar();
			tbPage.setLayout(f);
			tbPage.add(getPnCommonQueryPage());
		}
		return tbPage;
	}
	
	/**
	 * 初始化批次下拉框
	 */
	private void initUI(){
		DefaultComboBoxModel DfComboBox = new DefaultComboBoxModel();
		//格式化时间
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List dataSource=vfVerificationAction.findVFSectionList(new Request(CommonVars.getCurrUser()));
		List<VFSection> manulist=new ArrayList<VFSection>();
		for (int i = 0; i < dataSource.size(); i++) {
			VFSection vfSection=(VFSection) dataSource.get(i);
			vfSection.setEndDate(Date.valueOf(DateFormat.format(vfSection.getEndDate())));
			manulist.add(vfSection);
		}
		DfComboBox = new DefaultComboBoxModel(manulist.toArray());
		this.cbbVFSection.setModel(DfComboBox);
		CustomBaseComboBoxEditor.getInstance().setComboBoxEditor(cbbVFSection,"verificationNo","endDate",150);
		this.cbbVFSection.setRenderer(CustomBaseRender.getInstance().getRender(
				"verificationNo", "endDate", 50, 100));
		this.cbbVFSection.setSelectedItem(null);
		
	}
	
	
	private JTableListModel initTable(List list){
		if(list==null){
			list=new Vector();
		}
		tableModel = new AttributiveCellTableModel((MultiSpanCellTable) tbMaterials,
				list, new JTableListModelAdapter() {
					public List<JTableListColumn> InitColumns() {
						List<JTableListColumn> list=new Vector<JTableListColumn>();
						list.add(addColumn("手册号", "emsNo", 100));
						list.add(addColumn("备案成品序号", "vfCustomsCountExg.seqNum", 100,Integer.class));
						list.add(addColumn("备案成品名称", "vfCustomsCountExg.commName", 100));
						list.add(addColumn("备案成品规格", "vfCustomsCountExg.commSpec", 100));
						list.add(addColumn("备案成品数量", "vfCustomsCountExg.expTotalAmont", 100));
						list.add(addColumn("备案料件序号", "seqNum", 100,Integer.class));
						list.add(addColumn("备案料件名称", "commName", 100));
						list.add(addColumn("备案料件规格", "commSpec", 100));
						list.add(addColumn("备案料件单位", "unit", 100));
						list.add(addColumn("单耗", "unitWaste", 100));
						list.add(addColumn("损耗", "waste", 100));
						list.add(addColumn("单项用量", "unitUsed", 100));
						list.add(addColumn("成品耗用量", "wasteAmount", 100));
						list.add(addColumn("归并序号", "mergerNo", 100,Integer.class));
						return list;
					
					}
				});

		TableColumnModel cm = tbMaterials.getColumnModel();

		ColumnGroup hsExgGroup = new ColumnGroup("【备案成品】");
		hsExgGroup.add(cm.getColumn(1));
		hsExgGroup.add(cm.getColumn(2));
		hsExgGroup.add(cm.getColumn(3));
		hsExgGroup.add(cm.getColumn(4));
		hsExgGroup.add(cm.getColumn(5));
		
		ColumnGroup hsImgGroup = new ColumnGroup("【备案料件】");
		hsImgGroup.add(cm.getColumn(6));
		hsImgGroup.add(cm.getColumn(7));
		hsImgGroup.add(cm.getColumn(8));
		hsImgGroup.add(cm.getColumn(9));
		hsImgGroup.add(cm.getColumn(10));
		hsImgGroup.add(cm.getColumn(11));
		hsImgGroup.add(cm.getColumn(12));
		hsImgGroup.add(cm.getColumn(13));
		hsImgGroup.add(cm.getColumn(14));

		GroupableTableHeader header = (GroupableTableHeader) tbMaterials
				.getTableHeader();
		header.addColumnGroup(hsExgGroup);
		header.addColumnGroup(hsImgGroup);
		
		return tableModel;
	}
	
	
	/**
	 * 公共查询组件
	 * 
	 * @return
	 */
	private PnCommonQueryPage getPnCommonQueryPage()
	{
		if (pnCommonQueryPage == null)
		{
			pnCommonQueryPage = new PnCommonQueryPage()
			{
				/**
				 * 初始化表格
				 */
				@Override
				public JTableListModel initTable(List dataSource)
				{
					return FmVFCustomsCountExgConvert.this.initTable(dataSource);
				}

				/**
				 * 获得数据源
				 */
				@Override
				public List getDataSource(int index, int length, String property,
						Object value, boolean isLike)
				{
					List sourceList =null;
					if(cbbVFSection.getSelectedItem()==null){
						
					}else{
						VFSection vf=(VFSection)cbbVFSection.getSelectedItem() ;
						sourceList = vfVerificationAction.findPageVFByVFSection(
								new Request(CommonVars.getCurrUser()), vf ,index, length, property, value, isLike,VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT);
						return sourceList;
					}
					return null;
				}

				/**
				 * 获得总条数
				 */
				@Override
				public Long getDataSourceCount(int index, int length,
						String property, Object value, boolean isLike)
				{
					if(cbbVFSection.getSelectedItem()==null){
						
					}else{
						VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
						Long count = vfVerificationAction.countVFCustomsImgOrExgBySection(new Request(CommonVars.getCurrUser()), 
								vf, property, value, isLike, VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT);
						return count; 
					}
					return 0L;
				}
			};
			pnCommonQueryPage.setMaximumSize(new Dimension(10,35));
		}
		
		return pnCommonQueryPage;
	}
	
	/**
	 * 获取数据线程
	 * @author Administrator
	 *
	 */
	class GetQueryData extends Thread{
		public void run(){
			try {
				CommonProgress.showProgressDialog(FmVFCustomsCountExgConvert.this);
				CommonProgress.setMessage("系统正在删除数据，请稍后...");
				setButtonState(false);
				VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
				//先删除
				vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_COUNTEXG_CONVERT);
				//后增加，再读取
				CommonProgress.setMessage("系统正在保存数据，请稍后...");
				vfVerificationAction
						.convertCustomsCountExgToImg(new Request(CommonVars.getCurrUser()), vf);
				CommonProgress.setMessage("系统正在读取数据，请稍后...");
				pnCommonQueryPage.setInitState();
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(FmVFCustomsCountExgConvert.this, "获取并保存数据失败！","警告",JOptionPane.WARNING_MESSAGE);
			}finally{
				setButtonState(true);
				CommonProgress.closeProgressDialog();
			}
		}
	}
	
	public void setButtonState(boolean isEnabled){
		cbbVFSection.setEnabled(isEnabled);
		btnHistory.setEnabled(isEnabled);
		btnFind.setEnabled(isEnabled);
		btnExport.setEnabled(isEnabled);
		btnClear.setEnabled(isEnabled);
		btnClose.setEnabled(isEnabled);
	}
	/**
	 * 根据查询条件显示数据
	 * @param section
	 * @param mergerNo
	 */
	public void showData(VFSection section, Integer mergerNo) {
//		cbbVFSection.setSelectedItem(section);
		int count = cbbVFSection.getItemCount();
		for (int i = 0; i < count; i++) {
			Object obj = cbbVFSection.getItemAt(i);
			if(obj instanceof VFSection){
				VFSection vfsection = (VFSection)obj;
				if(vfsection.getId().equals(section.getId())){
					cbbVFSection.setSelectedIndex(i);
				}
			}
		}
		if(mergerNo != null){
			pnCommonQueryPage.getCbbQueryField().setSelectedIndex(13);
			pnCommonQueryPage.getTfQueryValue().setText(String.valueOf(mergerNo));
			pnCommonQueryPage.getRbPrecision().setSelected(true);
		}		
		btnHistory.doClick();
	}

}
