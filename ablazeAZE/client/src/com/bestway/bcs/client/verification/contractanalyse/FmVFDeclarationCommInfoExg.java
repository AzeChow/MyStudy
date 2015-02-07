package com.bestway.bcs.client.verification.contractanalyse;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.table.DefaultTableCellRenderer;

import com.bestway.bcs.verification.action.VFVerificationAction;
import com.bestway.bcs.verification.action.VFVerificationAuthority;
import com.bestway.bcs.verification.entity.VFAnalyType;
import com.bestway.bcs.verification.entity.VFSection;
import com.bestway.bcus.client.common.CommonProgress;
import com.bestway.bcus.client.common.CommonStepProgress;
import com.bestway.bcus.client.common.CommonVars;
import com.bestway.bcus.client.common.CustomBaseComboBoxEditor;
import com.bestway.bcus.client.common.CustomBaseRender;
import com.bestway.bcus.client.common.ItemProperty;
import com.bestway.bcus.client.common.PnCommonQueryPage;
import com.bestway.client.util.JTableListColumn;
import com.bestway.client.util.JTableListModel;
import com.bestway.client.util.JTableListModelAdapter;
import com.bestway.common.Request;
import com.bestway.common.constant.ImpExpType;
import com.bestway.ui.winuicontrol.JInternalFrameBase;
import com.bestway.util.AsynSwingWorker;

public class FmVFDeclarationCommInfoExg extends JInternalFrameBase {


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
	private JTableListModel tableModel = null; 
	private JPanel panel;
	private JToolBar toolBar;
	private JScrollPane scrollPane;
	private JTable tbMaterials;
	/**
	 * 分页菜单公共组件
	 */
	private PnCommonQueryPage pnCommonQueryPage=null;
	
	private VFVerificationAction vfVerificationAction=null;

	/**
	 * Create the frame.
	 */
	public FmVFDeclarationCommInfoExg() {
		super();
		vfVerificationAction=(VFVerificationAction) CommonVars
				.getApplicationContext().getBean("vfVerificationAction");
		
		VFVerificationAuthority authority = (VFVerificationAuthority)CommonVars.getApplicationContext().getBean("vfVerificationAuthority");		
		authority.checkDeclarationCommInfoExg(new Request(CommonVars.getCurrUser()));
		initialize();

		initUI();
		this.initTable(pnCommonQueryPage.dataSource);
		pnCommonQueryPage.setInitState();
	}
	
	/**
	 * 初始化控件
	 */
	private void initialize(){
		
		this.setContentPane(getJContentPane());
		this.setTitle("成品报关明细表");
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
						JOptionPane.showMessageDialog(FmVFDeclarationCommInfoExg.this, "请选择需要查询的批次！");
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
			btnFind = new JButton("获取成品明细数据");
			btnFind.setPreferredSize(new Dimension(120, 30));
			btnFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(cbbVFSection.getSelectedItem()==null){
						JOptionPane.showMessageDialog(FmVFDeclarationCommInfoExg.this, "请选择需要查询的批次！");
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
						JOptionPane.showMessageDialog(FmVFDeclarationCommInfoExg.this, "请选择需要查询的批次！");
						return;
					}
					if(tableModel.getRowCount()<=0){
						return;
					}
					try {
						int type=JOptionPane.showOptionDialog(null, "确定要清空本次批次的数据吗?","提示",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE,null,new Object[]{"是(Y)","否(N)"},"否(N)");
						if(type==JOptionPane.OK_OPTION){
							VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
							vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_EXG);
						}else{
							return;
						}
						pnCommonQueryPage.refreshData();
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(FmVFDeclarationCommInfoExg.this, "未能成功删除！","警告",JOptionPane.WARNING_MESSAGE);
						return;
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
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
		}
		return btnClose;
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getToolBar(), BorderLayout.NORTH);
			panel.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel;
	}
	private JToolBar getToolBar() {
		if (toolBar == null) {
			FlowLayout f=new FlowLayout();
			f.setAlignment(FlowLayout.LEFT);
			f.setVgap(1);
			f.setHgap(1);
			toolBar=new JToolBar();
			toolBar.setLayout(f);
			toolBar.add(getPnCommonQueryPage());
		}
		return toolBar;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (tbMaterials == null) {
			tbMaterials = new JTable();
		}
		return tbMaterials;
	}
	
	/**
	 * 初始化下拉框
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
					return FmVFDeclarationCommInfoExg.this.initTable(dataSource);
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
								new Request(CommonVars.getCurrUser()), vf ,index, length, property, value, isLike,VFAnalyType.VFCUSTOMS_EXG);
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
						Long count = vfVerificationAction.countVFCustomsImgOrExgBySection(new Request(CommonVars.getCurrUser()), vf, property, value, isLike, VFAnalyType.VFCUSTOMS_EXG);
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
	 * 初始化表格
	 * @param list
	 * @return
	 */
	private JTableListModel initTable(List list){
		tableModel=new JTableListModel(tbMaterials, list, new JTableListModelAdapter() {
			@Override
			public List InitColumns() {
				List<JTableListColumn> list=new Vector<JTableListColumn>();
				list.add(addColumn("备案序号", "seqNum", 100,Integer.class));
				list.add(addColumn("手册号",  "emsNo", 100));
				list.add(addColumn("报关单号", "customsDeclarationCode", 100));
				list.add(addColumn("企业名称","companyName", 100));
				list.add(addColumn("申报日期","declarationDate", 100));
				list.add(addColumn("商品名称","commName", 100));
				list.add(addColumn("商品规格","commSpec", 100));
				list.add(addColumn("商品数量","commAmount", 100));
				list.add(addColumn("单位", "unit", 100));
				list.add(addColumn("商品单价", "commUnitPrice", 100));
				list.add(addColumn("总金额", "totalMoney", 100));
				list.add(addColumn("进出口类型", "impExpType", 100));
				list.add(addColumn("贸易方式", "tradeMode", 100));
				list.add(addColumn("归并序号", "mergerNo", 100,Integer.class));
				return list;
			}
		});
		
		//重写setCellRenderer转换'进出口类型'格式
		tbMaterials.getColumnModel().getColumn(12).setCellRenderer(
				new DefaultTableCellRenderer(){
					public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						        super.getTableCellRendererComponent(table, value,
								isSelected, hasFocus, row, column);
								super.setText((value == null) ? "" : castValue1(value));
								return this;
							}

					private String castValue1(Object value) {
								String returnValue = "";
								returnValue=ImpExpType.getImpExpTypeDesc(Integer.parseInt((String)value));
								return returnValue;
							}
		});
		return tableModel;
	
	}
	
	/**
	 * 获取数据线程
	 * @author Administrator
	 *
	 */
	class GetQueryData extends Thread{
		public void run(){
			try {
				CommonProgress.showProgressDialog(FmVFDeclarationCommInfoExg.this);
				CommonProgress.setMessage("系统正在删除数据，请稍后...");
				setButtonState(false);
				VFSection vf=(VFSection) cbbVFSection.getSelectedItem();
				//先删除
				vfVerificationAction.delectVFBySection(new Request(CommonVars.getCurrUser()), vf, VFAnalyType.VFCUSTOMS_EXG);
				CommonProgress.setMessage("系统正在保存数据，请稍后...");
				//后增加，再读取
				vfVerificationAction
						.gainVFCustomsExg(new Request(CommonVars.getCurrUser()), vf);
				CommonProgress.setMessage("系统正在读取数据，请稍后...");
				pnCommonQueryPage.setInitState();
			}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(FmVFDeclarationCommInfoExg.this, "获取并保存数据失败！","警告",JOptionPane.WARNING_MESSAGE);
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
	 * 根据条件查询并显示数据
	 * @param section 批次
	 * @param emsNo 手册号
	 * @param seqNum 备案序号
	 */
	public void showData(final VFSection section, final String emsNo,final Integer seqNum) {
		new AsynSwingWorker<List<?>>() {
			protected List<?> submit() {
				try{
					CommonProgress.showProgressDialog();
					CommonProgress.setMessage("正在加载数据，请稍候...");
					setButtonState(false);
					return vfVerificationAction.findVFCustomsExg(new Request(CommonVars.getCurrUser()),section,emsNo,seqNum);
				}finally{
					setButtonState(true);
					CommonProgress.closeProgressDialog();
				}
			}
			protected void success(java.util.List<?> result) {
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
				initTable(result);
			}
		}.doWork();
	}
	
}
